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

package com.github.haixing_hu.util.transformer;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides utility function for transformers.
 *
 * @author Haixing Hu
 */
public final class TransformUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(TransformUtils.class);

  public static <T> T transform(@Nullable final Transformer<T> tr, @Nullable final T value) {
    if (value == null) {
      return null;
    }
    if (tr == null) {
      return value;
    }
    final T newValue;
    synchronized (tr) {
      newValue = tr.transform(value);
    }
    if (LOGGER.isDebugEnabled()) {
      if (! value.equals(newValue)) {
        LOGGER.debug("Transform: {} --> {}", value, newValue);
      }
    }
    return newValue;
  }

  public static <T> T transform(@Nullable final Transformer<T> tr1,
        @Nullable final Transformer<T> tr2, @Nullable T value) {
    if (value == null) {
      return null;
    }
    if (tr1 != null) {
      final T newValue;
      synchronized (tr1) {
        newValue = tr1.transform(value);
      }
      if (LOGGER.isDebugEnabled()) {
        if (! value.equals(newValue)) {
          LOGGER.debug("Transform: {} --> {}", value, newValue);
        }
      }
      value = newValue;
    }
    if (tr2 != null) {
      final T newValue;
      synchronized (tr2) {
        newValue = tr2.transform(value);
      }
      if (LOGGER.isDebugEnabled()) {
        if (! value.equals(newValue)) {
          LOGGER.debug("Transform: {} --> {}", value, newValue);
        }
      }
      value = newValue;
    }
    return value;
  }

  public static <T> T transform(@Nullable final Transformer<T> tr1,
        @Nullable final Transformer<T> tr2, @Nullable final Transformer<T> tr3,
        @Nullable T value) {
    if (value == null) {
      return null;
    }
    if (tr1 != null) {
      final T newValue;
      synchronized (tr1) {
        newValue = tr1.transform(value);
      }
      if (LOGGER.isDebugEnabled()) {
        if (! value.equals(newValue)) {
          LOGGER.debug("Transform: {} --> {}", value, newValue);
        }
      }
      value = newValue;
    }
    if (tr2 != null) {
      final T newValue;
      synchronized (tr2) {
        newValue = tr2.transform(value);
      }
      if (LOGGER.isDebugEnabled()) {
        if (! value.equals(newValue)) {
          LOGGER.debug("Transform: {} --> {}", value, newValue);
        }
      }
      value = newValue;
    }
    if (tr3 != null) {
      final T newValue;
      synchronized (tr3) {
        newValue = tr3.transform(value);
      }
      if (LOGGER.isDebugEnabled()) {
        if (! value.equals(newValue)) {
          LOGGER.debug("Transform: {} --> {}", value, newValue);
        }
      }
      value = newValue;
    }
    return value;
  }
}
