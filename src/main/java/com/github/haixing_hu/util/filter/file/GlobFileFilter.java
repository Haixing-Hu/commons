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

package com.github.haixing_hu.util.filter.file;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.concurrent.NotThreadSafe;

import com.github.haixing_hu.text.Glob;


/**
 * A {@link GlobFileFilter} filters files using the supplied glob patterns.
 *
 * This filter selects files and directories based on one or more glob patterns.
 * Testing is case-sensitive by default, but this can be configured.
 *
 * The following character sequences have special meaning within a glob pattern:
 *
 * <ul>
 * <li>? matches any one character</li>
 * <li>* matches any number of characters</li>
 * <li>{!glob} Matches anything that does not match glob</li>
 * <li>{a,b,c} matches any one of a, b or c</li>
 * <li>[abc] matches any character in the set a, b or c</li>
 * <li>[^abc] matches any character not in the set a, b or c</li>
 * <li>[a-z] matches any character in the range a to z, inclusive. A leading or
 * trailing dash will be interpreted literally</li>
 * </ul>
 *
 * For example:
 *
 * <pre>
 * File dir = new File(&quot;.&quot;);
 * FileFilter fileFilter = new GlobFileFilter(&quot;*test*.java&tilde;*&tilde;&quot;);
 * File[] files = dir.listFiles(fileFilter);
 * for (int i = 0; i &lt; files.length; i++) {
 *   System.out.println(files[i]);
 * }
 * </pre>
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class GlobFileFilter implements FileFilter {

  public static boolean DEFAULT_CASE_SENSITIVE = false;

  List<Glob> globs;

  public GlobFileFilter() {
    globs = null;
  }

  private int getGlobFlags(final boolean caseSensitive) {
    int options = 0;
    if (! caseSensitive) {
      options = Pattern.CASE_INSENSITIVE;
    }
    return options;
  }

  public void addPattern(final boolean caseSensitive, final String pattern) {
    final int options = getGlobFlags(caseSensitive);
    if (globs == null) {
      globs = new LinkedList<Glob>();
    }
    final Glob glob = new Glob(pattern, options);
    globs.add(glob);
  }

  public void addPatterns(final boolean caseSensitive, final String ... patterns) {
    final int options = getGlobFlags(caseSensitive);
    if (globs == null) {
      globs = new LinkedList<Glob>();
    }
    for (final String pattern : patterns) {
      final Glob glob = new Glob(pattern, options);
      globs.add(glob);
    }
  }

  public void addPatterns(final boolean caseSensitive, final Iterable<String> patterns) {
    final int options = getGlobFlags(caseSensitive);
    if (globs == null) {
      globs = new LinkedList<Glob>();
    }
    for (final String pattern : patterns) {
      final Glob glob = new Glob(pattern, options);
      globs.add(glob);
    }
  }

  public boolean isEmpty() {
    return (globs == null) || globs.isEmpty();
  }

  public int size() {
    return (globs == null ? 0 : globs.size());
  }

  public void clear() {
    if (globs != null) {
      globs.clear();
    }
  }

  @Override
  public boolean accept(final File file) {
    if (globs != null) {
      final String name = file.getName();
      for (final Glob glob : globs) {
        if (glob.matches(name)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean accept(final File dir, final String name) {
    if (globs != null) {
      for (final Glob glob : globs) {
        if (glob.matches(name)) {
          return true;
        }
      }
    }
    return false;
  }

}
