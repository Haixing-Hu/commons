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

import java.io.IOException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.text.xml.XmlUtils;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for the XML serialization of {@link DefaultProperty}.
 *
 * @author Haixing Hu
 */
public class DefaultPropertyXmlSerializationTest {

  private static final String BUG1 = "defaultproperty-bug1.xml";

  @Test
  public void testDeserializeBug1() throws XmlException, SAXException, IOException {
    final Document doc = XmlUtils.parse(BUG1, DefaultPropertyXmlSerializationTest.class);

    final DefaultProperty prop = XmlSerialization.deserialize(DefaultProperty.class,
        doc.getDocumentElement());
    assertEquals("", prop.getDescription());

    final String actual = XmlSerialization.serialize(DefaultProperty.class, prop);
    final DefaultProperty prop2 = XmlSerialization.deserialize(DefaultProperty.class, actual);
    assertEquals("", prop2.getDescription());
    assertEquals(prop, prop2);
  }

}
