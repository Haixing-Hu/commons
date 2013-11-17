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
import java.util.List;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import com.github.haixing_hu.collection.ArrayLinkedList;

/**
 * The abstract base class for implementing the {@link IdleQueue}.
 * <p>
 * In order to avoid memory allocation and deallocation at each call to
 * {@link #poll} and {@link #offer}, this implementation extends the
 * {@link ArrayLinkedList}.
 * </p>
 *
 * @author Haixing Hu
 */
@ThreadSafe
public abstract class AbstractIdleQueue<E> implements IdleQueue<E> {

  @GuardedBy("this")
  protected final ArrayLinkedList<E> queue;
  protected final int capacity;

  protected AbstractIdleQueue() {
    queue = new ArrayLinkedList<E>(DEFAULT_CAPACITY);
    capacity = DEFAULT_CAPACITY;
  }

  protected AbstractIdleQueue(final int capacity) {
    if (capacity <= 0) {
      throw new IllegalArgumentException("Capacity must be positive.");
    }
    this.queue = new ArrayLinkedList<E>(capacity);
    this.capacity = capacity;
  }

  @Override
  public synchronized boolean isEmpty() {
    return queue.isEmpty();
  }

  @Override
  public synchronized int size() {
    return queue.size();
  }

  @Override
  public int capacity() {
    return capacity;
  }

  @Override
  public abstract E poll();

  @Override
  public abstract boolean offer(final E e);

  @Override
  public synchronized List<E> dump() {
    final int n = queue.size();
    if (n == 0) {
      return Collections.emptyList();
    } else {
      final List<E> result = new ArrayList<E>(n);
      for (final E obj : queue) {
        result.add(obj);
      }
      queue.clear();
      return result;
    }
  }
}
