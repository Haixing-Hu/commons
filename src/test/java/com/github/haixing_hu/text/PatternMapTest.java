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

import com.github.haixing_hu.text.Pattern;
import com.github.haixing_hu.text.PatternMap;
import com.github.haixing_hu.text.PatternType;

import static org.junit.Assert.assertEquals;

/**
 * Unit test of the {@link PatternMap} class.
 *
 * @author Haixing Hu
 */
public class PatternMapTest {

  /**
   * Test method for {@link PatternMap#PatternMap()}.
   */
  @Test
  public void testPatternMap() {
    final PatternMap<String> map = new PatternMap<String>();
    assertEquals(0, map.size());
  }

  /**
   * Test method for {@link PatternMap#clear()}.
   */
  @Test
  public void testClear() {
    final PatternMap<String> map = new PatternMap<String>();
    assertEquals(0, map.size());
    map.put(new Pattern(PatternType.LITERAL, false, "abc"), "abc");
    map.put(new Pattern(PatternType.LITERAL, true, "abcD"), "abcD");
    map.put(new Pattern(PatternType.PREFIX, false, "def"), "def");
    map.put(new Pattern(PatternType.PREFIX, true, "Cdef"), "Cdef");
    assertEquals(4, map.size());
    map.clear();
    assertEquals(0, map.size());
  }

  /**
   * Test method for {@link PatternMap#put(Pattern, Object)} and
   * {@link PatternMap#get(String)}.
   */
  @Test
  public void testPutGet() {
    final PatternMap<String> map = new PatternMap<String>();

    map.put(new Pattern(PatternType.LITERAL, false, "abc"), "abc");
    assertEquals(1, map.size());
    assertEquals("abc", map.get("abc"));
    assertEquals(null, map.get("abC"));
    assertEquals(null, map.get("abcd"));

    map.put(new Pattern(PatternType.LITERAL, true, "abcD"), "abcD");
    assertEquals(2, map.size());
    assertEquals("abc", map.get("abc"));
    assertEquals("abcD", map.get("abCD"));
    assertEquals("abcD", map.get("abcd"));
    assertEquals(null, map.get("abcde"));

    map.put(new Pattern(PatternType.PREFIX, false, "def"), "def");
    assertEquals(3, map.size());
    assertEquals("def", map.get("def"));
    assertEquals("def", map.get("defg"));
    assertEquals(null, map.get("deF"));
    assertEquals(null, map.get("de"));

    map.put(new Pattern(PatternType.PREFIX, true, "Cdef"), "Cdef");
    assertEquals(4, map.size());
    assertEquals("Cdef", map.get("cdef"));
    assertEquals("Cdef", map.get("cdefg"));
    assertEquals(null, map.get("Cde"));
    assertEquals(null, map.get("bcdef"));

    map.put(new Pattern(PatternType.SUFFIX, false, "xyz"), "xyz");
    assertEquals(5, map.size());
    assertEquals("xyz", map.get("xyz"));
    assertEquals("xyz", map.get("wxyz"));
    assertEquals(null, map.get("xyZ"));
    assertEquals(null, map.get("yz"));
    assertEquals(null, map.get("xy"));

    map.put(new Pattern(PatternType.SUFFIX, true, "789x"), "789x");
    assertEquals(6, map.size());
    assertEquals("789x", map.get("789x"));
    assertEquals("789x", map.get("6789x"));
    assertEquals(null, map.get("789x0"));
    assertEquals("789x", map.get("123456789X"));

    map.put(new Pattern(PatternType.GLOB, false, "123abc?de"), "123abc?de");
    assertEquals(7, map.size());
    assertEquals("123abc?de", map.get("123abcxde"));
    assertEquals("123abc?de", map.get("123abcyde"));
    assertEquals("123abc?de", map.get("123abcXde"));
    assertEquals(null, map.get("123abcde"));
    assertEquals(null, map.get("123abcxyde"));
    assertEquals(null, map.get("123abcxDe"));

    map.put(new Pattern(PatternType.GLOB, true, "*45X67*"), "*45X67*");
    assertEquals(8, map.size());
    assertEquals("*45X67*", map.get("45x67"));
    assertEquals("*45X67*", map.get("45X67890"));
    assertEquals("*45X67*", map.get("12345x67"));
    assertEquals("*45X67*", map.get("12345X67890"));
    assertEquals(null, map.get("5X67"));
    assertEquals(null, map.get("45XX67"));


    map.put(new Pattern(PatternType.REGEX, false, "789abc.de"), "789abc.de");
    assertEquals(9, map.size());
    assertEquals("789abc.de", map.get("789abcXde"));
    assertEquals("789abc.de", map.get("789abcdde"));
    assertEquals("789abc.de", map.get("789abc1de"));
    assertEquals(null, map.get("789abc1dee"));
    assertEquals(null, map.get("789abc12de"));
    assertEquals(null, map.get("789abcde"));

    map.put(new Pattern(PatternType.REGEX, true, ".*xx.xx.*"), ".*xx.xx.*");
    assertEquals(10, map.size());
    assertEquals(".*xx.xx.*", map.get("xx1xx"));
    assertEquals(".*xx.xx.*", map.get("12xx1xx"));
    assertEquals(".*xx.xx.*", map.get("xx1xx34"));
    assertEquals(".*xx.xx.*", map.get("xx1XXX"));
    assertEquals(null, map.get("xxxx"));
    assertEquals(null, map.get("xx12xx"));
  }


}
