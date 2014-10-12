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

import com.github.haixing_hu.text.Trie;

import static org.junit.Assert.assertEquals;

/**
 * Unit test of the {@link Trie} class.
 *
 * @author Haixing Hu
 */
public class TrieTest {
  /**
   * Test method for {@link Trie#size()}.
   */
  @Test
  public void testSize() {
    final Trie trie = new Trie();

    assertEquals(0, trie.size());
    trie.add("hello");
    assertEquals(1, trie.size());
    trie.add("hello");
    assertEquals(2, trie.size());
    trie.add("world");
    assertEquals(3, trie.size());

    trie.add("hello\u00ff");
    assertEquals(4, trie.size());

    trie.remove("hello");
    assertEquals(3, trie.size());

    trie.remove("hihi");
    assertEquals(3, trie.size());

    trie.remove("hello");
    assertEquals(2, trie.size());

    trie.remove("world");
    assertEquals(1, trie.size());

    trie.remove("hello\u00ff");
    assertEquals(0, trie.size());

    trie.add("hello");
    trie.add("world");
    trie.add("hello\u00ff");
    assertEquals(3, trie.size());
    trie.clear();
    assertEquals(0, trie.size());
  }

  /**
   * Test method for {@link Trie#isEmpty()}.
   */
  @Test
  public void testIsEmpty() {
    final Trie trie = new Trie();

    assertEquals(true, trie.isEmpty());
    trie.add("hello");
    assertEquals(false, trie.isEmpty());
    trie.add("hello");
    assertEquals(false, trie.isEmpty());
    trie.add("world");
    assertEquals(false, trie.isEmpty());

    trie.add("hello\u00ff");
    assertEquals(false, trie.isEmpty());

    trie.remove("hello");
    assertEquals(false, trie.isEmpty());

    trie.remove("hihi");
    assertEquals(false, trie.isEmpty());

    trie.remove("hello");
    assertEquals(false, trie.isEmpty());

    trie.remove("world");
    assertEquals(false, trie.isEmpty());

    trie.add("hello");
    trie.add("world");
    trie.add("hello\u00ff");
    assertEquals(false, trie.isEmpty());
    trie.clear();
    assertEquals(true, trie.isEmpty());
  }

  /**
   * Test method for {@link Trie#contains(java.lang.String)}.
   */
  @Test
  public void testContains() {
    final Trie trie = new Trie();

    assertEquals(false, trie.contains(""));
    trie.add("");
    assertEquals(true, trie.contains(""));

    assertEquals(false, trie.contains("hello"));
    trie.add("hello");
    assertEquals(true, trie.contains("hello"));

    assertEquals(false, trie.contains("world"));
    trie.add("world");
    assertEquals(true, trie.contains("world"));

    assertEquals(false, trie.contains("world\u00ff"));
    trie.add("world\u00ff");
    assertEquals(true, trie.contains("world\u00ff"));

    trie.remove("world");
    assertEquals(false, trie.contains("world"));

  }

  /**
   * Test method for {@link Trie#getOccurence(java.lang.String)}.
   */
  @Test
  public void testGetOccurence() {
    final Trie trie = new Trie();

    assertEquals(0, trie.count(""));
    trie.add("");
    trie.add("");
    trie.add("");
    assertEquals(3, trie.count(""));
    trie.remove("");
    assertEquals(2, trie.count(""));


    assertEquals(0, trie.count("hello"));
    trie.add("hello");
    assertEquals(1, trie.count("hello"));
    trie.add("hello");
    assertEquals(2, trie.count("hello"));
    trie.add("hello");
    assertEquals(3, trie.count("hello"));

    assertEquals(0, trie.count("world"));
    trie.add("world");
    assertEquals(1, trie.count("world"));

    assertEquals(0, trie.count("world\u00ff"));
    trie.add("world\u00ff");
    assertEquals(1, trie.count("world\u00ff"));

    trie.remove("world");
    assertEquals(0, trie.count("world"));
  }

  /**
   * Test method for {@link Trie#add(java.lang.String)}.
   */
  @Test
  public void testAdd() {
    final Trie trie = new Trie();

    assertEquals(false, trie.contains(""));
    trie.add("");
    assertEquals(true, trie.contains(""));
    trie.remove("");
    assertEquals(false, trie.contains(""));

    assertEquals(false, trie.contains("hello"));
    trie.add("hello");
    assertEquals(true, trie.contains("hello"));

    assertEquals(false, trie.contains("world"));
    trie.add("world");
    assertEquals(true, trie.contains("world"));

    assertEquals(false, trie.contains("world\u00ff"));
    trie.add("world\u00ff");
    assertEquals(true, trie.contains("world\u00ff"));

    trie.remove("world");
    assertEquals(false, trie.contains("world"));
  }

  /**
   * Test method for {@link Trie#remove(java.lang.String)}.
   */
  @Test
  public void testRemove() {
    final Trie trie = new Trie();
    boolean result = false;

    assertEquals(0, trie.count(""));
    trie.add("");
    trie.add("");
    trie.add("");
    assertEquals(3, trie.count(""));
    result = trie.remove("");
    assertEquals(true, result);
    assertEquals(2, trie.count(""));

    assertEquals(0, trie.count("hello"));
    trie.add("hello");
    assertEquals(1, trie.count("hello"));
    trie.add("hello");
    assertEquals(2, trie.count("hello"));
    result = trie.remove("hello");
    assertEquals(true, result);
    assertEquals(1, trie.count("hello"));

    assertEquals(0, trie.count("world"));
    trie.add("world");
    assertEquals(1, trie.count("world"));
    result = trie.remove("world");
    assertEquals(true, result);
    assertEquals(0, trie.count("world"));

    assertEquals(0, trie.count("world\u00ff"));
    trie.add("world\u00ff");
    assertEquals(1, trie.count("world\u00ff"));
    result = trie.remove("world\u00ff");
    assertEquals(true, result);
    assertEquals(0, trie.count("world\u00ff"));

    result = trie.remove("world");
    assertEquals(false, result);
    assertEquals(0, trie.count("world"));
  }

  /**
   * Test method for {@link Trie#compact()}.
   */
  @Test
  public void testCompact() {
    final Trie trie = new Trie();

    assertEquals(1, trie.nodeCount());
    trie.compact();
    assertEquals(1, trie.nodeCount());

    trie.add("");
    trie.add("");
    trie.add("");
    trie.remove("");
    trie.add("hello");
    trie.add("hello");
    trie.remove("hello");
    trie.compact();
    assertEquals(3, trie.size());

    assertEquals(6, trie.nodeCount());
    trie.removeAll("");
    assertEquals(6, trie.nodeCount());
    trie.removeAll("hello");
    assertEquals(6, trie.nodeCount());
    trie.compact();
    assertEquals(1, trie.nodeCount());
  }
}
