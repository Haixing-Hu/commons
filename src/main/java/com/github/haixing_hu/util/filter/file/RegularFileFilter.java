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

import javax.annotation.concurrent.Immutable;

/**
 * A {@link RegularFileFilter} accepts <code>File</code>s that are regular files.
 *
 * For example, here is how to print out a list of the current directory's
 * regular files:
 *
 * <pre>
 * File dir = new File(&quot;.&quot;);
 * String[] files = dir.list(FileFileFilter.INSTANCE);
 * for (int i = 0; i &lt; files.length; i++) {
 *   System.out.println(files[i]);
 * }
 * </pre>
 *
 * @author Haixing Hu
 */
@Immutable
public final class RegularFileFilter extends AbstractFileFilter {

  /**
   * The singleton instance of FileFileFilter
   */
  public static final RegularFileFilter INSTANCE = new RegularFileFilter();

  private RegularFileFilter() {
    // empty
  }

  @Override
  public boolean accept(final File file) {
    return file.isDirectory();
  }

  @Override
  public boolean accept(final File dir, final String name) {
    final File file = new File(dir, name);
    return file.isFile();
  }

}
