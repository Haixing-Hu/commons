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

/**
 * An interface defining life-cycle methods for instances to be served by a
 * {@link KeyedPool}.
 * <p>
 * By contract, when an {@link KeyedPool} delegates to a
 * {@link KeyedPoolableFactory},
 * <ol>
 * <li>
 *   {@link #create} is called whenever a new instance is needed.</li>
 * <li>
 *   {@link #activate} is invoked on every instance that has been
 * {@link #passivate passivated} before it is {@link KeyedPool#borrow borrowed}
 * from the pool.</li>
 * <li>
 *   {@link #validate} is invoked on {@link #activate activated} instances to
 * make sure they can be {@link KeyedPool#borrow borrowed} from the pool.
 * <code>validate</code> <strong>may</strong> also be used to test an instance
 * being {@link KeyedPool#restore restored} to the pool before it is
 * {@link #passivate passivated}. It will only be invoked on an activated
 * instance.</li>
 * <li>
 *   {@link #passivate} is invoked on every instance when it is returned to
 * the pool.</li>
 * <li>
 *   {@link #destroy} is invoked on every instance when it is being "dropped"
 * from the pool (whether due to the response from <code>validate</code>, or for
 * reasons specific to the pool implementation.) There is no guarantee that the
 * instance being destroyed will be considered active, passive or in a generally
 * consistent state.</li>
 * </ol>
 * </p>
 * <p>
 * {@link KeyedPoolableFactory} must be thread-safe. The only promise an
 * {@link KeyedPool} makes is that the same instance of an object will not be
 * passed to more than one method of a <code>KeyedPoolableFactory</code> at a
 * time.
 * </p>
 *
 * @see KeyedPool
 * @author Haixing Hu
 */
public interface KeyedPoolableFactory<K, V> {
  /**
   * Create an instance that can be served by the pool.
   *
   * @param key
   *          the key used when constructing the object
   * @return an instance that can be served by the pool.
   * @throws Exception
   *           if there is a problem creating a new instance, this will be
   *           propagated to the code requesting an object.
   */
  public V create(K key) throws PoolException;

  /**
   * Destroy an instance no longer needed by the pool.
   * <p>
   * It is important for implementations of this method to be aware that there
   * is no guarantee about what state <code>obj</code> will be in and the
   * implementation should be prepared to handle unexpected errors.
   * </p>
   * <p>
   * Also, an implementation must take in to consideration that instances lost
   * to the garbage collector may never be destroyed.
   * </p>
   * <p>
   * Note that the implementation of this function MUST NOT throw anything.
   * </p>
   *
   * @param key
   *          the key used when selecting the instance
   * @param obj
   *          the instance to be destroyed.
   * @see #validate
   * @see KeyedPool#invalidate
   */
  public void destroy(K key, V obj);

  /**
   * Ensures that the instance is safe to be returned by the pool. Returns
   * <code>false</code> if <code>obj</code> should be destroyed.
   *
   * @param key
   *          the key used when selecting the object
   * @param obj
   *          the instance to be validated
   * @return <code>false</code> if <code>obj</code> is not valid and should be
   *         dropped from the pool, <code>true</code> otherwise.
   */
  public boolean validate(K key, V obj);

  /**
   * Reinitialize an instance to be returned by the pool.
   *
   * @param key
   *          the key used when selecting the object
   * @param obj
   *          the instance to be activated
   * @throws Exception
   *           if there is a problem activating <code>obj</code>, this exception
   *           may be swallowed by the pool.
   * @see #destroy
   */
  public void activate(K key, V obj) throws PoolException;

  /**
   * Uninitialize an instance to be returned to the idle object pool.
   *
   * @param key
   *          the key used when selecting the object
   * @param obj
   *          the instance to be passivated
   * @throws Exception
   *           if there is a problem passivating <code>obj</code>, this
   *           exception may be swallowed by the pool.
   * @see #destroy
   */
  public void passivate(K key, V obj) throws PoolException;
}
