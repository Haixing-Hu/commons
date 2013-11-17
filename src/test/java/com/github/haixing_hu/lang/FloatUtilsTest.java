package com.github.haixing_hu.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteOrder;
import java.util.Date;

import org.junit.Test;

import static org.junit.Assert.*;

public class FloatUtilsTest {

  private final float[] AREA = {Float.MIN_VALUE, Float.MIN_VALUE/2, -1, 0,
      1, Float.MAX_VALUE, Float.MAX_VALUE};

  @Test
  public void testToPrimitive_Float() {
    assertEquals(FloatUtils.DEFAULT, FloatUtils.toPrimitive(null), FloatUtils.EPSILON);

    for (final float x : AREA) {
      assertEquals(x, FloatUtils.toPrimitive(x), FloatUtils.EPSILON);
    }
  }

  @Test
  public void testToPrimitive_Float_float() {
    for (final float element : AREA) {
      assertEquals(element, FloatUtils.toPrimitive(null, element), FloatUtils.EPSILON);
    }

    for (final float x : AREA) {
      for (final float element : AREA) {
        assertEquals(x, FloatUtils.toPrimitive(x, element), FloatUtils.EPSILON);
      }
    }
  }

  @Test
  public void testToBoolean_float() {
    for (final float element : AREA) {
      assertEquals(element != 0, FloatUtils.toBoolean(element));
    }
  }

  @Test
  public void testToBoolean_Float() {
    assertEquals(BooleanUtils.DEFAULT, FloatUtils.toBoolean(null));

    for (final float x : AREA) {
      assertEquals(x != 0, FloatUtils.toBoolean(x));
    }
  }

  @Test
  public void testToBoolean_Float_boolean() {
    assertEquals(true, FloatUtils.toBoolean(null, true));
    assertEquals(false, FloatUtils.toBoolean(null, false));

    for (final float x : AREA) {
      assertEquals(x != 0, FloatUtils.toBoolean(x, true));
      assertEquals(x != 0, FloatUtils.toBoolean(x, false));
    }
  }

  @Test
  public void testToBooleanObject_float() {
    for (final float element : AREA) {
      final Boolean x = (element != 0);
      assertEquals(x, FloatUtils.toBooleanObject(element));
    }
  }

  @Test
  public void testToBooleanObject_Float() {
    assertEquals(null, FloatUtils.toBooleanObject(null));

    for (final float x : AREA) {
      final Boolean y = (x != 0);
      assertEquals(y, FloatUtils.toBooleanObject(x));
    }
  }

  @Test
  public void testToBooleanObject_Float_Boolean() {
    assertEquals(null, FloatUtils.toBooleanObject(null, null));

    final Boolean a = true;
    final Boolean b = false;
    assertEquals(true, FloatUtils.toBooleanObject(null, a));
    assertEquals(false, FloatUtils.toBooleanObject(null, b));

    for (final float x : AREA) {
      final Boolean y = (x != 0);
      assertEquals(y, FloatUtils.toBooleanObject(x, a));
      assertEquals(y, FloatUtils.toBooleanObject(x, b));
      assertEquals(y, FloatUtils.toBooleanObject(x, null));
    }
  }

  @Test
  public void testToChar_float() {
    for (final float element : AREA) {
      assertEquals((char) element, FloatUtils.toChar(element));
    }
  }

  @Test
  public void testToChar_Float() {
    assertEquals(CharUtils.DEFAULT, FloatUtils.toChar(null));

    for (final float x : AREA) {
      assertEquals((char) x, FloatUtils.toChar(x));
    }
  }

  @Test
  public void testToChar_Float_char() {
    final char[] a = {(char) Integer.MIN_VALUE, (char) Integer.MIN_VALUE/2, (char) 0,
        (char) 1, (char) Integer.MAX_VALUE/2, (char) Integer.MAX_VALUE};

    for (final char element : a) {
      assertEquals(element, FloatUtils.toChar(null, element));
    }

    for (final float x : AREA) {
      for (final char element : a) {
        assertEquals((char) x, FloatUtils.toChar(x, element));
      }
    }
  }

  @Test
  public void testToCharObject_float() {
    for (final float element : AREA) {
      final Character x = (char) element;
      assertEquals(x, FloatUtils.toCharObject(element));
    }
  }

  @Test
  public void testToCharObject_Float() {
    assertEquals(null, FloatUtils.toCharObject(null));

    for (final float x : AREA) {
      final Character y = (char) x;
      assertEquals(y, FloatUtils.toCharObject(x));
    }
  }

  @Test
  public void testToCharObject_Float_Character() {
    final Character[] a = {(char) Integer.MIN_VALUE, (char) Integer.MIN_VALUE/2, (char) 0,
        (char) 1, (char) Integer.MAX_VALUE/2, (char) Integer.MAX_VALUE};

    assertEquals(null, FloatUtils.toCharObject(null, null));

    for (final Character element : a) {
      assertEquals(element, FloatUtils.toCharObject(null, element));
    }

    for (final float x : AREA) {
      final Character y = (char) x;
      assertEquals(y, FloatUtils.toCharObject(x, null));
      for (final Character element : a) {
        assertEquals(y,FloatUtils.toCharObject(x, element));
      }

    }
  }

  @Test
  public void testToByte_float() {
    for (final float element : AREA) {
      assertEquals((byte) element, FloatUtils.toByte(element));
    }
  }

  @Test
  public void testToByte_Float() {
    assertEquals(ByteUtils.DEFAULT, FloatUtils.toByte(null));

    for (final float x : AREA) {
      assertEquals((byte) x, FloatUtils.toByte(x));
    }
  }

  @Test
  public void testToByte_Float_byte() {
    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      assertEquals((byte) i, FloatUtils.toByte(null, (byte) i));
    }

    for (final float x : AREA) {
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        assertEquals((byte) x, FloatUtils.toByte(x, (byte) j));
      }
    }
  }

  @Test
  public void testToByteObject_float() {
    for (final float element : AREA) {
      final Byte x = (byte) element;
      assertEquals(x, FloatUtils.toByteObject(element));
    }
  }

  @Test
  public void testToByteObject_Float() {
    for (final float x : AREA) {
      final Byte y = (byte) x;
      assertEquals(y, FloatUtils.toByteObject(x));
    }
  }

  @Test
  public void testToByteObject_Float_Byte() {
    assertEquals(null, FloatUtils.toByteObject(null, null));

    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final Byte x = (byte) i;
      assertEquals(x, FloatUtils.toByteObject(null, x));
    }

    for (final float x : AREA) {
      final Byte y = (byte) x;
      assertEquals(y, FloatUtils.toByteObject(x, null));
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final Byte z = (byte) j;
        assertEquals(y, FloatUtils.toByteObject(x, z));
      }
    }
  }

  @Test
  public void testToShort_float() {
    for (final float element : AREA) {
      assertEquals((short) element, FloatUtils.toShort(element));
    }
  }

  @Test
  public void testToShort_Float() {
    assertEquals(ShortUtils.DEFAULT, FloatUtils.toShort(null));

    for (final float x : AREA) {
      assertEquals((short) x, FloatUtils.toShort(x));
    }
  }

  @Test
  public void testToShort_Float_short() {
    final short[] a = {Short.MIN_VALUE, Short.MIN_VALUE/2, (short) -1, (short) 0, (short) 1,
        Short.MAX_VALUE/2, Short.MAX_VALUE};

    for (final short element : a) {
      assertEquals(element, FloatUtils.toShort(null, element));
    }

    for (final float x : AREA) {
      for (final short element : a) {
        assertEquals((short) x, FloatUtils.toShort(x, element));
      }
    }
  }

  @Test
  public void testToShortObject_float() {
    for (final float element : AREA) {
      final Short x = (short) element;
      assertEquals(x, FloatUtils.toShortObject(element));
    }
  }

  @Test
  public void testToShortObject_Float() {
    assertEquals(null, FloatUtils.toShortObject(null));

    for (final float x : AREA) {
      final Short y = (short) x;
      assertEquals(y, FloatUtils.toShortObject(x));
    }
  }

  @Test
  public void testToShortObject_Float_Short() {
    final Short[] a = {Short.MIN_VALUE, Short.MIN_VALUE/2, (short) -1, (short) 0, (short) 1,
        Short.MAX_VALUE/2, Short.MAX_VALUE};

    assertEquals(null, FloatUtils.toShortObject(null, null));

    for (final Short element : a) {
      assertEquals(element, FloatUtils.toShortObject(null, element));
    }

    for (final float x : AREA) {
      final Short y = (short) x;
      assertEquals(y, FloatUtils.toShortObject(x, null));
      for (final Short element : a) {
        assertEquals(y, FloatUtils.toShortObject(x, element));
      }
    }
  }

  @Test
  public void testToInt_float() {
    for (final float element : AREA) {
      assertEquals((int) element, FloatUtils.toInt(element));
    }
  }

  @Test
  public void testToInt_Float() {
    assertEquals(IntUtils.DEFAULT, FloatUtils.toInt(null));

    for (final float x : AREA) {
      assertEquals((int) x, FloatUtils.toInt(x));
    }
  }

  @Test
  public void testToInt_Float_int() {
    final int[] a = {Integer.MIN_VALUE, Integer.MIN_VALUE/2, -1, 0, 1,
        Integer.MAX_VALUE/2, Integer.MAX_VALUE};

    for (final int element : a) {
      assertEquals(element, FloatUtils.toInt(null, element));
    }

    for (final float x : AREA) {
      for (final int element : a) {
        assertEquals((int) x, FloatUtils.toInt(x, element));
      }
    }
  }

  @Test
  public void testToIntObject_float() {
    for (final float element : AREA) {
      final Integer x = (int) element;
      assertEquals(x, FloatUtils.toIntObject(element));
    }
  }

  @Test
  public void testToIntObject_Float() {
    assertEquals(null, FloatUtils.toIntObject(null));

    for (final float x : AREA) {
      final Integer y = (int) x;
      assertEquals(y, FloatUtils.toIntObject(x));
    }
  }

  @Test
  public void testToIntObject_Float_Integer() {
    final Integer[] a = {Integer.MIN_VALUE, Integer.MIN_VALUE/2, -1, 0, 1,
        Integer.MAX_VALUE/2, Integer.MAX_VALUE};

    assertEquals(null, FloatUtils.toIntObject(null, null));

    for (final Integer element : a) {
      assertEquals(element, FloatUtils.toIntObject(null, element));
    }

    for ( int i = 0; i < AREA.length; ++i) {
      final Float x = AREA[i];
      final Integer y = (int) AREA[i];
      assertEquals(y, FloatUtils.toIntObject(x, null));
      for (final Integer element : a) {
        assertEquals(y, FloatUtils.toIntObject(x, a[i]));
      }
    }
  }

  @Test
  public void testToLong_float() {
    for (final float element : AREA) {
      assertEquals((long) element, FloatUtils.toLong(element));
    }
  }

  @Test
  public void testToLong_Float() {
    assertEquals(LongUtils.DEFAULT, FloatUtils.toLong(null));

    for (final float x : AREA) {
      assertEquals((long) x, FloatUtils.toLong(x));
    }
  }

  @Test
  public void testToLong_Float_long() {
    final long[] a = {Long.MIN_VALUE, Long.MIN_VALUE/2, -1, 0, 1,
        Long.MAX_VALUE/2, Long.MAX_VALUE};

    for (final long element : a) {
      assertEquals(element, FloatUtils.toLong(null, element));
    }

    for (final float x : AREA) {
      for (final long element : a) {
        assertEquals((long) x, FloatUtils.toLong(x, element));
      }
    }
  }

  @Test
  public void testToLongObject_float() {
    for (final float element : AREA) {
      final Long x = (long) element;
      assertEquals(x, FloatUtils.toLongObject(element));
    }
  }

  @Test
  public void testToLongObject_Float() {
    assertEquals(null, FloatUtils.toLongObject(null));

    for (final float x : AREA) {
      final Long y = (long) x;
      assertEquals(y, FloatUtils.toLongObject(x));
    }
  }

  @Test
  public void testToLongObject_Float_Long() {
    final Long[] a = {Long.MIN_VALUE, Long.MIN_VALUE/2, (long) -1, (long) 0, (long) 1,
        Long.MAX_VALUE/2, Long.MAX_VALUE};

    assertEquals(null, FloatUtils.toLongObject(null, null));

    for (final Long element : a) {
      assertEquals(element, FloatUtils.toLongObject(null, element));
    }

    for (final float x : AREA) {
      final Long y = (long) x;
      assertEquals(y, FloatUtils.toLongObject(x, null));
      for (final Long element : a) {
        assertEquals(y, FloatUtils.toLongObject(x, element));
      }
    }
  }

  @Test
  public void testToDouble_float() {
    for (final float element : AREA) {
      assertEquals(element, FloatUtils.toDouble(element), DoubleUtils.EPSILON);
    }
  }

  @Test
  public void testToDouble_Float() {
    assertEquals(DoubleUtils.DEFAULT, FloatUtils.toDouble(null), DoubleUtils.EPSILON);

    for (final float x : AREA) {
      assertEquals(x, FloatUtils.toDouble(x), DoubleUtils.EPSILON);
    }
  }

  @Test
  public void testToDouble_Float_double() {
    final double[] a = {Double.MIN_VALUE, Double.MIN_VALUE/2, -1, 0, 1,
        Double.MAX_VALUE/2, Double.MAX_VALUE};

    for ( int i = 0; i < AREA.length; ++i) {
      assertEquals(a[i], FloatUtils.toDouble(null, a[i]), DoubleUtils.EPSILON);
    }

    for (final float x : AREA) {
      final double y = x;
      for (final double element : a) {
        assertEquals(y, FloatUtils.toDouble(x, element), DoubleUtils.EPSILON);
      }
    }
  }

  @Test
  public void testToDoubleObject_float() {
    for (final float element : AREA) {
      final Double x = (double) element;
      assertEquals(x, FloatUtils.toDoubleObject(element));
    }
  }

  @Test
  public void testToDoubleObject_Float() {
    assertEquals(null, FloatUtils.toDoubleObject(null));

    for (final float x : AREA) {
      final Double y = (double) x;
      assertEquals(y, FloatUtils.toDoubleObject(x));
    }
  }

  @Test
  public void testToDoubleObject_Float_Double() {
    final Double[] a = {Double.MIN_VALUE, Double.MIN_VALUE/2, (double) -1, (double) 0, (double) 1,
        Double.MAX_VALUE/2, Double.MAX_VALUE};

    assertEquals(null, FloatUtils.toDoubleObject(null, null));

    for (final Double element : a) {
      assertEquals(element, FloatUtils.toDoubleObject(null, element));
    }

    for (final float x : AREA) {
      final Double y = (double) x;
      assertEquals(y, FloatUtils.toDoubleObject(x, null));
      for (final Double element : a) {
        assertEquals(y, FloatUtils.toDoubleObject(x, element));
      }
    }
  }

  @Test
  public void testToString_float() {
    for (final float element : AREA) {
      final String x = Float.toString(element);
      assertEquals(x, FloatUtils.toString(element));
    }
  }

  @Test
  public void testToString_Float() {
    assertEquals(null, FloatUtils.toString(null));

    for (final float x : AREA) {
      final String y = Float.toString(x);
      assertEquals(y, FloatUtils.toString(x));
    }
  }

  @Test
  public void testToString_Float_String() {
    Float value1 = null;
    String value2 = null;

    assertEquals(null, FloatUtils.toString(value1, value2));

    value2 = "helloworld";
    assertEquals("helloworld", FloatUtils.toString(value1, value2));

    value1 = 0.005f;
    value2 = null;
    // there is a bug through JDK 1.3 to 1.6, and is resolved in JDK 1.7
    // see http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4511638
    if (SystemUtils.IS_JAVA_1_7) {
      assertEquals("0.005", FloatUtils.toString(value1, value2));
    } else {
      assertEquals("0.0050", FloatUtils.toString(value1, value2));
    }

    value1 = 0.005f;
    value2 = "helloworld";
    if (SystemUtils.IS_JAVA_1_7) {
      assertEquals("0.005", FloatUtils.toString(value1, value2));
    } else {
      assertEquals("0.0050", FloatUtils.toString(value1, value2));
    }
  }

  @Test
  public void testToDate_float() {
    for (final float element : AREA) {
      final Date x = new Date((long) element);
      assertEquals(x, FloatUtils.toDate(element));
    }
  }

  @Test
  public void testToDate_Float() {
    assertEquals(null, FloatUtils.toDate(null));

    for (final float x : AREA) {
      final Date y = new Date((long) x);
      assertEquals(y, FloatUtils.toDate(x));
    }
  }

  @Test
  public void testToDate_Float_Date() {
    final Date[] a = {new Date(Long.MIN_VALUE), new Date(Long.MIN_VALUE/2), new Date(-1),
        new Date(0), new Date(1),new Date(Long.MAX_VALUE/2), new Date(Long.MAX_VALUE)};

    assertEquals(null, FloatUtils.toDate(null,null));

    for (final Date element : a) {
      assertEquals(element, FloatUtils.toDate(null, element));
    }

    for (final float x : AREA) {
      final Date y = new Date((long) x);
      assertEquals(y, FloatUtils.toDate(x,null));
      for (final Date element : a) {
        assertEquals(y, FloatUtils.toDate(x, element));
      }
    }
  }

  @Test
  public void testToByteArray_float() {
    for (final float element : AREA) {
      assertArrayEquals(IntUtils.toByteArray(Float.floatToIntBits(element),SystemUtils.BYTE_ORDER),
          FloatUtils.toByteArray(element));
    }
  }

  @Test
  public void testToByteArray_float_ByteOrder() {
    for (final float element : AREA) {
      assertArrayEquals(IntUtils.toByteArray(Float.floatToIntBits(element),ByteOrder.BIG_ENDIAN),
          FloatUtils.toByteArray(element, ByteOrder.BIG_ENDIAN));
      assertArrayEquals(IntUtils.toByteArray(Float.floatToIntBits(element),ByteOrder.LITTLE_ENDIAN),
          FloatUtils.toByteArray(element, ByteOrder.LITTLE_ENDIAN));
    }
  }

  @Test
  public void testToByteArray_Float() {
    for (final float x : AREA) {
      assertArrayEquals(IntUtils.toByteArray(Float.floatToIntBits(x),SystemUtils.BYTE_ORDER),
          FloatUtils.toByteArray(x));
    }
  }

  @Test
  public void testToByteArray_Float_ByteOrder() {
    for (final float x : AREA) {
      assertArrayEquals(IntUtils.toByteArray(Float.floatToIntBits(x),ByteOrder.BIG_ENDIAN),
          FloatUtils.toByteArray(x, ByteOrder.BIG_ENDIAN));
      assertArrayEquals(IntUtils.toByteArray(Float.floatToIntBits(x),ByteOrder.LITTLE_ENDIAN),
          FloatUtils.toByteArray(x, ByteOrder.LITTLE_ENDIAN));
    }
  }

  @Test
  public void testToByteArray_Float_ByteArray() {
    final byte[] a = null;
    assertEquals(a, FloatUtils.toByteArray(null, a));

    for (final float x : AREA) {
      final byte[] y = {};
      assertArrayEquals(IntUtils.toByteArray(Float.floatToIntBits(x),SystemUtils.BYTE_ORDER),
          FloatUtils.toByteArray(x, y));
    }
  }

  @Test
  public void testToByteArray_Float_ByteArray_ByteOrder() {
    for (final float x : AREA) {
      final byte[] y = {};
      assertArrayEquals(IntUtils.toByteArray(Float.floatToIntBits(x),ByteOrder.BIG_ENDIAN),
          FloatUtils.toByteArray(x, y, ByteOrder.BIG_ENDIAN));
      assertArrayEquals(IntUtils.toByteArray(Float.floatToIntBits(x),ByteOrder.LITTLE_ENDIAN),
          FloatUtils.toByteArray(x, y, ByteOrder.LITTLE_ENDIAN));
    }
  }

  @Test
  public void testToClass_float() {
    for (final float element : AREA) {
      assertSame(Float.TYPE, FloatUtils.toClass(element));
    }
  }

  @Test
  public void testToClass_Float() {
    for (final Float x : AREA) {
      assertSame(Float.class, FloatUtils.toClass(x));
    }
  }

  @Test
  public void testToClass_Float_ClassOfQ() {
    assertEquals(null, FloatUtils.toClass(null, null));

    assertSame(Float.class, FloatUtils.toClass(null, Float.class));

    for (final float x : AREA) {
      assertSame(Float.class, FloatUtils.toClass(x, null));
      assertSame(Float.class, FloatUtils.toClass(x, Integer.class));
    }
  }

  @Test
  public void testToBigInteger_float() {
    for (final float element : AREA) {
      final BigInteger x = BigInteger.valueOf((long) element);
      assertEquals(x, FloatUtils.toBigInteger(element));
    }
  }

  @Test
  public void testToBigInteger_Float() {
    assertEquals(null, FloatUtils.toBigInteger(null));

    for (final float x : AREA) {
      final BigInteger y = BigInteger.valueOf((long) x);
      assertEquals(y, FloatUtils.toBigInteger(x));
    }
  }

  @Test
  public void testToBigInteger_Float_BigInteger() {
    assertEquals(null, FloatUtils.toBigInteger(null, null));

    for (final float element : AREA) {
      final BigInteger x = BigInteger.valueOf((long) element);
      assertEquals(x, FloatUtils.toBigInteger(null, x));
    }

    for (final float x : AREA) {
      final BigInteger y = BigInteger.valueOf((long) x);
      assertEquals(y, FloatUtils.toBigInteger(x, null));
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final BigInteger z = BigInteger.valueOf(j);
        assertEquals(y, FloatUtils.toBigInteger(x, z));
      }
    }
  }

  @Test
  public void testToBigDecimal_float() {
    for (final float element : AREA) {
      final BigDecimal x = BigDecimal.valueOf(element);
      assertEquals(x, FloatUtils.toBigDecimal(element));
    }
  }

  @Test
  public void testToBigDecimal_Float() {
    assertEquals(null, FloatUtils.toBigDecimal(null));

    for (final float x : AREA) {
      final BigDecimal y = BigDecimal.valueOf(x);
      assertEquals(y, FloatUtils.toBigDecimal(x));
    }
  }

  @Test
  public void testToBigDecimalFloatBigDecimal() {
    assertEquals(null, FloatUtils.toBigDecimal(null, null));

    for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final BigDecimal x = BigDecimal.valueOf(i);
      assertEquals(x, FloatUtils.toBigDecimal(null, x));
    }

    for (final float x : AREA) {
      final BigDecimal y = BigDecimal.valueOf(x);
      assertEquals(y, FloatUtils.toBigDecimal(x, null));
      for ( int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final BigDecimal z = BigDecimal.valueOf(j);
        assertEquals(y, FloatUtils.toBigDecimal(x, z));
      }
    }
  }

}
