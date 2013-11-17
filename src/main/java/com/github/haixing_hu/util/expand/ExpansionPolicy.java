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

package com.github.haixing_hu.util.expand;

import java.lang.reflect.Array;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import com.github.haixing_hu.CommonsConfig;
import com.github.haixing_hu.util.config.Config;

/**
 * The {@link ExpansionPolicy} is used to calculate the capacity while expanding
 * the dynamic arrays.
 * <p>
 * The implementation MUST be thread-safe.
 * </p>
 *
 * @author Haixing Hu
 */
@ThreadSafe
public abstract class ExpansionPolicy {

  /**
   * The value of this property is the class name of the implementation of the
   * default {@link ExpansionPolicy}.
   * <table border="1">
   * <tr>
   * <th>Type</th>
   * <th>Count</th>
   * <th>Value</th>
   * <th>Required</th>
   * <th>Default</th>
   * <th>Range</th>
   * </tr>
   * <tr>
   * <td>class</td>
   * <td>1</td>
   * <td>the class name of the implementation of the default
   * {@link ExpansionPolicy}.</td>
   * <td>no</td>
   * <td>{@link MemorySavingExpansionPolicy#INSTANCE}</td>
   * <td></td>
   * </tr>
   * </table>
   */
  public static final String PROPERTY_DEFAULT = "com.github.haixing_hu.util.expand.ExpansionPolicy.default";

  /**
   * The value of this property is the initial capacity used by the
   * {@link ExpansionPolicy}.
   * <table border="1">
   * <tr>
   * <th>Type</th>
   * <th>Count</th>
   * <th>Value</th>
   * <th>Required</th>
   * <th>Default</th>
   * <th>Range</th>
   * </tr>
   * <tr>
   * <td>int</td>
   * <td>1</td>
   * <td>the initial capacity used by the {@link ExpansionPolicy}.</td>
   * <td>no</td>
   * <td>{@link DEFAULT_INITIAL_CAPACITY}</td>
   * <td>[1, {@link Integer#MAX_VALUE}]</td>
   * </tr>
   * </table>
   *
   * @see #DEFAULT_INITIAL_CAPACITY
   */
  public static final String PROPERTY_INITIAL_CAPACITY = "com.github.haixing_hu.util.expand.ExpansionPolicy.initialCapacity";

  /**
   * The default value of the property {@link PROPERTY_INITIAL_CAPACITY}.
   *
   * @see PROPERTY_INITIAL_CAPACITY
   */
  public static final int DEFAULT_INITIAL_CAPACITY = 16;

  private static final String INVALID_IMPLEMENTATION =
    "Invalid implementation of the abstract method.";

  private static volatile ExpansionPolicy defaultPolicy = null;

  /**
   * Gets the default {@link ExpansionPoicy}.
   *
   * @return the default {@link ExpansionPoicy}.
   */
  public static ExpansionPolicy getDefault() {
    if (defaultPolicy == null) {
      synchronized (ExpansionPolicy.class) {
        if (defaultPolicy == null) {
          final Config config = CommonsConfig.get();
          defaultPolicy = config.getInstance(PROPERTY_DEFAULT,
              MemorySavingExpansionPolicy.INSTANCE);
        }
      }
    }
    return defaultPolicy;
  }

  private static volatile int initialCapacity = - 1;

  /**
   * Gets the suggested initial capacity.
   *
   * @return the suggested initial capacity.
   */
  public static int getInitialCapacity() {
    if (initialCapacity < 0) {
      synchronized (ExpansionPolicy.class) {
        if (initialCapacity < 0) {
          final Config config = CommonsConfig.get();
          initialCapacity = config.getInt(PROPERTY_INITIAL_CAPACITY,
              DEFAULT_INITIAL_CAPACITY);
        }
      }
    }
    return initialCapacity;
  }

  public abstract int getNextCapacity(int oldCapacity, int newLength);

  public abstract boolean needShrink(int length, int capacity);

  public abstract int getShrinkCapacity(int length, int capacity);

  public final boolean[] expand(@Nullable final boolean[] oldBuffer,
      final int oldLength, final int newLength) {
    return (boolean[]) doExpand(oldBuffer, oldLength, newLength, Boolean.TYPE);
  }

  public final boolean[] shrink(@Nullable final boolean[] oldBuffer,
      final int oldLength) {
    return (boolean[]) doShrink(oldBuffer, oldLength, Boolean.TYPE);
  }

  public final boolean[] resize(@Nullable final boolean[] oldBuffer,
      final int oldLength, final int newLength) {
    return (boolean[]) doResize(oldBuffer, oldLength, newLength, Boolean.TYPE);
  }

  public final char[] expand(@Nullable final char[] oldBuffer,
      final int oldLength, final int newLength) {
    return (char[]) doExpand(oldBuffer, oldLength, newLength, Character.TYPE);
  }

  public final char[] shrink(@Nullable final char[] oldBuffer,
      final int oldLength) {
    return (char[]) doShrink(oldBuffer, oldLength, Character.TYPE);
  }

  public final char[] resize(@Nullable final char[] oldBuffer,
      final int oldLength, final int newLength) {
    return (char[]) doResize(oldBuffer, oldLength, newLength, Character.TYPE);
  }

  public final byte[] expand(@Nullable final byte[] oldBuffer,
      final int oldLength, final int newLength) {
    return (byte[]) doExpand(oldBuffer, oldLength, newLength, Byte.TYPE);
  }

  public final byte[] shrink(@Nullable final byte[] oldBuffer,
      final int oldLength) {
    return (byte[]) doShrink(oldBuffer, oldLength, Byte.TYPE);
  }

  public final byte[] resize(@Nullable final byte[] oldBuffer,
      final int oldLength, final int newLength) {
    return (byte[]) doResize(oldBuffer, oldLength, newLength, Byte.TYPE);
  }

  public final short[] expand(@Nullable final short[] oldBuffer,
      final int oldLength, final int newLength) {
    return (short[]) doExpand(oldBuffer, oldLength, newLength, Short.TYPE);
  }

  public final short[] shrink(@Nullable final short[] oldBuffer,
      final int oldLength) {
    return (short[]) doShrink(oldBuffer, oldLength, Short.TYPE);
  }

  public final short[] resize(@Nullable final short[] oldBuffer,
      final int oldLength, final int newLength) {
    return (short[]) doResize(oldBuffer, oldLength, newLength, Short.TYPE);
  }

  public final int[] expand(@Nullable final int[] oldBuffer,
      final int oldLength, final int newLength) {
    return (int[]) doExpand(oldBuffer, oldLength, newLength, Integer.TYPE);
  }

  public final int[] shrink(@Nullable final int[] oldBuffer, final int oldLength) {
    return (int[]) doShrink(oldBuffer, oldLength, Integer.TYPE);
  }

  public final int[] resize(@Nullable final int[] oldBuffer,
      final int oldLength, final int newLength) {
    return (int[]) doResize(oldBuffer, oldLength, newLength, Integer.TYPE);
  }

  public final long[] expand(@Nullable final long[] oldBuffer,
      final int oldLength, final int newLength) {
    return (long[]) doExpand(oldBuffer, oldLength, newLength, Long.TYPE);
  }

  public final long[] shrink(@Nullable final long[] oldBuffer,
      final int oldLength) {
    return (long[]) doShrink(oldBuffer, oldLength, Long.TYPE);
  }

  public final long[] resize(@Nullable final long[] oldBuffer,
      final int oldLength, final int newLength) {
    return (long[]) doResize(oldBuffer, oldLength, newLength, Long.TYPE);
  }

  public final float[] expand(@Nullable final float[] oldBuffer,
      final int oldLength, final int newLength) {
    return (float[]) doExpand(oldBuffer, oldLength, newLength, Float.TYPE);
  }

  public final float[] shrink(@Nullable final float[] oldBuffer,
      final int oldLength) {
    return (float[]) doShrink(oldBuffer, oldLength, Float.TYPE);
  }

  public final float[] resize(@Nullable final float[] oldBuffer,
      final int oldLength, final int newLength) {
    return (float[]) doResize(oldBuffer, oldLength, newLength, Float.TYPE);
  }

  public final double[] expand(@Nullable final double[] oldBuffer,
      final int oldLength, final int newLength) {
    return (double[]) doExpand(oldBuffer, oldLength, newLength, Double.TYPE);
  }

  public final double[] shrink(@Nullable final double[] oldBuffer,
      final int oldLength) {
    return (double[]) doShrink(oldBuffer, oldLength, Double.TYPE);
  }

  public final double[] resize(@Nullable final double[] oldBuffer,
      final int oldLength, final int newLength) {
    return (double[]) doResize(oldBuffer, oldLength, newLength, Double.TYPE);
  }

  @SuppressWarnings("unchecked")
  public final <T> T[] expand(@Nullable final T[] oldBuffer,
      final int oldLength, final int newLength, final Class<T> valueClass) {
    return (T[]) doExpand(oldBuffer, oldLength, newLength, valueClass);
  }

  @SuppressWarnings("unchecked")
  public final <T> T[] shrink(@Nullable final T[] oldBuffer,
      final int oldLength, final Class<T> valueClass) {
    return (T[]) doShrink(oldBuffer, oldLength, valueClass);
  }

  @SuppressWarnings("unchecked")
  public final <T> T[] resize(@Nullable final T[] oldBuffer,
      final int oldLength, final int newLength, final Class<T> valueClass) {
    return (T[]) doResize(oldBuffer, oldLength, newLength, valueClass);
  }

  private final Object doExpand(@Nullable final Object oldBuffer,
      final int oldLength, final int newLength, final Class<?> valueClass) {
    if ((oldLength < 0) || (newLength < 0)) {
      throw new IndexOutOfBoundsException();
    }
    if (oldBuffer == null) {
      return Array.newInstance(valueClass, newLength);
    }
    final int oldCapacity = Array.getLength(oldBuffer);
    if (newLength <= oldCapacity) {
      return oldBuffer; // don't need to expand
    }
    final int newCapacity = getNextCapacity(oldCapacity, newLength);
    if (newCapacity < newLength) {
      throw new RuntimeException(INVALID_IMPLEMENTATION);
    }
    final Object newBuffer = Array.newInstance(valueClass, newCapacity);
    if (oldLength > 0) {
      System.arraycopy(oldBuffer, 0, newBuffer, 0, oldLength);
    }
    return newBuffer;
  }

  private final Object doShrink(@Nullable final Object oldBuffer,
      final int oldLength, final Class<?> valueClass) {
    if (oldLength < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (oldBuffer == null) {
      return null;
    }
    final int oldCapacity = Array.getLength(oldBuffer);
    final int newCapacity = getShrinkCapacity(oldLength, oldCapacity);
    if (newCapacity == oldCapacity) {
      return oldBuffer;
    }
    if ((newCapacity < oldLength) || (newCapacity > oldCapacity)) {
      throw new RuntimeException(INVALID_IMPLEMENTATION);
    }
    final Object newBuffer = Array.newInstance(valueClass, newCapacity);
    if (oldLength > 0) {
      System.arraycopy(oldBuffer, 0, newBuffer, 0, oldLength);
    }
    return newBuffer;
  }

  private final Object doResize(@Nullable final Object oldBuffer,
      final int oldLength, final int newLength, final Class<?> valueClass) {
    if ((oldLength < 0) || (newLength < 0)) {
      throw new IndexOutOfBoundsException();
    }
    if (oldBuffer == null) {
      return Array.newInstance(valueClass, newLength);
    }
    final int oldCapacity = Array.getLength(oldBuffer);
    if (newLength == oldCapacity) {
      return oldBuffer;
    } else {
      final Object newBuffer = Array.newInstance(valueClass, newLength);
      final int n = (oldLength < newLength ? oldLength : newLength);
      if (n > 0) {
        System.arraycopy(oldBuffer, 0, newBuffer, 0, n);
      }
      return newBuffer;
    }
  }
}
