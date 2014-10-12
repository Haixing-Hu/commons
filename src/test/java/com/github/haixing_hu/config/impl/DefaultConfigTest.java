/*
 * Copyright (c) 2014  Haixing Hu
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
 */
package com.github.haixing_hu.config.impl;

import org.junit.Test;

import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.text.xml.XmlException;

/**
 * Unit test of the DefaultConfig class.
 *
 * @author Haixing Hu
 */
public class DefaultConfigTest extends ConfigTestBase {

  @Test
  public void testStringProperties() throws XmlException {
    final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class,
        STRING_CONFIG, DefaultConfigTest.class);
    verifyStringProperties(config);
  }

  @Test
  public void testCharProperties() throws XmlException {
    final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class,
        CHAR_CONFIG, DefaultConfigTest.class);
    verifyCharProperties(config);
  }

  @Test
  public void testBooleanProperties() throws XmlException {
    final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class,
        BOOLEAN_CONFIG, DefaultConfigTest.class);
    verifyBooleanProperties(config);
  }

  @Test
  public void testByteProperties() throws XmlException {
    final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class,
        BYTE_CONFIG, DefaultConfigTest.class);
    verifyByteProperties(config);
  }

  @Test
  public void testClassProperties() throws XmlException {
    final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class,
        CLASS_CONFIG, DefaultConfigTest.class);
    verifyClassProperties(config);
  }

  @Test
  public void testInstanceProperties() throws XmlException {
    final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class,
        INSTANCE_CONFIG, DefaultConfigTest.class);
    verifyInstanceProperties(config);
  }
}
