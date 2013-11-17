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

import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.haixing_hu.util.pool.PoolableFactory;
import com.github.haixing_hu.util.pool.ValidationFailedException;

import static com.github.haixing_hu.lang.Argument.requireNonNull;
import static com.github.haixing_hu.util.pool.impl.ValidateAction.NOT_VALIDATE;

/**
 * A basic implementation of the {@link ValidatePolicy}.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public class BasicValidatePolicy<T> implements ValidatePolicy<T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(BasicValidatePolicy.class);

  /**
   * The default validate action to be taken on borrowing objects.
   */
  public static final ValidateAction DEFAULT_ON_BORROW_ACTION = ValidateAction.NOT_VALIDATE;

  /**
   * The default validate action to be taken on restoring objects.
   */
  public static final ValidateAction DEFAULT_ON_RESTORE_ACTION = ValidateAction.NOT_VALIDATE;

  /**
   * The default validate action to be taken on checking idle objects.
   */
  public static final ValidateAction DEFAULT_ON_CHECK_IDLE_ACTION = ValidateAction.NOT_VALIDATE;

  private volatile ValidateAction onBorrowAction;
  private volatile ValidateAction onRestoreAction;
  private volatile ValidateAction onCheckIdleAction;

  public BasicValidatePolicy() {
    this.onBorrowAction = DEFAULT_ON_BORROW_ACTION;
    this.onRestoreAction = DEFAULT_ON_RESTORE_ACTION;
    this.onCheckIdleAction = DEFAULT_ON_CHECK_IDLE_ACTION;
  }

  public BasicValidatePolicy(final ValidateAction onBorrowAction,
      final ValidateAction onRestoreAction,
      final ValidateAction onCheckIdleAction) {
    this.onBorrowAction = requireNonNull("onBorrowAction", onBorrowAction);
    this.onRestoreAction = requireNonNull("onRestoreAction", onRestoreAction);
    this.onCheckIdleAction = requireNonNull("onCheckIdleAction",
        onCheckIdleAction);
  }

  @Override
  public ValidateAction getOnBorrowAction() {
    return onBorrowAction;
  }

  @Override
  public void setOnBorrowAction(final ValidateAction action) {
    this.onBorrowAction = requireNonNull("action", action);
  }

  @Override
  public ValidateAction getOnRestoreAction() {
    return onRestoreAction;
  }

  @Override
  public void setOnRestoreAction(final ValidateAction action) {
    this.onRestoreAction = requireNonNull("action", action);
  }

  @Override
  public ValidateAction getOnCheckIdleAction() {
    return onCheckIdleAction;
  }

  @Override
  public void setOnCheckIdleAction(final ValidateAction action) {
    this.onCheckIdleAction = requireNonNull("action", action);
  }

  @Override
  public boolean onBorrow(final PoolableFactory<T> factory, final T obj)
      throws ValidationFailedException {
    return doValidate(onBorrowAction, factory, obj);
  }

  @Override
  public boolean onRestore(final PoolableFactory<T> factory, final T obj)
      throws ValidationFailedException {
    return doValidate(onRestoreAction, factory, obj);
  }

  @Override
  public boolean onCheckIdle(final PoolableFactory<T> factory, final T obj)
      throws ValidationFailedException {
    return doValidate(onCheckIdleAction, factory, obj);
  }

  protected boolean doValidate(final ValidateAction action,
      final PoolableFactory<T> factory, final T obj)
      throws ValidationFailedException {
    if (action == NOT_VALIDATE) {
      return true;
    }
    if (factory.validate(obj)) {
      return true;
    }
    // the obj did not pass the validation
    switch (action) {
      case THROWN_ON_FAILED: {
        final ValidationFailedException e = new ValidationFailedException(obj);
        factory.destroy(obj);
        throw e;
      }
      case LOG_ON_FAILED: {
        LOGGER.error("Validation of object failed: {}", obj);
        factory.destroy(obj);
        return false;
      }
      case SILENT_ON_FAILED:
      default: {
        factory.destroy(obj);
        return false;
      }
    }
  }

}
