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
package com.github.haixing_hu.text;

import com.github.haixing_hu.lang.CharUtils;
import com.github.haixing_hu.lang.LongUtils;

import static com.github.haixing_hu.text.ErrorCode.*;

/**
 * Provides function for parsing text.
 *
 * @author Haixing Hu
 */
public final class ParseUtils {

  /**
   * Skips the leading whitespace and non-printable characters of a string,
   * returns the first current of graph character.
   *
   * @param pos
   *          a {@link ParsingPosition} object indicate the current position in
   *          the character sequence to be parsed; after calling this function,
   *          the current of the position would be changed.
   * @param str
   *          a character sequence.
   * @param endIndex
   *          the skipping end at this current.
   */
  public static void skipBlanks(final ParsingPosition pos,
      final CharSequence str, final int endIndex) {
    int index = pos.getIndex();
    while (index < endIndex) {
      final int codePoint = Utf16.getNext(pos, str, endIndex);
      if (codePoint < 0) { // an error occurs
        pos.setIndex(index);
        return;
      }
      if (CharUtils.isGraph(codePoint)) { // find the first graph character
        pos.setIndex(index);
        return;
      }
      index = pos.getIndex();
    }
  }

  /**
   * Skips the leading non-blank characters of a string, returns the first
   * current of blank character.
   *
   * @param pos
   *          a {@link ParsingPosition} object indicate the current position in
   *          the character sequence to be parsed; after calling this function,
   *          the current of the position would be changed.
   * @param str
   *          a character sequence.
   * @param endIndex
   *          the skipping end at this current.
   */
  public static void skipNonBlanks(final ParsingPosition pos,
      final CharSequence str, final int endIndex) {
    int index = pos.getIndex();
    while (index < endIndex) {
      final int codePoint = Utf16.getNext(pos, str, endIndex);
      if (codePoint < 0) { // an error occurs
        pos.setIndex(index);
        return;
      }
      if (CharUtils.isBlank(codePoint)) { // find the first blank character
        pos.setIndex(index);
        return;
      }
      index = pos.getIndex();
    }
  }

  /**
   * Skips the leading whitespace, non-printable characters and a specified
   * separator of a string, returns the first current of printable character.
   *
   * @param pos
   *          a {@link ParsingPosition} object indicate the current position in
   *          the character sequence to be parsed; after calling this function,
   *          the current of the position would be changed.
   * @param str
   *          a character sequence.
   * @param endIndex
   *          the skipping end at this current.
   * @param separator
   *          the specified separator to be skipped. Note that the separator is
   *          treated as a substring.
   */
//  public static void skipSeparators(final ParsingPosition pos,
//      final CharSequence str, final int endIndex, final CharSequence separator) {
//    int index = pos.getIndex();
//    while (index < endIndex) {
//      final int codePoint = Utf16.getNext(pos, str, endIndex);
//      if (codePoint < 0) { // an error occurs
//        pos.setIndex(index);
//        return;
//      }
//      if (CharUtils.isGraph(codePoint)) { // find the first graph character
//        if (CharSequenceUtils.startsWith(str, index, endIndex, separator)) {
//          index += separator.length();
//          pos.setIndex(index);
//        } else {
//          pos.setIndex(index);
//          return;
//        }
//      } else {
//        index = pos.getIndex();
//      }
//    }
//  }

  /**
   * Skips the optional prefix.
   *
   * @param pos
   *          a {@link ParsingPosition} object indicate the current position in
   *          the character sequence to be parsed; after calling this function,
   *          if the prefix is successfully skipped, this position will be set
   *          to the position after skipping the prefix; otherwise, this
   *          position will not be changed.
   * @param str
   *          a character sequence.
   * @param endIndex
   *          the skipping end at this current.
   * @param prefix
   *          the optional prefix to be skipped.
   * @return true if the prefix successfully skipped; false otherwise.
   */
  public static boolean skipPrefix(final ParsingPosition pos,
      final CharSequence str, final int endIndex, final String prefix) {
    final int prefixLen = prefix.length();
    final int startIndex = pos.getIndex();
    if ((endIndex - startIndex) < prefixLen) {
      return false;
    }
    for (int i = 0; i < prefixLen; ++i) {
      if (str.charAt(startIndex + i) != prefix.charAt(i)) {
        return false;
      }
    }
    pos.setIndex(startIndex + prefixLen);
    return true;
  }

  /**
   * Gets the sign of the number to be parsed. After calling this function, the
   * current.value may be forwarded to skip the possible sign symbol.
   * <b>IMPORTANT NOTE:</b> this function does not check the validity of the
   * arguments, therefore, the caller MUST make sure that the arguments are
   * valid.
   *
   * @param pos
   *          a {@link ParsingPosition} object indicate the current position in
   *          the character sequence to be parsed; after calling this function,
   *          the current of the position would be changed.
   * @param str
   *          a character sequence.
   * @param endIndex
   *          the end current of the character sequence. It must satisfies that
   *          0 <= current.value <= endIndex <= str.length(); The whole text
   *          segment is within the range [current.value, endIndex) of input.
   * @param positiveSign
   *          the character of the positive sign symbol.
   * @param negativeSign
   *          the character of the negative sign symbol.
   * @return -1 if the input character sequence has a negative sign; +1
   *         otherwise.
   */
  public static int getSign(final ParsingPosition pos, final CharSequence str,
      final int endIndex, final char positiveSign, final char negativeSign) {
    int sign = + 1;
    final int index = pos.getIndex();
    if (index < endIndex) {
      final char ch = str.charAt(index);
      if (ch == positiveSign) {
        pos.setIndex(index + 1);
        sign = + 1;
      } else if (ch == negativeSign) {
        pos.setIndex(index + 1);
        sign = - 1;
      }
    }
    return sign;
  }

  /**
   * Gets the radix of the number to be parsed. After calling this function,
   * this.index may be forwarded to skip the possible radix prefix in the input
   * text segment. <b>IMPORTANT NOTE:</b> this function does not check the
   * validity of the arguments, therefore, the caller MUST make sure that the
   * arguments are valid.
   *
   * @param pos
   *          a {@link ParsingPosition} object indicate the current position in
   *          the character sequence to be parsed; after calling this function,
   *          the current of the position would be changed.
   * @param str
   *          a character sequence.
   * @param endIndex
   *          the end current of the character sequence. It must satisfies that
   *          0 <= current.value <= endIndex <= str.length(); The whole text
   *          segment is within the range [current.value, endIndex) of input.
   * @param flags
   *          the combination of the format flags of the number to be parsed.
   * @param defaultRadix
   *          the default radix used if the number to be parsed does not specify
   *          the radix nor has the radix prefix.
   * @return the radix of the number to be parsed.
   */
  public static int getRadix(final ParsingPosition pos, final CharSequence str,
      final int endIndex, final int flags, final int defaultRadix) {
    int index = pos.getIndex();
    switch (flags & FormatFlag.RADIX_MASK) {
      case FormatFlag.BINARY:
        if (index < endIndex) {
          char ch = str.charAt(index);
          if (ch == '0') {
            ++index;
            if (index < endIndex) {
              ch = str.charAt(index);
              if ((ch == 'b') || (ch == 'B')) {
                // skip the binary prefix "0b" or "0B"
                pos.setIndex(index + 1);
                return 2;
              }
            }
          }
        }
        return 2;
      case FormatFlag.OCTAL:
        // since the octal prefix is a single '0', it could also be
        // treated as the leading zero, so don't skip it.
        return 8;
      case FormatFlag.HEX:
        if (index < endIndex) {
          char ch = str.charAt(index);
          if (ch == '0') {
            ++index;
            if (index < endIndex) {
              ch = str.charAt(index);
              if ((ch == 'x') || (ch == 'X')) {
                // skip the hex prefix "0x" or "0X"
                pos.setIndex(index + 1);
                return 16;
              }
            }
          }
        }
        return 16;
      case FormatFlag.DECIMAL:
        return 10;
      default:
        // detect the radix prefix
        if (index < endIndex) {
          char ch = str.charAt(index);
          if (ch == '0') {
            ++index;
            if (index < endIndex) {
              ch = str.charAt(index);
              if ((ch == 'b') || (ch == 'B')) {
                // skip the binary prefix "0b" or "0B"
                pos.setIndex(index + 1);
                return 2;
              } else if ((ch == 'x') || (ch == 'X')) {
                // skip the hex prefix "0x" or "0X"
                pos.setIndex(index + 1);
                return 16;
              }
            }
            // since there is a leading 0, the number is treated
            // as a octal number. Note that do NOT skip the leading
            // prefix '0'.
            return 8;
          }
        }
        return defaultRadix;
    }
  }

  /**
   * Parse an unsigned int value in a special radix (2, 4, 8, or 16). Since Java
   * has no unsigned integral type, the returned value is still signed int, but
   * the overflow condition is modified for the unsigned int. After calling this
   * function, the pos.index is set to the position where the parsing stopped.
   * If an error occurs during the parsing, the pos.errorCode is set to the
   * error code, and the pos.errorIndex is set to the current where the error
   * occurs; otherwise, pos.errorCode is set to {@link ErrorCode#NONE} and
   * pos.errorIndex is set to -1. Note that this function does NOT skip the
   * leading whitespace, nor does it geet the radix prefix and sign. In order to
   * do that, call {@link #skipNonBlanks(ParsingPosition, CharSequence, int)},
   * {@link #getRadix(ParsingPosition, CharSequence, int, int, int)} and
   * {@link #getSign(ParsingPosition, CharSequence, int, char, char)} before
   * calling this function. <b>IMPORTANT NOTE:</b> this function does not check
   * the validity of the arguments, therefore, the caller MUST make sure that
   * the arguments are valid.
   *
   * @param pos
   *          a {@link ParsingPosition} object indicate the current position in
   *          the character sequence to be parsed; after calling this function,
   *          the current of the position would be changed.
   * @param str
   *          the text segment. It can't be null.
   * @param endIndex
   *          the end current of the text segment. It must satisfies that 0 <=
   *          this.index <= endIndex <= input.length(); The whole text segment
   *          is within the range [this.index, endIndex) of input.
   * @param sign
   *          the sign of the integer to be parsed.
   * @param radix
   *          the radix of the integral value to be parsed. It must be one of 2,
   *          8, or 16.
   * @param maxValue
   *          the maximum allowed value of the number to be parsed. Note that
   *          this value is treated as an unsigned int.
   * @param maxDigits
   *          the maximum number of digits could be parsed.
   * @return the int value parsed by this function. If this.errorCode is set to
   *         ParseError.EMPTY, 0 is returned; If this.errorCode is set to
   *         ParseError.OVERFLOW, the maxValue is returned.
   * @see #skipNonBlanks(ParsingPosition, CharSequence, int)
   * @see #getRadix(ParsingPosition, CharSequence, int, int, int)
   * @see #getSign(ParsingPosition, CharSequence, int, char, char)
   */
  public static int getSpecialRadixInt(final ParsingPosition pos,
      final CharSequence str, final int endIndex, final int sign,
      final int radix, final int maxValue, final int maxDigits) {
    if ((radix != 2) && (radix != 4) && (radix != 8) && (radix != 16)) {
      throw new IllegalArgumentException("radix is non of 2, 4, 8, 16.");
    }
    if (sign == 0) {
      throw new IllegalArgumentException("sign can't be zero.");
    }
    if (maxDigits <= 0) {
      throw new IllegalArgumentException("Maximum digits must be positive.");
    }

    // let shift = floor(log2(radix))
    final int shift = 31 - Integer.numberOfLeadingZeros(radix);
    final int limit = (maxValue >>> shift);
    // now perform the parsing
    int digitsCount = 0;
    int value = 0;
    pos.clearError();
    int index = pos.getIndex();
    for (; index < endIndex; ++index) {
      if (digitsCount >= maxDigits) {
        break;
      }
      final char ch = str.charAt(index);
      final int digit = Character.digit(ch, radix);
      if (digit < 0) {
        break;
      }
      ++digitsCount; // remember the number of digits have been read
      if (pos.success()) {
        // note that since in Java int is signed, the following
        // condition "value >= 0" is critical for binary radix.
        if ((value >= 0) && (value <= limit)) {
          value <<= shift;
          value |= digit;
        } else { // overflows
          pos.setErrorCode(NUMBER_OVERFLOW);
          pos.setErrorIndex(index);
        }
      }
    }
    pos.setIndex(index);
    if (pos.success()) {
      if (digitsCount == 0) { // no digit has been read
        pos.setErrorCode(EMPTY_VALUE);
        pos.setErrorIndex(index);
      }
      if (sign > 0) {
        return value;
      } else {
        return (- value);
      }
    } else {
      if (sign > 0) {
        return maxValue;
      } else {
        return (- maxValue);
      }
    }
  }

  /**
   * Parse an unsigned long value in a special radix (2, 4, 8, or 16). Since
   * Java has no unsigned integral type, the returned value is still signed
   * long, but the overflow condition is modified for the unsigned long. After
   * calling this function, the pos.index is set to the position where the
   * parsing stopped. If an error occurs during the parsing, the pos.errorCode
   * is set to the error code, and the pos.errorIndex is set to the current
   * where the error occurs; otherwise, pos.errorCode is set to
   * {@link ErrorCode#NONE} and pos.errorIndex is set to -1. Note that this
   * function does NOT skip the leading whitespace, nor does it geet the radix
   * prefix and sign. In order to do that, call
   * {@link #skipNonBlanks(ParsingPosition, CharSequence, int)},
   * {@link #getRadix(ParsingPosition, CharSequence, int, int, int)} and
   * {@link #getSign(ParsingPosition, CharSequence, int, char, char)} before
   * calling this function. <b>IMPORTANT NOTE:</b> this function does not check
   * the validity of the arguments, therefore, the caller MUST make sure that
   * the arguments are valid.
   *
   * @param pos
   *          a {@link ParsingPosition} object indicate the current position in
   *          the character sequence to be parsed; after calling this function,
   *          the current of the position would be changed.
   * @param str
   *          the text segment. It can't be null.
   * @param endIndex
   *          the end current of the text segment. It must satisfies that 0 <=
   *          this.index <= endIndex <= input.length(); The whole text segment
   *          is within the range [this.index, endIndex) of input.
   * @param radix
   *          the radix of the integral value to be parsed. It must be one of 2,
   *          8, or 16.
   * @return the long value parsed by this function. If this.errorCode is set to
   *         ParseError.EMPTY, 0 is returned; If this.errorCode is set to
   *         ParseError.OVERFLOW, the UNSIGNED_LONG_MAX is returned.
   * @param maxDigits
   *          the maximum number of digits could be parsed.
   * @see #skipNonBlanks(ParsingPosition, CharSequence, int)
   * @see #getRadix(ParsingPosition, CharSequence, int, int, int)
   * @see #getSign(ParsingPosition, CharSequence, int, char, char)
   */
  public static long getSpecialRadixLong(final ParsingPosition pos,
      final CharSequence str, final int endIndex, final int sign,
      final int radix, final int maxDigits) {
    if ((radix != 2) && (radix != 4) && (radix != 8) && (radix != 16)) {
      throw new IllegalArgumentException("radix is non of 2, 4, 8, 16.");
    }
    if (sign == 0) {
      throw new IllegalArgumentException("sign can't be zero.");
    }
    if (maxDigits <= 0) {
      throw new IllegalArgumentException("Maximum digits must be positive.");
    }

    // let shift = floor(log2(radix))
    final int shift = 31 - Integer.numberOfLeadingZeros(radix);
    final long limit = (LongUtils.UNSIGNED_MAX >>> shift);
    // now perform the parsing
    int digitsCount = 0;
    long value = 0;
    pos.clearError();
    int index = pos.getIndex();
    for (; index < endIndex; ++index) {
      if (digitsCount >= maxDigits) {
        break;
      }
      final char ch = str.charAt(index);
      final int digit = Character.digit(ch, radix);
      if (digit < 0) {
        break;
      }
      ++digitsCount; // remember the number of digits have been read
      if (pos.success()) {
        // note that since in Java int is signed, the following
        // condition "value >= 0" is critical for binary radix.
        if ((value >= 0) && (value <= limit)) {
          value <<= shift;
          value |= digit;
        } else { // overflows
          pos.setErrorCode(NUMBER_OVERFLOW);
          pos.setErrorIndex(index);
        }
      }
    }
    pos.setIndex(index);
    if (pos.success()) {
      if (digitsCount == 0) { // no digit has been read
        pos.setErrorCode(EMPTY_VALUE);
        pos.setErrorIndex(index);
      }
      if (sign > 0) {
        return value;
      } else {
        return (- value);
      }
    } else {
      if (sign > 0) {
        return LongUtils.UNSIGNED_MAX;
      } else {
        return (- LongUtils.UNSIGNED_MAX);
      }
    }
  }

  /**
   * Parse an signed int value in a decimal radix. After calling this function,
   * the pos.index is set to the position where the parsing stopped. If an error
   * occurs during the parsing, the pos.errorCode is set to the error code, and
   * the pos.errorIndex is set to the current where the error occurs; otherwise,
   * pos.errorCode is set to {@link ErrorCode#NONE} and pos.errorIndex is set to
   * -1. Note that this function does NOT skip the leading whitespace, nor does
   * it geet the radix prefix and sign. In order to do that, call
   * {@link #skipNonBlanks(ParsingPosition, CharSequence, int)},
   * {@link #getRadix(ParsingPosition, CharSequence, int, int, int)} and
   * {@link #getSign(ParsingPosition, CharSequence, int, char, char)} before
   * calling this function. <b>IMPORTANT NOTE:</b> this function does not check
   * the validity of the arguments, therefore, the caller MUST make sure that
   * the arguments are valid. TODO: add support to digit grouping.
   *
   * @param pos
   *          a {@link ParsingPosition} object indicate the current position in
   *          the character sequence to be parsed; after calling this function,
   *          the current of the position would be changed.
   * @param str
   *          the text segment. It can't be null.
   * @param endIndex
   *          the end current of the text segment. It must satisfies that 0 <=
   *          this.index <= endIndex <= input.length(); The whole text segment
   *          is within the range [this.index, endIndex) of input.
   * @param sign
   *          the sign of the integral value to be parsed. A negative value
   *          indicate the value to be parsed is a negative value, otherwise the
   *          value to be parsed is a positive value.
   * @param maxValue
   *          the maximum allowed value of the number to be parsed. Note that
   *          this value is treated as a signed int, and the minimum allowed
   *          value of the number to be parsed is (- maxValue - 1).
   * @param maxDigits
   *          the maximum number of digits could be parsed.
   * @return the integral value parsed by this function. If this.errorCode is
   *         ParseError.EMPTY, returned value is 0; If this.errorCode is
   *         ParseError.OVERFLOW, returned value is maxValue if sign >= 0, or (-
   *         maxValue - 1) if sign < 0.
   * @see #skipNonBlanks(ParsingPosition, CharSequence, int)
   * @see #getRadix(ParsingPosition, CharSequence, int, int, int)
   * @see #getSign(ParsingPosition, CharSequence, int, char, char)
   */
  public static int getDecimalInt(final ParsingPosition pos,
      final CharSequence str, final int endIndex, final int sign,
      final int maxValue, final int maxDigits) {
    if (sign == 0) {
      throw new IllegalArgumentException("sign can't be zero.");
    }
    if (maxDigits <= 0) {
      throw new IllegalArgumentException("Maximum digits must be positive.");
    }
    // note that we use a trick to deal with the overflow of integers.
    // since the maximum absolute value of a negative int can not be represented
    // as a positive int, we treat all int as a negative int, and fix it at last
    // if
    // if is really a positive int.
    //
    final int minValue = (- maxValue - 1);
    final int limit = minValue / 10;
    int digitsCount = 0;
    int value = 0;
    pos.clearError();
    int index = pos.getIndex();
    for (; index < endIndex; ++index) {
      if (digitsCount >= maxDigits) {
        break;
      }
      final char ch = str.charAt(index);
      final int digit = Character.digit(ch, 10);
      if (digit < 0) {
        break;
      }
      // remember the number of digits has been read
      ++digitsCount;
      // note that if the value overflow, the remained digits should still be
      // read, but we don't need to accumulate the absolute value for the
      // overflow
      // value.
      if (pos.success()) {
        if (value < limit) {
          pos.setErrorCode(NUMBER_OVERFLOW);
          pos.setErrorIndex(index);
        } else {
          value *= 10; // never overflow, since value >= limit
          value -= digit; // may cause overflow
          // check for overflow cased by the above,
          // note that the following checking will miss one special case:
          // the sign >= 0 and the value is minValue.
          // so it should be fixed in the following code.
          if ((value > 0) || (value < minValue)) {
            pos.setErrorCode(NUMBER_OVERFLOW);
            pos.setErrorIndex(index);
          }
        }
      }
    }
    pos.setIndex(index);
    if (digitsCount == 0) { // no digits are read
      pos.setErrorCode(EMPTY_VALUE);
      pos.setErrorIndex(index);
      return 0;
    } else if (pos.getErrorCode() == NUMBER_OVERFLOW) {
      return (sign >= 0 ? maxValue : minValue);
    } else if (sign >= 0) {
      if (value == minValue) {
        // it's also an case of overflow
        pos.setErrorCode(NUMBER_OVERFLOW);
        pos.setErrorIndex(index - 1);
        return maxValue;
      } else {
        return (- value); // return the fixed value
      }
    } else {
      return value;
    }
  }

  /**
   * Parse an signed long value in a decimal radix. After calling this function,
   * the pos.index is set to the position where the parsing stopped. If an error
   * occurs during the parsing, the pos.errorCode is set to the error code, and
   * the pos.errorIndex is set to the current where the error occurs; otherwise,
   * pos.errorCode is set to {@link ErrorCode#NONE} and pos.errorIndex is set to
   * -1. Note that this function does NOT skip the leading whitespace, nor does
   * it geet the radix prefix and sign. In order to do that, call
   * {@link #skipNonBlanks(ParsingPosition, CharSequence, int)},
   * {@link #getRadix(ParsingPosition, CharSequence, int, int, int)} and
   * {@link #getSign(ParsingPosition, CharSequence, int, char, char)} before
   * calling this function. <b>IMPORTANT NOTE:</b> this function does not check
   * the validity of the arguments, therefore, the caller MUST make sure that
   * the arguments are valid. TODO: add support to digit grouping.
   *
   * @param pos
   *          a {@link ParsingPosition} object indicate the current position in
   *          the character sequence to be parsed; after calling this function,
   *          the current of the position would be changed.
   * @param str
   *          the text segment. It can't be null.
   * @param endIndex
   *          the end current of the text segment. It must satisfies that 0 <=
   *          this.index <= endIndex <= input.length(); The whole text segment
   *          is within the range [this.index, endIndex) of input.
   * @param sign
   *          the sign of the integral value to be parsed. A negative value
   *          indicate the value to be parsed is a negative value, otherwise the
   *          value to be parsed is a positive value.
   * @param maxDigits
   *          the maximum number of digits could be parsed.
   * @return the integral value parsed by this function. If this.errorCode is
   *         ParseError.EMPTY, returned value is 0; If this.errorCode is
   *         ParseError.OVERFLOW, returned value is Long.MAX_VALUE if sign >= 0,
   *         or Long.MIN_VALUE if sign < 0.
   * @see #skipNonBlanks(ParsingPosition, CharSequence, int)
   * @see #getRadix(ParsingPosition, CharSequence, int, int, int)
   * @see #getSign(ParsingPosition, CharSequence, int, char, char)
   */
  public static long getDecimalLong(final ParsingPosition pos,
      final CharSequence str, final int endIndex, final int sign,
      final int maxDigits) {
    if (sign == 0) {
      throw new IllegalArgumentException("sign can't be zero.");
    }
    if (maxDigits <= 0) {
      throw new IllegalArgumentException("Maximum digits must be positive.");
    }

    final long limit = Long.MIN_VALUE / 10;
    int digitsCount = 0;
    long value = 0;
    pos.clearError();
    int index = pos.getIndex();
    for (; index < endIndex; ++index) {
      if (digitsCount >= maxDigits) {
        break;
      }
      final char ch = str.charAt(index);
      final int digit = Character.digit(ch, 10);
      if (digit < 0) {
        break;
      }
      // remember the number of digits has been read
      ++digitsCount;
      // note that if the value overflow, the remained digits should still be
      // read, but we don't need to accumulate the absolute value for the
      // overflow
      // value.
      if (pos.success()) {
        if (value < limit) {
          pos.setErrorCode(NUMBER_OVERFLOW);
          pos.setErrorIndex(index);
        } else {
          value *= 10; // never overflow, since value >= limit
          value -= digit; // may cause overflow
          // check for overflow cased by the above,
          // note that the following checking will miss one special case:
          // the sign >= 0 and the value is minValue.
          // so it should be fixed in the following code.
          if ((value > 0) || (value < Long.MIN_VALUE)) {
            pos.setErrorCode(NUMBER_OVERFLOW);
            pos.setErrorIndex(index);
          }
        }
      }
    }
    pos.setIndex(index);
    if (digitsCount == 0) { // no digits are read
      pos.setErrorCode(EMPTY_VALUE);
      pos.setErrorIndex(index);
      return 0;
    } else if (pos.getErrorCode() == NUMBER_OVERFLOW) {
      return (sign >= 0 ? Long.MAX_VALUE : Long.MIN_VALUE);
    } else if (sign >= 0) {
      if (value == Long.MIN_VALUE) {
        // it's also an case of overflow
        pos.setErrorCode(NUMBER_OVERFLOW);
        pos.setErrorIndex(index - 1);
        return Long.MAX_VALUE;
      } else {
        return (- value); // return the fixed value
      }
    } else {
      return value;
    }
  }

  /**
   * Parses a char value.
   *
   * @param pos
   *          a {@link ParsingPosition} object indicate the current position in
   *          the character sequence to be parsed; after calling this function,
   *          the current of the position would be changed, and if any error
   *          occurs during the parsing, the error code of this object will also
   *          be set.
   * @param str
   *          the text segment to be parsed.
   * @param endIndex
   *          the end current of the text segment.
   * @param options
   *          the parsing options.
   * @return the parsed value.
   */
  public static char parseChar(final ParsingPosition pos, final CharSequence str,
      final int endIndex, final ParseOptions options) {
    // skip the leading white space if necessary
    if (! options.isKeepBlank()) {
      skipBlanks(pos, str, endIndex);
      if (pos.fail()) {
        return (char) 0;
      }
    }
    final int index = pos.getIndex();
    if ((index >= endIndex) || (index >= str.length())) {
      pos.setErrorCode(EMPTY_VALUE);
      pos.setErrorIndex(index);
      return (char) 0;
    } else {
      return str.charAt(index);
    }
  }

  /**
   * Parses a boolean value.
   *
   * @param str
   *          the text segment to be parsed.
   * @param startIndex
   *          the start current of the text segment.
   * @param endIndex
   *          the end current of the text segment.
   * @param options
   *          the parse options.
   * @return the parsed value.
   * @throws TextParseException
   *           if any error occurs during parsing.
   * @throws IndexOutOfBoundsException
   *           if startIndex < 0 or startIndex > endIndex or endIndex >
   *           str.length().
   */
  public static char parseChar(final CharSequence str, final int startIndex,
      final int endIndex, final ParseOptions options) throws TextParseException {
    final ParsingPosition pos = new ParsingPosition(startIndex);
    final char result = parseChar(pos, str, endIndex, options);
    if (pos.fail()) {
      throw new TextParseException(str, startIndex, endIndex, pos);
    }
    return result;
  }

  /**
   * Parses a boolean value.
   *
   * @param str
   *          the text segment to be parsed.
   * @param startIndex
   *          the start current of the text segment.
   * @param endIndex
   *          the end current of the text segment.
   * @return the parsed value.
   * @throws TextParseException
   *           if any error occurs during parsing.
   * @throws IndexOutOfBoundsException
   *           if startIndex < 0 or startIndex > endIndex or endIndex >
   *           str.length().
   */
  public static char parseChar(final CharSequence str, final int startIndex,
      final int endIndex) throws TextParseException {
    final ParsingPosition pos = new ParsingPosition(startIndex);
    pos.reset(startIndex);
    final char result = parseChar(pos, str, endIndex,
        ParseOptions.DEFAULT_KEEP_BLANKS);
    if (pos.fail()) {
      throw new TextParseException(str, startIndex, endIndex, pos);
    }
    return result;
  }

  /**
   * Parses a char value.
   *
   * @param str
   *          the text segment to be parsed.
   * @return the parsed value.
   * @throws TextParseException
   *           if any error occurs during parsing.
   */
  public static char parseChar(final CharSequence str)
      throws TextParseException {
    final ParsingPosition pos = new ParsingPosition();
    final char result = parseChar(pos, str, str.length(),
        ParseOptions.DEFAULT_KEEP_BLANKS);
    if (pos.fail()) {
      throw new TextParseException(str, pos);
    }
    return result;
  }
//
//  public static Date parseDate(final ParsingPosition pos, final CharSequence str,
//      final int endIndex, final ParseOptions options,
//      final NumberFormatSymbols symbols, final String formatPattern) {
//    // skip the leading white space if necessary
//    if (! options.isKeepBlank()) {
//      skipBlanks(pos, str, endIndex);
//      if (pos.fail()) {
//        return null;
//      }
//    }
//    // / TODO: use a better parsing method
//    final SimpleDateFormat df = new SimpleDateFormat(formatPattern);
//    final Date result = df.parse(str.toString(), pos);
//    if (result == null) {
//      pos.setErrorCode(ErrorCode.INVALID_SYNTAX);
//      return null;
//    } else if (pos.getIndex() > endIndex) {
//      pos.setErrorCode(ErrorCode.INVALID_SYNTAX);
//      return null;
//    } else {
//      return result;
//    }
//  }
//
//  public static Date parseDate(final CharSequence str, final int startIndex,
//      final int endIndex, final ParseOptions options) throws TextParseException {
//    final ParsePositionPool pool = ParsePositionPool.getInstance();
//    final ParsingPosition pos = pool.borrow();
//    try {
//      pos.reset(startIndex);
//      final Date result = parseDate(pos, str, endIndex, options,
//          NumberFormatSymbols.DEFAULT, DateUtils.DEFAULT_PATTERN);
//      if (pos.fail()) {
//        throw new TextParseException(str, startIndex, endIndex, pos);
//      } else {
//        assert (result != null);
//        return result;
//      }
//    } finally {
//      pool.restore(pos);
//    }
//  }
//
//  public static Date parseDate(final CharSequence str, final int startIndex,
//      final int endIndex) throws TextParseException {
//    final ParsePositionPool pool = ParsePositionPool.getInstance();
//    final ParsingPosition pos = pool.borrow();
//    try {
//      pos.reset(startIndex);
//      final Date result = parseDate(pos, str, endIndex, ParseOptions.DEFAULT,
//          NumberFormatSymbols.DEFAULT, DateUtils.DEFAULT_PATTERN);
//      if (pos.fail()) {
//        throw new TextParseException(str, startIndex, endIndex, pos);
//      } else {
//        assert (result != null);
//        return result;
//      }
//    } finally {
//      pool.restore(pos);
//    }
//  }
//
//  public static Date parseDate(final CharSequence str)
//      throws TextParseException {
//    final ParsePositionPool pool = ParsePositionPool.getInstance();
//    final ParsingPosition pos = pool.borrow();
//    try {
//      final Date result = parseDate(pos, str, str.length(),
//          ParseOptions.DEFAULT, FormatSymbols.DEFAULT,
//          DateUtils.DEFAULT_PATTERN);
//      if (pos.fail()) {
//        throw new TextParseException(str, pos);
//      } else {
//        assert (result != null);
//        return result;
//      }
//    } finally {
//      pool.restore(pos);
//    }
//  }
}
