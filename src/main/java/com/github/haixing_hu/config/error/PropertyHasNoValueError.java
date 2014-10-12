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
package com.github.haixing_hu.config.error;

/**
 * This error indicates that the property with a specified name does not have
 * any value.
 *
 * @author Haixing Hu
 */
public class PropertyHasNoValueError extends ConfigurationError {

  private static final long serialVersionUID = - 2617774805970541714L;

  private String propertyName;

  public PropertyHasNoValueError(String propertyName) {
    super("The specified property has no value: " + propertyName);
    this.propertyName = propertyName;
  }

  public String getPropertyName() {
    return propertyName;
  }
}
