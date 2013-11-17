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

package com.github.haixing_hu.io.exception;

import com.github.haixing_hu.CommonsMessages;

/**
 * Thrown to indicate that a file is not found.
 *
 * This class extends the <code>java.io.FileNotFoundException</code>, except
 * that it could be constructed with a optional filename.
 *
 * @author Haixing Hu
 */
public class FileNotFoundException extends java.io.FileNotFoundException {

  private static final long serialVersionUID = -4901933132912456682L;

  public FileNotFoundException() {
    super(CommonsMessages.FILE_NOT_FOUND);
  }

  public FileNotFoundException(final String filepath) {
    super(CommonsMessages.FILE_NOT_FOUND + " (" + filepath + ")");
  }
}
