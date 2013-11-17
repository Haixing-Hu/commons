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
import com.github.haixing_hu.lang.SystemUtils;

/**
 * A {@link SeekableInputStream} which reads data from a file.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class SeekableFileInputStream extends SeekableInputStream {

  private static final int JVM_32BIT_READ_LIMIT = 64 * 1024 * 1024;

  private RandomAccessFile in;

  public SeekableFileInputStream(final File file) throws IOException {
    super();
    this.in = new RandomAccessFile(file, "r");
  }

  @Override
  public int read() throws IOException {
    if (in == null) {
      throw new AlreadyClosedException();
    }
    return in.read();
  }

  @Override
  public int read(final byte[] buf, final int off, final int len) throws IOException {
    if (in == null) {
      throw new AlreadyClosedException();
    }
    // note that in 32-bit JVM an OutOfMemoryException may be thrown if
    // the length to be read is too large, which is a known bug of 32-bit JVM.
    // But in 64-bit JVM it's safe to read directly.
    int n = len;
    if (SystemUtils.IS_JAVA_32BIT && (n > JVM_32BIT_READ_LIMIT)) {
      // for 32-bit JVM, read at most JVM_32BIT_READ_LIMIT bytes.
      n = JVM_32BIT_READ_LIMIT;
    }
    return in.read(buf, off, n);
  }

  @Override
  public long length() throws IOException {
    return in.length();
  }

  @Override
  public long position() throws IOException {
    return in.getFilePointer();
  }

  @Override
  public void seek(final long pos) throws IOException {
    in.seek(pos);
  }

  @Override
  public void close() throws IOException {
    if (in != null) {
      in.close();
      in = null;
    }
  }
}
