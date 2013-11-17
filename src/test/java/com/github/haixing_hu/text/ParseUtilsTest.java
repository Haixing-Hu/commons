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

package com.github.haixing_hu.text;

import org.junit.Test;

import com.github.haixing_hu.lang.ByteUtils;
import com.github.haixing_hu.lang.IntUtils;
import com.github.haixing_hu.lang.LongUtils;
import com.github.haixing_hu.lang.ShortUtils;
import com.github.haixing_hu.text.FormatFlag;
import com.github.haixing_hu.text.ParsePosition;
import com.github.haixing_hu.text.ParseUtils;

import static org.junit.Assert.assertEquals;

import static com.github.haixing_hu.text.ErrorCode.*;

/**
 * Unit test for {@link ParseUtils} class.
 *
 * @author Haixing Hu
 */
public class ParseUtilsTest {

  @Test
  public final void testSkipBlanks() {
    final ParsePosition pos = new ParsePosition();
    String str = null;
    // it should deal with the empty string (but not null string)
    str = "";
    pos.setIndex(0);
    ParseUtils.skipBlanks(pos, str, str.length());
    assertEquals(0, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // it should stop after the first white space
    str = " 1234";
    pos.setIndex(0);
    ParseUtils.skipBlanks(pos, str, str.length());
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // it should stop after the leading white space
    str = " \t\u00A0\r\fabcde ";
    pos.setIndex(0);
    ParseUtils.skipBlanks(pos, str, str.length());
    assertEquals(5, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // it should stop after the leading white space
    str = " \t\n\r\fabcde ";
    pos.setIndex(3);
    ParseUtils.skipBlanks(pos, str, 5);
    assertEquals(5, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // it should stop immediately since startIndex == endIndex
    str = " \t\n\r\fabcde ";
    pos.setIndex(3);
    ParseUtils.skipBlanks(pos, str, 3);
    assertEquals(3, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // it should stop after the leading white space
    str = " \t\n\r\fabcde ";
    pos.setIndex(6);
    ParseUtils.skipBlanks(pos, str, 7);
    assertEquals(6, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());
  }

  @Test
  public final void testSkipNonBlanks() {
    final ParsePosition pos = new ParsePosition();
    String str = null;

    // it should deal with the empty string (but not null string)
    str = "";
    pos.setIndex(0);
    ParseUtils.skipNonBlanks(pos, str, str.length());
    assertEquals(0, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // it should stop at the first white space
    str = " 1234 ";
    pos.setIndex(0);
    ParseUtils.skipNonBlanks(pos, str, str.length());
    assertEquals(0, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // it should stop at the last white space
    str = " 1234 ";
    pos.setIndex(1);
    ParseUtils.skipNonBlanks(pos, str, str.length());
    assertEquals(5, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // it should stop immediately since startIndex == endIndex
    str = " 1234";
    pos.setIndex(4);
    ParseUtils.skipNonBlanks(pos, str, 4);
    assertEquals(4, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // it should stop at the first white space
    str = " \t\n\r\fabcde ";
    pos.setIndex(0);
    ParseUtils.skipNonBlanks(pos, str, str.length());
    assertEquals(0, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // it should stop at the second white space
    str = " \t\n\r\fabcde ";
    pos.setIndex(1);
    ParseUtils.skipNonBlanks(pos, str, str.length());
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // it should stop at the forth white space
    str = " \t\n\r\fabcde ";
    pos.setIndex(4);
    ParseUtils.skipNonBlanks(pos, str, str.length());
    assertEquals(4, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // it should stop at the last white space
    str = " \t\n\r\fabcde ";
    pos.setIndex(5);
    ParseUtils.skipNonBlanks(pos, str, str.length());
    assertEquals(10, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());
  }

  @Test
  public final void testGetSign() {
    final ParsePosition pos = new ParsePosition();
    String str = null;
    int sign = 0;

    str = "";
    pos.setIndex(0);
    sign = ParseUtils.getSign(pos, str, str.length(), '+', '-');
    assertEquals(+ 1, sign);
    assertEquals(0, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "0";
    pos.setIndex(0);
    sign = ParseUtils.getSign(pos, str, str.length(), '+', '-');
    assertEquals(+ 1, sign);
    assertEquals(0, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "123";
    pos.setIndex(0);
    sign = ParseUtils.getSign(pos, str, str.length(), '+', '-');
    assertEquals(+ 1, sign);
    assertEquals(0, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "+123";
    pos.setIndex(0);
    sign = ParseUtils.getSign(pos, str, str.length(), '+', '-');
    assertEquals(+ 1, sign);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "-123";
    pos.setIndex(0);
    sign = ParseUtils.getSign(pos, str, str.length(), '+', '-');
    assertEquals(- 1, sign);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "?123";
    pos.setIndex(0);
    sign = ParseUtils.getSign(pos, str, str.length(), '?', '*');
    assertEquals(+ 1, sign);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "*123";
    pos.setIndex(0);
    sign = ParseUtils.getSign(pos, str, str.length(), '?', '*');
    assertEquals(- 1, sign);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());
  }

  @Test
  public final void testGetRadix() {
    final ParsePosition pos = new ParsePosition();
    String str = null;
    int flags = 0;
    int radix = 0;

    str = "";
    flags = 0;
    pos.setIndex(0);
    radix = ParseUtils.getRadix(pos, str, str.length(), flags, 10);
    assertEquals(10, radix);
    assertEquals(0, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "";
    flags = FormatFlag.BINARY;
    pos.setIndex(0);
    radix = ParseUtils.getRadix(pos, str, str.length(), flags, 10);
    assertEquals(2, radix);
    assertEquals(0, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "";
    flags = FormatFlag.DECIMAL;
    pos.setIndex(0);
    radix = ParseUtils.getRadix(pos, str, str.length(), flags, 10);
    assertEquals(10, radix);
    assertEquals(0, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "";
    flags = FormatFlag.HEX;
    pos.setIndex(0);
    radix = ParseUtils.getRadix(pos, str, str.length(), flags, 10);
    assertEquals(16, radix);
    assertEquals(0, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "";
    flags = FormatFlag.HEX | FormatFlag.BINARY;
    pos.setIndex(0);
    radix = ParseUtils.getRadix(pos, str, str.length(), flags, 8);
    assertEquals(8, radix);
    assertEquals(0, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "0b1010110";
    flags = FormatFlag.BINARY;
    pos.setIndex(0);
    radix = ParseUtils.getRadix(pos, str, str.length(), flags, 10);
    assertEquals(2, radix);
    assertEquals(2, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "0B1010110";
    flags = FormatFlag.BINARY;
    pos.setIndex(0);
    radix = ParseUtils.getRadix(pos, str, str.length(), flags, 10);
    assertEquals(2, radix);
    assertEquals(2, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "012345";
    flags = FormatFlag.OCTAL;
    pos.setIndex(0);
    radix = ParseUtils.getRadix(pos, str, str.length(), flags, 10);
    assertEquals(8, radix);
    assertEquals(0, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "012345";
    flags = FormatFlag.DECIMAL;
    pos.setIndex(0);
    radix = ParseUtils.getRadix(pos, str, str.length(), flags, 10);
    assertEquals(10, radix);
    assertEquals(0, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "0x12345";
    flags = FormatFlag.HEX;
    pos.setIndex(0);
    radix = ParseUtils.getRadix(pos, str, str.length(), flags, 10);
    assertEquals(16, radix);
    assertEquals(2, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "0X12345";
    flags = FormatFlag.HEX;
    pos.setIndex(0);
    radix = ParseUtils.getRadix(pos, str, str.length(), flags, 10);
    assertEquals(16, radix);
    assertEquals(2, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "0b12345";
    flags = 0;
    pos.setIndex(0);
    radix = ParseUtils.getRadix(pos, str, str.length(), flags, 10);
    assertEquals(2, radix);
    assertEquals(2, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "0B12345";
    flags = 0;
    pos.setIndex(0);
    radix = ParseUtils.getRadix(pos, str, str.length(), flags, 10);
    assertEquals(2, radix);
    assertEquals(2, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "012345";
    flags = 0;
    pos.setIndex(0);
    radix = ParseUtils.getRadix(pos, str, str.length(), flags, 10);
    assertEquals(8, radix);
    assertEquals(0, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "12345";
    flags = 0;
    pos.setIndex(0);
    radix = ParseUtils.getRadix(pos, str, str.length(), flags, 10);
    assertEquals(10, radix);
    assertEquals(0, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "0x12345";
    flags = 0;
    pos.setIndex(0);
    radix = ParseUtils.getRadix(pos, str, str.length(), flags, 10);
    assertEquals(16, radix);
    assertEquals(2, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "0X12345";
    flags = 0;
    pos.setIndex(0);
    radix = ParseUtils.getRadix(pos, str, str.length(), flags, 10);
    assertEquals(16, radix);
    assertEquals(2, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());
  }

  @Test
  public final void testGetSpecialRadixInt() {
    final ParsePosition pos = new ParsePosition();
    String str = null;
    int value = 0;

    // //////////////////////////////////////////////////////
    // Test for empty string
    // //////////////////////////////////////////////////////

    str = "";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(0, value);
    assertEquals(0, pos.getIndex());
    assertEquals(EMPTY_VALUE, pos.getErrorCode());
    assertEquals(0, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for empty error
    // //////////////////////////////////////////////////////

    str = "zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(0, value);
    assertEquals(0, pos.getIndex());
    assertEquals(EMPTY_VALUE, pos.getErrorCode());
    assertEquals(0, pos.getErrorIndex());

    str = "zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(0, value);
    assertEquals(0, pos.getIndex());
    assertEquals(EMPTY_VALUE, pos.getErrorCode());
    assertEquals(0, pos.getErrorIndex());

    str = "zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(0, value);
    assertEquals(0, pos.getIndex());
    assertEquals(EMPTY_VALUE, pos.getErrorCode());
    assertEquals(0, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for single digit
    // //////////////////////////////////////////////////////

    str = "0zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(0, value);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "1zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(1, value);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "0zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(0, value);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "7zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(7, value);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "0zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(0, value);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "Fzzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(15, value);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for normal value
    // //////////////////////////////////////////////////////

    str = "1111011zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(123, value);
    assertEquals(7, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "173zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(123, value);
    assertEquals(3, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "7Bzzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(123, value);
    assertEquals(2, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "11000000111001zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(12345, value);
    assertEquals(14, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "30071zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(12345, value);
    assertEquals(5, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "3039zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(12345, value);
    assertEquals(4, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "1001001100101100000001011010010zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(1234567890, value);
    assertEquals(31, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "11145401322zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(1234567890, value);
    assertEquals(11, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "499602D2zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(1234567890, value);
    assertEquals(8, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for normal value with leading zeros
    // //////////////////////////////////////////////////////

    str = "000001111011zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        ByteUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(123, value);
    assertEquals(12, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "00000173zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        ByteUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(123, value);
    assertEquals(8, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "000007Bzzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        ByteUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(123, value);
    assertEquals(7, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "0000011000000111001zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        ShortUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(12345, value);
    assertEquals(19, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "0000030071zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        ShortUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(12345, value);
    assertEquals(10, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "000003039zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        ShortUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(12345, value);
    assertEquals(9, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "000001001001100101100000001011010010zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(1234567890, value);
    assertEquals(36, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "0000011145401322zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(1234567890, value);
    assertEquals(16, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "00000499602D2zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(1234567890, value);
    assertEquals(13, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for all zeros value
    // //////////////////////////////////////////////////////

    str = "00000000000000000000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(0, value);
    assertEquals(32, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "00000000000000000000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(0, value);
    assertEquals(32, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "00000000000000000000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(0, value);
    assertEquals(32, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for minimum signed value
    // //////////////////////////////////////////////////////

    str = "10000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        ByteUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(value, (Byte.MIN_VALUE & ByteUtils.UNSIGNED_MAX));
    assertEquals(8, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "200zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        ByteUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(value, (Byte.MIN_VALUE & ByteUtils.UNSIGNED_MAX));
    assertEquals(3, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "80zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        ByteUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(value, (Byte.MIN_VALUE & ByteUtils.UNSIGNED_MAX));
    assertEquals(2, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "1000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        ShortUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(value, (Short.MIN_VALUE & ShortUtils.UNSIGNED_MAX));
    assertEquals(16, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "100000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        ShortUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(value, (Short.MIN_VALUE & ShortUtils.UNSIGNED_MAX));
    assertEquals(6, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "8000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        ShortUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(value, (Short.MIN_VALUE & ShortUtils.UNSIGNED_MAX));
    assertEquals(4, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "10000000000000000000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(Integer.MIN_VALUE, value);
    assertEquals(32, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "20000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(Integer.MIN_VALUE, value);
    assertEquals(11, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "80000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(Integer.MIN_VALUE, value);
    assertEquals(8, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for maximum signed value
    // //////////////////////////////////////////////////////

    str = "1111111zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        ByteUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(value, (Byte.MAX_VALUE & ByteUtils.UNSIGNED_MAX));
    assertEquals(7, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "177zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        ByteUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(value, (Byte.MAX_VALUE & ByteUtils.UNSIGNED_MAX));
    assertEquals(3, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "7Fzzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        ByteUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(value, (Byte.MAX_VALUE & ByteUtils.UNSIGNED_MAX));
    assertEquals(2, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "111111111111111zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        ShortUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(value, (Short.MAX_VALUE & ShortUtils.UNSIGNED_MAX));
    assertEquals(15, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "77777zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        ShortUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(value, (Short.MAX_VALUE & ShortUtils.UNSIGNED_MAX));
    assertEquals(5, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "7FFFzzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        ShortUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(value, (Short.MAX_VALUE & ShortUtils.UNSIGNED_MAX));
    assertEquals(4, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "1111111111111111111111111111111zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(value, (Integer.MAX_VALUE & IntUtils.UNSIGNED_MAX));
    assertEquals(31, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "17777777777zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(value, (Integer.MAX_VALUE & IntUtils.UNSIGNED_MAX));
    assertEquals(11, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "7FFFFFFFzzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(value, (Integer.MAX_VALUE & IntUtils.UNSIGNED_MAX));
    assertEquals(8, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for maximum unsigned value
    // //////////////////////////////////////////////////////

    str = "11111111zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        ByteUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(ByteUtils.UNSIGNED_MAX, value);
    assertEquals(8, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "377zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        ByteUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(ByteUtils.UNSIGNED_MAX, value);
    assertEquals(3, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "FFzzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        ByteUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(ByteUtils.UNSIGNED_MAX, value);
    assertEquals(2, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "1111111111111111zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        ShortUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(ShortUtils.UNSIGNED_MAX, value);
    assertEquals(16, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "177777zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        ShortUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(ShortUtils.UNSIGNED_MAX, value);
    assertEquals(6, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "FFFFzzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        ShortUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(ShortUtils.UNSIGNED_MAX, value);
    assertEquals(4, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "11111111111111111111111111111111zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(IntUtils.UNSIGNED_MAX, value);
    assertEquals(32, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "37777777777zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(IntUtils.UNSIGNED_MAX, value);
    assertEquals(11, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "FFFFFFFFzzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(IntUtils.UNSIGNED_MAX, value);
    assertEquals(8, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for maximum unsigned value + 1, which overflows
    // //////////////////////////////////////////////////////

    str = "100000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        ByteUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(ByteUtils.UNSIGNED_MAX, value);
    assertEquals(9, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(8, pos.getErrorIndex());

    str = "400zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        ByteUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(ByteUtils.UNSIGNED_MAX, value);
    assertEquals(3, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(2, pos.getErrorIndex());

    str = "100zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        ByteUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(ByteUtils.UNSIGNED_MAX, value);
    assertEquals(3, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(2, pos.getErrorIndex());

    str = "10000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        ShortUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(ShortUtils.UNSIGNED_MAX, value);
    assertEquals(17, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(16, pos.getErrorIndex());

    str = "200000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        ShortUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(ShortUtils.UNSIGNED_MAX, value);
    assertEquals(6, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(5, pos.getErrorIndex());

    str = "10000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        ShortUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(ShortUtils.UNSIGNED_MAX, value);
    assertEquals(5, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(4, pos.getErrorIndex());

    str = "100000000000000000000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(IntUtils.UNSIGNED_MAX, value);
    assertEquals(33, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(32, pos.getErrorIndex());

    str = "40000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(IntUtils.UNSIGNED_MAX, value);
    assertEquals(11, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(10, pos.getErrorIndex());

    str = "100000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(IntUtils.UNSIGNED_MAX, value);
    assertEquals(9, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(8, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for maximum unsigned value * radix, which overflows
    // //////////////////////////////////////////////////////

    str = "111111110zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        ByteUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(ByteUtils.UNSIGNED_MAX, value);
    assertEquals(9, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(8, pos.getErrorIndex());

    str = "3770zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        ByteUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(ByteUtils.UNSIGNED_MAX, value);
    assertEquals(4, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(3, pos.getErrorIndex());

    str = "FF0zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        ByteUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(ByteUtils.UNSIGNED_MAX, value);
    assertEquals(3, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(2, pos.getErrorIndex());

    str = "11111111111111110zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        ShortUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(ShortUtils.UNSIGNED_MAX, value);
    assertEquals(17, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(16, pos.getErrorIndex());

    str = "1777770zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        ShortUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(ShortUtils.UNSIGNED_MAX, value);
    assertEquals(7, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(6, pos.getErrorIndex());

    str = "FFFF0zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        ShortUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(ShortUtils.UNSIGNED_MAX, value);
    assertEquals(5, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(4, pos.getErrorIndex());

    str = "111111111111111111111111111111110zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 2,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(IntUtils.UNSIGNED_MAX, value);
    assertEquals(33, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(32, pos.getErrorIndex());

    str = "377777777770zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 8,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(IntUtils.UNSIGNED_MAX, value);
    assertEquals(12, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(11, pos.getErrorIndex());

    str = "FFFFFFFF0zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixInt(pos, str, str.length(), + 1, 16,
        IntUtils.UNSIGNED_MAX, Integer.MAX_VALUE);
    assertEquals(IntUtils.UNSIGNED_MAX, value);
    assertEquals(9, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(8, pos.getErrorIndex());

  }

  @Test
  public final void testGetSpecialRadixLong() {
    final ParsePosition pos = new ParsePosition();
    String str = null;
    long value = 0;

    // //////////////////////////////////////////////////////
    // Test for empty string
    // //////////////////////////////////////////////////////

    str = "";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 2,
        Integer.MAX_VALUE);
    assertEquals(0L, value);
    assertEquals(0, pos.getIndex());
    assertEquals(EMPTY_VALUE, pos.getErrorCode());
    assertEquals(0, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for empty error
    // //////////////////////////////////////////////////////

    str = "zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 2,
        Integer.MAX_VALUE);
    assertEquals(0L, value);
    assertEquals(0, pos.getIndex());
    assertEquals(EMPTY_VALUE, pos.getErrorCode());
    assertEquals(0, pos.getErrorIndex());

    str = "zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 8,
        Integer.MAX_VALUE);
    assertEquals(0L, value);
    assertEquals(0, pos.getIndex());
    assertEquals(EMPTY_VALUE, pos.getErrorCode());
    assertEquals(0, pos.getErrorIndex());

    str = "zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 16,
        Integer.MAX_VALUE);
    assertEquals(0L, value);
    assertEquals(0, pos.getIndex());
    assertEquals(EMPTY_VALUE, pos.getErrorCode());
    assertEquals(0, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for single digit
    // //////////////////////////////////////////////////////

    str = "0zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 2,
        Integer.MAX_VALUE);
    assertEquals(0L, value);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "1zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 2,
        Integer.MAX_VALUE);
    assertEquals(1L, value);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "0zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 8,
        Integer.MAX_VALUE);
    assertEquals(0L, value);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "7zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 8,
        Integer.MAX_VALUE);
    assertEquals(7L, value);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "0zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 16,
        Integer.MAX_VALUE);
    assertEquals(0L, value);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "Fzzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 16,
        Integer.MAX_VALUE);
    assertEquals(15L, value);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for normal value
    // //////////////////////////////////////////////////////

    str = "1000100100010000100001111010001111101111010011000000100010101zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 2,
        Integer.MAX_VALUE);
    assertEquals(1234567890123456789L, value);
    assertEquals(61, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "104420417217572300425zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 8,
        Integer.MAX_VALUE);
    assertEquals(1234567890123456789L, value);
    assertEquals(21, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "112210F47DE98115zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 16,
        Integer.MAX_VALUE);
    assertEquals(1234567890123456789L, value);
    assertEquals(16, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for normal value with leading zeros
    // //////////////////////////////////////////////////////

    str = "000001000100100010000100001111010001111101111010011000000100010101zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 2,
        Integer.MAX_VALUE);
    assertEquals(1234567890123456789L, value);
    assertEquals(66, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "00000104420417217572300425zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 8,
        Integer.MAX_VALUE);
    assertEquals(1234567890123456789L, value);
    assertEquals(26, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "00000112210F47DE98115zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 16,
        Integer.MAX_VALUE);
    assertEquals(1234567890123456789L, value);
    assertEquals(21, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for all zeros value
    // //////////////////////////////////////////////////////

    str = "00000000000000000000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 2,
        Integer.MAX_VALUE);
    assertEquals(0L, value);
    assertEquals(32, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "00000000000000000000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 8,
        Integer.MAX_VALUE);
    assertEquals(0L, value);
    assertEquals(32, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "00000000000000000000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 16,
        Integer.MAX_VALUE);
    assertEquals(0L, value);
    assertEquals(32, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for minimum signed value
    // //////////////////////////////////////////////////////

    str = "1000000000000000000000000000000000000000000000000000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 2,
        Integer.MAX_VALUE);
    assertEquals(Long.MIN_VALUE, value);
    assertEquals(64, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "1000000000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 8,
        Integer.MAX_VALUE);
    assertEquals(Long.MIN_VALUE, value);
    assertEquals(22, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "8000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 16,
        Integer.MAX_VALUE);
    assertEquals(Long.MIN_VALUE, value);
    assertEquals(16, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for maximum signed value
    // //////////////////////////////////////////////////////

    str = "111111111111111111111111111111111111111111111111111111111111111zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 2,
        Integer.MAX_VALUE);
    assertEquals(Long.MAX_VALUE, value);
    assertEquals(63, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "777777777777777777777zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 8,
        Integer.MAX_VALUE);
    assertEquals(Long.MAX_VALUE, value);
    assertEquals(21, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "7FFFFFFFFFFFFFFFzzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 16,
        Integer.MAX_VALUE);
    assertEquals(Long.MAX_VALUE, value);
    assertEquals(16, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for maximum unsigned value
    // //////////////////////////////////////////////////////

    str = "1111111111111111111111111111111111111111111111111111111111111111zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 2,
        Integer.MAX_VALUE);
    assertEquals(LongUtils.UNSIGNED_MAX, value);
    assertEquals(64, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "1777777777777777777777zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 8,
        Integer.MAX_VALUE);
    assertEquals(LongUtils.UNSIGNED_MAX, value);
    assertEquals(22, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "FFFFFFFFFFFFFFFFzzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 16,
        Integer.MAX_VALUE);
    assertEquals(LongUtils.UNSIGNED_MAX, value);
    assertEquals(16, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for maximum unsigned value + 1, which overflows
    // //////////////////////////////////////////////////////

    str = "10000000000000000000000000000000000000000000000000000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 2,
        Integer.MAX_VALUE);
    assertEquals(LongUtils.UNSIGNED_MAX, value);
    assertEquals(65, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(64, pos.getErrorIndex());

    str = "2000000000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 8,
        Integer.MAX_VALUE);
    assertEquals(LongUtils.UNSIGNED_MAX, value);
    assertEquals(22, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(21, pos.getErrorIndex());

    str = "10000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 16,
        Integer.MAX_VALUE);
    assertEquals(LongUtils.UNSIGNED_MAX, value);
    assertEquals(17, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(16, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for maximum unsigned value * radix, which overflows
    // //////////////////////////////////////////////////////

    str = "11111111111111111111111111111111111111111111111111111111111111110zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 2,
        Integer.MAX_VALUE);
    assertEquals(LongUtils.UNSIGNED_MAX, value);
    assertEquals(65, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(64, pos.getErrorIndex());

    str = "17777777777777777777770zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 8,
        Integer.MAX_VALUE);
    assertEquals(LongUtils.UNSIGNED_MAX, value);
    assertEquals(23, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(22, pos.getErrorIndex());

    str = "FFFFFFFFFFFFFFFF0zzz";
    pos.setIndex(0);
    value = ParseUtils.getSpecialRadixLong(pos, str, str.length(), + 1, 16,
        Integer.MAX_VALUE);
    assertEquals(LongUtils.UNSIGNED_MAX, value);
    assertEquals(17, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(16, pos.getErrorIndex());

  }

  @Test
  public final void testGetDecimalInt() {
    final ParsePosition pos = new ParsePosition();
    String str = null;
    int value = 0;

    // //////////////////////////////////////////////////////
    // Test for empty string
    // //////////////////////////////////////////////////////

    str = "";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(0, value);
    assertEquals(0, pos.getIndex());
    assertEquals(EMPTY_VALUE, pos.getErrorCode());
    assertEquals(0, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for empty error
    // //////////////////////////////////////////////////////

    str = "zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(0, value);
    assertEquals(0, pos.getIndex());
    assertEquals(EMPTY_VALUE, pos.getErrorCode());
    assertEquals(0, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for single digit
    // //////////////////////////////////////////////////////

    str = "0zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(0, value);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "0zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(0, value);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "9zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(9, value);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "9zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(- 9, value);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for normal value
    // //////////////////////////////////////////////////////

    str = "123zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Byte.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(123, value);
    assertEquals(3, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "123zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Byte.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(- 123, value);
    assertEquals(3, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "12345zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Short.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(12345, value);
    assertEquals(5, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "12345zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Short.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(- 12345, value);
    assertEquals(5, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "1234567890zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(1234567890, value);
    assertEquals(10, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "1234567890zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(- 1234567890, value);
    assertEquals(10, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for normal value with leading zeros
    // //////////////////////////////////////////////////////

    str = "0000123zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Byte.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(123, value);
    assertEquals(7, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "0000123zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Byte.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(- 123, value);
    assertEquals(7, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "000012345zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Short.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(12345, value);
    assertEquals(9, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "000012345zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Short.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(- 12345, value);
    assertEquals(9, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "00001234567890zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(1234567890, value);
    assertEquals(14, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "00001234567890zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(- 1234567890, value);
    assertEquals(14, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for all zeros value
    // //////////////////////////////////////////////////////

    str = "00000000000000000000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Byte.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(0, value);
    assertEquals(32, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "00000000000000000000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Byte.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(0, value);
    assertEquals(32, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "00000000000000000000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Short.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(0, value);
    assertEquals(32, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "00000000000000000000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Short.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(0, value);
    assertEquals(32, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "00000000000000000000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(0, value);
    assertEquals(32, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "00000000000000000000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(0, value);
    assertEquals(32, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for minimum signed value
    // //////////////////////////////////////////////////////

    str = "128zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Byte.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Byte.MIN_VALUE, value);
    assertEquals(3, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "32768zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Short.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Short.MIN_VALUE, value);
    assertEquals(5, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "2147483648zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Integer.MIN_VALUE, value);
    assertEquals(10, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for maximum signed value
    // //////////////////////////////////////////////////////

    str = "127zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Byte.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Byte.MAX_VALUE, value);
    assertEquals(3, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "32767zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Short.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Short.MAX_VALUE, value);
    assertEquals(5, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "2147483647zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Integer.MAX_VALUE, value);
    assertEquals(10, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for minimum signed value + 1
    // //////////////////////////////////////////////////////

    str = "127zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Byte.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Byte.MIN_VALUE + 1, value);
    assertEquals(3, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "32767zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Short.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Short.MIN_VALUE + 1, value);
    assertEquals(5, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "2147483647zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Integer.MIN_VALUE + 1, value);
    assertEquals(10, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for maximum signed value - 1
    // //////////////////////////////////////////////////////

    str = "126zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Byte.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Byte.MAX_VALUE - 1, value);
    assertEquals(3, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "32766zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Short.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Short.MAX_VALUE - 1, value);
    assertEquals(5, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "2147483646zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Integer.MAX_VALUE - 1, value);
    assertEquals(10, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for minimum signed value - 1, which overflows
    // //////////////////////////////////////////////////////

    str = "129zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Byte.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Byte.MIN_VALUE, value);
    assertEquals(3, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(2, pos.getErrorIndex());

    str = "32769zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Short.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Short.MIN_VALUE, value);
    assertEquals(5, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(4, pos.getErrorIndex());

    str = "2147483649zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Integer.MIN_VALUE, value);
    assertEquals(10, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(9, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for maximum signed value + 1, which overflows
    // //////////////////////////////////////////////////////

    str = "128zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Byte.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Byte.MAX_VALUE, value);
    assertEquals(3, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(2, pos.getErrorIndex());

    str = "32768zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Short.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Short.MAX_VALUE, value);
    assertEquals(5, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(4, pos.getErrorIndex());

    str = "2147483648zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Integer.MAX_VALUE, value);
    assertEquals(10, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(9, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for minimum signed value * 10, which overflows
    // //////////////////////////////////////////////////////

    str = "1280zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Byte.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Byte.MIN_VALUE, value);
    assertEquals(4, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(3, pos.getErrorIndex());

    str = "327680zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Short.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Short.MIN_VALUE, value);
    assertEquals(6, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(5, pos.getErrorIndex());

    str = "21474836480zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), - 1,
        Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Integer.MIN_VALUE, value);
    assertEquals(11, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(10, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for maximum signed value * 10, which overflows
    // //////////////////////////////////////////////////////

    str = "1270zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Byte.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Byte.MAX_VALUE, value);
    assertEquals(4, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(3, pos.getErrorIndex());

    str = "327670zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Short.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Short.MAX_VALUE, value);
    assertEquals(6, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(5, pos.getErrorIndex());

    str = "21474836470zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalInt(pos, str, str.length(), + 1,
        Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertEquals(Integer.MAX_VALUE, value);
    assertEquals(11, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(10, pos.getErrorIndex());

  }

  @Test
  public final void testGetDecimalLong() {
    final ParsePosition pos = new ParsePosition();
    String str = null;
    long value = 0;

    // //////////////////////////////////////////////////////
    // Test for empty string
    // //////////////////////////////////////////////////////

    str = "";
    pos.setIndex(0);
    value = ParseUtils.getDecimalLong(pos, str, str.length(), + 1,
        Integer.MAX_VALUE);
    assertEquals(0L, value);
    assertEquals(0, pos.getIndex());
    assertEquals(EMPTY_VALUE, pos.getErrorCode());
    assertEquals(0, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for empty error
    // //////////////////////////////////////////////////////

    str = "zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalLong(pos, str, str.length(), + 1,
        Integer.MAX_VALUE);
    assertEquals(0L, value);
    assertEquals(0, pos.getIndex());
    assertEquals(EMPTY_VALUE, pos.getErrorCode());
    assertEquals(0, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for single digit
    // //////////////////////////////////////////////////////

    str = "0zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalLong(pos, str, str.length(), + 1,
        Integer.MAX_VALUE);
    assertEquals(0L, value);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "0zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalLong(pos, str, str.length(), - 1,
        Integer.MAX_VALUE);
    assertEquals(0L, value);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "9zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalLong(pos, str, str.length(), + 1,
        Integer.MAX_VALUE);
    assertEquals(9L, value);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "9zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalLong(pos, str, str.length(), - 1,
        Integer.MAX_VALUE);
    assertEquals(- 9L, value);
    assertEquals(1, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for normal value
    // //////////////////////////////////////////////////////

    str = "1234567890123456789zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalLong(pos, str, str.length(), + 1,
        Integer.MAX_VALUE);
    assertEquals(1234567890123456789L, value);
    assertEquals(19, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "1234567890123456789zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalLong(pos, str, str.length(), - 1,
        Integer.MAX_VALUE);
    assertEquals(- 1234567890123456789L, value);
    assertEquals(19, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for normal value with leading zeros
    // //////////////////////////////////////////////////////

    str = "00001234567890123456789zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalLong(pos, str, str.length(), + 1,
        Integer.MAX_VALUE);
    assertEquals(1234567890123456789L, value);
    assertEquals(23, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "00001234567890123456789zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalLong(pos, str, str.length(), - 1,
        Integer.MAX_VALUE);
    assertEquals(- 1234567890123456789L, value);
    assertEquals(23, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for all zeros value
    // //////////////////////////////////////////////////////

    str = "00000000000000000000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalLong(pos, str, str.length(), + 1,
        Integer.MAX_VALUE);
    assertEquals(0L, value);
    assertEquals(32, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    str = "00000000000000000000000000000000zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalLong(pos, str, str.length(), - 1,
        Integer.MAX_VALUE);
    assertEquals(0L, value);
    assertEquals(32, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for minimum signed value
    // //////////////////////////////////////////////////////

    str = "9223372036854775808zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalLong(pos, str, str.length(), - 1,
        Integer.MAX_VALUE);
    assertEquals(Long.MIN_VALUE, value);
    assertEquals(19, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for maximum signed value
    // //////////////////////////////////////////////////////

    str = "9223372036854775807zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalLong(pos, str, str.length(), + 1,
        Integer.MAX_VALUE);
    assertEquals(Long.MAX_VALUE, value);
    assertEquals(19, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for minimum signed value + 1
    // //////////////////////////////////////////////////////

    str = "9223372036854775807zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalLong(pos, str, str.length(), - 1,
        Integer.MAX_VALUE);
    assertEquals(Long.MIN_VALUE + 1, value);
    assertEquals(19, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for maximum signed value - 1
    // //////////////////////////////////////////////////////

    str = "9223372036854775806zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalLong(pos, str, str.length(), + 1,
        Integer.MAX_VALUE);
    assertEquals(Long.MAX_VALUE - 1, value);
    assertEquals(19, pos.getIndex());
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(- 1, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for minimum signed value - 1, which overflows
    // //////////////////////////////////////////////////////

    str = "9223372036854775809zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalLong(pos, str, str.length(), - 1,
        Integer.MAX_VALUE);
    assertEquals(Long.MIN_VALUE, value);
    assertEquals(19, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(18, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for maximum signed value + 1, which overflows
    // //////////////////////////////////////////////////////

    str = "9223372036854775808zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalLong(pos, str, str.length(), + 1,
        Integer.MAX_VALUE);
    assertEquals(Long.MAX_VALUE, value);
    assertEquals(19, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(18, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for minimum signed value * 10, which overflows
    // //////////////////////////////////////////////////////

    str = "92233720368547758080zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalLong(pos, str, str.length(), - 1,
        Integer.MAX_VALUE);
    assertEquals(Long.MIN_VALUE, value);
    assertEquals(20, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(19, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for maximum signed value * 10, which overflows
    // //////////////////////////////////////////////////////

    str = "92233720368547758070zzz";
    pos.setIndex(0);
    value = ParseUtils.getDecimalLong(pos, str, str.length(), + 1,
        Integer.MAX_VALUE);
    assertEquals(Long.MAX_VALUE, value);
    assertEquals(20, pos.getIndex());
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(19, pos.getErrorIndex());
  }

/*
  @Test
  public final void testParseInt() {
    final ParsePosition pos = new ParsePosition();
    final ParseOptions options = new ParseOptions();
    final FormatSymbols symbols = new FormatSymbols();
    int value = 0;
    String str = null;

    // //////////////////////////////////////////////////////
    // Test for empty string
    // //////////////////////////////////////////////////////
    str = "";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(0, value);
    assertEquals(EMPTY_VALUE, pos.getErrorCode());
    assertEquals(0, pos.getIndex());

    // //////////////////////////////////////////////////////
    // Test for empty error
    // //////////////////////////////////////////////////////

    str = "zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(0, value);
    assertEquals(EMPTY_VALUE, pos.getErrorCode());
    assertEquals(0, pos.getIndex());

    // //////////////////////////////////////////////////////
    // Test for single digit
    // //////////////////////////////////////////////////////

    str = "0zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(0, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(1, pos.getIndex());

    str = "9zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(9, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(1, pos.getIndex());

    str = "0b0zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(0, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(3, pos.getIndex());

    str = "0b1zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(1, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(3, pos.getIndex());

    str = "00zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(0, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(2, pos.getIndex());

    str = "07zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(7, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(2, pos.getIndex());

    str = "0x0zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(0, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(3, pos.getIndex());

    str = "0xFzzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(15, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(3, pos.getIndex());

    // //////////////////////////////////////////////////////
    // Test for normal value
    // //////////////////////////////////////////////////////

    str = "0B1001001100101100000001011010010zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(1234567890, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(33, pos.getIndex());

    str = "011145401322zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(1234567890, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(12, pos.getIndex());

    str = "1234567890zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(1234567890, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(10, pos.getIndex());

    str = "0x499602D2zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(1234567890, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(10, pos.getIndex());

    str = "0x223e9f78";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), ParseOptions.DEFAULT,
        FormatSymbols.DEFAULT);
    assertEquals(574529400, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(str.length(), pos.getIndex());

    // //////////////////////////////////////////////////////
    // Test for normal value with leading zeros
    // //////////////////////////////////////////////////////

    str = "0B00001001001100101100000001011010010zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(1234567890, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(37, pos.getIndex());

    str = "0000011145401322zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(1234567890, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(16, pos.getIndex());

    str = "0x0000499602D2zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(1234567890, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(14, pos.getIndex());

    // //////////////////////////////////////////////////////
    // Test for all zeros value
    // //////////////////////////////////////////////////////

    str = "0b00000000000000000000000000000000zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(0L, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(34, pos.getIndex());

    str = "00000000000000000000000000000000zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(0L, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(32, pos.getIndex());

    str = "0x00000000000000000000000000000000zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(0L, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(34, pos.getIndex());

    // //////////////////////////////////////////////////////
    // Test for minimum signed value
    // //////////////////////////////////////////////////////

    str = "0b10000000000000000000000000000000zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(Integer.MIN_VALUE, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(34, pos.getIndex());

    str = "020000000000zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(Integer.MIN_VALUE, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(12, pos.getIndex());

    str = "-2147483648zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(Integer.MIN_VALUE, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(11, pos.getIndex());

    str = "0x80000000zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(Integer.MIN_VALUE, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(10, pos.getIndex());

    // //////////////////////////////////////////////////////
    // Test for maximum signed value
    // //////////////////////////////////////////////////////

    str = "0b1111111111111111111111111111111zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(Integer.MAX_VALUE, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(33, pos.getIndex());

    str = "017777777777zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(Integer.MAX_VALUE, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(12, pos.getIndex());

    str = "+2147483647zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(Integer.MAX_VALUE, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(11, pos.getIndex());

    str = "0x7FFFFFFFzzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(Integer.MAX_VALUE, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(10, pos.getIndex());

    // //////////////////////////////////////////////////////
    // Test for maximum unsigned value
    // //////////////////////////////////////////////////////

    str = "0b11111111111111111111111111111111zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(- 1, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(34, pos.getIndex());

    str = "037777777777zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(- 1, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(12, pos.getIndex());

    str = "-1zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(- 1, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(2, pos.getIndex());

    str = "4294967295zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(Integer.MAX_VALUE, value);
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(10, pos.getIndex());

    str = "-4294967295zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(Integer.MIN_VALUE, value);
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(11, pos.getIndex());

    str = "0xFFFFFFFFzzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(- 1, value);
    assertEquals(NONE, pos.getErrorCode());
    assertEquals(10, pos.getIndex());

    // //////////////////////////////////////////////////////
    // Test for maximum unsigned value + 1, which overflows
    // //////////////////////////////////////////////////////

    str = "0b100000000000000000000000000000000zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(- 1, value);
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(35, pos.getIndex());
    assertEquals(34, pos.getErrorIndex());

    str = "040000000000zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(- 1, value);
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(12, pos.getIndex());
    assertEquals(11, pos.getErrorIndex());

    str = "0x100000000zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(- 1, value);
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(11, pos.getIndex());
    assertEquals(10, pos.getErrorIndex());

    // //////////////////////////////////////////////////////
    // Test for maximum unsigned value * radix, which overflows
    // //////////////////////////////////////////////////////

    str = "0b111111111111111111111111111111110zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(- 1, value);
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(35, pos.getIndex());
    assertEquals(34, pos.getErrorIndex());

    str = "0377777777770zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(- 1, value);
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(13, pos.getIndex());
    assertEquals(12, pos.getErrorIndex());

    str = "0xFFFFFFFF0zzz";
    pos.reset(0);
    value = ParseUtils.parseInt(pos, str, str.length(), options, symbols);
    assertEquals(- 1, value);
    assertEquals(NUMBER_OVERFLOW, pos.getErrorCode());
    assertEquals(11, pos.getIndex());
    assertEquals(10, pos.getErrorIndex());
  }
*/
}
