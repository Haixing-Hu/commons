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
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import static com.github.haixing_hu.CommonsMessages.UNSUPPORTED_CHECKSUM_ALGORITHM;

/**
 * Read bytes through to a primary {@link Input}, computing checksum as it
 * goes. Note that you cannot use seek().
 *
 * @author Haixing Hu
 */
public final class ChecksumInputStream extends FilterInputStream {

  private final ChecksumAlgorithm algorithm;
  private Checksum                digest;

  public ChecksumInputStream(final InputStream in, final ChecksumAlgorithm algorithm) {
    super(in);
    this.algorithm = algorithm;
    switch (algorithm) {
      case ADLER32:
        this.digest = new Adler32();
        break;
      case CRC32:
        this.digest = new CRC32();
        break;
      default:
        throw new IllegalArgumentException(UNSUPPORTED_CHECKSUM_ALGORITHM);
    }
  }

  public ChecksumAlgorithm algorithm() {
    return algorithm;
  }

  @Override
  public int available() throws IOException {
    return in.available();
  }

  @Override
  public int read() throws IOException {
    final int ch = in.read();
    if (ch >= 0) {
      digest.update(ch);
    }
    return ch;
  }

  @Override
  public int read(final byte[] buffer, final int offset, final int len)
      throws IOException {
    final int n = in.read(buffer, offset, len);
    if (n >= 0) {
      digest.update(buffer, offset, n);
    }
    return n;
  }

  @Override
  public long skip(final long n) throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean markSupported() {
    return false;
  }

  @Override
  public void mark(final int readlimit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void reset() throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void close() throws IOException {
    in.close();
  }

  public long getChecksum() {
    return digest.getValue();
  }

  public boolean verify() throws IOException {
    final long expectedChecksum = InputUtils.readLong(in);
    return (expectedChecksum == digest.getValue());
  }

  public boolean verify(final long expectedChecksum) {
    return (expectedChecksum == digest.getValue());
  }
}
