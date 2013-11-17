/******************************************************************************
 * Copyright (c) 2009-2010 Ascent Dimension, Inc. All rights reserved.
 ******************************************************************************/

package com.github.haixing_hu.text.xml;

/**
 * Thrown when an XML node has invalid content.
 *
 * @author Haixing Hu
 */
public class InvalidXmlNodeContentException extends XmlException {

  private static final long serialVersionUID = 2084890707891169214L;

  private final String tagName;
  private final String content;

  public InvalidXmlNodeContentException(final String tagName,
      final String content) {
    super(formatMessage(tagName, content));
    this.tagName = tagName;
    this.content = content;
  }

  public InvalidXmlNodeContentException(final String tagName,
      final String content, final Throwable cause) {
    super(formatMessage(tagName, content), cause);
    this.tagName = tagName;
    this.content = content;
  }

  public InvalidXmlNodeContentException(final String tagName,
      final String content, final String message) {
    super(formatMessage(tagName, content) + message);
    this.tagName = tagName;
    this.content = content;
  }

  public InvalidXmlNodeContentException(final String tagName,
      final String content, final String message, final Throwable cause) {
    super(formatMessage(tagName, content) + message, cause);
    this.tagName = tagName;
    this.content = content;
  }

  public String getTagName() {
    return tagName;
  }

  public String getContent() {
    return content;
  }

  public static String formatMessage(final String tagName, final String content) {
    return "Invalid content of node <" + tagName + ">: \"" + content + "\". ";
  }
}
