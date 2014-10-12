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

import static com.github.haixing_hu.lang.StringUtils.*;

import static org.junit.Assert.*;
/**
 * Unit test for the modification functions in the Strings class.
 *
 * @author Haixing Hu
 */
public class StringUtilsModificationTest extends StringUtilsTestBase {

  @Test
  public void testStrip_String() {
    // null strip
    assertNull(strip(null));
    assertEquals("", strip(""));
    assertEquals("", strip("        "));
    assertEquals("abc", strip("  abc  "));
    assertEquals(NON_BLANK, strip(BLANK + NON_BLANK + BLANK));

    // "" strip
    assertNull(strip(null, " "));
    assertEquals("", strip("", ""));
    assertEquals("        ", strip("        ", ""));
    assertEquals("  abc  ", strip("  abc  ", ""));
    assertEquals(BLANK, strip(BLANK, ""));

    // " " strip
    assertNull(strip(null, " "));
    assertEquals("", strip("", " "));
    assertEquals("", strip("        ", " "));
    assertEquals("abc", strip("  abc  ", " "));

    // "ab" strip
    assertNull(strip(null, "ab"));
    assertEquals("", strip("", "ab"));
    assertEquals("        ", strip("        ", "ab"));
    assertEquals("  abc  ", strip("  abc  ", "ab"));
    assertEquals("c", strip("abcabab", "ab"));
    assertEquals(BLANK, strip(BLANK, ""));
  }

  @Test
  public void testStripStart_String_String() {
    // null stripStart
    assertNull(stripStart(null));
    assertEquals("", stripStart(""));
    assertEquals("", stripStart("        "));
    assertEquals("abc  ", stripStart("  abc  "));
    assertEquals(NON_BLANK + BLANK, stripStart(BLANK  + NON_BLANK + BLANK));

    // "" stripStart
    assertNull(stripStart(null, ""));
    assertEquals("", stripStart("", ""));
    assertEquals("        ", stripStart("        ", ""));
    assertEquals("  abc  ", stripStart("  abc  ", ""));
    assertEquals(BLANK, stripStart(BLANK, ""));

    // " " stripStart
    assertNull(stripStart(null, " "));
    assertEquals("", stripStart("", " "));
    assertEquals("", stripStart("        ", " "));
    assertEquals("abc  ", stripStart("  abc  ", " "));

    // "ab" stripStart
    assertNull(stripStart(null, "ab"));
    assertEquals("", stripStart("", "ab"));
    assertEquals("        ", stripStart("        ", "ab"));
    assertEquals("  abc  ", stripStart("  abc  ", "ab"));
    assertEquals("cabab", stripStart("abcabab", "ab"));
    assertEquals(BLANK, stripStart(BLANK, ""));
  }

  @Test
  public void testStripEnd_String_String() {
    // null stripEnd
    assertNull(stripEnd((String) null));
    assertEquals("", stripEnd(""));
    assertEquals("", stripEnd("        "));
    assertEquals("  abc", stripEnd("  abc  "));
    assertEquals(BLANK + NON_BLANK, stripEnd(BLANK + NON_BLANK + BLANK));

    // "" stripEnd
    assertNull(stripEnd(null, ""));
    assertEquals("", stripEnd("", ""));
    assertEquals("        ", stripEnd("        ", ""));
    assertEquals("  abc  ", stripEnd("  abc  ", ""));
    assertEquals(BLANK, stripEnd(BLANK, ""));

    // " " stripEnd
    assertNull(stripEnd(null, " "));
    assertEquals("", stripEnd("", " "));
    assertEquals("", stripEnd("        ", " "));
    assertEquals("  abc", stripEnd("  abc  ", " "));

    // "ab" stripEnd
    assertNull(stripEnd(null, "ab"));
    assertEquals("", stripEnd("", "ab"));
    assertEquals("        ", stripEnd("        ", "ab"));
    assertEquals("  abc  ", stripEnd("  abc  ", "ab"));
    assertEquals("abc", stripEnd("abcabab", "ab"));
    assertEquals(BLANK, stripEnd(BLANK, ""));
  }

  @Test
  public void testChop() {
    final String[][] chopCases = {
        { "foobar" + "\r\n", "foobar" } ,
        { "foobar" + "\n" , "foobar" } ,
        { "foobar" + "\r", "foobar" },
        { "foobar" + " \r", "foobar" + " " },
        { "foo", "fo"},
        { "foo\nfoo", "foo\nfo" },
        { "\n", "" },
        { "\r", "" },
        { "\r\n", "" },
        { null, null },
        { "", "" },
        { "a", "" },
    };
    for (final String[] chopCase : chopCases) {
        final String original = chopCase[0];
        final String expectedResult = chopCase[1];
        assertEquals("chop(String) failed",
                expectedResult, chop(original));
    }
  }

  @Test
  public void testChomp() {

    final String[][] chompCases = {
        { "foobar" + "\r\n", "foobar" },
        { "foobar" + "\n" , "foobar" },
        { "foobar" + "\r", "foobar" },
        { "foobar" + " \r", "foobar" + " " },
        { "foobar", "foobar" },
        { "foobar" + "\n\n", "foobar" + "\n"},
        { "foobar" + "\r\n\r\n", "foobar" + "\r\n" },
        { "foo\nfoo", "foo\nfoo" },
        { "foo\n\rfoo", "foo\n\rfoo" },
        { "\n", "" },
        { "\r", "" },
        { "a", "a" },
        { "\r\n", "" },
        { "", "" },
        { null, null },
        { "foobar" + "\n\r", "foobar" + "\n"}
    };
    for (final String[] chompCase : chompCases) {
        final String original = chompCase[0];
        final String expectedResult = chompCase[1];
        assertEquals("chomp(String) failed",
                expectedResult, chomp(original));
    }

    assertEquals("chomp(String, String) failed",
            "foo", chomp("foobar", "bar"));
    assertEquals("chomp(String, String) failed",
            "foobar", chomp("foobar", "baz"));
    assertEquals("chomp(String, String) failed",
            "foo", chomp("foo", "foooo"));
    assertEquals("chomp(String, String) failed",
            "foobar", chomp("foobar", ""));
    assertEquals("chomp(String, String) failed",
            "foobar", chomp("foobar", (String)null));
    assertEquals("chomp(String, String) failed",
            "", chomp("", "foo"));
    assertEquals("chomp(String, String) failed",
            "", chomp("", (String)null));
    assertEquals("chomp(String, String) failed",
            "", chomp("", ""));
    assertEquals("chomp(String, String) failed",
            null, chomp(null, "foo"));
    assertEquals("chomp(String, String) failed",
            null, chomp(null, (String)null));
    assertEquals("chomp(String, String) failed",
            null, chomp(null, ""));
    assertEquals("chomp(String, String) failed",
            "", chomp("foo", "foo"));
    assertEquals("chomp(String, String) failed",
            " ", chomp(" foo", "foo"));
    assertEquals("chomp(String, String) failed",
            "foo ", chomp("foo ", "foo"));
  }

  @Test
  public void testChopNewLine() {

    final String[][] newLineCases = {
        { "foobar" + "\r\n", "foobar" } ,
        { "foobar" + "\n" , "foobar" } ,
        { "foobar" + "\r", "foobar"  },
        { "foobar", "foobar" },
        { "foobar" + "\n" + "foobar" , "foobar" + "\n" + "foobar" },
        { "foobar" + "\n\n", "foobar" + "\n"},
        { "\n", "" },
        { "", "" },
        { "\r\n", "" }
    };

    for (final String[] newLineCase : newLineCases) {
      final String original = newLineCase[0];
      final String expectedResult = newLineCase[1];
      assertEquals("chomp(String) failed",
              expectedResult, chomp(original));
    }
  }


  @Test
  public void testRemovePrefix() {
    // Strings.removeStart("", *)        = ""
    assertNull(removePrefix(null, null, false));
    assertNull(removePrefix(null, "", false));
    assertNull(removePrefix(null, "a", false));

    // Strings.removeStart(*, null)      = *
    assertEquals("", removePrefix("", null, false));
    assertEquals("", removePrefix("", "", false));
    assertEquals("", removePrefix("", "a", false));

    // All others:
    assertEquals("domain.com", removePrefix("www.domain.com", "www.", false));
    assertEquals("wwW.domain.com", removePrefix("wwW.domain.com", "www.", false));
    assertEquals("domain.com", removePrefix("domain.com", "www.", false));
    assertEquals("domain.com", removePrefix("domain.com", "", false));
    assertEquals("domain.com", removePrefix("domain.com", null, false));
    assertEquals("", removePrefix("domain.com", "domain.com", false));
    assertEquals("domain.com", removePrefix("domain.com", "DOMAIN.COM", false));

    // Strings.removeStart("", *)        = ""
    assertNull(removePrefix(null, null, true));
    assertNull(removePrefix(null, "", true));
    assertNull(removePrefix(null, "a", true));

    // Strings.removeStart(*, null)      = *
    assertEquals("", removePrefix("", null, true));
    assertEquals("", removePrefix("", "", true));
    assertEquals("", removePrefix("", "a", true));

    // All others:
    assertEquals("domain.com", removePrefix("www.domain.com", "www.", true));
    assertEquals("domain.com", removePrefix("wwW.domain.com", "www.", true));
    assertEquals("domain.com", removePrefix("domain.com", "", true));
    assertEquals("", removePrefix("domain.com", "domain.com", true));
    assertEquals("", removePrefix("domain.com", "DOMAIN.COM", true));

  }

  @Test
  public void testRemoveSuffix() {
    // Strings.removeEnd("", *)        = ""
    assertNull(removeSuffix(null, null, false));
    assertNull(removeSuffix(null, "", false));
    assertNull(removeSuffix(null, "a", false));

    // Strings.removeEnd(*, null)      = *
    assertEquals("", removeSuffix("", null, false));
    assertEquals("", removeSuffix("", "", false));
    assertEquals("", removeSuffix("", "a", false));

    // All others:
    assertEquals("www.domain.com.", removeSuffix("www.domain.com.", ".com", false));
    assertEquals("www.domain", removeSuffix("www.domain.com", ".com", false));
    assertEquals("www.domain.Com", removeSuffix("www.domain.Com", ".com", false));
    assertEquals("www.domain", removeSuffix("www.domain", ".com", false));
    assertEquals("domain.com", removeSuffix("domain.com", "", false));
    assertEquals("domain.com", removeSuffix("domain.com", null, false));
    assertEquals("", removeSuffix("domain.com", "domain.com", false));
    assertEquals("domain.com", removeSuffix("domain.com", "domain.Com", false));

    // Strings.removeEndIgnoreCase("", *)        = ""
    assertNull(removeSuffix(null, null, true));
    assertNull(removeSuffix(null, "", true));
    assertNull(removeSuffix(null, "a", true));

    // Strings.removeEnd(*, null)      = *
    assertEquals(removeSuffix("", null, true), "");
    assertEquals(removeSuffix("", "", true), "");
    assertEquals(removeSuffix("", "a", true), "");

    // All others:
    assertEquals("www.domain.com.", removeSuffix("www.domain.com.", ".com", true));
    assertEquals("www.domain", removeSuffix("www.domain.com", ".com", true));
    assertEquals("www.domain", removeSuffix("www.domain.Com", ".com", true));
    assertEquals("www.domain", removeSuffix("www.domain", ".com", true));
    assertEquals("domain.com", removeSuffix("domain.com", "", true));
    assertEquals("domain.com", removeSuffix("domain.com", null, true));
    assertEquals("", removeSuffix("domain.com", "domain.com", true));
    assertEquals("", removeSuffix("domain.com", "domain.Com", true));

  }

  @Test
  public void testRemove_String() {
    // Strings.removeSubstring(null, *, *, *)        = null
    assertNull(removeSubstring(null, null, -1, false));
    assertNull(removeSubstring(null, "", -1, false));
    assertNull(removeSubstring(null, "a", -1, false));

    // Strings.removeSubstring("", *, *, *)          = ""
    assertEquals("", removeSubstring("", null, -1, false));
    assertEquals("", removeSubstring("", "", -1, false));
    assertEquals("", removeSubstring("", "a", -1, false));

    // Strings.remove(str, null, *, *)        = str
    assertNull(removeSubstring(null, null, -1, false));
    assertEquals("", removeSubstring("", null, -1, false));
    assertEquals("a", removeSubstring("a", null, -1, false));

    // Strings.remove(str, "", *, *)          = str
    assertNull(removeSubstring(null, "", -1, false));
    assertEquals("", removeSubstring("", "", -1, false));
    assertEquals("a", removeSubstring("a", "", -1, false));

    assertEquals("queued", removeSubstring("queued", "Ue", -1, false));
    assertEquals("qd", removeSubstring("queued", "Ue", -1, true));
    assertEquals("queued", removeSubstring("queued", "Ue", 1, false));
    assertEquals("qued", removeSubstring("queued", "Ue", 1, true));
    assertEquals("queued", removeSubstring("queued", "Ue", 0, false));
    assertEquals("queued", removeSubstring("queued", "Ue", 0, true));

    // Strings.remove("queued", "zz") = "queued"
    assertEquals("queued", removeSubstring("queued", "zz", -1, false));
    assertEquals("queued", removeSubstring("queued", "zz", -1, true));
  }

  @Test
  public void testRemoveChar() {
    // Strings.removeChar(null, *, *)       = null
    assertNull(removeChar(null, 'a', -1));
    assertNull(removeChar(null, 'a', -1));
    assertNull(removeChar(null, 'a', -1));

    // Strings.removeChar("", *)          = ""
    assertEquals("", removeChar("", 'a', -1));
    assertEquals("", removeChar("", 'a', -1));
    assertEquals("", removeChar("", 'a', -1));

    assertEquals("qeed", removeChar("queued", 'u', -1));
    assertEquals("qeued", removeChar("queued", 'u', 1));
    assertEquals("queued", removeChar("queued", 'u', 0));

    // Strings.removeChar("queued", 'z') = "queued"
    assertEquals("queued", removeChar("queued", 'z', -1));
  }

  @Test
  public void testCompactSpace() {
    String str = " hello \n\r\n\r\r     world  \u000B  !!   ";
    String expected = "hello\nworld !!";
    assertEquals(expected, normalizeSpace(str, false));

    expected = "hello world !!";
    assertEquals(expected, normalizeSpace(str, true));

    str = "\n\n\n\n\r\r\r\r\r\r  abcd\u00A0e\r\u00A0\u00A0\u00A0\u00A0 \nfgh\n  ";
    expected = "abcd e\nfgh";
    assertEquals(expected, normalizeSpace(str, false));

    expected = "abcd e fgh";
    assertEquals(expected, normalizeSpace(str, true));

    str = "2011年02月07日\u00A009:26";
    expected = "2011年02月07日 09:26";
    assertEquals(expected, normalizeSpace(str, true));

  }

}
