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

import com.github.haixing_hu.text.Unicode;

/**
 * A CharFilter that accept only the characters in the specified character array.
 *
 * @author Haixing Hu
 */
public final class InCharArrayCharFilter extends AbstractCharFilter {

  private char[] charArray;

  public InCharArrayCharFilter() {
    charArray = null;
  }

  public InCharArrayCharFilter(final char[] charArray) {
    this.charArray = charArray;
  }

  public char[] getCharArray() {
    return charArray;
  }

  public void setCharArray(final char[] charArray) {
    this.charArray = charArray;
  }

  @Override
  public boolean accept(final int codePoint) {
    if ((codePoint >= Unicode.SUPPLEMENTARY_MIN) || (charArray == null)) {
      return false;
    }
    for (final char ch : charArray) {
      if (ch == codePoint) {
        return true;
      }
    }
    return false;
  }

}
