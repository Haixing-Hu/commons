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

package com.github.haixing_hu.util.value;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.NoSuchElementException;

import javax.annotation.Nullable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.collection.primitive.BooleanCollection;
import com.github.haixing_hu.collection.primitive.ByteCollection;
import com.github.haixing_hu.collection.primitive.CharCollection;
import com.github.haixing_hu.collection.primitive.DoubleCollection;
import com.github.haixing_hu.collection.primitive.FloatCollection;
import com.github.haixing_hu.collection.primitive.IntCollection;
import com.github.haixing_hu.collection.primitive.LongCollection;
import com.github.haixing_hu.collection.primitive.ShortCollection;
import com.github.haixing_hu.lang.Cloneable;
import com.github.haixing_hu.lang.Type;
import com.github.haixing_hu.lang.TypeConvertException;
import com.github.haixing_hu.lang.TypeMismatchException;
import com.github.haixing_hu.text.xml.XmlException;

/**
 * A {@link MultiValues} object represents one or more values of values of
 * common types.
 * <p>
 * Currently the {@link MultiValues} interface support the following types:
 * <ul>
 * <li><code>boolean</code></li>
 * <li><code>char</code></li>
 * <li><code>byte</code></li>
 * <li><code>short</code></li>
 * <li><code>int</code></li>
 * <li><code>long</code></li>
 * <li><code>float</code></li>
 * <li><code>double</code></li>
 * <li>{@link String}</li>
 * <li><code>java.util.Date</code></li>
 * <li><code>java.math.BigDecimal</code></li>
 * <li><code>java.math.BigInteger</code></li>
 * <li><code>byte[]</code></li>
 * <li><code>Class</code></li>
 * </ul>
 *
 * @author Haixing Hu
 */
public interface MultiValues extends Cloneable<MultiValues> {

  /**
   * The default type of the {@link MultiValues} objects.
   */
  public static final Type DEFAULT_TYPE = Type.STRING;

  /**
   * Gets the type of this object.
   *
   * @return the type of this object.
   */
  public Type getType();

  /**
   * Sets the type of this object.
   * <p>
   * NOTE: If the new type is the same as the old type of this object, nothing
   * is done; otherwise, the type of this object will be set to the new type,
   * and all old values of this object will be cleared.
   * </p>
   *
   * @param type
   *          the new type of this object.
   */
  public void setType(Type type);

  /**
   * Gets the number of values of this object.
   *
   * @return the number of values of this object.
   */
  public int getCount();

  /**
   * Tests whether this object has any value.
   *
   * @return true if this object has any value; false otherwise.
   */
  public boolean isEmpty();

  /**
   * Clears all the values of this object.
   */
  public void clear();

  /**
   * Unions the values and type of another {@link MultiValues} object to this
   * {@link MultiValues} object .
   *
   * @param other
   *          the other {@link MultiValues} object.
   */
  public void assignValues(MultiValues other);

  /**
   * Unions the values of two variants with the same type.
   *
   * @param other
   *          the other {@link MultiValues} object, which must have the same
   *          type as this {@link MultiValues} object.
   * @throws TypeMismatchException
   *           if the type of this {@link MultiValues} object is not the same as
   *           the type of the other {@link MultiValues object}.
   */
  public void unionValues(MultiValues other) throws TypeMismatchException;

  /**
   * Reads values from an input stream.
   *
   * @param type
   *          the type of values to be read.
   * @param n
   *          the number of values to be read.
   * @param in
   *          the input stream.
   * @throws IOException
   *           If any I/O error occurred.
   */
  public void readValues(Type type, int n, InputStream in) throws IOException;

  /**
   * Writes values to an output stream.
   * <p>
   * Note that this function does NOT write the type nor the value count to the
   * output stream.
   *
   * @param out
   *          the output stream.
   * @throws IOException
   *           If any I/O error occurred.
   */
  public void writeValues(OutputStream out) throws IOException;

  /**
   * Deserialize the values from the children of a DOM node.
   *
   * @param parent
   *          the parent DOM node.
   * @param tagName
   *          the tag name of the value nodes.
   * @param prevSpaceAttr
   *          the attribute name of the preserve space attribute. If it is null,
   *          no preserve space attribute is used.
   * @throws XmlException
   *           If any XML related error occurred.
   */
  public void getValuesFromXml(Type type, Element parent, String tagName,
      @Nullable String prevSpaceAttr) throws XmlException;

  /**
   * Serializes the values as children nodes of a DOM node.
   *
   * @param doc
   *          the DOM document.
   * @param parent
   *          the parent DOM node.
   * @param containerName
   *          the tag name of the container of the value nodes, which could be
   *          null. If it is null, no container is used.
   * @param tagName
   *          the tag name of value nodes.
   * @param prevSpaceAttr
   *          the attribute name of the preserve space attribute. If it is null,
   *          no preserve space attribute is used.
   * @throws XmlException
   *           If any XML related error occurred.
   */
  public void appendValuesToXml(Document doc, Element parent,
      @Nullable String containerName, String tagName,
      @Nullable String prevSpaceAttr) throws XmlException;

  /**
   * Gets the first value of this object as a <code>boolean</code> value.
   *
   * @return the first value of this object as a <code>boolean</code> value.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BOOLEAN}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public boolean getBooleanValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single <code>boolean</code> value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#BOOLEAN}, all previous values of this object is cleared, and
   * the new value is set to this object.
   * </p>
   *
   * @param value
   *          the new <code>boolean</code> value to be set to this object.
   */
  public void setBooleanValue(boolean value);

  /**
   * Gets the values of this object as a <code>boolean</code> value.
   *
   * @return the values of this object as a <code>boolean</code> array; or an
   *         empty array if this object has no value.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BOOLEAN}.
   */
  public boolean[] getBooleanValues() throws TypeMismatchException;

  /**
   * Sets <code>boolean</code> values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#BOOLEAN}, all previous values of this object is cleared, and
   * the new values is set to this object.
   * </p>
   *
   * @param values
   *          the array of new <code>boolean</code> values to be set to this
   *          object.
   */
  public void setBooleanValues(boolean... values);

  /**
   * Sets <code>boolean</code> values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#BOOLEAN}, all previous values of this object is cleared, and
   * the new values is set to this object.
   * </p>
   *
   * @param values
   *          the collection of new <code>boolean</code> values to be set to
   *          this object.
   */
  public void setBooleanValues(BooleanCollection values);

  /**
   * Adds a <code>boolean</code> value to this object.
   * <p>
   * If the type of this object is not {@link Type#BOOLEAN}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>boolean</code> value will be add to this object.
   * </p>
   *
   * @param value
   *          the <code>boolean</code> value to be add to this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BOOLEAN}.
   */
  public void addBooleanValue(boolean value) throws TypeMismatchException;

  /**
   * Adds <code>boolean</code> values to this object.
   * <p>
   * If the type of this object is not {@link Type#BOOLEAN}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>boolean</code> values will be add to this object.
   * </p>
   *
   * @param values
   *          the array of <code>boolean</code> values to be add to this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BOOLEAN}.
   */
  public void addBooleanValues(boolean... values) throws TypeMismatchException;

  /**
   * Adds <code>boolean</code> values to this object.
   * <p>
   * If the type of this object is not {@link Type#BOOLEAN}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>boolean</code> values will be add to this object.
   * </p>
   *
   * @param values
   *          the colleciton of <code>boolean</code> values to be add to this
   *          object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BOOLEAN}.
   */
  public void addBooleanValues(BooleanCollection values)
      throws TypeMismatchException;

  /**
   * Gets the first value of this object as a <code>char</code> value.
   *
   * @return the first value of this object as a <code>char</code> value.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#CHAR}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public char getCharValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single <code>char</code> value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#CHAR}, all previous values of this object is cleared, and the
   * new value is set to this object.
   * </p>
   *
   * @param value
   *          the new <code>char</code> value to be set to this object.
   */
  public void setCharValue(char value);

  /**
   * Gets the values of this object as a <code>char</code> value.
   *
   * @return the values of this object as a <code>char</code> array; or an empty
   *         array if this object has no value.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#CHAR}.
   */
  public char[] getCharValues() throws TypeMismatchException;

  /**
   * Sets <code>char</code> values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#CHAR}, all previous values of this object is cleared, and the
   * new values is set to this object.
   * </p>
   *
   * @param values
   *          the array of new <code>char</code> values to be set to this
   *          object.
   */
  public void setCharValues(char... values);

  /**
   * Sets <code>char</code> values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#CHAR}, all previous values of this object is cleared, and the
   * new values is set to this object.
   * </p>
   *
   * @param values
   *          the collection of new <code>char</code> values to be set to this
   *          object.
   */
  public void setCharValues(CharCollection values);

  /**
   * Adds a <code>char</code> value to this object.
   * <p>
   * If the type of this object is not {@link Type#CHAR}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>char</code> value will be add to this object.
   * </p>
   *
   * @param value
   *          the <code>char</code> value to be add to this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#CHAR} and there is
   *           old value in this object.
   */
  public void addCharValue(char value) throws TypeMismatchException;

  /**
   * Adds <code>char</code> values to this object.
   * <p>
   * If the type of this object is not {@link Type#CHAR}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>char</code> values will be add to this object.
   * </p>
   *
   * @param values
   *          the array of <code>char</code> values to be add to this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#CHAR} and there is
   *           old value in this object.
   */
  public void addCharValues(char... values) throws TypeMismatchException;

  /**
   * Adds <code>char</code> values to this object.
   * <p>
   * If the type of this object is not {@link Type#CHAR}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>char</code> values will be add to this object.
   * </p>
   *
   * @param values
   *          the collection of <code>char</code> values to be add to this
   *          object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#CHAR} and there is
   *           old value in this object.
   */
  public void addCharValues(CharCollection values) throws TypeMismatchException;

  /**
   * Gets the first value of this object as a <code>byte</code> value.
   *
   * @return the first value of this object as a <code>byte</code> value.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BYTE}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public byte getByteValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single <code>byte</code> value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#BYTE}, all previous values of this object is cleared, and the
   * new value is set to this object.
   * </p>
   *
   * @param value
   *          the new <code>byte</code> value to be set to this object.
   */
  public void setByteValue(byte value);

  /**
   * Gets the values of this object as a <code>byte</code> value.
   *
   * @return the values of this object as a <code>byte</code> array; or an empty
   *         array if this object has no value.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BYTE}.
   */
  public byte[] getByteValues() throws TypeMismatchException;

  /**
   * Sets <code>byte</code> values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#BYTE}, all previous values of this object is cleared, and the
   * new values is set to this object.
   * </p>
   *
   * @param values
   *          the array of new <code>byte</code> values to be set to this
   *          object.
   */
  public void setByteValues(byte... values);

  /**
   * Sets <code>byte</code> values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#BYTE}, all previous values of this object is cleared, and the
   * new values is set to this object.
   * </p>
   *
   * @param values
   *          the collection of new <code>byte</code> values to be set to this
   *          object.
   */
  public void setByteValues(ByteCollection values);

  /**
   * Adds a <code>byte</code> value to this object.
   * <p>
   * If the type of this object is not {@link Type#BYTE}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>byte</code> value will be add to this object.
   * </p>
   *
   * @param value
   *          the <code>byte</code> value to be add to this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BYTE} and there is
   *           old value in this object.
   */
  public void addByteValue(byte value) throws TypeMismatchException;

  /**
   * Adds <code>byte</code> values to this object.
   * <p>
   * If the type of this object is not {@link Type#BYTE}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>byte</code> values will be add to this object.
   * </p>
   *
   * @param values
   *          the array of <code>byte</code> values to be add to this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BYTE} and there is
   *           old value in this object.
   */
  public void addByteValues(byte... values) throws TypeMismatchException;

  /**
   * Adds <code>byte</code> values to this object.
   * <p>
   * If the type of this object is not {@link Type#BYTE}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>byte</code> values will be add to this object.
   * </p>
   *
   * @param values
   *          the collection of <code>byte</code> values to be add to this
   *          object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BYTE} and there is
   *           old value in this object.
   */
  public void addByteValues(ByteCollection values) throws TypeMismatchException;

  /**
   * Gets the first value of this object as a <code>short</code> value.
   *
   * @return the first value of this object as a <code>short</code> value.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#SHORT}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public short getShortValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single <code>short</code> value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#SHORT}, all previous values of this object is cleared, and the
   * new value is set to this object.
   * </p>
   *
   * @param value
   *          the new <code>short</code> value to be set to this object.
   */
  public void setShortValue(short value);

  /**
   * Gets the values of this object as a <code>short</code> value.
   *
   * @return the values of this object as a <code>short</code> array; or an
   *         empty array if this object has no value.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#SHORT}.
   */
  public short[] getShortValues() throws TypeMismatchException;

  /**
   * Sets <code>short</code> values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#SHORT}, all previous values of this object is cleared, and the
   * new values is set to this object.
   * </p>
   *
   * @param values
   *          the array of new <code>short</code> values to be set to this
   *          object.
   */
  public void setShortValues(short... values);

  /**
   * Sets <code>short</code> values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#SHORT}, all previous values of this object is cleared, and the
   * new values is set to this object.
   * </p>
   *
   * @param values
   *          the collection of new <code>short</code> values to be set to this
   *          object.
   */
  public void setShortValues(ShortCollection values);

  /**
   * Adds a <code>short</code> value to this object.
   * <p>
   * If the type of this object is not {@link Type#SHORT}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>short</code> value will be add to this object.
   * </p>
   *
   * @param value
   *          the <code>short</code> value to be add to this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#SHORT} and there is
   *           old value in this object.
   */
  public void addShortValue(short value) throws TypeMismatchException;

  /**
   * Adds <code>short</code> values to this object.
   * <p>
   * If the type of this object is not {@link Type#SHORT}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>short</code> values will be add to this object.
   * </p>
   *
   * @param values
   *          the array of <code>short</code> values to be add to this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#SHORT} and there is
   *           old value in this object.
   */
  public void addShortValues(short... values) throws TypeMismatchException;

  /**
   * Adds <code>short</code> values to this object.
   * <p>
   * If the type of this object is not {@link Type#SHORT}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>short</code> values will be add to this object.
   * </p>
   *
   * @param values
   *          the collection of <code>short</code> values to be add to this
   *          object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#SHORT} and there is
   *           old value in this object.
   */
  public void addShortValues(ShortCollection values)
      throws TypeMismatchException;

  /**
   * Gets the first value of this object as a <code>int</code> value.
   *
   * @return the first value of this object as a <code>int</code> value.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#INT}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public int getIntValue() throws TypeMismatchException, NoSuchElementException;

  /**
   * Sets a single <code>int</code> value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#INT}, all previous values of this object is cleared, and the
   * new value is set to this object.
   * </p>
   *
   * @param value
   *          the new <code>int</code> value to be set to this object.
   */
  public void setIntValue(int value);

  /**
   * Gets the values of this object as a <code>int</code> value.
   *
   * @return the values of this object as a <code>int</code> array; or an empty
   *         array if this object has no value.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#INT}.
   */
  public int[] getIntValues() throws TypeMismatchException;

  /**
   * Sets <code>int</code> values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#INT}, all previous values of this object is cleared, and the
   * new values is set to this object.
   * </p>
   *
   * @param values
   *          the array of new <code>int</code> values to be set to this object.
   */
  public void setIntValues(int... values);

  /**
   * Sets <code>int</code> values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#INT}, all previous values of this object is cleared, and the
   * new values is set to this object.
   * </p>
   *
   * @param values
   *          the collection of new <code>int</code> values to be set to this
   *          object.
   */
  public void setIntValues(IntCollection values);

  /**
   * Adds a <code>int</code> value to this object.
   * <p>
   * If the type of this object is not {@link Type#INT}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>int</code> value will be add to this object.
   * </p>
   *
   * @param value
   *          the <code>int</code> value to be add to this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#INT} and there is
   *           old value in this object.
   */
  public void addIntValue(int value) throws TypeMismatchException;

  /**
   * Adds <code>int</code> values to this object.
   * <p>
   * If the type of this object is not {@link Type#INT}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>int</code> values will be add to this object.
   * </p>
   *
   * @param values
   *          the array of <code>int</code> values to be add to this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#INT} and there is
   *           old value in this object.
   */
  public void addIntValues(int... values) throws TypeMismatchException;

  /**
   * Adds <code>int</code> values to this object.
   * <p>
   * If the type of this object is not {@link Type#INT}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>int</code> values will be add to this object.
   * </p>
   *
   * @param values
   *          the collection of <code>int</code> values to be add to this
   *          object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#INT} and there is
   *           old value in this object.
   */
  public void addIntValues(IntCollection values) throws TypeMismatchException;

  /**
   * Gets the first value of this object as a <code>long</code> value.
   *
   * @return the first value of this object as a <code>long</code> value.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#LONG}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public long getLongValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single <code>long</code> value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#LONG}, all previous values of this object is cleared, and the
   * new value is set to this object.
   * </p>
   *
   * @param value
   *          the new <code>long</code> value to be set to this object.
   */
  public void setLongValue(long value);

  /**
   * Gets the values of this object as a <code>long</code> value.
   *
   * @return the values of this object as a <code>long</code> array; or an empty
   *         array if this object has no value.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#LONG}.
   */
  public long[] getLongValues() throws TypeMismatchException;

  /**
   * Sets <code>long</code> values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#LONG}, all previous values of this object is cleared, and the
   * new values is set to this object.
   * </p>
   *
   * @param values
   *          the array of new <code>long</code> values to be set to this
   *          object.
   */
  public void setLongValues(long... values);

  /**
   * Sets <code>long</code> values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#LONG}, all previous values of this object is cleared, and the
   * new values is set to this object.
   * </p>
   *
   * @param values
   *          the collection of new <code>long</code> values to be set to this
   *          object.
   */
  public void setLongValues(LongCollection values);

  /**
   * Adds a <code>long</code> value to this object.
   * <p>
   * If the type of this object is not {@link Type#LONG}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>long</code> value will be add to this object.
   * </p>
   *
   * @param value
   *          the <code>long</code> value to be add to this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#LONG} and there is
   *           old value in this object.
   */
  public void addLongValue(long value) throws TypeMismatchException;

  /**
   * Adds <code>long</code> values to this object.
   * <p>
   * If the type of this object is not {@link Type#LONG}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>long</code> values will be add to this object.
   * </p>
   *
   * @param values
   *          the array of <code>long</code> values to be add to this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#LONG} and there is
   *           old value in this object.
   */
  public void addLongValues(long... values) throws TypeMismatchException;

  /**
   * Adds <code>long</code> values to this object.
   * <p>
   * If the type of this object is not {@link Type#LONG}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>long</code> values will be add to this object.
   * </p>
   *
   * @param values
   *          the collection of <code>long</code> values to be add to this
   *          object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#LONG} and there is
   *           old value in this object.
   */
  public void addLongValues(LongCollection values) throws TypeMismatchException;

  /**
   * Gets the first value of this object as a <code>float</code> value.
   *
   * @return the first value of this object as a <code>float</code> value.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#FLOAT}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public float getFloatValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single <code>float</code> value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#FLOAT}, all previous values of this object is cleared, and the
   * new value is set to this object.
   * </p>
   *
   * @param value
   *          the new <code>float</code> value to be set to this object.
   */
  public void setFloatValue(float value);

  /**
   * Gets the values of this object as a <code>float</code> value.
   *
   * @return the values of this object as a <code>float</code> array; or an
   *         empty array if this object has no value.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#FLOAT}.
   */
  public float[] getFloatValues() throws TypeMismatchException;

  /**
   * Sets <code>float</code> values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#FLOAT}, all previous values of this object is cleared, and the
   * new values is set to this object.
   * </p>
   *
   * @param values
   *          the array of new <code>float</code> values to be set to this
   *          object.
   */
  public void setFloatValues(float... values);

  /**
   * Sets <code>float</code> values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#FLOAT}, all previous values of this object is cleared, and the
   * new values is set to this object.
   * </p>
   *
   * @param values
   *          the collection of new <code>float</code> values to be set to this
   *          object.
   */
  public void setFloatValues(FloatCollection values);

  /**
   * Adds a <code>float</code> value to this object.
   * <p>
   * If the type of this object is not {@link Type#FLOAT}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>float</code> value will be add to this object.
   * </p>
   *
   * @param value
   *          the <code>float</code> value to be add to this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#FLOAT} and there is
   *           old value in this object.
   * @throws NullPrimitiveTypeObjectException
   *           if any <code>Boolean</code> object in the <code>values</code>
   *           collection is null.
   */
  public void addFloatValue(float value) throws TypeMismatchException;

  /**
   * Adds <code>float</code> values to this object.
   * <p>
   * If the type of this object is not {@link Type#FLOAT}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>float</code> values will be add to this object.
   * </p>
   *
   * @param values
   *          the array of <code>float</code> values to be add to this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#FLOAT} and there is
   *           old value in this object.
   */
  public void addFloatValues(float... values) throws TypeMismatchException;

  /**
   * Adds <code>float</code> values to this object.
   * <p>
   * If the type of this object is not {@link Type#FLOAT}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>float</code> values will be add to this object.
   * </p>
   *
   * @param values
   *          the collection of <code>float</code> values to be add to this
   *          object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#FLOAT} and there is
   *           old value in this object.
   */
  public void addFloatValues(FloatCollection values)
      throws TypeMismatchException;

  /**
   * Gets the first value of this object as a <code>double</code> value.
   *
   * @return the first value of this object as a <code>double</code> value.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#DOUBLE}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public double getDoubleValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single <code>double</code> value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#DOUBLE}, all previous values of this object is cleared, and the
   * new value is set to this object.
   * </p>
   *
   * @param value
   *          the new <code>double</code> value to be set to this object.
   */
  public void setDoubleValue(double value);

  /**
   * Gets the values of this object as a <code>double</code> value.
   *
   * @return the values of this object as a <code>double</code> array; or an
   *         empty array if this object has no value.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#DOUBLE}.
   */
  public double[] getDoubleValues() throws TypeMismatchException;

  /**
   * Sets <code>double</code> values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#DOUBLE}, all previous values of this object is cleared, and the
   * new values is set to this object.
   * </p>
   *
   * @param values
   *          the array of new <code>double</code> values to be set to this
   *          object.
   */
  public void setDoubleValues(double... values);

  /**
   * Sets <code>double</code> values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#DOUBLE}, all previous values of this object is cleared, and the
   * new values is set to this object.
   * </p>
   *
   * @param values
   *          the collection of new <code>double</code> values to be set to this
   *          object.
   */
  public void setDoubleValues(DoubleCollection values);

  /**
   * Adds a <code>double</code> value to this object.
   * <p>
   * If the type of this object is not {@link Type#DOUBLE}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>double</code> value will be add to this object.
   * </p>
   *
   * @param value
   *          the <code>double</code> value to be add to this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#DOUBLE} and there
   *           is old value in this object.
   */
  public void addDoubleValue(double value) throws TypeMismatchException;

  /**
   * Adds <code>double</code> values to this object.
   * <p>
   * If the type of this object is not {@link Type#DOUBLE}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>double</code> values will be add to this object.
   * </p>
   *
   * @param values
   *          the array of <code>double</code> values to be add to this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#DOUBLE} and there
   *           is old value in this object.
   */
  public void addDoubleValues(double... values) throws TypeMismatchException;

  /**
   * Adds <code>double</code> values to this object.
   * <p>
   * If the type of this object is not {@link Type#DOUBLE}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>double</code> values will be add to this object.
   * </p>
   *
   * @param values
   *          the collection of <code>double</code> values to be add to this
   *          object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#DOUBLE} and there
   *           is old value in this object.
   */
  public void addDoubleValues(DoubleCollection values)
      throws TypeMismatchException;

  /**
   * Gets the first value of this object as a {@link String} value.
   *
   * @return the first value of this object as a {@link String} value, which may
   *         be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#STRING}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public String getStringValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single {@link String} value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#STRING}, all previous values of this object is cleared, and the
   * new value is set to this object.
   * </p>
   *
   * @param value
   *          the new {@link String} value to be set to this object, which may
   *          be null.
   */
  public void setStringValue(@Nullable String value);

  /**
   * Gets the values of this object as a {@link String} value.
   *
   * @return the values of this object as a {@link String} array, whose elements
   *         may be null; or an empty array if this object has no value.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#STRING}.
   */
  public String[] getStringValues() throws TypeMismatchException;

  /**
   * Sets {@link String} values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#STRING}, all previous values of this object is cleared, and the
   * new values is set to this object.
   * </p>
   *
   * @param values
   *          the array of new {@link String} values to be set to this object,
   *          whose elements may be null.
   */
  public void setStringValues(String... values);

  /**
   * Sets {@link String} values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#STRING}, all previous values of this object is cleared, and the
   * new values is set to this object.
   * </p>
   *
   * @param values
   *          the collection of new {@link String} values to be set to this
   *          object, whose elements may be null.
   */
  public void setStringValues(Collection<String> values);

  /**
   * Adds a {@link String} value to this object.
   * <p>
   * If the type of this object is not {@link Type#STRING}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * {@link String} value will be add to this object.
   * </p>
   *
   * @param value
   *          the {@link String} value to be add to this object, which may be
   *          null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#STRING} and there
   *           is old value in this object.
   */
  public void addStringValue(@Nullable String value)
      throws TypeMismatchException;

  /**
   * Adds {@link String} values to this object.
   * <p>
   * If the type of this object is not {@link Type#STRING}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * {@link String} values will be add to this object.
   * </p>
   *
   * @param values
   *          the array of {@link String} values to be add to this object, whose
   *          elements may be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#STRING} and there
   *           is old value in this object.
   */
  public void addStringValues(String... values) throws TypeMismatchException;

  /**
   * Adds {@link String} values to this object.
   * <p>
   * If the type of this object is not {@link Type#STRING}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * {@link String} values will be add to this object.
   * </p>
   *
   * @param values
   *          the collection of {@link String} values to be add to this object,
   *          whose elements may be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#STRING} and there
   *           is old value in this object.
   */
  public void addStringValues(Collection<String> values)
      throws TypeMismatchException;

  /**
   * Gets the first value of this object as a {@link Date} value.
   *
   * @return the first value of this object as a {@link Date} value, which may
   *         be null. Note that the returned object is the cloned copy of the
   *         first {@link Date} object stored in this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#DATE}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public Date getDateValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single {@link Date} value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#DATE}, all previous values of this object is cleared, and the
   * new value is set to this object.
   * </p>
   *
   * @param value
   *          the new {@link Date} value to be set to this object, which may be
   *          null. Note that the cloned copy of this object will be stored in
   *          this object.
   */
  public void setDateValue(@Nullable Date value);

  /**
   * Gets the values of this object as a {@link Date} value.
   *
   * @return the values of this object as a {@link Date} array, whose elements
   *         may be null; or an empty array if this object has no value. Note
   *         that the objects in returned array is the cloned copies of the
   *         {@link Date} objects stored in this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#DATE}.
   */
  public Date[] getDateValues() throws TypeMismatchException;

  /**
   * Sets {@link Date} values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#DATE}, all previous values of this object is cleared, and the
   * new values is set to this object.
   * </p>
   *
   * @param values
   *          the array of new {@link Date} values to be set to this object,
   *          whose elements may be null. Note that the cloned copy of this
   *          array will be stored in this object.
   */
  public void setDateValues(Date... values);

  /**
   * Sets {@link Date} values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#DATE}, all previous values of this object is cleared, and the
   * new values is set to this object.
   * </p>
   *
   * @param values
   *          the collection of new {@link Date} values to be set to this
   *          object, whose elements may be null. Note that the cloned copy of
   *          this collection will be stored in this object.
   */
  public void setDateValues(Collection<Date> values);

  /**
   * Adds a {@link Date} value to this object.
   * <p>
   * If the type of this object is not {@link Type#DATE}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * {@link Date} value will be add to this object.
   * </p>
   *
   * @param value
   *          the {@link Date} value to be add to this object, which may be
   *          null. Note that the cloned copy of this object will be stored in
   *          this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#DATE} and there is
   *           old value in this object.
   */
  public void addDateValue(@Nullable Date value) throws TypeMismatchException;

  /**
   * Adds {@link Date} values to this object.
   * <p>
   * If the type of this object is not {@link Type#DATE}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * {@link Date} values will be add to this object.
   * </p>
   *
   * @param values
   *          the array of {@link Date} values to be add to this object, whose
   *          elements may be null. Note that the cloned copy of this array will
   *          be stored in this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#DATE} and there is
   *           old value in this object.
   */
  public void addDateValues(Date... values) throws TypeMismatchException;

  /**
   * Adds {@link Date} values to this object.
   * <p>
   * If the type of this object is not {@link Type#DATE}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * {@link Date} values will be add to this object.
   * </p>
   *
   * @param values
   *          the collection of {@link Date} values to be add to this object,
   *          whose elements may be null. Note that the cloned copy of this
   *          collection will be stored in this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#DATE} and there is
   *           old value in this object.
   */
  public void addDateValues(Collection<Date> values)
      throws TypeMismatchException;

  /**
   * Gets the first value of this object as a {@link BigInteger} value.
   *
   * @return the first value of this object as a {@link BigInteger} value, which
   *         may be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BIG_INTEGER}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public BigInteger getBigIntegerValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single {@link BigInteger} value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#BIG_INTEGER}, all previous values of this object is cleared,
   * and the new value is set to this object.
   * </p>
   *
   * @param value
   *          the new {@link BigInteger} value to be set to this object, which
   *          may be null.
   */
  public void setBigIntegerValue(@Nullable BigInteger value);

  /**
   * Gets the values of this object as a {@link BigInteger} value.
   *
   * @return the values of this object as a {@link BigInteger} array, whose
   *         elements may be null; or an empty array if this object has no
   *         value.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BIG_INTEGER}.
   */
  public BigInteger[] getBigIntegerValues() throws TypeMismatchException;

  /**
   * Sets {@link BigInteger} values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#BIG_INTEGER}, all previous values of this object is cleared,
   * and the new values is set to this object.
   * </p>
   *
   * @param values
   *          the array of new {@link BigInteger} values to be set to this
   *          object, whose elements may be null.
   */
  public void setBigIntegerValues(BigInteger... values);

  /**
   * Sets {@link BigInteger} values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#BIG_INTEGER}, all previous values of this object is cleared,
   * and the new values is set to this object.
   * </p>
   *
   * @param values
   *          the collection of new {@link BigInteger} values to be set to this
   *          object, whose elements may be null.
   */
  public void setBigIntegerValues(Collection<BigInteger> values);

  /**
   * Adds a {@link BigInteger} value to this object.
   * <p>
   * If the type of this object is not {@link Type#BIG_INTEGER}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * {@link BigInteger} value will be add to this object.
   * </p>
   *
   * @param value
   *          the {@link BigInteger} value to be add to this object, which may
   *          be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BIG_INTEGER} and
   *           there is old value in this object.
   */
  public void addBigIntegerValue(@Nullable BigInteger value)
      throws TypeMismatchException;

  /**
   * Adds {@link BigInteger} values to this object.
   * <p>
   * If the type of this object is not {@link Type#BIG_INTEGER}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * {@link BigInteger} values will be add to this object.
   * </p>
   *
   * @param values
   *          the array of {@link BigInteger} values to be add to this object,
   *          whose elements may be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BIG_INTEGER} and
   *           there is old value in this object.
   */
  public void addBigIntegerValues(BigInteger... values)
      throws TypeMismatchException;

  /**
   * Adds {@link BigInteger} values to this object.
   * <p>
   * If the type of this object is not {@link Type#BIG_INTEGER}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * {@link BigInteger} values will be add to this object.
   * </p>
   *
   * @param values
   *          the collection of {@link BigInteger} values to be add to this
   *          object, whose elements may be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BIG_INTEGER} and
   *           there is old value in this object.
   */
  public void addBigIntegerValues(Collection<BigInteger> values)
      throws TypeMismatchException;

  /**
   * Gets the first value of this object as a {@link BigDecimal} value.
   *
   * @return the first value of this object as a {@link BigDecimal} value, which
   *         may be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BIG_DECIMAL}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public BigDecimal getBigDecimalValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single {@link BigDecimal} value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#BIG_DECIMAL}, all previous values of this object is cleared,
   * and the new value is set to this object.
   * </p>
   *
   * @param value
   *          the new {@link BigDecimal} value to be set to this object, which
   *          may be null.
   */
  public void setBigDecimalValue(@Nullable BigDecimal value);

  /**
   * Gets the values of this object as a {@link BigDecimal} value.
   *
   * @return the values of this object as a {@link BigDecimal} array, whose
   *         elements may be null; or an empty array if this object has no
   *         value.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BIG_DECIMAL}.
   */
  public BigDecimal[] getBigDecimalValues() throws TypeMismatchException;

  /**
   * Sets {@link BigDecimal} values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#BIG_DECIMAL}, all previous values of this object is cleared,
   * and the new values is set to this object.
   * </p>
   *
   * @param values
   *          the array of new {@link BigDecimal} values to be set to this
   *          object, whose elements may be null.
   */
  public void setBigDecimalValues(BigDecimal... values);

  /**
   * Sets {@link BigDecimal} values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#BIG_DECIMAL}, all previous values of this object is cleared,
   * and the new values is set to this object.
   * </p>
   *
   * @param values
   *          the collection of new {@link BigDecimal} values to be set to this
   *          object, whose elements may be null.
   */
  public void setBigDecimalValues(Collection<BigDecimal> values);

  /**
   * Adds a {@link BigDecimal} value to this object.
   * <p>
   * If the type of this object is not {@link Type#BIG_DECIMAL}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * {@link BigDecimal} value will be add to this object.
   * </p>
   *
   * @param value
   *          the {@link BigDecimal} value to be add to this object, which may
   *          be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BIG_DECIMAL} and
   *           there is old value in this object.
   */
  public void addBigDecimalValue(@Nullable BigDecimal value)
      throws TypeMismatchException;

  /**
   * Adds {@link BigDecimal} values to this object.
   * <p>
   * If the type of this object is not {@link Type#BIG_DECIMAL}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * {@link BigDecimal} values will be add to this object.
   * </p>
   *
   * @param values
   *          the array of {@link BigDecimal} values to be add to this object,
   *          whose elements may be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BIG_DECIMAL} and
   *           there is old value in this object.
   */
  public void addBigDecimalValues(BigDecimal... values)
      throws TypeMismatchException;

  /**
   * Adds {@link BigDecimal} values to this object.
   * <p>
   * If the type of this object is not {@link Type#BIG_DECIMAL}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * {@link BigDecimal} values will be add to this object.
   * </p>
   *
   * @param values
   *          the collection of {@link BigDecimal} values to be add to this
   *          object, whose elements may be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BIG_DECIMAL} and
   *           there is old value in this object.
   */
  public void addBigDecimalValues(Collection<BigDecimal> values)
      throws TypeMismatchException;

  /**
   * Gets the first value of this object as a <code>byte[]</code> value.
   *
   * @return the first value of this object as a <code>byte[]</code> value,
   *         which may be null. Note that the returned object is the cloned copy
   *         of the first <code>byte[]</code> object stored in this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BYTE_ARRAY}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public byte[] getByteArrayValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single <code>byte[]</code> value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#BYTE_ARRAY}, all previous values of this object is cleared, and
   * the new value is set to this object.
   * </p>
   *
   * @param value
   *          the new <code>byte[]</code> value to be set to this object, which
   *          may be null. Note that the cloned copy of this object will be
   *          stored in this object.
   */
  public void setByteArrayValue(@Nullable byte[] value);

  /**
   * Gets the values of this object as a <code>byte[]</code> value.
   *
   * @return the values of this object as a <code>byte[]</code> array, whose
   *         elements may be null; or an empty array if this object has no
   *         value. Note that the objects in returned array is the cloned copies
   *         of the <code>byte[]</code> objects stored in this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BYTE_ARRAY}.
   */
  public byte[][] getByteArrayValues() throws TypeMismatchException;

  /**
   * Sets <code>byte[]</code> values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#BYTE_ARRAY}, all previous values of this object is cleared, and
   * the new values is set to this object.
   * </p>
   *
   * @param values
   *          the array of new <code>byte[]</code> values to be set to this
   *          object, whose elements may be null. Note that the cloned copy of
   *          this array will be stored in this object.
   */
  public void setByteArrayValues(byte[]... values);

  /**
   * Sets <code>byte[]</code> values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#BYTE_ARRAY}, all previous values of this object is cleared, and
   * the new values is set to this object.
   * </p>
   *
   * @param values
   *          the collection of new <code>byte[]</code> values to be set to this
   *          object, whose elements may be null. Note that the cloned copy of
   *          this collection will be stored in this object.
   */
  public void setByteArrayValues(Collection<byte[]> values);

  /**
   * Adds a <code>byte[]</code> value to this object.
   * <p>
   * If the type of this object is not {@link Type#BYTE_ARRAY}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>byte[]</code> value will be add to this object.
   * </p>
   *
   * @param value
   *          the <code>byte[]</code> value to be add to this object, which may
   *          be null. Note that the cloned copy of this object will be stored
   *          in this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BYTE_ARRAY} and
   *           there is old value in this object.
   */
  public void addByteArrayValue(@Nullable byte[] value)
      throws TypeMismatchException;

  /**
   * Adds <code>byte[]</code> values to this object.
   * <p>
   * If the type of this object is not {@link Type#BYTE_ARRAY}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>byte[]</code> values will be add to this object.
   * </p>
   *
   * @param values
   *          the array of <code>byte[]</code> values to be add to this object,
   *          whose elements may be null. Note that the cloned copy of this
   *          array will be stored in this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BYTE_ARRAY} and
   *           there is old value in this object.
   */
  public void addByteArrayValues(byte[]... values) throws TypeMismatchException;

  /**
   * Adds <code>byte[]</code> values to this object.
   * <p>
   * If the type of this object is not {@link Type#BYTE_ARRAY}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>byte[]</code> values will be add to this object.
   * </p>
   *
   * @param values
   *          the collection of <code>byte[]</code> values to be add to this
   *          object, whose elements may be null. Note that the cloned copy of
   *          this collection will be stored in this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BYTE_ARRAY} and
   *           there is old value in this object.
   */
  public void addByteArrayValues(Collection<byte[]> values)
      throws TypeMismatchException;

  /**
   * Gets the first value of this object as a <code>Class</code> value.
   *
   * @return the first value of this object as a <code>Class</code> value, which
   *         may be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#CLASS}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public Class<?> getClassValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single <code>Class</code> value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#CLASS}, all previous values of this object is cleared, and the
   * new value is set to this object.
   * </p>
   *
   * @param value
   *          the new <code>Class</code> value to be set to this object, which
   *          may be null.
   */
  public void setClassValue(@Nullable Class<?> value);

  /**
   * Gets the values of this object as a <code>Class</code> value.
   *
   * @return the values of this object as a <code>Class</code> array, whose
   *         elements may be null; or an empty array if this object has no
   *         value.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#CLASS}.
   */
  public Class<?>[] getClassValues() throws TypeMismatchException;

  /**
   * Sets <code>Class</code> values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#CLASS}, all previous values of this object is cleared, and the
   * new values is set to this object.
   * </p>
   *
   * @param values
   *          the array of new <code>Class</code> values to be set to this
   *          object, whose elements may be null.
   */
  public void setClassValues(Class<?>... values);

  /**
   * Sets <code>Class</code> values to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#CLASS}, all previous values of this object is cleared, and the
   * new values is set to this object.
   * </p>
   *
   * @param values
   *          the collection of new <code>Class</code> values to be set to this
   *          object, whose elements may be null.
   */
  public void setClassValues(Collection<Class<?>> values);

  /**
   * Adds a <code>Class</code> value to this object.
   * <p>
   * If the type of this object is not {@link Type#CLASS}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>Class</code> value will be add to this object.
   * </p>
   *
   * @param value
   *          the <code>Class</code> value to be add to this object, which may
   *          be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#CLASS} and there is
   *           old value in this object.
   */
  public void addClassValue(@Nullable Class<?> value)
      throws TypeMismatchException;

  /**
   * Adds <code>Class</code> values to this object.
   * <p>
   * If the type of this object is not {@link Type#CLASS}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>Class</code> values will be add to this object.
   * </p>
   *
   * @param values
   *          the array of <code>Class</code> values to be add to this object,
   *          whose elements may be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#CLASS} and there is
   *           old value in this object.
   */
  public void addClassValues(Class<?>... values) throws TypeMismatchException;

  /**
   * Adds <code>Class</code> values to this object.
   * <p>
   * If the type of this object is not {@link Type#CLASS}, an
   * {@link TypeMismatchException} will be thrown; otherwise, the specified
   * <code>Class</code> values will be add to this object.
   * </p>
   *
   * @param values
   *          the collection of <code>Class</code> values to be add to this
   *          object, whose elements may be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#CLASS} and there is
   *           old value in this object.
   */
  public void addClassValues(Collection<Class<?>> values)
      throws TypeMismatchException;

  /**
   * Gets the first value of this object as a <code>boolean</code> value.
   * <p>
   * If the type of this object is not {@link Type#BOOLEAN}, the value will be
   * converted into a <code>boolean</code> value.
   *
   * @return the first value of this object as a <code>boolean</code> value.
   * @throws TypeConvertException
   *           if the first value of this object can not be converted into a
   *           <code>boolean</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public boolean getValueAsBoolean() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a <code>boolean</code> values.
   * <p>
   * If the type of this object is not {@link Type#BOOLEAN}, the values will be
   * converted into <code>boolean</code> values.
   *
   * @return the values of this object as a <code>boolean</code> values.
   * @throws TypeConvertException
   *           if the values of this object can not be converted into a
   *           <code>boolean</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public boolean[] getValuesAsBoolean() throws TypeConvertException;

  /**
   * Gets the first value of this object as a <code>char</code> value.
   * <p>
   * If the type of this object is not {@link Type#CHAR}, the value will be
   * converted into a <code>char</code> value.
   *
   * @return the first value of this object as a <code>char</code> value.
   * @throws TypeConvertException
   *           if the first value of this object can not be converted into a
   *           <code>char</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public char getValueAsChar() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a <code>char</code> values.
   * <p>
   * If the type of this object is not {@link Type#CHAR}, the values will be
   * converted into <code>char</code> values.
   *
   * @return the values of this object as a <code>char</code> values.
   * @throws TypeConvertException
   *           if the values of this object can not be converted into a
   *           <code>char</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public char[] getValuesAsChar() throws TypeConvertException;

  /**
   * Gets the first value of this object as a <code>byte</code> value.
   * <p>
   * If the type of this object is not {@link Type#BYTE}, the value will be
   * converted into a <code>byte</code> value.
   *
   * @return the first value of this object as a <code>boolean</code> value.
   * @throws TypeConvertException
   *           if the first value of this object can not be converted into a
   *           <code>byte</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public byte getValueAsByte() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a <code>byte</code> values.
   * <p>
   * If the type of this object is not {@link Type#BYTE}, the values will be
   * converted into <code>byte</code> values.
   *
   * @return the values of this object as a <code>byte</code> values.
   * @throws TypeConvertException
   *           if the values of this object can not be converted into a
   *           <code>byte</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public byte[] getValuesAsByte() throws TypeConvertException;

  /**
   * Gets the first value of this object as a <code>short</code> value.
   * <p>
   * If the type of this object is not {@link Type#SHORT}, the value will be
   * converted into a <code>short</code> value.
   *
   * @return the first value of this object as a <code>boolean</code> value.
   * @throws TypeConvertException
   *           if the first value of this object can not be converted into a
   *           <code>short</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public short getValueAsShort() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a <code>short</code> values.
   * <p>
   * If the type of this object is not {@link Type#SHORT}, the values will be
   * converted into <code>short</code> values.
   *
   * @return the values of this object as a <code>short</code> values.
   * @throws TypeConvertException
   *           if the values of this object can not be converted into a
   *           <code>short</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public short[] getValuesAsShort() throws TypeConvertException;

  /**
   * Gets the first value of this object as a <code>int</code> value.
   * <p>
   * If the type of this object is not {@link Type#INT}, the value will be
   * converted into a <code>int</code> value.
   *
   * @return the first value of this object as a <code>int</code> value.
   * @throws TypeConvertException
   *           if the first value of this object can not be converted into a
   *           <code>int</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public int getValueAsInt() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a <code>int</code> values.
   * <p>
   * If the type of this object is not {@link Type#INT}, the values will be
   * converted into <code>int</code> values.
   *
   * @return the values of this object as a <code>int</code> values.
   * @throws TypeConvertException
   *           if the values of this object can not be converted into a
   *           <code>int</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public int[] getValuesAsInt() throws TypeConvertException;

  /**
   * Gets the first value of this object as a <code>long</code> value.
   * <p>
   * If the type of this object is not {@link Type#LONG}, the value will be
   * converted into a <code>long</code> value.
   *
   * @return the first value of this object as a <code>long</code> value.
   * @throws TypeConvertException
   *           if the first value of this object can not be converted into a
   *           <code>long</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public long getValueAsLong() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a <code>long</code> values.
   * <p>
   * If the type of this object is not {@link Type#LONG}, the values will be
   * converted into <code>char</code> values.
   *
   * @return the values of this object as a <code>long</code> values.
   * @throws TypeConvertException
   *           if the values of this object can not be converted into a
   *           <code>long</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public long[] getValuesAsLong() throws TypeConvertException;

  /**
   * Gets the first value of this object as a <code>float</code> value.
   * <p>
   * If the type of this object is not {@link Type#FLOAT}, the value will be
   * converted into a <code>float</code> value.
   *
   * @return the first value of this object as a <code>float</code> value.
   * @throws TypeConvertException
   *           if the first value of this object can not be converted into a
   *           <code>float</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public float getValueAsFloat() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a <code>float</code> values.
   * <p>
   * If the type of this object is not {@link Type#FLOAT}, the values will be
   * converted into <code>float</code> values.
   *
   * @return the values of this object as a <code>float</code> values.
   * @throws TypeConvertException
   *           if the values of this object can not be converted into a
   *           <code>float</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public float[] getValuesAsFloat() throws TypeConvertException;

  /**
   * Gets the first value of this object as a <code>double</code> value.
   * <p>
   * If the type of this object is not {@link Type#DOUBLE}, the value will be
   * converted into a <code>boolean</code> value.
   *
   * @return the first value of this object as a <code>double</code> value.
   * @throws TypeConvertException
   *           if the first value of this object can not be converted into a
   *           <code>double</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public double getValueAsDouble() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a <code>double</code> values.
   * <p>
   * If the type of this object is not {@link Type#DOUBLE}, the values will be
   * converted into <code>double</code> values.
   *
   * @return the values of this object as a <code>double</code> values.
   * @throws TypeConvertException
   *           if the values of this object can not be converted into a
   *           <code>double</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public double[] getValuesAsDouble() throws TypeConvertException;

  /**
   * Gets the first value of this object as a {@link String} value.
   * <p>
   * If the type of this object is not {@link Type#STRING}, the value will be
   * converted into a {@link String} value.
   *
   * @return the first value of this object as a {@link String} value.
   * @throws TypeConvertException
   *           if the first value of this object can not be converted into a
   *           {@link String} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public String getValueAsString() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a {@link String} values.
   * <p>
   * If the type of this object is not {@link Type#STRING}, the values will be
   * converted into {@link String} values.
   *
   * @return the values of this object as a {@link String} values.
   * @throws TypeConvertException
   *           if the values of this object can not be converted into a
   *           {@link String} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public String[] getValuesAsString() throws TypeConvertException;

  /**
   * Gets the first value of this object as a {@link Date} value.
   * <p>
   * If the type of this object is not {@link Type#DATE}, the value will be
   * converted into a {@link Date} value.
   *
   * @return the first value of this object as a {@link Date} value.
   * @throws TypeConvertException
   *           if the first value of this object can not be converted into a
   *           {@link Date} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public Date getValueAsDate() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a {@link Date} values.
   * <p>
   * If the type of this object is not {@link Type#DATE}, the values will be
   * converted into {@link Date} values.
   *
   * @return the values of this object as a {@link Date} values.
   * @throws TypeConvertException
   *           if the values of this object can not be converted into a
   *           {@link Date} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public Date[] getValuesAsDate() throws TypeConvertException;

  /**
   * Gets the first value of this object as a <code>byte[]</code> value.
   * <p>
   * If the type of this object is not {@link Type#BYTE_ARRAY}, the value will
   * be converted into a <code>byte[]</code> value.
   *
   * @return the first value of this object as a <code>boolean</code> value.
   * @throws TypeConvertException
   *           if the first value of this object can not be converted into a
   *           <code>byte[]</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public byte[] getValueAsByteArray() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a <code>byte[]</code> values.
   * <p>
   * If the type of this object is not {@link Type#BYTE_ARRAY}, the values will
   * be converted into <code>byte[]</code> values.
   *
   * @return the values of this object as a <code>byte[]</code> values.
   * @throws TypeConvertException
   *           if the values of this object can not be converted into a
   *           <code>byte[]</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public byte[][] getValuesAsByteArray() throws TypeConvertException;

  /**
   * Gets the first value of this object as a <code>Class</code> value.
   * <p>
   * If the type of this object is not {@link Type#CLASS}, the value will be
   * converted into a <code>boolean</code> value.
   *
   * @return the first value of this object as a <code>Class</code> value.
   * @throws TypeConvertException
   *           if the first value of this object can not be converted into a
   *           <code>Class</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public Class<?> getValueAsClass() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a <code>Class</code> values.
   * <p>
   * If the type of this object is not {@link Type#CLASS}, the values will be
   * converted into <code>Class</code> values.
   *
   * @return the values of this object as a <code>Class</code> values.
   * @throws TypeConvertException
   *           if the values of this object can not be converted into a
   *           <code>Class</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public Class<?>[] getValuesAsClass() throws TypeConvertException;

  /**
   * Gets the first value of this object as a {@link BigInteger} value.
   * <p>
   * If the type of this object is not {@link Type#BIG_INTEGER}, the value will
   * be converted into a <code>boolean</code> value.
   *
   * @return the first value of this object as a {@link BigInteger} value.
   * @throws TypeConvertException
   *           if the first value of this object can not be converted into a
   *           {@link BigInteger} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public BigInteger getValueAsBigInteger() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a {@link BigInteger} values.
   * <p>
   * If the type of this object is not {@link Type#BIG_INTEGER}, the values will
   * be converted into {@link BigInteger} values.
   *
   * @return the values of this object as a <code>char</code> values.
   * @throws TypeConvertException
   *           if the values of this object can not be converted into a
   *           {@link BigInteger} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public BigInteger[] getValuesAsBigInteger() throws TypeConvertException;

  /**
   * Gets the first value of this object as a {@link BigDecimal} value.
   * <p>
   * If the type of this object is not {@link Type#BIG_DECIMAL}, the value will
   * be converted into a <code>boolean</code> value.
   *
   * @return the first value of this object as a {@link BigDecimal} value.
   * @throws TypeConvertException
   *           if the first value of this object can not be converted into a
   *           {@link BigDecimal} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public BigDecimal getValueAsBigDecimal() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a {@link BigDecimal} values.
   * <p>
   * If the type of this object is not {@link Type#BIG_DECIMAL}, the values will
   * be converted into {@link BigDecimal} values.
   *
   * @return the values of this object as a {@link BigDecimal} values.
   * @throws TypeConvertException
   *           if the values of this object can not be converted into a
   *           {@link BigDecimal} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public BigDecimal[] getValuesAsBigDecimal() throws TypeConvertException;

  /**
   * Clones this {@link MultiValues} object.
   *
   * @return the cloned copy of this {@link MultiValues} object.
   */
  @Override
  public MultiValues clone();
}
