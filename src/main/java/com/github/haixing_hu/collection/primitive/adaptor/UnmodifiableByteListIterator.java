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

import com.github.haixing_hu.collection.primitive.ByteIterator;
import com.github.haixing_hu.collection.primitive.ByteListIterator;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link ByteListIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableByteListIterator implements ByteListIterator {

  /**
   * Wraps a {@link ByteIterator} as an {@link UnmodifiableByteIterator}.
   *
   * @param iterator
   *          the {@link ByteIterator} to be wrap.
   * @return an {@link UnmodifiableByteIterator} wrapping the specified
   *         iterator.
   */
  public static UnmodifiableByteListIterator wrap(final ByteListIterator iterator) {
    if (iterator instanceof UnmodifiableByteListIterator) {
      return (UnmodifiableByteListIterator) iterator;
    } else {
      return new UnmodifiableByteListIterator(iterator);
    }
  }

  private final ByteListIterator iterator;

  protected UnmodifiableByteListIterator(final ByteListIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  @Override
  public void add(final byte element) {
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
  public byte next() {
    return iterator.next();
  }

  @Override
  public int nextIndex() {
    return iterator.nextIndex();
  }

  @Override
  public byte previous() {
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
  public void set(final byte element) {
    throw new UnsupportedOperationException();
  }

}
