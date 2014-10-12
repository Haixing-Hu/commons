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

import com.github.haixing_hu.collection.primitive.AbstractByteCollection;
import com.github.haixing_hu.collection.primitive.ByteCollection;
import com.github.haixing_hu.collection.primitive.ByteIterator;
import com.github.haixing_hu.collection.primitive.ByteList;
import com.github.haixing_hu.collection.primitive.ByteListIterator;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link ByteList}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableByteList extends AbstractByteCollection
    implements ByteList {

  /**
   * Wraps a {@link ByteList} as an {@link UnmodifiableByteList}.
   *
   * @param iterator
   *          the {@link ByteList} to be wrap.
   * @return an {@link UnmodifiableByteList} wrapping the specified
   *         collection.
   */
  public static UnmodifiableByteList wrap(final ByteList list) {
    if (list instanceof UnmodifiableByteList) {
      return (UnmodifiableByteList) list;
    } else {
      return new UnmodifiableByteList(list);
    }
  }

  private final ByteList list;

  protected UnmodifiableByteList(final ByteList list) {
    this.list = requireNonNull("list", list);
  }

  @Override
  public boolean addAll(final ByteCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final byte element) {
    return list.contains(element);
  }

  @Override
  public boolean containsAll(final ByteCollection c) {
    return list.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public boolean removeAll(final ByteCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeElement(final byte element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final ByteCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public byte[] toArray() {
    return list.toArray();
  }

  @Override
  public byte[] toArray(final byte[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean add(final byte element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(final int index, final byte element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final int index, final ByteCollection collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public byte get(final int index) {
    return list.get(index);
  }

  @Override
  public int indexOf(final byte element) {
    return list.indexOf(element);
  }

  @Override
  public ByteIterator iterator() {
    return new UnmodifiableByteIterator(list.iterator());
  }

  @Override
  public int lastIndexOf(final byte element) {
    return list.lastIndexOf(element);
  }

  @Override
  public ByteListIterator listIterator() {
    return new UnmodifiableByteListIterator(list.listIterator());
  }

  @Override
  public ByteListIterator listIterator(final int index) {
    return new UnmodifiableByteListIterator(list.listIterator(index));
  }

  @Override
  public byte removeElementAt(final int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public byte set(final int index, final byte element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ByteList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableByteList(list.subList(fromIndex, toIndex));
  }
}
