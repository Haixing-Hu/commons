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

/**
 * The abstract base class for implementing the {@link KeyedExhaustedPolicy}.
 *
 * @author Haixing Hu
 */
public abstract class AbstractKeyedExhaustedPolicy<K, V> implements
    KeyedExhaustedPolicy<K, V> {

  /**
   * The default cap on the total number of active instances from the pool,
   * which is {@value} .
   */
  public static final int DEFAULT_MAX_ACTIVE_COUNT = 64;

  protected final int maxActiveCount;

  public AbstractKeyedExhaustedPolicy() {
    this.maxActiveCount = DEFAULT_MAX_ACTIVE_COUNT;
  }

  public AbstractKeyedExhaustedPolicy(final int maxActiveCount) {
    this.maxActiveCount = (maxActiveCount < 0 ? Integer.MAX_VALUE : maxActiveCount);
  }

  @Override
  public int getMaxActiveCount(final K key) {
    return maxActiveCount;
  }

  @Override
  public boolean isExhausted(final K key, final int activeCount) {
    return activeCount >= maxActiveCount;
  }

  @Override
  public abstract V onExhausted(KeyedPoolableFactory<K, V> factory, K key)
      throws PoolException;

  @Override
  public abstract void onStateChanged(KeyedPoolableFactory<K, V> factory, K key)
      throws PoolException;
}
