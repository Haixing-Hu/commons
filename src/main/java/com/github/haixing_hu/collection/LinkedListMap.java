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
package com.github.haixing_hu.collection;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * An implementation of map using an linked list to store the keys and values.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class LinkedListMap<KEY, VALUE> extends AbstractListMap<KEY, VALUE> {

  @Override
  protected List<AbstractListMap.Entry<KEY, VALUE>> makeList() {
    return new LinkedList<AbstractListMap.Entry<KEY, VALUE>>();
  }

  public LinkedListMap() {
    super();
  }

  public LinkedListMap(Map<? extends KEY, ? extends VALUE> map) {
    super();
    this.putAll(map);
  }
}
