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

package com.github.haixing_hu.util;

import com.github.haixing_hu.io.exception.InvalidFormatException;


/**
 * Thrown to indicate a version mismatch error.
 *
 * @author Haixing Hu
 */
public class VersionMismatchException extends InvalidFormatException {

  private static final long serialVersionUID = 4296455318275520977L;

  private final Version expected;
  private final Version actual;

  public VersionMismatchException(final Version expected, final Version actual) {
    super();
    this.expected = expected;
    this.actual = actual;
  }

  public VersionMismatchException(final Version expected,
      final Version actual, final String message) {
    super(message);
    this.expected = expected;
    this.actual = actual;
  }

  public Version getExpected() {
    return expected;
  }

  public Version getActual() {
    return actual;
  }

  @Override
  public String getMessage() {
    final String message = super.getMessage();
    if (message != null) {
      return message;
    } else {
      return "Expect version " + expected.toString()
        + " but the actual version is " + actual.toString();
    }
  }
}
