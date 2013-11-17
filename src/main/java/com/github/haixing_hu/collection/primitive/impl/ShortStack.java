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

package com.github.haixing_hu.collection.primitive.impl;

import java.util.EmptyStackException;


/**
 * A primitive short based Stack. The underlying backing store is an
 * {@link ArrayShortList} where the front of the list is the bottom of the stack
 * and the tail of the list is the top of the stack.
 *
 * @author Haixing Hu
 */
public class ShortStack {
  /** the underlying dynamic primitive backing store */
  private final ArrayShortList list = new ArrayShortList();

  /**
   * Creates an empty primitive stack.
   */
  public ShortStack() {
  }

  /**
   * Creates a stack prepopulating it with values.
   *
   * @param numbas
   *          the array to add
   */
  public ShortStack(final short[] numbas) {
    for (final short numba : numbas) {
      list.add(numba);
    }
  }

  /**
   * Tests if this stack is empty.
   *
   * @return true if and only if this stack is empty; false otherwise
   */
  public boolean empty() {
    return list.isEmpty();
  }

  /**
   * Looks at the top of this stack without removing it.
   *
   * @return the value at the top of this stack
   * @throws java.util.EmptyStackException
   *           if this stack is empty
   */
  public short peek() {
    if (list.isEmpty()) {
      throw new EmptyStackException();
    }

    return list.get(list.size() - 1);
  }

  /**
   * Return the n'th short down the stack, where 0 is the top element and
   * [size()-1] is the bottom element.
   *
   * @param n
   *          the element current
   * @return the element at the current
   * @throws EmptyStackException
   *           if the stack is empty
   * @throws IndexOutOfBoundsException
   *           if the current is out of bounds
   */
  public short peek(final int n) {
    if (list.isEmpty()) {
      throw new EmptyStackException();
    }

    return list.get(list.size() - n - 1);
  }

  /**
   * Removes the value at the top of this stack and returns it.
   *
   * @return value at the top of this stack
   * @throws java.util.EmptyStackException
   *           if this stack is empty
   */
  public short pop() {
    if (list.isEmpty()) {
      throw new EmptyStackException();
    }

    return list.removeElementAt(list.size() - 1);
  }

  /**
   * Pushes a value onto the top of this stack.
   *
   * @param item
   *          the value to push onto this stack
   * @return the item argument for call chaining
   */
  public short push(final short item) {
    list.add(item);
    return item;
  }

  /**
   * Returns the 1-based position where a value is on this stack. If the value
   * occurs as an item in this stack, this method returns the distance from the
   * top of the stack of the occurrence nearest the top of the stack; the
   * topmost item on the stack is considered to be at distance 1.
   *
   * @param item
   *          the value to search for from the top down
   * @return the 1-based position from the top of the stack where the int is
   *         located; the return value -1 indicates that the int is not on the
   *         stack
   */
  public int search(final short item) {
    for (int ii = list.size() - 1; ii >= 0; ii--) {
      if (list.get(ii) == item) {
        return list.size() - ii;
      }
    }

    return - 1;
  }

  /**
   * Gets items from the stack where the current is zero based and the top of the
   * stack is at an current of size()-1 with the bottom of the stack at an current
   * of 0.
   *
   * @param current
   *          the current into the stack treated as a list
   * @return the value at the current
   */
  public short get(final int index) {
    return list.get(index);
  }

  /**
   * Gets the size of this stack.
   *
   * @return the size of this stack
   */
  public int size() {
    return list.size();
  }

  /**
   * Empties the contents of the stack.
   */
  public void clear() {
    list.clear();
  }
}
