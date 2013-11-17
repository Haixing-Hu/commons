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

package com.github.haixing_hu.lang;

import java.util.Iterator;

import javax.annotation.concurrent.ThreadSafe;

/**
 * This class provides the selection related algorithms.
 *
 * TODO:
 * 1. add the select median functions;
 * 2. finish the unit tests.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class Selection {

  private Selection() {}

  /**
   * Returns the minimum of two <code>boolean</code> values.
   *
   * Assume <code>true > false</code> for <code>boolean</code> values.
   *
   * @param value1
   *          a <code>boolean</code> value;
   * @param value2
   *          a <code>boolean</code> value;
   * @return the minimum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the smallest position.
   */
  public static boolean min(boolean value1, boolean value2) {
    if (! value1) {
      return false;
    } else {
      return value2;
    }
  }

  /**
   * Returns the minimum of three <code>boolean</code> values.
   *
   * Assume <code>true > false</code> for <code>boolean</code> values.
   *
   * @param value1
   *          a <code>boolean</code> value;
   * @param value2
   *          a <code>boolean</code> value;
   * @param value3
   *          a <code>boolean</code> value;
   * @return the minimum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         smallest position.
   */
  public static boolean min(boolean value1, boolean value2, boolean value3) {
    if ((! value1) || (! value2)) {
      return false;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum value in a <code>boolean</code> array.
   *
   * Assume <code>true > false</code> for <code>boolean</code> values.
   *
   * @param array
   *          a <code>boolean</code> array, must not be <code>null</code>
   *          nor empty.
   * @return the minimum value in the array. In case of ties, returns the
   *         one with the smallest position.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static boolean min(boolean... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    for (boolean element : array) {
      if (! element) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns the minimum of two <code>Boolean</code> objects.
   *
   * Assume <code>Boolean.TRUE > Boolean.FALSE > null</code> for
   * <code>Boolean</code> objects.
   *
   * @param value1
   *          a <code>Boolean</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Boolean</code> object, could be <code>null</code>.
   * @return the minimum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the smallest position. Note that the
   *         returned value is either <code>null</code> or a reference to one of
   *         the arguments.
   */
  public static Boolean min(Boolean value1, Boolean value2) {
    if ((value1 == null) || (value2 == null)) {
      return null;
    } else {
      boolean v1 = value1.booleanValue();
      boolean v2 = value2.booleanValue();
      if (v1 == v2) {
        return value1;
      } else if (v1) {
        // recall that v1 != v2, therefore v1 == true && v2 == false.
        return value2;
      } else {
        // recall that v1 != v2, therefore v1 == false && v2 == true.
        return value1;
      }
    }
  }

  /**
   * Returns the minimum of three <code>Boolean</code> objects.
   *
   * Assume <code>Boolean.TRUE > Boolean.FALSE > null</code> for
   * <code>Boolean</code> objects.
   *
   * @param value1
   *          a <code>Boolean</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Boolean</code> object, could be <code>null</code>.
   * @param value3
   *          a <code>Boolean</code> object, could be <code>null</code>.
   * @return the minimum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         smallest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of the arguments.
   */
  public static Boolean min(Boolean value1, Boolean value2, Boolean value3) {
    if ((value1 == null) || (value2 == null) || (value3 == null)) {
      return null;
    } else {
      if (! value1.booleanValue()) {
        return value1;
      }
      // now value1 == true
      if (! value2.booleanValue()) {
        return value2;
      }
      // now value1 == value2 == true
      if (! value3.booleanValue()) {
        return value3;
      }
      // now value1 == value2 == value3 == true
      return value1;
    }
  }

  /**
   * Returns the minimum object in a <code>Boolean</code> array.
   *
   * Assume that <code>Boolean.TRUE > Boolean.FALSE > null</code> for
   * <code>Boolean</code> objects.
   *
   * @param array
   *          a <code>Boolean</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the minimum object in the array. In case of ties, returns the one
   *         with the smallest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>array</code>.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static Boolean min(Boolean ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    Boolean min = array[0];
    if (min == null) {
      return null;
    }
    boolean minValue = min.booleanValue();
    for (int i = 1; i < array.length; ++i) {
      Boolean element = array[i];
      if (element == null) {
        return null;
      }
      if (minValue && (! element.booleanValue())) {
        min = element;
        minValue = false;
      }
    }
    return min;
  }


  /**
   * Returns the minimum of two <code>char</code> values.
   *
   * @param value1
   *          a <code>char</code> value;
   * @param value2
   *          a <code>char</code> value;
   * @return the minimum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the smallest position.
   */
  public static char min(char value1, char value2) {
    return (value1 <= value2 ? value1 : value2);
  }

  /**
   * Returns the minimum of three <code>char</code> values.
   *
   * @param value1
   *          a <code>char</code> value;
   * @param value2
   *          a <code>char</code> value;
   * @param value3
   *          a <code>char</code> value;
   * @return the minimum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         smallest position.
   */
  public static char min(char value1, char value2, char value3) {
    if (value1 <= value2) {
      if (value1 <= value3) {
        return value1;
      } else {
        return value3;
      }
    } else if (value2 <= value3) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum value in a <code>char</code> array.
   *
   * @param array
   *          a <code>char</code> array, must not be <code>null</code>
   *          nor empty.
   * @return the minimum value in the array. In case of ties, returns the
   *         one with the smallest position.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static char min(char ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    char min = array[0];
    for (int i = 1; i < array.length; ++i) {
      char element = array[i];
      if (element < min) {
        min = element;
      }
    }
    return min;
  }


  /**
   * Returns the minimum of two <code>Character</code> objects.
   *
   * Assume that null is the minimum value of <code>Character</code> objects.
   *
   * @param value1
   *          a <code>Character</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Character</code> object, could be <code>null</code>.
   * @return the minimum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the smallest position. Note that the
   *         returned value is either <code>null</code> or a reference to one of
   *         the arguments.
   */
  public static Character min(Character value1, Character value2) {
    if ((value1 == null) || (value2 == null)) {
      return null;
    } else {
      char v1 = value1.charValue();
      char v2 = value2.charValue();
      return (v1 <= v2 ? value1 : value2);
    }
  }

  /**
   * Returns the minimum of three <code>Character</code> objects.
   *
   * Assume that null is the minimum value of <code>Character</code> objects.
   *
   * @param value1
   *          a <code>Character</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Character</code> object, could be <code>null</code>.
   * @param value3
   *          a <code>Character</code> object, could be <code>null</code>.
   * @return the minimum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         smallest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of the arguments.
   */
  public static Character min(Character value1, Character value2, Character value3) {
    if ((value1 == null) || (value2 == null) || (value3 == null)) {
      return null;
    }
    char v1 = value1.charValue();
    char v2 = value2.charValue();
    char v3 = value3.charValue();
    if (v1 <= v2) {
      if (v1 <= v3) {
        return value1;
      } else {
        return value3;
      }
    } else if (v2 <= v3) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum object in a <code>Character</code> array.
   *
   * Assume that null is the minimum value of <code>Character</code> objects.
   *
   * @param array
   *          a <code>Character</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the minimum object in the array. In case of ties, returns the one
   *         with the smallest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>array</code>.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static Character min(Character ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    Character min = array[0];
    if (min == null) {
      return null;
    }
    char minValue = min.charValue();
    for (int i = 0; i < array.length; ++i) {
      Character element = array[i];
      if (element == null) {
        return null;
      } else {
        char value = element.charValue();
        if (value < minValue) {
          minValue = value;
          min = element;
        }
      }
    }
    return min;
  }


  /**
   * Returns the minimum of two <code>byte</code> values.
   *
   * @param value1
   *          a <code>byte</code> value;
   * @param value2
   *          a <code>byte</code> value;
   * @return the minimum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the smallest position.
   */
  public static byte min(byte value1, byte value2) {
    return (value1 <= value2 ? value1 : value2);
  }

  /**
   * Returns the minimum of three <code>byte</code> values.
   *
   * @param value1
   *          a <code>byte</code> value;
   * @param value2
   *          a <code>byte</code> value;
   * @param value3
   *          a <code>byte</code> value;
   * @return the minimum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         smallest position.
   */
  public static byte min(byte value1, byte value2, byte value3) {
    if (value1 <= value2) {
      if (value1 <= value3) {
        return value1;
      } else {
        return value3;
      }
    } else if (value2 <= value3) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum value in a <code>byte</code> array.
   *
   * @param array
   *          a <code>byte</code> array, must not be <code>null</code>
   *          nor empty.
   * @return the minimum value in the array. In case of ties, returns the
   *         one with the smallest position.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static byte min(byte ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    byte min = array[0];
    for (int i = 1; i < array.length; ++i) {
      byte element = array[i];
      if (element < min) {
        min = element;
      }
    }
    return min;
  }


  /**
   * Returns the minimum of two <code>Byte</code> objects.
   *
   * Assume that null is the minimum value of <code>Byte</code> objects.
   *
   * @param value1
   *          a <code>Byte</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Byte</code> object, could be <code>null</code>.
   * @return the minimum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the smallest position. Note that the
   *         returned value is either <code>null</code> or a reference to one of
   *         the arguments.
   */
  public static Byte min(Byte value1, Byte value2) {
    if ((value1 == null) || (value2 == null)) {
      return null;
    } else {
      byte v1 = value1.byteValue();
      byte v2 = value2.byteValue();
      return (v1 <= v2 ? value1 : value2);
    }
  }

  /**
   * Returns the minimum of three <code>Byte</code> objects.
   *
   * Assume that null is the minimum value of <code>Byte</code> objects.
   *
   * @param value1
   *          a <code>Byte</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Byte</code> object, could be <code>null</code>.
   * @param value3
   *          a <code>Byte</code> object;
   * @return the minimum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         smallest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of the arguments.
   */
  public static Byte min(Byte value1, Byte value2, Byte value3) {
    if ((value1 == null) || (value2 == null) || (value3 == null)) {
      return null;
    }
    byte v1 = value1.byteValue();
    byte v2 = value2.byteValue();
    byte v3 = value3.byteValue();
    if (v1 <= v2) {
      if (v1 <= v3) {
        return value1;
      } else {
        return value3;
      }
    } else if (v2 <= v3) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum object in a <code>Byte</code> array.
   *
   * Assume that null is the minimum value of <code>Byte</code> objects.
   *
   * @param array
   *          a <code>Byte</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the minimum object in the array. In case of ties, returns the one
   *         with the smallest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>array</code>.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static Byte min(Byte ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    Byte min = array[0];
    if (min == null) {
      return null;
    }
    byte minValue = min.byteValue();
    for (int i = 0; i < array.length; ++i) {
      Byte element = array[i];
      if (element == null) {
        return null;
      } else {
        byte value = element.byteValue();
        if (value < minValue) {
          minValue = value;
          min = element;
        }
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two <code>short</code> values.
   *
   * @param value1
   *          a <code>short</code> value;
   * @param value2
   *          a <code>short</code> value;
   * @return the minimum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the smallest position.
   */
  public static short min(short value1, short value2) {
    return (value1 <= value2 ? value1 : value2);
  }

  /**
   * Returns the minimum of three <code>short</code> values.
   *
   * @param value1
   *          a <code>short</code> value;
   * @param value2
   *          a <code>short</code> value;
   * @param value3
   *          a <code>short</code> value;
   * @return the minimum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         smallest position.
   */
  public static short min(short value1, short value2, short value3) {
    if (value1 <= value2) {
      if (value1 <= value3) {
        return value1;
      } else {
        return value3;
      }
    } else if (value2 <= value3) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum value in a <code>short</code> array.
   *
   * @param array
   *          a <code>short</code> array, must not be <code>null</code>
   *          nor empty.
   * @return the minimum value in the array. In case of ties, returns the
   *         one with the smallest position.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static short min(short ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    short min = array[0];
    for (int i = 1; i < array.length; ++i) {
      short element = array[i];
      if (element < min) {
        min = element;
      }
    }
    return min;
  }


  /**
   * Returns the minimum of two <code>Short</code> objects.
   *
   * Assume that null is the minimum value of <code>Short</code> objects.
   *
   * @param value1
   *          a <code>Short</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Short</code> object, could be <code>null</code>.
   * @return the minimum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the smallest position. Note that the
   *         returned value is either <code>null</code> or a reference to one of
   *         the arguments.
   */
  public static Short min(Short value1, Short value2) {
    if ((value1 == null) || (value2 == null)) {
      return null;
    } else {
      short v1 = value1.shortValue();
      short v2 = value2.shortValue();
      return (v1 <= v2 ? value1 : value2);
    }
  }

  /**
   * Returns the minimum of three <code>Short</code> objects.
   *
   * Assume that null is the minimum value of <code>Short</code> objects.
   *
   * @param value1
   *          a <code>Short</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Short</code> object, could be <code>null</code>.
   * @param value3
   *          a <code>Short</code> object, could be <code>null</code>.
   * @return the minimum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         smallest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of the arguments.
   */
  public static Short min(Short value1, Short value2, Short value3) {
    if ((value1 == null) || (value2 == null) || (value3 == null)) {
      return null;
    }
    short v1 = value1.shortValue();
    short v2 = value2.shortValue();
    short v3 = value3.shortValue();
    if (v1 <= v2) {
      if (v1 <= v3) {
        return value1;
      } else {
        return value3;
      }
    } else if (v2 <= v3) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum object in a <code>Short</code> array.
   *
   * Assume that null is the minimum value of <code>Short</code> objects.
   *
   * @param array
   *          a <code>Short</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the minimum object in the array. In case of ties, returns the one
   *         with the smallest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>array</code>.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static Short min(Short ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    Short min = array[0];
    if (min == null) {
      return null;
    }
    short minValue = min.shortValue();
    for (int i = 0; i < array.length; ++i) {
      Short element = array[i];
      if (element == null) {
        return null;
      } else {
        short value = element.shortValue();
        if (value < minValue) {
          minValue = value;
          min = element;
        }
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two <code>int</code> values.
   *
   * @param value1
   *          a <code>int</code> value;
   * @param value2
   *          a <code>int</code> value;
   * @return the minimum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the smallest position.
   */
  public static int min(int value1, int value2) {
    return (value1 <= value2 ? value1 : value2);
  }

  /**
   * Returns the minimum of three <code>int</code> values.
   *
   * @param value1
   *          a <code>int</code> value;
   * @param value2
   *          a <code>int</code> value;
   * @param value3
   *          a <code>int</code> value;
   * @return the minimum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         smallest position.
   */
  public static int min(int value1, int value2, int value3) {
    if (value1 <= value2) {
      if (value1 <= value3) {
        return value1;
      } else {
        return value3;
      }
    } else if (value2 <= value3) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum value in a <code>int</code> array.
   *
   * @param array
   *          a <code>int</code> array, must not be <code>null</code>
   *          nor empty.
   * @return the minimum value in the array. In case of ties, returns the
   *         one with the smallest position.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static int min(int ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    int min = array[0];
    for (int i = 1; i < array.length; ++i) {
      int element = array[i];
      if (element < min) {
        min = element;
      }
    }
    return min;
  }


  /**
   * Returns the minimum of two <code>Integer</code> objects.
   *
   * Assume that null is the minimum value of <code>Integer</code> objects.
   *
   * @param value1
   *          a <code>Integer</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Integer</code> object, could be <code>null</code>.
   * @return the minimum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the smallest position. Note that the
   *         returned value is either <code>null</code> or a reference to one of
   *         the arguments.
   */
  public static Integer min(Integer value1, Integer value2) {
    if ((value1 == null) || (value2 == null)) {
      return null;
    } else {
      int v1 = value1.intValue();
      int v2 = value2.intValue();
      return (v1 <= v2 ? value1 : value2);
    }
  }

  /**
   * Returns the minimum of three <code>Integer</code> objects.
   *
   * Assume that null is the minimum value of <code>Integer</code> objects.
   *
   * @param value1
   *          a <code>Integer</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Integer</code> object, could be <code>null</code>.
   * @param value3
   *          a <code>Integer</code> object, could be <code>null</code>.
   * @return the minimum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         smallest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of the arguments.
   */
  public static Integer min(Integer value1, Integer value2, Integer value3) {
    if ((value1 == null) || (value2 == null) || (value3 == null)) {
      return null;
    }
    int v1 = value1.intValue();
    int v2 = value2.intValue();
    int v3 = value3.intValue();
    if (v1 <= v2) {
      if (v1 <= v3) {
        return value1;
      } else {
        return value3;
      }
    } else if (v2 <= v3) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum object in a <code>Integer</code> array.
   *
   * Assume that null is the minimum value of <code>Integer</code> objects.
   *
   * @param array
   *          a <code>Integer</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the minimum object in the array. In case of ties, returns the one
   *         with the smallest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>array</code>.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static Integer min(Integer ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    Integer min = array[0];
    if (min == null) {
      return null;
    }
    int minValue = min.intValue();
    for (int i = 0; i < array.length; ++i) {
      Integer element = array[i];
      if (element == null) {
        return null;
      } else {
        int value = element.intValue();
        if (value < minValue) {
          minValue = value;
          min = element;
        }
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two <code>long</code> values.
   *
   * @param value1
   *          a <code>long</code> value;
   * @param value2
   *          a <code>long</code> value;
   * @return the minimum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the smallest position.
   */
  public static long min(long value1, long value2) {
    return (value1 <= value2 ? value1 : value2);
  }

  /**
   * Returns the minimum of three <code>long</code> values.
   *
   * @param value1
   *          a <code>long</code> value;
   * @param value2
   *          a <code>long</code> value;
   * @param value3
   *          a <code>long</code> value;
   * @return the minimum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         smallest position.
   */
  public static long min(long value1, long value2, long value3) {
    if (value1 <= value2) {
      if (value1 <= value3) {
        return value1;
      } else {
        return value3;
      }
    } else if (value2 <= value3) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum value in a <code>long</code> array.
   *
   * @param array
   *          a <code>long</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the minimum value in the array. In case of ties, returns the one
   *         with the smallest position.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static long min(long ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    long min = array[0];
    for (int i = 1; i < array.length; ++i) {
      long element = array[i];
      if (element < min) {
        min = element;
      }
    }
    return min;
  }


  /**
   * Returns the minimum of two <code>Long</code> objects.
   *
   * Assume that null is the minimum value of <code>Long</code> objects.
   *
   * @param value1
   *          a <code>Long</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Long</code> object, could be <code>null</code>.
   * @return the minimum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the smallest position. Note that the
   *         returned value is either <code>null</code> or a reference to one of
   *         the arguments.
   */
  public static Long min(Long value1, Long value2) {
    if ((value1 == null) || (value2 == null)) {
      return null;
    } else {
      long v1 = value1.longValue();
      long v2 = value2.longValue();
      return (v1 <= v2 ? value1 : value2);
    }
  }

  /**
   * Returns the minimum of three <code>Long</code> objects.
   *
   * Assume that null is the minimum value of <code>Long</code> objects.
   *
   * @param value1
   *          a <code>Long</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Long</code> object, could be <code>null</code>.
   * @param value3
   *          a <code>Long</code> object, could be <code>null</code>.
   * @return the minimum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         smallest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of the arguments.
   */
  public static Long min(Long value1, Long value2, Long value3) {
    if ((value1 == null) || (value2 == null) || (value3 == null)) {
      return null;
    }
    long v1 = value1.longValue();
    long v2 = value2.longValue();
    long v3 = value3.longValue();
    if (v1 <= v2) {
      if (v1 <= v3) {
        return value1;
      } else {
        return value3;
      }
    } else if (v2 <= v3) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum object in a <code>Long</code> array.
   *
   * Assume that null is the minimum value of <code>Long</code> objects.
   *
   * @param array
   *          a <code>Long</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the minimum object in the array. In case of ties, returns the one
   *         with the smallest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>array</code>.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static Long min(Long ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    Long min = array[0];
    if (min == null) {
      return null;
    }
    long minValue = min.longValue();
    for (int i = 0; i < array.length; ++i) {
      Long element = array[i];
      if (element == null) {
        return null;
      } else {
        long value = element.longValue();
        if (value < minValue) {
          minValue = value;
          min = element;
        }
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two <code>float</code> values.
   *
   * @param value1
   *          a <code>float</code> value;
   * @param value2
   *          a <code>float</code> value;
   * @return the minimum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the smallest position.
   */
  public static float min(float value1, float value2) {
    if (Comparison.compare(value1, value2) <= 0) {
      return value1;
    } else {
      return value2;
    }
  }

  /**
   * Returns the minimum of three <code>float</code> values.
   *
   * @param value1
   *          a <code>float</code> value;
   * @param value2
   *          a <code>float</code> value;
   * @param value3
   *          a <code>float</code> value;
   * @return the minimum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         smallest position.
   */
  public static float min(float value1, float value2, float value3) {
    if (Comparison.compare(value1, value2) <= 0) {
      if (Comparison.compare(value1, value3) <= 0) {
        return value1;
      } else {
        return value3;
      }
    } else if (Comparison.compare(value2, value3) <= 0) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum value in a <code>float</code> array.
   *
   * @param array
   *          a <code>float</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the minimum value in the array. In case of ties, returns the one
   *         with the smallest position.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static float min(float ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    float min = array[0];
    for (int i = 1; i < array.length; ++i) {
      float element = array[i];
      if (Comparison.compare(element, min) < 0) {
        min = element;
      }
    }
    return min;
  }


  /**
   * Returns the minimum of two <code>Float</code> objects.
   *
   * Assume that null is the minimum value of <code>Float</code> objects.
   *
   * @param value1
   *          a <code>Float</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Float</code> object, could be <code>null</code>.
   * @return the minimum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the smallest position. Note that the
   *         returned value is either <code>null</code> or a reference to one of
   *         the arguments.
   */
  public static Float min(Float value1, Float value2) {
    if ((value1 == null) || (value2 == null)) {
      return null;
    }
    float v1 = value1.floatValue();
    float v2 = value2.floatValue();
    if (Comparison.compare(v1, v2) <= 0) {
      return value1;
    } else {
      return value2;
    }
  }

  /**
   * Returns the minimum of three <code>Float</code> objects.
   *
   * Assume that null is the minimum value of <code>Float</code> objects.
   *
   * @param value1
   *          a <code>Float</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Float</code> object, could be <code>null</code>.
   * @param value3
   *          a <code>Float</code> object, could be <code>null</code>.
   * @return the minimum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         smallest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of the arguments.
   */
  public static Float min(Float value1, Float value2, Float value3) {
    if ((value1 == null) || (value2 == null) || (value3 == null)) {
      return null;
    }
    float v1 = value1.floatValue();
    float v2 = value2.floatValue();
    float v3 = value3.floatValue();
    if (Comparison.compare(v1, v2) <= 0) {
      if (Comparison.compare(v1, v3) <= 0) {
        return value1;
      } else {
        return value3;
      }
    } else if (Comparison.compare(v2, v3) <= 0) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum object in a <code>Float</code> array.
   *
   * Assume that null is the minimum value of <code>Float</code> objects.
   *
   * @param array
   *          a <code>Float</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the minimum object in the array. In case of ties, returns the one
   *         with the smallest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>array</code>.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static Float min(Float ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    Float min = array[0];
    if (min == null) {
      return null;
    }
    float minValue = min.floatValue();
    for (int i = 0; i < array.length; ++i) {
      Float element = array[i];
      if (element == null) {
        return null;
      } else {
        float value = element.floatValue();
        if (Comparison.compare(value, minValue) < 0) {
          minValue = value;
          min = element;
        }
      }
    }
    return min;
  }


  /**
   * Returns the minimum of two <code>double</code> values.
   *
   * @param value1
   *          a <code>double</code> value;
   * @param value2
   *          a <code>double</code> value;
   * @return the minimum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the smallest position.
   */
  public static double min(double value1, double value2) {
    if (Comparison.compare(value1, value2) <= 0) {
      return value1;
    } else {
      return value2;
    }
  }

  /**
   * Returns the minimum of three <code>double</code> values.
   *
   * @param value1
   *          a <code>double</code> value;
   * @param value2
   *          a <code>double</code> value;
   * @param value3
   *          a <code>double</code> value;
   * @return the minimum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         smallest position.
   */
  public static double min(double value1, double value2, double value3) {
    if (Comparison.compare(value1, value2) <= 0) {
      if (Comparison.compare(value1, value3) <= 0) {
        return value1;
      } else {
        return value3;
      }
    } else if (Comparison.compare(value2, value3) <= 0) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum value in a <code>double</code> array.
   *
   * @param array
   *          a <code>double</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the minimum value in the array. In case of ties, returns the one
   *         with the smallest position.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static double min(double ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    double min = array[0];
    for (int i = 1; i < array.length; ++i) {
      double element = array[i];
      if (Comparison.compare(element, min) < 0) {
        min = element;
      }
    }
    return min;
  }


  /**
   * Returns the minimum of two <code>Double</code> objects.
   *
   * Assume that null is the minimum value of <code>Double</code> objects.
   *
   * @param value1
   *          a <code>Double</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Double</code> object, could be <code>null</code>.
   * @return the minimum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the smallest position. Note that the
   *         returned value is either <code>null</code> or a reference to one of
   *         the arguments.
   */
  public static Double min(Double value1, Double value2) {
    if ((value1 == null) || (value2 == null)) {
      return null;
    }
    double v1 = value1.doubleValue();
    double v2 = value2.doubleValue();
    if (Comparison.compare(v1, v2) <= 0) {
      return value1;
    } else {
      return value2;
    }
  }

  /**
   * Returns the minimum of three <code>Double</code> objects.
   *
   * Assume that null is the minimum value of <code>Double</code> objects.
   *
   * @param value1
   *          a <code>Double</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Double</code> object, could be <code>null</code>.
   * @param value3
   *          a <code>Double</code> object, could be <code>null</code>.
   * @return the minimum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         smallest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of the arguments.
   */
  public static Double min(Double value1, Double value2, Double value3) {
    if ((value1 == null) || (value2 == null) || (value3 == null)) {
      return null;
    }
    double v1 = value1.doubleValue();
    double v2 = value2.doubleValue();
    double v3 = value3.doubleValue();
    if (Comparison.compare(v1, v2) <= 0) {
      if (Comparison.compare(v1, v3) <= 0) {
        return value1;
      } else {
        return value3;
      }
    } else if (Comparison.compare(v2, v3) <= 0) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum object in a <code>Double</code> array.
   *
   * Assume that null is the minimum value of <code>Double</code> objects.
   *
   * @param array
   *          a <code>Double</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the minimum object in the array. In case of ties, returns the one
   *         with the smallest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>array</code>.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static Double min(Double ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    Double min = array[0];
    if (min == null) {
      return null;
    }
    double minValue = min.doubleValue();
    for (int i = 0; i < array.length; ++i) {
      Double element = array[i];
      if (element == null) {
        return null;
      } else {
        double value = element.doubleValue();
        if (Comparison.compare(value, minValue) < 0) {
          minValue = value;
          min = element;
        }
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two <code>String</code> objects.
   *
   * Assume that <code>null</code> is the minimum value of
   * <code>String</code> objects.
   *
   * @param value1
   *          a <code>String</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>String</code> object, could be <code>null</code>.
   * @return the minimum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the smallest position.
   */
  public static String min(String value1, String value2) {
    if ((value1 == null) || (value2 == null)) {
      return null;
    } else if (value1.compareTo(value2) <= 0) {
      return value1;
    } else {
      return value2;
    }
  }

  /**
   * Returns the minimum of three <code>String</code> objects.
   *
   * Assume that <code>null</code> is the minimum value of
   *  <code>String</code> objects.
   *
   * @param value1
   *          a <code>String</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>String</code> object, could be <code>null</code>.
   * @param value3
   *          a <code>String</code> object, could be <code>null</code>.
   * @return the minimum of <code>value1</code>, <code>value2</code>
   *         and <code>value3</code>. In case of ties, returns the
   *         one with the smallest position.
   */
  public static String min(String value1, String value2, String value3) {
    if ((value1 == null) || (value2 == null) || (value3 == null)) {
      return null;
    }
    if ((value1 == value2) || (value1.compareTo(value2) <= 0)) {
      if ((value1 == value3) || (value1.compareTo(value3) <= 0)) {
        return value1;
      } else {
        return value3;
      }
    } else if ((value2 == value3) || (value2.compareTo(value3) <= 0)) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum object in a <code>String</code> array.
   *
   * Assume that null is the minimum value of <code>String</code> objects.
   *
   * @param array
   *          a <code>String</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the minimum object in the array. In case of ties, returns the one
   *         with the smallest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>array</code>.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static String min(String ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    String min = array[0];
    if (min == null) {
      return null;
    }
    for (int i = 0; i < array.length; ++i) {
      String element = array[i];
      if (element == null) {
        return null;
      } else if ((element != min) && (element.compareTo(min) < 0)) {
        min = element;
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two <code>Comparable</code> objects.
   *
   * Assume that <code>null</code> is the minimum value.
   *
   * @param T
   *          any object type. Note that it could be type of arrays or
   *          multi-dimensional arrays.
   * @param value1
   *          an object of class <code>T</code>, could be <code>null</code>.
   * @param value2
   *          an object of class <code>T</code>, could be <code>null</code>.
   * @return the minimum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the smallest position.
   */
  public static <T> T min(T value1, T value2) {
    if (Comparison.compare(value1, value2) <= 0) {
      return value1;
    } else {
      return value2;
    }
  }

  /**
   * Returns the minimum of three <code>Comparable</code> objects.
   *
   * Assume that <code>null</code> is the minimum value.
   *
   * @param T
   *          any object type. Note that it could be type of arrays or
   *          multi-dimensional arrays.
   * @param value1
   *          a object of class <code>T</code>, could be <code>null</code>.
   * @param value2
   *          a object of class <code>T</code>, could be <code>null</code>.
   * @param value3
   *          a object of class <code>T</code>, could be <code>null</code>.
   * @return the minimum of <code>value1</code>, <code>value2</code>
   *         and <code>value3</code>. In case of ties, returns the
   *         one with the smallest position.
   */
  public static <T> T min(T value1, T value2, T value3) {
    if (Comparison.compare(value1, value2) <= 0) {
      if (Comparison.compare(value1, value3) <= 0) {
        return value1;
      } else {
        return value3;
      }
    } else if (Comparison.compare(value2, value3) <= 0) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum object in a <code>Comparable</code> array.
   *
   * Assume that null is the minimum value .
   *
   * @param T
   *          any object type. Note that it could be type of arrays or
   *          multi-dimensional arrays.
   * @param array
   *          a array of class <code>T</code>, must not be <code>null</code> nor
   *          empty.
   * @return the minimum object in the array. In case of ties, returns the one
   *         with the smallest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>array</code>.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static <T> T min(T ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    T min = array[0];
    if (min == null) {
      return null;
    }
    for (int i = 0; i < array.length; ++i) {
      T element = array[i];
      if (element == null) {
        return null;
      } else if (Comparison.compare(element, min) < 0) {
        min = element;
      }
    }
    return min;
  }

  /**
   * Returns the minimum object in a <code>Comparable</code> <code>Iterable</code>
   * object.
   *
   * Assume that null is the minimum value.
   *
   *@param T
   *          any object type. Note that it could be type of arrays or
   *          multi-dimensional arrays.
   * @param iterable
   *          an <code>Iterable</code> of values of class <code>T</code>, must not be
   *          <code>null</code> nor empty.
   * @return the minimum object in the list.In case of ties, returns the one
   *         with the smallest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>iterable</code>.
   * @throws NullArgumentException
   *           if <code>iterable</code> is <code>null</code> or empty.
   */
  public static <T> T min(Iterable<T> iterable) {
    if (iterable == null) {
      throw new NullArgumentException();
    }
    Iterator<T> iter = iterable.iterator();
    if (! iter.hasNext()) {
      throw new NullArgumentException();
    }
    T min = iter.next();
    if (min == null) {
      return null;
    }
    while (iter.hasNext()) {
      T element = iter.next();
      if (element == null) {
        return null;
      } else if (Comparison.compare(element, min) < 0) {
        min = element;
      }
    }
    return min;
  }


  /**
   * Returns the maximum of two <code>boolean</code> values.
   *
   * Assume <code>true > false</code> for <code>boolean</code> values.
   *
   * @param value1
   *          a <code>boolean</code> value;
   * @param value2
   *          a <code>boolean</code> value;
   * @return the maximum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the largest position.
   */
  public static boolean max(boolean value1, boolean value2) {
    if (value2) {
      return true;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum of three <code>boolean</code> values.
   *
   * Assume <code>true > false</code> for <code>boolean</code> values.
   *
   * @param value1
   *          a <code>boolean</code> value;
   * @param value2
   *          a <code>boolean</code> value;
   * @param value3
   *          a <code>boolean</code> value;
   * @return the maximum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         largest position.
   */
  public static boolean max(boolean value1, boolean value2, boolean value3) {
    if (value3 || value2) {
      return true;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum value in a <code>boolean</code> array.
   *
   * Assume <code>true > false</code> for <code>boolean</code> values.
   *
   * @param array
   *          a <code>boolean</code> array, must not be <code>null</code>
   *          nor empty.
   * @return the maximum value in the array. In case of ties, returns the
   *         one with the largest position.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static boolean max(boolean... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    for (boolean element : array) {
      if (element) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the maximum of two <code>Boolean</code> objects.
   *
   * Assume <code>Boolean.TRUE > Boolean.FALSE > null</code> for
   * <code>Boolean</code> objects.
   *
   * @param value1
   *          a <code>Boolean</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Boolean</code> object, could be <code>null</code>.
   * @return the maximum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the largest position. Note that the
   *         returned value is either <code>null</code> or a reference to one of
   *         the arguments.
   */
  public static Boolean max(Boolean value1, Boolean value2) {
    if (value1 == null) {
      return value2;
    } else if (value2 == null) {
      return value1;
    } else {    // value1 != null && value2 != null
      boolean v1 = value1.booleanValue();
      boolean v2 = value2.booleanValue();
      if (v2 || (v2 == v1)) {  // v2 >= v1
        return value2;
      } else {                 // v2 < v1
        return value1;
      }
    }
  }

  /**
   * Returns the maximum of three <code>Boolean</code> objects.
   *
   * Assume <code>Boolean.TRUE > Boolean.FALSE > null</code> for
   * <code>Boolean</code> objects.
   *
   * @param value1
   *          a <code>Boolean</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Boolean</code> object, could be <code>null</code>.
   * @param value3
   *          a <code>Boolean</code> object, could be <code>null</code>.
   * @return the maximum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         largest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of the arguments.
   */
  public static Boolean max(Boolean value1, Boolean value2, Boolean value3) {
    if (value1 == null) {
      // returns the maximum of value2 and value3
      return max(value2, value3);
    } else if (value2 == null) {
      // returns the maximum of value1 and value3
      return max(value1, value3);
    } else if (value3 == null) {
      // returns the maximum of value1 and value2
      return max(value1, value2);
    } else {
      // returns the maximum of non-null value1, value2 and value3
      if (value3.booleanValue()) {
        return value3;
      }
      if (value2.booleanValue()) {
        return value2;
      }
      if (value1.booleanValue()) {
        return value1;
      }
      // value1 == value2 == value3 == false
      return value3;
    }
  }

  /**
   * Returns the maximum object in a <code>Boolean</code> array.
   *
   * Assume that <code>Boolean.TRUE > Boolean.FALSE > null</code> for
   * <code>Boolean</code> objects.
   *
   * @param array
   *          a <code>Boolean</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the maximum object in the array. In case of ties, returns the one
   *         with the largest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>array</code>.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static Boolean max(Boolean ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // find the last non-null element in array
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // all elements in array are null
      return null;
    }
    Boolean max = array[i];
    if (max.booleanValue()) {
      return max;
    }
    // now max.booleanValue() == false
    for (--i; i >= 0; --i) {
      Boolean element = array[i];
      if ((element != null) && element.booleanValue()) {
        return element;
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two <code>char</code> values.
   *
   * @param value1
   *          a <code>char</code> value;
   * @param value2
   *          a <code>char</code> value;
   * @return the maximum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the largest position.
   */
  public static char max(char value1, char value2) {
    return (value1 <= value2 ? value2 : value1);
  }

  /**
   * Returns the maximum of three <code>char</code> values.
   *
   * @param value1
   *          a <code>char</code> value;
   * @param value2
   *          a <code>char</code> value;
   * @param value3
   *          a <code>char</code> value;
   * @return the maximum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         largest position.
   */
  public static char max(char value1, char value2, char value3) {
    if (value3 >= value2) {
      if (value3 >= value1) {
        return value3;
      } else {
        return value1;
      }
    } else if (value2 >= value1) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum value in a <code>char</code> array.
   *
   * @param array
   *          a <code>char</code> array, must not be <code>null</code>
   *          nor empty.
   * @return the maximum value in the array. In case of ties, returns the
   *         one with the largest position.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static char max(char ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    int i = array.length - 1;
    char max = array[i];
    for (--i; i >= 0; --i) {
      char element = array[i];
      if (element > max) {
        max = element;
      }
    }
    return max;
  }


  /**
   * Returns the maximum of two <code>Character</code> objects.
   *
   * Assume that null is the minimum value of <code>Character</code> objects.
   *
   * @param value1
   *          a <code>Character</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Character</code> object, could be <code>null</code>.
   * @return the maximum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the largest position. Note that the
   *         returned value is either <code>null</code> or a reference to one of
   *         the arguments.
   */
  public static Character max(Character value1, Character value2) {
    if (value1 == null) {
      return value2;
    } else if (value2 == null) {
      return value1;
    } else {
      char v1 = value1.charValue();
      char v2 = value2.charValue();
      return (v1 <= v2 ? value2 : value1);
    }
  }

  /**
   * Returns the maximum of three <code>Character</code> objects.
   *
   * Assume that null is the minimum value of <code>Character</code> objects.
   *
   * @param value1
   *          a <code>Character</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Character</code> object, could be <code>null</code>.
   * @param value3
   *          a <code>Character</code> object, could be <code>null</code>.
   * @return the maximum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         largest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of the arguments.
   */
  public static Character max(Character value1, Character value2, Character value3) {
    if (value1 == null) {
      return max(value2, value3);
    } else if (value2 == null) {
      return max(value1, value3);
    } else if (value3 == null) {
      return max(value1, value2);
    } else {
      char v1 = value1.charValue();
      char v2 = value2.charValue();
      char v3 = value3.charValue();
      if (v3 >= v2) {
        if (v3 >= v1) {
          return value3;
        } else {
          return value1;
        }
      } else if (v2 >= v1) {
        return value2;
      } else {
        return value1;
      }
    }
  }

  /**
   * Returns the maximum object in a <code>Character</code> array.
   *
   * Assume that null is the minimum value of <code>Character</code> objects.
   *
   * @param array
   *          a <code>Character</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the maximum object in the array. In case of ties, returns the one
   *         with the largest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>array</code>.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static Character max(Character ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // find the last non-null element
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // all elements in array are null
      return null;
    }
    Character max = array[i];
    char maxValue = max.charValue();
    // test the rest elements
    for (--i; i >= 0; --i) {
      Character element = array[i];
      if (element != null) {
        char value = element.charValue();
        if (value > maxValue) {
          maxValue = value;
          max = element;
        }
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two <code>byte</code> values.
   *
   * @param value1
   *          a <code>byte</code> value;
   * @param value2
   *          a <code>byte</code> value;
   * @return the maximum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the largest position.
   */
  public static byte max(byte value1, byte value2) {
    return (value1 <= value2 ? value2 : value1);
  }

  /**
   * Returns the maximum of three <code>byte</code> values.
   *
   * @param value1
   *          a <code>byte</code> value;
   * @param value2
   *          a <code>byte</code> value;
   * @param value3
   *          a <code>byte</code> value;
   * @return the maximum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         largest position.
   */
  public static byte max(byte value1, byte value2, byte value3) {
    if (value3 >= value2) {
      if (value3 >= value1) {
        return value3;
      } else {
        return value1;
      }
    } else if (value2 >= value1) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum value in a <code>byte</code> array.
   *
   * @param array
   *          a <code>byte</code> array, must not be <code>null</code>
   *          nor empty.
   * @return the maximum value in the array. In case of ties, returns the
   *         one with the largest position.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static byte max(byte ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    int i = array.length - 1;
    byte max = array[i];
    for (--i; i >= 0; --i) {
      byte element = array[i];
      if (element > max) {
        max = element;
      }
    }
    return max;
  }


  /**
   * Returns the maximum of two <code>Byte</code> objects.
   *
   * Assume that null is the maximum value of <code>Byte</code> objects.
   *
   * @param value1
   *          a <code>Byte</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Byte</code> object, could be <code>null</code>.
   * @return the maximum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the largest position. Note that the
   *         returned value is either <code>null</code> or a reference to one of
   *         the arguments.
   */
  public static Byte max(Byte value1, Byte value2) {
    if (value1 == null) {
      return value2;
    } else if (value2 == null) {
      return value1;
    } else {
      byte v1 = value1.byteValue();
      byte v2 = value2.byteValue();
      return (v1 <= v2 ? value2 : value1);
    }
  }

  /**
   * Returns the maximum of three <code>Byte</code> objects.
   *
   * Assume that null is the maximum value of <code>Byte</code> objects.
   *
   * @param value1
   *          a <code>Byte</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Byte</code> object, could be <code>null</code>.
   * @param value3
   *          a <code>Byte</code> object;
   * @return the maximum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         largest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of the arguments.
   */
  public static Byte max(Byte value1, Byte value2, Byte value3) {
    if (value1 == null) {
      return max(value2, value3);
    } else if (value2 == null) {
      return max(value1, value3);
    } else if (value3 == null) {
      return max(value1, value2);
    } else {
      byte v1 = value1.byteValue();
      byte v2 = value2.byteValue();
      byte v3 = value3.byteValue();
      if (v3 >= v2) {
        if (v3 >= v1) {
          return value3;
        } else {
          return value1;
        }
      } else if (v2 >= v1) {
        return value2;
      } else {
        return value1;
      }
    }
  }

  /**
   * Returns the maximum object in a <code>Byte</code> array.
   *
   * Assume that null is the maximum value of <code>Byte</code> objects.
   *
   * @param array
   *          a <code>Byte</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the maximum object in the array. In case of ties, returns the one
   *         with the largest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>array</code>.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static Byte max(Byte ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // find the last non-null element
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // all elements in array are null
      return null;
    }
    Byte max = array[i];
    byte maxValue = max.byteValue();
    // test the rest elements
    for (--i; i >= 0; --i) {
      Byte element = array[i];
      if (element != null) {
        byte value = element.byteValue();
        if (value > maxValue) {
          maxValue = value;
          max = element;
        }
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two <code>short</code> values.
   *
   * @param value1
   *          a <code>short</code> value;
   * @param value2
   *          a <code>short</code> value;
   * @return the maximum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the largest position.
   */
  public static short max(short value1, short value2) {
    return (value1 <= value2 ? value2 : value1);
  }

  /**
   * Returns the maximum of three <code>short</code> values.
   *
   * @param value1
   *          a <code>short</code> value;
   * @param value2
   *          a <code>short</code> value;
   * @param value3
   *          a <code>short</code> value;
   * @return the maximum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         largest position.
   */
  public static short max(short value1, short value2, short value3) {
    if (value3 >= value2) {
      if (value3 >= value1) {
        return value3;
      } else {
        return value1;
      }
    } else if (value2 >= value1) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum value in a <code>short</code> array.
   *
   * @param array
   *          a <code>short</code> array, must not be <code>null</code>
   *          nor empty.
   * @return the maximum value in the array. In case of ties, returns the
   *         one with the largest position.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static short max(short ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    int i = array.length - 1;
    short max = array[i];
    for (--i; i >= 0; --i) {
      short element = array[i];
      if (element > max) {
        max = element;
      }
    }
    return max;
  }


  /**
   * Returns the maximum of two <code>Short</code> objects.
   *
   * Assume that null is the maximum value of <code>Short</code> objects.
   *
   * @param value1
   *          a <code>Short</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Short</code> object, could be <code>null</code>.
   * @return the maximum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the largest position. Note that the
   *         returned value is either <code>null</code> or a reference to one of
   *         the arguments.
   */
  public static Short max(Short value1, Short value2) {
    if (value1 == null) {
      return value2;
    } else if (value2 == null) {
      return value1;
    } else {
      short v1 = value1.shortValue();
      short v2 = value2.shortValue();
      return (v1 <= v2 ? value2 : value1);
    }
  }

  /**
   * Returns the maximum of three <code>Short</code> objects.
   *
   * Assume that null is the maximum value of <code>Short</code> objects.
   *
   * @param value1
   *          a <code>Short</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Short</code> object, could be <code>null</code>.
   * @param value3
   *          a <code>Short</code> object, could be <code>null</code>.
   * @return the maximum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         largest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of the arguments.
   */
  public static Short max(Short value1, Short value2, Short value3) {
    if (value1 == null) {
      return max(value2, value3);
    } else if (value2 == null) {
      return max(value1, value3);
    } else if (value3 == null) {
      return max(value1, value2);
    } else {
      short v1 = value1.shortValue();
      short v2 = value2.shortValue();
      short v3 = value3.shortValue();
      if (v3 >= v2) {
        if (v3 >= v1) {
          return value3;
        } else {
          return value1;
        }
      } else if (v2 >= v1) {
        return value2;
      } else {
        return value1;
      }
    }
  }

  /**
   * Returns the maximum object in a <code>Short</code> array.
   *
   * Assume that null is the maximum value of <code>Short</code> objects.
   *
   * @param array
   *          a <code>Short</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the maximum object in the array. In case of ties, returns the one
   *         with the largest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>array</code>.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static Short max(Short ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // find the last non-null element
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // all elements in array are null
      return null;
    }
    Short max = array[i];
    short maxValue = max.shortValue();
    // test the rest elements
    for (--i; i >= 0; --i) {
      Short element = array[i];
      if (element != null) {
        short value = element.shortValue();
        if (value > maxValue) {
          maxValue = value;
          max = element;
        }
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two <code>int</code> values.
   *
   * @param value1
   *          a <code>int</code> value;
   * @param value2
   *          a <code>int</code> value;
   * @return the maximum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the largest position.
   */
  public static int max(int value1, int value2) {
    return (value1 <= value2 ? value2 : value1);
  }

  /**
   * Returns the maximum of three <code>int</code> values.
   *
   * @param value1
   *          a <code>int</code> value;
   * @param value2
   *          a <code>int</code> value;
   * @param value3
   *          a <code>int</code> value;
   * @return the maximum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         largest position.
   */
  public static int max(int value1, int value2, int value3) {
    if (value3 >= value2) {
      if (value3 >= value1) {
        return value3;
      } else {
        return value1;
      }
    } else if (value2 >= value1) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum value in a <code>int</code> array.
   *
   * @param array
   *          a <code>int</code> array, must not be <code>null</code>
   *          nor empty.
   * @return the maximum value in the array. In case of ties, returns the
   *         one with the largest position.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static int max(int ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    int i = array.length - 1;
    int max = array[i];
    for (--i; i >= 0; --i) {
      int element = array[i];
      if (element > max) {
        max = element;
      }
    }
    return max;
  }


  /**
   * Returns the maximum of two <code>Integer</code> objects.
   *
   * Assume that null is the maximum value of <code>Integer</code> objects.
   *
   * @param value1
   *          a <code>Integer</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Integer</code> object, could be <code>null</code>.
   * @return the maximum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the largest position. Note that the
   *         returned value is either <code>null</code> or a reference to one of
   *         the arguments.
   */
  public static Integer max(Integer value1, Integer value2) {
    if (value1 == null) {
      return value2;
    } else if (value2 == null) {
      return value1;
    } else {
      int v1 = value1.intValue();
      int v2 = value2.intValue();
      return (v1 <= v2 ? value2 : value1);
    }
  }

  /**
   * Returns the maximum of three <code>Integer</code> objects.
   *
   * Assume that null is the maximum value of <code>Integer</code> objects.
   *
   * @param value1
   *          a <code>Integer</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Integer</code> object, could be <code>null</code>.
   * @param value3
   *          a <code>Integer</code> object, could be <code>null</code>.
   * @return the maximum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         largest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of the arguments.
   */
  public static Integer max(Integer value1, Integer value2, Integer value3) {
    if (value1 == null) {
      return max(value2, value3);
    } else if (value2 == null) {
      return max(value1, value3);
    } else if (value3 == null) {
      return max(value1, value2);
    } else {
      int v1 = value1.intValue();
      int v2 = value2.intValue();
      int v3 = value3.intValue();
      if (v3 >= v2) {
        if (v3 >= v1) {
          return value3;
        } else {
          return value1;
        }
      } else if (v2 >= v1) {
        return value2;
      } else {
        return value1;
      }
    }
  }

  /**
   * Returns the maximum object in a <code>Integer</code> array.
   *
   * Assume that null is the maximum value of <code>Integer</code> objects.
   *
   * @param array
   *          a <code>Integer</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the maximum object in the array. In case of ties, returns the one
   *         with the largest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>array</code>.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static Integer max(Integer ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // find the last non-null element
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // all elements in array are null
      return null;
    }
    Integer max = array[i];
    int maxValue = max.intValue();
    // test the rest elements
    for (--i; i >= 0; --i) {
      Integer element = array[i];
      if (element != null) {
        int value = element.intValue();
        if (value > maxValue) {
          maxValue = value;
          max = element;
        }
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two <code>long</code> values.
   *
   * @param value1
   *          a <code>long</code> value;
   * @param value2
   *          a <code>long</code> value;
   * @return the maximum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the largest position.
   */
  public static long max(long value1, long value2) {
    return (value1 <= value2 ? value2 : value1);
  }

  /**
   * Returns the maximum of three <code>long</code> values.
   *
   * @param value1
   *          a <code>long</code> value;
   * @param value2
   *          a <code>long</code> value;
   * @param value3
   *          a <code>long</code> value;
   * @return the maximum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         largest position.
   */
  public static long max(long value1, long value2, long value3) {
    if (value3 >= value2) {
      if (value3 >= value1) {
        return value3;
      } else {
        return value1;
      }
    } else if (value2 >= value1) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum value in a <code>long</code> array.
   *
   * @param array
   *          a <code>long</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the maximum value in the array. In case of ties, returns the one
   *         with the largest position.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static long max(long ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    int i = array.length - 1;
    long max = array[i];
    for (--i; i >= 0; --i) {
      long element = array[i];
      if (element > max) {
        max = element;
      }
    }
    return max;
  }


  /**
   * Returns the maximum of two <code>Long</code> objects.
   *
   * Assume that null is the maximum value of <code>Long</code> objects.
   *
   * @param value1
   *          a <code>Long</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Long</code> object, could be <code>null</code>.
   * @return the maximum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the largest position. Note that the
   *         returned value is either <code>null</code> or a reference to one of
   *         the arguments.
   */
  public static Long max(Long value1, Long value2) {
    if (value1 == null) {
      return value2;
    } else if (value2 == null) {
      return value1;
    } else {
      long v1 = value1.longValue();
      long v2 = value2.longValue();
      return (v1 <= v2 ? value2 : value1);
    }
  }

  /**
   * Returns the maximum of three <code>Long</code> objects.
   *
   * Assume that null is the maximum value of <code>Long</code> objects.
   *
   * @param value1
   *          a <code>Long</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Long</code> object, could be <code>null</code>.
   * @param value3
   *          a <code>Long</code> object, could be <code>null</code>.
   * @return the maximum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         largest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of the arguments.
   */
  public static Long max(Long value1, Long value2, Long value3) {
    if (value1 == null) {
      return max(value2, value3);
    } else if (value2 == null) {
      return max(value1, value3);
    } else if (value3 == null) {
      return max(value1, value2);
    } else {
      long v1 = value1.longValue();
      long v2 = value2.longValue();
      long v3 = value3.longValue();
      if (v3 >= v2) {
        if (v3 >= v1) {
          return value3;
        } else {
          return value1;
        }
      } else if (v2 >= v1) {
        return value2;
      } else {
        return value1;
      }
    }
  }

  /**
   * Returns the maximum object in a <code>Long</code> array.
   *
   * Assume that null is the maximum value of <code>Long</code> objects.
   *
   * @param array
   *          a <code>Long</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the maximum object in the array. In case of ties, returns the one
   *         with the largest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>array</code>.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static Long max(Long ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // find the last non-null element
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // all elements in array are null
      return null;
    }
    Long max = array[i];
    long maxValue = max.longValue();
    // test the rest elements
    for (--i; i >= 0; --i) {
      Long element = array[i];
      if (element != null) {
        long value = element.longValue();
        if (value > maxValue) {
          maxValue = value;
          max = element;
        }
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two <code>float</code> values.
   *
   * @param value1
   *          a <code>float</code> value;
   * @param value2
   *          a <code>float</code> value;
   * @return the maximum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the largest position.
   */
  public static float max(float value1, float value2) {
    if (Comparison.compare(value1, value2) <= 0) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum of three <code>float</code> values.
   *
   * @param value1
   *          a <code>float</code> value;
   * @param value2
   *          a <code>float</code> value;
   * @param value3
   *          a <code>float</code> value;
   * @return the maximum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         largest position.
   */
  public static float max(float value1, float value2, float value3) {
    if (Comparison.compare(value3, value2) >= 0) {
      if (Comparison.compare(value3, value1) >= 0) {
        return value3;
      } else {
        return value1;
      }
    } else if (Comparison.compare(value2, value1) >= 0) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum value in a <code>float</code> array.
   *
   * @param array
   *          a <code>float</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the maximum value in the array. In case of ties, returns the one
   *         with the largest position.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static float max(float ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    int i = array.length - 1;
    float max = array[i];
    for (--i; i >= 0; --i) {
      float element = array[i];
      if (Comparison.compare(element, max) > 0) {
        max = element;
      }
    }
    return max;
  }


  /**
   * Returns the maximum of two <code>Float</code> objects.
   *
   * Assume that null is the maximum value of <code>Float</code> objects.
   *
   * @param value1
   *          a <code>Float</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Float</code> object, could be <code>null</code>.
   * @return the maximum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the largest position. Note that the
   *         returned value is either <code>null</code> or a reference to one of
   *         the arguments.
   */
  public static Float max(Float value1, Float value2) {
    if (value1 == null) {
      return value2;
    } else if (value2 == null) {
      return value1;
    } else {
      float v1 = value1.floatValue();
      float v2 = value2.floatValue();
      return (Comparison.compare(v1, v2) <= 0 ? value2 : value1);
    }
  }

  /**
   * Returns the maximum of three <code>Float</code> objects.
   *
   * Assume that null is the maximum value of <code>Float</code> objects.
   *
   * @param value1
   *          a <code>Float</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Float</code> object, could be <code>null</code>.
   * @param value3
   *          a <code>Float</code> object, could be <code>null</code>.
   * @return the maximum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         largest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of the arguments.
   */
  public static Float max(Float value1, Float value2, Float value3) {
    if (value1 == null) {
      return max(value2, value3);
    } else if (value2 == null) {
      return max(value1, value3);
    } else if (value3 == null) {
      return max(value1, value2);
    } else {
      float v1 = value1.floatValue();
      float v2 = value2.floatValue();
      float v3 = value3.floatValue();
      if (Comparison.compare(v3, v2) >= 0) {
        if (Comparison.compare(v3, v1) >= 0) {
          return value3;
        } else {
          return value1;
        }
      } else if (Comparison.compare(v2, v1) >= 0) {
        return value2;
      } else {
        return value1;
      }
    }
  }

  /**
   * Returns the maximum object in a <code>Float</code> array.
   *
   * Assume that null is the maximum value of <code>Float</code> objects.
   *
   * @param array
   *          a <code>Float</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the maximum object in the array. In case of ties, returns the one
   *         with the largest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>array</code>.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static Float max(Float ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // find the last non-null element
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // all elements in array are null
      return null;
    }
    Float max = array[i];
    float maxValue = max.floatValue();
    // test the rest elements
    for (--i; i >= 0; --i) {
      Float element = array[i];
      if (element != null) {
        float value = element.floatValue();
        if (Comparison.compare(value, maxValue) > 0) {
          maxValue = value;
          max = element;
        }
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two <code>double</code> values.
   *
   * @param value1
   *          a <code>double</code> value;
   * @param value2
   *          a <code>double</code> value;
   * @return the maximum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the largest position.
   */
  public static double max(double value1, double value2) {
    if (Comparison.compare(value1, value2) <= 0) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum of three <code>double</code> values.
   *
   * @param value1
   *          a <code>double</code> value;
   * @param value2
   *          a <code>double</code> value;
   * @param value3
   *          a <code>double</code> value;
   * @return the maximum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         largest position.
   */
  public static double max(double value1, double value2, double value3) {
    if (Comparison.compare(value3, value2) >= 0) {
      if (Comparison.compare(value3, value1) >= 0) {
        return value3;
      } else {
        return value1;
      }
    } else if (Comparison.compare(value2, value1) >= 0) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum value in a <code>double</code> array.
   *
   * @param array
   *          a <code>double</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the maximum value in the array. In case of ties, returns the one
   *         with the largest position.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static double max(double ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    int i = array.length - 1;
    double max = array[i];
    for (--i; i >= 0; --i) {
      double element = array[i];
      if (Comparison.compare(element, max) > 0) {
        max = element;
      }
    }
    return max;
  }


  /**
   * Returns the maximum of two <code>Double</code> objects.
   *
   * Assume that null is the maximum value of <code>Double</code> objects.
   *
   * @param value1
   *          a <code>Double</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Double</code> object, could be <code>null</code>.
   * @return the maximum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the largest position. Note that the
   *         returned value is either <code>null</code> or a reference to one of
   *         the arguments.
   */
  public static Double max(Double value1, Double value2) {
    if (value1 == null) {
      return value2;
    } else if (value2 == null) {
      return value1;
    } else {
      double v1 = value1.doubleValue();
      double v2 = value2.doubleValue();
      return (Comparison.compare(v1, v2) <= 0 ? value2 : value1);
    }
  }

  /**
   * Returns the maximum of three <code>Double</code> objects.
   *
   * Assume that null is the maximum value of <code>Double</code> objects.
   *
   * @param value1
   *          a <code>Double</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>Double</code> object, could be <code>null</code>.
   * @param value3
   *          a <code>Double</code> object, could be <code>null</code>.
   * @return the maximum of <code>value1</code>, <code>value2</code> and
   *         <code>value3</code>. In case of ties, returns the one with the
   *         largest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of the arguments.
   */
  public static Double max(Double value1, Double value2, Double value3) {
    if (value1 == null) {
      return max(value2, value3);
    } else if (value2 == null) {
      return max(value1, value3);
    } else if (value3 == null) {
      return max(value1, value2);
    } else {
      double v1 = value1.doubleValue();
      double v2 = value2.doubleValue();
      double v3 = value3.doubleValue();
      if (Comparison.compare(v3, v2) >= 0) {
        if (Comparison.compare(v3, v1) >= 0) {
          return value3;
        } else {
          return value1;
        }
      } else if (Comparison.compare(v2, v1) >= 0) {
        return value2;
      } else {
        return value1;
      }
    }
  }

  /**
   * Returns the maximum object in a <code>Double</code> array.
   *
   * Assume that null is the maximum value of <code>Double</code> objects.
   *
   * @param array
   *          a <code>Double</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the maximum object in the array. In case of ties, returns the one
   *         with the largest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>array</code>.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static Double max(Double ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // find the last non-null element
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // all elements in array are null
      return null;
    }
    Double max = array[i];
    double maxValue = max.doubleValue();
    // test the rest elements
    for (--i; i >= 0; --i) {
      Double element = array[i];
      if (element != null) {
        double value = element.doubleValue();
        if (Comparison.compare(value, maxValue) > 0) {
          maxValue = value;
          max = element;
        }
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two <code>String</code> objects.
   *
   * Assume that <code>null</code> is the maximum value of
   * <code>String</code> objects.
   *
   * @param value1
   *          a <code>String</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>String</code> object, could be <code>null</code>.
   * @return the maximum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the largest position.
   */
  public static String max(String value1, String value2) {
    if (value1 == null) {
      return value2;
    } else if (value2 == null) {
      return value1;
    } else if (value1.compareTo(value2) <= 0) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum of three <code>String</code> objects.
   *
   * Assume that <code>null</code> is the maximum value of
   *  <code>String</code> objects.
   *
   * @param value1
   *          a <code>String</code> object, could be <code>null</code>.
   * @param value2
   *          a <code>String</code> object, could be <code>null</code>.
   * @param value3
   *          a <code>String</code> object, could be <code>null</code>.
   * @return the maximum of <code>value1</code>, <code>value2</code>
   *         and <code>value3</code>. In case of ties, returns the
   *         one with the largest position.
   */
  public static String max(String value1, String value2, String value3) {
    if (value1 == null) {
      return max(value2, value3);
    } else if (value2 == null) {
      return max(value1, value3);
    } else if (value3 == null) {
      return max(value1, value2);
    } else {
      if (value3.compareTo(value2) >= 0) {
        if (value3.compareTo(value1) >= 0) {
          return value3;
        } else {
          return value1;
        }
      } else if (value2.compareTo(value1) >= 0) {
        return value2;
      } else {
        return value1;
      }
    }
  }

  /**
   * Returns the maximum object in a <code>String</code> array.
   *
   * Assume that null is the maximum value of <code>String</code> objects.
   *
   * @param array
   *          a <code>String</code> array, must not be <code>null</code> nor
   *          empty.
   * @return the maximum object in the array. In case of ties, returns the one
   *         with the largest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>array</code>.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static String max(String ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // find the last non-null element
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // all elements in array are null
      return null;
    }
    String max = array[i];
    // test the rest elements
    for (--i; i >= 0; --i) {
      String element = array[i];
      if (element != null) {
        if (element.compareTo(max) > 0) {
          max = element;
        }
      }
    }
    return max;
  }


  /**
   * Returns the maximum of two <code>Comparable</code> objects.
   *
   * Assume that <code>null</code> is the maximum value.
   *
   * @param T
   *          any object type. Note that it could be type of arrays or
   *          multi-dimensional arrays.
   * @param value1
   *          an object of class <code>T</code>, could be <code>null</code>.
   * @param value2
   *          an object of class <code>T</code>, could be <code>null</code>.
   * @return the maximum of <code>value1</code> and <code>value2</code>. In case
   *         of ties, returns the one with the largest position.
   */
  public static <T> T max(T value1, T value2) {
    if (Comparison.compare(value1, value2) <= 0) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum of three <code>Comparable</code> objects.
   *
   * Assume that <code>null</code> is the maximum value.
   *
   * @param T
   *          any object type. Note that it could be type of arrays or
   *          multi-dimensional arrays.
   * @param value1
   *          a object of class <code>T</code>, could be <code>null</code>.
   * @param value2
   *          a object of class <code>T</code>, could be <code>null</code>.
   * @param value3
   *          a object of class <code>T</code>, could be <code>null</code>.
   * @return the maximum of <code>value1</code>, <code>value2</code>
   *         and <code>value3</code>. In case of ties, returns the
   *         one with the largest position.
   */
  public static <T> T max(T value1, T value2, T value3) {
    if (Comparison.compare(value3, value2) >= 0) {
      if (Comparison.compare(value3, value1) >= 0) {
        return value3;
      } else {
        return value1;
      }
    } else if (Comparison.compare(value2, value1) >= 0) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum object in a <code>Comparable</code> array.
   *
   * Assume that null is the maximum value .
   *
   * @param T
   *          any object type. Note that it could be type of arrays or
   *          multi-dimensional arrays.
   * @param array
   *          a array of class <code>T</code>, must not be <code>null</code> nor
   *          empty.
   * @return the maximum object in the array. In case of ties, returns the one
   *         with the largest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>array</code>.
   * @throws NullArgumentException
   *           if <code>array</code> is <code>null</code> or empty.
   */
  public static <T> T max(T ... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // find the last non-null element
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // all elements in array are null
      return null;
    }
    T max = array[i];
    // test the rest elements
    for (--i; i >= 0; --i) {
      T element = array[i];
      if (element != null) {
        if (Comparison.compare(element, max) > 0) {
          max = element;
        }
      }
    }
    return max;
  }

  /**
   * Returns the maximum object in a <code>Comparable</code> <code>Iterable</code>
   * object.
   *
   * Assume that null is the maximum value.
   *
   *@param T
   *          any object type. Note that it could be type of arrays or
   *          multi-dimensional arrays.
   * @param iterable
   *          an <code>Iterable</code> of values of class <code>T</code>, must not be
   *          <code>null</code> nor empty.
   * @return the maximum object in the list.In case of ties, returns the one
   *         with the largest position. Note that the returned value is either
   *         <code>null</code> or a reference to one of elements in the
   *         <code>iterable</code>.
   * @throws NullArgumentException
   *           if <code>iterable</code> is <code>null</code> or empty.
   */
  public static <T> T max(Iterable<T> iterable) {
    if (iterable == null) {
      throw new NullArgumentException();
    }
    Iterator<T> iter = iterable.iterator();
    if (! iter.hasNext()) {
      throw new NullArgumentException();
    }
    // find the first non-null element
    T max = iter.next();
    while ((max == null) && iter.hasNext()) {
      max = iter.next();
    }
    if (max == null) {
      // all elements in the iterable are null
      return null;
    }
    while (iter.hasNext()) {
      T element = iter.next();
      if (element != null) {
        if (Comparison.compare(element, max) > 0) {
          max = element;
        }
      }
    }
    return max;
  }
}
