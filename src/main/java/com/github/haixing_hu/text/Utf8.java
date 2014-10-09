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

package com.github.haixing_hu.text;

import com.github.haixing_hu.math.ByteBit;
import com.github.haixing_hu.math.IntBit;

import static com.github.haixing_hu.text.ErrorCode.*;

/**
 * Provides the UTF-8 coding scheme utilities.
 *
 * @author Haixing Hu
 */
public final class Utf8 {

  /**
   * The maximum number of code units in order to encode a single Unicode code
   * point.
   */
  public static final int MAX_CODE_UNIT_COUNT = 4;

  // http://en.wikipedia.org/wiki/UTF-8
  //
  // All leading UTF-8 code units has the following form
  //
  // 11000010-11011111 C2-DF 194-223 Start of 2-byte sequence
  // 11100000-11101111 E0-EF 224-239 Start of 3-byte sequence
  // 11110000-11110100 F0-F4 240-244 Start of 4-byte sequence
  //
  private static final int MIN_LEADING = 0xC2;
  private static final int MAX_LEADING = 0xF4;

  // http://en.wikipedia.org/wiki/UTF-8
  //
  // All trailing UTF-8 code units has the form of
  //
  // 10xxxxxx
  //
  // i.e., the most significant 2 bits are 1 and 0.
  //
  private static final int TRAILING_MASK = 0xC0;
  private static final int TRAILING_PATTERN = 0x80;

  // let k be the current of the first 0 of ch from the most significant bit
  // (the most significant bit has the current of 7), then
  //
  // - if k = 7, ch is a single code unit, and the number of trailing UTF-8
  // code units is 0.
  // - if k = 6, ch is NOT a valid leading UTF-8 code unit.
  // - if k = 5, 4, or 3, the number of trailing UTF-8 code units is 6 - k.
  // - otherwise, ch is NOT a valid leading UTF-8 code unit.
  //
  private static final int TRAILING_COUNT_MINUEND = 6;

  // The maximum value of Unicode code point which could be encoded in one
  // UTF-8 code unit.
  private static final int MAX_ONE_CODE_UNIT = 0x7F;
  // The maximum value of Unicode code point which could be encoded in two
  // UTF-8 code units.
  private static final int MAX_TWO_CODE_UNIT = 0x7FF;
  // The maximum value of Unicode code point which could be encoded in three
  // UTF-8 code unit.
  private static final int MAX_THREE_CODE_UNIT = 0xFFFF;
  // The maximum value of Unicode code point which could be encoded in four
  // UTF-8 code unit.
  // private static final int MAX_FOUR_CODE_UNIT = 0x10FFFF;

  // The mask and pattern used to test whether a Unicode code point is a
  // surrogate. That is, if ch is a Unicode code point, ch is a surrogate iff
  // (ch & SurrogateMask) == SurrogatePattern.
  private static final int SURROGATE_MASK = 0xFFFFF800;

  // private static final int SURROGATE_PATTERN = 0x0000D800;

  /**
   * Determines whether the specified UTF-8 code unit is a single code unit
   * encoding a valid code point.
   * <p>
   * A valid Unicode code point could be encoded as 1 to 4 UTF-8 code unit, and
   * a UTF-8 code unit is a single code unit encoding a Unicode code point iff
   * its value is less than 0x80.
   *
   * @param ch
   *          a UTF-8 code unit.
   * @return true if the specified UTF-8 code unit is a single code unit
   *         encoding a valid Unicode code point; false otherwise.
   */
  public static boolean isSingle(final byte ch) {
    final int codePoint = (ch & 0xFF);
    return codePoint <= MAX_ONE_CODE_UNIT;
  }

  /**
   * Determines whether the specified UTF-8 code unit is a single code unit
   * encoding a valid code point.
   * <p>
   * A valid Unicode code point could be encoded as 1 to 4 UTF-8 code unit, and
   * a UTF-8 code unit is a single code unit encoding a Unicode code point iff
   * its value is less than 0x80.
   *
   * @param ch
   *          a UTF-8 code unit as an unsigned integer.
   * @return true if the specified UTF-8 code unit is a single code unit
   *         encoding a valid Unicode code point; false otherwise.
   */
  public static boolean isSingle(final int ch) {
    return ch <= MAX_ONE_CODE_UNIT;
  }

  /**
   * Determines whether the specified UTF-8 code unit is a leading code unit of
   * a valid Unicode code point.
   * <p>
   * A valid Unicode code point could be encoded as 1 to 4 UTF-8 code unit, and
   * a UTF-8 code unit is a leading code unit of a Unicode code point iff its
   * bits are prefixed by 110, 1110, or 11110 (i.e., it is in the binary form of
   * 110xxxxx, 1110xxxx, or 11110xxx).
   *
   * @param ch
   *          a UTF-8 code unit.
   * @return true if the specified UTF-8 code unit is a leading code unit of a
   *         valid Unicode code point; false otherwise.
   */
  public static boolean isLeading(final byte ch) {
    // http://en.wikipedia.org/wiki/UTF-8
    //
    // 11000010-11011111 C2-DF 194-223 Start of 2-byte sequence
    // 11100000-11101111 E0-EF 224-239 Start of 3-byte sequence
    // 11110000-11110100 F0-F4 240-244 Start of 4-byte sequence
    //
    final int codePoint = (ch & 0xFF);
    return (codePoint - MIN_LEADING) <= (MAX_LEADING - MIN_LEADING);
  }

  /**
   * Determines whether the specified UTF-8 code unit is a leading code unit of
   * a valid Unicode code point.
   * <p>
   * A valid Unicode code point could be encoded as 1 to 4 UTF-8 code unit, and
   * a UTF-8 code unit is a leading code unit of a Unicode code point iff its
   * bits are prefixed by 110, 1110, or 11110 (i.e., it is in the binary form of
   * 110xxxxx, 1110xxxx, or 11110xxx).
   *
   * @param ch
   *          a UTF-8 code unit as an unsigned integer.
   * @return true if the specified UTF-8 code unit is a leading code unit of a
   *         valid Unicode code point; false otherwise.
   */
  public static boolean isLeading(final int ch) {
    // http://en.wikipedia.org/wiki/UTF-8
    //
    // 11000010-11011111 C2-DF 194-223 Start of 2-byte sequence
    // 11100000-11101111 E0-EF 224-239 Start of 3-byte sequence
    // 11110000-11110100 F0-F4 240-244 Start of 4-byte sequence
    //
    return (ch - MIN_LEADING) <= (MAX_LEADING - MIN_LEADING);
  }

  /**
   * Determines whether the specified UTF-8 code unit is a trailing code unit of
   * a valid Unicode code point.
   * <p>
   * A valid Unicode code point could be encoded as 1 to 4 UTF-8 code unit, and
   * a UTF-8 code unit is a trailing code unit of a Unicode code point iff its
   * bits are prefixed by 10 (i.e., it is in the binary form of 10xxxxxx, or
   * equivalently, it is between [0x80, 0xBF]).
   *
   * @param ch
   *          a UTF-8 code unit.
   * @return true if the specified UTF-8 code unit is a trailing code unit of a
   *         valid Unicode code point; false otherwise.
   */
  public static boolean isTrailing(final byte ch) {
    // http://en.wikipedia.org/wiki/UTF-8
    //
    // All trailing UTF-8 code units has the form of
    //
    // 10xxxxxx
    //
    // i.e., the most significant 2 bits are 1 and 0.
    //
    final int codePoint = (ch & 0xFF);
    return (codePoint & TRAILING_MASK) == TRAILING_PATTERN;
  }

  /**
   * Determines whether the specified UTF-8 code unit is a trailing code unit of
   * a valid Unicode code point.
   * <p>
   * A valid Unicode code point could be encoded as 1 to 4 UTF-8 code unit, and
   * a UTF-8 code unit is a trailing code unit of a Unicode code point iff its
   * bits are prefixed by 10 (i.e., it is in the binary form of 10xxxxxx, or
   * equivalently, it is between [0x80, 0xBF]).
   *
   * @param ch
   *          a UTF-8 code unit as an unsigned integer.
   * @return true if the specified UTF-8 code unit is a trailing code unit of a
   *         valid Unicode code point; false otherwise.
   */
  public static boolean isTrailing(final int ch) {
    // http://en.wikipedia.org/wiki/UTF-8
    //
    // All trailing UTF-8 code units has the form of
    //
    // 10xxxxxx
    //
    // i.e., the most significant 2 bits are 1 and 0.
    //
    return (ch & TRAILING_MASK) == TRAILING_PATTERN;
  }

  /**
   * Counts the number of trailing UTF-8 code units need to compose a valid
   * Unicode code point according to the leading code unit.
   *
   * @param ch
   *          a UTF-8 code unit, which must be a leading code unit of a valid
   *          code point.
   * @return The number of trailing code units (does NOT include the leading
   *         code unit itself) need to compose a valid Unicode code point
   *         according to the leading code unit.
   */
  public static int getTrailingCount(final byte ch) {
    // let k be the current of the first 0 of ch from the most significant bit
    // (the most significant bit has the current of 7), then
    //
    // - if k = 7, the leadingCodeUnit is a single code unit, and the number of
    // trailing UTF-8 code units is 0.
    // - if k = 6, the leadingCodeUnit is NOT a valid leading UTF-8 code unit.
    // - if k = 5, 4, or 3, the number of trailing UTF-8 code units is 6 - k.
    // - otherwise, the leadingCodeUnit is NOT a valid leading UTF-8 code unit.
    //
    assert isLeading(ch);
    final int lastZero = ByteBit.findLastUnset(ch);
    return (TRAILING_COUNT_MINUEND - lastZero);
  }

  /**
   * Counts the number of trailing UTF-8 code units need to compose a valid
   * Unicode code point according to the leading code unit.
   *
   * @param ch
   *          a UTF-8 code unit as an unsigned integer, which must be a leading
   *          code unit of a valid code point.
   * @return The number of trailing code units (does NOT include the leading
   *         code unit itself) need to compose a valid Unicode code point
   *         according to the leading code unit.
   */
  public static int getTrailingCount(final int ch) {
    // let k be the current of the first 0 of ch from the most significant bit
    // (the most significant bit has the current of 7), then
    //
    // - if k = 7, the leadingCodeUnit is a single code unit, and the number of
    // trailing UTF-8 code units is 0.
    // - if k = 6, the leadingCodeUnit is NOT a valid leading UTF-8 code unit.
    // - if k = 5, 4, or 3, the number of trailing UTF-8 code units is 6 - k.
    // - otherwise, the leadingCodeUnit is NOT a valid leading UTF-8 code unit.
    //
    assert isLeading(ch);
    final int lastZero = IntBit.findLastUnset(ch);
    return (TRAILING_COUNT_MINUEND - lastZero);
  }

  /**
   * Gets the number of UTF-8 code units need to encode the specified Unicode
   * code point.
   *
   * @param codePoint
   *          a code point, which must be a valid Unicode code point and must
   *          NOT be a surrogate code point.
   * @return The number of UTF-8 code units need to encode the specified Unicode
   *         code point, which is in the range of [1, MaxCount].
   */
  public static int getCodeUnitCount(final int codePoint) {
    // the codePoint must be a valid Unicode code point and not a surrogate.
    assert ((codePoint <= Unicode.UNICODE_MAX) && ((codePoint & SURROGATE_MASK) != Unicode.SURROGATE_MIN));

    if (codePoint <= MAX_ONE_CODE_UNIT) {
      return 1;
    } else if (codePoint <= MAX_TWO_CODE_UNIT) {
      return 2;
    } else if (codePoint <= MAX_THREE_CODE_UNIT) {
      return 3;
    } else {
      return 4;
    }
  }

  /**
   * Adjust a random-access offset in a UTF-8 code unit sequence to the start
   * position of the current code point.
   * <p>
   * More precisely, if the offset points to a trailing code unit, then the
   * offset is moved backward to the corresponding leading code unit; otherwise,
   * it is not modified.
   *
   * @param pos
   *          the current parsing position; after calling this function, it will
   *          be set to the new position.
   * @param buffer
   *          an array of bytes.
   * @param startIndex
   *          the start current of the byte array.
   * @return the amount of decreasing of the argument current, or 0 if the
   *         argument current does not modified. After successfully calling this
   *         function, the position of the buffer will be decreased to the
   *         amount of value returned by this function. If the current position
   *         of the buffer points an illegal code unit sequence, or an
   *         incomplete code unit sequence, the function returns a negative
   *         integer indicating the error: {@link ErrorCode#MALFORMED_UNICODE}
   *         indicates an illegal code unit sequence;
   *         {@link ErrorCode#INCOMPLETE_UNICODE} indicates an incomplete code
   *         unit sequence, and the position of the buffer does not changed.
   * @throws IndexOutOfBoundsException
   *           if startIndex < 0 or current.value < startIndex. If current.value ==
   *           startIndex, the function will check whether the byte at the
   *           startIndex is a trailing byte, if it is, an INCOMPLETE_UNICODE
   *           will be returned; otherwise, a 0 will be returned.
   */
  public static int setToStart(final ParsingPosition pos, final byte[] buffer,
      final int startIndex) {
    final int index = pos.getIndex();
    if ((startIndex < 0) || (index < startIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (! isTrailing(buffer[index])) {
      return 0;
    }
    for (int i = index - 1; i >= startIndex; --i) {
      final byte ch = buffer[i];
      if (isLeading(ch)) {
        final int count = index - i;
        if (count > getTrailingCount(ch)) {
          pos.setErrorIndex(i);
          pos.setErrorCode(MALFORMED_UNICODE);
          return MALFORMED_UNICODE;
        } else {
          pos.setIndex(i);
          return count;
        }
      } else if (! isTrailing(ch)) {
        pos.setErrorIndex(i);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      } else if (index - i >= (MAX_CODE_UNIT_COUNT - 1)) {
        // the code unit sequence is too long
        pos.setErrorIndex(i);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
    pos.setErrorIndex(startIndex);
    pos.setErrorCode(MALFORMED_UNICODE);
    return INCOMPLETE_UNICODE;
  }

  /**
   * Adjust a random-access offset in a UTF-8 code unit sequence to the position
   * of the end of current code point.
   * <p>
   * More precisely, if the offset points to a trailing code unit of a code
   * point, then the offset is increased to behind the whole code unit sequence
   * of the code point; otherwise, it is not modified.
   *
   * @param pos
   *          the current parsing position; after calling this function, it will
   *          be set to the new position.
   * @param buffer
   *          an array of bytes.
   * @param endIndex
   *          the end current of the byte array.
   * @return the amount of increasing of the argument current, or 0 if the
   *         argument current does not modified. After successfully calling this
   *         function, the position of the buffer will be increased to the
   *         amount of value returned by this function. If the current position
   *         of the buffer points to an illegal code unit sequence, or an
   *         incomplete code unit sequence, the function returns a negative
   *         integer indicating the error: {@link ErrorCode#MALFORMED_UNICODE}
   *         indicates an illegal code unit sequence;
   *         {@link ErrorCode#INCOMPLETE_UNICODE} indicates an incomplete code
   *         unit sequence.
   * @throws IndexOutOfBoundsException
   *           if endIndex > buffer.length or current.value > endIndex. Note that
   *           if current.value == endIndex, the function does nothing and returns
   *           0.
   */
  public static int setToTerminal(final ParsingPosition pos, final byte[] buffer,
      final int endIndex) {
    final int index = pos.getIndex();
    if ((endIndex > buffer.length) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == buffer.length) {
      return 0;
    }
    final byte ch = buffer[index];
    if (! isLeading(ch)) {
      return 0;
    }
    final int trailing_count = getTrailingCount(ch);
    for (int i = index + 1; i < endIndex; ++i) {
      final int count = i - index;
      if (count >= trailing_count) {
        // the code unit sequence is too long
        pos.setErrorIndex(i);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
      if (! isTrailing(buffer[i])) {
        pos.setIndex(i);
        return count;
      }
    }
    pos.setErrorIndex(endIndex);
    pos.setErrorCode(MALFORMED_UNICODE);
    return INCOMPLETE_UNICODE;
  }

  /**
   * Advance the offset of a UTF-8 code unit sequence from one code point
   * boundary to the next one.
   * <p>
   * The offset must point to a code unit starting a valid code point (i.e.,
   * it's either points to a single code unit, or points to a leading code
   * unit), and the function advance the offset to the starting of the next code
   * unit, returns the number of code unit it skipped. If the offset does not
   * points to a code unit starting a valid code point, or it points to a
   * illegal code unit sequence, the function does nothing but returns a
   * negative integer indicating the error.
   *
   * @param pos
   *          the current parsing position; after calling this function, it will
   *          be set to the new position.
   * @param buffer
   *          an array of bytes.
   * @param endIndex
   *          the end current of the byte array.
   * @return if the function successfully skip a code point from the code unit
   *         sequence at the specified offset, returns the number of code units
   *         consisting the skipped code point, and forward the position of the
   *         buffer to the new position; otherwise, if the specified offset does
   *         not point to a leading code unit of a valid code point, do nothing
   *         and returns a negative integer indicating the error:
   *         {@link ErrorCode#MALFORMED_UNICODE} indicates the specified offset
   *         does not point to the starting of a legal code unit sequence
   *         consisting a valid code point; {@link ErrorCode#INCOMPLETE_UNICODE}
   *         indicates the input code unit sequence is not complete to form a
   *         valid code point.
   * @throws IndexOutOfBoundsException
   *           if endIndex > buffer.length or current.value > endIndex. Note that
   *           if current.value == endIndex, the function does nothing and returns
   *           0.
   */
  public static int forward(final ParsingPosition pos, final byte[] buffer,
      final int endIndex) {
    final int index = pos.getIndex();
    if ((endIndex > buffer.length) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == endIndex) {
      return 0;
    }
    byte ch = buffer[index];
    if (isSingle(ch)) {
      pos.setIndex(index + 1);
      return 1;
    } else if (! isLeading(ch)) {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    }
    final int count = 1 + getTrailingCount(ch);
    final int next_index = index + count;
    if (next_index > endIndex) {
      pos.setErrorIndex(endIndex);
      pos.setErrorCode(INCOMPLETE_UNICODE);
      return INCOMPLETE_UNICODE;
    }
    for (int i = index + 1; i < next_index; ++i) {
      ch = buffer[i];
      if (! isTrailing(ch)) {
        pos.setErrorIndex(i);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
    pos.setIndex(next_index);
    return count;
  }

  /**
   * Decreases the offset of a UTF-8 code unit sequence from one code point
   * boundary to the previous one.
   * <p>
   * The offset must point to a position next to the code unit ending a valid
   * code point (i.e., it's either points to a single code unit, or points to a
   * leading code unit, or points to the position right after the last code unit
   * of the whole code unit sequence), and the function decreases the offset to
   * the starting of the previous code unit, returns the number of code unit it
   * passed. If the offset does not points to a position next to the code unit
   * ending a valid code point, or if the previous code unit sequence is
   * illegal, the function does nothing but returns a negative integer
   * indicating the error.
   *
   * @param pos
   *          the current parsing position; after calling this function, it will
   *          be set to the new position.
   * @param buffer
   *          an array of bytes.
   * @param startIndex
   *          the start current of the byte array.
   * @return if the function successfully passed the previous code point from
   *         the code unit sequence at the specified offset, returns the number
   *         of code units consisting the passed code point, and backward the
   *         position of the buffer to the new position; otherwise, if the
   *         specified offset does not point to a leading code unit of a valid
   *         code point, do nothing and returns a negative integer indicating
   *         the error: {@link ErrorCode#MALFORMED_UNICODE} indicates the
   *         specified offset does not point to the next position of the code
   *         unit ending a valid code point;
   *         {@link ErrorCode#INCOMPLETE_UNICODE} indicates the input code unit
   *         sequence is not complete to form a valid code point.
   * @throws IndexOutOfBoundsException
   *           if startIndex < 0 or current.value < startIndex. Note that if
   *           current.value == startIndex, the function does nothing and returns
   *           0.
   */
  public static int backward(final ParsingPosition pos, final byte[] buffer,
      final int startIndex) {
    final int index = pos.getIndex();
    if ((startIndex < 0) || (index < startIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == startIndex) {
      return 0;
    }
    final int prev_index = index - 1;
    assert (prev_index >= startIndex);
    byte ch = buffer[prev_index];
    if (isSingle(ch)) {
      pos.setIndex(prev_index);
      return 1;
    } else if (! isTrailing(ch)) {
      pos.setErrorIndex(prev_index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    }
    for (int i = prev_index - 1; i >= startIndex; --i) {
      ch = buffer[i];
      if (isLeading(ch)) {
        final int count = index - i;
        if (count != getTrailingCount(ch) + 1) {
          pos.setErrorIndex(i);
          pos.setErrorCode(MALFORMED_UNICODE);
          return MALFORMED_UNICODE;
        } else {
          pos.setIndex(i);
          return count;
        }
      } else if (! isTrailing(ch)) {
        pos.setErrorIndex(i);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      } else if (index - i >= MAX_CODE_UNIT_COUNT) {
        // the code unit sequence is too long
        pos.setErrorIndex(i);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
    pos.setErrorIndex(startIndex);
    pos.setErrorCode(MALFORMED_UNICODE);
    return INCOMPLETE_UNICODE;
  }

  /**
   * Get a code point from a UTF-8 code unit sequence at a code point boundary
   * offset.
   * <p>
   * The offset must point to a code unit starting a valid code point (i.e.,
   * either a single code unit, or the leading code unit of a valid code point).
   * The function read all the code points consisting that code point, and
   * returns the number of code units have been read. If the offset does not
   * point to a starting code unit of a valid code point, or points to an
   * illegal code unit sequence, the function returns a negative integer
   * indicating the error.
   *
   *@param pos
   *          the current parsing position; after calling this function, it will
   *          be set to the new position.
   * @param buffer
   *          an array of bytes.
   * @param endIndex
   *          the end current of the byte array.
   * @return if the function successfully get a code point from the code unit
   *         sequence at the specified offset, returns the value of the code
   *         point (which is non-negative); otherwise, returns a negative
   *         integer indicating the error: {@link ErrorCode#MALFORMED_UNICODE}
   *         indicates the specified offset does not point to the starting of a
   *         legal code unit sequence consisting a valid code point;
   *         {@link ErrorCode#INCOMPLETE_UNICODE} indicates the input code unit
   *         sequence is not complete to form a valid code point.
   * @throws IndexOutOfBoundsException
   *           if endIndex > buffer.length or current.value > endIndex. Note that
   *           if current.value == endIndex, the function does nothing and returns
   *           0.
   */
  public static int getNext(final ParsingPosition pos, final byte[] buffer,
      final int endIndex) {
    final int index = pos.getIndex();
    if ((endIndex > buffer.length) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == endIndex) {
      return 0;
    }
    final int p0 = index;
    final int c0 = (buffer[p0] & 0xFF);
    if (isSingle(c0)) {
      pos.setIndex(p0 + 1);
      return c0;
    } else if (! isLeading(c0)) {
      pos.setErrorIndex(p0);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    }
    if (c0 <= 0xC1) {
      // Over long encoding: c0 is the start of a 2-byte sequence, but code
      // point <= 127. This MUST be considered as an ERROR, for the security
      // reason. (for example, the over long UTF-8 sequence 0xC0 0x80 will be
      // decoded into the character U+0000, which would cause an buffer
      // overflow.)
      pos.setErrorIndex(p0);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    } else if (c0 <= 0xDF) {
      // c0 is the start of a 2-byte sequence, encoding U+0080..U+07FF
      final int p2 = p0 + 2;
      if (p2 > endIndex) {
        pos.setErrorIndex(endIndex);
        pos.setErrorCode(INCOMPLETE_UNICODE);
        return INCOMPLETE_UNICODE;
      }
      final int p1 = p0 + 1;
      int c1 = buffer[p1] & 0xFF;
      c1 ^= 0x80;
      if (c1 >= 0x40) {
        pos.setErrorIndex(p1);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
      int codePoint = (c0 & 0x1F);
      codePoint <<= 6;
      codePoint |= c1;
      pos.setIndex(p2);
      return codePoint;
    } else if (c0 <= 0xEF) {
      // c0 is the start of a 3-byte sequence, encoding U+0800..U+FFFF
      final int p3 = p0 + 3;
      if (p3 > endIndex) {
        pos.setErrorIndex(endIndex);
        pos.setErrorCode(INCOMPLETE_UNICODE);
        return INCOMPLETE_UNICODE;
      }
      final int p1 = p0 + 1;
      int c1 = (buffer[p1] & 0xFF);
      if ((c0 < 0xE1) && (c1 < 0xA0)) {
        pos.setErrorIndex(p1);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
      c1 ^= 0x80;
      if (c1 >= 0x40) {
        return MALFORMED_UNICODE;
      }
      final int p2 = p0 + 2;
      int c2 = (buffer[p2] & 0xFF);
      c2 ^= 0x80;
      if (c2 >= 0x40) {
        pos.setErrorIndex(p2);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
      int codePoint = c0 & 0x0F;
      codePoint <<= 12;
      codePoint |= (c1 << 6);
      codePoint |= c2;
      pos.setIndex(p3);
      return codePoint;
    } else if (c0 <= 0xF4) {
      // c0 is the start of a 4-byte sequence, encoding U+10000..U+10FFFF
      final int p4 = p0 + 4;
      if (p4 > endIndex) {
        pos.setErrorIndex(endIndex);
        pos.setErrorCode(INCOMPLETE_UNICODE);
        return INCOMPLETE_UNICODE;
      }
      final int p1 = p0 + 1;
      int c1 = (buffer[p1] & 0xFF);
      if ((c0 < 0xF1) && (c1 < 0x90)) {
        pos.setErrorIndex(p1);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
      c1 ^= 0x80;
      if (c1 >= 0x40) {
        return MALFORMED_UNICODE;
      }
      final int p2 = p0 + 2;
      int c2 = (buffer[p2] & 0xFF);
      c2 ^= 0x80;
      if (c2 >= 0x40) {
        pos.setErrorIndex(p2);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
      final int p3 = p0 + 3;
      int c3 = (buffer[p3] & 0xFF);
      c3 ^= 0x80;
      if (c3 >= 0x40) {
        pos.setErrorIndex(p3);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
      int codePoint = (c0 & 0x07);
      codePoint <<= 18;
      codePoint |= (c1 << 12);
      codePoint |= (c2 << 6);
      codePoint |= c3;
      pos.setIndex(p4);
      return codePoint;
    } else {
      pos.setErrorIndex(p0);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    }
  }

  /**
   * Move the offset of a UTF-8 code unit sequence from one code point boundary
   * to the previous one and get the code point between them.
   * <p>
   * The input offset must points to a position next to the ending code unit of
   * a valid code point (which is either a starting code unit of the next code
   * point, or the end of the whole code unit sequence). The function will move
   * the offset to the starting code unit of the previous code point, and reads
   * that code point, then returns the number of code units consisting that code
   * point (which is also the amount of decreasing of the offset). If the offset
   * does not point to a position next to the ending code unit of a valid code
   * point, or if the previous code unit sequence in illegal, the function
   * returns a negative integer indicating the error.
   *
   * @param pos
   *          the current parsing position; after calling this function, it will
   *          be set to the new position.
   * @param buffer
   *          an array of bytes.
   * @param startIndex
   *          the start current of the byte array.
   * @return if the function successfully get a code point from the code unit
   *         sequence at the specified current, returns the value of the code
   *         point (which is non-negative); otherwise, returns a negative
   *         integer indicating the error: {@link ErrorCode#MALFORMED_UNICODE}
   *         indicates the specified offset does not point to the next position
   *         of the ending of a legal code unit sequence consisting a valid code
   *         point; {@link ErrorCode#INCOMPLETE_UNICODE} indicates the input
   *         code unit sequence is not complete to form a valid Unicode code
   *         point.
   * @throws IndexOutOfBoundsException
   *           if startIndex < 0 or current.value < startIndex. Note that if
   *           current.value == startIndex, the function does nothing and returns
   *           0.
   */
  public static int getPrevious(final ParsingPosition pos, final byte[] buffer,
      final int startIndex) {
    final int index = pos.getIndex();
    if ((startIndex < 0) || (index < startIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == startIndex) {
      return 0;
    }
    // remember the old current
    final int old_index = index;
    int result = backward(pos, buffer, startIndex);
    if (result < 0) {
      return result;
    } else {
      // remember the new current
      final int new_index = pos.getIndex();
      result = getNext(pos, buffer, old_index);
      if (result < 0) {
        // on error restore the old current
        pos.reset(old_index);
        return result;
      } else {
        // on success set the new current
        pos.setIndex(new_index);
        return result;
      }
    }
  }

  /**
   * Puts a code point to a UTF-8 code unit buffer, writes 1 to 4 code units.
   * <p>
   * The current points to the position where to put the code point and is
   * advanced by 1 to 4 after putting the code point. If the code point is not
   * valid or the buffer space is not sufficient, the function returns 0.
   *
   * @param codePoint
   *          the code point to be put.
   * @param current
   *          the current in the buffer where to put the code point. It must be
   *          within [0, buffer.length).
   * @param buffer
   *          a UTF-8 code unit buffer.
   * @param endIndex
   *          the end current of the byte array.
   * @return if the code point is successfully put into the buffer, returns the
   *         number of code units put into the buffer; otherwise, returns a
   *         negative integer indicating the error:
   *         {@link ErrorCode#MALFORMED_UNICODE} indicates the specified code
   *         point is not a valid Unicode code point, or is a surrogate;
   *         {@link ErrorCode#BUFFER_OVERFLOW} indicates the code unit buffer
   *         has no sufficient space to hold the result.
   * @throws IndexOutOfBoundsException
   *           if endIndex > buffer.length or current > endIndex. Note that if
   *           current == endIndex, the function will returns BUFFER_OVERFLOW.
   */
  public static int put(int codePoint, final int index, final byte[] buffer,
      final int endIndex) {
    if ((endIndex > buffer.length) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (codePoint <= MAX_ONE_CODE_UNIT) {
      buffer[index] = (byte) (codePoint & 0xFF);
      return 1;
    } else if (codePoint < 0x0800) {
      if (index + 2 > endIndex) {
        return BUFFER_OVERFLOW;
      }
      final int c1 = ((codePoint & 0x3F) | 0x80);
      final int c0 = ((codePoint >>> 6) | 0xC0);
      buffer[index + 1] = (byte) (c1 & 0xFF);
      buffer[index] = (byte) (c0 & 0xFF);
      return 2;
    } else if (codePoint < 0x10000) {
      if (index + 3 > endIndex) {
        return BUFFER_OVERFLOW;
      }
      // Starting with Unicode 3.2, surrogate code points must not be encoded
      // in UTF-8.
      if ((codePoint & 0xFFFFF800) == 0xD800) {
        return MALFORMED_UNICODE;
      }
      final int c2 = ((codePoint & 0x3F) | 0x80);
      codePoint = ((codePoint >>> 6) | 0x800);
      final int c1 = ((codePoint & 0x3F) | 0x80);
      final int c0 = ((codePoint >>> 6) | 0xC0);
      buffer[index + 2] = (byte) (c2 & 0xFF);
      buffer[index + 1] = (byte) (c1 & 0xFF);
      buffer[index] = (byte) (c0 & 0xFF);
      return 3;
    } else if (codePoint < 0x110000) {
      if (index + 4 > endIndex) {
        return BUFFER_OVERFLOW;
      }
      final int c3 = ((codePoint & 0x3F) | 0x80);
      codePoint = ((codePoint >>> 6) | 0x10000);
      final int c2 = ((codePoint & 0x3F) | 0x80);
      codePoint = ((codePoint >>> 6) | 0x800);
      final int c1 = ((codePoint & 0x3F) | 0x80);
      final int c0 = ((codePoint >>> 6) | 0xC0);
      buffer[index + 3] = (byte) (c3 & 0xFF);
      buffer[index + 2] = (byte) (c2 & 0xFF);
      buffer[index + 1] = (byte) (c1 & 0xFF);
      buffer[index] = (byte) (c0 & 0xFF);
      return 4;
    } else {
      return MALFORMED_UNICODE;
    }
  }

}
