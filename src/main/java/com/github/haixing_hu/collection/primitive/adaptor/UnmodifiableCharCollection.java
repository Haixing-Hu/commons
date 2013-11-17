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

import com.github.haixing_hu.collection.primitive.AbstractCharCollection;
import com.github.haixing_hu.collection.primitive.CharCollection;
import com.github.haixing_hu.collection.primitive.CharIterator;

/**
 * An unmodifiable version of {@link CharCollection}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableCharCollection extends AbstractCharCollection {

  /**
   * Wraps a {@link CharCollection} as an {@link UnmodifiableCharCollection}.
   *
   * @param iterator
   *          the {@link CharCollection} to be wrap.
   * @return an {@link UnmodifiableCharCollection} wrapping the specified
   *         collection.
   */
  public static UnmodifiableCharCollection wrap(final CharCollection collection) {
    if (collection instanceof UnmodifiableCharCollection) {
      return (UnmodifiableCharCollection) collection;
    } else {
      return new UnmodifiableCharCollection(collection);
    }
  }

  private final CharCollection collection;

  protected UnmodifiableCharCollection(final CharCollection collection) {
    this.collection = requireNonNull("collection", collection);
  }

  @Override
  public boolean add(final char element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final CharCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final char element) {
    return collection.contains(element);
  }

  @Override
  public boolean containsAll(final CharCollection c) {
    return collection.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return collection.isEmpty();
  }

  @Override
  public CharIterator iterator() {
    return new UnmodifiableCharIterator(collection.iterator());
  }

  @Override
  public boolean removeAll(final CharCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeElement(final char element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final CharCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return collection.size();
  }

  @Override
  public char[] toArray() {
    return collection.toArray();
  }

  @Override
  public char[] toArray(final char[] a) {
    return collection.toArray(a);
  }
}
