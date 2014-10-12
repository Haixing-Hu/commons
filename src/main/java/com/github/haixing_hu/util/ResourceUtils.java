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
package com.github.haixing_hu.util;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * Provides helping function for resource bundles.
 *
 * @author Haixing Hu
 */
public final class ResourceUtils {

  private static Map<String, ResourceBundle> bundleMap = new HashMap<String, ResourceBundle>();

  /**
   * Gets a resource bundle of a specified name.
   *
   * <p>
   * The name may have one of the following formats:
   * </p>
   *
   * {@code
   *  [bundleName].properties
   *  [bundleName]_[language].properties
   *  [bundleName]_[language]_[country].properties
   * }
   *
   * <p>
   * where [bundleName] is the name of the bundle, which can not contains the
   * '_' character; [language] is a two lowercase letter language code,
   * [country] is a two uppercase letter country code.
   * </p>
   *
   * @param bundleName
   * @return The resource bundle of the specified name.
   * @throws MissingResourceException
   *           If the resource bundle can not be found.
   */
  public static ResourceBundle getBundle(final String bundleName)
      throws MissingResourceException {
    requireNonNull("bundleName", bundleName);
    synchronized (bundleMap) {
      // try to load bundle from cache
      final ResourceBundle bundle = bundleMap.get(bundleName);
      if (bundle != null) {
        return bundle;
      }
    }
    final int firstUnderscore = bundleName.indexOf('_');
    final int secondUnderscore = bundleName.indexOf('_', firstUnderscore + 1);
    Locale locale;
    if (firstUnderscore != - 1) {
      if (secondUnderscore != - 1) {
        final String language = bundleName.substring(firstUnderscore + 1,
            secondUnderscore);
        final String country = bundleName.substring(secondUnderscore + 1);
        locale = new Locale(language, country);
      } else {
        // secondUnderscore == -1
        final String language = bundleName.substring(firstUnderscore + 1);
        locale = new Locale(language);
      }
    } else {
      locale = Locale.getDefault();
    }
    // initialize the bundle
    final ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
    // cache the bundle
    synchronized (bundleMap) {
      bundleMap.put(bundleName, bundle);
    }
    return bundle;
  }

  /**
   * Gets the string of a specified key from a specified resource bundle.
   *
   * @param bundleName
   *          The name of the resource bundle.
   * @param key
   *          The key of the message to be get.
   * @return The string of a specified key from a specified resource bundle.
   * @throws MissingResourceException
   *           If the resource bundle can not be found, or the message of the
   *           specified key can not be found.
   */
  public static String getString(final String bundleName, final String key)
      throws MissingResourceException {
    final ResourceBundle bundle = getBundle(bundleName);
    return bundle.getString(key);
  }

  /**
   * Gets the message of a specified key from a specified resource
   * bundle, formatted without arguments.
   *
   * @param bundleName
   *          The name of the resource bundle.
   * @param key
   *          The unique identifier of the message
   * @return the formatted message.
   * @throws MissingResourceException
   *           If the resource bundle can not be found, or the message of the
   *           specified key can not be found.
   */
  public static String getMessage(final String bundleName, final String key)
      throws MissingResourceException {
    return getMessage(bundleName, key, new Object[]{});
  }

  /**
   * Gets the message of a specified key from a specified resource
   * bundle, formatted using specified arguments.
   *
   * @param bundleName
   *          The name of the resource bundle.
   * @param key
   *          The unique identifier of the message
   * @param arguments
   *          The arguments used to format the message.
   * @return the formatted message.
   * @throws MissingResourceException
   *           If the resource bundle can not be found, or the message of the
   *           specified key can not be found.
   */
  public static String getMessage(final String bundleName, final String key,
      final Object ... arguments) throws MissingResourceException {
    final ResourceBundle bundle = getBundle(bundleName);
    final String msgFormatStr = bundle.getString(key);
    final MessageFormat msgFormat = new MessageFormat(msgFormatStr);
    return msgFormat.format(arguments);
  }
}
