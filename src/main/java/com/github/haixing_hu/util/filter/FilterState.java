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
package com.github.haixing_hu.util.filter;

/**
 * The constant values of filter results returned by a filter.
 *
 * @author Haixing Hu
 */
public final class FilterState {

  /**
   * Means whether the object is accepted or rejected is unknown.
   */
  public static final int UNKNOWN = 0x0000;

  /**
   * Means the object is accepted.
   */
  public static final int ACCEPT  = 0x0001;

  /**
   * Means the object is rejected.
   */
  public static final int REJECT  = 0x0002;

  /**
   * The bitwise mask of the filter result values.
   */
  public static final int MASK    = UNKNOWN | ACCEPT | REJECT;

}
