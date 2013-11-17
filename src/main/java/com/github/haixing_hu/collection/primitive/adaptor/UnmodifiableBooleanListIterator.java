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
import com.github.haixing_hu.collection.primitive.BooleanListIterator;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link BooleanListIterator}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableBooleanListIterator implements BooleanListIterator {

  /**
   * Wraps a {@link BooleanIterator} as an {@link UnmodifiableBooleanIterator}.
   *
   * @param iterator
   *          the {@link BooleanIterator} to be wrap.
   * @return an {@link UnmodifiableBooleanIterator} wrapping the specified
   *         iterator.
   */
  public static UnmodifiableBooleanListIterator wrap(final BooleanListIterator iterator) {
    if (iterator instanceof UnmodifiableBooleanListIterator) {
      return (UnmodifiableBooleanListIterator) iterator;
    } else {
      return new UnmodifiableBooleanListIterator(iterator);
    }
  }

  private final BooleanListIterator iterator;

  protected UnmodifiableBooleanListIterator(final BooleanListIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  @Override
  public void add(final boolean element) {
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
  public boolean next() {
    return iterator.next();
  }

  @Override
  public int nextIndex() {
    return iterator.nextIndex();
  }

  @Override
  public boolean previous() {
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
  public void set(final boolean element) {
    throw new UnsupportedOperationException();
  }

}
