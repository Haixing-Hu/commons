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

package com.github.haixing_hu.collection.tree;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * An implementation of tree using an hash map to store the children.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class HashMapTree<KEY, VALUE> extends AbstractMapTree<KEY, VALUE> {

  @Override
  protected Map<KEY, Tree<KEY, VALUE>> makeTreeMap() {
    return new HashMap<KEY, Tree<KEY, VALUE>>();
  }

  public HashMapTree(KEY key, VALUE value) {
    super(key, value);
  }
}
