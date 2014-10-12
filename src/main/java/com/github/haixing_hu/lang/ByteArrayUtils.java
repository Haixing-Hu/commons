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
import java.nio.ByteOrder;
import java.util.Date;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import com.github.haixing_hu.util.codec.string.HexCodec;

/**
 * This class provides operations on byte arrays.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class ByteArrayUtils {

  public static final ByteOrder DEFAULT_BYTE_ORDER = ByteOrder.BIG_ENDIAN;

  public static boolean toBoolean(@Nullable final byte[] value) {
    return toBoolean(value, BooleanUtils.DEFAULT);
  }

  public static boolean toBoolean(@Nullable final byte[] value, final boolean defaultValue) {
    if ((value == null) || (value.length == 0)) {
      return defaultValue;
    } else {
      return value[0] != 0;
    }
  }

  public static Boolean toBooleanObject(@Nullable final byte[] value) {
    return (value == null ? null : Boolean.valueOf(toBoolean(value)));
  }

  public static Boolean toBooleanObject(@Nullable final byte[] value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : Boolean.valueOf(toBoolean(value)));
  }

  public static char toChar(@Nullable final byte[] value) {
    return toChar(value, CharUtils.DEFAULT, DEFAULT_BYTE_ORDER);
  }

  public static char toChar(@Nullable final byte[] value,
      final ByteOrder byteOrder) {
    return toChar(value, CharUtils.DEFAULT, byteOrder);
  }

  public static char toChar(@Nullable final byte[] value,
      final char defaultValue) {
    return toChar(value, defaultValue, DEFAULT_BYTE_ORDER);
  }

  public static char toChar(@Nullable final byte[] value,
      final char defaultValue, final ByteOrder byteOrder) {
    if (value == null) {
      return defaultValue;
    } else {
      return (char) toShort(value, ShortUtils.DEFAULT, byteOrder);
    }
  }

  public static Character toCharObject(@Nullable final byte[] value) {
    return (value == null ? null : Character.valueOf(toChar(value)));
  }

  public static Character toCharObject(@Nullable final byte[] value, final ByteOrder byteOrder) {
    return (value == null ? null : Character.valueOf(toChar(value, byteOrder)));
  }

  public static Character toCharObject(@Nullable final byte[] value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : Character.valueOf(toChar(value)));
  }

  public static Character toCharObject(@Nullable final byte[] value,
      @Nullable final Character defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue : Character.valueOf(toChar(value, byteOrder)));
  }

  public static byte toByte(@Nullable final byte[] value) {
    return toByte(value, ByteUtils.DEFAULT);
  }

  public static byte toByte(@Nullable final byte[] value, final byte defaultValue) {
    if ((value == null) || (value.length == 0)) {
      return defaultValue;
    } else {
      return value[0];
    }
  }

  public static Byte toByteObject(@Nullable final byte[] value) {
    return (value == null ? null : Byte.valueOf(toByte(value)));
  }

  public static Byte toByteObject(@Nullable final byte[] value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : Byte.valueOf(toByte(value)));
  }

  public static short toShort(@Nullable final byte[] value) {
    return toShort(value, ShortUtils.DEFAULT, DEFAULT_BYTE_ORDER);
  }

  public static short toShort(@Nullable final byte[] value,
      final ByteOrder byteOrder) {
    return toShort(value, ShortUtils.DEFAULT, byteOrder);
  }

  public static short toShort(@Nullable final byte[] value,
      final short defaultValue) {
    return toShort(value, defaultValue, DEFAULT_BYTE_ORDER);
  }

  public static short toShort(@Nullable final byte[] value, final short defaultValue,
      final ByteOrder byteOrder) {
    if (value == null) {
      return defaultValue;
    }
    if (value.length < 2) {
      // invalid length
      return defaultValue;
    }
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      switch (value.length) {
        case 0:
          return 0;
        case 1:
          return (short) (value[0] << 8);
        default:
          return (short) ((value[0] << 8) | (value[1] & 0xFF));
      }
    } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
      switch (value.length) {
        case 0:
          return 0;
        case 1:
          return value[0];
        default:
          return (short) ((value[1] << 8) | (value[0] & 0xFF));
      }
    } else {
      throw new UnsupportedByteOrderException(byteOrder);
    }
  }

  public static Short toShortObject(@Nullable final byte[] value) {
    return (value == null ? null : Short.valueOf(toShort(value)));
  }

  public static Short toShortObject(@Nullable final byte[] value, final ByteOrder byteOrder) {
    return (value == null ? null : Short.valueOf(toShort(value, byteOrder)));
  }

  public static Short toShortObject(@Nullable final byte[] value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : Short.valueOf(toShort(value)));
  }

  public static Short toShortObject(@Nullable final byte[] value,
      @Nullable final Short defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue : Short.valueOf(toShort(value, byteOrder)));
  }

  public static int toInt(@Nullable final byte[] value) {
    return toInt(value, IntUtils.DEFAULT, DEFAULT_BYTE_ORDER);
  }

  public static int toInt(@Nullable final byte[] value, final ByteOrder byteOrder) {
    return toInt(value, IntUtils.DEFAULT, byteOrder);
  }

  public static int toInt(@Nullable final byte[] value, final int defaultValue) {
    return toInt(value, defaultValue, DEFAULT_BYTE_ORDER);
  }

  public static int toInt(@Nullable final byte[] value, final int defaultValue,
      final ByteOrder byteOrder) {
    if (value == null) {
      return defaultValue;
    }
    if (value.length < 4) {
      // invalid length
      return defaultValue;
    }
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      switch (value.length) {
        case 0:
          return 0;
        case 1:
          return (value[0] << 24);
        case 2:
          return (value[0] << 24)
               | (value[1] << 16);
        case 3:
          return (value[0] << 24)
               | (value[1] << 16)
               | (value[2] << 8);
        default:
          return (value[0] << 24)
               | (value[1] << 16)
               | (value[2] << 8)
               | value[3];
      }
    } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
      switch (value.length) {
        case 0:
          return 0;
        case 1:
          return value[0];
        case 2:
          return (value[1] << 8)
               | (value[0] & 0xFF);
        case 3:
          return (value[2] << 16)
               | (value[1] << 8)
               | (value[0] & 0xFF);
        default:
          return (value[3] << 24)
               | (value[2] << 16)
               | (value[1] << 8)
               | (value[0] & 0xFF);
      }
    } else {
      throw new UnsupportedByteOrderException(byteOrder);
    }
  }

  public static Integer toIntObject(@Nullable final byte[] value) {
    return (value == null ? null : Integer.valueOf(toInt(value)));
  }

  public static Integer toIntObject(@Nullable final byte[] value,
      final ByteOrder byteOrder) {
    return (value == null ? null : Integer.valueOf(toInt(value, byteOrder)));
  }

  public static Integer toIntObject(@Nullable final byte[] value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : Integer.valueOf(toInt(value)));
  }

  public static Integer toIntObject(@Nullable final byte[] value,
      @Nullable final Integer defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue : Integer.valueOf(toInt(value,
        byteOrder)));
  }

  public static long toLong(@Nullable final byte[] value) {
    return toLong(value, LongUtils.DEFAULT, DEFAULT_BYTE_ORDER);
  }

  public static long toLong(@Nullable final byte[] value,
      final ByteOrder byteOrder) {
    return toLong(value, LongUtils.DEFAULT, byteOrder);
  }

  public static long toLong(@Nullable final byte[] value,
      final long defaultValue) {
    return toLong(value, defaultValue, DEFAULT_BYTE_ORDER);
  }

  public static long toLong(@Nullable final byte[] value,
      final long defaultValue, final ByteOrder byteOrder) {
    if (value == null) {
      return defaultValue;
    }
    if (value.length < 8) {
      // invalid length
      return defaultValue;
    }
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      switch (value.length) {
        case 0:
          return 0;
        case 1:
          return ((value[0] & 0xFFL) << 56);
        case 2:
          return ((value[0] & 0xFFL) << 56)
               | ((value[1] & 0xFFL) << 48);
        case 3:
          return ((value[0] & 0xFFL) << 56)
               | ((value[1] & 0xFFL) << 48)
               | ((value[2] & 0xFFL) << 40);
        case 4:
          return ((value[0] & 0xFFL) << 56)
               | ((value[1] & 0xFFL) << 48)
               | ((value[2] & 0xFFL) << 40)
               | ((value[3] & 0xFFL) << 32);
        case 5:
          return ((value[0] & 0xFFL) << 56)
               | ((value[1] & 0xFFL) << 48)
               | ((value[2] & 0xFFL) << 40)
               | ((value[3] & 0xFFL) << 32)
               | ((value[4] & 0xFFL) << 24);
        case 6:
          return ((value[0] & 0xFFL) << 56)
               | ((value[1] & 0xFFL) << 48)
               | ((value[2] & 0xFFL) << 40)
               | ((value[3] & 0xFFL) << 32)
               | ((value[4] & 0xFFL) << 24)
               | ((value[5] & 0xFFL) << 16);
        case 7:
          return ((value[0] & 0xFFL) << 56)
               | ((value[1] & 0xFFL) << 48)
               | ((value[2] & 0xFFL) << 40)
               | ((value[3] & 0xFFL) << 32)
               | ((value[4] & 0xFFL) << 24)
               | ((value[5] & 0xFFL) << 16)
               | ((value[6] & 0xFFL) << 8);
        default:
          return ((value[0] & 0xFFL) << 56)
               | ((value[1] & 0xFFL) << 48)
               | ((value[2] & 0xFFL) << 40)
               | ((value[3] & 0xFFL) << 32)
               | ((value[4] & 0xFFL) << 24)
               | ((value[5] & 0xFFL) << 16)
               | ((value[6] & 0xFFL) << 8)
               | (value[7] & 0xFFL);
      }
    } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
      switch (value.length) {
        case 0:
          return 0;
        case 1:
          return (value[0] & 0xFFL);
        case 2:
          return ((value[1] & 0xFFL) << 8)
               | (value[0] & 0xFFL);
        case 3:
          return ((value[2] & 0xFFL) << 16)
               | ((value[1] & 0xFFL) << 8)
               | (value[0] & 0xFFL);
        case 4:
          return ((value[3] & 0xFFL) << 24)
               | ((value[2] & 0xFFL) << 16)
               | ((value[1] & 0xFFL) << 8)
               | (value[0] & 0xFFL);
        case 5:
          return ((value[4] & 0xFFL) << 32)
               | ((value[3] & 0xFFL) << 24)
               | ((value[2] & 0xFFL) << 16)
               | ((value[1] & 0xFFL) << 8)
               | (value[0] & 0xFFL);
        case 6:
          return ((value[5] & 0xFFL) << 40)
               | ((value[4] & 0xFFL) << 32)
               | ((value[3] & 0xFFL) << 24)
               | ((value[2] & 0xFFL) << 16)
               | ((value[1] & 0xFFL) << 8)
               | (value[0] & 0xFFL);
        case 7:
          return ((value[6] & 0xFFL) << 48)
               | ((value[5] & 0xFFL) << 40)
               | ((value[4] & 0xFFL) << 32)
               | ((value[3] & 0xFFL) << 24)
               | ((value[2] & 0xFFL) << 16)
               | ((value[1] & 0xFFL) << 8)
               | (value[0] & 0xFFL);
        default:
          return ((value[7] & 0xFFL) << 56)
               | ((value[6] & 0xFFL) << 48)
               | ((value[5] & 0xFFL) << 40)
               | ((value[4] & 0xFFL) << 32)
               | ((value[3] & 0xFFL) << 24)
               | ((value[2] & 0xFFL) << 16)
               | ((value[1] & 0xFFL) << 8)
               | (value[0] & 0xFFL);
      }
    } else {
      throw new UnsupportedByteOrderException(byteOrder);
    }
  }

  public static Long toLongObject(@Nullable final byte[] value) {
    return (value == null ? null : Long.valueOf(toLong(value)));
  }

  public static Long toLongObject(@Nullable final byte[] value,
      final ByteOrder byteOrder) {
    return (value == null ? null : Long.valueOf(toLong(value, byteOrder)));
  }

  public static Long toLongObject(@Nullable final byte[] value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : Long.valueOf(toLong(value)));
  }

  public static Long toLongObject(@Nullable final byte[] value,
      @Nullable final Long defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue : Long.valueOf(toLong(value, byteOrder)));
  }

  public static float toFloat(@Nullable final byte[] value) {
    return toFloat(value, FloatUtils.DEFAULT, DEFAULT_BYTE_ORDER);
  }

  public static float toFloat(@Nullable final byte[] value,
      final ByteOrder byteOrder) {
    return toFloat(value, FloatUtils.DEFAULT, byteOrder);
  }

  public static float toFloat(@Nullable final byte[] value,
      final float defaultValue) {
    return toFloat(value, defaultValue, DEFAULT_BYTE_ORDER);
  }

  public static float toFloat(@Nullable final byte[] value,
      final float defaultValue, final ByteOrder byteOrder) {
    if (value == null) {
      return defaultValue;
    } else {
      final int bits = toInt(value, byteOrder);
      return Float.intBitsToFloat(bits);
    }
  }

  public static Float toFloatObject(@Nullable final byte[] value) {
    return (value == null ? null : Float.valueOf(toFloat(value)));
  }

  public static Float toFloatObject(@Nullable final byte[] value, final ByteOrder byteOrder) {
    return (value == null ? null : Float.valueOf(toFloat(value, byteOrder)));
  }

  public static Float toFloatObject(@Nullable final byte[] value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : Float.valueOf(toFloat(value)));
  }

  public static Float toFloatObject(@Nullable final byte[] value,
      @Nullable final Float defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue : Float.valueOf(toFloat(value, byteOrder)));
  }

  public static double toDouble(@Nullable final byte[] value) {
    return toDouble(value, DoubleUtils.DEFAULT, DEFAULT_BYTE_ORDER);
  }

  public static double toDouble(@Nullable final byte[] value,
      final ByteOrder byteOrder) {
    return toDouble(value, DoubleUtils.DEFAULT, byteOrder);
  }

  public static double toDouble(@Nullable final byte[] value,
      final double defaultValue) {
    return toDouble(value, defaultValue, DEFAULT_BYTE_ORDER);
  }

  public static double toDouble(@Nullable final byte[] value,
      final double defaultValue, final ByteOrder byteOrder) {
    if (value == null) {
      return defaultValue;
    } else {
      final long bits = toLong(value, byteOrder);
      return Double.longBitsToDouble(bits);
    }
  }

  public static Double toDoubleObject(@Nullable final byte[] value) {
    return (value == null ? null : Double.valueOf(toDouble(value)));
  }

  public static Double toDoubleObject(@Nullable final byte[] value, final ByteOrder byteOrder) {
    return (value == null ? null : Double.valueOf(toDouble(value, byteOrder)));
  }

  public static Double toDoubleObject(@Nullable final byte[] value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : Double.valueOf(toDouble(value)));
  }

  public static Double toDoubleObject(@Nullable final byte[] value,
      @Nullable final Double defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue : Double.valueOf(toDouble(value, byteOrder)));
  }

  public static String toString(@Nullable final byte[] value) {
    return toString(value, null);
  }

  public static String toString(@Nullable final byte[] value,
      @Nullable final String defaultValue) {
    if (value == null) {
      return defaultValue;
    } else {
      return new HexCodec().encode(value);
    }
  }

  public static Date toDate(@Nullable final byte[] value) {
    return toDate(value, null, DEFAULT_BYTE_ORDER);
  }

  public static Date toDate(@Nullable final byte[] value,
      final ByteOrder byteOrder) {
    return toDate(value, null, byteOrder);
  }

  public static Date toDate(@Nullable final byte[] value,
      @Nullable final Date defaultValue) {
    return toDate(value, defaultValue, DEFAULT_BYTE_ORDER);
  }

  public static Date toDate(@Nullable final byte[] value,
      @Nullable final Date defaultValue, final ByteOrder byteOrder) {
    if (value == null) {
      return defaultValue;
    } else {
      final long time = toLong(value, byteOrder);
      return new Date(time);
    }
  }

  public static Class<?> toClass(@Nullable final byte[] value) {
    return toClass(value, null);
  }

  public static Class<?> toClass(@Nullable final byte[] value,
      @Nullable final Class<?> defaultValue) {
    if (value == null) {
      return defaultValue;
    } else {
      final String className = toString(value);
      try {
        return ClassUtils.getClass(className);
      } catch (final ClassNotFoundException e) {
        return defaultValue;
      }
    }
  }

  public static BigInteger toBigInteger(@Nullable final byte[] value) {
    return toBigInteger(value, null);
  }

  public static BigInteger toBigInteger(@Nullable final byte[] value,
      @Nullable final BigInteger defaultValue) {
    if (value == null) {
      return defaultValue;
    } else if (value.length == 0) {
      return BigInteger.ZERO;
    } else {
      try {
        return new BigInteger(value);
      } catch (final NumberFormatException e) {
        return defaultValue;
      }
    }
  }

  public static BigDecimal toBigDecimal(@Nullable final byte[] value) {
    return toBigDecimal(value, null);
  }

  public static BigDecimal toBigDecimal(@Nullable final byte[] value,
      @Nullable final BigDecimal defaultValue) {
    if (value == null) {
      return defaultValue;
    } else if (value.length == 0) {
      return BigDecimal.ZERO;
    } else if (value.length <= 4) {
      // invalid byte array
      return defaultValue;
    } else {
      final int scale = toInt(value, DEFAULT_BYTE_ORDER);
      final byte[] temp = new byte[value.length - 4];
      System.arraycopy(value, 4, temp, 0, temp.length);
      final BigInteger unscaledValue;
      try {
        unscaledValue = new BigInteger(temp);
      } catch (final NumberFormatException e) {
        // invalid byte array
        return defaultValue;
      }
      return new BigDecimal(unscaledValue, scale);
    }
  }
}
