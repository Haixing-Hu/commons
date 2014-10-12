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

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * A wrap of another input stream which limit the number of bytes
 * could be read from the underlying input stream.
 *
 * @NotThreadSafe
 * @author Haixing Hu
 */
@NotThreadSafe
public class ReadLimitInputStream extends FilterInputStream {

  private int limit;
  private int count;
  private int markedCount;

  public ReadLimitInputStream(InputStream in, int limit) {
    super(in);
    this.limit = limit;
    this.count = 0;
    this.markedCount = 0;
  }

  @Override
  public int read() throws IOException {
    int result = -1;
    if (count < limit) {
      result = in.read();
      ++count;
    }
    return result;
  }

  @Override
  public int read(byte buffer[]) throws IOException {
    if (count >= limit) {
      return -1;
    }
    int length = Math.min(buffer.length, limit - count);
    int bytesRead = in.read(buffer, 0, length);
    if (bytesRead > 0) {
      count += bytesRead;
    }
    return bytesRead;
  }

  @Override
  public int read(byte buffer[], int off, int len) throws IOException {
    if (count >= limit) {
      return -1;
    }
    len = Math.min(len, limit - count);
    int bytesRead = in.read(buffer, off, len);
    if (bytesRead > 0) {
      count += bytesRead;
    }
    return bytesRead;
  }

  @Override
  public long skip(long n) throws IOException {
    if (count >= limit) {
      return 0;
    }
    n = Math.min(n, (limit - count));
    n = in.skip(n);
    count += n;
    return n;
  }

  @Override
  public int available() throws IOException {
    if (count >= limit) {
      return 0;
    }
    int result = in.available();
    return Math.min(result, limit - count);
  }

  @Override
  public void close() throws IOException {
    in.close();
  }

  @Override
  public synchronized void mark(int readlimit) {
    in.mark(readlimit);
    markedCount = count;
  }

  @Override
  public synchronized void reset() throws IOException {
    in.reset();
    count = markedCount;
  }

  @Override
  public boolean markSupported() {
    return in.markSupported();
  }

}
