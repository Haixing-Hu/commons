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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteOrder;
import java.util.Date;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import com.github.haixing_hu.text.NumberFormatSymbols;

/**
 * This class provides operations on {@code char} primitives and
 * {@link Character} objects.
 * <p>
 * This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} input. Each method documents its
 * behavior in more detail.
 * </p>
 * <p>
 * This class also handle the conversion from {@code char} values or
 * {@link Character} objects to common types.
 * </p>
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class CharUtils {

  /**
   * The default {@code char} value used when necessary.
   */
  public static final char DEFAULT = (char) 0;

  public static final char   LOWERCASE_DIGITS[]           = {
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
    'u', 'v', 'w', 'x', 'y', 'z',
  };

  public static final char   UPPERCASE_DIGITS[]           = {
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
    'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
    'U', 'V', 'W', 'X', 'Y', 'Z',
  };

  private static final String[] CHAR_STRING_CACHE = new String[128];
  static {
    for (char i = 0; i < 128; ++i) {
      CHAR_STRING_CACHE[i] = String.valueOf(i);
    }
  }

  /**
   * {@code \u000a} line feed LF ('\n').
   *
   * @see <a
   *      href="http://java.sun.com/docs/books/jls/third_edition/html/lexical.html#101089">JLF:
   *      Escape Sequences for Character and String Literals</a>
   */
  public static final char         LF                = '\n';

  /**
   * {@code \u000d} carriage return CR ('\r').
   *
   * @see <a
   *      href="http://java.sun.com/docs/books/jls/third_edition/html/lexical.html#101089">JLF:
   *      Escape Sequences for Character and String Literals</a>
   */
  public static final char         CR                = '\r';


  public static final char         SPACE             = ' ';


  public static final char         TAB               = '\t';

  /**
   * Indicates that the Unicode is a printable character except for spaces.
   */
  public static final int VISIBILITY_GRAPH  = 0x01;

  /**
   * Indicates that the Unicode is a "inline blank" character, including
   * spaces, control characters (except for '\r' and '\n'), etc.
   */
  public static final int VISIBILITY_INLINE_BLANK  = 0x02;

  /**
   * Indicates that the Unicode is a line break character, including '\r', '\n'
   * and all characters with general category of "Zl" (line separators) and "Zp"
   * (paragraph separators).
   */
  public static final int VISIBILITY_LINE_BREAK = 0x04;

  /**
   * Indicates that the Unicode is a "blank" character, including "inline blank"
   * characters and "line break" characters.
   *
   * This value is the bitwise OR of {@link #VISIBILITY_INLINE_BLANK}
   * and {@link #VISIBILITY_LINE_BREAK}.
   */
  public static final int VISIBILITY_BLANK      = VISIBILITY_INLINE_BLANK
                                                | VISIBILITY_LINE_BREAK;

  private CharUtils() { }

  /**
   * Determines whether the specified code point is a "graphic" character
   * (printable, excluding spaces).
   *
   * True for all characters except those with general categories "Cc" (control
   * codes), "Cf" (format controls), "Cs" (surrogates), "Cn" (unassigned), and
   * "Z" (separators).
   *
   * Note that a code point is either graph or blank.
   *
   * @param ch
   *    the code point of an Unicode.
   * @return
   *    true if the specified code point is a "graphic" character; false otherwise.
   * @see #isBlank(int)
   */
  public static boolean isGraph(final int codePoint) {
    final int type = Character.getType(codePoint);
    return (type != Character.CONTROL)
          && (type != Character.FORMAT)
          && (type != Character.SURROGATE)
          && (type != Character.UNASSIGNED)
          && (type != Character.LINE_SEPARATOR)
          && (type != Character.SPACE_SEPARATOR)
          && (type != Character.PARAGRAPH_SEPARATOR);
  }

  /**
   * Determines whether the specified code point is a "blank" character
   * (non-printable or white spaces).
   *
   * True for all characters with general categories "Cc" (control
   * codes), "Cf" (format controls), "Cs" (surrogates), "Cn" (unassigned), and
   * "Z" (separators).
   *
   * Note that a code point is either graph or blank.
   *
   * @param ch
   *    the code point of an Unicode.
   * @return
   *    true if the specified code point is a "graphic" character; false otherwise.
   * @see #isGraph(int)
   */
  public static boolean isBlank(final int codePoint) {
    final int type = Character.getType(codePoint);
    return (type == Character.CONTROL)
          || (type == Character.FORMAT)
          || (type == Character.SURROGATE)
          || (type == Character.UNASSIGNED)
          || (type == Character.LINE_SEPARATOR)
          || (type == Character.SPACE_SEPARATOR)
          || (type == Character.PARAGRAPH_SEPARATOR);
  }

  /**
   * Determines whether the specified code point is a "inline blank" character.
   * <p>
   * A code point is a "inline blank" character if and only if it is not '\r'
   * nor '\n', and it has the general category of "Cc" (control codes), "Cf"
   * (format controls), "Cs" (surrogates), "Cn" (unassigned), and "Zs" (space
   * separators).
   * </p>
   * @param codePoint
   * @return true if the specified code point is a "inline blank" character;
   *         false otherwise.
   * @see #isBlank(int)
   */
  public static boolean isInlineBlank(final int codePoint) {
    final int dir = Character.getDirectionality(codePoint);
    if (dir == Character.DIRECTIONALITY_PARAGRAPH_SEPARATOR) {
      return false;
    } else {
      final int type = Character.getType(codePoint);
      return (type == Character.CONTROL)
          || (type == Character.FORMAT)
          || (type == Character.SURROGATE)
          || (type == Character.UNASSIGNED)
          || (type == Character.SPACE_SEPARATOR);
    }
  }

  /**
   * Determines whether the specified code point is a "line break" character.
   * According to the Unicode Standard Annex #9, a character with bidirectional
   * class B is a "Paragraph Separator". And because a Paragraph Separator
   * breaks lines, there will be at most one per line, at the end of that line.”
   * As a consequence, there's 3 reasons to identify a character as a linebreak:
   * <ul>
   * <li>General Category Zl "Line Separator"</li>
   * <li>General Category Zp "Paragraph Separator"</li>
   * <li>Bidirectional Class B "Paragraph Separator"</li>
   * </ul>
   * There's 8 linebreaks in the current Unicode Database (5.2):
   * <table>
   * <tr>
   * <th>Code Point</th>
   * <th>Short Name</th>
   * <th>Long Name</th>
   * <th>General Category</th>
   * <th>Bidi Class</th>
   * <th>Notes</th>
   * </tr>
   * <tr>
   * <td>\c{U+000A}</td>
   * <td>LF</td>
   * <td>LINE FEED</td>
   * <td>Cc</td>
   * <td>B</td>
   * <td></td>
   * </tr>
   * <tr>
   * <td>\c{U+000D}</td>
   * <td>CR</td>
   * <td>CARRIAGE RETURN</td>
   * <td>Cc</td>
   * <td>B</td>
   * <td></td>
   * </tr>
   * <tr>
   * <td>\c{U+001C}</td>
   * <td>FS</td>
   * <td>INFORMATION SEPARATOR FOUR</td>
   * <td>Cc</td>
   * <td>B</td>
   * <td>UCD 3.1: FILE SEPARATOR</td>
   * </tr>
   * <tr>
   * <td>\c{U+001D}</td>
   * <td>GS</td>
   * <td>INFORMATION SEPARATOR THREE</td>
   * <td>Cc</td>
   * <td>B</td>
   * <td>UCD 3.1: GROUP SEPARATOR</td>
   * </tr>
   * <tr>
   * <td>\c{U+001E}</td>
   * <td>RS</td>
   * <td>INFORMATION SEPARATOR TWO</td>
   * <td>Cc</td>
   * <td>B</td>
   * <td>UCD 3.1: RECORD SEPARATOR</td>
   * </tr>
   * <tr>
   * <td>\c{U+0085}</td>
   * <td>NEL</td>
   * <td>NEXT LINE</td>
   * <td>Cc</td>
   * <td>B</td>
   * <td>C1 Control Code</td>
   * </tr>
   * <tr>
   * <td>\c{U+2028}</td>
   * <td>LS</td>
   * <td>LINE SEPARATOR</td>
   * <td>Zl</td>
   * <td>WS</td>
   * <td></td>
   * </tr>
   * <tr>
   * <td>\c{U+2029}</td>
   * <td>PS</td>
   * <td>PARAGRAPH SEPARATOR</td>
   * <td>Zp</td>
   * <td>B</td>
   * <td></td>
   * </tr>
   * </table>
   *
   * @param codePoint
   * @return true if the specified code point is a "line break" character; false
   *         otherwise.
   */
  public static boolean isLineBreak(final int codePoint) {
    final int type = Character.getType(codePoint);
    if ((type == Character.LINE_SEPARATOR)
        || (type == Character.PARAGRAPH_SEPARATOR)) {
        return true;
    }
    final int dir = Character.getDirectionality(codePoint);
    return (dir == Character.DIRECTIONALITY_PARAGRAPH_SEPARATOR);
  }

  /**
   * Get the visibility of a code point.
   *
   * The visibility of a code point is one of the following values:
   * <ul>
   *  <li>{@link #VISIBILITY_GRAPH}</li>
   *  <li>{@link #VISIBILITY_SPACE}</li>
   *  <li>{@link #VISIBILITY_LINE_BREAK}</li>
   * </ul>
   *
   * @param codePoint
   *    a code point.
   * @return
   *    the visibility of the code point.
   */
  public static int getVisibility(final int codePoint) {
    final int dir = Character.getDirectionality(codePoint);
    if (dir == Character.DIRECTIONALITY_PARAGRAPH_SEPARATOR) {
      return VISIBILITY_LINE_BREAK;
    } else {
      final int type = Character.getType(codePoint);
      switch (type) {
        case Character.CONTROL:
        case Character.FORMAT:
        case Character.SURROGATE:
        case Character.UNASSIGNED:
        case Character.SPACE_SEPARATOR:
          return VISIBILITY_INLINE_BLANK;
        case Character.LINE_SEPARATOR:
        case Character.PARAGRAPH_SEPARATOR:
          return VISIBILITY_LINE_BREAK;
        default:
          return VISIBILITY_GRAPH;
      }
    }
  }

  /**
   * Converts a {@link Character} object to a {@code char} value.
   *
   * <pre>
   *   CharUtils.toPrimitive(null) = CharUtils.DEFAULT
   *   CharUtils.toPrimitive(new Character(' '))  = ' '
   *   CharUtils.toPrimitive(new Character('A'))  = 'A'
   * </pre>
   *
   * @param value
   *          the {@link Character} object to convert, which could be null.
   * @return the {@code char} value of the {@link Character} object; or
   *         {@link #DEFAULT} if the object is null.
   */
  public static char toPrimitive(@Nullable final Character value) {
    return (value == null ? DEFAULT : value.charValue());
  }

  /**
   * Converts the Character to a char handling {@code null}.
   *
   * <pre>
   *   CharUtils.toPrimitive(null, 'X') = 'X'
   *   CharUtils.toPrimitive(new Character(' '), 'X')  = ' '
   *   CharUtils.toPrimitive(new Character('A'), 'X')  = 'A'
   * </pre>
   *
   * @param value
   *          the {@link Character} object to convert, which could be null.
   * @param defaultValue
   *          the value to use if the {@link Character} object is null
   * @return the {@code char} value of the {@link Character} object;
   *         or the default value if the {@link Character} object is null.
   */
  public static char toPrimitive(@Nullable final Character value,
      final char defaultValue) {
    return (value == null ? defaultValue : value.charValue());
  }

  public static boolean toBoolean(final char value) {
    return (value != 0);
  }

  public static boolean toBoolean(@Nullable final Character value) {
    return (value == null ? BooleanUtils.DEFAULT : toBoolean(value.charValue()));
  }

  public static boolean toBoolean(@Nullable final Character value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : toBoolean(value.charValue()));
  }

  public static Boolean toBooleanObject(final char value) {
    return Boolean.valueOf(toBoolean(value));
  }

  public static Boolean toBooleanObject(@Nullable final Character value) {
    return (value == null ? null : toBooleanObject(value.charValue()));
  }

  public static Boolean toBooleanObject(@Nullable final Character value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : toBooleanObject(value.charValue()));
  }

  public static byte toByte(final char value) {
    return (byte) value;
  }

  public static byte toByte(@Nullable final Character value) {
    return (value == null ? ByteUtils.DEFAULT : toByte(value.charValue()));
  }

  public static byte toByte(@Nullable final Character value, final byte defaultValue) {
    return (value == null ? defaultValue : toByte(value.charValue()));
  }

  public static Byte toByteObject(final char value) {
    return Byte.valueOf(toByte(value));
  }

  public static Byte toByteObject(@Nullable final Character value) {
    return (value == null ? null : toByteObject(value.charValue()));
  }

  public static Byte toByteObject(@Nullable final Character value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : toByteObject(value.charValue()));
  }

  public static short toShort(final char value) {
    return (short) value;
  }

  public static short toShort(@Nullable final Character value) {
    return (value == null ? ShortUtils.DEFAULT : toShort(value.charValue()));
  }

  public static short toShort(@Nullable final Character value, final short defaultValue) {
    return (value == null ? defaultValue : toShort(value.charValue()));
  }

  public static Short toShortObject(final char value) {
    return Short.valueOf(toShort(value));
  }

  public static Short toShortObject(@Nullable final Character value) {
    return (value == null ? null : toShortObject(value.charValue()));
  }

  public static Short toShortObject(@Nullable final Character value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : toShortObject(value.charValue()));
  }

  public static int toInt(final char value) {
    return value;
  }

  public static int toInt(@Nullable final Character value) {
    return (value == null ? IntUtils.DEFAULT : toInt(value.charValue()));
  }

  public static int toInt(@Nullable final Character value, final int defaultValue) {
    return (value == null ? defaultValue : toInt(value.charValue()));
  }

  public static Integer toIntObject(final char value) {
    return Integer.valueOf(toInt(value));
  }

  public static Integer toIntObject(@Nullable final Character value) {
    return (value == null ? null : toIntObject(value.charValue()));
  }

  public static Integer toIntObject(@Nullable final Character value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : toIntObject(value.charValue()));
  }

  public static long toLong(final char value) {
    return value;
  }

  public static long toLong(@Nullable final Character value) {
    return (value == null ? LongUtils.DEFAULT : toLong(value.charValue()));
  }

  public static long toLong(@Nullable final Character value, final long defaultValue) {
    return (value == null ? defaultValue : toLong(value.charValue()));
  }

  public static Long toLongObject(final char value) {
    return Long.valueOf(toLong(value));
  }

  public static Long toLongObject(@Nullable final Character value) {
    return (value == null ? null : toLongObject(value.charValue()));
  }

  public static Long toLongObject(@Nullable final Character value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : toLongObject(value.charValue()));
  }

  public static float toFloat(final char value) {
    return value;
  }

  public static float toFloat(@Nullable final Character value) {
    return (value == null ? FloatUtils.DEFAULT : toFloat(value.charValue()));
  }

  public static float toFloat(@Nullable final Character value, final float defaultValue) {
    return (value == null ? defaultValue : toFloat(value.charValue()));
  }

  public static Float toFloatObject(final char value) {
    return Float.valueOf(toFloat(value));
  }

  public static Float toFloatObject(@Nullable final Character value) {
    return (value == null ? null : toFloatObject(value.charValue()));
  }

  public static Float toFloatObject(@Nullable final Character value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : toFloatObject(value.charValue()));
  }

  public static double toDouble(final char value) {
    return value;
  }

  public static double toDouble(@Nullable final Character value) {
    return (value == null ? DoubleUtils.DEFAULT : toDouble(value.charValue()));
  }

  public static double toDouble(@Nullable final Character value, final double defaultValue) {
    return (value == null ? defaultValue : toDouble(value.charValue()));
  }

  public static Double toDoubleObject(final char value) {
    return Double.valueOf(toDouble(value));
  }

  public static Double toDoubleObject(@Nullable final Character value) {
    return (value == null ? null : toDoubleObject(value.charValue()));
  }

  public static Double toDoubleObject(@Nullable final Character value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : toDoubleObject(value.charValue()));
  }

  /**
   * Converts the character to a String that contains the one character.
   *
   * For ASCII 7 bit characters, this uses a cache that will return the same
   * String object each time.
   *
   * <pre>
   *   CharUtils.toString(' ')  = " "
   *   CharUtils.toString('A')  = "A"
   * </pre>
   *
   * @param value
   *          the character to convert
   * @return a String containing the one specified character
   */
  public static String toString(final char value) {
    if (value < 128) {
      return CHAR_STRING_CACHE[value];
    } else {
      final char data[] = {value};
      return new String(data);
    }
  }

  /**
   * Converts the character to a String that contains the one character.
   *
   * For ASCII 7 bit characters, this uses a cache that will return the same
   * String object each time.
   *
   * If {@code null} is passed in, {@code null} will be returned.
   *
   * <pre>
   *   CharUtils.toString(null) = null
   *   CharUtils.toString(' ')  = " "
   *   CharUtils.toString('A')  = "A"
   * </pre>
   *
   * @param value
   *          the character to convert, which could be null.
   * @return a String containing the one specified character, or null if the
   *         value is null.
   */
  public static String toString(@Nullable final Character value) {
    return (value == null ? null : toString(value.charValue()));
  }

  public static String toString(@Nullable final Character value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.charValue()));
  }

  /**
   * Convert a {@code char} value into hex string.
   *
   * @param value
   *    the value to be converted.
   * @param builder
   *    a {@link StringBuilder} where to append the hex string.
   */
  public static void toHexString(final char value, final StringBuilder builder) {
    final char[] digits = NumberFormatSymbols.DEFAULT_UPPERCASE_DIGITS;
    builder.append("\\u")
           .append(digits[(value >>> 12) & 0x0F])
           .append(digits[(value >>> 8) & 0x0F])
           .append(digits[(value >>> 4) & 0x0F])
           .append(digits[value & 0x0F]);
  }

  /**
   * Convert a {@code char} value into hex string.
   *
   * @param value
   *          the value to be converted.
   * @return the hex string of the value.
   */
  public static String toHexString(final char value) {
    final StringBuilder builder = new StringBuilder();
    toHexString(value, builder);
    return builder.toString();
  }

  public static Date toDate(final char value) {
    return new Date(value * 1L);
  }

  public static Date toDate(@Nullable final Character value) {
    return (value == null ? null : new Date(value.charValue() * 1L));
  }

  public static Date toDate(@Nullable final Character value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : new Date(value.charValue() * 1L));
  }

  public static byte[] toByteArray(final char value) {
    return toByteArray(value, ByteArrayUtils.DEFAULT_BYTE_ORDER);
  }

  public static byte[] toByteArray(final char value, final ByteOrder byteOrder) {
    final byte[] result = new byte[2];
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      result[0] = (byte) (value >>> 8);
      result[1] = (byte) value;
    } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
      result[1] = (byte) (value >>> 8);
      result[0] = (byte) value;
    } else {
      throw new UnsupportedByteOrderException(byteOrder);
    }
    return result;
  }

  public static byte[] toByteArray(@Nullable final Character value) {
    return (value == null ? null : toByteArray(value.charValue(),
        ByteArrayUtils.DEFAULT_BYTE_ORDER));
  }

  public static byte[] toByteArray(@Nullable final Character value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toByteArray(value.charValue(), byteOrder));
  }

  public static byte[] toByteArray(@Nullable final Character value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.charValue(),
        ByteArrayUtils.DEFAULT_BYTE_ORDER));
  }

  public static byte[] toByteArray(@Nullable final Character value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue : toByteArray(value.charValue(),
        byteOrder));
  }

  public static Class<?> toClass(final char value) {
    return Character.TYPE;
  }

  public static Class<?> toClass(@Nullable final Character value) {
    return (value == null ? null : Character.class);
  }

  public static Class<?> toClass(@Nullable final Character value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Character.class);
  }

  public static BigInteger toBigInteger(final char value) {
    return BigInteger.valueOf(value);
  }

  public static BigInteger toBigInteger(@Nullable final Character value) {
    return (value == null ? null : BigInteger.valueOf(value.charValue()));
  }

  public static BigInteger toBigInteger(@Nullable final Character value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : BigInteger.valueOf(value.charValue()));
  }

  public static BigDecimal toBigDecimal(final char value) {
    return BigDecimal.valueOf(value);
  }

  public static BigDecimal toBigDecimal(@Nullable final Character value) {
    return (value == null ? null : BigDecimal.valueOf(value.charValue()));
  }

  public static BigDecimal toBigDecimal(@Nullable final Character value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : BigDecimal.valueOf(value.charValue()));
  }

  /**
   * Converts the string to the unicode format '\u0020'.
   *
   * This format is the Java source code format.
   *
   * <pre>
   *   CharUtils.toUnicodeEscape(' ') = "\u0020"
   *   CharUtils.toUnicodeEscape('A') = "\u0041"
   * </pre>
   *
   * @param ch
   *          the character to convert
   * @return the escaped unicode string
   */
  public static String toUnicodeEscape(final int ch) {
    final StringBuilder builder = new StringBuilder();
    toUnicodeEscape(ch, builder);
    return builder.toString();
  }

  /**
   * Converts the string to the unicode format '\u0020'.
   *
   * This format is the Java source code format.
   *
   * <pre>
   *   CharUtils.toUnicodeEscape(' ') = "\u0020"
   *   CharUtils.toUnicodeEscape('A') = "\u0041"
   * </pre>
   *
   * @param ch
   *          the character to convert.
   * @param builder
   *          the {@link StringBuilder} where to append the result.
   * @return the {@link StringBuilder}.
   */
  public static StringBuilder toUnicodeEscape(final int ch,
      final StringBuilder builder) {
    return builder.append("\\u")
                  .append( UPPERCASE_DIGITS[((ch >>> 12) & 0xF)] )
                  .append( UPPERCASE_DIGITS[((ch >>> 8) & 0xF)] )
                  .append( UPPERCASE_DIGITS[((ch >>> 4) & 0xF)] )
                  .append( UPPERCASE_DIGITS[(ch & 0xF)] );
  }

  /**
   * Converts the string to the unicode format '\u0020'.
   *
   * This format is the Java source code format.
   *
   * If {@code null} is passed in, {@code null} will be returned.
   *
   * <pre>
   *   CharUtils.toUnicodeEscape(null) = null
   *   CharUtils.toUnicodeEscape(' ')  = "\u0020"
   *   CharUtils.toUnicodeEscape('A')  = "\u0041"
   * </pre>
   *
   * @param ch
   *          the character to convert, may be null
   * @return the escaped unicode string, null if null input
   */
  public static String toUnicodeEscape(@Nullable final Character ch) {
    if (ch == null) {
      return null;
    } else {
      final StringBuilder builder = new StringBuilder();
      toUnicodeEscape(ch, builder);
      return builder.toString();
    }
  }

  /**
   * Converts the string to the unicode format '\u0020'.
   *
   * This format is the Java source code format.
   *
   * If {@code null} is passed in, {@code null} will be returned.
   *
   * <pre>
   *   CharUtils.toUnicodeEscape(null) = null
   *   CharUtils.toUnicodeEscape(' ')  = "\u0020"
   *   CharUtils.toUnicodeEscape('A')  = "\u0041"
   * </pre>
   *
   * @param ch
   *          the character to convert, which can't be null
   * @param builder
   *          the {@link StringBuilder} where to append the result.
   * @return the {@link StringBuilder}.
   */
  public static StringBuilder toUnicodeEscape(final Character ch,
      final StringBuilder builder) {
    final int chValue = ch.charValue();
    return builder.append("\\u")
                  .append( UPPERCASE_DIGITS[((chValue >>> 12) & 0xF)] )
                  .append( UPPERCASE_DIGITS[((chValue >>> 8) & 0xF)] )
                  .append( UPPERCASE_DIGITS[((chValue >>> 4) & 0xF)] )
                  .append( UPPERCASE_DIGITS[(chValue & 0xF)] );
  }
}
