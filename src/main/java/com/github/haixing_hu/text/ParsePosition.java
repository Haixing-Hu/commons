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

package com.github.haixing_hu.text;

import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

/**
 * A simple structure used to pass the argument between parsing functions.
 * <p>
 * This class extends the {@link java.text.ParsePosition}, adding the error
 * code to indicate the error during the parsing.
 * </p>
 * @author Haixing Hu
 */
public final class ParsePosition extends java.text.ParsePosition {

  private int errorCode;

  public ParsePosition() {
    super(0);
    errorCode = ErrorCode.NONE;
  }

  public ParsePosition(final int index) {
    super(index);
    errorCode = ErrorCode.NONE;
  }

  public void increase() {
    setIndex(getIndex() + 1);
  }

  public void increase(final int amount) {
    setIndex(getIndex() + amount);
  }

  public void decrease() {
    setIndex(getIndex() - 1);
  }

  public void decrease(final int amount) {
    setIndex(getIndex() - amount);
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(final int errorCode) {
    this.errorCode = errorCode;
  }

  public boolean success() {
    return errorCode == ErrorCode.NONE;
  }

  public boolean fail() {
    return errorCode != ErrorCode.NONE;
  }

  public void clearError() {
    errorCode = ErrorCode.NONE;
    setErrorIndex(-1);
  }

  public void reset() {
    errorCode = ErrorCode.NONE;
    setIndex(0);
    setErrorIndex(-1);
  }

  public void reset(final int index) {
    errorCode = ErrorCode.NONE;
    setIndex(index);
    setErrorIndex(-1);
  }

  @Override
  public int hashCode() {
    final int multiplier = 71;
    int code = 133;
    code = Hash.combine(code, multiplier, super.hashCode());
    code = Hash.combine(code, multiplier, errorCode);
    return code;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    } else if (obj == null) {
      return false;
    }
    if (! (obj instanceof ParsePosition)) {
      return false;
    }
    final ParsePosition other = (ParsePosition) obj;
    return (errorCode == other.errorCode)
    	 && (getIndex() == other.getIndex())
    	 && (getErrorIndex() == other.getErrorIndex());
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("current", getIndex())
               .append("errorCode", errorCode)
               .append("errorIndex", getErrorIndex())
               .append("errorMessage", ErrorCode.getMessage(errorCode))
               .toString();
  }
}
