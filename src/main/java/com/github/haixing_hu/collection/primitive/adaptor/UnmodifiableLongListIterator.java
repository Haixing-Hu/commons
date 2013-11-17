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

import com.github.haixing_hu.collection.primitive.LongIterator;
import com.github.haixing_hu.collection.primitive.LongListIterator;

/**
 * An unmodifiable version of {@link LongListIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableLongListIterator implements LongListIterator {

  /**
   * Wraps a {@link LongIterator} as an {@link UnmodifiableLongIterator}.
   *
   * @param iterator
   *          the {@link LongIterator} to be wrap.
   * @return an {@link UnmodifiableLongIterator} wrapping the specified
   *         iterator.
   */
  public static UnmodifiableLongListIterator wrap(final LongListIterator iterator) {
    if (iterator instanceof UnmodifiableLongListIterator) {
      return (UnmodifiableLongListIterator) iterator;
    } else {
      return new UnmodifiableLongListIterator(iterator);
    }
  }

  private final LongListIterator iterator;

  protected UnmodifiableLongListIterator(final LongListIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  @Override
  public void add(final long element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public boolean hasPrevious() {
    return iterator.hasPrevious();
  }

  @Override
  public long next() {
    return iterator.next();
  }

  @Override
  public int nextIndex() {
    return iterator.nextIndex();
  }

  @Override
  public long previous() {
    return iterator.previous();
  }

  @Override
  public int previousIndex() {
    return iterator.previousIndex();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void set(final long element) {
    throw new UnsupportedOperationException();
  }

}
