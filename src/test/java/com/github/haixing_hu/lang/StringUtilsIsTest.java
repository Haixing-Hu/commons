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
package com.github.haixing_hu.lang;

import org.junit.Test;

import com.github.haixing_hu.lang.StringUtils;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for the IsXXXX functions of Strings class.
 *
 * @author Haixing Hu
 */
public class StringUtilsIsTest {

  @Test
  public void testIsEmpty() {
    assertEquals(true, StringUtils.isEmpty(null));
    assertEquals(true, StringUtils.isEmpty(""));
    assertEquals(false, StringUtils.isEmpty(" "));
    assertEquals(false, StringUtils.isEmpty("foo"));
    assertEquals(false, StringUtils.isEmpty("  foo  "));
  }

  @Test
  public void testIsNotEmpty() {
    assertEquals(false, StringUtils.isNotEmpty(null));
    assertEquals(false, StringUtils.isNotEmpty(""));
    assertEquals(true, StringUtils.isNotEmpty(" "));
    assertEquals(true, StringUtils.isNotEmpty("foo"));
    assertEquals(true, StringUtils.isNotEmpty("  foo  "));
  }

  @Test
  public void testIsBlank() {
    assertEquals(true, StringUtils.isBlank(null));
    assertEquals(true, StringUtils.isBlank(""));
    assertEquals(true, StringUtils.isBlank(StringUtilsTest.WHITESPACE));
    assertEquals(true, StringUtils.isBlank(StringUtilsTest.BLANK));
    assertEquals(true, StringUtils.isBlank(StringUtilsTest.NON_WHITE_SPACE_BUT_BLANK));
    assertEquals(false, StringUtils.isBlank("foo"));
    assertEquals(false, StringUtils.isBlank("  foo  "));
  }

  @Test
  public void testIsLetter() {
    assertEquals(false, StringUtils.isLetter(null));
    assertEquals(true, StringUtils.isLetter(""));
    assertEquals(false, StringUtils.isLetter(" "));
    assertEquals(true, StringUtils.isLetter("a"));
    assertEquals(true, StringUtils.isLetter("A"));
    assertEquals(true, StringUtils.isLetter("kgKgKgKgkgkGkjkjlJlOKLgHdGdHgl"));
    assertEquals(false, StringUtils.isLetter("ham kso"));
    assertEquals(false, StringUtils.isLetter("1"));
    assertEquals(false, StringUtils.isLetter("hkHKHik6iUGHKJgU7tUJgKJGI87GIkug"));
    assertEquals(false, StringUtils.isLetter("_"));
    assertEquals(false, StringUtils.isLetter("hkHKHik*khbkuh"));
  }

  @Test
  public void testIsLetterDigit() {
    assertEquals(false, StringUtils.isLetterDigit(null));
    assertEquals(true, StringUtils.isLetterDigit(""));
    assertEquals(false, StringUtils.isLetterDigit(" "));
    assertEquals(true, StringUtils.isLetterDigit("a"));
    assertEquals(true, StringUtils.isLetterDigit("A"));
    assertEquals(true, StringUtils.isLetterDigit("kgKgKgKgkgkGkjkjlJlOKLgHdGdHgl"));
    assertEquals(false, StringUtils.isLetterDigit("ham kso"));
    assertEquals(true, StringUtils.isLetterDigit("1"));
    assertEquals(true, StringUtils.isLetterDigit("hkHKHik6iUGHKJgU7tUJgKJGI87GIkug"));
    assertEquals(false, StringUtils.isLetterDigit("_"));
    assertEquals(false, StringUtils.isLetterDigit("hkHKHik*khbkuh"));
  }

  @Test
  public void testIsWhiteSpace() {
    assertEquals(true, StringUtils.isWhitespace(null));
    assertEquals(true, StringUtils.isWhitespace(""));
    assertEquals(true, StringUtils.isWhitespace(" "));
    assertEquals(true, StringUtils.isWhitespace("\t \n \t"));
    assertEquals(true, StringUtils.isWhitespace("\n\r"));
    assertEquals(false, StringUtils.isWhitespace("\t aa\n \t"));
    assertEquals(true, StringUtils.isWhitespace(" "));
    assertEquals(false, StringUtils.isWhitespace(" a "));
    assertEquals(false, StringUtils.isWhitespace("a  "));
    assertEquals(false, StringUtils.isWhitespace("  a"));
    assertEquals(false, StringUtils.isWhitespace("aba"));
    assertEquals(true, StringUtils.isWhitespace(StringUtilsTest.WHITESPACE));
    assertEquals(false, StringUtils.isWhitespace(StringUtilsTest.NON_WHITESPACE));
    assertEquals(false, StringUtils.isWhitespace(StringUtilsTest.NON_WHITE_SPACE_BUT_BLANK));
  }

  @Test
  public void testIsLetterSpace() {
    assertEquals(false, StringUtils.isLetterSpace(null));
    assertEquals(true, StringUtils.isLetterSpace(""));
    assertEquals(true, StringUtils.isLetterSpace(" "));
    assertEquals(true, StringUtils.isLetterSpace("a"));
    assertEquals(true, StringUtils.isLetterSpace("A"));
    assertEquals(true, StringUtils.isLetterSpace("kgKgKgKgkgkGkjkjlJlOKLgHdGdHgl"));
    assertEquals(true, StringUtils.isLetterSpace("ham kso"));
    assertEquals(false, StringUtils.isLetterSpace("1"));
    assertEquals(false, StringUtils.isLetterSpace("hkHKHik6iUGHKJgU7tUJgKJGI87GIkug"));
    assertEquals(false, StringUtils.isLetterSpace("_"));
    assertEquals(false, StringUtils.isLetterSpace("hkHKHik*khbkuh"));
  }

  @Test
  public void testIsLetterDigitSpace() {
    assertEquals(false, StringUtils.isLetterDigitSpace(null));
    assertEquals(true, StringUtils.isLetterDigitSpace(""));
    assertEquals(true, StringUtils.isLetterDigitSpace(" "));
    assertEquals(true, StringUtils.isLetterDigitSpace("a"));
    assertEquals(true, StringUtils.isLetterDigitSpace("A"));
    assertEquals(true, StringUtils.isLetterDigitSpace("kgKgKgKgkgkGkjkjlJlOKLgHdGdHgl"));
    assertEquals(true, StringUtils.isLetterDigitSpace("ham kso"));
    assertEquals(true, StringUtils.isLetterDigitSpace("1"));
    assertEquals(true, StringUtils.isLetterDigitSpace("hkHKHik6iUGHKJgU7tUJgKJGI87GIkug"));
    assertEquals(false, StringUtils.isLetterDigitSpace("_"));
    assertEquals(false, StringUtils.isLetterDigitSpace("hkHKHik*khbkuh"));
  }

  @Test
  public void testIsAsciiPrintable_String() {
    assertEquals(false, StringUtils.isAsciiPrintable(null));
    assertEquals(true, StringUtils.isAsciiPrintable(""));
    assertEquals(true, StringUtils.isAsciiPrintable(" "));
    assertEquals(true, StringUtils.isAsciiPrintable("a"));
    assertEquals(true, StringUtils.isAsciiPrintable("A"));
    assertEquals(true, StringUtils.isAsciiPrintable("1"));
    assertEquals(true, StringUtils.isAsciiPrintable("Ceki"));
    assertEquals(true, StringUtils.isAsciiPrintable("!ab2c~"));
    assertEquals(true, StringUtils.isAsciiPrintable("1000"));
    assertEquals(true, StringUtils.isAsciiPrintable("10 00"));
    assertEquals(false, StringUtils.isAsciiPrintable("10\t00"));
    assertEquals(true, StringUtils.isAsciiPrintable("10.00"));
    assertEquals(true, StringUtils.isAsciiPrintable("10,00"));
    assertEquals(true, StringUtils.isAsciiPrintable("!ab-c~"));
    assertEquals(true, StringUtils.isAsciiPrintable("hkHK=Hik6i?UGH_KJgU7.tUJgKJ*GI87GI,kug"));
    assertEquals(true, StringUtils.isAsciiPrintable("\u0020"));
    assertEquals(true, StringUtils.isAsciiPrintable("\u0021"));
    assertEquals(true, StringUtils.isAsciiPrintable("\u007e"));
    assertEquals(false, StringUtils.isAsciiPrintable("\u007f"));
    assertEquals(true, StringUtils.isAsciiPrintable("G?lc?"));
    assertEquals(true, StringUtils.isAsciiPrintable("=?iso-8859-1?Q?G=FClc=FC?="));
    assertEquals(false, StringUtils.isAsciiPrintable("G\u00fclc\u00fc"));
  }

  @Test
  public void testIsAscii_String() {
    assertEquals(false, StringUtils.isAscii(null));
    assertEquals(true, StringUtils.isAscii(""));
    assertEquals(true, StringUtils.isAscii(" "));
    assertEquals(true, StringUtils.isAscii("\n"));
    assertEquals(true, StringUtils.isAscii("\r"));
    assertEquals(true, StringUtils.isAscii("\t"));
    assertEquals(true, StringUtils.isAscii("\b"));
    assertEquals(true, StringUtils.isAscii("\\"));
    assertEquals(true, StringUtils.isAscii("a"));
    assertEquals(true, StringUtils.isAscii("A"));
    assertEquals(true, StringUtils.isAscii("1"));
    assertEquals(true, StringUtils.isAscii("Ceki"));
    assertEquals(true, StringUtils.isAscii("!ab2c~"));
    assertEquals(true, StringUtils.isAscii("1000"));
    assertEquals(true, StringUtils.isAscii("10 00"));
    assertEquals(true, StringUtils.isAscii("10\t00"));
    assertEquals(true, StringUtils.isAscii("10.00"));
    assertEquals(true, StringUtils.isAscii("10,00"));
    assertEquals(true, StringUtils.isAscii("!ab-c~"));
    assertEquals(true, StringUtils.isAscii("hkHK=Hik6i?UGH_KJgU7.tUJgKJ*GI87GI,kug"));
    assertEquals(true, StringUtils.isAscii("\u0020"));
    assertEquals(true, StringUtils.isAscii("\u0021"));
    assertEquals(true, StringUtils.isAscii("\u007e"));
    assertEquals(true, StringUtils.isAscii("\u007f"));
    assertEquals(true, StringUtils.isAscii("G?lc?"));
    assertEquals(true, StringUtils.isAscii("=?iso-8859-1?Q?G=FClc=FC?="));
    assertEquals(false, StringUtils.isAscii("G\u00fclc\u00fc"));
  }
  
  @Test
  public void testIsDigit() {
    assertEquals(false, StringUtils.isDigit(null));
    assertEquals(true, StringUtils.isDigit(""));
    assertEquals(false, StringUtils.isDigit(" "));
    assertEquals(false, StringUtils.isDigit("a"));
    assertEquals(false, StringUtils.isDigit("A"));
    assertEquals(false, StringUtils.isDigit("kgKgKgKgkgkGkjkjlJlOKLgHdGdHgl"));
    assertEquals(false, StringUtils.isDigit("ham kso"));
    assertEquals(true, StringUtils.isDigit("1"));
    assertEquals(true, StringUtils.isDigit("1000"));
    assertEquals(false, StringUtils.isDigit("2.3"));
    assertEquals(false, StringUtils.isDigit("10 00"));
    assertEquals(false, StringUtils.isDigit("hkHKHik6iUGHKJgU7tUJgKJGI87GIkug"));
    assertEquals(false, StringUtils.isDigit("_"));
    assertEquals(false, StringUtils.isDigit("hkHKHik*khbkuh"));
  }

  @Test
  public void testIsDigitSpace() {
    assertEquals(false, StringUtils.isDigitSpace(null));
    assertEquals(true, StringUtils.isDigitSpace(""));
    assertEquals(true, StringUtils.isDigitSpace(" "));
    assertEquals(false, StringUtils.isDigitSpace("a"));
    assertEquals(false, StringUtils.isDigitSpace("A"));
    assertEquals(false, StringUtils.isDigitSpace("kgKgKgKgkgkGkjkjlJlOKLgHdGdHgl"));
    assertEquals(false, StringUtils.isDigitSpace("ham kso"));
    assertEquals(true, StringUtils.isDigitSpace("1"));
    assertEquals(true, StringUtils.isDigitSpace("1000"));
    assertEquals(false, StringUtils.isDigitSpace("2.3"));
    assertEquals(true, StringUtils.isDigitSpace("10 00"));
    assertEquals(false, StringUtils.isDigitSpace("hkHKHik6iUGHKJgU7tUJgKJGI87GIkug"));
    assertEquals(false, StringUtils.isDigitSpace("_"));
    assertEquals(false, StringUtils.isDigitSpace("hkHKHik*khbkuh"));
  }
  
  @Test
  public void testIsNumber() {
    assertEquals(false, StringUtils.isNumber(null));
    assertEquals(true, StringUtils.isNumber(""));
    assertEquals(false, StringUtils.isNumber("  "));
    assertEquals(true, StringUtils.isNumber("0100"));
    assertEquals(true, StringUtils.isNumber("0x00"));
    assertEquals(true, StringUtils.isNumber("1234"));
    assertEquals(true, StringUtils.isNumber("1234.123"));
    assertEquals(true, StringUtils.isNumber("+1234.123"));
    assertEquals(true, StringUtils.isNumber("-1234.123"));
    assertEquals(true, StringUtils.isNumber("2e9"));
    assertEquals(true, StringUtils.isNumber("2e-9"));
    assertEquals(true, StringUtils.isNumber("-2e-8"));
    assertEquals(false, StringUtils.isNumber("1a2"));
  }
}
