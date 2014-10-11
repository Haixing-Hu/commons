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
 * Unit test for the {@link StringUtils#isQuoted()} functions.
 *
 * @author Haixing Hu
 */
public class StringUtilsIsQuotedTest {

  @Test
  public void testIsQuoted() {
    assertEquals(false, StringUtils.isQuoted(null));
    assertEquals(false, StringUtils.isQuoted(""));
    assertEquals(false, StringUtils.isQuoted("'"));
    assertEquals(false, StringUtils.isQuoted("\""));
    assertEquals(false, StringUtils.isQuoted("abc"));
    assertEquals(true, StringUtils.isQuoted("''"));
    assertEquals(true, StringUtils.isQuoted("\"\""));
    assertEquals(false, StringUtils.isQuoted("'\""));
    assertEquals(false, StringUtils.isQuoted("\"'"));
    assertEquals(true, StringUtils.isQuoted("'abc'"));
    assertEquals(true, StringUtils.isQuoted("\"def\""));
    assertEquals(true, StringUtils.isQuoted("'abc''"));
    assertEquals(true, StringUtils.isQuoted("\"def\"\""));
  }

  @Test
  public void testIsQuotedChar() {
    assertEquals(false, StringUtils.isQuoted(null, '\'', '\''));
    assertEquals(false, StringUtils.isQuoted("", '\'', '\''));
    assertEquals(false, StringUtils.isQuoted("'", '\'', '\''));
    assertEquals(false, StringUtils.isQuoted("\"", '\'', '\''));
    assertEquals(false, StringUtils.isQuoted("abc", '\'', '\''));
    assertEquals(true, StringUtils.isQuoted("''", '\'', '\''));
    assertEquals(false, StringUtils.isQuoted("\"\", '\'', '\''"));
    assertEquals(false, StringUtils.isQuoted("'\"", '\'', '\''));
    assertEquals(false, StringUtils.isQuoted("\"'", '\'', '\''));
    assertEquals(true, StringUtils.isQuoted("'abc'", '\'', '\''));
    assertEquals(false, StringUtils.isQuoted("\"def\"", '\'', '\''));
    assertEquals(true, StringUtils.isQuoted("'abc''", '\'', '\''));
    assertEquals(false, StringUtils.isQuoted("\"def\"\"", '\'', '\''));

    assertEquals(false, StringUtils.isQuoted("'a[b]c'", '[', ']'));
    assertEquals(true, StringUtils.isQuoted("[abc]", '[', ']'));
  }
}
