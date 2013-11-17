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

package com.github.haixing_hu.util.pool.impl;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.github.haixing_hu.util.pool.impl.IdleQueue;
import com.github.haixing_hu.util.pool.impl.LifoIdleQueue;

import static org.junit.Assert.*;

/**
 * Unit test of the {@link LifoIdleQueue} class.
 *
 * @author Haixing Hu
 */
public class LifoIdleQueueTest {

  /**
   * Test method for {@link LifoIdleQueue#LifoIdleQueue()}.
   */
  @Test
  public void testLifoIdleQueue() {
    final IdleQueue<String> queue = new LifoIdleQueue<String>();
    assertEquals(true, queue.isEmpty());
    assertEquals(0, queue.size());
    assertEquals(IdleQueue.DEFAULT_CAPACITY, queue.capacity());
  }

  /**
   * Test method for {@link LifoIdleQueue#LifoIdleQueue(int)}.
   */
  @Test
  public void testLifoIdleQueueInt() {
    final IdleQueue<String> queue = new LifoIdleQueue<String>(10);
    assertEquals(true, queue.isEmpty());
    assertEquals(0, queue.size());
    assertEquals(10, queue.capacity());

    try {
      new LifoIdleQueue<String>(- 10);
      fail("should throw IllegalArgumentException");
    } catch (final IllegalArgumentException e) {
      // pass
    }

    try {
      new LifoIdleQueue<String>(0);
      fail("should throw IllegalArgumentException");
    } catch (final IllegalArgumentException e) {
      // pass
    }
  }

  /**
   * Test method for {@link LifoIdleQueue#offer(Object)} and
   * {@link LifoIdleQueue#poll()}.
   */
  @Test
  public void testOfferPool() {
    final int CAPACITY = 10;
    final IdleQueue<Integer> queue = new LifoIdleQueue<Integer>(CAPACITY);
    assertEquals(true, queue.isEmpty());
    assertEquals(0, queue.size());
    assertEquals(CAPACITY, queue.capacity());
    assertEquals(null, queue.poll());

    for (int i = 0; i < CAPACITY; ++i) {
      assertEquals(true, queue.offer(i));
      assertEquals(i + 1, queue.size());
    }
    assertEquals(false, queue.offer(10));

    for (int i = CAPACITY - 1; i >= 0; --i) {
      assertEquals(Integer.valueOf(i), queue.poll());
      assertEquals(i, queue.size());
    }
    assertEquals(null, queue.poll());
  }

  /**
   * Test method for {@link LifoIdleQueue#dump()}.
   */
  @Test
  public void testDump() {
    final int CAPACITY = 10;
    final IdleQueue<Integer> queue = new LifoIdleQueue<Integer>(CAPACITY);
    assertEquals(true, queue.isEmpty());
    assertEquals(0, queue.size());
    assertEquals(CAPACITY, queue.capacity());
    assertEquals(null, queue.poll());

    for (int i = 0; i < CAPACITY; ++i) {
      assertEquals(true, queue.offer(i));
      assertEquals(i + 1, queue.size());
    }

    final List<Integer> list = queue.dump();
    final Iterator<Integer> iter = list.iterator();
    for (int i = 0; i < CAPACITY; ++i) {
      assertEquals(true, iter.hasNext());
      assertEquals(Integer.valueOf(i), iter.next());
    }
    assertEquals(false, iter.hasNext());
  }
}
