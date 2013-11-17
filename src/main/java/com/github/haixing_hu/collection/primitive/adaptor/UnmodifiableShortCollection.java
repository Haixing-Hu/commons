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

import com.github.haixing_hu.collection.primitive.AbstractShortCollection;
import com.github.haixing_hu.collection.primitive.ShortCollection;
import com.github.haixing_hu.collection.primitive.ShortIterator;

/**
 * An unmodifiable version of {@link ShortCollection}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableShortCollection extends AbstractShortCollection {

  /**
   * Wraps a {@link ShortCollection} as an {@link UnmodifiableShortCollection}.
   *
   * @param iterator
   *          the {@link ShortCollection} to be wrap.
   * @return an {@link UnmodifiableShortCollection} wrapping the specified
   *         collection.
   */
  public static UnmodifiableShortCollection wrap(final ShortCollection collection) {
    if (collection instanceof UnmodifiableShortCollection) {
      return (UnmodifiableShortCollection) collection;
    } else {
      return new UnmodifiableShortCollection(collection);
    }
  }

  private final ShortCollection collection;

  protected UnmodifiableShortCollection(final ShortCollection collection) {
    this.collection = requireNonNull("collection", collection);
  }

  @Override
  public boolean add(final short element) {
    throw new UnsupportedOperationException();
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
    return collection.contains(element);
  }

  @Override
  public boolean containsAll(final ShortCollection c) {
    return collection.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return collection.isEmpty();
  }

  @Override
  public ShortIterator iterator() {
    return new UnmodifiableShortIterator(collection.iterator());
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
    return collection.size();
  }

  @Override
  public short[] toArray() {
    return collection.toArray();
  }

  @Override
  public short[] toArray(final short[] a) {
    return collection.toArray(a);
  }
}
