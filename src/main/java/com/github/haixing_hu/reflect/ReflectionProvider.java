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


/**
 * Provides core reflection services.
 * <p>
 * NOTE: The implementation must be thread-safe.
 * </p>
 *
 * @author Haixing Hu
 */
public interface ReflectionProvider {

  /**
   * Creates a new instance of the specified type.
   * <p>
   * It is in the responsibility of the implementation how such an instance is
   * created.
   * </p>
   *
   * @param type
   *          The type to instantiate.
   * @return a new instance of this type
   */
  public Object newInstance(Class<?> type) throws ReflectionException;

  /**
   * Tests whether a specified class has a non-static, non-transient field with
   * the specified name.
   *
   * @param type
   *          The type where the field is defined.
   * @param fieldName
   *          The name of the field.
   * @return true if the specified class has a non-static, non-transient field
   *         with the specified name.
   */
  public boolean hasField(Class<?> type, String fieldName);

  /**
   * Gets a specified field defined in the specified class.
   *
   * @param type
   *          The type where the field is defined.
   * @param fieldName
   *          The name of the field to be get.
   * @return the field to be get.
   */
  public Field getField(Class<?> type, String fieldName);

  /**
   * Gets the type of a specified field of a specified object.
   *
   * @param type
   *          The type where the field is defined.
   * @param fieldName
   *          The name of the field to be get.
   * @return the type of the specified field.
   */
  public Class<?> getFieldType(Class<?> type, String fieldName);

  /**
   * Gets the value of the specified field of a specified object.
   *
   * @param type
   *          The type where the field is defined.
   * @param fieldName
   *          The name of the field.
   * @param object
   *          The object of the specified type.
   * @return the value of the specified field.
   */
  public Object getFieldValue(Class<?> type, String fieldName, Object object);

  /**
   * Sets the value of the specified field of a specified object.
   *
   * @param type
   *          The type where the field is defined.
   * @param fieldName
   *          The name of the field.
   * @param object
   *          The object of the specified type.
   * @param fieldValue
   *          The value to be set.
   */
  public void setFieldValue(Class<?> type, String fieldName, Object object,
      Object fieldValue);
}
