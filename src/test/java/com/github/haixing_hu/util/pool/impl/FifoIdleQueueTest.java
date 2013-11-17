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

import com.github.haixing_hu.util.pool.impl.FifoIdleQueue;
import com.github.haixing_hu.util.pool.impl.IdleQueue;

import static org.junit.Assert.*;

/**
 * Unit test of the {@link FifoIdleQueue} class.
 *
 * @author Haixing Hu
 */
public class FifoIdleQueueTest {

  /**
   * Test method for {@link FifoIdleQueue#FifoIdleQueue()}.
   */
  @Test
  public void testFifoIdleQueue() {
    final IdleQueue<String> queue = new FifoIdleQueue<String>();
    assertEquals(true, queue.isEmpty());
    assertEquals(0, queue.size());
    assertEquals(IdleQueue.DEFAULT_CAPACITY, queue.capacity());
  }

  /**
   * Test method for {@link FifoIdleQueue#FifoIdleQueue(int)}.
   */
  @Test
  public void testFifoIdleQueueInt() {
    final IdleQueue<String> queue = new FifoIdleQueue<String>(10);
    assertEquals(true, queue.isEmpty());
    assertEquals(0, queue.size());
    assertEquals(10, queue.capacity());

    try {
      new FifoIdleQueue<String>(- 10);
      fail("should throw IllegalArgumentException");
    } catch (final IllegalArgumentException e) {
      // pass
    }

    try {
      new FifoIdleQueue<String>(0);
      fail("should throw IllegalArgumentException");
    } catch (final IllegalArgumentException e) {
      // pass
    }
  }

  /**
   * Test method for {@link FifoIdleQueue#offer(Object)} and
   * {@link FifoIdleQueue#poll()}.
   */
  @Test
  public void testOfferPool() {
    final int CAPACITY = 10;
    final IdleQueue<Integer> queue = new FifoIdleQueue<Integer>(CAPACITY);
    assertEquals(true, queue.isEmpty());
    assertEquals(0, queue.size());
    assertEquals(CAPACITY, queue.capacity());
    assertEquals(null, queue.poll());

    for (int i = 0; i < CAPACITY; ++i) {
      assertEquals(true, queue.offer(i));
      assertEquals(i + 1, queue.size());
    }
    assertEquals(false, queue.offer(10));

    for (int i = 0; i < CAPACITY; ++i) {
      assertEquals(Integer.valueOf(i), queue.poll());
      assertEquals(CAPACITY - 1 - i, queue.size());
    }
    assertEquals(null, queue.poll());
  }

  /**
   * Test method for {@link FifoIdleQueue#dump()}.
   */
  @Test
  public void testDump() {
    final int CAPACITY = 10;
    final IdleQueue<Integer> queue = new FifoIdleQueue<Integer>(CAPACITY);
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
