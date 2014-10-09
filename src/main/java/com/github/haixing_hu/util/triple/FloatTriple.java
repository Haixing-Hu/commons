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

package com.github.haixing_hu.util.triple;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

/**
 * This class is used to represent a triple of {@code float} values.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class FloatTriple implements Serializable {
  private static final long serialVersionUID = 99160537425101428L;

  public float first;
  public float second;
  public float third;

  public FloatTriple() {
    first = 0;
    second = 0;
    third = 0;
  }

  public FloatTriple(final float t1, final float t2, final float t3) {
    first = t1;
    second = t2;
    third = t3;
  }

  @Override
  public int hashCode() {
    final int multiplier = 13117;
    int code = 1337;
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
    if (obj.getClass() != FloatTriple.class) {
      return false;
    }
    final FloatTriple other = (FloatTriple) obj;
    return (Float.floatToIntBits(first) == Float.floatToIntBits(other.first))
        && (Float.floatToIntBits(second) == Float.floatToIntBits(other.second))
        && (Float.floatToIntBits(third) == Float.floatToIntBits(other.third));
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
