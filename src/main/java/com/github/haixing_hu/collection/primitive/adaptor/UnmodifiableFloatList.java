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

import com.github.haixing_hu.collection.primitive.AbstractFloatCollection;
import com.github.haixing_hu.collection.primitive.FloatCollection;
import com.github.haixing_hu.collection.primitive.FloatIterator;
import com.github.haixing_hu.collection.primitive.FloatList;
import com.github.haixing_hu.collection.primitive.FloatListIterator;

/**
 * An unmodifiable version of {@link FloatList}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableFloatList extends AbstractFloatCollection
    implements FloatList {

  /**
   * Wraps a {@link FloatList} as an {@link UnmodifiableFloatList}.
   *
   * @param iterator
   *          the {@link FloatList} to be wrap.
   * @return an {@link UnmodifiableFloatList} wrapping the specified
   *         collection.
   */
  public static UnmodifiableFloatList wrap(final FloatList list) {
    if (list instanceof UnmodifiableFloatList) {
      return (UnmodifiableFloatList) list;
    } else {
      return new UnmodifiableFloatList(list);
    }
  }

  private final FloatList list;

  protected UnmodifiableFloatList(final FloatList list) {
    this.list = requireNonNull("list", list);
  }

  @Override
  public boolean addAll(final FloatCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final float element) {
    return list.contains(element);
  }

  @Override
  public boolean containsAll(final FloatCollection c) {
    return list.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public boolean removeAll(final FloatCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeElement(final float element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final FloatCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public float[] toArray() {
    return list.toArray();
  }

  @Override
  public float[] toArray(final float[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean add(final float element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(final int index, final float element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final int index, final FloatCollection collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public float get(final int index) {
    return list.get(index);
  }

  @Override
  public int indexOf(final float element) {
    return list.indexOf(element);
  }

  @Override
  public FloatIterator iterator() {
    return new UnmodifiableFloatIterator(list.iterator());
  }

  @Override
  public int lastIndexOf(final float element) {
    return list.lastIndexOf(element);
  }

  @Override
  public FloatListIterator listIterator() {
    return new UnmodifiableFloatListIterator(list.listIterator());
  }

  @Override
  public FloatListIterator listIterator(final int index) {
    return new UnmodifiableFloatListIterator(list.listIterator(index));
  }

  @Override
  public float removeElementAt(final int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public float set(final int index, final float element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public FloatList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableFloatList(list.subList(fromIndex, toIndex));
  }
}
