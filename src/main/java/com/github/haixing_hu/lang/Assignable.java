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

package com.github.haixing_hu.lang;

/**
 * This interface impose a assignment and clone operations of a class T.
 *
 * @author Haixing Hu
 */
public interface Assignable<T> extends Cloneable<T> {

  /**
   * Assigns another object to this object.
   * <p>
   * The function will clone all fields of the other object into the fields of
   * this object.
   *
   * @param other
   *          the other object.
   */
  public void assign(T other);
}
