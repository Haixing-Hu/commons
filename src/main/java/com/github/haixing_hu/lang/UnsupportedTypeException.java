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
 * Thrown when a type is not supported.
 *
 * @author Haixing Hu
 */
public class UnsupportedTypeException extends RuntimeException {

  private static final long serialVersionUID = 67589732551790772L;

  private String typeName;

  public UnsupportedTypeException(final String typeName) {
    super();
    this.typeName = typeName;
  }

  public UnsupportedTypeException(final Type type) {
    super();
    this.typeName = type.name();
  }

  public UnsupportedTypeException(final String typeName, final String message) {
    super(message);
    this.typeName = typeName;
  }

  public UnsupportedTypeException(final Type type, final String message) {
    super(message);
    this.typeName = type.name();
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(final String typeName) {
    this.typeName = typeName;
  }

  @Override
  public String getMessage() {
    String message = super.getMessage();
    if ((message == null) || (message.length() == 0)) {
      message = "The type is not supported: ";
    }
    if (typeName != null) {
      message += " type = " + typeName;
    }
    return message;
  }

}
