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

package com.github.haixing_hu.util.filter.string;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.text.Glob;

/**
 * The base for GlobWhiteListStringFilter and GlobBlackListStringFilter classes.
 *
 * @author Haixing Hu
 */
class GlobStringFilter extends AbstractStringFilter {

  private static final String ERROR_INVALID_PATTERN = "Invalid glob pattern '{}': {}";

  protected List<Matcher> matcherList;
  protected boolean       matchReturn;
  protected boolean       caseInsensitive;

  protected GlobStringFilter(final boolean matchReturn, final boolean caseInsensitive) {
    this.matcherList = null;
    this.matchReturn = matchReturn;
    this.caseInsensitive = caseInsensitive;
  }

  public boolean isCaseInsensitive() {
    return caseInsensitive;
  }

  public int size() {
    return (matcherList == null ? 0 : matcherList.size());
  }

  public boolean isEmpty() {
    return ((matcherList == null) || matcherList.isEmpty());
  }

  public void clear() {
    if (matcherList != null) {
      matcherList.clear();
    }
  }

  public void setGlobList(final List<String> globList) {
    if (matcherList != null) {
      matcherList.clear();
    }
    if ((globList != null) && (globList.size() > 0)) {
      if (matcherList == null) {
        matcherList = new LinkedList<Matcher>();
      }
      final int flags = (caseInsensitive ? Pattern.CASE_INSENSITIVE : 0);
      for (final String glob : globList) {
        try {
          final String regex = Glob.toRegex(glob);
          final Pattern pattern = Pattern.compile(regex, flags);
          final Matcher matcher = pattern.matcher(StringUtils.EMPTY);
          matcherList.add(matcher);
        } catch (final PatternSyntaxException e) {
          final Logger logger = LoggerFactory.getLogger(GlobStringFilter.class);
          logger.error(ERROR_INVALID_PATTERN, glob, e.toString());
        }
      }
    }
  }

  public boolean addToGlobList(final String glob) {
    if (matcherList == null) {
      matcherList = new LinkedList<Matcher>();
    }
    final int flags = (caseInsensitive ? Pattern.CASE_INSENSITIVE : 0);
    try {
      final String regex = Glob.toRegex(glob);
      final Pattern pattern = Pattern.compile(regex, flags);
      final Matcher matcher = pattern.matcher(StringUtils.EMPTY);
      matcherList.add(matcher);
      return true;
    } catch (final PatternSyntaxException e) {
      final Logger logger = LoggerFactory.getLogger(GlobStringFilter.class);
      logger.error(ERROR_INVALID_PATTERN, glob, e.toString());
      return false;
    }
  }

  @Override
  public boolean accept(final String str) {
    if (matcherList != null) {
      for (final Matcher matcher : matcherList) {
        if (matcher.reset(str).matches()) {
          return matchReturn;
        }
      }
    }
    return (! matchReturn);
  }
}
