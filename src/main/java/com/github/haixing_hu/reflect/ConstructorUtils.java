/******************************************************************************
 *
 * Copyright (c) 2013  Haixing Hu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package com.github.haixing_hu.reflect;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.ObjectStreamConstants;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import com.github.haixing_hu.io.IoUtils;
import com.github.haixing_hu.lang.ArrayUtils;
import com.github.haixing_hu.lang.ClassUtils;
import com.github.haixing_hu.lang.Equality;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * Provides utility reflection methods focused on constructors, modeled after
 * {@link MethodUtils}.
 *
 * <h2>Known Limitations</h2> <h4>Accessing Public Constructors In A Default
 * Access Superclass</h4>
 * <p>
 * There is an issue when invoking public constructors contained in a default
 * access superclass. Reflection locates these constructors fine and correctly
 * assigns them as public. However, an {@code IllegalAccessException} is
 * thrown if the constructors is invoked.
 * </p>
 *
 * <p>
 * {@code ConstructorUtils} contains a workaround for this situation. It
 * will attempt to call {@code setAccessible} on this constructor. If this
 * call succeeds, then the method can be invoked as normal. This call will only
 * succeed when the application has sufficient security privileges. If this call
 * fails then a warning will be logged and the method may fail.
 * </p>
 *
 * @author Haixing Hu
 * @since 1.0.0
 */
@ThreadSafe
public class ConstructorUtils {

  private static final Map<Class<?>, byte[]> serializedDataCache =
      new HashMap<Class<?>, byte[]>();

  private static final Map<Class<?>, List<Constructor<?>>> constructorCache =
      new HashMap<Class<?>, List<Constructor<?>>>();

  private static List<Constructor<?>> getAllConstructors(Class<?> cls) {
    requireNonNull("cls", cls);
    synchronized (constructorCache) {
      List<Constructor<?>> constructors = constructorCache.get(cls);
      if (constructors == null) {
        constructors = new ArrayList<Constructor<?>>();
        for (final Constructor<?> ctor : cls.getDeclaredConstructors()) {
          ctor.setAccessible(true); // suppress access checks
          constructors.add(ctor);
        }
        constructorCache.put(cls, constructors);
      }
      return constructors;
    }
  }

  /**
   * Creates an instance of a object using the Java serialization.
   *
   * @param cls
   *          The class to instantiate.
   * @return a new instance of the class.
   * @since 1.0.0
   */
  private static <T> T newInstanceUsingSerialization(final Class<T> cls) {
    try {
      byte[] data = null;
      synchronized (serializedDataCache) {
        data = serializedDataCache.get(cls);
        if (data == null) {
          final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
          final DataOutputStream dos = new DataOutputStream(bytes);
          dos.writeShort(ObjectStreamConstants.STREAM_MAGIC);
          dos.writeShort(ObjectStreamConstants.STREAM_VERSION);
          dos.writeByte(ObjectStreamConstants.TC_OBJECT);
          dos.writeByte(ObjectStreamConstants.TC_CLASSDESC);
          dos.writeUTF(cls.getName());
          dos.writeLong(ObjectStreamClass.lookup(cls).getSerialVersionUID());
          dos.writeByte(2); // classDescFlags (2 = Serializable)
          dos.writeShort(0); // field count
          dos.writeByte(ObjectStreamConstants.TC_ENDBLOCKDATA);
          dos.writeByte(ObjectStreamConstants.TC_NULL);
          data = bytes.toByteArray();
          serializedDataCache.put(cls, data);
        }
      }
      final ByteArrayInputStream bin = new ByteArrayInputStream(data);
      final ObjectInputStream in = new ObjectInputStream(bin) {
        @Override
        protected Class<?> resolveClass(ObjectStreamClass desc)
            throws IOException, ClassNotFoundException {
          return Class.forName(desc.getName(), false, cls.getClassLoader());
        }
      };
      try {
        @SuppressWarnings("unchecked")
        final T result = (T) in.readObject();
        return result;
      } finally {
        IoUtils.closeQuietly(in);
      }
    } catch (final IOException e) {
      throw new ConstructFailedException(cls, e);
    } catch (final ClassNotFoundException e) {
      throw new ConstructFailedException(cls, e);
    }
  }

  /**
   * Gets all constructors of a class.
   *
   * @param cls
   *          a class.
   * @param options
   *          A bitwise combination of reflection options defined in the
   *          {@link Option} class. The default value could be
   *          {@link Option#DEFAULT}.
   * @return the array of all specified constructors; or an empty array if no
   *         such constructor.
   * @throws ReflectionException
   *           if any error occurred.
   */
  public static <T> Constructor<T>[] getAllConstructors(Class<T> cls, int options) {
    final List<Constructor<?>> constructors = getAllConstructors(cls);
    final List<Constructor<T>> result = new ArrayList<Constructor<T>>();
    for (final Constructor<?> c : constructors) {
      if (Option.satisfy(cls, c, options)) {
        @SuppressWarnings("unchecked")
        final Constructor<T> ctor = (Constructor<T>) c;
        result.add(ctor);
      }
    }
    @SuppressWarnings("unchecked")
    final Constructor<T>[] array = new Constructor[result.size()];
    return result.toArray(array);
  }

  /**
   * Gets a constructor of a class that matches the given parameter types.
   *
   * @param cls
   *          The class on which to get the constructor.
   * @param options
   *          A bitwise combination of reflection options defined in the
   *          {@link Option} class. The default value could be
   *          {@link Option#DEFAULT}.
   * @param paramTypes
   *          The types of the parameters of the constructor to be get, or an
   *          empty array if the constructor to be get has no parameter.
   * @return the specified constructor, or {@code null} if no such field.
   * @throws ReflectionException
   *           if any error occurred.
   */
  public static <T> Constructor<T> getConstructor(Class<T> cls, int options,
      @Nullable Class<?>[] paramTypes) throws ReflectionException {
    final List<Constructor<?>> constructors = getAllConstructors(cls);
    Constructor<T> result = null;
    boolean ambiguous = false;
    if (paramTypes == null) {
      paramTypes = ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    for (final Constructor<?> c : constructors) {
      @SuppressWarnings("unchecked")
      final Constructor<T> ctor = (Constructor<T>) c;
      if (Option.satisfy(cls, ctor, options)
          && Equality.equals(paramTypes, ctor.getParameterTypes())) {
        if (result == null) {
          result = ctor;
          ambiguous = false;
        } else {
          ambiguous = true;
        }
      }
    }
    if (ambiguous) {
      throw new AmbiguousMemberException(cls, result.getName());
    }
    return result;
  }

  /**
   * Gets a constructor of a class that matches the compatible parameter types.
   *
   * @param cls
   *          The class on which to get the constructor.
   * @param options
   *          A bitwise combination of reflection options defined in the
   *          {@link Option} class. The default value could be
   *          {@link Option#DEFAULT}.
   * @param paramTypes
   *          The types of the compatible parameters of the constructor to be
   *          get, or an empty array if the constructor to be get has no
   *          parameter.
   * @return the specified constructor, or {@code null} if no such field.
   * @throws ReflectionException
   *           if any error occurred.
   */
  public static <T> Constructor<T> getMatchingConstructor(Class<T> cls,
      int options, Class<?>[] paramTypes) throws ReflectionException {
    final List<Constructor<?>> constructors = getAllConstructors(cls);
    Constructor<T> result = null;
    boolean ambiguous = false;
    if (paramTypes == null) {
      paramTypes = ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    for (final Constructor<?> c : constructors) {
      @SuppressWarnings("unchecked")
      final Constructor<T> ctor = (Constructor<T>) c;
      if (Option.satisfy(cls, ctor, options)
          && ClassUtils.isAssignable(paramTypes, ctor.getParameterTypes())) {
        if (result == null) {
          result = ctor;
          ambiguous = false;
        } else {
          final int rc = MemberUtils.compareParameterTypes(
              ctor.getParameterTypes(), result.getParameterTypes(), paramTypes);
          if (rc < 0) {
            // the parameter types of new constructor is better than the result
            result = ctor;
            ambiguous = false;
          } else if (rc == 0) {
            ambiguous = true;
          }
        }
      }
    }
    if (ambiguous) {
      throw new AmbiguousMemberException(cls, result.getName());
    }
    return result;
  }

  /**
   * Creates a new instance of the specified class.
   * <p>
   * It is in the responsibility of the implementation how such an instance is
   * created.
   * </p>
   *
   * @param cls
   *          The class to instantiate.
   * @param options
   *          A bitwise combination of reflection options defined in the
   *          {@link Option} class. The default value could be
   *          {@link Option#DEFAULT}.
   * @return a new instance of the class
   * @since 1.0.0
   */
  public static <T> T newInstance(Class<T> cls, int options)
      throws ReflectionException {
    requireNonNull("cls", cls);
    final Constructor<T> ctor = getConstructor(cls, options, null);
    if (ctor != null) {
      try {
        return ctor.newInstance(new Object[0]);
      } catch (final InstantiationException e) {
        throw new ConstructFailedException(cls, e);
      } catch (final IllegalAccessException e) {
        throw new ConstructFailedException(cls, e);
      } catch (final InvocationTargetException e) {
        throw new ConstructFailedException(cls, e);
      }
    } else if (((options & Option.SERIALIZATION) != 0)
        && Serializable.class.isAssignableFrom(cls)) {
        return newInstanceUsingSerialization(cls);
    } else {
      throw new ConstructorNotExistException(cls, options);
    }
  }

  /**
   * Creates a new instance of the specified class inferring the right
   * constructor from the types of the arguments.
   * <p>
   * This locates and calls a constructor. The constructor signature must match
   * the argument types by assignment compatibility.
   *
   * @param <T>
   *          The type to be constructed
   * @param cls
   *          The class to be constructed.
   * @param options
   *          A bitwise combination of reflection options defined in the
   *          {@link Option} class. The default value could be
   *          {@link Option#DEFAULT}.
   * @param arguments
   *          The array of arguments. If the constructor has no arguments, it
   *          could be {@code null} or empty.
   * @return A new instance of {@code cls}, which will never be
   *         {@code null}.
   * @throws ReflectionException
   *           if any error occurred.
   * @since 1.0.0
   */
  public static <T> T newInstance(Class<T> cls, int options,
      @Nullable Object... arguments) {
    if (arguments == null) {
      return newInstance(cls, options, null, null);
    } else {
      final Class<?>[] paramTypes = new Class<?>[arguments.length];
      for (int i = 0; i < arguments.length; ++i) {
        paramTypes[i] = arguments[i].getClass();
      }
      return newInstance(cls, options, paramTypes, arguments);
    }
  }

  /**
   * Creates a new instance of the specified class inferring the right
   * constructor from the specified parameter types.
   * <p>
   * This locates and calls a constructor. The constructor signature must match
   * the parameter types by assignment compatibility.
   *
   * @param <T>
   *          The type to be constructed
   * @param cls
   *          The class to be constructed.
   * @param options
   *          A bitwise combination of reflection options defined in the
   *          {@link Option} class. The default value could be
   *          {@link Option#DEFAULT}.
   * @param paramTypes
   *          The array of classes of the parameters for the constructor to be
   *          invoked. If the constructor has no arguments, it could be
   *          {@code null} or empty.
   * @param arguments
   *          The array of arguments. If the constructor has no arguments, it
   *          could be {@code null} or empty.
   * @return A new instance of {@code cls}, which will never be
   *         {@code null}.
   * @throws ReflectionException
   *           if any error occurred.
   * @since 1.0.0
   */
  public static <T> T newInstance(Class<T> cls, int options,
      @Nullable Class<?>[] paramTypes, @Nullable Object[] arguments) {
    requireNonNull("cls", cls);
    if (paramTypes == null) {
      paramTypes = ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    final Constructor<T> ctor = getMatchingConstructor(cls, options, paramTypes);
    if (ctor != null) {
      try {
        return ctor.newInstance(arguments);
      } catch (final IllegalArgumentException e) {
        throw new ConstructFailedException(cls,e);
      } catch (final InstantiationException e) {
        throw new ConstructFailedException(cls, e);
      } catch (final IllegalAccessException e) {
        throw new ConstructFailedException(cls, e);
      } catch (final InvocationTargetException e) {
        throw new ConstructFailedException(cls, e);
      }
    } else {
      throw new ConstructorNotExistException(cls, options, paramTypes);
    }
  }

  /**
   * Creates a new instance of the specified class choosing the right
   * constructor from the exact types of the arguments.
   * <p>
   * This function locates and calls a constructor. The constructor signature
   * must exactly match the specified argument types.
   *
   * @param <T>
   *          The type to be constructed
   * @param cls
   *          The class to be constructed.
   * @param options
   *          A bitwise combination of reflection options defined in the
   *          {@link Option} class. The default value could be
   *          {@link Option#DEFAULT}.
   * @param arguments
   *          The array of arguments. If the constructor has no arguments, it
   *          could be {@code null} or empty.
   * @return A new instance of {@code cls}, which will never be
   *         {@code null}.
   * @throws ReflectionException
   *           if any error occurred.
   * @since 1.0.0
   */
  public static <T> T newInstanceExactly(Class<T> cls, int options,
      @Nullable Object... arguments) {
    if (arguments == null) {
      return newInstanceExactly(cls, options, null, null);
    } else {
      final Class<?>[] paramTypes = new Class<?>[arguments.length];
      for (int i = 0; i < arguments.length; ++i) {
        paramTypes[i] = arguments[i].getClass();
      }
      return newInstanceExactly(cls, options, paramTypes, arguments);
    }
  }

  /**
   * Creates a new instance of the specified class choosing the right
   * constructor from the given parameter types.
   * <p>
   * This function locates and calls a constructor. The constructor signature
   * must exactly match the specified parameter types.
   *
   * @param <T>
   *          The type to be constructed
   * @param cls
   *          The class to be constructed.
   * @param options
   *          A bitwise combination of reflection options defined in the
   *          {@link Option} class. The default value could be
   *          {@link Option#DEFAULT}.
   * @param paramTypes
   *          The array of classes of the parameters for the constructor to be
   *          invoked. If the constructor has no arguments, it could be
   *          {@code null} or empty.
   * @param arguments
   *          The array of arguments. If the constructor has no arguments, it
   *          could be {@code null} or empty.
   * @return A new instance of {@code cls}, which will never be
   *         {@code null}.
   * @throws ReflectionException
   *           if any error occurred.
   * @since 1.0.0
   */
  public static <T> T newInstanceExactly(Class<T> cls, int options,
      @Nullable Class<?>[] paramTypes, @Nullable Object[] arguments) {
    requireNonNull("cls", cls);
    if (paramTypes == null) {
      paramTypes = ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    final Constructor<T> ctor = getConstructor(cls, options, paramTypes);
    if (ctor != null) {
      try {
        return ctor.newInstance(arguments);
      } catch (final IllegalArgumentException e) {
        throw new ConstructFailedException(cls,e);
      } catch (final InstantiationException e) {
        throw new ConstructFailedException(cls, e);
      } catch (final IllegalAccessException e) {
        throw new ConstructFailedException(cls, e);
      } catch (final InvocationTargetException e) {
        throw new ConstructFailedException(cls, e);
      }
    } else {
      throw new ConstructorNotExistException(cls, options, paramTypes);
    }
  }
}
