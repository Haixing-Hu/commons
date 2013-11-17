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

import com.github.haixing_hu.collection.primitive.FloatIterator;
import com.github.haixing_hu.collection.primitive.FloatListIterator;

/**
 * An unmodifiable version of {@link FloatListIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableFloatListIterator implements FloatListIterator {

  /**
   * Wraps a {@link FloatIterator} as an {@link UnmodifiableFloatIterator}.
   *
   * @param iterator
   *          the {@link FloatIterator} to be wrap.
   * @return an {@link UnmodifiableFloatIterator} wrapping the specified
   *         iterator.
   */
  public static UnmodifiableFloatListIterator wrap(final FloatListIterator iterator) {
    if (iterator instanceof UnmodifiableFloatListIterator) {
      return (UnmodifiableFloatListIterator) iterator;
    } else {
      return new UnmodifiableFloatListIterator(iterator);
    }
  }

  private final FloatListIterator iterator;

  protected UnmodifiableFloatListIterator(final FloatListIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  @Override
  public void add(final float element) {
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
  public float next() {
    return iterator.next();
  }

  @Override
  public int nextIndex() {
    return iterator.nextIndex();
  }

  @Override
  public float previous() {
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
  public void set(final float element) {
    throw new UnsupportedOperationException();
  }

}
