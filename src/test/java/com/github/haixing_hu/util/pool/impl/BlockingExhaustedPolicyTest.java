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

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

import org.junit.Test;

import com.github.haixing_hu.util.pool.MethodCallPoolableFactory;
import com.github.haixing_hu.util.pool.impl.BlockingExhaustedPolicy;
import com.github.haixing_hu.util.pool.impl.ExhaustedPolicy;

/**
 * Unit test of the {@link BlockingExhaustedPolicy} class.
 *
 * @author Haixing Hu
 */
public class BlockingExhaustedPolicyTest extends ExhaustedPolicyTest {

  static final int THREAD_COUNT = 10;
  static final long WAIT_DELAY = 1000;

  @Override
  protected <T> ExhaustedPolicy<T> makeExhaustedPolicy() {
    return new BlockingExhaustedPolicy<T>();
  }

  @Override
  protected <T> ExhaustedPolicy<T> makeExhaustedPolicy(final int maxActiveCount) {
    return new BlockingExhaustedPolicy<T>(maxActiveCount);
  }

  @Test
  public void testUnlimitedBlocking() {
    final MethodCallPoolableFactory factory = new MethodCallPoolableFactory();
    final BlockingExhaustedPolicy<Integer> policy = new BlockingExhaustedPolicy<Integer>();

    // creating borrowing threads
    final List<Integer> sleepOrders = new ArrayList<Integer>(THREAD_COUNT);
    final List<Integer> wakeUpOrders = new ArrayList<Integer>(THREAD_COUNT);
    final BorrowThread[] threads = new BorrowThread[THREAD_COUNT];
    for (int i = 0; i < THREAD_COUNT; ++i) {
      threads[i] = new BorrowThread(i, factory, policy, sleepOrders, wakeUpOrders);
    }

    // fire the onExhausted() event of each borrowing thread.
    for (int i = 0; i < THREAD_COUNT; ++i) {
      threads[i].start();
    }

    // wait some time
    try {
      System.out.println("Delay " + WAIT_DELAY + " millseconds ...");
      Thread.sleep(WAIT_DELAY);
    } catch (final InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // checking the state of borrowing threads
    for (int i = 0; i < THREAD_COUNT; ++i) {
      assertEquals(State.WAITING, threads[i].getState());
    }

    // wake up all borrowing threads
    for (int i = 0; i < THREAD_COUNT; ++i) {
      policy.onStateChanged(factory);
    }

    // wait some time
    try {
      System.out.println("Delay " + WAIT_DELAY + " millseconds ...");
      Thread.sleep(WAIT_DELAY);
    } catch (final InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // checking the state of borrowing threads
    for (int i = 0; i < THREAD_COUNT; ++i) {
      assertEquals(State.TERMINATED, threads[i].getState());
    }

    // check the wakeup orders
    assertEquals(THREAD_COUNT, wakeUpOrders.size());
    for (int i = 0; i < THREAD_COUNT; ++i) {
      assertEquals(sleepOrders.get(i), wakeUpOrders.get(i));
    }
  }

  class BorrowThread extends Thread {

    final MethodCallPoolableFactory factory;
    private final int number;
    private final BlockingExhaustedPolicy<Integer> policy;
    private final List<Integer> sleepOrders;
    private final List<Integer> wakeUpOrders;

    public BorrowThread(final int number, final MethodCallPoolableFactory factory,
        final BlockingExhaustedPolicy<Integer> policy,
        final List<Integer> sleepOrders,
        final List<Integer> wakeUpOrders) {
      this.number = number;
      this.factory = factory;
      this.policy = policy;
      this.sleepOrders = sleepOrders;
      this.wakeUpOrders = wakeUpOrders;
    }

    public int getNumber() {
      return number;
    }

    @Override
    public void run() {
      final Lock lock = policy.getLock();
      lock.lock();
      try {
        System.out.println("Before calling onExhausted at thread " + number);
        sleepOrders.add(number);
        final Integer obj = policy.onExhausted(factory);
        System.out.println("After calling onExhausted at thread " + number);
        assertEquals(null, obj);
        wakeUpOrders.add(number);
      } finally {
        lock.unlock();
      }
    }
  }
}
