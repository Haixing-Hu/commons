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

import static com.github.haixing_hu.text.FormatFlag.ALIGN_CENTER;
import static com.github.haixing_hu.text.FormatFlag.ALIGN_LEFT;
import static com.github.haixing_hu.text.FormatFlag.ALIGN_MASK;
import static com.github.haixing_hu.text.FormatFlag.ALIGN_RIGHT;

/**
 * Provides the utility functions for formatting text.
 *
 * @author Haixing Hu
 */
public final class FormatUtils {

  public static char[] getDigits(final FormatFlags flags,
      final NumberFormatSymbols symbols) {
    if (flags.isUppercase()) {
      return symbols.getUppercaseDigits();
    } else {
      return symbols.getLowercaseDigits();
    }
  }

  public static int putSpecialRadixIntBackward(int value, final int radix,
      int precision, final char[] digits, final char[] buffer, int startIndex) {
    assert ((radix == 2) || (radix == 4) || (radix == 8) || (radix == 16));
    // let shift = floor(log2(radix))
    final int shift = 31 - Integer.numberOfLeadingZeros(radix);
    final int mask = (1 << shift) - 1;
    // put the value as an unsigned long
    while (value != 0) {
      final int d = (value & mask);
      buffer[--startIndex] = digits[d];
      value >>>= shift;
      --precision;
    }
    // put the leading zeros to satisfy the precision
    while (precision > 0) {
      assert (startIndex > 0);
      buffer[--startIndex] = digits[0];
      --precision;
    }
    return startIndex;
  }

  public static int putSpecialRadixLongBackward(long value, final int radix,
      int precision, final char[] digits, final char[] buffer, int startIndex) {
    assert ((radix == 2) || (radix == 4) || (radix == 8) || (radix == 16));
    // let shift = floor(log2(radix))
    final int shift = 63 - Long.numberOfLeadingZeros(radix);
    final long mask = (1L << shift) - 1L;
    // put the value as an unsigned long
    while (value != 0) {
      final int d = (int) (value & mask);
      assert (startIndex > 0);
      buffer[--startIndex] = digits[d];
      value >>>= shift;
      --precision;
    }
    // put the leading zeros to satisfy the precision
    while (precision > 0) {
      assert (startIndex > 0);
      buffer[--startIndex] = digits[0];
      --precision;
    }
    return startIndex;
  }


  private static final int[] INT_MIN_ABS = {
    2,1,4,7,4,8,3,6,4,8,
  };

  public static int putDecimalIntAbsBackward(final int value, int precision,
      final char[] digits, final char[] buffer, int startIndex) {
    if (value == Integer.MIN_VALUE) {
      // take special care for Integer.MIN_VALUE, since its absolute value
      // can NOT be represented as a positive int value.
      for (int i = INT_MIN_ABS.length - 1; i >= 0; --i) {
        assert (startIndex > 0);
        final int d = INT_MIN_ABS[i];
        buffer[--startIndex] = digits[d];
        --precision;
      }
    } else {
      // put the absolute value backward
      int absValue = (value >= 0 ? value : (- value));
      while (absValue != 0) {
        final int d = (absValue % 10);
        assert (startIndex > 0);
        buffer[--startIndex] = digits[d];
        absValue /= 10;
        --precision;
      }
    }
    // put the leading zeros to satisfy the precision
    while (precision > 0) {
      assert (startIndex > 0);
      buffer[--startIndex] = digits[0];
      --precision;
    }
    return startIndex;
  }

  private static final int[] LONG_MIN_ABS = {
    9,2,2,3,3,7,2,0,3,6,8,5,4,7,7,5,8,0,8,
  };

  public static int putDecimalLongAbsBackward(final long value, int precision,
      final char[] digits, final char[] buffer, int startIndex) {
    if (value == Long.MIN_VALUE) {
      // take special care for Long.MIN_VALUE, since its absolute value
      // can NOT be represented as a positive long value.
      for (int i = LONG_MIN_ABS.length - 1; i >= 0; --i) {
        assert (startIndex > 0);
        final int d = LONG_MIN_ABS[i];
        buffer[--startIndex] = digits[d];
        --precision;
      }
    } else {
      // put the absolute value backward
      long absValue = (value >= 0 ? value : (- value));
      while (absValue != 0) {
        final int d = (int) (absValue % 10);
        assert (startIndex > 0);
        buffer[--startIndex] = digits[d];
        absValue /= 10;
        --precision;
      }
    }
    // put the leading zeros to satisfy the precision
    while (precision > 0) {
      assert (startIndex > 0);
      buffer[--startIndex] = digits[0];
      --precision;
    }
    return startIndex;
  }

  public static int putRadixPrefixBackward(final int radix,
      final String[] radixPrefixes, final char[] buffer, int startIndex) {
    final String prefix = radixPrefixes[radix];
    if (prefix == null) {
      return startIndex;
    }
    final int n = prefix.length();
    for (int i = 0; i < n; ++i) {
      buffer[--startIndex] = prefix.charAt(i);
    }
    return startIndex;
  }

  public static int putIntBackward(final int signedValue,
      final int unsignedValue, final NumberFormatOptions options,
      final NumberFormatSymbols symbols, final char[] buffer, int startIndex) {
    final int radix = options.getRadix();
    final char[] digits = symbols.getDigits(options.isUppercase());
    final int precision = options.getIntPrecision();
    switch (radix) {
      case 2:
      case 8:
      case 16:
        startIndex = putSpecialRadixIntBackward(unsignedValue, radix,
            precision, digits, buffer, startIndex);
        break;
      case 10:
      default:
        // TODO(haixing): Need to implement the grouping of decimal numbers in
        // the future version.
        startIndex = putDecimalIntAbsBackward(signedValue, precision, digits,
            buffer, startIndex);
        // put the sign
        if (signedValue < 0) {
          buffer[--startIndex] = symbols.getNegativeSign();
        } else if (options.isShowPositive()) {
          buffer[--startIndex] = symbols.getPositiveSign();
        } else if (options.isShowSpace()) {
          buffer[--startIndex] = Ascii.SPACE;
        }
        break;
    }
    if (options.isShowRadix()) {
      final boolean uppercasePrefix = options.isUppercaseRadixPrefix();
      final String[] radixPrefixes = symbols.getRadixPrefixes(uppercasePrefix);
      startIndex = putRadixPrefixBackward(radix, radixPrefixes, buffer,
          startIndex);
    }
    return startIndex;
  }

  public static int putLongBackward(final long value,
      final NumberFormatOptions options, final NumberFormatSymbols symbols,
      final char[] buffer, int startIndex) {
    final int radix = options.getRadix();
    final char[] digits = symbols.getDigits(options.isUppercase());
    final int precision = options.getIntPrecision();
    switch (radix) {
      case 2:
      case 8:
      case 16:
        startIndex = putSpecialRadixLongBackward(value, radix, precision,
            digits, buffer, startIndex);
        break;
      case 10:
      default:
        // TODO(haixing): Need to implement the grouping of decimal numbers in
        // the future version.
        startIndex = putDecimalLongAbsBackward(value, precision, digits,
            buffer, startIndex);
        // put the sign
        if (value < 0) {
          buffer[--startIndex] = symbols.getNegativeSign();
        } else if (options.isShowPositive()) {
          buffer[--startIndex] = symbols.getPositiveSign();
        } else if (options.isShowSpace()) {
          buffer[--startIndex] = Ascii.SPACE;
        }
        break;
    }
    if (options.isShowRadix()) {
      final boolean uppercasePrefix = options.isUppercaseRadixPrefix();
      final String[] radixPrefixes = symbols.getRadixPrefixes(uppercasePrefix);
      startIndex = putRadixPrefixBackward(radix, radixPrefixes, buffer,
          startIndex);
    }
    return startIndex;
  }

  public static int putFormatResult(final int flags, final int width,
      final int fill, final char[] buffer, final int startIndex,
      final int endIndex, final StringBuilder output) {

    assert ((buffer != null) && (startIndex >= 0) && (startIndex <= endIndex)
        && (endIndex <= buffer.length) && (output != null));

    final int actualWidth = endIndex - startIndex;
    if (width <= actualWidth) {
      for (int i = startIndex; i < endIndex; ++i) {
        output.append(buffer[i]);
      }
      return actualWidth;
    } else {
      final int padding = width - actualWidth;
      int leftPadding = 0, rightPadding = 0;
      switch (flags & ALIGN_MASK) {
        case ALIGN_LEFT:
          leftPadding = 0;
          rightPadding = padding;
          break;
        case ALIGN_CENTER:
          // note that if the padding is odd, the leftPadding is
          // larger than the rightPadding by 1.
          leftPadding = (padding + 1) / 2;
          rightPadding = padding - leftPadding;
          break;
        case ALIGN_RIGHT:
        default:
          leftPadding = padding;
          rightPadding = 0;
          break;
      }
      if (Unicode.isBmp(fill)) {
        final char ch = (char) fill;
        for (int i = 0; i < leftPadding; ++i) {
          output.append(ch);
        }
        for (int i = startIndex; i < endIndex; ++i) {
          output.append(buffer[i]);
        }
        for (int i = 0; i < rightPadding; ++i) {
          output.append(ch);
        }
      } else {
        final char high = (char) Unicode.decomposeHighSurrogate(fill);
        final char low = (char) Unicode.decomposeLowSurrogate(fill);
        for (int i = 0; i < leftPadding; ++i) {
          output.append(high);
          output.append(low);
        }
        for (int i = startIndex; i < endIndex; ++i) {
          output.append(buffer[i]);
        }
        for (int i = 0; i < rightPadding; ++i) {
          output.append(high);
          output.append(low);
        }
      }
      return width;
    }
  }
//
//  public static void formatDate(final Date value, final Appendable output,
//      final FormatOptions options, final FormatSymbols symbols,
//      final String formatPattern) throws IOException {
//    // FIXME: use a better format method, use the format options and symbols
//    final SimpleDateFormat df = new SimpleDateFormat(formatPattern);
//    final String str = df.format(value);
//    output.append(str);
//  }
//
//  public static void formatDate(final Date value, final StringBuilder builder,
//      final FormatOptions options, final FormatSymbols symbols,
//      final String formatPattern) {
//    // FIXME: use a better format method, use the format options and symbols
//    final SimpleDateFormat df = new SimpleDateFormat(formatPattern);
//    final String str = df.format(value);
//    builder.append(str);
//  }
//
//  public static String formatDate(final Date value, final FormatOptions options) {
//    // FIXME:use a better format method, use the format options
//    final SimpleDateFormat df = new SimpleDateFormat(DateUtils.DEFAULT_PATTERN);
//    final String str = df.format(value);
//    return str;
//  }
//
//  public static String formatDate(final Date value) {
//    // FIXME: use a better format method
//    final SimpleDateFormat df = new SimpleDateFormat(DateUtils.DEFAULT_PATTERN);
//    final String str = df.format(value);
//    return str;
//  }
}
