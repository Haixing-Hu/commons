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

import com.github.haixing_hu.text.NumberFormatSymbols;

/**
 * This class provides operations on <code>int</code> primitives and
 * {@link Integer} objects.
 * <p>
 * This class tries to handle <code>null</code> input gracefully. An exception
 * will not be thrown for a <code>null</code> input. Each method documents its
 * behavior in more detail.
 * </p>
 * <p>
 * This class also handle the conversion from <code>int</code> values or
 * {@link Integer} objects to common types.
 * </p>
 *
 * @author Haixing Hu
 */
public class IntUtils {

  /**
   * The default <code>int</code> value used when necessary.
   */
  public static final int DEFAULT = 0;

  /**
   * The maximum value of an unsigned int.
   */
  public static final int UNSIGNED_MAX  = 0xFFFFFFFF;

  public static int toPrimitive(@Nullable final Integer value) {
    return (value == null ? DEFAULT : value.intValue());
  }

  public static int toPrimitive(@Nullable final Integer value, final int defaultValue) {
    return (value == null ? defaultValue : value.intValue());
  }

  public static boolean toBoolean(final int value) {
    return (value != 0);
  }

  public static boolean toBoolean(@Nullable final Integer value) {
    return (value == null ? BooleanUtils.DEFAULT : toBoolean(value.intValue()));
  }

  public static boolean toBoolean(@Nullable final Integer value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : toBoolean(value.intValue()));
  }

  public static Boolean toBooleanObject(final int value) {
    return Boolean.valueOf(toBoolean(value));
  }

  public static Boolean toBooleanObject(@Nullable final Integer value) {
    return (value == null ? null : toBooleanObject(value.intValue()));
  }

  public static Boolean toBooleanObject(@Nullable final Integer value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : toBooleanObject(value.intValue()));
  }

  public static char toChar(final int value) {
    return (char) value;
  }

  public static char toChar(@Nullable final Integer value) {
    return (value == null ? CharUtils.DEFAULT : toChar(value.intValue()));
  }

  public static char toChar(@Nullable final Integer value,
      final char defaultValue) {
    return (value == null ? defaultValue : toChar(value.intValue()));
  }

  public static Character toCharObject(final int value) {
    return Character.valueOf(toChar(value));
  }

  public static Character toCharObject(@Nullable final Integer value) {
    return (value == null ? null : toCharObject(value.intValue()));
  }

  public static Character toCharObject(@Nullable final Integer value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : toCharObject(value.intValue()));
  }

  public static byte toByte(final int value) {
    return (byte) value;
  }

  public static byte toByte(@Nullable final Integer value) {
    return (value == null ? ByteUtils.DEFAULT : toByte(value.intValue()));
  }

  public static byte toByte(@Nullable final Integer value,
      final byte defaultValue) {
    return (value == null ? defaultValue : toByte(value.intValue()));
  }

  public static Byte toByteObject(final int value) {
    return Byte.valueOf(toByte(value));
  }

  public static Byte toByteObject(@Nullable final Integer value) {
    return (value == null ? null : toByteObject(value.intValue()));
  }

  public static Byte toByteObject(@Nullable final Integer value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : toByteObject(value.intValue()));
  }

  public static short toShort(final int value) {
    return (short) value;
  }

  public static short toShort(@Nullable final Integer value) {
    return (value == null ? IntUtils.DEFAULT : toShort(value.intValue()));
  }

  public static short toShort(@Nullable final Integer value,
      final short defaultValue) {
    return (value == null ? defaultValue : toShort(value.intValue()));
  }

  public static Short toShortObject(final int value) {
    return Short.valueOf(toShort(value));
  }

  public static Short toShortObject(@Nullable final Integer value) {
    return (value == null ? null : toShortObject(value.intValue()));
  }

  public static Short toShortObject(@Nullable final Integer value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : toShortObject(value.intValue()));
  }

  public static long toLong(final int value) {
    return value;
  }

  public static long toLong(@Nullable final Integer value) {
    return (value == null ? LongUtils.DEFAULT : toLong(value.intValue()));
  }

  public static long toLong(@Nullable final Integer value,
      final long defaultValue) {
    return (value == null ? defaultValue : toLong(value.intValue()));
  }

  public static Long toLongObject(final int value) {
    return Long.valueOf(toLong(value));
  }

  public static Long toLongObject(@Nullable final Integer value) {
    return (value == null ? null : toLongObject(value.intValue()));
  }

  public static Long toLongObject(@Nullable final Integer value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : toLongObject(value.intValue()));
  }

  public static float toFloat(final int value) {
    return value;
  }

  public static float toFloat(@Nullable final Integer value) {
    return (value == null ? FloatUtils.DEFAULT : toFloat(value.intValue()));
  }

  public static float toFloat(@Nullable final Integer value,
      final float defaultValue) {
    return (value == null ? defaultValue : toFloat(value.intValue()));
  }

  public static Float toFloatObject(final int value) {
    return Float.valueOf(toFloat(value));
  }

  public static Float toFloatObject(@Nullable final Integer value) {
    return (value == null ? null : toFloatObject(value.intValue()));
  }

  public static Float toFloatObject(@Nullable final Integer value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : toFloatObject(value.intValue()));
  }

  public static double toDouble(final int value) {
    return value;
  }

  public static double toDouble(@Nullable final Integer value) {
    return (value == null ? DoubleUtils.DEFAULT : toDouble(value.intValue()));
  }

  public static double toDouble(@Nullable final Integer value,
      final double defaultValue) {
    return (value == null ? defaultValue : toDouble(value.intValue()));
  }

  public static Double toDoubleObject(final int value) {
    return Double.valueOf(toDouble(value));
  }

  public static Double toDoubleObject(@Nullable final Integer value) {
    return (value == null ? null : toDoubleObject(value.intValue()));
  }

  public static Double toDoubleObject(@Nullable final Integer value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : toDoubleObject(value.intValue()));
  }

  public static String toString(final int value) {
    return Integer.toString(value, 10);
  }

  public static String toString(@Nullable final Integer value) {
    return (value == null ? null : toString(value.intValue()));
  }

  public static String toString(@Nullable final Integer value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.intValue()));
  }

  /**
   * Convert a <code>int</code> value into hex string.
   *
   * @param value
   *    the value to be converted.
   * @param builder
   *    a {@link StringBuilder} where to append the hex string.
   */
  public static void toHexString(final int value, final StringBuilder builder) {
    final char[] digits = NumberFormatSymbols.DEFAULT_UPPERCASE_DIGITS;
    builder.append("0x")
           .append(digits[(value >>> 28) & 0x0F])
           .append(digits[(value >>> 24) & 0x0F])
           .append(digits[(value >>> 20) & 0x0F])
           .append(digits[(value >>> 16) & 0x0F])
           .append(digits[(value >>> 12) & 0x0F])
           .append(digits[(value >>> 8) & 0x0F])
           .append(digits[(value >>> 4) & 0x0F])
           .append(digits[value & 0x0F]);
  }

  /**
   * Convert a <code>int</code> value into hex string.
   *
   * @param value
   *          the value to be converted.
   * @return the hex string of the value.
   */
  public static String toHexString(final int value) {
    final StringBuilder builder = new StringBuilder();
    toHexString(value, builder);
    return builder.toString();
  }

  public static Date toDate(final int value) {
    return new Date(value);
  }

  public static Date toDate(@Nullable final Integer value) {
    return (value == null ? null : new Date(value.longValue()));
  }

  public static Date toDate(@Nullable final Integer value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : new Date(value.longValue()));
  }

  public static byte[] toByteArray(final int value) {
    return toByteArray(value, ByteArrayUtils.DEFAULT_BYTE_ORDER);
  }

  public static byte[] toByteArray(final int value, final ByteOrder byteOrder) {
    final byte[] result = new byte[4];
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      result[0] = (byte) (value >>> 24);
      result[1] = (byte) (value >>> 16);
      result[2] = (byte) (value >>> 8);
      result[3] = (byte) value;
    } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
      result[3] = (byte) (value >>> 24);
      result[2] = (byte) (value >>> 16);
      result[1] = (byte) (value >>> 8);
      result[0] = (byte) value;
    } else {
      throw new UnsupportedByteOrderException(byteOrder);
    }
    return result;
  }

  public static byte[] toByteArray(@Nullable final Integer value) {
    return (value == null ? null : toByteArray(value.intValue(),
        ByteArrayUtils.DEFAULT_BYTE_ORDER));
  }

  public static byte[] toByteArray(@Nullable final Integer value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toByteArray(value.intValue(), byteOrder));
  }

  public static byte[] toByteArray(@Nullable final Integer value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.intValue(),
        ByteArrayUtils.DEFAULT_BYTE_ORDER));
  }

  public static byte[] toByteArray(@Nullable final Integer value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue : toByteArray(value.intValue(),
        byteOrder));
  }

  public static Class<?> toClass(final int value) {
    return Integer.TYPE;
  }

  public static Class<?> toClass(@Nullable final Integer value) {
    return (value == null ? null : Integer.class);
  }

  public static Class<?> toClass(@Nullable final Integer value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Integer.class);
  }

  public static BigInteger toBigInteger(final int value) {
    return BigInteger.valueOf(value);
  }

  public static BigInteger toBigInteger(@Nullable final Integer value) {
    return (value == null ? null : BigInteger.valueOf(value.longValue()));
  }

  public static BigInteger toBigInteger(@Nullable final Integer value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : BigInteger.valueOf(value.longValue()));
  }

  public static BigDecimal toBigDecimal(final int value) {
    return BigDecimal.valueOf(value);
  }

  public static BigDecimal toBigDecimal(@Nullable final Integer value) {
    return (value == null ? null : BigDecimal.valueOf(value.longValue()));
  }

  public static BigDecimal toBigDecimal(@Nullable final Integer value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : BigDecimal.valueOf(value.longValue()));
  }
}
