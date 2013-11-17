package com.github.haixing_hu.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import com.github.haixing_hu.collection.primitive.impl.ArrayBooleanList;
import com.github.haixing_hu.collection.primitive.impl.ArrayByteList;
import com.github.haixing_hu.collection.primitive.impl.ArrayCharList;
import com.github.haixing_hu.collection.primitive.impl.ArrayDoubleList;
import com.github.haixing_hu.collection.primitive.impl.ArrayFloatList;
import com.github.haixing_hu.collection.primitive.impl.ArrayIntList;
import com.github.haixing_hu.collection.primitive.impl.ArrayLongList;
import com.github.haixing_hu.collection.primitive.impl.ArrayShortList;

import static com.github.haixing_hu.lang.Comparison.*;

import static org.junit.Assert.assertEquals;

public class ComparisonTest {

  static public enum TestEnum {
    a,
    b,
    c,
    d,
    e
  };

  class TestClassA {
  }

  class TestClassB {
  }

  class TestClassZ {
  }

  @Test
  public void testBoolean() {
    assertEquals(0, compare(true, true));
    assertEquals(0, compare(false, false));
    assertEquals(1, compare(true, false));
    assertEquals(-1, compare(false, true));
  }

  @Test
  public void testBooleanArray() {
    final boolean[] array1 = { true, true, false };
    boolean[] array2 = { true, true, false };
    boolean[] array3 = { true, true, false, true };
    final boolean[] array4 = { true, false, false, true };
    assertEquals(0, compare(array1, array2));
    assertEquals(-1, compare(array1, array3));
    assertEquals(-1, compare(array4, array3));
    assertEquals(1, compare(array3, array4));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array3 = null;
    assertEquals(0, compare(array2, array3));
  }

  @Test
  public void testBooleanArrayInt() {
    final boolean[] array1 = { true, true, false };
    final boolean[] array2 = { true, true, false };
    final boolean[] array3 = { true, true, false, true };
    assertEquals(0, compare(array1, 1, array2, 1));
    assertEquals(0, compare(array1, 3, array2, 3));
    assertEquals(-2, compare(array1, 1, array2, 3));
    assertEquals(2, compare(array1, 3, array2, 1));
    assertEquals(-1, compare(array1, 2, array2, 3));
    assertEquals(1, compare(array1, 3, array2, 2));
    assertEquals(-1, compare(array1, array3));
  }

  @Test
  public void testBooleanObject() {
    Boolean value1 = true;
    Boolean value2 = true;

    assertEquals(0, compare(value1, value2));
    value2 = false;
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value1 = false;
    assertEquals(0, compare(value1, value2));
    value2 = null;
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
  }

  @Test
  public void testBooleanObjectArray() {
    Boolean[] array1 = { true, true, false, true };
    Boolean[] array2 = { true, true, false, true };
    final Boolean[] array3 = { true, true, null };
    final Boolean[] array4 = { true, false, null };
    final Boolean[] array5 = { true, true };

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    assertEquals(1, compare(array3, array4));
    assertEquals(-1, compare(array4, array3));
    assertEquals(2, compare(array1, array5));
    assertEquals(-2, compare(array5, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testBooleanObjectArrayInt() {
    final Boolean[] array1 = { true, true, false, true };
    final Boolean[] array2 = { true, true, false, true };
    final Boolean[] array3 = { true, true, null };
    final Boolean[] array4 = { true, false, null };

    assertEquals(1, compare(array1, 4, array2, 3));
    assertEquals(3, compare(array1, 4, array2, 1));
    assertEquals(-1, compare(array2, 3, array1, 4));
    assertEquals(-3, compare(array2, 1, array1, 4));
    assertEquals(1, compare(array1, 3, array3, 3));
    assertEquals(-1, compare(array3, 3, array1, 3));
    assertEquals(1, compare(array3, 2, array4, 2));
    assertEquals(-1, compare(array4, 2, array3, 2));
    assertEquals(1, compare(array3, 3, array4, 3));
    assertEquals(-1, compare(array4, 3, array3, 3));
  }

  @Test
  public void testChar() {
    assertEquals(32, compare('a', 'A'));
    assertEquals(-32, compare('A', 'a'));
    assertEquals(-5, compare('a', 'f'));
    assertEquals(65, compare('a', ' '));
    assertEquals(97, compare('a', '\0'));
  }

  @Test
  public void testCharArray() {
    char[] array1 = { 'a', 'b', 'c' };
    char[] array2 = { 'a', 'b', 'c' };
    assertEquals(0, compare(array1, array2));
    array2[2] = 'C';
    assertEquals(32, compare(array1, array2));
    assertEquals(-32, compare(array2, array1));
    array2[2] = 'd';
    assertEquals(-1, compare(array1, array2));
    assertEquals(1, compare(array2, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testIgnoreCaseCharArray() {
    char[] array1 = { 'a', 'b', 'c' };
    char[] array2 = { 'a', 'b', 'c' };
    assertEquals(0, compareIgnoreCase(array1, array2));
    array2[2] = 'C';
    assertEquals(0, compareIgnoreCase(array1, array2));
    array2[2] = 'd';
    assertEquals(-1, compareIgnoreCase(array1, array2));
    assertEquals(1, compareIgnoreCase(array2, array1));
    array2 = null;
    assertEquals(1, compareIgnoreCase(array1, array2));
    assertEquals(-1, compareIgnoreCase(array2, array1));
    array1 = null;
    assertEquals(0, compareIgnoreCase(array1, array2));
  }

  @Test
  public void testCharArrayInt() {
    char[] array1 = { 'a', 'b', 'c' };
    char[] array2 = { 'a', 'b', 'c' };
    char[] array3 = { 'a', 'b', 'c', 'd' };

    assertEquals(0, compare(array1, 3, array2, 3));
    assertEquals(0, compare(array1, 3, array3, 3));
    assertEquals(-1, compare(array1, 3, array3, 4));
    assertEquals(1, compare(array3, 4, array1, 3));
    array2[1] = 'B';
    assertEquals(32, compare(array1, 3, array2, 3));
    array2[1] = 'd';
    assertEquals(-2, compare(array1, 3, array2, 3));
  }

  @Test
  public void testIgnoreCaseCharArrayInt() {
    char[] array1 = { 'a', 'b', 'c' };
    char[] array2 = { 'a', 'b', 'c' };
    char[] array3 = { 'a', 'b', 'c', 'd' };

    assertEquals(0, compareIgnoreCase(array1, 3, array2, 3));
    assertEquals(0, compareIgnoreCase(array1, 3, array3, 3));
    assertEquals(-1, compareIgnoreCase(array1, 3, array3, 4));
    assertEquals(1, compareIgnoreCase(array3, 4, array1, 3));
    array2[1] = 'B';
    assertEquals(0, compareIgnoreCase(array1, 3, array2, 3));
    array2[1] = 'd';
    assertEquals(-2, compareIgnoreCase(array1, 3, array2, 3));
  }

  @Test
  public void testCharacter() {
    Character value1 = 'a';
    Character value2 = 'a';

    assertEquals(0, compare(value1, value2));
    value2 = 'A';
    assertEquals(32, compare(value1, value2));
    assertEquals(-32, compare(value2, value1));
    value2 = ' ';
    assertEquals(65, compare(value1, value2));
    assertEquals(-65, compare(value2, value1));
    value2 = null;
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value1 = null;
    assertEquals(0, compare(value1, value2));
  }

  @Test
  public void testCharacterArray() {
    Character[] array1 = { 'a', 'b', 'c' };
    Character[] array2 = { 'a', 'b', 'c' };
    assertEquals(0, compare(array1, array2));
    array2[2] = 'C';
    assertEquals(32, compare(array1, array2));
    assertEquals(-32, compare(array2, array1));
    array2[2] = 'd';
    assertEquals(-1, compare(array1, array2));
    assertEquals(1, compare(array2, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testIgnoreCaseCharacterArray() {
    Character[] array1 = { 'a', 'b', 'c' };
    Character[] array2 = { 'a', 'b', 'c' };
    assertEquals(0, compareIgnoreCase(array1, array2));
    array2[2] = 'C';
    assertEquals(0, compareIgnoreCase(array1, array2));
    array2[2] = 'd';
    assertEquals(-1, compareIgnoreCase(array1, array2));
    assertEquals(1, compareIgnoreCase(array2, array1));
    array2 = null;
    assertEquals(1, compareIgnoreCase(array1, array2));
    assertEquals(-1, compareIgnoreCase(array2, array1));
    array1 = null;
    assertEquals(0, compareIgnoreCase(array1, array2));
  }

  @Test
  public void testCharacterArrayInt() {
    Character[] array1 = { 'a', 'b', 'c' };
    Character[] array2 = { 'a', 'b', 'c' };
    Character[] array3 = { 'a', 'b', 'c', 'd' };

    assertEquals(0, compare(array1, 3, array2, 3));
    assertEquals(0, compare(array1, 3, array3, 3));
    assertEquals(-1, compare(array1, 3, array3, 4));
    assertEquals(1, compare(array3, 4, array1, 3));
    array2[1] = 'B';
    assertEquals(32, compare(array1, 3, array2, 3));
    array2[1] = 'd';
    assertEquals(-2, compare(array1, 3, array2, 3));
  }

  @Test
  public void testIgnoreCaseCharacterArrayInt() {
    Character[] array1 = { 'a', 'b', 'c' };
    Character[] array2 = { 'a', 'b', 'c' };
    Character[] array3 = { 'a', 'b', 'c', 'd' };

    assertEquals(0, compareIgnoreCase(array1, 3, array2, 3));
    assertEquals(0, compareIgnoreCase(array1, 3, array3, 3));
    assertEquals(-1, compareIgnoreCase(array1, 3, array3, 4));
    assertEquals(1, compareIgnoreCase(array3, 4, array1, 3));
    array2[1] = 'B';
    assertEquals(0, compareIgnoreCase(array1, 3, array2, 3));
    array2[1] = 'd';
    assertEquals(-2, compareIgnoreCase(array1, 3, array2, 3));
  }

  @Test
  public void testByte() {
    byte[] array = { (byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE };

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        assertEquals(array[i] - array[j], compare(array[i], array[j]));
      }
    }
  }

  @Test
  public void testByteArray() {
    byte[] array1 = { (byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE };
    byte[] array2 = { (byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE };
    byte[] array3 = { (byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE - 1 };
    byte[] array4 = { (byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE };

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    array3[array3.length - 1] = (byte) 0;
    assertEquals(Byte.MAX_VALUE, compare(array1, array3));
    assertEquals(-Byte.MAX_VALUE, compare(array3, array1));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testByteArrayInt() {
    byte[] array1 = { (byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE };
    byte[] array2 = { (byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE };
    byte[] array3 = { (byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE - 1 };

    assertEquals(0, compare(array1, array1.length, array2, array2.length));
    assertEquals(2, compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-2, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1, compare(array1, array1.length, array3, array3.length));
    assertEquals(-1, compare(array3, array3.length, array1, array1.length));
  }

  @Test
  public void testByteObject() {
    Byte[] array = { (byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE };

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        assertEquals(array[i] - array[j], compare(array[i], array[j]));
      }
    }

    array[0] = null;
    assertEquals(1, compare(array[1], array[0]));
    assertEquals(-1, compare(array[0], array[1]));
    array[1] = null;
    assertEquals(0, compare(array[1], array[0]));
  }

  @Test
  public void testByteObjectArray() {
    Byte[] array1 = { (byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE };
    Byte[] array2 = { (byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE };
    Byte[] array3 = { (byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE - 1 };
    Byte[] array4 = { (byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE };

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    array3[array3.length - 1] = (byte) 0;
    assertEquals(Byte.MAX_VALUE, compare(array1, array3));
    assertEquals(-Byte.MAX_VALUE, compare(array3, array1));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2[0] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1[0] = null;
    assertEquals(0, compare(array1, array2));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testByteObjectArrayInt() {
    Byte[] array1 = { (byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE };
    Byte[] array2 = { (byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE };
    Byte[] array3 = { (byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE - 1 };

    assertEquals(0, compare(array1, array1.length, array2, array2.length));
    assertEquals(2, compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-2, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1, compare(array1, array1.length, array3, array3.length));
    assertEquals(-1, compare(array3, array3.length, array1, array1.length));
    array3[1] = null;
    assertEquals(1,
        compare(array1, array1.length - 2, array3, array3.length - 2));
    array1[1] = null;
    assertEquals(0,
        compare(array1, array1.length - 2, array3, array3.length - 2));
  }

  @Test
  public void testShort() {
    short[] array = { (short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE };

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        assertEquals(array[i] - array[j], compare(array[i], array[j]));
      }
    }
  }

  @Test
  public void testShortArray() {
    short[] array1 = { (short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE };
    short[] array2 = { (short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE };
    short[] array3 = { (short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE - 1 };
    short[] array4 = { (short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE };

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    array3[array3.length - 1] = (byte) 0;
    assertEquals(Short.MAX_VALUE, compare(array1, array3));
    assertEquals(-Short.MAX_VALUE, compare(array3, array1));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testShortArrayInt() {
    short[] array1 = { (short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE };
    short[] array2 = { (short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE };
    short[] array3 = { (short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE - 1 };

    assertEquals(0, compare(array1, array1.length, array2, array2.length));
    assertEquals(2, compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-2, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1, compare(array1, array1.length, array3, array3.length));
    assertEquals(-1, compare(array3, array3.length, array1, array1.length));
  }

  @Test
  public void testShortObject() {
    Short[] array = { (short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE };

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        assertEquals(array[i] - array[j], compare(array[i], array[j]));
      }
    }

    array[0] = null;
    assertEquals(1, compare(array[1], array[0]));
    assertEquals(-1, compare(array[0], array[1]));
    array[1] = null;
    assertEquals(0, compare(array[1], array[0]));
  }

  @Test
  public void testShortObjectArray() {
    Short[] array1 = { (short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE };
    Short[] array2 = { (short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE };
    Short[] array3 = { (short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE - 1 };
    Short[] array4 = { (short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE };

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    array3[array3.length - 1] = (short) 0;
    assertEquals(Short.MAX_VALUE, compare(array1, array3));
    assertEquals(-Short.MAX_VALUE, compare(array3, array1));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2[0] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1[0] = null;
    assertEquals(0, compare(array1, array2));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testShortObjectArrayInt() {
    Short[] array1 = { (short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE };
    Short[] array2 = { (short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE };
    Short[] array3 = { (short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE - 1 };

    assertEquals(0, compare(array1, array1.length, array2, array2.length));
    assertEquals(2, compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-2, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1, compare(array1, array1.length, array3, array3.length));
    assertEquals(-1, compare(array3, array3.length, array1, array1.length));
    array3[1] = null;
    assertEquals(1,
        compare(array1, array1.length - 2, array3, array3.length - 2));
    array1[1] = null;
    assertEquals(0,
        compare(array1, array1.length - 2, array3, array3.length - 2));
  }

  @Test
  public void testInt() {
    int[] array = { (int) 0, (int) -1, (int) 1, (int) (Integer.MIN_VALUE / 2),
        (int) (Integer.MAX_VALUE / 2), Integer.MIN_VALUE, Integer.MAX_VALUE };

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        assertEquals(array[i] - array[j], compare(array[i], array[j]));
      }
    }
  }

  @Test
  public void testIntArray() {
    int[] array1 = { (int) 0, (int) -1, (int) 1, (int) (Integer.MIN_VALUE / 2),
        (int) (Integer.MAX_VALUE / 2), Integer.MIN_VALUE, Integer.MAX_VALUE };
    int[] array2 = { (int) 0, (int) -1, (int) 1, (int) (Integer.MIN_VALUE / 2),
        (int) (Integer.MAX_VALUE / 2), Integer.MIN_VALUE, Integer.MAX_VALUE };
    int[] array3 = { (int) 0, (int) -1, (int) 1, (int) (Integer.MIN_VALUE / 2),
        (int) (Integer.MAX_VALUE / 2), Integer.MIN_VALUE, Integer.MAX_VALUE - 1 };
    int[] array4 = { (int) 0, (int) -1, (int) 1, (int) (Integer.MIN_VALUE / 2),
        (int) (Integer.MAX_VALUE / 2), Integer.MIN_VALUE };

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    array3[array3.length - 1] = (int) 0;
    assertEquals(Integer.MAX_VALUE, compare(array1, array3));
    assertEquals(-Integer.MAX_VALUE, compare(array3, array1));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testIntArrayInt() {
    int[] array1 = { (int) 0, (int) -1, (int) 1, (int) (Integer.MIN_VALUE / 2),
        (int) (Integer.MAX_VALUE / 2), Integer.MIN_VALUE, Integer.MAX_VALUE };
    int[] array2 = { (int) 0, (int) -1, (int) 1, (int) (Integer.MIN_VALUE / 2),
        (int) (Integer.MAX_VALUE / 2), Integer.MIN_VALUE, Integer.MAX_VALUE };
    int[] array3 = { (int) 0, (int) -1, (int) 1, (int) (Integer.MIN_VALUE / 2),
        (int) (Integer.MAX_VALUE / 2), Integer.MIN_VALUE, Integer.MAX_VALUE - 1 };

    assertEquals(0, compare(array1, array1.length, array2, array2.length));
    assertEquals(2, compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-2, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1, compare(array1, array1.length, array3, array3.length));
    assertEquals(-1, compare(array3, array3.length, array1, array1.length));
  }

  @Test
  public void testInteger() {
    Integer[] array = { (int) 0, (int) -1, (int) 1,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MIN_VALUE, Integer.MAX_VALUE };

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        assertEquals(array[i] - array[j], compare(array[i], array[j]));
      }
    }

    array[0] = null;
    assertEquals(1, compare(array[1], array[0]));
    assertEquals(-1, compare(array[0], array[1]));
    array[1] = null;
    assertEquals(0, compare(array[1], array[0]));
  }

  @Test
  public void testIntegerArray() {
    Integer[] array1 = { (int) 0, (int) -1, (int) 1,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MIN_VALUE, Integer.MAX_VALUE };
    Integer[] array2 = { (int) 0, (int) -1, (int) 1,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MIN_VALUE, Integer.MAX_VALUE };
    Integer[] array3 = { (int) 0, (int) -1, (int) 1,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MIN_VALUE, Integer.MAX_VALUE - 1 };
    Integer[] array4 = { (int) 0, (int) -1, (int) 1,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MIN_VALUE };

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    array3[array3.length - 1] = (int) 0;
    assertEquals(Integer.MAX_VALUE, compare(array1, array3));
    assertEquals(-Integer.MAX_VALUE, compare(array3, array1));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2[0] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1[0] = null;
    assertEquals(0, compare(array1, array2));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testIntegerArrayInt() {
    Integer[] array1 = { (int) 0, (int) -1, (int) 1,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MIN_VALUE, Integer.MAX_VALUE };
    Integer[] array2 = { (int) 0, (int) -1, (int) 1,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MIN_VALUE, Integer.MAX_VALUE };
    Integer[] array3 = { (int) 0, (int) -1, (int) 1,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MIN_VALUE, Integer.MAX_VALUE - 1 };

    assertEquals(0, compare(array1, array1.length, array2, array2.length));
    assertEquals(2, compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-2, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1, compare(array1, array1.length, array3, array3.length));
    assertEquals(-1, compare(array3, array3.length, array1, array1.length));
    array3[1] = null;
    assertEquals(1,
        compare(array1, array1.length - 2, array3, array3.length - 2));
    array1[1] = null;
    assertEquals(0,
        compare(array1, array1.length - 2, array3, array3.length - 2));
  }

  @Test
  public void testLong() {
    long[] array = { (long) 0, (long) -1, (long) 1,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE };

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        if (array[i] != array[j]) {
          assertEquals((array[i] > array[j] ? 1 : -1),
              compare(array[i], array[j]));
        } else {
          assertEquals(0, compare(array[i], array[j]));
        }
      }
    }
  }

  @Test
  public void testLongArray() {
    long[] array1 = { (long) 0, (long) -1, (long) 1,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE };
    long[] array2 = { (long) 0, (long) -1, (long) 1,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE };
    long[] array3 = { (long) 0, (long) -1, (long) 1,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE - 1 };
    long[] array4 = { (long) 0, (long) -1, (long) 1,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MIN_VALUE };

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    array3[array3.length - 1] = (long) 0;
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testLongArrayInt() {
    long[] array1 = { (long) 0, (long) -1, (long) 1,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE };
    long[] array2 = { (long) 0, (long) -1, (long) 1,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE };
    long[] array3 = { (long) 0, (long) -1, (long) 1,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE - 1 };

    assertEquals(0, compare(array1, array1.length, array2, array2.length));
    assertEquals(2, compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-2, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1, compare(array1, array1.length, array3, array3.length));
    assertEquals(-1, compare(array3, array3.length, array1, array1.length));
  }

  @Test
  public void testLongObject() {
    Long[] array = { (long) 0, (long) -1, (long) 1,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE };

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        if (array[i] > array[j]) {
          assertEquals(1, compare(array[i], array[j]));
        } else if (array[i] < array[j]) {
          assertEquals(-1, compare(array[i], array[j]));
        } else {
          assertEquals(0, compare(array[i], array[j]));
        }

      }
    }

    array[0] = null;
    assertEquals(1, compare(array[1], array[0]));
    assertEquals(-1, compare(array[0], array[1]));
    array[1] = null;
    assertEquals(0, compare(array[1], array[0]));
  }

  @Test
  public void testLongObjectArray() {
    Long[] array1 = { (long) 0, (long) -1, (long) 1,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE };
    Long[] array2 = { (long) 0, (long) -1, (long) 1,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE };
    Long[] array3 = { (long) 0, (long) -1, (long) 1,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE - 1 };
    Long[] array4 = { (long) 0, (long) -1, (long) 1,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MIN_VALUE };

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    array3[array3.length - 1] = (long) 0;
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2[0] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1[0] = null;
    assertEquals(0, compare(array1, array2));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testLongObjectArrayInt() {
    Long[] array1 = { (long) 0, (long) -1, (long) 1,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE };
    Long[] array2 = { (long) 0, (long) -1, (long) 1,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE };
    Long[] array3 = { (long) 0, (long) -1, (long) 1,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE - 1 };

    assertEquals(0, compare(array1, array1.length, array2, array2.length));
    assertEquals(2, compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-2, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1, compare(array1, array1.length, array3, array3.length));
    assertEquals(-1, compare(array3, array3.length, array1, array1.length));
    array3[1] = null;
    assertEquals(1,
        compare(array1, array1.length - 2, array3, array3.length - 2));
    array1[1] = null;
    assertEquals(0,
        compare(array1, array1.length - 2, array3, array3.length - 2));
  }

  @Test
  public void testFloat() {
    float[] array = { (float) 0.0, (float) -1.0, (float) 1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE };

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        int inti = Float.floatToIntBits(array[i]);
        int intj = Float.floatToIntBits(array[j]);
        if (array[i] > array[j] || inti > intj) {
          assertEquals(1, compare(array[i], array[j]));
        } else if (array[i] < array[j] || inti < intj) {
          assertEquals(-1, compare(array[i], array[j]));
        } else {
          assertEquals(0, compare(array[i], array[j]));
        }
      }
    }
  }

  @Test
  public void testFloatArray() {
    float[] array1 = { (float) 0.0, (float) -1.0, (float) 1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE };
    float[] array2 = { (float) 0.0, (float) -1.0, (float) 1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE };
    float[] array3 = { (float) 0.0, (float) -1.0, (float) 1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        (float) 0.0 };
    float[] array4 = { (float) 0.0, (float) -1.0, (float) 1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MIN_VALUE };

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testFloatArrayInt() {
    float[] array1 = { (float) 0.0, (float) -1.0, (float) 1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE };
    float[] array2 = { (float) 0.0, (float) -1.0, (float) 1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE };
    float[] array3 = { (float) 0.0, (float) -1.0, (float) 1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        (float) 0.0, };

    assertEquals(0, compare(array1, array1.length, array2, array2.length));
    assertEquals(2, compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-2, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1, compare(array1, array1.length, array3, array3.length));
    assertEquals(-1, compare(array3, array3.length, array1, array1.length));
  }

  @Test
  public void testFloatObject() {
    Float[] array = { (float) 0.0, (float) -1.0, (float) 1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE };

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        int inti = Float.floatToIntBits(array[i]);
        int intj = Float.floatToIntBits(array[j]);
        if (array[i] > array[j] || inti > intj) {
          assertEquals(1, compare(array[i], array[j]));
        } else if (array[i] < array[j] || inti < intj) {
          assertEquals(-1, compare(array[i], array[j]));
        } else {
          assertEquals(0, compare(array[i], array[j]));
        }
      }
    }

    array[0] = null;
    assertEquals(1, compare(array[1], array[0]));
    assertEquals(-1, compare(array[0], array[1]));
    array[1] = null;
    assertEquals(0, compare(array[1], array[0]));
  }

  @Test
  public void testFloatObjectArray() {
    Float[] array1 = { (float) 0.0, (float) -1.0, (float) 1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE };
    Float[] array2 = { (float) 0.0, (float) -1.0, (float) 1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE };
    Float[] array3 = { (float) 0.0, (float) -1.0, (float) 1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        (float) 0.0 };
    Float[] array4 = { (float) 0.0, (float) -1.0, (float) 1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MIN_VALUE };

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2[0] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1[0] = null;
    assertEquals(0, compare(array1, array2));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testFloatObjectArrayInt() {
    Float[] array1 = { (float) 0.0, (float) -1.0, (float) 1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE };
    Float[] array2 = { (float) 0.0, (float) -1.0, (float) 1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE };
    Float[] array3 = { (float) 0.0, (float) -1.0, (float) 1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        (float) 0.0 };

    assertEquals(0, compare(array1, array1.length, array2, array2.length));
    assertEquals(2, compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-2, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1, compare(array1, array1.length, array3, array3.length));
    array3[1] = null;
    assertEquals(1,
        compare(array1, array1.length - 2, array3, array3.length - 2));
    array1[1] = null;
    assertEquals(0,
        compare(array1, array1.length - 3, array3, array3.length - 2));
  }

  @Test
  public void testFloatWithEpsilon() {
    float epsilon = 0.0001f;
    float value1 = 0.0f;
    float value2 = 0.0f + epsilon;

    assertEquals(0, compare(value1, value2, epsilon));
    value1 = -epsilon;
    assertEquals(-1, compare(value1, value2, epsilon));
    assertEquals(1, compare(value2, value1, epsilon));
    value1 = Float.MAX_VALUE;
    value2 = Float.MAX_VALUE + epsilon;
    assertEquals(0, compare(value1, value2, epsilon));
  }

  @Test
  public void testFloatArrayWithEpsilon() {
    float epsilon = 0.0001f;
    float[] array1 = { (float) 0.0, (float) 1.0, (float) -1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE };
    float[] array2 = { (float) 0.0 + epsilon, (float) 1.0, (float) -1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE };
    float[] array3 = { (float) 0.0 + epsilon * 2, (float) 1.0, (float) -1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE };

    assertEquals(0, compare(array1, array2, epsilon));
    assertEquals(-1, compare(array1, array3, epsilon));
    array2 = null;
    assertEquals(1, compare(array1, array2, epsilon));
    assertEquals(-1, compare(array2, array1, epsilon));
    array1 = null;
    assertEquals(0, compare(array1, array2, epsilon));
  }

  @Test
  public void testFloatArrayIntWithEpsilon() {
    float epsilon = 0.001f;
    float[] array1 = { (float) 0.0, (float) 1.0, (float) -1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE };
    float[] array2 = { (float) 0.0 + epsilon, (float) 1.0 + epsilon * 2,
        (float) -1.0, (float) (Float.MIN_VALUE / 2),
        (float) (Float.MAX_VALUE / 2), Float.MIN_VALUE, Float.MAX_VALUE };
    assertEquals(0, compare(array1, 1, array2, 1, epsilon));
    assertEquals(-1,
        compare(array1, array1.length, array2, array2.length, epsilon));
  }

  @Test
  public void testFloatObjectWithEpsilon() {
    float epsilon = 0.0001f;
    Float value1 = 0.0f;
    Float value2 = 0.0f + epsilon;

    assertEquals(0, compare(value1, value2, epsilon));
    value1 = -epsilon;
    assertEquals(-1, compare(value1, value2, epsilon));
    assertEquals(1, compare(value2, value1, epsilon));
    value1 = Float.MAX_VALUE;
    value2 = Float.MAX_VALUE + epsilon;
    assertEquals(0, compare(value1, value2, epsilon));
    value2 = null;
    assertEquals(1, compare(value1, value2, epsilon));
    assertEquals(-1, compare(value2, value1, epsilon));
    value1 = null;
    assertEquals(0, compare(value1, value2, epsilon));
  }

  @Test
  public void testFloatObjectArrayWithEpsilon() {
    float epsilon = 0.0001f;
    Float[] array1 = { (float) 0.0, (float) 1.0, (float) -1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE };
    Float[] array2 = { (float) 0.0 + epsilon, (float) 1.0, (float) -1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE };
    Float[] array3 = { (float) 0.0 + epsilon * 2, (float) 1.0, (float) -1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE };

    assertEquals(0, compare(array1, array2, epsilon));
    assertEquals(-1, compare(array1, array3, epsilon));
    array2 = null;
    assertEquals(1, compare(array1, array2, epsilon));
    assertEquals(-1, compare(array2, array1, epsilon));
    array1 = null;
    assertEquals(0, compare(array1, array2, epsilon));
  }

  @Test
  public void testFloatObjectArrayIntWithEpsilon() {
    float epsilon = 0.001f;
    Float[] array1 = { (float) 0.0, (float) 1.0, (float) -1.0,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE };
    Float[] array2 = { (float) 0.0 + epsilon, (float) 1.0 + epsilon * 2,
        (float) -1.0, (float) (Float.MIN_VALUE / 2),
        (float) (Float.MAX_VALUE / 2), Float.MIN_VALUE, Float.MAX_VALUE };
    assertEquals(0, compare(array1, 1, array2, 1, epsilon));
    assertEquals(-1,
        compare(array1, array1.length, array2, array2.length, epsilon));
  }

  @Test
  public void testDouble() {
    double[] array = { (double) 0.0, (double) -1.0, (double) 1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE };

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        long longi = Double.doubleToLongBits(array[i]);
        long longj = Double.doubleToLongBits(array[j]);
        if (array[i] > array[j] || longi > longj) {
          assertEquals(1, compare(array[i], array[j]));
        } else if (array[i] < array[j] || longi < longj) {
          assertEquals(-1, compare(array[i], array[j]));
        } else {
          assertEquals(0, compare(array[i], array[j]));
        }
      }
    }
  }

  @Test
  public void testDoubleArray() {
    double[] array1 = { (double) 0.0, (double) -1.0, (double) 1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE };
    double[] array2 = { (double) 0.0, (double) -1.0, (double) 1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE };
    double[] array3 = { (double) 0.0, (double) -1.0, (double) 1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        (double) 0.0 };
    double[] array4 = { (double) 0.0, (double) -1.0, (double) 1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        Double.MIN_VALUE };

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testDoubleArrayInt() {
    double[] array1 = { (double) 0.0, (double) -1.0, (double) 1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE };
    double[] array2 = { (double) 0.0, (double) -1.0, (double) 1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE };
    double[] array3 = { (double) 0.0, (double) -1.0, (double) 1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        (double) 0.0, };

    assertEquals(0, compare(array1, array1.length, array2, array2.length));
    assertEquals(2, compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-2, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1, compare(array1, array1.length, array3, array3.length));
    assertEquals(-1, compare(array3, array3.length, array1, array1.length));
  }

  @Test
  public void testDoubleObject() {
    Double[] array = { (double) 0.0, (double) -1.0, (double) 1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE };

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        long longi = Double.doubleToLongBits(array[i]);
        long longj = Double.doubleToLongBits(array[j]);
        if (array[i] > array[j] || longi > longj) {
          assertEquals(1, compare(array[i], array[j]));
        } else if (array[i] < array[j] || longi < longj) {
          assertEquals(-1, compare(array[i], array[j]));
        } else {
          assertEquals(0, compare(array[i], array[j]));
        }
      }
    }

    array[0] = null;
    assertEquals(1, compare(array[1], array[0]));
    assertEquals(-1, compare(array[0], array[1]));
    array[1] = null;
    assertEquals(0, compare(array[1], array[0]));
  }

  @Test
  public void testDoubleObjectArray() {
    Double[] array1 = { (double) 0.0, (double) -1.0, (double) 1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE };
    Double[] array2 = { (double) 0.0, (double) -1.0, (double) 1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE };
    Double[] array3 = { (double) 0.0, (double) -1.0, (double) 1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        (double) 0.0 };
    Double[] array4 = { (double) 0.0, (double) -1.0, (double) 1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        Double.MIN_VALUE };

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2[0] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1[0] = null;
    assertEquals(0, compare(array1, array2));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testDoubleObjectArrayInt() {
    Double[] array1 = { (double) 0.0, (double) -1.0, (double) 1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE };
    Double[] array2 = { (double) 0.0, (double) -1.0, (double) 1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE };
    Double[] array3 = { (double) 0.0, (double) -1.0, (double) 1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        (double) 0.0 };

    assertEquals(0, compare(array1, array1.length, array2, array2.length));
    assertEquals(2, compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-2, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1, compare(array1, array1.length, array3, array3.length));
    array3[1] = null;
    assertEquals(1,
        compare(array1, array1.length - 2, array3, array3.length - 2));
    array1[1] = null;
    assertEquals(0,
        compare(array1, array1.length - 3, array3, array3.length - 2));
  }

  @Test
  public void testDoubleWithEpsilon() {
    double epsilon = 0.0001f;
    double value1 = 0.0f;
    double value2 = 0.0f + epsilon;

    assertEquals(0, compare(value1, value2, epsilon));
    value1 = -epsilon;
    assertEquals(-1, compare(value1, value2, epsilon));
    assertEquals(1, compare(value2, value1, epsilon));
    value1 = Double.MAX_VALUE;
    value2 = Double.MAX_VALUE + epsilon;
    assertEquals(0, compare(value1, value2, epsilon));
  }

  @Test
  public void testDoubleArrayWithEpsilon() {
    double epsilon = 0.0001f;
    double[] array1 = { (double) 0.0, (double) 1.0, (double) -1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE };
    double[] array2 = { (double) 0.0 + epsilon, (double) 1.0, (double) -1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE };
    double[] array3 = { (double) 0.0 + epsilon * 2, (double) 1.0,
        (double) -1.0, (double) (Double.MIN_VALUE / 2),
        (double) (Double.MAX_VALUE / 2), Double.MIN_VALUE, Double.MAX_VALUE };

    assertEquals(0, compare(array1, array2, epsilon));
    assertEquals(-1, compare(array1, array3, epsilon));
    array2 = null;
    assertEquals(1, compare(array1, array2, epsilon));
    assertEquals(-1, compare(array2, array1, epsilon));
    array1 = null;
    assertEquals(0, compare(array1, array2, epsilon));
  }

  @Test
  public void testDoubleArrayIntWithEpsilon() {
    double epsilon = 0.001f;
    double[] array1 = { (double) 0.0, (double) 1.0, (double) -1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE };
    double[] array2 = { (double) 0.0 + epsilon, (double) 1.0 + epsilon * 2,
        (double) -1.0, (double) (Double.MIN_VALUE / 2),
        (double) (Double.MAX_VALUE / 2), Double.MIN_VALUE, Double.MAX_VALUE };
    assertEquals(0, compare(array1, 1, array2, 1, epsilon));
    assertEquals(-1,
        compare(array1, array1.length, array2, array2.length, epsilon));
  }

  @Test
  public void testDoubleObjectWithEpsilon() {
    double epsilon = 0.0001;
    Double value1 = 0.0;
    Double value2 = 0.0 + epsilon;

    assertEquals(0, compare(value1, value2, epsilon));
    value1 = -epsilon;
    assertEquals(-1, compare(value1, value2, epsilon));
    assertEquals(1, compare(value2, value1, epsilon));
    value1 = Double.MAX_VALUE;
    value2 = Double.MAX_VALUE + epsilon;
    assertEquals(0, compare(value1, value2, epsilon));
    value2 = null;
    assertEquals(1, compare(value1, value2, epsilon));
    assertEquals(-1, compare(value2, value1, epsilon));
    value1 = null;
    assertEquals(0, compare(value1, value2, epsilon));
  }

  @Test
  public void testDoubleObjectArrayWithEpsilon() {
    double epsilon = 0.0001f;
    Double[] array1 = { (double) 0.0, (double) 1.0, (double) -1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE };
    Double[] array2 = { (double) 0.0 + epsilon, (double) 1.0, (double) -1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE };
    Double[] array3 = { (double) 0.0 + epsilon * 2, (double) 1.0,
        (double) -1.0, (double) (Double.MIN_VALUE / 2),
        (double) (Double.MAX_VALUE / 2), Double.MIN_VALUE, Double.MAX_VALUE };

    assertEquals(0, compare(array1, array2, epsilon));
    assertEquals(-1, compare(array1, array3, epsilon));
    array2 = null;
    assertEquals(1, compare(array1, array2, epsilon));
    assertEquals(-1, compare(array2, array1, epsilon));
    array1 = null;
    assertEquals(0, compare(array1, array2, epsilon));
  }

  @Test
  public void testDoubleObjectArrayIntWithEpsilon() {
    double epsilon = 0.001f;
    Double[] array1 = { (double) 0.0, (double) 1.0, (double) -1.0,
        (double) (Double.MIN_VALUE / 2), (double) (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE };
    Double[] array2 = { (double) 0.0 + epsilon, (double) 1.0 + epsilon * 2,
        (double) -1.0, (double) (Double.MIN_VALUE / 2),
        (double) (Double.MAX_VALUE / 2), Double.MIN_VALUE, Double.MAX_VALUE };
    assertEquals(0, compare(array1, 1, array2, 1, epsilon));
    assertEquals(-1,
        compare(array1, array1.length, array2, array2.length, epsilon));
  }

  @Test
  public void testEnum() {
    final TestEnum[] array1 = { null, TestEnum.a, TestEnum.b, TestEnum.c,
        TestEnum.d, TestEnum.e };
    final TestEnum[] array2 = { null, TestEnum.a, TestEnum.b, TestEnum.c,
        TestEnum.d, TestEnum.e };

    int out;

    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array2.length; ++j) {
        if (array1[i] == array2[j]) {
          out = 0;
        } else if (array1[i] == null) {
          out = -1;
        } else if (array2[j] == null) {
          out = +1;
        } else {
          out = array1[i].ordinal() - array2[j].ordinal();
        }
        assertEquals(out, compare(array1[i], array2[j]));
      }
    }
  }

  @Test
  public void testEnumArray() {
    TestEnum[] array1 = { TestEnum.a, TestEnum.b, TestEnum.c };
    TestEnum[] array2 = { TestEnum.a, TestEnum.b, TestEnum.c };

    assertEquals(0, compare(array1, array2));
    array2[2] = TestEnum.e;
    assertEquals(-2, compare(array1, array2));
    assertEquals(2, compare(array2, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testEnumArrayInt() {
    TestEnum[] array1 = { TestEnum.a, TestEnum.b, TestEnum.c };
    TestEnum[] array2 = { TestEnum.a, TestEnum.b, TestEnum.c };

    assertEquals(0, compare(array1, array1.length, array2, array2.length));
    assertEquals(1, compare(array1, array1.length, array2, array2.length - 1));
    assertEquals(-1, compare(array2, array2.length - 1, array1, array1.length));
    assertEquals(0,
        compare(array1, array1.length - 1, array2, array2.length - 1));
    array2[1] = TestEnum.e;
    assertEquals(-3, compare(array1, array1.length, array2, array2.length - 1));
    assertEquals(3, compare(array2, array2.length - 1, array1, array1.length));
    array2[1] = null;
    assertEquals(1, compare(array1, array1.length, array2, array2.length - 1));
    assertEquals(-1, compare(array2, array2.length - 1, array1, array1.length));
  }

  @Test
  public void testString() {
    String value1 = "longlongago";
    String value2 = "longlongago";
    assertEquals(0, compare(value1, value2));
    value2 = "helloworld";
    assertEquals(4, compare(value1, value2));
    assertEquals(-4, compare(value2, value1));
    value2 = "longlongag";
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value2 = "longlongagoooo";
    assertEquals(-3, compare(value1, value2));
    assertEquals(3, compare(value2, value1));
    value2 = null;
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value1 = null;
    assertEquals(0, compare(value1, value2));
  }

  @Test
  public void testStringArray() {
    String[] array1 = { "long", "long", "ago" };
    String[] array2 = { "long", "long", "ago" };

    assertEquals(0, compare(array1, array2));
    array2[2] = "agooo";
    assertEquals(-2, compare(array1, array2));
    assertEquals(2, compare(array2, array1));
    array2[2] = "aao";
    assertEquals(6, compare(array1, array2));
    assertEquals(-6, compare(array2, array1));
    array2[2] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testStringArrayInt() {
    String[] array1 = { "long", "long", "ago" };
    String[] array2 = { "long", "long", "ago" };

    assertEquals(0, compare(array1, array1.length, array2, array2.length));
    assertEquals(1, compare(array1, array1.length, array2, array2.length - 1));
    array2[2] = "agooo";
    assertEquals(-2, compare(array1, array1.length, array2, array2.length));
    assertEquals(2, compare(array2, array2.length, array1, array1.length));
    array2[2] = "aao";
    assertEquals(0,
        compare(array1, array1.length - 1, array2, array2.length - 1));
    assertEquals(6, compare(array1, array1.length, array2, array2.length));
    assertEquals(-6, compare(array2, array2.length, array1, array1.length));
    array2[2] = null;
    assertEquals(1, compare(array1, array1.length, array2, array2.length));
    assertEquals(-1, compare(array2, array2.length, array1, array1.length));
  }

  @Test
  public void testClassOfQ() {
    TestClassA value1 = new TestClassA();
    final TestClassB value2 = new TestClassB();
    TestClassA value3 = new TestClassA();
    final TestClassZ value4 = new TestClassZ();

    assertEquals(0, compare(value1.getClass(), value3.getClass()));
    assertEquals(-1, compare(value1.getClass(), value2.getClass()));
    assertEquals(1, compare(value2.getClass(), value1.getClass()));
    assertEquals(-25, compare(value1.getClass(), value4.getClass()));
    assertEquals(25, compare(value4.getClass(), value1.getClass()));
    assertEquals(-1, compare(null, value3.getClass()));
    assertEquals(1, compare(value3.getClass(), (Class<?>) null));
    assertEquals(0, compare((Class<?>) null, (Class<?>) null));
  }

  @Test
  public void testClassOfQArray() {
    Class<?>[] array1 = { new TestClassA().getClass(),
        new TestClassA().getClass() };
    Class<?>[] array2 = { new TestClassA().getClass(),
        new TestClassA().getClass() };
    Class<?>[] array3 = { new TestClassB().getClass(),
        new TestClassB().getClass() };
    Class<?>[] array4 = { new TestClassZ().getClass(),
        new TestClassZ().getClass() };
    Class<?>[] array5 = { new TestClassA().getClass(),
        new TestClassA().getClass(), new TestClassA().getClass() };
    assertEquals(0, compare(array1, array2));
    assertEquals(-1, compare(array1, array3));
    assertEquals(1, compare(array3, array1));
    assertEquals(-25, compare(array1, array4));
    assertEquals(25, compare(array4, array1));
    assertEquals(-1, compare(array1, array5));
    assertEquals(1, compare(array5, array1));
    array2[1] = (Class<?>) null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1[1] = (Class<?>) null;
    assertEquals(0, compare(array1, array2));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testClassOfQArrayInt() {
    Class<?>[] array1 = { new TestClassA().getClass(),
        new TestClassA().getClass() };
    Class<?>[] array2 = { new TestClassA().getClass(),
        new TestClassA().getClass() };
    Class<?>[] array3 = { new TestClassB().getClass(),
        new TestClassB().getClass() };
    Class<?>[] array4 = { new TestClassZ().getClass(),
        new TestClassZ().getClass() };
    Class<?>[] array5 = { new TestClassA().getClass(),
        new TestClassA().getClass(), new TestClassA().getClass() };
    assertEquals(0, compare(array1, 2, array2, 2));
    assertEquals(-1, compare(array1, 2, array3, 2));
    assertEquals(1, compare(array3, 2, array1, 2));
    assertEquals(-25, compare(array1, 2, array4, 2));
    assertEquals(25, compare(array4, 2, array1, 2));
    assertEquals(-1, compare(array1, 2, array5, 3));
    assertEquals(1, compare(array5, 3, array1, 2));
    assertEquals(0, compare(array1, 2, array5, 2));
    array2[1] = new TestClassZ().getClass();
    assertEquals(0, compare(array1, 1, array2, 1));
    assertEquals(-25, compare(array1, 2, array2, 2));
    assertEquals(25, compare(array2, 2, array1, 2));
    assertEquals(-1, compare(array1, 1, array2, 2));
    assertEquals(1, compare(array2, 2, array1, 1));
    array2[1] = (Class<?>) null;
    assertEquals(1, compare(array1, 2, array2, 2));
    assertEquals(-1, compare(array2, 2, array1, 2));
    array1[1] = (Class<?>) null;
    assertEquals(0, compare(array1, 2, array2, 2));
  }

  @Test
  public void testDate() {
    Date value1 = null;
    Date value2 = null;

    assertEquals(0, compare(value1, value2));

    GregorianCalendar cal = new GregorianCalendar();
    cal.setTimeInMillis(0);
    cal.set(2012, 0, 12, 0, 0);
    value2 = cal.getTime();
    assertEquals(-1, compare(value1, value2));
    assertEquals(1, compare(value2, value1));
    cal.set(2012, 0, 8, 0, 0);
    value1 = cal.getTime();
    assertEquals(-1, compare(value1, value2));
    assertEquals(1, compare(value2, value1));
    cal.set(2012, 0, 12, 0, 0);
    value1 = cal.getTime();
    assertEquals(0, compare(value1, value2));
  }

  @Test
  public void testDateArray() {
    Date[] array1 = { new Date(), new Date() };
    Date[] array2 = { new Date(), new Date() };
    Date[] array3 = { new Date(), new Date(), new Date() };

    GregorianCalendar cal = new GregorianCalendar();
    cal.setTimeInMillis(0);
    cal.set(2012, 2, 15, 0, 0);
    array1[0] = cal.getTime();
    array2[0] = cal.getTime();
    array3[0] = cal.getTime();
    cal.set(2012, 3, 15, 0, 0);
    array1[1] = cal.getTime();
    array2[1] = cal.getTime();
    array3[1] = cal.getTime();

    assertEquals(0, compare(array1, array2));

    array3[2] = cal.getTime();
    assertEquals(-1, compare(array1, array3));
    assertEquals(1, compare(array3, array1));

    cal.set(2012, 3, 10, 0, 0);
    array2[1] = cal.getTime();
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));

    array2[1] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));

    array1[1] = null;
    assertEquals(0, compare(array1, array2));
    assertEquals(0, compare(array2, array1));

    array1 = null;
    assertEquals(-1, compare(array1, array2));
    assertEquals(1, compare(array2, array1));

    array2 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testDateArrayInt() {
    Date[] array1 = { new Date(), new Date() };
    Date[] array2 = { new Date(), new Date() };
    Date[] array3 = { new Date(), new Date(), new Date() };

    GregorianCalendar cal = new GregorianCalendar();
    cal.setTimeInMillis(0);
    cal.set(2012, 2, 15, 0, 0);
    array1[0] = cal.getTime();
    array2[0] = cal.getTime();
    array3[0] = cal.getTime();
    cal.set(2012, 3, 15, 0, 0);
    array1[1] = cal.getTime();
    array2[1] = cal.getTime();
    array3[1] = cal.getTime();

    assertEquals(0, compare(array1, 2, array2, 2));
    assertEquals(0, compare(array1, 2, array3, 2));

    array3[2] = cal.getTime();
    assertEquals(-1, compare(array1, 2, array3, 3));
    assertEquals(1, compare(array3, 3, array1, 2));

    cal.set(2012, 3, 10, 0, 0);
    array2[1] = cal.getTime();
    assertEquals(1, compare(array1, 2, array2, 2));
    assertEquals(-1, compare(array2, 2, array1, 2));
    assertEquals(0, compare(array1, 1, array2, 1));

    array2[1] = null;
    assertEquals(1, compare(array1, 2, array2, 2));
    assertEquals(-1, compare(array2, 2, array1, 2));
    assertEquals(0, compare(array1, 1, array2, 1));

    array1[1] = null;
    assertEquals(0, compare(array1, 2, array2, 2));
    assertEquals(0, compare(array2, 2, array1, 2));
    cal.set(2012, 3, 30, 0, 0);
    array2[0] = cal.getTime();
    assertEquals(-1, compare(array1, 1, array2, 1));
    assertEquals(1, compare(array2, 1, array1, 1));

    array1 = null;
    assertEquals(-1, compare(array1, array2));
    assertEquals(1, compare(array2, array1));

    array2 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testBigInteger() {
    BigInteger value1 = new BigInteger("123123123");
    BigInteger value2 = new BigInteger("123123123");

    assertEquals(0, compare(value1, value2));
    value2 = new BigInteger("123123");
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value2 = null;
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value1 = null;
    assertEquals(0, compare(value1, value2));
  }

  @Test
  public void testBigIntegerArray() {
    BigInteger[] array1 = { new BigInteger("123"), new BigInteger("123456") };
    BigInteger[] array2 = { new BigInteger("123"), new BigInteger("123456") };
    BigInteger[] array3 = { new BigInteger("123"), new BigInteger("123456"),
        new BigInteger("123456789") };

    assertEquals(0, compare(array1, array2));
    assertEquals(-1, compare(array1, array3));
    assertEquals(1, compare(array3, array1));
    array2[1] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1[1] = null;
    assertEquals(0, compare(array1, array2));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testBigIntegerArrayInt() {
    BigInteger[] array1 = { new BigInteger("123"), new BigInteger("123456") };
    BigInteger[] array2 = { new BigInteger("123"), new BigInteger("123456") };
    BigInteger[] array3 = { new BigInteger("123"), new BigInteger("123456"),
        new BigInteger("123456789") };

    assertEquals(0, compare(array1, 2, array2, 2));
    assertEquals(-1, compare(array1, 2, array3, 3));
    assertEquals(1, compare(array3, 3, array1, 2));
    assertEquals(0, compare(array1, 2, array3, 2));
    array2[1] = null;
    assertEquals(1, compare(array1, 2, array2, 2));
    assertEquals(-1, compare(array2, 2, array1, 2));
    assertEquals(0, compare(array1, 1, array2, 1));
    array1[1] = null;
    assertEquals(0, compare(array1, 2, array2, 2));
  }

  @Test
  public void testBigDecimal() {
    BigDecimal value1 = new BigDecimal("123123.123");
    BigDecimal value2 = new BigDecimal("123123.123");

    assertEquals(0, compare(value1, value2));
    value2 = new BigDecimal("123.123");
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value2 = null;
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value1 = null;
    assertEquals(0, compare(value1, value2));
  }

  @Test
  public void testBigDecimalArray() {
    BigDecimal[] array1 = { new BigDecimal("12.3"), new BigDecimal("1234.56") };
    BigDecimal[] array2 = { new BigDecimal("12.3"), new BigDecimal("1234.56") };
    BigDecimal[] array3 = { new BigDecimal("12.3"), new BigDecimal("1234.56"),
        new BigDecimal("123456.789") };

    assertEquals(0, compare(array1, array2));
    assertEquals(-1, compare(array1, array3));
    assertEquals(1, compare(array3, array1));
    array2[1] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1[1] = null;
    assertEquals(0, compare(array1, array2));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testBigDecimalArrayInt() {
    BigDecimal[] array1 = { new BigDecimal("12.3"), new BigDecimal("123.456") };
    BigDecimal[] array2 = { new BigDecimal("12.3"), new BigDecimal("123.456") };
    BigDecimal[] array3 = { new BigDecimal("12.3"), new BigDecimal("123.456"),
        new BigDecimal("123456.789") };

    assertEquals(0, compare(array1, 2, array2, 2));
    assertEquals(-1, compare(array1, 2, array3, 3));
    assertEquals(1, compare(array3, 3, array1, 2));
    assertEquals(0, compare(array1, 2, array3, 2));
    array2[1] = null;
    assertEquals(1, compare(array1, 2, array2, 2));
    assertEquals(-1, compare(array2, 2, array1, 2));
    assertEquals(0, compare(array1, 1, array2, 1));
    array1[1] = null;
    assertEquals(0, compare(array1, 2, array2, 2));
  }

  @Test
  public void testObject() {
    Object value1 = new TestClassA();
    Object value2 = new TestClassA();

    assertEquals(0, compare(value1, value1));
    assertEquals(true, compare(value1, value2) != 0);
    value2 = new TestClassB();
    assertEquals(-1, compare(value1, value2));
    assertEquals(1, compare(value2, value1));
    value2 = new TestClassZ();
    assertEquals(-25, compare(value1, value2));
    assertEquals(25, compare(value2, value1));
    value1 = TestClassA.class;
    value2 = Class.class;
    assertEquals(-7, compare(value1, value2));
    assertEquals(7, compare(value2, value1));
    value2 = null;
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value1 = null;
    assertEquals(0, compare(value1, value2));

    boolean[] booleanarray1 = { true, false, false, true };
    boolean[] booleanarray2 = { true, false, false, true };
    boolean[] booleanarray3 = { false, true, true };
    boolean[] booleanarray4 = { true, false };
    assertEquals(0, compare(booleanarray1, (Object) booleanarray2));
    assertEquals(1, compare(booleanarray2, (Object) booleanarray3));
    assertEquals(-1, compare(booleanarray3, (Object) booleanarray2));
    assertEquals(2, compare(booleanarray2, (Object) booleanarray4));
    assertEquals(-2, compare(booleanarray4, (Object) booleanarray2));

    char[] chararray1 = { (char) 0, (char) 1, (char) (Character.MIN_VALUE / 2),
        Character.MIN_VALUE, (char) (Character.MAX_VALUE / 2),
        Character.MAX_VALUE };
    char[] chararray2 = { (char) 0, (char) 1, (char) (Character.MIN_VALUE / 2),
        Character.MIN_VALUE, (char) (Character.MAX_VALUE / 2),
        Character.MAX_VALUE };
    char[] chararray3 = { (char) 0, (char) 3 };
    char[] chararray4 = { (char) 0, (char) 1, (char) (Character.MIN_VALUE / 2),
        Character.MIN_VALUE };

    assertEquals(0, compare(chararray1, (Object) chararray2));
    assertEquals(2, compare(chararray3, (Object) chararray2));
    assertEquals(-2, compare(chararray2, (Object) chararray3));
    assertEquals(-2, compare(chararray4, (Object) chararray2));
    assertEquals(2, compare(chararray2, (Object) chararray4));

    byte[] bytearray1 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE };
    byte[] bytearray2 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE };
    byte[] bytearray3 = { (byte) 0, (byte) 2 };
    byte[] bytearray4 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2) };
    assertEquals(0, compare(bytearray1, (Object) bytearray2));
    assertEquals(3, compare(bytearray3, (Object) bytearray2));
    assertEquals(-3, compare(bytearray2, (Object) bytearray3));
    assertEquals(2, compare(bytearray2, (Object) bytearray4));
    assertEquals(-2, compare(bytearray4, (Object) bytearray2));

    short[] shortarray1 = { (short) 0, (short) -1, (short) 1, Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2), Short.MIN_VALUE,
        (short) (Short.MAX_VALUE / 2) };
    short[] shortarray2 = { (short) 0, (short) -1, (short) 1, Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2), Short.MIN_VALUE,
        (short) (Short.MAX_VALUE / 2) };
    short[] shortarray3 = { (short) 0, (short) 3 };
    short[] shortarray4 = { (short) 0, (short) -1, (short) 1, Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2) };
    assertEquals(0, compare(shortarray1, (Object) shortarray2));
    assertEquals(4, compare(shortarray3, (Object) shortarray2));
    assertEquals(-4, compare(shortarray2, (Object) shortarray3));
    assertEquals(2, compare(shortarray2, (Object) shortarray4));
    assertEquals(-2, compare(shortarray4, (Object) shortarray2));

    int[] intarray1 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE };
    int[] intarray2 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE };
    int[] intarray3 = { (int) 0, (int) 4 };
    int[] intarray4 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2) };
    assertEquals(0, compare(intarray1, (Object) intarray2));
    assertEquals(5, compare(intarray3, (Object) intarray2));
    assertEquals(-5, compare(intarray2, (Object) intarray3));
    assertEquals(2, compare(intarray2, (Object) intarray4));
    assertEquals(-2, compare(intarray4, (Object) intarray2));

    long[] longarray1 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MAX_VALUE };
    long[] longarray2 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MAX_VALUE };
    long[] longarray3 = { (long) 0, (long) 2 };
    long[] longarray4 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        (long) (Long.MIN_VALUE / 2) };
    assertEquals(0, compare(longarray1, (Object) longarray2));
    assertEquals(1, compare(longarray3, (Object) longarray2));
    assertEquals(-1, compare(longarray2, (Object) longarray3));
    assertEquals(-2, compare(longarray4, (Object) longarray2));
    assertEquals(2, compare(longarray2, (Object) longarray4));

    float[] floatarray1 = { (float) 0, (float) -1, (float) 1, Float.MIN_VALUE,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MAX_VALUE };
    float[] floatarray2 = { (float) 0, (float) -1, (float) 1, Float.MIN_VALUE,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MAX_VALUE };
    float[] floatarray3 = { (float) 0, (float) 2 };
    float[] floatarray4 = { (float) 0, (float) -1, (float) 1, Float.MIN_VALUE,
        (float) (Float.MIN_VALUE / 2) };
    assertEquals(0, compare(floatarray1, (Object) floatarray2));
    assertEquals(-1, compare(floatarray2, (Object) floatarray3));
    assertEquals(1, compare(floatarray3, (Object) floatarray2));
    assertEquals(2, compare(floatarray2, (Object) floatarray4));
    assertEquals(-2, compare(floatarray4, (Object) floatarray2));

    double[] doublearray1 = { (double) 0, (double) -1, (double) 1,
        Double.MIN_VALUE, (double) (Double.MIN_VALUE / 2),
        (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE };
    double[] doublearray2 = { (double) 0, (double) -1, (double) 1,
        Double.MIN_VALUE, (double) (Double.MIN_VALUE / 2),
        (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE };
    double[] doublearray3 = { (double) 0, Double.MAX_VALUE };
    double[] doublearray4 = { (double) 0, (double) -1, (double) 1,
        Double.MIN_VALUE, (double) (Double.MIN_VALUE / 2) };
    assertEquals(0, compare(doublearray1, (Object) doublearray2));
    assertEquals(1, compare(doublearray3, (Object) doublearray2));
    assertEquals(-1, compare(doublearray2, (Object) doublearray3));
    assertEquals(-2, compare(doublearray4, (Object) doublearray2));
    assertEquals(2, compare(doublearray2, (Object) doublearray4));

    String[] stringarray1 = { "long", "long", "ago" };
    String[] stringarray2 = { "long", "long", "ago" };
    String[] stringarray3 = { "long", "time", "no", "see" };
    String[] stringarray4 = { "long", "long" };
    assertEquals(0, compare(stringarray1, (Object) stringarray2));
    assertEquals(-8, compare(stringarray2, (Object) stringarray3));
    assertEquals(8, compare(stringarray3, (Object) stringarray2));
    assertEquals(1, compare(stringarray2, (Object) stringarray4));
    assertEquals(-1, compare(stringarray4, (Object) stringarray2));

    Boolean[] booleanobjectarray1 = { true, false, false, true };
    Boolean[] booleanobjectarray2 = { true, false, false, true };
    Boolean[] booleanobjectarray3 = { false, true, true };
    Boolean[] booleanobjectarray4 = { true, false };
    assertEquals(0, compare(booleanobjectarray1, (Object) booleanobjectarray2));
    assertEquals(1, compare(booleanobjectarray2, (Object) booleanobjectarray3));
    assertEquals(-1, compare(booleanobjectarray3, (Object) booleanobjectarray2));
    assertEquals(2, compare(booleanobjectarray2, (Object) booleanobjectarray4));
    assertEquals(-2, compare(booleanobjectarray4, (Object) booleanobjectarray2));

    Character[] charobjectarray1 = { (char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE };
    Character[] charobjectarray2 = { (char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE };
    Character[] charobjectarray3 = { (char) 0, (char) 3 };
    Character[] charobjectarray4 = { (char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE };
    assertEquals(0, compare(charobjectarray1, (Object) charobjectarray2));
    assertEquals(2, compare(charobjectarray3, (Object) charobjectarray2));
    assertEquals(-2, compare(charobjectarray2, (Object) charobjectarray3));
    assertEquals(-2, compare(charobjectarray4, (Object) charobjectarray2));
    assertEquals(2, compare(charobjectarray2, (Object) charobjectarray4));

    Byte[] byteobjectarray1 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE };
    Byte[] byteobjectarray2 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE };
    Byte[] byteobjectarray3 = { (byte) 0, (byte) 2 };
    Byte[] byteobjectarray4 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2) };
    assertEquals(0, compare(byteobjectarray1, (Object) byteobjectarray2));
    assertEquals(3, compare(byteobjectarray3, (Object) byteobjectarray2));
    assertEquals(-3, compare(byteobjectarray2, (Object) byteobjectarray3));
    assertEquals(-2, compare(byteobjectarray4, (Object) byteobjectarray2));
    assertEquals(2, compare(byteobjectarray2, (Object) byteobjectarray4));

    Short[] shortobjectarray1 = { (short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2),
        (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE };
    Short[] shortobjectarray2 = { (short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2),
        (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE };
    Short[] shortobjectarray3 = { (short) 0, (short) 2 };
    Short[] shortobjectarray4 = { (short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) };
    assertEquals(0, compare(shortobjectarray1, (Object) shortobjectarray2));
    assertEquals(3, compare(shortobjectarray3, (Object) shortobjectarray2));
    assertEquals(-3, compare(shortobjectarray2, (Object) shortobjectarray3));
    assertEquals(-2, compare(shortobjectarray4, (Object) shortobjectarray2));
    assertEquals(2, compare(shortobjectarray2, (Object) shortobjectarray4));

    Integer[] integerarray1 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE };
    Integer[] integerarray2 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE };
    Integer[] integerarray3 = { (int) 0, (int) 3 };
    Integer[] integerarray4 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2) };
    assertEquals(0, compare(integerarray1, (Object) integerarray2));
    assertEquals(4, compare(integerarray3, (Object) integerarray2));
    assertEquals(-4, compare(integerarray2, (Object) integerarray3));
    assertEquals(-2, compare(integerarray4, (Object) integerarray2));
    assertEquals(2, compare(integerarray2, (Object) integerarray4));

    Long[] longobjectarray1 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MAX_VALUE };
    Long[] longobjectarray2 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MAX_VALUE };
    Long[] longobjectarray3 = { (long) 0, (long) 2 };
    Long[] longobjectarray4 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE };
    assertEquals(0, compare(longobjectarray1, (Object) longobjectarray2));
    assertEquals(1, compare(longobjectarray3, (Object) longobjectarray2));
    assertEquals(-1, compare(longobjectarray2, (Object) longobjectarray3));
    assertEquals(-3, compare(longobjectarray4, (Object) longobjectarray2));
    assertEquals(3, compare(longobjectarray2, (Object) longobjectarray4));

    Float[] floatobjectarray1 = { (float) 0, (float) -1, (float) 1,
        Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2),
        (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE };
    Float[] floatobjectarray2 = { (float) 0, (float) -1, (float) 1,
        Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2),
        (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE };
    Float[] floatobjectarray3 = { (float) 0, (float) 2 };
    Float[] floatobjectarray4 = { (float) 0, (float) -1, (float) 1,
        Float.MIN_VALUE };
    assertEquals(0, compare(floatobjectarray1, (Object) floatobjectarray2));
    assertEquals(1, compare(floatobjectarray3, (Object) floatobjectarray2));
    assertEquals(-1, compare(floatobjectarray2, (Object) floatobjectarray3));
    assertEquals(-3, compare(floatobjectarray4, (Object) floatobjectarray2));
    assertEquals(3, compare(floatobjectarray2, (Object) floatobjectarray4));

    Double[] doubleobjectarray1 = { (double) 0, (double) -1, (double) 1,
        (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE,
        (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE };
    Double[] doubleobjectarray2 = { (double) 0, (double) -1, (double) 1,
        (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE,
        (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE };
    Double[] doubleobjectarray3 = { (double) 0, (double) 2 };
    Double[] doubleobjectarray4 = { (double) 0, (double) -1, (double) 1,
        (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE };
    assertEquals(0, compare(doubleobjectarray1, (Object) doubleobjectarray2));
    assertEquals(1, compare(doubleobjectarray3, (Object) doubleobjectarray2));
    assertEquals(-1, compare(doubleobjectarray2, (Object) doubleobjectarray3));
    assertEquals(-2, compare(doubleobjectarray4, (Object) doubleobjectarray2));
    assertEquals(2, compare(doubleobjectarray2, (Object) doubleobjectarray4));

    Class<?>[] classarray1 = new Class<?>[] { TestClassA.class,
        TestClassA.class };
    Class<?>[] classarray2 = new Class<?>[] { TestClassA.class,
        TestClassA.class };
    Class<?>[] classarray3 = new Class<?>[] { TestClassB.class,
        TestClassB.class };
    Class<?>[] classarray4 = new Class<?>[] { TestClassA.class,
        TestClassA.class, TestClassA.class };
    assertEquals(0, compare(classarray1, (Object) classarray2));
    assertEquals(1, compare(classarray3, (Object) classarray2));
    assertEquals(-1, compare(classarray2, (Object) classarray3));
    assertEquals(1, compare(classarray4, (Object) classarray2));
    assertEquals(-1, compare(classarray2, (Object) classarray4));

    Date datea = new Date();
    datea.setTime((long) 123456);
    Date dateb = new Date();
    dateb.setTime((long) 7891011);
    Date datec = new Date();
    datec.setTime((long) 654321);
    Date[] datearray1 = { datea, dateb, datec };
    Date[] datearray2 = { datea, dateb, datec };
    Date[] datearray3 = { datec, datea, dateb };
    Date[] datearray4 = { datea };
    assertEquals(0, compare(datearray1, (Object) datearray2));
    assertEquals(1, compare(datearray3, (Object) datearray2));
    assertEquals(-1, compare(datearray2, (Object) datearray3));
    assertEquals(-2, compare(datearray4, (Object) datearray2));
    assertEquals(2, compare(datearray2, (Object) datearray4));

    BigInteger[] bigintegerarray1 = { new BigInteger(bytearray1),
        new BigInteger(bytearray3) };
    BigInteger[] bigintegerarray2 = { new BigInteger(bytearray2),
        new BigInteger(bytearray3) };
    BigInteger[] bigintegerarray3 = { new BigInteger(bytearray3) };
    BigInteger[] bigintegerarray4 = { new BigInteger(bytearray2) };
    assertEquals(0, compare(bigintegerarray1, (Object) bigintegerarray2));
    assertEquals(-1, compare(bigintegerarray3, (Object) bigintegerarray2));
    assertEquals(1, compare(bigintegerarray2, (Object) bigintegerarray3));
    assertEquals(-1, compare(bigintegerarray4, (Object) bigintegerarray2));
    assertEquals(1, compare(bigintegerarray2, (Object) bigintegerarray4));

    BigDecimal[] bigdecimalarray1 = { new BigDecimal(Integer.MIN_VALUE),
        new BigDecimal(Integer.MAX_VALUE) };
    BigDecimal[] bigdecimalarray2 = { new BigDecimal(Integer.MIN_VALUE),
        new BigDecimal(Integer.MAX_VALUE) };
    BigDecimal[] bigdecimalarray3 = { new BigDecimal(0), new BigDecimal(-1) };
    BigDecimal[] bigdecimalarray4 = { new BigDecimal(Integer.MIN_VALUE) };
    assertEquals(0, compare(bigdecimalarray1, (Object) bigdecimalarray2));
    assertEquals(-1, compare(bigdecimalarray2, (Object) bigdecimalarray3));
    assertEquals(1, compare(bigdecimalarray3, (Object) bigdecimalarray2));
    assertEquals(1, compare(bigdecimalarray2, (Object) bigdecimalarray4));
    assertEquals(-1, compare(bigdecimalarray4, (Object) bigdecimalarray2));

    Boolean[][] objectarray1 = { { true, false }, { true, true },
        { false, false, true } };
    Boolean[][] objectarray2 = { { true, false }, { true, true },
        { false, false, true } };
    Boolean[][] objectarray3 = { { true, false }, { true, true } };
    Boolean[][] objectarray4 = { { true, false }, { false, true } };
    assertEquals(0, compare(objectarray1, (Object) objectarray2));
    assertEquals(-1, compare(objectarray3, (Object) objectarray2));
    assertEquals(1, compare(objectarray2, (Object) objectarray3));
    assertEquals(-1, compare(objectarray4, (Object) objectarray2));
    assertEquals(1, compare(objectarray2, (Object) objectarray4));

    ArrayList<Character> col1 = new ArrayList<Character>();
    col1.add('a');
    col1.add('b');
    col1.add('c');
    ArrayList<Character> col2 = new ArrayList<Character>();
    col2.add('a');
    col2.add('b');
    col2.add('c');
    ArrayList<Character> col3 = new ArrayList<Character>();
    col3.add('b');
    col3.add('c');
    assertEquals(0, compare(col1, (Object) col2));
    assertEquals(1, compare(col3, (Object) col2));
    assertEquals(-1, compare(col2, (Object) col3));
  }

  @Test
  public void testObjectArray() {
    Object[] array1 = { new TestClassA(), new TestClassA() };
    Object[] array2 = { new TestClassA(), new TestClassA() };

    assertEquals(0, compare(array1, array1));
    assertEquals(true, compare(array1, array2) != 0);
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));

    boolean[][] booleanarray1 = { { true, false }, { false, true } };
    boolean[][] booleanarray2 = { { true, false }, { false, true } };
    boolean[][] booleanarray3 = { { false, true }, { true } };
    boolean[][] booleanarray4 = { { true, false } };
    assertEquals(0, compare(booleanarray1, booleanarray2));
    assertEquals(1, compare(booleanarray1, booleanarray3));
    assertEquals(-1, compare(booleanarray3, booleanarray1));
    assertEquals(1, compare(booleanarray1, booleanarray4));
    assertEquals(-1, compare(booleanarray4, booleanarray1));

    char[][] chararray1 = { { (char) 0, (char) 1 },
        { (char) Character.MIN_VALUE / 2, Character.MIN_VALUE },
        { (char) Character.MAX_VALUE, Character.MAX_VALUE } };
    char[][] chararray2 = { { (char) 0, (char) 1 },
        { (char) Character.MIN_VALUE / 2, Character.MIN_VALUE },
        { (char) Character.MAX_VALUE, Character.MAX_VALUE } };
    char[][] chararray3 = { { (char) 0, (char) 3 } };
    char[][] chararray4 = { { (char) 0, (char) 1 },
        { (char) Character.MIN_VALUE / 2, Character.MIN_VALUE } };
    assertEquals(0, compare(chararray1, chararray2));
    assertEquals(-2, compare(chararray1, chararray3));
    assertEquals(2, compare(chararray3, chararray1));
    assertEquals(1, compare(chararray1, chararray4));
    assertEquals(-1, compare(chararray4, chararray1));

    byte[][] bytearray1 = { { (byte) 0, (byte) -1, (byte) 1 },
        { (byte) Byte.MIN_VALUE / 2, Byte.MIN_VALUE },
        { (byte) Byte.MAX_VALUE, Byte.MAX_VALUE } };
    byte[][] bytearray2 = { { (byte) 0, (byte) -1, (byte) 1 },
        { (byte) Byte.MIN_VALUE / 2, Byte.MIN_VALUE },
        { (byte) Byte.MAX_VALUE, Byte.MAX_VALUE } };
    byte[][] bytearray3 = { { (byte) 0, (byte) 3 } };
    byte[][] bytearray4 = { { (byte) 0, (byte) -1, (byte) 1 },
        { (byte) Byte.MIN_VALUE / 2, Byte.MIN_VALUE } };
    assertEquals(0, compare(bytearray1, bytearray2));
    assertEquals(-4, compare(bytearray1, bytearray3));
    assertEquals(4, compare(bytearray3, bytearray1));
    assertEquals(1, compare(bytearray1, bytearray4));
    assertEquals(-1, compare(bytearray4, bytearray1));

    short[][] shortarray1 = { { (short) 0, (short) -1, (short) 1 },
        { (short) Short.MIN_VALUE / 2, Short.MIN_VALUE },
        { (short) Short.MAX_VALUE, Short.MAX_VALUE } };
    short[][] shortarray2 = { { (short) 0, (short) -1, (short) 1 },
        { (short) Short.MIN_VALUE / 2, Short.MIN_VALUE },
        { (short) Short.MAX_VALUE, Short.MAX_VALUE } };
    short[][] shortarray3 = { { (short) 0, (short) 3 } };
    short[][] shortarray4 = { { (short) 0, (short) -1, (short) 1 },
        { (short) Short.MIN_VALUE / 2, Short.MIN_VALUE } };
    assertEquals(0, compare(shortarray1, shortarray2));
    assertEquals(-4, compare(shortarray1, shortarray3));
    assertEquals(4, compare(shortarray3, shortarray1));
    assertEquals(1, compare(shortarray1, shortarray4));
    assertEquals(-1, compare(shortarray4, shortarray1));

    int[][] intarray1 = { { (int) 0, (int) -1, (int) 1 },
        { (int) Integer.MIN_VALUE / 2, Integer.MIN_VALUE },
        { (int) Integer.MAX_VALUE, Integer.MAX_VALUE } };
    int[][] intarray2 = { { (int) 0, (int) -1, (int) 1 },
        { (int) Integer.MIN_VALUE / 2, Integer.MIN_VALUE },
        { (int) Integer.MAX_VALUE, Integer.MAX_VALUE } };
    int[][] intarray3 = { { (int) 0, (int) 3 } };
    int[][] intarray4 = { { (int) 0, (int) -1, (int) 1 },
        { (int) Integer.MIN_VALUE / 2, Integer.MIN_VALUE } };
    assertEquals(0, compare(intarray1, intarray2));
    assertEquals(-4, compare(intarray1, intarray3));
    assertEquals(4, compare(intarray3, intarray1));
    assertEquals(1, compare(intarray1, intarray4));
    assertEquals(-1, compare(intarray4, intarray1));

    long[][] longarray1 = { { (long) 0, (long) -1, (long) 1 },
        { (long) Long.MIN_VALUE / 2, Long.MIN_VALUE },
        { (long) Long.MAX_VALUE, Long.MAX_VALUE } };
    long[][] longarray2 = { { (long) 0, (long) -1, (long) 1 },
        { (long) Long.MIN_VALUE / 2, Long.MIN_VALUE },
        { (long) Long.MAX_VALUE, Long.MAX_VALUE } };
    long[][] longarray3 = { { (long) 0, (long) 3 } };
    long[][] longarray4 = { { (long) 0, (long) -1, (long) 1 },
        { (long) Long.MIN_VALUE / 2, Long.MIN_VALUE } };
    assertEquals(0, compare(longarray1, longarray2));
    assertEquals(-1, compare(longarray1, longarray3));
    assertEquals(1, compare(longarray3, longarray1));
    assertEquals(1, compare(longarray1, longarray4));
    assertEquals(-1, compare(longarray4, longarray1));

    float[][] floatarray1 = { { (float) 0, (float) -1, (float) 1 },
        { (float) Float.MIN_VALUE / 2, Float.MIN_VALUE },
        { (float) Float.MAX_VALUE, Float.MAX_VALUE } };
    float[][] floatarray2 = { { (float) 0, (float) -1, (float) 1 },
        { (float) Float.MIN_VALUE / 2, Float.MIN_VALUE },
        { (float) Float.MAX_VALUE, Float.MAX_VALUE } };
    float[][] floatarray3 = { { (float) 0, (float) 3 } };
    float[][] floatarray4 = { { (float) 0, (float) -1, (float) 1 },
        { (float) Float.MIN_VALUE / 2, Float.MIN_VALUE } };
    assertEquals(0, compare(floatarray1, floatarray2));
    assertEquals(-1, compare(floatarray1, floatarray3));
    assertEquals(1, compare(floatarray3, floatarray1));
    assertEquals(1, compare(floatarray1, floatarray4));
    assertEquals(-1, compare(floatarray4, floatarray1));

    double[][] doublearray1 = { { (double) 0, (double) -1, (double) 1 },
        { (double) Double.MIN_VALUE / 2, Double.MIN_VALUE },
        { (double) Double.MAX_VALUE, Double.MAX_VALUE } };
    double[][] doublearray2 = { { (double) 0, (double) -1, (double) 1 },
        { (double) Double.MIN_VALUE / 2, Double.MIN_VALUE },
        { (double) Double.MAX_VALUE, Double.MAX_VALUE } };
    double[][] doublearray3 = { { (double) 0, (double) 3 } };
    double[][] doublearray4 = { { (double) 0, (double) -1, (double) 1 },
        { (double) Double.MIN_VALUE / 2, Double.MIN_VALUE } };
    assertEquals(0, compare(doublearray1, doublearray2));
    assertEquals(-1, compare(doublearray1, doublearray3));
    assertEquals(1, compare(doublearray3, doublearray1));
    assertEquals(1, compare(doublearray1, doublearray4));
    assertEquals(-1, compare(doublearray4, doublearray1));

    String[][] stringarray1 = { { "long", "long" }, { "ago" } };
    String[][] stringarray2 = { { "long", "long" }, { "ago" } };
    String[][] stringarray3 = { { "long", "time" }, { "no", "see" } };
    String[][] stringarray4 = { { "long", "long" } };
    assertEquals(0, compare(stringarray1, stringarray2));
    assertEquals(-8, compare(stringarray2, stringarray3));
    assertEquals(8, compare(stringarray3, stringarray2));
    assertEquals(1, compare(stringarray2, stringarray4));
    assertEquals(-1, compare(stringarray4, stringarray2));

    Boolean[][] booleanobjectarray1 = { { true, false }, { false, true } };
    Boolean[][] booleanobjectarray2 = { { true, false }, { false, true } };
    Boolean[][] booleanobjectarray3 = { { false, true }, { true } };
    Boolean[][] booleanobjectarray4 = { { true, false } };
    assertEquals(0, compare(booleanobjectarray1, booleanobjectarray2));
    assertEquals(1, compare(booleanobjectarray2, booleanobjectarray3));
    assertEquals(-1, compare(booleanobjectarray3, booleanobjectarray2));
    assertEquals(1, compare(booleanobjectarray2, booleanobjectarray4));
    assertEquals(-1, compare(booleanobjectarray4, booleanobjectarray2));

    Character[][] charobjectarray1 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    Character[][] charobjectarray2 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    Character[][] charobjectarray3 = { { (char) 0, (char) 3 } };
    Character[][] charobjectarray4 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE } };
    assertEquals(0, compare(charobjectarray1, charobjectarray2));
    assertEquals(2, compare(charobjectarray3, charobjectarray2));
    assertEquals(-2, compare(charobjectarray2, charobjectarray3));
    assertEquals(-1, compare(charobjectarray4, charobjectarray2));
    assertEquals(1, compare(charobjectarray2, charobjectarray4));

    Byte[][] byteobjectarray1 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    Byte[][] byteobjectarray2 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    Byte[][] byteobjectarray3 = { { (byte) 0, (byte) 2 } };
    Byte[][] byteobjectarray4 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) } };
    assertEquals(0, compare(byteobjectarray1, byteobjectarray2));
    assertEquals(3, compare(byteobjectarray3, byteobjectarray2));
    assertEquals(-3, compare(byteobjectarray2, byteobjectarray3));
    assertEquals(-1, compare(byteobjectarray4, byteobjectarray2));
    assertEquals(1, compare(byteobjectarray2, byteobjectarray4));

    Short[][] shortobjectarray1 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
    Short[][] shortobjectarray2 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
    Short[][] shortobjectarray3 = { { (short) 0, (short) 2 } };
    Short[][] shortobjectarray4 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) } };
    assertEquals(0, compare(shortobjectarray1, shortobjectarray2));
    assertEquals(3, compare(shortobjectarray3, shortobjectarray2));
    assertEquals(-3, compare(shortobjectarray2, shortobjectarray3));
    assertEquals(-1, compare(shortobjectarray4, shortobjectarray2));
    assertEquals(1, compare(shortobjectarray2, shortobjectarray4));

    Integer[][] integerarray1 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) },
        { (int) (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
    Integer[][] integerarray2 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) },
        { (int) (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
    Integer[][] integerarray3 = { { (int) 0, (int) 3 } };
    Integer[][] integerarray4 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) } };
    assertEquals(0, compare(integerarray1, integerarray2));
    assertEquals(4, compare(integerarray3, integerarray2));
    assertEquals(-4, compare(integerarray2, integerarray3));
    assertEquals(-1, compare(integerarray4, integerarray2));
    assertEquals(1, compare(integerarray2, integerarray4));

    Long[][] longobjectarray1 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) },
        { (long) (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
    Long[][] longobjectarray2 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) },
        { (long) (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
    Long[][] longobjectarray3 = { { (long) 0, (long) 2 } };
    Long[][] longobjectarray4 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) } };
    assertEquals(0, compare(longobjectarray1, longobjectarray2));
    assertEquals(1, compare(longobjectarray3, longobjectarray2));
    assertEquals(-1, compare(longobjectarray2, longobjectarray3));
    assertEquals(-1, compare(longobjectarray4, longobjectarray2));
    assertEquals(1, compare(longobjectarray2, longobjectarray4));

    Float[][] floatobjectarray1 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) },
        { (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
    Float[][] floatobjectarray2 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) },
        { (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
    Float[][] floatobjectarray3 = { { (float) 0, (float) 2 } };
    Float[][] floatobjectarray4 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) } };
    assertEquals(0, compare(floatobjectarray1, floatobjectarray2));
    assertEquals(1, compare(floatobjectarray3, floatobjectarray2));
    assertEquals(-1, compare(floatobjectarray2, floatobjectarray3));
    assertEquals(-1, compare(floatobjectarray4, floatobjectarray2));
    assertEquals(1, compare(floatobjectarray2, floatobjectarray4));

    Double[][] doubleobjectarray1 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE },
        { (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
    Double[][] doubleobjectarray2 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE },
        { (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
    Double[][] doubleobjectarray3 = { { (double) 0, (double) 2 } };
    Double[][] doubleobjectarray4 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE } };
    assertEquals(0, compare(doubleobjectarray1, doubleobjectarray2));
    assertEquals(1, compare(doubleobjectarray3, doubleobjectarray2));
    assertEquals(-1, compare(doubleobjectarray2, doubleobjectarray3));
    assertEquals(-1, compare(doubleobjectarray4, doubleobjectarray2));
    assertEquals(1, compare(doubleobjectarray2, doubleobjectarray4));

    Class<?>[][] classarray1 = { { TestClassA.class }, { TestClassA.class } };
    Class<?>[][] classarray2 = { { TestClassA.class }, { TestClassA.class } };
    Class<?>[][] classarray3 = { { TestClassB.class }, { TestClassB.class } };
    Class<?>[][] classarray4 = { { TestClassA.class }, { TestClassA.class },
        { TestClassA.class }};
    assertEquals(0, compare(classarray1, classarray2));
    assertEquals(1, compare(classarray3, classarray2));
    assertEquals(-1, compare(classarray2, classarray3));
    assertEquals(1, compare(classarray4, classarray2));
    assertEquals(-1, compare(classarray2, classarray4));

    Date datea = new Date();
    datea.setTime((long) 123456);
    Date dateb = new Date();
    dateb.setTime((long) 7891011);
    Date datec = new Date();
    datec.setTime((long) 654321);
    Date[][] datearray1 = { {datea, dateb}, {datec} };
    Date[][] datearray2 = { {datea, dateb}, {datec} };
    Date[][] datearray3 = { {datec, datea}, {dateb} };
    Date[][] datearray4 = { {datea, dateb}};
    assertEquals(0, compare(datearray1, datearray2));
    assertEquals(1, compare(datearray3, datearray2));
    assertEquals(-1, compare(datearray2, datearray3));
    assertEquals(-1, compare(datearray4, datearray2));
    assertEquals(1, compare(datearray2, datearray4));

    BigInteger[][] bigintegerarray1 = { {new BigInteger(bytearray1[0])},
        {new BigInteger(bytearray3[0]) }};
    BigInteger[][] bigintegerarray2 = { {new BigInteger(bytearray2[0])},
        {new BigInteger(bytearray3[0]) }};
    BigInteger[][] bigintegerarray3 = { {new BigInteger(bytearray3[0]) }};
    BigInteger[][] bigintegerarray4 = { {new BigInteger(bytearray2[0]) }};
    assertEquals(0, compare(bigintegerarray1, bigintegerarray2));
    assertEquals(-1, compare(bigintegerarray3, bigintegerarray2));
    assertEquals(1, compare(bigintegerarray2, bigintegerarray3));
    assertEquals(-1, compare(bigintegerarray4, bigintegerarray2));
    assertEquals(1, compare(bigintegerarray2, bigintegerarray4));

    BigDecimal[][] bigdecimalarray1 = { {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE) }};
    BigDecimal[][] bigdecimalarray2 = { {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE) }};
    BigDecimal[][] bigdecimalarray3 = { {new BigDecimal(0), new BigDecimal(-1)}};
    BigDecimal[][] bigdecimalarray4 = { {new BigDecimal(Integer.MIN_VALUE) }};
    assertEquals(0, compare(bigdecimalarray1, bigdecimalarray2));
    assertEquals(-1, compare(bigdecimalarray2, bigdecimalarray3));
    assertEquals(1, compare(bigdecimalarray3, bigdecimalarray2));
    assertEquals(1, compare(bigdecimalarray2, bigdecimalarray4));
    assertEquals(-1, compare(bigdecimalarray4, bigdecimalarray2));

    Boolean[][][] objectarray1 = { { {true}, {false} }, { {true, true }},
        { {false, false}, {true }} };
    Boolean[][][] objectarray2 = { { {true}, {false} }, { {true, true }},
        { {false, false}, {true }} };
    Boolean[][][] objectarray3 = { {{ true}, {false} }, { {false, true }} };
    Boolean[][][] objectarray4 = { { {true}, {false} }, { {true, true }} };
    assertEquals(0, compare(objectarray1, objectarray2));
    assertEquals(-1, compare(objectarray3, objectarray2));
    assertEquals(1, compare(objectarray2, objectarray3));
    assertEquals(-1, compare(objectarray4, objectarray2));
    assertEquals(1, compare(objectarray2, objectarray4));

    ArrayList<Character> arraylist1 = new ArrayList<Character>();
    ArrayList<Character> arraylist2 = new ArrayList<Character>();
    Object[] col1 = { arraylist1, arraylist2 };
    arraylist1.add('a');
    arraylist2.add('b');
    arraylist1.add('c');
    ArrayList<Character> arraylist3 = new ArrayList<Character>();
    ArrayList<Character> arraylist4 = new ArrayList<Character>();
    Object[] col2 = { arraylist3, arraylist4 };
    arraylist3.add('a');
    arraylist4.add('b');
    arraylist3.add('c');
    ArrayList<Character> arraylist5 = new ArrayList<Character>();
    Object[] col3 = { arraylist5 };
    arraylist5.add('a');
    arraylist5.add('c');
    assertEquals(0, compare(col1, col2));
    assertEquals(-1, compare(col3, col2));
    assertEquals(1, compare(col2, col3));
  }

  @Test
  public void testObjectArrayInt() {
    Object[] array1 = { new TestClassA(), new TestClassA() };
    Object[] array2 = { new TestClassA(), new TestClassA() };

    assertEquals(0, compare(array1, array1.length, array1, array1.length));
    assertEquals(true, compare(array1, array1.length, array2, array2.length) != 0);
    assertEquals(true, compare(array1, 1, array2, 1) != 0);

    boolean[][] booleanarray1 = { { true, false }, { false, true } };
    boolean[][] booleanarray2 = { { true, false }, { false, true } };
    boolean[][] booleanarray3 = { { false, true }, { true } };
    boolean[][] booleanarray4 = { { true, false } };
    assertEquals(0, compare(booleanarray1, booleanarray1.length, 
        booleanarray2, booleanarray2.length));
    assertEquals(1, compare(booleanarray1, booleanarray1.length, 
        booleanarray3, booleanarray3.length));
    assertEquals(-1, compare(booleanarray3, booleanarray3.length, 
        booleanarray1 , booleanarray1.length));
    assertEquals(1, compare(booleanarray1, booleanarray1.length, 
        booleanarray4, booleanarray4.length));
    assertEquals(-1, compare(booleanarray4, booleanarray4.length, 
        booleanarray1, booleanarray1.length));

    char[][] chararray1 = { { (char) 0, (char) 1 },
        { (char) Character.MIN_VALUE / 2, Character.MIN_VALUE },
        { (char) Character.MAX_VALUE, Character.MAX_VALUE } };
    char[][] chararray2 = { { (char) 0, (char) 1 },
        { (char) Character.MIN_VALUE / 2, Character.MIN_VALUE },
        { (char) Character.MAX_VALUE, Character.MAX_VALUE } };
    char[][] chararray3 = { { (char) 0, (char) 3 } };
    char[][] chararray4 = { { (char) 0, (char) 1 },
        { (char) Character.MIN_VALUE / 2, Character.MIN_VALUE } };
    assertEquals(0, compare(chararray1, chararray1.length, chararray2, 
        chararray2.length));
    assertEquals(-2, compare(chararray1, chararray1.length, chararray3, 
        chararray3.length));
    assertEquals(2, compare(chararray3, chararray3.length, chararray1, 
        chararray1.length));
    assertEquals(1, compare(chararray1, chararray1.length, chararray4, 
        chararray4.length));
    assertEquals(-1, compare(chararray4, chararray4.length, chararray1, 
        chararray1.length));
    assertEquals(0, compare(chararray1, 1, chararray4, 1));

    byte[][] bytearray1 = { { (byte) 0, (byte) -1, (byte) 1 },
        { (byte) Byte.MIN_VALUE / 2, Byte.MIN_VALUE },
        { (byte) Byte.MAX_VALUE, Byte.MAX_VALUE } };
    byte[][] bytearray2 = { { (byte) 0, (byte) -1, (byte) 1 },
        { (byte) Byte.MIN_VALUE / 2, Byte.MIN_VALUE },
        { (byte) Byte.MAX_VALUE, Byte.MAX_VALUE } };
    byte[][] bytearray3 = { { (byte) 0, (byte) 3 } };
    byte[][] bytearray4 = { { (byte) 0, (byte) -1, (byte) 1 },
        { (byte) Byte.MIN_VALUE / 2, Byte.MIN_VALUE } };
    assertEquals(0, compare(bytearray1, bytearray1.length, bytearray2, bytearray2.length));
    assertEquals(-4, compare(bytearray1, bytearray1.length, bytearray3, bytearray3.length));
    assertEquals(4, compare(bytearray3, bytearray3.length, bytearray1, bytearray1.length));
    assertEquals(1, compare(bytearray1, bytearray1.length, bytearray4, bytearray4.length));
    assertEquals(-1, compare(bytearray4, bytearray4.length, bytearray1, bytearray1.length));
    assertEquals(0, compare(bytearray1, 1, bytearray4, 1));

    short[][] shortarray1 = { { (short) 0, (short) -1, (short) 1 },
        { (short) Short.MIN_VALUE / 2, Short.MIN_VALUE },
        { (short) Short.MAX_VALUE, Short.MAX_VALUE } };
    short[][] shortarray2 = { { (short) 0, (short) -1, (short) 1 },
        { (short) Short.MIN_VALUE / 2, Short.MIN_VALUE },
        { (short) Short.MAX_VALUE, Short.MAX_VALUE } };
    short[][] shortarray3 = { { (short) 0, (short) 3 } };
    short[][] shortarray4 = { { (short) 0, (short) -1, (short) 1 },
        { (short) Short.MIN_VALUE / 2, Short.MIN_VALUE } };
    assertEquals(0, compare(shortarray1, shortarray1.length, shortarray2, shortarray2.length));
    assertEquals(-4, compare(shortarray1, shortarray1.length, shortarray3, shortarray3.length));
    assertEquals(4, compare(shortarray3, shortarray3.length, shortarray1, shortarray1.length));
    assertEquals(1, compare(shortarray1, shortarray1.length, shortarray4, shortarray4.length));
    assertEquals(-1, compare(shortarray4, shortarray4.length, shortarray1, shortarray1.length));
    assertEquals(0, compare(shortarray1, 1, shortarray4, 1));

    int[][] intarray1 = { { (int) 0, (int) -1, (int) 1 },
        { (int) Integer.MIN_VALUE / 2, Integer.MIN_VALUE },
        { (int) Integer.MAX_VALUE, Integer.MAX_VALUE } };
    int[][] intarray2 = { { (int) 0, (int) -1, (int) 1 },
        { (int) Integer.MIN_VALUE / 2, Integer.MIN_VALUE },
        { (int) Integer.MAX_VALUE, Integer.MAX_VALUE } };
    int[][] intarray3 = { { (int) 0, (int) 3 } };
    int[][] intarray4 = { { (int) 0, (int) -1, (int) 1 },
        { (int) Integer.MIN_VALUE / 2, Integer.MIN_VALUE } };
    assertEquals(0, compare(intarray1, intarray1.length, intarray2, intarray2.length));
    assertEquals(-4, compare(intarray1, intarray1.length, intarray3, intarray3.length));
    assertEquals(4, compare(intarray3, intarray3.length, intarray1, intarray1.length));
    assertEquals(1, compare(intarray1, intarray1.length, intarray4, intarray4.length));
    assertEquals(-1, compare(intarray4, intarray4.length, intarray1, intarray1.length));
    assertEquals(0, compare(intarray1, 1, intarray4, 1));

    long[][] longarray1 = { { (long) 0, (long) -1, (long) 1 },
        { (long) Long.MIN_VALUE / 2, Long.MIN_VALUE },
        { (long) Long.MAX_VALUE, Long.MAX_VALUE } };
    long[][] longarray2 = { { (long) 0, (long) -1, (long) 1 },
        { (long) Long.MIN_VALUE / 2, Long.MIN_VALUE },
        { (long) Long.MAX_VALUE, Long.MAX_VALUE } };
    long[][] longarray3 = { { (long) 0, (long) 3 } };
    long[][] longarray4 = { { (long) 0, (long) -1, (long) 1 },
        { (long) Long.MIN_VALUE / 2, Long.MIN_VALUE } };
    assertEquals(0, compare(longarray1, longarray1.length, longarray2, longarray2.length));
    assertEquals(-1, compare(longarray1, longarray1.length, longarray3, longarray3.length));
    assertEquals(1, compare(longarray3, longarray3.length, longarray1, longarray1.length));
    assertEquals(1, compare(longarray1, longarray1.length, longarray4, longarray4.length));
    assertEquals(-1, compare(longarray4, longarray4.length, longarray1, longarray1.length));
    assertEquals(0, compare(longarray1, 1, longarray4, 1));

    float[][] floatarray1 = { { (float) 0, (float) -1, (float) 1 },
        { (float) Float.MIN_VALUE / 2, Float.MIN_VALUE },
        { (float) Float.MAX_VALUE, Float.MAX_VALUE } };
    float[][] floatarray2 = { { (float) 0, (float) -1, (float) 1 },
        { (float) Float.MIN_VALUE / 2, Float.MIN_VALUE },
        { (float) Float.MAX_VALUE, Float.MAX_VALUE } };
    float[][] floatarray3 = { { (float) 0, (float) 3 } };
    float[][] floatarray4 = { { (float) 0, (float) -1, (float) 1 },
        { (float) Float.MIN_VALUE / 2, Float.MIN_VALUE } };
    assertEquals(0, compare(floatarray1, floatarray1.length, floatarray2, floatarray2.length));
    assertEquals(-1, compare(floatarray1, floatarray1.length, floatarray3, floatarray3.length));
    assertEquals(1, compare(floatarray3, floatarray3.length, floatarray1, floatarray1.length));
    assertEquals(1, compare(floatarray1, floatarray1.length, floatarray4, floatarray4.length));
    assertEquals(-1, compare(floatarray4, floatarray4.length, floatarray1, floatarray1.length));
    assertEquals(0, compare(floatarray1, 1, floatarray4, 1));

    double[][] doublearray1 = { { (double) 0, (double) -1, (double) 1 },
        { (double) Double.MIN_VALUE / 2, Double.MIN_VALUE },
        { (double) Double.MAX_VALUE, Double.MAX_VALUE } };
    double[][] doublearray2 = { { (double) 0, (double) -1, (double) 1 },
        { (double) Double.MIN_VALUE / 2, Double.MIN_VALUE },
        { (double) Double.MAX_VALUE, Double.MAX_VALUE } };
    double[][] doublearray3 = { { (double) 0, (double) 3 } };
    double[][] doublearray4 = { { (double) 0, (double) -1, (double) 1 },
        { (double) Double.MIN_VALUE / 2, Double.MIN_VALUE } };
    assertEquals(0, compare(doublearray1, doublearray1.length, doublearray2, doublearray2.length));
    assertEquals(-1, compare(doublearray1, doublearray1.length, doublearray3, doublearray3.length));
    assertEquals(1, compare(doublearray3, doublearray3.length, doublearray1, doublearray1.length));
    assertEquals(1, compare(doublearray1, doublearray1.length, doublearray4, doublearray4.length));
    assertEquals(-1, compare(doublearray4, doublearray4.length, doublearray1, doublearray1.length));
    assertEquals(0, compare(doublearray1, 1, doublearray4, 1));

    String[][] stringarray1 = { { "long", "long" }, { "ago" } };
    String[][] stringarray2 = { { "long", "long" }, { "ago" } };
    String[][] stringarray3 = { { "long", "time" }, { "no", "see" } };
    String[][] stringarray4 = { { "long", "long" } };
    assertEquals(0, compare(stringarray1, stringarray1.length, stringarray2, 
        stringarray2.length));
    assertEquals(-8, compare(stringarray2, stringarray2.length, stringarray3, 
        stringarray3.length));
    assertEquals(8, compare(stringarray3, stringarray3.length, stringarray2, 
        stringarray2.length));
    assertEquals(1, compare(stringarray2, stringarray2.length, stringarray4, 
        stringarray4.length));
    assertEquals(-1, compare(stringarray4, stringarray4.length, stringarray2, 
        stringarray2.length));
    assertEquals(0, compare(stringarray1, 1, stringarray4, 1));

    Boolean[][] booleanobjectarray1 = { { true, false }, { false, true } };
    Boolean[][] booleanobjectarray2 = { { true, false }, { false, true } };
    Boolean[][] booleanobjectarray3 = { { false, true }, { true } };
    Boolean[][] booleanobjectarray4 = { { true, false } };
    assertEquals(0, compare(booleanobjectarray1, booleanobjectarray1.length, 
        booleanobjectarray2, booleanobjectarray2.length));
    assertEquals(1, compare(booleanobjectarray2, booleanobjectarray2.length, 
        booleanobjectarray3, booleanobjectarray3.length));
    assertEquals(-1, compare(booleanobjectarray3, booleanobjectarray3.length, 
        booleanobjectarray2, booleanobjectarray2.length));
    assertEquals(1, compare(booleanobjectarray2, booleanobjectarray2.length, 
        booleanobjectarray4, booleanobjectarray4.length));
    assertEquals(-1, compare(booleanobjectarray4, booleanobjectarray4.length, 
        booleanobjectarray2, booleanobjectarray2.length));
    assertEquals(0, compare(booleanobjectarray1, 1, booleanobjectarray4, 1));

    Character[][] charobjectarray1 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    Character[][] charobjectarray2 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    Character[][] charobjectarray3 = { { (char) 0, (char) 3 } };
    Character[][] charobjectarray4 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE } };
    assertEquals(0, compare(charobjectarray1, charobjectarray1.length, 
        charobjectarray2, charobjectarray2.length));
    assertEquals(2, compare(charobjectarray3, charobjectarray3.length, 
        charobjectarray2, charobjectarray2.length));
    assertEquals(-2, compare(charobjectarray2, charobjectarray2.length, 
        charobjectarray3, charobjectarray3.length));
    assertEquals(-1, compare(charobjectarray4, charobjectarray4.length, 
        charobjectarray2, charobjectarray2.length));
    assertEquals(1, compare(charobjectarray2, charobjectarray2.length, 
        charobjectarray4, charobjectarray4.length));
    assertEquals(0, compare(charobjectarray1, 1, charobjectarray4, 1));

    Byte[][] byteobjectarray1 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    Byte[][] byteobjectarray2 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    Byte[][] byteobjectarray3 = { { (byte) 0, (byte) 2 } };
    Byte[][] byteobjectarray4 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) } };
    assertEquals(0, compare(byteobjectarray1, byteobjectarray1.length,
        byteobjectarray2, byteobjectarray2.length));
    assertEquals(3, compare(byteobjectarray3, byteobjectarray3.length, 
        byteobjectarray2, byteobjectarray2.length));
    assertEquals(-3, compare(byteobjectarray2, byteobjectarray2.length, 
        byteobjectarray3, byteobjectarray3.length));
    assertEquals(-1, compare(byteobjectarray4, byteobjectarray4.length, 
        byteobjectarray2, byteobjectarray2.length));
    assertEquals(1, compare(byteobjectarray2, byteobjectarray2.length, 
        byteobjectarray4, byteobjectarray4.length));
    assertEquals(0, compare(byteobjectarray1, 1, byteobjectarray4, 1));

    Short[][] shortobjectarray1 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
    Short[][] shortobjectarray2 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
    Short[][] shortobjectarray3 = { { (short) 0, (short) 2 } };
    Short[][] shortobjectarray4 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) } };
    assertEquals(0, compare(shortobjectarray1, shortobjectarray1.length, 
        shortobjectarray2, shortobjectarray2.length));
    assertEquals(3, compare(shortobjectarray3, shortobjectarray3.length, 
        shortobjectarray2, shortobjectarray2.length));
    assertEquals(-3, compare(shortobjectarray2, shortobjectarray2.length, 
        shortobjectarray3, shortobjectarray3.length));
    assertEquals(-1, compare(shortobjectarray4, shortobjectarray4.length,
        shortobjectarray2, shortobjectarray2.length));
    assertEquals(1, compare(shortobjectarray2, shortobjectarray2.length, 
        shortobjectarray4, shortobjectarray4.length));
    assertEquals(0, compare(shortobjectarray1, 1, shortobjectarray4, 1));

    Integer[][] integerarray1 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) },
        { (int) (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
    Integer[][] integerarray2 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) },
        { (int) (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
    Integer[][] integerarray3 = { { (int) 0, (int) 3 } };
    Integer[][] integerarray4 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) } };
    assertEquals(0, compare(integerarray1, integerarray1.length, 
        integerarray2, integerarray2.length));
    assertEquals(4, compare(integerarray3, integerarray3.length, 
        integerarray2, integerarray2.length));
    assertEquals(-4, compare(integerarray2, integerarray2.length, 
        integerarray3, integerarray3.length));
    assertEquals(-1, compare(integerarray4, integerarray4.length, 
        integerarray2, integerarray2.length));
    assertEquals(1, compare(integerarray2, integerarray2.length, 
        integerarray4, integerarray4.length));
    assertEquals(0, compare(integerarray1, 1, integerarray4, 1));

    Long[][] longobjectarray1 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) },
        { (long) (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
    Long[][] longobjectarray2 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) },
        { (long) (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
    Long[][] longobjectarray3 = { { (long) 0, (long) 2 } };
    Long[][] longobjectarray4 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) } };
    assertEquals(0, compare(longobjectarray1, longobjectarray1.length, 
        longobjectarray2, longobjectarray2.length));
    assertEquals(1, compare(longobjectarray3, longobjectarray3.length, 
        longobjectarray2, longobjectarray2.length));
    assertEquals(-1, compare(longobjectarray2, longobjectarray2.length, 
        longobjectarray3, longobjectarray3.length));
    assertEquals(-1, compare(longobjectarray4, longobjectarray4.length, 
        longobjectarray2, longobjectarray2.length));
    assertEquals(1, compare(longobjectarray2, longobjectarray2.length, 
        longobjectarray4, longobjectarray4.length));
    assertEquals(0, compare(longobjectarray1, 1, longobjectarray4, 1));

    Float[][] floatobjectarray1 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) },
        { (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
    Float[][] floatobjectarray2 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) },
        { (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
    Float[][] floatobjectarray3 = { { (float) 0, (float) 2 } };
    Float[][] floatobjectarray4 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) } };
    assertEquals(0, compare(floatobjectarray1, floatobjectarray1.length, 
        floatobjectarray2, floatobjectarray2.length));
    assertEquals(1, compare(floatobjectarray3, floatobjectarray3.length, 
        floatobjectarray2, floatobjectarray2.length));
    assertEquals(-1, compare(floatobjectarray2, floatobjectarray2.length, 
        floatobjectarray3, floatobjectarray3.length));
    assertEquals(-1, compare(floatobjectarray4, floatobjectarray4.length, 
        floatobjectarray2, floatobjectarray2.length));
    assertEquals(1, compare(floatobjectarray2, floatobjectarray2.length, 
        floatobjectarray4, floatobjectarray4.length));
    assertEquals(0, compare(floatobjectarray1, 1, floatobjectarray4, 1));

    Double[][] doubleobjectarray1 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE },
        { (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
    Double[][] doubleobjectarray2 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE },
        { (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
    Double[][] doubleobjectarray3 = { { (double) 0, (double) 2 } };
    Double[][] doubleobjectarray4 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE } };
    assertEquals(0, compare(doubleobjectarray1, doubleobjectarray1.length, 
        doubleobjectarray2, doubleobjectarray2.length));
    assertEquals(1, compare(doubleobjectarray3, doubleobjectarray3.length, 
        doubleobjectarray2, doubleobjectarray2.length));
    assertEquals(-1, compare(doubleobjectarray2, doubleobjectarray2.length, 
        doubleobjectarray3, doubleobjectarray3.length));
    assertEquals(-1, compare(doubleobjectarray4, doubleobjectarray4.length, 
        doubleobjectarray2, doubleobjectarray2.length));
    assertEquals(1, compare(doubleobjectarray2, doubleobjectarray2.length, 
        doubleobjectarray4, doubleobjectarray4.length));
    assertEquals(0, compare(doubleobjectarray1, 1, doubleobjectarray4, 1));

    Class<?>[][] classarray1 = { { TestClassA.class }, { TestClassA.class } };
    Class<?>[][] classarray2 = { { TestClassA.class }, { TestClassA.class } };
    Class<?>[][] classarray3 = { { TestClassB.class }, { TestClassB.class } };
    Class<?>[][] classarray4 = { { TestClassA.class }, { TestClassA.class },
        { TestClassA.class }};
    assertEquals(0, compare(classarray1, classarray1.length, classarray2, 
        classarray2.length));
    assertEquals(1, compare(classarray3, classarray3.length, classarray2, 
        classarray2.length));
    assertEquals(-1, compare(classarray2, classarray2.length, classarray3,
        classarray3.length));
    assertEquals(1, compare(classarray4, classarray4.length, classarray2, 
        classarray2.length));
    assertEquals(-1, compare(classarray2, classarray2.length, classarray4,
        classarray4.length));
    assertEquals(0, compare(classarray1, 1, classarray4, 1));

    Date datea = new Date();
    datea.setTime((long) 123456);
    Date dateb = new Date();
    dateb.setTime((long) 7891011);
    Date datec = new Date();
    datec.setTime((long) 654321);
    Date[][] datearray1 = { {datea, dateb}, {datec} };
    Date[][] datearray2 = { {datea, dateb}, {datec} };
    Date[][] datearray3 = { {datec, datea}, {dateb} };
    Date[][] datearray4 = { {datea, dateb}};
    assertEquals(0, compare(datearray1, datearray1.length, datearray2, 
        datearray2.length));
    assertEquals(1, compare(datearray3, datearray3.length, datearray2, 
        datearray2.length));
    assertEquals(-1, compare(datearray2, datearray2.length, datearray3, 
        datearray3.length));
    assertEquals(-1, compare(datearray4, datearray4.length, datearray2, 
        datearray2.length));
    assertEquals(1, compare(datearray2, datearray2.length, datearray4,
        datearray4.length));
    assertEquals(0, compare(datearray1, 1, datearray4, 1));

    BigInteger[][] bigintegerarray1 = { {new BigInteger(bytearray1[0])},
        {new BigInteger(bytearray3[0]) }};
    BigInteger[][] bigintegerarray2 = { {new BigInteger(bytearray2[0])},
        {new BigInteger(bytearray3[0]) }};
    BigInteger[][] bigintegerarray3 = { {new BigInteger(bytearray3[0]) }};
    BigInteger[][] bigintegerarray4 = { {new BigInteger(bytearray2[0]) }};
    assertEquals(0, compare(bigintegerarray1, bigintegerarray1.length, 
        bigintegerarray2, bigintegerarray2.length));
    assertEquals(-1, compare(bigintegerarray3, bigintegerarray3.length, 
        bigintegerarray2, bigintegerarray2.length));
    assertEquals(1, compare(bigintegerarray2, bigintegerarray2.length, 
        bigintegerarray3, bigintegerarray3.length));
    assertEquals(-1, compare(bigintegerarray4, bigintegerarray4.length, 
        bigintegerarray2, bigintegerarray2.length));
    assertEquals(1, compare(bigintegerarray2, bigintegerarray2.length, 
        bigintegerarray4, bigintegerarray4.length));
    assertEquals(0, compare(bigintegerarray1, 1, bigintegerarray4, 1));

    BigDecimal[][] bigdecimalarray1 = { {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE) }};
    BigDecimal[][] bigdecimalarray2 = { {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE) }};
    BigDecimal[][] bigdecimalarray3 = { {new BigDecimal(0), new BigDecimal(-1)}};
    BigDecimal[][] bigdecimalarray4 = { {new BigDecimal(Integer.MIN_VALUE) }};
    assertEquals(0, compare(bigdecimalarray1, bigdecimalarray1.length, 
        bigdecimalarray2, bigdecimalarray2.length));
    assertEquals(-1, compare(bigdecimalarray2, bigdecimalarray2.length, 
        bigdecimalarray3, bigdecimalarray3.length));
    assertEquals(1, compare(bigdecimalarray3, bigdecimalarray3.length, 
        bigdecimalarray2, bigdecimalarray2.length));
    assertEquals(1, compare(bigdecimalarray2, bigdecimalarray2.length, 
        bigdecimalarray4, bigdecimalarray4.length));
    assertEquals(-1, compare(bigdecimalarray4, bigdecimalarray4.length, 
        bigdecimalarray2, bigdecimalarray2.length));
    assertEquals(0, compare(bigdecimalarray1, 1, bigdecimalarray4, 1));

    Boolean[][][] objectarray1 = { { {true}, {false} }, { {true, true }},
        { {false, false}, {true }} };
    Boolean[][][] objectarray2 = { { {true}, {false} }, { {true, true }},
        { {false, false}, {true }} };
    Boolean[][][] objectarray3 = { {{ true}, {false} }, { {false, true }} };
    Boolean[][][] objectarray4 = { { {true}, {false} }, { {true, true }} };
    assertEquals(0, compare(objectarray1, objectarray1.length, objectarray2, 
        objectarray2.length));
    assertEquals(-1, compare(objectarray3, objectarray3.length, objectarray2, 
        objectarray2.length));
    assertEquals(1, compare(objectarray2, objectarray2.length, objectarray3, 
        objectarray3.length));
    assertEquals(-1, compare(objectarray4, objectarray4.length, objectarray2, 
        objectarray2.length));
    assertEquals(1, compare(objectarray2, objectarray2.length, objectarray4, 
        objectarray4.length));
    assertEquals(0, compare(objectarray1, 1, objectarray4, 1));

    ArrayList<Character> arraylist1 = new ArrayList<Character>();
    ArrayList<Character> arraylist2 = new ArrayList<Character>();
    Object[] col1 = { arraylist1, arraylist2 };
    arraylist1.add('a');
    arraylist2.add('b');
    arraylist1.add('c');
    ArrayList<Character> arraylist3 = new ArrayList<Character>();
    ArrayList<Character> arraylist4 = new ArrayList<Character>();
    Object[] col2 = { arraylist3, arraylist4 };
    arraylist3.add('a');
    arraylist4.add('b');
    arraylist3.add('c');
    ArrayList<Character> arraylist5 = new ArrayList<Character>();
    Object[] col3 = { arraylist5 };
    arraylist5.add('a');
    arraylist5.add('c');
    assertEquals(0, compare(col1, col2));
    assertEquals(-1, compare(col3, col2));
    assertEquals(1, compare(col2, col3));
  }

  @Test
  public void testObjectWithEpsilon() {
    double epsilon = 0.01;
    Object value1 = new TestClassA();
    Object value2 = new TestClassA();

    assertEquals(0, compare(value1, value1, epsilon));
    assertEquals(true, compare(value1, value2, epsilon) != 0);
    value2 = new TestClassB();
    assertEquals(-1, compare(value1, value2, epsilon));
    assertEquals(1, compare(value2, value1, epsilon));
    value2 = new TestClassZ();
    assertEquals(-25, compare(value1, value2, epsilon));
    assertEquals(25, compare(value2, value1, epsilon));
    value1 = TestClassA.class;
    value2 = Class.class;
    assertEquals(-7, compare(value1, value2, epsilon));
    assertEquals(7, compare(value2, value1, epsilon));
    value2 = null;
    assertEquals(1, compare(value1, value2, epsilon));
    assertEquals(-1, compare(value2, value1, epsilon));
    value1 = null;
    assertEquals(0, compare(value1, value2, epsilon));

    boolean[] booleanarray1 = { true, false, false, true };
    boolean[] booleanarray2 = { true, false, false, true };
    boolean[] booleanarray3 = { false, true, true };
    boolean[] booleanarray4 = { true, false };
    assertEquals(0, compare(booleanarray1, (Object) booleanarray2, epsilon));
    assertEquals(1, compare(booleanarray2, (Object) booleanarray3, epsilon));
    assertEquals(-1, compare(booleanarray3, (Object) booleanarray2, epsilon));
    assertEquals(2, compare(booleanarray2, (Object) booleanarray4, epsilon));
    assertEquals(-2, compare(booleanarray4, (Object) booleanarray2, epsilon));

    char[] chararray1 = { (char) 0, (char) 1, (char) (Character.MIN_VALUE / 2),
        Character.MIN_VALUE, (char) (Character.MAX_VALUE / 2),
        Character.MAX_VALUE };
    char[] chararray2 = { (char) 0, (char) 1, (char) (Character.MIN_VALUE / 2),
        Character.MIN_VALUE, (char) (Character.MAX_VALUE / 2),
        Character.MAX_VALUE };
    char[] chararray3 = { (char) 0, (char) 3 };
    char[] chararray4 = { (char) 0, (char) 1, (char) (Character.MIN_VALUE / 2),
        Character.MIN_VALUE };

    assertEquals(0, compare(chararray1, (Object) chararray2, epsilon));
    assertEquals(2, compare(chararray3, (Object) chararray2, epsilon));
    assertEquals(-2, compare(chararray2, (Object) chararray3, epsilon));
    assertEquals(-2, compare(chararray4, (Object) chararray2, epsilon));
    assertEquals(2, compare(chararray2, (Object) chararray4, epsilon));

    byte[] bytearray1 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE };
    byte[] bytearray2 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE };
    byte[] bytearray3 = { (byte) 0, (byte) 2 };
    byte[] bytearray4 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2) };
    assertEquals(0, compare(bytearray1, (Object) bytearray2, epsilon));
    assertEquals(3, compare(bytearray3, (Object) bytearray2, epsilon));
    assertEquals(-3, compare(bytearray2, (Object) bytearray3, epsilon));
    assertEquals(2, compare(bytearray2, (Object) bytearray4, epsilon));
    assertEquals(-2, compare(bytearray4, (Object) bytearray2, epsilon));

    short[] shortarray1 = { (short) 0, (short) -1, (short) 1, Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2), Short.MIN_VALUE,
        (short) (Short.MAX_VALUE / 2) };
    short[] shortarray2 = { (short) 0, (short) -1, (short) 1, Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2), Short.MIN_VALUE,
        (short) (Short.MAX_VALUE / 2) };
    short[] shortarray3 = { (short) 0, (short) 3 };
    short[] shortarray4 = { (short) 0, (short) -1, (short) 1, Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2) };
    assertEquals(0, compare(shortarray1, (Object) shortarray2, epsilon));
    assertEquals(4, compare(shortarray3, (Object) shortarray2, epsilon));
    assertEquals(-4, compare(shortarray2, (Object) shortarray3, epsilon));
    assertEquals(2, compare(shortarray2, (Object) shortarray4, epsilon));
    assertEquals(-2, compare(shortarray4, (Object) shortarray2, epsilon));

    int[] intarray1 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE };
    int[] intarray2 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE };
    int[] intarray3 = { (int) 0, (int) 4 };
    int[] intarray4 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2) };
    assertEquals(0, compare(intarray1, (Object) intarray2, epsilon));
    assertEquals(5, compare(intarray3, (Object) intarray2, epsilon));
    assertEquals(-5, compare(intarray2, (Object) intarray3, epsilon));
    assertEquals(2, compare(intarray2, (Object) intarray4, epsilon));
    assertEquals(-2, compare(intarray4, (Object) intarray2, epsilon));

    long[] longarray1 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MAX_VALUE };
    long[] longarray2 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MAX_VALUE };
    long[] longarray3 = { (long) 0, (long) 2 };
    long[] longarray4 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        (long) (Long.MIN_VALUE / 2) };
    assertEquals(0, compare(longarray1, (Object) longarray2, epsilon));
    assertEquals(1, compare(longarray3, (Object) longarray2, epsilon));
    assertEquals(-1, compare(longarray2, (Object) longarray3, epsilon));
    assertEquals(-2, compare(longarray4, (Object) longarray2, epsilon));
    assertEquals(2, compare(longarray2, (Object) longarray4, epsilon));

    float[] floatarray1 = { (float) 0, (float) -1, (float) 1, Float.MIN_VALUE,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MAX_VALUE };
    float[] floatarray2 = { (float) 0, (float) -1, (float) (1 - epsilon), Float.MIN_VALUE,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MAX_VALUE };
    float[] floatarray3 = { (float) 0, (float) (2 + epsilon) };
    float[] floatarray4 = { (float) 0, (float) -1, (float) 1, Float.MIN_VALUE,
        (float) (Float.MIN_VALUE / 2) };
    assertEquals(0, compare(floatarray1, (Object) floatarray2, epsilon));
    assertEquals(-1, compare(floatarray2, (Object) floatarray3, epsilon));
    assertEquals(1, compare(floatarray3, (Object) floatarray2, epsilon));
    assertEquals(2, compare(floatarray2, (Object) floatarray4, epsilon));
    assertEquals(-2, compare(floatarray4, (Object) floatarray2, epsilon));

    Float[] floatobjectarray1 = { (float) 0, (float) -1, (float) 1,
        Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2),
        (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE };
    Float[] floatobjectarray2 = { (float) 0, (float) -1, (float) (1 - epsilon),
        Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2),
        (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE };
    Float[] floatobjectarray3 = { (float) 0, (float) (2 + epsilon)};
    Float[] floatobjectarray4 = { (float) 0, (float) -1, (float) 1,
        Float.MIN_VALUE };
    assertEquals(0, compare(floatobjectarray1, (Object) floatobjectarray2, epsilon));
    assertEquals(1, compare(floatobjectarray3, (Object) floatobjectarray2, epsilon));
    assertEquals(-1, compare(floatobjectarray2, (Object) floatobjectarray3, epsilon));
    assertEquals(-3, compare(floatobjectarray4, (Object) floatobjectarray2, epsilon));
    assertEquals(3, compare(floatobjectarray2, (Object) floatobjectarray4, epsilon));
    
    double[] doublearray1 = { (double) 0, (double) -1, (double) 1,
        Double.MIN_VALUE, (double) (Double.MIN_VALUE / 2),
        (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE };
    double[] doublearray2 = { (double) 0, (double) -1, 1.0 - epsilon/10,
        Double.MIN_VALUE, (double) (Double.MIN_VALUE / 2),
        (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE };
    double[] doublearray3 = { (double) 0, Double.MAX_VALUE };
    
    double[] doublearray4 = { (double) 0, (double) -1, (double) 1,
        Double.MIN_VALUE, (double) (Double.MIN_VALUE / 2) };
    assertEquals(0, compare(doublearray1, (Object) doublearray2, epsilon));
    assertEquals(1, compare(doublearray3, (Object) doublearray2, epsilon));
    assertEquals(-1, compare(doublearray2, (Object) doublearray3, epsilon));
    assertEquals(-2, compare(doublearray4, (Object) doublearray2, epsilon));
    assertEquals(2, compare(doublearray2, (Object) doublearray4, epsilon));

    Double[] doubleobjectarray1 = { (double) 0, (double) -1, (double) 1,
        (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE,
        (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE };
    Double[] doubleobjectarray2 = { (double) 0, (double) -1, (double) (1 - epsilon/10),
        (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE,
        (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE };
    Double[] doubleobjectarray3 = { (double) 0, (double) (2+epsilon) };
    Double[] doubleobjectarray4 = { (double) 0, (double) -1, (double) 1,
        (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE };
    assertEquals(0, compare(doubleobjectarray1, (Object) doubleobjectarray2, epsilon));
    assertEquals(1, compare(doubleobjectarray3, (Object) doubleobjectarray2, epsilon));
    assertEquals(-1, compare(doubleobjectarray2, (Object) doubleobjectarray3, epsilon));
    assertEquals(-2, compare(doubleobjectarray4, (Object) doubleobjectarray2, epsilon));
    assertEquals(2, compare(doubleobjectarray2, (Object) doubleobjectarray4, epsilon));
    
    String[] stringarray1 = { "long", "long", "ago" };
    String[] stringarray2 = { "long", "long", "ago" };
    String[] stringarray3 = { "long", "time", "no", "see" };
    String[] stringarray4 = { "long", "long" };
    assertEquals(0, compare(stringarray1, (Object) stringarray2, epsilon));
    assertEquals(-8, compare(stringarray2, (Object) stringarray3, epsilon));
    assertEquals(8, compare(stringarray3, (Object) stringarray2, epsilon));
    assertEquals(1, compare(stringarray2, (Object) stringarray4, epsilon));
    assertEquals(-1, compare(stringarray4, (Object) stringarray2, epsilon));

    Boolean[] booleanobjectarray1 = { true, false, false, true };
    Boolean[] booleanobjectarray2 = { true, false, false, true };
    Boolean[] booleanobjectarray3 = { false, true, true };
    Boolean[] booleanobjectarray4 = { true, false };
    assertEquals(0, compare(booleanobjectarray1, (Object) booleanobjectarray2, epsilon));
    assertEquals(1, compare(booleanobjectarray2, (Object) booleanobjectarray3, epsilon));
    assertEquals(-1, compare(booleanobjectarray3, (Object) booleanobjectarray2, epsilon));
    assertEquals(2, compare(booleanobjectarray2, (Object) booleanobjectarray4, epsilon));
    assertEquals(-2, compare(booleanobjectarray4, (Object) booleanobjectarray2, epsilon));

    Character[] charobjectarray1 = { (char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE };
    Character[] charobjectarray2 = { (char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE };
    Character[] charobjectarray3 = { (char) 0, (char) 3 };
    Character[] charobjectarray4 = { (char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE };
    assertEquals(0, compare(charobjectarray1, (Object) charobjectarray2, epsilon));
    assertEquals(2, compare(charobjectarray3, (Object) charobjectarray2, epsilon));
    assertEquals(-2, compare(charobjectarray2, (Object) charobjectarray3, epsilon));
    assertEquals(-2, compare(charobjectarray4, (Object) charobjectarray2, epsilon));
    assertEquals(2, compare(charobjectarray2, (Object) charobjectarray4, epsilon));

    Byte[] byteobjectarray1 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE };
    Byte[] byteobjectarray2 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE };
    Byte[] byteobjectarray3 = { (byte) 0, (byte) 2 };
    Byte[] byteobjectarray4 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2) };
    assertEquals(0, compare(byteobjectarray1, (Object) byteobjectarray2, epsilon));
    assertEquals(3, compare(byteobjectarray3, (Object) byteobjectarray2, epsilon));
    assertEquals(-3, compare(byteobjectarray2, (Object) byteobjectarray3, epsilon));
    assertEquals(-2, compare(byteobjectarray4, (Object) byteobjectarray2, epsilon));
    assertEquals(2, compare(byteobjectarray2, (Object) byteobjectarray4, epsilon));

    Short[] shortobjectarray1 = { (short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2),
        (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE };
    Short[] shortobjectarray2 = { (short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2),
        (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE };
    Short[] shortobjectarray3 = { (short) 0, (short) 2 };
    Short[] shortobjectarray4 = { (short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) };
    assertEquals(0, compare(shortobjectarray1, (Object) shortobjectarray2, epsilon));
    assertEquals(3, compare(shortobjectarray3, (Object) shortobjectarray2, epsilon));
    assertEquals(-3, compare(shortobjectarray2, (Object) shortobjectarray3, epsilon));
    assertEquals(-2, compare(shortobjectarray4, (Object) shortobjectarray2, epsilon));
    assertEquals(2, compare(shortobjectarray2, (Object) shortobjectarray4, epsilon));

    Integer[] integerarray1 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE };
    Integer[] integerarray2 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE };
    Integer[] integerarray3 = { (int) 0, (int) 3 };
    Integer[] integerarray4 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2) };
    assertEquals(0, compare(integerarray1, (Object) integerarray2, epsilon));
    assertEquals(4, compare(integerarray3, (Object) integerarray2, epsilon));
    assertEquals(-4, compare(integerarray2, (Object) integerarray3, epsilon));
    assertEquals(-2, compare(integerarray4, (Object) integerarray2, epsilon));
    assertEquals(2, compare(integerarray2, (Object) integerarray4, epsilon));

    Long[] longobjectarray1 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MAX_VALUE };
    Long[] longobjectarray2 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MAX_VALUE };
    Long[] longobjectarray3 = { (long) 0, (long) 2 };
    Long[] longobjectarray4 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE };
    assertEquals(0, compare(longobjectarray1, (Object) longobjectarray2, epsilon));
    assertEquals(1, compare(longobjectarray3, (Object) longobjectarray2, epsilon));
    assertEquals(-1, compare(longobjectarray2, (Object) longobjectarray3, epsilon));
    assertEquals(-3, compare(longobjectarray4, (Object) longobjectarray2, epsilon));
    assertEquals(3, compare(longobjectarray2, (Object) longobjectarray4, epsilon));

    Class<?>[] classarray1 = new Class<?>[] { TestClassA.class,
        TestClassA.class };
    Class<?>[] classarray2 = new Class<?>[] { TestClassA.class,
        TestClassA.class };
    Class<?>[] classarray3 = new Class<?>[] { TestClassB.class,
        TestClassB.class };
    Class<?>[] classarray4 = new Class<?>[] { TestClassA.class,
        TestClassA.class, TestClassA.class };
    assertEquals(0, compare(classarray1, (Object) classarray2, epsilon));
    assertEquals(1, compare(classarray3, (Object) classarray2, epsilon));
    assertEquals(-1, compare(classarray2, (Object) classarray3, epsilon));
    assertEquals(1, compare(classarray4, (Object) classarray2, epsilon));
    assertEquals(-1, compare(classarray2, (Object) classarray4, epsilon));

    Date datea = new Date();
    datea.setTime((long) 123456);
    Date dateb = new Date();
    dateb.setTime((long) 7891011);
    Date datec = new Date();
    datec.setTime((long) 654321);
    Date[] datearray1 = { datea, dateb, datec };
    Date[] datearray2 = { datea, dateb, datec };
    Date[] datearray3 = { datec, datea, dateb };
    Date[] datearray4 = { datea };
    assertEquals(0, compare(datearray1, (Object) datearray2, epsilon));
    assertEquals(1, compare(datearray3, (Object) datearray2, epsilon));
    assertEquals(-1, compare(datearray2, (Object) datearray3, epsilon));
    assertEquals(-2, compare(datearray4, (Object) datearray2, epsilon));
    assertEquals(2, compare(datearray2, (Object) datearray4, epsilon));

    BigInteger[] bigintegerarray1 = { new BigInteger(bytearray1),
        new BigInteger(bytearray3) };
    BigInteger[] bigintegerarray2 = { new BigInteger(bytearray2),
        new BigInteger(bytearray3) };
    BigInteger[] bigintegerarray3 = { new BigInteger(bytearray3) };
    BigInteger[] bigintegerarray4 = { new BigInteger(bytearray2) };
    assertEquals(0, compare(bigintegerarray1, (Object) bigintegerarray2, epsilon));
    assertEquals(-1, compare(bigintegerarray3, (Object) bigintegerarray2, epsilon));
    assertEquals(1, compare(bigintegerarray2, (Object) bigintegerarray3, epsilon));
    assertEquals(-1, compare(bigintegerarray4, (Object) bigintegerarray2, epsilon));
    assertEquals(1, compare(bigintegerarray2, (Object) bigintegerarray4, epsilon));

    BigDecimal[] bigdecimalarray1 = { new BigDecimal(Integer.MIN_VALUE),
        new BigDecimal(Integer.MAX_VALUE) };
    BigDecimal[] bigdecimalarray2 = { new BigDecimal(Integer.MIN_VALUE),
        new BigDecimal(Integer.MAX_VALUE) };
    BigDecimal[] bigdecimalarray3 = { new BigDecimal(0), new BigDecimal(-1) };
    BigDecimal[] bigdecimalarray4 = { new BigDecimal(Integer.MIN_VALUE) };
    assertEquals(0, compare(bigdecimalarray1, (Object) bigdecimalarray2, epsilon));
    assertEquals(-1, compare(bigdecimalarray2, (Object) bigdecimalarray3, epsilon));
    assertEquals(1, compare(bigdecimalarray3, (Object) bigdecimalarray2, epsilon));
    assertEquals(1, compare(bigdecimalarray2, (Object) bigdecimalarray4, epsilon));
    assertEquals(-1, compare(bigdecimalarray4, (Object) bigdecimalarray2, epsilon));

    Boolean[][] objectarray1 = { { true, false }, { true, true },
        { false, false, true } };
    Boolean[][] objectarray2 = { { true, false }, { true, true },
        { false, false, true } };
    Boolean[][] objectarray3 = { { true, false }, { true, true } };
    Boolean[][] objectarray4 = { { true, false }, { false, true } };
    assertEquals(0, compare(objectarray1, (Object) objectarray2, epsilon));
    assertEquals(-1, compare(objectarray3, (Object) objectarray2, epsilon));
    assertEquals(1, compare(objectarray2, (Object) objectarray3, epsilon));
    assertEquals(-1, compare(objectarray4, (Object) objectarray2, epsilon));
    assertEquals(1, compare(objectarray2, (Object) objectarray4, epsilon));

    ArrayList<Character> col1 = new ArrayList<Character>();
    col1.add('a');
    col1.add('b');
    col1.add('c');
    ArrayList<Character> col2 = new ArrayList<Character>();
    col2.add('a');
    col2.add('b');
    col2.add('c');
    ArrayList<Character> col3 = new ArrayList<Character>();
    col3.add('b');
    col3.add('c');
    assertEquals(0, compare(col1, (Object) col2, epsilon));
    assertEquals(1, compare(col3, (Object) col2, epsilon));
    assertEquals(-1, compare(col2, (Object) col3, epsilon));
  }

  @Test
  public void testObjectArrayWithEpsilon() {
    double epsilon = 0.01;
    Object[] array1 = { new TestClassA(), new TestClassA() };
    Object[] array2 = { new TestClassA(), new TestClassA() };

    assertEquals(0, compare(array1, array1, epsilon));
    assertEquals(true, compare(array1, array2, epsilon) != 0);
    array2 = null;
    assertEquals(1, compare(array1, array2, epsilon));
    assertEquals(-1, compare(array2, array1, epsilon));
    array1 = null;
    assertEquals(0, compare(array1, array2, epsilon));

    boolean[][] booleanarray1 = { { true, false }, { false, true } };
    boolean[][] booleanarray2 = { { true, false }, { false, true } };
    boolean[][] booleanarray3 = { { false, true }, { true } };
    boolean[][] booleanarray4 = { { true, false } };
    assertEquals(0, compare(booleanarray1, booleanarray2, epsilon));
    assertEquals(1, compare(booleanarray1, booleanarray3, epsilon));
    assertEquals(-1, compare(booleanarray3, booleanarray1, epsilon));
    assertEquals(1, compare(booleanarray1, booleanarray4, epsilon));
    assertEquals(-1, compare(booleanarray4, booleanarray1, epsilon));

    char[][] chararray1 = { { (char) 0, (char) 1 },
        { (char) Character.MIN_VALUE / 2, Character.MIN_VALUE },
        { (char) Character.MAX_VALUE, Character.MAX_VALUE } };
    char[][] chararray2 = { { (char) 0, (char) 1 },
        { (char) Character.MIN_VALUE / 2, Character.MIN_VALUE },
        { (char) Character.MAX_VALUE, Character.MAX_VALUE } };
    char[][] chararray3 = { { (char) 0, (char) 3 } };
    char[][] chararray4 = { { (char) 0, (char) 1 },
        { (char) Character.MIN_VALUE / 2, Character.MIN_VALUE } };
    assertEquals(0, compare(chararray1, chararray2, epsilon));
    assertEquals(-2, compare(chararray1, chararray3, epsilon));
    assertEquals(2, compare(chararray3, chararray1, epsilon));
    assertEquals(1, compare(chararray1, chararray4, epsilon));
    assertEquals(-1, compare(chararray4, chararray1, epsilon));

    byte[][] bytearray1 = { { (byte) 0, (byte) -1, (byte) 1 },
        { (byte) Byte.MIN_VALUE / 2, Byte.MIN_VALUE },
        { (byte) Byte.MAX_VALUE, Byte.MAX_VALUE } };
    byte[][] bytearray2 = { { (byte) 0, (byte) -1, (byte) 1 },
        { (byte) Byte.MIN_VALUE / 2, Byte.MIN_VALUE },
        { (byte) Byte.MAX_VALUE, Byte.MAX_VALUE } };
    byte[][] bytearray3 = { { (byte) 0, (byte) 3 } };
    byte[][] bytearray4 = { { (byte) 0, (byte) -1, (byte) 1 },
        { (byte) Byte.MIN_VALUE / 2, Byte.MIN_VALUE } };
    assertEquals(0, compare(bytearray1, bytearray2, epsilon));
    assertEquals(-4, compare(bytearray1, bytearray3, epsilon));
    assertEquals(4, compare(bytearray3, bytearray1, epsilon));
    assertEquals(1, compare(bytearray1, bytearray4, epsilon));
    assertEquals(-1, compare(bytearray4, bytearray1, epsilon));

    short[][] shortarray1 = { { (short) 0, (short) -1, (short) 1 },
        { (short) Short.MIN_VALUE / 2, Short.MIN_VALUE },
        { (short) Short.MAX_VALUE, Short.MAX_VALUE } };
    short[][] shortarray2 = { { (short) 0, (short) -1, (short) 1 },
        { (short) Short.MIN_VALUE / 2, Short.MIN_VALUE },
        { (short) Short.MAX_VALUE, Short.MAX_VALUE } };
    short[][] shortarray3 = { { (short) 0, (short) 3 } };
    short[][] shortarray4 = { { (short) 0, (short) -1, (short) 1 },
        { (short) Short.MIN_VALUE / 2, Short.MIN_VALUE } };
    assertEquals(0, compare(shortarray1, shortarray2, epsilon));
    assertEquals(-4, compare(shortarray1, shortarray3, epsilon));
    assertEquals(4, compare(shortarray3, shortarray1, epsilon));
    assertEquals(1, compare(shortarray1, shortarray4, epsilon));
    assertEquals(-1, compare(shortarray4, shortarray1, epsilon));

    int[][] intarray1 = { { (int) 0, (int) -1, (int) 1 },
        { (int) Integer.MIN_VALUE / 2, Integer.MIN_VALUE },
        { (int) Integer.MAX_VALUE, Integer.MAX_VALUE } };
    int[][] intarray2 = { { (int) 0, (int) -1, (int) 1 },
        { (int) Integer.MIN_VALUE / 2, Integer.MIN_VALUE },
        { (int) Integer.MAX_VALUE, Integer.MAX_VALUE } };
    int[][] intarray3 = { { (int) 0, (int) 3 } };
    int[][] intarray4 = { { (int) 0, (int) -1, (int) 1 },
        { (int) Integer.MIN_VALUE / 2, Integer.MIN_VALUE } };
    assertEquals(0, compare(intarray1, intarray2, epsilon));
    assertEquals(-4, compare(intarray1, intarray3, epsilon));
    assertEquals(4, compare(intarray3, intarray1, epsilon));
    assertEquals(1, compare(intarray1, intarray4, epsilon));
    assertEquals(-1, compare(intarray4, intarray1, epsilon));

    long[][] longarray1 = { { (long) 0, (long) -1, (long) 1 },
        { (long) Long.MIN_VALUE / 2, Long.MIN_VALUE },
        { (long) Long.MAX_VALUE, Long.MAX_VALUE } };
    long[][] longarray2 = { { (long) 0, (long) -1, (long) 1 },
        { (long) Long.MIN_VALUE / 2, Long.MIN_VALUE },
        { (long) Long.MAX_VALUE, Long.MAX_VALUE } };
    long[][] longarray3 = { { (long) 0, (long) 3 } };
    long[][] longarray4 = { { (long) 0, (long) -1, (long) 1 },
        { (long) Long.MIN_VALUE / 2, Long.MIN_VALUE } };
    assertEquals(0, compare(longarray1, longarray2, epsilon));
    assertEquals(-1, compare(longarray1, longarray3, epsilon));
    assertEquals(1, compare(longarray3, longarray1, epsilon));
    assertEquals(1, compare(longarray1, longarray4, epsilon));
    assertEquals(-1, compare(longarray4, longarray1, epsilon));

    float[][] floatarray1 = { { (float) 0, (float) -1, (float) 1 },
        { (float) Float.MIN_VALUE / 2, Float.MIN_VALUE },
        { (float) Float.MAX_VALUE, Float.MAX_VALUE } };
    float[][] floatarray2 = { { (float) 0, (float) -1, (float) 1 },
        { (float) Float.MIN_VALUE / 2, Float.MIN_VALUE },
        { (float) Float.MAX_VALUE, Float.MAX_VALUE } };
    float[][] floatarray3 = { { (float) 0, (float) 3 } };
    float[][] floatarray4 = { { (float) 0, (float) -1, (float) 1 },
        { (float) Float.MIN_VALUE / 2, Float.MIN_VALUE } };
    assertEquals(0, compare(floatarray1, floatarray2, epsilon));
    assertEquals(-1, compare(floatarray1, floatarray3, epsilon));
    assertEquals(1, compare(floatarray3, floatarray1, epsilon));
    assertEquals(1, compare(floatarray1, floatarray4, epsilon));
    assertEquals(-1, compare(floatarray4, floatarray1, epsilon));

    double[][] doublearray1 = { { (double) 0, (double) -1, (double) 1 },
        { (double) Double.MIN_VALUE / 2, Double.MIN_VALUE },
        { (double) Double.MAX_VALUE, Double.MAX_VALUE } };
    double[][] doublearray2 = { { (double) 0, (double) -1, (double) 1 },
        { (double) Double.MIN_VALUE / 2, Double.MIN_VALUE },
        { (double) Double.MAX_VALUE, Double.MAX_VALUE } };
    double[][] doublearray3 = { { (double) 0, (double) 3 } };
    double[][] doublearray4 = { { (double) 0, (double) -1, (double) 1 },
        { (double) Double.MIN_VALUE / 2, Double.MIN_VALUE } };
    assertEquals(0, compare(doublearray1, doublearray2, epsilon));
    assertEquals(-1, compare(doublearray1, doublearray3, epsilon));
    assertEquals(1, compare(doublearray3, doublearray1, epsilon));
    assertEquals(1, compare(doublearray1, doublearray4, epsilon));
    assertEquals(-1, compare(doublearray4, doublearray1, epsilon));

    String[][] stringarray1 = { { "long", "long" }, { "ago" } };
    String[][] stringarray2 = { { "long", "long" }, { "ago" } };
    String[][] stringarray3 = { { "long", "time" }, { "no", "see" } };
    String[][] stringarray4 = { { "long", "long" } };
    assertEquals(0, compare(stringarray1, stringarray2, epsilon));
    assertEquals(-8, compare(stringarray2, stringarray3, epsilon));
    assertEquals(8, compare(stringarray3, stringarray2, epsilon));
    assertEquals(1, compare(stringarray2, stringarray4, epsilon));
    assertEquals(-1, compare(stringarray4, stringarray2, epsilon));

    Boolean[][] booleanobjectarray1 = { { true, false }, { false, true } };
    Boolean[][] booleanobjectarray2 = { { true, false }, { false, true } };
    Boolean[][] booleanobjectarray3 = { { false, true }, { true } };
    Boolean[][] booleanobjectarray4 = { { true, false } };
    assertEquals(0, compare(booleanobjectarray1, booleanobjectarray2, epsilon));
    assertEquals(1, compare(booleanobjectarray2, booleanobjectarray3, epsilon));
    assertEquals(-1, compare(booleanobjectarray3, booleanobjectarray2, epsilon));
    assertEquals(1, compare(booleanobjectarray2, booleanobjectarray4, epsilon));
    assertEquals(-1, compare(booleanobjectarray4, booleanobjectarray2, epsilon));

    Character[][] charobjectarray1 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    Character[][] charobjectarray2 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    Character[][] charobjectarray3 = { { (char) 0, (char) 3 } };
    Character[][] charobjectarray4 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE } };
    assertEquals(0, compare(charobjectarray1, charobjectarray2, epsilon));
    assertEquals(2, compare(charobjectarray3, charobjectarray2, epsilon));
    assertEquals(-2, compare(charobjectarray2, charobjectarray3, epsilon));
    assertEquals(-1, compare(charobjectarray4, charobjectarray2, epsilon));
    assertEquals(1, compare(charobjectarray2, charobjectarray4, epsilon));

    Byte[][] byteobjectarray1 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    Byte[][] byteobjectarray2 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    Byte[][] byteobjectarray3 = { { (byte) 0, (byte) 2 } };
    Byte[][] byteobjectarray4 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) } };
    assertEquals(0, compare(byteobjectarray1, byteobjectarray2, epsilon));
    assertEquals(3, compare(byteobjectarray3, byteobjectarray2, epsilon));
    assertEquals(-3, compare(byteobjectarray2, byteobjectarray3, epsilon));
    assertEquals(-1, compare(byteobjectarray4, byteobjectarray2, epsilon));
    assertEquals(1, compare(byteobjectarray2, byteobjectarray4, epsilon));

    Short[][] shortobjectarray1 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
    Short[][] shortobjectarray2 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
    Short[][] shortobjectarray3 = { { (short) 0, (short) 2 } };
    Short[][] shortobjectarray4 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) } };
    assertEquals(0, compare(shortobjectarray1, shortobjectarray2, epsilon));
    assertEquals(3, compare(shortobjectarray3, shortobjectarray2, epsilon));
    assertEquals(-3, compare(shortobjectarray2, shortobjectarray3, epsilon));
    assertEquals(-1, compare(shortobjectarray4, shortobjectarray2, epsilon));
    assertEquals(1, compare(shortobjectarray2, shortobjectarray4, epsilon));

    Integer[][] integerarray1 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) },
        { (int) (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
    Integer[][] integerarray2 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) },
        { (int) (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
    Integer[][] integerarray3 = { { (int) 0, (int) 3 } };
    Integer[][] integerarray4 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) } };
    assertEquals(0, compare(integerarray1, integerarray2, epsilon));
    assertEquals(4, compare(integerarray3, integerarray2, epsilon));
    assertEquals(-4, compare(integerarray2, integerarray3, epsilon));
    assertEquals(-1, compare(integerarray4, integerarray2, epsilon));
    assertEquals(1, compare(integerarray2, integerarray4, epsilon));

    Long[][] longobjectarray1 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) },
        { (long) (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
    Long[][] longobjectarray2 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) },
        { (long) (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
    Long[][] longobjectarray3 = { { (long) 0, (long) 2 } };
    Long[][] longobjectarray4 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) } };
    assertEquals(0, compare(longobjectarray1, longobjectarray2, epsilon));
    assertEquals(1, compare(longobjectarray3, longobjectarray2, epsilon));
    assertEquals(-1, compare(longobjectarray2, longobjectarray3, epsilon));
    assertEquals(-1, compare(longobjectarray4, longobjectarray2, epsilon));
    assertEquals(1, compare(longobjectarray2, longobjectarray4, epsilon));

    Float[][] floatobjectarray1 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) },
        { (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
    Float[][] floatobjectarray2 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) },
        { (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
    Float[][] floatobjectarray3 = { { (float) 0, (float) 2 } };
    Float[][] floatobjectarray4 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) } };
    assertEquals(0, compare(floatobjectarray1, floatobjectarray2, epsilon));
    assertEquals(1, compare(floatobjectarray3, floatobjectarray2, epsilon));
    assertEquals(-1, compare(floatobjectarray2, floatobjectarray3, epsilon));
    assertEquals(-1, compare(floatobjectarray4, floatobjectarray2, epsilon));
    assertEquals(1, compare(floatobjectarray2, floatobjectarray4, epsilon));

    Double[][] doubleobjectarray1 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE },
        { (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
    Double[][] doubleobjectarray2 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE },
        { (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
    Double[][] doubleobjectarray3 = { { (double) 0, (double) 2 } };
    Double[][] doubleobjectarray4 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE } };
    assertEquals(0, compare(doubleobjectarray1, doubleobjectarray2, epsilon));
    assertEquals(1, compare(doubleobjectarray3, doubleobjectarray2, epsilon));
    assertEquals(-1, compare(doubleobjectarray2, doubleobjectarray3, epsilon));
    assertEquals(-1, compare(doubleobjectarray4, doubleobjectarray2, epsilon));
    assertEquals(1, compare(doubleobjectarray2, doubleobjectarray4, epsilon));

    Class<?>[][] classarray1 = { { TestClassA.class }, { TestClassA.class } };
    Class<?>[][] classarray2 = { { TestClassA.class }, { TestClassA.class } };
    Class<?>[][] classarray3 = { { TestClassB.class }, { TestClassB.class } };
    Class<?>[][] classarray4 = { { TestClassA.class }, { TestClassA.class },
        { TestClassA.class }};
    assertEquals(0, compare(classarray1, classarray2, epsilon));
    assertEquals(1, compare(classarray3, classarray2, epsilon));
    assertEquals(-1, compare(classarray2, classarray3, epsilon));
    assertEquals(1, compare(classarray4, classarray2, epsilon));
    assertEquals(-1, compare(classarray2, classarray4, epsilon));

    Date datea = new Date();
    datea.setTime((long) 123456);
    Date dateb = new Date();
    dateb.setTime((long) 7891011);
    Date datec = new Date();
    datec.setTime((long) 654321);
    Date[][] datearray1 = { {datea, dateb}, {datec} };
    Date[][] datearray2 = { {datea, dateb}, {datec} };
    Date[][] datearray3 = { {datec, datea}, {dateb} };
    Date[][] datearray4 = { {datea, dateb}};
    assertEquals(0, compare(datearray1, datearray2, epsilon));
    assertEquals(1, compare(datearray3, datearray2, epsilon));
    assertEquals(-1, compare(datearray2, datearray3, epsilon));
    assertEquals(-1, compare(datearray4, datearray2, epsilon));
    assertEquals(1, compare(datearray2, datearray4, epsilon));

    BigInteger[][] bigintegerarray1 = { {new BigInteger(bytearray1[0])},
        {new BigInteger(bytearray3[0]) }};
    BigInteger[][] bigintegerarray2 = { {new BigInteger(bytearray2[0])},
        {new BigInteger(bytearray3[0]) }};
    BigInteger[][] bigintegerarray3 = { {new BigInteger(bytearray3[0]) }};
    BigInteger[][] bigintegerarray4 = { {new BigInteger(bytearray2[0]) }};
    assertEquals(0, compare(bigintegerarray1, bigintegerarray2, epsilon));
    assertEquals(-1, compare(bigintegerarray3, bigintegerarray2, epsilon));
    assertEquals(1, compare(bigintegerarray2, bigintegerarray3, epsilon));
    assertEquals(-1, compare(bigintegerarray4, bigintegerarray2, epsilon));
    assertEquals(1, compare(bigintegerarray2, bigintegerarray4, epsilon));

    BigDecimal[][] bigdecimalarray1 = { {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE) }};
    BigDecimal[][] bigdecimalarray2 = { {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE) }};
    BigDecimal[][] bigdecimalarray3 = { {new BigDecimal(0), new BigDecimal(-1)}};
    BigDecimal[][] bigdecimalarray4 = { {new BigDecimal(Integer.MIN_VALUE) }};
    assertEquals(0, compare(bigdecimalarray1, bigdecimalarray2, epsilon));
    assertEquals(-1, compare(bigdecimalarray2, bigdecimalarray3, epsilon));
    assertEquals(1, compare(bigdecimalarray3, bigdecimalarray2, epsilon));
    assertEquals(1, compare(bigdecimalarray2, bigdecimalarray4, epsilon));
    assertEquals(-1, compare(bigdecimalarray4, bigdecimalarray2, epsilon));

    Boolean[][][] objectarray1 = { { {true}, {false} }, { {true, true }},
        { {false, false}, {true }} };
    Boolean[][][] objectarray2 = { { {true}, {false} }, { {true, true }},
        { {false, false}, {true }} };
    Boolean[][][] objectarray3 = { {{ true}, {false} }, { {false, true }} };
    Boolean[][][] objectarray4 = { { {true}, {false} }, { {true, true }} };
    assertEquals(0, compare(objectarray1, objectarray2, epsilon));
    assertEquals(-1, compare(objectarray3, objectarray2, epsilon));
    assertEquals(1, compare(objectarray2, objectarray3, epsilon));
    assertEquals(-1, compare(objectarray4, objectarray2, epsilon));
    assertEquals(1, compare(objectarray2, objectarray4, epsilon));

    ArrayList<Character> arraylist1 = new ArrayList<Character>();
    ArrayList<Character> arraylist2 = new ArrayList<Character>();
    Object[] col1 = { arraylist1, arraylist2 };
    arraylist1.add('a');
    arraylist2.add('b');
    arraylist1.add('c');
    ArrayList<Character> arraylist3 = new ArrayList<Character>();
    ArrayList<Character> arraylist4 = new ArrayList<Character>();
    Object[] col2 = { arraylist3, arraylist4 };
    arraylist3.add('a');
    arraylist4.add('b');
    arraylist3.add('c');
    ArrayList<Character> arraylist5 = new ArrayList<Character>();
    Object[] col3 = { arraylist5 };
    arraylist5.add('a');
    arraylist5.add('c');
    assertEquals(0, compare(col1, col2, epsilon));
    assertEquals(-1, compare(col3, col2, epsilon));
    assertEquals(1, compare(col2, col3, epsilon));
  }

  @Test
  public void testObjectArrayIntWithEpsilon() {
    double epsilon = 0.01;
    Object[] array1 = { new TestClassA(), new TestClassA() };
    Object[] array2 = { new TestClassA(), new TestClassA() };

    assertEquals(0, compare(array1, array1.length, array1, array1.length, epsilon));
    assertEquals(true, compare(array1, array1.length, array2, array2.length, epsilon) != 0);
    assertEquals(true, compare(array1, 1, array2, 1, epsilon) != 0);

    boolean[][] booleanarray1 = { { true, false }, { false, true } };
    boolean[][] booleanarray2 = { { true, false }, { false, true } };
    boolean[][] booleanarray3 = { { false, true }, { true } };
    boolean[][] booleanarray4 = { { true, false } };
    assertEquals(0, compare(booleanarray1, booleanarray1.length, 
        booleanarray2, booleanarray2.length, epsilon));
    assertEquals(1, compare(booleanarray1, booleanarray1.length, 
        booleanarray3, booleanarray3.length, epsilon));
    assertEquals(-1, compare(booleanarray3, booleanarray3.length, 
        booleanarray1 , booleanarray1.length, epsilon));
    assertEquals(1, compare(booleanarray1, booleanarray1.length, 
        booleanarray4, booleanarray4.length, epsilon));
    assertEquals(-1, compare(booleanarray4, booleanarray4.length, 
        booleanarray1, booleanarray1.length, epsilon));

    char[][] chararray1 = { { (char) 0, (char) 1 },
        { (char) Character.MIN_VALUE / 2, Character.MIN_VALUE },
        { (char) Character.MAX_VALUE, Character.MAX_VALUE } };
    char[][] chararray2 = { { (char) 0, (char) 1 },
        { (char) Character.MIN_VALUE / 2, Character.MIN_VALUE },
        { (char) Character.MAX_VALUE, Character.MAX_VALUE } };
    char[][] chararray3 = { { (char) 0, (char) 3 } };
    char[][] chararray4 = { { (char) 0, (char) 1 },
        { (char) Character.MIN_VALUE / 2, Character.MIN_VALUE } };
    assertEquals(0, compare(chararray1, chararray1.length, chararray2, 
        chararray2.length, epsilon));
    assertEquals(-2, compare(chararray1, chararray1.length, chararray3, 
        chararray3.length, epsilon));
    assertEquals(2, compare(chararray3, chararray3.length, chararray1, 
        chararray1.length, epsilon));
    assertEquals(1, compare(chararray1, chararray1.length, chararray4, 
        chararray4.length, epsilon));
    assertEquals(-1, compare(chararray4, chararray4.length, chararray1, 
        chararray1.length, epsilon));
    assertEquals(0, compare(chararray1, 1, chararray4, 1, epsilon));

    byte[][] bytearray1 = { { (byte) 0, (byte) -1, (byte) 1 },
        { (byte) Byte.MIN_VALUE / 2, Byte.MIN_VALUE },
        { (byte) Byte.MAX_VALUE, Byte.MAX_VALUE } };
    byte[][] bytearray2 = { { (byte) 0, (byte) -1, (byte) 1 },
        { (byte) Byte.MIN_VALUE / 2, Byte.MIN_VALUE },
        { (byte) Byte.MAX_VALUE, Byte.MAX_VALUE } };
    byte[][] bytearray3 = { { (byte) 0, (byte) 3 } };
    byte[][] bytearray4 = { { (byte) 0, (byte) -1, (byte) 1 },
        { (byte) Byte.MIN_VALUE / 2, Byte.MIN_VALUE } };
    assertEquals(0, compare(bytearray1, bytearray1.length, bytearray2, bytearray2.length, epsilon));
    assertEquals(-4, compare(bytearray1, bytearray1.length, bytearray3, bytearray3.length, epsilon));
    assertEquals(4, compare(bytearray3, bytearray3.length, bytearray1, bytearray1.length, epsilon));
    assertEquals(1, compare(bytearray1, bytearray1.length, bytearray4, bytearray4.length, epsilon));
    assertEquals(-1, compare(bytearray4, bytearray4.length, bytearray1, bytearray1.length, epsilon));
    assertEquals(0, compare(bytearray1, 1, bytearray4, 1, epsilon));

    short[][] shortarray1 = { { (short) 0, (short) -1, (short) 1 },
        { (short) Short.MIN_VALUE / 2, Short.MIN_VALUE },
        { (short) Short.MAX_VALUE, Short.MAX_VALUE } };
    short[][] shortarray2 = { { (short) 0, (short) -1, (short) 1 },
        { (short) Short.MIN_VALUE / 2, Short.MIN_VALUE },
        { (short) Short.MAX_VALUE, Short.MAX_VALUE } };
    short[][] shortarray3 = { { (short) 0, (short) 3 } };
    short[][] shortarray4 = { { (short) 0, (short) -1, (short) 1 },
        { (short) Short.MIN_VALUE / 2, Short.MIN_VALUE } };
    assertEquals(0, compare(shortarray1, shortarray1.length, shortarray2, shortarray2.length, epsilon));
    assertEquals(-4, compare(shortarray1, shortarray1.length, shortarray3, shortarray3.length, epsilon));
    assertEquals(4, compare(shortarray3, shortarray3.length, shortarray1, shortarray1.length, epsilon));
    assertEquals(1, compare(shortarray1, shortarray1.length, shortarray4, shortarray4.length, epsilon));
    assertEquals(-1, compare(shortarray4, shortarray4.length, shortarray1, shortarray1.length, epsilon));
    assertEquals(0, compare(shortarray1, 1, shortarray4, 1, epsilon));

    int[][] intarray1 = { { (int) 0, (int) -1, (int) 1 },
        { (int) Integer.MIN_VALUE / 2, Integer.MIN_VALUE },
        { (int) Integer.MAX_VALUE, Integer.MAX_VALUE } };
    int[][] intarray2 = { { (int) 0, (int) -1, (int) 1 },
        { (int) Integer.MIN_VALUE / 2, Integer.MIN_VALUE },
        { (int) Integer.MAX_VALUE, Integer.MAX_VALUE } };
    int[][] intarray3 = { { (int) 0, (int) 3 } };
    int[][] intarray4 = { { (int) 0, (int) -1, (int) 1 },
        { (int) Integer.MIN_VALUE / 2, Integer.MIN_VALUE } };
    assertEquals(0, compare(intarray1, intarray1.length, intarray2, intarray2.length, epsilon));
    assertEquals(-4, compare(intarray1, intarray1.length, intarray3, intarray3.length, epsilon));
    assertEquals(4, compare(intarray3, intarray3.length, intarray1, intarray1.length, epsilon));
    assertEquals(1, compare(intarray1, intarray1.length, intarray4, intarray4.length, epsilon));
    assertEquals(-1, compare(intarray4, intarray4.length, intarray1, intarray1.length, epsilon));
    assertEquals(0, compare(intarray1, 1, intarray4, 1, epsilon));

    long[][] longarray1 = { { (long) 0, (long) -1, (long) 1 },
        { (long) Long.MIN_VALUE / 2, Long.MIN_VALUE },
        { (long) Long.MAX_VALUE, Long.MAX_VALUE } };
    long[][] longarray2 = { { (long) 0, (long) -1, (long) 1 },
        { (long) Long.MIN_VALUE / 2, Long.MIN_VALUE },
        { (long) Long.MAX_VALUE, Long.MAX_VALUE } };
    long[][] longarray3 = { { (long) 0, (long) 3 } };
    long[][] longarray4 = { { (long) 0, (long) -1, (long) 1 },
        { (long) Long.MIN_VALUE / 2, Long.MIN_VALUE } };
    assertEquals(0, compare(longarray1, longarray1.length, longarray2, longarray2.length, epsilon));
    assertEquals(-1, compare(longarray1, longarray1.length, longarray3, longarray3.length, epsilon));
    assertEquals(1, compare(longarray3, longarray3.length, longarray1, longarray1.length, epsilon));
    assertEquals(1, compare(longarray1, longarray1.length, longarray4, longarray4.length, epsilon));
    assertEquals(-1, compare(longarray4, longarray4.length, longarray1, longarray1.length, epsilon));
    assertEquals(0, compare(longarray1, 1, longarray4, 1, epsilon));

    float[][] floatarray1 = { { (float) 0, (float) -1, (float) 1 },
        { (float) Float.MIN_VALUE / 2, Float.MIN_VALUE },
        { (float) Float.MAX_VALUE, Float.MAX_VALUE } };
    float[][] floatarray2 = { { (float) 0, (float) -1, (float) 1 },
        { (float) Float.MIN_VALUE / 2, Float.MIN_VALUE },
        { (float) Float.MAX_VALUE, Float.MAX_VALUE } };
    float[][] floatarray3 = { { (float) 0, (float) 3 } };
    float[][] floatarray4 = { { (float) 0, (float) -1, (float) 1 },
        { (float) Float.MIN_VALUE / 2, Float.MIN_VALUE } };
    assertEquals(0, compare(floatarray1, floatarray1.length, floatarray2, floatarray2.length, epsilon));
    assertEquals(-1, compare(floatarray1, floatarray1.length, floatarray3, floatarray3.length, epsilon));
    assertEquals(1, compare(floatarray3, floatarray3.length, floatarray1, floatarray1.length, epsilon));
    assertEquals(1, compare(floatarray1, floatarray1.length, floatarray4, floatarray4.length, epsilon));
    assertEquals(-1, compare(floatarray4, floatarray4.length, floatarray1, floatarray1.length, epsilon));
    assertEquals(0, compare(floatarray1, 1, floatarray4, 1, epsilon));

    double[][] doublearray1 = { { (double) 0, (double) -1, (double) 1 },
        { (double) Double.MIN_VALUE / 2, Double.MIN_VALUE },
        { (double) Double.MAX_VALUE, Double.MAX_VALUE } };
    double[][] doublearray2 = { { (double) 0, (double) -1, (double) 1 },
        { (double) Double.MIN_VALUE / 2, Double.MIN_VALUE },
        { (double) Double.MAX_VALUE, Double.MAX_VALUE } };
    double[][] doublearray3 = { { (double) 0, (double) 3 } };
    double[][] doublearray4 = { { (double) 0, (double) -1, (double) 1 },
        { (double) Double.MIN_VALUE / 2, Double.MIN_VALUE } };
    assertEquals(0, compare(doublearray1, doublearray1.length, doublearray2, doublearray2.length, epsilon));
    assertEquals(-1, compare(doublearray1, doublearray1.length, doublearray3, doublearray3.length, epsilon));
    assertEquals(1, compare(doublearray3, doublearray3.length, doublearray1, doublearray1.length, epsilon));
    assertEquals(1, compare(doublearray1, doublearray1.length, doublearray4, doublearray4.length, epsilon));
    assertEquals(-1, compare(doublearray4, doublearray4.length, doublearray1, doublearray1.length, epsilon));
    assertEquals(0, compare(doublearray1, 1, doublearray4, 1, epsilon));

    String[][] stringarray1 = { { "long", "long" }, { "ago" } };
    String[][] stringarray2 = { { "long", "long" }, { "ago" } };
    String[][] stringarray3 = { { "long", "time" }, { "no", "see" } };
    String[][] stringarray4 = { { "long", "long" } };
    assertEquals(0, compare(stringarray1, stringarray1.length, stringarray2, 
        stringarray2.length, epsilon));
    assertEquals(-8, compare(stringarray2, stringarray2.length, stringarray3, 
        stringarray3.length, epsilon));
    assertEquals(8, compare(stringarray3, stringarray3.length, stringarray2, 
        stringarray2.length, epsilon));
    assertEquals(1, compare(stringarray2, stringarray2.length, stringarray4, 
        stringarray4.length, epsilon));
    assertEquals(-1, compare(stringarray4, stringarray4.length, stringarray2, 
        stringarray2.length, epsilon));
    assertEquals(0, compare(stringarray1, 1, stringarray4, 1, epsilon));

    Boolean[][] booleanobjectarray1 = { { true, false }, { false, true } };
    Boolean[][] booleanobjectarray2 = { { true, false }, { false, true } };
    Boolean[][] booleanobjectarray3 = { { false, true }, { true } };
    Boolean[][] booleanobjectarray4 = { { true, false } };
    assertEquals(0, compare(booleanobjectarray1, booleanobjectarray1.length, 
        booleanobjectarray2, booleanobjectarray2.length, epsilon));
    assertEquals(1, compare(booleanobjectarray2, booleanobjectarray2.length, 
        booleanobjectarray3, booleanobjectarray3.length, epsilon));
    assertEquals(-1, compare(booleanobjectarray3, booleanobjectarray3.length, 
        booleanobjectarray2, booleanobjectarray2.length, epsilon));
    assertEquals(1, compare(booleanobjectarray2, booleanobjectarray2.length, 
        booleanobjectarray4, booleanobjectarray4.length, epsilon));
    assertEquals(-1, compare(booleanobjectarray4, booleanobjectarray4.length, 
        booleanobjectarray2, booleanobjectarray2.length, epsilon));
    assertEquals(0, compare(booleanobjectarray1, 1, booleanobjectarray4, 1, epsilon));

    Character[][] charobjectarray1 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    Character[][] charobjectarray2 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    Character[][] charobjectarray3 = { { (char) 0, (char) 3 } };
    Character[][] charobjectarray4 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE } };
    assertEquals(0, compare(charobjectarray1, charobjectarray1.length, 
        charobjectarray2, charobjectarray2.length, epsilon));
    assertEquals(2, compare(charobjectarray3, charobjectarray3.length, 
        charobjectarray2, charobjectarray2.length, epsilon));
    assertEquals(-2, compare(charobjectarray2, charobjectarray2.length, 
        charobjectarray3, charobjectarray3.length, epsilon));
    assertEquals(-1, compare(charobjectarray4, charobjectarray4.length, 
        charobjectarray2, charobjectarray2.length, epsilon));
    assertEquals(1, compare(charobjectarray2, charobjectarray2.length, 
        charobjectarray4, charobjectarray4.length, epsilon));
    assertEquals(0, compare(charobjectarray1, 1, charobjectarray4, 1, epsilon));

    Byte[][] byteobjectarray1 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    Byte[][] byteobjectarray2 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    Byte[][] byteobjectarray3 = { { (byte) 0, (byte) 2 } };
    Byte[][] byteobjectarray4 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) } };
    assertEquals(0, compare(byteobjectarray1, byteobjectarray1.length,
        byteobjectarray2, byteobjectarray2.length, epsilon));
    assertEquals(3, compare(byteobjectarray3, byteobjectarray3.length, 
        byteobjectarray2, byteobjectarray2.length, epsilon));
    assertEquals(-3, compare(byteobjectarray2, byteobjectarray2.length, 
        byteobjectarray3, byteobjectarray3.length, epsilon));
    assertEquals(-1, compare(byteobjectarray4, byteobjectarray4.length, 
        byteobjectarray2, byteobjectarray2.length, epsilon));
    assertEquals(1, compare(byteobjectarray2, byteobjectarray2.length, 
        byteobjectarray4, byteobjectarray4.length, epsilon));
    assertEquals(0, compare(byteobjectarray1, 1, byteobjectarray4, 1, epsilon));

    Short[][] shortobjectarray1 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
    Short[][] shortobjectarray2 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
    Short[][] shortobjectarray3 = { { (short) 0, (short) 2 } };
    Short[][] shortobjectarray4 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) } };
    assertEquals(0, compare(shortobjectarray1, shortobjectarray1.length, 
        shortobjectarray2, shortobjectarray2.length, epsilon));
    assertEquals(3, compare(shortobjectarray3, shortobjectarray3.length, 
        shortobjectarray2, shortobjectarray2.length, epsilon));
    assertEquals(-3, compare(shortobjectarray2, shortobjectarray2.length, 
        shortobjectarray3, shortobjectarray3.length, epsilon));
    assertEquals(-1, compare(shortobjectarray4, shortobjectarray4.length,
        shortobjectarray2, shortobjectarray2.length, epsilon));
    assertEquals(1, compare(shortobjectarray2, shortobjectarray2.length, 
        shortobjectarray4, shortobjectarray4.length, epsilon));
    assertEquals(0, compare(shortobjectarray1, 1, shortobjectarray4, 1, epsilon));

    Integer[][] integerarray1 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) },
        { (int) (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
    Integer[][] integerarray2 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) },
        { (int) (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
    Integer[][] integerarray3 = { { (int) 0, (int) 3 } };
    Integer[][] integerarray4 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) } };
    assertEquals(0, compare(integerarray1, integerarray1.length, 
        integerarray2, integerarray2.length, epsilon));
    assertEquals(4, compare(integerarray3, integerarray3.length, 
        integerarray2, integerarray2.length, epsilon));
    assertEquals(-4, compare(integerarray2, integerarray2.length, 
        integerarray3, integerarray3.length, epsilon));
    assertEquals(-1, compare(integerarray4, integerarray4.length, 
        integerarray2, integerarray2.length, epsilon));
    assertEquals(1, compare(integerarray2, integerarray2.length, 
        integerarray4, integerarray4.length, epsilon));
    assertEquals(0, compare(integerarray1, 1, integerarray4, 1, epsilon));

    Long[][] longobjectarray1 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) },
        { (long) (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
    Long[][] longobjectarray2 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) },
        { (long) (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
    Long[][] longobjectarray3 = { { (long) 0, (long) 2 } };
    Long[][] longobjectarray4 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) } };
    assertEquals(0, compare(longobjectarray1, longobjectarray1.length, 
        longobjectarray2, longobjectarray2.length, epsilon));
    assertEquals(1, compare(longobjectarray3, longobjectarray3.length, 
        longobjectarray2, longobjectarray2.length, epsilon));
    assertEquals(-1, compare(longobjectarray2, longobjectarray2.length, 
        longobjectarray3, longobjectarray3.length, epsilon));
    assertEquals(-1, compare(longobjectarray4, longobjectarray4.length, 
        longobjectarray2, longobjectarray2.length, epsilon));
    assertEquals(1, compare(longobjectarray2, longobjectarray2.length, 
        longobjectarray4, longobjectarray4.length, epsilon));
    assertEquals(0, compare(longobjectarray1, 1, longobjectarray4, 1, epsilon));

    Float[][] floatobjectarray1 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) },
        { (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
    Float[][] floatobjectarray2 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) },
        { (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
    Float[][] floatobjectarray3 = { { (float) 0, (float) 2 } };
    Float[][] floatobjectarray4 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) } };
    assertEquals(0, compare(floatobjectarray1, floatobjectarray1.length, 
        floatobjectarray2, floatobjectarray2.length, epsilon));
    assertEquals(1, compare(floatobjectarray3, floatobjectarray3.length, 
        floatobjectarray2, floatobjectarray2.length, epsilon));
    assertEquals(-1, compare(floatobjectarray2, floatobjectarray2.length, 
        floatobjectarray3, floatobjectarray3.length, epsilon));
    assertEquals(-1, compare(floatobjectarray4, floatobjectarray4.length, 
        floatobjectarray2, floatobjectarray2.length, epsilon));
    assertEquals(1, compare(floatobjectarray2, floatobjectarray2.length, 
        floatobjectarray4, floatobjectarray4.length, epsilon));
    assertEquals(0, compare(floatobjectarray1, 1, floatobjectarray4, 1, epsilon));

    Double[][] doubleobjectarray1 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE },
        { (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
    Double[][] doubleobjectarray2 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE },
        { (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
    Double[][] doubleobjectarray3 = { { (double) 0, (double) 2 } };
    Double[][] doubleobjectarray4 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE } };
    assertEquals(0, compare(doubleobjectarray1, doubleobjectarray1.length, 
        doubleobjectarray2, doubleobjectarray2.length, epsilon));
    assertEquals(1, compare(doubleobjectarray3, doubleobjectarray3.length, 
        doubleobjectarray2, doubleobjectarray2.length, epsilon));
    assertEquals(-1, compare(doubleobjectarray2, doubleobjectarray2.length, 
        doubleobjectarray3, doubleobjectarray3.length, epsilon));
    assertEquals(-1, compare(doubleobjectarray4, doubleobjectarray4.length, 
        doubleobjectarray2, doubleobjectarray2.length, epsilon));
    assertEquals(1, compare(doubleobjectarray2, doubleobjectarray2.length, 
        doubleobjectarray4, doubleobjectarray4.length, epsilon));
    assertEquals(0, compare(doubleobjectarray1, 1, doubleobjectarray4, 1, epsilon));

    Class<?>[][] classarray1 = { { TestClassA.class }, { TestClassA.class } };
    Class<?>[][] classarray2 = { { TestClassA.class }, { TestClassA.class } };
    Class<?>[][] classarray3 = { { TestClassB.class }, { TestClassB.class } };
    Class<?>[][] classarray4 = { { TestClassA.class }, { TestClassA.class },
        { TestClassA.class }};
    assertEquals(0, compare(classarray1, classarray1.length, classarray2, 
        classarray2.length, epsilon));
    assertEquals(1, compare(classarray3, classarray3.length, classarray2, 
        classarray2.length, epsilon));
    assertEquals(-1, compare(classarray2, classarray2.length, classarray3,
        classarray3.length, epsilon));
    assertEquals(1, compare(classarray4, classarray4.length, classarray2, 
        classarray2.length, epsilon));
    assertEquals(-1, compare(classarray2, classarray2.length, classarray4,
        classarray4.length, epsilon));
    assertEquals(0, compare(classarray1, 1, classarray4, 1, epsilon));

    Date datea = new Date();
    datea.setTime((long) 123456);
    Date dateb = new Date();
    dateb.setTime((long) 7891011);
    Date datec = new Date();
    datec.setTime((long) 654321);
    Date[][] datearray1 = { {datea, dateb}, {datec} };
    Date[][] datearray2 = { {datea, dateb}, {datec} };
    Date[][] datearray3 = { {datec, datea}, {dateb} };
    Date[][] datearray4 = { {datea, dateb}};
    assertEquals(0, compare(datearray1, datearray1.length, datearray2, 
        datearray2.length, epsilon));
    assertEquals(1, compare(datearray3, datearray3.length, datearray2, 
        datearray2.length, epsilon));
    assertEquals(-1, compare(datearray2, datearray2.length, datearray3, 
        datearray3.length, epsilon));
    assertEquals(-1, compare(datearray4, datearray4.length, datearray2, 
        datearray2.length, epsilon));
    assertEquals(1, compare(datearray2, datearray2.length, datearray4,
        datearray4.length, epsilon));
    assertEquals(0, compare(datearray1, 1, datearray4, 1, epsilon));

    BigInteger[][] bigintegerarray1 = { {new BigInteger(bytearray1[0])},
        {new BigInteger(bytearray3[0]) }};
    BigInteger[][] bigintegerarray2 = { {new BigInteger(bytearray2[0])},
        {new BigInteger(bytearray3[0]) }};
    BigInteger[][] bigintegerarray3 = { {new BigInteger(bytearray3[0]) }};
    BigInteger[][] bigintegerarray4 = { {new BigInteger(bytearray2[0]) }};
    assertEquals(0, compare(bigintegerarray1, bigintegerarray1.length, 
        bigintegerarray2, bigintegerarray2.length, epsilon));
    assertEquals(-1, compare(bigintegerarray3, bigintegerarray3.length, 
        bigintegerarray2, bigintegerarray2.length, epsilon));
    assertEquals(1, compare(bigintegerarray2, bigintegerarray2.length, 
        bigintegerarray3, bigintegerarray3.length, epsilon));
    assertEquals(-1, compare(bigintegerarray4, bigintegerarray4.length, 
        bigintegerarray2, bigintegerarray2.length, epsilon));
    assertEquals(1, compare(bigintegerarray2, bigintegerarray2.length, 
        bigintegerarray4, bigintegerarray4.length, epsilon));
    assertEquals(0, compare(bigintegerarray1, 1, bigintegerarray4, 1, epsilon));

    BigDecimal[][] bigdecimalarray1 = { {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE) }};
    BigDecimal[][] bigdecimalarray2 = { {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE) }};
    BigDecimal[][] bigdecimalarray3 = { {new BigDecimal(0), new BigDecimal(-1)}};
    BigDecimal[][] bigdecimalarray4 = { {new BigDecimal(Integer.MIN_VALUE) }};
    assertEquals(0, compare(bigdecimalarray1, bigdecimalarray1.length, 
        bigdecimalarray2, bigdecimalarray2.length, epsilon));
    assertEquals(-1, compare(bigdecimalarray2, bigdecimalarray2.length, 
        bigdecimalarray3, bigdecimalarray3.length, epsilon));
    assertEquals(1, compare(bigdecimalarray3, bigdecimalarray3.length, 
        bigdecimalarray2, bigdecimalarray2.length, epsilon));
    assertEquals(1, compare(bigdecimalarray2, bigdecimalarray2.length, 
        bigdecimalarray4, bigdecimalarray4.length, epsilon));
    assertEquals(-1, compare(bigdecimalarray4, bigdecimalarray4.length, 
        bigdecimalarray2, bigdecimalarray2.length, epsilon));
    assertEquals(0, compare(bigdecimalarray1, 1, bigdecimalarray4, 1, epsilon));

    Boolean[][][] objectarray1 = { { {true}, {false} }, { {true, true }},
        { {false, false}, {true }} };
    Boolean[][][] objectarray2 = { { {true}, {false} }, { {true, true }},
        { {false, false}, {true }} };
    Boolean[][][] objectarray3 = { {{ true}, {false} }, { {false, true }} };
    Boolean[][][] objectarray4 = { { {true}, {false} }, { {true, true }} };
    assertEquals(0, compare(objectarray1, objectarray1.length, objectarray2, 
        objectarray2.length, epsilon));
    assertEquals(-1, compare(objectarray3, objectarray3.length, objectarray2, 
        objectarray2.length, epsilon));
    assertEquals(1, compare(objectarray2, objectarray2.length, objectarray3, 
        objectarray3.length, epsilon));
    assertEquals(-1, compare(objectarray4, objectarray4.length, objectarray2, 
        objectarray2.length, epsilon));
    assertEquals(1, compare(objectarray2, objectarray2.length, objectarray4, 
        objectarray4.length, epsilon));
    assertEquals(0, compare(objectarray1, 1, objectarray4, 1, epsilon));

    ArrayList<Character> arraylist1 = new ArrayList<Character>();
    ArrayList<Character> arraylist2 = new ArrayList<Character>();
    Object[] col1 = { arraylist1, arraylist2 };
    arraylist1.add('a');
    arraylist2.add('b');
    arraylist1.add('c');
    ArrayList<Character> arraylist3 = new ArrayList<Character>();
    ArrayList<Character> arraylist4 = new ArrayList<Character>();
    Object[] col2 = { arraylist3, arraylist4 };
    arraylist3.add('a');
    arraylist4.add('b');
    arraylist3.add('c');
    ArrayList<Character> arraylist5 = new ArrayList<Character>();
    Object[] col3 = { arraylist5 };
    arraylist5.add('a');
    arraylist5.add('c');
    assertEquals(0, compare(col1, col2, epsilon));
    assertEquals(-1, compare(col3, col2, epsilon));
    assertEquals(1, compare(col2, col3, epsilon));
  }

  static class StringComparator implements Comparator<String> {
    public int compare(String o1, String o2) {
      if (o1 instanceof String) {
        return ((String) o1).compareTo((String) o2);
      } else {
        return o1.toString().compareTo(o2.toString()); 
      }
    }    
  }
  
  @Test
  public void testObjectComparator() {
    final StringComparator comparator = new StringComparator();
    
    String value1 = "alongtime";
    String value2 = "alonglongago";

    assertEquals(0, compare(value1, value1, comparator));
    assertEquals(true, compare(value1, value2, comparator) != 0);
    value2 = "along";
    assertEquals(4, compare(value1, value2, comparator));
    assertEquals(-4, compare(value2, value1, comparator));
    value2 = "zoo";
    assertEquals(-25, compare(value1, value2, comparator));
    assertEquals(25, compare(value2, value1, comparator));
    Object value3 = TestClassA.class;
    Object value4 = Class.class;
    assertEquals(-7, compare(value3, value4, comparator));
    assertEquals(7, compare(value4, value3, comparator));
    value2 = null;
    assertEquals(1, compare(value1, value2, comparator));
    assertEquals(-1, compare(value2, value1, comparator));
    value1 = null;
    assertEquals(0, compare(value1, value2, comparator));

    boolean[] booleanarray1 = { true, false, false, true };
    boolean[] booleanarray2 = { true, false, false, true };
    boolean[] booleanarray3 = { false, true, true };
    boolean[] booleanarray4 = { true, false };
    assertEquals(0, compare(booleanarray1, (Object) booleanarray2, comparator));
    assertEquals(1, compare(booleanarray2, (Object) booleanarray3, comparator));
    assertEquals(-1, compare(booleanarray3, (Object) booleanarray2, comparator));
    assertEquals(2, compare(booleanarray2, (Object) booleanarray4, comparator));
    assertEquals(-2, compare(booleanarray4, (Object) booleanarray2, comparator));

    char[] chararray1 = { (char) 0, (char) 1, (char) (Character.MIN_VALUE / 2),
        Character.MIN_VALUE, (char) (Character.MAX_VALUE / 2),
        Character.MAX_VALUE };
    char[] chararray2 = { (char) 0, (char) 1, (char) (Character.MIN_VALUE / 2),
        Character.MIN_VALUE, (char) (Character.MAX_VALUE / 2),
        Character.MAX_VALUE };
    char[] chararray3 = { (char) 0, (char) 3 };
    char[] chararray4 = { (char) 0, (char) 1, (char) (Character.MIN_VALUE / 2),
        Character.MIN_VALUE };

    assertEquals(0, compare(chararray1, (Object) chararray2, comparator));
    assertEquals(2, compare(chararray3, (Object) chararray2, comparator));
    assertEquals(-2, compare(chararray2, (Object) chararray3, comparator));
    assertEquals(-2, compare(chararray4, (Object) chararray2, comparator));
    assertEquals(2, compare(chararray2, (Object) chararray4, comparator));

    byte[] bytearray1 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE };
    byte[] bytearray2 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE };
    byte[] bytearray3 = { (byte) 0, (byte) 2 };
    byte[] bytearray4 = { (byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2) };
    assertEquals(0, compare(bytearray1, (Object) bytearray2, comparator));
    assertEquals(3, compare(bytearray3, (Object) bytearray2, comparator));
    assertEquals(-3, compare(bytearray2, (Object) bytearray3, comparator));
    assertEquals(2, compare(bytearray2, (Object) bytearray4, comparator));
    assertEquals(-2, compare(bytearray4, (Object) bytearray2, comparator));

    short[] shortarray1 = { (short) 0, (short) -1, (short) 1, Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2), Short.MIN_VALUE,
        (short) (Short.MAX_VALUE / 2) };
    short[] shortarray2 = { (short) 0, (short) -1, (short) 1, Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2), Short.MIN_VALUE,
        (short) (Short.MAX_VALUE / 2) };
    short[] shortarray3 = { (short) 0, (short) 3 };
    short[] shortarray4 = { (short) 0, (short) -1, (short) 1, Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2) };
    assertEquals(0, compare(shortarray1, (Object) shortarray2, comparator));
    assertEquals(4, compare(shortarray3, (Object) shortarray2, comparator));
    assertEquals(-4, compare(shortarray2, (Object) shortarray3, comparator));
    assertEquals(2, compare(shortarray2, (Object) shortarray4, comparator));
    assertEquals(-2, compare(shortarray4, (Object) shortarray2, comparator));

    int[] intarray1 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE };
    int[] intarray2 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE };
    int[] intarray3 = { (int) 0, (int) 4 };
    int[] intarray4 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2) };
    assertEquals(0, compare(intarray1, (Object) intarray2, comparator));
    assertEquals(5, compare(intarray3, (Object) intarray2, comparator));
    assertEquals(-5, compare(intarray2, (Object) intarray3, comparator));
    assertEquals(2, compare(intarray2, (Object) intarray4, comparator));
    assertEquals(-2, compare(intarray4, (Object) intarray2, comparator));

    long[] longarray1 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MAX_VALUE };
    long[] longarray2 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MAX_VALUE };
    long[] longarray3 = { (long) 0, (long) 2 };
    long[] longarray4 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        (long) (Long.MIN_VALUE / 2) };
    assertEquals(0, compare(longarray1, (Object) longarray2, comparator));
    assertEquals(1, compare(longarray3, (Object) longarray2, comparator));
    assertEquals(-1, compare(longarray2, (Object) longarray3, comparator));
    assertEquals(-2, compare(longarray4, (Object) longarray2, comparator));
    assertEquals(2, compare(longarray2, (Object) longarray4, comparator));

    float[] floatarray1 = { (float) 0, (float) -1, (float) 1, Float.MIN_VALUE,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MAX_VALUE };
    float[] floatarray2 = { (float) 0, (float) -1, (float) 1, Float.MIN_VALUE,
        (float) (Float.MIN_VALUE / 2), (float) (Float.MAX_VALUE / 2),
        Float.MAX_VALUE };
    float[] floatarray3 = { (float) 0, (float) 2 };
    float[] floatarray4 = { (float) 0, (float) -1, (float) 1, Float.MIN_VALUE,
        (float) (Float.MIN_VALUE / 2) };
    assertEquals(0, compare(floatarray1, (Object) floatarray2, comparator));
    assertEquals(-1, compare(floatarray2, (Object) floatarray3, comparator));
    assertEquals(1, compare(floatarray3, (Object) floatarray2, comparator));
    assertEquals(2, compare(floatarray2, (Object) floatarray4, comparator));
    assertEquals(-2, compare(floatarray4, (Object) floatarray2, comparator));

    double[] doublearray1 = { (double) 0, (double) -1, (double) 1,
        Double.MIN_VALUE, (double) (Double.MIN_VALUE / 2),
        (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE };
    double[] doublearray2 = { (double) 0, (double) -1, (double) 1,
        Double.MIN_VALUE, (double) (Double.MIN_VALUE / 2),
        (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE };
    double[] doublearray3 = { (double) 0, Double.MAX_VALUE };
    double[] doublearray4 = { (double) 0, (double) -1, (double) 1,
        Double.MIN_VALUE, (double) (Double.MIN_VALUE / 2) };
    assertEquals(0, compare(doublearray1, (Object) doublearray2, comparator));
    assertEquals(1, compare(doublearray3, (Object) doublearray2, comparator));
    assertEquals(-1, compare(doublearray2, (Object) doublearray3, comparator));
    assertEquals(-2, compare(doublearray4, (Object) doublearray2, comparator));
    assertEquals(2, compare(doublearray2, (Object) doublearray4, comparator));

    String[] stringarray1 = { "long", "long", "ago" };
    String[] stringarray2 = { "long", "long", "ago" };
    String[] stringarray3 = { "long", "time", "no", "see" };
    String[] stringarray4 = { "long", "long" };
    assertEquals(0, compare(stringarray1, (Object) stringarray2, comparator));
    assertEquals(-8, compare(stringarray2, (Object) stringarray3, comparator));
    assertEquals(8, compare(stringarray3, (Object) stringarray2, comparator));
    assertEquals(1, compare(stringarray2, (Object) stringarray4, comparator));
    assertEquals(-1, compare(stringarray4, (Object) stringarray2, comparator));

    Boolean[] booleanobjectarray1 = { true, false, false, true };
    Boolean[] booleanobjectarray2 = { true, false, false, true };
    Boolean[] booleanobjectarray3 = { false, true, true };
    Boolean[] booleanobjectarray4 = { true, false };
    assertEquals(0, compare(booleanobjectarray1, (Object) booleanobjectarray2, comparator));
    assertEquals(1, compare(booleanobjectarray2, (Object) booleanobjectarray3, comparator));
    assertEquals(-1, compare(booleanobjectarray3, (Object) booleanobjectarray2, comparator));
    assertEquals(2, compare(booleanobjectarray2, (Object) booleanobjectarray4, comparator));
    assertEquals(-2, compare(booleanobjectarray4, (Object) booleanobjectarray2, comparator));

    Character[] charobjectarray1 = { (char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE };
    Character[] charobjectarray2 = { (char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE };
    Character[] charobjectarray3 = { (char) 0, (char) 3 };
    Character[] charobjectarray4 = { (char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE };
    assertEquals(0, compare(charobjectarray1, (Object) charobjectarray2, comparator));
    assertEquals(2, compare(charobjectarray3, (Object) charobjectarray2, comparator));
    assertEquals(-2, compare(charobjectarray2, (Object) charobjectarray3, comparator));
    assertEquals(-2, compare(charobjectarray4, (Object) charobjectarray2, comparator));
    assertEquals(2, compare(charobjectarray2, (Object) charobjectarray4, comparator));

    Short[] shortobjectarray1 = { (short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2),
        (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE };
    Short[] shortobjectarray2 = { (short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2),
        (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE };
    Short[] shortobjectarray3 = { (short) 0, (short) 2 };
    Short[] shortobjectarray4 = { (short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) };
    assertEquals(0, compare(shortobjectarray1, (Object) shortobjectarray2, comparator));
    assertEquals(3, compare(shortobjectarray3, (Object) shortobjectarray2, comparator));
    assertEquals(-3, compare(shortobjectarray2, (Object) shortobjectarray3, comparator));
    assertEquals(-2, compare(shortobjectarray4, (Object) shortobjectarray2, comparator));
    assertEquals(2, compare(shortobjectarray2, (Object) shortobjectarray4, comparator));

    Integer[] integerarray1 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE };
    Integer[] integerarray2 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2), (int) (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE };
    Integer[] integerarray3 = { (int) 0, (int) 3 };
    Integer[] integerarray4 = { (int) 0, (int) -1, (int) 1, Integer.MIN_VALUE,
        (int) (Integer.MIN_VALUE / 2) };
    assertEquals(0, compare(integerarray1, (Object) integerarray2, comparator));
    assertEquals(4, compare(integerarray3, (Object) integerarray2, comparator));
    assertEquals(-4, compare(integerarray2, (Object) integerarray3, comparator));
    assertEquals(-2, compare(integerarray4, (Object) integerarray2, comparator));
    assertEquals(2, compare(integerarray2, (Object) integerarray4, comparator));

    Long[] longobjectarray1 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MAX_VALUE };
    Long[] longobjectarray2 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        (long) (Long.MIN_VALUE / 2), (long) (Long.MAX_VALUE / 2),
        Long.MAX_VALUE };
    Long[] longobjectarray3 = { (long) 0, (long) 2 };
    Long[] longobjectarray4 = { (long) 0, (long) -1, (long) 1, Long.MIN_VALUE };
    assertEquals(0, compare(longobjectarray1, (Object) longobjectarray2, comparator));
    assertEquals(1, compare(longobjectarray3, (Object) longobjectarray2, comparator));
    assertEquals(-1, compare(longobjectarray2, (Object) longobjectarray3, comparator));
    assertEquals(-3, compare(longobjectarray4, (Object) longobjectarray2, comparator));
    assertEquals(3, compare(longobjectarray2, (Object) longobjectarray4, comparator));

    Float[] floatobjectarray1 = { (float) 0, (float) -1, (float) 1,
        Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2),
        (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE };
    Float[] floatobjectarray2 = { (float) 0, (float) -1, (float) 1,
        Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2),
        (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE };
    Float[] floatobjectarray3 = { (float) 0, (float) 2 };
    Float[] floatobjectarray4 = { (float) 0, (float) -1, (float) 1,
        Float.MIN_VALUE };
    assertEquals(0, compare(floatobjectarray1, (Object) floatobjectarray2, comparator));
    assertEquals(1, compare(floatobjectarray3, (Object) floatobjectarray2, comparator));
    assertEquals(-1, compare(floatobjectarray2, (Object) floatobjectarray3, comparator));
    assertEquals(-3, compare(floatobjectarray4, (Object) floatobjectarray2, comparator));
    assertEquals(3, compare(floatobjectarray2, (Object) floatobjectarray4, comparator));

    Double[] doubleobjectarray1 = { (double) 0, (double) -1, (double) 1,
        (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE,
        (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE };
    Double[] doubleobjectarray2 = { (double) 0, (double) -1, (double) 1,
        (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE,
        (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE };
    Double[] doubleobjectarray3 = { (double) 0, (double) 2 };
    Double[] doubleobjectarray4 = { (double) 0, (double) -1, (double) 1,
        (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE };
    assertEquals(0, compare(doubleobjectarray1, (Object) doubleobjectarray2, comparator));
    assertEquals(1, compare(doubleobjectarray3, (Object) doubleobjectarray2, comparator));
    assertEquals(-1, compare(doubleobjectarray2, (Object) doubleobjectarray3, comparator));
    assertEquals(-2, compare(doubleobjectarray4, (Object) doubleobjectarray2, comparator));
    assertEquals(2, compare(doubleobjectarray2, (Object) doubleobjectarray4, comparator));

    Class<?>[] classarray1 = new Class<?>[] { TestClassA.class,
        TestClassA.class };
    Class<?>[] classarray2 = new Class<?>[] { TestClassA.class,
        TestClassA.class };
    Class<?>[] classarray3 = new Class<?>[] { TestClassB.class,
        TestClassB.class };
    Class<?>[] classarray4 = new Class<?>[] { TestClassA.class,
        TestClassA.class, TestClassA.class };
    assertEquals(0, compare(classarray1, (Object) classarray2, comparator));
    assertEquals(1, compare(classarray3, (Object) classarray2, comparator));
    assertEquals(-1, compare(classarray2, (Object) classarray3, comparator));
    assertEquals(1, compare(classarray4, (Object) classarray2, comparator));
    assertEquals(-1, compare(classarray2, (Object) classarray4, comparator));

    Date datea = new Date();
    datea.setTime((long) 123456);
    Date dateb = new Date();
    dateb.setTime((long) 7891011);
    Date datec = new Date();
    datec.setTime((long) 654321);
    Date[] datearray1 = { datea, dateb, datec };
    Date[] datearray2 = { datea, dateb, datec };
    Date[] datearray3 = { datec, datea, dateb };
    Date[] datearray4 = { datea };
    assertEquals(0, compare(datearray1, (Object) datearray2, comparator));
    assertEquals(1, compare(datearray3, (Object) datearray2, comparator));
    assertEquals(-1, compare(datearray2, (Object) datearray3, comparator));
    assertEquals(-2, compare(datearray4, (Object) datearray2, comparator));
    assertEquals(2, compare(datearray2, (Object) datearray4, comparator));

    BigInteger[] bigintegerarray1 = { new BigInteger(bytearray1),
        new BigInteger(bytearray3) };
    BigInteger[] bigintegerarray2 = { new BigInteger(bytearray2),
        new BigInteger(bytearray3) };
    BigInteger[] bigintegerarray3 = { new BigInteger(bytearray3) };
    BigInteger[] bigintegerarray4 = { new BigInteger(bytearray2) };
    assertEquals(0, compare(bigintegerarray1, (Object) bigintegerarray2, comparator));
    assertEquals(-1, compare(bigintegerarray3, (Object) bigintegerarray2, comparator));
    assertEquals(1, compare(bigintegerarray2, (Object) bigintegerarray3, comparator));
    assertEquals(-1, compare(bigintegerarray4, (Object) bigintegerarray2, comparator));
    assertEquals(1, compare(bigintegerarray2, (Object) bigintegerarray4, comparator));

    BigDecimal[] bigdecimalarray1 = { new BigDecimal(Integer.MIN_VALUE),
        new BigDecimal(Integer.MAX_VALUE) };
    BigDecimal[] bigdecimalarray2 = { new BigDecimal(Integer.MIN_VALUE),
        new BigDecimal(Integer.MAX_VALUE) };
    BigDecimal[] bigdecimalarray3 = { new BigDecimal(0), new BigDecimal(-1) };
    BigDecimal[] bigdecimalarray4 = { new BigDecimal(Integer.MIN_VALUE) };
    assertEquals(0, compare(bigdecimalarray1, (Object) bigdecimalarray2, comparator));
    assertEquals(-1, compare(bigdecimalarray2, (Object) bigdecimalarray3, comparator));
    assertEquals(1, compare(bigdecimalarray3, (Object) bigdecimalarray2, comparator));
    assertEquals(1, compare(bigdecimalarray2, (Object) bigdecimalarray4, comparator));
    assertEquals(-1, compare(bigdecimalarray4, (Object) bigdecimalarray2, comparator));

    Boolean[][] objectarray1 = { { true, false }, { true, true },
        { false, false, true } };
    Boolean[][] objectarray2 = { { true, false }, { true, true },
        { false, false, true } };
    Boolean[][] objectarray3 = { { true, false }, { true, true } };
    Boolean[][] objectarray4 = { { true, false }, { false, true } };
    assertEquals(0, compare(objectarray1, (Object) objectarray2, comparator));
    assertEquals(-1, compare(objectarray3, (Object) objectarray2, comparator));
    assertEquals(1, compare(objectarray2, (Object) objectarray3, comparator));
    assertEquals(-1, compare(objectarray4, (Object) objectarray2, comparator));
    assertEquals(1, compare(objectarray2, (Object) objectarray4, comparator));
  }

  @Test
  public void testObjectArrayComparator() {
    final StringComparator comparator = new StringComparator();
    
    Object[] array1 = { new TestClassA(), new TestClassA() };
    Object[] array2 = { new TestClassA(), new TestClassA() };

    assertEquals(0, compare(array1, array1, comparator));
    array2 = null;
    assertEquals(1, compare(array1, array2, comparator));
    assertEquals(-1, compare(array2, array1, comparator));
    array1 = null;
    assertEquals(0, compare(array1, array2, comparator));

    boolean[][] booleanarray1 = { { true, false }, { false, true } };
    boolean[][] booleanarray2 = { { true, false }, { false, true } };
    boolean[][] booleanarray3 = { { false, true }, { true } };
    boolean[][] booleanarray4 = { { true, false } };
    assertEquals(0, compare(booleanarray1, booleanarray2, comparator));
    assertEquals(1, compare(booleanarray1, booleanarray3, comparator));
    assertEquals(-1, compare(booleanarray3, booleanarray1, comparator));
    assertEquals(1, compare(booleanarray1, booleanarray4, comparator));
    assertEquals(-1, compare(booleanarray4, booleanarray1, comparator));

    char[][] chararray1 = { { (char) 0, (char) 1 },
        { (char) Character.MIN_VALUE / 2, Character.MIN_VALUE },
        { (char) Character.MAX_VALUE, Character.MAX_VALUE } };
    char[][] chararray2 = { { (char) 0, (char) 1 },
        { (char) Character.MIN_VALUE / 2, Character.MIN_VALUE },
        { (char) Character.MAX_VALUE, Character.MAX_VALUE } };
    char[][] chararray3 = { { (char) 0, (char) 3 } };
    char[][] chararray4 = { { (char) 0, (char) 1 },
        { (char) Character.MIN_VALUE / 2, Character.MIN_VALUE } };
    assertEquals(0, compare(chararray1, chararray2, comparator));
    assertEquals(-2, compare(chararray1, chararray3, comparator));
    assertEquals(2, compare(chararray3, chararray1, comparator));
    assertEquals(1, compare(chararray1, chararray4, comparator));
    assertEquals(-1, compare(chararray4, chararray1, comparator));

    byte[][] bytearray1 = { { (byte) 0, (byte) -1, (byte) 1 },
        { (byte) Byte.MIN_VALUE / 2, Byte.MIN_VALUE },
        { (byte) Byte.MAX_VALUE, Byte.MAX_VALUE } };
    byte[][] bytearray2 = { { (byte) 0, (byte) -1, (byte) 1 },
        { (byte) Byte.MIN_VALUE / 2, Byte.MIN_VALUE },
        { (byte) Byte.MAX_VALUE, Byte.MAX_VALUE } };
    byte[][] bytearray3 = { { (byte) 0, (byte) 3 } };
    byte[][] bytearray4 = { { (byte) 0, (byte) -1, (byte) 1 },
        { (byte) Byte.MIN_VALUE / 2, Byte.MIN_VALUE } };
    assertEquals(0, compare(bytearray1, bytearray2, comparator));
    assertEquals(-4, compare(bytearray1, bytearray3, comparator));
    assertEquals(4, compare(bytearray3, bytearray1, comparator));
    assertEquals(1, compare(bytearray1, bytearray4, comparator));
    assertEquals(-1, compare(bytearray4, bytearray1, comparator));

    short[][] shortarray1 = { { (short) 0, (short) -1, (short) 1 },
        { (short) Short.MIN_VALUE / 2, Short.MIN_VALUE },
        { (short) Short.MAX_VALUE, Short.MAX_VALUE } };
    short[][] shortarray2 = { { (short) 0, (short) -1, (short) 1 },
        { (short) Short.MIN_VALUE / 2, Short.MIN_VALUE },
        { (short) Short.MAX_VALUE, Short.MAX_VALUE } };
    short[][] shortarray3 = { { (short) 0, (short) 3 } };
    short[][] shortarray4 = { { (short) 0, (short) -1, (short) 1 },
        { (short) Short.MIN_VALUE / 2, Short.MIN_VALUE } };
    assertEquals(0, compare(shortarray1, shortarray2, comparator));
    assertEquals(-4, compare(shortarray1, shortarray3, comparator));
    assertEquals(4, compare(shortarray3, shortarray1, comparator));
    assertEquals(1, compare(shortarray1, shortarray4, comparator));
    assertEquals(-1, compare(shortarray4, shortarray1, comparator));

    int[][] intarray1 = { { (int) 0, (int) -1, (int) 1 },
        { (int) Integer.MIN_VALUE / 2, Integer.MIN_VALUE },
        { (int) Integer.MAX_VALUE, Integer.MAX_VALUE } };
    int[][] intarray2 = { { (int) 0, (int) -1, (int) 1 },
        { (int) Integer.MIN_VALUE / 2, Integer.MIN_VALUE },
        { (int) Integer.MAX_VALUE, Integer.MAX_VALUE } };
    int[][] intarray3 = { { (int) 0, (int) 3 } };
    int[][] intarray4 = { { (int) 0, (int) -1, (int) 1 },
        { (int) Integer.MIN_VALUE / 2, Integer.MIN_VALUE } };
    assertEquals(0, compare(intarray1, intarray2, comparator));
    assertEquals(-4, compare(intarray1, intarray3, comparator));
    assertEquals(4, compare(intarray3, intarray1, comparator));
    assertEquals(1, compare(intarray1, intarray4, comparator));
    assertEquals(-1, compare(intarray4, intarray1, comparator));

    long[][] longarray1 = { { (long) 0, (long) -1, (long) 1 },
        { (long) Long.MIN_VALUE / 2, Long.MIN_VALUE },
        { (long) Long.MAX_VALUE, Long.MAX_VALUE } };
    long[][] longarray2 = { { (long) 0, (long) -1, (long) 1 },
        { (long) Long.MIN_VALUE / 2, Long.MIN_VALUE },
        { (long) Long.MAX_VALUE, Long.MAX_VALUE } };
    long[][] longarray3 = { { (long) 0, (long) 3 } };
    long[][] longarray4 = { { (long) 0, (long) -1, (long) 1 },
        { (long) Long.MIN_VALUE / 2, Long.MIN_VALUE } };
    assertEquals(0, compare(longarray1, longarray2, comparator));
    assertEquals(-1, compare(longarray1, longarray3, comparator));
    assertEquals(1, compare(longarray3, longarray1, comparator));
    assertEquals(1, compare(longarray1, longarray4, comparator));
    assertEquals(-1, compare(longarray4, longarray1, comparator));

    float[][] floatarray1 = { { (float) 0, (float) -1, (float) 1 },
        { (float) Float.MIN_VALUE / 2, Float.MIN_VALUE },
        { (float) Float.MAX_VALUE, Float.MAX_VALUE } };
    float[][] floatarray2 = { { (float) 0, (float) -1, (float) 1 },
        { (float) Float.MIN_VALUE / 2, Float.MIN_VALUE },
        { (float) Float.MAX_VALUE, Float.MAX_VALUE } };
    float[][] floatarray3 = { { (float) 0, (float) 3 } };
    float[][] floatarray4 = { { (float) 0, (float) -1, (float) 1 },
        { (float) Float.MIN_VALUE / 2, Float.MIN_VALUE } };
    assertEquals(0, compare(floatarray1, floatarray2, comparator));
    assertEquals(-1, compare(floatarray1, floatarray3, comparator));
    assertEquals(1, compare(floatarray3, floatarray1, comparator));
    assertEquals(1, compare(floatarray1, floatarray4, comparator));
    assertEquals(-1, compare(floatarray4, floatarray1, comparator));

    double[][] doublearray1 = { { (double) 0, (double) -1, (double) 1 },
        { (double) Double.MIN_VALUE / 2, Double.MIN_VALUE },
        { (double) Double.MAX_VALUE, Double.MAX_VALUE } };
    double[][] doublearray2 = { { (double) 0, (double) -1, (double) 1 },
        { (double) Double.MIN_VALUE / 2, Double.MIN_VALUE },
        { (double) Double.MAX_VALUE, Double.MAX_VALUE } };
    double[][] doublearray3 = { { (double) 0, (double) 3 } };
    double[][] doublearray4 = { { (double) 0, (double) -1, (double) 1 },
        { (double) Double.MIN_VALUE / 2, Double.MIN_VALUE } };
    assertEquals(0, compare(doublearray1, doublearray2, comparator));
    assertEquals(-1, compare(doublearray1, doublearray3, comparator));
    assertEquals(1, compare(doublearray3, doublearray1, comparator));
    assertEquals(1, compare(doublearray1, doublearray4, comparator));
    assertEquals(-1, compare(doublearray4, doublearray1, comparator));

    String[][] stringarray1 = { { "long", "long" }, { "ago" } };
    String[][] stringarray2 = { { "long", "long" }, { "ago" } };
    String[][] stringarray3 = { { "long", "time" }, { "no", "see" } };
    String[][] stringarray4 = { { "long", "long" } };
    assertEquals(0, compare(stringarray1, stringarray2, comparator));
    assertEquals(-8, compare(stringarray2, stringarray3, comparator));
    assertEquals(8, compare(stringarray3, stringarray2, comparator));
    assertEquals(1, compare(stringarray2, stringarray4, comparator));
    assertEquals(-1, compare(stringarray4, stringarray2, comparator));

    Boolean[][] booleanobjectarray1 = { { true, false }, { false, true } };
    Boolean[][] booleanobjectarray2 = { { true, false }, { false, true } };
    Boolean[][] booleanobjectarray3 = { { false, true }, { true } };
    Boolean[][] booleanobjectarray4 = { { true, false } };
    assertEquals(0, compare(booleanobjectarray1, booleanobjectarray2, comparator));
    assertEquals(1, compare(booleanobjectarray2, booleanobjectarray3, comparator));
    assertEquals(-1, compare(booleanobjectarray3, booleanobjectarray2, comparator));
    assertEquals(1, compare(booleanobjectarray2, booleanobjectarray4, comparator));
    assertEquals(-1, compare(booleanobjectarray4, booleanobjectarray2, comparator));

    Character[][] charobjectarray1 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    Character[][] charobjectarray2 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    Character[][] charobjectarray3 = { { (char) 0, (char) 3 } };
    Character[][] charobjectarray4 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE } };
    assertEquals(0, compare(charobjectarray1, charobjectarray2, comparator));
    assertEquals(2, compare(charobjectarray3, charobjectarray2, comparator));
    assertEquals(-2, compare(charobjectarray2, charobjectarray3, comparator));
    assertEquals(-1, compare(charobjectarray4, charobjectarray2, comparator));
    assertEquals(1, compare(charobjectarray2, charobjectarray4, comparator));

    Byte[][] byteobjectarray1 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    Byte[][] byteobjectarray2 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    Byte[][] byteobjectarray3 = { { (byte) 0, (byte) 2 } };
    Byte[][] byteobjectarray4 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) } };
    assertEquals(0, compare(byteobjectarray1, byteobjectarray2, comparator));
    assertEquals(3, compare(byteobjectarray3, byteobjectarray2, comparator));
    assertEquals(-3, compare(byteobjectarray2, byteobjectarray3, comparator));
    assertEquals(-1, compare(byteobjectarray4, byteobjectarray2, comparator));
    assertEquals(1, compare(byteobjectarray2, byteobjectarray4, comparator));

    Short[][] shortobjectarray1 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
    Short[][] shortobjectarray2 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
    Short[][] shortobjectarray3 = { { (short) 0, (short) 2 } };
    Short[][] shortobjectarray4 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) } };
    assertEquals(0, compare(shortobjectarray1, shortobjectarray2, comparator));
    assertEquals(3, compare(shortobjectarray3, shortobjectarray2, comparator));
    assertEquals(-3, compare(shortobjectarray2, shortobjectarray3, comparator));
    assertEquals(-1, compare(shortobjectarray4, shortobjectarray2, comparator));
    assertEquals(1, compare(shortobjectarray2, shortobjectarray4, comparator));

    Integer[][] integerarray1 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) },
        { (int) (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
    Integer[][] integerarray2 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) },
        { (int) (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
    Integer[][] integerarray3 = { { (int) 0, (int) 3 } };
    Integer[][] integerarray4 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) } };
    assertEquals(0, compare(integerarray1, integerarray2, comparator));
    assertEquals(4, compare(integerarray3, integerarray2, comparator));
    assertEquals(-4, compare(integerarray2, integerarray3, comparator));
    assertEquals(-1, compare(integerarray4, integerarray2, comparator));
    assertEquals(1, compare(integerarray2, integerarray4, comparator));

    Long[][] longobjectarray1 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) },
        { (long) (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
    Long[][] longobjectarray2 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) },
        { (long) (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
    Long[][] longobjectarray3 = { { (long) 0, (long) 2 } };
    Long[][] longobjectarray4 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) } };
    assertEquals(0, compare(longobjectarray1, longobjectarray2, comparator));
    assertEquals(1, compare(longobjectarray3, longobjectarray2, comparator));
    assertEquals(-1, compare(longobjectarray2, longobjectarray3, comparator));
    assertEquals(-1, compare(longobjectarray4, longobjectarray2, comparator));
    assertEquals(1, compare(longobjectarray2, longobjectarray4, comparator));

    Float[][] floatobjectarray1 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) },
        { (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
    Float[][] floatobjectarray2 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) },
        { (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
    Float[][] floatobjectarray3 = { { (float) 0, (float) 2 } };
    Float[][] floatobjectarray4 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) } };
    assertEquals(0, compare(floatobjectarray1, floatobjectarray2, comparator));
    assertEquals(1, compare(floatobjectarray3, floatobjectarray2, comparator));
    assertEquals(-1, compare(floatobjectarray2, floatobjectarray3, comparator));
    assertEquals(-1, compare(floatobjectarray4, floatobjectarray2, comparator));
    assertEquals(1, compare(floatobjectarray2, floatobjectarray4, comparator));

    Double[][] doubleobjectarray1 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE },
        { (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
    Double[][] doubleobjectarray2 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE },
        { (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
    Double[][] doubleobjectarray3 = { { (double) 0, (double) 2 } };
    Double[][] doubleobjectarray4 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE } };
    assertEquals(0, compare(doubleobjectarray1, doubleobjectarray2, comparator));
    assertEquals(1, compare(doubleobjectarray3, doubleobjectarray2, comparator));
    assertEquals(-1, compare(doubleobjectarray2, doubleobjectarray3, comparator));
    assertEquals(-1, compare(doubleobjectarray4, doubleobjectarray2, comparator));
    assertEquals(1, compare(doubleobjectarray2, doubleobjectarray4, comparator));

    Class<?>[][] classarray1 = { { TestClassA.class }, { TestClassA.class } };
    Class<?>[][] classarray2 = { { TestClassA.class }, { TestClassA.class } };
    Class<?>[][] classarray3 = { { TestClassB.class }, { TestClassB.class } };
    Class<?>[][] classarray4 = { { TestClassA.class }, { TestClassA.class },
        { TestClassA.class }};
    assertEquals(0, compare(classarray1, classarray2, comparator));
    assertEquals(1, compare(classarray3, classarray2, comparator));
    assertEquals(-1, compare(classarray2, classarray3, comparator));
    assertEquals(1, compare(classarray4, classarray2, comparator));
    assertEquals(-1, compare(classarray2, classarray4, comparator));

    Date datea = new Date();
    datea.setTime((long) 123456);
    Date dateb = new Date();
    dateb.setTime((long) 7891011);
    Date datec = new Date();
    datec.setTime((long) 654321);
    Date[][] datearray1 = { {datea, dateb}, {datec} };
    Date[][] datearray2 = { {datea, dateb}, {datec} };
    Date[][] datearray3 = { {datec, datea}, {dateb} };
    Date[][] datearray4 = { {datea, dateb}};
    assertEquals(0, compare(datearray1, datearray2, comparator));
    assertEquals(1, compare(datearray3, datearray2, comparator));
    assertEquals(-1, compare(datearray2, datearray3, comparator));
    assertEquals(-1, compare(datearray4, datearray2, comparator));
    assertEquals(1, compare(datearray2, datearray4, comparator));

    BigInteger[][] bigintegerarray1 = { {new BigInteger(bytearray1[0])},
        {new BigInteger(bytearray3[0]) }};
    BigInteger[][] bigintegerarray2 = { {new BigInteger(bytearray2[0])},
        {new BigInteger(bytearray3[0]) }};
    BigInteger[][] bigintegerarray3 = { {new BigInteger(bytearray3[0]) }};
    BigInteger[][] bigintegerarray4 = { {new BigInteger(bytearray2[0]) }};
    assertEquals(0, compare(bigintegerarray1, bigintegerarray2, comparator));
    assertEquals(-1, compare(bigintegerarray3, bigintegerarray2, comparator));
    assertEquals(1, compare(bigintegerarray2, bigintegerarray3, comparator));
    assertEquals(-1, compare(bigintegerarray4, bigintegerarray2, comparator));
    assertEquals(1, compare(bigintegerarray2, bigintegerarray4, comparator));

    BigDecimal[][] bigdecimalarray1 = { {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE) }};
    BigDecimal[][] bigdecimalarray2 = { {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE) }};
    BigDecimal[][] bigdecimalarray3 = { {new BigDecimal(0), new BigDecimal(-1)}};
    BigDecimal[][] bigdecimalarray4 = { {new BigDecimal(Integer.MIN_VALUE) }};
    assertEquals(0, compare(bigdecimalarray1, bigdecimalarray2, comparator));
    assertEquals(-1, compare(bigdecimalarray2, bigdecimalarray3, comparator));
    assertEquals(1, compare(bigdecimalarray3, bigdecimalarray2, comparator));
    assertEquals(1, compare(bigdecimalarray2, bigdecimalarray4, comparator));
    assertEquals(-1, compare(bigdecimalarray4, bigdecimalarray2, comparator));

    Boolean[][][] objectarray1 = { { {true}, {false} }, { {true, true }},
        { {false, false}, {true }} };
    Boolean[][][] objectarray2 = { { {true}, {false} }, { {true, true }},
        { {false, false}, {true }} };
    Boolean[][][] objectarray3 = { {{ true}, {false} }, { {false, true }} };
    Boolean[][][] objectarray4 = { { {true}, {false} }, { {true, true }} };
    assertEquals(0, compare(objectarray1, objectarray2, comparator));
    assertEquals(-1, compare(objectarray3, objectarray2, comparator));
    assertEquals(1, compare(objectarray2, objectarray3, comparator));
    assertEquals(-1, compare(objectarray4, objectarray2, comparator));
    assertEquals(1, compare(objectarray2, objectarray4, comparator));
  }

  @Test
  public void testObjectArrayIntComparator() {
    final StringComparator comparator = new StringComparator();
    
    Object[] array1 = { new TestClassA(), new TestClassA() };
    Object[] array2 = { new TestClassA(), new TestClassA() };

    assertEquals(0, compare(array1, array1.length, array1, array1.length, comparator));
    assertEquals(true, compare(array1, 1, array2, 1) != 0);

    boolean[][] booleanarray1 = { { true, false }, { false, true } };
    boolean[][] booleanarray2 = { { true, false }, { false, true } };
    boolean[][] booleanarray3 = { { false, true }, { true } };
    boolean[][] booleanarray4 = { { true, false } };
    assertEquals(0, compare(booleanarray1, booleanarray1.length, 
        booleanarray2, booleanarray2.length, comparator));
    assertEquals(1, compare(booleanarray1, booleanarray1.length, 
        booleanarray3, booleanarray3.length, comparator));
    assertEquals(-1, compare(booleanarray3, booleanarray3.length, 
        booleanarray1 , booleanarray1.length, comparator));
    assertEquals(1, compare(booleanarray1, booleanarray1.length, 
        booleanarray4, booleanarray4.length, comparator));
    assertEquals(-1, compare(booleanarray4, booleanarray4.length, 
        booleanarray1, booleanarray1.length, comparator));

    char[][] chararray1 = { { (char) 0, (char) 1 },
        { (char) Character.MIN_VALUE / 2, Character.MIN_VALUE },
        { (char) Character.MAX_VALUE, Character.MAX_VALUE } };
    char[][] chararray2 = { { (char) 0, (char) 1 },
        { (char) Character.MIN_VALUE / 2, Character.MIN_VALUE },
        { (char) Character.MAX_VALUE, Character.MAX_VALUE } };
    char[][] chararray3 = { { (char) 0, (char) 3 } };
    char[][] chararray4 = { { (char) 0, (char) 1 },
        { (char) Character.MIN_VALUE / 2, Character.MIN_VALUE } };
    assertEquals(0, compare(chararray1, chararray1.length, chararray2, 
        chararray2.length, comparator));
    assertEquals(-2, compare(chararray1, chararray1.length, chararray3, 
        chararray3.length, comparator));
    assertEquals(2, compare(chararray3, chararray3.length, chararray1, 
        chararray1.length, comparator));
    assertEquals(1, compare(chararray1, chararray1.length, chararray4, 
        chararray4.length, comparator));
    assertEquals(-1, compare(chararray4, chararray4.length, chararray1, 
        chararray1.length, comparator));
    assertEquals(0, compare(chararray1, 1, chararray4, 1, comparator));

    byte[][] bytearray1 = { { (byte) 0, (byte) -1, (byte) 1 },
        { (byte) Byte.MIN_VALUE / 2, Byte.MIN_VALUE },
        { (byte) Byte.MAX_VALUE, Byte.MAX_VALUE } };
    byte[][] bytearray2 = { { (byte) 0, (byte) -1, (byte) 1 },
        { (byte) Byte.MIN_VALUE / 2, Byte.MIN_VALUE },
        { (byte) Byte.MAX_VALUE, Byte.MAX_VALUE } };
    byte[][] bytearray3 = { { (byte) 0, (byte) 3 } };
    byte[][] bytearray4 = { { (byte) 0, (byte) -1, (byte) 1 },
        { (byte) Byte.MIN_VALUE / 2, Byte.MIN_VALUE } };
    assertEquals(0, compare(bytearray1, bytearray1.length, bytearray2, bytearray2.length, comparator));
    assertEquals(-4, compare(bytearray1, bytearray1.length, bytearray3, bytearray3.length, comparator));
    assertEquals(4, compare(bytearray3, bytearray3.length, bytearray1, bytearray1.length, comparator));
    assertEquals(1, compare(bytearray1, bytearray1.length, bytearray4, bytearray4.length, comparator));
    assertEquals(-1, compare(bytearray4, bytearray4.length, bytearray1, bytearray1.length, comparator));
    assertEquals(0, compare(bytearray1, 1, bytearray4, 1, comparator));

    short[][] shortarray1 = { { (short) 0, (short) -1, (short) 1 },
        { (short) Short.MIN_VALUE / 2, Short.MIN_VALUE },
        { (short) Short.MAX_VALUE, Short.MAX_VALUE } };
    short[][] shortarray2 = { { (short) 0, (short) -1, (short) 1 },
        { (short) Short.MIN_VALUE / 2, Short.MIN_VALUE },
        { (short) Short.MAX_VALUE, Short.MAX_VALUE } };
    short[][] shortarray3 = { { (short) 0, (short) 3 } };
    short[][] shortarray4 = { { (short) 0, (short) -1, (short) 1 },
        { (short) Short.MIN_VALUE / 2, Short.MIN_VALUE } };
    assertEquals(0, compare(shortarray1, shortarray1.length, shortarray2, shortarray2.length, comparator));
    assertEquals(-4, compare(shortarray1, shortarray1.length, shortarray3, shortarray3.length, comparator));
    assertEquals(4, compare(shortarray3, shortarray3.length, shortarray1, shortarray1.length, comparator));
    assertEquals(1, compare(shortarray1, shortarray1.length, shortarray4, shortarray4.length, comparator));
    assertEquals(-1, compare(shortarray4, shortarray4.length, shortarray1, shortarray1.length, comparator));
    assertEquals(0, compare(shortarray1, 1, shortarray4, 1, comparator));

    int[][] intarray1 = { { (int) 0, (int) -1, (int) 1 },
        { (int) Integer.MIN_VALUE / 2, Integer.MIN_VALUE },
        { (int) Integer.MAX_VALUE, Integer.MAX_VALUE } };
    int[][] intarray2 = { { (int) 0, (int) -1, (int) 1 },
        { (int) Integer.MIN_VALUE / 2, Integer.MIN_VALUE },
        { (int) Integer.MAX_VALUE, Integer.MAX_VALUE } };
    int[][] intarray3 = { { (int) 0, (int) 3 } };
    int[][] intarray4 = { { (int) 0, (int) -1, (int) 1 },
        { (int) Integer.MIN_VALUE / 2, Integer.MIN_VALUE } };
    assertEquals(0, compare(intarray1, intarray1.length, intarray2, intarray2.length, comparator));
    assertEquals(-4, compare(intarray1, intarray1.length, intarray3, intarray3.length, comparator));
    assertEquals(4, compare(intarray3, intarray3.length, intarray1, intarray1.length, comparator));
    assertEquals(1, compare(intarray1, intarray1.length, intarray4, intarray4.length, comparator));
    assertEquals(-1, compare(intarray4, intarray4.length, intarray1, intarray1.length, comparator));
    assertEquals(0, compare(intarray1, 1, intarray4, 1, comparator));

    long[][] longarray1 = { { (long) 0, (long) -1, (long) 1 },
        { (long) Long.MIN_VALUE / 2, Long.MIN_VALUE },
        { (long) Long.MAX_VALUE, Long.MAX_VALUE } };
    long[][] longarray2 = { { (long) 0, (long) -1, (long) 1 },
        { (long) Long.MIN_VALUE / 2, Long.MIN_VALUE },
        { (long) Long.MAX_VALUE, Long.MAX_VALUE } };
    long[][] longarray3 = { { (long) 0, (long) 3 } };
    long[][] longarray4 = { { (long) 0, (long) -1, (long) 1 },
        { (long) Long.MIN_VALUE / 2, Long.MIN_VALUE } };
    assertEquals(0, compare(longarray1, longarray1.length, longarray2, longarray2.length, comparator));
    assertEquals(-1, compare(longarray1, longarray1.length, longarray3, longarray3.length, comparator));
    assertEquals(1, compare(longarray3, longarray3.length, longarray1, longarray1.length, comparator));
    assertEquals(1, compare(longarray1, longarray1.length, longarray4, longarray4.length, comparator));
    assertEquals(-1, compare(longarray4, longarray4.length, longarray1, longarray1.length, comparator));
    assertEquals(0, compare(longarray1, 1, longarray4, 1, comparator));

    float[][] floatarray1 = { { (float) 0, (float) -1, (float) 1 },
        { (float) Float.MIN_VALUE / 2, Float.MIN_VALUE },
        { (float) Float.MAX_VALUE, Float.MAX_VALUE } };
    float[][] floatarray2 = { { (float) 0, (float) -1, (float) 1 },
        { (float) Float.MIN_VALUE / 2, Float.MIN_VALUE },
        { (float) Float.MAX_VALUE, Float.MAX_VALUE } };
    float[][] floatarray3 = { { (float) 0, (float) 3 } };
    float[][] floatarray4 = { { (float) 0, (float) -1, (float) 1 },
        { (float) Float.MIN_VALUE / 2, Float.MIN_VALUE } };
    assertEquals(0, compare(floatarray1, floatarray1.length, floatarray2, floatarray2.length, comparator));
    assertEquals(-1, compare(floatarray1, floatarray1.length, floatarray3, floatarray3.length, comparator));
    assertEquals(1, compare(floatarray3, floatarray3.length, floatarray1, floatarray1.length, comparator));
    assertEquals(1, compare(floatarray1, floatarray1.length, floatarray4, floatarray4.length, comparator));
    assertEquals(-1, compare(floatarray4, floatarray4.length, floatarray1, floatarray1.length, comparator));
    assertEquals(0, compare(floatarray1, 1, floatarray4, 1, comparator));

    double[][] doublearray1 = { { (double) 0, (double) -1, (double) 1 },
        { (double) Double.MIN_VALUE / 2, Double.MIN_VALUE },
        { (double) Double.MAX_VALUE, Double.MAX_VALUE } };
    double[][] doublearray2 = { { (double) 0, (double) -1, (double) 1 },
        { (double) Double.MIN_VALUE / 2, Double.MIN_VALUE },
        { (double) Double.MAX_VALUE, Double.MAX_VALUE } };
    double[][] doublearray3 = { { (double) 0, (double) 3 } };
    double[][] doublearray4 = { { (double) 0, (double) -1, (double) 1 },
        { (double) Double.MIN_VALUE / 2, Double.MIN_VALUE } };
    assertEquals(0, compare(doublearray1, doublearray1.length, doublearray2, doublearray2.length, comparator));
    assertEquals(-1, compare(doublearray1, doublearray1.length, doublearray3, doublearray3.length, comparator));
    assertEquals(1, compare(doublearray3, doublearray3.length, doublearray1, doublearray1.length, comparator));
    assertEquals(1, compare(doublearray1, doublearray1.length, doublearray4, doublearray4.length, comparator));
    assertEquals(-1, compare(doublearray4, doublearray4.length, doublearray1, doublearray1.length, comparator));
    assertEquals(0, compare(doublearray1, 1, doublearray4, 1, comparator));

    String[][] stringarray1 = { { "long", "long" }, { "ago" } };
    String[][] stringarray2 = { { "long", "long" }, { "ago" } };
    String[][] stringarray3 = { { "long", "time" }, { "no", "see" } };
    String[][] stringarray4 = { { "long", "long" } };
    assertEquals(0, compare(stringarray1, stringarray1.length, stringarray2, 
        stringarray2.length, comparator));
    assertEquals(-8, compare(stringarray2, stringarray2.length, stringarray3, 
        stringarray3.length, comparator));
    assertEquals(8, compare(stringarray3, stringarray3.length, stringarray2, 
        stringarray2.length, comparator));
    assertEquals(1, compare(stringarray2, stringarray2.length, stringarray4, 
        stringarray4.length, comparator));
    assertEquals(-1, compare(stringarray4, stringarray4.length, stringarray2, 
        stringarray2.length, comparator));
    assertEquals(0, compare(stringarray1, 1, stringarray4, 1, comparator));

    Boolean[][] booleanobjectarray1 = { { true, false }, { false, true } };
    Boolean[][] booleanobjectarray2 = { { true, false }, { false, true } };
    Boolean[][] booleanobjectarray3 = { { false, true }, { true } };
    Boolean[][] booleanobjectarray4 = { { true, false } };
    assertEquals(0, compare(booleanobjectarray1, booleanobjectarray1.length, 
        booleanobjectarray2, booleanobjectarray2.length, comparator));
    assertEquals(1, compare(booleanobjectarray2, booleanobjectarray2.length, 
        booleanobjectarray3, booleanobjectarray3.length, comparator));
    assertEquals(-1, compare(booleanobjectarray3, booleanobjectarray3.length, 
        booleanobjectarray2, booleanobjectarray2.length, comparator));
    assertEquals(1, compare(booleanobjectarray2, booleanobjectarray2.length, 
        booleanobjectarray4, booleanobjectarray4.length, comparator));
    assertEquals(-1, compare(booleanobjectarray4, booleanobjectarray4.length, 
        booleanobjectarray2, booleanobjectarray2.length, comparator));
    assertEquals(0, compare(booleanobjectarray1, 1, booleanobjectarray4, 1, comparator));

    Character[][] charobjectarray1 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    Character[][] charobjectarray2 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
    Character[][] charobjectarray3 = { { (char) 0, (char) 3 } };
    Character[][] charobjectarray4 = { { (char) 0, (char) 1 },
        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE } };
    assertEquals(0, compare(charobjectarray1, charobjectarray1.length, 
        charobjectarray2, charobjectarray2.length, comparator));
    assertEquals(2, compare(charobjectarray3, charobjectarray3.length, 
        charobjectarray2, charobjectarray2.length, comparator));
    assertEquals(-2, compare(charobjectarray2, charobjectarray2.length, 
        charobjectarray3, charobjectarray3.length, comparator));
    assertEquals(-1, compare(charobjectarray4, charobjectarray4.length, 
        charobjectarray2, charobjectarray2.length, comparator));
    assertEquals(1, compare(charobjectarray2, charobjectarray2.length, 
        charobjectarray4, charobjectarray4.length, comparator));
    assertEquals(0, compare(charobjectarray1, 1, charobjectarray4, 1, comparator));

    Byte[][] byteobjectarray1 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    Byte[][] byteobjectarray2 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
    Byte[][] byteobjectarray3 = { { (byte) 0, (byte) 2 } };
    Byte[][] byteobjectarray4 = { { (byte) 0, (byte) -1, (byte) 1 },
        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) } };
    assertEquals(0, compare(byteobjectarray1, byteobjectarray1.length,
        byteobjectarray2, byteobjectarray2.length, comparator));
    assertEquals(3, compare(byteobjectarray3, byteobjectarray3.length, 
        byteobjectarray2, byteobjectarray2.length, comparator));
    assertEquals(-3, compare(byteobjectarray2, byteobjectarray2.length, 
        byteobjectarray3, byteobjectarray3.length, comparator));
    assertEquals(-1, compare(byteobjectarray4, byteobjectarray4.length, 
        byteobjectarray2, byteobjectarray2.length, comparator));
    assertEquals(1, compare(byteobjectarray2, byteobjectarray2.length, 
        byteobjectarray4, byteobjectarray4.length, comparator));
    assertEquals(0, compare(byteobjectarray1, 1, byteobjectarray4, 1, comparator));

    Short[][] shortobjectarray1 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
    Short[][] shortobjectarray2 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
    Short[][] shortobjectarray3 = { { (short) 0, (short) 2 } };
    Short[][] shortobjectarray4 = { { (short) 0, (short) -1, (short) 1 },
        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) } };
    assertEquals(0, compare(shortobjectarray1, shortobjectarray1.length, 
        shortobjectarray2, shortobjectarray2.length, comparator));
    assertEquals(3, compare(shortobjectarray3, shortobjectarray3.length, 
        shortobjectarray2, shortobjectarray2.length, comparator));
    assertEquals(-3, compare(shortobjectarray2, shortobjectarray2.length, 
        shortobjectarray3, shortobjectarray3.length, comparator));
    assertEquals(-1, compare(shortobjectarray4, shortobjectarray4.length,
        shortobjectarray2, shortobjectarray2.length, comparator));
    assertEquals(1, compare(shortobjectarray2, shortobjectarray2.length, 
        shortobjectarray4, shortobjectarray4.length, comparator));
    assertEquals(0, compare(shortobjectarray1, 1, shortobjectarray4, 1, comparator));

    Integer[][] integerarray1 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) },
        { (int) (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
    Integer[][] integerarray2 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) },
        { (int) (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
    Integer[][] integerarray3 = { { (int) 0, (int) 3 } };
    Integer[][] integerarray4 = { { (int) 0, (int) -1, (int) 1 },
        { Integer.MIN_VALUE, (int) (Integer.MIN_VALUE / 2) } };
    assertEquals(0, compare(integerarray1, integerarray1.length, 
        integerarray2, integerarray2.length, comparator));
    assertEquals(4, compare(integerarray3, integerarray3.length, 
        integerarray2, integerarray2.length, comparator));
    assertEquals(-4, compare(integerarray2, integerarray2.length, 
        integerarray3, integerarray3.length, comparator));
    assertEquals(-1, compare(integerarray4, integerarray4.length, 
        integerarray2, integerarray2.length, comparator));
    assertEquals(1, compare(integerarray2, integerarray2.length, 
        integerarray4, integerarray4.length, comparator));
    assertEquals(0, compare(integerarray1, 1, integerarray4, 1, comparator));

    Long[][] longobjectarray1 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) },
        { (long) (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
    Long[][] longobjectarray2 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) },
        { (long) (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
    Long[][] longobjectarray3 = { { (long) 0, (long) 2 } };
    Long[][] longobjectarray4 = { { (long) 0, (long) -1, (long) 1 },
        { Long.MIN_VALUE, (long) (Long.MIN_VALUE / 2) } };
    assertEquals(0, compare(longobjectarray1, longobjectarray1.length, 
        longobjectarray2, longobjectarray2.length, comparator));
    assertEquals(1, compare(longobjectarray3, longobjectarray3.length, 
        longobjectarray2, longobjectarray2.length, comparator));
    assertEquals(-1, compare(longobjectarray2, longobjectarray2.length, 
        longobjectarray3, longobjectarray3.length, comparator));
    assertEquals(-1, compare(longobjectarray4, longobjectarray4.length, 
        longobjectarray2, longobjectarray2.length, comparator));
    assertEquals(1, compare(longobjectarray2, longobjectarray2.length, 
        longobjectarray4, longobjectarray4.length, comparator));
    assertEquals(0, compare(longobjectarray1, 1, longobjectarray4, 1, comparator));

    Float[][] floatobjectarray1 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) },
        { (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
    Float[][] floatobjectarray2 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) },
        { (float) (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
    Float[][] floatobjectarray3 = { { (float) 0, (float) 2 } };
    Float[][] floatobjectarray4 = { { (float) 0, (float) -1, (float) 1 },
        { Float.MIN_VALUE, (float) (Float.MIN_VALUE / 2) } };
    assertEquals(0, compare(floatobjectarray1, floatobjectarray1.length, 
        floatobjectarray2, floatobjectarray2.length, comparator));
    assertEquals(1, compare(floatobjectarray3, floatobjectarray3.length, 
        floatobjectarray2, floatobjectarray2.length, comparator));
    assertEquals(-1, compare(floatobjectarray2, floatobjectarray2.length, 
        floatobjectarray3, floatobjectarray3.length, comparator));
    assertEquals(-1, compare(floatobjectarray4, floatobjectarray4.length, 
        floatobjectarray2, floatobjectarray2.length, comparator));
    assertEquals(1, compare(floatobjectarray2, floatobjectarray2.length, 
        floatobjectarray4, floatobjectarray4.length, comparator));
    assertEquals(0, compare(floatobjectarray1, 1, floatobjectarray4, 1, comparator));

    Double[][] doubleobjectarray1 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE },
        { (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
    Double[][] doubleobjectarray2 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE },
        { (double) (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
    Double[][] doubleobjectarray3 = { { (double) 0, (double) 2 } };
    Double[][] doubleobjectarray4 = { { (double) 0, (double) -1, (double) 1 },
        { (double) (Double.MIN_VALUE / 2), Double.MIN_VALUE } };
    assertEquals(0, compare(doubleobjectarray1, doubleobjectarray1.length, 
        doubleobjectarray2, doubleobjectarray2.length, comparator));
    assertEquals(1, compare(doubleobjectarray3, doubleobjectarray3.length, 
        doubleobjectarray2, doubleobjectarray2.length, comparator));
    assertEquals(-1, compare(doubleobjectarray2, doubleobjectarray2.length, 
        doubleobjectarray3, doubleobjectarray3.length, comparator));
    assertEquals(-1, compare(doubleobjectarray4, doubleobjectarray4.length, 
        doubleobjectarray2, doubleobjectarray2.length, comparator));
    assertEquals(1, compare(doubleobjectarray2, doubleobjectarray2.length, 
        doubleobjectarray4, doubleobjectarray4.length, comparator));
    assertEquals(0, compare(doubleobjectarray1, 1, doubleobjectarray4, 1, comparator));

    Class<?>[][] classarray1 = { { TestClassA.class }, { TestClassA.class } };
    Class<?>[][] classarray2 = { { TestClassA.class }, { TestClassA.class } };
    Class<?>[][] classarray3 = { { TestClassB.class }, { TestClassB.class } };
    Class<?>[][] classarray4 = { { TestClassA.class }, { TestClassA.class },
        { TestClassA.class }};
    assertEquals(0, compare(classarray1, classarray1.length, classarray2, 
        classarray2.length, comparator));
    assertEquals(1, compare(classarray3, classarray3.length, classarray2, 
        classarray2.length, comparator));
    assertEquals(-1, compare(classarray2, classarray2.length, classarray3,
        classarray3.length, comparator));
    assertEquals(1, compare(classarray4, classarray4.length, classarray2, 
        classarray2.length, comparator));
    assertEquals(-1, compare(classarray2, classarray2.length, classarray4,
        classarray4.length, comparator));
    assertEquals(0, compare(classarray1, 1, classarray4, 1, comparator));

    Date datea = new Date();
    datea.setTime((long) 123456);
    Date dateb = new Date();
    dateb.setTime((long) 7891011);
    Date datec = new Date();
    datec.setTime((long) 654321);
    Date[][] datearray1 = { {datea, dateb}, {datec} };
    Date[][] datearray2 = { {datea, dateb}, {datec} };
    Date[][] datearray3 = { {datec, datea}, {dateb} };
    Date[][] datearray4 = { {datea, dateb}};
    assertEquals(0, compare(datearray1, datearray1.length, datearray2, 
        datearray2.length, comparator));
    assertEquals(1, compare(datearray3, datearray3.length, datearray2, 
        datearray2.length, comparator));
    assertEquals(-1, compare(datearray2, datearray2.length, datearray3, 
        datearray3.length, comparator));
    assertEquals(-1, compare(datearray4, datearray4.length, datearray2, 
        datearray2.length, comparator));
    assertEquals(1, compare(datearray2, datearray2.length, datearray4,
        datearray4.length, comparator));
    assertEquals(0, compare(datearray1, 1, datearray4, 1, comparator));

    BigInteger[][] bigintegerarray1 = { {new BigInteger(bytearray1[0])},
        {new BigInteger(bytearray3[0]) }};
    BigInteger[][] bigintegerarray2 = { {new BigInteger(bytearray2[0])},
        {new BigInteger(bytearray3[0]) }};
    BigInteger[][] bigintegerarray3 = { {new BigInteger(bytearray3[0]) }};
    BigInteger[][] bigintegerarray4 = { {new BigInteger(bytearray2[0]) }};
    assertEquals(0, compare(bigintegerarray1, bigintegerarray1.length, 
        bigintegerarray2, bigintegerarray2.length, comparator));
    assertEquals(-1, compare(bigintegerarray3, bigintegerarray3.length, 
        bigintegerarray2, bigintegerarray2.length, comparator));
    assertEquals(1, compare(bigintegerarray2, bigintegerarray2.length, 
        bigintegerarray3, bigintegerarray3.length, comparator));
    assertEquals(-1, compare(bigintegerarray4, bigintegerarray4.length, 
        bigintegerarray2, bigintegerarray2.length, comparator));
    assertEquals(1, compare(bigintegerarray2, bigintegerarray2.length, 
        bigintegerarray4, bigintegerarray4.length, comparator));
    assertEquals(0, compare(bigintegerarray1, 1, bigintegerarray4, 1, comparator));

    BigDecimal[][] bigdecimalarray1 = { {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE) }};
    BigDecimal[][] bigdecimalarray2 = { {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE) }};
    BigDecimal[][] bigdecimalarray3 = { {new BigDecimal(0), new BigDecimal(-1)}};
    BigDecimal[][] bigdecimalarray4 = { {new BigDecimal(Integer.MIN_VALUE) }};
    assertEquals(0, compare(bigdecimalarray1, bigdecimalarray1.length, 
        bigdecimalarray2, bigdecimalarray2.length, comparator));
    assertEquals(-1, compare(bigdecimalarray2, bigdecimalarray2.length, 
        bigdecimalarray3, bigdecimalarray3.length, comparator));
    assertEquals(1, compare(bigdecimalarray3, bigdecimalarray3.length, 
        bigdecimalarray2, bigdecimalarray2.length, comparator));
    assertEquals(1, compare(bigdecimalarray2, bigdecimalarray2.length, 
        bigdecimalarray4, bigdecimalarray4.length, comparator));
    assertEquals(-1, compare(bigdecimalarray4, bigdecimalarray4.length, 
        bigdecimalarray2, bigdecimalarray2.length, comparator));
    assertEquals(0, compare(bigdecimalarray1, 1, bigdecimalarray4, 1, comparator));

    Boolean[][][] objectarray1 = { { {true}, {false} }, { {true, true }},
        { {false, false}, {true }} };
    Boolean[][][] objectarray2 = { { {true}, {false} }, { {true, true }},
        { {false, false}, {true }} };
    Boolean[][][] objectarray3 = { {{ true}, {false} }, { {false, true }} };
    Boolean[][][] objectarray4 = { { {true}, {false} }, { {true, true }} };
    assertEquals(0, compare(objectarray1, objectarray1.length, objectarray2, 
        objectarray2.length, comparator));
    assertEquals(-1, compare(objectarray3, objectarray3.length, objectarray2, 
        objectarray2.length, comparator));
    assertEquals(1, compare(objectarray2, objectarray2.length, objectarray3, 
        objectarray3.length, comparator));
    assertEquals(-1, compare(objectarray4, objectarray4.length, objectarray2, 
        objectarray2.length, comparator));
    assertEquals(1, compare(objectarray2, objectarray2.length, objectarray4, 
        objectarray4.length, comparator));
    assertEquals(0, compare(objectarray1, 1, objectarray4, 1, comparator));
  }

  @Test
  public void testBooleanCollection() {
    ArrayBooleanList col1 = new ArrayBooleanList();
    ArrayBooleanList col2 = new ArrayBooleanList();
    col1.add(true);
    col2.add(true);
    col1.add(false);
    col2.add(false);
    
    assertEquals(0, compare(col1, col2));
    col2.add(true);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col1.add(false);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col2 = null;
    assertEquals(1, compare(col1, col2));
    assertEquals(-1, compare(col2, col1));
    col1 = null;
    assertEquals(0, compare(col1, col2));
  }

  @Test
  public void testCharCollection() {
    ArrayCharList col1 = new ArrayCharList();
    ArrayCharList col2 = new ArrayCharList();
    col1.add('a');
    col2.add('a');
    col1.add('b');
    col2.add('b');
    
    assertEquals(0, compare(col1, col2));
    col2.add('c');
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col1.add('z');
    assertEquals(23, compare(col1, col2));
    assertEquals(-23, compare(col2, col1));
    col2 = null;
    assertEquals(1, compare(col1, col2));
    assertEquals(-1, compare(col2, col1));
    col1 = null;
    assertEquals(0, compare(col1, col2));
  }

  @Test
  public void testByteCollection() {
    ArrayByteList col1 = new ArrayByteList();
    ArrayByteList col2 = new ArrayByteList();
    col1.add((byte) 2);
    col2.add((byte) 2);
    col1.add((byte) 31);
    col2.add((byte) 31);
    
    assertEquals(0, compare(col1, col2));
    col2.add((byte) 60);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col1.add((byte) 100);
    assertEquals(40, compare(col1, col2));
    assertEquals(-40, compare(col2, col1));
    col2 = null;
    assertEquals(1, compare(col1, col2));
    assertEquals(-1, compare(col2, col1));
    col1 = null;
    assertEquals(0, compare(col1, col2));
  }

  @Test
  public void testShortCollection() {
    ArrayShortList col1 = new ArrayShortList();
    ArrayShortList col2 = new ArrayShortList();
    col1.add((short) 2);
    col2.add((short) 2);
    col1.add((short) 31);
    col2.add((short) 31);
    
    assertEquals(0, compare(col1, col2));
    col2.add((short) 30);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col1.add((short) 70);
    assertEquals(40, compare(col1, col2));
    assertEquals(-40, compare(col2, col1));
    col2 = null;
    assertEquals(1, compare(col1, col2));
    assertEquals(-1, compare(col2, col1));
    col1 = null;
    assertEquals(0, compare(col1, col2));
  }

  @Test
  public void testIntCollection() {
    ArrayIntList col1 = new ArrayIntList();
    ArrayIntList col2 = new ArrayIntList();
    col1.add( 2);
    col2.add(2);
    col1.add(31);
    col2.add(31);
    
    assertEquals(0, compare(col1, col2));
    col2.add(30);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col1.add(100);
    assertEquals(70, compare(col1, col2));
    assertEquals(-70, compare(col2, col1));
    col2 = null;
    col1.compareTo(col2);
    assertEquals(1, compare(col1, col2));
    assertEquals(-1, compare(col2, col1));
    col1 = null;
    assertEquals(0, compare(col1, col2));
  }

  @Test
  public void testLongCollection() {
    ArrayLongList col1 = new ArrayLongList();
    ArrayLongList col2 = new ArrayLongList();
    col1.add((long) 2);
    col2.add((long) 2);
    col1.add((long) 31);
    col2.add((long) 31);
    
    assertEquals(0, compare(col1, col2));
    col2.add((long) 100);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col1.add((long) 50);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col2 = null;
    col1.compareTo(col2);
    assertEquals(1, compare(col1, col2));
    assertEquals(-1, compare(col2, col1));
    col1 = null;
    assertEquals(0, compare(col1, col2));
  }

  @Test
  public void testFloatCollection() {
    ArrayFloatList col1 = new ArrayFloatList();
    ArrayFloatList col2 = new ArrayFloatList();
    col1.add((float) 2);
    col2.add((float) 2);
    col1.add((float) 31);
    col2.add((float) 31);
    
    assertEquals(0, compare(col1, col2));
    col2.add((float) 100);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col1.add((float) 50);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col2 = null;
    col1.compareTo(col2);
    assertEquals(1, compare(col1, col2));
    assertEquals(-1, compare(col2, col1));
    col1 = null;
    assertEquals(0, compare(col1, col2));
  }

  @Test
  public void testDoubleCollection() {
    ArrayDoubleList col1 = new ArrayDoubleList();
    ArrayDoubleList col2 = new ArrayDoubleList();
    col1.add((double) 2);
    col2.add((double) 2);
    col1.add((double) 31);
    col2.add((double) 31);
    
    assertEquals(0, compare(col1, col2));
    col2.add((double) 100);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col1.add((double) 50);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col2 = null;
    col1.compareTo(col2);
    assertEquals(1, compare(col1, col2));
    assertEquals(-1, compare(col2, col1));
    col1 = null;
    assertEquals(0, compare(col1, col2));
  }

  @Test
  public void testCollectionOfT() {
    // fail("Not yet implemented");
  }

  @Test
  public void testCollectionOfTDouble() {
    // fail("Not yet implemented");
  }

  @Test
  public void testCollectionOfTCollectionOfTComparatorOfQ() {
    // fail
  }

}
