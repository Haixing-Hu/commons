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
package com.github.haixing_hu.collection.primitive.adaptor;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

import com.github.haixing_hu.collection.primitive.AbstractLongCollection;
import com.github.haixing_hu.collection.primitive.LongCollection;
import com.github.haixing_hu.collection.primitive.LongIterator;
import com.github.haixing_hu.collection.primitive.LongList;
import com.github.haixing_hu.collection.primitive.LongListIterator;

/**
 * An unmodifiable version of {@link LongList}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableLongList extends AbstractLongCollection
    implements LongList {

  /**
   * Wraps a {@link LongList} as an {@link UnmodifiableLongList}.
   *
   * @param iterator
   *          the {@link LongList} to be wrap.
   * @return an {@link UnmodifiableLongList} wrapping the specified
   *         collection.
   */
  public static UnmodifiableLongList wrap(final LongList list) {
    if (list instanceof UnmodifiableLongList) {
      return (UnmodifiableLongList) list;
    } else {
      return new UnmodifiableLongList(list);
    }
  }

  private final LongList list;

  protected UnmodifiableLongList(final LongList list) {
    this.list = requireNonNull("list", list);
  }

  @Override
  public boolean addAll(final LongCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final long element) {
    return list.contains(element);
  }

  @Override
  public boolean containsAll(final LongCollection c) {
    return list.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public boolean removeAll(final LongCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeElement(final long element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final LongCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public long[] toArray() {
    return list.toArray();
  }

  @Override
  public long[] toArray(final long[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean add(final long element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(final int index, final long element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final int index, final LongCollection collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public long get(final int index) {
    return list.get(index);
  }

  @Override
  public int indexOf(final long element) {
    return list.indexOf(element);
  }

  @Override
  public LongIterator iterator() {
    return new UnmodifiableLongIterator(list.iterator());
  }

  @Override
  public int lastIndexOf(final long element) {
    return list.lastIndexOf(element);
  }

  @Override
  public LongListIterator listIterator() {
    return new UnmodifiableLongListIterator(list.listIterator());
  }

  @Override
  public LongListIterator listIterator(final int index) {
    return new UnmodifiableLongListIterator(list.listIterator(index));
  }

  @Override
  public long removeElementAt(final int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public long set(final int index, final long element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public LongList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableLongList(list.subList(fromIndex, toIndex));
  }
}
