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
package com.github.haixing_hu.config;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.haixing_hu.config.impl.DefaultConfig;
import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.lang.SystemUtils;
import com.github.haixing_hu.text.xml.XmlException;

/**
 * Provides utility functions about the {@link Config} objects.
 *
 * @author Haixing Hu
 */
public final class ConfigUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultConfig.class);

  public static boolean choose(@Nullable final Boolean value,
      final Config config, final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getBoolean(name);
    }
  }

  public static boolean choose(@Nullable final Boolean value,
      final Config config, final String name, final boolean defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getBoolean(name, defaultValue);
    }
  }

  public static char choose(@Nullable final Character value,
      final Config config, final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getChar(name);
    }
  }

  public static char choose(@Nullable final Character value,
      final Config config, final String name, final char defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getChar(name, defaultValue);
    }
  }

  public static byte choose(@Nullable final Byte value, final Config config,
      final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getByte(name);
    }
  }

  public static byte choose(@Nullable final Byte value, final Config config,
      final String name, final byte defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getByte(name, defaultValue);
    }
  }

  public static short choose(@Nullable final Short value, final Config config,
      final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getShort(name);
    }
  }

  public static short choose(@Nullable final Short value, final Config config,
      final String name, final short defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getShort(name, defaultValue);
    }
  }

  public static int choose(@Nullable final Integer value, final Config config,
      final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getInt(name);
    }
  }

  public static int choose(@Nullable final Integer value, final Config config,
      final String name, final int defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getInt(name, defaultValue);
    }
  }

  public static long choose(@Nullable final Long value, final Config config,
      final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getLong(name);
    }
  }

  public static long choose(@Nullable final Long value, final Config config,
      final String name, final long defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getLong(name, defaultValue);
    }
  }

  public static float choose(@Nullable final Float value, final Config config,
      final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getFloat(name);
    }
  }

  public static float choose(@Nullable final Float value, final Config config,
      final String name, final float defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getFloat(name, defaultValue);
    }
  }

  public static double choose(@Nullable final Double value,
      final Config config, final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getDouble(name);
    }
  }

  public static double choose(@Nullable final Double value,
      final Config config, final String name, final double defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getDouble(name, defaultValue);
    }
  }

  public static String choose(@Nullable final String value,
      final Config config, final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getString(name);
    }
  }

  public static String choose(@Nullable final String value,
      final Config config, final String name, final String defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getString(name, defaultValue);
    }
  }

  public static Date choose(@Nullable final Date value, final Config config,
      final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getDate(name);
    }
  }

  public static Date choose(@Nullable final Date value, final Config config,
      final String name, @Nullable final Date defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getDate(name, defaultValue);
    }
  }

  public static BigInteger choose(@Nullable final BigInteger value,
      final Config config, final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getBigInteger(name);
    }
  }

  public static BigInteger choose(@Nullable final BigInteger value,
      final Config config, final String name,
      @Nullable final BigInteger defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getBigInteger(name, defaultValue);
    }
  }

  public static BigDecimal choose(@Nullable final BigDecimal value,
      final Config config, final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getBigDecimal(name);
    }
  }

  public static BigDecimal choose(@Nullable final BigDecimal value,
      final Config config, final String name,
      @Nullable final BigDecimal defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getBigDecimal(name, defaultValue);
    }
  }

  public static Class<?> choose(@Nullable final Class<?> value,
      final Config config, final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getClass(name);
    }
  }

  public static Class<?> choose(@Nullable final Class<?> value,
      final Config config, final String name,
      @Nullable final Class<?> defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getClass(name, defaultValue);
    }
  }

  public static <T> T choose(@Nullable final T value, final Config config,
      final String name) {
    if (value != null) {
      return value;
    } else {
      return config.<T>getInstance(name);
    }
  }

  public static <T> T choose(@Nullable final T value, final Config config,
      final String name, @Nullable final T defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.<T>getInstance(name, defaultValue);
    }
  }

  public static <T> T choose(@Nullable final T value, final Config config,
      final String name, @Nullable final Class<?> defaultClass) {
    if (value != null) {
      return value;
    } else {
      return config.<T>getInstance(name, defaultClass);
    }
  }

  public static <T> T choose(@Nullable final T value, final Config config,
      final String name, @Nullable final String defaultClassName) {
    if (value != null) {
      return value;
    } else {
      return config.<T>getInstance(name, defaultClassName);
    }
  }

  /**
   * Loads a configuration from the specified XML resource.
   * <p>
   * The function will search the XML resource in the following order:
   * </p>
   * <ol>
   * <li>First it will search the system's properties (using
   * {@link System#getProperty(String)}) for the specified property name, and if
   * the property was presented, the property value will be used as the XML
   * resource name.</li>
   * <li>If no such system property, the default XML resource name will be used.
   * </li>
   * </ol>
   * <p>
   * Then the function will try to load the configuration form the resource in
   * the context of the specified class. If success, the configuration is
   * returned; otherwise, an empty configuration is returned.
   * </p>
   * <p>
   * For example, suppose there is a system property
   * "com.github.haixing_hu.config" whose value is "ascent-commons.xml",
   * calling this function with the argument of
   * "com.github.haixing_hu.config" will gets a configuration load from
   * the resource "ascent-commons.xml".
   * </p>
   *
   * @param propertyName
   *          the name for the property in the system properties which specifies
   *          the resource name of the configuration. It could be null,
   *          indicating the function should use the default resource directly.
   * @param defaultResource
   *          the name of the default resource for the configuration. It can't
   *          be null.
   * @param clazz
   *          the class under whose context the resource will be searched.
   * @return the configuration load from that resource, or an empty
   *         configuration if failed.
   */
  public static Config loadXmlConfig(@Nullable final String propertyName,
      final String defaultResource, final Class<?> clazz) {
    String resource = null;
    if (propertyName != null) {
      resource = SystemUtils.getProperty(propertyName);
    }
    if (resource == null) {
      resource = defaultResource;
    }
    try {
      return XmlSerialization.deserialize(DefaultConfig.class, resource, clazz);
    } catch (final XmlException e) {
      LOGGER.warn("Failed to load the configuration from the resource: {}, " +
      		"use an empty configuration: {}", resource, e.getMessage());
      return new DefaultConfig();
    }
  }


}
