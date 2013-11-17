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
import java.util.NoSuchElementException;

import javax.annotation.concurrent.ThreadSafe;

import com.github.haixing_hu.util.pool.AbstractKeyedPool;
import com.github.haixing_hu.util.pool.KeyedPool;
import com.github.haixing_hu.util.pool.KeyedPoolableFactory;
import com.github.haixing_hu.util.pool.PoolException;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * A configurable {@link KeyedPool} implementation.
 * <p>
 * When coupled with the appropriate {@link KeyedPoolableFactory}, the
 * {@link BasicKeyedPool} provides robust pooling functionality for arbitrary
 * objects.
 * <p>
 * A {@link BasicKeyedPool} provides a number of configurable parameters:
 * <ul>
 * <li>A {@link KeyedPoolableFactory} controls how the poolable objects are created,
 * destroyed, activated, validated, and passivated.</li>
 * <li>A {@link KeyedIdleQueue} controls how to store and fetch the idle objects.</li>
 * <li>A {@link KeyedValidatePolicy} controls how to validate the poolable objects.</li>
 * <li>A {@link KeyedExhaustedPolicy} controls the actions to taken while the pool is
 * exhausted.</li>
 * </ul>
 * <p>
 * This implementation is thread-safe.
 * </p>
 * @author Haixing Hu
 */
@ThreadSafe
public class BasicKeyedPool<K, V> extends AbstractKeyedPool<K, V> {

  protected final KeyedIdleQueue<K,V> idleQueue;
  protected final KeyedValidatePolicy<K,V> validatePolicy;
  protected final KeyedExhaustedPolicy<K,V> exhaustedPolicy;

  public BasicKeyedPool(final KeyedPoolableFactory<K, V> factory,
      final KeyedIdleQueue<K, V> idleQueue,
      final KeyedValidatePolicy<K, V> validatePolicy,
      final KeyedExhaustedPolicy<K, V> exhaustedPolicy) {
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
  public KeyedIdleQueue<K, V> getIdleQueue() {
    return idleQueue;
  }

  /**
   * Gets the validate policy used by this pool.
   *
   * @return the validate policy used by this pool.
   */
  public KeyedValidatePolicy<K,V> getValidatePolicy() {
    return validatePolicy;
  }

  /**
   * Gets the exhausted policy used by this pool.
   *
   * @return the exhausted policy used by this pool.
   */
  public KeyedExhaustedPolicy<K,V> getExhaustedPolicy() {
    return exhaustedPolicy;
  }

  @Override
  public int getIdleCount(final K key) throws UnsupportedOperationException {
    return idleQueue.size(key);
  }

  @Override
  public int getIdleCount() throws UnsupportedOperationException {
    return idleQueue.size();
  }

  @Override
  protected V doBorrow(final K key) throws PoolException,
      NoSuchElementException, IllegalStateException {
    for (;;) {
      V obj = idleQueue.poll(key);
      if (obj != null) {
        doActive(key, obj);
      } else if (exhaustedPolicy.isExhausted(key, getActiveCount(key))) {
        // may throw, may block, may returns null
        obj = exhaustedPolicy.onExhausted(factory, key);
      } else {
        obj = factory.create(key); // may throw
      }
      // validate the object if necessary
      if (obj != null) {
        if (validatePolicy.onBorrow(factory, key, obj)) { // may throw
          increaseActiveCount(key);
          return obj;
        }
      }
      // repeat again.
    } // end of for
  }

  @Override
  protected void doRestore(final K key, final V obj) throws PoolException {
    try {
      // if the obj does not pass the validation, just destroy it
      // (by the validate policy) and return
      if (! validatePolicy.onRestore(factory, key, obj)) { // may throw
        return;
      }
      // passivate the object
      doPassivate(key, obj);
      // put the idle object to the queue
      if (! idleQueue.offer(key, obj)) {
        LOGGER.debug("The capacity of the idle queue has reached, "
            + "destroy the object of key {}: {}", key, obj);
        factory.destroy(key, obj);
      }
    } finally {
      // always decrease the active count
      decreaseActiveCount(key);
      // fire the event, may wake up the blocking threads
      exhaustedPolicy.onStateChanged(factory, key);
    }
  }

  @Override
  protected void doInvalidate(final K key, final V obj)
      throws PoolException {
    try {
      factory.destroy(key, obj);
    } finally {
      // always decrease the active count
      decreaseActiveCount(key);
      // fire the event, may wake up the blocking threads
      exhaustedPolicy.onStateChanged(factory, key);
    }
  }

  @Override
  protected void doAdd(final K key) throws PoolException,
      IllegalStateException, UnsupportedOperationException {
    final V obj = factory.create(key);
    // passivate the object
    doPassivate(key, obj);
    // put the idle object to the queue
    if (idleQueue.offer(key, obj)) {
      // fire the event, may wake up the blocking threads
      exhaustedPolicy.onStateChanged(factory, key);
    } else {
      LOGGER.debug("The capacity of the idle queue has reached, "
          + "destroy the object of key {}: {}", key, obj);
      factory.destroy(key, obj);
    }
  }

  @Override
  protected void doClear(final K key) throws PoolException,
      UnsupportedOperationException {
    final List<V> toDestroy = idleQueue.dump(key);
    // destroy all idle objects
    for (final V obj : toDestroy) {
      factory.destroy(key, obj);
    }
    toDestroy.clear();
  }

  @Override
  protected void doClear() throws PoolException, UnsupportedOperationException {
    final Map<K,List<V>> toDestroy = idleQueue.dump();
    // destroy all idle objects
    for (final Map.Entry<K, List<V>> entry : toDestroy.entrySet()) {
      final K key = entry.getKey();
      final List<V> list = entry.getValue();
      for (final V obj : list) {
        factory.destroy(key, obj);
      }
    }
    toDestroy.clear();
  }

  @Override
  protected void doClose() {
    doClear();
  }

}
