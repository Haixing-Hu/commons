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

import java.lang.reflect.Method;

import com.github.haixing_hu.lang.StringUtils;

/**
 * Thrown to indicate the invoking of a method failed.
 *
 * @author Haixing Hu
 */
public class InvokingMethodFailedException extends ReflectionException {

  private static final long serialVersionUID = 7457691421536998975L;

  public InvokingMethodFailedException(Class<?> cls, int options, String name,
      Class<?>[] paramTypes, Throwable cause) {
    super("Invoking the "
        + Option.toString(options)
        + " method named with '"
        + name
        + "' for the class "
        + cls.getName()
        + " with the parameter types ["
        + StringUtils.join(',', paramTypes)
        + "] failed.", cause);
  }

  public InvokingMethodFailedException(Object object, Method method,
     Object[] arguments, Throwable cause) {
    super("Invoking the method '"
        + method.getName()
        + "' on the object "
        + object.toString()
        + " with the arguments ["
        + StringUtils.join(',', arguments)
        + "] failed.", cause);
  }
}
