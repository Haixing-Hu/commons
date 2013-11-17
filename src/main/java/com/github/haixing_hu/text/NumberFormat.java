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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Locale;

import com.github.haixing_hu.lang.ArrayUtils;
import com.github.haixing_hu.lang.ByteUtils;
import com.github.haixing_hu.lang.Cloneable;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.lang.IntUtils;
import com.github.haixing_hu.lang.ShortUtils;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

import static com.github.haixing_hu.lang.Argument.*;
import static com.github.haixing_hu.text.ErrorCode.*;
import static com.github.haixing_hu.text.FormatUtils.*;
import static com.github.haixing_hu.text.ParseUtils.*;

/**
 * The {@link NumberFormat} is used to parse and format numbers.
 *
 * @author Haixing Hu
 */
public final class NumberFormat implements Cloneable<NumberFormat> {

  public static final int MAX_GROUP_COUNT = 32;

  public static final int MAX_FLOAT_DIGIT_COUNT = 34;

  public static final int MAX_FLOAT_EXPONENT = 340;

  public static final int DEFAULT_FLAGS = FormatFlag.DEFAULT;

  public static final int DEFAULT_RADIX = 10;

  public static final int DEFAULT_MAX_DIGITS = Integer.MAX_VALUE;

  public static final int MAX_REAL_DIGIT_COUNT = 34;

  public static final int MAX_REAL_EXPONENT = 340;

  public static final int DEFAULT_INT_PRECISION = 1;

  public static final int DEFAULT_REAL_PRECISION = 6;

  public static final int DEFAULT_WIDTH = 0;

  public static final int DEFAULT_FILL = ' ';

  private NumberFormatSymbols symbols;
  private NumberFormatOptions options;
  private final transient ParsePosition position;
  private final transient StringBuilder builder;
  private transient char[] buffer;


  public NumberFormat() {
    symbols = new NumberFormatSymbols();
    options = new NumberFormatOptions();
    position = new ParsePosition();
    builder = new StringBuilder();
    buffer = ArrayUtils.EMPTY_CHAR_ARRAY;
  }

  public NumberFormat(final Locale locale) {
    symbols = new NumberFormatSymbols(locale);
    options = new NumberFormatOptions();
    position = new ParsePosition();
    builder = new StringBuilder();
    buffer = ArrayUtils.EMPTY_CHAR_ARRAY;
  }

  public void reset() {
    symbols.reset();
    options.reset();
    position.reset();
  }

  public void reset(final Locale locale) {
    symbols.reset(locale);
    options.reset();
    position.reset();
  }

  public NumberFormatSymbols getSymbols() {
    return symbols;
  }

  public void setSymbols(final NumberFormatSymbols symbols) {
    this.symbols = requireNonNull("symbols", symbols);
  }

  public NumberFormatOptions getOptions() {
    return options;
  }

  public void setOptions(final NumberFormatOptions options) {
    this.options = requireNonNull("options", options);
  }

  public ParsePosition getParsePosition() {
    return position;
  }

  public int getParseIndex() {
    return position.getIndex();
  }

  public int getErrorIndex() {
    return position.getErrorIndex();
  }

  public int getErrorCode() {
    return position.getErrorCode();
  }

  public boolean success() {
    return position.success();
  }

  public boolean fail() {
    return position.fail();
  }

  /**
   * Parses a <code>byte</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>0</code> and stops at the character
   *          before the index <code>str.length()</code>.
   * @return the parsed value.
   */
  public byte parseByte(final CharSequence str) {
    return parseByte(str, 0, str.length());
  }

  /**
   * Parses a <code>byte</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>startIndex</code> and stops at the
   *          character before the index <code>str.length()</code>.
   * @param startIndex
   *          the index where to start parsing.
   * @return the parsed value.
   */
  public byte parseByte(final CharSequence str, final int startIndex) {
    return parseByte(str, startIndex, str.length());
  }

  /**
   * Parses a <code>byte</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>startIndex</code> and stops at the
   *          character before the index <code>endIndex</code>.
   * @param startIndex
   *          the index where to start parsing.
   * @param endIndex
   *          the index where to end parsing.
   * @return the parsed value.
   */
  public byte parseByte(final CharSequence str, final int startIndex,
      final int endIndex) {
    // check the index bounds
    requireIndexInCloseRange(startIndex, 0, str.length());
    requireIndexInCloseRange(endIndex, 0, str.length());
    // reset the parse position
    position.reset(startIndex);
    // skip the leading white space if necessary
    if (! options.isKeepBlank()) {
      skipBlanks(position, str, endIndex);
      if (position.fail()) {
        return 0;
      }
    }
    // get the sign
    final int sign = getSign(position, str, endIndex,
        symbols.getPositiveSign(), symbols.getNegativeSign());
    // get the radix
    final int radix = getRadix(position, str, endIndex, options.getFlags(),
        options.getDefaultRadix());
    // parse the integer
    if (radix == 10) {
      // parse the value in decimal radix
      return (byte) getDecimalInt(position, str, endIndex, sign,
          Byte.MAX_VALUE, options.getMaxDigits());
    } else {
      // parse the value in a special radix, ignoring the sign symbol.
      return (byte) getSpecialRadixInt(position, str, endIndex, sign, radix,
          ByteUtils.UNSIGNED_MAX, options.getMaxDigits());
    }
  }

  /**
   * Parses a <code>short</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>0</code> and stops at the character
   *          before the index <code>str.length()</code>.
   * @return the parsed value.
   */
  public short parseShort(final CharSequence str) {
    return parseShort(str, 0, str.length());
  }

  /**
   * Parses a <code>short</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>startIndex</code> and stops at the
   *          character before the index <code>str.length()</code>.
   * @param startIndex
   *          the index where to start parsing.
   * @return the parsed value.
   */
  public short parseShort(final CharSequence str, final int startIndex) {
    return parseShort(str, startIndex, str.length());
  }

  /**
   * Parses a <code>byte</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>startIndex</code> and stops at the
   *          character before the index <code>endIndex</code>.
   * @param startIndex
   *          the index where to start parsing.
   * @param endIndex
   *          the index where to end parsing.
   * @return the parsed value.
   */
  public short parseShort(final CharSequence str, final int startIndex,
      final int endIndex) {
    // check the index bounds
    requireIndexInCloseRange(startIndex, 0, str.length());
    requireIndexInCloseRange(endIndex, 0, str.length());
    // reset the parse position
    position.reset(startIndex);
    // skip the leading white space if necessary
    if (! options.isKeepBlank()) {
      ParseUtils.skipBlanks(position, str, endIndex);
      if (position.fail()) {
        return 0;
      }
    }
    // get the sign
    final int sign = getSign(position, str, endIndex,
        symbols.getPositiveSign(), symbols.getNegativeSign());
    // get the radix
    final int radix = getRadix(position, str, endIndex, options.getFlags(),
        options.getDefaultRadix());
    // parse the integer
    if (radix == 10) {
      // parse the value in decimal radix
      return (short) getDecimalInt(position, str, endIndex, sign,
          Short.MAX_VALUE, options.getMaxDigits());
    } else {
      // parse the value in a special radix, ignoring the sign symbol.
      return (short) getSpecialRadixInt(position, str, endIndex, sign, radix,
          ShortUtils.UNSIGNED_MAX, options.getMaxDigits());
    }
  }

  /**
   * Parses a <code>int</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>0</code> and stops at the character
   *          before the index <code>str.length()</code>.
   * @return the parsed value.
   */
  public int parseInt(final CharSequence str) {
    return parseInt(str, 0, str.length());
  }

  /**
   * Parses a <code>int</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>startIndex</code> and stops at the
   *          character before the index <code>str.length()</code>.
   * @param startIndex
   *          the index where to start parsing.
   * @return the parsed value.
   */
  public int parseInt(final CharSequence str, final int startIndex) {
    return parseInt(str, startIndex, str.length());
  }

  /**
   * Parses a <code>int</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>startIndex</code> and stops at the
   *          character before the index <code>endIndex</code>.
   * @param startIndex
   *          the index where to start parsing.
   * @param endIndex
   *          the index where to end parsing.
   * @return the parsed value.
   */
  public int parseInt(final CharSequence str, final int startIndex,
      final int endIndex) {
    // check the index bounds
    requireIndexInCloseRange(startIndex, 0, str.length());
    requireIndexInCloseRange(endIndex, 0, str.length());
    // reset the parse position
    position.reset(startIndex);
    // skip the leading white space if necessary
    if (! options.isKeepBlank()) {
      skipBlanks(position, str, endIndex);
      if (position.fail()) {
        return 0;
      }
    }
    // get the sign
    final int sign = getSign(position, str, endIndex,
        symbols.getPositiveSign(), symbols.getNegativeSign());
    // get the radix
    final int radix = getRadix(position, str, endIndex, options.getFlags(),
        options.getDefaultRadix());
    // parse the integer
    if (radix == 10) {
      // parse the value in decimal radix
      return getDecimalInt(position, str, endIndex, sign, Integer.MAX_VALUE,
          options.getMaxDigits());
    } else {
      // parse the value in a special radix, ignoring the sign symbol.
      return getSpecialRadixInt(position, str, endIndex, sign, radix,
          IntUtils.UNSIGNED_MAX, options.getMaxDigits());
    }
  }

  /**
   * Parses a <code>long</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>0</code> and stops at the character
   *          before the index <code>str.length()</code>.
   * @return the parsed value.
   */
  public long parseLong(final CharSequence str) {
    return parseLong(str, 0, str.length());
  }

  /**
   * Parses a <code>byte</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>startIndex</code> and stops at the
   *          character before the index <code>str.length()</code>.
   * @param startIndex
   *          the index where to start parsing.
   * @return the parsed value.
   */
  public long parseLong(final CharSequence str, final int startIndex) {
    return parseLong(str, startIndex, str.length());
  }

  /**
   * Parses a <code>byte</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>startIndex</code> and stops at the
   *          character before the index <code>endIndex</code>.
   * @param startIndex
   *          the index where to start parsing.
   * @param endIndex
   *          the index where to end parsing.
   * @return the parsed value.
   */
  public long parseLong(final CharSequence str, final int startIndex,
      final int endIndex) {
    // check the index bounds
    requireIndexInCloseRange(startIndex, 0, str.length());
    requireIndexInCloseRange(endIndex, 0, str.length());
    // reset the parse position
    position.reset(startIndex);
    // skip the leading white space if necessary
    if (! options.isKeepBlank()) {
      skipBlanks(position, str, endIndex);
      if (position.fail()) {
        return 0;
      }
    }
    // get the sign
    final int sign = getSign(position, str, endIndex,
        symbols.getPositiveSign(), symbols.getNegativeSign());
    // get the radix
    final int radix = getRadix(position, str, endIndex, options.getFlags(),
        options.getDefaultRadix());
    // parse the number
    if (radix == 10) {
      // parse the value in decimal radix
      return getDecimalLong(position, str, endIndex, sign,
          options.getMaxDigits());
    } else {
      // parse the value in a special radix, ignoring the sign symbol.
      return getSpecialRadixLong(position, str, endIndex, sign, radix,
          options.getMaxDigits());
    }
  }

  /**
   * Parses a <code>float</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>0</code> and stops at the character
   *          before the index <code>str.length()</code>.
   * @return the parsed value.
   */
  public float parseFloat(final CharSequence str) {
    return parseFloat(str, 0, str.length());
  }

  /**
   * Parses a <code>float</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>startIndex</code> and stops at the
   *          character before the index <code>str.length()</code>.
   * @param startIndex
   *          the index where to start parsing.
   * @return the parsed value.
   */
  public float parseFloat(final CharSequence str, final int startIndex) {
    return parseFloat(str, startIndex, str.length());
  }

  /**
   * Parses a <code>float</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>startIndex</code> and stops at the
   *          character before the index <code>endIndex</code>.
   * @param startIndex
   *          the index where to start parsing.
   * @param endIndex
   *          the index where to end parsing.
   * @return the parsed value.
   */
  public float parseFloat(final CharSequence str, final int startIndex,
      final int endIndex) {
    // check the index bounds
    requireIndexInCloseRange(startIndex, 0, str.length());
    requireIndexInCloseRange(endIndex, 0, str.length());
    // reset the parse position
    position.reset(startIndex);
    // skip the leading white space if necessary
    if (! options.isKeepBlank()) {
      skipBlanks(position, str, endIndex);
      if (position.fail()) {
        return 0;
      }
    }
    // TODO: implement the parsing of float numbers.
    final String num_str = str.subSequence(position.getIndex(), endIndex).toString();
    try {
      return Float.valueOf(num_str);
    } catch (final NumberFormatException e) {
      position.setErrorCode(UNKNOWN_ERROR);
      position.setErrorIndex(position.getIndex());
      return 0.0f;
    }
  }

  /**
   * Parses a <code>double</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>0</code> and stops at the character
   *          before the index <code>str.length()</code>.
   * @return the parsed value.
   */
  public double parseDouble(final CharSequence str) {
    return parseDouble(str, 0, str.length());
  }

  /**
   * Parses a <code>double</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>startIndex</code> and stops at the
   *          character before the index <code>str.length()</code>.
   * @param startIndex
   *          the index where to start parsing.
   * @return the parsed value.
   */
  public double parseDouble(final CharSequence str, final int startIndex) {
    return parseDouble(str, startIndex, str.length());
  }

  /**
   * Parses a <code>double</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>startIndex</code> and stops at the
   *          character before the index <code>endIndex</code>.
   * @param startIndex
   *          the index where to start parsing.
   * @param endIndex
   *          the index where to end parsing.
   * @return the parsed value.
   */
  public double parseDouble(final CharSequence str, final int startIndex,
      final int endIndex) {
    // check the index bounds
    requireIndexInCloseRange(startIndex, 0, str.length());
    requireIndexInCloseRange(endIndex, 0, str.length());
    // reset the parse position
    position.reset(startIndex);
    // skip the leading white space if necessary
    if (! options.isKeepBlank()) {
      skipBlanks(position, str, endIndex);
      if (position.fail()) {
        return 0;
      }
    }
    // TODO: implement the parsing of double numbers.
    final String num_str = str.subSequence(position.getIndex(), endIndex).toString();
    try {
      return Double.valueOf(num_str);
    } catch (final NumberFormatException e) {
      position.setErrorCode(UNKNOWN_ERROR);
      position.setErrorIndex(position.getIndex());
      return 0.0f;
    }
  }

  /**
   * Parses a <code>BigInteger</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>0</code> and stops at the character
   *          before the index <code>str.length()</code>.
   * @return the parsed value.
   */
  public BigInteger parseBigInteger(final CharSequence str) {
    return parseBigInteger(str, 0, str.length());
  }

  /**
   * Parses a <code>BigInteger</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>startIndex</code> and stops at the
   *          character before the index <code>str.length()</code>.
   * @param startIndex
   *          the index where to start parsing.
   * @return the parsed value.
   */
  public BigInteger parseBigInteger(final CharSequence str, final int startIndex) {
    return parseBigInteger(str, startIndex, str.length());
  }

  /**
   * Parses a <code>BigInteger</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>startIndex</code> and stops at the
   *          character before the index <code>endIndex</code>.
   * @param startIndex
   *          the index where to start parsing.
   * @param endIndex
   *          the index where to end parsing.
   * @return the parsed value.
   */
  public BigInteger parseBigInteger(final CharSequence str,
      final int startIndex, final int endIndex) {
    // check the index bounds
    requireIndexInCloseRange(startIndex, 0, str.length());
    requireIndexInCloseRange(endIndex, 0, str.length());
    // reset the parse position
    position.reset(startIndex);
    // skip the leading white space if necessary
    if (! options.isKeepBlank()) {
      skipBlanks(position, str, endIndex);
      if (position.fail()) {
        return null;
      }
    }
    try {
      // TODO: use a better parsing method
      final String text = str.subSequence(position.getIndex(), endIndex).toString();
      return new BigInteger(text);
    } catch (final NumberFormatException e) {
      position.setErrorCode(INVALID_SYNTAX);
      position.setErrorIndex(position.getIndex());
      return null;
    }
  }

  /**
   * Parses a <code>BigDecimal</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>0</code> and stops at the character
   *          before the index <code>str.length()</code>.
   * @return the parsed value.
   */
  public BigDecimal parseBigDecimal(final CharSequence str) {
    return parseBigDecimal(str, 0, str.length());
  }

  /**
   * Parses a <code>BigDecimal</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>startIndex</code> and stops at the
   *          character before the index <code>str.length()</code>.
   * @param startIndex
   *          the index where to start parsing.
   * @return the parsed value.
   */
  public BigDecimal parseBigDecimal(final CharSequence str, final int startIndex) {
    return parseBigDecimal(str, startIndex, str.length());
  }

  /**
   * Parses a <code>BigDecimal</code> value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index <code>startIndex</code> and stops at the
   *          character before the index <code>endIndex</code>.
   * @param startIndex
   *          the index where to start parsing.
   * @param endIndex
   *          the index where to end parsing.
   * @return the parsed value.
   */
  public BigDecimal parseBigDecimal(final CharSequence str,
      final int startIndex, final int endIndex) {
    // check the index bounds
    requireIndexInCloseRange(startIndex, 0, str.length());
    requireIndexInCloseRange(endIndex, 0, str.length());
    // reset the parse position
    position.reset(startIndex);
    // skip the leading white space if necessary
    if (! options.isKeepBlank()) {
      skipBlanks(position, str, endIndex);
      if (position.fail()) {
        return null;
      }
    }
    try {
      // TODO: use a better parsing method
      final String text = str.subSequence(position.getIndex(), endIndex).toString();
      return new BigDecimal(text);
    } catch (final NumberFormatException e) {
      position.setErrorCode(INVALID_SYNTAX);
      position.setErrorIndex(position.getIndex());
      return null;
    }
  }

  /**
   * Formats a <code>byte</code> value.
   *
   * @param value
   *          the value to be formated.
   * @return the formatted result.
   */
  public String formatByte(final byte value) {
    builder.setLength(0);
    formatByte(value, builder);
    return builder.toString();
  }

  /**
   * Formats a <code>byte</code> value.
   *
   * @param value
   *          the value to be formated.
   * @param output
   *          the {@link StringBuilder} where to append the formatted result.
   * @return the output {@link StringBuilder}.
   */
  public StringBuilder formatByte(final byte value, final StringBuilder output) {
    // the buffer must be larger enough to hold the sign, radix prefix, group
    // separator, and all digits
    final int bufferSize = Math.max(options.getIntPrecision(), Byte.SIZE) + 10;
    if (buffer.length < bufferSize) {
      buffer = new char[bufferSize];
    }
    final int startIndex = putIntBackward(value,
        (value & ByteUtils.UNSIGNED_MAX), options, symbols, buffer,
        buffer.length);
    putFormatResult(options.getFlags(), options.getWidth(), options.getFill(),
        buffer, startIndex, buffer.length, output);
    return output;
  }

  /**
   * Formats a <code>short</code> value.
   *
   * @param value
   *          the value to be formated.
   * @return the formatted result.
   */
  public String formatShort(final short value) {
    builder.setLength(0);
    formatShort(value, builder);
    return builder.toString();
  }

  /**
   * Formats a <code>short</code> value.
   *
   * @param value
   *          the value to be formated.
   * @param output
   *          the {@link StringBuilder} where to append the formatted result.
   * @return the output {@link StringBuilder}.
   */
  public StringBuilder formatShort(final short value, final StringBuilder output) {
    // the buffer must be larger enough to hold the sign, radix prefix, group
    // separator, and all digits
    final int bufferSize = Math.max(options.getIntPrecision(), Short.SIZE) + 10;
    if (buffer.length < bufferSize) {
      buffer = new char[bufferSize];
    }
    final int startIndex = putIntBackward(value,
        (value & ShortUtils.UNSIGNED_MAX), options, symbols, buffer,
        buffer.length);
    putFormatResult(options.getFlags(), options.getWidth(), options.getFill(),
        buffer, startIndex, buffer.length, output);
    return output;
  }

  /**
   * Formats a <code>int</code> value.
   *
   * @param value
   *          the value to be formated.
   * @return the formatted result.
   */
  public String formatInt(final int value) {
    builder.setLength(0);
    formatInt(value, builder);
    return builder.toString();
  }

  /**
   * Formats a <code>int</code> value.
   *
   * @param value
   *          the value to be formated.
   * @param output
   *          the {@link StringBuilder} where to append the formatted result.
   * @return the output {@link StringBuilder}.
   */
  public StringBuilder formatInt(final int value, final StringBuilder output) {
    // the buffer must be larger enough to hold the sign, radix prefix, group
    // separator, and all digits
    final int bufferSize = Math.max(options.getIntPrecision(), Integer.SIZE) + 10;
    if (buffer.length < bufferSize) {
      buffer = new char[bufferSize];
    }
    final int startIndex = putIntBackward(value,
        (value & IntUtils.UNSIGNED_MAX), options, symbols, buffer,
        buffer.length);
    putFormatResult(options.getFlags(), options.getWidth(), options.getFill(),
        buffer, startIndex, buffer.length, output);
    return output;
  }

  /**
   * Formats a <code>long</code> value.
   *
   * @param value
   *          the value to be formated.
   * @return the formatted result.
   */
  public String formatLong(final long value) {
    builder.setLength(0);
    formatLong(value, builder);
    return builder.toString();
  }

  /**
   * Formats a <code>long</code> value.
   *
   * @param value
   *          the value to be formated.
   * @param output
   *          the {@link StringBuilder} where to append the formatted result.
   * @return the output {@link StringBuilder}.
   */
  public StringBuilder formatLong(final long value, final StringBuilder output) {
    // the buffer must be larger enough to hold the sign, radix prefix, group
    // separator, and all digits
    final int bufferSize = Math.max(options.getIntPrecision(), Long.SIZE) + 10;
    if (buffer.length < bufferSize) {
      buffer = new char[bufferSize];
    }
    final int startIndex = putLongBackward(value, options, symbols, buffer,
        buffer.length);
    putFormatResult(options.getFlags(), options.getWidth(), options.getFill(),
        buffer, startIndex, buffer.length, output);
    return output;
  }

  /**
   * Formats a <code>float</code> value.
   *
   * @param value
   *          the value to be formated.
   * @return the formatted result.
   */
  public String formatFloat(final float value) {
    builder.setLength(0);
    formatFloat(value, builder);
    return builder.toString();
  }

  /**
   * Formats a <code>float</code> value.
   *
   * @param value
   *          the value to be formated.
   * @param output
   *          the {@link StringBuilder} where to append the formatted result.
   * @return the output {@link StringBuilder}.
   */
  public StringBuilder formatFloat(final float value, final StringBuilder output) {
    // FIXME: apply the format options and symbols
    final String str = Float.toString(value);
    output.append(str);
    return output;
  }

  /**
   * Formats a <code>double</code> value.
   *
   * @param value
   *          the value to be formated.
   * @return the formatted result.
   */
  public String formatDouble(final double value) {
    builder.setLength(0);
    formatDouble(value, builder);
    return builder.toString();
  }

  /**
   * Formats a <code>double</code> value.
   *
   * @param value
   *          the value to be formated.
   * @param output
   *          the {@link StringBuilder} where to append the formatted result.
   * @return the output {@link StringBuilder}.
   */
  public StringBuilder formatDouble(final double value, final StringBuilder output) {
    // FIXME: apply the format options and symbols
    final String str = Double.toString(value);
    output.append(str);
    return output;
  }

  /**
   * Formats a <code>BigInteger</code> value.
   *
   * @param value
   *          the value to be formated.
   * @return the formatted result.
   */
  public String formatBigInteger(final BigInteger value) {
    builder.setLength(0);
    formatBigInteger(value, builder);
    return builder.toString();
  }

  /**
   * Formats a {@link BigInteger} value.
   *
   * @param value
   *          the value to be formated.
   * @param output
   *          the {@link StringBuilder} where to append the formatted result.
   * @return the output {@link StringBuilder}.
   */
  public StringBuilder formatBigInteger(final BigInteger value,
      final StringBuilder output) {
    // FIXME: apply the format options and symbols
    final String str = value.toString();
    output.append(str);
    return output;
  }

  /**
   * Formats a <code>BigDecimal</code> value.
   *
   * @param value
   *          the value to be formated.
   * @return the formatted result.
   */
  public String formatBigDecimal(final BigDecimal value) {
    builder.setLength(0);
    formatBigDecimal(value, builder);
    return builder.toString();
  }

  /**
   * Formats a {@link BigDecimal} value.
   *
   * @param value
   *          the value to be formated.
   * @param output
   *          the {@link StringBuilder} where to append the formatted result.
   * @return the output {@link StringBuilder}.
   */
  public StringBuilder formatBigDecimal(final BigDecimal value,
      final StringBuilder output) {
    // FIXME: apply the format options and symbols
    final String str = value.toString();
    output.append(str);
    return output;
  }

  @Override
  public NumberFormat clone() {
    final NumberFormat result = new NumberFormat();
    result.symbols.assign(this.symbols);
    result.options.assign(this.options);
    return result;
  }

  @Override
  public int hashCode() {
    final int multiplier = 17;
    int code = 2;
    code = Hash.combine(code, multiplier, symbols);
    code = Hash.combine(code, multiplier, options);
    code = Hash.combine(code, multiplier, position);
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
    final NumberFormat other = (NumberFormat) obj;
    return symbols.equals(other.symbols)
         && options.equals(other.options)
         && position.equals(other.position);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .appendToString("symbols", symbols.toString())
               .appendToString("options", options.toString())
               .appendToString("position", position.toString())
               .toString();
  }
}
