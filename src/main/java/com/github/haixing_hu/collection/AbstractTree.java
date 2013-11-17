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

package com.github.haixing_hu.collection;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * An abstract tree structure.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public abstract class AbstractTree<KEY, VALUE> implements Tree<KEY, VALUE> {

  protected KEY key;
  protected VALUE value;

  protected AbstractTree() {
    this.key = null;
    this.value = null;
  }

  protected AbstractTree(KEY key, VALUE value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public boolean isLeaf() {
    return getChildrenCount() == 0;
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
  public void setValue(VALUE value) {
    this.value = value;
  }

  @Override
  public boolean containsChild(KEY key) {
    return getChild(key) != null;
  }

  @Override
  public abstract int getChildrenCount();

  @Override
  public abstract Collection<Tree<KEY, VALUE>> getChildren();

  @Override
  public abstract Tree<KEY, VALUE> getChild(KEY key);

  @Override
  public abstract Tree<KEY, VALUE> addChild(Tree<KEY, VALUE> child);

  @Override
  public abstract Tree<KEY, VALUE> removeChild(KEY key);

  @Override
  public abstract int clearChildren();

  @Override
  public boolean isValid() {
    Set<KEY> visited = new HashSet<KEY>();
    return checkValidity(visited);
  }

  private boolean checkValidity(Set<KEY> visited) {
    // Recursively check the validity of this node: a node is valid if and only if
    // there is no cycle nor bridge between the descendants of the node.
    // Note that the time complexity of this algorithm is O(n), where n is the
    // size of the subtree of this node.
    if (visited.contains(key)) {
      return false;
    }
    visited.add(key);
    Collection<Tree<KEY, VALUE>> children = getChildren();
    if ((children == null) || children.isEmpty()) {
      return true;
    }
    for (Tree<KEY, VALUE> child : children) {
      if (! ((AbstractTree<KEY, VALUE>) child).checkValidity(visited)) {
        return false;
      }
    }
    return true;
  }
}
