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

import java.util.List;

import org.junit.Test;

import com.github.haixing_hu.lang.StringUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * Unit test for the split() function of Strings class.
 *
 * @author Haixing Hu
 */
public class StringUtilsSplitTest {

  protected static final String WHITESPACE;
  protected static final String NON_WHITESPACE;

  static {
      String ws = "";
      String nws = "";
      for (int i = 0; i < Character.MAX_VALUE; i++) {
          if (Character.isWhitespace((char) i)) {
              ws += String.valueOf((char) i);
          } else if (i < 40) {
              nws += String.valueOf((char) i);
          }
      }
      WHITESPACE = ws;
      NON_WHITESPACE = nws;
  }

  @Test
  public void testSplitByWhitespace() {
    List<String> result = null;

    result = StringUtils.split(null, result);
    assertNull(result);

    result = StringUtils.split("", result);
    assertNull(result);

    String str = "a b  .c";
    result = StringUtils.split(str, result);
    assertEquals(3, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals(".c", result.get(2));

    str = " a ";
    result = StringUtils.split(str, result);
    assertEquals(1, result.size());
    assertEquals("a", result.get(0));

    str = "a" + WHITESPACE + "b" + NON_WHITESPACE + "c";
    result = StringUtils.split(str, result);
    assertEquals(2, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b" + NON_WHITESPACE + "c", result.get(1));
  }

  @Test
  public void testSplitByChar_Char() {
    List<String> result = null;

    result = StringUtils.split(null, '.', false, true, result);
    assertNull(result);
    result = StringUtils.split(null, '.', false, false, result);
    assertNull(result);

    result = StringUtils.split("", '.', false, false, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("", result.get(0));

    result = StringUtils.split(null, '.', true, true, result);
    assertNotNull(result);
    assertEquals(0, result.size());

    result = StringUtils.split("", '.', true, false, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("", result.get(0));

    result = StringUtils.split("", '.', false, true, result);
    assertNotNull(result);
    assertEquals(0, result.size());

    result = StringUtils.split("a.b.. c  ", '.', false, false, result);
    assertNotNull(result);
    assertEquals(4, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("", result.get(2));
    assertEquals(" c  ", result.get(3));

    result = StringUtils.split(null, '.', true, false, result);
    assertNotNull(result);
    assertEquals(0, result.size());

    result = StringUtils.split("a.b.. c  ", '.', false, true, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals(" c  ", result.get(2));

    result = StringUtils.split("", '.', true, true, result);
    assertNotNull(result);
    assertEquals(0, result.size());

    result = StringUtils.split("a.b.. c  ", '.', true, false, result);
    assertNotNull(result);
    assertEquals(4, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("", result.get(2));
    assertEquals("c", result.get(3));


    result = StringUtils.split("a.b.. c  ", '.', true, true, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));

    result = StringUtils.split(". a .", '.', false, false, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("", result.get(0));
    assertEquals(" a ", result.get(1));
    assertEquals("", result.get(2));

    result = StringUtils.split(". a .", '.', false, true, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(" a ", result.get(0));

    result = StringUtils.split(". a .", '.', true, false, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("", result.get(0));
    assertEquals("a", result.get(1));
    assertEquals("", result.get(2));

    result = StringUtils.split(". a .", '.', true, true, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("a", result.get(0));


    result = StringUtils.split(" a  b c", ' ', false, false, result);
    assertNotNull(result);
    assertEquals(5, result.size());
    assertEquals("", result.get(0));
    assertEquals("a", result.get(1));
    assertEquals("", result.get(2));
    assertEquals("b", result.get(3));
    assertEquals("c", result.get(4));

    result = StringUtils.split(" a  b c", ' ', false, true, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));

    result = StringUtils.split(" a  b c", ' ', true, false, result);
    assertNotNull(result);
    assertEquals(5, result.size());
    assertEquals("", result.get(0));
    assertEquals("a", result.get(1));
    assertEquals("", result.get(2));
    assertEquals("b", result.get(3));
    assertEquals("c", result.get(4));

    result = StringUtils.split(" a  b c", ' ', true, true, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));

    result = StringUtils.split(" ;;a;  b; ;c;", ';', false, false, result);
    assertNotNull(result);
    assertEquals(7, result.size());
    assertEquals(" ", result.get(0));
    assertEquals("", result.get(1));
    assertEquals("a", result.get(2));
    assertEquals("  b", result.get(3));
    assertEquals(" ", result.get(4));
    assertEquals("c", result.get(5));
    assertEquals("", result.get(6));

    result = StringUtils.split(" ;;a;  b; ;c;", ';', false, true, result);
    assertNotNull(result);
    assertEquals(5, result.size());
    assertEquals(" ", result.get(0));
    assertEquals("a", result.get(1));
    assertEquals("  b", result.get(2));
    assertEquals(" ", result.get(3));
    assertEquals("c", result.get(4));

    result = StringUtils.split(" ;;a;  b; ;c;", ';', true, false, result);
    assertNotNull(result);
    assertEquals(7, result.size());
    assertEquals("", result.get(0));
    assertEquals("", result.get(1));
    assertEquals("a", result.get(2));
    assertEquals("b", result.get(3));
    assertEquals("", result.get(4));
    assertEquals("c", result.get(5));
    assertEquals("", result.get(6));

    result = StringUtils.split(" ;;a;  b; ;c;", ';', true, true, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));
  }

  @Test
  public void testSplitByChar_String() {
    List<String> result = null;

    result = StringUtils.split(null, ".", true, true, result);
    assertNull(result);


    result = StringUtils.split("", ".", true, true, result);
    assertNull(result);

    result = StringUtils.split("", "", true, true, result);
    assertNull(result);

    result = StringUtils.split("", (String)null, true, true, result);
    assertNull(result);

    result = StringUtils.split("", ".", true, false, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("", result.get(0));

    result = StringUtils.split("", (String)null, false, false, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("", result.get(0));

    result = StringUtils.split(null, (String)null, true, true, result);
    assertNotNull(result);
    assertEquals(0, result.size());


    result = StringUtils.split("abc", "", true, true, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("abc", result.get(0));

    result = StringUtils.split("a ;b.c:: ;", ":.,;", false, false, result);
    assertNotNull(result);
    assertEquals(6, result.size());
    assertEquals("a ", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));
    assertEquals("", result.get(3));
    assertEquals(" ", result.get(4));
    assertEquals("", result.get(5));

    result = StringUtils.split("a ;b.c:: ;", ":.,;", true, false, result);
    assertNotNull(result);
    assertEquals(6, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));
    assertEquals("", result.get(3));
    assertEquals("", result.get(4));
    assertEquals("", result.get(5));


    result = StringUtils.split("a ;b.c:: ;", ":.,;", false, true, result);
    assertNotNull(result);
    assertEquals(4, result.size());
    assertEquals("a ", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));
    assertEquals(" ", result.get(3));


    result = StringUtils.split("a ;b.c:: ;", ":.,;", true, true, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));

    result = StringUtils.split(";", ":.,;", true, false, result);
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("", result.get(0));
    assertEquals("", result.get(1));


    result = StringUtils.split(";", ":.,;", true, true, result);
    assertNotNull(result);
    assertEquals(0, result.size());

  }

  @Test
  public void testSplitByChar_Array() {
    List<String> result = null;

    result = StringUtils.split(null, new char[]{','}, true, true, result);
    assertNull(result);


    result = StringUtils.split("", new char[]{','}, true, true, result);
    assertNull(result);

    result = StringUtils.split("", new char[]{}, true, true, result);
    assertNull(result);

    result = StringUtils.split("", (char[])null, true, true, result);
    assertNull(result);

    result = StringUtils.split("", new char[]{','}, true, false, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("", result.get(0));

    result = StringUtils.split("", (char[])null, false, false, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("", result.get(0));

    result = StringUtils.split(null, (char[])null, true, true, result);
    assertNotNull(result);
    assertEquals(0, result.size());


    result = StringUtils.split("abc", new char[]{}, true, true, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("abc", result.get(0));

    result = StringUtils.split("a ;b.c:: ;", new char[]{':', '.', ',', ';'}, false, false, result);
    assertNotNull(result);
    assertEquals(6, result.size());
    assertEquals("a ", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));
    assertEquals("", result.get(3));
    assertEquals(" ", result.get(4));
    assertEquals("", result.get(5));

    result = StringUtils.split("a ;b.c:: ;", new char[]{':', '.', ',', ';'}, true, false, result);
    assertNotNull(result);
    assertEquals(6, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));
    assertEquals("", result.get(3));
    assertEquals("", result.get(4));
    assertEquals("", result.get(5));


    result = StringUtils.split("a ;b.c:: ;", new char[]{':', '.', ',', ';'}, false, true, result);
    assertNotNull(result);
    assertEquals(4, result.size());
    assertEquals("a ", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));
    assertEquals(" ", result.get(3));


    result = StringUtils.split("a ;b.c:: ;", new char[]{':', '.', ',', ';'}, true, true, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));

    result = StringUtils.split(";", new char[]{':', '.', ',', ';'}, true, false, result);
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("", result.get(0));
    assertEquals("", result.get(1));


    result = StringUtils.split(";", new char[]{':', '.', ',', ';'}, true, true, result);
    assertNotNull(result);
    assertEquals(0, result.size());

  }

  @Test
  public void testSplitBySubstring() {
    List<String> result = null;

    result = StringUtils.splitByString(null, ".", false, true, result);
    assertNull(result);
    result = StringUtils.splitByString(null, "", false, false, result);
    assertNull(result);
    result = StringUtils.splitByString(null, null, false, false, result);
    assertNull(result);

    result = StringUtils.splitByString("", ".", false, false, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("", result.get(0));

    result = StringUtils.splitByString(null, ".", true, true, result);
    assertNotNull(result);
    assertEquals(0, result.size());

    result = StringUtils.splitByString("", ".", true, false, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("", result.get(0));

    result = StringUtils.splitByString("", ".", false, true, result);
    assertNotNull(result);
    assertEquals(0, result.size());

    result = StringUtils.splitByString("a.b.. c  ", null, false, false, result);
    assertNotNull(result);
    assertEquals(9, result.size());
    assertEquals("a", result.get(0));
    assertEquals(".", result.get(1));
    assertEquals("b", result.get(2));
    assertEquals(".", result.get(3));
    assertEquals(".", result.get(4));
    assertEquals(" ", result.get(5));
    assertEquals("c", result.get(6));
    assertEquals(" ", result.get(7));
    assertEquals(" ", result.get(8));

    result = StringUtils.splitByString("a.b.. c  ", null, false, true, result);
    assertNotNull(result);
    assertEquals(9, result.size());
    assertEquals("a", result.get(0));
    assertEquals(".", result.get(1));
    assertEquals("b", result.get(2));
    assertEquals(".", result.get(3));
    assertEquals(".", result.get(4));
    assertEquals(" ", result.get(5));
    assertEquals("c", result.get(6));
    assertEquals(" ", result.get(7));
    assertEquals(" ", result.get(8));

    result = StringUtils.splitByString("a.b.. c  ", null, true, false, result);
    assertNotNull(result);
    assertEquals(9, result.size());
    assertEquals("a", result.get(0));
    assertEquals(".", result.get(1));
    assertEquals("b", result.get(2));
    assertEquals(".", result.get(3));
    assertEquals(".", result.get(4));
    assertEquals("", result.get(5));
    assertEquals("c", result.get(6));
    assertEquals("", result.get(7));
    assertEquals("", result.get(8));


    result = StringUtils.splitByString("a.b.. c  ", null, true, true, result);
    assertNotNull(result);
    assertEquals(6, result.size());
    assertEquals("a", result.get(0));
    assertEquals(".", result.get(1));
    assertEquals("b", result.get(2));
    assertEquals(".", result.get(3));
    assertEquals(".", result.get(4));
    assertEquals("c", result.get(5));

    result = StringUtils.splitByString("a.b.. c  ", "", false, false, result);
    assertNotNull(result);
    assertEquals(9, result.size());
    assertEquals("a", result.get(0));
    assertEquals(".", result.get(1));
    assertEquals("b", result.get(2));
    assertEquals(".", result.get(3));
    assertEquals(".", result.get(4));
    assertEquals(" ", result.get(5));
    assertEquals("c", result.get(6));
    assertEquals(" ", result.get(7));
    assertEquals(" ", result.get(8));

    result = StringUtils.splitByString("a.b.. c  ", "", false, true, result);
    assertNotNull(result);
    assertEquals(9, result.size());
    assertEquals("a", result.get(0));
    assertEquals(".", result.get(1));
    assertEquals("b", result.get(2));
    assertEquals(".", result.get(3));
    assertEquals(".", result.get(4));
    assertEquals(" ", result.get(5));
    assertEquals("c", result.get(6));
    assertEquals(" ", result.get(7));
    assertEquals(" ", result.get(8));

    result = StringUtils.splitByString("a.b.. c  ", "", true, false, result);
    assertNotNull(result);
    assertEquals(9, result.size());
    assertEquals("a", result.get(0));
    assertEquals(".", result.get(1));
    assertEquals("b", result.get(2));
    assertEquals(".", result.get(3));
    assertEquals(".", result.get(4));
    assertEquals("", result.get(5));
    assertEquals("c", result.get(6));
    assertEquals("", result.get(7));
    assertEquals("", result.get(8));


    result = StringUtils.splitByString("a.b.. c  ", "", true, true, result);
    assertNotNull(result);
    assertEquals(6, result.size());
    assertEquals("a", result.get(0));
    assertEquals(".", result.get(1));
    assertEquals("b", result.get(2));
    assertEquals(".", result.get(3));
    assertEquals(".", result.get(4));
    assertEquals("c", result.get(5));


    result = StringUtils.splitByString("a", null, false, false, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("a", result.get(0));

    result = StringUtils.splitByString("a", "", false, false, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("a", result.get(0));

    result = StringUtils.splitByString("", null, false, false, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("", result.get(0));

    result = StringUtils.splitByString("", "", false, false, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("", result.get(0));

    result = StringUtils.splitByString("", null, false, true, result);
    assertNotNull(result);
    assertEquals(0, result.size());

    result = StringUtils.splitByString("", "", false, true, result);
    assertNotNull(result);
    assertEquals(0, result.size());

    result = StringUtils.splitByString("a.b.. c  ", ".", false, false, result);
    assertNotNull(result);
    assertEquals(4, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("", result.get(2));
    assertEquals(" c  ", result.get(3));

    result = StringUtils.splitByString(null, ".", true, false, result);
    assertNotNull(result);
    assertEquals(0, result.size());

    result = StringUtils.splitByString("a.b.. c  ", ".", false, true, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals(" c  ", result.get(2));

    result = StringUtils.splitByString("", ".", true, true, result);
    assertNotNull(result);
    assertEquals(0, result.size());

    result = StringUtils.splitByString("a.b.. c  ", ".", true, false, result);
    assertNotNull(result);
    assertEquals(4, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("", result.get(2));
    assertEquals("c", result.get(3));


    result = StringUtils.splitByString("a.b.. c  ", ".", true, true, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));

    result = StringUtils.splitByString(". a .", ".", false, false, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("", result.get(0));
    assertEquals(" a ", result.get(1));
    assertEquals("", result.get(2));

    result = StringUtils.splitByString(". a .", ".", false, true, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(" a ", result.get(0));

    result = StringUtils.splitByString(". a .", ".", true, false, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("", result.get(0));
    assertEquals("a", result.get(1));
    assertEquals("", result.get(2));

    result = StringUtils.splitByString(". a .", ".", true, true, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("a", result.get(0));


    result = StringUtils.splitByString(" a  b c", " ", false, false, result);
    assertNotNull(result);
    assertEquals(5, result.size());
    assertEquals("", result.get(0));
    assertEquals("a", result.get(1));
    assertEquals("", result.get(2));
    assertEquals("b", result.get(3));
    assertEquals("c", result.get(4));

    result = StringUtils.splitByString(" a  b c", " ", false, true, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));

    result = StringUtils.splitByString(" a  b c", " ", true, false, result);
    assertNotNull(result);
    assertEquals(5, result.size());
    assertEquals("", result.get(0));
    assertEquals("a", result.get(1));
    assertEquals("", result.get(2));
    assertEquals("b", result.get(3));
    assertEquals("c", result.get(4));

    result = StringUtils.splitByString(" a  b c", " ", true, true, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));

    result = StringUtils.splitByString(" ;;a;  b; ;c;", ";", false, false, result);
    assertNotNull(result);
    assertEquals(7, result.size());
    assertEquals(" ", result.get(0));
    assertEquals("", result.get(1));
    assertEquals("a", result.get(2));
    assertEquals("  b", result.get(3));
    assertEquals(" ", result.get(4));
    assertEquals("c", result.get(5));
    assertEquals("", result.get(6));

    result = StringUtils.splitByString(" ;;a;  b; ;c;", ";", false, true, result);
    assertNotNull(result);
    assertEquals(5, result.size());
    assertEquals(" ", result.get(0));
    assertEquals("a", result.get(1));
    assertEquals("  b", result.get(2));
    assertEquals(" ", result.get(3));
    assertEquals("c", result.get(4));

    result = StringUtils.splitByString(" ;;a;  b; ;c;", ";", true, false, result);
    assertNotNull(result);
    assertEquals(7, result.size());
    assertEquals("", result.get(0));
    assertEquals("", result.get(1));
    assertEquals("a", result.get(2));
    assertEquals("b", result.get(3));
    assertEquals("", result.get(4));
    assertEquals("c", result.get(5));
    assertEquals("", result.get(6));

    result = StringUtils.splitByString(" ;;a;  b; ;c;", ";", true, true, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));

    result = StringUtils.splitByString(" ;;a;  b; ;c;", ";;", true, false, result);
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("", result.get(0));
    assertEquals("a;  b; ;c;", result.get(1));


    result = StringUtils.splitByString(";;;;;;;;;", ";;", true, false, result);
    assertNotNull(result);
    assertEquals(5, result.size());
    assertEquals("", result.get(0));
    assertEquals("", result.get(1));
    assertEquals("", result.get(2));
    assertEquals("", result.get(3));
    assertEquals(";", result.get(4));


    result = StringUtils.splitByString(";;;;;;;;;", ";;", true, true, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(";", result.get(0));

    result = StringUtils.splitByString(";;;;;;;;;", ";;;", true, false, result);
    assertNotNull(result);
    assertEquals(4, result.size());
    assertEquals("", result.get(0));
    assertEquals("", result.get(1));
    assertEquals("", result.get(2));
    assertEquals("", result.get(3));

    result = StringUtils.splitByString(";;;;;;;;;", ";;;", true, true, result);
    assertNotNull(result);
    assertEquals(0, result.size());
  }

  @Test
  public void testSplitByCharType() {
    List<String> result = null;

    result = StringUtils.splitByCharType(null, false, false, false, result);
    assertNull(result);

    result = StringUtils.splitByCharType("", false, false, true, result);
    assertNull(result);

    result = StringUtils.splitByCharType("", false, false, false, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("", result.get(0));

    result = StringUtils.splitByCharType("ab de fg", false, false, false, result);
    assertNotNull(result);
    assertEquals(5, result.size());
    assertEquals("ab", result.get(0));
    assertEquals(" ", result.get(1));
    assertEquals("de", result.get(2));
    assertEquals(" ", result.get(3));
    assertEquals("fg", result.get(4));

    result = StringUtils.splitByCharType("ab de fg", false, false, true, result);
    assertNotNull(result);
    assertEquals(5, result.size());
    assertEquals("ab", result.get(0));
    assertEquals(" ", result.get(1));
    assertEquals("de", result.get(2));
    assertEquals(" ", result.get(3));
    assertEquals("fg", result.get(4));

    result = StringUtils.splitByCharType("ab de fg", false, true, false, result);
    assertNotNull(result);
    assertEquals(5, result.size());
    assertEquals("ab", result.get(0));
    assertEquals("", result.get(1));
    assertEquals("de", result.get(2));
    assertEquals("", result.get(3));
    assertEquals("fg", result.get(4));

    result = StringUtils.splitByCharType("ab de fg", false, true, true, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("ab", result.get(0));
    assertEquals("de", result.get(1));
    assertEquals("fg", result.get(2));

    result = StringUtils.splitByCharType("ab   de fg", false, false, false, result);
    assertNotNull(result);
    assertEquals(5, result.size());
    assertEquals("ab", result.get(0));
    assertEquals("   ", result.get(1));
    assertEquals("de", result.get(2));
    assertEquals(" ", result.get(3));
    assertEquals("fg", result.get(4));

    result = StringUtils.splitByCharType("ab:de:fg", false, false, false, result);
    assertNotNull(result);
    assertEquals(5, result.size());
    assertEquals("ab", result.get(0));
    assertEquals(":", result.get(1));
    assertEquals("de", result.get(2));
    assertEquals(":", result.get(3));
    assertEquals("fg", result.get(4));

    result = StringUtils.splitByCharType("number5", false, false, false, result);
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("number", result.get(0));
    assertEquals("5", result.get(1));

    result = StringUtils.splitByCharType("fooBar", false, false, false, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("foo", result.get(0));
    assertEquals("B", result.get(1));
    assertEquals("ar", result.get(2));

    result = StringUtils.splitByCharType("foo2000Bar", false, false, false, result);
    assertNotNull(result);
    assertEquals(4, result.size());
    assertEquals("foo", result.get(0));
    assertEquals("2000", result.get(1));
    assertEquals("B", result.get(2));
    assertEquals("ar", result.get(3));

    result = StringUtils.splitByCharType("ASFRules", false, false, false, result);
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("ASFR", result.get(0));
    assertEquals("ules", result.get(1));


    result = StringUtils.splitByCharType("fooBar", true, false, false, result);
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("foo", result.get(0));
    assertEquals("Bar", result.get(1));

    result = StringUtils.splitByCharType("foo2000Bar", true, false, false, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("foo", result.get(0));
    assertEquals("2000", result.get(1));
    assertEquals("Bar", result.get(2));

    result = StringUtils.splitByCharType("ASFRules", true, false, false, result);
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("ASF", result.get(0));
    assertEquals("Rules", result.get(1));

  }
}
