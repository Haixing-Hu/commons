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
package com.github.haixing_hu.lang;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test of the {@link StringUtils#unescape()} functions.
 *
 * @author Haixing Hu
 */
public class StringUtilsUnescapeTest {

  @Test
  public void testUnescape() {
    assertEquals("", StringUtils.unescape("", '\\'));

    assertEquals("h\\ello' wo%rld'",
        StringUtils.unescape("h\\\\ello\\' wo\\%rld\\'", '\\'));

    assertEquals("h%ello' wo%rld'",
        StringUtils.unescape("h%%ello%' wo%%rld%'", '%'));

    assertEquals("h%ello' wo%rld'",
        StringUtils.unescape("h%%ello%' wo%%rld%'%", '%'));
  }

  @Test
  public void testUnescapeStringBuilder() {
    final StringBuilder builder1 = new StringBuilder("abc");
    StringUtils.unescape("", '\\', builder1);
    assertEquals("abc", builder1.toString());

    final StringBuilder builder2 = new StringBuilder();
    StringUtils.unescape("h\\\\ello\\' wo\\%rld\\'", '\\', builder2);
    assertEquals("h\\ello' wo%rld'", builder2.toString());

    final StringBuilder builder3 = new StringBuilder("xyz");
    StringUtils.unescape("h%%ello%' wo%%rld%'", '%', builder3);
    assertEquals("xyzh%ello' wo%rld'", builder3.toString());

    final StringBuilder builder4 = new StringBuilder("xyz");
    StringUtils.unescape("h%%ello%' wo%%rld%'%", '%', builder4);
    assertEquals("xyzh%ello' wo%rld'", builder4.toString());
  }
}
