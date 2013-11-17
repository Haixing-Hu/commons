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

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.concurrent.GuardedBy;

import com.github.haixing_hu.collection.ArrayLinkedList;
import com.github.haixing_hu.util.pool.KeyedPool;
import com.github.haixing_hu.util.pool.KeyedPoolableFactory;
import com.github.haixing_hu.util.pool.Pool;
import com.github.haixing_hu.util.pool.PoolException;
import com.github.haixing_hu.util.pool.PoolExhaustedException;

/**
 * An implementation of {@link KeyedExhaustedPolicy} which blocking the current
 * thread while the pool is exhausted.
 * <p>
 * The thread will be waked up while an object is {@link KeyedPool#restore
 * restored} to the pool.
 * </p>
 *
 * @author Haixing Hu
 */
public class BlockingKeyedExhaustedPolicy<K, V> extends
    AbstractKeyedExhaustedPolicy<K, V> {

  /**
   * The default maximum amount of time (in milliseconds) the
   * {@link Pool#borrow} method should block before throwing an exception when
   * the pool is exhausted.
   * <p>
   * A non-positive value means no limit.
   * </p>
   */
  public static final long DEFAULT_MAX_WAIT_TIME = - 1L;

  private final long maxWaitTime;
  private final Lock lock;
  @GuardedBy("lock")
  private final Map<K, Queue<Condition>> waitersMap;

  public BlockingKeyedExhaustedPolicy() {
    super();
    this.maxWaitTime = DEFAULT_MAX_WAIT_TIME;
    this.lock = new ReentrantLock();
    this.waitersMap = new HashMap<K, Queue<Condition>>();
  }

  public BlockingKeyedExhaustedPolicy(final int maxActiveCount,
      final long maxWaitTime) {
    super(maxActiveCount);
    this.maxWaitTime = maxWaitTime;
    this.lock = new ReentrantLock();
    this.waitersMap = new HashMap<K, Queue<Condition>>();
  }

  /**
   * Gets the maximum amount of time (in milliseconds) the {@link Pool#borrow}
   * method should block before throwing an exception when the pool is
   * exhausted.
   * <p>
   * A non-positive value means no limit.
   * </p>
   *
   * @return the maximum amount of time (in milliseconds) the
   *         {@link Pool#borrow} method should block before throwing an
   *         exception when the pool is exhausted.
   */
  public long getMaxWaitTime() {
    return maxWaitTime;
  }

  @Override
  public V onExhausted(final KeyedPoolableFactory<K, V> factory, final K key)
      throws PoolException {
    final Condition cond = lock.newCondition();
    lock.lock();
    try {
      Queue<Condition> waiters = waitersMap.get(key);
      if (waiters == null) {
        waiters = new ArrayLinkedList<Condition>();
        waitersMap.put(key, waiters);
      }
      waiters.offer(cond);
      if (maxWaitTime > 0) {
        boolean success = false;
        try {
          success = cond.await(maxWaitTime, TimeUnit.MILLISECONDS);
        } catch (final InterruptedException e) {
          throw new PoolExhaustedException();
        }
        if (! success) {
          throw new PoolExhaustedException();
        }
      } else { // maxWaitTime <= 0
        try {
          cond.await();
        } catch (final InterruptedException e) {
          throw new PoolExhaustedException();
        }
      }
    } finally {
      lock.unlock();
    }
    return null;
  }

  @Override
  public void onStateChanged(final KeyedPoolableFactory<K, V> factory,
      final K key) {
    // unblock the blocking thread
    lock.lock();
    try {
      final Queue<Condition> waiters = waitersMap.get(key);
      if (waiters != null) {
        final Condition cond = waiters.poll();
        if (cond != null) {
          cond.signalAll();
        }
      }
    } finally {
      lock.unlock();
    }
  }

}
