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

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import com.github.haixing_hu.util.pool.Pool;
import com.github.haixing_hu.util.pool.PoolException;
import com.github.haixing_hu.util.pool.PoolExhaustedException;
import com.github.haixing_hu.util.pool.PoolableFactory;

/**
 * An implementation of {@link ExhaustedPolicy} which blocking the current
 * thread while the pool is exhausted.
 * <p>
 * The thread will be waked up while an object is {@link Pool#restore restored}
 * to the pool.
 * </p>
 *
 * @author Haixing Hu
 */
@ThreadSafe
public class BlockingExhaustedPolicy<T> extends AbstractExhaustedPolicy<T> {

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
  private final Queue<Condition> waiters;

  public BlockingExhaustedPolicy() {
    this(DEFAULT_MAX_ACTIVE_COUNT, DEFAULT_MAX_WAIT_TIME);
  }

  public BlockingExhaustedPolicy(final int maxActiveCount) {
    this(maxActiveCount, DEFAULT_MAX_WAIT_TIME);
  }

  public BlockingExhaustedPolicy(final int maxActiveCount, final long maxWaitTime) {
    super(maxActiveCount);
    this.maxWaitTime = maxWaitTime;
    this.lock = new ReentrantLock();
    this.waiters = new LinkedList<Condition>();
  }

  /**
   * Gets the lock of this policy.
   *
   * @return the lock of this policy.
   */
  public Lock getLock() {
    return lock;
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
  public T onExhausted(final PoolableFactory<T> factory) throws PoolException {
    final Condition cond = lock.newCondition();
    lock.lock();
    try {
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
      } else {  // maxWaitTime <= 0
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
  public void onStateChanged(final PoolableFactory<T> factory) {
    // unblock the blocking thread
    lock.lock();
    try {
      final Condition cond = waiters.poll();
      if (cond != null) {
        cond.signalAll();
      }
    } finally {
      lock.unlock();
    }
  }

}
