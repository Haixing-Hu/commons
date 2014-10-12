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
package com.github.haixing_hu.reflect;

import com.github.haixing_hu.lang.StringUtils;

/**
 * Thrown to indicate the specified method does not exist.
 *
 * @author Haixing Hu
 */
public class MethodNotExistException extends ReflectionException {

  private static final long serialVersionUID = 7457691421536998975L;

  public MethodNotExistException(Class<?> cls, int options, String name,
      Class<?>[] paramTypes) {
    super("There is no "
        + Option.toString(options)
        + " method named with '"
        + name
        + "' for the class "
        + cls.getName()
        + " with the parameter types ["
        + StringUtils.join(',', paramTypes)
        + "].");
  }
}
