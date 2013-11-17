/******************************************************************************
 *
 *    Copyright (c) 2009-2012  Ascent Dimension, Inc. All rights reserved.
 *
 ******************************************************************************/
package com.github.haixing_hu.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteOrder;
import java.util.Date;

import org.junit.Test;

import com.github.haixing_hu.lang.BooleanUtils;
import com.github.haixing_hu.lang.ByteUtils;
import com.github.haixing_hu.lang.CharUtils;
import com.github.haixing_hu.lang.DoubleUtils;
import com.github.haixing_hu.lang.FloatUtils;
import com.github.haixing_hu.lang.IntUtils;
import com.github.haixing_hu.lang.LongUtils;
import com.github.haixing_hu.lang.ShortUtils;
import com.github.haixing_hu.lang.UnsupportedByteOrderException;

import static org.junit.Assert.*;

/**
 * @author Hongming Ji
 */
public class ShortUtilsTest {
  
  private static final short[] AREA = {Short.MIN_VALUE, Short.MIN_VALUE/2, (short) -1,(short) 0,
                                       (short) 1, Short.MAX_VALUE/2, Short.MAX_VALUE};

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toPrimitive(java.lang.Short)}.
   */
  @Test
  public void testToPrimitive_Short() {
    assertEquals(ShortUtils.DEFAULT, ShortUtils.toPrimitive(null));
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = AREA[i];
      assertEquals(AREA[i], ShortUtils.toPrimitive(x));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toPrimitive(java.lang.Short, short)}.
   */
  @Test
  public void testToPrimitive_Short_short() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals(AREA[i], ShortUtils.toPrimitive(null, AREA[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = AREA[i];
      for ( int j = 0; j < AREA.length; ++j) {
        assertEquals(AREA[i], ShortUtils.toPrimitive(x, AREA[j]));
      }
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toBoolean(short)}.
   */
  @Test
  public void testToBoolean_short() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals( AREA[i] != 0, ShortUtils.toBoolean(AREA[i]));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toBoolean(java.lang.Short)}.
   */
  @Test
  public void testToBoolean_Short() {
    assertEquals(BooleanUtils.DEFAULT, ShortUtils.toBoolean(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = AREA[i];
      assertEquals( AREA[i] != 0, ShortUtils.toBoolean(x));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toBoolean(java.lang.Short, boolean)}.
   */
  @Test
  public void testToBoolean_Short_boolean() {
    assertEquals(true, ShortUtils.toBoolean(null, true));
    assertEquals(false, ShortUtils.toBoolean(null, false));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = AREA[i];
      assertEquals( AREA[i] != 0, ShortUtils.toBoolean(x, true));
      assertEquals( AREA[i] != 0, ShortUtils.toBoolean(x, false));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toBooleanObject(short)}.
   */
  @Test
  public void testToBooleanObject_short() {
    for ( int i = 0; i < AREA.length; ++i) {
      final Boolean x = (AREA[i] != 0);
      assertEquals(x, ShortUtils.toBooleanObject(AREA[i]));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toBooleanObject(java.lang.Short)}.
   */
  @Test
  public void testToBooleanObject_Short() {
    assertEquals(null, ShortUtils.toBooleanObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = AREA[i];
      final Boolean y = (AREA[i] != 0);
      assertEquals(y, ShortUtils.toBooleanObject(x));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toBooleanObject(java.lang.Short, java.lang.Boolean)}.
   */
  @Test
  public void testToBooleanObject_Short_Boolean() {
    assertEquals(null, ShortUtils.toBooleanObject(null, null));
    
    final Boolean xa = true;
    final Boolean xb = false;
    assertEquals(true, ShortUtils.toBooleanObject(null, xa));
    assertEquals(false, ShortUtils.toBooleanObject(null, xb));
    for ( int i = 0; i < AREA.length; ++i) {
      final Short y = AREA[i];
      assertEquals( AREA[i] != 0, ShortUtils.toBooleanObject(y, xa));
      assertEquals( AREA[i] != 0, ShortUtils.toBooleanObject(y, xa));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toChar(short)}.
   */
  @Test
  public void testToChar_short() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals((char) AREA[i], ShortUtils.toChar(AREA[i]));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toChar(java.lang.Short)}.
   */
  @Test
  public void testToChar_Short() {
    assertEquals(CharUtils.DEFAULT, ShortUtils.toChar(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = AREA[i];
      assertEquals((char) AREA[i], ShortUtils.toChar(x));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toChar(java.lang.Short, char)}.
   */
  @Test
  public void testToChar_Short_char() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals((char) AREA[i], ShortUtils.toChar(null, (char) AREA[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = AREA[i];
      for ( int j = 0; j < AREA.length; ++j) {
        assertEquals((char) AREA[i], ShortUtils.toChar(x, (char) AREA[j]));
      }
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toCharObject(short)}.
   */
  @Test
  public void testToCharObject_short() {
    for ( int i = 0; i < AREA.length; ++i) {
      final Character x = (char) AREA[i];
      assertEquals(x, ShortUtils.toCharObject(AREA[i]));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toCharObject(java.lang.Short)}.
   */
  @Test
  public void testToCharObject_Short() {
    assertEquals(null, ShortUtils.toCharObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = AREA[i];
      final Character y = (char) AREA[i];
      assertEquals(y, ShortUtils.toCharObject(x));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toCharObject(java.lang.Short, java.lang.Character)}.
   */
  @Test
  public void testToCharObject_Short_Character() {
    final int[] a = {Integer.MIN_VALUE, Integer.MIN_VALUE/2, (int) -1, (int) 0, (int) 1,
                   Integer.MAX_VALUE/2, Integer.MAX_VALUE};
    
    assertEquals(null, ShortUtils.toCharObject(null, null));
    
    for ( int i = 0; i < a.length; ++i) {
      final Character x = (char) a[i];
      assertEquals(x, ShortUtils.toCharObject(null, x));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Character x = (char) AREA[i];
      final Short y = (short) AREA[i];
      assertEquals(x, ShortUtils.toCharObject(y, null));
      for ( int j = 0; j< a.length; ++j) {
        final Character z = (char) a[j];
        assertEquals(x, ShortUtils.toCharObject(y, z));
      }
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toByte(short)}.
   */
  @Test
  public void testToByte_short() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals((byte) AREA[i], ShortUtils.toByte(AREA[i]));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toByte(java.lang.Short)}.
   */
  @Test
  public void testToByte_Short() {
    assertEquals(ByteUtils.DEFAULT, ShortUtils.toByte(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = AREA[i];
      assertEquals((byte) AREA[i], ShortUtils.toByte(x));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toByte(java.lang.Short, byte)}.
   */
  @Test
  public void testToByte_Short_byte() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals((byte) AREA[i], ShortUtils.toByte(null, (byte) AREA[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = AREA[i];
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        assertEquals((byte) AREA[i], ShortUtils.toByte(x, (byte) j));
      }
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toByteObject(short)}.
   */
  @Test
  public void testToByteObject_short() {
    for ( int i = 0; i < AREA.length; ++i) {
      final Byte x = (byte) AREA[i];
      assertEquals(x, ShortUtils.toByteObject(AREA[i]));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toByteObject(java.lang.Short)}.
   */
  @Test
  public void testToByteObject_Short() {
    assertEquals(null, ShortUtils.toByteObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = AREA[i];
      final Byte y = (byte) AREA[i];
      assertEquals(y, ShortUtils.toByteObject(x));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toByteObject(java.lang.Short, java.lang.Byte)}.
   */
  @Test
  public void testToByteObject_Short_Byte() {
    assertEquals(null, ShortUtils.toByteObject(null, null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Byte x = (byte) AREA[i];
      assertEquals(x, ShortUtils.toByteObject(null, x));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = AREA[i];
      final Byte y = (byte) AREA[i];
      assertEquals(y, ShortUtils.toByteObject(x, null));
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final Byte z = (byte) j;
        assertEquals(y, ShortUtils.toByteObject(x, z));
      }
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toInt(short)}.
   */
  @Test
  public void testToInt_short() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals(AREA[i], ShortUtils.toInt(AREA[i]));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toInt(java.lang.Short)}.
   */
  @Test
  public void testToInt_Short() {
    assertEquals(IntUtils.DEFAULT, ShortUtils.toInt(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = AREA[i];
      assertEquals(AREA[i], ShortUtils.toInt(x));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toInt(java.lang.Short, int)}.
   */
  @Test
  public void testToInt_Short_int() {
    final int[] a = {Integer.MIN_VALUE, Integer.MIN_VALUE/2, -1, 0, 1,
                     Integer.MAX_VALUE/2, Integer.MAX_VALUE};
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], ShortUtils.toInt(null, a[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      for ( int j = 0; j < a.length; ++j) {
        assertEquals(AREA[i], ShortUtils.toInt(x, a[j]));
      }
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toIntObject(short)}.
   */
  @Test
  public void testToIntObject_short() {
    for ( int i = 0; i < AREA.length; ++i) {
      final Integer x = (int) AREA[i];
      assertEquals(x, ShortUtils.toIntObject(AREA[i]));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toIntObject(java.lang.Short)}.
   */
  @Test
  public void testToIntObject_Short() {
    assertEquals(null, ShortUtils.toIntObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = AREA[i];
      final Integer y = (int) AREA[i];
      assertEquals(y, ShortUtils.toIntObject(x));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toIntObject(java.lang.Short, java.lang.Integer)}.
   */
  @Test
  public void testToIntObject_Short_Integer() {
    final Integer[] a = {Integer.MIN_VALUE, Integer.MIN_VALUE/2, -1, 0, 1,
                     Integer.MAX_VALUE/2, Integer.MAX_VALUE};
    
    assertEquals(null, ShortUtils.toIntObject(null, null));
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], ShortUtils.toIntObject(null, a[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = AREA[i];
      final Integer y = (int) AREA[i];
      assertEquals(y, ShortUtils.toIntObject(x, null));
      for ( int j = 0; j < a.length; ++j) {
        assertEquals(y, ShortUtils.toIntObject(x, a[j]));
      }
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toLong(short)}.
   */
  @Test
  public void testToLong_short() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals((long) AREA[i], ShortUtils.toLong(AREA[i]));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toLong(java.lang.Short)}.
   */
  @Test
  public void testToLong_Short() {
    assertEquals(LongUtils.DEFAULT, ShortUtils.toLong(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = AREA[i];
      assertEquals((long) AREA[i], ShortUtils.toLong(x));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toLong(java.lang.Short, long)}.
   */
  @Test
  public void testToLong_Short_long() {
    final long[] a = {Long.MIN_VALUE, Long.MIN_VALUE/2, (long) -1, (long) 0, (long) 1,
                    Long.MAX_VALUE/2, Long.MAX_VALUE};
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], ShortUtils.toLong(null, a[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      for ( int j = 0; j < a.length; ++j) {
        assertEquals((long) AREA[i], ShortUtils.toLong(x, a[j]));
      }
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toLongObject(short)}.
   */
  @Test
  public void testToLongObject_short() {
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = (long) AREA[i];
      assertEquals(x, ShortUtils.toLongObject((short) AREA[i]));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toLongObject(java.lang.Short)}.
   */
  @Test
  public void testToLongObject_Short() {
    assertEquals(null, ShortUtils.toLongObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      final Long y = (long) AREA[i];
      assertEquals(y, ShortUtils.toLongObject(x));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toLongObject(java.lang.Short, java.lang.Long)}.
   */
  @Test
  public void testToLongObject_Short_Long() {
    final Long[] a = {Long.MIN_VALUE, Long.MIN_VALUE/2, (long) -1, (long) 0, (long) 1,
                      Long.MAX_VALUE/2, Long.MAX_VALUE};
    
    assertEquals(null, ShortUtils.toLongObject(null, null));
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], ShortUtils.toLongObject(null, a[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      final Long y = (long) AREA[i];
      assertEquals(y, ShortUtils.toLongObject(x, null));
      for ( int j = 0; j < a.length; ++j) {
        assertEquals(y, ShortUtils.toLongObject(x, a[j]));
      }
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toFloat(short)}.
   */
  @Test
  public void testToFloat_short() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals((float) AREA[i], ShortUtils.toFloat((short) AREA[i]), FloatUtils.EPSILON);
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toFloat(java.lang.Short)}.
   */
  @Test
  public void testToFloat_Short() {
    assertEquals(FloatUtils.DEFAULT, ShortUtils.toFloat(null), FloatUtils.EPSILON);
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      assertEquals((float) AREA[i], ShortUtils.toFloat(x), FloatUtils.EPSILON);
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toFloat(java.lang.Short, float)}.
   */
  @Test
  public void testToFloat_Short_float() {
    final float[] a = {Float.MIN_VALUE, Float.MIN_VALUE/2, (float) -1, (float) 0, (float) 1,
                       Float.MAX_VALUE/2, Float.MAX_VALUE};
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], ShortUtils.toFloat(null, a[i]), FloatUtils.EPSILON);
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      for ( int j = 0; j < a.length; ++j) {
        assertEquals((float) AREA[i], ShortUtils.toFloat(x, a[j]), FloatUtils.EPSILON);
      }
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toFloatObject(short)}.
   */
  @Test
  public void testToFloatObject_short() {
    for ( int i = 0; i < AREA.length; ++i) {
      final Float x = (float) AREA[i];
      assertEquals(x, ShortUtils.toFloatObject((short) AREA[i]));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toFloatObject(java.lang.Short)}.
   */
  @Test
  public void testToFloatObject_Short() {
    assertEquals(null, ShortUtils.toFloatObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      final Float y = (float) AREA[i];
      assertEquals(y, ShortUtils.toFloatObject(x));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toFloatObject(java.lang.Short, java.lang.Float)}.
   */
  @Test
  public void testToFloatObject_Short_Float() {
    final Float[] a = {Float.MIN_VALUE, Float.MIN_VALUE/2, (float) -1, (float) 0, (float) 1,
                       Float.MAX_VALUE/2, Float.MAX_VALUE};
    
    assertEquals(null, ShortUtils.toFloatObject(null, null));
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], ShortUtils.toFloatObject(null, a[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      final Float y = (float) AREA[i];
      assertEquals(y, ShortUtils.toFloatObject(x, null));
      for ( int j = 0; j < a.length; ++j) {
        assertEquals(y, ShortUtils.toFloatObject(x, a[j]));
      }
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toDouble(short)}.
   */
  @Test
  public void testToDouble_short() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals((double) AREA[i], ShortUtils.toDouble((short) AREA[i]), DoubleUtils.EPSILON);
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toDouble(java.lang.Short)}.
   */
  @Test
  public void testToDouble_Short() {
    assertEquals(DoubleUtils.DEFAULT, ShortUtils.toDouble(null), DoubleUtils.EPSILON);
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      assertEquals((double) AREA[i], ShortUtils.toDouble(x), DoubleUtils.EPSILON);
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toDouble(java.lang.Short, double)}.
   */
  @Test
  public void testToDouble_Short_double() {
    final double[] a = {Double.MIN_VALUE, Double.MIN_VALUE/2, (double) -1, (double) 0, (double) 1,
                        Double.MAX_VALUE/2, Double.MAX_VALUE};
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], ShortUtils.toDouble(null, a[i]), DoubleUtils.EPSILON);
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      for ( int j = 0; j < a.length; ++j) {
        assertEquals((double) AREA[i], ShortUtils.toDouble(x, a[j]), DoubleUtils.EPSILON);
      }
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toDoubleObject(short)}.
   */
  @Test
  public void testToDoubleObject_short() {
    for ( int i = 0; i < AREA.length; ++i) {
      final Double x = (double) AREA[i];
      assertEquals(x, ShortUtils.toDoubleObject((short) AREA[i]));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toDoubleObject(java.lang.Short)}.
   */
  @Test
  public void testToDoubleObject_Short() {
    assertEquals(null, ShortUtils.toDoubleObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      final Double y = (double) AREA[i];
      assertEquals(y, ShortUtils.toDoubleObject(x));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toDoubleObject(java.lang.Short, java.lang.Double)}.
   */
  @Test
  public void testToDoubleObject_Short_Double() {
    final Double[] a = {Double.MIN_VALUE, Double.MIN_VALUE/2, (double) -1, (double) 0, (double) 1,
                        Double.MAX_VALUE/2, Double.MAX_VALUE};
    
    assertEquals(null, ShortUtils.toDoubleObject(null, null));
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], ShortUtils.toDoubleObject(null, a[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      final Double y = (double) AREA[i];
      assertEquals(y, ShortUtils.toDoubleObject(x, null));
      for ( int j = 0; j < a.length; ++j) {
        assertEquals(y, ShortUtils.toDoubleObject(x, a[j]));
      }
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toString(short)}.
   */
  @Test
  public void testToString_short() {
    for ( int i = 0; i < AREA.length; ++i) {
      final String x = Integer.toString(AREA[i], 10);
      assertEquals(x, ShortUtils.toString((short) AREA[i]));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toString(java.lang.Short)}.
   */
  @Test
  public void testToString_Short() {
    assertEquals(null, ShortUtils.toString(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      final String y = Integer.toString(AREA[i], 10);
      assertEquals(y, ShortUtils.toString(x));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toString(java.lang.Short, java.lang.String)}.
   */
  @Test
  public void testToString_Short_String() {
    assertEquals(null, ShortUtils.toString(null, null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final String x = Integer.toString(AREA[i], 10);
      assertEquals(x, ShortUtils.toString(null, x));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      final String y = Integer.toString(AREA[i], 10);
      assertEquals(y, ShortUtils.toString(x, null));
	    for ( int j = Byte.MIN_VALUE; j <= Byte.MIN_VALUE; ++j) {
	      final String z = Integer.toString(j, 10);
        assertEquals(y, ShortUtils.toString(x, z));
	    }
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toHexString(short, java.lang.StringBuilder)}.
   */
  @Test
  public void testToHexString_short_StringBuilder() {
    StringBuilder builder = new StringBuilder();
    
    builder.setLength(0);
    ShortUtils.toHexString((short)0x0000, builder);
    assertEquals("0x0000", builder.toString());
    
    builder.setLength(0);
    ShortUtils.toHexString((short)0x000A, builder);
    assertEquals("0x000A", builder.toString());
    
    builder.setLength(0);
    ShortUtils.toHexString((short)0x00AB, builder);
    assertEquals("0x00AB", builder.toString());
    
    builder.setLength(0);
    ShortUtils.toHexString((short)0x00FF, builder);
    assertEquals("0x00FF", builder.toString());
    
    builder.setLength(0);
    ShortUtils.toHexString((short)0xAAAA, builder);
    assertEquals("0xAAAA", builder.toString());
    
    builder.setLength(0);
    ShortUtils.toHexString((short)0xFFFF, builder);
    assertEquals("0xFFFF", builder.toString());
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toHexString(short)}.
   */
  @Test
  public void testToHexString_short() {
    assertEquals("0x0000", ShortUtils.toHexString((short) 0x0000));
    assertEquals("0x000A", ShortUtils.toHexString((short) 0x000A));
    assertEquals("0x00AB", ShortUtils.toHexString((short) 0x00AB));
    assertEquals("0x00FF", ShortUtils.toHexString((short) 0x00FF));
    assertEquals("0xAAAA", ShortUtils.toHexString((short) 0xAAAA));
    assertEquals("0xFFFF", ShortUtils.toHexString((short) 0xFFFF));
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toDate(short)}.
   */
  @Test
  public void testToDate_short() {
    for ( int i = 0; i < AREA.length; ++i) {
      final Date x = new Date(AREA[i]);
      assertEquals(x, ShortUtils.toDate((short) AREA[i]));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toDate(java.lang.Short)}.
   */
  @Test
  public void testToDate_Short() {
    assertEquals(null, ShortUtils.toDate(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      final Date y = new Date(AREA[i]);
      assertEquals(y, ShortUtils.toDate(x));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toDate(java.lang.Short, java.util.Date)}.
   */
  @Test
  public void testToDate_Short_Date() {
    final long[] a = {Long.MIN_VALUE, Long.MIN_VALUE/2, (long) -1, (long) 0, (long) 1,
                      Long.MAX_VALUE/2, Long.MAX_VALUE};
    
    assertEquals(null, ShortUtils.toDate(null, null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Date x = new Date(AREA[i]);
      assertEquals(x, ShortUtils.toDate(null, x));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      final Date y = new Date(AREA[i]);
      assertEquals(y, ShortUtils.toDate(x, null));
      for ( int j = 0; j < a.length; ++j) {
        final Date z = new Date(a[j]);
        assertEquals(y, ShortUtils.toDate(x, z));
      }
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toByteArray(short)}.
   */
  @Test
  public void testToByteArray_short() {
    final byte[] xa = {(byte) 0x00, (byte) 0x00};
    assertArrayEquals(xa, ShortUtils.toByteArray((short) 0x0000));
    
    final byte[] xb = {(byte) 0xAB, (byte) 0x00};
    assertArrayEquals(xb, ShortUtils.toByteArray((short) 0xAB00));
    
    final byte[] xc = {(byte) 0xAB, (byte) 0xAB};
    assertArrayEquals(xc, ShortUtils.toByteArray((short) 0xABAB));
    
    final byte[] xd = {(byte) 0xFF, (byte) 0x00};
    assertArrayEquals(xd, ShortUtils.toByteArray((short) 0xFF00));
    
    final byte[] xe = {(byte) 0xFF, (byte) 0xFF};
    assertArrayEquals(xe, ShortUtils.toByteArray((short) 0xFFFF));
    
    final byte[] xf = {(byte) 0xBB, (byte) 0xAA};
    assertArrayEquals(xf, ShortUtils.toByteArray((short) 0xBBAA));
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toByteArray(short, java.nio.ByteOrder)}.
   */
  @Test
  public void testToByteArray_short_ByteOrder() {
    final byte[] xa = {(byte) 0x00, (byte) 0x00};
    assertArrayEquals(xa, ShortUtils.toByteArray((short) 0x0000, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xa, ShortUtils.toByteArray((short) 0x0000, ByteOrder.BIG_ENDIAN));
    
    final byte[] xb = {(byte) 0xAB, (byte) 0x00};
    assertArrayEquals(xb, ShortUtils.toByteArray((short) 0x00AB, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xb, ShortUtils.toByteArray((short) 0xAB00, ByteOrder.BIG_ENDIAN));
    
    final byte[] xc = {(byte) 0xAB, (byte) 0xAB};
    assertArrayEquals(xc, ShortUtils.toByteArray((short) 0xABAB, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xc, ShortUtils.toByteArray((short) 0xABAB, ByteOrder.BIG_ENDIAN));
    
    final byte[] xd = {(byte) 0xFF, (byte) 0x00};
    assertArrayEquals(xd, ShortUtils.toByteArray((short) 0x00FF, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xd, ShortUtils.toByteArray((short) 0xFF00, ByteOrder.BIG_ENDIAN));
    
    final byte[] xe = {(byte) 0xFF, (byte) 0xFF};
    assertArrayEquals(xe, ShortUtils.toByteArray((short) 0xFFFF, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xe, ShortUtils.toByteArray((short) 0xFFFF, ByteOrder.BIG_ENDIAN));
    
    final byte[] xf = {(byte) 0xBB, (byte) 0xAA};
    assertArrayEquals(xf, ShortUtils.toByteArray((short) 0xAABB, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xf, ShortUtils.toByteArray((short) 0xBBAA, ByteOrder.BIG_ENDIAN));
    
    try {
      ShortUtils.toByteArray((short)10, null);
      fail("should throw");
    } catch (UnsupportedByteOrderException e) {
      // pass
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toByteArray(java.lang.Short)}.
   */
  @Test
  public void testToByteArray_Short() {
    assertArrayEquals(null, ShortUtils.toByteArray(null));
    
    final byte[] xa = {(byte) 0x00, (byte) 0x00};
    final Short ya = (short) 0x0000;
    assertArrayEquals(xa, ShortUtils.toByteArray(ya));
    
    final byte[] xb = {(byte) 0xAB, (byte) 0x00};
    final Short yb = (short) 0xAB00;
    assertArrayEquals(xb, ShortUtils.toByteArray(yb));
    
    final byte[] xc = {(byte) 0xAB, (byte) 0xAB};
    final Short yc = (short) 0xABAB;
    assertArrayEquals(xc, ShortUtils.toByteArray(yc));

    final byte[] xd = {(byte) 0xFF, (byte) 0x00};
    final Short yd = (short) 0xFF00;
    assertArrayEquals(xd, ShortUtils.toByteArray(yd));
    
    final byte[] xe = {(byte) 0xFF, (byte) 0xFF};
    final Short ye = (short) 0xFFFF;
    assertArrayEquals(xe, ShortUtils.toByteArray(ye));
    
    final byte[] xf = {(byte) 0xBB, (byte) 0xAA};
    final Short yf = (short) 0xBBAA;
    assertArrayEquals(xf, ShortUtils.toByteArray(yf));
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toByteArray(java.lang.Short, java.nio.ByteOrder)}.
   */
  @Test
  public void testToByteArray_Short_ByteOrder() {
    assertArrayEquals(null, ShortUtils.toByteArray(null, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(null, ShortUtils.toByteArray(null, ByteOrder.LITTLE_ENDIAN));
    
    final byte[] xa = {(byte) 0x00, (byte) 0x00};
    final Short ya = (short) 0x0000;
    assertArrayEquals(xa, ShortUtils.toByteArray(ya, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xa, ShortUtils.toByteArray(ya, ByteOrder.BIG_ENDIAN));
    
    final byte[] xb = {(byte) 0xAB, (byte) 0x00};
    final Short yb1 = (short) 0x00AB;
    assertArrayEquals(xb, ShortUtils.toByteArray(yb1, ByteOrder.LITTLE_ENDIAN));
    final Short yb2 = (short) 0xAB00;
    assertArrayEquals(xb, ShortUtils.toByteArray(yb2, ByteOrder.BIG_ENDIAN));
    
    final byte[] xc = {(byte) 0xAB, (byte) 0xAB};
    final Short yc = (short) 0xABAB;
    assertArrayEquals(xc, ShortUtils.toByteArray(yc, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xc, ShortUtils.toByteArray(yc, ByteOrder.BIG_ENDIAN));
    
    final byte[] xd = {(byte) 0xFF, (byte) 0x00};
    final Short yd1 = (short) 0x00FF;
    assertArrayEquals(xd, ShortUtils.toByteArray(yd1, ByteOrder.LITTLE_ENDIAN));
    final Short yd2 = (short) 0xFF00;
    assertArrayEquals(xd, ShortUtils.toByteArray(yd2, ByteOrder.BIG_ENDIAN));
    
    final byte[] xe = {(byte) 0xFF, (byte) 0xFF};
    final Short ye = (short) 0xFFFF;
    assertArrayEquals(xe, ShortUtils.toByteArray(ye, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xe, ShortUtils.toByteArray(ye, ByteOrder.BIG_ENDIAN));
    
    final byte[] xf = {(byte) 0xBB, (byte) 0xAA};
    final Short yf1 = (short) 0xAABB;
    assertArrayEquals(xf, ShortUtils.toByteArray(yf1, ByteOrder.LITTLE_ENDIAN));
    final Short yf2 = (short) 0xBBAA;
    assertArrayEquals(xf, ShortUtils.toByteArray(yf2, ByteOrder.BIG_ENDIAN));
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toByteArray(java.lang.Short, byte[])}.
   */
  @Test
  public void testToByteArray_Short_byteArray() {
    final byte[] x = null;
    assertEquals(null, ShortUtils.toByteArray(null, x));
    
    final byte[] a = {(byte) 0x00, (byte) 0x00};
    assertArrayEquals(a, ShortUtils.toByteArray(null, a));
    final byte[] b = {(byte) 0xAB, (byte) 0x00};
    assertArrayEquals(b, ShortUtils.toByteArray(null, b));
    final byte[] c = {(byte) 0xAB, (byte) 0xAB};
    assertArrayEquals(c, ShortUtils.toByteArray(null, c));
    final byte[] d = {(byte) 0xFF, (byte) 0x00};
    assertArrayEquals(d, ShortUtils.toByteArray(null, d));
    final byte[] e = {(byte) 0xFF, (byte) 0xFF};
    assertArrayEquals(e, ShortUtils.toByteArray(null, e));
    final byte[] f = {(byte) 0xBB, (byte) 0xAA};
    assertArrayEquals(f, ShortUtils.toByteArray(null, f));
    
    final byte[] xa = {(byte) 0x00, (byte) 0x00};
    final Short ya = (short) 0x0000;
    final byte[] za = {};
    assertArrayEquals(xa, ShortUtils.toByteArray(ya, za));
    assertArrayEquals(xa, ShortUtils.toByteArray(ya, x));
    final byte[] xb = {(byte) 0xAB, (byte) 0x00};
    final Short yb = (short) 0xAB00;
    final byte[] zb = {};
    assertArrayEquals(xb, ShortUtils.toByteArray(yb, zb));
    assertArrayEquals(xb, ShortUtils.toByteArray(yb, x));
    final byte[] xc = {(byte) 0xAB, (byte) 0xAB};
    final Short yc = (short) 0xABAB;
    final byte[] zc = {};
    assertArrayEquals(xc, ShortUtils.toByteArray(yc, zc));
    assertArrayEquals(xc, ShortUtils.toByteArray(yc, x));
    final byte[] xd = {(byte) 0xFF, (byte) 0x00};
    final Short yd = (short) 0xFF00;
    final byte[] zd = {};
    assertArrayEquals(xd, ShortUtils.toByteArray(yd, zd));
    assertArrayEquals(xd, ShortUtils.toByteArray(yd, x));
    final byte[] xe = {(byte) 0xFF, (byte) 0xFF};
    final Short ye = (short) 0xFFFF;
    final byte[] ze = {};
    assertArrayEquals(xe, ShortUtils.toByteArray(ye, ze));
    assertArrayEquals(xe, ShortUtils.toByteArray(ye, x));
    final byte[] xf = {(byte) 0xBB, (byte) 0xAA};
    final Short yf = (short) 0xBBAA;
    final byte[] zf = {};
    assertArrayEquals(xf, ShortUtils.toByteArray(yf, zf));
    assertArrayEquals(xf, ShortUtils.toByteArray(yf, x));
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toByteArray(java.lang.Short, byte[], java.nio.ByteOrder)}.
   */
  @Test
  public void testToByteArray_Short_byteArray_ByteOrder() {
    final byte[] x = null;
    assertEquals(null, ShortUtils.toByteArray(null, x, ByteOrder.BIG_ENDIAN));
    assertEquals(null, ShortUtils.toByteArray(null, x, ByteOrder.LITTLE_ENDIAN));
    
    final byte[] a = {(byte) 0x00, (byte) 0x00};
    assertArrayEquals(a, ShortUtils.toByteArray(null, a, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(a, ShortUtils.toByteArray(null, a, ByteOrder.LITTLE_ENDIAN));
    final byte[] b = {(byte) 0xAB, (byte) 0x00};
    assertArrayEquals(b, ShortUtils.toByteArray(null, b, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(b, ShortUtils.toByteArray(null, b, ByteOrder.LITTLE_ENDIAN));
    final byte[] c = {(byte) 0xAB, (byte) 0xAB};
    assertArrayEquals(c, ShortUtils.toByteArray(null, c, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(c, ShortUtils.toByteArray(null, c, ByteOrder.LITTLE_ENDIAN));
    final byte[] d = {(byte) 0xFF, (byte) 0x00};
    assertArrayEquals(d, ShortUtils.toByteArray(null, d, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(d, ShortUtils.toByteArray(null, d, ByteOrder.LITTLE_ENDIAN));
    final byte[] e = {(byte) 0xFF, (byte) 0xFF};
    assertArrayEquals(e, ShortUtils.toByteArray(null, e, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(e, ShortUtils.toByteArray(null, e, ByteOrder.LITTLE_ENDIAN));
    final byte[] f = {(byte) 0xBB, (byte) 0xAA};
    assertArrayEquals(f, ShortUtils.toByteArray(null, f, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(f, ShortUtils.toByteArray(null, f, ByteOrder.LITTLE_ENDIAN));
    
    final byte[] xa = {(byte) 0x00, (byte) 0x00};
    final Short ya = (short) 0x0000;
    final byte[] za = {};
    assertArrayEquals(xa, ShortUtils.toByteArray(ya, za, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xa, ShortUtils.toByteArray(ya, za, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xa, ShortUtils.toByteArray(ya, x, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xa, ShortUtils.toByteArray(ya, x, ByteOrder.LITTLE_ENDIAN));
    final byte[] xb = {(byte) 0xAB, (byte) 0x00};
    final Short yb1 = (short) 0xAB00;
    final Short yb2 = (short) 0x00AB;
    final byte[] zb = {};
    assertArrayEquals(xb, ShortUtils.toByteArray(yb1, zb, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xb, ShortUtils.toByteArray(yb2, zb, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xb, ShortUtils.toByteArray(yb1, x, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xb, ShortUtils.toByteArray(yb2, x, ByteOrder.LITTLE_ENDIAN));
    final byte[] xc = {(byte) 0xAB, (byte) 0xAB};
    final Short yc = (short) 0xABAB;
    final byte[] zc = {};
    assertArrayEquals(xc, ShortUtils.toByteArray(yc, zc, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xc, ShortUtils.toByteArray(yc, zc, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xc, ShortUtils.toByteArray(yc, x, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xc, ShortUtils.toByteArray(yc, x, ByteOrder.LITTLE_ENDIAN));
    final byte[] xd = {(byte) 0xFF, (byte) 0x00};
    final Short yd1 = (short) 0xFF00;
    final Short yd2 = (short) 0x00FF;
    final byte[] zd = {};
    assertArrayEquals(xd, ShortUtils.toByteArray(yd1, zd, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xd, ShortUtils.toByteArray(yd2, zd, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xd, ShortUtils.toByteArray(yd1, x, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xd, ShortUtils.toByteArray(yd2, x, ByteOrder.LITTLE_ENDIAN));
    final byte[] xe = {(byte) 0xFF, (byte) 0xFF};
    final Short ye = (short) 0xFFFF;
    final byte[] ze = {};
    assertArrayEquals(xe, ShortUtils.toByteArray(ye, ze, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xe, ShortUtils.toByteArray(ye, ze, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xe, ShortUtils.toByteArray(ye, x, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xe, ShortUtils.toByteArray(ye, x, ByteOrder.LITTLE_ENDIAN));
    final byte[] xf = {(byte) 0xBB, (byte) 0xAA};
    final Short yf1 = (short) 0xBBAA;
    final Short yf2 = (short) 0xAABB;
    final byte[] zf = {};
    assertArrayEquals(xf, ShortUtils.toByteArray(yf1, zf, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xf, ShortUtils.toByteArray(yf2, zf, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xf, ShortUtils.toByteArray(yf1, x, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xf, ShortUtils.toByteArray(yf2, x, ByteOrder.LITTLE_ENDIAN));
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toClass(short)}.
   */
  @Test
  public void testToClass_short() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertSame(Short.TYPE, ShortUtils.toClass((short) AREA[i]));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toClass(java.lang.Short)}.
   */
  @Test
  public void testToClass_Short() {
    assertSame(null, ShortUtils.toClass(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      assertSame(Short.class, ShortUtils.toClass(x));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toClass(java.lang.Short, java.lang.Class)}.
   */
  @Test
  public void testToClass_Short_ClassOfQ() {
    assertSame(null, ShortUtils.toClass(null, null));
    
    assertSame(Short.class, ShortUtils.toClass(null, Short.class));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      assertSame(Short.class, ShortUtils.toClass(x, null));
      assertSame(Short.class, ShortUtils.toClass(x, Short.class));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toBigInteger(short)}.
   */
  @Test
  public void testToBigInteger_short() {
    for ( int i = 0; i < AREA.length; ++i) {
      final BigInteger x = BigInteger.valueOf(AREA[i]);
      assertEquals(x, ShortUtils.toBigInteger((short) AREA[i]));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toBigInteger(java.lang.Short)}.
   */
  @Test
  public void testToBigInteger_Short() {
    assertEquals(null, ShortUtils.toBigInteger(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      final BigInteger y = BigInteger.valueOf(AREA[i]);
      assertEquals(y, ShortUtils.toBigInteger(x));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toBigInteger(java.lang.Short, java.math.BigInteger)}.
   */
  @Test
  public void testToBigInteger_Short_BigInteger() {
    assertEquals(null, ShortUtils.toBigInteger(null, null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final BigInteger x = BigInteger.valueOf(AREA[i]);
      assertEquals(x, ShortUtils.toBigInteger(null, x));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      final BigInteger y = BigInteger.valueOf(AREA[i]);
      assertEquals(y, ShortUtils.toBigInteger(x, null));
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final BigInteger z = BigInteger.valueOf(j);
        assertEquals(y, ShortUtils.toBigInteger(x, z));
      }
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toBigDecimal(short)}.
   */
  @Test
  public void testToBigDecimal_short() {
    for ( int i = 0; i < AREA.length; ++i) {
      final BigDecimal x = BigDecimal.valueOf(AREA[i]);
      assertEquals(x, ShortUtils.toBigDecimal((short) AREA[i]));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toBigDecimal(java.lang.Short)}.
   */
  @Test
  public void testToBigDecimal_Short() {
    assertEquals(null, ShortUtils.toBigDecimal(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      final BigDecimal y = BigDecimal.valueOf(AREA[i]);
      assertEquals(y, ShortUtils.toBigDecimal(x));
    }
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.ShortUtils#toBigDecimal(java.lang.Short, java.math.BigDecimal)}.
   */
  @Test
  public void testToBigDecimal_Short_BigDecimal() {
    assertEquals(null, ShortUtils.toBigDecimal(null, null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final BigDecimal x = BigDecimal.valueOf(AREA[i]);
      assertEquals(x, ShortUtils.toBigDecimal(null, x));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      final BigDecimal y = BigDecimal.valueOf(AREA[i]);
      assertEquals(y, ShortUtils.toBigDecimal(x, null));
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final BigDecimal z = BigDecimal.valueOf(j);
        assertEquals(y, ShortUtils.toBigDecimal(x, z));
      }
    }
  }

}
