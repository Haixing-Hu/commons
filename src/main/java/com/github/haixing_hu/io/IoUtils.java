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

import static com.github.haixing_hu.CommonsMessages.BUFFER_SIZE_MUST_POSITIVE;
import static com.github.haixing_hu.CommonsMessages.EXCEPTION_THROW_DURING_CLOSING;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;

import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.haixing_hu.lang.ArrayUtils;
import com.github.haixing_hu.lang.SystemUtils;
import com.github.haixing_hu.util.expand.ExpansionPolicy;

/**
 * This class provides common file operation functions.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class IoUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(IoUtils.class);

  /**
   * The default buffer size to use.
   */
  public static final int BUFFER_SIZE           = 16384;


  private static final String NEGATIVE_MAX_LENGTH =
    "The maxLength argument can't be negative.";

  /**
   * Unconditionally close a {@code Closeable} object.
   * <p>
   * Equivalent to {@link Closeable#close()}, except any exceptions will be
   * ignored and logged as an warning message. This is typically used in finally
   * blocks.
   *
   * @param closeable
   *          the Closeable object to close, may be null or already closed.
   */
  public static void closeQuietly(final Closeable closeable) {
    try {
      if (closeable != null) {
        closeable.close();
      }
    } catch (final IOException e) {
      final Logger logger = LoggerFactory.getLogger(IoUtils.class);
      logger.error(EXCEPTION_THROW_DURING_CLOSING, e);
    }
  }

  /**
   * Unconditionally close a {@code Socket} object.
   * <p>
   * Equivalent to {@link Socket#close()}, except any exceptions will be
   * ignored and logged as an warning message. This is typically used in finally
   * blocks.
   *
   * @param socket
   *          the socket to close, may be null or already closed.
   */
  public static void closeQuietly(final Socket socket) {
    try {
      if (socket != null) {
        socket.close();
      }
    } catch (final IOException e) {
      final Logger logger = LoggerFactory.getLogger(IoUtils.class);
      logger.error(EXCEPTION_THROW_DURING_CLOSING, e);
    }
  }

  /**
   * Try to read a specified number of bytes from an input stream.
   * <p>
   * This function will try to read from the input stream, until either the
   * specified number of bytes has been read, or the end-of-file has been met.
   *
   * @param input
   *          the {@link InputStream} to read from.
   * @param bytes
   *          the number of bytes has to read.
   * @param buffer
   *          the buffer used to store the bytes, which must be larger enough to
   *          hold the bytes.
   * @return the actual number of bytes has been read.
   * @throws IOException
   *           If any I/O error occurred.
   */
  public static int readFully(InputStream input, int bytes, byte[] buffer)
      throws IOException {
    if (bytes <= 0) {
      return 0;
    }
    if (bytes > buffer.length) {
      bytes = buffer.length;
    }
    int count = 0;
    while (count < bytes) {
      int n = input.read(buffer, count, bytes - count);
      if (n < 0) {
        return count;
      }
      count += n;
    }
    return count;
  }

  /**
   * Try to read a specified number of bytes from an input stream.
   * <p>
   * This function will try to read from the input stream, until either the
   * specified number of bytes has been read, or the end-of-file has been met.
   *
   * @param input
   *          the {@link InputStream} to read from.
   * @param bytes
   *          the number of bytes has to read.
   * @return the bytes has been read.
   * @throws EOFException
   *           If the end-of-file has been met before reading the desired number
   *           of bytes.
   * @throws IOException
   *           If any I/O error occurred.
   */
  public static byte[] readFully(InputStream input, int bytes)
        throws IOException {
    if (bytes <= 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    }
    byte[] buffer = new byte[bytes];
    int count = 0;
    while (count < bytes) {
      int n = input.read(buffer, count, bytes - count);
      if (n < 0) {
        throw new EOFException();
      }
      count += n;
    }
    return buffer;
  }

  /**
   * Copy bytes from an {@link InputStream} to an {@link OutputStream}.
   * <p>
   * This method buffers the input internally, so there is no need to use a
   * {@link BufferedInputStream}.
   * <p>
   * Note that after calling this function, the {@link OutputStream} was
   * NOT flushed NOR closed, instead, it MUST be flushed or closed by the
   * caller.
   * <p>
   * TODO: add the supporting of a progress displaying call-back function.
   *
   * @param input
   *          the {@link InputStream} to read from.
   * @param maxBytes
   *          the maximum number of bytes to be copied. It could be
   *          {@code Long.MAX_VALUE}, indicating no limit.
   * @param output
   *          the {@link OutputStream} to write to.
   * @return the number of bytes copied, which may be larger than 2 GB.
   * @throws IOException
   *           if an I/O error occurs
   * @see #copy(InputStream, long OutputStream, byte[])
   */
  public static long copy(final InputStream input, final long maxBytes,
      final OutputStream output) throws IOException {
    final byte[] buffer = new byte[BUFFER_SIZE];
    return copy(input, maxBytes, output, buffer);
  }

  /**
   * Copy bytes from an {@link InputStream} to an {@link OutputStream}.
   *
   * This method buffers the input internally, so there is no need to use a
   * {@link BufferedInputStream}.
   *
   * Note that after calling this function, the {@link OutputStream} was NOT
   * flushed NOR closed, instead, it MUST be flushed or closed by the caller.
   *
   * TODO: add the supporting of a progress displaying call-back function.
   *
   * @param input
   *          the {@link InputStream} to read from
   * @param output
   *          the {@link OutputStream} to write to.
   * @param maxBytes
   *          the maximum number of bytes to be copied. It could be
   *          {@code Long.MAX_VALUE}, indicating no limit.
   * @param buffer
   *          a buffer used for copying.
   * @return the number of bytes copied, which may be larger than 2 GB.
   * @throws IOException
   *           if an I/O error occurs
   * @see #copy(InputStream, long, OutputStream)
   */
  public static long copy(final InputStream input, final long maxBytes,
      final OutputStream output, final byte[] buffer) throws IOException {
    LOGGER.trace("Copying at most {} bytes.", maxBytes);
    if (maxBytes <= 0) {
      return 0;
    }
    if (buffer.length == 0) {
      throw new IllegalArgumentException(BUFFER_SIZE_MUST_POSITIVE);
    }
    long count = 0;
    while (count < maxBytes) {
      LOGGER.trace("{} bytes copied.", count);
      int n;
      final long remained = maxBytes - count;
      if (remained < buffer.length) {
        n = (int)remained;
      } else {
        n = buffer.length;
      }
      LOGGER.trace("Try to copy {} bytes.", n);
      n = input.read(buffer, 0, n);
      LOGGER.trace("Actually copied {} bytes.", n);
      if (n < 0) {  // EOF
        break;
      } else if (n > 0) {
        output.write(buffer, 0, n);
        count += n;
      }
    }
    LOGGER.trace("Totally {} bytes copied.", count);
    return count;
  }

  /**
   * Copy bytes from a {@code Reader} to an {@code Writer}.
   *
   * This method buffers the input internally, so there is no need to use a
   * {@code BufferedReader}.
   *
   * Note that after calling this function, the {@code Writer} was NOT
   * flushed NOR closed, instead, it MUST be flushed or closed by the caller.
   *
   * TODO: add the supporting of a progress displaying call-back function.
   *
   * @param input
   *          the {@code Reader} to read from.
   * @param maxChars
   *          the maximum number of characters to be copied. It count be
   *          Long.MAX_VALUE, indicating no limit.
   * @param output
   *          the {@code Writer} to write to.
   * @return the number of characters copied, which may be larger than 2 GB.
   * @throws IOException
   *           if an I/O error occurs
   * @see #copy(Reader, long, Writer, char[])
   */
  public static long copy(final Reader input, final long maxChars, final Writer output)
      throws IOException {
    final char[] buffer = new char[BUFFER_SIZE];
    return copy(input, maxChars, output, buffer);
  }

  /**
   * Copy bytes from a {@code Reader} to an {@code Writer}.
   *
   * This method buffers the input internally, so there is no need to use a
   * {@code BufferedReader}.
   *
   * Note that after calling this function, the {@code Writer} was NOT
   * flushed NOR closed, instead, it MUST be flushed or closed by the caller.
   *
   * TODO: add the supporting of a progress displaying call-back function.
   *
   * @param input
   *          the {@code Reader} to read from.
   * @param maxChars
   *          the maximum number of characters to be copied. It count be
   *          Long.MAX_VALUE, indicating no limit.
   * @param output
   *          the {@code Writer} to write to.
   * @param buffer
   *          a buffer used for copying.
   * @return the number of characters copied, which may be larger than 2 GB.
   * @throws IOException
   *           if an I/O error occurs
   * @see #copy(Reader, long, Writer)
   */
  public static long copy(final Reader input, final long maxChars,
      final Writer output, final char[] buffer) throws IOException {
    LOGGER.trace("Copying at most {} characters.", maxChars);
    if (maxChars <= 0) {
      return 0;
    }
    if (buffer.length == 0) {
      throw new IllegalArgumentException(BUFFER_SIZE_MUST_POSITIVE);
    }
    long count = 0;
    while (count < maxChars) {
      LOGGER.trace("{} characters copied.", count);
      int n;
      final long remained = maxChars - count;
      if (remained < buffer.length) {
        n = (int)remained;
      } else {
        n = buffer.length;
      }
      LOGGER.trace("Try to copy {} characters.", n);
      n = input.read(buffer, 0, n);
      LOGGER.trace("Actually copied {} characters.", n);
      if (n < 0) {  // EOF
        break;
      } else if (n > 0) {
        output.write(buffer, 0, n);
        count += n;
      }
    }
    LOGGER.trace("Totally {} characters copied.", count);
    return count;
  }

  /**
   * Gets the first few bytes of an {@link InputStream}.
   *
   * NOTE: the function does not close the {@link InputStream}.
   *
   * @param input
   *          an input stream.
   * @param maxBytes
   *          the maximum number of bytes to get. If it is
   *          {@link Integer#MAX_VALUE}, there is no limit on the number of
   *          bytes to get (except the memory limit).
   * @return the first few bytes of the {@link InputStream}; if the
   *         {@link InputStream}> has less than maxLength bytes, all the bytes
   *         of the {@link InputStream} were get and returned; otherwise, the
   *         first {@code maxLength} bytes of the {@link InputStream} were
   *         get and returned; if the {@link InputStream} has no content because
   *         of the {@code EOF}, a byte array of length 0 is returned.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static byte[] getBytes(final InputStream input, final int maxBytes)
      throws IOException {
    LOGGER.trace("Gets at most {} bytes.", maxBytes);
    if (maxBytes < 0) {
      throw new IllegalArgumentException(NEGATIVE_MAX_LENGTH);
    }
    byte[] buffer = new byte[Math.min(BUFFER_SIZE, maxBytes)];
    int hasRead = 0;
    int remained = maxBytes;
    final ExpansionPolicy expansionPolicy = ExpansionPolicy.getDefault();
    while (remained > 0) {
      LOGGER.trace("{} bytes has been get.", hasRead);
      if (hasRead == buffer.length) { // buffer is full, expand it
        buffer = expansionPolicy.expand(buffer, hasRead, hasRead + 1);
      }
      final int toRead = Math.min(buffer.length - hasRead, remained);
      LOGGER.trace("Try to get {} bytes.", toRead);
      final int n = input.read(buffer, hasRead, toRead);
      LOGGER.trace("Actually get {} bytes.", n);
      if (n < 0) { // EOF
        break;
      }
      hasRead += n;
      remained -= n;
    }
    if (hasRead == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    } else if (hasRead < buffer.length) {
      buffer = expansionPolicy.resize(buffer, hasRead, hasRead);
      return buffer;
    } else {
      assert (hasRead == buffer.length);
      return buffer;
    }
  }

  /**
   * Get the contents of an {@code InputStream} as a {@code byte[]}.
   *
   * This method buffers the input internally, so there is no need to use a
   * {@code BufferedInputStream}.
   *
   * @param input
   *          the {@code InputStream} to read from.
   * @return the requested byte array
   * @throws IOException
   *           if an I/O error occurs
   */
  public static byte[] toByteArray(final InputStream input) throws IOException {
    final ByteArrayOutputStream output = new ByteArrayOutputStream();
    copy(input, Long.MAX_VALUE, output);
    return output.toByteArray();
  }

  /**
   * Get the contents of a {@code Reader} as a character array.
   *
   * This method buffers the input internally, so there is no need to use a
   * {@code BufferedReader}.
   *
   * @param input
   *          the {@code Reader} to read from
   * @return the requested character array
   * @throws NullPointerException
   *           if the input is null
   * @throws IOException
   *           if an I/O error occurs
   */
  public static char[] toCharArray(final Reader input) throws IOException {
    final CharArrayWriter output = new CharArrayWriter();
    copy(input, Long.MAX_VALUE, output);
    return output.toCharArray();
  }

  /**
   * Get the contents of a {@code Reader} as a String.
   *
   * This method buffers the input internally, so there is no need to use a
   * {@code BufferedReader}.
   *
   * @param input
   *          the {@code Reader} to read from
   * @return the requested String
   * @throws IOException
   *           if an I/O error occurs
   */
  public static String toString(final Reader input) throws IOException {
    final StringWriter output = new StringWriter();
    copy(input, Long.MAX_VALUE, output);
    return output.toString();
  }

  /**
   * Get the contents of a {@code Reader} as a list of Strings, one entry
   * per line.
   *
   * This method buffers the input internally, so there is no need to use a
   * {@code BufferedReader}.
   *
   * Note that the caller MUST close the reader by itself.
   *
   * @param input
   *          the {@code Reader} to read from, not null
   * @return the array list of Strings. It never be null, but could be empty.
   * @throws IOException
   *           if an I/O error occurs
   */
  public static ArrayList<String> readLines(final Reader input) throws IOException {
    final BufferedReader reader = new BufferedReader(input);
    final ArrayList<String> result = new ArrayList<String>();
    String line = reader.readLine();
    while (line != null) {
      result.add(line);
      line = reader.readLine();
    }
    return result;
  }

  /**
   * Writes the {@code toString()} value of each item in a collection to a
   * {@code Writer} line by line, using the specified line ending.
   *
   * Note that after calling this function, the {@code OutputStream} was
   * NOT flushed NOR closed, instead, it MUST be flushed or closed by the
   * caller.
   *
   * @param lines
   *          the lines to write, null entries produce blank lines
   * @param lineEnding
   *          the line separator to use, null is system default
   * @param writer
   *          the {@code Writer} to write to, not null, not closed
   * @throws IOException
   *           if an I/O error occurs
   */
  public static void writeLines(final Iterable<String> lines, String lineEnding,
      final Writer writer) throws IOException {
    if (lines == null) {
      return;
    }
    if (lineEnding == null) {
      lineEnding = SystemUtils.LINE_SEPARATOR;
    }
    for (final String line : lines) {
      if ((line != null) && (line.length() > 0)) {
        writer.write(line);
      }
      writer.write(lineEnding);
    }
  }

  /**
   * Compare the contents of two input streams lexicographically.
   *
   * This method buffers the input internally using
   * {@code BufferedInputStream} if they are not already buffered.
   *
   * Note that after calling this function, the two input streams were NOT
   * closed by this function, instead, they MUST be closed by the caller.
   *
   * @param input1
   *          the first stream
   * @param input2
   *          the second stream
   * @return An integer less than, equal to or greater than 0, if the content of
   *         the first input stream compares lexicographically less than, equal
   *         to, or greater than the content of the second input stream.
   * @throws IOException
   *           if an I/O error occurs
   */
  public static int compareContent(InputStream input1, InputStream input2)
      throws IOException {
    if (! (input1 instanceof BufferedInputStream)) {
      input1 = new BufferedInputStream(input1);
    }
    if (! (input2 instanceof BufferedInputStream)) {
      input2 = new BufferedInputStream(input2);
    }
    int ch1 = input1.read();
    while (ch1 != -1) {
      final int ch2 = input2.read();
      if (ch1 != ch2) {
        if (ch2 == -1) {
          // input2 is shorter than input1
          return +1;
        } else {
          return (ch1 - ch2);
        }
      }
      ch1 = input1.read();
    }
    if (input2.read() == -1) {
      return 0;
    } else {
      // input2 is longer than input1
      return -1;
    }
  }

  /**
   * Compare the contents of two readers lexicographically.
   *
   * This method buffers the input internally using {@code BufferedReader}
   * if they are not already buffered.
   *
   * Note that after calling this function, the two readers were NOT closed by
   * this function, instead, they MUST be closed by the caller.
   *
   * @param input1
   *          the first reader
   * @param input2
   *          the second reader
   * @return An integer less than, equal to or greater than 0, if the content of
   *         the first reader compares lexicographically less than, equal to, or
   *         greater than the content of the second reader.
   * @throws IOException
   *           if an I/O error occurs
   */
  public static int compareContent(Reader input1, Reader input2)
      throws IOException {
    if (! (input1 instanceof BufferedReader)) {
      input1 = new BufferedReader(input1);
    }
    if (! (input2 instanceof BufferedReader)) {
      input2 = new BufferedReader(input2);
    }
    int ch1 = input1.read();
    while (ch1 != -1) {
      final int ch2 = input2.read();
      if (ch1 != ch2) {
        if (ch2 == -1) {
          // input2 is shorter than input1
          return +1;
        } else {
          return (ch1 - ch2);
        }
      }
      ch1 = input1.read();
    }
    if (input2.read() == -1) {
      return 0;
    } else {
      // input2 is longer than input1
      return -1;
    }
  }
}
