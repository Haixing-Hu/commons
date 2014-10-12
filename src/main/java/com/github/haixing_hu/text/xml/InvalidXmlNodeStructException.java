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

import org.w3c.dom.Node;



/**
 * Thrown when an XML node has invalid structure.
 *
 * @author Haixing Hu
 */
public class InvalidXmlNodeStructException extends XmlException {

  private static final long serialVersionUID = 5339788239653270934L;

  private final Node node;

  public InvalidXmlNodeStructException(final Node node) {
    super(formatMessage(node));
    this.node = node;
  }

  public InvalidXmlNodeStructException(final Node node, final String message) {
    super(formatMessage(node) + message);
    this.node = node;
  }

  public Node getNode() {
    return node;
  }

  public static String formatMessage(final Node node) {
    return "Invalid XML node structure. The actual XML node is:\n"
        + XmlUtils.toString(node);
  }

}
