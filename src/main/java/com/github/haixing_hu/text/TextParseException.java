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

import java.text.ParseException;

/**
 * Thrown to indicate a error occurs during parsing the text.
 *
 * This class extends {@link ParseException}, except it provides more detailed
 * information about the parsing and it will automatically build the error
 * message from those information.
 *
 * @author Haixing Hu
 */
public class TextParseException extends ParseException {

  private static final long serialVersionUID = 1897366428017592956L;

  private final String text;
  private final String reason;
  private final int startIndex;
  private final int endIndex;

  public TextParseException(final CharSequence text, final int errorCode) {
    this(text, 0, text.length(), ErrorCode.getMessage(errorCode), 0);
  }

  public TextParseException(final CharSequence text, final int errorCode,
      final int errorIndex) {
    this(text, 0, text.length(), ErrorCode.getMessage(errorCode), errorIndex);
  }

  public TextParseException(final CharSequence text, final int startIndex,
      final int endIndex, final int errorCode, final int errorIndex) {
    this(text, startIndex, endIndex, ErrorCode.getMessage(errorCode),
        errorIndex);
  }

  public TextParseException(final CharSequence text, final String reason) {
    this(text, 0, text.length(), reason, 0);
  }

  public TextParseException(final CharSequence text, final String reason,
      final int errorIndex) {
    this(text, 0, text.length(), reason, errorIndex);
  }

  public TextParseException(final CharSequence text, final int startIndex,
      final int endIndex, final String reason, final int errorIndex) {
    super(buildErrorMessage(text, startIndex, endIndex, reason), errorIndex);
    this.text = text.toString();
    this.reason = reason;
    this.startIndex = startIndex;
    this.endIndex = endIndex;
  }

  public TextParseException(final CharSequence text, final ParsePosition pos) {
    this(text, 0, text.length(), pos);
  }

  public TextParseException(final CharSequence text, final int startIndex,
      final ParsePosition pos) {
    this(text, startIndex, text.length(), pos);
  }

  public TextParseException(final CharSequence text, final int startIndex,
      final int endIndex, final ParsePosition pos) {
    super(buildErrorMessage(text, startIndex, endIndex,
        ErrorCode.getMessage(pos.getErrorCode())), pos.getErrorIndex());
    this.text = text.toString();
    this.reason = ErrorCode.getMessage(pos.getErrorCode());
    this.startIndex = startIndex;
    this.endIndex = endIndex;
  }

  public String getText() {
    return text;
  }

  public String getReason() {
    return reason;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public int getEndIndex() {
    return endIndex;
  }

  private static String buildErrorMessage(final CharSequence text,
      final int startIndex, final int endIndex, final String reason) {
    final StringBuilder builder = new StringBuilder();
    if ((startIndex == 0) && (endIndex == text.length())) {
      builder.append("Error while parsing the string '")
             .append(text)
             .append("': ")
             .append(reason);
    } else {
      builder.append("Error while parsing the sub-string from ")
             .append(startIndex)
             .append(" to ")
             .append(endIndex)
             .append(" of '")
             .append(text)
             .append("': ")
             .append(reason);
    }
    return builder.toString();
  }
}
