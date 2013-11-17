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

import java.io.Writer;

import javax.annotation.concurrent.Immutable;

/**
 * This {@link Writer} writes all data to the famous <b>/dev/null</b>.
 *
 * This <code>Writer</code> has no destination (file/socket etc.) and all
 * characters written to it are ignored and lost.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NullWriter extends Writer {

  /**
   * The singleton instance of the NullWriter.
   */
  public static final NullWriter INSTANCE = new NullWriter();

  /**
   * The restrictive constructor.
   */
  private NullWriter() {
    // empty
  }

  /**
   * Does nothing - output to <code>/dev/null</code>.
   *
   * @param idx
   *          The character to write
   */
  @Override
  public void write(int idx) {
    // to /dev/null
  }

  /**
   * Does nothing - output to <code>/dev/null</code>.
   *
   * @param chr
   *          The characters to write
   */
  @Override
  public void write(char[] chr) {
    // to /dev/null
  }

  /**
   * Does nothing - output to <code>/dev/null</code>.
   *
   * @param chr
   *          The characters to write
   * @param st
   *          The start offset
   * @param end
   *          The number of characters to write
   */
  @Override
  public void write(char[] chr, int st, int end) {
    // to /dev/null
  }

  /**
   * Does nothing - output to <code>/dev/null</code>.
   *
   * @param str
   *          The string to write
   */
  @Override
  public void write(String str) {
    // to /dev/null
  }

  /**
   * Does nothing - output to <code>/dev/null</code>.
   *
   * @param str
   *          The string to write
   * @param st
   *          The start offset
   * @param end
   *          The number of characters to write
   */
  @Override
  public void write(String str, int st, int end) {
    // to /dev/null
  }

  /**
   *  @see java.io.Writer#flush()
   */
  @Override
  public void flush() {
    // to /dev/null
  }

  /**
   * @see java.io.Writer#close()
   */
  @Override
  public void close() {
    // to /dev/null
  }

}
