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
import java.util.NoSuchElementException;

import javax.annotation.concurrent.GuardedBy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * The abstract base class for implementing the {@link KeyedPool}.
 *
 * @author Haixing Hu
 */
public abstract class AbstractKeyedPool<K, V> implements
    KeyedPool<K, V> {

  protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractKeyedPool.class);

  protected final KeyedPoolableFactory<K, V> factory;
  @GuardedBy("activeCounts")
  protected final Map<K, Integer> activeCounts;
  protected volatile boolean closed;

  protected AbstractKeyedPool(final KeyedPoolableFactory<K, V> factory) {
    this.factory = requireNonNull("factory", factory);
    this.activeCounts = new HashMap<K, Integer>();
    this.closed = false;
  }

  public KeyedPoolableFactory<K, V> getFactory() {
    return factory;
  }

  @Override
  public V borrow(final K key) throws PoolException,
      NoSuchElementException, IllegalStateException {
    if (closed) {
      throw new PoolClosedException();
    }
    return doBorrow(key);
  }

  protected abstract V doBorrow(K key) throws PoolException,
      NoSuchElementException, IllegalStateException;

  @Override
  public void restore(final K key, final V obj) throws PoolException {
    requireNonNull("obj", obj);
    if (closed) {
      // if the pool is closed, decrease the active count and
      // destroy the object
      LOGGER.warn("Restore an object to a closed pool: {}", obj);
      decreaseActiveCount(key);
      factory.destroy(key, obj);
    } else {
      doRestore(key, obj);
    }
  }

  protected void decreaseActiveCount(final K key) {
    synchronized (activeCounts) {
      final Integer count = activeCounts.get(key);
      if ((count == null) || (count.intValue() == 0)) {
        LOGGER.error("No active object with the specified key: {}", key);
      } else {
        final Integer newCount = Integer.valueOf(count.intValue() - 1);
        activeCounts.put(key, newCount);
      }
    }
  }

  protected void increaseActiveCount(final K key) {
    synchronized (activeCounts) {
      final Integer count = activeCounts.get(key);
      if (count == null) {
        activeCounts.put(key, Integer.valueOf(1));
      } else {
        final Integer newCount = Integer.valueOf(count.intValue() + 1);
        activeCounts.put(key, newCount);
      }
    }
  }

  protected abstract void doRestore(K key, V obj) throws PoolException;

  @Override
  public void invalidate(final K key, final V obj) throws PoolException {
    if (closed) {
      throw new PoolClosedException();
    }
    requireNonNull("obj", obj);
    doInvalidate(key, obj);
  }

  protected abstract void doInvalidate(K key, V obj) throws PoolException;

  @Override
  public void add(final K key) throws PoolException, IllegalStateException,
      UnsupportedOperationException {
    if (closed) {
      throw new PoolClosedException();
    }
    doAdd(key);
  }

  protected abstract void doAdd(K key) throws PoolException,
      IllegalStateException, UnsupportedOperationException;

  @Override
  public int getActiveCount(final K key) throws UnsupportedOperationException {
    synchronized (activeCounts) {
      final Integer count = activeCounts.get(key);
      if (count == null) {
        activeCounts.put(key, Integer.valueOf(0));
        return 0;
      } else {
        return count.intValue();
      }
    }
  }

  @Override
  public int getActiveCount() throws UnsupportedOperationException {
    int result = 0;
    synchronized (activeCounts) {
      for (final Integer count : activeCounts.values()) {
        result += count.intValue();
      }
    }
    return result;
  }

  @Override
  public void clear(final K key) throws PoolException,
      UnsupportedOperationException {
    if (closed) {
      throw new PoolClosedException();
    }
    doClear(key);
  }

  protected abstract void doClear(K key) throws PoolException,
      UnsupportedOperationException;

  @Override
  public void clear() throws PoolException, UnsupportedOperationException {
    if (closed) {
      throw new PoolClosedException();
    }
    doClear();
  }

  protected abstract void doClear() throws PoolException,
      UnsupportedOperationException;

  @Override
  public void close() {
    if (closed) {
      return;
    } else {
      doClose();
      closed = true;
    }
  }

  protected abstract void doClose();

  public boolean isClosed() {
    return closed;
  }

  protected final void doActive(final K key, final V obj) throws PoolException {
    try {
      factory.activate(key, obj); // may throw
    } catch (final PoolException e) {
      LOGGER.error("Failed to active the object of key '{}': {}", key, obj);
      factory.destroy(key, obj);
      throw e;
    }
  }

  protected final void doPassivate(final K key, final V obj) throws PoolException {
    try {
      factory.passivate(key, obj); // may throw
    } catch (final PoolException e) {
      LOGGER.error("Failed to passivate the object of key '{}': {}", key, obj);
      factory.destroy(key, obj);
      throw e;
    }
  }
}
