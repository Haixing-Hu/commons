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
package com.github.haixing_hu.net;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.io.exception.InvalidFormatException;
import com.github.haixing_hu.io.exception.SerializationException;
import com.github.haixing_hu.io.serialize.BinarySerialization;
import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.net.Url;
import com.github.haixing_hu.net.UrlPart;
import com.github.haixing_hu.net.UrlPattern;
import com.github.haixing_hu.text.Pattern;
import com.github.haixing_hu.text.PatternType;
import com.github.haixing_hu.text.xml.DomUtils;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.text.xml.XmlUtils;

import static org.junit.Assert.*;

/**
 * Unit test of the {@link UrlPattern} class.
 *
 * @author Haixing Hu
 */
public class UrlPatternTest {

  /**
   * Test method for {@link UrlPattern#UrlPattern()}.
   */
  @Test
  public void testUrlPattern() {
    final UrlPattern urlPattern = new UrlPattern();
    assertEquals(UrlPattern.DEFAULT_URL_PART, urlPattern.getUrlPart());
    assertEquals(new Pattern(), urlPattern.getPattern());
  }

  /**
   * Test method for {@link UrlPattern#UrlPattern(UrlPart, Pattern)}.
   */
  @Test
  public void testUrlPatternUrlPartPattern() {

    final Pattern pattern = new Pattern(PatternType.GLOB, "www.*");
    final UrlPattern urlPattern = new UrlPattern(UrlPart.HOSTNAME, pattern.clone());
    assertEquals(UrlPart.HOSTNAME, urlPattern.getUrlPart());
    assertEquals(pattern, urlPattern.getPattern());

    try {
      new UrlPattern(null, pattern);
      fail("Must throw NullPointerException");
    } catch (final NullPointerException e) {
      // pass
    }

    try {
      new UrlPattern(UrlPart.HOSTNAME, null);
      fail("Must throw NullPointerException");
    } catch (final NullPointerException e) {
      // pass
    }
  }

  /**
   * Test method for {@link UrlPattern#setUrlPart(UrlPart)}
   * and {@link UrlPattern#getUrlPart()}.
   */
  @Test
  public void testGetSetUrlPart() {
    final Pattern pattern = new Pattern(PatternType.GLOB, "www.*");
    final UrlPattern urlPattern = new UrlPattern(UrlPart.HOSTNAME, pattern.clone());

    assertEquals(UrlPart.HOSTNAME, urlPattern.getUrlPart());

    urlPattern.setUrlPart(UrlPart.URL);
    assertEquals(UrlPart.URL, urlPattern.getUrlPart());

    urlPattern.setUrlPart(UrlPart.DOMAIN);
    assertEquals(UrlPart.DOMAIN, urlPattern.getUrlPart());

    try {
      urlPattern.setUrlPart(null);
      fail("Must throw NullPointerException");
    } catch (final NullPointerException e) {
      // pass
    }

  }

  /**
   * Test method for {@link UrlPattern#getPattern()} and
   * {@link UrlPattern#setPattern(Pattern)}.
   */
  @Test
  public void testGetSetPattern() {
    Pattern pattern = new Pattern(PatternType.GLOB, "www.*");
    final UrlPattern urlPattern = new UrlPattern(UrlPart.HOSTNAME, pattern.clone());

    assertEquals(pattern, urlPattern.getPattern());

    pattern = new Pattern(PatternType.SUFFIX, "com.cn");
    urlPattern.setPattern(pattern.clone());
    assertEquals(pattern, urlPattern.getPattern());

    try {
      urlPattern.setPattern(null);
      fail("Must throw NullPointerException");
    } catch (final NullPointerException e) {
      // pass
    }

  }

  /**
   * Test method for {@link UrlPattern#matches(Url)}.
   * @throws URISyntaxException
   */
  @Test
  public void testMatches() throws MalformedURLException {
    final UrlPattern p = new UrlPattern();
    final Url sina = new Url("http://www.sina.com.cn/news/12345.html#top");
    final Url google = new Url("http://www.gooGLe.com/");
    final Url gmail = new Url("https://username:password@www.Gmail.com/read?id=1234");
    final Url yahooNews = new Url("http://NEWS.YAHOO.COM.cn/789012345.html#bottom");
    final Url yahooHome = new Url("http://www.YAHOO.COM.cn/");

    p.setUrlPart(UrlPart.HOSTNAME);
    p.getPattern().setType(PatternType.PREFIX);
    p.getPattern().setExpression("www");
    assertEquals(true, p.matches(sina));
    assertEquals(true, p.matches(google));
    assertEquals(true, p.matches(gmail));
    assertEquals(false, p.matches(yahooNews));
    assertEquals(true, p.matches(yahooHome));

    p.setUrlPart(UrlPart.HOSTNAME);
    p.getPattern().setType(PatternType.SUFFIX);
    p.getPattern().setExpression("com.cn");
    assertEquals(true, p.matches(sina));
    assertEquals(false, p.matches(google));
    assertEquals(false, p.matches(gmail));
    assertEquals(true, p.matches(yahooNews));
    assertEquals(true, p.matches(yahooHome));

    p.setUrlPart(UrlPart.URL);
    p.getPattern().setType(PatternType.LITERAL);
    p.getPattern().setExpression(sina.toString());
    assertEquals(true, p.matches(sina));
    assertEquals(false, p.matches(google));
    assertEquals(false, p.matches(gmail));
    assertEquals(false, p.matches(yahooNews));
    assertEquals(false, p.matches(yahooHome));

    p.setUrlPart(UrlPart.DOMAIN);
    p.getPattern().setType(PatternType.LITERAL);
    p.getPattern().setExpression("yahoo.com.cn");
    assertEquals(false, p.matches(sina));
    assertEquals(false, p.matches(google));
    assertEquals(false, p.matches(gmail));
    assertEquals(true, p.matches(yahooNews));
    assertEquals(true, p.matches(yahooHome));

    p.setUrlPart(UrlPart.USER_INFO);
    p.getPattern().setType(PatternType.GLOB);
    p.getPattern().setExpression("user*");
    assertEquals(false, p.matches(sina));
    assertEquals(false, p.matches(google));
    assertEquals(true, p.matches(gmail));
    assertEquals(false, p.matches(yahooNews));
    assertEquals(false, p.matches(yahooHome));

    p.setUrlPart(UrlPart.QUERY);
    p.getPattern().setType(PatternType.REGEX);
    p.getPattern().setExpression("^[a-z]+=[0-9]+$");
    assertEquals(false, p.matches(sina));
    assertEquals(false, p.matches(google));
    assertEquals(true, p.matches(gmail));
    assertEquals(false, p.matches(yahooNews));
    assertEquals(false, p.matches(yahooHome));

    p.setUrlPart(UrlPart.FRAGMENT);
    p.getPattern().setType(PatternType.REGEX);
    p.getPattern().setExpression("[a-z]+");
    assertEquals(true, p.matches(sina));
    assertEquals(false, p.matches(google));
    assertEquals(false, p.matches(gmail));
    assertEquals(true, p.matches(yahooNews));
    assertEquals(false, p.matches(yahooHome));
  }

  /**
   * Test method for {@link UrlPattern#fromXml(Element)} and
   * {@link UrlPattern#toXml(Document)}.
   * @throws XmlException
   * @throws SerializationException
   */
  @Test
  public void testXmlSerialization() throws XmlException {
    UrlPattern p1 = null;
    UrlPattern p2 = null;
    String xml = null;

    // test for empty object
    p1 = new UrlPattern();
    xml = XmlSerialization.serialize(UrlPattern.class, p1);
    System.out.println(xml);
    p2 = XmlSerialization.deserialize(UrlPattern.class, xml);
    assertEquals(p1, p2);

    final String XML_FILE = "url-patterns.xml";
    final Document doc = XmlUtils.parse(XML_FILE, UrlPatternTest.class);
    final Element root = doc.getDocumentElement();
    final List<Element> nodeList = DomUtils.getChildren(root, null);
    for (final Element node : nodeList) {
      p1 = XmlSerialization.deserialize(UrlPattern.class, node);
      xml = XmlSerialization.serialize(UrlPattern.class, p1);
      System.out.println(xml);
      p2 = XmlSerialization.deserialize(UrlPattern.class, xml);
      assertEquals(p1, p2);
    }
  }

  @Test
  public void testBinarySerilization() throws XmlException, IOException {
    UrlPattern p1 = null;
    UrlPattern p2 = null;
    byte[] data = null;

    // test for null
    p1 = null;
    data = BinarySerialization.serialize(UrlPattern.class, p1);
    p2 = BinarySerialization.deserialize(UrlPattern.class, data, true);
    assertEquals(p1, p2);
    try {
      p2 = BinarySerialization.deserialize(UrlPattern.class, data, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // pass
    }

    // test for empty object
    p1 = new UrlPattern();
    data = BinarySerialization.serialize(UrlPattern.class, p1);
    p2 = BinarySerialization.deserialize(UrlPattern.class, data, true);
    assertEquals(p1, p2);

    // test for normal objects
    final String XML_FILE = "url-patterns.xml";
    final Document doc = XmlUtils.parse(XML_FILE, UrlPatternTest.class);
    final Element root = doc.getDocumentElement();
    final List<Element> nodeList = DomUtils.getChildren(root, null);
    for (final Element node : nodeList) {
      p1 = XmlSerialization.deserialize(UrlPattern.class, node);
      data = BinarySerialization.serialize(UrlPattern.class, p1);
      p2 = BinarySerialization.deserialize(UrlPattern.class, data, true);
      assertEquals(p1, p2);
    }
  }

  /**
   * Test method for {@link UrlPattern#clone()}.
   */
  @Test
  public void testClone() {
   // fail("Not yet implemented");
  }

  /**
   * Test method for {@link UrlPattern#equals(Object)}.
   */
  @Test
  public void testEqualsObject() {
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link UrlPattern#hashCode()}.
   */
  @Test
  public void testHashCode() {
   // fail("Not yet implemented");
  }

  /**
   * Test method for {@link UrlPattern#toString()}.
   */
  @Test
  public void testToString() {
   // fail("Not yet implemented");
  }

}
