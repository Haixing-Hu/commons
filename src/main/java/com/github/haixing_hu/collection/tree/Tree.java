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

package com.github.haixing_hu.collection.tree;

import java.util.Collection;

/**
 * The interface of the tree structure.
 *
 * @param <KEY>
 *          the type of the keys of tree nodes.
 * @param <VALUE>
 *          the type of the values of tree nodes.
 * @author Haixing Hu
 */
public interface Tree<KEY, VALUE> {

  /**
   * Tests whether this tree node is a leaf, i.e., has no children.
   *
   * @return true if this tree node is a leaf, false otherwise.
   */
  public boolean isLeaf();

  /**
   * Gets the key of this tree node.
   *
   * @return the key of this tree node.
   */
  public KEY getKey();

  /**
   * Gets the value of this tree node.
   *
   * @return the value of this tree node.
   */
  public VALUE getValue();

  /**
   * Sets the value of this tree node.
   *
   * @param value
   *          the new value to be set.
   */
  public void setValue(VALUE value);

  /**
   * Tests whether the tree node contains a child of the specified key.
   *
   * @param key
   *          a specified key, which could be null.
   * @return true if the tree node contains a child of the specified key, false
   *         otherwise.
   */
  public boolean containsChild(KEY key);

  /**
   * Gets the amount of children of this tree node.
   *
   * @return the amount of children of this tree node.
   */
  public int getChildrenCount();

  /**
   * Gets the collection of children node of this tree node.
   *
   * @return the collection of children node of this tree node.
   */
  public Collection<Tree<KEY, VALUE>> getChildren();

  /**
   * Gets the child of this tree node with the specified key.
   *
   * @param key
   *          a specified key, which could be null.
   * @return the child of this tree node with the specified key, or null if this
   *         tree node has no child with the specified key.
   */
  public Tree<KEY, VALUE> getChild(KEY key);

  /**
   * Adds a child node to this tree node.
   *
   * @param child
   *          the child node to be added.
   * @return if this tree node already has a child with the same key as the new
   *         child node, this function returns the old node, and use the new
   *         child node to replace the old node; otherwise, this function add
   *         the new child node to the children of this tree node, and returns
   *         null.
   * @throws NullPointerException
   *           if child is null.
   * @throws IllegalArgumentException
   *           if this == child.
   */
  public Tree<KEY, VALUE> addChild(Tree<KEY, VALUE> child);

  /**
   * Removes the child node of this tree node with the specified key.
   *
   * @param key
   *          a specified key, which could be null.
   * @return if this tree node has a child with the specified key, this function
   *         will remove that child node from the children of this node, and
   *         returns the removed child node; otherwise, this function returns
   *         null.
   */
  public Tree<KEY, VALUE> removeChild(KEY key);

  /**
   * Removes all the children node of this tree node.
   *
   * @return the total number of children nodes removed from this tree node.
   */
  public int clearChildren();

  /**
   * Tests whether this node is valid. A node is valid if and only if there is
   * NO cycle NOR bridge between the descendants of the node.
   *
   * @return true if this node is valid, false otherwise.
   */
  public boolean isValid();

}
