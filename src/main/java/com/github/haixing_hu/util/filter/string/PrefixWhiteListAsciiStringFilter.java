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

package com.github.haixing_hu.util.filter.string;


/**
 * A ASCII string filter with a prefix white list.
 *
 * Given a string, if it is an ASCII string and has a prefix in the white list
 * of a {@code PrefixWhiteListAsciiStringFilter} object, it is accepted by
 * the {@code PrefixWhiteListAsciiStringFilter} object; otherwise, it is
 * rejected by the {@code PrefixWhiteListAsciiStringFilter} object.
 *
 * @author Haixing Hu
 */
public class PrefixWhiteListAsciiStringFilter extends PrefixListAsciiStringFilter {

  public PrefixWhiteListAsciiStringFilter() {
    super(true, false);
  }

  public PrefixWhiteListAsciiStringFilter(boolean caseInsensitive) {
    super(true, caseInsensitive);
  }
}
