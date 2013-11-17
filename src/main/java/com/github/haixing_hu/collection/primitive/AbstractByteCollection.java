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

package com.github.haixing_hu.collection.primitive;

import javax.annotation.Nullable;

import com.github.haixing_hu.lang.Comparison;
import com.github.haixing_hu.lang.Hash;

/**
 * Abstract base class for {@link ByteCollection}s.
 * <p />
 * Read-only subclasses must override {@link #iterator} and {@link #size}.
 * Mutable subclasses should also override {@link #add} and
 * {@link ByteIterator#remove ByteIterator.remove}. All other methods have at
 * least some base implementation derived from these. Subclasses may choose to
 * override these methods to provide a more efficient implementation.
 *
 * @author Haixing Hu
 */
public abstract class AbstractByteCollection implements ByteCollection {
  @Override
  public abstract ByteIterator iterator();

  @Override
  public abstract int size();

  @Override
  public abstract boolean add(final byte element);

  @Override
  public boolean addAll(final ByteCollection c) {
    boolean modified = false;
    for (final ByteIterator iter = c.iterator(); iter.hasNext();) {
      modified |= add(iter.next());
    }
    return modified;
  }

  @Override
  public void clear() {
    for (final ByteIterator iter = iterator(); iter.hasNext();) {
      iter.next();
      iter.remove();
    }
  }

  @Override
  public boolean contains(final byte element) {
    for (final ByteIterator iter = iterator(); iter.hasNext();) {
      if (iter.next() == element) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean containsAll(final ByteCollection c) {
    for (final ByteIterator iter = c.iterator(); iter.hasNext();) {
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
  public boolean removeElement(final byte element) {
    for (final ByteIterator iter = iterator(); iter.hasNext();) {
      if (iter.next() == element) {
        iter.remove();
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean removeAll(final ByteCollection c) {
    boolean modified = false;
    for (final ByteIterator iter = c.iterator(); iter.hasNext();) {
      modified |= removeElement(iter.next());
    }
    return modified;
  }

  @Override
  public boolean retainAll(final ByteCollection c) {
    boolean modified = false;
    for (final ByteIterator iter = iterator(); iter.hasNext();) {
      if (! c.contains(iter.next())) {
        iter.remove();
        modified = true;
      }
    }
    return modified;
  }

  @Override
  public byte[] toArray() {
    final byte[] array = new byte[size()];
    int i = 0;
    for (final ByteIterator iter = iterator(); iter.hasNext();) {
      array[i] = iter.next();
      i++;
    }
    return array;
  }

  @Override
  public byte[] toArray(final byte[] a) {
    if (a.length < size()) {
      return toArray();
    } else {
      int i = 0;
      for (final ByteIterator iter = iterator(); iter.hasNext();) {
        a[i] = iter.next();
        i++;
      }
      return a;
    }
  }

  @Override
  public int compareTo(@Nullable final ByteCollection other) {
    if (other == null) {
      return +1;
    }
    final ByteIterator lhsIter = this.iterator();
    final ByteIterator rhsIter = other.iterator();
    while (lhsIter.hasNext() && rhsIter.hasNext()) {
      final byte lhsValue = lhsIter.next();
      final byte rhsValue = rhsIter.next();
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
    final ByteIterator iter = this.iterator();
    while (iter.hasNext()) {
      final byte value = iter.next();
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
    final ByteCollection other = (ByteCollection) obj;
    if (this.size() != other.size()) {
      return false;
    }
    final ByteIterator lhsIter = this.iterator();
    final ByteIterator rhsIter = other.iterator();
    while (lhsIter.hasNext() && rhsIter.hasNext()) {
      final byte lhsValue = lhsIter.next();
      final byte rhsValue = rhsIter.next();
      if (lhsValue != rhsValue) {
        return false;
      }
    }
    return true;
  }
}
