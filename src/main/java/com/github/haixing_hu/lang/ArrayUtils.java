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

import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.RandomAccess;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import com.github.haixing_hu.text.tostring.SimpleToStringStyle;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * This class provides operations on arrays, primitive arrays (like
 * {@code int[]}) and primitive wrapper arrays (like {@code Integer[]}).
 *
 * This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} array input. However, an Object
 * array that contains a {@code null} element may throw an exception. Each
 * method documents its behavior.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public class ArrayUtils {

  /**
   * An empty immutable {@code Object} array.
   */
  public static final Object[]    EMPTY_OBJECT_ARRAY           = new Object[0];

  /**
   * An empty immutable {@code Class} array.
   */
  public static final Class<?>[]  EMPTY_CLASS_ARRAY            = new Class<?>[0];

  /**
   * An empty immutable {@code String} array.
   */
  public static final String[]    EMPTY_STRING_ARRAY           = new String[0];

  /**
   * An empty immutable {@code long} array.
   */
  public static final long[]      EMPTY_LONG_ARRAY             = new long[0];

  /**
   * An empty immutable {@code Long} array.
   */
  public static final Long[]      EMPTY_LONG_OBJECT_ARRAY      = new Long[0];

  /**
   * An empty immutable {@code int} array.
   */
  public static final int[]       EMPTY_INT_ARRAY              = new int[0];

  /**
   * An empty immutable {@code Integer} array.
   */
  public static final Integer[]   EMPTY_INTEGER_OBJECT_ARRAY   = new Integer[0];

  /**
   * An empty immutable {@code short} array.
   */
  public static final short[]     EMPTY_SHORT_ARRAY            = new short[0];

  /**
   * An empty immutable {@code Short} array.
   */
  public static final Short[]     EMPTY_SHORT_OBJECT_ARRAY     = new Short[0];

  /**
   * An empty immutable {@code byte} array.
   */
  public static final byte[]      EMPTY_BYTE_ARRAY             = new byte[0];

  /**
   * An empty immutable {@code Byte} array.
   */
  public static final Byte[]      EMPTY_BYTE_OBJECT_ARRAY      = new Byte[0];

  /**
   * An empty immutable {@code double} array.
   */
  public static final double[]    EMPTY_DOUBLE_ARRAY           = new double[0];

  /**
   * An empty immutable {@code Double} array.
   */
  public static final Double[]    EMPTY_DOUBLE_OBJECT_ARRAY    = new Double[0];

  /**
   * An empty immutable {@code float} array.
   */
  public static final float[]     EMPTY_FLOAT_ARRAY            = new float[0];

  /**
   * An empty immutable {@code Float} array.
   */
  public static final Float[]     EMPTY_FLOAT_OBJECT_ARRAY     = new Float[0];

  /**
   * An empty immutable {@code boolean} array.
   */
  public static final boolean[]   EMPTY_BOOLEAN_ARRAY          = new boolean[0];

  /**
   * An empty immutable {@code Boolean} array.
   */
  public static final Boolean[]   EMPTY_BOOLEAN_OBJECT_ARRAY   = new Boolean[0];

  /**
   * An empty immutable {@code char} array.
   */
  public static final char[]      EMPTY_CHAR_ARRAY             = new char[0];

  /**
   * An empty immutable {@code Character} array.
   */
  public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];

  /**
   * An empty immutable {@code byte[]} array.
   */
  public static final byte[][]    EMPTY_BYTE_ARRAY_ARRAY       = new byte[0][];

  /**
   * An empty immutable {@link BigIngeger} array.
   */
  public static final BigInteger[] EMPTY_BIG_INTEGER_ARRAY     = new BigInteger[0];

  /**
   * An empty immutable {@link BigDecimal} array.
   */
  public static final BigDecimal[] EMPTY_BIG_DECIMAL_ARRAY     = new BigDecimal[0];

  /**
   * An empty immutable {@link Date} array.
   */
  public static final Date[]       EMPTY_DATE_ARRAY            = new Date[0];

  /**
   * An empty immutable {@link Time} array.
   */
  public static final Time[]       EMPTY_TIME_ARRAY            = new Time[0];

  /**
   * An empty immutable {@link Timestamp} array.
   */
  public static final Timestamp[] EMPTY_TIMESTAMP_ARRAY        = new Timestamp[0];

  /**
   * The current value when an element is not found in a list or array:
   * {@code -1}. This value is returned by methods in this class and can
   * also be used in comparisons with values returned by various method from
   * {@link java.util.List}.
   */
  public static final int         INDEX_NOT_FOUND              = -1;

  private ArrayUtils() { }

  /**
   * Outputs an array as a String, treating {@code null} as an empty array.
   *
   * Multi-dimensional arrays are handled correctly, including multi-dimensional
   * primitive arrays.
   *
   * The format is that of Java source code, for example {@code {a,b}}.
   *
   * @param array
   *          the array to get a toString for, may be {@code null}
   * @return a String representation of the array, '{}' if null array input
   */
  public static String toString(final Object array) {
    return toString(array, "{}");
  }

  /**
   * Outputs an array as a String handling {@code null}s.
   *
   * Multi-dimensional arrays are handled correctly, including multi-dimensional
   * primitive arrays.
   *
   * The format is that of Java source code, for example {@code {a,b}}.
   *
   * @param array
   *          the array to get a toString for, may be {@code null}
   * @param stringIfNull
   *          the String to return if the array is {@code null}
   * @return a String representation of the array
   */
  public static String toString(final Object array, final String stringIfNull) {
    if (array == null) {
      return stringIfNull;
    }
    return new ToStringBuilder(SimpleToStringStyle.INSTANCE)
               .append(array)
               .toString();
  }

  /**
   * Get a hashCode for an array handling multi-dimensional arrays correctly.
   *
   * Multi-dimensional primitive arrays are also handled correctly by this
   * method.
   *
   * @param array
   *          the array to get a hashCode for, may be {@code null}
   * @return a hashCode for the array, zero if null array input.
   */
  public static int hashCode(final Object array) {
    int code = 121;
    final int multiplier = 13333;
    code = Hash.combine(code, multiplier, array);
    return code;
  }

  /**
   * Converts the given array into a {@link java.util.Map}. Each element of the
   * array must be either a {@link java.util.Map.Entry} or an Buffer, containing
   * at least two elements, where the first element is used as key and the
   * second as value.
   *
   * This method can be used to initialize:
   *
   * <pre>
   * // Create a Map mapping colors.
   * Map colorMap = MapUtils.toMap(new String[][] {{
   *     {"RED", "#FF0000"},
   *     {"GREEN", "#00FF00"},
   *     {"BLUE", "#0000FF"}});
   * </pre>
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          an array whose elements are either a {@link java.util.Map.Entry}
   *          or an Buffer containing at least two elements, may be
   *          {@code null}
   * @return a {@code Map} that was created from the array
   * @throws IllegalArgumentException
   *           if one element of this Buffer is itself an Buffer containing less
   *           then two elements
   * @throws IllegalArgumentException
   *           if the array contains elements other than
   *           {@link java.util.Map.Entry} and an Buffer
   */
/*  public static Map toMap(Object[] array) {
    if (array == null) {
      return null;
    }
    final Map map = new HashMap((int) (array.length * 1.5));
    for (int i = 0; i < array.length; ++i) {
      Object object = array[i];
      if (object instanceof Map.Entry) {
        Map.Entry entry = (Map.Entry) object;
        map.put(entry.getKey(), entry.getValue());
      } else if (object instanceof Object[]) {
        Object[] entry = (Object[]) object;
        if (entry.length < 2) {
          throw new IllegalArgumentException("Buffer element " + i + ", '"
              + object + "', has a length less than 2");
        }
        map.put(entry[0], entry[1]);
      } else {
        throw new IllegalArgumentException("Buffer element " + i + ", '"
            + object + "', is neither of type Map.Entry nor an Buffer");
      }
    }
    return map;
  }*/

  public static <T extends Comparable<? super T>>
  T max(@Nullable T[] array, int start, int end) {
    if (array == null) {
      return null;
    }
    if (start < 0) {
      start = 0;
    }
    if (end > array.length) {
      end = array.length;
    }
    if (start >= end) {
      return null;
    }
    T result = array[start];
    for (int i = start + 1; i < end; ++i) {
      if (array[i] != null) {
        if (result == null) {
          result = array[i];
        } else if (result.compareTo(array[i]) < 0) {
          result = array[i];
        }
      }
    }
    return result;
  }

  public static <T extends Comparable<? super T>> T max(@Nullable T[] array) {
    if (array == null) {
      return null;
    }
    return max(array, 0, array.length);
  }

  public static <T extends Comparable<? super T>>
  T min(@Nullable T[] array, int start, int end) {
    if (array == null) {
      return null;
    }
    if (start < 0) {
      start = 0;
    }
    if (end > array.length) {
      end = array.length;
    }
    if (start >= end) {
      return null;
    }
    T result = array[start];
    for (int i = start + 1; i < end; ++i) {
      if (array[i] != null) {
        if (result == null) {
          result = null;
        } else if (result.compareTo(array[i]) > 0) {
          result = array[i];
        }
      }
    }
    return result;
  }

  public static <T extends Comparable<? super T>> T min(@Nullable T[] array) {
    if (array == null) {
      return null;
    }
    return min(array, 0, array.length);
  }

  /**
   * Produces a new array containing the elements between the start and end
   * indices.
   *
   * The start current is inclusive, the end current exclusive. Null array input
   * produces null output.
   *
   * The component type of the subarray is always the same as that of the input
   * array. Thus, if the input is an array of type {@code Date}, the
   * following usage is envisaged:
   *
   * <pre>
   * Date[] someDates = Arrays.subarray(allDates, 2, 5);
   * </pre>
   *
   * @param array
   *          the array
   * @param start
   *          the starting current. Undervalue (&lt;0) is promoted to 0, overvalue
   *          (&gt;array.length) results in an empty array.
   * @param end
   *          elements up to endIndex-1 are present in the returned subarray.
   *          Undervalue (&lt; startIndex) produces empty array, overvalue
   *          (&gt;array.length) is demoted to array length.
   * @return a new array containing the elements between the start and end
   *         indices.
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] subarray(final T[] array, int start, int end) {
    if (array == null) {
      return null;
    }
    if (start < 0) {
      start = 0;
    }
    if (end > array.length) {
      end = array.length;
    }
    final int newSize = end - start;
    final Class<?> type = array.getClass().getComponentType();
    if (newSize <= 0) {
      return (T[]) Array.newInstance(type, 0);
    } else {
      final T[] result = (T[]) Array.newInstance(type, newSize);
      System.arraycopy(array, start, result, 0, newSize);
      return result;
    }
  }

  /**
   * Produces a new {@code long} array containing the elements between the
   * start and end indices.
   *
   * The start current is inclusive, the end current exclusive. Null array input
   * produces null output.
   *
   * @param array
   *          the array
   * @param start
   *          the starting current. Undervalue (&lt;0) is promoted to 0, overvalue
   *          (&gt;array.length) results in an empty array.
   * @param end
   *          elements up to endIndex-1 are present in the returned subarray.
   *          Undervalue (&lt; startIndex) produces empty array, overvalue
   *          (&gt;array.length) is demoted to array length.
   * @return a new array containing the elements between the start and end
   *         indices.
   */
  public static long[] subarray(final long[] array, int start, int end) {
    if (array == null) {
      return null;
    }
    if (start < 0) {
      start = 0;
    }
    if (end > array.length) {
      end = array.length;
    }
    final int newSize = end - start;
    if (newSize <= 0) {
      return EMPTY_LONG_ARRAY;
    } else {
      final long[] result = new long[newSize];
      System.arraycopy(array, start, result, 0, newSize);
      return result;
    }
  }

  /**
   * Produces a new {@code int} array containing the elements between the
   * start and end indices.
   *
   * The start current is inclusive, the end current exclusive. Null array input
   * produces null output.
   *
   * @param array
   *          the array
   * @param start
   *          the starting current. Undervalue (&lt;0) is promoted to 0, overvalue
   *          (&gt;array.length) results in an empty array.
   * @param end
   *          elements up to endIndex-1 are present in the returned subarray.
   *          Undervalue (&lt; startIndex) produces empty array, overvalue
   *          (&gt;array.length) is demoted to array length.
   * @return a new array containing the elements between the start and end
   *         indices.
   */
  public static int[] subarray(final int[] array, int start, int end) {
    if (array == null) {
      return null;
    }
    if (start < 0) {
      start = 0;
    }
    if (end > array.length) {
      end = array.length;
    }
    final int newSize = end - start;
    if (newSize <= 0) {
      return EMPTY_INT_ARRAY;
    } else {
      final int[] result = new int[newSize];
      System.arraycopy(array, start, result, 0, newSize);
      return result;
    }
  }

  /**
   * Produces a new {@code short} array containing the elements between the
   * start and end indices.
   *
   * The start current is inclusive, the end current exclusive. Null array input
   * produces null output.
   *
   * @param array
   *          the array
   * @param start
   *          the starting current. Undervalue (&lt;0) is promoted to 0, overvalue
   *          (&gt;array.length) results in an empty array.
   * @param end
   *          elements up to endIndex-1 are present in the returned subarray.
   *          Undervalue (&lt; startIndex) produces empty array, overvalue
   *          (&gt;array.length) is demoted to array length.
   * @return a new array containing the elements between the start and end
   *         indices.
   */
  public static short[] subarray(final short[] array, int start, int end) {
    if (array == null) {
      return null;
    }
    if (start < 0) {
      start = 0;
    }
    if (end > array.length) {
      end = array.length;
    }
    final int newSize = end - start;
    if (newSize <= 0) {
      return EMPTY_SHORT_ARRAY;
    } else {
      final short[] result = new short[newSize];
      System.arraycopy(array, start, result, 0, newSize);
      return result;
    }
  }

  /**
   * Produces a new {@code char} array containing the elements between the
   * start and end indices.
   *
   * The start current is inclusive, the end current exclusive. Null array input
   * produces null output.
   *
   * @param array
   *          the array
   * @param start
   *          the starting current. Undervalue (&lt;0) is promoted to 0, overvalue
   *          (&gt;array.length) results in an empty array.
   * @param end
   *          elements up to endIndex-1 are present in the returned subarray.
   *          Undervalue (&lt; startIndex) produces empty array, overvalue
   *          (&gt;array.length) is demoted to array length.
   * @return a new array containing the elements between the start and end
   *         indices.
   */
  public static char[] subarray(final char[] array, int start, int end) {
    if (array == null) {
      return null;
    }
    if (start < 0) {
      start = 0;
    }
    if (end > array.length) {
      end = array.length;
    }
    final int newSize = end - start;
    if (newSize <= 0) {
      return EMPTY_CHAR_ARRAY;
    } else {
      final char[] result = new char[newSize];
      System.arraycopy(array, start, result, 0, newSize);
      return result;
    }
  }

  /**
   * Produces a new {@code byte} array containing the elements between the
   * start and end indices.
   *
   * The start current is inclusive, the end current exclusive. Null array input
   * produces null output.
   *
   * @param array
   *          the array
   * @param start
   *          the starting current. Undervalue (&lt;0) is promoted to 0, overvalue
   *          (&gt;array.length) results in an empty array.
   * @param end
   *          elements up to endIndex-1 are present in the returned subarray.
   *          Undervalue (&lt; startIndex) produces empty array, overvalue
   *          (&gt;array.length) is demoted to array length.
   * @return a new array containing the elements between the start and end
   *         indices.
   */
  public static byte[] subarray(final byte[] array, int start, int end) {
    if (array == null) {
      return null;
    }
    if (start < 0) {
      start = 0;
    }
    if (end > array.length) {
      end = array.length;
    }
    final int newSize = end - start;
    if (newSize <= 0) {
      return EMPTY_BYTE_ARRAY;
    } else {
      final byte[] result = new byte[newSize];
      System.arraycopy(array, start, result, 0, newSize);
      return result;
    }
  }

  /**
   * Produces a new {@code double} array containing the elements between
   * the start and end indices.
   *
   * The start current is inclusive, the end current exclusive. Null array input
   * produces null output.
   *
   * @param array
   *          the array
   * @param start
   *          the starting current. Undervalue (&lt;0) is promoted to 0, overvalue
   *          (&gt;array.length) results in an empty array.
   * @param end
   *          elements up to endIndex-1 are present in the returned subarray.
   *          Undervalue (&lt; startIndex) produces empty array, overvalue
   *          (&gt;array.length) is demoted to array length.
   * @return a new array containing the elements between the start and end
   *         indices.
   */
  public static double[] subarray(final double[] array, int start, int end) {
    if (array == null) {
      return null;
    }
    if (start < 0) {
      start = 0;
    }
    if (end > array.length) {
      end = array.length;
    }
    final int newSize = end - start;
    if (newSize <= 0) {
      return EMPTY_DOUBLE_ARRAY;
    } else {
      final double[] result = new double[newSize];
      System.arraycopy(array, start, result, 0, newSize);
      return result;
    }
  }

  /**
   * Produces a new {@code float} array containing the elements between the
   * start and end indices.
   *
   * The start current is inclusive, the end current exclusive. Null array input
   * produces null output.
   *
   * @param array
   *          the array
   * @param start
   *          the starting current. Undervalue (&lt;0) is promoted to 0, overvalue
   *          (&gt;array.length) results in an empty array.
   * @param end
   *          elements up to endIndex-1 are present in the returned subarray.
   *          Undervalue (&lt; startIndex) produces empty array, overvalue
   *          (&gt;array.length) is demoted to array length.
   * @return a new array containing the elements between the start and end
   *         indices.
   */
  public static float[] subarray(final float[] array, int start, int end) {
    if (array == null) {
      return null;
    }
    if (start < 0) {
      start = 0;
    }
    if (end > array.length) {
      end = array.length;
    }
    final int newSize = end - start;
    if (newSize <= 0) {
      return EMPTY_FLOAT_ARRAY;
    } else {
      final float[] result = new float[newSize];
      System.arraycopy(array, start, result, 0, newSize);
      return result;
    }
  }

  /**
   * Produces a new {@code boolean} array containing the elements between
   * the start and end indices.
   *
   * The start current is inclusive, the end current exclusive. Null array input
   * produces null output.
   *
   * @param array
   *          the array
   * @param start
   *          the starting current. Undervalue (&lt;0) is promoted to 0, overvalue
   *          (&gt;array.length) results in an empty array.
   * @param end
   *          elements up to endIndex-1 are present in the returned subarray.
   *          Undervalue (&lt; startIndex) produces empty array, overvalue
   *          (&gt;array.length) is demoted to array length.
   * @return a new array containing the elements between the start and end
   *         indices.
   */
  public static boolean[] subarray(final boolean[] array, int start, int end) {
    if (array == null) {
      return null;
    }
    if (start < 0) {
      start = 0;
    }
    if (end > array.length) {
      end = array.length;
    }
    final int newSize = end - start;
    if (newSize <= 0) {
      return EMPTY_BOOLEAN_ARRAY;
    } else {
      final boolean[] result = new boolean[newSize];
      System.arraycopy(array, start, result, 0, newSize);
      return result;
    }
  }

  /**
   * Tests whether an object is an array.
   *
   * @param obj
   *          the object to be test, which could be null.
   * @return true if the object is not null and is an array; false otherwise.
   */
  public static boolean isArray(@Nullable final Object obj) {
    return ((obj != null) && obj.getClass().isArray());
  }

  /**
   * Checks whether two arrays are the same length, treating {@code null}
   * arrays as length {@code 0}.
   *
   * Any multi-dimensional aspects of the arrays are ignored.
   *
   * @param array1
   *          the first array, may be {@code null}
   * @param array2
   *          the second array, may be {@code null}
   * @return {@code true} if length of arrays matches, treating
   *         {@code null} as an empty array
   */
  public static <T> boolean isSameLength(final T[] array1, final T[] array2) {
    if (((array1 == null) && (array2 != null) && (array2.length > 0))
        || ((array2 == null) && (array1 != null) && (array1.length > 0))
        || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Checks whether two arrays are the same length, treating {@code null}
   * arrays as length {@code 0}.
   *
   * @param array1
   *          the first array, may be {@code null}
   * @param array2
   *          the second array, may be {@code null}
   * @return {@code true} if length of arrays matches, treating
   *         {@code null} as an empty array
   */
  public static boolean isSameLength(final long[] array1, final long[] array2) {
    if (((array1 == null) && (array2 != null) && (array2.length > 0))
        || ((array2 == null) && (array1 != null) && (array1.length > 0))
        || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Checks whether two arrays are the same length, treating {@code null}
   * arrays as length {@code 0}.
   *
   * @param array1
   *          the first array, may be {@code null}
   * @param array2
   *          the second array, may be {@code null}
   * @return {@code true} if length of arrays matches, treating
   *         {@code null} as an empty array
   */
  public static boolean isSameLength(final int[] array1, final int[] array2) {
    if (((array1 == null) && (array2 != null) && (array2.length > 0))
        || ((array2 == null) && (array1 != null) && (array1.length > 0))
        || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Checks whether two arrays are the same length, treating {@code null}
   * arrays as length {@code 0}.
   *
   * @param array1
   *          the first array, may be {@code null}
   * @param array2
   *          the second array, may be {@code null}
   * @return {@code true} if length of arrays matches, treating
   *         {@code null} as an empty array
   */
  public static boolean isSameLength(final short[] array1, final short[] array2) {
    if (((array1 == null) && (array2 != null) && (array2.length > 0))
        || ((array2 == null) && (array1 != null) && (array1.length > 0))
        || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Checks whether two arrays are the same length, treating {@code null}
   * arrays as length {@code 0}.
   *
   * @param array1
   *          the first array, may be {@code null}
   * @param array2
   *          the second array, may be {@code null}
   * @return {@code true} if length of arrays matches, treating
   *         {@code null} as an empty array
   */
  public static boolean isSameLength(final char[] array1, final char[] array2) {
    if (((array1 == null) && (array2 != null) && (array2.length > 0))
        || ((array2 == null) && (array1 != null) && (array1.length > 0))
        || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Checks whether two arrays are the same length, treating {@code null}
   * arrays as length {@code 0}.
   *
   * @param array1
   *          the first array, may be {@code null}
   * @param array2
   *          the second array, may be {@code null}
   * @return {@code true} if length of arrays matches, treating
   *         {@code null} as an empty array
   */
  public static boolean isSameLength(final byte[] array1, final byte[] array2) {
    if (((array1 == null) && (array2 != null) && (array2.length > 0))
        || ((array2 == null) && (array1 != null) && (array1.length > 0))
        || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Checks whether two arrays are the same length, treating {@code null}
   * arrays as length {@code 0}.
   *
   * @param array1
   *          the first array, may be {@code null}
   * @param array2
   *          the second array, may be {@code null}
   * @return {@code true} if length of arrays matches, treating
   *         {@code null} as an empty array
   */
  public static boolean isSameLength(final double[] array1, final double[] array2) {
    if (((array1 == null) && (array2 != null) && (array2.length > 0))
        || ((array2 == null) && (array1 != null) && (array1.length > 0))
        || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Checks whether two arrays are the same length, treating {@code null}
   * arrays as length {@code 0}.
   *
   * @param array1
   *          the first array, may be {@code null}
   * @param array2
   *          the second array, may be {@code null}
   * @return {@code true} if length of arrays matches, treating
   *         {@code null} as an empty array
   */
  public static boolean isSameLength(final float[] array1, final float[] array2) {
    if (((array1 == null) && (array2 != null) && (array2.length > 0))
        || ((array2 == null) && (array1 != null) && (array1.length > 0))
        || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Checks whether two arrays are the same length, treating {@code null}
   * arrays as length {@code 0}.
   *
   * @param array1
   *          the first array, may be {@code null}
   * @param array2
   *          the second array, may be {@code null}
   * @return {@code true} if length of arrays matches, treating
   *         {@code null} as an empty array
   */
  public static boolean isSameLength(final boolean[] array1, final boolean[] array2) {
    if (((array1 == null) && (array2 != null) && (array2.length > 0))
        || ((array2 == null) && (array1 != null) && (array1.length > 0))
        || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Returns the length of the specified array. This method can deal with
   * {@code Object} arrays and with primitive arrays.
   *
   * If the input array is {@code null}, {@code 0} is returned.
   *
   * <pre>
   * Arrays.getLength(null)            = 0
   * Arrays.getLength([])              = 0
   * Arrays.getLength([null])          = 1
   * Arrays.getLength([true, false])   = 2
   * Arrays.getLength([1, 2, 3])       = 3
   * Arrays.getLength(["a", "b", "c"]) = 3
   * </pre>
   *
   * @param array
   *          the array to retrieve the length from, may be null
   * @return The length of the array, or {@code 0} if the array is
   *         {@code null}
   * @throws IllegalArgumentException
   *           if the object arguement is not an array.
   */
  public static int getLength(final Object array) {
    if (array == null) {
      return 0;
    } else {
      return Array.getLength(array);
    }
  }

  /**
   * Checks whether two arrays are the same type taking into account
   * multi-dimensional arrays.
   *
   * @param array1
   *          the first array, must not be {@code null}
   * @param array2
   *          the second array, must not be {@code null}
   * @return {@code true} if type of arrays matches
   * @throws IllegalArgumentException
   *           if either array is {@code null}
   */
  public static boolean isSameType(final Object array1, final Object array2) {
    if ((array1 == null) || (array2 == null)) {
      throw new IllegalArgumentException("The Buffer must not be null");
    } else {
      final String array1ClassName = array1.getClass().getName();
      final String array2ClassName = array2.getClass().getName();
      return array1ClassName.equals(array2ClassName);
    }
  }

  /**
   * Reverses the order of the given array.
   *
   * There is no special handling for multi-dimensional arrays.
   *
   * This method does nothing for a {@code null} input array.
   *
   * @param array
   *          the array to reverse, may be {@code null}
   */
  public static <T> void reverse(final T[] array) {
    if (array == null) {
      return;
    }
    int i = 0;
    int j = array.length - 1;
    while (j > i) {
      final T tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      --j;
      ++i;
    }
  }

  /**
   * Reverses the order of the given array.
   *
   * This method does nothing for a {@code null} input array.
   *
   * @param array
   *          the array to reverse, may be {@code null}
   */
  public static void reverse(final long[] array) {
    if (array == null) {
      return;
    }
    int i = 0;
    int j = array.length - 1;
    while (j > i) {
      final long tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      --j;
      ++i;
    }
  }

  /**
   * Reverses the order of the given array.
   *
   * This method does nothing for a {@code null} input array.
   *
   * @param array
   *          the array to reverse, may be {@code null}
   */
  public static void reverse(final int[] array) {
    if (array == null) {
      return;
    }
    int i = 0;
    int j = array.length - 1;
    while (j > i) {
      final int tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      --j;
      ++i;
    }
  }

  /**
   * Reverses the order of the given array.
   *
   * This method does nothing for a {@code null} input array.
   *
   * @param array
   *          the array to reverse, may be {@code null}
   */
  public static void reverse(final short[] array) {
    if (array == null) {
      return;
    }
    int i = 0;
    int j = array.length - 1;
    while (j > i) {
      final short tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      --j;
      ++i;
    }
  }

  /**
   * Reverses the order of the given array.
   *
   * This method does nothing for a {@code null} input array.
   *
   * @param array
   *          the array to reverse, may be {@code null}
   */
  public static void reverse(final char[] array) {
    if (array == null) {
      return;
    }
    int i = 0;
    int j = array.length - 1;
    while (j > i) {
      final char tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      --j;
      ++i;
    }
  }

  /**
   * Reverses the order of the given array.
   *
   * This method does nothing for a {@code null} input array.
   *
   * @param array
   *          the array to reverse, may be {@code null}
   */
  public static void reverse(final byte[] array) {
    if (array == null) {
      return;
    }
    int i = 0;
    int j = array.length - 1;
    while (j > i) {
      final byte tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      --j;
      ++i;
    }
  }

  /**
   * Reverses the order of the given array.
   *
   * This method does nothing for a {@code null} input array.
   *
   * @param array
   *          the array to reverse, may be {@code null}
   */
  public static void reverse(final double[] array) {
    if (array == null) {
      return;
    }
    int i = 0;
    int j = array.length - 1;
    while (j > i) {
      final double tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      --j;
      ++i;
    }
  }

  /**
   * Reverses the order of the given array.
   *
   * This method does nothing for a {@code null} input array.
   *
   * @param array
   *          the array to reverse, may be {@code null}
   */
  public static void reverse(final float[] array) {
    if (array == null) {
      return;
    }
    int i = 0;
    int j = array.length - 1;
    while (j > i) {
      final float tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      --j;
      ++i;
    }
  }

  /**
   * Reverses the order of the given array.
   *
   * This method does nothing for a {@code null} input array.
   *
   * @param array
   *          the array to reverse, may be {@code null}
   */
  public static void reverse(final boolean[] array) {
    if (array == null) {
      return;
    }
    int i = 0;
    int j = array.length - 1;
    while (j > i) {
      final boolean tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      --j;
      ++i;
    }
  }

  /**
   * Finds the current of the given object in the array.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param objectToFind
   *          the object to find, may be {@code null}
   * @return the current of the object within the array, {@link #INDEX_NOT_FOUND}
   *         ({@code -1}) if not found or {@code null} array input
   */
  public static <T> int indexOf(final T[] array, final T objectToFind) {
    return indexOf(array, objectToFind, 0);
  }

  /**
   * Finds the current of the given object in the array starting at the given
   * current.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param objectToFind
   *          the object to find, may be {@code null}
   * @param startIndex
   *          the current to start searching at
   * @return the current of the object within the array starting at the current,
   *         {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or
   *         {@code null} array input
   */
  public static <T> int indexOf(final T[] array, final T objectToFind, int startIndex) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (objectToFind == null) {
      for (int i = startIndex; i < array.length; ++i) {
        if (array[i] == null) {
          return i;
        }
      }
    } else {
      for (int i = startIndex; i < array.length; ++i) {
        if (objectToFind.equals(array[i])) {
          return i;
        }
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * Finds the last current of the given object within the array.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * @param array
   *          the array to travers backwords looking for the object, may be
   *          {@code null}
   * @param objectToFind
   *          the object to find, may be {@code null}
   * @return the last current of the object within the array,
   *         {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or
   *         {@code null} array input
   */
  public static <T> int lastIndexOf(final T[] array, final T objectToFind) {
    return lastIndexOf(array, objectToFind, Integer.MAX_VALUE);
  }

  /**
   * Finds the last current of the given object in the array starting at the given
   * current.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}
   * ). A startIndex larger than the array length will search from the end of
   * the array.
   *
   * @param array
   *          the array to traverse for looking for the object, may be
   *          {@code null}
   * @param objectToFind
   *          the object to find, may be {@code null}
   * @param startIndex
   *          the start current to travers backwards from
   * @return the last current of the object within the array,
   *         {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or
   *         {@code null} array input
   */
  public static <T> int lastIndexOf(final T[] array, final T objectToFind,
      int startIndex) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    if (startIndex < 0) {
      return INDEX_NOT_FOUND;
    } else if (startIndex >= array.length) {
      startIndex = array.length - 1;
    }
    if (objectToFind == null) {
      for (int i = startIndex; i >= 0; --i) {
        if (array[i] == null) {
          return i;
        }
      }
    } else {
      for (int i = startIndex; i >= 0; --i) {
        if (objectToFind.equals(array[i])) {
          return i;
        }
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * Checks if the object is in the given array.
   *
   * The method returns {@code false} if a {@code null} array is
   * passed in.
   *
   * @param array
   *          the array to search through
   * @param objectToFind
   *          the object to find
   * @return {@code true} if the array contains the object
   */
  public static <T> boolean contains(final T[] array, final T objectToFind) {
    return indexOf(array, objectToFind) != INDEX_NOT_FOUND;
  }

  /**
   * Finds the current of the given value in the array.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND} (
   *         {@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(final long[] array, final long valueToFind) {
    return indexOf(array, valueToFind, 0);
  }

  /**
   * Finds the current of the given value in the array starting at the given
   * current.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @param startIndex
   *          the current to start searching at
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND} (
   *         {@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(final long[] array, final long valueToFind, int startIndex) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    if (startIndex < 0) {
      startIndex = 0;
    }
    for (int i = startIndex; i < array.length; ++i) {
      if (valueToFind == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * Finds the last current of the given value within the array.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * @param array
   *          the array to travers backwords looking for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the object to find
   * @return the last current of the value within the array,
   *         {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or
   *         {@code null} array input
   */
  public static int lastIndexOf(final long[] array, final long valueToFind) {
    return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
  }

  /**
   * Finds the last current of the given value in the array starting at the given
   * current.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}
   * ). A startIndex larger than the array length will search from the end of
   * the array.
   *
   * @param array
   *          the array to traverse for looking for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @param startIndex
   *          the start current to travers backwards from
   * @return the last current of the value within the array,
   *         {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or
   *         {@code null} array input
   */
  public static int lastIndexOf(final long[] array, final long valueToFind, int startIndex) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    if (startIndex < 0) {
      return INDEX_NOT_FOUND;
    } else if (startIndex >= array.length) {
      startIndex = array.length - 1;
    }
    for (int i = startIndex; i >= 0; --i) {
      if (valueToFind == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * Checks if the value is in the given array.
   *
   * The method returns {@code false} if a {@code null} array is
   * passed in.
   *
   * @param array
   *          the array to search through
   * @param valueToFind
   *          the value to find
   * @return {@code true} if the array contains the object
   */
  public static boolean contains(final long[] array, final long valueToFind) {
    return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
  }

  /**
   * Finds the current of the given value in the array.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND} (
   *         {@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(final int[] array, final int valueToFind) {
    return indexOf(array, valueToFind, 0);
  }

  /**
   * Finds the current of the given value in the array starting at the given
   * current.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @param startIndex
   *          the current to start searching at
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND} (
   *         {@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(final int[] array, final int valueToFind, int startIndex) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    if (startIndex < 0) {
      startIndex = 0;
    }
    for (int i = startIndex; i < array.length; ++i) {
      if (valueToFind == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * Finds the last current of the given value within the array.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * @param array
   *          the array to travers backwords looking for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the object to find
   * @return the last current of the value within the array,
   *         {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or
   *         {@code null} array input
   */
  public static int lastIndexOf(final int[] array, final int valueToFind) {
    return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
  }

  /**
   * Finds the last current of the given value in the array starting at the given
   * current.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}
   * ). A startIndex larger than the array length will search from the end of
   * the array.
   *
   * @param array
   *          the array to traverse for looking for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @param startIndex
   *          the start current to travers backwards from
   * @return the last current of the value within the array,
   *         {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or
   *         {@code null} array input
   */
  public static int lastIndexOf(final int[] array, final int valueToFind, int startIndex) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    if (startIndex < 0) {
      return INDEX_NOT_FOUND;
    } else if (startIndex >= array.length) {
      startIndex = array.length - 1;
    }
    for (int i = startIndex; i >= 0; --i) {
      if (valueToFind == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * Checks if the value is in the given array.
   *
   * The method returns {@code false} if a {@code null} array is
   * passed in.
   *
   * @param array
   *          the array to search through
   * @param valueToFind
   *          the value to find
   * @return {@code true} if the array contains the object
   */
  public static boolean contains(final int[] array, final int valueToFind) {
    return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
  }

  /**
   * Finds the current of the given value in the array.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND} (
   *         {@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(final short[] array, final short valueToFind) {
    return indexOf(array, valueToFind, 0);
  }

  /**
   * Finds the current of the given value in the array starting at the given
   * current.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @param startIndex
   *          the current to start searching at
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND} (
   *         {@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(final short[] array, final short valueToFind, int startIndex) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    if (startIndex < 0) {
      startIndex = 0;
    }
    for (int i = startIndex; i < array.length; ++i) {
      if (valueToFind == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * Finds the last current of the given value within the array.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * @param array
   *          the array to travers backwords looking for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the object to find
   * @return the last current of the value within the array,
   *         {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or
   *         {@code null} array input
   */
  public static int lastIndexOf(final short[] array, final short valueToFind) {
    return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
  }

  /**
   * Finds the last current of the given value in the array starting at the given
   * current.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}
   * ). A startIndex larger than the array length will search from the end of
   * the array.
   *
   * @param array
   *          the array to traverse for looking for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @param startIndex
   *          the start current to travers backwards from
   * @return the last current of the value within the array,
   *         {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or
   *         {@code null} array input
   */
  public static int lastIndexOf(final short[] array, final short valueToFind, int startIndex) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    if (startIndex < 0) {
      return INDEX_NOT_FOUND;
    } else if (startIndex >= array.length) {
      startIndex = array.length - 1;
    }
    for (int i = startIndex; i >= 0; --i) {
      if (valueToFind == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * Checks if the value is in the given array.
   *
   * The method returns {@code false} if a {@code null} array is
   * passed in.
   *
   * @param array
   *          the array to search through
   * @param valueToFind
   *          the value to find
   * @return {@code true} if the array contains the object
   */
  public static boolean contains(final short[] array, final short valueToFind) {
    return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
  }

  /**
   * Finds the current of the given value in the array.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND} (
   *         {@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(final char[] array, final char valueToFind) {
    return indexOf(array, valueToFind, 0);
  }

  /**
   * Finds the current of the given value in the array starting at the given
   * current.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @param startIndex
   *          the current to start searching at
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND} (
   *         {@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(final char[] array, final char valueToFind, int startIndex) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    if (startIndex < 0) {
      startIndex = 0;
    }
    for (int i = startIndex; i < array.length; ++i) {
      if (valueToFind == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * Finds the last current of the given value within the array.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * @param array
   *          the array to travers backwords looking for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the object to find
   * @return the last current of the value within the array,
   *         {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or
   *         {@code null} array input
   */
  public static int lastIndexOf(final char[] array, final char valueToFind) {
    return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
  }

  /**
   * Finds the last current of the given value in the array starting at the given
   * current.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}
   * ). A startIndex larger than the array length will search from the end of
   * the array.
   *
   * @param array
   *          the array to traverse for looking for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @param startIndex
   *          the start current to travers backwards from
   * @return the last current of the value within the array,
   *         {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or
   *         {@code null} array input
   */
  public static int lastIndexOf(final char[] array, final char valueToFind, int startIndex) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    if (startIndex < 0) {
      return INDEX_NOT_FOUND;
    } else if (startIndex >= array.length) {
      startIndex = array.length - 1;
    }
    for (int i = startIndex; i >= 0; --i) {
      if (valueToFind == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * Checks if the value is in the given array.
   *
   * The method returns {@code false} if a {@code null} array is
   * passed in.
   *
   * @param array
   *          the array to search through
   * @param valueToFind
   *          the value to find
   * @return {@code true} if the array contains the object
   */
  public static boolean contains(final char[] array, final char valueToFind) {
    return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
  }

  /**
   * Finds the current of the given value in the array.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND} (
   *         {@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(final byte[] array, final byte valueToFind) {
    return indexOf(array, valueToFind, 0);
  }

  /**
   * Finds the current of the given value in the array starting at the given
   * current.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @param startIndex
   *          the current to start searching at
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND} (
   *         {@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(final byte[] array, final byte valueToFind, int startIndex) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    if (startIndex < 0) {
      startIndex = 0;
    }
    for (int i = startIndex; i < array.length; ++i) {
      if (valueToFind == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * Finds the last current of the given value within the array.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * @param array
   *          the array to travers backwords looking for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the object to find
   * @return the last current of the value within the array,
   *         {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or
   *         {@code null} array input
   */
  public static int lastIndexOf(final byte[] array, final byte valueToFind) {
    return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
  }

  /**
   * Finds the last current of the given value in the array starting at the given
   * current.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}
   * ). A startIndex larger than the array length will search from the end of
   * the array.
   *
   * @param array
   *          the array to traverse for looking for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @param startIndex
   *          the start current to travers backwards from
   * @return the last current of the value within the array,
   *         {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or
   *         {@code null} array input
   */
  public static int lastIndexOf(final byte[] array, final byte valueToFind, int startIndex) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    if (startIndex < 0) {
      return INDEX_NOT_FOUND;
    } else if (startIndex >= array.length) {
      startIndex = array.length - 1;
    }
    for (int i = startIndex; i >= 0; --i) {
      if (valueToFind == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * Checks if the value is in the given array.
   *
   * The method returns {@code false} if a {@code null} array is
   * passed in.
   *
   * @param array
   *          the array to search through
   * @param valueToFind
   *          the value to find
   * @return {@code true} if the array contains the object
   */
  public static boolean contains(final byte[] array, final byte valueToFind) {
    return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
  }

  /**
   * Finds the current of the given value in the array.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND} (
   *         {@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(final double[] array, final double valueToFind) {
    return indexOf(array, valueToFind, 0);
  }

  /**
   * Finds the current of the given value within a given tolerance in the array.
   * This method will return the current of the first value which falls between
   * the region defined by valueToFind - tolerance and valueToFind + tolerance.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @param tolerance
   *          tolerance of the search
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND} (
   *         {@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(final double[] array, final double valueToFind, final double tolerance) {
    return indexOf(array, valueToFind, 0, tolerance);
  }

  /**
   * Finds the current of the given value in the array starting at the given
   * current.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @param startIndex
   *          the current to start searching at
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND} (
   *         {@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(final double[] array, final double valueToFind, int startIndex) {
    if (ArrayUtils.isEmpty(array)) {
      return INDEX_NOT_FOUND;
    }
    if (startIndex < 0) {
      startIndex = 0;
    }
    for (int i = startIndex; i < array.length; ++i) {
      if (valueToFind == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * Finds the current of the given value in the array starting at the given
   * current. This method will return the current of the first value which falls
   * between the region defined by valueToFind - tolerance and valueToFind +
   * tolerance.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @param startIndex
   *          the current to start searching at
   * @param tolerance
   *          tolerance of the search
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND} (
   *         {@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(final double[] array, final double valueToFind, int startIndex,
      final double tolerance) {
    if (ArrayUtils.isEmpty(array)) {
      return INDEX_NOT_FOUND;
    }
    if (startIndex < 0) {
      startIndex = 0;
    }
    final double min = valueToFind - tolerance;
    final double max = valueToFind + tolerance;
    for (int i = startIndex; i < array.length; ++i) {
      if ((array[i] >= min) && (array[i] <= max)) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * Finds the last current of the given value within the array.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * @param array
   *          the array to travers backwords looking for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the object to find
   * @return the last current of the value within the array,
   *         {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or
   *         {@code null} array input
   */
  public static int lastIndexOf(final double[] array, final double valueToFind) {
    return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
  }

  /**
   * Finds the last current of the given value within a given tolerance in the
   * array. This method will return the current of the last value which falls
   * between the region defined by valueToFind - tolerance and valueToFind +
   * tolerance.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @param tolerance
   *          tolerance of the search
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND} (
   *         {@code -1}) if not found or {@code null} array input
   */
  public static int lastIndexOf(final double[] array, final double valueToFind,
      final double tolerance) {
    return lastIndexOf(array, valueToFind, Integer.MAX_VALUE, tolerance);
  }

  /**
   * Finds the last current of the given value in the array starting at the given
   * current.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}
   * ). A startIndex larger than the array length will search from the end of
   * the array.
   *
   * @param array
   *          the array to traverse for looking for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @param startIndex
   *          the start current to travers backwards from
   * @return the last current of the value within the array,
   *         {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or
   *         {@code null} array input
   */
  public static int lastIndexOf(final double[] array, final double valueToFind,
      int startIndex) {
    if (ArrayUtils.isEmpty(array)) {
      return INDEX_NOT_FOUND;
    }
    if (startIndex < 0) {
      return INDEX_NOT_FOUND;
    } else if (startIndex >= array.length) {
      startIndex = array.length - 1;
    }
    for (int i = startIndex; i >= 0; --i) {
      if (valueToFind == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * Finds the last current of the given value in the array starting at the given
   * current. This method will return the current of the last value which falls
   * between the region defined by valueToFind - tolerance and valueToFind +
   * tolerance.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}
   * ). A startIndex larger than the array length will search from the end of
   * the array.
   *
   * @param array
   *          the array to traverse for looking for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @param startIndex
   *          the start current to travers backwards from
   * @param tolerance
   *          search for value within plus/minus this amount
   * @return the last current of the value within the array,
   *         {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or
   *         {@code null} array input
   */
  public static int lastIndexOf(final double[] array, final double valueToFind,
      int startIndex, final double tolerance) {
    if (ArrayUtils.isEmpty(array)) {
      return INDEX_NOT_FOUND;
    }
    if (startIndex < 0) {
      return INDEX_NOT_FOUND;
    } else if (startIndex >= array.length) {
      startIndex = array.length - 1;
    }
    final double min = valueToFind - tolerance;
    final double max = valueToFind + tolerance;
    for (int i = startIndex; i >= 0; --i) {
      if ((array[i] >= min) && (array[i] <= max)) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * Checks if the value is in the given array.
   *
   * The method returns {@code false} if a {@code null} array is
   * passed in.
   *
   * @param array
   *          the array to search through
   * @param valueToFind
   *          the value to find
   * @return {@code true} if the array contains the object
   */
  public static boolean contains(final double[] array, final double valueToFind) {
    return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
  }

  /**
   * Checks if a value falling within the given tolerance is in the given array.
   * If the array contains a value within the inclusive range defined by (value
   * - tolerance) to (value + tolerance).
   *
   * The method returns {@code false} if a {@code null} array is
   * passed in.
   *
   * @param array
   *          the array to search
   * @param valueToFind
   *          the value to find
   * @param tolerance
   *          the array contains the tolerance of the search
   * @return true if value falling within tolerance is in array
   */
  public static boolean contains(final double[] array, final double valueToFind,
      final double tolerance) {
    return indexOf(array, valueToFind, 0, tolerance) != INDEX_NOT_FOUND;
  }

  // float IndexOf
  // -----------------------------------------------------------------------
  /**
   * Finds the current of the given value in the array.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND} (
   *         {@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(final float[] array, final float valueToFind) {
    return indexOf(array, valueToFind, 0);
  }

  /**
   * Finds the current of the given value in the array starting at the given
   * current.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @param startIndex
   *          the current to start searching at
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND} (
   *         {@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(final float[] array, final float valueToFind, int startIndex) {
    if (ArrayUtils.isEmpty(array)) {
      return INDEX_NOT_FOUND;
    }
    if (startIndex < 0) {
      startIndex = 0;
    }
    for (int i = startIndex; i < array.length; ++i) {
      if (valueToFind == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * Finds the last current of the given value within the array.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * @param array
   *          the array to travers backwords looking for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the object to find
   * @return the last current of the value within the array,
   *         {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or
   *         {@code null} array input
   */
  public static int lastIndexOf(final float[] array, final float valueToFind) {
    return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
  }

  /**
   * Finds the last current of the given value in the array starting at the given
   * current.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}
   * ). A startIndex larger than the array length will search from the end of
   * the array.
   *
   * @param array
   *          the array to traverse for looking for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @param startIndex
   *          the start current to travers backwards from
   * @return the last current of the value within the array,
   *         {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or
   *         {@code null} array input
   */
  public static int lastIndexOf(final float[] array, final float valueToFind, int startIndex) {
    if (ArrayUtils.isEmpty(array)) {
      return INDEX_NOT_FOUND;
    }
    if (startIndex < 0) {
      return INDEX_NOT_FOUND;
    } else if (startIndex >= array.length) {
      startIndex = array.length - 1;
    }
    for (int i = startIndex; i >= 0; --i) {
      if (valueToFind == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * Checks if the value is in the given array.
   *
   * The method returns {@code false} if a {@code null} array is
   * passed in.
   *
   * @param array
   *          the array to search through
   * @param valueToFind
   *          the value to find
   * @return {@code true} if the array contains the object
   */
  public static boolean contains(final float[] array, final float valueToFind) {
    return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
  }

  /**
   * Finds the current of the given value in the array.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND} (
   *         {@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(final boolean[] array, final boolean valueToFind) {
    return indexOf(array, valueToFind, 0);
  }

  /**
   * Finds the current of the given value in the array starting at the given
   * current.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array
   *          the array to search through for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @param startIndex
   *          the current to start searching at
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND} (
   *         {@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(final boolean[] array, final boolean valueToFind, int startIndex) {
    if (ArrayUtils.isEmpty(array)) {
      return INDEX_NOT_FOUND;
    }
    if (startIndex < 0) {
      startIndex = 0;
    }
    for (int i = startIndex; i < array.length; ++i) {
      if (valueToFind == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * Finds the last current of the given value within the array.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) if
   * {@code null} array input.
   *
   * @param array
   *          the array to travers backwords looking for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the object to find
   * @return the last current of the value within the array,
   *         {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or
   *         {@code null} array input
   */
  public static int lastIndexOf(final boolean[] array, final boolean valueToFind) {
    return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
  }

  /**
   * Finds the last current of the given value in the array starting at the given
   * current.
   *
   * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a
   * {@code null} input array.
   *
   * A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}
   * ). A startIndex larger than the array length will search from the end of
   * the array.
   *
   * @param array
   *          the array to traverse for looking for the object, may be
   *          {@code null}
   * @param valueToFind
   *          the value to find
   * @param startIndex
   *          the start current to travers backwards from
   * @return the last current of the value within the array,
   *         {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or
   *         {@code null} array input
   */
  public static int lastIndexOf(final boolean[] array, final boolean valueToFind,
      int startIndex) {
    if (ArrayUtils.isEmpty(array)) {
      return INDEX_NOT_FOUND;
    }
    if (startIndex < 0) {
      return INDEX_NOT_FOUND;
    } else if (startIndex >= array.length) {
      startIndex = array.length - 1;
    }
    for (int i = startIndex; i >= 0; --i) {
      if (valueToFind == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * Checks if the value is in the given array.
   *
   * The method returns {@code false} if a {@code null} array is
   * passed in.
   *
   * @param array
   *          the array to search through
   * @param valueToFind
   *          the value to find
   * @return {@code true} if the array contains the object
   */
  public static boolean contains(final boolean[] array, final boolean valueToFind) {
    return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
  }

  /**
   * Converts an array of object Characters to primitives.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code Character} array, may be {@code null}
   * @return a {@code char} array, {@code null} if null array input
   * @throws NullPointerException
   *           if array content is {@code null}
   */
  public static char[] toPrimitive(final Character[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_CHAR_ARRAY;
    }
    final char[] result = new char[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i].charValue();
    }
    return result;
  }

  /**
   * Converts an array of object Character to primitives handling
   * {@code null}.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code Character} array, may be {@code null}
   * @param valueForNull
   *          the value to insert if {@code null} found
   * @return a {@code char} array, {@code null} if null array input
   */
  public static char[] toPrimitive(final Character[] array, final char valueForNull) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_CHAR_ARRAY;
    }
    final char[] result = new char[array.length];
    for (int i = 0; i < array.length; ++i) {
      final Character b = array[i];
      result[i] = (b == null ? valueForNull : b.charValue());
    }
    return result;
  }

  /**
   * Converts an array of primitive chars to objects.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code char} array
   * @return a {@code Character} array, {@code null} if null array
   *         input
   */
  public static Character[] toObject(final char[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_CHARACTER_OBJECT_ARRAY;
    }
    final Character[] result = new Character[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = new Character(array[i]);
    }
    return result;
  }

  // Long array converters
  // ----------------------------------------------------------------------
  /**
   * Converts an array of object Longs to primitives.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code Long} array, may be {@code null}
   * @return a {@code long} array, {@code null} if null array input
   * @throws NullPointerException
   *           if array content is {@code null}
   */
  public static long[] toPrimitive(final Long[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_LONG_ARRAY;
    }
    final long[] result = new long[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i].longValue();
    }
    return result;
  }

  /**
   * Converts an array of object Long to primitives handling {@code null}.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code Long} array, may be {@code null}
   * @param valueForNull
   *          the value to insert if {@code null} found
   * @return a {@code long} array, {@code null} if null array input
   */
  public static long[] toPrimitive(final Long[] array, final long valueForNull) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_LONG_ARRAY;
    }
    final long[] result = new long[array.length];
    for (int i = 0; i < array.length; ++i) {
      final Long b = array[i];
      result[i] = (b == null ? valueForNull : b.longValue());
    }
    return result;
  }

  /**
   * Converts an array of primitive longs to objects.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code long} array
   * @return a {@code Long} array, {@code null} if null array input
   */
  public static Long[] toObject(final long[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_LONG_OBJECT_ARRAY;
    }
    final Long[] result = new Long[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = new Long(array[i]);
    }
    return result;
  }

  /**
   * Converts an array of object Integers to primitives.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code Integer} array, may be {@code null}
   * @return an {@code int} array, {@code null} if null array input
   * @throws NullPointerException
   *           if array content is {@code null}
   */
  public static int[] toPrimitive(final Integer[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_INT_ARRAY;
    }
    final int[] result = new int[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i].intValue();
    }
    return result;
  }

  /**
   * Converts an array of object Integer to primitives handling
   * {@code null}.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code Integer} array, may be {@code null}
   * @param valueForNull
   *          the value to insert if {@code null} found
   * @return an {@code int} array, {@code null} if null array input
   */
  public static int[] toPrimitive(final Integer[] array, final int valueForNull) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_INT_ARRAY;
    }
    final int[] result = new int[array.length];
    for (int i = 0; i < array.length; ++i) {
      final Integer b = array[i];
      result[i] = (b == null ? valueForNull : b.intValue());
    }
    return result;
  }

  /**
   * Converts an array of primitive ints to objects.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          an {@code int} array
   * @return an {@code Integer} array, {@code null} if null array
   *         input
   */
  public static Integer[] toObject(final int[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_INTEGER_OBJECT_ARRAY;
    }
    final Integer[] result = new Integer[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = new Integer(array[i]);
    }
    return result;
  }

  /**
   * Converts an array of object Shorts to primitives.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code Short} array, may be {@code null}
   * @return a {@code byte} array, {@code null} if null array input
   * @throws NullPointerException
   *           if array content is {@code null}
   */
  public static short[] toPrimitive(final Short[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_SHORT_ARRAY;
    }
    final short[] result = new short[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i].shortValue();
    }
    return result;
  }

  /**
   * Converts an array of object Short to primitives handling {@code null}.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code Short} array, may be {@code null}
   * @param valueForNull
   *          the value to insert if {@code null} found
   * @return a {@code byte} array, {@code null} if null array input
   */
  public static short[] toPrimitive(final Short[] array, final short valueForNull) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_SHORT_ARRAY;
    }
    final short[] result = new short[array.length];
    for (int i = 0; i < array.length; ++i) {
      final Short b = array[i];
      result[i] = (b == null ? valueForNull : b.shortValue());
    }
    return result;
  }

  /**
   * Converts an array of primitive shorts to objects.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code short} array
   * @return a {@code Short} array, {@code null} if null array input
   */
  public static Short[] toObject(final short[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_SHORT_OBJECT_ARRAY;
    }
    final Short[] result = new Short[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = new Short(array[i]);
    }
    return result;
  }

  /**
   * Converts an array of object Bytes to primitives.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code Byte} array, may be {@code null}
   * @return a {@code byte} array, {@code null} if null array input
   * @throws NullPointerException
   *           if array content is {@code null}
   */
  public static byte[] toPrimitive(final Byte[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_BYTE_ARRAY;
    }
    final byte[] result = new byte[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i].byteValue();
    }
    return result;
  }

  /**
   * Converts an array of object Bytes to primitives handling {@code null}.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code Byte} array, may be {@code null}
   * @param valueForNull
   *          the value to insert if {@code null} found
   * @return a {@code byte} array, {@code null} if null array input
   */
  public static byte[] toPrimitive(final Byte[] array, final byte valueForNull) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_BYTE_ARRAY;
    }
    final byte[] result = new byte[array.length];
    for (int i = 0; i < array.length; ++i) {
      final Byte b = array[i];
      result[i] = (b == null ? valueForNull : b.byteValue());
    }
    return result;
  }

  /**
   * Converts an array of primitive bytes to objects.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code byte} array
   * @return a {@code Byte} array, {@code null} if null array input
   */
  public static Byte[] toObject(final byte[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_BYTE_OBJECT_ARRAY;
    }
    final Byte[] result = new Byte[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = new Byte(array[i]);
    }
    return result;
  }

  /**
   * Converts an array of object Doubles to primitives.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code Double} array, may be {@code null}
   * @return a {@code double} array, {@code null} if null array input
   * @throws NullPointerException
   *           if array content is {@code null}
   */
  public static double[] toPrimitive(final Double[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_DOUBLE_ARRAY;
    }
    final double[] result = new double[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i].doubleValue();
    }
    return result;
  }

  /**
   * Converts an array of object Doubles to primitives handling
   * {@code null}.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code Double} array, may be {@code null}
   * @param valueForNull
   *          the value to insert if {@code null} found
   * @return a {@code double} array, {@code null} if null array input
   */
  public static double[] toPrimitive(final Double[] array, final double valueForNull) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_DOUBLE_ARRAY;
    }
    final double[] result = new double[array.length];
    for (int i = 0; i < array.length; ++i) {
      final Double b = array[i];
      result[i] = (b == null ? valueForNull : b.doubleValue());
    }
    return result;
  }

  /**
   * Converts an array of primitive doubles to objects.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code double} array
   * @return a {@code Double} array, {@code null} if null array input
   */
  public static Double[] toObject(final double[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_DOUBLE_OBJECT_ARRAY;
    }
    final Double[] result = new Double[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = new Double(array[i]);
    }
    return result;
  }

  /**
   * Converts an array of object Floats to primitives.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code Float} array, may be {@code null}
   * @return a {@code float} array, {@code null} if null array input
   * @throws NullPointerException
   *           if array content is {@code null}
   */
  public static float[] toPrimitive(final Float[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_FLOAT_ARRAY;
    }
    final float[] result = new float[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i].floatValue();
    }
    return result;
  }

  /**
   * Converts an array of object Floats to primitives handling {@code null}
   * .
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code Float} array, may be {@code null}
   * @param valueForNull
   *          the value to insert if {@code null} found
   * @return a {@code float} array, {@code null} if null array input
   */
  public static float[] toPrimitive(final Float[] array, final float valueForNull) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_FLOAT_ARRAY;
    }
    final float[] result = new float[array.length];
    for (int i = 0; i < array.length; ++i) {
      final Float b = array[i];
      result[i] = (b == null ? valueForNull : b.floatValue());
    }
    return result;
  }

  /**
   * Converts an array of primitive floats to objects.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code float} array
   * @return a {@code Float} array, {@code null} if null array input
   */
  public static Float[] toObject(final float[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_FLOAT_OBJECT_ARRAY;
    }
    final Float[] result = new Float[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = new Float(array[i]);
    }
    return result;
  }

  /**
   * Converts an array of object Booleans to primitives.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code Boolean} array, may be {@code null}
   * @return a {@code boolean} array, {@code null} if null array input
   * @throws NullPointerException
   *           if array content is {@code null}
   */
  public static boolean[] toPrimitive(final Boolean[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_BOOLEAN_ARRAY;
    }
    final boolean[] result = new boolean[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i].booleanValue();
    }
    return result;
  }

  /**
   * Converts an array of object Booleans to primitives handling
   * {@code null}.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code Boolean} array, may be {@code null}
   * @param valueForNull
   *          the value to insert if {@code null} found
   * @return a {@code boolean} array, {@code null} if null array input
   */
  public static boolean[] toPrimitive(final Boolean[] array, final boolean valueForNull) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_BOOLEAN_ARRAY;
    }
    final boolean[] result = new boolean[array.length];
    for (int i = 0; i < array.length; ++i) {
      final Boolean b = array[i];
      result[i] = (b == null ? valueForNull : b.booleanValue());
    }
    return result;
  }

  /**
   * Converts an array of primitive booleans to objects.
   *
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          a {@code boolean} array
   * @return a {@code Boolean} array, {@code null} if null array input
   */
  public static Boolean[] toObject(final boolean[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_BOOLEAN_OBJECT_ARRAY;
    }
    final Boolean[] result = new Boolean[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = (array[i] ? Boolean.TRUE : Boolean.FALSE);
    }
    return result;
  }

  /**
   * Checks if an array of Objects is empty or {@code null}.
   *
   * @param array
   *          the array to test
   * @return {@code true} if the array is empty or {@code null}
   */
  public static <T> boolean isEmpty(final T[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * Checks if an array of primitive longs is empty or {@code null}.
   *
   * @param array
   *          the array to test
   * @return {@code true} if the array is empty or {@code null}
   */
  public static boolean isEmpty(final long[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * Checks if an array of primitive ints is empty or {@code null}.
   *
   * @param array
   *          the array to test
   * @return {@code true} if the array is empty or {@code null}
   */
  public static boolean isEmpty(final int[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * Checks if an array of primitive shorts is empty or {@code null}.
   *
   * @param array
   *          the array to test
   * @return {@code true} if the array is empty or {@code null}
   */
  public static boolean isEmpty(final short[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * Checks if an array of primitive chars is empty or {@code null}.
   *
   * @param array
   *          the array to test
   * @return {@code true} if the array is empty or {@code null}
   */
  public static boolean isEmpty(final char[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * Checks if an array of primitive bytes is empty or {@code null}.
   *
   * @param array
   *          the array to test
   * @return {@code true} if the array is empty or {@code null}
   */
  public static boolean isEmpty(final byte[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * Checks if an array of primitive doubles is empty or {@code null}.
   *
   * @param array
   *          the array to test
   * @return {@code true} if the array is empty or {@code null}
   */
  public static boolean isEmpty(final double[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * Checks if an array of primitive floats is empty or {@code null}.
   *
   * @param array
   *          the array to test
   * @return {@code true} if the array is empty or {@code null}
   */
  public static boolean isEmpty(final float[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * Checks if an array of primitive booleans is empty or {@code null}.
   *
   * @param array
   *          the array to test
   * @return {@code true} if the array is empty or {@code null}
   */
  public static boolean isEmpty(final boolean[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * Adds all the elements of the given arrays into a new array.
   * The new array contains all of the element of {@code array1} followed
   * by all of the elements {@code array2}. When an array is returned, it
   * is always a new array.
   *
   * <pre>
   * Arrays.addAll(null, null)     = null
   * Arrays.addAll(array1, null)   = cloned copy of array1
   * Arrays.addAll(null, array2)   = cloned copy of array2
   * Arrays.addAll([], [])         = []
   * Arrays.addAll([null], [null]) = [null, null]
   * Arrays.addAll(["a", "b", "c"], ["1", "2", "3"]) = ["a", "b", "c", "1", "2", "3"]
   * </pre>
   *
   * @param array1
   *          the first array whose elements are added to the new array, may be
   *          {@code null}
   * @param array2
   *          the second array whose elements are added to the new array, may be
   *          {@code null}
   * @return The new array, {@code null} if {@code null} array inputs.
   *         The type of the new array is the type of the first array.
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] addAll(final T[] array1, final T[] array2) {
    if (array1 == null) {
      return Assignment.clone(array2);
    } else if (array2 == null) {
      return Assignment.clone(array1);
    }
    final T[] joinedArray = (T[]) Array.newInstance(array1.getClass().getComponentType(),
        array1.length + array2.length);
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * Adds all the elements of the given arrays into a new array.
   * The new array contains all of the element of {@code array1} followed
   * by all of the elements {@code array2}. When an array is returned, it
   * is always a new array.
   *
   * <pre>
   * Arrays.addAll(array1, null)   = cloned copy of array1
   * Arrays.addAll(null, array2)   = cloned copy of array2
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *          the first array whose elements are added to the new array.
   * @param array2
   *          the second array whose elements are added to the new array.
   * @return The new boolean[] array.
   */
  public static boolean[] addAll(final boolean[] array1, final boolean[] array2) {
    if (array1 == null) {
      return Assignment.clone(array2);
    } else if (array2 == null) {
      return Assignment.clone(array1);
    }
    final boolean[] joinedArray = new boolean[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * Adds all the elements of the given arrays into a new array.
   * The new array contains all of the element of {@code array1} followed
   * by all of the elements {@code array2}. When an array is returned, it
   * is always a new array.
   *
   * <pre>
   * Arrays.addAll(array1, null)   = cloned copy of array1
   * Arrays.addAll(null, array2)   = cloned copy of array2
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *          the first array whose elements are added to the new array.
   * @param array2
   *          the second array whose elements are added to the new array.
   * @return The new char[] array.
   */
  public static char[] addAll(final char[] array1, final char[] array2) {
    if (array1 == null) {
      return Assignment.clone(array2);
    } else if (array2 == null) {
      return Assignment.clone(array1);
    }
    final char[] joinedArray = new char[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * Adds all the elements of the given arrays into a new array.
   * The new array contains all of the element of {@code array1} followed
   * by all of the elements {@code array2}. When an array is returned, it
   * is always a new array.
   *
   * <pre>
   * Arrays.addAll(array1, null)   = cloned copy of array1
   * Arrays.addAll(null, array2)   = cloned copy of array2
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *          the first array whose elements are added to the new array.
   * @param array2
   *          the second array whose elements are added to the new array.
   * @return The new byte[] array.
   */
  public static byte[] addAll(final byte[] array1, final byte[] array2) {
    if (array1 == null) {
      return Assignment.clone(array2);
    } else if (array2 == null) {
      return Assignment.clone(array1);
    }
    final byte[] joinedArray = new byte[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * Adds all the elements of the given arrays into a new array.
   * The new array contains all of the element of {@code array1} followed
   * by all of the elements {@code array2}. When an array is returned, it
   * is always a new array.
   *
   * <pre>
   * Arrays.addAll(array1, null)   = cloned copy of array1
   * Arrays.addAll(null, array2)   = cloned copy of array2
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *          the first array whose elements are added to the new array.
   * @param array2
   *          the second array whose elements are added to the new array.
   * @return The new short[] array.
   */
  public static short[] addAll(final short[] array1, final short[] array2) {
    if (array1 == null) {
      return Assignment.clone(array2);
    } else if (array2 == null) {
      return Assignment.clone(array1);
    }
    final short[] joinedArray = new short[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * Adds all the elements of the given arrays into a new array.
   * The new array contains all of the element of {@code array1} followed
   * by all of the elements {@code array2}. When an array is returned, it
   * is always a new array.
   *
   * <pre>
   * Arrays.addAll(array1, null)   = cloned copy of array1
   * Arrays.addAll(null, array2)   = cloned copy of array2
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *          the first array whose elements are added to the new array.
   * @param array2
   *          the second array whose elements are added to the new array.
   * @return The new int[] array.
   */
  public static int[] addAll(final int[] array1, final int[] array2) {
    if (array1 == null) {
      return Assignment.clone(array2);
    } else if (array2 == null) {
      return Assignment.clone(array1);
    }
    final int[] joinedArray = new int[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * Adds all the elements of the given arrays into a new array.
   * The new array contains all of the element of {@code array1} followed
   * by all of the elements {@code array2}. When an array is returned, it
   * is always a new array.
   *
   * <pre>
   * Arrays.addAll(array1, null)   = cloned copy of array1
   * Arrays.addAll(null, array2)   = cloned copy of array2
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *          the first array whose elements are added to the new array.
   * @param array2
   *          the second array whose elements are added to the new array.
   * @return The new long[] array.
   */
  public static long[] addAll(final long[] array1, final long[] array2) {
    if (array1 == null) {
      return Assignment.clone(array2);
    } else if (array2 == null) {
      return Assignment.clone(array1);
    }
    final long[] joinedArray = new long[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * Adds all the elements of the given arrays into a new array.
   * The new array contains all of the element of {@code array1} followed
   * by all of the elements {@code array2}. When an array is returned, it
   * is always a new array.
   *
   * <pre>
   * Arrays.addAll(array1, null)   = cloned copy of array1
   * Arrays.addAll(null, array2)   = cloned copy of array2
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *          the first array whose elements are added to the new array.
   * @param array2
   *          the second array whose elements are added to the new array.
   * @return The new float[] array.
   */
  public static float[] addAll(final float[] array1, final float[] array2) {
    if (array1 == null) {
      return Assignment.clone(array2);
    } else if (array2 == null) {
      return Assignment.clone(array1);
    }
    final float[] joinedArray = new float[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * Adds all the elements of the given arrays into a new array.
   * The new array contains all of the element of {@code array1} followed
   * by all of the elements {@code array2}. When an array is returned, it
   * is always a new array.
   *
   * <pre>
   * Arrays.addAll(array1, null)   = cloned copy of array1
   * Arrays.addAll(null, array2)   = cloned copy of array2
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *          the first array whose elements are added to the new array.
   * @param array2
   *          the second array whose elements are added to the new array.
   * @return The new double[] array.
   */
  public static double[] addAll(final double[] array1, final double[] array2) {
    if (array1 == null) {
      return Assignment.clone(array2);
    } else if (array2 == null) {
      return Assignment.clone(array1);
    }
    final double[] joinedArray = new double[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * Copies the given array and adds the given element at the end of the new
   * array.
   *
   * The new array contains the same elements of the input array plus the given
   * element in the last position. The component type of the new array is the
   * same as that of the input array.
   *
   * If the input array is {@code null}, a new one element array is
   * returned whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, null)      = [null]
   * Arrays.add(null, "a")       = ["a"]
   * Arrays.add(["a"], null)     = ["a", null]
   * Arrays.add(["a"], "b")      = ["a", "b"]
   * Arrays.add(["a", "b"], "c") = ["a", "b", "c"]
   * </pre>
   *
   * @param array
   *          the array to "add" the element to, may be {@code null}
   * @param element
   *          the object to add
   * @return A new array containing the existing elements plus the new element
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] add(final T[] array, final T element) {
    Class<?> type = null;
    if (array != null) {
      type = array.getClass().getComponentType();
    } else if (element != null) {
      // FIXME: can we use the element.getClass() as the type of returned array?
      // For examle, assume array is of type Object[] and its value is null,
      // assume element is a string "a", then calling this function with the
      // argument of array and element, what should be the type of returned
      // array? An array of Object, or an array of String?
      //
      type = element.getClass();
    } else {
      type = Object.class;
    }
    final T[] newArray = (T[]) copyArrayGrow1(array, type);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * Copies the given array and adds the given element at the end of the new
   * array.
   *
   * The new array contains the same elements of the input array plus the given
   * element in the last position. The component type of the new array is the
   * same as that of the input array.
   *
   * If the input array is {@code null}, a new one element array is
   * returned whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, true)          = [true]
   * Arrays.add([true], false)       = [true, false]
   * Arrays.add([true, false], true) = [true, false, true]
   * </pre>
   *
   * @param array
   *          the array to copy and add the element to, may be {@code null}
   * @param element
   *          the object to add at the last current of the new array
   * @return A new array containing the existing elements plus the new element
   */
  public static boolean[] add(final boolean[] array, final boolean element) {
    final boolean[] newArray = (boolean[]) copyArrayGrow1(array, Boolean.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * Copies the given array and adds the given element at the end of the new
   * array.
   *
   * The new array contains the same elements of the input array plus the given
   * element in the last position. The component type of the new array is the
   * same as that of the input array.
   *
   * If the input array is {@code null}, a new one element array is
   * returned whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, 0)   = [0]
   * Arrays.add([1], 0)    = [1, 0]
   * Arrays.add([1, 0], 1) = [1, 0, 1]
   * </pre>
   *
   * @param array
   *          the array to copy and add the element to, may be {@code null}
   * @param element
   *          the object to add at the last current of the new array
   * @return A new array containing the existing elements plus the new element
   */
  public static byte[] add(final byte[] array, final byte element) {
    final byte[] newArray = (byte[]) copyArrayGrow1(array, Byte.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * Copies the given array and adds the given element at the end of the new
   * array.
   *
   * The new array contains the same elements of the input array plus the given
   * element in the last position. The component type of the new array is the
   * same as that of the input array.
   *
   * If the input array is {@code null}, a new one element array is
   * returned whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, '0')       = ['0']
   * Arrays.add(['1'], '0')      = ['1', '0']
   * Arrays.add(['1', '0'], '1') = ['1', '0', '1']
   * </pre>
   *
   * @param array
   *          the array to copy and add the element to, may be {@code null}
   * @param element
   *          the object to add at the last current of the new array
   * @return A new array containing the existing elements plus the new element
   */
  public static char[] add(final char[] array, final char element) {
    final char[] newArray = (char[]) copyArrayGrow1(array, Character.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * Copies the given array and adds the given element at the end of the new
   * array.
   *
   * The new array contains the same elements of the input array plus the given
   * element in the last position. The component type of the new array is the
   * same as that of the input array.
   *
   * If the input array is {@code null}, a new one element array is
   * returned whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, 0)   = [0]
   * Arrays.add([1], 0)    = [1, 0]
   * Arrays.add([1, 0], 1) = [1, 0, 1]
   * </pre>
   *
   * @param array
   *          the array to copy and add the element to, may be {@code null}
   * @param element
   *          the object to add at the last current of the new array
   * @return A new array containing the existing elements plus the new element
   */
  public static double[] add(final double[] array, final double element) {
    final double[] newArray = (double[]) copyArrayGrow1(array, Double.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * Copies the given array and adds the given element at the end of the new
   * array.
   *
   * The new array contains the same elements of the input array plus the given
   * element in the last position. The component type of the new array is the
   * same as that of the input array.
   *
   * If the input array is {@code null}, a new one element array is
   * returned whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, 0)   = [0]
   * Arrays.add([1], 0)    = [1, 0]
   * Arrays.add([1, 0], 1) = [1, 0, 1]
   * </pre>
   *
   * @param array
   *          the array to copy and add the element to, may be {@code null}
   * @param element
   *          the object to add at the last current of the new array
   * @return A new array containing the existing elements plus the new element
   */
  public static float[] add(final float[] array, final float element) {
    final float[] newArray = (float[]) copyArrayGrow1(array, Float.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * Copies the given array and adds the given element at the end of the new
   * array.
   *
   * The new array contains the same elements of the input array plus the given
   * element in the last position. The component type of the new array is the
   * same as that of the input array.
   *
   * If the input array is {@code null}, a new one element array is
   * returned whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, 0)   = [0]
   * Arrays.add([1], 0)    = [1, 0]
   * Arrays.add([1, 0], 1) = [1, 0, 1]
   * </pre>
   *
   * @param array
   *          the array to copy and add the element to, may be {@code null}
   * @param element
   *          the object to add at the last current of the new array
   * @return A new array containing the existing elements plus the new element
   */
  public static int[] add(final int[] array, final int element) {
    final int[] newArray = (int[]) copyArrayGrow1(array, Integer.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * Copies the given array and adds the given element at the end of the new
   * array.
   *
   * The new array contains the same elements of the input array plus the given
   * element in the last position. The component type of the new array is the
   * same as that of the input array.
   *
   * If the input array is {@code null}, a new one element array is
   * returned whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, 0)   = [0]
   * Arrays.add([1], 0)    = [1, 0]
   * Arrays.add([1, 0], 1) = [1, 0, 1]
   * </pre>
   *
   * @param array
   *          the array to copy and add the element to, may be {@code null}
   * @param element
   *          the object to add at the last current of the new array
   * @return A new array containing the existing elements plus the new element
   */
  public static long[] add(final long[] array, final long element) {
    final long[] newArray = (long[]) copyArrayGrow1(array, Long.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * Copies the given array and adds the given element at the end of the new
   * array.
   *
   * The new array contains the same elements of the input array plus the given
   * element in the last position. The component type of the new array is the
   * same as that of the input array.
   *
   * If the input array is {@code null}, a new one element array is
   * returned whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, 0)   = [0]
   * Arrays.add([1], 0)    = [1, 0]
   * Arrays.add([1, 0], 1) = [1, 0, 1]
   * </pre>
   *
   * @param array
   *          the array to copy and add the element to, may be {@code null}
   * @param element
   *          the object to add at the last current of the new array
   * @return A new array containing the existing elements plus the new element
   */
  public static short[] add(final short[] array, final short element) {
    final short[] newArray = (short[]) copyArrayGrow1(array, Short.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * Returns a copy of the given array of size 1 greater than the argument. The
   * last value of the array is left to the default value.
   *
   * @param array
   *          The array to copy, must not be {@code null}.
   * @param newArrayComponentType
   *          If {@code array} is {@code null}, create a size 1 array
   *          of this type.
   * @return A new copy of the array of size 1 greater than the input.
   */
  private static Object copyArrayGrow1(final Object array, final Class<?> newArrayComponentType) {
    if (array != null) {
      final int arrayLength = Array.getLength(array);
      final Object newArray = Array.newInstance(array.getClass().getComponentType(),
          arrayLength + 1);
      System.arraycopy(array, 0, newArray, 0, arrayLength);
      return newArray;
    }
    return Array.newInstance(newArrayComponentType, 1);
  }

  /**
   * Inserts the specified element at the specified position in the array.
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * This method returns a new array with the same elements of the input array
   * plus the given element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * If the input array is {@code null}, a new one element array is
   * returned whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, 0, null)      = [null]
   * Arrays.add(null, 0, "a")       = ["a"]
   * Arrays.add(["a"], 1, null)     = ["a", null]
   * Arrays.add(["a"], 1, "b")      = ["a", "b"]
   * Arrays.add(["a", "b"], 3, "c") = ["a", "b", "c"]
   * </pre>
   *
   * @param array
   *          the array to add the element to, may be {@code null}
   * @param current
   *          the position of the new object
   * @param element
   *          the object to add
   * @return A new array containing the existing elements and the new element
   * @throws IndexOutOfBoundsException
   *           if the current is out of range (current < 0 || current > array.length).
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] add(final T[] array, final int index, final T element) {
    Class<?> cls = null;
    if (array != null) {
      cls = array.getClass().getComponentType();
    } else if (element != null) {
      // FIXME: can we use the element.getClass() as the type of returned array?
      // For examle, assume array is of type Object[] and its value is null,
      // assume element is a string "a", then calling this function with the
      // argument of array and element, what should be the type of returned
      // array? An array of Object, or an array of String?
      //
      cls = element.getClass();
    } else {
      cls = Object.class;
    }
    return (T[]) add(array, index, element, cls);
  }

  /**
   * Inserts the specified element at the specified position in the array.
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * This method returns a new array with the same elements of the input array
   * plus the given element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * If the input array is {@code null}, a new one element array is
   * returned whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, 0, true)          = [true]
   * Arrays.add([true], 0, false)       = [false, true]
   * Arrays.add([false], 1, true)       = [false, true]
   * Arrays.add([true, false], 1, true) = [true, true, false]
   * </pre>
   *
   * @param array
   *          the array to add the element to, may be {@code null}
   * @param current
   *          the position of the new object
   * @param element
   *          the object to add
   * @return A new array containing the existing elements and the new element
   * @throws IndexOutOfBoundsException
   *           if the current is out of range (current < 0 || current > array.length).
   */
  public static boolean[] add(final boolean[] array, final int index, final boolean element) {
    return (boolean[]) add(array, index,
        (element ? Boolean.TRUE : Boolean.FALSE), Boolean.TYPE);
  }

  /**
   * Inserts the specified element at the specified position in the array.
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * This method returns a new array with the same elements of the input array
   * plus the given element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * If the input array is {@code null}, a new one element array is
   * returned whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, 0, 'a')            = ['a']
   * Arrays.add(['a'], 0, 'b')           = ['b', 'a']
   * Arrays.add(['a', 'b'], 0, 'c')      = ['c', 'a', 'b']
   * Arrays.add(['a', 'b'], 1, 'k')      = ['a', 'k', 'b']
   * Arrays.add(['a', 'b', 'c'], 1, 't') = ['a', 't', 'b', 'c']
   * </pre>
   *
   * @param array
   *          the array to add the element to, may be {@code null}
   * @param current
   *          the position of the new object
   * @param element
   *          the object to add
   * @return A new array containing the existing elements and the new element
   * @throws IndexOutOfBoundsException
   *           if the current is out of range (current < 0 || current > array.length).
   */
  public static char[] add(final char[] array, final int index, final char element) {
    return (char[]) add(array, index, new Character(element), Character.TYPE);
  }

  /**
   * Inserts the specified element at the specified position in the array.
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * This method returns a new array with the same elements of the input array
   * plus the given element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * If the input array is {@code null}, a new one element array is
   * returned whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add([1], 0, 2)         = [2, 1]
   * Arrays.add([2, 6], 2, 3)      = [2, 6, 3]
   * Arrays.add([2, 6], 0, 1)      = [1, 2, 6]
   * Arrays.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
   * </pre>
   *
   * @param array
   *          the array to add the element to, may be {@code null}
   * @param current
   *          the position of the new object
   * @param element
   *          the object to add
   * @return A new array containing the existing elements and the new element
   * @throws IndexOutOfBoundsException
   *           if the current is out of range (current < 0 || current > array.length).
   */
  public static byte[] add(final byte[] array, final int index, final byte element) {
    return (byte[]) add(array, index, new Byte(element), Byte.TYPE);
  }

  /**
   * Inserts the specified element at the specified position in the array.
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * This method returns a new array with the same elements of the input array
   * plus the given element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * If the input array is {@code null}, a new one element array is
   * returned whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add([1], 0, 2)         = [2, 1]
   * Arrays.add([2, 6], 2, 10)     = [2, 6, 10]
   * Arrays.add([2, 6], 0, -4)     = [-4, 2, 6]
   * Arrays.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
   * </pre>
   *
   * @param array
   *          the array to add the element to, may be {@code null}
   * @param current
   *          the position of the new object
   * @param element
   *          the object to add
   * @return A new array containing the existing elements and the new element
   * @throws IndexOutOfBoundsException
   *           if the current is out of range (current < 0 || current > array.length).
   */
  public static short[] add(final short[] array, final int index, final short element) {
    return (short[]) add(array, index, new Short(element), Short.TYPE);
  }

  /**
   * Inserts the specified element at the specified position in the array.
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * This method returns a new array with the same elements of the input array
   * plus the given element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * If the input array is {@code null}, a new one element array is
   * returned whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add([1], 0, 2)         = [2, 1]
   * Arrays.add([2, 6], 2, 10)     = [2, 6, 10]
   * Arrays.add([2, 6], 0, -4)     = [-4, 2, 6]
   * Arrays.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
   * </pre>
   *
   * @param array
   *          the array to add the element to, may be {@code null}
   * @param current
   *          the position of the new object
   * @param element
   *          the object to add
   * @return A new array containing the existing elements and the new element
   * @throws IndexOutOfBoundsException
   *           if the current is out of range (current < 0 || current > array.length).
   */
  public static int[] add(final int[] array, final int index, final int element) {
    return (int[]) add(array, index, new Integer(element), Integer.TYPE);
  }

  /**
   * Inserts the specified element at the specified position in the array.
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * This method returns a new array with the same elements of the input array
   * plus the given element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * If the input array is {@code null}, a new one element array is
   * returned whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add([1L], 0, 2L)           = [2L, 1L]
   * Arrays.add([2L, 6L], 2, 10L)      = [2L, 6L, 10L]
   * Arrays.add([2L, 6L], 0, -4L)      = [-4L, 2L, 6L]
   * Arrays.add([2L, 6L, 3L], 2, 1L)   = [2L, 6L, 1L, 3L]
   * </pre>
   *
   * @param array
   *          the array to add the element to, may be {@code null}
   * @param current
   *          the position of the new object
   * @param element
   *          the object to add
   * @return A new array containing the existing elements and the new element
   * @throws IndexOutOfBoundsException
   *           if the current is out of range (current < 0 || current > array.length).
   */
  public static long[] add(final long[] array, final int index, final long element) {
    return (long[]) add(array, index, new Long(element), Long.TYPE);
  }

  /**
   * Inserts the specified element at the specified position in the array.
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * This method returns a new array with the same elements of the input array
   * plus the given element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * If the input array is {@code null}, a new one element array is
   * returned whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add([1.1f], 0, 2.2f)               = [2.2f, 1.1f]
   * Arrays.add([2.3f, 6.4f], 2, 10.5f)        = [2.3f, 6.4f, 10.5f]
   * Arrays.add([2.6f, 6.7f], 0, -4.8f)        = [-4.8f, 2.6f, 6.7f]
   * Arrays.add([2.9f, 6.0f, 0.3f], 2, 1.0f)   = [2.9f, 6.0f, 1.0f, 0.3f]
   * </pre>
   *
   * @param array
   *          the array to add the element to, may be {@code null}
   * @param current
   *          the position of the new object
   * @param element
   *          the object to add
   * @return A new array containing the existing elements and the new element
   * @throws IndexOutOfBoundsException
   *           if the current is out of range (current < 0 || current > array.length).
   */
  public static float[] add(final float[] array, final int index, final float element) {
    return (float[]) add(array, index, new Float(element), Float.TYPE);
  }

  /**
   * Inserts the specified element at the specified position in the array.
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * This method returns a new array with the same elements of the input array
   * plus the given element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * If the input array is {@code null}, a new one element array is
   * returned whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add([1.1], 0, 2.2)              = [2.2, 1.1]
   * Arrays.add([2.3, 6.4], 2, 10.5)        = [2.3, 6.4, 10.5]
   * Arrays.add([2.6, 6.7], 0, -4.8)        = [-4.8, 2.6, 6.7]
   * Arrays.add([2.9, 6.0, 0.3], 2, 1.0)    = [2.9, 6.0, 1.0, 0.3]
   * </pre>
   *
   * @param array
   *          the array to add the element to, may be {@code null}
   * @param current
   *          the position of the new object
   * @param element
   *          the object to add
   * @return A new array containing the existing elements and the new element
   * @throws IndexOutOfBoundsException
   *           if the current is out of range (current < 0 || current > array.length).
   */
  public static double[] add(final double[] array, final int index, final double element) {
    return (double[]) add(array, index, new Double(element), Double.TYPE);
  }

  /**
   * Underlying implementation of add(array, current, element) methods. The last
   * parameter is the class, which may not equal element.getClass for
   * primitives.
   *
   * @param array
   *          the array to add the element to, may be {@code null}
   * @param current
   *          the position of the new object
   * @param element
   *          the object to add
   * @param clss
   *          the type of the element being added
   * @return A new array containing the existing elements and the new element
   */
  private static Object add(final Object array, final int index, final Object element, final Class<?> clss) {
    if (array == null) {
      if (index != 0) {
        throw new IndexOutOfBoundsException("Index: " + index + ", Length: 0");
      }
      final Object joinedArray = Array.newInstance(clss, 1);
      Array.set(joinedArray, 0, element);
      return joinedArray;
    }
    final int length = Array.getLength(array);
    if ((index > length) || (index < 0)) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Length: "
          + length);
    }
    final Object result = Array.newInstance(clss, length + 1);
    System.arraycopy(array, 0, result, 0, index);
    Array.set(result, index, element);
    if (index < length) {
      System.arraycopy(array, index, result, index + 1, length - index);
    }
    return result;
  }

  /**
   * Removes the element at the specified position from the specified array. All
   * subsequent elements are shifted to the left (substracts one from their
   * indices).
   *
   * This method returns a new array with the same elements of the input array
   * except the element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * If the input array is {@code null}, an IndexOutOfBoundsException will
   * be thrown, because in that case no valid current can be specified.
   *
   * <pre>
   * Arrays.remove(["a"], 0)           = []
   * Arrays.remove(["a", "b"], 0)      = ["b"]
   * Arrays.remove(["a", "b"], 1)      = ["a"]
   * Arrays.remove(["a", "b", "c"], 1) = ["a", "c"]
   * </pre>
   *
   * @param array
   *          the array to remove the element from, may not be {@code null}
   * @param current
   *          the position of the element to be removed
   * @return A new array containing the existing elements except the element at
   *         the specified position.
   * @throws IndexOutOfBoundsException
   *           if the current is out of range (current < 0 || current >=
   *           array.length), or if the array is {@code null}.
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] remove(final T[] array, final int index) {
    final Object obj = array;
    return (T[]) remove(obj, index);
  }

  /**
   * Removes the first occurrence of the specified element from the specified
   * array. All subsequent elements are shifted to the left (substracts one from
   * their indices). If the array doesn't contains such an element, no elements
   * are removed from the array.
   *
   * This method returns a new array with the same elements of the input array
   * except the first occurrence of the specified element. The component type of
   * the returned array is always the same as that of the input array.
   *
   * <pre>
   * Arrays.removeElement(null, "a")            = null
   * Arrays.removeElement([], "a")              = []
   * Arrays.removeElement(["a"], "b")           = ["a"]
   * Arrays.removeElement(["a", "b"], "a")      = ["b"]
   * Arrays.removeElement(["a", "b", "a"], "a") = ["b", "a"]
   * </pre>
   *
   * @param array
   *          the array to remove the element from, may be {@code null}
   * @param element
   *          the element to be removed
   * @return A new array containing the existing elements except the first
   *         occurrence of the specified element.
   */
  public static<T> T[] removeElement(final T[] array, final T element) {
    final int index = indexOf(array, element);
    if (index == INDEX_NOT_FOUND) {
      return Assignment.clone(array);
    }
    return remove(array, index);
  }

  /**
   * Removes the element at the specified position from the specified array. All
   * subsequent elements are shifted to the left (substracts one from their
   * indices).
   *
   * This method returns a new array with the same elements of the input array
   * except the element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * If the input array is {@code null}, an IndexOutOfBoundsException will
   * be thrown, because in that case no valid current can be specified.
   *
   * <pre>
   * Arrays.remove([true], 0)              = []
   * Arrays.remove([true, false], 0)       = [false]
   * Arrays.remove([true, false], 1)       = [true]
   * Arrays.remove([true, true, false], 1) = [true, false]
   * </pre>
   *
   * @param array
   *          the array to remove the element from, may not be {@code null}
   * @param current
   *          the position of the element to be removed
   * @return A new array containing the existing elements except the element at
   *         the specified position.
   * @throws IndexOutOfBoundsException
   *           if the current is out of range (current < 0 || current >=
   *           array.length), or if the array is {@code null}.
   */
  public static boolean[] remove(final boolean[] array, final int index) {
    return (boolean[]) remove((Object) array, index);
  }

  /**
   * Removes the first occurrence of the specified element from the specified
   * array. All subsequent elements are shifted to the left (substracts one from
   * their indices). If the array doesn't contains such an element, no elements
   * are removed from the array.
   *
   * This method returns a new array with the same elements of the input array
   * except the first occurrence of the specified element. The component type of
   * the returned array is always the same as that of the input array.
   *
   * <pre>
   * Arrays.removeElement(null, true)                = null
   * Arrays.removeElement([], true)                  = []
   * Arrays.removeElement([true], false)             = [true]
   * Arrays.removeElement([true, false], false)      = [true]
   * Arrays.removeElement([true, false, true], true) = [false, true]
   * </pre>
   *
   * @param array
   *          the array to remove the element from, may be {@code null}
   * @param element
   *          the element to be removed
   * @return A new array containing the existing elements except the first
   *         occurrence of the specified element.
   */
  public static boolean[] removeElement(final boolean[] array, final boolean element) {
    final int index = indexOf(array, element);
    if (index == INDEX_NOT_FOUND) {
      return Assignment.clone(array);
    }
    return remove(array, index);
  }

  /**
   * Removes the element at the specified position from the specified array. All
   * subsequent elements are shifted to the left (substracts one from their
   * indices).
   *
   * This method returns a new array with the same elements of the input array
   * except the element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * If the input array is {@code null}, an IndexOutOfBoundsException will
   * be thrown, because in that case no valid current can be specified.
   *
   * <pre>
   * Arrays.remove([1], 0)          = []
   * Arrays.remove([1, 0], 0)       = [0]
   * Arrays.remove([1, 0], 1)       = [1]
   * Arrays.remove([1, 0, 1], 1)    = [1, 1]
   * </pre>
   *
   * @param array
   *          the array to remove the element from, may not be {@code null}
   * @param current
   *          the position of the element to be removed
   * @return A new array containing the existing elements except the element at
   *         the specified position.
   * @throws IndexOutOfBoundsException
   *           if the current is out of range (current < 0 || current >=
   *           array.length), or if the array is {@code null}.
   */
  public static byte[] remove(final byte[] array, final int index) {
    return (byte[]) remove((Object) array, index);
  }

  /**
   * Removes the first occurrence of the specified element from the specified
   * array. All subsequent elements are shifted to the left (substracts one from
   * their indices). If the array doesn't contains such an element, no elements
   * are removed from the array.
   *
   * This method returns a new array with the same elements of the input array
   * except the first occurrence of the specified element. The component type of
   * the returned array is always the same as that of the input array.
   *
   * <pre>
   * Arrays.removeElement(null, 1)        = null
   * Arrays.removeElement([], 1)          = []
   * Arrays.removeElement([1], 0)         = [1]
   * Arrays.removeElement([1, 0], 0)      = [1]
   * Arrays.removeElement([1, 0, 1], 1)   = [0, 1]
   * </pre>
   *
   * @param array
   *          the array to remove the element from, may be {@code null}
   * @param element
   *          the element to be removed
   * @return A new array containing the existing elements except the first
   *         occurrence of the specified element.
   */
  public static byte[] removeElement(final byte[] array, final byte element) {
    final int index = indexOf(array, element);
    if (index == INDEX_NOT_FOUND) {
      return Assignment.clone(array);
    }
    return remove(array, index);
  }

  /**
   * Removes the element at the specified position from the specified array. All
   * subsequent elements are shifted to the left (substracts one from their
   * indices).
   *
   * This method returns a new array with the same elements of the input array
   * except the element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * If the input array is {@code null}, an IndexOutOfBoundsException will
   * be thrown, because in that case no valid current can be specified.
   *
   * <pre>
   * Arrays.remove(['a'], 0)           = []
   * Arrays.remove(['a', 'b'], 0)      = ['b']
   * Arrays.remove(['a', 'b'], 1)      = ['a']
   * Arrays.remove(['a', 'b', 'c'], 1) = ['a', 'c']
   * </pre>
   *
   * @param array
   *          the array to remove the element from, may not be {@code null}
   * @param current
   *          the position of the element to be removed
   * @return A new array containing the existing elements except the element at
   *         the specified position.
   * @throws IndexOutOfBoundsException
   *           if the current is out of range (current < 0 || current >=
   *           array.length), or if the array is {@code null}.
   */
  public static char[] remove(final char[] array, final int index) {
    return (char[]) remove((Object) array, index);
  }

  /**
   * Removes the first occurrence of the specified element from the specified
   * array. All subsequent elements are shifted to the left (substracts one from
   * their indices). If the array doesn't contains such an element, no elements
   * are removed from the array.
   *
   * This method returns a new array with the same elements of the input array
   * except the first occurrence of the specified element. The component type of
   * the returned array is always the same as that of the input array.
   *
   * <pre>
   * Arrays.removeElement(null, 'a')            = null
   * Arrays.removeElement([], 'a')              = []
   * Arrays.removeElement(['a'], 'b')           = ['a']
   * Arrays.removeElement(['a', 'b'], 'a')      = ['b']
   * Arrays.removeElement(['a', 'b', 'a'], 'a') = ['b', 'a']
   * </pre>
   *
   * @param array
   *          the array to remove the element from, may be {@code null}
   * @param element
   *          the element to be removed
   * @return A new array containing the existing elements except the first
   *         occurrence of the specified element.
   */
  public static char[] removeElement(final char[] array, final char element) {
    final int index = indexOf(array, element);
    if (index == INDEX_NOT_FOUND) {
      return Assignment.clone(array);
    }
    return remove(array, index);
  }

  /**
   * Removes the element at the specified position from the specified array. All
   * subsequent elements are shifted to the left (substracts one from their
   * indices).
   *
   * This method returns a new array with the same elements of the input array
   * except the element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * If the input array is {@code null}, an IndexOutOfBoundsException will
   * be thrown, because in that case no valid current can be specified.
   *
   * <pre>
   * Arrays.remove([1.1], 0)           = []
   * Arrays.remove([2.5, 6.0], 0)      = [6.0]
   * Arrays.remove([2.5, 6.0], 1)      = [2.5]
   * Arrays.remove([2.5, 6.0, 3.8], 1) = [2.5, 3.8]
   * </pre>
   *
   * @param array
   *          the array to remove the element from, may not be {@code null}
   * @param current
   *          the position of the element to be removed
   * @return A new array containing the existing elements except the element at
   *         the specified position.
   * @throws IndexOutOfBoundsException
   *           if the current is out of range (current < 0 || current >=
   *           array.length), or if the array is {@code null}.
   */
  public static double[] remove(final double[] array, final int index) {
    return (double[]) remove((Object) array, index);
  }

  /**
   * Removes the first occurrence of the specified element from the specified
   * array. All subsequent elements are shifted to the left (substracts one from
   * their indices). If the array doesn't contains such an element, no elements
   * are removed from the array.
   *
   * This method returns a new array with the same elements of the input array
   * except the first occurrence of the specified element. The component type of
   * the returned array is always the same as that of the input array.
   *
   * <pre>
   * Arrays.removeElement(null, 1.1)            = null
   * Arrays.removeElement([], 1.1)              = []
   * Arrays.removeElement([1.1], 1.2)           = [1.1]
   * Arrays.removeElement([1.1, 2.3], 1.1)      = [2.3]
   * Arrays.removeElement([1.1, 2.3, 1.1], 1.1) = [2.3, 1.1]
   * </pre>
   *
   * @param array
   *          the array to remove the element from, may be {@code null}
   * @param element
   *          the element to be removed
   * @return A new array containing the existing elements except the first
   *         occurrence of the specified element.
   */
  public static double[] removeElement(final double[] array, final double element) {
    final int index = indexOf(array, element);
    if (index == INDEX_NOT_FOUND) {
      return Assignment.clone(array);
    }
    return remove(array, index);
  }

  /**
   * Removes the element at the specified position from the specified array. All
   * subsequent elements are shifted to the left (substracts one from their
   * indices).
   *
   * This method returns a new array with the same elements of the input array
   * except the element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * If the input array is {@code null}, an IndexOutOfBoundsException will
   * be thrown, because in that case no valid current can be specified.
   *
   * <pre>
   * Arrays.remove([1.1], 0)           = []
   * Arrays.remove([2.5, 6.0], 0)      = [6.0]
   * Arrays.remove([2.5, 6.0], 1)      = [2.5]
   * Arrays.remove([2.5, 6.0, 3.8], 1) = [2.5, 3.8]
   * </pre>
   *
   * @param array
   *          the array to remove the element from, may not be {@code null}
   * @param current
   *          the position of the element to be removed
   * @return A new array containing the existing elements except the element at
   *         the specified position.
   * @throws IndexOutOfBoundsException
   *           if the current is out of range (current < 0 || current >=
   *           array.length), or if the array is {@code null}.
   */
  public static float[] remove(final float[] array, final int index) {
    return (float[]) remove((Object) array, index);
  }

  /**
   * Removes the first occurrence of the specified element from the specified
   * array. All subsequent elements are shifted to the left (substracts one from
   * their indices). If the array doesn't contains such an element, no elements
   * are removed from the array.
   *
   * This method returns a new array with the same elements of the input array
   * except the first occurrence of the specified element. The component type of
   * the returned array is always the same as that of the input array.
   *
   * <pre>
   * Arrays.removeElement(null, 1.1)            = null
   * Arrays.removeElement([], 1.1)              = []
   * Arrays.removeElement([1.1], 1.2)           = [1.1]
   * Arrays.removeElement([1.1, 2.3], 1.1)      = [2.3]
   * Arrays.removeElement([1.1, 2.3, 1.1], 1.1) = [2.3, 1.1]
   * </pre>
   *
   * @param array
   *          the array to remove the element from, may be {@code null}
   * @param element
   *          the element to be removed
   * @return A new array containing the existing elements except the first
   *         occurrence of the specified element.
   */
  public static float[] removeElement(final float[] array, final float element) {
    final int index = indexOf(array, element);
    if (index == INDEX_NOT_FOUND) {
      return Assignment.clone(array);
    }
    return remove(array, index);
  }

  /**
   * Removes the element at the specified position from the specified array. All
   * subsequent elements are shifted to the left (substracts one from their
   * indices).
   *
   * This method returns a new array with the same elements of the input array
   * except the element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * If the input array is {@code null}, an IndexOutOfBoundsException will
   * be thrown, because in that case no valid current can be specified.
   *
   * <pre>
   * Arrays.remove([1], 0)         = []
   * Arrays.remove([2, 6], 0)      = [6]
   * Arrays.remove([2, 6], 1)      = [2]
   * Arrays.remove([2, 6, 3], 1)   = [2, 3]
   * </pre>
   *
   * @param array
   *          the array to remove the element from, may not be {@code null}
   * @param current
   *          the position of the element to be removed
   * @return A new array containing the existing elements except the element at
   *         the specified position.
   * @throws IndexOutOfBoundsException
   *           if the current is out of range (current < 0 || current >=
   *           array.length), or if the array is {@code null}.
   */
  public static int[] remove(final int[] array, final int index) {
    return (int[]) remove((Object) array, index);
  }

  /**
   * Removes the first occurrence of the specified element from the specified
   * array. All subsequent elements are shifted to the left (substracts one from
   * their indices). If the array doesn't contains such an element, no elements
   * are removed from the array.
   *
   * This method returns a new array with the same elements of the input array
   * except the first occurrence of the specified element. The component type of
   * the returned array is always the same as that of the input array.
   *
   * <pre>
   * Arrays.removeElement(null, 1)      = null
   * Arrays.removeElement([], 1)        = []
   * Arrays.removeElement([1], 2)       = [1]
   * Arrays.removeElement([1, 3], 1)    = [3]
   * Arrays.removeElement([1, 3, 1], 1) = [3, 1]
   * </pre>
   *
   * @param array
   *          the array to remove the element from, may be {@code null}
   * @param element
   *          the element to be removed
   * @return A new array containing the existing elements except the first
   *         occurrence of the specified element.
   */
  public static int[] removeElement(final int[] array, final int element) {
    final int index = indexOf(array, element);
    if (index == INDEX_NOT_FOUND) {
      return Assignment.clone(array);
    }
    return remove(array, index);
  }

  /**
   * Removes the element at the specified position from the specified array. All
   * subsequent elements are shifted to the left (substracts one from their
   * indices).
   *
   * This method returns a new array with the same elements of the input array
   * except the element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * If the input array is {@code null}, an IndexOutOfBoundsException will
   * be thrown, because in that case no valid current can be specified.
   *
   * <pre>
   * Arrays.remove([1], 0)         = []
   * Arrays.remove([2, 6], 0)      = [6]
   * Arrays.remove([2, 6], 1)      = [2]
   * Arrays.remove([2, 6, 3], 1)   = [2, 3]
   * </pre>
   *
   * @param array
   *          the array to remove the element from, may not be {@code null}
   * @param current
   *          the position of the element to be removed
   * @return A new array containing the existing elements except the element at
   *         the specified position.
   * @throws IndexOutOfBoundsException
   *           if the current is out of range (current < 0 || current >=
   *           array.length), or if the array is {@code null}.
   */
  public static long[] remove(final long[] array, final int index) {
    return (long[]) remove((Object) array, index);
  }

  /**
   * Removes the first occurrence of the specified element from the specified
   * array. All subsequent elements are shifted to the left (substracts one from
   * their indices). If the array doesn't contains such an element, no elements
   * are removed from the array.
   *
   * This method returns a new array with the same elements of the input array
   * except the first occurrence of the specified element. The component type of
   * the returned array is always the same as that of the input array.
   *
   * <pre>
   * Arrays.removeElement(null, 1)      = null
   * Arrays.removeElement([], 1)        = []
   * Arrays.removeElement([1], 2)       = [1]
   * Arrays.removeElement([1, 3], 1)    = [3]
   * Arrays.removeElement([1, 3, 1], 1) = [3, 1]
   * </pre>
   *
   * @param array
   *          the array to remove the element from, may be {@code null}
   * @param element
   *          the element to be removed
   * @return A new array containing the existing elements except the first
   *         occurrence of the specified element.
   */
  public static long[] removeElement(final long[] array, final long element) {
    final int index = indexOf(array, element);
    if (index == INDEX_NOT_FOUND) {
      return Assignment.clone(array);
    }
    return remove(array, index);
  }

  /**
   * Removes the element at the specified position from the specified array. All
   * subsequent elements are shifted to the left (substracts one from their
   * indices).
   *
   * This method returns a new array with the same elements of the input array
   * except the element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * If the input array is {@code null}, an IndexOutOfBoundsException will
   * be thrown, because in that case no valid current can be specified.
   *
   * <pre>
   * Arrays.remove([1], 0)         = []
   * Arrays.remove([2, 6], 0)      = [6]
   * Arrays.remove([2, 6], 1)      = [2]
   * Arrays.remove([2, 6, 3], 1)   = [2, 3]
   * </pre>
   *
   * @param array
   *          the array to remove the element from, may not be {@code null}
   * @param current
   *          the position of the element to be removed
   * @return A new array containing the existing elements except the element at
   *         the specified position.
   * @throws IndexOutOfBoundsException
   *           if the current is out of range (current < 0 || current >=
   *           array.length), or if the array is {@code null}.
   */
  public static short[] remove(final short[] array, final int index) {
    return (short[]) remove((Object) array, index);
  }

  /**
   * Removes the first occurrence of the specified element from the specified
   * array. All subsequent elements are shifted to the left (substracts one from
   * their indices). If the array doesn't contains such an element, no elements
   * are removed from the array.
   *
   * This method returns a new array with the same elements of the input array
   * except the first occurrence of the specified element. The component type of
   * the returned array is always the same as that of the input array.
   *
   * <pre>
   * Arrays.removeElement(null, 1)      = null
   * Arrays.removeElement([], 1)        = []
   * Arrays.removeElement([1], 2)       = [1]
   * Arrays.removeElement([1, 3], 1)    = [3]
   * Arrays.removeElement([1, 3, 1], 1) = [3, 1]
   * </pre>
   *
   * @param array
   *          the array to remove the element from, may be {@code null}
   * @param element
   *          the element to be removed
   * @return A new array containing the existing elements except the first
   *         occurrence of the specified element.
   */
  public static short[] removeElement(final short[] array, final short element) {
    final int index = indexOf(array, element);
    if (index == INDEX_NOT_FOUND) {
      return Assignment.clone(array);
    }
    return remove(array, index);
  }

  /**
   * Removes the element at the specified position from the specified array. All
   * subsequent elements are shifted to the left (substracts one from their
   * indices).
   *
   * This method returns a new array with the same elements of the input array
   * except the element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * If the input array is {@code null}, an IndexOutOfBoundsException will
   * be thrown, because in that case no valid current can be specified.
   *
   * @param array
   *          the array to remove the element from, may not be {@code null}
   * @param current
   *          the position of the element to be removed
   * @return A new array containing the existing elements except the element at
   *         the specified position.
   * @throws IndexOutOfBoundsException
   *           if the current is out of range (current < 0 || current >=
   *           array.length), or if the array is {@code null}.
   */
  private static Object remove(final Object array, final int index) {
    final int length = getLength(array);
    if ((index < 0) || (index >= length)) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Length: "
          + length);
    }
    final Object result = Array.newInstance(array.getClass().getComponentType(), length - 1);
    System.arraycopy(array, 0, result, 0, index);
    if (index < (length - 1)) {
      System.arraycopy(array, index + 1, result, index, length - index - 1);
    }
    return result;
  }

  /**
   * Returns a fixed-size list backed by the specified array.  (Changes to
   * the returned list "write through" to the array.)  This method acts
   * as bridge between array-based and collection-based APIs, in
   * combination with {@link Collection#toArray}.  The returned list is
   * serializable and implements {@link RandomAccess}.
   *
   * <p>This method also provides a convenient way to create a fixed-size
   * list initialized to contain several elements:
   * <pre>
   *     List&lt;String&gt; stooges = Arrays.asList("Larry", "Moe", "Curly");
   * </pre>
   *
   * @param a the array by which the list will be backed
   * @return a list view of the specified array
   */
  @SafeVarargs
  public static <T> List<T> asList(final T... a) {
    return new ArrayAsList<T>(a);
  }

  private static class ArrayAsList<E>
          extends AbstractList<E> implements RandomAccess, Serializable {

      private static final long serialVersionUID = 5685648301139710359L;
      private final E[] array;

      ArrayAsList(final E[] array) {
        this.array = requireNonNull("array", array);
      }

      @Override
      public E get(final int index) {
        return array[index];
      }

      @Override
      public int size() {
        return array.length;
      }
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which does not compare less than a specified value.
   *
   * For the function to yield the expected result, the elements in the range
   * shall already be ordered from the lower to the higher.
   *
   * Note that if there is an element equal to the specified value in the range,
   * this function returns the current of the first such element; otherwise, this
   * function returns the current of the first smallest element which is greater
   * than the specified value.
   *
   * The function name {@code lowerBound} comes from the fact that if there
   * is a continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] < value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] > value.</li>
   * </ul>
   *
   * Then the function {@code lowerBound(array, begin, end, value)} will
   * return {@code iter1}, which is the "lower bound" of the maximum
   * continuous range equals to value in the sorted range. If no such continuous
   * range exists, that is, no elements in [begin, end) equals to value, the
   * function will returns the position of the smallest element in [begin, end)
   * which is greater than value.
   *
   * The function use a binary search algorithm to search the lower bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *          a {@code char} array, sorted from the lower to the higher.
   * @param begin
   *          the begin current.
   * @param end
   *          the end current. The range of the array is defined as [begin, end).
   * @param value
   *          the value to be found.
   * @return the current of the first element in [begin, end) which does not
   *         compare less than the specified value. If the range [begin, end) is
   *         not correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *           if <code>begin < 0</code> or {@code begin > end} or
   *           {@code end > array.length}.
   */
  public static int lowerBound(final char[] array, final int begin,
      final int end, final char value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (array[middle] < value) {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      } else {
        n = half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which does not compare less than a specified value.
   *
   * For the function to yield the expected result, the elements in the range
   * shall already be ordered from the lower to the higher.
   *
   * Note that if there is an element equal to the specified value in the range,
   * this function returns the current of the first such element; otherwise, this
   * function returns the current of the first smallest element which is greater
   * than the specified value.
   *
   * The function name {@code lowerBound} comes from the fact that if there
   * is a continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] < value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] > value.</li>
   * </ul>
   *
   * Then the function {@code lowerBound(array, begin, end, value)} will
   * return {@code iter1}, which is the "lower bound" of the maximum
   * continuous range equals to value in the sorted range. If no such continuous
   * range exists, that is, no elements in [begin, end) equals to value, the
   * function will returns the position of the smallest element in [begin, end)
   * which is greater than value.
   *
   * The function use a binary search algorithm to search the lower bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *          a {@code byte} array, sorted from the lower to the higher.
   * @param begin
   *          the begin current.
   * @param end
   *          the end current. The range of the array is defined as [begin, end).
   * @param value
   *          the value to be found.
   * @return the current of the first element in [begin, end) which does not
   *         compare less than the specified value. If the range [begin, end) is
   *         not correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *           if <code>begin < 0</code> or {@code begin > end} or
   *           {@code end > array.length}.
   */
  public static int lowerBound(final byte[] array, final int begin,
      final int end, final byte value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (array[middle] < value) {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      } else {
        n = half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which does not compare less than a specified value.
   *
   * For the function to yield the expected result, the elements in the range
   * shall already be ordered from the lower to the higher.
   *
   * Note that if there is an element equal to the specified value in the range,
   * this function returns the current of the first such element; otherwise, this
   * function returns the current of the first smallest element which is greater
   * than the specified value.
   *
   * The function name {@code lowerBound} comes from the fact that if there
   * is a continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] < value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] > value.</li>
   * </ul>
   *
   * Then the function {@code lowerBound(array, begin, end, value)} will
   * return {@code iter1}, which is the "lower bound" of the maximum
   * continuous range equals to value in the sorted range. If no such continuous
   * range exists, that is, no elements in [begin, end) equals to value, the
   * function will returns the position of the smallest element in [begin, end)
   * which is greater than value.
   *
   * The function use a binary search algorithm to search the lower bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *          a {@code short} array, sorted from the lower to the higher.
   * @param begin
   *          the begin current.
   * @param end
   *          the end current. The range of the array is defined as [begin, end).
   * @param value
   *          the value to be found.
   * @return the current of the first element in [begin, end) which does not
   *         compare less than the specified value. If the range [begin, end) is
   *         not correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *           if <code>begin < 0</code> or {@code begin > end} or
   *           {@code end > array.length}.
   */
  public static int lowerBound(final short[] array, final int begin,
      final int end, final short value) {
     if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (array[middle] < value) {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      } else {
        n = half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which does not compare less than a specified value.
   *
   * For the function to yield the expected result, the elements in the range
   * shall already be ordered from the lower to the higher.
   *
   * Note that if there is an element equal to the specified value in the range,
   * this function returns the current of the first such element; otherwise, this
   * function returns the current of the first smallest element which is greater
   * than the specified value.
   *
   * The function name {@code lowerBound} comes from the fact that if there
   * is a continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] < value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] > value.</li>
   * </ul>
   *
   * Then the function {@code lowerBound(array, begin, end, value)} will
   * return {@code iter1}, which is the "lower bound" of the maximum
   * continuous range equals to value in the sorted range. If no such continuous
   * range exists, that is, no elements in [begin, end) equals to value, the
   * function will returns the position of the smallest element in [begin, end)
   * which is greater than value.
   *
   * The function use a binary search algorithm to search the lower bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *          a {@code int} array, sorted from the lower to the higher.
   * @param begin
   *          the begin current.
   * @param end
   *          the end current. The range of the array is defined as [begin, end).
   * @param value
   *          the value to be found.
   * @return the current of the first element in [begin, end) which does not
   *         compare less than the specified value. If the range [begin, end) is
   *         not correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *           if <code>begin < 0</code> or {@code begin > end} or
   *           {@code end > array.length}.
   */
  public static int lowerBound(final int[] array, final int begin,
      final int end, final int value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (array[middle] < value) {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      } else {
        n = half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which does not compare less than a specified value.
   *
   * For the function to yield the expected result, the elements in the range
   * shall already be ordered from the lower to the higher.
   *
   * Note that if there is an element equal to the specified value in the range,
   * this function returns the current of the first such element; otherwise, this
   * function returns the current of the first smallest element which is greater
   * than the specified value.
   *
   * The function name {@code lowerBound} comes from the fact that if there
   * is a continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] < value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] > value.</li>
   * </ul>
   *
   * Then the function {@code lowerBound(array, begin, end, value)} will
   * return {@code iter1}, which is the "lower bound" of the maximum
   * continuous range equals to value in the sorted range. If no such continuous
   * range exists, that is, no elements in [begin, end) equals to value, the
   * function will returns the position of the smallest element in [begin, end)
   * which is greater than value.
   *
   * The function use a binary search algorithm to search the lower bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *          a {@code long} array, sorted from the lower to the higher.
   * @param begin
   *          the begin current.
   * @param end
   *          the end current. The range of the array is defined as [begin, end).
   * @param value
   *          the value to be found.
   * @return the current of the first element in [begin, end) which does not
   *         compare less than the specified value. If the range [begin, end) is
   *         not correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *           if <code>begin < 0</code> or {@code begin > end} or
   *           {@code end > array.length}.
   */
  public static int lowerBound(final long[] array, final int begin,
      final int end, final long value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (array[middle] < value) {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      } else {
        n = half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which does not compare less than a specified value.
   *
   * For the function to yield the expected result, the elements in the range
   * shall already be ordered from the lower to the higher.
   *
   * Note that if there is an element equal to the specified value in the range,
   * this function returns the current of the first such element; otherwise, this
   * function returns the current of the first smallest element which is greater
   * than the specified value.
   *
   * The function name {@code lowerBound} comes from the fact that if there
   * is a continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] < value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] > value.</li>
   * </ul>
   *
   * Then the function {@code lowerBound(array, begin, end, value)} will
   * return {@code iter1}, which is the "lower bound" of the maximum
   * continuous range equals to value in the sorted range. If no such continuous
   * range exists, that is, no elements in [begin, end) equals to value, the
   * function will returns the position of the smallest element in [begin, end)
   * which is greater than value.
   *
   * The function use a binary search algorithm to search the lower bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *          a {@code float} array, sorted from the lower to the higher.
   * @param begin
   *          the begin current.
   * @param end
   *          the end current. The range of the array is defined as [begin, end).
   * @param value
   *          the value to be found.
   * @return the current of the first element in [begin, end) which does not
   *         compare less than the specified value. If the range [begin, end) is
   *         not correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *           if <code>begin < 0</code> or {@code begin > end} or
   *           {@code end > array.length}.
   */
  public static int lowerBound(final float[] array, final int begin,
      final int end, final float value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (array[middle] < value) {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      } else {
        n = half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which does not compare less than a specified value.
   *
   * For the function to yield the expected result, the elements in the range
   * shall already be ordered from the lower to the higher.
   *
   * Note that if there is an element equal to the specified value in the range,
   * this function returns the current of the first such element; otherwise, this
   * function returns the current of the first smallest element which is greater
   * than the specified value.
   *
   * The function name {@code lowerBound} comes from the fact that if there
   * is a continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] < value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] > value.</li>
   * </ul>
   *
   * Then the function {@code lowerBound(array, begin, end, value)} will
   * return {@code iter1}, which is the "lower bound" of the maximum
   * continuous range equals to value in the sorted range. If no such continuous
   * range exists, that is, no elements in [begin, end) equals to value, the
   * function will returns the position of the smallest element in [begin, end)
   * which is greater than value.
   *
   * The function use a binary search algorithm to search the lower bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *          a {@code double} array, sorted from the lower to the higher.
   * @param begin
   *          the begin current.
   * @param end
   *          the end current. The range of the array is defined as [begin, end).
   * @param value
   *          the value to be found.
   * @return the current of the first element in [begin, end) which does not
   *         compare less than the specified value. If the range [begin, end) is
   *         not correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *           if <code>begin < 0</code> or {@code begin > end} or
   *           {@code end > array.length}.
   */
  public static int lowerBound(final double[] array, final int begin,
      final int end, final double value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (array[middle] < value) {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      } else {
        n = half;
      }
    }
    return index;
  }


  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which does not compare less than a specified value.
   *
   * For the function to yield the expected result, the elements in the range
   * shall already be ordered from the lower to the higher.
   *
   * Note that if there is an element equal to the specified value in the range,
   * this function returns the current of the first such element; otherwise, this
   * function returns the current of the first smallest element which is greater
   * than the specified value.
   *
   * The function name {@code lowerBound} comes from the fact that if there
   * is a continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] < value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] > value.</li>
   * </ul>
   *
   * Then the function {@code lowerBound(array, begin, end, value)} will
   * return {@code iter1}, which is the "lower bound" of the maximum
   * continuous range equals to value in the sorted range. If no such continuous
   * range exists, that is, no elements in [begin, end) equals to value, the
   * function will returns the position of the smallest element in [begin, end)
   * which is greater than value.
   *
   * The function use a binary search algorithm to search the lower bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *          a {@code Comparable} objects array, sorted from the lower to
   *          the higher.
   * @param begin
   *          the begin current.
   * @param end
   *          the end current. The range of the array is defined as [begin, end).
   * @param value
   *          the value to be found.
   * @return the current of the first element in [begin, end) which does not
   *         compare less than the specified value. If the range [begin, end) is
   *         not correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *           if <code>begin < 0</code> or {@code begin > end} or
   *           {@code end > array.length}.
   */
  public static <T extends Comparable<T>> int lowerBound(final T[] array,
      final int begin, final int end, final T value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (value.compareTo(array[middle]) > 0) { // array[middle] < value
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      } else {
        n = half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which compares greater than a specified value.
   *
   * For the function to yield the expected result, the elements in the range
   * shall already be ordered from the lower to the higher.
   *
   * Note that if there is an element equal to the specified value in the range,
   * this function does NOT return the current of such element; instead, it
   * returns the current of the first smallest element which is strictly greater
   * than the specified value.
   *
   * The function name {@code upperBound} comes from the fact that if there
   * is a continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] < value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] > value.</li>
   * </ul>
   *
   * Then the function {@code upperBound(array, begin, end, value)} will
   * return {@code iter2}, which is the "upper bound" of the maximum
   * continuous range equals to the specified value in the sorted range. If no
   * such continuous range exists, that is, no elements in [begin, end) equals
   * to the specified value, the function will returns the current of the smallest
   * element in [begin, end) which is greater than value.
   *
   * The function use a binary search algorithm to search the upper bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *          a {@code char} array, sorted from the lower to the higher.
   * @param begin
   *          the begin current.
   * @param end
   *          the end current. The range of the array is defined as [begin, end).
   * @param value
   *          the value to be found.
   * @return the current of the first element in [begin, end) which compares
   *         greater than the specified value. If the range [begin, end) is not
   *         correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *           if <code>begin < 0</code> or {@code begin > end} or
   *           {@code end > array.length}.
   */
  public static int upperBound(final char[] array, final int begin,
      final int end, final char value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (value < array[middle]) {
        // search in the lower half part
        n = half;
      } else {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which compares greater than a specified value.
   *
   * For the function to yield the expected result, the elements in the range
   * shall already be ordered from the lower to the higher.
   *
   * Note that if there is an element equal to the specified value in the range,
   * this function does NOT return the current of such element; instead, it
   * returns the current of the first smallest element which is strictly greater
   * than the specified value.
   *
   * The function name {@code upperBound} comes from the fact that if there
   * is a continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] < value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] > value.</li>
   * </ul>
   *
   * Then the function {@code upperBound(array, begin, end, value)} will
   * return {@code iter2}, which is the "upper bound" of the maximum
   * continuous range equals to the specified value in the sorted range. If no
   * such continuous range exists, that is, no elements in [begin, end) equals
   * to the specified value, the function will returns the current of the smallest
   * element in [begin, end) which is greater than value.
   *
   * The function use a binary search algorithm to search the upper bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *          a {@code byte} array, sorted from the lower to the higher.
   * @param begin
   *          the begin current.
   * @param end
   *          the end current. The range of the array is defined as [begin, end).
   * @param value
   *          the value to be found.
   * @return the current of the first element in [begin, end) which compares
   *         greater than the specified value. If the range [begin, end) is not
   *         correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *           if <code>begin < 0</code> or {@code begin > end} or
   *           {@code end > array.length}.
   */
  public static int upperBound(final byte[] array, final int begin,
      final int end, final byte value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (value < array[middle]) {
        // search in the lower half part
        n = half;
      } else {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which compares greater than a specified value.
   *
   * For the function to yield the expected result, the elements in the range
   * shall already be ordered from the lower to the higher.
   *
   * Note that if there is an element equal to the specified value in the range,
   * this function does NOT return the current of such element; instead, it
   * returns the current of the first smallest element which is strictly greater
   * than the specified value.
   *
   * The function name {@code upperBound} comes from the fact that if there
   * is a continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] < value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] > value.</li>
   * </ul>
   *
   * Then the function {@code upperBound(array, begin, end, value)} will
   * return {@code iter2}, which is the "upper bound" of the maximum
   * continuous range equals to the specified value in the sorted range. If no
   * such continuous range exists, that is, no elements in [begin, end) equals
   * to the specified value, the function will returns the current of the smallest
   * element in [begin, end) which is greater than value.
   *
   * The function use a binary search algorithm to search the upper bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *          a {@code short} array, sorted from the lower to the higher.
   * @param begin
   *          the begin current.
   * @param end
   *          the end current. The range of the array is defined as [begin, end).
   * @param value
   *          the value to be found.
   * @return the current of the first element in [begin, end) which compares
   *         greater than the specified value. If the range [begin, end) is not
   *         correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *           if <code>begin < 0</code> or {@code begin > end} or
   *           {@code end > array.length}.
   */
  public static int upperBound(final short[] array, final int begin,
      final int end, final short value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (value < array[middle]) {
        // search in the lower half part
        n = half;
      } else {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which compares greater than a specified value.
   *
   * For the function to yield the expected result, the elements in the range
   * shall already be ordered from the lower to the higher.
   *
   * Note that if there is an element equal to the specified value in the range,
   * this function does NOT return the current of such element; instead, it
   * returns the current of the first smallest element which is strictly greater
   * than the specified value.
   *
   * The function name {@code upperBound} comes from the fact that if there
   * is a continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] < value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] > value.</li>
   * </ul>
   *
   * Then the function {@code upperBound(array, begin, end, value)} will
   * return {@code iter2}, which is the "upper bound" of the maximum
   * continuous range equals to the specified value in the sorted range. If no
   * such continuous range exists, that is, no elements in [begin, end) equals
   * to the specified value, the function will returns the current of the smallest
   * element in [begin, end) which is greater than value.
   *
   * The function use a binary search algorithm to search the upper bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *          a {@code int} array, sorted from the lower to the higher.
   * @param begin
   *          the begin current.
   * @param end
   *          the end current. The range of the array is defined as [begin, end).
   * @param value
   *          the value to be found.
   * @return the current of the first element in [begin, end) which compares
   *         greater than the specified value. If the range [begin, end) is not
   *         correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *           if <code>begin < 0</code> or {@code begin > end} or
   *           {@code end > array.length}.
   */
  public static int upperBound(final int[] array, final int begin,
      final int end, final int value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (value < array[middle]) {
        // search in the lower half part
        n = half;
      } else {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which compares greater than a specified value.
   *
   * For the function to yield the expected result, the elements in the range
   * shall already be ordered from the lower to the higher.
   *
   * Note that if there is an element equal to the specified value in the range,
   * this function does NOT return the current of such element; instead, it
   * returns the current of the first smallest element which is strictly greater
   * than the specified value.
   *
   * The function name {@code upperBound} comes from the fact that if there
   * is a continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] < value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] > value.</li>
   * </ul>
   *
   * Then the function {@code upperBound(array, begin, end, value)} will
   * return {@code iter2}, which is the "upper bound" of the maximum
   * continuous range equals to the specified value in the sorted range. If no
   * such continuous range exists, that is, no elements in [begin, end) equals
   * to the specified value, the function will returns the current of the smallest
   * element in [begin, end) which is greater than value.
   *
   * The function use a binary search algorithm to search the upper bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *          a {@code long} array, sorted from the lower to the higher.
   * @param begin
   *          the begin current.
   * @param end
   *          the end current. The range of the array is defined as [begin, end).
   * @param value
   *          the value to be found.
   * @return the current of the first element in [begin, end) which compares
   *         greater than the specified value. If the range [begin, end) is not
   *         correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *           if <code>begin < 0</code> or {@code begin > end} or
   *           {@code end > array.length}.
   */
  public static int upperBound(final long[] array, final int begin,
      final int end, final long value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (value < array[middle]) {
        // search in the lower half part
        n = half;
      } else {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which compares greater than a specified value.
   *
   * For the function to yield the expected result, the elements in the range
   * shall already be ordered from the lower to the higher.
   *
   * Note that if there is an element equal to the specified value in the range,
   * this function does NOT return the current of such element; instead, it
   * returns the current of the first smallest element which is strictly greater
   * than the specified value.
   *
   * The function name {@code upperBound} comes from the fact that if there
   * is a continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] < value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] > value.</li>
   * </ul>
   *
   * Then the function {@code upperBound(array, begin, end, value)} will
   * return {@code iter2}, which is the "upper bound" of the maximum
   * continuous range equals to the specified value in the sorted range. If no
   * such continuous range exists, that is, no elements in [begin, end) equals
   * to the specified value, the function will returns the current of the smallest
   * element in [begin, end) which is greater than value.
   *
   * The function use a binary search algorithm to search the upper bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *          a {@code float} array, sorted from the lower to the higher.
   * @param begin
   *          the begin current.
   * @param end
   *          the end current. The range of the array is defined as [begin, end).
   * @param value
   *          the value to be found.
   * @return the current of the first element in [begin, end) which compares
   *         greater than the specified value. If the range [begin, end) is not
   *         correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *           if <code>begin < 0</code> or {@code begin > end} or
   *           {@code end > array.length}.
   */
  public static int upperBound(final float[] array, final int begin,
      final int end, final float value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (value < array[middle]) {
        // search in the lower half part
        n = half;
      } else {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which compares greater than a specified value.
   *
   * For the function to yield the expected result, the elements in the range
   * shall already be ordered from the lower to the higher.
   *
   * Note that if there is an element equal to the specified value in the range,
   * this function does NOT return the current of such element; instead, it
   * returns the current of the first smallest element which is strictly greater
   * than the specified value.
   *
   * The function name {@code upperBound} comes from the fact that if there
   * is a continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] < value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] > value.</li>
   * </ul>
   *
   * Then the function {@code upperBound(array, begin, end, value)} will
   * return {@code iter2}, which is the "upper bound" of the maximum
   * continuous range equals to the specified value in the sorted range. If no
   * such continuous range exists, that is, no elements in [begin, end) equals
   * to the specified value, the function will returns the current of the smallest
   * element in [begin, end) which is greater than value.
   *
   * The function use a binary search algorithm to search the upper bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *          a {@code double} array, sorted from the lower to the higher.
   * @param begin
   *          the begin current.
   * @param end
   *          the end current. The range of the array is defined as [begin, end).
   * @param value
   *          the value to be found.
   * @return the current of the first element in [begin, end) which compares
   *         greater than the specified value. If the range [begin, end) is not
   *         correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *           if <code>begin < 0</code> or {@code begin > end} or
   *           {@code end > array.length}.
   */
  public static int upperBound(final double[] array, final int begin,
      final int end, final double value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (value < array[middle]) {
        // search in the lower half part
        n = half;
      } else {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which compares greater than a specified value.
   *
   * For the function to yield the expected result, the elements in the range
   * shall already be ordered from the lower to the higher.
   *
   * Note that if there is an element equal to the specified value in the range,
   * this function does NOT return the current of such element; instead, it
   * returns the current of the first smallest element which is strictly greater
   * than the specified value.
   *
   * The function name {@code upperBound} comes from the fact that if there
   * is a continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] < value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] > value.</li>
   * </ul>
   *
   * Then the function {@code upperBound(array, begin, end, value)} will
   * return {@code iter2}, which is the "upper bound" of the maximum
   * continuous range equals to the specified value in the sorted range. If no
   * such continuous range exists, that is, no elements in [begin, end) equals
   * to the specified value, the function will returns the current of the smallest
   * element in [begin, end) which is greater than value.
   *
   * The function use a binary search algorithm to search the upper bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *          a {@code Comparable} objects array, sorted from the lower to
   *          the higher.
   * @param begin
   *          the begin current.
   * @param end
   *          the end current. The range of the array is defined as [begin, end).
   * @param value
   *          the value to be found.
   * @return the current of the first element in [begin, end) which compares
   *         greater than the specified value. If the range [begin, end) is not
   *         correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *           if <code>begin < 0</code> or {@code begin > end} or
   *           {@code end > array.length}.
   */
  public static <T extends Comparable<T>> int upperBound(final T[] array,
      final int begin, final int end, final T value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (value.compareTo(array[middle]) < 0) { // value < array[middle]
        // search in the lower half part
        n = half;
      } else {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      }
    }
    return index;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @return
   *      the sum of all values in the array.
   */
  public static int sum(byte[] array) {
    int result = 0;
    for (final byte v : array) {
      result += v;
    }
    return result;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @param startIndex
   *      the index of value starting the sum.
   * @param endIndex
   *      the index next to the value ending the sum.
   * @return
   *      the sum of all values in the array.
   */
  public static int sum(byte[] array, int startIndex, int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > array.length) {
      endIndex = array.length;
    }
    int result = 0;
    for (int i = startIndex; i < endIndex; ++i) {
      result += array[i];
    }
    return result;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @return
   *      the sum of all values in the array.
   */
  public static int sum(short[] array) {
    int result = 0;
    for (final short v : array) {
      result += v;
    }
    return result;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @param startIndex
   *      the index of value starting the sum.
   * @param endIndex
   *      the index next to the value ending the sum.
   * @return
   *      the sum of all values in the array.
   */
  public static int sum(short[] array, int startIndex, int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > array.length) {
      endIndex = array.length;
    }
    int result = 0;
    for (int i = startIndex; i < endIndex; ++i) {
      result += array[i];
    }
    return result;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @return
   *      the sum of all values in the array.
   */
  public static int sum(int[] array) {
    int result = 0;
    for (final int v : array) {
      result += v;
    }
    return result;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @param startIndex
   *      the index of value starting the sum.
   * @param endIndex
   *      the index next to the value ending the sum.
   * @return
   *      the sum of all values in the array.
   */
  public static int sum(int[] array, int startIndex, int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > array.length) {
      endIndex = array.length;
    }
    int result = 0;
    for (int i = startIndex; i < endIndex; ++i) {
      result += array[i];
    }
    return result;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @return
   *      the sum of all values in the array.
   */
  public static long sum(long[] array) {
    long result = 0;
    for (final long v : array) {
      result += v;
    }
    return result;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @param startIndex
   *      the index of value starting the sum.
   * @param endIndex
   *      the index next to the value ending the sum.
   * @return
   *      the sum of all values in the array.
   */
  public static long sum(long[] array, int startIndex, int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > array.length) {
      endIndex = array.length;
    }
    long result = 0;
    for (int i = startIndex; i < endIndex; ++i) {
      result += array[i];
    }
    return result;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @return
   *      the sum of all values in the array.
   */
  public static float sum(float[] array) {
    float result = 0;
    for (final float v : array) {
      result += v;
    }
    return result;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @param startIndex
   *      the index of value starting the sum.
   * @param endIndex
   *      the index next to the value ending the sum.
   * @return
   *      the sum of all values in the array.
   */
  public static float sum(float[] array, int startIndex, int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > array.length) {
      endIndex = array.length;
    }
    float result = 0;
    for (int i = startIndex; i < endIndex; ++i) {
      result += array[i];
    }
    return result;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @return
   *      the sum of all values in the array.
   */
  public static double sum(double[] array) {
    double result = 0;
    for (final double v : array) {
      result += v;
    }
    return result;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @param startIndex
   *      the index of value starting the sum.
   * @param endIndex
   *      the index next to the value ending the sum.
   * @return
   *      the sum of all values in the array.
   */
  public static double sum(double[] array, int startIndex, int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > array.length) {
      endIndex = array.length;
    }
    double result = 0;
    for (int i = startIndex; i < endIndex; ++i) {
      result += array[i];
    }
    return result;
  }


  /**
   * Products the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @return
   *      the production of all values in the array.
   */
  public static double product(byte[] array) {
    double result = 1;
    for (final byte v : array) {
      result *= v;
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @param startIndex
   *      the index of value starting the sum.
   * @param endIndex
   *      the index next to the value ending the sum.
   * @return
   *      the production of all values in the array.
   */
  public static double product(byte[] array, int startIndex, int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > array.length) {
      endIndex = array.length;
    }
    double result = 1;
    for (int i = startIndex; i < endIndex; ++i) {
      result *= array[i];
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @return
   *      the production of all values in the array.
   */
  public static double product(short[] array) {
    double result = 1;
    for (final short v : array) {
      result *= v;
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @param startIndex
   *      the index of value starting the sum.
   * @param endIndex
   *      the index next to the value ending the sum.
   * @return
   *      the production of all values in the array.
   */
  public static double product(short[] array, int startIndex, int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > array.length) {
      endIndex = array.length;
    }
    double result = 1;
    for (int i = startIndex; i < endIndex; ++i) {
      result *= array[i];
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @return
   *      the production of all values in the array.
   */
  public static double product(int[] array) {
    double result = 1;
    for (final int v : array) {
      result *= v;
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @param startIndex
   *      the index of value starting the sum.
   * @param endIndex
   *      the index next to the value ending the sum.
   * @return
   *      the production of all values in the array.
   */
  public static double product(int[] array, int startIndex, int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > array.length) {
      endIndex = array.length;
    }
    double result = 1;
    for (int i = startIndex; i < endIndex; ++i) {
      result *= array[i];
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @return
   *      the production of all values in the array.
   */
  public static double product(long[] array) {
    double result = 1;
    for (final long v : array) {
      result *= v;
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @param startIndex
   *      the index of value starting the sum.
   * @param endIndex
   *      the index next to the value ending the sum.
   * @return
   *      the production of all values in the array.
   */
  public static double product(long[] array, int startIndex, int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > array.length) {
      endIndex = array.length;
    }
    double result = 1;
    for (int i = startIndex; i < endIndex; ++i) {
      result *= array[i];
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @return
   *      the production of all values in the array.
   */
  public static float product(float[] array) {
    float result = 1;
    for (final float v : array) {
      result *= v;
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @param startIndex
   *      the index of value starting the sum.
   * @param endIndex
   *      the index next to the value ending the sum.
   * @return
   *      the production of all values in the array.
   */
  public static float product(float[] array, int startIndex, int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > array.length) {
      endIndex = array.length;
    }
    float result = 1;
    for (int i = startIndex; i < endIndex; ++i) {
      result *= array[i];
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @return
   *      the production of all values in the array.
   */
  public static double product(double[] array) {
    double result = 1;
    for (final double v : array) {
      result *= v;
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *      the array of values.
   * @param startIndex
   *      the index of value starting the sum.
   * @param endIndex
   *      the index next to the value ending the sum.
   * @return
   *      the production of all values in the array.
   */
  public static double product(double[] array, int startIndex, int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > array.length) {
      endIndex = array.length;
    }
    double result = 1;
    for (int i = startIndex; i < endIndex; ++i) {
      result *= array[i];
    }
    return result;
  }
}
