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
package com.github.haixing_hu.util.value;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.github.haixing_hu.lang.Equality;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.lang.Type;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * The basic implementation of the {@link NamedValue} interface.
 *
 * @author Haixing Hu
 */
public class BasicNamedValue extends BasicValue implements NamedValue {

  protected String name;

  public BasicNamedValue() {
    super();
    name = StringUtils.EMPTY;
  }

  public BasicNamedValue(final String name) {
    super();
    this.name = requireNonNull("name", name);
  }

  public BasicNamedValue(final String name, final Type type) {
    super(type);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedValue(final String name, final boolean value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedValue(final String name, final char value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedValue(final String name, final byte value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedValue(final String name, final short value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedValue(final String name, final int value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedValue(final String name, final long value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedValue(final String name, final float value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedValue(final String name, final double value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedValue(final String name, final String value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedValue(final String name, final Date value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedValue(final String name, final BigInteger value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedValue(final String name, final BigDecimal value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedValue(final String name, final byte[] value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedValue(final String name, final Class<?> value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(final String name) {
    this.name = requireNonNull("name", name);
  }

  @Override
  public BasicNamedValue clone() {
    final BasicNamedValue result = (BasicNamedValue) super.clone();
    result.name = name;
    return result;
  }

  @Override
  public int hashCode() {
    final int multiplier = 17;
    int code = super.hashCode();
    code = Hash.combine(code, multiplier, name);
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
    if (! (obj instanceof BasicNamedValue)) {
      return false;
    }
    final BasicNamedValue other = (BasicNamedValue) obj;
    return (super.equals(other))
          && Equality.equals(name, other.name);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("name", name)
               .appendSuper(super.toString())
               .toString();
  }
}
