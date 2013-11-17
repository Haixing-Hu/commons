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

package com.github.haixing_hu.util.pool;

/**
 * The abstract base class for implementing the {@link KeyedPoolableFactory}.
 *
 * @author Haixing Hu
 */
public abstract class AbstractKeyedPoolableFactory<K, V> implements
    KeyedPoolableFactory<K, V> {

  @Override
  public abstract V create(final K key) throws PoolException;

  @Override
  public void destroy(final K key, final V obj) {
    // do nothing
  }

  @Override
  public boolean validate(final K key, final V obj) {
    // do nothing
    return true;
  }

  @Override
  public void activate(final K key, final V obj) throws PoolException {
    // do nothing
  }

  @Override
  public void passivate(final K key, final V obj) throws PoolException {
    // do nothing
  }

}
