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

import com.github.haixing_hu.io.AccessMode;
import com.github.haixing_hu.io.OpenOption;

/**
 * Thrown to indicate an invalid access mode.
 *
 * @author Haixing Hu
 */
public class InvalidAccessModeException extends IOException {

  private static final long serialVersionUID = 5327741114744838035L;

  private final AccessMode accessMode;

  public InvalidAccessModeException(final AccessMode accessMode) {
    super("Invalid access mode for the operation: " + accessMode);
    this.accessMode = accessMode;
  }

  public InvalidAccessModeException(final AccessMode accessMode,
      final OpenOption openOption) {
    super("Invalid access mode for the open option "
        + openOption + ": " + accessMode);
    this.accessMode = accessMode;
  }

  public AccessMode getAccessMode() {
    return accessMode;
  }
}
