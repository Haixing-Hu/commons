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

import com.github.haixing_hu.collection.primitive.AbstractDoubleCollection;
import com.github.haixing_hu.collection.primitive.DoubleCollection;
import com.github.haixing_hu.collection.primitive.DoubleIterator;
import com.github.haixing_hu.collection.primitive.DoubleList;
import com.github.haixing_hu.collection.primitive.DoubleListIterator;

/**
 * An unmodifiable version of {@link DoubleList}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableDoubleList extends AbstractDoubleCollection
    implements DoubleList {

  /**
   * Wraps a {@link DoubleList} as an {@link UnmodifiableDoubleList}.
   *
   * @param iterator
   *          the {@link DoubleList} to be wrap.
   * @return an {@link UnmodifiableDoubleList} wrapping the specified
   *         collection.
   */
  public static UnmodifiableDoubleList wrap(final DoubleList list) {
    if (list instanceof UnmodifiableDoubleList) {
      return (UnmodifiableDoubleList) list;
    } else {
      return new UnmodifiableDoubleList(list);
    }
  }

  private final DoubleList list;

  protected UnmodifiableDoubleList(final DoubleList list) {
    this.list = requireNonNull("list", list);
  }

  @Override
  public boolean addAll(final DoubleCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final double element) {
    return list.contains(element);
  }

  @Override
  public boolean containsAll(final DoubleCollection c) {
    return list.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public boolean removeAll(final DoubleCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeElement(final double element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final DoubleCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public double[] toArray() {
    return list.toArray();
  }

  @Override
  public double[] toArray(final double[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean add(final double element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(final int index, final double element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final int index, final DoubleCollection collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public double get(final int index) {
    return list.get(index);
  }

  @Override
  public int indexOf(final double element) {
    return list.indexOf(element);
  }

  @Override
  public DoubleIterator iterator() {
    return new UnmodifiableDoubleIterator(list.iterator());
  }

  @Override
  public int lastIndexOf(final double element) {
    return list.lastIndexOf(element);
  }

  @Override
  public DoubleListIterator listIterator() {
    return new UnmodifiableDoubleListIterator(list.listIterator());
  }

  @Override
  public DoubleListIterator listIterator(final int index) {
    return new UnmodifiableDoubleListIterator(list.listIterator(index));
  }

  @Override
  public double removeElementAt(final int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public double set(final int index, final double element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public DoubleList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableDoubleList(list.subList(fromIndex, toIndex));
  }
}
