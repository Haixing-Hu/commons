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

package com.github.haixing_hu.util;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Nullable;

import com.github.haixing_hu.lang.ArrayUtils;
import com.github.haixing_hu.lang.EnumUtils;
import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.text.BooleanFormat;
import com.github.haixing_hu.text.NumberFormat;
import com.github.haixing_hu.util.config.error.ConfigurationError;
import com.github.haixing_hu.util.config.error.InvalidPropertyValueError;
import com.github.haixing_hu.util.config.error.PropertyNotExistError;

/**
 * Provides functions dealing with the {@link java.util.Properties} class.
 *
 * @author Haixing Hu
 */
public final class PropertiesUtils {

  public static String[][] toArray(final Properties props) {
    String[][] result = new String[props.size()][2];
    int i = 0;
    for (Map.Entry<Object, Object> entry : props.entrySet()) {
      String key = (String) entry.getKey();
      String value = (String) entry.getValue();
      result[i][0] = key;
      result[i][1] = value;
      ++i;
    }
    return result;
  }

  public static Properties fromArray(final String[][] array) {
    Properties result = new Properties();
    for (int i = 0; i < array.length; ++i) {
      String key = array[i][0];
      String value = array[i][1];
      result.put(key, value);
    }
    return result;
  }

  public static boolean getBoolean(final Properties props, final String name)
      throws ConfigurationError {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      throw new PropertyNotExistError(name);
    }
    final BooleanFormat bf = new BooleanFormat();
    final boolean result = bf.parse(strValue);
    if (bf.fail()) {
      throw new InvalidPropertyValueError(name, strValue);
    }
    return result;
  }

  public static boolean getBoolean(final Properties props, final String name,
      final boolean defaultValue) {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      return defaultValue;
    }
    final BooleanFormat bf = new BooleanFormat();
    final boolean result = bf.parse(strValue);
    if (bf.fail()) {
      return defaultValue;
    } else {
      return result;
    }
  }

  public static boolean[] getBooleans(final Properties props, final String name,
      final char separator) throws ConfigurationError {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      throw new PropertyNotExistError(name);
    }
    final List<String> strValues = StringUtils.split(strValue, separator,
        true, true, null);
    if ((strValues == null) || strValues.isEmpty()) {
      return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
    }
    final boolean[] result = new boolean[strValues.size()];
    final BooleanFormat bf = new BooleanFormat();
    int index = 0;
    for (final String str : strValues) {
      final boolean value = bf.parse(str);
      if (bf.fail()) {
        throw new InvalidPropertyValueError(name, strValue);
      }
      result[index++] = value;
    }
    return result;
  }

  public static boolean[] getBooleans(final Properties props, final String name,
      final char separator, @Nullable final boolean[] defaultValues) {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      return defaultValues;
    }
    final List<String> strValues = StringUtils.split(strValue, separator,
        true, true, null);
    if ((strValues == null) || strValues.isEmpty()) {
      return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
    }
    final boolean[] result = new boolean[strValues.size()];
    final BooleanFormat bf = new BooleanFormat();
    int index = 0;
    for (final String str : strValues) {
      final boolean value = bf.parse(str);
      if (bf.fail()) {
        return defaultValues;
      }
      result[index++] = value;
    }
    return result;
  }

  public static byte getByte(final Properties props, final String name)
      throws ConfigurationError {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      throw new PropertyNotExistError(name);
    }
    final NumberFormat nf = new NumberFormat();
    final byte result = nf.parseByte(strValue);
    if (nf.fail()) {
      throw new InvalidPropertyValueError(name, strValue);
    }
    return result;
  }

  public static byte getByte(final Properties props, final String name,
      final byte defaultValue) {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final byte result = nf.parseByte(strValue);
    if (nf.fail()) {
      return defaultValue;
    } else {
      return result;
    }
  }

  public static byte[] getBytes(final Properties props, final String name,
      final char separator) throws ConfigurationError {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      throw new PropertyNotExistError(name);
    }
    final List<String> strValues = StringUtils.split(strValue, separator,
        true, true, null);
    if ((strValues == null) || strValues.isEmpty()) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    }
    final byte[] result = new byte[strValues.size()];
    final NumberFormat nf = new NumberFormat();
    int index = 0;
    for (final String str : strValues) {
      final byte value = nf.parseByte(str);
      if (nf.fail()) {
        throw new InvalidPropertyValueError(name, strValue);
      }
      result[index++] = value;
    }
    return result;
  }

  public static byte[] getBytes(final Properties props, final String name,
      final char separator, @Nullable final byte[] defaultValues) {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      return defaultValues;
    }
    final List<String> strValues = StringUtils.split(strValue, separator,
        true, true, null);
    if ((strValues == null) || strValues.isEmpty()) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    }
    final byte[] result = new byte[strValues.size()];
    final NumberFormat nf = new NumberFormat();
    int index = 0;
    for (final String str : strValues) {
      final byte value = nf.parseByte(str);
      if (nf.fail()) {
        return defaultValues;
      }
      result[index++] = value;
    }
    return result;
  }

  public static short getShort(final Properties props, final String name)
      throws ConfigurationError {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      throw new PropertyNotExistError(name);
    }
    final NumberFormat nf = new NumberFormat();
    final short result = nf.parseShort(strValue);
    if (nf.fail()) {
      throw new InvalidPropertyValueError(name, strValue);
    }
    return result;
  }

  public static short getShort(final Properties props, final String name,
      final short defaultValue) {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final short result = nf.parseShort(strValue);
    if (nf.fail()) {
      return defaultValue;
    } else {
      return result;
    }
  }

  public static short[] getShorts(final Properties props, final String name,
      final char separator) throws ConfigurationError {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      throw new PropertyNotExistError(name);
    }
    final List<String> strValues = StringUtils.split(strValue, separator,
        true, true, null);
    if ((strValues == null) || strValues.isEmpty()) {
      return ArrayUtils.EMPTY_SHORT_ARRAY;
    }
    final short[] result = new short[strValues.size()];
    final NumberFormat nf = new NumberFormat();
    int index = 0;
    for (final String str : strValues) {
      final short value = nf.parseShort(str);
      if (nf.fail()) {
        throw new InvalidPropertyValueError(name, strValue);
      }
      result[index++] = value;
    }
    return result;
  }

  public static short[] getShorts(final Properties props, final String name,
      final char separator, @Nullable final short[] defaultValues) {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      return defaultValues;
    }
    final List<String> strValues = StringUtils.split(strValue, separator,
        true, true, null);
    if ((strValues == null) || strValues.isEmpty()) {
      return ArrayUtils.EMPTY_SHORT_ARRAY;
    }
    final short[] result = new short[strValues.size()];
    final NumberFormat nf = new NumberFormat();
    int index = 0;
    for (final String str : strValues) {
      final short value = nf.parseShort(str);
      if (nf.fail()) {
        return defaultValues;
      }
      result[index++] = value;
    }
    return result;
  }

  public static int getInt(final Properties props, final String name)
      throws ConfigurationError {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      throw new PropertyNotExistError(name);
    }
    final NumberFormat nf = new NumberFormat();
    final int result = nf.parseInt(strValue);
    if (nf.fail()) {
      throw new InvalidPropertyValueError(name, strValue);
    }
    return result;
  }

  public static int getInt(final Properties props, final String name,
      final int defaultValue) {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final int result = nf.parseInt(strValue);
    if (nf.fail()) {
      return defaultValue;
    } else {
      return result;
    }
  }

  public static int[] getInts(final Properties props, final String name,
      final char separator) throws ConfigurationError {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      throw new PropertyNotExistError(name);
    }
    final List<String> strValues = StringUtils.split(strValue, separator,
        true, true, null);
    if ((strValues == null) || strValues.isEmpty()) {
      return ArrayUtils.EMPTY_INT_ARRAY;
    }
    final int[] result = new int[strValues.size()];
    final NumberFormat nf = new NumberFormat();
    int index = 0;
    for (final String str : strValues) {
      final int value = nf.parseInt(str);
      if (nf.fail()) {
        throw new InvalidPropertyValueError(name, strValue);
      }
      result[index++] = value;
    }
    return result;
  }

  public static int[] getInts(final Properties props, final String name,
      final char separator, @Nullable final int[] defaultValues) {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      return defaultValues;
    }
    final List<String> strValues = StringUtils.split(strValue, separator,
        true, true, null);
    if ((strValues == null) || strValues.isEmpty()) {
      return ArrayUtils.EMPTY_INT_ARRAY;
    }
    final int[] result = new int[strValues.size()];
    final NumberFormat nf = new NumberFormat();
    int index = 0;
    for (final String str : strValues) {
      final int value = nf.parseInt(str);
      if (nf.fail()) {
        return defaultValues;
      }
      result[index++] = value;
    }
    return result;
  }

  public static long getLong(final Properties props, final String name)
      throws ConfigurationError {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      throw new PropertyNotExistError(name);
    }
    final NumberFormat nf = new NumberFormat();
    final long result = nf.parseLong(strValue);
    if (nf.fail()) {
      throw new InvalidPropertyValueError(name, strValue);
    }
    return result;
  }

  public static long getLong(final Properties props, final String name,
      final long defaultValue) {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final long result = nf.parseLong(strValue);
    if (nf.fail()) {
      return defaultValue;
    } else {
      return result;
    }
  }

  public static long[] getLongs(final Properties props, final String name,
      final char separator) throws ConfigurationError {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      throw new PropertyNotExistError(name);
    }
    final List<String> strValues = StringUtils.split(strValue, separator,
        true, true, null);
    if ((strValues == null) || strValues.isEmpty()) {
      return ArrayUtils.EMPTY_LONG_ARRAY;
    }
    final long[] result = new long[strValues.size()];
    final NumberFormat nf = new NumberFormat();
    int index = 0;
    for (final String str : strValues) {
      final long value = nf.parseLong(str);
      if (nf.fail()) {
        throw new InvalidPropertyValueError(name, strValue);
      }
      result[index++] = value;
    }
    return result;
  }

  public static long[] getLongs(final Properties props, final String name,
      final char separator, @Nullable final long[] defaultValues) {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      return defaultValues;
    }
    final List<String> strValues = StringUtils.split(strValue, separator,
        true, true, null);
    if ((strValues == null) || strValues.isEmpty()) {
      return ArrayUtils.EMPTY_LONG_ARRAY;
    }
    final long[] result = new long[strValues.size()];
    final NumberFormat nf = new NumberFormat();
    int index = 0;
    for (final String str : strValues) {
      final long value = nf.parseLong(str);
      if (nf.fail()) {
        return defaultValues;
      }
      result[index++] = value;
    }
    return result;
  }

  public static float getFloat(final Properties props, final String name)
      throws ConfigurationError {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      throw new PropertyNotExistError(name);
    }
    final NumberFormat nf = new NumberFormat();
    final float result = nf.parseFloat(strValue);
    if (nf.fail()) {
      throw new InvalidPropertyValueError(name, strValue);
    }
    return result;
  }

  public static float getFloat(final Properties props, final String name,
      final float defaultValue) {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final float result = nf.parseFloat(strValue);
    if (nf.fail()) {
      return defaultValue;
    } else {
      return result;
    }
  }

  public static float[] getFloats(final Properties props, final String name,
      final char separator) throws ConfigurationError {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      throw new PropertyNotExistError(name);
    }
    final List<String> strValues = StringUtils.split(strValue, separator,
        true, true, null);
    if ((strValues == null) || strValues.isEmpty()) {
      return ArrayUtils.EMPTY_FLOAT_ARRAY;
    }
    final float[] result = new float[strValues.size()];
    final NumberFormat nf = new NumberFormat();
    int index = 0;
    for (final String str : strValues) {
      final float value = nf.parseFloat(str);
      if (nf.fail()) {
        throw new InvalidPropertyValueError(name, strValue);
      }
      result[index++] = value;
    }
    return result;
  }

  public static float[] getFloats(final Properties props, final String name,
      final char separator, @Nullable final float[] defaultValues) {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      return defaultValues;
    }
    final List<String> strValues = StringUtils.split(strValue, separator,
        true, true, null);
    if ((strValues == null) || strValues.isEmpty()) {
      return ArrayUtils.EMPTY_FLOAT_ARRAY;
    }
    final float[] result = new float[strValues.size()];
    final NumberFormat nf = new NumberFormat();
    int index = 0;
    for (final String str : strValues) {
      final float value = nf.parseFloat(str);
      if (nf.fail()) {
        return defaultValues;
      }
      result[index++] = value;
    }
    return result;
  }

  public static double getDouble(final Properties props, final String name)
      throws ConfigurationError {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      throw new PropertyNotExistError(name);
    }
    final NumberFormat nf = new NumberFormat();
    final double result = nf.parseDouble(strValue);
    if (nf.fail()) {
      throw new InvalidPropertyValueError(name, strValue);
    }
    return result;
  }

  public static double getDouble(final Properties props, final String name,
      final double defaultValue) {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final double result = nf.parseDouble(strValue);
    if (nf.fail()) {
      return defaultValue;
    } else {
      return result;
    }
  }

  public static double[] getDoubles(final Properties props, final String name,
      final char separator) throws ConfigurationError {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      throw new PropertyNotExistError(name);
    }
    final List<String> strValues = StringUtils.split(strValue, separator,
        true, true, null);
    if ((strValues == null) || strValues.isEmpty()) {
      return ArrayUtils.EMPTY_DOUBLE_ARRAY;
    }
    final double[] result = new double[strValues.size()];
    final NumberFormat nf = new NumberFormat();
    int index = 0;
    for (final String str : strValues) {
      final double value = nf.parseDouble(str);
      if (nf.fail()) {
        throw new InvalidPropertyValueError(name, strValue);
      }
      result[index++] = value;
    }
    return result;
  }

  public static double[] getDoubles(final Properties props, final String name,
      final char separator, @Nullable final double[] defaultValues) {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      return defaultValues;
    }
    final List<String> strValues = StringUtils.split(strValue, separator,
        true, true, null);
    if ((strValues == null) || strValues.isEmpty()) {
      return ArrayUtils.EMPTY_DOUBLE_ARRAY;
    }
    final double[] result = new double[strValues.size()];
    final NumberFormat nf = new NumberFormat();
    int index = 0;
    for (final String str : strValues) {
      final double value = nf.parseDouble(str);
      if (nf.fail()) {
        return defaultValues;
      }
      result[index++] = value;
    }
    return result;
  }

  public static String getString(final Properties props, final String name)
      throws ConfigurationError {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      throw new PropertyNotExistError(name);
    }
    return strValue;
  }

  public static String getString(final Properties props, final String name,
      @Nullable final String defaultValue) {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      return defaultValue;
    } else {
      return strValue;
    }
  }

  public static String[] getStrings(final Properties props, final String name,
      final char separator) throws ConfigurationError {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      throw new PropertyNotExistError(name);
    }
    final List<String> strValues = StringUtils.split(strValue, separator,
        true, true, null);
    if ((strValues == null) || strValues.isEmpty()) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    } else {
      return strValues.toArray(new String[strValues.size()]);
    }
  }

  public static String[] getStrings(final Properties props, final String name,
      final char separator, @Nullable final String[] defaultValues) {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      return defaultValues;
    }
    final List<String> strValues = StringUtils.split(strValue, separator,
        true, true, null);
    if ((strValues == null) || strValues.isEmpty()) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    } else {
      return strValues.toArray(new String[strValues.size()]);
    }
  }

  public static <E extends Enum<E>> E getEnum(final Properties props,
      final String name, final boolean isShortName, final boolean ignoreCase,
      final Class<E> enumClass) throws ConfigurationError {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      throw new PropertyNotExistError(name);
    }
    final E result = EnumUtils.forName(strValue, isShortName, ignoreCase,
        enumClass);
    if (result == null) {
      throw new InvalidPropertyValueError(name, strValue);
    }
    return result;
  }

  public static <E extends Enum<E>> E getEnum(final Properties props,
      final String name, final boolean isShortName,
      final boolean ignoreCase,  final Class<E> enumClass,
      @Nullable final E defaultValue) {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      return defaultValue;
    }
    final E result = EnumUtils.forName(strValue, isShortName, ignoreCase,
        enumClass);
    if (result == null) {
      return defaultValue;
    } else {
      return result;
    }
  }

  @SuppressWarnings("unchecked")
  public static <E extends Enum<E>> E[] getEnums(final Properties props,
      final String name, final char separator, final boolean isShortName,
      final boolean ignoreCase, final Class<E> enumClass)
      throws ConfigurationError {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      throw new PropertyNotExistError(name);
    }
    final List<String> strValues = StringUtils.split(strValue, separator, true,
        true, null);
    if ((strValues == null) || strValues.isEmpty()) {
      return (E[]) Array.newInstance(enumClass, 0);
    }
    final E[] result = (E[]) Array.newInstance(enumClass, strValues.size());
    int index = 0;
    for (final String str : strValues) {
      final E value = EnumUtils.forName(str, isShortName, ignoreCase,
          enumClass);
      if (value == null) {
        throw new InvalidPropertyValueError(name, strValue);
      }
      result[index++] = value;
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public static <E extends Enum<E>> E[] getEnums(final Properties props,
      final String name, final char separator, final boolean isShortName,
      final boolean ignoreCase, final Class<E> enumClass,
      @Nullable final E[] defaultValues) throws ConfigurationError {
    final String strValue = props.getProperty(name);
    if (strValue == null) {
      return defaultValues;
    }
    final List<String> strValues = StringUtils.split(strValue, separator, true,
        true, null);
    if ((strValues == null) || strValues.isEmpty()) {
      return (E[]) Array.newInstance(enumClass, 0);
    }
    final E[] result = (E[]) Array.newInstance(enumClass, strValues.size());
    int index = 0;
    for (final String str : strValues) {
      final E value = EnumUtils.forName(str, isShortName, ignoreCase, enumClass);
      if (value == null) {
        return defaultValues;
      }
      result[index++] = value;
    }
    return result;
  }
}
