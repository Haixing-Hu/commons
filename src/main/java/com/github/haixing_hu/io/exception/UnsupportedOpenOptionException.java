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

import com.github.haixing_hu.io.OpenOption;

import static com.github.haixing_hu.CommonsMessages.UNSUPPORTED_OPEN_OPTION;

/**
 * Thrown to indicate an open option is not supported by a function.
 *
 * @author Haixing Hu
 */
public class UnsupportedOpenOptionException extends IOException {

  private static final long serialVersionUID = 2438085249149373956L;

  private final OpenOption option;

  public UnsupportedOpenOptionException(final OpenOption option) {
    super(UNSUPPORTED_OPEN_OPTION);
    this.option = option;
  }

  public OpenOption option() {
    return option;
  }

}
