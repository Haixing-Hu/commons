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

package com.github.haixing_hu.lang;

/**
 * Provides utility functions for character sequences.
 *
 * @author Haixing Hu
 */
public final class CharSequenceUtils {

  public static boolean startsWith(final CharSequence str,
      final CharSequence prefix) {
    return startsWith(str, 0, str.length(), prefix);
  }

  public static boolean startsWith(final CharSequence str,
      final int startIndex, final CharSequence prefix) {
    return startsWith(str, startIndex, str.length(), prefix);
  }

  public static boolean startsWith(final CharSequence str,
      final int startIndex, final int endIndex, final CharSequence prefix) {
    final int prefixLen = prefix.length();
    if (endIndex - startIndex < prefixLen) {
      return false;
    }
    for (int i = 0; i < prefixLen; ++i) {
      if (str.charAt(startIndex + i) != prefix.charAt(i)) {
        return false;
      }
    }
    return true;
  }

  public static boolean startsWithIgnoreCase(final CharSequence str,
      final CharSequence prefix) {
    return startsWithIgnoreCase(str, 0, str.length(), prefix);
  }

  public static boolean startsWithIgnoreCase(final CharSequence str,
      final int startIndex, final CharSequence prefix) {
    return startsWithIgnoreCase(str, startIndex, str.length(), prefix);
  }

  public static boolean startsWithIgnoreCase(final CharSequence str,
      final int startIndex, final int endIndex, final CharSequence prefix) {
    final int prefixLen = prefix.length();
    if (endIndex - startIndex < prefixLen) {
      return false;
    }
    for (int i = 0; i < prefixLen; ++i) {
      final char ch1 = str.charAt(startIndex + i);
      final char ch2 = prefix.charAt(i);
      if (Character.toLowerCase(ch1) != Character.toLowerCase(ch2)) {
        return false;
      }
    }
    return true;
  }

  public static boolean endsWith(final CharSequence str,
      final CharSequence suffix) {
    return endsWith(str, 0, str.length(), suffix);
  }

  public static boolean endsWith(final CharSequence str, final int endIndex,
      final CharSequence suffix) {
    return endsWith(str, 0, endIndex, suffix);
  }

  public static boolean endsWith(final CharSequence str, final int startIndex,
      final int endIndex, final CharSequence suffix) {
    final int suffixLen = suffix.length();
    if (endIndex - startIndex < suffixLen) {
      return false;
    }
    for (int i = 1; i <= suffixLen; ++i) {
      if (str.charAt(endIndex - i) != suffix.charAt(suffixLen - i)) {
        return false;
      }
    }
    return true;
  }

  public static boolean endsWithIgnoreCase(final CharSequence str,
      final CharSequence suffix) {
    return endsWithIgnoreCase(str, 0, str.length(), suffix);
  }

  public static boolean endsWithIgnoreCase(final CharSequence str,
      final int endIndex, final CharSequence suffix) {
    return endsWithIgnoreCase(str, 0, endIndex, suffix);
  }

  public static boolean endsWithIgnoreCase(final CharSequence str,
      final int startIndex, final int endIndex, final CharSequence suffix) {
    final int suffixLen = suffix.length();
    if (endIndex - startIndex < suffixLen) {
      return false;
    }
    for (int i = 1; i <= suffixLen; ++i) {
      final char ch1 = str.charAt(endIndex - i);
      final char ch2 = suffix.charAt(suffixLen - i);
      if (Character.toLowerCase(ch1) != Character.toLowerCase(ch2)) {
        return false;
      }
    }
    return true;
  }
}
