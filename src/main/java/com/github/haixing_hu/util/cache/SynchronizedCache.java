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

package com.github.haixing_hu.util.cache;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javax.annotation.concurrent.ThreadSafe;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * Simple {@link Cache} wrapper that synchronizes all calls that access the cache.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class SynchronizedCache<KEY, VALUE> implements Cache<KEY, VALUE> {

  private final Cache<KEY, VALUE> cache;

  public SynchronizedCache(final Cache<KEY, VALUE> cache) {
    this.cache = requireNonNull("cache", cache);
  }

  @Override
  public synchronized boolean isOpened() {
    return cache.isOpened();
  }

  @Override
  public synchronized void open() throws IOException {
    cache.open();
  }

  @Override
  public synchronized VALUE get(final KEY key) throws IOException {
    return cache.get(key);
  }

  @Override
  public synchronized Collection<VALUE> getAll() throws IOException {
    return cache.getAll();
  }

  @Override
  public synchronized Set<KEY> keySet() throws IOException {
    return cache.keySet();
  }

  @Override
  public synchronized boolean containsKey(final KEY key) throws IOException {
    return cache.containsKey(key);
  }

  @Override
  public synchronized boolean cachesKey(final KEY key) throws IOException {
    return cache.cachesKey(key);
  }

  @Override
  public synchronized void close() throws IOException {
    cache.close();
  }

}
