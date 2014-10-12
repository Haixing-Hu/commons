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

import com.github.haixing_hu.text.tostring.ToStringBuilder;

import static com.github.haixing_hu.text.FormatFlag.*;

/**
 * A {@link FormatFlags} is a bitwise or combination of format flags.
 *
 * @author Haixing Hu
 */
public class FormatFlags {

  public static final int[] ALLOWED_RADIX = {
    2, 8, 10, 16,
  };

  public static final int DEFAULT_FLAGS = FormatFlag.DEFAULT;

  protected int flags;

  public FormatFlags() {
    flags = FormatFlag.DEFAULT;
  }

  public FormatFlags(final int flags) {
    this.flags = flags;
  }

  public void reset() {
    flags = FormatFlag.DEFAULT;
  }

  public int getFlags() {
    return flags;
  }

  public void setFlags(final int flags) {
    this.flags = flags;
  }

  public boolean isBoolAlpha() {
    return (flags & BOOL_ALPHA) != 0;
  }

  public void setBoolAlpha(final boolean value) {
    if (value) {
      flags |= BOOL_ALPHA;
    } else {
      flags &= (~ BOOL_ALPHA);
    }
  }

  public boolean isGrouping() {
    return (flags & GROUPING) != 0;
  }

  public void setGrouping(final boolean value) {
    if (value) {
      flags |= GROUPING;
    } else {
      flags &= (~ GROUPING);
    }
  }

  public boolean isKeepBlank() {
    return (flags & KEEP_BLANKS) != 0;
  }

  public void setKeepBlank(final boolean value) {
    if (value) {
      flags |= KEEP_BLANKS;
    } else {
      flags &= (~ KEEP_BLANKS);
    }
  }

  public boolean isBinary() {
    return (flags & BINARY) != 0;
  }

  public void setBinary(final boolean value) {
    if (value) {
      // clear all other radix options
      flags &= (~ RADIX_MASK);
      // set binary option
      flags |= BINARY;
    } else {
      flags &= (~ BINARY);
    }
  }

  public boolean isOctal() {
    return (flags & OCTAL) != 0;
  }

  public void setOctal(final boolean value) {
    if (value) {
      // clear all other radix options
      flags &= (~ RADIX_MASK);
      // set octal option
      flags |= OCTAL;
    } else {
      flags &= (~ OCTAL);
    }
  }

  public boolean isDecimal() {
    return (flags & DECIMAL) != 0;
  }

  public void setDecimal(final boolean value) {
    if (value) {
      // clear all other radix options
      flags &= (~ RADIX_MASK);
      // set decimal option
      flags |= DECIMAL;
    } else {
      flags &= (~ DECIMAL);
    }
  }

  public boolean isHex() {
    return (flags & HEX) != 0;
  }

  public void setHex(final boolean value) {
    if (value) {
      // clear all other radix options
      flags &= (~ RADIX_MASK);
      // set hex option
      flags |= HEX;
    } else {
      flags &= (~ HEX);
    }
  }

  public int getRadix() {
    switch (flags & RADIX_MASK) {
      case BINARY:
        return 2;
      case OCTAL:
        return 8;
      case DECIMAL:
        return 10;
      case HEX:
        return 10;
      default:
        return -1;
    }
  }

  public void setRadix(final int radix) {
    flags &= (~ RADIX_MASK);
    switch (radix) {
      case 2:
        flags |= BINARY;
        break;
      case 8:
        flags |= OCTAL;
        break;
      case 10:
        flags |= DECIMAL;
        break;
      case 16:
        flags |= HEX;
        break;
    }
  }

  public void clearRadixOptions() {
    // clear all other radix options
    flags &= (~ RADIX_MASK);
  }

  public boolean isFixPoint() {
    return (flags & FIXED_POINT) != 0;
  }

  public void setFixPoint(final boolean value) {
    if (value) {
      // clear all other real options
      flags &= (~ REAL_MASK);
      // set fix point option
      flags |= FIXED_POINT;
    } else {
      flags &= (~ FIXED_POINT);
    }
  }

  public boolean isScientific() {
    return (flags & SCIENTIFIC) != 0;
  }

  public void setScientific(final boolean value) {
    if (value) {
      // clear all other real options
      flags &= (~ REAL_MASK);
      // set scientific option
      flags |= SCIENTIFIC;
    } else {
      flags &= (~ SCIENTIFIC);
    }
  }

  public boolean isShortReal() {
    return (flags & SHORT_REAL) != 0;
  }

  public void setShortReal(final boolean value) {
    if (value) {
      // clear all other real options
      flags &= (~ REAL_MASK);
      // set scientific option
      flags |= SHORT_REAL;
    } else {
      flags &= (~ SHORT_REAL);
    }
  }

  public void clearRealOptions() {
    // clear all other real options
    flags &= (~ REAL_MASK);
  }

  public boolean isShowRadix() {
    return (flags & SHOW_RADIX) != 0;
  }

  public void setShowRadix(final boolean value) {
    if (value) {
      flags |= SHOW_RADIX;
    } else {
      flags &= (~ SHOW_RADIX);
    }
  }

  public boolean isShowPoint() {
    return (flags & SHOW_POINT) != 0;
  }

  public void setShowPoint(final boolean value) {
    if (value) {
      flags |= SHOW_POINT;
    } else {
      flags &= (~ SHOW_POINT);
    }
  }

  public boolean isShowPositive() {
    return (flags & SHOW_POSITIVE) != 0;
  }

  public void setShowPositive(final boolean value) {
    if (value) {
      // clear the show space option
      flags &= (~ SHOW_SPACE);
      // set the show radix option
      flags |= SHOW_POSITIVE;
    } else {
      flags &= (~ SHOW_POSITIVE);
    }
  }

  public boolean isShowSpace() {
    return (flags & SHOW_SPACE) != 0;
  }

  public void setShowSpace(final boolean value) {
    if (value) {
      // clear the show positive option
      flags &= (~ SHOW_POSITIVE);
      // set the show space option
      flags |= SHOW_SPACE;
    } else {
      flags &= (~ SHOW_SPACE);
    }
  }

  public boolean isUppercaseRadixPrefix() {
    return (flags & UPPERCASE_RADIX_PREFIX) != 0;
  }

  public void setUppercaseRadixPrefix(final boolean value) {
    if (value) {
      flags |= UPPERCASE_RADIX_PREFIX;
    } else {
      flags &= (~ UPPERCASE_RADIX_PREFIX);
    }
  }

  public boolean isUppercaseExponent() {
    return (flags & UPPERCASE_EXPONENT) != 0;
  }

  public void setUppercaseExponent(final boolean value) {
    if (value) {
      flags |= UPPERCASE_EXPONENT;
    } else {
      flags &= (~ UPPERCASE_EXPONENT);
    }
  }

  public boolean isAlignLeft() {
    return (flags & ALIGN_LEFT) != 0;
  }

  public void setAlignLeft(final boolean value) {
    if (value) {
      // clear the alignment options
      flags &= (~ ALIGN_MASK);
      // set the left option
      flags |= ALIGN_LEFT;
    } else {
      flags &= (~ ALIGN_LEFT);
    }
  }

  public boolean isAlignCenter() {
    return (flags & ALIGN_CENTER) != 0;
  }

  public void setAlignCenter(final boolean value) {
    if (value) {
      // clear the alignment options
      flags &= (~ ALIGN_MASK);
      // set the center option
      flags |= ALIGN_CENTER;
    } else {
      flags &= (~ ALIGN_CENTER);
    }
  }

  public boolean isAlignRight() {
    return (flags & ALIGN_RIGHT) != 0;
  }

  public void setAlignRight(final boolean value) {
    if (value) {
      // clear the alignment options
      flags &= (~ ALIGN_MASK);
      // set the right option
      flags |= ALIGN_RIGHT;
    } else {
      flags &= (~ ALIGN_RIGHT);
    }
  }

  public void clearAlignOptions() {
    flags &= (~ ALIGN_MASK);
  }

  public boolean isUppercase() {
    return (flags & UPPERCASE) != 0;
  }

  public void setUppercase(final boolean value) {
    if (value) {
      flags &= (~ CASE_MASK);
      flags |= UPPERCASE;
    } else {
      flags &= (~ UPPERCASE);
    }
  }

  public boolean isLowercase() {
    return (flags & LOWERCASE) != 0;
  }

  public void setLowercase(final boolean value) {
    if (value) {
      flags &= (~ CASE_MASK);
      flags |= LOWERCASE;
    } else {
      flags &= (~ LOWERCASE);
    }
  }

  public boolean isTitlecase() {
    return (flags & TITLECASE) != 0;
  }

  public void setTitlecase(final boolean value) {
    if (value) {
      flags &= (~ CASE_MASK);
      flags |= TITLECASE;
    } else {
      flags &= (~ TITLECASE);
    }
  }

  public void clearCaseOptions() {
    flags &= (~ CASE_MASK);
  }

  @Override
  public int hashCode() {
    return flags;
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
    final FormatFlags other = (FormatFlags) obj;
    return (flags == other.flags);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("flags", flags)
               .toString();
  }

}
