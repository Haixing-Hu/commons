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

import javax.annotation.concurrent.ThreadSafe;

/**
 * This class defines the constants of formatting flags.
 * <p>
 * These constants can be combined with bitwise or.
 * </p>
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class FormatFlag {

  /**
   * Reads or writes boolean elements as alphabetic strings (true and false).
   */
  public static final int BOOL_ALPHA = 0x00000001;

  /**
   * Writes integral values preceded by their corresponding numeric radix
   * prefix.
   */
  public static final int SHOW_RADIX = 0x00000002;

  /**
   * Writes real values including always the decimal separator.
   */
  public static final int SHOW_POINT = 0x00000004;

  /**
   * Writes non-negative numerical values preceded by a plus sign (+). This
   * option conflicts with the {@link #SHOW_SPACE} option.
   */
  public static final int SHOW_POSITIVE = 0x00000008;

  /**
   * Writes non-negative numerical values preceded by a single space.
   * <p>
   * This option conflicts with the {@link #SHOW_POSITIVE} option.
   * </p>
   */
  public static final int SHOW_SPACE = 0x00000010;

  /**
   * If the integral values is printed with their corresponding numeric radix
   * prefix preceded, the radix prefix should be printed in its uppercase
   * mapping.
   */
  public static final int UPPERCASE_RADIX_PREFIX = 0x00000020;

  /**
   * If the real values is printed in scientific notation, the exponential
   * symbol should be printed in its uppercase mapping.
   */
  public static final int UPPERCASE_EXPONENT = 0x00000040;

  /**
   * Uses the grouping when reading or writing decimal integers.
   */
  public static final int GROUPING = 0x00000080;

  /**
   * Do NOT skip leading white spaces on certain input operations, and do NOT
   * stop reading when another white space is encountered.
   * <p>
   * Note that by default, all parsing routines skip the leading white spaces,
   * and stop reading when another white space is encountered. If this flag is
   * presented, the parsing routine will NOT skip the leading white space, and
   * will NOT stop reading when another white space is encountered.
   * </p>
   */
  public static final int KEEP_BLANKS = 0x00000100;

  /**
   * Writes uppercase letters in certain insertion operations.
   * <p>
   * If is option is presented, the output of hexadecimal numbers will also use
   * the uppercase letter of digits.
   * </p>
   */
  public static final int UPPERCASE = 0x00000200;

  /**
   * Writes lowercase letters in certain insertion operations.
   * <p>
   * If is option is presented, the output of hexadecimal numbers will also use
   * the lowercase letter of digits.
   * </p>
   */
  public static final int LOWERCASE = 0x00000400;

  /**
   * Writes titlecase letters in certain insertion operations.
   */
  public static final int TITLECASE = 0x00000800;

  /**
   * Reads or writes integral values using binary radix format. A binary integer
   * literal would have a prefix of "0b". For example, "0b101011101" represents
   * the decimal number 349.
   * <p>
   * This option conflicts with the {@link #OCTAL}, {@link #DECIMAL}, and
   * {@link #HEX} options.
   * </p>
   */
  public static final int BINARY = 0x00001000;

  /**
   * Reads or writes integral values using octal radix format.A binary integer
   * literal would have a prefix of "0". For example, "0127" represents the
   * decimal number 87.
   * <p>
   * This option conflicts with the {@link #BINARY}, {@link #DECIMAL}, and
   * {@link #HEX} option.
   * </p>
   */
  public static final int OCTAL = 0x00002000;

  /**
   * Reads or writes integral values using decimal radix format. This is the
   * default base setting.
   * <p>
   * This option conflicts with the {@link #BINARY}, {@link #OCTAL}, and
   * {@link #HEX} option.
   * </p>
   */
  public static final int DECIMAL = 0x00004000;

  /**
   * Reads or writes integral values using hexadecimal radix format.
   * <p>
   * A hexadecimal integer literal would have a prefix of "0x". For example,
   * "0x3F2A" represents the decimal number 16170.
   * </p>
   * <p>
   * This option conflicts with the {@link #BINARY}, {@link #OCTAL}, and
   * {@link #DECIMAL} option.
   * </p>
   */
  public static final int HEX = 0x00008000;

  /**
   * Reads or writes real values in fixed-point notation.
   * <p>
   * This option conflicts with the {@link #SCIENTIFIC}, {@link #SHORT_REAL}
   * options.
   * </p>
   */
  public static final int FIXED_POINT = 0x00010000;

  /**
   * Reads or writes real values in scientific notation.
   * <p>
   * This option conflicts with the {@link #FIXED_POINT}, {@link #SHORT_REAL}
   * options.
   * </p>
   */
  public static final int SCIENTIFIC = 0x00020000;

  /**
   * Writes real values in the the shorter form of fixed point or scientific
   * notation. This is the default real format setting.
   * <p>
   * This option conflicts with the {@link #FIXED_POINT}, {@link #SCIENTIFIC}
   * options.
   * </p>
   */
  public static final int SHORT_REAL = 0x00040000;

  /**
   * The formated object is aligned to the left, i.e., the output is padded to
   * the field width appending fill characters at the end.
   * <p>
   * This option conflicts with the {@link #ALIGN_RIGHT}, {@link #ALIGN_CENTER}
   * options.
   * </p>
   */
  public static final int ALIGN_LEFT = 0x00080000;

  /**
   * The formated object is aligned to the left, i.e., the output is padded to
   * the field width appending fill characters at the beginning. This is the
   * default alignment setting.
   * <p>
   * This option conflicts with the {@link #ALIGN_LEFT}, {@link #ALIGN_CENTER}
   * options.
   * </p>
   */
  public static final int ALIGN_RIGHT = 0x00100000;

  /**
   * The formated object is aligned to the center, i.e., the output is padded to
   * the field width appending fill characters at the both ends. It makes the
   * center of the formated object align to the center of the specified width of
   * field.
   * <p>
   * This option conflicts with the {@link #ALIGN_LEFT}, {@link #ALIGN_RIGHT}
   * options.
   * </p>
   */
  public static final int ALIGN_CENTER = 0x00200000;

  /**
   * The mask of all case related fields.
   */
  public static final int CASE_MASK = (UPPERCASE | LOWERCASE | TITLECASE);

  /**
   * The default case field.
   */
  public static final int DEFAULT_CASE = UPPERCASE;

  /**
   * The mask of all radix related fields.
   */
  public static final int RADIX_MASK = (BINARY | OCTAL | DECIMAL | HEX);

  /**
   * The default radix field.
   */
  public static final int DEFAULT_RADIX = DECIMAL;

  /**
   * The mask of all real format related fields.
   */
  public static final int REAL_MASK = (FIXED_POINT | SCIENTIFIC | SHORT_REAL);

  /**
   * The default real format field.
   */
  public static final int DEFAULT_REAL = SHORT_REAL;

  /**
   * The mask of all align related fields.
   */
  public static final int ALIGN_MASK = (ALIGN_LEFT | ALIGN_RIGHT | ALIGN_CENTER);

  /**
   * The default align field.
   */
  public static final int DEFAULT_ALIGN = ALIGN_RIGHT;

  /**
   * The default format flags.
   */
  public static final int DEFAULT = (BOOL_ALPHA | SHOW_RADIX | SHOW_POINT
      | DEFAULT_CASE | DEFAULT_REAL | DEFAULT_ALIGN);
}