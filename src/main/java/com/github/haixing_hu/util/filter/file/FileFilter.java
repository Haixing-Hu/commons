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
 */package com.github.haixing_hu.util.filter.file;

import java.io.File;

import com.github.haixing_hu.util.filter.Filter;

/**
 * An implementation of {@link FileFilter} interface is a filter used to filter
 * the abstract file pathname.
 *
 * Note that it also extends the {@link java.io.FilenameFilter} in the standard
 * JDK.
 *
 * @author Haixing Hu
 */
public interface FileFilter extends Filter<File>, java.io.FileFilter,
    java.io.FilenameFilter {

  /**
   * Tests whether or not the specified abstract pathname should be included in
   * a pathname list.
   *
   * @param pathname
   *          The abstract pathname to be tested
   * @return {@code true} if and only if {@code pathname} should be
   *         included
   */
  @Override
  public boolean accept(File file);

  /**
   * Tests if a specified file should be included in a file list.
   *
   * @param dir
   *          the directory in which the file was found.
   * @param name
   *          the name of the file.
   * @return {@code true} if and only if the name should be included in the
   *         file list; {@code false} otherwise.
   */
  @Override
  public boolean accept(File dir, String name);

}
