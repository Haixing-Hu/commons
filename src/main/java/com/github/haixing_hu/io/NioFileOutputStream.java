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

import com.github.haixing_hu.io.exception.AlreadyClosedException;

/**
 * A {@link SeekableOutputStream} which writes data to a file using the
 * <code>java.nio</code> APIs.
 *
 * @author Haixing Hu
 */
public class NioFileOutputStream extends SeekableOutputStream {

  public static final int DEFAULT_BUFFER_SIZE = 8192;

  private RandomAccessFile descriptor;
  private FileChannel channel;
  private ByteBuffer buffer;
  private long offset;

  public NioFileOutputStream(final File file) throws IOException {
    this(file, DEFAULT_BUFFER_SIZE, false);
  }

  public NioFileOutputStream(final File file, final boolean append)
      throws IOException {
    this(file, DEFAULT_BUFFER_SIZE, append);
  }

  public NioFileOutputStream(final File file, final int bufferSize)
      throws IOException {
    this(file, bufferSize, false);
  }

  public NioFileOutputStream(final File file, final int bufferSize,
      final boolean append) throws IOException {
    descriptor = new RandomAccessFile(file, "rws");
    channel = descriptor.getChannel();
    buffer = ByteBuffer.allocate(bufferSize);
    offset = 0;
    if (append) {
      final long len = descriptor.length();
      channel.position(len);
      offset = len;
    }
  }

  @Override
  public void write(final int ch) throws IOException {
    if (descriptor == null) {
      throw new AlreadyClosedException();
    }
    if (! buffer.hasRemaining()) {
      flush();
    }
    buffer.put((byte) ch);
  }

  @Override
  public void write(final byte[] buf, int off, int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > buf.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    if (descriptor == null) {
      throw new AlreadyClosedException();
    }
    if (len == 0) {
      return;
    }
    int room = buffer.remaining();
    // is there enough space in the buffer?
    if (room >= len) {
      // we add the data to the end of the buffer
      buffer.put(buf, off, len);
      // if the buffer is full, flush it
      if (! buffer.hasRemaining()) {
        flush();
      }
    } else { // room < len
      if (len > buffer.capacity()) {
        // flush the buffer
        flush();
        // and write data directly
        final ByteBuffer tempBuffer = ByteBuffer.wrap(buf, off, len);
        channel.write(tempBuffer);
        offset += len;
      } else {
        // we fill/flush the buffer (until the input is written)
        while (len > 0) {
          final int n = (len < room ? len : room);
          buffer.put(buf, off, n);
          off += n;
          len -= n;
          room = buffer.remaining();
          if (room == 0) {
            flush();
            room = buffer.remaining();
          }
        }
      }
    }

  }

  @Override
  public long length() throws IOException {
    if (descriptor == null) {
      throw new AlreadyClosedException();
    }
    return descriptor.length();
  }

  @Override
  public long position() throws IOException {
    if (descriptor == null) {
      throw new AlreadyClosedException();
    }
    return offset + buffer.position();
  }

  @Override
  public void seek(final long newPos) throws IOException {
    if (descriptor == null) {
      throw new AlreadyClosedException();
    }
    final long oldPos = offset + buffer.position();
    if (newPos != oldPos) {
      flush();
      channel.position(newPos);
      offset = newPos;
    }
  }

  @Override
  public void flush() throws IOException {
    if (descriptor == null) {
      throw new AlreadyClosedException();
    }
    final int pos = buffer.position();
    if (pos > 0) {
      buffer.flip();
      channel.write(buffer);
      buffer.clear();
      offset += pos;
    }
  }

  @Override
  public void close() throws IOException {
    if (descriptor != null) {
      // Close the channel & file
      try {
        channel.close();
      } finally {
        try {
          descriptor.close();
        } finally {
          buffer = null;
          channel = null;
          descriptor = null;
        }
      }
    }
  }
}
