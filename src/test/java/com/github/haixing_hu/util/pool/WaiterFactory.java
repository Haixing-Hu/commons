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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.concurrent.ThreadSafe;

import com.github.haixing_hu.util.pool.KeyedPoolableFactory;
import com.github.haixing_hu.util.pool.PoolException;
import com.github.haixing_hu.util.pool.PoolableFactory;

/**
 * Object factory with configurable latencies for object life cycle methods.
 * <p>
 * This factory will also track and enforce maxActive, maxActivePerKey
 * contracts. If the factory's maxActive / maxActivePerKey are set to match
 * those of the pool, makeObject will throw IllegalStateException if the number
 * of makes - destroys (per key) exceeds the configured max.
 * </p>
 */
@ThreadSafe
public class WaiterFactory implements PoolableFactory<Waiter>,
    KeyedPoolableFactory<String, Waiter> {

  /** Latency of activateObject */
  private final long activateLatency;

  /** Latency of destroyObject */
  private final long destroyLatency;

  /** Latency of makeObject */
  private final long makeLatency;

  /** Latency of passivateObject */
  private final long passivateLatency;

  /** Latency of validateObject */
  private final long validateLatency;

  /** Latency of doWait for Waiter instances created by this factory */
  private final long waiterLatency;

  /** Probability that passivation will invalidate Waiter instances */
  private final double passivateInvalidationProbability;

  /** Count of (makes - destroys) since last reset */
  private long activeCount = 0;

  /** Count of (makes - destroys) per key since last reset */
  private final Map<String, Integer> activeCounts = new HashMap<String, Integer>();

  /** Maximum of (makes - destroys) - if exceeded IllegalStateException */
  private final long maxActive; // GKOP 1.x calls this maxTotal

  /** Maximum of (makes - destroys) per key */
  private final long maxActivePerKey; // GKOP 1.x calls this maxActive

  public WaiterFactory(final long activateLatency, final long destroyLatency,
      final long makeLatency, final long passivateLatency,
      final long validateLatency, final long waiterLatency,
      final long maxActive, final long maxActivePerKey,
      final double passivateInvalidationProbability) {
    this.activateLatency = activateLatency;
    this.destroyLatency = destroyLatency;
    this.makeLatency = makeLatency;
    this.passivateLatency = passivateLatency;
    this.validateLatency = validateLatency;
    this.waiterLatency = waiterLatency;
    this.maxActive = maxActive;
    this.maxActivePerKey = maxActivePerKey;
    this.passivateInvalidationProbability = passivateInvalidationProbability;
  }

  public WaiterFactory(final long activateLatency, final long destroyLatency,
      final long makeLatency, final long passivateLatency,
      final long validateLatency, final long waiterLatency) {
    this(activateLatency, destroyLatency, makeLatency, passivateLatency,
        validateLatency, waiterLatency, Long.MAX_VALUE, Long.MAX_VALUE, 0);
  }

  public WaiterFactory(final long activateLatency, final long destroyLatency,
      final long makeLatency, final long passivateLatency,
      final long validateLatency, final long waiterLatency, final long maxActive) {
    this(activateLatency, destroyLatency, makeLatency, passivateLatency,
        validateLatency, waiterLatency, maxActive, Long.MAX_VALUE, 0);
  }

  @Override
  public void activate(final Waiter obj) throws PoolException {
    doWait(activateLatency);
    obj.setActive(true);
  }

  @Override
  public void destroy(final Waiter obj) {
    doWait(destroyLatency);
    obj.setValid(false);
    obj.setActive(false);
    // Decrement *after* destroy
    synchronized (this) {
      --activeCount;
    }
  }

  @Override
  public Waiter create() throws PoolException {
    // Increment and test *before* make
    synchronized (this) {
      if (activeCount >= maxActive) {
        throw new PoolException("Too many active instances: " + activeCount
            + " in circulation with maxActive = " + maxActive);
      } else {
        ++activeCount;
      }
    }
    doWait(makeLatency);
    return new Waiter(false, true, waiterLatency);
  }

  @Override
  public void passivate(final Waiter obj) throws PoolException {
    obj.setActive(false);
    doWait(passivateLatency);
    if (Math.random() < passivateInvalidationProbability) {
      obj.setValid(false);
    }
  }

  @Override
  public boolean validate(final Waiter obj) {
    doWait(validateLatency);
    return obj.isValid();
  }

  protected void doWait(final long latency) {
    try {
      Thread.sleep(latency);
    } catch (final InterruptedException ex) {
      // ignore
    }
  }

  public synchronized void reset() {
    activeCount = 0;
    if (activeCounts.isEmpty()) {
      return;
    }
    for (final String key : activeCounts.keySet()) {
      activeCounts.put(key, Integer.valueOf(0));
    }
  }

  public synchronized long getMaxActive() {
    return maxActive;
  }

  @Override
  public void activate(final String key, final Waiter obj) throws PoolException {
    activate(obj);
  }

  @Override
  public void destroy(final String key, final Waiter obj) {
    destroy(obj);
    synchronized (this) {
      final Integer count = activeCounts.get(key);
      if (count != null) {
        activeCounts.put(key, Integer.valueOf(count.intValue() - 1));
      }
    }
  }

  @Override
  public Waiter create(final String key) throws PoolException {
    synchronized (this) {
      Integer count = activeCounts.get(key);
      if (count == null) {
        count = Integer.valueOf(1);
        activeCounts.put(key, count);
      } else {
        if (count.intValue() >= maxActivePerKey) {
          throw new PoolException("Too many active " + "instances for key = "
              + key + ": " + count.intValue() + " in circulation "
              + "with maxActivePerKey = " + maxActivePerKey);
        } else {
          activeCounts.put(key, Integer.valueOf(count.intValue() + 1));
        }
      }
    }
    return create();
  }

  @Override
  public void passivate(final String key, final Waiter obj)
      throws PoolException {
    passivate(obj);
  }

  @Override
  public boolean validate(final String key, final Waiter obj) {
    return validate(obj);
  }

}
