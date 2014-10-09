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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

import com.github.haixing_hu.collection.primitive.impl.ArrayBooleanList;
import com.github.haixing_hu.collection.primitive.impl.ArrayByteList;
import com.github.haixing_hu.collection.primitive.impl.ArrayCharList;
import com.github.haixing_hu.collection.primitive.impl.ArrayDoubleList;
import com.github.haixing_hu.collection.primitive.impl.ArrayFloatList;
import com.github.haixing_hu.collection.primitive.impl.ArrayIntList;
import com.github.haixing_hu.collection.primitive.impl.ArrayLongList;
import com.github.haixing_hu.collection.primitive.impl.ArrayShortList;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for the Equality class.
 *
 * @author Haixing Hu
 */
public class EqualityTest {

  static class TestObject {
    private int a;

    public TestObject() {
    }

    public TestObject(final int a) {
      this.a = a;
    }

    @Override
    public boolean equals(final Object o) {
      if (o == null) {
        return false;
      }
      if (o == this) {
        return true;
      }
      if (o.getClass() != getClass()) {
        return false;
      }
      final TestObject rhs = (TestObject) o;
      return (a == rhs.a);
    }

    public void setA(final int a) {
      this.a = a;
    }

    public int getA() {
      return a;
    }
  }

  public static class TestACanEqualB {
    private final int a;

    public TestACanEqualB(final int a) {
      this.a = a;
    }

    @Override
    public int hashCode() {
      return a;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final TestACanEqualB other = (TestACanEqualB) obj;
      if (a != other.a) {
        return false;
      }
      return true;
    }

    public int getA() {
      return a;
    }
  }

  public static class TestBCanEqualA {
    private final int b;

    public TestBCanEqualA(final int b) {
      this.b = b;
    }

    @Override
    public int hashCode() {
      return b;
    }

    @Override
    public boolean equals(final Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof TestACanEqualB) {
        return b == ((TestACanEqualB) o).getA();
      }
      if (o instanceof TestBCanEqualA) {
        return b == ((TestBCanEqualA) o).getB();
      }
      return false;
    }

    public int getB() {
      return b;
    }
  }

  public enum TestEnum {
    a,
    b,
    c,
    d,
    e
  };

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(boolean[], boolean[])}.
   */
  @Test
  public void testBooleanArray() {
    boolean[] obj1 = new boolean[] { true, false };
    boolean[] obj2 = new boolean[] { true, false };
    final boolean[] obj3 = new boolean[] { true, false, true };

    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(false, Equality.equals(obj1, obj3));
    assertEquals(false, Equality.equals(obj2, obj3));
    assertEquals(true, Equality.equals(obj3, obj3));
    obj1[1] = true;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj2 = null;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1 = null;
    assertEquals(true, Equality.equals(obj1, obj2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equals(boolean[], int, boolean[], int, int)}
   * .
   */
  @Test
  public void testBooleanArrayIndexLength() {
    final boolean[] array1 = new boolean[] { true, true, false, false };
    final boolean[] array2 = new boolean[] { true, true, false, false };
    final boolean[] array3 = new boolean[] { true, true, true, false };

    assertEquals(true, Equality.equals(array1, 0, array2, 0, 4));
    assertEquals(true, Equality.equals(array1, 0, array2, 0, 3));
    assertEquals(false, Equality.equals(array1, 0, array2, 1, 3));
    assertEquals(false, Equality.equals(array1, 1, array3, 1, 3));
    assertEquals(true, Equality.equals(array1, 1, array3, 2, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equal(Boolean, Boolean)}.
   */
  @Test
  public void testBooleanObject() {
    Boolean value1 = null;
    Boolean value2 = null;

    assertEquals(true, Equality.equals(value1, value2));
    value1 = new Boolean(true);
    assertEquals(false, Equality.equals(value1, value2));
    value2 = new Boolean(false);
    assertEquals(false, Equality.equals(value1, value2));
    value2 = new Boolean(true);
    assertEquals(true, Equality.equals(value1, value2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equal(Boolean[], Boolean[])}.
   */
  @Test
  public void testBooleanObjectArray() {
    Boolean[] obj1 = new Boolean[] { true, false };
    Boolean[] obj2 = new Boolean[] { true, false };
    final Boolean[] obj3 = new Boolean[] { true, false, true };

    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(false, Equality.equals(obj1, obj3));
    assertEquals(false, Equality.equals(obj2, obj3));
    assertEquals(true, Equality.equals(obj3, obj3));
    obj1[1] = new Boolean(true);
    assertEquals(false, Equality.equals(obj1, obj2));
    obj2 = null;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1 = null;
    assertEquals(true, Equality.equals(obj1, obj2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equal(Boolean[], int, Boolean[], int, int)}
   * .
   */
  @Test
  public void testBooleanObjectArrayIndexLength() {
    final Boolean[] array1 = new Boolean[] { true, true, false, false };
    final Boolean[] array2 = new Boolean[] { true, true, false, false };
    final Boolean[] array3 = new Boolean[] { true, true, true, false };

    assertEquals(true, Equality.equals(array1, 0, array2, 0, 4));
    assertEquals(true, Equality.equals(array1, 0, array2, 0, 3));
    assertEquals(false, Equality.equals(array1, 0, array2, 1, 3));
    assertEquals(false, Equality.equals(array1, 1, array3, 1, 3));
    assertEquals(true, Equality.equals(array1, 1, array3, 2, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(char[], char[])}.
   */
  @Test
  public void testCharArray() {
    char[] obj1 = new char[] { 'a', 'b' };
    char[] obj2 = new char[] { 'a', 'b' };
    final char[] obj3 = new char[] { 'a', 'b', 'c' };

    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(false, Equality.equals(obj1, obj3));
    assertEquals(false, Equality.equals(obj2, obj3));
    assertEquals(true, Equality.equals(obj3, obj3));
    obj1[1] = 'a';
    assertEquals(false, Equality.equals(obj1, obj2));
    obj2 = null;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1 = null;
    assertEquals(true, Equality.equals(obj1, obj2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equals(char[], int, char[], int, int)}
   * .
   */
  @Test
  public void testCharArrayIndexLength() {
    final char[] obj1 = new char[] { 'a', 'b', 'c' };
    final char[] obj2 = new char[] { 'a', 'b', 'c' };

    assertEquals(true, Equality.equals(obj1, 0, obj1, 0, 3));
    assertEquals(true, Equality.equals(obj1, 1, obj2, 1, 2));
    assertEquals(false, Equality.equals(obj1, 1, obj2, 0, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equals(Character, Character)}.
   */
  @Test
  public void testCharacter() {
    Character value1 = null;
    Character value2 = null;

    assertEquals(true, Equality.equals(value1, value2));
    value1 = new Character('a');
    assertEquals(false, Equality.equals(value1, value2));
    value2 = new Character('a');
    assertEquals(true, Equality.equals(value1, value2));
    value2 = new Character('b');
    assertEquals(false, Equality.equals(value1, value2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equals(Character[], Character[])}
   * .
   */
  @Test
  public void testCharacterArray() {
    Character[] obj1 = new Character[] { 'a', 'b' };
    Character[] obj2 = new Character[] { 'a', 'b' };
    final Character[] obj3 = new Character[] { 'a', 'b', 'c' };

    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(false, Equality.equals(obj1, obj3));
    assertEquals(false, Equality.equals(obj2, obj3));
    assertEquals(true, Equality.equals(obj3, obj3));
    obj1[1] = 'a';
    assertEquals(false, Equality.equals(obj1, obj2));
    obj2 = null;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1 = null;
    assertEquals(true, Equality.equals(obj1, obj2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equals(Character[], int, Character[], int, int)}
   * .
   */
  @Test
  public void testCharacterArrayIndexLength() {
    final Character[] obj1 = new Character[] { 'a', 'b', 'c' };
    final Character[] obj2 = new Character[] { 'a', 'b', 'c' };

    assertEquals(true, Equality.equals(obj1, 0, obj1, 0, 3));
    assertEquals(true, Equality.equals(obj1, 1, obj2, 1, 2));
    assertEquals(false, Equality.equals(obj1, 1, obj2, 0, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equalsIgnoreCase(char, char)}.
   */
  @Test
  public void testIgnoreCaseChar() {
    assertEquals(true, Equality.equalsIgnoreCase('a', 'a'));
    assertEquals(true, Equality.equalsIgnoreCase('a', 'A'));
    assertEquals(true, Equality.equalsIgnoreCase('A', 'a'));
    assertEquals(false, Equality.equalsIgnoreCase('a', 'b'));
    assertEquals(false, Equality.equalsIgnoreCase('a', 'B'));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equalsIgnoreCase(char[], char[])}
   * .
   */
  @Test
  public void testIgnoreCaseCharArray() {
    char[] array1 = new char[] { 'a', 'b', 'c' };
    char[] array2 = new char[] { 'a', 'B', 'c' };
    final char[] array3 = new char[] { 'A', 'B', 'C' };
    final char[] array4 = new char[] { 'A', 'B', 'd' };

    assertEquals(true, Equality.equalsIgnoreCase(array1, array2));
    assertEquals(true, Equality.equalsIgnoreCase(array1, array3));
    assertEquals(true, Equality.equalsIgnoreCase(array3, array2));
    assertEquals(false, Equality.equalsIgnoreCase(array1, array4));
    array1 = null;
    assertEquals(false, Equality.equalsIgnoreCase(array1, array2));
    array2 = null;
    assertEquals(true, Equality.equalsIgnoreCase(array1, array2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equalsIgnoreCase(char[], int, char[], int, int)}
   * .
   */
  @Test
  public void testIgnoreCaseCharArrayIndexLength() {
    final char[] array1 = new char[] { 'a', 'b', 'c', 'd' };
    final char[] array2 = new char[] { 'a', 'B', 'c', 'd' };
    final char[] array3 = new char[] { 'A', 'B', 'C', 'D' };

    assertEquals(true, Equality.equalsIgnoreCase(array1, 0, array2, 0, 4));
    assertEquals(true, Equality.equalsIgnoreCase(array1, 0, array3, 0, 4));
    assertEquals(true, Equality.equalsIgnoreCase(array1, 1, array2, 1, 3));
    assertEquals(true, Equality.equalsIgnoreCase(array1, 1, array3, 1, 2));
    assertEquals(false, Equality.equalsIgnoreCase(array1, 1, array3, 2, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equalsIgnoreCase(Character, Character)}
   * .
   */
  @Test
  public void testIgnoreCaseCharacter() {
    Character value1 = null;
    Character value2 = null;
    assertEquals(true, Equality.equalsIgnoreCase(value1, value2));
    value1 = new Character('a');
    assertEquals(false, Equality.equalsIgnoreCase(value1, value2));
    value2 = new Character('a');
    assertEquals(true, Equality.equalsIgnoreCase(value1, value2));
    value2 = new Character('A');
    assertEquals(true, Equality.equalsIgnoreCase(value1, value2));
    value2 = new Character('b');
    assertEquals(false, Equality.equalsIgnoreCase(value1, value2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equalsIgnoreCase(Character[], Character[])}
   * .
   */
  @Test
  public void testIgnoreCaseCharacterArray() {
    Character[] array1 = new Character[] { 'a', 'b', 'c' };
    Character[] array2 = new Character[] { 'a', 'B', 'c' };
    final Character[] array3 = new Character[] { 'A', 'B', 'C' };
    final Character[] array4 = new Character[] { 'A', 'B', 'd' };

    assertEquals(true, Equality.equalsIgnoreCase(array1, array2));
    assertEquals(true, Equality.equalsIgnoreCase(array1, array3));
    assertEquals(true, Equality.equalsIgnoreCase(array3, array2));
    assertEquals(false, Equality.equalsIgnoreCase(array1, array4));
    array1 = null;
    assertEquals(false, Equality.equalsIgnoreCase(array1, array2));
    array2 = null;
    assertEquals(true, Equality.equalsIgnoreCase(array1, array2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equalsIgnoreCase(Character[], int, Character[], int, int)}
   * .
   */
  @Test
  public void testIgnoreCaseCharacterArrayIndexLength() {
    final Character[] array1 = new Character[] { 'a', 'b', 'c', 'd' };
    final Character[] array2 = new Character[] { 'a', 'B', 'c', 'd' };
    final Character[] array3 = new Character[] { 'A', 'B', 'C', 'D' };

    assertEquals(true, Equality.equalsIgnoreCase(array1, 0, array2, 0, 4));
    assertEquals(true, Equality.equalsIgnoreCase(array1, 0, array3, 0, 4));
    assertEquals(true, Equality.equalsIgnoreCase(array1, 1, array2, 1, 3));
    assertEquals(true, Equality.equalsIgnoreCase(array1, 1, array3, 1, 2));
    assertEquals(false, Equality.equalsIgnoreCase(array1, 1, array3, 2, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(byte[], byte[])}.
   */
  @Test
  public void testByteArray() {
    byte[] obj1 = new byte[] { 0, 1, -1, Byte.MIN_VALUE, Byte.MAX_VALUE };
    byte[] obj2 = new byte[] { 0, 1, -1, Byte.MIN_VALUE, Byte.MAX_VALUE };
    final byte[] obj3 = new byte[] { 0, 1, 2 };

    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(false, Equality.equals(obj1, obj3));
    assertEquals(false, Equality.equals(obj2, obj3));
    assertEquals(true, Equality.equals(obj3, obj3));
    obj1[0] = 1;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj2 = null;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1 = null;
    assertEquals(true, Equality.equals(obj1, obj2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equals(byte[], int, byte[], int, int)}
   * .
   */
  @Test
  public void testByteArrayIndexLength() {
    final byte[] array1 = new byte[] { 0, 1, -1, Byte.MIN_VALUE, Byte.MAX_VALUE };
    final byte[] array2 = new byte[] { 0, 1, -1, Byte.MIN_VALUE, Byte.MAX_VALUE };
    final byte[] array3 = new byte[] { -1, Byte.MIN_VALUE, 0, 1 };

    assertEquals(true, Equality.equals(array1, 0, array2, 0, 4));
    assertEquals(true, Equality.equals(array1, 0, array2, 0, 3));
    assertEquals(true, Equality.equals(array1, 1, array2, 1, 3));
    assertEquals(false, Equality.equals(array1, 1, array2, 0, 3));
    assertEquals(false, Equality.equals(array1, 1, array3, 1, 3));
    assertEquals(true, Equality.equals(array1, 0, array3, 2, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(Byte, Byte)}.
   */
  @Test
  public void testByteObject() {
    Byte value1 = null;
    Byte value2 = null;

    assertEquals(true, Equality.equals(value1, value2));
    value1 = (byte) 0;
    assertEquals(false, Equality.equals(value1, value2));
    value2 = (byte) 1;
    assertEquals(false, Equality.equals(value1, value2));
    value2 = (byte) 0;
    assertEquals(true, Equality.equals(value1, value2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(Byte[], Byte[])}.
   */
  @Test
  public void testByteObjectArray() {
    Byte[] obj1 = new Byte[] { 0, 1, -1, Byte.MIN_VALUE, Byte.MAX_VALUE };
    Byte[] obj2 = new Byte[] { 0, 1, -1, Byte.MIN_VALUE, Byte.MAX_VALUE };
    final Byte[] obj3 = new Byte[] { 0, 1, 2 };

    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(false, Equality.equals(obj1, obj3));
    assertEquals(false, Equality.equals(obj2, obj3));
    assertEquals(true, Equality.equals(obj3, obj3));
    obj1[0] = 1;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj2 = null;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1 = null;
    assertEquals(true, Equality.equals(obj1, obj2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equals(Byte[], int, Byte[], int, int)}
   * .
   */
  @Test
  public void testByteObjectArrayIndexLength() {
    final Byte[] array1 = new Byte[] { 0, 1, -1, Byte.MIN_VALUE, Byte.MAX_VALUE };
    final Byte[] array2 = new Byte[] { 0, 1, -1, Byte.MIN_VALUE, Byte.MAX_VALUE };
    final Byte[] array3 = new Byte[] { -1, Byte.MIN_VALUE, 0, 1 };

    assertEquals(true, Equality.equals(array1, 0, array2, 0, 4));
    assertEquals(true, Equality.equals(array1, 0, array2, 0, 3));
    assertEquals(true, Equality.equals(array1, 1, array2, 1, 3));
    assertEquals(false, Equality.equals(array1, 1, array2, 0, 3));
    assertEquals(false, Equality.equals(array1, 1, array3, 1, 3));
    assertEquals(true, Equality.equals(array1, 0, array3, 2, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(short[], short[])}.
   */
  @Test
  public void testShortArray() {
    short[] obj1 = new short[] { 0, 1 };
    short[] obj2 = new short[] { 0, 1 };
    final short[] obj3 = new short[] { 0, 1, 2 };

    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(false, Equality.equals(obj1, obj3));
    assertEquals(false, Equality.equals(obj2, obj3));
    assertEquals(true, Equality.equals(obj3, obj3));
    obj1[0] = 1;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj2 = null;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1 = null;
    assertEquals(true, Equality.equals(obj1, obj2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equals(short[], int, short[], int, int)}
   * .
   */
  @Test
  public void testShortArrayIndexLength() {
    final short[] array1 = new short[] { 0, -1, 1, Short.MIN_VALUE,
        Short.MAX_VALUE };
    final short[] array2 = new short[] { 0, -1, 1, Short.MIN_VALUE,
        Short.MAX_VALUE };
    final short[] array3 = new short[] { 1, Short.MIN_VALUE, 0, -1 };

    assertEquals(true, Equality.equals(array1, 0, array2, 0, 4));
    assertEquals(true, Equality.equals(array1, 0, array2, 0, 3));
    assertEquals(true, Equality.equals(array1, 1, array2, 1, 3));
    assertEquals(false, Equality.equals(array1, 1, array2, 0, 3));
    assertEquals(false, Equality.equals(array1, 1, array3, 1, 3));
    assertEquals(true, Equality.equals(array1, 0, array3, 2, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(Short, Short)}.
   */
  @Test
  public void testShortObject() {
    Short value1 = null;
    Short value2 = null;

    assertEquals(true, Equality.equals(value1, value2));
    value1 = 1;
    assertEquals(false, Equality.equals(value1, value2));
    value2 = 2;
    assertEquals(false, Equality.equals(value1, value2));
    value2 = 1;
    assertEquals(true, Equality.equals(value1, value2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(Short[], Short[])}.
   */
  @Test
  public void testShortObejctArray() {
    Short[] obj1 = new Short[] { 0, -1, 1, Short.MIN_VALUE, Short.MAX_VALUE };
    Short[] obj2 = new Short[] { 0, -1, 1, Short.MIN_VALUE, Short.MAX_VALUE };
    final Short[] obj3 = new Short[] { 0, 1, 2 };

    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(false, Equality.equals(obj1, obj3));
    assertEquals(false, Equality.equals(obj2, obj3));
    assertEquals(true, Equality.equals(obj3, obj3));
    obj1[0] = 1;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj2 = null;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1 = null;
    assertEquals(true, Equality.equals(obj1, obj2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equals(short[], int, short[], int, int)}
   * .
   */
  @Test
  public void testShortObejctArrayIndexLength() {
    final Short[] array1 = new Short[] { 0, -1, 1, Short.MIN_VALUE,
        Short.MAX_VALUE };
    final Short[] array2 = new Short[] { 0, -1, 1, Short.MIN_VALUE,
        Short.MAX_VALUE };
    final Short[] array3 = new Short[] { 1, Short.MIN_VALUE, 0, -1 };

    assertEquals(true, Equality.equals(array1, 0, array2, 0, 4));
    assertEquals(true, Equality.equals(array1, 0, array2, 0, 3));
    assertEquals(true, Equality.equals(array1, 1, array2, 1, 3));
    assertEquals(false, Equality.equals(array1, 1, array2, 0, 3));
    assertEquals(false, Equality.equals(array1, 1, array3, 1, 3));
    assertEquals(true, Equality.equals(array1, 0, array3, 2, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(int[], int[])}.
   */
  @Test
  public void testIntArray() {
    int[] obj1 = new int[] { 0, -1, 1, Integer.MIN_VALUE, Integer.MAX_VALUE };
    int[] obj2 = new int[] { 0, -1, 1, Integer.MIN_VALUE, Integer.MAX_VALUE };
    final int[] obj3 = new int[] { 0, 1, 2 };

    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(false, Equality.equals(obj1, obj3));
    assertEquals(false, Equality.equals(obj2, obj3));
    assertEquals(true, Equality.equals(obj3, obj3));
    obj1[0] = 1;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj2 = null;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1 = null;
    assertEquals(true, Equality.equals(obj1, obj2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equals(int[], int, int[], int, int)}
   * .
   */
  @Test
  public void testIntArrayIndexLength() {
    final int[] obj1 = { 0, -1, 1, Integer.MIN_VALUE, Integer.MAX_VALUE };
    final int[] obj2 = { 0, -1, 1, Integer.MIN_VALUE, Integer.MAX_VALUE };
    final int[] obj3 = { 1, Integer.MIN_VALUE, 0, -1 };

    assertEquals(true, Equality.equals(obj1, 0, obj2, 0, 4));
    assertEquals(true, Equality.equals(obj1, 0, obj2, 0, 3));
    assertEquals(false, Equality.equals(obj1, 0, obj2, 1, 3));
    assertEquals(true, Equality.equals(obj1, 2, obj3, 0, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(Integer, Integer)}.
   */
  @Test
  public void testInteger() {
    Integer value1 = null;
    Integer value2 = null;

    assertEquals(true, Equality.equals(value1, value2));
    value1 = new Integer(1);
    assertEquals(false, Equality.equals(value1, value2));
    value2 = new Integer(2);
    assertEquals(false, Equality.equals(value1, value2));
    value2 = new Integer(1);
    assertEquals(true, Equality.equals(value1, value2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equals(Integer[], Integer[])}.
   */
  @Test
  public void testIntegerArray() {
    Integer[] obj1 = new Integer[] { 0, -1, 1, Integer.MIN_VALUE,
        Integer.MAX_VALUE };
    Integer[] obj2 = new Integer[] { 0, -1, 1, Integer.MIN_VALUE,
        Integer.MAX_VALUE };
    final Integer[] obj3 = new Integer[] { 0, 1, 2 };

    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(false, Equality.equals(obj1, obj3));
    assertEquals(false, Equality.equals(obj2, obj3));
    assertEquals(true, Equality.equals(obj3, obj3));
    obj1[0] = 1;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj2 = null;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1 = null;
    assertEquals(true, Equality.equals(obj1, obj2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# equals(Integer[], int, Integer[], int, int)}
   * .
   */
  @Test
  public void testIntegerArrayIndexLength() {
    final Integer[] obj1 = new Integer[] { 0, -1, 1, Integer.MIN_VALUE,
        Integer.MAX_VALUE };
    final Integer[] obj2 = new Integer[] { 0, -1, 1, Integer.MIN_VALUE,
        Integer.MAX_VALUE };
    final Integer[] obj3 = new Integer[] { 1, Integer.MIN_VALUE, 0, 1 };

    assertEquals(true, Equality.equals(obj1, 0, obj2, 0, 4));
    assertEquals(true, Equality.equals(obj1, 0, obj2, 0, 3));
    assertEquals(false, Equality.equals(obj1, 0, obj2, 1, 3));
    assertEquals(true, Equality.equals(obj1, 2, obj3, 0, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(long[], long[])}.
   */
  @Test
  public void testLongArray() {
    long[] obj1 = { 0, -1, 1, Long.MIN_VALUE, Long.MAX_VALUE };
    long[] obj2 = { 0, -1, 1, Long.MIN_VALUE, Long.MAX_VALUE };
    final long[] obj3 = { 0, 1, 2 };

    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(false, Equality.equals(obj1, obj3));
    assertEquals(false, Equality.equals(obj2, obj3));
    assertEquals(true, Equality.equals(obj3, obj3));
    obj1[0] = 1;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj2 = null;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1 = null;
    assertEquals(true, Equality.equals(obj1, obj2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# euqls(long[], int, long[], int, int)}
   * .
   */
  @Test
  public void testLongArrayIndexLength() {
    final long[] array1 = { 0, -1, 1, Long.MIN_VALUE, Long.MAX_VALUE };
    final long[] array2 = { 0, -1, 1, Long.MIN_VALUE, Long.MAX_VALUE };
    final long[] array3 = { 1, Long.MIN_VALUE, 0, -1 };

    assertEquals(true, Equality.equals(array1, 0, array2, 0, 4));
    assertEquals(true, Equality.equals(array1, 0, array2, 0, 2));
    assertEquals(true, Equality.equals(array1, 1, array2, 1, 2));
    assertEquals(false, Equality.equals(array1, 0, array3, 0, 2));
    assertEquals(false, Equality.equals(array1, 1, array3, 1, 3));
    assertEquals(true, Equality.equals(array1, 0, array3, 2, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(Long, Long)}.
   */
  @Test
  public void testLongObject() {
    Long value1 = (long) 1;
    Long value2 = (long) 1;

    assertEquals(true, Equality.equals(value1, value2));
    value2 = (long) 2;
    assertEquals(false, Equality.equals(value1, value2));
    value2 = null;
    assertEquals(false, Equality.equals(value1, value2));
    value1 = null;
    assertEquals(true, Equality.equals(value1, value2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(Long[], Long[])}.
   */
  @Test
  public void testLongObjectArray() {
    Long[] obj1 = new Long[] { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        Long.MAX_VALUE };
    Long[] obj2 = new Long[] { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        Long.MAX_VALUE };
    final Long[] obj3 = new Long[] { (long) 0, (long) 1, (long) 2 };

    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(false, Equality.equals(obj1, obj3));
    assertEquals(false, Equality.equals(obj2, obj3));
    assertEquals(true, Equality.equals(obj3, obj3));
    obj1[0] = (long) 1;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj2 = null;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1 = null;
    assertEquals(true, Equality.equals(obj1, obj2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality# euqls(Long[], int, Long[], int, int)}
   * .
   */
  @Test
  public void testLongObjectArrayIndexLength() {
    final Long[] array1 = new Long[] { (long) 0, (long) -1, (long) 1,
        Long.MIN_VALUE, Long.MAX_VALUE };
    final Long[] array2 = new Long[] { (long) 0, (long) -1, (long) 1,
        Long.MIN_VALUE, Long.MAX_VALUE };
    final Long[] array3 = new Long[] { (long) 1, Long.MIN_VALUE, (long) 0,
        (long) -1 };

    assertEquals(true, Equality.equals(array1, 0, array2, 0, 4));
    assertEquals(true, Equality.equals(array1, 0, array2, 0, 2));
    assertEquals(true, Equality.equals(array1, 1, array2, 1, 2));
    assertEquals(false, Equality.equals(array1, 0, array3, 0, 2));
    assertEquals(false, Equality.equals(array1, 1, array3, 1, 3));
    assertEquals(true, Equality.equals(array1, 0, array3, 2, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(float, float)}.
   */
  @Test
  public void testFloat() {
    assertEquals(true, Equality.equals(0.0f, 0.0f));
    assertEquals(false, Equality.equals(0.0f, -0.0f));
    assertEquals(true, Equality.equals(Float.MAX_VALUE, Float.MAX_VALUE));
    assertEquals(true, Equality.equals(Float.MIN_VALUE, Float.MIN_VALUE));
    // assertEquals(true, Equality.equals(Float.MIN_NORMAL, Float.MIN_NORMAL));
    assertEquals(true, Equality.equals(Float.NaN, Float.NaN));
    assertEquals(false, Equality.equals(Float.MAX_VALUE, Float.NaN));
    assertEquals(false, Equality.equals(Float.MIN_VALUE, Float.NaN));
    // assertEquals(false, Equality.equals(Float.MIN_NORMAL, Float.NaN));
    assertEquals(true, Equality.equals(-Float.NaN, Float.NaN));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(float[], float[])}.
   */
  @Test
  public void testFloatArray() {
    float[] obj1 = new float[] { 0.0f, 3.1415926535768f };
    float[] obj2 = new float[] { 0.0f, 3.1415926535768f };
    final float[] obj3 = new float[] { -0.0f, 3.1415926535768f,
        3.1415926535768f };

    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(false, Equality.equals(obj1, obj3));
    assertEquals(false, Equality.equals(obj2, obj3));
    assertEquals(true, Equality.equals(obj3, obj3));
    obj1[0] = -0.0f;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj2 = null;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1 = null;
    assertEquals(true, Equality.equals(obj1, obj2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(float[], int, float[], int, int)}
   * .
   */
  @Test
  public void testFloatArrayIndexLength() {
    final float[] array1 = { 0f, -1f, 1f, Float.MIN_VALUE, Float.MAX_VALUE };
    final float[] array2 = { 0f, -1f, 1f, Float.MIN_VALUE, Float.MAX_VALUE };
    final float[] array3 = { 1f, Float.MIN_VALUE, 0f, -1f };

    assertEquals(true, Equality.equals(array1, 0, array2, 0, 4));
    assertEquals(true, Equality.equals(array1, 0, array2, 0, 2));
    assertEquals(false, Equality.equals(array1, 0, array2, 1, 3));
    assertEquals(false, Equality.equals(array1, 0, array3, 0, 4));
    assertEquals(true, Equality.equals(array1, 0, array3, 2, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #valueEquals(float, float, float)}
   * .
   */
  @Test
  public void testFloatWithEpsilon() {
    final float epsilon = 0.001f;

    assertEquals(true, Equality.valueEquals(0.0f, 0.0f, epsilon));
    assertEquals(true, Equality.valueEquals(0.0f, 0.0f + epsilon, epsilon));
    assertEquals(true, Equality.valueEquals(0.0f, 0.0f - epsilon, epsilon));
    assertEquals(true, Equality.valueEquals(0.0f, -0.0f, epsilon));

    assertEquals(true,
        Equality.valueEquals(Float.MAX_VALUE, Float.MAX_VALUE, epsilon));
    assertEquals(true, Equality.valueEquals(Float.MAX_VALUE, Float.MAX_VALUE
        + epsilon, epsilon));
    assertEquals(true, Equality.valueEquals(Float.MAX_VALUE, Float.MAX_VALUE
        - epsilon, epsilon));

    assertEquals(true,
        Equality.valueEquals(Float.MIN_VALUE, Float.MIN_VALUE, epsilon));
    assertEquals(true, Equality.valueEquals(Float.MIN_VALUE, Float.MIN_VALUE
        + epsilon, epsilon));
    assertEquals(true, Equality.valueEquals(Float.MIN_VALUE, Float.MIN_VALUE
        - epsilon, epsilon));

    // assertEquals(true, Equality.valueEquals(Float.MIN_NORMAL,
    // Float.MIN_NORMAL, epsilon));
    // assertEquals(true, Equality.valueEquals(Float.MIN_NORMAL,
    // Float.MIN_NORMAL + epsilon, epsilon));
    // assertEquals(true, Equality.valueEquals(Float.MIN_NORMAL,
    // Float.MIN_NORMAL - epsilon, epsilon));

    assertEquals(true, Equality.valueEquals(Float.NaN, Float.NaN, epsilon));
    assertEquals(true,
        Equality.valueEquals(Float.NaN, Float.NaN + epsilon, epsilon));
    assertEquals(true,
        Equality.valueEquals(Float.NaN, Float.NaN - epsilon, epsilon));

    assertEquals(false,
        Equality.valueEquals(Float.MAX_VALUE, Float.NaN, epsilon));
    assertEquals(false,
        Equality.valueEquals(Float.MAX_VALUE, Float.NaN + epsilon, epsilon));
    assertEquals(false,
        Equality.valueEquals(Float.MAX_VALUE, Float.NaN - epsilon, epsilon));

    assertEquals(false,
        Equality.valueEquals(Float.MIN_VALUE, Float.NaN, epsilon));
    assertEquals(false,
        Equality.valueEquals(Float.MAX_VALUE, Float.NaN + epsilon, epsilon));
    assertEquals(false,
        Equality.valueEquals(Float.MAX_VALUE, Float.NaN - epsilon, epsilon));

    // assertEquals(false, Equality.equals(Float.MIN_NORMAL, Float.NaN,
    // epsilon));
    assertEquals(false,
        Equality.valueEquals(Float.MAX_VALUE, Float.NaN + epsilon, epsilon));
    assertEquals(false,
        Equality.valueEquals(Float.MAX_VALUE, Float.NaN - epsilon, epsilon));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #valueEquals(float[], float[], float)}
   * .
   */
  @Test
  public void testFloatArrayWithEpsilon() {
    final float epsilon = 0.0001f;
    float[] obj1 = new float[] { 0.0f, 3.1415926535768f + epsilon };
    float[] obj2 = new float[] { 0.0f + epsilon, 3.1415926535768f };
    final float[] obj3 = new float[] { -0.0f, 3.1415926535768f,
        3.1415926535768f };

    assertEquals(true, Equality.valueEquals(obj1, obj1, epsilon));
    // NOTE: the epsilon MUST be small enough, otherwise, obj1 may not
    // equals to obj2 because of the floating-point arithmetic inaccuracy.
    assertEquals(true, Equality.valueEquals(obj1, obj2, epsilon));
    assertEquals(false, Equality.valueEquals(obj1, obj3, epsilon));
    assertEquals(false, Equality.valueEquals(obj2, obj3, epsilon));
    assertEquals(true, Equality.valueEquals(obj3, obj3, epsilon));
    obj1[0] = -0.0f;
    assertEquals(true, Equality.valueEquals(obj1, obj2, epsilon));
    obj2 = null;
    assertEquals(false, Equality.valueEquals(obj1, obj2, epsilon));
    obj1 = null;
    assertEquals(true, Equality.valueEquals(obj1, obj2, epsilon));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #valueEquals(float[], int, float[], int, int, float)}
   * .
   */
  @Test
  public void testFloatArrayIndexLengthWithEpsilon() {
    final float epsilon = 0.01f;
    final float[] array1 = { 0f, -1f, 1f, Float.MIN_VALUE, Float.MAX_VALUE };
    final float[] array2 = { 0f, -1f + epsilon, 1f, Float.MIN_VALUE + epsilon,
        Float.MAX_VALUE };
    final float[] array3 = { 1f + epsilon, Float.MIN_VALUE, 0f + epsilon, -1f,
        Float.MAX_VALUE };

    assertEquals(true, Equality.valueEquals(array1, 0, array2, 0, 4, epsilon));
    assertEquals(true, Equality.valueEquals(array1, 0, array2, 0, 2, epsilon));
    assertEquals(false, Equality.valueEquals(array1, 0, array2, 1, 3, epsilon));
    assertEquals(false, Equality.valueEquals(array1, 0, array3, 0, 4, epsilon));
    assertEquals(true, Equality.valueEquals(array1, 0, array3, 2, 2, epsilon));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(java.lang.Float, java.lang.Float)}
   * .
   */
  @Test
  public void testFloatObject() {
    assertEquals(true, Equality.equals(new Float(0.0f), new Float(0.0f)));
    assertEquals(false, Equality.equals(new Float(0.0f), new Float(-0.0f)));
    assertEquals(true,
        Equality.equals(new Float(Float.MAX_VALUE), new Float(Float.MAX_VALUE)));
    assertEquals(true,
        Equality.equals(new Float(Float.MIN_VALUE), new Float(Float.MIN_VALUE)));
    // assertEquals(true, Equality.equals(new Float(Float.MIN_NORMAL), new
    // Float(Float.MIN_NORMAL)));
    assertEquals(true,
        Equality.equals(new Float(Float.NaN), new Float(Float.NaN)));
    assertEquals(false,
        Equality.equals(new Float(Float.MAX_VALUE), new Float(Float.NaN)));
    assertEquals(false,
        Equality.equals(new Float(Float.MIN_VALUE), new Float(Float.NaN)));
    // assertEquals(false, Equality.equals(new Float(Float.MIN_NORMAL), new
    // Float(Float.NaN)));
    assertEquals(true,
        Equality.equals(new Float(-Float.NaN), new Float(Float.NaN)));

    assertEquals(false, Equality.equals(null, new Float(Float.NaN)));
    assertEquals(false, Equality.equals(new Float(-Float.NaN), null));
    assertEquals(true, Equality.equals((Float) null, (Float) null));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(java.lang.Float[], java.lang.Float[])}
   * .
   */
  @Test
  public void testFloatObjectArray() {
    Float[] obj1 = new Float[] { 0.0f, 3.1415926535768f };
    Float[] obj2 = new Float[] { 0.0f, 3.1415926535768f };
    final Float[] obj3 = new Float[] { -0.0f, 3.1415926535768f,
        3.1415926535768f };

    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(false, Equality.equals(obj1, obj3));
    assertEquals(false, Equality.equals(obj2, obj3));
    assertEquals(true, Equality.equals(obj3, obj3));

    obj1[0] = -0.0f;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1[0] = null;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj2 = null;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1 = null;
    assertEquals(true, Equality.equals(obj1, obj2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(Float[], int, Float[], int, int)}
   * .
   */
  @Test
  public void testFloatObjectArrayIndexLength() {
    final Float[] array1 = new Float[] { 0f, -1f, 1f, Float.MIN_VALUE,
        Float.MAX_VALUE };
    final Float[] array2 = new Float[] { 0f, -1f, 1f, Float.MIN_VALUE,
        Float.MAX_VALUE };
    final Float[] array3 = new Float[] { 1f, Float.MIN_VALUE, 0f, -1f,
        Float.MAX_VALUE };

    assertEquals(true, Equality.equals(array1, 0, array2, 0, 4));
    assertEquals(true, Equality.equals(array1, 0, array2, 0, 2));
    assertEquals(false, Equality.equals(array1, 0, array2, 1, 3));
    assertEquals(false, Equality.equals(array1, 0, array3, 0, 4));
    assertEquals(true, Equality.equals(array1, 0, array3, 2, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #valueEquals(java.lang.Float, java.lang.Float, float)}
   * .
   */
  @Test
  public void testFloatObjectWithEpsilon() {
    final float epsilon = 0.001f;
    Float a = 0.0f;
    Float b = 0.0f;

    assertEquals(true, Equality.valueEquals(a, b, epsilon));
    b = 0.0f + epsilon;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));
    b = 0.0f - epsilon;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));
    b = -0.0f;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));

    a = Float.MAX_VALUE;
    b = Float.MAX_VALUE;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));
    b = Float.MAX_VALUE + epsilon;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));
    b = Float.MAX_VALUE - epsilon;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));

    a = Float.MIN_VALUE;
    b = Float.MIN_VALUE;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));
    b = Float.MIN_VALUE + epsilon;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));
    b = Float.MIN_VALUE - epsilon;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));

    // a = Float.MIN_NORMAL;
    // b = Float.MIN_NORMAL;
    // assertEquals(true, Equality.valueEquals(a, b, epsilon));
    // b = Float.MIN_NORMAL + epsilon;
    // assertEquals(true, Equality.valueEquals(a, b, epsilon));
    // b = Float.MIN_NORMAL - epsilon;
    // assertEquals(true, Equality.valueEquals(a, b, epsilon));

    a = Float.NaN;
    b = Float.NaN;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));
    b = Float.NaN + epsilon;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));
    b = Float.NaN - epsilon;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));

    a = Float.MAX_VALUE;
    b = Float.NaN;
    assertEquals(false, Equality.valueEquals(a, b, epsilon));
    b = Float.NaN + epsilon;
    assertEquals(false, Equality.valueEquals(a, b, epsilon));
    b = Float.NaN - epsilon;
    assertEquals(false, Equality.valueEquals(a, b, epsilon));

    a = Float.NaN;
    b = null;
    assertEquals(false, Equality.valueEquals(a, b, epsilon));
    a = null;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #valueEquals(java.lang.Float[], java.lang.Float[], float)}
   * .
   */
  @Test
  public void testFloatObjectArrayWithEpsilon() {
    final Float epsilon = 0.0001f;
    Float[] obj1 = new Float[] { 0.0f, 3.1415926535768f + epsilon };
    Float[] obj2 = new Float[] { 0.0f + epsilon, 3.1415926535768f };
    final Float[] obj3 = new Float[] { -0.0f, 3.1415926535768f,
        3.1415926535768f };

    assertEquals(true, Equality.valueEquals(obj1, obj1, epsilon));
    // NOTE: the epsilon MUST be small enough, otherwise, obj1 may not
    // equals to obj2 because of the floating-point arithmetic inaccuracy.
    assertEquals(true, Equality.valueEquals(obj1, obj2, epsilon));
    assertEquals(false, Equality.valueEquals(obj1, obj3, epsilon));
    assertEquals(false, Equality.valueEquals(obj2, obj3, epsilon));
    assertEquals(true, Equality.valueEquals(obj3, obj3, epsilon));
    obj1[0] = -0.0f;
    assertEquals(true, Equality.valueEquals(obj1, obj2, epsilon));
    obj1[0] = null;
    assertEquals(false, Equality.valueEquals(obj1, obj2, epsilon));
    obj2 = null;
    assertEquals(false, Equality.valueEquals(obj1, obj2, epsilon));
    obj1 = null;
    assertEquals(true, Equality.valueEquals(obj1, obj2, epsilon));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #valueEquals(Float[], int, Float[], int, int, float)}
   * .
   */
  @Test
  public void testFloatObjectArrayIndexLengthWithEpsilon() {
    final Float epsilon = 0.001f;
    final Float[] array1 = new Float[] { 0f, -1f, 1f, Float.MIN_VALUE,
        Float.MAX_VALUE };
    final Float[] array2 = new Float[] { 0f, -1f + epsilon, 1f,
        Float.MIN_VALUE + epsilon, Float.MAX_VALUE };
    final Float[] array3 = new Float[] { 1f + epsilon, Float.MIN_VALUE,
        0f + epsilon, -1f, Float.MAX_VALUE };

    assertEquals(true, Equality.valueEquals(array1, 0, array2, 0, 5, epsilon));
    assertEquals(true, Equality.valueEquals(array1, 0, array2, 0, 2, epsilon));
    assertEquals(false, Equality.valueEquals(array1, 0, array2, 1, 3, epsilon));
    assertEquals(false, Equality.valueEquals(array1, 0, array3, 0, 4, epsilon));
    assertEquals(true, Equality.valueEquals(array1, 0, array3, 2, 2, epsilon));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(double, double)}.
   */
  @Test
  public void testDouble() {
    assertEquals(true, Equality.equals(0.0, 0.0));
    assertEquals(false, Equality.equals(0.0, -0.0));
    assertEquals(true, Equality.equals(Double.MAX_VALUE, Double.MAX_VALUE));
    assertEquals(true, Equality.equals(Double.MIN_VALUE, Double.MIN_VALUE));
    // assertEquals(true, Equality.equals(Double.MIN_NORMAL,
    // Double.MIN_NORMAL));
    assertEquals(true, Equality.equals(Double.NaN, Double.NaN));
    assertEquals(false, Equality.equals(Double.MAX_VALUE, Double.NaN));
    assertEquals(false, Equality.equals(Double.MIN_VALUE, Double.NaN));
    // assertEquals(false, Equality.equals(Double.MIN_NORMAL, Double.NaN));
    assertEquals(true, Equality.equals(-Double.NaN, Double.NaN));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(double[], double[])}.
   */
  @Test
  public void testDoubleArray() {
    double[] obj1 = new double[] { 0.0, 3.1415926535768 };
    double[] obj2 = new double[] { 0.0, 3.1415926535768 };
    final double[] obj3 = new double[] { -0.0, 3.1415926535768, 3.1415926535768 };

    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(false, Equality.equals(obj1, obj3));
    assertEquals(false, Equality.equals(obj2, obj3));
    assertEquals(true, Equality.equals(obj3, obj3));
    obj1[0] = -0.0;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj2 = null;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1 = null;
    assertEquals(true, Equality.equals(obj1, obj2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(double[], int, double[], int, int)}
   * .
   */
  @Test
  public void testDoubleArrayIndexLength() {
    final double[] array1 = new double[] { 0, -1, 1, Double.MIN_VALUE,
        Double.MAX_VALUE };
    final double[] array2 = new double[] { 0, -1, 1, Double.MIN_VALUE,
        Double.MAX_VALUE };
    final double[] array3 = new double[] { 1, Double.MIN_VALUE, 0, -1 };

    assertEquals(true, Equality.equals(array1, 0, array2, 0, 5));
    assertEquals(true, Equality.equals(array1, 0, array2, 0, 3));
    assertEquals(false, Equality.equals(array1, 0, array2, 2, 3));
    assertEquals(false, Equality.equals(array1, 0, array3, 0, 5));
    assertEquals(false, Equality.equals(array1, 0, array3, 1, 3));
    assertEquals(true, Equality.equals(array1, 2, array3, 0, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #valueEquals(double, double, double)}
   * .
   */
  @Test
  public void testDoubleWithEpsilon() {
    final double epsilon = 0.00001;

    assertEquals(true, Equality.valueEquals(0.0, 0.0, epsilon));
    assertEquals(true, Equality.valueEquals(0.0, 0.0 + epsilon, epsilon));
    assertEquals(true, Equality.valueEquals(0.0, 0.0 - epsilon, epsilon));
    assertEquals(true, Equality.valueEquals(0.0, -0.0, epsilon));

    assertEquals(true,
        Equality.valueEquals(Double.MAX_VALUE, Double.MAX_VALUE, epsilon));
    assertEquals(true, Equality.valueEquals(Double.MAX_VALUE, Double.MAX_VALUE
        + epsilon, epsilon));
    assertEquals(true, Equality.valueEquals(Double.MAX_VALUE, Double.MAX_VALUE
        - epsilon, epsilon));

    assertEquals(true,
        Equality.valueEquals(Double.MIN_VALUE, Double.MIN_VALUE, epsilon));
    assertEquals(true, Equality.valueEquals(Double.MIN_VALUE, Double.MIN_VALUE
        + epsilon, epsilon));
    assertEquals(true, Equality.valueEquals(Double.MIN_VALUE, Double.MIN_VALUE
        - epsilon, epsilon));
    //
    // assertEquals(true, Equality.valueEquals(Double.MIN_NORMAL,
    // Double.MIN_NORMAL, epsilon));
    // assertEquals(true, Equality.valueEquals(Double.MIN_NORMAL,
    // Double.MIN_NORMAL + epsilon, epsilon));
    // assertEquals(true, Equality.valueEquals(Double.MIN_NORMAL,
    // Double.MIN_NORMAL - epsilon, epsilon));

    assertEquals(true, Equality.valueEquals(Double.NaN, Double.NaN, epsilon));
    assertEquals(true,
        Equality.valueEquals(Double.NaN, Double.NaN + epsilon, epsilon));
    assertEquals(true,
        Equality.valueEquals(Double.NaN, Double.NaN - epsilon, epsilon));

    assertEquals(false,
        Equality.valueEquals(Double.MAX_VALUE, Double.NaN, epsilon));
    assertEquals(false,
        Equality.valueEquals(Double.MAX_VALUE, Double.NaN + epsilon, epsilon));
    assertEquals(false,
        Equality.valueEquals(Double.MAX_VALUE, Double.NaN - epsilon, epsilon));

    assertEquals(false,
        Equality.valueEquals(Double.MIN_VALUE, Double.NaN, epsilon));
    assertEquals(false,
        Equality.valueEquals(Double.MAX_VALUE, Double.NaN + epsilon, epsilon));
    assertEquals(false,
        Equality.valueEquals(Double.MAX_VALUE, Double.NaN - epsilon, epsilon));

    // assertEquals(false, Equality.valueEquals(Double.MIN_NORMAL, Double.NaN,
    // epsilon));
    assertEquals(false,
        Equality.valueEquals(Double.MAX_VALUE, Double.NaN + epsilon, epsilon));
    assertEquals(false,
        Equality.valueEquals(Double.MAX_VALUE, Double.NaN - epsilon, epsilon));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #valueEquals(double[], double[], double)}
   * .
   */
  @Test
  public void testDoubleArrayWithEpsilon() {

    final double epsilon = 0.0000001;
    double[] obj1 = new double[] { 0.0, 3.1415926535768 + epsilon };
    double[] obj2 = new double[] { 0.0 + epsilon, 3.1415926535768 };
    final double[] obj3 = new double[] { -0.0, 3.1415926535768, 3.1415926535768 };

    assertEquals(true, Equality.valueEquals(obj1, obj1, epsilon));
    // NOTE: the epsilon MUST be small enough, otherwise, obj1 may not
    // equals to obj2 because of the floating-point arithmetic inaccuracy.
    assertEquals(true, Equality.valueEquals(obj1, obj2, epsilon));
    assertEquals(false, Equality.valueEquals(obj1, obj3, epsilon));
    assertEquals(false, Equality.valueEquals(obj2, obj3, epsilon));
    assertEquals(true, Equality.valueEquals(obj3, obj3, epsilon));
    obj1[0] = -0.0;
    assertEquals(true, Equality.valueEquals(obj1, obj2, epsilon));
    obj2 = null;
    assertEquals(false, Equality.valueEquals(obj1, obj2, epsilon));
    obj1 = null;
    assertEquals(true, Equality.valueEquals(obj1, obj2, epsilon));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #valueEquals(double[], int, double[], int, int, double)}
   * .
   */
  @Test
  public void testDoubleArrayIndexLengthWithEpsilon() {
    final double epsilon = 0.0000001;
    final double[] array1 = new double[] { 0, -1, 1,
        Double.MIN_VALUE, Double.MAX_VALUE };
    final double[] array2 = new double[] { 0, -1 + epsilon,
        1, Double.MIN_VALUE + epsilon, Double.MAX_VALUE };
    final double[] array3 = new double[] { 1, Double.MIN_VALUE,
        0, -1, Double.MAX_VALUE };

    assertEquals(true, Equality.valueEquals(array1, 0, array2, 0, 5, epsilon));
    assertEquals(true, Equality.valueEquals(array1, 1, array2, 1, 3, epsilon));
    assertEquals(false, Equality.valueEquals(array1, 1, array2, 2, 3, epsilon));
    assertEquals(false, Equality.valueEquals(array1, 0, array3, 0, 5, epsilon));
    assertEquals(true, Equality.valueEquals(array1, 0, array3, 2, 2, epsilon));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(java.lang.Double, java.lang.Double)}
   * .
   */
  @Test
  public void testDoubleObject() {
    assertEquals(true, Equality.equals(new Double(0.0), new Double(0.0)));
    assertEquals(false, Equality.equals(new Double(0.0), new Double(-0.0)));
    assertEquals(true, Equality.equals(new Double(Double.MAX_VALUE),
        new Double(Double.MAX_VALUE)));
    assertEquals(true, Equality.equals(new Double(Double.MIN_VALUE),
        new Double(Double.MIN_VALUE)));
    // assertEquals(true, Equality.equals(new Double(Double.MIN_NORMAL), new
    // Double(Double.MIN_NORMAL)));
    assertEquals(true,
        Equality.equals(new Double(Double.NaN), new Double(Double.NaN)));
    assertEquals(false,
        Equality.equals(new Double(Double.MAX_VALUE), new Double(Double.NaN)));
    assertEquals(false,
        Equality.equals(new Double(Double.MIN_VALUE), new Double(Double.NaN)));
    // assertEquals(false, Equality.equals(new Double(Double.MIN_NORMAL), new
    // Double(Double.NaN)));
    assertEquals(true,
        Equality.equals(new Double(-Double.NaN), new Double(Double.NaN)));

    assertEquals(false, Equality.equals(null, new Double(Float.NaN)));
    assertEquals(false, Equality.equals(new Double(-Double.NaN), null));
    assertEquals(true, Equality.equals((Double) null, (Double) null));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(java.lang.Double[], java.lang.Double[])}
   * .
   */
  @Test
  public void testDoubleObjectArray() {
    Double[] obj1 = new Double[] { 0.0, 3.1415926535768 };
    Double[] obj2 = new Double[] { 0.0, 3.1415926535768 };
    final Double[] obj3 = new Double[] { -0.0, 3.1415926535768, 3.1415926535768 };

    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(false, Equality.equals(obj1, obj3));
    assertEquals(false, Equality.equals(obj2, obj3));
    assertEquals(true, Equality.equals(obj3, obj3));
    obj1[0] = -0.0;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1[0] = null;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj2 = null;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1 = null;
    assertEquals(true, Equality.equals(obj1, obj2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(double[], int, double[], int, int)}
   * .
   */
  @Test
  public void testDoubleObjectArrayIndexLength() {
    final Double[] array1 = new Double[] { (double) 0, (double) -1, (double) 1,
        Double.MIN_VALUE, Double.MAX_VALUE };
    final Double[] array2 = new Double[] { (double) 0, (double) -1, (double) 1,
        Double.MIN_VALUE, Double.MAX_VALUE };
    final Double[] array3 = new Double[] { (double) 1, Double.MIN_VALUE,
        (double) 0, (double) -1 };

    assertEquals(true, Equality.equals(array1, 0, array2, 0, 5));
    assertEquals(true, Equality.equals(array1, 0, array2, 0, 3));
    assertEquals(false, Equality.equals(array1, 0, array2, 2, 3));
    assertEquals(false, Equality.equals(array1, 0, array3, 0, 5));
    assertEquals(false, Equality.equals(array1, 0, array3, 1, 3));
    assertEquals(true, Equality.equals(array1, 2, array3, 0, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #valueEquals(java.lang.Double, java.lang.Double, double)}
   * .
   */
  @Test
  public void testDoubleObjectWithEpsilon() {
    final double epsilon = 0.00001;
    Double a = 0.0;
    Double b = 0.0;

    assertEquals(true, Equality.valueEquals(a, b, epsilon));
    b = 0.0 + epsilon;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));
    b = 0.0 - epsilon;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));
    b = -0.0;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));

    a = Double.MAX_VALUE;
    b = Double.MAX_VALUE;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));
    b = Double.MAX_VALUE + epsilon;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));
    b = Double.MAX_VALUE - epsilon;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));

    a = Double.MIN_VALUE;
    b = Double.MIN_VALUE;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));
    b = Double.MIN_VALUE + epsilon;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));
    b = Double.MIN_VALUE - epsilon;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));
    //
    // a = Double.MIN_NORMAL;
    // b = Double.MIN_NORMAL;
    // assertEquals(true, Equality.valueEquals(a, b, epsilon));
    // b = Double.MIN_NORMAL + epsilon;
    // assertEquals(true, Equality.valueEquals(a, b, epsilon));
    // b = Double.MIN_NORMAL - epsilon;
    // assertEquals(true, Equality.valueEquals(a, b, epsilon));

    a = Double.NaN;
    b = Double.NaN;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));
    b = Float.NaN + epsilon;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));
    b = Float.NaN - epsilon;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));

    a = Double.MAX_VALUE;
    b = Double.NaN;
    assertEquals(false, Equality.valueEquals(a, b, epsilon));
    b = Double.NaN + epsilon;
    assertEquals(false, Equality.valueEquals(a, b, epsilon));
    b = Double.NaN - epsilon;
    assertEquals(false, Equality.valueEquals(a, b, epsilon));

    a = Double.NaN;
    b = null;
    assertEquals(false, Equality.valueEquals(a, b, epsilon));
    a = null;
    assertEquals(true, Equality.valueEquals(a, b, epsilon));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #valueEquals(java.lang.Double[], java.lang.Double[], double)}
   * .
   */
  @Test
  public void testDoubleObjectArrayWithEpsilon() {
    final Double epsilon = 0.0000001;
    Double[] obj1 = new Double[] { 0.0, 3.1415926535768 + epsilon };
    Double[] obj2 = new Double[] { 0.0 + epsilon, 3.1415926535768 };
    final Double[] obj3 = new Double[] { -0.0, 3.1415926535768, 3.1415926535768 };

    assertEquals(true, Equality.valueEquals(obj1, obj1, epsilon));
    // NOTE: the epsilon MUST be small enough, otherwise, obj1 may not
    // equals to obj2 because of the floating-point arithmetic inaccuracy.
    assertEquals(true, Equality.valueEquals(obj1, obj2, epsilon));
    assertEquals(false, Equality.valueEquals(obj1, obj3, epsilon));
    assertEquals(false, Equality.valueEquals(obj2, obj3, epsilon));
    assertEquals(true, Equality.valueEquals(obj3, obj3, epsilon));
    obj1[0] = -0.0;
    assertEquals(true, Equality.valueEquals(obj1, obj2, epsilon));
    obj1[0] = null;
    assertEquals(false, Equality.valueEquals(obj1, obj2, epsilon));
    obj2 = null;
    assertEquals(false, Equality.valueEquals(obj1, obj2, epsilon));
    obj1 = null;
    assertEquals(true, Equality.valueEquals(obj1, obj2, epsilon));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #valueEquals(Double[], int, Double[], int, int, double)}
   * .
   */
  @Test
  public void testDoubleObjectArrayIndexLengthWithEpsilon() {
    final Double epsilon = 0.0000001;
    final Double[] array1 = new Double[] { (double) 0, (double) -1, (double) 1,
        Double.MIN_VALUE, Double.MAX_VALUE };
    final Double[] array2 = new Double[] { (double) 0, -1 + epsilon,
        (double) 1, Double.MIN_VALUE + epsilon, Double.MAX_VALUE };
    final Double[] array3 = new Double[] { 1 + epsilon,
        Double.MIN_VALUE, 0 + epsilon, (double) -1, Double.MAX_VALUE };

    assertEquals(true, Equality.valueEquals(array1, 0, array2, 0, 5, epsilon));
    assertEquals(true, Equality.valueEquals(array1, 1, array2, 1, 3, epsilon));
    assertEquals(false, Equality.valueEquals(array1, 0, array3, 0, 5, epsilon));
    assertEquals(true, Equality.valueEquals(array1, 0, array3, 2, 2, epsilon));
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.Equality#equals(E, E)}.
   */
  @Test
  public void testEnum() {
    TestEnum value1 = TestEnum.a;
    TestEnum value2 = TestEnum.a;

    assertEquals(true, Equality.equals(value1, value2));
    value2 = TestEnum.b;
    assertEquals(false, Equality.equals(value1, value2));
    value2 = null;
    assertEquals(false, Equality.equals(value1, value2));
    value1 = null;
    assertEquals(true, Equality.equals(value1, value2));
  }

  /**
   * Test method for {@link com.github.haixing_hu.lang.Equality#equals(E[], E[])}.
   */
  @Test
  public void testEnumArray() {
    TestEnum[] Array1 = { TestEnum.a, TestEnum.b };
    TestEnum[] Array2 = { TestEnum.a, TestEnum.b };
    final TestEnum[] Array3 = { TestEnum.a, TestEnum.b, TestEnum.c };

    assertEquals(true, Equality.equals(Array1, Array2));
    Array2[1] = TestEnum.c;
    assertEquals(false, Equality.equals(Array1, Array2));
    Array2 = null;
    assertEquals(false, Equality.equals(Array1, Array2));
    Array1 = null;
    assertEquals(true, Equality.equals(Array1, Array2));
    assertEquals(false, Equality.equals(Array1, Array3));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(E[], int, E[], int, int)}.
   */
  @Test
  public void testEnumArrayIndexLength() {
    final TestEnum[] Array1 = { TestEnum.a, TestEnum.b, TestEnum.c, TestEnum.d };
    final TestEnum[] Array2 = { TestEnum.a, TestEnum.b, TestEnum.c, TestEnum.d };
    final TestEnum[] Array3 = { TestEnum.a, TestEnum.b, TestEnum.c, TestEnum.e };

    assertEquals(true, Equality.equals(Array1, 0, Array2, 0, 4));
    assertEquals(false, Equality.equals(Array1, 0, Array3, 0, 4));
    assertEquals(true, Equality.equals(Array1, 1, Array2, 1, 3));
    assertEquals(true, Equality.equals(Array1, 1, Array3, 1, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(String, String)}.
   */
  @Test
  public void testString() {
    String value1 = "longlongago";
    String value2 = "longlongago";

    assertEquals(true, Equality.equals(value1, value2));
    value2 = "longago";
    assertEquals(false, Equality.equals(value1, value2));
    value2 = null;
    assertEquals(false, Equality.equals(value1, value2));
    value1 = null;
    assertEquals(true, Equality.equals(value1, value2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(String[], String[])}.
   */
  @Test
  public void testStringArray() {
    String[] array1 = { "long", "long", "ago" };
    String[] array2 = { "long", "long", "ago" };
    final String[] array3 = { "long", "ago" };

    assertEquals(true, Equality.equals(array1, array2));
    assertEquals(false, Equality.equals(array1, array3));
    array2 = null;
    assertEquals(false, Equality.equals(array1, array2));
    array1 = null;
    assertEquals(true, Equality.equals(array1, array2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(String[], int, String[], int, int)}
   * .
   */
  @Test
  public void testStringArrayIndexLength() {
    final String[] array1 = { "long", "long", "ago" };
    final String[] array2 = { "long", "long", "ago" };
    final String[] array3 = { "long", "ago", "long" };

    assertEquals(true, Equality.equals(array1, 0, array2, 0, 3));
    assertEquals(true, Equality.equals(array1, 1, array2, 1, 2));
    assertEquals(false, Equality.equals(array1, 0, array3, 0, 3));
    assertEquals(true, Equality.equals(array1, 1, array3, 0, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equalsIgnoreCase(String, String);
   */
  @Test
  public void testIgnoreCaseString() {
    String value1 = "longlongago";
    String value2 = "longlongago";

    assertEquals(true, Equality.equalsIgnoreCase(value1, value2));
    value2 = "LongLongAGO";
    assertEquals(true, Equality.equalsIgnoreCase(value1, value2));
    value2 = "longagO";
    assertEquals(false, Equality.equalsIgnoreCase(value1, value2));
    value2 = null;
    assertEquals(false, Equality.equalsIgnoreCase(value1, value2));
    value1 = null;
    assertEquals(true, Equality.equalsIgnoreCase(value1, value2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equalsIgnoreCase(String[], String[])}
   * .
   */
  @Test
  public void testIgnoreCaseStringArray() {
    String[] array1 = { "long", "long", "ago" };
    String[] array2 = { "long", "long", "ago" };
    final String[] array3 = { "lOng", "long", "AGO" };
    final String[] array4 = { "long", "ago" };

    assertEquals(true, Equality.equalsIgnoreCase(array1, array2));
    assertEquals(true, Equality.equalsIgnoreCase(array1, array3));
    assertEquals(false, Equality.equalsIgnoreCase(array1, array4));
    array2 = null;
    assertEquals(false, Equality.equalsIgnoreCase(array1, array2));
    array1 = null;
    assertEquals(true, Equality.equalsIgnoreCase(array1, array2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equalsIgnoreCase(String[], int, String[], int, int)}
   * .
   */
  @Test
  public void testIgnoreCaseStringArrayIndexLength() {
    final String[] array1 = { "long", "long", "ago" };
    final String[] array2 = { "loNg", "Long", "ago" };
    final String[] array3 = { "long", "ago", "long" };

    assertEquals(true, Equality.equalsIgnoreCase(array1, 0, array2, 0, 3));
    assertEquals(true, Equality.equalsIgnoreCase(array1, 1, array2, 1, 2));
    assertEquals(false, Equality.equalsIgnoreCase(array1, 0, array3, 0, 3));
    assertEquals(true, Equality.equalsIgnoreCase(array1, 1, array3, 0, 2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(String, String, boolean)}.
   */
  @Test
  public void testStringCase() {
    String value1 = "longlongago";
    String value2 = "longlongago";
    boolean casein = true;

    assertEquals(true, Equality.equals(value1, value2, casein));
    value2 = "LongLongAGO";
    assertEquals(true, Equality.equals(value1, value2, casein));
    value2 = "longagO";
    assertEquals(false, Equality.equals(value1, value2, casein));
    value2 = null;
    assertEquals(false, Equality.equals(value1, value2, casein));
    value1 = null;
    assertEquals(true, Equality.equals(value1, value2, casein));

    value1 = "longlongago";
    value2 = "longlongago";
    casein = false;

    assertEquals(true, Equality.equals(value1, value2, casein));
    value2 = "LongLongAGO";
    assertEquals(false, Equality.equals(value1, value2, casein));
    value2 = "longagO";
    assertEquals(false, Equality.equals(value1, value2, casein));
    value2 = null;
    assertEquals(false, Equality.equals(value1, value2, casein));
    value1 = null;
    assertEquals(true, Equality.equals(value1, value2, casein));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(String[], String[], boolean)}
   * .
   */
  @Test
  public void testStringArrayCase() {
    String[] array1 = { "long", "long", "ago" };
    String[] array2 = { "long", "long", "ago" };
    boolean casein = true;

    assertEquals(true, Equality.equals(array1, array2, casein));

    assertEquals(true, Equality.equals(array1, array2, casein));
    array2 = new String[] { "long", "ago" };
    assertEquals(false, Equality.equals(array1, array2, casein));
    array2 = null;
    assertEquals(false, Equality.equals(array1, array2, casein));
    array1 = null;
    assertEquals(true, Equality.equals(array1, array2, casein));

    array1 = new String[] { "long", "long", "ago" };
    array2 = new String[] { "long", "long", "ago" };
    casein = false;

    assertEquals(true, Equality.equals(array1, array2, casein));
    array2 = new String[] { "lOng", "long", "AGO" };
    assertEquals(false, Equality.equals(array1, array2, casein));
    array2 = new String[] { "long", "ago" };
    assertEquals(false, Equality.equals(array1, array2, casein));
    array2 = null;
    assertEquals(false, Equality.equals(array1, array2, casein));
    array1 = null;
    assertEquals(true, Equality.equals(array1, array2, casein));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(String[], int, String[], int, int, boolean)}
   * .
   */
  @Test
  public void testStringArrayIndexLengthCase() {
    String[] array1 = { "long", "long", "ago" };
    String[] array2 = { "loNg", "Long", "ago" };
    String[] array3 = { "long", "ago", "long" };
    boolean casein = true;

    assertEquals(true, Equality.equals(array1, 0, array2, 0, 3, casein));
    assertEquals(true, Equality.equals(array1, 1, array2, 1, 2, casein));
    assertEquals(false, Equality.equals(array1, 0, array3, 0, 3, casein));
    assertEquals(true, Equality.equals(array1, 1, array3, 0, 2, casein));

    array1 = new String[] { "long", "long", "ago" };
    array2 = new String[] { "loNg", "Long", "ago" };
    array3 = new String[] { "long", "ago", "long" };
    casein = false;

    assertEquals(false, Equality.equals(array1, 0, array2, 0, 3, casein));
    assertEquals(false, Equality.equals(array1, 1, array2, 1, 2, casein));
    assertEquals(false, Equality.equals(array1, 0, array3, 0, 3, casein));
    assertEquals(true, Equality.equals(array1, 1, array3, 0, 2, casein));
  }

  /**
   * Test method for{@link com.github.haixing_hu.lang.Equality #equals(Class<?>,
   * Class<?>)}.
   */
  @Test
  public void testClass() {
    final class TestClassA {
    }
    final class TestClassB {
    }
    assertEquals(true, Equality.equals(TestClassA.class, TestClassA.class));
    assertEquals(false, Equality.equals(TestClassA.class, TestClassB.class));
    assertEquals(false, Equality.equals((TestClassA) null, TestClassB.class));
    assertEquals(true, Equality.equals((TestClassA) null, (TestClassA) null));
    assertEquals(true, Equality.equals((TestClassA) null, (TestClassB) null));
  }

  /**
   * Test method for{@link com.github.haixing_hu.lang.Equality #equals(Class<?>[],
   * Class<?>[])}.
   */
  @Test
  public void testClassArray() {
    final class TestClassA {
    }
    final class TestClassB {
    }
    Class<?>[] array1 = new Class<?>[] { TestClassA.class, TestClassA.class };
    Class<?>[] array2 = new Class<?>[] { TestClassB.class, TestClassB.class };
    final Class<?>[] array3 = new Class<?>[] { TestClassA.class, TestClassA.class };

    assertEquals(false, Equality.equals(array1, array2));
    assertEquals(true, Equality.equals(array1, array3));
    array1 = new Class<?>[] { null };
    assertEquals(false, Equality.equals(array1, array2));
    array2 = new Class<?>[] { null };
    assertEquals(true, Equality.equals(array1, array2));
  }

  /**
   * Test method for{@link com.github.haixing_hu.lang.Equality #equals(Class<?>[],
   * int, Class<?>[], int, int)}.
   */
  @Test
  public void testClassArrayIndexLength() {
    final class TestClassA {
    }
    final class TestClassB {
    }
    final Class<?>[] array1 = new Class<?>[] { TestClassA.class, TestClassA.class };
    final Class<?>[] array2 = new Class<?>[] { TestClassB.class, TestClassB.class };
    final Class<?>[] array3 = new Class<?>[] { TestClassA.class, TestClassA.class };

    assertEquals(false, Equality.equals(array1, 0, array2, 0, 2));
    assertEquals(true, Equality.equals(array1, 0, array3, 0, 2));
    assertEquals(true, Equality.equals(array1, 1, array3, 1, 1));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(java.lang.Object, java.lang.Object)}
   * .
   */
  @Test
  public void testObject() {
    TestObject o1 = new TestObject(4);
    TestObject o2 = new TestObject(5);
    assertEquals(true, Equality.equals(o1, o1));
    assertEquals(false, Equality.equals(o1, o2));
    o2.setA(4);
    assertEquals(true, Equality.equals(o1, o2));
    assertEquals(false, Equality.equals(o1, this));
    assertEquals(false, Equality.equals(o1, null));
    assertEquals(false, Equality.equals(null, o2));
    o1 = null;
    o2 = null;
    assertEquals(true, Equality.equals(o1, o2));

    final boolean[] booleanarray1 = { true, false, false, true };
    final boolean[] booleanarray2 = { true, false, false, true };
    final boolean[] booleanarray3 = { false, true, true };
    assertEquals(true, Equality.equals(booleanarray1, (Object) booleanarray2));
    assertEquals(false, Equality.equals(booleanarray3, (Object) booleanarray2));

    final char[] chararray1 = { (char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE };
    final char[] chararray2 = { (char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE };
    final char[] chararray3 = { (char) 0, Character.MAX_VALUE };

    assertEquals(true, Equality.equals(chararray1, (Object) chararray2));
    assertEquals(false, Equality.equals(chararray3, (Object) chararray2));

    final byte[] bytearray1 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE };
    final byte[] bytearray2 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE };
    final byte[] bytearray3 = { (byte) 0, Byte.MAX_VALUE };
    assertEquals(true, Equality.equals(bytearray1, (Object) bytearray2));
    assertEquals(false, Equality.equals(bytearray3, (Object) bytearray2));

    final short[] shortarray1 = { (short) 0, (short) -1, (short) 1, Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2), Short.MIN_VALUE,
        (short) (Short.MAX_VALUE / 2) };
    final short[] shortarray2 = { (short) 0, (short) -1, (short) 1, Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2), Short.MIN_VALUE,
        (short) (Short.MAX_VALUE / 2) };
    final short[] shortarray3 = { (short) 0, Short.MAX_VALUE };
    assertEquals(true, Equality.equals(shortarray1, (Object) shortarray2));
    assertEquals(false, Equality.equals(shortarray3, (Object) shortarray2));

    final int[] intarray1 = { 0, -1, 1, Integer.MIN_VALUE,
        Integer.MIN_VALUE / 2, Integer.MAX_VALUE / 2,
        Integer.MAX_VALUE };
    final int[] intarray2 = { 0, -1, 1, Integer.MIN_VALUE,
        Integer.MIN_VALUE / 2, Integer.MAX_VALUE / 2,
        Integer.MAX_VALUE };
    final int[] intarray3 = { 0, Integer.MAX_VALUE };
    assertEquals(true, Equality.equals(intarray1, (Object) intarray2));
    assertEquals(false, Equality.equals(intarray3, (Object) intarray2));

    final long[] longarray1 = { 0, -1, 1, Long.MIN_VALUE,
        Long.MIN_VALUE / 2, Long.MAX_VALUE / 2,
        Long.MAX_VALUE };
    final long[] longarray2 = { 0, -1, 1, Long.MIN_VALUE,
        Long.MIN_VALUE / 2, Long.MAX_VALUE / 2,
        Long.MAX_VALUE };
    final long[] longarray3 = { 0, Long.MAX_VALUE };
    assertEquals(true, Equality.equals(longarray1, (Object) longarray2));
    assertEquals(false, Equality.equals(longarray3, (Object) longarray2));

    final float[] floatarray1 = { 0, -1, 1, Float.MIN_VALUE,
        Float.MIN_VALUE / 2, Float.MAX_VALUE / 2,
        Float.MAX_VALUE };
    final float[] floatarray2 = { 0, -1, 1, Float.MIN_VALUE,
        Float.MIN_VALUE / 2, Float.MAX_VALUE / 2,
        Float.MAX_VALUE };
    final float[] floatarray3 = { 0, Float.MAX_VALUE };
    assertEquals(true, Equality.equals(floatarray1, (Object) floatarray2));
    assertEquals(false, Equality.equals(floatarray3, (Object) floatarray2));

    final double[] doublearray1 = { 0, -1, 1,
        Double.MIN_VALUE, Double.MIN_VALUE / 2,
        Double.MAX_VALUE / 2, Double.MAX_VALUE };
    final double[] doublearray2 = { 0, -1, 1,
        Double.MIN_VALUE, Double.MIN_VALUE / 2,
        Double.MAX_VALUE / 2, Double.MAX_VALUE };
    final double[] doublearray3 = { 0, Double.MAX_VALUE };
    assertEquals(true, Equality.equals(doublearray1, (Object) doublearray2));
    assertEquals(false, Equality.equals(doublearray3, (Object) doublearray2));

    final String[] stringarray1 = { "long", "long", "ago" };
    final String[] stringarray2 = { "long", "long", "ago" };
    final String[] stringarray3 = { "long", "time", "no", "see" };
    assertEquals(true, Equality.equals(stringarray1, (Object) stringarray2));
    assertEquals(false, Equality.equals(stringarray3, (Object) stringarray2));

    final Boolean[] booleanobjectarray1 = { true, false, false, true };
    final Boolean[] booleanobjectarray2 = { true, false, false, true };
    final Boolean[] booleanobjectarray3 = { false, true, true };
    assertEquals(true,
        Equality.equals(booleanobjectarray1, (Object) booleanobjectarray2));
    assertEquals(false,
        Equality.equals(booleanobjectarray3, (Object) booleanobjectarray2));

    final Character[] charobjectarray1 = { (char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE };
    final Character[] charobjectarray2 = { (char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE };
    final Character[] charobjectarray3 = { (char) 0, Character.MAX_VALUE };
    assertEquals(true,
        Equality.equals(charobjectarray1, (Object) charobjectarray2));
    assertEquals(false,
        Equality.equals(charobjectarray3, (Object) charobjectarray2));

    final Byte[] byteobjectarray1 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE };
    final Byte[] byteobjectarray2 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE };
    final Byte[] byteobjectarray3 = { (byte) 0, Byte.MAX_VALUE };
    assertEquals(true,
        Equality.equals(byteobjectarray1, (Object) byteobjectarray2));
    assertEquals(false,
        Equality.equals(byteobjectarray3, (Object) byteobjectarray2));

    final Short[] shortobjectarray1 = { (short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2),
        (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE };
    final Short[] shortobjectarray2 = { (short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2),
        (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE };
    final Short[] shortobjectarray3 = { (short) 0, Short.MAX_VALUE };
    assertEquals(true,
        Equality.equals(shortobjectarray1, (Object) shortobjectarray2));
    assertEquals(false,
        Equality.equals(shortobjectarray3, (Object) shortobjectarray2));

    final Integer[] integerarray1 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE };
    final Integer[] integerarray2 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE };
    final Integer[] integerarray3 = { (int) 0, Integer.MAX_VALUE };
    assertEquals(true, Equality.equals(integerarray1, (Object) integerarray2));
    assertEquals(false, Equality.equals(integerarray3, (Object) integerarray2));

    final Long[] longobjectarray1 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MAX_VALUE };
    final Long[] longobjectarray2 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MAX_VALUE };
    final Long[] longobjectarray3 = { (long) 0, Long.MAX_VALUE };
    assertEquals(true,
        Equality.equals(longobjectarray1, (Object) longobjectarray2));
    assertEquals(false,
        Equality.equals(longobjectarray3, (Object) longobjectarray2));

    final Float[] floatobjectarray1 = { (float) 0, (float) -1, (float) 1,
        Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2),
        (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE };
    final Float[] floatobjectarray2 = { (float) 0, (float) -1, (float) 1,
        Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2),
        (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE };
    final Float[] floatobjectarray3 = { (float) 0, Float.MAX_VALUE };
    assertEquals(true,
        Equality.equals(floatobjectarray1, (Object) floatobjectarray2));
    assertEquals(false,
        Equality.equals(floatobjectarray3, (Object) floatobjectarray2));

    final Double[] doubleobjectarray1 = { (double) 0, (double) -1, (double) 1,
        (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE,
        (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE };
    final Double[] doubleobjectarray2 = { (double) 0, (double) -1, (double) 1,
        (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE,
        (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE };
    final Double[] doubleobjectarray3 = { (double) 0, Double.MAX_VALUE };
    assertEquals(true,
        Equality.equals(doubleobjectarray1, (Object) doubleobjectarray2));
    assertEquals(false,
        Equality.equals(doubleobjectarray3, (Object) doubleobjectarray2));

    final class TestClassA {
    }
    final class TestClassB {
    }
    final Class<?>[] classarray1 = new Class<?>[] { TestClassA.class,
        TestClassA.class };
    final Class<?>[] classarray2 = new Class<?>[] { TestClassA.class,
        TestClassA.class };
    final Class<?>[] classarray3 = new Class<?>[] { TestClassB.class,
        TestClassB.class };
    assertEquals(true, Equality.equals(classarray1, (Object) classarray2));
    assertEquals(false, Equality.equals(classarray3, (Object) classarray2));

    final Date datea = new Date();
    datea.setTime(123456);
    final Date dateb = new Date();
    dateb.setTime(7891011);
    final Date datec = new Date();
    datec.setTime(654321);
    final Date[] datearray1 = { datea, dateb, datec };
    final Date[] datearray2 = { datea, dateb, datec };
    final Date[] datearray3 = { datec, datea, dateb };
    assertEquals(true, Equality.equals(datearray1, (Object) datearray2));
    assertEquals(false, Equality.equals(datearray3, (Object) datearray2));

    final BigInteger[] bigintegerarray1 = { new BigInteger(bytearray1),
        new BigInteger(bytearray3) };
    final BigInteger[] bigintegerarray2 = { new BigInteger(bytearray2),
        new BigInteger(bytearray3) };
    final BigInteger[] bigintegerarray3 = { new BigInteger(bytearray3) };
    assertEquals(true,
        Equality.equals(bigintegerarray1, (Object) bigintegerarray2));
    assertEquals(false,
        Equality.equals(bigintegerarray3, (Object) bigintegerarray2));

    final BigDecimal[] bigdecimalarray1 = { new BigDecimal(Integer.MIN_VALUE),
        new BigDecimal(Integer.MAX_VALUE) };
    final BigDecimal[] bigdecimalarray2 = { new BigDecimal(Integer.MIN_VALUE),
        new BigDecimal(Integer.MAX_VALUE) };
    final BigDecimal[] bigdecimalarray3 = { new BigDecimal(0), new BigDecimal(-1) };
    assertEquals(true,
        Equality.equals(bigdecimalarray1, (Object) bigdecimalarray2));
    assertEquals(false,
        Equality.equals(bigdecimalarray3, (Object) bigdecimalarray2));

    final Boolean[][] objectarray1 = { { true, false }, { true, true },
        { false, false, true } };
    final Boolean[][] objectarray2 = { { true, false }, { true, true },
        { false, false, true } };
    final Boolean[][] objectarray3 = { { true, false }, { true, true } };
    assertEquals(true, Equality.equals(objectarray1, (Object) objectarray2));
    assertEquals(false, Equality.equals(objectarray3, (Object) objectarray2));

    final ArrayList<Character> col1 = new ArrayList<Character>();
    col1.add('a');
    col1.add('b');
    col1.add('c');
    final ArrayList<Character> col2 = new ArrayList<Character>();
    col2.add('a');
    col2.add('b');
    col2.add('c');
    final ArrayList<Character> col3 = new ArrayList<Character>();
    col3.add('b');
    col3.add('c');
    assertEquals(true, Equality.equals(col1, (Object) col2));
    assertEquals(false, Equality.equals(col3, (Object) col2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equals(java.lang.Object[], java.lang.Object[])}
   * .
   */
  @Test
  public void testObjectArray() {
    TestObject[] obj1 = new TestObject[3];
    obj1[0] = new TestObject(4);
    obj1[1] = new TestObject(5);
    obj1[2] = null;
    TestObject[] obj2 = new TestObject[3];
    obj2[0] = new TestObject(4);
    obj2[1] = new TestObject(5);
    obj2[2] = null;

    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj2, obj2));
    assertEquals(true, Equality.equals(obj1, obj2));
    obj1[1].setA(6);
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1[1].setA(5);
    assertEquals(true, Equality.equals(obj1, obj2));
    obj1[2] = obj1[1];
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1[2] = null;
    assertEquals(true, Equality.equals(obj1, obj2));
    obj2 = null;
    assertEquals(false, Equality.equals(obj1, obj2));
    obj1 = null;
    assertEquals(true, Equality.equals(obj1, obj2));

    final boolean[][] booleanarray1 = { { true }, { false, false }, { true } };
    final boolean[][] booleanarray2 = { { true }, { false, false }, { true } };
    final boolean[][] booleanarray3 = { { false, true }, { true } };
    assertEquals(true, Equality.equals(booleanarray1, (Object) booleanarray2));
    assertEquals(false, Equality.equals(booleanarray3, (Object) booleanarray2));

    final char[][] chararray1 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    final char[][] chararray2 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    final char[][] chararray3 = { { (char) 0, Character.MAX_VALUE } };
    assertEquals(true, Equality.equals(chararray1, (Object) chararray2));
    assertEquals(false, Equality.equals(chararray3, (Object) chararray2));

    final byte[][] bytearray1 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    final byte[][] bytearray2 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    final byte[][] bytearray3 = { { (byte) 0, Byte.MAX_VALUE } };
    assertEquals(true, Equality.equals(bytearray1, (Object) bytearray2));
    assertEquals(false, Equality.equals(bytearray3, (Object) bytearray2));

    final short[][] shortarray1 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MAX_VALUE, (short) (Short.MIN_VALUE / 2) },
        { Short.MIN_VALUE, (short) (Short.MAX_VALUE / 2) } };
    final short[][] shortarray2 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MAX_VALUE, (short) (Short.MIN_VALUE / 2) },
        { Short.MIN_VALUE, (short) (Short.MAX_VALUE / 2) } };
    final short[][] shortarray3 = { { (short) 0, Short.MAX_VALUE } };
    assertEquals(true, Equality.equals(shortarray1, (Object) shortarray2));
    assertEquals(false, Equality.equals(shortarray3, (Object) shortarray2));

    final int[][] intarray1 = { { 0, -1, 1 },
        { Integer.MIN_VALUE, Integer.MIN_VALUE / 2 },
        { Integer.MAX_VALUE / 2, Integer.MAX_VALUE } };
    final int[][] intarray2 = { { 0, -1, 1 },
        { Integer.MIN_VALUE, Integer.MIN_VALUE / 2 },
        { Integer.MAX_VALUE / 2, Integer.MAX_VALUE } };
    final int[][] intarray3 = { { 0, Integer.MAX_VALUE } };
    assertEquals(true, Equality.equals(intarray1, (Object) intarray2));
    assertEquals(false, Equality.equals(intarray3, (Object) intarray2));

    final long[][] longarray1 = { { 0, -1, 1 },
        { Long.MIN_VALUE, Long.MIN_VALUE / 2 },
        { Long.MAX_VALUE / 2, Long.MAX_VALUE } };
    final long[][] longarray2 = { { 0, -1, 1 },
        { Long.MIN_VALUE, Long.MIN_VALUE / 2 },
        { Long.MAX_VALUE / 2, Long.MAX_VALUE } };
    final long[][] longarray3 = { { 0, Long.MAX_VALUE } };
    assertEquals(true, Equality.equals(longarray1, (Object) longarray2));
    assertEquals(false, Equality.equals(longarray3, (Object) longarray2));

    final float[][] floatarray1 = { { 0, -1, 1 },
        { Float.MIN_VALUE, Float.MIN_VALUE / 2 },
        { Float.MAX_VALUE / 2, Float.MAX_VALUE } };
    final float[][] floatarray2 = { { 0, -1, 1 },
        { Float.MIN_VALUE, Float.MIN_VALUE / 2 },
        { Float.MAX_VALUE / 2, Float.MAX_VALUE } };
    final float[][] floatarray3 = { { 0, Float.MAX_VALUE } };
    assertEquals(true, Equality.equals(floatarray1, (Object) floatarray2));
    assertEquals(false, Equality.equals(floatarray3, (Object) floatarray2));

    final double[][] doublearray1 = { { 0, -1, 1 },
        { Double.MIN_VALUE, Double.MIN_VALUE / 2 },
        { Double.MAX_VALUE / 2, Double.MAX_VALUE } };
    final double[][] doublearray2 = { { 0, -1, 1 },
        { Double.MIN_VALUE, Double.MIN_VALUE / 2 },
        { Double.MAX_VALUE / 2, Double.MAX_VALUE } };
    final double[][] doublearray3 = { { 0, Double.MAX_VALUE } };
    assertEquals(true, Equality.equals(doublearray1, (Object) doublearray2));
    assertEquals(false, Equality.equals(doublearray3, (Object) doublearray2));

    final String[][] stringarray1 = { { "long", "long" }, { "ago" } };
    final String[][] stringarray2 = { { "long", "long" }, { "ago" } };
    final String[][] stringarray3 = { { "long", "time" }, { "no", "see" } };
    assertEquals(true, Equality.equals(stringarray1, (Object) stringarray2));
    assertEquals(false, Equality.equals(stringarray3, (Object) stringarray2));

    final Boolean[][] booleanobjectarray1 = { { true, false }, { false }, { true } };
    final Boolean[][] booleanobjectarray2 = { { true, false }, { false }, { true } };
    final Boolean[][] booleanobjectarray3 = { { false, true }, { true } };
    assertEquals(true,
        Equality.equals(booleanobjectarray1, (Object) booleanobjectarray2));
    assertEquals(false,
        Equality.equals(booleanobjectarray3, (Object) booleanobjectarray2));

    final Character[][] charobjectarray1 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    final Character[][] charobjectarray2 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    final Character[][] charobjectarray3 = { { (char) 0, Character.MAX_VALUE } };
    assertEquals(true,
        Equality.equals(charobjectarray1, (Object) charobjectarray2));
    assertEquals(false,
        Equality.equals(charobjectarray3, (Object) charobjectarray2));

    final Byte[][] byteobjectarray1 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    final Byte[][] byteobjectarray2 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    final Byte[][] byteobjectarray3 = { { (byte) 0, Byte.MAX_VALUE } };
    assertEquals(true,
        Equality.equals(byteobjectarray1, (Object) byteobjectarray2));
    assertEquals(false,
        Equality.equals(byteobjectarray3, (Object) byteobjectarray2));

    final Short[][] shortobjectarray1 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
    final Short[][] shortobjectarray2 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
    final Short[][] shortobjectarray3 = { { (short) 0, Short.MAX_VALUE } };
    assertEquals(true,
        Equality.equals(shortobjectarray1, (Object) shortobjectarray2));
    assertEquals(false,
        Equality.equals(shortobjectarray3, (Object) shortobjectarray2));

    final Integer[][] integerarray1 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) },
        { (int) (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
    final Integer[][] integerarray2 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) },
        { (int) (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
    final Integer[][] integerarray3 = { { (int) 0, Integer.MAX_VALUE } };
    assertEquals(true, Equality.equals(integerarray1, (Object) integerarray2));
    assertEquals(false, Equality.equals(integerarray3, (Object) integerarray2));

    final Long[][] longobjectarray1 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) },
        { (long) (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
    final Long[][] longobjectarray2 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) },
        { (long) (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
    final Long[][] longobjectarray3 = { { (long) 0, Long.MAX_VALUE } };
    assertEquals(true,
        Equality.equals(longobjectarray1, (Object) longobjectarray2));
    assertEquals(false,
        Equality.equals(longobjectarray3, (Object) longobjectarray2));

    final Float[][] floatobjectarray1 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) },
        { (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
    final Float[][] floatobjectarray2 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) },
        { (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
    final Float[][] floatobjectarray3 = { { (float) 0, Float.MAX_VALUE } };
    assertEquals(true,
        Equality.equals(floatobjectarray1, (Object) floatobjectarray2));
    assertEquals(false,
        Equality.equals(floatobjectarray3, (Object) floatobjectarray2));

    final Double[][] doubleobjectarray1 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE },
        { (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
    final Double[][] doubleobjectarray2 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE },
        { (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
    final Double[][] doubleobjectarray3 = { { (double) 0, Double.MAX_VALUE } };
    assertEquals(true,
        Equality.equals(doubleobjectarray1, (Object) doubleobjectarray2));
    assertEquals(false,
        Equality.equals(doubleobjectarray3, (Object) doubleobjectarray2));

    final class TestClassA {
    }
    final class TestClassB {
    }
    final Class<?>[][] classarray1 = { { TestClassA.class }, { TestClassA.class } };
    final Class<?>[][] classarray2 = { { TestClassA.class }, { TestClassA.class } };
    final Class<?>[][] classarray3 = { { TestClassB.class, TestClassA.class } };
    assertEquals(true, Equality.equals(classarray1, classarray2));
    assertEquals(false, Equality.equals(classarray3, classarray2));

    final Date datea = new Date();
    datea.setTime(123456);
    final Date dateb = new Date();
    dateb.setTime(7891011);
    final Date datec = new Date();
    datec.setTime(654321);
    final Date[][] datearray1 = { { datea, dateb }, { datec } };
    final Date[][] datearray2 = { { datea, dateb }, { datec } };
    final Date[][] datearray3 = { { datec }, { datea, dateb } };
    assertEquals(true, Equality.equals(datearray1, (Object) datearray2));
    assertEquals(false, Equality.equals(datearray3, (Object) datearray2));

    final BigInteger[][] bigintegerarray1 = { { new BigInteger(bytearray1[0]),
        new BigInteger(bytearray3[0]) } };
    final BigInteger[][] bigintegerarray2 = { { new BigInteger(bytearray2[0]),
        new BigInteger(bytearray3[0]) } };
    final BigInteger[][] bigintegerarray3 = { { new BigInteger(bytearray3[0]) } };
    assertEquals(true,
        Equality.equals(bigintegerarray1, (Object) bigintegerarray2));
    assertEquals(false,
        Equality.equals(bigintegerarray3, (Object) bigintegerarray2));

    final BigDecimal[][] bigdecimalarray1 = { { new BigDecimal(Integer.MIN_VALUE) },
        { new BigDecimal(Integer.MAX_VALUE) } };
    final BigDecimal[][] bigdecimalarray2 = { { new BigDecimal(Integer.MIN_VALUE) },
        { new BigDecimal(Integer.MAX_VALUE) } };
    final BigDecimal[][] bigdecimalarray3 = { { new BigDecimal(0), new BigDecimal(-1) } };
    assertEquals(true,
        Equality.equals(bigdecimalarray1, (Object) bigdecimalarray2));
    assertEquals(false,
        Equality.equals(bigdecimalarray3, (Object) bigdecimalarray2));

    final Boolean[][][][] objectarray1 = { { { { true, false }, { true, true } } },
        { { { false, false }, { true } } } };
    final Boolean[][][][] objectarray2 = { { { { true, false }, { true, true } } },
        { { { false, false }, { true } } } };
    final Boolean[][][][] objectarray3 = { { { { true }, { false } } },
        { { { true, true } } } };
    assertEquals(true, Equality.equals(objectarray1, (Object) objectarray2));
    assertEquals(false, Equality.equals(objectarray3, (Object) objectarray2));

    final ArrayList<Character> arraylist1 = new ArrayList<Character>();
    final ArrayList<Character> arraylist2 = new ArrayList<Character>();
    final Object[] col1 = { arraylist1, arraylist2 };
    arraylist1.add('a');
    arraylist2.add('b');
    arraylist1.add('c');
    final ArrayList<Character> arraylist3 = new ArrayList<Character>();
    final ArrayList<Character> arraylist4 = new ArrayList<Character>();
    final Object[] col2 = { arraylist3, arraylist4 };
    arraylist3.add('a');
    arraylist4.add('b');
    arraylist3.add('c');
    final ArrayList<Character> arraylist5 = new ArrayList<Character>();
    final Object[] col3 = { arraylist5 };
    arraylist5.add('a');
    assertEquals(true, Equality.equals(col1, col2));
    assertEquals(false, Equality.equals(col3, col2));
  }

  @Test
  public void testObjectArrayIndexLength() {
    final boolean[][] booleanarray1 = { { true }, { false, false }, { true } };
    final boolean[][] booleanarray2 = { { true }, { false, false }, { true } };
    final boolean[][] booleanarray3 = { { false, true }, { true } };
    assertEquals(true, Equality.equals(booleanarray1, 0,
        booleanarray2, 0, booleanarray2.length));
    assertEquals(false, Equality.equals(booleanarray3, 0,
        booleanarray2, 0, booleanarray2.length));
    assertEquals(false,
        Equality.equals(booleanarray1, 1, booleanarray2, 0, 1));
    assertEquals(true,
        Equality.equals(booleanarray1, 0, booleanarray2, 2, 1));

    final char[][] chararray1 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    final char[][] chararray2 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    final char[][] chararray3 = { { (char) 0, Character.MAX_VALUE } };
    assertEquals(true, Equality.equals(chararray1, 0, chararray2, 0,
        chararray2.length));
    assertEquals(false, Equality.equals(chararray3, 0, chararray2,
        0, chararray2.length));
    assertEquals(false,
        Equality.equals(chararray3, 0, chararray2, 2, 1));
    assertEquals(true,
        Equality.equals(chararray1, 1, chararray2, 1, 1));

    final byte[][] bytearray1 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    final byte[][] bytearray2 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    final byte[][] bytearray3 = { { (byte) 0, Byte.MAX_VALUE } };
    assertEquals(true, Equality.equals(bytearray1, 0, bytearray2, 0,
        bytearray2.length));
    assertEquals(false, Equality.equals(bytearray3, 0, bytearray2,
        0, bytearray2.length));
    assertEquals(false,
        Equality.equals(bytearray3, 0, bytearray2, 2, 1));
    assertEquals(true,
        Equality.equals(bytearray1, 1, bytearray2, 1, 1));

    final short[][] shortarray1 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MAX_VALUE, (short) (Short.MIN_VALUE / 2) },
        { Short.MIN_VALUE, (short) (Short.MAX_VALUE / 2) } };
    final short[][] shortarray2 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MAX_VALUE, (short) (Short.MIN_VALUE / 2) },
        { Short.MIN_VALUE, (short) (Short.MAX_VALUE / 2) } };
    final short[][] shortarray3 = { { (short) 0, Short.MAX_VALUE } };
    assertEquals(true, Equality.equals(shortarray1, 0, shortarray2,
        0, shortarray2.length));
    assertEquals(false, Equality.equals(shortarray3, 0, shortarray2,
        0, shortarray2.length));
    assertEquals(false,
        Equality.equals(shortarray3, 0, shortarray2, 2, 1));
    assertEquals(true,
        Equality.equals(shortarray1, 1, shortarray2, 1, 1));

    final int[][] intarray1 = { { 0, -1, 1 },
        { Integer.MIN_VALUE, Integer.MIN_VALUE / 2 },
        { Integer.MAX_VALUE / 2, Integer.MAX_VALUE } };
    final int[][] intarray2 = { { 0, -1, 1 },
        { Integer.MIN_VALUE, Integer.MIN_VALUE / 2 },
        { Integer.MAX_VALUE / 2, Integer.MAX_VALUE } };
    final int[][] intarray3 = { { 0, Integer.MAX_VALUE } };
    assertEquals(
        true,
        Equality.equals(intarray1, 0, intarray2, 0, intarray2.length));
    assertEquals(
        false,
        Equality.equals(intarray3, 0, intarray2, 0, intarray2.length));
    assertEquals(false,
        Equality.equals(intarray3, 0, intarray2, 2, 1));
    assertEquals(true,
        Equality.equals(intarray1, 1, intarray2, 1, 1));

    final long[][] longarray1 = { { 0, -1, 1 },
        { Long.MIN_VALUE, Long.MIN_VALUE / 2 },
        { Long.MAX_VALUE / 2, Long.MAX_VALUE } };
    final long[][] longarray2 = { { 0, -1, 1 },
        { Long.MIN_VALUE, Long.MIN_VALUE / 2 },
        { Long.MAX_VALUE / 2, Long.MAX_VALUE } };
    final long[][] longarray3 = { { 0, Long.MAX_VALUE } };
    assertEquals(true, Equality.equals(longarray1, 0, longarray2, 0,
        longarray2.length));
    assertEquals(false, Equality.equals(longarray3, 0, longarray2,
        0, longarray2.length));
    assertEquals(false,
        Equality.equals(longarray3, 0, longarray2, 2, 1));
    assertEquals(true,
        Equality.equals(longarray1, 1, longarray2, 1, 1));

    final float[][] floatarray1 = { { 0, -1, 1 },
        { Float.MIN_VALUE, Float.MIN_VALUE / 2 },
        { Float.MAX_VALUE / 2, Float.MAX_VALUE } };
    final float[][] floatarray2 = { { 0, -1, 1 },
        { Float.MIN_VALUE, Float.MIN_VALUE / 2 },
        { Float.MAX_VALUE / 2, Float.MAX_VALUE } };
    final float[][] floatarray3 = { { 0, Float.MAX_VALUE } };
    assertEquals(true, Equality.equals(floatarray1, 0, floatarray2,
        0, floatarray2.length));
    assertEquals(false, Equality.equals(floatarray3, 0, floatarray2,
        0, floatarray2.length));
    assertEquals(false,
        Equality.equals(floatarray3, 0, floatarray2, 2, 1));
    assertEquals(true,
        Equality.equals(floatarray1, 1, floatarray2, 1, 1));

    final double[][] doublearray1 = { { 0, -1, 1 },
        { Double.MIN_VALUE, Double.MIN_VALUE / 2 },
        { Double.MAX_VALUE / 2, Double.MAX_VALUE } };
    final double[][] doublearray2 = { { 0, -1, 1 },
        { Double.MIN_VALUE, Double.MIN_VALUE / 2 },
        { Double.MAX_VALUE / 2, Double.MAX_VALUE } };
    final double[][] doublearray3 = { { 0, Double.MAX_VALUE } };
    assertEquals(true, Equality.equals(doublearray1, 0,
        doublearray2, 0, doublearray2.length));
    assertEquals(false, Equality.equals(doublearray3, 0,
        doublearray2, 0, doublearray2.length));
    assertEquals(false,
        Equality.equals(doublearray3, 0, doublearray2, 2, 1));
    assertEquals(true,
        Equality.equals(doublearray1, 1, doublearray2, 1, 1));

    final String[][] stringarray1 = { { "long", "long" }, { "ago" } };
    final String[][] stringarray2 = { { "long", "long" }, { "ago" } };
    final String[][] stringarray3 = { { "long", "time" }, { "no", "see" } };
    assertEquals(true, Equality.equals(stringarray1, 0,
        stringarray2, 0, stringarray2.length));
    assertEquals(false, Equality.equals(stringarray3, 0,
        stringarray2, 0, stringarray2.length));
    assertEquals(false,
        Equality.equals(stringarray3, 0, stringarray2, 1, 1));
    assertEquals(true,
        Equality.equals(stringarray1, 1, stringarray2, 1, 1));

    final Boolean[][] booleanobjectarray1 = { { true, false }, { false }, { true } };
    final Boolean[][] booleanobjectarray2 = { { true, false }, { false }, { true } };
    final Boolean[][] booleanobjectarray3 = { { false, true }, { true } };
    assertEquals(true, Equality.equals(booleanobjectarray1, 0,
        booleanobjectarray2, 0, booleanobjectarray2.length));
    assertEquals(false, Equality.equals(booleanobjectarray3, 0,
        booleanobjectarray2, 0, booleanobjectarray2.length));
    assertEquals(false, Equality.equals(booleanobjectarray3, 0,
        booleanobjectarray2, 2, 1));
    assertEquals(true, Equality.equals(booleanobjectarray1, 1,
        booleanobjectarray2, 1, 1));

    final Character[][] charobjectarray1 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    final Character[][] charobjectarray2 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    final Character[][] charobjectarray3 = { { (char) 0, Character.MAX_VALUE } };
    assertEquals(true, Equality.equals(charobjectarray1, 0,
        charobjectarray2, 0, charobjectarray2.length));
    assertEquals(false, Equality.equals(charobjectarray3, 0,
        charobjectarray2, 0, charobjectarray2.length));
    assertEquals(false,
        Equality.equals(charobjectarray3, 0, charobjectarray2, 2, 1));
    assertEquals(true,
        Equality.equals(charobjectarray1, 1, charobjectarray2, 1, 1));

    final Byte[][] byteobjectarray1 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    final Byte[][] byteobjectarray2 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    final Byte[][] byteobjectarray3 = { { (byte) 0, Byte.MAX_VALUE } };
    assertEquals(true, Equality.equals(byteobjectarray1, 0,
        byteobjectarray2, 0, byteobjectarray2.length));
    assertEquals(false, Equality.equals(byteobjectarray3, 0,
        byteobjectarray2, 0, byteobjectarray2.length));
    assertEquals(false,
        Equality.equals(byteobjectarray3, 0, byteobjectarray2, 2, 1));
    assertEquals(true,
        Equality.equals(byteobjectarray1, 1, byteobjectarray2, 1, 1));

    final Short[][] shortobjectarray1 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
    final Short[][] shortobjectarray2 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
    final Short[][] shortobjectarray3 = { { (short) 0, Short.MAX_VALUE } };
    assertEquals(true, Equality.equals(shortobjectarray1, 0,
        shortobjectarray2, 0, shortobjectarray2.length));
    assertEquals(false, Equality.equals(shortobjectarray3, 0,
        shortobjectarray2, 0, shortobjectarray2.length));
    assertEquals(false, Equality.equals(shortobjectarray3, 0,
        shortobjectarray2, 2, 1));
    assertEquals(true, Equality.equals(shortobjectarray1, 1,
        shortobjectarray2, 1, 1));

    final Integer[][] integerarray1 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) },
        { (int) (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
    final Integer[][] integerarray2 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) },
        { (int) (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
    final Integer[][] integerarray3 = { { (int) 0, Integer.MAX_VALUE } };
    assertEquals(true, Equality.equals(integerarray1, 0,
        integerarray2, 0, integerarray2.length));
    assertEquals(false, Equality.equals(integerarray3, 0,
        integerarray2, 0, integerarray2.length));
    assertEquals(false,
        Equality.equals(integerarray3, 0, integerarray2, 2, 1));
    assertEquals(true,
        Equality.equals(integerarray1, 1, integerarray2, 1, 1));

    final Long[][] longobjectarray1 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) },
        { (long) (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
    final Long[][] longobjectarray2 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) },
        { (long) (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
    final Long[][] longobjectarray3 = { { (long) 0, Long.MAX_VALUE } };
    assertEquals(true, Equality.equals(longobjectarray1, 0,
        longobjectarray2, 0, longobjectarray2.length));
    assertEquals(false, Equality.equals(longobjectarray3, 0,
        longobjectarray2, 0, longobjectarray2.length));
    assertEquals(false,
        Equality.equals(longobjectarray3, 0, longobjectarray2, 2, 1));
    assertEquals(true,
        Equality.equals(longobjectarray1, 1, longobjectarray2, 1, 1));

    final Float[][] floatobjectarray1 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) },
        { (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
    final Float[][] floatobjectarray2 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) },
        { (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
    final Float[][] floatobjectarray3 = { { (float) 0, Float.MAX_VALUE } };
    assertEquals(true, Equality.equals(floatobjectarray1, 0,
        floatobjectarray2, 0, floatobjectarray2.length));
    assertEquals(false, Equality.equals(floatobjectarray3, 0,
        floatobjectarray2, 0, floatobjectarray2.length));
    assertEquals(false, Equality.equals(floatobjectarray3, 0,
        floatobjectarray2, 2, 1));
    assertEquals(true, Equality.equals(floatobjectarray1, 1,
        floatobjectarray2, 1, 1));

    final Double[][] doubleobjectarray1 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE },
        { (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
    final Double[][] doubleobjectarray2 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE },
        { (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
    final Double[][] doubleobjectarray3 = { { (double) 0, Double.MAX_VALUE } };
    assertEquals(true, Equality.equals(doubleobjectarray1, 0,
        doubleobjectarray2, 0, doubleobjectarray2.length));
    assertEquals(false, Equality.equals(doubleobjectarray3, 0,
        doubleobjectarray2, 0, doubleobjectarray2.length));
    assertEquals(false, Equality.equals(doubleobjectarray3, 0,
        doubleobjectarray2, 2, 1));
    assertEquals(true, Equality.equals(doubleobjectarray1, 1,
        doubleobjectarray2, 1, 1));

    final class TestClassA {
    }
    final class TestClassB {
    }
    final Class<?>[][] classarray1 = { { TestClassA.class }, { TestClassA.class } };
    final Class<?>[][] classarray2 = { { TestClassA.class }, { TestClassA.class } };
    final Class<?>[][] classarray3 = { { TestClassB.class, TestClassA.class } };

    assertEquals(true,
        Equality.equals(classarray1, 0, classarray2, 0, classarray2.length));
    assertEquals(true,
        Equality.equals(classarray1, 0, classarray2, 0, classarray2.length));
    assertEquals(false,
        Equality.equals(classarray3, 0, classarray2, 0, classarray2.length));
    assertEquals(false, Equality.equals(classarray3, 0, classarray2, 1, 1));
    assertEquals(true, Equality.equals(classarray1, 1, classarray2, 1, 1));

    final Date datea = new Date();
    datea.setTime(123456);
    final Date dateb = new Date();
    dateb.setTime(7891011);
    final Date datec = new Date();
    datec.setTime(654321);
    final Date[][] datearray1 = { { datea, dateb }, { datec } };
    final Date[][] datearray2 = { { datea, dateb }, { datec } };
    final Date[][] datearray3 = { { datec }, { datea, dateb } };
    assertEquals(true, Equality.equals(datearray1, 0, datearray2, 0,
        datearray2.length));
    assertEquals(false, Equality.equals(datearray3, 0, datearray2,
        0, datearray2.length));
    assertEquals(false,
        Equality.equals(datearray3, 0, datearray2, 0, 1));
    assertEquals(true,
        Equality.equals(datearray3, 0, datearray2, 1, 1));
    assertEquals(true,
        Equality.equals(datearray1, 1, datearray2, 1, 1));

    final BigInteger[][] bigintegerarray1 = { { new BigInteger(bytearray1[0]),
        new BigInteger(bytearray3[0]) } };
    final BigInteger[][] bigintegerarray2 = { { new BigInteger(bytearray2[0]),
        new BigInteger(bytearray3[0]) } };
    final BigInteger[][] bigintegerarray3 = { { new BigInteger(bytearray3[0]) } };
    assertEquals(true, Equality.equals(bigintegerarray1, 0,
        bigintegerarray2, 0, bigintegerarray2.length));
    assertEquals(false, Equality.equals(bigintegerarray3, 0,
        bigintegerarray2, 0, bigintegerarray2.length));
    assertEquals(false,
        Equality.equals(bigintegerarray3, 0, bigintegerarray2, 0, 1));

    final BigDecimal[][] bigdecimalarray1 = { { new BigDecimal(Integer.MIN_VALUE) },
        { new BigDecimal(Integer.MAX_VALUE) } };
    final BigDecimal[][] bigdecimalarray2 = { { new BigDecimal(Integer.MIN_VALUE) },
        { new BigDecimal(Integer.MAX_VALUE) } };
    final BigDecimal[][] bigdecimalarray3 = { { new BigDecimal(0), new BigDecimal(-1) } };
    assertEquals(true, Equality.equals(bigdecimalarray1, 0,
        bigdecimalarray2, 0, bigdecimalarray2.length));
    assertEquals(false, Equality.equals(bigdecimalarray3, 0,
        bigdecimalarray2, 0, bigdecimalarray2.length));
    assertEquals(false,
        Equality.equals(bigdecimalarray3, 0, bigdecimalarray2, 1, 1));
    assertEquals(true,
        Equality.equals(bigdecimalarray1, 1, bigdecimalarray2, 1, 1));

    final Boolean[][][][] objectarray1 = { { { { true, false }, { true, true } } },
        { { { false, false }, { true } } } };
    final Boolean[][][][] objectarray2 = { { { { true, false }, { true, true } } },
        { { { false, false }, { true } } } };
    final Boolean[][][][] objectarray3 = { { { { true }, { false } } },
        { { { true, true } } } };
    assertEquals(true,
        Equality.equals(objectarray1, 0, objectarray2, 0, objectarray2.length));
    assertEquals(false,
        Equality.equals(objectarray3, 0, objectarray2, 0, objectarray2.length));
    assertEquals(false, Equality.equals(objectarray3, 0, objectarray2, 1, 1));
    assertEquals(true, Equality.equals(objectarray1, 1, objectarray2, 1, 1));

    final ArrayList<Character> arraylist1 = new ArrayList<Character>();
    final ArrayList<Character> arraylist2 = new ArrayList<Character>();
    final Object[] col1 = { arraylist1, arraylist2 };
    arraylist1.add('a');
    arraylist2.add('b');
    arraylist1.add('c');
    final ArrayList<Character> arraylist3 = new ArrayList<Character>();
    final ArrayList<Character> arraylist4 = new ArrayList<Character>();
    final Object[] col2 = { arraylist3, arraylist4 };
    arraylist3.add('a');
    arraylist4.add('b');
    arraylist3.add('c');
    final ArrayList<Character> arraylist5 = new ArrayList<Character>();
    final Object[] col3 = { arraylist5 };
    arraylist5.add('a');
    assertEquals(true,
        Equality.equals(col1, 0, col2, 0, col2.length));
    assertEquals(false,
        Equality.equals(col3, 0, col2, 0, col2.length));
    assertEquals(false, Equality.equals(col3, 0, col2, 1, 1));
    assertEquals(true, Equality.equals(col1, 1, col2, 1, 1));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equal(java.lang.Object, java.lang.Object, double)}
   * .
   */
  @Test
  public void testObjectWithEpsilon() {
    final double epsilon = 0.01;
    final ArrayList<Double> value1 = new ArrayList<Double>();
    value1.add(0.0);
    value1.add(1.0);
    value1.add(-1.0);
    value1.add(Double.MAX_VALUE);
    final ArrayList<Double> value2 = new ArrayList<Double>();
    value2.add(0.0);
    value2.add(1.0);
    value2.add(-1.0);
    value2.add(Double.MAX_VALUE);

    assertEquals(true, Equality.valueEquals(value1, (Object) value2, epsilon));
    value2.add(-1.0 + epsilon);
    assertEquals(false, Equality.valueEquals(value1, (Object) value2, epsilon));
    assertEquals(false, Equality.valueEquals(value1, (Object) null, epsilon));
    assertEquals(true,
        Equality.valueEquals((Object) null, (Object) null, epsilon));

    final boolean[] booleanarray1 = { true, false, false, true };
    final boolean[] booleanarray2 = { true, false, false, true };
    final boolean[] booleanarray3 = { false, true, true };
    assertEquals(true,
        Equality.valueEquals(booleanarray1, booleanarray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(booleanarray3, booleanarray2, epsilon));

    final char[] chararray1 = { (char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE };
    final char[] chararray2 = { (char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE };
    final char[] chararray3 = { (char) 0, Character.MAX_VALUE };
    assertEquals(true,
        Equality.valueEquals(chararray1, chararray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(chararray3, chararray2, epsilon));

    final byte[] bytearray1 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE };
    final byte[] bytearray2 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE };
    final byte[] bytearray3 = { (byte) 0, Byte.MAX_VALUE };
    assertEquals(true,
        Equality.valueEquals(bytearray1, bytearray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(bytearray3, bytearray2, epsilon));

    final short[] shortarray1 = { (short) 0, (short) -1, (short) 1, Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2), Short.MIN_VALUE,
        (short) (Short.MAX_VALUE / 2) };
    final short[] shortarray2 = { (short) 0, (short) -1, (short) 1, Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2), Short.MIN_VALUE,
        (short) (Short.MAX_VALUE / 2) };
    final short[] shortarray3 = { (short) 0, Short.MAX_VALUE };
    assertEquals(true,
        Equality.valueEquals(shortarray1, shortarray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(shortarray3, shortarray2, epsilon));

    final int[] intarray1 = { 0, -1, 1, Integer.MIN_VALUE,
        Integer.MIN_VALUE / 2, Integer.MAX_VALUE / 2,
        Integer.MAX_VALUE };
    final int[] intarray2 = { 0, -1, 1, Integer.MIN_VALUE,
        Integer.MIN_VALUE / 2, Integer.MAX_VALUE / 2,
        Integer.MAX_VALUE };
    final int[] intarray3 = { 0, Integer.MAX_VALUE };
    assertEquals(true,
        Equality.valueEquals(intarray1, intarray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(intarray3, intarray2, epsilon));

    final long[] longarray1 = { 0, -1, 1, Long.MIN_VALUE,
        Long.MIN_VALUE / 2, Long.MAX_VALUE / 2,
        Long.MAX_VALUE };
    final long[] longarray2 = { 0, -1, 1, Long.MIN_VALUE,
        Long.MIN_VALUE / 2, Long.MAX_VALUE / 2,
        Long.MAX_VALUE };
    final long[] longarray3 = { 0, Long.MAX_VALUE };
    assertEquals(true,
        Equality.valueEquals(longarray1, longarray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(longarray3, longarray2, epsilon));

    final float[] floatarray1 = { 0, -1, 1, Float.MIN_VALUE,
        Float.MIN_VALUE / 2, Float.MAX_VALUE / 2,
        Float.MAX_VALUE };
    final float[] floatarray2 = { 0, -1, 1, Float.MIN_VALUE,
        Float.MIN_VALUE / 2, Float.MAX_VALUE / 2,
        Float.MAX_VALUE };
    final float[] floatarray3 = { 0, Float.MAX_VALUE };
    assertEquals(true,
        Equality.valueEquals(floatarray1, floatarray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(floatarray3, floatarray2, epsilon));

    final double[] doublearray1 = { 0, -1, 1,
        Double.MIN_VALUE, Double.MIN_VALUE / 2,
        Double.MAX_VALUE / 2, Double.MAX_VALUE };
    final double[] doublearray2 = { 0, -1, 1,
        Double.MIN_VALUE, Double.MIN_VALUE / 2,
        Double.MAX_VALUE / 2, Double.MAX_VALUE };
    final double[] doublearray3 = { 0, Double.MAX_VALUE };
    assertEquals(true,
        Equality.valueEquals(doublearray1, (Object) doublearray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(doublearray3, (Object) doublearray2, epsilon));

    final String[] stringarray1 = { "long", "long", "ago" };
    final String[] stringarray2 = { "long", "long", "ago" };
    final String[] stringarray3 = { "long", "time", "no", "see" };
    assertEquals(true,
        Equality.valueEquals(stringarray1, (Object) stringarray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(stringarray3, (Object) stringarray2, epsilon));

    final Boolean[] booleanobjectarray1 = { true, false, false, true };
    final Boolean[] booleanobjectarray2 = { true, false, false, true };
    final Boolean[] booleanobjectarray3 = { false, true, true };
    assertEquals(true, Equality.valueEquals(booleanobjectarray1,
        (Object) booleanobjectarray2, epsilon));
    assertEquals(false, Equality.valueEquals(booleanobjectarray3,
        (Object) booleanobjectarray2, epsilon));

    final Character[] charobjectarray1 = { (char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE };
    final Character[] charobjectarray2 = { (char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE };
    final Character[] charobjectarray3 = { (char) 0, Character.MAX_VALUE };
    assertEquals(true, Equality.valueEquals(charobjectarray1,
        (Object) charobjectarray2, epsilon));
    assertEquals(false, Equality.valueEquals(charobjectarray3,
        (Object) charobjectarray2, epsilon));

    final Byte[] byteobjectarray1 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE };
    final Byte[] byteobjectarray2 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE };
    final Byte[] byteobjectarray3 = { (byte) 0, Byte.MAX_VALUE };
    assertEquals(true, Equality.valueEquals(byteobjectarray1,
        (Object) byteobjectarray2, epsilon));
    assertEquals(false, Equality.valueEquals(byteobjectarray3,
        (Object) byteobjectarray2, epsilon));

    final Short[] shortobjectarray1 = { (short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2),
        (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE };
    final Short[] shortobjectarray2 = { (short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2),
        (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE };
    final Short[] shortobjectarray3 = { (short) 0, Short.MAX_VALUE };
    assertEquals(true, Equality.valueEquals(shortobjectarray1,
        (Object) shortobjectarray2, epsilon));
    assertEquals(false, Equality.valueEquals(shortobjectarray3,
        (Object) shortobjectarray2, epsilon));

    final Integer[] integerarray1 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE };
    final Integer[] integerarray2 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE };
    final Integer[] integerarray3 = { (int) 0, Integer.MAX_VALUE };
    assertEquals(true,
        Equality.valueEquals(integerarray1, (Object) integerarray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(integerarray3, (Object) integerarray2, epsilon));

    final Long[] longobjectarray1 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MAX_VALUE };
    final Long[] longobjectarray2 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MAX_VALUE };
    final Long[] longobjectarray3 = { (long) 0, Long.MAX_VALUE };
    assertEquals(true, Equality.valueEquals(longobjectarray1,
        (Object) longobjectarray2, epsilon));
    assertEquals(false, Equality.valueEquals(longobjectarray3,
        (Object) longobjectarray2, epsilon));

    final Float[] floatobjectarray1 = { (float) 0, (float) -1, (float) 1,
        Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2),
        (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE };
    final Float[] floatobjectarray2 = { (float) 0, (float) -1, (float) 1,
        Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2),
        (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE };
    final Float[] floatobjectarray3 = { (float) 0, Float.MAX_VALUE };
    assertEquals(true, Equality.valueEquals(floatobjectarray1,
        (Object) floatobjectarray2, epsilon));
    assertEquals(false, Equality.valueEquals(floatobjectarray3,
        (Object) floatobjectarray2, epsilon));

    final Double[] doubleobjectarray1 = { (double) 0, (double) -1, (double) 1,
        (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE,
        (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE };
    final Double[] doubleobjectarray2 = { (double) 0, (double) -1, (double) 1,
        (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE,
        (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE };
    final Double[] doubleobjectarray3 = { (double) 0, Double.MAX_VALUE };
    assertEquals(true, Equality.valueEquals(doubleobjectarray1,
        (Object) doubleobjectarray2, epsilon));
    assertEquals(false, Equality.valueEquals(doubleobjectarray3,
        (Object) doubleobjectarray2, epsilon));

    final class TestClassA {
    }
    final class TestClassB {
    }
    final Class<?>[] classarray1 = new Class<?>[] { TestClassA.class,
        TestClassA.class };
    final Class<?>[] classarray2 = new Class<?>[] { TestClassA.class,
        TestClassA.class };
    final Class<?>[] classarray3 = new Class<?>[] { TestClassB.class,
        TestClassB.class };
    assertEquals(true,
        Equality.valueEquals(classarray1, (Object) classarray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(classarray3, (Object) classarray2, epsilon));

    final Date datea = new Date();
    datea.setTime(123456);
    final Date dateb = new Date();
    dateb.setTime(7891011);
    final Date datec = new Date();
    datec.setTime(654321);
    final Date[] datearray1 = { datea, dateb, datec };
    final Date[] datearray2 = { datea, dateb, datec };
    final Date[] datearray3 = { datec, datea, dateb };
    assertEquals(true,
        Equality.valueEquals(datearray1, (Object) datearray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(datearray3, (Object) datearray2, epsilon));

    final BigInteger[] bigintegerarray1 = { new BigInteger(bytearray1),
        new BigInteger(bytearray3) };
    final BigInteger[] bigintegerarray2 = { new BigInteger(bytearray2),
        new BigInteger(bytearray3) };
    final BigInteger[] bigintegerarray3 = { new BigInteger(bytearray3) };
    assertEquals(true, Equality.valueEquals(bigintegerarray1,
        (Object) bigintegerarray2, epsilon));
    assertEquals(false, Equality.valueEquals(bigintegerarray3,
        (Object) bigintegerarray2, epsilon));

    final BigDecimal[] bigdecimalarray1 = { new BigDecimal(Integer.MIN_VALUE),
        new BigDecimal(Integer.MAX_VALUE) };
    final BigDecimal[] bigdecimalarray2 = { new BigDecimal(Integer.MIN_VALUE),
        new BigDecimal(Integer.MAX_VALUE) };
    final BigDecimal[] bigdecimalarray3 = { new BigDecimal(0), new BigDecimal(-1) };
    assertEquals(true, Equality.valueEquals(bigdecimalarray1,
        (Object) bigdecimalarray2, epsilon));
    assertEquals(false, Equality.valueEquals(bigdecimalarray3,
        (Object) bigdecimalarray2, epsilon));

    final Boolean[][] objectarray1 = { { true, false }, { true, true },
        { false, false, true } };
    final Boolean[][] objectarray2 = { { true, false }, { true, true },
        { false, false, true } };
    final Boolean[][] objectarray3 = { { true, false }, { true, true } };
    assertEquals(true,
        Equality.valueEquals(objectarray1, (Object) objectarray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(objectarray3, (Object) objectarray2, epsilon));

    final ArrayList<Character> col1 = new ArrayList<Character>();
    col1.add('a');
    col1.add('b');
    col1.add('c');
    final ArrayList<Character> col2 = new ArrayList<Character>();
    col2.add('a');
    col2.add('b');
    col2.add('c');
    final ArrayList<Character> col3 = new ArrayList<Character>();
    col3.add('b');
    col3.add('c');
    assertEquals(true, Equality.valueEquals(col1, (Object) col2, epsilon));
    assertEquals(false, Equality.valueEquals(col3, (Object) col2, epsilon));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality#equal(java.lang.Object[], java.lang.Object[], double)}
   * .
   */
  @Test
  public void testObjectArrayWithEpsilon() {
    final double epsilon = 0.01;

    final boolean[][] booleanarray1 = { { true }, { false, false }, { true } };
    final boolean[][] booleanarray2 = { { true }, { false, false }, { true } };
    final boolean[][] booleanarray3 = { { false, true }, { true } };
    assertEquals(true,
        Equality.valueEquals(booleanarray1, (Object) booleanarray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(booleanarray3, (Object) booleanarray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(booleanarray1, (Object) null, epsilon));
    assertEquals(true,
        Equality.valueEquals((Object) null, (Object) null, epsilon));

    final char[][] chararray1 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    final char[][] chararray2 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    final char[][] chararray3 = { { (char) 0, Character.MAX_VALUE } };
    assertEquals(true,
        Equality.valueEquals(chararray1, (Object) chararray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(chararray3, (Object) chararray2, epsilon));

    final byte[][] bytearray1 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    final byte[][] bytearray2 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    final byte[][] bytearray3 = { { (byte) 0, Byte.MAX_VALUE } };
    assertEquals(true,
        Equality.valueEquals(bytearray1, (Object) bytearray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(bytearray3, (Object) bytearray2, epsilon));

    final short[][] shortarray1 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MAX_VALUE, (short) (Short.MIN_VALUE / 2) },
        { Short.MIN_VALUE, (short) (Short.MAX_VALUE / 2) } };
    final short[][] shortarray2 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MAX_VALUE, (short) (Short.MIN_VALUE / 2) },
        { Short.MIN_VALUE, (short) (Short.MAX_VALUE / 2) } };
    final short[][] shortarray3 = { { (short) 0, Short.MAX_VALUE } };
    assertEquals(true,
        Equality.valueEquals(shortarray1, (Object) shortarray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(shortarray3, (Object) shortarray2, epsilon));

    final int[][] intarray1 = { { 0, -1, 1 },
        { Integer.MIN_VALUE, Integer.MIN_VALUE / 2 },
        { Integer.MAX_VALUE / 2, Integer.MAX_VALUE } };
    final int[][] intarray2 = { { 0, -1, 1 },
        { Integer.MIN_VALUE, Integer.MIN_VALUE / 2 },
        { Integer.MAX_VALUE / 2, Integer.MAX_VALUE } };
    final int[][] intarray3 = { { 0, Integer.MAX_VALUE } };
    assertEquals(true,
        Equality.valueEquals(intarray1, (Object) intarray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(intarray3, (Object) intarray2, epsilon));

    final long[][] longarray1 = { { 0, -1, 1 },
        { Long.MIN_VALUE, Long.MIN_VALUE / 2 },
        { Long.MAX_VALUE / 2, Long.MAX_VALUE } };
    final long[][] longarray2 = { { 0, -1, 1 },
        { Long.MIN_VALUE, Long.MIN_VALUE / 2 },
        { Long.MAX_VALUE / 2, Long.MAX_VALUE } };
    final long[][] longarray3 = { { 0, Long.MAX_VALUE } };
    assertEquals(true,
        Equality.valueEquals(longarray1, (Object) longarray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(longarray3, (Object) longarray2, epsilon));

    final float[][] floatarray1 = { { 0, -1, 1 },
        { Float.MIN_VALUE, Float.MIN_VALUE / 2 },
        { Float.MAX_VALUE / 2, Float.MAX_VALUE } };
    final float[][] floatarray2 = { { 0, -1, 1 },
        { Float.MIN_VALUE, Float.MIN_VALUE / 2 },
        { Float.MAX_VALUE / 2, Float.MAX_VALUE } };
    final float[][] floatarray3 = { { 0, Float.MAX_VALUE } };
    assertEquals(true,
        Equality.valueEquals(floatarray1, (Object) floatarray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(floatarray3, (Object) floatarray2, epsilon));

    final double[][] doublearray1 = { { 0, -1, 1 },
        { Double.MIN_VALUE, Double.MIN_VALUE / 2 },
        { Double.MAX_VALUE / 2, Double.MAX_VALUE } };
    final double[][] doublearray2 = { { 0, -1, 1 },
        { Double.MIN_VALUE, Double.MIN_VALUE / 2 },
        { Double.MAX_VALUE / 2, Double.MAX_VALUE } };
    final double[][] doublearray3 = { { 0, Double.MAX_VALUE } };
    assertEquals(true,
        Equality.valueEquals(doublearray1, (Object) doublearray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(doublearray3, (Object) doublearray2, epsilon));

    final String[][] stringarray1 = { { "long", "long" }, { "ago" } };
    final String[][] stringarray2 = { { "long", "long" }, { "ago" } };
    final String[][] stringarray3 = { { "long", "time" }, { "no", "see" } };
    assertEquals(true,
        Equality.valueEquals(stringarray1, (Object) stringarray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(stringarray3, (Object) stringarray2, epsilon));

    final Boolean[][] booleanobjectarray1 = { { true, false }, { false }, { true } };
    final Boolean[][] booleanobjectarray2 = { { true, false }, { false }, { true } };
    final Boolean[][] booleanobjectarray3 = { { false, true }, { true } };
    assertEquals(true, Equality.valueEquals(booleanobjectarray1,
        (Object) booleanobjectarray2, epsilon));
    assertEquals(false, Equality.valueEquals(booleanobjectarray3,
        (Object) booleanobjectarray2, epsilon));

    final Character[][] charobjectarray1 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    final Character[][] charobjectarray2 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    final Character[][] charobjectarray3 = { { (char) 0, Character.MAX_VALUE } };
    assertEquals(true, Equality.valueEquals(charobjectarray1,
        (Object) charobjectarray2, epsilon));
    assertEquals(false, Equality.valueEquals(charobjectarray3,
        (Object) charobjectarray2, epsilon));

    final Byte[][] byteobjectarray1 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    final Byte[][] byteobjectarray2 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    final Byte[][] byteobjectarray3 = { { (byte) 0, Byte.MAX_VALUE } };
    assertEquals(true, Equality.valueEquals(byteobjectarray1,
        (Object) byteobjectarray2, epsilon));
    assertEquals(false, Equality.valueEquals(byteobjectarray3,
        (Object) byteobjectarray2, epsilon));

    final Short[][] shortobjectarray1 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
    final Short[][] shortobjectarray2 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
    final Short[][] shortobjectarray3 = { { (short) 0, Short.MAX_VALUE } };
    assertEquals(true, Equality.valueEquals(shortobjectarray1,
        (Object) shortobjectarray2, epsilon));
    assertEquals(false, Equality.valueEquals(shortobjectarray3,
        (Object) shortobjectarray2, epsilon));

    final Integer[][] integerarray1 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) },
        { (int) (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
    final Integer[][] integerarray2 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) },
        { (int) (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
    final Integer[][] integerarray3 = { { (int) 0, Integer.MAX_VALUE } };
    assertEquals(true,
        Equality.valueEquals(integerarray1, (Object) integerarray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(integerarray3, (Object) integerarray2, epsilon));

    final Long[][] longobjectarray1 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) },
        { (long) (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
    final Long[][] longobjectarray2 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) },
        { (long) (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
    final Long[][] longobjectarray3 = { { (long) 0, Long.MAX_VALUE } };
    assertEquals(true, Equality.valueEquals(longobjectarray1,
        (Object) longobjectarray2, epsilon));
    assertEquals(false, Equality.valueEquals(longobjectarray3,
        (Object) longobjectarray2, epsilon));

    final Float[][] floatobjectarray1 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) },
        { (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
    final Float[][] floatobjectarray2 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) },
        { (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
    final Float[][] floatobjectarray3 = { { (float) 0, Float.MAX_VALUE } };
    assertEquals(true, Equality.valueEquals(floatobjectarray1,
        (Object) floatobjectarray2, epsilon));
    assertEquals(false, Equality.valueEquals(floatobjectarray3,
        (Object) floatobjectarray2, epsilon));

    final Double[][] doubleobjectarray1 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE },
        { (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
    final Double[][] doubleobjectarray2 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE },
        { (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
    final Double[][] doubleobjectarray3 = { { (double) 0, Double.MAX_VALUE } };
    assertEquals(true, Equality.valueEquals(doubleobjectarray1,
        (Object) doubleobjectarray2, epsilon));
    assertEquals(false, Equality.valueEquals(doubleobjectarray3,
        (Object) doubleobjectarray2, epsilon));

    final class TestClassA {
    }
    final class TestClassB {
    }
    final Class<?>[][] classarray1 = { { TestClassA.class }, { TestClassA.class } };
    final Class<?>[][] classarray2 = { { TestClassA.class }, { TestClassA.class } };
    final Class<?>[][] classarray3 = { { TestClassB.class, TestClassA.class } };

    assertEquals(true, Equality.valueEquals(classarray1, classarray2, epsilon));
    assertEquals(false, Equality.valueEquals(classarray2, classarray3, epsilon));

    final Date datea = new Date();
    datea.setTime(123456);
    final Date dateb = new Date();
    dateb.setTime(7891011);
    final Date datec = new Date();
    datec.setTime(654321);
    final Date[][] datearray1 = { { datea, dateb }, { datec } };
    final Date[][] datearray2 = { { datea, dateb }, { datec } };
    final Date[][] datearray3 = { { datec }, { datea, dateb } };
    assertEquals(true, Equality.valueEquals(datearray1, datearray2, epsilon));
    assertEquals(false, Equality.valueEquals(datearray3, datearray2, epsilon));

    final BigInteger[][] bigintegerarray1 = { { new BigInteger(bytearray1[0]),
        new BigInteger(bytearray3[0]) } };
    final BigInteger[][] bigintegerarray2 = { { new BigInteger(bytearray2[0]),
        new BigInteger(bytearray3[0]) } };
    final BigInteger[][] bigintegerarray3 = { { new BigInteger(bytearray3[0]) } };
    assertEquals(true, Equality.valueEquals(bigintegerarray1,
        (Object) bigintegerarray2, epsilon));
    assertEquals(false, Equality.valueEquals(bigintegerarray3,
        (Object) bigintegerarray2, epsilon));

    final BigDecimal[][] bigdecimalarray1 = { { new BigDecimal(Integer.MIN_VALUE) },
        { new BigDecimal(Integer.MAX_VALUE) } };
    final BigDecimal[][] bigdecimalarray2 = { { new BigDecimal(Integer.MIN_VALUE) },
        { new BigDecimal(Integer.MAX_VALUE) } };
    final BigDecimal[][] bigdecimalarray3 = { { new BigDecimal(0), new BigDecimal(-1) } };
    assertEquals(true, Equality.valueEquals(bigdecimalarray1,
        (Object) bigdecimalarray2, epsilon));
    assertEquals(false, Equality.valueEquals(bigdecimalarray3,
        (Object) bigdecimalarray2, epsilon));

    final Boolean[][][][] objectarray1 = { { { { true, false }, { true, true } } },
        { { { false, false }, { true } } } };
    final Boolean[][][][] objectarray2 = { { { { true, false }, { true, true } } },
        { { { false, false }, { true } } } };
    final Boolean[][][][] objectarray3 = { { { { true }, { false } } },
        { { { true, true } } } };
    assertEquals(true,
        Equality.valueEquals(objectarray1, (Object) objectarray2, epsilon));
    assertEquals(false,
        Equality.valueEquals(objectarray3, (Object) objectarray2, epsilon));

    final ArrayList<Character> arraylist1 = new ArrayList<Character>();
    final ArrayList<Character> arraylist2 = new ArrayList<Character>();
    final Object[] col1 = { arraylist1, arraylist2 };
    arraylist1.add('a');
    arraylist2.add('b');
    arraylist1.add('c');
    final ArrayList<Character> arraylist3 = new ArrayList<Character>();
    final ArrayList<Character> arraylist4 = new ArrayList<Character>();
    final Object[] col2 = { arraylist3, arraylist4 };
    arraylist3.add('a');
    arraylist4.add('b');
    arraylist3.add('c');
    final ArrayList<Character> arraylist5 = new ArrayList<Character>();
    final Object[] col3 = { arraylist5 };
    arraylist5.add('a');
    assertEquals(true, Equality.valueEquals(col1, (Object) col2, epsilon));
    assertEquals(false, Equality.valueEquals(col3, (Object) col2, epsilon));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equal(java.lang.Object[], int, java.lang.Object[], int, int, double)}
   * .
   */
  @Test
  public void testObjectArrayIndexLengthWithEpsilon() {
    final double epsilon = 0.0001;

    final boolean[][] booleanarray1 = { { true }, { false, false }, { true } };
    final boolean[][] booleanarray2 = { { true }, { false, false }, { true } };
    final boolean[][] booleanarray3 = { { false, true }, { true } };
    assertEquals(true, Equality.valueEquals(booleanarray1, 0,
        booleanarray2, 0, booleanarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(booleanarray3, 0,
        booleanarray2, 0, booleanarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(booleanarray1, 1,
        booleanarray2, 0, 1, epsilon));
    assertEquals(true, Equality.valueEquals(booleanarray1, 0,
        booleanarray2, 2, 1, epsilon));

    final char[][] chararray1 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    final char[][] chararray2 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    final char[][] chararray3 = { { (char) 0, Character.MAX_VALUE } };
    assertEquals(true, Equality.valueEquals(chararray1, 0,
        chararray2, 0, chararray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(chararray3, 0,
        chararray2, 0, chararray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(chararray3, 0,
        chararray2, 2, 1, epsilon));
    assertEquals(true, Equality.valueEquals(chararray1, 1,
        chararray2, 1, 1, epsilon));

    final byte[][] bytearray1 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    final byte[][] bytearray2 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    final byte[][] bytearray3 = { { (byte) 0, Byte.MAX_VALUE } };
    assertEquals(true, Equality.valueEquals(bytearray1, 0,
        bytearray2, 0, bytearray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(bytearray3, 0,
        bytearray2, 0, bytearray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(bytearray3, 0,
        bytearray2, 2, 1, epsilon));
    assertEquals(true, Equality.valueEquals(bytearray1, 1,
        bytearray2, 1, 1, epsilon));

    final short[][] shortarray1 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MAX_VALUE, (short) (Short.MIN_VALUE / 2) },
        { Short.MIN_VALUE, (short) (Short.MAX_VALUE / 2) } };
    final short[][] shortarray2 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MAX_VALUE, (short) (Short.MIN_VALUE / 2) },
        { Short.MIN_VALUE, (short) (Short.MAX_VALUE / 2) } };
    final short[][] shortarray3 = { { (short) 0, Short.MAX_VALUE } };
    assertEquals(true, Equality.valueEquals(shortarray1, 0,
        shortarray2, 0, shortarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(shortarray3, 0,
        shortarray2, 0, shortarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(shortarray3, 0,
        shortarray2, 2, 1, epsilon));
    assertEquals(true, Equality.valueEquals(shortarray1, 1,
        shortarray2, 1, 1, epsilon));

    final int[][] intarray1 = { { 0, -1, 1 },
        { Integer.MIN_VALUE, Integer.MIN_VALUE / 2 },
        { Integer.MAX_VALUE / 2, Integer.MAX_VALUE } };
    final int[][] intarray2 = { { 0, -1, 1 },
        { Integer.MIN_VALUE, Integer.MIN_VALUE / 2 },
        { Integer.MAX_VALUE / 2, Integer.MAX_VALUE } };
    final int[][] intarray3 = { { 0, Integer.MAX_VALUE } };
    assertEquals(true, Equality.valueEquals(intarray1, 0, intarray2,
        0, intarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(intarray3, 0,
        intarray2, 0, intarray2.length, epsilon));
    assertEquals(false,
        Equality.valueEquals(intarray3, 0, intarray2, 2, 1, epsilon));
    assertEquals(true,
        Equality.valueEquals(intarray1, 1, intarray2, 1, 1, epsilon));

    final long[][] longarray1 = { { 0, -1, 1 },
        { Long.MIN_VALUE, Long.MIN_VALUE / 2 },
        { Long.MAX_VALUE / 2, Long.MAX_VALUE } };
    final long[][] longarray2 = { { 0, -1, 1 },
        { Long.MIN_VALUE, Long.MIN_VALUE / 2 },
        { Long.MAX_VALUE / 2, Long.MAX_VALUE } };
    final long[][] longarray3 = { { 0, Long.MAX_VALUE } };
    assertEquals(true, Equality.valueEquals(longarray1, 0,
        longarray2, 0, longarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(longarray3, 0,
        longarray2, 0, longarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(longarray3, 0,
        longarray2, 2, 1, epsilon));
    assertEquals(true, Equality.valueEquals(longarray1, 1,
        longarray2, 1, 1, epsilon));

    final float[][] floatarray1 = { { 0, -1, 1 },
        { Float.MIN_VALUE, Float.MIN_VALUE / 2 },
        { Float.MAX_VALUE / 2, Float.MAX_VALUE } };
    final float[][] floatarray2 = { { 0, -1, 1 },
        { Float.MIN_VALUE, Float.MIN_VALUE / 2 },
        { Float.MAX_VALUE / 2, Float.MAX_VALUE } };
    final float[][] floatarray3 = { { 0, Float.MAX_VALUE } };
    assertEquals(true, Equality.valueEquals(floatarray1, 0,
        floatarray2, 0, floatarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(floatarray3, 0,
        floatarray2, 0, floatarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(floatarray3, 0,
        floatarray2, 2, 1, epsilon));
    assertEquals(true, Equality.valueEquals(floatarray1, 1,
        floatarray2, 1, 1, epsilon));

    final double[][] doublearray1 = { { 0, -1, 1 },
        { Double.MIN_VALUE, Double.MIN_VALUE / 2 },
        { Double.MAX_VALUE / 2, Double.MAX_VALUE } };
    final double[][] doublearray2 = { { 0, -1, 1 },
        { Double.MIN_VALUE, Double.MIN_VALUE / 2 },
        { Double.MAX_VALUE / 2, Double.MAX_VALUE } };
    final double[][] doublearray3 = { { 0, Double.MAX_VALUE } };
    assertEquals(true, Equality.valueEquals(doublearray1, 0,
        doublearray2, 0, doublearray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(doublearray3, 0,
        doublearray2, 0, doublearray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(doublearray3, 0,
        doublearray2, 2, 1, epsilon));
    assertEquals(true, Equality.valueEquals(doublearray1, 1,
        doublearray2, 1, 1, epsilon));

    final String[][] stringarray1 = { { "long", "long" }, { "ago" } };
    final String[][] stringarray2 = { { "long", "long" }, { "ago" } };
    final String[][] stringarray3 = { { "long", "time" }, { "no", "see" } };
    assertEquals(true, Equality.valueEquals(stringarray1, 0,
        stringarray2, 0, stringarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(stringarray3, 0,
        stringarray2, 0, stringarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(stringarray3, 0,
        stringarray2, 1, 1, epsilon));
    assertEquals(true, Equality.valueEquals(stringarray1, 1,
        stringarray2, 1, 1, epsilon));

    final Boolean[][] booleanobjectarray1 = { { true, false }, { false }, { true } };
    final Boolean[][] booleanobjectarray2 = { { true, false }, { false }, { true } };
    final Boolean[][] booleanobjectarray3 = { { false, true }, { true } };
    assertEquals(true, Equality.valueEquals(booleanobjectarray1, 0,
        booleanobjectarray2, 0, booleanobjectarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(booleanobjectarray3, 0,
        booleanobjectarray2, 0, booleanobjectarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(booleanobjectarray3, 0,
        booleanobjectarray2, 2, 1, epsilon));
    assertEquals(true, Equality.valueEquals(booleanobjectarray1, 1,
        booleanobjectarray2, 1, 1, epsilon));

    final Character[][] charobjectarray1 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    final Character[][] charobjectarray2 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    final Character[][] charobjectarray3 = { { (char) 0, Character.MAX_VALUE } };
    assertEquals(true, Equality.valueEquals(charobjectarray1, 0,
        charobjectarray2, 0, charobjectarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(charobjectarray3, 0,
        charobjectarray2, 0, charobjectarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(charobjectarray3, 0,
        charobjectarray2, 2, 1, epsilon));
    assertEquals(true, Equality.valueEquals(charobjectarray1, 1,
        charobjectarray2, 1, 1, epsilon));

    final Byte[][] byteobjectarray1 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    final Byte[][] byteobjectarray2 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    final Byte[][] byteobjectarray3 = { { (byte) 0, Byte.MAX_VALUE } };
    assertEquals(true, Equality.valueEquals(byteobjectarray1, 0,
        byteobjectarray2, 0, byteobjectarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(byteobjectarray3, 0,
        byteobjectarray2, 0, byteobjectarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(byteobjectarray3, 0,
        byteobjectarray2, 2, 1, epsilon));
    assertEquals(true, Equality.valueEquals(byteobjectarray1, 1,
        byteobjectarray2, 1, 1, epsilon));

    final Short[][] shortobjectarray1 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
    final Short[][] shortobjectarray2 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
    final Short[][] shortobjectarray3 = { { (short) 0, Short.MAX_VALUE } };
    assertEquals(true, Equality.valueEquals(shortobjectarray1, 0,
        shortobjectarray2, 0, shortobjectarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(shortobjectarray3, 0,
        shortobjectarray2, 0, shortobjectarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(shortobjectarray3, 0,
        shortobjectarray2, 2, 1, epsilon));
    assertEquals(true, Equality.valueEquals(shortobjectarray1, 1,
        shortobjectarray2, 1, 1, epsilon));

    final Integer[][] integerarray1 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) },
        { (int) (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
    final Integer[][] integerarray2 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) },
        { (int) (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
    final Integer[][] integerarray3 = { { (int) 0, Integer.MAX_VALUE } };
    assertEquals(true, Equality.valueEquals(integerarray1, 0,
        integerarray2, 0, integerarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(integerarray3, 0,
        integerarray2, 0, integerarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(integerarray3, 0,
        integerarray2, 2, 1, epsilon));
    assertEquals(true, Equality.valueEquals(integerarray1, 1,
        integerarray2, 1, 1, epsilon));

    final Long[][] longobjectarray1 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) },
        { (long) (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
    final Long[][] longobjectarray2 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) },
        { (long) (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
    final Long[][] longobjectarray3 = { { (long) 0, Long.MAX_VALUE } };
    assertEquals(true, Equality.valueEquals(longobjectarray1, 0,
        longobjectarray2, 0, longobjectarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(longobjectarray3, 0,
        longobjectarray2, 0, longobjectarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(longobjectarray3, 0,
        longobjectarray2, 2, 1, epsilon));
    assertEquals(true, Equality.valueEquals(longobjectarray1, 1,
        longobjectarray2, 1, 1, epsilon));

    final Float[][] floatobjectarray1 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) },
        { (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
    final Float[][] floatobjectarray2 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) },
        { (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
    final Float[][] floatobjectarray3 = { { (float) 0, Float.MAX_VALUE } };
    assertEquals(true, Equality.valueEquals(floatobjectarray1, 0,
        floatobjectarray2, 0, floatobjectarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(floatobjectarray3, 0,
        floatobjectarray2, 0, floatobjectarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(floatobjectarray3, 0,
        floatobjectarray2, 2, 1, epsilon));
    assertEquals(true, Equality.valueEquals(floatobjectarray1, 1,
        floatobjectarray2, 1, 1, epsilon));

    final Double[][] doubleobjectarray1 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE },
        { (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
    final Double[][] doubleobjectarray2 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE },
        { (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
    final Double[][] doubleobjectarray3 = { { (double) 0, Double.MAX_VALUE } };
    assertEquals(true, Equality.valueEquals(doubleobjectarray1, 0,
        doubleobjectarray2, 0, doubleobjectarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(doubleobjectarray3, 0,
        doubleobjectarray2, 0, doubleobjectarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(doubleobjectarray3, 0,
        doubleobjectarray2, 2, 1, epsilon));
    assertEquals(true, Equality.valueEquals(doubleobjectarray1, 1,
        doubleobjectarray2, 1, 1, epsilon));

    final class TestClassA {
    }
    final class TestClassB {
    }
    final Class<?>[][] classarray1 = { { TestClassA.class }, { TestClassA.class } };
    final Class<?>[][] classarray2 = { { TestClassA.class }, { TestClassA.class } };
    final Class<?>[][] classarray3 = { { TestClassB.class, TestClassA.class } };

    assertEquals(true, Equality.valueEquals(classarray1, 0, classarray2, 0,
        classarray2.length, epsilon));
    assertEquals(true, Equality.valueEquals(classarray1, 0, classarray2, 0,
        classarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(classarray3, 0, classarray2, 0,
        classarray2.length, epsilon));
    assertEquals(false,
        Equality.valueEquals(classarray3, 0, classarray2, 1, 1, epsilon));
    assertEquals(true,
        Equality.valueEquals(classarray1, 1, classarray2, 1, 1, epsilon));

    final Date datea = new Date();
    datea.setTime(123456);
    final Date dateb = new Date();
    dateb.setTime(7891011);
    final Date datec = new Date();
    datec.setTime(654321);
    final Date[][] datearray1 = { { datea, dateb }, { datec } };
    final Date[][] datearray2 = { { datea, dateb }, { datec } };
    final Date[][] datearray3 = { { datec }, { datea, dateb } };
    assertEquals(true, Equality.valueEquals(datearray1, 0,
        datearray2, 0, datearray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(datearray3, 0,
        datearray2, 0, datearray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(datearray3, 0,
        datearray2, 0, 1, epsilon));
    assertEquals(true, Equality.valueEquals(datearray3, 0,
        datearray2, 1, 1, epsilon));
    assertEquals(true, Equality.valueEquals(datearray1, 1,
        datearray2, 1, 1, epsilon));

    final BigInteger[][] bigintegerarray1 = { { new BigInteger(bytearray1[0]),
        new BigInteger(bytearray3[0]) } };
    final BigInteger[][] bigintegerarray2 = { { new BigInteger(bytearray2[0]),
        new BigInteger(bytearray3[0]) } };
    final BigInteger[][] bigintegerarray3 = { { new BigInteger(bytearray3[0]) } };
    assertEquals(true, Equality.valueEquals(bigintegerarray1, 0,
        bigintegerarray2, 0, bigintegerarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(bigintegerarray3, 0,
        bigintegerarray2, 0, bigintegerarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(bigintegerarray3, 0,
        bigintegerarray2, 0, 1, epsilon));

    final BigDecimal[][] bigdecimalarray1 = { { new BigDecimal(Integer.MIN_VALUE) },
        { new BigDecimal(Integer.MAX_VALUE) } };
    final BigDecimal[][] bigdecimalarray2 = { { new BigDecimal(Integer.MIN_VALUE) },
        { new BigDecimal(Integer.MAX_VALUE) } };
    final BigDecimal[][] bigdecimalarray3 = { { new BigDecimal(0), new BigDecimal(-1) } };
    assertEquals(true, Equality.valueEquals(bigdecimalarray1, 0,
        bigdecimalarray2, 0, bigdecimalarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(bigdecimalarray3, 0,
        bigdecimalarray2, 0, bigdecimalarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(bigdecimalarray3, 0,
        bigdecimalarray2, 1, 1, epsilon));
    assertEquals(true, Equality.valueEquals(bigdecimalarray1, 1,
        bigdecimalarray2, 1, 1, epsilon));

    final Boolean[][][][] objectarray1 = { { { { true, false }, { true, true } } },
        { { { false, false }, { true } } } };
    final Boolean[][][][] objectarray2 = { { { { true, false }, { true, true } } },
        { { { false, false }, { true } } } };
    final Boolean[][][][] objectarray3 = { { { { true }, { false } } },
        { { { true, true } } } };
    assertEquals(true, Equality.valueEquals(objectarray1, 0,
        objectarray2, 0, objectarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(objectarray3, 0,
        objectarray2, 0, objectarray2.length, epsilon));
    assertEquals(false, Equality.valueEquals(objectarray3, 0,
        objectarray2, 1, 1, epsilon));
    assertEquals(true, Equality.valueEquals(objectarray1, 1,
        objectarray2, 1, 1, epsilon));

    final ArrayList<Character> arraylist1 = new ArrayList<Character>();
    final ArrayList<Character> arraylist2 = new ArrayList<Character>();
    final Object[] col1 = { arraylist1, arraylist2 };
    arraylist1.add('a');
    arraylist2.add('b');
    arraylist1.add('c');
    final ArrayList<Character> arraylist3 = new ArrayList<Character>();
    final ArrayList<Character> arraylist4 = new ArrayList<Character>();
    final Object[] col2 = { arraylist3, arraylist4 };
    arraylist3.add('a');
    arraylist4.add('b');
    arraylist3.add('c');
    final ArrayList<Character> arraylist5 = new ArrayList<Character>();
    final Object[] col3 = { arraylist5 };
    arraylist5.add('a');
    assertEquals(true,
        Equality.valueEquals(col1, 0, col2, 0, col2.length, epsilon));
    assertEquals(false,
        Equality.valueEquals(col3, 0, col2, 0, col2.length, epsilon));
    assertEquals(false,
        Equality.valueEquals(col3, 0, col2, 1, 1, epsilon));
    assertEquals(true,
        Equality.valueEquals(col1, 1, col2, 1, 1, epsilon));
  }

  public void testMultiBooleanArray() {
    boolean[][] array1 = new boolean[2][2];
    boolean[][] array2 = new boolean[2][2];
    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array1[0].length; j++) {
        array1[i][j] = (i == 1) || (j == 1);
        array2[i][j] = (i == 1) || (j == 1);
      }
    }
    assertEquals(true, Equality.equals(array1, array1));
    assertEquals(true, Equality.equals(array1, array2));
    array1[1][1] = false;
    assertEquals(false, Equality.equals(array1, array2));

    // compare 1 dim to 2.
    final boolean[] array3 = new boolean[] { true, true };
    assertEquals(false, Equality.equals(array1, array3));
    assertEquals(false, Equality.equals(array3, array1));
    assertEquals(false, Equality.equals(array2, array3));
    assertEquals(false, Equality.equals(array3, array2));

    array1 = null;
    assertEquals(false, Equality.equals(array1, array2));
    array2 = null;
    assertEquals(true, Equality.equals(array1, array2));
  }

  public void testMultiCharArray() {
    char[][] array1 = new char[2][2];
    char[][] array2 = new char[2][2];
    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array1[0].length; j++) {
        array1[i][j] = (char) i;
        array2[i][j] = (char) i;
      }
    }
    assertEquals(true, Equality.equals(array1, array1));
    assertEquals(true, Equality.equals(array1, array2));
    array1[1][1] = 128;
    assertEquals(false, Equality.equals(array1, array2));

    // compare 1 dim to 2.
    final char[] array3 = new char[] { 1, 2 };
    assertEquals(false, Equality.equals(array1, array3));
    assertEquals(false, Equality.equals(array3, array1));
    assertEquals(false, Equality.equals(array2, array3));
    assertEquals(false, Equality.equals(array3, array2));

    array1 = null;
    assertEquals(false, Equality.equals(array1, array2));
    array2 = null;
    assertEquals(true, Equality.equals(array1, array2));
  }

  public void testMultiByteArray() {
    byte[][] array1 = new byte[2][2];
    byte[][] array2 = new byte[2][2];
    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array1[0].length; j++) {
        array1[i][j] = (byte) i;
        array2[i][j] = (byte) i;
      }
    }
    assertEquals(true, Equality.equals(array1, array1));
    assertEquals(true, Equality.equals(array1, array2));
    array1[1][1] = (byte) 100;
    assertEquals(false, Equality.equals(array1, array2));

    // compare 1 dim to 2.
    final byte[] array3 = new byte[] { 1, 2 };
    assertEquals(false, Equality.equals(array1, array3));
    assertEquals(false, Equality.equals(array3, array1));
    assertEquals(false, Equality.equals(array2, array3));
    assertEquals(false, Equality.equals(array3, array2));

    array1 = null;
    assertEquals(false, Equality.equals(array1, array2));
    array2 = null;
    assertEquals(true, Equality.equals(array1, array2));
  }

  public void testMultiShortArray() {
    short[][] array1 = new short[2][2];
    short[][] array2 = new short[2][2];
    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array1[0].length; j++) {
        array1[i][j] = (short) i;
        array2[i][j] = (short) i;
      }
    }
    assertEquals(true, Equality.equals(array1, array1));
    assertEquals(true, Equality.equals(array1, array2));
    array1[1][1] = (short) 100;
    assertEquals(false, Equality.equals(array1, array2));

    // compare 1 dim to 2.
    final short[] array3 = new short[] { 1, 2 };
    assertEquals(false, Equality.equals(array1, array3));
    assertEquals(false, Equality.equals(array3, array1));
    assertEquals(false, Equality.equals(array2, array3));
    assertEquals(false, Equality.equals(array3, array2));

    array1 = null;
    assertEquals(false, Equality.equals(array1, array2));
    array2 = null;
    assertEquals(true, Equality.equals(array1, array2));
  }

  public void testMultiIntArray() {
    int[][] array1 = new int[2][2];
    int[][] array2 = new int[2][2];
    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array1[0].length; j++) {
        array1[i][j] = i;
        array2[i][j] = i;
      }
    }
    assertEquals(true, Equality.equals(array1, array1));
    assertEquals(true, Equality.equals(array1, array2));
    array1[1][1] = 100;
    assertEquals(false, Equality.equals(array1, array2));

    // compare 1 dim to 2.
    final int[] array3 = new int[] { 1, 2 };
    assertEquals(false, Equality.equals(array1, array3));
    assertEquals(false, Equality.equals(array3, array1));
    assertEquals(false, Equality.equals(array2, array3));
    assertEquals(false, Equality.equals(array3, array2));

    array1 = null;
    assertEquals(false, Equality.equals(array1, array2));
    array2 = null;
    assertEquals(true, Equality.equals(array1, array2));
  }

  public void testMultiLongArray() {
    long[][] array1 = new long[2][2];
    long[][] array2 = new long[2][2];
    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array1[0].length; j++) {
        array1[i][j] = i;
        array2[i][j] = i;
      }
    }
    assertEquals(true, Equality.equals(array1, array1));
    assertEquals(true, Equality.equals(array1, array2));
    array1[1][1] = 100;
    assertEquals(false, Equality.equals(array1, array2));

    // compare 1 dim to 2.
    final long[] array3 = new long[] { 1, 2 };
    assertEquals(false, Equality.equals(array1, array3));
    assertEquals(false, Equality.equals(array3, array1));
    assertEquals(false, Equality.equals(array2, array3));
    assertEquals(false, Equality.equals(array3, array2));

    array1 = null;
    assertEquals(false, Equality.equals(array1, array2));
    array2 = null;
    assertEquals(true, Equality.equals(array1, array2));
  }

  public void testMultiFloatArray() {
    float[][] array1 = new float[2][2];
    float[][] array2 = new float[2][2];
    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array1[0].length; j++) {
        array1[i][j] = (i + 1) * (j + 1);
        array2[i][j] = (i + 1) * (j + 1);
      }
    }
    assertEquals(true, Equality.equals(array1, array1));
    assertEquals(true, Equality.equals(array1, array2));
    array1[1][1] = -0.0f;
    assertEquals(false, Equality.equals(array1, array2));

    // compare 1 dim to 2.
    final float[] array3 = new float[] { 1, 2 };
    assertEquals(false, Equality.equals(array1, array3));
    assertEquals(false, Equality.equals(array3, array1));
    assertEquals(false, Equality.equals(array2, array3));
    assertEquals(false, Equality.equals(array3, array2));

    array1 = null;
    assertEquals(false, Equality.equals(array1, array2));
    array2 = null;
    assertEquals(true, Equality.equals(array1, array2));
  }

  public void testMultiDoubleArray() {
    double[][] array1 = new double[2][2];
    double[][] array2 = new double[2][2];
    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array1[0].length; j++) {
        array1[i][j] = (i + 1) * (j + 1);
        array2[i][j] = (i + 1) * (j + 1);
      }
    }
    assertEquals(true, Equality.equals(array1, array1));
    assertEquals(true, Equality.equals(array1, array2));
    array1[1][1] = -0.0;
    assertEquals(false, Equality.equals(array1, array2));

    // compare 1 dim to 2.
    final double[] array3 = new double[] { 1, 2 };
    assertEquals(false, Equality.equals(array1, array3));
    assertEquals(false, Equality.equals(array3, array1));
    assertEquals(false, Equality.equals(array2, array3));
    assertEquals(false, Equality.equals(array3, array2));

    array1 = null;
    assertEquals(false, Equality.equals(array1, array2));
    array2 = null;
    assertEquals(true, Equality.equals(array1, array2));
  }

  public void testMultiFloatObjectArray() {
    Float[][] array1 = new Float[2][2];
    Float[][] array2 = new Float[2][2];
    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array1[0].length; j++) {
        array1[i][j] = new Float((i + 1) * (j + 1));
        array2[i][j] = new Float((i + 1) * (j + 1));
      }
    }
    assertEquals(true, Equality.equals(array1, array1));
    assertEquals(true, Equality.equals(array1, array2));
    array1[1][1] = -0.0f;
    assertEquals(false, Equality.equals(array1, array2));
    array1[1][1] = null;
    assertEquals(false, Equality.equals(array1, array2));

    // compare 1 dim to 2.
    final Float[] array3 = new Float[] { 1.0f, 2.0f };
    assertEquals(false, Equality.equals(array1, array3));
    assertEquals(false, Equality.equals(array3, array1));
    assertEquals(false, Equality.equals(array2, array3));
    assertEquals(false, Equality.equals(array3, array2));

    array1 = null;
    assertEquals(false, Equality.equals(array1, array2));
    array2 = null;
    assertEquals(true, Equality.equals(array1, array2));
  }

  public void testMultiDoubleObjectArray() {
    Double[][] array1 = new Double[2][2];
    Double[][] array2 = new Double[2][2];
    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array1[0].length; j++) {
        array1[i][j] = new Double((i + 1) * (j + 1));
        array2[i][j] = new Double((i + 1) * (j + 1));
      }
    }
    assertEquals(true, Equality.equals(array1, array1));
    assertEquals(true, Equality.equals(array1, array2));
    array1[1][1] = -0.0;
    assertEquals(false, Equality.equals(array1, array2));
    array1[1][1] = null;
    assertEquals(false, Equality.equals(array1, array2));

    // compare 1 dim to 2.
    final Double[] array3 = new Double[] { 1.0, 2.0 };
    assertEquals(false, Equality.equals(array1, array3));
    assertEquals(false, Equality.equals(array3, array1));
    assertEquals(false, Equality.equals(array2, array3));
    assertEquals(false, Equality.equals(array3, array2));

    array1 = null;
    assertEquals(false, Equality.equals(array1, array2));
    array2 = null;
    assertEquals(true, Equality.equals(array1, array2));
  }

  public void testRaggedArray() {
    final long array1[][] = new long[2][];
    final long array2[][] = new long[2][];
    for (int i = 0; i < array1.length; ++i) {
      array1[i] = new long[2];
      array2[i] = new long[2];
      for (int j = 0; j < array1[i].length; ++j) {
        array1[i][j] = (i + 1) * (j + 1);
        array2[i][j] = (i + 1) * (j + 1);
      }
    }
    assertEquals(true, Equality.equals(array1, array1));
    assertEquals(true, Equality.equals(array1, array2));
    array1[1][1] = 0;
    assertEquals(false, Equality.equals(array1, array2));
  }

  public void testMixedArray() {
    final Object array1[] = new Object[2];
    final Object array2[] = new Object[2];
    for (int i = 0; i < array1.length; ++i) {
      array1[i] = new long[2];
      array2[i] = new long[2];
      for (int j = 0; j < 2; ++j) {
        ((long[]) array1[i])[j] = (i + 1) * (j + 1);
        ((long[]) array2[i])[j] = (i + 1) * (j + 1);
      }
    }
    assertEquals(true, Equality.equals(array1, array1));
    assertEquals(true, Equality.equals(array1, array2));

    ((long[]) array1[1])[1] = 0;
    assertEquals(false, Equality.equals(array1, array2));
  }

  public void testBooleanArrayHiddenByObject() {
    final boolean[] array1 = new boolean[2];
    array1[0] = true;
    array1[1] = false;
    final boolean[] array2 = new boolean[2];
    array2[0] = true;
    array2[1] = false;
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, array1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(true, Equality.equals(obj1, array2));
    array1[1] = true;
    assertEquals(false, Equality.equals(obj1, obj2));
  }

  public void testCharArrayHiddenByObject() {
    final char[] array1 = new char[2];
    array1[0] = '1';
    array1[1] = '2';
    final char[] array2 = new char[2];
    array2[0] = '1';
    array2[1] = '2';
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, array1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(true, Equality.equals(obj1, array2));
    array1[1] = '3';
    assertEquals(false, Equality.equals(obj1, obj2));
  }

  public void testByteArrayHiddenByObject() {
    final byte[] array1 = new byte[2];
    array1[0] = 1;
    array1[1] = 2;
    final byte[] array2 = new byte[2];
    array2[0] = 1;
    array2[1] = 2;
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, array1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(true, Equality.equals(obj1, array2));
    array1[1] = 3;
    assertEquals(false, Equality.equals(obj1, obj2));
  }

  public void testShortArrayHiddenByObject() {
    final short[] array1 = new short[2];
    array1[0] = 1;
    array1[1] = 2;
    final short[] array2 = new short[2];
    array2[0] = 1;
    array2[1] = 2;
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, array1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(true, Equality.equals(obj1, array2));
    array1[1] = 3;
    assertEquals(false, Equality.equals(obj1, obj2));
  }

  public void testIntArrayHiddenByObject() {
    final int[] array1 = new int[2];
    array1[0] = 1;
    array1[1] = 2;
    final int[] array2 = new int[2];
    array2[0] = 1;
    array2[1] = 2;
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, array1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(true, Equality.equals(obj1, array2));
    array1[1] = 3;
    assertEquals(false, Equality.equals(obj1, obj2));
  }

  public void testLongArrayHiddenByObject() {
    final long[] array1 = new long[2];
    array1[0] = 1;
    array1[1] = 2;
    final long[] array2 = new long[2];
    array2[0] = 1;
    array2[1] = 2;
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, array1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(true, Equality.equals(obj1, array2));
    array1[1] = 3;
    assertEquals(false, Equality.equals(obj1, obj2));
  }

  public void testFloatArrayHiddenByObject() {
    final float[] array1 = new float[2];
    array1[0] = 1;
    array1[1] = 2;
    final float[] array2 = new float[2];
    array2[0] = 1;
    array2[1] = 2;
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, array1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(true, Equality.equals(obj1, array2));
    array1[1] = 3;
    assertEquals(false, Equality.equals(obj1, obj2));
  }

  public void testDoubleArrayHiddenByObject() {
    final double[] array1 = new double[2];
    array1[0] = 1;
    array1[1] = 2;
    final double[] array2 = new double[2];
    array2[0] = 1;
    array2[1] = 2;
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, array1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(true, Equality.equals(obj1, array2));
    array1[1] = 3;
    assertEquals(false, Equality.equals(obj1, obj2));
  }

  public void testFloatObjectArrayHiddenByObject() {
    final Float[] array1 = new Float[2];
    array1[0] = 1.0f;
    array1[1] = 2.0f;
    final Float[] array2 = new Float[2];
    array2[0] = 1.0f;
    array2[1] = 2.0f;
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, array1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(true, Equality.equals(obj1, array2));
    array1[1] = 3.0f;
    assertEquals(false, Equality.equals(obj1, obj2));
    array1[1] = null;
    assertEquals(false, Equality.equals(obj1, obj2));
  }

  public void testDoubleObjectArrayHiddenByObject() {
    final Double[] array1 = new Double[2];
    array1[0] = 1.0;
    array1[1] = 2.0;
    final Double[] array2 = new Double[2];
    array2[0] = 1.0;
    array2[1] = 2.0;
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, array1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(true, Equality.equals(obj1, array2));
    array1[1] = 3.0;
    assertEquals(false, Equality.equals(obj1, obj2));
    array1[1] = null;
    assertEquals(false, Equality.equals(obj1, obj2));
  }

  public void testObjectArrayHiddenByObject() {
    final TestObject[] array1 = new TestObject[2];
    array1[0] = new TestObject(4);
    array1[1] = new TestObject(5);
    final TestObject[] array2 = new TestObject[2];
    array2[0] = new TestObject(4);
    array2[1] = new TestObject(5);
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertEquals(true, Equality.equals(obj1, obj1));
    assertEquals(true, Equality.equals(obj1, array1));
    assertEquals(true, Equality.equals(obj1, obj2));
    assertEquals(true, Equality.equals(obj1, array2));
    array1[1].setA(6);
    assertEquals(false, Equality.equals(obj1, obj2));
  }

  /*
   * Tests two instances of classes that can be equal and that are not
   * "related". The two classes are not subclasses of each other and do not
   * share a parent aside from Object.
   */
  public void testUnrelatedClasses() {
    final Object[] x = new Object[] { new TestACanEqualB(1) };
    final Object[] y = new Object[] { new TestBCanEqualA(1) };

    // sanity checks:
    assertEquals(true, Arrays.equals(x, x));
    assertEquals(true, Arrays.equals(y, y));
    assertEquals(true, Arrays.equals(x, y));
    assertEquals(true, Arrays.equals(y, x));
    // real tests:
    assertEquals(true, x[0].equals(x[0]));
    assertEquals(true, y[0].equals(y[0]));
    assertEquals(true, x[0].equals(y[0]));
    assertEquals(true, y[0].equals(x[0]));
    assertEquals(true, Equality.equals(x, x));
    assertEquals(true, Equality.equals(y, y));
    assertEquals(true, Equality.equals(x, y));
    assertEquals(true, Equality.equals(y, x));
  }

  /*
   * Test for null element in the array.
   */
  public void testNpeForNullElement() {
    final Object[] x1 = new Object[] { new Integer(1), null, new Integer(3) };
    final Object[] x2 = new Object[] { new Integer(1), new Integer(2),
        new Integer(3) };
    assertEquals(false, Equality.equals(x1, x2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(com.github.haixing_hu.collection.primitive.BooleanCollection, com.github.haixing_hu.collection.primitive.BooleanCollection)}
   * .
   */
  @Test
  public void testBooleanCollection() {
    ArrayBooleanList col1 = new ArrayBooleanList();
    ArrayBooleanList col2 = new ArrayBooleanList();

    assertEquals(true, Equality.equals(col1, col2));
    col1.add(true);
    col2.add(true);
    assertEquals(true, Equality.equals(col1, col2));
    col1.add(false);
    col2.add(false);
    assertEquals(true, Equality.equals(col1, col2));
    col1.add(true);
    col2.add(false);
    assertEquals(false, Equality.equals(col1, col2));
    col1 = null;
    assertEquals(false, Equality.equals(col1, col2));
    col2 = null;
    assertEquals(true, Equality.equals(col1, col2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(com.github.haixing_hu.collection.primitive.CharCollection, com.github.haixing_hu.collection.primitive.CharCollection)}
   * .
   */
  @Test
  public void testCharCollection() {
    ArrayCharList col1 = new ArrayCharList(new char[] { 'a', 'b', 'c', 'd' });
    ArrayCharList col2 = new ArrayCharList(new char[] { 'a', 'b', 'c', 'd' });

    assertEquals(true, Equality.equals(col1, col2));
    col1.add('e');
    assertEquals(false, Equality.equals(col1, col2));
    col2.add('e');
    assertEquals(true, Equality.equals(col1, col2));
    col1 = null;
    assertEquals(false, Equality.equals(col1, col2));
    col2 = null;
    assertEquals(true, Equality.equals(col1, col2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(com.github.haixing_hu.collection.primitive.ByteCollection, com.github.haixing_hu.collection.primitive.ByteCollection)}
   * .
   */
  @Test
  public void testByteCollection() {
    ArrayByteList col1 = new ArrayByteList(new byte[] { 1, 2, 3, 4 });
    ArrayByteList col2 = new ArrayByteList(new byte[] { 1, 2, 3, 4 });

    assertEquals(true, Equality.equals(col1, col2));
    col1.add((byte) 5);
    assertEquals(false, Equality.equals(col1, col2));
    col2.add((byte) 5);
    assertEquals(true, Equality.equals(col1, col2));
    col1 = null;
    assertEquals(false, Equality.equals(col1, col2));
    col2 = null;
    assertEquals(true, Equality.equals(col1, col2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(com.github.haixing_hu.collection.primitive.ShortCollection, com.github.haixing_hu.collection.primitive.ShortCollection)}
   * .
   */
  @Test
  public void testShortCollection() {
    ArrayShortList col1 = new ArrayShortList();
    ArrayShortList col2 = new ArrayShortList();

    col1.add((short) 2);
    assertEquals(false, Equality.equals(col1, col2));
    col2.add((short) 2);
    assertEquals(true, Equality.equals(col1, col2));
    col1.add((short) 5);
    assertEquals(false, Equality.equals(col1, col2));
    col2.add((short) 6);
    assertEquals(false, Equality.equals(col1, col2));
    col1 = null;
    assertEquals(false, Equality.equals(col1, col2));
    col2 = null;
    assertEquals(true, Equality.equals(col1, col2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(com.github.haixing_hu.collection.primitive.IntCollection, com.github.haixing_hu.collection.primitive.IntCollection)}
   * .
   */
  @Test
  public void testIntCollection() {
    ArrayIntList col1 = new ArrayIntList();
    ArrayIntList col2 = new ArrayIntList();

    col1.add(2);
    assertEquals(false, Equality.equals(col1, col2));
    col2.add(2);
    assertEquals(true, Equality.equals(col1, col2));
    col1.add(5);
    assertEquals(false, Equality.equals(col1, col2));
    col2.add(6);
    assertEquals(false, Equality.equals(col1, col2));
    col1 = null;
    assertEquals(false, Equality.equals(col1, col2));
    col2 = null;
    assertEquals(true, Equality.equals(col1, col2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(com.github.haixing_hu.collection.primitive.LongCollection, com.github.haixing_hu.collection.primitive.LongCollection)}
   * .
   */
  @Test
  public void testLongCollection() {
    ArrayLongList col1 = new ArrayLongList();
    ArrayLongList col2 = new ArrayLongList();

    col1.add(2);
    assertEquals(false, Equality.equals(col1, col2));
    col2.add(2);
    assertEquals(true, Equality.equals(col1, col2));
    col1.add(5);
    assertEquals(false, Equality.equals(col1, col2));
    col2.add(6);
    assertEquals(false, Equality.equals(col1, col2));
    col1 = null;
    assertEquals(false, Equality.equals(col1, col2));
    col2 = null;
    assertEquals(true, Equality.equals(col1, col2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(com.github.haixing_hu.collection.primitive.FloatCollection, com.github.haixing_hu.collection.primitive.FloatCollection)}
   * .
   */
  @Test
  public void testFloatCollection() {
    ArrayFloatList col1 = new ArrayFloatList();
    ArrayFloatList col2 = new ArrayFloatList();

    col1.add(2);
    assertEquals(false, Equality.equals(col1, col2));
    col2.add(2);
    assertEquals(true, Equality.equals(col1, col2));
    col1.add(5);
    assertEquals(false, Equality.equals(col1, col2));
    col2.add(6);
    assertEquals(false, Equality.equals(col1, col2));
    col1 = null;
    assertEquals(false, Equality.equals(col1, col2));
    col2 = null;
    assertEquals(true, Equality.equals(col1, col2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #valueEquals(com.github.haixing_hu.collection.primitive.FloatCollection, com.github.haixing_hu.collection.primitive.FloatCollection, float)}
   * .
   */
  @Test
  public void testFloatCollectionWithEpsilon() {
    ArrayFloatList col1 = new ArrayFloatList();
    ArrayFloatList col2 = new ArrayFloatList();
    final float epsilon = 0.01f;

    col1.add(2);
    assertEquals(false, Equality.valueEquals(col1, col2, epsilon));
    col2.add(2 + epsilon);
    assertEquals(true, Equality.valueEquals(col1, col2, epsilon));
    col1.add(5);
    assertEquals(false, Equality.valueEquals(col1, col2, epsilon));
    col2.add(6);
    assertEquals(false, Equality.valueEquals(col1, col2, epsilon));
    col1 = null;
    assertEquals(false, Equality.valueEquals(col1, col2, epsilon));
    col2 = null;
    assertEquals(true, Equality.valueEquals(col1, col2, epsilon));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #equals(com.github.haixing_hu.collection.primitive.DoubleCollection, com.github.haixing_hu.collection.primitive.DoubleCollection)}
   * .
   */
  @Test
  public void testDoubleCollection() {
    ArrayDoubleList col1 = new ArrayDoubleList();
    ArrayDoubleList col2 = new ArrayDoubleList();

    col1.add(2);
    assertEquals(false, Equality.equals(col1, col2));
    col2.add(2);
    assertEquals(true, Equality.equals(col1, col2));
    col1.add(5);
    assertEquals(false, Equality.equals(col1, col2));
    col2.add(6);
    assertEquals(false, Equality.equals(col1, col2));
    col1 = null;
    assertEquals(false, Equality.equals(col1, col2));
    col2 = null;
    assertEquals(true, Equality.equals(col1, col2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #valueEquals(com.github.haixing_hu.collection.primitive.DoubleCollection, com.github.haixing_hu.collection.primitive.DoubleCollection, double)}
   * .
   */
  @Test
  public void testDoubleCollectionWithEpsilon() {
    ArrayDoubleList col1 = new ArrayDoubleList();
    ArrayDoubleList col2 = new ArrayDoubleList();
    final double epsilon = 0.01;

    col1.add(2);
    assertEquals(false, Equality.valueEquals(col1, col2, epsilon));
    col2.add(2 + epsilon);
    assertEquals(true, Equality.valueEquals(col1, col2, epsilon));
    col1.add(5);
    assertEquals(false, Equality.valueEquals(col1, col2, epsilon));
    col2.add(6);
    assertEquals(false, Equality.valueEquals(col1, col2, epsilon));
    col1 = null;
    assertEquals(false, Equality.valueEquals(col1, col2, epsilon));
    col2 = null;
    assertEquals(true, Equality.valueEquals(col1, col2, epsilon));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #valueEquals(com.github.haixing_hu.collection.primitive.Collection, com.github.haixing_hu.collection.primitive.Collection, float)}
   * .
   */
  @Test
  public void testCollection() {
    ArrayList<Character> col1 = new ArrayList<Character>();
    col1.add(new Character('a'));
    ArrayList<Character> col2 = new ArrayList<Character>();
    col2.add(new Character('a'));

    assertEquals(true, Equality.equals(col1, col2));
    col2.add(0, new Character('b'));
    assertEquals(false, Equality.equals(col1, col2));
    col2 = null;
    assertEquals(false, Equality.equals(col1, col2));
    col1 = null;
    assertEquals(true, Equality.equals(col1, col2));
  }

  /**
   * Test method for
   * {@link com.github.haixing_hu.lang.Equality #valueEquals(com.github.haixing_hu.collection.primitive.Collection, com.github.haixing_hu.collection.primitive.Collection, float)}
   * .
   */
  @Test
  public void testCollectionWithEpsilon() {
    ArrayList<Double> col1 = new ArrayList<Double>();
    col1.add(0.002);
    final double epsilon = 0.000001;
    ArrayList<Double> col2 = new ArrayList<Double>();
    col2.add((0.002));

    assertEquals(true, Equality.valueEquals(col1, col2, epsilon));
    col1.add(0, 0.3);
    assertEquals(false, Equality.equals(col1, col2));
    col2 = null;
    assertEquals(false, Equality.equals(col1, col2));
    col1 = null;
    assertEquals(true, Equality.equals(col1, col2));
  }
}
