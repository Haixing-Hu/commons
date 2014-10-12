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
package com.github.haixing_hu.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.github.haixing_hu.lang.ArrayUtils;
import com.github.haixing_hu.util.expand.DoubleExpansionPolicy;
import com.github.haixing_hu.util.expand.ExpansionPolicy;
import com.github.haixing_hu.util.expand.JustFitExpansionPolicy;
import com.github.haixing_hu.util.expand.MemorySavingExpansionPolicy;

import static org.junit.Assert.*;

/**
 * Unit test of the {@link ArrayLinkedList} class.
 *
 * @author Haixing Hu
 */
public class ArrayLinkedListTest {

  /**
   * Test method for {@link ArrayLinkedList#ArrayLinkedList()}.
   */
  @Test
  public void testArrayLinkedList() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());
    assertEquals(ExpansionPolicy.getInitialCapacity(), list.capacity());
    assertSame(ExpansionPolicy.getDefault(), list.getExpansionPolicy());
  }

  /**
   * Test method for {@link ArrayLinkedList#ArrayLinkedList(int)}.
   */
  @Test
  public void testArrayLinkedListInt() {
    ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>(100);
    assertEquals(0, list.size());
    assertEquals(100, list.capacity());
    assertSame(ExpansionPolicy.getDefault(), list.getExpansionPolicy());

    list = new ArrayLinkedList<Integer>(1);
    assertEquals(0, list.size());
    assertEquals(1, list.capacity());
    assertSame(ExpansionPolicy.getDefault(), list.getExpansionPolicy());

    try {
      list = new ArrayLinkedList<Integer>(0);
      fail("should throw IllegalArgumentException");
    } catch (final IllegalArgumentException e) {
      // pass
    }
    try {
      list = new ArrayLinkedList<Integer>(-10);
      fail("should throw IllegalArgumentException");
    } catch (final IllegalArgumentException e) {
      // pass
    }
  }

  /**
   * Test method for {@link ArrayLinkedList#ArrayLinkedList(int, ExpansionPolicy)}.
   */
  @Test
  public void testArrayLinkedListIntExpansionPolicy() {

    ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>(100, DoubleExpansionPolicy.INSTANCE);
    assertEquals(0, list.size());
    assertEquals(100, list.capacity());
    assertSame(DoubleExpansionPolicy.INSTANCE, list.getExpansionPolicy());

    list = new ArrayLinkedList<Integer>(1, JustFitExpansionPolicy.INSTANCE);
    assertEquals(0, list.size());
    assertEquals(1, list.capacity());
    assertSame(JustFitExpansionPolicy.INSTANCE, list.getExpansionPolicy());

    try {
      list = new ArrayLinkedList<Integer>(0, JustFitExpansionPolicy.INSTANCE);
      fail("should throw IllegalArgumentException");
    } catch (final IllegalArgumentException e) {
      // pass
    }

    try {
      list = new ArrayLinkedList<Integer>(-10, JustFitExpansionPolicy.INSTANCE);
      fail("should throw IllegalArgumentException");
    } catch (final IllegalArgumentException e) {
      // pass
    }

    try {
      list = new ArrayLinkedList<Integer>(10, null);
      fail("should throw NullPointerException");
    } catch (final NullPointerException e) {
      // pass
    }
  }

  /**
   * Test method for {@link ArrayLinkedList#ArrayLinkedList(ExpansionPolicy)}.
   */
  @Test
  public void testArrayLinkedListExpansionPolicy() {
    ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>(DoubleExpansionPolicy.INSTANCE);
    assertEquals(0, list.size());
    assertEquals(ExpansionPolicy.getInitialCapacity(), list.capacity());
    assertSame(DoubleExpansionPolicy.INSTANCE, list.getExpansionPolicy());

    list = new ArrayLinkedList<Integer>(JustFitExpansionPolicy.INSTANCE);
    assertEquals(0, list.size());
    assertEquals(ExpansionPolicy.getInitialCapacity(), list.capacity());
    assertSame(JustFitExpansionPolicy.INSTANCE, list.getExpansionPolicy());

    try {
      list = new ArrayLinkedList<Integer>((ExpansionPolicy) null);
      fail("should throw NullPointerException");
    } catch (final NullPointerException e) {
      // pass
    }
  }

  /**
   * Test method for {@link ArrayLinkedList#ArrayLinkedList(Collection)}.
   */
  @Test
  public void testArrayLinkedListCollectionOfE() {
    final List<Integer> col = new LinkedList<Integer>();

    ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>(col);
    assertEquals(0, list.size());
    assertEquals(ExpansionPolicy.getInitialCapacity(), list.capacity());
    assertSame(ExpansionPolicy.getDefault(), list.getExpansionPolicy());

    col.add(1);
    col.add(2);
    assertEquals(0, list.size());

    list = new ArrayLinkedList<Integer>(col);
    assertEquals(2, list.size());
    assertEquals(2, list.capacity());
    assertSame(ExpansionPolicy.getDefault(), list.getExpansionPolicy());
    assertEquals(col, list);

    try {
      list = new ArrayLinkedList<Integer>((List<Integer>) null);
      fail("should throw NullPointerException");
    } catch (final NullPointerException e) {
      // pass
    }
  }

  /**
   * Test method for {@link ArrayLinkedList#ArrayLinkedList(Collection, ExpansionPolicy)}.
   */
  @Test
  public void testArrayLinkedListCollectionOfEExpansionPolicy() {
    final List<Integer> col = new LinkedList<Integer>();

    ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>(col, DoubleExpansionPolicy.INSTANCE);
    assertEquals(0, list.size());
    assertEquals(ExpansionPolicy.getInitialCapacity(), list.capacity());
    assertEquals(DoubleExpansionPolicy.INSTANCE, list.getExpansionPolicy());

    col.add(1);
    col.add(2);
    assertEquals(0, list.size());

    list = new ArrayLinkedList<Integer>(col, MemorySavingExpansionPolicy.INSTANCE);
    assertEquals(2, list.size());
    assertEquals(2, list.capacity());
    assertEquals(MemorySavingExpansionPolicy.INSTANCE, list.getExpansionPolicy());
    assertEquals(col, list);

    try {
      list = new ArrayLinkedList<Integer>(col, null);
      fail("should throw NullPointerException");
    } catch (final NullPointerException e) {
      // pass
    }

    try {
      list = new ArrayLinkedList<Integer>(null, MemorySavingExpansionPolicy.INSTANCE);
      fail("should throw NullPointerException");
    } catch (final NullPointerException e) {
      // pass
    }
  }

  /**
   * Test method for {@link ArrayLinkedList#size()}.
   */
  @Test
  public void testSize() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());
    list.add(1);
    assertEquals(1, list.size());
    list.add(2);
    assertEquals(2, list.size());
    list.add(3);
    assertEquals(3, list.size());
    list.poll();
    assertEquals(2, list.size());
    list.clear();
    assertEquals(0, list.size());
  }

  /**
   * Test method for {@link ArrayLinkedList#capacity()}.
   */
  @Test
  public void testCapacity() {
    doTestCapacity(new ArrayLinkedList<Integer>());
    doTestCapacity(new ArrayLinkedList<Integer>(JustFitExpansionPolicy.INSTANCE));
    doTestCapacity(new ArrayLinkedList<Integer>(DoubleExpansionPolicy.INSTANCE));
    doTestCapacity(new ArrayLinkedList<Integer>(MemorySavingExpansionPolicy.INSTANCE));
  }

  private void doTestCapacity(final ArrayLinkedList<Integer> list) {
    final int capacity1 = ExpansionPolicy.getInitialCapacity();

    assertEquals(0, list.size());
    assertEquals(capacity1, list.capacity());

    list.add(1);
    assertEquals(1, list.size());
    assertEquals(capacity1, list.capacity());

    list.add(2);
    assertEquals(2, list.size());
    assertEquals(capacity1, list.capacity());

    list.add(3);
    assertEquals(3, list.size());
    assertEquals(capacity1, list.capacity());

    list.poll();
    assertEquals(2, list.size());
    assertEquals(capacity1, list.capacity());

    list.clear();
    assertEquals(0, list.size());
    assertEquals(capacity1, list.capacity());


    for (int i = 0; i < capacity1; ++i) {
      list.add(i);
    }
    assertEquals(capacity1, list.size());
    assertEquals(capacity1, list.capacity());

    list.add(-1);
    final ExpansionPolicy policy = list.getExpansionPolicy();
    final int capacity2 = policy.getNextCapacity(capacity1, list.size());
    assertEquals(capacity1 + 1, list.size());
    assertEquals(capacity2, list.capacity());

    list.clear();
    assertEquals(0, list.size());
    assertEquals(capacity2, list.capacity());


    for (int i = 0; i < capacity2; ++i) {
      list.add(i);
    }
    assertEquals(capacity2, list.size());
    assertEquals(capacity2, list.capacity());

    list.add(-1);
    final int capacity3 = policy.getNextCapacity(capacity2, list.size());
    assertEquals(capacity2 + 1, list.size());
    assertEquals(capacity3, list.capacity());

    list.clear();
    assertEquals(0, list.size());
    assertEquals(capacity3, list.capacity());

  }

  /**
   * Test method for {@link ArrayLinkedList#isEmpty()}.
   */
  @Test
  public void testIsEmpty() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());
    assertEquals(true, list.isEmpty());
    list.add(1);
    assertEquals(1, list.size());
    assertEquals(false, list.isEmpty());
    list.add(2);
    assertEquals(2, list.size());
    assertEquals(false, list.isEmpty());
    list.add(3);
    assertEquals(3, list.size());
    assertEquals(false, list.isEmpty());
    list.poll();
    assertEquals(2, list.size());
    assertEquals(false, list.isEmpty());
    list.clear();
    assertEquals(0, list.size());
    assertEquals(true, list.isEmpty());
  }

  /**
   * Test method for {@link ArrayLinkedList#clear()}.
   */
  @Test
  public void testClear() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());
    assertEquals(true, list.isEmpty());
    list.add(1);
    assertEquals(1, list.size());
    assertEquals(false, list.isEmpty());
    list.add(2);
    assertEquals(2, list.size());
    assertEquals(false, list.isEmpty());
    list.add(3);
    assertEquals(3, list.size());
    assertEquals(false, list.isEmpty());
    list.poll();
    assertEquals(2, list.size());
    assertEquals(false, list.isEmpty());
    list.clear();
    assertEquals(0, list.size());
    assertEquals(true, list.isEmpty());

    list.add(1);
    assertEquals(1, list.size());
    assertEquals(false, list.isEmpty());
    list.add(2);
    assertEquals(2, list.size());
    assertEquals(false, list.isEmpty());
    list.add(3);
    assertEquals(3, list.size());
    assertEquals(false, list.isEmpty());
    list.poll();
    assertEquals(2, list.size());
    assertEquals(false, list.isEmpty());
    list.clear();
    assertEquals(0, list.size());
    assertEquals(true, list.isEmpty());
  }

  /**
   * Test method for {@link ArrayLinkedList#contains(Object)}.
   */
  @Test
  public void testContainsObject() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(false, list.contains(Integer.valueOf(0)));

    // fill the list
    for (int i = 0; i < 100; ++i) {
      list.add(i);
    }

    assertEquals(true, list.contains(Integer.valueOf(0)));
    assertEquals(true, list.contains(Integer.valueOf(1)));
    assertEquals(true, list.contains(Integer.valueOf(10)));
    assertEquals(true, list.contains(Integer.valueOf(99)));
    assertEquals(false, list.contains(Integer.valueOf(100)));
    assertEquals(false, list.contains(Integer.valueOf(-10)));

    assertEquals(false, list.contains(null));
    list.addFirst(null);
    assertEquals(true, list.contains(null));
  }


  /**
   * Test method for {@link ArrayLinkedList#iterator()}.
   * <p>
   * This function only test the {@link ArrayLinkedList#iterator()} method.
   * The {@link ArrayLinkedList.ListIter} class will be test in the
   * {@link ArrayLinkedListListIterTest} class.
   * </p>
   */
  @Test
  public void testIterator() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    Iterator<Integer> iter = list.iterator();
    assertEquals(false, iter.hasNext());

    for (int i = 0; i < 100; ++i) {
      list.add(i);
    }
    iter = list.listIterator();
    for (int i = 0; i < 100; ++i) {
      assertEquals(true, iter.hasNext());
      assertEquals(Integer.valueOf(i), iter.next());
    }
  }

  /**
   * Test method for {@link ArrayLinkedList#add(Object)}.
   */
  @Test
  public void testAddE() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());

    list.add(1);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.getLast());

    list.add(2);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.getLast());

    list.add(3);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.getLast());

    list.add(null);
    assertEquals(4, list.size());
    assertEquals(null, list.getLast());

    assertArrayEquals(new Object[]{1, 2, 3, null}, list.toArray());

    list.clear();
    assertEquals(0, list.size());
    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.add(i);
      expected.add(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);
  }

  /**
   * Test method for {@link ArrayLinkedList#remove(Object)}.
   */
  @Test
  public void testRemoveObject() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(false, list.remove(Integer.valueOf(0)));
    assertEquals(false, list.remove(null));

    Integer[] array = {0, null, 0, 1, 1, 2, 3, null, 3, 0};
    list.addAll(Arrays.asList(array));

    assertEquals(false, list.remove(Integer.valueOf(10)));
    assertEquals(array.length, list.size());

    assertEquals(true, list.remove(Integer.valueOf(0)));
    array = new Integer[]{null, 0, 1, 1, 2, 3, null, 3, 0};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.remove(Integer.valueOf(0)));
    array = new Integer[]{null, 1, 1, 2, 3, null, 3, 0};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.remove(null));
    array = new Integer[]{1, 1, 2, 3, null, 3, 0};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.remove(null));
    array = new Integer[]{1, 1, 2, 3, 3, 0};
    assertArrayEquals(array, list.toArray());

    assertEquals(false, list.remove(null));
    array = new Integer[]{1, 1, 2, 3, 3, 0};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.remove(Integer.valueOf(0)));
    array = new Integer[]{1, 1, 2, 3, 3};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.remove(Integer.valueOf(2)));
    array = new Integer[]{1, 1, 3, 3};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.remove(Integer.valueOf(3)));
    array = new Integer[]{1, 1, 3};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.remove(Integer.valueOf(1)));
    array = new Integer[]{1, 3};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.remove(Integer.valueOf(3)));
    array = new Integer[]{1};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.remove(Integer.valueOf(1)));
    array = new Integer[]{};
    assertArrayEquals(array, list.toArray());

  }

  /**
   * Test method for {@link ArrayLinkedList#containsAll(Collection)}.
   */
  @Test
  public void testContainsAllCollectionOfQ() {
    final Collection<Integer> col = new LinkedList<Integer>();
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();

    assertEquals(true, list.containsAll(col));
    col.add(1);
    assertEquals(false, list.containsAll(col));

    // fill the list
    for (int i = 0; i < 100; ++i) {
      list.add(i);
    }

    assertEquals(true, list.containsAll(col));
    col.clear();
    assertEquals(true, list.containsAll(col));

    col.add(1);
    col.add(-1);
    assertEquals(false, list.containsAll(col));

    col.clear();
    for (int i = 100; i > 0; --i) {
      col.add(i - 1);
    }
    assertEquals(true, list.containsAll(col));
    assertEquals(false, list.equals(col));
  }

  /**
   * Test method for {@link ArrayLinkedList#addAll(Collection)}.
   */
  @Test
  public void testAddAllCollectionOfQextendsE() {
    final Collection<Integer> col = new LinkedList<Integer>();
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();

    list.addAll(col);
    assertEquals(0, list.size());

    col.add(1);
    list.addAll(col);
    assertEquals(1, list.size());

    col.add(2);
    col.add(null);
    list.addAll(col);
    final Integer[] array1 = {1, 1, 2, null};
    assertArrayEquals(array1, list.toArray());

    final Integer[] array2 = new Integer[1000];
    for (int i = 0; i < 1000; ++i) {
      array2[i] = Integer.valueOf(i);
    }

    list.addAll(Arrays.asList(array2));
    final Integer[] array3 = ArrayUtils.addAll(array1, array2);
    assertArrayEquals(array3, list.toArray());
  }

  /**
   * Test method for {@link ArrayLinkedList#addAll(int, Collection)}.
   */
  @Test
  public void testAddAllIntCollectionOfQextendsE() {
    Collection<Integer> col = new LinkedList<Integer>();
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();

    try {
      list.addAll(-1, col);
      fail("should throw IndexOutOfBoundsException");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
    try {
      list.addAll(1, col);
      fail("should throw IndexOutOfBoundsException");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }

    list.addAll(0, col);
    assertEquals(0, list.size());

    col.add(1);
    list.addAll(0, col);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.getFirst());

    final Integer[] array0 = {1, 2, null};
    col = Arrays.asList(array0);

    list.addAll(0, col);
    final Integer[] array1 = {1, 2, null, 1};
    assertArrayEquals(array1, list.toArray());

    list.addAll(1, col);
    final Integer[] array2 = {1, 1, 2, null, 2, null, 1};
    assertArrayEquals(array2, list.toArray());

    list.addAll(list.size(), col);
    final Integer[] array3 = {1, 1, 2, null, 2, null, 1, 1, 2, null};
    assertArrayEquals(array3, list.toArray());

    final Integer[] array4 = new Integer[1000];
    for (int i = 0; i < 1000; ++i) {
      array4[i] = Integer.valueOf(i);
    }

    list.addAll(list.size(), Arrays.asList(array4));
    final Integer[] array5 = ArrayUtils.addAll(array3, array4);
    assertArrayEquals(array5, list.toArray());
  }

  /**
   * Test method for {@link ArrayLinkedList#get(int)}.
   */
  @Test
  public void testGetInt() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());

    list.add(1);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.get(0));

    list.add(2);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.get(1));

    list.add(3);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.get(2));

    list.add(null);
    assertEquals(4, list.size());
    assertEquals(null, list.get(3));

    assertArrayEquals(new Object[]{1, 2, 3, null}, list.toArray());

    list.clear();
    assertEquals(0, list.size());
    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.add(i);
      expected.add(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      assertEquals(expected.get(i), list.get(i));
    }

    try {
      list.get(-1);
      fail("should throw IndexOutOfBoundsException");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }

    try {
      list.get(list.size());
      fail("should throw IndexOutOfBoundsException");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }

    list.clear();
    try {
      list.get(0);
      fail("should throw IndexOutOfBoundsException");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
  }

  /**
   * Test method for {@link ArrayLinkedList#set(int, Object)}.
   */
  @Test
  public void testSetIntE() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());

    list.add(1);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.get(0));
    list.set(0, -1);
    assertEquals(Integer.valueOf(-1), list.get(0));

    list.add(2);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.get(1));
    list.set(1, -2);
    assertEquals(Integer.valueOf(-2), list.get(1));

    list.add(3);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.get(2));
    list.set(2, -3);
    assertEquals(Integer.valueOf(-3), list.get(2));

    list.add(null);
    assertEquals(4, list.size());
    assertEquals(null, list.get(3));
    list.set(3, null);
    assertEquals(null, list.get(3));

    assertArrayEquals(new Object[]{-1, -2, -3, null}, list.toArray());

    list.clear();
    assertEquals(0, list.size());
    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.add(i);
      expected.add(i);
      list.set(i, -i);
      expected.set(i, -i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);

    try {
      list.set(-1, 0);
      fail("should throw IndexOutOfBoundsException");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }

    try {
      list.set(list.size(), 1);
      fail("should throw IndexOutOfBoundsException");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }

    list.clear();
    try {
      list.set(0, 0);
      fail("should throw IndexOutOfBoundsException");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
  }

  /**
   * Test method for {@link ArrayLinkedList#add(int, Object)}.
   */
  @Test
  public void testAddIntE() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    Integer[] array = null;

    assertEquals(0, list.size());
    try {
      list.add(-1, Integer.valueOf(0));
      fail("should throw IndexOutOfBoundsException");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
    try {
      list.add(1, Integer.valueOf(0));
      fail("should throw IndexOutOfBoundsException");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }

    list.add(0, Integer.valueOf(0));
    array = new Integer[]{0};
    assertArrayEquals(array, list.toArray());

    list.add(0, Integer.valueOf(1));
    array = new Integer[]{1, 0};
    assertArrayEquals(array, list.toArray());

    list.add(2, null);
    array = new Integer[]{1, 0, null};
    assertArrayEquals(array, list.toArray());

    for (int i = 0; i < 10; ++i) {
      array = ArrayUtils.add(array, 2, i);
      list.add(2, i);
    }
    assertArrayEquals(array, list.toArray());
  }

  /**
   * Test method for {@link ArrayLinkedList#remove(int)}.
   */
  @Test
  public void testRemoveInt() {
    ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    try {
      list.remove(0);
      fail("should throw IndexOutOfBoundsException");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }

    Integer[] array = {0, 1, 2, null, 4, 5, null, 6, 7, 8, null, 9};
    list = new ArrayLinkedList<Integer>(Arrays.asList(array));

    list.remove(0);
    array = ArrayUtils.remove(array, 0);
    assertArrayEquals(array, list.toArray());

    list.remove(list.size() - 1);
    array = ArrayUtils.remove(array, array.length - 1);
    assertArrayEquals(array, list.toArray());

    list.remove(3);
    array = ArrayUtils.remove(array, 3);
    assertArrayEquals(array, list.toArray());

    try {
      list.remove(-1);
      fail("should throw IndexOutOfBoundsException");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }

    try {
      list.remove(list.size());
      fail("should throw IndexOutOfBoundsException");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
  }

  /**
   * Test method for {@link ArrayLinkedList#indexOf(Object)}.
   */
  @Test
  public void testIndexOfObject() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(-1, list.indexOf(null));

    final Integer[] array = {0, 1, 2, null, 1, null, 4, 2, null};
    list.addAll(Arrays.asList(array));
    assertEquals(0, list.indexOf(Integer.valueOf(0)));
    assertEquals(1, list.indexOf(Integer.valueOf(1)));
    assertEquals(2, list.indexOf(Integer.valueOf(2)));
    assertEquals(3, list.indexOf(null));
    assertEquals(6, list.indexOf(Integer.valueOf(4)));
    assertEquals(-1, list.indexOf(Integer.valueOf(100)));
  }

  /**
   * Test method for {@link ArrayLinkedList#lastIndexOf(Object)}.
   */
  @Test
  public void testLastIndexOfObject() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(-1, list.lastIndexOf(null));

    final Integer[] array = {0, 1, 2, null, 1, null, 4, 2, null};
    list.addAll(Arrays.asList(array));
    assertEquals(0, list.lastIndexOf(Integer.valueOf(0)));
    assertEquals(4, list.lastIndexOf(Integer.valueOf(1)));
    assertEquals(7, list.lastIndexOf(Integer.valueOf(2)));
    assertEquals(8, list.lastIndexOf(null));
    assertEquals(6, list.lastIndexOf(Integer.valueOf(4)));
    assertEquals(-1, list.lastIndexOf(Integer.valueOf(100)));
  }

  /**
   * Test method for {@link ArrayLinkedList#listIterator()}.
   * <p>
   * This function only test the {@link ArrayLinkedList#listIterator()} method.
   * The {@link ArrayLinkedList.ListIter} class will be test in the
   * {@link ArrayLinkedListListIterTest} class.
   * </p>
   */
  @Test
  public void testListIterator() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    ListIterator<Integer> iter = list.listIterator();
    assertEquals(false, iter.hasNext());
    assertEquals(false, iter.hasPrevious());
    assertEquals(0, iter.nextIndex());
    assertEquals(-1, iter.previousIndex());

    for (int i = 0; i < 100; ++i) {
      list.add(i);
    }

    iter = list.listIterator();
    assertEquals(0, iter.nextIndex());
    assertEquals(-1, iter.previousIndex());
    for (int i = 0; i < 100; ++i) {
      assertEquals(true, iter.hasNext());
      assertEquals(Integer.valueOf(i), iter.next());
    }

    assertEquals(false, iter.hasNext());
    for (int i = 99; i >= 0; --i) {
      assertEquals(true, iter.hasPrevious());
      assertEquals(Integer.valueOf(i), iter.previous());
    }

    assertEquals(false, iter.hasPrevious());
    assertEquals(0, iter.nextIndex());
    assertEquals(-1, iter.previousIndex());
  }



  /**
   * Test method for {@link ArrayLinkedList#listIterator(int)}.
   * <p>
   * This function only test the {@link ArrayLinkedList#listIterator(int)} method.
   * The {@link ArrayLinkedList.ListIter} class will be test in the
   * {@link ArrayLinkedListListIterTest} class.
   * </p>
   */
  @Test
  public void testListIteratorInt() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    ListIterator<Integer> iter = list.listIterator(0);
    assertEquals(false, iter.hasNext());
    assertEquals(false, iter.hasPrevious());
    assertEquals(0, iter.nextIndex());
    assertEquals(-1, iter.previousIndex());

    for (int i = 0; i < 100; ++i) {
      list.add(i);
    }

    iter = list.listIterator(0);
    assertEquals(0, iter.nextIndex());
    assertEquals(-1, iter.previousIndex());
    for (int i = 0; i < 100; ++i) {
      assertEquals(true, iter.hasNext());
      assertEquals(Integer.valueOf(i), iter.next());
    }
    assertEquals(false, iter.hasNext());
    for (int i = 99; i >= 0; --i) {
      assertEquals(true, iter.hasPrevious());
      assertEquals(Integer.valueOf(i), iter.previous());
    }
    assertEquals(false, iter.hasPrevious());

    iter = list.listIterator(50);
    assertEquals(50, iter.nextIndex());
    assertEquals(49, iter.previousIndex());
    for (int i = 50; i < 100; ++i) {
      assertEquals(true, iter.hasNext());
      assertEquals(Integer.valueOf(i), iter.next());
    }
    assertEquals(false, iter.hasNext());
    for (int i = 99; i >= 50; --i) {
      assertEquals(true, iter.hasPrevious());
      assertEquals(Integer.valueOf(i), iter.previous());
    }
    assertEquals(true, iter.hasPrevious());
    assertEquals(50, iter.nextIndex());
    assertEquals(49, iter.previousIndex());
  }

  /**
   * Test method for {@link ArrayLinkedList#addFirst(Object)}.
   */
  @Test
  public void testAddFirst() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());

    list.addFirst(1);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.getFirst());

    list.addFirst(2);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.getFirst());

    list.addFirst(3);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.getFirst());

    list.addFirst(null);
    assertEquals(4, list.size());
    assertEquals(null, list.getFirst());

    assertArrayEquals(new Object[]{null, 3, 2, 1}, list.toArray());

    list.clear();
    assertEquals(0, list.size());
    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.addFirst(i);
      expected.addFirst(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);
  }

  /**
   * Test method for {@link ArrayLinkedList#addLast(Object)}.
   */
  @Test
  public void testAddLast() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());

    list.addLast(1);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.getLast());

    list.addLast(2);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.getLast());

    list.addLast(3);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.getLast());

    list.addLast(null);
    assertEquals(4, list.size());
    assertEquals(null, list.getLast());

    assertArrayEquals(new Object[]{1, 2, 3, null}, list.toArray());

    list.clear();
    assertEquals(0, list.size());
    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.addLast(i);
      expected.addLast(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);
  }

  /**
   * Test method for {@link ArrayLinkedList#offerFirst(Object)}.
   */
  @Test
  public void testOfferFirst() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());

    boolean ret = list.offerFirst(1);
    assertEquals(true, ret);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.getFirst());

    ret = list.offerFirst(2);
    assertEquals(true, ret);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.getFirst());

    ret = list.offerFirst(3);
    assertEquals(true, ret);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.getFirst());

    ret = list.offerFirst(null);
    assertEquals(true, ret);
    assertEquals(4, list.size());
    assertEquals(null, list.getFirst());

    assertArrayEquals(new Object[]{null, 3, 2, 1}, list.toArray());


    list.clear();
    assertEquals(0, list.size());
    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.offerFirst(i);
      expected.offerFirst(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);

  }

  /**
   * Test method for {@link ArrayLinkedList#offerLast(Object)}.
   */
  @Test
  public void testOfferLast() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());

    boolean ret = list.offerLast(1);
    assertEquals(true, ret);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.getLast());

    ret = list.offerLast(2);
    assertEquals(true, ret);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.getLast());

    ret = list.offerLast(3);
    assertEquals(true, ret);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.getLast());

    ret = list.offerLast(null);
    assertEquals(true, ret);
    assertEquals(4, list.size());
    assertEquals(null, list.getLast());

    assertArrayEquals(new Object[]{1, 2, 3, null}, list.toArray());

    list.clear();
    assertEquals(0, list.size());
    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.offerLast(i);
      expected.offerLast(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);
  }

  /**
   * Test method for {@link ArrayLinkedList#removeFirst()}.
   */
  @Test
  public void testRemoveFirst() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());
    try {
      list.removeFirst();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }

    list.addFirst(1);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.getFirst());

    list.addFirst(2);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.getFirst());

    list.addFirst(3);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.getFirst());

    list.addFirst(null);
    assertEquals(4, list.size());
    assertEquals(null, list.getFirst());

    assertArrayEquals(new Object[]{null, 3, 2, 1}, list.toArray());

    assertEquals(null, list.removeFirst());
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.removeFirst());
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.removeFirst());
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.removeFirst());
    assertEquals(0, list.size());

    list.clear();
    assertEquals(0, list.size());
    try {
      list.removeFirst();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }

    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.addFirst(i);
      expected.addFirst(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);

    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      assertEquals(expected.removeFirst(), list.removeFirst());
      assertEquals(expected.size(), list.size());
    }

    assertEquals(0, list.size());
    try {
      list.removeFirst();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }
  }

  /**
   * Test method for {@link ArrayLinkedList#removeLast()}.
   */
  @Test
  public void testRemoveLast() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());
    try {
      list.removeLast();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }

    list.addLast(1);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.getLast());

    list.addLast(2);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.getLast());

    list.addLast(3);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.getLast());

    list.addLast(null);
    assertEquals(4, list.size());
    assertEquals(null, list.getLast());

    assertArrayEquals(new Object[]{1, 2, 3, null}, list.toArray());

    assertEquals(null, list.removeLast());
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.removeLast());
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.removeLast());
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.removeLast());
    assertEquals(0, list.size());

    list.clear();
    assertEquals(0, list.size());
    try {
      list.removeLast();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }

    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.addLast(i);
      expected.addLast(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);

    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      assertEquals(expected.removeLast(), list.removeLast());
      assertEquals(expected.size(), list.size());
    }

    assertEquals(0, list.size());
    try {
      list.removeLast();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }
  }

  /**
   * Test method for {@link ArrayLinkedList#pollFirst()}.
   */
  @Test
  public void testPollFirst() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());
    assertEquals(null, list.pollFirst());

    list.addFirst(1);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.getFirst());

    list.addFirst(2);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.getFirst());

    list.addFirst(3);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.getFirst());

    list.addFirst(null);
    assertEquals(4, list.size());
    assertEquals(null, list.getFirst());

    assertArrayEquals(new Object[]{null, 3, 2, 1}, list.toArray());

    assertEquals(null, list.pollFirst());
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.pollFirst());
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.pollFirst());
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.pollFirst());
    assertEquals(0, list.size());

    list.clear();
    assertEquals(0, list.size());
    assertEquals(null, list.pollFirst());

    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.addFirst(i);
      expected.addFirst(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);

    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      assertEquals(expected.pollFirst(), list.pollFirst());
      assertEquals(expected.size(), list.size());
    }

    assertEquals(0, list.size());
    assertEquals(null, list.pollFirst());
  }

  /**
   * Test method for {@link ArrayLinkedList#pollLast()}.
   */
  @Test
  public void testPollLast() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());
    assertEquals(null, list.pollLast());

    list.addLast(1);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.getLast());

    list.addLast(2);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.getLast());

    list.addLast(3);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.getLast());

    list.addLast(null);
    assertEquals(4, list.size());
    assertEquals(null, list.getLast());

    assertArrayEquals(new Object[]{1, 2, 3, null}, list.toArray());

    assertEquals(null, list.pollLast());
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.pollLast());
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.pollLast());
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.pollLast());
    assertEquals(0, list.size());

    list.clear();
    assertEquals(0, list.size());
    assertEquals(null, list.pollLast());

    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.addLast(i);
      expected.addLast(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);

    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      assertEquals(expected.pollLast(), list.pollLast());
      assertEquals(expected.size(), list.size());
    }

    assertEquals(0, list.size());
    assertEquals(null, list.pollLast());
  }

  /**
   * Test method for {@link ArrayLinkedList#getFirst()}.
   */
  @Test
  public void testGetFirst() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());
    try {
      list.getFirst();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }

    list.addFirst(1);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.getFirst());

    list.addFirst(2);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.getFirst());

    list.addFirst(3);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.getFirst());

    list.addFirst(null);
    assertEquals(4, list.size());
    assertEquals(null, list.getFirst());

    assertArrayEquals(new Object[]{null, 3, 2, 1}, list.toArray());

    list.clear();
    assertEquals(0, list.size());
    try {
      list.getFirst();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }

    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.addFirst(i);
      expected.addFirst(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);
    assertEquals(expected.getFirst(), list.getFirst());
  }

  /**
   * Test method for {@link ArrayLinkedList#getLast()}.
   */
  @Test
  public void testGetLast() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());
    try {
      list.getLast();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }

    list.addLast(1);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.getLast());

    list.addLast(2);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.getLast());

    list.addLast(3);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.getLast());

    list.addLast(null);
    assertEquals(4, list.size());
    assertEquals(null, list.getLast());

    assertArrayEquals(new Object[]{1, 2, 3, null}, list.toArray());

    list.clear();
    assertEquals(0, list.size());
    try {
      list.getLast();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }

    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.addLast(i);
      expected.addLast(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);
    assertEquals(expected.getLast(), list.getLast());
  }

  /**
   * Test method for {@link ArrayLinkedList#peekFirst()}.
   */
  @Test
  public void testPeekFirst() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());
    assertEquals(null, list.peekFirst());

    list.addFirst(1);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.peekFirst());

    list.addFirst(2);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.peekFirst());

    list.addFirst(3);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.peekFirst());

    list.addFirst(null);
    assertEquals(4, list.size());
    assertEquals(null, list.peekFirst());

    assertArrayEquals(new Object[]{null, 3, 2, 1}, list.toArray());

    list.clear();
    assertEquals(0, list.size());
    assertEquals(null, list.peekFirst());

    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.addFirst(i);
      expected.addFirst(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);
    assertEquals(expected.peekFirst(), list.peekFirst());
  }

  /**
   * Test method for {@link ArrayLinkedList#peekLast()}.
   */
  @Test
  public void testPeekLast() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());
    assertEquals(null, list.peekLast());

    list.addLast(1);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.peekLast());

    list.addLast(2);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.peekLast());

    list.addLast(3);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.peekLast());

    list.addLast(null);
    assertEquals(4, list.size());
    assertEquals(null, list.peekLast());

    assertArrayEquals(new Object[]{1, 2, 3, null}, list.toArray());

    list.clear();
    assertEquals(0, list.size());
    assertEquals(null, list.peekLast());

    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.addLast(i);
      expected.addLast(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);
    assertEquals(expected.peekLast(), list.peekLast());
  }

  /**
   * Test method for {@link ArrayLinkedList#removeFirstOccurrence(Object)}.
   */
  @Test
  public void testRemoveFirstOccurrence() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(false, list.removeFirstOccurrence(Integer.valueOf(0)));
    assertEquals(false, list.removeFirstOccurrence(null));

    Integer[] array = {0, null, 0, 1, 1, 2, 3, null, 3, 0};
    list.addAll(Arrays.asList(array));

    assertEquals(false, list.removeFirstOccurrence(Integer.valueOf(10)));
    assertEquals(array.length, list.size());

    assertEquals(true, list.removeFirstOccurrence(Integer.valueOf(0)));
    array = new Integer[]{null, 0, 1, 1, 2, 3, null, 3, 0};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.removeFirstOccurrence(Integer.valueOf(0)));
    array = new Integer[]{null, 1, 1, 2, 3, null, 3, 0};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.removeFirstOccurrence(null));
    array = new Integer[]{1, 1, 2, 3, null, 3, 0};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.removeFirstOccurrence(null));
    array = new Integer[]{1, 1, 2, 3, 3, 0};
    assertArrayEquals(array, list.toArray());


    assertEquals(false, list.removeFirstOccurrence(null));
    array = new Integer[]{1, 1, 2, 3, 3, 0};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.removeFirstOccurrence(Integer.valueOf(0)));
    array = new Integer[]{1, 1, 2, 3, 3};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.removeFirstOccurrence(Integer.valueOf(2)));
    array = new Integer[]{1, 1, 3, 3};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.removeFirstOccurrence(Integer.valueOf(3)));
    array = new Integer[]{1, 1, 3};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.removeFirstOccurrence(Integer.valueOf(1)));
    array = new Integer[]{1, 3};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.removeFirstOccurrence(Integer.valueOf(3)));
    array = new Integer[]{1};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.removeFirstOccurrence(Integer.valueOf(1)));
    array = new Integer[]{};
    assertArrayEquals(array, list.toArray());

  }

  /**
   * Test method for {@link ArrayLinkedList#removeLastOccurrence(Object)}.
   */
  @Test
  public void testRemoveLastOccurrence() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(false, list.removeLastOccurrence(Integer.valueOf(0)));
    assertEquals(false, list.removeLastOccurrence(null));

    Integer[] array = {0, null, 0, 1, 1, 2, 3, null, 3, 0};
    list.addAll(Arrays.asList(array));

    assertEquals(false, list.removeLastOccurrence(Integer.valueOf(10)));
    assertEquals(array.length, list.size());

    assertEquals(true, list.removeLastOccurrence(Integer.valueOf(0)));
    array = new Integer[]{0, null, 0, 1, 1, 2, 3, null, 3};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.removeLastOccurrence(Integer.valueOf(0)));
    array = new Integer[]{0, null, 1, 1, 2, 3, null, 3};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.removeLastOccurrence(null));
    array = new Integer[]{0, null, 1, 1, 2, 3, 3};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.removeLastOccurrence(null));
    array = new Integer[]{0, 1, 1, 2, 3, 3};
    assertArrayEquals(array, list.toArray());

    assertEquals(false, list.removeLastOccurrence(null));
    array = new Integer[]{0, 1, 1, 2, 3, 3};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.removeLastOccurrence(Integer.valueOf(0)));
    array = new Integer[]{1, 1, 2, 3, 3};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.removeLastOccurrence(Integer.valueOf(2)));
    array = new Integer[]{1, 1, 3, 3};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.removeLastOccurrence(Integer.valueOf(3)));
    array = new Integer[]{1, 1, 3};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.removeLastOccurrence(Integer.valueOf(1)));
    array = new Integer[]{1, 3};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.removeLastOccurrence(Integer.valueOf(3)));
    array = new Integer[]{1};
    assertArrayEquals(array, list.toArray());

    assertEquals(true, list.removeLastOccurrence(Integer.valueOf(1)));
    array = new Integer[]{};
    assertArrayEquals(array, list.toArray());
  }

  /**
   * Test method for {@link ArrayLinkedList#offer(Object)}.
   */
  @Test
  public void testOffer() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());

    boolean ret = list.offer(1);
    assertEquals(true, ret);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.getLast());

    ret = list.offer(2);
    assertEquals(true, ret);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.getLast());

    ret = list.offer(3);
    assertEquals(true, ret);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.getLast());

    ret = list.offer(null);
    assertEquals(true, ret);
    assertEquals(4, list.size());
    assertEquals(null, list.getLast());

    assertArrayEquals(new Object[]{1, 2, 3, null}, list.toArray());

    list.clear();
    assertEquals(0, list.size());
    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.offer(i);
      expected.offer(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);
  }

  /**
   * Test method for {@link ArrayLinkedList#remove()}.
   */
  @Test
  public void testRemove() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());
    try {
      list.remove();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }

    list.addFirst(1);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.getFirst());

    list.addFirst(2);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.getFirst());

    list.addFirst(3);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.getFirst());

    list.addFirst(null);
    assertEquals(4, list.size());
    assertEquals(null, list.getFirst());

    assertArrayEquals(new Object[]{null, 3, 2, 1}, list.toArray());

    assertEquals(null, list.remove());
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.remove());
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.remove());
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.remove());
    assertEquals(0, list.size());

    list.clear();
    assertEquals(0, list.size());
    try {
      list.remove();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }

    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.addFirst(i);
      expected.addFirst(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);

    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      assertEquals(expected.remove(), list.remove());
      assertEquals(expected.size(), list.size());
    }

    assertEquals(0, list.size());
    try {
      list.remove();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }
  }

  /**
   * Test method for {@link ArrayLinkedList#poll()}.
   */
  @Test
  public void testPoll() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());
    assertEquals(null, list.poll());

    list.addFirst(1);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.getFirst());

    list.addFirst(2);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.getFirst());

    list.addFirst(3);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.getFirst());

    list.addFirst(null);
    assertEquals(4, list.size());
    assertEquals(null, list.getFirst());

    assertArrayEquals(new Object[]{null, 3, 2, 1}, list.toArray());

    assertEquals(null, list.poll());
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.poll());
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.poll());
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.poll());
    assertEquals(0, list.size());

    list.clear();
    assertEquals(0, list.size());
    assertEquals(null, list.poll());

    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.addFirst(i);
      expected.addFirst(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);

    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      assertEquals(expected.poll(), list.poll());
      assertEquals(expected.size(), list.size());
    }

    assertEquals(0, list.size());
    assertEquals(null, list.poll());
  }

  /**
   * Test method for {@link ArrayLinkedList#element()}.
   */
  @Test
  public void testElement() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());
    try {
      list.element();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }

    list.addFirst(1);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.element());

    list.addFirst(2);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.element());

    list.addFirst(3);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.element());

    list.addFirst(null);
    assertEquals(4, list.size());
    assertEquals(null, list.element());

    assertArrayEquals(new Object[]{null, 3, 2, 1}, list.toArray());

    list.clear();
    assertEquals(0, list.size());
    try {
      list.element();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }

    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.addFirst(i);
      expected.addFirst(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);
    assertEquals(expected.element(), list.element());
  }

  /**
   * Test method for {@link ArrayLinkedList#peek()}.
   */
  @Test
  public void testPeek() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());
    assertEquals(null, list.peek());

    list.addFirst(1);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.peek());

    list.addFirst(2);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.peek());

    list.addFirst(3);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.peek());

    list.addFirst(null);
    assertEquals(4, list.size());
    assertEquals(null, list.peek());

    assertArrayEquals(new Object[]{null, 3, 2, 1}, list.toArray());

    list.clear();
    assertEquals(0, list.size());
    assertEquals(null, list.peek());

    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.addFirst(i);
      expected.addFirst(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);
    assertEquals(expected.peek(), list.peek());
  }

  /**
   * Test method for {@link ArrayLinkedList#push(Object)}.
   */
  @Test
  public void testPush() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());

    list.push(1);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.getFirst());

    list.push(2);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.getFirst());

    list.push(3);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.getFirst());

    list.push(null);
    assertEquals(4, list.size());
    assertEquals(null, list.getFirst());

    assertArrayEquals(new Object[]{null, 3, 2, 1}, list.toArray());

    list.clear();
    assertEquals(0, list.size());
    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.push(i);
      expected.push(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);
  }

  /**
   * Test method for {@link ArrayLinkedList#pop()}.
   */
  @Test
  public void testPop() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(0, list.size());
    try {
      list.pop();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }

    list.addFirst(1);
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.getFirst());

    list.addFirst(2);
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.getFirst());

    list.addFirst(3);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.getFirst());

    list.addFirst(null);
    assertEquals(4, list.size());
    assertEquals(null, list.getFirst());

    assertArrayEquals(new Object[]{null, 3, 2, 1}, list.toArray());

    assertEquals(null, list.pop());
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(3), list.pop());
    assertEquals(2, list.size());
    assertEquals(Integer.valueOf(2), list.pop());
    assertEquals(1, list.size());
    assertEquals(Integer.valueOf(1), list.pop());
    assertEquals(0, list.size());

    list.clear();
    assertEquals(0, list.size());
    try {
      list.pop();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }

    final LinkedList<Integer> expected = new LinkedList<Integer>();
    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      list.addFirst(i);
      expected.addFirst(i);
    }
    assertEquals(ExpansionPolicy.getInitialCapacity() + 1, list.size());
    assertEquals(expected, list);

    for (int i = 0; i <= ExpansionPolicy.getInitialCapacity(); ++i) {
      assertEquals(expected.pop(), list.pop());
      assertEquals(expected.size(), list.size());
    }

    assertEquals(0, list.size());
    try {
      list.pop();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }
  }

  /**
   * Test method for {@link ArrayLinkedList#descendingIterator()}.
   */
  @Test
  public void testDescendingIterator() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    Iterator<Integer> iter = list.descendingIterator();
    assertEquals(false, iter.hasNext());

    final Integer[] array = {0, 1, 2, null, 4, 5, null, 7, 8, null, 10};
    list.addAll(Arrays.asList(array));
    iter = list.descendingIterator();

    for (int i = array.length - 1; i >= 0; --i) {
      assertEquals(true, iter.hasNext());
      assertEquals(array[i], iter.next());
    }
  }

  /**
   * Test method for {@link ArrayLinkedList#removeAll(Collection)}.
   */
  @Test
  public void testRemoveAll() {
    Collection<Integer> col = Collections.emptyList();
    Integer[] array = null;
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(false, list.removeAll(col));

    array = new Integer[]{3, 0, 1, 2, null, 1, 4, null, 2, 2, null, 4};
    list.clear();
    list.addAll(Arrays.asList(array));
    col = Collections.emptyList();
    assertEquals(false, list.removeAll(col));
    assertArrayEquals(array, list.toArray());

    col = Arrays.asList(3, null, 1, 4, null, 1);
    assertEquals(true, list.removeAll(col));
    array = new Integer[]{0, 2, 2, 2};
    assertArrayEquals(array, list.toArray());

    col = Arrays.asList(100);
    assertEquals(false, list.removeAll(col));
    assertArrayEquals(array, list.toArray());

    col = Arrays.asList(0, 0, 100);
    assertEquals(true, list.removeAll(col));
    array = new Integer[]{2, 2, 2};
    assertArrayEquals(array, list.toArray());

    array = new Integer[]{0};
    col = Arrays.asList(array);
    list.clear();
    list.addAll(col);
    assertEquals(true, list.removeAll(col));
    assertEquals(0, list.size());
  }

  /**
   * Test method for {@link ArrayLinkedList#retainAll(Collection)}.
   */
  @Test
  public void testRetainAll() {
    Collection<Integer> col = Collections.emptyList();
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    assertEquals(false, list.retainAll(col));

    Integer[] array = {0, 1, 2, null, 1, 4, null, 2, 2, null, 4};
    list.addAll(Arrays.asList(array));
    assertEquals(true, list.retainAll(col));
    assertEquals(0, list.size());

    list.addAll(Arrays.asList(array));
    col = Arrays.asList(4, null, 1, 1, 1, 1, null);
    assertEquals(true, list.retainAll(col));
    array = new Integer[]{1, null, 1, 4, null, null, 4};
    assertArrayEquals(array, list.toArray());

    col = Arrays.asList(null, null, 1, 100, 11);
    assertEquals(true, list.retainAll(col));
    array = new Integer[]{1, null, 1, null, null};
    assertArrayEquals(array, list.toArray());

    col = Arrays.asList(1, 11, 100);
    assertEquals(true, list.retainAll(col));
    array = new Integer[]{1, 1};
    assertArrayEquals(array, list.toArray());

    col = Arrays.asList(11, 100);
    assertEquals(true, list.retainAll(col));
    array = new Integer[]{};
    assertArrayEquals(array, list.toArray());
  }

  /**
   * Test method for {@link ArrayLinkedList#clone()}.
   */
  @Test
  public void testClone() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<Integer>();
    ArrayLinkedList<Integer> cloned = list.clone();
    assertNotSame(list, cloned);
    assertEquals(list, cloned);

    list.add(1);
    cloned = list.clone();
    assertNotSame(list, cloned);
    assertEquals(list, cloned);

    final Integer[] array = {0, 1, 2, null, 1, 4, null, 2, 2, null, 4};
    list.addAll(Arrays.asList(array));
    cloned = list.clone();
    assertNotSame(list, cloned);
    assertEquals(list, cloned);

    for (int i = 0; i < 1000; ++i) {
      list.add(i);
    }
    cloned = list.clone();
    assertNotSame(list, cloned);
    assertEquals(list, cloned);
  }

}
