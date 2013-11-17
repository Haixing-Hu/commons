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

import com.github.haixing_hu.text.AsciiStringUtils;

import static org.junit.Assert.assertEquals;

import static com.github.haixing_hu.text.AsciiStringUtils.*;

/**
 * Unit test of the {@link AsciiString} class.
 *
 * @author Haixing Hu
 */
public class AsciiStringsTest {

  private static final byte[] EMPTY             = {};

  private static final byte[] HELLO_WORLD       = {
    'h', 'e', 'l', 'l', 'o', ',', ' ', '\n', 'w', 'o', 'r', 'l', 'd', '!',
  };

  private static final byte[] HELLO_WORLD_COPY       = {
    'h', 'e', 'l', 'l', 'o', ',', ' ', '\n', 'w', 'o', 'r', 'l', 'd', '!',
  };

  private static final byte[] HELLO             = {
    'h', 'e', 'l', 'l', 'o',
  };

  private static final byte[] HELLO_UPPERCASE   = {
    'H', 'E', 'L', 'L', 'O',
  };

  private static final byte[] HELLO_MIX_CASE   = {
    'H', 'e', 'l', 'L', 'o',
  };

  private static final byte[] WORLD             = {
    'w', 'o', 'r', 'l', 'd',
  };

  private static final byte[] WORLD_UPPERCASE   = {
    'W', 'O', 'R', 'L', 'D',
  };

  private static final byte[] WORLD_MIX_CASE   = {
    'w', 'o', 'R', 'l', 'd',
  };

  private static final byte[] ALL_WHITE_SPACE   = {
    ' ', '\t', '\f', '\n', '\r', ' '
  };

  private static final byte[] HELLO_WORLD_WITH_WS       = {
    'h', 'e', '\b', '\f', 'l', 'l', 'o', ',', ' ', '\r', '\t','\n', '\f',
    'w', 'o', 'r', 'l', 'd', '!',
  };

  /**
   * Test method for {@link AsciiStringUtils#toString(byte[], int, int)}.
   */
  @Test
  public void testToStringByteArrayIntInt() {
    assertEquals("", AsciiStringUtils.toString(EMPTY, 0, EMPTY.length));
    assertEquals("", AsciiStringUtils.toString(HELLO_WORLD, 0, 0));
    assertEquals("", AsciiStringUtils.toString(HELLO_WORLD, 1, 1));
    assertEquals("", AsciiStringUtils.toString(HELLO_WORLD, 2, 1));
    assertEquals("hello, \nworld!", AsciiStringUtils.toString(HELLO_WORLD, 0, HELLO_WORLD.length));
    assertEquals("h", AsciiStringUtils.toString(HELLO_WORLD, 0, 1));
    assertEquals("e", AsciiStringUtils.toString(HELLO_WORLD, 1, 2));
    assertEquals("hello", AsciiStringUtils.toString(HELLO_WORLD, 0, 5));
    assertEquals("!", AsciiStringUtils.toString(HELLO_WORLD, HELLO_WORLD.length - 1, HELLO_WORLD.length));
  }

  /**
   * Test method for {@link AsciiStringUtils#equals(byte[], int, int, byte[], int, int)}.
   */
  @Test
  public void testEqualsByteArrayIntIntByteArrayIntInt() {
    assertEquals(true, AsciiStringUtils.equals(EMPTY, 0, EMPTY.length,
        EMPTY, 0, EMPTY.length));
    assertEquals(true, AsciiStringUtils.equals(EMPTY, 0, EMPTY.length,
        HELLO_WORLD, 0, 0));
    assertEquals(false, AsciiStringUtils.equals(EMPTY, 0, EMPTY.length,
        HELLO_WORLD, 0, HELLO_WORLD.length));
    assertEquals(true, AsciiStringUtils.equals(HELLO_WORLD, 0, HELLO_WORLD.length,
        HELLO_WORLD, 0, HELLO_WORLD.length));
    assertEquals(true, AsciiStringUtils.equals(HELLO_WORLD, 0, HELLO_WORLD.length,
        HELLO_WORLD_COPY, 0, HELLO_WORLD_COPY.length));
    assertEquals(false, AsciiStringUtils.equals(HELLO, 0, HELLO.length,
        WORLD, 0, WORLD.length));
    assertEquals(false, AsciiStringUtils.equals(HELLO, 0, HELLO.length,
        HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length));
    assertEquals(false, AsciiStringUtils.equals(HELLO, 0, HELLO.length,
        HELLO_MIX_CASE, 0, HELLO_MIX_CASE.length));
    assertEquals(false, AsciiStringUtils.equals(HELLO_MIX_CASE, 0, HELLO_MIX_CASE.length,
        HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length));
    assertEquals(false, AsciiStringUtils.equals(WORLD, 0, WORLD.length,
        WORLD_UPPERCASE, 0, WORLD_UPPERCASE.length));
    assertEquals(false, AsciiStringUtils.equals(WORLD, 0, WORLD.length,
        WORLD_MIX_CASE, 0, WORLD_MIX_CASE.length));
    assertEquals(false, AsciiStringUtils.equals(WORLD_UPPERCASE, 0, WORLD_UPPERCASE.length,
        WORLD_MIX_CASE, 0, WORLD_MIX_CASE.length));

    assertEquals(true, AsciiStringUtils.equals(HELLO_WORLD, 0, HELLO.length,
        HELLO, 0, HELLO.length));
    assertEquals(false, AsciiStringUtils.equals(HELLO_WORLD, 0, HELLO.length,
        HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length));
    assertEquals(false, AsciiStringUtils.equals(HELLO_WORLD, 0, HELLO.length,
        HELLO_MIX_CASE, 0, HELLO_MIX_CASE.length));
    assertEquals(true, AsciiStringUtils.equals(HELLO_WORLD, HELLO.length + 3,
        HELLO.length + 3 + WORLD.length,
        WORLD, 0, WORLD.length));
    assertEquals(false, AsciiStringUtils.equals(HELLO_WORLD, HELLO.length + 3,
        HELLO.length + 3 + WORLD.length,
        WORLD_UPPERCASE, 0, WORLD_UPPERCASE.length));
    assertEquals(false, AsciiStringUtils.equals(HELLO_WORLD, HELLO.length + 3,
        HELLO.length + 3 + WORLD.length,
        WORLD_MIX_CASE, 0, WORLD_MIX_CASE.length));

  }

  /**
   * Test method for {@link AsciiStringUtils#equalsIgnoreCase(byte[], int, int, byte[], int, int)}.
   */
  @Test
  public void testEqualsIgnoreCase() {
    assertEquals(true, equalsIgnoreCase(EMPTY, 0, EMPTY.length,
        EMPTY, 0, EMPTY.length));
    assertEquals(true, equalsIgnoreCase(EMPTY, 0, EMPTY.length,
        HELLO_WORLD, 0, 0));
    assertEquals(false, equalsIgnoreCase(EMPTY, 0, EMPTY.length,
        HELLO_WORLD, 0, HELLO_WORLD.length));
    assertEquals(true, equalsIgnoreCase(HELLO_WORLD, 0, HELLO_WORLD.length,
        HELLO_WORLD, 0, HELLO_WORLD.length));
    assertEquals(true, equalsIgnoreCase(HELLO_WORLD, 0, HELLO_WORLD.length,
        HELLO_WORLD_COPY, 0, HELLO_WORLD_COPY.length));
    assertEquals(false, equalsIgnoreCase(HELLO, 0, HELLO.length,
        WORLD, 0, WORLD.length));
    assertEquals(true, equalsIgnoreCase(HELLO, 0, HELLO.length,
        HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length));
    assertEquals(true, equalsIgnoreCase(HELLO, 0, HELLO.length,
        HELLO_MIX_CASE, 0, HELLO_MIX_CASE.length));
    assertEquals(true, equalsIgnoreCase(HELLO_MIX_CASE, 0, HELLO_MIX_CASE.length,
        HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length));
    assertEquals(true, equalsIgnoreCase(WORLD, 0, WORLD.length,
        WORLD_UPPERCASE, 0, WORLD_UPPERCASE.length));
    assertEquals(true, equalsIgnoreCase(WORLD, 0, WORLD.length,
        WORLD_MIX_CASE, 0, WORLD_MIX_CASE.length));
    assertEquals(true, equalsIgnoreCase(WORLD_UPPERCASE, 0, WORLD_UPPERCASE.length,
        WORLD_MIX_CASE, 0, WORLD_MIX_CASE.length));

    assertEquals(true, equalsIgnoreCase(HELLO_WORLD, 0, HELLO.length,
        HELLO, 0, HELLO.length));
    assertEquals(true, equalsIgnoreCase(HELLO_WORLD, 0, HELLO.length,
        HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length));
    assertEquals(true, equalsIgnoreCase(HELLO_WORLD, 0, HELLO.length,
        HELLO_MIX_CASE, 0, HELLO_MIX_CASE.length));
    assertEquals(true, equalsIgnoreCase(HELLO_WORLD, HELLO.length + 3,
        HELLO.length + 3 + WORLD.length,
        WORLD, 0, WORLD.length));
    assertEquals(true, equalsIgnoreCase(HELLO_WORLD, HELLO.length + 3,
        HELLO.length + 3 + WORLD.length,
        WORLD_UPPERCASE, 0, WORLD_UPPERCASE.length));
    assertEquals(true, equalsIgnoreCase(HELLO_WORLD, HELLO.length + 3,
        HELLO.length + 3 + WORLD.length,
        WORLD_MIX_CASE, 0, WORLD_MIX_CASE.length));
  }

  /**
   * Test method for {@link AsciiStringUtils#indexOf(byte[], int, int, byte)}.
   */
  @Test
  public void testIndexOfByteArrayIntIntByte() {
    assertEquals(-1, indexOf(EMPTY, 0, 0, (byte)0));
    assertEquals(-1, indexOf(EMPTY, 0, 0, (byte)' '));
    assertEquals(-1, indexOf(HELLO_WORLD, 0, 0, (byte)' '));
    assertEquals(-1, indexOf(HELLO_WORLD, 0, 0, (byte)'o'));
    assertEquals(4, indexOf(HELLO_WORLD, 0, HELLO_WORLD.length, (byte)'o'));
    assertEquals(9, indexOf(HELLO_WORLD, 5, HELLO_WORLD.length, (byte)'o'));
    assertEquals(-1, indexOf(HELLO_WORLD, 10, HELLO_WORLD.length, (byte)'o'));
    assertEquals(-1, indexOf(HELLO_WORLD, 4, 3, (byte)'o'));
    assertEquals(-1, indexOf(HELLO_WORLD, 4, 4, (byte)'o'));
    assertEquals(4, indexOf(HELLO_WORLD, 4, 5, (byte)'o'));
    assertEquals(-1, indexOf(HELLO_WORLD, 0, HELLO_WORLD.length, (byte)'O'));
    assertEquals(4, indexOf(HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length, (byte)'O'));
    assertEquals(-1, indexOf(HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length, (byte)'o'));
  }

  /**
   * Test method for {@link AsciiStringUtils#indexOfIgnoreCase(byte[], int, int, byte)}.
   */
  @Test
  public void testIndexOfIgnoreCaseByteArrayIntIntByte() {
    assertEquals(-1, indexOfIgnoreCase(EMPTY, 0, 0, (byte)0));
    assertEquals(-1, indexOfIgnoreCase(EMPTY, 0, 0, (byte)' '));
    assertEquals(-1, indexOfIgnoreCase(HELLO_WORLD, 0, 0, (byte)' '));
    assertEquals(-1, indexOfIgnoreCase(HELLO_WORLD, 0, 0, (byte)'o'));
    assertEquals(4, indexOfIgnoreCase(HELLO_WORLD, 0, HELLO_WORLD.length, (byte)'o'));
    assertEquals(9, indexOfIgnoreCase(HELLO_WORLD, 5, HELLO_WORLD.length, (byte)'o'));
    assertEquals(-1, indexOfIgnoreCase(HELLO_WORLD, 10, HELLO_WORLD.length, (byte)'o'));
    assertEquals(-1, indexOfIgnoreCase(HELLO_WORLD, 4, 3, (byte)'o'));
    assertEquals(-1, indexOfIgnoreCase(HELLO_WORLD, 4, 4, (byte)'o'));
    assertEquals(4, indexOfIgnoreCase(HELLO_WORLD, 4, 5, (byte)'o'));
    assertEquals(4, indexOfIgnoreCase(HELLO_WORLD, 0, HELLO_WORLD.length, (byte)'O'));
    assertEquals(4, indexOfIgnoreCase(HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length, (byte)'O'));
    assertEquals(4, indexOfIgnoreCase(HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length, (byte)'o'));
  }

  /**
   * Test method for {@link AsciiStringUtils#indexOfAny(byte[], int, int, byte[])}.
   */
  @Test
  public void testIndexOfAny() {
    assertEquals(-1, indexOfAny(EMPTY, 0, 0,
        (byte)0));
    assertEquals(-1, indexOfAny(EMPTY, 0, 0,
        (byte)' '));
    assertEquals(-1, indexOfAny(EMPTY, 0, 0,
        (byte)0, (byte)0));
    assertEquals(-1, indexOfAny(EMPTY, 0, 0,
        (byte)' ', (byte)' '));
    assertEquals(-1, indexOfAny(HELLO_WORLD, 0, 0,
        (byte)' '));
    assertEquals(-1, indexOfAny(HELLO_WORLD, 0, 0,
        (byte)' ', (byte)' '));
    assertEquals(-1, indexOfAny(HELLO_WORLD, 0, 0,
        (byte)'o', (byte)'o'));

    assertEquals(0, indexOfAny(HELLO_WORLD, 0, HELLO_WORLD.length,
        (byte)'o', (byte)'h'));
    assertEquals(1, indexOfAny(HELLO_WORLD, 0, HELLO_WORLD.length,
        (byte)'o', (byte)'e'));
    assertEquals(2, indexOfAny(HELLO_WORLD, 0, HELLO_WORLD.length,
        (byte)'o', (byte)'l'));
    assertEquals(5, indexOfAny(HELLO_WORLD, 5, HELLO_WORLD.length,
        (byte)'d', (byte)','));
    assertEquals(12, indexOfAny(HELLO_WORLD, 6, HELLO_WORLD.length,
        (byte)'d', (byte)','));

    assertEquals(-1, indexOfAny(HELLO_WORLD, 4, 3,
        (byte)'o', (byte)'!'));
    assertEquals(-1, indexOfAny(HELLO_WORLD, 4, 4,
        (byte)'o', (byte)'!'));
    assertEquals(4, indexOfAny(HELLO_WORLD, 4, 5,
        (byte)'o', (byte)'!'));

    assertEquals(-1, indexOfAny(HELLO_WORLD, 0, HELLO_WORLD.length,
        (byte)'O', (byte)'E'));
    assertEquals(1, indexOfAny(HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length,
        (byte)'O', (byte)'E'));

    assertEquals(-1, indexOfAny(HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length,
        (byte)'o', (byte)'l'));
    assertEquals(2, indexOfAny(HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length,
        (byte)'o', (byte)'L'));

  }

  /**
   * Test method for {@link AsciiStringUtils#indexOfAnyIgnoreCase(byte[], int, int, byte[])}.
   */
  @Test
  public void testIndexOfAnyIgnoreCase() {
    assertEquals(-1, indexOfAnyIgnoreCase(EMPTY, 0, 0,
        (byte)0));
    assertEquals(-1, indexOfAnyIgnoreCase(EMPTY, 0, 0,
        (byte)' '));
    assertEquals(-1, indexOfAnyIgnoreCase(EMPTY, 0, 0,
        (byte)0, (byte)0));
    assertEquals(-1, indexOfAnyIgnoreCase(EMPTY, 0, 0,
        (byte)' ', (byte)' '));
    assertEquals(-1, indexOfAnyIgnoreCase(HELLO_WORLD, 0, 0,
        (byte)' '));
    assertEquals(-1, indexOfAnyIgnoreCase(HELLO_WORLD, 0, 0,
        (byte)' ', (byte)' '));
    assertEquals(-1, indexOfAnyIgnoreCase(HELLO_WORLD, 0, 0,
        (byte)'o', (byte)'o'));

    assertEquals(0, indexOfAnyIgnoreCase(HELLO_WORLD, 0, HELLO_WORLD.length,
        (byte)'o', (byte)'h'));
    assertEquals(1, indexOfAnyIgnoreCase(HELLO_WORLD, 0, HELLO_WORLD.length,
        (byte)'o', (byte)'e'));
    assertEquals(2, indexOfAnyIgnoreCase(HELLO_WORLD, 0, HELLO_WORLD.length,
        (byte)'o', (byte)'l'));
    assertEquals(5, indexOfAnyIgnoreCase(HELLO_WORLD, 5, HELLO_WORLD.length,
        (byte)'d', (byte)','));
    assertEquals(12, indexOfAnyIgnoreCase(HELLO_WORLD, 6, HELLO_WORLD.length,
        (byte)'d', (byte)','));

    assertEquals(-1, indexOfAnyIgnoreCase(HELLO_WORLD, 4, 3,
        (byte)'o', (byte)'!'));
    assertEquals(-1, indexOfAnyIgnoreCase(HELLO_WORLD, 4, 4,
        (byte)'o', (byte)'!'));
    assertEquals(4, indexOfAnyIgnoreCase(HELLO_WORLD, 4, 5,
        (byte)'o', (byte)'!'));

    assertEquals(1, indexOfAnyIgnoreCase(HELLO_WORLD, 0, HELLO_WORLD.length,
        (byte)'O', (byte)'E'));
    assertEquals(1, indexOfAnyIgnoreCase(HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length,
        (byte)'O', (byte)'E'));
    assertEquals(2, indexOfAnyIgnoreCase(HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length,
        (byte)'o', (byte)'l'));
    assertEquals(2, indexOfAnyIgnoreCase(HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length,
        (byte)'o', (byte)'L'));

  }

  /**
   * Test method for {@link AsciiStringUtils#indexOfAnyBut(byte[], int, int, byte[])}.
   */
  @Test
  public void testIndexOfAnyBut() {
    assertEquals(-1, indexOfAnyBut(EMPTY, 0, 0,
        (byte)0));
    assertEquals(-1, indexOfAnyBut(EMPTY, 0, 0,
        (byte)' '));
    assertEquals(-1, indexOfAnyBut(EMPTY, 0, 0,
        (byte)0, (byte)0));
    assertEquals(-1, indexOfAnyBut(EMPTY, 0, 0,
        (byte)' ', (byte)' '));
    assertEquals(-1, indexOfAnyBut(HELLO_WORLD, 0, 0,
        (byte)' '));
    assertEquals(-1, indexOfAnyBut(HELLO_WORLD, 0, 0,
        (byte)' ', (byte)' '));
    assertEquals(-1, indexOfAnyBut(HELLO_WORLD, 0, 0,
        (byte)'o', (byte)'o'));

    assertEquals(1, indexOfAnyBut(HELLO_WORLD, 0, HELLO_WORLD.length,
        (byte)'o', (byte)'h'));
    assertEquals(2, indexOfAnyBut(HELLO_WORLD, 0, HELLO_WORLD.length,
        (byte)'o', (byte)'h', (byte)'e'));
    assertEquals(5, indexOfAnyBut(HELLO_WORLD, 0, HELLO_WORLD.length,
        (byte)'o', (byte)'h', (byte)'e', (byte)'l'));
    assertEquals(5, indexOfAnyBut(HELLO_WORLD, 5, HELLO_WORLD.length,
        (byte)'o', (byte)'h', (byte)'e', (byte)'l'));
    assertEquals(6, indexOfAnyBut(HELLO_WORLD, 6, HELLO_WORLD.length,
        (byte)'o', (byte)'h', (byte)'e', (byte)'l', (byte)'\n'));

    assertEquals(-1, indexOfAnyBut(HELLO_WORLD, 4, 3,
        (byte)'o', (byte)'h', (byte)'e', (byte)'l', (byte)'\n'));
    assertEquals(-1, indexOfAnyBut(HELLO_WORLD, 4, 4,
        (byte)'o', (byte)'h', (byte)'e', (byte)'l', (byte)'\n'));
    assertEquals(-1, indexOfAnyBut(HELLO_WORLD, 4, 5,
        (byte)'o', (byte)'h', (byte)'e', (byte)'l', (byte)'\n'));

    assertEquals(2, indexOfAnyBut(HELLO_WORLD, 0, HELLO_WORLD.length,
        (byte)'o', (byte)'h', (byte)'e', (byte)'L', (byte)'\n'));
    assertEquals(2, indexOfAnyBut(HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length,
        (byte)'o', (byte)'H', (byte)'E', (byte)'l', (byte)'\n'));
    assertEquals(-1, indexOfAnyBut(HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length,
        (byte)'H', (byte)'E', (byte)'L', (byte)'O'));
    assertEquals(0, indexOfAnyBut(HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length,
        (byte)'h', (byte)'e', (byte)'l', (byte)'o'));

  }

  /**
   * Test method for {@link AsciiStringUtils#indexOfAnyButIgnoreCase(byte[], int, int, byte[])}.
   */
  @Test
  public void testIndexOfAnyButIgnoreCase() {
    assertEquals(-1, indexOfAnyButIgnoreCase(EMPTY, 0, 0,
        (byte)0));
    assertEquals(-1, indexOfAnyButIgnoreCase(EMPTY, 0, 0,
        (byte)' '));
    assertEquals(-1, indexOfAnyButIgnoreCase(EMPTY, 0, 0,
        (byte)0, (byte)0));
    assertEquals(-1, indexOfAnyButIgnoreCase(EMPTY, 0, 0,
        (byte)' ', (byte)' '));
    assertEquals(-1, indexOfAnyButIgnoreCase(HELLO_WORLD, 0, 0,
        (byte)' '));
    assertEquals(-1, indexOfAnyButIgnoreCase(HELLO_WORLD, 0, 0,
        (byte)' ', (byte)' '));
    assertEquals(-1, indexOfAnyButIgnoreCase(HELLO_WORLD, 0, 0,
        (byte)'o', (byte)'o'));

    assertEquals(1, indexOfAnyButIgnoreCase(HELLO_WORLD, 0, HELLO_WORLD.length,
        (byte)'o', (byte)'h'));
    assertEquals(2, indexOfAnyButIgnoreCase(HELLO_WORLD, 0, HELLO_WORLD.length,
        (byte)'o', (byte)'h', (byte)'e'));
    assertEquals(5, indexOfAnyButIgnoreCase(HELLO_WORLD, 0, HELLO_WORLD.length,
        (byte)'o', (byte)'h', (byte)'e', (byte)'l'));
    assertEquals(5, indexOfAnyButIgnoreCase(HELLO_WORLD, 5, HELLO_WORLD.length,
        (byte)'o', (byte)'h', (byte)'e', (byte)'l'));
    assertEquals(6, indexOfAnyButIgnoreCase(HELLO_WORLD, 6, HELLO_WORLD.length,
        (byte)'o', (byte)'h', (byte)'e', (byte)'l', (byte)'\n'));

    assertEquals(-1, indexOfAnyButIgnoreCase(HELLO_WORLD, 4, 3,
        (byte)'o', (byte)'h', (byte)'e', (byte)'l', (byte)'\n'));
    assertEquals(-1, indexOfAnyButIgnoreCase(HELLO_WORLD, 4, 4,
        (byte)'o', (byte)'h', (byte)'e', (byte)'l', (byte)'\n'));
    assertEquals(-1, indexOfAnyButIgnoreCase(HELLO_WORLD, 4, 5,
        (byte)'o', (byte)'h', (byte)'e', (byte)'l', (byte)'\n'));

    assertEquals(5, indexOfAnyButIgnoreCase(HELLO_WORLD, 0, HELLO_WORLD.length,
        (byte)'o', (byte)'h', (byte)'e', (byte)'L', (byte)'\n'));
    assertEquals(-1, indexOfAnyButIgnoreCase(HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length,
        (byte)'o', (byte)'H', (byte)'E', (byte)'l', (byte)'\n'));
    assertEquals(-1, indexOfAnyButIgnoreCase(HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length,
        (byte)'H', (byte)'E', (byte)'L', (byte)'O'));
    assertEquals(-1, indexOfAnyButIgnoreCase(HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length,
        (byte)'h', (byte)'e', (byte)'l', (byte)'o'));
  }

  /**
   * Test method for {@link AsciiStringUtils#indexOfWhitespace(byte[], int, int)}.
   */
  @Test
  public void testIndexOfWhitespace() {
    assertEquals(-1, indexOfWhitespace(EMPTY, 0, EMPTY.length));

    assertEquals(6, indexOfWhitespace(HELLO_WORLD, 0, HELLO_WORLD.length));
    assertEquals(6, indexOfWhitespace(HELLO_WORLD, 1, HELLO_WORLD.length));
    assertEquals(6, indexOfWhitespace(HELLO_WORLD, 2, HELLO_WORLD.length));
    assertEquals(6, indexOfWhitespace(HELLO_WORLD, 3, HELLO_WORLD.length));
    assertEquals(6, indexOfWhitespace(HELLO_WORLD, 6, HELLO_WORLD.length));
    assertEquals(7, indexOfWhitespace(HELLO_WORLD, 7, HELLO_WORLD.length));

    assertEquals(-1, indexOfWhitespace(HELLO, 0, HELLO.length));
    assertEquals(-1, indexOfWhitespace(WORLD, 0, WORLD.length));
    assertEquals(0, indexOfWhitespace(ALL_WHITE_SPACE, 0, ALL_WHITE_SPACE.length));
    assertEquals(1, indexOfWhitespace(ALL_WHITE_SPACE, 1, ALL_WHITE_SPACE.length));
    assertEquals(2, indexOfWhitespace(ALL_WHITE_SPACE, 2, ALL_WHITE_SPACE.length));
    assertEquals(3, indexOfWhitespace(ALL_WHITE_SPACE, 3, ALL_WHITE_SPACE.length));
    assertEquals(4, indexOfWhitespace(ALL_WHITE_SPACE, 4, ALL_WHITE_SPACE.length));
    assertEquals(5, indexOfWhitespace(ALL_WHITE_SPACE, 5, ALL_WHITE_SPACE.length));
  }

  /**
   * Test method for {@link AsciiStringUtils#indexOfNonWhitespace(byte[], int, int)}.
   */
  @Test
  public void testIndexOfNonWhitespace() {
    assertEquals(-1, indexOfNonWhitespace(EMPTY, 0, EMPTY.length));

    assertEquals(0, indexOfNonWhitespace(HELLO_WORLD, 0, HELLO_WORLD.length));
    assertEquals(1, indexOfNonWhitespace(HELLO_WORLD, 1, HELLO_WORLD.length));
    assertEquals(2, indexOfNonWhitespace(HELLO_WORLD, 2, HELLO_WORLD.length));
    assertEquals(3, indexOfNonWhitespace(HELLO_WORLD, 3, HELLO_WORLD.length));
    assertEquals(8, indexOfNonWhitespace(HELLO_WORLD, 6, HELLO_WORLD.length));
    assertEquals(8, indexOfNonWhitespace(HELLO_WORLD, 7, HELLO_WORLD.length));

    assertEquals(0, indexOfNonWhitespace(HELLO, 0, HELLO.length));
    assertEquals(0, indexOfNonWhitespace(WORLD, 0, WORLD.length));
    assertEquals(-1, indexOfNonWhitespace(ALL_WHITE_SPACE, 0, ALL_WHITE_SPACE.length));
    assertEquals(-1, indexOfNonWhitespace(ALL_WHITE_SPACE, 1, ALL_WHITE_SPACE.length));
    assertEquals(-1, indexOfNonWhitespace(ALL_WHITE_SPACE, 2, ALL_WHITE_SPACE.length));
    assertEquals(-1, indexOfNonWhitespace(ALL_WHITE_SPACE, 3, ALL_WHITE_SPACE.length));
    assertEquals(-1, indexOfNonWhitespace(ALL_WHITE_SPACE, 4, ALL_WHITE_SPACE.length));
    assertEquals(-1, indexOfNonWhitespace(ALL_WHITE_SPACE, 5, ALL_WHITE_SPACE.length));

    assertEquals(0, indexOfNonWhitespace(HELLO_WORLD_WITH_WS, 0, HELLO_WORLD_WITH_WS.length));
    assertEquals(2, indexOfNonWhitespace(HELLO_WORLD_WITH_WS, 2, HELLO_WORLD_WITH_WS.length));
    assertEquals(13, indexOfNonWhitespace(HELLO_WORLD_WITH_WS, 8, HELLO_WORLD_WITH_WS.length));
    assertEquals(13, indexOfNonWhitespace(HELLO_WORLD_WITH_WS, 12, HELLO_WORLD_WITH_WS.length));
    assertEquals(13, indexOfNonWhitespace(HELLO_WORLD_WITH_WS, 13, HELLO_WORLD_WITH_WS.length));

  }

  /**
   * Test method for {@link AsciiStringUtils#indexOfLetter(byte[], int, int)}.
   */
  @Test
  public void testIndexOfLetter() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#indexOfNonLetter(byte[], int, int)}.
   */
  @Test
  public void testIndexOfNonLetter() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#indexOfUppercaseLetter(byte[], int, int)}.
   */
  @Test
  public void testIndexOfUppercaseLetter() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#indexOfNonUppercaseLetter(byte[], int, int)}.
   */
  @Test
  public void testIndexOfNonUppercaseLetter() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#indexOfLowercaseLetter(byte[], int, int)}.
   */
  @Test
  public void testIndexOfLowercaseLetter() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#indexOfNonLowercaseLetter(byte[], int, int)}.
   */
  @Test
  public void testIndexOfNonLowercaseLetter() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#indexOfDigit(byte[], int, int)}.
   */
  @Test
  public void testIndexOfDigit() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#indexOfNonDigit(byte[], int, int)}.
   */
  @Test
  public void testIndexOfNonDigit() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#indexOf(byte[], int, int, byte[], int, int)}.
   */
  @Test
  public void testIndexOfByteArrayIntIntByteArrayIntInt() {
    assertEquals(-1, indexOf(EMPTY, 0, EMPTY.length,
        EMPTY, 0, EMPTY.length));

    assertEquals(-1, indexOf(EMPTY, 0, EMPTY.length, HELLO_WORLD, 0,
        HELLO_WORLD.length));

    assertEquals(0, indexOf(HELLO_WORLD, 0, HELLO_WORLD.length,
        EMPTY, 0, EMPTY.length));

    assertEquals(0, indexOf(HELLO_WORLD, 0, HELLO_WORLD.length,
        HELLO_WORLD_COPY, 0, HELLO_WORLD_COPY.length));

    assertEquals(0, indexOf(HELLO_WORLD, 0, HELLO_WORLD.length,
        HELLO, 0, HELLO.length));

    assertEquals(-1, indexOf(HELLO_WORLD, 0, HELLO_WORLD.length,
        HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length));

    assertEquals(-1, indexOf(HELLO_WORLD, 0, HELLO_WORLD.length,
        HELLO_MIX_CASE, 0, HELLO_MIX_CASE.length));

    assertEquals(8, indexOf(HELLO_WORLD, 0, HELLO_WORLD.length,
        WORLD, 0, WORLD.length));

    assertEquals(-1, indexOf(HELLO_WORLD, 0, HELLO_WORLD.length,
        WORLD_UPPERCASE, 0, WORLD_UPPERCASE.length));

    assertEquals(-1, indexOf(HELLO_WORLD, 0, HELLO_WORLD.length,
        WORLD_MIX_CASE, 0, WORLD_MIX_CASE.length));

  }

  /**
   * Test method for {@link AsciiStringUtils#indexOfIgnoreCase(byte[], int, int, byte[], int, int)}.
   */
  @Test
  public void testIndexOfIgnoreCaseByteArrayIntIntByteArrayIntInt() {
    assertEquals(-1, indexOfIgnoreCase(EMPTY, 0, EMPTY.length,
        EMPTY, 0, EMPTY.length));

    assertEquals(-1, indexOfIgnoreCase(EMPTY, 0, EMPTY.length, HELLO_WORLD, 0,
        HELLO_WORLD.length));

    assertEquals(0, indexOfIgnoreCase(HELLO_WORLD, 0, HELLO_WORLD.length,
        EMPTY, 0, EMPTY.length));

    assertEquals(0, indexOfIgnoreCase(HELLO_WORLD, 0, HELLO_WORLD.length,
        HELLO_WORLD_COPY, 0, HELLO_WORLD_COPY.length));

    assertEquals(0, indexOfIgnoreCase(HELLO_WORLD, 0, HELLO_WORLD.length,
        HELLO, 0, HELLO.length));

    assertEquals(0, indexOfIgnoreCase(HELLO_WORLD, 0, HELLO_WORLD.length,
        HELLO_UPPERCASE, 0, HELLO_UPPERCASE.length));

    assertEquals(0, indexOfIgnoreCase(HELLO_WORLD, 0, HELLO_WORLD.length,
        HELLO_MIX_CASE, 0, HELLO_MIX_CASE.length));

    assertEquals(8, indexOfIgnoreCase(HELLO_WORLD, 0, HELLO_WORLD.length,
        WORLD, 0, WORLD.length));

    assertEquals(8, indexOfIgnoreCase(HELLO_WORLD, 0, HELLO_WORLD.length,
        WORLD_UPPERCASE, 0, WORLD_UPPERCASE.length));

    assertEquals(8, indexOfIgnoreCase(HELLO_WORLD, 0, HELLO_WORLD.length,
        WORLD_MIX_CASE, 0, WORLD_MIX_CASE.length));
  }

  /**
   * Test method for {@link AsciiStringUtils#lastIndexOf(byte[], int, int, byte)}.
   */
  @Test
  public void testLastIndexOfByteArrayIntIntByte() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#lastIndexOfIgnoreCase(byte[], int, int, byte)}.
   */
  @Test
  public void testLastIndexOfIgnoreCaseByteArrayIntIntByte() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#lastIndexOf(byte[], int, int, byte[], int, int)}.
   */
  @Test
  public void testLastIndexOfByteArrayIntIntByteArrayIntInt() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#lastIndexOfIgnoreCase(byte[], int, int, byte[], int, int)}.
   */
  @Test
  public void testLastIndexOfIgnoreCaseByteArrayIntIntByteArrayIntInt() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#startsWith(byte[], int, int, byte)}.
   */
  @Test
  public void testStartsWithByteArrayIntIntByte() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#startsWithIgnoreCase(byte[], int, int, byte)}.
   */
  @Test
  public void testStartsWithIgnoreCaseByteArrayIntIntByte() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#endsWith(byte[], int, int, byte)}.
   */
  @Test
  public void testEndsWithByteArrayIntIntByte() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#endsWithIgnoreCase(byte[], int, int, byte)}.
   */
  @Test
  public void testEndsWithIgnoreCaseByteArrayIntIntByte() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#startsWithAny(byte[], int, int, byte[])}.
   */
  @Test
  public void testStartsWithAny() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#startsWithAnyIgnoreCase(byte[], int, int, byte[])}.
   */
  @Test
  public void testStartsWithAnyIgnoreCase() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#endsWithAny(byte[], int, int, byte[])}.
   */
  @Test
  public void testEndsWithAny() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#endsWithAnyIgnoreCase(byte[], int, int, byte[])}.
   */
  @Test
  public void testEndsWithAnyIgnoreCase() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#startsWith(byte[], int, int, byte[], int, int)}.
   */
  @Test
  public void testStartsWithByteArrayIntIntByteArrayIntInt() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#startsWithIgnoreCase(byte[], int, int, byte[], int, int)}.
   */
  @Test
  public void testStartsWithIgnoreCaseByteArrayIntIntByteArrayIntInt() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#endsWith(byte[], int, int, byte[], int, int)}.
   */
  @Test
  public void testEndsWithByteArrayIntIntByteArrayIntInt() {
    // TODO
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link AsciiStringUtils#endsWithIgnoreCase(byte[], int, int, byte[], int, int)}.
   */
  @Test
  public void testEndsWithIgnoreCaseByteArrayIntIntByteArrayIntInt() {
    // TODO
    // fail("Not yet implemented");
  }

}
