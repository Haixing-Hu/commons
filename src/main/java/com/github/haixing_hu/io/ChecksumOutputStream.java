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

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import static com.github.haixing_hu.CommonsMessages.UNSUPPORTED_CHECKSUM_ALGORITHM;


/**
 * Writes bytes through to a primary {@link Output}, computing checksum as it goes.
 * Note that you cannot use seek().
 *
 * @author Haixing Hu
 */
public class ChecksumOutputStream extends FilterOutputStream {

  private final ChecksumAlgorithm algorithm;
  private Checksum                digest;

  public ChecksumOutputStream(final OutputStream out, final ChecksumAlgorithm algorithm) {
    super(out);
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
  public void write(final int ch) throws IOException {
    out.write(ch);
    digest.update(ch);
  }

  @Override
  public void write(final byte[] buffer, final int off, final int len)
      throws IOException {
    out.write(buffer, off, len);
    digest.update(buffer, off, len);
  }

  @Override
  public void flush() throws IOException {
    out.flush();
  }

  @Override
  public void close() throws IOException {
    out.close();
  }

  public long getChecksum() {
    return digest.getValue();
  }

  public void sign() throws IOException {
    final long signature = digest.getValue();
    OutputUtils.writeLong(out, signature);
  }
}
