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

import java.io.IOException;

import static com.github.haixing_hu.CommonsMessages.DIRECTORY_CANT_DELETE;

/**
 * Thrown to indicate that a directory can not be deleted.
 *
 * @author Haixing Hu
 */
public class DirectoryCanNotDeleteException extends IOException {

  private static final long serialVersionUID = 568544508014410954L;

  public DirectoryCanNotDeleteException() {
    super(DIRECTORY_CANT_DELETE);
  }

  public DirectoryCanNotDeleteException(final String path) {
    super(DIRECTORY_CANT_DELETE + "(" + path + ")");
  }
}
