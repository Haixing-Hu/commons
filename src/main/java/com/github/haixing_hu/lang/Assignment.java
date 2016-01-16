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
package com.github.haixing_hu.lang;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 * Utility functions for helping implementation of the Assignable interface.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class Assignment {

  @SuppressWarnings("unchecked")
  public static <T extends Cloneable<? super T>>
  T clone(@Nullable final T obj) {
    if (obj == null) {
      return null;
    } else {
      return (T) obj.clone();
    }
  }

  public static Date clone(@Nullable final Date value) {
    return (value == null ? null : (Date) value.clone());
  }

  public static Time clone(@Nullable final Time value) {
    return (value == null ? null : (Time) value.clone());
  }

  public static Timestamp clone(@Nullable final Timestamp value) {
    return (value == null ? null : (Timestamp) value.clone());
  }

  public static char[] clone(@Nullable final char[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_CHAR_ARRAY;
    } else {
      final char[] result = new char[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static Character[] clone(@Nullable final Character[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY;
    } else {
      final Character[] result = new Character[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static boolean[] clone(@Nullable final boolean[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
    } else {
      final boolean[] result = new boolean[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static Boolean[] clone(@Nullable final Boolean[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY;
    } else {
      final Boolean[] result = new Boolean[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static byte[] clone(@Nullable final byte[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    } else {
      final byte[] result = new byte[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static Byte[] clone(@Nullable final Byte[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_BYTE_OBJECT_ARRAY;
    } else {
      final Byte[] result = new Byte[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static short[] clone(@Nullable final short[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_SHORT_ARRAY;
    } else {
      final short[] result = new short[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static Short[] clone(@Nullable final Short[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_SHORT_OBJECT_ARRAY;
    } else {
      final Short[] result = new Short[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static int[] clone(@Nullable final int[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_INT_ARRAY;
    } else {
      final int[] result = new int[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static Integer[] clone(@Nullable final Integer[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY;
    } else {
      final Integer[] result = new Integer[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static long[] clone(@Nullable final long[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_LONG_ARRAY;
    } else {
      final long[] result = new long[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static Long[] clone(@Nullable final Long[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
    } else {
      final Long[] result = new Long[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static float[] clone(@Nullable final float[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_FLOAT_ARRAY;
    } else {
      final float[] result = new float[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static Float[] clone(@Nullable final Float[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_FLOAT_OBJECT_ARRAY;
    } else {
      final Float[] result = new Float[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static double[] clone(@Nullable final double[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_DOUBLE_ARRAY;
    } else {
      final double[] result = new double[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static Double[] clone(@Nullable final Double[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_DOUBLE_OBJECT_ARRAY;
    } else {
      final Double[] result = new Double[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static String[] clone(@Nullable final String[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    } else {
      final String[] result = new String[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static Date[] clone(@Nullable final Date[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_DATE_ARRAY;
    } else {
      final Date[] result = new Date[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static Time[] clone(@Nullable final Time[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_TIME_ARRAY;
    } else {
      final Time[] result = new Time[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static Timestamp[] clone(@Nullable final Timestamp[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_TIMESTAMP_ARRAY;
    } else {
      final Timestamp[] result = new Timestamp[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static LocalDate[] clone(@Nullable final LocalDate[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_LOCAL_DATE_ARRAY;
    } else {
      final LocalDate[] result = new LocalDate[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static LocalTime[] clone(@Nullable final LocalTime[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_LOCAL_TIME_ARRAY;
    } else {
      final LocalTime[] result = new LocalTime[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static LocalDateTime[] clone(@Nullable final LocalDateTime[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_LOCAL_DATETIME_ARRAY;
    } else {
      final LocalDateTime[] result = new LocalDateTime[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static byte[][] clone(@Nullable final byte[][] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
    } else {
      final byte[][] result = new byte[array.length][];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static Class<?>[] clone(@Nullable final Class<?>[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_CLASS_ARRAY;
    } else {
      final Class<?>[] result = new Class<?>[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static BigInteger[] clone(@Nullable final BigInteger[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
    } else {
      final BigInteger[] result = new BigInteger[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  public static BigDecimal[] clone(@Nullable final BigDecimal[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
    } else {
      final BigDecimal[] result = new BigDecimal[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * Shallowly clones an array.
   * <p>
   * NOTE: The argument type T does NOT have to be cloneable, since this is a
   * shallow clone.
   *
   * @param <T>
   *          The type of the element of the array to be cloned.
   * @param array
   *          The source array to be cloned, which could be null.
   * @return The cloned copy of the source array; or null if the source array is
   *         null. Note that the objects in the source array is simply copied
   *         into the returned array, thus this is a shallow clone.
   */
  public static <T> T[] clone(@Nullable final T[] array) {
    if (array == null) {
      return null;
    } else {
      return array.clone();
    }
  }

  /**
   * Shallowly clones a list.
   *
   * @param <T>
   *          The type of the element of the list to be cloned.
   * @param list
   *          The source list to be cloned, which could be null.
   * @return The cloned copy of the source list; or null if the source list is
   *         null. Note that the objects in the source list is simply copied
   *         into the returned list, thus this is a shallow clone.
   */
  public static <T> List<T> clone(@Nullable final List<T> list) {
    if (list == null) {
      return null;
    } else {
      final List<T> result = new ArrayList<T>();
      result.addAll(list);
      return result;
    }
  }

  /**
   * Shallowly clones a set.
   *
   * @param <T>
   *          The type of the element of the set to be cloned.
   * @param set
   *          The source set to be cloned, which could be null.
   * @return The cloned copy of the source set; or null if the source set is
   *         null. Note that the objects in the source set is simply copied
   *         into the returned set, thus this is a shallow clone.
   */
  public static <T> Set<T> clone(@Nullable final Set<T> set) {
    if (set == null) {
      return null;
    } else {
      final Set<T> result = new HashSet<T>();
      result.addAll(set);
      return result;
    }
  }

  /**
   * Shallowly clones a map.
   *
   * @param <K>
   *          The type of the key of the map to be cloned.
   * @param <V>
   *          The type of the value of the map to be cloned.
   * @param map
   *          The source map to be cloned, which could be null.
   * @return The cloned copy of the source map; or null if the source map is
   *         null. Note that the objects in the source map is simply copied
   *         into the returned map, thus this is a shallow clone.
   */
  public static <K, V>
  Map<K, V> clone(@Nullable final Map<K, V> map) {
    if (map == null) {
      return null;
    } else {
      final Map<K, V> result = new HashMap<K, V>();
      result.putAll(map);
      return result;
    }
  }

  /**
   * Shallowly clones a multi-map.
   *
   * @param <K>
   *          The type of the key of the multi-map to be cloned.
   * @param <V>
   *          The type of the value of the multi-map to be cloned.
   * @param map
   *          The source multi-map to be cloned, which could be null.
   * @return The cloned copy of the source multi-map; or null if the source map is
   *         null. Note that the objects in the source multi-map is simply copied
   *         into the returned multi-map, thus this is a shallow clone.
   */
  public static <K, V>
  Multimap<K, V> clone(@Nullable final Multimap<K, V> map) {
    if (map == null) {
      return null;
    } else {
      final Multimap<K, V> result = LinkedHashMultimap.create();
      result.putAll(map);
      return result;
    }
  }

  public static Date[] deepClone(@Nullable final Date[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_DATE_ARRAY;
    } else {
      final Date[] result = new Date[array.length];
      for (int i = 0; i < array.length; ++i) {
        result[i] = clone(array[i]);
      }
      return result;
    }
  }

  public static Time[] deepClone(@Nullable final Time[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_TIME_ARRAY;
    } else {
      final Time[] result = new Time[array.length];
      for (int i = 0; i < array.length; ++i) {
        result[i] = clone(array[i]);
      }
      return result;
    }
  }

  public static Timestamp[] deepClone(@Nullable final Timestamp[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_TIMESTAMP_ARRAY;
    } else {
      final Timestamp[] result = new Timestamp[array.length];
      for (int i = 0; i < array.length; ++i) {
        result[i] = clone(array[i]);
      }
      return result;
    }
  }

  public static byte[][] deepClone(@Nullable final byte[][] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
    } else {
      final byte[][] result = new byte[array.length][];
      for (int i = 0; i < array.length; ++i) {
        result[i] = clone(array[i]);
      }
      return result;
    }
  }

  /**
   * Deeply clones an array.
   *
   * @param <T>
   *          The type of the element of the array to be cloned.
   * @param array
   *          The source array to be cloned, which could be null.
   * @return The cloned copy of the source array; or null if the source array is
   *         null. Note that the objects in the source array is also cloned
   *         into the returned array, thus this is a deep clone.
   */
  @SuppressWarnings("unchecked")
  public static <T extends Cloneable<? super T>>
  T[] deepClone(@Nullable final T[] array) {
    if (array == null) {
      return null;
    } else {
      final T[] result = array.clone();
      for (int i = 0; i < array.length; ++i) {
        final T obj = array[i];
        if (obj != null) {
          result[i] = (T) obj.clone();
        } else {
          result[i] = null;
        }
      }
      return result;
    }
  }

  /**
   * Deeply clones a list.
   *
   * @param <T>
   *          The type of the element of the list to be cloned.
   * @return The cloned copy of the source list; or null if the source list is
   *         null. Note that the objects in the source list is also cloned into
   *         the returned list, thus this is a deep clone.
   */
  @SuppressWarnings("unchecked")
  public static <T extends Cloneable<? super T>>
  List<T> deepClone(@Nullable final List<T> list) {
    if (list == null) {
      return null;
    } else {
      final List<T> result = new ArrayList<T>();
      for (final T t : list) {
        if (t == null) {
          result.add(null);
        } else {
          result.add((T) t.clone());
        }
      }
      return result;
    }
  }

  /**
   * Deeply clones a set.
   *
   * @param <T>
   *          The type of the element of the set to be cloned.
   * @param set
   *          The source set to be cloned, which could be null.
   * @return The cloned copy of the source set; or null if the source set is
   *         null. Note that the objects in the source set is also cloned into
   *         the returned set, thus this is a deep clone.
   */
  @SuppressWarnings("unchecked")
  public static <T extends Cloneable<? super T>>
  Set<T> deepClone(@Nullable final Set<T> set) {
    if (set == null) {
      return null;
    } else {
      final Set<T> result = new HashSet<T>();
      for (final T t : set) {
        if (t == null) {
          result.add(null);
        } else {
          result.add((T) t.clone());
        }
      }
      return result;
    }
  }

  /**
   * Deeply clones a map.
   *
   * @param <K>
   *          The type of the key of the map to be cloned.
   * @param <V>
   *          The type of the value of the map to be cloned.
   * @param map
   *          The source map to be cloned, which could be null.
   * @return The cloned copy of the source map; or null if the source map is
   *         null. Note that the values in the source map is also cloned
   *         into the returned map, thus this is a deep clone.
   */
  @SuppressWarnings("unchecked")
  public static <K, V extends Cloneable<? super V>>
  Map<K, V> deepClone(@Nullable final Map<K, V> map) {
    if (map == null) {
      return null;
    } else {
      final Map<K, V> result = new HashMap<K, V>();
      final Set<Map.Entry<K, V>> entries = map.entrySet();
      for (final Map.Entry<K, V> entry : entries) {
        final K key = entry.getKey();
        final V value = entry.getValue();
        if (value == null) {
          result.put(key, null);
        } else {
          result.put(key, (V) value.clone());
        }
      }
      return result;
    }
  }

  /**
   * Deeply clones a multi-map.
   *
   * @param <K>
   *          The type of the key of the multi-map to be cloned.
   * @param <V>
   *          The type of the value of the multi-map to be cloned.
   * @param map
   *          The source multi-map to be cloned, which could be null.
   * @return The cloned copy of the source multi-map; or null if the source
   *         multi-map is null. Note that the values in the source multi-map is
   *         also cloned into the returned multi-map, thus this is a deep clone.
   */
  @SuppressWarnings("unchecked")
  public static <K, V extends Cloneable<? super V>>
  Multimap<K, V> deepClone(@Nullable final Multimap<K, V> map) {
    if (map == null) {
      return null;
    } else {
      final Multimap<K, V> result = LinkedHashMultimap.create();
      final Collection<Map.Entry<K, V>> entries = map.entries();
      for (final Map.Entry<K, V> entry : entries) {
        final K key = entry.getKey();
        final V value = entry.getValue();
        if (value == null) {
          result.put(key, null);
        } else {
          result.put(key, (V) value.clone());
        }
      }
      return result;
    }
  }

  @SuppressWarnings("unchecked")
  public static <T extends Assignable<? super T>>
  T assign(@Nullable T left, @Nullable final T right) {
    if (right == null) {
      return null;
    } else {
      if (left == null) {
        left = (T) right.clone();
      } else {
        left.assign(right);
      }
      return left;
    }
  }

  public static Date assign(@Nullable final Date left,
      @Nullable final Date right) {
    if (right == null) {
      return null;
    } else if (left == null) {
      return (Date) right.clone();
    } else {
      left.setTime(right.getTime());
      return left;
    }
  }

  public static Time assign(@Nullable final Time left,
      @Nullable final Time right) {
    if (right == null) {
      return null;
    } else if (left == null) {
      return (Time) right.clone();
    } else {
      left.setTime(right.getTime());
      return left;
    }
  }

  public static Timestamp assign(@Nullable final Timestamp left,
      @Nullable final Timestamp right) {
    if (right == null) {
      return null;
    } else if (left == null) {
      return (Timestamp) right.clone();
    } else {
      left.setTime(right.getTime());
      left.setNanos(right.getNanos());
      return left;
    }
  }

  public static char[] assign(@Nullable char[] left,
      @Nullable final char[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_CHAR_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new char[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static Character[] assign(@Nullable Character[] left,
      @Nullable final Character[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new Character[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static boolean[] assign(@Nullable boolean[] left,
      @Nullable final boolean[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new boolean[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static Boolean[] assign(@Nullable Boolean[] left,
      @Nullable final Boolean[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new Boolean[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static byte[] assign(@Nullable byte[] left,
      @Nullable final byte[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new byte[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static Byte[] assign(@Nullable Byte[] left,
      @Nullable final Byte[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_BYTE_OBJECT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new Byte[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static short[] assign(@Nullable short[] left,
      @Nullable final short[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_SHORT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new short[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static Short[] assign(@Nullable Short[] left,
      @Nullable final Short[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_SHORT_OBJECT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new Short[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static int[] assign(@Nullable int[] left,
      @Nullable final int[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_INT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new int[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static Integer[] assign(@Nullable Integer[] left,
      @Nullable final Integer[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new Integer[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static long[] assign(@Nullable long[] left,
      @Nullable final long[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_LONG_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new long[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static Long[] assign(@Nullable Long[] left,
      @Nullable final Long[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new Long[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static float[] assign(@Nullable float[] left,
      @Nullable final float[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_FLOAT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new float[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static Float[] assign(@Nullable Float[] left,
      @Nullable final Float[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_FLOAT_OBJECT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new Float[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static double[] assign(@Nullable double[] left,
      @Nullable final double[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_DOUBLE_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new double[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static Double[] assign(@Nullable Double[] left,
      @Nullable final Double[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_DOUBLE_OBJECT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new Double[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static String[] assign(@Nullable String[] left,
      @Nullable final String[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new String[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static Date[] assign(@Nullable Date[] left,
      @Nullable final Date[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_DATE_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new Date[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static Time[] assign(@Nullable Time[] left,
      @Nullable final Time[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_TIME_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new Time[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static Timestamp[] assign(@Nullable Timestamp[] left,
      @Nullable final Timestamp[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_TIMESTAMP_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new Timestamp[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static byte[][] assign(@Nullable byte[][] left,
      @Nullable final byte[][] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new byte[right.length][];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static Class<?>[] assign(@Nullable Class<?>[] left,
      @Nullable final Class<?>[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_CLASS_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new Class<?>[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static BigInteger[] assign(@Nullable BigInteger[] left,
      @Nullable final BigInteger[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new BigInteger[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static BigDecimal[] assign(@Nullable BigDecimal[] left,
      @Nullable final BigDecimal[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new BigDecimal[right.length];
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T[] assign(@Nullable T[] left,
      @Nullable final T[] right, final Class<T> clazz) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return (T[]) Array.newInstance(clazz, 0);
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = (T[]) Array.newInstance(clazz, right.length);
      }
      System.arraycopy(right, 0, left, 0, right.length);
      return left;
    }
  }

  public static <T> List<T> assign(@Nullable List<T> left,
      @Nullable final List<T> right) {
    if (right == null) {
      if (left != null) {
        left.clear();
      }
    } else {
      if (left == null) {
        left = new ArrayList<T>();
      } else {
        left.clear();
      }
      left.addAll(right);
    }
    return left;
  }

  public static <T> Set<T> assign(@Nullable Set<T> left,
      @Nullable final Set<T> right) {
    if (right == null) {
      if (left != null) {
        left.clear();
      }
    } else {
      if (left == null) {
        left = new HashSet<T>();
      } else {
        left.clear();
      }
      left.addAll(right);
    }
    return left;
  }

  public static <K, V>
  Map<K, V> assign(@Nullable Map<K, V> left,
      @Nullable final Map<K, V> right) {
    if (right == null) {
      if (left != null) {
        left.clear();
      }
    } else {
      if (left == null) {
        left = new HashMap<K, V>();
      } else {
        left.clear();
      }
      left.putAll(right);
    }
    return left;
  }

  public static <K, V>
  Multimap<K, V> assign(@Nullable Multimap<K, V> left,
      @Nullable final Multimap<K, V> right) {
    if (right == null) {
      if (left != null) {
        left.clear();
      }
    } else {
      if (left == null) {
        left = LinkedHashMultimap.create();
      } else {
        left.clear();
      }
      left.putAll(right);
    }
    return left;
  }

  public static Date[] deepAssign(@Nullable Date[] left,
      @Nullable final Date[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_DATE_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new Date[right.length];
      }
      for (int i = 0; i < right.length; ++i) {
        left[i] = assign(left[i], right[i]);
      }
      return left;
    }
  }

  public static Time[] deepAssign(@Nullable Time[] left,
      @Nullable final Time[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_TIME_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new Time[right.length];
      }
      for (int i = 0; i < right.length; ++i) {
        left[i] = assign(left[i], right[i]);
      }
      return left;
    }
  }

  public static Timestamp[] deepAssign(@Nullable Timestamp[] left,
      @Nullable final Timestamp[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_TIMESTAMP_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new Timestamp[right.length];
      }
      for (int i = 0; i < right.length; ++i) {
        left[i] = assign(left[i], right[i]);
      }
      return left;
    }
  }

  public static byte[][] deepAssign(@Nullable byte[][] left,
      @Nullable final byte[][] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = new byte[right.length][];
      }
      for (int i = 0; i < right.length; ++i) {
        left[i] = assign(left[i], right[i]);
      }
      return left;
    }
  }


  @SuppressWarnings("unchecked")
  public static <T extends Assignable<? super T>>
  T[] deepAssign(@Nullable T[] left, @Nullable final T[] right,
      final Class<T> clazz) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return (T[]) Array.newInstance(clazz, 0);
    } else {
      if ((left == null) || (left.length != right.length)) {
        left = (T[]) Array.newInstance(clazz, right.length);
      }
      for (int i = 0; i < right.length; ++i) {
        if (right[i] == null) {
          left[i] = null;
        } else if (left[i] == null) {
          left[i] = (T) right[i].clone();
        } else {
          left[i].assign(right[i]);
        }
      }
      return left;
    }
  }

  @SuppressWarnings("unchecked")
  public static <T extends Assignable<? super T>>
  List<T> deepAssign(@Nullable List<T> left,
      @Nullable final List<T> right) {
    if (right == null) {
      return null;
    } else {
      if (left == null) {
        left = new ArrayList<T>();
      } else {
        left.clear();
      }
      for (final T t : right) {
        if (t == null) {
          left.add(null);
        } else {
          left.add((T) t.clone());
        }
      }
      return left;
    }
  }

  @SuppressWarnings("unchecked")
  public static <T extends Assignable<? super T>>
  Set<T> deepAssign(@Nullable Set<T> left,
      @Nullable final Set<T> right) {
    if (right == null) {
      return null;
    } else {
      if (left == null) {
        left = new HashSet<T>();
      } else {
        left.clear();
      }
      for (final T t : right) {
        if (t == null) {
          left.add(null);
        } else {
          left.add((T) t.clone());
        }
      }
      return left;
    }
  }

  @SuppressWarnings("unchecked")
  public static <K, V extends Assignable<? super V>>
  Map<K, V> deepAssign(@Nullable Map<K, V> left,
      @Nullable final Map<K, V> right) {
    if (right == null) {
      return null;
    } else {
      if (left == null) {
        left = new HashMap<K, V>();
      } else {
        left.clear();
      }
      final Set<Map.Entry<K, V>> entries = right.entrySet();
      for (final Map.Entry<K, V> entry : entries) {
        final K key = entry.getKey();
        final V value = entry.getValue();
        if (value == null) {
          left.put(key, null);
        } else {
          left.put(key, (V) value.clone());
        }
      }
      return left;
    }
  }

  @SuppressWarnings("unchecked")
  public static <K, V extends Assignable<? super V>>
  Multimap<K, V> deepAssign(@Nullable Multimap<K, V> left,
      @Nullable final Multimap<K, V> right) {
    if (right == null) {
      return null;
    } else {
      if (left == null) {
        left = LinkedHashMultimap.create();
      } else {
        left.clear();
      }
      final Collection<Map.Entry<K, V>> entries = right.entries();
      for (final Map.Entry<K, V> entry : entries) {
        final K key = entry.getKey();
        final V value = entry.getValue();
        if (value == null) {
          left.put(key, null);
        } else {
          left.put(key, (V) value.clone());
        }
      }
      return left;
    }
  }

}
