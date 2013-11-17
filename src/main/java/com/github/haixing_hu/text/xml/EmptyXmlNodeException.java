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
 * Thrown when a XML node is empty.
 *
 * @author Haixing Hu
 */
public class EmptyXmlNodeException extends XmlException {

  private static final long serialVersionUID = - 1959147058166324842L;

  private String tagName;

  public EmptyXmlNodeException(String tagName) {
    super(formatMessage(tagName));
    this.tagName = tagName;
  }

  public EmptyXmlNodeException(String tagName, String message) {
    super(formatMessage(tagName) + message);
    this.tagName = tagName;
  }

  public String getTagName() {
    return tagName;
  }

  public static String formatMessage(String tagName) {
    return "The node <" + tagName + "> should not be empty. ";
  }
}
