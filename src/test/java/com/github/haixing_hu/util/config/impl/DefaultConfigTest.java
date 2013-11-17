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

package com.github.haixing_hu.util.config.impl;

import org.junit.Test;

import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.lang.Type;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.util.config.Configured;
import com.github.haixing_hu.util.config.error.PropertyHasNoValueError;
import com.github.haixing_hu.util.config.impl.DefaultConfig;
import com.github.haixing_hu.util.config.impl.DefaultProperty;

import static org.junit.Assert.*;

import static com.github.haixing_hu.util.junit.Assert.assertArrayEquals;

/**
 * Unit test of the DefaultConfig class.
 *
 * @author Haixing Hu
 */
public class DefaultConfigTest {

  @Test
  public void testStringProperties() throws XmlException {
    final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class,
        "string-props.xml", DefaultConfigTest.class);

    assertEquals("description 1", config.getDescription("prop1"));
    assertEquals(Type.STRING, config.getType("prop1"));
    assertEquals(false, config.isFinal("prop1"));
    assertEquals(1, config.getCount("prop1"));
    assertEquals("value1", config.getString("prop1"));
    assertArrayEquals(new String[]{"value1"},
        config.getStrings("prop1"));

    assertEquals("", config.getDescription("prop2"));
    assertEquals(Type.STRING, config.getType("prop2"));
    assertEquals(true, config.isFinal("prop2"));
    assertEquals(1, config.getCount("prop2"));
    assertEquals("value2", config.getString("prop2"));
    assertArrayEquals(new String[]{"value2"},
        config.getStrings("prop2"));

    assertEquals(null, config.getDescription("prop3"));
    assertEquals(Type.STRING, config.getType("prop3"));
    assertEquals(true, config.isFinal("prop3"));
    assertEquals(5, config.getCount("prop3"));
    assertEquals("value3.1", config.getString("prop3"));
    assertArrayEquals(new String[]{"value3.1", "value3.2", "value3.3", "value3.4", "value3.5"},
        config.getStrings("prop3"));

    assertEquals("description 4", config.getDescription("prop4"));
    assertEquals(Type.STRING, config.getType("prop4"));
    assertEquals(false, config.isFinal("prop4"));
    assertEquals(0, config.getCount("prop4"));
    try {
      config.getString("prop4");
      fail("should throw");
    } catch (final PropertyHasNoValueError e) {
      // pass
    }
    assertArrayEquals(new String[]{},
        config.getStrings("prop4"));

    assertEquals("description 5 with trailing whitespace", config.getDescription("prop5"));
    assertEquals(Type.STRING, config.getType("prop5"));
    assertEquals(false, config.isFinal("prop5"));
    assertEquals(2, config.getCount("prop5"));
    assertEquals("value 5.1 with leading and trailing whitespace", config.getString("prop5"));
    assertArrayEquals(new String[]{
        "value 5.1 with leading and trailing whitespace",
        "  value 5.2 with preserved whitespace "},
        config.getStrings("prop5"));

    assertEquals("  description 6 with preserved leading and trailing whitespace  ",
        config.getDescription("prop6"));
    assertEquals(Type.STRING, config.getType("prop6"));
    assertEquals(false, config.isFinal("prop6"));
    assertEquals(0, config.getCount("prop6"));

  }

  @Test
  public void testCharProperties() throws XmlException {
    final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class,
        "char-props.xml", DefaultConfigTest.class);

    assertEquals("description 1", config.getDescription("prop1"));
    assertEquals(Type.CHAR, config.getType("prop1"));
    assertEquals(false, config.isFinal("prop1"));
    assertEquals(1, config.getCount("prop1"));
    assertEquals('a', config.getChar("prop1"));
    assertArrayEquals(new char[]{'a'},
        config.getChars("prop1"));

    assertEquals("description 2", config.getDescription("prop2"));
    assertEquals(Type.CHAR, config.getType("prop2"));
    assertEquals(true, config.isFinal("prop2"));
    assertEquals(2, config.getCount("prop2"));
    assertEquals('a', config.getChar("prop2"));
    assertArrayEquals(new char[]{'a', 'b'},
        config.getChars("prop2"));

    assertEquals(null, config.getDescription("prop3"));
    assertEquals(Type.CHAR, config.getType("prop3"));
    assertEquals(false, config.isFinal("prop3"));
    assertEquals(4, config.getCount("prop3"));
    assertEquals('a', config.getChar("prop3"));
    assertArrayEquals(new char[]{'a', 'b', 'c', 'd'},
        config.getChars("prop3"));

    assertEquals("description 4", config.getDescription("prop4"));
    assertEquals(Type.CHAR, config.getType("prop4"));
    assertEquals(false, config.isFinal("prop4"));
    assertEquals(0, config.getCount("prop4"));
    try {
      config.getChar("prop4");
      fail("should throw");
    } catch (final PropertyHasNoValueError e) {
      // pass
    }
    assertArrayEquals(new char[]{},
        config.getChars("prop4"));
  }

  @Test
  public void testBooleanProperties() throws XmlException {
    final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class,
        "boolean-props.xml", DefaultConfigTest.class);

    assertEquals("description 1", config.getDescription("prop1"));
    assertEquals(Type.BOOLEAN, config.getType("prop1"));
    assertEquals(false, config.isFinal("prop1"));
    assertEquals(1, config.getCount("prop1"));
    assertEquals(true, config.getBoolean("prop1"));
    assertArrayEquals(new boolean[]{true},
        config.getBooleans("prop1"));

    assertEquals("description 2", config.getDescription("prop2"));
    assertEquals(Type.BOOLEAN, config.getType("prop2"));
    assertEquals(true, config.isFinal("prop2"));
    assertEquals(2, config.getCount("prop2"));
    assertEquals(false, config.getBoolean("prop2"));
    assertArrayEquals(new boolean[]{false, true},
        config.getBooleans("prop2"));

    assertEquals(null, config.getDescription("prop3"));
    assertEquals(Type.BOOLEAN, config.getType("prop3"));
    assertEquals(false, config.isFinal("prop3"));
    assertEquals(4, config.getCount("prop3"));
    assertEquals(true, config.getBoolean("prop3"));
    assertArrayEquals(new boolean[]{true, false, true, false},
        config.getBooleans("prop3"));

    assertEquals("description 4", config.getDescription("prop4"));
    assertEquals(Type.BOOLEAN, config.getType("prop4"));
    assertEquals(false, config.isFinal("prop4"));
    assertEquals(0, config.getCount("prop4"));
    try {
      config.getBoolean("prop4");
      fail("should throw");
    } catch (final PropertyHasNoValueError e) {
      // pass
    }
    assertArrayEquals(new boolean[]{},
        config.getBooleans("prop4"));
  }


  @Test
  public void testByteProperties() throws XmlException {
    final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class,
        "byte-props.xml", DefaultConfigTest.class);

    assertEquals("description 1", config.getDescription("prop1"));
    assertEquals(Type.BYTE, config.getType("prop1"));
    assertEquals(false, config.isFinal("prop1"));
    assertEquals(1, config.getCount("prop1"));
    assertEquals(12, config.getByte("prop1"));
    assertArrayEquals(new byte[]{12},
        config.getBytes("prop1"));

    assertEquals("description 2", config.getDescription("prop2"));
    assertEquals(Type.BYTE, config.getType("prop2"));
    assertEquals(true, config.isFinal("prop2"));
    assertEquals(2, config.getCount("prop2"));
    assertEquals(0, config.getByte("prop2"));
    assertArrayEquals(new byte[]{0, -10},
        config.getBytes("prop2"));

    assertEquals(null, config.getDescription("prop3"));
    assertEquals(Type.BYTE, config.getType("prop3"));
    assertEquals(false, config.isFinal("prop3"));
    assertEquals(5, config.getCount("prop3"));
    assertEquals(0x10, config.getByte("prop3"));
    assertArrayEquals(new byte[]{0x10, 0x56, 0, -0xB, -23},
        config.getBytes("prop3"));

    assertEquals("description 4", config.getDescription("prop4"));
    assertEquals(Type.BYTE, config.getType("prop4"));
    assertEquals(false, config.isFinal("prop4"));
    assertEquals(0, config.getCount("prop4"));
    try {
      config.getByte("prop4");
      fail("should throw");
    } catch (final PropertyHasNoValueError e) {
      // pass
    }
    assertArrayEquals(new byte[]{},
        config.getBytes("prop4"));
  }

  @Test
  public void testClassProperties() throws XmlException {
    final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class,
        "class-props.xml", DefaultConfigTest.class);

    assertEquals("description 1", config.getDescription("prop1"));
    assertEquals(Type.CLASS, config.getType("prop1"));
    assertEquals(false, config.isFinal("prop1"));
    assertEquals(1, config.getCount("prop1"));
    assertEquals(DefaultConfig.class, config.getClass("prop1"));
    assertArrayEquals(new Class<?>[]{DefaultConfig.class},
        config.getClasses("prop1"));

    assertEquals("description 2", config.getDescription("prop2"));
    assertEquals(Type.CLASS, config.getType("prop2"));
    assertEquals(true, config.isFinal("prop2"));
    assertEquals(2, config.getCount("prop2"));
    assertEquals(DefaultConfig.class, config.getClass("prop2"));
    assertArrayEquals(new Class<?>[]{DefaultConfig.class, DefaultProperty.class},
        config.getClasses("prop2"));

    assertEquals(null, config.getDescription("prop3"));
    assertEquals(Type.CLASS, config.getType("prop3"));
    assertEquals(false, config.isFinal("prop3"));
    assertEquals(5, config.getCount("prop3"));
    assertEquals(String.class, config.getClass("prop3"));
    assertArrayEquals(new Class<?>[]{String.class, Integer.class,
        Float.class, DefaultConfig.class, DefaultProperty.class},
        config.getClasses("prop3"));

    assertEquals("description 4", config.getDescription("prop4"));
    assertEquals(Type.CLASS, config.getType("prop4"));
    assertEquals(false, config.isFinal("prop4"));
    assertEquals(0, config.getCount("prop4"));
    try {
      config.getClass("prop4");
      fail("should throw");
    } catch (final PropertyHasNoValueError e) {
      // pass
    }
    assertArrayEquals(new Class<?>[]{},
        config.getClasses("prop4"));

    assertEquals(null, config.getDescription("prop5"));
    assertEquals(Type.CLASS, config.getType("prop5"));
    assertEquals(false, config.isFinal("prop5"));
    assertEquals(1, config.getCount("prop5"));
    assertEquals(String.class, config.getClass("prop5"));
    assertArrayEquals(new Class<?>[]{String.class},
        config.getClasses("prop5"));
  }

  @Test
  public void testInstanceProperties() throws XmlException {
    final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class,
        "instance-props.xml", DefaultConfigTest.class);

    assertEquals("description 1", config.getDescription("prop1"));
    assertEquals(Type.CLASS, config.getType("prop1"));
    assertEquals(false, config.isFinal("prop1"));
    assertEquals(1, config.getCount("prop1"));
    assertEquals(new DefaultConfig(), config.<DefaultConfig>getInstance("prop1"));
    assertArrayEquals(new DefaultConfig[]{new DefaultConfig()},
        config.<DefaultConfig>getInstances("prop1", DefaultConfig.class));

    assertEquals("description 2", config.getDescription("prop2"));
    assertEquals(Type.CLASS, config.getType("prop2"));
    assertEquals(true, config.isFinal("prop2"));
    assertEquals(0, config.getCount("prop2"));
    try {
      config.getInstance("prop2");
      fail("should throw");
    } catch (final PropertyHasNoValueError e) {
      // pass
    }
    assertArrayEquals(new Object[]{},
        config.getInstances("prop2", Object.class));

    assertEquals(null, config.getDescription("prop3"));
    assertEquals(Type.CLASS, config.getType("prop3"));
    assertEquals(false, config.isFinal("prop3"));
    assertEquals(2, config.getCount("prop3"));
    final MyConfigurableClass obj = config.getInstance("prop3");
    assertTrue(config == obj.getConfig());

    final Configured[] objs = config.getInstances("prop3", Configured.class);
    assertNotNull(objs);
    assertEquals(2, objs.length);
    assertEquals(MyConfigurableClass.class, objs[0].getClass());
    assertEquals(Configured.class, objs[1].getClass());
  }
}
