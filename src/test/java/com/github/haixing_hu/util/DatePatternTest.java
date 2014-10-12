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
package com.github.haixing_hu.util;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.Test;

import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.util.DatePattern;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for the {@link DatePattern} class.
 *
 * @author Haixing Hu
 */
public class DatePatternTest {

  public static final String[] PATTERN_XMLS = {
    "<date-pattern>" +
    "  <pattern>[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}</pattern>" +
    "  <format>yyyy-MM-dd</format>" +
    "</date-pattern>",
    "<date-pattern>" +
    "  <pattern>[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}</pattern>" +
    "  <format>yyyy-MM-dd HH:mm:ss</format>" +
    "</date-pattern>",
    "<date-pattern>" +
    "  <pattern>[0-9]{1,2}/[0-9]{1,2}/[0-9]{2} [0-9]{1,2}:[0-9]{1,2}</pattern>" +
    "  <format>MMM/dd/yy h:m</format>" +
    "  <locale>en_US</locale>" +
    "</date-pattern>",
  };

  @Test
  public void testXmlSerialization() throws XmlException {
    DatePattern dp1 = null;
    DatePattern dp2 = null;
    for (final String xml1 : PATTERN_XMLS) {
      dp1 = XmlSerialization.deserialize(DatePattern.class, xml1);
      final String xml2 = XmlSerialization.serialize(DatePattern.class, dp1);
      System.out.println(xml2);
      dp2 = XmlSerialization.deserialize(DatePattern.class, xml2);
      assertEquals(dp1, dp2);
    }
  }

  @Test
  public void testMatch() throws XmlException {
    final DatePattern dp = new DatePattern();
    Date actual = null;
    final GregorianCalendar expected = new GregorianCalendar();

    dp.setPattern("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}");
    dp.setFormat("yyyy-MM-dd");
    actual = dp.match("日期：2010-01-11");
    expected.clear();
    expected.set(2010, 0, 11);
    assertEquals(expected.getTime(), actual);

    dp.setPattern("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}");
    dp.setFormat("yyyy-MM-dd HH:mm:ss");
    actual = dp.match("日期：2010-01-11 10:24:36");
    expected.clear();
    expected.set(2010, 0, 11, 10, 24, 36);
    assertEquals(expected.getTime(), actual);

    dp.setPattern("[0-9]{2}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}");
    dp.setFormat("yy-MM-dd HH:mm:ss");
    actual = dp.match("日期：11-01-11 10:24:36");
    expected.clear();
    expected.set(2011, 0, 11, 10, 24, 36);
    assertEquals(expected.getTime(), actual);

    actual = dp.match("日期：97-01-11 10:24:36 作者：张三");
    expected.clear();
    expected.set(1997, 0, 11, 10, 24, 36);
    assertEquals(expected.getTime(), actual);

    dp.setPattern("[0-9]{2}-[A-Z][a-z]+-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}");
    dp.setFormat("yy-MMM-dd HH:mm:ss");
    dp.setLocale(Locale.US);
    actual = dp.match("日期：97-Jan-11 10:24:36 作者：张三");
    expected.clear();
    expected.set(1997, 0, 11, 10, 24, 36);
    assertEquals(expected.getTime(), actual);


    dp.setPattern("[0-9]{2}-[A-Z][a-z]+-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2} [a-zA-Z]{2}");
    dp.setFormat("yy-MMM-dd hh:mm:ss aa");
    dp.setLocale(Locale.US);
    actual = dp.match("日期：97-Jan-11 10:24:36 PM 作者：张三");
    expected.clear();
    expected.set(1997, 0, 11, 22, 24, 36);
    assertEquals(expected.getTime(), actual);
  }

}
