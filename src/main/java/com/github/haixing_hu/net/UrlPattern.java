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
package com.github.haixing_hu.net;

import java.io.Serializable;

import javax.annotation.Nullable;

import com.github.haixing_hu.io.serialize.BinarySerialization;
import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.lang.Cloneable;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.text.Pattern;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * A {@link UrlPattern} represents a rule of pattern used to matches URLs.
 *
 * @author Haixing Hu
 */
public class UrlPattern implements Serializable, Cloneable<UrlPattern> {

  private static final long serialVersionUID = 9135054642012865500L;

  public static final UrlPart     DEFAULT_URL_PART       = UrlPart.URL;

  static {
    BinarySerialization.register(UrlPattern.class, UrlPatternBinarySerializer.INSTANCE);
    XmlSerialization.register(UrlPattern.class, UrlPatternXmlSerializer.INSTANCE);
  }

  protected UrlPart     urlPart;
  protected Pattern     pattern;

  public UrlPattern() {
    urlPart = DEFAULT_URL_PART;
    pattern = new Pattern();
  }

  public UrlPattern(final Pattern pattern) {
    this.urlPart = DEFAULT_URL_PART;
    this.pattern = requireNonNull("pattern", pattern);
  }

  public UrlPattern(final UrlPart urlPart, final Pattern pattern) {
    this.urlPart = requireNonNull("urlPart", urlPart);
    this.pattern = requireNonNull("pattern", pattern);
  }

  public UrlPart getUrlPart() {
    return urlPart;
  }

  public void setUrlPart(final UrlPart urlPart) {
    this.urlPart = requireNonNull("urlPart", urlPart);
  }

  public Pattern getPattern() {
    return pattern;
  }

  public void setPattern(final Pattern pattern) {
    this.pattern = requireNonNull("pattern", pattern);
  }

  public boolean matches(@Nullable final Url url) {
    if (url == null) {
      return false;
    }
    final String str = url.get(urlPart);
    return pattern.matches(str);
  }

  @Override
  public UrlPattern clone() {
    return new UrlPattern(urlPart, pattern.clone());
  }

  @Override
  public int hashCode() {
    final int multiplier = 77771;
    int code = 771;
    code = Hash.combine(code, multiplier, urlPart);
    code = Hash.combine(code, multiplier, pattern);
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
    final UrlPattern other = (UrlPattern) obj;
    return (urlPart == other.urlPart)
        && pattern.equals(other.pattern);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("urlPart", urlPart)
               .append("pattern", pattern)
               .toString();
  }

}
