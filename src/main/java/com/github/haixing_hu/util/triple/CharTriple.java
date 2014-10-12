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
package com.github.haixing_hu.util.triple;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

/**
 * This class is used to represent a triple of {@code char} values.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class CharTriple implements Serializable {
  private static final long serialVersionUID = 3038555683328429236L;

  public char first;
  public char second;
  public char third;

  public CharTriple() {
    first = 0;
    second = 0;
    third = 0;
  }

  public CharTriple(final char t1, final char t2, final char t3) {
    first = t1;
    second = t2;
    third = t3;
  }

  @Override
  public int hashCode() {
    final int multiplier = 13113;
    int code = 1333;
    code = Hash.combine(code, multiplier, first);
    code = Hash.combine(code, multiplier, second);
    code = Hash.combine(code, multiplier, third);
    return code;
  }

  @Override
  public boolean equals(@Nullable final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (obj.getClass() != CharTriple.class) {
      return false;
    }
    final CharTriple other = (CharTriple) obj;
    return (first == other.first)
        && (second == other.second)
        && (third == other.third);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("first", first)
               .append("second", second)
               .append("third", third)
               .toString();
  }

}
