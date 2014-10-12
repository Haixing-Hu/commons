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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.annotation.concurrent.ThreadSafe;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.github.haixing_hu.io.IoUtils;
import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.lang.SystemUtils;
import com.github.haixing_hu.net.Url;
import com.github.haixing_hu.net.UrlUtils;

import static com.github.haixing_hu.CommonsMessages.RESOURCE_NOT_FOUND;
import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * This class provides utility functions for manipulating XML documents.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public class XmlUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(XmlUtils.class);

  public static final String PRESERVE_SPACE_ATTRIBUTE = "preserve-space";
  public static final boolean DEFAULT_PRESERVE_SPACE = false;
  public static final boolean DEFAULT_TRIM = true;

  public static final String DEFAULT_SEPARATOR_ATTRIBUTE = "separator";
  public static final char DEFAULT_SEPARATOR = ',';

  public static final String DEFAULT_CLASS_ATTRIBUTE = "class";

  public static final String DEFAULT_CONFIG_ATTRIBUTE = "config";

  public static final String ENCODING = "UTF-8";

  public static final String INDENT_AMOUNT = "2";

  private static final String CREATING_XML_BUILDER = "Creating the XML builder ...";

  private static final String CREATING_XML_TRANSFORMER = "Creating the XML transformer ...";

  private static final String CREATING_XPATH = "Creating the XPath ...";

  private static final String PARSING_XML = "Parsing the XML at {} ...";

  private static final String TRANSFORMING_XML = "Transforming the XML ...";

  private static final String INCLUSION_NOT_SUPPORTED = "The XML document builder factory does not support the XML inclusions feature. ";

  private static final String INDENT_AMOUNT_KEY = "{http://xml.apache.org/xslt}indent-amount";

  /**
   * A global thread-local XML document builder factory.
   */
  public static final ThreadLocal<DocumentBuilderFactory> BUILDER_FACTORY =
      new ThreadLocal<DocumentBuilderFactory>() {
    @Override
    protected DocumentBuilderFactory initialValue() {
      final DocumentBuilderFactory factory = DocumentBuilderFactory
          .newInstance();
      factory.setIgnoringComments(true);
      factory.setIgnoringElementContentWhitespace(true);
      factory.setNamespaceAware(true);
      try {
        factory.setXIncludeAware(true);
      } catch (final UnsupportedOperationException e) {
        LOGGER.error(INCLUSION_NOT_SUPPORTED);
      }
      return factory;
    }
  };

  /**
   * A global thread-local XML document builder.
   */
  public static final ThreadLocal<DocumentBuilder> BUILDER =
      new ThreadLocal<DocumentBuilder>() {
    @Override
    protected DocumentBuilder initialValue() {
      try {
        LOGGER.debug(CREATING_XML_BUILDER);
        return BUILDER_FACTORY.get().newDocumentBuilder();
      } catch (final ParserConfigurationException e) {
        LOGGER.error("Failed to create a XML document builder.", e);
        return null;
      }
    }
  };

  /**
   * A global thread-local XML transformer factory.
   */
  public static final ThreadLocal<TransformerFactory> TRANSFORMER_FACTORY =
      new ThreadLocal<TransformerFactory>() {
    @Override
    protected TransformerFactory initialValue() {
      final TransformerFactory factory = TransformerFactory.newInstance();
      return factory;
    }
  };

  /**
   * A global thread-local XML transformer.
   */
  public static final ThreadLocal<Transformer> TRANSFORMER =
      new ThreadLocal<Transformer>() {
    @Override
    protected Transformer initialValue() {
      try {
        LOGGER.debug(CREATING_XML_TRANSFORMER);
        return TRANSFORMER_FACTORY.get().newTransformer();
      } catch (final TransformerConfigurationException e) {
        LOGGER.error("Failed to create a XML transformer.", e);
        return null;
      }
    }
  };

  /**
   * A global thread-local XPath factory.
   */
  public static final ThreadLocal<XPathFactory> XPATH_FACTORY =
      new ThreadLocal<XPathFactory>() {
    @Override
    protected XPathFactory initialValue() {
      final XPathFactory factory = XPathFactory.newInstance();
      return factory;
    }
  };

  /**
   * A global thread-local XPath.
   */
  public static final ThreadLocal<XPath> XPATH = new ThreadLocal<XPath>() {
    @Override
    protected XPath initialValue() {
      final XPathFactory factory = XPATH_FACTORY.get();
      LOGGER.debug(CREATING_XPATH);
      return factory.newXPath();
    }
  };

  /**
   * Creates a new XML document builder.
   *
   * @return a new XML document builder.
   * @throws XmlException
   *           if any error occurs.
   */
  public static final DocumentBuilder newBuilder() throws XmlException {
    final DocumentBuilderFactory factory = BUILDER_FACTORY.get();
    try {
      LOGGER.debug(CREATING_XML_BUILDER);
      return factory.newDocumentBuilder();
    } catch (final ParserConfigurationException e) {
      throw new CreateXmlBuilderException(e);
    }
  }

  /**
   * Creates a new XML transformer.
   *
   * @return a new XML transformer.
   * @throws XmlException
   *           if any error occurs.
   */
  public static Transformer newTransformer() throws XmlException {
    final TransformerFactory factory = TRANSFORMER_FACTORY.get();
    try {
      LOGGER.debug(CREATING_XML_TRANSFORMER);
      return factory.newTransformer();
    } catch (final TransformerConfigurationException e) {
      throw new CreateXmlTransformerException(e);
    }
  }

  /**
   * Creates a new XPath.
   *
   * @return a new XPath.
   * @throws XmlException
   *           if any error occurs.
   */
  public static XPath newXPath() {
    final XPathFactory factory = XPATH_FACTORY.get();
    LOGGER.debug(CREATING_XPATH);
    return factory.newXPath();
  }

  /**
   * Creates a new XML document.
   *
   * @return a new XML document.
   * @throws XmlException
   *           if any error occurs.
   */
  public static Document newDocument() throws XmlException {
    final DocumentBuilder builder = BUILDER.get();
    builder.reset();
    return builder.newDocument();
  }

  /**
   * Parses an XML document.
   *
   * @param resource
   *          the path of resource containing the XML document.
   * @return the parsed XML document.
   * @throws XmlException
   *           if any error occurs.
   */
  public static Document parse(final String resource) throws XmlException {
    final URL url = SystemUtils.getResource(resource);
    if (url == null) {
      throw new XmlParseException(RESOURCE_NOT_FOUND + resource);
    }
    InputStream in = null;
    try {
      in = UrlUtils.openStream(url);
      LOGGER.debug(PARSING_XML, url);
      return parseInputStream(in);
    } catch (final IOException e) {
      throw new XmlParseException(url, e);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  /**
   * Parses an XML document.
   *
   * @param resource
   *          the path of resource containing the XML document.
   * @param classLoader
   *          a class loader used to load the resource.
   * @return the parsed XML document.
   * @throws XmlException
   *           if any error occurs.
   */
  public static Document parse(final String resource,
      final ClassLoader classLoader) throws XmlException {
    final URL url = SystemUtils.getResource(resource, classLoader);
    if (url == null) {
      throw new XmlParseException(RESOURCE_NOT_FOUND + resource);
    }
    InputStream in = null;
    try {
      in = UrlUtils.openStream(url);
      LOGGER.debug(PARSING_XML, url);
      return parseInputStream(in);
    } catch (final IOException e) {
      throw new XmlParseException(url, e);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  /**
   * Parses an XML document.
   *
   * @param resource
   *          the path of resource containing the XML document.
   * @param clazz
   *          a class used to load the resource.
   * @return the parsed XML document.
   * @throws XmlException
   *           if any error occurs.
   */
  public static Document parse(final String resource, final Class<?> clazz)
      throws XmlException {
    final URL url = SystemUtils.getResource(resource, clazz);
    if (url == null) {
      throw new XmlParseException(RESOURCE_NOT_FOUND + resource);
    }
    InputStream in = null;
    try {
      in = UrlUtils.openStream(url);
      LOGGER.debug(PARSING_XML, url);
      return parseInputStream(in);
    } catch (final IOException e) {
      throw new XmlParseException(url, e);
    }  finally {
      IoUtils.closeQuietly(in);
    }
  }

  /**
   * Parses an XML document.
   *
   * @param file
   *          an XML file.
   * @return the parsed XML document.
   * @throws XmlException
   *           if any error occurs.
   */
  public static Document parse(final File file) throws XmlException {
    InputStream in = null;
    try {
      in = new FileInputStream(file);
      LOGGER.debug(PARSING_XML, file);
      return parseInputStream(in);
    } catch (final IOException e) {
      throw new XmlParseException(file, e);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  /**
   * Parses an XML document.
   *
   * @param url
   *          the URL to the XML file.
   * @return the parsed XML document.
   * @throws XmlException
   *           if any error occurs.
   */
  public static Document parse(final Url url) throws XmlException {
    InputStream in = null;
    try {
      in = UrlUtils.openStream(url);
      LOGGER.debug(PARSING_XML, url);
      return parseInputStream(in);
    } catch (final MalformedURLException e) {
      throw new XmlParseException(url, e);
    } catch (final IOException e) {
      throw new XmlParseException(url, e);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  /**
   * Parses an XML document.
   *
   * @param url
   *          the URL to the XML file.
   * @return the parsed XML document.
   * @throws XmlException
   *           if any error occurs.
   */
  public static Document parse(final URL url) throws XmlException {
    InputStream in = null;
    try {
      in = UrlUtils.openStream(url);
      LOGGER.debug(PARSING_XML, url);
      return parseInputStream(in);
    } catch (final MalformedURLException e) {
      throw new XmlParseException(url, e);
    } catch (final IOException e) {
      throw new XmlParseException(url, e);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  /**
   * Parses an XML document.
   *
   * @param uri
   *          the URI to the XML file.
   * @return the parsed XML document.
   * @throws XmlException
   *           if any error occurs.
   */
  public static Document parse(final URI uri) throws XmlException {
    InputStream in = null;
    try {
      in = UrlUtils.openStream(uri);
      LOGGER.debug(PARSING_XML, uri);
      return parseInputStream(in);
    } catch (final MalformedURLException e) {
      throw new XmlParseException(uri, e);
    } catch (final IOException e) {
      throw new XmlParseException(uri, e);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  /**
   * Parses an XML document from an input stream.
   * <p>
   * The input stream remains opened after calling this function.
   *
   * @param in
   *          an input stream.
   * @return a parsed XML document.
   * @throws XmlException
   *           if any error occurs.
   */
  public static Document parse(final InputStream in) throws XmlException {
    LOGGER.debug(PARSING_XML, in);
    return parseInputStream(in);
  }

  /**
   * Parses an XML document from a reader.
   * <p>
   * The reader remains opened after calling this function.
   *
   * @param reader
   *          a reader.
   * @return a parsed XML document.
   * @throws XmlException
   *           if any error occurs.
   */
  public static Document parse(final Reader reader) throws XmlException {
    final DocumentBuilder builder = BUILDER.get();
    try {
      LOGGER.debug(PARSING_XML, reader);
      builder.reset();
      final InputSource input = new InputSource(reader);
      return builder.parse(input);
    } catch (final SAXException e) {
      throw new XmlParseException(reader, e);
    } catch (final IOException e) {
      throw new XmlParseException(reader, e);
    }
  }

  /**
   * Parses an XML document from an input stream.
   * <p>
   * The input stream remains opened after calling this function.
   *
   * @param in
   *          an input stream.
   * @return a parsed XML document.
   * @throws XmlException
   *           if any error occurs.
   */
  private static Document parseInputStream(final InputStream in)
      throws XmlException {
    final DocumentBuilder builder = BUILDER.get();
    try {
      builder.reset();
      return builder.parse(in);
    } catch (final SAXException e) {
      throw new XmlParseException(in, e);
    } catch (final IOException e) {
      throw new XmlParseException(in, e);
    }
  }

  /**
   * Prints an XML document to a writer.
   * <p>
   * After calling this function, the writer is flushed but remains opened.
   *
   * @param doc
   *          an XML document.
   * @param writer
   *          a writer where to print the XML document.
   * @throws XmlException
   *           if any error occurs.
   */
  public static void print(final Document doc, final Writer writer)
      throws XmlException {
    final DOMSource source = new DOMSource(requireNonNull("doc", doc));
    final StreamResult sr = new StreamResult(requireNonNull("writer", writer));
    final Transformer transformer = TRANSFORMER.get();
    transformer.reset();
    transformer.setOutputProperty(OutputKeys.ENCODING, ENCODING);
    transformer.setOutputProperty(OutputKeys.INDENT, StringUtils.YES);
    transformer.setOutputProperty(INDENT_AMOUNT_KEY, INDENT_AMOUNT);
    try {
      LOGGER.debug(TRANSFORMING_XML);
      transformer.transform(source, sr);
    } catch (final TransformerException e) {
      throw new XmlTransformException(e);
    }
  }

  /**
   * Prints an XML document to a print stream.
   * <p>
   * After calling this function, the stream is flushed but remains opened.
   *
   * @param doc
   *          an XML document.
   * @param out
   *          a print stream where to print the XML document.
   * @throws XmlException
   *           if any error occurs.
   */
  public static void print(final Document doc, final PrintStream out)
      throws XmlException {
    final DOMSource source = new DOMSource(requireNonNull("doc", doc));
    final StreamResult sr = new StreamResult(requireNonNull("out", out));
    final Transformer transformer = TRANSFORMER.get();
    transformer.reset();
    transformer.setOutputProperty(OutputKeys.ENCODING, ENCODING);
    transformer.setOutputProperty(OutputKeys.INDENT, StringUtils.YES);
    transformer.setOutputProperty(INDENT_AMOUNT_KEY, INDENT_AMOUNT);
    try {
      LOGGER.debug(TRANSFORMING_XML);
      transformer.transform(source, sr);
    } catch (final TransformerException e) {
      throw new XmlTransformException(e);
    }
  }

  /**
   * Prints an XML document to an output stream.
   * <p>
   * After calling this function, the stream is flushed but remains opened.
   *
   * @param doc
   *          an XML document.
   * @param out
   *          an output stream where to print the XML document.
   * @throws XmlException
   *           if any error occurs.
   */
  public static void print(final Document doc, final OutputStream out)
      throws XmlException {
    final DOMSource source = new DOMSource(requireNonNull("doc", doc));
    final StreamResult sr = new StreamResult(requireNonNull("out", out));
    final Transformer transformer = TRANSFORMER.get();
    transformer.reset();
    transformer.setOutputProperty(OutputKeys.ENCODING, ENCODING);
    transformer.setOutputProperty(OutputKeys.INDENT, StringUtils.YES);
    transformer.setOutputProperty(INDENT_AMOUNT_KEY, INDENT_AMOUNT);
    try {
      LOGGER.debug(TRANSFORMING_XML);
      transformer.transform(source, sr);
    } catch (final TransformerException e) {
      throw new XmlTransformException(e);
    }
  }

  /**
   * Prints an XML node to a writer.
   * <p>
   * After calling this function, the writer is flushed but remains opened.
   *
   * @param node
   *          an XML node.
   * @param writer
   *          a writer where to print the XML node.
   * @throws XmlException
   *           if any error occurs.
   */
  public static void print(final Node node, final Writer writer)
      throws XmlException {
    final DOMSource source = new DOMSource(requireNonNull("node", node));
    final StreamResult sr = new StreamResult(requireNonNull("writer", writer));
    final Transformer transformer = TRANSFORMER.get();
    transformer.reset();
    transformer.setOutputProperty(OutputKeys.ENCODING, ENCODING);
    transformer.setOutputProperty(OutputKeys.INDENT, StringUtils.YES);
    transformer.setOutputProperty(INDENT_AMOUNT_KEY, INDENT_AMOUNT);
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
        StringUtils.YES);
    try {
      LOGGER.debug(TRANSFORMING_XML);
      transformer.transform(source, sr);
    } catch (final TransformerException e) {
      throw new XmlTransformException(e);
    }
  }

  /**
   * Prints an XML node to a print stream.
   * <p>
   * After calling this function, the stream is flushed but remains opened.
   *
   * @param node
   *          an XML node.
   * @param out
   *          a print stream where to print the XML node.
   * @throws XmlException
   *           if any error occurs.
   */
  public static void print(final Node node, final PrintStream out)
      throws XmlException {
    final DOMSource source = new DOMSource(requireNonNull("node", node));
    final StreamResult sr = new StreamResult(requireNonNull("out", out));
    final Transformer transformer = TRANSFORMER.get();
    transformer.reset();
    transformer.setOutputProperty(OutputKeys.ENCODING, ENCODING);
    transformer.setOutputProperty(OutputKeys.INDENT, StringUtils.YES);
    transformer.setOutputProperty(INDENT_AMOUNT_KEY, INDENT_AMOUNT);
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
        StringUtils.YES);
    try {
      LOGGER.debug(TRANSFORMING_XML);
      transformer.transform(source, sr);
    } catch (final TransformerException e) {
      throw new XmlTransformException(e);
    }
  }

  /**
   * Formats an XML document to a string.
   * <p>
   * <b>NOTE:</b> This function does not throws any error. Instead, it will
   * returns the string containing the exception information in case of error.
   *
   * @param document
   *          an XML document.
   * @return a string contains the formated XML document.
   */
  public static String toString(final Document doc) {
    if (doc == null) {
      return StringUtils.EMPTY;
    } else {
      final StringWriter out = new StringWriter();
      try {
        print(doc, out);
      } catch (final XmlException e) {
        out.append(e.toString());
      }
      return out.toString();
    }
  }

  /**
   * Formats an XML node to a string.
   * <p>
   * <b>NOTE:</b> This function does not throws any error. Instead, it will
   * returns the string containing the exception information in case of error.
   *
   * @param node
   *          an XML node.
   * @return a string contains the formated XML node.
   */
  public static String toString(final Node node) {
    if (node == null) {
      return StringUtils.EMPTY;
    } else {
      final StringWriter out = new StringWriter();
      try {
        print(node, out);
      } catch (final XmlException e) {
        out.append(e.toString());
      }
      return out.toString();
    }
  }

  /**
   * Compiles a XPath expression to an {@link XPath} object.
   *
   * @param expression
   *          the expression of an XPath.
   * @return the compiled {@link XPath} object.
   * @throws InvalidXPathExpressionException
   *           if any error occurs.
   */
  public static XPathExpression compileXPath(final String expression)
      throws InvalidXPathExpressionException {
    requireNonNull("expression", expression);
    final XPath xpath = XPATH.get();
    try {
      xpath.reset();
      return xpath.compile(expression);
    } catch (final XPathExpressionException e) {
      throw new InvalidXPathExpressionException(expression, e);
    }
  }
}
