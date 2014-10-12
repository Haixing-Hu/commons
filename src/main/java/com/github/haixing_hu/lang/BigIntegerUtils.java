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
 * This class provides operations on {@link BigInteger} objects.
 * <p>
 * This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} input. Each method documents its
 * behavior in more detail.
 * </p>
 * <p>
 * This class also handle the conversion from {@link BigInteger} objects to
 * common types.
 * </p>
 *
 * @author Haixing Hu
 */
public class BigIntegerUtils {

  public static boolean toBoolean(@Nullable final BigInteger value) {
    return (value == null ? BooleanUtils.DEFAULT : (value.signum() != 0));
  }

  public static boolean toBoolean(@Nullable final BigInteger value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : (value.signum() != 0));
  }

  public static Boolean toBooleanObject(@Nullable final BigInteger value) {
    return (value == null ? null : Boolean.valueOf(value.signum() != 0));
  }

  public static Boolean toBooleanObject(@Nullable final BigInteger value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : Boolean.valueOf(value.signum() != 0));
  }

  public static char toChar(@Nullable final BigInteger value) {
    return (value == null ? CharUtils.DEFAULT : (char) value.shortValue());
  }

  public static char toChar(@Nullable final BigInteger value,
      final char defaultValue) {
    return (value == null ? defaultValue : (char) value.shortValue());
  }

  public static Character toCharObject(@Nullable final BigInteger value) {
    return (value == null ? null : Character.valueOf((char) value.shortValue()));
  }

  public static Character toCharObject(@Nullable final BigInteger value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : Character.valueOf((char) value.shortValue()));
  }

  public static byte toByte(@Nullable final BigInteger value) {
    return (value == null ? ByteUtils.DEFAULT : value.byteValue());
  }

  public static byte toByte(@Nullable final BigInteger value,
      final byte defaultValue) {
    return (value == null ? defaultValue : value.byteValue());
  }

  public static Byte toByteObject(@Nullable final BigInteger value) {
    return (value == null ? null : Byte.valueOf(value.byteValue()));
  }

  public static Byte toByteObject(@Nullable final BigInteger value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : Byte.valueOf(value.byteValue()));
  }

  public static short toShort(@Nullable final BigInteger value) {
    return (value == null ? IntUtils.DEFAULT : value.shortValue());
  }

  public static short toShort(@Nullable final BigInteger value,
      final short defaultValue) {
    return (value == null ? defaultValue : value.shortValue());
  }

  public static Short toShortObject(@Nullable final BigInteger value) {
    return (value == null ? null : Short.valueOf(value.shortValue()));
  }

  public static Short toShortObject(@Nullable final BigInteger value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : Short.valueOf(value.shortValue()));
  }

  public static int toInt(@Nullable final BigInteger value) {
    return (value == null ? IntUtils.DEFAULT : value.intValue());
  }

  public static int toInt(@Nullable final BigInteger value,
      final int defaultValue) {
    return (value == null ? defaultValue : value.intValue());
  }

  public static Integer toIntObject(@Nullable final BigInteger value) {
    return (value == null ? null : Integer.valueOf(value.intValue()));
  }

  public static Integer toIntObject(@Nullable final BigInteger value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : Integer.valueOf(value.intValue()));
  }

  public static long toLong(@Nullable final BigInteger value) {
    return (value == null ? LongUtils.DEFAULT : value.longValue());
  }

  public static long toLong(@Nullable final BigInteger value,
      final long defaultValue) {
    return (value == null ? defaultValue : value.longValue());
  }

  public static Long toLongObject(@Nullable final BigInteger value) {
    return (value == null ? null : Long.valueOf(value.longValue()));
  }

  public static Long toLongObject(@Nullable final BigInteger value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : Long.valueOf(value.longValue()));
  }

  public static float toFloat(@Nullable final BigInteger value) {
    return (value == null ? FloatUtils.DEFAULT : value.floatValue());
  }

  public static float toFloat(@Nullable final BigInteger value,
      final float defaultValue) {
    return (value == null ? defaultValue : value.floatValue());
  }

  public static Float toFloatObject(@Nullable final BigInteger value) {
    return (value == null ? null : Float.valueOf(value.floatValue()));
  }

  public static Float toFloatObject(@Nullable final BigInteger value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : Float.valueOf(value.floatValue()));
  }

  public static double toDouble(@Nullable final BigInteger value) {
    return (value == null ? DoubleUtils.DEFAULT : value.doubleValue());
  }

  public static double toDouble(@Nullable final BigInteger value,
      final double defaultValue) {
    return (value == null ? defaultValue : value.doubleValue());
  }

  public static Double toDoubleObject(@Nullable final BigInteger value) {
    return (value == null ? null : Double.valueOf(value.doubleValue()));
  }

  public static Double toDoubleObject(@Nullable final BigInteger value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : Double.valueOf(value.doubleValue()));
  }

  public static String toString(@Nullable final BigInteger value) {
    return (value == null ? null : value.toString(10));
  }

  public static String toString(@Nullable final BigInteger value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : value.toString(10));
  }

  public static Date toDate(@Nullable final BigInteger value) {
    return (value == null ? null : new Date(value.longValue()));
  }

  public static Date toDate(@Nullable final BigInteger value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : new Date(value.longValue()));
  }

  public static byte[] toByteArray(@Nullable final BigInteger value) {
    if (value == null) {
      return null;
    } else if (value.signum() == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    } else {
      return value.toByteArray();
    }
  }

  public static byte[] toByteArray(@Nullable final BigInteger value,
      @Nullable final byte[] defaultValue) {
    if (value == null) {
      return defaultValue;
    } else if (value.signum() == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    } else {
      return value.toByteArray();
    }
  }

  public static Class<?> toClass(@Nullable final BigInteger value) {
    return (value == null ? null : BigInteger.class);
  }

  public static Class<?> toClass(@Nullable final BigInteger value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : BigInteger.class);
  }

  public static BigDecimal toBigDecimal(@Nullable final BigInteger value) {
    return (value == null ? null : new BigDecimal(value));
  }

  public static BigDecimal toBigDecimal(@Nullable final BigInteger value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : new BigDecimal(value));
  }
}
