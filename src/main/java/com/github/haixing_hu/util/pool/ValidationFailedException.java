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

package com.github.haixing_hu.util.pool;

/**
 * Thrown to indicate a failure of the validation of an object in the pool.
 *
 * @author Haixing Hu
 */
public class ValidationFailedException extends PoolException {

  private static final long serialVersionUID = - 4385993753376804704L;

  public ValidationFailedException(final Object obj) {
    super("Validation of object failed: " + obj.toString());
  }

  public ValidationFailedException(final Object key, final Object value) {
    super("Validation of object failed: key = "
        + key.toString() + " value = " + value.toString());
  }
}
