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

import java.io.IOException;
import java.nio.InvalidMarkException;

/**
 * The {@link AbstractSeekableInputStream} is an abstract base class for
 * implementing the {@link SeekableInputStream} interface.
 *
 * @author Haixing Hu
 */
public abstract class AbstractSeekableInputStream extends SeekableInputStream {

  protected long markPos;
  protected int markLimit;

  protected AbstractSeekableInputStream() {
    markPos = - 1;
    markLimit = - 1;
  }

  @Override
  public int available() throws IOException {
    final long result = length() - position();
    if (result > Integer.MAX_VALUE) {
      return Integer.MAX_VALUE;
    } else {
      return (int) result;
    }
  }

  @Override
  public long skip(final long n) throws IOException {
    final long len = length();
    final long pos = position();
    long skipped = len - pos;
    if (skipped > n) {
      skipped = n;
    }
    final long newpos = pos + skipped;
    seek(newpos);
    if (markPos >= 0) {
      if ((newpos < markPos) || (markPos < (newpos - markLimit))) {
        //  invalid the mark
        markPos = -1;
        markLimit = -1;
      }
    }
    return skipped;
  }

  @Override
  public boolean markSupported() {
    return true;
  }

  @Override
  public void mark(final int readLimit) {
    try {
      markPos = position();
      markLimit = readLimit;
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void reset() throws IOException {
    if (markPos < 0) {
      throw new InvalidMarkException();
    }
    final long pos = position();
    if ((markPos + markLimit) < pos) {
      markPos = - 1;
      throw new InvalidMarkException();
    }
    seek(markPos);
  }
}
