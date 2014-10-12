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
package com.github.haixing_hu.text.tostring;

import javax.annotation.concurrent.Immutable;

import com.github.haixing_hu.lang.SystemUtils;

/**
 * The multi-line {@code toString()} style.
 * <p>
 * Using the {@code Person} example from {@link ToStringBuilder}, the
 * output would look like this:
 * </p>
 *
 * <pre>
 * Person@182f0db[
 *   name=John Doe
 *   age=33
 *   smoker=false
 * ]
 * </pre>
 *
 * @author Haixing Hu
 */
@Immutable
public final class MultiLineToStringStyle extends ToStringStyle {

  private static final long serialVersionUID = 4592558914484643637L;

  public static final MultiLineToStringStyle INSTANCE = new MultiLineToStringStyle();

  public MultiLineToStringStyle() {
    super();
    this.setContentStart("[");
    this.setFieldSeparator(SystemUtils.LINE_SEPARATOR + "  ");
    this.setFieldSeparatorAtStart(true);
    this.setContentEnd(SystemUtils.LINE_SEPARATOR + "]");
  }

  @Override
  public String toString() {
    return "MultiLineToStringStyle";
  }
}
