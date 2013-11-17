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

package com.github.haixing_hu.lang;

/**
 * Thrown to indicate that an argument was <code>null</code> and should not have
 * been. This exception supplements the standard
 * <code>IllegalArgumentException</code> by providing a more semantically rich
 * description of the problem.
 *
 * <code>NullArgumentException</code> represents the case where a method takes
 * in a parameter that must not be <code>null</code>. Some coding standards
 * would use <code>NullPointerException</code> for this case, others will use
 * <code>IllegalArgumentException</code>. Thus this exception would be used in
 * place of <code>IllegalArgumentException</code>, yet it still extends it.
 *
 * <pre>
 * public void foo(String str) {
 *   if (str == null) {
 *     throw new NullArgumentException(&quot;str&quot;);
 *   }
 *   // do something with the string
 * }
 * </pre>
 *
 * @author Haixing Hu
 */
public class NullArgumentException extends IllegalArgumentException {

  private static final long serialVersionUID = -5537609091567361359L;


  public NullArgumentException() {
    super("The argument must not be null nor empty.");
  }

  /**
   * Instantiates with the given argument name.
   *
   * @param argName
   *          the name of the argument that was <code>null</code>.
   */
  public NullArgumentException(final String argName) {
    super("The "
        + ((argName == null) || (argName.length() == 0) ? "argument" : argName)
        + " must not be null nor empty.");
  }

}
