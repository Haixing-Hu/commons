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

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import static com.github.haixing_hu.lang.Argument.*;

/**
 * Abstract base class for {@link BooleanList}s backed by random access
 * structures like arrays.
 * <p>
 * Read-only subclasses must override {@link #get} and {@link #size}. Mutable
 * subclasses should also override {@link #set}. Variably-sized subclasses
 * should also override {@link #add(boolean)} and {@link #removeElementAt}. All
 * other methods have at least some base implementation derived from these.
 * Subclasses may choose to override these methods to provide a more efficient
 * implementation.
 * </p>
 *
 * @author Haixing Hu
 */
public abstract class RandomAccessBooleanList extends AbstractBooleanCollection
    implements BooleanList {

  protected int modCount = 0;

  @Override
  public abstract boolean get(int index);

  @Override
  public abstract int size();

  @Override
  public abstract boolean removeElementAt(final int index);

  @Override
  public abstract boolean set(final int index, final boolean element);

  @Override
  public abstract void add(final int index, final boolean element);

  @Override
  public boolean add(final boolean element) {
    add(size(), element);
    return true;
  }

  @Override
  public boolean addAll(int index, final BooleanCollection collection) {
    boolean modified = false;
    final BooleanIterator iter = collection.iterator();
    while (iter.hasNext()) {
      add(index++, iter.next());
      modified = true;
    }
    return modified;
  }

  @Override
  public int indexOf(final boolean element) {
    final BooleanIterator iter = iterator();
    int i = 0;
    while (iter.hasNext()) {
      if (iter.next() == element) {
        return i;
      } else {
        ++i;
      }
    }
    return - 1;
  }

  @Override
  public int lastIndexOf(final boolean element) {
    final BooleanListIterator iter = listIterator(size());
    while (iter.hasPrevious()) {
      if (iter.previous() == element) {
        return iter.nextIndex();
      }
    }
    return - 1;
  }

  @Override
  public BooleanIterator iterator() {
    return listIterator();
  }

  @Override
  public BooleanListIterator listIterator() {
    return listIterator(0);
  }

  @Override
  public BooleanListIterator listIterator(final int index) {
    return new RandomAccessBooleanListIterator(this, index);
  }

  @Override
  public BooleanList subList(final int fromIndex, final int toIndex) {
    return new RandomAccessBooleanSubList(this, fromIndex, toIndex);
  }

  @Override
  public boolean equals(final Object that) {
    if (this == that) {
      return true;
    } else if (that instanceof BooleanList) {
      final BooleanList thatList = (BooleanList) that;
      if (size() != thatList.size()) {
        return false;
      }
      final BooleanIterator thisIter = iterator();
      final BooleanIterator thatIter = thatList.iterator();
      while (thisIter.hasNext()) {
        if (thisIter.next() != thatIter.next()) {
          return false;
        }
      }
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    int hash = 1;
    final BooleanIterator iter = iterator();
    while (iter.hasNext()) {
      hash = (31 * hash) + (iter.next() ? 1231 : 1237);
    }
    return hash;
  }

  @Override
  public String toString() {
    if (size() == 0) {
      return "[]";
    } else {
      final StringBuilder builder = new StringBuilder();
      builder.append('[');
      final BooleanIterator iter = iterator();
      while (iter.hasNext()) {
        builder.append(iter.next()).append(',');
      }
      // eat the last separator ','
      builder.setLength(builder.length() - 1);
      builder.append(']');
      return builder.toString();
    }
  }

  protected static class RandomAccessBooleanListIterator
      implements BooleanListIterator {

    protected RandomAccessBooleanList source;
    protected int nextIndex;
    protected int lastReturnedIndex;
    protected int expectedModCount;

    RandomAccessBooleanListIterator(final RandomAccessBooleanList list,
        final int index) {
      requireIndexInCloseRange(index, 0, list.size());
      source = list;
      nextIndex = index;
      lastReturnedIndex = -1;
      expectedModCount = source.modCount;
    }

    @Override
    public boolean hasNext() {
      if (expectedModCount != source.modCount) {
        throw new ConcurrentModificationException();
      }
      return nextIndex < source.size();
    }

    @Override
    public boolean hasPrevious() {
      if (expectedModCount != source.modCount) {
        throw new ConcurrentModificationException();
      }
      return nextIndex > 0;
    }

    @Override
    public int nextIndex() {
      if (expectedModCount != source.modCount) {
        throw new ConcurrentModificationException();
      }
      return nextIndex;
    }

    @Override
    public int previousIndex() {
      if (expectedModCount != source.modCount) {
        throw new ConcurrentModificationException();
      }
      return nextIndex - 1;
    }

    @Override
    public boolean next() {
      if (expectedModCount != source.modCount) {
        throw new ConcurrentModificationException();
      }
      if (! hasNext()) {
        throw new NoSuchElementException();
      } else {
        final boolean val = source.get(nextIndex);
        lastReturnedIndex = nextIndex;
        ++nextIndex;
        return val;
      }
    }

    @Override
    public boolean previous() {
      if (expectedModCount != source.modCount) {
        throw new ConcurrentModificationException();
      }
      if (! hasPrevious()) {
        throw new NoSuchElementException();
      } else {
        final boolean val = source.get(nextIndex - 1);
        lastReturnedIndex = nextIndex - 1;
        --nextIndex;
        return val;
      }
    }

    @Override
    public void add(final boolean value) {
      if (expectedModCount != source.modCount) {
        throw new ConcurrentModificationException();
      }
      source.add(nextIndex, value);
      ++nextIndex;
      lastReturnedIndex = - 1;
      expectedModCount = source.modCount;
    }

    @Override
    public void remove() {
      if (expectedModCount != source.modCount) {
        throw new ConcurrentModificationException();
      }
      if (lastReturnedIndex == - 1) {
        throw new IllegalStateException();
      }
      if (lastReturnedIndex == nextIndex) {
        // remove() following previous()
        source.removeElementAt(lastReturnedIndex);
      } else {
        // remove() following next()
        source.removeElementAt(lastReturnedIndex);
        --nextIndex;
      }
      lastReturnedIndex = - 1;
      expectedModCount = source.modCount;
    }

    @Override
    public void set(final boolean value) {
      if (expectedModCount != source.modCount) {
        throw new ConcurrentModificationException();
      }
      if (lastReturnedIndex == - 1) {
        throw new IllegalStateException();
      }
      source.set(lastReturnedIndex, value);
      expectedModCount = source.modCount;
    }
  }

  protected static class RandomAccessBooleanSubList extends
      RandomAccessBooleanList implements BooleanList {
    protected final int offset;
    protected int limit;
    protected final RandomAccessBooleanList source;
    protected int expectedModCount;

    RandomAccessBooleanSubList(final RandomAccessBooleanList list,
        final int fromIndex, final int toIndex) {
      requireLessEqual("fromIndex", fromIndex, "toIndex", toIndex);
      requireInCloseRange("fromIndex", fromIndex, 0, list.size());
      source = list;
      offset = fromIndex;
      limit = toIndex - fromIndex;
      expectedModCount = list.modCount;
    }

    @Override
    public boolean get(final int index) {
      requireInRightOpenRange("index", index, 0, limit);
      if (expectedModCount != source.modCount) {
        throw new ConcurrentModificationException();
      }
      return source.get(index + offset);
    }

    @Override
    public boolean removeElementAt(final int index) {
      requireInRightOpenRange("index", index, 0, limit);
      if (expectedModCount != source.modCount) {
        throw new ConcurrentModificationException();
      }
      final boolean val = source.removeElementAt(index + offset);
      --limit;
      expectedModCount = source.modCount;
      ++modCount;
      return val;
    }

    @Override
    public boolean set(final int index, final boolean element) {
      requireInRightOpenRange("index", index, 0, limit);
      if (expectedModCount != source.modCount) {
        throw new ConcurrentModificationException();
      }
      final boolean val = source.set(index + offset, element);
      ++modCount;
      expectedModCount = source.modCount;
      return val;
    }

    @Override
    public void add(final int index, final boolean element) {
      requireInCloseRange("index", index, 0, limit);
      if (expectedModCount != source.modCount) {
        throw new ConcurrentModificationException();
      }
      source.add(index + offset, element);
      ++limit;
      ++modCount;
      expectedModCount = source.modCount;
    }

    @Override
    public int size() {
      if (expectedModCount != source.modCount) {
        throw new ConcurrentModificationException();
      }
      return limit;
    }
  }
}
