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

package com.github.haixing_hu.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.github.haixing_hu.lang.ArrayUtils;
import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.text.CharsetUtils;

/**
 * Simple utility class providing static methods to compress and decompress
 * binary data. This class uses {@link Deflater} and {@link Inflater} classes to
 * compress and decompress.
 *
 * @author Haixing Hu
 */
public final class CompressionTools {

  public static final int BUFFER_SIZE = 4096;

  /**
   * Compresses the specified byte range using the specified compressionLevel
   * (constants are defined in {@link Deflater} class).
   *
   * @param data
   *          the data to be compressed.
   * @param offset
   *          the offset of the byte array where to start compression.
   * @param nBytes
   *          the number of bytes to be compressed.
   * @param compressionLevel
   *          the level of compression. It should be a constant defined in
   *          {@link Deflater} class.
   * @return the compressed byte array.
   */
  public static byte[] compress(final byte[] data, final int offset,
      final int nBytes, final int compressionLevel) {
    if (nBytes == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    }
    // Create an expandable byte array to hold the compressed data. You cannot
    // use an array that's the same size as the original because there is no
    // guarantee that the compressed data will be smaller than the uncompressed
    // data.
    final ByteArrayOutputStream bos = new ByteArrayOutputStream(nBytes);
    final Deflater compressor = new Deflater();
    try {
      compressor.setLevel(compressionLevel);
      compressor.setInput(data, offset, nBytes);
      compressor.finish();
      // Compress the data
      final byte[] buffer = new byte[BUFFER_SIZE];
      while (! compressor.finished()) {
        final int count = compressor.deflate(buffer);
        bos.write(buffer, 0, count);
      }
    } finally {
      compressor.end();
    }
    return bos.toByteArray();
  }

  public static byte[] compressString(final String str, final int compressionLevel) {
    if (str.length() == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    } else {
      final byte[] bytes = str.getBytes(CharsetUtils.UTF_8);
      return compress(bytes, 0, bytes.length, compressionLevel);
    }
  }

  /**
   * Decompress the byte array previously returned by compress.
   *
   * @param data
   *    the data to be decompressed.
   * @param offset
   *          the offset of the byte array where to start decompression.
   * @param nBytes
   *          the number of bytes to be decompressed.
   * @return the decompressed result of the original values.
   * @throws DataFormatException
   *    if the compressed data has errors.
   */
  public static byte[] decompress(final byte[] data, final int offset, final int nBytes)
      throws DataFormatException {
    if (nBytes == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    }
    final ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
    final Inflater decompressor = new Inflater();
    try {
      decompressor.setInput(data, offset, nBytes);
      // Decompress the data
      final byte[] buffer = new byte[BUFFER_SIZE];
      while (! decompressor.finished()) {
        final int count = decompressor.inflate(buffer);
        bos.write(buffer, 0, count);
      }
    } finally {
      decompressor.end();
    }
    return bos.toByteArray();
  }

  public static String decompressString(final byte[] data, final int offset, final int nBytes)
      throws DataFormatException {
    if (nBytes == 0) {
      return StringUtils.EMPTY;
    } else {
      final byte[] bytes = decompress(data, offset, nBytes);
      return new String(bytes, CharsetUtils.UTF_8);
    }
  }
}
