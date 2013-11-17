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
 * This class provides operations on <code>long</code> primitives and
 * {@link Long} objects.
 * <p>
 * This class tries to handle <code>null</code> input gracefully. An exception
 * will not be thrown for a <code>null</code> input. Each method documents its
 * behavior in more detail.
 * </p>
 * <p>
 * This class also handle the conversion from <code>long</code> values or
 * {@link Long} objects to common types.
 * </p>
 *
 * @author Haixing Hu
 */
public class LongUtils {

  /**
   * The default <code>long</code> value used when necessary.
   */
  public static final long DEFAULT = 0L;

  /**
   * The maximum value of an unsigned long.
   */
  public static final long UNSIGNED_MAX  = 0xFFFFFFFFFFFFFFFFL;

  public static long toPrimitive(@Nullable final Long value) {
    return (value == null ? DEFAULT : value.longValue());
  }

  public static long toPrimitive(@Nullable final Long value, final long defaultValue) {
    return (value == null ? defaultValue : value.longValue());
  }

  public static boolean toBoolean(final long value) {
    return (value != 0);
  }

  public static boolean toBoolean(@Nullable final Long value) {
    return (value == null ? BooleanUtils.DEFAULT : toBoolean(value.longValue()));
  }

  public static boolean toBoolean(@Nullable final Long value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : toBoolean(value.longValue()));
  }

  public static Boolean toBooleanObject(final long value) {
    return Boolean.valueOf(toBoolean(value));
  }

  public static Boolean toBooleanObject(@Nullable final Long value) {
    return (value == null ? null : toBooleanObject(value.longValue()));
  }

  public static Boolean toBooleanObject(@Nullable final Long value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : toBooleanObject(value.longValue()));
  }

  public static char toChar(final long value) {
    return (char) value;
  }

  public static char toChar(@Nullable final Long value) {
    return (value == null ? CharUtils.DEFAULT : toChar(value.longValue()));
  }

  public static char toChar(@Nullable final Long value,
      final char defaultValue) {
    return (value == null ? defaultValue : toChar(value.longValue()));
  }

  public static Character toCharObject(final long value) {
    return Character.valueOf(toChar(value));
  }

  public static Character toCharObject(@Nullable final Long value) {
    return (value == null ? null : toCharObject(value.longValue()));
  }

  public static Character toCharObject(@Nullable final Long value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : toCharObject(value.longValue()));
  }

  public static byte toByte(final long value) {
    return (byte) value;
  }

  public static byte toByte(@Nullable final Long value) {
    return (value == null ? ByteUtils.DEFAULT : toByte(value.longValue()));
  }

  public static byte toByte(@Nullable final Long value,
      final byte defaultValue) {
    return (value == null ? defaultValue : toByte(value.longValue()));
  }

  public static Byte toByteObject(final long value) {
    return Byte.valueOf(toByte(value));
  }

  public static Byte toByteObject(@Nullable final Long value) {
    return (value == null ? null : toByteObject(value.longValue()));
  }

  public static Byte toByteObject(@Nullable final Long value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : toByteObject(value.longValue()));
  }

  public static short toShort(final long value) {
    return (short) value;
  }

  public static short toShort(@Nullable final Long value) {
    return (value == null ? IntUtils.DEFAULT : toShort(value.longValue()));
  }

  public static short toShort(@Nullable final Long value,
      final short defaultValue) {
    return (value == null ? defaultValue : toShort(value.longValue()));
  }

  public static Short toShortObject(final long value) {
    return Short.valueOf(toShort(value));
  }

  public static Short toShortObject(@Nullable final Long value) {
    return (value == null ? null : toShortObject(value.longValue()));
  }

  public static Short toShortObject(@Nullable final Long value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : toShortObject(value.longValue()));
  }

  public static int toInt(final long value) {
    return (int) value;
  }

  public static int toInt(@Nullable final Long value) {
    return (value == null ? IntUtils.DEFAULT : toInt(value.longValue()));
  }

  public static int toInt(@Nullable final Long value,
      final int defaultValue) {
    return (value == null ? defaultValue : toInt(value.longValue()));
  }

  public static Integer toIntObject(final long value) {
    return Integer.valueOf(toInt(value));
  }

  public static Integer toIntObject(@Nullable final Long value) {
    return (value == null ? null : toIntObject(value.longValue()));
  }

  public static Integer toIntObject(@Nullable final Long value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : toIntObject(value.longValue()));
  }

  public static float toFloat(final long value) {
    return value;
  }

  public static float toFloat(@Nullable final Long value) {
    return (value == null ? FloatUtils.DEFAULT : toFloat(value.longValue()));
  }

  public static float toFloat(@Nullable final Long value,
      final float defaultValue) {
    return (value == null ? defaultValue : toFloat(value.longValue()));
  }

  public static Float toFloatObject(final long value) {
    return Float.valueOf(toFloat(value));
  }

  public static Float toFloatObject(@Nullable final Long value) {
    return (value == null ? null : toFloatObject(value.longValue()));
  }

  public static Float toFloatObject(@Nullable final Long value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : toFloatObject(value.longValue()));
  }

  public static double toDouble(final long value) {
    return value;
  }

  public static double toDouble(@Nullable final Long value) {
    return (value == null ? DoubleUtils.DEFAULT : toDouble(value.longValue()));
  }

  public static double toDouble(@Nullable final Long value,
      final double defaultValue) {
    return (value == null ? defaultValue : toDouble(value.longValue()));
  }

  public static Double toDoubleObject(final long value) {
    return Double.valueOf(toDouble(value));
  }

  public static Double toDoubleObject(@Nullable final Long value) {
    return (value == null ? null : toDoubleObject(value.longValue()));
  }

  public static Double toDoubleObject(@Nullable final Long value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : toDoubleObject(value.longValue()));
  }

  public static String toString(final long value) {
    return Long.toString(value, 10);
  }

  public static String toString(@Nullable final Long value) {
    return (value == null ? null : toString(value.longValue()));
  }

  public static String toString(@Nullable final Long value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.longValue()));
  }

  /**
   * Convert a <code>long</code> value into hex string.
   *
   * @param value
   *    the value to be converted.
   * @param builder
   *    a {@link StringBuilder} where to append the hex string.
   */
  public static void toHexString(final long value, final StringBuilder builder) {
    final char[] digits = NumberFormatSymbols.DEFAULT_UPPERCASE_DIGITS;
    final int high = (int) (value >>> 32);
    final int low  = (int) value;
    builder.append("0x")
           .append(digits[(high >>> 28) & 0x0F])
           .append(digits[(high >>> 24) & 0x0F])
           .append(digits[(high >>> 20) & 0x0F])
           .append(digits[(high >>> 16) & 0x0F])
           .append(digits[(high >>> 12) & 0x0F])
           .append(digits[(high >>> 8) & 0x0F])
           .append(digits[(high >>> 4) & 0x0F])
           .append(digits[high & 0x0F])
           .append(digits[(low >>> 28) & 0x0F])
           .append(digits[(low >>> 24) & 0x0F])
           .append(digits[(low >>> 20) & 0x0F])
           .append(digits[(low >>> 16) & 0x0F])
           .append(digits[(low >>> 12) & 0x0F])
           .append(digits[(low >>> 8) & 0x0F])
           .append(digits[(low >>> 4) & 0x0F])
           .append(digits[low & 0x0F]);
  }

  /**
   * Convert a <code>long</code> value into hex string.
   *
   * @param value
   *          the value to be converted.
   * @return the hex string of the value.
   */
  public static String toHexString(final long value) {
    final StringBuilder builder = new StringBuilder();
    toHexString(value, builder);
    return builder.toString();
  }

  public static Date toDate(final long value) {
    return new Date(value);
  }

  public static Date toDate(@Nullable final Long value) {
    return (value == null ? null : new Date(value.longValue()));
  }

  public static Date toDate(@Nullable final Long value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : new Date(value.longValue()));
  }


  public static byte[] toByteArray(final long value) {
    return toByteArray(value, ByteArrayUtils.DEFAULT_BYTE_ORDER);
  }

  public static byte[] toByteArray(final long value, final ByteOrder byteOrder) {
    final byte[] result = new byte[8];
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      result[0] = (byte) (value >>> 56);
      result[1] = (byte) (value >>> 48);
      result[2] = (byte) (value >>> 40);
      result[3] = (byte) (value >>> 32);
      result[4] = (byte) (value >>> 24);
      result[5] = (byte) (value >>> 16);
      result[6] = (byte) (value >>> 8);
      result[7] = (byte) value;
    } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
      result[7] = (byte) (value >>> 56);
      result[6] = (byte) (value >>> 48);
      result[5] = (byte) (value >>> 40);
      result[4] = (byte) (value >>> 32);
      result[3] = (byte) (value >>> 24);
      result[2] = (byte) (value >>> 16);
      result[1] = (byte) (value >>> 8);
      result[0] = (byte) value;
    } else {
      throw new UnsupportedByteOrderException(byteOrder);
    }
    return result;
  }

  public static byte[] toByteArray(@Nullable final Long value) {
    return (value == null ? null : toByteArray(value.longValue(),
        ByteArrayUtils.DEFAULT_BYTE_ORDER));
  }

  public static byte[] toByteArray(@Nullable final Long value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toByteArray(value.longValue(), byteOrder));
  }

  public static byte[] toByteArray(@Nullable final Long value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.longValue(),
        ByteArrayUtils.DEFAULT_BYTE_ORDER));
  }

  public static byte[] toByteArray(@Nullable final Long value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue : toByteArray(value.longValue(),
        byteOrder));
  }

  public static Class<?> toClass(final long value) {
    return Long.TYPE;
  }

  public static Class<?> toClass(@Nullable final Long value) {
    return (value == null ? null : Long.class);
  }

  public static Class<?> toClass(@Nullable final Long value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Long.class);
  }

  public static BigInteger toBigInteger(final long value) {
    return BigInteger.valueOf(value);
  }

  public static BigInteger toBigInteger(@Nullable final Long value) {
    return (value == null ? null : BigInteger.valueOf(value.longValue()));
  }

  public static BigInteger toBigInteger(@Nullable final Long value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : BigInteger.valueOf(value.longValue()));
  }

  public static BigDecimal toBigDecimal(final long value) {
    return BigDecimal.valueOf(value);
  }

  public static BigDecimal toBigDecimal(@Nullable final Long value) {
    return (value == null ? null : BigDecimal.valueOf(value.longValue()));
  }

  public static BigDecimal toBigDecimal(@Nullable final Long value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : BigDecimal.valueOf(value.longValue()));
  }
}
