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

import com.github.haixing_hu.util.pool.KeyedPoolableFactory;
import com.github.haixing_hu.util.pool.PoolException;
import com.github.haixing_hu.util.pool.PoolExhaustedException;
import com.github.haixing_hu.util.pool.PoolableFactory;

/**
 * The policy to deal with the exhausted keyed pool.
 * <p>
 * The implementation MUST be thread-safe.
 * </p>
 *
 * @author Haixing Hu
 */
public interface KeyedExhaustedPolicy<K, V> {

  /**
   * The default cap on the total number of active instances from the pool,
   * which is {@value} .
   */
  public static final int DEFAULT_MAX_ACTIVE_COUNT = 64;

  /**
   * Gets the maximum allowed number of active objects of the specified key.
   *
   * @param key
   *          a specified key.
   * @return the maximum allowed number of active objects of the specified key.
   */
  public int getMaxActiveCount(K key);

  /**
   * Tests whether the pool is exhausted because of the current number of active
   * objects exceeds the limit.
   *
   * @param key
   *          the key of the object to be borrowed.
   * @param activeCount
   *          the current number of active objects of the specified key.
   * @return true if the pool is exhausted because of the current number of
   *         active objects exceeds the limit; false otherwise.
   */
  public boolean isExhausted(K key, int activeCount);

  /**
   * The event fired on exhausting the pool.
   *
   * @param factory
   *          the {@link KeyedPoolableFactory} of the objects.
   * @param key
   *          the key of the object to be borrowed.
   * @return an activated object to be borrowed. The returned object is either a
   *         new object {@link PoolableFactory#create created} by the
   *         {@link PoolableFactory}, or an old object polled from the
   *         {@link IdleQueue}, or even null. The implementation of this policy
   *         may also choose to throw an exception while the pool is exhausted.
   * @throws PoolExhaustedException
   *           If this policy choose to throw an exception while the pool is
   *           exhausted.
   * @throws PoolException
   *           If any error occurred.
   */
  public V onExhausted(KeyedPoolableFactory<K, V> factory, K key) throws PoolException;

  /**
   * The event fired on changing the exhausted state, i.e., add an object to the
   * idle queue or decreasing the active count.
   * <p>
   * The implementation of this policy may do nothing, or wake up the blocking
   * threads.
   * </p>
   *
   * @param factory
   *          the {@link KeyedPoolableFactory} of the objects.
   * @param key
   *          the key of the object to be borrowed.
   * @throws PoolException
   *           If any error occurred.
   */
  public void onStateChanged(KeyedPoolableFactory<K, V> factory, K key) throws PoolException;
}
