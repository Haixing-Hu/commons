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
package com.github.haixing_hu.collection;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.github.haixing_hu.lang.Equality;
import com.github.haixing_hu.util.pair.Pair;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * A binary search tree implemented using the AVL tree.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class AvlTreeMap<KEY, VALUE> implements Map<KEY, VALUE> {

  protected final class Node implements Map.Entry<KEY, VALUE> {
    KEY     key;
    VALUE   value;
    Node    left;
    Node    right;
    Node    parent;
    int     height;

    Node(final KEY key, final VALUE value) {
      this.key = requireNonNull("key", key);
      this.value = value;
      this.height = 1;
      this.left = null;
      this.right = null;
      this.parent = null;
    }

    @Override
    public KEY getKey() {
      return key;
    }

    @Override
    public VALUE getValue() {
      return value;
    }

    @Override
    public VALUE setValue(final VALUE value) {
      final VALUE old = this.value;
      this.value = value;
      return old;
    }

    Node next() {
      if (right != null) {
        Node node = right;
        while (node.left != null) {
          node = node.left;
        }
        return node;
      } else {
        Node par = parent;
        Node cur = this;
        while ((par != null) && (cur == par.right)) {
          cur = par;
          par = par.parent;
        }
        return par;
      }
    }

    Node previous() {
      if (left != null) {
        Node node = left;
        while (node.right != null) {
          node = node.right;
        }
        return node;
      } else {
        Node par = parent;
        Node cur = this;
        while ((par != null) && (cur == par.left)) {
          cur = par;
          par = par.parent;
        }
        return par;
      }
    }
  }

  private Node                  root;
  private final Comparator<KEY> comparator;
  private int                   size;
  private transient int         modifications;

  public AvlTreeMap() {
    root = null;
    comparator = null;
    size = 0;
    modifications = 0;
  }

  public AvlTreeMap(final Comparator<KEY> comparator) {
    this.root = null;
    this.comparator = requireNonNull("comparator", comparator);
    this.size = 0;
    this.modifications = 0;
  }

  @Override
  public boolean isEmpty() {
    return (size == 0);
  }

  @Override
  public int size() {
    return size;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean containsKey(final Object key) {
    requireNonNull("key", key);
    Node node;
    if (comparator == null) {
      node = getNode((KEY) key);
    } else {
      node = getNodeByComparator((KEY) key);
    }
    return (node != null);
  }

  @Override
  public boolean containsValue(final Object value) {
    Node node = getFirstNode();
    while (node != null) {
      if (Equality.equals(node.value, value)) {
        return true;
      }
      node = node.next();
    }
    return false;
  }

  protected final Node getFirstNode() {
    if (root == null) {
      return null;
    } else {
      Node node = root;
      while (node.left != null) {
        node = node.left;
      }
      return node;
    }
  }

  protected final Node getLastNode() {
    if (root == null) {
      return null;
    } else {
      Node node = root;
      while (node.right != null) {
        node = node.right;
      }
      return node;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public VALUE get(final Object key) {
    requireNonNull("key", key);
    Node node;
    if (comparator == null) {
      node = getNode((KEY) key);
    } else {
      node = getNodeByComparator((KEY) key);
    }
    if (node == null) {
      return null;
    } else {
      return node.value;
    }
  }

  @SuppressWarnings("unchecked")
  protected final Node getNode(final KEY key) {
    Node node = root;
    while (node != null) {
      // compare the key and node.key
      final int rc = ((Comparable<KEY>)key).compareTo(node.key);
      if (rc == 0) {
        return node;        // found the value
      } else if (rc < 0) {
        node = node.left;   // search in the left subtree
      } else {
        node = node.right;  // search in the right subtree
      }
    }
    return null;
  }

  protected final Node getNodeByComparator(final KEY key) {
    Node node = root;
    while (node != null) {
      // compare the key and node.key
      final int rc = comparator.compare(key, node.key);
      if (rc == 0) {
        return node;        // found the value
      } else if (rc < 0) {
        node = node.left;   // search in the left subtree
      } else {
        node = node.right;  // search in the right subtree
      }
    }
    return null;
  }

  @Override
  public VALUE put(final KEY key, final VALUE value) {
    requireNonNull("key", key);
    final Pair<VALUE, Node> result = insert(key, value, root);
    root = result.second;
    return result.first;
  }

  @SuppressWarnings("unchecked")
  protected final Pair<VALUE, Node> insert(final KEY key, final VALUE value,
      final Node node) {
    if (node == null) {
      final Pair<VALUE, Node> result = new Pair<VALUE, Node>();
      result.first = null;
      result.second = new Node(key, value);
      ++size;
      ++modifications;
      return result;
    } else {
      int rc;
      if (comparator == null) {
        rc = ((Comparable<KEY>)key).compareTo(node.key);
      } else {
        rc = comparator.compare(key, node.key);
      }
      if (rc == 0) { // found the key
        final Pair<VALUE, Node> result = new Pair<VALUE, Node>();
        result.first = node.value;
        result.second = node;
        node.value = value; // replace the old value
        return result;
      } else if (rc < 0) {
        final Pair<VALUE, Node> result = insert(key, value, node.left);
        result.second = rebalance(node, result.second, node.right);
        return result;
      } else { // rc > 0
        final Pair<VALUE, Node> result = insert(key, value, node.right);
        result.second = rebalance(node, node.left, result.second);
        return result;
      }
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public VALUE remove(final Object key) {
    requireNonNull("key", key);
    final Pair<Node, Node> result = delete((KEY) key, root);
    root = result.second;
    if (result.first != null) {
      return result.first.value;
    } else {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  protected final Pair<Node, Node> delete(final KEY key, final Node node) {
    if (node == null) {
      final Pair<Node, Node> result = new Pair<Node, Node>();
      return result;
    }
    int rc;
    if (comparator == null) {
      rc = ((Comparable<KEY>)key).compareTo(node.key);
    } else {
      rc = comparator.compare(key, node.key);
    }
    final Pair<Node, Node> result;
    if (rc == 0) {  // found the key
      if (node.left == null) {
        // delete the node itself and keep its right subtree as the remained subtree
        result = new Pair<Node, Node>();
        result.second = node.right;
      } else {
        // delete the largest element in the left subtree
        result = deleteLargest(node.left);
        // re-balance the remained subtree
        result.second = rebalance(result.first, result.second, node.right);
      }
      // delete the node itself
      result.first = node;
      --size;
      ++modifications;
    } else if (rc < 0) {
      // delete the key in the left subtree
      result = delete(key, node.left);
      if (result.first != null) {
        // re-balance the remained subtree
        result.second = rebalance(node, result.second, node.right);
      } else {  // nothing changed
        result.second = node;
      }
    } else {
      // delete the key in the right subtree
      result = delete(key, node.right);
      if (result.first != null) {
        // re-balance the remained subtree
        result.second = rebalance(node, node.left, result.second);
      } else {  // nothing changed
        result.second = node;
      }
    }
    return result;
  }

  /*
   * Deletes the largest node form the subtree rooted at the specified node.
   * Returns a pair of nodes, with the first element be the node deleted, and
   * the second element be the new subtree after deletion.
   */
  protected final Pair<Node, Node> deleteLargest(final Node node) {
    if (node.right == null) {
      // delete the node itself, and keep the left subtree as the new subtree
      final Pair<Node, Node> result = new Pair<Node, Node>();
      result.first = node;
      result.second = node.left;
      return result;
    } else {
      // recursively delete the largest node in the right subtree
      final Pair<Node, Node> result = deleteLargest(node.right);
      // re-balance the new subtree
      result.second = rebalance(node, node.left, result.second);
      return result;
    }
  }

  /*
   * Balance the tree of the form:
   *
   *                  t
   *                 / \
   *                tl  tr
   */
  protected final Node rebalance(final Node t, @Nullable final Node tl,
      @Nullable final Node tr) {
    final int hl = (tl == null ? 0 : tl.height);
    final int hr = (tr == null ? 0 : tr.height);
    if (hr <= hl + 1) {
      if (hl <= hr + 1) {
        // don't need to re-balance, just adjust the height
        t.height = (hl > hr ? hl : hr) + 1;
        t.left = tl;
        tl.parent = t;
        t.right = tr;
        tr.parent = t;
        return t;
      } else { // hl > hr + 1
        assert (hl == hr + 2);
        return rotateLeft(t, tl, hl, tr, hr);
      }
    } else { // hr > hl + 1
      assert (hr == hl + 2);
      return rotateRight(t, tl, hl, tr, hr);
    }
  }

  /*
   * Given a tree of the form
   *
   *                  t
   *                 / \
   *                tl  tr
   *
   * where height(tl) == height(tr) + 2, rotate the left subtree tl.
   */
  private final Node rotateLeft(final Node t, final Node tl, final int hl,
      @Nullable final Node tr, final int hr) {
    assert (t != null) && (tl != null) && (hl == hr + 2);
    final Node tll = tl.left;
    final Node tlr = tl.right;
    final int hll = (tll == null ? 0 : tll.height);
    final int hlr = (tlr == null ? 0 : tlr.height);
    if (hll >= hlr) {
      /*
       * The subtree is of the form
       *
       *                    t   <------- hr+3
       *                  /   \
       * hr+2 ------>   tl    tr   <---- hr
       *               /  \
       * hr+1 --->   tll  tlr   <------- hr
       *
       * where
       *    height(tr)  == hr
       *    height(tl)  == hr + 2
       *    height(tlr) == hr
       *    height(tll) == hr + 1
       *
       * Perform a single rotation to transform the subtree into the form
       *
       *                tl  <---------- hr+2
       *              /    \
       * hr+1 --->  tll     t  <------- hr+1
       *                  /   \
       *   hr --------> tlr    tr <---- hr
       */
      assert (hlr == hr) && (hll == hr + 1);
      t.height = hr + 1;
      t.left = tlr;
      if (tlr != null) {
        tlr.parent = t;
      }
      t.right = tr;
      if (tr != null) {
        tr.parent = t;
      }
      tl.height = hr + 2;
      tl.right = t;
      t.parent = tl;
      return tl;
    } else {    // hll < hlr
      /*
       * The subtree is of the form
       *
       *                    t   <------- hr+3
       *                  /   \
       * hr+2 ------>   tl    tr   <---- hr
       *               /  \
       * hr ----->   tll  tlr   <------- hr+1
       *                 /   \
       *               tlrl  tlrr
       *
       * where
       *    height(tl)  == hr + 2
       *    height(tll) == hr
       *    height(tlr) == hr + 1
       * and one of the following holds
       *    height(tlrl) == hr and height(tlrr) = hr - 1
       *  or
       *    height(tlrl) == hr and height(tlrr) = hr
       *  or
       *    height(tlrl) == hr - 1 and height(tlrr) = hr
       *
       * Perform a double rotation to transform the subtree into the form
       *
       *                            tlr   <---------------- hr+2
       *                             |
       *                      ------------------
       *                     /                  \
       * hr+1 --------->   tl                    t  <------ hr+1
       *                 /   \                 /    \
       *   hr -------> tll   tlrl           tlrr    tr <--- hr
       *
       *                       |             |
       *                  (hr or hr-1)  (hr or hr-1)
       *
       */
      assert (hll == hr) && (hlr == hr + 1);
      assert (tlr != null);
      final Node tlrl = tlr.left;
      final Node tlrr = tlr.right;
      t.height = hr + 1;
      t.left = tlrr;
      if (tlrr != null) {
        tlrr.parent = t;
      }
      t.right = tr;
      if (tr != null) {
        tr.parent = t;
      }
      tl.height = hr + 1;
      tl.right = tlrl;
      if (tlrl != null) {
        tlrl.parent = tl;
      }
      tlr.height = hr + 2;
      tlr.left = tl;
      tl.parent = tlr;
      tlr.right = t;
      t.parent = tlr;
      return tlr;
    }
  }

  /*
   * Given a tree of the form
   *
   *                  t
   *                 / \
   *                tl  tr
   *
   * where height(tr) == height(tl) + 2, rotate the right subtree tr.
   */
  private final Node rotateRight(final Node t, @Nullable final Node tl, final int hl,
      final Node tr, final int hr) {
    assert (t != null) && (tr != null) && (hr == hl + 2);
    final Node trl = tr.left;
    final Node trr = tr.right;
    final int hrl = (trl == null ? 0 : trl.height);
    final int hrr = (trr == null ? 0 : trr.height);
    if (hrl <= hrr) {
      /*
       * The subtree is of the form
       *
       *                  t   <-------------- hl+3
       *                /   \
       * hl ------>   tl      tr   <--------- hl+2
       *                     /  \
       * hl -------------> trl  trr  <------- hl+1
       *
       * where
       *    height(tl)  == hl
       *    height(tr)  == hl + 2
       *    height(trl) == hl
       *    height(trr) == hl + 1
       *
       * Perform a single rotation to transform the subtree into the form
       *
       *                 tr  <----------- hl+2
       *               /    \
       * hl+1 --->    t     trr  <------- hl+1
       *            /   \
       *   hl ---> tl   trl <------------ hl
       */
      assert (hrl == hl) && (hrr == hl + 1);
      t.height = hl + 1;
      t.left = tl;
      if (tl != null) {
        tl.parent = t;
      }
      t.right = trl;
      if (trl != null) {
        trl.parent = t;
      }
      tr.height = hl + 2;
      tr.left = t;
      t.parent = tr;
      return tr;
    } else {    //  hrl > hrr
      /*
       * The subtree is of the form
       *
       *                t   <------- hl+3
       *              /   \
       * hl   ---->  tl    tr   <---- hl+2
       *                 /  \
       * hl+1 -------> trl  trr <--- hl
       *              /   \
       *           trll   trlr
       *
       * where
       *    height(tl)  == hl
       *    height(tr)  == hl + 2
       *    height(trl) == hl + 1
       *    height(trr) == hl
       * and one of the following holds
       *    height(trll) == hl and height(trlr) = hl - 1
       *  or
       *    height(trll) == hl and height(trlr) = hl
       *  or
       *    height(trll) == hl - 1 and height(trlr) = hl
       *
       * Perform a double rotation to transform the subtree into the form
       *
       *                            trl   <---------------- hl+2
       *                             |
       *                      ------------------
       *                     /                  \
       * hl+1 --------->   t                    tr  <------ hl+1
       *                 /   \                 /   \
       *   hl -------> tl   trll           trlr    trr <--- hl
       *
       *                       |             |
       *                  (hl or hl-1)  (hl or hl-1)
       *
       */
      assert (hrl == hl + 1) && (hrr == hl);
      assert (trl != null);
      final Node trll = trl.left;
      final Node trlr = trl.right;
      t.height = hl + 1;
      t.left = tl;
      if (tl != null) {
        tl.parent = t;
      }
      t.right = trll;
      if (trll != null) {
        trll.parent = t;
      }
      tr.height = hl + 1;
      tr.left = trlr;
      if (trlr != null) {
        trlr.parent = tr;
      }
      trl.height = hl + 2;
      trl.left = t;
      t.parent = trl;
      trl.right = tr;
      tr.parent = trl;
      return trl;
    }
  }

  @Override
  public void putAll(final Map<? extends KEY, ? extends VALUE> map) {
    for (final Map.Entry<? extends KEY, ? extends VALUE> entry : map.entrySet()) {
      final KEY key = entry.getKey();
      final VALUE value = entry.getValue();
      final Pair<VALUE, Node> result = insert(key, value, root);
      root = result.second;
    }
  }

  @Override
  public void clear() {
    root = null;
    size = 0;
  }

  @Override
  public Set<KEY> keySet() {
    return new KeySet();
  }

  @Override
  public Collection<VALUE> values() {
    return new Values();
  }

  @Override
  public Set<Map.Entry<KEY, VALUE>> entrySet() {
    return new EntrySet();
  }

  abstract class PrivateIterator<T> implements Iterator<T> {

    private Node next;
    private Node lastReturned;
    private int expectedModifications;

    public PrivateIterator(final Node node) {
      this.next = node;
      this.lastReturned = null;
      this.expectedModifications = AvlTreeMap.this.modifications;
    }

    @Override
    public boolean hasNext() {
      return (next != null);
    }

    public Node nextEntry() {
      if (next == null) {
        throw new NoSuchElementException();
      }
      if (AvlTreeMap.this.modifications != expectedModifications) {
        throw new ConcurrentModificationException();
      }
      final Node node = next;
      next = node.next();
      lastReturned = node;
      return node;
    }

    public Node prevEntry() {
      if (next == null) {
        throw new NoSuchElementException();
      }
      if (AvlTreeMap.this.modifications != expectedModifications) {
        throw new ConcurrentModificationException();
      }
      final Node node = next;
      next = node.previous();
      lastReturned = node;
      return node;
    }

    @Override
    public void remove() {
      if (lastReturned == null) {
        throw new IllegalStateException();
      }
      if (AvlTreeMap.this.modifications != expectedModifications) {
        throw new ConcurrentModificationException();
      }
      AvlTreeMap.this.remove(lastReturned.key);
      expectedModifications = AvlTreeMap.this.modifications;
      lastReturned = null;
    }
  }

  final class KeyIterator extends PrivateIterator<KEY> {

    public KeyIterator(final Node node) {
      super(node);
    }

    @Override
    public KEY next() {
      return super.nextEntry().key;
    }
  }

  final class KeySet extends AbstractSet<KEY> {

    @Override
    public Iterator<KEY> iterator() {
      final Node first = AvlTreeMap.this.getFirstNode();
      return new KeyIterator(first);
    }

    @Override
    public int size() {
      return AvlTreeMap.this.size();
    }

    @Override
    public boolean isEmpty() {
      return AvlTreeMap.this.isEmpty();
    }
  }

  final class ValueIterator extends PrivateIterator<VALUE> {

    public ValueIterator(final Node node) {
      super(node);
    }

    @Override
    public VALUE next() {
      return super.nextEntry().value;
    }
  }

  final class Values extends AbstractCollection<VALUE> {

    @Override
    public Iterator<VALUE> iterator() {
      final Node first = AvlTreeMap.this.getFirstNode();
      return new ValueIterator(first);
    }

    @Override
    public int size() {
      return AvlTreeMap.this.size();
    }

    @Override
    public boolean isEmpty() {
      return AvlTreeMap.this.isEmpty();
    }

    @Override
    public boolean contains(final Object obj) {
      return AvlTreeMap.this.containsValue(obj);
    }

    @Override
    public boolean remove(final Object obj) {
      Node node = AvlTreeMap.this.getFirstNode();
      while (node != null) {
        if (Equality.equals(obj, node.value)) {
          AvlTreeMap.this.remove(node.key);
          return true;
        }
        node = node.next();
      }
      return false;
    }

    @Override
    public void clear() {
      AvlTreeMap.this.clear();
    }
  }

  final class EntryIterator extends PrivateIterator<Map.Entry<KEY, VALUE>> {

    public EntryIterator(final Node node) {
      super(node);
    }

    @Override
    public Map.Entry<KEY, VALUE> next() {
      return super.nextEntry();
    }
  }

  final class EntrySet extends AbstractSet<Map.Entry<KEY, VALUE>> {

    @Override
    public int size() {
      return AvlTreeMap.this.size();
    }

    @Override
    public boolean isEmpty() {
      return AvlTreeMap.this.isEmpty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(final Object obj) {
      if (!(obj instanceof Map.Entry)) {
        return false;
      }
      final Map.Entry<KEY, VALUE> entry = (Map.Entry<KEY, VALUE>) obj;
      final KEY key = entry.getKey();
      final VALUE value = entry.getValue();
      final Node node = AvlTreeMap.this.getNode(key);
      if (node != null) {
        return Equality.equals(value, node.value);
      } else {
        return false;
      }
    }

    @Override
    public Iterator<Map.Entry<KEY, VALUE>> iterator() {
      final Node first = AvlTreeMap.this.getFirstNode();
      return new EntryIterator(first);
    }

    @Override
    public boolean add(final Map.Entry<KEY, VALUE> entry) {
      final KEY key = entry.getKey();
      final VALUE value = entry.getValue();
      final int oldSize = AvlTreeMap.this.size();
      AvlTreeMap.this.put(key, value);
      final int newSize = AvlTreeMap.this.size();
      return (oldSize < newSize);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(final Object obj) {
      if (!(obj instanceof Map.Entry)) {
        return false;
      }
      final Map.Entry<KEY, VALUE> entry = (Map.Entry<KEY, VALUE>) obj;
      final KEY key = entry.getKey();
      final VALUE value = entry.getValue();
      final Node node = AvlTreeMap.this.getNode(key);
      if (node != null) {
        if (Equality.equals(value, node.value)) {
          AvlTreeMap.this.remove(key);
          return true;
        }
      }
      return false;
    }

    @Override
    public void clear() {
      AvlTreeMap.this.clear();
    }
  }

}
