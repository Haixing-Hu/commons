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

import org.junit.Test;

import com.github.haixing_hu.util.pool.MethodCallPoolableFactory;
import com.github.haixing_hu.util.pool.PrivateException;
import com.github.haixing_hu.util.pool.impl.ExhaustedPolicy;
import com.github.haixing_hu.util.pool.impl.GrowingExhaustedPolicy;

/**
 * Unit test of the {@link GrowingExhaustedPolicy} class.
 *
 * @author Haixing Hu
 */
public class GrowingExhaustedPolicyTest extends ExhaustedPolicyTest {

  @Override
  protected <T> ExhaustedPolicy<T> makeExhaustedPolicy() {
    return new GrowingExhaustedPolicy<T>();
  }

  @Override
  protected <T> ExhaustedPolicy<T> makeExhaustedPolicy(final int maxActiveCount) {
    return new GrowingExhaustedPolicy<T>(maxActiveCount);
  }

  @Test
  public void testOnExhausted() {
    final MethodCallPoolableFactory factory = new MethodCallPoolableFactory();
    final GrowingExhaustedPolicy<Integer> policy = new GrowingExhaustedPolicy<Integer>();

    Integer obj = policy.onExhausted(factory);
    assertNotNull(obj);
    assertEquals(Integer.valueOf(0), obj);
    assertEquals(1, factory.getCurrentCount());

    obj = policy.onExhausted(factory);
    assertNotNull(obj);
    assertEquals(Integer.valueOf(1), obj);
    assertEquals(2, factory.getCurrentCount());

    factory.setCreateFail(true);
    try {
      obj = policy.onExhausted(factory);
      fail("should throw PrivateException");
    } catch (final PrivateException e) {
      // pass
    }
  }

  @Test
  public void testOnStateChanged() {
    final MethodCallPoolableFactory factory = new MethodCallPoolableFactory();
    final GrowingExhaustedPolicy<Integer> policy = new GrowingExhaustedPolicy<Integer>();

    policy.onStateChanged(factory);  // no effect
  }
}
