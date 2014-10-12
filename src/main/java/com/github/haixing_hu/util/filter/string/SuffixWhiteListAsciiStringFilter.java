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
package com.github.haixing_hu.util.filter.string;


/**
 * A ASCII string filter with a suffix white list.
 *
 * Given a string, if it is an ASCII string and has a suffix in the white list
 * of a <code>SuffixBlackListAsciiStringFilter<code> object, it is accepted by
 * the <code>SuffixBlackListAsciiStringFilter<code> object; otherwise, it is
 * rejected by the <code>SuffixBlackListAsciiStringFilter<code> object.
 *
 * @author Haixing Hu
 */
public class SuffixWhiteListAsciiStringFilter extends SuffixListAsciiStringFilter {

  public SuffixWhiteListAsciiStringFilter() {
    super(true, false);
  }

  public SuffixWhiteListAsciiStringFilter(boolean caseInsensitive) {
    super(true, caseInsensitive);
  }

}
