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

import com.github.haixing_hu.collection.primitive.ShortIterator;

/**
 * An unmodifiable version of {@link ShortIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableShortIterator implements ShortIterator {

  /**
   * Wraps a {@link ShortIterator} as an {@link UnmodifiableShortIterator}.
   *
   * @param iterator
   *          the {@link ShortIterator} to be wrap.
   * @return an {@link UnmodifiableShortIterator} wrapping the specified
   *         iterator.
   */
  public static UnmodifiableShortIterator wrap(final ShortIterator iterator) {
    if (iterator instanceof UnmodifiableShortIterator) {
      return (UnmodifiableShortIterator) iterator;
    } else {
      return new UnmodifiableShortIterator(iterator);
    }
  }

  private final ShortIterator iterator;

  protected UnmodifiableShortIterator(final ShortIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public short next() {
    return iterator.next();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
