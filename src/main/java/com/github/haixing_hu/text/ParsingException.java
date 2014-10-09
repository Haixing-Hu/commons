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
 * A extension of the {@link java.text.ParseException} which could be supplied
 * with an additional error message.
 *
 * @author Haixing Hu
 */
public class ParsingException extends ParseException {

  private static final long serialVersionUID = -3592758755666565544L;

  private final String text;
  private final String message;

  public ParsingException(final String text, final int errorOffset) {
    super(text, errorOffset);
    this.text = text;
    message = null;
  }

  public ParsingException(final String text, final int errorOffset,
      final String message) {
    super(text, errorOffset);
    this.text = text;
    this.message = message;
  }

  @Override
  public String getMessage() {
    String result = "An error occurs at the offset "
      + getErrorOffset()
      + " while parsing the text '" + text + "'";
    if (message != null) {
      result += ": " + message;
    }
    return result;
  }
}
