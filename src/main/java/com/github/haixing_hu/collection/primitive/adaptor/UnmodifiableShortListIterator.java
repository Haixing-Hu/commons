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
import com.github.haixing_hu.collection.primitive.ShortListIterator;

/**
 * An unmodifiable version of {@link ShortListIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableShortListIterator implements ShortListIterator {

  /**
   * Wraps a {@link ShortIterator} as an {@link UnmodifiableShortIterator}.
   *
   * @param iterator
   *          the {@link ShortIterator} to be wrap.
   * @return an {@link UnmodifiableShortIterator} wrapping the specified
   *         iterator.
   */
  public static UnmodifiableShortListIterator wrap(final ShortListIterator iterator) {
    if (iterator instanceof UnmodifiableShortListIterator) {
      return (UnmodifiableShortListIterator) iterator;
    } else {
      return new UnmodifiableShortListIterator(iterator);
    }
  }

  private final ShortListIterator iterator;

  protected UnmodifiableShortListIterator(final ShortListIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  @Override
  public void add(final short element) {
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
  public short next() {
    return iterator.next();
  }

  @Override
  public int nextIndex() {
    return iterator.nextIndex();
  }

  @Override
  public short previous() {
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
  public void set(final short element) {
    throw new UnsupportedOperationException();
  }

}
