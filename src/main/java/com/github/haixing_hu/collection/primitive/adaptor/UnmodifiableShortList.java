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

import com.github.haixing_hu.collection.primitive.AbstractShortCollection;
import com.github.haixing_hu.collection.primitive.ShortCollection;
import com.github.haixing_hu.collection.primitive.ShortIterator;
import com.github.haixing_hu.collection.primitive.ShortList;
import com.github.haixing_hu.collection.primitive.ShortListIterator;

/**
 * An unmodifiable version of {@link ShortList}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableShortList extends AbstractShortCollection
    implements ShortList {

  /**
   * Wraps a {@link ShortList} as an {@link UnmodifiableShortList}.
   *
   * @param iterator
   *          the {@link ShortList} to be wrap.
   * @return an {@link UnmodifiableShortList} wrapping the specified
   *         collection.
   */
  public static UnmodifiableShortList wrap(final ShortList list) {
    if (list instanceof UnmodifiableShortList) {
      return (UnmodifiableShortList) list;
    } else {
      return new UnmodifiableShortList(list);
    }
  }

  private final ShortList list;

  protected UnmodifiableShortList(final ShortList list) {
    this.list = requireNonNull("list", list);
  }

  @Override
  public boolean addAll(final ShortCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final short element) {
    return list.contains(element);
  }

  @Override
  public boolean containsAll(final ShortCollection c) {
    return list.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public boolean removeAll(final ShortCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeElement(final short element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final ShortCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public short[] toArray() {
    return list.toArray();
  }

  @Override
  public short[] toArray(final short[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean add(final short element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(final int index, final short element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final int index, final ShortCollection collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public short get(final int index) {
    return list.get(index);
  }

  @Override
  public int indexOf(final short element) {
    return list.indexOf(element);
  }

  @Override
  public ShortIterator iterator() {
    return new UnmodifiableShortIterator(list.iterator());
  }

  @Override
  public int lastIndexOf(final short element) {
    return list.lastIndexOf(element);
  }

  @Override
  public ShortListIterator listIterator() {
    return new UnmodifiableShortListIterator(list.listIterator());
  }

  @Override
  public ShortListIterator listIterator(final int index) {
    return new UnmodifiableShortListIterator(list.listIterator(index));
  }

  @Override
  public short removeElementAt(final int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public short set(final int index, final short element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ShortList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableShortList(list.subList(fromIndex, toIndex));
  }
}
