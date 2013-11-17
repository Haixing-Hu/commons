package com.github.haixing_hu.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteOrder;
import java.util.Date;

import org.junit.Test;

import com.github.haixing_hu.lang.BooleanUtils;
import com.github.haixing_hu.lang.ByteUtils;
import com.github.haixing_hu.lang.CharUtils;
import com.github.haixing_hu.lang.DateUtils;
import com.github.haixing_hu.lang.DoubleUtils;
import com.github.haixing_hu.lang.FloatUtils;
import com.github.haixing_hu.lang.IntUtils;
import com.github.haixing_hu.lang.LongUtils;
import com.github.haixing_hu.lang.ShortUtils;

import static org.junit.Assert.*;

public class DateUtilsTest {
  
  private final Date[] AREA = {new Date(Integer.MIN_VALUE), new Date(Integer.MIN_VALUE/2),
                       new Date(0), new Date(1), new Date(Integer.MAX_VALUE), 
                       new Date(Integer.MAX_VALUE)};
  
  @Test
  public void testToBoolean_Date() {
    assertEquals(BooleanUtils.DEFAULT, DateUtils.toBoolean(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals(AREA[i].getTime() != 0, DateUtils.toBoolean(AREA[i]));
    }
  }

  @Test
  public void testToBoolean_Date_boolean() {
    assertEquals(true, DateUtils.toBoolean(null, true));
    assertEquals(false, DateUtils.toBoolean(null, false));
    
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals(AREA[i].getTime() != 0, DateUtils.toBoolean(AREA[i], true));
      assertEquals(AREA[i].getTime() != 0, DateUtils.toBoolean(AREA[i], false));
    }
  }

  @Test
  public void testToBooleanObject_Date() {
    assertEquals(null, DateUtils.toBooleanObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Boolean x = (AREA[i].getTime() != 0);
      assertEquals(x, DateUtils.toBooleanObject(AREA[i]));
    }
  }

  @Test
  public void testToBooleanObject_Date_Boolean() {
    assertEquals(null, DateUtils.toBooleanObject(null, null));
    
    final Boolean x = true;
    final Boolean y = false;
    assertEquals(true, DateUtils.toBooleanObject(null, x));
    assertEquals(false, DateUtils.toBooleanObject(null, y));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Boolean z = (AREA[i].getTime() != 0);
      assertEquals(z, DateUtils.toBooleanObject(AREA[i], x));
      assertEquals(z, DateUtils.toBooleanObject(AREA[i], y));
    }
  }

  @Test
  public void testToChar_Date() {
    assertEquals(CharUtils.DEFAULT, DateUtils.toChar(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals((char) AREA[i].getTime(),  DateUtils.toChar(AREA[i]));
    }
  }

  @Test
  public void testToChar_Date_char() {
    final char[] a = {Character.MIN_VALUE, Character.MIN_VALUE/2, (char) 0, (char) 1,
                      Character.MAX_VALUE/2,Character.MAX_VALUE};
    
    for ( int i = 0 ; i < a.length; ++i) {
      assertEquals(a[i], DateUtils.toChar(null, a[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      for ( int j = 0; j < a.length; ++j) {
        assertEquals((char) AREA[i].getTime(), DateUtils.toChar(AREA[i], a[i]));
      }
    }
  }

  @Test
  public void testToCharObject_Date() {
    assertEquals(null, DateUtils.toCharObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Character x = (char) (AREA[i].getTime());
      assertEquals(x, DateUtils.toCharObject(AREA[i]));
    }
  }

  @Test
  public void testToCharObject_Date_Character() {
    final Character[] a = {Character.MIN_VALUE, Character.MIN_VALUE/2, (char) 0, (char) 1,
                           Character.MAX_VALUE/2,Character.MAX_VALUE};
    
    assertEquals(null, DateUtils.toCharObject(null, null));
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], DateUtils.toCharObject(null, a[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Character x = (char) (AREA[i].getTime());
      for ( int j = 0; j < a.length; ++j) {
        assertEquals(x, DateUtils.toCharObject(AREA[i], a[j]));
      }
    }
  }

  @Test
  public void testToByte_Date() {
    assertEquals(ByteUtils.DEFAULT, DateUtils.toByte(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals((byte) AREA[i].getTime(), DateUtils.toByte(AREA[i]));
    }
  }

  @Test
  public void testToByte_Date_byte() {
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertEquals((byte) i, DateUtils.toByte(null, (byte) i));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        assertEquals((byte) AREA[i].getTime(), DateUtils.toByte(AREA[i], (byte) j));
      }
    }
  }

  @Test
  public void testToByteObject_Date() {
    assertEquals(null, DateUtils.toByteObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Byte x = (byte) AREA[i].getTime();
      assertEquals(x, DateUtils.toByteObject(AREA[i]));
    }
  }

  @Test
  public void testToByteObject_Date_Byte() {
    assertEquals(null, DateUtils.toByteObject(null, null));
    
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      assertEquals(x, DateUtils.toByteObject(null, x));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Byte x = (byte) AREA[i].getTime();
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final Byte y = (byte) j;
        assertEquals(x, DateUtils.toByteObject(AREA[i], y));
      }
    }
  }

  @Test
  public void testToShort_Date() {
    assertEquals(ShortUtils.DEFAULT, DateUtils.toShort(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals((short) AREA[i].getTime(), DateUtils.toShort(AREA[i]));
    }
  }

  @Test
  public void testToShort_Date_short() {
    final short[] a = {Short.MIN_VALUE, Short.MIN_VALUE/2, (short) -1, (short) 0, (short) 1,
                       Short.MAX_VALUE/2, Short.MAX_VALUE};
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], DateUtils.toShort(null, a[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      for ( int j = 0; j < a.length; ++j) {
        assertEquals((short) AREA[i].getTime(), DateUtils.toShort(AREA[i], a[j]));
      }
    }
  }

  @Test
  public void testToShortObject_Date() {
    assertEquals(null, DateUtils.toShortObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i].getTime();
      assertEquals(x, DateUtils.toShortObject(AREA[i]));
    }
  }

  @Test
  public void testToShortObject_Date_Short() {
    final Short[] a = {Short.MIN_VALUE, Short.MIN_VALUE/2, (short) -1, (short) 0, (short) 1,
                       Short.MAX_VALUE/2, Short.MAX_VALUE};
    
    assertEquals(null, DateUtils.toShortObject(null, null));
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], DateUtils.toShortObject(null, a[i]));
    }

    for ( int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i].getTime();
      for ( int j = 0; j < a.length; ++j) {
        assertEquals(x, DateUtils.toShortObject(AREA[i], a[j]));
      }
    }
  }

  @Test
  public void testToInt_Date() {
    assertEquals(IntUtils.DEFAULT, DateUtils.toInt(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals((int) AREA[i].getTime(), DateUtils.toInt(AREA[i]));
    }
  }

  @Test
  public void testToInt_Date_int() {
    final int[] a = {Integer.MIN_VALUE, Integer.MIN_VALUE/2, -1, 0, 1, Integer.MAX_VALUE/2,
                     Integer.MAX_VALUE};
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], DateUtils.toInt(null, a[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      for ( int j = 0; j < a.length; ++j) {
        assertEquals((int) AREA[i].getTime(), DateUtils.toInt(AREA[i], a[j]));
      }
    }
  }

  @Test
  public void testToIntObject_Date() {
    assertEquals(null, DateUtils.toIntObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Integer x = (int) AREA[i].getTime();
      assertEquals(x, DateUtils.toIntObject(AREA[i]));
    }
  }

  @Test
  public void testToIntObject_Date_Integer() {
    final Integer[] a = {Integer.MIN_VALUE, Integer.MIN_VALUE/2, -1, 0, 1, 
                         Integer.MAX_VALUE/2,Integer.MAX_VALUE};
    
    assertEquals(null, DateUtils.toIntObject(null, null));
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], DateUtils.toIntObject(null, a[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Integer x = (int) AREA[i].getTime();
      for ( int j = 0; j < a.length; ++j) {
        assertEquals(x, DateUtils.toIntObject(AREA[i], a[j]));
      }
    }
  }

  @Test
  public void testToLong_Date() {
    assertEquals(LongUtils.DEFAULT, DateUtils.toLong(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals(AREA[i].getTime(), DateUtils.toLong(AREA[i]));
    }
  }

  @Test
  public void testToLong_Date_long() {
    final long[] a = {Long.MIN_VALUE, Long.MIN_VALUE/2, (long) -1, (long) 0, (long) 1,
                      Long.MAX_VALUE/2, Long.MAX_VALUE};
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], DateUtils.toLong(null, a[i]));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      for ( int j = 0; j < a.length; ++j) {
        assertEquals(AREA[i].getTime(), DateUtils.toLong(AREA[i], a[j]));
      }
    }
  }

  @Test
  public void testToLongObject_Date() {
    assertEquals(null, DateUtils.toLongObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Long x = AREA[i].getTime();
      assertEquals(x, DateUtils.toLongObject(AREA[i]));
    }
  }

  @Test
  public void testToLongObject_Date_Integer() {
    //TODO
//    Date value1 = new Date();
//    value1 = null;
//    Integer value2 = null;
//    
//    assertEquals((Long) null, DateUtils.toLongObject(value1, (Integer) null));
//    value2 = 2;
//    Long out = (long) 2;
//    assertEquals(out, DateUtils.toLongObject(value1, value2));
  }

  @Test
  public void testToFloat_Date() {
    assertEquals(FloatUtils.DEFAULT, DateUtils.toFloat(null), FloatUtils.EPSILON);
    
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals((float) AREA[i].getTime(), DateUtils.toFloat(AREA[i]), FloatUtils.EPSILON);
    }
  }

  @Test
  public void testToFloat_Date_float() {
    final float[] a = {Float.MIN_VALUE, Float.MIN_VALUE/2, (float) -1, (float) 0, (float) 1,
                       Float.MAX_VALUE/2, Float.MAX_VALUE};
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], DateUtils.toFloat(null, a[i]), FloatUtils.EPSILON);
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      for ( int j = 0; j < a.length; ++j) {
        assertEquals((float) AREA[i].getTime(), DateUtils.toFloat(AREA[i], a[j]), FloatUtils.EPSILON);
      }
    }
  }

  @Test
  public void testToFloatObject_Date() {
    assertEquals(null, DateUtils.toFloatObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Float x = (float) AREA[i].getTime();
      assertEquals(x, DateUtils.toFloatObject(AREA[i]));
    }
  }

  @Test
  public void testToFloatObject_Date_Float() {
    final Float[] a = {Float.MIN_VALUE, Float.MIN_VALUE/2, (float) -1, (float) 0, (float) 1,
        Float.MAX_VALUE/2, Float.MAX_VALUE};

    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], DateUtils.toFloatObject(null, a[i]));
    }

    for ( int i = 0; i < AREA.length; ++i) {
      final Float x = (float) AREA[i].getTime();
      for ( int j = 0; j < a.length; ++j) {
        assertEquals(x, DateUtils.toFloatObject(AREA[i], a[j]));
      }
    }
  }

  @Test
  public void testToDouble_Date() {
    assertEquals(DoubleUtils.DEFAULT, DateUtils.toDouble(null), DoubleUtils.EPSILON);
    
    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals((double) AREA[i].getTime(), DateUtils.toDouble(AREA[i]), DoubleUtils.EPSILON);
    }
  }

  @Test
  public void testToDouble_Date_double() {
    final double[] a = {Double.MIN_VALUE, Double.MIN_VALUE/2, (double) -1, (double) 0, (double) 1,
                        Double.MAX_VALUE/2, Double.MAX_VALUE};

    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], DateUtils.toDouble(null, a[i]), DoubleUtils.EPSILON);
    }

    for ( int i = 0; i < AREA.length; ++i) {
      for ( int j = 0; j < a.length; ++j) {
        assertEquals((double) AREA[i].getTime(), DateUtils.toDouble(AREA[i], a[j]), DoubleUtils.EPSILON);
      }
    }
  }

  @Test
  public void testToDoubleObject_Date() {
    assertEquals(null, DateUtils.toDoubleObject(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final Double x = (double) AREA[i].getTime();
      assertEquals(x, DateUtils.toDoubleObject(AREA[i]));
    }
  }

  @Test
  public void testToDoubleObjectDateDouble() {
    final Double[] a = {Double.MIN_VALUE, Double.MIN_VALUE/2, (double) -1, (double) 0, (double) 1,
                        Double.MAX_VALUE/2, Double.MAX_VALUE};
    
    for ( int i = 0; i < a.length; ++i) {
      assertEquals(a[i], DateUtils.toDoubleObject(null, a[i]));
    }

    for ( int i = 0; i < AREA.length; ++i) {
      final Double x = (double) AREA[i].getTime();
      for ( int j = 0; j < a.length; ++j) {
        assertEquals(x, DateUtils.toDoubleObject(AREA[i], a[j]));
      }
    }
  }

  @Test
  public void testToString_Date_String() {
    //TODO
  }

  @Test
  public void testToString_Date_String_String() {
    //TODO
  }

  @Test
  public void testToByteArray_Date() {
    assertEquals(null, DateUtils.toByteArray(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      assertArrayEquals(LongUtils.toByteArray(AREA[i].getTime(), ByteOrder.BIG_ENDIAN),
                        DateUtils.toByteArray(AREA[i]));
    }
  }

  @Test
  public void testToByteArray_Date_ByteOrder() {
    assertArrayEquals(null, DateUtils.toByteArray(null, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(null, DateUtils.toByteArray(null, ByteOrder.LITTLE_ENDIAN));
    
    for ( int i = 0; i < AREA.length; ++i) {
      assertArrayEquals(LongUtils.toByteArray(AREA[i].getTime(), ByteOrder.BIG_ENDIAN),
                   DateUtils.toByteArray(AREA[i], ByteOrder.BIG_ENDIAN));
      assertArrayEquals(LongUtils.toByteArray(AREA[i].getTime(), ByteOrder.LITTLE_ENDIAN),
                   DateUtils.toByteArray(AREA[i], ByteOrder.LITTLE_ENDIAN));
    }
  }

  @Test
  public void testToByteArray_Date_ByteArray() {
    final byte[] x = null;
    assertArrayEquals(x, DateUtils.toByteArray(null, x));
    
    final byte[] a = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                      (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    assertArrayEquals(a, DateUtils.toByteArray(null, a));
    final byte[] b = {(byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00,
                      (byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00};
    assertArrayEquals(b, DateUtils.toByteArray(null, b));
    final byte[] c = {(byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB,
                      (byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB};
    assertArrayEquals(c, DateUtils.toByteArray(null, c));
    final byte[] d = {(byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00,
                      (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00};
    assertArrayEquals(d, DateUtils.toByteArray(null, d));
    final byte[] e = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                      (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
    assertArrayEquals(e, DateUtils.toByteArray(null, e));
    final byte[] f = {(byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA,
                     (byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA};
    assertArrayEquals(f, DateUtils.toByteArray(null, f));
    
    final byte[] y = {};
    for ( int i = 0; i < AREA.length; ++i) {
      assertArrayEquals(LongUtils.toByteArray(AREA[i].getTime()),
                        DateUtils.toByteArray(AREA[i], y));
    }
  }

  @Test
  public void testToByteArray_Date_ByteArray_ByteOrder() {
    final byte[] x = null;
    assertEquals(null, DateUtils.toByteArray(null, x, ByteOrder.BIG_ENDIAN));
    assertEquals(null, DateUtils.toByteArray(null, x, ByteOrder.LITTLE_ENDIAN));
    
    final byte[] a = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                      (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    assertArrayEquals(a, DateUtils.toByteArray(null, a, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(a, DateUtils.toByteArray(null, a, ByteOrder.LITTLE_ENDIAN));
    final byte[] b = {(byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00,
                      (byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00};
    assertArrayEquals(b, DateUtils.toByteArray(null, b, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(b, DateUtils.toByteArray(null, b, ByteOrder.LITTLE_ENDIAN));
    final byte[] c = {(byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB,
                      (byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB};
    assertArrayEquals(c, DateUtils.toByteArray(null, c, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(c, DateUtils.toByteArray(null, c, ByteOrder.LITTLE_ENDIAN));
    final byte[] d = {(byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00,
                      (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00};
    assertArrayEquals(d, DateUtils.toByteArray(null, d, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(d, DateUtils.toByteArray(null, d, ByteOrder.LITTLE_ENDIAN));
    final byte[] e = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                      (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
    assertArrayEquals(e, DateUtils.toByteArray(null, e, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(e, DateUtils.toByteArray(null, e, ByteOrder.LITTLE_ENDIAN));
    final byte[] f = {(byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA,
                      (byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA};
    assertArrayEquals(f, DateUtils.toByteArray(null, f, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(f, DateUtils.toByteArray(null, f, ByteOrder.LITTLE_ENDIAN));
    
    final byte[] y = {};
    for ( int i = 0; i < AREA.length; ++i) {
      assertArrayEquals(LongUtils.toByteArray(AREA[i].getTime(), ByteOrder.BIG_ENDIAN),
                        DateUtils.toByteArray(AREA[i], y, ByteOrder.BIG_ENDIAN));
      assertArrayEquals(LongUtils.toByteArray(AREA[i].getTime(), ByteOrder.LITTLE_ENDIAN),
                        DateUtils.toByteArray(AREA[i], y, ByteOrder.LITTLE_ENDIAN));
    }
  }

  @Test
  public void testToClass_Date() {
    assertSame(null, DateUtils.toClass(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      assertSame(Date.class, DateUtils.toClass(AREA[i]));
    }
  }

  @Test
  public void testToClass_Date_ClassOfQ() {
    assertSame(null, DateUtils.toClass(null, null));
    
    assertSame(Date.class, DateUtils.toClass(null, Date.class));
    
    for ( int i = 0; i < AREA.length; ++i) {
      assertSame(Date.class, DateUtils.toClass(AREA[i], null));
      assertSame(Date.class, DateUtils.toClass(AREA[i], Date.class));
    }
  }

  @Test
  public void testToBigInteger_Date() {
    assertEquals(null, DateUtils.toBigInteger(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final BigInteger x = BigInteger.valueOf(AREA[i].getTime());
      assertEquals(x, DateUtils.toBigInteger(AREA[i]));
    }
  }

  @Test
  public void testToBigInteger_Date_BigInteger() {
    assertEquals(null, DateUtils.toBigInteger(null, null));
    
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i){ 
      final BigInteger x = BigInteger.valueOf(i);
      assertEquals(x, DateUtils.toBigInteger(null, x));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final BigInteger x = BigInteger.valueOf(AREA[i].getTime());
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final BigInteger y = BigInteger.valueOf(i);
        assertEquals(x, DateUtils.toBigInteger(AREA[i], y));
      }
    }
  }

  @Test
  public void testToBigDecimal_Date() {
    assertEquals(null, DateUtils.toBigDecimal(null));
    
    for ( int i = 0; i < AREA.length; ++i) {
      final BigDecimal x = BigDecimal.valueOf(AREA[i].getTime());
      assertEquals(x, DateUtils.toBigDecimal(AREA[i]));
    }
  }

  @Test
  public void testToBigDecimal_Date_BigDecimal() {
   assertEquals(null, DateUtils.toBigDecimal(null, null));
    
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i){ 
      final BigDecimal x = BigDecimal.valueOf(i);
      assertEquals(x, DateUtils.toBigDecimal(null, x));
    }
    
    for ( int i = 0; i < AREA.length; ++i) {
      final BigDecimal x = BigDecimal.valueOf(AREA[i].getTime());
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final BigDecimal y = BigDecimal.valueOf(i);
        assertEquals(x, DateUtils.toBigDecimal(AREA[i], y));
      }
    }
  }

}
