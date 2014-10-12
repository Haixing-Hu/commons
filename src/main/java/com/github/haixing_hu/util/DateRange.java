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

import javax.annotation.Nullable;

import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.text.tostring.ToStringBuilder;


/**
 * A DateRange object is used to represents a range of date.
 *
 * @author Haixing Hu
 */
public class DateRange {

  public enum Type {
    ABSOLUTE,
    RELATIVE,
  }

  private Type type;
  private long start;
  private long end;
  private TimeUnit unit;

  public DateRange() {
    type = Type.ABSOLUTE;
    start = -1;
    end = -1;
    unit = TimeUnit.MILLISECONDS;
  }

  public Type getType() {
    return type;
  }

  public void setType(final Type type) {
    this.type = type;
  }

  public long getStart() {
    return start;
  }

  public void setStart(final long start) {
    this.start = start;
  }

  public long getEnd() {
    return end;
  }

  public void setEnd(final long end) {
    this.end = end;
  }

  public TimeUnit getUnit() {
    return unit;
  }

  public void setUnit(final TimeUnit unit) {
    this.unit = unit;
  }

  @Override
  public int hashCode() {
    final int multiplier = 33311;
    int code = 11;
    code = Hash.combine(code, multiplier, type);
    code = Hash.combine(code, multiplier, start);
    code = Hash.combine(code, multiplier, end);
    code = Hash.combine(code, multiplier, unit);
    return code;
  }

  @Override
  public boolean equals(@Nullable final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final DateRange other = (DateRange) obj;
    return (type == other.type)
        && (start == other.start)
        && (end == other.end)
        && (unit == other.unit);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("type", type)
               .append("start", start)
               .append("end", end)
               .append("unit", unit)
               .toString();
  }


}
