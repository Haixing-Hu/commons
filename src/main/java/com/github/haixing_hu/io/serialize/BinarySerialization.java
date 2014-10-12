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
package com.github.haixing_hu.io.serialize;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.haixing_hu.io.FileUtils;
import com.github.haixing_hu.io.IoUtils;
import com.github.haixing_hu.io.exception.SerializationException;
import com.github.haixing_hu.io.serialize.predefined.BigDecimalBinarySerializer;
import com.github.haixing_hu.io.serialize.predefined.BigIntegerBinarySerializer;
import com.github.haixing_hu.io.serialize.predefined.BooleanArrayBinarySerializer;
import com.github.haixing_hu.io.serialize.predefined.BooleanBinarySerializer;
import com.github.haixing_hu.io.serialize.predefined.ByteArrayBinarySerializer;
import com.github.haixing_hu.io.serialize.predefined.ByteBinarySerializer;
import com.github.haixing_hu.io.serialize.predefined.CharArrayBinarySerializer;
import com.github.haixing_hu.io.serialize.predefined.CharacterBinarySerializer;
import com.github.haixing_hu.io.serialize.predefined.ClassBinarySerializer;
import com.github.haixing_hu.io.serialize.predefined.DateBinarySerializer;
import com.github.haixing_hu.io.serialize.predefined.DoubleArrayBinarySerializer;
import com.github.haixing_hu.io.serialize.predefined.DoubleBinarySerializer;
import com.github.haixing_hu.io.serialize.predefined.FloatArrayBinarySerializer;
import com.github.haixing_hu.io.serialize.predefined.FloatBinarySerializer;
import com.github.haixing_hu.io.serialize.predefined.IntArrayBinarySerializer;
import com.github.haixing_hu.io.serialize.predefined.IntegerBinarySerializer;
import com.github.haixing_hu.io.serialize.predefined.LongArrayBinarySerializer;
import com.github.haixing_hu.io.serialize.predefined.LongBinarySerializer;
import com.github.haixing_hu.io.serialize.predefined.ShortArrayBinarySerializer;
import com.github.haixing_hu.io.serialize.predefined.ShortBinarySerializer;
import com.github.haixing_hu.io.serialize.predefined.StringBinarySerializer;
import com.github.haixing_hu.net.Url;
import com.github.haixing_hu.net.UrlUtils;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * Provides functions to manager {@link BinarySerializer}s, as well as functions
 * to help serializing and deserializing objects to and from binary streams.
 *
 * @author Haixing Hu
 */
public final class BinarySerialization {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(BinarySerialization.class);

  @GuardedBy("registry")
  private static final Map<Class<?>, BinarySerializer> registry = new HashMap<Class<?>, BinarySerializer>();

  // register the binary serializer for classes of common data types
  static {
    registry.put(Boolean.class, BooleanBinarySerializer.INSTANCE);
    registry.put(Character.class, CharacterBinarySerializer.INSTANCE);
    registry.put(Byte.class, ByteBinarySerializer.INSTANCE);
    registry.put(Short.class, ShortBinarySerializer.INSTANCE);
    registry.put(Integer.class, IntegerBinarySerializer.INSTANCE);
    registry.put(Long.class, LongBinarySerializer.INSTANCE);
    registry.put(Float.class, FloatBinarySerializer.INSTANCE);
    registry.put(Double.class, DoubleBinarySerializer.INSTANCE);
    registry.put(String.class, StringBinarySerializer.INSTANCE);
    registry.put(Class.class, ClassBinarySerializer.INSTANCE);
    registry.put(Date.class, DateBinarySerializer.INSTANCE);
    registry.put(BigInteger.class, BigIntegerBinarySerializer.INSTANCE);
    registry.put(BigDecimal.class, BigDecimalBinarySerializer.INSTANCE);

    registry.put(boolean[].class, BooleanArrayBinarySerializer.INSTANCE);
    registry.put(char[].class, CharArrayBinarySerializer.INSTANCE);
    registry.put(byte[].class, ByteArrayBinarySerializer.INSTANCE);
    registry.put(short[].class, ShortArrayBinarySerializer.INSTANCE);
    registry.put(int[].class, IntArrayBinarySerializer.INSTANCE);
    registry.put(long[].class, LongArrayBinarySerializer.INSTANCE);
    registry.put(float[].class, FloatArrayBinarySerializer.INSTANCE);
    registry.put(double[].class, DoubleArrayBinarySerializer.INSTANCE);
  }

  /**
   * Registers a binary serializer for a class.
   *
   * @param T
   *          The type of the class.
   * @param objClass
   *          The class object.
   * @param serializer
   *          The binary serializer for the specified class.
   */
  public static void register(final Class<?> objClass,
      final BinarySerializer serializer) {
    requireNonNull("objClass", objClass);
    requireNonNull("serializer", serializer);
    LOGGER.debug("Registering a binary serializer for class {}.", objClass);
    synchronized (registry) {
      if (registry.containsKey(objClass)) {
        LOGGER.warn("Override the binary serializer for class {}.", objClass);
      }
      registry.put(objClass, serializer);
    }
  }

  /**
   * Gets the registered binary serializer for a specified class.
   *
   * @param T
   *          The type of the class.
   * @param objClass
   *          The class object.
   * @return The registered binary serializer for the specified class, or null
   *         if no binary serializer was registered for the specified class.
   */
  public static BinarySerializer getSerializer(final Class<?> objClass) {
    requireNonNull("objClass", objClass);
    LOGGER.debug("Getting the binary serializer for class {}.", objClass);
    // ensure the objClass has been load, such that the register code in
    // the static initialization block of the class could be run.
    final String className = objClass.getName();
    try {
      Class.forName(className);
    } catch (final ClassNotFoundException e) {
      LOGGER.error("Failed to load the class {}", className);
    }
    synchronized (registry) {
      return registry.get(objClass);
    }
  }

  /**
   * Serializes an object to an binary output stream.
   *
   * @param <T>
   *          The type of the class.
   * @param objClass
   *          The class object of the object to be serialized.
   * @param obj
   *          The object to be serialized. It could be null.
   * @param out
   *          A binary output stream. Note that the output stream will be closed
   *          after calling this function.
   * @throws IOException
   *           If any I/O error occurred.
   */
  public static <T> void serialize(final Class<T> objClass,
      @Nullable final T obj, final OutputStream out) throws IOException {
    final BinarySerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(objClass);
    }
    try {
      serializer.serialize(out, obj);
    } finally {
      IoUtils.closeQuietly(out);
    }
  }

  /**
   * Serializes an object to a local file.
   *
   * @param <T>
   *          The type of the class.
   * @param objClass
   *          The class object of the object to be serialized.
   * @param obj
   *          The object to be serialized. It could be null.
   * @param file
   *          The abstract path of the local file where to store the binary
   *          serialization of the object.
   * @throws IOException
   *           If any I/O error occurred.
   */
  public static <T> void serialize(final Class<T> objClass,
      @Nullable final T obj, final File file) throws IOException {
    final BinarySerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(objClass);
    }
    OutputStream out = null;
    try {
      FileUtils.ensureParentExist(file);
      out = new FileOutputStream(file);
      out = new BufferedOutputStream(out);
      serializer.serialize(out, obj);
    } finally {
      IoUtils.closeQuietly(out);
    }
  }

  /**
   * Serializes an object to a binary array.
   *
   * @param <T>
   *          The type of the class.
   * @param objClass
   *          The class object of the object to be serialized.
   * @param obj
   *          The object to be serialized. It could be null.
   * @return The binary array where to store the binary serialization of the
   *         object.
   * @throws IOException
   *           If any I/O error occurred.
   */
  public static <T> byte[] serialize(final Class<T> objClass,
      @Nullable final T obj) throws IOException {
    final BinarySerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(objClass);
    }
    ByteArrayOutputStream out = null;
    try {
      out = new ByteArrayOutputStream();
      serializer.serialize(out, obj);
      return out.toByteArray();
    } finally {
      IoUtils.closeQuietly(out);
    }
  }

  /**
   * Deserializes an object from an binary input stream.
   *
   * @param <T>
   *          The type of the class.
   * @param obj
   *          The object to serialize.
   * @param in
   *          A binary input stream. Note that the input stream will be closed
   *          after calling this function.
   * @param allowNull
   *          Indicates whether to allowed the serialized object to be null.
   * @return The deserialized object.
   * @throws IOException
   *           If any I/O error occurred.
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass,
      final InputStream in, final boolean allowNull) throws IOException {
    final BinarySerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(objClass);
    }
    try {
      return (T) serializer.deserialize(in, allowNull);
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  /**
   * Deserializes an object from a local file.
   *
   * @param <T>
   *          The type of the class.
   * @param obj
   *          The object to serialize.
   * @param file
   *          An abstract path of a local file.
   * @param allowNull
   *          Indicates whether to allowed the serialized object to be null.
   * @return The deserialized object.
   * @throws IOException
   *           If any I/O error occurred.
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final File file,
      final boolean allowNull) throws IOException {
    final BinarySerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(objClass);
    }
    InputStream in = null;
    try {
      in = new FileInputStream(file);
      in = new BufferedInputStream(in);
      return (T) serializer.deserialize(in, allowNull);
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  /**
   * Deserializes an object from a URL.
   *
   * @param <T>
   *          The type of the class.
   * @param obj
   *          The object to serialize.
   * @param url
   *          An URL.
   * @param allowNull
   *          Indicates whether to allowed the serialized object to be null.
   * @return The deserialized object.
   * @throws IOException
   *           If any I/O error occurred.
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final URL url,
      final boolean allowNull) throws IOException {
    final BinarySerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(objClass);
    }
    InputStream in = null;
    try {
      in = UrlUtils.openStream(url);
      if (! (in instanceof BufferedInputStream)) {
        in = new BufferedInputStream(in);
      }
      return (T) serializer.deserialize(in, allowNull);
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  /**
   * Deserializes an object from a URI.
   *
   * @param <T>
   *          The type of the class.
   * @param obj
   *          The object to serialize.
   * @param uri
   *          An URI.
   * @param allowNull
   *          Indicates whether to allowed the serialized object to be null.
   * @return The deserialized object.
   * @throws IOException
   *           If any I/O error occurred.
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final URI uri,
      final boolean allowNull) throws IOException {
    final BinarySerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(objClass);
    }
    InputStream in = null;
    try {
      final URL url = uri.toURL();
      in = UrlUtils.openStream(url);
      if (! (in instanceof BufferedInputStream)) {
        in = new BufferedInputStream(in);
      }
      return (T) serializer.deserialize(in, allowNull);
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    } catch (final MalformedURLException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  /**
   * Deserializes an object from a URL.
   *
   * @param <T>
   *          The type of the class.
   * @param obj
   *          The object to serialize.
   * @param url
   *          An URL.
   * @param allowNull
   *          Indicates whether to allowed the serialized object to be null.
   * @return The deserialized object.
   * @throws IOException
   *           If any I/O error occurred.
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final Url url,
      final boolean allowNull) throws IOException {
    final BinarySerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(objClass);
    }
    InputStream in = null;
    try {
      in = url.openStream();
      if (! (in instanceof BufferedInputStream)) {
        in = new BufferedInputStream(in);
      }
      return (T) serializer.deserialize(in, allowNull);
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  /**
   * Deserializes an object from a byte array.
   *
   * @param <T>
   *          The type of the class.
   * @param obj
   *          The object to serialize.
   * @param data
   *          An byte array.
   * @param allowNull
   *          Indicates whether to allowed the serialized object to be null.
   * @return The deserialized object.
   * @throws IOException
   *           If any I/O error occurred.
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final byte[] data,
      final boolean allowNull) throws IOException {
    final BinarySerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(objClass);
    }
    ByteArrayInputStream in = null;
    try {
      in = new ByteArrayInputStream(data);
      return (T) serializer.deserialize(in, allowNull);
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }
}
