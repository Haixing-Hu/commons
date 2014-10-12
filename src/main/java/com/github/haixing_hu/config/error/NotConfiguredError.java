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
package com.github.haixing_hu.config.error;

import com.github.haixing_hu.config.Configurable;

/**
 * Thrown to indicate that an {@link Configurable} is not configured.
 *
 * @author Haixing Hu
 */
public class NotConfiguredError extends ConfigurationError {

  private static final long serialVersionUID = - 6212497530366587501L;

  private static final String DEFAULT_MESSAGE = "The configurable object is not configured. ";

  public NotConfiguredError() {
    super(DEFAULT_MESSAGE);
  }

  public NotConfiguredError(final Throwable t) {
    super(DEFAULT_MESSAGE + t.toString(), t);
  }

  public NotConfiguredError(final String msg) {
    super(msg);
  }

  public NotConfiguredError(final String msg, final Throwable t) {
    super(msg, t);
  }
}
