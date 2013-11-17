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
import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * The {@link StringCodePointIterator} is used to iterate through the code points of
 * a string.
 *
 * @author Haixing Hu
 */
public final class StringCodePointIterator extends CodePointIterator
    implements Cloneable<StringCodePointIterator>, Serializable {

  private static final long serialVersionUID = - 5929301593382742173L;

  private String string;

  public StringCodePointIterator() {
    super();
    string = StringUtils.EMPTY;
    start = 0;
    end = 0;
    setToStart();
  }

  public StringCodePointIterator(final String string) {
    super();
    this.string = requireNonNull("string", string);
    this.start = 0;
    this.end = string.length();
    setToStart();
  }

  public StringCodePointIterator(final String string, int start, int end) {
    requireNonNull("string", string);
    if (start < 0) {
      start = 0;
    }
    if (end > string.length()) {
      end = string.length();
    }
    this.string = string;
    this.start = start;
    this.end = end;
    setToStart();
  }

  public String string() {
    return string;
  }

  public void reset(final String string) {
    this.string = requireNonNull("string", string);
    this.start = 0;
    this.end = string.length();
    setToStart();
  }

  public void reset(final String string, final int index) {
    requireNonNull("string", string);
    if ((index < 0) || (index > string.length())) {
      throw new IndexOutOfBoundsException();
    }
    this.string = string;
    this.start = 0;
    this.end = string.length();
    setTo(index);
  }

  public void reset(final String string, int start, int end) {
    requireNonNull("string", string);
    if (start < 0) {
      start = 0;
    }
    if (end > string.length()) {
      end = string.length();
    }
    this.string = string;
    this.start = start;
    this.end = end;
    setToStart();
  }

  public void reset(final String string, int start, int end, final int index) {
    requireNonNull("string", string);
    if (start < 0) {
      start = 0;
    }
    if (end > string.length()) {
      end = string.length();
    }
    if ((index < start) || (index > end)) {
      throw new IndexOutOfBoundsException();
    }
    this.string = string;
    this.start = start;
    this.end = end;
    setTo(index);
  }


  @Override
  public void setToStart() {
    current = Character.codePointAt(string, start);
    left = start;
    if (current < Unicode.SUPPLEMENTARY_MIN) {
      right = left + 1;
    } else {
      right = left + 2;
    }
  }

  @Override
  public void setToEnd() {
    left = end;
    right = end;
    current = -1;
  }

  @Override
  public void setToLast() {
    current = Character.codePointBefore(string, end);
    right = end;
    if (current < Unicode.SUPPLEMENTARY_MIN) {
      left = right - 1;
    } else {
      left = right - 2;
    }
  }

  @Override
  public void setTo(final int index) {
    final char ch = string.charAt(index);
    if (Unicode.isHighSurrogate(ch)) {
      current = Character.codePointAt(string, index);
      left = index;
      if (current < Unicode.SUPPLEMENTARY_MIN) {
        right = left + 1;
      } else {
        right = left + 2;
      }
    } else if (Unicode.isLowSurrogate(ch)) {
      current = Character.codePointBefore(string, index);
      right = index;
      if (current < Unicode.SUPPLEMENTARY_MIN) {
        left = right - 1;
      } else {
        left = right - 2;
      }
    } else {
      current = ch;
      left = index;
      right = index + 1;
    }
  }

  @Override
  public void forward() {
    if (right < end) {
      current = Character.codePointAt(string, right);
      left = right;
      if (current < Unicode.SUPPLEMENTARY_MIN) {
        right = left + 1;
      } else {
        right = left + 2;
      }
    } else {
      left = right;
      current = -1;
    }
  }

  @Override
  public void backward() {
    if (left > start) {
      current = Character.codePointBefore(string, left);
      right = left;
      if (current < Unicode.SUPPLEMENTARY_MIN) {
        left = right - 1;
      } else {
        left = right - 2;
      }
    }
  }

  @Override
  public StringCodePointIterator clone() {
    final StringCodePointIterator cloned = new StringCodePointIterator();
    cloned.string = string;
    cloned.start = start;
    cloned.end = end;
    cloned.left = left;
    cloned.right = right;
    cloned.current = current;
    return cloned;
  }

  @Override
  public int hashCode() {
    final int multiplier = 71;
    int code = 31;
    code = Hash.combine(code, multiplier, string);
    code = Hash.combine(code, multiplier, start);
    code = Hash.combine(code, multiplier, end);
    code = Hash.combine(code, multiplier, left);
    code = Hash.combine(code, multiplier, right);
    code = Hash.combine(code, multiplier, current);
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
    final StringCodePointIterator other = (StringCodePointIterator) obj;
    return (start == other.start)
        && (end == other.end)
        && (left == other.left)
        && (right == other.right)
        && (current == other.current)
        && (string.equals(other.string));
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("string", string)
               .append("start", start)
               .append("end", end)
               .append("left", left)
               .append("right", right)
               .append("current", current)
               .toString();
  }

}
