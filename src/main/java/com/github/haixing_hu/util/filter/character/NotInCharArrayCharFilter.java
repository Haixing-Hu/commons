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
 * A CharFilter that accept only the characters NOT in the specified string.
 *
 * @author Haixing Hu
 */
public final class NotInCharArrayCharFilter extends AbstractCharFilter {

  private char[] rejectChars;

  public NotInCharArrayCharFilter(final char[] rejectChars) {
    this.rejectChars = rejectChars;
  }

  public char[] getRejectChars() {
    return rejectChars;
  }

  public void setRejectChars(final char[] rejectChars) {
    this.rejectChars = rejectChars;
  }

  @Override
  public boolean accept(final int codePoint) {
    if ((codePoint >= Unicode.SUPPLEMENTARY_MIN) || (rejectChars == null)) {
      return true;
    }
    for (final char ch : rejectChars) {
      if (ch == codePoint) {
        return false;
      }
    }
    return true;
  }

}