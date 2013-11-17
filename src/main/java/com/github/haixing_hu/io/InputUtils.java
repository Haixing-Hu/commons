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

package com.github.haixing_hu.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UTFDataFormatException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import com.github.haixing_hu.collection.primitive.BooleanList;
import com.github.haixing_hu.collection.primitive.ByteList;
import com.github.haixing_hu.collection.primitive.CharList;
import com.github.haixing_hu.collection.primitive.DoubleList;
import com.github.haixing_hu.collection.primitive.FloatList;
import com.github.haixing_hu.collection.primitive.IntList;
import com.github.haixing_hu.collection.primitive.LongList;
import com.github.haixing_hu.collection.primitive.ShortList;
import com.github.haixing_hu.collection.primitive.impl.ArrayBooleanList;
import com.github.haixing_hu.collection.primitive.impl.ArrayByteList;
import com.github.haixing_hu.collection.primitive.impl.ArrayCharList;
import com.github.haixing_hu.collection.primitive.impl.ArrayDoubleList;
import com.github.haixing_hu.collection.primitive.impl.ArrayFloatList;
import com.github.haixing_hu.collection.primitive.impl.ArrayIntList;
import com.github.haixing_hu.collection.primitive.impl.ArrayLongList;
import com.github.haixing_hu.collection.primitive.impl.ArrayShortList;
import com.github.haixing_hu.io.exception.InvalidFormatException;
import com.github.haixing_hu.io.exception.SerializationException;
import com.github.haixing_hu.io.serialize.BinarySerialization;
import com.github.haixing_hu.io.serialize.BinarySerializer;
import com.github.haixing_hu.io.serialize.NoBinarySerializerRegisteredException;
import com.github.haixing_hu.lang.ArrayUtils;
import com.github.haixing_hu.lang.StringUtils;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import static com.github.haixing_hu.CommonsMessages.*;

/**
 * Provides functions to read data from the {@link Input} object.
 *
 * @author Haixing Hu
 */
public final class InputUtils {

  /**
   * Reads specified number of bytes from the input.
   *
   * <p>This method blocks until one of the following conditions occurs:</p>
   *
   * <ul>
   * <li><code>len</code> bytes of input data are available, in which case a
   * normal return is made.</li>
   * <li>End of file is detected, in which case an <code>EOFException</code> is
   * thrown.</li>
   * <li>An I/O error occurs, in which case an <code>IOException</code> other
   * than <code>EOFException</code> is thrown.</li>
   * </ul>
   *
   * <p>If <code>len</code> is zero, then no bytes are read. Otherwise, the first
   * byte read is stored into element <code>buffer[off]</code>, the next one
   * into <code>buffer[off+1]</code>, and so on. The number of bytes read is
   * always equal to <code>len</code>.</p>
   *
   * @param in
   *          the input source where to read the data.
   * @param buf
   *          the byte array where to store the bytes read from the input.
   * @param off
   *          the start offset in array <code>buffer</code> at which the data is
   *          written.
   * @param len
   *          the number of bytes need to read.
   * @throws NullPointerException
   *           If <code>buffer</code> is null.
   * @throws IndexOutOfBoundsException
   *           If <code>off</code> is negative, <code>len</code> is negative, or
   *           <code>len</code> is greater than <code>buff.length - off</code>.
   * @throws EOFException
   *           if this input reaches the end before reading <code>len</code>
   *           bytes.
   * @throws IOException
   *           if an I/O error occurs.
   * @see #read(byte[], int, int)
   */
  public static void readFully(final InputStream in, final byte[] buf, final int off,
      final int len) throws IOException {
    int n = 0;
    while (n < len) {
      final int count = in.read(buf, off + n, len - n);
      if (count < 0) {
        throw new EOFException();
      }
      n += count;
    }
  }

  public static boolean readNullMark(final InputStream in) throws IOException {
    final int ch = in.read();
    if (ch < 0) {
      throw new EOFException();
    }
    return (ch != 0);
  }

  /**
   * Reads a <code>boolean</code> value from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @return a <code>boolean</code> value read from the input.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>boolean</code> value.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static boolean readBoolean(final InputStream in) throws IOException {
    final int ch = in.read();
    if (ch < 0) {
      throw new EOFException();
    }
    return (ch != 0);
  }

  /**
   * Reads a <code>Boolean</code> object from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>Boolean</code> object to be read could be a
   *          null value; otherwise, if the <code>Boolean</code> object read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @return a <code>Boolean</code> object read from the input.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>Boolean</code> object.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Boolean readBooleanObject(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final boolean value = readBoolean(in);
    return new Boolean(value);
  }

  /**
   * Reads a <code>char</code> value from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @return a <code>char</code> value read from the input.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>char</code> value.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static char readChar(final InputStream in) throws IOException {
    return (char) readVarShort(in);
  }

  /**
   * Reads a <code>Character</code> object from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>Character</code> object to be read could be a
   *          null value; otherwise, if the <code>Character</code> object read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @return a <code>Character</code> object read from the input.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>Character</code> object.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Character readCharObject(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final char value = readChar(in);
    return new Character(value);
  }

  /**
   * Reads a <code>byte</code> value from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @return a <code>byte</code> value read from the input.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>byte</code> value.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static byte readByte(final InputStream in) throws IOException {
    final int ch = in.read();
    if (ch < 0) {
      throw new EOFException();
    }
    return (byte)(ch);
  }

  /**
   * Reads a <code>Byte</code> object from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>Byte</code> object to be read could be a
   *          null value; otherwise, if the <code>Byte</code> object read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @return a <code>Byte</code> object read from the input.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>Byte</code> object.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Byte readByteObject(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final byte value = readByte(in);
    return new Byte(value);
  }

  /**
   * Reads a <code>short</code> value from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @return a <code>short</code> value read from the input.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>short</code> value.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static short readShort(final InputStream in) throws IOException {
    final int ch1 = in.read();
    final int ch2 = in.read();
    if ((ch1 | ch2) < 0) {
      throw new EOFException();
    }
    return (short)((ch1 << 8) | ch2);
  }

  /**
   * Reads a <code>Short</code> object from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>Short</code> object to be read could be a
   *          null value; otherwise, if the <code>Short</code> object read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @return a <code>Short</code> object read from the input.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>Short</code> object.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Short readShortObject(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final short value = readShort(in);
    return new Short(value);
  }

  /**
   * Reads a variant length encoded <code>short</code> value from the input.
   *
   * <p>A variable-length format for positive integers is defined where the
   * high-order bit of each byte indicates whether more bytes remain to be read.
   * The low-order seven bits are appended as increasingly more significant bits
   * in the resulting integer value. Thus values from zero to 127 may be stored
   * in a single byte, values from 128 to 16,383 may be stored in two bytes, and
   * so on. This provides compression while still being efficient to decode.</p>
   *
   * <p>This function will read between one and three bytes. Smaller values take
   * fewer bytes. Negative numbers are not supported.</p>
   *
   * @param in
   *          the input source where to read the data.
   * @return a variant length encoded <code>short</code> value read from the
   *         input. Note that the <code>short</code> value encoded is always
   *         a positive value, therefore the returned value is always between
   *         0 and 32767.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>short</code> value.
   * @throws InvalidFormatExceptoin
   *           if the <code>short</code> value is not correctly encoded.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static short readVarShort(final InputStream in) throws IOException {
    int ch = in.read();
    if (ch < 0) {
      throw new EOFException();
    }
    int value = ch & 0x7F;
    for (int shift = 7; (ch & 0x80) != 0; shift +=7) {
      ch = in.read();
      if (ch < 0) {
        throw new EOFException();
      }
      if (shift == 14) {
        // A variant length short is encoded in at most three bytes,
        // so shift == 14 indicate that this is the last possible byte
        // of a variant length encoded short. Since the maximum positive
        // short has 15 bits of 1s, the last byte read should be less
        // equal than 0x01 (or one 1s in binary form).
        if (ch > 0x01) {
          throw new InvalidFormatException(MALFORMED_VINT_ERROR);
        }
      }
      value |= (ch & 0x7F) << shift;
    }
    assert (value <= Short.MAX_VALUE);
    return (short) value;
  }

  /**
   * Reads a variable length <code>Short</code> object from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>Short</code> object to be read could be a
   *          null value; otherwise, if the <code>Short</code> object read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @return a <code>Short</code> object read from the input.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>Short</code> object.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Short readVarShortObject(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final short value = readVarShort(in);
    return new Short(value);
  }

  /**
   * Reads a <code>int</code> value from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @return a <code>int</code> value read from the input.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>int</code> value.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static int readInt(final InputStream in) throws IOException {
    final int ch1 = in.read();
    final int ch2 = in.read();
    final int ch3 = in.read();
    final int ch4 = in.read();
    if ((ch1 | ch2 | ch3 | ch4) < 0) {
      throw new EOFException();
    }
    return ((ch1 << 24) | (ch2 << 16) | (ch3 << 8) | ch4);
  }

  /**
   * Reads a <code>Integer</code> object from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>Integer</code> object to be read could be a
   *          null value; otherwise, if the <code>Integer</code> object read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @return a <code>Integer</code> object read from the input.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>Integer</code> object.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Integer readIntObject(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int value = readInt(in);
    return new Integer(value);
  }

  /**
   * Reads a variant length encoded <code>int</code> value from the input.
   *
   * <p>A variable-length format for positive integers is defined where the
   * high-order bit of each byte indicates whether more bytes remain to be read.
   * The low-order seven bits are appended as increasingly more significant bits
   * in the resulting integer value. Thus values from zero to 127 may be stored
   * in a single byte, values from 128 to 16,383 may be stored in two bytes, and
   * so on. This provides compression while still being efficient to decode.</p>
   *
   * <p>This function will read between one and five bytes. Smaller values take
   * fewer bytes. Negative numbers are not supported.</p>
   *
   * @param in
   *          the input source where to read the data.
   * @return a variant length encoded <code>int</code> value read from the
   *         input. Note that the <code>int</code> value encoded is always
   *         a positive value, therefore the returned value is always between
   *         0 and 2147483647.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>int</code> value.
   * @throws InvalidFormatExceptoin
   *           if the <code>int</code> value is not correctly encoded.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static int readVarInt(final InputStream in) throws IOException {
    int ch = in.read();
    if (ch < 0) {
      throw new EOFException();
    }
    int value = ch & 0x7F;
    for (int shift = 7; (ch & 0x80) != 0; shift += 7) {
      ch = in.read();
      if (ch < 0) {
        throw new EOFException();
      }
      if (shift == 28) {
        // A variant length int is encoded in at most five bytes,
        // so shift == 28 indicate that this is the last possible byte
        // of a variant length encoded int. Since the maximum positive
        // int has 31 bits of 1s, the last byte read should be less
        // equal than 0x07 (or three 1s in binary form).
        if (ch > 0x07) {
          throw new InvalidFormatException(MALFORMED_VINT_ERROR);
        }
      }
      value |= (ch & 0x7F) << shift;
    }
    return value;
  }

  /**
   * Reads a variable length <code>Integer</code> object from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>Integer</code> object to be read could be a
   *          null value; otherwise, if the <code>Integer</code> object read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @return a <code>Integer</code> object read from the input.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>Integer</code> object.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Integer readVarIntObject(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int value = readVarInt(in);
    return new Integer(value);
  }

  /**
   * Reads a <code>long</code> value from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @return a <code>long</code> value read from the input.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>long</code> value.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static long readLong(final InputStream in) throws IOException {
    final byte[] buffer = new byte[8];
    readFully(in, buffer, 0, 8);
    return (((long)buffer[0] << 56)
            | ((long)(buffer[1] & 0xFF) << 48)
            | ((long)(buffer[2] & 0xFF) << 40)
            | ((long)(buffer[3] & 0xFF) << 32)
            | ((long)(buffer[4] & 0xFF) << 24)
            | ((long)(buffer[5] & 0xFF) << 16)
            | ((long)(buffer[6] & 0xFF) <<  8)
            | ((buffer[7] & 0xFF)));
  }

  /**
   * Reads a <code>Long</code> object from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>Long</code> object to be read could be a
   *          null value; otherwise, if the <code>Long</code> object read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @return a <code>Long</code> object read from the input.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>Long</code> object.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Long readLongObject(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final long value = readLong(in);
    return new Long(value);
  }

  /**
   * Reads a variant length encoded <code>long</code> value from the input.
   *
   * <p>A variable-length format for positive integers is defined where the
   * high-order bit of each byte indicates whether more bytes remain to be read.
   * The low-order seven bits are appended as increasingly more significant bits
   * in the resulting integer value. Thus values from zero to 127 may be stored
   * in a single byte, values from 128 to 16,383 may be stored in two bytes, and
   * so on. This provides compression while still being efficient to decode.</p>
   *
   * <p>This function will read between one and nine bytes. Smaller values take
   * fewer bytes. Negative numbers are not supported.</p>
   *
   * @param in
   *          the input source where to read the data.
   * @return a variant length encoded <code>long</code> value read from the
   *         input. Note that the <code>long</code> value encoded is always
   *         a positive value, therefore the returned value is always between
   *         0 and 9223372036854775807.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>long</code> value.
   * @throws InvalidFormatExceptoin
   *           if the <code>long</code> value is not correctly encoded.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static long readVarLong(final InputStream in) throws IOException {
    int ch = in.read();
    if (ch < 0) {
      throw new EOFException();
    }
    long value = ch & 0x7F;
    for (int shift = 7; (ch & 0x80) != 0; shift += 7) {
      ch = in.read();
      if (ch < 0) {
        throw new EOFException();
      }
      if (shift == 56) {
        // A variant length long is encoded in at most nine bytes,
        // so shift == 56 indicate that this is the last possible byte
        // of a variant length encoded long. Since the maximum positive
        // long has 63 bits of 1s, the last byte read should be less
        // equal than 0x7F (or seven 1s in binary form).
        if (ch > 0x7F) {
          throw new InvalidFormatException(MALFORMED_VINT_ERROR);
        }
      }
      // XXX: NEVER forget to convert the (ch & 0x7F) to long before left shifting
      value |= (long)(ch & 0x7F) << shift;
    }
    return value;
  }

  /**
   * Reads a variable length<code>Long</code> object from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>Long</code> object to be read could be a
   *          null value; otherwise, if the <code>Long</code> object read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @return a <code>Long</code> object read from the input.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>Long</code> object.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Long readVarLongObject(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final long value = readVarLong(in);
    return new Long(value);
  }

  /**
   * Reads a <code>float</code> value from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @return a <code>float</code> value read from the input.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>float</code> value.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static float readFloat(final InputStream in) throws IOException {
    final int intBits = readInt(in);
    return Float.intBitsToFloat(intBits);
  }

  /**
   * Reads a <code>Float</code> object from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>Float</code> object to be read could be a
   *          null value; otherwise, if the <code>Float</code> object read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @return a <code>Float</code> object read from the input.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>Float</code> object.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Float readFloatObject(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final float value = readFloat(in);
    return new Float(value);
  }

  /**
   * Reads a <code>double</code> value from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @return a <code>double</code> value read from the input.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>double</code> value.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static double readDouble(final InputStream in) throws IOException {
    final long longBits = readLong(in);
    return Double.longBitsToDouble(longBits);
  }

  /**
   * Reads a <code>Double</code> object from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>Double</code> object to be read could be a
   *          null value; otherwise, if the <code>Double</code> object read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @return a <code>Double</code> object read from the input.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>Double</code> object.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Double readDoubleObject(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final double value = readDouble(in);
    return new Double(value);
  }

  /**
   * Reads a <code>String</code> value from the input.
   *
   * <p>The <code>String</code> is encoded in the modified UTF-8 format, as
   * described in the {@link java.io.DataInput} interface.</p>
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>String</code> value to be read could be a
   *          null value; otherwise, if the <code>String</code> value read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @return a <code>String</code> value read from the input. Note that it could
   *         be null if the value read from the input is a null and the argument
   *         <code>allowNull<code> is true.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>String</code> value.
   * @throws InvalidFormatException
   *           if the value read from the input is a null value and the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static String readString(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int utflen = readVarInt(in);
    if (utflen == 0) {
      return StringUtils.EMPTY;
    }
    final byte[] byteBuffer = new byte[utflen];
    final char[] charBuffer = new char[utflen];
    int ch, ch2, ch3;
    int count = 0;
    int charCount = 0;
    readFully(in, byteBuffer, 0, utflen);
    // optimization for ASCII string
    while (count < utflen) {
      ch = byteBuffer[count] & 0xff;
      if (ch > 127) {
        break;
      }
      count++;
      charBuffer[charCount++] = (char) ch;
    }
    // decode the remained bytes
    while (count < utflen) {
      ch = byteBuffer[count] & 0xff;
      switch (ch >> 4) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
          /* 0xxxxxxx */
          count++;
          charBuffer[charCount++] = (char) ch;
          break;
        case 12:
        case 13:
          /* 110x xxxx 10xx xxxx */
          count += 2;
          if (count > utflen) {
            throw new UTFDataFormatException(PARTIAL_CHAR_ERROR);
          }
          ch2 = byteBuffer[count - 1];
          if ((ch2 & 0xC0) != 0x80) {
            throw new UTFDataFormatException(MALFORMED_UTF_ERROR + count);
          }
          charBuffer[charCount++] = (char) (((ch & 0x1F) << 6)
                                             | (ch2 & 0x3F));
          break;
        case 14:
          /* 1110 xxxx 10xx xxxx 10xx xxxx */
          count += 3;
          if (count > utflen) {
            throw new UTFDataFormatException(PARTIAL_CHAR_ERROR);
          }
          ch2 = byteBuffer[count - 2];
          ch3 = byteBuffer[count - 1];
          if (((ch2 & 0xC0) != 0x80) || ((ch3 & 0xC0) != 0x80)) {
            throw new UTFDataFormatException(MALFORMED_UTF_ERROR + (count - 1));
          }
          charBuffer[charCount++] = (char) (((ch & 0x0F) << 12)
                                            | ((ch2 & 0x3F) << 6)
                                            | ((ch3 & 0x3F) << 0));
          break;
        default:
          /* 10xx xxxx, 1111 xxxx */
          throw new UTFDataFormatException(MALFORMED_UTF_ERROR + count);
      }
    }
    // the number of chars produced may be less than utflen
    return new String(charBuffer, 0, charCount);
  }

  /**
   * Reads a <code>Date</code> value from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>Date</code> value to be read could be a
   *          null value; otherwise, if the <code>Date</code> value read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @return a <code>Date</code> value read from the input. Note that it could
   *         be null if the value read from the input is a null and the argument
   *         <code>allowNull<code> is true.
   * @throws EOFException
   *           if the input reaches the end before reading the whole <code>Date
   *           </code> value.
   * @throws InvalidFormatException
   *           if the value read from the input is a null value and the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Date readDate(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final long time = readLong(in);
    return new Date(time);
  }

  /**
   * Reads a <code>BigInteger</code> value from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>BigInteger</code> value to be read could
   *          be a null value; otherwise, if the <code>BigInteger</code> value
   *          read from the input is null, an
   *          <code>InvalidFormatException</code> will be thrown.
   * @return a <code>BigInteger</code> value read from the input. Note that it
   *         could be null if the value read from the input is a null and the
   *         argument <code>allowNull<code> is true.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>BigInteger</code> value.
   * @throws InvalidFormatException
   *           if the value read from the input is a null value and the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static BigInteger readBigInteger(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return BigInteger.ZERO;
    } else {
      final byte[] bits = new byte[n];
      readFully(in, bits, 0, n);
      return new BigInteger(bits);
    }
  }

  /**
   * Reads a <code>BigDecimal</code> value from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>BigDecimal</code> value to be read could
   *          be a null value; otherwise, if the <code>BigDecimal</code> value
   *          read from the input is null, an
   *          <code>InvalidFormatException</code> will be thrown.
   * @return a <code>BigDecimal</code> value read from the input. Note that it
   *         could be null if the value read from the input is a null and the
   *         argument <code>allowNull<code> is true.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>BigDecimal</code> value.
   * @throws InvalidFormatException
   *           if the value read from the input is a null value and the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static BigDecimal readBigDecimal(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return BigDecimal.ZERO;
    } else {
      final byte[] bits = new byte[n];
      readFully(in, bits, 0, n);
      final BigInteger unscaledValue = new BigInteger(bits);
      final int scale = readInt(in);
      return new BigDecimal(unscaledValue, scale);
    }
  }

  /**
   * Reads a <code>Class</code> value from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>Class</code> value to be read could be a
   *          null value; otherwise, if the <code>Class</code> value read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @return a <code>Class</code> value read from the input. Note that it could
   *         be null if the value read from the input is a null and the argument
   *         <code>allowNull<code> is true.
   * @throws EOFException
   *           if the input reaches the end before reading the whole <code>Class
   *           </code> value.
   * @throws InvalidFormatException
   *           if the value read from the input is a null value and the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Class<?> readClass(final InputStream in, final boolean allowNull)
      throws IOException {
    final String className = readString(in, true);
    if (className == null) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    Class<?> result;
    try {
      result = Class.forName(className);
    } catch (final ClassNotFoundException e) {
      throw new InvalidFormatException(e);
    }
    return result;
  }

  /**
   * Reads a <code>Enum</code> value from the input.
   *
   * @param enumClass
   *          the class object of the <code>Enum</code> type.
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>Enum</code> value to be read could be a
   *          null value; otherwise, if the <code>Enum</code> value read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @return a <code>Enum</code> value read from the input. Note that it could
   *         be null if the value read from the input is a null and the argument
   *         <code>allowNull<code> is true.
   * @throws EOFException
   *           if the input reaches the end before reading the whole <code>Enum
   *           </code> value.
   * @throws InvalidFormatException
   *           if the value read from the input is a null value and the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static <T extends Enum<T>> T readEnum(final Class<T> enumClass,
      final InputStream in, final boolean allowNull) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int ordinal = readVarInt(in);
    final T[] enumValues = enumClass.getEnumConstants();
    if ((ordinal >= 0) && (ordinal < enumValues.length)) {
      return enumValues[ordinal];
    } else {
      throw new InvalidFormatException(INVALID_ENUM_ORDINAL + ordinal);
    }
  }

  /**
   * Reads a <code>boolean</code> array from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>boolean</code> array to be read could be
   *          a null value; otherwise, if the <code>boolean</code> array read
   *          from the input is null, an <code>InvalidFormatException</code>
   *          will be thrown.
   * @param result
   *          a <code>boolean</code> array used to store the result. It could be
   *          null.
   * @return a <code>boolean</code> array read from the input. If the length of
   *         the result array is of the same as the length of the argument
   *         <code>result</code>, the returned values are stored in the argument
   *         <code>result</code>; otherwise, a new <code>boolean</code> array is
   *         created to store the returned values. Note that the returned array
   *         may be null if <code>allowNull</code> is true and the array
   *         read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>boolean</code> array.
   * @throws InvalidFormatException
   *           if the array read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static boolean[] readBooleanArray(final InputStream in,
      final boolean allowNull, @Nullable boolean[] result)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
    } else {
      if ((result == null) || (result.length != n)) {
        result = new boolean[n];
      }
      for (int i = 0; i < n; ++i) {
        result[i] = readBoolean(in);
      }
      return result;
    }
  }

  /**
   * Reads a <code>boolean</code> list from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>boolean</code> list to be read could be a
   *          null value; otherwise, if the <code>boolean</code> list read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>boolean</code> list used to store the result. It could be
   *          null.
   * @return a <code>boolean</code> list read from the input. If the argument
   *         <code>result</code> is not null, and the list to be read is not a
   *         null value, the argument <code>result</code> is cleared and the
   *         returned values are stored in it; otherwise, a new array list of
   *         <code>boolean</code> is created to store the returned values. Note
   *         that the returned list may be null if <code>allowNull</code>
   *         is true and the list read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>boolean</code> list.
   * @throws InvalidFormatException
   *           if the list read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static BooleanList readBooleanList(final InputStream in,
      final boolean allowNull, @Nullable BooleanList result)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new ArrayBooleanList();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new ArrayBooleanList(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final boolean value = readBoolean(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a <code>boolean</code> set from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>boolean</code> set to be read could be a
   *          null value; otherwise, if the <code>boolean</code> set read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>boolean</code> set used to store the result. It could be
   *          null.
   * @return a <code>boolean</code> set read from the input. If the argument
   *         <code>result</code> is not null, and the set to be read is not a
   *         null value, the argument <code>result</code> is cleared and the
   *         returned values are stored in it; otherwise, a new hash set of
   *         <code>boolean</code> is created to store the returned values. Note
   *         that the returned set may be null if <code>allowNull</code> is
   *         true and the set read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>boolean</code> set.
   * @throws InvalidFormatException
   *           if the set read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Set<Boolean> readBooleanSet(final InputStream in,
      final boolean allowNull, @Nullable Set<Boolean> result)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new HashSet<Boolean>();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new HashSet<Boolean>(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final boolean value = readBoolean(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a <code>char</code> array from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>char</code> array to be read could be a
   *          null value; otherwise, if the <code>char</code> array read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>char</code> array used to store the result. It could be
   *          null.
   * @return a <code>char</code> array read from the input. If the length of the
   *         result array is of the same as the length of the argument
   *         <code>result</code>, the returned values are stored in the argument
   *         <code>result</code>; otherwise, a new <code>char</code> array is
   *         created to store the returned values. Note that the returned array
   *         may be null if <code>allowNull</code> is true and the array
   *         read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>char</code> array.
   * @throws InvalidFormatException
   *           if the array read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static char[] readCharArray(final InputStream in, final boolean allowNull,
      @Nullable char[] result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_CHAR_ARRAY;
    } else {
      if ((result == null) || (result.length != n)) {
        result = new char[n];
      }
      for (int i = 0; i < n; ++i) {
        result[i] = readChar(in);
      }
      return result;
    }
  }

  /**
   * Reads a <code>char</code> list from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>char</code> list to be read could be a
   *          null value; otherwise, if the <code>char</code> list read from the
   *          input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>char</code> list used to store the result. It could be
   *          null.
   * @return a <code>char</code> list read from the input. If the argument
   *         <code>result</code> is not null, and the list to be read is not a
   *         null value, the argument <code>result</code> is cleared and the
   *         returned values are stored in it; otherwise, a new array list of
   *         <code>char</code> is created to store the returned values. Note
   *         that the returned list may be null if <code>allowNull</code>
   *         is true and the list read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>char</code> list.
   * @throws InvalidFormatException
   *           if the list read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static CharList readCharList(final InputStream in,
      final boolean allowNull, @Nullable CharList result)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new ArrayCharList();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new ArrayCharList(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final char value = readChar(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a <code>char</code> set from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>char</code> set to be read could be a
   *          null value; otherwise, if the <code>char</code> set read from the
   *          input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>char</code> set used to store the result. It could be
   *          null.
   * @return a <code>char</code> set read from the input. If the argument
   *         <code>result</code> is not null, and the set to be read is not a
   *         null value, the argument <code>result</code> is cleared and the
   *         returned values are stored in it; otherwise, a new hash set of
   *         <code>char</code> is created to store the returned values. Note
   *         that the returned set may be null if <code>allowNull</code> is
   *         true and the set read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>char</code> set.
   * @throws InvalidFormatException
   *           if the set read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Set<Character> readCharSet(final InputStream in,
      final boolean allowNull, @Nullable Set<Character> result)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new HashSet<Character>();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new HashSet<Character>(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final char value = readChar(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a <code>byte</code> array from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>byte</code> array to be read could be a
   *          null value; otherwise, if the <code>byte</code> array read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>byte</code> array used to store the result. It could be
   *          null.
   * @return a <code>byte</code> array read from the input. If the length of the
   *         result array is of the same as the length of the argument
   *         <code>result</code>, the returned values are stored in the argument
   *         <code>result</code>; otherwise, a new <code>byte</code> array is
   *         created to store the returned values. Note that the returned array
   *         may be null if <code>allowNull</code> is true and the array
   *         read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>byte</code> array.
   * @throws InvalidFormatException
   *           if the array read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static byte[] readByteArray(final InputStream in, final boolean allowNull,
      @Nullable byte[] result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    } else {
      if ((result == null) || (result.length != n)) {
        result = new byte[n];
      }
      readFully(in, result, 0, n);
      return result;
    }
  }

  /**
   * Reads a <code>byte</code> list from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>byte</code> list to be read could be a
   *          null value; otherwise, if the <code>byte</code> list read from the
   *          input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>byte</code> list used to store the result. It could be
   *          null.
   * @return a <code>byte</code> list read from the input. If the argument
   *         <code>result</code> is not null, and the list to be read is not a
   *         null value, the argument <code>result</code> is cleared and the
   *         returned values are stored in it; otherwise, a new array list of
   *         <code>byte</code> is created to store the returned values. Note
   *         that the returned list may be null if <code>allowNull</code>
   *         is true and the list read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>byte</code> list.
   * @throws InvalidFormatException
   *           if the list read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static ByteList readByteList(final InputStream in, final boolean allowNull,
      @Nullable ByteList result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new ArrayByteList();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new ArrayByteList(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final byte value = readByte(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a <code>byte</code> set from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>byte</code> set to be read could be a
   *          null value; otherwise, if the <code>byte</code> set read from the
   *          input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>byte</code> set used to store the result. It could be
   *          null.
   * @return a <code>byte</code> set read from the input. If the argument
   *         <code>result</code> is not null, and the set to be read is not a
   *         null value, the argument <code>result</code> is cleared and the
   *         returned values are stored in it; otherwise, a new hash set of
   *         <code>byte</code> is created to store the returned values. Note
   *         that the returned set may be null if <code>allowNull</code> is
   *         true and the set read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>byte</code> set.
   * @throws InvalidFormatException
   *           if the set read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Set<Byte> readByteSet(final InputStream in, final boolean allowNull,
      @Nullable Set<Byte> result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new HashSet<Byte>();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new HashSet<Byte>(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final byte value = readByte(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a <code>short</code> array from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>short</code> array to be read could be a
   *          null value; otherwise, if the <code>short</code> array read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>short</code> array used to store the result. It could be
   *          null.
   * @return a <code>short</code> array read from the input. If the length of
   *         the result array is of the same as the length of the argument
   *         <code>result</code>, the returned values are stored in the argument
   *         <code>result</code>; otherwise, a new <code>short</code> array is
   *         created to store the returned values. Note that the returned array
   *         may be null if <code>allowNull</code> is true and the array
   *         read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>short</code> array.
   * @throws InvalidFormatException
   *           if the array read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static short[] readShortArray(final InputStream in, final boolean allowNull,
      @Nullable short[] result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_SHORT_ARRAY;
    } else {
      if ((result == null) || (result.length != n)) {
        result = new short[n];
      }
      for (int i = 0; i < n; ++i) {
        result[i] = readShort(in);
      }
      return result;
    }
  }

  /**
   * Reads a <code>short</code> list from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>short</code> list to be read could be a
   *          null value; otherwise, if the <code>short</code> list read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>short</code> list used to store the result. It could be
   *          null.
   * @return a <code>short</code> list read from the input. If the argument
   *         <code>result</code> is not null, and the list to be read is not a
   *         null value, the argument <code>result</code> is cleared and the
   *         returned values are stored in it; otherwise, a new array list of
   *         <code>short</code> is created to store the returned values. Note
   *         that the returned list may be null if <code>allowNull</code>
   *         is true and the list read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>short</code> list.
   * @throws InvalidFormatException
   *           if the list read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static ShortList readShortList(final InputStream in, final boolean allowNull,
      @Nullable ShortList result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new ArrayShortList();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new ArrayShortList(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final short value = readShort(in);
        result.add(value);
      }
      return result;
    }
  }


  /**
   * Reads a <code>short</code> set from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>short</code> set to be read could be a
   *          null value; otherwise, if the <code>short</code> set read from the
   *          input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>short</code> set used to store the result. It could be
   *          null.
   * @return a <code>short</code> set read from the input. If the argument
   *         <code>result</code> is not null, and the set to be read is not a
   *         null value, the argument <code>result</code> is cleared and the
   *         returned values are stored in it; otherwise, a new hash set of
   *         <code>short</code> is created to store the returned values. Note
   *         that the returned set may be null if <code>allowNull</code> is
   *         true and the set read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>short</code> set.
   * @throws InvalidFormatException
   *           if the set read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Set<Short> readShortSet(final InputStream in, final boolean allowNull,
      @Nullable Set<Short> result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new HashSet<Short>();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new HashSet<Short>(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final short value = readShort(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a variable length encoded <code>short</code> array from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>short</code> array to be read could be a
   *          null value; otherwise, if the <code>short</code> array read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>short</code> array used to store the result. It could be
   *          null.
   * @return a variable length encoded <code>short</code> array read from the
   *         input. If the length of the result array is of the same as the
   *         length of the argument <code>result</code>, the returned values are
   *         stored in the argument <code>result</code>; otherwise, a new
   *         <code>short</code> array is created to store the returned values.
   *         Note that the returned array may be null if
   *         <code>allowNull</code> is true and the array read from the
   *         input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>short</code> array.
   * @throws InvalidFormatException
   *           if the array read from the input is null, while the argument
   *           <code>allowNull</code> is false; or a variable length
   *           encoded <code>short</code> value has invalid format.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static short[] readVarShortArray(final InputStream in, final boolean allowNull,
      @Nullable short[] result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_SHORT_ARRAY;
    } else {
      if ((result == null) || (result.length != n)) {
        result = new short[n];
      }
      for (int i = 0; i < n; ++i) {
        result[i] = readVarShort(in);
      }
      return result;
    }
  }

  /**
   * Reads a variable length encoded <code>short</code> list from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>short</code> list to be read could be a
   *          null value; otherwise, if the <code>short</code> list read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>short</code> list used to store the result. It could be
   *          null.
   * @return a variable length encoded <code>short</code> list read from the
   *         input. If the argument <code>result</code> is not null, and the
   *         list to be read is not a null value, the argument
   *         <code>result</code> is cleared and the returned values are stored
   *         in it; otherwise, a new array list of <code>short</code> is created
   *         to store the returned values. Note that the returned list may be
   *         null if <code>allowNull</code> is true and the list read from
   *         the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>short</code> list.
   * @throws InvalidFormatException
   *           if the list read from the input is null, while the argument
   *           <code>allowNull</code> is false; or a variable length
   *           encoded <code>short</code> value has invalid format.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static ShortList readVarShortList(final InputStream in, final boolean allowNull,
      @Nullable ShortList result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new ArrayShortList();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new ArrayShortList(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final short value = readVarShort(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a variable length encoded <code>short</code> set from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>short</code> set to be read could be a
   *          null value; otherwise, if the <code>short</code> set read from the
   *          input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>short</code> set used to store the result. It could be
   *          null.
   * @return a variable length encoded <code>short</code> set read from the
   *         input. If the argument <code>result</code> is not null, and the set
   *         to be read is not a null value, the argument <code>result</code> is
   *         cleared and the returned values are stored in it; otherwise, a new
   *         hash set of <code>short</code> is created to store the returned
   *         values. Note that the returned set may be null if
   *         <code>allowNull</code> is true and the set read from the input
   *         is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>short</code> set.
   * @throws InvalidFormatException
   *           if the set read from the input is null, while the argument
   *           <code>allowNull</code> is false; or a variable length encoded
   *           <code>short</code> value has invalid format.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Set<Short> readVarShortSet(final InputStream in, final boolean allowNull,
      @Nullable Set<Short> result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new HashSet<Short>();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new HashSet<Short>(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final short value = readVarShort(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a <code>int</code> array from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>int</code> array to be read could be a
   *          null value; otherwise, if the <code>int</code> array read from the
   *          input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>int</code> array used to store the result. It could be
   *          null.
   * @return a <code>int</code> array read from the input. If the length of the
   *         result array is of the same as the length of the argument
   *         <code>result</code>, the returned values are stored in the argument
   *         <code>result</code>; otherwise, a new <code>int</code> array is
   *         created to store the returned values. Note that the returned array
   *         may be null if <code>allowNull</code> is true and the array
   *         read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>int</code> array.
   * @throws InvalidFormatException
   *           if the array read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static int[] readIntArray(final InputStream in, final boolean allowNull,
      @Nullable int[] result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_INT_ARRAY;
    } else {
      if ((result == null) || (result.length != n)) {
        result = new int[n];
      }
      for (int i = 0; i < n; ++i) {
        result[i] = readInt(in);
      }
      return result;
    }
  }

  /**
   * Reads a <code>int</code> list from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>int</code> list to be read could be a
   *          null value; otherwise, if the <code>int</code> list read from the
   *          input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>int</code> list used to store the result. It could be
   *          null.
   * @return a <code>int</code> list read from the input. If the argument
   *         <code>result</code> is not null, and the list to be read is not a
   *         null value, the argument <code>result</code> is cleared and the
   *         returned values are stored in it; otherwise, a new array list of
   *         <code>int</code> is created to store the returned values. Note that
   *         the returned list may be null if <code>allowNull</code> is
   *         true and the list read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>int</code> list.
   * @throws InvalidFormatException
   *           if the list read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static IntList readIntList(final InputStream in, final boolean allowNull,
      @Nullable IntList result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new ArrayIntList();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new ArrayIntList(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final int value = readInt(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a <code>int</code> set from the input.
   *
   * @param allowNull
   *          if it is true, the <code>int</code> set to be read could be a null
   *          value; otherwise, if the <code>int</code> set read from the input
   *          is null, an <code>InvalidFormatException</code> will be thrown.
   * @param result
   *          a <code>int</code> set used to store the result. It could be null.
   * @return a <code>int</code> set read from the input. If the argument
   *         <code>result</code> is not null, and the set to be read is not a
   *         null value, the argument <code>result</code> is cleared and the
   *         returned values are stored in it; otherwise, a new hash set of
   *         <code>int</code> is created to store the returned values. Note that
   *         the returned set may be null if <code>allowNull</code> is true
   *         and the set read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>int</code> set.
   * @throws InvalidFormatException
   *           if the set read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Set<Integer> readIntSet(final InputStream in, final boolean allowNull,
      @Nullable Set<Integer> result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new HashSet<Integer>();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new HashSet<Integer>(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final int value = readInt(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a variable length encoded <code>int</code> array from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>int</code> array to be read could be a
   *          null value; otherwise, if the <code>int</code> array read from the
   *          input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>int</code> array used to store the result. It could be
   *          null.
   * @return a variable length encoded <code>int</code> array read from the
   *         input. If the length of the result array is of the same as the
   *         length of the argument <code>result</code>, the returned values are
   *         stored in the argument <code>result</code>; otherwise, a new
   *         <code>int</code> array is created to store the returned values.
   *         Note that the returned array may be null if
   *         <code>allowNull</code> is true and the array read from the
   *         input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>int</code> array.
   * @throws InvalidFormatException
   *           if the array read from the input is null, while the argument
   *           <code>allowNull</code> is false; or a variable length
   *           encoded <code>short</code> value has invalid format.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static int[] readVarIntArray(final InputStream in, final boolean allowNull,
      @Nullable int[] result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_INT_ARRAY;
    } else {
      if ((result == null) || (result.length != n)) {
        result = new int[n];
      }
      for (int i = 0; i < n; ++i) {
        result[i] = readVarInt(in);
      }
      return result;
    }
  }

  /**
   * Reads a variable length encoded <code>int</code> list from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>int</code> list to be read could be a
   *          null value; otherwise, if the <code>int</code> list read from the
   *          input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>int</code> list used to store the result. It could be
   *          null.
   * @return a variable length encoded <code>int</code> list read from the
   *         input. If the argument <code>result</code> is not null, and the
   *         list to be read is not a null value, the argument
   *         <code>result</code> is cleared and the returned values are stored
   *         in it; otherwise, a new array list of <code>int</code> is created
   *         to store the returned values. Note that the returned list may be
   *         null if <code>allowNull</code> is true and the list read from
   *         the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>int</code> list.
   * @throws InvalidFormatException
   *           if the list read from the input is null, while the argument
   *           <code>allowNull</code> is false; or a variable length
   *           encoded <code>short</code> value has invalid format.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static IntList readVarIntList(final InputStream in, final boolean allowNull,
      @Nullable IntList result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new ArrayIntList();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new ArrayIntList(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final int value = readVarInt(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a variable length encoded <code>int</code> set from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>int</code> set to be read could be a null
   *          value; otherwise, if the <code>int</code> set read from the input
   *          is null, an <code>InvalidFormatException</code> will be thrown.
   * @param result
   *          a <code>int</code> set used to store the result. It could be null.
   * @return a variable length encoded <code>int</code> set read from the input.
   *         If the argument <code>result</code> is not null, and the set to be
   *         read is not a null value, the argument <code>result</code> is
   *         cleared and the returned values are stored in it; otherwise, a new
   *         hash set of <code>int</code> is created to store the returned
   *         values. Note that the returned set may be null if
   *         <code>allowNull</code> is true and the set read from the input
   *         is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>int</code> set.
   * @throws InvalidFormatException
   *           if the set read from the input is null, while the argument
   *           <code>allowNull</code> is false; or a variable length encoded
   *           <code>short</code> value has invalid format.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Set<Integer> readVarIntSet(final InputStream in, final boolean allowNull,
      @Nullable Set<Integer> result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new HashSet<Integer>();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new HashSet<Integer>(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final int value = readVarInt(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a <code>char</code> array from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>long</code> array to be read could be a
   *          null value; otherwise, if the <code>long</code> array read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>long</code> array used to store the result. It could be
   *          null.
   * @return a <code>long</code> array read from the input. If the length of the
   *         result array is of the same as the length of the argument
   *         <code>result</code>, the returned values are stored in the argument
   *         <code>result</code>; otherwise, a new <code>long</code> array is
   *         created to store the returned values. Note that the returned array
   *         may be null if <code>allowNull</code> is true and the array
   *         read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>long</code> array.
   * @throws InvalidFormatException
   *           if the array read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static long[] readLongArray(final InputStream in, final boolean allowNull,
      @Nullable long[] result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_LONG_ARRAY;
    } else {
      if ((result == null) || (result.length != n)) {
        result = new long[n];
      }
      for (int i = 0; i < n; ++i) {
        result[i] = readLong(in);
      }
      return result;
    }
  }

  /**
   * Reads a <code>long</code> list from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>long</code> list to be read could be a
   *          null value; otherwise, if the <code>long</code> list read from the
   *          input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>long</code> list used to store the result. It could be
   *          null.
   * @return a <code>long</code> list read from the input. If the argument
   *         <code>result</code> is not null, and the list to be read is not a
   *         null value, the argument <code>result</code> is cleared and the
   *         returned values are stored in it; otherwise, a new array list of
   *         <code>long</code> is created to store the returned values. Note
   *         that the returned list may be null if <code>allowNull</code>
   *         is true and the list read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>long</code> list.
   * @throws InvalidFormatException
   *           if the list read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static LongList readLongList(final InputStream in, final boolean allowNull,
      @Nullable LongList result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new ArrayLongList();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new ArrayLongList(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final long value = readLong(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a <code>long</code> set from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>long</code> set to be read could be a
   *          null value; otherwise, if the <code>long</code> set read from the
   *          input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>long</code> set used to store the result. It could be
   *          null.
   * @return a <code>long</code> set read from the input. If the argument
   *         <code>result</code> is not null, and the set to be read is not a
   *         null value, the argument <code>result</code> is cleared and the
   *         returned values are stored in it; otherwise, a new hash set of
   *         <code>long</code> is created to store the returned values. Note
   *         that the returned set may be null if <code>allowNull</code> is
   *         true and the set read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>long</code> set.
   * @throws InvalidFormatException
   *           if the set read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Set<Long> readLongSet(final InputStream in, final boolean allowNull,
      @Nullable Set<Long> result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new HashSet<Long>();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new HashSet<Long>(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final long value = readLong(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a variable length encoded <code>char</code> array from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>long</code> array to be read could be a
   *          null value; otherwise, if the <code>long</code> array read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>long</code> array used to store the result. It could be
   *          null.
   * @return a variable length encoded <code>long</code> array read from the
   *         input. If the length of the result array is of the same as the
   *         length of the argument <code>result</code>, the returned values are
   *         stored in the argument <code>result</code>; otherwise, a new
   *         <code>long</code> array is created to store the returned values.
   *         Note that the returned array may be null if
   *         <code>allowNull</code> is true and the array read from the
   *         input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>long</code> array.
   * @throws InvalidFormatException
   *           if the array read from the input is null, while the argument
   *           <code>allowNull</code> is false; or a variable length
   *           encoded <code>short</code> value has invalid format.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static long[] readVarLongArray(final InputStream in, final boolean allowNull,
      @Nullable long[] result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_LONG_ARRAY;
    } else {
      if ((result == null) || (result.length != n)) {
        result = new long[n];
      }
      for (int i = 0; i < n; ++i) {
        result[i] = readVarLong(in);
      }
      return result;
    }
  }

  /**
   * Reads a variable length encoded <code>long</code> list from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>long</code> list to be read could be a
   *          null value; otherwise, if the <code>long</code> list read from the
   *          input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>long</code> list used to store the result. It could be
   *          null.
   * @return a variable length encoded <code>long</code> list read from the
   *         input. If the argument <code>result</code> is not null, and the
   *         list to be read is not a null value, the argument
   *         <code>result</code> is cleared and the returned values are stored
   *         in it; otherwise, a new array list of <code>long</code> is created
   *         to store the returned values. Note that the returned list may be
   *         null if <code>allowNull</code> is true and the list read from
   *         the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>long</code> list.
   * @throws InvalidFormatException
   *           if the list read from the input is null, while the argument
   *           <code>allowNull</code> is false; or a variable length
   *           encoded <code>short</code> value has invalid format.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static LongList readVarLongList(final InputStream in, final boolean allowNull,
      @Nullable LongList result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new ArrayLongList();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new ArrayLongList(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final long value = readVarLong(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a variable length encoded <code>long</code> set from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>long</code> set to be read could be a
   *          null value; otherwise, if the <code>long</code> set read from the
   *          input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>long</code> set used to store the result. It could be
   *          null.
   * @return a variable length encoded <code>long</code> set read from the
   *         input. If the argument <code>result</code> is not null, and the set
   *         to be read is not a null value, the argument <code>result</code> is
   *         cleared and the returned values are stored in it; otherwise, a new
   *         hash set of <code>long</code> is created to store the returned
   *         values. Note that the returned set may be null if
   *         <code>allowNull</code> is true and the set read from the input
   *         is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>long</code> set.
   * @throws InvalidFormatException
   *           if the set read from the input is null, while the argument
   *           <code>allowNull</code> is false; or a variable length encoded
   *           <code>short</code> value has invalid format.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Set<Long> readVarLongSet(final InputStream in, final boolean allowNull,
      @Nullable Set<Long> result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new HashSet<Long>();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new HashSet<Long>(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final long value = readVarLong(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a <code>float</code> array from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>float</code> array to be read could be a
   *          null value; otherwise, if the <code>float</code> array read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>float</code> array used to store the result. It could be
   *          null.
   * @return a <code>float</code> array read from the input. If the length of
   *         the result array is of the same as the length of the argument
   *         <code>result</code>, the returned values are stored in the argument
   *         <code>result</code>; otherwise, a new <code>float</code> array is
   *         created to store the returned values. Note that the returned array
   *         may be null if <code>allowNull</code> is true and the array
   *         read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>float</code> array.
   * @throws InvalidFormatException
   *           if the array read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static float[] readFloatArray(final InputStream in, final boolean allowNull,
      @Nullable float[] result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_FLOAT_ARRAY;
    } else {
      if ((result == null) || (result.length != n)) {
        result = new float[n];
      }
      for (int i = 0; i < n; ++i) {
        result[i] = readFloat(in);
      }
      return result;
    }
  }

  /**
   * Reads a <code>float</code> list from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>float</code> list to be read could be a
   *          null value; otherwise, if the <code>float</code> list read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>float</code> list used to store the result. It could be
   *          null.
   * @return a <code>float</code> list read from the input. If the argument
   *         <code>result</code> is not null, and the list to be read is not a
   *         null value, the argument <code>result</code> is cleared and the
   *         returned values are stored in it; otherwise, a new array list of
   *         <code>float</code> is created to store the returned values. Note
   *         that the returned list may be null if <code>allowNull</code>
   *         is true and the list read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>float</code> list.
   * @throws InvalidFormatException
   *           if the list read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static FloatList readFloatList(final InputStream in, final boolean allowNull,
      @Nullable FloatList result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new ArrayFloatList();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new ArrayFloatList(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final float value = readFloat(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a <code>float</code> set from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>float</code> set to be read could be a
   *          null value; otherwise, if the <code>float</code> set read from the
   *          input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>float</code> set used to store the result. It could be
   *          null.
   * @return a <code>float</code> set read from the input. If the argument
   *         <code>result</code> is not null, and the set to be read is not a
   *         null value, the argument <code>result</code> is cleared and the
   *         returned values are stored in it; otherwise, a new hash set of
   *         <code>float</code> is created to store the returned values. Note
   *         that the returned set may be null if <code>allowNull</code> is
   *         true and the set read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>float</code> set.
   * @throws InvalidFormatException
   *           if the set read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Set<Float> readFloatSet(final InputStream in, final boolean allowNull,
      @Nullable Set<Float> result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new HashSet<Float>();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new HashSet<Float>(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final float value = readFloat(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a <code>double</code> array from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>double</code> array to be read could be a
   *          null value; otherwise, if the <code>double</code> array read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>double</code> array used to store the result. It could be
   *          null.
   * @return a <code>double</code> array read from the input. If the length of
   *         the result array is of the same as the length of the argument
   *         <code>result</code>, the returned values are stored in the argument
   *         <code>result</code>; otherwise, a new <code>double</code> array is
   *         created to store the returned values. Note that the returned array
   *         may be null if <code>allowNull</code> is true and the array
   *         read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>double</code> array.
   * @throws InvalidFormatException
   *           if the array read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static double[] readDoubleArray(final InputStream in, final boolean allowNull,
      @Nullable double[] result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_DOUBLE_ARRAY;
    } else {
      if ((result == null) || (result.length != n)) {
        result = new double[n];
      }
      for (int i = 0; i < n; ++i) {
        result[i] = readDouble(in);
      }
      return result;
    }
  }

  /**
   * Reads a <code>double</code> list from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>double</code> list to be read could be a
   *          null value; otherwise, if the <code>double</code> list read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>double</code> list used to store the result. It could be
   *          null.
   * @return a <code>double</code> list read from the input. If the argument
   *         <code>result</code> is not null, and the list to be read is not a
   *         null value, the argument <code>result</code> is cleared and the
   *         returned values are stored in it; otherwise, a new array list of
   *         <code>double</code> is created to store the returned values. Note
   *         that the returned list may be null if <code>allowNull</code>
   *         is true and the list read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>double</code> list.
   * @throws InvalidFormatException
   *           if the list read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static DoubleList readDoubleList(final InputStream in, final boolean allowNull,
      @Nullable DoubleList result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new ArrayDoubleList();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new ArrayDoubleList(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final double value = readDouble(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a <code>double</code> set from the input.
   *
   * @param in
   *          the input source where to read the data.
   * @param allowNull
   *          if it is true, the <code>double</code> set to be read could be a
   *          null value; otherwise, if the <code>double</code> set read from
   *          the input is null, an <code>InvalidFormatException</code> will be
   *          thrown.
   * @param result
   *          a <code>double</code> set used to store the result. It could be
   *          null.
   * @return a <code>double</code> set read from the input. If the argument
   *         <code>result</code> is not null, and the set to be read is not a
   *         null value, the argument <code>result</code> is cleared and the
   *         returned values are stored in it; otherwise, a new hash set of
   *         <code>double</code> is created to store the returned values. Note
   *         that the returned set may be null if <code>allowNull</code> is
   *         true and the set read from the input is a null value.
   * @throws EOFException
   *           if the input reaches the end before reading the whole
   *           <code>double</code> set.
   * @throws InvalidFormatException
   *           if the set read from the input is null, while the argument
   *           <code>allowNull</code> is false.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static Set<Double> readDoubleSet(final InputStream in, final boolean allowNull,
      @Nullable Set<Double> result) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new HashSet<Double>();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new HashSet<Double>(n);
      } else {
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final double value = readDouble(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads an object from the input.
   *
   * @param <T>
   *          The type of the class. The binary serializer of the class
   *          <code>T</code> must have already been registered.
   * @param obj
   *          The object to serialize.
   * @param in
   *          A binary input stream. Note that this function does NOT close the
   *          input stream.
   * @param allowNull
   *          Indicates whether to allowed the serialized object to be null.
   * @return The deserialized object.
   * @throws IOException
   *           If any I/O error occurred.
   */
  @SuppressWarnings("unchecked")
  public static <T> T readObject(final Class<T> objClass, final InputStream in,
      final boolean allowNull) throws IOException {
    final BinarySerializer serializer = BinarySerialization.getSerializer(objClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(objClass);
    }
    try {
      return (T) serializer.deserialize(in, allowNull);
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
  }

  /**
   * Reads an array of objects from the input stream.
   *
   * @param T
   *          the type of the elements of the array to be read. The binary
   *          serializer of the class <code>T</code> must have already been
   *          registered.
   * @param valueClass
   *          the class of the elements of the array to be read.
   * @param in
   *          a binary input stream.
   * @param allowNullArray
   *          if it is true, the array to be read could be null; otherwise, if
   *          the array read from the input is null, an
   *          <code>InvalidFormatException</code> will be thrown.
   * @param allowNullValue
   *          if it is true, the elements in the array to be read could be null;
   *          otherwise, if any element of the array read from the input is
   *          null, an <code>InvalidFormatException</code> will be thrown.
   * @return an array of objects read from the input. The returned array may be
   *         null if <code>allowNullArray</code> is true, and the element in the
   *         returned array may be null if <code>allowNullValue</code> is true.
   * @throws EOFException
   *           if the input reaches the end before reading the whole array.
   * @throws InvalidFormatException
   *           if the array read from the input is null, while the argument
   *           <code>allowNullArray</code> is false; or any element in the array
   *           read from the input is null, while the argument
   *           <code>allowNullValue</code> is false.
   * @throws IOException
   *           if any I/O other error occurs.
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] readArray(final Class<T> valueClass, final InputStream in,
      final boolean allowNullArray, final boolean allowNullValue, @Nullable T[] result)
      throws IOException {
    final BinarySerializer serializer = BinarySerialization.getSerializer(valueClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    if (readNullMark(in)) {
      if (allowNullArray) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if ((result == null) || (result.length != 0)) {
        return (T[]) Array.newInstance(valueClass, 0);
      } else {
        return result;
      }
    } else {
      if ((result == null) || (result.length != n)) {
        result = (T[]) Array.newInstance(valueClass, n);
      }
      try {
        for (int i = 0; i < n; ++i) {
          result[i] = (T) serializer.deserialize(in, allowNullValue);
        }
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      return result;
    }
  }

  /**
   * Reads a list of objects from the input stream.
   *
   * @param T
   *          the type of the elements of the list to be read. The binary
   *          serializer of the class <code>T</code> must have already been
   *          registered.
   * @param valueClass
   *          the class of the elements of the list to be read.
   * @param in
   *          a binary input stream.
   * @param allowNullList
   *          if it is true, the list to be read could be null; otherwise, if
   *          the list read from the input is null, an
   *          <code>InvalidFormatException</code> will be thrown.
   * @param allowNullValue
   *          if it is true, the elements in the list to be read could be null;
   *          otherwise, if any element of the list read from the input is
   *          null, an <code>InvalidFormatException</code> will be thrown.
   * @param result
   *          a list used to store the result. It could be null.
   * @return a list of objects read from the input. The returned list may be
   *         null if <code>allowNullList</code> is true, and the element in the
   *         returned list may be null if <code>allowNullValue</code> is true.
   * @throws EOFException
   *           if the input reaches the end before reading the whole list.
   * @throws InvalidFormatException
   *           if the list read from the input is null, while the argument
   *           <code>allowNullList</code> is false; or any element in the list
   *           read from the input is null, while the argument
   *           <code>allowNullValue</code> is false.
   * @throws IOException
   *           if any I/O other error occurs.
   */
  public static <T> List<T> readList(final Class<T> valueClass,
      final InputStream in, final boolean allowNullList,
      final boolean allowNullValue, @Nullable List<T> result) throws IOException {
    final BinarySerializer serializer = BinarySerialization.getSerializer(valueClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    if (readNullMark(in)) {
      if (allowNullList) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new ArrayList<T>();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new ArrayList<T>(n);
      } else {
        result.clear();
      }
      try {
        for (int i = 0; i < n; ++i) {
          @SuppressWarnings("unchecked")
          final T value = (T) serializer.deserialize(in, allowNullValue);
          result.add(value);
        }
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      return result;
    }
  }

  /**
   * Reads a set of objects from the input stream.
   *
   * @param T
   *          the type of the elements of the set to be read. The binary
   *          serializer of the class <code>T</code> must have already been
   *          registered.
   * @param valueClass
   *          the class of the elements of the set to be read.
   * @param in
   *          a binary input stream.
   * @param allowNullSet
   *          if it is true, the set to be read could be null; otherwise, if
   *          the set read from the input is null, an
   *          <code>InvalidFormatException</code> will be thrown.
   * @param allowNullValue
   *          if it is true, the elements in the set to be read could be null;
   *          otherwise, if any element of the set read from the input is
   *          null, an <code>InvalidFormatException</code> will be thrown.
   * @param result
   *          a set used to store the result. It could be null.
   * @return a set of objects read from the input. The returned set may be
   *         null if <code>allowNullList</code> is true, and the element in the
   *         returned set may be null if <code>allowNullValue</code> is true.
   * @throws EOFException
   *           if the input reaches the end before reading the whole set.
   * @throws InvalidFormatException
   *           if the set read from the input is null, while the argument
   *           <code>allowNullSet</code> is false; or any element in the set
   *           read from the input is null, while the argument
   *           <code>allowNullValue</code> is false.
   * @throws IOException
   *           if any I/O other error occurs.
   */
  public static <T> Set<T> readSet(final Class<T> valueClass,
      final InputStream in, final boolean allowNullSet,
      final boolean allowNullValue, @Nullable Set<T> result) throws IOException {
    final BinarySerializer serializer = BinarySerialization.getSerializer(valueClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    if (readNullMark(in)) {
      if (allowNullSet) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result == null) {
        return new HashSet<T>();
      } else {
        result.clear();
        return result;
      }
    } else {
      if (result == null) {
        result = new HashSet<T>(n);
      } else {
        result.clear();
      }
      try {
        for (int i = 0; i < n; ++i) {
          @SuppressWarnings("unchecked")
          final T value = (T) serializer.deserialize(in, allowNullValue);
          result.add(value);
        }
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      return result;
    }
  }

  /**
   * Reads a map of objects from the input stream.
   *
   * @param K
   *          the type of the keys of the map to be read. The binary serializer
   *          of the class <code>K</code> must have already been registered.
   * @param V
   *          the type of the values of the map to be read. The binary
   *          serializer of the class <code>V</code> must have already been
   *          registered.
   * @param keyClass
   *          the class of the keys of the map to be read.
   * @param valueClass
   *          the class of the values of the map to be read.
   * @param in
   *          a binary input stream.
   * @param allowNullMap
   *          if it is true, the map to be read could be null; otherwise, if the
   *          map read from the input is null, an
   *          <code>InvalidFormatException</code> will be thrown.
   * @param allowNullKey
   *          if it is true, the key in the map to be read could be null;
   *          otherwise, if any key of the map read from the input is null, an
   *          <code>InvalidFormatException</code> will be thrown.
   * @param allowNullValue
   *          if it is true, the value in the map to be read could be null;
   *          otherwise, if any value of the map read from the input is null, an
   *          <code>InvalidFormatException</code> will be thrown.
   * @param result
   *          a map used to store the result. It could be null.
   * @return a map read from the input. The returned map may be null if
   *         <code>allowNullMap</code> is true; the key in the returned map may
   *         be null if <code>allowNullKey</code> is true; and the value in the
   *         returned map may be null if <code>allowNullValue</code> is true.
   * @throws EOFException
   *           if the input reaches the end before reading the whole map.
   * @throws InvalidFormatException
   *           if the map read from the input is null, while the argument
   *           <code>allowNullMap</code> is false; or any key in the map read
   *           from the input is null, while the argument
   *           <code>allowNullKey</code> is false; or any value in the map read
   *           from the input is null, while the argument
   *           <code>allowNullValue</code> is false.
   * @throws IOException
   *           if any I/O other error occurs.
   */
  public static <K, V> Map<K, V> readMap(final Class<K> keyClass,
      final Class<V> valueClass, final InputStream in,
      final boolean allowNullMap, final boolean allowNullKey,
      final boolean allowNullValue, @Nullable Map<K, V> result)
      throws IOException {
    final BinarySerializer keySerializer = BinarySerialization.getSerializer(keyClass);
    if (keySerializer == null) {
      throw new NoBinarySerializerRegisteredException(keyClass);
    }
    final BinarySerializer valueSerializer = BinarySerialization.getSerializer(valueClass);
    if (valueSerializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    if (readNullMark(in)) {
      if (allowNullMap) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result != null) {
        result.clear();
        return result;
      } else {
        return new HashMap<K, V>();
      }
    } else {
      if (result == null) {
        result = new HashMap<K, V>();
      } else {
        result.clear();
      }
      try {
        for (int i = 0; i < n; ++i) {
          @SuppressWarnings("unchecked")
          final K key = (K) keySerializer.deserialize(in, allowNullKey);
          @SuppressWarnings("unchecked")
          final V value = (V) valueSerializer.deserialize(in, allowNullValue);
          result.put(key, value);
        }
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      return result;
    }
  }

  /**
   * Reads a multimap of objects from the input stream.
   *
   * @param K
   *          the type of the keys of the multimap to be read. The binary
   *          serializer of the class <code>K</code> must have already been
   *          registered.
   * @param V
   *          the type of the values of the multimap to be read. The binary
   *          serializer of the class <code>V</code> must have already been
   *          registered.
   * @param keyClass
   *          the class of the keys of the multimap to be read.
   * @param valueClass
   *          the class of the values of the multimap to be read.
   * @param in
   *          a binary input stream.
   * @param allowNullMap
   *          if it is true, the multimap to be read could be null; otherwise,
   *          if the multimap read from the input is null, an
   *          <code>InvalidFormatException</code> will be thrown.
   * @param allowNullKey
   *          if it is true, the key in the multimap to be read could be null;
   *          otherwise, if any key of the multimap read from the input is null,
   *          an <code>InvalidFormatException</code> will be thrown.
   * @param allowNullValue
   *          if it is true, the value in the multimap to be read could be null;
   *          otherwise, if any value of the multimap read from the input is
   *          null, an <code>InvalidFormatException</code> will be thrown.
   * @param result
   *          a multimap used to store the result. It could be null.
   * @return a multimap read from the input. The returned multimap may be null
   *         if <code>allowNullMap</code> is true; the key in the returned
   *         multimap may be null if <code>allowNullKey</code> is true; and the
   *         value in the returned multimap may be null if
   *         <code>allowNullValue</code> is true.
   * @throws EOFException
   *           if the input reaches the end before reading the whole multimap.
   * @throws InvalidFormatException
   *           if the multimap read from the input is null, while the argument
   *           <code>allowNullMap</code> is false; or any key in the multimap
   *           read from the input is null, while the argument
   *           <code>allowNullKey</code> is false; or any value in the multimap
   *           read from the input is null, while the argument
   *           <code>allowNullValue</code> is false.
   * @throws IOException
   *           if any I/O other error occurs.
   */
  public static <K, V> Multimap<K, V> readMultimap(final Class<K> keyClass,
      final Class<V> valueClass, final InputStream in,
      final boolean allowNullMap, final boolean allowNullKey,
      final boolean allowNullValue, @Nullable Multimap<K, V> result)
      throws IOException {
    final BinarySerializer keySerializer = BinarySerialization.getSerializer(keyClass);
    if (keySerializer == null) {
      throw new NoBinarySerializerRegisteredException(keyClass);
    }
    final BinarySerializer valueSerializer = BinarySerialization.getSerializer(valueClass);
    if (valueSerializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    if (readNullMark(in)) {
      if (allowNullMap) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (result != null) {
        result.clear();
        return result;
      } else {
        return LinkedHashMultimap.create();
      }
    } else {
      if (result == null) {
        result = LinkedHashMultimap.create();
      } else {
        result.clear();
      }
      try {
        for (int i = 0; i < n; ++i) {
          @SuppressWarnings("unchecked")
          final K key = (K) keySerializer.deserialize(in, allowNullKey);
          @SuppressWarnings("unchecked")
          final V value = (V) valueSerializer.deserialize(in, allowNullValue);
          result.put(key, value);
        }
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      return result;
    }
  }
}
