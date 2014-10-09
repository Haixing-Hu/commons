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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;

import com.github.haixing_hu.io.exception.SerializationException;

import static com.github.haixing_hu.lang.Argument.*;

/**
 * Assists with the serialization process and performs additional functionality
 * based on serialization.
 *
 * <ul>
 * <li>Deep clone using serialization
 * <li>Serialize managing finally and IOException
 * <li>Deserialize managing finally and IOException
 * </ul>
 *
 * This class throws exceptions for invalid {@code null} inputs. Each
 * method documents its behavior in more detail.
 *
 * @ThreadSafe
 * @author Haixing Hu
 */
public final class Serializer {

  private Serializer() {}

  /**
   * Serializes an {@code Object} to the specified stream.
   *
   * The stream will be closed once the object is written. This avoids the need
   * for a finally clause, and maybe also exception handling, in the application
   * code.
   *
   * The stream passed in is not buffered internally within this method. This is
   * the responsibility of your application if desired.
   *
   * @param object
   *          the object to serialize to bytes, may be null
   * @param output
   *          the stream to write to, must not be null
   * @throws SerializationException
   * @throws NullPointerException
   *           if {@code object} or {@code output} is
   *           {@code null}
   * @throws SerializationException
   *           if the serialization failed.
   */
  public static void serialize(final Serializable object, final OutputStream output)
      throws SerializationException {
    requireNonNull("object", object);
    requireNonNull("output", output);
    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(output);
      oos.writeObject(object);
    } catch (final IOException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(oos);
      IoUtils.closeQuietly(output);
    }
  }

  /**
   * Serializes an {@code Object} to a file for storage/serialization.
   *
   * @param object
   *          the object to serialize to bytes
   * @param file
   *          the abstract pathname of the file to store the serialization of
   *          the object.
   * @throws NullPointerException
   *           if {@code object} or {@code file} is
   *           {@code null}
   * @throws SerializationException
   *           if the serialization failed.
   */
  public static void serialize(final Serializable object, final File file)
      throws SerializationException {
    requireNonNull("object", object);
    requireNonNull("file", file);
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    ObjectOutputStream oos = null;
    try {
      FileUtils.ensureParentExist(file);
      fos = new FileOutputStream(file);
      bos = new BufferedOutputStream(fos);
      oos = new ObjectOutputStream(bos);
      oos.writeObject(object);
    } catch (final IOException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(oos);
      IoUtils.closeQuietly(bos);
      IoUtils.closeQuietly(fos);
    }
  }

  /**
   * Serializes an {@code Object} to a byte array for
   * storage/serialization.
   *
   * @param object
   *          the object to serialize to bytes
   * @return a byte array for the converted Serializable object.
   * @throws NullPointerException
   *           if {@code object} is {@code null}.
   * @throws SerializationException
   *           if the serialization failed.
   */
  public static byte[] serialize(final Serializable object)
      throws SerializationException {
    requireNonNull("object", object);
    final ByteArrayOutputStream output = new ByteArrayOutputStream();
    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(output);
      oos.writeObject(object);
    } catch (final IOException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(oos);
      IoUtils.closeQuietly(output);
    }
    return output.toByteArray();
  }

  /**
   * Deserializes an {@code Object} from the specified stream.
   *
   * The stream will be closed once the object is written. This avoids the need
   * for a finally clause, and maybe also exception handling, in the application
   * code.
   *
   * The stream passed in is not buffered internally within this method. This is
   * the responsibility of your application if desired.
   *
   * @param input
   *          the serialized object input stream, must not be null
   * @return the deserialized object
   * @throws IOException
   * @throws NullPointerException
   *           if {@code inputStream} is {@code null}
   * @throws SerializationException
   *           if the deserialization failed.
   */
  public static Object deserialize(final InputStream input)
      throws SerializationException {
    requireNonNull("input", input);
    ObjectInputStream ois = null;
    try {
      // stream closed in the finally
      ois = new ObjectInputStream(input);
      return ois.readObject();
    } catch (final ClassNotFoundException e) {
      throw new SerializationException(e);
    } catch (final IOException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(ois);
      IoUtils.closeQuietly(input);
    }
  }

  /**
   * Deserializes a single {@code Object} from a file.
   *
   * @param file
   *          the abstract pathname of a file.
   * @return the deserialized object
   * @throws SerializationException
   * @throws NullPointerException
   *           if {@code file} is {@code null}.
   * @throws SerializationException
   *           if the deserialization failed.
   */
  public static Object deserialize(final File file)
      throws SerializationException {
    requireNonNull("file", file);
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    ObjectInputStream ois = null;
    try {
      fis = new FileInputStream(file);
      bis = new BufferedInputStream(fis);
      ois = new ObjectInputStream(bis);
      return ois.readObject();
    } catch (final ClassNotFoundException e) {
      throw new SerializationException(e);
    } catch (final IOException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(ois);
      IoUtils.closeQuietly(bis);
      IoUtils.closeQuietly(fis);
    }
  }

  /**
   * Deserializes a single {@code Object} from a URL.
   *
   * @param url
   *          a URL.
   * @return the deserialized object
   * @throws SerializationException
   * @throws NullPointerException
   *           if {@code url} is {@code null}.
   * @throws SerializationException
   *           if the deserialization failed.
   */
  public static Object deserialize(final URL url)
      throws SerializationException {
    requireNonNull("url", url);
    InputStream input = null;
    BufferedInputStream bis = null;
    ObjectInputStream ois = null;
    try {
      input = url.openStream();
      bis = new BufferedInputStream(input);
      ois = new ObjectInputStream(bis);
      return ois.readObject();
    } catch (final ClassNotFoundException e) {
      throw new SerializationException(e);
    } catch (final IOException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(ois);
      IoUtils.closeQuietly(bis);
      IoUtils.closeQuietly(input);
    }
  }

  /**
   * Deserializes a single {@code Object} from an array of bytes.
   *
   * @param data
   *          the serialized object, must not be null
   * @return the deserialized object
   * @throws SerializationException
   * @throws NullPointerException
   *           if {@code objectData} is {@code null} or empty.
   * @throws SerializationException
   *           if the deserialization failed.
   */
  public static Object deserialize(final byte[] data)
      throws SerializationException {
    requireNonEmpty("data", data);
    final ByteArrayInputStream input = new ByteArrayInputStream(data);
    ObjectInputStream ois = null;
    try {
      // stream closed in the finally
      ois = new ObjectInputStream(input);
      return ois.readObject();
    } catch (final ClassNotFoundException e) {
      throw new SerializationException(e);
    } catch (final IOException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(ois);
      IoUtils.closeQuietly(input);
    }
  }
}
