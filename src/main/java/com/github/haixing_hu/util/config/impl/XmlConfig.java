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

package com.github.haixing_hu.util.config.impl;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.URL;

import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.net.Url;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.util.config.Config;
import com.github.haixing_hu.util.config.error.ConfigurationError;

/**
 * A {@link Config} object which loads/stores configurations from/to XML files.
 *
 * @author Haixing Hu
 */
public final class XmlConfig extends DefaultConfig {

  private static final long serialVersionUID = - 2988726634410349164L;

  /**
   * Constructs an empty {@link XmlConfig}.
   */
  public XmlConfig() {
    super();
  }

  /**
   * Constructs an {@link XmlConfig}.
   *
   * @param resource
   *          the path of the XML resource where to load the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public XmlConfig(final String resource) {
    super();
    load(resource);
  }

  /**
   * Constructs an {@link XmlConfig}.
   *
   * @param resource
   *          the path of the XML resource where to load the configuration.
   * @param loaderClass
   *          the class used to load the resource.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public XmlConfig(final String resource, final Class<?> loaderClass) {
    super();
    load(resource, loaderClass);
  }

  /**
   * Constructs an {@link XmlConfig}.
   *
   * @param resource
   *          the path of the XML resource where to load the configuration.
   * @param loader
   *          the class loader use to load the resource.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public XmlConfig(final String resource, final ClassLoader loader) {
    super();
    load(resource, loader);
  }

  /**
   * Constructs an {@link XmlConfig}.
   *
   * @param url
   *          the URL of the XML resource where to load the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public XmlConfig(final Url url) {
    super();
    load(url);
  }

  /**
   * Constructs an {@link XmlConfig}.
   *
   * @param url
   *          the URL of the XML resource where to load the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public XmlConfig(final URL url) {
    super();
    load(url);
  }

  /**
   * Constructs an {@link XmlConfig}.
   *
   * @param uri
   *          the URI of the XML resource where to load the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public XmlConfig(final URI uri) {
    super();
    load(uri);
  }

  /**
   * Constructs an {@link XmlConfig}.
   *
   * @param file
   *          the XML file where to load the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public XmlConfig(final File file) {
    super();
    load(file);
  }

  /**
   * Constructs an {@link XmlConfig}.
   * <p>
   * After calling this function, the input stream remains opened.
   *
   * @param in
   *          the input stream storing the XML file where to load the
   *          configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public XmlConfig(final InputStream in) {
    super();
    load(in);
  }

  /**
   * Constructs an {@link XmlConfig}.
   * <p>
   * After calling this function, the reader remains opened.
   *
   * @param reader
   *          the reader storing the XML file where to load the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public XmlConfig(final Reader reader) {
    super();
    load(reader);
  }

  /**
   * Loads the configuration from an XML file.
   *
   * @param resource
   *          the path of the XML resource where to load the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void load(final String resource) {
    LOGGER.debug("Loading XML configuration from {}", resource);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(
          DefaultConfig.class, resource, XmlConfig.class.getClassLoader());
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an XML file.
   *
   * @param resource
   *          the path of the XML resource where to load the configuration.
   * @param loaderClass
   *          the class used to load the resource.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void load(final String resource, final Class<?> loaderClass) {
    LOGGER.debug("Loading XML configuration from {}", resource);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(
          DefaultConfig.class, resource, loaderClass);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an XML file.
   *
   * @param resource
   *          the path of the XML resource where to load the configuration.
   * @param loader
   *          the class loader used to load the resource.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void load(final String resource, final ClassLoader loader) {
    LOGGER.debug("Loading XML configuration from {}", resource);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(
          DefaultConfig.class, resource, loader);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an XML file.
   *
   * @param url
   *          the URL of the XML resource where to load the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void load(final Url url) {
    LOGGER.debug("Loading XML configuration from {}", url);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(
          DefaultConfig.class, url);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an XML file.
   *
   * @param url
   *          the URL of the XML resource where to load the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void load(final URL url) {
    LOGGER.debug("Loading XML configuration from {}", url);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(
          DefaultConfig.class, url);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an XML file.
   *
   * @param URI
   *          the URI of the XML resource where to load the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void load(final URI uri) {
    try {
      final DefaultConfig config = XmlSerialization.deserialize(
          DefaultConfig.class, uri);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an XML file.
   *
   * @param file
   *          the XML file where to load the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void load(final File file) {
    LOGGER.debug("Loading XML configuration from {}", file);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(
          DefaultConfig.class, file);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an XML file.
   * <p>
   * After calling this function, the input stream remains opened.
   *
   * @param in
   *          the input stream storing the XML file where to load the
   *          configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void load(final InputStream in) {
    LOGGER.debug("Loading XML configuration from {}", in);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(
          DefaultConfig.class, in);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an XML file.
   * <p>
   * After calling this function, the input stream remains opened.
   *
   * @param reader
   *          the reader storing the XML file where to load the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void load(final Reader reader) {
    LOGGER.debug("Loading XML configuration from {}", reader);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(
          DefaultConfig.class, reader);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Stores this configuration to an output stream.
   * <p>
   * After calling this function, the stream is flushed but remains opened.
   *
   * @param out
   *          the output stream where to store the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void store(final OutputStream out) {
    LOGGER.debug("Storing XML configuration from {}", out);
    try {
      XmlSerialization.serialize(DefaultConfig.class, this, out);
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Stores this configuration to a print stream.
   * <p>
   * After calling this function, the stream is flushed but remains opened.
   *
   * @param out
   *          the print stream where to store the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void store(final PrintStream out) {
    LOGGER.debug("Storing XML configuration from {}", out);
    try {
      XmlSerialization.serialize(DefaultConfig.class, this, out);
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Stores this configuration to a writer.
   * <p>
   * After calling this function, the writer is flushed but remains opened.
   *
   * @param writer
   *          the writer where to store the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void store(final Writer writer) {
    LOGGER.debug("Storing XML configuration from {}", writer);
    try {
      XmlSerialization.serialize(DefaultConfig.class, this, writer);
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Stores this configuration to an XML file.
   *
   * @param file
   *          the XML file where to store the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void store(final File file) {
    LOGGER.debug("Storing XML configuration from {}", file);
    try {
      XmlSerialization.serialize(DefaultConfig.class, this, file);
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }
}
