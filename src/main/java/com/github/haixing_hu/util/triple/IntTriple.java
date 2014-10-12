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
 * This class is used to represent a triple of {@code int} values.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class IntTriple implements Serializable {
  private static final long serialVersionUID = 4320326299014925006L;

  public int first;
  public int second;
  public int third;

  public IntTriple() {
    first = 0;
    second = 0;
    third = 0;
  }

  public IntTriple(final int t1, final int t2, final int t3) {
    first = t1;
    second = t2;
    third = t3;
  }

  @Override
  public int hashCode() {
    final int multiplier = 13119;
    int code = 1339;
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
    if (obj.getClass() != IntTriple.class) {
      return false;
    }
    final IntTriple other = (IntTriple) obj;
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
