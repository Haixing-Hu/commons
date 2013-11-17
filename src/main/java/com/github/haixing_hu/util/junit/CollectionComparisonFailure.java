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

package com.github.haixing_hu.util.junit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

/**
 * Thrown when two collection elements differ
 *
 * @see Assert#assertCollectionEquals(String, Object, Object)
 */
public class CollectionComparisonFailure extends AssertionError {

  private static final long serialVersionUID = 1L;

  private final List<Integer> indices = new ArrayList<Integer>();
  private final String messgae;
  private final AssertionError cause;

  /**
   * Construct a new <code>CollectionComparisonFailure</code> with an error text
   * and the collection's dimension that was not equal
   *
   * @param cause
   *          the exception that caused the collection's content to fail the
   *          assertion test
   * @param current
   *          the collection position of the objects that are not equal.
   * @see Assert#assertCollectionEquals(String, Object, Object)
   */
  public CollectionComparisonFailure(final String message,
      final AssertionError cause, final int index) {
    this.messgae = message;
    this.cause = cause;
    addDimension(index);
  }

  public void addDimension(final int index) {
    indices.add(0, index);
  }

  @Override
  public String getMessage() {
    final StringBuilder builder = new StringBuilder();
    if (messgae != null) {
      builder.append(messgae);
    }
    builder.append("collections first differed at element ");
    for (final int each : indices) {
      builder.append('[')
             .append(each)
             .append(']');
    }
    builder.append("; ")
           .append(cause.getMessage());
    return builder.toString();
  }

  @Override
  public String toString() {
    return getMessage();
  }
}
