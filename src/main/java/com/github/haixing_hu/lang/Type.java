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

package com.github.haixing_hu.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * The enumeration of common data types.
 *
 * Currently this enumeration supports the following types:
 *
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
 * <li>{@link Date}</li>
 * <li><code>byte[]</code></li>
 * <li>{@link Class}</li>
 * <li>{@link BigInteger}</li>
 * <li>{@link BigDecimal}</li>
 * </ul>
 *
 * @author Haixing Hu
 */
public enum Type {
  BOOLEAN,
  CHAR,
  BYTE,
  SHORT,
  INT,
  LONG,
  FLOAT,
  DOUBLE,
  STRING,
  DATE,
  BYTE_ARRAY,
  CLASS,
  BIG_INTEGER,
  BIG_DECIMAL;

  private static final Map<Class<?>, Type>  CLASS_TYPE_MAP;
  static {
    CLASS_TYPE_MAP = new HashMap<Class<?>, Type>();
    CLASS_TYPE_MAP.put(Boolean.class, BOOLEAN);
    CLASS_TYPE_MAP.put(Character.class, CHAR);
    CLASS_TYPE_MAP.put(Byte.class, BYTE);
    CLASS_TYPE_MAP.put(Short.class, SHORT);
    CLASS_TYPE_MAP.put(Integer.class, INT);
    CLASS_TYPE_MAP.put(Long.class, LONG);
    CLASS_TYPE_MAP.put(Float.class, FLOAT);
    CLASS_TYPE_MAP.put(Double.class, DOUBLE);
    CLASS_TYPE_MAP.put(String.class, STRING);
    CLASS_TYPE_MAP.put(Date.class, DATE);
    CLASS_TYPE_MAP.put(byte[].class, BYTE_ARRAY);
    CLASS_TYPE_MAP.put(Class.class, CLASS);
    CLASS_TYPE_MAP.put(BigInteger.class, BIG_INTEGER);
    CLASS_TYPE_MAP.put(BigDecimal.class, BIG_DECIMAL);
  }

  private static final Map<Type, Class<?>>  TYPE_CLASS_MAP;
  static {
    TYPE_CLASS_MAP = new HashMap<Type, Class<?>>();
    TYPE_CLASS_MAP.put(BOOLEAN, Boolean.class);
    TYPE_CLASS_MAP.put(CHAR, Character.class);
    TYPE_CLASS_MAP.put(BYTE, Byte.class);
    TYPE_CLASS_MAP.put(SHORT, Short.class);
    TYPE_CLASS_MAP.put(INT, Integer.class);
    TYPE_CLASS_MAP.put(LONG, Long.class);
    TYPE_CLASS_MAP.put(FLOAT, Float.class);
    TYPE_CLASS_MAP.put(DOUBLE, Double.class);
    TYPE_CLASS_MAP.put(STRING, String.class);
    TYPE_CLASS_MAP.put(DATE, Date.class);
    TYPE_CLASS_MAP.put(BYTE_ARRAY, byte[].class);
    TYPE_CLASS_MAP.put(CLASS, Class.class);
    TYPE_CLASS_MAP.put(BIG_INTEGER, BigInteger.class);
    TYPE_CLASS_MAP.put(BIG_DECIMAL, BigDecimal.class);
  }

  public static Type forClass(final Class<?> clazz) {
    return CLASS_TYPE_MAP.get(clazz);
  }

  public static Class<?> toClass(final Type type) {
    return TYPE_CLASS_MAP.get(type);
  }
}
