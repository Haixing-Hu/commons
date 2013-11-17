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

package com.github.haixing_hu.text.xml;

import java.util.Stack;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * A utility class that iterates through the element nodes of a DOM tree.
 *
 * It differs from the {@link DomNodeIterator}, since it only iterates throw element nodes.
 *
 * Note that this class is not thread safe.
 *
 * @author Haixing Hu
 * @see {@link DomNodeIterator}
 */
public final class DomElementIterator {

  private Element           current;
  private NodeList          children;
  private final Stack<Node> nodes;

  public DomElementIterator(final Node root) {
    nodes = new Stack<Node>();
    nodes.add(requireNonNull("root", root));
    current = null;
    children = null;
  }

  /**
   * Returns true if there are more nodes on the current stack.
   *
   * @return true if there are more nodes on the current stack.
   */
  public boolean hasNext() {
    return (nodes.size() > 0);
  }

  /**
   * Returns the next {@link Node} on the stack and pushes all of its children
   * onto the stack, allowing us to walk the node tree without the use of
   * recursion. If there are no more nodes on the stack then null is returned.
   *
   * @return the next {@link Node} on the stack or null if there isn't a next
   *         node.
   */
  public Element next() {
    Element next = null;
    while (! nodes.isEmpty()) {
      final Node node = nodes.pop();
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        next = (Element) node;
        break;
      }
    }
    if (next == null) {
      return null;
    }
    current = next;
    children = current.getChildNodes();
    if (children != null) {
      // put the children node on the stack in first to last order
      for (int i = children.getLength() - 1; i >= 0; --i) {
        nodes.add(children.item(i));
      }
    }
    return current;
  }

  /**
   * Skips over and removes from the node stack the children of the last node.
   * When getting a next node from the walker, that node's children are
   * automatically added to the stack. You can call this method to remove those
   * children from the stack.
   *
   * This is useful when you don't want to process deeper into the current path
   * of the node tree but you want to continue processing sibling nodes.
   */
  public void skipChildren() {
    if (children != null) {
      final int n = children.getLength();
      for (int i = 0; i < n; ++i) {
        final Node child = nodes.peek();
        if (child.equals(children.item(i))) {
          nodes.pop();
        }
      }
    }
  }
}
