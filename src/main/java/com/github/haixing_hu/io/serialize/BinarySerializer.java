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

package com.github.haixing_hu.io.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * A {@link BinarySerializer} provides interface to serialize and deserialize
 * objects to and from binary streams.
 * <p>
 * <b>IMPORTANT NOTE</b>: the implementation of this interface <emph>MUST</emph>
 * be thread safe.
 * </p>
 *
 * @author Haixing Hu
 */
@ThreadSafe
public interface BinarySerializer {

  /**
   * Deserializes an object from a binary input stream.
   *
   * @param in
   *          A binary input stream.
   * @param allowNull
   *          Indicates whether to allowed the returned object to be null.
   * @return The object deserialized from the binary input stream, or null if
   *         the object stored in the binary input stream is null and the
   *         argument <code>allowNull</code> is true.
   * @throws IOException
   *           If any I/O error occurred.
   */
  public Object deserialize(InputStream in, boolean allowNull)
      throws IOException;

  /**
   * Serializes an object to a binary output stream.
   *
   * @param T
   *          The type of the objects to be serialized.
   * @param out
   *          A binary output stream.
   * @param obj
   *          The object to be serialized. It could be null.
   * @throws IOException
   *           If any I/O error occurred.
   */
  public void serialize(OutputStream out, @Nullable Object obj)
      throws IOException;

}
