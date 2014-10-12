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

import javax.annotation.Nullable;

/**
 * Thrown when an XML node has invalid number of children.
 *
 * @author Haixing Hu
 */
public class InvalidXmlChildrenCountException extends XmlException {

  private static final long serialVersionUID = 1129619473649011560L;

  private final String parentName;
  private final String childName;
  private final int expectedMin;
  private final int expectedMax;      // < 0 means unlimited
  private int actual;

  public InvalidXmlChildrenCountException(final String parentName, @Nullable final String childName,
      final int expectedMin, final int expectedMax, final int actual) {
    super(formatMessage(parentName, childName, expectedMin, expectedMax, actual));
    this.parentName = parentName;
    this.childName = childName;
    this.expectedMin = expectedMin;
    this.expectedMax = expectedMax;
    this.actual = actual;
  }

  public InvalidXmlChildrenCountException(final String parentName, @Nullable final String childName,
      final int expectedMin, final int expectedMax, final int actual, final String message) {
    super(formatMessage(parentName, childName, expectedMin, expectedMax, actual) + message);
    this.parentName = parentName;
    this.childName = childName;
    this.expectedMin = expectedMin;
    this.expectedMax = expectedMax;
    this.actual = actual;
  }

  public String getParentName() {
    return parentName;
  }

  public String getChildName() {
    return childName;
  }

  public int getExpectedMin() {
    return expectedMin;
  }

  public int getExpectedMax() {
    return expectedMax;
  }

  public int getActual() {
    return actual;
  }

  public void setActual(final int actual) {
    this.actual = actual;
  }

  public static String formatMessage(final String parentName, final String childName,
      final int expectedMin, final int expectedMax, final int actual) {
    final StringBuilder builder = new StringBuilder();
    builder.append("Invalid number of children nodes ");
    if (childName != null) {
      builder.append('<')
             .append(childName)
             .append("> ");
    }
    builder.append("for the parent node <")
           .append(parentName)
           .append(">: the expected minimum count is ")
           .append(expectedMin)
           .append(" and the expected maximum count is ");
    if ((expectedMax < 0) || (expectedMax == Integer.MAX_VALUE)) {
      builder.append("<unlimited>");
    } else {
      builder.append(expectedMax);
    }
    builder.append(" while the actual count is ")
           .append(actual)
           .append(". ");
    return builder.toString();
  }
}
