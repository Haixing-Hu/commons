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

package com.github.haixing_hu.lang;

/**
 * Thrown when a type mismatch error occurs.
 *
 * @author Haixing Hu
 */
public class TypeMismatchException extends RuntimeException {

  private static final long serialVersionUID = 779519202297853145L;

  private final String expectedTypeName;
  private final String actualTypeName;

  public TypeMismatchException(final String expectedTypeName,
      final String actualTypeName) {
    super(buildMessage(expectedTypeName, actualTypeName));
    this.expectedTypeName = expectedTypeName;
    this.actualTypeName = actualTypeName;
  }

  public TypeMismatchException(final String expectedTypeName,
      final String actualTypeName, final String message) {
    super(buildMessage(expectedTypeName, actualTypeName) + message);
    this.expectedTypeName = expectedTypeName;
    this.actualTypeName = actualTypeName;
  }

  public TypeMismatchException(final Class<?> expectedType,
      final Class<?> actualType) {
    super(buildMessage(expectedType.getName(), actualType.getName()));
    this.expectedTypeName = expectedType.getName();
    this.actualTypeName = actualType.getName();
  }

  public TypeMismatchException(final Class<?> expectedType,
      final Class<?> actualType, final String message) {
    super(buildMessage(expectedType.getName(), actualType.getName()) + message);
    this.expectedTypeName = expectedType.getName();
    this.actualTypeName = actualType.getName();
  }

  public TypeMismatchException(final Type expectedType,
      final Type actualType) {
    super(buildMessage(expectedType.name(), actualType.name()));
    this.expectedTypeName = expectedType.name();
    this.actualTypeName = actualType.name();
  }

  public TypeMismatchException(final Type expectedType,
      final Type actualType, final String message) {
    super(buildMessage(expectedType.name(), actualType.name()) + message);
    this.expectedTypeName = expectedType.name();
    this.actualTypeName = actualType.name();
  }

  public String getExpectedTypeName() {
    return expectedTypeName;
  }

  public String getActualTypeName() {
    return actualTypeName;
  }

  private static String buildMessage(final String expectedTypeName,
      final String actualTypeName) {
    return "Type mismatch: the expected type is " + expectedTypeName
    + " but the actual type is " + actualTypeName + ". ";
  }

}
