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

package com.github.haixing_hu.util.pool;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.github.haixing_hu.lang.Equality;

import static com.github.haixing_hu.lang.Argument.requireNonNull;
/**
 * Holds method names, parameters, and return values for tracing method calls.
 *
 * @author Haixing Hu
 */
public class MethodCall {
  private final String name;
  private final List<Object> params;
  private Object returned;

  public MethodCall(final String name) {
    this(name, Collections.emptyList());
  }

  public MethodCall(final String name, final Object param) {
    this(name, Collections.singletonList(param));
  }

  public MethodCall(final String name, final Object ... params) {
    this(name, Arrays.asList(params));
  }

  public MethodCall(final String name, @Nullable final List<Object> params) {
    this.name = requireNonNull("name", name);
    if (params != null) {
      this.params = params;
    } else {
      this.params = Collections.emptyList();
    }
  }

  public String getName() {
    return name;
  }

  public List<Object> getParams() {
    return params;
  }

  public Object getReturned() {
    return returned;
  }

  public void setReturned(final Object returned) {
    this.returned = returned;
  }

  public MethodCall returned(final Object obj) {
    setReturned(obj);
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }

    final MethodCall that = (MethodCall) o;
    return Equality.equals(name, that.name)
          && Equality.equals(params, that.params)
          && Equality.equals(returned, that.returned);
  }

  @Override
  public int hashCode() {
    int result;
    result = (name != null ? name.hashCode() : 0);
    result = 29 * result + (params != null ? params.hashCode() : 0);
    result = 29 * result + (returned != null ? returned.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("MethodCall");
    sb.append("{name='").append(name).append('\'');
    if (! params.isEmpty()) {
      sb.append(", params=").append(params);
    }
    if (returned != null) {
      sb.append(", returned=").append(returned);
    }
    sb.append('}');
    return sb.toString();
  }
}
