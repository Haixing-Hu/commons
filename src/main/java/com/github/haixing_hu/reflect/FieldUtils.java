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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * Provides utilities for working with fields by reflection.
 * <p>
 * The ability is provided to break the scoping restrictions coded by the
 * programmer. This can allow fields to be changed that shouldn't be. This
 * facility should be used with care.
 *
 * @author Haixing Hu
 * @since 1.0.0
 */
@ThreadSafe
public class FieldUtils {

  private final static Map<Class<?>, List<MemberInfo>> fieldCache =
      new HashMap<Class<?>, List<MemberInfo>>();

  private static void buildFieldCache(Class<?> cls) {
    if (fieldCache.containsKey(cls)) {
      return;
    }
    final List<MemberInfo> fields = new ArrayList<MemberInfo>();
    final Class<?> superCls = cls.getSuperclass();
    if (superCls != null) {
      buildFieldCache(superCls);    // recursively
      final List<MemberInfo> superFields = fieldCache.get(superCls);
      for (final MemberInfo m : superFields) {
        fields.add(new MemberInfo(m.member, m.depth + 1));
      }
    }
    for (final Class<?> superInterface : cls.getInterfaces()) {
      buildFieldCache(superInterface);    // recursively
      final List<MemberInfo> superFields = fieldCache.get(superInterface);
      for (final MemberInfo m : superFields) {
        fields.add(new MemberInfo(m.member, m.depth + 1));
      }
    }
    for (final Field field : cls.getDeclaredFields()) {
      field.setAccessible(true);  // suppress access checks
      fields.add(new MemberInfo(field, 0));
    }
    Collections.sort(fields);
    fieldCache.put(cls, fields);
  }

  private static List<MemberInfo> getAllFields(Class<?> cls) {
    requireNonNull("cls", cls);
    synchronized (fieldCache) {
      buildFieldCache(cls);
      return fieldCache.get(cls);
    }
  }

  /**
   * Gets all fields on a class.
   *
   * @param cls
   *          The class on which to get the fields.
   * @param options
   *          A bitwise combination of reflection options defined in the
   *          {@link Option} class. The default value could be
   *          {@link Option#DEFAULT}.
   * @return the array of all specified fields; or an empty array if no such
   *         field.
   * @throws ReflectionException
   *           if any error occurred.
   */
  public static Field[] getAllFields(Class<?> cls, int options)
      throws ReflectionException {
    final List<MemberInfo> members = getAllFields(cls);
    final List<Field> result = new ArrayList<Field>();
    for (final MemberInfo m : members) {
      if (Option.satisfy(cls, m.member, options)) {
        result.add((Field) m.member);
      }
    }
    return result.toArray(new Field[result.size()]);
  }

  /**
   * Gets a field with a specified name on a class.
   * <p>
   * NOTE: if the <code>options</code> argument contains {@link Option#ANCESTOR}
   * , and there is more than one field with the specified name declared in the
   * specified class or its ancestor class or its ancestor interfaces, the
   * function will try to return the field with the shallower depth; if there
   * are more than one field has the specified name in the same depth, the
   * function will throw an {@link AmbiguousMemberException}.
   *
   * @param cls
   *          The class on which to get the field.
   * @param options
   *          A bitwise combination of reflection options defined in the
   *          {@link Option} class. The default value could be
   *          {@link Option#DEFAULT}.
   * @param name
   *          The name of the field to be get.
   * @return the specified field, or <code>null</code> if no such field.
   * @throws ReflectionException
   *           if any error occurred.
   */
  public static Field getField(Class<?> cls, int options, String name)
      throws ReflectionException {
    requireNonNull("name", name);
    final List<MemberInfo> members = getAllFields(cls);
    MemberInfo result = null;
    boolean ambiguous = false;
    for (final MemberInfo m : members) {
      if (Option.satisfy(cls, m.member, options)
          && name.equals(m.member.getName())) {
        if (result == null) {
          result = m;
          ambiguous = false;
        } else if (result.depth > m.depth) {
          result = m;       // keep the field with shallower depth
          ambiguous = false;
        } else if (result.depth == m.depth) {
          ambiguous = true; // more than one field with the same name in the same depth
        }
      }
    }
    if (ambiguous) {
      throw new AmbiguousMemberException(cls, name);
    }
    return (Field) result.member;
  }

  /**
   * Gets the class of a field on a specified class.
   * <p>
   * NOTE: if the <code>options</code> argument contains {@link Option#ANCESTOR}
   * , and there is more than one field with the specified name declared in the
   * specified class or its ancestor class or its ancestor interfaces, the
   * function will try to read the field with the shallower depth; if there are
   * more than one field has the specified name in the same depth, the function
   * will throw an {@link AmbiguousMemberException}.
   *
   * @param cls
   *          The class on which to get the field type.
   * @param options
   *          A bitwise combination of reflection options defined in the
   *          {@link Option} class. The default value could be
   *          {@link Option#DEFAULT}.
   * @param name
   *          The name of the field to be get.
   * @return the class of the specified field, or <code>null</code> if no such
   *         field.
   * @throws ReflectionException
   *           if any error occurred.
   */
  public static Class<?> getFieldType(Class<?> cls, int options, String name)
      throws ReflectionException {
    final Field field = getField(cls, options, name);
    if (field == null) {
      return null;
    } else {
      return field.getClass();
    }
  }

  /**
   * Gets the value of a field on the specified object.
   * <p>
   * The value is automatically wrapped in an object if it has a primitive type.
   * <p>
   * NOTE: if the <code>options</code> argument contains {@link Option#ANCESTOR}
   * , and there is more than one field with the specified name declared in the
   * specified class or its ancestor class or its ancestor interfaces, the
   * function will try to read the field with the shallower depth; if there are
   * more than one field has the specified name in the same depth, the function
   * will throw an {@link AmbiguousMemberException}.
   *
   * @param cls
   *          The class on which to get the field value.
   * @param options
   *          A bitwise combination of reflection options defined in the
   *          {@link Option} class. The default value could be
   *          {@link Option#DEFAULT}.
   * @param name
   *          The name of the field.
   * @param object
   *          The object of the specified class. If the field whose value is to
   *          be get is a static field, this argument may be <code>null</code>.
   * @return the value of the specified field.
   * @throws ReflectionException
   *           if any error occurred.
   */
  public static Object getFieldValue(Class<?> cls, int options, String name,
      @Nullable Object object) throws ReflectionException {
    final Field field = getField(cls, options, name);
    if (field == null) {
      throw new FieldNotExistException(cls, options, name);
    }
    try {
      return field.get(object);
    } catch (final IllegalArgumentException e) {
      throw new ReflectionException(e);
    } catch (final IllegalAccessException e) {
      throw new ReflectionException(e);
    }
  }

  /**
   * Sets a field on the specified object to the specified new value.
   * <p>
   * The new value is automatically unwrapped if the underlying field has a
   * primitive type.
   * <p>
   * NOTE: if the <code>options</code> argument contains {@link Option#ANCESTOR}
   * , and there is more than one field with the specified name declared in the
   * specified class or its ancestor class or its ancestor interfaces, the
   * function will try to write the field with the shallower depth; if there are
   * more than one field has the specified name in the same depth, the function
   * will throw an {@link AmbiguousMemberException}.
   *
   * @param cls
   *          The class on which to set the field value.
   * @param options
   *          A bitwise combination of reflection options defined in the
   *          {@link Option} class. The default value could be
   *          {@link Option#DEFAULT}.
   * @param name
   *          The name of the field.
   * @param object
   *          The object of the specified class.
   * @param value
   *          The value to be set.
   * @throws ReflectionException
   *           if any error occurred.
   */
  public static void setFieldValue(Class<?> cls, int options, String name,
      Object object, Object value) throws ReflectionException {
    requireNonNull("object", object);
    final Field field = getField(cls, options, name);
    if (field == null) {
      throw new FieldNotExistException(cls, options, name);
    }
    try {
      field.set(object, value);
    } catch (final IllegalArgumentException e) {
      throw new ReflectionException(e);
    } catch (final IllegalAccessException e) {
      throw new ReflectionException(e);
    }
  }

}
