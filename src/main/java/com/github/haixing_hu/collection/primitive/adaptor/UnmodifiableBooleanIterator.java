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

import com.github.haixing_hu.collection.primitive.BooleanIterator;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link BooleanIterator}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableBooleanIterator implements BooleanIterator {

  /**
   * Wraps a {@link BooleanIterator} as an {@link UnmodifiableBooleanIterator}.
   *
   * @param iterator
   *          the {@link BooleanIterator} to be wrap.
   * @return an {@link UnmodifiableBooleanIterator} wrapping the specified
   *         iterator.
   */
  public static UnmodifiableBooleanIterator wrap(final BooleanIterator iterator) {
    if (iterator instanceof UnmodifiableBooleanIterator) {
      return (UnmodifiableBooleanIterator) iterator;
    } else {
      return new UnmodifiableBooleanIterator(iterator);
    }
  }

  private final BooleanIterator iterator;

  protected UnmodifiableBooleanIterator(final BooleanIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public boolean next() {
    return iterator.next();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
