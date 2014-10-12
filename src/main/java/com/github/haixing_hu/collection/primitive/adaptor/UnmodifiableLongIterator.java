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

import com.github.haixing_hu.collection.primitive.LongIterator;

/**
 * An unmodifiable version of {@link LongIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableLongIterator implements LongIterator {

  /**
   * Wraps a {@link LongIterator} as an {@link UnmodifiableLongIterator}.
   *
   * @param iterator
   *          the {@link LongIterator} to be wrap.
   * @return an {@link UnmodifiableLongIterator} wrapping the specified
   *         iterator.
   */
  public static UnmodifiableLongIterator wrap(final LongIterator iterator) {
    if (iterator instanceof UnmodifiableLongIterator) {
      return (UnmodifiableLongIterator) iterator;
    } else {
      return new UnmodifiableLongIterator(iterator);
    }
  }

  private final LongIterator iterator;

  protected UnmodifiableLongIterator(final LongIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public long next() {
    return iterator.next();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
