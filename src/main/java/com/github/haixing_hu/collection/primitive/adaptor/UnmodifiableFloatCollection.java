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

/**
 * An unmodifiable version of {@link FloatCollection}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableFloatCollection extends AbstractFloatCollection {

  /**
   * Wraps a {@link FloatCollection} as an {@link UnmodifiableFloatCollection}.
   *
   * @param iterator
   *          the {@link FloatCollection} to be wrap.
   * @return an {@link UnmodifiableFloatCollection} wrapping the specified
   *         collection.
   */
  public static UnmodifiableFloatCollection wrap(final FloatCollection collection) {
    if (collection instanceof UnmodifiableFloatCollection) {
      return (UnmodifiableFloatCollection) collection;
    } else {
      return new UnmodifiableFloatCollection(collection);
    }
  }

  private final FloatCollection collection;

  protected UnmodifiableFloatCollection(final FloatCollection collection) {
    this.collection = requireNonNull("collection", collection);
  }

  @Override
  public boolean add(final float element) {
    throw new UnsupportedOperationException();
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
    return collection.contains(element);
  }

  @Override
  public boolean containsAll(final FloatCollection c) {
    return collection.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return collection.isEmpty();
  }

  @Override
  public FloatIterator iterator() {
    return new UnmodifiableFloatIterator(collection.iterator());
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
    return collection.size();
  }

  @Override
  public float[] toArray() {
    return collection.toArray();
  }

  @Override
  public float[] toArray(final float[] a) {
    return collection.toArray(a);
  }
}
