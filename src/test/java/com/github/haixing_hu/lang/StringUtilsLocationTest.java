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

import com.github.haixing_hu.util.filter.character.CharFilter;
import com.github.haixing_hu.util.filter.character.InStringCharFilter;
import com.github.haixing_hu.util.filter.character.NotInStringCharFilter;

import static com.github.haixing_hu.lang.StringUtils.*;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for the indexOfXXXX() functions of the Strings class.
 *
 * @author Haixing Hu
 */
public class StringUtilsLocationTest {

  @Test
  public void testIndexOfChar_String_int_int() {
    assertEquals(-1, indexOfChar(null, ' ', 0));
    assertEquals(-1, indexOfChar(null, ' ', -1));
    assertEquals(-1, indexOfChar("", ' ', 0));
    assertEquals(-1, indexOfChar("", ' ', -1));
    assertEquals(0, indexOfChar("aabaabaa", 'a', 0));
    assertEquals(2, indexOfChar("aabaabaa", 'b', 0));
    assertEquals(5, indexOfChar("aabaabaa", 'b', 3));
    assertEquals(-1, indexOfChar("aabaabaa", 'b', 9));
    assertEquals(2, indexOfChar("aabaabaa", 'b', -1));
  }

  @Test
  public void testIndexOfChar_String_CharFilter_int() {
    assertEquals(-1, indexOfChar(null, (CharFilter) null, 0));
    assertEquals(-1, indexOfChar(null, (CharFilter) null, -1));
    assertEquals(-1, indexOfChar(null, (CharFilter) null, 100));

    assertEquals(-1, indexOfChar(null, new InStringCharFilter(""), 0));
    assertEquals(-1, indexOfChar(null, new InStringCharFilter(""), -1));
    assertEquals(-1, indexOfChar(null, new InStringCharFilter(""), 100));
    assertEquals(-1, indexOfChar(null, new InStringCharFilter("ab"), 0));
    assertEquals(-1, indexOfChar(null, new InStringCharFilter("ab"), -1));
    assertEquals(-1, indexOfChar(null, new InStringCharFilter("ab"), 100));

    assertEquals(-1, indexOfChar(null, new NotInStringCharFilter(""), 0));
    assertEquals(-1, indexOfChar(null, new NotInStringCharFilter(""), -1));
    assertEquals(-1, indexOfChar(null, new NotInStringCharFilter(""), 100));
    assertEquals(-1, indexOfChar(null, new NotInStringCharFilter("ab"), 0));
    assertEquals(-1, indexOfChar(null, new NotInStringCharFilter("ab"), -1));
    assertEquals(-1, indexOfChar(null, new NotInStringCharFilter("ab"), 100));

    assertEquals(-1, indexOfChar("", (CharFilter) null, 0));
    assertEquals(-1, indexOfChar("", (CharFilter) null, -1));
    assertEquals(-1, indexOfChar("", (CharFilter) null, 100));

    assertEquals(-1, indexOfChar("", new InStringCharFilter(""), 0));
    assertEquals(-1, indexOfChar("", new InStringCharFilter(""), -1));
    assertEquals(-1, indexOfChar("", new InStringCharFilter(""), 100));
    assertEquals(-1, indexOfChar("", new InStringCharFilter("ab"), 0));
    assertEquals(-1, indexOfChar("", new InStringCharFilter("ab"), -1));
    assertEquals(-1, indexOfChar("", new InStringCharFilter("ab"), 100));

    assertEquals(-1, indexOfChar("", new NotInStringCharFilter(""), 0));
    assertEquals(-1, indexOfChar("", new NotInStringCharFilter(""), -1));
    assertEquals(-1, indexOfChar("", new NotInStringCharFilter(""), 100));
    assertEquals(-1, indexOfChar("", new NotInStringCharFilter("ab"), 0));
    assertEquals(-1, indexOfChar("", new NotInStringCharFilter("ab"), -1));
    assertEquals(-1, indexOfChar("", new NotInStringCharFilter("ab"), 100));

    assertEquals(-1, indexOfChar("zzabyycdxx", (CharFilter) null, 0));
    assertEquals(-1, indexOfChar("zzabyycdxx", (CharFilter) null, -1));
    assertEquals(-1, indexOfChar("zzabyycdxx", (CharFilter) null, 100));

    assertEquals(-1, indexOfChar("zzabyycdxx", new InStringCharFilter(""), 0));
    assertEquals(-1, indexOfChar("zzabyycdxx", new InStringCharFilter(""), -1));
    assertEquals(-1, indexOfChar("zzabyycdxx", new InStringCharFilter(""), 100));

    assertEquals(0, indexOfChar("zzabyycdxx", new NotInStringCharFilter(""), 0));
    assertEquals(0, indexOfChar("zzabyycdxx", new NotInStringCharFilter(""), -1));
    assertEquals(-1, indexOfChar("zzabyycdxx", new NotInStringCharFilter(""), 100));

    assertEquals(0, indexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 0));
    assertEquals(0, indexOfChar("zzabyycdxx", new InStringCharFilter("zax"), -1));
    assertEquals(-1, indexOfChar("zzabyycdxx",new InStringCharFilter("zax"), 100));
    assertEquals(1, indexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 1));
    assertEquals(2, indexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 2));
    assertEquals(8, indexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 3));
    assertEquals(9, indexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 9));

    assertEquals(3, indexOfChar("zzabyycdxx", new NotInStringCharFilter("zax"), 0));
    assertEquals(3, indexOfChar("zzabyycdxx", new NotInStringCharFilter("zax"), -1));
    assertEquals(-1, indexOfChar("zzabyycdxx",new NotInStringCharFilter("zax"), 100));
    assertEquals(3, indexOfChar("zzabyycdxx", new NotInStringCharFilter("zax"), 1));
    assertEquals(3, indexOfChar("zzabyycdxx", new NotInStringCharFilter("zax"), 2));
    assertEquals(3, indexOfChar("zzabyycdxx", new NotInStringCharFilter("zax"), 3));
    assertEquals(-1, indexOfChar("zzabyycdxx", new NotInStringCharFilter("zax"), 9));

    assertEquals(3, indexOfChar("zzabyycdxx", new InStringCharFilter("byx"), 0));
    assertEquals(3, indexOfChar("zzabyycdxx", new InStringCharFilter("byx"), -1));
    assertEquals(-1, indexOfChar("zzabyycdxx", new InStringCharFilter("byx"), 100));
    assertEquals(3, indexOfChar("zzabyycdxx", new InStringCharFilter("byx"), 1));
    assertEquals(4, indexOfChar("zzabyycdxx", new InStringCharFilter("byx"), 4));
    assertEquals(8, indexOfChar("zzabyycdxx", new InStringCharFilter("byx"), 6));

    assertEquals(0, indexOfChar("zzabyycdxx", new NotInStringCharFilter("byx"), 0));
    assertEquals(0, indexOfChar("zzabyycdxx", new NotInStringCharFilter("byx"), -1));
    assertEquals(-1, indexOfChar("zzabyycdxx", new NotInStringCharFilter("byx"), 100));
    assertEquals(1, indexOfChar("zzabyycdxx", new NotInStringCharFilter("byx"), 1));
    assertEquals(6, indexOfChar("zzabyycdxx", new NotInStringCharFilter("byx"), 4));
    assertEquals(7, indexOfChar("zzabyycdxx", new NotInStringCharFilter("byx"), 7));
    assertEquals(-1, indexOfChar("zzabyycdxx", new NotInStringCharFilter("byx"), 8));

    assertEquals(-1, indexOfChar("ab", new InStringCharFilter("zx"), 0));
    assertEquals(-1, indexOfChar("ab", new InStringCharFilter("zx"), -1));
    assertEquals(-1, indexOfChar("ab", new InStringCharFilter("zx"), 100));

    assertEquals(0, indexOfChar("ab", new NotInStringCharFilter("zx"), 0));
    assertEquals(0, indexOfChar("ab", new NotInStringCharFilter("zx"), -1));
    assertEquals(-1, indexOfChar("ab", new NotInStringCharFilter("zx"), 100));

  }

  @Test
  public void testIndexOfAnyChar_String_CharArray_int() {
    assertEquals(-1, indexOfAnyChar(null, (char[]) null, 0));
    assertEquals(-1, indexOfAnyChar(null, (char[]) null, -1));
    assertEquals(-1, indexOfAnyChar(null, (char[]) null, 100));
    assertEquals(-1, indexOfAnyChar(null, new char[0], 0));
    assertEquals(-1, indexOfAnyChar(null, new char[0], -1));
    assertEquals(-1, indexOfAnyChar(null, new char[0], 100));
    assertEquals(-1, indexOfAnyChar(null, new char[] {'a','b'}, 0));
    assertEquals(-1, indexOfAnyChar(null, new char[] {'a','b'}, -1));
    assertEquals(-1, indexOfAnyChar(null, new char[] {'a','b'}, 100));

    assertEquals(-1, indexOfAnyChar("", (char[]) null, 0));
    assertEquals(-1, indexOfAnyChar("", (char[]) null, -1));
    assertEquals(-1, indexOfAnyChar("", (char[]) null, 100));
    assertEquals(-1, indexOfAnyChar("", new char[0], 0));
    assertEquals(-1, indexOfAnyChar("", new char[0], -1));
    assertEquals(-1, indexOfAnyChar("", new char[0], 100));
    assertEquals(-1, indexOfAnyChar("", new char[] {'a','b'}, 0));
    assertEquals(-1, indexOfAnyChar("", new char[] {'a','b'}, -1));
    assertEquals(-1, indexOfAnyChar("", new char[] {'a','b'}, 100));

    assertEquals(-1, indexOfAnyChar("zzabyycdxx", (char[]) null, 0));
    assertEquals(-1, indexOfAnyChar("zzabyycdxx", (char[]) null, -1));
    assertEquals(-1, indexOfAnyChar("zzabyycdxx", (char[]) null, 100));
    assertEquals(-1, indexOfAnyChar("zzabyycdxx", new char[0], 0));
    assertEquals(-1, indexOfAnyChar("zzabyycdxx", new char[0], -1));
    assertEquals(-1, indexOfAnyChar("zzabyycdxx", new char[0], 100));

    assertEquals(0, indexOfAnyChar("zzabyycdxx", new char[] {'z','a','x'}, 0));
    assertEquals(0, indexOfAnyChar("zzabyycdxx", new char[] {'z','a','x'}, -1));
    assertEquals(-1, indexOfAnyChar("zzabyycdxx", new char[] {'z','a','x'}, 100));
    assertEquals(1, indexOfAnyChar("zzabyycdxx", new char[] {'z','a','x'}, 1));
    assertEquals(2, indexOfAnyChar("zzabyycdxx", new char[] {'z','a','x'}, 2));
    assertEquals(8, indexOfAnyChar("zzabyycdxx", new char[] {'z','a','x'}, 3));
    assertEquals(9, indexOfAnyChar("zzabyycdxx", new char[] {'z','a','x'}, 9));

    assertEquals(3, indexOfAnyChar("zzabyycdxx", new char[] {'b','y','x'}, 0));
    assertEquals(3, indexOfAnyChar("zzabyycdxx", new char[] {'b','y','x'}, -1));
    assertEquals(-1, indexOfAnyChar("zzabyycdxx", new char[] {'b','y','x'}, 100));
    assertEquals(3, indexOfAnyChar("zzabyycdxx", new char[] {'b','y','x'}, 1));
    assertEquals(4, indexOfAnyChar("zzabyycdxx", new char[] {'b','y','x'}, 4));
    assertEquals(8, indexOfAnyChar("zzabyycdxx", new char[] {'b','y','x'}, 6));

    assertEquals(-1, indexOfAnyChar("ab", new char[] {'z'}, 0));
    assertEquals(-1, indexOfAnyChar("ab", new char[] {'z'}, -1));
    assertEquals(-1, indexOfAnyChar("ab", new char[] {'z'}, 100));

  }

  @Test
  public void testIndexOfAnyChar_String_String_int() {
    assertEquals(-1, indexOfAnyChar(null, (String) null, 0));
    assertEquals(-1, indexOfAnyChar(null, (String) null, -1));
    assertEquals(-1, indexOfAnyChar(null, (String) null, 100));
    assertEquals(-1, indexOfAnyChar(null, "", 0));
    assertEquals(-1, indexOfAnyChar(null, "", -1));
    assertEquals(-1, indexOfAnyChar(null, "", 100));
    assertEquals(-1, indexOfAnyChar(null, "ab", 0));
    assertEquals(-1, indexOfAnyChar(null, "ab", -1));
    assertEquals(-1, indexOfAnyChar(null, "ab", 100));

    assertEquals(-1, indexOfAnyChar("", (String) null, 0));
    assertEquals(-1, indexOfAnyChar("", (String) null, -1));
    assertEquals(-1, indexOfAnyChar("", (String) null, 100));
    assertEquals(-1, indexOfAnyChar("", "", 0));
    assertEquals(-1, indexOfAnyChar("", "", -1));
    assertEquals(-1, indexOfAnyChar("", "", 100));
    assertEquals(-1, indexOfAnyChar("", "ab", 0));
    assertEquals(-1, indexOfAnyChar("", "ab", -1));
    assertEquals(-1, indexOfAnyChar("", "ab", 100));

    assertEquals(-1, indexOfAnyChar("zzabyycdxx", (String) null, 0));
    assertEquals(-1, indexOfAnyChar("zzabyycdxx", (String) null, -1));
    assertEquals(-1, indexOfAnyChar("zzabyycdxx", (String) null, 100));
    assertEquals(-1, indexOfAnyChar("zzabyycdxx", "", 0));
    assertEquals(-1, indexOfAnyChar("zzabyycdxx", "", -1));
    assertEquals(-1, indexOfAnyChar("zzabyycdxx", "", 100));

    assertEquals(0, indexOfAnyChar("zzabyycdxx", "zax", 0));
    assertEquals(0, indexOfAnyChar("zzabyycdxx", "zax", -1));
    assertEquals(-1, indexOfAnyChar("zzabyycdxx","zax", 100));
    assertEquals(1, indexOfAnyChar("zzabyycdxx", "zax", 1));
    assertEquals(2, indexOfAnyChar("zzabyycdxx", "zax", 2));
    assertEquals(8, indexOfAnyChar("zzabyycdxx", "zax", 3));
    assertEquals(9, indexOfAnyChar("zzabyycdxx", "zax", 9));

    assertEquals(3, indexOfAnyChar("zzabyycdxx", "byx", 0));
    assertEquals(3, indexOfAnyChar("zzabyycdxx", "byx", -1));
    assertEquals(-1, indexOfAnyChar("zzabyycdxx", "byx", 100));
    assertEquals(3, indexOfAnyChar("zzabyycdxx", "byx", 1));
    assertEquals(4, indexOfAnyChar("zzabyycdxx", "byx", 4));
    assertEquals(8, indexOfAnyChar("zzabyycdxx", "byx", 6));

    assertEquals(-1, indexOfAnyChar("ab", "zx", 0));
    assertEquals(-1, indexOfAnyChar("ab", "zx", -1));
    assertEquals(-1, indexOfAnyChar("ab", "zx", 100));
  }


  @Test
  public void testIndexOfAnyCharBut_String_CharArray_int() {
    assertEquals(-1, indexOfAnyCharBut(null, (char[]) null, 0));
    assertEquals(-1, indexOfAnyCharBut(null, (char[]) null, -1));
    assertEquals(-1, indexOfAnyCharBut(null, (char[]) null, 100));
    assertEquals(-1, indexOfAnyCharBut(null, new char[0], 0));
    assertEquals(-1, indexOfAnyCharBut(null, new char[0], -1));
    assertEquals(-1, indexOfAnyCharBut(null, new char[0], 100));
    assertEquals(-1, indexOfAnyCharBut(null, new char[] {'a','b'}, 0));
    assertEquals(-1, indexOfAnyCharBut(null, new char[] {'a','b'}, -1));
    assertEquals(-1, indexOfAnyCharBut(null, new char[] {'a','b'}, 100));

    assertEquals(-1, indexOfAnyCharBut("", (char[]) null, 0));
    assertEquals(-1, indexOfAnyCharBut("", (char[]) null, -1));
    assertEquals(-1, indexOfAnyCharBut("", (char[]) null, 100));
    assertEquals(-1, indexOfAnyCharBut("", new char[0], 0));
    assertEquals(-1, indexOfAnyCharBut("", new char[0], -1));
    assertEquals(-1, indexOfAnyCharBut("", new char[0], 100));
    assertEquals(-1, indexOfAnyCharBut("", new char[] {'a','b'}, 0));
    assertEquals(-1, indexOfAnyCharBut("", new char[] {'a','b'}, -1));
    assertEquals(-1, indexOfAnyCharBut("", new char[] {'a','b'}, 100));

    assertEquals(0, indexOfAnyCharBut("zzabyycdxx", (char[]) null, 0));
    assertEquals(0, indexOfAnyCharBut("zzabyycdxx", (char[]) null, -1));
    assertEquals(-1, indexOfAnyCharBut("zzabyycdxx", (char[]) null, 100));
    assertEquals(0, indexOfAnyCharBut("zzabyycdxx", new char[0], 0));
    assertEquals(1, indexOfAnyCharBut("zzabyycdxx", new char[0], 1));
    assertEquals(0, indexOfAnyCharBut("zzabyycdxx", new char[0], -1));
    assertEquals(-1, indexOfAnyCharBut("zzabyycdxx", new char[0], 100));

    assertEquals(3, indexOfAnyCharBut("zzabyycdxx", new char[] {'z','a','x'}, 0));
    assertEquals(3, indexOfAnyCharBut("zzabyycdxx", new char[] {'z','a','x'}, -1));
    assertEquals(-1, indexOfAnyCharBut("zzabyycdxx", new char[] {'z','a','x'}, 100));
    assertEquals(3, indexOfAnyCharBut("zzabyycdxx", new char[] {'z','a','x'}, 1));
    assertEquals(3, indexOfAnyCharBut("zzabyycdxx", new char[] {'z','a','x'}, 2));
    assertEquals(3, indexOfAnyCharBut("zzabyycdxx", new char[] {'z','a','x'}, 3));
    assertEquals(-1, indexOfAnyCharBut("zzabyycdxx", new char[] {'z','a','x'}, 9));

    assertEquals(0, indexOfAnyCharBut("zzabyycdxx", new char[] {'b','y','x'}, 0));
    assertEquals(0, indexOfAnyCharBut("zzabyycdxx", new char[] {'b','y','x'}, -1));
    assertEquals(-1, indexOfAnyCharBut("zzabyycdxx", new char[] {'b','y','x'}, 100));
    assertEquals(1, indexOfAnyCharBut("zzabyycdxx", new char[] {'b','y','x'}, 1));
    assertEquals(6, indexOfAnyCharBut("zzabyycdxx", new char[] {'b','y','x'}, 4));
    assertEquals(6, indexOfAnyCharBut("zzabyycdxx", new char[] {'b','y','x'}, 6));

    assertEquals(0, indexOfAnyCharBut("ab", new char[] {'z'}, 0));
    assertEquals(0, indexOfAnyCharBut("ab", new char[] {'z'}, -1));
    assertEquals(-1, indexOfAnyCharBut("ab", new char[] {'z'}, 100));

  }

  @Test
  public void testIndexOfAnyCharBut_String_String_int() {
    assertEquals(-1, indexOfAnyCharBut(null, (String) null, 0));
    assertEquals(-1, indexOfAnyCharBut(null, (String) null, -1));
    assertEquals(-1, indexOfAnyCharBut(null, (String) null, 100));
    assertEquals(-1, indexOfAnyCharBut(null, "", 0));
    assertEquals(-1, indexOfAnyCharBut(null, "", -1));
    assertEquals(-1, indexOfAnyCharBut(null, "", 100));
    assertEquals(-1, indexOfAnyCharBut(null, "ab", 0));
    assertEquals(-1, indexOfAnyCharBut(null, "ab", -1));
    assertEquals(-1, indexOfAnyCharBut(null, "ab", 100));

    assertEquals(-1, indexOfAnyCharBut("", (String) null, 0));
    assertEquals(-1, indexOfAnyCharBut("", (String) null, -1));
    assertEquals(-1, indexOfAnyCharBut("", (String) null, 100));
    assertEquals(-1, indexOfAnyCharBut("", "", 0));
    assertEquals(-1, indexOfAnyCharBut("", "", -1));
    assertEquals(-1, indexOfAnyCharBut("", "", 100));
    assertEquals(-1, indexOfAnyCharBut("", "ab", 0));
    assertEquals(-1, indexOfAnyCharBut("", "ab", -1));
    assertEquals(-1, indexOfAnyCharBut("", "ab", 100));

    assertEquals(0, indexOfAnyCharBut("zzabyycdxx", (String) null, 0));
    assertEquals(0, indexOfAnyCharBut("zzabyycdxx", (String) null, -1));
    assertEquals(-1, indexOfAnyCharBut("zzabyycdxx", (String) null, 100));
    assertEquals(0, indexOfAnyCharBut("zzabyycdxx", "", 0));
    assertEquals(0, indexOfAnyCharBut("zzabyycdxx", "", -1));
    assertEquals(-1, indexOfAnyCharBut("zzabyycdxx", "", 100));

    assertEquals(3, indexOfAnyCharBut("zzabyycdxx", "zax", 0));
    assertEquals(3, indexOfAnyCharBut("zzabyycdxx", "zax", -1));
    assertEquals(-1, indexOfAnyCharBut("zzabyycdxx","zax", 100));
    assertEquals(3, indexOfAnyCharBut("zzabyycdxx", "zax", 1));
    assertEquals(3, indexOfAnyCharBut("zzabyycdxx", "zax", 2));
    assertEquals(3, indexOfAnyCharBut("zzabyycdxx", "zax", 3));
    assertEquals(-1, indexOfAnyCharBut("zzabyycdxx", "zax", 9));

    assertEquals(0, indexOfAnyCharBut("zzabyycdxx", "byx", 0));
    assertEquals(0, indexOfAnyCharBut("zzabyycdxx", "byx", -1));
    assertEquals(-1, indexOfAnyCharBut("zzabyycdxx", "byx", 100));
    assertEquals(1, indexOfAnyCharBut("zzabyycdxx", "byx", 1));
    assertEquals(6, indexOfAnyCharBut("zzabyycdxx", "byx", 4));
    assertEquals(6, indexOfAnyCharBut("zzabyycdxx", "byx", 6));

    assertEquals(0, indexOfAnyCharBut("ab", "zx", 0));
    assertEquals(0, indexOfAnyCharBut("ab", "zx", -1));
    assertEquals(-1, indexOfAnyCharBut("ab", "zx", 100));
  }


  @Test
  public void testIndexOf() {
    assertEquals(-1, indexOf(null, (String)null, 0, false));
    assertEquals(-1, indexOf(null, (String)null, -1, false));
    assertEquals(-1, indexOf(null, (String)null, 100, false));

    assertEquals(-1, indexOf("", (String)null, 0, false));
    assertEquals(-1, indexOf("", (String)null, -1, false));
    assertEquals(-1, indexOf("", (String)null, 100, false));

    assertEquals(-1, indexOf("", "", 0, false));
    assertEquals(-1, indexOf("", "", -1, false));
    assertEquals(-1, indexOf("", "", 100, false));

    assertEquals(0, indexOf("aabaabaa", "a", 0, false));
    assertEquals(0, indexOf("aabaabaa", "a", -1, false));
    assertEquals(-1, indexOf("aabaabaa", "a", 100, false));
    assertEquals(1, indexOf("aabaabaa", "a", 1, false));
    assertEquals(3, indexOf("aabaabaa", "a", 2, false));


    assertEquals(2, indexOf("aabaabaa", "b", 0, false));
    assertEquals(2, indexOf("aabaabaa", "b", -1, false));
    assertEquals(-1, indexOf("aabaabaa", "b", 100, false));
    assertEquals(5, indexOf("aabaabaa", "b", 3, false));

    assertEquals(-1, indexOf("aabaabaa", "Ab", 0, false));
    assertEquals(1, indexOf("aabaabaa", "Ab", 0, true));
    assertEquals(-1, indexOf("aabaabaa", "aB", -1, false));
    assertEquals(1, indexOf("aabaabaa", "aB", -1, true));
    assertEquals(-1, indexOf("aabaabaa", "ab", 100, false));
    assertEquals(-1, indexOf("aabaabaa", "ab", 100, true));
    assertEquals(-1, indexOf("aabaabaa", "AB", 2, false));
    assertEquals(4, indexOf("aabaabaa", "AB", 2, true));

    assertEquals(0, indexOf("aabaabaa", "", 0, false));
    assertEquals(0, indexOf("aabaabaa", "", -1, false));
    assertEquals(-1, indexOf("aabaabaa", "", 100, false));
    assertEquals(4, indexOf("aabaabaa", "", 4, false));
  }

  @Test
  public void testIndexOfAny_String() {
    assertEquals(-1, indexOfAny(null, (String[]) null, 0, false));
    assertEquals(-1, indexOfAny(null, new String[]{"ob", "ba"}, 0, false));
    assertEquals(-1, indexOfAny("foobar", (String[]) null, 0, false));

    assertEquals(2, indexOfAny("foobar", new String[]{"ob", "ba"}, 0, false));
    assertEquals(2, indexOfAny("foobar", new String[]{"ob", "ba"}, -1, false));
    assertEquals(-1, indexOfAny("foobar", new String[]{"ob", "ba"}, 100, false));
    assertEquals(3, indexOfAny("foobar", new String[]{"ob", "ba"}, 3, false));

    assertEquals(-1, indexOfAny("foobar", new String[0], 0, false));
    assertEquals(-1, indexOfAny(null, new String[0], 0, false));
    assertEquals(-1, indexOfAny("", new String[0], 0, false));
    assertEquals(-1, indexOfAny("foobar", new String[] {"llll"}, 0, false));

    assertEquals(0, indexOfAny("foobar", new String[] {""}, 0, false));
    assertEquals(0, indexOfAny("foobar", new String[] {""}, -1, false));
    assertEquals(-1, indexOfAny("foobar", new String[] {""}, 100, false));
    assertEquals(4, indexOfAny("foobar", new String[] {""}, 4, false));

    assertEquals(-1, indexOfAny("", new String[] {""}, 0, false));
    assertEquals(-1, indexOfAny("", new String[] {"a"}, 0, false));
    assertEquals(-1, indexOfAny("", new String[] {null}, 0, false));
    assertEquals(-1, indexOfAny("foobar", new String[] {null}, 0, false));
    assertEquals(0, indexOfAny("foobar", new String[] {null, ""}, 0, false));
    assertEquals(-1, indexOfAny(null, new String[] {null}, 0, false));

    assertEquals(2, indexOfAny("foobar", new String[]{"ob", null}, -1, false));
  }

  @Test
  public void testLastIndexOfChar() {
    assertEquals(-1, lastIndexOfChar(null, ' ', 0));
    assertEquals(-1, lastIndexOfChar(null, ' ', -1));
    assertEquals(-1, lastIndexOfChar("", ' ', 0));
    assertEquals(-1, lastIndexOfChar("", ' ', -1));

    assertEquals(7, lastIndexOfChar("aabaabaa", 'a', 8));
    assertEquals(7, lastIndexOfChar("aabaabaa", 'a', Integer.MAX_VALUE));
    assertEquals(5, lastIndexOfChar("aabaabaa", 'b', 8));
    assertEquals(2, lastIndexOfChar("aabaabaa", 'b', 3));
    assertEquals(5, lastIndexOfChar("aabaabaa", 'b', 9));
    assertEquals(5, lastIndexOfChar("aabaabaa", 'b', 100));
    assertEquals(-1, lastIndexOfChar("aabaabaa", 'b', -1));
    assertEquals(0, lastIndexOfChar("aabaabaa", 'a', 0));
  }

  @Test
  public void testLastIndexOfChar_String_CharFilter_int() {
    assertEquals(-1, lastIndexOfChar(null, (CharFilter) null, 0));
    assertEquals(-1, lastIndexOfChar(null, (CharFilter) null, -1));
    assertEquals(-1, lastIndexOfChar(null, (CharFilter) null, 100));

    assertEquals(-1, lastIndexOfChar(null, new InStringCharFilter(""), 0));
    assertEquals(-1, lastIndexOfChar(null, new InStringCharFilter(""), -1));
    assertEquals(-1, lastIndexOfChar(null, new InStringCharFilter(""), 100));
    assertEquals(-1, lastIndexOfChar(null, new InStringCharFilter("ab"), 0));
    assertEquals(-1, lastIndexOfChar(null, new InStringCharFilter("ab"), -1));
    assertEquals(-1, lastIndexOfChar(null, new InStringCharFilter("ab"), 100));

    assertEquals(-1, lastIndexOfChar(null, new NotInStringCharFilter(""), 0));
    assertEquals(-1, lastIndexOfChar(null, new NotInStringCharFilter(""), -1));
    assertEquals(-1, lastIndexOfChar(null, new NotInStringCharFilter(""), 100));
    assertEquals(-1, lastIndexOfChar(null, new NotInStringCharFilter("ab"), 0));
    assertEquals(-1, lastIndexOfChar(null, new NotInStringCharFilter("ab"), -1));
    assertEquals(-1, lastIndexOfChar(null, new NotInStringCharFilter("ab"), 100));

    assertEquals(-1, lastIndexOfChar("", (CharFilter) null, 0));
    assertEquals(-1, lastIndexOfChar("", (CharFilter) null, -1));
    assertEquals(-1, lastIndexOfChar("", (CharFilter) null, 100));

    assertEquals(-1, lastIndexOfChar("", new InStringCharFilter(""), 0));
    assertEquals(-1, lastIndexOfChar("", new InStringCharFilter(""), -1));
    assertEquals(-1, lastIndexOfChar("", new InStringCharFilter(""), 100));
    assertEquals(-1, lastIndexOfChar("", new InStringCharFilter("ab"), 0));
    assertEquals(-1, lastIndexOfChar("", new InStringCharFilter("ab"), -1));
    assertEquals(-1, lastIndexOfChar("", new InStringCharFilter("ab"), 100));

    assertEquals(-1, lastIndexOfChar("", new NotInStringCharFilter(""), 0));
    assertEquals(-1, lastIndexOfChar("", new NotInStringCharFilter(""), -1));
    assertEquals(-1, lastIndexOfChar("", new NotInStringCharFilter(""), 100));
    assertEquals(-1, lastIndexOfChar("", new NotInStringCharFilter("ab"), 0));
    assertEquals(-1, lastIndexOfChar("", new NotInStringCharFilter("ab"), -1));
    assertEquals(-1, lastIndexOfChar("", new NotInStringCharFilter("ab"), 100));

    assertEquals(-1, lastIndexOfChar("zzabyycdxx", (CharFilter) null, 0));
    assertEquals(-1, lastIndexOfChar("zzabyycdxx", (CharFilter) null, -1));
    assertEquals(-1, lastIndexOfChar("zzabyycdxx", (CharFilter) null, 100));

    assertEquals(-1, lastIndexOfChar("zzabyycdxx", new InStringCharFilter(""), 0));
    assertEquals(-1, lastIndexOfChar("zzabyycdxx", new InStringCharFilter(""), -1));
    assertEquals(-1, lastIndexOfChar("zzabyycdxx", new InStringCharFilter(""), 100));

    assertEquals(0, lastIndexOfChar("zzabyycdxx", new NotInStringCharFilter(""), 0));
    assertEquals(-1, lastIndexOfChar("zzabyycdxx", new NotInStringCharFilter(""), -1));
    assertEquals(9, lastIndexOfChar("zzabyycdxx", new NotInStringCharFilter(""), 100));

    assertEquals(0, lastIndexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 0));
    assertEquals(-1, lastIndexOfChar("zzabyycdxx", new InStringCharFilter("zax"), -1));
    assertEquals(9, lastIndexOfChar("zzabyycdxx",new InStringCharFilter("zax"), 100));
    assertEquals(1, lastIndexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 1));
    assertEquals(2, lastIndexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 2));
    assertEquals(2, lastIndexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 3));
    assertEquals(2, lastIndexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 7));

    assertEquals(-1, lastIndexOfChar("zzabyycdxx", new NotInStringCharFilter("zax"), 0));
    assertEquals(-1, lastIndexOfChar("zzabyycdxx", new NotInStringCharFilter("zax"), -1));
    assertEquals(7, lastIndexOfChar("zzabyycdxx",new NotInStringCharFilter("zax"), 100));
    assertEquals(-1, lastIndexOfChar("zzabyycdxx", new NotInStringCharFilter("zax"), 1));
    assertEquals(-1, lastIndexOfChar("zzabyycdxx", new NotInStringCharFilter("zax"), 2));
    assertEquals(3, lastIndexOfChar("zzabyycdxx", new NotInStringCharFilter("zax"), 3));
    assertEquals(7, lastIndexOfChar("zzabyycdxx", new NotInStringCharFilter("zax"), 9));

    assertEquals(-1, lastIndexOfChar("zzabyycdxx", new InStringCharFilter("byx"), 0));
    assertEquals(-1, lastIndexOfChar("zzabyycdxx", new InStringCharFilter("byx"), -1));
    assertEquals(9, lastIndexOfChar("zzabyycdxx", new InStringCharFilter("byx"), 100));
    assertEquals(-1, lastIndexOfChar("zzabyycdxx", new InStringCharFilter("byx"), 1));
    assertEquals(4, lastIndexOfChar("zzabyycdxx", new InStringCharFilter("byx"), 4));
    assertEquals(5, lastIndexOfChar("zzabyycdxx", new InStringCharFilter("byx"), 6));

    assertEquals(0, lastIndexOfChar("zzabyycdxx", new NotInStringCharFilter("byx"), 0));
    assertEquals(-1, lastIndexOfChar("zzabyycdxx", new NotInStringCharFilter("byx"), -1));
    assertEquals(7, lastIndexOfChar("zzabyycdxx", new NotInStringCharFilter("byx"), 100));
    assertEquals(1, lastIndexOfChar("zzabyycdxx", new NotInStringCharFilter("byx"), 1));
    assertEquals(2, lastIndexOfChar("zzabyycdxx", new NotInStringCharFilter("byx"), 4));
    assertEquals(7, lastIndexOfChar("zzabyycdxx", new NotInStringCharFilter("byx"), 7));
    assertEquals(7, lastIndexOfChar("zzabyycdxx", new NotInStringCharFilter("byx"), 8));

    assertEquals(-1, lastIndexOfChar("ab", new InStringCharFilter("zx"), 0));
    assertEquals(-1, lastIndexOfChar("ab", new InStringCharFilter("zx"), -1));
    assertEquals(-1, lastIndexOfChar("ab", new InStringCharFilter("zx"), 100));

    assertEquals(0, lastIndexOfChar("ab", new NotInStringCharFilter("zx"), 0));
    assertEquals(-1, lastIndexOfChar("ab", new NotInStringCharFilter("zx"), -1));
    assertEquals(1, lastIndexOfChar("ab", new NotInStringCharFilter("zx"), 100));

  }

  @Test
  public void testLastIndexOfAnyChar_String_CharArray_int() {
    assertEquals(-1, lastIndexOfAnyChar(null, (char[]) null, 0));
    assertEquals(-1, lastIndexOfAnyChar(null, (char[]) null, -1));
    assertEquals(-1, lastIndexOfAnyChar(null, (char[]) null, 100));
    assertEquals(-1, lastIndexOfAnyChar(null, new char[0], 0));
    assertEquals(-1, lastIndexOfAnyChar(null, new char[0], -1));
    assertEquals(-1, lastIndexOfAnyChar(null, new char[0], 100));
    assertEquals(-1, lastIndexOfAnyChar(null, new char[] {'a','b'}, 0));
    assertEquals(-1, lastIndexOfAnyChar(null, new char[] {'a','b'}, -1));
    assertEquals(-1, lastIndexOfAnyChar(null, new char[] {'a','b'}, 100));

    assertEquals(-1, lastIndexOfAnyChar("", (char[]) null, 0));
    assertEquals(-1, lastIndexOfAnyChar("", (char[]) null, -1));
    assertEquals(-1, lastIndexOfAnyChar("", (char[]) null, 100));
    assertEquals(-1, lastIndexOfAnyChar("", new char[0], 0));
    assertEquals(-1, lastIndexOfAnyChar("", new char[0], -1));
    assertEquals(-1, lastIndexOfAnyChar("", new char[0], 100));
    assertEquals(-1, lastIndexOfAnyChar("", new char[] {'a','b'}, 0));
    assertEquals(-1, lastIndexOfAnyChar("", new char[] {'a','b'}, -1));
    assertEquals(-1, lastIndexOfAnyChar("", new char[] {'a','b'}, 100));

    assertEquals(-1, lastIndexOfAnyChar("zzabyycdxx", (char[]) null, 0));
    assertEquals(-1, lastIndexOfAnyChar("zzabyycdxx", (char[]) null, -1));
    assertEquals(-1, lastIndexOfAnyChar("zzabyycdxx", (char[]) null, 100));
    assertEquals(-1, lastIndexOfAnyChar("zzabyycdxx", new char[0], 0));
    assertEquals(-1, lastIndexOfAnyChar("zzabyycdxx", new char[0], -1));
    assertEquals(-1, lastIndexOfAnyChar("zzabyycdxx", new char[0], 100));

    assertEquals(0, lastIndexOfAnyChar("zzabyycdxx", new char[] {'z','a','x'}, 0));
    assertEquals(-1, lastIndexOfAnyChar("zzabyycdxx", new char[] {'z','a','x'}, -1));
    assertEquals(9, lastIndexOfAnyChar("zzabyycdxx", new char[] {'z','a','x'}, 100));
    assertEquals(1, lastIndexOfAnyChar("zzabyycdxx", new char[] {'z','a','x'}, 1));
    assertEquals(2, lastIndexOfAnyChar("zzabyycdxx", new char[] {'z','a','x'}, 2));
    assertEquals(2, lastIndexOfAnyChar("zzabyycdxx", new char[] {'z','a','x'}, 3));
    assertEquals(9, lastIndexOfAnyChar("zzabyycdxx", new char[] {'z','a','x'}, 9));

    assertEquals(-1, lastIndexOfAnyChar("zzabyycdxx", new char[] {'b','y','x'}, 0));
    assertEquals(-1, lastIndexOfAnyChar("zzabyycdxx", new char[] {'b','y','x'}, -1));
    assertEquals(9, lastIndexOfAnyChar("zzabyycdxx", new char[] {'b','y','x'}, 100));
    assertEquals(-1, lastIndexOfAnyChar("zzabyycdxx", new char[] {'b','y','x'}, 1));
    assertEquals(4, lastIndexOfAnyChar("zzabyycdxx", new char[] {'b','y','x'}, 4));
    assertEquals(5, lastIndexOfAnyChar("zzabyycdxx", new char[] {'b','y','x'}, 6));

    assertEquals(-1, lastIndexOfAnyChar("ab", new char[] {'z'}, 0));
    assertEquals(-1, lastIndexOfAnyChar("ab", new char[] {'z'}, -1));
    assertEquals(-1, lastIndexOfAnyChar("ab", new char[] {'z'}, 100));
  }

  @Test
  public void testLastIndexOfAnyChar_String_String_int() {
    assertEquals(-1, lastIndexOfAnyChar(null, (String) null, 0));
    assertEquals(-1, lastIndexOfAnyChar(null, (String) null, -1));
    assertEquals(-1, lastIndexOfAnyChar(null, (String) null, 100));
    assertEquals(-1, lastIndexOfAnyChar(null, "", 0));
    assertEquals(-1, lastIndexOfAnyChar(null, "", -1));
    assertEquals(-1, lastIndexOfAnyChar(null, "", 100));
    assertEquals(-1, lastIndexOfAnyChar(null, "ab", 0));
    assertEquals(-1, lastIndexOfAnyChar(null, "ab", -1));
    assertEquals(-1, lastIndexOfAnyChar(null, "ab", 100));

    assertEquals(-1, lastIndexOfAnyChar("", (String) null, 0));
    assertEquals(-1, lastIndexOfAnyChar("", (String) null, -1));
    assertEquals(-1, lastIndexOfAnyChar("", (String) null, 100));
    assertEquals(-1, lastIndexOfAnyChar("", "", 0));
    assertEquals(-1, lastIndexOfAnyChar("", "", -1));
    assertEquals(-1, lastIndexOfAnyChar("", "", 100));
    assertEquals(-1, lastIndexOfAnyChar("", "ab", 0));
    assertEquals(-1, lastIndexOfAnyChar("", "ab", -1));
    assertEquals(-1, lastIndexOfAnyChar("", "ab", 100));

    assertEquals(-1, lastIndexOfAnyChar("zzabyycdxx", (String) null, 0));
    assertEquals(-1, lastIndexOfAnyChar("zzabyycdxx", (String) null, -1));
    assertEquals(-1, lastIndexOfAnyChar("zzabyycdxx", (String) null, 100));
    assertEquals(-1, lastIndexOfAnyChar("zzabyycdxx", "", 0));
    assertEquals(-1, lastIndexOfAnyChar("zzabyycdxx", "", -1));
    assertEquals(-1, lastIndexOfAnyChar("zzabyycdxx", "", 100));

    assertEquals(0, lastIndexOfAnyChar("zzabyycdxx", "zax", 0));
    assertEquals(-1, lastIndexOfAnyChar("zzabyycdxx", "zax", -1));
    assertEquals(9, lastIndexOfAnyChar("zzabyycdxx","zax", 100));
    assertEquals(1, lastIndexOfAnyChar("zzabyycdxx", "zax", 1));
    assertEquals(2, lastIndexOfAnyChar("zzabyycdxx", "zax", 2));
    assertEquals(2, lastIndexOfAnyChar("zzabyycdxx", "zax", 3));
    assertEquals(2, lastIndexOfAnyChar("zzabyycdxx", "zax", 7));

    assertEquals(-1, lastIndexOfAnyChar("zzabyycdxx", "byx", 0));
    assertEquals(-1, lastIndexOfAnyChar("zzabyycdxx", "byx", -1));
    assertEquals(9, lastIndexOfAnyChar("zzabyycdxx", "byx", 100));
    assertEquals(-1, lastIndexOfAnyChar("zzabyycdxx", "byx", 1));
    assertEquals(4, lastIndexOfAnyChar("zzabyycdxx", "byx", 4));
    assertEquals(5, lastIndexOfAnyChar("zzabyycdxx", "byx", 6));

    assertEquals(-1, lastIndexOfAnyChar("ab", "zx", 0));
    assertEquals(-1, lastIndexOfAnyChar("ab", "zx", -1));
    assertEquals(-1, lastIndexOfAnyChar("ab", "zx", 100));
  }


  @Test
  public void testLastIndexOfAnyCharBut_String_CharArray_int() {
    assertEquals(-1, lastIndexOfAnyCharBut(null, (char[]) null, 0));
    assertEquals(-1, lastIndexOfAnyCharBut(null, (char[]) null, -1));
    assertEquals(-1, lastIndexOfAnyCharBut(null, (char[]) null, 100));
    assertEquals(-1, lastIndexOfAnyCharBut(null, new char[0], 0));
    assertEquals(-1, lastIndexOfAnyCharBut(null, new char[0], -1));
    assertEquals(-1, lastIndexOfAnyCharBut(null, new char[0], 100));
    assertEquals(-1, lastIndexOfAnyCharBut(null, new char[] {'a','b'}, 0));
    assertEquals(-1, lastIndexOfAnyCharBut(null, new char[] {'a','b'}, -1));
    assertEquals(-1, lastIndexOfAnyCharBut(null, new char[] {'a','b'}, 100));

    assertEquals(-1, lastIndexOfAnyCharBut("", (char[]) null, 0));
    assertEquals(-1, lastIndexOfAnyCharBut("", (char[]) null, -1));
    assertEquals(-1, lastIndexOfAnyCharBut("", (char[]) null, 100));
    assertEquals(-1, lastIndexOfAnyCharBut("", new char[0], 0));
    assertEquals(-1, lastIndexOfAnyCharBut("", new char[0], -1));
    assertEquals(-1, lastIndexOfAnyCharBut("", new char[0], 100));
    assertEquals(-1, lastIndexOfAnyCharBut("", new char[] {'a','b'}, 0));
    assertEquals(-1, lastIndexOfAnyCharBut("", new char[] {'a','b'}, -1));
    assertEquals(-1, lastIndexOfAnyCharBut("", new char[] {'a','b'}, 100));

    assertEquals(0, lastIndexOfAnyCharBut("zzabyycdxx", (char[]) null, 0));
    assertEquals(-1, lastIndexOfAnyCharBut("zzabyycdxx", (char[]) null, -1));
    assertEquals(9, lastIndexOfAnyCharBut("zzabyycdxx", (char[]) null, 100));
    assertEquals(0, lastIndexOfAnyCharBut("zzabyycdxx", new char[0], 0));
    assertEquals(1, lastIndexOfAnyCharBut("zzabyycdxx", new char[0], 1));
    assertEquals(-1, lastIndexOfAnyCharBut("zzabyycdxx", new char[0], -1));
    assertEquals(9, lastIndexOfAnyCharBut("zzabyycdxx", new char[0], 100));

    assertEquals(-1, lastIndexOfAnyCharBut("zzabyycdxx", new char[] {'z','a','x'}, 0));
    assertEquals(-1, lastIndexOfAnyCharBut("zzabyycdxx", new char[] {'z','a','x'}, -1));
    assertEquals(7, lastIndexOfAnyCharBut("zzabyycdxx", new char[] {'z','a','x'}, 100));
    assertEquals(-1, lastIndexOfAnyCharBut("zzabyycdxx", new char[] {'z','a','x'}, 1));
    assertEquals(-1, lastIndexOfAnyCharBut("zzabyycdxx", new char[] {'z','a','x'}, 2));
    assertEquals(3, lastIndexOfAnyCharBut("zzabyycdxx", new char[] {'z','a','x'}, 3));
    assertEquals(7, lastIndexOfAnyCharBut("zzabyycdxx", new char[] {'z','a','x'}, 9));

    assertEquals(0, lastIndexOfAnyCharBut("zzabyycdxx", new char[] {'b','y','x'}, 0));
    assertEquals(-1, lastIndexOfAnyCharBut("zzabyycdxx", new char[] {'b','y','x'}, -1));
    assertEquals(7, lastIndexOfAnyCharBut("zzabyycdxx", new char[] {'b','y','x'}, 100));
    assertEquals(1, lastIndexOfAnyCharBut("zzabyycdxx", new char[] {'b','y','x'}, 1));
    assertEquals(2, lastIndexOfAnyCharBut("zzabyycdxx", new char[] {'b','y','x'}, 4));
    assertEquals(6, lastIndexOfAnyCharBut("zzabyycdxx", new char[] {'b','y','x'}, 6));

    assertEquals(0, lastIndexOfAnyCharBut("ab", new char[] {'z'}, 0));
    assertEquals(-1, lastIndexOfAnyCharBut("ab", new char[] {'z'}, -1));
    assertEquals(1, lastIndexOfAnyCharBut("ab", new char[] {'z'}, 100));

  }

  @Test
  public void testLastIndexOfAnyCharBut_String_String_int() {
    assertEquals(-1, lastIndexOfAnyCharBut(null, (String) null, 0));
    assertEquals(-1, lastIndexOfAnyCharBut(null, (String) null, -1));
    assertEquals(-1, lastIndexOfAnyCharBut(null, (String) null, 100));
    assertEquals(-1, lastIndexOfAnyCharBut(null, "", 0));
    assertEquals(-1, lastIndexOfAnyCharBut(null, "", -1));
    assertEquals(-1, lastIndexOfAnyCharBut(null, "", 100));
    assertEquals(-1, lastIndexOfAnyCharBut(null, "ab", 0));
    assertEquals(-1, lastIndexOfAnyCharBut(null, "ab", -1));
    assertEquals(-1, lastIndexOfAnyCharBut(null, "ab", 100));

    assertEquals(-1, lastIndexOfAnyCharBut("", (String) null, 0));
    assertEquals(-1, lastIndexOfAnyCharBut("", (String) null, -1));
    assertEquals(-1, lastIndexOfAnyCharBut("", (String) null, 100));
    assertEquals(-1, lastIndexOfAnyCharBut("", "", 0));
    assertEquals(-1, lastIndexOfAnyCharBut("", "", -1));
    assertEquals(-1, lastIndexOfAnyCharBut("", "", 100));
    assertEquals(-1, lastIndexOfAnyCharBut("", "ab", 0));
    assertEquals(-1, lastIndexOfAnyCharBut("", "ab", -1));
    assertEquals(-1, lastIndexOfAnyCharBut("", "ab", 100));

    assertEquals(0, lastIndexOfAnyCharBut("zzabyycdxx", (String) null, 0));
    assertEquals(-1, lastIndexOfAnyCharBut("zzabyycdxx", (String) null, -1));
    assertEquals(9, lastIndexOfAnyCharBut("zzabyycdxx", (String) null, 100));
    assertEquals(0, lastIndexOfAnyCharBut("zzabyycdxx", "", 0));
    assertEquals(1, lastIndexOfAnyCharBut("zzabyycdxx", "", 1));
    assertEquals(-1, lastIndexOfAnyCharBut("zzabyycdxx", "", -1));
    assertEquals(9, lastIndexOfAnyCharBut("zzabyycdxx", "", 100));

    assertEquals(-1, lastIndexOfAnyCharBut("zzabyycdxx", "zax", 0));
    assertEquals(-1, lastIndexOfAnyCharBut("zzabyycdxx", "zax", -1));
    assertEquals(7, lastIndexOfAnyCharBut("zzabyycdxx","zax", 100));
    assertEquals(-1, lastIndexOfAnyCharBut("zzabyycdxx", "zax", 1));
    assertEquals(-1, lastIndexOfAnyCharBut("zzabyycdxx", "zax", 2));
    assertEquals(3, lastIndexOfAnyCharBut("zzabyycdxx", "zax", 3));
    assertEquals(7, lastIndexOfAnyCharBut("zzabyycdxx", "zax", 9));

    assertEquals(0, lastIndexOfAnyCharBut("zzabyycdxx", "byx", 0));
    assertEquals(-1, lastIndexOfAnyCharBut("zzabyycdxx", "byx", -1));
    assertEquals(7, lastIndexOfAnyCharBut("zzabyycdxx", "byx", 100));
    assertEquals(1, lastIndexOfAnyCharBut("zzabyycdxx", "byx", 1));
    assertEquals(2, lastIndexOfAnyCharBut("zzabyycdxx", "byx", 4));
    assertEquals(6, lastIndexOfAnyCharBut("zzabyycdxx", "byx", 6));

    assertEquals(0, lastIndexOfAnyCharBut("ab", "zx", 0));
    assertEquals(-1, lastIndexOfAnyCharBut("ab", "zx", -1));
    assertEquals(1, lastIndexOfAnyCharBut("ab", "zx", 100));
  }


  @Test
  public void testLastIndexOf() {
    assertEquals(-1, lastIndexOf(null, (String)null, 0, false));
    assertEquals(-1, lastIndexOf(null, (String)null, -1, false));
    assertEquals(-1, lastIndexOf(null, (String)null, 100, false));

    assertEquals(-1, lastIndexOf("", (String)null, 0, false));
    assertEquals(-1, lastIndexOf("", (String)null, -1, false));
    assertEquals(-1, lastIndexOf("", (String)null, 100, false));

    assertEquals(-1, lastIndexOf("", "", 0, false));
    assertEquals(-1, lastIndexOf("", "", -1, false));
    assertEquals(-1, lastIndexOf("", "", 100, false));

    assertEquals(0, lastIndexOf("aabaabaa", "a", 0, false));
    assertEquals(-1, lastIndexOf("aabaabaa", "a", -1, false));
    assertEquals(7, lastIndexOf("aabaabaa", "a", 100, false));
    assertEquals(1, lastIndexOf("aabaabaa", "a", 1, false));
    assertEquals(1, lastIndexOf("aabaabaa", "a", 2, false));

    assertEquals(-1, lastIndexOf("aabaabaa", "b", 0, false));
    assertEquals(-1, lastIndexOf("aabaabaa", "b", -1, false));
    assertEquals(5, lastIndexOf("aabaabaa", "b", 100, false));
    assertEquals(2, lastIndexOf("aabaabaa", "b", 3, false));

    assertEquals(-1, lastIndexOf("aabaabaa", "ab", 0, false));
    assertEquals(-1, lastIndexOf("aabaabaa", "ab", -1, false));
    assertEquals(4, lastIndexOf("aabaabaa", "ab", 100, false));
    assertEquals(1, lastIndexOf("aabaabaa", "ab", 3, false));

    assertEquals(0, lastIndexOf("aabaabaa", "", 0, false));
    assertEquals(-1, lastIndexOf("aabaabaa", "", -1, false));
    assertEquals(7, lastIndexOf("aabaabaa", "", 100, false));
    assertEquals(4, lastIndexOf("aabaabaa", "", 4, false));
  }

  @Test
  public void testLastIndexOfAny() {
    assertEquals(-1, lastIndexOfAny(null, (String[]) null, 0, false));
    assertEquals(-1, lastIndexOfAny(null, new String[]{"ob", "ba"}, 0, false));
    assertEquals(-1, lastIndexOfAny("foobar", (String[]) null, 0, false));

    assertEquals(-1, lastIndexOfAny("foobar", new String[]{"ob", "ba"}, 0, false));
    assertEquals(-1, lastIndexOfAny("foobar", new String[]{"ob", "ba"}, -1, false));
    assertEquals(3, lastIndexOfAny("foobar", new String[]{"ob", "ba"}, 100, false));
    assertEquals(3, lastIndexOfAny("foobar", new String[]{"ob", "ba"}, 3, false));

    assertEquals(-1, lastIndexOfAny("foobar", new String[0], 0, false));
    assertEquals(-1, lastIndexOfAny(null, new String[0], 0, false));
    assertEquals(-1, lastIndexOfAny("", new String[0], 0, false));
    assertEquals(-1, lastIndexOfAny("foobar", new String[] {"llll"}, 0, false));

    assertEquals(0, lastIndexOfAny("foobar", new String[] {""}, 0, false));
    assertEquals(-1, lastIndexOfAny("foobar", new String[] {""}, -1, false));
    assertEquals(5, lastIndexOfAny("foobar", new String[] {""}, 100, false));
    assertEquals(4, lastIndexOfAny("foobar", new String[] {""}, 4, false));

    assertEquals(-1, lastIndexOfAny("", new String[] {""}, 0, false));
    assertEquals(-1, lastIndexOfAny("", new String[] {"a"}, 0, false));
    assertEquals(-1, lastIndexOfAny("", new String[] {null}, 0, false));
    assertEquals(-1, lastIndexOfAny("foobar", new String[] {null}, 0, false));
    assertEquals(5, lastIndexOfAny("foobar", new String[] {null, ""}, 100, false));
    assertEquals(-1, lastIndexOfAny(null, new String[] {null}, 0, false));

    assertEquals(2, lastIndexOfAny("foobar", new String[]{"ob", null}, 100, false));
  }

  @Test
  public void testContainsOnly_String() {
    final String str1 = "a";
    final String str2 = "b";
    final String str3 = "ab";
    final String chars1= "b";
    final String chars2= "a";
    final String chars3= "ab";
    assertEquals(false, containsOnly(null, (String) null));
    assertEquals(false, containsOnly("", (String) null));
    assertEquals(false, containsOnly(null, ""));
    assertEquals(false, containsOnly(str1, ""));
    assertEquals(true, containsOnly("", ""));
    assertEquals(true, containsOnly("", chars1));
    assertEquals(false, containsOnly(str1, chars1));
    assertEquals(true, containsOnly(str1, chars2));
    assertEquals(true, containsOnly(str1, chars3));
    assertEquals(true, containsOnly(str2, chars1));
    assertEquals(false, containsOnly(str2, chars2));
    assertEquals(true, containsOnly(str2, chars3));
    assertEquals(false, containsOnly(str3, chars1));
    assertEquals(false, containsOnly(str3, chars2));
    assertEquals(true, containsOnly(str3, chars3));
  }

  @Test
  public void testContainsOnly_Chararray() {
    final String str1 = "a";
    final String str2 = "b";
    final String str3 = "ab";
    final char[] chars1= {'b'};
    final char[] chars2= {'a'};
    final char[] chars3= {'a', 'b'};
    final char[] emptyChars = new char[0];
    assertEquals(false, containsOnly(null, (char[]) null));
    assertEquals(false, containsOnly("", (char[]) null));
    assertEquals(false, containsOnly(null, emptyChars));
    assertEquals(false, containsOnly(str1, emptyChars));
    assertEquals(true, containsOnly("", emptyChars));
    assertEquals(true, containsOnly("", chars1));
    assertEquals(false, containsOnly(str1, chars1));
    assertEquals(true, containsOnly(str1, chars2));
    assertEquals(true, containsOnly(str1, chars3));
    assertEquals(true, containsOnly(str2, chars1));
    assertEquals(false, containsOnly(str2, chars2));
    assertEquals(true, containsOnly(str2, chars3));
    assertEquals(false, containsOnly(str3, chars1));
    assertEquals(false, containsOnly(str3, chars2));
    assertEquals(true, containsOnly(str3, chars3));
  }
//TODO
//  @Test
//  public void testContainsNone_String() {
//    final String str1 = "a";
//    final String str2 = "b";
//    final String str3 = "ab.";
//    final String chars1= "b";
//    final String chars2= ".";
//    final String chars3= "cd";
//    assertEquals(true, containsNone(null, (String) null));
//    assertEquals(true, containsNone("", (String) null));
//    assertEquals(true, containsNone(null, ""));
//    assertEquals(true, containsNone(str1, ""));
//    assertEquals(true, containsNone("", ""));
//    assertEquals(true, containsNone("", chars1));
//    assertEquals(true, containsNone(str1, chars1));
//    assertEquals(true, containsNone(str1, chars2));
//    assertEquals(true, containsNone(str1, chars3));
//    assertEquals(false, containsNone(str2, chars1));
//    assertEquals(true, containsNone(str2, chars2));
//    assertEquals(true, containsNone(str2, chars3));
//    assertEquals(false, containsNone(str3, chars1));
//    assertEquals(false, containsNone(str3, chars2));
//    assertEquals(true, containsNone(str3, chars3));
//  }
//
//  @Test
//  public void testContainsNone_Chararray() {
//    final String str1 = "a";
//    final String str2 = "b";
//    final String str3 = "ab.";
//    final char[] chars1= {'b'};
//    final char[] chars2= {'.'};
//    final char[] chars3= {'c', 'd'};
//    final char[] emptyChars = new char[0];
//    assertEquals(true, containsNone(null, (char[]) null));
//    assertEquals(true, containsNone("", (char[]) null));
//    assertEquals(true, containsNone(null, emptyChars));
//    assertEquals(true, containsNone(str1, emptyChars));
//    assertEquals(true, containsNone("", emptyChars));
//    assertEquals(true, containsNone("", chars1));
//    assertEquals(true, containsNone(str1, chars1));
//    assertEquals(true, containsNone(str1, chars2));
//    assertEquals(true, containsNone(str1, chars3));
//    assertEquals(false, containsNone(str2, chars1));
//    assertEquals(true, containsNone(str2, chars2));
//    assertEquals(true, containsNone(str2, chars3));
//    assertEquals(false, containsNone(str3, chars1));
//    assertEquals(false, containsNone(str3, chars2));
//    assertEquals(true, containsNone(str3, chars3));
//  }


  @Test
  public void testCountMatches_Int() {
    assertEquals(0, countMatches(null, 0));
    assertEquals(0, countMatches("", 0));
    assertEquals(0, countMatches("hello world", 0));
    assertEquals(1, countMatches("hello world", 'h'));
    assertEquals(2, countMatches("hello world", 'o'));
    assertEquals(3, countMatches("hello world", 'l'));

  }

  @Test
  public void testCountMatches_String() {
    assertEquals(0, countMatches(null, null));
    assertEquals(0, countMatches("blah", null));
    assertEquals(0, countMatches(null, "DD"));

    assertEquals(0, countMatches("x", ""));
    assertEquals(0, countMatches("", ""));

    assertEquals(3, countMatches("one long someone sentence of one",
        "one"));
    assertEquals(0, countMatches("one long someone sentence of one",
        "two"));
    assertEquals(4, countMatches("oooooooooooo", "ooo"));
  }
}
