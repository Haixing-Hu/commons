/******************************************************************************
 *
 *    Copyright (c) 2009-2012  Ascent Dimension, Inc. All rights reserved.
 *
 ******************************************************************************/

package com.github.haixing_hu.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
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

import static org.junit.Assert.*;

/**
 * Unit test of the {@link ByteUtils} class.
 * 
 * @author Hongming Ji
 */
public class ByteUtilsTest {

  @Test
  public void testToPrimitive_Byte() {
    assertEquals(ByteUtils.DEFAULT, ByteUtils.toPrimitive(null));
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = new Byte((byte) i);
      assertEquals((byte) i, ByteUtils.toPrimitive(x));
    }
  }

  @Test
  public void testToPrimitive_Byte_byte() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertEquals((byte) i, ByteUtils.toPrimitive(null, (byte) i));
    }

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      for (int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final Byte x = (byte) j;
        assertEquals((byte) j, ByteUtils.toPrimitive(x, (byte) i));
      }
    }
  }

  @Test
  public void testToBoolean_byte() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      if (i != 0) {
        assertEquals(true, ByteUtils.toBoolean((byte) i));
      } else {
        assertEquals(false, ByteUtils.toBoolean((byte) i));
      }
    }
  }

  @Test
  public void testToBoolean_Byte() {
    assertEquals(BooleanUtils.DEFAULT, ByteUtils.toBoolean(null));
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      if (i != 0) {
        assertEquals(true, ByteUtils.toBoolean(x));
      } else {
        assertEquals(false, ByteUtils.toBoolean(x));
      }
    }
  }

  @Test
  public void testToBoolean_Byte_boolean() {
    assertEquals(true, ByteUtils.toBoolean(null, true));
    assertEquals(false, ByteUtils.toBoolean(null, false));
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      if (i != 0) {
        assertEquals(true, ByteUtils.toBoolean(x, true));
        assertEquals(true, ByteUtils.toBoolean(x, false));
      } else {
        assertEquals(false, ByteUtils.toBoolean(x, true));
        assertEquals(false, ByteUtils.toBoolean(x, false));
      }
    }
  }

  @Test
  public void testToBooleanObject_byte() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      if (i != 0) {
        assertEquals(true, ByteUtils.toBooleanObject((byte) i));
      } else {
        assertEquals(false, ByteUtils.toBoolean((byte) i));
      }
    }
  }

  @Test
  public void testToBooleanObject_Byte() {
    assertEquals(null, ByteUtils.toBooleanObject(null));
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      if (i != 0) {
        assertEquals(true, ByteUtils.toBooleanObject(x));
      } else {
        assertEquals(false, ByteUtils.toBooleanObject(x));
      }
    }
  }

  @Test
  public void testToBooleanObject_Byte_Boolean() {
    assertEquals(null, ByteUtils.toBooleanObject(null, null));
    assertEquals(true, ByteUtils.toBooleanObject(null, true));
    assertEquals(false, ByteUtils.toBooleanObject(null, false));

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      if (i != 0) {
        assertEquals(true, ByteUtils.toBooleanObject(x, null));
        assertEquals(true, ByteUtils.toBooleanObject(x, true));
        assertEquals(true, ByteUtils.toBooleanObject(x, false));
      } else {
        assertEquals(false, ByteUtils.toBooleanObject(x, null));
        assertEquals(false, ByteUtils.toBooleanObject(x, true));
        assertEquals(false, ByteUtils.toBooleanObject(x, false));
      }
    }
  }

  @Test
  public void testToChar_byte() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertEquals((char) i, ByteUtils.toChar((byte) i));
    }
  }

  @Test
  public void testToChar_Byte() {
    assertEquals(CharUtils.DEFAULT, ByteUtils.toChar(null));
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      assertEquals((char) i, ByteUtils.toChar(x));
    }
  }

  @Test
  public void testToChar_Byte_char() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertEquals((char) i, ByteUtils.toChar(null, (char) i));
    }

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      for (int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final Byte x = (byte) i;
        assertEquals((char) i, ByteUtils.toChar(x, (char) j));
      }
    }
  }

  @Test
  public void testToChar_Object_byte() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Character a = (char) i;
      assertEquals(a, ByteUtils.toCharObject((byte) i));
    }
  }

  @Test
  public void testToCharObject_Byte() {
    assertEquals(null, ByteUtils.toCharObject(null));
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Character a = (char) i;
      final Byte x = (byte) i;
      assertEquals(a, ByteUtils.toCharObject(x));
    }
  }

  @Test
  public void testToCharObject_Byte_Character() {
    assertEquals(null, ByteUtils.toCharObject(null, null));

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Character a = (char) i;
      assertEquals(a, ByteUtils.toCharObject(null, a));
    }

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Character a = (char) i;
      final Byte x = (byte) i;
      for (int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final Character b = (char) j;
        assertEquals(a, ByteUtils.toCharObject(x, b));
      }
    }
  }

  @Test
  public void testToShort_byte() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertEquals((short) i, ByteUtils.toShort((byte) i));
    }
  }

  @Test
  public void testToShort_Byte() {
    assertEquals(ShortUtils.DEFAULT, ByteUtils.toShort(null));

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      assertEquals((short) i, ByteUtils.toShort(x));
    }
  }

  @Test
  public void testToShort_Byte_short() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertEquals((short) i, ByteUtils.toShort(null, (short) i));
    }

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      for (int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        assertEquals((short) i, ByteUtils.toShort(x, (short) j));
      }
    }
  }

  @Test
  public void testToShortObject_byte() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Short y = (short) i;
      assertEquals(y, ByteUtils.toShortObject((byte) i));
    }
  }

  @Test
  public void testToShortObject_Byte() {
    assertEquals(null, ByteUtils.toShortObject(null));

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      final Short y = (short) i;
      assertEquals(y, ByteUtils.toShortObject(x));
    }
  }

  @Test
  public void testToShortObject_Byte_Short() {
    assertEquals(null, ByteUtils.toShortObject(null, null));
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Short x = (short) i;
      assertEquals(x, ByteUtils.toShortObject(null, x));
    }

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Short x = (short) i;
      for (int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final Byte y = (byte) j;
        final Short z = (short) j;
        assertEquals(z, ByteUtils.toShortObject(y, x));
      }
    }
  }

  @Test
  public void testToInt_byte() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertEquals(i, ByteUtils.toInt((byte) i));
    }
  }

  @Test
  public void testToInt_Byte() {
    assertEquals(IntUtils.DEFAULT, ByteUtils.toInt(null));
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      assertEquals(i, ByteUtils.toInt(x));
    }
  }

  @Test
  public void testToInt_Byte_Int() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      for (int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        assertEquals(i, ByteUtils.toInt(x, j));
      }
    }
  }

  @Test
  public void testToIntObject_byte() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Integer x = (int) i;
      assertEquals(x, ByteUtils.toIntObject((byte) i));
    }
  }

  @Test
  public void testToIntObject_Byte() {
    assertEquals(null, ByteUtils.toIntObject(null));

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Integer x = (int) i;
      final Byte y = (byte) i;
      assertEquals(x, ByteUtils.toIntObject(y));
    }
  }

  @Test
  public void testToIntObject_Byte_Integer() {
    assertEquals(null, ByteUtils.toIntObject(null, null));

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Integer x = (int) i;
      assertEquals(x, ByteUtils.toIntObject(null, x));
    }

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      final Integer y = (int) i;
      assertEquals(y, ByteUtils.toIntObject(x, null));
      for (int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final Integer z = (int) j;
        assertEquals(y, ByteUtils.toIntObject(x, z));
      }
    }
  }

  @Test
  public void testToLong_byte() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertEquals((long) i, ByteUtils.toLong((byte) i));
    }
  }

  @Test
  public void testToLong_Byte() {
    assertEquals(LongUtils.DEFAULT, ByteUtils.toLong(null));

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      assertEquals((long) i, ByteUtils.toLong(x));
    }
  }

  @Test
  public void testToLong_Byte_Long() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertEquals((long) i, ByteUtils.toLong(null, (long) i));
    }

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      for (int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        assertEquals((long) i, ByteUtils.toLong(x, (long) j));
      }
    }
  }

  @Test
  public void testToLongObject_byte() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Long x = (long) i;
      assertEquals(x, ByteUtils.toLongObject((byte) i));
    }
  }

  @Test
  public void testToLongObject_Byte() {
    assertEquals(null, ByteUtils.toLongObject(null));

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      final Long y = (long) i;
      assertEquals(y, ByteUtils.toLongObject(x));
    }
  }

  @Test
  public void testToLongObject_Byte_Long() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Long x = (long) i;
      assertEquals(x, ByteUtils.toLongObject(null, x));
    }

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      final Long y = (long) i;
      assertEquals(y, ByteUtils.toLongObject(x, null));
      for (int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final Long z = (long) j;
        assertEquals(y, ByteUtils.toLongObject(x, z));
      }
    }
  }

  @Test
  public void testToFloat_byte() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertEquals((float) i, ByteUtils.toFloat((byte) i), FloatUtils.EPSILON);
    }
  }

  @Test
  public void testToFloat_Byte() {
    assertEquals(FloatUtils.DEFAULT, ByteUtils.toFloat(null), FloatUtils.EPSILON);

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      assertEquals((float) i, ByteUtils.toFloat(x), FloatUtils.EPSILON);
    }
  }

  @Test
  public void testToFloat_Byte_float() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertEquals((float) i, ByteUtils.toFloat(null, (float) i), FloatUtils.EPSILON);
    }

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      for (int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        assertEquals((float) i, ByteUtils.toFloat(x, (float) j), FloatUtils.EPSILON);
      }
    }
  }

  @Test
  public void testToFloatObject_byte() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Float x = (float) i;
      assertEquals(x, ByteUtils.toFloatObject((byte) i));
    }
  }

  @Test
  public void testToFloatObject_Byte() {
    assertEquals(null, ByteUtils.toFloatObject(null));

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Float x = (float) i;
      final Byte y = (byte) i;
      assertEquals(x, ByteUtils.toFloatObject(y));
    }
  }

  @Test
  public void testToFloatObject_Byte_Float() {
    assertEquals(null, ByteUtils.toFloatObject(null, null));

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Float x = (float) i;
      assertEquals(x, ByteUtils.toFloatObject(null, x));
    }

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      final Float y = (float) i;
      assertEquals(y, ByteUtils.toFloatObject(x, null));
      for (int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final Float z = (float) j;
        assertEquals(y, ByteUtils.toFloatObject(x, z));
      }
    }
  }

  @Test
  public void testToDouble_byte() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertEquals((double) i, ByteUtils.toDouble((byte) i), DoubleUtils.EPSILON);
    }
  }

  @Test
  public void testToDouble_Byte() {
    assertEquals(DoubleUtils.DEFAULT, ByteUtils.toDouble(null), DoubleUtils.EPSILON);

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      assertEquals((double) i, ByteUtils.toDouble(x), DoubleUtils.EPSILON);
    }
  }

  @Test
  public void testToDouble_Byte_double() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertEquals((double) i, ByteUtils.toDouble(null, (double) i), DoubleUtils.EPSILON);
    }

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      for (int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        assertEquals((double) i, ByteUtils.toDouble(x, (double) j), DoubleUtils.EPSILON);
      }
    }
  }

  @Test
  public void testToDoubleObject_byte() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Double x = (double) i;
      assertEquals(x, ByteUtils.toDoubleObject((byte) i));
    }
  }

  @Test
  public void testToDoubleObject_Byte() {
    assertEquals(null, ByteUtils.toDoubleObject(null));

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Double x = (double) i;
      final Byte y = (byte) i;
      assertEquals(x, ByteUtils.toDoubleObject(y));
    }
  }

  @Test
  public void testToDoubleObject_Byte_Double() {
    assertEquals(null, ByteUtils.toDoubleObject(null, null));

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Double x = (double) i;
      assertEquals(x, ByteUtils.toDoubleObject(null, x));
    }

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      final Double y = (double) i;
      assertEquals(y, ByteUtils.toDoubleObject(x, null));
      for (int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final Double z = (double) j;
        assertEquals(y, ByteUtils.toDoubleObject(x, z));
      }
    }
  }

  @Test
  public void testToString_byte() {
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final String x = Integer.toString(i, 10);
      assertEquals(x, ByteUtils.toString((byte) i));
    }
  }

  @Test
  public void testToString_Byte() {
    assertEquals(null, ByteUtils.toString(null));

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      final String y = Integer.toString(x, 10);
      assertEquals(y, ByteUtils.toString(x));
    }
  }

  @Test
  public void testToStringByteString() {
    assertEquals(null, ByteUtils.toString(null, null));

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final String x = Integer.toString(i);
      assertEquals(x, ByteUtils.toString(null, x));
    }

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      final String y = Integer.toString(i);
      assertEquals(y, ByteUtils.toString(x, null));
      for (int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final String z = Integer.toString(j);
        assertEquals(y, ByteUtils.toString(x, z));
      }
    }
  }

  @Test
  public void testToHexString_byte_StringBuilder() {
    StringBuilder builder = new StringBuilder();
    
    builder.setLength(0);
    ByteUtils.toHexString((byte)0x00, builder);
    assertEquals("0x00", builder.toString());
    
    builder.setLength(0);
    ByteUtils.toHexString((byte)0x01, builder);
    assertEquals("0x01", builder.toString());
    
    builder.setLength(0);
    ByteUtils.toHexString((byte)0x0A, builder);
    assertEquals("0x0A", builder.toString());
    
    builder.setLength(0);
    ByteUtils.toHexString((byte)0x0F, builder);
    assertEquals("0x0F", builder.toString());
    
    builder.setLength(0);
    ByteUtils.toHexString((byte)0x10, builder);
    assertEquals("0x10", builder.toString());
    
    builder.setLength(0);
    byte value = (byte) 0xAB;
    ByteUtils.toHexString(value, builder);
    assertEquals("0xAB", builder.toString());
    
    builder.setLength(0);
    ByteUtils.toHexString((byte)0x7F, builder);
    assertEquals("0x7F", builder.toString());
    
    builder.setLength(0);
    ByteUtils.toHexString((byte)0xFF, builder);
    assertEquals("0xFF", builder.toString());
  }

  @Test
  public void testToHexString_byte() {
    assertEquals("0x00", ByteUtils.toHexString((byte)0x00));
    assertEquals("0x01", ByteUtils.toHexString((byte)0x01));
    assertEquals("0x0A", ByteUtils.toHexString((byte)0x0A));
    assertEquals("0x0F", ByteUtils.toHexString((byte)0x0F));
    assertEquals("0x10", ByteUtils.toHexString((byte)0x10));
    assertEquals("0xAB", ByteUtils.toHexString((byte)0xAB));
    assertEquals("0x7F", ByteUtils.toHexString((byte)0x7F));
    assertEquals("0xFF", ByteUtils.toHexString((byte)0xFF));   
  }

  @Test
  public void testToDate_byte() {
    assertEquals(new Date(0), ByteUtils.toDate((byte)0));
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertEquals(new Date(i), ByteUtils.toDate((byte) i));
    }
  }

  @Test
  public void testToDate_Byte() {
    assertEquals(null, ByteUtils.toDate(null));
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      assertEquals(new Date(i), ByteUtils.toDate(x));
    }
  }

  @Test
  public void testToDate_Byte_Date() {
    assertEquals(null, ByteUtils.toDate(null, null));
    
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Date x = new Date(i);
      assertEquals(x, ByteUtils.toDate(null, x));
    }
    
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Date x = new Date(i);
      final Byte y = (byte) i;
      assertEquals(x, ByteUtils.toDate(y, null));
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final Date z = new Date (j);
        assertEquals(x, ByteUtils.toDate(y, z));
      }
    }
  }

  @Test
  public void testToByteArray_byte() {
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final byte[] x = {(byte) i};      
      assertArrayEquals(x, ByteUtils.toByteArray((byte) i));
    }
  }

  @Test
  public void testToByteArray_Byte() {
    assertEquals(null, ByteUtils.toByteArray(null));
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      final byte[] y = {(byte) i};
      assertArrayEquals(y, ByteUtils.toByteArray(x));
    }
  }

  @Test
  public void testToByteArray_Byte_byteArray() {
    assertArrayEquals(null, ByteUtils.toByteArray(null, null));
    
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final byte[] x = {(byte) i};
      assertArrayEquals(x, ByteUtils.toByteArray(null, x));
    }
    
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      final byte[] y = {(byte) i};
      assertArrayEquals(y, ByteUtils.toByteArray(x, null));
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final byte[] z = {(byte) j};
        assertArrayEquals(y, ByteUtils.toByteArray(x, z));
      }
    }
  }

  @Test
  public void testToClass_byte() {
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertSame(Byte.TYPE, ByteUtils.toClass((byte) i));
    }
  }

  @Test
  public void testToClass_Byte() {
    assertEquals(null, ByteUtils.toClass(null));
    
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      assertSame(Byte.class, ByteUtils.toClass(x));
    }
  }

  @Test
  public void testToClass_Byte_ClassOfQ() {
    assertSame(null, ByteUtils.toClass(null, null));
    
    assertSame(Byte.class, ByteUtils.toClass(null, Byte.class));
    
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      assertSame(Byte.class, ByteUtils.toClass(x, null));
      assertSame(Byte.class, ByteUtils.toClass(x, Byte.class));
    }
  }

  @Test
  public void testToBigInteger_byte() {
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertEquals(BigInteger.valueOf(i), ByteUtils.toBigInteger((byte) i));
    }
  }

  @Test
  public void testToBigInteger_Byte() {
    assertEquals(null, ByteUtils.toBigInteger(null));
    
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      assertEquals(BigInteger.valueOf(i), ByteUtils.toBigInteger(x));
    }
  }

  @Test
  public void testToBigInteger_Byte_BigInteger() {
    assertEquals(null, ByteUtils.toBigInteger(null, null));
    
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final BigInteger x = BigInteger.valueOf(i);
      assertEquals(x, ByteUtils.toBigInteger(null, x));
    }
    
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      final BigInteger y = BigInteger.valueOf(i);
      assertEquals(y, ByteUtils.toBigInteger(x, null));
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final BigInteger z = BigInteger.valueOf(j);
        assertEquals(y, ByteUtils.toBigInteger(x, z));
      }
    }
  }

  @Test
  public void testToBigDecimal_byte() {
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertEquals(BigDecimal.valueOf(i), ByteUtils.toBigDecimal((byte) i));
    }
  }

  @Test
  public void testToBigDecimal_Byte() {
    assertEquals(null, ByteUtils.toBigDecimal(null));
    
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      assertEquals(BigDecimal.valueOf(i), ByteUtils.toBigDecimal(x));
    }
  }

  @Test
  public void testToBigDecimal_Byte_BigDecimal() {
    assertEquals(null, ByteUtils.toBigDecimal(null, null));
    
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertEquals(BigDecimal.valueOf(i), ByteUtils.toBigDecimal(null, BigDecimal.valueOf(i)));
    }
    
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      assertEquals(BigDecimal.valueOf(i), ByteUtils.toBigDecimal(x, null));
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        assertEquals(BigDecimal.valueOf(i), ByteUtils.toBigDecimal(x, BigDecimal.valueOf(j)));
      }
    }
  }

}
