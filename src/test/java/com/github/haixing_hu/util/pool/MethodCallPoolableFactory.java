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

import java.util.ArrayList;
import java.util.List;

import com.github.haixing_hu.util.pool.PoolException;
import com.github.haixing_hu.util.pool.PoolableFactory;

/**
 * A poolable object factory that tracks how {@link MethodCall methods are
 * called}.
 *
 * @author Haixing Hu
 */
public class MethodCallPoolableFactory implements PoolableFactory<Integer> {

  private final List<MethodCall> methodCalls = new ArrayList<MethodCall>();
  private int count = 0;
  private boolean valid = true;
  private boolean createFail;
  private boolean activateFail;
  private boolean validateFail;
  private boolean passivateFail;

  public void reset() {
    count = 0;
    methodCalls.clear();
    createFail = false;
    activateFail = false;
    valid = true;
    validateFail = false;
    passivateFail = false;
  }

  public List<MethodCall> getMethodCalls() {
    return methodCalls;
  }

  public int getCurrentCount() {
    return count;
  }

  public void setCurrentCount(final int count) {
    this.count = count;
  }

  public boolean isCreateFail() {
    return createFail;
  }

  public void setCreateFail(final boolean createFail) {
    this.createFail = createFail;
  }

  public boolean isValid() {
    return valid;
  }

  public void setValid(final boolean valid) {
    this.valid = valid;
  }

  public boolean isValidateFail() {
    return validateFail;
  }

  public void setValidateFail(final boolean validateFail) {
    this.validateFail = validateFail;
  }

  public boolean isActivateFail() {
    return activateFail;
  }

  public void setActivateFail(final boolean activateFail) {
    this.activateFail = activateFail;
  }

  public boolean isPassivateFail() {
    return passivateFail;
  }

  public void setPassivateFail(final boolean passivateFail) {
    this.passivateFail = passivateFail;
  }

  @Override
  public Integer create() throws PoolException {
    final MethodCall call = new MethodCall("makeObject");
    methodCalls.add(call);
    final int count = this.count++;
    if (createFail) {
      throw new PrivateException("makeObject");
    }
    final Integer obj = new Integer(count);
    call.setReturned(obj);
    return obj;
  }

  @Override
  public void activate(final Integer obj) throws PoolException {
    methodCalls.add(new MethodCall("activateObject", obj));
    if (activateFail) {
      throw new PrivateException("activateObject");
    }
  }

  @Override
  public boolean validate(final Integer obj) {
    final MethodCall call = new MethodCall("validateObject", obj);
    methodCalls.add(call);
    if (validateFail) {
      throw new PrivateException("validateObject");
    }
    final boolean r = valid;
    call.returned(new Boolean(r));
    return r;
  }

  @Override
  public void passivate(final Integer obj) throws PoolException {
    methodCalls.add(new MethodCall("passivateObject", obj));
    if (passivateFail) {
      throw new PrivateException("passivateObject");
    }
  }

  @Override
  public void destroy(final Integer obj) throws PoolException {
    methodCalls.add(new MethodCall("destroyObject", obj));
  }
}
