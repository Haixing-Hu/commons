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

import javax.annotation.concurrent.NotThreadSafe;

/**
 * A string filter with a white list.
 *
 * Given a string, if it is in the white list of a <code>WhiteListStringFilter<code>
 * object, it is accepted by the <code>WhiteListStringFilter<code> object; otherwise,
 * it is rejected by the <code>WhiteListStringFilter<code> object.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class WhiteListStringFilter extends ListStringFilter {

  public WhiteListStringFilter() {
    super(true, false);
  }

  public WhiteListStringFilter(boolean caseInsensitive) {
    super(true, caseInsensitive);
  }
}
