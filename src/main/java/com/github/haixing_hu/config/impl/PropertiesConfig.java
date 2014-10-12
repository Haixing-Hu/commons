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
package com.github.haixing_hu.config.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;

import com.github.haixing_hu.config.Config;
import com.github.haixing_hu.config.error.ConfigurationError;
import com.github.haixing_hu.io.IoUtils;
import com.github.haixing_hu.net.Url;
import com.github.haixing_hu.text.CharsetUtils;
import com.github.haixing_hu.util.PropertiesUtils;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * A {@link Config} object which loads/stores configurations from/to Java
 * properties files.
 *
 * @author Haixing Hu
 */
public class PropertiesConfig extends DefaultConfig {

  private static final long serialVersionUID = 6463503499725651996L;

  /**
   * The default charset of the properties file.
   */
  public static final Charset DEFAULT_CHARSET = CharsetUtils.UTF_8;

  private Charset charset;

  /**
   * Constructs an empty {@link PropertiesConfig}, with the default charset.
   */
  public PropertiesConfig() {
    super();
    charset = DEFAULT_CHARSET;
  }

  /**
   * Constructs an empty {@link PropertiesConfig}, with the specified charset.
   *
   * @param charset
   *          the charset of the properties file.
   */
  public PropertiesConfig(final Charset charset) {
    super();
    this.charset = requireNonNull("charset", charset);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param resource
   *          the path of the properties resource where to load the
   *          configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public PropertiesConfig(final String resource) {
    super();
    charset = DEFAULT_CHARSET;
    load(resource);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param resource
   *          the path of the properties resource where to load the
   *          configuration.
   * @param charset
   *          the charset of the properties file.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public PropertiesConfig(final String resource, final Charset charset) {
    super();
    this.charset = requireNonNull("charset", charset);
    load(resource);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param resource
   *          the path of the properties resource where to load the
   *          configuration.
   * @param loaderClass
   *          the class used to load the resource.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public PropertiesConfig(final String resource, final Class<?> loaderClass) {
    super();
    charset = DEFAULT_CHARSET;
    load(resource, loaderClass);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param resource
   *          the path of the properties resource where to load the
   *          configuration.
   * @param loaderClass
   *          the class used to load the resource.
   * @param charset
   *          the charset of the properties file.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public PropertiesConfig(final String resource, final Class<?> loaderClass,
      final Charset charset) {
    super();
    this.charset = requireNonNull("charset", charset);
    load(resource, loaderClass);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param resource
   *          the path of the properties resource where to load the
   *          configuration.
   * @param loader
   *          the class loader use to load the resource.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public PropertiesConfig(final String resource, final ClassLoader loader) {
    super();
    charset = DEFAULT_CHARSET;
    load(resource, loader);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param resource
   *          the path of the properties resource where to load the
   *          configuration.
   * @param loader
   *          the class loader use to load the resource.
   * @param charset
   *          the charset of the properties file.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public PropertiesConfig(final String resource, final ClassLoader loader,
      final Charset charset) {
    super();
    this.charset = requireNonNull("charset", charset);
    load(resource, loader);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param url
   *          the URL of the properties resource where to load the
   *          configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public PropertiesConfig(final Url url) {
    super();
    charset = DEFAULT_CHARSET;
    load(url);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param url
   *          the URL of the properties resource where to load the
   *          configuration.
   * @param charset
   *          the charset of the properties file.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public PropertiesConfig(final Url url, final Charset charset) {
    super();
    this.charset = requireNonNull("charset", charset);
    load(url);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param url
   *          the URL of the properties resource where to load the
   *          configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public PropertiesConfig(final URL url) {
    super();
    charset = DEFAULT_CHARSET;
    load(url);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param url
   *          the URL of the properties resource where to load the
   *          configuration.
   * @param charset
   *          the charset of the properties file.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public PropertiesConfig(final URL url, final Charset charset) {
    super();
    this.charset = requireNonNull("charset", charset);
    load(url);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param uri
   *          the URI of the properties resource where to load the
   *          configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public PropertiesConfig(final URI uri) {
    super();
    charset = DEFAULT_CHARSET;
    load(uri);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param uri
   *          the URI of the properties resource where to load the
   *          configuration.
   * @param charset
   *          the charset of the properties file.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public PropertiesConfig(final URI uri, final Charset charset) {
    super();
    this.charset = requireNonNull("charset", charset);
    load(uri);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param file
   *          the properties file where to load the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public PropertiesConfig(final File file) {
    super();
    charset = DEFAULT_CHARSET;
    load(file);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param file
   *          the properties file where to load the configuration.
   * @param charset
   *          the charset of the properties file.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public PropertiesConfig(final File file, final Charset charset) {
    super();
    this.charset = requireNonNull("charset", charset);
    load(file);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   * <p>
   * After calling this function, the input stream remains opened.
   *
   * @param in
   *          the input stream storing the properties where to load the
   *          configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public PropertiesConfig(final InputStream in) {
    super();
    charset = DEFAULT_CHARSET;
    load(in);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   * <p>
   * After calling this function, the input stream remains opened.
   *
   * @param in
   *          the input stream storing the properties where to load the
   *          configuration.
   * @param charset
   *          the charset of the properties file.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public PropertiesConfig(final InputStream in, final Charset charset) {
    super();
    this.charset = requireNonNull("charset", charset);
    load(in);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   * <p>
   * After calling this function, the reader remains opened.
   *
   * @param reader
   *          the reader storing the properties where to load the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public PropertiesConfig(final Reader reader) {
    super();
    charset = DEFAULT_CHARSET;
    load(reader);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   * <p>
   * After calling this function, the reader remains opened.
   *
   * @param reader
   *          the reader storing the properties where to load the configuration.
   * @param charset
   *          the charset of the properties file.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public PropertiesConfig(final Reader reader, final Charset charset) {
    super();
    this.charset = requireNonNull("charset", charset);
    load(reader);
  }

  /**
   * Gets the charset.
   *
   * @return the charset.
   */
  public Charset getCharset() {
    return charset;
  }

  /**
   * Sets the charset.
   *
   * @param charset
   *          the new charset to set.
   */
  public void setCharset(final Charset charset) {
    this.charset = requireNonNull("charset", charset);
  }

  /**
   * Loads the configuration from an properties file.
   *
   * @param resource
   *          the path of the properties resource where to load the
   *          configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void load(final String resource) {
    LOGGER.debug("Loading properties configuration from {}", resource);
    try {
      final Properties properties = PropertiesUtils.load(resource, charset);
      load(properties);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an properties file.
   *
   * @param resource
   *          the path of the properties resource where to load the
   *          configuration.
   * @param loaderClass
   *          the class used to load the resource.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void load(final String resource, final Class<?> loaderClass) {
    LOGGER.debug("Loading properties configuration from {}", resource);
    try {
      final Properties properties = PropertiesUtils.load(resource, loaderClass,
          charset);
      load(properties);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an properties file.
   *
   * @param resource
   *          the path of the properties resource where to load the
   *          configuration.
   * @param loader
   *          the class loader used to load the resource.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void load(final String resource, final ClassLoader loader) {
    LOGGER.debug("Loading properties configuration from {}", resource);
    try {
      final Properties properties = PropertiesUtils.load(resource, loader,
          charset);
      load(properties);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an properties file.
   *
   * @param url
   *          the URL of the properties resource where to load the
   *          configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void load(final Url url) {
    LOGGER.debug("Loading properties configuration from {}", url);
    try {
      final Properties properties = PropertiesUtils.load(url, charset);
      load(properties);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an properties file.
   *
   * @param url
   *          the URL of the properties resource where to load the
   *          configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void load(final URL url) {
    LOGGER.debug("Loading properties configuration from {}", url);
    try {
      final Properties properties = PropertiesUtils.load(url, charset);
      load(properties);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an properties file.
   *
   * @param URI
   *          the URI of the properties resource where to load the
   *          configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void load(final URI uri) {
    try {
      final Properties properties = PropertiesUtils.load(uri, charset);
      load(properties);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an properties file.
   *
   * @param file
   *          the properties file where to load the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void load(final File file) {
    LOGGER.debug("Loading properties configuration from {}", file);
    try {
      final Properties properties = PropertiesUtils.load(file, charset);
      load(properties);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from a properties read from an input stream.
   * <p>
   * After calling this function, the input stream remains opened.
   *
   * @param in
   *          the input stream of the properties file where to load the
   *          configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void load(final InputStream in) {
    LOGGER.debug("Loading properties configuration from {}", in);
    try {
      final Properties properties = PropertiesUtils.load(in, charset);
      load(properties);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from a properties read from a reader.
   * <p>
   * After calling this function, the reader remains opened.
   *
   * @param reader
   *          the reader of the properties file where to load the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void load(final Reader reader) {
    LOGGER.debug("Loading properties configuration from {}", reader);
    try {
      final Properties properties = new Properties();
      properties.load(reader);
      load(properties);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an properties.
   *
   * @param properties
   *          the properties where to load the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void load(final Properties properties) {
    removeAll();
    for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
      final String name = (String) entry.getKey();
      final String value = (String) entry.getValue();
      addString(name, value);
    }
  }

  /**
   * Stores this configuration to a output stream.
   * <p>
   * After calling this function, the stream is flushed but remains opened.
   *
   * @param out
   *          the output stream where to store the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void store(final OutputStream out) {
    LOGGER.debug("Storing properties configuration from {}", out);
    final Properties properties = store();
    try {
      PropertiesUtils.store(properties, out, charset, description);
    } catch (final IOException e) {
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
    LOGGER.debug("Storing properties configuration from {}", out);
    final Properties properties = store();
    try {
      PropertiesUtils.store(properties, out, charset, description);
    } catch (final IOException e) {
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
    LOGGER.debug("Storing properties configuration from {}", writer);
    final Properties properties = store();
    try {
      properties.store(writer, description);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    } finally {
      IoUtils.closeQuietly(writer);
    }
  }

  /**
   * Stores this configuration to an properties file.
   *
   * @param file
   *          the properties file where to store the configuration.
   * @throws ConfigurationError
   *           if any error occurs.
   */
  public void store(final File file) {
    LOGGER.debug("Storing properties configuration from {}", file);
    final Properties properties = store();
    OutputStream out = null;
    try {
      out = new FileOutputStream(file);
      properties.store(out, description);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    } finally {
      IoUtils.closeQuietly(out);
    }
  }

  /**
   * Stores this configuration to a properties.
   *
   * @return the properties storing this configuration.
   */
  public Properties store() {
    final Properties result = new Properties();
    for (final Map.Entry<String, DefaultProperty> entry : properties.entrySet()) {
      final String name = entry.getKey();
      final DefaultProperty property = entry.getValue();
      result.put(name, property.getValueAsString());
    }
    return result;
  }
}
