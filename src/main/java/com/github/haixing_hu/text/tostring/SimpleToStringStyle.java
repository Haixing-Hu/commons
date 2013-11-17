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

package com.github.haixing_hu.text.tostring;

import javax.annotation.concurrent.Immutable;

import com.github.haixing_hu.lang.StringUtils;

/**
 * The simple <code>toString()</code> style.
 * <p>
 * Using the <code>Person</code> example from {@link ToStringBuilder}, the
 * output would look like this:
 * </p>
 *
 * <pre>
 * John Doe,33,false
 * </pre>
 *
 * @author Haixing Hu
 */
@Immutable
public final class SimpleToStringStyle extends ToStringStyle {

  private static final long serialVersionUID = - 542352958755202081L;

  public static final SimpleToStringStyle INSTANCE = new SimpleToStringStyle();

  public SimpleToStringStyle() {
    super();
    this.setUseClassName(false);
    this.setUseIdentityHashCode(false);
    this.setUseFieldNames(false);
    this.setContentStart(StringUtils.EMPTY);
    this.setContentEnd(StringUtils.EMPTY);
  }

  @Override
  public String toString() {
    return "SimpleToStringStyle";
  }
}
