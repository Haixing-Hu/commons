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

package com.github.haixing_hu.config.error;

/**
 * This error indicates that the property with a specified name contains a list of values.
 *
 * @author Haixing Hu
 */
public class PropertyIsListError extends ConfigurationError {

  private static final long serialVersionUID = 1286137143396514506L;

  private String propertyName;

  public PropertyIsListError(String propertyName) {
    super("The specified property contains a list of values: " + propertyName);
    this.propertyName = propertyName;
  }

  public String getPropertyName() {
    return propertyName;
  }

}
