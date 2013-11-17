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

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.annotation.Nullable;

import com.github.haixing_hu.collection.primitive.BooleanCollection;
import com.github.haixing_hu.collection.primitive.BooleanIterator;
import com.github.haixing_hu.collection.primitive.ByteCollection;
import com.github.haixing_hu.collection.primitive.ByteIterator;
import com.github.haixing_hu.collection.primitive.CharCollection;
import com.github.haixing_hu.collection.primitive.CharIterator;
import com.github.haixing_hu.collection.primitive.DoubleCollection;
import com.github.haixing_hu.collection.primitive.DoubleIterator;
import com.github.haixing_hu.collection.primitive.FloatCollection;
import com.github.haixing_hu.collection.primitive.FloatIterator;
import com.github.haixing_hu.collection.primitive.IntCollection;
import com.github.haixing_hu.collection.primitive.IntIterator;
import com.github.haixing_hu.collection.primitive.LongCollection;
import com.github.haixing_hu.collection.primitive.LongIterator;
import com.github.haixing_hu.collection.primitive.ShortCollection;
import com.github.haixing_hu.collection.primitive.ShortIterator;
import com.github.haixing_hu.io.serialize.BinarySerialization;
import com.github.haixing_hu.io.serialize.BinarySerializer;
import com.github.haixing_hu.io.serialize.NoBinarySerializerRegisteredException;
import com.github.haixing_hu.util.buffer.ByteBuffer;
import com.google.common.collect.Multimap;

/**
 * Provides functions to write data to the {@link Output} object.
 *
 * @author Haixing Hu
 */
public final class OutputUtils {

  private static final String UNSUPPORTED_VAR_NUMBER  =
    "The variable length integer format does not support negative value.";

  public static boolean writeNullMark(final OutputStream out, final Object object)
      throws IOException {
    if (object == null) {
      out.write(1);
      return true;
    } else {
      out.write(0);
      return false;
    }
  }

  public static void writeBoolean(final OutputStream out, final boolean value)
      throws IOException {
    if (value) {
      out.write(1);
    } else {
      out.write(0);
    }
  }

  public static void writeBooleanObject(final OutputStream out, @Nullable final Boolean value)
      throws IOException {
    if (! writeNullMark(out, value)) {
      writeBoolean(out, value);
    }
  }

  public static void writeChar(final OutputStream out, final char value)
      throws IOException {
    writeVarShort(out, (short) value);
  }

  public static void writeCharObject(final OutputStream out, @Nullable final Character value)
      throws IOException {
    if (! writeNullMark(out, value)) {
      writeChar(out, value);
    }
  }

  public static void writeByte(final OutputStream out, final byte value)
      throws IOException {
    out.write(value);
  }

  public static void writeByteObject(final OutputStream out, @Nullable final Byte value)
      throws IOException {
    if (! writeNullMark(out, value)) {
      writeByte(out, value);
    }
  }

  public static void writeShort(final OutputStream out, final short value)
      throws IOException {
    out.write(value >>> 8);
    out.write(value);
  }

  public static void writeShortObject(final OutputStream out, @Nullable final Short value)
      throws IOException {
    if (! writeNullMark(out, value)) {
      writeShort(out, value);
    }
  }

  public static void writeVarShort(final OutputStream out, final short value)
      throws IOException {
    writeVarInt(out, value);
  }

  public static void writeVarShortObject(final OutputStream out, @Nullable final Short value)
      throws IOException {
    if ((value != null) && (value < 0)) {
      throw new IllegalArgumentException(UNSUPPORTED_VAR_NUMBER);
    }
    if (! writeNullMark(out, value)) {
      writeVarInt(out, value);
    }
  }

  public static void writeInt(final OutputStream out, final int value)
      throws IOException {
    out.write(value >>> 24);
    out.write(value >>> 16);
    out.write(value >>> 8);
    out.write(value);
  }

  public static void writeIntObject(final OutputStream out, @Nullable final Integer value)
      throws IOException {
    if (! writeNullMark(out, value)) {
      writeInt(out, value);
    }
  }

  public static void writeVarInt(final OutputStream out, int value)
      throws IOException {
    if (value < 0) {
      throw new IllegalArgumentException(UNSUPPORTED_VAR_NUMBER);
    }
    while (value > 0x7F) {
      out.write((value & 0x7F) | 0x80);
      value >>>= 7;
    }
    out.write(value);
  }

  public static void writeVarIntObject(final OutputStream out, @Nullable final Integer value)
      throws IOException {
    if ((value != null) && (value < 0)) {
      throw new IllegalArgumentException(UNSUPPORTED_VAR_NUMBER);
    }
    if (! writeNullMark(out, value)) {
      writeVarInt(out, value);
    }
  }

  public static void writeLong(final OutputStream out, final long value)
      throws IOException {
    final byte[] buffer = new byte[8];
    buffer[0] = (byte) (value >>> 56);
    buffer[1] = (byte) (value >>> 48);
    buffer[2] = (byte) (value >>> 40);
    buffer[3] = (byte) (value >>> 32);
    buffer[4] = (byte) (value >>> 24);
    buffer[5] = (byte) (value >>> 16);
    buffer[6] = (byte) (value >>> 8);
    buffer[7] = (byte) value;
    out.write(buffer, 0, 8);
  }

  public static void writeLongObject(final OutputStream out, @Nullable final Long value)
      throws IOException {
    if (! writeNullMark(out, value)) {
      writeLong(out, value);
    }
  }

  public static void writeVarLong(final OutputStream out, long value)
      throws IOException {
    if (value < 0) {
      throw new IllegalArgumentException(UNSUPPORTED_VAR_NUMBER);
    }
    while (value > 0x7FL) {
      out.write((int)(value & 0x7FL) | 0x80);
      value >>>= 7;
    }
    out.write((int) value);
  }

  public static void writeVarLongObject(final OutputStream out, @Nullable final Long value)
      throws IOException {
    if ((value != null) && (value < 0)) {
      throw new IllegalArgumentException(UNSUPPORTED_VAR_NUMBER);
    }
    if (! writeNullMark(out, value)) {
      writeVarLong(out, value);
    }
  }

  public static void writeFloat(final OutputStream out, final float value)
      throws IOException {
    final int intBits = Float.floatToIntBits(value);
    writeInt(out, intBits);
  }

  public static void writeFloatObject(final OutputStream out, @Nullable final Float value)
      throws IOException {
    if (! writeNullMark(out, value)) {
      writeFloat(out, value);
    }
  }

  public static void writeDouble(final OutputStream out, final double value)
      throws IOException {
    final long longBits = Double.doubleToLongBits(value);
    writeLong(out, longBits);
  }

  public static void writeDoubleObject(final OutputStream out, @Nullable final Double value)
      throws IOException {
    if (! writeNullMark(out, value)) {
      writeDouble(out, value);
    }
  }

  public static void writeString(final OutputStream out, @Nullable final String value)
      throws IOException {
    if (! writeNullMark(out, value)) {
      final int strlen = value.length();
      if (strlen == 0) {
        writeVarInt(out, 0); // special case for empty string
        return;
      }
      final ByteBuffer bytes = new ByteBuffer();
      // encode the characters
      for (int i = 0; i < strlen; ++i) {
        final int ch = value.charAt(i);
        if ((ch >= 0x0001) && (ch <= 0x007F)) {
          bytes.append((byte) ch);
        } else if (ch > 0x07FF) {
          bytes.append((byte) (0xE0 | ((ch >> 12) & 0x0F)));
          bytes.append((byte) (0x80 | ((ch >> 6) & 0x3F)));
          bytes.append((byte) (0x80 | (ch & 0x3F)));
        } else {
          bytes.append((byte) (0xC0 | ((ch >> 6) & 0x1F)));
          bytes.append((byte) (0x80 | (ch & 0x3F)));
        }
      }
      // write the length and bytes of the buffer
      writeVarInt(out, bytes.length());
      out.write(bytes.buffer(), 0, bytes.length());
    }
  }

  public static void writeDate(final OutputStream out, @Nullable final Date value)
      throws IOException {
    if (! writeNullMark(out, value)) {
      final long time = value.getTime();
      writeLong(out, time);
    }
  }

  public static void writeBigInteger(final OutputStream out,
      @Nullable final BigInteger value) throws IOException {
    if (! writeNullMark(out, value)) {
      if (value.signum() == 0) { // value == 0
        writeVarInt(out, 0);
      } else {
        final byte[] bits = value.toByteArray();
        writeVarInt(out, bits.length);
        out.write(bits, 0, bits.length);
      }
    }
  }

  public static void writeBigDecimal(final OutputStream out,
      @Nullable final BigDecimal value) throws IOException {
    if (! writeNullMark(out, value)) {
      if (value.signum() == 0) { // value == 0
        writeVarInt(out, 0);
      } else {
        final BigInteger unscaledValue = value.unscaledValue();
        final byte[] bits = unscaledValue.toByteArray();
        writeVarInt(out, bits.length);
        out.write(bits, 0, bits.length);
        writeInt(out, value.scale());
      }
    }
  }

  public static void writeClass(final OutputStream out, @Nullable final Class<?> value)
      throws IOException {
    if (value == null) {
      writeString(out, null);
    } else {
      writeString(out, value.getName());
    }
  }

  public static void writeEnum(final OutputStream out, @Nullable final Enum<?> value)
      throws IOException {
    if (! writeNullMark(out, value)) {
      writeVarInt(out, value.ordinal());
    }
  }

  public static void writeBooleanArray(final OutputStream out,
      @Nullable final boolean[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final boolean value : array) {
        writeBoolean(out, value);
      }
    }
  }

  public static void writeBooleanArray(final OutputStream out, final boolean[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      writeBoolean(out, array[i]);
    }
  }

  public static void writeBooleanCollection(final OutputStream out,
      @Nullable final BooleanCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final BooleanIterator iter = col.iterator();
      while (iter.hasNext()) {
        final boolean value = iter.next();
        writeBoolean(out, value);
      }
    }
  }

  public static void writeCharArray(final OutputStream out,
      @Nullable final char[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final char value : array) {
        writeChar(out, value);
      }
    }
  }

  public static void writeCharArray(final OutputStream out, final char[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      writeChar(out, array[i]);
    }
  }

  public static void writeCharCollection(final OutputStream out,
      @Nullable final CharCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final CharIterator iter = col.iterator();
      while (iter.hasNext()) {
        final char value = iter.next();
        writeChar(out, value);
      }
    }
  }

  public static void writeByteArray(final OutputStream out,
      @Nullable final byte[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      // use the write() to optimize the writing of bytes
      out.write(array, 0, array.length);
    }
  }

  public static void writeByteArray(final OutputStream out, final byte[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    out.write(array, off, len);
  }

  public static void writeByteCollection(final OutputStream out,
      @Nullable final ByteCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final ByteIterator iter = col.iterator();
      while (iter.hasNext()) {
        final byte value = iter.next();
        writeByte(out, value);
      }
    }
  }

  public static void writeShortArray(final OutputStream out,
      @Nullable final short[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final short value : array) {
        writeShort(out, value);
      }
    }
  }

  public static void writeShortArray(final OutputStream out, final short[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      writeShort(out, array[i]);
    }
  }

  public static void writeShortCollection(final OutputStream out,
      @Nullable final ShortCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final ShortIterator iter = col.iterator();
      while (iter.hasNext()) {
        final short value = iter.next();
        writeShort(out, value);
      }
    }
  }

  public static void writeVarShortArray(final OutputStream out,
      @Nullable final short[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final short value : array) {
        writeVarShort(out, value);
      }
    }
  }

  public static void writeVarShortArray(final OutputStream out, final short[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      writeVarShort(out, array[i]);
    }
  }

  public static void writeVarShortCollection(final OutputStream out,
      @Nullable final ShortCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final ShortIterator iter = col.iterator();
      while (iter.hasNext()) {
        final short value = iter.next();
        writeVarShort(out, value);
      }
    }
  }

  public static void writeIntArray(final OutputStream out, @Nullable final int[] array)
      throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final int value : array) {
        writeInt(out, value);
      }
    }
  }

  public static void writeIntArray(final OutputStream out, final int[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      writeInt(out, array[i]);
    }
  }

  public static void writeIntCollection(final OutputStream out,
      @Nullable final IntCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final IntIterator iter = col.iterator();
      while (iter.hasNext()) {
        final int value = iter.next();
        writeInt(out, value);
      }
    }
  }

  public static void writeVarIntArray(final OutputStream out,
      @Nullable final int[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final int value : array) {
        writeVarInt(out, value);
      }
    }
  }

  public static void writeVarIntArray(final OutputStream out, final int[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      writeVarInt(out, array[i]);
    }
  }

  public static void writeVarIntCollection(final OutputStream out,
      @Nullable final IntCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final IntIterator iter = col.iterator();
      while (iter.hasNext()) {
        final int value = iter.next();
        writeVarInt(out, value);
      }
    }
  }

  public static void writeLongArray(final OutputStream out,
      @Nullable final long[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final long value : array) {
        writeLong(out, value);
      }
    }
  }

  public static void writeLongArray(final OutputStream out, final long[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      writeLong(out, array[i]);
    }
  }

  public static void writeLongCollection(final OutputStream out,
      @Nullable final LongCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final LongIterator iter = col.iterator();
      while (iter.hasNext()) {
        final long value = iter.next();
        writeLong(out, value);
      }
    }
  }

  public static void writeVarLongArray(final OutputStream out,
      @Nullable final long[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final long value : array) {
        writeVarLong(out, value);
      }
    }
  }

  public static void writeVarLongArray(final OutputStream out, final long[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      writeVarLong(out, array[i]);
    }
  }

  public static void writeVarLongCollection(final OutputStream out,
      @Nullable final LongCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final LongIterator iter = col.iterator();
      while (iter.hasNext()) {
        final long value = iter.next();
        writeVarLong(out, value);
      }
    }
  }

  public static void writeFloatArray(final OutputStream out,
      @Nullable final float[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final float value : array) {
        writeFloat(out, value);
      }
    }
  }

  public static void writeFloatArray(final OutputStream out, final float[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      writeFloat(out, array[i]);
    }
  }

  public static void writeFloatCollection(final OutputStream out,
      @Nullable final FloatCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final FloatIterator iter = col.iterator();
      while (iter.hasNext()) {
        final float value = iter.next();
        writeFloat(out, value);
      }
    }
  }

  public static void writeDoubleArray(final OutputStream out,
      @Nullable final double[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final double value : array) {
        writeDouble(out, value);
      }
    }
  }

  public static void writeDoubleArray(final OutputStream out, final double[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      writeDouble(out, array[i]);
    }
  }

  public static void writeDoubleCollection(final OutputStream out,
      @Nullable final DoubleCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final DoubleIterator iter = col.iterator();
      while (iter.hasNext()) {
        final double value = iter.next();
        writeDouble(out, value);
      }
    }
  }

  /**
   * Writes an object to an binary output stream.
   *
   * @param <T>
   *          The type of the class.
   * @param valueClass
   *          The class object of the object to be serialized.
   * @param out
   *          A binary output stream.
   * @param value
   *          The object to be serialized. It could be null.
   * @throws IOException
   *           If any I/O error occurred.
   */
  public static <T> void writeObject(final Class<T> valueClass,
      final OutputStream out, @Nullable final T value) throws IOException {
    final BinarySerializer serializer = BinarySerialization.getSerializer(valueClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    serializer.serialize(out, value);
  }

  public static <T> void writeArray(final Class<T> valueClass,
      final OutputStream out, @Nullable final T[] array) throws IOException {
    final BinarySerializer serializer = BinarySerialization.getSerializer(valueClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final T value : array) {
        serializer.serialize(out, value);
      }
    }
  }

  public static <T> void writeArray(final Class<T> valueClass,
      final OutputStream out, final T[] array, final int off, final int len)
      throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    final BinarySerializer serializer = BinarySerialization.getSerializer(valueClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      serializer.serialize(out, array[i]);
    }
  }

  public static <T> void writeCollection(final Class<T> valueClass,
      final OutputStream out, @Nullable final Collection<T> col)
      throws IOException {
    final BinarySerializer serializer = BinarySerialization.getSerializer(valueClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      for (final T value : col) {
        serializer.serialize(out, value);
      }
    }
  }

  public static <K, V> void writeMap(final Class<K> keyClass,
      final Class<V> valueClass, final OutputStream out,
      @Nullable final Map<K, V> map) throws IOException {
    final BinarySerializer keySerializer = BinarySerialization.getSerializer(keyClass);
    if (keySerializer == null) {
      throw new NoBinarySerializerRegisteredException(keyClass);
    }
    final BinarySerializer valueSerializer = BinarySerialization.getSerializer(valueClass);
    if (valueSerializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    if (! writeNullMark(out, map)) {
      writeVarInt(out, map.size());
      for (final Map.Entry<K, V> entry : map.entrySet()) {
        final K key = entry.getKey();
        final V value = entry.getValue();
        keySerializer.serialize(out, key);
        valueSerializer.serialize(out, value);
      }
    }
  }

  public static <K, V> void writeMultimap(final OutputStream out,
      @Nullable final Multimap<K, V> map, final Class<K> keyClass,
      final Class<V> valueClass) throws IOException {
    final BinarySerializer keySerializer = BinarySerialization.getSerializer(keyClass);
    if (keySerializer == null) {
      throw new NoBinarySerializerRegisteredException(keyClass);
    }
    final BinarySerializer valueSerializer = BinarySerialization.getSerializer(valueClass);
    if (valueSerializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    if (! writeNullMark(out, map)) {
      writeVarInt(out, map.size());
      for (final Map.Entry<K, V> entry : map.entries()) {
        final K key = entry.getKey();
        final V value = entry.getValue();
        keySerializer.serialize(out, key);
        valueSerializer.serialize(out, value);
      }
    }
  }
}
