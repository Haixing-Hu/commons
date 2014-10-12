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

import com.github.haixing_hu.collection.primitive.DoubleIterator;

/**
 * An unmodifiable version of {@link DoubleIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableDoubleIterator implements DoubleIterator {

  /**
   * Wraps a {@link DoubleIterator} as an {@link UnmodifiableDoubleIterator}.
   *
   * @param iterator
   *          the {@link DoubleIterator} to be wrap.
   * @return an {@link UnmodifiableDoubleIterator} wrapping the specified
   *         iterator.
   */
  public static UnmodifiableDoubleIterator wrap(final DoubleIterator iterator) {
    if (iterator instanceof UnmodifiableDoubleIterator) {
      return (UnmodifiableDoubleIterator) iterator;
    } else {
      return new UnmodifiableDoubleIterator(iterator);
    }
  }

  private final DoubleIterator iterator;

  protected UnmodifiableDoubleIterator(final DoubleIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public double next() {
    return iterator.next();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
