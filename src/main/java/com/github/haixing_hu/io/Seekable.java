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

/**
 * This interface provides random seeking functions for input streams.
 *
 * @author Haixing Hu
 */
public interface Seekable {

  /**
   * Gets the length of the stream in bytes.
   *
   * @return the length of the stream in bytes.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public long length() throws IOException;

  /**
   * Gets the current position in the stream.
   *
   * @return the current position in the stream.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public long position() throws IOException;

  /**
   * Seeks to a specified position.
   *
   * @param pos
   *          the new position, measured in bytes from the beginning of the
   *          file, at which to set the current position.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public void seek(long pos) throws IOException;
}
