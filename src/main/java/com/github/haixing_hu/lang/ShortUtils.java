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
 * This class provides operations on {@code short} primitives and
 * {@link Short} objects.
 * <p>
 * This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} input. Each method documents its
 * behavior in more detail.
 * </p>
 * <p>
 * This class also handle the conversion from {@code short} values or
 * {@link Short} objects to common types.
 * </p>
 *
 * @author Haixing Hu
 */
public class ShortUtils {

  /**
   * The default {@code short} value used when necessary.
   */
  public static final short DEFAULT = (short) 0;

  /**
   * The maximum value of an unsigned short.
   */
  public static final int UNSIGNED_MAX  = 0xFFFF;

  public static short toPrimitive(@Nullable final Short value) {
    return (value == null ? DEFAULT : value.shortValue());
  }

  public static short toPrimitive(@Nullable final Short value, final short defaultValue) {
    return (value == null ? defaultValue : value.shortValue());
  }

  public static boolean toBoolean(final short value) {
    return (value != 0);
  }

  public static boolean toBoolean(@Nullable final Short value) {
    return (value == null ? BooleanUtils.DEFAULT : toBoolean(value.shortValue()));
  }

  public static boolean toBoolean(@Nullable final Short value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : toBoolean(value.shortValue()));
  }

  public static Boolean toBooleanObject(final short value) {
    return Boolean.valueOf(toBoolean(value));
  }

  public static Boolean toBooleanObject(@Nullable final Short value) {
    return (value == null ? null : toBooleanObject(value.shortValue()));
  }

  public static Boolean toBooleanObject(@Nullable final Short value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : toBooleanObject(value.shortValue()));
  }

  public static char toChar(final short value) {
    return (char) value;
  }

  public static char toChar(@Nullable final Short value) {
    return (value == null ? CharUtils.DEFAULT : toChar(value.shortValue()));
  }

  public static char toChar(@Nullable final Short value,
      final char defaultValue) {
    return (value == null ? defaultValue : toChar(value.shortValue()));
  }

  public static Character toCharObject(final short value) {
    return Character.valueOf(toChar(value));
  }

  public static Character toCharObject(@Nullable final Short value) {
    return (value == null ? null : toCharObject(value.shortValue()));
  }

  public static Character toCharObject(@Nullable final Short value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : toCharObject(value.shortValue()));
  }

  public static byte toByte(final short value) {
    return (byte) value;
  }

  public static byte toByte(@Nullable final Short value) {
    return (value == null ? ByteUtils.DEFAULT : toByte(value.shortValue()));
  }

  public static byte toByte(@Nullable final Short value,
      final byte defaultValue) {
    return (value == null ? defaultValue : toByte(value.shortValue()));
  }

  public static Byte toByteObject(final short value) {
    return Byte.valueOf(toByte(value));
  }

  public static Byte toByteObject(@Nullable final Short value) {
    return (value == null ? null : toByteObject(value.shortValue()));
  }

  public static Byte toByteObject(@Nullable final Short value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : toByteObject(value.shortValue()));
  }

  public static int toInt(final short value) {
    return value;
  }

  public static int toInt(@Nullable final Short value) {
    return (value == null ? IntUtils.DEFAULT : toInt(value.shortValue()));
  }

  public static int toInt(@Nullable final Short value,
      final int defaultValue) {
    return (value == null ? defaultValue : toInt(value.shortValue()));
  }

  public static Integer toIntObject(final short value) {
    return Integer.valueOf(toInt(value));
  }

  public static Integer toIntObject(@Nullable final Short value) {
    return (value == null ? null : toIntObject(value.shortValue()));
  }

  public static Integer toIntObject(@Nullable final Short value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : toIntObject(value.shortValue()));
  }

  public static long toLong(final short value) {
    return value;
  }

  public static long toLong(@Nullable final Short value) {
    return (value == null ? LongUtils.DEFAULT : toLong(value.shortValue()));
  }

  public static long toLong(@Nullable final Short value,
      final long defaultValue) {
    return (value == null ? defaultValue : toLong(value.shortValue()));
  }

  public static Long toLongObject(final short value) {
    return Long.valueOf(toLong(value));
  }

  public static Long toLongObject(@Nullable final Short value) {
    return (value == null ? null : toLongObject(value.shortValue()));
  }

  public static Long toLongObject(@Nullable final Short value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : toLongObject(value.shortValue()));
  }

  public static float toFloat(final short value) {
    return value;
  }

  public static float toFloat(@Nullable final Short value) {
    return (value == null ? FloatUtils.DEFAULT : toFloat(value.shortValue()));
  }

  public static float toFloat(@Nullable final Short value,
      final float defaultValue) {
    return (value == null ? defaultValue : toFloat(value.shortValue()));
  }

  public static Float toFloatObject(final short value) {
    return Float.valueOf(toFloat(value));
  }

  public static Float toFloatObject(@Nullable final Short value) {
    return (value == null ? null : toFloatObject(value.shortValue()));
  }

  public static Float toFloatObject(@Nullable final Short value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : toFloatObject(value.shortValue()));
  }

  public static double toDouble(final short value) {
    return value;
  }

  public static double toDouble(@Nullable final Short value) {
    return (value == null ? DoubleUtils.DEFAULT : toDouble(value.shortValue()));
  }

  public static double toDouble(@Nullable final Short value,
      final double defaultValue) {
    return (value == null ? defaultValue : toDouble(value.shortValue()));
  }

  public static Double toDoubleObject(final short value) {
    return Double.valueOf(toDouble(value));
  }

  public static Double toDoubleObject(@Nullable final Short value) {
    return (value == null ? null : toDoubleObject(value.shortValue()));
  }

  public static Double toDoubleObject(@Nullable final Short value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : toDoubleObject(value.shortValue()));
  }

  public static String toString(final short value) {
    return Integer.toString(value, 10);
  }

  public static String toString(@Nullable final Short value) {
    return (value == null ? null : toString(value.shortValue()));
  }

  public static String toString(@Nullable final Short value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.shortValue()));
  }

  /**
   * Convert a {@code short} value into hex string.
   *
   * @param value
   *    the value to be converted.
   * @param builder
   *    a {@link StringBuilder} where to append the hex string.
   */
  public static void toHexString(final short value, final StringBuilder builder) {
    final char[] digits = NumberFormatSymbols.DEFAULT_UPPERCASE_DIGITS;
    builder.append("0x")
           .append(digits[(value >>> 12) & 0x0F])
           .append(digits[(value >>> 8) & 0x0F])
           .append(digits[(value >>> 4) & 0x0F])
           .append(digits[value & 0x0F]);
  }

  /**
   * Convert a {@code short} value into hex string.
   *
   * @param value
   *          the value to be converted.
   * @return the hex string of the value.
   */
  public static String toHexString(final short value) {
    final StringBuilder builder = new StringBuilder();
    toHexString(value, builder);
    return builder.toString();
  }

  public static Date toDate(final short value) {
    return new Date(value * 1L);
  }

  public static Date toDate(@Nullable final Short value) {
    return (value == null ? null : new Date(value.longValue()));
  }

  public static Date toDate(@Nullable final Short value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : new Date(value.longValue()));
  }


  public static byte[] toByteArray(final short value) {
    return toByteArray(value, ByteArrayUtils.DEFAULT_BYTE_ORDER);
  }

  public static byte[] toByteArray(final short value, final ByteOrder byteOrder) {
    final byte[] result = new byte[2];
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      result[0] = (byte) (value >>> 8);
      result[1] = (byte) value;
    } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
      result[1] = (byte) (value >>> 8);
      result[0] = (byte) value;
    } else {
      throw new UnsupportedByteOrderException(byteOrder);
    }
    return result;
  }

  public static byte[] toByteArray(@Nullable final Short value) {
    return (value == null ? null : toByteArray(value.shortValue(),
        ByteArrayUtils.DEFAULT_BYTE_ORDER));
  }

  public static byte[] toByteArray(@Nullable final Short value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toByteArray(value.shortValue(), byteOrder));
  }

  public static byte[] toByteArray(@Nullable final Short value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.shortValue(),
        ByteArrayUtils.DEFAULT_BYTE_ORDER));
  }

  public static byte[] toByteArray(@Nullable final Short value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue : toByteArray(value.shortValue(),
        byteOrder));
  }

  public static Class<?> toClass(final short value) {
    return Short.TYPE;
  }

  public static Class<?> toClass(@Nullable final Short value) {
    return (value == null ? null : Short.class);
  }

  public static Class<?> toClass(@Nullable final Short value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Short.class);
  }

  public static BigInteger toBigInteger(final short value) {
    return BigInteger.valueOf(value);
  }

  public static BigInteger toBigInteger(@Nullable final Short value) {
    return (value == null ? null : BigInteger.valueOf(value.longValue()));
  }

  public static BigInteger toBigInteger(@Nullable final Short value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : BigInteger.valueOf(value.longValue()));
  }

  public static BigDecimal toBigDecimal(final short value) {
    return BigDecimal.valueOf(value);
  }

  public static BigDecimal toBigDecimal(@Nullable final Short value) {
    return (value == null ? null : BigDecimal.valueOf(value.longValue()));
  }

  public static BigDecimal toBigDecimal(@Nullable final Short value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : BigDecimal.valueOf(value.longValue()));
  }
}
