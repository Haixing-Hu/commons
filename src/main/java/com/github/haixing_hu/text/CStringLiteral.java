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

import java.text.ParseException;

import javax.annotation.concurrent.ThreadSafe;

/**
 * A class for encoding and decoding C-style string literal.
 * <p>
 * As defined by the ISO C++ Standard 2003, the C-style string literal has the
 * following grammar:
 * <p>
 * <pre class="code">
 *  string-literal:
 *              ""
 *              L""
 *              "s-char-sequence"
 *              L"s-char-sequence"
 *
 *  s-char-sequence:
 *              s-char
 *              s-char-sequence s-char
 *
 *  s-char:
 *              any member of the source character set except
 *              the double-quote ", backslash \, or new-line character
 *              escape-sequence
 *              universal-character-name
 *
 *  escape-sequence:
 *              simple-escape-sequence
 *              octal-escape-sequence
 *              hexadecimal-escape-sequence
 *
 *  simple-escape-sequence: one of
 *              \` \" \? \\
 *              \a \b \f \n \r \t \v
 *
 *  octal-escape-sequence:
 *              \ octal-digit
 *              \ octal-digit octal-digit
 *              \ octal-digit octal-digit octal-digit
 *
 *  hexadecimal-escape-sequence:
 *              \x hexadecimal-digit
 *              hexadecimal-escape-sequence hexadecimal-digit
 *
 *  universal-character-name:
 *              \\u hex-quad
 *              \U hex-quad hex-quad
 *
 *  hex-quad:
 *              hexadecimal-digit hexadecimal-digit hexadecimal-digit hexadecimal-digit
 *
 *  </pre>
 * <p>
 * Note that "\\u" should be a single '\' before 'u', but Java treats that as a
 * Unicode prefix, so we use "\\u".
 * <p>
 * The character designated by the universal-character-name \UNNNNNNNN is that
 * character whose character short name in ISO/IEC 10646 is NNNNNNNN; the
 * character designated by the universal-character-name \\uNNNN is that
 * character whose character short name in ISO/IEC 10646 is 0000NNNN. If the
 * hexadecimal value for a universal character name is less than 0x20 or in the
 * range 0x7F-0x9F (inclusive), or if the universal character name designates a
 * character in the basic source character set, then the program is illformed.
 * (Standard 2.2.2)
 * <p>
 * The basic source character set consists of 96 characters: the space
 * character, the control characters representing horizontal tab, vertical tab,
 * form feed, and new-line, plus the following 91 graphical characters:
 * (Standard 2.2.1)
 * <p>
 *  <pre class="code">
 *      a b c d e f g h i j k l m n o p q r s t u v w x y z
 *      A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
 *      0 1 2 3 4 5 6 7 8 9
 *      _ { } [ ] # ( ) < > % : ; . ? * + - / ^ & | ~ ! = , \ " '
 *  </pre>
 * <p>
 * Note that the '$', '@' and '`' are NOT required by the C++ standard to be in
 * the source character set, but in fact they are used widely. So this function
 * will also treat them as valid characters.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public class CStringLiteral {

  private static final byte ACTION_ERROR   = -1;
  private static final byte ACTION_UTF16   = -2;
  private static final byte ACTION_UTF32   = -3;
  private static final byte ACTION_HEX     = -4;
  private static final byte ACTION_OCTAL   = -5;

  //  the ESCAPE_ACTION[ch] stores the action need to done if a char ch is right after the
  //  escape character \. If the ESCAPE_ACTION[ch] is none of the predefined actions, the
  //  ch after \ consists of a simple escape sequence, and it denotes the character with the
  //  code point of ESCAPE_ACTION[ch].
  private static final byte ESCAPE_ACTION[] = {
       /*         0    1    2    3    4    5    6    7   8    9    A    B    C    D    E    F  */
       /* 0 */   -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1, -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,
       /* 1 */   -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1, -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,
       /* 2 */   -1,  -1, '"',  -1,  -1,  -1,  -1,'\'', -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,
       /* 3 */   -5,  -5,  -5,  -5,  -5,  -5,  -5,  -5, -1,  -1,  -1,  -1,  -1,  -1,  -1,  '?',
       /* 4 */   -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1, -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,
       /* 5 */   -1,  -1,  -1,  -1,  -1,  -3,  -1,  -1, -1,  -1,  -1,  -1,'\\',  -1,  -1,  -1,
       /* 6 */   -1, 0x7,'\b',  -1,  -1,  -1,'\f',  -1, -1,  -1,  -1,  -1,  -1,  -1,'\n',  -1,
       /* 7 */   -1,  -1,'\r',  -1,'\t',  -2, 0xB,  -1, -4,  -1,  -1,  -1,  -1,  -1,  -1,  -1,
  };

  //  all the characters in the basic source character set plus $, @ and ` are set to 1,
  //  totally 96 + 3 = 99 characters are set.
  private static final byte SOURCE_CHARACTER_SET[] = {
       /*         0    1    2    3    4    5    6    7   8    9    A    B    C    D    E    F  */
       /* 0 */    0,   0,   0,   0,   0,   0,   0,   0,  0,   1,   1,   1,   1,   0,   0,   0,
       /* 1 */    0,   0,   0,   0,   0,   0,   0,   0,  0,   0,   0,   0,   0,   0,   0,   0,
       /* 2 */    1,   1,   1,   1,   1,   1,   1,   1,  1,   1,   1,   1,   1,   1,   1,   1,
       /* 3 */    1,   1,   1,   1,   1,   1,   1,   1,  1,   1,   1,   1,   1,   1,   1,   1,
       /* 4 */    1,   1,   1,   1,   1,   1,   1,   1,  1,   1,   1,   1,   1,   1,   1,   1,
       /* 5 */    1,   1,   1,   1,   1,   1,   1,   1,  1,   1,   1,   1,   1,   1,   1,   1,
       /* 6 */    1,   1,   1,   1,   1,   1,   1,   1,  1,   1,   1,   1,   1,   1,   1,   1,
       /* 7 */    1,   1,   1,   1,   1,   1,   1,   1,  1,   1,   1,   1,   1,   1,   1,   0,
  };

  private static final String ERROR_INVALID_CHARACTER    = "invalid character.";
  private static final String ERROR_INCOMPLETE           = "incomplete escape sequence.";
  private static final String ERROR_INVALID_HEX          = "invalid hexadecimal-escape-sequence.";
  private static final int MAX_ASCII_CHAR_VALUE          = 0x7F;
  private static final int UTF16_DIGITS                  = 4;
  private static final int UTF32_DIGITS                  = 8;
  private static final int MAX_OCTAL_DIGITS              = 3;

  /**
   * Given a C-style string literal (without quoted), decode it into a byte
   * array.
   * <p>
   * This function reads a C-style string literal, and decodes it to a byte
   * array. The string literal is in the unquoted form (i.e., without leading
   * and trailing double quote), and does not contain any character whose code
   * point is greater than 0xFF.
   *
   * @param str
   *          a character sequence contains a C-style string literal. It can't
   *          be null, and must has a non-zero length. Note also that this
   *          string should not contain any character greater than 0xFF.
   * @return the byte array of the decoded result.
   * @throws ParsingException
   *           if the given C-style string literal is ill-formed.
   */
  public static byte[] decode(final CharSequence str)
      throws ParseException {
    return decode(str, 0, str.length());
  }

  /**
   * Given a C-style string literal (without quoted), decode it into a byte
   * array.
   * <p>
   * This function reads a C-style string literal, and decodes it to a byte
   * array. The string literal is in the unquoted form (i.e., without leading
   * and trailing double quote), and does not contain any character whose code
   * point is greater than 0xFF.
   *
   * @param str
   *          a character sequence contains a C-style string literal. It can't
   *          be null, and must has a non-zero length. Note also that this
   *          string should not contain any character greater than 0xFF.
   * @param startIndex
   *          the current where to start parsing. The parsing will stop at the
   *          end of str.
   * @return the byte array of the decoded result.
   * @throws ParsingException
   *           if the given C-style string literal is ill-formed.
   */
  public static byte[] decode(final CharSequence str, final int startIndex)
      throws ParseException {
    return decode(str, startIndex, str.length());
  }

  /**
   * Given a C-style string literal (without quoted), decode it into a byte
   * array.
   * <p>
   * This function reads a C-style string literal, and decodes it to a byte
   * array. The string literal is in the unquoted form (i.e., without leading
   * and trailing double quote), and does not contain any character whose code
   * point is greater than 0xFF.
   *
   * @param str
   *          a character sequence contains a C-style string literal. It can't
   *          be null, and must has a non-zero length. Note also that this
   *          string should not contain any character greater than 0xFF.
   * @param startIndex
   *          the current where to start parsing.
   * @param endIndex
   *          the current where to stop parsing, i.e., parsing the text within the
   *          range [startIndex, endIndex).
   * @return the byte array of the decoded result.
   * @throws TextParseException
   *           if the given C-style string literal is ill-formed.
   */
  public static byte[] decode(final CharSequence str, final int startIndex,
      final int endIndex) throws TextParseException {
    byte[] result = new byte[str.length()];
    final NumberFormat nf = new NumberFormat();
    final NumberFormatOptions options = nf.getOptions();
    int len = 0;
    int index = startIndex;
    while (index < endIndex) {
      char ch = str.charAt(index);
      if (ch != '\\') {
        // ch is not escape character '\'
        if ((ch > MAX_ASCII_CHAR_VALUE) || (SOURCE_CHARACTER_SET[ch] == 0)) {
          // ch is not a source character, which is an error
          throw new TextParseException(str, startIndex, endIndex,
              ERROR_INVALID_CHARACTER, index);
        }
        // directly convert ch into its byte value
        result[len++] = (byte) ch;
        ++index;
      } else {
        // ch is escape character '\'
        ++index;
        if (index == endIndex) {
          throw new TextParseException(str, startIndex, endIndex,
              ERROR_INCOMPLETE, index);
        }
        ch = str.charAt(index);
        if (ch > MAX_ASCII_CHAR_VALUE) { // error
          throw new TextParseException(str, startIndex, endIndex,
              ERROR_INVALID_CHARACTER, index);
        }
        final byte action = ESCAPE_ACTION[ch];
        switch (action) {
          case ACTION_ERROR:
            return null;
          case ACTION_UTF16:
            // start parsing a UTF-16 code point
            // note that by Standard 2.2.2, it must has exactly 4 hex
            // digits.
            ++index; // skip the 'u'
            options.setKeepBlank(true); // don't skip blanks
            options.setHex(true);
            options.setMaxDigits(NumberFormatOptions.DEFAULT_MAX_DIGITS);
            result[len++] = nf.parseByte(str, index, endIndex);
            if (nf.fail()) {
              throw new TextParseException(str, startIndex, endIndex,
                  nf.getParsePosition());
            }
            if ((index + UTF16_DIGITS) != nf.getParseIndex()) {
              // the UTF16 must has exactly UTF16_DIGITS hex digits.
              throw new TextParseException(str, startIndex, endIndex,
                  ERROR_INVALID_HEX, index);
            }
            index = nf.getParseIndex();
            break;
          case ACTION_UTF32:
            // start parsing a UTF-32 code point
            // note that by Standard 2.2.2, it must has exactly 8 hex
            // digits.
            ++index; // skip the 'U'
            options.setKeepBlank(true); // don't skip blanks
            options.setHex(true);
            options.setMaxDigits(NumberFormatOptions.DEFAULT_MAX_DIGITS);
            result[len++] = nf.parseByte(str, index, endIndex);
            if (nf.fail()) {
              throw new TextParseException(str, startIndex, endIndex,
                  nf.getParsePosition());
            }
            if ((index + UTF32_DIGITS) != nf.getParseIndex()) {
              // the UTF32 must has exactly UTF32_DIGITS hex digits.
              throw new TextParseException(str, startIndex, endIndex,
                  ERROR_INVALID_HEX, index);
            }
            index = nf.getParseIndex();
            break;
          case ACTION_HEX:
            // start parsing a HEX code point.
            // note that by Standard 2.13.2.4,
            // there may be 1 or more hex digits
            ++index; // skip the 'x'
            options.setKeepBlank(true); // don't skip blanks
            options.setHex(true);
            options.setMaxDigits(NumberFormatOptions.DEFAULT_MAX_DIGITS);
            result[len++] = nf.parseByte(str, index, endIndex);
            if (nf.fail()) {
              throw new TextParseException(str, startIndex, endIndex, nf.getParsePosition());
            }
            index = nf.getParseIndex();
            break;
          case ACTION_OCTAL:
            // start parsing a OCATL code point.
            // note that by Standard 2.13.2.4,
            // there may be 1, 2, or 3 octal digits
            options.setKeepBlank(true); // don't skip blanks
            options.setOctal(true);
            options.setMaxDigits(MAX_OCTAL_DIGITS);
            result[len++] = nf.parseByte(str, index, endIndex);
            if (nf.fail()) {
              throw new TextParseException(str, startIndex, endIndex,
                  nf.getParsePosition());
            }
            index = nf.getParseIndex();
            break;
          default:
            // \ followed by ch is a simple escape sequence, and
            // the character it denotes is the value of action
            assert (len < result.length);
            result[len++] = action;
            ++index;
            break;
        } // end of switch
      } // end of else
    } // end of while
    if (len < result.length) {
      // Shrink the array if necessary
      final byte[] newResult = new byte[len];
      System.arraycopy(result, 0, newResult, 0, len);
      result = newResult;
    }
    return result;
  }

  /**
   * Given a byte array of the raw data of an ASCII string, encode it to a
   * C-style string literal (without quoted).
   * <p>
   * This function reads a byte array of the raw data of an ASCII string, and
   * encodes it into a C-style string literal. The string literal is in the
   * unquoted form (i.e., without leading and trailing double quote), and does
   * not contain any character whose code point is greater than 0xFF.
   *
   * @param rawData
   *          the byte array of the raw data of an ASCII string, which cannot be
   *          {@code null}.
   * @return the C-style string literal encoded from the byte array.
   */
  public static String encode(final byte[] rawData) {
    final StringBuilder builder = new StringBuilder();
    encode(rawData, builder);
    return builder.toString();
  }

  /**
   * Given a byte array of the raw data of an ASCII string, encode it to a
   * C-style string literal (without quoted).
   * <p>
   * This function reads a byte array of the raw data of an ASCII string, and
   * encodes it into a C-style string literal. The string literal is in the
   * unquoted form (i.e., without leading and trailing double quote), and does
   * not contain any character whose code point is greater than 0xFF.
   *
   * @param rawData
   *          the byte array of the raw data of an ASCII string, which cannot be
   *          {@code null}.
   * @param builder
   *          used to append the C-style string literal encoded from the byte
   *          array.
   */
  public static void encode(final byte[] rawData, final StringBuilder builder) {
    final NumberFormat nf = new NumberFormat();
    final NumberFormatOptions options = nf.getOptions();
    options.setShowRadix(false);
    options.setHex(true);
    options.setUppercase(true);
    options.setIntPrecision(2);
    for (int i = 0; i < rawData.length; ++i) {
      final char ch = (char) (rawData[i] & 0xFF);
      switch (ch) {
        case '"': // "\""
          builder.append('\\').append('"');
          break;
        case '\'': // "\'"
          builder.append('\\').append('\'');
          break;
        case '?':
          builder.append('\\').append('?');
          break;
        case '\\': // "\\"
          builder.append('\\').append('\\');
          break;
        case 0x07: // "\a"
          builder.append('\\').append('a');
          break;
        case '\b': // "\b"
          builder.append('\\').append('b');
          break;
        case '\f': // "\f"
          builder.append('\\').append('f');
          break;
        case '\n': // "\n"
          builder.append('\\').append('n');
          break;
        case '\r': // "\r"
          builder.append('\\').append('r');
          break;
        case '\t': // "\t"
          builder.append('\\').append('t');
          break;
        case 0x0B: // "\v"
          builder.append('\\').append('v');
          break;
        default:
          if ((ch <= MAX_ASCII_CHAR_VALUE) && (SOURCE_CHARACTER_SET[ch] != 0)) {
              builder.append(ch);
          } else {
            builder.append('\\').append('x');
            nf.formatByte(rawData[i], builder);
          }
          break;
      }
    }
  }
}
