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
package com.github.haixing_hu.util;

import java.util.concurrent.TimeUnit;

/**
 * Provides functions to format human readable quantities.
 *
 * @author Haixing Hu
 */
public final class HumanReadable {

  private static final long NANOSECOND_PER_MICROSECOND = 1000L;
  private static final long NANOSECOND_PER_MILLISECOND = 1000L * NANOSECOND_PER_MICROSECOND;
  private static final long NANOSECOND_PER_SECOND      = 1000L * NANOSECOND_PER_MILLISECOND;
  private static final long NANOSECOND_PER_MINUTE      = 60L * NANOSECOND_PER_SECOND;
  private static final long NANOSECOND_PER_HOUR        = 60L * NANOSECOND_PER_MINUTE;
  private static final long NANOSECOND_PER_DAY         = 24L * NANOSECOND_PER_HOUR;

  /**
   * Formats a time duration to a human readable string.
   *
   * @param timeSpan
   *          The value of the time span.
   * @param unit
   *          The unit of the time span.
   * @return a human readable string of the time duration value.
   */
  public static String formatDuration(final long duration, final TimeUnit unit) {
    if (duration == 0) {
      switch (unit) {
        case NANOSECONDS:
          return "0 nanosecond";
        case MICROSECONDS:
          return "0 microsecond";
        case MILLISECONDS:
          return "0 millisecond";
        case SECONDS:
          return "0 second";
        case MINUTES:
          return "0 minute";
        case HOURS:
          return "0 hour";
        case DAYS:
          return "0 day";
        default:
          return "Unknown time unit.";
      }
    }

    long nanoseconds = unit.toNanos(duration);
    final long days = nanoseconds / NANOSECOND_PER_DAY;
    nanoseconds %= NANOSECOND_PER_DAY;
    final long hours = nanoseconds / NANOSECOND_PER_HOUR;
    nanoseconds %= NANOSECOND_PER_HOUR;
    final long minutes = nanoseconds / NANOSECOND_PER_MINUTE;
    nanoseconds %= NANOSECOND_PER_MINUTE;
    final long seconds = nanoseconds / NANOSECOND_PER_SECOND;
    nanoseconds %= NANOSECOND_PER_SECOND;
    final long milliseconds = nanoseconds / NANOSECOND_PER_MILLISECOND;
    nanoseconds %= NANOSECOND_PER_MILLISECOND;
    final long microseconds = nanoseconds / NANOSECOND_PER_MICROSECOND;
    nanoseconds %= NANOSECOND_PER_MICROSECOND;

    final StringBuilder builder = new StringBuilder();
    boolean first = true;

    if ((days > 0) && (unit.ordinal() <= TimeUnit.DAYS.ordinal())) {
      if (! first) {
        builder.append(' ');
      } else {
        first = false;
      }
      builder.append(days)
             .append(' ')
             .append(days > 1 ? "days" : "day");
    }
    if ((hours > 0) && (unit.ordinal() <= TimeUnit.HOURS.ordinal())) {
      if (! first) {
        builder.append(' ');
      } else {
        first = false;
      }
      builder.append(hours)
             .append(' ')
             .append(hours > 1 ? "hours" : "hour");
    }
    if ((minutes > 0) && (unit.ordinal() <= TimeUnit.MINUTES.ordinal())) {
      if (! first) {
        builder.append(' ');
      } else {
        first = false;
      }
      builder.append(minutes)
             .append(' ')
             .append(minutes > 1 ? "minutes" : "minute");
    }
    if ((seconds > 0) && (unit.ordinal() <= TimeUnit.SECONDS.ordinal())) {
      if (! first) {
        builder.append(' ');
      } else {
        first = false;
      }
      builder.append(seconds)
             .append(' ')
             .append(seconds > 1 ? "seconds" : "second");
    }
    if ((milliseconds > 0)
        && (unit.ordinal() <= TimeUnit.MILLISECONDS.ordinal())) {
      if (! first) {
        builder.append(' ');
      } else {
        first = false;
      }
      builder.append(milliseconds)
             .append(' ')
             .append(milliseconds > 1 ? "milliseconds" : "millisecond");
    }
    if ((microseconds > 0)
        && (unit.ordinal() <= TimeUnit.MICROSECONDS.ordinal())) {
      if (! first) {
        builder.append(' ');
      } else {
        first = false;
      }
      builder.append(microseconds)
             .append(' ')
             .append(microseconds > 1 ? "microseconds" : "microsecond");
    }
    if ((nanoseconds > 0)
        && (unit.ordinal() <= TimeUnit.NANOSECONDS.ordinal())) {
      if (! first) {
        builder.append(' ');
      } else {
        first = false;
      }
      builder.append(nanoseconds)
             .append(' ')
             .append(nanoseconds > 1 ? "nanoseconds" : "nanosecond");
    }
    return builder.toString();
  }


  private static final long BYTES_COUNT[] = {
    1L, //  B
    1024L,  //  KB
    1024L * 1024L,  //  MB
    1024L * 1024L * 1024L,  //  GB
    1024L * 1024L * 1024L * 1024L,  //  TB
    1024L * 1024L * 1024L * 1024L * 1024L,  //  PB
    1024L * 1024L * 1024L * 1024L * 1024L * 1024L,  //  EB
  };

  //  http://en.wikipedia.org/wiki/SI_prefix
  private static final String BYTES_UNIT_SHORT_NAMES[] = {
    "B",
    "KB",
    "MB",
    "GB",
    "TB",
    "PB",
    "EB",
  };

  //  http://en.wikipedia.org/wiki/SI_prefix
  private static final String BYTES_UNIT_LONG_NAMES[] = {
    "byte",
    "kilobyte",
    "megabyte",
    "gigabyte",
    "terabyte",
    "petabyte",
    "exabyte",
  };

  /**
   * Formats a number of bytes to a human readable string, using the short name
   * of bytes units.
   *
   * @param bytes
   *          the number of bytes.
   * @return a human readable string of the number of bytes.
   */
  public static String formatBytesShort(final long bytes) {
    int i = BYTES_COUNT.length - 1;
    for ( ; i >= 0; --i) {
      if (bytes >= BYTES_COUNT[i]) {
        break;
      }
    }
    if (i < 0) {
      return "0 " + BYTES_UNIT_SHORT_NAMES[0];
    }
    if (bytes == BYTES_COUNT[i]) {
      return "1 " + BYTES_UNIT_SHORT_NAMES[i];
    } else {
      final double value = (double) bytes / (double) BYTES_COUNT[i];
      return String.format("%.3f %s", value, BYTES_UNIT_SHORT_NAMES[i]);
    }
  }

  /**
   * Formats a number of bytes to a human readable string, using the long name
   * of bytes units.
   *
   * @param bytes
   *          the number of bytes.
   * @param useShortName
   *          true to use the shot name of the unit, false to use the long name.
   * @return a human readable string of the number of bytes.
   */
  public static String formatBytesLong(final long bytes) {
    int i = BYTES_COUNT.length - 1;
    for ( ; i >= 0; --i) {
      if (bytes >= BYTES_COUNT[i]) {
        break;
      }
    }
    if (i < 0) {
      return "0 " + BYTES_UNIT_LONG_NAMES[0];
    }
    if (bytes == BYTES_COUNT[i]) {
      return "1 " + BYTES_UNIT_SHORT_NAMES[i];
    } else {
      final double value = (double) bytes / (double) BYTES_COUNT[i];
      return String.format("%.3f %ss", value, BYTES_UNIT_LONG_NAMES[i]);
    }
  }
}
