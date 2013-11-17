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

import com.github.haixing_hu.text.TrieMap;

import static org.junit.Assert.assertEquals;

/**
 * Unit test of the {@link TrieMap} class.
 *
 * @author Haixing Hu
 */
public class TrieMapTest {

  /**
   * Test method for {@link TrieMap#size()}.
   */
  @Test
  public void testSize() {
    final TrieMap<String> trie = new TrieMap<String>();

    assertEquals(0, trie.size());
    trie.put("hello", "value");
    assertEquals(1, trie.size());
    trie.put("hello", "value");
    assertEquals(1, trie.size());
    trie.put("world", "value");
    assertEquals(2, trie.size());

    trie.put("hello\u00ff", "value");
    assertEquals(3, trie.size());

    trie.remove("hello");
    assertEquals(2, trie.size());

    trie.remove("hihi");
    assertEquals(2, trie.size());

    trie.remove("hello");
    assertEquals(2, trie.size());

    trie.remove("world");
    assertEquals(1, trie.size());

    trie.remove("hello\u00ff");
    assertEquals(0, trie.size());

    trie.put("hello", "value");
    trie.put("world", "value");
    trie.put("hello\u00ff", "value");
    assertEquals(3, trie.size());
    trie.clear();
    assertEquals(0, trie.size());
  }

  /**
   * Test method for {@link TrieMap#isEmpty()}.
   */
  @Test
  public void testIsEmpty() {
    final TrieMap<String> trie = new TrieMap<String>();

    assertEquals(true, trie.isEmpty());
    trie.put("hello", "value");
    assertEquals(false, trie.isEmpty());
    trie.put("hello", "value");
    assertEquals(false, trie.isEmpty());
    trie.put("world", "value");
    assertEquals(false, trie.isEmpty());

    trie.put("hello\u00ff", "value");
    assertEquals(false, trie.isEmpty());

    trie.remove("hello");
    assertEquals(false, trie.isEmpty());

    trie.remove("hihi");
    assertEquals(false, trie.isEmpty());

    trie.remove("hello");
    assertEquals(false, trie.isEmpty());

    trie.remove("world");
    assertEquals(false, trie.isEmpty());

    trie.put("hello", "value");
    trie.put("world", "value");
    trie.put("hello\u00ff", "value");
    assertEquals(false, trie.isEmpty());
    trie.clear();
    assertEquals(true, trie.isEmpty());
  }

  /**
   * Test method for {@link TrieMap#contains(String)}.
   */
  @Test
  public void testContains() {
    final TrieMap<String> trie = new TrieMap<String>();

    assertEquals(false, trie.contains(""));
    trie.put("", "value");
    assertEquals(true, trie.contains(""));

    assertEquals(false, trie.contains("hello"));
    trie.put("hello", "value");
    assertEquals(true, trie.contains("hello"));

    assertEquals(false, trie.contains("world"));
    trie.put("world", "value");
    assertEquals(true, trie.contains("world"));

    assertEquals(false, trie.contains("world\u00ff"));
    trie.put("world\u00ff", "value");
    assertEquals(true, trie.contains("world\u00ff"));

    trie.remove("world");
    assertEquals(false, trie.contains("world"));

  }

  /**
   * Test method for {@link TrieMap#put(String, Object)}.
   */
  @Test
  public void testPut() {
    final TrieMap<String> trie = new TrieMap<String>();
    String oldValue = null;
    boolean success = false;

    assertEquals(false, trie.contains(""));
    oldValue = trie.put("", "value1");
    assertEquals(null, oldValue);
    oldValue = trie.put("", "value2");
    assertEquals("value1", oldValue);
    assertEquals(true, trie.contains(""));
    success = trie.remove("");
    assertEquals(true, success);
    assertEquals(false, trie.contains(""));

    assertEquals(false, trie.contains("hello"));
    oldValue = trie.put("hello", "value1");
    assertEquals(null, oldValue);
    oldValue = trie.put("hello", "value2");
    assertEquals("value1", oldValue);
    assertEquals(true, trie.contains("hello"));

    assertEquals(false, trie.contains("world"));
    oldValue = trie.put("world", "value1");
    assertEquals(null, oldValue);
    oldValue = trie.put("world", "value2");
    assertEquals("value1", oldValue);
    assertEquals(true, trie.contains("world"));

    assertEquals(false, trie.contains("world\u00ff"));
    oldValue = trie.put("world\u00ff", "value1");
    assertEquals(null, oldValue);
    oldValue = trie.put("world\u00ff", "value2");
    assertEquals("value1", oldValue);
    assertEquals(true, trie.contains("world\u00ff"));

    success = trie.remove("world");
    assertEquals(false, trie.contains("world"));
  }

  /**
   * Test method for {@link TrieMap#remove(String)}.
   */
  @Test
  public void testRemove() {
    final TrieMap<String> trie = new TrieMap<String>();
    boolean result = false;

    assertEquals(false, trie.contains(""));
    trie.put("", "value");
    trie.put("", "value");
    trie.put("", "value");
    assertEquals(true, trie.contains(""));
    result = trie.remove("");
    assertEquals(true, result);
    assertEquals(false, trie.contains(""));

    assertEquals(false, trie.contains("hello"));
    trie.put("hello", "value");
    assertEquals(true, trie.contains("hello"));
    trie.put("hello", "value2");
    assertEquals(true, trie.contains("hello"));
    result = trie.remove("hello");
    assertEquals(true, result);
    assertEquals(false, trie.contains("hello"));

    assertEquals(false, trie.contains("world"));
    trie.put("world", "value");
    assertEquals(true, trie.contains("world"));
    result = trie.remove("world");
    assertEquals(true, result);
    assertEquals(false, trie.contains("world"));

    assertEquals(false, trie.contains("world\u00ff"));
    trie.put("world\u00ff", "value");
    assertEquals(true, trie.contains("world\u00ff"));
    result = trie.remove("world\u00ff");
    assertEquals(true, result);
    assertEquals(false, trie.contains("world\u00ff"));

    result = trie.remove("world");
    assertEquals(false, result);
    assertEquals(false, trie.contains("world"));
  }

  /**
   * Test method for {@link TrieMap#get(String)}.
   */
  @Test
  public void testGet() {
    final TrieMap<String> trie = new TrieMap<String>();
    String result = null;

    result = trie.get("");
    assertEquals(null, result);
    trie.put("", "value1");
    result = trie.get("");
    assertEquals("value1", result);
    trie.put("", "value2");
    result = trie.get("");
    assertEquals("value2", result);

    result = trie.get("abc");
    assertEquals(null, result);
    trie.put("abc", "value1");
    result = trie.get("abc");
    assertEquals("value1", result);
    trie.put("abc", "value2");
    result = trie.get("abc");
    assertEquals("value2", result);

    result = trie.get("abcd");
    assertEquals(null, result);
    trie.put("abcd", "value1");
    result = trie.get("abcd");
    assertEquals("value1", result);
    trie.put("abcd", "value2");
    result = trie.get("abcd");
    assertEquals("value2", result);

    result = trie.get("abcdefg");
    assertEquals(null, result);
    trie.put("abcdefg", "value1");
    result = trie.get("abcdefg");
    assertEquals("value1", result);
    trie.put("abcdefg", "value2");
    result = trie.get("abcdefg");
    assertEquals("value2", result);

    result = trie.get("ab");
    assertEquals(null, result);

    result = trie.get("abd");
    assertEquals(null, result);

    result = trie.get("abcde");
    assertEquals(null, result);

    result = trie.get("abcdef");
    assertEquals(null, result);
  }

  /**
   * Test method for {@link TrieMap#getPrefixOf(String)}.
   */
  @Test
  public void testGetPrefixOf() {
    final TrieMap<String> trie = new TrieMap<String>();
    String result = null;

    result = trie.getPrefixOf("");
    assertEquals(null, result);
    result = trie.getPrefixOf("ab");
    assertEquals(null, result);
    trie.put("", "_value1");
    result = trie.getPrefixOf("");
    assertEquals("_value1", result);
    trie.put("", "_value2");
    result = trie.getPrefixOf("");
    assertEquals("_value2", result);
    result = trie.getPrefixOf("ab");
    assertEquals("_value2", result);

    result = trie.getPrefixOf("abc");
    assertEquals("_value2", result);
    trie.put("abc", "abc_value1");
    result = trie.getPrefixOf("abc");
    assertEquals("abc_value1", result);
    trie.put("abc", "abc_value2");
    result = trie.getPrefixOf("abc");
    assertEquals("abc_value2", result);
    result = trie.getPrefixOf("ab");
    assertEquals("_value2", result);
    result = trie.getPrefixOf("abcd");
    assertEquals("abc_value2", result);
    result = trie.getPrefixOf("accd");
    assertEquals("_value2", result);
    result = trie.getPrefixOf("abd");
    assertEquals("_value2", result);
    result = trie.getPrefixOf("abcdefgh");
    assertEquals("abc_value2", result);

  }


  /**
   * Test method for {@link TrieMap#compact()}.
   */
  @Test
  public void testCompact() {
    final TrieMap<String> trie = new TrieMap<String>();

    assertEquals(1, trie.nodeCount());
    trie.compact();
    assertEquals(1, trie.nodeCount());

    trie.put("", "value");
    trie.put("", "value1");
    trie.put("", "value2");
    trie.put("hello", "value1");
    trie.put("hello", "value2");
    assertEquals(2, trie.size());

    assertEquals(6, trie.nodeCount());
    trie.remove("");
    assertEquals(6, trie.nodeCount());
    trie.remove("hello");
    assertEquals(6, trie.nodeCount());
    trie.compact();
    assertEquals(1, trie.nodeCount());
  }
}
