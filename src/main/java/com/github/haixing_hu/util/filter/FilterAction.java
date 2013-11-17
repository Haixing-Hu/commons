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
 * The constant values of filter actions returned by a filter.
 *
 * @author Haixing Hu
 */
public final class FilterAction {

  /**
   * No action suggestions to the chained filter containing this filter.
   */
  public static final int NONE     = 0x0000;

  /**
   * Suggests the chained filter containing this filter to continue to the next
   * chained filter.
   */
  public static final int CONTINUE = 0x0100;

  /**
   * Suggests the chained filter containing this filter to break the current
   * chained loop and return immediately.
   */
  public static final int BREAK    = 0x0200;

  /**
   * The bitwise mask of the filter action values.
   */
  public static final int MASK     = NONE | CONTINUE | BREAK;
}
