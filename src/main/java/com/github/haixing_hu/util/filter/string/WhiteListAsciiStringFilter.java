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
 * An ASCII string filter with a white list.
 *
 * Given a string, if it is an ASCII string and is in the white list of a
 * <code>WhiteListAsciiStringFilter<code> object, it is
 * accepted by the <code>WhiteListAsciiStringFilter<code> object; otherwise,
 * it is rejected by the <code>WhiteListAsciiStringFilter<code> object.
 *
 * @author Haixing Hu
 */
public class WhiteListAsciiStringFilter extends ListAsciiStringFilter {

  public WhiteListAsciiStringFilter() {
    super(true, false);
  }

  public WhiteListAsciiStringFilter(boolean caseInsensitive) {
    super(true, caseInsensitive);
  }
}
