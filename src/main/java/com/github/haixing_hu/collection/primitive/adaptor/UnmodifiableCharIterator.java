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

import com.github.haixing_hu.collection.primitive.CharIterator;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link CharIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableCharIterator implements CharIterator {

  /**
   * Wraps a {@link CharIterator} as an {@link UnmodifiableCharIterator}.
   *
   * @param iterator
   *          the {@link CharIterator} to be wrap.
   * @return an {@link UnmodifiableCharIterator} wrapping the specified
   *         iterator.
   */
  public static UnmodifiableCharIterator wrap(final CharIterator iterator) {
    if (iterator instanceof UnmodifiableCharIterator) {
      return (UnmodifiableCharIterator) iterator;
    } else {
      return new UnmodifiableCharIterator(iterator);
    }
  }

  private final CharIterator iterator;

  protected UnmodifiableCharIterator(final CharIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public char next() {
    return iterator.next();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
