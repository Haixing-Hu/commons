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
package com.github.haixing_hu.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.haixing_hu.lang.InitializationError;



/**
 * Provides utilities about the logging operations.
 *
 * @author Haixing Hu
 */
public final class LogUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(LogUtils.class);

  private static Method TRACE = null;
  private static Method DEBUG = null;
  private static Method INFO  = null;
  private static Method WARN  = null;
  private static Method ERROR = null;

  static {
    try {
      TRACE = Logger.class.getMethod("trace", String.class);
      DEBUG = Logger.class.getMethod("debug", String.class);
      INFO  = Logger.class.getMethod("info",  String.class);
      WARN  = Logger.class.getMethod("warn",  String.class);
      ERROR = Logger.class.getMethod("error", String.class);
    } catch(final Throwable e) {
      LOGGER.error("Cannot init the log methods.", e);
      throw new InitializationError(e);
    }
  }


  public static PrintStream getTraceStream(final Logger logger) {
    return getLogStream(logger, TRACE);
  }

  public static PrintStream getDebugStream(final Logger logger) {
    return getLogStream(logger, DEBUG);
  }

  public static PrintStream getInfoStream(final Logger logger) {
    return getLogStream(logger, INFO);
  }

  public static PrintStream getWarnStream(final Logger logger) {
    return getLogStream(logger, WARN);
  }

  public static PrintStream getErrorStream(final Logger logger) {
    return getLogStream(logger, ERROR);
  }

  /**
   * Returns a stream that, when written to, adds log lines.
   */
  private static PrintStream getLogStream(final Logger logger, final Method method) {
    return new PrintStream(new ByteArrayOutputStream() {
      private int scan = 0;

      private boolean hasNewline() {
        for (; scan < count; ++scan) {
          if (buf[scan] == '\n') {
            return true;
          }
        }
        return false;
      }

      @Override
      public void flush() throws IOException {
        if (! hasNewline()) {
          return;
        }
        try {
          method.invoke(logger, this.toString());
        } catch (final Exception e) {
          LOGGER.error("Cannot log with method [{}].", method, e);
        }
        reset();
        scan = 0;
      }
    }, true);
  }


}
