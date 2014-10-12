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

import com.github.haixing_hu.collection.primitive.AbstractIntCollection;
import com.github.haixing_hu.collection.primitive.IntCollection;
import com.github.haixing_hu.collection.primitive.IntIterator;

/**
 * An unmodifiable version of {@link IntCollection}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableIntCollection extends AbstractIntCollection {

  /**
   * Wraps a {@link IntCollection} as an {@link UnmodifiableIntCollection}.
   *
   * @param iterator
   *          the {@link IntCollection} to be wrap.
   * @return an {@link UnmodifiableIntCollection} wrapping the specified
   *         collection.
   */
  public static UnmodifiableIntCollection wrap(final IntCollection collection) {
    if (collection instanceof UnmodifiableIntCollection) {
      return (UnmodifiableIntCollection) collection;
    } else {
      return new UnmodifiableIntCollection(collection);
    }
  }

  private final IntCollection collection;

  protected UnmodifiableIntCollection(final IntCollection collection) {
    this.collection = requireNonNull("collection", collection);
  }

  @Override
  public boolean add(final int element) {
    throw new UnsupportedOperationException();
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
    return collection.contains(element);
  }

  @Override
  public boolean containsAll(final IntCollection c) {
    return collection.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return collection.isEmpty();
  }

  @Override
  public IntIterator iterator() {
    return new UnmodifiableIntIterator(collection.iterator());
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
    return collection.size();
  }

  @Override
  public int[] toArray() {
    return collection.toArray();
  }

  @Override
  public int[] toArray(final int[] a) {
    return collection.toArray(a);
  }
}
