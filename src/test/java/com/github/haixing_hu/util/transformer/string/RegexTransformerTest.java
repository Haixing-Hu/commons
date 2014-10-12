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
package com.github.haixing_hu.util.transformer.string;

import org.junit.Test;

import com.github.haixing_hu.io.exception.SerializationException;
import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.util.transformer.string.RegexTransformer;

import static org.junit.Assert.assertEquals;

/**
 * Unit test of the {@link RegexTransformer} class.
 *
 * @author Haixing Hu
 */
public class RegexTransformerTest {

  @Test
  public void testConstructor() {
    final RegexTransformer transformer1 = new RegexTransformer();
    assertEquals("", transformer1.getInputPattern());
    assertEquals("", transformer1.getOutputPattern());
    final RegexTransformer transformer2 = new RegexTransformer("([0-9]+)", "abc${1}cdf");
    assertEquals("([0-9]+)", transformer2.getInputPattern());
    assertEquals("abc${1}cdf", transformer2.getOutputPattern());
  }

  @Test
  public void testSetGetInputPattern() {
    final RegexTransformer transformer = new RegexTransformer();
    transformer.setInputPattern("([0-9]+)");
    assertEquals("([0-9]+)", transformer.getInputPattern());
  }

  @Test
  public void testSetGetOutputPattern() {
    final RegexTransformer transformer = new RegexTransformer();
    transformer.setOutputPattern("abc${1}cdf");
    assertEquals("abc${1}cdf", transformer.getOutputPattern());
  }

  @Test
  public void testTransform() {
    final RegexTransformer tr = new RegexTransformer();

    tr.setInputPattern("");
    tr.setOutputPattern("");
    assertEquals("hello 123 world 456", tr.transform("hello 123 world 456"));

    tr.setInputPattern("");
    tr.setOutputPattern("abc${1}cdf");
    assertEquals("hello 123 world 456", tr.transform("hello 123 world 456"));

    tr.setInputPattern("([0-9]+)");
    tr.setOutputPattern("");
    assertEquals("hello  world ", tr.transform("hello 123 world 456"));

    tr.setInputPattern("([0-9]+)");
    tr.setOutputPattern("");
    assertEquals("hello  world ", tr.transform("hello 123 world 456"));

    tr.setInputPattern("([0-9]+)");
    tr.setOutputPattern("abc");
    assertEquals("hello abc world abc", tr.transform("hello 123 world 456"));

    tr.setInputPattern("([0-9]+)");
    tr.setOutputPattern("a${1}bc");
    assertEquals("hello a123bc world a456bc", tr.transform("hello 123 world 456"));

    tr.setInputPattern("([0-9]+)");
    tr.setOutputPattern("a${1}bc${1}");
    assertEquals("hello a123bc123 world a456bc456", tr.transform("hello 123 world 456"));

    tr.setInputPattern("([0-9]+)-([0-9]+)-([0-9]+)");
    tr.setOutputPattern("[year: ${1}, month: ${2}, day: ${3}, day again: ${3}]");
    assertEquals("date1: [year: 2011, month: 01, day: 02, day again: 02], " +
    		"date2: [year: 2012, month: 12, day: 1, day again: 1]",
        tr.transform("date1: 2011-01-02, date2: 2012-12-1"));

    tr.setInputPattern("([0-9]+)-([0-9]+)-([0-9]+)");
    tr.setOutputPattern("${1}${2}${3}${3}");
    assertEquals("date1: 2011010202, date2: 20121211",
        tr.transform("date1: 2011-01-02, date2: 2012-12-1"));

  }

  @Test
  public void testXmlSerialization() throws XmlException, SerializationException {
    String xml = null;
    RegexTransformer tr1 = null;
    RegexTransformer tr2 = null;

    xml = "<regex-transformer>" +
          "  <input-pattern></input-pattern>" +
          "  <output-pattern></output-pattern>" +
          "</regex-transformer>";
    tr1 = XmlSerialization.deserialize(RegexTransformer.class, xml);
    assertEquals("", tr1.getInputPattern());
    assertEquals("", tr1.getOutputPattern());
    xml = XmlSerialization.serialize(RegexTransformer.class, tr1);
    System.out.println(xml);
    tr2 = XmlSerialization.deserialize(RegexTransformer.class, xml);
    assertEquals(tr1, tr2);

    xml = "<regex-transformer>" +
          "  <input-pattern> ([0-9]+) </input-pattern>" +
          "  <output-pattern></output-pattern>" +
          "</regex-transformer>";
    tr1 = XmlSerialization.deserialize(RegexTransformer.class, xml);
    assertEquals("([0-9]+)", tr1.getInputPattern());
    assertEquals("", tr1.getOutputPattern());
    xml = XmlSerialization.serialize(RegexTransformer.class, tr1);
    System.out.println(xml);
    tr2 = XmlSerialization.deserialize(RegexTransformer.class, xml);
    assertEquals(tr1, tr2);

    xml = "<regex-transformer>" +
          "  <input-pattern>  </input-pattern>" +
          "  <output-pattern>a${1}bc  </output-pattern>" +
          "</regex-transformer>";
    tr1 = XmlSerialization.deserialize(RegexTransformer.class, xml);
    assertEquals("", tr1.getInputPattern());
    assertEquals("a${1}bc", tr1.getOutputPattern());
    xml = XmlSerialization.serialize(RegexTransformer.class, tr1);
    System.out.println(xml);
    tr2 = XmlSerialization.deserialize(RegexTransformer.class, xml);
    assertEquals(tr1, tr2);

    xml = "<regex-transformer>" +
          "  <input-pattern> ([0-9]+)-([0-9]+)-([0-9]+) </input-pattern>" +
          "  <output-pattern> [year: ${1}, month: ${2}, day: ${3}, day again: ${3}]  </output-pattern>" +
          "</regex-transformer>";
    tr1 = XmlSerialization.deserialize(RegexTransformer.class, xml);
    assertEquals("([0-9]+)-([0-9]+)-([0-9]+)", tr1.getInputPattern());
    assertEquals("[year: ${1}, month: ${2}, day: ${3}, day again: ${3}]", tr1.getOutputPattern());
    xml = XmlSerialization.serialize(RegexTransformer.class, tr1);
    System.out.println(xml);
    tr2 = XmlSerialization.deserialize(RegexTransformer.class, xml);
    assertEquals(tr1, tr2);
  }
}
