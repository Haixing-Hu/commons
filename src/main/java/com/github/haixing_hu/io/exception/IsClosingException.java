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

import java.io.IOException;

/**
 * Thrown to indicate a file or a device is being closed.
 *
 * @author Haixing Hu
 */
public class IsClosingException extends IOException {

  private static final long serialVersionUID = - 4707122216334525975L;

  public IsClosingException() {
    super("The file, directory or device is being closed.");
  }

  public IsClosingException(final String message) {
    super(message);
  }
}
