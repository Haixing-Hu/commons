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

/**
 * This class provides operations on <code>double</code> primitives and
 * {@link Double} objects.
 * <p>
 * This class tries to handle <code>null</code> input gracefully. An exception
 * will not be thrown for a <code>null</code> input. Each method documents its
 * behavior in more detail.
 * </p>
 * <p>
 * This class also handle the conversion from <code>double</code> values or
 * {@link Double} objects to common types.
 * </p>
 *
 * @author Haixing Hu
 */
public class DoubleUtils {

  /**
   * The default <code>double</code> value used when necessary.
   */
  public static final double DEFAULT = 0.0;

  /**
   * The default epsilon for <code>double</code> values.
   */
  public static final double EPSILON = 0.0000001;

  public static double toPrimitive(@Nullable final Double value) {
    return (value == null ? DEFAULT : value.doubleValue());
  }

  public static double toPrimitive(@Nullable final Double value, final double defaultValue) {
    return (value == null ? defaultValue : value.doubleValue());
  }

  public static boolean toBoolean(final double value) {
    return (value != 0);
  }

  public static boolean toBoolean(@Nullable final Double value) {
    return (value == null ? BooleanUtils.DEFAULT : toBoolean(value.doubleValue()));
  }

  public static boolean toBoolean(@Nullable final Double value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : toBoolean(value.doubleValue()));
  }

  public static Boolean toBooleanObject(final double value) {
    return Boolean.valueOf(toBoolean(value));
  }

  public static Boolean toBooleanObject(@Nullable final Double value) {
    return (value == null ? null : toBooleanObject(value.doubleValue()));
  }

  public static Boolean toBooleanObject(@Nullable final Double value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : toBooleanObject(value.doubleValue()));
  }

  public static char toChar(final double value) {
    return (char) value;
  }

  public static char toChar(@Nullable final Double value) {
    return (value == null ? CharUtils.DEFAULT : toChar(value.doubleValue()));
  }

  public static char toChar(@Nullable final Double value,
      final char defaultValue) {
    return (value == null ? defaultValue : toChar(value.doubleValue()));
  }

  public static Character toCharObject(final double value) {
    return Character.valueOf(toChar(value));
  }

  public static Character toCharObject(@Nullable final Double value) {
    return (value == null ? null : toCharObject(value.doubleValue()));
  }

  public static Character toCharObject(@Nullable final Double value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : toCharObject(value.doubleValue()));
  }

  public static byte toByte(final double value) {
    return (byte) value;
  }

  public static byte toByte(@Nullable final Double value) {
    return (value == null ? ByteUtils.DEFAULT : toByte(value.doubleValue()));
  }

  public static byte toByte(@Nullable final Double value,
      final byte defaultValue) {
    return (value == null ? defaultValue : toByte(value.doubleValue()));
  }

  public static Byte toByteObject(final double value) {
    return Byte.valueOf(toByte(value));
  }

  public static Byte toByteObject(@Nullable final Double value) {
    return (value == null ? null : toByteObject(value.doubleValue()));
  }

  public static Byte toByteObject(@Nullable final Double value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : toByteObject(value.doubleValue()));
  }

  public static short toShort(final double value) {
    return (short) value;
  }

  public static short toShort(@Nullable final Double value) {
    return (value == null ? IntUtils.DEFAULT : toShort(value.doubleValue()));
  }

  public static short toShort(@Nullable final Double value,
      final short defaultValue) {
    return (value == null ? defaultValue : toShort(value.doubleValue()));
  }

  public static Short toShortObject(final double value) {
    return Short.valueOf(toShort(value));
  }

  public static Short toShortObject(@Nullable final Double value) {
    return (value == null ? null : toShortObject(value.doubleValue()));
  }

  public static Short toShortObject(@Nullable final Double value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : toShortObject(value.doubleValue()));
  }

  public static int toInt(final double value) {
    return (int) value;
  }

  public static int toInt(@Nullable final Double value) {
    return (value == null ? IntUtils.DEFAULT : toInt(value.doubleValue()));
  }

  public static int toInt(@Nullable final Double value,
      final int defaultValue) {
    return (value == null ? defaultValue : toInt(value.doubleValue()));
  }

  public static Integer toIntObject(final double value) {
    return Integer.valueOf(toInt(value));
  }

  public static Integer toIntObject(@Nullable final Double value) {
    return (value == null ? null : toIntObject(value.doubleValue()));
  }

  public static Integer toIntObject(@Nullable final Double value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : toIntObject(value.doubleValue()));
  }

  public static long toLong(final double value) {
    return (long) value;
  }

  public static long toLong(@Nullable final Double value) {
    return (value == null ? LongUtils.DEFAULT : toLong(value.doubleValue()));
  }

  public static long toLong(@Nullable final Double value,
      final long defaultValue) {
    return (value == null ? defaultValue : toLong(value.doubleValue()));
  }

  public static Long toLongObject(final double value) {
    return Long.valueOf(toLong(value));
  }

  public static Long toLongObject(@Nullable final Double value) {
    return (value == null ? null : toLongObject(value.doubleValue()));
  }

  public static Long toLongObject(@Nullable final Double value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : toLongObject(value.doubleValue()));
  }

  public static float toFloat(final double value) {
    return (float) value;
  }

  public static float toFloat(@Nullable final Double value) {
    return (value == null ? FloatUtils.DEFAULT : toFloat(value.doubleValue()));
  }

  public static float toFloat(@Nullable final Double value,
      final float defaultValue) {
    return (value == null ? defaultValue : toFloat(value.doubleValue()));
  }

  public static Float toFloatObject(final double value) {
    return Float.valueOf(toFloat(value));
  }

  public static Float toFloatObject(@Nullable final Double value) {
    return (value == null ? null : toFloatObject(value.doubleValue()));
  }

  public static Float toFloatObject(@Nullable final Double value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : toFloatObject(value.doubleValue()));
  }

  public static String toString(final double value) {
    return Double.toString(value);
  }

  public static String toString(@Nullable final Double value) {
    return (value == null ? null : toString(value.doubleValue()));
  }

  public static String toString(@Nullable final Double value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.doubleValue()));
  }

  public static Date toDate(final double value) {
    return new Date((long) value);
  }

  public static Date toDate(@Nullable final Double value) {
    return (value == null ? null : toDate(value.doubleValue()));
  }

  public static Date toDate(@Nullable final Double value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : toDate(value.doubleValue()));
  }

  public static byte[] toByteArray(final double value) {
    return toByteArray(value, SystemUtils.BYTE_ORDER);
  }

  public static byte[] toByteArray(final double value, final ByteOrder byteOrder) {
    final long bits = Double.doubleToLongBits(value);
    return LongUtils.toByteArray(bits, byteOrder);
  }

  public static byte[] toByteArray(@Nullable final Double value) {
    return (value == null ? null : toByteArray(value.doubleValue(),
        SystemUtils.BYTE_ORDER));
  }

  public static byte[] toByteArray(@Nullable final Double value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toByteArray(value.doubleValue(), byteOrder));
  }

  public static byte[] toByteArray(@Nullable final Double value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.doubleValue(),
        SystemUtils.BYTE_ORDER));
  }

  public static byte[] toByteArray(@Nullable final Double value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue : toByteArray(value.doubleValue(),
        byteOrder));
  }

  public static Class<?> toClass(final double value) {
    return Double.TYPE;
  }

  public static Class<?> toClass(@Nullable final Double value) {
    return (value == null ? null : Double.class);
  }

  public static Class<?> toClass(@Nullable final Double value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Double.class);
  }

  public static BigInteger toBigInteger(final double value) {
    return BigInteger.valueOf((long) value);
  }

  public static BigInteger toBigInteger(@Nullable final Double value) {
    return (value == null ? null : BigInteger.valueOf(value.longValue()));
  }

  public static BigInteger toBigInteger(@Nullable final Double value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : BigInteger.valueOf(value.longValue()));
  }

  public static BigDecimal toBigDecimal(final double value) {
    return BigDecimal.valueOf(value);
  }

  public static BigDecimal toBigDecimal(@Nullable final Double value) {
    return (value == null ? null : BigDecimal.valueOf(value.doubleValue()));
  }

  public static BigDecimal toBigDecimal(@Nullable final Double value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : BigDecimal.valueOf(value.doubleValue()));
  }
}
