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

package com.github.haixing_hu.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteOrder;
import java.util.Date;

import javax.annotation.Nullable;

import com.github.haixing_hu.text.DateFormat;

/**
 * This class provides operations on {@link {@link Date} objects.
 * <p>
 * This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} input. Each method documents its
 * behavior in more detail.
 * </p>
 * <p>
 * This class also handle the conversion from {@link Date} objects to common
 * types.
 * </p>
 *
 * @author Haixing Hu
 */
public class DateUtils {

  /**
   * The default date format pattern.
   */
  public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

  public static boolean toBoolean(@Nullable final Date value) {
    return (value == null ? BooleanUtils.DEFAULT : (value.getTime() != 0));
  }

  public static boolean toBoolean(@Nullable final Date value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : (value.getTime() != 0));
  }

  public static Boolean toBooleanObject(@Nullable final Date value) {
    return (value == null ? null : Boolean.valueOf(value.getTime() != 0));
  }

  public static Boolean toBooleanObject(@Nullable final Date value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : Boolean.valueOf(value.getTime() != 0));
  }

  public static char toChar(@Nullable final Date value) {
    return (value == null ? CharUtils.DEFAULT : (char) value.getTime());
  }

  public static char toChar(@Nullable final Date value,
      final char defaultValue) {
    return (value == null ? defaultValue : (char) value.getTime());
  }

  public static Character toCharObject(@Nullable final Date value) {
    return (value == null ? null : Character.valueOf((char) value.getTime()));
  }

  public static Character toCharObject(@Nullable final Date value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : Character.valueOf((char) value.getTime()));
  }

  public static byte toByte(@Nullable final Date value) {
    return (value == null ? ByteUtils.DEFAULT : (byte) value.getTime());
  }

  public static byte toByte(@Nullable final Date value,
      final byte defaultValue) {
    return (value == null ? defaultValue : (byte) value.getTime());
  }

  public static Byte toByteObject(@Nullable final Date value) {
    return (value == null ? null : Byte.valueOf((byte) value.getTime()));
  }

  public static Byte toByteObject(@Nullable final Date value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : Byte.valueOf((byte) value.getTime()));
  }

  public static short toShort(@Nullable final Date value) {
    return (value == null ? IntUtils.DEFAULT : (short) value.getTime());
  }

  public static short toShort(@Nullable final Date value,
      final short defaultValue) {
    return (value == null ? defaultValue : (short) value.getTime());
  }

  public static Short toShortObject(@Nullable final Date value) {
    return (value == null ? null : Short.valueOf((short) value.getTime()));
  }

  public static Short toShortObject(@Nullable final Date value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : Short.valueOf((short) value.getTime()));
  }

  public static int toInt(@Nullable final Date value) {
    return (value == null ? IntUtils.DEFAULT : (int) value.getTime());
  }

  public static int toInt(@Nullable final Date value,
      final int defaultValue) {
    return (value == null ? defaultValue : (int) value.getTime());
  }

  public static Integer toIntObject(@Nullable final Date value) {
    return (value == null ? null : Integer.valueOf((int) value.getTime()));
  }

  public static Integer toIntObject(@Nullable final Date value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : Integer.valueOf((int) value.getTime()));
  }

  public static long toLong(@Nullable final Date value) {
    return (value == null ? LongUtils.DEFAULT : value.getTime());
  }

  public static long toLong(@Nullable final Date value,
      final long defaultValue) {
    return (value == null ? defaultValue : value.getTime());
  }

  public static Long toLongObject(@Nullable final Date value) {
    return (value == null ? null : Long.valueOf(value.getTime()));
  }

  public static Long toLongObject(@Nullable final Date value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : Long.valueOf(value.getTime()));
  }

  public static float toFloat(@Nullable final Date value) {
    return (value == null ? FloatUtils.DEFAULT : (float) value.getTime());
  }

  public static float toFloat(@Nullable final Date value,
      final float defaultValue) {
    return (value == null ? defaultValue : (float) value.getTime());
  }

  public static Float toFloatObject(@Nullable final Date value) {
    return (value == null ? null : Float.valueOf(value.getTime()));
  }

  public static Float toFloatObject(@Nullable final Date value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : Float.valueOf(value.getTime()));
  }

  public static double toDouble(@Nullable final Date value) {
    return (value == null ? DoubleUtils.DEFAULT : (double) value.getTime());
  }

  public static double toDouble(@Nullable final Date value,
      final double defaultValue) {
    return (value == null ? defaultValue : (double) value.getTime());
  }

  public static Double toDoubleObject(@Nullable final Date value) {
    return (value == null ? null : Double.valueOf(value.getTime()));
  }

  public static Double toDoubleObject(@Nullable final Date value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : Double.valueOf(value.getTime()));
  }

  public static String toString(@Nullable final Date value,
      final String pattern) {
    return toString(value, null, pattern);
  }

  public static String toString(@Nullable final Date value,
      @Nullable final String defaultValue, final String pattern) {
    if (value == null) {
      return defaultValue;
    }
    final DateFormat df = new DateFormat(pattern);
    return df.format(value);
  }

  public static byte[] toByteArray(@Nullable final Date value) {
    return (value == null ? null : LongUtils.toByteArray(value.getTime(),
        ByteArrayUtils.DEFAULT_BYTE_ORDER));
  }

  public static byte[] toByteArray(@Nullable final Date value,
      final ByteOrder byteOrder) {
    return (value == null ? null : LongUtils.toByteArray(value.getTime(),
        byteOrder));
  }

  public static byte[] toByteArray(@Nullable final Date value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : LongUtils.toByteArray(value.getTime(),
        ByteArrayUtils.DEFAULT_BYTE_ORDER));
  }

  public static byte[] toByteArray(@Nullable final Date value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue : LongUtils.toByteArray(value.getTime(),
        byteOrder));
  }

  public static Class<?> toClass(@Nullable final Date value) {
    return (value == null ? null : Date.class);
  }

  public static Class<?> toClass(@Nullable final Date value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Date.class);
  }

  public static BigInteger toBigInteger(@Nullable final Date value) {
    return (value == null ? null : BigInteger.valueOf(value.getTime()));
  }

  public static BigInteger toBigInteger(@Nullable final Date value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : BigInteger.valueOf(value.getTime()));
  }

  public static BigDecimal toBigDecimal(@Nullable final Date value) {
    return (value == null ? null : BigDecimal.valueOf(value.getTime()));
  }

  public static BigDecimal toBigDecimal(@Nullable final Date value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : BigDecimal.valueOf(value.getTime()));
  }
}
