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
 * Thrown when an error occurs during the type conversion.
 *
 * @author Haixing Hu
 */
public class TypeConvertException extends RuntimeException {

  private static final long serialVersionUID = 7806278638359973347L;

  private final String sourceTypeName;
  private final String destinationTypeName;

  public TypeConvertException(final String sourceTypeName,
      final String destinationTypeName) {
    super(buildMessage(sourceTypeName, destinationTypeName));
    this.sourceTypeName = sourceTypeName;
    this.destinationTypeName = destinationTypeName;
  }

  public TypeConvertException(final String sourceType,
      final String destinationType, final String message) {
    super(buildMessage(sourceType, destinationType) + " : " + message);
    this.sourceTypeName = sourceType;
    this.destinationTypeName = destinationType;
  }

  public TypeConvertException(final Type sourceType,
      final Type destinationType) {
    super(buildMessage(sourceType.toString(), destinationType.toString()));
    this.sourceTypeName = sourceType.toString();
    this.destinationTypeName = destinationType.toString();
  }

  public TypeConvertException(final Type sourceType,
      final Type destinationType, final String message) {
    super(buildMessage(sourceType.toString(),
        destinationType.toString()) + " : " + message);
    this.sourceTypeName = sourceType.toString();
    this.destinationTypeName = destinationType.toString();
  }

  public TypeConvertException(final Class<?> sourceType,
      final Class<?> destinationType) {
    super(buildMessage(sourceType.getName(), destinationType.getName()));
    this.sourceTypeName = sourceType.getName();
    this.destinationTypeName = destinationType.getName();
  }

  public TypeConvertException(final Class<?> sourceType,
      final Class<?> destinationType, final String message) {
    super(buildMessage(sourceType.getName(),
        destinationType.getName()) + " : " + message);
    this.sourceTypeName = sourceType.getName();
    this.destinationTypeName = destinationType.getName();
  }

  private static String buildMessage(final String sourceTypeName,
      final String destinationTypeName) {
    return "Can not convert from type " + sourceTypeName
        + " to type " + destinationTypeName;
  }

  public String getSourceTypeName() {
    return sourceTypeName;
  }

  public String getDestinationTypeName() {
    return destinationTypeName;
  }

}
