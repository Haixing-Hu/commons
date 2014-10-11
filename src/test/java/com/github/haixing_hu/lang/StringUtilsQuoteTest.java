/******************************************************************************
 *
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
 ******************************************************************************/

package com.github.haixing_hu.lang;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for the {@link StringUtils#quote()} functions.
 *
 * @author Haixing Hu
 */
public class StringUtilsQuoteTest {

  @Test
  public void testQuote() {
    final String str1 = "hello\\, 'Tom'!";
    assertEquals("'hello\\\\, \\'Tom\\'!'",
        StringUtils.quote(str1, '\\', '\'', '\''));

    final String str2 = "hello\\, \\(tom)!";
    assertEquals("(hello\\\\, \\\\\\(tom\\)!)",
        StringUtils.quote(str2, '\\', '(', ')'));

    final String str3 = "hello\\, \\(tom)!";
    assertEquals("\\hello\\\\, \\\\(tom\\)!)",
        StringUtils.quote(str3, '\\', '\\', ')'));
  }

  @Test
  public void testQuoteStringBuilder() {
    final String str1 = "hello\\, 'Tom'!";
    final StringBuilder builder1 = new StringBuilder();
    StringUtils.quote(str1, '\\', '\'', '\'', builder1);
    assertEquals("'hello\\\\, \\'Tom\\'!'", builder1.toString());

    final String str2 = "hello\\, \\(tom)!";
    final StringBuilder builder2 = new StringBuilder("xyz");
    StringUtils.quote(str2, '\\', '(', ')', builder2);
    assertEquals("xyz(hello\\\\, \\\\\\(tom\\)!)", builder2.toString());

    final String str3 = "hello\\, \\(tom)!";
    final StringBuilder builder3 = new StringBuilder("abc");
    StringUtils.quote(str3, '\\', '\\', ')', builder3);
    assertEquals("abc\\hello\\\\, \\\\(tom\\)!)", builder3.toString());
  }
}
