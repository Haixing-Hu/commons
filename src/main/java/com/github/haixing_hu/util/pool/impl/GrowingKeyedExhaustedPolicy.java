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

import javax.annotation.concurrent.Immutable;

import com.github.haixing_hu.util.pool.KeyedPoolableFactory;
import com.github.haixing_hu.util.pool.PoolException;

/**
 * An implementation of {@link ExhaustedPolicy} which growing the pool while the
 * pool is exhausted.
 *
 * @author Haixing Hu
 */
@Immutable
public final class GrowingKeyedExhaustedPolicy<K, V> extends
    AbstractKeyedExhaustedPolicy<K, V> {

  public GrowingKeyedExhaustedPolicy() {
    super();
  }

  public GrowingKeyedExhaustedPolicy(final int maxActiveCount) {
    super(maxActiveCount);
  }

  @Override
  public V onExhausted(final KeyedPoolableFactory<K, V> factory, final K key)
      throws PoolException {
    return factory.create(key);
  }

  @Override
  public void onStateChanged(final KeyedPoolableFactory<K, V> factory,
      final K key) throws PoolException {
    // do nothing
  }

}
