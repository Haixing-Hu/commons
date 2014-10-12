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
import com.github.haixing_hu.collection.primitive.AbstractByteCollection;
import com.github.haixing_hu.collection.primitive.ByteCollection;
import com.github.haixing_hu.collection.primitive.ByteIterator;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link ByteCollection}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableByteCollection extends AbstractByteCollection {

  /**
   * Wraps a {@link ByteCollection} as an {@link UnmodifiableByteCollection}.
   *
   * @param iterator
   *          the {@link ByteCollection} to be wrap.
   * @return an {@link UnmodifiableByteCollection} wrapping the specified
   *         collection.
   */
  public static UnmodifiableByteCollection wrap(final ByteCollection collection) {
    if (collection instanceof UnmodifiableByteCollection) {
      return (UnmodifiableByteCollection) collection;
    } else {
      return new UnmodifiableByteCollection(collection);
    }
  }

  private final ByteCollection collection;

  protected UnmodifiableByteCollection(final ByteCollection collection) {
    this.collection = requireNonNull("collection", collection);
  }

  @Override
  public boolean add(final byte element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final ByteCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final byte element) {
    return collection.contains(element);
  }

  @Override
  public boolean containsAll(final ByteCollection c) {
    return collection.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return collection.isEmpty();
  }

  @Override
  public ByteIterator iterator() {
    return new UnmodifiableByteIterator(collection.iterator());
  }

  @Override
  public boolean removeAll(final ByteCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeElement(final byte element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final ByteCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return collection.size();
  }

  @Override
  public byte[] toArray() {
    return collection.toArray();
  }

  @Override
  public byte[] toArray(final byte[] a) {
    return collection.toArray(a);
  }
}
