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
package com.github.haixing_hu.collection.primitive;

import javax.annotation.Nullable;

import com.github.haixing_hu.lang.Comparison;
import com.github.haixing_hu.lang.Hash;

/**
 * Abstract base class for {@link IntCollection}s.
 * <p />
 * Read-only subclasses must override {@link #iterator} and {@link #size}.
 * Mutable subclasses should also override {@link #add} and
 * {@link IntIterator#remove IntIterator.remove}. All other methods have at
 * least some base implementation derived from these. Subclasses may choose to
 * override these methods to provide a more efficient implementation.
 *
 * @author Haixing Hu
 */
public abstract class AbstractIntCollection implements IntCollection {
  @Override
  public abstract IntIterator iterator();

  @Override
  public abstract int size();

  @Override
  public abstract boolean add(final int element);

  @Override
  public boolean addAll(final IntCollection c) {
    boolean modified = false;
    for (final IntIterator iter = c.iterator(); iter.hasNext();) {
      modified |= add(iter.next());
    }
    return modified;
  }

  @Override
  public void clear() {
    for (final IntIterator iter = iterator(); iter.hasNext();) {
      iter.next();
      iter.remove();
    }
  }

  @Override
  public boolean contains(final int element) {
    for (final IntIterator iter = iterator(); iter.hasNext();) {
      if (iter.next() == element) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean containsAll(final IntCollection c) {
    for (final IntIterator iter = c.iterator(); iter.hasNext();) {
      if (! contains(iter.next())) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isEmpty() {
    return (0 == size());
  }

  @Override
  public boolean removeElement(final int element) {
    for (final IntIterator iter = iterator(); iter.hasNext();) {
      if (iter.next() == element) {
        iter.remove();
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean removeAll(final IntCollection c) {
    boolean modified = false;
    for (final IntIterator iter = c.iterator(); iter.hasNext();) {
      modified |= removeElement(iter.next());
    }
    return modified;
  }

  @Override
  public boolean retainAll(final IntCollection c) {
    boolean modified = false;
    for (final IntIterator iter = iterator(); iter.hasNext();) {
      if (! c.contains(iter.next())) {
        iter.remove();
        modified = true;
      }
    }
    return modified;
  }

  @Override
  public int[] toArray() {
    final int[] array = new int[size()];
    int i = 0;
    for (final IntIterator iter = iterator(); iter.hasNext();) {
      array[i] = iter.next();
      i++;
    }
    return array;
  }

  @Override
  public int[] toArray(final int[] a) {
    if (a.length < size()) {
      return toArray();
    } else {
      int i = 0;
      for (final IntIterator iter = iterator(); iter.hasNext();) {
        a[i] = iter.next();
        i++;
      }
      return a;
    }
  }

  @Override
  public int compareTo(@Nullable final IntCollection other) {
    if (other == null) {
      return +1;
    }
    final IntIterator lhsIter = this.iterator();
    final IntIterator rhsIter = other.iterator();
    while (lhsIter.hasNext() && rhsIter.hasNext()) {
      final int lhsValue = lhsIter.next();
      final int rhsValue = rhsIter.next();
      final int rc = Comparison.compare(lhsValue, rhsValue);
      if (rc != 0) {
        return rc;
      }
    }
    if (lhsIter.hasNext()) {
      return +1;
    } else if (rhsIter.hasNext()) {
      return -1;
    } else {
      return 0;
    }
  }

  @Override
  public int hashCode() {
    final int multiplier = 131;
    int code = 11;
    final IntIterator iter = this.iterator();
    while (iter.hasNext()) {
      final int value = iter.next();
      code = Hash.combine(code, multiplier, value);
    }
    return code;
  }

  @Override
  public boolean equals(@Nullable final Object obj) {
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final IntCollection other = (IntCollection) obj;
    if (this.size() != other.size()) {
      return false;
    }
    final IntIterator lhsIter = this.iterator();
    final IntIterator rhsIter = other.iterator();
    while (lhsIter.hasNext() && rhsIter.hasNext()) {
      final int lhsValue = lhsIter.next();
      final int rhsValue = rhsIter.next();
      if (lhsValue != rhsValue) {
        return false;
      }
    }
    return true;
  }
}
