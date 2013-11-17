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
import com.github.haixing_hu.util.pool.PoolExhaustedException;
import com.github.haixing_hu.util.pool.impl.ExhaustedPolicy;
import com.github.haixing_hu.util.pool.impl.FailExhaustedPolicy;

/**
 * Unit test of the {@link FailExhaustedPolicy} class.
 *
 * @author Haixing Hu
 */
public class FailExhaustedPolicyTest extends ExhaustedPolicyTest {

  @Override
  protected <T> ExhaustedPolicy<T> makeExhaustedPolicy() {
    return new FailExhaustedPolicy<T>();
  }

  @Override
  protected <T> ExhaustedPolicy<T> makeExhaustedPolicy(final int maxActiveCount) {
    return new FailExhaustedPolicy<T>(maxActiveCount);
  }

  @Test
  public void testOnExhausted() {
    final MethodCallPoolableFactory factory = new MethodCallPoolableFactory();
    final FailExhaustedPolicy<Integer> policy = new FailExhaustedPolicy<Integer>();

    try {
      policy.onExhausted(factory);
      fail("should throw PoolExhaustedException");
    } catch (final PoolExhaustedException e) {
      // pass
    }
  }

  @Test
  public void testOnStateChanged() {
    final MethodCallPoolableFactory factory = new MethodCallPoolableFactory();
    final FailExhaustedPolicy<Integer> policy = new FailExhaustedPolicy<Integer>();

    policy.onStateChanged(factory);  // no effect
  }
}
