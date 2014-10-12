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

import org.junit.Test;

import com.github.haixing_hu.text.Ascii;

import static org.junit.Assert.assertEquals;

/**
 * Unit test of the {@link Ascii} class.
 *
 * @author Haixing Hu
 */
public class AsciiTest {

  /**
   * Test method for {@link Ascii#isAscii(byte)}.
   */
  @Test
  public void testIsAsciiByte() {
    byte ch;

    ch = 0x00;
    assertEquals(true, Ascii.isAscii(ch));

    ch = 0x01;
    assertEquals(true, Ascii.isAscii(ch));

    ch = 0x0F;
    assertEquals(true, Ascii.isAscii(ch));

    ch = 0x7F;
    assertEquals(true, Ascii.isAscii(ch));

    ch = (byte)0x80;
    assertEquals(false, Ascii.isAscii(ch));

    ch = (byte)0xFF;
    assertEquals(false, Ascii.isAscii(ch));

    ch = (byte)0xEF;
    assertEquals(false, Ascii.isAscii(ch));

    ch = Byte.MAX_VALUE;
    assertEquals(true, Ascii.isAscii(ch));

    ch = Byte.MIN_VALUE;
    assertEquals(false, Ascii.isAscii(ch));

    for (ch = 0x00; ch >= 0; ++ch) {
      assertEquals("ch = " + ch, true, Ascii.isAscii(ch));
    }

    for (ch = 0x00 - 1; ch < 0; --ch) {
      assertEquals("ch = " + ch, false, Ascii.isAscii(ch));
    }

  }

  /**
   * Test method for {@link Ascii#isAscii(int)}.
   */
  @Test
  public void testIsAsciiInt() {
    int ch;

    ch = 0x00;
    assertEquals(true, Ascii.isAscii(ch));

    ch = 0x01;
    assertEquals(true, Ascii.isAscii(ch));

    ch = 0x0F;
    assertEquals(true, Ascii.isAscii(ch));

    ch = 0x7F;
    assertEquals(true, Ascii.isAscii(ch));

    ch = 0x80;
    assertEquals(false, Ascii.isAscii(ch));

    ch = 0xFF;
    assertEquals(false, Ascii.isAscii(ch));

    ch = 0xEF;
    assertEquals(false, Ascii.isAscii(ch));

    ch = 0xFFFFF;
    assertEquals(false, Ascii.isAscii(ch));

    ch = Integer.MAX_VALUE;
    assertEquals(false, Ascii.isAscii(ch));

    ch = Integer.MIN_VALUE;
    assertEquals(false, Ascii.isAscii(ch));
  }

  /**
   * Test method for {@link Ascii#isWhitespace(int)}.
   */
  @Test
  public void testIsWhitespace() {
    assertEquals(true, Ascii.isWhitespace('\t'));
    assertEquals(true, Ascii.isWhitespace('\n'));
    assertEquals(true, Ascii.isWhitespace('\f'));
    assertEquals(true, Ascii.isWhitespace('\r'));
    assertEquals(true, Ascii.isWhitespace(' '));

    for (int ch = 0; ch < '\t'; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isWhitespace(ch));
    }
    for (int ch = '\t' + 1; ch < '\n'; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isWhitespace(ch));
    }
    for (int ch = '\n' + 1; ch < '\f'; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isWhitespace(ch));
    }
    for (int ch = '\f' + 1; ch < '\r'; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isWhitespace(ch));
    }
    for (int ch = '\r' + 1; ch < ' '; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isWhitespace(ch));
    }
    for (int ch = ' ' + 1; ch <= 0xFF; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isWhitespace(ch));
    }

    assertEquals(false, Ascii.isWhitespace(Integer.MIN_VALUE));
    assertEquals(false, Ascii.isWhitespace(Integer.MAX_VALUE));
  }

  /**
   * Test method for {@link Ascii#isLetter(int)}.
   */
  @Test
  public void testIsLetter() {
    for (int ch = 'A'; ch <= 'Z'; ++ch) {
      assertEquals("ch = " + (char)ch, true, Ascii.isLetter(ch));
    }
    for (int ch = 'a'; ch <= 'z'; ++ch) {
      assertEquals("ch = " + (char)ch, true, Ascii.isLetter(ch));
    }

    for (int ch = 0; ch < 'A'; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isLetter(ch));
    }
    for (int ch = 'Z' + 1; ch < 'a'; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isLetter(ch));
    }
    for (int ch = 'z' + 1; ch <= 0xFF; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isLetter(ch));
    }

    assertEquals("ch = " + 0, false, Ascii.isLetter(0));
    assertEquals("ch = " + Integer.MIN_VALUE, false, Ascii.isLetter(Integer.MIN_VALUE));
    assertEquals("ch = " + Integer.MAX_VALUE, false, Ascii.isLetter(Integer.MAX_VALUE));
  }

  /**
   * Test method for {@link Ascii#isUppercaseLetter(int)}.
   */
  @Test
  public void testIsUppercaseLetter() {
    for (int ch = 'A'; ch <= 'Z'; ++ch) {
      assertEquals("ch = " + (char)ch, true, Ascii.isUppercaseLetter(ch));
    }
    for (int ch = 'a'; ch <= 'z'; ++ch) {
      assertEquals("ch = " + (char)ch, false, Ascii.isUppercaseLetter(ch));
    }

    for (int ch = 0; ch < 'A'; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isUppercaseLetter(ch));
    }
    for (int ch = 'Z' + 1; ch < 'a'; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isUppercaseLetter(ch));
    }
    for (int ch = 'z' + 1; ch <= 0xFF; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isUppercaseLetter(ch));
    }

    assertEquals("ch = " + 0, false, Ascii.isLetter(0));
    assertEquals("ch = " + Integer.MIN_VALUE, false, Ascii.isUppercaseLetter(Integer.MIN_VALUE));
    assertEquals("ch = " + Integer.MAX_VALUE, false, Ascii.isUppercaseLetter(Integer.MAX_VALUE));
  }

  /**
   * Test method for {@link Ascii#isLowercaseLetter(int)}.
   */
  @Test
  public void testIsLowercaseLetter() {
    for (int ch = 'A'; ch <= 'Z'; ++ch) {
      assertEquals("ch = " + (char)ch, false, Ascii.isLowercaseLetter(ch));
    }
    for (int ch = 'a'; ch <= 'z'; ++ch) {
      assertEquals("ch = " + (char)ch, true, Ascii.isLowercaseLetter(ch));
    }

    for (int ch = 0; ch < 'A'; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isLowercaseLetter(ch));
    }
    for (int ch = 'Z' + 1; ch < 'a'; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isLowercaseLetter(ch));
    }
    for (int ch = 'z' + 1; ch <= 0xFF; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isLowercaseLetter(ch));
    }

    assertEquals("ch = " + 0, false, Ascii.isLetter(0));
    assertEquals("ch = " + Integer.MIN_VALUE, false, Ascii.isLowercaseLetter(Integer.MIN_VALUE));
    assertEquals("ch = " + Integer.MAX_VALUE, false, Ascii.isLowercaseLetter(Integer.MAX_VALUE));
  }

  /**
   * Test method for {@link Ascii#isDigit(int)}.
   */
  @Test
  public void testIsDigit() {
    for (int ch = '0'; ch <= '9'; ++ch) {
      assertEquals("ch = " + (char)ch, true, Ascii.isDigit(ch));
    }
    for (int ch = 0; ch < '0'; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isDigit(ch));
    }
    for (int ch = '9' + 1; ch <= 0xFF; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isDigit(ch));
    }
    assertEquals("ch = " + 0, false, Ascii.isDigit(0));
    assertEquals("ch = " + Integer.MIN_VALUE, false, Ascii.isDigit(Integer.MIN_VALUE));
    assertEquals("ch = " + Integer.MAX_VALUE, false, Ascii.isDigit(Integer.MAX_VALUE));

  }

  /**
   * Test method for {@link Ascii#isLetterOrDigit(int)}.
   */
  @Test
  public void testIsLetterOrDigit() {
    for (int ch = '0'; ch <= '9'; ++ch) {
      assertEquals("ch = " + (char)ch, true, Ascii.isLetterOrDigit(ch));
    }
    for (int ch = 'A'; ch <= 'Z'; ++ch) {
      assertEquals("ch = " + (char)ch, true, Ascii.isLetterOrDigit(ch));
    }
    for (int ch = 'a'; ch <= 'z'; ++ch) {
      assertEquals("ch = " + (char)ch, true, Ascii.isLetterOrDigit(ch));
    }

    for (int ch = 0; ch < '0'; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isLetterOrDigit(ch));
    }
    for (int ch = '9' + 1; ch < 'A'; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isLetterOrDigit(ch));
    }
    for (int ch = 'Z' + 1; ch < 'a'; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isLetterOrDigit(ch));
    }
    for (int ch = 'z' + 1; ch <= 0xFF; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isLetterOrDigit(ch));
    }

    assertEquals("ch = " + 0, false, Ascii.isLetterOrDigit(0));
    assertEquals("ch = " + Integer.MIN_VALUE, false, Ascii.isLetterOrDigit(Integer.MIN_VALUE));
    assertEquals("ch = " + Integer.MAX_VALUE, false, Ascii.isLetterOrDigit(Integer.MAX_VALUE));
  }

  /**
   * Test method for {@link Ascii#isPrintable(int)}.
   */
  @Test
  public void testIsPrintable() {
    for (int ch = 0x20; ch < 0x7F; ++ch) {
      assertEquals("ch = " + ch, true, Ascii.isPrintable(ch));
    }
    for (int ch = 0; ch < 0x20; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isPrintable(ch));
    }
    for (int ch = 0x7F; ch <= 0xFF; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isPrintable(ch));
    }

    assertEquals("ch = " + 0, false, Ascii.isPrintable(0));
    assertEquals("ch = " + Integer.MIN_VALUE, false, Ascii.isPrintable(Integer.MIN_VALUE));
    assertEquals("ch = " + Integer.MAX_VALUE, false, Ascii.isPrintable(Integer.MAX_VALUE));

  }

  /**
   * Test method for {@link Ascii#isControl(int)}.
   */
  @Test
  public void testIsControl() {
    for (int ch = 0; ch < 0x20; ++ch) {
      assertEquals("ch = " + ch, true, Ascii.isControl(ch));
    }
    for (int ch = 0x20; ch < 0x7F; ++ch) {
      assertEquals("ch = " + ch, false, Ascii.isControl(ch));
    }
    assertEquals("ch = " + 0x7F, true, Ascii.isControl(0x7F));
    for (int ch = 0; ch < 0; --ch) {
      assertEquals("ch = " + ch, false, Ascii.isControl(ch));
    }

    assertEquals("ch = " + 0, true, Ascii.isControl(0));
    assertEquals("ch = " + Integer.MIN_VALUE, false, Ascii.isControl(Integer.MIN_VALUE));
    assertEquals("ch = " + Integer.MAX_VALUE, false, Ascii.isControl(Integer.MAX_VALUE));
    assertEquals("ch = " + Byte.MAX_VALUE, true, Ascii.isControl(Byte.MAX_VALUE));
  }

  /**
   * Test method for {@link Ascii#equalsIgnoreCase(int, int)}.
   */
  @Test
  public void testEqualsIgnoreCase() {
    for (int ch = 0; ch <= Ascii.MAX; ++ch) {
      assertEquals("ch = " + ch, true, Ascii.equalsIgnoreCase(ch, ch));
    }

    for (int ch = 'A'; ch <= 'Z'; ++ch) {
      assertEquals("ch = " + ch, true, Ascii.equalsIgnoreCase(ch, ch + 0x20));
      assertEquals("ch = " + (ch + 0x20), true, Ascii.equalsIgnoreCase(ch + 0x20, ch));
    }

    for (int ch1 = 0; ch1 <= Ascii.MAX; ++ch1) {
      for (int ch2 = 0; ch2 <= Ascii.MAX; ++ch2) {
        if (ch1 == ch2) {
          assertEquals("ch1 = " + ch1 + " ch2 = " + ch2,
              true, Ascii.equalsIgnoreCase(ch1, ch2));
        } else if ((ch1 + 0x20 == ch2)
            && Ascii.isUppercaseLetter(ch1)
            && Ascii.isLowercaseLetter(ch2)) {
          assertEquals("ch1 = " + ch1 + " ch2 = " + ch2,
              true, Ascii.equalsIgnoreCase(ch1, ch2));
        } else if ((ch1 - 0x20 == ch2)
            && Ascii.isLowercaseLetter(ch1)
            && Ascii.isUppercaseLetter(ch2)) {
          assertEquals("ch1 = " + ch1 + " ch2 = " + ch2,
              true, Ascii.equalsIgnoreCase(ch1, ch2));
        } else {
          assertEquals("ch1 = " + ch1 + " ch2 = " + ch2,
              false, Ascii.equalsIgnoreCase(ch1, ch2));
        }
      }
    }

    int ch1 = Integer.MIN_VALUE;
    int ch2 = Integer.MIN_VALUE;
    assertEquals("ch1 = " + ch1 + " ch2 = " + ch2,
        true, Ascii.equalsIgnoreCase(ch1, ch2));

    ch1 = Integer.MAX_VALUE;
    ch2 = Integer.MAX_VALUE;
    assertEquals("ch1 = " + ch1 + " ch2 = " + ch2,
        true, Ascii.equalsIgnoreCase(ch1, ch2));

    ch1 = Integer.MIN_VALUE;
    ch2 = Integer.MAX_VALUE;
    assertEquals("ch1 = " + ch1 + " ch2 = " + ch2,
        false, Ascii.equalsIgnoreCase(ch1, ch2));
  }

  /**
   * Test method for {@link Ascii#toUppercase(byte)}.
   */
  @Test
  public void testToUppercaseByte() {
    for (byte ch = 0; ch >= 0; ++ch) {
      if (Ascii.isLowercaseLetter(ch)) {
        assertEquals("ch = " + ch, ch - 0x20, Ascii.toUppercase(ch));
      } else {
        assertEquals("ch = " + ch, ch, Ascii.toUppercase(ch));
      }
    }
    for (byte ch = 0; ch < 0; --ch) {
      assertEquals("ch = " + ch, ch, Ascii.toUppercase(ch));
    }
  }

  /**
   * Test method for {@link Ascii#toUppercase(char)}.
   */
  @Test
  public void testToUppercaseChar() {
    for (char ch = 0; ch <= Ascii.MAX; ++ch) {
      if (Ascii.isLowercaseLetter(ch)) {
        assertEquals("ch = " + ch, ch - 0x20, Ascii.toUppercase(ch));
      } else {
        assertEquals("ch = " + ch, ch, Ascii.toUppercase(ch));
      }
    }

    char ch = Character.MIN_VALUE;
    assertEquals("ch = " + ch, ch, Ascii.toUppercase(ch));

    ch = Character.MAX_VALUE;
    assertEquals("ch = " + ch, ch, Ascii.toUppercase(ch));

    ch = 0;
    assertEquals("ch = " + ch, ch, Ascii.toUppercase(ch));
  }

  /**
   * Test method for {@link Ascii#toUppercase(int)}.
   */
  @Test
  public void testToUppercaseInt() {
    for (int ch = 0; ch <= Ascii.MAX; ++ch) {
      if (Ascii.isLowercaseLetter(ch)) {
        assertEquals("ch = " + ch, ch - 0x20, Ascii.toUppercase(ch));
      } else {
        assertEquals("ch = " + ch, ch, Ascii.toUppercase(ch));
      }
    }

    int ch = Integer.MIN_VALUE;
    assertEquals("ch = " + ch, ch, Ascii.toUppercase(ch));

    ch = Integer.MAX_VALUE;
    assertEquals("ch = " + ch, ch, Ascii.toUppercase(ch));

    ch = 0;
    assertEquals("ch = " + ch, ch, Ascii.toUppercase(ch));
  }

  /**
   * Test method for {@link Ascii#toLowercase(byte)}.
   */
  @Test
  public void testToLowercaseByte() {
    for (byte ch = 0; ch >= 0; ++ch) {
      if (Ascii.isUppercaseLetter(ch)) {
        assertEquals("ch = " + ch, ch + 0x20, Ascii.toLowercase(ch));
      } else {
        assertEquals("ch = " + ch, ch, Ascii.toLowercase(ch));
      }
    }
    for (byte ch = 0; ch < 0; --ch) {
      assertEquals("ch = " + ch, ch, Ascii.toLowercase(ch));
    }
  }

  /**
   * Test method for {@link Ascii#toLowercase(char)}.
   */
  @Test
  public void testToLowercaseChar() {
    for (char ch = 0; ch <= Ascii.MAX; ++ch) {
      if (Ascii.isUppercaseLetter(ch)) {
        assertEquals("ch = " + ch, ch + 0x20, Ascii.toLowercase(ch));
      } else {
        assertEquals("ch = " + ch, ch, Ascii.toLowercase(ch));
      }
    }

    char ch = Character.MIN_VALUE;
    assertEquals("ch = " + ch, ch, Ascii.toLowercase(ch));

    ch = Character.MAX_VALUE;
    assertEquals("ch = " + ch, ch, Ascii.toLowercase(ch));

    ch = 0;
    assertEquals("ch = " + ch, ch, Ascii.toLowercase(ch));
  }

  /**
   * Test method for {@link Ascii#toLowercase(int)}.
   */
  @Test
  public void testToLowercaseInt() {
    for (int ch = 0; ch <= Ascii.MAX; ++ch) {
      if (Ascii.isUppercaseLetter(ch)) {
        assertEquals("ch = " + ch, ch + 0x20, Ascii.toLowercase(ch));
      } else {
        assertEquals("ch = " + ch, ch, Ascii.toLowercase(ch));
      }
    }

    int ch = Integer.MIN_VALUE;
    assertEquals("ch = " + ch, ch, Ascii.toLowercase(ch));

    ch = Integer.MAX_VALUE;
    assertEquals("ch = " + ch, ch, Ascii.toLowercase(ch));

    ch = 0;
    assertEquals("ch = " + ch, ch, Ascii.toLowercase(ch));
  }

  /**
   * Test method for {@link Ascii#toDigit(int)}.
   */
  @Test
  public void testToDigit() {
    for (int ch = '0'; ch <= '9'; ++ch) {
      assertEquals("ch = " + ch, ch - '0', Ascii.toDigit(ch));
    }
    for (int ch = 0; ch < '0'; ++ch) {
      assertEquals("ch = " + ch, -1, Ascii.toDigit(ch));
    }
    for (int ch = '9' + 1; ch <= 0xFF; ++ch) {
      assertEquals("ch = " + ch, -1, Ascii.toDigit(ch));
    }

    assertEquals("ch = " + 0, -1, Ascii.toDigit(0));
    assertEquals("ch = " + Integer.MAX_VALUE, -1, Ascii.toDigit(Integer.MAX_VALUE));
    assertEquals("ch = " + Integer.MIN_VALUE, -1, Ascii.toDigit(Integer.MIN_VALUE));
  }

  /**
   * Test method for {@link Ascii#toHexDigit(int)}.
   */
  @Test
  public void testToHexDigit() {
    for (int ch = '0'; ch <= '9'; ++ch) {
      assertEquals("ch = " + ch, ch - '0', Ascii.toHexDigit(ch));
    }
    for (int ch = 'A'; ch <= 'F'; ++ch) {
      assertEquals("ch = " + ch, ch - 'A' + 10, Ascii.toHexDigit(ch));
    }
    for (int ch = 'a'; ch <= 'f'; ++ch) {
      assertEquals("ch = " + ch, ch - 'a' + 10, Ascii.toHexDigit(ch));
    }
    for (int ch = 0; ch < '0'; ++ch) {
      assertEquals("ch = " + ch, -1, Ascii.toHexDigit(ch));
    }
    for (int ch = '9' + 1; ch < 'A'; ++ch) {
      assertEquals("ch = " + ch, -1, Ascii.toHexDigit(ch));
    }
    for (int ch = 'F' + 1; ch < 'a'; ++ch) {
      assertEquals("ch = " + ch, -1, Ascii.toHexDigit(ch));
    }
    for (int ch = 'f' + 1; ch <= 0xFF; ++ch) {
      assertEquals("ch = " + ch, -1, Ascii.toHexDigit(ch));
    }


    assertEquals("ch = " + 0, -1, Ascii.toHexDigit(0));
    assertEquals("ch = " + Integer.MAX_VALUE, -1, Ascii.toHexDigit(Integer.MAX_VALUE));
    assertEquals("ch = " + Integer.MIN_VALUE, -1, Ascii.toHexDigit(Integer.MIN_VALUE));
  }

}
