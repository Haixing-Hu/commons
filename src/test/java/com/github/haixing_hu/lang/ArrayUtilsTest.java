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

import java.util.Date;

import org.junit.Test;

import com.github.haixing_hu.lang.ArrayUtils;
import com.github.haixing_hu.lang.Equality;
import com.github.haixing_hu.lang.StringUtils;

import static org.junit.Assert.*;

/**
 * The unit test for the Arrays class.
 *
 * @author Haixing Hu
 */
public class ArrayUtilsTest {

  @Test
  public void testToString() {
    assertEquals("{}", ArrayUtils.toString(null));
    assertEquals("{}", ArrayUtils.toString(new Object[0]));
    assertEquals("{}", ArrayUtils.toString(new String[0]));
    assertEquals("{<null>}", ArrayUtils.toString(new String[] { null }));
    assertEquals("{pink,blue}", ArrayUtils.toString(new String[] { "pink", "blue" }));

    assertEquals("<empty>", ArrayUtils.toString(null, "<empty>"));
    assertEquals("{}", ArrayUtils.toString(new Object[0], "<empty>"));
    assertEquals("{}", ArrayUtils.toString(new String[0], "<empty>"));
    assertEquals("{<null>}", ArrayUtils.toString(new String[] { null }, "<empty>"));
    assertEquals("{pink,blue}", ArrayUtils.toString(
        new String[] { "pink", "blue" }, "<empty>"));
  }

  @Test
  public void testHashCode() {
    final long[][] array1 = new long[][] { { 2, 5 }, { 4, 5 } };
    final long[][] array2 = new long[][] { { 2, 5 }, { 4, 6 } };
    assertEquals(true, ArrayUtils.hashCode(array1) == ArrayUtils.hashCode(array1));
    assertEquals(false, ArrayUtils.hashCode(array1) == ArrayUtils.hashCode(array2));

    final Object[] array3 = new Object[] { new String(new char[] { 'A', 'B' }) };
    final Object[] array4 = new Object[] { "AB" };
    assertEquals(true, ArrayUtils.hashCode(array3) == ArrayUtils.hashCode(array3));
    assertEquals(true, ArrayUtils.hashCode(array3) == ArrayUtils.hashCode(array4));
  }

//  private void assertArraysEqual(Object array1, Object array2, Object array3) {
//    assertEquals(true, Equality.equals(array1, array1));
//    assertEquals(true, Equality.equals(array2, array2));
//    assertEquals(true, Equality.equals(array3, array3));
//    assertEquals(false, Equality.equals(array1, array2));
//    assertEquals(false, Equality.equals(array2, array1));
//    assertEquals(false, Equality.equals(array1, array3));
//    assertEquals(false, Equality.equals(array3, array1));
//    assertEquals(false, Equality.equals(array1, array2));
//    assertEquals(false, Equality.equals(array2, array1));
//  }

//  @Test
//  public void testToMap() {
//    Map map = Arrays.toMap(new String[][] { { "foo", "bar" },{ "hello", "world" } });
//
//    assertEquals("bar", map.get("foo"));
//    assertEquals("world", map.get("hello"));
//
//    assertNull(Arrays.toMap(null));
//    try {
//      Arrays.toMap(new String[][] { { "foo", "bar" }, { "short" } });
//      fail("exception expected");
//    } catch (IllegalArgumentException ex) {
//    }
//    try {
//      Arrays
//          .toMap(new Object[] { new Object[] { "foo", "bar" }, "illegal type" });
//      fail("exception expected");
//    } catch (IllegalArgumentException ex) {
//    }
//    try {
//      Arrays.toMap(new Object[] { new Object[] { "foo", "bar" }, null });
//      fail("exception expected");
//    } catch (IllegalArgumentException ex) {
//    }
//
//    map = Arrays.toMap(new Object[] { new Map.Entry() {
//      public Object getKey() {
//        return "foo";
//      }
//
//      public Object getValue() {
//        return "bar";
//      }
//
//      public Object setValue(Object value) {
//        throw new UnsupportedOperationException();
//      }
//
//      public boolean equals(Object o) {
//        throw new UnsupportedOperationException();
//      }
//
//      public int hashCode() {
//        throw new UnsupportedOperationException();
//      }
//    } });
//    assertEquals("bar", map.get("foo"));
//  }

  @Test
  public void testSubarrayObject() {
    final Object[] nullArray = null;
    final Object[] objectArray = { "a", "b", "c", "d", "e", "f" };
    Object[] subArray = null;

    subArray = ArrayUtils.subarray(objectArray, 0, 4);
    assertEquals("0 start, mid end", "abcd",
        StringUtils.join(null, subArray));

    subArray = ArrayUtils.subarray(objectArray, 0, objectArray.length);
    assertEquals("0 start, length end", "abcdef",
        StringUtils.join(null, subArray));

    subArray = ArrayUtils.subarray(objectArray, 1, 4);
    assertEquals("mid start, mid end", "bcd",
        StringUtils.join(null, subArray));

    subArray = ArrayUtils.subarray(objectArray, 1, objectArray.length);
    assertEquals("mid start, length end", "bcdef",
        StringUtils.join(null, subArray));

    subArray = ArrayUtils.subarray(nullArray, 0, 3);
    assertNull("null input", subArray);

    subArray = ArrayUtils.subarray(ArrayUtils.EMPTY_OBJECT_ARRAY, 1, 2);
    assertEquals("empty array", "",
        StringUtils.join(null, subArray));

    subArray = ArrayUtils.subarray(objectArray, 4, 2);
    assertEquals("start > end", "",
        StringUtils.join(null, subArray));

    subArray = ArrayUtils.subarray(objectArray, 3, 3);
    assertEquals("start == end", "",
        StringUtils.join(null, subArray));

    subArray = ArrayUtils.subarray(objectArray, -2, 4);
    assertEquals("start undershoot, normal end", "abcd",
        StringUtils.join(null, subArray));

    subArray = ArrayUtils.subarray(objectArray, 33, 4);
    assertEquals("start overshoot, any end", "",
        StringUtils.join(null, subArray));

    subArray = ArrayUtils.subarray(objectArray, 2, 33);
    assertEquals("normal start, end overshoot", "cdef",
        StringUtils.join(null, subArray));

    subArray = ArrayUtils.subarray(objectArray, -2, 12);
    assertEquals("start undershoot, end overshoot", "abcdef",
        StringUtils.join(null, subArray));

    // array type tests
    final Date[] dateArray = { new java.sql.Date(new Date().getTime()), new Date(),
        new Date(), new Date(), new Date() };

    assertSame("Object type", Object.class,
        ArrayUtils.subarray(objectArray, 2, 4).getClass().getComponentType());
    assertSame("java.util.Date type", java.util.Date.class,
        ArrayUtils.subarray(dateArray, 1, 4).getClass().getComponentType());
    assertNotSame("java.sql.Date type", java.sql.Date.class,
        ArrayUtils.subarray(dateArray, 1, 4).getClass().getComponentType());
//    try {
//      @SuppressWarnings("unused")
//      final Object dummy = ArrayUtils.subarray(dateArray, 1, 3);
//      fail("Invalid downcast");
//    } catch (final ClassCastException e) {
//    }
  }

  @Test
  public void testSubarrayLong() {
    final long[] nullArray = null;
    final long[] array = { 999910, 999911, 999912, 999913, 999914, 999915 };
    final long[] leftSubarray = { 999910, 999911, 999912, 999913 };
    final long[] midSubarray = { 999911, 999912, 999913, 999914 };
    final long[] rightSubarray = { 999912, 999913, 999914, 999915 };

    assertTrue("0 start, mid end",
        Equality.equals(leftSubarray, ArrayUtils.subarray(array, 0, 4)));

    assertTrue("0 start, length end",
        Equality.equals(array, ArrayUtils.subarray(array, 0, array.length)));

    assertTrue("mid start, mid end",
        Equality.equals(midSubarray, ArrayUtils.subarray(array, 1, 5)));

    assertTrue("mid start, length end",
        Equality.equals(rightSubarray, ArrayUtils.subarray(array, 2, array.length)));

    assertNull("null input",
        ArrayUtils.subarray(nullArray, 0, 3));

    assertEquals("empty array",
        ArrayUtils.EMPTY_LONG_ARRAY,
        ArrayUtils.subarray(ArrayUtils.EMPTY_LONG_ARRAY, 1, 2));

    assertEquals("start > end",
        ArrayUtils.EMPTY_LONG_ARRAY,
        ArrayUtils.subarray(array, 4, 2));

    assertEquals("start == end",
        ArrayUtils.EMPTY_LONG_ARRAY,
        ArrayUtils.subarray(array, 3, 3));

    assertTrue("start undershoot, normal end",
        Equality.equals(leftSubarray,
        ArrayUtils.subarray(array, -2, 4)));

    assertEquals("start overshoot, any end",
        ArrayUtils.EMPTY_LONG_ARRAY,
        ArrayUtils.subarray(array, 33, 4));

    assertTrue("normal start, end overshoot",
        Equality.equals(rightSubarray,
        ArrayUtils.subarray(array, 2, 33)));

    assertTrue("start undershoot, end overshoot",
        Equality.equals(array, ArrayUtils.subarray(array, -2, 12)));

    // empty-return tests

    assertSame("empty array, object test",
        ArrayUtils.EMPTY_LONG_ARRAY,
        ArrayUtils.subarray(ArrayUtils.EMPTY_LONG_ARRAY, 1, 2));

    assertSame("start > end, object test",
        ArrayUtils.EMPTY_LONG_ARRAY,
        ArrayUtils.subarray(array, 4, 1));

    assertSame("start == end, object test",
        ArrayUtils.EMPTY_LONG_ARRAY,
        ArrayUtils.subarray(array, 3, 3));

    assertSame("start overshoot, any end, object test",
        ArrayUtils.EMPTY_LONG_ARRAY,
        ArrayUtils.subarray(array, 8733, 4));

    // array type tests

    assertSame("long type", long.class,
        ArrayUtils.subarray(array, 2, 4).getClass().getComponentType());

  }

  @Test
  public void testSubarrayInt() {
    final int[] nullArray = null;
    final int[] array = { 10, 11, 12, 13, 14, 15 };
    final int[] leftSubarray = { 10, 11, 12, 13 };
    final int[] midSubarray = { 11, 12, 13, 14 };
    final int[] rightSubarray = { 12, 13, 14, 15 };

    assertTrue("0 start, mid end",
        Equality.equals(leftSubarray, ArrayUtils.subarray(array, 0, 4)));

    assertTrue("0 start, length end",
        Equality.equals(array, ArrayUtils.subarray(array, 0, array.length)));

    assertTrue("mid start, mid end",
        Equality.equals(midSubarray, ArrayUtils.subarray(array, 1, 5)));

    assertTrue("mid start, length end",
        Equality.equals(rightSubarray, ArrayUtils.subarray(array, 2, array.length)));

    assertNull("null input",
        ArrayUtils.subarray(nullArray, 0, 3));

    assertEquals("empty array",
        ArrayUtils.EMPTY_INT_ARRAY,
        ArrayUtils.subarray(ArrayUtils.EMPTY_INT_ARRAY, 1, 2));

    assertEquals("start > end",
        ArrayUtils.EMPTY_INT_ARRAY,
        ArrayUtils.subarray(array, 4, 2));

    assertEquals("start == end",
        ArrayUtils.EMPTY_INT_ARRAY,
        ArrayUtils.subarray(array, 3, 3));

    assertTrue("start undershoot, normal end",
        Equality.equals(leftSubarray, ArrayUtils.subarray(array, -2, 4)));

    assertEquals("start overshoot, any end",
        ArrayUtils.EMPTY_INT_ARRAY,
        ArrayUtils.subarray(array, 33, 4));

    assertTrue("normal start, end overshoot",
        Equality.equals(rightSubarray, ArrayUtils.subarray(array, 2, 33)));

    assertTrue("start undershoot, end overshoot",
        Equality.equals(array, ArrayUtils.subarray(array, -2, 12)));

    // empty-return tests

    assertSame("empty array, object test",
        ArrayUtils.EMPTY_INT_ARRAY,
        ArrayUtils.subarray(ArrayUtils.EMPTY_INT_ARRAY, 1, 2));

    assertSame("start > end, object test",
        ArrayUtils.EMPTY_INT_ARRAY,
        ArrayUtils.subarray(array, 4, 1));

    assertSame("start == end, object test",
        ArrayUtils.EMPTY_INT_ARRAY,
        ArrayUtils.subarray(array, 3, 3));

    assertSame("start overshoot, any end, object test",
        ArrayUtils.EMPTY_INT_ARRAY,
        ArrayUtils.subarray(array, 8733, 4));

    // array type tests

    assertSame("int type", int.class,
        ArrayUtils.subarray(array, 2, 4).getClass().getComponentType());

  }

  @Test
  public void testSubarrayShort() {
    final short[] nullArray = null;
    final short[] array = { 10, 11, 12, 13, 14, 15 };
    final short[] leftSubarray = { 10, 11, 12, 13 };
    final short[] midSubarray = { 11, 12, 13, 14 };
    final short[] rightSubarray = { 12, 13, 14, 15 };

    assertTrue("0 start, mid end",
        Equality.equals(leftSubarray, ArrayUtils.subarray(array, 0, 4)));

    assertTrue("0 start, length end",
        Equality.equals(array, ArrayUtils.subarray(array, 0, array.length)));

    assertTrue("mid start, mid end",
        Equality.equals(midSubarray, ArrayUtils.subarray(array, 1, 5)));

    assertTrue("mid start, length end",
        Equality.equals(rightSubarray, ArrayUtils.subarray(array, 2, array.length)));

    assertNull("null input",
        ArrayUtils.subarray(nullArray, 0, 3));

    assertEquals("empty array",
        ArrayUtils.EMPTY_SHORT_ARRAY,
        ArrayUtils.subarray(ArrayUtils.EMPTY_SHORT_ARRAY, 1, 2));

    assertEquals("start > end",
        ArrayUtils.EMPTY_SHORT_ARRAY,
        ArrayUtils.subarray(array, 4, 2));

    assertEquals("start == end",
        ArrayUtils.EMPTY_SHORT_ARRAY,
        ArrayUtils.subarray(array, 3, 3));

    assertTrue("start undershoot, normal end",
        Equality.equals(leftSubarray,
        ArrayUtils.subarray(array, -2, 4)));

    assertEquals("start overshoot, any end",
        ArrayUtils.EMPTY_SHORT_ARRAY,
        ArrayUtils.subarray(array, 33, 4));

    assertTrue("normal start, end overshoot",
        Equality.equals(rightSubarray, ArrayUtils.subarray(array, 2, 33)));

    assertTrue("start undershoot, end overshoot",
        Equality.equals(array, ArrayUtils.subarray(array, -2, 12)));

    // empty-return tests

    assertSame("empty array, object test",
        ArrayUtils.EMPTY_SHORT_ARRAY,
        ArrayUtils.subarray(ArrayUtils.EMPTY_SHORT_ARRAY, 1, 2));

    assertSame("start > end, object test",
        ArrayUtils.EMPTY_SHORT_ARRAY,
        ArrayUtils.subarray(array, 4, 1));

    assertSame("start == end, object test",
        ArrayUtils.EMPTY_SHORT_ARRAY,
        ArrayUtils.subarray(array, 3, 3));

    assertSame("start overshoot, any end, object test",
        ArrayUtils.EMPTY_SHORT_ARRAY,
        ArrayUtils.subarray(array, 8733, 4));

    // array type tests

    assertSame("short type", short.class, ArrayUtils.subarray(array, 2, 4)
        .getClass().getComponentType());

  }

  @Test
  public void testSubarrChar() {
    final char[] nullArray = null;
    final char[] array = { 'a', 'b', 'c', 'd', 'e', 'f' };
    final char[] leftSubarray = { 'a', 'b', 'c', 'd', };
    final char[] midSubarray = { 'b', 'c', 'd', 'e', };
    final char[] rightSubarray = { 'c', 'd', 'e', 'f', };

    assertTrue("0 start, mid end",
        Equality.equals(leftSubarray, ArrayUtils.subarray(array, 0, 4)));

    assertTrue("0 start, length end",
        Equality.equals(array, ArrayUtils.subarray(array, 0, array.length)));

    assertTrue("mid start, mid end",
        Equality.equals(midSubarray, ArrayUtils.subarray(array, 1, 5)));

    assertTrue("mid start, length end",
        Equality.equals(rightSubarray, ArrayUtils.subarray(array, 2, array.length)));

    assertNull("null input", ArrayUtils.subarray(nullArray, 0, 3));

    assertEquals("empty array",
        ArrayUtils.EMPTY_CHAR_ARRAY,
        ArrayUtils.subarray(ArrayUtils.EMPTY_CHAR_ARRAY, 1, 2));

    assertEquals("start > end",
        ArrayUtils.EMPTY_CHAR_ARRAY,
        ArrayUtils.subarray(array, 4, 2));

    assertEquals("start == end",
        ArrayUtils.EMPTY_CHAR_ARRAY,
        ArrayUtils.subarray(array, 3, 3));

    assertTrue("start undershoot, normal end",
        Equality.equals(leftSubarray,
        ArrayUtils.subarray(array, -2, 4)));

    assertEquals("start overshoot, any end",
        ArrayUtils.EMPTY_CHAR_ARRAY,
        ArrayUtils.subarray(array, 33, 4));

    assertTrue("normal start, end overshoot",
        Equality.equals(rightSubarray,
        ArrayUtils.subarray(array, 2, 33)));

    assertTrue("start undershoot, end overshoot",
        Equality.equals(array, ArrayUtils.subarray(array, -2, 12)));

    // empty-return tests

    assertSame("empty array, object test",
        ArrayUtils.EMPTY_CHAR_ARRAY,
        ArrayUtils.subarray(ArrayUtils.EMPTY_CHAR_ARRAY, 1, 2));

    assertSame("start > end, object test",
        ArrayUtils.EMPTY_CHAR_ARRAY,
        ArrayUtils.subarray(array, 4, 1));

    assertSame("start == end, object test",
        ArrayUtils.EMPTY_CHAR_ARRAY,
        ArrayUtils.subarray(array, 3, 3));

    assertSame("start overshoot, any end, object test",
        ArrayUtils.EMPTY_CHAR_ARRAY,
        ArrayUtils.subarray(array, 8733, 4));

    // array type tests

    assertSame("char type", char.class,
        ArrayUtils.subarray(array, 2, 4).getClass().getComponentType());

  }

  @Test
  public void testSubarrayByte() {
    final byte[] nullArray = null;
    final byte[] array = { 10, 11, 12, 13, 14, 15 };
    final byte[] leftSubarray = { 10, 11, 12, 13 };
    final byte[] midSubarray = { 11, 12, 13, 14 };
    final byte[] rightSubarray = { 12, 13, 14, 15 };

    assertTrue("0 start, mid end", Equality.equals(leftSubarray, ArrayUtils
        .subarray(array, 0, 4)));

    assertTrue("0 start, length end", Equality.equals(array, ArrayUtils.subarray(
        array, 0, array.length)));

    assertTrue("mid start, mid end", Equality.equals(midSubarray, ArrayUtils
        .subarray(array, 1, 5)));

    assertTrue("mid start, length end", Equality.equals(rightSubarray, ArrayUtils
        .subarray(array, 2, array.length)));

    assertNull("null input", ArrayUtils.subarray(nullArray, 0, 3));

    assertEquals("empty array", ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.subarray(
        ArrayUtils.EMPTY_BYTE_ARRAY, 1, 2));

    assertEquals("start > end", ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.subarray(array,
        4, 2));

    assertEquals("start == end", ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.subarray(
        array, 3, 3));

    assertTrue("start undershoot, normal end", Equality.equals(leftSubarray,
        ArrayUtils.subarray(array, -2, 4)));

    assertEquals("start overshoot, any end", ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils
        .subarray(array, 33, 4));

    assertTrue("normal start, end overshoot", Equality.equals(rightSubarray,
        ArrayUtils.subarray(array, 2, 33)));

    assertTrue("start undershoot, end overshoot", Equality.equals(array, ArrayUtils
        .subarray(array, -2, 12)));

    // empty-return tests

    assertSame("empty array, object test", ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils
        .subarray(ArrayUtils.EMPTY_BYTE_ARRAY, 1, 2));

    assertSame("start > end, object test", ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils
        .subarray(array, 4, 1));

    assertSame("start == end, object test", ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils
        .subarray(array, 3, 3));

    assertSame("start overshoot, any end, object test",
        ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.subarray(array, 8733, 4));

    // array type tests

    assertSame("byte type", byte.class, ArrayUtils.subarray(array, 2, 4).getClass()
        .getComponentType());

  }

  @Test
  public void testSubarrayDouble() {
    final double[] nullArray = null;
    final double[] array = { 10.123, 11.234, 12.345, 13.456, 14.567, 15.678 };
    final double[] leftSubarray = { 10.123, 11.234, 12.345, 13.456, };
    final double[] midSubarray = { 11.234, 12.345, 13.456, 14.567, };
    final double[] rightSubarray = { 12.345, 13.456, 14.567, 15.678 };

    assertTrue("0 start, mid end", Equality.equals(leftSubarray, ArrayUtils
        .subarray(array, 0, 4)));

    assertTrue("0 start, length end", Equality.equals(array, ArrayUtils.subarray(
        array, 0, array.length)));

    assertTrue("mid start, mid end", Equality.equals(midSubarray, ArrayUtils
        .subarray(array, 1, 5)));

    assertTrue("mid start, length end", Equality.equals(rightSubarray, ArrayUtils
        .subarray(array, 2, array.length)));

    assertNull("null input", ArrayUtils.subarray(nullArray, 0, 3));

    assertEquals("empty array", ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.subarray(
        ArrayUtils.EMPTY_DOUBLE_ARRAY, 1, 2));

    assertEquals("start > end", ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.subarray(
        array, 4, 2));

    assertEquals("start == end", ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.subarray(
        array, 3, 3));

    assertTrue("start undershoot, normal end", Equality.equals(leftSubarray,
        ArrayUtils.subarray(array, -2, 4)));

    assertEquals("start overshoot, any end", ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils
        .subarray(array, 33, 4));

    assertTrue("normal start, end overshoot", Equality.equals(rightSubarray,
        ArrayUtils.subarray(array, 2, 33)));

    assertTrue("start undershoot, end overshoot", Equality.equals(array, ArrayUtils
        .subarray(array, -2, 12)));

    // empty-return tests

    assertSame("empty array, object test", ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils
        .subarray(ArrayUtils.EMPTY_DOUBLE_ARRAY, 1, 2));

    assertSame("start > end, object test", ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils
        .subarray(array, 4, 1));

    assertSame("start == end, object test", ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils
        .subarray(array, 3, 3));

    assertSame("start overshoot, any end, object test",
        ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.subarray(array, 8733, 4));

    // array type tests

    assertSame("double type", double.class, ArrayUtils.subarray(array, 2, 4)
        .getClass().getComponentType());

  }

  @Test
  public void testSubarrayFloat() {
    final float[] nullArray = null;
    final float[] array = { 10, 11, 12, 13, 14, 15 };
    final float[] leftSubarray = { 10, 11, 12, 13 };
    final float[] midSubarray = { 11, 12, 13, 14 };
    final float[] rightSubarray = { 12, 13, 14, 15 };

    assertTrue("0 start, mid end", Equality.equals(leftSubarray, ArrayUtils
        .subarray(array, 0, 4)));

    assertTrue("0 start, length end", Equality.equals(array, ArrayUtils.subarray(
        array, 0, array.length)));

    assertTrue("mid start, mid end", Equality.equals(midSubarray, ArrayUtils
        .subarray(array, 1, 5)));

    assertTrue("mid start, length end", Equality.equals(rightSubarray, ArrayUtils
        .subarray(array, 2, array.length)));

    assertNull("null input", ArrayUtils.subarray(nullArray, 0, 3));

    assertEquals("empty array", ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.subarray(
        ArrayUtils.EMPTY_FLOAT_ARRAY, 1, 2));

    assertEquals("start > end", ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.subarray(
        array, 4, 2));

    assertEquals("start == end", ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.subarray(
        array, 3, 3));

    assertTrue("start undershoot, normal end", Equality.equals(leftSubarray,
        ArrayUtils.subarray(array, -2, 4)));

    assertEquals("start overshoot, any end", ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils
        .subarray(array, 33, 4));

    assertTrue("normal start, end overshoot", Equality.equals(rightSubarray,
        ArrayUtils.subarray(array, 2, 33)));

    assertTrue("start undershoot, end overshoot", Equality.equals(array, ArrayUtils
        .subarray(array, -2, 12)));

    // empty-return tests

    assertSame("empty array, object test", ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils
        .subarray(ArrayUtils.EMPTY_FLOAT_ARRAY, 1, 2));

    assertSame("start > end, object test", ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils
        .subarray(array, 4, 1));

    assertSame("start == end, object test", ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils
        .subarray(array, 3, 3));

    assertSame("start overshoot, any end, object test",
        ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.subarray(array, 8733, 4));

    // array type tests

    assertSame("float type", float.class, ArrayUtils.subarray(array, 2, 4)
        .getClass().getComponentType());

  }

  @Test
  public void testSubarrayBoolean() {
    final boolean[] nullArray = null;
    final boolean[] array = { true, true, false, true, false, true };
    final boolean[] leftSubarray = { true, true, false, true };
    final boolean[] midSubarray = { true, false, true, false };
    final boolean[] rightSubarray = { false, true, false, true };

    assertTrue("0 start, mid end", Equality.equals(leftSubarray, ArrayUtils
        .subarray(array, 0, 4)));

    assertTrue("0 start, length end", Equality.equals(array, ArrayUtils.subarray(
        array, 0, array.length)));

    assertTrue("mid start, mid end", Equality.equals(midSubarray, ArrayUtils
        .subarray(array, 1, 5)));

    assertTrue("mid start, length end", Equality.equals(rightSubarray, ArrayUtils
        .subarray(array, 2, array.length)));

    assertNull("null input", ArrayUtils.subarray(nullArray, 0, 3));

    assertEquals("empty array", ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.subarray(
        ArrayUtils.EMPTY_BOOLEAN_ARRAY, 1, 2));

    assertEquals("start > end", ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.subarray(
        array, 4, 2));

    assertEquals("start == end", ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.subarray(
        array, 3, 3));

    assertTrue("start undershoot, normal end", Equality.equals(leftSubarray,
        ArrayUtils.subarray(array, -2, 4)));

    assertEquals("start overshoot, any end", ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils
        .subarray(array, 33, 4));

    assertTrue("normal start, end overshoot", Equality.equals(rightSubarray,
        ArrayUtils.subarray(array, 2, 33)));

    assertTrue("start undershoot, end overshoot", Equality.equals(array, ArrayUtils
        .subarray(array, -2, 12)));

    // empty-return tests

    assertSame("empty array, object test", ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils
        .subarray(ArrayUtils.EMPTY_BOOLEAN_ARRAY, 1, 2));

    assertSame("start > end, object test", ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils
        .subarray(array, 4, 1));

    assertSame("start == end, object test", ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils
        .subarray(array, 3, 3));

    assertSame("start overshoot, any end, object test",
        ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.subarray(array, 8733, 4));

    // array type tests

    assertSame("boolean type", boolean.class, ArrayUtils.subarray(array, 2, 4)
        .getClass().getComponentType());

  }

  @Test
  public void testSameLength() {
    final Object[] nullArray = null;
    final Object[] emptyArray = new Object[0];
    final Object[] oneArray = new Object[] { "pick" };
    final Object[] twoArray = new Object[] { "pick", "stick" };

    assertEquals(true, ArrayUtils.isSameLength(nullArray, nullArray));
    assertEquals(true, ArrayUtils.isSameLength(nullArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(nullArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(nullArray, twoArray));

    assertEquals(true, ArrayUtils.isSameLength(emptyArray, nullArray));
    assertEquals(true, ArrayUtils.isSameLength(emptyArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(emptyArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(emptyArray, twoArray));

    assertEquals(false, ArrayUtils.isSameLength(oneArray, nullArray));
    assertEquals(false, ArrayUtils.isSameLength(oneArray, emptyArray));
    assertEquals(true, ArrayUtils.isSameLength(oneArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(oneArray, twoArray));

    assertEquals(false, ArrayUtils.isSameLength(twoArray, nullArray));
    assertEquals(false, ArrayUtils.isSameLength(twoArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(twoArray, oneArray));
    assertEquals(true, ArrayUtils.isSameLength(twoArray, twoArray));
  }

  @Test
  public void testSameLengthBoolean() {
    final boolean[] nullArray = null;
    final boolean[] emptyArray = new boolean[0];
    final boolean[] oneArray = new boolean[] { true };
    final boolean[] twoArray = new boolean[] { true, false };

    assertEquals(true, ArrayUtils.isSameLength(nullArray, nullArray));
    assertEquals(true, ArrayUtils.isSameLength(nullArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(nullArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(nullArray, twoArray));

    assertEquals(true, ArrayUtils.isSameLength(emptyArray, nullArray));
    assertEquals(true, ArrayUtils.isSameLength(emptyArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(emptyArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(emptyArray, twoArray));

    assertEquals(false, ArrayUtils.isSameLength(oneArray, nullArray));
    assertEquals(false, ArrayUtils.isSameLength(oneArray, emptyArray));
    assertEquals(true, ArrayUtils.isSameLength(oneArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(oneArray, twoArray));

    assertEquals(false, ArrayUtils.isSameLength(twoArray, nullArray));
    assertEquals(false, ArrayUtils.isSameLength(twoArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(twoArray, oneArray));
    assertEquals(true, ArrayUtils.isSameLength(twoArray, twoArray));
  }

  @Test
  public void testSameLengthLong() {
    final long[] nullArray = null;
    final long[] emptyArray = new long[0];
    final long[] oneArray = new long[] { 0L };
    final long[] twoArray = new long[] { 0L, 76L };

    assertEquals(true, ArrayUtils.isSameLength(nullArray, nullArray));
    assertEquals(true, ArrayUtils.isSameLength(nullArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(nullArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(nullArray, twoArray));

    assertEquals(true, ArrayUtils.isSameLength(emptyArray, nullArray));
    assertEquals(true, ArrayUtils.isSameLength(emptyArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(emptyArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(emptyArray, twoArray));

    assertEquals(false, ArrayUtils.isSameLength(oneArray, nullArray));
    assertEquals(false, ArrayUtils.isSameLength(oneArray, emptyArray));
    assertEquals(true, ArrayUtils.isSameLength(oneArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(oneArray, twoArray));

    assertEquals(false, ArrayUtils.isSameLength(twoArray, nullArray));
    assertEquals(false, ArrayUtils.isSameLength(twoArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(twoArray, oneArray));
    assertEquals(true, ArrayUtils.isSameLength(twoArray, twoArray));
  }

  @Test
  public void testSameLengthInt() {
    final int[] nullArray = null;
    final int[] emptyArray = new int[0];
    final int[] oneArray = new int[] { 4 };
    final int[] twoArray = new int[] { 5, 7 };

    assertEquals(true, ArrayUtils.isSameLength(nullArray, nullArray));
    assertEquals(true, ArrayUtils.isSameLength(nullArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(nullArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(nullArray, twoArray));

    assertEquals(true, ArrayUtils.isSameLength(emptyArray, nullArray));
    assertEquals(true, ArrayUtils.isSameLength(emptyArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(emptyArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(emptyArray, twoArray));

    assertEquals(false, ArrayUtils.isSameLength(oneArray, nullArray));
    assertEquals(false, ArrayUtils.isSameLength(oneArray, emptyArray));
    assertEquals(true, ArrayUtils.isSameLength(oneArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(oneArray, twoArray));

    assertEquals(false, ArrayUtils.isSameLength(twoArray, nullArray));
    assertEquals(false, ArrayUtils.isSameLength(twoArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(twoArray, oneArray));
    assertEquals(true, ArrayUtils.isSameLength(twoArray, twoArray));
  }

  @Test
  public void testSameLengthShort() {
    final short[] nullArray = null;
    final short[] emptyArray = new short[0];
    final short[] oneArray = new short[] { 4 };
    final short[] twoArray = new short[] { 6, 8 };

    assertEquals(true, ArrayUtils.isSameLength(nullArray, nullArray));
    assertEquals(true, ArrayUtils.isSameLength(nullArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(nullArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(nullArray, twoArray));

    assertEquals(true, ArrayUtils.isSameLength(emptyArray, nullArray));
    assertEquals(true, ArrayUtils.isSameLength(emptyArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(emptyArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(emptyArray, twoArray));

    assertEquals(false, ArrayUtils.isSameLength(oneArray, nullArray));
    assertEquals(false, ArrayUtils.isSameLength(oneArray, emptyArray));
    assertEquals(true, ArrayUtils.isSameLength(oneArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(oneArray, twoArray));

    assertEquals(false, ArrayUtils.isSameLength(twoArray, nullArray));
    assertEquals(false, ArrayUtils.isSameLength(twoArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(twoArray, oneArray));
    assertEquals(true, ArrayUtils.isSameLength(twoArray, twoArray));
  }

  @Test
  public void testSameLengthChar() {
    final char[] nullArray = null;
    final char[] emptyArray = new char[0];
    final char[] oneArray = new char[] { 'f' };
    final char[] twoArray = new char[] { 'd', 't' };

    assertEquals(true, ArrayUtils.isSameLength(nullArray, nullArray));
    assertEquals(true, ArrayUtils.isSameLength(nullArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(nullArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(nullArray, twoArray));

    assertEquals(true, ArrayUtils.isSameLength(emptyArray, nullArray));
    assertEquals(true, ArrayUtils.isSameLength(emptyArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(emptyArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(emptyArray, twoArray));

    assertEquals(false, ArrayUtils.isSameLength(oneArray, nullArray));
    assertEquals(false, ArrayUtils.isSameLength(oneArray, emptyArray));
    assertEquals(true, ArrayUtils.isSameLength(oneArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(oneArray, twoArray));

    assertEquals(false, ArrayUtils.isSameLength(twoArray, nullArray));
    assertEquals(false, ArrayUtils.isSameLength(twoArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(twoArray, oneArray));
    assertEquals(true, ArrayUtils.isSameLength(twoArray, twoArray));
  }

  @Test
  public void testSameLengthByte() {
    final byte[] nullArray = null;
    final byte[] emptyArray = new byte[0];
    final byte[] oneArray = new byte[] { 3 };
    final byte[] twoArray = new byte[] { 4, 6 };

    assertEquals(true, ArrayUtils.isSameLength(nullArray, nullArray));
    assertEquals(true, ArrayUtils.isSameLength(nullArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(nullArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(nullArray, twoArray));

    assertEquals(true, ArrayUtils.isSameLength(emptyArray, nullArray));
    assertEquals(true, ArrayUtils.isSameLength(emptyArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(emptyArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(emptyArray, twoArray));

    assertEquals(false, ArrayUtils.isSameLength(oneArray, nullArray));
    assertEquals(false, ArrayUtils.isSameLength(oneArray, emptyArray));
    assertEquals(true, ArrayUtils.isSameLength(oneArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(oneArray, twoArray));

    assertEquals(false, ArrayUtils.isSameLength(twoArray, nullArray));
    assertEquals(false, ArrayUtils.isSameLength(twoArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(twoArray, oneArray));
    assertEquals(true, ArrayUtils.isSameLength(twoArray, twoArray));
  }

  @Test
  public void testSameLengthDouble() {
    final double[] nullArray = null;
    final double[] emptyArray = new double[0];
    final double[] oneArray = new double[] { 1.3d };
    final double[] twoArray = new double[] { 4.5d, 6.3d };

    assertEquals(true, ArrayUtils.isSameLength(nullArray, nullArray));
    assertEquals(true, ArrayUtils.isSameLength(nullArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(nullArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(nullArray, twoArray));

    assertEquals(true, ArrayUtils.isSameLength(emptyArray, nullArray));
    assertEquals(true, ArrayUtils.isSameLength(emptyArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(emptyArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(emptyArray, twoArray));

    assertEquals(false, ArrayUtils.isSameLength(oneArray, nullArray));
    assertEquals(false, ArrayUtils.isSameLength(oneArray, emptyArray));
    assertEquals(true, ArrayUtils.isSameLength(oneArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(oneArray, twoArray));

    assertEquals(false, ArrayUtils.isSameLength(twoArray, nullArray));
    assertEquals(false, ArrayUtils.isSameLength(twoArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(twoArray, oneArray));
    assertEquals(true, ArrayUtils.isSameLength(twoArray, twoArray));
  }

  @Test
  public void testSameLengthFloat() {
    final float[] nullArray = null;
    final float[] emptyArray = new float[0];
    final float[] oneArray = new float[] { 2.5f };
    final float[] twoArray = new float[] { 6.4f, 5.8f };

    assertEquals(true, ArrayUtils.isSameLength(nullArray, nullArray));
    assertEquals(true, ArrayUtils.isSameLength(nullArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(nullArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(nullArray, twoArray));

    assertEquals(true, ArrayUtils.isSameLength(emptyArray, nullArray));
    assertEquals(true, ArrayUtils.isSameLength(emptyArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(emptyArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(emptyArray, twoArray));

    assertEquals(false, ArrayUtils.isSameLength(oneArray, nullArray));
    assertEquals(false, ArrayUtils.isSameLength(oneArray, emptyArray));
    assertEquals(true, ArrayUtils.isSameLength(oneArray, oneArray));
    assertEquals(false, ArrayUtils.isSameLength(oneArray, twoArray));

    assertEquals(false, ArrayUtils.isSameLength(twoArray, nullArray));
    assertEquals(false, ArrayUtils.isSameLength(twoArray, emptyArray));
    assertEquals(false, ArrayUtils.isSameLength(twoArray, oneArray));
    assertEquals(true, ArrayUtils.isSameLength(twoArray, twoArray));
  }

  @Test
  public void testSameType() {
    try {
      ArrayUtils.isSameType(null, null);
      fail();
    } catch (final IllegalArgumentException ex) {
    }
    try {
      ArrayUtils.isSameType(null, new Object[0]);
      fail();
    } catch (final IllegalArgumentException ex) {
    }
    try {
      ArrayUtils.isSameType(new Object[0], null);
      fail();
    } catch (final IllegalArgumentException ex) {
    }

    assertEquals(true, ArrayUtils.isSameType(new Object[0], new Object[0]));
    assertEquals(false, ArrayUtils.isSameType(new String[0], new Object[0]));
    assertEquals(true, ArrayUtils.isSameType(new String[0][0], new String[0][0]));
    assertEquals(false, ArrayUtils.isSameType(new String[0], new String[0][0]));
    assertEquals(false, ArrayUtils.isSameType(new String[0][0], new String[0]));
  }

  @Test
  public void testReverse() {
    final StringBuilder str1 = new StringBuilder("pick");
    final String str2 = "a";
    final String[] str3 = new String[] { "stick" };
    final String str4 = "up";

    Object[] array = new Object[] { str1, str2, str3 };
    ArrayUtils.reverse(array);
    assertEquals(array[0], str3);
    assertEquals(array[1], str2);
    assertEquals(array[2], str1);

    array = new Object[] { str1, str2, str3, str4 };
    ArrayUtils.reverse(array);
    assertEquals(array[0], str4);
    assertEquals(array[1], str3);
    assertEquals(array[2], str2);
    assertEquals(array[3], str1);

    array = null;
    ArrayUtils.reverse(array);
    assertNull(array);
  }

  @Test
  public void testReverseLong() {
    long[] array = new long[] { 1L, 2L, 3L };
    ArrayUtils.reverse(array);
    assertEquals(array[0], 3L);
    assertEquals(array[1], 2L);
    assertEquals(array[2], 1L);

    array = null;
    ArrayUtils.reverse(array);
    assertNull(array);
  }

  @Test
  public void testReverseInt() {
    int[] array = new int[] { 1, 2, 3 };
    ArrayUtils.reverse(array);
    assertEquals(array[0], 3);
    assertEquals(array[1], 2);
    assertEquals(array[2], 1);

    array = null;
    ArrayUtils.reverse(array);
    assertNull(array);
  }

  @Test
  public void testReverseShort() {
    short[] array = new short[] { 1, 2, 3 };
    ArrayUtils.reverse(array);
    assertEquals(array[0], 3);
    assertEquals(array[1], 2);
    assertEquals(array[2], 1);

    array = null;
    ArrayUtils.reverse(array);
    assertNull(array);
  }

  @Test
  public void testReverseChar() {
    char[] array = new char[] { 'a', 'f', 'C' };
    ArrayUtils.reverse(array);
    assertEquals(array[0], 'C');
    assertEquals(array[1], 'f');
    assertEquals(array[2], 'a');

    array = null;
    ArrayUtils.reverse(array);
    assertNull(array);
  }

  @Test
  public void testReverseByte() {
    byte[] array = new byte[] { 2, 3, 4 };
    ArrayUtils.reverse(array);
    assertEquals(array[0], 4);
    assertEquals(array[1], 3);
    assertEquals(array[2], 2);

    array = null;
    ArrayUtils.reverse(array);
    assertNull(array);
  }

  @Test
  public void testReverseDouble() {
    double[] array = new double[] { 0.3d, 0.4d, 0.5d };
    ArrayUtils.reverse(array);
    assertEquals(array[0], 0.5d, 0.0d);
    assertEquals(array[1], 0.4d, 0.0d);
    assertEquals(array[2], 0.3d, 0.0d);

    array = null;
    ArrayUtils.reverse(array);
    assertNull(array);
  }

  @Test
  public void testReverseFloat() {
    float[] array = new float[] { 0.3f, 0.4f, 0.5f };
    ArrayUtils.reverse(array);
    assertEquals(array[0], 0.5f, 0.0f);
    assertEquals(array[1], 0.4f, 0.0f);
    assertEquals(array[2], 0.3f, 0.0f);

    array = null;
    ArrayUtils.reverse(array);
    assertNull(array);
  }

  @Test
  public void testReverseBoolean() {
    boolean[] array = new boolean[] { false, false, true };
    ArrayUtils.reverse(array);
    assertEquals(array[0], true);
    assertEquals(array[1], false);
    assertEquals(array[2], false);

    array = null;
    ArrayUtils.reverse(array);
    assertNull(array);
  }

  @Test
  public void testIndexOf() {
    final Object[] array = new Object[] { "0", "1", "2", "3", null, "0" };
    assertEquals(-1, ArrayUtils.indexOf(null, null));
    assertEquals(-1, ArrayUtils.indexOf(null, "0"));
    assertEquals(-1, ArrayUtils.indexOf(new Object[0], "0"));
    assertEquals(0, ArrayUtils.indexOf(array, "0"));
    assertEquals(1, ArrayUtils.indexOf(array, "1"));
    assertEquals(2, ArrayUtils.indexOf(array, "2"));
    assertEquals(3, ArrayUtils.indexOf(array, "3"));
    assertEquals(4, ArrayUtils.indexOf(array, null));
    assertEquals(-1, ArrayUtils.indexOf(array, "notInArray"));
  }

  @Test
  public void testIndexOfWithStartIndex() {
    final Object[] array = new Object[] { "0", "1", "2", "3", null, "0" };
    assertEquals(-1, ArrayUtils.indexOf(null, null, 2));
    assertEquals(-1, ArrayUtils.indexOf(new Object[0], "0", 0));
    assertEquals(-1, ArrayUtils.indexOf(null, "0", 2));
    assertEquals(5, ArrayUtils.indexOf(array, "0", 2));
    assertEquals(-1, ArrayUtils.indexOf(array, "1", 2));
    assertEquals(2, ArrayUtils.indexOf(array, "2", 2));
    assertEquals(3, ArrayUtils.indexOf(array, "3", 2));
    assertEquals(4, ArrayUtils.indexOf(array, null, 2));
    assertEquals(-1, ArrayUtils.indexOf(array, "notInArray", 2));

    assertEquals(4, ArrayUtils.indexOf(array, null, -1));
    assertEquals(-1, ArrayUtils.indexOf(array, null, 8));
    assertEquals(-1, ArrayUtils.indexOf(array, "0", 8));
  }

  @Test
  public void testLastIndexOf() {
    final Object[] array = new Object[] { "0", "1", "2", "3", null, "0" };
    assertEquals(-1, ArrayUtils.lastIndexOf(null, null));
    assertEquals(-1, ArrayUtils.lastIndexOf(null, "0"));
    assertEquals(5, ArrayUtils.lastIndexOf(array, "0"));
    assertEquals(1, ArrayUtils.lastIndexOf(array, "1"));
    assertEquals(2, ArrayUtils.lastIndexOf(array, "2"));
    assertEquals(3, ArrayUtils.lastIndexOf(array, "3"));
    assertEquals(4, ArrayUtils.lastIndexOf(array, null));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, "notInArray"));
  }

  @Test
  public void testLastIndexOfWithStartIndex() {
    final Object[] array = new Object[] { "0", "1", "2", "3", null, "0" };
    assertEquals(-1, ArrayUtils.lastIndexOf(null, null, 2));
    assertEquals(-1, ArrayUtils.lastIndexOf(null, "0", 2));
    assertEquals(0, ArrayUtils.lastIndexOf(array, "0", 2));
    assertEquals(1, ArrayUtils.lastIndexOf(array, "1", 2));
    assertEquals(2, ArrayUtils.lastIndexOf(array, "2", 2));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, "3", 2));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, "3", -1));
    assertEquals(4, ArrayUtils.lastIndexOf(array, null, 5));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, null, 2));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, "notInArray", 5));

    assertEquals(-1, ArrayUtils.lastIndexOf(array, null, -1));
    assertEquals(5, ArrayUtils.lastIndexOf(array, "0", 88));
  }

  @Test
  public void testContains() {
    final Object[] array = new Object[] { "0", "1", "2", "3", null, "0" };
    assertEquals(false, ArrayUtils.contains(null, null));
    assertEquals(false, ArrayUtils.contains(null, "1"));
    assertEquals(true, ArrayUtils.contains(array, "0"));
    assertEquals(true, ArrayUtils.contains(array, "1"));
    assertEquals(true, ArrayUtils.contains(array, "2"));
    assertEquals(true, ArrayUtils.contains(array, "3"));
    assertEquals(true, ArrayUtils.contains(array, null));
    assertEquals(false, ArrayUtils.contains(array, "notInArray"));
  }

  @Test
  public void testIndexOfLong() {
    long[] array = null;
    assertEquals(-1, ArrayUtils.indexOf(array, 0));
    array = new long[] { 0, 1, 2, 3, 0 };
    assertEquals(0, ArrayUtils.indexOf(array, 0));
    assertEquals(1, ArrayUtils.indexOf(array, 1));
    assertEquals(2, ArrayUtils.indexOf(array, 2));
    assertEquals(3, ArrayUtils.indexOf(array, 3));
    assertEquals(-1, ArrayUtils.indexOf(array, 99));
  }

  @Test
  public void testIndexOfLongWithStartIndex() {
    long[] array = null;
    assertEquals(-1, ArrayUtils.indexOf(array, 0, 2));
    array = new long[] { 0, 1, 2, 3, 0 };
    assertEquals(4, ArrayUtils.indexOf(array, 0, 2));
    assertEquals(-1, ArrayUtils.indexOf(array, 1, 2));
    assertEquals(2, ArrayUtils.indexOf(array, 2, 2));
    assertEquals(3, ArrayUtils.indexOf(array, 3, 2));
    assertEquals(3, ArrayUtils.indexOf(array, 3, -1));
    assertEquals(-1, ArrayUtils.indexOf(array, 99, 0));
    assertEquals(-1, ArrayUtils.indexOf(array, 0, 6));
  }

  @Test
  public void testLastIndexOfLong() {
    long[] array = null;
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0));
    array = new long[] { 0, 1, 2, 3, 0 };
    assertEquals(4, ArrayUtils.lastIndexOf(array, 0));
    assertEquals(1, ArrayUtils.lastIndexOf(array, 1));
    assertEquals(2, ArrayUtils.lastIndexOf(array, 2));
    assertEquals(3, ArrayUtils.lastIndexOf(array, 3));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 99));
  }

  @Test
  public void testLastIndexOfLongWithStartIndex() {
    long[] array = null;
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0, 2));
    array = new long[] { 0, 1, 2, 3, 0 };
    assertEquals(0, ArrayUtils.lastIndexOf(array, 0, 2));
    assertEquals(1, ArrayUtils.lastIndexOf(array, 1, 2));
    assertEquals(2, ArrayUtils.lastIndexOf(array, 2, 2));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 3, 2));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 3, -1));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 99, 4));
    assertEquals(4, ArrayUtils.lastIndexOf(array, 0, 88));
  }

  @Test
  public void testContainsLong() {
    long[] array = null;
    assertEquals(false, ArrayUtils.contains(array, 1));
    array = new long[] { 0, 1, 2, 3, 0 };
    assertEquals(true, ArrayUtils.contains(array, 0));
    assertEquals(true, ArrayUtils.contains(array, 1));
    assertEquals(true, ArrayUtils.contains(array, 2));
    assertEquals(true, ArrayUtils.contains(array, 3));
    assertEquals(false, ArrayUtils.contains(array, 99));
  }

  @Test
  public void testIndexOfInt() {
    int[] array = null;
    assertEquals(-1, ArrayUtils.indexOf(array, 0));
    array = new int[] { 0, 1, 2, 3, 0 };
    assertEquals(0, ArrayUtils.indexOf(array, 0));
    assertEquals(1, ArrayUtils.indexOf(array, 1));
    assertEquals(2, ArrayUtils.indexOf(array, 2));
    assertEquals(3, ArrayUtils.indexOf(array, 3));
    assertEquals(-1, ArrayUtils.indexOf(array, 99));
  }

  @Test
  public void testIndexOfIntWithStartIndex() {
    int[] array = null;
    assertEquals(-1, ArrayUtils.indexOf(array, 0, 2));
    array = new int[] { 0, 1, 2, 3, 0 };
    assertEquals(4, ArrayUtils.indexOf(array, 0, 2));
    assertEquals(-1, ArrayUtils.indexOf(array, 1, 2));
    assertEquals(2, ArrayUtils.indexOf(array, 2, 2));
    assertEquals(3, ArrayUtils.indexOf(array, 3, 2));
    assertEquals(3, ArrayUtils.indexOf(array, 3, -1));
    assertEquals(-1, ArrayUtils.indexOf(array, 99, 0));
    assertEquals(-1, ArrayUtils.indexOf(array, 0, 6));
  }

  @Test
  public void testLastIndexOfInt() {
    int[] array = null;
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0));
    array = new int[] { 0, 1, 2, 3, 0 };
    assertEquals(4, ArrayUtils.lastIndexOf(array, 0));
    assertEquals(1, ArrayUtils.lastIndexOf(array, 1));
    assertEquals(2, ArrayUtils.lastIndexOf(array, 2));
    assertEquals(3, ArrayUtils.lastIndexOf(array, 3));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 99));
  }

  @Test
  public void testLastIndexOfIntWithStartIndex() {
    int[] array = null;
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0, 2));
    array = new int[] { 0, 1, 2, 3, 0 };
    assertEquals(0, ArrayUtils.lastIndexOf(array, 0, 2));
    assertEquals(1, ArrayUtils.lastIndexOf(array, 1, 2));
    assertEquals(2, ArrayUtils.lastIndexOf(array, 2, 2));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 3, 2));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 3, -1));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 99));
    assertEquals(4, ArrayUtils.lastIndexOf(array, 0, 88));
  }

  @Test
  public void testContainsInt() {
    int[] array = null;
    assertEquals(false, ArrayUtils.contains(array, 1));
    array = new int[] { 0, 1, 2, 3, 0 };
    assertEquals(true, ArrayUtils.contains(array, 0));
    assertEquals(true, ArrayUtils.contains(array, 1));
    assertEquals(true, ArrayUtils.contains(array, 2));
    assertEquals(true, ArrayUtils.contains(array, 3));
    assertEquals(false, ArrayUtils.contains(array, 99));
  }

  @Test
  public void testIndexOfShort() {
    short[] array = null;
    assertEquals(-1, ArrayUtils.indexOf(array, (short) 0));
    array = new short[] { 0, 1, 2, 3, 0 };
    assertEquals(0, ArrayUtils.indexOf(array, (short) 0));
    assertEquals(1, ArrayUtils.indexOf(array, (short) 1));
    assertEquals(2, ArrayUtils.indexOf(array, (short) 2));
    assertEquals(3, ArrayUtils.indexOf(array, (short) 3));
    assertEquals(-1, ArrayUtils.indexOf(array, (short) 99));
  }

  @Test
  public void testIndexOfShortWithStartIndex() {
    short[] array = null;
    assertEquals(-1, ArrayUtils.indexOf(array, (short) 0, 2));
    array = new short[] { 0, 1, 2, 3, 0 };
    assertEquals(4, ArrayUtils.indexOf(array, (short) 0, 2));
    assertEquals(-1, ArrayUtils.indexOf(array, (short) 1, 2));
    assertEquals(2, ArrayUtils.indexOf(array, (short) 2, 2));
    assertEquals(3, ArrayUtils.indexOf(array, (short) 3, 2));
    assertEquals(3, ArrayUtils.indexOf(array, (short) 3, -1));
    assertEquals(-1, ArrayUtils.indexOf(array, (short) 99, 0));
    assertEquals(-1, ArrayUtils.indexOf(array, (short) 0, 6));
  }

  @Test
  public void testLastIndexOfShort() {
    short[] array = null;
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (short) 0));
    array = new short[] { 0, 1, 2, 3, 0 };
    assertEquals(4, ArrayUtils.lastIndexOf(array, (short) 0));
    assertEquals(1, ArrayUtils.lastIndexOf(array, (short) 1));
    assertEquals(2, ArrayUtils.lastIndexOf(array, (short) 2));
    assertEquals(3, ArrayUtils.lastIndexOf(array, (short) 3));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (short) 99));
  }

  @Test
  public void testLastIndexOfShortWithStartIndex() {
    short[] array = null;
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (short) 0, 2));
    array = new short[] { 0, 1, 2, 3, 0 };
    assertEquals(0, ArrayUtils.lastIndexOf(array, (short) 0, 2));
    assertEquals(1, ArrayUtils.lastIndexOf(array, (short) 1, 2));
    assertEquals(2, ArrayUtils.lastIndexOf(array, (short) 2, 2));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (short) 3, 2));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (short) 3, -1));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (short) 99));
    assertEquals(4, ArrayUtils.lastIndexOf(array, (short) 0, 88));
  }

  @Test
  public void testContainsShort() {
    short[] array = null;
    assertEquals(false, ArrayUtils.contains(array, (short) 1));
    array = new short[] { 0, 1, 2, 3, 0 };
    assertEquals(true, ArrayUtils.contains(array, (short) 0));
    assertEquals(true, ArrayUtils.contains(array, (short) 1));
    assertEquals(true, ArrayUtils.contains(array, (short) 2));
    assertEquals(true, ArrayUtils.contains(array, (short) 3));
    assertEquals(false, ArrayUtils.contains(array, (short) 99));
  }

  @Test
  public void testIndexOfChar() {
    char[] array = null;
    assertEquals(-1, ArrayUtils.indexOf(array, 'a'));
    array = new char[] { 'a', 'b', 'c', 'd', 'a' };
    assertEquals(0, ArrayUtils.indexOf(array, 'a'));
    assertEquals(1, ArrayUtils.indexOf(array, 'b'));
    assertEquals(2, ArrayUtils.indexOf(array, 'c'));
    assertEquals(3, ArrayUtils.indexOf(array, 'd'));
    assertEquals(-1, ArrayUtils.indexOf(array, 'e'));
  }

  @Test
  public void testIndexOfCharWithStartIndex() {
    char[] array = null;
    assertEquals(-1, ArrayUtils.indexOf(array, 'a', 2));
    array = new char[] { 'a', 'b', 'c', 'd', 'a' };
    assertEquals(4, ArrayUtils.indexOf(array, 'a', 2));
    assertEquals(-1, ArrayUtils.indexOf(array, 'b', 2));
    assertEquals(2, ArrayUtils.indexOf(array, 'c', 2));
    assertEquals(3, ArrayUtils.indexOf(array, 'd', 2));
    assertEquals(3, ArrayUtils.indexOf(array, 'd', -1));
    assertEquals(-1, ArrayUtils.indexOf(array, 'e', 0));
    assertEquals(-1, ArrayUtils.indexOf(array, 'a', 6));
  }

  @Test
  public void testLastIndexOfChar() {
    char[] array = null;
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 'a'));
    array = new char[] { 'a', 'b', 'c', 'd', 'a' };
    assertEquals(4, ArrayUtils.lastIndexOf(array, 'a'));
    assertEquals(1, ArrayUtils.lastIndexOf(array, 'b'));
    assertEquals(2, ArrayUtils.lastIndexOf(array, 'c'));
    assertEquals(3, ArrayUtils.lastIndexOf(array, 'd'));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 'e'));
  }

  @Test
  public void testLastIndexOfCharWithStartIndex() {
    char[] array = null;
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 'a', 2));
    array = new char[] { 'a', 'b', 'c', 'd', 'a' };
    assertEquals(0, ArrayUtils.lastIndexOf(array, 'a', 2));
    assertEquals(1, ArrayUtils.lastIndexOf(array, 'b', 2));
    assertEquals(2, ArrayUtils.lastIndexOf(array, 'c', 2));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 'd', 2));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 'd', -1));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 'e'));
    assertEquals(4, ArrayUtils.lastIndexOf(array, 'a', 88));
  }

  @Test
  public void testContainsChar() {
    char[] array = null;
    assertEquals(false, ArrayUtils.contains(array, 'b'));
    array = new char[] { 'a', 'b', 'c', 'd', 'a' };
    assertEquals(true, ArrayUtils.contains(array, 'a'));
    assertEquals(true, ArrayUtils.contains(array, 'b'));
    assertEquals(true, ArrayUtils.contains(array, 'c'));
    assertEquals(true, ArrayUtils.contains(array, 'd'));
    assertEquals(false, ArrayUtils.contains(array, 'e'));
  }

  @Test
  public void testIndexOfByte() {
    byte[] array = null;
    assertEquals(-1, ArrayUtils.indexOf(array, (byte) 0));
    array = new byte[] { 0, 1, 2, 3, 0 };
    assertEquals(0, ArrayUtils.indexOf(array, (byte) 0));
    assertEquals(1, ArrayUtils.indexOf(array, (byte) 1));
    assertEquals(2, ArrayUtils.indexOf(array, (byte) 2));
    assertEquals(3, ArrayUtils.indexOf(array, (byte) 3));
    assertEquals(-1, ArrayUtils.indexOf(array, (byte) 99));
  }

  @Test
  public void testIndexOfByteWithStartIndex() {
    byte[] array = null;
    assertEquals(-1, ArrayUtils.indexOf(array, (byte) 0, 2));
    array = new byte[] { 0, 1, 2, 3, 0 };
    assertEquals(4, ArrayUtils.indexOf(array, (byte) 0, 2));
    assertEquals(-1, ArrayUtils.indexOf(array, (byte) 1, 2));
    assertEquals(2, ArrayUtils.indexOf(array, (byte) 2, 2));
    assertEquals(3, ArrayUtils.indexOf(array, (byte) 3, 2));
    assertEquals(3, ArrayUtils.indexOf(array, (byte) 3, -1));
    assertEquals(-1, ArrayUtils.indexOf(array, (byte) 99, 0));
    assertEquals(-1, ArrayUtils.indexOf(array, (byte) 0, 6));
  }

  @Test
  public void testLastIndexOfByte() {
    byte[] array = null;
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (byte) 0));
    array = new byte[] { 0, 1, 2, 3, 0 };
    assertEquals(4, ArrayUtils.lastIndexOf(array, (byte) 0));
    assertEquals(1, ArrayUtils.lastIndexOf(array, (byte) 1));
    assertEquals(2, ArrayUtils.lastIndexOf(array, (byte) 2));
    assertEquals(3, ArrayUtils.lastIndexOf(array, (byte) 3));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (byte) 99));
  }

  @Test
  public void testLastIndexOfByteWithStartIndex() {
    byte[] array = null;
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (byte) 0, 2));
    array = new byte[] { 0, 1, 2, 3, 0 };
    assertEquals(0, ArrayUtils.lastIndexOf(array, (byte) 0, 2));
    assertEquals(1, ArrayUtils.lastIndexOf(array, (byte) 1, 2));
    assertEquals(2, ArrayUtils.lastIndexOf(array, (byte) 2, 2));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (byte) 3, 2));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (byte) 3, -1));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (byte) 99));
    assertEquals(4, ArrayUtils.lastIndexOf(array, (byte) 0, 88));
  }

  @Test
  public void testContainsByte() {
    byte[] array = null;
    assertEquals(false, ArrayUtils.contains(array, (byte) 1));
    array = new byte[] { 0, 1, 2, 3, 0 };
    assertEquals(true, ArrayUtils.contains(array, (byte) 0));
    assertEquals(true, ArrayUtils.contains(array, (byte) 1));
    assertEquals(true, ArrayUtils.contains(array, (byte) 2));
    assertEquals(true, ArrayUtils.contains(array, (byte) 3));
    assertEquals(false, ArrayUtils.contains(array, (byte) 99));
  }

  @Test
  public void testIndexOfDouble() {
    double[] array = null;
    assertEquals(-1, ArrayUtils.indexOf(array, (double) 0));
    array = new double[0];
    assertEquals(-1, ArrayUtils.indexOf(array, (double) 0));
    array = new double[] { 0, 1, 2, 3, 0 };
    assertEquals(0, ArrayUtils.indexOf(array, (double) 0));
    assertEquals(1, ArrayUtils.indexOf(array, (double) 1));
    assertEquals(2, ArrayUtils.indexOf(array, (double) 2));
    assertEquals(3, ArrayUtils.indexOf(array, (double) 3));
    assertEquals(3, ArrayUtils.indexOf(array, (double) 3, -1));
    assertEquals(-1, ArrayUtils.indexOf(array, (double) 99));
  }

  @Test
  public void testIndexOfDoubleTolerance() {
    double[] array = null;
    assertEquals(-1, ArrayUtils.indexOf(array, (double) 0, (double) 0));
    array = new double[0];
    assertEquals(-1, ArrayUtils.indexOf(array, (double) 0, (double) 0));
    array = new double[] { 0, 1, 2, 3, 0 };
    assertEquals(0, ArrayUtils.indexOf(array, (double) 0, (double) 0.3));
    assertEquals(2, ArrayUtils.indexOf(array, (double) 2.2, (double) 0.35));
    assertEquals(3, ArrayUtils.indexOf(array, (double) 4.15, (double) 2.0));
    assertEquals(1, ArrayUtils.indexOf(array, (double) 1.00001324, (double) 0.0001));
  }

  @Test
  public void testIndexOfDoubleWithStartIndex() {
    double[] array = null;
    assertEquals(-1, ArrayUtils.indexOf(array, (double) 0, 2));
    array = new double[0];
    assertEquals(-1, ArrayUtils.indexOf(array, (double) 0, 2));
    array = new double[] { 0, 1, 2, 3, 0 };
    assertEquals(4, ArrayUtils.indexOf(array, (double) 0, 2));
    assertEquals(-1, ArrayUtils.indexOf(array, (double) 1, 2));
    assertEquals(2, ArrayUtils.indexOf(array, (double) 2, 2));
    assertEquals(3, ArrayUtils.indexOf(array, (double) 3, 2));
    assertEquals(-1, ArrayUtils.indexOf(array, (double) 99, 0));
    assertEquals(-1, ArrayUtils.indexOf(array, (double) 0, 6));
  }

  @Test
  public void testIndexOfDoubleWithStartIndexTolerance() {
    double[] array = null;
    assertEquals(-1, ArrayUtils.indexOf(array, (double) 0, 2, (double) 0));
    array = new double[0];
    assertEquals(-1, ArrayUtils.indexOf(array, (double) 0, 2, (double) 0));
    array = new double[] { 0, 1, 2, 3, 0 };
    assertEquals(-1, ArrayUtils.indexOf(array, (double) 0, 99, (double) 0.3));
    assertEquals(0, ArrayUtils.indexOf(array, (double) 0, 0, (double) 0.3));
    assertEquals(4, ArrayUtils.indexOf(array, (double) 0, 3, (double) 0.3));
    assertEquals(2, ArrayUtils.indexOf(array, (double) 2.2, 0, (double) 0.35));
    assertEquals(3, ArrayUtils.indexOf(array, (double) 4.15, 0, (double) 2.0));
    assertEquals(1, ArrayUtils.indexOf(array, (double) 1.00001324, 0,
        (double) 0.0001));
    assertEquals(3, ArrayUtils.indexOf(array, (double) 4.15, -1, (double) 2.0));
    assertEquals(1, ArrayUtils.indexOf(array, (double) 1.00001324, -300,
        (double) 0.0001));
  }

  @Test
  public void testLastIndexOfDouble() {
    double[] array = null;
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (double) 0));
    array = new double[0];
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (double) 0));
    array = new double[] { 0, 1, 2, 3, 0 };
    assertEquals(4, ArrayUtils.lastIndexOf(array, (double) 0));
    assertEquals(1, ArrayUtils.lastIndexOf(array, (double) 1));
    assertEquals(2, ArrayUtils.lastIndexOf(array, (double) 2));
    assertEquals(3, ArrayUtils.lastIndexOf(array, (double) 3));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (double) 99));
  }

  @Test
  public void testLastIndexOfDoubleTolerance() {
    double[] array = null;
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (double) 0, (double) 0));
    array = new double[0];
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (double) 0, (double) 0));
    array = new double[] { 0, 1, 2, 3, 0 };
    assertEquals(4, ArrayUtils.lastIndexOf(array, (double) 0, (double) 0.3));
    assertEquals(2, ArrayUtils.lastIndexOf(array, (double) 2.2, (double) 0.35));
    assertEquals(3, ArrayUtils.lastIndexOf(array, (double) 4.15, (double) 2.0));
    assertEquals(1, ArrayUtils.lastIndexOf(array, (double) 1.00001324,
        (double) 0.0001));
  }

  @Test
  public void testLastIndexOfDoubleWithStartIndex() {
    double[] array = null;
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (double) 0, 2));
    array = new double[0];
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (double) 0, 2));
    array = new double[] { 0, 1, 2, 3, 0 };
    assertEquals(0, ArrayUtils.lastIndexOf(array, (double) 0, 2));
    assertEquals(1, ArrayUtils.lastIndexOf(array, (double) 1, 2));
    assertEquals(2, ArrayUtils.lastIndexOf(array, (double) 2, 2));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (double) 3, 2));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (double) 3, -1));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (double) 99));
    assertEquals(4, ArrayUtils.lastIndexOf(array, (double) 0, 88));
  }

  @Test
  public void testLastIndexOfDoubleWithStartIndexTolerance() {
    double[] array = null;
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (double) 0, 2, (double) 0));
    array = new double[0];
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (double) 0, 2, (double) 0));
    array = new double[] { (double) 3 };
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (double) 1, 0, (double) 0));
    array = new double[] { 0, 1, 2, 3, 0 };
    assertEquals(4, ArrayUtils.lastIndexOf(array, (double) 0, 99, (double) 0.3));
    assertEquals(0, ArrayUtils.lastIndexOf(array, (double) 0, 3, (double) 0.3));
    assertEquals(2, ArrayUtils.lastIndexOf(array, (double) 2.2, 3, (double) 0.35));
    assertEquals(3, ArrayUtils.lastIndexOf(array, (double) 4.15, array.length,
        (double) 2.0));
    assertEquals(1, ArrayUtils.lastIndexOf(array, (double) 1.00001324,
        array.length, (double) 0.0001));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (double) 4.15, -200,
        (double) 2.0));
  }

  @Test
  public void testContainsDouble() {
    double[] array = null;
    assertEquals(false, ArrayUtils.contains(array, (double) 1));
    array = new double[] { 0, 1, 2, 3, 0 };
    assertEquals(true, ArrayUtils.contains(array, (double) 0));
    assertEquals(true, ArrayUtils.contains(array, (double) 1));
    assertEquals(true, ArrayUtils.contains(array, (double) 2));
    assertEquals(true, ArrayUtils.contains(array, (double) 3));
    assertEquals(false, ArrayUtils.contains(array, (double) 99));
  }

  @Test
  public void testContainsDoubleTolerance() {
    double[] array = null;
    assertEquals(false, ArrayUtils.contains(array, (double) 1, (double) 0));
    array = new double[] { 0, 1, 2, 3, 0 };
    assertEquals(false, ArrayUtils.contains(array, (double) 4.0, (double) 0.33));
    assertEquals(false, ArrayUtils.contains(array, (double) 2.5, (double) 0.49));
    assertEquals(true, ArrayUtils.contains(array, (double) 2.5, (double) 0.50));
    assertEquals(true, ArrayUtils.contains(array, (double) 2.5, (double) 0.51));
  }

  @Test
  public void testIndexOfFloat() {
    float[] array = null;
    assertEquals(-1, ArrayUtils.indexOf(array, (float) 0));
    array = new float[0];
    assertEquals(-1, ArrayUtils.indexOf(array, (float) 0));
    array = new float[] { 0, 1, 2, 3, 0 };
    assertEquals(0, ArrayUtils.indexOf(array, (float) 0));
    assertEquals(1, ArrayUtils.indexOf(array, (float) 1));
    assertEquals(2, ArrayUtils.indexOf(array, (float) 2));
    assertEquals(3, ArrayUtils.indexOf(array, (float) 3));
    assertEquals(-1, ArrayUtils.indexOf(array, (float) 99));
  }

  @Test
  public void testIndexOfFloatWithStartIndex() {
    float[] array = null;
    assertEquals(-1, ArrayUtils.indexOf(array, (float) 0, 2));
    array = new float[0];
    assertEquals(-1, ArrayUtils.indexOf(array, (float) 0, 2));
    array = new float[] { 0, 1, 2, 3, 0 };
    assertEquals(4, ArrayUtils.indexOf(array, (float) 0, 2));
    assertEquals(-1, ArrayUtils.indexOf(array, (float) 1, 2));
    assertEquals(2, ArrayUtils.indexOf(array, (float) 2, 2));
    assertEquals(3, ArrayUtils.indexOf(array, (float) 3, 2));
    assertEquals(3, ArrayUtils.indexOf(array, (float) 3, -1));
    assertEquals(-1, ArrayUtils.indexOf(array, (float) 99, 0));
    assertEquals(-1, ArrayUtils.indexOf(array, (float) 0, 6));
  }

  @Test
  public void testLastIndexOfFloat() {
    float[] array = null;
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (float) 0));
    array = new float[0];
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (float) 0));
    array = new float[] { 0, 1, 2, 3, 0 };
    assertEquals(4, ArrayUtils.lastIndexOf(array, (float) 0));
    assertEquals(1, ArrayUtils.lastIndexOf(array, (float) 1));
    assertEquals(2, ArrayUtils.lastIndexOf(array, (float) 2));
    assertEquals(3, ArrayUtils.lastIndexOf(array, (float) 3));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (float) 99));
  }

  @Test
  public void testLastIndexOfFloatWithStartIndex() {
    float[] array = null;
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (float) 0, 2));
    array = new float[0];
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (float) 0, 2));
    array = new float[] { 0, 1, 2, 3, 0 };
    assertEquals(0, ArrayUtils.lastIndexOf(array, (float) 0, 2));
    assertEquals(1, ArrayUtils.lastIndexOf(array, (float) 1, 2));
    assertEquals(2, ArrayUtils.lastIndexOf(array, (float) 2, 2));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (float) 3, 2));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (float) 3, -1));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, (float) 99));
    assertEquals(4, ArrayUtils.lastIndexOf(array, (float) 0, 88));
  }

  @Test
  public void testContainsFloat() {
    float[] array = null;
    assertEquals(false, ArrayUtils.contains(array, (float) 1));
    array = new float[] { 0, 1, 2, 3, 0 };
    assertEquals(true, ArrayUtils.contains(array, (float) 0));
    assertEquals(true, ArrayUtils.contains(array, (float) 1));
    assertEquals(true, ArrayUtils.contains(array, (float) 2));
    assertEquals(true, ArrayUtils.contains(array, (float) 3));
    assertEquals(false, ArrayUtils.contains(array, (float) 99));
  }

  @Test
  public void testIndexOfBoolean() {
    boolean[] array = null;
    assertEquals(-1, ArrayUtils.indexOf(array, true));
    array = new boolean[0];
    assertEquals(-1, ArrayUtils.indexOf(array, true));
    array = new boolean[] { true, false, true };
    assertEquals(0, ArrayUtils.indexOf(array, true));
    assertEquals(1, ArrayUtils.indexOf(array, false));
    array = new boolean[] { true, true };
    assertEquals(-1, ArrayUtils.indexOf(array, false));
  }

  @Test
  public void testIndexOfBooleanWithStartIndex() {
    boolean[] array = null;
    assertEquals(-1, ArrayUtils.indexOf(array, true, 2));
    array = new boolean[0];
    assertEquals(-1, ArrayUtils.indexOf(array, true, 2));
    array = new boolean[] { true, false, true };
    assertEquals(2, ArrayUtils.indexOf(array, true, 1));
    assertEquals(-1, ArrayUtils.indexOf(array, false, 2));
    assertEquals(1, ArrayUtils.indexOf(array, false, 0));
    assertEquals(1, ArrayUtils.indexOf(array, false, -1));
    array = new boolean[] { true, true };
    assertEquals(-1, ArrayUtils.indexOf(array, false, 0));
    assertEquals(-1, ArrayUtils.indexOf(array, false, -1));
  }

  @Test
  public void testLastIndexOfBoolean() {
    boolean[] array = null;
    assertEquals(-1, ArrayUtils.lastIndexOf(array, true));
    array = new boolean[0];
    assertEquals(-1, ArrayUtils.lastIndexOf(array, true));
    array = new boolean[] { true, false, true };
    assertEquals(2, ArrayUtils.lastIndexOf(array, true));
    assertEquals(1, ArrayUtils.lastIndexOf(array, false));
    array = new boolean[] { true, true };
    assertEquals(-1, ArrayUtils.lastIndexOf(array, false));
  }

  @Test
  public void testLastIndexOfBooleanWithStartIndex() {
    boolean[] array = null;
    assertEquals(-1, ArrayUtils.lastIndexOf(array, true, 2));
    array = new boolean[0];
    assertEquals(-1, ArrayUtils.lastIndexOf(array, true, 2));
    array = new boolean[] { true, false, true };
    assertEquals(2, ArrayUtils.lastIndexOf(array, true, 2));
    assertEquals(0, ArrayUtils.lastIndexOf(array, true, 1));
    assertEquals(1, ArrayUtils.lastIndexOf(array, false, 2));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, true, -1));
    array = new boolean[] { true, true };
    assertEquals(-1, ArrayUtils.lastIndexOf(array, false, 2));
    assertEquals(-1, ArrayUtils.lastIndexOf(array, true, -1));
  }

  @Test
  public void testContainsBoolean() {
    boolean[] array = null;
    assertEquals(false, ArrayUtils.contains(array, true));
    array = new boolean[] { true, false, true };
    assertEquals(true, ArrayUtils.contains(array, true));
    assertEquals(true, ArrayUtils.contains(array, false));
    array = new boolean[] { true, true };
    assertEquals(true, ArrayUtils.contains(array, true));
    assertEquals(false, ArrayUtils.contains(array, false));
  }

  @Test
  public void testToPrimitive_boolean() {
    final Boolean[] b = null;
    assertNull(ArrayUtils.toPrimitive(b));
    assertSame(ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.toPrimitive(new Boolean[0]));
    assertTrue(Equality.equals(new boolean[] { true, false, true },
        ArrayUtils.toPrimitive(new Boolean[] { Boolean.TRUE, Boolean.FALSE,
            Boolean.TRUE })));

    try {
      ArrayUtils.toPrimitive(new Boolean[] { Boolean.TRUE, null });
      fail();
    } catch (final NullPointerException ex) {
    }
  }

  @Test
  public void testToPrimitive_boolean_boolean() {
    assertNull(ArrayUtils.toPrimitive(null, false));
    assertSame(ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.toPrimitive(new Boolean[0],
        false));
    assertTrue(Equality.equals(new boolean[] { true, false, true },
        ArrayUtils.toPrimitive(new Boolean[] { Boolean.TRUE, Boolean.FALSE, Boolean.TRUE }, false)));
    assertTrue(Equality.equals(new boolean[] { true, false, false },
        ArrayUtils.toPrimitive(new Boolean[] { Boolean.TRUE, null, Boolean.FALSE }, false)));
    assertTrue(Equality.equals(new boolean[] { true, true, false },
        ArrayUtils.toPrimitive(new Boolean[] { Boolean.TRUE, null, Boolean.FALSE }, true)));
  }

  @Test
  public void testToObject_boolean() {
    final boolean[] b = null;
    assertNull(ArrayUtils.toObject(b));
    assertSame(ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY, ArrayUtils.toObject(new boolean[0]));
    assertTrue(Equality.equals(new Boolean[] { Boolean.TRUE, Boolean.FALSE, Boolean.TRUE },
        ArrayUtils.toObject(new boolean[] { true, false, true })));
  }

  @Test
  public void testToPrimitive_char() {
    final Character[] b = null;
    assertNull(ArrayUtils.toPrimitive(b));
    assertSame(ArrayUtils.EMPTY_CHAR_ARRAY, ArrayUtils.toPrimitive(new Character[0]));
    assertTrue(Equality.equals(new char[] { Character.MIN_VALUE, Character.MAX_VALUE, '0' },
        ArrayUtils.toPrimitive(new Character[] { new Character(Character.MIN_VALUE),
            new Character(Character.MAX_VALUE),
            new Character('0') })));
    try {
      ArrayUtils.toPrimitive(new Character[] { new Character(Character.MIN_VALUE),
          null });
      fail();
    } catch (final NullPointerException ex) {
    }
  }

  @Test
  public void testToPrimitive_char_char() {
    final Character[] b = null;
    assertNull(ArrayUtils.toPrimitive(b, Character.MIN_VALUE));

    assertSame(ArrayUtils.EMPTY_CHAR_ARRAY, ArrayUtils.toPrimitive(new Character[0],
        (char) 0));

    assertTrue(Equality.equals(new char[] { Character.MIN_VALUE,
        Character.MAX_VALUE, '0' }, ArrayUtils.toPrimitive(new Character[] {
        new Character(Character.MIN_VALUE), new Character(Character.MAX_VALUE),
        new Character('0') }, Character.MIN_VALUE)));

    assertTrue(Equality.equals(new char[] { Character.MIN_VALUE,
        Character.MAX_VALUE, '0' }, ArrayUtils.toPrimitive(new Character[] {
        new Character(Character.MIN_VALUE), null, new Character('0') },
        Character.MAX_VALUE)));
  }

  @Test
  public void testToObject_char() {
    final char[] b = null;
    assertNull(ArrayUtils.toObject(b));

    assertSame(ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, ArrayUtils
        .toObject(new char[0]));

    assertTrue(Equality.equals(new Character[] {
        new Character(Character.MIN_VALUE), new Character(Character.MAX_VALUE),
        new Character('0') }, ArrayUtils.toObject(new char[] { Character.MIN_VALUE,
        Character.MAX_VALUE, '0' })));
  }

  @Test
  public void testToPrimitive_byte() {
    final Byte[] b = null;
    assertNull(ArrayUtils.toPrimitive(b));

    assertSame(ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.toPrimitive(new Byte[0]));

    assertTrue(Equality.equals(new byte[] { Byte.MIN_VALUE, Byte.MAX_VALUE,
        (byte) 9999999 }, ArrayUtils.toPrimitive(new Byte[] {
        new Byte(Byte.MIN_VALUE), new Byte(Byte.MAX_VALUE),
        new Byte((byte) 9999999) })));

    try {
      ArrayUtils.toPrimitive(new Byte[] { new Byte(Byte.MIN_VALUE), null });
      fail();
    } catch (final NullPointerException ex) {
    }
  }

  @Test
  public void testToPrimitive_byte_byte() {
    final Byte[] b = null;
    assertNull(ArrayUtils.toPrimitive(b, Byte.MIN_VALUE));

    assertSame(ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.toPrimitive(new Byte[0],
        (byte) 1));

    assertTrue(Equality.equals(new byte[] { Byte.MIN_VALUE, Byte.MAX_VALUE,
        (byte) 9999999 }, ArrayUtils.toPrimitive(new Byte[] {
        new Byte(Byte.MIN_VALUE), new Byte(Byte.MAX_VALUE),
        new Byte((byte) 9999999) }, Byte.MIN_VALUE)));

    assertTrue(Equality.equals(new byte[] { Byte.MIN_VALUE, Byte.MAX_VALUE,
        (byte) 9999999 }, ArrayUtils.toPrimitive(new Byte[] {
        new Byte(Byte.MIN_VALUE), null, new Byte((byte) 9999999) },
        Byte.MAX_VALUE)));
  }

  @Test
  public void testToObject_byte() {
    final byte[] b = null;
    assertNull(ArrayUtils.toObject(b));

    assertSame(ArrayUtils.EMPTY_BYTE_OBJECT_ARRAY, ArrayUtils.toObject(new byte[0]));

    assertTrue(Equality.equals(new Byte[] { new Byte(Byte.MIN_VALUE),
        new Byte(Byte.MAX_VALUE), new Byte((byte) 9999999) },
        ArrayUtils.toObject(new byte[] { Byte.MIN_VALUE, Byte.MAX_VALUE,
            (byte) 9999999 })));
  }

  @Test
  public void testToPrimitive_short() {
    final Short[] b = null;
    assertNull(ArrayUtils.toPrimitive(b));

    assertSame(ArrayUtils.EMPTY_SHORT_ARRAY, ArrayUtils.toPrimitive(new Short[0]));

    assertTrue(Equality.equals(new short[] { Short.MIN_VALUE, Short.MAX_VALUE,
        (short) 9999999 }, ArrayUtils.toPrimitive(new Short[] {
        new Short(Short.MIN_VALUE), new Short(Short.MAX_VALUE),
        new Short((short) 9999999) })));

    try {
      ArrayUtils.toPrimitive(new Short[] { new Short(Short.MIN_VALUE), null });
      fail();
    } catch (final NullPointerException ex) {
    }
  }

  @Test
  public void testToPrimitive_short_short() {
    final Short[] s = null;
    assertNull(ArrayUtils.toPrimitive(s, Short.MIN_VALUE));

    assertSame(ArrayUtils.EMPTY_SHORT_ARRAY, ArrayUtils.toPrimitive(new Short[0],
        Short.MIN_VALUE));

    assertTrue(Equality.equals(new short[] { Short.MIN_VALUE, Short.MAX_VALUE,
        (short) 9999999 }, ArrayUtils.toPrimitive(new Short[] {
        new Short(Short.MIN_VALUE), new Short(Short.MAX_VALUE),
        new Short((short) 9999999) }, Short.MIN_VALUE)));

    assertTrue(Equality.equals(new short[] { Short.MIN_VALUE, Short.MAX_VALUE,
        (short) 9999999 }, ArrayUtils.toPrimitive(new Short[] {
        new Short(Short.MIN_VALUE), null, new Short((short) 9999999) },
        Short.MAX_VALUE)));
  }

  @Test
  public void testToObject_short() {
    final short[] b = null;
    assertNull(ArrayUtils.toObject(b));

    assertSame(ArrayUtils.EMPTY_SHORT_OBJECT_ARRAY, ArrayUtils.toObject(new short[0]));

    assertTrue(Equality.equals(new Short[] { new Short(Short.MIN_VALUE),
        new Short(Short.MAX_VALUE), new Short((short) 9999999) }, ArrayUtils
        .toObject(new short[] { Short.MIN_VALUE, Short.MAX_VALUE,
            (short) 9999999 })));
  }

  @Test
  public void testToPrimitive_int() {
    final Integer[] b = null;
    assertNull(ArrayUtils.toPrimitive(b));
    assertSame(ArrayUtils.EMPTY_INT_ARRAY, ArrayUtils.toPrimitive(new Integer[0]));
    assertTrue(Equality.equals(new int[] { Integer.MIN_VALUE, Integer.MAX_VALUE,
        9999999 }, ArrayUtils.toPrimitive(new Integer[] {
        new Integer(Integer.MIN_VALUE), new Integer(Integer.MAX_VALUE),
        new Integer(9999999) })));

    try {
      ArrayUtils
          .toPrimitive(new Integer[] { new Integer(Integer.MIN_VALUE), null });
      fail();
    } catch (final NullPointerException ex) {
    }
  }

  @Test
  public void testToPrimitive_int_int() {
    final Long[] l = null;
    assertNull(ArrayUtils.toPrimitive(l, Integer.MIN_VALUE));
    assertSame(ArrayUtils.EMPTY_INT_ARRAY, ArrayUtils.toPrimitive(new Integer[0], 1));
    assertTrue(Equality.equals(new int[] { Integer.MIN_VALUE, Integer.MAX_VALUE,
        9999999 }, ArrayUtils.toPrimitive(new Integer[] {
        new Integer(Integer.MIN_VALUE), new Integer(Integer.MAX_VALUE),
        new Integer(9999999) }, 1)));
    assertTrue(Equality.equals(new int[] { Integer.MIN_VALUE, Integer.MAX_VALUE,
        9999999 }, ArrayUtils.toPrimitive(new Integer[] {
        new Integer(Integer.MIN_VALUE), null, new Integer(9999999) },
        Integer.MAX_VALUE)));
  }

  @Test
  public void testToPrimitive_intNull() {
    final Integer[] iArray = null;
    assertNull(ArrayUtils.toPrimitive(iArray, Integer.MIN_VALUE));
  }

  @Test
  public void testToObject_int() {
    final int[] b = null;
    assertNull(ArrayUtils.toObject(b));

    assertSame(ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY, ArrayUtils.toObject(new int[0]));

    assertTrue(Equality.equals(new Integer[] { new Integer(Integer.MIN_VALUE),
        new Integer(Integer.MAX_VALUE), new Integer(9999999) }, ArrayUtils
        .toObject(new int[] { Integer.MIN_VALUE, Integer.MAX_VALUE, 9999999 })));
  }

  @Test
  public void testToPrimitive_long() {
    final Long[] b = null;
    assertNull(ArrayUtils.toPrimitive(b));

    assertSame(ArrayUtils.EMPTY_LONG_ARRAY, ArrayUtils.toPrimitive(new Long[0]));

    assertTrue(Equality.equals(new long[] { Long.MIN_VALUE, Long.MAX_VALUE,
        9999999 }, ArrayUtils.toPrimitive(new Long[] { new Long(Long.MIN_VALUE),
        new Long(Long.MAX_VALUE), new Long(9999999) })));

    try {
      ArrayUtils.toPrimitive(new Long[] { new Long(Long.MIN_VALUE), null });
      fail();
    } catch (final NullPointerException ex) {
    }
  }

  @Test
  public void testToPrimitive_long_long() {
    final Long[] l = null;
    assertNull(ArrayUtils.toPrimitive(l, Long.MIN_VALUE));

    assertSame(ArrayUtils.EMPTY_LONG_ARRAY, ArrayUtils.toPrimitive(new Long[0], 1));

    assertTrue(Equality.equals(new long[] { Long.MIN_VALUE, Long.MAX_VALUE,
        9999999 }, ArrayUtils.toPrimitive(new Long[] { new Long(Long.MIN_VALUE),
        new Long(Long.MAX_VALUE), new Long(9999999) }, 1)));

    assertTrue(Equality.equals(new long[] { Long.MIN_VALUE, Long.MAX_VALUE,
        9999999 }, ArrayUtils.toPrimitive(new Long[] { new Long(Long.MIN_VALUE),
        null, new Long(9999999) }, Long.MAX_VALUE)));
  }

  @Test
  public void testToObject_long() {
    final long[] b = null;
    assertNull(ArrayUtils.toObject(b));

    assertSame(ArrayUtils.EMPTY_LONG_OBJECT_ARRAY, ArrayUtils.toObject(new long[0]));

    assertTrue(Equality.equals(new Long[] { new Long(Long.MIN_VALUE),
        new Long(Long.MAX_VALUE), new Long(9999999) }, ArrayUtils
        .toObject(new long[] { Long.MIN_VALUE, Long.MAX_VALUE, 9999999 })));
  }

  @Test
  public void testToPrimitive_float() {
    final Float[] b = null;
    assertNull(ArrayUtils.toPrimitive(b));

    assertSame(ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.toPrimitive(new Float[0]));

    assertTrue(Equality.equals(new float[] { Float.MIN_VALUE, Float.MAX_VALUE,
        9999999 }, ArrayUtils.toPrimitive(new Float[] { new Float(Float.MIN_VALUE),
        new Float(Float.MAX_VALUE), new Float(9999999) })));

    try {
      ArrayUtils.toPrimitive(new Float[] { new Float(Float.MIN_VALUE), null });
      fail();
    } catch (final NullPointerException ex) {
    }
  }

  @Test
  public void testToPrimitive_float_float() {
    final Float[] l = null;
    assertNull(ArrayUtils.toPrimitive(l, Float.MIN_VALUE));

    assertSame(ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.toPrimitive(new Float[0], 1));

    assertTrue(Equality.equals(new float[] { Float.MIN_VALUE, Float.MAX_VALUE,
        9999999 }, ArrayUtils.toPrimitive(new Float[] { new Float(Float.MIN_VALUE),
        new Float(Float.MAX_VALUE), new Float(9999999) }, 1)));

    assertTrue(Equality.equals(new float[] { Float.MIN_VALUE, Float.MAX_VALUE,
        9999999 }, ArrayUtils.toPrimitive(new Float[] { new Float(Float.MIN_VALUE),
        null, new Float(9999999) }, Float.MAX_VALUE)));
  }

  @Test
  public void testToObject_float() {
    final float[] b = null;
    assertNull(ArrayUtils.toObject(b));

    assertSame(ArrayUtils.EMPTY_FLOAT_OBJECT_ARRAY, ArrayUtils.toObject(new float[0]));

    assertTrue(Equality.equals(new Float[] { new Float(Float.MIN_VALUE),
        new Float(Float.MAX_VALUE), new Float(9999999) }, ArrayUtils
        .toObject(new float[] { Float.MIN_VALUE, Float.MAX_VALUE, 9999999 })));
  }

  @Test
  public void testToPrimitive_double() {
    final Double[] b = null;
    assertNull(ArrayUtils.toPrimitive(b));

    assertSame(ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.toPrimitive(new Double[0]));

    assertTrue(Equality.equals(new double[] { Double.MIN_VALUE, Double.MAX_VALUE,
        9999999 }, ArrayUtils.toPrimitive(new Double[] {
        new Double(Double.MIN_VALUE), new Double(Double.MAX_VALUE),
        new Double(9999999) })));

    try {
      ArrayUtils.toPrimitive(new Float[] { new Float(Float.MIN_VALUE), null });
      fail();
    } catch (final NullPointerException ex) {
    }
  }

  @Test
  public void testToPrimitive_double_double() {
    final Double[] l = null;
    assertNull(ArrayUtils.toPrimitive(l, Double.MIN_VALUE));

    assertSame(ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.toPrimitive(new Double[0], 1));

    assertTrue(Equality.equals(new double[] { Double.MIN_VALUE, Double.MAX_VALUE,
        9999999 }, ArrayUtils.toPrimitive(new Double[] {
        new Double(Double.MIN_VALUE), new Double(Double.MAX_VALUE),
        new Double(9999999) }, 1)));

    assertTrue(Equality.equals(new double[] { Double.MIN_VALUE, Double.MAX_VALUE,
        9999999 }, ArrayUtils.toPrimitive(new Double[] {
        new Double(Double.MIN_VALUE), null, new Double(9999999) },
        Double.MAX_VALUE)));
  }

  @Test
  public void testToObject_double() {
    final double[] b = null;
    assertNull(ArrayUtils.toObject(b));

    assertSame(ArrayUtils.EMPTY_DOUBLE_OBJECT_ARRAY, ArrayUtils.toObject(new double[0]));

    assertTrue(Equality.equals(new Double[] { new Double(Double.MIN_VALUE),
            new Double(Double.MAX_VALUE), new Double(9999999) },
            ArrayUtils.toObject(new double[] { Double.MIN_VALUE, Double.MAX_VALUE,
                9999999 })));
  }

  @Test
  public void testIsEmptyObject() {
    final Object[] emptyArray = new Object[] {};
    final Object[] notEmptyArray = new Object[] { new String("Value") };
    assertEquals(true, ArrayUtils.isEmpty((Object[]) null));
    assertEquals(true, ArrayUtils.isEmpty(emptyArray));
    assertEquals(false, ArrayUtils.isEmpty(notEmptyArray));
  }

  @Test
  public void testIsEmptyPrimitives() {
    final long[] emptyLongArray = new long[] {};
    final long[] notEmptyLongArray = new long[] { 1L };
    assertEquals(true, ArrayUtils.isEmpty((long[]) null));
    assertEquals(true, ArrayUtils.isEmpty(emptyLongArray));
    assertEquals(false, ArrayUtils.isEmpty(notEmptyLongArray));

    final int[] emptyIntArray = new int[] {};
    final int[] notEmptyIntArray = new int[] { 1 };
    assertEquals(true, ArrayUtils.isEmpty((int[]) null));
    assertEquals(true, ArrayUtils.isEmpty(emptyIntArray));
    assertEquals(false, ArrayUtils.isEmpty(notEmptyIntArray));

    final short[] emptyShortArray = new short[] {};
    final short[] notEmptyShortArray = new short[] { 1 };
    assertEquals(true, ArrayUtils.isEmpty((short[]) null));
    assertEquals(true, ArrayUtils.isEmpty(emptyShortArray));
    assertEquals(false, ArrayUtils.isEmpty(notEmptyShortArray));

    final char[] emptyCharArray = new char[] {};
    final char[] notEmptyCharArray = new char[] { 1 };
    assertEquals(true, ArrayUtils.isEmpty((char[]) null));
    assertEquals(true, ArrayUtils.isEmpty(emptyCharArray));
    assertEquals(false, ArrayUtils.isEmpty(notEmptyCharArray));

    final byte[] emptyByteArray = new byte[] {};
    final byte[] notEmptyByteArray = new byte[] { 1 };
    assertEquals(true, ArrayUtils.isEmpty((byte[]) null));
    assertEquals(true, ArrayUtils.isEmpty(emptyByteArray));
    assertEquals(false, ArrayUtils.isEmpty(notEmptyByteArray));

    final double[] emptyDoubleArray = new double[] {};
    final double[] notEmptyDoubleArray = new double[] { 1.0 };
    assertEquals(true, ArrayUtils.isEmpty((double[]) null));
    assertEquals(true, ArrayUtils.isEmpty(emptyDoubleArray));
    assertEquals(false, ArrayUtils.isEmpty(notEmptyDoubleArray));

    final float[] emptyFloatArray = new float[] {};
    final float[] notEmptyFloatArray = new float[] { 1.0F };
    assertEquals(true, ArrayUtils.isEmpty((float[]) null));
    assertEquals(true, ArrayUtils.isEmpty(emptyFloatArray));
    assertEquals(false, ArrayUtils.isEmpty(notEmptyFloatArray));

    final boolean[] emptyBooleanArray = new boolean[] {};
    final boolean[] notEmptyBooleanArray = new boolean[] { true };
    assertEquals(true, ArrayUtils.isEmpty((boolean[]) null));
    assertEquals(true, ArrayUtils.isEmpty(emptyBooleanArray));
    assertEquals(false, ArrayUtils.isEmpty(notEmptyBooleanArray));
  }

  @Test
  public void testGetLength() {
    assertEquals(0, ArrayUtils.getLength(null));

    final Object[] emptyObjectArray = new Object[0];
    final Object[] notEmptyObjectArray = new Object[] { "aValue" };
    assertEquals(0, ArrayUtils.getLength((Object[]) null));
    assertEquals(0, ArrayUtils.getLength(emptyObjectArray));
    assertEquals(1, ArrayUtils.getLength(notEmptyObjectArray));

    final int[] emptyIntArray = new int[] {};
    final int[] notEmptyIntArray = new int[] { 1 };
    assertEquals(0, ArrayUtils.getLength((int[]) null));
    assertEquals(0, ArrayUtils.getLength(emptyIntArray));
    assertEquals(1, ArrayUtils.getLength(notEmptyIntArray));

    final short[] emptyShortArray = new short[] {};
    final short[] notEmptyShortArray = new short[] { 1 };
    assertEquals(0, ArrayUtils.getLength((short[]) null));
    assertEquals(0, ArrayUtils.getLength(emptyShortArray));
    assertEquals(1, ArrayUtils.getLength(notEmptyShortArray));

    final char[] emptyCharArray = new char[] {};
    final char[] notEmptyCharArray = new char[] { 1 };
    assertEquals(0, ArrayUtils.getLength((char[]) null));
    assertEquals(0, ArrayUtils.getLength(emptyCharArray));
    assertEquals(1, ArrayUtils.getLength(notEmptyCharArray));

    final byte[] emptyByteArray = new byte[] {};
    final byte[] notEmptyByteArray = new byte[] { 1 };
    assertEquals(0, ArrayUtils.getLength((byte[]) null));
    assertEquals(0, ArrayUtils.getLength(emptyByteArray));
    assertEquals(1, ArrayUtils.getLength(notEmptyByteArray));

    final double[] emptyDoubleArray = new double[] {};
    final double[] notEmptyDoubleArray = new double[] { 1.0 };
    assertEquals(0, ArrayUtils.getLength((double[]) null));
    assertEquals(0, ArrayUtils.getLength(emptyDoubleArray));
    assertEquals(1, ArrayUtils.getLength(notEmptyDoubleArray));

    final float[] emptyFloatArray = new float[] {};
    final float[] notEmptyFloatArray = new float[] { 1.0F };
    assertEquals(0, ArrayUtils.getLength((float[]) null));
    assertEquals(0, ArrayUtils.getLength(emptyFloatArray));
    assertEquals(1, ArrayUtils.getLength(notEmptyFloatArray));

    final boolean[] emptyBooleanArray = new boolean[] {};
    final boolean[] notEmptyBooleanArray = new boolean[] { true };
    assertEquals(0, ArrayUtils.getLength((boolean[]) null));
    assertEquals(0, ArrayUtils.getLength(emptyBooleanArray));
    assertEquals(1, ArrayUtils.getLength(notEmptyBooleanArray));

    try {
      ArrayUtils.getLength("notAnArray");
      fail("IllegalArgumentException should have been thrown");
    } catch (final IllegalArgumentException e) {
    }
  }

  @Test
  public void testAddObjectArrayBoolean() {
    boolean[] newArray;
    newArray = ArrayUtils.add((boolean[]) null, false);
    assertTrue(Equality.equals(new boolean[] { false }, newArray));
    assertEquals(Boolean.TYPE, newArray.getClass().getComponentType());
    newArray = ArrayUtils.add((boolean[]) null, true);
    assertTrue(Equality.equals(new boolean[] { true }, newArray));
    assertEquals(Boolean.TYPE, newArray.getClass().getComponentType());
    final boolean[] array1 = new boolean[] { true, false, true };
    newArray = ArrayUtils.add(array1, false);
    assertTrue(Equality.equals(new boolean[] { true, false, true, false },
        newArray));
    assertEquals(Boolean.TYPE, newArray.getClass().getComponentType());
  }

  @Test
  public void testAddObjectArrayByte() {
    byte[] newArray;
    newArray = ArrayUtils.add((byte[]) null, (byte) 0);
    assertTrue(Equality.equals(new byte[] { 0 }, newArray));
    assertEquals(Byte.TYPE, newArray.getClass().getComponentType());
    newArray = ArrayUtils.add((byte[]) null, (byte) 1);
    assertTrue(Equality.equals(new byte[] { 1 }, newArray));
    assertEquals(Byte.TYPE, newArray.getClass().getComponentType());
    final byte[] array1 = new byte[] { 1, 2, 3 };
    newArray = ArrayUtils.add(array1, (byte) 0);
    assertTrue(Equality.equals(new byte[] { 1, 2, 3, 0 }, newArray));
    assertEquals(Byte.TYPE, newArray.getClass().getComponentType());
    newArray = ArrayUtils.add(array1, (byte) 4);
    assertTrue(Equality.equals(new byte[] { 1, 2, 3, 4 }, newArray));
    assertEquals(Byte.TYPE, newArray.getClass().getComponentType());
  }

  @Test
  public void testAddObjectArrayChar() {
    char[] newArray;
    newArray = ArrayUtils.add((char[]) null, (char) 0);
    assertTrue(Equality.equals(new char[] { 0 }, newArray));
    assertEquals(Character.TYPE, newArray.getClass().getComponentType());
    newArray = ArrayUtils.add((char[]) null, (char) 1);
    assertTrue(Equality.equals(new char[] { 1 }, newArray));
    assertEquals(Character.TYPE, newArray.getClass().getComponentType());
    final char[] array1 = new char[] { 1, 2, 3 };
    newArray = ArrayUtils.add(array1, (char) 0);
    assertTrue(Equality.equals(new char[] { 1, 2, 3, 0 }, newArray));
    assertEquals(Character.TYPE, newArray.getClass().getComponentType());
    newArray = ArrayUtils.add(array1, (char) 4);
    assertTrue(Equality.equals(new char[] { 1, 2, 3, 4 }, newArray));
    assertEquals(Character.TYPE, newArray.getClass().getComponentType());
  }

  @Test
  public void testAddObjectArrayDouble() {
    double[] newArray;
    newArray = ArrayUtils.add((double[]) null, 0);
    assertTrue(Equality.equals(new double[] { 0 }, newArray));
    assertEquals(Double.TYPE, newArray.getClass().getComponentType());
    newArray = ArrayUtils.add((double[]) null, 1);
    assertTrue(Equality.equals(new double[] { 1 }, newArray));
    assertEquals(Double.TYPE, newArray.getClass().getComponentType());
    final double[] array1 = new double[] { 1, 2, 3 };
    newArray = ArrayUtils.add(array1, 0);
    assertTrue(Equality.equals(new double[] { 1, 2, 3, 0 }, newArray));
    assertEquals(Double.TYPE, newArray.getClass().getComponentType());
    newArray = ArrayUtils.add(array1, 4);
    assertTrue(Equality.equals(new double[] { 1, 2, 3, 4 }, newArray));
    assertEquals(Double.TYPE, newArray.getClass().getComponentType());
  }

  @Test
  public void testAddObjectArrayFloat() {
    float[] newArray;
    newArray = ArrayUtils.add((float[]) null, 0);
    assertTrue(Equality.equals(new float[] { 0 }, newArray));
    assertEquals(Float.TYPE, newArray.getClass().getComponentType());
    newArray = ArrayUtils.add((float[]) null, 1);
    assertTrue(Equality.equals(new float[] { 1 }, newArray));
    assertEquals(Float.TYPE, newArray.getClass().getComponentType());
    final float[] array1 = new float[] { 1, 2, 3 };
    newArray = ArrayUtils.add(array1, 0);
    assertTrue(Equality.equals(new float[] { 1, 2, 3, 0 }, newArray));
    assertEquals(Float.TYPE, newArray.getClass().getComponentType());
    newArray = ArrayUtils.add(array1, 4);
    assertTrue(Equality.equals(new float[] { 1, 2, 3, 4 }, newArray));
    assertEquals(Float.TYPE, newArray.getClass().getComponentType());
  }

  @Test
  public void testAddObjectArrayInt() {
    int[] newArray;
    newArray = ArrayUtils.add((int[]) null, 0);
    assertTrue(Equality.equals(new int[] { 0 }, newArray));
    assertEquals(Integer.TYPE, newArray.getClass().getComponentType());
    newArray = ArrayUtils.add((int[]) null, 1);
    assertTrue(Equality.equals(new int[] { 1 }, newArray));
    assertEquals(Integer.TYPE, newArray.getClass().getComponentType());
    final int[] array1 = new int[] { 1, 2, 3 };
    newArray = ArrayUtils.add(array1, 0);
    assertTrue(Equality.equals(new int[] { 1, 2, 3, 0 }, newArray));
    assertEquals(Integer.TYPE, newArray.getClass().getComponentType());
    newArray = ArrayUtils.add(array1, 4);
    assertTrue(Equality.equals(new int[] { 1, 2, 3, 4 }, newArray));
    assertEquals(Integer.TYPE, newArray.getClass().getComponentType());
  }

  @Test
  public void testAddObjectArrayLong() {
    long[] newArray;
    newArray = ArrayUtils.add((long[]) null, 0);
    assertTrue(Equality.equals(new long[] { 0 }, newArray));
    assertEquals(Long.TYPE, newArray.getClass().getComponentType());
    newArray = ArrayUtils.add((long[]) null, 1);
    assertTrue(Equality.equals(new long[] { 1 }, newArray));
    assertEquals(Long.TYPE, newArray.getClass().getComponentType());
    final long[] array1 = new long[] { 1, 2, 3 };
    newArray = ArrayUtils.add(array1, 0);
    assertTrue(Equality.equals(new long[] { 1, 2, 3, 0 }, newArray));
    assertEquals(Long.TYPE, newArray.getClass().getComponentType());
    newArray = ArrayUtils.add(array1, 4);
    assertTrue(Equality.equals(new long[] { 1, 2, 3, 4 }, newArray));
    assertEquals(Long.TYPE, newArray.getClass().getComponentType());
  }

  @Test
  public void testAddObjectArrayShort() {
    short[] newArray;
    newArray = ArrayUtils.add((short[]) null, (short) 0);
    assertTrue(Equality.equals(new short[] { 0 }, newArray));
    assertEquals(Short.TYPE, newArray.getClass().getComponentType());
    newArray = ArrayUtils.add((short[]) null, (short) 1);
    assertTrue(Equality.equals(new short[] { 1 }, newArray));
    assertEquals(Short.TYPE, newArray.getClass().getComponentType());
    final short[] array1 = new short[] { 1, 2, 3 };
    newArray = ArrayUtils.add(array1, (short) 0);
    assertTrue(Equality.equals(new short[] { 1, 2, 3, 0 }, newArray));
    assertEquals(Short.TYPE, newArray.getClass().getComponentType());
    newArray = ArrayUtils.add(array1, (short) 4);
    assertTrue(Equality.equals(new short[] { 1, 2, 3, 4 }, newArray));
    assertEquals(Short.TYPE, newArray.getClass().getComponentType());
  }

  @Test
  public void testAddObjectArrayObject() {
    Object[] newArray;
    newArray = ArrayUtils.add((Object[]) null, null);
    assertTrue(Equality.equals((new Object[] { null }), newArray));
    assertEquals(Object.class, newArray.getClass().getComponentType());

    newArray = ArrayUtils.add((Object[]) null, "a");
    assertTrue(Equality.equals((new String[] { "a" }), newArray));

    // FIXME: see fixme at Arrays.add(T[], T)
    //assertTrue(Equality.equals((new Object[] { "a" }), newArray));

    assertEquals(String.class, newArray.getClass().getComponentType());

    final String[] stringArray1 = new String[] { "a", "b", "c" };
    newArray = ArrayUtils.add(stringArray1, null);
    assertTrue(Equality.equals((new String[] { "a", "b", "c", null }), newArray));
    assertEquals(String.class, newArray.getClass().getComponentType());

    newArray = ArrayUtils.add(stringArray1, "d");
    assertTrue(Equality.equals((new String[] { "a", "b", "c", "d" }), newArray));
    assertEquals(String.class, newArray.getClass().getComponentType());

    Number[] numberArray1 = new Number[] { new Integer(1), new Double(2) };
    newArray = ArrayUtils.add(numberArray1, new Float(3));
    assertTrue(Equality.equals((new Number[] { new Integer(1), new Double(2),
        new Float(3) }), newArray));
    assertEquals(Number.class, newArray.getClass().getComponentType());

    numberArray1 = null;
    newArray = ArrayUtils.add(numberArray1, new Float(3));
    assertTrue(Equality.equals((new Float[] { new Float(3) }), newArray));
    assertEquals(Float.class, newArray.getClass().getComponentType());

    numberArray1 = null;
    newArray = ArrayUtils.add(numberArray1, null);
    assertTrue(Equality.equals((new Object[] { null }), newArray));
    assertEquals(Object.class, newArray.getClass().getComponentType());
  }

  @Test
  public void testAddObjectArrayToObjectArray() {
    assertNull(ArrayUtils.addAll((Object[]) null, (Object[]) null));
    Object[] newArray;
    final String[] stringArray1 = new String[] { "a", "b", "c" };
    final String[] stringArray2 = new String[] { "1", "2", "3" };
    newArray = ArrayUtils.addAll(stringArray1, null);
    assertNotSame(stringArray1, newArray);
    assertTrue(Equality.equals(stringArray1, newArray));
    assertTrue(Equality.equals((new String[] { "a", "b", "c" }), newArray));
    assertEquals(String.class, newArray.getClass().getComponentType());
    newArray = ArrayUtils.addAll(null, stringArray2);
    assertNotSame(stringArray2, newArray);
    assertTrue(Equality.equals(stringArray2, newArray));
    assertTrue(Equality.equals((new String[] { "1", "2", "3" }), newArray));
    assertEquals(String.class, newArray.getClass().getComponentType());
    newArray = ArrayUtils.addAll(stringArray1, stringArray2);
    assertTrue(Equality.equals((new String[] { "a", "b", "c", "1", "2", "3" }),
        newArray));
    assertEquals(String.class, newArray.getClass().getComponentType());
    newArray = ArrayUtils.addAll(ArrayUtils.EMPTY_STRING_ARRAY, null);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_STRING_ARRAY, newArray));
    assertTrue(Equality.equals((new String[] {}), newArray));
    assertEquals(String.class, newArray.getClass().getComponentType());
    newArray = ArrayUtils.addAll(null, ArrayUtils.EMPTY_STRING_ARRAY);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_STRING_ARRAY, newArray));
    assertTrue(Equality.equals((new String[] {}), newArray));
    assertEquals(String.class, newArray.getClass().getComponentType());
    newArray = ArrayUtils.addAll(ArrayUtils.EMPTY_STRING_ARRAY,
        ArrayUtils.EMPTY_STRING_ARRAY);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_STRING_ARRAY, newArray));
    assertTrue(Equality.equals((new String[] {}), newArray));
    assertEquals(String.class, newArray.getClass().getComponentType());
    final String[] stringArrayNull = new String[] { null };
    newArray = ArrayUtils.addAll(stringArrayNull, stringArrayNull);
    assertTrue(Equality.equals((new String[] { null, null }), newArray));
    assertEquals(String.class, newArray.getClass().getComponentType());

    // boolean
    assertTrue(Equality.equals(new boolean[] { true, false, false, true }, ArrayUtils
        .addAll(new boolean[] { true, false }, new boolean[] { false, true })));

    assertTrue(Equality.equals(new boolean[] { false, true }, ArrayUtils.addAll(null,
        new boolean[] { false, true })));

    assertTrue(Equality.equals(new boolean[] { true, false }, ArrayUtils.addAll(
        new boolean[] { true, false }, null)));

    // char
    assertTrue(Equality.equals(new char[] { 'a', 'b', 'c', 'd' }, ArrayUtils.addAll(
        new char[] { 'a', 'b' }, new char[] { 'c', 'd' })));

    assertTrue(Equality.equals(new char[] { 'c', 'd' }, ArrayUtils.addAll(null,
        new char[] { 'c', 'd' })));

    assertTrue(Equality.equals(new char[] { 'a', 'b' }, ArrayUtils.addAll(new char[] {
        'a', 'b' }, null)));

    // byte
    assertTrue(Equality.equals(new byte[] { (byte) 0, (byte) 1, (byte) 2,
        (byte) 3 }, ArrayUtils.addAll(new byte[] { (byte) 0, (byte) 1 },
        new byte[] { (byte) 2, (byte) 3 })));

    assertTrue(Equality.equals(new byte[] { (byte) 2, (byte) 3 }, ArrayUtils.addAll(
        null, new byte[] { (byte) 2, (byte) 3 })));

    assertTrue(Equality.equals(new byte[] { (byte) 0, (byte) 1 }, ArrayUtils.addAll(
        new byte[] { (byte) 0, (byte) 1 }, null)));

    // short
    assertTrue(Equality.equals(new short[] { (short) 10, (short) 20, (short) 30,
        (short) 40 }, ArrayUtils.addAll(new short[] { (short) 10, (short) 20 },
        new short[] { (short) 30, (short) 40 })));

    assertTrue(Equality.equals(new short[] { (short) 30, (short) 40 }, ArrayUtils
        .addAll(null, new short[] { (short) 30, (short) 40 })));

    assertTrue(Equality.equals(new short[] { (short) 10, (short) 20 }, ArrayUtils
        .addAll(new short[] { (short) 10, (short) 20 }, null)));

    // int
    assertTrue(Equality.equals(new int[] { 1, 1000, -1000, -1 }, ArrayUtils.addAll(
        new int[] { 1, 1000 }, new int[] { -1000, -1 })));

    assertTrue(Equality.equals(new int[] { -1000, -1 }, ArrayUtils.addAll(null,
        new int[] { -1000, -1 })));

    assertTrue(Equality.equals(new int[] { 1, 1000 }, ArrayUtils.addAll(new int[] {
        1, 1000 }, null)));

    // long
    assertTrue(Equality.equals(new long[] { 1L, -1L, 1000L, -1000L }, ArrayUtils
        .addAll(new long[] { 1L, -1L }, new long[] { 1000L, -1000L })));

    assertTrue(Equality.equals(new long[] { 1000L, -1000L }, ArrayUtils.addAll(null,
        new long[] { 1000L, -1000L })));

    assertTrue(Equality.equals(new long[] { 1L, -1L }, ArrayUtils.addAll(new long[] {
        1L, -1L }, null)));

    // float
    assertTrue(Equality.equals(new float[] { 10.5f, 10.1f, 1.6f, 0.01f }, ArrayUtils
        .addAll(new float[] { 10.5f, 10.1f }, new float[] { 1.6f, 0.01f })));

    assertTrue(Equality.equals(new float[] { 1.6f, 0.01f }, ArrayUtils.addAll(null,
        new float[] { 1.6f, 0.01f })));

    assertTrue(Equality.equals(new float[] { 10.5f, 10.1f }, ArrayUtils.addAll(
        new float[] { 10.5f, 10.1f }, null)));

    // double
    assertTrue(Equality.equals(new double[] { Math.PI, -Math.PI, 0, 9.99 },
        ArrayUtils.addAll(new double[] { Math.PI, -Math.PI }, new double[] { 0,
            9.99 })));

    assertTrue(Equality.equals(new double[] { 0, 9.99 }, ArrayUtils.addAll(null,
        new double[] { 0, 9.99 })));

    assertTrue(Equality.equals(new double[] { Math.PI, -Math.PI }, ArrayUtils.addAll(
        new double[] { Math.PI, -Math.PI }, null)));

  }

  @Test
  public void testAddObjectAtIndex() {
    Object[] newArray;
    newArray = ArrayUtils.add((Object[]) null, 0, null);
    assertTrue(Equality.equals((new Object[] { null }), newArray));

    // FIXME: see fixme at Arrays.add(T[], int, T)
    // assertEquals(Object.class, newArray.getClass().getComponentType());

    newArray = ArrayUtils.add((Object[]) null, 0, "a");
    assertTrue(Equality.equals((new String[] { "a" }), newArray));

    // FIXME: see fixme at Arrays.add(T[], int, T)
    // assertTrue(Equality.equals((new Object[] { "a" }), newArray));

    assertEquals(String.class, newArray.getClass().getComponentType());
    final String[] stringArray1 = new String[] { "a", "b", "c" };
    newArray = ArrayUtils.add(stringArray1, 0, null);
    assertTrue(Equality.equals((new String[] { null, "a", "b", "c" }), newArray));
    assertEquals(String.class, newArray.getClass().getComponentType());
    newArray = ArrayUtils.add(stringArray1, 1, null);
    assertTrue(Equality.equals((new String[] { "a", null, "b", "c" }), newArray));
    assertEquals(String.class, newArray.getClass().getComponentType());
    newArray = ArrayUtils.add(stringArray1, 3, null);
    assertTrue(Equality.equals((new String[] { "a", "b", "c", null }), newArray));
    assertEquals(String.class, newArray.getClass().getComponentType());
    newArray = ArrayUtils.add(stringArray1, 3, "d");
    assertTrue(Equality.equals((new String[] { "a", "b", "c", "d" }), newArray));
    assertEquals(String.class, newArray.getClass().getComponentType());
    assertEquals(String.class, newArray.getClass().getComponentType());

    final Object[] o = new Object[] { "1", "2", "4" };
    final Object[] result = ArrayUtils.add(o, 2, "3");
    final Object[] result2 = ArrayUtils.add(o, 3, "5");

    assertNotNull(result);
    assertEquals(4, result.length);
    assertEquals("1", result[0]);
    assertEquals("2", result[1]);
    assertEquals("3", result[2]);
    assertEquals("4", result[3]);
    assertNotNull(result2);
    assertEquals(4, result2.length);
    assertEquals("1", result2[0]);
    assertEquals("2", result2[1]);
    assertEquals("4", result2[2]);
    assertEquals("5", result2[3]);

    // boolean tests
    boolean[] booleanArray = ArrayUtils.add(null, 0, true);
    assertTrue(Equality.equals(new boolean[] { true }, booleanArray));
    try {
      booleanArray = ArrayUtils.add(null, -1, true);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 0", e.getMessage());
    }
    booleanArray = ArrayUtils.add(new boolean[] { true }, 0, false);
    assertTrue(Equality.equals(new boolean[] { false, true }, booleanArray));
    booleanArray = ArrayUtils.add(new boolean[] { false }, 1, true);
    assertTrue(Equality.equals(new boolean[] { false, true }, booleanArray));
    booleanArray = ArrayUtils.add(new boolean[] { true, false }, 1, true);
    assertTrue(Equality.equals(new boolean[] { true, true, false }, booleanArray));
    try {
      booleanArray = ArrayUtils.add(new boolean[] { true, false }, 4, true);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: 4, Length: 2", e.getMessage());
    }
    try {
      booleanArray = ArrayUtils.add(new boolean[] { true, false }, -1, true);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 2", e.getMessage());
    }

    // char tests
    char[] charArray = ArrayUtils.add((char[]) null, 0, 'a');
    assertTrue(Equality.equals(new char[] { 'a' }, charArray));
    try {
      charArray = ArrayUtils.add((char[]) null, -1, 'a');
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 0", e.getMessage());
    }
    charArray = ArrayUtils.add(new char[] { 'a' }, 0, 'b');
    assertTrue(Equality.equals(new char[] { 'b', 'a' }, charArray));
    charArray = ArrayUtils.add(new char[] { 'a', 'b' }, 0, 'c');
    assertTrue(Equality.equals(new char[] { 'c', 'a', 'b' }, charArray));
    charArray = ArrayUtils.add(new char[] { 'a', 'b' }, 1, 'k');
    assertTrue(Equality.equals(new char[] { 'a', 'k', 'b' }, charArray));
    charArray = ArrayUtils.add(new char[] { 'a', 'b', 'c' }, 1, 't');
    assertTrue(Equality.equals(new char[] { 'a', 't', 'b', 'c' }, charArray));
    try {
      charArray = ArrayUtils.add(new char[] { 'a', 'b' }, 4, 'c');
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: 4, Length: 2", e.getMessage());
    }
    try {
      charArray = ArrayUtils.add(new char[] { 'a', 'b' }, -1, 'c');
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 2", e.getMessage());
    }

    // short tests
    short[] shortArray = ArrayUtils.add(new short[] { 1 }, 0, (short) 2);
    assertTrue(Equality.equals(new short[] { 2, 1 }, shortArray));
    try {
      shortArray = ArrayUtils.add((short[]) null, -1, (short) 2);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 0", e.getMessage());
    }
    shortArray = ArrayUtils.add(new short[] { 2, 6 }, 2, (short) 10);
    assertTrue(Equality.equals(new short[] { 2, 6, 10 }, shortArray));
    shortArray = ArrayUtils.add(new short[] { 2, 6 }, 0, (short) -4);
    assertTrue(Equality.equals(new short[] { -4, 2, 6 }, shortArray));
    shortArray = ArrayUtils.add(new short[] { 2, 6, 3 }, 2, (short) 1);
    assertTrue(Equality.equals(new short[] { 2, 6, 1, 3 }, shortArray));
    try {
      shortArray = ArrayUtils.add(new short[] { 2, 6 }, 4, (short) 10);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: 4, Length: 2", e.getMessage());
    }
    try {
      shortArray = ArrayUtils.add(new short[] { 2, 6 }, -1, (short) 10);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 2", e.getMessage());
    }

    // byte tests
    byte[] byteArray = ArrayUtils.add(new byte[] { 1 }, 0, (byte) 2);
    assertTrue(Equality.equals(new byte[] { 2, 1 }, byteArray));
    try {
      byteArray = ArrayUtils.add((byte[]) null, -1, (byte) 2);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 0", e.getMessage());
    }
    byteArray = ArrayUtils.add(new byte[] { 2, 6 }, 2, (byte) 3);
    assertTrue(Equality.equals(new byte[] { 2, 6, 3 }, byteArray));
    byteArray = ArrayUtils.add(new byte[] { 2, 6 }, 0, (byte) 1);
    assertTrue(Equality.equals(new byte[] { 1, 2, 6 }, byteArray));
    byteArray = ArrayUtils.add(new byte[] { 2, 6, 3 }, 2, (byte) 1);
    assertTrue(Equality.equals(new byte[] { 2, 6, 1, 3 }, byteArray));
    try {
      byteArray = ArrayUtils.add(new byte[] { 2, 6 }, 4, (byte) 3);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: 4, Length: 2", e.getMessage());
    }
    try {
      byteArray = ArrayUtils.add(new byte[] { 2, 6 }, -1, (byte) 3);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 2", e.getMessage());
    }

    // int tests
    int[] intArray = ArrayUtils.add(new int[] { 1 }, 0, 2);
    assertTrue(Equality.equals(new int[] { 2, 1 }, intArray));
    try {
      intArray = ArrayUtils.add((int[]) null, -1, 2);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 0", e.getMessage());
    }
    intArray = ArrayUtils.add(new int[] { 2, 6 }, 2, 10);
    assertTrue(Equality.equals(new int[] { 2, 6, 10 }, intArray));
    intArray = ArrayUtils.add(new int[] { 2, 6 }, 0, -4);
    assertTrue(Equality.equals(new int[] { -4, 2, 6 }, intArray));
    intArray = ArrayUtils.add(new int[] { 2, 6, 3 }, 2, 1);
    assertTrue(Equality.equals(new int[] { 2, 6, 1, 3 }, intArray));
    try {
      intArray = ArrayUtils.add(new int[] { 2, 6 }, 4, 10);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: 4, Length: 2", e.getMessage());
    }
    try {
      intArray = ArrayUtils.add(new int[] { 2, 6 }, -1, 10);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 2", e.getMessage());
    }

    // long tests
    long[] longArray = ArrayUtils.add(new long[] { 1L }, 0, 2L);
    assertTrue(Equality.equals(new long[] { 2L, 1L }, longArray));
    try {
      longArray = ArrayUtils.add((long[]) null, -1, 2L);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 0", e.getMessage());
    }
    longArray = ArrayUtils.add(new long[] { 2L, 6L }, 2, 10L);
    assertTrue(Equality.equals(new long[] { 2L, 6L, 10L }, longArray));
    longArray = ArrayUtils.add(new long[] { 2L, 6L }, 0, -4L);
    assertTrue(Equality.equals(new long[] { -4L, 2L, 6L }, longArray));
    longArray = ArrayUtils.add(new long[] { 2L, 6L, 3L }, 2, 1L);
    assertTrue(Equality.equals(new long[] { 2L, 6L, 1L, 3L }, longArray));
    try {
      longArray = ArrayUtils.add(new long[] { 2L, 6L }, 4, 10L);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: 4, Length: 2", e.getMessage());
    }
    try {
      longArray = ArrayUtils.add(new long[] { 2L, 6L }, -1, 10L);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 2", e.getMessage());
    }

    // float tests
    float[] floatArray = ArrayUtils.add(new float[] { 1.1f }, 0, 2.2f);
    assertTrue(Equality.equals(new float[] { 2.2f, 1.1f }, floatArray));
    try {
      floatArray = ArrayUtils.add((float[]) null, -1, 2.2f);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 0", e.getMessage());
    }
    floatArray = ArrayUtils.add(new float[] { 2.3f, 6.4f }, 2, 10.5f);
    assertTrue(Equality.equals(new float[] { 2.3f, 6.4f, 10.5f }, floatArray));
    floatArray = ArrayUtils.add(new float[] { 2.6f, 6.7f }, 0, -4.8f);
    assertTrue(Equality.equals(new float[] { -4.8f, 2.6f, 6.7f }, floatArray));
    floatArray = ArrayUtils.add(new float[] { 2.9f, 6.0f, 0.3f }, 2, 1.0f);
    assertTrue(Equality.equals(new float[] { 2.9f, 6.0f, 1.0f, 0.3f }, floatArray));
    try {
      floatArray = ArrayUtils.add(new float[] { 2.3f, 6.4f }, 4, 10.5f);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: 4, Length: 2", e.getMessage());
    }
    try {
      floatArray = ArrayUtils.add(new float[] { 2.3f, 6.4f }, -1, 10.5f);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 2", e.getMessage());
    }

    // double tests
    double[] doubleArray = ArrayUtils.add(new double[] { 1.1 }, 0, 2.2);
    assertTrue(Equality.equals(new double[] { 2.2, 1.1 }, doubleArray));
    try {
      doubleArray = ArrayUtils.add((double[]) null, -1, 2.2);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 0", e.getMessage());
    }
    doubleArray = ArrayUtils.add(new double[] { 2.3, 6.4 }, 2, 10.5);
    assertTrue(Equality.equals(new double[] { 2.3, 6.4, 10.5 }, doubleArray));
    doubleArray = ArrayUtils.add(new double[] { 2.6, 6.7 }, 0, -4.8);
    assertTrue(Equality.equals(new double[] { -4.8, 2.6, 6.7 }, doubleArray));
    doubleArray = ArrayUtils.add(new double[] { 2.9, 6.0, 0.3 }, 2, 1.0);
    assertTrue(Equality.equals(new double[] { 2.9, 6.0, 1.0, 0.3 }, doubleArray));
    try {
      doubleArray = ArrayUtils.add(new double[] { 2.3, 6.4 }, 4, 10.5);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: 4, Length: 2", e.getMessage());
    }
    try {
      doubleArray = ArrayUtils.add(new double[] { 2.3, 6.4 }, -1, 10.5);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 2", e.getMessage());
    }
  }

  @Test
  public void testRemoveObjectArray() {
    Object[] array;
    array = ArrayUtils.remove(new Object[] { "a" }, 0);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_OBJECT_ARRAY, array));
    assertEquals(Object.class, array.getClass().getComponentType());
    array = ArrayUtils.remove(new Object[] { "a", "b" }, 0);
    assertTrue(Equality.equals(new Object[] { "b" }, array));
    assertEquals(Object.class, array.getClass().getComponentType());
    array = ArrayUtils.remove(new Object[] { "a", "b" }, 1);
    assertTrue(Equality.equals(new Object[] { "a" }, array));
    assertEquals(Object.class, array.getClass().getComponentType());
    array = ArrayUtils.remove(new Object[] { "a", "b", "c" }, 1);
    assertTrue(Equality.equals(new Object[] { "a", "c" }, array));
    assertEquals(Object.class, array.getClass().getComponentType());
    try {
      ArrayUtils.remove(new Object[] { "a", "b" }, -1);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
    try {
      ArrayUtils.remove(new Object[] { "a", "b" }, 2);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
    try {
      ArrayUtils.remove((Object[]) null, 0);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
  }

  @Test
  public void testRemoveBooleanArray() {
    boolean[] array;
    array = ArrayUtils.remove(new boolean[] { true }, 0);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, array));
    assertEquals(Boolean.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new boolean[] { true, false }, 0);
    assertTrue(Equality.equals(new boolean[] { false }, array));
    assertEquals(Boolean.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new boolean[] { true, false }, 1);
    assertTrue(Equality.equals(new boolean[] { true }, array));
    assertEquals(Boolean.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new boolean[] { true, false, true }, 1);
    assertTrue(Equality.equals(new boolean[] { true, true }, array));
    assertEquals(Boolean.TYPE, array.getClass().getComponentType());
    try {
      ArrayUtils.remove(new boolean[] { true, false }, -1);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
    try {
      ArrayUtils.remove(new boolean[] { true, false }, 2);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
    try {
      ArrayUtils.remove((boolean[]) null, 0);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
  }

  @Test
  public void testRemoveByteArray() {
    byte[] array;
    array = ArrayUtils.remove(new byte[] { 1 }, 0);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_BYTE_ARRAY, array));
    assertEquals(Byte.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new byte[] { 1, 2 }, 0);
    assertTrue(Equality.equals(new byte[] { 2 }, array));
    assertEquals(Byte.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new byte[] { 1, 2 }, 1);
    assertTrue(Equality.equals(new byte[] { 1 }, array));
    assertEquals(Byte.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new byte[] { 1, 2, 1 }, 1);
    assertTrue(Equality.equals(new byte[] { 1, 1 }, array));
    assertEquals(Byte.TYPE, array.getClass().getComponentType());
    try {
      ArrayUtils.remove(new byte[] { 1, 2 }, -1);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
    try {
      ArrayUtils.remove(new byte[] { 1, 2 }, 2);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
    try {
      ArrayUtils.remove((byte[]) null, 0);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
  }

  @Test
  public void testRemoveCharArray() {
    char[] array;
    array = ArrayUtils.remove(new char[] { 'a' }, 0);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_CHAR_ARRAY, array));
    assertEquals(Character.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new char[] { 'a', 'b' }, 0);
    assertTrue(Equality.equals(new char[] { 'b' }, array));
    assertEquals(Character.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new char[] { 'a', 'b' }, 1);
    assertTrue(Equality.equals(new char[] { 'a' }, array));
    assertEquals(Character.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new char[] { 'a', 'b', 'c' }, 1);
    assertTrue(Equality.equals(new char[] { 'a', 'c' }, array));
    assertEquals(Character.TYPE, array.getClass().getComponentType());
    try {
      ArrayUtils.remove(new char[] { 'a', 'b' }, -1);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
    try {
      ArrayUtils.remove(new char[] { 'a', 'b' }, 2);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
    try {
      ArrayUtils.remove((char[]) null, 0);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
  }

  @Test
  public void testRemoveDoubleArray() {
    double[] array;
    array = ArrayUtils.remove(new double[] { 1 }, 0);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_DOUBLE_ARRAY, array));
    assertEquals(Double.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new double[] { 1, 2 }, 0);
    assertTrue(Equality.equals(new double[] { 2 }, array));
    assertEquals(Double.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new double[] { 1, 2 }, 1);
    assertTrue(Equality.equals(new double[] { 1 }, array));
    assertEquals(Double.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new double[] { 1, 2, 1 }, 1);
    assertTrue(Equality.equals(new double[] { 1, 1 }, array));
    assertEquals(Double.TYPE, array.getClass().getComponentType());
    try {
      ArrayUtils.remove(new double[] { 1, 2 }, -1);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
    try {
      ArrayUtils.remove(new double[] { 1, 2 }, 2);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
    try {
      ArrayUtils.remove((double[]) null, 0);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
  }

  @Test
  public void testRemoveFloatArray() {
    float[] array;
    array = ArrayUtils.remove(new float[] { 1 }, 0);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_FLOAT_ARRAY, array));
    assertEquals(Float.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new float[] { 1, 2 }, 0);
    assertTrue(Equality.equals(new float[] { 2 }, array));
    assertEquals(Float.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new float[] { 1, 2 }, 1);
    assertTrue(Equality.equals(new float[] { 1 }, array));
    assertEquals(Float.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new float[] { 1, 2, 1 }, 1);
    assertTrue(Equality.equals(new float[] { 1, 1 }, array));
    assertEquals(Float.TYPE, array.getClass().getComponentType());
    try {
      ArrayUtils.remove(new float[] { 1, 2 }, -1);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
    try {
      ArrayUtils.remove(new float[] { 1, 2 }, 2);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
    try {
      ArrayUtils.remove((float[]) null, 0);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
  }

  @Test
  public void testRemoveIntArray() {
    int[] array;
    array = ArrayUtils.remove(new int[] { 1 }, 0);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_INT_ARRAY, array));
    assertEquals(Integer.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new int[] { 1, 2 }, 0);
    assertTrue(Equality.equals(new int[] { 2 }, array));
    assertEquals(Integer.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new int[] { 1, 2 }, 1);
    assertTrue(Equality.equals(new int[] { 1 }, array));
    assertEquals(Integer.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new int[] { 1, 2, 1 }, 1);
    assertTrue(Equality.equals(new int[] { 1, 1 }, array));
    assertEquals(Integer.TYPE, array.getClass().getComponentType());
    try {
      ArrayUtils.remove(new int[] { 1, 2 }, -1);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
    try {
      ArrayUtils.remove(new int[] { 1, 2 }, 2);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
    try {
      ArrayUtils.remove((int[]) null, 0);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
  }

  @Test
  public void testRemoveLongArray() {
    long[] array;
    array = ArrayUtils.remove(new long[] { 1 }, 0);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_LONG_ARRAY, array));
    assertEquals(Long.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new long[] { 1, 2 }, 0);
    assertTrue(Equality.equals(new long[] { 2 }, array));
    assertEquals(Long.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new long[] { 1, 2 }, 1);
    assertTrue(Equality.equals(new long[] { 1 }, array));
    assertEquals(Long.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new long[] { 1, 2, 1 }, 1);
    assertTrue(Equality.equals(new long[] { 1, 1 }, array));
    assertEquals(Long.TYPE, array.getClass().getComponentType());
    try {
      ArrayUtils.remove(new long[] { 1, 2 }, -1);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
    try {
      ArrayUtils.remove(new long[] { 1, 2 }, 2);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
    try {
      ArrayUtils.remove((long[]) null, 0);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
  }

  @Test
  public void testRemoveShortArray() {
    short[] array;
    array = ArrayUtils.remove(new short[] { 1 }, 0);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_SHORT_ARRAY, array));
    assertEquals(Short.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new short[] { 1, 2 }, 0);
    assertTrue(Equality.equals(new short[] { 2 }, array));
    assertEquals(Short.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new short[] { 1, 2 }, 1);
    assertTrue(Equality.equals(new short[] { 1 }, array));
    assertEquals(Short.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.remove(new short[] { 1, 2, 1 }, 1);
    assertTrue(Equality.equals(new short[] { 1, 1 }, array));
    assertEquals(Short.TYPE, array.getClass().getComponentType());
    try {
      ArrayUtils.remove(new short[] { 1, 2 }, -1);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
    try {
      ArrayUtils.remove(new short[] { 1, 2 }, 2);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
    try {
      ArrayUtils.remove((short[]) null, 0);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
    }
  }

  @Test
  public void testRemoveElementObjectArray() {
    Object[] array;
    array = ArrayUtils.removeElement((Object[]) null, "a");
    assertNull(array);
    array = ArrayUtils.removeElement(ArrayUtils.EMPTY_OBJECT_ARRAY, "a");
    assertTrue(Equality.equals(ArrayUtils.EMPTY_OBJECT_ARRAY, array));
    assertEquals(Object.class, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new Object[] { "a" }, "a");
    assertTrue(Equality.equals(ArrayUtils.EMPTY_OBJECT_ARRAY, array));
    assertEquals(Object.class, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new Object[] { "a", "b" }, "a");
    assertTrue(Equality.equals(new Object[] { "b" }, array));
    assertEquals(Object.class, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new Object[] { "a", "b", "a" }, "a");
    assertTrue(Equality.equals(new Object[] { "b", "a" }, array));
    assertEquals(Object.class, array.getClass().getComponentType());
  }

  @Test
  public void testRemoveElementBooleanArray() {
    boolean[] array;
    array = ArrayUtils.removeElement((boolean[]) null, true);
    assertNull(array);
    array = ArrayUtils.removeElement(ArrayUtils.EMPTY_BOOLEAN_ARRAY, true);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, array));
    assertEquals(Boolean.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new boolean[] { true }, true);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, array));
    assertEquals(Boolean.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new boolean[] { true, false }, true);
    assertTrue(Equality.equals(new boolean[] { false }, array));
    assertEquals(Boolean.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new boolean[] { true, false, true }, true);
    assertTrue(Equality.equals(new boolean[] { false, true }, array));
    assertEquals(Boolean.TYPE, array.getClass().getComponentType());
  }

  @Test
  public void testRemoveElementByteArray() {
    byte[] array;
    array = ArrayUtils.removeElement((byte[]) null, (byte) 1);
    assertNull(array);
    array = ArrayUtils.removeElement(ArrayUtils.EMPTY_BYTE_ARRAY, (byte) 1);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_BYTE_ARRAY, array));
    assertEquals(Byte.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new byte[] { 1 }, (byte) 1);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_BYTE_ARRAY, array));
    assertEquals(Byte.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new byte[] { 1, 2 }, (byte) 1);
    assertTrue(Equality.equals(new byte[] { 2 }, array));
    assertEquals(Byte.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new byte[] { 1, 2, 1 }, (byte) 1);
    assertTrue(Equality.equals(new byte[] { 2, 1 }, array));
    assertEquals(Byte.TYPE, array.getClass().getComponentType());
  }

  @Test
  public void testRemoveElementCharArray() {
    char[] array;
    array = ArrayUtils.removeElement((char[]) null, 'a');
    assertNull(array);
    array = ArrayUtils.removeElement(ArrayUtils.EMPTY_CHAR_ARRAY, 'a');
    assertTrue(Equality.equals(ArrayUtils.EMPTY_CHAR_ARRAY, array));
    assertEquals(Character.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new char[] { 'a' }, 'a');
    assertTrue(Equality.equals(ArrayUtils.EMPTY_CHAR_ARRAY, array));
    assertEquals(Character.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new char[] { 'a', 'b' }, 'a');
    assertTrue(Equality.equals(new char[] { 'b' }, array));
    assertEquals(Character.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new char[] { 'a', 'b', 'a' }, 'a');
    assertTrue(Equality.equals(new char[] { 'b', 'a' }, array));
    assertEquals(Character.TYPE, array.getClass().getComponentType());
  }

  @Test
  public void testRemoveElementDoubleArray() {
    double[] array;
    array = ArrayUtils.removeElement((double[]) null, (double) 1);
    assertNull(array);
    array = ArrayUtils.removeElement(ArrayUtils.EMPTY_DOUBLE_ARRAY, (double) 1);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_DOUBLE_ARRAY, array));
    assertEquals(Double.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new double[] { 1 }, (double) 1);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_DOUBLE_ARRAY, array));
    assertEquals(Double.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new double[] { 1, 2 }, (double) 1);
    assertTrue(Equality.equals(new double[] { 2 }, array));
    assertEquals(Double.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new double[] { 1, 2, 1 }, (double) 1);
    assertTrue(Equality.equals(new double[] { 2, 1 }, array));
    assertEquals(Double.TYPE, array.getClass().getComponentType());
  }

  @Test
  public void testRemoveElementFloatArray() {
    float[] array;
    array = ArrayUtils.removeElement((float[]) null, (float) 1);
    assertNull(array);
    array = ArrayUtils.removeElement(ArrayUtils.EMPTY_FLOAT_ARRAY, (float) 1);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_FLOAT_ARRAY, array));
    assertEquals(Float.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new float[] { 1 }, (float) 1);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_FLOAT_ARRAY, array));
    assertEquals(Float.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new float[] { 1, 2 }, (float) 1);
    assertTrue(Equality.equals(new float[] { 2 }, array));
    assertEquals(Float.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new float[] { 1, 2, 1 }, (float) 1);
    assertTrue(Equality.equals(new float[] { 2, 1 }, array));
    assertEquals(Float.TYPE, array.getClass().getComponentType());
  }

  @Test
  public void testRemoveElementIntArray() {
    int[] array;
    array = ArrayUtils.removeElement((int[]) null, 1);
    assertNull(array);
    array = ArrayUtils.removeElement(ArrayUtils.EMPTY_INT_ARRAY, 1);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_INT_ARRAY, array));
    assertEquals(Integer.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new int[] { 1 }, 1);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_INT_ARRAY, array));
    assertEquals(Integer.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new int[] { 1, 2 }, 1);
    assertTrue(Equality.equals(new int[] { 2 }, array));
    assertEquals(Integer.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new int[] { 1, 2, 1 }, 1);
    assertTrue(Equality.equals(new int[] { 2, 1 }, array));
    assertEquals(Integer.TYPE, array.getClass().getComponentType());
  }

  @Test
  public void testRemoveElementLongArray() {
    long[] array;
    array = ArrayUtils.removeElement((long[]) null, (long) 1);
    assertNull(array);
    array = ArrayUtils.removeElement(ArrayUtils.EMPTY_LONG_ARRAY, (long) 1);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_LONG_ARRAY, array));
    assertEquals(Long.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new long[] { 1 }, (long) 1);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_LONG_ARRAY, array));
    assertEquals(Long.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new long[] { 1, 2 }, (long) 1);
    assertTrue(Equality.equals(new long[] { 2 }, array));
    assertEquals(Long.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new long[] { 1, 2, 1 }, (long) 1);
    assertTrue(Equality.equals(new long[] { 2, 1 }, array));
    assertEquals(Long.TYPE, array.getClass().getComponentType());
  }

  @Test
  public void testRemoveElementShortArray() {
    short[] array;
    array = ArrayUtils.removeElement((short[]) null, (short) 1);
    assertNull(array);
    array = ArrayUtils.removeElement(ArrayUtils.EMPTY_SHORT_ARRAY, (short) 1);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_SHORT_ARRAY, array));
    assertEquals(Short.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new short[] { 1 }, (short) 1);
    assertTrue(Equality.equals(ArrayUtils.EMPTY_SHORT_ARRAY, array));
    assertEquals(Short.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new short[] { 1, 2 }, (short) 1);
    assertTrue(Equality.equals(new short[] { 2 }, array));
    assertEquals(Short.TYPE, array.getClass().getComponentType());
    array = ArrayUtils.removeElement(new short[] { 1, 2, 1 }, (short) 1);
    assertTrue(Equality.equals(new short[] { 2, 1 }, array));
    assertEquals(Short.TYPE, array.getClass().getComponentType());
  }

}
