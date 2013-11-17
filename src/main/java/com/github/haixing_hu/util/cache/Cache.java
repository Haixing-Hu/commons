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

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import com.github.haixing_hu.io.Openable;

/**
 * The interface of a cache.
 * <p>
 * A cache is a facility which reads data from a underlying data source, and
 * store them in the memory in order to provide fast access.
 * </p>
 * <p>
 * The cache may store all data in the memory, or just store some amount of data
 * in the memory, depending on its caching policy.
 * </p>
 * <p>
 * The cache may fetch a piece of data from the data source a time, or fetch a
 * batch of data a time.
 * </p>
 * <p>
 * Sometime the cached data may be abandoned, if they satisfy some condition
 * (i.e., too old, too little access frequency, etc). If the client request the
 * abandoned data again, the cache will fetch the data from the underlying data
 * source again.
 * </p>
 * <p>
 * The implementation do NOT need to be thread-safe. In order to get a
 * thread-safe cache, use the {@link SynchronizedCache} to wrap a non
 * thread-safe {@link Cache} object.
 * </p>
 *
 * @author Haixing Hu
 */
public interface Cache<K, V> extends Closeable, Openable {

  /**
   * Tests whether this cache is opened.
   *
   * @return true if this cache is opened; false otherwise.
   */
  @Override
  public boolean isOpened();

  /**
   * Opens this cache.
   * <p>
   * If this cache has already been opened, an {@link IOException} will be
   * thrown.
   * </p>
   *
   * @throws IOException
   *           if any I/O error occurred.
   */
  @Override
  public void open() throws IOException;

  /**
   * Gets the value for the given key from the cache.
   *
   * @param key
   *          the key of the value to be get.
   * @return the value corresponding to the key, or null if no such value.
   * @throws IOException
   *           if any I/O error occurred.
   */
  public V get(K key) throws IOException;

  /**
   * Gets all values in the underlying data source of this cache.
   *
   * @return the collection of all values in the underlying data source of this
   *         cache. The returned collection may be a simple collection, or a
   *         "lazy" (i.e., request the data on demand) collection.
   * @throws IOException
   *           if any I/O error occurred.
   */
  public Collection<V> getAll() throws IOException;

  /**
   * Gets all keys of values in the underlying data source of this cache.
   *
   * @return the set of all keys of values in the underlying data source of this
   *         cache. The returned collection may be a simple set, or a "lazy"
   *         (i.e., request the data on demand) set.
   * @throws IOException
   *           if any I/O error occurred.
   */
  public Set<K> keySet() throws IOException;

  /**
   * Tests whether the data corresponding to the given key is contained in the
   * underlying data source of this cache.
   *
   * @param key
   *          the key to be test.
   * @return true if the data corresponding to the given key is contained in the
   *         underlying data source of this cache; false otherwise.
   * @throws IOException
   *           if any I/O error occurred.
   */
  public boolean containsKey(K key) throws IOException;

  /**
   * Tests whether the data corresponding to the given key is cached in this
   * cache.
   *
   * @param key
   *          the key to be test.
   * @return true if the data corresponding to the given key is cached in this
   *         cache; false otherwise.
   * @throws IOException
   *           if any I/O error occurred.
   */
  public boolean cachesKey(K key) throws IOException;

  /**
   * Closes this cache.
   * <p>
   * If this cache has already been closed, calling this function has no effect.
   * </p>
   *
   * @throws IOException
   *           if any I/O error occurred.
   */
  @Override
  public void close() throws IOException;
}
