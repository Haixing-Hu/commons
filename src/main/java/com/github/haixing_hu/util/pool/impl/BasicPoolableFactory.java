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

import com.github.haixing_hu.util.pool.AbstractPoolableFactory;
import com.github.haixing_hu.util.pool.PoolException;
import com.github.haixing_hu.util.pool.PoolableFactory;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * The basic implementation of the {@link PoolableFactory}.
 *
 * @author Haixing Hu
 */
public class BasicPoolableFactory<T> extends AbstractPoolableFactory<T> {

  private final Class<T> objectClass;

  /**
   * Constructs a {@link BasicPoolableFactory} with the class object of the
   * poolable objects.
   *
   * @param objectClass
   *          the class object of the poolable objects.
   */
  public BasicPoolableFactory(final Class<T> objectClass) {
    this.objectClass = requireNonNull("objectClass", objectClass);
  }

  /**
   * Gets the class object of the poolable objects.
   *
   * @return the class object of the poolable objects.
   */
  public Class<T> getObjectClass() {
    return objectClass;
  }

  @Override
  public T create() throws PoolException {
    try {
      return objectClass.newInstance();
    } catch (final Exception e) {
      throw new PoolException(e);
    }
  }

}
