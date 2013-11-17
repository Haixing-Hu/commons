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

package com.github.haixing_hu.text;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * The enumeration of pattern types.
 *
 * @author Haixing Hu
 */
public enum PatternType {
  /**
   * Indicate that the pattern represents a full string literal, therefore a string
   * matches the pattern if and only if the string equals the pattern.
   */
  LITERAL("literal"),

  /**
   * Indicate that the pattern represents a prefix, therefore a string matches
   * the pattern if and only if the string has a prefix equals the pattern.
   */
  PREFIX("prefix"),

  /**
   * Indicate that the pattern represents a suffix, therefore a string matches
   * the pattern if and only if the string has a suffix equals the pattern.
   */
  SUFFIX("suffix"),

  /**
   * Indicate that the pattern represents a regular expression pattern,
   * therefore a string matches the pattern if and only if the string matches
   * the regular expression represented by the pattern.
   */
  REGEX("regex"),

  /**
   * Indicate that the pattern represents a glob pattern, therefore a string
   * matches the pattern if and only if the string matches the glob represented
   * by the pattern.
   */
  GLOB("glob");

  private static final Map<String, PatternType> NAME_MAP;
  static {
    NAME_MAP = new HashMap<String, PatternType>(5);
    NAME_MAP.put(LITERAL.name, LITERAL);
    NAME_MAP.put(PREFIX.name, PREFIX);
    NAME_MAP.put(SUFFIX.name, SUFFIX);
    NAME_MAP.put(REGEX.name, REGEX);
    NAME_MAP.put(GLOB.name, GLOB);
  }

  public static PatternType forName(final String name) {
    return NAME_MAP.get(name);
  }

  private String name;

  private PatternType(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String toRegex(final String pattern) {
    switch (this) {
      case LITERAL:
        return Pattern.quote(pattern);
      case PREFIX:
        return "^" + Pattern.quote(pattern);
      case SUFFIX:
        return Pattern.quote(pattern) + "$";
      case REGEX:
        return pattern;
      case GLOB:
        return Glob.toRegex(pattern);
      default:
        throw new IllegalStateException("Unknown pattern type.");
    }
  }

  @Override
  public String toString() {
    return name;
  }
}
