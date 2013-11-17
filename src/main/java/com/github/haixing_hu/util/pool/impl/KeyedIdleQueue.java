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

import java.util.List;
import java.util.Map;

import javax.annotation.concurrent.ThreadSafe;

import com.github.haixing_hu.util.pool.KeyedPool;

/**
 * The interface of the queue used to store the idle objects in the
 * {@link KeyedPool}.
 * <p>
 * The {@link KeyedIdleQueue} acts as a map from a key to a {@link IdleQueue},
 * providing the operations of each keyed {@link IdleQueue}.
 * </p>
 * <p>
 * Note: the implementation MUST be thread-safe.
 * </p>
 *
 * @author Haixing Hu
 */
@ThreadSafe
public interface KeyedIdleQueue<KEY, VALUE> {

  /**
   * The default capacity of each keyed queue, which is {@value} .
   */
  public static final int DEFAULT_CAPACITY = 64;

  /**
   * Tests whether a keyed idle queue is empty.
   *
   * @param key
   *          the key of an idle queue.
   * @return true if the corresponding idle queue is empty; false otherwise.
   */
  public boolean isEmpty(KEY key);

  /**
   * Gets the number of objects in a keyed idle queue.
   *
   * @param key
   *          the key of an idle queue.
   * @return the number of objects in the corresponding idle queue.
   */
  public int size(KEY key);

  /**
   * Gets the total number of objects in all keyed idle queues.
   *
   * @return the total number of objects in all keyed idle queues.
   */
  public int size();

  /**
   * Gets the capacity of each keyed idle queue.
   *
   * @return the capacity of each idle queue.
   */
  public int capacity();

  /**
   * Retrieves and removes the head of a keyed idle queue, or returns null if
   * that queue is empty.
   *
   * @param key
   *          the key of an idle queue.
   * @return the head of the corresponding idle queue, or null if that queue is
   *         empty.
   */
  public VALUE poll(KEY key);

  /**
   * Tries to insert an object to keyed idle queue.
   * <p>
   * If the specified queue if full, this function will failed to insert an
   * object to the queue, and returns false.
   * </p>
   *
   * @param key
   *          the key of an idle queue.
   * @param value
   *          the object to be inserted into the queue.
   * @return true if the insertion success; false otherwise.
   */
  public boolean offer(KEY key, VALUE value);

  /**
   * Dumps a keyed idle queue.
   * <p>
   * Note that this function does NOT destroy the object contained in the
   * specified queue, instead, it just copy all objects in that queue to a list
   * and clear this queue.
   * </p>
   *
   * @param key
   *          the key of an idle queue.
   * @return the list of objects contains in the corresponding idle queue before
   *         calling this function.
   */
  public List<VALUE> dump(KEY key);

  /**
   * Dumps all keyed idle queues.
   * <p>
   * Note that this function does NOT destroy the object contained in each idle
   * queue, instead, it just copy all objects in that queue to a list and clear
   * that queue.
   * </p>
   *
   * @return the map from the key to the list of objects contains in the
   *         corresponding idle queue before calling this function.
   */
  public Map<KEY, List<VALUE>> dump();
}
