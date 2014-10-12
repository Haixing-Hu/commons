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

import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

import static com.github.haixing_hu.lang.Argument.*;

/**
 * The options for formatting and parsing numbers.
 *
 * @author Haixing Hu
 */
public class BooleanFormatOptions extends FormatFlags {

  public static final int DEFAULT_WIDTH = 0;

  public static final int DEFAULT_FILL = ' ';

  private int width;
  private int fill;

  public BooleanFormatOptions() {
    super();
    width = DEFAULT_WIDTH;
    fill = DEFAULT_FILL;
  }

  @Override
  public void reset() {
    super.reset();
    width = DEFAULT_WIDTH;
    fill = DEFAULT_FILL;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(final int width) {
    requireGreaterEqual("width", width, "zero", 0);
    this.width = width;
  }

  public int getFill() {
    return fill;
  }

  public void setFill(final int fill) {
    this.fill = requireValidUnicode("fill", fill);
  }

  public void assign(final BooleanFormatOptions that) {
    if (this == that) {
      return;
    }
    this.flags = that.flags;
    this.width = that.width;
    this.fill = that.fill;
  }

  @Override
  public int hashCode() {
    final int multiplier = 31;
    int code = 3;
    code = Hash.combine(code, multiplier, flags);
    code = Hash.combine(code, multiplier, width);
    code = Hash.combine(code, multiplier, fill);
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
    final BooleanFormatOptions other = (BooleanFormatOptions) obj;
    return (flags == other.flags)
        && (width == other.width)
        && (fill == other.fill);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("flags", flags)
               .append("width", width)
               .append("fill", fill)
               .toString();
  }

}
