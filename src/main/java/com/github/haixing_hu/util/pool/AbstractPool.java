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

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * An abstraction base class for implementing the {@link Pool} interface.
 *
 * @author Haixing Hu
 */
public abstract class AbstractPool<T> implements Pool<T> {

  protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractPool.class);

  protected final PoolableFactory<T> factory;
  protected final AtomicInteger activeCount;
  protected volatile boolean closed;

  protected AbstractPool(final PoolableFactory<T> factory) {
    this.factory = requireNonNull("factory", factory);
    this.activeCount = new AtomicInteger(0);
    this.closed = false;
  }

  public PoolableFactory<T> getFactory() {
    return factory;
  }

  @Override
  public T borrow() throws PoolException, NoSuchElementException,
      IllegalStateException {
    if (closed) {
      throw new PoolClosedException();
    }
    return doBorrow();
  }

  protected abstract T doBorrow() throws PoolException,
      NoSuchElementException, IllegalStateException;

  @Override
  public void restore(final T obj) throws PoolException {
    requireNonNull("obj", obj);
    if (closed) {
      // if the pool is closed, decrease the active count and
      // destroy the object
      LOGGER.warn("Restore an object to a closed pool: {}", obj);
      activeCount.decrementAndGet();
      factory.destroy(obj);
    } else {
      doRestore(obj);
    }
  }

  protected abstract void doRestore(T obj) throws PoolException;

  @Override
  public void invalidate(final T obj) throws PoolException {
    if (closed) {
      throw new PoolClosedException();
    }
    requireNonNull("obj", obj);
    doInvalidate(obj);
  }

  protected abstract void doInvalidate(T obj) throws PoolException;

  @Override
  public void add() throws PoolException, IllegalStateException,
      UnsupportedOperationException {
    if (closed) {
      throw new PoolClosedException();
    }
    doAdd();
  }

  protected abstract void doAdd() throws PoolException, IllegalStateException,
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

  @Override
  public int getActiveCount() {
    return activeCount.get();
  }

  protected final void doActive(final T obj) throws PoolException {
    try {
      factory.activate(obj); // may throw
    } catch (final PoolException e) {
      LOGGER.error("Failed to active the object: {}", obj);
      factory.destroy(obj);
      throw e;
    }
  }

  protected final void doPassivate(final T obj) throws PoolException {
    try {
      factory.passivate(obj);
    } catch (final PoolException e) {
      LOGGER.error("Failed to passivate the object: {}", obj);
      factory.destroy(obj);
      throw e;
    }
  }
}
