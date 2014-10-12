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



/**
 * A CharFilter that accept only the characters in the specified string.
 *
 * @author Haixing Hu
 */
public final class InStringCharFilter extends AbstractCharFilter {

  private String acceptChars;

  public InStringCharFilter(final String acceptChars) {
    this.acceptChars = acceptChars;
  }

  public String getAcceptChars() {
    return acceptChars;
  }

  public void setAcceptChars(final String acceptChars) {
    this.acceptChars = acceptChars;
  }

  @Override
  public boolean accept(final int codePoint) {
    return((acceptChars != null) && (acceptChars.indexOf(codePoint) >= 0));
  }

}
