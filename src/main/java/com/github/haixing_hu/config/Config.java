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
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import com.github.haixing_hu.config.error.ConfigurationError;
import com.github.haixing_hu.config.impl.DefaultConfig;
import com.github.haixing_hu.lang.Cloneable;
import com.github.haixing_hu.lang.Type;

/**
 * A {@link Config} is a container of {@link Property}s.
 *
 * The {@link Config} interface does not provides functions to modify the
 * property values, since a {@link Config} object is intended to be create and
 * write once, then read multiple times.
 *
 * The implementation of {@link Config} interface may read configurations from
 * an XML file, a database, or other sources.
 *
 * @author Haixing Hu
 * @see DefaultConfig
 */
public interface Config extends Cloneable<Config> {

  /**
   * The regular expression pattern of variables substitution.
   */
  public static Pattern VARIABLE_PATTERN         =
      Pattern.compile("\\$\\{[^\\}\\$\u0020]+\\}");

  /**
   * The maximum depth of variables substitutions.
   */
  public static int MAX_SUBSTITUTION_DEPTH    = 64;

  /**
   * Gets the description of this configuration.
   *
   * @return
   *    the description of this configuration, or {@code null} if none.
   */
  public String getDescription();

  /**
   * Tests whether this Config object is empty.
   *
   * @return true if this Config object is empty; false otherwise.
   */
  public boolean isEmpty();

  /**
   * Gets the number of properties in this Config object.
   *
   * @return the number of properties in this Config object.
   */
  public int size();

  /**
   * Gets the collection of properties in this configuration.
   *
   * @return the collection of properties in this configuration.
   */
  public Collection<? extends Property> getProperties();

  /**
   * Gets the set of names of properties in this configuration.
   *
   * @return the set of names of properties in this configuration.
   */
  public Set<String> getNames();

  /**
   * Tests whether a specified property name is contained in this configuration.
   *
   * @param name
   *          a property name.
   * @return true if the specified property name is contained in this
   *         configuration; false otherwise.
   */
  public boolean contains(final String name);

  /**
   * Gets the {@link Property} with the specified name.
   *
   * @param name
   *          the name of the property to be get.
   * @return the {@link Property} with the specified name, or null if no
   *         property with the specified name.
   */
  public Property get(String name);

  /**
   * Sets the {@link Property}.
   *
   * @param property
   *          the property to be set. If there was a property with the same name
   *          as this property, the old property will be override.
   */
  //  public void set(Property property);

  /**
   * Gets the description of the property with the specified name.
   *
   * @param name
   *        the name of a property.
   * @return
   *        the description of the property with the specified name. Note that
   *        returning a null does not indicates there is no property with the
   *        specified name; instead, it indicates that there is a property with
   *        the specified name but has a null description.
   * @throws ConfigurationError
   *        if no property with the specified name.
   */
  public String getDescription(String name);

  /**
   * Gets the type of the property with the specified name.
   *
   * @param name
   *        the name of a property.
   * @return
   *        the type of the property with the specified name.
   * @throws ConfigurationError
   *        if no property with the specified name.
   */
  public Type getType(String name);

  /**
   * Tests whether the property with the specified name is final.
   *
   * @param name
   *        the name of a property.
   * @return
   *        true if the property with the specified name is final; false
   *        otherwise.
   * @throws ConfigurationError
   *        if no property with the specified name.
   */
  public boolean isFinal(String name);

  /**
   * Gets the number of values in the property with the specified name.
   *
   * @param name
   *        the name of a property.
   * @return
   *        the number of values in the property with the specified name. Note
   *        that if this configuration does not have the property with the specified
   *        name, or this configuration has a property with the specified name but
   *        the property has no value, the function returns 0.
   */
  public int getCount(String name);

  /**
   * Gets the value of the specified property as a {@code boolean} value.
   *
   * @param name
   *          the name of the specified property.
   * @return the value of the property with the specified name as a
   *         {@code boolean}.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code boolean}.
   */
  public boolean getBoolean(final String name);

  /**
   * Gets the value of the specified property as a {@code boolean} value.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValue
   *          the default values returned in case of there is no such property
   *          or the property has no value.
   * @return the value of the property with the specified name as a
   *         {@code boolean}; or the {@code defaultValue} if there is
   *         no such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code boolean}
   *           .
   */
  public boolean getBoolean(final String name, final boolean defaultValue);

  /**
   * Gets the values of the specified property as a array of
   * {@code boolean} values.
   *
   * @param name
   *          the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *         a {@code boolean} array.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code boolean}.
   */
  public boolean[] getBooleans(final String name);

  /**
   * Gets the values of the specified property as a array of
   * {@code boolean} values.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValues
   *          the default values returned in case of there is no such property
   *          or the property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *         a {@code boolean} array; or the {@code defaultValues} if
   *         there is no such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code boolean}
   *           .
   */
  public boolean[] getBooleans(final String name,
      @Nullable final boolean[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code char} value.
   *
   * @param name
   *          the name of the specified property.
   * @return the value of the property with the specified name as a
   *         {@code char}.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code char}.
   */
  public char getChar(final String name);

  /**
   * Gets the value of the specified property as a {@code char} value.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValue
   *          the default value returned in case of there is no such property or
   *          the property has no value. It could be null.
   * @return the value of the property with the specified name as a
   *         {@code char}; or the {@code defaultValue} if there is no
   *         such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code char}.
   */
  public char getChar(final String name, final char defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code char}
   * values.
   *
   * @param name
   *          the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *         a {@code char} array.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code char}.
   */
  public char[] getChars(final String name);

  /**
   * Gets the values of the specified property as a array of {@code char}
   * values.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValues
   *          the default values returned in case of there is no such property
   *          or the property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *         a {@code char} array; or the {@code defaultValues} if
   *         there is no such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code char}.
   */
  public char[] getChars(final String name, @Nullable final char[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code byte} value.
   *
   * @param name
   *          the name of the specified property.
   * @return the value of the property with the specified name as a
   *         {@code byte}.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code byte}.
   */
  public byte getByte(final String name);

  /**
   * Gets the value of the specified property as a {@code byte} value.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValue
   *          the default value returned in case of there is no such property or
   *          the property has no value. It could be null.
   * @return the value of the property with the specified name as a
   *         {@code byte}; or the {@code defaultValue} if there is no
   *         such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code byte}.
   */
  public byte getByte(final String name, final byte defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code byte}
   * values.
   *
   * @param name
   *          the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *         a {@code byte} array.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code byte}.
   */
  public byte[] getBytes(final String name);

  /**
   * Gets the values of the specified property as a array of {@code byte}
   * values.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValues
   *          the default values returned in case of there is no such property
   *          or the property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *         a {@code byte} array; or the {@code defaultValues} if
   *         there is no such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code byte}.
   */
  public byte[] getBytes(final String name, @Nullable final byte[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code short} value.
   *
   * @param name
   *          the name of the specified property.
   * @return the value of the property with the specified name as a
   *         {@code short}.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code short}.
   */
  public short getShort(final String name);

  /**
   * Gets the value of the specified property as a {@code short} value.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValue
   *          the default value returned in case of there is no such property or
   *          the property has no value. It could be null.
   * @return the value of the property with the specified name as a
   *         {@code short}; or the {@code defaultValue} if there is no
   *         such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code short}.
   */
  public short getShort(final String name, final short defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code short}
   * values.
   *
   * @param name
   *          the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *         a {@code short} array.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code short}.
   */
  public short[] getShorts(final String name);

  /**
   * Gets the values of the specified property as a array of {@code short}
   * values.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValues
   *          the default values returned in case of there is no such property
   *          or the property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *         a {@code short} array; or the {@code defaultValues} if
   *         there is no such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code short}.
   */
  public short[] getShorts(final String name,
      @Nullable final short[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code int} value.
   *
   * @param name
   *          the name of the specified property.
   * @return the value of the property with the specified name as a
   *         {@code int}.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code int}.
   */
  public int getInt(final String name);

  /**
   * Gets the value of the specified property as a {@code int} value.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValue
   *          the default value returned in case of there is no such property or
   *          the property has no value. It could be null.
   * @return the value of the property with the specified name as a
   *         {@code int}; or the {@code defaultValue} if there is no
   *         such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code int}.
   */
  public int getInt(final String name, final int defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code int}
   * values.
   *
   * @param name
   *          the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *         a {@code int} array.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code int}.
   */
  public int[] getInts(final String name);

  /**
   * Gets the values of the specified property as a array of {@code int}
   * values.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValues
   *          the default values returned in case of there is no such property
   *          or the property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *         a {@code int} array; or the {@code defaultValues} if
   *         there is no such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code int}.
   */
  public int[] getInts(final String name, @Nullable final int[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code long} value.
   *
   * @param name
   *          the name of the specified property.
   * @return the value of the property with the specified name as a
   *         {@code long}.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code long}.
   */
  public long getLong(final String name);

  /**
   * Gets the value of the specified property as a {@code long} value.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValue
   *          the default value returned in case of there is no such property or
   *          the property has no value. It could be null.
   * @return the value of the property with the specified name as a
   *         {@code long}; or the {@code defaultValue} if there is no
   *         such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code long}.
   */
  public long getLong(final String name, final long defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code long}
   * values.
   *
   * @param name
   *          the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *         a {@code long} array.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code long}.
   */
  public long[] getLongs(final String name);

  /**
   * Gets the values of the specified property as a array of {@code long}
   * values.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValues
   *          the default values returned in case of there is no such property
   *          or the property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *         a {@code long} array; or the {@code defaultValues} if
   *         there is no such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code long}.
   */
  public long[] getLongs(final String name, @Nullable final long[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code float} value.
   *
   * @param name
   *          the name of the specified property.
   * @return the value of the property with the specified name as a
   *         {@code float}.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code float}.
   */
  public float getFloat(final String name);

  /**
   * Gets the value of the specified property as a {@code float} value.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValue
   *          the default value returned in case of there is no such property or
   *          the property has no value. It could be null.
   * @return the value of the property with the specified name as a
   *         {@code float}; or the {@code defaultValue} if there is no
   *         such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code float}.
   */
  public float getFloat(final String name, final float defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code float}
   * values.
   *
   * @param name
   *          the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *         a {@code float} array.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code float}.
   */
  public float[] getFloats(final String name);

  /**
   * Gets the values of the specified property as a array of {@code float}
   * values.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValues
   *          the default values returned in case of there is no such property
   *          or the property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *         a {@code float} array; or the {@code defaultValues} if
   *         there is no such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code float}.
   */
  public float[] getFloats(final String name,
      @Nullable final float[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code double} value.
   *
   * @param name
   *          the name of the specified property.
   * @return the value of the property with the specified name as a
   *         {@code double}.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code double}.
   */
  public double getDouble(final String name);

  /**
   * Gets the value of the specified property as a {@code double} value.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValue
   *          the default value returned in case of there is no such property or
   *          the property has no value. It could be null.
   * @return the value of the property with the specified name as a
   *         {@code double}; or the {@code defaultValue} if there is
   *         no such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code double}.
   */
  public double getDouble(final String name, final double defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code double}
   * values.
   *
   * @param name
   *          the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *         a {@code double} array.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code double}.
   */
  public double[] getDoubles(final String name);

  /**
   * Gets the values of the specified property as a array of {@code double}
   * values.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValues
   *          the default values returned in case of there is no such property
   *          or the property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *         a {@code double} array; or the {@code defaultValues} if
   *         there is no such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code double}.
   */
  public double[] getDoubles(final String name,
      @Nullable final double[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code String} value.
   *
   * @param name
   *          the name of the specified property.
   * @return the value of the property with the specified name as a
   *         {@code String}.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code String}.
   * @see getString(String)
   */
  public String getRawString(final String name);

  /**
   * Gets the value of the specified property as a {@code String} value.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValue
   *          the default value returned in case of there is no such property or
   *          the property has no value. It could be null.
   * @return the value of the property with the specified name as a
   *         {@code String}; or the {@code defaultValue} if there is
   *         no such property or the property has no value. Note that if the
   *         {@code defaultValue} is returned, it is also substituted.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code String}.
   * @see getString(String, String)
   */
  public String getRawString(final String name, @Nullable final String defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code String}
   * values.
   *
   * @param name
   *          the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *         a {@code String} array.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code String}.
   * @see getStrings(String)
   */
  public String[] getRawStrings(final String name);

  /**
   * Gets the values of the specified property as a array of {@code String}
   * values.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValues
   *          the default values returned in case of there is no such property
   *          or the property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *         a {@code String} array; or the {@code defaultValues} if
   *         there is no such property or the property has no value. Note that
   *         if the {@code defaultValues} is returned, it is also
   *         substituted.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code String}.
   * @see getStrings(String, String[])
   */
  public String[] getRawStrings(final String name,
      @Nullable final String[] defaultValues);

  /**
   * Gets the value of the specified property as a substituted
   * {@code String} value.
   *
   * That is, if the property value of the specified name contains substitutable
   * variables, in the form of {@code ${var_name}}, the variable will be
   * substituted by the property value of that name or the system property value
   * of that name.
   *
   * The substitution may be recursive, but the maximum depth of the
   * substitution is {@link MAX_SUBSTITUTION_DEPTH}.
   *
   * @param name
   *          the name of the specified property.
   * @return the value of the property with the specified name as a substituted
   *         {@code String}.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code String}.
   * @see getRawString(String)
   */
  public String getString(final String name);

  /**
   * Gets the value of the specified property as a substituted
   * {@code String} value.
   *
   * That is, if the property value of the specified name contains substitutable
   * variables, in the form of {@code ${var_name}}, the variable will be
   * substituted by the property value of that name or the system property value
   * of that name.
   *
   * The substitution may be recursive, but the maximum depth of the
   * substitution is {@link MAX_SUBSTITUTION_DEPTH}.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValue
   *          the default value returned in case of there is no such property or
   *          the property has no value. It could be null.
   * @return the value of the property with the specified name as a substituted
   *         {@code String}; or the {@code defaultValue} if there is
   *         no such property or the property has no value. Note that if the
   *         {@code defaultValue} is returned, it is also substituted.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code String}.
   * @see getRawString(String, String)
   */
  public String getString(final String name, @Nullable final String defaultValue);

  /**
   * Gets the values of the specified property as a array of substituted
   * {@code String} values.
   *
   * That is, if the property value of the specified name contains substitutable
   * variables, in the form of {@code ${var_name}}, the variable will be
   * substituted by the property value of that name or the system property value
   * of that name.
   *
   * The substitution may be recursive, but the maximum depth of the
   * substitution is {@link MAX_SUBSTITUTION_DEPTH}.
   *
   * @param name
   *          the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *         a substituted {@code String} array.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code String}.
   * @see getRawStrings(String)
   */
  public String[] getStrings(final String name);

  /**
   * Gets the values of the specified property as a array of substituted
   * {@code String} values.
   *
   * That is, if the property value of the specified name contains substitutable
   * variables, in the form of {@code ${var_name}}, the variable will be
   * substituted by the property value of that name or the system property value
   * of that name.
   *
   * The substitution may be recursive, but the maximum depth of the
   * substitution is {@link MAX_SUBSTITUTION_DEPTH}.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValues
   *          the default values returned in case of there is no such property
   *          or the property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *         a substituted {@code String} array; or the
   *         {@code defaultValues} if there is no such property or the
   *         property has no value. Note that if the {@code defaultValues}
   *         is returned, it is also substituted.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code String}.
   * @see getRawStrings(String, String[])
   */
  public String[] getStrings(final String name,
      @Nullable final String[] defaultValues);

  /**
   * Substitutes all substitutable variables in a {@code String}, using
   * the property values in this configuration and the system property values.
   *
   * The substitution may be recursive, but the maximum depth of the
   * substitution is {@link MAX_SUBSTITUTION_DEPTH}.
   *
   * @param value
   *    the {@code String} value to be substituted. It could be null.
   * @return
   *    the substituted result of the specified string; or null if the
   *    specified string is null.
   */
  public String substitute(@Nullable final String value);

  /**
   * Gets the value of the specified property as a {@code BigDecimal}
   * object.
   *
   * If the specified property does not exists or has no value, a
   * {@code ConfigurationError} is thrown. If the specified property exists
   * and has more than one value, the first value (earliest added into this
   * configuration) is returned.
   *
   * @param name
   *          the name of the specified property.
   * @return the value of the property with the specified name as a
   *         {@code BigDecimal} object.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code BigDecimal}.
   */
  public BigDecimal getBigDecimal(final String name);

  /**
   * Gets the value of the specified property as a {@code BigDecimal}
   * object.
   *
   * If the specified property does not exists or has no value, the specified
   * default value is returned. If the specified property exists and has more
   * than one value, the first value (earliest added into this configuration) is
   * returned.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValue
   *          the default value returned in case of there is no such property or
   *          the property has no value. It could be null.
   * @return the value of the property with the specified name as a
   *         {@code BigDecimal}; or the {@code defaultValue} if there
   *         is no such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not
   *           {@code BigDecimal}.
   */
  public BigDecimal getBigDecimal(final String name,
      @Nullable final BigDecimal defaultValue);

  /**
   * Gets the values of the specified property as a array of
   * {@code BigDecimal} values. If the specified property does not exists
   * or has no value, a {@code ConfigurationError} is thrown.
   *
   * @param name
   *          the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *         a {@code BigDecimal} array.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the property
   *           has no value, or the type of the property is not
   *           {@code BigDecimal}.
   */
  public BigDecimal[] getBigDecimals(final String name);

  /**
   * Gets the values of the specified property as a array of
   * {@code BigDecimal} values. If the specified property does not exists
   * or has no value, a {@code ConfigurationError} is thrown.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValues
   *          the default values returned in case of there is no such property
   *          or the property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *         a {@code BigDecimal} array; or the {@code defaultValues}
   *         if there is no such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not
   *           {@code BigDecimal}.
   */
  public BigDecimal[] getBigDecimals(final String name,
      @Nullable final BigDecimal[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code BigInteger}
   * value.
   *
   * @param name
   *          the name of the specified property.
   * @return the value of the property with the specified name as a
   *         {@code BigInteger}.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code BigInteger}.
   */
  public BigInteger getBigInteger(final String name);

  /**
   * Gets the value of the specified property as a {@code BigInteger}
   * value.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValue
   *          the default values returned in case of there is no such property
   *          or the property has no value. It could be null.
   * @return the value of the property with the specified name as a
   *         {@code BigInteger}; or the {@code defaultValue} if there
   *         is no such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not
   *           {@code BigInteger}.
   */
  public BigInteger getBigInteger(final String name,
      @Nullable final BigInteger defaultValue);

  /**
   * Gets the values of the specified property as a array of
   * {@code BigInteger} values.
   *
   * @param name
   *          the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *         a {@code BigInteger} array.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code BigInteger}.
   */
  public BigInteger[] getBigIntegers(final String name);

  /**
   * Gets the values of the specified property as a array of
   * {@code BigInteger} values.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValues
   *          the default values returned in case of there is no such property
   *          or the property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *         a {@code BigInteger} array; or the {@code defaultValues}
   *         if there is no such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not
   *           {@code BigInteger}.
   */
  public BigInteger[] getBigIntegers(final String name,
      @Nullable final BigInteger[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code Date} value.
   *
   * @param name
   *          the name of the specified property.
   * @return the value of the property with the specified name as a
   *         {@code Date}.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code Date}.
   */
  public Date getDate(final String name);

  /**
   * Gets the value of the specified property as a {@code Date} value.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValue
   *          the default value returned in case of there is no such property or
   *          the property has no value. It could be null.
   * @return the value of the property with the specified name as a
   *         {@code Date}; or the {@code defaultValue} if there is no
   *         such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code Date}.
   */
  public Date getDate(final String name, @Nullable final Date defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code Date}
   * values.
   *
   * @param name
   *          the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *         a {@code Date} array.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code Date}.
   */
  public Date[] getDates(final String name);

  /**
   * Gets the values of the specified property as a array of {@code Date}
   * values.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValues
   *          the default values returned in case of there is no such property
   *          or the property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *         a {@code Date} array; or the {@code defaultValues} if
   *         there is no such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code Date}.
   */
  public Date[] getDates(final String name, @Nullable final Date[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code byte[]} value.
   *
   * @param name
   *          the name of the specified property.
   * @return the value of the property with the specified name as a
   *         {@code byte[]}.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code byte[]}.
   */
  public byte[] getByteArray(final String name);

  /**
   * Gets the value of the specified property as a {@code byte[]} value.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValue
   *          the default value returned in case of there is no such property or
   *          the property has no value. It could be null.
   * @return the value of the property with the specified name as a
   *         {@code byte[]}; or the {@code defaultValue} if there is
   *         no such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code byte[]}.
   */
  public byte[] getByteArray(final String name,
      @Nullable final byte[] defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code byte[]}
   * values.
   *
   * @param name
   *          the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *         a {@code byte[]} array.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code byte[]}.
   */
  public byte[][] getByteArrays(final String name);

  /**
   * Gets the values of the specified property as a array of {@code byte[]}
   * values.
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValues
   *          the default values returned in case of there is no such property
   *          or the property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *         a {@code byte[]} array; or the {@code defaultValues} if
   *         there is no such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code byte[]}.
   */
  public byte[][] getByteArrays(final String name,
      @Nullable final byte[][] defaultValues);

  /**
   * Gets the value of the specified property as a {@code Class} value.
   *
   * <p>Note: the type of the specified property must be either {@link Type#CLASS}
   * or {@link Type#STRING}.</p>
   *
   * @param name
   *          the name of the specified property.
   * @return the value of the property with the specified name as a
   *         {@code Class}.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code Class}.
   */
  public Class<?> getClass(final String name);

  /**
   * Gets the value of the specified property as a {@code Class} value.
   *
   * <p>Note: the type of the specified property must be either {@link Type#CLASS}
   * or {@link Type#STRING}.</p>
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValue
   *          the default value returned in case of there is no such property or
   *          the property has no value. It could be null.
   * @return the value of the property with the specified name as a
   *         {@code Class}; or the {@code defaultValue} if there is no
   *         such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code Class}
   *           nor {@code String}.
   */
  public Class<?> getClass(final String name,
      @Nullable final Class<?> defaultValue);

  /**
   * Gets the value of the specified property as a {@code Class} value.
   *
   * <p>Note: the type of the specified property must be either {@link Type#CLASS}
   * or {@link Type#STRING}.</p>
   *
   * @param name
   *          the name of the specified property.
   * @param defaultClassName
   *          the name of default class returned in case of there is no such
   *          property or the property has no value. It could be null.
   * @return the value of the property with the specified name as a
   *         {@code Class}; or the {@code defaultValue} if there is no
   *         such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code Class}
   *           nor {@code String}, or there is no such property or the
   *           property has no value, but the class with the name of
   *           {@code defaultClass} can not be created.
   */
  public Class<?> getClass(final String name,
      @Nullable final String defaultClassName);

  /**
   * Gets the values of the specified property as a array of {@code Class}
   * values.
   *
   * <p>Note: the type of the specified property must be either {@link Type#CLASS}
   * or {@link Type#STRING}.</p>
   *
   * @param name
   *          the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *         a {@code Class} array.
   * @throws ConfigurationError
   *           if there is no property with the specified name, or the specified
   *           property has no value, or the type of the specified property is
   *           not {@code Class}.
   */
  public Class<?>[] getClasses(final String name);

  /**
   * Gets the values of the specified property as a array of {@code Class}
   * values.
   *
   * <p>Note: the type of the specified property must be either {@link Type#CLASS}
   * or {@link Type#STRING}.</p>
   *
   * @param name
   *          the name of the specified property.
   * @param defaultValues
   *          the default values returned in case of there is no such property
   *          or the property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *         a {@code Class} array; or the {@code defaultValues} if
   *         there is no such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code Class}.
   */
  public Class<?>[] getClasses(final String name,
      @Nullable final Class<?>[] defaultValues);

  /**
   * Gets the values of the specified property as a array of {@code Class}
   * values.
   *
   * <p>Note: the type of the specified property must be either {@link Type#CLASS}
   * or {@link Type#STRING}.</p>
   *
   * @param name
   *          the name of the specified property.
   * @param defaultClassNames
   *          the names of default classes returned in case of there is no such
   *          property or the property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *         a {@code Class} array; or the {@code defaultValues} if
   *         there is no such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code Class},
   *           or there is no such property or the property has no value, but
   *           the class with the name of {@code defaultClass} can not be
   *           created.
   */
  public Class<?>[] getClasses(final String name,
      @Nullable final String[] defaultClassNames);

  /**
   * Gets the value of the specified property as a {@code Class} value,
   * then creates a instance of that class and returns it.
   *
   * @param T
   *          the type of the objects to be created and returned.
   * @param name
   *          the name of the specified property.
   * @return an instance of the {@code Class} value of the property with
   *         the specified name.
   * @throws ConfigurationError
   *           if there is no property with the specified name; or the specified
   *           property has a list of values; or the type of the specified
   *           property is not {@code Class}; or the instance of the
   *           {@code Class} can not be created.
   */
  public <T> T getInstance(final String name);

  /**
   * Gets the value of the specified property as a {@code Class} value,
   * then creates a instance of that class and returns it.
   *
   * @param T
   *          the type of the objects to be created and returned.
   * @param name
   *          the name of the specified property.
   * @param defaultClass
   *          the class of the default value returned in case of there is no
   *          such property or the property has no value. It could be null.
   * @return an instance of the {@code Class} value of the property with
   *         the specified name; or a instance of the {@code defaultClass}
   *         if there is no such property or the property has no value; or null
   *         if there is no such property or the property has no value and the
   *         default class is null.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code Class};
   *           or the instance of the {@code Class} value of the specified
   *           property can not be created.
   */
  public <T> T getInstance(final String name,
      @Nullable final Class<?> defaultClass);

  /**
   * Gets the value of the specified property as a {@code Class} value,
   * then creates a instance of that class and returns it.
   *
   * @param T
   *          the type of the objects to be created and returned.
   * @param name
   *          the name of the specified property.
   * @param defaultClassName
   *          the class name of the default value returned in case of there is
   *          no such property or the property has no value. It could be null.
   * @return an instance of the {@code Class} value of the property with
   *         the specified name; or a instance of the class with the
   *         {@code defaultClassName} if there is no such property or the
   *         property has no value; or null if there is no such property or the
   *         property has no value and the default class name is null.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code Class};
   *           or the instance of the {@code Class} value of the specified
   *           property can not be created.
   */
  public <T> T getInstance(final String name,
      @Nullable final String defaultClassName);

  /**
   * Gets the value of the specified property as a {@code Class} value,
   * then creates a instance of that class and returns it.
   *
   * @param T
   *          the type of the objects to be created and returned.
   * @param name
   *          the name of the specified property.
   * @param defaultValue
   *          the default value returned in case of there is no such property or
   *          the property has no value. It could be null.
   * @return an instance of the {@code Class} value of the property with
   *         the specified name; or the {@code defaultValue} if there is no
   *         such property or the property has no value; or null if there is no
   *         such property or the property has no value and the default class
   *         name is null.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code Class};
   *           or the instance of the {@code Class} value of the specified
   *           property can not be created.
   */
  public <T> T getInstance(final String name, @Nullable final T defaultValue);

  /**
   * Gets the values of the specified property as a {@code Class} array,
   * then creates a instance of each class in the array and returns the object
   * array.
   *
   * @param T
   *          the type of the objects to be created.
   * @param name
   *          the name of the specified property.
   * @param clazz
   *          the class object of the type T. It is required to create the array
   *          of type T.
   * @return an array of instances of the {@code Class} values of the
   *         property with the specified name.
   * @throws ConfigurationError
   *           if there is no property with the specified name; or the specified
   *           property has a list of values; or the type of the specified
   *           property is not {@code Class}; or the instance of the
   *           {@code Class} can not be created.
   */
  public <T> T[] getInstances(final String name, final Class<?> clazz);

  /**
   * Gets the values of the specified property as a {@code Class} array,
   * then creates a instance of each class in the array and returns the object
   * array.
   *
   * @param T
   *          the type of the objects to be created.
   * @param name
   *          the name of the specified property.
   * @param clazz
   *          the class object of the type T. It is required to create the array
   *          of type T.
   * @param defaultClasses
   *          the classes of the default values returned in case of there is no
   *          such property or the property has no value. It could be null.
   * @return an array of instances of the {@code Class} values of the
   *         property with the specified name; or a instance of the
   *         {@code defaultClass} if there is no such property or the
   *         property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code Class};
   *           or the instance of the {@code Class} value of the specified
   *           property can not be created.
   */
  public <T> T[] getInstances(final String name, final Class<?> clazz,
      @Nullable final Class<?>[] defaultClasses);


  /**
   * Gets the values of the specified property as a {@code Class} array,
   * then creates a instance of each class in the array and returns the object
   * array.
   *
   * @param T
   *          the type of the objects to be created.
   * @param name
   *          the name of the specified property.
   * @param clazz
   *          the class object of the type T. It is required to create the array
   *          of type T.
   * @param defaultClassNames
   *          the class names of the default values returned in case of there is no
   *          such property or the property has no value. It could be null.
   * @return an array of instances of the {@code Class} values of the
   *         property with the specified name; or a instance of the class with the
   *         {@code defaultClassName} if there is no such property or the
   *         property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code Class};
   *           or the instance of the {@code Class} value of the specified
   *           property can not be created.
   */
  public <T> T[] getInstances(final String name, final Class<?> clazz,
      @Nullable final String[] defaultClassNames);

  /**
   * Gets the values of the specified property as a {@code Class} array,
   * then creates a instance of each class in the array and returns the object
   * array.
   *
   * @param T
   *          the type of the objects to be created.
   * @param name
   *          the name of the specified property.
   * @param clazz
   *          the class object of the type T. It is required to create the array
   *          of type T.
   * @param defaultValues
   *          the default values returned in case of there is no such property
   *          or the property has no value. It could be null.
   * @return an array of instances of the {@code Class} values of the
   *         property with the specified name; or the {@code defaultValues}
   *         if there is no such property or the property has no value.
   * @throws ConfigurationError
   *           if the type of the specified property is not {@code Class};
   *           or the instance of the {@code Class} value of the specified
   *           property can not be created.
   */
  public <T> T[] getInstances(final String name, final Class<?> clazz,
      @Nullable final T[] defaultValues);
}
