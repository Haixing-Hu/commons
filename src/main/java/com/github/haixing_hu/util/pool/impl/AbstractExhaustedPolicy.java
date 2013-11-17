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

import com.github.haixing_hu.util.pool.PoolException;
import com.github.haixing_hu.util.pool.PoolableFactory;

/**
 * The abstract base class for implementing the {@link ExhaustedPolicy}.
 *
 * @author Haixing Hu
 */
public abstract class AbstractExhaustedPolicy<T> implements ExhaustedPolicy<T> {

  protected final int maxActiveCount;

  protected AbstractExhaustedPolicy() {
    this.maxActiveCount = DEFAULT_MAX_ACTIVE_COUNT;
  }

  protected AbstractExhaustedPolicy(final int maxActiveCount) {
    this.maxActiveCount = (maxActiveCount < 0 ? Integer.MAX_VALUE : maxActiveCount);
  }


  @Override
  public int getMaxActiveCount() {
    return maxActiveCount;
  }

  @Override
  public boolean isExhausted(final int activeCount) {
    return activeCount >= maxActiveCount;
  }

  @Override
  public abstract T onExhausted(PoolableFactory<T> factory) throws PoolException;

  @Override
  public abstract void onStateChanged(PoolableFactory<T> factory) throws PoolException;
}
