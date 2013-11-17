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
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import javax.annotation.concurrent.NotThreadSafe;

import com.github.haixing_hu.io.exception.AlreadyClosedException;
import com.github.haixing_hu.lang.SystemUtils;

import static com.github.haixing_hu.CommonsMessages.BUFFER_SIZE_MUST_POSITIVE;

/**
 * A {@link SeekableInputStream} to read data from a file using mutiple mmaps.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class MultiMmapFileInputStream extends SeekableInputStream {

  private final int        maxBufferSize;
  private ByteBuffer[]     buffers;
  private long             length;
  private int              index;
  private ByteBuffer       buffer;       // buffers[current]
  private int              available;    // bufferSizes[current] - buffer.position()

  public MultiMmapFileInputStream(final File file, final int maxBufferSize)
      throws IOException {
    if (maxBufferSize <= 0) {
      throw new IllegalArgumentException(BUFFER_SIZE_MUST_POSITIVE);
    }
    this.maxBufferSize = maxBufferSize;
    final RandomAccessFile raf = new RandomAccessFile(file, "r");
    try {
      length = raf.length();
      if ((length / maxBufferSize) > Integer.MAX_VALUE) {
        throw new IOException("The file is too big for maximum buffer size "
            + maxBufferSize + ": " + file);
      }
      int n = (int) (length / maxBufferSize);
      if (((long) n * maxBufferSize) < length) {
        ++n;
      }
      buffers = new ByteBuffer[n];
      long pos = 0;
      final FileChannel channel = raf.getChannel();
      for (int i = 0; i < n; ++i) {
        int size = maxBufferSize;
        if (length <= (pos + maxBufferSize)) {
          size = (int) (length - pos);
        }
        assert (size > 0);
        buffers[i] = channel.map(MapMode.READ_ONLY, pos, size);
        pos += size;
      }
    } finally {
      raf.close();
    }
    index = 0;
    buffer = buffers[0];
    buffer.position(0);
    available = buffer.remaining();
  }

  @Override
  public int available() throws IOException {
    final long result = length - this.position();
    if (result > Integer.MAX_VALUE) {
      return Integer.MAX_VALUE;
    } else {
      return (int) result;
    }
  }

  @Override
  public int read() throws IOException {
    if (buffer == null) {
      throw new AlreadyClosedException();
    }
    // Performance might be improved by reading ahead into an array of
    // e.g. 128 bytes and read() from there.
    if (available == 0) {
      ++index;
      if (index >= buffers.length) {
        return -1;    // EOF
      }
      buffer = buffers[index];
      buffer.position(0);
      available = buffer.remaining();
    }
    assert (available > 0);
    --available;
    return buffer.get();
  }

  @Override
  public int read(final byte[] buf, int off, int len)
      throws IOException {
    if (buffer == null) {
      throw new AlreadyClosedException();
    }
    if ((available == 0) && (index >= buffers.length)) {
      return -1; // EOF
    }
    int count = 0;
    while (len > available) {
      buffer.get(buf, off, available);
      len -= available;
      off += available;
      count += available;
      available = 0;
      ++index;
      if (index >= buffers.length) { // EOF
        return (count == 0 ? -1 : count);
      }
      buffer = buffers[index];
      buffer.position(0);
      available = buffer.remaining();
    }
    buffer.get(buf, off, len);
    available -= len;
    return count + len;
  }

  @Override
  public long length() throws IOException {
    return length;
  }

  @Override
  public long position() throws IOException {
    if (buffer == null) {
      throw new AlreadyClosedException();
    }
    return ((long)index * maxBufferSize) + buffer.position();
  }

  @Override
  public void seek(final long newPos) throws IOException {
    if (buffer == null) {
      throw new AlreadyClosedException();
    }
    index = (int) (newPos / maxBufferSize);
    buffer = buffers[index];
    final int bufferPos = (int) (newPos - ((long) index * maxBufferSize));
    buffer.position(bufferPos);
    available = buffer.remaining();
  }

  @Override
  public void close() throws IOException {
    if (buffer != null) {
      try {
        for (int i = 0; i < buffers.length; ++i) {
          try {
            SystemUtils.cleanupMmapping(buffers[i]);
          } finally {
            buffers[i] = null;
          }
        }
      } finally {
        buffers = null;
        buffer = null;
      }
    }
  }
}
