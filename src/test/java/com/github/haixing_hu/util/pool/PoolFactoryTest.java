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

import com.github.haixing_hu.util.pool.Pool;
import com.github.haixing_hu.util.pool.PoolFactory;
import com.github.haixing_hu.util.pool.PoolableFactory;

import junit.framework.TestCase;

/**
 * Unit tests for all {@link PoolFactory}.
 *
 * @author Haixing Hu
 */
public abstract class PoolFactoryTest extends TestCase {

  protected PoolFactoryTest(final String name) {
    super(name);
  }

  /**
   * @throws UnsupportedOperationException
   *           when this is unsupported by this PoolableObjectFactory type.
   */
  protected PoolFactory<Integer> makeFactory()
      throws UnsupportedOperationException {
    return makeFactory(new MethodCallPoolableFactory());
  }

  /**
   * @throws UnsupportedOperationException
   *           when this is unsupported by this PoolableObjectFactory type.
   */
  protected abstract <T> PoolFactory<T> makeFactory(
      PoolableFactory<T> objectFactory) throws UnsupportedOperationException;

  public void testCreatePool() throws Exception {
    final PoolFactory<Integer> factory;
    try {
      factory = makeFactory();
    } catch (final UnsupportedOperationException uoe) {
      return;
    }
    final Pool<Integer> pool = factory.createPool();
    pool.close();
  }

  public void testToString() {
    final PoolFactory<Integer> factory;
    try {
      factory = makeFactory();
    } catch (final UnsupportedOperationException uoe) {
      return;
    }
    factory.toString();
  }
}
