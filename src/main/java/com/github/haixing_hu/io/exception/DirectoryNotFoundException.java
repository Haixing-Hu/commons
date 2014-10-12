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
package com.github.haixing_hu.io.exception;

import com.github.haixing_hu.CommonsMessages;

/**
 * This exception is thrown when you try to list a non-existent directory.
 *
 * @author Haixing Hu
 */
public class DirectoryNotFoundException extends java.io.FileNotFoundException {

  private static final long serialVersionUID = - 8781993099750916914L;

  public DirectoryNotFoundException() {
    super(CommonsMessages.DIRECTORY_NOT_FOUND);
  }

  public DirectoryNotFoundException(final String dirpath) {
    super(CommonsMessages.DIRECTORY_NOT_FOUND + " (" + dirpath + ")");
  }
}
