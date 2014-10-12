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
package com.github.haixing_hu.reflect;

/**
 * Thrown to indicate an exception occurs during a reflection operation.
 *
 * @author Haixing Hu
 */
public class ReflectionException extends RuntimeException {

  private static final long serialVersionUID = -4383352207393863063L;

  private static final String DEFAULT_MESSAGE =
    "An exception occurs during the reflection operation.";

  public ReflectionException() {
    super(DEFAULT_MESSAGE);
  }

  public ReflectionException(final String message) {
    super(message);
  }

  public ReflectionException(final Throwable cause) {
    super(DEFAULT_MESSAGE, cause);
  }

  public ReflectionException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
