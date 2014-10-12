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

import com.github.haixing_hu.lang.StringUtils;

import static org.junit.Assert.*;

/**
 * Unit test for the Strings class.
 *
 * @author Haixing Hu
 */
public class StringUtilsTest extends StringUtilsTestBase {

  @Test
  public void testCaseFunctions() {
    assertNull(StringUtils.toUpperCase(null));
    assertNull(StringUtils.toLowerCase(null));
    assertNull(StringUtils.capitalize(null));
    assertNull(StringUtils.uncapitalize(null));

    assertEquals("capitalize(String) failed",
                 FOO_CAP, StringUtils.capitalize(FOO_UNCAP) );
    assertEquals("capitalize(empty-string) failed",
                 "", StringUtils.capitalize("") );
    assertEquals("capitalize(single-char-string) failed",
                 "X", StringUtils.capitalize("x") );
    assertEquals("uncapitalize(String) failed",
                 FOO_UNCAP, StringUtils.uncapitalize(FOO_CAP) );
    assertEquals("uncapitalize(empty-string) failed",
                 "", StringUtils.uncapitalize("") );
    assertEquals("uncapitalize(single-char-string) failed",
                 "x", StringUtils.uncapitalize("X") );

    assertEquals("uncapitalize(capitalize(String)) failed",
                 SENTENCE_UNCAP, StringUtils.uncapitalize(StringUtils.capitalize(SENTENCE_UNCAP)) );
    assertEquals("capitalize(uncapitalize(String)) failed",
                 SENTENCE_CAP, StringUtils.capitalize(StringUtils.uncapitalize(SENTENCE_CAP)) );

    assertEquals("uncapitalize(capitalize(String)) failed",
                 FOO_UNCAP, StringUtils.uncapitalize(StringUtils.capitalize(FOO_UNCAP)) );
    assertEquals("capitalize(uncapitalize(String)) failed",
                 FOO_CAP, StringUtils.capitalize(StringUtils.uncapitalize(FOO_CAP)) );

    assertEquals("upperCase(String) failed",
                 "FOO TEST THING", StringUtils.toUpperCase("fOo test THING") );
    assertEquals("upperCase(empty-string) failed",
                 "", StringUtils.toUpperCase("") );
    assertEquals("lowerCase(String) failed",
                 "foo test thing", StringUtils.toLowerCase("fOo test THING") );
    assertEquals("lowerCase(empty-string) failed",
                 "", StringUtils.toLowerCase("") );
  }

  @Test
  public void testSwapCase_String() {
    assertNull(StringUtils.swapCase(null));
    assertEquals("", StringUtils.swapCase(""));
    assertEquals("  ", StringUtils.swapCase("  "));

    assertEquals("i", StringUtils.swapCase("I") );
    assertEquals("I", StringUtils.swapCase("i") );
    assertEquals("I AM HERE 123", StringUtils.swapCase("i am here 123") );
    assertEquals("i aM hERE 123", StringUtils.swapCase("I Am Here 123") );
    assertEquals("I AM here 123", StringUtils.swapCase("i am HERE 123") );
    assertEquals("i am here 123", StringUtils.swapCase("I AM HERE 123") );

    final String test = "This String contains a TitleCase character: \u01C8";
    final String expect = "tHIS sTRING CONTAINS A tITLEcASE CHARACTER: \u01C9";
    assertEquals(expect, StringUtils.swapCase(test));
  }

  @Test
  public void testReplace() {
    assertNull(StringUtils.replace(null, null, null, -1, false));
    assertNull(StringUtils.replace(null, null, "any", -1, false));
    assertNull(StringUtils.replace(null, "any", null, -1, false));
    assertNull(StringUtils.replace(null, "any", "any", -1, false));

    assertNull(StringUtils.replace(null, null, null, 2, false));
    assertNull(StringUtils.replace(null, null, "any", 2, false));
    assertNull(StringUtils.replace(null, "any", null, 2, false));
    assertNull(StringUtils.replace(null, "any", "any", 2, false));

    assertEquals("", StringUtils.replace("", null, null, -1, false));
    assertEquals("", StringUtils.replace("", null, "any", -1, false));
    assertEquals("", StringUtils.replace("", "any", null, -1, false));
    assertEquals("", StringUtils.replace("", "any", "any", -1, false));

    assertEquals("FOO", StringUtils.replace("FOO", "", "any", -1, false));
    assertEquals("FOO", StringUtils.replace("FOO", null, "any", -1, false));
    assertEquals("FOO", StringUtils.replace("FOO", "F", null, -1, false));
    assertEquals("FOO", StringUtils.replace("FOO", null, null, -1, false));

    assertEquals("", StringUtils.replace("foofoofoo", "foo", "", -1, false));
    assertEquals("barbarbar", StringUtils.replace("foofoofoo", "foo", "bar", -1, false));
    assertEquals("farfarfar", StringUtils.replace("foofoofoo", "oo", "ar", -1, false));

    assertEquals("", StringUtils.replace("", null, null, 2, false));
    assertEquals("", StringUtils.replace("", null, "any", 2, false));
    assertEquals("", StringUtils.replace("", "any", null, 2, false));
    assertEquals("", StringUtils.replace("", "any", "any", 2, false));

    final String str = new String(new char[] {'o', 'o', 'f', 'o', 'o'});
    assertSame(str, StringUtils.replace(str, "x", "", -1, false));

    assertEquals("f", StringUtils.replace("oofoo", "o", "", -1, false));
    assertEquals("oofoo", StringUtils.replace("oofoo", "o", "", 0, false));
    assertEquals("ofoo", StringUtils.replace("oofoo", "o", "", 1, false));
    assertEquals("foo", StringUtils.replace("oofoo", "o", "", 2, false));
    assertEquals("fo", StringUtils.replace("oofoo", "o", "", 3, false));
    assertEquals("f", StringUtils.replace("oofoo", "o", "", 4, false));

    assertEquals("f", StringUtils.replace("oofoo", "o", "", -5, false));
    assertEquals("f", StringUtils.replace("oofoo", "o", "", 1000, false));

    assertNull(StringUtils.replace(null, null, null, 1, false));
    assertNull(StringUtils.replace(null, null, "any", 1, false));
    assertNull(StringUtils.replace(null, "any", null, 1, false));
    assertNull(StringUtils.replace(null, "any", "any", 1, false));

    assertEquals("", StringUtils.replace("", null, null, 1, false));
    assertEquals("", StringUtils.replace("", null, "any", 1, false));
    assertEquals("", StringUtils.replace("", "any", null, 1, false));
    assertEquals("", StringUtils.replace("", "any", "any", 1, false));

    assertEquals("FOO", StringUtils.replace("FOO", "", "any", 1, false));
    assertEquals("FOO", StringUtils.replace("FOO", null, "any", 1, false));
    assertEquals("FOO", StringUtils.replace("FOO", "F", null, 1, false));
    assertEquals("FOO", StringUtils.replace("FOO", null, null, 1, false));

    assertEquals("foofoo", StringUtils.replace("foofoofoo", "foo", "", 1, false));
  }

  @Test
  public void testReplaceEach() {
    //JAVADOC TESTS START
    assertNull(StringUtils.replaceEach(null, new String[]{"a"}, new String[]{"b"}, false));
    assertEquals("", StringUtils.replaceEach("", new String[]{"a"}, new String[]{"b"}, false));
    assertEquals("aba", StringUtils.replaceEach("aba", null, null, false));
    assertEquals("aba", StringUtils.replaceEach("aba", new String[0], null, false));
    assertEquals("aba", StringUtils.replaceEach("aba", null, new String[0], false));
    assertEquals("aba", StringUtils.replaceEach("aba", new String[]{"a"}, null, false));

    assertEquals("b", StringUtils.replaceEach("aba", new String[]{"a"}, new String[]{""}, false));
    assertEquals("aba", StringUtils.replaceEach("aba", new String[]{null}, new String[]{"a"}, false));
    assertEquals("wcte", StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"}, false));
    assertEquals("dcte", StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}, false));
    //JAVADOC TESTS END

    assertEquals("bcc", StringUtils.replaceEach("abc", new String[]{"a", "b"}, new String[]{"b", "c"}, false));
    assertEquals("q651.506bera", StringUtils.replaceEach("d216.102oren",
        new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D",
            "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9"},
        new String[]{"n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "a",
            "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "5", "6", "7", "8", "9", "1", "2", "3", "4"},
            false));
  }

  @Test
  public void testReplaceEachRepeatedly() {
    //JAVADOC TESTS START
    assertNull(StringUtils.replaceEachRepeatedly(null, new String[]{"a"}, new String[]{"b"}, false));
    assertEquals("", StringUtils.replaceEachRepeatedly("", new String[]{"a"}, new String[]{"b"}, false));
    assertEquals("aba", StringUtils.replaceEachRepeatedly("aba", null, null, false));
    assertEquals("aba", StringUtils.replaceEachRepeatedly("aba", new String[0], null, false));
    assertEquals("aba", StringUtils.replaceEachRepeatedly("aba", null, new String[0], false));
    assertEquals("aba", StringUtils.replaceEachRepeatedly("aba", new String[0], null, false));

    assertEquals("b", StringUtils.replaceEachRepeatedly("aba", new String[]{"a"}, new String[]{""}, false));
    assertEquals("aba", StringUtils.replaceEachRepeatedly("aba", new String[]{null}, new String[]{"a"}, false));
    assertEquals("wcte", StringUtils.replaceEachRepeatedly("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"}, false));
    assertEquals("tcte", StringUtils.replaceEachRepeatedly("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}, false));

    try {
      StringUtils.replaceEachRepeatedly("abcde",
          new String[]{"ab", "d"},
          new String[]{"d", "ab"},
          false);
      fail("Should be a circular reference");
    } catch (final IllegalStateException e) {
      // pass
    }

    //JAVADOC TESTS END

  }

  @Test
  public void testReplaceChar() {
    assertNull(StringUtils.replaceChar(null, 'b', 'z'));
    assertEquals("", StringUtils.replaceChar("", 'b', 'z'));
    assertEquals("azcza", StringUtils.replaceChar("abcba", 'b', 'z'));
    assertEquals("abcba", StringUtils.replaceChar("abcba", 'x', 'z'));
  }

  @Test
  public void testReplaceChars() {
    assertNull(StringUtils.replaceChars(null, null, null));
    assertNull(StringUtils.replaceChars(null, "", null));
    assertNull(StringUtils.replaceChars(null, "a", null));
    assertNull(StringUtils.replaceChars(null, null, ""));
    assertNull(StringUtils.replaceChars(null, null, "x"));

    assertEquals("", StringUtils.replaceChars("", null, null));
    assertEquals("", StringUtils.replaceChars("", "", null));
    assertEquals("", StringUtils.replaceChars("", "a", null));
    assertEquals("", StringUtils.replaceChars("", null, ""));
    assertEquals("", StringUtils.replaceChars("", null, "x"));

    assertEquals("abc", StringUtils.replaceChars("abc", null, null));
    assertEquals("abc", StringUtils.replaceChars("abc", null, ""));
    assertEquals("abc", StringUtils.replaceChars("abc", null, "x"));

    assertEquals("abc", StringUtils.replaceChars("abc", "", null));
    assertEquals("abc", StringUtils.replaceChars("abc", "", ""));
    assertEquals("abc", StringUtils.replaceChars("abc", "", "x"));

    assertEquals("ac", StringUtils.replaceChars("abc", "b", null));
    assertEquals("ac", StringUtils.replaceChars("abc", "b", ""));
    assertEquals("axc", StringUtils.replaceChars("abc", "b", "x"));

    assertEquals("ayzya", StringUtils.replaceChars("abcba", "bc", "yz"));
    assertEquals("ayya", StringUtils.replaceChars("abcba", "bc", "y"));
    assertEquals("ayzya", StringUtils.replaceChars("abcba", "bc", "yzx"));

    assertEquals("abcba", StringUtils.replaceChars("abcba", "z", "w"));
    assertSame("abcba", StringUtils.replaceChars("abcba", "z", "w"));

    // Javadoc examples:
    assertEquals("jelly", StringUtils.replaceChars("hello", "ho", "jy"));
    assertEquals("ayzya", StringUtils.replaceChars("abcba", "bc", "yz"));
    assertEquals("ayya", StringUtils.replaceChars("abcba", "bc", "y"));
    assertEquals("ayzya", StringUtils.replaceChars("abcba", "bc", "yzx"));

    // From http://issues.apache.org/bugzilla/show_bug.cgi?id=25454
    assertEquals("bcc", StringUtils.replaceChars("abc", "ab", "bc"));
    assertEquals("q651.506bera", StringUtils.replaceChars("d216.102oren",
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789",
        "nopqrstuvwxyzabcdefghijklmNOPQRSTUVWXYZABCDEFGHIJKLM567891234"));
  }

  @Test
  public void testOverlay() {
    assertNull(StringUtils.overlay(null, null, 2, 4));
    assertNull(StringUtils.overlay(null, null, -2, -4));

    assertEquals("", StringUtils.overlay("", null, 0, 0));
    assertEquals("", StringUtils.overlay("", "", 0, 0));
    assertEquals("zzzz", StringUtils.overlay("", "zzzz", 0, 0));
    assertEquals("zzzz", StringUtils.overlay("", "zzzz", 2, 4));
    assertEquals("zzzz", StringUtils.overlay("", "zzzz", -2, -4));

    assertEquals("abef", StringUtils.overlay("abcdef", null, 2, 4));
    assertEquals("abef", StringUtils.overlay("abcdef", null, 4, 2));
    assertEquals("abef", StringUtils.overlay("abcdef", "", 2, 4));
    assertEquals("abef", StringUtils.overlay("abcdef", "", 4, 2));
    assertEquals("abzzzzef", StringUtils.overlay("abcdef", "zzzz", 2, 4));
    assertEquals("abzzzzef", StringUtils.overlay("abcdef", "zzzz", 4, 2));

    assertEquals("zzzzef", StringUtils.overlay("abcdef", "zzzz", -1, 4));
    assertEquals("zzzzef", StringUtils.overlay("abcdef", "zzzz", 4, -1));
    assertEquals("zzzzabcdef", StringUtils.overlay("abcdef", "zzzz", -2, -1));
    assertEquals("zzzzabcdef", StringUtils.overlay("abcdef", "zzzz", -1, -2));
    assertEquals("abcdzzzz", StringUtils.overlay("abcdef", "zzzz", 4, 10));
    assertEquals("abcdzzzz", StringUtils.overlay("abcdef", "zzzz", 10, 4));
    assertEquals("abcdefzzzz", StringUtils.overlay("abcdef", "zzzz", 8, 10));
    assertEquals("abcdefzzzz", StringUtils.overlay("abcdef", "zzzz", 10, 8));
  }

  @Test
  public void testRepeat_StringInt() {
    assertNull(StringUtils.repeat(null, 2));
    assertEquals("", StringUtils.repeat("ab", 0));
    assertEquals("", StringUtils.repeat("", 3));
    assertEquals("aaa", StringUtils.repeat("a", 3));
    assertEquals("ababab", StringUtils.repeat("ab", 3));
    assertEquals("abcabcabc", StringUtils.repeat("abc", 3));
    final String str = StringUtils.repeat("a", 10000);  // bigger than pad limit
    assertEquals(10000, str.length());
    assertEquals(true, StringUtils.containsOnly(str, new char[] {'a'}));
  }

  @Test
  public void testRightPad_StringIntChar() {
    assertNull(StringUtils.rightPad(null, 5, ' '));
    assertEquals("     ", StringUtils.rightPad("", 5, ' '));
    assertEquals("abc  ", StringUtils.rightPad("abc", 5, ' '));
    assertEquals("abc", StringUtils.rightPad("abc", 2, ' '));
    assertEquals("abc", StringUtils.rightPad("abc", -1, ' '));
    assertEquals("abcxx", StringUtils.rightPad("abc", 5, 'x'));
    final String str = StringUtils.rightPad("aaa", 10000, 'a');  // bigger than pad length
    assertEquals(10000, str.length());
    assertEquals(true, StringUtils.containsOnly(str, new char[] {'a'}));
  }

  @Test
  public void testRightPad_StringIntString() {
    assertNull(StringUtils.rightPad(null, 5, "-+"));
    assertEquals("     ", StringUtils.rightPad("", 5, " "));
    assertNull(StringUtils.rightPad(null, 8, null));
    assertEquals("abc-+-+", StringUtils.rightPad("abc", 7, "-+"));
    assertEquals("abc-+~", StringUtils.rightPad("abc", 6, "-+~"));
    assertEquals("abc-+", StringUtils.rightPad("abc", 5, "-+~"));
    assertEquals("abc", StringUtils.rightPad("abc", 2, " "));
    assertEquals("abc", StringUtils.rightPad("abc", -1, " "));
    assertEquals("abc  ", StringUtils.rightPad("abc", 5, null));
    assertEquals("abc  ", StringUtils.rightPad("abc", 5, ""));
  }

  @Test
  public void testLeftPad_StringIntChar() {
    assertNull(StringUtils.leftPad(null, 5, ' '));
    assertEquals("     ", StringUtils.leftPad("", 5, ' '));
    assertEquals("  abc", StringUtils.leftPad("abc", 5, ' '));
    assertEquals("xxabc", StringUtils.leftPad("abc", 5, 'x'));
    assertEquals("\uffff\uffffabc", StringUtils.leftPad("abc", 5, '\uffff'));
    assertEquals("abc", StringUtils.leftPad("abc", 2, ' '));
    final String str = StringUtils.leftPad("aaa", 10000, 'a');  // bigger than pad length
    assertEquals(10000, str.length());
    assertEquals(true, StringUtils.containsOnly(str, new char[] {'a'}));
  }

  @Test
  public void testLeftPad_StringIntString() {
    assertNull(StringUtils.leftPad(null, 5, "-+"));
    assertNull(StringUtils.leftPad(null, 5, null));
    assertEquals("     ", StringUtils.leftPad("", 5, " "));
    assertEquals("-+-+abc", StringUtils.leftPad("abc", 7, "-+"));
    assertEquals("-+~abc", StringUtils.leftPad("abc", 6, "-+~"));
    assertEquals("-+abc", StringUtils.leftPad("abc", 5, "-+~"));
    assertEquals("abc", StringUtils.leftPad("abc", 2, " "));
    assertEquals("abc", StringUtils.leftPad("abc", -1, " "));
    assertEquals("  abc", StringUtils.leftPad("abc", 5, null));
    assertEquals("  abc", StringUtils.leftPad("abc", 5, ""));
  }

  @Test
  public void testCenter_StringIntChar() {
    assertNull(StringUtils.center(null, -1, ' '));
    assertNull(StringUtils.center(null, 4, ' '));
    assertEquals("    ", StringUtils.center("", 4, ' '));
    assertEquals("ab", StringUtils.center("ab", 0, ' '));
    assertEquals("ab", StringUtils.center("ab", -1, ' '));
    assertEquals("ab", StringUtils.center("ab", 1, ' '));
    assertEquals("    ", StringUtils.center("", 4, ' '));
    assertEquals(" ab ", StringUtils.center("ab", 4, ' '));
    assertEquals("abcd", StringUtils.center("abcd", 2, ' '));
    assertEquals(" a  ", StringUtils.center("a", 4, ' '));
    assertEquals("  a  ", StringUtils.center("a", 5, ' '));
    assertEquals("xxaxx", StringUtils.center("a", 5, 'x'));
  }

  @Test
  public void testCenter_StringIntString() {
    assertNull(StringUtils.center(null, 4, null));
    assertNull(StringUtils.center(null, -1, " "));
    assertNull(StringUtils.center(null, 4, " "));
    assertEquals("    ", StringUtils.center("", 4, " "));
    assertEquals("ab", StringUtils.center("ab", 0, " "));
    assertEquals("ab", StringUtils.center("ab", -1, " "));
    assertEquals("ab", StringUtils.center("ab", 1, " "));
    assertEquals("    ", StringUtils.center("", 4, " "));
    assertEquals(" ab ", StringUtils.center("ab", 4, " "));
    assertEquals("abcd", StringUtils.center("abcd", 2, " "));
    assertEquals(" a  ", StringUtils.center("a", 4, " "));
    assertEquals("yayz", StringUtils.center("a", 4, "yz"));
    assertEquals("yzyayzy", StringUtils.center("a", 7, "yz"));
    assertEquals("  abc  ", StringUtils.center("abc", 7, null));
    assertEquals("  abc  ", StringUtils.center("abc", 7, ""));
  }

  @Test
  public void testReverse() {
    assertNull(StringUtils.reverse(null) );
    assertEquals("", StringUtils.reverse("") );
    assertEquals("sdrawkcab", StringUtils.reverse("backwards") );
  }

  @Test
  public void testAbbreviate() {
    assertNull(StringUtils.abbreviate(null, 10, 12));
    assertEquals("", StringUtils.abbreviate("", 0, 10));
    assertEquals("", StringUtils.abbreviate("", 2, 10));

    try {
        @SuppressWarnings("unused")
        final
        String res = StringUtils.abbreviate("abcdefghij", 0, 3);
        fail("Strings.abbreviate expecting IllegalArgumentException");
    } catch (final IllegalArgumentException ex) {
            // empty
    }
    try {
        @SuppressWarnings("unused")
        final
        String res = StringUtils.abbreviate("abcdefghij", 5, 6);
        fail("Strings.abbreviate expecting IllegalArgumentException");
    } catch (final IllegalArgumentException ex) {
            // empty
    }


    final String raspberry = "raspberry peach";
    assertEquals("raspberry peach", StringUtils.abbreviate(raspberry, 11, 15));

    assertNull(StringUtils.abbreviate(null, 7, 14));
    assertAbbreviateWithOffset("abcdefg...", -1, 10);
    assertAbbreviateWithOffset("abcdefg...", 0, 10);
    assertAbbreviateWithOffset("abcdefg...", 1, 10);
    assertAbbreviateWithOffset("abcdefg...", 2, 10);
    assertAbbreviateWithOffset("abcdefg...", 3, 10);
    assertAbbreviateWithOffset("abcdefg...", 4, 10);
    assertAbbreviateWithOffset("...fghi...", 5, 10);
    assertAbbreviateWithOffset("...ghij...", 6, 10);
    assertAbbreviateWithOffset("...hijk...", 7, 10);
    assertAbbreviateWithOffset("...ijklmno", 8, 10);
    assertAbbreviateWithOffset("...ijklmno", 9, 10);
    assertAbbreviateWithOffset("...ijklmno", 10, 10);
    assertAbbreviateWithOffset("...ijklmno", 10, 10);
    assertAbbreviateWithOffset("...ijklmno", 11, 10);
    assertAbbreviateWithOffset("...ijklmno", 12, 10);
    assertAbbreviateWithOffset("...ijklmno", 13, 10);
    assertAbbreviateWithOffset("...ijklmno", 14, 10);
    assertAbbreviateWithOffset("...ijklmno", 15, 10);
    assertAbbreviateWithOffset("...ijklmno", 16, 10);
    assertAbbreviateWithOffset("...ijklmno", Integer.MAX_VALUE, 10);
  }

  private void assertAbbreviateWithOffset(final String expected, final int offset, final int maxWidth) {
    final String abcdefghijklmno = "abcdefghijklmno";
    final String message = "abbreviate(String,int,int) failed";
    final String actual = StringUtils.abbreviate(abcdefghijklmno, offset, maxWidth);
    if ((offset >= 0) && (offset < abcdefghijklmno.length())) {
        assertTrue(message + " -- should contain offset character",
                actual.indexOf((char)('a'+offset)) != -1);
    }
    assertTrue(message + " -- should not be greater than maxWidth",
            actual.length() <= maxWidth);
    assertEquals(message, expected, actual);
  }



  @Test
  public void testEMPTY() {
      assertNotNull(StringUtils.EMPTY);
      assertEquals("", StringUtils.EMPTY);
      assertEquals(0, StringUtils.EMPTY.length());
  }



  @Test
  public void testSubstring_StringInt() {
    assertEquals(null, StringUtils.substring(null, 0));
    assertEquals("", StringUtils.substring("", 0));
    assertEquals("", StringUtils.substring("", 2));

    assertEquals("", StringUtils.substring(SENTENCE, 80));
    assertEquals(BAZ, StringUtils.substring(SENTENCE, 8));
    assertEquals(BAZ, StringUtils.substring(SENTENCE, -3));
    assertEquals(SENTENCE, StringUtils.substring(SENTENCE, 0));
    assertEquals("abc", StringUtils.substring("abc", -4));
    assertEquals("abc", StringUtils.substring("abc", -3));
    assertEquals("bc", StringUtils.substring("abc", -2));
    assertEquals("c", StringUtils.substring("abc", -1));
    assertEquals("abc", StringUtils.substring("abc", 0));
    assertEquals("bc", StringUtils.substring("abc", 1));
    assertEquals("c", StringUtils.substring("abc", 2));
    assertEquals("", StringUtils.substring("abc", 3));
    assertEquals("", StringUtils.substring("abc", 4));
  }

  @Test
  public void testSubstring_StringIntInt() {
    assertEquals(null, StringUtils.substring(null, 0, 0));
    assertEquals(null, StringUtils.substring(null, 1, 2));
    assertEquals("", StringUtils.substring("", 0, 0));
    assertEquals("", StringUtils.substring("", 1, 2));
    assertEquals("", StringUtils.substring("", -2, -1));

    assertEquals("", StringUtils.substring(SENTENCE, 8, 6));
    assertEquals(FOO, StringUtils.substring(SENTENCE, 0, 3));
    assertEquals("o", StringUtils.substring(SENTENCE, -9, 3));
    assertEquals(FOO, StringUtils.substring(SENTENCE, 0, -8));
    assertEquals("o", StringUtils.substring(SENTENCE, -9, -8));
    assertEquals(SENTENCE, StringUtils.substring(SENTENCE, 0, 80));
    assertEquals("", StringUtils.substring(SENTENCE, 2, 2));
    assertEquals("b", StringUtils.substring("abc", -2, -1));
  }

  @Test
  public void testLeft() {
    assertSame(null, StringUtils.left(null, -1));
    assertSame(null, StringUtils.left(null, 0));
    assertSame(null, StringUtils.left(null, 2));

    assertEquals("", StringUtils.left("", -1));
    assertEquals("", StringUtils.left("", 0));
    assertEquals("", StringUtils.left("", 2));

    assertEquals("", StringUtils.left(FOOBAR, -1));
    assertEquals("", StringUtils.left(FOOBAR, 0));
    assertEquals(FOO, StringUtils.left(FOOBAR, 3));
    assertSame(FOOBAR, StringUtils.left(FOOBAR, 80));
  }

  @Test
  public void testRight() {
    assertSame(null, StringUtils.right(null, -1));
    assertSame(null, StringUtils.right(null, 0));
    assertSame(null, StringUtils.right(null, 2));

    assertEquals("", StringUtils.right("", -1));
    assertEquals("", StringUtils.right("", 0));
    assertEquals("", StringUtils.right("", 2));

    assertEquals("", StringUtils.right(FOOBAR, -1));
    assertEquals("", StringUtils.right(FOOBAR, 0));
    assertEquals(BAR, StringUtils.right(FOOBAR, 3));
    assertSame(FOOBAR, StringUtils.right(FOOBAR, 80));
  }

  @Test
  public void testMid() {
    assertSame(null, StringUtils.mid(null, -1, 0));
    assertSame(null, StringUtils.mid(null, 0, -1));
    assertSame(null, StringUtils.mid(null, 3, 0));
    assertSame(null, StringUtils.mid(null, 3, 2));

    assertEquals("", StringUtils.mid("", 0, -1));
    assertEquals("", StringUtils.mid("", 0, 0));
    assertEquals("", StringUtils.mid("", 0, 2));

    assertEquals("", StringUtils.mid(FOOBAR, 3, -1));
    assertEquals("", StringUtils.mid(FOOBAR, 3, 0));
    assertEquals("b", StringUtils.mid(FOOBAR, 3, 1));
    assertEquals(FOO, StringUtils.mid(FOOBAR, 0, 3));
    assertEquals(BAR, StringUtils.mid(FOOBAR, 3, 3));
    assertEquals(FOOBAR, StringUtils.mid(FOOBAR, 0, 80));
    assertEquals(BAR, StringUtils.mid(FOOBAR, 3, 80));
    assertEquals("", StringUtils.mid(FOOBAR, 9, 3));
    assertEquals(FOO, StringUtils.mid(FOOBAR, -1, 3));
  }

  @Test
  public void testSubstringBefore_StringString() {
    assertEquals("foo", StringUtils.substringBefore("fooXXbarXXbaz", "XX"));

    assertEquals(null, StringUtils.substringBefore(null, null));
    assertEquals(null, StringUtils.substringBefore(null, ""));
    assertEquals(null, StringUtils.substringBefore(null, "XX"));
    assertEquals("", StringUtils.substringBefore("", null));
    assertEquals("", StringUtils.substringBefore("", ""));
    assertEquals("", StringUtils.substringBefore("", "XX"));

    assertEquals("foo", StringUtils.substringBefore("foo", null));
    assertEquals("foo", StringUtils.substringBefore("foo", "b"));
    assertEquals("f", StringUtils.substringBefore("foot", "o"));
    assertEquals("", StringUtils.substringBefore("abc", "a"));
    assertEquals("a", StringUtils.substringBefore("abcba", "b"));
    assertEquals("ab", StringUtils.substringBefore("abc", "c"));
    assertEquals("", StringUtils.substringBefore("abc", ""));
  }

  @Test
  public void testSubstringAfter_StringString() {
    assertEquals("barXXbaz", StringUtils.substringAfter("fooXXbarXXbaz", "XX"));

    assertEquals(null, StringUtils.substringAfter(null, null));
    assertEquals(null, StringUtils.substringAfter(null, ""));
    assertEquals(null, StringUtils.substringAfter(null, "XX"));
    assertEquals("", StringUtils.substringAfter("", null));
    assertEquals("", StringUtils.substringAfter("", ""));
    assertEquals("", StringUtils.substringAfter("", "XX"));

    assertEquals("", StringUtils.substringAfter("foo", null));
    assertEquals("ot", StringUtils.substringAfter("foot", "o"));
    assertEquals("bc", StringUtils.substringAfter("abc", "a"));
    assertEquals("cba", StringUtils.substringAfter("abcba", "b"));
    assertEquals("", StringUtils.substringAfter("abc", "c"));
    assertEquals("abc", StringUtils.substringAfter("abc", ""));
    assertEquals("", StringUtils.substringAfter("abc", "d"));
  }

  @Test
  public void testSubstringBeforeLast_StringString() {
    assertEquals("fooXXbar", StringUtils.substringBeforeLast("fooXXbarXXbaz", "XX"));

    assertEquals(null, StringUtils.substringBeforeLast(null, null));
    assertEquals(null, StringUtils.substringBeforeLast(null, ""));
    assertEquals(null, StringUtils.substringBeforeLast(null, "XX"));
    assertEquals("", StringUtils.substringBeforeLast("", null));
    assertEquals("", StringUtils.substringBeforeLast("", ""));
    assertEquals("", StringUtils.substringBeforeLast("", "XX"));

    assertEquals("foo", StringUtils.substringBeforeLast("foo", null));
    assertEquals("foo", StringUtils.substringBeforeLast("foo", "b"));
    assertEquals("fo", StringUtils.substringBeforeLast("foo", "o"));
    assertEquals("abc\r\n", StringUtils.substringBeforeLast("abc\r\n", "d"));
    assertEquals("abc", StringUtils.substringBeforeLast("abcdabc", "d"));
    assertEquals("abcdabc", StringUtils.substringBeforeLast("abcdabcd", "d"));
    assertEquals("a", StringUtils.substringBeforeLast("abc", "b"));
    assertEquals("abc ", StringUtils.substringBeforeLast("abc \n", "\n"));
    assertEquals("a", StringUtils.substringBeforeLast("a", null));
    assertEquals("a", StringUtils.substringBeforeLast("a", ""));
    assertEquals("", StringUtils.substringBeforeLast("a", "a"));
  }

  @Test
  public void testSubstringAfterLast_StringString() {
    assertEquals("baz", StringUtils.substringAfterLast("fooXXbarXXbaz", "XX"));

    assertEquals(null, StringUtils.substringAfterLast(null, null));
    assertEquals(null, StringUtils.substringAfterLast(null, ""));
    assertEquals(null, StringUtils.substringAfterLast(null, "XX"));
    assertEquals("", StringUtils.substringAfterLast("", null));
    assertEquals("", StringUtils.substringAfterLast("", ""));
    assertEquals("", StringUtils.substringAfterLast("", "a"));

    assertEquals("", StringUtils.substringAfterLast("foo", null));
    assertEquals("", StringUtils.substringAfterLast("foo", "b"));
    assertEquals("t", StringUtils.substringAfterLast("foot", "o"));
    assertEquals("bc", StringUtils.substringAfterLast("abc", "a"));
    assertEquals("a", StringUtils.substringAfterLast("abcba", "b"));
    assertEquals("", StringUtils.substringAfterLast("abc", "c"));
    assertEquals("", StringUtils.substringAfterLast("", "d"));
    assertEquals("", StringUtils.substringAfterLast("abc", ""));
  }

  @Test
  public void testSubstringBetween_StringString() {
    assertEquals(null, StringUtils.substringBetween(null, "tag"));
    assertEquals("", StringUtils.substringBetween("", ""));
    assertEquals(null, StringUtils.substringBetween("", "abc"));
    assertEquals("", StringUtils.substringBetween("    ", " "));
    assertEquals(null, StringUtils.substringBetween("abc", null));
    assertEquals("", StringUtils.substringBetween("abc", ""));
    assertEquals(null, StringUtils.substringBetween("abc", "a"));
    assertEquals("bc", StringUtils.substringBetween("abca", "a"));
    assertEquals("bc", StringUtils.substringBetween("abcabca", "a"));
    assertEquals("bar", StringUtils.substringBetween("\nbar\n", "\n"));
  }

  @Test
  public void testSubstringBetween_StringStringString() {
    assertEquals(null, StringUtils.substringBetween(null, "", ""));
    assertEquals("", StringUtils.substringBetween("", "", ""));
    assertEquals("", StringUtils.substringBetween("foo", "", ""));
    assertEquals(null, StringUtils.substringBetween("foo", "", "]"));
    assertEquals(null, StringUtils.substringBetween("foo", "[", "]"));
    assertEquals("", StringUtils.substringBetween("    ", " ", "  "));
    assertEquals("bar", StringUtils.substringBetween("<foo>bar</foo>", "<foo>",
        "</foo>"));
  }

  @Test
  public void testSubstringsBetween_StringStringString() {

    String[] results = StringUtils.substringsBetween("[one], [two], [three]", "[",
        "]");
    assertEquals(3, results.length);
    assertEquals("one", results[0]);
    assertEquals("two", results[1]);
    assertEquals("three", results[2]);

    results = StringUtils.substringsBetween("[one], [two], three", "[", "]");
    assertEquals(2, results.length);
    assertEquals("one", results[0]);
    assertEquals("two", results[1]);

    results = StringUtils.substringsBetween("[one], [two], three]", "[", "]");
    assertEquals(2, results.length);
    assertEquals("one", results[0]);
    assertEquals("two", results[1]);

    results = StringUtils.substringsBetween("[one], two], three]", "[", "]");
    assertEquals(1, results.length);
    assertEquals("one", results[0]);

    results = StringUtils.substringsBetween("one], two], [three]", "[", "]");
    assertEquals(1, results.length);
    assertEquals("three", results[0]);

    // 'ab hello ba' will match, but 'ab non ba' won't
    // this is because the 'a' is shared between the two and can't be matched
    // twice
    results = StringUtils.substringsBetween("aabhellobabnonba", "ab", "ba");
    assertEquals(1, results.length);
    assertEquals("hello", results[0]);

    results = StringUtils.substringsBetween("one, two, three", "[", "]");
    assertNull(results);

    results = StringUtils.substringsBetween("[one, two, three", "[", "]");
    assertNull(results);

    results = StringUtils.substringsBetween("one, two, three]", "[", "]");
    assertNull(results);

    results = StringUtils.substringsBetween("[one], [two], [three]", "[", null);
    assertNull(results);

    results = StringUtils.substringsBetween("[one], [two], [three]", null, "]");
    assertNull(results);

    results = StringUtils.substringsBetween("[one], [two], [three]", "", "");
    assertNull(results);

    results = StringUtils.substringsBetween(null, "[", "]");
    assertNull(results);

    results = StringUtils.substringsBetween("", "[", "]");
    assertEquals(0, results.length);
  }


}
