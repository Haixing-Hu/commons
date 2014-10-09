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
import java.util.Date;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import com.github.haixing_hu.text.NumberFormatSymbols;

/**
 * This class provides operations on {@code byte} primitives and
 * {@link Byte} objects.
 * <p>
 * This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} input. Each method documents its
 * behavior in more detail.
 * </p>
 * <p>
 * This class also handle the conversion from {@code byte} values or
 * {@link Byte} objects to common types.
 * </p>
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class ByteUtils {

  /**
   * The default {@code byte} value used when necessary.
   */
  public static final byte DEFAULT = (byte) 0;

  /**
   * The maximum value of an unsigned byte.
   */
  public static final int UNSIGNED_MAX   = 0xFF;

  private ByteUtils() {}

  public static byte toPrimitive(@Nullable final Byte value) {
    return (value == null ? DEFAULT : value.byteValue());
  }

  public static byte toPrimitive(@Nullable final Byte value, final byte defaultValue) {
    return (value == null ? defaultValue : value.byteValue());
  }

  public static boolean toBoolean(final byte value) {
    return (value != 0);
  }

  public static boolean toBoolean(@Nullable final Byte value) {
    return (value == null ? BooleanUtils.DEFAULT : toBoolean(value.byteValue()));
  }

  public static boolean toBoolean(@Nullable final Byte value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : toBoolean(value.byteValue()));
  }

  public static Boolean toBooleanObject(final byte value) {
    return Boolean.valueOf(toBoolean(value));
  }

  public static Boolean toBooleanObject(@Nullable final Byte value) {
    return (value == null ? null : toBooleanObject(value.byteValue()));
  }

  public static Boolean toBooleanObject(@Nullable final Byte value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : toBooleanObject(value.byteValue()));
  }

  public static char toChar(final byte value) {
    return (char) value;
  }

  public static char toChar(@Nullable final Byte value) {
    return (value == null ? CharUtils.DEFAULT : toChar(value.byteValue()));
  }

  public static char toChar(@Nullable final Byte value,
      final char defaultValue) {
    return (value == null ? defaultValue : toChar(value.byteValue()));
  }

  public static Character toCharObject(final byte value) {
    return Character.valueOf(toChar(value));
  }

  public static Character toCharObject(@Nullable final Byte value) {
    return (value == null ? null : toCharObject(value.byteValue()));
  }

  public static Character toCharObject(@Nullable final Byte value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : toCharObject(value.byteValue()));
  }

  public static short toShort(final byte value) {
    return value;
  }

  public static short toShort(@Nullable final Byte value) {
    return (value == null ? ShortUtils.DEFAULT : toShort(value.byteValue()));
  }

  public static short toShort(@Nullable final Byte value,
      final short defaultValue) {
    return (value == null ? defaultValue : toShort(value.byteValue()));
  }

  public static Short toShortObject(final byte value) {
    return Short.valueOf(toShort(value));
  }

  public static Short toShortObject(@Nullable final Byte value) {
    return (value == null ? null : toShortObject(value.byteValue()));
  }

  public static Short toShortObject(@Nullable final Byte value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : toShortObject(value.byteValue()));
  }

  public static int toInt(final byte value) {
    return value;
  }

  public static int toInt(@Nullable final Byte value) {
    return (value == null ? IntUtils.DEFAULT : toInt(value.byteValue()));
  }

  public static int toInt(@Nullable final Byte value,
      final int defaultValue) {
    return (value == null ? defaultValue : toInt(value.byteValue()));
  }

  public static Integer toIntObject(final byte value) {
    return Integer.valueOf(toInt(value));
  }

  public static Integer toIntObject(@Nullable final Byte value) {
    return (value == null ? null : toIntObject(value.byteValue()));
  }

  public static Integer toIntObject(@Nullable final Byte value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : toIntObject(value.byteValue()));
  }

  public static long toLong(final byte value) {
    return value;
  }

  public static long toLong(@Nullable final Byte value) {
    return (value == null ? LongUtils.DEFAULT : toLong(value.byteValue()));
  }

  public static long toLong(@Nullable final Byte value,
      final long defaultValue) {
    return (value == null ? defaultValue : toLong(value.byteValue()));
  }

  public static Long toLongObject(final byte value) {
    return Long.valueOf(toLong(value));
  }

  public static Long toLongObject(@Nullable final Byte value) {
    return (value == null ? null : toLongObject(value.byteValue()));
  }

  public static Long toLongObject(@Nullable final Byte value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : toLongObject(value.byteValue()));
  }

  public static float toFloat(final byte value) {
    return value;
  }

  public static float toFloat(@Nullable final Byte value) {
    return (value == null ? FloatUtils.DEFAULT : toFloat(value.byteValue()));
  }

  public static float toFloat(@Nullable final Byte value,
      final float defaultValue) {
    return (value == null ? defaultValue : toFloat(value.byteValue()));
  }

  public static Float toFloatObject(final byte value) {
    return Float.valueOf(toFloat(value));
  }

  public static Float toFloatObject(@Nullable final Byte value) {
    return (value == null ? null : toFloatObject(value.byteValue()));
  }

  public static Float toFloatObject(@Nullable final Byte value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : toFloatObject(value.byteValue()));
  }

  public static double toDouble(final byte value) {
    return value;
  }

  public static double toDouble(@Nullable final Byte value) {
    return (value == null ? DoubleUtils.DEFAULT : toDouble(value.byteValue()));
  }

  public static double toDouble(@Nullable final Byte value,
      final double defaultValue) {
    return (value == null ? defaultValue : toDouble(value.byteValue()));
  }

  public static Double toDoubleObject(final byte value) {
    return Double.valueOf(toDouble(value));
  }

  public static Double toDoubleObject(@Nullable final Byte value) {
    return (value == null ? null : toDoubleObject(value.byteValue()));
  }

  public static Double toDoubleObject(@Nullable final Byte value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : toDoubleObject(value.byteValue()));
  }

  public static String toString(final byte value) {
    return Integer.toString(value, 10);
  }

  public static String toString(@Nullable final Byte value) {
    return (value == null ? null : toString(value.byteValue()));
  }

  public static String toString(@Nullable final Byte value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.byteValue()));
  }

  /**
   * Convert a {@code byte} value into hex string.
   *
   * @param value
   *    the value to be converted.
   * @param builder
   *    a {@link StringBuilder} where to append the hex string.
   */
  public static void toHexString(final byte value, final StringBuilder builder) {
    final char[] digits = NumberFormatSymbols.DEFAULT_UPPERCASE_DIGITS;
    builder.append("0x")
           .append(digits[(value >>> 4) & 0x0F])
           .append(digits[value & 0x0F]);
  }

  /**
   * Convert a {@code byte} value into hex string.
   *
   * @param value
   *          the value to be converted.
   * @return the hex string of the value.
   */
  public static String toHexString(final byte value) {
    final StringBuilder builder = new StringBuilder();
    toHexString(value, builder);
    return builder.toString();
  }

  public static Date toDate(final byte value) {
    return new Date(value);
  }

  public static Date toDate(@Nullable final Byte value) {
    return (value == null ? null : new Date(value.longValue()));
  }

  public static Date toDate(@Nullable final Byte value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : new Date(value.longValue()));
  }

  public static byte[] toByteArray(final byte value) {
    final byte[] result = {value};
    return result;
  }

  public static byte[] toByteArray(@Nullable final Byte value) {
    return (value == null ? null : toByteArray(value.byteValue()));
  }

  public static byte[] toByteArray(@Nullable final Byte value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.byteValue()));
  }

  public static Class<?> toClass(final byte value) {
    return Byte.TYPE;
  }

  public static Class<?> toClass(@Nullable final Byte value) {
    return (value == null ? null : Byte.class);
  }

  public static Class<?> toClass(@Nullable final Byte value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Byte.class);
  }

  public static BigInteger toBigInteger(final byte value) {
    return BigInteger.valueOf(value);
  }

  public static BigInteger toBigInteger(@Nullable final Byte value) {
    return (value == null ? null : BigInteger.valueOf(value.longValue()));
  }

  public static BigInteger toBigInteger(@Nullable final Byte value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : BigInteger.valueOf(value.longValue()));
  }

  public static BigDecimal toBigDecimal(final byte value) {
    return BigDecimal.valueOf(value);
  }

  public static BigDecimal toBigDecimal(@Nullable final Byte value) {
    return (value == null ? null : BigDecimal.valueOf(value.longValue()));
  }

  public static BigDecimal toBigDecimal(@Nullable final Byte value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : BigDecimal.valueOf(value.longValue()));
  }
}
