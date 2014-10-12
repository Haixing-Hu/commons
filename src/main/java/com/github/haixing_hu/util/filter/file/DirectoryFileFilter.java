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
package com.github.haixing_hu.util.filter.file;

import java.io.File;

import javax.annotation.concurrent.Immutable;

/**
 * This filter accepts {@link File} objects which denoting the abstract path of
 * a directory.
 *
 * For example, here is how to print out a list of the current directory's
 * subdirectories:
 *
 * <pre>
 * File dir = new File(&quot;.&quot;);
 * String[] files = dir.list(DirectoryFileFilter.INSTANCE);
 * for (int i = 0; i &lt; files.length; i++) {
 *   System.out.println(files[i]);
 * }
 * </pre>
 *
 * @author Haixing Hu
 */
@Immutable
public final class DirectoryFileFilter extends AbstractFileFilter {

  /**
   * The singleton instance of DirectoryFileFilter
   */
  public static final DirectoryFileFilter INSTANCE = new DirectoryFileFilter();

  private DirectoryFileFilter() {
    // empty
  }

  @Override
  public boolean accept(final File file) {
    return file.isDirectory();
  }

  @Override
  public boolean accept(final File dir, final String name) {
    final File file = new File(dir, name);
    return file.isDirectory();
  }

}
