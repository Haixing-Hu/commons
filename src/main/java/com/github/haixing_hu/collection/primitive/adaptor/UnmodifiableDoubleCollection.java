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

import com.github.haixing_hu.collection.primitive.AbstractDoubleCollection;
import com.github.haixing_hu.collection.primitive.DoubleCollection;
import com.github.haixing_hu.collection.primitive.DoubleIterator;

/**
 * An unmodifiable version of {@link DoubleCollection}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableDoubleCollection extends AbstractDoubleCollection {

  /**
   * Wraps a {@link DoubleCollection} as an {@link UnmodifiableDoubleCollection}.
   *
   * @param iterator
   *          the {@link DoubleCollection} to be wrap.
   * @return an {@link UnmodifiableDoubleCollection} wrapping the specified
   *         collection.
   */
  public static UnmodifiableDoubleCollection wrap(final DoubleCollection collection) {
    if (collection instanceof UnmodifiableDoubleCollection) {
      return (UnmodifiableDoubleCollection) collection;
    } else {
      return new UnmodifiableDoubleCollection(collection);
    }
  }

  private final DoubleCollection collection;

  protected UnmodifiableDoubleCollection(final DoubleCollection collection) {
    this.collection = requireNonNull("collection", collection);
  }

  @Override
  public boolean add(final double element) {
    throw new UnsupportedOperationException();
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
    return collection.contains(element);
  }

  @Override
  public boolean containsAll(final DoubleCollection c) {
    return collection.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return collection.isEmpty();
  }

  @Override
  public DoubleIterator iterator() {
    return new UnmodifiableDoubleIterator(collection.iterator());
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
    return collection.size();
  }

  @Override
  public double[] toArray() {
    return collection.toArray();
  }

  @Override
  public double[] toArray(final double[] a) {
    return collection.toArray(a);
  }
}
