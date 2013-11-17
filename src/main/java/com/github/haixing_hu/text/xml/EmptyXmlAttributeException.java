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
 * Thrown when a required XML attribute is empty.
 *
 * @author Haixing Hu
 */
public class EmptyXmlAttributeException extends XmlException {

  private static final long serialVersionUID = - 4714885273534110209L;
  private String tagName;
  private String attributeName;

  public EmptyXmlAttributeException(String tagName, String attributeName) {
    super(formatMessage(tagName, attributeName));
    this.tagName = tagName;
    this.attributeName = attributeName;
  }

  public EmptyXmlAttributeException(String tagName, String attributeName, String message) {
    super(formatMessage(tagName, attributeName) + message);
    this.tagName = tagName;
    this.attributeName = attributeName;
  }

  public String getTagName() {
    return tagName;
  }

  public String getAttributeName() {
    return attributeName;
  }

  public static String formatMessage(String tagName, String attributeName) {
    return "The XML attribute '"
      + attributeName
      + "' of the node <"
      + tagName
      + "> should not be empty. ";
  }
}
