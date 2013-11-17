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

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.concurrent.ThreadSafe;

import com.github.haixing_hu.util.pool.KeyedPoolableFactory;
import com.github.haixing_hu.util.pool.PoolException;
import com.github.haixing_hu.util.pool.PoolableFactory;

/**
 * Factory that creates {@link VisitTracker} instances.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public class VisitTrackerFactory implements PoolableFactory<VisitTracker>,
    KeyedPoolableFactory<String, VisitTracker> {

  private final AtomicInteger nextId;

  public VisitTrackerFactory() {
    super();
    nextId = new AtomicInteger(0);
  }

  @Override
  public VisitTracker create() {
    return new VisitTracker(nextId.incrementAndGet());
  }

  @Override
  public VisitTracker create(final String key) {
    return new VisitTracker(nextId.incrementAndGet(), key);
  }

  @Override
  public boolean validate(final VisitTracker obj) {
    return obj.validate();
  }

  @Override
  public boolean validate(final String key, final VisitTracker obj) {
    return obj.validate();
  }

  @Override
  public void activate(final VisitTracker obj) throws PoolException {
    obj.activate();
  }

  @Override
  public void activate(final String key, final VisitTracker obj)
      throws PoolException {
    obj.activate();
  }

  @Override
  public void passivate(final VisitTracker obj) throws PoolException {
    obj.passivate();
  }

  @Override
  public void passivate(final String key, final VisitTracker obj)
      throws PoolException {
    obj.passivate();
  }

  @Override
  public void destroy(final VisitTracker obj) {
    obj.destroy();
  }

  @Override
  public void destroy(final String key, final VisitTracker obj) {
    obj.destroy();
  }

  public void resetId() {
    nextId.set(0);
  }
}
