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

import javax.annotation.concurrent.ThreadSafe;

/**
 * The interface of the queue used to store the idle objects in the {@link Pool}.
 * <p>
 * Note: the implementation MUST be thread-safe.
 * </p>
 *
 * @author Haixing Hu
 */
@ThreadSafe
public interface IdleQueue<E> {

  /**
   * The default capacity of this queue, which is {@value} .
   */
  public static final int DEFAULT_CAPACITY = 64;

  /**
   * Tests whether this queue is empty.
   *
   * @return true if this queue is empty; false otherwise.
   */
  public boolean isEmpty();

  /**
   * Gets the number of objects in the queue.
   *
   * @return the number of objects in the queue.
   */
  public int size();

  /**
   * Gets the capacity of this queue.
   *
   * @return the capacity of this queue.
   */
  public int capacity();

  /**
   * Retrieves and removes the head of this queue, or returns null if this queue
   * is empty.
   *
   * @return the head of this queue, or null if this queue is empty.
   */
  public E poll();

  /**
   * Tries to insert an object to the queue.
   * <p>
   * If the queue if full, this function will failed to insert an object to the
   * queue, and returns false.
   * </p>
   *
   * @param e
   *          the object to be inserted into the queue.
   * @return true if the insertion success; false otherwise.
   */
  public boolean offer(E e);

  /**
   * Dumps this queue.
   * <p>
   * Note that this function does NOT destroy the object contained in this
   * queue, instead, it just copy all objects in this queue to a list and clear
   * this queue.
   * </p>
   *
   * @return the list of objects contains in this queue before calling this
   *         function.
   */
  public List<E> dump();

}
