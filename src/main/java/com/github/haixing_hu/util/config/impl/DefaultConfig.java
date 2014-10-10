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

package com.github.haixing_hu.util.config.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.haixing_hu.collection.primitive.BooleanCollection;
import com.github.haixing_hu.collection.primitive.ByteCollection;
import com.github.haixing_hu.collection.primitive.CharCollection;
import com.github.haixing_hu.collection.primitive.DoubleCollection;
import com.github.haixing_hu.collection.primitive.FloatCollection;
import com.github.haixing_hu.collection.primitive.IntCollection;
import com.github.haixing_hu.collection.primitive.LongCollection;
import com.github.haixing_hu.collection.primitive.ShortCollection;
import com.github.haixing_hu.io.serialize.BinarySerialization;
import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.lang.Type;
import com.github.haixing_hu.text.tostring.ToStringBuilder;
import com.github.haixing_hu.util.config.AbstractConfig;
import com.github.haixing_hu.util.config.Config;
import com.github.haixing_hu.util.config.MergingPolicy;
import com.github.haixing_hu.util.config.Property;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * The {@link DefaultConfig} class is the default implementation of the
 * {@link ModifiableConfig} interface.
 *
 * @author Haixing Hu
 */
public class DefaultConfig extends AbstractConfig {

  private static final long serialVersionUID = 3519879255214071861L;

  protected static final Logger LOGGER = LoggerFactory.getLogger(DefaultConfig.class);

  static {
    BinarySerialization.register(DefaultConfig.class, DefaultConfigBinarySerializer.INSTANCE);
    XmlSerialization.register(DefaultConfig.class, DefaultConfigXmlSerializer.INSTANCE);
  }

  protected String description;
  protected Map<String, DefaultProperty> properties;

  /**
   * Constructs an empty {@link DefaultConfig} object.
   */
  public DefaultConfig() {
    description = null;
    properties = new HashMap<String, DefaultProperty>();
  }

  @Override
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description.
   *
   * @param description
   *          the new description to set, or {@code null} if none.
   */
  public void setDescription(@Nullable final String description) {
    this.description = description;
  }

  @Override
  public boolean isEmpty() {
    return properties.isEmpty();
  }

  @Override
  public int size() {
    return properties.size();
  }

  @Override
  public Collection<DefaultProperty> getProperties() {
    return properties.values();
  }

  @Override
  public Set<String> getNames() {
    return properties.keySet();
  }

  @Override
  public boolean contains(final String name) {
    return properties.containsKey(name);
  }

  @Override
  public DefaultProperty get(final String name) {
    return properties.get(name);
  }

  public DefaultProperty set(final DefaultProperty property) {
    return properties.put(property.getName(), property);
  }

  /**
   * Adds a new {@link Property} to this {@link DefaultConfig} object.
   *
   * @param prop
   *          the new {@link Property} to be add.
   * @return the previous {@link Property} with the same name in this
   *         {@link DefaultConfig} object, or null if no previous
   *         {@link Property} with the same name.
   */
  public DefaultProperty add(final Property prop) {
    DefaultProperty newProp = null;
    if (prop instanceof DefaultProperty) {
      newProp = (DefaultProperty) prop;
    } else {
      newProp = new DefaultProperty();
      newProp.assign(prop);
    }
    return properties.put(prop.getName(), newProp);
  }

  /**
   * Add a new empty property with the specified name to this
   * {@link DefaultConfig} object.
   *
   * @param name
   *          the name of the new empty property.
   * @return the newly added {@link DefaultProperty} object.
   */
  public DefaultProperty add(final String name) {
    final DefaultProperty prop = new DefaultProperty(name);
    properties.put(name, prop);
    return prop;
  }

  /**
   * Add a new empty property with the specified name and type to this
   * {@link DefaultConfig} object.
   *
   * @param name
   *          the name of the new empty property.
   * @param type
   *          the type of the new empty property.
   * @return the newly added {@link DefaultProperty} object.
   */
  public DefaultProperty add(final String name, final Type type) {
    final DefaultProperty prop = new DefaultProperty(name, type);
    properties.put(name, prop);
    return prop;
  }

  public DefaultProperty remove(final String name) {
    return properties.remove(name);
  }

  public void setFinal(final String name, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setFinal(isFinal);
  }

  public void setType(final String name, final Type type) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setType(type);
  }

  public void setDescription(final String name, final String description) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setDescription(description);
  }

  public void setBoolean(final String name, final boolean value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setBooleanValue(value);
  }

  public void setBooleans(final String name, final boolean... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setBooleanValues(values);
  }

  public void setBooleans(final String name, final BooleanCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setBooleanValues(values);
  }

  public void addBoolean(final String name, final boolean value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addBooleanValue(value);
  }

  public void addBooleans(final String name, final boolean... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addBooleanValues(values);
  }

  public void addBooleans(final String name, final BooleanCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addBooleanValues(values);
  }

  public void setChar(final String name, final char value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setCharValue(value);
  }

  public void setChars(final String name, final char... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setCharValues(values);
  }

  public void setChars(final String name, final CharCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setCharValues(values);
  }

  public void addChar(final String name, final char value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addCharValue(value);
  }

  public void addChars(final String name, final char... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addCharValues(values);
  }

  public void addChars(final String name, final CharCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addCharValues(values);
  }

  public void setByte(final String name, final byte value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setByteValue(value);
  }

  public void setBytes(final String name, final byte... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setByteValues(values);
  }

  public void setBytes(final String name, final ByteCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setByteValues(values);
  }

  public void addByte(final String name, final byte value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addByteValue(value);
  }

  public void addBytes(final String name, final byte... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addByteValues(values);
  }

  public void addBytes(final String name, final ByteCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addByteValues(values);
  }

  public void setShort(final String name, final short value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setShortValue(value);
  }

  public void setShorts(final String name, final short... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setShortValues(values);
  }

  public void setShorts(final String name, final ShortCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setShortValues(values);
  }

  public void addShort(final String name, final short value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addShortValue(value);
  }

  public void addShorts(final String name, final short... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addShortValues(values);
  }

  public void addShorts(final String name, final ShortCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addShortValues(values);
  }

  public void setInt(final String name, final int value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setIntValue(value);
  }

  public void setInts(final String name, final int... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setIntValues(values);
  }

  public void setInts(final String name, final IntCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setIntValues(values);
  }

  public void addInt(final String name, final int value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addIntValue(value);
  }

  public void addInts(final String name, final int... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addIntValues(values);
  }

  public void addInts(final String name, final IntCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addIntValues(values);
  }

  public void setLong(final String name, final long value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setLongValue(value);
  }

  public void setLongs(final String name, final long... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setLongValues(values);
  }

  public void setLongs(final String name, final LongCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setLongValues(values);
  }

  public void addLong(final String name, final long value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addLongValue(value);
  }

  public void addLongs(final String name, final long... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addLongValues(values);
  }

  public void addLongs(final String name, final LongCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addLongValues(values);
  }

  public void setFloat(final String name, final float value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setFloatValue(value);
  }

  public void setFloats(final String name, final float... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setFloatValues(values);
  }

  public void setFloats(final String name, final FloatCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setFloatValues(values);
  }

  public void addFloat(final String name, final float value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addFloatValue(value);
  }

  public void addFloats(final String name, final float... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addFloatValues(values);
  }

  public void addFloats(final String name, final FloatCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addFloatValues(values);
  }

  public void setDouble(final String name, final double value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setDoubleValue(value);
  }

  public void setDoubles(final String name, final double... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setDoubleValues(values);
  }

  public void setDoubles(final String name, final DoubleCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setDoubleValues(values);
  }

  public void addDouble(final String name, final double value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addDoubleValue(value);
  }

  public void addDoubles(final String name, final double... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addDoubleValues(values);
  }

  public void addDoubles(final String name, final DoubleCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addDoubleValues(values);
  }

  public void setString(final String name, final String value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setStringValue(value);
  }

  public void setStrings(final String name, final String... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setStringValues(values);
  }

  public void setStrings(final String name, final Collection<String> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setStringValues(values);
  }

  public void addString(final String name, final String value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addStringValue(value);
  }

  public void addStrings(final String name, final String... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addStringValues(values);
  }

  public void addStrings(final String name, final Collection<String> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addStringValues(values);
  }

  public void setDate(final String name, final Date value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setDateValue(value);
  }

  public void setDates(final String name, final Date... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setDateValues(values);
  }

  public void setDates(final String name, final Collection<Date> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setDateValues(values);
  }

  public void addDate(final String name, final Date value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addDateValue(value);
  }

  public void addDates(final String name, final Date... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addDateValues(values);
  }

  public void addDates(final String name, final Collection<Date> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addDateValues(values);
  }

  public void setBigInteger(final String name, final BigInteger value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setBigIntegerValue(value);
  }

  public void setBigIntegers(final String name, final BigInteger... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setBigIntegerValues(values);
  }

  public void setBigIntegers(final String name,
      final Collection<BigInteger> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setBigIntegerValues(values);
  }

  public void addBigInteger(final String name, final BigInteger value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addBigIntegerValue(value);
  }

  public void addBigIntegers(final String name, final BigInteger... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addBigIntegerValues(values);
  }

  public void addBigIntegers(final String name,
      final Collection<BigInteger> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addBigIntegerValues(values);
  }

  public void setBigDecimal(final String name, final BigDecimal value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setBigDecimalValue(value);
  }

  public void setBigDecimals(final String name, final BigDecimal... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setBigDecimalValues(values);
  }

  public void setBigDecimals(final String name,
      final Collection<BigDecimal> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setBigDecimalValues(values);
  }

  public void addBigDecimal(final String name, final BigDecimal value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addBigDecimalValue(value);
  }

  public void addBigDecimals(final String name, final BigDecimal... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addBigDecimalValues(values);
  }

  public void addBigDecimals(final String name,
      final Collection<BigDecimal> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addBigDecimalValues(values);
  }

  public void setByteArray(final String name, final byte[] value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setByteArrayValue(value);
  }

  public void setByteArrays(final String name, final byte[]... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setByteArrayValues(values);
  }

  public void setByteArrays(final String name, final Collection<byte[]> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setByteArrayValues(values);
  }

  public void addByteArray(final String name, final byte[] value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addByteArrayValue(value);
  }

  public void addByteArrays(final String name, final byte[]... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addByteArrayValues(values);
  }

  public void addByteArrays(final String name, final Collection<byte[]> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addByteArrayValues(values);
  }

  public void setClass(final String name, final Class<?> value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setClassValue(value);
  }

  public void setClasses(final String name, final Class<?>... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setClassValues(values);
  }

  public void setClasses(final String name, final Collection<Class<?>> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.setClassValues(values);
  }

  public void addClass(final String name, final Class<?> value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addClassValue(value);
  }

  public void addClasses(final String name, final Class<?>... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addClassValues(values);
  }

  public void addClasses(final String name, final Collection<Class<?>> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = this.add(name);
    }
    prop.addClassValues(values);
  }

  public void merge(final Config config, final MergingPolicy policy) {
    merge(config, StringUtils.EMPTY, policy);
  }

  public void merge(final Config config, final String prefix,
      final MergingPolicy policy) {
    requireNonNull("config", config);
    requireNonNull("prefix", prefix);
    requireNonNull("policy", policy);
    if (this == config) {
      return;
    }
    LOGGER.trace("Start merging using policy {} ....", policy);
    switch (policy) {
      case SKIP:
        for (final Property thatProp : config.getProperties()) {
          final String name = thatProp.getName();
          if (! name.startsWith(prefix)) {
            continue;
          }
          if (contains(name)) {
            LOGGER.trace("Skip the existing property '{}'.", name);
          } else {
            LOGGER.trace("Adding a new property '{}'.", name);
            final DefaultProperty thisProp = this.add(name);
            thisProp.assign(thatProp);
          }
        }
        break;
      case UNION:
        for (final Property thatProp : config.getProperties()) {
          final String name = thatProp.getName();
          if (! name.startsWith(prefix)) {
            continue;
          }
          DefaultProperty thisProp = get(name);
          if (thisProp == null) {
            LOGGER.trace("Adding a new property '{}'.", name);
            thisProp = this.add(name);
            thisProp.assign(thatProp);
          } else if (thisProp.isFinal) {
            LOGGER.trace("Skip the final property '{}'.", name);
          } else {
            if (thisProp.getType() != thatProp.getType()) {
              LOGGER.trace("Overwrite a existing property '{}'.", name);
              thisProp.assign(thatProp);
            } else {
              LOGGER.trace("Union a existing property '{}'.", name);
              thisProp.unionValues(thatProp);
            }
          }
        }
        break;
      case OVERWRITE:
      default:
        for (final Property thatProp : config.getProperties()) {
          final String name = thatProp.getName();
          if (! name.startsWith(prefix)) {
            continue;
          }
          DefaultProperty thisProp = get(name);
          if (thisProp == null) {
            LOGGER.trace("Adding a new property '{}'.", name);
            thisProp = this.add(name);
            thisProp.assign(thatProp);
          } else if (thisProp.isFinal()) {
            LOGGER.trace("Skip the final property '{}'.", name);
          } else {
            LOGGER.trace("Overwrite a existing property '{}'.", name);
            thisProp.assign(thatProp);
          }
        }
        break;
    }
    LOGGER.trace("Merging finished.");
  }

  public void assign(final Config config) {
    LOGGER.trace("Start Assignment with another configuration ...");
    if (this != config) {
      LOGGER.trace("Removing all properties ... ");
      removeAll();
      for (final Property thatProp : config.getProperties()) {
        final String name = thatProp.getName();
        LOGGER.trace("Adding a new property '{}'.", name);
        final DefaultProperty thisProp = this.add(name);
        thisProp.assign(thatProp);
      }
    }
    LOGGER.trace("Assignment finished.");
  }

  public void assign(final Config config, final String prefix) {
    LOGGER.trace("Start Assignment with another configuration ...");
    if (this != config) {
      LOGGER.trace("Removing all properties ... ");
      removeAll();
      for (final Property thatProp : config.getProperties()) {
        final String name = thatProp.getName();
        if (name.startsWith(prefix)) {
          LOGGER.trace("Adding a new property '{}'.", name);
          final DefaultProperty thisProp = this.add(name);
          thisProp.assign(thatProp);
        }
      }
    } else {
      // this == config, just remove all properties not starting with prefix
      for (final String name : getNames()) {
        if (! name.startsWith(prefix)) {
          LOGGER.trace("Removing the property '{}'.", name);
          remove(name);
        }
      }
    }
    LOGGER.trace("Assignment finished.");
  }

  public DefaultProperty clear(final String name) {
    final DefaultProperty prop = get(name);
    if (prop != null) {
      prop.clear();
    }
    return prop;
  }

  public void removeAll() {
    properties.clear();
  }

  @Override
  public int hashCode() {
    return properties.hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    // note that we only need the obj to be instance of DefaultConfig,
    // that is, allowing the object of the sub-class of DefaultConfig
    // to be equal to the object of DefaultConfig, as long as they have
    // the same properties.
    if (! (obj instanceof DefaultConfig)) {
      return false;
    }
    final DefaultConfig other = (DefaultConfig) obj;
    return properties.equals(other.properties);
  }

  @Override
  public Config clone() {
    final DefaultConfig result = (DefaultConfig) super.clone();
    result.properties = new HashMap<String, DefaultProperty>();
    for (final DefaultProperty prop : properties.values()) {
      result.properties.put(prop.getName(), prop.clone());
    }
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
    .append("properties", properties)
    .toString();
  }

}
