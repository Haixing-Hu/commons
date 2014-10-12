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
package com.github.haixing_hu.text;

import com.github.haixing_hu.lang.CharUtils;
import com.github.haixing_hu.lang.Cloneable;
import com.github.haixing_hu.lang.Equality;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

import static com.github.haixing_hu.lang.Argument.requireLengthAtLeast;

/**
 * A {@link BooleanFormatSymbols} object is used to store the symbols for
 * formatting and parsing boolean values.
 *
 * @author Haixing Hu
 */
public final class BooleanFormatSymbols implements Cloneable<BooleanFormatSymbols> {

  public static final char[] DEFAULT_BINARY_DIGITS  =  CharUtils.LOWERCASE_DIGITS;

  public static final String DEFAULT_TRUE_NAME = "true";

  public static final String DEFAULT_FALSE_NAME = "false";

  private char[] binaryDigits;
  private String trueName;
  private String falseName;

  public BooleanFormatSymbols() {
    binaryDigits = DEFAULT_BINARY_DIGITS;
    trueName = DEFAULT_TRUE_NAME;
    falseName = DEFAULT_FALSE_NAME;
  }

  public void reset() {
    binaryDigits = DEFAULT_BINARY_DIGITS;
    trueName = DEFAULT_TRUE_NAME;
    falseName = DEFAULT_FALSE_NAME;
  }

  public char[] getBinaryDigits() {
    return binaryDigits;
  }

  public void setBinaryDigits(final char[] binaryDigits) {
    this.binaryDigits = requireLengthAtLeast("binaryDigits", binaryDigits, 2);
  }

  public String getTrueName() {
    return trueName;
  }

  public void setTrueName(final String trueName) {
    this.trueName = requireLengthAtLeast("trueName", trueName, 1);
  }

  public String getFalseName() {
    return falseName;
  }

  public void setFalseName(final String falseName) {
    this.falseName = requireLengthAtLeast("falseName", falseName, 1);
  }

  public void assign(final BooleanFormatSymbols that) {
    if (this == that) {
      return;
    }
    this.binaryDigits = that.binaryDigits;
    this.trueName = that.trueName;
    this.falseName = that.falseName;
  }

  @Override
  public BooleanFormatSymbols clone() {
    final BooleanFormatSymbols result = new BooleanFormatSymbols();
    result.assign(this);
    return result;
  }

  @Override
  public int hashCode() {
    final int multiplier = 3;
    int code = 17;
    code = Hash.combine(code, multiplier, binaryDigits);
    code = Hash.combine(code, multiplier, trueName);
    code = Hash.combine(code, multiplier, falseName);
    return code;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final BooleanFormatSymbols other = (BooleanFormatSymbols) obj;
    return Equality.equals(binaryDigits, other.binaryDigits)
        && trueName.equals(other.trueName)
        && falseName.equals(other.falseName);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("binaryDigits", binaryDigits)
               .append("trueName", trueName)
               .append("falseName", falseName)
               .toString();
  }
}
