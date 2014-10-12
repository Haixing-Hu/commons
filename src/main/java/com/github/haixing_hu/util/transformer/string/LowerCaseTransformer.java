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
package com.github.haixing_hu.util.transformer.string;

import java.util.Locale;

import com.github.haixing_hu.text.tostring.ToStringBuilder;

/**
 * A {@link LowerCaseTransformer} transform a string to its lowercase form.
 *
 * @author Haixing Hu
 */
 public final class LowerCaseTransformer extends AbstractStringTransformer {

  private Locale locale;

  public LowerCaseTransformer() {
    locale = null;
  }

  public LowerCaseTransformer(final Locale locale) {
    this.locale = locale;
  }

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(final Locale locale) {
    this.locale = locale;
  }

  @Override
  public String transform(final String str) {
    if (locale == null) {
      return str.toLowerCase();
    } else {
      return str.toLowerCase(locale);
    }
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("locale", locale)
               .toString();
  }
}
