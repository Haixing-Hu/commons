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
import com.github.haixing_hu.collection.primitive.AbstractBooleanCollection;
import com.github.haixing_hu.collection.primitive.BooleanCollection;
import com.github.haixing_hu.collection.primitive.BooleanIterator;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link BooleanCollection}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableBooleanCollection extends AbstractBooleanCollection {

  /**
   * Wraps a {@link BooleanCollection} as an {@link UnmodifiableBooleanCollection}.
   *
   * @param iterator
   *          the {@link BooleanCollection} to be wrap.
   * @return an {@link UnmodifiableBooleanCollection} wrapping the specified
   *         collection.
   */
  public static UnmodifiableBooleanCollection wrap(final BooleanCollection collection) {
    if (collection instanceof UnmodifiableBooleanCollection) {
      return (UnmodifiableBooleanCollection) collection;
    } else {
      return new UnmodifiableBooleanCollection(collection);
    }
  }

  private final BooleanCollection collection;

  protected UnmodifiableBooleanCollection(final BooleanCollection collection) {
    this.collection = requireNonNull("collection", collection);
  }

  @Override
  public boolean add(final boolean element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final BooleanCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final boolean element) {
    return collection.contains(element);
  }

  @Override
  public boolean containsAll(final BooleanCollection c) {
    return collection.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return collection.isEmpty();
  }

  @Override
  public BooleanIterator iterator() {
    return new UnmodifiableBooleanIterator(collection.iterator());
  }

  @Override
  public boolean removeAll(final BooleanCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeElement(final boolean element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final BooleanCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return collection.size();
  }

  @Override
  public boolean[] toArray() {
    return collection.toArray();
  }

  @Override
  public boolean[] toArray(final boolean[] a) {
    return collection.toArray(a);
  }
}
