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

package com.github.haixing_hu.util;

import java.util.Locale;

import javax.annotation.Nullable;

import com.github.haixing_hu.lang.StringUtils;

/**
 * Provide utilities functions deal with locale.
 *
 * @author Haixing Hu
 */
public class LocaleUtils {

  /**
   * Converts a POSIX locale ID into the corresponding Java {@link Locale}
   * object.
   *
   * The POSIX locale ID is a string of the following format:
   *
   * <pre>
   *  locale_id ::= language ("_" country)? ("_" variant)?
   * </pre>
   *
   * where
   *
   * <ul>
   * <li><code>language</code> is a 2-letter ISO-639 codes, in lower case.</li>
   * <li><code>country</code> is a 2-letter ISO-3166 codes, in upper case.</li>
   * <li><code>variant</code> specify particular variants of the locale,
   * typically with special options. </li>
   * </ul>
   *
   * @param localeId
   *          a POSIX locale ID.
   * @return the corresponding Java {@link Locale} object; returns null if the
   *         POSIX locale ID is invalid or the locale is not supported.
   */
  public static Locale fromPosixLocale(@Nullable final String localeId) {
    if ((localeId == null) || (localeId.length() == 0)) {
      return null;
    }
    String language = StringUtils.EMPTY;
    String country = StringUtils.EMPTY;
    String variant = StringUtils.EMPTY;
    final int n = localeId.length();
    int index = localeId.indexOf('_');
    if (index < 0) {
      // the whole locale ID is the language code
      if (n != 2) {   // invalid length of the language code
        return null;
      } else {
        return new Locale(localeId);
      }
    } else if (index != 2) {
      // invalid length of the language code
      return null;
    }
    // extract 2-letter language code
    language = localeId.substring(0, 2);
    // locate the next '_'
    final int start = index + 1;
    index = localeId.indexOf('_', start);
    if (index < 0) {
      // now the localeId[start, n) is either the 2-letter country code, or a variant.
      if ((start + 2) == n) {
        // it's a 2-letter country code
        country = localeId.substring(start, n);
      } else {
        // it's a variant
        variant = localeId.substring(start, n);
      }
    } else {
      // now the localeId[start, current) must be the 2-letter country code, and
      // localeId[current + 1, n) must be the variant
      if ((start + 2) != index) {
        // invalid length  of the country code
        return null;
      }
      country = localeId.substring(start, index);
      variant = localeId.substring(index + 1, n);
    }
    return new Locale(language, country, variant);
  }

  /**
   * Convert a Java {@link Locale} object into the corresponding POSIX locale
   * ID.
   *
   * The POSIX locale ID is a string of the following format:
   *
   * <pre>
   *  locale_id      ::= "C" | base_locale_id options?
   *  base_locale_id ::= language ("_" script)? ("_" territory)? ("_" variant)? ("." encoding)?
   *  options        ::= "@" key "=" value ("," key "=" value )*
   * </pre>
   *
   * where
   *
   * <ul>
   * <li>the string "C" is used to represent a unspecified locale id. It acts as
   * the current default locale of the current process.</li>
   * <li><code>language</code> is a 2-letter ISO-639 codes, in lower case</li>
   * <li><code>script</code> is a 4-letter ISO-15924 codes, in title case.
   * <li><code>script</code> is a 2-letter ISO-3166 codes, in upper case. Also
   * known as a country code, although the territories may not be countries.</li>
   * <li><code>variant</code> specify particular variants of the locale,
   * typically with special options. They cannot overlap with script or
   * territory codes, so they must have either one letter or have more than 4
   * letters.</li>
   * <li><code>encoding</code> is the text encoding for ANSI applications.</li>
   * <li><code>key</code>, <code>value</code> pair is the user predefined
   * configuration items.</li>
   * </ul>
   *
   * Since the Java {@link Locale} object only support the language, country,
   * and variant fields, the other fields will be ignored during conversion.
   *
   * @param locale
   *          a Java {@link Locale} object. If it is null, a null string
   *          is returned.
   * @return the corresponding POSIX locale ID; or null if the locale is null.
   */
  public static String toPosixLocale(@Nullable final Locale locale) {
    if (locale == null) {
      return null;
    }
    String language = locale.getLanguage();
    if ((language == null) || (language.length() == 0)) {
      language = locale.getISO3Language();
    }
    String country = locale.getCountry();
    if ((country == null) || (country.length() == 0)) {
      country = locale.getISO3Country();
    }
    final String variant = locale.getVariant();
    final StringBuilder builder = new StringBuilder();
    // build the POSIX local ID
    if ((language != null) && (language.length() > 0)) {
      builder.append(language);
    }
    if ((country != null) && (country.length() > 0)) {
      if (builder.length() > 0) {
        builder.append('_');
      }
      builder.append(country);
    }
    if ((variant != null) && (variant.length() > 0)) {
      if (builder.length() > 0) {
        builder.append('_');
      }
      builder.append(variant);
    }
    return builder.toString();
  }

}
