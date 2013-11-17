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
import java.util.Date;
import java.util.NoSuchElementException;

import javax.annotation.Nullable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.lang.Cloneable;
import com.github.haixing_hu.lang.Type;
import com.github.haixing_hu.lang.TypeConvertException;
import com.github.haixing_hu.lang.TypeMismatchException;
import com.github.haixing_hu.text.xml.XmlException;

/**
 * A {@link Value} object represents a value of common types.
 * <p>
 * Currently the {@link Value} interface support the following types:
 * <ul>
 * <li><code>boolean</code></li>
 * <li><code>char</code></li>
 * <li><code>byte</code></li>
 * <li><code>short</code></li>
 * <li><code>int</code></li>
 * <li><code>long</code></li>
 * <li><code>float</code></li>
 * <li><code>double</code></li>
 * <li><code>String</code></li>
 * <li><code>java.util.Date</code></li>
 * <li><code>java.math.BigDecimal</code></li>
 * <li><code>java.math.BigInteger</code></li>
 * <li><code>byte[]</code></li>
 * <li><code>Class</code></li>
 * </ul>
 *
 * @author Haixing Hu
 */
public interface Value extends Cloneable<Value> {

  /**
   * The default type of the {@link Value} objects.
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
   * and all old value of this object will be cleared.
   * </p>
   *
   * @param type
   *          the new type of this object.
   */
  public void setType(Type type);

  /**
   * Tests whether this object has any value.
   *
   * @return true if this object has any value; false otherwise.
   */
  public boolean isEmpty();

  /**
   * Clears the value of this object.
   */
  public void clear();

  /**
   * Unions the value and type of another {@link Value} object to this
   * {@link Value} object .
   *
   * @param other
   *          the other {@link Value} object.
   */
  public void assignValue(Value other);

  /**
   * Reads value from an input stream.
   *
   * @param type
   *          the type of value to be read.
   * @param in
   *          the input stream.
   * @throws IOException
   *           If any I/O error occurred.
   */
  public void readValue(Type type, InputStream in) throws IOException;

  /**
   * Writes value to an output stream.
   * <p>
   * Note that this function does NOT write the type to the output stream.
   *
   * @param out
   *          the output stream.
   * @throws IOException
   *           If any I/O error occurred.
   */
  public void writeValue(OutputStream out) throws IOException;

  /**
   * Deserialize the value from the children of a DOM node.
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
  public void getValueFromXml(Type type, Element parent, String tagName,
      @Nullable String prevSpaceAttr) throws XmlException;

  /**
   * Serializes the value as a child nodes of a DOM node.
   *
   * @param doc
   *          the DOM document.
   * @param parent
   *          the parent DOM node.
   * @param tagName
   *          the tag name of value nodes.
   * @param prevSpaceAttr
   *          the attribute name of the preserve space attribute. If it is null,
   *          no preserve space attribute is used.
   * @throws XmlException
   *           If any XML related error occurred.
   */
  public void appendValueToXml(Document doc, Element parent,
      String tagName, @Nullable String prevSpaceAttr) throws XmlException;

  /**
   * Gets the <code>boolean</code> value of this object.
   *
   * @return the <code>boolean</code> value of this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BOOLEAN}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public boolean getBooleanValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a <code>boolean</code> value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#BOOLEAN}, all previous value of this object is cleared, and the
   * new value is set to this object.
   * </p>
   *
   * @param value
   *          the new <code>boolean</code> value to be set to this object.
   */
  public void setBooleanValue(boolean value);

  /**
   * Gets the <code>char</code> value of this object.
   *
   * @return the <code>char</code> value of this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#CHAR}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public char getCharValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a <code>char</code> value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#CHAR}, all previous value of this object is cleared, and the
   * new value is set to this object.
   * </p>
   *
   * @param value
   *          the new <code>char</code> value to be set to this object.
   */
  public void setCharValue(char value);

  /**
   * the <code>byte</code> value of this object.
   *
   * @return the <code>byte</code> value of this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BYTE}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public byte getByteValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a <code>byte</code> value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#BYTE}, all previous value of this object is cleared, and the
   * new value is set to this object.
   * </p>
   *
   * @param value
   *          the new <code>byte</code> value to be set to this object.
   */
  public void setByteValue(byte value);

  /**
   * the <code>short</code> value of this object.
   *
   * @return the <code>short</code> value of this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#SHORT}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public short getShortValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a <code>short</code> value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#SHORT}, all previous value of this object is cleared, and the
   * new value is set to this object.
   * </p>
   *
   * @param value
   *          the new <code>short</code> value to be set to this object.
   */
  public void setShortValue(short value);

  /**
   * the <code>int</code> value of this object.
   *
   * @return the <code>int</code> value of this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#INT}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public int getIntValue() throws TypeMismatchException, NoSuchElementException;

  /**
   * Sets a <code>int</code> value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#INT}, all previous value of this object is cleared, and the new
   * value is set to this object.
   * </p>
   *
   * @param value
   *          the new <code>int</code> value to be set to this object.
   */
  public void setIntValue(int value);

  /**
   * the <code>long</code> value of this object.
   *
   * @return the <code>long</code> value of this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#LONG}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public long getLongValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a <code>long</code> value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#LONG}, all previous value of this object is cleared, and the
   * new value is set to this object.
   * </p>
   *
   * @param value
   *          the new <code>long</code> value to be set to this object.
   */
  public void setLongValue(long value);

  /**
   * the <code>float</code> value of this object.
   *
   * @return the <code>float</code> value of this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#FLOAT}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public float getFloatValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a <code>float</code> value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#FLOAT}, all previous value of this object is cleared, and the
   * new value is set to this object.
   * </p>
   *
   * @param value
   *          the new <code>float</code> value to be set to this object.
   */
  public void setFloatValue(float value);

  /**
   * the <code>double</code> value of this object.
   *
   * @return the <code>double</code> value of this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#DOUBLE}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public double getDoubleValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a <code>double</code> value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#DOUBLE}, all previous value of this object is cleared, and the
   * new value is set to this object.
   * </p>
   *
   * @param value
   *          the new <code>double</code> value to be set to this object.
   */
  public void setDoubleValue(double value);

  /**
   * the <code>String</code> value of this object.
   *
   * @return the <code>String</code> value of this object, which may be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#STRING}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public String getStringValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a {@link String} value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#STRING}, all previous value of this object is cleared, and the
   * new value is set to this object.
   * </p>
   *
   * @param value
   *          the new {@link String} value to be set to this object, which may
   *          be null.
   */
  public void setStringValue(@Nullable String value);

  /**
   * the {@link Date} value of this object.
   *
   * @return the {@link Date} value of this object, which may be null. Note that
   *         the returned object is the cloned copy of the first {@link Date}
   *         object stored in this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#DATE}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public Date getDateValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a {@link Date} value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#DATE}, all previous value of this object is cleared, and the
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
   * Gets the {@link BigInteger} value of this object.
   *
   * @return the {@link BigInteger} value of this object, which may be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BIG_INTEGER}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public BigInteger getBigIntegerValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a {@link BigInteger} value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#BIG_INTEGER}, all previous value of this object is cleared, and
   * the new value is set to this object.
   * </p>
   *
   * @param value
   *          the new {@link BigInteger} value to be set to this object, which
   *          may be null.
   */
  public void setBigIntegerValue(@Nullable BigInteger value);

  /**
   * Gets the {@link BigDecimal} value of this object.
   *
   * @return the {@link BigDecimal} value of this object, which may be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BIG_DECIMAL}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public BigDecimal getBigDecimalValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a {@link BigDecimal} value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#BIG_DECIMAL}, all previous value of this object is cleared, and
   * the new value is set to this object.
   * </p>
   *
   * @param value
   *          the new {@link BigDecimal} value to be set to this object, which
   *          may be null.
   */
  public void setBigDecimalValue(@Nullable BigDecimal value);

  /**
   * Gets the <code>byte[]</code> value of this object.
   *
   * @return the <code>byte[]</code> value of this object, which may be null.
   *         Note that the returned object is the cloned copy of the
   *         <code>byte[]</code> object stored in this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BYTE_ARRAY}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public byte[] getByteArrayValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a <code>byte[]</code> value to this object.
   * <p>
   * After calling this function, the type of this object is set to
   * {@link Type#BYTE_ARRAY}, all previous value of this object is cleared, and
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
   * Gets the value of this object as a <code>Class</code> value.
   *
   * @return the value of this object as a <code>Class</code> value, which may
   *         be null.
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
   * {@link Type#CLASS}, all previous value of this object is cleared, and the
   * new value is set to this object.
   * </p>
   *
   * @param value
   *          the new <code>Class</code> value to be set to this object, which
   *          may be null.
   */
  public void setClassValue(@Nullable Class<?> value);

  /**
   * Gets the value of this object as a <code>boolean</code> value.
   * <p>
   * If the type of this object is not {@link Type#BOOLEAN}, the value will be
   * converted into a <code>boolean</code> value.
   *
   * @return the value of this object as a <code>boolean</code> value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           <code>boolean</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public boolean getValueAsBoolean() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a <code>char</code> value.
   * <p>
   * If the type of this object is not {@link Type#CHAR}, the value will be
   * converted into a <code>char</code> value.
   *
   * @return the value of this object as a <code>char</code> value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           <code>char</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public char getValueAsChar() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a <code>byte</code> value.
   * <p>
   * If the type of this object is not {@link Type#BYTE}, the value will be
   * converted into a <code>byte</code> value.
   *
   * @return the value of this object as a <code>boolean</code> value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           <code>byte</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public byte getValueAsByte() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a <code>short</code> value.
   * <p>
   * If the type of this object is not {@link Type#SHORT}, the value will be
   * converted into a <code>short</code> value.
   *
   * @return the value of this object as a <code>boolean</code> value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           <code>short</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public short getValueAsShort() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a <code>int</code> value.
   * <p>
   * If the type of this object is not {@link Type#INT}, the value will be
   * converted into a <code>int</code> value.
   *
   * @return the value of this object as a <code>int</code> value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           <code>int</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public int getValueAsInt() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a <code>long</code> value.
   * <p>
   * If the type of this object is not {@link Type#LONG}, the value will be
   * converted into a <code>long</code> value.
   *
   * @return the value of this object as a <code>long</code> value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           <code>long</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public long getValueAsLong() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a <code>float</code> value.
   * <p>
   * If the type of this object is not {@link Type#FLOAT}, the value will be
   * converted into a <code>float</code> value.
   *
   * @return the value of this object as a <code>float</code> value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           <code>float</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public float getValueAsFloat() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a <code>double</code> value.
   * <p>
   * If the type of this object is not {@link Type#DOUBLE}, the value will be
   * converted into a <code>boolean</code> value.
   *
   * @return the value of this object as a <code>double</code> value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           <code>double</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public double getValueAsDouble() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a <code>String</code> value.
   * <p>
   * If the type of this object is not {@link Type#STRING}, the value will be
   * converted into a <code>String</code> value.
   *
   * @return the value of this object as a <code>String</code> value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           <code>String</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public String getValueAsString() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a {@link Date} value.
   * <p>
   * If the type of this object is not {@link Type#DATE}, the value will be
   * converted into a {@link Date} value.
   *
   * @return the value of this object as a {@link Date} value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           {@link Date} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public Date getValueAsDate() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a <code>byte[]</code> value.
   * <p>
   * If the type of this object is not {@link Type#BYTE_ARRAY}, the value will
   * be converted into a <code>byte[]</code> value.
   *
   * @return the value of this object as a <code>boolean</code> value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           <code>byte[]</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public byte[] getValueAsByteArray() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a <code>Class</code> value.
   * <p>
   * If the type of this object is not {@link Type#CLASS}, the value will be
   * converted into a <code>boolean</code> value.
   *
   * @return the value of this object as a <code>Class</code> value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           <code>Class</code> value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public Class<?> getValueAsClass() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a {@link BigInteger} value.
   * <p>
   * If the type of this object is not {@link Type#BIG_INTEGER}, the value will
   * be converted into a <code>boolean</code> value.
   *
   * @return the value of this object as a {@link BigInteger} value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           {@link BigInteger} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public BigInteger getValueAsBigInteger() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a {@link BigDecimal} value.
   * <p>
   * If the type of this object is not {@link Type#BIG_DECIMAL}, the value will
   * be converted into a <code>boolean</code> value.
   *
   * @return the value of this object as a {@link BigDecimal} value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           {@link BigDecimal} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  public BigDecimal getValueAsBigDecimal() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Clones this {@link Value} object.
   *
   * @return the cloned copy of this {@link Value} object.
   */
  @Override
  public Value clone();
}
