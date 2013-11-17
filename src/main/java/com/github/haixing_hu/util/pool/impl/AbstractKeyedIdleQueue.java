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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import com.github.haixing_hu.collection.ArrayLinkedList;

/**
 * The abstract base class for implementing the {@link KeyedIdleQueue}.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public abstract class AbstractKeyedIdleQueue<KEY, VALUE> implements
    KeyedIdleQueue<KEY, VALUE> {

  @GuardedBy("this")
  protected final Map<KEY, ArrayLinkedList<VALUE>> queueMap;
  protected final int capacity;

  protected AbstractKeyedIdleQueue() {
    this.queueMap = new HashMap<KEY, ArrayLinkedList<VALUE>>();
    this.capacity = DEFAULT_CAPACITY;
  }

  protected AbstractKeyedIdleQueue(final int capacity) {
    if (capacity <= 0) {
      throw new IllegalArgumentException(
          "The default capacity must be positive.");
    }
    this.queueMap = new HashMap<KEY, ArrayLinkedList<VALUE>>();
    this.capacity = capacity;
  }

  @Override
  public synchronized boolean isEmpty(final KEY key) {
    final ArrayLinkedList<VALUE> queue = queueMap.get(key);
    return ((queue == null) || queue.isEmpty());
  }

  @Override
  public synchronized int size(final KEY key) {
    final ArrayLinkedList<VALUE> queue = queueMap.get(key);
    return (queue == null ? 0 : queue.size());
  }

  @Override
  public synchronized int size() {
    int result = 0;
    for (final ArrayLinkedList<VALUE> queue : queueMap.values()) {
      result += queue.size();
    }
    return result;
  }

  @Override
  public int capacity() {
    return capacity;
  }

  @Override
  public synchronized VALUE poll(final KEY key) {
    final ArrayLinkedList<VALUE> queue = queueMap.get(key);
    if (queue == null) {
      return null;
    } else {
      return getFromQueue(queue);
    }
  }

  /**
   * Gets an object from the specified queue.
   * <p>
   * The implementation need NOT to be synchronized.
   * </p>
   * @param queue
   *      a specified queue.
   * @return
   *      the object get from the queue.
   */
  protected abstract VALUE getFromQueue(ArrayLinkedList<VALUE> queue);

  @Override
  public synchronized boolean offer(final KEY key, final VALUE value) {
    ArrayLinkedList<VALUE> queue = queueMap.get(key);
    if (queue == null) {
      queue = new ArrayLinkedList<VALUE>(capacity);
      queueMap.put(key, queue);
    }
    if (queue.size() == capacity) {
      return false;
    } else {
      putToQueue(queue, value);
      return true;
    }
  }

  /**
   * Puts an object to the specified queue.
   * <p>
   * The implementation need NOT to be synchronized.
   * </p>
   * @param queue
   *      a specified queue.
   * @param value
   *      the object to be put to the queue.
   */
  protected abstract void putToQueue(ArrayLinkedList<VALUE> queue, VALUE value);

  @Override
  public synchronized List<VALUE> dump(final KEY key) {
    final ArrayLinkedList<VALUE> queue = queueMap.get(key);
    if (queue == null) {
      return Collections.emptyList();
    }
    final int n = queue.size();
    if (n == 0) {
      return Collections.emptyList();
    } else {
      final List<VALUE> result = new ArrayList<VALUE>(n);
      for (final VALUE obj : queue) {
        result.add(obj);
      }
      queue.clear();
      return result;
    }
  }

  @Override
  public synchronized Map<KEY, List<VALUE>> dump() {
    if (queueMap.isEmpty()) {
      return Collections.emptyMap();
    }
    final Map<KEY, List<VALUE>> result = new HashMap<KEY, List<VALUE>>();
    for (final Map.Entry<KEY, ArrayLinkedList<VALUE>> entry : queueMap.entrySet()) {
      final KEY key = entry.getKey();
      final ArrayLinkedList<VALUE> queue = entry.getValue();
      final List<VALUE> list;
      final int n = queue.size();
      if (n == 0) {
        list = Collections.emptyList();
      } else {
        list = new ArrayList<VALUE>(n);
        for (final VALUE obj : queue) {
          list.add(obj);
        }
        queue.clear();
      }
      result.put(key, list);
    }
    return result;
  }

}
