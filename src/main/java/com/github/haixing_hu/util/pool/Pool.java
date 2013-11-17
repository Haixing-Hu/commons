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
 * A pooling interface.
 * <p>
 * The {@link Pool} defines a trivially simple pooling interface. The only
 * required methods are {@link #borrow borrow}, {@link #restore restore} and
 * {@link #invalidate invalidate}.
 * </p>
 * <p>
 * The implementation must be thread-safe.
 * </p>
 * <p>
 * Example of use:
 * </p>
 * <pre style="border:solid thin; padding: 1ex;" * >
 * T obj = <code style="color:#00C">null</code>;
 * <code style="color:#00C">try</code> {
 *   obj = pool.borrow();
 *   <code style="color:#00C">try</code> {
 *     <code style="color:#0C0">//...use the object...</code>
 *   } <code style="color:#00C">catch</code>(Exception e) {
 *     <code style="color:#0C0">// invalidate the object</code>
 *     pool.invalidate(obj);
 *     <code style="color:#0C0">// do not return the object to the pool twice</code>
 *     obj = <code style="color:#00C">null</code>;
 *   } <code style="color:#00C">finally</code> {
 *     <code style="color:#0C0">// make sure the object is returned to the pool</code>
 *     <code style="color:#00C">if</code>(obj != <code style="color:#00C">null</code>) {
 *       pool.restore(obj);
 *     }
 *   }
 * } <code style="color:#00C">catch</code>(PoolException e) {
 *   <code style="color:#0C0">// failed to borrow an object</code>
 * }
 * </pre>
 *
 * @author Haixing Hu
 */
public interface Pool<T> {

  /**
   * Obtains an instance from this pool.
   * <p>
   * Instances returned from this method will have been either newly created
   * with {@link PoolableFactory#create create} or will be a previously
   * idle object and have been activated with
   * {@link PoolableFactory#activate activate} and then validated
   * with {@link PoolableFactory#validate validate}.
   * </p>
   * <p>
   * By contract, clients <strong>must</strong> return the borrowed instance
   * using {@link #restore restore}, {@link #invalidate invalidate}, or a
   * related method as defined in an implementation or sub-interface.
   * </p>
   * <p>
   * If the pool has been exhausted, this method will throw a
   * {@link NoSuchElementException}.
   * </p>
   *
   * @return an instance from this pool.
   * @throws IllegalStateException
   *           after {@link #close close} has been called on this pool.
   * @throws PoolException
   *           when {@link PoolableFactory#create create} throws an
   *           exception.
   * @throws NoSuchElementException
   *           when the pool is exhausted and cannot or will not return another
   *           instance.
   */
  public T borrow() throws PoolException, NoSuchElementException,
      IllegalStateException;

  /**
   * Return an instance to the pool.
   * <p>
   * By contract, <code>obj</code> <strong>must</strong> have been obtained
   * using {@link #borrow() borrow} or a related method as defined in an
   * implementation or sub-interface.
   * </p>
   *
   * @param obj
   *          a {@link #borrow borrowed} instance to be returned.
   * @throws PoolException
   */
  public void restore(T obj) throws PoolException;

  /**
   * Invalidates an object from the pool.
   * <p>
   * By contract, <code>obj</code> <strong>must</strong> have been obtained
   * using {@link #borrow borrow} or a related method as defined in an
   * implementation or sub-interface.
   * </p>
   * <p>
   * This method should be used when an object that has been borrowed is
   * determined (due to an exception or other problem) to be invalid.
   * </p>
   *
   * @param obj
   *          a {@link #borrow borrowed} instance to be disposed.
   * @throws PoolException
   *           when {@link PoolableFactory} fails.
   */
  public void invalidate(T obj) throws PoolException;

  /**
   * Create an object using the {@link PoolableFactory factory} or other
   * implementation dependent mechanism, passivate it, and then place it in the
   * idle object pool.
   * <p>
   * This method is useful for "pre-loading" a pool with idle objects. (Optional
   * operation).
   * </p>
   *
   * @throws PoolException
   *           when {@link PoolableFactory#create} fails.
   * @throws IllegalStateException
   *           after {@link #close} has been called on this pool.
   * @throws UnsupportedOperationException
   *           when this pool cannot add new idle objects.
   */
  public void add() throws PoolException, IllegalStateException,
      UnsupportedOperationException;

  /**
   * Gets the number of instances currently idle in this pool.
   * <p>
   * This may be considered an approximation of the number of objects that can
   * be {@link #borrow borrowed} without creating any new instances.
   * </p>
   *
   * @return the number of instances currently idle in this pool.
   * @throws UnsupportedOperationException
   *           <strong>deprecated</strong>: if this implementation does not
   *           support the operation
   */
  public int getIdleCount() throws UnsupportedOperationException;

  /**
   * Return the number of instances currently borrowed from this pool.
   *
   * @return the number of instances currently borrowed from this pool.
   * @throws UnsupportedOperationException
   *           <strong>deprecated</strong>: if this implementation does not
   *           support the operation.
   */
  public int getActiveCount() throws UnsupportedOperationException;

  /**
   * Clears any objects sitting idle in the pool, releasing any associated
   * resources (optional operation).
   * <p>
   * Idle objects cleared must be {@link PoolableFactory#destroy destroyed}.
   * </p>
   *
   * @throws PoolException
   *           when {@link PoolableFactory#destroy} fails.
   * @throws UnsupportedOperationException
   *           if this implementation does not support the operation
   */
  public void clear() throws PoolException, UnsupportedOperationException;

  /**
   * Close this pool, and free any resources associated with it.
   * <p>
   * Calling {@link #add} or {@link #borrow} after invoking this method on
   * a pool will cause them to throw an {@link IllegalStateException}.
   * </p>
   * <p>
   * Implementations should silently fail if not all resources can be freed.
   * </p>
   */
  public void close();
}
