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
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A {@link RamFile} object represents a virtual file in RAM.
 *
 * Note that this class is NOT thread safe.
 *
 * @author Haixing Hu
 */
public final class RamFile implements Serializable {

  private static final long   serialVersionUID = - 1964074757799348292L;

  /**
   * The size in bytes of the blocks in the RamFile.
   */
  public static final int BLOCK_SIZE           = 4096;

  private final ArrayList<byte[]> blocks;       // the data in a RamFile is stored in blocks
  private long              length;       // the length of this RamFile
  private long              lastModified; // the last modified time of this RamFile

  public RamFile() {
    blocks = new ArrayList<byte[]>();
    length = 0;
    lastModified = System.currentTimeMillis();
  }

  public long getLength() {
    return length;
  }

  public void setLength(final long length) {
    this.length = length;
  }

  public long getLastModified() {
    return lastModified;
  }

  public void setLastModified(final long lastModified) {
    this.lastModified = lastModified;
  }

  public long getOccupiedSize() {
    return ((long) blocks.size()) * ((long) BLOCK_SIZE);
  }

  public byte[] addBlock() {
    final byte[] block = new byte[BLOCK_SIZE];
    synchronized (this) {
      blocks.add(block);
    }
    return block;
  }

  public byte[] getBlock(final int blockIndex) {
    return blocks.get(blockIndex);
  }

  public int getBlockCount() {
    return blocks.size();
  }

  /**
   * Copy the current contents of this {@link RamFile} to a specified
   * {@link Output}.
   *
   * @param out
   *          a specified {@link Output} where to write the data.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public void writeTo(final OutputStream out) throws IOException {
    long pos = 0;
    int blockIndex = 0;
    while (pos < length) {
      int bytes = BLOCK_SIZE;
      final long nextPos = pos + bytes;
      if (nextPos > length) { // at the last buffer
        bytes = (int) (length - pos);
      }
      final byte[] block = getBlock(blockIndex++);
      out.write(block, 0, bytes);
      pos = nextPos;
    }
  }

}
