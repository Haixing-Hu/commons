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
package com.github.haixing_hu.util.junit;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

import org.junit.internal.ArrayComparisonFailure;

import com.github.haixing_hu.lang.ArrayUtils;

/**
 * Provides some assertion functions that current version of JUnit does not
 * provide.
 *
 * @author Haixing Hu
 */
public class Assert {

  /**
   * Asserts that two boolean arrays are equal. If they are not, an
   * {@link AssertionError} is thrown with the given message.
   *
   * @param message
   *          the identifying message for the {@link AssertionError} (
   *          {@code null} okay)
   * @param expecteds
   *          boolean array with expected values.
   * @param actuals
   *          boolean array with actual values
   */
  public static void assertArrayEquals(final String message,
      final boolean[] expecteds, final boolean[] actuals)
      throws ArrayComparisonFailure {
    if (expecteds == actuals) {
      return;
    }
    final String header = (message == null ? "" : message + ": ");
    assertBothNonNull(header, expecteds, actuals);
    assertArrayAreSameLength(header, expecteds.length, actuals.length);

    for (int i = 0; i < expecteds.length; ++i) {
      final boolean expected = expecteds[i];
      final boolean actual = actuals[i];
      try {
        org.junit.Assert.assertEquals(expected, actual);
      } catch (final AssertionError e) {
        throw new CollectionComparisonFailure(header, e, i);
      }
    }
  }

  /**
   * Asserts that two boolean arrays are equal. If they are not, an
   * {@link AssertionError} is thrown.
   *
   * @param expecteds
   *          boolean array with expected values.
   * @param actuals
   *          boolean array with actual values
   */
  public static void assertArrayEquals(final boolean[] expecteds,
      final boolean[] actuals) {
    assertArrayEquals(null, expecteds, actuals);
  }

  /**
   * Asserts that two byte arrays are equal. If they are not, an
   * {@link AssertionError} is thrown with the given message.
   *
   * @param message
   *          the identifying message for the {@link AssertionError} (
   *          {@code null} okay)
   * @param expecteds
   *          byte array with expected values.
   * @param actuals
   *          byte array with actual values
   */
  public static void assertArrayEquals(final String message,
      final byte[] expecteds, final byte[] actuals)
      throws ArrayComparisonFailure {
    if (expecteds == actuals) {
      return;
    }
    final String header = (message == null ? "" : message + ": ");
    assertBothNonNull(header, expecteds, actuals);
    assertArrayAreSameLength(header, expecteds.length, actuals.length);

    for (int i = 0; i < expecteds.length; ++i) {
      final byte expected = expecteds[i];
      final byte actual = actuals[i];
      try {
        org.junit.Assert.assertEquals(expected, actual);
      } catch (final AssertionError e) {
        throw new CollectionComparisonFailure(header, e, i);
      }
    }
  }

  /**
   * Asserts that two byte arrays are equal. If they are not, an
   * {@link AssertionError} is thrown.
   *
   * @param expecteds
   *          byte array with expected values.
   * @param actuals
   *          byte array with actual values
   */
  public static void assertArrayEquals(final byte[] expecteds,
      final byte[] actuals) {
    assertArrayEquals(null, expecteds, actuals);
  }

  /**
   * Asserts that two char arrays are equal. If they are not, an
   * {@link AssertionError} is thrown with the given message.
   *
   * @param message
   *          the identifying message for the {@link AssertionError} (
   *          {@code null} okay)
   * @param expecteds
   *          char array with expected values.
   * @param actuals
   *          char array with actual values
   */
  public static void assertArrayEquals(final String message,
      final char[] expecteds, final char[] actuals)
      throws ArrayComparisonFailure {
    if (expecteds == actuals) {
      return;
    }
    final String header = (message == null ? "" : message + ": ");
    assertBothNonNull(header, expecteds, actuals);
    assertArrayAreSameLength(header, expecteds.length, actuals.length);
    for (int i = 0; i < expecteds.length; ++i) {
      final char expected = expecteds[i];
      final char actual = actuals[i];
      try {
        org.junit.Assert.assertEquals(expected, actual);
      } catch (final AssertionError e) {
        throw new CollectionComparisonFailure(header, e, i);
      }
    }
  }

  /**
   * Asserts that two char arrays are equal. If they are not, an
   * {@link AssertionError} is thrown.
   *
   * @param expecteds
   *          char array with expected values.
   * @param actuals
   *          char array with actual values
   */
  public static void assertArrayEquals(final char[] expecteds,
      final char[] actuals) {
    assertArrayEquals(null, expecteds, actuals);
  }

  /**
   * Asserts that two short arrays are equal. If they are not, an
   * {@link AssertionError} is thrown with the given message.
   *
   * @param message
   *          the identifying message for the {@link AssertionError} (
   *          {@code null} okay)
   * @param expecteds
   *          short array with expected values.
   * @param actuals
   *          short array with actual values
   */
  public static void assertArrayEquals(final String message,
      final short[] expecteds, final short[] actuals)
      throws ArrayComparisonFailure {
    if (expecteds == actuals) {
      return;
    }
    final String header = (message == null ? "" : message + ": ");
    assertBothNonNull(header, expecteds, actuals);
    assertArrayAreSameLength(header, expecteds.length, actuals.length);

    for (int i = 0; i < expecteds.length; ++i) {
      final short expected = expecteds[i];
      final short actual = actuals[i];
      try {
        org.junit.Assert.assertEquals(expected, actual);
      } catch (final AssertionError e) {
        throw new CollectionComparisonFailure(header, e, i);
      }
    }
  }

  /**
   * Asserts that two short arrays are equal. If they are not, an
   * {@link AssertionError} is thrown.
   *
   * @param expecteds
   *          short array with expected values.
   * @param actuals
   *          short array with actual values
   */
  public static void assertArrayEquals(final short[] expecteds,
      final short[] actuals) {
    assertArrayEquals(null, expecteds, actuals);
  }

  /**
   * Asserts that two int arrays are equal. If they are not, an
   * {@link AssertionError} is thrown with the given message.
   *
   * @param message
   *          the identifying message for the {@link AssertionError} (
   *          {@code null} okay)
   * @param expecteds
   *          int array with expected values.
   * @param actuals
   *          int array with actual values
   */
  public static void assertArrayEquals(final String message,
      final int[] expecteds, final int[] actuals) throws ArrayComparisonFailure {
    if (expecteds == actuals) {
      return;
    }
    final String header = (message == null ? "" : message + ": ");
    assertBothNonNull(header, expecteds, actuals);
    assertArrayAreSameLength(header, expecteds.length, actuals.length);

    for (int i = 0; i < expecteds.length; ++i) {
      final int expected = expecteds[i];
      final int actual = actuals[i];
      try {
        org.junit.Assert.assertEquals(expected, actual);
      } catch (final AssertionError e) {
        throw new CollectionComparisonFailure(header, e, i);
      }
    }
  }

  /**
   * Asserts that two int arrays are equal. If they are not, an
   * {@link AssertionError} is thrown.
   *
   * @param expecteds
   *          int array with expected values.
   * @param actuals
   *          int array with actual values
   */
  public static void assertArrayEquals(final int[] expecteds,
      final int[] actuals) {
    assertArrayEquals(null, expecteds, actuals);
  }

  /**
   * Asserts that two long arrays are equal. If they are not, an
   * {@link AssertionError} is thrown with the given message.
   *
   * @param message
   *          the identifying message for the {@link AssertionError} (
   *          {@code null} okay)
   * @param expecteds
   *          long array with expected values.
   * @param actuals
   *          long array with actual values
   */
  public static void assertArrayEquals(final String message,
      final long[] expecteds, final long[] actuals)
      throws ArrayComparisonFailure {
    if (expecteds == actuals) {
      return;
    }
    final String header = (message == null ? "" : message + ": ");
    assertBothNonNull(header, expecteds, actuals);
    assertArrayAreSameLength(header, expecteds.length, actuals.length);

    for (int i = 0; i < expecteds.length; ++i) {
      final long expected = expecteds[i];
      final long actual = actuals[i];
      try {
        org.junit.Assert.assertEquals(expected, actual);
      } catch (final AssertionError e) {
        throw new CollectionComparisonFailure(header, e, i);
      }
    }
  }

  /**
   * Asserts that two long arrays are equal. If they are not, an
   * {@link AssertionError} is thrown.
   *
   * @param expecteds
   *          long array with expected values.
   * @param actuals
   *          long array with actual values
   */
  public static void assertArrayEquals(final long[] expecteds,
      final long[] actuals) {
    assertArrayEquals(null, expecteds, actuals);
  }

  /**
   * Asserts that two double arrays are equal. If they are not, an
   * {@link AssertionError} is thrown with the given message.
   *
   * @param message
   *          the identifying message for the {@link AssertionError} (
   *          {@code null} okay)
   * @param expecteds
   *          double array with expected values.
   * @param actuals
   *          double array with actual values
   */
  public static void assertArrayEquals(final String message,
      final double[] expecteds, final double[] actuals, final double delta)
      throws ArrayComparisonFailure {
    if (expecteds == actuals) {
      return;
    }
    final String header = (message == null ? "" : message + ": ");
    assertBothNonNull(header, expecteds, actuals);
    assertArrayAreSameLength(header, expecteds.length, actuals.length);

    for (int i = 0; i < expecteds.length; ++i) {
      final double expected = expecteds[i];
      final double actual = actuals[i];
      try {
        org.junit.Assert.assertEquals(expected, actual, delta);
      } catch (final AssertionError e) {
        throw new CollectionComparisonFailure(header, e, i);
      }
    }
  }

  /**
   * Asserts that two double arrays are equal. If they are not, an
   * {@link AssertionError} is thrown.
   *
   * @param expecteds
   *          double array with expected values.
   * @param actuals
   *          double array with actual values
   */
  public static void assertArrayEquals(final double[] expecteds,
      final double[] actuals, final double delta) {
    assertArrayEquals(null, expecteds, actuals, delta);
  }

  /**
   * Asserts that two float arrays are equal. If they are not, an
   * {@link AssertionError} is thrown with the given message.
   *
   * @param message
   *          the identifying message for the {@link AssertionError} (
   *          {@code null} okay)
   * @param expecteds
   *          float array with expected values.
   * @param actuals
   *          float array with actual values
   */
  public static void assertArrayEquals(final String message,
      final float[] expecteds, final float[] actuals, final float delta)
      throws ArrayComparisonFailure {
    if (expecteds == actuals) {
      return;
    }
    final String header = (message == null ? "" : message + ": ");
    assertBothNonNull(header, expecteds, actuals);
    assertArrayAreSameLength(header, expecteds.length, actuals.length);

    for (int i = 0; i < expecteds.length; ++i) {
      final float expected = expecteds[i];
      final float actual = actuals[i];
      try {
        org.junit.Assert.assertEquals(expected, actual, delta);
      } catch (final AssertionError e) {
        throw new CollectionComparisonFailure(header, e, i);
      }
    }
  }

  /**
   * Asserts that two float arrays are equal. If they are not, an
   * {@link AssertionError} is thrown.
   *
   * @param expecteds
   *          float array with expected values.
   * @param actuals
   *          float array with actual values
   */
  public static void assertArrayEquals(final float[] expecteds,
      final float[] actuals, final float delta) {
    assertArrayEquals(null, expecteds, actuals, delta);
  }

  /**
   * Asserts that two generic type arrays are equal. If they are not, an
   * {@link AssertionError} is thrown with the given message. If
   * {@code expecteds} and {@code actuals} are {@code null}, they
   * are considered equal.
   *
   * @param message
   *          the identifying message for the {@link AssertionError} (
   *          {@code null} okay)
   * @param expecteds
   *          Generic type array with expected values.
   * @param actuals
   *          Generic type array with actual values
   */
  public static <T> void assertArrayEquals(final String message,
      final T[] expecteds, final T[] actuals) throws ArrayComparisonFailure {
    internalAssertArrayEquals(message, expecteds, actuals);
  }

  /**
   * Asserts that two Class arrays are equal. If they are not, an
   * {@link AssertionError} is thrown. If {@code expected} and
   * {@code actual} are {@code null}, they are considered equal.
   *
   * @param expecteds
   *          Class array with expected values
   * @param actuals
   *          Class array with actual values
   */
  public static <T> void assertArrayEquals(final T[] expecteds,
      final T[] actuals) {
    assertArrayEquals(null, expecteds, actuals);
  }

  private static void internalAssertArrayEquals(final String message,
      final Object expecteds, final Object actuals)
      throws ArrayComparisonFailure {
    if (expecteds == actuals) {
      return;
    }
    final String header = message == null ? "" : message + ": ";
    assertBothNonNull(header, expecteds, actuals);
    final int expectedsLength = Array.getLength(expecteds);
    final int actualsLength = Array.getLength(actuals);
    assertArrayAreSameLength(header, expectedsLength, actualsLength);

    for (int i = 0; i < expectedsLength; i++) {
      final Object expected = Array.get(expecteds, i);
      final Object actual = Array.get(actuals, i);
      if (ArrayUtils.isArray(expected) && ArrayUtils.isArray(actual)) {
        try {
          internalAssertArrayEquals(message, expected, actual);
        } catch (final ArrayComparisonFailure e) {
          e.addDimension(i);
          throw e;
        }
      } else {
        try {
          org.junit.Assert.assertEquals(expected, actual);
        } catch (final AssertionError e) {
          throw new ArrayComparisonFailure(header, e, i);
        }
      }
    }
  }

  public static void assertCollectionEquals(final Collection<?> expecteds,
      final Collection<?> actuals) {
    assertCollectionEquals(null, expecteds, actuals);
  }

  public static void assertCollectionEquals(final String message,
      final Collection<?> expecteds, final Collection<?> actuals) {
    if (expecteds == actuals) {
      return;
    }
    final String header= (message == null ? "" : message + ": ");
    assertBothNonNull(header, expecteds, actuals);
    assertCollectionAreSameSize(header, expecteds.size(), actuals.size());
    final Iterator<?> expectedIter = expecteds.iterator();
    final Iterator<?> actualIter = actuals.iterator();
    int index = 0;
    while (expectedIter.hasNext()) {
      final Object expected = expectedIter.next();
      final Object actual = actualIter.next();
      if (ArrayUtils.isArray(expected) && ArrayUtils.isArray(actual)) {
        try {
          internalAssertArrayEquals(message, expected, actual);
        } catch (final ArrayComparisonFailure e) {
          throw new CollectionComparisonFailure(header, e, index);
        }
      } else {
        try {
          org.junit.Assert.assertEquals(expected, actual);
        } catch (final AssertionError e) {
          throw new CollectionComparisonFailure(header, e, index);
        }
      }
      ++index;
    }
  }

  private static void assertBothNonNull(final String header,
      final Object expecteds, final Object actuals) {
    if (expecteds == null) {
      org.junit.Assert.fail(header + "expected was null");
    } else if (actuals == null) {
      org.junit.Assert.fail(header + "actual was null");
    }
  }

  private static void assertCollectionAreSameSize(final String header,
      final int expectedSize, final int actualSize) {
    if (expectedSize != actualSize) {
      org.junit.Assert.fail(header
          + "collection sizes differed, expected.size() =" + expectedSize
          + " actual.size() =" + actualSize);
    }
  }

  private static void assertArrayAreSameLength(final String header,
      final int expectedLength, final int actualLength) {
    if (expectedLength != actualLength) {
      org.junit.Assert.fail(header
          + "array lengths differed, expected.length =" + expectedLength
          + " actual.length =" + actualLength);
    }
  }
}
