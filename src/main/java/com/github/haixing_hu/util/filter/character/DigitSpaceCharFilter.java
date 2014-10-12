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

package com.github.haixing_hu.util.filter.character;

import javax.annotation.concurrent.Immutable;


/**
 * A CharFilter that accept only the digit characters or whitespace character.
 *
 * @author Haixing Hu
 */
@Immutable
public final class DigitSpaceCharFilter extends AbstractCharFilter {

  public static final DigitSpaceCharFilter INSTANCE = new DigitSpaceCharFilter();

  private DigitSpaceCharFilter() {
    // empty
  }

  @Override
  public boolean accept(final int codePoint) {
    return Character.isDigit(codePoint) || Character.isWhitespace(codePoint);
  }

}
