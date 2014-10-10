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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.github.haixing_hu.collection.primitive.BooleanCollection;
import com.github.haixing_hu.collection.primitive.BooleanIterator;
import com.github.haixing_hu.collection.primitive.BooleanList;
import com.github.haixing_hu.collection.primitive.ByteCollection;
import com.github.haixing_hu.collection.primitive.ByteIterator;
import com.github.haixing_hu.collection.primitive.ByteList;
import com.github.haixing_hu.collection.primitive.CharCollection;
import com.github.haixing_hu.collection.primitive.CharIterator;
import com.github.haixing_hu.collection.primitive.CharList;
import com.github.haixing_hu.collection.primitive.DoubleCollection;
import com.github.haixing_hu.collection.primitive.DoubleIterator;
import com.github.haixing_hu.collection.primitive.DoubleList;
import com.github.haixing_hu.collection.primitive.FloatCollection;
import com.github.haixing_hu.collection.primitive.FloatIterator;
import com.github.haixing_hu.collection.primitive.FloatList;
import com.github.haixing_hu.collection.primitive.IntCollection;
import com.github.haixing_hu.collection.primitive.IntIterator;
import com.github.haixing_hu.collection.primitive.IntList;
import com.github.haixing_hu.collection.primitive.LongCollection;
import com.github.haixing_hu.collection.primitive.LongIterator;
import com.github.haixing_hu.collection.primitive.LongList;
import com.github.haixing_hu.collection.primitive.ShortCollection;
import com.github.haixing_hu.collection.primitive.ShortIterator;
import com.github.haixing_hu.collection.primitive.ShortList;
import com.github.haixing_hu.collection.primitive.impl.ArrayBooleanList;
import com.github.haixing_hu.collection.primitive.impl.ArrayByteList;
import com.github.haixing_hu.collection.primitive.impl.ArrayCharList;
import com.github.haixing_hu.collection.primitive.impl.ArrayDoubleList;
import com.github.haixing_hu.collection.primitive.impl.ArrayFloatList;
import com.github.haixing_hu.collection.primitive.impl.ArrayIntList;
import com.github.haixing_hu.collection.primitive.impl.ArrayLongList;
import com.github.haixing_hu.collection.primitive.impl.ArrayShortList;
import com.github.haixing_hu.io.serialize.NoXmlSerializerRegisteredException;
import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.io.serialize.XmlSerializer;
import com.github.haixing_hu.lang.BigDecimalUtils;
import com.github.haixing_hu.lang.BigIntegerUtils;
import com.github.haixing_hu.lang.BooleanUtils;
import com.github.haixing_hu.lang.ByteArrayUtils;
import com.github.haixing_hu.lang.ByteUtils;
import com.github.haixing_hu.lang.CharUtils;
import com.github.haixing_hu.lang.ClassUtils;
import com.github.haixing_hu.lang.Comparison;
import com.github.haixing_hu.lang.DoubleUtils;
import com.github.haixing_hu.lang.EnumUtils;
import com.github.haixing_hu.lang.Equality;
import com.github.haixing_hu.lang.FloatUtils;
import com.github.haixing_hu.lang.IntUtils;
import com.github.haixing_hu.lang.LongUtils;
import com.github.haixing_hu.lang.ShortUtils;
import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.text.BooleanFormat;
import com.github.haixing_hu.text.DateFormat;
import com.github.haixing_hu.text.NumberFormat;
import com.github.haixing_hu.util.codec.string.HexCodec;

/**
 * Provides utility functions for dealing with DOM trees.
 *
 * @author Haixing Hu
 */
public final class DomUtils {

  public static final String PRESERVE_SPACE_ATTRIBUTE = "preserve-space";

  public static final boolean DEFAULT_PRESERVE_SPACE = false;

  public static final boolean DEFAULT_TRIM = true;

  public static final String DEFAULT_SEPARATOR_ATTRIBUTE = "separator";

  public static final char DEFAULT_SEPARATOR = ',';

  public static final String DEFAULT_CLASS_ATTRIBUTE = "class";

  public static final String DEFAULT_CONFIG_ATTRIBUTE = "config";

  public static void checkNode(final Element node, final String expectedName)
      throws InvalidXmlNodeException {
    if (node == null) {
      throw new InvalidXmlNodeException(expectedName);
    }
    final String actualName = node.getTagName();
    if (! expectedName.equals(actualName)) {
      throw new InvalidXmlNodeException(expectedName, actualName);
    }
  }

  public static void checkClassAttribute(final Element node,
      final Class<?> clazz, @Nullable String classAttribute)
          throws XmlException {
    if (classAttribute == null) {
      classAttribute = DEFAULT_CLASS_ATTRIBUTE;
    }
    if (! node.hasAttribute(classAttribute)) {
      throw new EmptyXmlAttributeException(node.getTagName(), classAttribute);
    }
    final String className = node.getAttribute(classAttribute);
    if (! className.equals(clazz.getName())) {
      throw new InvalidXmlAttributeException(node.getTagName(), classAttribute,
          className);
    }
  }

  /**
   * Gets the first child element node with the specified tag name of a
   * specified parent node.
   * <p>
   * Note that this function does NOT check the number of children nodes with
   * the specified child name, instead, it just find the first child node with
   * the specified child name and return it.
   *
   * @param parent
   *          the parent node.
   * @param childName
   *          the specified tag name.
   * @return the first child element node with the specified tag name of a
   *         specified parent node; returns null if the parent has no child
   *         element node with the specified tag name.
   * @see getOptionalChild
   * @see getRequiredChild
   */
  public static Element getFirstChild(final Element parent,
      final String childName) {
    final NodeList childrenList = parent.getChildNodes();
    final int childrenCount = childrenList.getLength();
    for (int i = 0; i < childrenCount; ++i) {
      final Node child = childrenList.item(i);
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        final Element element = (Element) child;
        if (childName.equals(element.getTagName())) {
          return element;
        }
      }
    }
    return null;
  }

  /**
   * Gets the optional child element node with the specified tag name of a
   * specified parent node.
   * <p>
   * Note that this function DOES check the number of children nodes with the
   * specified child name. If the node has only one child node with the
   * specified name, the child node is returned; if the node has no child node
   * with the specified name, {@code null} is returned; otherwise, the node
   * has more than one child node with the specified name, an XmlException is
   * thrown.
   *
   * @param parent
   *          the parent node.
   * @param childName
   *          the specified tag name.
   * @return the optional child element node with the specified tag name of a
   *         specified parent node; returns null if the parent has no child
   *         element node with the specified tag name (which is not an error).
   * @throws InvalidXmlChildrenCountException
   *           if the parent has more than one children element nodes with the
   *           specified tag name.
   * @see getFirstChild
   * @see getRequiredChild
   */
  public static Element getOptChild(final Element parent, final String childName)
      throws XmlException {
    final NodeList childrenList = parent.getChildNodes();
    final int childrenCount = childrenList.getLength();
    if (childrenCount == 0) {
      return null;
    }
    int count = 0;
    Element result = null;
    for (int i = 0; i < childrenCount; ++i) {
      final Node child = childrenList.item(i);
      if (child.getNodeType() != Node.ELEMENT_NODE) {
        continue;
      }
      final Element element = (Element) child;
      if (childName.equals(element.getTagName())) {
        ++count;
        result = element;
      }
    }
    if (count > 1) {
      throw new InvalidXmlChildrenCountException(parent.getTagName(),
          childName, 0, 1, count);
    }
    return result;
  }

  /**
   * Gets the required child element node with the specified tag name of a
   * specified parent node.
   * <p>
   * Note that this function DOES check the number of children nodes with the
   * specified child name. If the node has only one child node with the
   * specified name, the child node is returned; if the node has none or more
   * than one child node with the specified name, an XmlException is thrown.
   *
   * @param parent
   *          the parent node.
   * @param childName
   *          the specified tag name.
   * @return the required child element node with the specified tag name of a
   *         specified parent node.
   * @throws InvalidXmlChildrenCountException
   *           if the parent has none or more than one children element nodes
   *           with the specified tag name.
   * @see getFirstChild
   * @see getOptionalChild
   */
  public static Element getReqChild(final Element parent, final String childName)
      throws XmlException {

    final NodeList childrenList = parent.getChildNodes();
    final int childrenCount = childrenList.getLength();
    if (childrenCount == 0) {
      throw new InvalidXmlChildrenCountException(parent.getTagName(),
          childName, 1, 1, 0);
    }
    int count = 0;
    Element result = null;
    for (int i = 0; i < childrenCount; ++i) {
      final Node child = childrenList.item(i);
      if (child.getNodeType() != Node.ELEMENT_NODE) {
        continue;
      }
      final Element element = (Element) child;
      if (childName.equals(element.getTagName())) {
        ++count;
        result = element;
      }
    }
    if (count != 1) {
      throw new InvalidXmlChildrenCountException(parent.getTagName(),
          childName, 1, 1, count);
    }
    return result;

  }

  /**
   * Gets all children element nodes of a specified parent node.
   *
   * @param parent
   *          a parent node.
   * @param result
   *          an optional list used to store the result. If it is null, a new
   *          list will be created to store the result and returned; if it is
   *          not null, it will be cleared, then the result will be stored in
   *          it, and it is returned.
   * @return the list of all child element nodes of the specified parent node;
   *         returns null or an empty list if no such child element node.
   */
  public static List<Element> getChildren(final Element parent,
      @Nullable List<Element> result) {
    final NodeList childrenList = parent.getChildNodes();
    final int childrenCount = childrenList.getLength();
    if (childrenCount == 0) {
      if (result != null) {
        result.clear();
      }
      return result;
    }
    if (result == null) {
      result = new ArrayList<Element>(childrenCount);
    } else {
      result.clear();
    }
    for (int i = 0; i < childrenCount; ++i) {
      final Node child = childrenList.item(i);
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        result.add((Element) child);
      }
    }
    return result;
  }

  /**
   * Gets all child element nodes of a specified parent node, specifying the
   * minimum and maximum count of the children.
   *
   * @param parent
   *          a parent node.
   * @param minCount
   *          the minimum count of the allowed children.
   * @param maxCount
   *          the maximum count of the allowed children. A negative value means
   *          no limit.
   * @param result
   *          an optional list used to store the result. If it is null, a new
   *          list will be created to store the result and returned; if it is
   *          not null, it will be cleared, then the result will be stored in
   *          it, and it is returned.
   * @return the list of all child element nodes of the specified parent node;
   *         returns null or an empty list if no such child element node.
   * @throws InvalidXmlChildrenCountException
   *           if the children of the node does not confirm to the limitation.
   */
  public static List<Element> getReqChildren(final Element parent,
      final int minCount, final int maxCount, @Nullable List<Element> result)
          throws XmlException {

    result = getChildren(parent, result);
    final int actual = (result == null ? 0 : result.size());
    if ((actual < minCount) || ((maxCount >= 0) && (actual > maxCount))) {
      throw new InvalidXmlChildrenCountException(parent.getTagName(), null,
          minCount, maxCount, actual);
    }
    return result;
  }

  /**
   * Gets all child element nodes of a specified parent node with the specified
   * tag name.
   *
   * @param parent
   *          a parent node.
   * @param childName
   *          a specified tag name.
   * @param result
   *          an optional list used to store the result. If it is null, a new
   *          list will be created to store the result and returned; if it is
   *          not null, it will be cleared, then the result will be stored in
   *          it, and it is returned.
   * @return the list of all child element nodes of the specified parent node;
   *         with the specified tag name; or null if no such child element node.
   */
  public static List<Element> getChildren(final Element parent,
      final String childName, @Nullable List<Element> result) {

    final NodeList childrenList = parent.getChildNodes();
    final int childrenCount = childrenList.getLength();
    if (childrenCount == 0) {
      if (result != null) {
        result.clear();
      }
      return result;
    }
    if (result == null) {
      result = new ArrayList<Element>(childrenCount);
    } else {
      result.clear();
    }
    for (int i = 0; i < childrenCount; ++i) {
      final Node child = childrenList.item(i);
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        final Element element = (Element) child;
        if (childName.equals(element.getTagName())) {
          result.add(element);
        }
      }
    }
    return result;
  }

  /**
   * Gets all child element nodes of a specified parent node with the specified
   * tag name. specifying the minimum and maximum count of the children.
   *
   * @param parent
   *          a parent node.
   * @param childName
   *          a specified tag name.
   * @param minCount
   *          the minimum count of the allowed children.
   * @param maxCount
   *          the maximum count of the allowed children. A negative value means
   *          no limit.
   * @param result
   *          an optional list used to store the result. If it is null, a new
   *          list will be created to store the result and returned; if it is
   *          not null, it will be cleared, then the result will be stored in
   *          it, and it is returned.
   * @return the list of all child element nodes of the specified parent node;
   *         returns null or an empty list if no such child element node.
   * @throws InvalidXmlChildrenCountException
   *           if the children of the node does not confirm to the limitation.
   */
  public static List<Element> getReqChildren(final Element parent,
      final String childName, final int minCount, final int maxCount,
      @Nullable List<Element> result) throws XmlException {

    result = getChildren(parent, childName, result);
    final int actual = (result == null ? 0 : result.size());
    if ((actual < minCount) || ((maxCount >= 0) && (actual > maxCount))) {
      throw new InvalidXmlChildrenCountException(parent.getTagName(),
          childName, minCount, maxCount, actual);
    }
    return result;
  }

  public static List<Element> getDescendant(final Element root,
      final String descendantName, final boolean onlyFirstLevel,
      @Nullable List<Element> result) throws XmlException {
    if (result != null) {
      result.clear();
    } else {
      result = new ArrayList<Element>();
    }
    final DomElementIterator iter = new DomElementIterator(root);
    while (iter.hasNext()) {
      final Element node = iter.next();
      if (descendantName.equals(node.getTagName())) {
        result.add(node);
        if (onlyFirstLevel) {
          iter.skipChildren();
        }
      }
    }
    return result;
  }

  public static boolean getOptBooleanAttr(final Element node,
      final String attrName, final boolean defaultValue) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = node.getAttribute(attrName);
    final BooleanFormat bf = new BooleanFormat();
    final boolean value = bf.parse(attrValue);
    if (bf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static boolean getReqBooleanAttr(final Element node,
      final String attrName) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    final String attrValue = node.getAttribute(attrName);
    final BooleanFormat bf = new BooleanFormat();
    final boolean value = bf.parse(attrValue);
    if (bf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static char getOptCharAttr(final Element node, final String attrName,
      final char defaultValue) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = node.getAttribute(attrName);
    if (attrValue.length() != 1) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return attrValue.charAt(0);
  }

  public static char getOptCharAttr(final Element node, final String attrName,
      final char minValue, final char maxValue, final char defaultValue)
          throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = node.getAttribute(attrName);
    if (attrValue.length() != 1) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    final char result = attrValue.charAt(0);
    if ((result < minValue) || (result > maxValue)) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return result;
  }

  public static char getReqCharAttr(final Element node, final String attrName)
      throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    String attrValue = node.getAttribute(attrName);
    attrValue = StringUtils.strip(attrValue);
    if (attrValue.length() != 1) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return attrValue.charAt(0);
  }

  public static char getReqCharAttr(final Element node, final String attrName,
      final char minValue, final char maxValue) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    String attrValue = node.getAttribute(attrName);
    attrValue = StringUtils.strip(attrValue);
    if (attrValue.length() != 1) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    final char result = attrValue.charAt(0);
    if ((result < minValue) || (result > maxValue)) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return result;
  }

  public static byte getOptByteAttr(final Element node, final String attrName,
      final byte defaultValue) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final byte value = nf.parseByte(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static byte getOptByteAttr(final Element node, final String attrName,
      final byte minValue, final byte maxValue, final byte defaultValue)
          throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final byte value = nf.parseByte(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    if ((value < minValue) || (value > maxValue)) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static byte getReqByteAttr(final Element node, final String attrName)
      throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final byte value = nf.parseByte(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static byte getReqByteAttr(final Element node, final String attrName,
      final byte minValue, final byte maxValue) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final byte value = nf.parseByte(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    if ((value < minValue) || (value > maxValue)) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static short getOptShortAttr(final Element node,
      final String attrName, final short defaultValue) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final short value = nf.parseShort(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static short getOptShortAttr(final Element node,
      final String attrName, final short minValue, final short maxValue,
      final short defaultValue) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final short value = nf.parseShort(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    if ((value < minValue) || (value > maxValue)) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static short getReqShortAttr(final Element node, final String attrName)
      throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final short value = nf.parseShort(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static short getReqShortAttr(final Element node,
      final String attrName, final short minValue, final short maxValue)
          throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final short value = nf.parseShort(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    if ((value < minValue) || (value > maxValue)) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static int getOptIntAttr(final Element node, final String attrName,
      final int defaultValue) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final int value = nf.parseInt(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static int getOptIntAttr(final Element node, final String attrName,
      final int minValue, final int maxValue, final int defaultValue)
          throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final int value = nf.parseInt(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    if ((value < minValue) || (value > maxValue)) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static int getReqIntAttr(final Element node, final String attrName)
      throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final int value = nf.parseInt(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static int getReqIntAttr(final Element node, final String attrName,
      final int minValue, final int maxValue) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final int value = nf.parseInt(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    if ((value < minValue) || (value > maxValue)) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static long getOptLongAttr(final Element node, final String attrName,
      final long defaultValue) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final long value = nf.parseLong(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static long getOptLongAttr(final Element node, final String attrName,
      final long minValue, final long maxValue, final long defaultValue)
          throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final long value = nf.parseLong(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    if ((value < minValue) || (value > maxValue)) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static long getReqLongAttr(final Element node, final String attrName)
      throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final long value = nf.parseLong(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static long getReqLongAttr(final Element node, final String attrName,
      final long minValue, final long maxValue) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final long value = nf.parseLong(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    if ((value < minValue) || (value > maxValue)) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static float getOptFloatAttr(final Element node,
      final String attrName, final float defaultValue) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final float value = nf.parseFloat(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static float getOptFloatAttr(final Element node,
      final String attrName, final float minValue, final float maxValue,
      final float defaultValue) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final float value = nf.parseFloat(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    if ((Comparison.compare(value, minValue) < 0)
        || (Comparison.compare(value, maxValue) > 0)) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static float getReqFloatAttr(final Element node, final String attrName)
      throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final float value = nf.parseFloat(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static float getReqFloatAttr(final Element node,
      final String attrName, final float minValue, final float maxValue)
          throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final float value = nf.parseFloat(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    if ((Comparison.compare(value, minValue) < 0)
        || (Comparison.compare(value, maxValue) > 0)) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static double getOptDoubleAttr(final Element node,
      final String attrName, final double defaultValue) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final double value = nf.parseDouble(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static double getOptDoubleAttr(final Element node,
      final String attrName, final double minValue, final double maxValue,
      final double defaultValue) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final double value = nf.parseDouble(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    if ((Comparison.compare(value, minValue) < 0)
        || (Comparison.compare(value, maxValue) > 0)) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static double getReqDoubleAttr(final Element node,
      final String attrName) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final double value = nf.parseDouble(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static double getReqDoubleAttr(final Element node,
      final String attrName, final double minValue, final double maxValue)
          throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final double value = nf.parseDouble(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    if ((Comparison.compare(value, minValue) < 0)
        || (Comparison.compare(value, maxValue) > 0)) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static String getOptStringAttr(final Element node,
      final String attrName, final boolean strip,
      @Nullable final String defaultValue) {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    String attrValue = node.getAttribute(attrName);
    if (strip) {
      attrValue = StringUtils.strip(attrValue);
    }
    return attrValue;
  }

  public static String getReqStringAttr(final Element node,
      final String attrName, final boolean strip, final boolean allowEmpty)
          throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    String attrValue = node.getAttribute(attrName);
    if (strip) {
      attrValue = StringUtils.strip(attrValue);
    }
    if ((! allowEmpty) && (attrValue.length() == 0)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    return attrValue;
  }

  public static Date getOptDateAttr(final Element node, final String attrName,
      final String pattern, @Nullable final Date defaultValue)
          throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = node.getAttribute(attrName);
    final DateFormat df = new DateFormat(pattern);
    final Date value = df.parse(attrValue);
    if (df.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static Date getReqDateAttr(final Element node, final String attrName,
      final String pattern) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    final String attrValue = node.getAttribute(attrName);
    final DateFormat df = new DateFormat(pattern);
    final Date value = df.parse(attrValue);
    if (df.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static byte[] getOptByteArrayAttr(final Element node,
      final String attrName, @Nullable final String separator,
      @Nullable final byte[] defaultValue) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String str = node.getAttribute(attrName);
    final HexCodec codec = new HexCodec();
    codec.setSeparator(separator);
    final byte[] value = codec.decode(str);
    if (codec.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName, str);
    }
    return value;
  }

  public static byte[] getReqByteArrayAttr(final Element node,
      final String attrName, @Nullable final String separator)
          throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    final String str = node.getAttribute(attrName);
    final HexCodec codec = new HexCodec();
    codec.setSeparator(separator);
    final byte[] value = codec.decode(str);
    if (codec.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName, str);
    }
    return value;
  }

  public static Class<?> getOptClassAttr(final Element node,
      final String attrName, @Nullable final Class<?> defaultValue)
          throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = node.getAttribute(attrName);
    try {
      final String className = StringUtils.strip(attrValue);
      return ClassUtils.getClass(className);
    } catch (final ClassNotFoundException e) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue, e);
    }
  }

  public static Class<?> getReqClassAttr(final Element node,
      final String attrName) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    final String attrValue = node.getAttribute(attrName);
    try {
      final String className = StringUtils.strip(attrValue);
      return ClassUtils.getClass(className);
    } catch (final ClassNotFoundException e) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue, e);
    }
  }

  public static BigInteger getOptBigIntegerAttr(final Element node,
      final String attrName, @Nullable final BigInteger defaultValue)
          throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final BigInteger value = nf.parseBigInteger(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static BigInteger getReqBigIntegerAttr(final Element node,
      final String attrName) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final BigInteger value = nf.parseBigInteger(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static BigDecimal getOptBigDecimalAttr(final Element node,
      final String attrName, @Nullable final BigDecimal defaultValue)
          throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final BigDecimal value = nf.parseBigDecimal(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static BigDecimal getReqBigDecimalAttr(final Element node,
      final String attrName) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    final String attrValue = node.getAttribute(attrName);
    final NumberFormat nf = new NumberFormat();
    final BigDecimal value = nf.parseBigDecimal(attrValue);
    if (nf.fail()) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue);
    }
    return value;
  }

  public static <T extends Enum<T>> T getOptEnumAttr(final Element node,
      final String attrName, final boolean useShortName,
      final Class<T> enumClass, @Nullable final T defaultValue)
          throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = StringUtils.strip(node.getAttribute(attrName));
    final String enumName = (useShortName ? EnumUtils.getFullName(attrValue)
        : attrValue);
    final T[] enumValues = enumClass.getEnumConstants();
    for (int i = 0; i < enumValues.length; ++i) {
      if (enumName.equals(enumValues[i].name())) {
        return enumValues[i];
      }
    }
    throw new InvalidXmlAttributeException(node.getTagName(), attrName,
        attrValue);
  }

  public static <T extends Enum<T>> T getReqEnumAttr(final Element node,
      final String attrName, final boolean useShortName,
      final Class<T> enumClass) throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    final String attrValue = StringUtils.strip(node.getAttribute(attrName));
    final String enumName = (useShortName ? EnumUtils.getFullName(attrValue)
        : attrValue);
    final T[] enumValues = enumClass.getEnumConstants();
    for (int i = 0; i < enumValues.length; ++i) {
      if (enumName.equals(enumValues[i].name())) {
        return enumValues[i];
      }
    }
    throw new InvalidXmlAttributeException(node.getTagName(), attrName,
        attrValue);
  }

  @SuppressWarnings("unchecked")
  public static <T> T getOptObjectAttr(final Element node,
      final String attrName, @Nullable final T defaultValue)
          throws XmlException {
    if (! node.hasAttribute(attrName)) {
      return defaultValue;
    }
    final String attrValue = node.getAttribute(attrName);
    try {
      final String className = StringUtils.strip(attrValue);
      final Class<?> clazz = ClassUtils.getClass(className);
      return (T) clazz.newInstance();
    } catch (final Exception e) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue, e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T getReqObjectAttr(final Element node, final String attrName)
      throws XmlException {
    if (! node.hasAttribute(attrName)) {
      throw new EmptyXmlAttributeException(node.getTagName(), attrName);
    }
    final String attrValue = node.getAttribute(attrName);
    try {
      final String className = StringUtils.strip(attrValue);
      final Class<?> clazz = ClassUtils.getClass(className);
      return (T) clazz.newInstance();
    } catch (final Exception e) {
      throw new InvalidXmlAttributeException(node.getTagName(), attrName,
          attrValue, e);
    }
  }

  public static boolean getOptBoolean(@Nullable final Element node,
      final boolean defaultValue) throws XmlException {
    final String str = getOptString(node, null, false, null);
    if (str == null) {
      return defaultValue;
    }
    final BooleanFormat bf = new BooleanFormat();
    final boolean value = bf.parse(str);
    if (bf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static Boolean getOptBoolean(@Nullable final Element node,
      @Nullable final Boolean defaultValue) throws XmlException {
    final String str = getOptString(node, null, false, null);
    if (str == null) {
      return defaultValue;
    }
    final BooleanFormat bf = new BooleanFormat();
    final boolean value = bf.parse(str);
    if (bf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return Boolean.valueOf(value);
  }

  public static boolean getReqBoolean(final Element node) throws XmlException {
    final String str = getReqString(node, null, false, false);
    final BooleanFormat bf = new BooleanFormat();
    final boolean value = bf.parse(str);
    if (bf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static BooleanList getOptBooleanList(
      @Nullable final List<Element> nodes, @Nullable BooleanList result)
          throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final BooleanFormat bf = new BooleanFormat();
    for (final Element node : nodes) {
      final String str = getOptString(node, null, false, null);
      if (str != null) {
        final boolean value = bf.parse(str);
        if (bf.fail()) {
          throw new InvalidXmlNodeContentException(node.getTagName(), str);
        }
        if (result == null) {
          result = new ArrayBooleanList();
        }
        result.add(value);
      }
    }
    return result;
  }

  public static BooleanList getReqBooleanList(
      @Nullable final List<Element> nodes, @Nullable BooleanList result)
          throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final BooleanFormat bf = new BooleanFormat();
    for (final Element node : nodes) {
      final String str = getReqString(node, null, false, false);
      final boolean value = bf.parse(str);
      if (bf.fail()) {
        throw new InvalidXmlNodeContentException(node.getTagName(), str);
      }
      if (result == null) {
        result = new ArrayBooleanList();
      }
      result.add(value);
    }
    return result;
  }

  public static char getOptChar(@Nullable final Element node,
      final char defaultValue) throws XmlException {
    final String str = getOptString(node, null, false, null);
    if (str == null) {
      return defaultValue;
    }
    if (str.length() == 1) {
      return str.charAt(0);
    } else {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
  }

  public static Character getOptChar(@Nullable final Element node,
      @Nullable final Character defaultValue) throws XmlException {
    final String str = getOptString(node, null, false, null);
    if (str == null) {
      return defaultValue;
    }
    if (str.length() == 1) {
      return Character.valueOf(str.charAt(0));
    } else {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
  }

  public static char getReqChar(final Element node) throws XmlException {
    final String str = getReqString(node, null, false, false);
    if (str.length() == 1) {
      return str.charAt(0);
    } else {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
  }

  public static CharList getOptCharList(@Nullable final List<Element> nodes,
      @Nullable CharList result) throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    for (final Element node : nodes) {
      final String str = getOptString(node, null, false, null);
      if (str != null) {
        if (str.length() != 1) {
          throw new InvalidXmlNodeContentException(node.getTagName(), str);
        }
        final char value = str.charAt(0);
        if (result == null) {
          result = new ArrayCharList();
        }
        result.add(value);
      }
    }
    return result;
  }

  public static CharList getReqCharList(@Nullable final List<Element> nodes,
      @Nullable CharList result) throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    for (final Element node : nodes) {
      final String str = getOptString(node, null, false, null);
      if (str.length() != 1) {
        throw new InvalidXmlNodeContentException(node.getTagName(), str);
      }
      final char value = str.charAt(0);
      if (result == null) {
        result = new ArrayCharList();
      }
      result.add(value);
    }
    return result;
  }

  public static byte getOptByte(@Nullable final Element node,
      final byte defaultValue) throws XmlException {
    final String str = getOptString(node, null, false, null);
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final byte value = nf.parseByte(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static Byte getOptByte(@Nullable final Element node,
      @Nullable final Byte defaultValue) throws XmlException {
    final String str = getOptString(node, null, false, null);
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final byte value = nf.parseByte(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return Byte.valueOf(value);
  }

  public static byte getReqByte(final Element node) throws XmlException {
    final String str = getReqString(node, null, false, false);
    final NumberFormat nf = new NumberFormat();
    final byte value = nf.parseByte(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static ByteList getOptByteList(@Nullable final List<Element> nodes,
      @Nullable ByteList result) throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final NumberFormat nf = new NumberFormat();
    for (final Element node : nodes) {
      final String str = getOptString(node, null, false, null);
      if (str != null) {
        final byte value = nf.parseByte(str);
        if (nf.fail()) {
          throw new InvalidXmlNodeContentException(node.getTagName(), str);
        }
        if (result == null) {
          result = new ArrayByteList();
        }
        result.add(value);
      }
    }
    return result;
  }

  public static ByteList getReqByteList(@Nullable final List<Element> nodes,
      @Nullable ByteList result) throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final NumberFormat nf = new NumberFormat();
    for (final Element node : nodes) {
      final String str = getReqString(node, null, false, false);
      final byte value = nf.parseByte(str);
      if (nf.fail()) {
        throw new InvalidXmlNodeContentException(node.getTagName(), str);
      }
      if (result == null) {
        result = new ArrayByteList();
      }
      result.add(value);
    }
    return result;
  }

  public static short getOptShort(@Nullable final Element node,
      final short defaultValue) throws XmlException {
    final String str = getOptString(node, null, false, null);
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final short value = nf.parseShort(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static Short getOptShort(@Nullable final Element node,
      @Nullable final Short defaultValue) throws XmlException {
    final String str = getOptString(node, null, false, null);
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final short value = nf.parseShort(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return Short.valueOf(value);
  }

  public static short getReqShort(final Element node) throws XmlException {
    final String str = getReqString(node, null, false, false);
    final NumberFormat nf = new NumberFormat();
    final short value = nf.parseShort(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static ShortList getOptShortList(@Nullable final List<Element> nodes,
      @Nullable ShortList result) throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final NumberFormat nf = new NumberFormat();
    for (final Element node : nodes) {
      final String str = getOptString(node, null, false, null);
      if (str != null) {
        final short value = nf.parseShort(str);
        if (nf.fail()) {
          throw new InvalidXmlNodeContentException(node.getTagName(), str);
        }
        if (result == null) {
          result = new ArrayShortList();
        }
        result.add(value);
      }
    }
    return result;
  }

  public static ShortList getReqShortList(@Nullable final List<Element> nodes,
      @Nullable ShortList result) throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final NumberFormat nf = new NumberFormat();
    for (final Element node : nodes) {
      final String str = getReqString(node, null, false, false);
      final short value = nf.parseShort(str);
      if (nf.fail()) {
        throw new InvalidXmlNodeContentException(node.getTagName(), str);
      }
      if (result == null) {
        result = new ArrayShortList();
      }
      result.add(value);
    }
    return result;
  }

  public static int getOptInt(@Nullable final Element node,
      final int defaultValue) throws XmlException {
    final String str = getOptString(node, null, false, null);
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final int value = nf.parseInt(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static Integer getOptInt(@Nullable final Element node,
      @Nullable final Integer defaultValue) throws XmlException {
    final String str = getOptString(node, null, false, null);
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final int value = nf.parseInt(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return Integer.valueOf(value);
  }

  public static int getReqInt(final Element node) throws XmlException {
    final String str = getReqString(node, null, false, false);
    final NumberFormat nf = new NumberFormat();
    final int value = nf.parseInt(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static IntList getOptIntList(@Nullable final List<Element> nodes,
      @Nullable IntList result) throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final NumberFormat nf = new NumberFormat();
    for (final Element node : nodes) {
      final String str = getOptString(node, null, false, null);
      if (str != null) {
        final int value = nf.parseInt(str);
        if (nf.fail()) {
          throw new InvalidXmlNodeContentException(node.getTagName(), str);
        }
        if (result == null) {
          result = new ArrayIntList();
        }
        result.add(value);
      }
    }
    return result;
  }

  public static IntList getReqIntList(@Nullable final List<Element> nodes,
      @Nullable IntList result) throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final NumberFormat nf = new NumberFormat();
    for (final Element node : nodes) {
      final String str = getReqString(node, null, false, false);
      final int value = nf.parseInt(str);
      if (nf.fail()) {
        throw new InvalidXmlNodeContentException(node.getTagName(), str);
      }
      if (result == null) {
        result = new ArrayIntList();
      }
      result.add(value);
    }
    return result;
  }

  public static long getOptLong(@Nullable final Element node,
      final long defaultValue) throws XmlException {
    final String str = getOptString(node, null, false, null);
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final long value = nf.parseLong(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static Long getOptLong(@Nullable final Element node,
      @Nullable final Long defaultValue) throws XmlException {
    final String str = getOptString(node, null, false, null);
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final long value = nf.parseLong(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return Long.valueOf(value);
  }

  public static long getReqLong(final Element node) throws XmlException {
    final String str = getReqString(node, null, false, false);
    final NumberFormat nf = new NumberFormat();
    final long value = nf.parseLong(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static LongList getOptLongList(@Nullable final List<Element> nodes,
      @Nullable LongList result) throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final NumberFormat nf = new NumberFormat();
    for (final Element node : nodes) {
      final String str = getOptString(node, null, false, null);
      if (str != null) {
        final long value = nf.parseLong(str);
        if (nf.fail()) {
          throw new InvalidXmlNodeContentException(node.getTagName(), str);
        }
        if (result == null) {
          result = new ArrayLongList();
        }
        result.add(value);
      }
    }
    return result;
  }

  public static LongList getReqLongList(@Nullable final List<Element> nodes,
      @Nullable LongList result) throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final NumberFormat nf = new NumberFormat();
    for (final Element node : nodes) {
      final String str = getReqString(node, null, false, false);
      final long value = nf.parseLong(str);
      if (nf.fail()) {
        throw new InvalidXmlNodeContentException(node.getTagName(), str);
      }
      if (result == null) {
        result = new ArrayLongList();
      }
      result.add(value);
    }
    return result;
  }

  public static float getOptFloat(@Nullable final Element node,
      final float defaultValue) throws XmlException {
    final String str = getOptString(node, null, false, null);
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final float value = nf.parseFloat(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static Float getOptFloat(@Nullable final Element node,
      @Nullable final Float defaultValue) throws XmlException {
    final String str = getOptString(node, null, false, null);
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final float value = nf.parseFloat(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return Float.valueOf(value);
  }

  public static float getReqFloat(final Element node) throws XmlException {
    final String str = getReqString(node, null, false, false);
    final NumberFormat nf = new NumberFormat();
    final float value = nf.parseFloat(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static FloatList getOptFloatList(@Nullable final List<Element> nodes,
      @Nullable FloatList result) throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final NumberFormat nf = new NumberFormat();
    for (final Element node : nodes) {
      final String str = getOptString(node, null, false, null);
      if (str != null) {
        final float value = nf.parseFloat(str);
        if (nf.fail()) {
          throw new InvalidXmlNodeContentException(node.getTagName(), str);
        }
        if (result == null) {
          result = new ArrayFloatList();
        }
        result.add(value);
      }
    }
    return result;
  }

  public static FloatList getReqFloatList(@Nullable final List<Element> nodes,
      @Nullable FloatList result) throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final NumberFormat nf = new NumberFormat();
    for (final Element node : nodes) {
      final String str = getReqString(node, null, false, false);
      final float value = nf.parseFloat(str);
      if (nf.fail()) {
        throw new InvalidXmlNodeContentException(node.getTagName(), str);
      }
      if (result == null) {
        result = new ArrayFloatList();
      }
      result.add(value);
    }
    return result;
  }

  public static double getOptDouble(@Nullable final Element node,
      final double defaultValue) throws XmlException {
    final String str = getOptString(node, null, false, null);
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final double value = nf.parseDouble(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static Double getOptDouble(@Nullable final Element node,
      @Nullable final Double defaultValue) throws XmlException {
    final String str = getOptString(node, null, false, null);
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final double value = nf.parseDouble(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return Double.valueOf(value);
  }

  public static double getReqDouble(final Element node) throws XmlException {
    final String str = getReqString(node, null, false, false);
    final NumberFormat nf = new NumberFormat();
    final double value = nf.parseDouble(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static DoubleList getOptDoubleList(
      @Nullable final List<Element> nodes, @Nullable DoubleList result)
          throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final NumberFormat nf = new NumberFormat();
    for (final Element node : nodes) {
      final String str = getOptString(node, null, false, null);
      if (str != null) {
        final double value = nf.parseDouble(str);
        if (nf.fail()) {
          throw new InvalidXmlNodeContentException(node.getTagName(), str);
        }
        if (result == null) {
          result = new ArrayDoubleList();
        }
        result.add(value);
      }
    }
    return result;
  }

  public static DoubleList getReqDoubleList(
      @Nullable final List<Element> nodes, @Nullable DoubleList result)
          throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final NumberFormat nf = new NumberFormat();
    for (final Element node : nodes) {
      final String str = getReqString(node, null, false, false);
      final double value = nf.parseDouble(str);
      if (nf.fail()) {
        throw new InvalidXmlNodeContentException(node.getTagName(), str);
      }
      if (result == null) {
        result = new ArrayDoubleList();
      }
      result.add(value);
    }
    return result;
  }

  /**
   * Gets the character data of the first text child node of a specified node.
   *
   * @param node
   *          a node. It must has one and only one text child.
   * @param prevSpaceAttr
   *          the attribute name of the "preserve space" attribute. If this
   *          argument is not null and the {@code node} has the attribute
   *          with this name, the function will use the attribute value to
   *          decide whether to strip the returned string; otherwise, the
   *          function will use the {@code defaultTrim} argument value to
   *          decide whether to strip the returned string.
   * @param defaultTrim
   *          the function will use this argument value to decide whether to
   *          strip the returned string in case that {@code prevSpaceAttr}
   *          argument is null or the {@code node} has no attribute of name
   *          {@code prevSpaceAttr}.
   * @param defaultValue
   *          the default value to be returned if the node is empty (i.e., no
   *          children). Note that the it could be {@code null}.
   * @return the character data of the ONLY text child node of a specified
   *         node; or the defaultValue if the node is empty (i.e., has no
   *         children).
   */
  public static String getOptString(@Nullable final Element node,
      @Nullable final String prevSpaceAttr, final boolean defaultTrim,
      @Nullable final String defaultValue) {
    if (node == null) {
      return defaultValue;
    }
    if (! node.hasChildNodes()) {
      return defaultValue;
    }
    // decide whether to strip the returned string
    boolean strip = defaultTrim;
    if ((prevSpaceAttr != null) && node.hasAttribute(prevSpaceAttr)) {
      final String attrValue = node.getAttribute(prevSpaceAttr);
      final boolean preserveSpace = StringUtils.toBoolean(attrValue);
      strip = (! preserveSpace);
    }
    // get the text data of the first Text child of node
    String result = defaultValue;
    final NodeList childrenList = node.getChildNodes();
    final int childrenCount = childrenList.getLength();
    for (int i = 0; i < childrenCount; ++i) {
      final Node child = childrenList.item(i);
      if (child.getNodeType() == Node.TEXT_NODE) {
        final Text textChild = (Text) child;
        result = textChild.getData();
        if ((result != null) && strip) {
          result = StringUtils.strip(result);
        }
        // only get the text from the first Text child
        return result;
      }
    }
    //  the node has no text child
    return defaultValue;
  }

  /**
   * Gets the character data of the first text child node of a specified node.
   *
   * @param node
   *          a node. It must has one and only one text child.
   * @param prevSpaceAttr
   *          the attribute name of the "preserve space" attribute. If this
   *          argument is not null and the {@code node} has the attribute
   *          with this name, the function will use the attribute value to
   *          decide whether to strip the returned string; otherwise, the
   *          function will use the {@code defaultTrim} argument value to
   *          decide whether to strip the returned string.
   * @param defaultTrim
   *          the function will use this argument value to decide whether to
   *          strip the returned string in case that {@code prevSpaceAttr}
   *          argument is null or the {@code node} has no attribute of name
   *          {@code prevSpaceAttr}.
   * @param allowEmpty
   *          whether the text content allows to be empty.
   * @return the character data of the ONLY text child node of a specified node.
   * @throws EmptyXmlNodeException
   *           if the node has no children nodes or its {@code Text} child
   *           node has an empty content.
   * @throws InvalidXmlNodeStructException
   *           if the node has no {@code Text} child node.
   */
  public static String getReqString(final Element node,
      @Nullable final String prevSpaceAttr, final boolean defaultTrim,
      final boolean allowEmpty) throws XmlException {

    if (! node.hasChildNodes()) {
      if (allowEmpty) {
        return StringUtils.EMPTY;
      } else {
        throw new EmptyXmlNodeException(node.getTagName());
      }
    }
    // decide whether to strip the returned string
    boolean strip = defaultTrim;
    if ((prevSpaceAttr != null) && node.hasAttribute(prevSpaceAttr)) {
      final String attrValue = node.getAttribute(prevSpaceAttr);
      final boolean preserveSpace = StringUtils.toBoolean(attrValue);
      strip = (! preserveSpace);
    }
    // get the text data of the first Text child of node
    String result = null;
    final NodeList childrenList = node.getChildNodes();
    final int childrenCount = childrenList.getLength();
    for (int i = 0; i < childrenCount; ++i) {
      final Node child = childrenList.item(i);
      if (child.getNodeType() != Node.TEXT_NODE) {
        // skip non Text children
        continue;
      }
      final Text textChild = (Text) child;
      result = textChild.getData();
      if ((result != null) && strip) {
        result = StringUtils.strip(result);
      }
      // only get the text from the first Text child
      break;
    }
    // return the result
    if (result == null) {
      throw new InvalidXmlNodeStructException(node);
    }
    if ((result.length() == 0) && (! allowEmpty)) {
      throw new EmptyXmlNodeException(node.getTagName());
    }
    return result;
  }

  public static List<String> getOptStringList(
      @Nullable final List<Element> nodes,
      @Nullable final String prevSpaceAttr, final boolean defaultTrim,
      final boolean ignoreEmpty, @Nullable List<String> result)
          throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    for (final Element node : nodes) {
      final String str = getOptString(node, prevSpaceAttr, defaultTrim, null);
      if (str == null) {
        continue;
      }
      if (ignoreEmpty && (str.length() == 0)) {
        continue;
      }
      if (result == null) {
        result = new ArrayList<String>();
      }
      result.add(str);
    }
    return result;
  }

  public static List<String> getReqStringList(
      @Nullable final List<Element> nodes,
      @Nullable final String prevSpaceAttr, final boolean defaultTrim,
      final boolean ignoreEmpty, @Nullable List<String> result)
          throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    for (final Element node : nodes) {
      final String str = getReqString(node, prevSpaceAttr, defaultTrim, true);
      if (ignoreEmpty && (str.length() == 0)) {
        continue;
      }
      if (result == null) {
        result = new ArrayList<String>();
      }
      result.add(str);
    }
    return result;
  }

  public static Date getOptDate(@Nullable final Element node,
      final String pattern, @Nullable final Date defaultValue)
          throws XmlException {
    final String str = getOptString(node, null, false, null);
    if (str == null) {
      return defaultValue;
    }
    final DateFormat df = new DateFormat(pattern);
    final Date value = df.parse(str);
    if (df.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static Date getReqDate(final Element node, final String pattern)
      throws XmlException {
    final String str = getReqString(node, null, false, false);
    final DateFormat df = new DateFormat(pattern);
    final Date value = df.parse(str);
    if (df.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static List<Date> getOptDateList(@Nullable final List<Element> nodes,
      final String pattern, @Nullable List<Date> result) throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final DateFormat df = new DateFormat(pattern);
    for (final Element node : nodes) {
      final String str = getOptString(node, null, false, null);
      if (str != null) {
        final Date value = df.parse(str);
        if (df.fail()) {
          throw new InvalidXmlNodeContentException(node.getTagName(), str);
        }
        if (result == null) {
          result = new ArrayList<Date>();
        }
        result.add(value);
      }
    }
    return result;
  }

  public static List<Date> getReqDateList(@Nullable final List<Element> nodes,
      final String pattern, @Nullable List<Date> result) throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final DateFormat df = new DateFormat(pattern);
    for (final Element node : nodes) {
      final String str = getReqString(node, null, false, false);
      final Date value = df.parse(str);
      if (df.fail()) {
        throw new InvalidXmlNodeContentException(node.getTagName(), str);
      }
      if (result == null) {
        result = new ArrayList<Date>();
      }
      result.add(value);
    }
    return result;
  }

  public static Class<?> getOptClass(@Nullable final Element node,
      @Nullable final Class<?> defaultValue) throws XmlException {
    final String className = getOptString(node, null, true, null);
    if (className == null) {
      return defaultValue;
    }
    try {
      return ClassUtils.getClass(className);
    } catch (final ClassNotFoundException e) {
      throw new InvalidXmlNodeContentException(node.getTagName(), className, e);
    }
  }

  public static Class<?> getReqClass(final Element node) throws XmlException {
    final String str = getReqString(node, null, true, false);
    try {
      return ClassUtils.getClass(str);
    } catch (final ClassNotFoundException e) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str, e);
    }
  }

  public static List<Class<?>> getOptClassList(
      @Nullable final List<Element> nodes, @Nullable List<Class<?>> result)
          throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    for (final Element node : nodes) {
      final String str = getOptString(node, null, true, null);
      if (str != null) {
        Class<?> value;
        try {
          value = ClassUtils.getClass(str);
        } catch (final ClassNotFoundException e) {
          throw new InvalidXmlNodeContentException(node.getTagName(), str, e);
        }
        if (result == null) {
          result = new ArrayList<Class<?>>();
        }
        result.add(value);
      }
    }
    return result;
  }

  public static List<Class<?>> getReqClassList(
      @Nullable final List<Element> nodes, @Nullable List<Class<?>> result)
          throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    for (final Element node : nodes) {
      final String str = getReqString(node, null, false, false);
      Class<?> value;
      try {
        value = ClassUtils.getClass(str);
      } catch (final ClassNotFoundException e) {
        throw new InvalidXmlNodeContentException(node.getTagName(), str, e);
      }
      if (result == null) {
        result = new ArrayList<Class<?>>();
      }
      result.add(value);
    }
    return result;
  }

  public static byte[] getOptByteArray(@Nullable final Element node,
      @Nullable final String separator, @Nullable final byte[] defaultValue)
          throws XmlException {
    final String str = getOptString(node, null, false, null);
    if (str == null) {
      return defaultValue;
    }
    final HexCodec codec = new HexCodec();
    codec.setSeparator(separator);
    final byte[] value = codec.decode(str);
    if (codec.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static byte[] getReqByteArray(final Element node,
      @Nullable final String separator) throws XmlException {
    final String str = getReqString(node, null, false, false);
    final HexCodec codec = new HexCodec();
    codec.setSeparator(separator);
    final byte[] value = codec.decode(str);
    if (codec.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static List<byte[]> getOptByteArrayList(
      @Nullable final List<Element> nodes, @Nullable final String separator,
      @Nullable List<byte[]> result) throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final HexCodec codec = new HexCodec();
    codec.setSeparator(separator);
    for (final Element node : nodes) {
      final String str = getOptString(node, null, false, null);
      if (str != null) {
        final byte[] value = codec.decode(str);
        if (codec.fail()) {
          throw new InvalidXmlNodeContentException(node.getTagName(), str);
        }
        if (result == null) {
          result = new ArrayList<byte[]>();
        }
        result.add(value);
      }
    }
    return result;
  }

  public static List<byte[]> getReqByteArrayList(
      @Nullable final List<Element> nodes, @Nullable final String separator,
      @Nullable List<byte[]> result) throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final HexCodec codec = new HexCodec();
    codec.setSeparator(separator);
    for (final Element node : nodes) {
      final String str = getReqString(node, null, false, false);
      final byte[] value = codec.decode(str);
      if (codec.fail()) {
        throw new InvalidXmlNodeContentException(node.getTagName(), str);
      }
      if (result == null) {
        result = new ArrayList<byte[]>();
      }
      result.add(value);
    }
    return result;
  }

  public static BigInteger getOptBigInteger(@Nullable final Element node,
      @Nullable final BigInteger defaultValue) throws XmlException {
    final String str = getOptString(node, null, false, null);
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final BigInteger value = nf.parseBigInteger(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static BigInteger getReqBigInteger(final Element node)
      throws XmlException {
    final String str = getReqString(node, null, false, false);
    final NumberFormat nf = new NumberFormat();
    final BigInteger value = nf.parseBigInteger(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static List<BigInteger> getOptBigIntegerList(
      @Nullable final List<Element> nodes, @Nullable List<BigInteger> result)
          throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final NumberFormat nf = new NumberFormat();
    for (final Element node : nodes) {
      final String str = getOptString(node, null, false, null);
      if (str != null) {
        final BigInteger value = nf.parseBigInteger(str);
        if (nf.fail()) {
          throw new InvalidXmlNodeContentException(node.getTagName(), str);
        }
        if (result == null) {
          result = new ArrayList<BigInteger>();
        }
        result.add(value);
      }
    }
    return result;
  }

  public static List<BigInteger> getReqBigIntegerList(
      @Nullable final List<Element> nodes, @Nullable List<BigInteger> result)
          throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final NumberFormat nf = new NumberFormat();
    for (final Element node : nodes) {
      final String str = getReqString(node, null, false, false);
      final BigInteger value = nf.parseBigInteger(str);
      if (nf.fail()) {
        throw new InvalidXmlNodeContentException(node.getTagName(), str);
      }
      if (result == null) {
        result = new ArrayList<BigInteger>();
      }
      result.add(value);
    }
    return result;
  }

  public static BigDecimal getOptBigDecimal(@Nullable final Element node,
      @Nullable final BigDecimal defaultValue) throws XmlException {
    final String str = getOptString(node, null, false, null);
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final BigDecimal value = nf.parseBigDecimal(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static BigDecimal getReqBigDecimal(final Element node)
      throws XmlException {
    final String str = getReqString(node, null, false, false);
    final NumberFormat nf = new NumberFormat();
    final BigDecimal value = nf.parseBigDecimal(str);
    if (nf.fail()) {
      throw new InvalidXmlNodeContentException(node.getTagName(), str);
    }
    return value;
  }

  public static List<BigDecimal> getOptBigDecimalList(
      @Nullable final List<Element> nodes, @Nullable List<BigDecimal> result)
          throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final NumberFormat nf = new NumberFormat();
    for (final Element node : nodes) {
      final String str = getOptString(node, null, false, null);
      if (str != null) {
        final BigDecimal value = nf.parseBigDecimal(str);
        if (nf.fail()) {
          throw new InvalidXmlNodeContentException(node.getTagName(), str);
        }
        if (result == null) {
          result = new ArrayList<BigDecimal>();
        }
        result.add(value);
      }
    }
    return result;
  }

  public static List<BigDecimal> getReqBigDecimalList(
      @Nullable final List<Element> nodes, @Nullable List<BigDecimal> result)
          throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    final NumberFormat nf = new NumberFormat();
    for (final Element node : nodes) {
      final String str = getReqString(node, null, false, false);
      final BigDecimal value = nf.parseBigDecimal(str);
      if (nf.fail()) {
        throw new InvalidXmlNodeContentException(node.getTagName(), str);
      }
      if (result == null) {
        result = new ArrayList<BigDecimal>();
      }
      result.add(value);
    }
    return result;
  }

  public static <T extends Enum<T>> T getOptEnum(@Nullable final Element node,
      final boolean useShortName, final Class<T> enumClass,
      @Nullable final T defaultValue) throws XmlException {
    final String str = getOptString(node, null, true, null);
    if (str == null) {
      return defaultValue;
    }
    final String enumName = (useShortName ? EnumUtils.getFullName(str) : str);
    final T[] enumValues = enumClass.getEnumConstants();
    for (int i = 0; i < enumValues.length; ++i) {
      if (enumName.equals(enumValues[i].name())) {
        return enumValues[i];
      }
    }
    throw new InvalidXmlNodeContentException(node.getTagName(), str);
  }

  public static <T extends Enum<T>> T getReqEnum(final Element node,
      final boolean useShortName, final Class<T> enumClass) throws XmlException {
    final String str = getReqString(node, null, true, false);
    final String enumName = (useShortName ? EnumUtils.getFullName(str) : str);
    final T[] enumValues = enumClass.getEnumConstants();
    for (int i = 0; i < enumValues.length; ++i) {
      if (enumName.equals(enumValues[i].name())) {
        return enumValues[i];
      }
    }
    throw new InvalidXmlNodeContentException(node.getTagName(), str);
  }

  public static <T extends Enum<T>> List<T> getOptEnumList(
      @Nullable final List<Element> nodes, final boolean useShortName,
      final Class<T> enumClass, @Nullable List<T> result) throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    for (final Element node : nodes) {
      final T value = getOptEnum(node, useShortName, enumClass, null);
      if (value != null) {
        if (result == null) {
          result = new ArrayList<T>();
        }
        result.add(value);
      }
    }
    return result;
  }

  public static <T extends Enum<T>> List<T> getReqEnumList(
      @Nullable final List<Element> nodes, final boolean useShortName,
      final Class<T> enumClass, @Nullable List<T> result) throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    for (final Element node : nodes) {
      final T value = getReqEnum(node, useShortName, enumClass);
      if (result == null) {
        result = new ArrayList<T>();
      }
      result.add(value);
    }
    return result;
  }

  public static <T> T getOptSer(@Nullable final Element node,
      final Class<T> objClass, final boolean allowConfig,
      @Nullable final String configAttr, @Nullable final T defaultValue)
          throws XmlException {
    final XmlSerializer serializer = XmlSerialization.getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    return getOptSer(node, objClass, serializer, allowConfig, configAttr,
        defaultValue);
  }

  @SuppressWarnings("unchecked")
  public static <T> T getOptSer(@Nullable final Element node,
      final Class<T> objClass, final XmlSerializer serializer,
      final boolean allowConfig, @Nullable String configAttr,
      @Nullable final T defaultValue) throws XmlException {
    if (node == null) {
      return defaultValue;
    }
    if (configAttr == null) {
      configAttr = DEFAULT_CONFIG_ATTRIBUTE;
    }
    final Element root;
    if (allowConfig && node.hasAttribute(configAttr)) {
      final String attrValue = node.getAttribute(configAttr);
      final String resource = StringUtils.strip(attrValue);
      final Document doc = XmlUtils.parse(resource, objClass);
      root = doc.getDocumentElement();
    } else {
      root = node;
    }
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlException(e);
    }
  }

  public static <T> T getReqSer(final Element node, final Class<T> objClass,
      final boolean allowConfig, @Nullable final String configAttr)
          throws XmlException {
    final XmlSerializer serializer = XmlSerialization.getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    return getReqSer(node, objClass, serializer, allowConfig, configAttr);
  }

  @SuppressWarnings("unchecked")
  public static <T> T getReqSer(final Element node, final Class<T> objClass,
      final XmlSerializer serializer, final boolean allowConfig,
      @Nullable String configAttr) throws XmlException {
    if (configAttr == null) {
      configAttr = DEFAULT_CONFIG_ATTRIBUTE;
    }
    Element root;
    if (allowConfig && node.hasAttribute(configAttr)) {
      final String attrValue = node.getAttribute(configAttr);
      final String resource = StringUtils.strip(attrValue);
      final Document doc = XmlUtils.parse(resource, objClass);
      root = doc.getDocumentElement();
    } else {
      root = node;
    }
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> List<T> getSerList(@Nullable final List<Element> nodes,
      final Class<T> objClass, final XmlSerializer serializer,
      final boolean allowConfig, @Nullable String configAttr,
      @Nullable List<T> result) throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    if (configAttr == null) {
      configAttr = DEFAULT_CONFIG_ATTRIBUTE;
    }
    for (final Element node : nodes) {
      final Element root;
      if (allowConfig && node.hasAttribute(configAttr)) {
        final String attrValue = node.getAttribute(configAttr);
        final String resource = StringUtils.strip(attrValue);
        final Document doc = XmlUtils.parse(resource, objClass);
        root = doc.getDocumentElement();
      } else {
        root = node;
      }
      T value;
      try {
        value = (T) serializer.deserialize(root);
      } catch (final ClassCastException e) {
        throw new XmlException(e);
      }
      if (result == null) {
        result = new ArrayList<T>();
      }
      result.add(value);
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public static <T> T getOptPolySer(@Nullable final Element node,
      @Nullable String classAttr, final boolean allowConfig,
      @Nullable String configAttr, @Nullable final T defaultValue)
          throws XmlException {
    if (node == null) {
      return defaultValue;
    }
    if (classAttr == null) {
      classAttr = DEFAULT_CLASS_ATTRIBUTE;
    }
    if (configAttr == null) {
      configAttr = DEFAULT_CONFIG_ATTRIBUTE;
    }
    final String className = getReqStringAttr(node, classAttr, true, false);
    Class<?> objClass;
    try {
      objClass = ClassUtils.getClass(className);
    } catch (final ClassNotFoundException e) {
      throw new InvalidXmlAttributeException(node.getTagName(), classAttr,
          className, e);
    }
    final XmlSerializer serializer = XmlSerialization.getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    Element root;
    if (allowConfig && node.hasAttribute(configAttr)) {
      final String config = StringUtils.strip(node.getAttribute(configAttr));
      final Document doc = XmlUtils.parse(config, objClass);
      root = doc.getDocumentElement();
    } else {
      root = node;
    }
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T getReqPolySer(final Element node,
      @Nullable String classAttr, final boolean allowConfig,
      @Nullable String configAttr) throws XmlException {
    if (classAttr == null) {
      classAttr = DEFAULT_CLASS_ATTRIBUTE;
    }
    if (configAttr == null) {
      configAttr = DEFAULT_CONFIG_ATTRIBUTE;
    }
    final String className = getReqStringAttr(node, classAttr, true, false);
    Class<?> objClass;
    try {
      objClass = ClassUtils.getClass(className);
    } catch (final ClassNotFoundException e) {
      throw new InvalidXmlAttributeException(node.getTagName(), classAttr,
          className, e);
    }
    final XmlSerializer serializer = XmlSerialization.getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    Element root;
    if (allowConfig && node.hasAttribute(configAttr)) {
      final String config = StringUtils.strip(node.getAttribute(configAttr));
      final Document doc = XmlUtils.parse(config, objClass);
      root = doc.getDocumentElement();
    } else {
      root = node;
    }
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> List<T> getPolySerList(@Nullable final List<Element> nodes,
      @Nullable String classAttr, final boolean allowConfig,
      @Nullable String configAttr, @Nullable List<T> result)
          throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    if (classAttr == null) {
      classAttr = DEFAULT_CLASS_ATTRIBUTE;
    }
    if (configAttr == null) {
      configAttr = DEFAULT_CONFIG_ATTRIBUTE;
    }
    for (final Element node : nodes) {
      final String className = getReqStringAttr(node, classAttr, true, false);
      Class<?> objClass;
      try {
        objClass = ClassUtils.getClass(className);
      } catch (final ClassNotFoundException e) {
        throw new InvalidXmlAttributeException(node.getTagName(), classAttr,
            className, e);
      }
      final XmlSerializer serializer = XmlSerialization.getSerializer(objClass);
      if (serializer == null) {
        throw new NoXmlSerializerRegisteredException(objClass);
      }
      final Element root;
      if (allowConfig && node.hasAttribute(configAttr)) {
        final String attrValue = node.getAttribute(configAttr);
        final String resource = StringUtils.strip(attrValue);
        final Document doc = XmlUtils.parse(resource, objClass);
        root = doc.getDocumentElement();
      } else {
        root = node;
      }
      T value;
      try {
        value = (T) serializer.deserialize(root);
      } catch (final ClassCastException e) {
        throw new XmlException(e);
      }
      if (result == null) {
        result = new ArrayList<T>();
      }
      result.add(value);
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public static <T> T getOptInstance(@Nullable final Element node,
      @Nullable final T defaultValue) throws XmlException {
    final String className = getOptString(node, null, true, null);
    if (className == null) {
      return defaultValue;
    }
    try {
      final Class<?> clazz = ClassUtils.getClass(className);
      return (T) clazz.newInstance();
    } catch (final Exception e) {
      throw new InvalidXmlNodeContentException(node.getTagName(), className, e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T getReqInstance(final Element node)
      throws XmlException {
    final String className = getReqString(node, null, true, false);
    try {
      final Class<?> clazz = ClassUtils.getClass(className);
      return (T) clazz.newInstance();
    } catch (final Exception e) {
      throw new InvalidXmlNodeContentException(node.getTagName(), className, e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> List<T> getOptInstanceList(
      @Nullable final List<Element> nodes, @Nullable List<T> result)
          throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    for (final Element node : nodes) {
      final String className = getOptString(node, null, true, null);
      if (className == null) {
        continue;
      }
      T value;
      try {
        final Class<?> clazz = ClassUtils.getClass(className);
        value = (T) clazz.newInstance();
      } catch (final Exception e) {
        throw new InvalidXmlNodeContentException(node.getTagName(), className,
            e);
      }
      if (result == null) {
        result = new ArrayList<T>();
      }
      result.add(value);
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public static <T> List<T> getReqInstanceList(
      @Nullable final List<Element> nodes, @Nullable List<T> result)
          throws XmlException {
    if (result != null) {
      result.clear();
    }
    if ((nodes == null) || nodes.isEmpty()) {
      return result;
    }
    for (final Element node : nodes) {
      final String className = getReqString(node, null, true, false);
      T value;
      try {
        final Class<?> clazz = ClassUtils.getClass(className);
        value = (T) clazz.newInstance();
      } catch (final Exception e) {
        throw new InvalidXmlNodeContentException(node.getTagName(), className,
            e);
      }
      if (result == null) {
        result = new ArrayList<T>();
      }
      result.add(value);
    }
    return result;
  }

  public static List<String> getOptStrings(@Nullable final Element node,
      @Nullable final String prevSpaceAttr, final boolean defaultTrim,
      @Nullable final String separatorAttr, final char defaultSeparator,
      final boolean ignoreEmpty, @Nullable final List<String> result)
          throws XmlException {
    if ((node == null) || (! node.hasChildNodes())) {
      if (result != null) {
        result.clear();
      }
      return result;
    }
    // decide whether to strip the returned string
    boolean strip = defaultTrim;
    if ((prevSpaceAttr != null) && node.hasAttribute(prevSpaceAttr)) {
      final String attrValue = node.getAttribute(prevSpaceAttr);
      final boolean preserveSpace = StringUtils.toBoolean(attrValue);
      strip = (! preserveSpace);
    }
    // get the separator used to split the returned string
    char separator = defaultSeparator;
    final String separatorAttrValue = node.getAttribute(separatorAttr);
    if ((separatorAttrValue != null) && (separatorAttrValue.length() > 0)) {
      if (separatorAttrValue.length() != 1) {
        throw new InvalidXmlAttributeException(node.getTagName(),
            separatorAttr, separatorAttrValue);
      }
      separator = separatorAttrValue.charAt(0);
    }
    // get the text data of the first Text child of node
    String resultStr = null;
    final NodeList childrenList = node.getChildNodes();
    final int childrenCount = childrenList.getLength();
    for (int i = 0; i < childrenCount; ++i) {
      final Node child = childrenList.item(i);
      if (child.getNodeType() != Node.TEXT_NODE) {
        // skip non Text children
        continue;
      }
      final Text textChild = (Text) child;
      if (resultStr == null) {
        resultStr = textChild.getData();
      } else {
        // there was a Text child before this child,
        // join the text with the separator
        resultStr += separator;
        resultStr += textChild.getData();
      }
      // continue to get the next Text child
    }
    if (resultStr == null) {
      throw new InvalidXmlNodeStructException(node);
    }
    return StringUtils.split(resultStr, separator, strip, ignoreEmpty, result);
  }

  public static List<String> getReqStrings(final Element node,
      @Nullable final String prevSpaceAttr, final boolean defaultTrim,
      final boolean allowEmpty, @Nullable final String separatorAttr,
      final char defaultSeparator, final boolean ignoreEmpty,
      @Nullable final List<String> result) throws XmlException {

    if (! node.hasChildNodes()) {
      if (allowEmpty) {
        if (result == null) {
          return new ArrayList<String>();
        } else {
          result.clear();
          return result;
        }
      } else {
        throw new EmptyXmlNodeException(node.getTagName());
      }
    }
    // decide whether to strip the returned string
    boolean strip = defaultTrim;
    if ((prevSpaceAttr != null) && node.hasAttribute(prevSpaceAttr)) {
      final String attrValue = node.getAttribute(prevSpaceAttr);
      final boolean preserveSpace = StringUtils.toBoolean(attrValue);
      strip = (! preserveSpace);
    }
    // get the separator used to split the returned string
    char separator = defaultSeparator;
    final String separatorAttrValue = node.getAttribute(separatorAttr);
    if ((separatorAttrValue != null) && (separatorAttrValue.length() > 0)) {
      if (separatorAttrValue.length() != 1) {
        throw new InvalidXmlAttributeException(node.getTagName(),
            separatorAttr, separatorAttrValue);
      }
      separator = separatorAttrValue.charAt(0);
    }
    // get the text data of the first Text child of node
    String resultStr = null;
    final NodeList childrenList = node.getChildNodes();
    final int childrenCount = childrenList.getLength();
    for (int i = 0; i < childrenCount; ++i) {
      final Node child = childrenList.item(i);
      if (child.getNodeType() != Node.TEXT_NODE) {
        // skip non Text children
        continue;
      }
      final Text textChild = (Text) child;
      if (resultStr == null) {
        resultStr = textChild.getData();
      } else {
        // there was a Text child before this child,
        // join the text with the separator
        resultStr += separator;
        resultStr += textChild.getData();
      }
      // continue to get the next Text child
    }
    if (resultStr == null) {
      throw new InvalidXmlNodeStructException(node);
    }
    if (strip) {
      resultStr = StringUtils.strip(resultStr);
    }
    if ((! allowEmpty) && (resultStr.length() == 0)) {
      throw new EmptyXmlNodeException(node.getTagName());
    }
    return StringUtils.split(resultStr, separator, strip, ignoreEmpty, result);
  }

  public static boolean getOptBooleanChild(final Element node,
      final String childName, final boolean defaultValue) throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptBoolean(child, defaultValue);
  }

  public static Boolean getOptBooleanChild(final Element node,
      final String childName, @Nullable final Boolean defaultValue)
          throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptBoolean(child, defaultValue);
  }

  public static boolean getReqBooleanChild(final Element node,
      final String childName, @Nullable final String prevSpaceAttr,
      final boolean defaultTrim, final boolean allowEmpty) throws XmlException {
    final Element child = getReqChild(node, childName);
    return getReqBoolean(child);
  }

  public static char getOptCharChild(final Element node,
      final String childName, final char defaultValue) throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptChar(child, defaultValue);
  }

  public static Character getOptCharChild(final Element node,
      final String childName, @Nullable final Character defaultValue)
          throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptChar(child, defaultValue);
  }

  public static char getReqCharChild(final Element node, final String childName)
      throws XmlException {
    final Element child = getReqChild(node, childName);
    return getReqChar(child);
  }

  public static byte getOptByteChild(final Element node,
      final String childName, final byte defaultValue) throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptByte(child, defaultValue);
  }

  public static Byte getOptByteChild(final Element node,
      final String childName, @Nullable final Byte defaultValue)
          throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptByte(child, defaultValue);
  }

  public static byte getReqByteChild(final Element node, final String childName)
      throws XmlException {
    final Element child = getReqChild(node, childName);
    return getReqByte(child);
  }

  public static short getOptShortChild(final Element node,
      final String childName, final short defaultValue) throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptShort(child, defaultValue);
  }

  public static Short getOptShortChild(final Element node,
      final String childName, @Nullable final Short defaultValue)
          throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptShort(child, defaultValue);
  }

  public static short getReqShortChild(final Element node,
      final String childName) throws XmlException {
    final Element child = getReqChild(node, childName);
    return getReqShort(child);
  }

  public static int getOptIntChild(final Element node, final String childName,
      final int defaultValue) throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptInt(child, defaultValue);
  }

  public static Integer getOptIntChild(final Element node,
      final String childName, @Nullable final Integer defaultValue)
          throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptInt(child, defaultValue);
  }

  public static int getReqIntChild(final Element node, final String childName)
      throws XmlException {
    final Element child = getReqChild(node, childName);
    return getReqInt(child);
  }

  public static long getOptLongChild(final Element node,
      final String childName, final long defaultValue) throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptLong(child, defaultValue);
  }

  public static Long getOptLongChild(final Element node,
      final String childName, @Nullable final Long defaultValue)
          throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptLong(child, defaultValue);
  }

  public static long getReqLongChild(final Element node, final String childName)
      throws XmlException {
    final Element child = getReqChild(node, childName);
    return getReqLong(child);
  }

  public static float getOptFloatChild(final Element node,
      final String childName, final float defaultValue) throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptFloat(child, defaultValue);
  }

  public static Float getOptFloatChild(final Element node,
      final String childName, @Nullable final Float defaultValue)
          throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptFloat(child, defaultValue);
  }

  public static float getReqFloatChild(final Element node,
      final String childName) throws XmlException {
    final Element child = getReqChild(node, childName);
    return getReqFloat(child);
  }

  public static double getOptDoubleChild(final Element node,
      final String childName, final double defaultValue) throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptDouble(child, defaultValue);
  }

  public static Double getOptDoubleChild(final Element node,
      final String childName, @Nullable final Double defaultValue)
          throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptDouble(child, defaultValue);
  }

  public static double getReqDoubleChild(final Element node,
      final String childName) throws XmlException {
    final Element child = getReqChild(node, childName);
    return getReqDouble(child);
  }

  public static String getOptStringChild(final Element node,
      final String childName, @Nullable final String prevSpaceAttr,
      final boolean defaultTrim, @Nullable final String defaultValue)
          throws XmlException {
    final Element child = getOptChild(node, childName);
    //  if the child exist but has no children, we must returns an empty string
    if ((child != null) && (! child.hasChildNodes())) {
      return StringUtils.EMPTY;
    } else {
      return getOptString(child, prevSpaceAttr, defaultTrim, defaultValue);
    }
  }

  public static String getReqStringChild(final Element node,
      final String childName, @Nullable final String prevSpaceAttr,
      final boolean defaultTrim, final boolean allowEmpty) throws XmlException {
    final Element child = getReqChild(node, childName);
    return getReqString(child, prevSpaceAttr, defaultTrim, allowEmpty);
  }

  public static Date getOptDateChild(final Element node,
      final String childName, final String formatPattern,
      @Nullable final Date defaultValue) throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptDate(child, formatPattern, defaultValue);
  }

  public static Date getReqDateChild(final Element node,
      final String childName, final String formatPattern) throws XmlException {
    final Element child = getReqChild(node, childName);
    return getReqDate(child, formatPattern);
  }

  public static byte[] getOptByteArrayChild(final Element node,
      final String childName, @Nullable final String separator,
      @Nullable final byte[] defaultValue) throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptByteArray(child, separator, defaultValue);
  }

  public static byte[] getReqByteArrayChild(final Element node,
      final String childName, @Nullable final String separator)
          throws XmlException {
    final Element child = getReqChild(node, childName);
    return getReqByteArray(child, separator);
  }

  public static Class<?> getOptClassChild(final Element node,
      final String childName, @Nullable final Class<?> defaultValue)
          throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptClass(child, defaultValue);
  }

  public static Class<?> getReqClassChild(final Element node,
      final String childName) throws XmlException {
    final Element child = getReqChild(node, childName);
    return getReqClass(child);
  }

  public static BigInteger getOptBigIntegerChild(final Element node,
      final String childName, @Nullable final BigInteger defaultValue)
          throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptBigInteger(child, defaultValue);
  }

  public static BigInteger getReqBigIntegerChild(final Element node,
      final String childName) throws XmlException {
    final Element child = getReqChild(node, childName);
    return getReqBigInteger(child);
  }

  public static BigDecimal getOptBigDecimalChild(final Element node,
      final String childName, @Nullable final BigDecimal defaultValue)
          throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptBigDecimal(child, defaultValue);
  }

  public static BigDecimal getReqBigDecimalChild(final Element node,
      final String childName) throws XmlException {
    final Element child = getReqChild(node, childName);
    return getReqBigDecimal(child);
  }

  public static <T extends Enum<T>> T getOptEnumChild(final Element node,
      final String childName, final boolean useShortName,
      final Class<T> enumClass, @Nullable final T defaultValue)
          throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptEnum(child, useShortName, enumClass, defaultValue);
  }

  public static <T extends Enum<T>> T getReqEnumChild(final Element node,
      final String childName, final boolean useShortName,
      final Class<T> enumClass) throws XmlException {
    final Element child = getReqChild(node, childName);
    return getReqEnum(child, useShortName, enumClass);
  }

  public static <T> T getOptSerChild(final Element node,
      final Class<T> objClass, final boolean allowConfig,
      @Nullable final String configAttr, @Nullable final T defaultValue)
          throws XmlException {
    final XmlSerializer serializer = XmlSerialization.getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final Element child = getOptChild(node, serializer.getRootNodeName());
    return getOptSer(child, objClass, serializer, allowConfig, configAttr,
        defaultValue);
  }

  public static <T> T getReqSerChild(final Element node,
      final Class<T> objClass, final boolean allowConfig,
      @Nullable final String configAttr) throws XmlException {
    final XmlSerializer serializer = XmlSerialization.getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final Element child = getReqChild(node, serializer.getRootNodeName());
    return getReqSer(child, objClass, serializer, allowConfig, configAttr);
  }

  public static <T> T getOptPolySerChild(final Element node,
      final String childName, @Nullable final String classAttr,
      final boolean allowConfig, @Nullable final String configAttr,
      @Nullable final T defaultValue) throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptPolySer(child, classAttr, allowConfig, configAttr,
        defaultValue);
  }

  public static <T> T getReqPolySerChild(final Element node,
      final String childName, @Nullable final String classAttr,
      final boolean allowConfig, @Nullable final String configAttr)
          throws XmlException {
    final Element child = getReqChild(node, childName);
    return getReqPolySer(child, classAttr, allowConfig, configAttr);
  }

  public static <T> T getOptInstanceChild(final Element node,
      final String childName, @Nullable final T defaultValue)
          throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptInstance(child, defaultValue);
  }

  public static <T> T getReqInstanceChild(final Element node,
      final String childName) throws XmlException {
    final Element child = getReqChild(node, childName);
    return getReqInstance(child);
  }

  public static List<String> getOptStringsChild(final Element node,
      final String childName, @Nullable final String prevSpaceAttr,
      final boolean defaultTrim, @Nullable final String separatorAttr,
      final char defaultSeparator, final boolean ignoreEmpty,
      @Nullable final List<String> result) throws XmlException {
    final Element child = getOptChild(node, childName);
    return getOptStrings(child, prevSpaceAttr, defaultTrim, separatorAttr,
        defaultSeparator, ignoreEmpty, result);
  }

  public static List<String> getReqStringsChild(final Element node,
      final String childName, @Nullable final String prevSpaceAttr,
      final boolean defaultTrim, final boolean allowEmpty,
      @Nullable final String separatorAttr, final char defaultSeparator,
      final boolean ignoreEmpty, @Nullable final List<String> result)
          throws XmlException {
    final Element child = getReqChild(node, childName);
    return getReqStrings(child, prevSpaceAttr, defaultTrim, allowEmpty,
        separatorAttr, defaultSeparator, ignoreEmpty, result);
  }

  public static BooleanList getOptBooleanChildren(final Element node,
      final String childName, @Nullable final BooleanList result)
          throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    return getOptBooleanList(children, result);
  }

  public static BooleanList getReqBooleanChildren(final Element node,
      final String childName, final int minCount, final int maxCount,
      @Nullable BooleanList result) throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    result = getReqBooleanList(children, result);
    final int count = (result == null ? 0 : result.size());
    if ((count < minCount) || ((maxCount >= 0) && (count > maxCount))) {
      throw new InvalidXmlChildrenCountException(node.getTagName(), childName,
          minCount, maxCount, count);
    }
    return result;
  }

  public static CharList getOptCharChildren(final Element node,
      final String childName, @Nullable final CharList result)
          throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    return getOptCharList(children, result);
  }

  public static CharList getReqCharChildren(final Element node,
      final String childName, final int minCount, final int maxCount,
      @Nullable CharList result) throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    result = getReqCharList(children, result);
    final int count = (result == null ? 0 : result.size());
    if ((count < minCount) || ((maxCount >= 0) && (count > maxCount))) {
      throw new InvalidXmlChildrenCountException(node.getTagName(), childName,
          minCount, maxCount, count);
    }
    return result;
  }

  public static ByteList getOptByteChildren(final Element node,
      final String childName, @Nullable final ByteList result)
          throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    return getOptByteList(children, result);
  }

  public static ByteList getReqByteChildren(final Element node,
      final String childName, final int minCount, final int maxCount,
      @Nullable ByteList result) throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    result = getReqByteList(children, result);
    final int count = (result == null ? 0 : result.size());
    if ((count < minCount) || ((maxCount >= 0) && (count > maxCount))) {
      throw new InvalidXmlChildrenCountException(node.getTagName(), childName,
          minCount, maxCount, count);
    }
    return result;
  }

  public static ShortList getOptShortChildren(final Element node,
      final String childName, @Nullable final ShortList result)
          throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    return getOptShortList(children, result);
  }

  public static ShortList getReqShortChildren(final Element node,
      final String childName, final int minCount, final int maxCount,
      @Nullable ShortList result) throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    result = getReqShortList(children, result);
    final int count = (result == null ? 0 : result.size());
    if ((count < minCount) || ((maxCount >= 0) && (count > maxCount))) {
      throw new InvalidXmlChildrenCountException(node.getTagName(), childName,
          minCount, maxCount, count);
    }
    return result;
  }

  public static IntList getOptIntChildren(final Element node,
      final String childName, @Nullable final IntList result)
          throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    return getOptIntList(children, result);
  }

  public static IntList getReqIntChildren(final Element node,
      final String childName, final int minCount, final int maxCount,
      @Nullable IntList result) throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    result = getReqIntList(children, result);
    final int count = (result == null ? 0 : result.size());
    if ((count < minCount) || ((maxCount >= 0) && (count > maxCount))) {
      throw new InvalidXmlChildrenCountException(node.getTagName(), childName,
          minCount, maxCount, count);
    }
    return result;
  }

  public static LongList getOptLongChildren(final Element node,
      final String childName, @Nullable final LongList result)
          throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    return getOptLongList(children, result);
  }

  public static LongList getReqLongChildren(final Element node,
      final String childName, final int minCount, final int maxCount,
      @Nullable LongList result) throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    result = getReqLongList(children, result);
    final int count = (result == null ? 0 : result.size());
    if ((count < minCount) || ((maxCount >= 0) && (count > maxCount))) {
      throw new InvalidXmlChildrenCountException(node.getTagName(), childName,
          minCount, maxCount, count);
    }
    return result;
  }

  public static FloatList getOptFloatChildren(final Element node,
      final String childName, @Nullable final FloatList result)
          throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    return getOptFloatList(children, result);
  }

  public static FloatList getReqFloatChildren(final Element node,
      final String childName, final int minCount, final int maxCount,
      @Nullable FloatList result) throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    result = getReqFloatList(children, result);
    final int count = (result == null ? 0 : result.size());
    if ((count < minCount) || ((maxCount >= 0) && (count > maxCount))) {
      throw new InvalidXmlChildrenCountException(node.getTagName(), childName,
          minCount, maxCount, count);
    }
    return result;
  }

  public static DoubleList getOptDoubleChildren(final Element node,
      final String childName, @Nullable final DoubleList result)
          throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    return getOptDoubleList(children, result);
  }

  public static DoubleList getReqDoubleChildren(final Element node,
      final String childName, final int minCount, final int maxCount,
      @Nullable DoubleList result) throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    result = getReqDoubleList(children, result);
    final int count = (result == null ? 0 : result.size());
    if ((count < minCount) || ((maxCount >= 0) && (count > maxCount))) {
      throw new InvalidXmlChildrenCountException(node.getTagName(), childName,
          minCount, maxCount, count);
    }
    return result;
  }

  public static List<String> getOptStringChildren(final Element node,
      final String childName, @Nullable final String prevSpaceAttr,
      final boolean defaultTrim, final boolean ignoreEmpty,
      @Nullable final List<String> result) throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    return getOptStringList(children, prevSpaceAttr, defaultTrim, ignoreEmpty,
        result);
  }

  public static List<String> getReqStringChildren(final Element node,
      final String childName, @Nullable final String prevSpaceAttr,
      final boolean defaultTrim, final boolean ignoreEmpty, final int minCount,
      final int maxCount, @Nullable List<String> result) throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    result = getReqStringList(children, prevSpaceAttr, defaultTrim,
        ignoreEmpty, result);
    final int count = (result == null ? 0 : result.size());
    if ((count < minCount) || ((maxCount >= 0) && (count > maxCount))) {
      throw new InvalidXmlChildrenCountException(node.getTagName(), childName,
          minCount, maxCount, count);
    }
    return result;
  }

  public static List<Date> getOptDateChildren(final Element node,
      final String childName, final String formatPattern,
      @Nullable final List<Date> result) throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    return getOptDateList(children, formatPattern, result);
  }

  public static List<Date> getReqDateChildren(final Element node,
      final String childName, final String formatPattern, final int minCount,
      final int maxCount, @Nullable List<Date> result) throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    result = getReqDateList(children, formatPattern, result);
    final int count = (result == null ? 0 : result.size());
    if ((count < minCount) || ((maxCount >= 0) && (count > maxCount))) {
      throw new InvalidXmlChildrenCountException(node.getTagName(), childName,
          minCount, maxCount, count);
    }
    return result;
  }

  public static List<byte[]> getOptByteArrayChildren(final Element node,
      final String childName, @Nullable final String separator,
      @Nullable final List<byte[]> result) throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    return getOptByteArrayList(children, separator, result);
  }

  public static List<byte[]> getReqByteArrayChildren(final Element node,
      final String childName, @Nullable final String separator,
      final int minCount, final int maxCount, @Nullable List<byte[]> result)
          throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    result = getReqByteArrayList(children, separator, result);
    final int count = (result == null ? 0 : result.size());
    if ((count < minCount) || ((maxCount >= 0) && (count > maxCount))) {
      throw new InvalidXmlChildrenCountException(node.getTagName(), childName,
          minCount, maxCount, count);
    }
    return result;
  }

  public static List<BigInteger> getOptBigIntegerChildren(final Element node,
      final String childName, @Nullable final List<BigInteger> result)
          throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    return getOptBigIntegerList(children, result);
  }

  public static List<BigInteger> getReqBigIntegerChildren(final Element node,
      final String childName, final int minCount, final int maxCount,
      @Nullable List<BigInteger> result) throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    result = getReqBigIntegerList(children, result);
    final int count = (result == null ? 0 : result.size());
    if ((count < minCount) || ((maxCount >= 0) && (count > maxCount))) {
      throw new InvalidXmlChildrenCountException(node.getTagName(), childName,
          minCount, maxCount, count);
    }
    return result;
  }

  public static List<BigDecimal> getOptBigDecimalChildren(final Element node,
      final String childName, @Nullable final List<BigDecimal> result)
          throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    return getOptBigDecimalList(children, result);
  }

  public static List<BigDecimal> getReqBigDecimalChildren(final Element node,
      final String childName, final int minCount, final int maxCount,
      @Nullable List<BigDecimal> result) throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    result = getReqBigDecimalList(children, result);
    final int count = (result == null ? 0 : result.size());
    if ((count < minCount) || ((maxCount >= 0) && (count > maxCount))) {
      throw new InvalidXmlChildrenCountException(node.getTagName(), childName,
          minCount, maxCount, count);
    }
    return result;
  }

  public static <T extends Enum<T>> List<T> getOptEnumChildren(
      final Element node, final String childName, final boolean useShortName,
      final Class<T> enumClass, @Nullable final List<T> result)
          throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    return getOptEnumList(children, useShortName, enumClass, result);
  }

  public static <T extends Enum<T>> List<T> getReqEnumChildren(
      final Element node, final String childName, final int minCount,
      final int maxCount, final boolean useShortName, final Class<T> enumClass,
      @Nullable List<T> result) throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    result = getReqEnumList(children, useShortName, enumClass, result);
    final int count = (result == null ? 0 : result.size());
    if ((count < minCount) || ((maxCount >= 0) && (count > maxCount))) {
      throw new InvalidXmlChildrenCountException(node.getTagName(), childName,
          minCount, maxCount, count);
    }
    return result;
  }

  public static List<Class<?>> getOptClassChildren(final Element node,
      final String childName, @Nullable final List<Class<?>> result)
          throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    return getOptClassList(children, result);
  }

  public static List<Class<?>> getReqClassChildren(final Element node,
      final String childName, final int minCount, final int maxCount,
      @Nullable List<Class<?>> result) throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    result = getReqClassList(children, result);
    final int count = (result == null ? 0 : result.size());
    if ((count < minCount) || ((maxCount >= 0) && (count > maxCount))) {
      throw new InvalidXmlChildrenCountException(node.getTagName(), childName,
          minCount, maxCount, count);
    }
    return result;
  }

  public static <T> List<T> getOptSerChildren(final Element node,
      final Class<T> objClass, final boolean allowConfig,
      @Nullable final String configAttr, @Nullable final List<T> result)
          throws XmlException {
    final XmlSerializer serializer = XmlSerialization.getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final String childName = serializer.getRootNodeName();
    final List<Element> children = getChildren(node, childName, null);
    return getSerList(children, objClass, serializer, allowConfig, configAttr,
        result);
  }

  public static <T> List<T> getReqSerChildren(final Element node,
      final int minCount, final int maxCount, final Class<T> objClass,
      final boolean allowConfig, @Nullable final String configAttr,
      @Nullable List<T> result) throws XmlException {
    final XmlSerializer serializer = XmlSerialization.getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final String childName = serializer.getRootNodeName();
    final List<Element> children = getChildren(node, childName, null);
    result = getSerList(children, objClass, serializer, allowConfig,
        configAttr, result);
    final int count = (result == null ? 0 : result.size());
    if ((count < minCount) || ((maxCount >= 0) && (count > maxCount))) {
      throw new InvalidXmlChildrenCountException(node.getTagName(),
          serializer.getRootNodeName(), minCount, maxCount, count);
    }
    return result;
  }

  public static <T> List<T> getOptPolySerChildren(final Element node,
      final String childName, @Nullable final String classAttr,
      final boolean allowConfig, @Nullable final String configAttr,
      @Nullable final List<T> result) throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    return getPolySerList(children, classAttr, allowConfig, configAttr, result);
  }

  public static <T> List<T> getReqPolySerChildren(final Element node,
      final String childName, final int minCount, final int maxCount,
      final String classAttr, final boolean allowConfig,
      @Nullable final String configAttr, @Nullable List<T> result)
          throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    result = getPolySerList(children, classAttr, allowConfig, configAttr,
        result);
    final int count = (result == null ? 0 : result.size());
    if ((count < minCount) || ((maxCount >= 0) && (count > maxCount))) {
      throw new InvalidXmlChildrenCountException(node.getTagName(), childName,
          minCount, maxCount, count);
    }
    return result;
  }

  public static <T> List<T> getOptInstanceChildren(final Element node,
      final String childName, @Nullable final List<T> result)
          throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    return getOptInstanceList(children, result);
  }

  public static <T> List<T> getReqInstanceChildren(final Element node,
      final String childName, final int minCount, final int maxCount,
      @Nullable List<T> result) throws XmlException {
    final List<Element> children = getChildren(node, childName, null);
    result = getReqInstanceList(children, result);
    final int count = (result == null ? 0 : result.size());
    if ((count < minCount) || ((maxCount >= 0) && (count > maxCount))) {
      throw new InvalidXmlChildrenCountException(node.getTagName(), childName,
          minCount, maxCount, count);
    }
    return result;
  }

  public static <T> List<T> getOptSerGrandchildren(final Element node,
      final String childName, final Class<T> objClass,
      final boolean allowConfig, @Nullable final String configAttr,
      @Nullable final List<T> result) throws XmlException {
    if (result != null) {
      result.clear();
    }
    final Element child = getFirstChild(node, childName);
    if (child == null) {
      return result;
    }
    final XmlSerializer serializer = XmlSerialization.getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final String grandchildName = serializer.getRootNodeName();
    final List<Element> grandchildren = getChildren(child, grandchildName, null);
    return getSerList(grandchildren, objClass, serializer, allowConfig,
        configAttr, result);
  }

  public static <T> List<T> getReqSerGrandchildren(final Element node,
      final String childName, final int minCount, final int maxCount,
      final Class<T> objClass, final boolean allowConfig,
      @Nullable final String configAttr, @Nullable List<T> result)
          throws XmlException {
    if (result != null) {
      result.clear();
    }
    final Element child = getFirstChild(node, childName);
    if (child == null) {
      if (minCount > 0) {
        throw new InvalidXmlChildrenCountException(node.getTagName(),
            childName, minCount, maxCount, 0);
      }
      return result;
    }
    final XmlSerializer serializer = XmlSerialization.getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final String grandchildName = serializer.getRootNodeName();
    final List<Element> grandchildren = getChildren(child, grandchildName, null);
    result = getSerList(grandchildren, objClass, serializer, allowConfig,
        configAttr, result);
    final int count = (result == null ? 0 : result.size());
    if ((count < minCount) || ((maxCount >= 0) && (count > maxCount))) {
      throw new InvalidXmlChildrenCountException(childName,
          serializer.getRootNodeName(), minCount, maxCount, count);
    }
    return result;
  }

  public static <T> List<T> getOptPolySerGrandchildren(final Element node,
      final String childName, final String grandchildName,
      @Nullable final String classAttr, final boolean allowConfig,
      @Nullable final String configAttr, @Nullable final List<T> result)
          throws XmlException {
    if (result != null) {
      result.clear();
    }
    final Element child = getFirstChild(node, childName);
    if (child == null) {
      return result;
    }
    final List<Element> grandchildren = getChildren(child, grandchildName, null);
    return getPolySerList(grandchildren, classAttr, allowConfig, configAttr,
        result);
  }

  public static <T> List<T> getReqPolySerGrandchildren(final Element node,
      final String childName, final String grandchildName, final int minCount,
      final int maxCount, final String classAttr, final boolean allowConfig,
      @Nullable final String configAttr, @Nullable List<T> result)
          throws XmlException {
    if (result != null) {
      result.clear();
    }
    final Element child = getFirstChild(node, childName);
    if (child == null) {
      if (minCount > 0) {
        throw new InvalidXmlChildrenCountException(node.getTagName(),
            childName, minCount, maxCount, 0);
      }
      return result;
    }
    final List<Element> grandchildren = getChildren(child, grandchildName, null);
    result = getPolySerList(grandchildren, classAttr, allowConfig, configAttr,
        result);
    final int count = (result == null ? 0 : result.size());
    if ((count < minCount) || ((maxCount >= 0) && (count > maxCount))) {
      throw new InvalidXmlChildrenCountException(node.getTagName(), childName,
          minCount, maxCount, count);
    }
    return result;
  }

  public static void setOptBooleanAttr(final Element node,
      final String attrName, final boolean value, final boolean defaultValue) {
    if (value != defaultValue) {
      node.setAttribute(attrName, BooleanUtils.toString(value));
    }
  }

  public static void setOptBooleanAttr(final Element node,
      final String attrName, @Nullable final Boolean value,
      @Nullable final Boolean defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      node.setAttribute(attrName, BooleanUtils.toString(value.booleanValue()));
    }
  }

  public static void setOptBooleanAttr(final Element node,
      final String attrName, @Nullable final Boolean value) {
    if (value != null) {
      node.setAttribute(attrName, BooleanUtils.toString(value.booleanValue()));
    }
  }

  public static void setReqBooleanAttr(final Element node,
      final String attrName, final boolean value) {
    node.setAttribute(attrName, BooleanUtils.toString(value));
  }

  public static void setReqBooleanAttr(final Element node,
      final String attrName, final Boolean value) {
    node.setAttribute(attrName, BooleanUtils.toString(value.booleanValue()));
  }

  public static void setOptCharAttr(final Element node, final String attrName,
      final char value, final char defaultValue) {
    if (value != defaultValue) {
      node.setAttribute(attrName, CharUtils.toString(value));
    }
  }

  public static void setOptCharAttr(final Element node, final String attrName,
      @Nullable final Character value, @Nullable final Character defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      node.setAttribute(attrName, CharUtils.toString(value.charValue()));
    }
  }

  public static void setOptCharAttr(final Element node, final String attrName,
      @Nullable final Character value) {
    if (value != null) {
      node.setAttribute(attrName, CharUtils.toString(value.charValue()));
    }
  }

  public static void setReqCharAttr(final Element node, final String attrName,
      final char value) {
    node.setAttribute(attrName, CharUtils.toString(value));
  }

  public static void setReqCharAttr(final Element node, final String attrName,
      final Character value) {
    node.setAttribute(attrName, CharUtils.toString(value.charValue()));
  }

  public static void setOptByteAttr(final Element node, final String attrName,
      final byte value, final byte defaultValue) {
    if (value != defaultValue) {
      node.setAttribute(attrName, ByteUtils.toString(value));
    }
  }

  public static void setOptByteAttr(final Element node, final String attrName,
      @Nullable final Byte value, @Nullable final Byte defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      node.setAttribute(attrName, ByteUtils.toString(value.byteValue()));
    }
  }

  public static void setOptByteAttr(final Element node, final String attrName,
      @Nullable final Byte value) {
    if (value != null) {
      node.setAttribute(attrName, ByteUtils.toString(value.byteValue()));
    }
  }

  public static void setReqByteAttr(final Element node, final String attrName,
      final byte value) {
    node.setAttribute(attrName, ByteUtils.toString(value));
  }

  public static void setReqByteAttr(final Element node, final String attrName,
      final Byte value) {
    node.setAttribute(attrName, ByteUtils.toString(value.byteValue()));
  }

  public static void setOptShortAttr(final Element node, final String attrName,
      final short value, final short defaultValue) {
    if (value != defaultValue) {
      node.setAttribute(attrName, ShortUtils.toString(value));
    }
  }

  public static void setOptShortAttr(final Element node, final String attrName,
      @Nullable final Short value, @Nullable final Short defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      node.setAttribute(attrName, ShortUtils.toString(value.shortValue()));
    }
  }

  public static void setOptShortAttr(final Element node, final String attrName,
      @Nullable final Short value) {
    if (value != null) {
      node.setAttribute(attrName, ShortUtils.toString(value.shortValue()));
    }
  }

  public static void setReqShortAttr(final Element node, final String attrName,
      final short value) {
    node.setAttribute(attrName, ShortUtils.toString(value));
  }

  public static void setReqShortAttr(final Element node, final String attrName,
      final Short value) {
    node.setAttribute(attrName, ShortUtils.toString(value.shortValue()));
  }

  public static void setOptIntAttr(final Element node, final String attrName,
      final int value, final int defaultValue) {
    if (value != defaultValue) {
      node.setAttribute(attrName, IntUtils.toString(value));
    }
  }

  public static void setOptIntAttr(final Element node, final String attrName,
      @Nullable final Integer value, @Nullable final Integer defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      node.setAttribute(attrName, IntUtils.toString(value.intValue()));
    }
  }

  public static void setOptIntAttr(final Element node, final String attrName,
      @Nullable final Integer value) {
    if (value != null) {
      node.setAttribute(attrName, IntUtils.toString(value.intValue()));
    }
  }

  public static void setReqIntAttr(final Element node, final String attrName,
      final int value) {
    node.setAttribute(attrName, IntUtils.toString(value));
  }

  public static void setReqIntAttr(final Element node, final String attrName,
      final Integer value) {
    node.setAttribute(attrName, IntUtils.toString(value.intValue()));
  }

  public static void setOptLongAttr(final Element node, final String attrName,
      final long value, final long defaultValue) {
    if (value != defaultValue) {
      node.setAttribute(attrName, LongUtils.toString(value));
    }
  }

  public static void setOptLongAttr(final Element node, final String attrName,
      @Nullable final Long value, @Nullable final Long defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      node.setAttribute(attrName, LongUtils.toString(value.longValue()));
    }
  }

  public static void setOptLongAttr(final Element node, final String attrName,
      @Nullable final Long value) {
    if (value != null) {
      node.setAttribute(attrName, LongUtils.toString(value.longValue()));
    }
  }

  public static void setReqLongAttr(final Element node, final String attrName,
      final long value) {
    node.setAttribute(attrName, LongUtils.toString(value));
  }

  public static void setReqLongAttr(final Element node, final String attrName,
      final Long value) {
    node.setAttribute(attrName, LongUtils.toString(value.longValue()));
  }

  public static void setOptFloatAttr(final Element node, final String attrName,
      final float value, final float defaultValue) {
    if (! Equality.equals(value, defaultValue)) {
      node.setAttribute(attrName, FloatUtils.toString(value));
    }
  }

  public static void setOptFloatAttr(final Element node, final String attrName,
      @Nullable final Float value, @Nullable final Float defaultValue) {
    if ((value != null) && (! Equality.equals(value, defaultValue))) {
      node.setAttribute(attrName, FloatUtils.toString(value.floatValue()));
    }
  }

  public static void setOptFloatAttr(final Element node, final String attrName,
      @Nullable final Float value) {
    if (value != null) {
      node.setAttribute(attrName, FloatUtils.toString(value.floatValue()));
    }
  }

  public static void setReqFloatAttr(final Element node, final String attrName,
      final float value) {
    node.setAttribute(attrName, FloatUtils.toString(value));
  }

  public static void setReqFloatAttr(final Element node, final String attrName,
      final Float value) {
    node.setAttribute(attrName, FloatUtils.toString(value.floatValue()));
  }

  public static void setOptDoubleAttr(final Element node,
      final String attrName, final double value, final double defaultValue) {
    if (! Equality.equals(value, defaultValue)) {
      node.setAttribute(attrName, DoubleUtils.toString(value));
    }
  }

  public static void setOptDoubleAttr(final Element node,
      final String attrName, @Nullable final Double value,
      @Nullable final Double defaultValue) {
    if ((value != null) && (! Equality.equals(value, defaultValue))) {
      node.setAttribute(attrName, DoubleUtils.toString(value.doubleValue()));
    }
  }

  public static void setOptDoubleAttr(final Element node,
      final String attrName, @Nullable final Double value) {
    if (value != null) {
      node.setAttribute(attrName, DoubleUtils.toString(value.doubleValue()));
    }
  }

  public static void setReqDoubleAttr(final Element node,
      final String attrName, final double value) {
    node.setAttribute(attrName, DoubleUtils.toString(value));
  }

  public static void setReqDoubleAttr(final Element node,
      final String attrName, final Double value) {
    node.setAttribute(attrName, DoubleUtils.toString(value.doubleValue()));
  }

  public static void setOptStringAttr(final Element node,
      final String attrName, @Nullable final String value,
      @Nullable final String defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      node.setAttribute(attrName, value);
    }
  }

  public static void setOptStringAttr(final Element node,
      final String attrName, @Nullable final String value) {
    if (value != null) {
      node.setAttribute(attrName, value);
    }
  }

  public static void setReqStringAttr(final Element node,
      final String attrName, final String value) {
    node.setAttribute(attrName, value);
  }

  public static void setOptDateAttr(final Element node, final String attrName,
      @Nullable final Date value, @Nullable final Date defaultValue,
      final String pattern) {
    if ((value != null) && (! value.equals(defaultValue))) {
      final DateFormat df = new DateFormat(pattern);
      final String str = df.format(value);
      node.setAttribute(attrName, str);
    }
  }

  public static void setOptDateAttr(final Element node, final String attrName,
      @Nullable final Date value, final String pattern) {
    if (value != null) {
      final DateFormat df = new DateFormat(pattern);
      final String str = df.format(value);
      node.setAttribute(attrName, str);
    }
  }

  public static void setReqDateAttr(final Element node, final String attrName,
      final Date value, final String pattern) {
    final DateFormat df = new DateFormat(pattern);
    final String str = df.format(value);
    node.setAttribute(attrName, str);
  }

  public static void setOptByteArrayAttr(final Element node,
      final String attrName, @Nullable final byte[] value,
      @Nullable final byte[] defaultValue) {
    if ((value != null) && (! Equality.equals(value, defaultValue))) {
      node.setAttribute(attrName, ByteArrayUtils.toString(value));
    }
  }

  public static void setOptByteArrayAttr(final Element node,
      final String attrName, @Nullable final byte[] value) {
    if (value != null) {
      node.setAttribute(attrName, ByteArrayUtils.toString(value));
    }
  }

  public static void setReqByteArrayAttr(final Element node,
      final String attrName, final byte[] value) {
    node.setAttribute(attrName, ByteArrayUtils.toString(value));
  }

  public static void setOptClassAttr(final Element node, final String attrName,
      @Nullable final Class<?> value, @Nullable final Class<?> defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      node.setAttribute(attrName, value.getName());
    }
  }

  public static void setOptClassAttr(final Element node, final String attrName,
      @Nullable final Class<?> value) {
    if (value != null) {
      node.setAttribute(attrName, value.getName());
    }
  }

  public static void setReqClassAttr(final Element node, final String attrName,
      final Class<?> value) {
    node.setAttribute(attrName, value.getName());
  }

  public static void setOptBigIntegerAttr(final Element node,
      final String attrName, @Nullable final BigInteger value,
      @Nullable final BigInteger defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      node.setAttribute(attrName, BigIntegerUtils.toString(value));
    }
  }

  public static void setOptBigIntegerAttr(final Element node,
      final String attrName, @Nullable final BigInteger value) {
    if (value != null) {
      node.setAttribute(attrName, BigIntegerUtils.toString(value));
    }
  }

  public static void setReqBigIntegerAttr(final Element node,
      final String attrName, final BigInteger value) {
    node.setAttribute(attrName, BigIntegerUtils.toString(value));
  }

  public static void setOptBigDecimalAttr(final Element node,
      final String attrName, @Nullable final BigDecimal value,
      @Nullable final BigDecimal defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      node.setAttribute(attrName, BigDecimalUtils.toString(value));
    }
  }

  public static void setOptBigDecimalAttr(final Element node,
      final String attrName, @Nullable final BigDecimal value) {
    if (value != null) {
      node.setAttribute(attrName, BigDecimalUtils.toString(value));
    }
  }

  public static void setReqBigDecimalAttr(final Element node,
      final String attrName, final BigDecimal value) {
    node.setAttribute(attrName, BigDecimalUtils.toString(value));
  }

  public static <T extends Enum<T>> void setOptEnumAttr(final Element node,
      final String attrName, final boolean useShortName,
      @Nullable final T value, @Nullable final T defaultValue) {
    if ((value != null) && (value != defaultValue)) {
      final String name = (useShortName ? EnumUtils.getShortName(value)
          : value.name());
      node.setAttribute(attrName, name);
    }
  }

  public static <T extends Enum<T>> void setOptEnumAttr(final Element node,
      final String attrName, final boolean useShortName, @Nullable final T value) {
    if (value != null) {
      final String name = (useShortName ? EnumUtils.getShortName(value)
          : value.name());
      node.setAttribute(attrName, name);
    }
  }

  public static <T extends Enum<T>> void setReqEnumAttr(final Element node,
      final String attrName, final boolean useShortName, final T value) {
    final String name = (useShortName ? EnumUtils.getShortName(value)
        : value.name());
    node.setAttribute(attrName, name);
  }

  public static <T> void setOptObjectClassAttr(final Element node,
      final String attrName, @Nullable final T value,
      @Nullable final T defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      node.setAttribute(attrName, value.getClass().getName());
    }
  }

  public static <T> void setOptObjectClassAttr(final Element node,
      final String attrName, @Nullable final T value) {
    if (value != null) {
      node.setAttribute(attrName, value.getClass().getName());
    }
  }

  public static <T> void setReqObjectClassAttr(final Element node,
      final String attrName, final T value) {
    node.setAttribute(attrName, value.getClass().getName());
  }

  public static void appendOptBooleanChild(final Document doc,
      final Element parent, final String childName, final boolean value,
      final boolean defaultValue) {
    if (value != defaultValue) {
      final Element node = doc.createElement(childName);
      final String str = BooleanUtils.toString(value);
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendReqBooleanChild(final Document doc,
      final Element parent, final String childName, final boolean value) {
    final Element node = doc.createElement(childName);
    final String str = BooleanUtils.toString(value);
    node.setTextContent(str);
    parent.appendChild(node);
  }

  public static void appendOptBooleanChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final Boolean value) {
    if (value != null) {
      final Element node = doc.createElement(childName);
      final String str = BooleanUtils.toString(value.booleanValue());
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendOptBooleanChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final Boolean value, @Nullable final Boolean defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      final Element node = doc.createElement(childName);
      final String str = BooleanUtils.toString(value.booleanValue());
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendReqBooleanChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final Boolean value) {
    final Element node = doc.createElement(childName);
    if (value != null) {
      final String str = BooleanUtils.toString(value.booleanValue());
      node.setTextContent(str);
    }
    parent.appendChild(node);
  }

  public static void appendOptCharChild(final Document doc,
      final Element parent, final String childName, final char value,
      final char defaultValue) {
    if (value != defaultValue) {
      final Element node = doc.createElement(childName);
      final String str = CharUtils.toString(value);
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendReqCharChild(final Document doc,
      final Element parent, final String childName, final char value) {
    final Element node = doc.createElement(childName);
    final String str = CharUtils.toString(value);
    node.setTextContent(str);
    parent.appendChild(node);
  }

  public static void appendOptCharChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final Character value) {
    if (value != null) {
      final Element node = doc.createElement(childName);
      final String str = CharUtils.toString(value.charValue());
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendOptCharChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final Character value, @Nullable final Character defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      final Element node = doc.createElement(childName);
      final String str = CharUtils.toString(value.charValue());
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendReqCharChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final Character value) {
    final Element node = doc.createElement(childName);
    if (value != null) {
      final String str = CharUtils.toString(value.charValue());
      node.setTextContent(str);
    }
    parent.appendChild(node);
  }

  public static void appendOptByteChild(final Document doc,
      final Element parent, final String childName, final byte value,
      final byte defaultValue) {
    if (value != defaultValue) {
      final Element node = doc.createElement(childName);
      final String str = ByteUtils.toString(value);
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendReqByteChild(final Document doc,
      final Element parent, final String childName, final byte value) {
    final Element node = doc.createElement(childName);
    final String str = ByteUtils.toString(value);
    node.setTextContent(str);
    parent.appendChild(node);
  }

  public static void appendOptByteChild(final Document doc,
      final Element parent, final String childName, @Nullable final Byte value) {
    if (value != null) {
      final Element node = doc.createElement(childName);
      final String str = ByteUtils.toString(value.byteValue());
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendOptByteChild(final Document doc,
      final Element parent, final String childName, @Nullable final Byte value,
      @Nullable final Byte defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      final Element node = doc.createElement(childName);
      final String str = ByteUtils.toString(value.byteValue());
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendReqByteChild(final Document doc,
      final Element parent, final String childName, @Nullable final Byte value) {
    final Element node = doc.createElement(childName);
    if (value != null) {
      final String str = ByteUtils.toString(value.byteValue());
      node.setTextContent(str);
    }
    parent.appendChild(node);
  }

  public static void appendOptShortChild(final Document doc,
      final Element parent, final String childName, final short value,
      final short defaultValue) {
    if (value != defaultValue) {
      final Element node = doc.createElement(childName);
      final String str = ShortUtils.toString(value);
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendReqShortChild(final Document doc,
      final Element parent, final String childName, final short value) {
    final Element node = doc.createElement(childName);
    final String str = ShortUtils.toString(value);
    node.setTextContent(str);
    parent.appendChild(node);
  }

  public static void appendOptShortChild(final Document doc,
      final Element parent, final String childName, @Nullable final Short value) {
    if (value != null) {
      final Element node = doc.createElement(childName);
      final String str = ShortUtils.toString(value.shortValue());
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendOptShortChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final Short value, @Nullable final Short defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      final Element node = doc.createElement(childName);
      final String str = ShortUtils.toString(value.shortValue());
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendReqShortChild(final Document doc,
      final Element parent, final String childName, @Nullable final Short value) {
    final Element node = doc.createElement(childName);
    if (value != null) {
      final String str = ShortUtils.toString(value.shortValue());
      node.setTextContent(str);
    }
    parent.appendChild(node);
  }

  public static void appendOptIntChild(final Document doc,
      final Element parent, final String childName, final int value,
      final int defaultValue) {
    if (value != defaultValue) {
      final Element node = doc.createElement(childName);
      final String str = IntUtils.toString(value);
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendReqIntChild(final Document doc,
      final Element parent, final String childName, final int value) {
    final Element node = doc.createElement(childName);
    final String str = IntUtils.toString(value);
    node.setTextContent(str);
    parent.appendChild(node);
  }

  public static void appendOptIntChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final Integer value) {
    if (value != null) {
      final Element node = doc.createElement(childName);
      final String str = IntUtils.toString(value.intValue());
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendOptIntChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final Integer value, @Nullable final Integer defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      final Element node = doc.createElement(childName);
      final String str = IntUtils.toString(value.intValue());
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendReqIntChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final Integer value) {
    final Element node = doc.createElement(childName);
    if (value != null) {
      final String str = IntUtils.toString(value.intValue());
      node.setTextContent(str);
    }
    parent.appendChild(node);
  }

  public static void appendOptLongChild(final Document doc,
      final Element parent, final String childName, final long value,
      final long defaultValue) {
    if (value != defaultValue) {
      final Element node = doc.createElement(childName);
      final String str = LongUtils.toString(value);
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendReqLongChild(final Document doc,
      final Element parent, final String childName, final long value) {
    final Element node = doc.createElement(childName);
    final String str = LongUtils.toString(value);
    node.setTextContent(str);
    parent.appendChild(node);
  }

  public static void appendOptLongChild(final Document doc,
      final Element parent, final String childName, @Nullable final Long value) {
    if (value != null) {
      final Element node = doc.createElement(childName);
      final String str = LongUtils.toString(value.longValue());
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendOptLongChild(final Document doc,
      final Element parent, final String childName, @Nullable final Long value,
      @Nullable final Long defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      final Element node = doc.createElement(childName);
      final String str = LongUtils.toString(value.longValue());
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendReqLongChild(final Document doc,
      final Element parent, final String childName, @Nullable final Long value) {
    final Element node = doc.createElement(childName);
    if (value != null) {
      final String str = LongUtils.toString(value.longValue());
      node.setTextContent(str);
    }
    parent.appendChild(node);
  }

  public static void appendOptFloatChild(final Document doc,
      final Element parent, final String childName, final float value,
      final float defaultValue) {
    if (! Equality.equals(value, defaultValue)) {
      final Element node = doc.createElement(childName);
      final String str = FloatUtils.toString(value);
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendReqFloatChild(final Document doc,
      final Element parent, final String childName, final float value) {
    final Element node = doc.createElement(childName);
    final String str = FloatUtils.toString(value);
    node.setTextContent(str);
    parent.appendChild(node);
  }

  public static void appendOptFloatChild(final Document doc,
      final Element parent, final String childName, @Nullable final Float value) {
    if (value != null) {
      final Element node = doc.createElement(childName);
      final String str = FloatUtils.toString(value.floatValue());
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendOptFloatChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final Float value, @Nullable final Float defaultValue) {
    if ((value != null) && (! Equality.equals(value, defaultValue))) {
      final Element node = doc.createElement(childName);
      final String str = FloatUtils.toString(value.floatValue());
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendReqFloatChild(final Document doc,
      final Element parent, final String childName, @Nullable final Float value) {
    final Element node = doc.createElement(childName);
    if (value != null) {
      final String str = FloatUtils.toString(value.floatValue());
      node.setTextContent(str);
    }
    parent.appendChild(node);
  }

  public static void appendOptDoubleChild(final Document doc,
      final Element parent, final String childName, final double value,
      final double defaultValue) {
    if (! Equality.equals(value, defaultValue)) {
      final Element node = doc.createElement(childName);
      final String str = DoubleUtils.toString(value);
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendReqDoubleChild(final Document doc,
      final Element parent, final String childName, final double value) {
    final Element node = doc.createElement(childName);
    final String str = DoubleUtils.toString(value);
    node.setTextContent(str);
    parent.appendChild(node);
  }

  public static void appendOptDoubleChild(final Document doc,
      final Element parent, final String childName, @Nullable final Double value) {
    if (value != null) {
      final Element node = doc.createElement(childName);
      final String str = DoubleUtils.toString(value.doubleValue());
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendOptDoubleChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final Double value, @Nullable final Double defaultValue) {
    if ((value != null) && (! Equality.equals(value, defaultValue))) {
      final Element node = doc.createElement(childName);
      final String str = DoubleUtils.toString(value.doubleValue());
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendReqDoubleChild(final Document doc,
      final Element parent, final String childName, @Nullable final Double value) {
    final Element node = doc.createElement(childName);
    if (value != null) {
      final String str = DoubleUtils.toString(value.doubleValue());
      node.setTextContent(str);
    }
    parent.appendChild(node);
  }

  public static void appendOptStringChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final String prevSpaceAttr, @Nullable final String value) {
    if (value != null) {
      final Element node = StringUtils.toXmlNode(doc, childName, prevSpaceAttr,
          value);
      parent.appendChild(node);
    }
  }

  public static void appendOptStringChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final String prevSpaceAttr, @Nullable final String value,
      @Nullable final String defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      final Element node = StringUtils.toXmlNode(doc, childName, prevSpaceAttr,
          value);
      parent.appendChild(node);
    }
  }

  public static void appendReqStringChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final String prevSpaceAttr, final String value) {
    final Element node = StringUtils.toXmlNode(doc, childName, prevSpaceAttr,
        value);
    parent.appendChild(node);
  }

  public static void appendOptDateChild(final Document doc,
      final Element parent, final String childName, final String pattern,
      @Nullable final Date value) {
    if (value != null) {
      final Element node = doc.createElement(childName);
      final DateFormat df = new DateFormat(pattern);
      final String str = df.format(value);
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendOptDateChild(final Document doc,
      final Element parent, final String childName, final String pattern,
      @Nullable final Date value, @Nullable final Date defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      final Element node = doc.createElement(childName);
      final DateFormat df = new DateFormat(pattern);
      final String str = df.format(value);
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendReqDateChild(final Document doc,
      final Element parent, final String childName, final String pattern,
      final Date value) {
    final Element node = doc.createElement(childName);
    if (value != null) {
      final DateFormat df = new DateFormat(pattern);
      final String str = df.format(value);
      node.setTextContent(str);
    }
    parent.appendChild(node);
  }

  public static void appendOptByteArrayChild(final Document doc,
      final Element parent, final String childName, @Nullable final byte[] value) {
    if (value != null) {
      final Element node = doc.createElement(childName);
      final String str = ByteArrayUtils.toString(value);
      doc.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendOptByteArrayChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final byte[] value, @Nullable final byte[] defaultValue) {
    if ((value != null) && (! Equality.equals(value, defaultValue))) {
      final Element node = doc.createElement(childName);
      final String str = ByteArrayUtils.toString(value);
      doc.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendReqByteArrayChild(final Document doc,
      final Element parent, final String childName, final byte[] value) {
    final Element node = doc.createElement(childName);
    final String str = ByteArrayUtils.toString(value);
    doc.setTextContent(str);
    parent.appendChild(node);
  }

  public static void appendOptClassChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final Class<?> value) {
    if (value != null) {
      final Element node = doc.createElement(childName);
      doc.setTextContent(value.getName());
      parent.appendChild(node);
    }
  }

  public static void appendOptClassChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final Class<?> value, @Nullable final Class<?> defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      final Element node = doc.createElement(childName);
      doc.setTextContent(value.getName());
      parent.appendChild(node);
    }
  }

  public static void appendReqClassChild(final Document doc,
      final Element parent, final String childName, final Class<?> value) {
    final Element node = doc.createElement(childName);
    doc.setTextContent(value.getName());
    parent.appendChild(node);
  }

  public static void appendOptBigIntegerChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final BigInteger value) {
    if (value != null) {
      final Element node = doc.createElement(childName);
      final String str = BigIntegerUtils.toString(value);
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendOptBigIntegerChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final BigInteger value, @Nullable final BigInteger defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      final Element node = doc.createElement(childName);
      final String str = BigIntegerUtils.toString(value);
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendReqBigIntegerChild(final Document doc,
      final Element parent, final String childName, final BigInteger value) {
    final Element node = doc.createElement(childName);
    final String str = BigIntegerUtils.toString(value);
    node.setTextContent(str);
    parent.appendChild(node);
  }

  public static void appendOptBigDecimalChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final BigDecimal value) {
    if (value != null) {
      final Element node = doc.createElement(childName);
      final String str = BigDecimalUtils.toString(value);
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendOptClassChild(final Document doc,
      final Element parent, final String childName,
      @Nullable final BigDecimal value, @Nullable final BigDecimal defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      final Element node = doc.createElement(childName);
      final String str = BigDecimalUtils.toString(value);
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static void appendReqBigDecimalChild(final Document doc,
      final Element parent, final String childName, final BigDecimal value) {
    final Element node = doc.createElement(childName);
    final String str = BigDecimalUtils.toString(value);
    node.setTextContent(str);
    parent.appendChild(node);
  }

  public static <T extends Enum<T>> void appendOptEnumChild(final Document doc,
      final Element parent, final String childName, final boolean useShortName,
      @Nullable final T value) {
    if (value != null) {
      final Element node = doc.createElement(childName);
      final String str = EnumUtils.toString(value, useShortName);
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static <T extends Enum<T>> void appendOptEnumChild(final Document doc,
      final Element parent, final String childName, final boolean useShortName,
      @Nullable final T value, @Nullable final T defaultValue) {
    if ((value != null) && (value != defaultValue)) {
      final Element node = doc.createElement(childName);
      final String str = EnumUtils.toString(value, useShortName);
      node.setTextContent(str);
      parent.appendChild(node);
    }
  }

  public static <T extends Enum<T>> void appendReqEnumChild(final Document doc,
      final Element parent, final String childName, final boolean useShortName,
      final T value) {
    final Element node = doc.createElement(childName);
    final String str = EnumUtils.toString(value, useShortName);
    node.setTextContent(str);
    parent.appendChild(node);
  }

  public static <T> void appendOptSerChild(final Document doc,
      final Element parent, final Class<T> valueClass, @Nullable final T value)
          throws XmlException {
    if (value != null) {
      final XmlSerializer serializer = XmlSerialization.getSerializer(valueClass);
      if (serializer == null) {
        throw new NoXmlSerializerRegisteredException(valueClass);
      }
      final Element node = serializer.serialize(doc, value);
      parent.appendChild(node);
    }
  }

  public static <T> void appendOptSerChild(final Document doc,
      final Element parent, final Class<T> valueClass, @Nullable final T value,
      @Nullable final T defaultValue) throws XmlException {
    if ((value != null) && (! value.equals(defaultValue))) {
      final XmlSerializer serializer = XmlSerialization.getSerializer(valueClass);
      if (serializer == null) {
        throw new NoXmlSerializerRegisteredException(valueClass);
      }
      final Element node = serializer.serialize(doc, value);
      parent.appendChild(node);
    }
  }

  public static <T> void appendReqSerChild(final Document doc,
      final Element parent, final Class<T> valueClass, final T value)
          throws XmlException {
    final XmlSerializer serializer = XmlSerialization.getSerializer(valueClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(valueClass);
    }
    final Element node = serializer.serialize(doc, value);
    parent.appendChild(node);
  }

  public static <T> void appendOptPolySerChild(final Document doc,
      final Element parent, @Nullable final T value) throws XmlException {
    if (value != null) {
      final Class<?> valueClass = value.getClass();
      final XmlSerializer serializer = XmlSerialization.getSerializer(valueClass);
      if (serializer == null) {
        throw new NoXmlSerializerRegisteredException(valueClass);
      }
      final Element node = serializer.serialize(doc, value);
      parent.appendChild(node);
    }
  }

  public static <T> void appendOptPolySerChild(final Document doc,
      final Element parent, @Nullable final T value,
      @Nullable final T defaultValue) throws XmlException {
    if ((value != null) && (! value.equals(defaultValue))) {
      final Class<?> valueClass = value.getClass();
      final XmlSerializer serializer = XmlSerialization.getSerializer(valueClass);
      if (serializer == null) {
        throw new NoXmlSerializerRegisteredException(valueClass);
      }
      final Element node = serializer.serialize(doc, value);
      parent.appendChild(node);
    }
  }

  public static <T> void appendReqPolySerChild(final Document doc,
      final Element parent, final T value) throws XmlException {
    final Class<?> valueClass = value.getClass();
    final XmlSerializer serializer = XmlSerialization.getSerializer(valueClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(valueClass);
    }
    final Element node = serializer.serialize(doc, value);
    parent.appendChild(node);
  }

  public static <T> void appendOptObjectClassChild(final Document doc,
      final Element parent, final String childName, @Nullable final T value) {
    if (value != null) {
      final Element node = doc.createElement(childName);
      node.setTextContent(value.getClass().getName());
      parent.appendChild(node);
    }
  }

  public static <T> void appendOptObjectClassChild(final Document doc,
      final Element parent, final String childName, @Nullable final T value,
      @Nullable final T defaultValue) {
    if ((value != null) && (! value.equals(defaultValue))) {
      final Element node = doc.createElement(childName);
      node.setTextContent(value.getClass().getName());
      parent.appendChild(node);
    }
  }

  public static <T> void appendReqObjectClassChild(final Document doc,
      final Element parent, final String childName, final T value) {
    final Element node = doc.createElement(childName);
    node.setTextContent(value.getClass().getName());
    parent.appendChild(node);
  }

  public static void appendBooleanChildren(final Document doc,
      final Element parent, @Nullable final String containerName,
      final String childName, @Nullable final BooleanCollection values) {
    Element container = parent;
    if (containerName != null) {
      container = doc.createElement(containerName);
      parent.appendChild(container);
    }
    if ((values == null) || values.isEmpty()) {
      return;
    }
    final BooleanIterator iter = values.iterator();
    while (iter.hasNext()) {
      final boolean value = iter.next();
      final Element node = doc.createElement(childName);
      final String str = BooleanUtils.toString(value);
      node.setTextContent(str);
      container.appendChild(node);
    }
  }

  public static void appendCharChildren(final Document doc,
      final Element parent, @Nullable final String containerName,
      final String childName, @Nullable final CharCollection values) {
    Element container = parent;
    if (containerName != null) {
      container = doc.createElement(containerName);
      parent.appendChild(container);
    }
    if ((values == null) || values.isEmpty()) {
      return;
    }
    final CharIterator iter = values.iterator();
    while (iter.hasNext()) {
      final char value = iter.next();
      final Element node = doc.createElement(childName);
      final String str = CharUtils.toString(value);
      node.setTextContent(str);
      container.appendChild(node);
    }
  }

  public static void appendByteChildren(final Document doc,
      final Element parent, @Nullable final String containerName,
      final String childName, @Nullable final ByteCollection values) {
    Element container = parent;
    if (containerName != null) {
      container = doc.createElement(containerName);
      parent.appendChild(container);
    }
    if ((values == null) || values.isEmpty()) {
      return;
    }
    final ByteIterator iter = values.iterator();
    while (iter.hasNext()) {
      final byte value = iter.next();
      final Element node = doc.createElement(childName);
      final String str = ByteUtils.toString(value);
      node.setTextContent(str);
      container.appendChild(node);
    }
  }

  public static void appendShortChildren(final Document doc,
      final Element parent, @Nullable final String containerName,
      final String childName, @Nullable final ShortCollection values) {
    Element container = parent;
    if (containerName != null) {
      container = doc.createElement(containerName);
      parent.appendChild(container);
    }
    if ((values == null) || values.isEmpty()) {
      return;
    }
    final ShortIterator iter = values.iterator();
    while (iter.hasNext()) {
      final short value = iter.next();
      final Element node = doc.createElement(childName);
      final String str = ShortUtils.toString(value);
      node.setTextContent(str);
      container.appendChild(node);
    }
  }

  public static void appendIntChildren(final Document doc,
      final Element parent, @Nullable final String containerName,
      final String childName, @Nullable final IntCollection values) {
    Element container = parent;
    if (containerName != null) {
      container = doc.createElement(containerName);
      parent.appendChild(container);
    }
    if ((values == null) || values.isEmpty()) {
      return;
    }
    final IntIterator iter = values.iterator();
    while (iter.hasNext()) {
      final int value = iter.next();
      final Element node = doc.createElement(childName);
      final String str = IntUtils.toString(value);
      node.setTextContent(str);
      container.appendChild(node);
    }
  }

  public static void appendLongChildren(final Document doc,
      final Element parent, @Nullable final String containerName,
      final String childName, @Nullable final LongCollection values) {
    Element container = parent;
    if (containerName != null) {
      container = doc.createElement(containerName);
      parent.appendChild(container);
    }
    if ((values == null) || values.isEmpty()) {
      return;
    }
    final LongIterator iter = values.iterator();
    while (iter.hasNext()) {
      final long value = iter.next();
      final Element node = doc.createElement(childName);
      final String str = LongUtils.toString(value);
      node.setTextContent(str);
      container.appendChild(node);
    }
  }

  public static void appendFloatChildren(final Document doc,
      final Element parent, @Nullable final String containerName,
      final String childName, @Nullable final FloatCollection values) {
    Element container = parent;
    if (containerName != null) {
      container = doc.createElement(containerName);
      parent.appendChild(container);
    }
    if ((values == null) || values.isEmpty()) {
      return;
    }
    final FloatIterator iter = values.iterator();
    while (iter.hasNext()) {
      final float value = iter.next();
      final Element node = doc.createElement(childName);
      final String str = FloatUtils.toString(value);
      node.setTextContent(str);
      container.appendChild(node);
    }
  }

  public static void appendDoubleChildren(final Document doc,
      final Element parent, @Nullable final String containerName,
      final String childName, @Nullable final DoubleCollection values) {
    Element container = parent;
    if (containerName != null) {
      container = doc.createElement(containerName);
      parent.appendChild(container);
    }
    if ((values == null) || values.isEmpty()) {
      return;
    }
    final DoubleIterator iter = values.iterator();
    while (iter.hasNext()) {
      final double value = iter.next();
      final Element node = doc.createElement(childName);
      final String str = DoubleUtils.toString(value);
      node.setTextContent(str);
      container.appendChild(node);
    }
  }

  public static void appendStringChildren(final Document doc,
      final Element parent, @Nullable final String containerName,
      final String childName, @Nullable final String prevSpaceAttr,
      @Nullable final List<String> values) {
    Element container = parent;
    if (containerName != null) {
      container = doc.createElement(containerName);
      parent.appendChild(container);
    }
    if ((values == null) || values.isEmpty()) {
      return;
    }
    for (final String value : values) {
      if (value == null) {
        continue;
      }
      final Element node = StringUtils.toXmlNode(doc, childName, prevSpaceAttr,
          value);
      container.appendChild(node);
    }
  }

  public static void appendDateChildren(final Document doc,
      final Element parent, @Nullable final String containerName,
      final String childName, final String pattern,
      @Nullable final Collection<Date> values) {
    Element container = parent;
    if (containerName != null) {
      container = doc.createElement(containerName);
      parent.appendChild(container);
    }
    if ((values == null) || values.isEmpty()) {
      return;
    }
    final DateFormat df = new DateFormat(pattern);
    for (final Date value : values) {
      if (value == null) {
        continue;
      }
      final Element node = doc.createElement(childName);
      final String str = df.format(value);
      node.setTextContent(str);
      container.appendChild(node);
    }
  }

  public static void appendByteArrayChildren(final Document doc,
      final Element parent, @Nullable final String containerName,
      final String childName, @Nullable final Collection<byte[]> values) {
    Element container = parent;
    if (containerName != null) {
      container = doc.createElement(containerName);
      parent.appendChild(container);
    }
    if ((values == null) || values.isEmpty()) {
      return;
    }
    for (final byte[] value : values) {
      if (value == null) {
        continue;
      }
      final Element node = doc.createElement(childName);
      final String str = ByteArrayUtils.toString(value);
      doc.setTextContent(str);
      container.appendChild(node);
    }
  }

  public static void appendClassChildren(final Document doc,
      final Element parent, @Nullable final String containerName,
      final String childName, @Nullable final Collection<Class<?>> values) {
    Element container = parent;
    if (containerName != null) {
      container = doc.createElement(containerName);
      parent.appendChild(container);
    }
    if ((values == null) || values.isEmpty()) {
      return;
    }
    for (final Class<?> value : values) {
      if (value == null) {
        continue;
      }
      final Element node = doc.createElement(childName);
      doc.setTextContent(value.getName());
      container.appendChild(node);
    }
  }

  public static void appendBigIntegerChildren(final Document doc,
      final Element parent, @Nullable final String containerName,
      final String childName, @Nullable final Collection<BigInteger> values) {
    Element container = parent;
    if (containerName != null) {
      container = doc.createElement(containerName);
      parent.appendChild(container);
    }
    if ((values == null) || values.isEmpty()) {
      return;
    }
    for (final BigInteger value : values) {
      if (value == null) {
        continue;
      }
      final Element node = doc.createElement(childName);
      final String str = BigIntegerUtils.toString(value);
      node.setTextContent(str);
      container.appendChild(node);
    }
  }

  public static void appendBigDecimalChildren(final Document doc,
      final Element parent, @Nullable final String containerName,
      final String childName, @Nullable final Collection<BigDecimal> values) {
    Element container = parent;
    if (containerName != null) {
      container = doc.createElement(containerName);
      parent.appendChild(container);
    }
    if ((values == null) || values.isEmpty()) {
      return;
    }
    for (final BigDecimal value : values) {
      if (value == null) {
        continue;
      }
      final Element node = doc.createElement(childName);
      final String str = BigDecimalUtils.toString(value);
      node.setTextContent(str);
      container.appendChild(node);
    }
  }

  public static <T extends Enum<T>> void appendEnumChildren(final Document doc,
      final Element parent, @Nullable final String containerName,
      final String childName, final boolean useShortName,
      @Nullable final Collection<T> values) {
    Element container = parent;
    if (containerName != null) {
      container = doc.createElement(containerName);
      parent.appendChild(container);
    }
    if ((values == null) || values.isEmpty()) {
      return;
    }
    for (final T value : values) {
      if (value == null) {
        continue;
      }
      final Element node = doc.createElement(childName);
      final String str = EnumUtils.toString(value, useShortName);
      node.setTextContent(str);
      container.appendChild(node);
    }
  }

  public static <T> void appendSerChildren(final Document doc,
      final Element parent, @Nullable final String containerName,
      final Class<T> valueClass, @Nullable final Collection<T> values)
          throws XmlException {
    Element container = parent;
    if (containerName != null) {
      container = doc.createElement(containerName);
      parent.appendChild(container);
    }
    if ((values == null) || values.isEmpty()) {
      return;
    }
    final XmlSerializer serializer = XmlSerialization.getSerializer(valueClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(valueClass);
    }
    for (final T value : values) {
      if (value == null) {
        continue;
      }
      final Element node = serializer.serialize(doc, value);
      container.appendChild(node);
    }
  }

  public static <T> void appendPolySerChildren(final Document doc,
      final Element parent, @Nullable final String containerName,
      @Nullable final Collection<T> values) throws XmlException {
    Element container = parent;
    if (containerName != null) {
      container = doc.createElement(containerName);
      parent.appendChild(container);
    }
    if ((values == null) || values.isEmpty()) {
      return;
    }
    for (final T value : values) {
      if (value == null) {
        continue;
      }
      final Class<?> valueClass = value.getClass();
      final XmlSerializer serializer = XmlSerialization.getSerializer(valueClass);
      if (serializer == null) {
        throw new NoXmlSerializerRegisteredException(valueClass);
      }
      final Element node = serializer.serialize(doc, value);
      container.appendChild(node);
    }
  }

  public static <T> void appendObjectClassChildren(final Document doc,
      final Element parent, @Nullable final String containerName,
      final String childName, @Nullable final Collection<T> values) {
    Element container = parent;
    if (containerName != null) {
      container = doc.createElement(containerName);
      parent.appendChild(container);
    }
    if ((values == null) || values.isEmpty()) {
      return;
    }
    for (final T value : values) {
      if (value == null) {
        continue;
      }
      final Class<?> clazz = value.getClass();
      final Element node = doc.createElement(childName);
      doc.setTextContent(clazz.getName());
      container.appendChild(node);
    }
  }
}
