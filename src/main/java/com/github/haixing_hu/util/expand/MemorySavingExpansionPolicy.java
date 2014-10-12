/*
 * Copyright (c) 2014  Haixing Hu
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
 */
package com.github.haixing_hu.util.expand;

import javax.annotation.concurrent.Immutable;

/**
 * The {@link ExpansionPolicy} which use less memory.
 *
 * The growth pattern is:  0, 4, 8, 16, 25, 35, 46, 58, 72, 88, ...
 *
 * @author Haixing Hu
 */
@Immutable
public class MemorySavingExpansionPolicy extends ExpansionPolicy {

  public static final MemorySavingExpansionPolicy INSTANCE = new MemorySavingExpansionPolicy();

  @Override
  public int getNextCapacity(final int oldCapacity, final int newLength) {
    // The growth pattern is:  0, 4, 8, 16, 25, 35, 46, 58, 72, 88, ...
    final int newCapacity = (newLength >> 3) + (newLength < 9 ? 3 : 6) + newLength;
    return newCapacity;
  }

  @Override
  public boolean needShrink(final int length, final int capacity) {
    return length < capacity / 3;
  }

  @Override
  public int getShrinkCapacity(final int length, final int capacity) {
    final int cap1 = getNextCapacity(capacity, length);
    final int cap2 = getNextCapacity(capacity, capacity / 2);
    return Math.max(cap1, cap2);
  }

}
