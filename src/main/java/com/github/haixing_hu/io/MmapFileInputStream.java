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
package com.github.haixing_hu.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel.MapMode;

import javax.annotation.concurrent.NotThreadSafe;

import com.github.haixing_hu.io.exception.AlreadyClosedException;
import com.github.haixing_hu.io.exception.InvalidSeekPositionException;
import com.github.haixing_hu.lang.SystemUtils;

/**
 * A {@link SeekableInputStream} to read data from a file using a mmap.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class MmapFileInputStream extends SeekableInputStream {

  private ByteBuffer buffer;

  public MmapFileInputStream(final File file) throws IOException {
    super();
    final RandomAccessFile raf = new RandomAccessFile(file, "r");
    try {
      buffer = raf.getChannel().map(MapMode.READ_ONLY, 0, raf.length());
    } finally {
      raf.close();
    }
  }

  @Override
  public int available() throws IOException {
    return buffer.remaining();
  }

  @Override
  public int read() throws IOException {
    if (buffer == null) {
      throw new AlreadyClosedException();
    }
    if (buffer.position() < buffer.limit()) {
      return buffer.get();
    } else {
      return -1;
    }
  }

  @Override
  public int read(final byte[] buf, final int off, final int len)
      throws IOException {
    if (buffer == null) {
      throw new AlreadyClosedException();
    }
    int count = buffer.remaining();
    if (count == 0) {
      return -1;
    } else {
      if (len < count) {
        count = len;
      }
      buffer.get(buf, off, count);
      return count;
    }
  }


  @Override
  public long length() throws IOException {
    if (buffer == null) {
      throw new AlreadyClosedException();
    }
    return buffer.limit();
  }

  @Override
  public long position() throws IOException {
    if (buffer == null) {
      throw new AlreadyClosedException();
    }
    return buffer.position();
  }

  @Override
  public void seek(final long pos) throws IOException {
    if (buffer == null) {
      throw new AlreadyClosedException();
    }
    if (pos > buffer.limit()) {
      throw new InvalidSeekPositionException(pos);
    }
    buffer.position((int)pos);
  }

  @Override
  public void close() throws IOException {
    if (buffer != null) {
      try {
        SystemUtils.cleanupMmapping(buffer);
      } finally {
        buffer = null;
      }
    }
  }

}
