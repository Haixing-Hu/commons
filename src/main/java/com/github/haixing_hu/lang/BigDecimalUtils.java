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
package com.github.haixing_hu.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.annotation.Nullable;

/**
 * This class provides operations on {@link BigDecimal} objects.
 * <p>
 * This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} input. Each method documents its
 * behavior in more detail.
 * </p>
 * <p>
 * This class also handle the conversion from {@link BigDecimal} objects to
 * common types.
 * </p>
 *
 * @author Haixing Hu
 */
public class BigDecimalUtils {

  public static boolean toBoolean(@Nullable final BigDecimal value) {
    return (value == null ? BooleanUtils.DEFAULT : (value.signum() != 0));
  }

  public static boolean toBoolean(@Nullable final BigDecimal value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : (value.signum() != 0));
  }

  public static Boolean toBooleanObject(@Nullable final BigDecimal value) {
    return (value == null ? null : Boolean.valueOf(value.signum() != 0));
  }

  public static Boolean toBooleanObject(@Nullable final BigDecimal value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : Boolean.valueOf(value.signum() != 0));
  }

  public static char toChar(@Nullable final BigDecimal value) {
    return (value == null ? CharUtils.DEFAULT : (char) value.shortValue());
  }

  public static char toChar(@Nullable final BigDecimal value,
      final char defaultValue) {
    return (value == null ? defaultValue : (char) value.shortValue());
  }

  public static Character toCharObject(@Nullable final BigDecimal value) {
    return (value == null ? null : Character.valueOf((char) value.shortValue()));
  }

  public static Character toCharObject(@Nullable final BigDecimal value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : Character.valueOf((char) value.shortValue()));
  }

  public static byte toByte(@Nullable final BigDecimal value) {
    return (value == null ? ByteUtils.DEFAULT : value.byteValue());
  }

  public static byte toByte(@Nullable final BigDecimal value,
      final byte defaultValue) {
    return (value == null ? defaultValue : value.byteValue());
  }

  public static Byte toByteObject(@Nullable final BigDecimal value) {
    return (value == null ? null : Byte.valueOf(value.byteValue()));
  }

  public static Byte toByteObject(@Nullable final BigDecimal value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : Byte.valueOf(value.byteValue()));
  }

  public static short toShort(@Nullable final BigDecimal value) {
    return (value == null ? IntUtils.DEFAULT : value.shortValue());
  }

  public static short toShort(@Nullable final BigDecimal value,
      final short defaultValue) {
    return (value == null ? defaultValue : value.shortValue());
  }

  public static Short toShortObject(@Nullable final BigDecimal value) {
    return (value == null ? null : Short.valueOf(value.shortValue()));
  }

  public static Short toShortObject(@Nullable final BigDecimal value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : Short.valueOf(value.shortValue()));
  }

  public static int toInt(@Nullable final BigDecimal value) {
    return (value == null ? IntUtils.DEFAULT : value.intValue());
  }

  public static int toInt(@Nullable final BigDecimal value,
      final int defaultValue) {
    return (value == null ? defaultValue : value.intValue());
  }

  public static Integer toIntObject(@Nullable final BigDecimal value) {
    return (value == null ? null : Integer.valueOf(value.intValue()));
  }

  public static Integer toIntObject(@Nullable final BigDecimal value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : Integer.valueOf(value.intValue()));
  }

  public static long toLong(@Nullable final BigDecimal value) {
    return (value == null ? LongUtils.DEFAULT : value.longValue());
  }

  public static long toLong(@Nullable final BigDecimal value,
      final long defaultValue) {
    return (value == null ? defaultValue : value.longValue());
  }

  public static Long toLongObject(@Nullable final BigDecimal value) {
    return (value == null ? null : Long.valueOf(value.longValue()));
  }

  public static Long toLongObject(@Nullable final BigDecimal value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : Long.valueOf(value.longValue()));
  }

  public static float toFloat(@Nullable final BigDecimal value) {
    return (value == null ? FloatUtils.DEFAULT : value.floatValue());
  }

  public static float toFloat(@Nullable final BigDecimal value,
      final float defaultValue) {
    return (value == null ? defaultValue : value.floatValue());
  }

  public static Float toFloatObject(@Nullable final BigDecimal value) {
    return (value == null ? null : Float.valueOf(value.floatValue()));
  }

  public static Float toFloatObject(@Nullable final BigDecimal value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : Float.valueOf(value.floatValue()));
  }

  public static double toDouble(@Nullable final BigDecimal value) {
    return (value == null ? DoubleUtils.DEFAULT : value.doubleValue());
  }

  public static double toDouble(@Nullable final BigDecimal value,
      final double defaultValue) {
    return (value == null ? defaultValue : value.doubleValue());
  }

  public static Double toDoubleObject(@Nullable final BigDecimal value) {
    return (value == null ? null : Double.valueOf(value.doubleValue()));
  }

  public static Double toDoubleObject(@Nullable final BigDecimal value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : Double.valueOf(value.doubleValue()));
  }

  public static String toString(@Nullable final BigDecimal value) {
    return (value == null ? null : value.toString());
  }

  public static String toString(@Nullable final BigDecimal value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : value.toString());
  }

  public static Date toDate(@Nullable final BigDecimal value) {
    return (value == null ? null : new Date(value.longValue()));
  }

  public static Date toDate(@Nullable final BigDecimal value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : new Date(value.longValue()));
  }

  public static byte[] toByteArray(@Nullable final BigDecimal value) {
    if (value == null) {
      return null;
    }
    if (value.signum() == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    }
    final int scale = value.scale();
    final byte[] scaleBytes = IntUtils.toByteArray(scale, ByteArrayUtils.DEFAULT_BYTE_ORDER);
    final BigInteger unscaledValue = value.unscaledValue();
    final byte[] unscaledValueBytes = unscaledValue.toByteArray();
    final byte[] result = new byte[scaleBytes.length + unscaledValueBytes.length];
    System.arraycopy(scaleBytes, 0, result, 0, scaleBytes.length);
    System.arraycopy(unscaledValueBytes, 0, result, scaleBytes.length, unscaledValueBytes.length);
    return result;
  }

  public static byte[] toByteArray(@Nullable final BigDecimal value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value));
  }

  public static Class<?> toClass(@Nullable final BigDecimal value) {
    return (value == null ? null : BigDecimal.class);
  }

  public static Class<?> toClass(@Nullable final BigDecimal value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : BigDecimal.class);
  }

  public static BigInteger toBigInteger(@Nullable final BigDecimal value) {
    return (value == null ? null : value.toBigInteger());
  }

  public static BigInteger toBigInteger(@Nullable final BigDecimal value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : value.toBigInteger());
  }
}
