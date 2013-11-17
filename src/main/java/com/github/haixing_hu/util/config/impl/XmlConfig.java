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
import java.io.PrintStream;
import java.io.Writer;
import java.net.URI;
import java.net.URL;

import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.net.Url;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.util.config.Config;
import com.github.haixing_hu.util.config.error.ConfigurationError;

/**
 * A {@link Config} object loading configurations from XML files.
 *
 * @author Haixing Hu
 */
public final class XmlConfig extends DefaultConfig {

  private static final long serialVersionUID = - 2988726634410349164L;

  public XmlConfig() {
    super();
  }

  public XmlConfig(final String resource) {
    super();
    load(resource);
  }

  public XmlConfig(final String resource, final Class<?> resourceClass) {
    super();
    load(resource, resourceClass);
  }

  public XmlConfig(final String resource, final ClassLoader resourceClassLoader) {
    super();
    load(resource, resourceClassLoader);
  }

  public XmlConfig(final Url url) {
    super();
    load(url);
  }

  public XmlConfig(final URL url) {
    super();
    load(url);
  }

  public XmlConfig(final URI uri) {
    super();
    load(uri);
  }

  public XmlConfig(final File file) {
    super();
    load(file);
  }

  public void load(final String resource) {
    LOGGER.debug("Loading XML configuration from {}", resource);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class, resource,
          XmlConfig.class.getClassLoader());
      this.properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  public void load(final String resource, final Class<?> resourceClass) {
    LOGGER.debug("Loading XML configuration from {}", resource);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class, resource,
          resourceClass);
      this.properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  public void load(final String resource, final ClassLoader resourceClassLoader) {
    LOGGER.debug("Loading XML configuration from {}", resource);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class, resource,
          resourceClassLoader);
      this.properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  public void load(final Url url) {
    LOGGER.debug("Loading XML configuration from {}", url);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class, url);
      this.properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  public void load(final URL url) {
    LOGGER.debug("Loading XML configuration from {}", url);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class, url);
      this.properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  public void load(final URI uri) {
    try {
      final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class, uri);
      this.properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  public void load(final File file) {
    LOGGER.debug("Loading XML configuration from {}", file);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class, file);
      this.properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  public void store(final PrintStream out) {
    LOGGER.debug("Storing XML configuration from {}", out);
    try {
      XmlSerialization.serialize(DefaultConfig.class, this, out);
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  public void store(final Writer writer) {
    LOGGER.debug("Storing XML configuration from {}", writer);
    try {
      XmlSerialization.serialize(DefaultConfig.class, this, writer);
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  public void store(final File file) {
    LOGGER.debug("Storing XML configuration from {}", file);
    try {
      XmlSerialization.serialize(DefaultConfig.class, this, file);
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }
}
