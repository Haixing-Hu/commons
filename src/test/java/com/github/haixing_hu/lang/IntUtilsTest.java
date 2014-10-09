package com.github.haixing_hu.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteOrder;
import java.util.Date;

import org.junit.Test;

import static org.junit.Assert.*;

public class IntUtilsTest {

  private static final int[] AREA = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2,
      - 1, 0, 1, Short.MAX_VALUE / 3, Short.MAX_VALUE / 4 };

  @Test
  public void testToPrimitive_Integer() {
    assertEquals(IntUtils.DEFAULT, IntUtils.toPrimitive(null));
    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      assertEquals(AREA[i], IntUtils.toPrimitive(x));
    }
  }

  @Test
  public void testToPrimitive_Integer_int() {
    for (int i = 0; i < AREA.length; ++i) {
      assertEquals(AREA[i], IntUtils.toPrimitive(null, AREA[i]));
    }

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      for (int j = 0; j < AREA.length; ++j) {
        assertEquals(AREA[i], IntUtils.toPrimitive(x, AREA[j]));
      }
    }
  }

  @Test
  public void testToBoolean_int() {
    for (int i = 0; i < AREA.length; ++i) {
      assertEquals(AREA[i] != 0, IntUtils.toBoolean(AREA[i]));
    }
  }

  @Test
  public void testToBoolean_Integer() {
    assertEquals(BooleanUtils.DEFAULT, IntUtils.toBoolean(null));

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      assertEquals(AREA[i] != 0, IntUtils.toBoolean(x));
    }
  }

  @Test
  public void testToBoolean_Integer_boolean() {
    assertEquals(true, IntUtils.toBoolean(null, true));
    assertEquals(false, IntUtils.toBoolean(null, false));

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      assertEquals(AREA[i] != 0, IntUtils.toBoolean(x, true));
      assertEquals(AREA[i] != 0, IntUtils.toBoolean(x, false));
    }
  }

  @Test
  public void testToBooleanObject_int() {
    for (int i = 0; i < AREA.length; ++i) {
      final Boolean x = (AREA[i] != 0);
      assertEquals(x, IntUtils.toBooleanObject(AREA[i]));
    }
  }

  @Test
  public void testToBooleanObject_Integer() {
    assertEquals(null, IntUtils.toBooleanObject(null));

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      final Boolean y = (AREA[i] != 0);
      assertEquals(y, IntUtils.toBooleanObject(x));
    }
  }

  @Test
  public void testToBooleanObject_Integer_Boolean() {
    assertEquals(null, IntUtils.toBooleanObject(null, null));

    final Boolean x = true;
    final Boolean y = false;
    assertEquals(true, IntUtils.toBooleanObject(null, x));
    assertEquals(false, IntUtils.toBooleanObject(null, y));

    for (int i = 0; i < AREA.length; ++i) {
      final Integer z = AREA[i];
      final Boolean a = (AREA[i] != 0);
      assertEquals(a, IntUtils.toBooleanObject(z, null));
      assertEquals(a, IntUtils.toBooleanObject(z, x));
      assertEquals(a, IntUtils.toBooleanObject(z, y));
    }
  }

  @Test
  public void testToChar_int() {
    for (int i = 0; i < AREA.length; ++i) {
      assertEquals((char) AREA[i], IntUtils.toChar(AREA[i]));
    }
  }

  @Test
  public void testToChar_Integer() {
    assertEquals(CharUtils.DEFAULT, IntUtils.toChar(null));

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      assertEquals((char) AREA[i], IntUtils.toChar(x));
    }
  }

  @Test
  public void testToChar_Integer_char() {
    for (int i = 0; i < AREA.length; ++i) {
      assertEquals((char) AREA[i], IntUtils.toChar(null, (char) AREA[i]));
    }

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      for (int j = 0; j < AREA.length; ++j) {
        assertEquals((char) AREA[i], IntUtils.toChar(x, (char) AREA[j]));
      }
    }
  }

  @Test
  public void testToCharObject_int() {
    for (int i = 0; i < AREA.length; ++i) {
      final Character x = (char) AREA[i];
      assertEquals(x, IntUtils.toCharObject(AREA[i]));
    }
  }

  @Test
  public void testToCharObject_Integer() {
    assertEquals(null, IntUtils.toCharObject(null));

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      final Character y = (char) AREA[i];
      assertEquals(y, IntUtils.toCharObject(x));
    }
  }

  @Test
  public void testToCharObject_Integer_Character() {
    assertEquals(null, IntUtils.toCharObject(null, null));

    for (int i = 0; i < AREA.length; ++i) {
      final Character x = (char) AREA[i];
      assertEquals(x, IntUtils.toCharObject(null, x));
    }

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      final Character y = (char) AREA[i];
      assertEquals(y, IntUtils.toCharObject(x, null));
      for (int j = 0; j < AREA.length; ++j) {
        final Character z = (char) AREA[j];
        assertEquals(y, IntUtils.toCharObject(x, z));
      }
    }
  }

  @Test
  public void testToByte_int() {
    for (int i = 0; i < AREA.length; ++i) {
      assertEquals((byte) AREA[i], IntUtils.toByte(AREA[i]));
    }
  }

  @Test
  public void testToByte_Integer() {
    assertEquals(ByteUtils.DEFAULT, IntUtils.toByte(null));

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      assertEquals((byte) AREA[i], IntUtils.toByte(x));
    }
  }

  @Test
  public void testToByte_Integer_byte() {
    final byte[] a = { Byte.MIN_VALUE, Byte.MIN_VALUE / 2, (byte) - 1,
        (byte) 0, (byte) 1, Byte.MAX_VALUE / 2, Byte.MAX_VALUE };

    for (int i = 0; i < a.length; ++i) {
      assertEquals(a[i], IntUtils.toByte(null, a[i]));
    }

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      for (int j = 0; j < a.length; ++j) {
        assertEquals((byte) AREA[i], IntUtils.toByte(x, a[j]));
      }
    }
  }

  @Test
  public void testToByteObject_int() {
    for (int i = 0; i < AREA.length; ++i) {
      final Byte x = (byte) AREA[i];
      assertEquals(x, IntUtils.toByteObject(AREA[i]));
    }
  }

  @Test
  public void testToByteObject_Integer() {
    assertEquals(null, IntUtils.toByteObject(null));

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      final Byte y = (byte) AREA[i];
      assertEquals(y, IntUtils.toByteObject(x));
    }
  }

  @Test
  public void testToByteObject_Integer_Byte() {
    final byte[] a = { Byte.MIN_VALUE, Byte.MIN_VALUE / 2, (byte) - 1,
        (byte) 0, (byte) 1, Byte.MAX_VALUE / 2, Byte.MAX_VALUE };

    assertEquals(null, IntUtils.toByteObject(null, null));

    for (int i = 0; i < a.length; ++i) {
      final Byte x = a[i];
      assertEquals(x, IntUtils.toByteObject(null, x));
    }

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      final Byte y = (byte) AREA[i];
      assertEquals(y, IntUtils.toByteObject(x, null));
      for (int j = 0; j < a.length; ++j) {
        final Byte z = a[j];
        assertEquals(y, IntUtils.toByteObject(x, z));
      }
    }
  }

  @Test
  public void testToShort_int() {
    for (int i = 0; i < AREA.length; ++i) {
      assertEquals((short) AREA[i], IntUtils.toShort(AREA[i]));
    }
  }

  @Test
  public void testToShort_Integer() {
    assertEquals(IntUtils.DEFAULT, IntUtils.toShort(null));

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      assertEquals((short) AREA[i], IntUtils.toShort(x));
    }
  }

  @Test
  public void testToShort_Integer_short() {
    final short[] a = { Short.MIN_VALUE, Short.MIN_VALUE / 2, (short) - 1,
        (short) 0, (short) 1, Short.MAX_VALUE / 2, Short.MAX_VALUE };

    for (int i = 0; i < a.length; ++i) {
      assertEquals(a[i], IntUtils.toShort(null, a[i]));
    }

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      for (int j = 0; j < a.length; ++j) {
        assertEquals((short) AREA[i], IntUtils.toShort(x, a[j]));
      }
    }
  }

  @Test
  public void testToShortObject_int() {
    for (int i = 0; i < AREA.length; ++i) {
      final Short x = (short) AREA[i];
      assertEquals(x, IntUtils.toShortObject(AREA[i]));
    }
  }

  @Test
  public void testToShortObject_Integer() {
    assertEquals(null, IntUtils.toShortObject(null));

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      final Short y = (short) AREA[i];
      assertEquals(y, IntUtils.toShortObject(x));
    }
  }

  @Test
  public void testToShortObject_Integer_Short() {
    final Short[] a = { Short.MIN_VALUE, Short.MIN_VALUE / 2, (short) - 1,
        (short) 0, (short) 1, Short.MAX_VALUE / 2, Short.MAX_VALUE };

    assertEquals(null, IntUtils.toShortObject(null, null));

    for (int i = 0; i < a.length; ++i) {
      assertEquals(a[i], IntUtils.toShortObject(null, a[i]));
    }

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      final Short y = (short) AREA[i];
      assertEquals(y, IntUtils.toShortObject(x, null));
      for (int j = 0; j < a.length; ++j) {
        assertEquals(y, IntUtils.toShortObject(x, a[j]));
      }
    }
  }

  @Test
  public void testToLong_int() {
    for (int i = 0; i < AREA.length; ++i) {
      assertEquals(AREA[i], IntUtils.toLong(AREA[i]));
    }
  }

  @Test
  public void testToLong_Integer() {
    assertEquals(LongUtils.DEFAULT, IntUtils.toLong(null));

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      assertEquals(AREA[i], IntUtils.toLong(x));
    }
  }

  @Test
  public void testToLong_Integer_long() {
    final long[] a = { Long.MIN_VALUE, Long.MIN_VALUE / 2, - 1,
        0, 1, Long.MAX_VALUE / 2, Long.MAX_VALUE };

    for (int i = 0; i < a.length; ++i) {
      assertEquals(a[i], IntUtils.toLong(null, a[i]));
    }

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      for (int j = 0; j < a.length; ++j) {
        assertEquals(AREA[i], IntUtils.toLong(x, a[j]));
      }
    }
  }

  @Test
  public void testToLongObject_int() {
    for (int i = 0; i < AREA.length; ++i) {
      final Long x = (long) AREA[i];
      assertEquals(x, IntUtils.toLongObject(AREA[i]));
    }
  }

  @Test
  public void testToLongObject_Integer() {
    assertEquals(null, IntUtils.toLongObject(null));

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      final Long y = (long) AREA[i];
      assertEquals(y, IntUtils.toLongObject(x));
    }
  }

  @Test
  public void testToLongObject_Integer_Long() {
    final Long[] a = { Long.MIN_VALUE, Long.MIN_VALUE / 2, (long) - 1,
        (long) 0, (long) 1, Long.MAX_VALUE / 2, Long.MAX_VALUE };

    assertEquals(null, IntUtils.toLongObject(null, null));

    for (int i = 0; i < AREA.length; ++i) {
      final Long x = (long) AREA[i];
      assertEquals(x, IntUtils.toLongObject(null, x));
    }

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      final Long y = (long) AREA[i];
      assertEquals(y, IntUtils.toLongObject(x, null));
      for (int j = 0; j < a.length; ++j) {
        assertEquals(y, IntUtils.toLongObject(x, a[j]));
      }
    }
  }

  @Test
  public void testToFloat_int() {


    for (int i = 0; i < AREA.length; ++i) {
      assertEquals(AREA[i], IntUtils.toFloat(AREA[i]), FloatUtils.EPSILON);
    }
  }

  @Test
  public void testToFloat_Integer() {
    assertEquals(FloatUtils.DEFAULT, IntUtils.toFloat(null), FloatUtils.EPSILON);

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      assertEquals(AREA[i], IntUtils.toFloat(x), FloatUtils.EPSILON);
    }
  }

  @Test
  public void testToFloat_Integer_float() {
    final float[] x = { Float.MIN_VALUE, Float.MIN_VALUE / 2, - 1,
        0, 1, Float.MAX_VALUE, Float.MAX_VALUE };

    for (int i = 0; i < x.length; ++i) {
      assertEquals(x[i], IntUtils.toFloat(null, x[i]), FloatUtils.EPSILON);
    }

    for (int i = 0; i < AREA.length; ++i) {
      final Integer y = AREA[i];
      for (int j = 0; j < x.length; ++j) {
        assertEquals(AREA[i], IntUtils.toFloat(y, x[j]),
            FloatUtils.EPSILON);
      }
    }
  }

  @Test
  public void testToFloatObject_int() {
    for (int i = 0; i < AREA.length; ++i) {
      final Float x = (float) AREA[i];
      assertEquals(x, IntUtils.toFloatObject(AREA[i]));
    }
  }

  @Test
  public void testToFloatObject_Integer() {
    assertEquals(null, IntUtils.toFloatObject(null));

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      final Float y = (float) AREA[i];
      assertEquals(y, IntUtils.toFloatObject(x));
    }
  }

  @Test
  public void testToFloatObject_Integer_Float() {
    final Float[] a = { Float.MIN_VALUE, Float.MIN_VALUE / 2, (float) - 1,
        (float) 0, (float) 1, Float.MAX_VALUE, Float.MAX_VALUE };

    assertEquals(null, IntUtils.toFloatObject(null, null));

    for (int i = 0; i < a.length; ++i) {
      assertEquals(a[i], IntUtils.toFloatObject(null, a[i]));
    }

    for (int i = 0; i < AREA.length; ++i) {
      final Integer y = AREA[i];
      final Float z = (float) AREA[i];
      for (int j = 0; j < a.length; ++j) {
        assertEquals(z, IntUtils.toFloatObject(y, a[j]));
      }
    }
  }

  @Test
  public void testToDouble_int() {
    for (int i = 0; i < AREA.length; ++i) {
      assertEquals(AREA[i], IntUtils.toDouble(AREA[i]),
          DoubleUtils.EPSILON);
    }
  }

  @Test
  public void testToDouble_Integer() {
    assertEquals(DoubleUtils.DEFAULT, IntUtils.toDouble(null),
        DoubleUtils.EPSILON);

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      assertEquals(AREA[i], IntUtils.toDouble(x), DoubleUtils.EPSILON);
    }
  }

  @Test
  public void testToDouble_Integer_double() {
    final double[] a = { Double.MIN_VALUE, Double.MIN_VALUE / 2, - 1,
        0, 1, Double.MAX_VALUE, Double.MAX_VALUE };

    for (int i = 0; i < a.length; ++i) {
      assertEquals(a[i], IntUtils.toDouble(null, a[i]), DoubleUtils.EPSILON);
    }

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      for (int j = 0; j < a.length; ++j) {
        assertEquals(AREA[i], IntUtils.toDouble(x, a[j]),
            DoubleUtils.EPSILON);
      }
    }
  }

  @Test
  public void testToDoubleObject_int() {
    for (int i = 0; i < AREA.length; ++i) {
      final Double x = (double) AREA[i];
      assertEquals(x, IntUtils.toDoubleObject(AREA[i]));
    }
  }

  @Test
  public void testToDoubleObject_Integer() {
    assertEquals(null, IntUtils.toDoubleObject(null));

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      final Double y = (double) AREA[i];
      assertEquals(y, IntUtils.toDoubleObject(x));
    }
  }

  @Test
  public void testToDoubleObject_Integer_Double() {
    final Double[] a = { Double.MIN_VALUE, Double.MIN_VALUE / 2, (double) - 1,
        (double) 0, (double) 1, Double.MAX_VALUE, Double.MAX_VALUE };

    assertEquals(null, IntUtils.toDoubleObject(null, null));

    for (int i = 0; i < a.length; ++i) {
      assertEquals(a[i], IntUtils.toDoubleObject(null, a[i]));
    }

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      final Double y = (double) AREA[i];
      assertEquals(y, IntUtils.toDoubleObject(x, null));
      for (int j = 0; j < a.length; ++j) {
        assertEquals(y, IntUtils.toDoubleObject(x, a[j]));
      }
    }
  }

  @Test
  public void testToString_int() {
    for (int i = 0; i < AREA.length; ++i) {
      final String x = Integer.toString(AREA[i]);
      assertEquals(x, IntUtils.toString(AREA[i]));
    }
  }

  @Test
  public void testToString_Integer() {
    assertEquals(null, IntUtils.toString(null));

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      final String y = Integer.toString(AREA[i]);
      assertEquals(y, IntUtils.toString(x));
    }
  }

  @Test
  public void testToString_Integer_String() {
    assertEquals(null, IntUtils.toString(null, null));

    for (int i = 0; i < AREA.length; ++i) {
      final String x = Integer.toString(AREA[i]);
      assertEquals(x, IntUtils.toString(null, x));
    }

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      final String y = Integer.toString(AREA[i]);
      assertEquals(y, IntUtils.toString(x, null));
      for (int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final String z = Integer.toString(j);
        assertEquals(y, IntUtils.toString(x, z));
      }
    }
  }

  @Test
  public void testToHexString_int_StringBuilder() {
    final StringBuilder builder = new StringBuilder();

    builder.setLength(0);
    IntUtils.toHexString(0x00000000, builder);
    assertEquals("0x00000000", builder.toString());

    builder.setLength(0);
    IntUtils.toHexString(0x0000000A, builder);
    assertEquals("0x0000000A", builder.toString());

    builder.setLength(0);
    IntUtils.toHexString(0x0000AAAA, builder);
    assertEquals("0x0000AAAA", builder.toString());

    builder.setLength(0);
    IntUtils.toHexString(0x0000FFFF, builder);
    assertEquals("0x0000FFFF", builder.toString());

    builder.setLength(0);
    IntUtils.toHexString(0xAAAAAAAA, builder);
    assertEquals("0xAAAAAAAA", builder.toString());

    builder.setLength(0);
    IntUtils.toHexString(0xFFFFFFFF, builder);
    assertEquals("0xFFFFFFFF", builder.toString());
  }

  @Test
  public void testToHexString_int() {
    assertEquals("0x00000000", IntUtils.toHexString(0x00000000));
    assertEquals("0x0000000A", IntUtils.toHexString(0x0000000A));
    assertEquals("0x0000AAAA", IntUtils.toHexString(0x0000AAAA));
    assertEquals("0x0000FFFF", IntUtils.toHexString(0x0000FFFF));
    assertEquals("0xAAAAAAAA", IntUtils.toHexString(0xAAAAAAAA));
    assertEquals("0xFFFFFFFF", IntUtils.toHexString(0xFFFFFFFF));
  }

  @Test
  public void testToDate_int() {
    for (int i = 0; i < AREA.length; ++i) {
      final Date x = new Date(AREA[i] * 1L);
      assertEquals(x, IntUtils.toDate(AREA[i]));
    }
  }

  @Test
  public void testToDate_Integer() {
    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      final Date y = new Date(AREA[i] * 1L);
      assertEquals(y, IntUtils.toDate(x));
    }
  }

  @Test
  public void testToDate_Integer_Date() {
    final long[] a = { Long.MIN_VALUE, Long.MIN_VALUE / 2, - 1,
        0, 1, Long.MAX_VALUE / 2, Long.MAX_VALUE };

    assertEquals(null, IntUtils.toDate(null, null));

    for (int i = 0; i < a.length; ++i) {
      final Date x = new Date(a[i]);
      assertEquals(x, IntUtils.toDate(null, x));
    }

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      final Date y = new Date(AREA[i] * 1L);
      assertEquals(y, IntUtils.toDate(x, null));
      for (int j = 0; j < a.length; ++j) {
        final Date z = new Date(a[j]);
        assertEquals(y, IntUtils.toDate(x, z));
      }
    }
  }

  @Test
  public void testToByteArray_int() {
    final byte[] xa = { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
    assertArrayEquals(xa, IntUtils.toByteArray(0x00000000));

    final byte[] xb = { (byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00 };
    assertArrayEquals(xb, IntUtils.toByteArray(0xAB00AB00));

    final byte[] xc = { (byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB };
    assertArrayEquals(xc, IntUtils.toByteArray(0xABABABAB));

    final byte[] xd = { (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00 };
    assertArrayEquals(xd, IntUtils.toByteArray(0xFF00FF00));

    final byte[] xe = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
    assertArrayEquals(xe, IntUtils.toByteArray(0xFFFFFFFF));

    final byte[] xf = { (byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA };
    assertArrayEquals(xf, IntUtils.toByteArray(0xBBAABBAA));
  }

  @Test
  public void testToByteArray_int_ByteOrder() {
    final byte[] xa = { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
    assertArrayEquals(xa,
        IntUtils.toByteArray(0x00000000, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xa,
        IntUtils.toByteArray(0x00000000, ByteOrder.BIG_ENDIAN));

    final byte[] xb = { (byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00 };
    assertArrayEquals(xb,
        IntUtils.toByteArray(0x00AB00AB, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xb,
        IntUtils.toByteArray(0xAB00AB00, ByteOrder.BIG_ENDIAN));

    final byte[] xc = { (byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB };
    assertArrayEquals(xc,
        IntUtils.toByteArray(0xABABABAB, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xc,
        IntUtils.toByteArray(0xABABABAB, ByteOrder.BIG_ENDIAN));

    final byte[] xd = { (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00 };
    assertArrayEquals(xd,
        IntUtils.toByteArray(0x00FF00FF, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xd,
        IntUtils.toByteArray(0xFF00FF00, ByteOrder.BIG_ENDIAN));

    final byte[] xe = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
    assertArrayEquals(xe,
        IntUtils.toByteArray(0xFFFFFFFF, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xe,
        IntUtils.toByteArray(0xFFFFFFFF, ByteOrder.BIG_ENDIAN));

    final byte[] xf = { (byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA };
    assertArrayEquals(xf,
        IntUtils.toByteArray(0xAABBAABB, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xf,
        IntUtils.toByteArray(0xBBAABBAA, ByteOrder.BIG_ENDIAN));

    try {
      IntUtils.toByteArray(10, null);
      fail("should throw");
    } catch (final UnsupportedByteOrderException e) {
      // pass
    }
  }

  @Test
  public void testToByteArray_Integer() {
    assertEquals(null, IntUtils.toByteArray(null));

    final byte[] xa = { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
    final Integer ya = 0x00000000;
    assertArrayEquals(xa, IntUtils.toByteArray(ya));

    final byte[] xb = { (byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00 };
    final Integer yb = 0xAB00AB00;
    assertArrayEquals(xb, IntUtils.toByteArray(yb));

    final byte[] xc = { (byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB };
    final Integer yc = 0xABABABAB;
    assertArrayEquals(xc, IntUtils.toByteArray(yc));

    final byte[] xd = { (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00 };
    final Integer yd = 0xFF00FF00;
    assertArrayEquals(xd, IntUtils.toByteArray(yd));

    final byte[] xe = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
    final Integer ye = 0xFFFFFFFF;
    assertArrayEquals(xe, IntUtils.toByteArray(ye));

    final byte[] xf = { (byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA };
    final Integer yf = 0xBBAABBAA;
    assertArrayEquals(xf, IntUtils.toByteArray(yf));
  }

  @Test
  public void testToByteArray_Integer_ByteOrder() {
    assertArrayEquals(null, IntUtils.toByteArray(null, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(null, IntUtils.toByteArray(null, ByteOrder.LITTLE_ENDIAN));

    final byte[] xa = { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
    final Integer ya = 0x00000000;
    assertArrayEquals(xa, IntUtils.toByteArray(ya, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xa, IntUtils.toByteArray(ya, ByteOrder.BIG_ENDIAN));

    final byte[] xb = { (byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00 };
    final Integer yb1 = 0x00AB00AB;
    assertArrayEquals(xb, IntUtils.toByteArray(yb1, ByteOrder.LITTLE_ENDIAN));
    final Integer yb2 = 0xAB00AB00;
    assertArrayEquals(xb, IntUtils.toByteArray(yb2, ByteOrder.BIG_ENDIAN));

    final byte[] xc = { (byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB };
    final Integer yc = 0xABABABAB;
    assertArrayEquals(xc, IntUtils.toByteArray(yc, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xc, IntUtils.toByteArray(yc, ByteOrder.BIG_ENDIAN));

    final byte[] xd = { (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00 };
    final Integer yd1 = 0x00FF00FF;
    assertArrayEquals(xd, IntUtils.toByteArray(yd1, ByteOrder.LITTLE_ENDIAN));
    final Integer yd2 = 0xFF00FF00;
    assertArrayEquals(xd, IntUtils.toByteArray(yd2, ByteOrder.BIG_ENDIAN));

    final byte[] xe = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
    final Integer ye = 0xFFFFFFFF;
    assertArrayEquals(xe, IntUtils.toByteArray(ye, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xe, IntUtils.toByteArray(ye, ByteOrder.BIG_ENDIAN));

    final byte[] xf = { (byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA };
    final Integer yf1 = 0xAABBAABB;
    assertArrayEquals(xf, IntUtils.toByteArray(yf1, ByteOrder.LITTLE_ENDIAN));
    final Integer yf2 = 0xBBAABBAA;
    assertArrayEquals(xf, IntUtils.toByteArray(yf2, ByteOrder.BIG_ENDIAN));
  }

  @Test
  public void testToByteArray_Integer_ByteArray() {
    final byte[] x = null;
    assertEquals(null, IntUtils.toByteArray(null, x));

    final byte[] a = { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
    assertArrayEquals(a, IntUtils.toByteArray(null, a));
    final byte[] b = { (byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00 };
    assertArrayEquals(b, IntUtils.toByteArray(null, b));
    final byte[] c = { (byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB };
    assertArrayEquals(c, IntUtils.toByteArray(null, c));
    final byte[] d = { (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00 };
    assertArrayEquals(d, IntUtils.toByteArray(null, d));
    final byte[] e = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
    assertArrayEquals(e, IntUtils.toByteArray(null, e));
    final byte[] f = { (byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA };
    assertArrayEquals(f, IntUtils.toByteArray(null, f));

    final byte[] xa = { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
    final Integer ya = 0x00000000;
    final byte[] za = {};
    assertArrayEquals(xa, IntUtils.toByteArray(ya, za));
    assertArrayEquals(xa, IntUtils.toByteArray(ya, x));
    final byte[] xb = { (byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00 };
    final Integer yb = 0xAB00AB00;
    final byte[] zb = {};
    assertArrayEquals(xb, IntUtils.toByteArray(yb, zb));
    assertArrayEquals(xb, IntUtils.toByteArray(yb, x));
    final byte[] xc = { (byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB };
    final Integer yc = 0xABABABAB;
    final byte[] zc = {};
    assertArrayEquals(xc, IntUtils.toByteArray(yc, zc));
    assertArrayEquals(xc, IntUtils.toByteArray(yc, x));
    final byte[] xd = { (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00 };
    final Integer yd = 0xFF00FF00;
    final byte[] zd = {};
    assertArrayEquals(xd, IntUtils.toByteArray(yd, zd));
    assertArrayEquals(xd, IntUtils.toByteArray(yd, x));
    final byte[] xe = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
    final Integer ye = 0xFFFFFFFF;
    final byte[] ze = {};
    assertArrayEquals(xe, IntUtils.toByteArray(ye, ze));
    assertArrayEquals(xe, IntUtils.toByteArray(ye, x));
    final byte[] xf = { (byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA };
    final Integer yf = 0xBBAABBAA;
    final byte[] zf = {};
    assertArrayEquals(xf, IntUtils.toByteArray(yf, zf));
    assertArrayEquals(xf, IntUtils.toByteArray(yf, x));
  }

  @Test
  public void testToByteArray_Integer_ByteArray_ByteOrder() {
    final byte[] x = null;
    assertEquals(null, IntUtils.toByteArray(null, x, ByteOrder.BIG_ENDIAN));
    assertEquals(null, IntUtils.toByteArray(null, x, ByteOrder.LITTLE_ENDIAN));

    final byte[] a = { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
    assertArrayEquals(a, IntUtils.toByteArray(null, a, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(a, IntUtils.toByteArray(null, a, ByteOrder.LITTLE_ENDIAN));
    final byte[] b = { (byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00 };
    assertArrayEquals(b, IntUtils.toByteArray(null, b, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(b, IntUtils.toByteArray(null, b, ByteOrder.LITTLE_ENDIAN));
    final byte[] c = { (byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB };
    assertArrayEquals(c, IntUtils.toByteArray(null, c, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(c, IntUtils.toByteArray(null, c, ByteOrder.LITTLE_ENDIAN));
    final byte[] d = { (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00 };
    assertArrayEquals(d, IntUtils.toByteArray(null, d, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(d, IntUtils.toByteArray(null, d, ByteOrder.LITTLE_ENDIAN));
    final byte[] e = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
    assertArrayEquals(e, IntUtils.toByteArray(null, e, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(e, IntUtils.toByteArray(null, e, ByteOrder.LITTLE_ENDIAN));
    final byte[] f = { (byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA };
    assertArrayEquals(f, IntUtils.toByteArray(null, f, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(f, IntUtils.toByteArray(null, f, ByteOrder.LITTLE_ENDIAN));

    final byte[] xa = { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
    final Integer ya = 0x00000000;
    final byte[] za = {};
    assertArrayEquals(xa, IntUtils.toByteArray(ya, za, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xa, IntUtils.toByteArray(ya, za, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xa, IntUtils.toByteArray(ya, x, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xa, IntUtils.toByteArray(ya, x, ByteOrder.LITTLE_ENDIAN));
    final byte[] xb = { (byte) 0xAB, (byte) 0x00, (byte) 0xAB, (byte) 0x00 };
    final Integer yb1 = 0xAB00AB00;
    final Integer yb2 = 0x00AB00AB;
    final byte[] zb = {};
    assertArrayEquals(xb, IntUtils.toByteArray(yb1, zb, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xb,
        IntUtils.toByteArray(yb2, zb, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xb, IntUtils.toByteArray(yb1, x, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xb, IntUtils.toByteArray(yb2, x, ByteOrder.LITTLE_ENDIAN));
    final byte[] xc = { (byte) 0xAB, (byte) 0xAB, (byte) 0xAB, (byte) 0xAB };
    final Integer yc = 0xABABABAB;
    final byte[] zc = {};
    assertArrayEquals(xc, IntUtils.toByteArray(yc, zc, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xc, IntUtils.toByteArray(yc, zc, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xc, IntUtils.toByteArray(yc, x, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xc, IntUtils.toByteArray(yc, x, ByteOrder.LITTLE_ENDIAN));
    final byte[] xd = { (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00 };
    final Integer yd1 = 0xFF00FF00;
    final Integer yd2 = 0x00FF00FF;
    final byte[] zd = {};
    assertArrayEquals(xd, IntUtils.toByteArray(yd1, zd, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xd,
        IntUtils.toByteArray(yd2, zd, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xd, IntUtils.toByteArray(yd1, x, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xd, IntUtils.toByteArray(yd2, x, ByteOrder.LITTLE_ENDIAN));
    final byte[] xe = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
    final Integer ye = 0xFFFFFFFF;
    final byte[] ze = {};
    assertArrayEquals(xe, IntUtils.toByteArray(ye, ze, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xe, IntUtils.toByteArray(ye, ze, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xe, IntUtils.toByteArray(ye, x, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xe, IntUtils.toByteArray(ye, x, ByteOrder.LITTLE_ENDIAN));
    final byte[] xf = { (byte) 0xBB, (byte) 0xAA, (byte) 0xBB, (byte) 0xAA };
    final Integer yf1 = 0xBBAABBAA;
    final Integer yf2 = 0xAABBAABB;
    final byte[] zf = {};
    assertArrayEquals(xf, IntUtils.toByteArray(yf1, zf, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xf,
        IntUtils.toByteArray(yf2, zf, ByteOrder.LITTLE_ENDIAN));
    assertArrayEquals(xf, IntUtils.toByteArray(yf1, x, ByteOrder.BIG_ENDIAN));
    assertArrayEquals(xf, IntUtils.toByteArray(yf2, x, ByteOrder.LITTLE_ENDIAN));
  }

  @Test
  public void testToClass_int() {
    for (int i = 0; i < AREA.length; ++i) {
      assertSame(Integer.TYPE, IntUtils.toClass(AREA[i]));
    }
  }

  @Test
  public void testToClass_Integer() {
    assertSame(null, IntUtils.toClass(null));

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      assertSame(Integer.class, IntUtils.toClass(x));
    }
  }

  @Test
  public void testToClass_Integer_ClassOfQ() {
    assertSame(null, IntUtils.toClass(null, null));

    assertSame(Integer.class, IntUtils.toClass(null, Integer.class));

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      assertSame(Integer.class, IntUtils.toClass(x, null));
      assertSame(Integer.class, IntUtils.toClass(x, Integer.class));
    }
  }

  @Test
  public void testToBigInteger_int() {
    for (int i = 0; i < AREA.length; ++i) {
      final BigInteger x = BigInteger.valueOf(AREA[i]);
      assertEquals(x, IntUtils.toBigInteger(AREA[i]));
    }
  }

  @Test
  public void testToBigInteger_Integer() {
    assertEquals(null, IntUtils.toBigInteger(null));

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      final BigInteger y = BigInteger.valueOf(AREA[i]);
      assertEquals(y, IntUtils.toBigInteger(x));
    }
  }

  @Test
  public void testToBigInteger_Integer_BigInteger() {
    assertEquals(null, IntUtils.toBigInteger(null, null));

    for (int i = 0; i < AREA.length; ++i) {
      final BigInteger x = BigInteger.valueOf(AREA[i]);
      assertEquals(x, IntUtils.toBigInteger(null, x));
    }

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      final BigInteger y = BigInteger.valueOf(AREA[i]);
      assertEquals(y, IntUtils.toBigInteger(x, null));
      for (int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final BigInteger z = BigInteger.valueOf(j);
        assertEquals(y, IntUtils.toBigInteger(x, z));
      }
    }
  }

  @Test
  public void testToBigDecimal_int() {
    for (int i = 0; i < AREA.length; ++i) {
      final BigDecimal x = BigDecimal.valueOf(AREA[i]);
      assertEquals(x, IntUtils.toBigDecimal(AREA[i]));
    }
  }

  @Test
  public void testToBigDecimal_Integer() {
    assertEquals(null, IntUtils.toBigDecimal(null));

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      final BigDecimal y = BigDecimal.valueOf(AREA[i]);
      assertEquals(y, IntUtils.toBigDecimal(x));
    }
  }

  @Test
  public void testToBigDecimal_Integer_BigDecimal() {
    assertEquals(null, IntUtils.toBigDecimal(null, null));

    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i) {
      final BigDecimal x = BigDecimal.valueOf(i);
      assertEquals(x, IntUtils.toBigDecimal(null, x));
    }

    for (int i = 0; i < AREA.length; ++i) {
      final Integer x = AREA[i];
      final BigDecimal y = BigDecimal.valueOf(AREA[i]);
      assertEquals(y, IntUtils.toBigDecimal(x, null));
      for (int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; ++j) {
        final BigDecimal z = BigDecimal.valueOf(j);
        assertEquals(y, IntUtils.toBigDecimal(x, z));
      }
    }
  }

}
