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

package com.github.haixing_hu.util.filter;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides utility function for filters.
 *
 * @author Haixing Hu
 */
public final class FilterUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(FilterUtils.class);

  public static <T> T filter(@Nullable final Filter<T> f,
      @Nullable final T value) {
    if (value == null) {
      return null;
    }
    if (f != null) {
      boolean accept;
      synchronized (f) {
        accept = f.accept(value);
      }
      if (! accept) {
        LOGGER.debug("Rejected by filter: {}", value);
        return null;
      }
    }
    return value;
  }

  public static <T> T filter(@Nullable final Filter<T> f1,
      @Nullable final Filter<T> f2, @Nullable final T value) {
    if (value == null) {
      return null;
    }
    if (f1 != null) {
      boolean accept;
      synchronized (f1) {
        accept = f1.accept(value);
      }
      if (! accept) {
        LOGGER.debug("Rejected by filter: {}", value);
        return null;
      }
    }
    if (f2 != null) {
      boolean accept;
      synchronized (f2) {
        accept = f2.accept(value);
      }
      if (! accept) {
        LOGGER.debug("Rejected by filter: {}", value);
        return null;
      }
    }
    return value;
  }

  public static <T> T filter(@Nullable final Filter<T> f1,
      @Nullable final Filter<T> f2, @Nullable final Filter<T> f3,
      @Nullable final T value) {
    if (value == null) {
      return null;
    }
    if (f1 != null) {
      boolean accept;
      synchronized (f1) {
        accept = f1.accept(value);
      }
      if (! accept) {
        LOGGER.debug("Rejected by filter: {}", value);
        return null;
      }
    }
    if (f2 != null) {
      boolean accept;
      synchronized (f2) {
        accept = f2.accept(value);
      }
      if (! accept) {
        LOGGER.debug("Rejected by filter: {}", value);
        return null;
      }
    }
    if (f3 != null) {
      boolean accept;
      synchronized (f3) {
        accept = f3.accept(value);
      }
      if (! accept) {
        LOGGER.debug("Rejected by filter: {}", value);
        return null;
      }
    }
    return value;
  }
}
