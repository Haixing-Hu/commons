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

package com.github.haixing_hu.util.expand;

import javax.annotation.concurrent.Immutable;

/**
 * The {@link ExpansionPolicy} which doubles the capacity to hold the new length.
 *
 * @author Haixing Hu
 */
@Immutable
public class DoubleExpansionPolicy extends ExpansionPolicy {

  public static final DoubleExpansionPolicy INSTANCE = new DoubleExpansionPolicy();

  @Override
  public int getNextCapacity(final int oldCapacity, final int newLength) {
    int newCapacity = (oldCapacity <= 0 ? 1 : oldCapacity);
    while (newCapacity < newLength) {
      newCapacity *= 2;
    }
    return newCapacity;
  }

  @Override
  public boolean needShrink(final int length, final int capacity) {
    return length < capacity / 3;
  }

  @Override
  public int getShrinkCapacity(final int length, final int capacity) {
    return Math.max(length, capacity / 2);
  }

}
