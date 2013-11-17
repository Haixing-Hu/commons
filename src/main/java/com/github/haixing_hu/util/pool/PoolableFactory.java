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
 * An interface defining life-cycle methods for instances to be served by an
 * {@link Pool}.
 * <p>
 * By contract, when an {@link Pool} delegates to a {@link PoolableFactory},
 * <ol>
 * <li>{@link #create} is called whenever a new instance is needed.</li>
 * <li>{@link #activate} is invoked on every instance that has been
 * {@link #passivate passivated} before it is {@link Pool#borrow borrowed} from
 * the pool.</li>
 * <li>{@link #validate} is invoked on {@link #activate activated} instances to
 * make sure they can be {@link Pool#borrow borrowed} from the pool.
 * <code>validate</code> <strong>may</strong> also be used to test an instance
 * being {@link Pool#restore restored} to the pool before it is
 * {@link #passivate passivated}. It will only be invoked on an activated
 * instance.</li>
 * <li>{@link #passivate} is invoked on every instance when it is returned to
 * the pool.</li>
 * <li>{@link #destroy} is invoked on every instance when it is being "dropped"
 * from the pool (whether due to the response from <code>validate</code>, or for
 * reasons specific to the pool implementation.) There is no guarantee that the
 * instance being destroyed will be considered active, passive or in a generally
 * consistent state.</li>
 * </ol>
 * </p>
 * <p>
 * {@link PoolableFactory} must be thread-safe. The only promise an {@link Pool}
 * makes is that the same instance of an object will not be passed to more than
 * one method of a <code>PoolableFactory</code> at a time.
 * </p>
 *
 * @author Haixing Hu
 */
public interface PoolableFactory<T> {

  /**
   * Creates an instance that can be served by the pool.
   * <p>
   * Instances returned from this method should be in the same state as if they
   * had been {@link #activate activated}. They will not be activated before
   * being served by the pool.
   * </p>
   *
   * @return an instance that can be served by the pool.
   * @throws PoolException
   *           if there is an error while creating a new instance, this will be
   *           propagated to the code requesting an object.
   */
  public T create() throws PoolException;

  /**
   * Ensures that the instance is safe to be returned by the pool.
   * <p>
   * Returns <code>false</code> if <code>obj</code> should be destroyed.
   * </p>
   * <p>
   * Note that the implementation of this function MUST NOT throw anything.
   * </p>
   *
   * @param obj
   *          the instance to be validated
   * @return <code>false</code> if <code>obj</code> is not valid and should be
   *         dropped from the pool and destroyed, <code>true</code> otherwise.
   */
  public boolean validate(T obj);

  /**
   * Reinitialize an instance to be returned by the pool.
   *
   * @param obj
   *          the instance to be activated
   * @throws PoolException
   *           if there is an error while activating <code>obj</code>, this
   *           exception may be swallowed by the pool.
   * @see #destroy
   */
  public void activate(T obj) throws PoolException;

  /**
   * Uninitialize an instance to be returned to the idle object pool.
   *
   * @param obj
   *          the instance to be passivated
   * @throws PoolException
   *           if there is an error while passivating <code>obj</code>, this
   *           exception may be swallowed by the pool.
   * @see #destroy
   */
  public void passivate(T obj) throws PoolException;

  /**
   * Destroys an instance no longer needed by the pool.
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
   * @param obj
   *          the instance to be destroyed.
   * @throws PoolException
   *           if there is an error while destroying <code>obj</code>, this
   *           exception may be swallowed by the pool.
   * @see #validate
   * @see Pool#invalidate
   */
  public void destroy(T obj);
}
