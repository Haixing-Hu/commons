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
package com.github.haixing_hu.text.xml;

/**
 * Thrown to indicate an error occurred while creating the XML document builder.
 *
 * @author Haixing Hu
 */
public class CreateXmlBuilderException extends XmlException {

  private static final long serialVersionUID = 7422489284067686642L;

  private static final String MESSAGE_CREATE_BUILDER_FAILED =
    "An error occurs while creating the XML document builder. ";

  public CreateXmlBuilderException() {
    super(MESSAGE_CREATE_BUILDER_FAILED);
  }

  public CreateXmlBuilderException(String message) {
    super(message);
  }

  public CreateXmlBuilderException(Throwable e) {
    super(MESSAGE_CREATE_BUILDER_FAILED + e, e);
  }

  public CreateXmlBuilderException(String message, Throwable e) {
    super(message, e);
  }
}
