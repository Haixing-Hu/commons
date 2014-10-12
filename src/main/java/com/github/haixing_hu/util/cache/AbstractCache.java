/*
 * Copyright (c) 2014  Haixing Hu
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
 */
package com.github.haixing_hu.util.cache;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.github.haixing_hu.io.exception.AlreadyOpenedException;
import com.github.haixing_hu.io.exception.NotOpenedException;

/**
 * The abstract base class for implementing the {@link Cache} interface.
 *
 * @author Haixing Hu
 */
public abstract class AbstractCache<KEY, VALUE> implements Cache<KEY, VALUE> {

  /**
   * The default value of maximum allowed number of objects in the cache, which is {@value}.
   */
  public static final int DEFAULT_MAX_CACHED = Integer.MAX_VALUE;

  /**
   * The data stored in the internal map, which contains the value and its last access time,
   * as well as its access frequency.
   *
   * @author Haixing Hu
   */
  protected class Data {
    long lastAccessTime = 0;
    long accessFrequency = 0;
    VALUE value = null;
  }

  protected boolean opened;
  protected int maxCached;
  protected Map<KEY, Data> cached;

  protected AbstractCache() {
    opened = false;
    maxCached = DEFAULT_MAX_CACHED;
    cached = new HashMap<KEY, Data>();
  }

  protected AbstractCache(final int maxCached) {
    this.opened = false;
    this.maxCached = (maxCached < 0 ? Integer.MAX_VALUE : maxCached);
    this.cached = new HashMap<KEY, Data>();
  }

  protected AbstractCache(final int intitalCapacity, final float loadFactor) {
    this.opened = false;
    this.maxCached = DEFAULT_MAX_CACHED;
    this.cached = new HashMap<KEY, Data>(intitalCapacity, loadFactor);
  }

  protected AbstractCache(final int maxCached, final int intitalCapacity,
      final float loadFactor) {
    this.opened = false;
    this.maxCached = (maxCached < 0 ? Integer.MAX_VALUE : maxCached);
    this.cached = new HashMap<KEY, Data>(intitalCapacity, loadFactor);
  }

  @Override
  public boolean isOpened() {
    return opened;
  }

  @Override
  public void open() throws IOException {
    if (opened) {
      throw new AlreadyOpenedException();
    }
    doOpen();
    opened = true;
  }

  /**
   * Performs the open operation.
   * <p>
   * Implementation may initialize the underlying data source, open the
   * underlying data source, fetch some or all the data.
   * </p>
   *
   * @throws IOException
   *           if any I/O error occurred.
   */
  protected abstract void doOpen() throws IOException;

  @Override
  public VALUE get(final KEY key) throws IOException {
    if (! opened) {
      throw new NotOpenedException();
    }
    Data data = cached.get(key);
    if (data == null) {
      data = new Data();
      data.value = fetchValue(key);
      if (cached.size() > maxCached - 1) {
        cleanCache(cached.size() - 1);
      }
      cached.put(key, data);
    }
    ++data.accessFrequency;
    data.lastAccessTime = System.currentTimeMillis();
    return data.value;
  }

  /**
   * Fetches the value corresponding to the specified key from the underlying
   * data source.
   *
   * @param key
   *          the key of the value to be fetched.
   * @return the value corresponding to the specified key fetched from the
   *         underlying data source.
   * @throws IOException
   *           if any I/O error occurred.
   */
  protected abstract VALUE fetchValue(KEY key) throws IOException;

  /**
   * Cleans the cache so that it has no more than the desired number of objects.
   * <p>
   * Implementation may choose how to select the objects to remove from the cache.
   * </p>
   * @param desiredSize
   *          the desired number of object remained in the cache after calling
   *          this function.
   * @throws IOException
   *           If any I/O error occurred.
   */
  protected abstract void cleanCache(int desiredSize) throws IOException;

  @Override
  public Collection<VALUE> getAll() throws IOException {
    if (! opened) {
      throw new NotOpenedException();
    }
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<KEY> keySet() throws IOException {
    if (! opened) {
      throw new NotOpenedException();
    }
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean containsKey(final KEY key) throws IOException {
    if (! opened) {
      throw new NotOpenedException();
    }
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean cachesKey(final KEY key) throws IOException {
    if (! opened) {
      throw new NotOpenedException();
    }
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void close() throws IOException {
    if (! opened) {
      return;
    }
    doClose();
    opened = false;
  }

  /**
   * Performs the close operation.
   *
   * @throws IOException
   *           if any I/O error occurred.
   */
  protected abstract void doClose() throws IOException;

}
