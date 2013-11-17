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

import com.github.haixing_hu.collection.ArrayLinkedList;

/**
 * A "last in first out" fixed-capacity keyed queue for storing idle objects.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public class LifoKeyedIdleQueue<K, V> extends AbstractKeyedIdleQueue<K, V> {

  public LifoKeyedIdleQueue() {
    super();
  }

  public LifoKeyedIdleQueue(final int capacity) {
    super(capacity);
  }

  @Override
  protected V getFromQueue(final ArrayLinkedList<V> queue) {
    return queue.pollLast();
  }

  @Override
  protected void putToQueue(final ArrayLinkedList<V> queue, final V value) {
    queue.addLast(value);
  }

}
