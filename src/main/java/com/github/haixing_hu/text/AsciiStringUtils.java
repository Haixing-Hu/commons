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

import javax.annotation.concurrent.ThreadSafe;

import com.github.haixing_hu.lang.StringUtils;

/**
 * Provides functions for manipulating the ASCII strings (represented by a byte[]).
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class AsciiStringUtils {

  public static String toString(final byte[] str, int startIndex, int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    if (startIndex >= endIndex) {
      return StringUtils.EMPTY;
    }
    final StringBuilder builder = new StringBuilder();
    for (int i = startIndex; i < endIndex; ++i) {
      builder.append((char)(str[i] & 0xFF));
    }
    return builder.toString();
  }

  public static boolean equals(final byte[] str1, int startIndex1, int endIndex1,
      final byte[] str2, int startIndex2, int endIndex2) {
    if (startIndex1 < 0) {
      startIndex1 = 0;
    }
    if (endIndex1 > str1.length) {
      endIndex1 = str1.length;
    }
    if (startIndex2 < 0) {
      startIndex2 = 0;
    }
    if (endIndex2 > str2.length) {
      endIndex2 = str2.length;
    }
    if ((endIndex1 - startIndex1) != (endIndex2 - startIndex2)) {
      return false;
    }
    for (int i = startIndex1, j = startIndex2; i < endIndex1; ++i, ++j) {
      if (str1[i] != str2[j]) {
        return false;
      }
    }
    return true;
  }

  public static boolean equalsIgnoreCase(final byte[] str1, int startIndex1, int endIndex1,
      final byte[] str2, int startIndex2, int endIndex2) {
    if (startIndex1 < 0) {
      startIndex1 = 0;
    }
    if (endIndex1 > str1.length) {
      endIndex1 = str1.length;
    }
    if (startIndex2 < 0) {
      startIndex2 = 0;
    }
    if (endIndex2 > str2.length) {
      endIndex2 = str2.length;
    }
    if ((endIndex1 - startIndex1) != (endIndex2 - startIndex2)) {
      return false;
    }
    for (int i = startIndex1, j = startIndex2; i < endIndex1; ++i, ++j) {
      if (! Ascii.equalsIgnoreCase(str1[i], str2[j])) {
        return false;
      }
    }
    return true;
  }

  public static int indexOf(final byte[] str, int startIndex, int endIndex, final byte ch) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    for (int i = startIndex; i < endIndex; ++i) {
      if (str[i] == ch) {
        return i;
      }
    }
    return -1;
  }

  public static int indexOfIgnoreCase(final byte[] str, int startIndex, int endIndex, byte ch) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    ch = Ascii.toLowercase(ch);
    for (int i = startIndex; i < endIndex; ++i) {
      if (Ascii.toLowercase(str[i]) ==  ch) {
        return i;
      }
    }
    return -1;
  }

  public static int indexOfAny(final byte[] str, int startIndex, int endIndex,
      final byte ... chars) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    for (int i = startIndex; i < endIndex; ++i) {
      final byte ch = str[i];
      for (int j = 0; j < chars.length; ++j) {
        if (ch == chars[j]) {
          return i;
        }
      }
    }
    return -1;
  }

  public static int indexOfAnyIgnoreCase(final byte[] str, int startIndex,
      int endIndex, final byte ...  chars) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    for (int i = startIndex; i < endIndex; ++i) {
      final byte ch = Ascii.toLowercase(str[i]);
      for (int j = 0; j < chars.length; ++j) {
        if (ch == Ascii.toLowercase(chars[j])) {
          return i;
        }
      }
    }
    return -1;
  }

  public static int indexOfAnyBut(final byte[] str, int startIndex, int endIndex,
      final byte ... chars) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    for (int i = startIndex; i < endIndex; ++i) {
      final byte ch = str[i];
      boolean found = false;
      for (int j = 0; j < chars.length; ++j) {
        if (ch == chars[j]) {
          found = true;
          break;
        }
      }
      if (! found) {
        return i;
      }
    }
    return -1;
  }

  public static int indexOfAnyButIgnoreCase(final byte[] str, int startIndex,
      int endIndex, final byte ...  chars) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    for (int i = startIndex; i < endIndex; ++i) {
      final byte ch = Ascii.toLowercase(str[i]);
      boolean found = false;
      for (int j = 0; j < chars.length; ++j) {
        if (ch == Ascii.toLowercase(chars[j])) {
          found = true;
          break;
        }
      }
      if (! found) {
        return i;
      }
    }
    return -1;
  }

  public static int indexOfWhitespace(final byte[] str, int startIndex,
      int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    for (int i = startIndex; i < endIndex; ++i) {
      if (Ascii.isWhitespace(str[i])) {
        return i;
      }
    }
    return -1;
  }

  public static int indexOfNonWhitespace(final byte[] str, int startIndex,
      int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    for (int i = startIndex; i < endIndex; ++i) {
      if (! Ascii.isWhitespace(str[i])) {
        return i;
      }
    }
    return -1;
  }

  public static int indexOfLetter(final byte[] str, int startIndex, int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    for (int i = startIndex; i < endIndex; ++i) {
      if (Ascii.isLetter(str[i])) {
        return i;
      }
    }
    return -1;
  }

  public static int indexOfNonLetter(final byte[] str, int startIndex, int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    for (int i = startIndex; i < endIndex; ++i) {
      if (! Ascii.isLetter(str[i])) {
        return i;
      }
    }
    return -1;
  }

  public static int indexOfUppercaseLetter(final byte[] str, int startIndex,
      int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    for (int i = startIndex; i < endIndex; ++i) {
      if (Ascii.isUppercaseLetter(str[i])) {
        return i;
      }
    }
    return -1;
  }

  public static int indexOfNonUppercaseLetter(final byte[] str, int startIndex,
      int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    for (int i = startIndex; i < endIndex; ++i) {
      if (! Ascii.isUppercaseLetter(str[i])) {
        return i;
      }
    }
    return -1;
  }

  public static int indexOfLowercaseLetter(final byte[] str, int startIndex,
      int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    for (int i = startIndex; i < endIndex; ++i) {
      if (Ascii.isLowercaseLetter(str[i])) {
        return i;
      }
    }
    return -1;
  }

  public static int indexOfNonLowercaseLetter(final byte[] str, int startIndex,
      int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    for (int i = startIndex; i < endIndex; ++i) {
      if (! Ascii.isLowercaseLetter(str[i])) {
        return i;
      }
    }
    return -1;
  }

  public static int indexOfDigit(final byte[] str, int startIndex, int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    for (int i = startIndex; i < endIndex; ++i) {
      if (Ascii.isDigit(str[i])) {
        return i;
      }
    }
    return -1;
  }

  public static int indexOfNonDigit(final byte[] str, int startIndex, int endIndex) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    for (int i = startIndex; i < endIndex; ++i) {
      if (! Ascii.isDigit(str[i])) {
        return i;
      }
    }
    return -1;
  }

  public static int indexOf(final byte[] str1, int startIndex1, int endIndex1,
      final byte[] str2, int startIndex2, int endIndex2) {
    if (startIndex1 < 0) {
      startIndex1 = 0;
    }
    if (endIndex1 > str1.length) {
      endIndex1 = str1.length;
    }
    if (startIndex2 < 0) {
      startIndex2 = 0;
    }
    if (endIndex2 > str2.length) {
      endIndex2 = str2.length;
    }
    final int len1 = endIndex1 - startIndex1;
    if (len1 <= 0) {
      return -1;
    }
    final int len2 = endIndex2 - startIndex2;
    if (len2 <= 0) {
      return startIndex1;
    }
    if (len1 < len2) {
      return -1;
    }
    final byte first2 = str2[startIndex2];
    final int newEndIndex1 = endIndex1 - (len2 - 1);
    for (int i = startIndex1; i < newEndIndex1; ++i) {
      if (str1[i] == first2) {
        boolean match = true;
        for (int j = i + 1, k = startIndex2 + 1; k < endIndex2; ++j, ++k) {
          assert (j < endIndex1);
          if (str1[j] != str2[k]) {
            match = false;
            break;
          }
        }
        if (match) {
          return i;
        }
      }
    }
    return -1;
  }

  public static int indexOfIgnoreCase(final byte[] str1, int startIndex1, int endIndex1,
      final byte[] str2, int startIndex2, int endIndex2) {
    if (startIndex1 < 0) {
      startIndex1 = 0;
    }
    if (endIndex1 > str1.length) {
      endIndex1 = str1.length;
    }
    if (startIndex2 < 0) {
      startIndex2 = 0;
    }
    if (endIndex2 > str2.length) {
      endIndex2 = str2.length;
    }
    final int len1 = endIndex1 - startIndex1;
    if (len1 <= 0) {
      return -1;
    }
    final int len2 = endIndex2 - startIndex2;
    if (len2 <= 0) {
      return startIndex1;
    }
    if (len1 < len2) {
      return -1;
    }
    final byte first2 = Ascii.toLowercase(str2[startIndex2]);
    final int newEndIndex1 = endIndex1 - (len2 - 1);
    for (int i = startIndex1; i < newEndIndex1; ++i) {
      if (Ascii.toLowercase(str1[i]) == first2) {
        boolean match = true;
        for (int j = i + 1, k = startIndex2 + 1; k < endIndex2; ++j, ++k) {
          assert (j < endIndex1);
          if (! Ascii.equalsIgnoreCase(str1[j], str2[k])) {
            match = false;
            break;
          }
        }
        if (match) {
          return i;
        }
      }
    }
    return -1;
  }

  public static int lastIndexOf(final byte[] str, int startIndex, int endIndex, final byte ch) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    for (int i = endIndex - 1; i >= startIndex; --i) {
      if (str[i] == ch) {
        return i;
      }
    }
    return -1;
  }

  public static int lastIndexOfIgnoreCase(final byte[] str, int startIndex, int endIndex, byte ch) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    ch = Ascii.toLowercase(ch);
    for (int i = endIndex - 1; i >= startIndex; --i) {
      if (Ascii.toLowercase(str[i]) ==  ch) {
        return i;
      }
    }
    return -1;
  }

  public static int lastIndexOf(final byte[] str1, int startIndex1, int endIndex1,
      final byte[] str2, int startIndex2, int endIndex2) {
    if (startIndex1 < 0) {
      startIndex1 = 0;
    }
    if (endIndex1 > str1.length) {
      endIndex1 = str1.length;
    }
    if (startIndex2 < 0) {
      startIndex2 = 0;
    }
    if (endIndex2 > str2.length) {
      endIndex2 = str2.length;
    }
    final int len1 = endIndex1 - startIndex1;
    if (len1 <= 0) {
      return -1;
    }
    final int len2 = endIndex2 - startIndex2;
    if (len2 <= 0) {
      return endIndex1 - 1;
    }
    if (len1 < len2) {
      return -1;
    }
    final byte last2 = str2[endIndex2 - 1];
    final int newStartIndex1 = startIndex1 + (len2 - 1);
    for (int i = endIndex1 - 1; i >= newStartIndex1; --i) {
      if (str1[i] == last2) {
        boolean match = true;
        for (int j = i - 1, k = endIndex2 - 2; k >= startIndex2; --j, --k) {
          assert (j >= startIndex1);
          if (str1[j] != str2[k]) {
            match = false;
            break;
          }
        }
        if (match) {
          return i;
        }
      }
    }
    return -1;
  }

  public static int lastIndexOfIgnoreCase(final byte[] str1, int startIndex1, int endIndex1,
      final byte[] str2, int startIndex2, int endIndex2) {
    if (startIndex1 < 0) {
      startIndex1 = 0;
    }
    if (endIndex1 > str1.length) {
      endIndex1 = str1.length;
    }
    if (startIndex2 < 0) {
      startIndex2 = 0;
    }
    if (endIndex2 > str2.length) {
      endIndex2 = str2.length;
    }
    final int len1 = endIndex1 - startIndex1;
    if (len1 <= 0) {
      return -1;
    }
    final int len2 = endIndex2 - startIndex2;
    if (len2 <= 0) {
      return endIndex1 - 1;
    }
    if (len1 < len2) {
      return -1;
    }
    final byte last2 = Ascii.toLowercase(str2[endIndex2 - 1]);
    final int newStartIndex1 = startIndex1 + (len2 - 1);
    for (int i = endIndex1 - 1; i >= newStartIndex1; --i) {
      if (Ascii.toLowercase(str1[i]) == last2) {
        boolean match = true;
        for (int j = i - 1, k = endIndex2 - 2; k >= startIndex2; --j, --k) {
          assert (j >= startIndex1);
          if (! Ascii.equalsIgnoreCase(str1[j], str2[k])) {
            match = false;
            break;
          }
        }
        if (match) {
          return i;
        }
      }
    }
    return -1;
  }

  public static boolean startsWith(final byte[] str, int startIndex,
      int endIndex, final byte ch) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    return (startIndex < endIndex) && (str[startIndex] == ch);
  }

  public static boolean startsWithIgnoreCase(final byte[] str, int startIndex,
      int endIndex, final byte ch) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    return (startIndex < endIndex)
        && Ascii.equalsIgnoreCase(str[startIndex], ch);
  }

  public static boolean endsWith(final byte[] str, int startIndex,
      int endIndex, final byte ch) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    return (startIndex < endIndex) && (str[endIndex - 1] == ch);
  }

  public static boolean endsWithIgnoreCase(final byte[] str, int startIndex,
      int endIndex, final byte ch) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    return (startIndex < endIndex)
      && Ascii.equalsIgnoreCase(str[endIndex - 1], ch);
  }

  public static boolean startsWithAny(final byte[] str, int startIndex,
      int endIndex, final byte ...  chars) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    if (startIndex >= endIndex) {
      return false;
    } else {
      final byte first = str[startIndex];
      for (int i = 0; i < chars.length; ++i) {
        if (first == chars[i]) {
          return true;
        }
      }
      return false;
    }
  }

  public static boolean startsWithAnyIgnoreCase(final byte[] str, int startIndex,
      int endIndex, final byte ...  chars) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    if (startIndex >= endIndex) {
      return false;
    } else {
      final byte first = Ascii.toLowercase(str[startIndex]);
      for (int i = 0; i < chars.length; ++i) {
        if (first == Ascii.toLowercase(chars[i])) {
          return true;
        }
      }
      return false;
    }
  }


  public static boolean endsWithAny(final byte[] str, int startIndex,
      int endIndex, final byte ...  chars) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    if (startIndex >= endIndex) {
      return false;
    } else {
      final byte last = str[endIndex - 1];
      for (int i = 0; i < chars.length; ++i) {
        if (last == chars[i]) {
          return true;
        }
      }
      return false;
    }
  }

  public static boolean endsWithAnyIgnoreCase(final byte[] str, int startIndex,
      int endIndex, final byte ...  chars) {
    if (startIndex < 0) {
      startIndex = 0;
    }
    if (endIndex > str.length) {
      endIndex = str.length;
    }
    if (startIndex >= endIndex) {
      return false;
    } else {
      final byte last = Ascii.toLowercase(str[endIndex - 1]);
      for (int i = 0; i < chars.length; ++i) {
        if (last == Ascii.toLowercase(chars[i])) {
          return true;
        }
      }
      return false;
    }
  }

  public static boolean startsWith(final byte[] str1, int startIndex1, int endIndex1,
      final byte[] str2, int startIndex2, int endIndex2) {
    if (startIndex1 < 0) {
      startIndex1 = 0;
    }
    if (endIndex1 > str1.length) {
      endIndex1 = str1.length;
    }
    if (startIndex2 < 0) {
      startIndex2 = 0;
    }
    if (endIndex2 > str2.length) {
      endIndex2 = str2.length;
    }
    if ((endIndex1 - startIndex1) < (endIndex2 - startIndex2)) {
      return false;
    }
    for (int i1 = startIndex1, i2 = startIndex2; i2 < endIndex2; ++i1, ++i2) {
      if (str1[i1] != str2[i2]) {
        return false;
      }
    }
    return true;
  }

  public static boolean startsWithIgnoreCase(final byte[] str1, int startIndex1, int endIndex1,
      final byte[] str2, int startIndex2, int endIndex2) {
    if (startIndex1 < 0) {
      startIndex1 = 0;
    }
    if (endIndex1 > str1.length) {
      endIndex1 = str1.length;
    }
    if (startIndex2 < 0) {
      startIndex2 = 0;
    }
    if (endIndex2 > str2.length) {
      endIndex2 = str2.length;
    }
    if ((endIndex1 - startIndex1) < (endIndex2 - startIndex2)) {
      return false;
    }
    for (int i1 = startIndex1, i2 = startIndex2; i2 < endIndex2; ++i1, ++i2) {
      if (! Ascii.equalsIgnoreCase(str1[i1], str2[i2])) {
        return false;
      }
    }
    return true;
  }

  public static boolean endsWith(final byte[] str1, int startIndex1, int endIndex1,
      final byte[] str2, int startIndex2, int endIndex2) {
    if (startIndex1 < 0) {
      startIndex1 = 0;
    }
    if (endIndex1 > str1.length) {
      endIndex1 = str1.length;
    }
    if (startIndex2 < 0) {
      startIndex2 = 0;
    }
    if (endIndex2 > str2.length) {
      endIndex2 = str2.length;
    }
    if ((endIndex1 - startIndex1) < (endIndex2 - startIndex2)) {
      return false;
    }
    for (int i1 = endIndex1 - 1, i2 = endIndex2 - 1; i2 >= startIndex2; --i1, --i2) {
      if (str1[i1] != str2[i2]) {
        return false;
      }
    }
    return true;
  }

  public static boolean endsWithIgnoreCase(final byte[] str1, int startIndex1, int endIndex1,
      final byte[] str2, int startIndex2, int endIndex2) {
    if (startIndex1 < 0) {
      startIndex1 = 0;
    }
    if (endIndex1 > str1.length) {
      endIndex1 = str1.length;
    }
    if (startIndex2 < 0) {
      startIndex2 = 0;
    }
    if (endIndex2 > str2.length) {
      endIndex2 = str2.length;
    }
    if ((endIndex1 - startIndex1) < (endIndex2 - startIndex2)) {
      return false;
    }
    for (int i1 = endIndex1 - 1, i2 = endIndex2 - 1; i2 >= startIndex2; --i1, --i2) {
      if (! Ascii.equalsIgnoreCase(str1[i1], str2[i2])) {
        return false;
      }
    }
    return true;
  }

}
