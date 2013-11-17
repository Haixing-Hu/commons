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

package com.github.haixing_hu.beans;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.haixing_hu.lang.ArrayUtils;
import com.github.haixing_hu.reflect.ReflectionException;

/**
 * Provides functions for reflection operations.
 *
 * @author Haixing Hu
 */
public class ReflectionUtils {

  /**
   * Cache of constructors for each class. Pins the classes so they can't be
   * garbage collected until ReflectionUtils can be collected.
   */
  private static final Map<Class<?>, Constructor<?>> CONSTRUCTOR_CACHE =
    new ConcurrentHashMap<Class<?>, Constructor<?>>();

  /**
   * Create an object for the given class.
   *
   * The function will suppress the Java language access checking for the
   * constructor of the object.
   *
   * @param theClass
   *          class of which an object is created.
   * @return a new object of the given class.
   * @throws ReflectionException
   *        if any exception thrown during the construction of the object.
   */
  @SuppressWarnings("unchecked")
  public static <T> T newInstance(final Class<T> theClass)
      throws ReflectionException {
    T result;
    try {
      Constructor<T> ctor = (Constructor<T>) CONSTRUCTOR_CACHE.get(theClass);
      if (ctor == null) {
        ctor = theClass.getDeclaredConstructor(ArrayUtils.EMPTY_CLASS_ARRAY);
        ctor.setAccessible(true);
        CONSTRUCTOR_CACHE.put(theClass, ctor);
      }
      result = ctor.newInstance();
    } catch (final Exception e) {
      throw new ReflectionException(e);
    }
    return result;
  }
}
