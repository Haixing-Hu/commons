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

package com.github.haixing_hu.util.buffer;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;

import javax.annotation.Nullable;

import com.github.haixing_hu.io.serialize.BinarySerialization;
import com.github.haixing_hu.lang.Argument;
import com.github.haixing_hu.lang.ArrayUtils;
import com.github.haixing_hu.lang.Assignable;
import com.github.haixing_hu.lang.CharUtils;
import com.github.haixing_hu.lang.Cloneable;
import com.github.haixing_hu.lang.Comparison;
import com.github.haixing_hu.lang.Equality;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.lang.Size;
import com.github.haixing_hu.lang.Swapable;
import com.github.haixing_hu.text.Ascii;
import com.github.haixing_hu.util.expand.ExpansionPolicy;

import static com.github.haixing_hu.lang.Argument.*;
import static com.github.haixing_hu.lang.ArrayUtils.EMPTY_CHAR_ARRAY;

/**
 * A simple auto-expansion buffer for <code>char</code> values.
 *
 * @author Haixing Hu
 */
public final class CharBuffer implements Swapable<CharBuffer>,
    Assignable<CharBuffer>, Cloneable<CharBuffer>, Comparable<CharBuffer>,
    Serializable {

  private static final long serialVersionUID = - 5150163651924701441L;

  static {
    BinarySerialization.register(CharBuffer.class,
        CharBufferBinarySerializer.INSTANCE);
  }

  /**
   * The buffer used to store the characters.
   */
  protected char[] buffer;

  /**
   * The length of the array stored in this character buffer.
   */
  protected int length;

  /**
   * The expansion policy used by this buffer.
   */
  protected transient ExpansionPolicy expansionPolicy;

  public CharBuffer() {
    buffer = ArrayUtils.EMPTY_CHAR_ARRAY;
    length = 0;
    expansionPolicy = ExpansionPolicy.getDefault();
  }

  public CharBuffer(final ExpansionPolicy expansionPolicy) {
    this.buffer = ArrayUtils.EMPTY_CHAR_ARRAY;
    this.length = 0;
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
  }

  public CharBuffer(final char[] buffer) {
    this.buffer = requireNonNull("buffer", buffer);
    this.length = 0;
    this.expansionPolicy = ExpansionPolicy.getDefault();
  }

  public CharBuffer(final char[] buffer, final ExpansionPolicy expansionPolicy) {
    this.buffer = requireNonNull("buffer", buffer);
    this.length = 0;
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
  }

  public CharBuffer(final int bufferSize) {
    requireGreaterEqual("bufferSize", bufferSize, "zero", 0);
    if (bufferSize == 0) {
      this.buffer = EMPTY_CHAR_ARRAY;
    } else {
      this.buffer = new char[bufferSize];
    }
    this.length = 0;
    this.expansionPolicy = ExpansionPolicy.getDefault();
  }

  public CharBuffer(final int bufferSize, final ExpansionPolicy expansionPolicy) {
    requireGreaterEqual("bufferSize", bufferSize, "zero", 0);
    if (bufferSize == 0) {
      this.buffer = EMPTY_CHAR_ARRAY;
    } else {
      this.buffer = new char[bufferSize];
    }
    this.length = 0;
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
  }

  public CharBuffer(final String value) {
    this.buffer = value.toCharArray();
    this.length = buffer.length;
    this.expansionPolicy = ExpansionPolicy.getDefault();
  }

  public CharBuffer(final String value, final ExpansionPolicy expansionPolicy) {
    this.buffer = value.toCharArray();
    this.length = buffer.length;
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
  }

  public CharBuffer(final char ch) {
    this.buffer = new char[1];
    this.buffer[0] = ch;
    this.length = 1;
    this.expansionPolicy = ExpansionPolicy.getDefault();
  }

  public CharBuffer(final char ch, final ExpansionPolicy expansionPolicy) {
    this.buffer = new char[1];
    this.buffer[0] = ch;
    this.length = 1;
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
  }

  public ExpansionPolicy getExpansionPolicy() {
    return expansionPolicy;
  }

  public void setExpansionPolicy(final ExpansionPolicy expansionPolicy) {
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
  }

  public long bytes() {
    return Size.REFERENCE + 4 + (buffer.length * 2);
  }

  public char at(final int i) {
    return buffer[i];
  }

  public char[] buffer() {
    return buffer;
  }

  public int length() {
    return length;
  }

  public int capacity() {
    return buffer.length;
  }

  public boolean isEmpty() {
    return length == 0;
  }

  public boolean isFull() {
    return length == buffer.length;
  }

  public void clear() {
    length = 0;
  }

  public int room() {
    return buffer.length - length;
  }

  public void setLength(final int newLength) {
    if (newLength < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength);
    }
    length = newLength;
  }

  public void append(final char ch) {
    if (length >= buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, length + 1);
    }
    buffer[length++] = ch;
  }

  public void append(@Nullable final char[] array) {
    if ((array == null) || (array.length == 0)) {
      return;
    }
    final int newLength = length + array.length;
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength);
    }
    System.arraycopy(array, 0, buffer, length, array.length);
    length = newLength;
  }

  public void append(@Nullable final char[] array, final int off, final int n) {
    if (array == null) {
      return;
    }
    Argument.checkBounds(off, n, array.length);
    if (n == 0) {
      return;
    }
    final int newLength = length + n;
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength);
    }
    System.arraycopy(array, off, buffer, length, n);
    length = newLength;
  }

  public void append(@Nullable final String str) {
    if (str == null) {
      return;
    }
    final int strLen = str.length();
    if (strLen == 0) {
      return;
    }
    final int newLength = length + strLen;
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength);
    }
    str.getChars(0, strLen, buffer, length);
    length = newLength;
  }

  public void append(@Nullable final String str, final int off, final int n) {
    if ((str == null) || (n == 0)) {
      return;
    }
    Argument.checkBounds(off, n, str.length());
    final int newLength = length + n;
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength);
    }
    str.getChars(off, off + n, buffer, length);
    length = newLength;
  }

  public void append(@Nullable final CharBuffer array) {
    if ((array == null) || (array.length == 0)) {
      return;
    }
    final int newLength = length + array.length;
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength);
    }
    System.arraycopy(array.buffer, 0, buffer, length, array.length);
    length = newLength;
  }

  public void append(@Nullable final CharBuffer array, final int off,
      final int n) {
    if (array == null) {
      return;
    }
    Argument.checkBounds(off, n, array.length);
    if (n == 0) {
      return;
    }
    final int newLength = length + n;
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength);
    }
    System.arraycopy(array.buffer, off, buffer, length, n);
    length = newLength;
  }

  public int append(@Nullable final Reader reader, final int limit)
      throws IOException {
    if ((reader == null) || (limit <= 0)) {
      return 0;
    }
    int remained = limit;
    while (remained > 0) {
      if (length == buffer.length) {
        final int newLength = length + ExpansionPolicy.getInitialCapacity();
        buffer = expansionPolicy.expand(buffer, length, newLength);
      }
      final int space = buffer.length - length;
      final int charsToRead = (space < remained ? space : remained);
      final int n = reader.read(buffer, length, charsToRead);
      if (n == - 1) {
        break; // EOF
      }
      length += n;
      remained -= n;
    }
    return limit - remained;
  }

  /**
   * Appends <code>n</code> bytes to this buffer from the given source array .
   * The capacity of the buffer is increased, if necessary, to accommodate all
   * bytes.
   * <p>
   * NOTE: The bytes are converted to chars using simple cast.
   * </p>
   *
   * @param array
   *          the bytes to be appended.
   */
  public void append(@Nullable final byte[] array) {
    if ((array == null) || (array.length == 0)) {
      return;
    }
    final int newLength = length + array.length;
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength);
    }
    for (int i1 = 0, i2 = length; i2 < newLength; ++i1, ++i2) {
      buffer[i2] = (char) (array[i1] & 0xFF);
    }
    length = newLength;
  }

  /**
   * Appends <code>n</code> bytes to this buffer from the given source array
   * starting at current <code>off</code>. The capacity of the buffer is
   * increased, if necessary, to accommodate all <code>n</code> bytes.
   * <p>
   * NOTE: The bytes are converted to chars using simple cast.
   * </p>
   *
   * @param array
   *          the bytes to be appended.
   * @param off
   *          the current of the first byte to append.
   * @param n
   *          the number of bytes to append.
   * @throws IndexOutOfBoundsException
   *           if <code>off</code> is out of range, <code>n</code> is negative,
   *           or <code>off</code> + <code>n</code> is out of range.
   */
  public void append(@Nullable final byte[] array, final int off, final int n) {
    if (array == null) {
      return;
    }
    Argument.checkBounds(off, n, array.length);
    if (n == 0) {
      return;
    }
    final int newLength = length + n;
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength);
    }
    for (int i1 = off, i2 = length; i2 < newLength; ++i1, ++i2) {
      buffer[i2] = (char) (array[i1] & 0xFF);
    }
    length = newLength;
  }

  /**
   * Appends <code>n</code> bytes to this buffer from the given source char
   * array buffer. The capacity of the buffer is increased if necessary to
   * accommodate all chars.
   * <p>
   * NOTE: The bytes are converted to chars using simple cast.
   * </p>
   *
   * @param array
   *          the bytes to be appended.
   */
  public void append(@Nullable final ByteBuffer array) {
    if (array == null) {
      return;
    }
    append(array.buffer(), 0, array.length());
  }

  /**
   * Appends <code>n</code> bytes to this buffer from the given source char
   * array buffer starting at current <code>off</code>. The capacity of the
   * buffer is increased if necessary to accommodate all <code>n</code> chars.
   * <p>
   * NOTE: The bytes are converted to chars using simple cast.
   * </p>
   *
   * @param array
   *          the bytes to be appended.
   * @param off
   *          the current of the first byte to append.
   * @param n
   *          the number of bytes to append.
   * @throws IndexOutOfBoundsException
   *           if <code>off</code> if out of range, <code>n</code> is negative,
   *           or <code>off</code> + <code>n</code> is out of range.
   */
  public void append(@Nullable final ByteBuffer array, final int off,
      final int n) {
    if (array == null) {
      return;
    }
    append(array.buffer(), off, n);
  }

  /**
   * Returns the current within this buffer of the first occurrence of the
   * specified character, starting the search at the specified
   * <code>beginIndex</code> and finishing at <code>endIndex</code>. If no such
   * character occurs in this buffer within the specified bounds,
   * <code>-1</code> is returned.
   * <p>
   * There is no restriction on the value of <code>beginIndex</code> and
   * <code>endIndex</code>. If <code>beginIndex</code> is negative, it has the
   * same effect as if it were zero. If <code>endIndex</code> is greater than
   * {@link #length()}, it has the same effect as if it were {@link #length()}.
   * If the <code>beginIndex</code> is greater than the <code>endIndex</code>,
   * <code>-1</code> is returned.
   *
   * @param ch
   *          the char to search for.
   * @param beginIndex
   *          the current to start the search from.
   * @param endIndex
   *          the current to finish the search at.
   * @return the current of the first occurrence of the character in the buffer
   *         within the given bounds, or <code>-1</code> if the character does
   *         not occur.
   */
  public int indexOf(final int ch, int beginIndex, int endIndex) {
    if (beginIndex < 0) {
      beginIndex = 0;
    }
    if (endIndex > length) {
      endIndex = length;
    }
    if (beginIndex > endIndex) {
      return - 1;
    }
    for (int i = beginIndex; i < endIndex; ++i) {
      if (this.buffer[i] == ch) {
        return i;
      }
    }
    return - 1;
  }

  /**
   * Returns the current within this buffer of the last occurrence of the
   * specified character, starting the search at the specified
   * <code>beginIndex</code> and finishing at <code>endIndex</code>. If no such
   * character occurs in this buffer within the specified bounds,
   * <code>-1</code> is returned.
   * <p>
   * There is no restriction on the value of <code>beginIndex</code> and
   * <code>endIndex</code>. If <code>beginIndex</code> is negative, it has the
   * same effect as if it were zero. If <code>endIndex</code> is greater than
   * {@link #length()}, it has the same effect as if it were {@link #length()}.
   * If the <code>beginIndex</code> is greater than the <code>endIndex</code>,
   * <code>-1</code> is returned.
   *
   * @param ch
   *          the char to search for.
   * @param beginIndex
   *          the current to start the search from.
   * @param endIndex
   *          the current to finish the search at. If it is larger than the
   *          length of this buffer, it is treated as the length of this buffer.
   * @return the current of the first occurrence of the character in the buffer
   *         within the given bounds, or <code>-1</code> if the character does
   *         not occur.
   */
  public int lastIndexOf(final int ch, int beginIndex, int endIndex) {
    if (beginIndex < 0) {
      beginIndex = 0;
    }
    if (endIndex > length) {
      endIndex = length;
    }
    if (beginIndex > endIndex) {
      return - 1;
    }
    for (int i = endIndex - 1; i >= beginIndex; --i) {
      if (buffer[i] == ch) {
        return i;
      }
    }
    return - 1;
  }

  /**
   * Returns the current within this buffer of the first occurrence of the
   * specified character, starting the search at <code>0</code> and finishing at
   * {@link #length()}. If no such character occurs in this buffer within those
   * bounds, <code>-1</code> is returned.
   *
   * @param ch
   *          the char to search for.
   * @return the current of the first occurrence of the character in the buffer,
   *         or <code>-1</code> if the character does not occur.
   */
  public int indexOf(final int ch) {
    return indexOf(ch, 0, length);
  }

  /**
   * Returns the current within this buffer of the last occurrence of the
   * specified character, starting the search at <code>0</code> and finishing at
   * {@link #length()}. If no such character occurs in this buffer within those
   * bounds, <code>-1</code> is returned.
   *
   * @param ch
   *          the char to search for.
   * @return the current of the first occurrence of the character in the buffer,
   *         or <code>-1</code> if the character does not occur.
   */
  public int lastIndexOf(final int ch) {
    return lastIndexOf(ch, 0, length);
  }

  public void compact() {
    if (length == 0) {
      buffer = EMPTY_CHAR_ARRAY;
    } else if (length < buffer.length) {
      final char[] temp = new char[length];
      System.arraycopy(buffer, 0, temp, 0, length);
      buffer = temp;
    }
  }

  public void reserve(final int n, final boolean keepContent) {
    if (buffer.length < n) {
      final char[] newBuffer = new char[n];
      if (keepContent && (length > 0)) {
        System.arraycopy(buffer, 0, newBuffer, 0, length);
      } else {
        length = 0;
      }
      buffer = newBuffer;
    }
  }

  @Override
  public void swap(final CharBuffer other) {
    final char[] tempBuffer = other.buffer;
    other.buffer = this.buffer;
    this.buffer = tempBuffer;
    final int tempLength = other.length;
    other.length = this.length;
    this.length = tempLength;
  }

  @Override
  public void assign(final CharBuffer value) {
    if (this != value) {
      length = 0;
      if (buffer.length < value.length) {
        // create a new buffer and abandon the old one
        buffer = new char[value.length];
      }
      System.arraycopy(value.buffer, 0, buffer, 0, value.length);
      length = value.length;
    }
  }

  public void assign(final String value) {
    length = 0;
    final int n = value.length();
    if (buffer.length < n) {
      // create a new buffer and abandon the old one
      buffer = new char[n];
    }
    value.getChars(0, n, buffer, 0);
    length = n;
  }

  @Override
  public CharBuffer clone() {
    final CharBuffer cloned = new CharBuffer(buffer.length);
    cloned.length = length;
    System.arraycopy(buffer, 0, cloned.buffer, 0, buffer.length);
    return cloned;
  }

  public char[] toArray() {
    if (length == 0) {
      return ArrayUtils.EMPTY_CHAR_ARRAY;
    } else {
      final char[] result = new char[length];
      System.arraycopy(buffer, 0, result, 0, length);
      return result;
    }
  }

  @Override
  public int hashCode() {
    final int multiplier = 1771;
    int code = 93;
    code = Hash.combine(code, multiplier, length);
    for (int i = 0; i < length; ++i) {
      code = Hash.combine(code, multiplier, buffer[i]);
    }
    return code;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final CharBuffer other = (CharBuffer) obj;
    return (length == other.length)
        && Equality.equals(buffer, 0, other.buffer, 0, length);
  }

  public boolean equalsIgnoreCase(final CharBuffer other) {
    return (length == other.length)
        && Equality.equalsIgnoreCase(buffer, 0, other.buffer, 0, length);
  }

  public boolean equals(final String str) {
    if (str == null) {
      return false;
    }
    if (length != str.length()) {
      return false;
    }
    for (int i = 0; i < length; ++i) {
      if (buffer[i] != str.charAt(i)) {
        return false;
      }
    }
    return true;
  }

  public boolean equalsIgnoreCase(final String str) {
    if (str == null) {
      return false;
    }
    if (length != str.length()) {
      return false;
    }
    for (int i = 0; i < length; ++i) {
      final char ch1 = buffer[i];
      final char ch2 = str.charAt(i);
      if (Character.toLowerCase(ch1) != Character.toLowerCase(ch2)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int compareTo(final CharBuffer other) {
    if (other == null) {
      return + 1;
    } else if (this == other) {
      return 0;
    } else {
      return Comparison.compare(buffer, length, other.buffer, other.length);
    }
  }

  public int compareToIgnoreCase(final CharBuffer other) {
    if (other == null) {
      return + 1;
    } else if (this == other) {
      return 0;
    } else {
      return Comparison.compareIgnoreCase(buffer, length, other.buffer,
          other.length);
    }
  }

  public int compareTo(final String str) {
    if (str == null) {
      return + 1;
    } else {
      final int strlen = str.length();
      final int n = (length < strlen ? length : strlen);
      for (int i = 0; i < n; ++i) {
        final char x = buffer[i];
        final char y = str.charAt(i);
        if (x != y) {
          return (x - y);
        }
      }
      return (length - strlen);
    }
  }

  public int compareToIgnoreCase(final String str) {
    if (str == null) {
      return + 1;
    } else {
      final int strlen = str.length();
      final int n = (length < strlen ? length : strlen);
      for (int i = 0; i < n; ++i) {
        final char x = Character.toLowerCase(buffer[i]);
        final char y = Character.toLowerCase(str.charAt(i));
        if (x != y) {
          return (x - y);
        }
      }
      return (length - strlen);
    }
  }

  /**
   * Returns a substring of this buffer. The substring begins at the specified
   * <code>beginIndex</code> and extends to the character at current
   * <code>endIndex - 1</code>.
   *
   * @param beginIndex
   *          the beginning current, inclusive.
   * @param endIndex
   *          the ending current, exclusive.
   * @return the specified substring.
   * @exception StringIndexOutOfBoundsException
   *              if the <code>beginIndex</code> is negative, or
   *              <code>endIndex</code> is larger than the length of this
   *              buffer, or <code>beginIndex</code> is larger than
   *              <code>endIndex</code>.
   */
  public String substring(final int beginIndex, final int endIndex) {
    return new String(buffer, beginIndex, endIndex - beginIndex);
  }

  /**
   * Returns a substring of this buffer with leading and trailing whitespace
   * omitted. The substring begins with the first non-whitespace character from
   * <code>beginIndex</code> and extends to the last non-whitespace character
   * with the current lesser than <code>endIndex</code>.
   *
   * @param beginIndex
   *          the beginning current, inclusive.
   * @param endIndex
   *          the ending current, exclusive.
   * @return the specified substring.
   * @exception IndexOutOfBoundsException
   *              if the <code>beginIndex</code> is negative, or
   *              <code>endIndex</code> is larger than the length of this
   *              buffer, or <code>beginIndex</code> is larger than
   *              <code>endIndex</code>.
   */
  public String substringTrimmed(int beginIndex, int endIndex) {
    if (beginIndex < 0) {
      throw new IndexOutOfBoundsException("Negative beginIndex: " + beginIndex);
    }
    if (endIndex > length) {
      throw new IndexOutOfBoundsException("endIndex: " + endIndex
          + " > length: " + length);
    }
    if (beginIndex > endIndex) {
      throw new IndexOutOfBoundsException("beginIndex: " + beginIndex
          + " > endIndex: " + endIndex);
    }
    while ((beginIndex < endIndex) && CharUtils.isBlank(buffer[beginIndex])) {
      beginIndex++;
    }
    while ((endIndex > beginIndex) && CharUtils.isBlank(buffer[endIndex - 1])) {
      endIndex--;
    }
    return new String(this.buffer, beginIndex, endIndex - beginIndex);
  }

  /**
   * Returns a substring of this buffer with leading and trailing ASCII
   * whitespace omitted. The substring begins with the first non-whitespace
   * character from <code>beginIndex</code> and extends to the last
   * non-whitespace character with the current lesser than <code>endIndex</code>
   * .
   *
   * @param beginIndex
   *          the beginning current, inclusive.
   * @param endIndex
   *          the ending current, exclusive.
   * @return the specified substring.
   * @exception IndexOutOfBoundsException
   *              if the <code>beginIndex</code> is negative, or
   *              <code>endIndex</code> is larger than the length of this
   *              buffer, or <code>beginIndex</code> is larger than
   *              <code>endIndex</code>.
   */
  public String substringTrimmedAscii(int beginIndex, int endIndex) {
    if (beginIndex < 0) {
      throw new IndexOutOfBoundsException("Negative beginIndex: " + beginIndex);
    }
    if (endIndex > length) {
      throw new IndexOutOfBoundsException("endIndex: " + endIndex
          + " > length: " + length);
    }
    if (beginIndex > endIndex) {
      throw new IndexOutOfBoundsException("beginIndex: " + beginIndex
          + " > endIndex: " + endIndex);
    }
    while ((beginIndex < endIndex) && Ascii.isWhitespace(buffer[beginIndex])) {
      beginIndex++;
    }
    while ((endIndex > beginIndex) && Ascii.isWhitespace(buffer[endIndex - 1])) {
      endIndex--;
    }
    return new String(buffer, beginIndex, endIndex - beginIndex);
  }

  @Override
  public String toString() {
    return new String(buffer, 0, length);
  }
}
