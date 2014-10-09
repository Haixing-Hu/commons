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

import javax.annotation.concurrent.Immutable;

import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.text.tostring.ToStringBuilder;


/**
 * A top-level domain (TLD) is the last part of an Internet domain name; that
 * is, the letters which follow the final dot of any domain name. For example,
 * in the domain name {@code www.website.com}, the top-level domain is
 * {@code com}.
 *
 * @author Haixing Hu
 * @see http://www.iana.org/
 * @see http://en.wikipedia.org/wiki/Top-level_domain
 */
@Immutable
public final class TopLevelDomain extends DomainSuffix {

  private static final long serialVersionUID = - 3880755857023154643L;

  public enum Type {
    INFRASTRUCTURE,
    GENERIC,
    COUNTRY
  };

  Type type;
  String country;

  TopLevelDomain() {
    super();
    type = Type.GENERIC;
    country = StringUtils.EMPTY;
  }

  TopLevelDomain(final String domain, final Status status, final String description, final Type type, final String country){
    super(domain, status, description);
    this.type = type;
    this.country = country;
  }

  public Type getType() {
    return type;
  }

  @Override
  public boolean isTopLevel() {
    return true;
  }

  /**
   * Returns the country name if this TLD is Country Code TLD.
   *
   * @return country name or null
   */
  public String getCountry(){
    return country;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("domain", domain)
               .append("status", status)
               .append("description", description)
               .append("type", type)
               .append("country", country)
               .toString();
  }
}
