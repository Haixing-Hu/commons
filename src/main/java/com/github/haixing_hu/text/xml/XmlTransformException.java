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

package com.github.haixing_hu.text.xml;

/**
 * Thrown to indicate an error occurred while transforming the XML.
 *
 * @author Haixing Hu
 */
public class XmlTransformException extends XmlException {

  private static final long serialVersionUID = 1582261306326363474L;

  private static final String MESSAGE_TRANSFORM_FAILED = "Transforming the XML failed. ";

  public XmlTransformException() {
    super(MESSAGE_TRANSFORM_FAILED);
  }

  public XmlTransformException(String message) {
    super(message);
  }

  public XmlTransformException(Throwable t) {
    super(MESSAGE_TRANSFORM_FAILED + t.getMessage(), t);
  }

  public XmlTransformException(String message, Throwable t) {
    super(message, t);
  }
}
