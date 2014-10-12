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

import com.github.haixing_hu.lang.Equality;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

/**
 * This class is used to represent a generic pair.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class Pair<T1, T2> implements Serializable {
  private static final long serialVersionUID = -1250168249356486261L;

  public T1 first;
  public T2 second;

  public Pair() {
    first = null;
    second = null;
  }

  public Pair(final T1 t1, final T2 t2) {
    first = t1;
    second = t2;
  }

  @Override
  public int hashCode() {
    final int multiplier = 1111;
    int code = 123;
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
    if (obj.getClass() != Pair.class) {
      return false;
    }
    final Pair<?,?> other = (Pair<?,?>) obj;
    return Equality.equals(first, other.first)
        && Equality.equals(second, other.second);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("first", first)
               .append("second", second)
               .toString();
  }
}
