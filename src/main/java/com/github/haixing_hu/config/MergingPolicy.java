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
package com.github.haixing_hu.config;

/**
 * The enumeration of the policies used when merging configurations.
 *
 * @author Haixing Hu
 */
public enum MergingPolicy {

  /**
   * Skip all the existing property, no matter whether it is final or not.
   */
  SKIP,

  /**
   * Union the values of the existing property, or skip it if it is final. If the
   * existing property has a different type and it is not final, overwrite it with
   * new value.
   */
  UNION,

  /**
   * Overwrite the existing property, or skip it if it is final.
   */
  OVERWRITE,
}
