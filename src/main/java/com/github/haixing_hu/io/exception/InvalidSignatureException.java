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
package com.github.haixing_hu.io.exception;

/**
 * Thrown to indicate the version signature of a file is invalid.
 *
 * @author Haixing Hu
 */
public class InvalidSignatureException extends InvalidFormatException {

  private static final long serialVersionUID = 3066517065925815105L;

  public InvalidSignatureException() {
    super("The version signature of the file is invalid.");
  }

  public InvalidSignatureException(final String message) {
    super(message);
  }
}
