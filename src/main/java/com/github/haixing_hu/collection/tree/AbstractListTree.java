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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.concurrent.NotThreadSafe;

import static com.github.haixing_hu.lang.Argument.*;

/**
 * An implementation of tree using an abstract list to store the children.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public abstract class AbstractListTree<KEY, VALUE> extends AbstractTree<KEY, VALUE> {

  protected List<Tree<KEY, VALUE>> children;

  protected abstract List<Tree<KEY, VALUE>> makeTreeList();

  protected AbstractListTree() {
    super();
    this.children = null;
  }

  protected AbstractListTree(final KEY key, final VALUE value) {
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
      return Collections.unmodifiableCollection(children);
    }
  }

  @Override
  public Tree<KEY, VALUE> getChild(final KEY key) {
    if (children == null) {
      return null;
    }
    if (key == null) {
      for (final Tree<KEY, VALUE> child : children) {
        if (child.getKey() == null) {
          return child;
        }
      }
    } else {
      for (final Tree<KEY, VALUE> child : children) {
        if (key.equals(child.getKey())) {
          return child;
        }
      }
    }
    return null;
  }

  @Override
  public Tree<KEY, VALUE> addChild(final Tree<KEY, VALUE> child) {
    requireNonNull("child", child);
    requireNonSame("this", this, "child", child);
    final KEY key = child.getKey();
    if (children == null) {
      children = makeTreeList();
      children.add(child);
    } else if (key == null) {
      for (final ListIterator<Tree<KEY, VALUE>> it = children.listIterator(); it.hasNext(); ) {
        final Tree<KEY, VALUE> thisChild = it.next();
        if (thisChild.getKey() == null) {
          it.set(child);
          return thisChild;
        }
      }
    } else {  // key != null
      for (final ListIterator<Tree<KEY, VALUE>> it = children.listIterator(); it.hasNext(); ) {
        final Tree<KEY, VALUE> thisChild = it.next();
        if (key.equals(thisChild.getKey())) {
          it.set(child);
          return thisChild;
        }
      }
    }
    return null;
  }

  @Override
  public Tree<KEY, VALUE> removeChild(final KEY key) {
    if (children == null) {
      return null;
    }
    if (key == null) {
      for (final Iterator<Tree<KEY, VALUE>> it = children.iterator(); it.hasNext();) {
        final Tree<KEY, VALUE> child = it.next();
        if (child.getKey() == null) {
          it.remove();
          return child;
        }
      }
    } else {
      for (final Iterator<Tree<KEY, VALUE>> it = children.iterator(); it.hasNext();) {
        final Tree<KEY, VALUE> child = it.next();
        if (key.equals(child.getKey())) {
          it.remove();
          return child;
        }
      }
    }
    return null;
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
