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
 * The {@link ExpansionPolicy} which just fit the new length.
 *
 * @author Haixing Hu
 */
@Immutable
public class JustFitExpansionPolicy extends ExpansionPolicy {

  public static final JustFitExpansionPolicy INSTANCE = new JustFitExpansionPolicy();

  @Override
  public int getNextCapacity(final int oldCapacity, final int newLength) {
    return newLength;
  }

  @Override
  public boolean needShrink(final int length, final int capacity) {
    return length < capacity;
  }

  @Override
  public int getShrinkCapacity(final int length, final int capacity) {
    return length;
  }

}
