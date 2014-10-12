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

import com.github.haixing_hu.lang.IntUtils;
import com.github.haixing_hu.text.FormatUtils;

import static org.junit.Assert.assertEquals;

import static com.github.haixing_hu.text.FormatUtils.*;
import static com.github.haixing_hu.text.NumberFormatSymbols.*;

/**
 * Unit test of the {@link FormatUtils} class.
 *
 * @author Haixing Hu
 */
public class FormatUtilsTest {

  @Test
  public final void testPutSpecialRadixIntBackward() {
    String str = null;
    final char[] buffer = new char[512];
    int startIndex = 0;

    // //////////////////////////////////////////////////////
    // test for zeros
    // //////////////////////////////////////////////////////

    startIndex = putSpecialRadixIntBackward(0, 2, 0, DEFAULT_LOWERCASE_DIGITS,
        buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("", str);

    startIndex = putSpecialRadixIntBackward(0, 2, 1, DEFAULT_LOWERCASE_DIGITS,
        buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("0", str);

    startIndex = putSpecialRadixIntBackward(0, 2, 2, DEFAULT_LOWERCASE_DIGITS,
        buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("00", str);

    // //////////////////////////////////////////////////////
    // test for single digit
    // //////////////////////////////////////////////////////

    startIndex = putSpecialRadixIntBackward(1, 2, 0, DEFAULT_LOWERCASE_DIGITS,
        buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("1", str);

    startIndex = putSpecialRadixIntBackward(1, 2, 8, DEFAULT_LOWERCASE_DIGITS,
        buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("00000001", str);

    startIndex = putSpecialRadixIntBackward(7, 8, 3, DEFAULT_LOWERCASE_DIGITS,
        buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("007", str);

    startIndex = putSpecialRadixIntBackward(15, 16, 2,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("0f", str);

    startIndex = putSpecialRadixIntBackward(15, 16, 4,
        DEFAULT_UPPERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("000F", str);

    // //////////////////////////////////////////////////////
    // test for normal value
    // //////////////////////////////////////////////////////

    startIndex = putSpecialRadixIntBackward(1234567890, 2, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("1001001100101100000001011010010", str);

    startIndex = putSpecialRadixIntBackward(1234567890, 8, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("11145401322", str);

    startIndex = putSpecialRadixIntBackward(1234567890, 16, 0,
        DEFAULT_UPPERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("499602D2", str);

    // //////////////////////////////////////////////////////
    // test for maximum unsigned value
    // //////////////////////////////////////////////////////

    startIndex = putSpecialRadixIntBackward(IntUtils.UNSIGNED_MAX, 2, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("11111111111111111111111111111111", str);

    startIndex = putSpecialRadixIntBackward(IntUtils.UNSIGNED_MAX, 8, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("37777777777", str);

    startIndex = putSpecialRadixIntBackward(IntUtils.UNSIGNED_MAX, 16, 0,
        DEFAULT_UPPERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("FFFFFFFF", str);

    // //////////////////////////////////////////////////////
    // test for maximum unsigned value - 1
    // //////////////////////////////////////////////////////

    startIndex = putSpecialRadixIntBackward(IntUtils.UNSIGNED_MAX - 1, 2, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("11111111111111111111111111111110", str);

    startIndex = putSpecialRadixIntBackward(IntUtils.UNSIGNED_MAX - 1, 8, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("37777777776", str);

    startIndex = putSpecialRadixIntBackward(IntUtils.UNSIGNED_MAX - 1, 16, 0,
        DEFAULT_UPPERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("FFFFFFFE", str);

    // //////////////////////////////////////////////////////
    // test for maximum unsigned value / 10
    // //////////////////////////////////////////////////////

    startIndex = putSpecialRadixIntBackward(0x19999999, 2, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("11001100110011001100110011001", str);

    startIndex = putSpecialRadixIntBackward(0x19999999, 8, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("3146314631", str);

    startIndex = putSpecialRadixIntBackward(0x19999999, 16, 0,
        DEFAULT_UPPERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("19999999", str);

  }

  @Test
  public final void testPutSpecialRadixLongBackward() {
    String str = null;
    final char[] buffer = new char[512];
    int startIndex = 0;

    // //////////////////////////////////////////////////////
    // test for zeros
    // //////////////////////////////////////////////////////

    startIndex = putSpecialRadixLongBackward(0L, 2, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("", str);

    startIndex = putSpecialRadixLongBackward(0L, 2, 1,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("0", str);

    startIndex = putSpecialRadixLongBackward(0L, 2, 2,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("00", str);

    // //////////////////////////////////////////////////////
    // test for single digit
    // //////////////////////////////////////////////////////

    startIndex = putSpecialRadixLongBackward(1L, 2, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("1", str);

    startIndex = putSpecialRadixLongBackward(1L, 2, 8,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("00000001", str);

    startIndex = putSpecialRadixLongBackward(7L, 8, 3,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("007", str);

    startIndex = putSpecialRadixLongBackward(15L, 16, 2,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("0f", str);

    startIndex = putSpecialRadixLongBackward(15L, 16, 4,
        DEFAULT_UPPERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("000F", str);

    // //////////////////////////////////////////////////////
    // test for normal value
    // //////////////////////////////////////////////////////

    startIndex = putSpecialRadixLongBackward(1234567890123456789L, 2, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals(
        "1000100100010000100001111010001111101111010011000000100010101", str);

    startIndex = putSpecialRadixLongBackward(1234567890123456789L, 8, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("104420417217572300425", str);

    startIndex = putSpecialRadixLongBackward(1234567890123456789L, 16, 0,
        DEFAULT_UPPERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("112210F47DE98115", str);

    // //////////////////////////////////////////////////////
    // test for maximum unsigned value
    // //////////////////////////////////////////////////////

    startIndex = putSpecialRadixLongBackward(0xFFFFFFFFFFFFFFFFL, 2, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals(
        "1111111111111111111111111111111111111111111111111111111111111111", str);

    startIndex = putSpecialRadixLongBackward(0xFFFFFFFFFFFFFFFFL, 8, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("1777777777777777777777", str);

    startIndex = putSpecialRadixLongBackward(0xFFFFFFFFFFFFFFFFL, 16, 0,
        DEFAULT_UPPERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("FFFFFFFFFFFFFFFF", str);

    // //////////////////////////////////////////////////////
    // test for maximum unsigned value - 1
    // //////////////////////////////////////////////////////

    startIndex = putSpecialRadixLongBackward(0xFFFFFFFFFFFFFFFFL - 1L, 2, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals(
        "1111111111111111111111111111111111111111111111111111111111111110", str);

    startIndex = putSpecialRadixLongBackward(0xFFFFFFFFFFFFFFFFL - 1L, 8, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("1777777777777777777776", str);

    startIndex = putSpecialRadixLongBackward(0xFFFFFFFFFFFFFFFFL - 1L, 16, 0,
        DEFAULT_UPPERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("FFFFFFFFFFFFFFFE", str);

    // //////////////////////////////////////////////////////
    // test for maximum unsigned value / radix
    // //////////////////////////////////////////////////////

    startIndex = putSpecialRadixLongBackward(0x7FFFFFFFFFFFFFFFL, 2, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals(
        "111111111111111111111111111111111111111111111111111111111111111", str);

    startIndex = putSpecialRadixLongBackward(0x1FFFFFFFFFFFFFFFL, 8, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("177777777777777777777", str);
    assertEquals("177777777777777777777", str);

    startIndex = putSpecialRadixLongBackward(0x1111111111111111L, 16, 0,
        DEFAULT_UPPERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("1111111111111111", str);
  }

  @Test
  public final void testPutDecimalIntAbsBackward() {
    String str = null;
    final char[] buffer = new char[512];
    int startIndex = 0;

    // //////////////////////////////////////////////////////
    // test for zeros
    // //////////////////////////////////////////////////////

    startIndex = putDecimalIntAbsBackward(0, 0, DEFAULT_LOWERCASE_DIGITS,
        buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("", str);

    startIndex = putDecimalIntAbsBackward(0, 1, DEFAULT_LOWERCASE_DIGITS,
        buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("0", str);

    startIndex = putDecimalIntAbsBackward(0, 2, DEFAULT_LOWERCASE_DIGITS,
        buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("00", str);

    // //////////////////////////////////////////////////////
    // test for single digit
    // //////////////////////////////////////////////////////

    startIndex = putDecimalIntAbsBackward(9, 0, DEFAULT_LOWERCASE_DIGITS,
        buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("9", str);

    // //////////////////////////////////////////////////////
    // test for normal value
    // //////////////////////////////////////////////////////

    startIndex = putDecimalIntAbsBackward(1234567890, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("1234567890", str);

    startIndex = putDecimalIntAbsBackward(1234567890, 25,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("0000000000000001234567890", str);

    // //////////////////////////////////////////////////////
    // test for maximum signed value
    // //////////////////////////////////////////////////////

    startIndex = putDecimalIntAbsBackward(Integer.MAX_VALUE, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("2147483647", str);

    // //////////////////////////////////////////////////////
    // test for maximum signed value - 1
    // //////////////////////////////////////////////////////

    startIndex = putDecimalIntAbsBackward(Integer.MAX_VALUE - 1, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("2147483646", str);

    // //////////////////////////////////////////////////////
    // test for maximum signed value / radix
    // //////////////////////////////////////////////////////

    startIndex = putDecimalIntAbsBackward(Integer.MAX_VALUE / 10, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("214748364", str);

    // //////////////////////////////////////////////////////
    // test for minimum signed value
    // //////////////////////////////////////////////////////

    startIndex = putDecimalIntAbsBackward(Integer.MIN_VALUE, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("2147483648", str);

    // //////////////////////////////////////////////////////
    // test for minimum signed value + 1
    // //////////////////////////////////////////////////////

    startIndex = putDecimalIntAbsBackward(Integer.MIN_VALUE + 1, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("2147483647", str);

    // //////////////////////////////////////////////////////
    // test for minimum signed value / radix
    // //////////////////////////////////////////////////////

    startIndex = putDecimalIntAbsBackward(Integer.MIN_VALUE / 10, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("214748364", str);

  }

  @Test
  public final void testPutDecimalLongAbsBackward() {
    String str = null;
    final char[] buffer = new char[512];
    int startIndex = 0;

    // //////////////////////////////////////////////////////
    // test for zeros
    // //////////////////////////////////////////////////////

    startIndex = putDecimalLongAbsBackward(0L, 0, DEFAULT_LOWERCASE_DIGITS,
        buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("", str);

    startIndex = putDecimalLongAbsBackward(0L, 1, DEFAULT_LOWERCASE_DIGITS,
        buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("0", str);

    startIndex = putDecimalLongAbsBackward(0L, 2, DEFAULT_LOWERCASE_DIGITS,
        buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("00", str);

    // //////////////////////////////////////////////////////
    // test for single digit
    // //////////////////////////////////////////////////////

    startIndex = putDecimalLongAbsBackward(9L, 0, DEFAULT_LOWERCASE_DIGITS,
        buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("9", str);

    // //////////////////////////////////////////////////////
    // test for normal value
    // //////////////////////////////////////////////////////

    startIndex = putDecimalLongAbsBackward(1234567890123456789L, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("1234567890123456789", str);

    startIndex = putDecimalLongAbsBackward(1234567890123456789L, 25,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("0000001234567890123456789", str);

    // //////////////////////////////////////////////////////
    // test for maximum signed value
    // //////////////////////////////////////////////////////

    startIndex = putDecimalLongAbsBackward(Long.MAX_VALUE, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("9223372036854775807", str);

    // //////////////////////////////////////////////////////
    // test for maximum signed value - 1
    // //////////////////////////////////////////////////////

    startIndex = putDecimalLongAbsBackward(Long.MAX_VALUE - 1, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("9223372036854775806", str);

    // //////////////////////////////////////////////////////
    // test for maximum signed value / radix
    // //////////////////////////////////////////////////////

    startIndex = putDecimalLongAbsBackward(Long.MAX_VALUE / 10, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("922337203685477580", str);

    // //////////////////////////////////////////////////////
    // test for minimum signed value
    // //////////////////////////////////////////////////////

    startIndex = putDecimalLongAbsBackward(Long.MIN_VALUE, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("9223372036854775808", str);

    // //////////////////////////////////////////////////////
    // test for minimum signed value + 1
    // //////////////////////////////////////////////////////

    startIndex = putDecimalLongAbsBackward(Long.MIN_VALUE + 1, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("9223372036854775807", str);

    // //////////////////////////////////////////////////////
    // test for minimum signed value / radix
    // //////////////////////////////////////////////////////

    startIndex = putDecimalLongAbsBackward(Long.MIN_VALUE / 10, 0,
        DEFAULT_LOWERCASE_DIGITS, buffer, buffer.length);
    str = new String(buffer, startIndex, buffer.length - startIndex);
    assertEquals("922337203685477580", str);

  }
}
