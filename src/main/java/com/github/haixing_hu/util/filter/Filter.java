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

package com.github.haixing_hu.util.filter;

/**
 * A filter interface provides a function to filter objects of a specified type.
 *
 * @author Haixing Hu
 */
public interface Filter<T> {

  /**
   * Tests whether to accept the specified object.
   *
   * @param t
   *    the object to be test.
   * @return
   *    true if the object <code>t</code> is accepted by this filter; false otherwise.
   */
  public boolean accept(T t);
}
