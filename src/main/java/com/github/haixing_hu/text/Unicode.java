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


/**
 * Utility functions about the Unicode.
 *
 * @author Haixing Hu
 */
public final class Unicode {
  /**
   * The maximum value of a valid ASCII code point.
   */
  public static final int ASCII_MAX                  = 0x7F;

  /**
   * The maximum value of a valid Latin1 code point.
   */
  public static final int LATIN1_MAX                 = 0xFF;

  /**
   * The maximum value of a valid Unicode code point.
   */
  public static final int UNICODE_MAX                = 0x10FFFF;

  /**
   * Minimum value of a supplementary code point.
   */
  public static final int SUPPLEMENTARY_MIN          = 0x10000;

  /**
   * Minimum value of a high surrogate code point.
   */
  public static final int HIGH_SURROGATE_MIN         = 0xD800;

  /**
   * Maximum value of a high surrogate code point.
   */
  public static final int HIGH_SURROGATE_MAX         = 0xDBFF;

  /**
   * The mask value used to test whether a 32-bit unsigned integer is a high
   * surrogate code point value.
   *
   * More precisely, a 32-bit unsigned integer ch is a high surrogate code point
   * value if and only if
   *
   * {@code 
   *      (ch & HIGH_SURROGATE_MASK) == HIGH_SURROGATE_MIN
   *  }
   */
  public static final int HIGH_SURROGATE_MASK        = 0xFFFFFC00;

  /**
   * Minimum value of a low surrogate code point.
   */
  public static final int LOW_SURROGATE_MIN          = 0xDC00;

  /**
   * Maximum value of a low surrogate code point.
   */
  public static final int LOW_SURROGATE_MAX          = 0xDFFF;

  /**
   * The mask value used to test whether a 32-bit unsigned integer is a low
   * surrogate code point value.
   *
   * More precisely, a 32-bit unsigned integer ch is a low surrogate code point
   * value if and only if
   *
   * {@code 
   *      (ch & LOW_SURROGATE_MASK) == LOW_SURROGATE_MIN
   *  }
   */
  public static final int LOW_SURROGATE_MASK         = 0xFFFFFC00;

  /**
   * Minimum value of a surrogate code point.
   */
  public static final int SURROGATE_MIN              = HIGH_SURROGATE_MIN;

  /**
   * The mask value used to test whether a 32-bit unsigned integer is a
   * surrogate code point value.
   *
   * More precisely, a 32-bit unsigned integer ch is a surrogate code point
   * value if and only if
   *
   * {@code 
   *      (ch & SURROGATE_MASK) == SURROGATE_MIN
   *  }
   */
  public static final int SURROGATE_MASK             = 0xFFFFF800;

  /**
   * The number of bits need to shift by the high surrogate when compose the
   * surrogate pair into a single Unicode code point.
   */
  public static final int HIGH_SURROGATE_SHIFT       = 10;

  /**
   * The offset used when composing surrogate pair.
   *
   * More precisely, the formula to compose a surrogate pair is:
   *
   * <code>
   *      (high << HIGH_SURROGATE_SHIFT) + low - SURROGATE_COMPOSE_OFFSET
   *  </code>
   */
  public static final int SURROGATE_COMPOSE_OFFSET   = ((HIGH_SURROGATE_MIN << HIGH_SURROGATE_SHIFT)
                                                         + LOW_SURROGATE_MIN - SUPPLEMENTARY_MIN);

  /**
   * The offset used to calculate the high surrogate when decomposing a
   * supplementary code point into surrogate pair.
   *
   * More precisely, the formula to calculate the high surrogate when
   * decomposing a supplementary code point is:
   *
   * {@code 
   *      high = (codePoint >> HIGH_SURROGATE_SHIFT) + SURROGATE_DECOMPOSE_OFFSET;
   *  }
   *
   * which is equivalent to
   *
   * {@code 
   *      high = ((codePoint - SUPPLEMENTARY_MIN) >> HIGH_SURROGATE_SHIFT)
   *           + HIGH_SURROGATE_MIN;
   *  }
   *
   */
  public static final int SURROGATE_DECOMPOSE_OFFSET =
    (HIGH_SURROGATE_MIN - (SUPPLEMENTARY_MIN >> HIGH_SURROGATE_SHIFT));

  /**
   * The offset used to calculate the low surrogate when decomposing a
   * supplementary code point into surrogate pair.
   *
   * More precisely, the formula to calculate the low surrogate when decomposing
   * a supplementary code point is:
   *
   * {@code 
   *      low = (codePoint & SURROGATE_DECOMPOSE_MASK) | LOW_SURROGATE_MIN;
   *  }
   *
   * which is equivalent to
   *
   * {@code 
   *      low = ((codePoint - SUPPLEMENTARY_MIN) & SURROGATE_DECOMPOSE_MASK)
   *           + LOW_SURROGATE_MIN;
   *  }
   *
   */
  public static final int SURROGATE_DECOMPOSE_MASK   =
    ((1 << HIGH_SURROGATE_SHIFT) - 1);

  /**
   * The number of bits need to be shifted to the right in order to get the
   * plane of a Unicode code point.
   */
  public static final int PlaneShift                 = 16;

  public static boolean isValidAscii(final int codePoint) {
    return codePoint <= ASCII_MAX;
  }

  public static boolean isValidLatin1(final int codePoint) {
    return codePoint <= LATIN1_MAX;
  }

  public static boolean isValidUnicode(final int codePoint) {
    return codePoint <= UNICODE_MAX;
  }

  public static boolean isBmp(final int codePoint) {
    return codePoint < SUPPLEMENTARY_MIN;
  }

  public static boolean isSupplementary(final int codePoint) {
    return (codePoint >= SUPPLEMENTARY_MIN) && (codePoint <= UNICODE_MAX);
  }

  public static boolean isHighSurrogate(final int codePoint) {
    return (codePoint & HIGH_SURROGATE_MASK) == HIGH_SURROGATE_MIN;
  }

  public static boolean isLowSurrogate(final int codePoint) {
    return (codePoint & LOW_SURROGATE_MASK) == LOW_SURROGATE_MIN;
  }

  public static boolean isSurrogate(final int codePoint) {
    return (codePoint & SURROGATE_MASK) == SURROGATE_MIN;
  }

  public static int composeSurrogatePair(final int high, final int low) {
    assert (isHighSurrogate(high) && isLowSurrogate(low));
    return (high << HIGH_SURROGATE_SHIFT) + low - SURROGATE_COMPOSE_OFFSET;
  }

  public static int decomposeHighSurrogate(final int codePoint) {
    assert (isSupplementary(codePoint));
    return (codePoint >> HIGH_SURROGATE_SHIFT) + SURROGATE_DECOMPOSE_OFFSET;
  }

  public static int decomposeLowSurrogate(final int codePoint) {
    assert (isSupplementary(codePoint));
    return (codePoint & SURROGATE_DECOMPOSE_MASK) | LOW_SURROGATE_MIN;
  }
}
