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

package com.github.haixing_hu.io.serialize;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.concurrent.GuardedBy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.io.IoUtils;
import com.github.haixing_hu.net.Url;
import com.github.haixing_hu.text.CharsetUtils;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.text.xml.XmlSerializationException;
import com.github.haixing_hu.text.xml.XmlUtils;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * Provides functions to manager {@link XmlSerializer}s, as well as functions to
 * help serializing and deserializing objects to and from XML DOM trees.
 *
 * @author Haixing Hu
 */
public class XmlSerialization {

  private static final Logger LOGGER = LoggerFactory.getLogger(XmlSerialization.class);

  @GuardedBy("registry")
  private static final Map<Class<?>, XmlSerializer> registry = new HashMap<Class<?>, XmlSerializer>();

  /**
   * Registers an XML serializer for a class.
   *
   * @param objClass
   *          The class object.
   * @param serializer
   *          The XML serializer for the specified class.
   */
  public static void register(final Class<?> objClass,
      final XmlSerializer serializer) {
    requireNonNull("objClass", objClass);
    requireNonNull("serializer", serializer);
    LOGGER.debug("Registering an XML serializer for class {}.", objClass);
    synchronized (registry) {
      if (registry.containsKey(objClass)) {
        LOGGER.warn("Override the XML serializer for class {}.", objClass);
      }
      registry.put(objClass, serializer);
    }
  }

  /**
   * Gets the registered XML serializer for a specified class.
   *
   * @param objClass
   *          The class object.
   * @return The registered XML serializer for the specified class, or null if
   *         no XML serializer was registered for the specified class.
   */
  public static XmlSerializer getSerializer(final Class<?> objClass) {
    requireNonNull("objClass", objClass);
    LOGGER.debug("Getting the XML serializer for class {}.", objClass);
    // ensure the objClass has been load, such that the register code in
    // the static initialization block of the class could be run.
    final String className = objClass.getName();
    try {
      Class.forName(className);
    } catch (final ClassNotFoundException e) {
      LOGGER.error("Failed to load the class {}", className);
    }
    synchronized (registry) {
      return registry.get(objClass);
    }
  }

  public static <T> String serialize(final Class<T> objClass, final T obj)
      throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final org.w3c.dom.Document doc = XmlUtils.newDocument();
    final org.w3c.dom.Element node = serializer.serialize(doc, obj);
    final StringWriter out = new StringWriter();
    XmlUtils.print(node, out);
    return out.toString();
  }

  public static <T> Element serialize(final Class<T> objClass, final T obj,
      final Document doc) throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    return serializer.serialize(doc, obj);
  }

  public static <T> void serialize(final Class<T> objClass, final T obj,
      final PrintStream out) throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final org.w3c.dom.Document doc = XmlUtils.newDocument();
    final org.w3c.dom.Element root = serializer.serialize(doc, obj);
    doc.appendChild(root);
    XmlUtils.print(doc, out);
  }

  public static <T> void serialize(final Class<T> objClass, final T obj,
      final Writer writer) throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final org.w3c.dom.Document doc = XmlUtils.newDocument();
    final org.w3c.dom.Element root = serializer.serialize(doc, obj);
    doc.appendChild(root);
    XmlUtils.print(doc, writer);
  }

  public static <T> void serialize(final Class<T> objClass, final T obj,
      final File file) throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    OutputStream os = null;
    Writer writer = null;
    try {
      os = new FileOutputStream(file);
      writer = new OutputStreamWriter(os, CharsetUtils.UTF_8);
      final org.w3c.dom.Document doc = XmlUtils.newDocument();
      final org.w3c.dom.Element root = serializer.serialize(doc, obj);
      doc.appendChild(root);
      XmlUtils.print(doc, writer);
    } catch (final FileNotFoundException e) {
      throw new XmlSerializationException(e);
    } catch (final SecurityException e) {
      throw new XmlSerializationException(e);
    } finally {
      IoUtils.closeQuietly(writer);
      IoUtils.closeQuietly(os);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final Element node)
      throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    try {
      return (T) serializer.deserialize(node);
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final String xml)
      throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final StringReader reader = new StringReader(xml);
    final org.w3c.dom.Document doc = XmlUtils.parse(reader);
    final org.w3c.dom.Element root = doc.getDocumentElement();
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final Reader reader)
      throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final org.w3c.dom.Document doc = XmlUtils.parse(reader);
    final org.w3c.dom.Element root = doc.getDocumentElement();
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final InputStream in)
      throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final org.w3c.dom.Document doc = XmlUtils.parse(in);
    final org.w3c.dom.Element root = doc.getDocumentElement();
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final File file)
      throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final org.w3c.dom.Document doc = XmlUtils.parse(file);
    final org.w3c.dom.Element root = doc.getDocumentElement();
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final Url url)
      throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final org.w3c.dom.Document doc = XmlUtils.parse(url);
    final org.w3c.dom.Element root = doc.getDocumentElement();
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final URL url)
      throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final org.w3c.dom.Document doc = XmlUtils.parse(url);
    final org.w3c.dom.Element root = doc.getDocumentElement();
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final URI uri)
      throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final org.w3c.dom.Document doc = XmlUtils.parse(uri);
    final org.w3c.dom.Element root = doc.getDocumentElement();
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
  }


  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass,
      final String resource, final Class<?> resourceClass) throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final org.w3c.dom.Document doc = XmlUtils.parse(resource, resourceClass);
    final org.w3c.dom.Element root = doc.getDocumentElement();
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass,
      final String resource, final ClassLoader loader) throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final org.w3c.dom.Document doc = XmlUtils.parse(resource, loader);
    final org.w3c.dom.Element root = doc.getDocumentElement();
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
  }
}
