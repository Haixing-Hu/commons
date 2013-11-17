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

package com.github.haixing_hu.net;

import java.io.Serializable;

import javax.annotation.concurrent.Immutable;

import com.github.haixing_hu.lang.Cloneable;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * A {@link Redirection} object stores the information about a URL redirection.
 *
 * @author Haixing Hu
 */
@Immutable
public final class Redirection implements Serializable, Cloneable<Redirection>,
    Comparable<Redirection> {

  private static final long serialVersionUID = 7029539141176579563L;

  public final Url  url;
  public final boolean temporary;
  public final long    refreshTime;

  public Redirection(final Url url, final boolean temporary,
      final long refreshTime) {
    this.url = requireNonNull("url", url);
    this.temporary = temporary;
    this.refreshTime = refreshTime;
  }

  @Override
  public int hashCode() {
    final int multiplier = 11;
    int code = 17;
    code = Hash.combine(code, multiplier, url);
    code = Hash.combine(code, multiplier, temporary);
    code = Hash.combine(code, multiplier, refreshTime);
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
    final Redirection other = (Redirection) obj;
    return url.equals(other.url)
        && (temporary == other.temporary)
        && (refreshTime == other.refreshTime);
  }

  @Override
  public int compareTo(final Redirection other) {
    return url.compareTo(other.url);
  }

  @Override
  public Redirection clone() {
    return new Redirection(url, temporary, refreshTime);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("url", url)
               .append("temporary", temporary)
               .append("refreshTime", refreshTime)
               .toString();
  }
}
