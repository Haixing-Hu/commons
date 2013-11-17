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

package com.github.haixing_hu.collection.primitive.adaptor;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

import com.github.haixing_hu.collection.primitive.AbstractIntCollection;
import com.github.haixing_hu.collection.primitive.IntCollection;
import com.github.haixing_hu.collection.primitive.IntIterator;
import com.github.haixing_hu.collection.primitive.IntList;
import com.github.haixing_hu.collection.primitive.IntListIterator;

/**
 * An unmodifiable version of {@link IntList}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableIntList extends AbstractIntCollection
    implements IntList {

  /**
   * Wraps a {@link IntList} as an {@link UnmodifiableIntList}.
   *
   * @param iterator
   *          the {@link IntList} to be wrap.
   * @return an {@link UnmodifiableIntList} wrapping the specified
   *         collection.
   */
  public static UnmodifiableIntList wrap(final IntList list) {
    if (list instanceof UnmodifiableIntList) {
      return (UnmodifiableIntList) list;
    } else {
      return new UnmodifiableIntList(list);
    }
  }

  private final IntList list;

  protected UnmodifiableIntList(final IntList list) {
    this.list = requireNonNull("list", list);
  }

  @Override
  public boolean addAll(final IntCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final int element) {
    return list.contains(element);
  }

  @Override
  public boolean containsAll(final IntCollection c) {
    return list.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public boolean removeAll(final IntCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeElement(final int element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final IntCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public int[] toArray() {
    return list.toArray();
  }

  @Override
  public int[] toArray(final int[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean add(final int element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(final int index, final int element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final int index, final IntCollection collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int get(final int index) {
    return list.get(index);
  }

  @Override
  public int indexOf(final int element) {
    return list.indexOf(element);
  }

  @Override
  public IntIterator iterator() {
    return new UnmodifiableIntIterator(list.iterator());
  }

  @Override
  public int lastIndexOf(final int element) {
    return list.lastIndexOf(element);
  }

  @Override
  public IntListIterator listIterator() {
    return new UnmodifiableIntListIterator(list.listIterator());
  }

  @Override
  public IntListIterator listIterator(final int index) {
    return new UnmodifiableIntListIterator(list.listIterator(index));
  }

  @Override
  public int removeElementAt(final int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int set(final int index, final int element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public IntList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableIntList(list.subList(fromIndex, toIndex));
  }
}
