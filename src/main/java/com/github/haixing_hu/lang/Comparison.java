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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import com.github.haixing_hu.collection.primitive.BooleanCollection;
import com.github.haixing_hu.collection.primitive.ByteCollection;
import com.github.haixing_hu.collection.primitive.CharCollection;
import com.github.haixing_hu.collection.primitive.DoubleCollection;
import com.github.haixing_hu.collection.primitive.FloatCollection;
import com.github.haixing_hu.collection.primitive.IntCollection;
import com.github.haixing_hu.collection.primitive.LongCollection;
import com.github.haixing_hu.collection.primitive.ShortCollection;

/**
 * This class provides functions to compare between objects.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class Comparison {

  /**
   * Compares two {@code boolean} values for order.
   *
   * We assume that {@code true > false} for {@code boolean} values.
   *
   * @param value1
   *          the first {@code boolean} value.
   * @param value2
   *          the second {@code boolean} value.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static int compare(final boolean value1, final boolean value2) {
    if (value1 == value2) {
      return 0;
    } else {
      return (value1 ? +1 : -1);
    }
  }

  /**
   * Compares two {@code boolean} arrays lexically.
   *
   * We assume that {@code true > false} for {@code boolean} values.
   *
   * @param array1
   *          the first {@code boolean} array, which could be null.
   * @param array2
   *          the second {@code boolean} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(@Nullable final boolean[] array1,
      @Nullable final boolean[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code boolean} arrays lexically.
   *
   * We assume that {@code true > false} for {@code boolean} values.
   *
   * @param array1
   *          the first {@code boolean} array.
   * @param n1
   *          the length of the first {@code boolean} array.
   * @param array2
   *          the second {@code boolean} array.
   * @param n1
   *          the length of the second {@code boolean} array.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(final boolean[] array1, final int n1,
      final boolean[] array2, final int n2) {
    final int n = (n1 < n2? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final boolean x = array1[i];
      final boolean y = array2[i];
      if (x != y) {
        return (x ? +1 : -1);
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code Boolean} objects for order.
   *
   * We assume that {@code Boolean.TRUE > Boolean.FALSE > null} for
   * {@code Boolean} objects.
   *
   * @param value1
   *          the first {@code Boolean} object, which could be null.
   * @param value2
   *          the second {@code Boolean} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static int compare(@Nullable final Boolean value1,
      @Nullable final Boolean value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      final boolean vl = value1.booleanValue();
      final boolean vr = value2.booleanValue();
      if (vl == vr) {
        return 0;
      } else {
        return (vl ? +1 : -1);
      }
    }
  }

  /**
   * Compares two {@code Boolean} arrays lexically.
   *
   * We assume that {@code Boolean.TRUE > Boolean.FALSE > null} for
   * {@code Boolean} objects.
   *
   * @param array1
   *          the first {@code Boolean} array, which could be null.
   * @param array2
   *          the second {@code Boolean} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(@Nullable final Boolean[] array1,
      @Nullable final Boolean[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code Boolean} arrays lexically.
   *
   * We assume that {@code Boolean.TRUE > Boolean.FALSE > null} for
   * {@code Boolean} objects.
   *
   * @param array1
   *          the first {@code Boolean} array.
   * @param n1
   *          the length of the first {@code Boolean} array.
   * @param array2
   *          the second {@code Boolean} array.
   * @param n2
   *          the length of the second {@code Boolean} array.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(final Boolean[] array1, final int n1,
      final Boolean[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final int rc = compare(array1[i], array2[i]);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code char} value lexically.
   *
   * The function compares two {@code char} value by their numeric values.
   *
   * @param value1
   *          the first {@code char} value.
   * @param value2
   *          the second {@code char} value.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static int compare(final char value1, final char value2) {
    return (value1 - value2);
  }

  /**
   * Compares two {@code char} arrays lexically.
   *
   * The function compares two {@code char} value by their numeric values.
   *
   * @param array1
   *          the first {@code char} array, which could be null.
   * @param array2
   *          the second {@code char} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(@Nullable final char[] array1,
      @Nullable final char[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code char} arrays lexically ignoring the case.
   *
   * The function compares two {@code char} value by their numeric values
   * of lowercase.
   *
   * @param array1
   *          the first {@code char} array, which could be null.
   * @param array2
   *          the second {@code char} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compareIgnoreCase(@Nullable final char[] array1,
      @Nullable final char[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compareIgnoreCase(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code char} arrays lexically.
   *
   * The function compares two {@code char} value by their numeric values.
   *
   * @param array1
   *          the first {@code char} array.
   * @param n1
   *          the length of the first {@code char} array.
   * @param array2
   *          the second {@code char} array.
   * @param n2
   *          the length of the second {@code char} array.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(final char[] array1, final int n1,
      final char[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final char x = array1[i];
      final char y = array2[i];
      if (x != y) {
        return (x - y);
      }
    }
    return (n1 - n2);
  }


  /**
   * Compares two {@code char} arrays lexically ignoring the case.
   *
   * The function compares two {@code char} value by their numeric values
   * of lowercase.
   *
   * @param array1
   *          the first {@code char} array.
   * @param n1
   *          the length of the first {@code char} array.
   * @param array2
   *          the second {@code char} array.
   * @param n2
   *          the length of the second {@code char} array.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compareIgnoreCase(final char[] array1, final int n1,
      final char[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final char x = Character.toLowerCase(array1[i]);
      final char y = Character.toLowerCase(array2[i]);
      if (x != y) {
        return (x - y);
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code Character} objects for order.
   *
   * We assume that {@code null} is the minimum value for
   * {@code Character} objects.
   *
   * @param value1
   *          the first {@code Character} object, which could be null.
   * @param value2
   *          the second {@code Character} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static int compare(@Nullable final Character value1,
      @Nullable final Character value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      final char vl = value1.charValue();
      final char vr = value2.charValue();
      if (vl == vr) {
        return 0;
      } else {
        return (vl - vr);
      }
    }
  }


  /**
   * Compares two {@code Character} objects for order, ignoring the case.
   *
   * We assume that {@code null} is the minimum value for
   * {@code Character} objects.
   *
   * @param value1
   *          the first {@code Character} object, which could be null.
   * @param value2
   *          the second {@code Character} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static int compareIgnoreCase(@Nullable final Character value1,
      @Nullable final Character value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      final char vl = Character.toLowerCase(value1.charValue());
      final char vr = Character.toLowerCase(value2.charValue());
      if (vl == vr) {
        return 0;
      } else {
        return (vl - vr);
      }
    }
  }

  /**
   * Compares two {@code Character} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for
   * {@code Character} objects.
   *
   * @param array1
   *          the first {@code Character} array, which could be null.
   * @param array2
   *          the second {@code Character} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(@Nullable final Character[] array1,
      @Nullable final Character[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code Character} arrays lexically ignoring the case.
   *
   * We assume that {@code null} is the minimum value for the lowercase of
   * {@code Character} objects.
   *
   * @param array1
   *          the first {@code Character} array, which could be null.
   * @param array2
   *          the second {@code Character} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compareIgnoreCase(@Nullable final Character[] array1,
      @Nullable final Character[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compareIgnoreCase(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code Character} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for
   * {@code Character} objects.
   *
   * @param array1
   *          the first {@code Character} array.
   * @param n1
   *          the length of the first {@code Character} array.
   * @param array2
   *          the second {@code Character} array.
   * @param n2
   *          the length of the second {@code Character} array.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(final Character[] array1, final int n1,
      final Character[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final int rc = compare(array1[i], array2[i]);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code Character} arrays lexically ignoring the case.
   *
   * We assume that {@code null} is the minimum value for the lowercase of
   * {@code Character} objects.
   *
   * @param array1
   *          the first {@code Character} array.
   * @param n1
   *          the length of the first {@code Character} array.
   * @param array2
   *          the second {@code Character} array.
   * @param n2
   *          the length of the second {@code Character} array.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compareIgnoreCase(final Character[] array1, final int n1,
      final Character[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final int rc = compareIgnoreCase(array1[i], array2[i]);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code byte} value lexically.
   *
   * @param value1
   *          the first {@code byte} value.
   * @param value2
   *          the second {@code byte} value.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static int compare(final byte value1, final byte value2) {
    return (value1 - value2);
  }

  /**
   * Compares two {@code byte} arrays lexically.
   *
   * @param array1
   *          the first {@code byte} array, which could be null.
   * @param array2
   *          the second {@code byte} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(@Nullable final byte[] array1,
      @Nullable final byte[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code byte} arrays lexically.
   *
   * @param array1
   *          the first {@code byte} array.
   * @param n1
   *          the length of the first {@code byte} array.
   * @param array2
   *          the second {@code byte} array.
   * @param n2
   *          the length of the second {@code byte} array.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(final byte[] array1, final int n1,
      final byte[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final byte x = array1[i];
      final byte y = array2[i];
      if (x != y) {
        return (x - y);
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code Byte} objects for order.
   *
   * We assume that {@code null} is the minimum value for {@code Byte}
   * objects.
   *
   * @param value1
   *          the first {@code Byte} object, which could be null.
   * @param value2
   *          the second {@code Byte} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static int compare(@Nullable final Byte value1,
        @Nullable final Byte value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      final byte vl = value1.byteValue();
      final byte vr = value2.byteValue();
      if (vl == vr) {
        return 0;
      } else {
        return (vl - vr);
      }
    }
  }

  /**
   * Compares two {@code Byte} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for {@code Byte}
   * objects.
   *
   * @param array1
   *          the first {@code Byte} array, which could be null.
   * @param array2
   *          the second {@code Byte} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(@Nullable final Byte[] array1,
      @Nullable final Byte[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code Byte} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for {@code Byte}
   * objects.
   *
   * @param array1
   *          the first {@code Byte} array.
   * @param n1
   *          the length of the first {@code Byte} array.
   * @param array2
   *          the second {@code Byte} array.
   * @param n2
   *          the length of the second {@code Byte} array.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(final Byte[] array1, final int n1,
      final Byte[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final int rc = compare(array1[i], array2[i]);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code short} value lexically.
   *
   * @param value1
   *          the first {@code short} value.
   * @param value2
   *          the second {@code short} value.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static int compare(final short value1, final short value2) {
    return (value1 - value2);
  }

  /**
   * Compares two {@code short} arrays lexically.
   *
   * @param array1
   *          the first {@code short} array, which could be null.
   * @param array2
   *          the second {@code short} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(@Nullable final short[] array1,
      @Nullable final short[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code short} arrays lexically.
   *
   * @param array1
   *          the first {@code short} array.
   * @param n1
   *          the length of the first {@code short} array.
   * @param array2
   *          the second {@code short} array.
   * @param n2
   *          the length of the second {@code short} array.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(final short[] array1, final int n1,
      final short[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final short x = array1[i];
      final short y = array2[i];
      if (x != y) {
        return (x - y);
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code Short} objects for order.
   *
   * We assume that {@code null} is the minimum value for
   * {@code Short} objects.
   *
   * @param value1
   *          the first {@code Short} object, which could be null.
   * @param value2
   *          the second {@code Short} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static int compare(@Nullable final Short value1,
      @Nullable final Short value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      final short vl = value1.shortValue();
      final short vr = value2.shortValue();
      if (vl == vr) {
        return 0;
      } else {
        return (vl - vr);
      }
    }
  }

  /**
   * Compares two {@code Short} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for
   * {@code Short} objects.
   *
   * @param array1
   *          the first {@code Short} array, which could be null.
   * @param array2
   *          the second {@code Short} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(@Nullable final Short[] array1,
      @Nullable final Short[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code Short} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for
   * {@code Short} objects.
   *
   * @param array1
   *          the first {@code Short} array.
   * @param n1
   *          the length of the first {@code Short} array.
   * @param array2
   *          the second {@code Short} array.
   * @param n2
   *          the length of the second {@code Short} array.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(final Short[] array1, final int n1,
      final Short[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final int rc = compare(array1[i], array2[i]);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code int} value lexically.
   *
   * @param value1
   *          the first {@code int} value.
   * @param value2
   *          the second {@code int} value.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static int compare(final int value1, final int value2) {
    return (value1 - value2);
  }

  /**
   * Compares two {@code int} arrays lexically.
   *
   * @param array1
   *          the first {@code int} array, which could be null.
   * @param array2
   *          the second {@code int} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(@Nullable final int[] array1,
      @Nullable final int[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code int} arrays lexically.
   *
   * @param array1
   *          the first {@code int} array.
   * @param n1
   *          the length of the first {@code int} array.
   * @param array2
   *          the second {@code int} array.
   * @param n2
   *          the length of the second {@code int} array.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(final int[] array1, final int n1,
      final int[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final int x = array1[i];
      final int y = array2[i];
      if (x != y) {
        return (x - y);
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code Integer} objects for order.
   *
   * We assume that {@code null} is the minimum value for
   * {@code Integer} objects.
   *
   * @param value1
   *          the first {@code Integer} object, which could be null.
   * @param value2
   *          the second {@code Integer} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static int compare(@Nullable final Integer value1,
      @Nullable final Integer value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      final int vl = value1.intValue();
      final int vr = value2.intValue();
      if (vl == vr) {
        return 0;
      } else {
        return (vl - vr);
      }
    }
  }

  /**
   * Compares two {@code Integer} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for
   * {@code Integer} objects.
   *
   * @param array1
   *          the first {@code Integer} array, which could be null.
   * @param array2
   *          the second {@code Integer} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(@Nullable final Integer[] array1,
      @Nullable final Integer[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code Integer} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for
   * {@code Integer} objects.
   *
   * @param array1
   *          the first {@code Integer} array.
   * @param n1
   *          the length of the first {@code Integer} array.
   * @param array2
   *          the second {@code Integer} array.
   * @param n2
   *          the length of the second {@code Integer} array.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(final Integer[] array1, final int n1,
      final Integer[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final int rc = compare(array1[i], array2[i]);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code long} value lexically.
   *
   * @param value1
   *          the first {@code long} value.
   * @param value2
   *          the second {@code long} value.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static int compare(final long value1, final long value2) {
    if (value1 == value2) {
      return 0;
    } else {
      return (value1 < value2 ? -1 : +1);
    }
  }

  /**
   * Compares two {@code long} arrays lexically.
   *
   * @param array1
   *          the first {@code long} array, which could be null.
   * @param array2
   *          the second {@code long} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(@Nullable final long[] array1,
      @Nullable final long[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code long} arrays lexically.
   *
   * @param array1
   *          the first {@code long} array.
   * @param n1
   *          the length of the first {@code long} array.
   * @param array2
   *          the second {@code long} array.
   * @param n2
   *          the length of the second {@code long} array.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(final long[] array1, final int n1,
      final long[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final long x = array1[i];
      final long y = array2[i];
      if (x != y) {
        return (x < y ? -1 : +1);
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code Long} objects for order.
   *
   * We assume that {@code null} is the minimum value for {@code Long}
   * objects.
   *
   * @param value1
   *          the first {@code Long} object, which could be null.
   * @param value2
   *          the second {@code Long} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static int compare(@Nullable final Long value1,
      @Nullable final Long value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      final long vl = value1.longValue();
      final long vr = value2.longValue();
      if (vl == vr) {
        return 0;
      } else {
        return (vl < vr ? -1 : +1);
      }
    }
  }

  /**
   * Compares two {@code Long} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for {@code Long}
   * objects.
   *
   * @param array1
   *          the first {@code Long} array, which could be null.
   * @param array2
   *          the second {@code Long} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(@Nullable final Long[] array1,
      @Nullable final Long[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code Long} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for {@code Long}
   * objects.
   *
   * @param array1
   *          the first {@code Long} array.
   * @param n1
   *          the length of the first {@code Long} array.
   * @param array2
   *          the second {@code Long} array.
   * @param n2
   *          the length of the second {@code Long} array.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(final Long[] array1, final int n1,
      final Long[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final int rc = compare(array1[i], array2[i]);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code float} values for order.
   *
   * This method is more comprehensive than the standard Java greater than, less
   * than and equals operators.
   *
   * <ul>
   * <li>It returns {@code -1} if the first value is less than the second.
   * <li>It returns {@code +1} if the first value is greater than the
   * second.
   * <li>It returns {@code 0} if the values are equal.
   * </ul>
   *
   * The ordering is as follows, largest to smallest:
   *
   * <ul>
   * <li>NaN
   * <li>Positive infinity
   * <li>Maximum float
   * <li>Normal positive numbers
   * <li>+0.0
   * <li>-0.0
   * <li>Normal negative numbers
   * <li>Minimum float ({@code -Float.MAX_VALUE})
   * <li>Negative infinity
   * </ul>
   *
   * Comparing {@code NaN} with {@code NaN} will return {@code 0}
   * .
   *
   * @param value1
   *          the first {@code float} value.
   * @param value2
   *          the second {@code float} value.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static int compare(final float value1, final float value2) {
    if (value1 < value2) {
      return -1;
    } else if (value1 > value2) {
      return +1;
    }
    // Need to compare bits to handle 0.0 == -0.0 being true
    // compare should put -0.0 < +0.0
    // Two NaNs are also == for compare purposes
    // where NaN == NaN is false
    final int lhsBits = Float.floatToIntBits(value1);
    final int rhsBits = Float.floatToIntBits(value2);
    if (lhsBits == rhsBits) {
      return 0;
    }
    // Something exotic! A comparison to NaN or 0.0 vs -0.0
    // Fortunately NaN's int is > than everything else
    // Also negzeros bits < poszero
    // NAN: 2143289344
    // MAX: 2139095039
    // NEGZERO: -2147483648
    if (lhsBits < rhsBits) {
      return -1;
    } else {
      return +1;
    }
  }

  /**
   * Compares two {@code float} arrays lexically.
   *
   * @param array1
   *          the first {@code float} array, which could be null.
   * @param array2
   *          the second {@code float} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(@Nullable final float[] array1,
      @Nullable final float[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code float} arrays lexically.
   *
   * @param array1
   *          the first {@code float} array.
   * @param n1
   *          the length of the first {@code float} array.
   * @param array2
   *          the second {@code float} array.
   * @param n2
   *          the length of the second {@code float} array.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(final float[] array1, final int n1,
      final float[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final int rc = compare(array1[i], array2[i]);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code Float} values for order.
   *
   * This method is more comprehensive than the standard Java greater than, less
   * than and equals operators.
   *
   * <ul>
   * <li>It returns {@code -1} if the first value is less than the second.
   * <li>It returns {@code +1} if the first value is greater than the
   * second.
   * <li>It returns {@code 0} if the values are equal.
   * </ul>
   *
   * The ordering is as follows, largest to smallest:
   *
   * <ul>
   * <li>NaN
   * <li>Positive infinity
   * <li>Maximum float
   * <li>Normal positive numbers
   * <li>+0.0
   * <li>-0.0
   * <li>Normal negative numbers
   * <li>Minimum float ({@code -Float.MAX_VALUE})
   * <li>Negative infinityfloat
   * </ul>
   *
   * Comparing {@code NaN} with {@code NaN} will return {@code 0}
   * .
   *
   * @param value1
   *          the first {@code Float} value, which could be null.
   * @param value2
   *          the second {@code Float} value, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *         value compares lexicographically less than, equal to, or greater
   *         than the second value.
   */
  public static int compare(@Nullable final Float value1,
      @Nullable final Float value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    }
    final float x = value1.floatValue();
    final float y = value2.floatValue();
    if (x < y) {
      return -1;
    } else if (x > y) {
      return +1;
    }
    // Need to compare bits to handle 0.0 == -0.0 being true
    // compare should put -0.0 < +0.0
    // Two NaNs are also == for compare purposes
    // where NaN == NaN is false
    final int lhsBits = Float.floatToIntBits(x);
    final int rhsBits = Float.floatToIntBits(y);
    if (lhsBits == rhsBits) {
      return 0;
    }
    // Something exotic! A comparison to NaN or 0.0 vs -0.0
    // Fortunately NaN's int is > than everything else
    // Also negzeros bits < poszero
    // NAN: 2143289344
    // MAX: 2139095039
    // NEGZERO: -2147483648
    if (lhsBits < rhsBits) {
      return -1;
    } else {
      return +1;
    }
  }

  /**
   * Compares two {@code Float} arrays lexically.
   *
   * @param array1
   *          the first {@code Float} array, which could be null.
   * @param array2
   *          the second {@code Float} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(@Nullable final Float[] array1,
      @Nullable final Float[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code Float} arrays lexically.
   *
   * @param array1
   *          the first {@code Float} array.
   * @param n1
   *          the length of the first {@code Float} array.
   * @param array2
   *          the second {@code Float} array.
   * @param n2
   *          the length of the second {@code Float} array.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(final Float[] array1, final int n1,
      final Float[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final int rc = compare(array1[i], array2[i]);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code float} values for order, with regard to an
   * epsilon.
   *
   * If the distance between two {@code float} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param value1
   *          the first {@code float} value.
   * @param value2
   *          the second {@code float} value.
   * @param epsilon
   *          the epsilon.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static int compare(final float value1, final float value2,
      final float epsilon) {
    final float diff = value1 - value2;
    if (Math.abs(diff) <= epsilon) {
      return 0;
    } else {
      return (diff > 0 ? +1 : -1);
    }
  }

  /**
   * Compares two {@code float} arrays lexically, with regard to an
   * epsilon.
   *
   * If the distance between two {@code float} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param array1
   *          the first {@code float} array, which could be null.
   * @param array2
   *          the second {@code float} array, which could be null.
   * @param epsilon
   *        the epsilon.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(@Nullable final float[] array1,
      @Nullable final float[] array2, final float epsilon) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length, epsilon);
    }
  }

  /**
   * Compares two {@code float} arrays lexically, with regard to an
   * epsilon.
   *
   * If the distance between two {@code float} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param array1
   *          the first {@code float} array.
   * @param n1
   *          the length of the first {@code float} array.
   * @param array2
   *          the second {@code float} array.
   * @param n2
   *          the length of the second {@code float} array.
   * @param epsilon
   *        the epsilon.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(final float[] array1, final int n1,
      final float[] array2, final int n2, final float epsilon) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final float diff = array1[i] - array2[i];
      if (Math.abs(diff) > epsilon) {
        return (diff < 0 ? -1 : +1);
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code Float} values for order, with regard to an
   * epsilon.
   *
   * If the distance between two {@code Float} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param value1
   *          the first {@code Float} value, which could be null.
   * @param value2
   *          the second {@code Float} value, which could be null.
   * @param epsilon
   *          the epsilon.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static int compare(@Nullable final Float value1,
      @Nullable final Float value2, final float epsilon) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    }
    final float diff = value1 - value2;
    if (Math.abs(diff) <= epsilon) {
      return 0;
    } else {
      return (diff > 0 ? +1 : -1);
    }
  }

  /**
   * Compares two {@code Float} arrays lexically, with regard to an
   * epsilon.
   *
   * If the distance between two {@code Float} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param array1
   *          the first {@code Float} array, which could be null.
   * @param array2
   *          the second {@code Float} array, which could be null.
   * @param epsilon
   *          the epsilon.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(@Nullable final Float[] array1,
      @Nullable final Float[] array2, final float epsilon) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length, epsilon);
    }
  }

  /**
   * Compares two {@code Float} arrays lexically, with regard to an
   * epsilon.
   *
   * If the distance between two {@code Float} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param array1
   *          the first {@code Float} array.
   * @param n1
   *          the length of the first {@code Float} array.
   * @param array2
   *          the second {@code Float} array.
   * @param n2
   *          the length of the second {@code Float} array.
   * @param epsilon
   *          the epsilon.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(final Float[] array1, final int n1,
      final Float[] array2, final int n2, final float epsilon) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final int rc = compare(array1[i], array2[i], epsilon);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code doubles} for order.
   *
   * This method is more comprehensive than the standard Java greater than, less
   * than and equals operators.
   *
   * <ul>
   * <li>It returns {@code -1} if the first value is less than the second.</li>
   * <li>It returns {@code +1} if the first value is greater than the
   * second.</li>
   * <li>It returns {@code 0} if the values are equal.</li>
   * </ul>
   *
   * The ordering is as follows, largest to smallest:
   *
   * <ul>
   * <li>NaN
   * <li>Positive infinity
   * <li>Maximum double
   * <li>Normal positive numbers
   * <li>+0.0
   * <li>-0.0
   * <li>Normal negative numbers
   * <li>Minimum double ({@code -Double.MAX_VALUE})
   * <li>Negative infinity
   * </ul>
   *
   * Comparing {@code NaN} with {@code NaN} will return {@code 0}
   * .
   *
   * @param value1
   *          the first {@code double} value.
   * @param value2
   *          the second {@code double} value.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static int compare(final double value1, final double value2) {
    if (value1 < value2) {
      return -1;
    } else if (value1 > value2) {
      return +1;
    }
    // Need to compare bits to handle 0.0 == -0.0 being true
    // compare should put -0.0 < +0.0
    // Two NaNs are also == for compare purposes
    // where NaN == NaN is false
    final long lhsBits = Double.doubleToLongBits(value1);
    final long rhsBits = Double.doubleToLongBits(value2);
    if (lhsBits == rhsBits) {
      return 0;
    }
    // Something exotic! A comparison to NaN or 0.0 vs -0.0
    // Fortunately NaN's long is > than everything else
    // Also negzeros bits < poszero
    // NAN: 9221120237041090560
    // MAX: 9218868437227405311
    // NEGZERO: -9223372036854775808
    if (lhsBits < rhsBits) {
      return -1;
    } else {
      return +1;
    }
  }

  /**
   * Compares two {@code double} arrays lexically.
   *
   * @param array1
   *          the first {@code double} array, which could be null.
   * @param array2
   *          the second {@code double} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(@Nullable final double[] array1,
      @Nullable final double[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code double} arrays lexically.
   *
   * @param array1
   *          the first {@code double} array.
   * @param n1
   *          the length of the first {@code double} array.
   * @param array2
   *          the second {@code double} array.
   * @param n2
   *          the length of the second {@code double} array.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(final double[] array1, final int n1,
      final double[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final int rc = compare(array1[i], array2[i]);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code Double} for order.
   *
   * This method is more comprehensive than the standard Java greater than, less
   * than and equals operators.
   *
   * <ul>
   * <li>It returns {@code -1} if the first value is less than the second.</li>
   * <li>It returns {@code +1} if the first value is greater than the
   * second.</li>
   * <li>It returns {@code 0} if the values are equal.</li>
   * </ul>
   *
   * The ordering is as follows, largest to smallest:
   *
   * <ul>
   * <li>NaN
   * <li>Positive infinity
   * <li>Maximum double
   * <li>Normal positive numbers
   * <li>+0.0
   * <li>-0.0
   * <li>Normal negative numbers
   * <li>Minimum double ({@code -Double.MAX_VALUE})
   * <li>Negative infinity
   * </ul>
   *
   * Comparing {@code NaN} with {@code NaN} will return {@code 0}
   * .
   *
   * @param value1
   *          the first {@code Double} value, which could be null.
   * @param value2
   *          the second {@code Double} value, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static int compare(@Nullable final Double value1,
      @Nullable final Double value2) {
   if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    }
    final double x = value1.doubleValue();
    final double y = value2.doubleValue();
    if (x < y) {
      return -1;
    } else if (x > y) {
      return +1;
    }
    // Need to compare bits to handle 0.0 == -0.0 being true
    // compare should put -0.0 < +0.0
    // Two NaNs are also == for compare purposes
    // where NaN == NaN is false
    final long lhsBits = Double.doubleToLongBits(x);
    final long rhsBits = Double.doubleToLongBits(y);
    if (lhsBits == rhsBits) {
      return 0;
    }
    // Something exotic! A comparison to NaN or 0.0 vs -0.0
    // Fortunately NaN's long is > than everything else
    // Also negzeros bits < poszero
    // NAN: 9221120237041090560
    // MAX: 9218868437227405311
    // NEGZERO: -9223372036854775808
    if (lhsBits < rhsBits) {
      return -1;
    } else {
      return +1;
    }
  }

  /**
   * Compares two {@code Double} arrays lexically.
   *
   * @param array1
   *          the first {@code Double} array, which could be null.
   * @param array2
   *          the second {@code Double} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(@Nullable final Double[] array1,
      @Nullable final Double[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code Double} arrays lexically.
   *
   * @param array1
   *          the first {@code Double} array.
   * @param n1
   *          the length of the first {@code Double} array.
   * @param array2
   *          the second {@code Double} array.
   * @param n1
   *          the length of the first {@code Double} array.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(final Double[] array1, final int n1,
      final Double[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final int rc = compare(array1[i], array2[i]);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code double} values for order, with regard to an
   * epsilon.
   *
   * If the distance between two {@code double} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param value1
   *          the first {@code double} value.
   * @param value2
   *          the second {@code double} value.
   * @param epsilon
   *          the epsilon.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static int compare(final double value1, final double value2,
      final double epsilon) {
    final double diff = value1 - value2;
    if (Math.abs(diff) <= epsilon) {
      return 0;
    } else {
      return (diff > 0 ? +1 : -1);
    }
  }

  /**
   * Compares two {@code double} arrays lexically, with regard to an
   * epsilon.
   *
   * If the distance between two {@code double} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param array1
   *          the first {@code double} array, which could be null.
   * @param array2
   *          the second {@code double} array, which could be null.
   * @param epsilon
   *          the epsilon.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(@Nullable final double[] array1,
      @Nullable final double[] array2, final double epsilon) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length, epsilon);
    }
  }

  /**
   * Compares two {@code double} arrays lexically, with regard to an
   * epsilon.
   *
   * If the distance between two {@code double} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param array1
   *          the first {@code double} array.
   * @param n1
   *          the length of the first {@code double} array.
   * @param array2
   *          the second {@code double} array.
   * @param n2
   *          the length of the second {@code double} array.
   * @param epsilon
   *          the epsilon.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(final double[] array1, final int n1,
      final double[] array2, final int n2, final double epsilon) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final double diff = array1[i] - array2[i];
      if (Math.abs(diff) > epsilon) {
        return (diff < 0 ? -1 : +1);
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code Double} values for order, with regard to an
   * epsilon.
   *
   * If the distance between two {@code Double} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param value1
   *          the first {@code Double} value, which could be null.
   * @param value2
   *          the second {@code Double} value, which could be null.
   * @param epsilon
   *          the epsilon.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static int compare(@Nullable final Double value1,
      @Nullable final Double value2, final double epsilon) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    }
    final double diff = value1 - value2;
    if (Math.abs(diff) <= epsilon) {
      return 0;
    } else {
      return (diff > 0 ? +1 : -1);
    }
  }

  /**
   * Compares two {@code Double} arrays lexically, with regard to an
   * epsilon.
   *
   * If the distance between two {@code Double} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param array1
   *          the first {@code Double} array, which could be null.
   * @param array2
   *          the second {@code Double} array, which could be null.
   * @param epsilon
   *          the epsilon.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(@Nullable final Double[] array1,
      @Nullable final Double[] array2, final double epsilon) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length, epsilon);
    }
  }

  /**
   * Compares two {@code Double} arrays lexically, with regard to an
   * epsilon.
   *
   * If the distance between two {@code Double} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param array1
   *          the first {@code Double} array.
   * @param n1
   *          the length of the first {@code Double} array.
   * @param array2
   *          the second {@code Double} array.
   * @param n2
   *          the length of the second {@code Double} array.
   * @param epsilon
   *          the epsilon.
   * @return An integer less than, equal to or greater than 0, if the
   *         first array compares lexicographically less than, equal to, or
   *         greater than the second array.
   */
  public static int compare(final Double[] array1, final int n1,
      final Double[] array2, final int n2, final double epsilon) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final int rc = compare(array1[i], array2[i], epsilon);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code Enum} objects for order.
   *
   * We assume that {@code null} is the minimum value for {@code Enum}
   * objects.
   *
   * @param value1
   *          the first {@code Enum} object, which could be null.
   * @param value2
   *          the second {@code Enum} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the
   *         first value compares lexicographically less than, equal to, or
   *         greater than the second value.
   */
  public static <E extends Enum<E>> int compare(@Nullable final E value1,
      @Nullable final E value2) {
    if (value1 == value2) {
      return 0;
    } else if (value1 == null) {
      return -1;
    } else if (value2 == null) {
      return +1;
    } else {
      return value1.ordinal() - value2.ordinal();
    }
  }

  /**
   * Compares two {@code Enum} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for {@code Enum}
   * objects.
   *
   * @param <E>
   *          the type of an {@code Enum} class.
   * @param array1
   *          the first {@code Enum} array, which could be null.
   * @param array2
   *          the second {@code Enum} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *         array compares lexicographically less than, equal to, or greater
   *         than the second array.
   */
  public static <E extends Enum<E>> int compare(@Nullable final E[] array1,
      @Nullable final E[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code Enum} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for {@code Enum}
   * objects.
   *
   * @param <E>
   *          the type of an {@code Enum} class.
   * @param array1
   *          the first {@code Enum} array.
   * @param n1
   *          the length of the first {@code Enum} array.
   * @param array2
   *          the second {@code Enum} array.
   * @param n2
   *          the length of the second {@code Enum} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *         array compares lexicographically less than, equal to, or greater
   *         than the second array.
   */
  public static <E extends Enum<E>> int compare(final E[] array1, final int n1,
      final E[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final E x = array1[i];
      final E y = array2[i];
      if (x != y) {
        if (x == null) {
          return -1;
        } else if (y == null) {
          return +1;
        } else {
          final int rc = x.ordinal() - y.ordinal();
          if (rc != 0) {
            return rc;
          }
        }
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code String} objects for order.
   *
   * We assume that {@code null} is the minimum value for
   * {@code String} objects.
   *
   * @param value1
   *          the first {@code String} object, which could be null.
   * @param value2
   *          the second {@code String} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *         value compares lexicographically less than, equal to, or greater
   *         than the second value.
   */
  public static int compare(@Nullable final String value1,
      @Nullable final String value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      return value1.compareTo(value2);
    }
  }

  /**
   * Compares two {@code String} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for
   * {@code String} objects.
   *
   * @param array1
   *          the first {@code String} array, which could be null.
   * @param array2
   *          the second {@code String} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *         array compares lexicographically less than, equal to, or greater
   *         than the second array.
   */
  public static int compare(@Nullable final String[] array1,
      @Nullable final String[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code String} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for
   * {@code String} objects.
   *
   * @param array1
   *          the first {@code String} array.
   * @param n1
   *          the length of the first {@code String} array.
   * @param array2
   *          the second {@code String} array.
   * @param n2
   *          the length of the second {@code String} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *         array compares lexicographically less than, equal to, or greater
   *         than the second array.
   */
  public static int compare(final String[] array1, final int n1,
      final String[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final int rc = compare(array1[i], array2[i]);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code Class} objects for order.
   *
   * We assume that {@code null} is the minimum value for
   * {@code Class} objects.
   *
   * @param value1
   *          the first {@code Class} object, which could be null.
   * @param value2
   *          the second {@code Class} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *         value compares lexicographically less than, equal to, or greater
   *         than the second value.
   */
  public static int compare(@Nullable final Class<?> value1,
      @Nullable final Class<?> value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      return value1.getName().compareTo(value2.getName());
    }
  }

  /**
   * Compares two {@code Class} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for
   * {@code Class} objects.
   *
   * @param array1
   *          the first {@code Class} array, which could be null.
   * @param array2
   *          the second {@code Class} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *         array compares lexicographically less than, equal to, or greater
   *         than the second array.
   */
  public static int compare(@Nullable final Class<?>[] array1,
      @Nullable final Class<?>[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code Class} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for
   * {@code Class} objects.
   *
   * @param array1
   *          the first {@code Class} array.
   * @param n1
   *          the length of the first {@code Class} array.
   * @param array2
   *          the second {@code Class} array.
   * @param n2
   *          the length of the second {@code Class} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *         array compares lexicographically less than, equal to, or greater
   *         than the second array.
   */
  public static int compare(final Class<?>[] array1, final int n1,
      final Class<?>[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final int rc = compare(array1[i], array2[i]);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code Date} objects for order.
   *
   * We assume that {@code null} is the minimum value for
   * {@code Date} objects.
   *
   * @param value1
   *          the first {@code Date} object.
   * @param value2
   *          the second {@code Date} object.
   * @return An integer less than, equal to or greater than 0, if the first
   *         value compares lexicographically less than, equal to, or greater
   *         than the second value.
   */
  public static int compare(@Nullable final Date value1,
      @Nullable final Date value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      return value1.compareTo(value2);
    }
  }

  /**
   * Compares two {@code Date} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for {@code Date}
   * objects.
   *
   * @param array1
   *          the first {@code Date} array, which could be null.
   * @param array2
   *          the second {@code Date} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *         array compares lexicographically less than, equal to, or greater
   *         than the second array.
   */
  public static int compare(@Nullable final Date[] array1,
      @Nullable final Date[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code Date} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for {@code Date}
   * objects.
   *
   * @param array1
   *          the first {@code Date} array.
   * @param n1
   *          the length of the first {@code Date} array.
   * @param array2
   *          the second {@code Date} array.
   * @param n2
   *          the length of the second {@code Date} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *         array compares lexicographically less than, equal to, or greater
   *         than the second array.
   */
  public static int compare(final Date[] array1, final int n1,
      final Date[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final int rc = compare(array1[i], array2[i]);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code BigInteger} objects for order.
   *
   * We assume that {@code null} is the minimum value for
   * {@code BigInteger} objects.
   *
   * @param value1
   *          the first {@code BigInteger} object, which could be null.
   * @param value2
   *          the second {@code BigInteger} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *         value compares lexicographically less than, equal to, or greater
   *         than the second value.
   */
  public static int compare(@Nullable final BigInteger value1,
      @Nullable final BigInteger value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      return value1.compareTo(value2);
    }
  }

  /**
   * Compares two {@code BigInteger} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for
   * {@code BigInteger} objects.
   *
   * @param array1
   *          the first {@code BigInteger} array, which could be null.
   * @param array2
   *          the second {@code BigInteger} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *         array compares lexicographically less than, equal to, or greater
   *         than the second array.
   */
  public static int compare(@Nullable final BigInteger[] array1,
      @Nullable final BigInteger[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code BigInteger} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for
   * {@code BigInteger} objects.
   *
   * @param array1
   *          the first {@code BigInteger} array.
   * @param n1
   *          the length of the first {@code BigInteger} array.
   * @param array2
   *          the second {@code BigInteger} array.
   * @param n2
   *          the length of the second {@code BigInteger} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *         array compares lexicographically less than, equal to, or greater
   *         than the second array.
   */
  public static int compare(final BigInteger[] array1, final int n1,
      final BigInteger[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final int rc = compare(array1[i], array2[i]);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code BigDecimal} objects for order.
   *
   * We assume that {@code null} is the minimum value for
   * {@code BigDecimal} objects.
   *
   * @param value1
   *          the first {@code BigDecimal} object.
   * @param value2
   *          the second {@code BigDecimal} object.
   * @return An integer less than, equal to or greater than 0, if the first
   *         value compares lexicographically less than, equal to, or greater
   *         than the second value.
   */
  public static int compare(@Nullable final BigDecimal value1,
      @Nullable final BigDecimal value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      return value1.compareTo(value2);
    }
  }

  /**
   * Compares two {@code BigDecimal} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for
   * {@code BigDecimal} objects.
   *
   * @param array1
   *          the first {@code BigDecimal} array, which could be null.
   * @param array2
   *          the second {@code BigDecimal} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *         array compares lexicographically less than, equal to, or greater
   *         than the second array.
   */
  public static int compare(@Nullable final BigDecimal[] array1,
      @Nullable final BigDecimal[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code BigDecimal} arrays lexically.
   *
   * We assume that {@code null} is the minimum value for
   * {@code BigDecimal} objects.
   *
   * @param array1
   *          the first {@code BigDecimal} array.
   * @param n1
   *          the length of the first {@code BigDecimal} array.
   * @param array2
   *          the second {@code BigDecimal} array.
   * @param n2
   *          the length of the second {@code BigDecimal} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *         array compares lexicographically less than, equal to, or greater
   *         than the second array.
   */
  public static int compare(final BigDecimal[] array1, final int n1,
      final BigDecimal[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      final int rc = compare(array1[i], array2[i]);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code Comparable} objects for order.
   *<p>
   * We assume that {@code null} is the minimum value for
   * {@code Comparable} objects.
   *<p>
   * Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array is also an Object in Java.
   *
   * @param value1
   *          the first {@code Comparable} object. Note that it could
   *          be null or a multi-dimensional array.
   * @param value2
   *          the second {@code Comparable} object. Note that it could
   *          be null or a multi-dimensional array.
   * @return An integer less than, equal to or greater than 0, if the first
   *         value compares lexicographically less than, equal to, or greater
   *         than the second value.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static int compare(@Nullable final Object value1,
      @Nullable final Object value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      final Class<?> class1 = value1.getClass();
      final Class<?> class2 = value2.getClass();
      if (class1 != class2) {
        return (class1.getName().compareTo(class2.getName()));
      }
      if (class1.isArray()) {
        // switch on type of array, to dispatch to the correct handler
        // handles multi dimensional arrays.
        if (value1 instanceof boolean[]) {
          final boolean[] array1 = (boolean[]) value1;
          final boolean[] array2 = (boolean[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof char[]) {
          final char[] array1 = (char[]) value1;
          final char[] array2 = (char[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof byte[]) {
          final byte[] array1 = (byte[]) value1;
          final byte[] array2 = (byte[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof short[]) {
          final short[] array1 = (short[]) value1;
          final short[] array2 = (short[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof int[]) {
          final int[] array1 = (int[]) value1;
          final int[] array2 = (int[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof long[]) {
          final long[] array1 = (long[]) value1;
          final long[] array2 = (long[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof float[]) {
          final float[] array1 = (float[]) value1;
          final float[] array2 = (float[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof double[]) {
          final double[] array1 = (double[]) value1;
          final double[] array2 = (double[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof String[]) {
          final String[] array1 = (String[]) value1;
          final String[] array2 = (String[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Boolean[]) {
          final Boolean[] array1 = (Boolean[]) value1;
          final Boolean[] array2 = (Boolean[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Character[]) {
          final Character[] array1 = (Character[]) value1;
          final Character[] array2 = (Character[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Short[]) {
          final Short[] array1 = (Short[]) value1;
          final Short[] array2 = (Short[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Integer[]) {
          final Integer[] array1 = (Integer[]) value1;
          final Integer[] array2 = (Integer[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Long[]) {
          final Long[] array1 = (Long[]) value1;
          final Long[] array2 = (Long[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Float[]) {
          final Float[] array1 = (Float[]) value1;
          final Float[] array2 = (Float[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Double[]) {
          final Double[] array1 = (Double[]) value1;
          final Double[] array2 = (Double[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Class<?>[]) {
          final Class<?>[] array1 = (Class<?>[]) value1;
          final Class<?>[] array2 = (Class<?>[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Date[]) {
          final Date[] array1 = (Date[]) value1;
          final Date[] array2 = (Date[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof BigInteger[]) {
          final BigInteger[] array1 = (BigInteger[]) value1;
          final BigInteger[] array2 = (BigInteger[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof BigDecimal[]) {
          final BigDecimal[] array1 = (BigDecimal[]) value1;
          final BigDecimal[] array2 = (BigDecimal[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else { // array of non-primitives
          final Object[] array1 = (Object[]) value1;
          final Object[] array2 = (Object[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        }
      } else if (class1 == Class.class) {
        return compare((Class<?>) value1, (Class<?>) value2);
      } else if (value1 instanceof Comparable<?>) {
        // lhs and rhs are not arrays
        return ((Comparable) value1).compareTo(value2);
      } else {
        // compare the string representation of two objects
        final String str1 = value1.toString();
        final String str2 = value2.toString();
        return str1.compareTo(str2);
      }
    }
  }

  /**
   * Compares two {@code Comparable} object arrays lexically.
   *<p>
   * Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array is also an Object in Java.
   *
   * @param array1
   *          the first {@code Comparable} object array, which could be
   *          null or array of arrays.
   * @param array2
   *          the second {@code Comparable} object array, which could be
   *          null or array of arrays.
   * @return An integer less than, equal to or greater than 0, if the first
   *         array compares lexicographically less than, equal to, or greater
   *         than the second array.
   */
  public static int compare(@Nullable final Object[] array1,
      @Nullable final Object[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code Comparable} object arrays lexically.
   *<p>
   * Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array is also an Object in Java.
   *
   * @param array1
   *          the first {@code Comparable} object array, which could be
   *          array of arrays.
   * @param n1
   *          the length of the first {@code Comparable} object array.
   * @param array2
   *          the second {@code Comparable} object array, which could be
   *          array of arrays.
   * @param n2
   *          the length of the second {@code Comparable} object array.
   * @return An integer less than, equal to or greater than 0, if the first
   *         array compares lexicographically less than, equal to, or greater
   *         than the second array.
   */
  public static int compare(final Object[] array1, final int n1,
      final Object[] array2, final int n2) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      // Note that here is very important to call the
      // Comparison.compare(Object, Object) to compare
      // array1[i] and array2[i], since the multi-array is an Object in Java
      final int rc = compare(array1[i], array2[i]);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code Comparable} objects for order.
   *<p>
   * We assume that {@code null} is the minimum value for
   * {@code Comparable} objects.
   *<p>
   * Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array is also an Object in Java.
   *
   * @param value1
   *          the first {@code Comparable} object. Note that it could
   *          be null or a multi-dimensional array.
   * @param value2
   *          the second {@code Comparable} object. Note that it could
   *          be null or a multi-dimensional array.
   * @param epsilon
   *          the epsilon used to compare float or double values.
   * @return An integer less than, equal to or greater than 0, if the first
   *         value compares lexicographically less than, equal to, or greater
   *         than the second value.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static int compare(@Nullable final Object value1,
      @Nullable final Object value2, final double epsilon) {
    if (value1 == value2) {
      return 0;
    } else if (value1 == null) {
      return -1; // note that rhs != null
    } else if (value2 == null) {
      return +1; // note that lhs != null
    } else {
      final Class<?> class1 = value1.getClass();
      final Class<?> class2 = value2.getClass();
      if (class1 != class2) {
        return (class1.getName().compareTo(class2.getName()));
      }
      if (class1.isArray()) {
        // switch on type of array, to dispatch to the correct handler
        // handles multi dimensional arrays.
        if (value1 instanceof float[]) {
          final float[] array1 = (float[]) value1;
          final float[] array2 = (float[]) value2;
          return compare(array1, array1.length, array2, array2.length,
              (float)epsilon);
        } else if (value1 instanceof double[]) {
          final double[] array1 = (double[]) value1;
          final double[] array2 = (double[]) value2;
          return compare(array1, array1.length, array2, array2.length, epsilon);
        } else if (value1 instanceof boolean[]) {
          final boolean[] array1 = (boolean[]) value1;
          final boolean[] array2 = (boolean[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof char[]) {
          final char[] array1 = (char[]) value1;
          final char[] array2 = (char[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof byte[]) {
          final byte[] array1 = (byte[]) value1;
          final byte[] array2 = (byte[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof short[]) {
          final short[] array1 = (short[]) value1;
          final short[] array2 = (short[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof int[]) {
          final int[] array1 = (int[]) value1;
          final int[] array2 = (int[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof long[]) {
          final long[] array1 = (long[]) value1;
          final long[] array2 = (long[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof String[]) {
          final String[] array1 = (String[]) value1;
          final String[] array2 = (String[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Boolean[]) {
          final Boolean[] array1 = (Boolean[]) value1;
          final Boolean[] array2 = (Boolean[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Character[]) {
          final Character[] array1 = (Character[]) value1;
          final Character[] array2 = (Character[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Byte[]) {
          final Byte[] array1 = (Byte[]) value1;
          final Byte[] array2 = (Byte[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Short[]) {
          final Short[] array1 = (Short[]) value1;
          final Short[] array2 = (Short[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Integer[]) {
          final Integer[] array1 = (Integer[]) value1;
          final Integer[] array2 = (Integer[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Long[]) {
          final Long[] array1 = (Long[]) value1;
          final Long[] array2 = (Long[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Float[]) {
          final Float[] array1 = (Float[]) value1;
          final Float[] array2 = (Float[]) value2;
          return compare(array1, array1.length, array2, array2.length,
              (float)epsilon);
        } else if (value1 instanceof Double[]) {
          final Double[] array1 = (Double[]) value1;
          final Double[] array2 = (Double[]) value2;
          return compare(array1, array1.length, array2, array2.length, epsilon);
        } else if (value1 instanceof Class<?>[]) {
          final Class<?>[] array1 = (Class<?>[]) value1;
          final Class<?>[] array2 = (Class<?>[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Date[]) {
          final Date[] array1 = (Date[]) value1;
          final Date[] array2 = (Date[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof BigInteger[]) {
          final BigInteger[] array1 = (BigInteger[]) value1;
          final BigInteger[] array2 = (BigInteger[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof BigDecimal[]) {
          final BigDecimal[] array1 = (BigDecimal[]) value1;
          final BigDecimal[] array2 = (BigDecimal[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else { // array of non-primitives
          final Object[] array1 = (Object[]) value1;
          final Object[] array2 = (Object[]) value2;
          return compare(array1, array1.length, array2, array2.length, epsilon);
        }
      } else if (class1 == Class.class) {
        return compare((Class<?>) value1, (Class<?>) value2);
      } else if (value1 instanceof Comparable<?>) {
        // lhs and rhs are not arrays
        return ((Comparable) value1).compareTo(value2);
      } else {
        // compare the string representation of two objects
        final String str1 = value1.toString();
        final String str2 = value2.toString();
        return str1.compareTo(str2);
      }
    }
  }

  /**
   * Compares two {@code Comparable} object arrays lexically.
   *<p>
   * Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array is also an Object in Java.
   *
   * @param array1
   *          the first {@code Comparable} object array, which could be
   *          null or array of arrays.
   * @param array2
   *          the second {@code Comparable} object array, which could be
   *          null or array of arrays.
   * @param epsilon
   *          the epsilon used to compare float or double values.
   * @return An integer less than, equal to or greater than 0, if the first
   *         array compares lexicographically less than, equal to, or greater
   *         than the second array.
   */
  public static int compare(@Nullable final Object[] array1,
      @Nullable final Object[] array2, final double epsilon) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length, epsilon);
    }
  }

  /**
   * Compares two {@code Comparable} object arrays lexically.
   *<p>
   * Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array is also an Object in Java.
   *
   * @param array1
   *          the first {@code Comparable} object array, which could be
   *          array of arrays.
   * @param n1
   *          the length of the first {@code Comparable} object array.
   * @param array2
   *          the second {@code Comparable} object array, which could be
   *          array of arrays.
   * @param n2
   *          the length of the second {@code Comparable} object array.
   * @param epsilon
   *          the epsilon used to compare float or double values.
   * @return An integer less than, equal to or greater than 0, if the first
   *         array compares lexicographically less than, equal to, or greater
   *         than the second array.
   */
  public static int compare(final Object[] array1, int n1,
      final Object[] array2, final int n2, final double epsilon) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      // Note that here is very important to call the
      // Comparison.compare(Object, Object) to compare array1[i]
      // and array2[i], since the multi-array is an Object in
      final int rc = compare(array1[i], array2[i], epsilon);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two {@code Comparable} objects for order.
   *<p>
   * We assume that {@code null} is the minimum value for
   * {@code Comparable} objects.
   *<p>
   * Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array is also an Object in Java.
   *
   * @param value1
   *          the first {@code Comparable} object. Note that it could
   *          be null or a multi-dimensional array.
   * @param value2
   *          the second {@code Comparable} object. Note that it could
   *          be null or a multi-dimensional array.
   * @param comparator
   *          the comparator used to compare the underlying objects.
   * @return An integer less than, equal to or greater than 0, if the first
   *         value compares lexicographically less than, equal to, or greater
   *         than the second value.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static int compare(@Nullable final Object value1,
      @Nullable final Object value2, final Comparator comparator) {
    if (value1 == value2) {
      return 0;
    } else if (value1 == null) {
      return -1; // note that rhs != null
    } else if (value2 == null) {
      return +1; // note that lhs != null
    } else {
      final Class<?> class1 = value1.getClass();
      final Class<?> class2 = value2.getClass();
      if (class1 != class2) {
        return (class1.getName().compareTo(class2.getName()));
      }
      if (class1.isArray()) {
        // switch on type of array, to dispatch to the correct handler
        // handles multi dimensional arrays.
        if (value1 instanceof boolean[]) {
          final boolean[] array1 = (boolean[]) value1;
          final boolean[] array2 = (boolean[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof char[]) {
          final char[] array1 = (char[]) value1;
          final char[] array2 = (char[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof byte[]) {
          final byte[] array1 = (byte[]) value1;
          final byte[] array2 = (byte[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof short[]) {
          final short[] array1 = (short[]) value1;
          final short[] array2 = (short[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof int[]) {
          final int[] array1 = (int[]) value1;
          final int[] array2 = (int[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof long[]) {
          final long[] array1 = (long[]) value1;
          final long[] array2 = (long[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof float[]) {
          final float[] array1 = (float[]) value1;
          final float[] array2 = (float[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof double[]) {
          final double[] array1 = (double[]) value1;
          final double[] array2 = (double[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof String[]) {
          final String[] array1 = (String[]) value1;
          final String[] array2 = (String[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Boolean[]) {
          final Boolean[] array1 = (Boolean[]) value1;
          final Boolean[] array2 = (Boolean[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Character[]) {
          final Character[] array1 = (Character[]) value1;
          final Character[] array2 = (Character[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Byte[]) {
          final Byte[] array1 = (Byte[]) value1;
          final Byte[] array2 = (Byte[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Short[]) {
          final Short[] array1 = (Short[]) value1;
          final Short[] array2 = (Short[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Integer[]) {
          final Integer[] array1 = (Integer[]) value1;
          final Integer[] array2 = (Integer[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Long[]) {
          final Long[] array1 = (Long[]) value1;
          final Long[] array2 = (Long[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Float[]) {
          final Float[] array1 = (Float[]) value1;
          final Float[] array2 = (Float[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Double[]) {
          final Double[] array1 = (Double[]) value1;
          final Double[] array2 = (Double[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Class<?>[]) {
          final Class<?>[] array1 = (Class<?>[]) value1;
          final Class<?>[] array2 = (Class<?>[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Date[]) {
          final Date[] array1 = (Date[]) value1;
          final Date[] array2 = (Date[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof BigInteger[]) {
          final BigInteger[] array1 = (BigInteger[]) value1;
          final BigInteger[] array2 = (BigInteger[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof BigDecimal[]) {
          final BigDecimal[] array1 = (BigDecimal[]) value1;
          final BigDecimal[] array2 = (BigDecimal[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else { // array of non-primitives
          final Object[] array1 = (Object[]) value1;
          final Object[] array2 = (Object[]) value2;
          return compare(array1, array1.length, array2, array2.length,
              comparator);
        }
      } else if (class1 == Class.class) {
        return compare((Class<?>) value1, (Class<?>) value2);
      } else {
        // compare the two objects using the comparator
        return comparator.compare(value1, value2);
      }
    }
  }

  /**
   * Compares two {@code Comparable} {@code Object} arrays lexically.
   *<p>
   * Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array is also an Object in Java.
   *
   * @param array1
   *          the first {@code Comparable} {@code Object} array,
   *          which could be null or array of arrays.
   * @param array2
   *          the second {@code Comparable} {@code Object} array,
   *          which could be null or array of arrays.
   * @param comparator
   *          the comparator used to compare the underlying objects.
   * @return An integer less than, equal to or greater than 0, if the first
   *         array compares lexicographically less than, equal to, or greater
   *         than the second array.
   */
  @SuppressWarnings("rawtypes")
  public static int compare(@Nullable final Object[] array1,
      @Nullable final Object[] array2, final Comparator comparator) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length, comparator);
    }
  }

  /**
   * Compares two object arrays lexically.
   *<p>
   * Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array is also an Object in Java.
   *
   * @param array1
   *          the first object array, which could be array of arrays.
   * @param n1
   *          the length of the first {@code Comparable} object array.
   * @param array2
   *          the second object array, which could be array of arrays.
   * @param n2
   *          the length of the second {@code Comparable} object array.
   * @param comparator
   *          the comparator used to compare the underlying objects.
   * @return An integer less than, equal to or greater than 0, if the first
   *         array compares lexicographically less than, equal to, or greater
   *         than the second array.
   */
  @SuppressWarnings("rawtypes")
  public static int compare(final Object[] array1, final int n1,
      final Object[] array2, final int n2, final Comparator comparator) {
    final int n = (n1 < n2 ? n1 : n2);
    for (int i = 0; i < n; ++i) {
      // Note that here is very important to call the
      // Comparison.compare(Object, Object) to compare array1[i]
      // and array2[i], since the multi-array is an Object in
      final int rc = compare(array1[i], array2[i], comparator);
      if (rc != 0) {
        return rc;
      }
    }
    return (n1 - n2);
  }

  /**
   * Compares two collection of {@code boolean} values for order.
   * <p>
   * We assume that {@code null} is the minimum value for
   * {@code Comparable} objects.
   * <p>
   * This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param col1
   *          the first collection of {@code boolean} values, which
   *          could be null.
   * @param col2
   *          the second collection of {@code boolean} values, which
   *          could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *         collection compares lexicographically less than, equal to, or
   *         greater than the second collection.
   */
  public static int compare(@Nullable final BooleanCollection col1,
      @Nullable final BooleanCollection col2) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else {
      return col1.compareTo(col2);
    }
  }

  /**
   * Compares two collection of {@code char} values for order.
   * <p>
   * We assume that {@code null} is the minimum value for
   * {@code Comparable} objects.
   * <p>
   * This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param col1
   *          the first collection of {@code char} values, which
   *          could be null.
   * @param col2
   *          the second collection of {@code char} values, which
   *          could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *         collection compares lexicographically less than, equal to, or
   *         greater than the second collection.
   */
  public static int compare(@Nullable final CharCollection col1,
      @Nullable final CharCollection col2) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else {
      return col1.compareTo(col2);
    }
  }

  /**
   * Compares two collection of {@code byte} values for order.
   * <p>
   * We assume that {@code null} is the minimum value for
   * {@code Comparable} objects.
   * <p>
   * This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param col1
   *          the first collection of {@code byte} values, which
   *          could be null.
   * @param col2
   *          the second collection of {@code byte} values, which
   *          could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *         collection compares lexicographically less than, equal to, or
   *         greater than the second collection.
   */
  public static int compare(@Nullable final ByteCollection col1,
      @Nullable final ByteCollection col2) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else {
      return col1.compareTo(col2);
    }
  }

  /**
   * Compares two collection of {@code short} values for order.
   * <p>
   * We assume that {@code null} is the minimum value for
   * {@code Comparable} objects.
   * <p>
   * This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param col1
   *          the first collection of {@code short} values, which
   *          could be null.
   * @param col2
   *          the second collection of {@code short} values, which
   *          could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *         collection compares lexicographically less than, equal to, or
   *         greater than the second collection.
   */
  public static int compare(@Nullable final ShortCollection col1,
      @Nullable final ShortCollection col2) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else {
      return col1.compareTo(col2);
    }
  }

  /**
   * Compares two collection of {@code int} values for order.
   * <p>
   * We assume that {@code null} is the minimum value for
   * {@code Comparable} objects.
   * <p>
   * This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param col1
   *          the first collection of {@code int} values, which
   *          could be null.
   * @param col2
   *          the second collection of {@code int} values, which
   *          could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *         collection compares lexicographically less than, equal to, or
   *         greater than the second collection.
   */
  public static int compare(@Nullable final IntCollection col1,
      @Nullable final IntCollection col2) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else {
      return col1.compareTo(col2);
    }
  }

  /**
   * Compares two collection of {@code long} values for order.
   * <p>
   * We assume that {@code null} is the minimum value for
   * {@code Comparable} objects.
   * <p>
   * This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param col1
   *          the first collection of {@code long} values, which
   *          could be null.
   * @param col2
   *          the second collection of {@code long} values, which
   *          could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *         collection compares lexicographically less than, equal to, or
   *         greater than the second collection.
   */
  public static int compare(@Nullable final LongCollection col1,
      @Nullable final LongCollection col2) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else {
      return col1.compareTo(col2);
    }
  }

  /**
   * Compares two collection of {@code float} values for order.
   * <p>
   * We assume that {@code null} is the minimum value for
   * {@code Comparable} objects.
   * <p>
   * This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param col1
   *          the first collection of {@code float} values, which
   *          could be null.
   * @param col2
   *          the second collection of {@code float} values, which
   *          could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *         collection compares lexicographically less than, equal to, or
   *         greater than the second collection.
   */
  public static int compare(@Nullable final FloatCollection col1,
      @Nullable final FloatCollection col2) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else {
      return col1.compareTo(col2);
    }
  }

  /**
   * Compares two collection of {@code double} values for order.
   * <p>
   * We assume that {@code null} is the minimum value for
   * {@code Comparable} objects.
   * <p>
   * This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param col1
   *          the first collection of {@code double} values, which
   *          could be null.
   * @param col2
   *          the second collection of {@code double} values, which
   *          could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *         collection compares lexicographically less than, equal to, or
   *         greater than the second collection.
   */
  public static int compare(@Nullable final DoubleCollection col1,
      @Nullable final DoubleCollection col2) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else {
      return col1.compareTo(col2);
    }
  }

  /**
   * Compares two collection of {@code Comparable} objects for order.
   * <p>
   * We assume that {@code null} is the minimum value for
   * {@code Comparable} objects.
   * <p>
   * This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param <T>
   *          the type of the elements of the collection.
   * @param col1
   *          the first collection of {@code Comparable} objects, which
   *          could be null.
   * @param col2
   *          the second collection of {@code Comparable} objects, which
   *          could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *         collection compares lexicographically less than, equal to, or
   *         greater than the second collection.
   */
  public static <T> int compare(@Nullable final Collection<T> col1,
      @Nullable final Collection<T> col2) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else if (col2 == null) {
      return +1;
    }
    final Iterator<T> lhsIter = col1.iterator();
    final Iterator<T> rhsIter = col2.iterator();
    while (lhsIter.hasNext() && rhsIter.hasNext()) {
      final T lhsValue = lhsIter.next();
      final T rhsValue = rhsIter.next();
      final int rc = compare(lhsValue, rhsValue);
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

  /**
   * Compares two collection of {@code Comparable} objects for order.
   * <p>
   * We assume that {@code null} is the minimum value for
   * {@code Comparable} objects.
   * <p>
   * This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param <T>
   *          the type of the elements of the collection.
   * @param col1
   *          the first collection of {@code Comparable} objects, which
   *          could be null.
   * @param col2
   *          the second collection of {@code Comparable} objects, which
   *          could be null.
   * @param epsilon
   *          the epsilon used to compare float or double values.
   * @return An integer less than, equal to or greater than 0, if the first
   *         collection compares lexicographically less than, equal to, or
   *         greater than the second collection.
   */
  public static <T> int compare(@Nullable final Collection<T> col1,
      @Nullable final Collection<T> col2, final double epsilon) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else if (col2 == null) {
      return +1;
    }
    final Iterator<T> lhsIter = col1.iterator();
    final Iterator<T> rhsIter = col2.iterator();
    while (lhsIter.hasNext() && rhsIter.hasNext()) {
      final T lhsValue = lhsIter.next();
      final T rhsValue = rhsIter.next();
      final int rc = compare(lhsValue, rhsValue, epsilon);
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

  /**
   * Compares two collection of {@code Comparable} objects for order.
   * <p>
   * We assume that {@code null} is the minimum value for
   * {@code Comparable} objects.
   * <p>
   * This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param <T>
   *          the type of the elements of the collection.
   * @param col1
   *          the first collection of {@code Comparable} objects, which
   *          could be null.
   * @param col2
   *          the second collection of {@code Comparable} objects, which
   *          could be null.
   * @param comparator
   *          the comparator used to compare the underlying objects.
   * @return An integer less than, equal to or greater than 0, if the first
   *         collection compares lexicographically less than, equal to, or
   *         greater than the second collection.
   */
  public static <T> int compare(@Nullable final Collection<T> col1,
      @Nullable final Collection<T> col2, final Comparator<?> comparator) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else if (col2 == null) {
      return +1;
    }
    final Iterator<T> lhsIter = col1.iterator();
    final Iterator<T> rhsIter = col2.iterator();
    while (lhsIter.hasNext() && rhsIter.hasNext()) {
      final T lhsValue = lhsIter.next();
      final T rhsValue = rhsIter.next();
      final int rc = compare(lhsValue, rhsValue, comparator);
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
}
