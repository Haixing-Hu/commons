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

package com.github.haixing_hu.net;

import org.junit.Test;

import com.github.haixing_hu.net.UrlUtils;

import static org.junit.Assert.assertEquals;

/**
 * Unit test of the UrlUtils class.
 *
 * @author Haixing Hu
 */
public class UrlUtilsTest {

  @Test
  public void testIsIPv4Address() {
    assertEquals(false, UrlUtils.isIPv4Address(""));
    assertEquals(false, UrlUtils.isIPv4Address("www.google.com"));

    assertEquals(false, UrlUtils.isIPv4Address("192"));
    assertEquals(false, UrlUtils.isIPv4Address("192.168"));
    assertEquals(false, UrlUtils.isIPv4Address("192.168.0"));
    assertEquals(false, UrlUtils.isIPv4Address("192.168.0.1."));
    assertEquals(false, UrlUtils.isIPv4Address("0..0.0"));
    assertEquals(false, UrlUtils.isIPv4Address("0.1121.0.0"));
    assertEquals(false, UrlUtils.isIPv4Address("255.256.0.0"));

    assertEquals(true, UrlUtils.isIPv4Address("0.0.0.0"));
    assertEquals(true, UrlUtils.isIPv4Address("192.0.0.0"));
    assertEquals(true, UrlUtils.isIPv4Address("192.168.0.1"));
    assertEquals(true, UrlUtils.isIPv4Address("192.168.0.111"));
    assertEquals(true, UrlUtils.isIPv4Address("192.168.0.000111"));
    assertEquals(true, UrlUtils.isIPv4Address("0.0000000.0.000000"));
    assertEquals(true, UrlUtils.isIPv4Address("255.0000255.0101.0000"));
  }

  @Test
  public void testGetDomain() {

    assertEquals("sina.com.cn", UrlUtils.getDomain("www.sina.com.cn"));
    assertEquals("sina.com.cn", UrlUtils.getDomain("news.sina.com.cn"));
    assertEquals("sina.com.cn", UrlUtils.getDomain("sina.com.cn"));
    assertEquals("sina.com.cn", UrlUtils.getDomain("a.b.c.d.sina.com.cn"));

    assertEquals("", UrlUtils.getDomain(""));
    assertEquals("a", UrlUtils.getDomain("a"));

    assertEquals("a.cn", UrlUtils.getDomain("a.cn"));
    assertEquals("cn", UrlUtils.getDomain("cn"));

  }

  @Test
  public void testGetDomainSuffix() {

    assertEquals("com.cn", UrlUtils.getDomainSuffix("www.sina.com.cn").getDomain());
    assertEquals("com.cn", UrlUtils.getDomainSuffix("news.sina.com.cn").getDomain());
    assertEquals("com.cn", UrlUtils.getDomainSuffix("sina.com.cn").getDomain());
    assertEquals("com.cn", UrlUtils.getDomainSuffix("a.b.c.d.sina.com.cn").getDomain());
    assertEquals("cn", UrlUtils.getDomainSuffix("a.cn").getDomain());
    assertEquals("cn", UrlUtils.getDomainSuffix("cn").getDomain());

    assertEquals(null, UrlUtils.getDomainSuffix(""));
    assertEquals(null, UrlUtils.getDomainSuffix("a"));
  }


}
