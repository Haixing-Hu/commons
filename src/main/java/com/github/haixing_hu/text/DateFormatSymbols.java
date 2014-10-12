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
package com.github.haixing_hu.text;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.concurrent.GuardedBy;

import com.github.haixing_hu.lang.Cloneable;
import com.github.haixing_hu.lang.Equality;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

import static com.github.haixing_hu.lang.Argument.*;

/**
 * The symbols used to format and parse dates.
 *
 * @author Haixing Hu
 */
public final class DateFormatSymbols implements Cloneable<DateFormatSymbols> {

  /**
   * Unlocalized date-time pattern characters. For example: 'y', 'd', etc.
   * All locales use the same these unlocalized pattern characters.
   */
  public static final String  PATTERN_CHARS = "GyMdkHmsSEDFwWahKzZ";

  @GuardedBy("DateFormatSymbols.class")
  private static final Map<Locale, java.text.DateFormatSymbols> symbolsCache =
    new HashMap<Locale, java.text.DateFormatSymbols>();

  private static synchronized java.text.DateFormatSymbols getJdkSymbols(final Locale locale) {
    requireNonNull("locale", locale);
    java.text.DateFormatSymbols symbols = symbolsCache.get(locale);
    if (symbols == null) {
      symbols = java.text.DateFormatSymbols.getInstance(locale);
      symbolsCache.put(locale, symbols);
    }
    return symbols;
  }

  /**
   * Era strings. For example: "AD" and "BC".  An array of 2 strings,
   * indexed by {@code Calendar.BC} and {@code Calendar.AD}.
   * @serial
   */
  private String eras[] = null;

  /**
   * Month strings. For example: "January", "February", etc.  An array
   * of 13 strings (some calendars have 13 months), indexed by
   * {@code Calendar.JANUARY}, {@code Calendar.FEBRUARY}, etc.
   * @serial
   */
  private String months[] = null;

  /**
   * Short month strings. For example: "Jan", "Feb", etc.  An array of
   * 13 strings (some calendars have 13 months), indexed by
   * {@code Calendar.JANUARY}, {@code Calendar.FEBRUARY}, etc.

   * @serial
   */
  private String shortMonths[] = null;

  /**
   * Weekday strings. For example: "Sunday", "Monday", etc.  An array
   * of 8 strings, indexed by {@code Calendar.SUNDAY},
   * {@code Calendar.MONDAY}, etc.
   * The element {@code weekdays[0]} is ignored.
   * @serial
   */
  private String weekdays[] = null;

  /**
   * Short weekday strings. For example: "Sun", "Mon", etc.  An array
   * of 8 strings, indexed by {@code Calendar.SUNDAY},
   * {@code Calendar.MONDAY}, etc.
   * The element {@code shortWeekdays[0]} is ignored.
   * @serial
   */
  private String shortWeekdays[] = null;

  /**
   * AM and PM strings. For example: "AM" and "PM".  An array of
   * 2 strings, indexed by {@code Calendar.AM} and
   * {@code Calendar.PM}.
   * @serial
   */
  private String ampms[] = null;

  /**
   * Localized names of time zones in this locale.  This is a
   * two-dimensional array of strings of size <em>n</em> by <em>m</em>,
   * where <em>m</em> is at least 5.  Each of the <em>n</em> rows is an
   * entry containing the localized names for a single {@code TimeZone}.
   * Each such row contains (with {@code i} ranging from
   * 0..<em>n</em>-1):
   * <ul>
   * <li>{@code zoneStrings[i][0]} - time zone ID</li>
   * <li>{@code zoneStrings[i][1]} - long name of zone in standard
   * time</li>
   * <li>{@code zoneStrings[i][2]} - short name of zone in
   * standard time</li>
   * <li>{@code zoneStrings[i][3]} - long name of zone in daylight
   * saving time</li>
   * <li>{@code zoneStrings[i][4]} - short name of zone in daylight
   * saving time</li>
   * </ul>
   * The zone ID is <em>not</em> localized; it's one of the valid IDs of
   * the {@link java.util.TimeZone TimeZone} class that are not
   * <a href="../java/util/TimeZone.html#CustomID">custom IDs</a>.
   * All other entries are localized names.
   * @see java.util.TimeZone
   * @serial
   */
  private String zoneStrings[][] = null;

  /**
   * Localized date-time pattern characters. For example, a locale may
   * wish to use 'u' rather than 'y' to represent years in its date format
   * pattern strings.
   * This string must be exactly 18 characters long, with the index of
   * the characters described by {@code DateFormat.ERA_FIELD},
   * {@code DateFormat.YEAR_FIELD}, etc.  Thus, if the string were
   * "Xz...", then localized patterns would use 'X' for era and 'z' for year.
   * @serial
   */
  private String  localizedPatternChars = null;


  public DateFormatSymbols() {
    reset();
  }

  public DateFormatSymbols(final Locale locale) {
    reset(locale);
  }

  public void reset() {
    reset(DateFormat.DEFAULT_LOCALE);
  }

  public void reset(final Locale locale) {
    final java.text.DateFormatSymbols symbols = getJdkSymbols(locale);
    this.eras = symbols.getEras();
    this.months = symbols.getMonths();
    this.shortMonths = symbols.getShortMonths();
    this.weekdays = symbols.getWeekdays();
    this.shortWeekdays = symbols.getShortWeekdays();
    this.ampms = symbols.getAmPmStrings();
    this.zoneStrings = symbols.getZoneStrings();
    this.localizedPatternChars = symbols.getLocalPatternChars();
  }

  public String[] getEras() {
    return eras;
  }

  public void setEras(final String[] eras) {
    this.eras = requireLengthBe("eras", eras, 2);
  }

  public String[] getMonths() {
    return months;
  }

  public void setMonths(final String[] months) {
    this.months = requireLengthBe("months", months, 13);
  }

  public String[] getShortMonths() {
    return shortMonths;
  }

  public void setShortMonths(final String[] shortMonths) {
    this.shortMonths = requireLengthBe("shortMonths", shortMonths, 13);
  }

  public String[] getWeekdays() {
    return weekdays;
  }

  public void setWeekdays(final String[] weekdays) {
    this.weekdays = requireLengthBe("weekdays", weekdays, 8);
  }

  public String[] getShortWeekdays() {
    return shortWeekdays;
  }

  public void setShortWeekdays(final String[] shortWeekdays) {
    this.shortWeekdays = requireLengthBe("shortWeekdays", shortWeekdays, 8);
  }

  public String[] getAmpms() {
    return ampms;
  }

  public void setAmpms(final String[] ampms) {
    this.ampms = requireLengthBe("ampms", ampms, 2);
  }

  public String[][] getZoneStrings() {
    return zoneStrings;
  }

  public void setZoneStrings(final String[][] zoneStrings) {
    this.zoneStrings = requireNonNull("zoneStrings", zoneStrings);
  }

  public String getLocalizedPatternChars() {
    return localizedPatternChars;
  }

  public void setLocalizedPatternChars(final String localizedPatternChars) {
    this.localizedPatternChars = requireNonNull("localizedPatternChars", localizedPatternChars);
  }

  public void assign(final DateFormatSymbols that) {
    if (this == that) {
      return;
    }
    this.eras = that.eras;
    this.months = that.months;
    this.shortMonths = that.shortMonths;
    this.weekdays = that.weekdays;
    this.shortWeekdays = that.shortWeekdays;
    this.ampms = that.ampms;
    this.zoneStrings = that.zoneStrings;
    this.localizedPatternChars = that.localizedPatternChars;
  }

  @Override
  public DateFormatSymbols clone() {
    final DateFormatSymbols result = new DateFormatSymbols();
    result.assign(this);
    return result;
  }

  @Override
  public int hashCode() {
    final int multiplier = 3;
    int code = 17;
    code = Hash.combine(code, multiplier, eras);
    code = Hash.combine(code, multiplier, months);
    code = Hash.combine(code, multiplier, shortMonths);
    code = Hash.combine(code, multiplier, weekdays);
    code = Hash.combine(code, multiplier, shortWeekdays);
    code = Hash.combine(code, multiplier, ampms);
    code = Hash.combine(code, multiplier, zoneStrings);
    code = Hash.combine(code, multiplier, localizedPatternChars);
    return code;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final DateFormatSymbols other = (DateFormatSymbols) obj;
    return Equality.equals(eras, other.eras)
        && Equality.equals(months, other.months)
        && Equality.equals(shortMonths, other.shortMonths)
        && Equality.equals(weekdays, other.weekdays)
        && Equality.equals(shortWeekdays, other.shortWeekdays)
        && Equality.equals(ampms, other.ampms)
        && Equality.equals(zoneStrings, other.zoneStrings)
        && Equality.equals(localizedPatternChars, other.localizedPatternChars);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("eras", eras)
               .append("months", months)
               .append("shortMonths", shortMonths)
               .append("weekdays", weekdays)
               .append("shortWeekdays", shortWeekdays)
               .append("ampms", ampms)
               .append("zoneStrings", zoneStrings)
               .append("localizedPatternChars", localizedPatternChars)
               .toString();
  }
}
