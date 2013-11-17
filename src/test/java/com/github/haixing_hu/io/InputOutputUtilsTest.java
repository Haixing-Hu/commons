/******************************************************************************
 *
 * Copyright (c) 2013  Haixing Hu
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
 ******************************************************************************/

package com.github.haixing_hu.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.github.haixing_hu.io.AbstractSeekableInputStream;
import com.github.haixing_hu.io.InputUtils;
import com.github.haixing_hu.io.OutputUtils;
import com.github.haixing_hu.io.exception.InvalidFormatException;
import com.github.haixing_hu.lang.Type;
import com.github.haixing_hu.net.UrlPart;
import com.github.haixing_hu.text.PatternType;

import static org.junit.Assert.*;

/**
 * Unit test of the {@link InputUtils} and {@link OutputUtils} classes.
 *
 * TODO: finish the test of remained functions.
 *
 * @author Haixing Hu
 */
public class InputOutputUtilsTest {

  public static final float FLOAT_EPSILON = 0.00001f;

  public static final double DOUBLE_EPSILON = 0.000000001;

  @Test
  public void testBoolean() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeBoolean(out, true);
    OutputUtils.writeBoolean(out, false);
    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(true, InputUtils.readBoolean(in));
    assertEquals(false, InputUtils.readBoolean(in));
  }

  @Test
  public void testBooleanObject() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeBooleanObject(out, Boolean.TRUE);
    OutputUtils.writeBooleanObject(out, null);
    OutputUtils.writeBooleanObject(out, Boolean.FALSE);
    OutputUtils.writeBooleanObject(out, null);
    OutputUtils.writeBooleanObject(out, Boolean.TRUE);
    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(Boolean.TRUE, InputUtils.readBooleanObject(in, true));
    assertEquals(null, InputUtils.readBooleanObject(in, true));
    assertEquals(Boolean.FALSE, InputUtils.readBooleanObject(in, true));
    try {
      InputUtils.readBooleanObject(in, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // ok
    }
    assertEquals(Boolean.TRUE, InputUtils.readBooleanObject(in, true));
  }

  @Test
  public void testChar() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeChar(out, '\0');
    OutputUtils.writeChar(out, 'x');
    OutputUtils.writeChar(out, 'y');
    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals('\0', InputUtils.readChar(in));
    assertEquals('x', InputUtils.readChar(in));
    assertEquals('y', InputUtils.readChar(in));
  }

  @Test
  public void testCharObject() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeCharObject(out, '\0');
    OutputUtils.writeCharObject(out, 'x');
    OutputUtils.writeCharObject(out, null);
    OutputUtils.writeCharObject(out, 'y');
    OutputUtils.writeCharObject(out, null);
    OutputUtils.writeCharObject(out, 'x');

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(new Character('\0'), InputUtils.readCharObject(in, true));
    assertEquals(new Character('x'), InputUtils.readCharObject(in, true));
    assertEquals(null, InputUtils.readCharObject(in, true));
    assertEquals(new Character('y'), InputUtils.readCharObject(in, true));
    try {
      InputUtils.readCharObject(in, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // ok
    }
    assertEquals(new Character('x'), InputUtils.readCharObject(in, true));
  }

  @Test
  public void testByte() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeByte(out, (byte)0);
    OutputUtils.writeByte(out, (byte)12);
    OutputUtils.writeByte(out, (byte)-12);
    OutputUtils.writeByte(out, Byte.MIN_VALUE);
    OutputUtils.writeByte(out, Byte.MAX_VALUE);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(0, InputUtils.readByte(in));
    assertEquals(12, InputUtils.readByte(in));
    assertEquals(-12, InputUtils.readByte(in));
    assertEquals(Byte.MIN_VALUE, InputUtils.readByte(in));
    assertEquals(Byte.MAX_VALUE, InputUtils.readByte(in));
  }

  @Test
  public void testByteObject() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeByteObject(out, (byte)0);
    OutputUtils.writeByteObject(out, (byte)12);
    OutputUtils.writeByteObject(out, (byte)-12);
    OutputUtils.writeByteObject(out, null);
    OutputUtils.writeByteObject(out, Byte.MIN_VALUE);
    OutputUtils.writeByteObject(out, Byte.MAX_VALUE);
    OutputUtils.writeByteObject(out, null);
    OutputUtils.writeByteObject(out, Byte.MIN_VALUE);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(new Byte((byte)0), InputUtils.readByteObject(in, true));
    assertEquals(new Byte((byte)12), InputUtils.readByteObject(in, true));
    assertEquals(new Byte((byte)-12), InputUtils.readByteObject(in, true));
    assertEquals(null, InputUtils.readByteObject(in, true));
    assertEquals(new Byte(Byte.MIN_VALUE), InputUtils.readByteObject(in, true));
    assertEquals(new Byte(Byte.MAX_VALUE), InputUtils.readByteObject(in, true));
    try {
      InputUtils.readByteObject(in, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // ok
    }
    assertEquals(new Byte(Byte.MIN_VALUE), InputUtils.readByteObject(in, true));
  }

  @Test
  public void testShort() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeShort(out, (short)0);
    OutputUtils.writeShort(out, (short)1212);
    OutputUtils.writeShort(out, (short)-1212);
    OutputUtils.writeShort(out, Short.MIN_VALUE);
    OutputUtils.writeShort(out, Short.MAX_VALUE);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(0, InputUtils.readShort(in));
    assertEquals(1212, InputUtils.readShort(in));
    assertEquals(-1212, InputUtils.readShort(in));
    assertEquals(Short.MIN_VALUE, InputUtils.readShort(in));
    assertEquals(Short.MAX_VALUE, InputUtils.readShort(in));
  }

  @Test
  public void testShortObject() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeShortObject(out, (short)0);
    OutputUtils.writeShortObject(out, (short)1212);
    OutputUtils.writeShortObject(out, (short)-1212);
    OutputUtils.writeShortObject(out, null);
    OutputUtils.writeShortObject(out, Short.MIN_VALUE);
    OutputUtils.writeShortObject(out, Short.MAX_VALUE);
    OutputUtils.writeShortObject(out, null);
    OutputUtils.writeShortObject(out, Short.MAX_VALUE);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(new Short((short)0), InputUtils.readShortObject(in, true));
    assertEquals(new Short((short)1212), InputUtils.readShortObject(in, true));
    assertEquals(new Short((short)-1212), InputUtils.readShortObject(in, true));
    assertEquals(null, InputUtils.readShortObject(in, true));
    assertEquals(new Short(Short.MIN_VALUE), InputUtils.readShortObject(in, true));
    assertEquals(new Short(Short.MAX_VALUE), InputUtils.readShortObject(in, true));
    try {
      InputUtils.readShortObject(in, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // ok
    }
    assertEquals(new Short(Short.MAX_VALUE), InputUtils.readShortObject(in, true));
  }

  @Test
  public void testVarShort() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeVarShort(out, (short)0);
    OutputUtils.writeVarShort(out, (short)1212);
    try {
      OutputUtils.writeVarShort(out, (short)-1212);
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }
    try {
      OutputUtils.writeVarShort(out, Short.MIN_VALUE);
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }
    OutputUtils.writeVarShort(out, Short.MAX_VALUE);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(0, InputUtils.readVarShort(in));
    assertEquals(1212, InputUtils.readVarShort(in));
    assertEquals(Short.MAX_VALUE, InputUtils.readVarShort(in));
  }

  @Test
  public void testVarShortObject() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeVarShortObject(out, (short)0);
    OutputUtils.writeVarShortObject(out, (short)1212);
    try {
      OutputUtils.writeVarShortObject(out, (short)-1212);
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }
    OutputUtils.writeVarShortObject(out, null);
    try {
      OutputUtils.writeVarShortObject(out, Short.MIN_VALUE);
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }
    OutputUtils.writeVarShortObject(out, Short.MAX_VALUE);
    OutputUtils.writeVarShortObject(out, null);
    OutputUtils.writeVarShortObject(out, Short.MAX_VALUE);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(new Short((short)0), InputUtils.readVarShortObject(in, true));
    assertEquals(new Short((short)1212), InputUtils.readVarShortObject(in, true));
    assertEquals(null, InputUtils.readShortObject(in, true));
    assertEquals(new Short(Short.MAX_VALUE), InputUtils.readVarShortObject(in, true));
    try {
      InputUtils.readVarShortObject(in, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // ok
    }
    assertEquals(new Short(Short.MAX_VALUE), InputUtils.readVarShortObject(in, true));
  }

  @Test
  public void testInt() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeInt(out, 0);
    OutputUtils.writeInt(out, 1234567);
    OutputUtils.writeInt(out, -1234567);
    OutputUtils.writeInt(out, Integer.MIN_VALUE);
    OutputUtils.writeInt(out, Integer.MAX_VALUE);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(0, InputUtils.readInt(in));
    assertEquals(1234567, InputUtils.readInt(in));
    assertEquals(-1234567, InputUtils.readInt(in));
    assertEquals(Integer.MIN_VALUE, InputUtils.readInt(in));
    assertEquals(Integer.MAX_VALUE, InputUtils.readInt(in));
  }

  @Test
  public void testIntObject() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeIntObject(out, 0);
    OutputUtils.writeIntObject(out, 1234567);
    OutputUtils.writeIntObject(out, -1234567);
    OutputUtils.writeIntObject(out, null);
    OutputUtils.writeIntObject(out, Integer.MIN_VALUE);
    OutputUtils.writeIntObject(out, Integer.MAX_VALUE);
    OutputUtils.writeIntObject(out, null);
    OutputUtils.writeIntObject(out, Integer.MAX_VALUE);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(new Integer(0), InputUtils.readIntObject(in, true));
    assertEquals(new Integer(1234567), InputUtils.readIntObject(in, true));
    assertEquals(new Integer(-1234567), InputUtils.readIntObject(in, true));
    assertEquals(null, InputUtils.readIntObject(in, true));
    assertEquals(new Integer(Integer.MIN_VALUE), InputUtils.readIntObject(in, true));
    assertEquals(new Integer(Integer.MAX_VALUE), InputUtils.readIntObject(in, true));
    try {
      InputUtils.readIntObject(in, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // ok
    }
    assertEquals(new Integer(Integer.MAX_VALUE), InputUtils.readIntObject(in, true));
  }

  @Test
  public void testVarInt() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeVarInt(out, 0);
    OutputUtils.writeVarInt(out, 1234567);
    try {
      OutputUtils.writeVarInt(out, -1234567);
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }
    try {
      OutputUtils.writeVarInt(out, Integer.MIN_VALUE);
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }
    OutputUtils.writeVarInt(out, Integer.MAX_VALUE);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(0, InputUtils.readVarInt(in));
    assertEquals(1234567, InputUtils.readVarInt(in));
    assertEquals(Integer.MAX_VALUE, InputUtils.readVarInt(in));
  }

  @Test
  public void testVarIntObject() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeVarIntObject(out, 0);
    OutputUtils.writeVarIntObject(out, 1234567);
    try {
      OutputUtils.writeVarIntObject(out, -1234567);
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }
    OutputUtils.writeVarIntObject(out, null);
    try {
      OutputUtils.writeVarIntObject(out, Integer.MIN_VALUE);
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }
    OutputUtils.writeVarIntObject(out, Integer.MAX_VALUE);
    OutputUtils.writeVarIntObject(out, null);
    OutputUtils.writeVarIntObject(out, Integer.MAX_VALUE);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(new Integer(0), InputUtils.readVarIntObject(in, true));
    assertEquals(new Integer(1234567), InputUtils.readVarIntObject(in, true));
    assertEquals(null, InputUtils.readVarIntObject(in, true));
    assertEquals(new Integer(Integer.MAX_VALUE), InputUtils.readVarIntObject(in, true));
    try {
      InputUtils.readVarIntObject(in, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // ok
    }
    assertEquals(new Integer(Integer.MAX_VALUE), InputUtils.readVarIntObject(in, true));
  }

  @Test
  public void testLong() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeLong(out, 0L);
    OutputUtils.writeLong(out, 12345678901234567L);
    OutputUtils.writeLong(out, -12345678901234567L);
    OutputUtils.writeLong(out, Long.MIN_VALUE);
    OutputUtils.writeLong(out, Long.MAX_VALUE);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(0, InputUtils.readLong(in));
    assertEquals(12345678901234567L, InputUtils.readLong(in));
    assertEquals(-12345678901234567L, InputUtils.readLong(in));
    assertEquals(Long.MIN_VALUE, InputUtils.readLong(in));
    assertEquals(Long.MAX_VALUE, InputUtils.readLong(in));
  }

  @Test
  public void testLongObject() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeLongObject(out, 0L);
    OutputUtils.writeLongObject(out, 12345678901234567L);
    OutputUtils.writeLongObject(out, -12345678901234567L);
    OutputUtils.writeLongObject(out, null);
    OutputUtils.writeLongObject(out, Long.MIN_VALUE);
    OutputUtils.writeLongObject(out, Long.MAX_VALUE);
    OutputUtils.writeLongObject(out, null);
    OutputUtils.writeLongObject(out, Long.MAX_VALUE);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(new Long(0), InputUtils.readLongObject(in, true));
    assertEquals(new Long(12345678901234567L), InputUtils.readLongObject(in, true));
    assertEquals(new Long(-12345678901234567L), InputUtils.readLongObject(in, true));
    assertEquals(null, InputUtils.readLongObject(in, true));
    assertEquals(new Long(Long.MIN_VALUE), InputUtils.readLongObject(in, true));
    assertEquals(new Long(Long.MAX_VALUE), InputUtils.readLongObject(in, true));
    try {
      InputUtils.readLongObject(in, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // ok
    }
    assertEquals(new Long(Long.MAX_VALUE), InputUtils.readLongObject(in, true));
  }

  @Test
  public void testVarLong() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeVarLong(out, 0L);
    OutputUtils.writeVarLong(out, 12345678901234567L);
    try {
      OutputUtils.writeVarLong(out, -12345678901234567L);
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }
    try {
      OutputUtils.writeVarLong(out, Long.MIN_VALUE);
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }
    OutputUtils.writeVarLong(out, Long.MAX_VALUE);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(0, InputUtils.readVarLong(in));
    assertEquals(12345678901234567L, InputUtils.readVarLong(in));
    assertEquals(Long.MAX_VALUE, InputUtils.readVarLong(in));
  }

  @Test
  public void testVarLongObject() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeVarLongObject(out, 0L);
    OutputUtils.writeVarLongObject(out, 12345678901234567L);
    try {
      OutputUtils.writeVarLongObject(out, -12345678901234567L);
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }
    OutputUtils.writeVarLongObject(out, null);
    try {
      OutputUtils.writeVarLongObject(out, Long.MIN_VALUE);
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }
    OutputUtils.writeVarLongObject(out, Long.MAX_VALUE);
    OutputUtils.writeVarLongObject(out, null);
    OutputUtils.writeVarLongObject(out, Long.MAX_VALUE);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(new Long(0), InputUtils.readVarLongObject(in, true));
    assertEquals(new Long(12345678901234567L), InputUtils.readVarLongObject(in, true));
    assertEquals(null, InputUtils.readVarLongObject(in, true));
    assertEquals(new Long(Long.MAX_VALUE), InputUtils.readVarLongObject(in, true));
    try {
      InputUtils.readVarLongObject(in, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // ok
    }
    assertEquals(new Long(Long.MAX_VALUE), InputUtils.readVarLongObject(in, true));
  }

  @Test
  public void testFloat() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeFloat(out, 0.0f);
    OutputUtils.writeFloat(out, 1234.567f);
    OutputUtils.writeFloat(out, -1234.567f);
    OutputUtils.writeFloat(out, Float.MIN_VALUE);
    OutputUtils.writeFloat(out, Float.MAX_VALUE);
    OutputUtils.writeFloat(out, Float.MIN_NORMAL);
    OutputUtils.writeFloat(out, Float.NaN);
    OutputUtils.writeFloat(out, Float.NEGATIVE_INFINITY);
    OutputUtils.writeFloat(out, Float.POSITIVE_INFINITY);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(0.0f, InputUtils.readFloat(in), FLOAT_EPSILON);
    assertEquals(1234.567f, InputUtils.readFloat(in), FLOAT_EPSILON);
    assertEquals(-1234.567f, InputUtils.readFloat(in), FLOAT_EPSILON);
    assertEquals(Float.MIN_VALUE, InputUtils.readFloat(in), FLOAT_EPSILON);
    assertEquals(Float.MAX_VALUE, InputUtils.readFloat(in), FLOAT_EPSILON);
    assertEquals(Float.MIN_NORMAL, InputUtils.readFloat(in), FLOAT_EPSILON);
    assertEquals(Float.NaN, InputUtils.readFloat(in), FLOAT_EPSILON);
    assertEquals(Float.NEGATIVE_INFINITY, InputUtils.readFloat(in), FLOAT_EPSILON);
    assertEquals(Float.POSITIVE_INFINITY, InputUtils.readFloat(in), FLOAT_EPSILON);
  }

  @Test
  public void testFloatObject() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeFloatObject(out, 0.0f);
    OutputUtils.writeFloatObject(out, 1234.567f);
    OutputUtils.writeFloatObject(out, -1234.567f);
    OutputUtils.writeFloatObject(out, null);
    OutputUtils.writeFloatObject(out, Float.MIN_VALUE);
    OutputUtils.writeFloatObject(out, Float.MAX_VALUE);
    OutputUtils.writeFloatObject(out, Float.MIN_NORMAL);
    OutputUtils.writeFloatObject(out, Float.NaN);
    OutputUtils.writeFloatObject(out, Float.NEGATIVE_INFINITY);
    OutputUtils.writeFloatObject(out, Float.POSITIVE_INFINITY);
    OutputUtils.writeFloatObject(out, null);
    OutputUtils.writeFloatObject(out, Float.POSITIVE_INFINITY);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(0.0f, InputUtils.readFloatObject(in, true), FLOAT_EPSILON);
    assertEquals(1234.567f, InputUtils.readFloatObject(in, true), FLOAT_EPSILON);
    assertEquals(-1234.567f, InputUtils.readFloatObject(in, true), FLOAT_EPSILON);
    assertEquals(null, InputUtils.readFloatObject(in, true));
    assertEquals(Float.MIN_VALUE, InputUtils.readFloatObject(in, true), FLOAT_EPSILON);
    assertEquals(Float.MAX_VALUE, InputUtils.readFloatObject(in, true), FLOAT_EPSILON);
    assertEquals(Float.MIN_NORMAL, InputUtils.readFloatObject(in, true), FLOAT_EPSILON);
    assertEquals(Float.NaN, InputUtils.readFloatObject(in, true), FLOAT_EPSILON);
    assertEquals(Float.NEGATIVE_INFINITY, InputUtils.readFloatObject(in, true), FLOAT_EPSILON);
    assertEquals(Float.POSITIVE_INFINITY, InputUtils.readFloatObject(in, true), FLOAT_EPSILON);
    try {
      InputUtils.readFloatObject(in, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // ok
    }
    assertEquals(Float.POSITIVE_INFINITY, InputUtils.readFloatObject(in, true), FLOAT_EPSILON);
  }

  @Test
  public void testDouble() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeDouble(out, 0.0f);
    OutputUtils.writeDouble(out, 1234.567);
    OutputUtils.writeDouble(out, -1234.567);
    OutputUtils.writeDouble(out, Double.MIN_VALUE);
    OutputUtils.writeDouble(out, Double.MAX_VALUE);
    OutputUtils.writeDouble(out, Double.MIN_NORMAL);
    OutputUtils.writeDouble(out, Double.NaN);
    OutputUtils.writeDouble(out, Double.NEGATIVE_INFINITY);
    OutputUtils.writeDouble(out, Double.POSITIVE_INFINITY);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(0.0f, InputUtils.readDouble(in), FLOAT_EPSILON);
    assertEquals(1234.567, InputUtils.readDouble(in), FLOAT_EPSILON);
    assertEquals(-1234.567, InputUtils.readDouble(in), FLOAT_EPSILON);
    assertEquals(Double.MIN_VALUE, InputUtils.readDouble(in), FLOAT_EPSILON);
    assertEquals(Double.MAX_VALUE, InputUtils.readDouble(in), FLOAT_EPSILON);
    assertEquals(Double.MIN_NORMAL, InputUtils.readDouble(in), FLOAT_EPSILON);
    assertEquals(Double.NaN, InputUtils.readDouble(in), FLOAT_EPSILON);
    assertEquals(Double.NEGATIVE_INFINITY, InputUtils.readDouble(in), FLOAT_EPSILON);
    assertEquals(Double.POSITIVE_INFINITY, InputUtils.readDouble(in), FLOAT_EPSILON);
  }

  @Test
  public void testDoubleObject() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeDoubleObject(out, 0.0);
    OutputUtils.writeDoubleObject(out, 1234.567);
    OutputUtils.writeDoubleObject(out, -1234.567);
    OutputUtils.writeDoubleObject(out, null);
    OutputUtils.writeDoubleObject(out, Double.MIN_VALUE);
    OutputUtils.writeDoubleObject(out, Double.MAX_VALUE);
    OutputUtils.writeDoubleObject(out, Double.MIN_NORMAL);
    OutputUtils.writeDoubleObject(out, Double.NaN);
    OutputUtils.writeDoubleObject(out, Double.NEGATIVE_INFINITY);
    OutputUtils.writeDoubleObject(out, Double.POSITIVE_INFINITY);
    OutputUtils.writeDoubleObject(out, null);
    OutputUtils.writeDoubleObject(out, Double.POSITIVE_INFINITY);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(0.0f, InputUtils.readDoubleObject(in, true), FLOAT_EPSILON);
    assertEquals(1234.567, InputUtils.readDoubleObject(in, true), FLOAT_EPSILON);
    assertEquals(-1234.567, InputUtils.readDoubleObject(in, true), FLOAT_EPSILON);
    assertEquals(null, InputUtils.readDoubleObject(in, true));
    assertEquals(Double.MIN_VALUE, InputUtils.readDoubleObject(in, true), FLOAT_EPSILON);
    assertEquals(Double.MAX_VALUE, InputUtils.readDoubleObject(in, true), FLOAT_EPSILON);
    assertEquals(Double.MIN_NORMAL, InputUtils.readDoubleObject(in, true), FLOAT_EPSILON);
    assertEquals(Double.NaN, InputUtils.readDoubleObject(in, true), FLOAT_EPSILON);
    assertEquals(Double.NEGATIVE_INFINITY, InputUtils.readDoubleObject(in, true), FLOAT_EPSILON);
    assertEquals(Double.POSITIVE_INFINITY, InputUtils.readDoubleObject(in, true), FLOAT_EPSILON);
    try {
      InputUtils.readDoubleObject(in, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // ok
    }
    assertEquals(Double.POSITIVE_INFINITY, InputUtils.readDoubleObject(in, true), FLOAT_EPSILON);
  }

  @Test
  public void testString() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeString(out, "");
    OutputUtils.writeString(out, "hello, world");
    OutputUtils.writeString(out, null);
    OutputUtils.writeString(out, "1234\n1234");
    OutputUtils.writeString(out, "");
    OutputUtils.writeString(out, null);
    OutputUtils.writeString(out, "1234\n1234");

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals("", InputUtils.readString(in, true));
    assertEquals("hello, world", InputUtils.readString(in, true));
    assertEquals(null, InputUtils.readString(in, true));
    assertEquals("1234\n1234", InputUtils.readString(in, true));
    assertEquals("", InputUtils.readString(in, true));
    try {
      InputUtils.readString(in, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // ok
    }
    assertEquals("1234\n1234", InputUtils.readString(in, true));
  }

  @Test
  public void testDate() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeDate(out, new Date(0L));
    OutputUtils.writeDate(out, new Date(12345678901234567L));
    OutputUtils.writeDate(out, new Date(-12345678901234567L));
    OutputUtils.writeDate(out, null);
    OutputUtils.writeDate(out, new Date(Long.MIN_VALUE));
    OutputUtils.writeDate(out, new Date(Long.MAX_VALUE));
    OutputUtils.writeDate(out, null);
    OutputUtils.writeDate(out, new Date(Long.MAX_VALUE));

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(new Date(0), InputUtils.readDate(in, true));
    assertEquals(new Date(12345678901234567L), InputUtils.readDate(in, true));
    assertEquals(new Date(-12345678901234567L), InputUtils.readDate(in, true));
    assertEquals(null, InputUtils.readDate(in, true));
    assertEquals(new Date(Long.MIN_VALUE), InputUtils.readDate(in, true));
    assertEquals(new Date(Long.MAX_VALUE), InputUtils.readDate(in, true));
    try {
      InputUtils.readDate(in, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // ok
    }
    assertEquals(new Date(Long.MAX_VALUE), InputUtils.readDate(in, true));
  }

  @Test
  public void testBigInteger() throws IOException {
    final int MAX_BITS = 1024;
    final int RAND_COUNT = 10;
    final BigInteger[] randomBigInteger = new BigInteger[RAND_COUNT];
    final Random rand = new Random(System.currentTimeMillis());

    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeBigInteger(out, BigInteger.TEN);
    OutputUtils.writeBigInteger(out, BigInteger.ONE);
    OutputUtils.writeBigInteger(out, BigInteger.ZERO);
    OutputUtils.writeBigInteger(out, null);
    for (int i = 0; i < RAND_COUNT; ++i) {
      randomBigInteger[i] = new BigInteger(rand.nextInt(MAX_BITS), rand);
      OutputUtils.writeBigInteger(out, randomBigInteger[i]);
    }
    OutputUtils.writeBigInteger(out, null);
    OutputUtils.writeBigInteger(out, BigInteger.ONE);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(BigInteger.TEN, InputUtils.readBigInteger(in, true));
    assertEquals(BigInteger.ONE, InputUtils.readBigInteger(in, true));
    assertEquals(BigInteger.ZERO, InputUtils.readBigInteger(in, true));
    assertEquals(null, InputUtils.readBigInteger(in, true));
    for (int i = 0; i < RAND_COUNT; ++i) {
      assertEquals(randomBigInteger[i], InputUtils.readBigInteger(in, true));
    }
    try {
      InputUtils.readBigInteger(in, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // ok
    }
    assertEquals(BigInteger.ONE, InputUtils.readBigInteger(in, true));
  }

  @Test
  public void testBigDecimal() throws IOException {
    final int MAX_BITS = 1024;
    final int MAX_SCALE = 100;
    final int RAND_COUNT = 10;

    final BigInteger[] randomBigInteger = new BigInteger[RAND_COUNT];
    final BigDecimal[] randomBigDecimal = new BigDecimal[RAND_COUNT];
    final Random rand = new Random(System.currentTimeMillis());

    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeBigDecimal(out, BigDecimal.TEN);
    OutputUtils.writeBigDecimal(out, BigDecimal.ONE);
    OutputUtils.writeBigDecimal(out, BigDecimal.ZERO);
    OutputUtils.writeBigDecimal(out, null);
    for (int i = 0; i < RAND_COUNT; ++i) {
      randomBigInteger[i] = new BigInteger(rand.nextInt(MAX_BITS), rand);
      int scale = rand.nextInt(MAX_SCALE);
      final int scaleSign = rand.nextInt() % 2;
      if (scaleSign == 1) {
        scale = -scale;
      }
      randomBigDecimal[i] = new BigDecimal(randomBigInteger[i], scale);
      // XXX: fix the zero big decimal, e.g., 0^52, is the same as 0
      if (randomBigDecimal[i].signum() == 0) {
        randomBigDecimal[i] = BigDecimal.ZERO;
      }
      OutputUtils.writeBigDecimal(out, randomBigDecimal[i]);
    }
    OutputUtils.writeBigDecimal(out, null);
    OutputUtils.writeBigDecimal(out, BigDecimal.TEN);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(BigDecimal.TEN, InputUtils.readBigDecimal(in, true));
    assertEquals(BigDecimal.ONE, InputUtils.readBigDecimal(in, true));
    assertEquals(BigDecimal.ZERO, InputUtils.readBigDecimal(in, true));
    assertEquals(null, InputUtils.readBigDecimal(in, true));
    for (int i = 0; i < RAND_COUNT; ++i) {
      assertEquals(randomBigDecimal[i], InputUtils.readBigDecimal(in, true));
    }
    try {
      InputUtils.readBigDecimal(in, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // ok
    }
    assertEquals(BigDecimal.TEN, InputUtils.readBigDecimal(in, true));
  }

  @Test
  public void testClass() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeClass(out, String.class);
    OutputUtils.writeClass(out, Integer.class);
    OutputUtils.writeClass(out, null);
    OutputUtils.writeClass(out, ByteArrayInputStream.class);
    OutputUtils.writeClass(out, ByteArrayOutputStream.class);
    OutputUtils.writeClass(out, null);
    OutputUtils.writeClass(out, Date.class);
    OutputUtils.writeClass(out, InputStream.class);
    OutputUtils.writeClass(out, AbstractSeekableInputStream.class);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(String.class, InputUtils.readClass(in, true));
    assertEquals(Integer.class, InputUtils.readClass(in, true));
    assertEquals(null, InputUtils.readClass(in, true));
    assertEquals(ByteArrayInputStream.class, InputUtils.readClass(in, true));
    assertEquals(ByteArrayOutputStream.class, InputUtils.readClass(in, true));
    try {
      InputUtils.readClass(in, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // ok
    }
    assertEquals(Date.class, InputUtils.readClass(in, true));
    assertEquals(InputStream.class, InputUtils.readClass(in, true));
    assertEquals(AbstractSeekableInputStream.class, InputUtils.readClass(in, true));
  }

  @Test
  public void testEnum() throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputUtils.writeEnum(out, Type.BOOLEAN);
    OutputUtils.writeEnum(out, Type.BYTE_ARRAY);
    OutputUtils.writeEnum(out, null);
    OutputUtils.writeEnum(out, TimeUnit.MICROSECONDS);
    OutputUtils.writeEnum(out, TimeUnit.DAYS);
    OutputUtils.writeEnum(out, null);
    OutputUtils.writeEnum(out, PatternType.GLOB);
    OutputUtils.writeEnum(out, Type.BYTE);
    OutputUtils.writeEnum(out, UrlPart.DOMAIN);

    final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    assertEquals(Type.BOOLEAN, InputUtils.readEnum(Type.class, in, true));
    assertEquals(Type.BYTE_ARRAY, InputUtils.readEnum(Type.class, in, true));
    assertEquals(null, InputUtils.readEnum(Type.class, in, true));
    assertEquals(TimeUnit.MICROSECONDS, InputUtils.readEnum(TimeUnit.class, in, true));
    assertEquals(TimeUnit.DAYS, InputUtils.readEnum(TimeUnit.class, in, true));
    try {
      InputUtils.readEnum(TimeUnit.class, in, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // ok
    }
    assertEquals(PatternType.GLOB, InputUtils.readEnum(PatternType.class, in, true));
    assertEquals(Type.BYTE, InputUtils.readEnum(Type.class, in, true));
    assertEquals(UrlPart.DOMAIN, InputUtils.readEnum(UrlPart.class, in, true));
  }
}
