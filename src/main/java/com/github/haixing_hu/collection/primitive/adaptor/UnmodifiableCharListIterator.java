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
import com.github.haixing_hu.collection.primitive.CharListIterator;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link CharListIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableCharListIterator implements CharListIterator {

  /**
   * Wraps a {@link CharIterator} as an {@link UnmodifiableCharIterator}.
   *
   * @param iterator
   *          the {@link CharIterator} to be wrap.
   * @return an {@link UnmodifiableCharIterator} wrapping the specified
   *         iterator.
   */
  public static UnmodifiableCharListIterator wrap(final CharListIterator iterator) {
    if (iterator instanceof UnmodifiableCharListIterator) {
      return (UnmodifiableCharListIterator) iterator;
    } else {
      return new UnmodifiableCharListIterator(iterator);
    }
  }

  private final CharListIterator iterator;

  protected UnmodifiableCharListIterator(final CharListIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  @Override
  public void add(final char element) {
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
  public char next() {
    return iterator.next();
  }

  @Override
  public int nextIndex() {
    return iterator.nextIndex();
  }

  @Override
  public char previous() {
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
  public void set(final char element) {
    throw new UnsupportedOperationException();
  }

}
