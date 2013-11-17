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

import org.junit.Test;

import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.lang.TypeConvertException;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.util.value.BasicValue;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for the {@link BasicValue} class.
 *
 * @author Haixing Hu
 */
public class BasicValueTest {

  /**
   * Test method for {@link BasicValue#getValueAsString()}.
   */
  @Test
  public void testGetValueAsString() {
    BasicValue var = null;

    var = new BasicValue("hello");
    assertEquals("hello", var.getValueAsString());

    var = new BasicValue(true);
    assertEquals("true", var.getValueAsString());

    var = new BasicValue((byte)123);
    assertEquals("123", var.getValueAsString());

    var = new BasicValue((short)123);
    assertEquals("123", var.getValueAsString());

    var = new BasicValue(1234567);
    assertEquals("1234567", var.getValueAsString());

    var = new BasicValue(12345678980L);
    assertEquals("12345678980", var.getValueAsString());

    var = new BasicValue(3.141592f);
    assertEquals("3.141592", var.getValueAsString());

    var = new BasicValue(0.618123456789);
    assertEquals("0.618123456789", var.getValueAsString());


    var.setStringValue("hello");
    assertEquals("hello", var.getValueAsString());

    var.setBooleanValue(true);
    assertEquals("true", var.getValueAsString());

    var.setByteValue((byte)123);
    assertEquals("123", var.getValueAsString());

    var.setShortValue((short)123);
    assertEquals("123", var.getValueAsString());

    var.setIntValue(1234567);
    assertEquals("1234567", var.getValueAsString());

    var.setLongValue(12345678980L);
    assertEquals("12345678980", var.getValueAsString());

    var.setFloatValue(3.141592f);
    assertEquals("3.141592", var.getValueAsString());

    var.setDoubleValue(0.618123456789);
    assertEquals("0.618123456789", var.getValueAsString());
  }

  /**
   * Test method for {@link BasicValue#getValueAsBoolean()}.
   * @throws TypeConvertException
   */
  @Test
  public void testAsBoolean() throws TypeConvertException {
    BasicValue var = null;

    var = new BasicValue("true");
    assertEquals(true, var.getValueAsBoolean());

    var = new BasicValue("False");
    assertEquals(false, var.getValueAsBoolean());

    var = new BasicValue("other");
    assertEquals(false, var.getValueAsBoolean());

    var = new BasicValue(true);
    assertEquals(true, var.getValueAsBoolean());

    var = new BasicValue(false);
    assertEquals(false, var.getValueAsBoolean());

    var = new BasicValue((byte)123);
    assertEquals(true, var.getValueAsBoolean());

    var = new BasicValue((byte)0);
    assertEquals(false, var.getValueAsBoolean());

    var = new BasicValue((short)123);
    assertEquals(true, var.getValueAsBoolean());

    var = new BasicValue((short)0);
    assertEquals(false, var.getValueAsBoolean());

    var = new BasicValue(1234567);
    assertEquals(true, var.getValueAsBoolean());

    var = new BasicValue(0);
    assertEquals(false, var.getValueAsBoolean());

    var = new BasicValue(12345678980L);
    assertEquals(true, var.getValueAsBoolean());

    var = new BasicValue(0L);
    assertEquals(false, var.getValueAsBoolean());

    var = new BasicValue(3.1415926f);
    assertEquals(true, var.getValueAsBoolean());

    var = new BasicValue(0.0f);
    assertEquals(false, var.getValueAsBoolean());

    var = new BasicValue(0.618123456789);
    assertEquals(true, var.getValueAsBoolean());

    var = new BasicValue(0.0);
    assertEquals(false, var.getValueAsBoolean());

    var.setStringValue("true");
    assertEquals(true, var.getValueAsBoolean());

    var.setStringValue("False");
    assertEquals(false, var.getValueAsBoolean());

    var.setStringValue("other");
    assertEquals(false, var.getValueAsBoolean());

    var.setBooleanValue(true);
    assertEquals(true, var.getValueAsBoolean());

    var.setBooleanValue(false);
    assertEquals(false, var.getValueAsBoolean());

    var.setByteValue((byte)123);
    assertEquals(true, var.getValueAsBoolean());

    var.setByteValue((byte)0);
    assertEquals(false, var.getValueAsBoolean());

    var.setShortValue((short)123);
    assertEquals(true, var.getValueAsBoolean());

    var.setShortValue((short)0);
    assertEquals(false, var.getValueAsBoolean());

    var.setIntValue(1234567);
    assertEquals(true, var.getValueAsBoolean());

    var.setIntValue(0);
    assertEquals(false, var.getValueAsBoolean());

    var.setLongValue(12345678980L);
    assertEquals(true, var.getValueAsBoolean());

    var.setLongValue(0L);
    assertEquals(false, var.getValueAsBoolean());

    var.setFloatValue(3.1415926f);
    assertEquals(true, var.getValueAsBoolean());

    var.setFloatValue(0.0f);
    assertEquals(false, var.getValueAsBoolean());

    var.setDoubleValue(0.618123456789);
    assertEquals(true, var.getValueAsBoolean());

    var.setDoubleValue(0.0);
    assertEquals(false, var.getValueAsBoolean());
  }

  /**
   * Test method for {@link BasicValue#getValueAsByte()}.
   * @throws TypeConvertException
   */
  @Test
  public void testAsByte() throws TypeConvertException {
    BasicValue var = null;

    var = new BasicValue("123");
    assertEquals((byte)123, var.getValueAsByte());

    var = new BasicValue(true);
    assertEquals((byte)1, var.getValueAsByte());

    var = new BasicValue(false);
    assertEquals((byte)0, var.getValueAsByte());

    var = new BasicValue((byte)123);
    assertEquals((byte)123, var.getValueAsByte());

    var = new BasicValue((short)12345);
    assertEquals((byte)12345, var.getValueAsByte());

    var = new BasicValue(1234567);
    assertEquals((byte)1234567, var.getValueAsByte());

    var = new BasicValue(12345678980L);
    assertEquals((byte)12345678980L, var.getValueAsByte());

    var = new BasicValue(3.1415926f);
    assertEquals((byte)3.1415926f, var.getValueAsByte());

    var = new BasicValue(0.618123456789);
    assertEquals((byte)0.618123456789, var.getValueAsByte());

    var.setStringValue("123");
    assertEquals((byte)123, var.getValueAsByte());

    var.setBooleanValue(true);
    assertEquals((byte)1, var.getValueAsByte());

    var.setBooleanValue(false);
    assertEquals((byte)0, var.getValueAsByte());

    var.setByteValue((byte)123);
    assertEquals((byte)123, var.getValueAsByte());

    var.setShortValue((short)12345);
    assertEquals((byte)12345, var.getValueAsByte());

    var.setIntValue(1234567);
    assertEquals((byte)1234567, var.getValueAsByte());

    var.setLongValue(12345678980L);
    assertEquals((byte)12345678980L, var.getValueAsByte());

    var.setFloatValue(3.1415926f);
    assertEquals((byte)3.1415926f, var.getValueAsByte());

    var.setDoubleValue(0.618123456789);
    assertEquals((byte)0.618123456789, var.getValueAsByte());
  }

  /**
   * Test method for {@link BasicValue#getValueAsShort()}.
   * @throws TypeConvertException
   */
  @Test
  public void testAsShort() throws TypeConvertException {
    BasicValue var = null;

    var = new BasicValue("123");
    assertEquals((short)123, var.getValueAsShort());

    var = new BasicValue(true);
    assertEquals((short)1, var.getValueAsShort());

    var = new BasicValue(false);
    assertEquals((short)0, var.getValueAsShort());

    var = new BasicValue((byte)123);
    assertEquals((short)123, var.getValueAsShort());

    var = new BasicValue((short)12345);
    assertEquals((short)12345, var.getValueAsShort());

    var = new BasicValue(1234567);
    assertEquals((short)1234567, var.getValueAsShort());

    var = new BasicValue(12345678980L);
    assertEquals((short)12345678980L, var.getValueAsShort());

    var = new BasicValue(3.1415926f);
    assertEquals((short)3.1415926f, var.getValueAsShort());

    var = new BasicValue(0.618123456789);
    assertEquals((short)0.618123456789, var.getValueAsShort());

    var.setStringValue("123");
    assertEquals((short)123, var.getValueAsShort());

    var.setBooleanValue(true);
    assertEquals((short)1, var.getValueAsShort());

    var.setBooleanValue(false);
    assertEquals((short)0, var.getValueAsShort());

    var.setByteValue((byte)123);
    assertEquals((short)123, var.getValueAsShort());

    var.setShortValue((short)12345);
    assertEquals((short)12345, var.getValueAsShort());

    var.setDoubleValue(1234567);
    assertEquals((short)1234567, var.getValueAsShort());

    var.setLongValue(12345678980L);
    assertEquals((short)12345678980L, var.getValueAsShort());

    var.setFloatValue(3.1415926f);
    assertEquals((short)3.1415926f, var.getValueAsShort());

    var.setDoubleValue(0.618123456789);
    assertEquals((short)0.618123456789, var.getValueAsShort());
  }

  /**
   * Test method for {@link BasicValue#getValueAsInt()}.
   * @throws TypeConvertException
   */
  @Test
  public void testAsInt() throws TypeConvertException {
    BasicValue var = null;

    var = new BasicValue("123456");
    assertEquals(123456, var.getValueAsInt());

    var = new BasicValue(true);
    assertEquals(1, var.getValueAsInt());

    var = new BasicValue(false);
    assertEquals(0, var.getValueAsInt());

    var = new BasicValue((byte)123);
    assertEquals(123, var.getValueAsInt());

    var = new BasicValue((short)12345);
    assertEquals(12345, var.getValueAsInt());

    var = new BasicValue(1234567);
    assertEquals(1234567, var.getValueAsInt());

    var = new BasicValue(12345678980L);
    assertEquals((int)12345678980L, var.getValueAsInt());

    var = new BasicValue(3.1415926f);
    assertEquals((int)3.1415926f, var.getValueAsInt());

    var = new BasicValue(0.618123456789);
    assertEquals((int)0.618123456789, var.getValueAsInt());

    var.setStringValue("123456");
    assertEquals(123456, var.getValueAsInt());

    var.setBooleanValue(true);
    assertEquals(1, var.getValueAsInt());

    var.setBooleanValue(false);
    assertEquals(0, var.getValueAsInt());

    var.setByteValue((byte)123);
    assertEquals(123, var.getValueAsInt());

    var.setShortValue((short)12345);
    assertEquals(12345, var.getValueAsInt());

    var.setDoubleValue(1234567);
    assertEquals(1234567, var.getValueAsInt());

    var.setLongValue(12345678980L);
    assertEquals((int)12345678980L, var.getValueAsInt());

    var.setFloatValue(3.1415926f);
    assertEquals((int)3.1415926f, var.getValueAsInt());

    var.setDoubleValue(0.618123456789);
    assertEquals((int)0.618123456789, var.getValueAsInt());
  }

  /**
   * Test method for {@link BasicValue#getValueAsLong()}.
   * @throws TypeConvertException
   */
  @Test
  public void testAsLong() throws TypeConvertException {
    BasicValue var = null;

    var = new BasicValue("1234567890123");
    assertEquals(1234567890123L, var.getValueAsLong());

    var = new BasicValue(true);
    assertEquals(1, var.getValueAsLong());

    var = new BasicValue(false);
    assertEquals(0, var.getValueAsLong());

    var = new BasicValue((byte)123);
    assertEquals(123, var.getValueAsLong());

    var = new BasicValue((short)12345);
    assertEquals(12345, var.getValueAsLong());

    var = new BasicValue(1234567);
    assertEquals(1234567, var.getValueAsLong());

    var = new BasicValue(12345678980L);
    assertEquals(12345678980L, var.getValueAsLong());

    var = new BasicValue(3.1415926f);
    assertEquals((long)3.1415926f, var.getValueAsLong());

    var = new BasicValue(0.618123456789);
    assertEquals((long)0.618123456789, var.getValueAsLong());

    var.setStringValue("1234567890123");
    assertEquals(1234567890123L, var.getValueAsLong());

    var.setBooleanValue(true);
    assertEquals(1, var.getValueAsLong());

    var.setBooleanValue(false);
    assertEquals(0, var.getValueAsLong());

    var.setByteValue((byte)123);
    assertEquals(123, var.getValueAsLong());

    var.setShortValue((short)12345);
    assertEquals(12345, var.getValueAsLong());

    var.setDoubleValue(1234567);
    assertEquals(1234567, var.getValueAsLong());

    var.setLongValue(12345678980L);
    assertEquals(12345678980L, var.getValueAsLong());

    var.setFloatValue(3.1415926f);
    assertEquals((long)3.1415926f, var.getValueAsLong());

    var.setDoubleValue(0.618123456789);
    assertEquals((long)0.618123456789, var.getValueAsLong());
  }

  /**
   * Test method for {@link BasicValue#getValueAsFloat()}.
   * @throws TypeConvertException
   */
  @Test
  public void testAsFloat() throws TypeConvertException {
    final float epsilon = 0.0000001f;
    BasicValue var = null;

    var = new BasicValue("1.234567890123");
    assertEquals(1.234567890123f, var.getValueAsFloat(), epsilon);

    var = new BasicValue(true);
    assertEquals(1.0f, var.getValueAsFloat(), epsilon);

    var = new BasicValue(false);
    assertEquals(0.0f, var.getValueAsFloat(), epsilon);

    var = new BasicValue((byte)123);
    assertEquals(123.0f, var.getValueAsFloat(), epsilon);

    var = new BasicValue((short)12345);
    assertEquals(12345.0f, var.getValueAsFloat(), epsilon);

    var = new BasicValue(1234567);
    assertEquals(1234567.0f, var.getValueAsFloat(), epsilon);

    var = new BasicValue(12345678980L);
    assertEquals(12345678980.0f, var.getValueAsFloat(), epsilon);

    var = new BasicValue(3.1415926f);
    assertEquals(3.1415926f, var.getValueAsFloat(), epsilon);

    var = new BasicValue(0.618123456789);
    assertEquals(0.618123456789f, var.getValueAsFloat(), epsilon);

    var.setStringValue("1.234567890123");
    assertEquals(1.234567890123f, var.getValueAsFloat(), epsilon);

    var.setBooleanValue(true);
    assertEquals(1.0f, var.getValueAsFloat(), epsilon);

    var.setBooleanValue(false);
    assertEquals(0.0f, var.getValueAsFloat(), epsilon);

    var.setByteValue((byte)123);
    assertEquals(123.0f, var.getValueAsFloat(), epsilon);

    var.setShortValue((short)12345);
    assertEquals(12345.0f, var.getValueAsFloat(), epsilon);

    var.setDoubleValue(1234567);
    assertEquals(1234567.0f, var.getValueAsFloat(), epsilon);

    var.setLongValue(12345678980L);
    assertEquals(12345678980.0f, var.getValueAsFloat(), epsilon);

    var.setFloatValue(3.1415926f);
    assertEquals(3.1415926f, var.getValueAsFloat(), epsilon);

    var.setDoubleValue(0.618123456789);
    assertEquals(0.618123456789f, var.getValueAsFloat(), epsilon);


  }

  /**
   * Test method for {@link BasicValue#getValueAsDouble()}.
   * @throws TypeConvertException
   */
  @Test
  public void testAsDouble() throws TypeConvertException {
    final double epsilon = 0.0000001;
    BasicValue var = null;

    var = new BasicValue("1.234567890123");
    assertEquals(1.234567890123, var.getValueAsDouble(), epsilon);

    var = new BasicValue(true);
    assertEquals(1.0, var.getValueAsDouble(), epsilon);

    var = new BasicValue(false);
    assertEquals(0.0, var.getValueAsDouble(), epsilon);

    var = new BasicValue((byte)123);
    assertEquals(123.0, var.getValueAsDouble(), epsilon);

    var = new BasicValue((short)12345);
    assertEquals(12345.0, var.getValueAsDouble(), epsilon);

    var = new BasicValue(1234567);
    assertEquals(1234567.0, var.getValueAsDouble(), epsilon);

    var = new BasicValue(12345678980L);
    assertEquals(12345678980.0, var.getValueAsDouble(), epsilon);

    var = new BasicValue(3.1415926f);
    assertEquals(3.1415926, var.getValueAsDouble(), epsilon);

    var = new BasicValue(0.618123456789);
    assertEquals(0.618123456789, var.getValueAsDouble(), epsilon);


    var.setStringValue("1.234567890123");
    assertEquals(1.234567890123, var.getValueAsDouble(), epsilon);

    var.setBooleanValue(true);
    assertEquals(1.0, var.getValueAsDouble(), epsilon);

    var.setBooleanValue(false);
    assertEquals(0.0, var.getValueAsDouble(), epsilon);

    var.setByteValue((byte)123);
    assertEquals(123.0, var.getValueAsDouble(), epsilon);

    var.setShortValue((short)12345);
    assertEquals(12345.0, var.getValueAsDouble(), epsilon);

    var.setDoubleValue(1234567);
    assertEquals(1234567.0, var.getValueAsDouble(), epsilon);

    var.setLongValue(12345678980L);
    assertEquals(12345678980.0, var.getValueAsDouble(), epsilon);

    var.setFloatValue(3.1415926f);
    assertEquals(3.1415926, var.getValueAsDouble(), epsilon);

    var.setDoubleValue(0.618123456789);
    assertEquals(0.618123456789, var.getValueAsDouble(), epsilon);
  }

  /**
   * Test method for {@link com.ascent.commons.lang.Variant#read(java.io.DataInput)}.
   */
//  @Test
//  public void testRead() {
//    fail("Not yet implemented"); // TODO
//  }
//
//  /**
//   * Test method for {@link com.ascent.commons.lang.Variant#write(java.io.DataOutput)}.
//   */
//  @Test
//  public void testWrite() {
//    fail("Not yet implemented"); // TODO
//  }

  /**
   * Test method for {@link com.ascent.commons.lang.Variant#compareTo(com.ascent.commons.lang.Variant)}.
   */
//  @Test
//  public void testCompareTo() {
//    fail("Not yet implemented"); // TODO
//  }

  /**
   * Test method for {@link BasicValue#toString()}.
   */
  @Test
  public void testToString() {
    final BasicValue var = new BasicValue();

    System.out.println(var.toString());

    var.setStringValue("1.234567890123");
    System.out.println(var.toString());

    var.setBooleanValue(true);
    System.out.println(var.toString());

    var.setBooleanValue(false);
    System.out.println(var.toString());

    var.setByteValue((byte)123);
    System.out.println(var.toString());

    var.setShortValue((short)12345);
    System.out.println(var.toString());

    var.setDoubleValue(1234567);
    System.out.println(var.toString());

    var.setLongValue(12345678980L);
    System.out.println(var.toString());

    var.setFloatValue(3.1415926f);
    System.out.println(var.toString());

    var.setDoubleValue(0.618123456789);
    System.out.println(var.toString());
  }

  /**
   * Test method for {@link BasicValue#equals(java.lang.Object)}.
   */
//  @Test
//  public void testEqualsObject() {
//    fail("Not yet implemented"); // TODO
//  }

  @Test
  public void testXmlSerialization() throws XmlException {
    BasicValue var = null;
    BasicValue var2 = null;
    String xml = null;

    var = new BasicValue();
    xml = XmlSerialization.serialize(BasicValue.class, var);
    System.out.println(xml);
    var2 = XmlSerialization.deserialize(BasicValue.class, xml);
    assertEquals(var, var2);

    var.setStringValue("1.234567890123");
    xml = XmlSerialization.serialize(BasicValue.class, var);
    System.out.println(xml);
    var2 = XmlSerialization.deserialize(BasicValue.class, xml);
    assertEquals(var, var2);

    var.setStringValue(" 1.234567890123");
    xml = XmlSerialization.serialize(BasicValue.class, var);
    System.out.println(xml);
    var2 = XmlSerialization.deserialize(BasicValue.class, xml);
    assertEquals(var, var2);

    var.setStringValue("1.234567890123 ");
    xml = XmlSerialization.serialize(BasicValue.class, var);
    System.out.println(xml);
    var2 = XmlSerialization.deserialize(BasicValue.class, xml);
    assertEquals(var, var2);

    var.setBooleanValue(true);
    xml = XmlSerialization.serialize(BasicValue.class, var);
    System.out.println(xml);
    var2 = XmlSerialization.deserialize(BasicValue.class, xml);
    assertEquals(var, var2);

    var.setBooleanValue(false);
    xml = XmlSerialization.serialize(BasicValue.class, var);
    System.out.println(xml);
    var2 = XmlSerialization.deserialize(BasicValue.class, xml);
    assertEquals(var, var2);

    var.setByteValue((byte)123);
    xml = XmlSerialization.serialize(BasicValue.class, var);
    System.out.println(xml);
    var2 = XmlSerialization.deserialize(BasicValue.class, xml);
    assertEquals(var, var2);

    var.setShortValue((short)12345);
    xml = XmlSerialization.serialize(BasicValue.class, var);
    System.out.println(xml);
    var2 = XmlSerialization.deserialize(BasicValue.class, xml);
    assertEquals(var, var2);

    var.setDoubleValue(1234567);
    xml = XmlSerialization.serialize(BasicValue.class, var);
    System.out.println(xml);
    var2 = XmlSerialization.deserialize(BasicValue.class, xml);
    assertEquals(var, var2);

    var.setLongValue(12345678980L);
    xml = XmlSerialization.serialize(BasicValue.class, var);
    System.out.println(xml);
    var2 = XmlSerialization.deserialize(BasicValue.class, xml);
    assertEquals(var, var2);

    var.setFloatValue(3.1415926f);
    xml = XmlSerialization.serialize(BasicValue.class, var);
    System.out.println(xml);
    var2 = XmlSerialization.deserialize(BasicValue.class, xml);
    assertEquals(var, var2);

    var.setDoubleValue(0.618123456789);
    xml = XmlSerialization.serialize(BasicValue.class, var);
    System.out.println(xml);
    var2 = XmlSerialization.deserialize(BasicValue.class, xml);
    assertEquals(var, var2);
  }

}
