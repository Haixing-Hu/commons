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

import com.github.haixing_hu.collection.primitive.AbstractCharCollection;
import com.github.haixing_hu.collection.primitive.CharCollection;
import com.github.haixing_hu.collection.primitive.CharIterator;
import com.github.haixing_hu.collection.primitive.CharList;
import com.github.haixing_hu.collection.primitive.CharListIterator;

/**
 * An unmodifiable version of {@link CharList}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableCharList extends AbstractCharCollection
    implements CharList {

  /**
   * Wraps a {@link CharList} as an {@link UnmodifiableCharList}.
   *
   * @param iterator
   *          the {@link CharList} to be wrap.
   * @return an {@link UnmodifiableCharList} wrapping the specified
   *         collection.
   */
  public static UnmodifiableCharList wrap(final CharList list) {
    if (list instanceof UnmodifiableCharList) {
      return (UnmodifiableCharList) list;
    } else {
      return new UnmodifiableCharList(list);
    }
  }

  private final CharList list;

  protected UnmodifiableCharList(final CharList list) {
    this.list = requireNonNull("list", list);
  }

  @Override
  public boolean addAll(final CharCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final char element) {
    return list.contains(element);
  }

  @Override
  public boolean containsAll(final CharCollection c) {
    return list.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public boolean removeAll(final CharCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeElement(final char element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final CharCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public char[] toArray() {
    return list.toArray();
  }

  @Override
  public char[] toArray(final char[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean add(final char element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(final int index, final char element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final int index, final CharCollection collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public char get(final int index) {
    return list.get(index);
  }

  @Override
  public int indexOf(final char element) {
    return list.indexOf(element);
  }

  @Override
  public CharIterator iterator() {
    return new UnmodifiableCharIterator(list.iterator());
  }

  @Override
  public int lastIndexOf(final char element) {
    return list.lastIndexOf(element);
  }

  @Override
  public CharListIterator listIterator() {
    return new UnmodifiableCharListIterator(list.listIterator());
  }

  @Override
  public CharListIterator listIterator(final int index) {
    return new UnmodifiableCharListIterator(list.listIterator(index));
  }

  @Override
  public char removeElementAt(final int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public char set(final int index, final char element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public CharList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableCharList(list.subList(fromIndex, toIndex));
  }
}
