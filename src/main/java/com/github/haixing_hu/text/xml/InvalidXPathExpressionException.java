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
package com.github.haixing_hu.text.xml;

/**
 * Thrown to indicate an X-Path expression has an invalid syntax.
 *
 * @author Haixing Hu
 */
public class InvalidXPathExpressionException extends XmlException {

  private static final long serialVersionUID = 2865294933434160448L;

  private final String expression;

  public InvalidXPathExpressionException(final String expression) {
    super("The syntax of the x-path expression is invalid: " + expression);
    this.expression = expression;
  }

  public InvalidXPathExpressionException(final String expression, final Throwable e) {
    super("The syntax of the x-path expression is invalid: " + expression, e);
    this.expression = expression;
  }

  public String getExpression() {
    return expression;
  }
}
