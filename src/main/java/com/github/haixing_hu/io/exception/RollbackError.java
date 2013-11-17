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

package com.github.haixing_hu.io.exception;

/**
 * Thrown to indicate that an error or exception is occurred during the roll
 * back operation.
 *
 * @author Haixing Hu
 */
public class RollbackError extends Error {

  private static final long serialVersionUID = 8638885014873271424L;

  private static final String MESSAGE = "An error occurrs during the roll back operation. The data may be lost.";

  public RollbackError() {
    super(MESSAGE);
  }

  public RollbackError(final String message) {
    super(message);
  }

  public RollbackError(final Throwable cause) {
    super(MESSAGE, cause);
  }

  public RollbackError(final String message, final Throwable cause) {
    super(message, cause);
  }
}
