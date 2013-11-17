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
import java.util.NoSuchElementException;

import javax.annotation.concurrent.ThreadSafe;

import com.github.haixing_hu.util.pool.AbstractPool;
import com.github.haixing_hu.util.pool.Pool;
import com.github.haixing_hu.util.pool.PoolException;
import com.github.haixing_hu.util.pool.PoolableFactory;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * A configurable {@link Pool} implementation.
 * <p>
 * When coupled with the appropriate {@link PoolableFactory}, the
 * {@link BasicPool} provides robust pooling functionality for arbitrary
 * objects.
 * <p>
 * A {@link BasicPool} provides a number of configurable parameters:
 * <ul>
 * <li>A {@link PoolableFactory} controls how the poolable objects are created,
 * destroyed, activated, validated, and passivated.</li>
 * <li>A {@link IdleQueue} controls how to store and fetch the idle objects.</li>
 * <li>A {@link ValidatePolicy} controls how to validate the poolable objects.</li>
 * <li>A {@link ExhaustedPolicy} controls the actions to taken while the pool is
 * exhausted.</li>
 * </ul>
 * <p>
 * This implementation is thread-safe.
 * </p>
 *
 * @author Haixing Hu
 */
@ThreadSafe
public class BasicPool<T> extends AbstractPool<T> {

  protected final IdleQueue<T> idleQueue;
  protected final ValidatePolicy<T> validatePolicy;
  protected final ExhaustedPolicy<T> exhaustedPolicy;

  public BasicPool(final PoolableFactory<T> factory,
      final IdleQueue<T> idleQueue,
      final ValidatePolicy<T> validatePolicy,
      final ExhaustedPolicy<T> exhaustedPolicy) {
    super(factory);
    this.idleQueue = requireNonNull("idleQueue", idleQueue);
    this.validatePolicy = requireNonNull("validatePolicy", validatePolicy);
    this.exhaustedPolicy = requireNonNull("exhaustedPolicy", exhaustedPolicy);
  }

  /**
   * Gets the queue of the idle objects.
   *
   * @return the queue of the idle objects.
   */
  public IdleQueue<T> getIdleQueue() {
    return idleQueue;
  }

  /**
   * Gets the validate policy used by this pool.
   *
   * @return the validate policy used by this pool.
   */
  public ValidatePolicy<T> getValidatePolicy() {
    return validatePolicy;
  }

  /**
   * Gets the exhausted policy used by this pool.
   *
   * @return the exhausted policy used by this pool.
   */
  public ExhaustedPolicy<T> getExhaustedPolicy() {
    return exhaustedPolicy;
  }

  @Override
  public int getIdleCount() {
    return idleQueue.size();
  }

  @Override
  protected T doBorrow() throws PoolException, NoSuchElementException,
      IllegalStateException {

    for (;;) {
      T obj = idleQueue.poll();
      if (obj != null) {
        doActive(obj);
      } else if (exhaustedPolicy.isExhausted(activeCount.get())) {
        obj = exhaustedPolicy.onExhausted(factory); // may throw, may block, may returns null
      } else {
        obj = factory.create(); // may throw
      }
      // validate the object if necessary
      if (obj != null) {
        if (validatePolicy.onBorrow(factory, obj)) { // may throw
          activeCount.incrementAndGet();
          return obj;
        }
      }
      // repeat again.
    } // end of for
  }

  @Override
  protected void doRestore(final T obj) throws PoolException {
    try {
      // if the obj does not pass the validation, just destroy it
      // (by the validate policy) and return
      if (! validatePolicy.onRestore(factory, obj)) { // may throw
        return;
      }
      // passivate the object
      doPassivate(obj);
      // put the idle object to the queue
      if (! idleQueue.offer(obj)) {
        LOGGER.debug("The capacity of the idle queue has reached, "
            + "destroy the object: {}", obj);
        factory.destroy(obj);
      }
    } finally {
      // always decrease the active count
      activeCount.decrementAndGet();
      // fire the event, may wake up the blocking threads
      exhaustedPolicy.onStateChanged(factory);
    }
  }

  @Override
  protected void doInvalidate(final T obj) throws PoolException {
    try {
      factory.destroy(obj);
    } finally {
      // always decrease the active count
      activeCount.decrementAndGet();
      // fire the event, may wake up the blocking threads
      exhaustedPolicy.onStateChanged(factory);
    }
  }

  @Override
  protected void doAdd() throws PoolException, IllegalStateException,
      UnsupportedOperationException {
    final T obj = factory.create();
    // passivate the object
    doPassivate(obj);
    // put the idle object to the queue
    if (idleQueue.offer(obj)) {
      // fire the event, may wake up the blocking threads
      exhaustedPolicy.onStateChanged(factory);
    } else {
      LOGGER.debug("The capacity of the idle queue has reached, "
          + "destroy the object: {}", obj);
      factory.destroy(obj);
    }
  }

  @Override
  protected void doClear() throws PoolException, UnsupportedOperationException {
    final List<T> toDestroy = idleQueue.dump();
    // destroy all idle objects
    for (final T obj : toDestroy) {
      factory.destroy(obj);
    }
    toDestroy.clear();
  }

  @Override
  protected void doClose() {
    doClear();
  }

}
