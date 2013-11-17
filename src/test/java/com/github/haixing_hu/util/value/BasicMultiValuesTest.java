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

package com.github.haixing_hu.util.value;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.junit.Test;

import com.github.haixing_hu.lang.Equality;
import com.github.haixing_hu.lang.FloatUtils;
import com.github.haixing_hu.lang.Type;
import com.github.haixing_hu.util.value.BasicMultiValues;

import static org.junit.Assert.*;

/**
 * Unit test of the {@link BasicMultiValues} class.
 *
 * @author Haixing Hu
 */
public class BasicMultiValuesTest {

  /**
   * Test method for {@link BasicMultiValues#BasicVariants()}.
   */
  @Test
  public void testBasicVariants() {
    BasicMultiValues prop = null;

    prop = new BasicMultiValues();
    assertEquals(BasicMultiValues.DEFAULT_TYPE, prop.getType());
    assertEquals(0, prop.getCount());
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(com.github.haixing_hu.lang.Type)}.
   */
  @Test
  public void testBasicVariantsType() {
    BasicMultiValues prop = null;

    prop = new BasicMultiValues(Type.STRING);
    assertEquals(Type.STRING, prop.getType());
    assertEquals(0, prop.getCount());

    prop = new BasicMultiValues(Type.BOOLEAN);
    assertEquals(Type.BOOLEAN, prop.getType());
    assertEquals(0, prop.getCount());

    prop = new BasicMultiValues(Type.BIG_DECIMAL);
    assertEquals(Type.BIG_DECIMAL, prop.getType());
    assertEquals(0, prop.getCount());

    try {
      final Type type = null;
      prop = new BasicMultiValues(type);
      fail("Should throw a NullPointerException.");
    } catch (final NullPointerException e) {
      // pass
    }
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(boolean)}.
   */
  @Test
  public void testBasicVariantsBoolean() {
    BasicMultiValues prop = null;

    prop = new BasicMultiValues(true);
    assertEquals(Type.BOOLEAN, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(true, prop.getBooleanValue());
    assertTrue(Equality.equals(new boolean[]{true}, prop.getBooleanValues()));

    prop = new BasicMultiValues(false);
    assertEquals(Type.BOOLEAN, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(false, prop.getBooleanValue());
    assertTrue(Equality.equals(new boolean[]{false}, prop.getBooleanValues()));
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(char)}.
   */
  @Test
  public void testBasicVariantsChar() {
    BasicMultiValues prop = null;

    prop = new BasicMultiValues('x');
    assertEquals(Type.CHAR, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals('x', prop.getCharValue());
    assertTrue(Equality.equals(new char[]{'x'}, prop.getCharValues()));

    prop = new BasicMultiValues('y');
    assertEquals(Type.CHAR, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals('y', prop.getCharValue());
    assertTrue(Equality.equals(new char[]{'y'}, prop.getCharValues()));
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(byte)}.
   */
  @Test
  public void testBasicVariantsByte() {
    BasicMultiValues prop = null;
    byte value = 0;

    value = (byte) 0;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BYTE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getByteValue());
    assertTrue(Equality.equals(new byte[]{value}, prop.getByteValues()));

    value = (byte) 10;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BYTE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getByteValue());
    assertTrue(Equality.equals(new byte[]{value}, prop.getByteValues()));

    value = (byte) -100;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BYTE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getByteValue());
    assertTrue(Equality.equals(new byte[]{value}, prop.getByteValues()));

    value = Byte.MIN_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BYTE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getByteValue());
    assertTrue(Equality.equals(new byte[]{value}, prop.getByteValues()));

    value = Byte.MAX_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BYTE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getByteValue());
    assertTrue(Equality.equals(new byte[]{value}, prop.getByteValues()));
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(short)}.
   */
  @Test
  public void testBasicVariantsShort() {
    BasicMultiValues prop = null;
    short value = 0;

    value = (short) 0;
    prop = new BasicMultiValues(value);
    assertEquals(Type.SHORT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getShortValue());
    assertTrue(Equality.equals(new short[]{value}, prop.getShortValues()));

    value = (short) 10;
    prop = new BasicMultiValues(value);
    assertEquals(Type.SHORT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getShortValue());
    assertTrue(Equality.equals(new short[]{value}, prop.getShortValues()));

    value = (short) -100;
    prop = new BasicMultiValues(value);
    assertEquals(Type.SHORT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getShortValue());
    assertTrue(Equality.equals(new short[]{value}, prop.getShortValues()));

    value = Short.MIN_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.SHORT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getShortValue());
    assertTrue(Equality.equals(new short[]{value}, prop.getShortValues()));

    value = Short.MAX_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.SHORT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getShortValue());
    assertTrue(Equality.equals(new short[]{value}, prop.getShortValues()));
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(int)}.
   */
  @Test
  public void testBasicVariantsInt() {
    BasicMultiValues prop = null;
    int value = 0;

    value = 0;
    prop = new BasicMultiValues(value);
    assertEquals(Type.INT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getIntValue());
    assertTrue(Equality.equals(new int[]{value}, prop.getIntValues()));

    value = 10;
    prop = new BasicMultiValues(value);
    assertEquals(Type.INT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getIntValue());
    assertTrue(Equality.equals(new int[]{value}, prop.getIntValues()));

    value = -100;
    prop = new BasicMultiValues(value);
    assertEquals(Type.INT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getIntValue());
    assertTrue(Equality.equals(new int[]{value}, prop.getIntValues()));

    value = Integer.MIN_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.INT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getIntValue());
    assertTrue(Equality.equals(new int[]{value}, prop.getIntValues()));

    value = Integer.MAX_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.INT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getIntValue());
    assertTrue(Equality.equals(new int[]{value}, prop.getIntValues()));
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(long)}.
   */
  @Test
  public void testBasicVariantsLong() {
    BasicMultiValues prop = null;
    long value = 0;

    value = 0;
    prop = new BasicMultiValues(value);
    assertEquals(Type.LONG, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getLongValue());
    assertTrue(Equality.equals(new long[]{value}, prop.getLongValues()));

    value = 10;
    prop = new BasicMultiValues(value);
    assertEquals(Type.LONG, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getLongValue());
    assertTrue(Equality.equals(new long[]{value}, prop.getLongValues()));

    value = -100;
    prop = new BasicMultiValues(value);
    assertEquals(Type.LONG, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getLongValue());
    assertTrue(Equality.equals(new long[]{value}, prop.getLongValues()));

    value = Long.MIN_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.LONG, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getLongValue());
    assertTrue(Equality.equals(new long[]{value}, prop.getLongValues()));

    value = Long.MAX_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.LONG, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getLongValue());
    assertTrue(Equality.equals(new long[]{value}, prop.getLongValues()));
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(float)}.
   */
  @Test
  public void testBasicVariantsFloat() {
    BasicMultiValues prop = null;
    float value = 0;

    value = 0;
    prop = new BasicMultiValues(value);
    assertEquals(Type.FLOAT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getFloatValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new float[]{value}, prop.getFloatValues()));

    value = 10.123f;
    prop = new BasicMultiValues(value);
    assertEquals(Type.FLOAT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getFloatValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new float[]{value}, prop.getFloatValues()));

    value = -3.1415f;
    prop = new BasicMultiValues(value);
    assertEquals(Type.FLOAT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getFloatValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new float[]{value}, prop.getFloatValues()));

    value = Float.MIN_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.FLOAT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getFloatValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new float[]{value}, prop.getFloatValues()));

    value = Float.MIN_NORMAL;
    prop = new BasicMultiValues(value);
    assertEquals(Type.FLOAT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getFloatValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new float[]{value}, prop.getFloatValues()));

    value = Float.MAX_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.FLOAT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getFloatValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new float[]{value}, prop.getFloatValues()));

    value = Float.NaN;
    prop = new BasicMultiValues(value);
    assertEquals(Type.FLOAT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getFloatValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new float[]{value}, prop.getFloatValues()));

    value = Float.NEGATIVE_INFINITY;
    prop = new BasicMultiValues(value);
    assertEquals(Type.FLOAT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getFloatValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new float[]{value}, prop.getFloatValues()));

    value = Float.POSITIVE_INFINITY;
    prop = new BasicMultiValues(value);
    assertEquals(Type.FLOAT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getFloatValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new float[]{value}, prop.getFloatValues()));
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(double)}.
   */
  @Test
  public void testBasicVariantsDouble() {
    BasicMultiValues prop = null;
    double value = 0;

    value = 0;
    prop = new BasicMultiValues(value);
    assertEquals(Type.DOUBLE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getDoubleValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new double[]{value}, prop.getDoubleValues()));

    value = 10.123;
    prop = new BasicMultiValues(value);
    assertEquals(Type.DOUBLE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getDoubleValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new double[]{value}, prop.getDoubleValues()));

    value = -3.1415;
    prop = new BasicMultiValues(value);
    assertEquals(Type.DOUBLE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getDoubleValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new double[]{value}, prop.getDoubleValues()));

    value = Double.MIN_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.DOUBLE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getDoubleValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new double[]{value}, prop.getDoubleValues()));

    value = Double.MIN_NORMAL;
    prop = new BasicMultiValues(value);
    assertEquals(Type.DOUBLE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getDoubleValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new double[]{value}, prop.getDoubleValues()));

    value = Double.MAX_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.DOUBLE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getDoubleValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new double[]{value}, prop.getDoubleValues()));

    value = Double.NaN;
    prop = new BasicMultiValues(value);
    assertEquals(Type.DOUBLE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getDoubleValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new double[]{value}, prop.getDoubleValues()));

    value = Double.NEGATIVE_INFINITY;
    prop = new BasicMultiValues(value);
    assertEquals(Type.DOUBLE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getDoubleValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new double[]{value}, prop.getDoubleValues()));

    value = Double.POSITIVE_INFINITY;
    prop = new BasicMultiValues(value);
    assertEquals(Type.DOUBLE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getDoubleValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new double[]{value}, prop.getDoubleValues()));
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(String)}.
   */
  @Test
  public void testBasicVariantsString() {
    BasicMultiValues prop = null;

    prop = new BasicMultiValues("value1");
    assertEquals(Type.STRING, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals("value1", prop.getStringValue());
    assertArrayEquals(new String[]{"value1"}, prop.getStringValues());

    prop = new BasicMultiValues("");
    assertEquals(Type.STRING, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals("", prop.getStringValue());
    assertArrayEquals(new String[]{""}, prop.getStringValues());

    final String value = null;
    prop = new BasicMultiValues(value);
    assertEquals(Type.STRING, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(null, prop.getStringValue());
    assertArrayEquals(new String[]{null}, prop.getStringValues());
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(java.util.Date)}.
   */
  @Test
  public void testBasicVariantsDate() {
    BasicMultiValues prop = null;
    Date date = new Date();

    prop = new BasicMultiValues(date);
    assertEquals(Type.DATE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(date, prop.getDateValue());
    assertNotSame(date, prop.getDateValue());
    assertArrayEquals(new Date[]{date}, prop.getDateValues());

    date.setTime(200);
    prop = new BasicMultiValues(date);
    assertEquals(Type.DATE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(date, prop.getDateValue());
    assertNotSame(date, prop.getDateValue());
    assertArrayEquals(new Date[]{date}, prop.getDateValues());

    date = null;
    prop = new BasicMultiValues(date);
    assertEquals(Type.DATE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(date, prop.getDateValue());
    assertArrayEquals(new Date[]{date}, prop.getDateValues());
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(byte[])}.
   */
  @Test
  public void testBasicVariantsByteArray() {
    BasicMultiValues prop = null;
    byte[] bytes = new byte[]{ 1, 2, 3 };

    prop = new BasicMultiValues(bytes);
    assertEquals(Type.BYTE_ARRAY, prop.getType());
    assertEquals(1, prop.getCount());
    assertArrayEquals(bytes, prop.getByteArrayValue());
    assertNotSame(bytes, prop.getByteArrayValue());
    assertArrayEquals(new byte[][]{bytes}, prop.getByteArrayValues());

    bytes = new byte[0];
    prop = new BasicMultiValues(bytes);
    assertEquals(Type.BYTE_ARRAY, prop.getType());
    assertEquals(1, prop.getCount());
    assertArrayEquals(bytes, prop.getByteArrayValue());
    assertNotSame(bytes, prop.getByteArrayValue());
    assertArrayEquals(new byte[][]{bytes}, prop.getByteArrayValues());

    bytes = null;
    prop = new BasicMultiValues(bytes);
    assertEquals(Type.BYTE_ARRAY, prop.getType());
    assertEquals(1, prop.getCount());
    assertArrayEquals(bytes, prop.getByteArrayValue());
    assertArrayEquals(new byte[][]{bytes}, prop.getByteArrayValues());
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(Class)}.
   */
  @Test
  public void testBasicVariantsClass() {
    BasicMultiValues prop = null;
    Class<?> cls = String.class;

    prop = new BasicMultiValues(cls);
    assertEquals(Type.CLASS, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(cls, prop.getClassValue());
    assertSame(cls, prop.getClassValue());
    assertArrayEquals(new Class<?>[]{cls}, prop.getClassValues());

    cls = Boolean.class;
    prop = new BasicMultiValues(cls);
    assertEquals(Type.CLASS, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(cls, prop.getClassValue());
    assertSame(cls, prop.getClassValue());
    assertArrayEquals(new Class<?>[]{cls}, prop.getClassValues());

    cls = null;
    prop = new BasicMultiValues(cls);
    assertEquals(Type.CLASS, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(cls, prop.getClassValue());
    assertSame(cls, prop.getClassValue());
    assertArrayEquals(new Class<?>[]{cls}, prop.getClassValues());
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(java.math.BigInteger)}.
   */
  @Test
  public void testBasicVariantsBigInteger() {
    BasicMultiValues prop = null;
    BigInteger value = null;

    value = BigInteger.ZERO;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BIG_INTEGER, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getBigIntegerValue());
    assertArrayEquals(new BigInteger[]{value}, prop.getBigIntegerValues());

    value = BigInteger.TEN;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BIG_INTEGER, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getBigIntegerValue());
    assertArrayEquals(new BigInteger[]{value}, prop.getBigIntegerValues());

    value = null;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BIG_INTEGER, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getBigIntegerValue());
    assertArrayEquals(new BigInteger[]{value}, prop.getBigIntegerValues());
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(java.math.BigDecimal)}.
   */
  @Test
  public void testBasicVariantsBigDecimal() {
    BasicMultiValues prop = null;
    BigDecimal value = null;

    value = BigDecimal.ZERO;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BIG_DECIMAL, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getBigDecimalValue());
    assertArrayEquals(new BigDecimal[]{value}, prop.getBigDecimalValues());

    value = BigDecimal.TEN;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BIG_DECIMAL, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getBigDecimalValue());
    assertArrayEquals(new BigDecimal[]{value}, prop.getBigDecimalValues());

    value = null;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BIG_DECIMAL, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getBigDecimalValue());
    assertArrayEquals(new BigDecimal[]{value}, prop.getBigDecimalValues());
  }

  /**
   * Test method for {@link BasicMultiValues#getType()} and {@link BasicMultiValues#setType(Type)}.
   */
  @Test
  public void testGetSetType() {
    final BasicMultiValues prop = new BasicMultiValues("prop1");
    assertEquals(BasicMultiValues.DEFAULT_TYPE, prop.getType());

    prop.setType(Type.BIG_DECIMAL);
    assertEquals(Type.BIG_DECIMAL, prop.getType());

    try {
      prop.setType(null);
      fail("Should throw NullPointerException.");
    } catch (final NullPointerException e) {
      // pass
    }
  }


  /**
   * Test method for {@link BasicMultiValues#getCount()}.
   */
  @Test
  public void testSize() {
    final BasicMultiValues prop = new BasicMultiValues();
    assertEquals(0, prop.getCount());
    prop.addStringValue("value1");
    assertEquals(1, prop.getCount());
    prop.addStringValue("value2");
    assertEquals(2, prop.getCount());
    prop.addStringValue("value3");
    assertEquals(3, prop.getCount());
  }

  /**
   * Test method for {@link BasicMultiValues#isEmpty()}.
   */
  @Test
  public void testIsEmpty() {
    final BasicMultiValues prop = new BasicMultiValues();
    assertEquals(true, prop.isEmpty());
    prop.addStringValue("value1");
    assertEquals(false, prop.isEmpty());
    prop.addStringValue("value2");
    assertEquals(false, prop.isEmpty());
    prop.addStringValue("value3");
    assertEquals(false, prop.isEmpty());
  }

  /**
   * Test method for {@link BasicMultiValues#clear()}.
   */
  @Test
  public void testClear() {
    final BasicMultiValues prop = new BasicMultiValues();
    assertEquals(true, prop.isEmpty());
    prop.clear();
    assertEquals(true, prop.isEmpty());
    prop.addStringValue("value1");
    assertEquals(false, prop.isEmpty());
    prop.clear();
    assertEquals(true, prop.isEmpty());
    prop.addStringValue("value2");
    assertEquals(false, prop.isEmpty());
    prop.addStringValue("value3");
    assertEquals(false, prop.isEmpty());
    prop.clear();
    assertEquals(true, prop.isEmpty());
  }
//
//  @Test
//  public void testXmlSerialization() throws XmlException {
//    BasicMultiValues prop1 = null;
//    BasicMultiValues prop2 = null;
//    String xml = null;
//
//    prop1 = new BasicMultiValues();
//    xml = XmlSerialization.serialize(BasicMultiValues.class, prop1);
//    System.out.println(xml);
//    prop2 = XmlSerialization.deserialize(BasicMultiValues.class, xml);
//    assertEquals(prop1, prop2);
//
//    prop1 = new BasicMultiValues("prop1");
//    xml = XmlSerialization.serialize(BasicMultiValues.class, prop1);
//    System.out.println(xml);
//    prop2 = XmlSerialization.deserialize(BasicMultiValues.class, xml);
//    assertEquals(prop1, prop2);
//
//    // single String value
//    prop1.setStringValue("value1");
//    xml = XmlSerialization.serialize(BasicMultiValues.class, prop1);
//    System.out.println(xml);
//    prop2 = XmlSerialization.deserialize(BasicMultiValues.class, xml);
//    assertEquals(prop1, prop2);
//
//    // multiple String values with preserving space
//    prop1.addStringValue(" value2 ");
//    xml = XmlSerialization.serialize(BasicMultiValues.class, prop1);
//    System.out.println(xml);
//    prop2 = XmlSerialization.deserialize(BasicMultiValues.class, xml);
//    assertEquals(prop1, prop2);
//
//    // single int value
//    prop1.setIntValue(1);
//    xml = XmlSerialization.serialize(BasicMultiValues.class, prop1);
//    System.out.println(xml);
//    prop2 = XmlSerialization.deserialize(BasicMultiValues.class, xml);
//    assertEquals(prop1, prop2);
//
//    // multiple int values
//    prop1.addIntValue(1000);
//    xml = XmlSerialization.serialize(BasicMultiValues.class, prop1);
//    System.out.println(xml);
//    prop2 = XmlSerialization.deserialize(BasicMultiValues.class, xml);
//    assertEquals(prop1, prop2);
//
//    // single byte[] value
//    prop1.setByteArrayValue(new byte[]{1, 2, 3});
//    xml = XmlSerialization.serialize(BasicMultiValues.class, prop1);
//    System.out.println(xml);
//    prop2 = XmlSerialization.deserialize(BasicMultiValues.class, xml);
//    assertEquals(prop1, prop2);
//
//    // multiple byte[] values
//    prop1.addByteArrayValue(new byte[]{4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18});
//    xml = XmlSerialization.serialize(BasicMultiValues.class, prop1);
//    System.out.println(xml);
//    prop2 = XmlSerialization.deserialize(BasicMultiValues.class, xml);
//    assertEquals(prop1, prop2);
//
//  }

  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getValue()}.
  //   */
  //  @Test
  //  public void testGetValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getValues()}.
  //   */
  //  @Test
  //  public void testGetValues() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getStringValue()}.
  //   */
  //  @Test
  //  public void testGetStringValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getStringValues()}.
  //   */
  //  @Test
  //  public void testGetStringValues() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getBoolValue()}.
  //   */
  //  @Test
  //  public void testGetBooleanValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getBoolValues()}.
  //   */
  //  @Test
  //  public void testGetBooleanValues() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getCharValue()}.
  //   */
  //  @Test
  //  public void testGetCharValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getCharValues()}.
  //   */
  //  @Test
  //  public void testGetCharValues() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getByteValue()}.
  //   */
  //  @Test
  //  public void testGetByteValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getByteValues()}.
  //   */
  //  @Test
  //  public void testGetByteValues() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getShortValue()}.
  //   */
  //  @Test
  //  public void testGetShortValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getShortValues()}.
  //   */
  //  @Test
  //  public void testGetShortValues() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getIntValue()}.
  //   */
  //  @Test
  //  public void testGetIntValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getIntValues()}.
  //   */
  //  @Test
  //  public void testGetIntValues() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getLongValue()}.
  //   */
  //  @Test
  //  public void testGetLongValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getLongValues()}.
  //   */
  //  @Test
  //  public void testGetLongValues() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getFloatValue()}.
  //   */
  //  @Test
  //  public void testGetFloatValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getFloatValues()}.
  //   */
  //  @Test
  //  public void testGetFloatValues() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getDoubleValue()}.
  //   */
  //  @Test
  //  public void testGetDoubleValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getDoubleValues()}.
  //   */
  //  @Test
  //  public void testGetDoubleValues() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getByteArrayValue()}.
  //   */
  //  @Test
  //  public void testgetByteArrayValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getByteArrayValues()}.
  //   */
  //  @Test
  //  public void testgetByteArrayValues() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getBigIntegerValue()}.
  //   */
  //  @Test
  //  public void testgetBigIntegerValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getBigIntegerValues()}.
  //   */
  //  @Test
  //  public void testgetBigIntegerValues() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getBigDecimalValue()}.
  //   */
  //  @Test
  //  public void testgetBigDecimalValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getBigDecimalValues()}.
  //   */
  //  @Test
  //  public void testgetBigDecimalValues() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getDateValue()}.
  //   */
  //  @Test
  //  public void testgetDateValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getDateValues()}.
  //   */
  //  @Test
  //  public void testgetDateValues() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getClass()}.
  //   */
  //  @Test
  //  public void testGetClassValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#getClasss()}.
  //   */
  //  @Test
  //  public void testGetClassValues() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setValue(Type, Object)}.
  //   */
  //  @Test
  //  public void testSetValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setString(String)}.
  //   */
  //  @Test
  //  public void testSetStringValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setBoolean(boolean)}.
  //   */
  //  @Test
  //  public void testSetBooleanValueBoolean() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setBoolean(Boolean)}.
  //   */
  //  @Test
  //  public void testSetBooleanValueBoolean1() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setChar(char)}.
  //   */
  //  @Test
  //  public void testSetCharValueChar() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setChar(Character)}.
  //   */
  //  @Test
  //  public void testSetCharValueCharacter() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setByte(byte)}.
  //   */
  //  @Test
  //  public void testSetByteValueByte() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setByte(Byte)}.
  //   */
  //  @Test
  //  public void testSetByteValueByte1() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setShort(short)}.
  //   */
  //  @Test
  //  public void testSetShortValueShort() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setShort(Short)}.
  //   */
  //  @Test
  //  public void testSetShortValueShort1() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setInt(int)}.
  //   */
  //  @Test
  //  public void testSetIntValueInt() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setInt(Integer)}.
  //   */
  //  @Test
  //  public void testSetIntValueInteger() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setLong(long)}.
  //   */
  //  @Test
  //  public void testSetLongValueLong() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setLong(Long)}.
  //   */
  //  @Test
  //  public void testSetLongValueLong1() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setFloat(float)}.
  //   */
  //  @Test
  //  public void testSetFloatValueFloat() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setFloat(Float)}.
  //   */
  //  @Test
  //  public void testSetFloatValueFloat1() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setDouble(double)}.
  //   */
  //  @Test
  //  public void testSetDoubleValueDouble() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setDouble(Double)}.
  //   */
  //  @Test
  //  public void testSetDoubleValueDouble1() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setBigIntegerValue(java.math.BigInteger)}.
  //   */
  //  @Test
  //  public void testSetBigIntegerValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setBigDecimalValue(java.math.BigDecimal)}.
  //   */
  //  @Test
  //  public void testSetBigDecimalValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setDate(java.util.Date)}.
  //   */
  //  @Test
  //  public void testSetDateValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#setByteArrayValue(byte[])}.
  //   */
  //  @Test
  //  public void testSetByteArrayValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addValue(Type, Object)}.
  //   */
  //  @Test
  //  public void testAddValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addBoolValue(boolean)}.
  //   */
  //  @Test
  //  public void testaddBoolValueBoolean() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addBoolValue(Boolean)}.
  //   */
  //  @Test
  //  public void testaddBoolValueBoolean1() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addCharValue(char)}.
  //   */
  //  @Test
  //  public void testaddCharValueChar() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addCharValue(Character)}.
  //   */
  //  @Test
  //  public void testaddCharValueCharacter() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addByteValue(byte)}.
  //   */
  //  @Test
  //  public void testaddByteValueByte() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addByteValue(Byte)}.
  //   */
  //  @Test
  //  public void testaddByteValueByte1() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addShortValue(short)}.
  //   */
  //  @Test
  //  public void testaddShortValueShort() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addShortValue(Short)}.
  //   */
  //  @Test
  //  public void testaddShortValueShort1() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addIntValue(int)}.
  //   */
  //  @Test
  //  public void testaddIntValueInt() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addIntValue(Integer)}.
  //   */
  //  @Test
  //  public void testaddIntValueInteger() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addLongValue(long)}.
  //   */
  //  @Test
  //  public void testaddLongValueLong() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addLongValue(Long)}.
  //   */
  //  @Test
  //  public void testaddLongValueLong1() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addFloatValue(float)}.
  //   */
  //  @Test
  //  public void testaddFloatValueFloat() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addFloatValue(Float)}.
  //   */
  //  @Test
  //  public void testaddFloatValueFloat1() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addDoubleValue(double)}.
  //   */
  //  @Test
  //  public void testaddDoubleValueDouble() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addDoubleValue(Double)}.
  //   */
  //  @Test
  //  public void testaddDoubleValueDouble1() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addBigIntegerValue(java.math.BigInteger)}.
  //   */
  //  @Test
  //  public void testAddBigIntegerValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addBigDecimalValue(java.math.BigDecimal)}.
  //   */
  //  @Test
  //  public void testAddBigDecimalValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addDate(java.util.Date)}.
  //   */
  //  @Test
  //  public void testAddDateValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#addByteValueArrayValue(byte[])}.
  //   */
  //  @Test
  //  public void testaddByteValueArrayValue() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#readFrom(java.io.DataInput)}.
  //   */
  //  @Test
  //  public void testReadFrom() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#writeTo(java.io.DataOutput)}.
  //   */
  //  @Test
  //  public void testWriteTo() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#assign()}.
  //   */
  //  @Test
  //  public void testAssign() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#clone()}.
  //   */
  //  @Test
  //  public void testClone() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#swap()}.
  //   */
  //  @Test
  //  public void testSwap() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#equals(Object)}.
  //   */
  //  @Test
  //  public void testEqualsObject() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#compareTo(BasicMultiValues)}.
  //   */
  //  @Test
  //  public void testCompareTo() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#hashCode()}.
  //   */
  //  @Test
  //  public void testHashCode() {
  //    fail("Not yet implemented");
  //  }
  //
  //  /**
  //   * Test method for {@link BasicMultiValues#toString()}.
  //   */
  //  @Test
  //  public void testToString() {
  //    fail("Not yet implemented");
  //  }

//
//    private void verifyPropertiesXml(final String xmlFile, final BasicMultiValues[] expectedProps)
//          throws XmlException {
//      Document doc = null;
//      try {
//        doc = XmlUtils.parse(xmlFile, BasicMultiValuesTest.class);
//      } catch (final XmlException e) {
//        fail("failed to parse the XML file: " + xmlFile + " " + e.getMessage());
//      }
//      final Element root = doc.getDocumentElement();
//      final List<Element> propNodes = XmlUtils.getChildren(root, null);
//
//      if (propNodes.size() != expectedProps.length) {
//        fail("The number of property node in the XML file does not" +
//        		" confirm to the number of expected properties.");
//      }
//
//      BasicMultiValues prop = null;
//      int i = 0;
//      for (final Element propNode : propNodes) {
//        final String xmlStr = XmlUtils.toString(propNode);
//        System.out.println("Verifying the XML node:\n" + xmlStr);
//        final BasicMultiValues expected = expectedProps[i++];
//        if (expected == null) {
//          // there should be a parsing error
//          try {
//            prop = XmlSerialization.deserialize(BasicMultiValues.class, propNode);
//            fail("There should be a parsing error for the " + i
//                + "-th XML node:\n" + xmlStr);
//          } catch (final XmlException e) {
//            System.out.println("Failed to parse the XML node: " + e.getMessage()
//                + "\nBut this IS what's desired.");
//            // passed
//          }
//        } else {
//          try {
//            prop = XmlSerialization.deserialize(BasicMultiValues.class, propNode);
//          } catch (final XmlException e) {
//            fail("There MUST NOT be a parsing error for the " + i
//                + "-th XML node:\n" + e.getMessage());
//          }
//          assertEquals(expected, prop);
//        }
//      }
//    }

//
//  /**
//   * Test method for {@link BasicMultiValues#fromXml(org.w3c.dom.Element)}.
//   * @throws XmlException
//   */
//  @Test
//  public void testFromXml() throws XmlException {
//    BasicMultiValues[] expected = null;
//
//
//    expected = new BasicMultiValues[]{
//      new BasicMultiValues("prop0", Type.STRING),
//      new BasicMultiValues("prop1", Type.STRING),
//      new BasicMultiValues("prop2", Type.STRING)
//          .addStringValue("value2"),
//      new BasicMultiValues("prop3", Type.STRING)
//          .addStringValue(" value3"),
//      new BasicMultiValues("prop4", Type.STRING)
//          .addStringValue("value4"),
//      new BasicMultiValues("prop5", Type.STRING)
//          .addStringValue("value5-1")
//          .addStringValue("value5-2")
//          .addStringValue("value5-3")
//          .addStringValue("value5-4 "),
//    };
//    verifyPropertiesXml("string-properties.xml", expected);
//
//    expected = new BasicMultiValues[]{
//      new BasicMultiValues("prop0", Type.BOOLEAN),
//      new BasicMultiValues("prop1", Type.BOOLEAN)
//          .addBoolValue(true),
//      new BasicMultiValues("prop2", Type.BOOLEAN)
//          .addBoolValue(false),
//      new BasicMultiValues("prop3", Type.BOOLEAN)
//          .addBoolValue(false)
//          .addBoolValue(true)
//          .addBoolValue(false)
//          .addBoolValue(true)
//          .addBoolValue(false),
//    };
//    verifyPropertiesXml("boolean-properties.xml", expected);
//
//    expected = new BasicMultiValues[]{
//      new BasicMultiValues("prop0", Type.BYTE),
//      new BasicMultiValues("prop1", Type.BYTE),
//      new BasicMultiValues("prop2", Type.BYTE)
//          .addByteValue((byte)10),
//      new BasicMultiValues("prop3", Type.BYTE)
//          .addByteValue((byte)-1),
//      null,
//      null,
//      new BasicMultiValues("prop6", Type.BYTE)
//          .addByteValue((byte)10),
//      new BasicMultiValues("prop7", Type.BYTE)
//          .addByteValue((byte)26),
//      new BasicMultiValues("prop8", Type.BYTE)
//          .addByteValue((byte)0)
//          .addByteValue((byte)1)
//          .addByteValue((byte)-128)
//          .addByteValue((byte)127),
//    };
//    verifyPropertiesXml("byte-properties.xml", expected);
//
//    expected = new BasicMultiValues[]{
//      new BasicMultiValues("prop0", Type.SHORT),
//      new BasicMultiValues("prop1", Type.SHORT),
//      new BasicMultiValues("prop2", Type.SHORT)
//          .addShortValue((short)10),
//      new BasicMultiValues("prop3", Type.SHORT)
//          .addShortValue((short)-1),
//      null,
//      null,
//      new BasicMultiValues("prop6", Type.SHORT)
//          .addShortValue((short)10),
//      new BasicMultiValues("prop7", Type.SHORT)
//          .addShortValue((short)26),
//      new BasicMultiValues("prop8", Type.SHORT)
//          .addShortValue((short)0)
//          .addShortValue((short)1)
//          .addShortValue((short)-32768)
//          .addShortValue((short)32767),
//    };
//    verifyPropertiesXml("short-properties.xml", expected);
//
//    expected = new BasicMultiValues[]{
//      new BasicMultiValues("prop0", Type.INT),
//      new BasicMultiValues("prop1", Type.INT),
//      new BasicMultiValues("prop2", Type.INT)
//          .addIntValue(10),
//      new BasicMultiValues("prop3", Type.INT)
//          .addIntValue(-1),
//      null,
//      null,
//      new BasicMultiValues("prop6", Type.INT)
//          .addIntValue(10),
//      new BasicMultiValues("prop7", Type.INT)
//          .addIntValue(26),
//      new BasicMultiValues("prop8", Type.INT)
//          .addIntValue(0)
//          .addIntValue(1)
//          .addIntValue(-2147483648)
//          .addIntValue(2147483647),
//    };
//    verifyPropertiesXml("int-properties.xml", expected);
//
//    expected = new BasicMultiValues[]{
//      new BasicMultiValues("prop0", Type.LONG),
//      new BasicMultiValues("prop1", Type.LONG),
//      new BasicMultiValues("prop2", Type.LONG)
//          .addLongValue(10L),
//      new BasicMultiValues("prop3", Type.LONG)
//          .addLongValue(-1L),
//      null,
//      null,
//      new BasicMultiValues("prop6", Type.LONG)
//          .addLongValue(10L),
//      new BasicMultiValues("prop7", Type.LONG)
//          .addLongValue(26L),
//      new BasicMultiValues("prop8", Type.LONG)
//          .addLongValue(0L)
//          .addLongValue(1L)
//          .addLongValue(-9223372036854775808L)
//          .addLongValue(9223372036854775807L),
//    };
//    verifyPropertiesXml("long-properties.xml", expected);
//
//    expected = new BasicMultiValues[]{
//      new BasicMultiValues("prop0", Type.FLOAT),
//      new BasicMultiValues("prop1", Type.FLOAT),
//      new BasicMultiValues("prop2", Type.FLOAT)
//          .addFloatValue(3.14f),
//      new BasicMultiValues("prop3", Type.FLOAT)
//          .addFloatValue(-0.618f),
//      new BasicMultiValues("prop4", Type.FLOAT)
//          .addFloatValue(Float.POSITIVE_INFINITY),
//      null,
//      new BasicMultiValues("prop6", Type.FLOAT)
//          .addFloatValue(1.414e8f),
//      new BasicMultiValues("prop7", Type.FLOAT)
//          .addFloatValue(0.618e-8f),
//      new BasicMultiValues("prop8", Type.FLOAT)
//          .addFloatValue(0L)
//          .addFloatValue(1L)
//          .addFloatValue(0x0.000002p-126f)
//          .addFloatValue(0x1.fffffp+127f),
//    };
//    verifyPropertiesXml("float-properties.xml", expected);
//
//    expected = new BasicMultiValues[]{
//      new BasicMultiValues("prop0", Type.DOUBLE),
//      new BasicMultiValues("prop1", Type.DOUBLE),
//      new BasicMultiValues("prop2", Type.DOUBLE)
//          .addDoubleValue(3.14),
//      new BasicMultiValues("prop3", Type.DOUBLE)
//          .addDoubleValue(-0.618),
//      new BasicMultiValues("prop4", Type.DOUBLE)
//          .addDoubleValue(Double.POSITIVE_INFINITY),
//      null,
//      new BasicMultiValues("prop6", Type.DOUBLE)
//          .addDoubleValue(1.414e8),
//      new BasicMultiValues("prop7", Type.DOUBLE)
//          .addDoubleValue(0.618e-8),
//      new BasicMultiValues("prop8", Type.DOUBLE)
//          .addDoubleValue(0L)
//          .addDoubleValue(1L)
//          .addDoubleValue(0x0.0000000000001p-1022)
//          .addDoubleValue(0x1.fffffffffffffp+1023),
//    };
//    verifyPropertiesXml("double-properties.xml", expected);
//
//
//    expected = new BasicMultiValues[]{
//      new BasicMultiValues("prop0", Type.CLASS),
//      new BasicMultiValues("prop1", Type.CLASS),
//      new BasicMultiValues("prop2", Type.CLASS)
//          .addClassValue(String.class),
//      new BasicMultiValues("prop3", Type.CLASS)
//          .addClassValue(Class.class),
//      null,
//      new BasicMultiValues("prop5", Type.CLASS)
//          .addClassValue(String.class)
//          .addClassValue(Date.class)
//          .addClassValue(BasicValue.class)
//          .addClassValue(BasicMultiValues.class),
//    };
//    verifyPropertiesXml("class-properties.xml", expected);
//
//    // TODO: test the remain data types ...
//  }

}
