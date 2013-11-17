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

import com.github.haixing_hu.collection.primitive.AbstractLongCollection;
import com.github.haixing_hu.collection.primitive.LongCollection;
import com.github.haixing_hu.collection.primitive.LongIterator;

/**
 * An unmodifiable version of {@link LongCollection}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableLongCollection extends AbstractLongCollection {

  /**
   * Wraps a {@link LongCollection} as an {@link UnmodifiableLongCollection}.
   *
   * @param iterator
   *          the {@link LongCollection} to be wrap.
   * @return an {@link UnmodifiableLongCollection} wrapping the specified
   *         collection.
   */
  public static UnmodifiableLongCollection wrap(final LongCollection collection) {
    if (collection instanceof UnmodifiableLongCollection) {
      return (UnmodifiableLongCollection) collection;
    } else {
      return new UnmodifiableLongCollection(collection);
    }
  }

  private final LongCollection collection;

  protected UnmodifiableLongCollection(final LongCollection collection) {
    this.collection = requireNonNull("collection", collection);
  }

  @Override
  public boolean add(final long element) {
    throw new UnsupportedOperationException();
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
    return collection.contains(element);
  }

  @Override
  public boolean containsAll(final LongCollection c) {
    return collection.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return collection.isEmpty();
  }

  @Override
  public LongIterator iterator() {
    return new UnmodifiableLongIterator(collection.iterator());
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
    return collection.size();
  }

  @Override
  public long[] toArray() {
    return collection.toArray();
  }

  @Override
  public long[] toArray(final long[] a) {
    return collection.toArray(a);
  }
}
