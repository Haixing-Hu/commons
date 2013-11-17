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

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.annotation.concurrent.NotThreadSafe;

import com.github.haixing_hu.io.exception.AlreadyClosedException;


/**
 * A {@link SeekableOutputStream} which writes data into a file.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class SeekableFileOutputStream extends SeekableOutputStream {

  private RandomAccessFile out;

  public SeekableFileOutputStream(final File file) throws IOException {
    out = new RandomAccessFile(file, "w");
  }

  public SeekableFileOutputStream(final File file, final boolean append) throws IOException {
    out = new RandomAccessFile(file, "w");
    if (append) {
      out.seek(out.length());
    }
  }

  @Override
  public void write(final int data) throws IOException {
    if (out == null) {
      throw new AlreadyClosedException();
    }
    out.write(data);
  }

  @Override
  public void write(final byte[] data, final int off, final int len) throws IOException {
    if (out == null) {
      throw new AlreadyClosedException();
    }
    out.write(data, off, len);
  }

  @Override
  public void flush() throws IOException {
    if (out == null) {
      throw new AlreadyClosedException();
    }
    out.getFD().sync();
  }

  @Override
  public void close() throws IOException {
    if (out != null) {
      out.close();
      out = null;
    }
  }

  @Override
  public long length() throws IOException {
    return out.length();
  }

  @Override
  public long position() throws IOException {
    return out.getFilePointer();
  }

  @Override
  public void seek(final long pos) throws IOException {
    out.seek(pos);
  }
}
