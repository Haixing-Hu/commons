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

import java.lang.reflect.Array;
import java.util.Map;

import com.github.haixing_hu.util.expand.ExpansionPolicy;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * The {@link TrieMap} implements a {@link Map} using a trie.
 *
 * @author Haixing Hu
 */
public final class TrieMap<VALUE> {

  private static final int INITIAL_CHILDREN_CAPACITY    = 4;

  private final class Node {
    char   ch;
    VALUE  value;
    int    childrenCount;
    Node[] children;

    Node(final char ch, final VALUE value) {
      this.ch = ch;
      this.value = value;
      this.childrenCount = 0;
      this.children = null;
    }

    Node getChild(final char ch) {
      if (childrenCount == 0) {
        return null;
      }
      int min = 0;
      int max = childrenCount - 1;
      int mid = 0;
      // binary search the child
      while (min < max) {
        mid = (min + max) / 2;
        final Node child = children[mid];
        if (child.ch == ch) {
          return child;
        }
        if (child.ch < ch) {
          min = mid + 1;
        } else {
          max = mid - 1;
        }
      }
      if (min == max) {
        final Node child = children[min];
        if (child.ch == ch) {
          return child;
        }
      }
      return null;
    }

    @SuppressWarnings("unchecked")
    Node getOrAddChild(final char ch) {
      if (childrenCount == 0) {
        final Node newChild = new Node(ch, null);
        children = (Node[]) Array.newInstance(Node.class, INITIAL_CHILDREN_CAPACITY);
        children[0] = newChild;
        childrenCount = 1;
        return newChild;
      }
      int min = 0;
      int max = childrenCount - 1;
      int mid = 0;
      // binary search the child
      final ExpansionPolicy expansionPolicy = ExpansionPolicy.getDefault();
      while (min < max) {
        mid = (min + max) / 2;
        final Node child = children[mid];
        if (child.ch == ch) {
          return child;
        }
        if (child.ch < ch) {
          min = mid + 1;
        } else {
          max = mid - 1;
        }
      }
      final Node child = children[min];
      if (child.ch == ch) {
        return child;
      }
      final Node newChild = new Node(ch, null);
      // calculate the position where to insert the new child
      int pos = min;
      if (child.ch < ch) {
        ++pos;
      }
      // now insert the new child into the children array at pos
      if (childrenCount == children.length) {
        children = expansionPolicy.expand(children, childrenCount,
            childrenCount + 1, Node.class);
      }
      // move children after the pos
      assert (childrenCount < children.length);
      for (int i = childrenCount - 1; i >= pos; --i) {
        children[i + 1] = children[i];
      }
      children[pos] = newChild;
      ++childrenCount;
      return newChild;
    }

    void compact() {
      if (childrenCount == 0) {
        return;
      }
      int nonEmpty = 0;
      for (int i = 0; i < childrenCount; ++i) {
        assert (children[i] != null);
        children[i].compact();
        if ((children[i].childrenCount == 0)
            && (children[i].value == null)) {
          children[i] = null; // delete children[i]
        } else {
          ++nonEmpty;
        }
      }
      if (nonEmpty == 0) {
        childrenCount = 0;
        children = null;
      } else if (nonEmpty < childrenCount) {
        // compact the children array
        @SuppressWarnings("unchecked")
        final Node[] newChildren = (Node[])Array.newInstance(Node.class, nonEmpty);
        int index = 0;
        for (int i = 0; i < childrenCount; ++i) {
          if (children[i] != null) {
            newChildren[index++] = children[i];
          }
        }
        children = newChildren;
        childrenCount = nonEmpty;
      }
    }

    int nodeCount() {
      int result = 1;
      for (int i = 0; i < childrenCount; ++i) {
        assert (children[i] != null);
        result += children[i].nodeCount();
      }
      return result;
    }
  }

  private final Node root;
  private int size;
  private final boolean caseInsensitive;

  public TrieMap() {
    root = new Node('\0', null);
    size = 0;
    caseInsensitive = false;
  }

  public TrieMap(final boolean caseInsensitive) {
    this.root = new Node('\0', null);
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
    return size == 0;
  }

  public int nodeCount() {
    return root.nodeCount();
  }

  public void clear() {
    root.value = null;
    root.childrenCount = 0;
    root.children = null;
    size = 0;
  }

  private Node getNode(final String str) {
    final int len = str.length();
    Node node = root;
    for (int i = 0; i < len; ++i) {
      char ch = str.charAt(i);
      if (caseInsensitive) {
        ch = Character.toLowerCase(ch);
      }
      node = node.getChild(ch);
      if (node == null) {
        return null;
      }
    }
    return node;
  }

  public VALUE get(final String str) {
    final Node node = getNode(str);
    if (node == null) {
      return null;
    } else {
      return node.value;
    }
  }

  public boolean contains(final String str) {
    final Node node = getNode(str);
    if (node == null) {
      return false;
    } else {
      return (node.value != null);
    }
  }

  public boolean containsPrefix(final String prefix) {
    final Node node = getNode(prefix);
    return (node != null);
  }

  private Node getNodeByPrefixOf(final String str) {
    final int len = str.length();
    Node node = root;
    // note that is str is empty, the root is returned
    Node target = root;
    for (int i = 0; i < len; ++i) {
      char ch = str.charAt(i);
      if (caseInsensitive) {
        ch = Character.toLowerCase(ch);
      }
      node = node.getChild(ch);
      if (node == null) {
        return target;
      }
      // remember the last node which contains a value
      if (node.value != null) {
        target = node;
      }
    }
    return target;
  }

  public VALUE getPrefixOf(final String str) {
    final Node node = getNodeByPrefixOf(str);
    assert (node != null);
    return node.value;
  }

  public boolean containsPrefixOf(final String str) {
    final Node node = getNodeByPrefixOf(str);
    assert (node != null);
    return (node.value != null);
  }

  private Node getOrAddNode(final String str) {
    final int len = str.length();
    Node node = root;
    for (int i = 0; i < len; ++i) {
      char ch = str.charAt(i);
      if (caseInsensitive) {
        ch = Character.toLowerCase(ch);
      }
      node = node.getOrAddChild(ch);
    }
    return node;
  }

  public VALUE put(final String str, final VALUE value) {
    requireNonNull("str", str);
    requireNonNull("value", value);
    final Node node = getOrAddNode(str);
    assert (node != null);
    if (node.value == null) {
      node.value = value;
      ++size;
      return null;
    } else {
      final VALUE oldValue = node.value;
      node.value = value;
      return oldValue;
    }
  }

  public boolean remove(final String str) {
    requireNonNull("str", str);
    if (size == 0) {
      return false;
    }
    final Node node = getNode(str);
    if ((node == null) || (node.value == null)) {
      return false;
    } else {
      node.value = null;
      --size;
      return true;
    }
  }

  public void compact() {
    root.compact();
  }
}
