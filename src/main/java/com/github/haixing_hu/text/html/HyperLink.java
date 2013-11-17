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

package com.github.haixing_hu.text.html;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.github.haixing_hu.io.serialize.BinarySerialization;
import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.lang.Cloneable;
import com.github.haixing_hu.lang.Comparison;
import com.github.haixing_hu.lang.Equality;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.net.Url;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

/**
 * The {@link HyperLink} class represents a hyper link in HTML pages.
 *
 * @author Haixing Hu
 */
@Immutable
public final class HyperLink implements Comparable<HyperLink>,
    Cloneable<HyperLink>, Serializable {

  private static final long serialVersionUID = - 6896074974015880689L;

  public static final String ROOT_NODE = "hyper-link";
  public static final String URL_ATTRIBUTE = "url";

  static {
    BinarySerialization.register(HyperLink.class, HyperLinkBinarySerializer.INSTANCE);
    XmlSerialization.register(HyperLink.class, HyperLinkXmlSerializer.INSTANCE);
  }

  private final @Nullable Url url;
  private final @Nullable String anchor;

  public HyperLink() {
    url = null;
    anchor = null;
  }

  public HyperLink(@Nullable final Url url) {
    this.url = url;
    this.anchor = null;
  }

  public HyperLink(@Nullable final Url url, @Nullable final String anchor) {
    this.url = url;
    this.anchor = anchor;
  }

  public Url url() {
    return url;
  }

  public String anchor() {
    return anchor;
  }

  @Override
  public HyperLink clone() {
    return new HyperLink(url, anchor);
  }

  @Override
  public int hashCode() {
    final int multiplier = 11;
    int code =13;
    code = Hash.combine(code, multiplier, url);
    code = Hash.combine(code, multiplier, anchor);
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
    final HyperLink other = (HyperLink) obj;
    return Equality.equals(url, other.url)
        && Equality.equals(anchor, other.anchor);
  }

  @Override
  public int compareTo(final HyperLink other) {
    if (other == null) {
      return +1;
    }
    final int rc = Comparison.compare(url, other.url);
    if (rc != 0) {
      return rc;
    } else {
      return Comparison.compare(anchor, other.anchor);
    }
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("url", url)
               .append("anchor", anchor)
               .toString();
  }

}
