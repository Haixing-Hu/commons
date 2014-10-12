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

import javax.annotation.concurrent.NotThreadSafe;

/**
 * A tree based trie for ASCII strings.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class AsciiTrie {

  private static class Node {
    int occurence;
    Node[] children;

    Node() {
      this.occurence = 0;
      this.children = new Node[Ascii.MAX];
    }

    boolean compact() {
      boolean result = true;
      for (int i = 0; i < Ascii.MAX; ++i) {
        if (children[i] != null) {
          if (children[i].compact()) {
            children[i] = null; // delete children[i]
          } else {
            result = false;
          }
        }
      }
      return result & (occurence == 0);
    }

    int nodeCount() {
      int result = 1;
      if (children != null) {
        for (int i = 0; i < Ascii.MAX; ++i) {
          if (children[i] != null) {
            result += children[i].nodeCount();
          }
        }
      }
      return result;
    }
  }

  private final Node root;
  private int size;
  private final boolean caseInsensitive;

  public AsciiTrie() {
    root = new Node();
    size = 0;
    caseInsensitive = false;
  }

  public AsciiTrie(final boolean caseInsensitive) {
    this.root = new Node();
    this.size = 0;
    this.caseInsensitive = caseInsensitive;
  }

  public boolean isCaseInsensitive() {
    return caseInsensitive;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return (size == 0);
  }

  public int nodeCount() {
    return root.nodeCount();
  }

  public void clear() {
    for (int i = 0; i < Ascii.MAX; ++i) {
      root.children[i] = null;
    }
    root.occurence = 0;
    size = 0;
  }

  private Node getNode(final String str) {
    final int len = str.length();
    Node node = root;
    for (int i = 0; i < len; ++i) {
      char ch = str.charAt(i);
      if (! Ascii.isAscii(ch)) {
        return null;
      }
      if (caseInsensitive) {
        ch = Ascii.toLowercase(ch);
      }
      node = node.children[ch];
      if (node == null) {
        return null;
      }
    }
    return node;
  }

  public boolean contains(final String str) {
    final Node node = getNode(str);
    return ((node != null) && (node.occurence > 0));
  }

  public boolean containsPrefix(final String prefix) {
    final Node node = getNode(prefix);
    return (node != null);
  }

  public boolean containsPrefixOf(final String str) {
    if (str == null) {
      return false;
    }
    final int len = str.length();
    Node node = root;
    for (int i = 0; i < len; ++i) {
      char ch = str.charAt(i);
      if (! Ascii.isAscii(ch)) {
        return false;
      }
      if (caseInsensitive) {
        ch = Ascii.toLowercase(ch);
      }
      node = node.children[ch];
      if (node == null) {
        return false;
      }
      if (node.occurence > 0) {
        return true;
      }
    }
    return false;
  }

  public int count(final String str) {
    final Node node = getNode(str);
    if (node == null) {
      return 0;
    } else {
      return node.occurence;
    }
  }

  private Node getOrAddNode(final String str) {
    final int len = str.length();
    Node node = root;
    for (int i = 0; i < len; ++i) {
      char ch = str.charAt(i);
      if (! Ascii.isAscii(ch)) {
        return null;
      }
      if (caseInsensitive) {
        ch = Ascii.toLowercase(ch);
      }
      if (node.children[ch] == null) {
        node.children[ch] = new Node();
      }
      node = node.children[ch];
    }
    return node;
  }

  public boolean add(final String str) {
    final Node node = getOrAddNode(str);
    if (node == null) {
      return false;
    } else {
      ++node.occurence;
      ++size;
      return true;
    }
  }


  public int add(final String str, final int occurence) {
    if (occurence < 0) {
      throw new IllegalArgumentException();
    }
    if (occurence == 0) {
      return 0;
    }
    final Node node = getOrAddNode(str);
    if (node == null) {
      return 0;
    } else {
      node.occurence += occurence;
      size += occurence;
      return occurence;
    }
  }

  public boolean remove(final String str) {
    if (size == 0) {
      return false;
    }
    final Node node = getNode(str);
    if (node == null) {
      return false;
    }
    if (node.occurence == 0) {
      return false;
    } else {
      --node.occurence;
      --size;
      return true;
    }
  }

  public int remove(final String str, int occurence) {
    if (occurence < 0) {
      throw new IllegalArgumentException();
    }
    if (occurence == 0) {
      return 0;
    }
    if (size == 0) {
      return 0;
    }
    final Node node = getNode(str);
    if (node == null) {
      return 0;
    }
    if (node.occurence < occurence) {
      occurence = node.occurence;
    }
    node.occurence -= occurence;
    size -= occurence;
    return occurence;
  }

  public int removeAll(final String str) {
    if (size == 0) {
      return 0;
    }
    final Node node = getNode(str);
    if (node == null) {
      return 0;
    }
    final int occurence = node.occurence;
    node.occurence = 0;
    size -= occurence;
    return occurence;
  }

  public void compact() {
    root.compact();
  }
}
