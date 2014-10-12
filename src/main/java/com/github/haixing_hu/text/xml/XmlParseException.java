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

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;

import com.github.haixing_hu.net.Url;

/**
 * Thrown to indicate an error occurred while parsing the XML.
 *
 * @author Haixing Hu
 */
public class XmlParseException extends XmlException {

  private static final long serialVersionUID = - 9037334417781578122L;

  private static final String MESSAGE_PARSING_FAILED = "An error occurs while parsing the XML. ";

  public XmlParseException() {
    super(MESSAGE_PARSING_FAILED);
  }

  public XmlParseException(final String message) {
    super(message);
  }

  public XmlParseException(final File file) {
    super(MESSAGE_PARSING_FAILED + file);
  }

  public XmlParseException(final Url url) {
    super(MESSAGE_PARSING_FAILED + url);
  }

  public XmlParseException(final URL url) {
    super(MESSAGE_PARSING_FAILED + url);
  }

  public XmlParseException(final URI uri) {
    super(MESSAGE_PARSING_FAILED + uri);
  }

  public XmlParseException(final InputStream in) {
    super(MESSAGE_PARSING_FAILED + in);
  }

  public XmlParseException(final Throwable e) {
    super(MESSAGE_PARSING_FAILED + e, e);
  }

  public XmlParseException(final String message, final Throwable e) {
    super(message, e);
  }

  public XmlParseException(final File file, final Throwable e) {
    super(MESSAGE_PARSING_FAILED + file + " " + e, e);
  }

  public XmlParseException(final Url url, final Throwable e) {
    super(MESSAGE_PARSING_FAILED + url + " " + e, e);
  }

  public XmlParseException(final URL url, final Throwable e) {
    super(MESSAGE_PARSING_FAILED + url + " " + e, e);
  }

  public XmlParseException(final URI uri, final Throwable e) {
    super(MESSAGE_PARSING_FAILED + uri + " " + e, e);
  }

  public XmlParseException(final InputStream in, final Throwable e) {
    super(MESSAGE_PARSING_FAILED + in + " " + e, e);
  }

  public XmlParseException(final Reader reader, final Throwable e) {
    super(MESSAGE_PARSING_FAILED + reader + " " + e, e);
  }

  public XmlParseException(final File file, final String message) {
    super(MESSAGE_PARSING_FAILED + file + " " + message);
  }

  public XmlParseException(final URL url, final String message) {
    super(MESSAGE_PARSING_FAILED + url + " " + message);
  }

  public XmlParseException(final URI uri, final String message) {
    super(MESSAGE_PARSING_FAILED + uri + " " + message);
  }

  public XmlParseException(final InputStream in, final String message) {
    super(MESSAGE_PARSING_FAILED + in + " " + message);
  }
}
