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

package com.github.haixing_hu.util.config.error;

/**
 * Thrown to indicate an invalid property value.
 *
 * @author Haixing Hu
 */
public class InvalidPropertyValueError extends ConfigurationError {

  private static final long serialVersionUID = 7500607643203773303L;

  public InvalidPropertyValueError(final String name, final Object value) {
    super("The property value of '" + name + "' is invalid: " + value);
  }

}
