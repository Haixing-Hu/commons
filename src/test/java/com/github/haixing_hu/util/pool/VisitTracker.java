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

import com.github.haixing_hu.util.pool.PoolException;

/**
 * Test pooled object class. Keeps track of how many times it has been
 * validated, activated, passivated.
 *
 * @author Haixing Hu
 */
public class VisitTracker {
  private int validateCount = 0;
  private int activateCount = 0;
  private int passivateCount = 0;
  private boolean destroyed = false;
  private int id = 0;
  private String key = null;

  public VisitTracker() {
    reset();
  }

  public VisitTracker(final int id) {
    this.id = id;
    reset();
  }

  public VisitTracker(final int id, final String key) {
    super();
    this.id = id;
    this.key = key;
    reset();
  }

  public boolean validate() {
    if (destroyed) {
      fail("attempted to validate a destroyed object");
    }
    validateCount++;
    return true;
  }

  public void activate() {
    if (destroyed) {
      fail("attempted to activate a destroyed object");
    }
    activateCount++;
  }

  public void passivate() {
    if (destroyed) {
      fail("attempted to passivate a destroyed object");
    }
    passivateCount++;
  }

  public void reset() {
    validateCount = 0;
    activateCount = 0;
    passivateCount = 0;
    destroyed = false;
  }

  public void destroy() {
    destroyed = true;
  }

  public int getValidateCount() {
    return validateCount;
  }

  public int getActivateCount() {
    return activateCount;
  }

  public int getPassivateCount() {
    return passivateCount;
  }

  public boolean isDestroyed() {
    return destroyed;
  }

  public int getId() {
    return id;
  }

  public String getKey() {
    return key;
  }

  @Override
  public String toString() {
    return "Key: " + key + " id: " + id;
  }

  private void fail(final String message) {
    throw new PoolException(message);
  }
}
