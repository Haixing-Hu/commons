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

package com.github.haixing_hu.text.xml;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.text.xml.TagPattern;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.text.xml.XmlUtils;

import static org.junit.Assert.*;

/**
 * Unit test of the {@link TagPattern} class.
 *
 * @author Haixing Hu
 */
public class TagPatternTest {

  /**
   * Test method for {@link TagPattern#TagPattern()}.
   */
  @Test
  public void testTagPattern() {
    final TagPattern pattern = new TagPattern();
    assertEquals(null, pattern.getTagName());
    assertEquals(null, pattern.getAttributeName());
    assertEquals(null, pattern.getAttributeValue());
    assertEquals(TagPattern.DEFAULT_ORDER, pattern.getOrder());
    assertEquals(null, pattern.getChild());
  }

  /**
   * Test method for {@link TagPattern#setTagName(String)} and {@link TagPattern#getTagName()}.
   */
  @Test
  public void testSetGetTagName() {
    final TagPattern pattern = new TagPattern();
    assertEquals(null, pattern.getTagName());

    pattern.setTagName("tag");
    assertEquals("tag", pattern.getTagName());

    pattern.setTagName(null);
    assertEquals(null, pattern.getTagName());

  }

  /**
   * Test method for {@link TagPattern#setAttributeName(String)} and
   * {@link TagPattern#getAttributeName()}.
   */
  @Test
  public void testSetGetAttributeName() {
    final TagPattern pattern = new TagPattern();
    assertEquals(null, pattern.getAttributeName());

    pattern.setAttributeName("attribute");
    assertEquals("attribute", pattern.getAttributeName());

    pattern.setAttributeName(null);
    assertEquals(null, pattern.getAttributeName());
  }

  /**
   * Test method for {@link TagPattern#setAttributeValue(String)}
   * and {@link TagPattern#getAttributeValue()}.
   */
  @Test
  public void testSetGetAttributeValue() {
    final TagPattern pattern = new TagPattern();
    assertEquals(null, pattern.getAttributeValue());

    pattern.setAttributeValue("value");
    assertEquals("value", pattern.getAttributeValue());

    pattern.setAttributeValue(null);
    assertEquals(null, pattern.getAttributeValue());
  }

  /**
   * Test method for {@link TagPattern#setOrder(int)} and
   * {@link TagPattern#getOrder()}.
   */
  @Test
  public void testSetGetOrder() {
    final TagPattern pattern = new TagPattern();
    assertEquals(TagPattern.DEFAULT_ORDER, pattern.getOrder());

    pattern.setOrder(0);
    assertEquals(0, pattern.getOrder());

    pattern.setOrder(Integer.MAX_VALUE);
    assertEquals(Integer.MAX_VALUE, pattern.getOrder());

    try {
      pattern.setOrder(-1);
      fail("Should throw exception.");
    } catch (final IllegalArgumentException e) {
      // pass
    }
  }

  /**
   * Test method for {@link TagPattern#setChild(TagPattern)}
   * and {@link TagPattern#getChild()}.
   */
  @Test
  public void testGetChild() {
    final TagPattern pattern = new TagPattern();
    assertEquals(null, pattern.getChild());

    final TagPattern child = new TagPattern();
    child.setTagName("child");

    pattern.setChild(child.clone());
    assertEquals(child, pattern.getChild());

    pattern.setChild(null);
    assertEquals(null, pattern.getChild());
  }

  /**
   * Test method for {@link TagPattern#matches(Node)}.
   * @throws XmlException
   */
  @Test
  public void testMatches() throws XmlException {
    final Document doc = XmlUtils.newDocument();
    final Element node = doc.createElement("div");
    node.setAttribute("id", "div-id");

    final TagPattern p = new TagPattern();
    p.setTagName("div");
    assertEquals(true, p.matches(node));
    p.setTagName("td");
    assertEquals(false, p.matches(node));

    p.setTagName(null);
    assertEquals(true, p.matches(node));

    p.setAttributeName("class");
    assertEquals(false, p.matches(node));

    p.setAttributeName("id");
    assertEquals(true, p.matches(node));

    p.setAttributeValue("false");
    assertEquals(false, p.matches(node));

    p.setAttributeValue("div-id");
    assertEquals(true, p.matches(node));

  }

  /**
   * Test method for {@link TagPattern#search(Node)}.
   * @throws XmlException
   */
  @Test
  public void testSearch() throws XmlException {
    final TagPattern p0 = new TagPattern();
    final TagPattern p1 = new TagPattern();
    final TagPattern p2 = new TagPattern();
    final TagPattern p3 = new TagPattern();

    p0.setTagName("div");
    p0.setAttributeName("id");
    p0.setAttributeValue("footer");

    p1.setTagName("div");
    p1.setAttributeName("id");
    p1.setAttributeValue("footer-menu");

    p2.setTagName("b");
    p2.setOrder(2);

    p3.setTagName("a");

    final Document doc = XmlUtils.parse("test1.xml", TagPatternTest.class);
    final Element root = doc.getDocumentElement();
    Element node = p0.search(root);
    assertNotNull(node);
    System.out.println(XmlUtils.toString(node));

    node = p1.search(root);
    assertNotNull(node);
    System.out.println(XmlUtils.toString(node));

    p0.setChild(p1);
    node = p0.search(root);
    assertNotNull(node);
    System.out.println(XmlUtils.toString(node));

    node = p2.search(root);
    assertNotNull(node);
    System.out.println(XmlUtils.toString(node));

    p1.setChild(p2);
    node = p0.search(root);
    assertNotNull(node);
    assertEquals("feedback always welcome", node.getTextContent().trim());

    node = p3.search(root);
    assertNotNull(node);
    System.out.println(XmlUtils.toString(node));

    p2.setChild(p3);
    node = p0.search(root);
    assertNotNull(node);
    assertEquals("feedback always welcome", node.getTextContent().trim());
    System.out.println(XmlUtils.toString(node));
  }

  @Test
  public void testXmlSerialization() throws XmlException {
    final TagPattern p0 = new TagPattern();
    final TagPattern p1 = new TagPattern();
    final TagPattern p2 = new TagPattern();
    final TagPattern p3 = new TagPattern();
    final TagPattern p4 = new TagPattern();
    TagPattern p = null;
    String xml = null;

    p0.setTagName("div");
    p0.setAttributeName("id");
    p0.setAttributeValue("p0");
    p0.setChild(p1);
    p1.setTagName("div");
    p1.setAttributeName("class");
    p1.setAttributeValue("p1");
    p1.setChild(p2);
    p2.setTagName("p");
    p2.setOrder(2);
    p2.setChild(p3);
    p3.setAttributeName("class");
    p3.setAttributeValue("p3");

    xml = XmlSerialization.serialize(TagPattern.class, p0);
    System.out.println(xml);
    p = XmlSerialization.deserialize(TagPattern.class, xml);
    assertEquals(p0, p);

    xml = XmlSerialization.serialize(TagPattern.class, p1);
    System.out.println(xml);
    p = XmlSerialization.deserialize(TagPattern.class, xml);
    assertEquals(p1, p);

    xml = XmlSerialization.serialize(TagPattern.class, p2);
    System.out.println(xml);
    p = XmlSerialization.deserialize(TagPattern.class, xml);
    assertEquals(p2, p);

    xml = XmlSerialization.serialize(TagPattern.class, p3);
    System.out.println(xml);
    p = XmlSerialization.deserialize(TagPattern.class, xml);
    assertEquals(p3, p);

    xml = XmlSerialization.serialize(TagPattern.class, p4);
    System.out.println(xml);
    p = XmlSerialization.deserialize(TagPattern.class, xml);
    assertEquals(p4, p);
  }

  @Test
  public void testBinarySerialization() {
    // TODO
  }

  /**
   * Test method for {@link TagPattern#clone()}.
   */
  @Test
  public void testClone() {
    final TagPattern pattern = new TagPattern();
    TagPattern cloned = pattern.clone();
    assertEquals(pattern, cloned);
    assertNotSame(pattern, cloned);

    pattern.setTagName("tag");
    cloned = pattern.clone();
    assertEquals(pattern, cloned);
    assertNotSame(pattern, cloned);
  }

  /**
   * Test method for {@link TagPattern#hashCode()}.
   */
  @Test
  public void testHashCode() {
    final TagPattern pattern = new TagPattern();
    TagPattern cloned = pattern.clone();
    assertEquals(pattern.hashCode(), cloned.hashCode());

    pattern.setTagName("tag");
    cloned = pattern.clone();
    assertEquals(pattern.hashCode(), cloned.hashCode());
  }

  /**
   * Test method for {@link TagPattern#equals(Object)}.
   */
  @Test
  public void testEqualsObject() {
    // TODO
  }

  /**
   * Test method for {@link TagPattern#toString()}.
   */
  @Test
  public void testToString() {
    final TagPattern p0 = new TagPattern();
    final TagPattern p1 = new TagPattern();
    final TagPattern p2 = new TagPattern();
    final TagPattern p3 = new TagPattern();

    p0.setTagName("div");
    p0.setAttributeName("id");
    p0.setAttributeValue("p0");
    p0.setChild(p1);
    p1.setTagName("div");
    p1.setAttributeName("class");
    p1.setAttributeValue("p1");
    p1.setChild(p2);
    p2.setTagName("p");
    p2.setOrder(2);
    p2.setChild(p3);
    p3.setAttributeName("class");
    p3.setAttributeValue("p3");

    System.out.println(p0);
    System.out.println(p1);
    System.out.println(p2);
    System.out.println(p3);
  }

}
