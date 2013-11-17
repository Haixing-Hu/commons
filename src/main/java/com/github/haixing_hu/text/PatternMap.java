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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.annotation.Nullable;

import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * The {@link PatternMap} maps a {@link String} to an object according to
 * predefined patterns.
 *
 * @author Haixing Hu
 */
public class PatternMap<VALUE> {

  private final class RegexEntry {
    Matcher  matcher;
    VALUE    value;
  }

  private final Map<String, VALUE> caseSensitiveStringMap;
  private final Map<String, VALUE> caseInsensitiveStringMap;
  private final TrieMap<VALUE>   caseSensitivePrefixMap;
  private final TrieMap<VALUE>   caseInsensitivePrefixMap;
  private final TrieMap<VALUE>   caseSensitiveSuffixMap;
  private final TrieMap<VALUE>   caseInsensitiveSuffixMap;
  private final List<RegexEntry> regexEntries;
  private final Map<Pattern, VALUE> patternMap;
  private int size;

  public PatternMap() {
    caseSensitiveStringMap = new HashMap<String, VALUE>();
    caseInsensitiveStringMap = new HashMap<String, VALUE>();
    caseSensitivePrefixMap = new TrieMap<VALUE>(false);
    caseInsensitivePrefixMap = new TrieMap<VALUE>(true);
    caseSensitiveSuffixMap = new TrieMap<VALUE>(false);
    caseInsensitiveSuffixMap = new TrieMap<VALUE>(true);
    regexEntries = new LinkedList<RegexEntry>();
    patternMap = new HashMap<Pattern, VALUE>();
    size = 0;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return (size == 0);
  }

  public void clear() {
    caseSensitiveStringMap.clear();
    caseInsensitiveStringMap.clear();
    caseSensitivePrefixMap.clear();
    caseInsensitivePrefixMap.clear();
    caseSensitiveSuffixMap.clear();
    caseInsensitiveSuffixMap.clear();
    regexEntries.clear();
    patternMap.clear();
    size = 0;
  }

  public void put(final Pattern pattern, final VALUE value) {
    requireNonNull("value", value);
    final PatternType type = pattern.getType();
    final boolean caseInsensitive = pattern.isIgnoreCase();
    final String expression = pattern.getExpression();
    switch (type) {
      case LITERAL:
        if (caseInsensitive) {
          caseInsensitiveStringMap.put(expression.toLowerCase(), value);
        } else {
          caseSensitiveStringMap.put(expression, value);
        }
        break;
      case PREFIX:
        if (caseInsensitive) {
          caseInsensitivePrefixMap.put(expression, value);
        } else {
          caseSensitivePrefixMap.put(expression, value);
        }
        break;
      case SUFFIX: {
        final String reversedExp = StringUtils.reverse(expression);
        if (caseInsensitive) {
          caseInsensitiveSuffixMap.put(reversedExp, value);
        } else {
          caseSensitiveSuffixMap.put(reversedExp, value);
        }
        break;
      }
      case REGEX: {
        int flags = 0;
        if (caseInsensitive) {
          flags = java.util.regex.Pattern.CASE_INSENSITIVE;
        }
        final RegexEntry entry = new RegexEntry();
        entry.matcher = java.util.regex.Pattern.compile(expression, flags)
                                               .matcher(StringUtils.EMPTY);
        entry.value = value;
        regexEntries.add(entry);
        break;
      }
      case GLOB: {
        int flags = 0;
        if (caseInsensitive) {
          flags = java.util.regex.Pattern.CASE_INSENSITIVE;
        }
        final String regex = Glob.toRegex(expression);
        final RegexEntry entry = new RegexEntry();
        entry.matcher = java.util.regex.Pattern.compile(regex, flags)
                                               .matcher(StringUtils.EMPTY);
        entry.value = value;
        regexEntries.add(entry);
        break;
      }
      default:
        throw new IllegalArgumentException("Unsupported pattern type: " + type);
    }
    patternMap.put(pattern, value);
    ++size;
  }

  public VALUE get(@Nullable final String str) {
    if (str == null) {
      return null;
    }
    // check the string patterns
    VALUE result = caseSensitiveStringMap.get(str);
    if (result != null) {
      return result;
    }
    result = caseInsensitiveStringMap.get(str.toLowerCase());
    if (result != null) {
      return result;
    }
    // check the prefix patterns
    result = caseSensitivePrefixMap.getPrefixOf(str);
    if (result != null) {
      return result;
    }
    result = caseInsensitivePrefixMap.getPrefixOf(str);
    if (result != null) {
      return result;
    }
    // check the suffix patterns
    final String reversedStr = StringUtils.reverse(str);
    result = caseSensitiveSuffixMap.getPrefixOf(reversedStr);
    if (result != null) {
      return result;
    }
    result = caseInsensitiveSuffixMap.getPrefixOf(reversedStr);
    if (result != null) {
      return result;
    }
    // now check the regex and glob patterns
    for (final RegexEntry entry : regexEntries) {
      if (entry.matcher.reset(str).matches()) {
        return entry.value;
      }
    }
    return null;
  }

  public Map<Pattern, VALUE> getPatternMap() {
    return patternMap;
  }

  @Override
  public int hashCode() {
    return patternMap.hashCode();
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
    final PatternMap<?> other = (PatternMap<?>) obj;
    return patternMap.equals(other.patternMap);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("patternMap", patternMap)
               .toString();
  }

}
