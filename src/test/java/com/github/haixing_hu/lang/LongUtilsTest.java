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
import com.github.haixing_hu.lang.UnsupportedByteOrderException;

import static org.junit.Assert.*;

public class LongUtilsTest {

  private static final long[] AREA = {Long.MIN_VALUE, Long.MIN_VALUE/2, (long) -1,(long) 0,
                                      (long) 1, Long.MAX_VALUE/2, Long.MAX_VALUE};
  
  @Test
  public void testToPrimitive_Long() {
    assertEquals(LongUtils.DEFAULT, LongUtils.toPrimitive(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      assertEquals(AREA[i], LongUtils.toPrimitive(x));
    }
  }

  @Test
  public void testToPrimitive_Long_long() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals(AREA[i], LongUtils.toPrimitive(null, AREA[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      for ( int j = 0; j < AREA.length; ++j) {
        assertEquals(AREA[i], LongUtils.toPrimitive(x, AREA[j]));
      }
    }
  }

  @Test
  public void testToBoolean_long() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals( AREA[i] != 0, LongUtils.toBoolean(AREA[i]));
    }
  }

  @Test
  public void testToBoolean_Long() {
    assertEquals(BooleanUtils.DEFAULT, LongUtils.toBoolean(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      assertEquals( AREA[i] != 0, LongUtils.toBoolean(x));
    }
  }

  @Test
  public void testToBoolean_Long_boolean() {
    assertEquals(true, LongUtils.toBoolean(null, true));
    assertEquals(false, LongUtils.toBoolean(null, false));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      assertEquals( AREA[i] != 0, LongUtils.toBoolean(x, true));
      assertEquals( AREA[i] != 0, LongUtils.toBoolean(x, false));
    }
  }

  @Test
  public void testToBooleanObject_long() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals(Boolean.valueOf( AREA[i] != 0), LongUtils.toBooleanObject(AREA[i]));
    }
  }

  @Test
  public void testToBooleanObject_Long() {
    assertEquals(null, LongUtils.toBooleanObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      final Boolean y = (AREA[i] != 0);
      assertEquals(y, LongUtils.toBooleanObject(x));
    }
  }

  @Test
  public void testToBooleanObject_Long_Boolean() {
    final Boolean a = true;
    final Boolean b = false;
    assertEquals(true, LongUtils.toBooleanObject(null, a));
    assertEquals(false, LongUtils.toBooleanObject(null, b));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      final Boolean y = (AREA[i] != 0);
      assertEquals( y, LongUtils.toBooleanObject(x, null));
      assertEquals( y, LongUtils.toBooleanObject(x, true));
      assertEquals( y, LongUtils.toBooleanObject(x, false));
    }
  }

  @Test
  public void testToChar_long() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals((char) AREA[i], LongUtils.toChar(AREA[i]));
    }
  }

  @Test
  public void testToChar_Long() {
    assertEquals(CharUtils.DEFAULT, LongUtils.toChar(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      assertEquals((char) AREA[i], LongUtils.toChar(x));
    }
  }

  @Test
  public void testToChar_Long_char() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals((char) AREA[i], LongUtils.toChar(null, (char) AREA[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      for ( int j = 0; j < AREA.length; ++j) {
        assertEquals((char) AREA[i], LongUtils.toChar(x, (char) AREA[j]));
      }
    }
  }

  @Test
  public void testToCharObject_long() {
    for ( int i = 0; i < AREA.length; ++i) {
      final Character x = (char) AREA[i];
      assertEquals(x, LongUtils.toCharObject(AREA[i]));
    }
  }

  @Test
  public void testToCharObject_Long() {
    assertEquals(null, LongUtils.toCharObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i]; 
      final Character y = (char) AREA[i];
      assertEquals(y, LongUtils.toCharObject(x));
    }
  }

  @Test
  public void testToCharObject_Long_Character() {
    assertEquals(null, LongUtils.toCharObject(null, null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Character x = (char) AREA[i];
      assertEquals(x, LongUtils.toCharObject(null, x));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      final Character y = (char) AREA[i];
      assertEquals(y, LongUtils.toCharObject(x, null));
      for ( int j = 0; j < AREA.length; ++j) {
        final Character z = (char) AREA[j];
        assertEquals(y, LongUtils.toCharObject(x, z));
      }
    }
  }

  @Test
  public void testToByte_long() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals((byte) AREA[i], LongUtils.toByte(AREA[i]));
    }
  }

  @Test
  public void testToByte_Long() {
    assertEquals(ByteUtils.DEFAULT, LongUtils.toByte(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      assertEquals((byte) AREA[i], LongUtils.toByte(x));
    }
  }

  @Test
  public void testToByte_Long_byte() {
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertEquals((byte) i, LongUtils.toByte(null, (byte) i));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        assertEquals((byte) AREA[i], LongUtils.toByte(x, (byte) j));
      }
    }
  }

  @Test
  public void testToByteObject_long() {
    for ( int i = 0; i < AREA.length; ++i) {
      final Byte x = (byte) AREA[i];
      assertEquals(x, LongUtils.toByteObject(AREA[i]));
    }
  }

  @Test
  public void testToByteObject_Long() {
    assertEquals(null, LongUtils.toByteObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      final Byte y = (byte) AREA[i];
      assertEquals(y, LongUtils.toByteObject(x));
    }
  }

  @Test
  public void testToByteObject_Long_Byte() {
    assertEquals(null, LongUtils.toByteObject(null, null));
    
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      assertEquals(x, LongUtils.toByteObject(null, x));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      final Byte y = (byte) AREA[i];
      assertEquals(y, LongUtils.toByteObject(x, null));
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final Byte z = (byte) j;
        assertEquals(y, LongUtils.toByteObject(x, z));
      }
    }
  }

  @Test
  public void testToShort_long() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals((short) i, LongUtils.toShort(i));
    }
  }

  @Test
  public void testToShort_Long() {
    assertEquals(IntUtils.DEFAULT, LongUtils.toShort(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      assertEquals((short) AREA[i], LongUtils.toShort(x));
    }
  }

  @Test
  public void testToShort_Long_short() {
    final short[] a = {Short.MIN_VALUE, Short.MIN_VALUE/2, (short) -1, (short) 0, (short) 1,
                       Short.MAX_VALUE/2, Short.MAX_VALUE};
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], LongUtils.toShort(null, a[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      for ( int j = 0; j < a.length; ++j) {
        assertEquals((short) AREA[i], LongUtils.toShort(x, a[j]));
      }
    }
  }

  @Test
  public void testToShortObject_long() {
    for ( int i = 0; i < AREA.length; ++i) {
      final Short y = (short) AREA[i];
      assertEquals(y, LongUtils.toShortObject(AREA[i]));
    }
  }

  @Test
  public void testToShortObject_Long() {
    assertEquals(null, LongUtils.toShortObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short y = (short) AREA[i];
      assertEquals(y, LongUtils.toShortObject(AREA[i]));
    }
  }

  @Test
  public void testToShortObject_Long_Short() {
    final Short[] a = {Short.MIN_VALUE, Short.MIN_VALUE/2, (short) -1, (short) 0, (short) 1,
                       Short.MAX_VALUE/2, Short.MAX_VALUE};
    
    assertEquals(null, LongUtils.toShortObject(null, null));
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], LongUtils.toShortObject(null, a[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      final Short y = (short) AREA[i];
      assertEquals(y, LongUtils.toShortObject(x, null));
      for ( int j = 0; j < a.length; ++j) {
        assertEquals(y, LongUtils.toShortObject(x, a[j]));
      }
    }
  }

  @Test
  public void testToInt_long() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals((int) AREA[i], LongUtils.toInt(AREA[i]));
    }
  }

  @Test
  public void testToInt_Long() {
    assertEquals(IntUtils.DEFAULT, LongUtils.toInt(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      assertEquals((int) AREA[i], LongUtils.toInt(x));
    }
  }

  @Test
  public void testToInt_Long_int() {
    final int[] a = {Integer.MIN_VALUE, Integer.MIN_VALUE/2, -1, 0, 1,
                     Integer.MAX_VALUE/2, Integer.MAX_VALUE};
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], LongUtils.toInt(null, a[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      for ( int j = 0; j < a.length; ++j) {
        assertEquals((int) AREA[i], LongUtils.toInt(x, a[j]));
      }
    }
  }

  @Test
  public void testToIntObject_long() {
    for ( int i = 0; i < AREA.length; ++i) {
      final Integer x = (int) AREA[i];
      assertEquals(x, LongUtils.toIntObject(AREA[i]));
    }
  }

  @Test
  public void testToIntObject_Long() {
    assertEquals(null, LongUtils.toIntObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      final Integer y = (int) AREA[i];
      assertEquals(y, LongUtils.toIntObject(x));
    }
  }

  @Test
  public void testToIntObject_Long_Integer() {
    final Integer[] a = {Integer.MIN_VALUE, Integer.MIN_VALUE/2, -1, 0, 1,
                         Integer.MAX_VALUE/2, Integer.MAX_VALUE};
    
    assertEquals(null, LongUtils.toIntObject(null, null));
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], LongUtils.toIntObject(null, a[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      final Integer y = (int) AREA[i];
      assertEquals(y, LongUtils.toIntObject(x, null));
      for ( int j = 0; j < a.length; ++j) {
        assertEquals(y, LongUtils.toIntObject(x, a[j]));
      }
    }
  }

  @Test
  public void testToFloat_long() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals((float) AREA[i], LongUtils.toFloat(AREA[i]), FloatUtils.EPSILON);
    }
  }

  @Test
  public void testToFloat_Long() {
    assertEquals(FloatUtils.DEFAULT, LongUtils.toFloat(null), FloatUtils.EPSILON);
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      assertEquals((float) AREA[i], LongUtils.toFloat(x), FloatUtils.EPSILON);
    }
  }

  @Test
  public void testToFloat_Long_float() {
    final float[] a = {Float.MIN_VALUE, Float.MIN_VALUE/2, (float) -1, (float) 0, (float) 1,
                       Float.MAX_VALUE/2, Float.MAX_VALUE};
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], LongUtils.toFloat(null, a[i]), FloatUtils.EPSILON);
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      for ( int j = 0; j < a.length; ++j) {
        assertEquals((float) AREA[i], LongUtils.toFloat(x, a[j]), FloatUtils.EPSILON);
      }
    }
  }

  @Test
  public void testToFloatObject_long() {
    for ( int i = 0; i < AREA.length; ++i) {
      final Float y = (float) AREA[i];
      assertEquals(y, LongUtils.toFloatObject(AREA[i]));
    }
  }

  @Test
  public void testToFloatObject_Long() {
    assertEquals(null, LongUtils.toFloatObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      final Float y = (float) AREA[i];
      assertEquals(y, LongUtils.toFloatObject(x));
    }
  }

  @Test
  public void testToFloatObject_Long_Float() {
    final Float[] a = {Float.MIN_VALUE, Float.MIN_VALUE/2, (float) -1, (float) 0, (float) 1,
                       Float.MAX_VALUE/2, Float.MAX_VALUE};
    
    assertEquals(null, LongUtils.toFloatObject(null, null));
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], LongUtils.toFloatObject(null, a[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      final Float y = (float) AREA[i];
      assertEquals(y, LongUtils.toFloatObject(x, null));
      for ( int j = 0; j < a.length; ++j) {
        assertEquals(y, LongUtils.toFloatObject(x, a[j]));
      }
    }
  }

  @Test
  public void testToDouble_long() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals((double) AREA[i], LongUtils.toDouble(AREA[i]), DoubleUtils.EPSILON);
    }
  }

  @Test
  public void testToDouble_Long() {
    assertEquals(DoubleUtils.DEFAULT, LongUtils.toDouble(null), DoubleUtils.EPSILON);
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      assertEquals((double) AREA[i], LongUtils.toDouble(x), DoubleUtils.EPSILON);
    }
  }

  @Test
  public void testToDouble_Long_double() {
    final double[] a = {Double.MIN_VALUE, Double.MIN_VALUE/2, (double) -1, (double) 0, (double) 1,
                        Double.MAX_VALUE/2, Double.MAX_VALUE};
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], LongUtils.toDouble(null, a[i]), DoubleUtils.EPSILON);
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      for ( int j = 0; j < a.length; ++j) {
        assertEquals((double) AREA[i], LongUtils.toDouble(x, a[j]), DoubleUtils.EPSILON);
      }
    }
  }

  @Test
  public void testToDoubleObject_long() {
    for ( int i = 0; i < AREA.length; ++i) {
      final Double x = (double) AREA[i];
      assertEquals(x, LongUtils.toDoubleObject(AREA[i]));
    }
  }

  @Test
  public void testToDoubleObject_Long() {
    assertEquals(null, LongUtils.toDoubleObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      final Double y = (double) AREA[i];
      assertEquals(y, LongUtils.toDoubleObject(x));
    }
  }

  @Test
  public void testToDoubleObject_Long_Double() {
    final Double[] a = {Double.MIN_VALUE, Double.MIN_VALUE/2, (double) -1, (double) 0, (double) 1,
                        Double.MAX_VALUE/2, Double.MAX_VALUE};
    
    assertEquals(null, LongUtils.toDoubleObject(null, null));
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], LongUtils.toDoubleObject(null, a[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      final Double y = (double) AREA[i];
      assertEquals(y, LongUtils.toDoubleObject(x, null));
      for ( int j = 0; j < a.length; ++j) {
        assertEquals(y, LongUtils.toDoubleObject(x, a[j]));
      }
    }
  }

  @Test
  public void testToString_long() {
    for ( int i = 0; i < AREA.length; ++i) {
      final String x = Long.toString(AREA[i]);
      assertEquals(x, LongUtils.toString(AREA[i]));
    }
  }

  @Test
  public void testToString_Long() {
    assertEquals(null,LongUtils.toString(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      final String y = Long.toString(AREA[i]);
      assertEquals(y, LongUtils.toString(x));
    }
  }

  @Test
  public void testToString_Long_String() {
    assertEquals(null, LongUtils.toString(null, null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final String x = Long.toString(AREA[i]);
      assertEquals(x, LongUtils.toString(null, x));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      final String y = Long.toString(AREA[i]);
      assertEquals(y, LongUtils.toString(x, null));
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final String z = Integer.toString(j);
        assertEquals(y, LongUtils.toString(x, z));
      }
    }
  }

  @Test
  public void testToHexString_long_StringBuilder() {
    StringBuilder builder = new StringBuilder();
    
    builder.setLength(0);
    LongUtils.toHexString(0x0000000000000000L, builder);
    assertEquals("0x0000000000000000", builder.toString());
    
    builder.setLength(0);
    LongUtils.toHexString(0x0000000A0000000AL, builder);
    assertEquals("0x0000000A0000000A", builder.toString());
    
    builder.setLength(0);
    LongUtils.toHexString(0x0000AAAA0000AAAAL, builder);
    assertEquals("0x0000AAAA0000AAAA", builder.toString());
    
    builder.setLength(0);
    LongUtils.toHexString(0x0000FFFF0000FFFFL, builder);
    assertEquals("0x0000FFFF0000FFFF", builder.toString());
    
    builder.setLength(0);
    LongUtils.toHexString(0xAAAAAAAAAAAAAAAAL, builder);
    assertEquals("0xAAAAAAAAAAAAAAAA", builder.toString());
    
    builder.setLength(0);
    LongUtils.toHexString(0xFFFFFFFFFFFFFFFFL, builder);
    assertEquals("0xFFFFFFFFFFFFFFFF", builder.toString());
  }

  @Test
  public void testToHexString_long() {
    assertEquals("0x0000000000000000", LongUtils.toHexString(0x0000000000000000L)); 
    assertEquals("0x0000000A0000000A", LongUtils.toHexString(0x0000000A0000000AL));
    assertEquals("0x0000AAAA0000AAAA", LongUtils.toHexString(0x0000AAAA0000AAAAL));
    assertEquals("0x0000FFFF0000FFFF", LongUtils.toHexString(0x0000FFFF0000FFFFL));
    assertEquals("0xAAAAAAAAAAAAAAAA", LongUtils.toHexString(0xAAAAAAAAAAAAAAAAL));
    assertEquals("0xFFFFFFFFFFFFFFFF", LongUtils.toHexString(0xFFFFFFFFFFFFFFFFL));
  }

  @Test
  public void testToDate_long() {
    for ( int i = 0; i < AREA.length; ++i) {
      final Date x = new Date(AREA[i]);
      assertEquals(x, LongUtils.toDate(AREA[i]));
    }
  }

  @Test
  public void testToDate_Long() {
    assertEquals(null, LongUtils.toDate(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      final Date y = new Date(AREA[i]);
      assertEquals(y, LongUtils.toDate(x));
    }
  }

  @Test
  public void testToDate_Long_Date() {
    assertEquals(null, LongUtils.toDate(null, null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Date x = new Date(AREA[i]);
      assertEquals(x, LongUtils.toDate(null, x));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      final Date y = new Date(AREA[i]);
      assertEquals(y, LongUtils.toDate(x, null));
      for ( int j = 0; j < AREA.length; ++j) {
        final Date z = new Date(AREA[j]);
        assertEquals(y, LongUtils.toDate(x, z));
      }
    }
  }

  @Test
  public void testToByteArray_long() {
    final byte[] xa = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                       (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    assertArrayEquals(xa, LongUtils.toByteArray(0x0000000000000000L));
    
    final byte[] xb = {(byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00,
                       (byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00};
    assertArrayEquals(xb, LongUtils.toByteArray(0xAB00AB00AB00AB00L));
    
    final byte[] xc = {(byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB,
                       (byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB};
    assertArrayEquals(xc, LongUtils.toByteArray(0xABABABABABABABABL));
    
    final byte[] xd = {(byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00,
                       (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00};
    assertArrayEquals(xd, LongUtils.toByteArray(0xFF00FF00FF00FF00L));
    
    final byte[] xe = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                       (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
    assertArrayEquals(xe, LongUtils.toByteArray(0xFFFFFFFFFFFFFFFFL));
    
    final byte[] xf = {(byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA,
                       (byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA};
    assertArrayEquals(xf, LongUtils.toByteArray(0xBBAABBAABBAABBAAL));
  }

  @Test
  public void testToByteArray_long_ByteOrder() {
    final byte[] xa = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                       (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    assertArrayEquals(xa, LongUtils.toByteArray(0x0000000000000000L, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xa, LongUtils.toByteArray(0x0000000000000000L, ByteOrder.BIG_ENDIAN));
    
    final byte[] xb = {(byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00,
                       (byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00};
    assertArrayEquals(xb, LongUtils.toByteArray(0x00AB00AB00AB00ABL, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xb, LongUtils.toByteArray(0xAB00AB00AB00AB00L, ByteOrder.BIG_ENDIAN));
    
    final byte[] xc = {(byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB,
                       (byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB};
    assertArrayEquals(xc, LongUtils.toByteArray(0xABABABABABABABABL, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xc, LongUtils.toByteArray(0xABABABABABABABABL, ByteOrder.BIG_ENDIAN));
    
    final byte[] xd = {(byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00,
                       (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00};
    assertArrayEquals(xd, LongUtils.toByteArray(0x00FF00FF00FF00FFL, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xd, LongUtils.toByteArray(0xFF00FF00FF00FF00L, ByteOrder.BIG_ENDIAN));
    
    final byte[] xe = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                       (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
    assertArrayEquals(xe, LongUtils.toByteArray(0xFFFFFFFFFFFFFFFFL, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xe, LongUtils.toByteArray(0xFFFFFFFFFFFFFFFFL, ByteOrder.BIG_ENDIAN));
    
    final byte[] xf = {(byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA,
                       (byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA};
    assertArrayEquals(xf, LongUtils.toByteArray(0xAABBAABBAABBAABBL, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xf, LongUtils.toByteArray(0xBBAABBAABBAABBAAL, ByteOrder.BIG_ENDIAN));
    
    try {
      LongUtils.toByteArray(10, null);
      fail("should throw");
    } catch (UnsupportedByteOrderException e) {
      // pass
    }
  }

  @Test
  public void testToByteArray_Long() {
    assertEquals(null, LongUtils.toByteArray(null));
    
    final byte[] xa = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                       (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    final Long ya = 0x0000000000000000L;
    assertArrayEquals(xa, LongUtils.toByteArray(ya));
    
    final byte[] xb = {(byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00,
                       (byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00};
    final Long yb = 0xAB00AB00AB00AB00L;
    assertArrayEquals(xb, LongUtils.toByteArray(yb));
    
    final byte[] xc = {(byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB,
                       (byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB};
    final Long yc = 0xABABABABABABABABL;
    assertArrayEquals(xc, LongUtils.toByteArray(yc));

    final byte[] xd = {(byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00,
                       (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00};
    final Long yd = 0xFF00FF00FF00FF00L;
    assertArrayEquals(xd, LongUtils.toByteArray(yd));
    
    final byte[] xe = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                       (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
    final Long ye = 0xFFFFFFFFFFFFFFFFL;
    assertArrayEquals(xe, LongUtils.toByteArray(ye));
    
    final byte[] xf = {(byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA,
                       (byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA};
    final Long yf = 0xBBAABBAABBAABBAAL;
    assertArrayEquals(xf, LongUtils.toByteArray(yf));
  }

  @Test
  public void testToByteArray_Long_ByteOrder() {
    assertArrayEquals(null, LongUtils.toByteArray(null, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(null, LongUtils.toByteArray(null, ByteOrder.LITTLE_ENDIAN));
    
    final byte[] xa = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                       (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    final Long ya = 0x0000000000000000L;
    assertArrayEquals(xa, LongUtils.toByteArray(ya, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xa, LongUtils.toByteArray(ya, ByteOrder.BIG_ENDIAN));
    
    final byte[] xb = {(byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00,
                       (byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00};
    final Long yb1 = 0x00AB00AB00AB00ABL;
    assertArrayEquals(xb, LongUtils.toByteArray(yb1, ByteOrder.LITTLE_ENDIAN));
    final Long yb2 = 0xAB00AB00AB00AB00L;
    assertArrayEquals(xb, LongUtils.toByteArray(yb2, ByteOrder.BIG_ENDIAN));
    
    final byte[] xc = {(byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB,
                       (byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB};
    final Long yc = 0xABABABABABABABABL;
    assertArrayEquals(xc, LongUtils.toByteArray(yc, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xc, LongUtils.toByteArray(yc, ByteOrder.BIG_ENDIAN));
    
    final byte[] xd = {(byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00,
                       (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00};
    final Long yd1 = 0x00FF00FF00FF00FFL;
    assertArrayEquals(xd, LongUtils.toByteArray(yd1, ByteOrder.LITTLE_ENDIAN));
    final Long yd2 = 0xFF00FF00FF00FF00L;
    assertArrayEquals(xd, LongUtils.toByteArray(yd2, ByteOrder.BIG_ENDIAN));
    
    final byte[] xe = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                       (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
    final Long ye = 0xFFFFFFFFFFFFFFFFL;
    assertArrayEquals(xe, LongUtils.toByteArray(ye, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xe, LongUtils.toByteArray(ye, ByteOrder.BIG_ENDIAN));
    
    final byte[] xf = {(byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA,
                       (byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA};
    final Long yf1 = 0xAABBAABBAABBAABBL;
    assertArrayEquals(xf, LongUtils.toByteArray(yf1, ByteOrder.LITTLE_ENDIAN));
    final Long yf2 = 0xBBAABBAABBAABBAAL;
    assertArrayEquals(xf, LongUtils.toByteArray(yf2, ByteOrder.BIG_ENDIAN));
  }

  @Test
  public void testToByteArray_Long_ByteArray() {
    final byte[] x = null;
    assertEquals(null, LongUtils.toByteArray(null, x));
    
    final byte[] a = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                      (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    assertArrayEquals(a, LongUtils.toByteArray(null, a));
    final byte[] b = {(byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00,
                      (byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00};
    assertArrayEquals(b, LongUtils.toByteArray(null, b));
    final byte[] c = {(byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB,
                      (byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB};
    assertArrayEquals(c, LongUtils.toByteArray(null, c));
    final byte[] d = {(byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00,
                      (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00};
    assertArrayEquals(d, LongUtils.toByteArray(null, d));
    final byte[] e = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                      (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
    assertArrayEquals(e, LongUtils.toByteArray(null, e));
    final byte[] f = {(byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA,
                      (byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA};
    assertArrayEquals(f, LongUtils.toByteArray(null, f));
    
    final byte[] xa = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                       (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    final Long ya = 0x0000000000000000L;
    final byte[] za = {};
    assertArrayEquals(xa, LongUtils.toByteArray(ya, za));
    assertArrayEquals(xa, LongUtils.toByteArray(ya, x));
    final byte[] xb = {(byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00,
                       (byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00};
    final Long yb = 0xAB00AB00AB00AB00L;
    final byte[] zb = {};
    assertArrayEquals(xb, LongUtils.toByteArray(yb, zb));
    assertArrayEquals(xb, LongUtils.toByteArray(yb, x));
    final byte[] xc = {(byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB,
                       (byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB};
    final Long yc = 0xABABABABABABABABL;
    final byte[] zc = {};
    assertArrayEquals(xc, LongUtils.toByteArray(yc, zc));
    assertArrayEquals(xc, LongUtils.toByteArray(yc, x));
    final byte[] xd = {(byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00,
                       (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00};
    final Long yd = 0xFF00FF00FF00FF00L;
    final byte[] zd = {};
    assertArrayEquals(xd, LongUtils.toByteArray(yd, zd));
    assertArrayEquals(xd, LongUtils.toByteArray(yd, x));
    final byte[] xe = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                       (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
    final Long ye = 0xFFFFFFFFFFFFFFFFL;
    final byte[] ze = {};
    assertArrayEquals(xe, LongUtils.toByteArray(ye, ze));
    assertArrayEquals(xe, LongUtils.toByteArray(ye, x));
    final byte[] xf = {(byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA,
                       (byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA};
    final Long yf = 0xBBAABBAABBAABBAAL;
    final byte[] zf = {};
    assertArrayEquals(xf, LongUtils.toByteArray(yf, zf));
    assertArrayEquals(xf, LongUtils.toByteArray(yf, x));
  }

  @Test
  public void testToByteArray_Long_ByteArray_ByteOrder() {
    final byte[] x = null;
    assertEquals(null, LongUtils.toByteArray(null, x, ByteOrder.BIG_ENDIAN));
    assertEquals(null, LongUtils.toByteArray(null, x, ByteOrder.LITTLE_ENDIAN));
    
    final byte[] a = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                      (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    assertArrayEquals(a, LongUtils.toByteArray(null, a, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(a, LongUtils.toByteArray(null, a, ByteOrder.LITTLE_ENDIAN));
    final byte[] b = {(byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00,
                      (byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00};
    assertArrayEquals(b, LongUtils.toByteArray(null, b, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(b, LongUtils.toByteArray(null, b, ByteOrder.LITTLE_ENDIAN));
    final byte[] c = {(byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB,
                      (byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB};
    assertArrayEquals(c, LongUtils.toByteArray(null, c, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(c, LongUtils.toByteArray(null, c, ByteOrder.LITTLE_ENDIAN));
    final byte[] d = {(byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00,
                      (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00};
    assertArrayEquals(d, LongUtils.toByteArray(null, d, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(d, LongUtils.toByteArray(null, d, ByteOrder.LITTLE_ENDIAN));
    final byte[] e = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                      (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
    assertArrayEquals(e, LongUtils.toByteArray(null, e, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(e, LongUtils.toByteArray(null, e, ByteOrder.LITTLE_ENDIAN));
    final byte[] f = {(byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA,
                      (byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA};
    assertArrayEquals(f, LongUtils.toByteArray(null, f, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(f, LongUtils.toByteArray(null, f, ByteOrder.LITTLE_ENDIAN));
    
    final byte[] xa = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                       (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    final Long ya = 0x0000000000000000L;
    final byte[] za = {};
    assertArrayEquals(xa, LongUtils.toByteArray(ya, za, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xa, LongUtils.toByteArray(ya, za, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xa, LongUtils.toByteArray(ya, x, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xa, LongUtils.toByteArray(ya, x, ByteOrder.LITTLE_ENDIAN));
    final byte[] xb = {(byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00,
                       (byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00};
    final Long yb1 = 0xAB00AB00AB00AB00L;
    final Long yb2 = 0x00AB00AB00AB00ABL;
    final byte[] zb = {};
    assertArrayEquals(xb, LongUtils.toByteArray(yb1, zb, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xb, LongUtils.toByteArray(yb2, zb, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xb, LongUtils.toByteArray(yb1, x, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xb, LongUtils.toByteArray(yb2, x, ByteOrder.LITTLE_ENDIAN));
    final byte[] xc = {(byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB,
                       (byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB};
    final Long yc = 0xABABABABABABABABL;
    final byte[] zc = {};
    assertArrayEquals(xc, LongUtils.toByteArray(yc, zc, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xc, LongUtils.toByteArray(yc, zc, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xc, LongUtils.toByteArray(yc, x, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xc, LongUtils.toByteArray(yc, x, ByteOrder.LITTLE_ENDIAN));
    final byte[] xd = {(byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00,
                       (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00};
    final Long yd1 = 0xFF00FF00FF00FF00L;
    final Long yd2 = 0x00FF00FF00FF00FFL;
    final byte[] zd = {};
    assertArrayEquals(xd, LongUtils.toByteArray(yd1, zd, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xd, LongUtils.toByteArray(yd2, zd, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xd, LongUtils.toByteArray(yd1, x, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xd, LongUtils.toByteArray(yd2, x, ByteOrder.LITTLE_ENDIAN));
    final byte[] xe = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                       (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
    final Long ye = 0xFFFFFFFFFFFFFFFFL;
    final byte[] ze = {};
    assertArrayEquals(xe, LongUtils.toByteArray(ye, ze, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xe, LongUtils.toByteArray(ye, ze, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xe, LongUtils.toByteArray(ye, x, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xe, LongUtils.toByteArray(ye, x, ByteOrder.LITTLE_ENDIAN));
    final byte[] xf = {(byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA,
                       (byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA};
    final Long yf1 = 0xBBAABBAABBAABBAAL;
    final Long yf2 = 0xAABBAABBAABBAABBL;
    final byte[] zf = {};
    assertArrayEquals(xf, LongUtils.toByteArray(yf1, zf, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xf, LongUtils.toByteArray(yf2, zf, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xf, LongUtils.toByteArray(yf1, x, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xf, LongUtils.toByteArray(yf2, x, ByteOrder.LITTLE_ENDIAN));
  }

  @Test
  public void testToClass_long() {
    for ( int i = 0; i < AREA.length; ++i) {
      assertSame(Long.TYPE, LongUtils.toClass(AREA[i]));
    }
  }

  @Test
  public void testToClass_Long() {
    assertSame(null, LongUtils.toClass(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      assertSame(Long.class, LongUtils.toClass(x));
    }
  }

  @Test
  public void testToClass_Long_ClassOfQ() {
    assertSame(null, LongUtils.toClass(null, null));
    
    assertSame(Long.class, LongUtils.toClass(null, Long.class));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      assertSame(Long.class, LongUtils.toClass(x, null));
      assertSame(Long.class, LongUtils.toClass(x, Long.class));
    }
  }

  @Test
  public void testToBigInteger_long() {
    for ( int i = 0; i < AREA.length; ++i) {
      final BigInteger x = BigInteger.valueOf(AREA[i]);
      assertEquals(x, LongUtils.toBigInteger(AREA[i]));
    }
  }

  @Test
  public void testToBigInteger_Long() {
    assertEquals(null, LongUtils.toBigInteger(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      final BigInteger y = BigInteger.valueOf(AREA[i]);
      assertEquals(y, LongUtils.toBigInteger(x));
    }
  }

  @Test
  public void testToBigInteger_Long_BigInteger() {
    assertEquals(null, LongUtils.toBigInteger(null, null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final BigInteger x = BigInteger.valueOf(AREA[i]);
      assertEquals(x, LongUtils.toBigInteger(null, x));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      final BigInteger y = BigInteger.valueOf(AREA[i]);
      assertEquals(y, LongUtils.toBigInteger(x, null));
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final BigInteger z = BigInteger.valueOf(j);
        assertEquals(y, LongUtils.toBigInteger(x, z));
      }
    }
  }

  @Test
  public void testToBigDecimal_long() {
    for ( int i = 0; i < AREA.length; ++i) {
      final BigDecimal x = BigDecimal.valueOf(AREA[i]);
      assertEquals(x, LongUtils.toBigDecimal(AREA[i]));
    }
  }

  @Test
  public void testToBigDecimal_Long() {
    assertEquals(null, LongUtils.toBigDecimal(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      final BigDecimal y = BigDecimal.valueOf(AREA[i]);
      assertEquals(y, LongUtils.toBigDecimal(x));
    }
  }

  @Test
  public void testToBigDecimal_Long_BigDecimal() {
    assertEquals(null, LongUtils.toBigDecimal(null, null));
    
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final BigDecimal x = BigDecimal.valueOf(i);
      assertEquals(x, LongUtils.toBigDecimal(null, x));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i];
      final BigDecimal y = BigDecimal.valueOf(AREA[i]);
      assertEquals(y, LongUtils.toBigDecimal(x, null));
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final BigDecimal z = BigDecimal.valueOf(j);
        assertEquals(y, LongUtils.toBigDecimal(x, z));
      }
    }
  }

}
