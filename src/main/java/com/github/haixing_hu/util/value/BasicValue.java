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

import com.github.haixing_hu.io.exception.InvalidFormatException;
import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.lang.Assignment;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.lang.Type;
import com.github.haixing_hu.lang.TypeConvertException;
import com.github.haixing_hu.lang.TypeMismatchException;
import com.github.haixing_hu.lang.TypeUtils;
import com.github.haixing_hu.lang.UnsupportedTypeException;
import com.github.haixing_hu.text.tostring.ToStringBuilder;
import com.github.haixing_hu.text.xml.DomUtils;
import com.github.haixing_hu.text.xml.XmlException;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * The basic implementation of the {@link Value} interface.
 *
 * @author Haixing Hu
 */
public class BasicValue implements Value {

  static {
    XmlSerialization.register(BasicValue.class, BasicValueXmlSerializer.INSTANCE);
  }

  protected Type type;
  protected Object value;

  public BasicValue() {
    type = DEFAULT_TYPE;
    value = null;
  }

  public BasicValue(final Type type) {
    this.type = requireNonNull("type", type);
    this.value = null;
  }

  public BasicValue(final boolean value) {
    this();
    setBooleanValue(value);
  }

  public BasicValue(final char value) {
    this();
    setCharValue(value);
  }

  public BasicValue(final byte value) {
    this();
    setByteValue(value);
  }

  public BasicValue(final short value) {
    this();
    setShortValue(value);
  }

  public BasicValue(final int value) {
    this();
    setIntValue(value);
  }

  public BasicValue(final long value) {
    this();
    setLongValue(value);
  }

  public BasicValue(final float value) {
    this();
    setFloatValue(value);
  }

  public BasicValue(final double value) {
    this();
    setDoubleValue(value);
  }

  public BasicValue(@Nullable final String value) {
    this();
    setStringValue(value);
  }

  public BasicValue(@Nullable final Date value) {
    this();
    setDateValue(value);
  }

  public BasicValue(@Nullable final BigInteger value) {
    this();
    setBigIntegerValue(value);
  }

  public BasicValue(@Nullable final BigDecimal value) {
    this();
    setBigDecimalValue(value);
  }

  public BasicValue(@Nullable final byte[] value) {
    this();
    setByteArrayValue(value);
  }

  public BasicValue(@Nullable final Class<?> value) {
    this();
    setClassValue(value);
  }

  @Override
  public Type getType() {
    return type;
  }

  @Override
  public void setType(final Type type) {
    this.type = requireNonNull("type", type);
  }

  @Override
  public boolean isEmpty() {
    return value == null;
  }

  @Override
  public void clear() {
    value = null;
  }

  @Override
  public void assignValue(final Value other) {
    if (this == other) {
      return;
    }
    final Type type = other.getType();
    if (other.isEmpty()) {
      this.clear();
      this.setType(type);
      return;
    }
    switch (type) {
      case BOOLEAN: {
        final boolean value = other.getBooleanValue();
        this.type = Type.BOOLEAN;
        this.value = Boolean.valueOf(value);
        return;
      }
      case CHAR: {
        final char value = other.getCharValue();
        this.type = Type.CHAR;
        this.value = Character.valueOf(value);
        return;
      }
      case BYTE: {
        final byte value = other.getByteValue();
        this.type = Type.BYTE;
        this.value = Byte.valueOf(value);
        return;
      }
      case SHORT: {
        final short value = other.getShortValue();
        this.type = Type.SHORT;
        this.value = Short.valueOf(value);
        return;
      }
      case INT: {
        final int value = other.getIntValue();
        this.type = Type.INT;
        this.value = Integer.valueOf(value);
        return;
      }
      case LONG: {
        final long value = other.getLongValue();
        this.type = Type.LONG;
        this.value = Long.valueOf(value);
        return;
      }
      case FLOAT: {
        final float value = other.getFloatValue();
        this.type = Type.FLOAT;
        this.value = Float.valueOf(value);
        return;
      }
      case DOUBLE: {
        final double value = other.getDoubleValue();
        this.type = Type.DOUBLE;
        this.value = Double.valueOf(value);
        return;
      }
      case STRING: {
        final String value = other.getStringValue();
        this.type = Type.STRING;
        this.value = value;
        return;
      }
      case DATE: {
        final Date value = other.getDateValue();
        // don't need to clone the value, since it is already the cloned
        // copy of the object in other.
        this.type = Type.DATE;
        this.value = value;
        return;
      }
      case BYTE_ARRAY: {
        final byte[] value = other.getByteArrayValue();
        // don't need to clone the value, since it is already the cloned
        // copy of the object in other.
        this.type = Type.BYTE_ARRAY;
        this.value = value;
        return;
      }
      case CLASS: {
        final Class<?> value = other.getClassValue();
        this.type = Type.CLASS;
        this.value = value;
        return;
      }
      case BIG_INTEGER: {
        final BigInteger value = other.getBigIntegerValue();
        this.type = Type.BIG_INTEGER;
        this.value = value;
        return;
      }
      case BIG_DECIMAL: {
        final BigDecimal value = other.getBigDecimalValue();
        this.type = Type.BIG_DECIMAL;
        this.value = value;
        return;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  @Override
  public void readValue(final Type type, final InputStream in)
      throws IOException {
    try {
      final Object value = TypeUtils.readObject(type, in);
      this.type = type;
      this.value = value;
    } catch (final UnsupportedTypeException e) {
      throw new InvalidFormatException(e);
    } catch (final ClassNotFoundException e) {
      throw new InvalidFormatException(e);
    }
  }

  @Override
  public void writeValue(final OutputStream out) throws IOException {
    TypeUtils.writeObject(type, value, out);
  }

  @Override
  public void getValueFromXml(final Type type, final Element parent,
      final String tagName, final String prevSpaceAttr) throws XmlException {
    final Element child = DomUtils.getOptChild(parent, tagName);
    if (child == null) {
      this.type = type;
      this.value = null;
    } else {
      final Object value = TypeUtils.fromXmlNode(type, child, prevSpaceAttr);
      this.type = type;
      this.value = value;
    }
  }

  @Override
  public void appendValueToXml(final Document doc, final Element parent,
      final String tagName, @Nullable final String prevSpaceAttr)
      throws XmlException {
    if (value != null) {
      final Element node = TypeUtils.toXmlNode(type, value, doc, tagName,
          prevSpaceAttr);
      parent.appendChild(node);
    }
  }

  @Override
  public boolean getBooleanValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BOOLEAN) {
      throw new TypeMismatchException(Type.BOOLEAN.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (Boolean) value;
  }

  @Override
  public void setBooleanValue(final boolean value) {
    this.type = Type.BOOLEAN;
    this.value = Boolean.valueOf(value);
  }

  @Override
  public char getCharValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.CHAR) {
      throw new TypeMismatchException(Type.CHAR.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (Character) value;
  }

  @Override
  public void setCharValue(final char value) {
    this.type = Type.CHAR;
    this.value = Character.valueOf(value);
  }

  @Override
  public byte getByteValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BYTE) {
      throw new TypeMismatchException(Type.BYTE.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (Byte) value;
  }

  @Override
  public void setByteValue(final byte value) {
    this.type = Type.BYTE;
    this.value = Byte.valueOf(value);
  }

  @Override
  public short getShortValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.SHORT) {
      throw new TypeMismatchException(Type.SHORT.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (Short) value;
  }

  @Override
  public void setShortValue(final short value) {
    this.type = Type.SHORT;
    this.value = Short.valueOf(value);
  }

  @Override
  public int getIntValue() throws TypeMismatchException, NoSuchElementException {
    if (type != Type.INT) {
      throw new TypeMismatchException(Type.INT.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (Integer) value;
  }

  @Override
  public void setIntValue(final int value) {
    this.type = Type.INT;
    this.value = Integer.valueOf(value);
  }

  @Override
  public long getLongValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.LONG) {
      throw new TypeMismatchException(Type.LONG.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (Long) value;
  }

  @Override
  public void setLongValue(final long value) {
    this.type = Type.LONG;
    this.value = Long.valueOf(value);
  }

  @Override
  public float getFloatValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.FLOAT) {
      throw new TypeMismatchException(Type.FLOAT.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (Float) value;
  }

  @Override
  public void setFloatValue(final float value) {
    this.type = Type.FLOAT;
    this.value = Float.valueOf(value);
  }

  @Override
  public double getDoubleValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.DOUBLE) {
      throw new TypeMismatchException(Type.DOUBLE.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (Double) value;
  }

  @Override
  public void setDoubleValue(final double value) {
    this.type = Type.DOUBLE;
    this.value = Double.valueOf(value);
  }

  @Override
  public String getStringValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.STRING) {
      throw new TypeMismatchException(Type.STRING.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (String) value;
  }

  @Override
  public void setStringValue(final String value) {
    this.type = Type.STRING;
    this.value = value;
  }

  @Override
  public Date getDateValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.DATE) {
      throw new TypeMismatchException(Type.DATE.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return Assignment.clone((Date) value);
  }

  @Override
  public void setDateValue(final Date value) {
    this.type = Type.DATE;
    this.value = Assignment.clone(value);
  }

  @Override
  public BigInteger getBigIntegerValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BIG_INTEGER) {
      throw new TypeMismatchException(Type.BIG_INTEGER.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (BigInteger) value;
  }

  @Override
  public void setBigIntegerValue(final BigInteger value) {
    this.type = Type.BIG_INTEGER;
    this.value = value;
  }

  @Override
  public BigDecimal getBigDecimalValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BIG_DECIMAL) {
      throw new TypeMismatchException(Type.BIG_DECIMAL.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (BigDecimal) value;
  }

  @Override
  public void setBigDecimalValue(final BigDecimal value) {
    this.type = Type.BIG_DECIMAL;
    this.value = value;
  }

  @Override
  public byte[] getByteArrayValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BYTE_ARRAY) {
      throw new TypeMismatchException(Type.BYTE_ARRAY.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return Assignment.clone((byte[]) value);
  }

  @Override
  public void setByteArrayValue(final byte[] value) {
    this.type = Type.BYTE_ARRAY;
    this.value = Assignment.clone(value);
  }

  @Override
  public Class<?> getClassValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.CLASS) {
      throw new TypeMismatchException(Type.CLASS.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (Class<?>) value;
  }

  @Override
  public void setClassValue(final Class<?> value) {
    this.type = Type.CLASS;
    this.value = value;
  }

  @Override
  public boolean getValueAsBoolean() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    return TypeUtils.objectAsBoolean(type, value);
  }

  @Override
  public char getValueAsChar() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    return TypeUtils.objectAsChar(type, value);
  }

  @Override
  public byte getValueAsByte() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    return TypeUtils.objectAsByte(type, value);
  }

  @Override
  public short getValueAsShort() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    return TypeUtils.objectAsShort(type, value);
  }

  @Override
  public int getValueAsInt() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    return TypeUtils.objectAsInt(type, value);
  }

  @Override
  public long getValueAsLong() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    return TypeUtils.objectAsLong(type, value);
  }

  @Override
  public float getValueAsFloat() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    return TypeUtils.objectAsFloat(type, value);
  }

  @Override
  public double getValueAsDouble() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    return TypeUtils.objectAsDouble(type, value);
  }

  @Override
  public String getValueAsString() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    final Type type = this.getType();
    return TypeUtils.objectAsString(type, value);
  }

  @Override
  public Date getValueAsDate() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    final Type type = this.getType();
    return TypeUtils.objectAsDate(type, value);
  }

  @Override
  public byte[] getValueAsByteArray() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    final Type type = this.getType();
    return TypeUtils.objectAsByteArray(type, value);
  }

  @Override
  public Class<?> getValueAsClass() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    return TypeUtils.objectAsClass(type, value);
  }

  @Override
  public BigInteger getValueAsBigInteger() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    return TypeUtils.objectAsBigInteger(type, value);
  }

  @Override
  public BigDecimal getValueAsBigDecimal() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    final Type type = this.getType();
    return TypeUtils.objectAsBigDecimal(type, value);
  }

  @Override
  public BasicValue clone() {
    final BasicValue result = new BasicValue();
    result.type = this.type;
    result.value = TypeUtils.cloneObject(this.type, this.type);
    return result;
  }

  @Override
  public int hashCode() {
    final int multiplier = 13;
    int code = 2;
    code = Hash.combine(code, multiplier, type);
    code = Hash.combine(code, multiplier, TypeUtils.hashCodeOfObject(type, value));
    return code;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }
    if (! (obj instanceof BasicValue)) {
      return false;
    }
    final BasicValue other = (BasicValue) obj;
    return (type == other.type)
        && TypeUtils.equalsObject(type, value, other.value);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("type", type)
               .append("value", value)
               .toString();
  }
}
