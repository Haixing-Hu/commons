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

import java.util.Collection;

import javax.annotation.concurrent.ThreadSafe;

/**
 * This class provides functions for calculating the hash code.
 *
 * For example, suppose you need to combine the hash code of three variables: a
 * name, which is a String; a age, which is a int; and a addresses, which is an
 * array of Address objects; and a multi-array, which is a multi-dimensional int
 * array. First, randomly choose two ODD integer number, say, 11 and 12345; then
 * write the following codes:
 *
 * <pre>
 *    String name = ...;
 *    int age = ...;
 *    Address[] addreses = ...;
 *    int[][] multiarray = ...;
 *
 *    int code = 11;
 *    int multiplier = 12345;
 *    code = Hash.combine(code, multiplier, name);
 *    code = Hash.combine(code, multiplier, age);
 *    code = Hash.combine(code, multiplier, address);
 *    code = Hash.combine(code, multiplier, multiarray);
 *    return code;
 * </pre>
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class Hash {

  private Hash() {}

  public static int combine(int code, int multiplier, boolean value) {
    return (code * multiplier) + (value ? 1 : 0);
  }

  public static int combine(int code, int multiplier, boolean[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      for (int i = 0; i < value.length; ++i) {
        code = (code * multiplier) + (value[i] ? 1 : 0);
      }
      return code;
    }
  }

  public static int combine(int code, int multiplier, char value) {
    return (code * multiplier) + value;
  }

  public static int combine(int code, int multiplier, char[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      for (int i = 0; i < value.length; ++i) {
        code = (code * multiplier) + value[i];
      }
      return code;
    }
  }

  public static int combine(int code, int multiplier, byte value) {
    return (code * multiplier) + value;
  }

  public static int combine(int code, int multiplier, byte[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      for (int i = 0; i < value.length; ++i) {
        code = (code * multiplier) + value[i];
      }
      return code;
    }
  }

  public static int combine(int code, int multiplier, short value) {
    return (code * multiplier) + value;
  }

  public static int combine(int code, int multiplier, short[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      for (int i = 0; i < value.length; ++i) {
        code = (code * multiplier) + value[i];
      }
      return code;
    }
  }

  public static int combine(int code, int multiplier, int value) {
    return (code * multiplier) + value;
  }

  public static int combine(int code, int multiplier, int[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      for (int i = 0; i < value.length; ++i) {
        code = (code * multiplier) + value[i];
      }
      return code;
    }
  }

  public static int combine(int code, int multiplier, long value) {
    return (code * multiplier) + (int) (value ^ (value >> 32));
  }

  public static int combine(int code, int multiplier, long[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      for (int i = 0; i < value.length; ++i) {
        long v = value[i];
        code = (code * multiplier) + (int) (v ^ (v >> 32));
      }
      return code;
    }
  }

  public static int combine(int code, int multiplier, float value) {
    return (code * multiplier) + Float.floatToIntBits(value);
  }

  public static int combine(int code, int multiplier, float[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      for (int i = 0; i < value.length; ++i) {
        code = (code * multiplier) + Float.floatToIntBits(value[i]);
      }
      return code;
    }
  }

  public static int combine(int code, int multiplier, double value) {
    long bits = Double.doubleToLongBits(value);
    return (code * multiplier) + (int) (bits ^ (bits >> 32));
  }

  public static int combine(int code, int multiplier, double[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      for (int i = 0; i < value.length; ++i) {
        long bits = Double.doubleToLongBits(value[i]);
        code = (code * multiplier) + (int) (bits ^ (bits >> 32));
      }
      return code;
    }
  }

  public static int combine(int code, int multiplier, Boolean value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  public static int combine(int code, int multiplier, Boolean[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      for (int i = 0; i < value.length; ++i) {
        Boolean x = value[i];
        code = (code * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return code;
    }
  }

  public static int combine(int code, int multiplier, Character value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  public static int combine(int code, int multiplier, Character[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      for (int i = 0; i < value.length; ++i) {
        Character x = value[i];
        code = (code * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return code;
    }
  }

  public static int combine(int code, int multiplier, Byte value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  public static int combine(int code, int multiplier, Byte[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      for (int i = 0; i < value.length; ++i) {
        Byte x = value[i];
        code = (code * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return code;
    }
  }

  public static int combine(int code, int multiplier, Short value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  public static int combine(int code, int multiplier, Short[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      for (int i = 0; i < value.length; ++i) {
        Short x = value[i];
        code = (code * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return code;
    }
  }

  public static int combine(int code, int multiplier, Integer value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  public static int combine(int code, int multiplier, Integer[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      for (int i = 0; i < value.length; ++i) {
        Integer x = value[i];
        code = (code * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return code;
    }
  }

  public static int combine(int code, int multiplier, Long value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  public static int combine(int code, int multiplier, Long[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      for (int i = 0; i < value.length; ++i) {
        Long x = value[i];
        code = (code * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return code;
    }
  }

  public static int combine(int code, int multiplier, Float value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  public static int combine(int code, int multiplier, Float[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      for (int i = 0; i < value.length; ++i) {
        Float x = value[i];
        code = (code * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return code;
    }
  }

  public static int combine(int code, int multiplier, Double value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  public static int combine(int code, int multiplier, Double[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      for (int i = 0; i < value.length; ++i) {
        Double x = value[i];
        code = (code * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return code;
    }
  }

  public static int combine(int code, int multiplier, String value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  public static int combine(int code, int multiplier, String[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      for (int i = 0; i < value.length; ++i) {
        String x = value[i];
        code = (code * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return code;
    }
  }

  public static <E extends Enum<E>> int combine(int code, int multiplier, E value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.ordinal();
    }
  }

  public static <E extends Enum<E>> int combine(int code, int multiplier, E[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      for (int i = 0; i < value.length; ++i) {
        E x = value[i];
        code = (code * multiplier) + (x == null ? 0 : x.ordinal());
      }
      return code;
    }
  }

  public static int combine(int code, int multiplier, Object value) {
    if (value == null) {
      return code * multiplier;
    } else {
      Class<?> valueClass = value.getClass();
      if (valueClass.isArray()) {
        // 'Switch' on type of array, to dispatch to the correct handler
        // This handles multi-dimensional arrays
        if (value instanceof boolean[]) {
          return combine(code, multiplier, (boolean[])value);
        } else if (value instanceof char[]) {
          return combine(code, multiplier, (char[])value);
        } else if (value instanceof byte[]) {
          return combine(code, multiplier, (byte[])value);
        } else if (value instanceof short[]) {
          return combine(code, multiplier, (short[])value);
        } else if (value instanceof int[]) {
          return combine(code, multiplier, (int[])value);
        } else if (value instanceof long[]) {
          return combine(code, multiplier, (long[])value);
        } else if (value instanceof float[]) {
          return combine(code, multiplier, (float[])value);
        } else if (value instanceof double[]) {
          return combine(code, multiplier, (double[])value);
        } else { // an array of non-primitives
          return combine(code, multiplier, (Object[])value);
        }
      } else {
        // value is not an array
        return code * multiplier + value.hashCode();
      }
    }
  }

  public static int combine(int code, int multiplier, Object[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      for (int i = 0; i < value.length; ++i) {
        // Note that it's important to call the Hash.combine(int, int, Object)
        // to calculate the combination of hash code of value[i], since an
        // multi-dimensional array is an Object in Java.
        code = combine(code, multiplier, value[i]);
      }
      return code;
    }
  }

  public static <T> int combine(int code, int multiplier, Collection<T> value) {
    if (value == null) {
      return code * multiplier;
    } else {
      for (T val : value) {
        // Note that it's important to call the Hash.combine(int, int, Object)
        // to calculate the combination of hash code of val, since an
        // multi-dimensional array is an Object in Java.
        code = combine(code, multiplier, val);
      }
      return code;
    }
  }
}
