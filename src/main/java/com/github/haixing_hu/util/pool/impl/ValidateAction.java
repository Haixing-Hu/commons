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

package com.github.haixing_hu.util.pool.impl;

/**
 * The enumeration of validation actions.
 *
 * @author Haixing Hu
 */
public enum ValidateAction {

  /**
   * Indicates not to validate.
   */
  NOT_VALIDATE,

  /**
   * Indicates validate the object and throw an exception if the validation
   * failed.
   */
  THROWN_ON_FAILED,

  /**
   * Indicates validate the object and log the error if the validation failed.
   */
  LOG_ON_FAILED,

  /**
   * Indicates validate the object and keep silent if the validation failed.
   */
  SILENT_ON_FAILED,
}
