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

import com.github.haixing_hu.collection.primitive.DoubleIterator;
import com.github.haixing_hu.collection.primitive.DoubleListIterator;

/**
 * An unmodifiable version of {@link DoubleListIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableDoubleListIterator implements DoubleListIterator {

  /**
   * Wraps a {@link DoubleIterator} as an {@link UnmodifiableDoubleIterator}.
   *
   * @param iterator
   *          the {@link DoubleIterator} to be wrap.
   * @return an {@link UnmodifiableDoubleIterator} wrapping the specified
   *         iterator.
   */
  public static UnmodifiableDoubleListIterator wrap(final DoubleListIterator iterator) {
    if (iterator instanceof UnmodifiableDoubleListIterator) {
      return (UnmodifiableDoubleListIterator) iterator;
    } else {
      return new UnmodifiableDoubleListIterator(iterator);
    }
  }

  private final DoubleListIterator iterator;

  protected UnmodifiableDoubleListIterator(final DoubleListIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  @Override
  public void add(final double element) {
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
  public double next() {
    return iterator.next();
  }

  @Override
  public int nextIndex() {
    return iterator.nextIndex();
  }

  @Override
  public double previous() {
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
  public void set(final double element) {
    throw new UnsupportedOperationException();
  }

}
