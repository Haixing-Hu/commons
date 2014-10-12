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
package com.github.haixing_hu.config.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.haixing_hu.config.Property;
import com.github.haixing_hu.io.serialize.BinarySerialization;
import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.lang.Equality;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.lang.Type;
import com.github.haixing_hu.text.tostring.ToStringBuilder;
import com.github.haixing_hu.util.value.BasicNamedMultiValues;

/**
 * The default implementation of the {@link Property} interface.
 *
 * @author Haixing Hu
 */
public class DefaultProperty extends BasicNamedMultiValues implements Property {

  private static final long serialVersionUID = 3586004753799522564L;

  protected final Logger LOGGER = LoggerFactory.getLogger(DefaultProperty.class);

  static {
    BinarySerialization.register(DefaultProperty.class, DefaultPropertyBinarySerializer.INSTANCE);
    XmlSerialization.register(DefaultProperty.class, DefaultPropertyXmlSerializer.INSTANCE);
  }

  public static final boolean DEFAULT_IS_FINAL          = false;
  public static final boolean DEFAULT_TRIM              = true;
  public static final boolean DEFAULT_PRESERVE_SPACE    = false;

  protected String description;
  protected boolean isFinal;

  public DefaultProperty() {
    super();
    description = null;
    isFinal = DEFAULT_IS_FINAL;
  }

  public DefaultProperty(final String name) {
    super(name);
    this.description = null;
    this.isFinal = DEFAULT_IS_FINAL;
  }

  public DefaultProperty(final String name, final Type type) {
    super(name, type);
    this.description = null;
    this.isFinal = DEFAULT_IS_FINAL;
  }

  public DefaultProperty(final String name, final boolean value) {
    super(name, value);
    this.description = null;
    this.isFinal = DEFAULT_IS_FINAL;
  }

  public DefaultProperty(final String name, final char value) {
    super(name, value);
    this.description = null;
    this.isFinal = DEFAULT_IS_FINAL;
  }

  public DefaultProperty(final String name, final byte value) {
    super(name, value);
    this.description = null;
    this.isFinal = DEFAULT_IS_FINAL;
  }

  public DefaultProperty(final String name, final short value) {
    super(name, value);
    this.description = null;
    this.isFinal = DEFAULT_IS_FINAL;
  }

  public DefaultProperty(final String name, final int value) {
    super(name, value);
    this.description = null;
    this.isFinal = DEFAULT_IS_FINAL;
  }

  public DefaultProperty(final String name, final long value) {
    super(name, value);
    this.description = null;
    this.isFinal = DEFAULT_IS_FINAL;
  }

  public DefaultProperty(final String name, final float value) {
    super(name, value);
    this.description = null;
    this.isFinal = DEFAULT_IS_FINAL;
  }

  public DefaultProperty(final String name, final double value) {
    super(name, value);
    this.description = null;
    this.isFinal = DEFAULT_IS_FINAL;
  }

  public DefaultProperty(final String name, final String value) {
    super(name, value);
    this.description = null;
    this.isFinal = DEFAULT_IS_FINAL;
  }

  public DefaultProperty(final String name, final Date value) {
    super(name, value);
    this.description = null;
    this.isFinal = DEFAULT_IS_FINAL;
  }

  public DefaultProperty(final String name, final BigInteger value) {
    super(name, value);
    this.description = null;
    this.isFinal = DEFAULT_IS_FINAL;
  }

  public DefaultProperty(final String name, final BigDecimal value) {
    super(name, value);
    this.description = null;
    this.isFinal = DEFAULT_IS_FINAL;
  }

  public DefaultProperty(final String name, final byte[] value) {
    super(name, value);
    this.description = null;
    this.isFinal = DEFAULT_IS_FINAL;
  }

  public DefaultProperty(final String name, final Class<?> value) {
    super(name, value);
    this.description = null;
    this.isFinal = DEFAULT_IS_FINAL;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public void setDescription(@Nullable final String description) {
    this.description = description;
  }

  @Override
  public boolean isFinal() {
    return isFinal;
  }

  @Override
  public void setFinal(final boolean isFinal) {
    this.isFinal = isFinal;
  }

  @Override
  public void assign(final Property that) {
    if (this != that) {
      this.name = that.getName();
      this.description = that.getDescription();
      this.isFinal = that.isFinal();
      this.assignValues(that);
    }
  }

  @Override
  public DefaultProperty clone() {
    final DefaultProperty result = new DefaultProperty(name, type);
    result.assign(this);
    return result;
  }

  @Override
  public int hashCode() {
    final int multiplier = 1121;
    int code = 3;
    code = Hash.combine(code, multiplier, super.hashCode());
    code = Hash.combine(code, multiplier, description);
    code = Hash.combine(code, multiplier, isFinal);
    return code;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final DefaultProperty other = (DefaultProperty) obj;
    return super.equals(other)
        && (isFinal == other.isFinal)
        && Equality.equals(description, other.description);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .appendSuper(super.toString())
               .append("description", description)
               .append("isFinal", isFinal)
               .toString();
  }

}
