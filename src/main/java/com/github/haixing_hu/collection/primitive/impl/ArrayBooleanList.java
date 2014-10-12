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
package com.github.haixing_hu.collection.primitive.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.github.haixing_hu.collection.primitive.BooleanCollection;
import com.github.haixing_hu.collection.primitive.BooleanIterator;
import com.github.haixing_hu.collection.primitive.BooleanList;
import com.github.haixing_hu.collection.primitive.RandomAccessBooleanList;

import static com.github.haixing_hu.lang.Argument.*;

/**
 * An {@link BooleanList} backed by an array of {@code boolean}s. This
 * implementation supports all optional methods.
 *
 * @author Haixing Hu
 */
public class ArrayBooleanList extends RandomAccessBooleanList implements
    BooleanList, Serializable {

  private static final long serialVersionUID = 4991972645899316018L;

  private transient boolean[] data;
  private int size;

  /**
   * Construct an empty list with the default initial capacity.
   */
  public ArrayBooleanList() {
    this(8);
  }

  /**
   * Construct an empty list with the given initial capacity.
   *
   * @throws IllegalArgumentException
   *           when <i>initialCapacity</i> is negative
   */
  public ArrayBooleanList(final int initialCapacity) {
    if (initialCapacity < 0) {
      throw new IllegalArgumentException("capacity " + initialCapacity);
    }
    data = new boolean[initialCapacity];
    size = 0;
  }

  /**
   * Constructs a list containing the elements of the given collection, in the
   * order they are returned by that collection's iterator.
   *
   * @see ArrayBooleanList#addAll(BooleanCollection)
   * @param that
   *          the non-{@code null} collection of {@code boolean}s to
   *          add
   * @throws NullPointerException
   *           if <i>that</i> is {@code null}
   */
  public ArrayBooleanList(final BooleanCollection that) {
    this(that.size());
    addAll(that);
  }

  /**
   * Constructs a list by copying the specified array.
   *
   * @param array
   *          the array to initialize the collection with
   * @throws NullPointerException
   *           if the array is {@code null}
   */
  public ArrayBooleanList(final boolean[] array) {
    this(array.length);
    System.arraycopy(array, 0, data, 0, array.length);
    size = array.length;
  }

  // BooleanList methods
  // -------------------------------------------------------------------------

  @Override
  public boolean get(final int index) {
    requireIndexInRightOpenRange(index, 0, size);
    return data[index];
  }

  @Override
  public int size() {
    return size;
  }

  /**
   * Removes the element at the specified position in (optional operation). Any
   * subsequent elements are shifted to the left, subtracting one from their
   * indices. Returns the element that was removed.
   *
   * @param current
   *          the current of the element to remove
   * @return the value of the element that was removed
   * @throws UnsupportedOperationException
   *           when this operation is not supported
   * @throws IndexOutOfBoundsException
   *           if the specified current is out of range
   */
  @Override
  public boolean removeElementAt(final int index) {
    requireIndexInRightOpenRange(index, 0, size);
    ++modCount;
    final boolean oldval = data[index];
    final int numtomove = size - index - 1;
    if (numtomove > 0) {
      System.arraycopy(data, index + 1, data, index, numtomove);
    }
    size--;
    return oldval;
  }

  /**
   * Replaces the element at the specified position in me with the specified
   * element (optional operation).
   *
   * @param current
   *          the current of the element to change
   * @param element
   *          the value to be stored at the specified position
   * @return the value previously stored at the specified position
   * @throws UnsupportedOperationException
   *           when this operation is not supported
   * @throws IndexOutOfBoundsException
   *           if the specified current is out of range
   */
  @Override
  public boolean set(final int index, final boolean element) {
    requireIndexInRightOpenRange(index, 0, size);
    ++modCount;
    final boolean oldval = data[index];
    data[index] = element;
    return oldval;
  }

  /**
   * Inserts the specified element at the specified position (optional
   * operation). Shifts the element currently at that position (if any) and any
   * subsequent elements to the right, increasing their indices.
   *
   * @param current
   *          the current at which to insert the element
   * @param element
   *          the value to insert
   * @throws UnsupportedOperationException
   *           when this operation is not supported
   * @throws IllegalArgumentException
   *           if some aspect of the specified element prevents it from being
   *           added to me
   * @throws IndexOutOfBoundsException
   *           if the specified current is out of range
   */
  @Override
  public void add(final int index, final boolean element) {
    requireIndexInCloseRange(index, 0, size);
    ++modCount;
    ensureCapacity(size + 1);
    final int numtomove = size - index;
    System.arraycopy(data, index, data, index + 1, numtomove);
    data[index] = element;
    size++;
  }

  @Override
  public void clear() {
    ++modCount;
    size = 0;
  }

  @Override
  public boolean addAll(final BooleanCollection collection) {
    return addAll(size(), collection);
  }

  @Override
  public boolean addAll(int index, final BooleanCollection collection) {
    if (collection.size() == 0) {
      return false;
    }
    requireIndexInCloseRange(index, 0, size);
    ++modCount;
    ensureCapacity(size + collection.size());
    if (index != size) {
      // Need to move some elements
      System.arraycopy(data, index, data, index + collection.size(), size - index);
    }
    for (final BooleanIterator it = collection.iterator(); it.hasNext();) {
      data[index] = it.next();
      index++;
    }
    size += collection.size();
    return true;
  }

  // capacity methods
  // -------------------------------------------------------------------------

  /**
   * Increases my capacity, if necessary, to ensure that I can hold at least the
   * number of elements specified by the minimum capacity argument without
   * growing.
   */
  public void ensureCapacity(final int mincap) {
    ++modCount;
    if (mincap > data.length) {
      final int newcap = ((data.length * 3) / 2) + 1;
      final boolean[] olddata = data;
      data = new boolean[newcap < mincap ? mincap : newcap];
      System.arraycopy(olddata, 0, data, 0, size);
    }
  }

  /**
   * Reduce my capacity, if necessary, to match my current {@link #size size}.
   */
  public void trimToSize() {
    ++modCount;
    if (size < data.length) {
      final boolean[] olddata = data;
      data = new boolean[size];
      System.arraycopy(olddata, 0, data, 0, size);
    }
  }

  private void writeObject(final ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();
    out.writeInt(data.length);
    for (int i = 0; i < size; i++) {
      out.writeBoolean(data[i]);
    }
  }

  private void readObject(final ObjectInputStream in) throws IOException,
      ClassNotFoundException {
    in.defaultReadObject();
    data = new boolean[in.readInt()];
    for (int i = 0; i < size; i++) {
      data[i] = in.readBoolean();
    }
  }
}
