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
import static org.junit.Assert.fail;

/**
 * Unit test of the {@link CharUtils#unquote(String, char, char, char)}
 * function.
 *
 * @author Haixing Hu
 */
public class CharUtilsUnquoteTest {

  @Test
  public void testUnquote() {
    assertEquals('x', CharUtils.unquote("'x'", '\\', '\'', '\''));
    assertEquals('x', CharUtils.unquote("\"x\"", '\\', '"', '"'));
    assertEquals('x', CharUtils.unquote("[x]", '\\', '[', ']'));
    assertEquals('\\', CharUtils.unquote("'\\\\'", '\\', '\'', '\''));
    assertEquals('\'', CharUtils.unquote("'\\''", '\\', '\'', '\''));
  }

  @Test
  public void testUnquoteError() {
    try {
      CharUtils.unquote(null, '\\', '\'', '\'');
      fail("should throw");
    } catch (final NullPointerException e) {
      //  pass
    }

    try {
      CharUtils.unquote("", '\\', '\'', '\'');
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }

    try {
      CharUtils.unquote("'", '\\', '\'', '\'');
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }

    try {
      CharUtils.unquote("''", '\\', '\'', '\'');
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }

    try {
      CharUtils.unquote("'xx'", '\\', '\'', '\'');
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }

    try {
      CharUtils.unquote("'xxx'", '\\', '\'', '\'');
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }

    try {
      CharUtils.unquote("''\\'", '\\', '\'', '\'');
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }

    try {
      CharUtils.unquote("[x)", '\\', '[', ']');
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }

    try {
      CharUtils.unquote("[\\]", '\\', '[', ']');
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }
  }
}
