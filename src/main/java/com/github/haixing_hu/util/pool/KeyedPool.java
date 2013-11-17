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

/**
 * A "keyed" pooling interface.
 * <p>
 * A keyed pool pools instances of multiple types. Each type may be accessed
 * using an arbitrary key.
 * </p>
 * <p>
 * Example of use:
 * </p>
 * <pre style="border:solid thin; padding: 1ex;" * >
 * T obj = <code style="color:#00C">null</code>;
 * K key = ... ;  <code style="color:#0C0">// set the key</code>
 * <code style="color:#00C">try</code> {
 *   obj = pool.borrow(key);
 *   <code style="color:#00C">try</code> {
 *     <code style="color:#0C0">//...use the object...</code>
 *   } <code style="color:#00C">catch</code>(Exception e) {
 *     <code style="color:#0C0">// invalidate the object</code>
 *     pool.invalidate(key, obj);
 *     <code style="color:#0C0">// do not return the object to the pool twice</code>
 *     obj = <code style="color:#00C">null</code>;
 *   } <code style="color:#00C">finally</code> {
 *     <code style="color:#0C0">// make sure the object is returned to the pool</code>
 *     <code style="color:#00C">if</code>(obj != <code style="color:#00C">null</code>) {
 *       pool.restore(key, obj);
 *     }
 *   }
 * } <code style="color:#00C">catch</code>(PoolException e) {
 *   <code style="color:#0C0">// failed to borrow an object</code>
 * }
 * </pre>
 * <p>
 * {@link KeyedPool} implementations <i>may</i> choose to store at most one
 * instance per key value, or may choose to maintain a pool of instances for
 * each key (essentially creating a {@link java.util.Map Map} of {@link Pool
 * pools}).
 * </p>
 *
 * @author Haixing Hu
 * @see KeyedPoolableFactory
 * @see KeyedPoolFactory
 * @see Pool
 */
public interface KeyedPool<K, V> {

  /**
   * Obtains an instance from this pool for the specified <code>key</code>.
   * <p>
   * Instances returned from this method will have been either newly created
   * with {@link KeyedPoolableFactory#create create} or will be a previously
   * idle object and have been activated with
   * {@link KeyedPoolableFactory#activate activate} and then validated with
   * {@link KeyedPoolableFactory#validate validate}.
   * <p>
   * By contract, clients <strong>must</strong> return the borrowed object using
   * {@link #restore restore}, {@link #invalidate invalidate}, or a related
   * method as defined in an implementation or sub-interface, using a
   * <code>key</code> that is {@link Object#equals equivalent} to the one used
   * to borrow the instance in the first place.
   * <p>
   * The behaviour of this method when the pool has been exhausted is not
   * strictly specified (although it may be specified by implementations). Older
   * versions of this method would return <code>null</code> to indicate
   * exhaustion, newer versions are encouraged to throw a
   * {@link NoSuchElementException}.
   *
   * @param key
   *          the key used to obtain the object
   * @return an instance from this pool.
   * @throws IllegalStateException
   *           after {@link #close close} has been called on this pool
   * @throws PoolException
   *           when {@link KeyedPoolableFactory#create create} throws an
   *           exception
   * @throws NoSuchElementException
   *           when the pool is exhausted and cannot or will not return another
   *           instance
   */
  public V borrow(K key) throws PoolException,
      NoSuchElementException, IllegalStateException;

  /**
   * Return an instance to the pool.
   * <p>
   * By contract, <code>obj</code> <strong>must</strong> have been obtained
   * using {@link #borrow borrow} or a related method as defined in an
   * implementation or sub-interface using a <code>key</code> that is equivalent
   * to the one used to borrow the instance in the first place.
   * </p>
   *
   * @param key
   *          the key used to obtain the object
   * @param obj
   *          a {@link #borrow borrowed} instance to be returned.
   * @throws PoolException
   */
  public void restore(K key, V obj) throws PoolException;

  /**
   * Invalidates an object from the pool.
   * <p>
   * By contract, <code>obj</code> <strong>must</strong> have been obtained
   * using {@link #borrow borrow} or a related method as defined in an
   * implementation or sub-interface using a <code>key</code> that is equivalent
   * to the one used to borrow the <code>Object</code> in the first place.
   * </p>
   * <p>
   * This method should be used when an object that has been borrowed is
   * determined (due to an exception or other problem) to be invalid.
   * </p>
   *
   * @param key
   *          the key used to obtain the object
   * @param obj
   *          a {@link #borrow borrowed} instance to be returned.
   * @throws PoolException
   */
  public void invalidate(K key, V obj) throws PoolException;

  /**
   * Create an object using the {@link KeyedPoolableFactory factory} or other
   * implementation dependent mechanism, passivate it, and then place it in the
   * idle object pool.
   * <p>
   * This method is useful for "pre-loading" a pool with idle objects (Optional
   * operation).
   * </p>
   *
   * @param key
   *          the key a new instance should be added to
   * @throws PoolException
   *           when {@link KeyedPoolableFactory#create} fails.
   * @throws IllegalStateException
   *           after {@link #close} has been called on this pool.
   * @throws UnsupportedOperationException
   *           when this pool cannot add new idle objects.
   */
  public void add(K key) throws PoolException, IllegalStateException,
      UnsupportedOperationException;

  /**
   * Returns the number of instances corresponding to the given <code>key</code>
   * currently idle in this pool (optional operation). Returns a negative value
   * if this information is not available.
   *
   * @param key
   *          the key to query
   * @return the number of instances corresponding to the given <code>key</code>
   *         currently idle in this pool or a negative value if unsupported
   * @throws UnsupportedOperationException
   *           <strong>deprecated</strong>: when this implementation doesn't
   *           support the operation
   */
  public int getIdleCount(K key) throws UnsupportedOperationException;

  /**
   * Returns the total number of instances currently idle in this pool (optional
   * operation). Returns a negative value if this information is not available.
   *
   * @return the total number of instances currently idle in this pool or a
   *         negative value if unsupported
   * @throws UnsupportedOperationException
   *           <strong>deprecated</strong>: when this implementation doesn't
   *           support the operation
   */
  public int getIdleCount() throws UnsupportedOperationException;

  /**
   * Returns the number of instances currently borrowed from but not yet
   * returned to the pool corresponding to the given <code>key</code> (optional
   * operation). Returns a negative value if this information is not available.
   *
   * @param key
   *          the key to query
   * @return the number of instances corresponding to the given <code>key</code>
   *         currently borrowed in this pool or a negative value if unsupported
   * @throws UnsupportedOperationException
   *           <strong>deprecated</strong>: when this implementation doesn't
   *           support the operation
   */
  public int getActiveCount(K key) throws UnsupportedOperationException;

  /**
   * Returns the total number of instances current borrowed from this pool but
   * not yet returned (optional operation). Returns a negative value if this
   * information is not available.
   *
   * @return the total number of instances currently borrowed from this pool or
   *         a negative value if unsupported
   * @throws UnsupportedOperationException
   *           <strong>deprecated</strong>: when this implementation doesn't
   *           support the operation
   */
  public int getActiveCount() throws UnsupportedOperationException;

  /**
   * Clears the specified pool, removing all pooled instances corresponding to
   * the given <code>key</code> (optional operation). Throws
   * {@link UnsupportedOperationException} if the pool cannot be cleared.
   *
   * @param key
   *          the key to clear
   * @throws PoolException
   *           when {@link KeyedPoolableFactory#destroy} fails.
   * @throws UnsupportedOperationException
   *           when this implementation doesn't support the operation
   */
  public void clear(K key) throws PoolException,
      UnsupportedOperationException;

  /**
   * Clears the pool, removing all pooled instances (optional operation). Throws
   * {@link UnsupportedOperationException} if the pool cannot be cleared.
   *
   * @throws PoolException
   *           when {@link KeyedPoolableFactory#destroy} fails.
   * @throws UnsupportedOperationException
   *           when this implementation doesn't support the operation
   */
  public void clear() throws PoolException, UnsupportedOperationException;

  /**
   * Close this pool, and free any resources associated with it.
   * <p>
   * Calling {@link #add add} or {@link #borrow borrow} after invoking this
   * method on a pool will cause them to throw an {@link IllegalStateException}.
   * </p>
   * <p>
   * Implementations should silently fail if not all resources can be freed.
   * </p>
   */
  public void close();
}
