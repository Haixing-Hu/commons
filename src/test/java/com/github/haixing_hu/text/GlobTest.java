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
package com.github.haixing_hu.text;

import java.io.IOException;
import java.util.regex.Pattern;

import org.junit.Test;

import com.github.haixing_hu.io.exception.InvalidFormatException;
import com.github.haixing_hu.io.serialize.BinarySerialization;
import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.text.Glob;
import com.github.haixing_hu.text.xml.XmlException;

import static org.junit.Assert.*;

/**
 * Unit test for the {@link Glob} class.
 *
 * @author Haixing Hu
 */
public class GlobTest {

  /**
   * Test method for
   * {@link Glob#toRegex(java.lang.String)}.
   */
  @Test
  public void testToRegex() {
    String glob = null;
    String regex = null;

    glob = "";
    regex = Glob.toRegex(glob);
    assertEquals("", regex);

    glob = "*";
    regex = Glob.toRegex(glob);
    assertEquals(".*", regex);

    glob = "*.java";
    regex = Glob.toRegex(glob);
    assertEquals(".*\\.java", regex);

    glob = "*.[ch]";
    regex = Glob.toRegex(glob);
    assertEquals(".*\\.[ch]", regex);

    glob = "*.{c,cpp,h,hpp,cxx,hxx}";
    regex = Glob.toRegex(glob);
    assertEquals(".*\\.(c|cpp|h|hpp|cxx|hxx)", regex);

    glob = "[^#]*";
    regex = Glob.toRegex(glob);
    assertEquals("[^#].*", regex);

    glob = "h?ello*.{c,cpp,h,hpp,cxx,hxx}";
    regex = Glob.toRegex(glob);
    assertEquals("h.ello.*\\.(c|cpp|h|hpp|cxx|hxx)", regex);
  }

  @Test
  public void testXmlSerilization() throws XmlException {
    Glob g1 = null;
    Glob g2 = null;
    String xml = null;

    g1 = new Glob();
    xml = XmlSerialization.serialize(Glob.class, g1);
    System.out.println(xml);
    g2 = XmlSerialization.deserialize(Glob.class, xml);
    assertEquals(g1, g2);

    g1 = new Glob("*");
    xml = XmlSerialization.serialize(Glob.class, g1);
    System.out.println(xml);
    g2 = XmlSerialization.deserialize(Glob.class, xml);
    assertEquals(g1, g2);

    g1 = new Glob("*", Pattern.CASE_INSENSITIVE);
    xml = XmlSerialization.serialize(Glob.class, g1);
    System.out.println(xml);
    g2 = XmlSerialization.deserialize(Glob.class, xml);
    assertEquals(g1, g2);

    g1 = new Glob("*.java", Pattern.CANON_EQ);
    xml = XmlSerialization.serialize(Glob.class, g1);
    System.out.println(xml);
    g2 = XmlSerialization.deserialize(Glob.class, xml);
    assertEquals(g1, g2);

    g1 = new Glob("*.[ch]", Pattern.CANON_EQ | Pattern.UNICODE_CASE);
    xml = XmlSerialization.serialize(Glob.class, g1);
    System.out.println(xml);
    g2 = XmlSerialization.deserialize(Glob.class, xml);
    assertEquals(g1, g2);

    g1 = new Glob("*.{c,cpp,h,hpp,cxx,hxx}");
    xml = XmlSerialization.serialize(Glob.class, g1);
    System.out.println(xml);
    g2 = XmlSerialization.deserialize(Glob.class, xml);
    assertEquals(g1, g2);

    g1 = new Glob("[^#]*");
    xml = XmlSerialization.serialize(Glob.class, g1);
    System.out.println(xml);
    g2 = XmlSerialization.deserialize(Glob.class, xml);
    assertEquals(g1, g2);

    g1 = new Glob("h?ello*.{c,cpp,h,hpp,cxx,hxx}");
    xml = XmlSerialization.serialize(Glob.class, g1);
    System.out.println(xml);
    g2 = XmlSerialization.deserialize(Glob.class, xml);
    assertEquals(g1, g2);

    g1 = new Glob("h?e>l<l&o*.{c,cpp,h,hpp,cxx,hxx}");
    xml = XmlSerialization.serialize(Glob.class, g1);
    System.out.println(xml);
    g2 = XmlSerialization.deserialize(Glob.class, xml);
    assertEquals(g1, g2);
  }


  @Test
  public void testBinarySerialization() throws IOException {
    Glob g1 = null;
    Glob g2 = null;
    byte[] data = null;

    // test for null
    g1 = null;
    data = BinarySerialization.serialize(Glob.class, g1);
    g2 = BinarySerialization.deserialize(Glob.class, data, true);
    assertEquals(g1, g2);
    try {
      g2 = BinarySerialization.deserialize(Glob.class, data, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // pass
    }

    // test for empty
    g1 = new Glob();
    data = BinarySerialization.serialize(Glob.class, g1);
    g2 = BinarySerialization.deserialize(Glob.class, data, true);
    assertEquals(g1, g2);

    g1 = new Glob("*");
    data = BinarySerialization.serialize(Glob.class, g1);
    g2 = BinarySerialization.deserialize(Glob.class, data, true);
    assertEquals(g1, g2);

    g1 = new Glob("*", Pattern.CASE_INSENSITIVE);
    data = BinarySerialization.serialize(Glob.class, g1);
    g2 = BinarySerialization.deserialize(Glob.class, data, true);
    assertEquals(g1, g2);

    g1 = new Glob("*.java", Pattern.CANON_EQ);
    data = BinarySerialization.serialize(Glob.class, g1);
    g2 = BinarySerialization.deserialize(Glob.class, data, true);
    assertEquals(g1, g2);

    g1 = new Glob("*.[ch]", Pattern.CANON_EQ | Pattern.UNICODE_CASE);
    data = BinarySerialization.serialize(Glob.class, g1);
    g2 = BinarySerialization.deserialize(Glob.class, data, true);
    assertEquals(g1, g2);

    g1 = new Glob("*.{c,cpp,h,hpp,cxx,hxx}");
    data = BinarySerialization.serialize(Glob.class, g1);
    g2 = BinarySerialization.deserialize(Glob.class, data, true);
    assertEquals(g1, g2);

    g1 = new Glob("[^#]*");
    data = BinarySerialization.serialize(Glob.class, g1);
    g2 = BinarySerialization.deserialize(Glob.class, data, true);
    assertEquals(g1, g2);

    g1 = new Glob("h?ello*.{c,cpp,h,hpp,cxx,hxx}");
    data = BinarySerialization.serialize(Glob.class, g1);
    g2 = BinarySerialization.deserialize(Glob.class, data, true);
    assertEquals(g1, g2);

    g1 = new Glob("h?e>l<l&o*.{c,cpp,h,hpp,cxx,hxx}");
    data = BinarySerialization.serialize(Glob.class, g1);
    g2 = BinarySerialization.deserialize(Glob.class, data, true);
    assertEquals(g1, g2);
  }

}
