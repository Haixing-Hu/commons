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

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

import static com.github.haixing_hu.lang.Argument.*;

/**
 * An implementation of tree using an abstract map to store the children.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public abstract class AbstractMapTree<KEY, VALUE> extends AbstractTree<KEY, VALUE> {

  protected Map<KEY, Tree<KEY, VALUE>> children;

  protected abstract Map<KEY, Tree<KEY, VALUE>> makeTreeMap();

  protected AbstractMapTree() {
    super();
    this.children = null;
  }

  protected AbstractMapTree(final KEY key, final VALUE value) {
    super(key, value);
    this.children = null;
  }

  @Override
  public boolean isLeaf() {
    return ((children == null) || children.isEmpty());
  }

  @Override
  public int getChildrenCount() {
    return (children == null ? 0 : children.size());
  }

  @Override
  public Collection<Tree<KEY, VALUE>> getChildren() {
    if (children == null) {
      return Collections.emptyList();
    } else {
      return Collections.unmodifiableCollection(children.values());
    }
  }

  @Override
  public Tree<KEY, VALUE> getChild(final KEY key) {
    if (children == null) {
      return null;
    } else {
      return children.get(key);
    }
  }

  @Override
  public Tree<KEY, VALUE> addChild(final Tree<KEY, VALUE> child) {
    requireNonNull("child", child);
    requireNonSame("this", this, "child", child);
    if (children == null) {
      children = makeTreeMap();
    }
    return children.put(child.getKey(), child);
  }

  @Override
  public Tree<KEY, VALUE> removeChild(final KEY key) {
    if (children == null) {
      return null;
    }
    return children.remove(key);
  }

  @Override
  public int clearChildren() {
    if (children == null) {
      return 0;
    } else {
      final int result = children.size();
      children.clear();
      return result;
    }
  }
}
