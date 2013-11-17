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

import junit.framework.TestCase;

import org.junit.Test;

import com.github.haixing_hu.util.pool.MethodCallPoolableFactory;
import com.github.haixing_hu.util.pool.impl.ExhaustedPolicy;

/**
 * The abstract base class for unit test classes of the implementations of
 * {@link ExhaustedPolicy}.
 *
 * @author Haixing Hu
 */
public abstract class ExhaustedPolicyTest extends TestCase {

  protected abstract <T> ExhaustedPolicy<T> makeExhaustedPolicy();

  protected abstract <T> ExhaustedPolicy<T> makeExhaustedPolicy(int maxActiveCount);

  @Test
  public void testGetMaxActive() {
    new MethodCallPoolableFactory();
    ExhaustedPolicy<Integer> policy;

    policy = makeExhaustedPolicy();
    assertEquals(ExhaustedPolicy.DEFAULT_MAX_ACTIVE_COUNT, policy.getMaxActiveCount());

    policy = makeExhaustedPolicy(10);
    assertEquals(10, policy.getMaxActiveCount());

    policy = makeExhaustedPolicy(0);
    assertEquals(0, policy.getMaxActiveCount());

    policy = makeExhaustedPolicy(-1);
    assertEquals(Integer.MAX_VALUE, policy.getMaxActiveCount());
  }

  @Test
  public void testIsExhausted() {
    new MethodCallPoolableFactory();
    ExhaustedPolicy<Integer> policy;

    policy = makeExhaustedPolicy();
    testIsExhausted(policy);

    policy = makeExhaustedPolicy(10);
    testIsExhausted(policy);

    policy = makeExhaustedPolicy(0);
    testIsExhausted(policy);

    policy = makeExhaustedPolicy(-1);
    testIsExhausted(policy);
  }

  private void testIsExhausted(final ExhaustedPolicy<Integer> policy) {
    final int maxActiveCount = policy.getMaxActiveCount();
    for (int i = 0; i < maxActiveCount; ++i) {
      assertEquals(false, policy.isExhausted(i));
    }
    assertEquals(true, policy.isExhausted(maxActiveCount));
  }


}
