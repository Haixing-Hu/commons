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

import java.io.Serializable;

import com.github.haixing_hu.lang.Cloneable;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.text.tostring.ToStringBuilder;


/**
 * A {@link ParseOptions} object stores the options for parsing text.
 *
 * @author Haixing Hu
 */
public class ParseOptions implements Serializable, Cloneable<ParseOptions> {

  private static final long serialVersionUID = 1340148038313182446L;

  public static final int DEFAULT_FLAGS = FormatFlag.DEFAULT;

  public static final int DEFAULT_RADIX = 10;

  public static final int DEFAULT_MAX_DIGITS = Integer.MAX_VALUE;

  /**
   * The default parse options.
   */
  public static final ParseOptions DEFAULT = new UnmodifiableParseOptions();

  /**
   * The default parse options keeping the blanks.
   */
  public static final ParseOptions DEFAULT_KEEP_BLANKS = new UnmodifiableParseOptions(
      FormatFlag.DEFAULT | FormatFlag.KEEP_BLANKS);

  /**
   * The parse options for hex byte.
   */
  public static final ParseOptions HEX_BYTE = new UnmodifiableParseOptions(
      ((FormatFlag.DEFAULT & (~ FormatFlag.RADIX_MASK)) | FormatFlag.HEX), 16, 2);

  protected int flags;
  protected int defaultRadix;
  protected int maxDigits;

  public ParseOptions() {
    flags = DEFAULT_FLAGS;
    defaultRadix = DEFAULT_RADIX;
    maxDigits = DEFAULT_MAX_DIGITS;
  }

  public ParseOptions(final int flags) {
    this.flags = flags;
    defaultRadix = DEFAULT_RADIX;
    maxDigits = DEFAULT_MAX_DIGITS;
  }

  public ParseOptions(final int flags, final int defaultRadix) {
    this.flags = flags;
    this.defaultRadix = defaultRadix;
    maxDigits = DEFAULT_MAX_DIGITS;
  }

  public ParseOptions(final int flags, final int defaultRadix, final int maxDigits) {
    this.flags = flags;
    this.defaultRadix = defaultRadix;
    this.maxDigits = maxDigits;
  }

  public final int getFlags() {
    return flags;
  }

  public void setFlags(final int flags) {
    this.flags = flags;
  }

  public void addFlags(final int flags) {
    this.flags |= flags;
  }

  public void addFlags(final int flags, final int mask) {
    this.flags &= (~ mask);
    this.flags |= flags;
  }

  public final int getDefaultRadix() {
    return defaultRadix;
  }

  public void setDefaultRadix(final int defaultRadix) {
    this.defaultRadix = defaultRadix;
  }

  public final int getMaxDigits() {
    return maxDigits;
  }

  public void setMaxDigits(final int maxDigits) {
    this.maxDigits = maxDigits;
  }

  public void resetMaxDigits() {
    this.maxDigits = DEFAULT_MAX_DIGITS;
  }

  public final boolean isBoolAlpha() {
    return (flags & FormatFlag.BOOL_ALPHA) != 0;
  }

  public void setBoolAlpha(final boolean value) {
    if (value) {
      flags |= FormatFlag.BOOL_ALPHA;
    } else {
      flags &= (~ FormatFlag.BOOL_ALPHA);
    }
  }

  public final boolean isGrouping() {
    return (flags & FormatFlag.GROUPING) != 0;
  }

  public void setGrouping(final boolean value) {
    if (value) {
      flags |= FormatFlag.GROUPING;
    } else {
      flags &= (~ FormatFlag.GROUPING);
    }
  }

  public final boolean isKeepBlank() {
    return (flags & FormatFlag.KEEP_BLANKS) != 0;
  }

  public void setKeepBlank(final boolean value) {
    if (value) {
      flags |= FormatFlag.KEEP_BLANKS;
    } else {
      flags &= (~ FormatFlag.KEEP_BLANKS);
    }
  }

  public final boolean isBinary() {
    return (flags & FormatFlag.BINARY) != 0;
  }

  public void setBinary(final boolean value) {
    if (value) {
      // clear all other radix options
      flags &= (~ FormatFlag.RADIX_MASK);
      // set binary option
      flags |= FormatFlag.BINARY;
    } else {
      flags &= (~ FormatFlag.BINARY);
    }
  }

  public final boolean isOctal() {
    return (flags & FormatFlag.OCTAL) != 0;
  }

  public void setOctal(final boolean value) {
    if (value) {
      // clear all other radix options
      flags &= (~ FormatFlag.RADIX_MASK);
      // set octal option
      flags |= FormatFlag.OCTAL;
    } else {
      flags &= (~ FormatFlag.OCTAL);
    }
  }

  public final boolean isDecimal() {
    return (flags & FormatFlag.DECIMAL) != 0;
  }

  public void setDecimal(final boolean value) {
    if (value) {
      // clear all other radix options
      flags &= (~ FormatFlag.RADIX_MASK);
      // set decimal option
      flags |= FormatFlag.DECIMAL;
    } else {
      flags &= (~ FormatFlag.DECIMAL);
    }
  }

  public final boolean isHex() {
    return (flags & FormatFlag.HEX) != 0;
  }

  public void setHex(final boolean value) {
    if (value) {
      // clear all other radix options
      flags &= (~ FormatFlag.RADIX_MASK);
      // set hex option
      flags |= FormatFlag.HEX;
    } else {
      flags &= (~ FormatFlag.HEX);
    }
  }

  public void clearRadixOptions() {
    // clear all other radix options
    flags &= (~ FormatFlag.RADIX_MASK);
  }

  public final boolean isFixPoint() {
    return (flags & FormatFlag.FIXED_POINT) != 0;
  }

  public void setFixPoint(final boolean value) {
    if (value) {
      // clear all other real options
      flags &= (~ FormatFlag.REAL_MASK);
      // set fix point option
      flags |= FormatFlag.FIXED_POINT;
    } else {
      flags &= (~ FormatFlag.FIXED_POINT);
    }
  }

  public final boolean isScientific() {
    return (flags & FormatFlag.SCIENTIFIC) != 0;
  }

  public void setScientific(final boolean value) {
    if (value) {
      // clear all other real options
      flags &= (~ FormatFlag.REAL_MASK);
      // set scientific option
      flags |= FormatFlag.SCIENTIFIC;
    } else {
      flags &= (~ FormatFlag.SCIENTIFIC);
    }
  }

  public final boolean isGeneral() {
    return (flags & FormatFlag.SHORT_REAL) != 0;
  }

  public void setGeneral(final boolean value) {
    if (value) {
      // clear all other real options
      flags &= (~ FormatFlag.REAL_MASK);
      // set scientific option
      flags |= FormatFlag.SHORT_REAL;
    } else {
      flags &= (~ FormatFlag.SHORT_REAL);
    }
  }

  public void clearRealOptions() {
    // clear all other real options
    flags &= (~ FormatFlag.REAL_MASK);
  }

  public void reset() {
    flags = DEFAULT_FLAGS;
    defaultRadix = DEFAULT_RADIX;
    maxDigits = DEFAULT_MAX_DIGITS;
  }

  @Override
  public ParseOptions clone() {
    final ParseOptions cloned = new ParseOptions();
    cloned.flags = flags;
    cloned.defaultRadix = defaultRadix;
    cloned.maxDigits = maxDigits;
    return cloned;
  }

  @Override
  public int hashCode() {
    final int multiplier = 13;
    int code = 17;
    code = Hash.combine(code, multiplier, flags);
    code = Hash.combine(code, multiplier, defaultRadix);
    code = Hash.combine(code, multiplier, maxDigits);
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
    final ParseOptions other = (ParseOptions) obj;
    return (flags == other.flags)
    	&& (defaultRadix == other.defaultRadix)
    	&& (maxDigits == other.maxDigits);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("flags", flags)
               .append("defaultRadix", defaultRadix)
               .append("maxDigits", maxDigits)
               .toString();
  }
}
