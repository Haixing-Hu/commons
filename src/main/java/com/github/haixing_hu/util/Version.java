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

package com.github.haixing_hu.util;

import java.io.Serializable;
import java.text.ParseException;

import javax.annotation.concurrent.Immutable;

import com.github.haixing_hu.io.serialize.BinarySerialization;
import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.lang.Cloneable;
import com.github.haixing_hu.text.NumberFormat;
import com.github.haixing_hu.text.NumberFormatOptions;
import com.github.haixing_hu.text.TextParseException;

import static com.github.haixing_hu.lang.Argument.requireNonNull;
import static com.github.haixing_hu.text.ErrorCode.*;

/**
 * Represents a version number.
 *
 * @author Haixing Hu
 */
@Immutable
public final class Version implements Serializable, Cloneable<Version>,
    Comparable<Version> {

  private static final long   serialVersionUID          = - 1155725203116066917L;

  public static final char    SEPARATOR                 = '.';
  public static final int     MAX_NUMBER                = 255;
  public static final Version EMPTY                     = new Version(0, false);

  static {
    BinarySerialization.register(Version.class, VersionBinarySerializer.INSTANCE);
    XmlSerialization.register(Version.class, VersionXmlSerializer.INSTANCE);
  }

  public static Version create(final String str) {
    try {
      return new Version(str);
    } catch (final ParseException e) {
      throw new IllegalArgumentException(e);
    }
  }

  protected final int number;

  public Version(final int major) {
    if ((major < 0) || (major > MAX_NUMBER)) {
      throw new IllegalArgumentException();
    }
    number = (major << 24);
  }

  public Version(final int major, final int minor) {
    if ((major < 0) || (major > MAX_NUMBER)
        || (minor < 0) || (minor > MAX_NUMBER)) {
      throw new IllegalArgumentException();
    }
    this.number = (major << 24) | (minor << 16);
  }

  public Version(final int major, final int minor, final int milli) {
    if ((major < 0) || (major > MAX_NUMBER)
        || (minor < 0) || (minor > MAX_NUMBER)
        || (milli < 0) || (milli > MAX_NUMBER)) {
      throw new IllegalArgumentException();
    }
    this.number = (major << 24) | (minor << 16) | (milli << 8);
  }

  public Version(final int major, final int minor, final int milli, final int micro) {
    if ((major < 0) || (major > MAX_NUMBER)
        || (minor < 0) || (minor > MAX_NUMBER)
        || (milli < 0) || (milli > MAX_NUMBER)
        || (micro < 0) || (micro > MAX_NUMBER)) {
      throw new IllegalArgumentException();
    }
    this.number = (major << 24) | (minor << 16) | (milli << 8) | micro;
  }

  public Version(final String str) throws ParseException {
    requireNonNull("str", str);
    int strLen;
    if ((strLen = str.length()) == 0) {
      throw new TextParseException(str, EMPTY_VALUE, 0);
    }
    final NumberFormat nf = new NumberFormat();
    final NumberFormatOptions options = nf.getOptions();
    options.setDecimal(true);
    options.setKeepBlank(true);
    int group = 0;
    int shift = 24;
    int newNumber = 0;
    int index = 0;
    while (index < strLen) {
      if (group == 4) {
        // too many groups
        throw new TextParseException(str, INVALID_SYNTAX, index);
      }
      if (group > 0) {
        if (str.charAt(index) != SEPARATOR) {
          throw new TextParseException(str, INVALID_SYNTAX, index);
        }
        // skip the separator
        ++index;
      }
      final int v = nf.parseInt(str, index);
      if (nf.fail()) {
        throw new TextParseException(str, nf.getParsePosition());
      } else if (v < 0) {
        throw new TextParseException(str, NUMBER_UNDERFLOW, nf.getParseIndex());
      } else if (v > MAX_NUMBER) {
        throw new TextParseException(str, NUMBER_OVERFLOW, nf.getParseIndex());
      }
      assert (shift >= 0);
      newNumber |= (v << shift);
      shift -= 8;
      ++group;
      index = nf.getParseIndex();
    }
    // now set the number
    number = newNumber;
  }

  protected Version(final int number, final boolean dummy) {
    this.number = number;
  }

  public int major() {
    return (number >>> 24);
  }

  public int minor() {
    return ((number >>> 16) & 0xFF);
  }

  public int milli() {
    return ((number >>> 8) & 0xFF);
  }

  public int micro() {
    return (number & 0xFF);
  }

  @Override
  public int hashCode() {
    return number;
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
    final Version other = (Version) obj;
    return (number == other.number);
  }

  @Override
  public int compareTo(final Version that) {
    // Note also that we MUST compare this.number and that.number as
    // UNSIGNED integer.
    int thisNumber = this.number;
    int thatNumber = that.number;
    if (thisNumber < 0) {
      // the highest bit of this.number is set
      if (thatNumber >= 0) {
        // the highest bit of that.number is NOT set, then this > that
        return + 1;
      } else {
        // the highest bit of that.number is also set, clear both highest bit
        thisNumber &= 0x7FFFFFFF;
        thatNumber &= 0x7FFFFFFF;
      }
    } else if (thatNumber < 0) {
      // the highest bit of that.number is set, then this < that
      return - 1;
    }
    // now compare two positive number thisNumber and thatNumber directly;
    return thisNumber - thatNumber;
  }

  @Override
  public String toString() {
    final int major = (number >>> 24);
    final int minor = ((number >>> 16) & 0xFF);
    final int milli = ((number >>> 8) & 0xFF);
    final int micro = (number & 0xFF);
    final StringBuilder builder = new StringBuilder();
    if (micro == 0) {
      if (milli == 0) {
        // print the major.minor
        builder.append(major)
               .append(SEPARATOR)
               .append(minor);
      } else {
        // print the major.minor.milli
        builder.append(major)
               .append(SEPARATOR)
               .append(minor)
               .append(SEPARATOR)
               .append(milli);
      }
    } else {
      // print the major.minor.milli.micro
      builder.append(major)
             .append(SEPARATOR)
             .append(minor)
             .append(SEPARATOR)
             .append(milli)
             .append(SEPARATOR)
             .append(micro);
    }
    return builder.toString();
  }

  @Override
  public Version clone() {
    return new Version(number, false);
  }
}
