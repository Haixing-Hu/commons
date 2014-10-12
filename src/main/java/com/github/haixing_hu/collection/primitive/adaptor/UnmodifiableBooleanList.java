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

import com.github.haixing_hu.collection.primitive.AbstractBooleanCollection;
import com.github.haixing_hu.collection.primitive.BooleanCollection;
import com.github.haixing_hu.collection.primitive.BooleanIterator;
import com.github.haixing_hu.collection.primitive.BooleanList;
import com.github.haixing_hu.collection.primitive.BooleanListIterator;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link BooleanList}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableBooleanList extends AbstractBooleanCollection
    implements BooleanList {

  /**
   * Wraps a {@link BooleanList} as an {@link UnmodifiableBooleanList}.
   *
   * @param iterator
   *          the {@link BooleanList} to be wrap.
   * @return an {@link UnmodifiableBooleanList} wrapping the specified
   *         collection.
   */
  public static UnmodifiableBooleanList wrap(final BooleanList list) {
    if (list instanceof UnmodifiableBooleanList) {
      return (UnmodifiableBooleanList) list;
    } else {
      return new UnmodifiableBooleanList(list);
    }
  }

  private final BooleanList list;

  protected UnmodifiableBooleanList(final BooleanList list) {
    this.list = requireNonNull("list", list);
  }

  @Override
  public boolean addAll(final BooleanCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final boolean element) {
    return list.contains(element);
  }

  @Override
  public boolean containsAll(final BooleanCollection c) {
    return list.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public boolean removeAll(final BooleanCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeElement(final boolean element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final BooleanCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public boolean[] toArray() {
    return list.toArray();
  }

  @Override
  public boolean[] toArray(final boolean[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean add(final boolean element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(final int index, final boolean element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final int index, final BooleanCollection collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean get(final int index) {
    return list.get(index);
  }

  @Override
  public int indexOf(final boolean element) {
    return list.indexOf(element);
  }

  @Override
  public BooleanIterator iterator() {
    return new UnmodifiableBooleanIterator(list.iterator());
  }

  @Override
  public int lastIndexOf(final boolean element) {
    return list.lastIndexOf(element);
  }

  @Override
  public BooleanListIterator listIterator() {
    return new UnmodifiableBooleanListIterator(list.listIterator());
  }

  @Override
  public BooleanListIterator listIterator(final int index) {
    return new UnmodifiableBooleanListIterator(list.listIterator(index));
  }

  @Override
  public boolean removeElementAt(final int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean set(final int index, final boolean element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public BooleanList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableBooleanList(list.subList(fromIndex, toIndex));
  }
}
