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

package com.github.haixing_hu.text;

import java.io.IOException;

import org.junit.Test;

import com.github.haixing_hu.io.exception.InvalidFormatException;
import com.github.haixing_hu.io.serialize.BinarySerialization;
import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.text.xml.XmlException;

import static org.junit.Assert.*;

/**
 * Unit test of the {@link Pattern} class.
 *
 * @author Haixing Hu
 */
public class PatternTest {


  /**
   * Test method for {@link Pattern#Pattern()}.
   */
  @Test
  public void testPattern() {
    final Pattern pattern = new Pattern();
    assertEquals(Pattern.DEFAULT_TYPE, pattern.getType());
    assertEquals(Pattern.DEFAULT_IGNORE_CASE, pattern.isIgnoreCase());
    assertEquals(StringUtils.EMPTY, pattern.getExpression());
  }

  /**
   * Test method for {@link Pattern#Pattern(PatternType, String)}.
   */
  @Test
  public void testPatternPatternTypeString() {
    Pattern pattern = new Pattern(PatternType.SUFFIX, "suffix");
    assertEquals(PatternType.SUFFIX, pattern.getType());
    assertEquals(Pattern.DEFAULT_IGNORE_CASE, pattern.isIgnoreCase());
    assertEquals("suffix", pattern.getExpression());

    pattern = new Pattern(PatternType.PREFIX, "prefix");
    assertEquals(PatternType.PREFIX, pattern.getType());
    assertEquals(Pattern.DEFAULT_IGNORE_CASE, pattern.isIgnoreCase());
    assertEquals("prefix", pattern.getExpression());
  }

  /**
   * Test method for {@link Pattern#Pattern(PatternType, boolean, String)}.
   */
  @Test
  public void testPatternPatternTypeBooleanString() {
    Pattern pattern = new Pattern(PatternType.SUFFIX, true, "suffix");
    assertEquals(PatternType.SUFFIX, pattern.getType());
    assertEquals(true, pattern.isIgnoreCase());
    assertEquals("suffix", pattern.getExpression());

    pattern = new Pattern(PatternType.PREFIX, false, "prefix");
    assertEquals(PatternType.PREFIX, pattern.getType());
    assertEquals(false, pattern.isIgnoreCase());
    assertEquals("prefix", pattern.getExpression());
  }

  /**
   * Test method for {@link Pattern#getType()} and {@link Pattern#setType(PatternType)}.
   */
  @Test
  public void testGetSetType() {
    final Pattern pattern = new Pattern();

    assertEquals(Pattern.DEFAULT_TYPE, pattern.getType());

    pattern.setType(PatternType.GLOB);
    assertEquals(PatternType.GLOB, pattern.getType());

    pattern.setType(PatternType.PREFIX);
    assertEquals(PatternType.PREFIX, pattern.getType());

    pattern.setType(PatternType.REGEX);
    assertEquals(PatternType.REGEX, pattern.getType());

    pattern.setType(PatternType.LITERAL);
    assertEquals(PatternType.LITERAL, pattern.getType());

    pattern.setType(PatternType.SUFFIX);
    assertEquals(PatternType.SUFFIX, pattern.getType());

    try {
      pattern.setType(null);
      fail("Must throw NullPointerException.");
    } catch (final NullPointerException e) {
      // pass
    }
  }

  /**
   * Test method for {@link Pattern#isIgnoreCase()} and
   * {@link Pattern#setIgnoreCase(boolean)}.
   */
  @Test
  public void testGetSetCaseInsensitive() {
    final Pattern pattern = new Pattern();

    assertEquals(Pattern.DEFAULT_IGNORE_CASE, pattern.isIgnoreCase());

    pattern.setIgnoreCase(true);
    assertEquals(true, pattern.isIgnoreCase());

    pattern.setIgnoreCase(false);
    assertEquals(false, pattern.isIgnoreCase());
  }

  /**
   * Test method for {@link Pattern#getExpression()} and
   * {@link Pattern#setExpression(String)}.
   */
  @Test
  public void testGetSetExpression() {
    final Pattern pattern = new Pattern();

    assertEquals(StringUtils.EMPTY, pattern.getExpression());

    pattern.setExpression("test");
    assertEquals("test", pattern.getExpression());

    try {
      pattern.setExpression(null);
      fail("Must throw NullPointerException.");
    } catch (final NullPointerException e) {
      // pass
    }
  }


  /**
   * Test method for {@link Pattern#matches(String)}.
   */
  @Test
  public void testMatches() {
    testMatchesForStringPattern();
    testMatchesForPrefixPattern();
    testMatchesForSuffixPattern();
    testMatchesForRegexPattern();
    testMatchesForGlobPattern();
  }

  private void testMatchesForStringPattern() {
    final Pattern p = new Pattern();
    p.setType(PatternType.LITERAL);

    p.setIgnoreCase(false);

    p.setExpression("");
    assertEquals(true, p.matches(""));
    assertEquals(false, p.matches("str"));

    p.setExpression("hello");
    assertEquals(true, p.matches("hello"));
    assertEquals(false, p.matches("str"));
    assertEquals(false, p.matches(""));
    assertEquals(false, p.matches("Hello"));

    p.setIgnoreCase(true);
    assertEquals(true, p.matches("hello"));
    assertEquals(false, p.matches("str"));
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("Hello"));
  }

  private void testMatchesForPrefixPattern() {
    final Pattern p = new Pattern();
    p.setType(PatternType.PREFIX);

    p.setIgnoreCase(false);

    p.setExpression("");
    assertEquals(true, p.matches(""));
    assertEquals(true, p.matches("str"));

    p.setExpression("hello");
    assertEquals(true, p.matches("hello"));
    assertEquals(true, p.matches("hello123"));
    assertEquals(false, p.matches("str"));
    assertEquals(false, p.matches(""));
    assertEquals(false, p.matches("Hello"));
    assertEquals(false, p.matches("Hello123"));

    p.setIgnoreCase(true);
    assertEquals(true, p.matches("hello"));
    assertEquals(true, p.matches("hello123"));
    assertEquals(false, p.matches("str"));
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("Hello"));
    assertEquals(true, p.matches("Hello123"));
  }

  private void testMatchesForSuffixPattern() {
    final Pattern p = new Pattern();
    p.setType(PatternType.SUFFIX);

    p.setIgnoreCase(false);

    p.setExpression("");
    assertEquals(true, p.matches(""));
    assertEquals(true, p.matches("str"));

    p.setExpression("hello");
    assertEquals(true, p.matches("hello"));
    assertEquals(true, p.matches("123hello"));
    assertEquals(false, p.matches("hello123"));
    assertEquals(false, p.matches("str"));
    assertEquals(false, p.matches(""));
    assertEquals(false, p.matches("Hello"));
    assertEquals(false, p.matches("123Hello"));
    assertEquals(false, p.matches("Hello123"));

    p.setIgnoreCase(true);
    assertEquals(true, p.matches("hello"));
    assertEquals(true, p.matches("123hello"));
    assertEquals(false, p.matches("hello123"));
    assertEquals(false, p.matches("str"));
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("Hello"));
    assertEquals(true, p.matches("123Hello"));
    assertEquals(false, p.matches("Hello123"));
  }

  private void testMatchesForRegexPattern() {
    final Pattern p = new Pattern();
    p.setType(PatternType.REGEX);

    p.setIgnoreCase(false);

    p.setExpression("");
    assertEquals(true, p.matches(""));
    assertEquals(false, p.matches("str"));

    p.setExpression(".");
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("s"));
    assertEquals(true, p.matches("t"));
    assertEquals(false, p.matches("st"));

    p.setExpression(".*");
    assertEquals(true, p.matches(""));
    assertEquals(true, p.matches("s"));
    assertEquals(true, p.matches("s"));
    assertEquals(true, p.matches("st"));


    p.setExpression("ab.c");
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("abxc"));
    assertEquals(true, p.matches("abyc"));
    assertEquals(false, p.matches("abc"));
    assertEquals(false, p.matches("abxyc"));
    assertEquals(false, p.matches("abx"));

    p.setExpression("ab.*c");
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("abxc"));
    assertEquals(true, p.matches("abyc"));
    assertEquals(true, p.matches("abc"));
    assertEquals(true, p.matches("abxyc"));
    assertEquals(false, p.matches("aBxc"));
    assertEquals(false, p.matches("Abyc"));
    assertEquals(false, p.matches("abC"));
    assertEquals(false, p.matches("AbxYC"));
    assertEquals(false, p.matches("abx"));

    p.setExpression("ab.+c");
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("abxc"));
    assertEquals(true, p.matches("abyc"));
    assertEquals(false, p.matches("abc"));
    assertEquals(true, p.matches("abxyc"));
    assertEquals(false, p.matches("aBxc"));
    assertEquals(false, p.matches("Abyc"));
    assertEquals(false, p.matches("abC"));
    assertEquals(false, p.matches("AbxYC"));
    assertEquals(false, p.matches("abx"));

    p.setIgnoreCase(true);
    p.setExpression("ab.*c");
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("abxc"));
    assertEquals(true, p.matches("abyc"));
    assertEquals(true, p.matches("abc"));
    assertEquals(true, p.matches("abxyc"));
    assertEquals(true, p.matches("aBxc"));
    assertEquals(true, p.matches("Abyc"));
    assertEquals(true, p.matches("abC"));
    assertEquals(true, p.matches("AbxYC"));
    assertEquals(false, p.matches("abx"));

    p.setExpression("ab.+c");
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("abxc"));
    assertEquals(true, p.matches("abyc"));
    assertEquals(false, p.matches("abc"));
    assertEquals(true, p.matches("abxyc"));
    assertEquals(true, p.matches("aBxc"));
    assertEquals(true, p.matches("Abyc"));
    assertEquals(false, p.matches("abC"));
    assertEquals(true, p.matches("AbxYC"));
    assertEquals(false, p.matches("abx"));
  }

  private void testMatchesForGlobPattern() {
    final Pattern p = new Pattern();
    p.setType(PatternType.GLOB);

    p.setIgnoreCase(false);

    p.setExpression("");
    assertEquals(true, p.matches(""));
    assertEquals(false, p.matches("str"));

    p.setExpression("?");
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("s"));
    assertEquals(true, p.matches("t"));
    assertEquals(false, p.matches("st"));

    p.setExpression("*");
    assertEquals(true, p.matches(""));
    assertEquals(true, p.matches("s"));
    assertEquals(true, p.matches("s"));
    assertEquals(true, p.matches("st"));


    p.setExpression("ab?c");
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("abxc"));
    assertEquals(true, p.matches("abyc"));
    assertEquals(false, p.matches("abc"));
    assertEquals(false, p.matches("abxyc"));
    assertEquals(false, p.matches("abx"));

    p.setExpression("ab*c");
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("abxc"));
    assertEquals(true, p.matches("abyc"));
    assertEquals(true, p.matches("abc"));
    assertEquals(true, p.matches("abxyc"));
    assertEquals(false, p.matches("aBxc"));
    assertEquals(false, p.matches("Abyc"));
    assertEquals(false, p.matches("abC"));
    assertEquals(false, p.matches("AbxYC"));
    assertEquals(false, p.matches("abx"));

    p.setIgnoreCase(true);
    p.setExpression("ab*c");
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("abxc"));
    assertEquals(true, p.matches("abyc"));
    assertEquals(true, p.matches("abc"));
    assertEquals(true, p.matches("abxyc"));
    assertEquals(true, p.matches("aBxc"));
    assertEquals(true, p.matches("Abyc"));
    assertEquals(true, p.matches("abC"));
    assertEquals(true, p.matches("AbxYC"));
    assertEquals(false, p.matches("abx"));

  }


  @Test
  public void testXmlSerialization() throws XmlException {
    Pattern p1 = null;
    Pattern p2 = null;
    String xml = null;

    //  test for empty
    p1 = new Pattern();
    xml = XmlSerialization.serialize(Pattern.class, p1);
    System.out.println(xml);
    p2 = XmlSerialization.deserialize(Pattern.class, xml);
    assertEquals(p1, p2);

    p1.setIgnoreCase(true);
    xml = XmlSerialization.serialize(Pattern.class, p1);
    System.out.println(xml);
    p2 = XmlSerialization.deserialize(Pattern.class, xml);
    assertEquals(p1, p2);

    p1.setType(PatternType.REGEX);
    xml = XmlSerialization.serialize(Pattern.class, p1);
    System.out.println(xml);
    p2 = XmlSerialization.deserialize(Pattern.class, xml);
    assertEquals(p1, p2);

    p1.setExpression("expression");
    xml = XmlSerialization.serialize(Pattern.class, p1);
    System.out.println(xml);
    p2 = XmlSerialization.deserialize(Pattern.class, xml);
    assertEquals(p1, p2);

    p1.setType(Pattern.DEFAULT_TYPE);
    xml = XmlSerialization.serialize(Pattern.class, p1);
    System.out.println(xml);
    p2 = XmlSerialization.deserialize(Pattern.class, xml);
    assertEquals(p1, p2);

    p1.setIgnoreCase(Pattern.DEFAULT_IGNORE_CASE);
    p1.setType(PatternType.REGEX);
    xml = XmlSerialization.serialize(Pattern.class, p1);
    System.out.println(xml);
    p2 = XmlSerialization.deserialize(Pattern.class, xml);
    assertEquals(p1, p2);

    p1.setType(Pattern.DEFAULT_TYPE);
    p1.setIgnoreCase(Pattern.DEFAULT_IGNORE_CASE);
    xml = XmlSerialization.serialize(Pattern.class, p1);
    System.out.println(xml);
    p2 = XmlSerialization.deserialize(Pattern.class, xml);
    assertEquals(p1, p2);
  }

  @Test
  public void testBinarySerilization() throws IOException {
    Pattern p1 = null;
    Pattern p2 = null;
    byte[] data = null;

    //  test for null
    p1 = null;
    data = BinarySerialization.serialize(Pattern.class, p1);
    p2 = BinarySerialization.deserialize(Pattern.class, data, true);
    assertEquals(p1, p2);
    try {
      p2 = BinarySerialization.deserialize(Pattern.class, data, false);
      fail("shoudl throw");
    } catch (final InvalidFormatException e) {
      // pass
    }

    //  test for empty
    p1 = new Pattern();
    data = BinarySerialization.serialize(Pattern.class, p1);
    p2 = BinarySerialization.deserialize(Pattern.class, data, true);
    assertEquals(p1, p2);

    //  test for normal object
    p1.setIgnoreCase(true);
    data = BinarySerialization.serialize(Pattern.class, p1);
    p2 = BinarySerialization.deserialize(Pattern.class, data, true);
    assertEquals(p1, p2);

    p1.setType(PatternType.REGEX);
    data = BinarySerialization.serialize(Pattern.class, p1);
    p2 = BinarySerialization.deserialize(Pattern.class, data, true);
    assertEquals(p1, p2);

    p1.setExpression("expression");
    data = BinarySerialization.serialize(Pattern.class, p1);
    p2 = BinarySerialization.deserialize(Pattern.class, data, true);
    assertEquals(p1, p2);

    p1.setType(Pattern.DEFAULT_TYPE);
    data = BinarySerialization.serialize(Pattern.class, p1);
    p2 = BinarySerialization.deserialize(Pattern.class, data, true);
    assertEquals(p1, p2);
  }

  /**
   * Test method for {@link Pattern#equals(Object)} and
   * {@link Pattern#hashCode()}.
   */
  @Test
  public void testEqualsHashCode() {
    final Pattern p1 = new Pattern();
    final Pattern p2 = new Pattern();

    assertEquals(true, p1.equals(p1));
    assertEquals(true, p1.equals(p2));
    assertEquals(false, p1.equals(null));
    assertEquals(p1.hashCode(), p2.hashCode());

    p1.setIgnoreCase(false);
    p2.setIgnoreCase(true);
    assertEquals(false, p1.equals(p2));
    assertFalse(p1.hashCode() == p2.hashCode());

    p1.setIgnoreCase(false);
    p2.setIgnoreCase(false);
    p1.setExpression("expression");
    p2.setExpression("expression");
    assertEquals(true, p1.equals(p2));
    assertEquals(p1.hashCode(), p2.hashCode());

    p2.setExpression("expression2");
    assertEquals(false, p1.equals(p2));
    assertFalse(p1.hashCode() == p2.hashCode());

    p2.setExpression("expression");
    p1.setType(PatternType.LITERAL);
    p2.setType(PatternType.LITERAL);
    assertEquals(true, p1.equals(p2));
    assertEquals(p1.hashCode(), p2.hashCode());

    p2.setType(PatternType.REGEX);
    assertEquals(false, p1.equals(p2));
    assertFalse(p1.hashCode() == p2.hashCode());
  }

  /**
   * Test method for {@link Pattern#toString()}.
   */
  @Test
  public void testToString() {
    final Pattern p = new Pattern();

    System.out.println(p);

    p.setIgnoreCase(true);
    System.out.println(p);

    p.setType(PatternType.REGEX); System.out.println(p);
    System.out.println(p);

    p.setExpression("expression");
    System.out.println(p);

    p.setType(Pattern.DEFAULT_TYPE);
    p.setIgnoreCase(Pattern.DEFAULT_IGNORE_CASE);
    System.out.println(p);
  }

}
