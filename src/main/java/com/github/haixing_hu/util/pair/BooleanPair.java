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
package com.github.haixing_hu.util.pair;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

/**
 * This class is used to represent a pair of {@code boolean} values.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class BooleanPair implements Serializable {
  private static final long serialVersionUID = 162911917195180929L;

  public boolean first;
  public boolean second;

  public BooleanPair() {
    first = false;
    second = false;
  }

  public BooleanPair(final boolean t1, final boolean t2) {
    first = t1;
    second = t2;
  }

  @Override
  public int hashCode() {
    final int multiplier = 111115;
    int code = 12315;
    code = Hash.combine(code, multiplier, first);
    code = Hash.combine(code, multiplier, second);
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
    if (obj.getClass() != BooleanPair.class) {
      return false;
    }
    final BooleanPair other = (BooleanPair) obj;
    return (first == other.first)
        && (second == other.second);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("first", first)
               .append("second", second)
               .toString();
  }
}
