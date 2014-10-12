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
 * Unit test of the {@link CharUtils#quote()} functions.
 *
 * @author Haixing Hu
 */
public class CharUtilsQuoteTest {

  @Test
  public void testQuote() {
    assertEquals("'x'", CharUtils.quote('x', '\\', '\'', '\''));
    assertEquals("'\\\\'", CharUtils.quote('\\', '\\', '\'', '\''));
    assertEquals("'\\''", CharUtils.quote('\'', '\\', '\'', '\''));

    assertEquals("[x]", CharUtils.quote('x', '\\', '[', ']'));
    assertEquals("[\\\\]", CharUtils.quote('\\', '\\', '[', ']'));
    assertEquals("[\\[]", CharUtils.quote('[', '\\', '[', ']'));
    assertEquals("[\\]]", CharUtils.quote(']', '\\', '[', ']'));
  }

  @Test
  public void testQuoteStringBuilder() {
    final StringBuilder b1 = new StringBuilder();
    CharUtils.quote('x', '\\', '\'', '\'', b1);
    assertEquals("'x'", b1.toString());

    final StringBuilder b2 = new StringBuilder();
    CharUtils.quote('\\', '\\', '\'', '\'', b2);
    assertEquals("'\\\\'", b2.toString());

    final StringBuilder b3 = new StringBuilder("abc");
    CharUtils.quote('\'', '\\', '\'', '\'', b3);
    assertEquals("abc'\\''", b3.toString());

    final StringBuilder b4 = new StringBuilder();
    CharUtils.quote('x', '\\', '[', ']', b4);
    assertEquals("[x]", b4.toString());

    final StringBuilder b5 = new StringBuilder("xyz");
    CharUtils.quote('\\', '\\', '[', ']', b5);
    assertEquals("xyz[\\\\]", b5.toString());

    final StringBuilder b6 = new StringBuilder();
    CharUtils.quote('[', '\\', '[', ']', b6);
    assertEquals("[\\[]", b6.toString());

    final StringBuilder b7 = new StringBuilder("www");
    CharUtils.quote(']', '\\', '[', ']', b7);
    assertEquals("www[\\]]", b7.toString());
  }
}
