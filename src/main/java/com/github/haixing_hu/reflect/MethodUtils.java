/*
 * Copyright (c) 2014  Haixing Hu
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
 */
package com.github.haixing_hu.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import com.github.haixing_hu.lang.ArrayUtils;
import com.github.haixing_hu.lang.ClassUtils;
import com.github.haixing_hu.lang.Equality;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * Provides utility reflection methods focused on methods, originally from
 * Commons BeanUtils. Differences from the BeanUtils version may be noted,
 * especially where similar functionality already existed within Lang.
 *
 * <h3>Known Limitations</h3> <h4>Accessing Public Methods In A Default Access
 * Superclass</h4>
 * <p>
 * There is an issue when invoking public methods contained in a default access
 * superclass on JREs prior to 1.4. Reflection locates these methods fine and
 * correctly assigns them as public. However, an
 * {@code IllegalAccessException} is thrown if the method is invoked.
 * </p>
 *
 * <p>
 * {@code MethodUtils} contains a workaround for this situation. It will
 * attempt to call {@code setAccessible} on this method. If this call
 * succeeds, then the method can be invoked as normal. This call will only
 * succeed when the application has sufficient security privileges. If this call
 * fails then the method may fail.
 * </p>
 *
 * @author Haixing Hu
 * @since 1.0.0
 */
@ThreadSafe
public class MethodUtils {

  private static final Map<Class<?>, List<MemberInfo>> methodCache =
      new HashMap<Class<?>, List<MemberInfo>>();

  private static void buildMethodCache(Class<?> cls) {
    if (methodCache.containsKey(cls)) {
      return;
    }
    final List<MemberInfo> methods = new ArrayList<MemberInfo>();
    final Class<?> superCls = cls.getSuperclass();
    if (superCls != null) {
      buildMethodCache(superCls); // recursively
      final List<MemberInfo> superMethods = methodCache.get(superCls);
      for (final MemberInfo m : superMethods) {
        methods.add(new MemberInfo(m.member, m.depth + 1));
      }
    }
    for (final Class<?> superInterface : cls.getInterfaces()) {
      buildMethodCache(superInterface); // recursively
      final List<MemberInfo> superMethods = methodCache.get(superInterface);
      for (final MemberInfo m : superMethods) {
        methods.add(new MemberInfo(m.member, m.depth + 1));
      }
    }
    for (final Method method : cls.getDeclaredMethods()) {
      method.setAccessible(true); // suppress access checks
      methods.add(new MemberInfo(method, 0));
    }
    Collections.sort(methods);
    methodCache.put(cls, methods);
  }

  private static List<MemberInfo> getAllMethods(Class<?> cls) {
    requireNonNull("cls", cls);
    synchronized (methodCache) {
      buildMethodCache(cls);
      return methodCache.get(cls);
    }
  }

  /**
   * Gets all methods of a class.
   *
   * @param cls
   *          The class on which to get the methods.
   * @param options
   *          A bitwise combination of reflection options defined in the
   *          {@link Option} class. The default value could be
   *          {@link Option#DEFAULT}.
   * @return the array of all specified methods; or an empty array if no such
   *         method.
   * @throws ReflectionException
   *           if any error occurred.
   */
  public static Method[] getAllMethods(Class<?> cls, int options)
      throws ReflectionException {
    final List<MemberInfo> members = getAllMethods(cls);
    final List<Method> result = new ArrayList<Method>();
    for (final MemberInfo m : members) {
      if (Option.satisfy(cls, m.member, options)) {
        result.add((Method) m.member);
      }
    }
    return result.toArray(new Method[result.size()]);
  }

  /**
   * Gets a method of a class that matches the given name and the given
   * parameter types.
   * <p>
   * NOTE: if the {@code options} argument contains {@link Option#ANCESTOR}
   * , and there is more than one method with the specified name and parameter
   * types declared in the specified class or its ancestor class or its ancestor
   * interfaces, the function will try to return the method with the shallower
   * depth; if there are more than one method has the specified name in the same
   * depth, the function will returns the one with a more precise returned type;
   * otherwise, the function will throw an {@link AmbiguousMemberException}.
   *
   * @param cls
   *          The class on which to get the method.
   * @param options
   *          A bitwise combination of reflection options defined in the
   *          {@link Option} class. The default value could be
   *          {@link Option#DEFAULT}.
   * @param name
   *          The name of the method to be get.
   * @param paramTypes
   *          The types of the parameters of the method to be get, or
   *          {@code null} or an empty array if the method to be get has no
   *          parameter.
   * @return the specified method, or {@code null} if no such field.
   * @throws ReflectionException
   *           if any error occurred.
   */
  public static Method getMethod(Class<?> cls, int options, String name,
      @Nullable Class<?>[] paramTypes) throws ReflectionException {
    requireNonNull("name", name);
    final List<MemberInfo> members = getAllMethods(cls);
    MemberInfo result = null;
    boolean ambiguous = false;
    if (paramTypes == null) {
      paramTypes = ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    for (final MemberInfo m : members) {
      final Method method = (Method) m.member;
      if (Option.satisfy(cls, method, options) && name.equals(method.getName())
          && Equality.equals(paramTypes, method.getParameterTypes())) {
        if (result == null) {
          result = m;
          ambiguous = false;
        } else if (result.depth > m.depth) {
          result = m; // keep the method with shallower depth
          ambiguous = false;
        } else if (result.depth == m.depth) {
          final Class<?> result_ret = ((Method) result.member).getReturnType();
          final Class<?> m_ret = method.getReturnType();
          if (result_ret.isAssignableFrom(m_ret)) {
            result = m; // keep the method with more precise returned type
            ambiguous = false;
          } else {
            ambiguous = true;
          }
        }
      }
    }
    if (ambiguous) {
      throw new AmbiguousMemberException(cls, name);
    }
    return (Method) result.member;
  }

  /**
   * Gets a method of a class that matches the given name and the compatible
   * parameter types.
   * <p>
   * NOTE: if the {@code options} argument contains {@link Option#ANCESTOR}
   * , and there is more than one method with the specified name and parameter
   * types declared in the specified class or its ancestor class or its ancestor
   * interfaces, the function will try to return the method with the shallower
   * depth; if there are more than one method has the specified name in the same
   * depth, the function will returns the one with a more precise returned type;
   * otherwise, the function will throw an {@link AmbiguousMemberException}.
   *
   * @param cls
   *          The class on which to get the method.
   * @param options
   *          A bitwise combination of reflection options defined in the
   *          {@link Option} class. The default value could be
   *          {@link Option#DEFAULT}.
   * @param name
   *          The name of the method to be get.
   * @param paramTypes
   *          The types of the compatible parameters of the method to be get, or
   *          an empty array if the method to be get has no parameter.
   * @return the specified method, or {@code null} if no such field.
   * @throws ReflectionException
   *           if any error occurred.
   */
  public static Method getMatchingMethod(Class<?> cls, int options,
      String name, @Nullable Class<?>[] paramTypes) throws ReflectionException {
    requireNonNull("name", name);
    final List<MemberInfo> members = getAllMethods(cls);
    MemberInfo result = null;
    boolean ambiguous = false;
    if (paramTypes == null) {
      paramTypes = ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    for (final MemberInfo m : members) {
      final Method method = (Method) m.member;
      if (Option.satisfy(cls, method, options) && name.equals(method.getName())
          && ClassUtils.isAssignable(paramTypes, method.getParameterTypes())) {
        if (result == null) {
          result = m;
          ambiguous = false;
        } else {
          final int rc = MemberUtils.compareParameterTypes(
              method.getParameterTypes(),
              ((Method) result.member).getParameterTypes(), paramTypes);
          if (rc < 0) {
            // the parameter types of new method is better than the result
            result = m;
            ambiguous = false;
          } else if (rc == 0) {
            if (result.depth > m.depth) {
              result = m; // keep the method with shallower depth
              ambiguous = false;
            } else if (result.depth == m.depth) {
              ambiguous = true;
            }
          }
        }
      }
    }
    if (ambiguous) {
      throw new AmbiguousMemberException(cls, name);
    }
    return (Method) result.member;
  }

  /**
   * Invoke the specified method of a specified object with the specified
   * arguments.
   *
   * This function simply wrap the IllegalArgumentException to make the message
   * more instructive.
   *
   * @param method
   *          The method to be invoked.
   * @param object
   *          An object whose method should be invoked.
   * @param arguments
   *          The arguments used to invoke the method, which may be
   *          {@code null} or empty if the method has no arguments.
   * @return The value returned by the invoked method.
   * @throws InvokingMethodFailedException
   *           if any error occurred.
   */
  public static Object invokeMethod(Method method, Object object,
      @Nullable Object ... arguments) throws InvokingMethodFailedException {
    requireNonNull("object", object);
    requireNonNull("method", method);
    if (arguments == null) {
      arguments = ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
    try {
      return method.invoke(object, arguments);
    } catch (final Throwable e) {
      throw new InvokingMethodFailedException(object, method, arguments, e);
    }
  }

  /**
   * Invokes a named method whose parameter types match the given arguments
   * types.
   * <p>
   * This method delegates the method search to
   * {@link #getMatchingMethod(Class, String, Class[])}.
   * <p>
   * This method supports calls to methods taking primitive parameters via
   * passing in wrapping classes. So, for example, a {@code Boolean} object
   * would match a {@code boolean} primitive.
   *
   * @param cls
   *          The class on which to get the method.
   * @param options
   *          A bitwise combination of reflection options defined in the
   *          {@link Option} class. The default value could be
   *          {@link Option#DEFAULT}.
   * @param name
   *          The name of the method to be get.
   * @param object
   *          The object on which the method should be called. If the method is
   *          a static method, this argument could be {@code null}.
   * @param arguments
   *          The arguments used to invoke the method. If the method has no
   *          argument, this argument could be {@code null} or an empty
   *          array.
   * @return The value returned by the invoked method.
   * @throws ReflectionException
   *           if any error occurred.
   */
  public static Object invokeMethod(Class<?> cls, int options, String name,
      Object object, @Nullable Object[] arguments) throws ReflectionException {
    if (arguments == null) {
      return invokeMethod(cls, options, name, null, object, null);
    } else {
      final Class<?>[] paramTypes = new Class<?>[arguments.length];
      for (int i = 0; i < arguments.length; ++i) {
        paramTypes[i] = arguments[i].getClass();
      }
      return invokeMethod(cls, options, name, paramTypes, object, arguments);
    }
  }

  /**
   * Invokes a named method whose parameter types match the given parameter
   * types.
   * <p>
   * This method delegates the method search to
   * {@link #getMatchingMethod(Class, String, Class[])}.
   * <p>
   * This method supports calls to methods taking primitive parameters via
   * passing in wrapping classes. So, for example, a {@code Boolean} object
   * would match a {@code boolean} primitive.
   *
   * @param cls
   *          The class on which to get the method.
   * @param options
   *          A bitwise combination of reflection options defined in the
   *          {@link Option} class. The default value could be
   *          {@link Option#DEFAULT}.
   * @param name
   *          The name of the method to be get.
   * @param paramTypes
   *          The types of the parameters of the method to be get, which could
   *          be {@code null} or an empty array if the method has no
   *          argument.
   * @param object
   *          The object on which the method should be called. If the method is
   *          a static method, this argument could be {@code null}.
   * @param arguments
   *          The arguments used to invoke the method. If the method has no
   *          argument, this argument could be {@code null} or an empty
   *          array.
   * @return The value returned by the invoked method.
   * @throws ReflectionException
   *           if any error occurred.
   */
  public static Object invokeMethod(Class<?> cls, int options, String name,
      @Nullable Class<?>[] paramTypes, @Nullable Object object,
      @Nullable Object[] arguments) throws ReflectionException {
    if (paramTypes == null) {
      paramTypes = ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    final Method method = getMatchingMethod(cls, options, name, paramTypes);
    if (method == null) {
      throw new MethodNotExistException(cls, options, name, paramTypes);
    }
    try {
      return method.invoke(object, arguments);
    } catch (final Throwable e) {
      throw new InvokingMethodFailedException(cls, options, name, paramTypes, e);
    }
  }

  /**
   * Invokes a method whose parameter types match exactly the given argument
   * types.
   * <p>
   * This method delegates the method search to
   * {@link #getMethod(Class, String, Class[])}.
   *
   * @param cls
   *          The class on which to get the method.
   * @param options
   *          A bitwise combination of reflection options defined in the
   *          {@link Option} class. The default value could be
   *          {@link Option#DEFAULT}.
   * @param name
   *          The name of the method to be get.
   * @param object
   *          The object on which the method should be called. If the method is
   *          a static method, this argument could be {@code null}.
   * @param arguments
   *          The arguments used to invoke the method. If the method has no
   *          argument, this argument could be {@code null} or an empty
   *          array.
   * @return The value returned by the invoked method.
   * @throws ReflectionException
   *           if any error occurred.
   */
  public static Object invokeExactMethod(Class<?> cls, int options,
      String name, Object object, @Nullable Object[] arguments)
      throws ReflectionException {
    if (arguments == null) {
      return invokeExactMethod(cls, options, name, null, object, null);
    } else {
      final Class<?>[] paramTypes = new Class<?>[arguments.length];
      for (int i = 0; i < arguments.length; ++i) {
        paramTypes[i] = arguments[i].getClass();
      }
      return invokeExactMethod(cls, options, name, paramTypes, object, arguments);
    }
  }

  /**
   * Invokes a method whose parameter types match exactly the given parameter
   * types.
   * <p>
   * This method delegates the method search to
   * {@link #getMethod(Class, String, Class[])}.
   *
   * @param cls
   *          The class on which to get the method.
   * @param options
   *          A bitwise combination of reflection options defined in the
   *          {@link Option} class. The default value could be
   *          {@link Option#DEFAULT}.
   * @param name
   *          The name of the method to be get.
   * @param paramTypes
   *          The types of the parameters of the method to be get, which could
   *          be {@code null} or an empty array if the method has no
   *          argument.
   * @param object
   *          The object on which the method should be called. If the method is
   *          a static method, this argument could be {@code null}.
   * @param arguments
   *          The arguments used to invoke the method. If the method has no
   *          argument, this argument could be {@code null} or an empty
   *          array.
   * @return The value returned by the invoked method.
   * @throws ReflectionException
   *           if any error occurred.
   */
  public static Object invokeExactMethod(Class<?> cls, int options,
      String name, @Nullable Class<?>[] paramTypes, @Nullable Object object,
      @Nullable Object[] arguments) throws ReflectionException {
    if (paramTypes == null) {
      paramTypes = ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    final Method method = getMethod(cls, options, name, paramTypes);
    if (method == null) {
      throw new MethodNotExistException(cls, options, name, paramTypes);
    }
    try {
      return method.invoke(object, arguments);
    } catch (final Throwable e) {
      throw new InvokingMethodFailedException(cls, options, name, paramTypes, e);
    }
  }

}
