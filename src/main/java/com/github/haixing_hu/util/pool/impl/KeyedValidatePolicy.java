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

package com.github.haixing_hu.util.pool.impl;

import com.github.haixing_hu.util.pool.KeyedPoolableFactory;
import com.github.haixing_hu.util.pool.ValidationFailedException;

/**
 * The policy to validate keyed poolable objects.
 * <p>
 * The implementation MUST be thread-safe.
 * </p>
 *
 * @author Haixing Hu
 */
public interface KeyedValidatePolicy<K, V> {

  /**
   * Gets the action to take on borrowing objects.
   *
   * @return the action to take on borrowing objects.
   */
  public ValidateAction getOnBorrowAction();

  /**
   * Sets the action to take on borrowing objects.
   *
   * @param action
   *          the action to take on borrowing objects.
   */
  public void setOnBorrowAction(ValidateAction action);

  /**
   * Gets the action to take on restoring objects.
   *
   * @return the action to take on restoring objects.
   */
  public ValidateAction getOnRestoreAction();

  /**
   * Sets the action to take on restoring objects.
   *
   * @param action
   *          the action to take on restoring objects.
   */
  public void setOnRestoreAction(ValidateAction action);

  /**
   * Gets the action to take on checking idle objects.
   *
   * @return the action to take on checking idle objects.
   */
  public ValidateAction getOnCheckIdleAction();

  /**
   * Sets the action to take on checking idle objects.
   *
   * @param action
   *          the action to take on checking idle objects.
   */
  public void setOnCheckIdleAction(ValidateAction action);

  /**
   * The event fired on borrowing an object.
   *
   * @param factory
   *          the {@link KeyedPoolableFactory} of the objects.
   * @param key
   *          the key of the object to be borrowed.
   * @param value
   *          the object to be borrowed, which can't be null. Note that the
   *          object must be activated before calling this function.
   * @return if this policy does not need to validate the object on borrowing,
   *         just returns <code>true</code>; otherwise, the function will
   *         validate the <code>obj</code>, and if it pass the validation,
   *         returns <code>true</code>; otherwise, the <code>obj</code> was
   *         destroyed, and depending on the implementation, either returns
   *         <code>false</code> or throws {@link ValidationFailedException}.
   * @throws ValidationFailedException
   *           if this policy perform the validation and the object does not
   *           pass the validation, and this policy choose to thrown an
   *           exception on this situation.
   */
  public boolean onBorrow(KeyedPoolableFactory<K, V> factory, K key, V value)
      throws ValidationFailedException;

  /**
   * The event fired on restoring an object.
   *
   * @param factory
   *          the {@link KeyedPoolableFactory} of the objects.
   * @param key
   *          the key of the object to be restored.
   * @param value
   *          the object to be restored, which can't be null. Note that the
   *          object must be activated before calling this function.
   * @return if this policy does not need to validate the object on restoring,
   *         just returns <code>true</code>; otherwise, the function will
   *         validate the <code>obj</code>, and if it pass the validation,
   *         returns <code>true</code>; otherwise, the <code>obj</code> was
   *         destroyed, and depending on the implementation, either returns
   *         <code>false</code> or throws {@link ValidationFailedException}.
   * @throws ValidationFailedException
   *           if this policy perform the validation and the object does not
   *           pass the validation, and this policy choose to thrown an
   *           exception on this situation.
   */
  public boolean onRestore(KeyedPoolableFactory<K, V> factory, K key, V value)
      throws ValidationFailedException;

  /**
   * The event fired on checking the idle objects.
   *
   * @param factory
   *          the {@link KeyedPoolableFactory} of the objects.
   * @param key
   *          the key of the object to be validate.
   * @param value
   *          the object to be validate, which can't be null. Note that the
   *          object must be passivated before calling this function.
   * @return if this policy does not need to validate the objects in the idle
   *         queue, just returns <code>true</code>; otherwise, the function will
   *         validate the <code>obj</code>, and if it pass the validation,
   *         returns <code>true</code>; otherwise, the <code>obj</code> was
   *         destroyed, and depending on the implementation, either returns
   *         <code>false</code> or throws {@link ValidationFailedException}.
   * @throws ValidationFailedException
   *           if this policy perform the validation and the object does not
   *           pass the validation, and this policy choose to thrown an
   *           exception on this situation.
   */
  public boolean onCheckIdle(KeyedPoolableFactory<K, V> factory, K key, V value)
      throws ValidationFailedException;
}
