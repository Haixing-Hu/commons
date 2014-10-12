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
import java.nio.channels.FileChannel;

import com.github.haixing_hu.io.exception.AlreadyClosedException;

/**
 * A {@link SeekableInputStream} which reads data from a file using the
 * {@code java.nio} APIs.
 *
 * @author Haixing Hu
 */
public class NioFileInputStream extends SeekableInputStream {

  public static final int DEFAULT_BUFFER_SIZE = 8192;

  private RandomAccessFile descriptor;
  private FileChannel channel;
  private ByteBuffer buffer;
  private long offset;
  private long length;

  public NioFileInputStream(final File file) throws IOException {
    this(file, DEFAULT_BUFFER_SIZE);
  }

  public NioFileInputStream(final File file, final int bufferSize) throws IOException {
    super();
    descriptor = new RandomAccessFile(file, "r");
    channel = descriptor.getChannel();
    buffer = ByteBuffer.allocate(bufferSize);
    offset = 0;
    length = descriptor.length();
  }

  @Override
  public int read() throws IOException {
    if (descriptor == null) {
      throw new AlreadyClosedException();
    }
    if (! buffer.hasRemaining()) {
      if (! fillBuffer()) {
        return - 1; // EOF
      }
    }
    return (buffer.get() & 0xFF);
  }

  @Override
  public int read(final byte[] buf, int off, int len) throws IOException {
    if ((off < 0) || (len < 0) || (off > buf.length - len)) {
      throw new IndexOutOfBoundsException();
    }
    if (descriptor == null) {
      throw new AlreadyClosedException();
    }
    if (len == 0) {
      return 0;
    }
    final int available = buffer.remaining();
    if (len <= available) {
      // the buffer contains enough data to satisfy this request
      buffer.get(buf, off, len);
      return len;
    }
    // the buffer does not have enough data.
    assert (len > 0);
    int count = 0;
    // First copy all bytes remained in the buffer
    if (available > 0) {
      buffer.get(buf, off, available);
      off += available;
      len -= available;
      count = available;
    }
    // and now, try to read the remaining 'len' bytes:
    if (len < buffer.capacity()) {
      // If the amount left to read is small enough, and
      // we are allowed to use our buffer, do it in the usual
      // buffered way: fill the buffer once and copy from it:
      if (! fillBuffer()) {
        // EOF has encountered
        return (count == 0 ? - 1 : count);
      }
      int n = buffer.remaining();
      if (n > len) {
        n = len;
      }
      buffer.get(buf, off, n);
      return count + n;
    } else { // len >= buffer.length
      // The amount left to read is larger than the buffer
      // or we've been asked to not use our buffer -
      // there's no performance reason not to read it all
      // at once. Note that unlike the previous code of
      // this function, there is no need to do a seek
      // here, because there's no need to reread what we
      // had in the buffer.
      assert (! buffer.hasRemaining()); // the buffer should be empty
      final ByteBuffer tempBuffer = ByteBuffer.wrap(buf, off, len);
      final int n = channel.read(tempBuffer);
      if (n < 0) { // EOF
        return (count == 0 ? - 1 : count);
      } else {
        // fix the start offset of the next buffer
        offset += (buffer.position() + n);
        buffer.position(0);
        buffer.limit(0);
        return count + n;
      }
    }
  }

  private boolean fillBuffer() throws IOException {
    // fix the start offset of the buffer
    offset += buffer.position();
    // next fill the buffer
    buffer.clear();
    int n;
    do {
      n = channel.read(buffer);
    } while (n == 0);
    buffer.flip();
    return (n > 0);
  }

  @Override
  public long length() throws IOException {
    return length;
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
    if ((newPos >= offset) && (newPos < (offset + buffer.limit()))) {
      // seek within the buffer
      buffer.position((int) (newPos - offset));
    } else {
      // seek the channel to the new position
      channel.position(newPos);
      // abandon the current data in buffer
      offset = newPos;
      buffer.position(0);
      buffer.limit(0);
    }
  }

  @Override
  public void close() throws IOException {
    if (descriptor != null) {
      try {
        descriptor.close();
      } finally {
        buffer = null;
        channel = null;
        descriptor = null;
        length = 0;
      }
    }
  }

}
