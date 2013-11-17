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
import com.github.haixing_hu.lang.SystemUtils;

import static org.junit.Assert.*;

public class DoubleUtilsTest {

  private static final double[] area = {Double.MIN_VALUE, Double.MIN_VALUE/2, 
    (double) -1, (double) 0, (double) 1, Double.MAX_VALUE/2, Double.MAX_VALUE};
  
  @Test
  public void testToPrimitive_Double() {
    assertEquals(DoubleUtils.DEFAULT, DoubleUtils.toPrimitive(null), DoubleUtils.EPSILON);
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      assertEquals(area[i], DoubleUtils.toPrimitive(x), DoubleUtils.EPSILON);
    }
  }

  @Test
  public void testToPrimitive_Double_double() {
    for ( int i = 0; i < area.length; ++i ) {
      assertEquals(area[i], DoubleUtils.toPrimitive(null, area[i]), DoubleUtils.EPSILON);
    }
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      for ( int j = 0; j < area.length; ++j ) {
        assertEquals(area[i], DoubleUtils.toPrimitive(x, area[j]), DoubleUtils.EPSILON);
      }
    }
  }

  @Test
  public void testToBoolean_double() {
    for ( int i = 0; i < area.length; ++i ) {
      assertEquals(area[i] != 0, DoubleUtils.toBoolean(area[i]));
    }
  }

  @Test
  public void testToBoolean_Double() {
    assertEquals(BooleanUtils.DEFAULT, DoubleUtils.toBoolean(null));
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      assertEquals(area[i] != 0, DoubleUtils.toBoolean(x));
    }
  }

  @Test
  public void testToBoolean_Double_Boolean() {
    assertEquals(true, DoubleUtils.toBoolean(null, true));
    assertEquals(false, DoubleUtils.toBoolean(null, false));
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      assertEquals(area[i] != 0, DoubleUtils.toBoolean(x, true));
      assertEquals(area[i] != 0, DoubleUtils.toBoolean(x, false));
    }
  }

  @Test
  public void testToBooleanObject_double() {
    for ( int i = 0; i < area.length; ++i ) {
      final Boolean x = (area[i] != 0);
      assertEquals(x, DoubleUtils.toBooleanObject(area[i]));
    }
  }

  @Test
  public void testToBooleanObject_Double() {
    assertEquals(null, DoubleUtils.toBooleanObject(null));
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      final Boolean y = (area[i] != 0);
      assertEquals(y, DoubleUtils.toBooleanObject(x));
    }
  }

  @Test
  public void testToBooleanObject_Double_Boolean() {
    assertEquals(null, DoubleUtils.toBooleanObject(null, null));
    
    final Boolean a = true;
    final Boolean b = false;
    assertEquals(a, DoubleUtils.toBooleanObject(null, a));
    assertEquals(b, DoubleUtils.toBooleanObject(null, b));
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      assertEquals(area[i] != 0, DoubleUtils.toBoolean(x, a));
      assertEquals(area[i] != 0, DoubleUtils.toBoolean(x, b));
    }
  }

  @Test
  public void testToChar_double() {
    for ( int i = 0; i < area.length; ++i ) {
      assertEquals((char) area[i], DoubleUtils.toChar(area[i]));
    }
  }

  @Test
  public void testToChar_Double() {
    assertEquals(CharUtils.DEFAULT, DoubleUtils.toChar(null));
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      assertEquals((char) area[i], DoubleUtils.toChar(x));
    }
  }

  @Test
  public void testToChar_Double_char() {
    final char[] a = {(char) Character.MIN_VALUE, (char) Character.MIN_VALUE/2,
        (char) 0, (char) 1, (char) Character.MAX_VALUE/2, (char) Character.MAX_VALUE};
    
    for ( int i = 0; i < a.length; ++i ) {
      assertEquals(a[i], DoubleUtils.toChar(null, a[i]));
    }
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      for ( int j = 0; j < a.length; ++j ) {
        assertEquals((char) area[i], DoubleUtils.toChar(x, a[j]));
      }
    }
  }

  @Test
  public void testToCharObject_double() {
    for ( int i = 0; i < area.length; ++i ) {
      final Character x = (char) area[i];
      assertEquals(x, DoubleUtils.toCharObject(area[i]));
    }
  }

  @Test
  public void testToCharObject_Double() {
    assertEquals(null, DoubleUtils.toCharObject(null));
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      final Character y = (char) area[i];
      assertEquals(y, DoubleUtils.toCharObject(x));
    }
  }

  @Test
  public void testToCharObject_Double_Character() {
    final Character[] a = {(char) Character.MIN_VALUE, (char) Character.MIN_VALUE/2,
        (char) 0, (char) 1, (char) Character.MAX_VALUE/2, (char) Character.MAX_VALUE};
    
    assertEquals(null, DoubleUtils.toCharObject(null, null));
    
    for ( int i = 0; i < a.length; ++i ) {
      assertEquals(a[i], DoubleUtils.toCharObject(null, a[i]));
    }
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      final Character y = (char) area[i];
      assertEquals(y, DoubleUtils.toCharObject(x, null));
      for ( int j = 0; j < a.length; ++j ) {
        assertEquals(y, DoubleUtils.toCharObject(x, a[j]));
      }
    }
  }

  @Test
  public void testToByte_double() {
    for ( int i = 0; i < area.length; ++i ) {
      assertEquals((byte) area[i], DoubleUtils.toByte(area[i]));
    }
  }

  @Test
  public void testToByte_Double() {
    assertEquals(ByteUtils.DEFAULT, DoubleUtils.toByte(null));
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      assertEquals((byte) area[i], DoubleUtils.toByte(x));
    }
  }

  @Test
  public void testToByte_Double_byte() {
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertEquals((byte) i, DoubleUtils.toByte(null, (byte) i));
    }
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        assertEquals((byte) area[i], DoubleUtils.toByte(x, (byte) j));
      }
    }
  }

  @Test
  public void testToByteObject_double() {
    for ( int i = 0; i < area.length; ++i ) {
      final Byte x = (byte) area[i];
      assertEquals(x, DoubleUtils.toByteObject(area[i]));
    }
  }

  @Test
  public void testToByteObject_Double() {
    assertEquals(null, DoubleUtils.toByteObject(null));
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      final Byte y = (byte) area[i];
      assertEquals(y, DoubleUtils.toByteObject(x));
    }
  }

  @Test
  public void testToByteObject_Double_Byte() {
    assertEquals(null, DoubleUtils.toByteObject(null, null));
    
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      assertEquals(x, DoubleUtils.toByteObject(null, x));
    }
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      final Byte y = (byte) area[i];
      assertEquals(y, DoubleUtils.toByteObject(x, null));
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final Byte z = (byte) j;
        assertEquals(y, DoubleUtils.toByteObject(x, z));
      }
    }
  }

  @Test
  public void testToShort_double() {
    for ( int i = 0; i < area.length; ++i ) {
      assertEquals((short) area[i], DoubleUtils.toShort(area[i]));
    }
  }

  @Test
  public void testToShort_Double() {
    assertEquals(IntUtils.DEFAULT, DoubleUtils.toShort(null));
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      assertEquals((short) area[i], DoubleUtils.toShort(x));
    }
  }

  @Test
  public void testToShort_Double_short() {
    final short[] a = {Short.MIN_VALUE, Short.MIN_VALUE/2, (short) -1, (short) 0, (short) 1,
        Short.MAX_VALUE/2, Short.MAX_VALUE};
    
    for ( int i = 0; i < a.length; ++i ) {
      assertEquals(a[i], DoubleUtils.toShort(null, a[i]));
    }
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      for ( int j = 0; j < a.length; ++j) {
        assertEquals((short) area[i], DoubleUtils.toShort(x, a[j]));
      }
    }
  }

  @Test
  public void testToShortObject_double() {
    for ( int i = 0; i < area.length; ++i ) {
      final Short x = (short) area[i];
      assertEquals(x, DoubleUtils.toShortObject(area[i]));
    }
  }

  @Test
  public void testToShortObject_Double() {
    assertEquals(null, DoubleUtils.toShortObject(null));
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      final Short y = (short) area[i];
      assertEquals(y, DoubleUtils.toShortObject(x));
    }
  }

  @Test
  public void testToShortObject_Double_Short() {
    final Short[] a = {Short.MIN_VALUE, Short.MIN_VALUE/2, (short) -1, (short) 0, (short) 1,
        Short.MAX_VALUE/2, Short.MAX_VALUE};
    
    assertEquals(null, DoubleUtils.toShortObject(null, null));
    
    for ( int i = 0; i < a.length; ++i ) {
      assertEquals(a[i], DoubleUtils.toShortObject(null, a[i]));
    }
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      final Short y = (short) area[i];
      assertEquals(y, DoubleUtils.toShortObject(x, null));
      for ( int j = 0; j < a.length; ++j ) {
        assertEquals(y, DoubleUtils.toShortObject(x, a[j]));
      }
    }
  }

  @Test
  public void testToInt_double() {
    for ( int i = 0; i < area.length; ++i ) {
      assertEquals((int) area[i], DoubleUtils.toInt(area[i]));
    }
  }

  @Test
  public void testToInt_Double() {
    assertEquals(IntUtils.DEFAULT, DoubleUtils.toInt(null));
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      assertEquals((int) area[i], DoubleUtils.toInt(x));
    }
  }

  @Test
  public void testToInt_Double_int() {
    final int[] a = {Integer.MIN_VALUE, Integer.MIN_VALUE/2, -1, 0, 1,
        Integer.MAX_VALUE/2, Integer.MAX_VALUE};
    
    for ( int i = 0; i < a.length; ++i ) {
      assertEquals(a[i], DoubleUtils.toInt(null, a[i]));
    }
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      for ( int j = 0; j < a.length; ++j ) {
        assertEquals((int) area[i], DoubleUtils.toInt(x, a[j]));
      }
    }
  }

  @Test
  public void testToIntObject_double() {
    for ( int i = 0; i < area.length; ++i ) {
      final Integer x = (int) area[i];
      assertEquals(x, DoubleUtils.toIntObject(area[i]));
    }
  }

  @Test
  public void testToIntObject_Double() {
    assertEquals(null, DoubleUtils.toIntObject(null));
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      final Integer y = (int) area[i];
      assertEquals(y, DoubleUtils.toIntObject(x));
    }
  }

  @Test
  public void testToIntObject_Double_Integer() {
    final Integer[] a = {Integer.MIN_VALUE, Integer.MIN_VALUE/2, -1, 0, 1,
        Integer.MAX_VALUE/2, Integer.MAX_VALUE};
    
    assertEquals(null, DoubleUtils.toIntObject(null, null));
    
    for ( int i = 0; i < a.length; ++i ) {
      assertEquals(a[i], DoubleUtils.toIntObject(null, a[i]));
    }
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      final Integer y = (int) area[i];
      assertEquals(y, DoubleUtils.toIntObject(x, null));
      for ( int j = 0; j < a.length; ++j ) {
        assertEquals(y, DoubleUtils.toIntObject(x, a[j]));
      }
    }
  }

  @Test
  public void testToLong_double() {
    for ( int i = 0; i < area.length; ++i ) {
      assertEquals((long) area[i], DoubleUtils.toLong(area[i]));
    }
  }

  @Test
  public void testToLong_Double() {
    assertEquals(LongUtils.DEFAULT, DoubleUtils.toLong(null));
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      assertEquals((long) area[i], DoubleUtils.toLong(x));
    }
  }

  @Test
  public void testToLong_Double_long() {
    final long[] a = {Long.MIN_VALUE, Long.MIN_VALUE/2, (long) -1, (long) 0, (long) 1,
        Long.MAX_VALUE/2, Long.MAX_VALUE};
    
    for ( int i = 0; i < a.length; ++i ) {
      assertEquals(a[i], DoubleUtils.toLong(null, a[i]));
    }
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      final long y = (long) area[i];
      for ( int j = 0; j < a.length; ++j ) {
        assertEquals(y, DoubleUtils.toLong(x, a[j]));
      }
    }
  }

  @Test
  public void testToLongObject_double() {
    for ( int i = 0; i < area.length; ++i ) {
      final Long x = (long) area[i];
      assertEquals(x, DoubleUtils.toLongObject(area[i]));
    }
  }

  @Test
  public void testToLongObject_Double() {
    assertEquals(null, DoubleUtils.toLongObject(null));
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      final Long y = (long) area[i];
      assertEquals(y, DoubleUtils.toLongObject(x));
    }
  }

  @Test
  public void testToLongObject_Double_Long() {
    final Long[] a = {Long.MIN_VALUE, Long.MIN_VALUE/2, (long) -1, (long) 0, (long) 1,
        Long.MAX_VALUE/2, Long.MAX_VALUE};
    
    assertEquals(null, DoubleUtils.toLongObject(null, null));
    
    for ( int i = 0; i < a.length; ++i ) {
      assertEquals(a[i], DoubleUtils.toLongObject(null, a[i]));
    }
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      final Long y = (long) area[i];
      assertEquals(y, DoubleUtils.toLongObject(x, null));
      for ( int j = 0; j < a.length; ++j ) {
        assertEquals(y, DoubleUtils.toLongObject(x, a[j]));
      }
    }
  }

  @Test
  public void testToFloat_double() {
    for ( int i = 0; i < area.length; ++i ) {
      assertEquals((float) area[i], DoubleUtils.toFloat(area[i]), FloatUtils.EPSILON);
    }
  }

  @Test
  public void testToFloat_Double() {
    assertEquals(FloatUtils.DEFAULT, DoubleUtils.toFloat(null), FloatUtils.EPSILON);
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      assertEquals((float) area[i], DoubleUtils.toFloat(x), FloatUtils.EPSILON);
    }
  }

  @Test
  public void testToFloat_Double_float() {
    final float[] a = {Float.MIN_VALUE, Float.MIN_VALUE/2, (float) -1, (float) 0, (float) 1,
        Float.MAX_VALUE/2, Float.MAX_VALUE};
    
    for ( int i = 0; i < a.length; ++i ) {
      assertEquals(a[i], DoubleUtils.toFloat(null, a[i]), FloatUtils.EPSILON);
    }
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      for ( int j = 0; j < a.length; ++j ) {
        assertEquals((float) area[i], DoubleUtils.toFloat(x, a[j]), FloatUtils.EPSILON);
      }
    }
  }

  @Test
  public void testToFloatObject_double() {
    for ( int i = 0; i < area.length; ++i ) {
      final Float x = (float) area[i];
      assertEquals(x, DoubleUtils.toFloatObject(area[i]));
    }
  }

  @Test
  public void testToFloatObject_Double() {
    assertEquals(null, DoubleUtils.toFloatObject(null));
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      final Float y = (float) area[i];
      assertEquals(y, DoubleUtils.toFloatObject(x));
    }
  }

  @Test
  public void testToFloatObject_Double_Float() {
    final Float[] a = {Float.MIN_VALUE, Float.MIN_VALUE/2, (float) -1, (float) 0, (float) 1,
        Float.MAX_VALUE/2, Float.MAX_VALUE};
    
    for ( int i = 0; i < a.length; ++i ) {
      assertEquals(a[i], DoubleUtils.toFloatObject(null, a[i]));
    }
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      final Float y = (float) area[i];
      for ( int j = 0; j < a.length; ++j ) {
        assertEquals(y, DoubleUtils.toFloatObject(x, a[j]));
      }
    }
  }

  @Test
  public void testToString_double() {
    for ( int i = 0; i < area.length; ++i ) {
      assertEquals(Double.toString(area[i]), DoubleUtils.toString(area[i]));
    }
  }

  @Test
  public void testToString_Double() {
    assertEquals(null, DoubleUtils.toString(null));
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      assertEquals(Double.toString(area[i]), DoubleUtils.toString(x));
    }
  }

  @Test
  public void testToString_Double_String() {
    Double value1 = null;
    String value2 = null;
    assertEquals(null, DoubleUtils.toString(value1, value2));
    
    value2 = "helloworld";
    assertEquals("helloworld", DoubleUtils.toString(value1, value2));
    
    value1 = 0.5;
    value2 = null;
    assertEquals("0.5", DoubleUtils.toString(value1, value2));
    
    value1 = 0.5;
    value2 = "helloworld";
    assertEquals("0.5", DoubleUtils.toString(value1, value2));
  }

  @Test
  public void testToDate_double() {
    for ( int i = 0; i < area.length; ++i ) {
      final Date x = new Date((long) area[i]);
      assertEquals(x, DoubleUtils.toDate(area[i]));
    }
  }

  @Test
  public void testToDate_Double() {
    assertEquals(null, DoubleUtils.toDate(null));
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      final Date y = new Date((long) area[i]);
      assertEquals(y, DoubleUtils.toDate(x));
    }
  }

  @Test
  public void testToDate_Double_Date() {
    final Date[] a = {new Date(Long.MIN_VALUE), new Date(Long.MIN_VALUE/2),
        new Date((long) -1), new Date((long) 0), new Date((long) 1),
        new Date(Long.MAX_VALUE/2), new Date(Long.MAX_VALUE)};
    
    assertEquals(null, DoubleUtils.toDate(null, null));
    
    for ( int i = 0; i < a.length; ++i ) {
      assertEquals(a[i], DoubleUtils.toDate(null, a[i]));
    }
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      final Date y = new Date((long) area[i]);
      assertEquals(y, DoubleUtils.toDate(x, null));
      for ( int j = 0; j < a.length; ++j ) {
        assertEquals(y, DoubleUtils.toDate(x, a[j]));
      }
    }
  }

  @Test
  public void testToByteArray_double() {
    for ( int i = 0; i < area.length; ++i ) {
      assertArrayEquals(
          LongUtils.toByteArray(Double.doubleToLongBits(area[i]),SystemUtils.BYTE_ORDER), 
          DoubleUtils.toByteArray(area[i]));
    }
  }

  @Test
  public void testToByteArray_double_ByteOrder() {
    for ( int i = 0; i < area.length; ++i) {
      assertArrayEquals(
          LongUtils.toByteArray(Double.doubleToLongBits(area[i]),ByteOrder.BIG_ENDIAN), 
          DoubleUtils.toByteArray(area[i], ByteOrder.BIG_ENDIAN));
      assertArrayEquals(
          LongUtils.toByteArray(Double.doubleToLongBits(area[i]),ByteOrder.LITTLE_ENDIAN), 
          DoubleUtils.toByteArray(area[i], ByteOrder.LITTLE_ENDIAN));
    }
  }

  @Test
  public void testToByteArray_Double() {
    assertEquals(null, DoubleUtils.toByteArray(null));
    
    for ( int i = 0; i < area.length; ++i ) {
      final Double x = area[i];
      assertArrayEquals(
          LongUtils.toByteArray(Double.doubleToLongBits(x),SystemUtils.BYTE_ORDER), 
          DoubleUtils.toByteArray(x));
    }
  }

  @Test
  public void testToByteArray_Double_ByteOrder() {
    assertEquals(null, DoubleUtils.toByteArray(null, ByteOrder.BIG_ENDIAN));
    assertEquals(null, DoubleUtils.toByteArray(null, ByteOrder.LITTLE_ENDIAN));
    
    for ( int i = 0; i < area.length; ++i) {
      final Double x = area[i];
      assertArrayEquals(
          LongUtils.toByteArray(Double.doubleToLongBits(area[i]),ByteOrder.BIG_ENDIAN), 
          DoubleUtils.toByteArray(x, ByteOrder.BIG_ENDIAN));
      assertArrayEquals(
          LongUtils.toByteArray(Double.doubleToLongBits(area[i]),ByteOrder.LITTLE_ENDIAN), 
          DoubleUtils.toByteArray(x, ByteOrder.LITTLE_ENDIAN));
    }
  }

  @Test
  public void testToByteArray_Double_ByteArray() {
    final byte[] a = null;
    assertEquals(a, DoubleUtils.toByteArray(null, a));
    
    for ( int i = 0; i < area.length; ++i) {
      final Double x = area[i];
      final byte[] y = {};
      assertArrayEquals(LongUtils.toByteArray(Double.doubleToLongBits(area[i]),SystemUtils.BYTE_ORDER), 
          DoubleUtils.toByteArray(x, y));
    }
  }

  @Test
  public void testToByteArray_Double_ByteArray_ByteOrder() {
    for ( int i = 0; i < area.length; ++i) {
      final Double x = area[i];
      final byte[] y = {};
      assertArrayEquals(LongUtils.toByteArray(Double.doubleToLongBits(area[i]),ByteOrder.BIG_ENDIAN), 
          DoubleUtils.toByteArray(x, y, ByteOrder.BIG_ENDIAN));
      assertArrayEquals(LongUtils.toByteArray(Double.doubleToLongBits(area[i]),ByteOrder.LITTLE_ENDIAN), 
          DoubleUtils.toByteArray(x, y, ByteOrder.LITTLE_ENDIAN));
    }
  }

  @Test
  public void testToClass_double() {
    for ( int i = 0; i < area.length; ++i) {
      assertEquals(Double.TYPE, DoubleUtils.toClass(area[i]));
    }
  }

  @Test
  public void testToClass_Double() {
    assertEquals(null, DoubleUtils.toClass(null));
    
    for ( int i = 0; i < area.length; ++i) {
      final Double x = area[i];
      assertEquals(Double.class, DoubleUtils.toClass(x));
    }
  }

  @Test
  public void testToClass_Double_ClassOfQ() {
    assertEquals(null, DoubleUtils.toClass(null, null));
    
    assertSame(Double.class, DoubleUtils.toClass(null, Double.class));
    
    for ( int i = 0; i < area.length; ++i) {
      final Double x = area[i];
      assertSame(Double.class, DoubleUtils.toClass(x, null));
      assertSame(Double.class, DoubleUtils.toClass(x, Integer.class));
    }
  }

  @Test
  public void testToBigInteger_double() {
    for ( int i = 0; i < area.length; ++i) {
      final BigInteger x = BigInteger.valueOf((long) area[i]);
      assertEquals(x, DoubleUtils.toBigInteger(area[i]));
    }
  }

  @Test
  public void testToBigInteger_Double() {
    assertEquals(null, DoubleUtils.toBigInteger(null));
    
    for ( int i = 0; i < area.length; ++i) {
      final Double x = area[i];
      final BigInteger y = BigInteger.valueOf((long) area[i]);
      assertEquals(y, DoubleUtils.toBigInteger(x));
    }
  }

  @Test
  public void testToBigInteger_Double_BigInteger() {
    assertEquals(null, DoubleUtils.toBigInteger(null, null));
    
    for ( int i = 0; i < area.length; ++i) {
      final BigInteger x = BigInteger.valueOf((long) area[i]);
      assertEquals(x, DoubleUtils.toBigInteger(null, x));
    }
    
    for ( int i = 0; i < area.length; ++i) {
      final Double x = area[i];
      final BigInteger y = BigInteger.valueOf((long) area[i]);
      assertEquals(y, DoubleUtils.toBigInteger(x, null));
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final BigInteger z = BigInteger.valueOf(j);
        assertEquals(y, DoubleUtils.toBigInteger(x, z));
      }
    }
  }

  @Test
  public void testToBigDecimal_double() {
    for ( int i = 0; i < area.length; ++i) {
      final BigDecimal x = BigDecimal.valueOf(area[i]);
      assertEquals(x, DoubleUtils.toBigDecimal(area[i]));
    }
  }

  @Test
  public void testToBigDecimal_Double() {
    assertEquals(null, DoubleUtils.toBigDecimal(null));
    
    for ( int i = 0; i < area.length; ++i) {
      final Double x = area[i];
      final BigDecimal y = BigDecimal.valueOf(area[i]);
      assertEquals(y, DoubleUtils.toBigDecimal(x));
    }
  }

  @Test
  public void testToBigDecimal_Double_BigDecimal() {
    assertEquals(null, DoubleUtils.toBigDecimal(null, null));
    
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final BigDecimal x = BigDecimal.valueOf(i);
      assertEquals(x, DoubleUtils.toBigDecimal(null, x));
    }
    
    for ( int i = 0; i < area.length; ++i) {
      final Double x = area[i];
      final BigDecimal y = BigDecimal.valueOf(area[i]);
      assertEquals(y, DoubleUtils.toBigDecimal(x, null));
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final BigDecimal z = BigDecimal.valueOf(j);
        assertEquals(y, DoubleUtils.toBigDecimal(x, z));
      }
    }
  }

}
