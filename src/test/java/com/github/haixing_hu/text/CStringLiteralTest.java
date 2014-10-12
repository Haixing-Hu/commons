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
package com.github.haixing_hu.text;

import java.text.ParseException;

import org.junit.Test;

import com.github.haixing_hu.text.CStringLiteral;

import static org.junit.Assert.*;

/**
 * Unit test of the {@link CStringLiteral} class.
 *
 * @author Haixing Hu
 */
public class CStringLiteralTest {

  /**
   * Test method for
   * {@link CStringLiteral#decode(java.lang.String, int, int)}
   * .
   */
  @Test
  public void testDecode() {
    String str = null;
    byte[] expect = null;
    byte[] result = null;

    try {
      str = "hello, world.";
      expect = new byte[] { 0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x2c, 0x20, 0x77,
          0x6f, 0x72, 0x6c, 0x64, 0x2e };
      result = CStringLiteral.decode(str, 0, str.length());
      assertArrayEquals(expect, result);
    } catch (final ParseException e) {
      fail("should not throw.");
    }

    try {
      str = "hello, \\\"world\\\".";
      expect = new byte[] { 0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x2c, 0x20, 0x22,
          0x77, 0x6f, 0x72, 0x6c, 0x64, 0x22, 0x2e };
      result = CStringLiteral.decode(str, 0, str.length());
      assertArrayEquals(expect, result);
    } catch (final ParseException e) {
      fail("should not throw.");
    }

    try {
      str = "hello, \\x22world\\x22.";
      expect = new byte[] { 0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x2c, 0x20, 0x22,
          0x77, 0x6f, 0x72, 0x6c, 0x64, 0x22, 0x2e };
      result = CStringLiteral.decode(str, 0, str.length());
      assertArrayEquals(expect, result);
    } catch (final ParseException e) {
      fail("should not throw.");
    }

    try {
      str = "hello, \\42world\\42\\100123.";
      expect = new byte[] { 0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x2c, 0x20, 0x22,
          0x77, 0x6f, 0x72, 0x6c, 0x64, 0x22, 0x40, 0x31, 0x32, 0x33, 0x2e };
      result = CStringLiteral.decode(str, 0, str.length());
      assertArrayEquals(expect, result);
    } catch (final ParseException e) {
      fail("should not throw.");
    }

    try {
      str = "hello, \\u0022world\\u0022.";
      expect = new byte[] { 0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x2c, 0x20, 0x22,
          0x77, 0x6f, 0x72, 0x6c, 0x64, 0x22, 0x2e };
      result = CStringLiteral.decode(str, 0, str.length());
      assertArrayEquals(expect, result);
    } catch (final ParseException e) {
      fail("should not throw.");
    }

    try {
      str = "hello, \\U00000022world\\U00000022.";
      expect = new byte[] { 0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x2c, 0x20, 0x22,
          0x77, 0x6f, 0x72, 0x6c, 0x64, 0x22, 0x2e };
      result = CStringLiteral.decode(str, 0, str.length());
      assertArrayEquals(expect, result);
    } catch (final ParseException e) {
      fail("should not throw.");
    }

  }

  /**
   * Test method for
   * {@link com.ascent.text.CStringLiteral#encode(byte[], java.lang.StringBuilder)}
   * .
   */
  // @Test
  // public void testEncode()
  // {
  // fail("Not yet implemented");
  // }

}
