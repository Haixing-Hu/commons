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
package com.github.haixing_hu.lang;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.Nullable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.collection.primitive.BooleanCollection;
import com.github.haixing_hu.collection.primitive.BooleanIterator;
import com.github.haixing_hu.collection.primitive.BooleanList;
import com.github.haixing_hu.collection.primitive.ByteCollection;
import com.github.haixing_hu.collection.primitive.ByteIterator;
import com.github.haixing_hu.collection.primitive.ByteList;
import com.github.haixing_hu.collection.primitive.CharCollection;
import com.github.haixing_hu.collection.primitive.CharIterator;
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
import com.github.haixing_hu.io.InputUtils;
import com.github.haixing_hu.io.OutputUtils;
import com.github.haixing_hu.text.BooleanFormat;
import com.github.haixing_hu.text.DateFormat;
import com.github.haixing_hu.text.NumberFormat;
import com.github.haixing_hu.text.ParseUtils;
import com.github.haixing_hu.text.TextParseException;
import com.github.haixing_hu.text.xml.DomUtils;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.util.codec.string.HexCodec;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * Provides utility functions to deal with the common types.
 *
 * @author Haixing Hu
 */
public final class TypeUtils {

  public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

  /**
   * Converts a {@link Type} object to a {@link Class} object.
   *
   * @param type
   *          a {@link Type} object to be converted.
   * @return the corresponding {@link Class} object of the {@link Type} object.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   */
  public static Class<?> typeToClass(final Type type)
      throws UnsupportedTypeException {
    switch (type) {
      case BIG_DECIMAL:
        return BigDecimal.class;
      case BIG_INTEGER:
        return BigInteger.class;
      case BOOLEAN:
        return Boolean.class;
      case BYTE:
        return Byte.class;
      case BYTE_ARRAY:
        return byte[].class;
      case CHAR:
        return Character.class;
      case CLASS:
        return Class.class;
      case DATE:
        return Date.class;
      case DOUBLE:
        return Double.class;
      case FLOAT:
        return Float.class;
      case INT:
        return Integer.class;
      case LONG:
        return Long.class;
      case SHORT:
        return Short.class;
      case STRING:
        return String.class;
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a {@link Class} object to a {@link Type} object.
   *
   * @param cls
   *          a {@link Class} object to be converted.
   * @return the corresponding {@link Type} object of the {@code Class}
   *         object.
   * @throws UnsupportedTypeException
   *           if the {@link Class} object has no corresponding {@link Type}
   *           object.
   */
  public static Type classToType(final Class<?> cls)
      throws UnsupportedTypeException {
    if (cls == BigDecimal.class) {
      return Type.BIG_DECIMAL;
    } else if (cls == BigInteger.class) {
      return Type.BIG_INTEGER;
    } else if (cls == Boolean.class) {
      return Type.BOOLEAN;
    } else if (cls == Byte.class) {
      return Type.BYTE;
    } else if (cls == byte[].class) {
      return Type.BYTE_ARRAY;
    } else if (cls == Character.class) {
      return Type.CHAR;
    } else if (cls == Class.class) {
      return Type.CLASS;
    } else if (cls == Date.class) {
      return Type.DATE;
    } else if (cls == Double.class) {
      return Type.DOUBLE;
    } else if (cls == Float.class) {
      return Type.FLOAT;
    } else if (cls == Long.class) {
      return Type.LONG;
    } else if (cls == Short.class) {
      return Type.SHORT;
    } else if (cls == String.class) {
      return Type.STRING;
    } else {
      throw new UnsupportedTypeException(cls.getName());
    }
  }

  /**
   * Creates an array of the specified type.
   *
   * @param type
   *          a specified type.
   * @param length
   *          the length of the array to be created.
   * @return a new array of the specified type with the specified length. each
   *         elements of the array is initialized with null.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   */
  public static Object createArray(final Type type, final int length)
      throws UnsupportedTypeException {
    switch (type) {
      case BOOLEAN:
        return new boolean[length];
      case CHAR:
        return new char[length];
      case BYTE:
        return new byte[length];
      case SHORT:
        return new short[length];
      case INT:
        return new int[length];
      case LONG:
        return new long[length];
      case FLOAT:
        return new float[length];
      case DOUBLE:
        return new double[length];
      case STRING:
        return new String[length];
      case DATE:
        return new Date[length];
      case BYTE_ARRAY:
        return new byte[length][];
      case CLASS:
        return new Class[length];
      case BIG_INTEGER:
        return new BigInteger[length];
      case BIG_DECIMAL:
        return new BigDecimal[length];
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Creates a list of the specified type.
   *
   * @param type
   *          a specified type.
   * @return a new empty list of the specified type. If the type is primitive
   *         type, the corresponding primitive array list is returned;
   *         otherwise, the generic array list is returned.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   */
  public static Object createList(final Type type)
      throws UnsupportedTypeException {
    switch (type) {
      case BOOLEAN:
        return new ArrayBooleanList();
      case CHAR:
        return new ArrayCharList();
      case BYTE:
        return new ArrayByteList();
      case SHORT:
        return new ArrayShortList();
      case INT:
        return new ArrayIntList();
      case LONG:
        return new ArrayLongList();
      case FLOAT:
        return new ArrayFloatList();
      case DOUBLE:
        return new ArrayDoubleList();
      case STRING:
        return new ArrayList<String>();
      case DATE:
        return new ArrayList<Date>();
      case BYTE_ARRAY:
        return new ArrayList<byte[]>();
      case CLASS:
        return new ArrayList<Class<?>>();
      case BIG_INTEGER:
        return new ArrayList<BigInteger>();
      case BIG_DECIMAL:
        return new ArrayList<BigDecimal>();
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Clone an object of a specified type.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be cloned.
   * @param object
   *          the object to be cloned. It could be null.
   * @return the cloned copy of the object; or null if the object is null.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be cloned.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   */
  public static Object cloneObject(final Type type,
      @Nullable final Object object) throws ClassCastException,
      UnsupportedTypeException {
    if (object == null) {
      return null;
    }
    switch (type) {
      case DATE:
        return Assignment.clone((Date) object);
      case BYTE_ARRAY:
        return Assignment.clone((byte[]) object);
      case STRING: // drop down
      case BOOLEAN: // drop down
      case CHAR: // drop down
      case BYTE: // drop down
      case SHORT: // drop down
      case INT: // drop down
      case LONG: // drop down
      case FLOAT: // drop down
      case DOUBLE: // drop down
      case CLASS: // drop down
      case BIG_DECIMAL: // drop down
      case BIG_INTEGER:
        // the other types are immutable, thus don't need to clone it
        return object;
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Clones a collection of objects of a specified type as an array.
   *
   * @param type
   *          the corresponding {@link Type} object of the type of objects in
   *          the collection to be cloned.
   * @param col
   *          the collection to be cloned. It could be null. If the type is
   *          primitive type, the collection must be a primitive collection;
   *          otherwise, it must be a generic collection.
   * @return a cloned copy of the collection as an array; or null if the
   *         collection to be cloned is null.
   * @throws ClassCastException
   *           if the type of the objects of the collection to be cloned does
   *           not correspond to the specified {@link Type} object.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   */
  public static Object cloneCollectionAsArray(final Type type,
      @Nullable final Object col) throws ClassCastException,
      UnsupportedTypeException {
    if (col == null) {
      return null;
    }
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        return values.toArray();
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        return values.toArray();
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        return values.toArray();
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        return values.toArray();
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        return values.toArray();
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        return values.toArray();
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        return values.toArray();
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        return values.toArray();
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        return values.toArray(new String[values.size()]);
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final ArrayList<Date> result = new ArrayList<Date>(values.size());
        for (final Date value : values) {
          result.add(Assignment.clone(value));
        }
        return result;
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final ArrayList<byte[]> result = new ArrayList<byte[]>(values.size());
        for (final byte[] value : values) {
          result.add(Assignment.clone(value));
        }
        return result;
      }
      case CLASS: {
        @SuppressWarnings("unchecked")
        final Collection<Class<?>> values = (Collection<Class<?>>) col;
        return values.toArray(new Class<?>[values.size()]);
      }
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        return values.toArray(new BigInteger[values.size()]);
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        return values.toArray(new BigDecimal[values.size()]);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Clones an array of objects of a specified type.
   *
   * @param type
   *          the corresponding {@link Type} object of the type of objects of
   *          the array to be cloned.
   * @param array
   *          the array to be cloned. It could be null. If the type is primitive
   *          type, the array must be the array of that primitive type.
   * @return a cloned copy of the array; or null if the array to be cloned is
   *         null.
   * @throws ClassCastException
   *           if the type of the objects of the array to be cloned does not
   *           correspond to the specified {@link Type} object.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   */
  public static Object cloneArrayAsArray(final Type type, @Nullable final Object array)
      throws ClassCastException, UnsupportedTypeException {
    if (array == null) {
      return null;
    }
    switch (type) {
      case BOOLEAN: {
        final boolean[] values = (boolean[]) array;
        final boolean[] result = new boolean[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case CHAR: {
        final char[] values = (char[]) array;
        final char[] result = new char[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case BYTE: {
        final byte[] values = (byte[]) array;
        final byte[] result = new byte[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case SHORT: {
        final short[] values = (short[]) array;
        final short[] result = new short[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case INT: {
        final int[] values = (int[]) array;
        final int[] result = new int[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case LONG: {
        final long[] values = (long[]) array;
        final long[] result = new long[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case FLOAT: {
        final float[] values = (float[]) array;
        final float[] result = new float[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case DOUBLE: {
        final double[] values = (double[]) array;
        final double[] result = new double[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case STRING: {
        final String[] values = (String[]) array;
        final String[] result = new String[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case DATE: {
        final Date[] values = (Date[]) array;
        final Date[] result = new Date[values.length];
        for (int i = 0; i < values.length; ++i) {
          result[i] = Assignment.clone(values[i]);
        }
        return result;
      }
      case BYTE_ARRAY: {
        final byte[][] values = (byte[][]) array;
        final byte[][] result = new byte[values.length][];
        for (int i = 0; i < values.length; ++i) {
          result[i] = Assignment.clone(values[i]);
        }
        return result;
      }
      case CLASS: {
        final Class<?>[] values = (Class<?>[]) array;
        final Class<?>[] result = new Class<?>[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case BIG_INTEGER: {
        final BigInteger[] values = (BigInteger[]) array;
        final BigInteger[] result = new BigInteger[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case BIG_DECIMAL: {
        final BigDecimal[] values = (BigDecimal[]) array;
        final BigDecimal[] result = new BigDecimal[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Clones a collection of objects of a specified type, returning a
   * {@link ArrayList} containing the cloned copies of each objects in the
   * collection.
   *
   * @param type
   *          the corresponding {@link Type} object of the type of objects of
   *          the collection to be cloned.
   * @param col
   *          the collection to be cloned. It could be null. If the type is
   *          primitive type, the collection must be a primitive collection;
   *          otherwise, it must be a generic collection.
   * @return a cloned copy of the collection as a {@link ArrayList}; or null if
   *         the collection to be cloned is null.
   * @throws ClassCastException
   *           if the type of the objects of the collection to be cloned does
   *           not correspond to the specified {@link Type} object.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   */
  public static Object cloneCollectionAsList(final Type type,
      @Nullable final Object col) throws ClassCastException,
      UnsupportedTypeException {
    if (col == null) {
      return null;
    }
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        return new ArrayBooleanList(values);
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        return new ArrayCharList(values);
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        return new ArrayByteList(values);
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        return new ArrayShortList(values);
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        return new ArrayIntList(values);
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        return new ArrayLongList(values);
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        return new ArrayFloatList(values);
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        return new ArrayDoubleList(values);
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        return new ArrayList<String>(values);
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final ArrayList<Date> result = new ArrayList<Date>(values.size());
        for (final Date value : values) {
          result.add(Assignment.clone(value));
        }
        return result;
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final ArrayList<byte[]> result = new ArrayList<byte[]>(values.size());
        for (final byte[] value : values) {
          result.add(Assignment.clone(value));
        }
        return result;
      }
      case CLASS: {
        @SuppressWarnings("unchecked")
        final Collection<Class<?>> values = (Collection<Class<?>>) col;
        return new ArrayList<Class<?>>(values);
      }
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        return new ArrayList<BigInteger>(values);
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        return new ArrayList<BigDecimal>(values);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Clones an array of objects of a specified type, returning a
   * {@link ArrayList} containing the cloned copies of each objects in the
   * collection.
   *
   * @param type
   *          the corresponding {@link Type} object of the type of objects of
   *          the array to be cloned.
   * @param array
   *          the array to be cloned. It could be null. If the type is primitive
   *          type, the array must be the array of that primitive type.
   * @return a cloned copy of the collection as an array list; or null if
   *         the array to be cloned is null. If the type is primitive type, the
   *         returned list the the primitive array list.
   * @throws ClassCastException
   *           if the type of the objects of the array to be cloned does not
   *           correspond to the specified {@link Type} object.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   */
  public static Object cloneArrayAsList(final Type type,
      @Nullable final Object array) throws ClassCastException,
      UnsupportedTypeException {
    if (array == null) {
      return null;
    }
    switch (type) {
      case BOOLEAN: {
        final boolean[] values = (boolean[]) array;
        final ArrayBooleanList result = new ArrayBooleanList(values.length);
        for (final boolean value : values) {
          result.add(value);
        }
        return result;
      }
      case CHAR: {
        final char[] values = (char[]) array;
        final ArrayCharList result = new ArrayCharList(values.length);
        for (final char value : values) {
          result.add(value);
        }
        return result;
      }
      case BYTE: {
        final byte[] values = (byte[]) array;
        final ArrayByteList result = new ArrayByteList(values.length);
        for (final byte value : values) {
          result.add(value);
        }
        return result;
      }
      case SHORT: {
        final short[] values = (short[]) array;
        final ArrayShortList result = new ArrayShortList(values.length);
        for (final short value : values) {
          result.add(value);
        }
        return result;
      }
      case INT: {
        final int[] values = (int[]) array;
        final ArrayIntList result = new ArrayIntList(values.length);
        for (final int value : values) {
          result.add(value);
        }
        return result;
      }
      case LONG: {
        final long[] values = (long[]) array;
        final ArrayLongList result = new ArrayLongList(values.length);
        for (final long value : values) {
          result.add(value);
        }
        return result;
      }
      case FLOAT: {
        final float[] values = (float[]) array;
        final ArrayFloatList result = new ArrayFloatList(values.length);
        for (final float value : values) {
          result.add(value);
        }
        return result;
      }
      case DOUBLE: {
        final double[] values = (double[]) array;
        final ArrayDoubleList result = new ArrayDoubleList(values.length);
        for (final double value : values) {
          result.add(value);
        }
        return result;
      }
      case STRING: {
        final String[] values = (String[]) array;
        final List<String> result = new ArrayList<String>(values.length);
        for (final String value : values) {
          result.add(value);
        }
        return result;
      }
      case DATE: {
        final Date[] values = (Date[]) array;
        final List<Date> result = new ArrayList<Date>(values.length);
        for (final Date value : values) {
          result.add(Assignment.clone(value));
        }
        return result;
      }
      case BYTE_ARRAY: {
        final byte[][] values = (byte[][]) array;
        final List<byte[]> result = new ArrayList<byte[]>(values.length);
        for (final byte[] value : values) {
          result.add(Assignment.clone(value));
        }
        return result;
      }
      case CLASS: {
        final Class<?>[] values = (Class<?>[]) array;
        final List<Class<?>> result = new ArrayList<Class<?>>(values.length);
        for (final Class<?> value : values) {
          result.add(value);
        }
        return result;
      }
      case BIG_INTEGER: {
        final BigInteger[] values = (BigInteger[]) array;
        final List<BigInteger> result = new ArrayList<BigInteger>(values.length);
        for (final BigInteger value : values) {
          result.add(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        final BigDecimal[] values = (BigDecimal[]) array;
        final List<BigDecimal> result = new ArrayList<BigDecimal>(values.length);
        for (final BigDecimal value : values) {
          result.add(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Parse an object of a specified type from a string representation.
   *
   * @param type
   *          the type of the object to be parsed.
   * @param str
   *          the string representation of the object to be parsed. It could
   *          be null, and it don't need to be stripped before calling this
   *          function.
   * @return the object of the parsed result; or null if the string is null.
   * @throws TextParseException
   *           if the string representation can not be successfully parsed.
   * @throws ClassNotFoundException
   *           if the {@code type} is a {@link Type} object corresponding
   *           to {@link Class} class, and the string representation of the
   *           class can not be initialized to a {@link Class} instance by the
   *           current class loader.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   */
  public static Object parseObject(final Type type, @Nullable final String str)
      throws TextParseException, ClassNotFoundException,
      UnsupportedTypeException {
    if (str == null) {
      return null;
    }
    switch (type) {
      case BOOLEAN: {
        final BooleanFormat bf = new BooleanFormat();
        final boolean value = bf.parse(str);
        if (bf.fail()) {
          throw new TextParseException(str, bf.getParsePosition());
        }
        return Boolean.valueOf(value);
      }
      case CHAR: {
        final char value = ParseUtils.parseChar(str);
        return Character.valueOf(value);
      }
      case BYTE: {
        final NumberFormat nf = new NumberFormat();
        final byte value = nf.parseByte(str);
        if (nf.fail()) {
          throw new TextParseException(str, nf.getParsePosition());
        }
        return Byte.valueOf(value);
      }
      case SHORT: {
        final NumberFormat nf = new NumberFormat();
        final short value = nf.parseShort(str);
        if (nf.fail()) {
          throw new TextParseException(str, nf.getParsePosition());
        }
        return Short.valueOf(value);
      }
      case INT: {
        final NumberFormat nf = new NumberFormat();
        final int value = nf.parseInt(str);
        if (nf.fail()) {
          throw new TextParseException(str, nf.getParsePosition());
        }
        return Integer.valueOf(value);
      }
      case LONG: {
        final NumberFormat nf = new NumberFormat();
        final long value = nf.parseLong(str);
        if (nf.fail()) {
          throw new TextParseException(str, nf.getParsePosition());
        }
        return Long.valueOf(value);
      }
      case FLOAT: {
        final NumberFormat nf = new NumberFormat();
        final float value = nf.parseFloat(str);
        if (nf.fail()) {
          throw new TextParseException(str, nf.getParsePosition());
        }
        return Float.valueOf(value);
      }
      case DOUBLE: {
        final NumberFormat nf = new NumberFormat();
        final double value = nf.parseDouble(str);
        if (nf.fail()) {
          throw new TextParseException(str, nf.getParsePosition());
        }
        return Double.valueOf(value);
      }
      case STRING:
        return str;
      case DATE: {
        final DateFormat nf = new DateFormat();
        final Date value = nf.parse(str);
        if (nf.fail()) {
          throw new TextParseException(str, nf.getParsePosition());
        }
        return value;
      }
      case BYTE_ARRAY: {
        final HexCodec codec = new HexCodec();
        final byte[] value = codec.decode(str);
        if (codec.fail()) {
          throw new TextParseException(str, codec.getParsePosition());
        }
        return value;
      }
      case CLASS:
        return ClassUtils.getClass(StringUtils.strip(str));
      case BIG_INTEGER: {
        final NumberFormat nf = new NumberFormat();
        final BigInteger value = nf.parseBigInteger(str);
        if (nf.fail()) {
          throw new TextParseException(str, nf.getParsePosition());
        }
        return value;
      }
      case BIG_DECIMAL: {
        final NumberFormat nf = new NumberFormat();
        final BigDecimal value = nf.parseBigDecimal(str);
        if (nf.fail()) {
          throw new TextParseException(str, nf.getParsePosition());
        }
        return value;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }


  /**
   * Converts an object of the specified type to a {@code boolean} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param value
   *          the object to be converted, which could be null.
   * @return a {@code boolean} value corresponds to the object to be
   *         converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code boolean}
   *           value.
   */
  public static boolean objectAsBoolean(final Type type, @Nullable final Object value)
      throws ClassCastException, UnsupportedTypeException, TypeConvertException {
    switch (type) {
      case BOOLEAN:
        return BooleanUtils.toPrimitive((Boolean) value);
      case CHAR:
        return CharUtils.toBoolean((Character) value);
      case BYTE:
        return ByteUtils.toBoolean((Byte) value);
      case SHORT:
        return ShortUtils.toBoolean((Short) value);
      case INT:
        return IntUtils.toBoolean((Integer) value);
      case LONG:
        return LongUtils.toBoolean((Long) value);
      case FLOAT:
        return FloatUtils.toBoolean((Float) value);
      case DOUBLE:
        return DoubleUtils.toBoolean((Double) value);
      case STRING:
        return StringUtils.toBoolean((String) value);
      case DATE:
        return DateUtils.toBoolean((Date) value);
      case BYTE_ARRAY:
        return ByteArrayUtils.toBoolean((byte[]) value);
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER:
        return BigIntegerUtils.toBoolean((BigInteger) value);
      case BIG_DECIMAL:
        return BigDecimalUtils.toBoolean((BigDecimal) value);
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts an object of the specified type to a {@code char} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param value
   *          the object to be converted, which could be null.
   * @return a {@code char} value corresponds to the object to be
   *         converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code char}
   *           value.
   */
  public static char objectAsChar(final Type type, @Nullable final Object value)
      throws ClassCastException, UnsupportedTypeException, TypeConvertException {
    switch (type) {
      case BOOLEAN:
        return BooleanUtils.toChar((Boolean) value);
      case CHAR:
        return CharUtils.toPrimitive((Character) value);
      case BYTE:
        return ByteUtils.toChar((Byte) value);
      case SHORT:
        return ShortUtils.toChar((Short) value);
      case INT:
        return IntUtils.toChar((Integer) value);
      case LONG:
        return LongUtils.toChar((Long) value);
      case FLOAT:
        return FloatUtils.toChar((Float) value);
      case DOUBLE:
        return DoubleUtils.toChar((Double) value);
      case STRING:
        return StringUtils.toChar((String) value);
      case DATE:
        return DateUtils.toChar((Date) value);
      case BYTE_ARRAY:
        return ByteArrayUtils.toChar((byte[]) value);
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER:
        return BigIntegerUtils.toChar((BigInteger) value);
      case BIG_DECIMAL:
        return BigDecimalUtils.toChar((BigDecimal) value);
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts an object of the specified type to a {@code byte} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param value
   *          the object to be converted, which could be null.
   * @return a {@code byte} value corresponds to the object to be
   *         converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code byte}
   *           value.
   */
  public static byte objectAsByte(final Type type, @Nullable final Object value)
      throws ClassCastException, UnsupportedTypeException, TypeConvertException {
    switch (type) {
      case BOOLEAN:
        return BooleanUtils.toByte((Boolean) value);
      case CHAR:
        return CharUtils.toByte((Character) value);
      case BYTE:
        return ByteUtils.toPrimitive((Byte) value);
      case SHORT:
        return ShortUtils.toByte((Short) value);
      case INT:
        return IntUtils.toByte((Integer) value);
      case LONG:
        return LongUtils.toByte((Long) value);
      case FLOAT:
        return FloatUtils.toByte((Float) value);
      case DOUBLE:
        return DoubleUtils.toByte((Double) value);
      case STRING:
        return StringUtils.toByte((String) value);
      case DATE:
        return DateUtils.toByte((Date) value);
      case BYTE_ARRAY:
        return ByteArrayUtils.toByte((byte[]) value);
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER:
        return BigIntegerUtils.toByte((BigInteger) value);
      case BIG_DECIMAL:
        return BigDecimalUtils.toByte((BigDecimal) value);
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts an object of the specified type to a {@code short} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param value
   *          the object to be converted, which could be null.
   * @return a {@code short} value corresponds to the object to be
   *         converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code short}
   *           value.
   */
  public static short objectAsShort(final Type type, @Nullable final Object value)
      throws ClassCastException, UnsupportedTypeException, TypeConvertException {
    switch (type) {
      case BOOLEAN:
        return BooleanUtils.toShort((Boolean) value);
      case CHAR:
        return CharUtils.toShort((Character) value);
      case BYTE:
        return ByteUtils.toShort((Byte) value);
      case SHORT:
        return ShortUtils.toPrimitive((Short) value);
      case INT:
        return IntUtils.toShort((Integer) value);
      case LONG:
        return LongUtils.toShort((Long) value);
      case FLOAT:
        return FloatUtils.toShort((Float) value);
      case DOUBLE:
        return DoubleUtils.toShort((Double) value);
      case STRING:
        return StringUtils.toShort((String) value);
      case DATE:
        return DateUtils.toShort((Date) value);
      case BYTE_ARRAY:
        return ByteArrayUtils.toShort((byte[]) value);
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER:
        return BigIntegerUtils.toShort((BigInteger) value);
      case BIG_DECIMAL:
        return BigDecimalUtils.toShort((BigDecimal) value);
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts an object of the specified type to a {@code int} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param value
   *          the object to be converted, which could be null.
   * @return a {@code int} value corresponds to the object to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code int} value.
   */
  public static int objectAsInt(final Type type, @Nullable final Object value)
      throws ClassCastException, UnsupportedTypeException, TypeConvertException {
    switch (type) {
      case BOOLEAN:
        return BooleanUtils.toInt((Boolean) value);
      case CHAR:
        return CharUtils.toInt((Character) value);
      case BYTE:
        return ByteUtils.toInt((Byte) value);
      case SHORT:
        return ShortUtils.toInt((Short) value);
      case INT:
        return IntUtils.toPrimitive((Integer) value);
      case LONG:
        return LongUtils.toInt((Long) value);
      case FLOAT:
        return FloatUtils.toInt((Float) value);
      case DOUBLE:
        return DoubleUtils.toInt((Double) value);
      case STRING:
        return StringUtils.toInt((String) value);
      case DATE:
        return DateUtils.toInt((Date) value);
      case BYTE_ARRAY:
        return ByteArrayUtils.toInt((byte[]) value);
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER:
        return BigIntegerUtils.toInt((BigInteger) value);
      case BIG_DECIMAL:
        return BigDecimalUtils.toInt((BigDecimal) value);
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts an object of the specified type to a {@code long} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param value
   *          the object to be converted, which could be null.
   * @return a {@code long} value corresponds to the object to be
   *         converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code long}
   *           value.
   */
  public static long objectAsLong(final Type type, @Nullable final Object value)
      throws ClassCastException, UnsupportedTypeException, TypeConvertException {
    switch (type) {
      case BOOLEAN:
        return BooleanUtils.toLong((Boolean) value);
      case CHAR:
        return CharUtils.toLong((Character) value);
      case BYTE:
        return ByteUtils.toLong((Byte) value);
      case SHORT:
        return ShortUtils.toLong((Short) value);
      case INT:
        return IntUtils.toLong((Integer) value);
      case LONG:
        return LongUtils.toPrimitive((Long) value);
      case FLOAT:
        return FloatUtils.toLong((Float) value);
      case DOUBLE:
        return DoubleUtils.toLong((Double) value);
      case STRING:
        return StringUtils.toLong((String) value);
      case DATE:
        return DateUtils.toLong((Date) value);
      case BYTE_ARRAY:
        return ByteArrayUtils.toLong((byte[]) value);
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER:
        return BigIntegerUtils.toLong((BigInteger) value);
      case BIG_DECIMAL:
        return BigDecimalUtils.toLong((BigDecimal) value);
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts an object of the specified type to a {@code float} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param value
   *          the object to be converted, which could be null.
   * @return a {@code float} value corresponds to the object to be
   *         converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code float}
   *           value.
   */
  public static float objectAsFloat(final Type type, @Nullable final Object value)
      throws ClassCastException, UnsupportedTypeException, TypeConvertException {
    switch (type) {
      case BOOLEAN:
        return BooleanUtils.toFloat((Boolean) value);
      case CHAR:
        return CharUtils.toFloat((Character) value);
      case BYTE:
        return ByteUtils.toFloat((Byte) value);
      case SHORT:
        return ShortUtils.toFloat((Short) value);
      case INT:
        return IntUtils.toFloat((Integer) value);
      case LONG:
        return LongUtils.toFloat((Long) value);
      case FLOAT:
        return FloatUtils.toPrimitive((Float) value);
      case DOUBLE:
        return DoubleUtils.toFloat((Double) value);
      case STRING:
        return StringUtils.toFloat((String) value);
      case DATE:
        return DateUtils.toFloat((Date) value);
      case BYTE_ARRAY:
        return ByteArrayUtils.toFloat((byte[]) value);
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER:
        return BigIntegerUtils.toFloat((BigInteger) value);
      case BIG_DECIMAL:
        return BigDecimalUtils.toFloat((BigDecimal) value);
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts an object of the specified type to a {@code double} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param value
   *          the object to be converted, which could be null.
   * @return a {@code double} value corresponds to the object to be
   *         converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code double}
   *           value.
   */
  public static double objectAsDouble(final Type type, @Nullable final Object value)
      throws ClassCastException, UnsupportedTypeException, TypeConvertException {
    switch (type) {
      case BOOLEAN:
        return BooleanUtils.toDouble((Boolean) value);
      case CHAR:
        return CharUtils.toDouble((Character) value);
      case BYTE:
        return ByteUtils.toDouble((Byte) value);
      case SHORT:
        return ShortUtils.toDouble((Short) value);
      case INT:
        return IntUtils.toDouble((Integer) value);
      case LONG:
        return LongUtils.toDouble((Long) value);
      case FLOAT:
        return FloatUtils.toDouble((Float) value);
      case DOUBLE:
        return DoubleUtils.toPrimitive((Double) value);
      case STRING:
        return StringUtils.toDouble((String) value);
      case DATE:
        return DateUtils.toDouble((Date) value);
      case BYTE_ARRAY:
        return ByteArrayUtils.toDouble((byte[]) value);
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER:
        return BigIntegerUtils.toDouble((BigInteger) value);
      case BIG_DECIMAL:
        return BigDecimalUtils.toDouble((BigDecimal) value);
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts an object of the specified type to a {@link String} object.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param value
   *          the object to be converted, which could be null.
   * @return a {@link String} object corresponds to the object to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   */
  public static String objectAsString(final Type type, @Nullable final Object value)
      throws ClassCastException, UnsupportedTypeException {
    switch (type) {
      case BOOLEAN:
        return BooleanUtils.toString((Boolean) value);
      case CHAR:
        return CharUtils.toString((Character) value);
      case BYTE:
        return ByteUtils.toString((Byte) value);
      case SHORT:
        return ShortUtils.toString((Short) value);
      case INT:
        return IntUtils.toString((Integer) value);
      case LONG:
        return LongUtils.toString((Long) value);
      case FLOAT:
        return FloatUtils.toString((Float) value);
      case DOUBLE:
        return DoubleUtils.toString((Double) value);
      case STRING:
        return (String) value;
      case DATE:
        return DateUtils.toString((Date) value, DateUtils.DEFAULT_LOCAL_DATETIME_PATTERN);
      case BYTE_ARRAY:
        return ByteArrayUtils.toString((byte[]) value);
      case CLASS:
        return (value == null ? null : ((Class<?>) value).getName());
      case BIG_INTEGER:
        return BigIntegerUtils.toString((BigInteger) value);
      case BIG_DECIMAL:
        return BigDecimalUtils.toString((BigDecimal) value);
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts an object of the specified type to a {@link Date} object.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param value
   *          the object to be converted, which could be null.
   * @return a {@link Date} object corresponds to the object to be converted.
   *         Note that if the specified object is of the {@link Date} class, the
   *         returned {@link Date} object is a cloned copy of the original
   *         value.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@link Date} object.
   */
  public static Date objectAsDate(final Type type, @Nullable final Object value)
      throws ClassCastException, UnsupportedTypeException, TypeConvertException {
    switch (type) {
      case BOOLEAN:
        return BooleanUtils.toDate((Boolean) value);
      case CHAR:
        return CharUtils.toDate((Character) value);
      case BYTE:
        return ByteUtils.toDate((Byte) value);
      case SHORT:
        return ShortUtils.toDate((Short) value);
      case INT:
        return IntUtils.toDate((Integer) value);
      case LONG:
        return LongUtils.toDate((Long) value);
      case FLOAT:
        return FloatUtils.toDate((Float) value);
      case DOUBLE:
        return DoubleUtils.toDate((Double) value);
      case STRING:
        return StringUtils.toDate((String) value);
      case DATE:
        return Assignment.clone((Date) value);
      case BYTE_ARRAY:
        return ByteArrayUtils.toDate((byte[]) value);
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER:
        return BigIntegerUtils.toDate((BigInteger) value);
      case BIG_DECIMAL:
        return BigDecimalUtils.toDate((BigDecimal) value);
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts an object of the specified type to a {@code byte[]} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param value
   *          the object to be converted, which could be null.
   * @return a {@code byte[]} value corresponds to the object to be
   *         converted. Note that if the specified object is of the
   *         {@code byte[]} type, the returned {@code byte[]} value is
   *         a cloned copy of the original value.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code byte[]}
   *           value.
   */
  public static byte[] objectAsByteArray(final Type type, @Nullable final Object value)
      throws ClassCastException, UnsupportedTypeException, TypeConvertException {
    switch (type) {
      case BOOLEAN:
        return BooleanUtils.toByteArray((Boolean) value);
      case CHAR:
        return CharUtils.toByteArray((Character) value);
      case BYTE:
        return ByteUtils.toByteArray((Byte) value);
      case SHORT:
        return ShortUtils.toByteArray((Short) value);
      case INT:
        return IntUtils.toByteArray((Integer) value);
      case LONG:
        return LongUtils.toByteArray((Long) value);
      case FLOAT:
        return FloatUtils.toByteArray((Float) value);
      case DOUBLE:
        return DoubleUtils.toByteArray((Double) value);
      case STRING:
        return StringUtils.toByteArray((String) value);
      case DATE:
        return DateUtils.toByteArray((Date) value);
      case BYTE_ARRAY:
        return Assignment.clone((byte[]) value);
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER:
        return BigIntegerUtils.toByteArray((BigInteger) value);
      case BIG_DECIMAL:
        return BigDecimalUtils.toByteArray((BigDecimal) value);
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts an object of the specified type to a {@link Class} object.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param value
   *          the object to be converted, which could be null.
   * @return a {@link Class} object corresponds to the object to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@link Class} object.
   */
  public static Class<?> objectAsClass(final Type type, @Nullable final Object value)
      throws ClassCastException, UnsupportedTypeException, TypeConvertException {
    switch (type) {
      case BOOLEAN:
        return BooleanUtils.toClass((Boolean) value);
      case CHAR:
        return CharUtils.toClass((Character) value);
      case BYTE:
        return ByteUtils.toClass((Byte) value);
      case SHORT:
        return ShortUtils.toClass((Short) value);
      case INT:
        return IntUtils.toClass((Integer) value);
      case LONG:
        return LongUtils.toClass((Long) value);
      case FLOAT:
        return FloatUtils.toClass((Float) value);
      case DOUBLE:
        return DoubleUtils.toClass((Double) value);
      case STRING:
        return StringUtils.toClass((String) value);
      case DATE:
        return DateUtils.toClass((Date) value);
      case BYTE_ARRAY:
        return ByteArrayUtils.toClass((byte[]) value);
      case CLASS:
        return (Class<?>) value;
      case BIG_INTEGER:
        return BigIntegerUtils.toClass((BigInteger) value);
      case BIG_DECIMAL:
        return BigDecimalUtils.toClass((BigDecimal) value);
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts an object of the specified type to a {@link BigInteger} object.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param value
   *          the object to be converted, which could be null.
   * @return a {@link BigInteger} object corresponds to the object to be
   *         converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@link BigInteger}
   *           object.
   */
  public static BigInteger objectAsBigInteger(final Type type, @Nullable final Object value)
      throws ClassCastException, UnsupportedTypeException, TypeConvertException {
    switch (type) {
      case BOOLEAN:
        return BooleanUtils.toBigInteger((Boolean) value);
      case CHAR:
        return CharUtils.toBigInteger((Character) value);
      case BYTE:
        return ByteUtils.toBigInteger((Byte) value);
      case SHORT:
        return ShortUtils.toBigInteger((Short) value);
      case INT:
        return IntUtils.toBigInteger((Integer) value);
      case LONG:
        return LongUtils.toBigInteger((Long) value);
      case FLOAT:
        return FloatUtils.toBigInteger((Float) value);
      case DOUBLE:
        return DoubleUtils.toBigInteger((Double) value);
      case STRING:
        return StringUtils.toBigInteger((String) value);
      case DATE:
        return DateUtils.toBigInteger((Date) value);
      case BYTE_ARRAY:
        return ByteArrayUtils.toBigInteger((byte[]) value);
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER:
        return (BigInteger) value;
      case BIG_DECIMAL:
        return BigDecimalUtils.toBigInteger((BigDecimal) value);
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts an object of the specified type to a {@link BigDecimal} object.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param value
   *          the object to be converted, which could be null.
   * @return a {@link BigDecimal} object corresponds to the object to be
   *         converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@link BigDecimal}
   *           object.
   */
  public static BigDecimal objectAsBigDecimal(final Type type, @Nullable final Object value)
      throws ClassCastException, UnsupportedTypeException, TypeConvertException {
    switch (type) {
      case BOOLEAN:
        return BooleanUtils.toBigDecimal((Boolean) value);
      case CHAR:
        return CharUtils.toBigDecimal((Character) value);
      case BYTE:
        return ByteUtils.toBigDecimal((Byte) value);
      case SHORT:
        return ShortUtils.toBigDecimal((Short) value);
      case INT:
        return IntUtils.toBigDecimal((Integer) value);
      case LONG:
        return LongUtils.toBigDecimal((Long) value);
      case FLOAT:
        return FloatUtils.toBigDecimal((Float) value);
      case DOUBLE:
        return DoubleUtils.toBigDecimal((Double) value);
      case STRING:
        return StringUtils.toBigDecimal((String) value);
      case DATE:
        return DateUtils.toBigDecimal((Date) value);
      case BYTE_ARRAY:
        return ByteArrayUtils.toBigDecimal((byte[]) value);
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER:
        return BigIntegerUtils.toBigDecimal((BigInteger) value);
      case BIG_DECIMAL:
        return (BigDecimal) value;
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts the first element in a collection of values of the specified type
   * into a {@code boolean} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects of the specified type, which can't be
   *          null.
   * @return a {@code boolean} value converted from the first object in the
   *         collection.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code boolean}
   *           value.
   * @param NoSuchElementException
   *          if the collection is empty.
   */
  public static boolean firstInCollectionAsBoolean(final Type type,
      final Object col) throws ClassCastException, UnsupportedTypeException,
      TypeConvertException, NoSuchElementException {
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final boolean value = values.iterator().next();
        return value;
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final char value = values.iterator().next();
        return CharUtils.toBoolean(value);
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final byte value = values.iterator().next();
        return ByteUtils.toBoolean(value);
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final short value = values.iterator().next();
        return ShortUtils.toBoolean(value);
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int value = values.iterator().next();
        return IntUtils.toBoolean(value);
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final long value = values.iterator().next();
        return LongUtils.toBoolean(value);
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final float value = values.iterator().next();
        return FloatUtils.toBoolean(value);
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final double value = values.iterator().next();
        return DoubleUtils.toBoolean(value);
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final String value = values.iterator().next();
        return StringUtils.toBoolean(value);
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final Date value = values.iterator().next();
        return DateUtils.toBoolean(value);
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final byte[] value = values.iterator().next();
        return ByteArrayUtils.toBoolean(value);
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final BigInteger value = values.iterator().next();
        return BigIntegerUtils.toBoolean(value);
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final BigDecimal value = values.iterator().next();
        return BigDecimalUtils.toBoolean(value);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts the first element in a collection of values of the specified type
   * into a {@code char} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects of the specified type, which can't be
   *          null.
   * @return a {@code char} value converted from the first object in the
   *         collection.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code boolean}
   *           value.
   * @param NoSuchElementException
   *          if the collection is empty.
   */
  public static char firstInCollectionAsChar(final Type type,
      final Object col) throws ClassCastException, UnsupportedTypeException,
      TypeConvertException, NoSuchElementException {
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final boolean value = values.iterator().next();
        return BooleanUtils.toChar(value);
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final char value = values.iterator().next();
        return value;
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final byte value = values.iterator().next();
        return ByteUtils.toChar(value);
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final short value = values.iterator().next();
        return ShortUtils.toChar(value);
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int value = values.iterator().next();
        return IntUtils.toChar(value);
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final long value = values.iterator().next();
        return LongUtils.toChar(value);
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final float value = values.iterator().next();
        return FloatUtils.toChar(value);
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final double value = values.iterator().next();
        return DoubleUtils.toChar(value);
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final String value = values.iterator().next();
        return StringUtils.toChar(value);
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final Date value = values.iterator().next();
        return DateUtils.toChar(value);
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final byte[] value = values.iterator().next();
        return ByteArrayUtils.toChar(value);
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final BigInteger value = values.iterator().next();
        return BigIntegerUtils.toChar(value);
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final BigDecimal value = values.iterator().next();
        return BigDecimalUtils.toChar(value);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }


  /**
   * Converts the first element in a collection of values of the specified type
   * into a {@code byte} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects of the specified type, which can't be
   *          null.
   * @return a {@code byte} value converted from the first object in the
   *         collection.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code boolean}
   *           value.
   * @param NoSuchElementException
   *          if the collection is empty.
   */
  public static byte firstInCollectionAsByte(final Type type,
      final Object col) throws ClassCastException, UnsupportedTypeException,
      TypeConvertException, NoSuchElementException {
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final boolean value = values.iterator().next();
        return BooleanUtils.toByte(value);
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final char value = values.iterator().next();
        return CharUtils.toByte(value);
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final byte value = values.iterator().next();
        return value;
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final short value = values.iterator().next();
        return ShortUtils.toByte(value);
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int value = values.iterator().next();
        return IntUtils.toByte(value);
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final long value = values.iterator().next();
        return LongUtils.toByte(value);
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final float value = values.iterator().next();
        return FloatUtils.toByte(value);
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final double value = values.iterator().next();
        return DoubleUtils.toByte(value);
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final String value = values.iterator().next();
        return StringUtils.toByte(value);
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final Date value = values.iterator().next();
        return DateUtils.toByte(value);
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final byte[] value = values.iterator().next();
        return ByteArrayUtils.toByte(value);
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final BigInteger value = values.iterator().next();
        return BigIntegerUtils.toByte(value);
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final BigDecimal value = values.iterator().next();
        return BigDecimalUtils.toByte(value);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts the first element in a collection of values of the specified type
   * into a {@code short} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects of the specified type, which can't be
   *          null.
   * @return a {@code short} value converted from the first object in the
   *         collection.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code boolean}
   *           value.
   * @param NoSuchElementException
   *          if the collection is empty.
   */
  public static short firstInCollectionAsShort(final Type type,
      final Object col) throws ClassCastException, UnsupportedTypeException,
      TypeConvertException, NoSuchElementException {
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final boolean value = values.iterator().next();
        return BooleanUtils.toShort(value);
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final char value = values.iterator().next();
        return CharUtils.toShort(value);
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final byte value = values.iterator().next();
        return ByteUtils.toShort(value);
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final short value = values.iterator().next();
        return value;
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int value = values.iterator().next();
        return IntUtils.toShort(value);
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final long value = values.iterator().next();
        return LongUtils.toShort(value);
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final float value = values.iterator().next();
        return FloatUtils.toShort(value);
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final double value = values.iterator().next();
        return DoubleUtils.toShort(value);
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final String value = values.iterator().next();
        return StringUtils.toShort(value);
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final Date value = values.iterator().next();
        return DateUtils.toShort(value);
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final byte[] value = values.iterator().next();
        return ByteArrayUtils.toShort(value);
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final BigInteger value = values.iterator().next();
        return BigIntegerUtils.toShort(value);
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final BigDecimal value = values.iterator().next();
        return BigDecimalUtils.toShort(value);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts the first element in a collection of values of the specified type
   * into a {@code int} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects of the specified type, which can't be
   *          null.
   * @return a {@code int} value converted from the first object in the
   *         collection.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code boolean}
   *           value.
   * @param NoSuchElementException
   *          if the collection is empty.
   */
  public static int firstInCollectionAsInt(final Type type,
      final Object col) throws ClassCastException, UnsupportedTypeException,
      TypeConvertException, NoSuchElementException {
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final boolean value = values.iterator().next();
        return BooleanUtils.toInt(value);
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final char value = values.iterator().next();
        return CharUtils.toInt(value);
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final byte value = values.iterator().next();
        return ByteUtils.toInt(value);
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final short value = values.iterator().next();
        return ShortUtils.toInt(value);
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int value = values.iterator().next();
        return value;
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final long value = values.iterator().next();
        return LongUtils.toInt(value);
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final float value = values.iterator().next();
        return FloatUtils.toInt(value);
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final double value = values.iterator().next();
        return DoubleUtils.toInt(value);
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final String value = values.iterator().next();
        return StringUtils.toInt(value);
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final Date value = values.iterator().next();
        return DateUtils.toInt(value);
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final byte[] value = values.iterator().next();
        return ByteArrayUtils.toInt(value);
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final BigInteger value = values.iterator().next();
        return BigIntegerUtils.toInt(value);
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final BigDecimal value = values.iterator().next();
        return BigDecimalUtils.toInt(value);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }


  /**
   * Converts the first element in a collection of values of the specified type
   * into a {@code long} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects of the specified type, which can't be
   *          null.
   * @return a {@code long} value converted from the first object in the
   *         collection.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code boolean}
   *           value.
   * @param NoSuchElementException
   *          if the collection is empty.
   */
  public static long firstInCollectionAsLong(final Type type,
      final Object col) throws ClassCastException, UnsupportedTypeException,
      TypeConvertException, NoSuchElementException {
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final boolean value = values.iterator().next();
        return BooleanUtils.toLong(value);
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final char value = values.iterator().next();
        return CharUtils.toLong(value);
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final byte value = values.iterator().next();
        return ByteUtils.toLong(value);
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final short value = values.iterator().next();
        return ShortUtils.toLong(value);
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int value = values.iterator().next();
        return IntUtils.toLong(value);
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final long value = values.iterator().next();
        return value;
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final float value = values.iterator().next();
        return FloatUtils.toLong(value);
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final double value = values.iterator().next();
        return DoubleUtils.toLong(value);
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final String value = values.iterator().next();
        return StringUtils.toLong(value);
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final Date value = values.iterator().next();
        return DateUtils.toLong(value);
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final byte[] value = values.iterator().next();
        return ByteArrayUtils.toLong(value);
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final BigInteger value = values.iterator().next();
        return BigIntegerUtils.toLong(value);
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final BigDecimal value = values.iterator().next();
        return BigDecimalUtils.toLong(value);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts the first element in a collection of values of the specified type
   * into a {@code float} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects of the specified type, which can't be
   *          null.
   * @return a {@code float} value converted from the first object in the
   *         collection.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code boolean}
   *           value.
   * @param NoSuchElementException
   *          if the collection is empty.
   */
  public static float firstInCollectionAsFloat(final Type type,
      final Object col) throws ClassCastException, UnsupportedTypeException,
      TypeConvertException, NoSuchElementException {
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final boolean value = values.iterator().next();
        return BooleanUtils.toFloat(value);
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final char value = values.iterator().next();
        return CharUtils.toFloat(value);
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final byte value = values.iterator().next();
        return ByteUtils.toFloat(value);
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final short value = values.iterator().next();
        return ShortUtils.toFloat(value);
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int value = values.iterator().next();
        return IntUtils.toFloat(value);
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final long value = values.iterator().next();
        return LongUtils.toFloat(value);
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final float value = values.iterator().next();
        return value;
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final double value = values.iterator().next();
        return DoubleUtils.toFloat(value);
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final String value = values.iterator().next();
        return StringUtils.toFloat(value);
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final Date value = values.iterator().next();
        return DateUtils.toFloat(value);
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final byte[] value = values.iterator().next();
        return ByteArrayUtils.toFloat(value);
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final BigInteger value = values.iterator().next();
        return BigIntegerUtils.toFloat(value);
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final BigDecimal value = values.iterator().next();
        return BigDecimalUtils.toFloat(value);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }


  /**
   * Converts the first element in a collection of values of the specified type
   * into a {@code double} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects of the specified type, which can't be
   *          null.
   * @return a {@code double} value converted from the first object in the
   *         collection.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code boolean}
   *           value.
   * @param NoSuchElementException
   *          if the collection is empty.
   */
  public static double firstInCollectionAsDouble(final Type type,
      final Object col) throws ClassCastException, UnsupportedTypeException,
      TypeConvertException, NoSuchElementException {
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final boolean value = values.iterator().next();
        return BooleanUtils.toDouble(value);
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final char value = values.iterator().next();
        return CharUtils.toDouble(value);
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final byte value = values.iterator().next();
        return ByteUtils.toDouble(value);
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final short value = values.iterator().next();
        return ShortUtils.toDouble(value);
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int value = values.iterator().next();
        return IntUtils.toDouble(value);
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final long value = values.iterator().next();
        return LongUtils.toDouble(value);
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final float value = values.iterator().next();
        return FloatUtils.toDouble(value);
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final double value = values.iterator().next();
        return value;
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final String value = values.iterator().next();
        return StringUtils.toDouble(value);
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final Date value = values.iterator().next();
        return DateUtils.toDouble(value);
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final byte[] value = values.iterator().next();
        return ByteArrayUtils.toDouble(value);
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final BigInteger value = values.iterator().next();
        return BigIntegerUtils.toDouble(value);
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final BigDecimal value = values.iterator().next();
        return BigDecimalUtils.toDouble(value);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts the first element in a collection of values of the specified type
   * into a {@code String} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects of the specified type, which can't be
   *          null.
   * @return a {@code String} value converted from the first object in the
   *         collection.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code boolean}
   *           value.
   * @param NoSuchElementException
   *          if the collection is empty.
   */
  public static String firstInCollectionAsString(final Type type,
      final Object col) throws ClassCastException, UnsupportedTypeException,
      TypeConvertException, NoSuchElementException {
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final boolean value = values.iterator().next();
        return BooleanUtils.toString(value);
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final char value = values.iterator().next();
        return CharUtils.toString(value);
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final byte value = values.iterator().next();
        return ByteUtils.toString(value);
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final short value = values.iterator().next();
        return ShortUtils.toString(value);
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int value = values.iterator().next();
        return IntUtils.toString(value);
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final long value = values.iterator().next();
        return LongUtils.toString(value);
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final float value = values.iterator().next();
        return FloatUtils.toString(value);
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final double value = values.iterator().next();
        return DoubleUtils.toString(value);
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final String value = values.iterator().next();
        return value;
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final Date value = values.iterator().next();
        return DateUtils.toString(value, DateUtils.DEFAULT_LOCAL_DATETIME_PATTERN);
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final byte[] value = values.iterator().next();
        return ByteArrayUtils.toString(value);
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final BigInteger value = values.iterator().next();
        return BigIntegerUtils.toString(value);
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final BigDecimal value = values.iterator().next();
        return BigDecimalUtils.toString(value);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts the first element in a collection of values of the specified type
   * into a {@code Date} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects of the specified type, which can't be
   *          null.
   * @return a {@code Date} value converted from the first object in the
   *         collection.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code boolean}
   *           value.
   * @param NoSuchElementException
   *          if the collection is empty.
   */
  public static Date firstInCollectionAsDate(final Type type,
      final Object col) throws ClassCastException, UnsupportedTypeException,
      TypeConvertException, NoSuchElementException {
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final boolean value = values.iterator().next();
        return BooleanUtils.toDate(value);
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final char value = values.iterator().next();
        return CharUtils.toDate(value);
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final byte value = values.iterator().next();
        return ByteUtils.toDate(value);
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final short value = values.iterator().next();
        return ShortUtils.toDate(value);
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int value = values.iterator().next();
        return IntUtils.toDate(value);
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final long value = values.iterator().next();
        return LongUtils.toDate(value);
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final float value = values.iterator().next();
        return FloatUtils.toDate(value);
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final double value = values.iterator().next();
        return DoubleUtils.toDate(value);
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final String value = values.iterator().next();
        return StringUtils.toDate(value);
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final Date value = values.iterator().next();
        return Assignment.clone(value);
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final byte[] value = values.iterator().next();
        return ByteArrayUtils.toDate(value);
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final BigInteger value = values.iterator().next();
        return BigIntegerUtils.toDate(value);
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final BigDecimal value = values.iterator().next();
        return BigDecimalUtils.toDate(value);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts the first element in a collection of values of the specified type
   * into a {@code byte[]} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects of the specified type, which can't be
   *          null.
   * @return a {@code byte[]} value converted from the first object in the
   *         collection.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code boolean}
   *           value.
   * @param NoSuchElementException
   *          if the collection is empty.
   */
  public static byte[] firstInCollectionAsByteArray(final Type type,
      final Object col) throws ClassCastException, UnsupportedTypeException,
      TypeConvertException, NoSuchElementException {
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final boolean value = values.iterator().next();
        return BooleanUtils.toByteArray(value);
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final char value = values.iterator().next();
        return CharUtils.toByteArray(value);
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final byte value = values.iterator().next();
        return ByteUtils.toByteArray(value);
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final short value = values.iterator().next();
        return ShortUtils.toByteArray(value);
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int value = values.iterator().next();
        return IntUtils.toByteArray(value);
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final long value = values.iterator().next();
        return LongUtils.toByteArray(value);
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final float value = values.iterator().next();
        return FloatUtils.toByteArray(value);
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final double value = values.iterator().next();
        return DoubleUtils.toByteArray(value);
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final String value = values.iterator().next();
        return StringUtils.toByteArray(value);
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final Date value = values.iterator().next();
        return DateUtils.toByteArray(value);
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final byte[] value = values.iterator().next();
        return Assignment.clone(value);
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final BigInteger value = values.iterator().next();
        return BigIntegerUtils.toByteArray(value);
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final BigDecimal value = values.iterator().next();
        return BigDecimalUtils.toByteArray(value);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }


  /**
   * Converts the first element in a collection of values of the specified type
   * into a {@code Class} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects of the specified type, which can't be
   *          null.
   * @return a {@code Class} value converted from the first object in the
   *         collection.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code boolean}
   *           value.
   * @param NoSuchElementException
   *          if the collection is empty.
   */
  public static Class<?> firstInCollectionAsClass(final Type type,
      final Object col) throws ClassCastException, UnsupportedTypeException,
      TypeConvertException, NoSuchElementException {
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final boolean value = values.iterator().next();
        return BooleanUtils.toClass(value);
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final char value = values.iterator().next();
        return CharUtils.toClass(value);
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final byte value = values.iterator().next();
        return ByteUtils.toClass(value);
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final short value = values.iterator().next();
        return ShortUtils.toClass(value);
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int value = values.iterator().next();
        return IntUtils.toClass(value);
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final long value = values.iterator().next();
        return LongUtils.toClass(value);
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final float value = values.iterator().next();
        return FloatUtils.toClass(value);
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final double value = values.iterator().next();
        return DoubleUtils.toClass(value);
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final String value = values.iterator().next();
        return StringUtils.toClass(value);
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final Date value = values.iterator().next();
        return DateUtils.toClass(value);
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final byte[] value = values.iterator().next();
        return ByteArrayUtils.toClass(value);
      }
      case CLASS: {
        @SuppressWarnings("unchecked")
        final Collection<Class<?>> values = (Collection<Class<?>>) col;
        final Class<?> value = values.iterator().next();
        return value;
      }
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final BigInteger value = values.iterator().next();
        return BigIntegerUtils.toClass(value);
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final BigDecimal value = values.iterator().next();
        return BigDecimalUtils.toClass(value);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }


  /**
   * Converts the first element in a collection of values of the specified type
   * into a {@code BigInteger} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects of the specified type, which can't be
   *          null.
   * @return a {@code BigInteger} value converted from the first object in the
   *         collection.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code boolean}
   *           value.
   * @param NoSuchElementException
   *          if the collection is empty.
   */
  public static BigInteger firstInCollectionAsBigInteger(final Type type,
      final Object col) throws ClassCastException, UnsupportedTypeException,
      TypeConvertException, NoSuchElementException {
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final boolean value = values.iterator().next();
        return BooleanUtils.toBigInteger(value);
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final char value = values.iterator().next();
        return CharUtils.toBigInteger(value);
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final byte value = values.iterator().next();
        return ByteUtils.toBigInteger(value);
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final short value = values.iterator().next();
        return ShortUtils.toBigInteger(value);
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int value = values.iterator().next();
        return IntUtils.toBigInteger(value);
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final long value = values.iterator().next();
        return LongUtils.toBigInteger(value);
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final float value = values.iterator().next();
        return FloatUtils.toBigInteger(value);
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final double value = values.iterator().next();
        return DoubleUtils.toBigInteger(value);
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final String value = values.iterator().next();
        return StringUtils.toBigInteger(value);
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final Date value = values.iterator().next();
        return DateUtils.toBigInteger(value);
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final byte[] value = values.iterator().next();
        return ByteArrayUtils.toBigInteger(value);
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final BigInteger value = values.iterator().next();
        return value;
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final BigDecimal value = values.iterator().next();
        return BigDecimalUtils.toBigInteger(value);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }


  /**
   * Converts the first element in a collection of values of the specified type
   * into a {@code BigDecimal} value.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects of the specified type, which can't be
   *          null.
   * @return a {@code BigDecimal} value converted from the first object in the
   *         collection.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the object can not be converted into a {@code boolean}
   *           value.
   * @param NoSuchElementException
   *          if the collection is empty.
   */
  public static BigDecimal firstInCollectionAsBigDecimal(final Type type,
      final Object col) throws ClassCastException, UnsupportedTypeException,
      TypeConvertException, NoSuchElementException {
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final boolean value = values.iterator().next();
        return BooleanUtils.toBigDecimal(value);
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final char value = values.iterator().next();
        return CharUtils.toBigDecimal(value);
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final byte value = values.iterator().next();
        return ByteUtils.toBigDecimal(value);
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final short value = values.iterator().next();
        return ShortUtils.toBigDecimal(value);
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int value = values.iterator().next();
        return IntUtils.toBigDecimal(value);
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final long value = values.iterator().next();
        return LongUtils.toBigDecimal(value);
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final float value = values.iterator().next();
        return FloatUtils.toBigDecimal(value);
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final double value = values.iterator().next();
        return DoubleUtils.toBigDecimal(value);
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final String value = values.iterator().next();
        return StringUtils.toBigDecimal(value);
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final Date value = values.iterator().next();
        return DateUtils.toBigDecimal(value);
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final byte[] value = values.iterator().next();
        return ByteArrayUtils.toBigDecimal(value);
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final BigInteger value = values.iterator().next();
        return BigIntegerUtils.toBigDecimal(value);
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final BigDecimal value = values.iterator().next();
        return value;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a collection of objects of the specified type to a
   * {@code boolean} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects to be converted; if it is null or empty,
   *          a empty array will be returned. If the type is primitive type, the
   *          collection must be a primitive collection; otherwise, it must be a
   *          generic collection.
   * @return a {@code boolean} array corresponds to the collection of
   *         objects to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@code boolean} values.
   */
  public static boolean[] collectionAsBooleans(final Type type,
      @Nullable final Object col) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (col == null) {
      return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        if (values.size() == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        return values.toArray();
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[n];
        final CharIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final char value = iter.next();
          result[i++] = CharUtils.toBoolean(value);
        }
        return result;
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[n];
        final ByteIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final byte value = iter.next();
          result[i++] = ByteUtils.toBoolean(value);
        }
        return result;
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[n];
        final ShortIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final short value = iter.next();
          result[i++] = ShortUtils.toBoolean(value);
        }
        return result;
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[n];
        final IntIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final int value = iter.next();
          result[i++] = IntUtils.toBoolean(value);
        }
        return result;
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[n];
        final LongIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final long value = iter.next();
          result[i++] = LongUtils.toBoolean(value);
        }
        return result;
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[n];
        final FloatIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final float value = iter.next();
          result[i++] = FloatUtils.toBoolean(value);
        }
        return result;
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[n];
        final DoubleIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final double value = iter.next();
          result[i++] = DoubleUtils.toBoolean(value);
        }
        return result;
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[n];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toBoolean(value);
        }
        return result;
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[n];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toBoolean(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[n];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toBoolean(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[n];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toBoolean(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[n];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toBoolean(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a collection of objects of the specified type to a
   * {@code char} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects to be converted; if it is null or empty,
   *          a empty array will be returned. If the type is primitive type, the
   *          collection must be a primitive collection; otherwise, it must be a
   *          generic collection.
   * @return a {@code char} array corresponds to the collection of objects
   *         to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           Character.class, Class.class if the {@link Type} object is not
   *           supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@code char} values.
   */
  public static char[] collectionAsChars(final Type type,
      @Nullable final Object col) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (col == null) {
      return ArrayUtils.EMPTY_CHAR_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[n];
        final BooleanIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final boolean value = iter.next();
          result[i++] = BooleanUtils.toChar(value);
        }
        return result;
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        return values.toArray();
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[n];
        final ByteIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final byte value = iter.next();
          result[i++] = ByteUtils.toChar(value);
        }
        return result;
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[n];
        final ShortIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final short value = iter.next();
          result[i++] = ShortUtils.toChar(value);
        }
        return result;
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[n];
        final IntIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final int value = iter.next();
          result[i++] = IntUtils.toChar(value);
        }
        return result;
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[n];
        final LongIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final long value = iter.next();
          result[i++] = LongUtils.toChar(value);
        }
        return result;
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[n];
        final FloatIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final float value = iter.next();
          result[i++] = FloatUtils.toChar(value);
        }
        return result;
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[n];
        final DoubleIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final double value = iter.next();
          result[i++] = DoubleUtils.toChar(value);
        }
        return result;
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[n];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toChar(value);
        }
        return result;
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[n];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toChar(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[n];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toChar(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[n];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toChar(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[n];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toChar(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a collection of objects of the specified type to a
   * {@code byte} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects to be converted; if it is null or empty,
   *          a empty array will be returned. If the type is primitive type, the
   *          collection must be a primitive collection; otherwise, it must be a
   *          generic collection.
   * @return a {@code byte} array corresponds to the collection of objects
   *         to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@code byte} values.
   */
  public static byte[] collectionAsBytes(final Type type,
      @Nullable final Object col) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (col == null) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[n];
        final BooleanIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final boolean value = iter.next();
          result[i++] = BooleanUtils.toByte(value);
        }
        return result;
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[n];
        final CharIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final char value = iter.next();
          result[i++] = CharUtils.toByte(value);
        }
        return result;
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        return values.toArray();
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[n];
        final ShortIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final short value = iter.next();
          result[i++] = ShortUtils.toByte(value);
        }
        return result;
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[n];
        final IntIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final int value = iter.next();
          result[i++] = IntUtils.toByte(value);
        }
        return result;
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[n];
        final LongIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final long value = iter.next();
          result[i++] = LongUtils.toByte(value);
        }
        return result;
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[n];
        final FloatIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final float value = iter.next();
          result[i++] = FloatUtils.toByte(value);
        }
        return result;
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[n];
        final DoubleIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final double value = iter.next();
          result[i++] = DoubleUtils.toByte(value);
        }
        return result;
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[n];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toByte(value);
        }
        return result;
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[n];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toByte(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[n];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toByte(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[n];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toByte(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[n];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toByte(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a collection of objects of the specified type to a
   * {@code short} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects to be converted; if it is null or empty,
   *          a empty array will be returned. If the type is primitive type, the
   *          collection must be a primitive collection; otherwise, it must be a
   *          generic collection.
   * @return a {@code short} array corresponds to the collection of objects
   *         to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@code short} values.
   */
  public static short[] collectionAsShorts(final Type type,
      @Nullable final Object col) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (col == null) {
      return ArrayUtils.EMPTY_SHORT_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[n];
        final BooleanIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final boolean value = iter.next();
          result[i++] = BooleanUtils.toShort(value);
        }
        return result;
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[n];
        final CharIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final char value = iter.next();
          result[i++] = CharUtils.toShort(value);
        }
        return result;
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[n];
        final ByteIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final byte value = iter.next();
          result[i++] = ByteUtils.toShort(value);
        }
        return result;
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        return values.toArray();
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[n];
        final IntIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final int value = iter.next();
          result[i++] = IntUtils.toShort(value);
        }
        return result;
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[n];
        final LongIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final long value = iter.next();
          result[i++] = LongUtils.toShort(value);
        }
        return result;
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[n];
        final FloatIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final float value = iter.next();
          result[i++] = FloatUtils.toShort(value);
        }
        return result;
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[n];
        final DoubleIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final double value = iter.next();
          result[i++] = DoubleUtils.toShort(value);
        }
        return result;
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[n];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toShort(value);
        }
        return result;
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[n];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toShort(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[n];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toShort(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[n];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toShort(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[n];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toShort(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a collection of objects of the specified type to a
   * {@code int} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects to be converted; if it is null or empty,
   *          a empty array will be returned. If the type is primitive type, the
   *          collection must be a primitive collection; otherwise, it must be a
   *          generic collection.
   * @return a {@code int} array corresponds to the collection of objects
   *         to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@code int} values.
   */
  public static int[] collectionAsInts(final Type type, @Nullable final Object col)
      throws ClassCastException, UnsupportedTypeException, TypeConvertException {
    if (col == null) {
      return ArrayUtils.EMPTY_INT_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[n];
        final BooleanIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final boolean value = iter.next();
          result[i++] = BooleanUtils.toInt(value);
        }
        return result;
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[n];
        final CharIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final char value = iter.next();
          result[i++] = CharUtils.toInt(value);
        }
        return result;
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[n];
        final ByteIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final byte value = iter.next();
          result[i++] = ByteUtils.toInt(value);
        }
        return result;
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[n];
        final ShortIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final short value = iter.next();
          result[i++] = ShortUtils.toInt(value);
        }
        return result;
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        return values.toArray();
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[n];
        final LongIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final long value = iter.next();
          result[i++] = LongUtils.toInt(value);
        }
        return result;
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[n];
        final FloatIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final float value = iter.next();
          result[i++] = FloatUtils.toInt(value);
        }
        return result;
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[n];
        final DoubleIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final double value = iter.next();
          result[i++] = DoubleUtils.toInt(value);
        }
        return result;
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[n];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toInt(value);
        }
        return result;
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[n];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toInt(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[n];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toInt(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[n];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toInt(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[n];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toInt(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a collection of objects of the specified type to a
   * {@code long} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects to be converted; if it is null or empty,
   *          a empty array will be returned. If the type is primitive type, the
   *          collection must be a primitive collection; otherwise, it must be a
   *          generic collection.
   * @return a {@code long} array corresponds to the collection of objects
   *         to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@code long} values.
   */
  public static long[] collectionAsLongs(final Type type,
      @Nullable final Object col) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (col == null) {
      return ArrayUtils.EMPTY_LONG_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[n];
        final BooleanIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final boolean value = iter.next();
          result[i++] = BooleanUtils.toLong(value);
        }
        return result;
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[n];
        final CharIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final char value = iter.next();
          result[i++] = CharUtils.toLong(value);
        }
        return result;
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[n];
        final ByteIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final byte value = iter.next();
          result[i++] = ByteUtils.toLong(value);
        }
        return result;
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[n];
        final ShortIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final short value = iter.next();
          result[i++] = ShortUtils.toLong(value);
        }
        return result;
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[n];
        final IntIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final int value = iter.next();
          result[i++] = IntUtils.toLong(value);
        }
        return result;
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        return values.toArray();
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[n];
        final FloatIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final float value = iter.next();
          result[i++] = FloatUtils.toLong(value);
        }
        return result;
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[n];
        final DoubleIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final double value = iter.next();
          result[i++] = DoubleUtils.toLong(value);
        }
        return result;
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[n];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toLong(value);
        }
        return result;
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[n];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toLong(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[n];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toLong(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[n];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toLong(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[n];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toLong(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a collection of objects of the specified type to a
   * {@code float} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects to be converted; if it is null or empty,
   *          a empty array will be returned. If the type is primitive type, the
   *          collection must be a primitive collection; otherwise, it must be a
   *          generic collection.
   * @return a {@code float} array corresponds to the collection of objects
   *         to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@code float} values.
   */
  public static float[] collectionAsFloats(final Type type,
      @Nullable final Object col) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (col == null) {
      return ArrayUtils.EMPTY_FLOAT_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[n];
        final BooleanIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final boolean value = iter.next();
          result[i++] = BooleanUtils.toFloat(value);
        }
        return result;
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[n];
        final CharIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final char value = iter.next();
          result[i++] = CharUtils.toFloat(value);
        }
        return result;
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[n];
        final ByteIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final byte value = iter.next();
          result[i++] = ByteUtils.toFloat(value);
        }
        return result;
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[n];
        final ShortIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final short value = iter.next();
          result[i++] = ShortUtils.toFloat(value);
        }
        return result;
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[n];
        final IntIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final int value = iter.next();
          result[i++] = IntUtils.toFloat(value);
        }
        return result;
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[n];
        final LongIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final long value = iter.next();
          result[i++] = LongUtils.toFloat(value);
        }
        return result;
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        return values.toArray();
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[n];
        final DoubleIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final double value = iter.next();
          result[i++] = DoubleUtils.toFloat(value);
        }
        return result;
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[n];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toFloat(value);
        }
        return result;
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[n];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toFloat(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[n];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toFloat(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[n];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toFloat(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[n];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toFloat(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a collection of objects of the specified type to a
   * {@code double} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects to be converted; if it is null or empty,
   *          a empty array will be returned. If the type is primitive type, the
   *          collection must be a primitive collection; otherwise, it must be a
   *          generic collection.
   * @return a {@code double} array corresponds to the collection of
   *         objects to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@code double} values.
   */
  public static double[] collectionAsDoubles(final Type type,
      @Nullable final Object col) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (col == null) {
      return ArrayUtils.EMPTY_DOUBLE_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[n];
        final BooleanIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final boolean value = iter.next();
          result[i++] = BooleanUtils.toDouble(value);
        }
        return result;
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[n];
        final CharIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final char value = iter.next();
          result[i++] = CharUtils.toDouble(value);
        }
        return result;
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[n];
        final ByteIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final byte value = iter.next();
          result[i++] = ByteUtils.toDouble(value);
        }
        return result;
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[n];
        final ShortIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final short value = iter.next();
          result[i++] = ShortUtils.toDouble(value);
        }
        return result;
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[n];
        final IntIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final int value = iter.next();
          result[i++] = IntUtils.toDouble(value);
        }
        return result;
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[n];
        final LongIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final long value = iter.next();
          result[i++] = LongUtils.toDouble(value);
        }
        return result;
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[n];
        final FloatIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final float value = iter.next();
          result[i++] = FloatUtils.toDouble(value);
        }
        return result;
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        return values.toArray();
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[n];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toDouble(value);
        }
        return result;
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[n];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toDouble(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[n];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toDouble(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[n];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toDouble(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[n];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toDouble(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a collection of objects of the specified type to a {@link String}
   * array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects to be converted; if it is null or empty,
   *          a empty array will be returned. If the type is primitive type, the
   *          collection must be a primitive collection; otherwise, it must be a
   *          generic collection.
   * @return a {@link String} array corresponds to the collection of objects to
   *         be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   */
  public static String[] collectionAsStrings(final Type type,
      @Nullable final Object col) throws ClassCastException,
      UnsupportedTypeException {
    if (col == null) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[n];
        final BooleanIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final boolean value = iter.next();
          result[i++] = BooleanUtils.toString(value);
        }
        return result;
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[n];
        final CharIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final char value = iter.next();
          result[i++] = CharUtils.toString(value);
        }
        return result;
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[n];
        final ByteIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final byte value = iter.next();
          result[i++] = ByteUtils.toString(value);
        }
        return result;
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[n];
        final ShortIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final short value = iter.next();
          result[i++] = ShortUtils.toString(value);
        }
        return result;
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[n];
        final IntIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final int value = iter.next();
          result[i++] = IntUtils.toString(value);
        }
        return result;
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[n];
        final LongIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final long value = iter.next();
          result[i++] = LongUtils.toString(value);
        }
        return result;
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[n];
        final FloatIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final float value = iter.next();
          result[i++] = FloatUtils.toString(value);
        }
        return result;
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[n];
        final DoubleIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final double value = iter.next();
          result[i++] = DoubleUtils.toString(value);
        }
        return result;
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return values.toArray(new String[n]);
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[n];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toString(value, DateUtils.DEFAULT_LOCAL_DATETIME_PATTERN);
        }
        return result;
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[n];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toString(value);
        }
        return result;
      }
      case CLASS: {
        @SuppressWarnings("unchecked")
        final Collection<Class<?>> values = (Collection<Class<?>>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[n];
        int i = 0;
        for (final Class<?> value : values) {
          result[i++] = (value == null ? null : value.getName());
        }
        return result;
      }
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[n];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toString(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[n];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toString(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a collection of objects of the specified type to a {@link Date}
   * array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects to be converted; if it is null or empty,
   *          a empty array will be returned. If the type is primitive type, the
   *          collection must be a primitive collection; otherwise, it must be a
   *          generic collection.
   * @return a {@link Date} array corresponds to the collection of objects to be
   *         converted. Note that if the specified object is a collection of
   *         {@link Date} objects, each object in the returned {@link Date}
   *         array is a cloned copy of the original corresponding value.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@link Date} objects.
   */
  public static Date[] collectionAsDates(final Type type,
      @Nullable final Object col) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (col == null) {
      return ArrayUtils.EMPTY_DATE_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[n];
        final BooleanIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final boolean value = iter.next();
          result[i++] = BooleanUtils.toDate(value);
        }
        return result;
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[n];
        final CharIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final char value = iter.next();
          result[i++] = CharUtils.toDate(value);
        }
        return result;
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[n];
        final ByteIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final byte value = iter.next();
          result[i++] = ByteUtils.toDate(value);
        }
        return result;
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[n];
        final ShortIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final short value = iter.next();
          result[i++] = ShortUtils.toDate(value);
        }
        return result;
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[n];
        final IntIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final int value = iter.next();
          result[i++] = IntUtils.toDate(value);
        }
        return result;
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[n];
        final LongIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final long value = iter.next();
          result[i++] = LongUtils.toDate(value);
        }
        return result;
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[n];
        final FloatIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final float value = iter.next();
          result[i++] = FloatUtils.toDate(value);
        }
        return result;
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[n];
        final DoubleIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final double value = iter.next();
          result[i++] = DoubleUtils.toDate(value);
        }
        return result;
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[n];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toDate(value);
        }
        return result;
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[n];
        int i = 0;
        for (final Date value : values) {
          result[i++] = Assignment.clone(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[n];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toDate(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[n];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toDate(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[n];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toDate(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a collection of objects of the specified type to a
   * {@code byte[]} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects to be converted; if it is null or empty,
   *          a empty array will be returned. If the type is primitive type, the
   *          collection must be a primitive collection; otherwise, it must be a
   *          generic collection.
   * @return a {@code byte[]} array corresponds to the collection of
   *         objects to be converted. Note that if the specified object is of
   *         the <code>Collection<byte[]></code> type, each element in the
   *         returned {@code byte[][]} array is a cloned copy of the
   *         original corresponding value.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@code byte[]} values.
   */
  public static byte[][] collectionAsByteArrays(final Type type,
      @Nullable final Object col) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (col == null) {
      return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[n][];
        final BooleanIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final boolean value = iter.next();
          result[i++] = BooleanUtils.toByteArray(value);
        }
        return result;
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[n][];
        final CharIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final char value = iter.next();
          result[i++] = CharUtils.toByteArray(value);
        }
        return result;
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[n][];
        final ByteIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final byte value = iter.next();
          result[i++] = ByteUtils.toByteArray(value);
        }
        return result;
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[n][];
        final ShortIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final short value = iter.next();
          result[i++] = ShortUtils.toByteArray(value);
        }
        return result;
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[n][];
        final IntIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final int value = iter.next();
          result[i++] = IntUtils.toByteArray(value);
        }
        return result;
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[n][];
        final LongIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final long value = iter.next();
          result[i++] = LongUtils.toByteArray(value);
        }
        return result;
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[n][];
        final FloatIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final float value = iter.next();
          result[i++] = FloatUtils.toByteArray(value);
        }
        return result;
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[n][];
        final DoubleIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final double value = iter.next();
          result[i++] = DoubleUtils.toByteArray(value);
        }
        return result;
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[n][];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toByteArray(value);
        }
        return result;
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[n][];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toByteArray(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[n][];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = Assignment.clone(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[n][];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toByteArray(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[n][];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toByteArray(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a collection of objects of the specified type to a {@link Class}
   * array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects to be converted; if it is null or empty,
   *          a empty array will be returned. If the type is primitive type, the
   *          collection must be a primitive collection; otherwise, it must be a
   *          generic collection.
   * @return a {@link Date} array corresponds to the collection of objects to be
   *         converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@link Class} objects.
   */
  public static Class<?>[] collectionAsClasses(final Type type,
      @Nullable final Object col) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (col == null) {
      return ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[n];
        final BooleanIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final boolean value = iter.next();
          result[i++] = BooleanUtils.toClass(value);
        }
        return result;
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[n];
        final CharIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final char value = iter.next();
          result[i++] = CharUtils.toClass(value);
        }
        return result;
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[n];
        final ByteIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final byte value = iter.next();
          result[i++] = ByteUtils.toClass(value);
        }
        return result;
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[n];
        final ShortIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final short value = iter.next();
          result[i++] = ShortUtils.toClass(value);
        }
        return result;
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[n];
        final IntIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final int value = iter.next();
          result[i++] = IntUtils.toClass(value);
        }
        return result;
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[n];
        final LongIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final long value = iter.next();
          result[i++] = LongUtils.toClass(value);
        }
        return result;
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[n];
        final FloatIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final float value = iter.next();
          result[i++] = FloatUtils.toClass(value);
        }
        return result;
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[n];
        final DoubleIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final double value = iter.next();
          result[i++] = DoubleUtils.toClass(value);
        }
        return result;
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[n];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toClass(value);
        }
        return result;
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[n];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toClass(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[n];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toClass(value);
        }
        return result;
      }
      case CLASS: {
        @SuppressWarnings("unchecked")
        final Collection<Class<?>> values = (Collection<Class<?>>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        return values.toArray(new Class<?>[n]);
      }
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[n];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toClass(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[n];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toClass(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a collection of objects of the specified type to a
   * {@link BigInteger} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects to be converted; if it is null or empty,
   *          a empty array will be returned. If the type is primitive type, the
   *          collection must be a primitive collection; otherwise, it must be a
   *          generic collection.
   * @return a {@link BigInteger} array corresponds to the collection of objects
   *         to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@link BigInteger} objects.
   */
  public static BigInteger[] collectionAsBigIntegers(final Type type,
      @Nullable final Object col) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (col == null) {
      return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[n];
        final BooleanIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final boolean value = iter.next();
          result[i++] = BooleanUtils.toBigInteger(value);
        }
        return result;
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[n];
        final CharIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final char value = iter.next();
          result[i++] = CharUtils.toBigInteger(value);
        }
        return result;
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[n];
        final ByteIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final byte value = iter.next();
          result[i++] = ByteUtils.toBigInteger(value);
        }
        return result;
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[n];
        final ShortIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final short value = iter.next();
          result[i++] = ShortUtils.toBigInteger(value);
        }
        return result;
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[n];
        final IntIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final int value = iter.next();
          result[i++] = IntUtils.toBigInteger(value);
        }
        return result;
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[n];
        final LongIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final long value = iter.next();
          result[i++] = LongUtils.toBigInteger(value);
        }
        return result;
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[n];
        final FloatIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final float value = iter.next();
          result[i++] = FloatUtils.toBigInteger(value);
        }
        return result;
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[n];
        final DoubleIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final double value = iter.next();
          result[i++] = DoubleUtils.toBigInteger(value);
        }
        return result;
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[n];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toBigInteger(value);
        }
        return result;
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[n];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toBigInteger(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[n];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toBigInteger(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        return values.toArray(new BigInteger[n]);
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[n];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toBigInteger(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a collection of objects of the specified type to a
   * {@link BigDecimal} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection of objects to be converted; if it is null or empty,
   *          a empty array will be returned. If the type is primitive type, the
   *          collection must be a primitive collection; otherwise, it must be a
   *          generic collection.
   * @return a {@link BigDecimal} array corresponds to the collection of objects
   *         to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@link BigDecimal} objects.
   */
  public static BigDecimal[] collectionAsBigDecimals(final Type type,
      @Nullable final Object col) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (col == null) {
      return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[n];
        final BooleanIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final boolean value = iter.next();
          result[i++] = BooleanUtils.toBigDecimal(value);
        }
        return result;
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[n];
        final CharIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final char value = iter.next();
          result[i++] = CharUtils.toBigDecimal(value);
        }
        return result;
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[n];
        final ByteIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final byte value = iter.next();
          result[i++] = ByteUtils.toBigDecimal(value);
        }
        return result;
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[n];
        final ShortIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final short value = iter.next();
          result[i++] = ShortUtils.toBigDecimal(value);
        }
        return result;
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[n];
        final IntIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final int value = iter.next();
          result[i++] = IntUtils.toBigDecimal(value);
        }
        return result;
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[n];
        final LongIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final long value = iter.next();
          result[i++] = LongUtils.toBigDecimal(value);
        }
        return result;
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[n];
        final FloatIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final float value = iter.next();
          result[i++] = FloatUtils.toBigDecimal(value);
        }
        return result;
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[n];
        final DoubleIterator iter = values.iterator();
        int i = 0;
        while (iter.hasNext()) {
          final double value = iter.next();
          result[i++] = DoubleUtils.toBigDecimal(value);
        }
        return result;
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> values = (Collection<String>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[n];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toBigDecimal(value);
        }
        return result;
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> values = (Collection<Date>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[n];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toBigDecimal(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> values = (Collection<byte[]>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[n];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toBigDecimal(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[n];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toBigDecimal(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        final int n = values.size();
        if (n == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        return values.toArray(new BigDecimal[n]);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts an array of objects of the specified type to a
   * {@code boolean} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param array
   *          the array of objects to be converted; if it is null or empty, a
   *          empty array will be returned. If the type is primitive type, the
   *          array must be the array of that primitive type.
   * @return a {@code boolean} array corresponds to the collection of
   *         objects to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@code boolean} values.
   */
  public static boolean[] arrayAsBooleans(final Type type,
      @Nullable final Object array) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (array == null) {
      return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final boolean[] values = (boolean[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case CHAR: {
        final char[] values = (char[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[values.length];
        int i = 0;
        for (final char value : values) {
          result[i++] = CharUtils.toBoolean(value);
        }
        return result;
      }
      case BYTE: {
        final byte[] values = (byte[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[values.length];
        int i = 0;
        for (final byte value : values) {
          result[i++] = ByteUtils.toBoolean(value);
        }
        return result;
      }
      case SHORT: {
        final short[] values = (short[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[values.length];
        int i = 0;
        for (final short value : values) {
          result[i++] = ShortUtils.toBoolean(value);
        }
        return result;
      }
      case INT: {
        final int[] values = (int[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[values.length];
        int i = 0;
        for (final int value : values) {
          result[i++] = IntUtils.toBoolean(value);
        }
        return result;
      }
      case LONG: {
        final long[] values = (long[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[values.length];
        int i = 0;
        for (final long value : values) {
          result[i++] = LongUtils.toBoolean(value);
        }
        return result;
      }
      case FLOAT: {
        final float[] values = (float[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[values.length];
        int i = 0;
        for (final float value : values) {
          result[i++] = FloatUtils.toBoolean(value);
        }
        return result;
      }
      case DOUBLE: {
        final double[] values = (double[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[values.length];
        int i = 0;
        for (final double value : values) {
          result[i++] = DoubleUtils.toBoolean(value);
        }
        return result;
      }
      case STRING: {
        final String[] values = (String[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[values.length];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toBoolean(value);
        }
        return result;
      }
      case DATE: {
        final Date[] values = (Date[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[values.length];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toBoolean(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        final byte[][] values = (byte[][]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[values.length];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toBoolean(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        final BigInteger[] values = (BigInteger[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[values.length];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toBoolean(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        final BigDecimal[] values = (BigDecimal[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[values.length];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toBoolean(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts an array of objects of the specified type to a {@code char}
   * array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param array
   *          the array of objects to be converted; if it is null or empty, a
   *          empty array will be returned. If the type is primitive type, the
   *          array must be the array of that primitive type.
   * @return a {@code char} array corresponds to the collection of objects
   *         to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           Character.class, Class.class if the {@link Type} object is not
   *           supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@code char} values.
   */
  public static char[] arrayAsChars(final Type type, @Nullable final Object array)
      throws ClassCastException, UnsupportedTypeException, TypeConvertException {
    if (array == null) {
      return ArrayUtils.EMPTY_CHAR_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final boolean[] values = (boolean[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[values.length];
        int i = 0;
        for (final boolean value : values) {
          result[i++] = BooleanUtils.toChar(value);
        }
        return result;
      }
      case CHAR: {
        final char[] values = (char[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case BYTE: {
        final byte[] values = (byte[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[values.length];
        int i = 0;
        for (final byte value : values) {
          result[i++] = ByteUtils.toChar(value);
        }
        return result;
      }
      case SHORT: {
        final short[] values = (short[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[values.length];
        int i = 0;
        for (final short value : values) {
          result[i++] = ShortUtils.toChar(value);
        }
        return result;
      }
      case INT: {
        final int[] values = (int[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[values.length];
        int i = 0;
        for (final int value : values) {
          result[i++] = IntUtils.toChar(value);
        }
        return result;
      }
      case LONG: {
        final long[] values = (long[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[values.length];
        int i = 0;
        for (final long value : values) {
          result[i++] = LongUtils.toChar(value);
        }
        return result;
      }
      case FLOAT: {
        final float[] values = (float[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[values.length];
        int i = 0;
        for (final float value : values) {
          result[i++] = FloatUtils.toChar(value);
        }
        return result;
      }
      case DOUBLE: {
        final double[] values = (double[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[values.length];
        int i = 0;
        for (final double value : values) {
          result[i++] = DoubleUtils.toChar(value);
        }
        return result;
      }
      case STRING: {
        final String[] values = (String[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[values.length];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toChar(value);
        }
        return result;
      }
      case DATE: {
        final Date[] values = (Date[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[values.length];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toChar(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        final byte[][] values = (byte[][]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[values.length];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toChar(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        final BigInteger[] values = (BigInteger[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[values.length];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toChar(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        final BigDecimal[] values = (BigDecimal[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[values.length];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toChar(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a array of objects of the specified type to a
   * {@code byte} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param array
   *          the array of objects to be converted; if it is null or empty, a
   *          empty array will be returned. If the type is primitive type, the
   *          array must be the array of that primitive type.
   * @return a {@code byte} array corresponds to the collection of objects
   *         to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@code byte} values.
   */
  public static byte[] arrayAsBytes(final Type type,
      @Nullable final Object array) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (array == null) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final boolean[] values = (boolean[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[values.length];
        int i = 0;
        for (final boolean value : values) {
          result[i++] = BooleanUtils.toByte(value);
        }
        return result;
      }
      case CHAR: {
        final char[] values = (char[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[values.length];
        int i = 0;
        for (final char value : values) {
          result[i++] = CharUtils.toByte(value);
        }
        return result;
      }
      case BYTE: {
        final byte[] values = (byte[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case SHORT: {
        final short[] values = (short[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[values.length];
        int i = 0;
        for (final short value : values) {
          result[i++] = ShortUtils.toByte(value);
        }
        return result;
      }
      case INT: {
        final int[] values = (int[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[values.length];
        int i = 0;
        for (final int value : values) {
          result[i++] = IntUtils.toByte(value);
        }
        return result;
      }
      case LONG: {
        final long[] values = (long[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[values.length];
        int i = 0;
        for (final long value : values) {
          result[i++] = LongUtils.toByte(value);
        }
        return result;
      }
      case FLOAT: {
        final float[] values = (float[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[values.length];
        int i = 0;
        for (final float value : values) {
          result[i++] = FloatUtils.toByte(value);
        }
        return result;
      }
      case DOUBLE: {
        final double[] values = (double[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[values.length];
        int i = 0;
        for (final double value : values) {
          result[i++] = DoubleUtils.toByte(value);
        }
        return result;
      }
      case STRING: {
        final String[] values = (String[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[values.length];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toByte(value);
        }
        return result;
      }
      case DATE: {
        final Date[] values = (Date[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[values.length];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toByte(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        final byte[][] values = (byte[][]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[values.length];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toByte(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        final BigInteger[] values = (BigInteger[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[values.length];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toByte(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        final BigDecimal[] values = (BigDecimal[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[values.length];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toByte(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a array of objects of the specified type to a
   * {@code short} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param array
   *          the array of objects to be converted; if it is null or empty, a
   *          empty array will be returned. If the type is primitive type, the
   *          array must be the array of that primitive type.
   * @return a {@code short} array corresponds to the collection of objects
   *         to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@code short} values.
   */
  public static short[] arrayAsShorts(final Type type,
      @Nullable final Object array) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (array == null) {
      return ArrayUtils.EMPTY_SHORT_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final boolean[] values = (boolean[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[values.length];
        int i = 0;
        for (final boolean value : values) {
          result[i++] = BooleanUtils.toShort(value);
        }
        return result;
      }
      case CHAR: {
        final char[] values = (char[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[values.length];
        int i = 0;
        for (final char value : values) {
          result[i++] = CharUtils.toShort(value);
        }
        return result;
      }
      case BYTE: {
        final byte[] values = (byte[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[values.length];
        int i = 0;
        for (final byte value : values) {
          result[i++] = ByteUtils.toShort(value);
        }
        return result;
      }
      case SHORT: {
        final short[] values = (short[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case INT: {
        final int[] values = (int[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[values.length];
        int i = 0;
        for (final int value : values) {
          result[i++] = IntUtils.toShort(value);
        }
        return result;
      }
      case LONG: {
        final long[] values = (long[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[values.length];
        int i = 0;
        for (final long value : values) {
          result[i++] = LongUtils.toShort(value);
        }
        return result;
      }
      case FLOAT: {
        final float[] values = (float[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[values.length];
        int i = 0;
        for (final float value : values) {
          result[i++] = FloatUtils.toShort(value);
        }
        return result;
      }
      case DOUBLE: {
        final double[] values = (double[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[values.length];
        int i = 0;
        for (final double value : values) {
          result[i++] = DoubleUtils.toShort(value);
        }
        return result;
      }
      case STRING: {
        final String[] values = (String[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[values.length];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toShort(value);
        }
        return result;
      }
      case DATE: {
        final Date[] values = (Date[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[values.length];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toShort(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        final byte[][] values = (byte[][]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[values.length];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toShort(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        final BigInteger[] values = (BigInteger[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[values.length];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toShort(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        final BigDecimal[] values = (BigDecimal[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[values.length];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toShort(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a array of objects of the specified type to a
   * {@code int} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param array
   *          the array of objects to be converted; if it is null or empty, a
   *          empty array will be returned. If the type is primitive type, the
   *          array must be the array of that primitive type.
   * @return a {@code int} array corresponds to the collection of objects
   *         to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@code int} values.
   */
  public static int[] arrayAsInts(final Type type, @Nullable final Object array)
      throws ClassCastException, UnsupportedTypeException, TypeConvertException {
    if (array == null) {
      return ArrayUtils.EMPTY_INT_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final boolean[] values = (boolean[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[values.length];
        int i = 0;
        for (final boolean value : values) {
          result[i++] = BooleanUtils.toInt(value);
        }
        return result;
      }
      case CHAR: {
        final char[] values = (char[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[values.length];
        int i = 0;
        for (final char value : values) {
          result[i++] = CharUtils.toInt(value);
        }
        return result;
      }
      case BYTE: {
        final byte[] values = (byte[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[values.length];
        int i = 0;
        for (final byte value : values) {
          result[i++] = ByteUtils.toInt(value);
        }
        return result;
      }
      case SHORT: {
        final short[] values = (short[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[values.length];
        int i = 0;
        for (final short value : values) {
          result[i++] = ShortUtils.toInt(value);
        }
        return result;
      }
      case INT: {
        final int[] values = (int[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case LONG: {
        final long[] values = (long[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[values.length];
        int i = 0;
        for (final long value : values) {
          result[i++] = LongUtils.toInt(value);
        }
        return result;
      }
      case FLOAT: {
        final float[] values = (float[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[values.length];
        int i = 0;
        for (final float value : values) {
          result[i++] = FloatUtils.toInt(value);
        }
        return result;
      }
      case DOUBLE: {
        final double[] values = (double[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[values.length];
        int i = 0;
        for (final double value : values) {
          result[i++] = DoubleUtils.toInt(value);
        }
        return result;
      }
      case STRING: {
        final String[] values = (String[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[values.length];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toInt(value);
        }
        return result;
      }
      case DATE: {
        final Date[] values = (Date[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[values.length];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toInt(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        final byte[][] values = (byte[][]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[values.length];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toInt(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        final BigInteger[] values = (BigInteger[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[values.length];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toInt(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        final BigDecimal[] values = (BigDecimal[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[values.length];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toInt(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a array of objects of the specified type to a
   * {@code long} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param array
   *          the array of objects to be converted; if it is null or empty, a
   *          empty array will be returned. If the type is primitive type, the
   *          array must be the array of that primitive type.
   * @return a {@code long} array corresponds to the collection of objects
   *         to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@code long} values.
   */
  public static long[] arrayAsLongs(final Type type,
      @Nullable final Object array) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (array == null) {
      return ArrayUtils.EMPTY_LONG_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final boolean[] values = (boolean[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[values.length];
        int i = 0;
        for (final boolean value : values) {
          result[i++] = BooleanUtils.toLong(value);
        }
        return result;
      }
      case CHAR: {
        final char[] values = (char[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[values.length];
        int i = 0;
        for (final char value : values) {
          result[i++] = CharUtils.toLong(value);
        }
        return result;
      }
      case BYTE: {
        final byte[] values = (byte[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[values.length];
        int i = 0;
        for (final byte value : values) {
          result[i++] = ByteUtils.toLong(value);
        }
        return result;
      }
      case SHORT: {
        final short[] values = (short[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[values.length];
        int i = 0;
        for (final short value : values) {
          result[i++] = ShortUtils.toLong(value);
        }
        return result;
      }
      case INT: {
        final int[] values = (int[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[values.length];
        int i = 0;
        for (final int value : values) {
          result[i++] = IntUtils.toLong(value);
        }
        return result;
      }
      case LONG: {
        final long[] values = (long[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case FLOAT: {
        final float[] values = (float[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[values.length];
        int i = 0;
        for (final float value : values) {
          result[i++] = FloatUtils.toLong(value);
        }
        return result;
      }
      case DOUBLE: {
        final double[] values = (double[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[values.length];
        int i = 0;
        for (final double value : values) {
          result[i++] = DoubleUtils.toLong(value);
        }
        return result;
      }
      case STRING: {
        final String[] values = (String[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[values.length];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toLong(value);
        }
        return result;
      }
      case DATE: {
        final Date[] values = (Date[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[values.length];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toLong(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        final byte[][] values = (byte[][]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[values.length];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toLong(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        final BigInteger[] values = (BigInteger[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[values.length];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toLong(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        final BigDecimal[] values = (BigDecimal[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[values.length];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toLong(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a array of objects of the specified type to a
   * {@code float} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param array
   *          the array of objects to be converted; if it is null or empty, a
   *          empty array will be returned. If the type is primitive type, the
   *          array must be the array of that primitive type.
   * @return a {@code float} array corresponds to the collection of objects
   *         to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@code float} values.
   */
  public static float[] arrayAsFloats(final Type type,
      @Nullable final Object array) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (array == null) {
      return ArrayUtils.EMPTY_FLOAT_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final boolean[] values = (boolean[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[values.length];
        int i = 0;
        for (final boolean value : values) {
          result[i++] = BooleanUtils.toFloat(value);
        }
        return result;
      }
      case CHAR: {
        final char[] values = (char[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[values.length];
        int i = 0;
        for (final char value : values) {
          result[i++] = CharUtils.toFloat(value);
        }
        return result;
      }
      case BYTE: {
        final byte[] values = (byte[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[values.length];
        int i = 0;
        for (final byte value : values) {
          result[i++] = ByteUtils.toFloat(value);
        }
        return result;
      }
      case SHORT: {
        final short[] values = (short[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[values.length];
        int i = 0;
        for (final short value : values) {
          result[i++] = ShortUtils.toFloat(value);
        }
        return result;
      }
      case INT: {
        final int[] values = (int[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[values.length];
        int i = 0;
        for (final int value : values) {
          result[i++] = IntUtils.toFloat(value);
        }
        return result;
      }
      case LONG: {
        final long[] values = (long[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[values.length];
        int i = 0;
        for (final long value : values) {
          result[i++] = LongUtils.toFloat(value);
        }
        return result;
      }
      case FLOAT: {
        final float[] values = (float[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case DOUBLE: {
        final double[] values = (double[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[values.length];
        int i = 0;
        for (final double value : values) {
          result[i++] = DoubleUtils.toFloat(value);
        }
        return result;
      }
      case STRING: {
        final String[] values = (String[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[values.length];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toFloat(value);
        }
        return result;
      }
      case DATE: {
        final Date[] values = (Date[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[values.length];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toFloat(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        final byte[][] values = (byte[][]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[values.length];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toFloat(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        final BigInteger[] values = (BigInteger[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[values.length];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toFloat(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        final BigDecimal[] values = (BigDecimal[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[values.length];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toFloat(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a array of objects of the specified type to a
   * {@code double} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param array
   *          the array of objects to be converted; if it is null or empty, a
   *          empty array will be returned. If the type is primitive type, the
   *          array must be the array of that primitive type.
   * @return a {@code double} array corresponds to the collection of
   *         objects to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@code double} values.
   */
  public static double[] arrayAsDoubles(final Type type,
      @Nullable final Object array) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (array == null) {
      return ArrayUtils.EMPTY_DOUBLE_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final boolean[] values = (boolean[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[values.length];
        int i = 0;
        for (final boolean value : values) {
          result[i++] = BooleanUtils.toDouble(value);
        }
        return result;
      }
      case CHAR: {
        final char[] values = (char[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[values.length];
        int i = 0;
        for (final char value : values) {
          result[i++] = CharUtils.toDouble(value);
        }
        return result;
      }
      case BYTE: {
        final byte[] values = (byte[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[values.length];
        int i = 0;
        for (final byte value : values) {
          result[i++] = ByteUtils.toDouble(value);
        }
        return result;
      }
      case SHORT: {
        final short[] values = (short[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[values.length];
        int i = 0;
        for (final short value : values) {
          result[i++] = ShortUtils.toDouble(value);
        }
        return result;
      }
      case INT: {
        final int[] values = (int[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[values.length];
        int i = 0;
        for (final int value : values) {
          result[i++] = IntUtils.toDouble(value);
        }
        return result;
      }
      case LONG: {
        final long[] values = (long[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[values.length];
        int i = 0;
        for (final long value : values) {
          result[i++] = LongUtils.toDouble(value);
        }
        return result;
      }
      case FLOAT: {
        final float[] values = (float[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[values.length];
        int i = 0;
        for (final float value : values) {
          result[i++] = FloatUtils.toDouble(value);
        }
        return result;
      }
      case DOUBLE: {
        final double[] values = (double[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case STRING: {
        final String[] values = (String[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[values.length];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toDouble(value);
        }
        return result;
      }
      case DATE: {
        final Date[] values = (Date[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[values.length];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toDouble(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        final byte[][] values = (byte[][]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[values.length];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toDouble(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        final BigInteger[] values = (BigInteger[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[values.length];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toDouble(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        final BigDecimal[] values = (BigDecimal[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[values.length];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toDouble(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a array of objects of the specified type to a {@link String}
   * array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param array
   *          the array of objects to be converted; if it is null or empty, a
   *          empty array will be returned. If the type is primitive type, the
   *          array must be the array of that primitive type.
   * @return a {@link String} array corresponds to the collection of objects to
   *         be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   */
  public static String[] arrayAsStrings(final Type type,
      @Nullable final Object array) throws ClassCastException,
      UnsupportedTypeException {
    if (array == null) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final boolean[] values = (boolean[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[values.length];
        int i = 0;
        for (final boolean value : values) {
          result[i++] = BooleanUtils.toString(value);
        }
        return result;
      }
      case CHAR: {
        final char[] values = (char[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[values.length];
        int i = 0;
        for (final char value : values) {
          result[i++] = CharUtils.toString(value);
        }
        return result;
      }
      case BYTE: {
        final byte[] values = (byte[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[values.length];
        int i = 0;
        for (final byte value : values) {
          result[i++] = ByteUtils.toString(value);
        }
        return result;
      }
      case SHORT: {
        final short[] values = (short[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[values.length];
        int i = 0;
        for (final short value : values) {
          result[i++] = ShortUtils.toString(value);
        }
        return result;
      }
      case INT: {
        final int[] values = (int[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[values.length];
        int i = 0;
        for (final int value : values) {
          result[i++] = IntUtils.toString(value);
        }
        return result;
      }
      case LONG: {
        final long[] values = (long[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[values.length];
        int i = 0;
        for (final long value : values) {
          result[i++] = LongUtils.toString(value);
        }
        return result;
      }
      case FLOAT: {
        final float[] values = (float[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[values.length];
        int i = 0;
        for (final float value : values) {
          result[i++] = FloatUtils.toString(value);
        }
        return result;
      }
      case DOUBLE: {
        final double[] values = (double[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[values.length];
        int i = 0;
        for (final double value : values) {
          result[i++] = DoubleUtils.toString(value);
        }
        return result;
      }
      case STRING: {
        final String[] values = (String[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case DATE: {
        final Date[] values = (Date[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[values.length];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toString(value, DateUtils.DEFAULT_LOCAL_DATETIME_PATTERN);
        }
        return result;
      }
      case BYTE_ARRAY: {
        final byte[][] values = (byte[][]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[values.length];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toString(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        final BigInteger[] values = (BigInteger[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[values.length];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toString(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        final BigDecimal[] values = (BigDecimal[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final String[] result = new String[values.length];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toString(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a array of objects of the specified type to a {@link Date}
   * array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param array
   *          the array of objects to be converted; if it is null or empty, a
   *          empty array will be returned. If the type is primitive type, the
   *          array must be the array of that primitive type.
   * @return a {@link Date} array corresponds to the collection of objects to be
   *         converted. Note that if the specified object is a collection of
   *         {@link Date} objects, each object in the returned {@link Date}
   *         array is a cloned copy of the original corresponding value.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@link Date} objects.
   */
  public static Date[] arrayAsDates(final Type type,
      @Nullable final Object array) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (array == null) {
      return ArrayUtils.EMPTY_DATE_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final boolean[] values = (boolean[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[values.length];
        int i = 0;
        for (final boolean value : values) {
          result[i++] = BooleanUtils.toDate(value);
        }
        return result;
      }
      case CHAR: {
        final char[] values = (char[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[values.length];
        int i = 0;
        for (final char value : values) {
          result[i++] = CharUtils.toDate(value);
        }
        return result;
      }
      case BYTE: {
        final byte[] values = (byte[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[values.length];
        int i = 0;
        for (final byte value : values) {
          result[i++] = ByteUtils.toDate(value);
        }
        return result;
      }
      case SHORT: {
        final short[] values = (short[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[values.length];
        int i = 0;
        for (final short value : values) {
          result[i++] = ShortUtils.toDate(value);
        }
        return result;
      }
      case INT: {
        final int[] values = (int[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[values.length];
        int i = 0;
        for (final int value : values) {
          result[i++] = IntUtils.toDate(value);
        }
        return result;
      }
      case LONG: {
        final long[] values = (long[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[values.length];
        int i = 0;
        for (final long value : values) {
          result[i++] = LongUtils.toDate(value);
        }
        return result;
      }
      case FLOAT: {
        final float[] values = (float[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[values.length];
        int i = 0;
        for (final float value : values) {
          result[i++] = FloatUtils.toDate(value);
        }
        return result;
      }
      case DOUBLE: {
        final double[] values = (double[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[values.length];
        int i = 0;
        for (final double value : values) {
          result[i++] = DoubleUtils.toDate(value);
        }
        return result;
      }
      case STRING: {
        final String[] values = (String[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[values.length];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toDate(value);
        }
        return result;
      }
      case DATE: {
        final Date[] values = (Date[]) array;
        return Assignment.deepClone(values);
      }
      case BYTE_ARRAY: {
        final byte[][] values = (byte[][]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[values.length];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toDate(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        final BigInteger[] values = (BigInteger[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[values.length];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toDate(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        final BigDecimal[] values = (BigDecimal[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_DATE_ARRAY;
        }
        final Date[] result = new Date[values.length];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toDate(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a array of objects of the specified type to a
   * {@code byte[]} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param array
   *          the array of objects to be converted; if it is null or empty, a
   *          empty array will be returned. If the type is primitive type, the
   *          array must be the array of that primitive type.
   * @return a {@code byte[]} array corresponds to the collection of
   *         objects to be converted. Note that if the specified object is of
   *         the <code>Collection<byte[]></code> type, each element in the
   *         returned {@code byte[][]} array is a cloned copy of the
   *         original corresponding value.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@code byte[]} values.
   */
  public static byte[][] arrayAsByteArrays(final Type type,
      @Nullable final Object array) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (array == null) {
      return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final boolean[] values = (boolean[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[values.length][];
        int i = 0;
        for (final boolean value : values) {
          result[i++] = BooleanUtils.toByteArray(value);
        }
        return result;
      }
      case CHAR: {
        final char[] values = (char[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[values.length][];
        int i = 0;
        for (final char value : values) {
          result[i++] = CharUtils.toByteArray(value);
        }
        return result;
      }
      case BYTE: {
        final byte[] values = (byte[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[values.length][];
        int i = 0;
        for (final byte value : values) {
          result[i++] = ByteUtils.toByteArray(value);
        }
        return result;
      }
      case SHORT: {
        final short[] values = (short[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[values.length][];
        int i = 0;
        for (final short value : values) {
          result[i++] = ShortUtils.toByteArray(value);
        }
        return result;
      }
      case INT: {
        final int[] values = (int[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[values.length][];
        int i = 0;
        for (final int value : values) {
          result[i++] = IntUtils.toByteArray(value);
        }
        return result;
      }
      case LONG: {
        final long[] values = (long[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[values.length][];
        int i = 0;
        for (final long value : values) {
          result[i++] = LongUtils.toByteArray(value);
        }
        return result;
      }
      case FLOAT: {
        final float[] values = (float[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[values.length][];
        int i = 0;
        for (final float value : values) {
          result[i++] = FloatUtils.toByteArray(value);
        }
        return result;
      }
      case DOUBLE: {
        final double[] values = (double[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[values.length][];
        int i = 0;
        for (final double value : values) {
          result[i++] = DoubleUtils.toByteArray(value);
        }
        return result;
      }
      case STRING: {
        final String[] values = (String[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[values.length][];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toByteArray(value);
        }
        return result;
      }
      case DATE: {
        final Date[] values = (Date[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[values.length][];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toByteArray(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        final byte[][] values = (byte[][]) array;
        return Assignment.deepClone(values);
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        final BigInteger[] values = (BigInteger[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[values.length][];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toByteArray(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        final BigDecimal[] values = (BigDecimal[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
        }
        final byte[][] result = new byte[values.length][];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toByteArray(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts an array of objects of the specified type to a {@link Class}
   * array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param array
   *          the array of objects to be converted; if it is null or empty, a
   *          empty array will be returned. If the type is primitive type, the
   *          array must be the array of that primitive type.
   * @return a {@link Date} array corresponds to the collection of objects to be
   *         converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@link Class} objects.
   */
  public static Class<?>[] arrayAsClasses(final Type type,
      @Nullable final Object array) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (array == null) {
      return ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final boolean[] values = (boolean[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[values.length];
        int i = 0;
        for (final boolean value : values) {
          result[i++] = BooleanUtils.toClass(value);
        }
        return result;
      }
      case CHAR: {
        final char[] values = (char[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[values.length];
        int i = 0;
        for (final char value : values) {
          result[i++] = CharUtils.toClass(value);
        }
        return result;
      }
      case BYTE: {
        final byte[] values = (byte[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[values.length];
        int i = 0;
        for (final byte value : values) {
          result[i++] = ByteUtils.toClass(value);
        }
        return result;
      }
      case SHORT: {
        final short[] values = (short[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[values.length];
        int i = 0;
        for (final short value : values) {
          result[i++] = ShortUtils.toClass(value);
        }
        return result;
      }
      case INT: {
        final int[] values = (int[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[values.length];
        int i = 0;
        for (final int value : values) {
          result[i++] = IntUtils.toClass(value);
        }
        return result;
      }
      case LONG: {
        final long[] values = (long[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[values.length];
        int i = 0;
        for (final long value : values) {
          result[i++] = LongUtils.toClass(value);
        }
        return result;
      }
      case FLOAT: {
        final float[] values = (float[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[values.length];
        int i = 0;
        for (final float value : values) {
          result[i++] = FloatUtils.toClass(value);
        }
        return result;
      }
      case DOUBLE: {
        final double[] values = (double[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[values.length];
        int i = 0;
        for (final double value : values) {
          result[i++] = DoubleUtils.toClass(value);
        }
        return result;
      }
      case STRING: {
        final String[] values = (String[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[values.length];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toClass(value);
        }
        return result;
      }
      case DATE: {
        final Date[] values = (Date[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[values.length];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toClass(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        final byte[][] values = (byte[][]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[values.length];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toClass(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        final BigInteger[] values = (BigInteger[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[values.length];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toClass(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        final BigDecimal[] values = (BigDecimal[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] result = new Class<?>[values.length];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toClass(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a array of objects of the specified type to a
   * {@link BigInteger} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param array
   *          the array of objects to be converted; if it is null or empty, a
   *          empty array will be returned. If the type is primitive type, the
   *          array must be the array of that primitive type.
   * @return a {@link BigInteger} array corresponds to the collection of objects
   *         to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@link BigInteger} objects.
   */
  public static BigInteger[] arrayAsBigIntegers(final Type type,
      @Nullable final Object array) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (array == null) {
      return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final boolean[] values = (boolean[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[values.length];
        int i = 0;
        for (final boolean value : values) {
          result[i++] = BooleanUtils.toBigInteger(value);
        }
        return result;
      }
      case CHAR: {
        final char[] values = (char[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[values.length];
        int i = 0;
        for (final char value : values) {
          result[i++] = CharUtils.toBigInteger(value);
        }
        return result;
      }
      case BYTE: {
        final byte[] values = (byte[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[values.length];
        int i = 0;
        for (final byte value : values) {
          result[i++] = ByteUtils.toBigInteger(value);
        }
        return result;
      }
      case SHORT: {
        final short[] values = (short[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[values.length];
        int i = 0;
        for (final short value : values) {
          result[i++] = ShortUtils.toBigInteger(value);
        }
        return result;
      }
      case INT: {
        final int[] values = (int[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[values.length];
        int i = 0;
        for (final int value : values) {
          result[i++] = IntUtils.toBigInteger(value);
        }
        return result;
      }
      case LONG: {
        final long[] values = (long[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[values.length];
        int i = 0;
        for (final long value : values) {
          result[i++] = LongUtils.toBigInteger(value);
        }
        return result;
      }
      case FLOAT: {
        final float[] values = (float[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[values.length];
        int i = 0;
        for (final float value : values) {
          result[i++] = FloatUtils.toBigInteger(value);
        }
        return result;
      }
      case DOUBLE: {
        final double[] values = (double[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[values.length];
        int i = 0;
        for (final double value : values) {
          result[i++] = DoubleUtils.toBigInteger(value);
        }
        return result;
      }
      case STRING: {
        final String[] values = (String[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[values.length];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toBigInteger(value);
        }
        return result;
      }
      case DATE: {
        final Date[] values = (Date[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[values.length];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toBigInteger(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        final byte[][] values = (byte[][]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[values.length];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toBigInteger(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        final BigInteger[] values = (BigInteger[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      case BIG_DECIMAL: {
        final BigDecimal[] values = (BigDecimal[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
        }
        final BigInteger[] result = new BigInteger[values.length];
        int i = 0;
        for (final BigDecimal value : values) {
          result[i++] = BigDecimalUtils.toBigInteger(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Converts a array of objects of the specified type to a
   * {@link BigDecimal} array.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param array
   *          the array of objects to be converted; if it is null or empty, a
   *          empty array will be returned. If the type is primitive type, the
   *          array must be the array of that primitive type.
   * @return a {@link BigDecimal} array corresponds to the collection of objects
   *         to be converted.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   * @throws TypeConvertException
   *           if the objects in the collection can not be converted into
   *           {@link BigDecimal} objects.
   */
  public static BigDecimal[] arrayAsBigDecimals(final Type type,
      @Nullable final Object array) throws ClassCastException,
      UnsupportedTypeException, TypeConvertException {
    if (array == null) {
      return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
    }
    switch (type) {
      case BOOLEAN: {
        final boolean[] values = (boolean[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[values.length];
        int i = 0;
        for (final boolean value : values) {
          result[i++] = BooleanUtils.toBigDecimal(value);
        }
        return result;
      }
      case CHAR: {
        final char[] values = (char[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[values.length];
        int i = 0;
        for (final char value : values) {
          result[i++] = CharUtils.toBigDecimal(value);
        }
        return result;
      }
      case BYTE: {
        final byte[] values = (byte[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[values.length];
        int i = 0;
        for (final byte value : values) {
          result[i++] = ByteUtils.toBigDecimal(value);
        }
        return result;
      }
      case SHORT: {
        final short[] values = (short[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[values.length];
        int i = 0;
        for (final short value : values) {
          result[i++] = ShortUtils.toBigDecimal(value);
        }
        return result;
      }
      case INT: {
        final int[] values = (int[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[values.length];
        int i = 0;
        for (final int value : values) {
          result[i++] = IntUtils.toBigDecimal(value);
        }
        return result;
      }
      case LONG: {
        final long[] values = (long[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[values.length];
        int i = 0;
        for (final long value : values) {
          result[i++] = LongUtils.toBigDecimal(value);
        }
        return result;
      }
      case FLOAT: {
        final float[] values = (float[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[values.length];
        int i = 0;
        for (final float value : values) {
          result[i++] = FloatUtils.toBigDecimal(value);
        }
        return result;
      }
      case DOUBLE: {
        final double[] values = (double[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[values.length];
        int i = 0;
        for (final double value : values) {
          result[i++] = DoubleUtils.toBigDecimal(value);
        }
        return result;
      }
      case STRING: {
        final String[] values = (String[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[values.length];
        int i = 0;
        for (final String value : values) {
          result[i++] = StringUtils.toBigDecimal(value);
        }
        return result;
      }
      case DATE: {
        final Date[] values = (Date[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[values.length];
        int i = 0;
        for (final Date value : values) {
          result[i++] = DateUtils.toBigDecimal(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        final byte[][] values = (byte[][]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[values.length];
        int i = 0;
        for (final byte[] value : values) {
          result[i++] = ByteArrayUtils.toBigDecimal(value);
        }
        return result;
      }
      case CLASS:
        throw new TypeConvertException(Type.CLASS, Type.BOOLEAN);
      case BIG_INTEGER: {
        final BigInteger[] values = (BigInteger[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[values.length];
        int i = 0;
        for (final BigInteger value : values) {
          result[i++] = BigIntegerUtils.toBigDecimal(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        final BigDecimal[] values = (BigDecimal[]) array;
        if (values.length == 0) {
          return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
        }
        final BigDecimal[] result = new BigDecimal[values.length];
        System.arraycopy(values, 0, result, 0, values.length);
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Add a specified object to a specified collection.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection where the object to be added to. If the type is a
   *          primitive type, it must be the corresponding primitive collection;
   *          otherwise, it must be the corresponding generic collection.
   * @param value
   *          the object to be added to the collection. It could be null.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws NullPointerException
   *           if the collection is null.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   */
  public static void addObject(final Type type,
      final Object col, @Nullable final Object value)
      throws NullPointerException, UnsupportedTypeException {
    requireNonNull("col", col);
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection target = (BooleanCollection) col;
        target.add(BooleanUtils.toPrimitive((Boolean) value));
        return;
      }
      case CHAR: {
        final CharCollection target = (CharCollection) col;
        target.add(CharUtils.toPrimitive((Character) value));
        return;
      }
      case BYTE: {
        final ByteCollection target = (ByteCollection) col;
        target.add(ByteUtils.toPrimitive((Byte) value));
        return;
      }
      case SHORT: {
        final ShortCollection target = (ShortCollection) col;
        target.add(ShortUtils.toPrimitive((Short) value));
        return;
      }
      case INT: {
        final IntCollection target = (IntCollection) col;
        target.add(IntUtils.toPrimitive((Integer) value));
        return;
      }
      case LONG: {
        final LongCollection target = (LongCollection) col;
        target.add(LongUtils.toPrimitive((Long) value));
      }
      case FLOAT: {
        final FloatCollection target = (FloatCollection) col;
        target.add(FloatUtils.toPrimitive((Float) value));
      }
      case DOUBLE: {
        final DoubleCollection target = (DoubleCollection) col;
        target.add(DoubleUtils.toPrimitive((Double) value));
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> target = (Collection<String>) col;
        target.add((String) value);
        return;
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> target = (Collection<Date>) col;
        target.add(Assignment.clone((Date) value));
        return;
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> target = (Collection<byte[]>) col;
        target.add(Assignment.clone((byte[]) value));
        return;
      }
      case CLASS: {
        @SuppressWarnings("unchecked")
        final Collection<Class<?>> target = (Collection<Class<?>>) col;
        target.add((Class<?>) value);
        return;
      }
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> target = (Collection<BigInteger>) col;
        target.add((BigInteger) value);
        return;
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> target = (Collection<BigDecimal>) col;
        target.add((BigDecimal) value);
        return;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Add an array of values to a specified collection.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection where the object to be added to. If the type is a
   *          primitive type, it must be the corresponding primitive collection;
   *          otherwise, it must be the corresponding generic collection.
   * @param values
   *          the array of values to be added to the collection. It could be null or
   *          empty. If the type is a primitive type, it must be the corresponding
   *          primitive array; otherwise, it must be the corresponding object array.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws NullPointerException
   *           if the collection is null.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   */
  public static void addArray(final Type type,
      final Object col, @Nullable final Object values) {
    requireNonNull("col", col);
    if (values == null) {
      return;
    }
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection target = (BooleanCollection) col;
        final boolean[] array = (boolean[]) values;
        for (final boolean value : array) {
          target.add(value);
        }
        return;
      }
      case CHAR: {
        final CharCollection target = (CharCollection) col;
        final char[] array = (char[]) values;
        for (final char value : array) {
          target.add(value);
        }
        return;
      }
      case BYTE: {
        final ByteCollection target = (ByteCollection) col;
        final byte[] array = (byte[]) values;
        for (final byte value : array) {
          target.add(value);
        }
        return;
      }
      case SHORT: {
        final ShortCollection target = (ShortCollection) col;
        final short[] array = (short[]) values;
        for (final short value : array) {
          target.add(value);
        }
        return;
      }
      case INT: {
        final IntCollection target = (IntCollection) col;
        final int[] array = (int[]) values;
        for (final int value : array) {
          target.add(value);
        }
        return;
      }
      case LONG: {
        final LongCollection target = (LongCollection) col;
        final long[] array = (long[]) values;
        for (final long value : array) {
          target.add(value);
        }
        return;
      }
      case FLOAT: {
        final FloatCollection target = (FloatCollection) col;
        final float[] array = (float[]) values;
        for (final float value : array) {
          target.add(value);
        }
        return;
      }
      case DOUBLE: {
        final DoubleCollection target = (DoubleCollection) col;
        final double[] array = (double[]) values;
        for (final double value : array) {
          target.add(value);
        }
        return;
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> target = (Collection<String>) col;
        final String[] array = (String[]) values;
        for (final String value : array) {
          target.add(value);
        }
        return;
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> target = (Collection<Date>) col;
        final Date[] array = (Date[]) values;
        for (final Date value : array) {
          target.add(Assignment.clone(value));
        }
        return;
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> target = (Collection<byte[]>) col;
        final byte[][] array = (byte[][]) values;
        for (final byte[] value : array) {
          target.add(Assignment.clone(value));
        }
        return;
      }
      case CLASS: {
        @SuppressWarnings("unchecked")
        final Collection<Class<?>> target = (Collection<Class<?>>) col;
        final Class<?>[] array = (Class<?>[]) values;
        for (final Class<?> value : array) {
          target.add(value);
        }
        return;
      }
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> target = (Collection<BigInteger>) col;
        final BigInteger[] array = (BigInteger[]) values;
        for (final BigInteger value : array) {
          target.add(value);
        }
        return;
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> target = (Collection<BigDecimal>) col;
        final BigDecimal[] array = (BigDecimal[]) values;
        for (final BigDecimal value : array) {
          target.add(value);
        }
        return;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Add a collection of values to a specified collection.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be converted.
   * @param col
   *          the collection where the object to be added to. If the type is a
   *          primitive type, it must be the corresponding primitive collection;
   *          otherwise, it must be the corresponding generic collection.
   * @param values
   *          the collection of objects to be added to the collection. If the
   *          type is a primitive type, it must be the corresponding primitive
   *          collection; otherwise, it must be the corresponding generic
   *          collection.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects in the collection to be converted.
   * @throws NullPointerException
   *           if the collection is null.
   * @throws IllegalArgumentException
   *           if the specified collection and the collection of values to be
   *           add is the same collection.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   */
  public static void addCollection(final Type type,
      final Object col, @Nullable final Object values) {
    requireNonNull("col", col);
    if (values == null) {
      return;
    }
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection target = (BooleanCollection) col;
        final BooleanCollection source = (BooleanCollection) values;
        final BooleanIterator iter = source.iterator();
        while (iter.hasNext()) {
          final boolean value = iter.next();
          target.add(value);
        }
        return;
      }
      case CHAR: {
        final CharCollection target = (CharCollection) col;
        final CharCollection source = (CharCollection) values;
        final CharIterator iter = source.iterator();
        while (iter.hasNext()) {
          final char value = iter.next();
          target.add(value);
        }
        return;
      }
      case BYTE: {
        final ByteCollection target = (ByteCollection) col;
        final ByteCollection source = (ByteCollection) values;
        final ByteIterator iter = source.iterator();
        while (iter.hasNext()) {
          final byte value = iter.next();
          target.add(value);
        }
        return;
      }
      case SHORT: {
        final ShortCollection target = (ShortCollection) col;
        final ShortCollection source = (ShortCollection) values;
        final ShortIterator iter = source.iterator();
        while (iter.hasNext()) {
          final short value = iter.next();
          target.add(value);
        }
        return;
      }
      case INT: {
        final IntCollection target = (IntCollection) col;
        final IntCollection source = (IntCollection) values;
        final IntIterator iter = source.iterator();
        while (iter.hasNext()) {
          final int value = iter.next();
          target.add(value);
        }
        return;
      }
      case LONG: {
        final LongCollection target = (LongCollection) col;
        final LongCollection source = (LongCollection) values;
        final LongIterator iter = source.iterator();
        while (iter.hasNext()) {
          final long value = iter.next();
          target.add(value);
        }
        return;
      }
      case FLOAT: {
        final FloatCollection target = (FloatCollection) col;
        final FloatCollection source = (FloatCollection) values;
        final FloatIterator iter = source.iterator();
        while (iter.hasNext()) {
          final float value = iter.next();
          target.add(value);
        }
        return;
      }
      case DOUBLE: {
        final DoubleCollection target = (DoubleCollection) col;
        final DoubleCollection source = (DoubleCollection) values;
        final DoubleIterator iter = source.iterator();
        while (iter.hasNext()) {
          final double value = iter.next();
          target.add(value);
        }
        return;
      }
      case STRING: {
        @SuppressWarnings("unchecked")
        final Collection<String> target = (Collection<String>) col;
        @SuppressWarnings("unchecked")
        final Collection<String> source = (Collection<String>) values;
        for (final String value : source) {
          target.add(value);
        }
        return;
      }
      case DATE: {
        @SuppressWarnings("unchecked")
        final Collection<Date> target = (Collection<Date>) col;
        @SuppressWarnings("unchecked")
        final Collection<Date> source = (Collection<Date>) values;
        for (final Date value : source) {
          target.add(Assignment.clone(value));
        }
        return;
      }
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> target = (Collection<byte[]>) col;
        @SuppressWarnings("unchecked")
        final Collection<byte[]> source = (Collection<byte[]>) values;
        for (final byte[] value : source) {
          target.add(Assignment.clone(value));
        }
        return;
      }
      case CLASS: {
        @SuppressWarnings("unchecked")
        final Collection<Class<?>> target = (Collection<Class<?>>) col;
        @SuppressWarnings("unchecked")
        final Collection<Class<?>> source = (Collection<Class<?>>) values;
        for (final Class<?> value : source) {
          target.add(value);
        }
        return;
      }
      case BIG_INTEGER: {
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> target = (Collection<BigInteger>) col;
        @SuppressWarnings("unchecked")
        final Collection<BigInteger> source = (Collection<BigInteger>) values;
        for (final BigInteger value : source) {
          target.add(value);
        }
        return;
      }
      case BIG_DECIMAL: {
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> target = (Collection<BigDecimal>) col;
        @SuppressWarnings("unchecked")
        final Collection<BigDecimal> source = (Collection<BigDecimal>) values;
        for (final BigDecimal value : source) {
          target.add(value);
        }
        return;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Reads an object of a specified from a {@link InputStream}.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be read.
   * @param in
   *          a data input.
   * @return the object read from the data input.
   * @throws IOException
   *           if any I/O error occurs.
   * @throws ClassNotFoundException
   *           if the type of the object is a class, and it can not be loaded by
   *           the current class loader.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   */
  public static Object readObject(final Type type, final InputStream in)
      throws IOException, ClassNotFoundException, UnsupportedTypeException {
    switch (type) {
      case STRING:
        return InputUtils.readString(in, false);
      case INT: {
        final int value = InputUtils.readInt(in);
        return Integer.valueOf(value);
      }
      case LONG: {
        final long value = InputUtils.readLong(in);
        return Long.valueOf(value);
      }
      case SHORT: {
        final short value = InputUtils.readShort(in);
        return Short.valueOf(value);
      }
      case BYTE: {
        final byte value = InputUtils.readByte(in);
        return Byte.valueOf(value);
      }
      case BOOLEAN: {
        final boolean value = InputUtils.readBoolean(in);
        return Boolean.valueOf(value);
      }
      case FLOAT: {
        final float value = InputUtils.readFloat(in);
        return Float.valueOf(value);
      }
      case DOUBLE: {
        final double value = InputUtils.readDouble(in);
        return Double.valueOf(value);
      }
      case CHAR: {
        final char value = InputUtils.readChar(in);
        return Character.valueOf(value);
      }
      case CLASS:
        return InputUtils.readClass(in, false);
      case BIG_INTEGER:
        return InputUtils.readBigInteger(in, false);
      case BIG_DECIMAL:
        return InputUtils.readBigDecimal(in, false);
      case DATE:
        return InputUtils.readDate(in, false);
      case BYTE_ARRAY:
        return InputUtils.readByteArray(in, false, null);
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Reads a list of objects of a specified from a {@link InputStream}.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be read.
   * @param n
   *          the number of objects to be read.
   * @param in
   *          a data input.
   * @return the list of objects read from the data input. If the type is a
   *         primitive type, the returned list is the corresponding primitive
   *         list; otherwise, the returned list is the corresponding generic
   *         list. If {@code n} is zero, the returned list is an empty
   *         list.
   * @throws IOException
   *           if any I/O error occurs.
   * @throws ClassNotFoundException
   *           if the type of the object is a class, and it can not be loaded by
   *           the current class loader.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   */
  public static Object readList(final Type type, final int n, final InputStream in)
      throws IOException, ClassNotFoundException, UnsupportedTypeException {
    switch (type) {
      case STRING: {
        final List<String> result = new ArrayList<String>(n);
        for (int i = 0; i < n; ++i) {
          final String value = InputUtils.readString(in, true);
          result.add(value);
        }
        return result;
      }
      case INT: {
        final IntList result = new ArrayIntList(n);
        for (int i = 0; i < n; ++i) {
          final int value = InputUtils.readInt(in);
          result.add(value);
        }
        return result;
      }
      case LONG: {
        final LongList result = new ArrayLongList(n);
        for (int i = 0; i < n; ++i) {
          final long value = InputUtils.readLong(in);
          result.add(value);
        }
        return result;
      }
      case SHORT: {
        final ShortList result = new ArrayShortList(n);
        for (int i = 0; i < n; ++i) {
          final short value = InputUtils.readShort(in);
          result.add(value);
        }
        return result;
      }
      case BYTE: {
        final ByteList result = new ArrayByteList(n);
        for (int i = 0; i < n; ++i) {
          final byte value = InputUtils.readByte(in);
          result.add(value);
        }
        return result;
      }
      case BOOLEAN: {
        final BooleanList result = new ArrayBooleanList(n);
        for (int i = 0; i < n; ++i) {
          final boolean value = InputUtils.readBoolean(in);
          result.add(value);
        }
        return result;
      }
      case FLOAT: {
        final FloatList result = new ArrayFloatList(n);
        for (int i = 0; i < n; ++i) {
          final float value = InputUtils.readFloat(in);
          result.add(value);
        }
        return result;
      }
      case DOUBLE: {
        final DoubleList result = new ArrayDoubleList(n);
        for (int i = 0; i < n; ++i) {
          final double value = InputUtils.readDouble(in);
          result.add(value);
        }
        return result;
      }
      case CHAR: {
        final IntList result = new ArrayIntList(n);
        for (int i = 0; i < n; ++i) {
          final int value = InputUtils.readInt(in);
          result.add(value);
        }
        return result;
      }
      case CLASS: {
        final List<Class<?>> result = new ArrayList<Class<?>>(n);
        for (int i = 0; i < n; ++i) {
          final Class<?> value = InputUtils.readClass(in, true);
          result.add(value);
        }
        return result;
      }
      case BIG_INTEGER: {
        final List<BigInteger> result = new ArrayList<BigInteger>(n);
        for (int i = 0; i < n; ++i) {
          final BigInteger value = InputUtils.readBigInteger(in, true);
          result.add(value);
        }
        return result;
      }
      case BIG_DECIMAL: {
        final List<BigDecimal> result = new ArrayList<BigDecimal>(n);
        for (int i = 0; i < n; ++i) {
          final BigDecimal value = InputUtils.readBigDecimal(in, true);
          result.add(value);
        }
        return result;
      }
      case DATE: {
        final List<Date> result = new ArrayList<Date>(n);
        for (int i = 0; i < n; ++i) {
          final Date value = InputUtils.readDate(in, true);
          result.add(value);
        }
        return result;
      }
      case BYTE_ARRAY: {
        final List<byte[]> result = new ArrayList<byte[]>(n);
        for (int i = 0; i < n; ++i) {
          final byte[] value = InputUtils.readByteArray(in, true, null);
          result.add(value);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Writes an object of a specified to a {@link OutputStream}.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be write.
   * @param value
   *          the object to be write.
   * @param out
   *          a data output.
   * @throws IOException
   *           if any I/O error occurs.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be write.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   */
  public static void writeObject(final Type type, final Object value,
      final OutputStream out) throws IOException, ClassCastException,
      UnsupportedTypeException {
    switch (type) {
      case STRING:
        OutputUtils.writeString(out, (String) value);
        return;
      case INT:
        OutputUtils.writeInt(out, (Integer) value);
        return;
      case LONG:
        OutputUtils.writeLong(out, (Long) value);
        return;
      case SHORT:
        OutputUtils.writeShort(out, (Short) value);
        return;
      case BYTE:
        OutputUtils.writeByte(out, (Byte) value);
        return;
      case BOOLEAN:
        OutputUtils.writeBoolean(out, (Boolean) value);
        return;
      case FLOAT:
        OutputUtils.writeFloat(out, (Float) value);
        return;
      case DOUBLE:
        OutputUtils.writeDouble(out, (Double) value);
        return;
      case CHAR:
        OutputUtils.writeChar(out, (Character) value);
        return;
      case CLASS:
        OutputUtils.writeClass(out, (Class<?>) value);
        return;
      case BIG_INTEGER:
        OutputUtils.writeBigInteger(out, (BigInteger) value);
        return;
      case BIG_DECIMAL:
        OutputUtils.writeBigDecimal(out, (BigDecimal) value);
        return;
      case DATE:
        OutputUtils.writeDate(out, (Date) value);
        return;
      case BYTE_ARRAY:
        OutputUtils.writeByteArray(out, (byte[]) value);
        return;
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Writes a collection of objects of a specified to a {@link OutputStream}.
   * <p>
   * Note that the size of the collection will NEVER be written.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the object to
   *          be write.
   * @param col
   *          the collection of objects to be write. If the type is a primitive
   *          type, it must be the corresponding primitive collection;
   *          otherwise, it must be the corresponding generic collection.
   * @param out
   *          a data output.
   * @throws IOException
   *           if any I/O error occurs.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object to be write.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object is not supported.
   */
  @SuppressWarnings("unchecked")
  public static void writeCollection(final Type type, final Object col,
      final OutputStream out) throws IOException, ClassCastException,
      UnsupportedTypeException {
    switch (type) {
      case STRING: {
        final Collection<String> values = (Collection<String>) col;
        for (final String value : values) {
          OutputUtils.writeString(out, value);
        }
        return;
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final IntIterator iter = values.iterator();
        while (iter.hasNext()) {
          final int value = iter.next();
          OutputUtils.writeInt(out, value);
        }
        return;
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final LongIterator iter = values.iterator();
        while (iter.hasNext()) {
          final long value = iter.next();
          OutputUtils.writeLong(out, value);
        }
        return;
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final ShortIterator iter = values.iterator();
        while (iter.hasNext()) {
          final short value = iter.next();
          OutputUtils.writeShort(out, value);
        }
        return;
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final ByteIterator iter = values.iterator();
        while (iter.hasNext()) {
          final byte value = iter.next();
          OutputUtils.writeByte(out, value);
        }
        return;
      }
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final BooleanIterator iter = values.iterator();
        while (iter.hasNext()) {
          final boolean value = iter.next();
          OutputUtils.writeBoolean(out, value);
        }
        return;
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final FloatIterator iter = values.iterator();
        while (iter.hasNext()) {
          final float value = iter.next();
          OutputUtils.writeFloat(out, value);
        }
        return;
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final DoubleIterator iter = values.iterator();
        while (iter.hasNext()) {
          final double value = iter.next();
          OutputUtils.writeDouble(out, value);
        }
        return;
      }
      case CHAR: {
        final IntCollection values = (IntCollection) col;
        final IntIterator iter = values.iterator();
        while (iter.hasNext()) {
          final int value = iter.next();
          OutputUtils.writeInt(out, value);
        }
        return;
      }
      case CLASS: {
        final Collection<Class<?>> values = (Collection<Class<?>>) col;
        for (final Class<?> value : values) {
          OutputUtils.writeClass(out, value);
        }
        return;
      }
      case BIG_INTEGER: {
        final Collection<BigInteger> values = (Collection<BigInteger>) col;
        for (final BigInteger value : values) {
          OutputUtils.writeBigInteger(out, value);
        }
        return;
      }
      case BIG_DECIMAL: {
        final Collection<BigDecimal> values = (Collection<BigDecimal>) col;
        for (final BigDecimal value : values) {
          OutputUtils.writeBigDecimal(out, value);
        }
        return;
      }
      case DATE: {
        final Collection<Date> values = (Collection<Date>) col;
        for (final Date value : values) {
          OutputUtils.writeDate(out, value);
        }
        return;
      }
      case BYTE_ARRAY: {
        final Collection<byte[]> values = (Collection<byte[]>) col;
        for (final byte[] value : values) {
          OutputUtils.writeByteArray(out, value);
        }
        return;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  public static Object fromXmlNode(final Type type, final Element node,
      @Nullable final String prevSpaceAttr) throws XmlException {
    switch (type) {
      case BOOLEAN: {
        final boolean value = DomUtils.getReqBoolean(node);
        return Boolean.valueOf(value);
      }
      case CHAR: {
        final char value = DomUtils.getReqChar(node);
        return Character.valueOf(value);
      }
      case BYTE: {
        final byte value = DomUtils.getReqByte(node);
        return Byte.valueOf(value);
      }
      case SHORT: {
        final short value = DomUtils.getReqShort(node);
        return Short.valueOf(value);
      }
      case INT: {
        final int value = DomUtils.getReqInt(node);
        return Integer.valueOf(value);
      }
      case LONG: {
        final long value = DomUtils.getReqLong(node);
        return Long.valueOf(value);
      }
      case FLOAT: {
        final float value = DomUtils.getReqFloat(node);
        return Float.valueOf(value);
      }
      case DOUBLE: {
        final double value = DomUtils.getReqDouble(node);
        return Double.valueOf(value);
      }
      case STRING: {
        return DomUtils.getReqString(node, prevSpaceAttr, true, true);
      }
      case DATE: {
        return DomUtils.getReqDate(node, DateUtils.DEFAULT_LOCAL_DATETIME_PATTERN);
      }
      case BYTE_ARRAY: {
        return DomUtils.getReqByteArray(node, null);
      }
      case CLASS: {
        return DomUtils.getReqClass(node);
      }
      case BIG_INTEGER: {
        return DomUtils.getReqBigInteger(node);
      }
      case BIG_DECIMAL: {
        return DomUtils.getReqBigDecimal(node);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  public static Object fromXmlNodes(final Type type,
      @Nullable final List<Element> nodes,
      @Nullable final String prevSpaceAttr) throws XmlException {
    switch (type) {
      case BOOLEAN:
        return DomUtils.getReqBooleanList(nodes, null);
      case CHAR:
        return DomUtils.getReqCharList(nodes, null);
      case BYTE:
        return DomUtils.getReqByteList(nodes, null);
      case SHORT:
        return DomUtils.getReqShortList(nodes, null);
      case INT:
        return DomUtils.getReqIntList(nodes, null);
      case LONG:
        return DomUtils.getReqLongList(nodes, null);
      case FLOAT:
        return DomUtils.getReqFloatList(nodes, null);
      case DOUBLE:
        return DomUtils.getReqDoubleList(nodes, null);
      case STRING:
        return DomUtils.getReqStringList(nodes, prevSpaceAttr, true, false, null);
      case DATE:
        return DomUtils.getReqDateList(nodes, DateUtils.DEFAULT_LOCAL_DATETIME_PATTERN, null);
      case BYTE_ARRAY:
        return DomUtils.getReqByteArrayList(nodes, null, null);
      case CLASS:
        return DomUtils.getReqClassList(nodes, null);
      case BIG_INTEGER:
        return DomUtils.getReqBigIntegerList(nodes, null);
      case BIG_DECIMAL:
        return DomUtils.getReqBigDecimalList(nodes, null);
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  public static Element toXmlNode(final Type type, @Nullable final Object obj,
      final Document doc, final String tagName,
      @Nullable final String prevSpaceAttr) {
    switch (type) {
      case BOOLEAN: {
        final Boolean value = (Boolean) obj;
        final Element node = doc.createElement(tagName);
        if (value != null) {
          final String str = BooleanUtils.toString(value.booleanValue());
          node.setTextContent(str);
        }
        return node;
      }
      case CHAR: {
        final Character value = (Character) obj;
        final Element node = doc.createElement(tagName);
        if (value != null) {
          final String str = CharUtils.toString(value.charValue());
          node.setTextContent(str);
        }
        return node;
      }
      case BYTE: {
        final Byte value = (Byte) obj;
        final Element node = doc.createElement(tagName);
        if (value != null) {
          final String str = ByteUtils.toString(value.byteValue());
          node.setTextContent(str);
        }
        return node;
      }
      case SHORT: {
        final Short value = (Short) obj;
        final Element node = doc.createElement(tagName);
        if (value != null) {
          final String str = ShortUtils.toString(value.shortValue());
          node.setTextContent(str);
        }
        return node;
      }
      case INT: {
        final Integer value = (Integer) obj;
        final Element node = doc.createElement(tagName);
        if (value != null) {
          final String str = IntUtils.toString(value.intValue());
          node.setTextContent(str);
        }
        return node;
      }
      case LONG: {
        final Long value = (Long) obj;
        final Element node = doc.createElement(tagName);
        if (value != null) {
          final String str = LongUtils.toString(value.longValue());
          node.setTextContent(str);
        }
        return node;
      }
      case FLOAT: {
        final Float value = (Float) obj;
        final Element node = doc.createElement(tagName);
        if (value != null) {
          final String str = FloatUtils.toString(value.floatValue());
          node.setTextContent(str);
        }
        return node;
      }
      case DOUBLE: {
        final Double value = (Double) obj;
        final Element node = doc.createElement(tagName);
        if (value != null) {
          final String str = DoubleUtils.toString(value.doubleValue());
          node.setTextContent(str);
        }
        return node;
      }
      case STRING: {
        final String value = (String) obj;
        return StringUtils.toXmlNode(doc, tagName, prevSpaceAttr, value);
      }
      case DATE: {
        final Date value = (Date) obj;
        final Element node = doc.createElement(tagName);
        if (value != null) {
          final String str = DateUtils.toString(value, DateUtils.DEFAULT_LOCAL_DATETIME_PATTERN);
          node.setTextContent(str);
        }
        return node;
      }
      case BYTE_ARRAY: {
        final byte[] value = (byte[]) obj;
        final Element node = doc.createElement(tagName);
        if (value != null) {
          final String str = ByteArrayUtils.toString(value);
          node.setTextContent(str);
        }
        return node;
      }
      case CLASS: {
        final Class<?> value = (Class<?>) obj;
        final Element node = doc.createElement(tagName);
        if (value != null) {
          node.setTextContent(value.getName());
        }
        return node;
      }
      case BIG_INTEGER: {
        final BigInteger value = (BigInteger) obj;
        final Element node = doc.createElement(tagName);
        if (value != null) {
          final String str = BigIntegerUtils.toString(value);
          node.setTextContent(str);
        }
        return node;
      }
      case BIG_DECIMAL: {
        final BigDecimal value = (BigDecimal) obj;
        final Element node = doc.createElement(tagName);
        if (value != null) {
          final String str = BigDecimalUtils.toString(value);
          node.setTextContent(str);
        }
        return node;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  @SuppressWarnings("unchecked")
  public static List<Element> toXmlNodes(final Type type, final Object col,
      final Document doc, final Element parent,
      @Nullable final String containerName, final String tagName,
      @Nullable final String prevSpaceAttr) {
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection values = (BooleanCollection) col;
        final List<Element> result = new ArrayList<Element>();
        final BooleanIterator iter = values.iterator();
        while (iter.hasNext()) {
          final boolean value = iter.next();
          final Element node = doc.createElement(tagName);
          final String str = BooleanUtils.toString(value);
          node.setTextContent(str);
          result.add(node);
        }
        return result;
      }
      case CHAR: {
        final CharCollection values = (CharCollection) col;
        final List<Element> result = new ArrayList<Element>();
        final CharIterator iter = values.iterator();
        while (iter.hasNext()) {
          final char value = iter.next();
          final Element node = doc.createElement(tagName);
          final String str = CharUtils.toString(value);
          node.setTextContent(str);
          result.add(node);
        }
        return result;
      }
      case BYTE: {
        final ByteCollection values = (ByteCollection) col;
        final List<Element> result = new ArrayList<Element>();
        final ByteIterator iter = values.iterator();
        while (iter.hasNext()) {
          final byte value = iter.next();
          final Element node = doc.createElement(tagName);
          final String str = ByteUtils.toString(value);
          node.setTextContent(str);
          result.add(node);
        }
        return result;
      }
      case SHORT: {
        final ShortCollection values = (ShortCollection) col;
        final List<Element> result = new ArrayList<Element>();
        final ShortIterator iter = values.iterator();
        while (iter.hasNext()) {
          final short value = iter.next();
          final Element node = doc.createElement(tagName);
          final String str = ShortUtils.toString(value);
          node.setTextContent(str);
          result.add(node);
        }
        return result;
      }
      case INT: {
        final IntCollection values = (IntCollection) col;
        final List<Element> result = new ArrayList<Element>();
        final IntIterator iter = values.iterator();
        while (iter.hasNext()) {
          final int value = iter.next();
          final Element node = doc.createElement(tagName);
          final String str = IntUtils.toString(value);
          node.setTextContent(str);
          result.add(node);
        }
        return result;
      }
      case LONG: {
        final LongCollection values = (LongCollection) col;
        final List<Element> result = new ArrayList<Element>();
        final LongIterator iter = values.iterator();
        while (iter.hasNext()) {
          final long value = iter.next();
          final Element node = doc.createElement(tagName);
          final String str = LongUtils.toString(value);
          node.setTextContent(str);
          result.add(node);
        }
        return result;
      }
      case FLOAT: {
        final FloatCollection values = (FloatCollection) col;
        final List<Element> result = new ArrayList<Element>();
        final FloatIterator iter = values.iterator();
        while (iter.hasNext()) {
          final float value = iter.next();
          final Element node = doc.createElement(tagName);
          final String str = FloatUtils.toString(value);
          node.setTextContent(str);
          result.add(node);
        }
        return result;
      }
      case DOUBLE: {
        final DoubleCollection values = (DoubleCollection) col;
        final List<Element> result = new ArrayList<Element>();
        final DoubleIterator iter = values.iterator();
        while (iter.hasNext()) {
          final double value = iter.next();
          final Element node = doc.createElement(tagName);
          final String str = DoubleUtils.toString(value);
          node.setTextContent(str);
          result.add(node);
        }
        return result;
      }
      case STRING: {
        final List<String> values = (List<String>) col;
        final List<Element> result = new ArrayList<Element>();
        for (final String value : values) {
          if (value == null) {
            continue;
          }
          final Element node = StringUtils.toXmlNode(doc, tagName,
              prevSpaceAttr, value);
          result.add(node);
        }
        return result;
      }
      case DATE: {
        final List<Date> values = (List<Date>) col;
        final List<Element> result = new ArrayList<Element>();
        for (final Date value : values) {
          if (value == null) {
            continue;
          }
          final Element node = doc.createElement(tagName);
          final String str = DateUtils.toString(value, DateUtils.DEFAULT_LOCAL_DATETIME_PATTERN);
          node.setTextContent(str);
          result.add(node);
        }
        return result;
      }
      case BYTE_ARRAY: {
        final List<byte[]> values = (List<byte[]>) col;
        final List<Element> result = new ArrayList<Element>();
        for (final byte[] value : values) {
          if (value == null) {
            continue;
          }
          final Element node = doc.createElement(tagName);
          final String str = ByteArrayUtils.toString(value);
          node.setTextContent(str);
          result.add(node);
        }
        return result;
      }
      case CLASS: {
        final List<Class<?>> values = (List<Class<?>>) col;
        final List<Element> result = new ArrayList<Element>();
        for (final Class<?> value : values) {
          if (value == null) {
            continue;
          }
          final Element node = doc.createElement(tagName);
          node.setTextContent(value.getName());
          result.add(node);
        }
        return result;
      }
      case BIG_INTEGER: {
        final List<BigInteger> values = (List<BigInteger>) col;
        final List<Element> result = new ArrayList<Element>();
        for (final BigInteger value : values) {
          if (value == null) {
            continue;
          }
          final Element node = doc.createElement(tagName);
          final String str = BigIntegerUtils.toString(value);
          node.setTextContent(str);
          result.add(node);
        }
        return result;
      }
      case BIG_DECIMAL: {
        final List<BigDecimal> values = (List<BigDecimal>) col;
        final List<Element> result = new ArrayList<Element>();
        for (final BigDecimal value : values) {
          if (value == null) {
            continue;
          }
          final Element node = doc.createElement(tagName);
          final String str = BigDecimalUtils.toString(value);
          node.setTextContent(str);
          result.add(node);
        }
        return result;
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Compares two objects of the same type lexically.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the two
   *          objects.
   * @param lhs
   *          the left hand side of object. It could be null.
   * @param rhs
   *          the right hand side of object. It could be null.
   * @return 0 if the two objects equal to each other; -1 if the left hand
   *         object compares lexically less than the right hand side object; +1
   *         if the left hand object compares lexically greater than the right
   *         hand side object. Note that null considered to be less than any
   *         non-null values.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           two objects.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object does not supported.
   */
  public static int compareObject(final Type type, @Nullable final Object lhs,
      @Nullable final Object rhs) throws ClassCastException,
      UnsupportedTypeException {
    if (lhs == null) {
      return (rhs == null ? 0 : -1);
    } else if (rhs == null) {
      return +1;
    }
    switch (type) {
      case BOOLEAN:
        return ((Boolean) lhs).compareTo((Boolean) rhs);
      case CHAR:
        return ((Character) lhs).compareTo((Character) rhs);
      case BYTE:
        return ((Byte) lhs).compareTo((Byte) rhs);
      case SHORT:
        return ((Short) lhs).compareTo((Short) rhs);
      case INT:
        return ((Integer) lhs).compareTo((Integer) rhs);
      case LONG:
        return ((Long) lhs).compareTo((Long) rhs);
      case FLOAT:
        return ((Float) lhs).compareTo((Float) rhs);
      case DOUBLE:
        return ((Double) lhs).compareTo((Double) rhs);
      case STRING:
        return ((String) lhs).compareTo((String) rhs);
      case DATE:
        return ((Date) lhs).compareTo((Date) rhs);
      case BYTE_ARRAY:
        return Comparison.compare((byte[]) lhs, (byte[]) rhs);
      case CLASS:
        return Comparison.compare((Class<?>) lhs, (Class<?>) rhs);
      case BIG_INTEGER:
        return ((BigInteger) lhs).compareTo((BigInteger) rhs);
      case BIG_DECIMAL:
        return ((BigDecimal) lhs).compareTo((BigDecimal) rhs);
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Compares two collection of values of the same type lexically.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the objects
   *          in the lists.
   * @param lhs
   *          the left hand side of collection of values. It could be null. If
   *          the type is a primitive type, it must be the corresponding
   *          primitive collection; otherwise, it must be the corresponding
   *          generic collection.
   * @param rhs
   *          the right hand side of collection of values. It could be null.If
   *          the type is a primitive type, it must be the corresponding
   *          primitive collection; otherwise, it must be the corresponding
   *          generic collection.
   * @return 0 if the two collections of values equal to each other; -1 if the
   *         left hand side collection of values compares lexically less than
   *         the right hand side collection of values; +1 if the left hand side
   *         collection of values compares lexically greater than the right hand
   *         side collection of values. Note that null considered to be less
   *         than any non-null values.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects of the lists.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object does not supported.
   */
  @SuppressWarnings("unchecked")
  public static int compareCollections(final Type type,
      @Nullable final Object lhs, @Nullable final Object rhs)
      throws ClassCastException, UnsupportedTypeException {
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection lhsCol = (BooleanCollection) lhs;
        final BooleanCollection rhsCol = (BooleanCollection) rhs;
        return Comparison.compare(lhsCol, rhsCol);
      }
      case CHAR: {
        final CharCollection lhsCol = (CharCollection) lhs;
        final CharCollection rhsCol = (CharCollection) rhs;
        return Comparison.compare(lhsCol, rhsCol);
      }
      case BYTE: {
        final ByteCollection lhsCol = (ByteCollection) lhs;
        final ByteCollection rhsCol = (ByteCollection) rhs;
        return Comparison.compare(lhsCol, rhsCol);
      }
      case SHORT: {
        final ShortCollection lhsCol = (ShortCollection) lhs;
        final ShortCollection rhsCol = (ShortCollection) rhs;
        return Comparison.compare(lhsCol, rhsCol);
      }
      case INT: {
        final IntCollection lhsCol = (IntCollection) lhs;
        final IntCollection rhsCol = (IntCollection) rhs;
        return Comparison.compare(lhsCol, rhsCol);
      }
      case LONG: {
        final LongCollection lhsCol = (LongCollection) lhs;
        final LongCollection rhsCol = (LongCollection) rhs;
        return Comparison.compare(lhsCol, rhsCol);
      }
      case FLOAT: {
        final FloatCollection lhsCol = (FloatCollection) lhs;
        final FloatCollection rhsCol = (FloatCollection) rhs;
        return Comparison.compare(lhsCol, rhsCol);
      }
      case DOUBLE: {
        final DoubleCollection lhsCol = (DoubleCollection) lhs;
        final DoubleCollection rhsCol = (DoubleCollection) rhs;
        return Comparison.compare(lhsCol, rhsCol);
      }
      case STRING: {
        final Collection<String> lhsCol = (Collection<String>) lhs;
        final Collection<String> rhsCol = (Collection<String>) rhs;
        return Comparison.compare(lhsCol, rhsCol);
      }
      case DATE: {
        final Collection<Date> lhsCol = (Collection<Date>) lhs;
        final Collection<Date> rhsCol = (Collection<Date>) rhs;
        return Comparison.compare(lhsCol, rhsCol);
      }
      case BYTE_ARRAY: {
        final Collection<byte[]> lhsCol = (Collection<byte[]>) lhs;
        final Collection<byte[]> rhsCol = (Collection<byte[]>) rhs;
        return Comparison.compare(lhsCol, rhsCol);
      }
      case CLASS: {
        final Collection<Class<?>> lhsCol = (Collection<Class<?>>) lhs;
        final Collection<Class<?>> rhsCol = (Collection<Class<?>>) rhs;
        return Comparison.compare(lhsCol, rhsCol);
      }
      case BIG_INTEGER: {
        final Collection<BigInteger> lhsCol = (Collection<BigInteger>) lhs;
        final Collection<BigInteger> rhsCol = (Collection<BigInteger>) rhs;
        return Comparison.compare(lhsCol, rhsCol);
      }
      case BIG_DECIMAL: {
        final Collection<BigDecimal> lhsCol = (Collection<BigDecimal>) lhs;
        final Collection<BigDecimal> rhsCol = (Collection<BigDecimal>) rhs;
        return Comparison.compare(lhsCol, rhsCol);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Compares two arrays of values of the same type lexically.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the objects
   *          in the lists.
   * @param lhs
   *          the left hand side of array of values. It could be null. If
   *          the type is a primitive type, it must be the corresponding
   *          primitive array; otherwise, it must be the corresponding
   *          object array.
   * @param rhs
   *          the right hand side of array of values. It could be null.If
   *          the type is a primitive type, it must be the corresponding
   *          primitive array; otherwise, it must be the corresponding
   *          object array.
   * @return 0 if the two arrays of values equal to each other; -1 if the
   *         left hand side array of values compares lexically less than
   *         the right hand side array of values; +1 if the left hand side
   *         array of values compares lexically greater than the right hand
   *         side array of values. Note that null considered to be less
   *         than any non-null values.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects of the lists.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object does not supported.
   */
  public static int compareArrays(final Type type,
      @Nullable final Object lhs, @Nullable final Object rhs)
      throws ClassCastException, UnsupportedTypeException {
    switch (type) {
      case BOOLEAN: {
        final boolean[] lhsArray = (boolean[]) lhs;
        final boolean[] rhsArray = (boolean[]) rhs;
        return Comparison.compare(lhsArray, rhsArray);
      }
      case CHAR: {
        final char[] lhsArray = (char[]) lhs;
        final char[] rhsArray = (char[]) rhs;
        return Comparison.compare(lhsArray, rhsArray);
      }
      case BYTE: {
        final byte[] lhsArray = (byte[]) lhs;
        final byte[] rhsArray = (byte[]) rhs;
        return Comparison.compare(lhsArray, rhsArray);
      }
      case SHORT: {
        final short[] lhsArray = (short[]) lhs;
        final short[] rhsArray = (short[]) rhs;
        return Comparison.compare(lhsArray, rhsArray);
      }
      case INT: {
        final int[] lhsArray = (int[]) lhs;
        final int[] rhsArray = (int[]) rhs;
        return Comparison.compare(lhsArray, rhsArray);
      }
      case LONG: {
        final long[] lhsArray = (long[]) lhs;
        final long[] rhsArray = (long[]) rhs;
        return Comparison.compare(lhsArray, rhsArray);
      }
      case FLOAT: {
        final float[] lhsArray = (float[]) lhs;
        final float[] rhsArray = (float[]) rhs;
        return Comparison.compare(lhsArray, rhsArray);
      }
      case DOUBLE: {
        final double[] lhsArray = (double[]) lhs;
        final double[] rhsArray = (double[]) rhs;
        return Comparison.compare(lhsArray, rhsArray);
      }
      case STRING: {
        final String[] lhsArray = (String[]) lhs;
        final String[] rhsArray = (String[]) rhs;
        return Comparison.compare(lhsArray, rhsArray);
      }
      case DATE: {
        final Date[] lhsArray = (Date[]) lhs;
        final Date[] rhsArray = (Date[]) rhs;
        return Comparison.compare(lhsArray, rhsArray);
      }
      case BYTE_ARRAY: {
        final byte[][] lhsArray = (byte[][]) lhs;
        final byte[][] rhsArray = (byte[][]) rhs;
        return Comparison.compare(lhsArray, rhsArray);
      }
      case CLASS: {
        final Class<?>[] lhsArray = (Class<?>[]) lhs;
        final Class<?>[] rhsArray = (Class<?>[]) rhs;
        return Comparison.compare(lhsArray, rhsArray);
      }
      case BIG_INTEGER: {
        final BigInteger[] lhsArray = (BigInteger[]) lhs;
        final BigInteger[] rhsArray = (BigInteger[]) rhs;
        return Comparison.compare(lhsArray, rhsArray);
      }
      case BIG_DECIMAL: {
        final BigDecimal[] lhsArray = (BigDecimal[]) lhs;
        final BigDecimal[] rhsArray = (BigDecimal[]) rhs;
        return Comparison.compare(lhsArray, rhsArray);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Tests the equality of two objects of the same type.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the two
   *          objects.
   * @param lhs
   *          the left hand side of object. It could be null.
   * @param rhs
   *          the right hand side of object. It could be null.
   * @return true if the two objects equal to each other; false otherwise. Note
   *         that null only equals to null.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           two objects.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object does not supported.
   */
  public static boolean equalsObject(final Type type, @Nullable final Object lhs,
      @Nullable final Object rhs) throws ClassCastException,
      UnsupportedTypeException {
    if (lhs == null) {
      return (rhs == null);
    } else if (rhs == null) {
      return false;
    }
    switch (type) {
      case STRING:
        return ((String) lhs).equals(rhs);
      case BOOLEAN:
        return ((Boolean) lhs).equals(rhs);
      case CHAR:
        return ((Character) lhs).equals(rhs);
      case BYTE:
        return ((Byte) lhs).equals(rhs);
      case SHORT:
        return ((Short) lhs).equals(rhs);
      case INT:
        return ((Integer) lhs).equals(rhs);
      case LONG:
        return ((Long) lhs).equals(rhs);
      case FLOAT:
        return ((Float) lhs).equals(rhs);
      case DOUBLE:
        return ((Double) lhs).equals(rhs);
      case BIG_INTEGER:
        return ((BigInteger) lhs).equals(rhs);
      case BIG_DECIMAL:
        // note that BigDecimal's natural ordering is inconsistent with equals.
        return (((BigDecimal) lhs).compareTo((BigDecimal) rhs) == 0);
      case DATE:
        return ((Date) lhs).equals(rhs);
      case BYTE_ARRAY:
        return Equality.equals((byte[]) lhs, (byte[]) rhs);
      case CLASS:
        return ((Class<?>) lhs == (Class<?>) rhs);
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Tests the equality of two collection of values of the same type.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the list of
   *          objects.
   * @param lhs
   *          the left hand side of collection of values. It could be null. If
   *          the type is a primitive type, it must be the corresponding
   *          primitive collection; otherwise, it must be the corresponding
   *          generic collection.
   * @param rhs
   *          the right hand side of collection of values. It could be null.If
   *          the type is a primitive type, it must be the corresponding
   *          primitive collection; otherwise, it must be the corresponding
   *          generic collection.
   * @return true if the two collections equal to each other; false otherwise.
   *         Note that null only equals to null.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects of the lists.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object does not supported.
   */
  @SuppressWarnings("unchecked")
  public static boolean equalsCollections(final Type type,
      @Nullable final Object lhs, @Nullable final Object rhs)
      throws ClassCastException, UnsupportedTypeException {
    switch (type) {
      case BOOLEAN: {
        final BooleanCollection lhsCol = (BooleanCollection) lhs;
        final BooleanCollection rhsCol = (BooleanCollection) rhs;
        return Equality.equals(lhsCol, rhsCol);
      }
      case CHAR: {
        final CharCollection lhsCol = (CharCollection) lhs;
        final CharCollection rhsCol = (CharCollection) rhs;
        return Equality.equals(lhsCol, rhsCol);
      }
      case BYTE: {
        final ByteCollection lhsCol = (ByteCollection) lhs;
        final ByteCollection rhsCol = (ByteCollection) rhs;
        return Equality.equals(lhsCol, rhsCol);
      }
      case SHORT: {
        final ShortCollection lhsCol = (ShortCollection) lhs;
        final ShortCollection rhsCol = (ShortCollection) rhs;
        return Equality.equals(lhsCol, rhsCol);
      }
      case INT: {
        final IntCollection lhsCol = (IntCollection) lhs;
        final IntCollection rhsCol = (IntCollection) rhs;
        return Equality.equals(lhsCol, rhsCol);
      }
      case LONG: {
        final LongCollection lhsCol = (LongCollection) lhs;
        final LongCollection rhsCol = (LongCollection) rhs;
        return Equality.equals(lhsCol, rhsCol);
      }
      case FLOAT: {
        final FloatCollection lhsCol = (FloatCollection) lhs;
        final FloatCollection rhsCol = (FloatCollection) rhs;
        return Equality.equals(lhsCol, rhsCol);
      }
      case DOUBLE: {
        final DoubleCollection lhsCol = (DoubleCollection) lhs;
        final DoubleCollection rhsCol = (DoubleCollection) rhs;
        return Equality.equals(lhsCol, rhsCol);
      }
      case STRING: {
        final Collection<String> lhsCol = (Collection<String>) lhs;
        final Collection<String> rhsCol = (Collection<String>) rhs;
        return Equality.equals(lhsCol, rhsCol);
      }
      case DATE: {
        final Collection<Date> lhsCol = (Collection<Date>) lhs;
        final Collection<Date> rhsCol = (Collection<Date>) rhs;
        return Equality.equals(lhsCol, rhsCol);
      }
      case BYTE_ARRAY: {
        final Collection<byte[]> lhsCol = (Collection<byte[]>) lhs;
        final Collection<byte[]> rhsCol = (Collection<byte[]>) rhs;
        return Equality.equals(lhsCol, rhsCol);
      }
      case CLASS: {
        final Collection<Class<?>> lhsCol = (Collection<Class<?>>) lhs;
        final Collection<Class<?>> rhsCol = (Collection<Class<?>>) rhs;
        return Equality.equals(lhsCol, rhsCol);
      }
      case BIG_INTEGER: {
        final Collection<BigInteger> lhsCol = (Collection<BigInteger>) lhs;
        final Collection<BigInteger> rhsCol = (Collection<BigInteger>) rhs;
        return Equality.equals(lhsCol, rhsCol);
      }
      case BIG_DECIMAL: {
        final Collection<BigDecimal> lhsCol = (Collection<BigDecimal>) lhs;
        final Collection<BigDecimal> rhsCol = (Collection<BigDecimal>) rhs;
        return Equality.equals(lhsCol, rhsCol);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }


  /**
   * Tests the equality of two arrays of values of the same type.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the list of
   *          objects.
   * @param lhs
   *          the left hand side of array of values. It could be null. If
   *          the type is a primitive type, it must be the corresponding
   *          primitive array; otherwise, it must be the corresponding
   *          generic array.
   * @param rhs
   *          the right hand side of array of values. It could be null.If
   *          the type is a primitive type, it must be the corresponding
   *          primitive array; otherwise, it must be the corresponding
   *          generic array.
   * @return true if the two arrays equal to each other; false otherwise.
   *         Note that null only equals to null.
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects of the lists.
   * @throws UnsupportedTypeException
   *           if the {@link Type} object does not supported.
   */
  public static boolean equalsArrays(final Type type,
      @Nullable final Object lhs, @Nullable final Object rhs)
      throws ClassCastException, UnsupportedTypeException {
    switch (type) {
      case BOOLEAN: {
        final boolean[] lhsArray = (boolean[]) lhs;
        final boolean[] rhsArray = (boolean[]) rhs;
        return Equality.equals(lhsArray, rhsArray);
      }
      case CHAR: {
        final char[] lhsArray = (char[]) lhs;
        final char[] rhsArray = (char[]) rhs;
        return Equality.equals(lhsArray, rhsArray);
      }
      case BYTE: {
        final byte[] lhsArray = (byte[]) lhs;
        final byte[] rhsArray = (byte[]) rhs;
        return Equality.equals(lhsArray, rhsArray);
      }
      case SHORT: {
        final short[] lhsArray = (short[]) lhs;
        final short[] rhsArray = (short[]) rhs;
        return Equality.equals(lhsArray, rhsArray);
      }
      case INT: {
        final int[] lhsArray = (int[]) lhs;
        final int[] rhsArray = (int[]) rhs;
        return Equality.equals(lhsArray, rhsArray);
      }
      case LONG: {
        final long[] lhsArray = (long[]) lhs;
        final long[] rhsArray = (long[]) rhs;
        return Equality.equals(lhsArray, rhsArray);
      }
      case FLOAT: {
        final float[] lhsArray = (float[]) lhs;
        final float[] rhsArray = (float[]) rhs;
        return Equality.equals(lhsArray, rhsArray);
      }
      case DOUBLE: {
        final double[] lhsArray = (double[]) lhs;
        final double[] rhsArray = (double[]) rhs;
        return Equality.equals(lhsArray, rhsArray);
      }
      case STRING: {
        final String[] lhsArray = (String[]) lhs;
        final String[] rhsArray = (String[]) rhs;
        return Equality.equals(lhsArray, rhsArray);
      }
      case DATE: {
        final Date[] lhsArray = (Date[]) lhs;
        final Date[] rhsArray = (Date[]) rhs;
        return Equality.equals(lhsArray, rhsArray);
      }
      case BYTE_ARRAY: {
        final byte[][] lhsArray = (byte[][]) lhs;
        final byte[][] rhsArray = (byte[][]) rhs;
        return Equality.equals(lhsArray, rhsArray);
      }
      case CLASS: {
        final Class<?>[] lhsArray = (Class<?>[]) lhs;
        final Class<?>[] rhsArray = (Class<?>[]) rhs;
        return Equality.equals(lhsArray, rhsArray);
      }
      case BIG_INTEGER: {
        final BigInteger[] lhsArray = (BigInteger[]) lhs;
        final BigInteger[] rhsArray = (BigInteger[]) rhs;
        return Equality.equals(lhsArray, rhsArray);
      }
      case BIG_DECIMAL: {
        final BigDecimal[] lhsArray = (BigDecimal[]) lhs;
        final BigDecimal[] rhsArray = (BigDecimal[]) rhs;
        return Equality.equals(lhsArray, rhsArray);
      }
      default:
        throw new UnsupportedTypeException(type);
    }
  }

  /**
   * Gets the hash code of an object in the specified type.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the two
   *          objects.
   * @param value
   *          an object of the specified type.
   * @return the hash code of the object.
   * @throws ClassCastException
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           object.
   */
  public static int hashCodeOfObject(final Type type, final @Nullable Object value)
      throws ClassCastException {
    if (value == null) {
      return 0;
    }
    switch (type) {
      case BYTE_ARRAY:
        return Hash.combine(0, 0, (byte[]) value);
      default:
        return value.hashCode();
    }
  }

  /**
   * Gets the hash code of a list of objects in the specified type.
   *
   * @param type
   *          the {@link Type} object corresponding to the type of the two
   *          objects.
   * @param values
   *          a collection of values of the specified type. If the type is a
   *          primitive type, it must be the corresponding primitive collection;
   *          otherwise, it must be the corresponding generic collection.
   * @return the hash code of the list of objects.
   * @throws ClassCastException
   * @throws ClassCastException
   *           if the {@link Type} object does not correspond to the type of the
   *           objects.
   */
  public static int hashCodeOfCollection(final Type type, final @Nullable Object values)
      throws ClassCastException {
    if (values == null) {
      return 0;
    }
    switch (type) {
      case BYTE_ARRAY: {
        @SuppressWarnings("unchecked")
        final Collection<byte[]> col = (Collection<byte[]>) values;
        int code = 11;
        final int multiplier = 131;
        for (final byte[] value : col) {
          code = Hash.combine(code, multiplier, value);
        }
        return code;
      }
      default:
        return values.hashCode();
    }
  }
}
