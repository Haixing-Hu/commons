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

import java.util.Collection;

import com.github.haixing_hu.text.Unicode;

/**
 * Provides common argument checking functions.
 *
 * @author Haixing Hu
 */
public final class Argument {

  /**
   * Checks the current bounds.
   * <p>
   * Note that the checking is non-trivial, since we have to consider the
   * integer overflows.
   * </p>
   *
   * @param off
   *          the offset.
   * @param n
   *          the number of elements.
   * @param length
   *          the length of the sequence.
   * @throws IndexOutOfBoundsException
   *           If the current is out of bounds.
   */
  public static void checkBounds(final int off, final int n, final int length) {
    if ((off < 0) || (n < 0) || (off > (length - n))) {
      throw new IndexOutOfBoundsException("off: " + off + " n: " + n
          + " length: " + length);
    }
  }

  public static <T> T requireNonNull(final String argumentName, final T argument) {
    if (argument == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    return argument;
  }

  public static boolean[] requireNonEmpty(final String argumentName,
      final boolean[] arguments) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length == 0) {
      throw new IllegalArgumentException("The '" + argumentName
          + "' can not be empty.");
    }
    return arguments;
  }

  public static char[] requireNonEmpty(final String argumentName,
      final char[] arguments) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length == 0) {
      throw new IllegalArgumentException("The '" + argumentName
          + "' can not be empty.");
    }
    return arguments;
  }

  public static byte[] requireNonEmpty(final String argumentName,
      final byte[] arguments) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length == 0) {
      throw new IllegalArgumentException("The '" + argumentName
          + "' can not be empty.");
    }
    return arguments;
  }

  public static short[] requireNonEmpty(final String argumentName,
      final short[] arguments) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length == 0) {
      throw new IllegalArgumentException("The '" + argumentName
          + "' can not be empty.");
    }
    return arguments;
  }

  public static int[] requireNonEmpty(final String argumentName,
      final int[] arguments) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length == 0) {
      throw new IllegalArgumentException("The '" + argumentName
          + "' can not be empty.");
    }
    return arguments;
  }

  public static long[] requireNonEmpty(final String argumentName,
      final long[] arguments) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length == 0) {
      throw new IllegalArgumentException("The '" + argumentName
          + "' can not be empty.");
    }
    return arguments;
  }

  public static float[] requireNonEmpty(final String argumentName,
      final float[] arguments) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length == 0) {
      throw new IllegalArgumentException("The '" + argumentName
          + "' can not be empty.");
    }
    return arguments;
  }

  public static double[] requireNonEmpty(final String argumentName,
      final double[] arguments) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length == 0) {
      throw new IllegalArgumentException("The '" + argumentName
          + "' can not be empty.");
    }
    return arguments;
  }

  public static <T> T[] requireNonEmpty(final String argumentName,
      final T[] arguments) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length == 0) {
      throw new IllegalArgumentException("The '" + argumentName
          + "' can not be empty.");
    }
    return arguments;
  }

  public static String requireNonEmpty(final String argumentName,
      final String argument) {
    if (argument == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (argument.length() == 0) {
      throw new IllegalArgumentException("The '" + argumentName
          + "' can not be empty.");
    }
    return argument;
  }

  public static <T> Collection<T> requireNonEmpty(final String argumentName,
      final Collection<T> argument) {
    if (argument == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (argument.isEmpty()) {
      throw new IllegalArgumentException("The '" + argumentName
          + "' can not be empty.");
    }
    return argument;
  }

  public static boolean[] requireLengthBe(final String argumentName,
      final boolean[] arguments, final int length) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length != length) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be " + length + ", but it is " + arguments.length);
    }
    return arguments;
  }

  public static char[] requireLengthBe(final String argumentName,
      final char[] arguments, final int length) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length != length) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be " + length + ", but it is " + arguments.length);
    }
    return arguments;
  }

  public static byte[] requireLengthBe(final String argumentName,
      final byte[] arguments, final int length) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length != length) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be " + length + ", but it is " + arguments.length);
    }
    return arguments;
  }

  public static short[] requireLengthBe(final String argumentName,
      final short[] arguments, final int length) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length != length) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be " + length + ", but it is " + arguments.length);
    }
    return arguments;
  }

  public static int[] requireLengthBe(final String argumentName,
      final int[] arguments, final int length) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length != length) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be " + length + ", but it is " + arguments.length);
    }
    return arguments;
  }

  public static long[] requireLengthBe(final String argumentName,
      final long[] arguments, final int length) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length != length) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be " + length + ", but it is " + arguments.length);
    }
    return arguments;
  }

  public static float[] requireLengthBe(final String argumentName,
      final float[] arguments, final int length) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length != length) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be " + length + ", but it is " + arguments.length);
    }
    return arguments;
  }

  public static double[] requireLengthBe(final String argumentName,
      final double[] arguments, final int length) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length != length) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be " + length + ", but it is " + arguments.length);
    }
    return arguments;
  }

  public static <T> T[] requireLengthBe(final String argumentName,
      final T[] arguments, final int length) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length != length) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be " + length + ", but it is " + arguments.length);
    }
    return arguments;
  }

  public static String requireLengthBe(final String argumentName,
      final String argument, final int length) {
    if (argument == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (argument.length() != length) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be " + length + ", but it is " + argument.length());
    }
    return argument;
  }

  public static <T> Collection<T> requireSizeBe(final String argumentName,
      final Collection<T> argument, final int size) {
    if (argument == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (argument.size() != size) {
      throw new IllegalArgumentException("The size of '" + argumentName
          + "' must be " + size + ", but it is " + argument.size());
    }
    return argument;
  }

  public static boolean[] requireLengthAtLeast(final String argumentName,
      final boolean[] arguments, final int minLength) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length < minLength) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be at least " + minLength + ", but it is "
          + arguments.length);
    }
    return arguments;
  }

  public static char[] requireLengthAtLeast(final String argumentName,
      final char[] arguments, final int minLength) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length < minLength) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be at least " + minLength + ", but it is "
          + arguments.length);
    }
    return arguments;
  }

  public static byte[] requireLengthAtLeast(final String argumentName,
      final byte[] arguments, final int minLength) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length < minLength) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be at least " + minLength + ", but it is "
          + arguments.length);
    }
    return arguments;
  }

  public static short[] requireLengthAtLeast(final String argumentName,
      final short[] arguments, final int minLength) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length < minLength) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be at least " + minLength + ", but it is "
          + arguments.length);
    }
    return arguments;
  }

  public static int[] requireLengthAtLeast(final String argumentName,
      final int[] arguments, final int minLength) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length < minLength) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be at least " + minLength + ", but it is "
          + arguments.length);
    }
    return arguments;
  }

  public static long[] requireLengthAtLeast(final String argumentName,
      final long[] arguments, final int minLength) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length < minLength) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be at least " + minLength + ", but it is "
          + arguments.length);
    }
    return arguments;
  }

  public static float[] requireLengthAtLeast(final String argumentName,
      final float[] arguments, final int minLength) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length < minLength) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be at least " + minLength + ", but it is "
          + arguments.length);
    }
    return arguments;
  }

  public static double[] requireLengthAtLeast(final String argumentName,
      final double[] arguments, final int minLength) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length < minLength) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be at least " + minLength + ", but it is "
          + arguments.length);
    }
    return arguments;
  }

  public static <T> T[] requireLengthAtLeast(final String argumentName,
      final T[] arguments, final int minLength) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length < minLength) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be at least " + minLength + ", but it is "
          + arguments.length);
    }
    return arguments;
  }

  public static String requireLengthAtLeast(final String argumentName,
      final String argument, final int minLength) {
    if (argument == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (argument.length() < minLength) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be at least " + minLength + ", but it is "
          + argument.length());
    }
    return argument;
  }

  public static <T> Collection<T> requireSizeAtLeast(final String argumentName,
      final Collection<T> argument, final int minSize) {
    if (argument == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (argument.size() < minSize) {
      throw new IllegalArgumentException("The size of '" + argumentName
          + "' must be at least " + minSize + ", but it is " + argument.size());
    }
    return argument;
  }

  public static boolean[] requireLengthAtMost(final String argumentName,
      final boolean[] arguments, final int maxLength) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length > maxLength) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be at most " + maxLength + ", but it is "
          + arguments.length);
    }
    return arguments;
  }

  public static char[] requireLengthAtMost(final String argumentName,
      final char[] arguments, final int maxLength) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length > maxLength) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be at most " + maxLength + ", but it is "
          + arguments.length);
    }
    return arguments;
  }

  public static byte[] requireLengthAtMost(final String argumentName,
      final byte[] arguments, final int maxLength) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length > maxLength) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be at most " + maxLength + ", but it is "
          + arguments.length);
    }
    return arguments;
  }

  public static short[] requireLengthAtMost(final String argumentName,
      final short[] arguments, final int maxLength) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length > maxLength) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be at most " + maxLength + ", but it is "
          + arguments.length);
    }
    return arguments;
  }

  public static int[] requireLengthAtMost(final String argumentName,
      final int[] arguments, final int maxLength) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length > maxLength) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be at most " + maxLength + ", but it is "
          + arguments.length);
    }
    return arguments;
  }

  public static long[] requireLengthAtMost(final String argumentName,
      final long[] arguments, final int maxLength) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length > maxLength) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be at most " + maxLength + ", but it is "
          + arguments.length);
    }
    return arguments;
  }

  public static float[] requireLengthAtMost(final String argumentName,
      final float[] arguments, final int maxLength) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length > maxLength) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be at most " + maxLength + ", but it is "
          + arguments.length);
    }
    return arguments;
  }

  public static double[] requireLengthAtMost(final String argumentName,
      final double[] arguments, final int maxLength) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length > maxLength) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be at most " + maxLength + ", but it is "
          + arguments.length);
    }
    return arguments;
  }

  public static <T> T[] requireLengthAtMost(final String argumentName,
      final T[] arguments, final int maxLength) {
    if (arguments == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (arguments.length > maxLength) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be at most " + maxLength + ", but it is "
          + arguments.length);
    }
    return arguments;
  }

  public static String requireLengthAtMost(final String argumentName,
      final String argument, final int maxLength) {
    if (argument == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (argument.length() > maxLength) {
      throw new IllegalArgumentException("The length of '" + argumentName
          + "' must be at most " + maxLength + ", but it is "
          + argument.length());
    }
    return argument;
  }

  public static <T> Collection<T> requireSizeAtMost(final String argumentName,
      final Collection<T> argument, final int maxSize) {
    if (argument == null) {
      throw new NullPointerException("The '" + argumentName
          + "' can not be null.");
    }
    if (argument.size() > maxSize) {
      throw new IllegalArgumentException("The size of '" + argumentName
          + "' must be at most " + maxSize + ", but it is " + argument.size());
    }
    return argument;
  }

  public static byte requireZero(final String argName, final byte arg) {
    if (arg != 0) {
      throw new IllegalArgumentException(argName + " must be zero.");
    }
    return arg;
  }

  public static short requireZero(final String argName, final short arg) {
    if (arg != 0) {
      throw new IllegalArgumentException(argName + " must be zero.");
    }
    return arg;
  }

  public static int requireZero(final String argName, final int arg) {
    if (arg != 0) {
      throw new IllegalArgumentException(argName + " must be zero.");
    }
    return arg;
  }

  public static long requireZero(final String argName, final long arg) {
    if (arg != 0) {
      throw new IllegalArgumentException(argName + " must be zero.");
    }
    return arg;
  }

  public static float requireZero(final String argName, final float arg) {
    if (arg != 0) {
      throw new IllegalArgumentException(argName + " must be zero.");
    }
    return arg;
  }

  public static double requireZero(final String argName, final double arg) {
    if (arg != 0) {
      throw new IllegalArgumentException(argName + " must be zero.");
    }
    return arg;
  }

  public static byte requireNonZero(final String argName, final byte arg) {
    if (arg == 0) {
      throw new IllegalArgumentException(argName + " cannot be zero.");
    }
    return arg;
  }

  public static short requireNonZero(final String argName, final short arg) {
    if (arg == 0) {
      throw new IllegalArgumentException(argName + " cannot be zero.");
    }
    return arg;
  }

  public static int requireNonZero(final String argName, final int arg) {
    if (arg == 0) {
      throw new IllegalArgumentException(argName + " cannot be zero.");
    }
    return arg;
  }

  public static long requireNonZero(final String argName, final long arg) {
    if (arg == 0) {
      throw new IllegalArgumentException(argName + " cannot be zero.");
    }
    return arg;
  }

  public static float requireNonZero(final String argName, final float arg) {
    if (arg == 0) {
      throw new IllegalArgumentException(argName + " cannot be zero.");
    }
    return arg;
  }

  public static double requireNonZero(final String argName, final double arg) {
    if (arg == 0) {
      throw new IllegalArgumentException(argName + " cannot be zero.");
    }
    return arg;
  }

  public static byte requirePositive(final String argName, final byte arg) {
    if (arg <= 0) {
      throw new IllegalArgumentException(argName + " must be positive.");
    }
    return arg;
  }

  public static short requirePositive(final String argName, final short arg) {
    if (arg <= 0) {
      throw new IllegalArgumentException(argName + " must be positive.");
    }
    return arg;
  }

  public static int requirePositive(final String argName, final int arg) {
    if (arg <= 0) {
      throw new IllegalArgumentException(argName + " must be positive.");
    }
    return arg;
  }

  public static long requirePositive(final String argName, final long arg) {
    if (arg <= 0) {
      throw new IllegalArgumentException(argName + " must be positive.");
    }
    return arg;
  }

  public static float requirePositive(final String argName, final float arg) {
    if (arg <= 0) {
      throw new IllegalArgumentException(argName + " must be positive.");
    }
    return arg;
  }

  public static double requirePositive(final String argName, final double arg) {
    if (arg <= 0) {
      throw new IllegalArgumentException(argName + " must be positive.");
    }
    return arg;
  }

  public static byte requireNonPositive(final String argName, final byte arg) {
    if (arg > 0) {
      throw new IllegalArgumentException(argName + " must be non-positive.");
    }
    return arg;
  }

  public static short requireNonPositive(final String argName, final short arg) {
    if (arg > 0) {
      throw new IllegalArgumentException(argName + " must be non-positive.");
    }
    return arg;
  }

  public static int requireNonPositive(final String argName, final int arg) {
    if (arg > 0) {
      throw new IllegalArgumentException(argName + " must be non-positive.");
    }
    return arg;
  }

  public static long requireNonPositive(final String argName, final long arg) {
    if (arg > 0) {
      throw new IllegalArgumentException(argName + " must be non-positive.");
    }
    return arg;
  }

  public static float requireNonPositive(final String argName, final float arg) {
    if (arg > 0) {
      throw new IllegalArgumentException(argName + " must be non-positive.");
    }
    return arg;
  }

  public static double requireNonPositive(final String argName, final double arg) {
    if (arg > 0) {
      throw new IllegalArgumentException(argName + " must be non-positive.");
    }
    return arg;
  }

  public static byte requireNegative(final String argName, final byte arg) {
    if (arg >= 0) {
      throw new IllegalArgumentException(argName + " must be negative.");
    }
    return arg;
  }

  public static short requireNegative(final String argName, final short arg) {
    if (arg >= 0) {
      throw new IllegalArgumentException(argName + " must be negative.");
    }
    return arg;
  }

  public static int requireNegative(final String argName, final int arg) {
    if (arg >= 0) {
      throw new IllegalArgumentException(argName + " must be negative.");
    }
    return arg;
  }

  public static long requireNegative(final String argName, final long arg) {
    if (arg >= 0) {
      throw new IllegalArgumentException(argName + " must be negative.");
    }
    return arg;
  }

  public static float requireNegative(final String argName, final float arg) {
    if (arg >= 0) {
      throw new IllegalArgumentException(argName + " must be negative.");
    }
    return arg;
  }

  public static double requireNegative(final String argName, final double arg) {
    if (arg >= 0) {
      throw new IllegalArgumentException(argName + " must be negative.");
    }
    return arg;
  }

  public static byte requireNonNegative(final String argName, final byte arg) {
    if (arg < 0) {
      throw new IllegalArgumentException(argName + " must be non-negative.");
    }
    return arg;
  }

  public static short requireNonNegative(final String argName, final short arg) {
    if (arg < 0) {
      throw new IllegalArgumentException(argName + " must be non-negative.");
    }
    return arg;
  }

  public static int requireNonNegative(final String argName, final int arg) {
    if (arg < 0) {
      throw new IllegalArgumentException(argName + " must be non-negative.");
    }
    return arg;
  }

  public static long requireNonNegative(final String argName, final long arg) {
    if (arg < 0) {
      throw new IllegalArgumentException(argName + " must be non-negative.");
    }
    return arg;
  }

  public static float requireNonNegative(final String argName, final float arg) {
    if (arg < 0) {
      throw new IllegalArgumentException(argName + " must be non-negative.");
    }
    return arg;
  }

  public static double requireNonNegative(final String argName, final double arg) {
    if (arg < 0) {
      throw new IllegalArgumentException(argName + " must be non-negative.");
    }
    return arg;
  }

  public static <T> void requireSame(final String argName1, final T arg1,
      final String argName2, final T arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(argName1 + " and " + argName2
          + " must be the same object.");
    }
  }

  public static <T> void requireNonSame(final String argName1, final T arg1,
      final String argName2, final T arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException(argName1 + " and " + argName2
          + " can not be the same object.");
    }
  }

  public static boolean requireEqual(final String argName1, final boolean arg1,
      final String argName2, final boolean arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(argName1 + " and " + argName2
          + " must be equal.");
    }
    return arg1;
  }

  public static char requireEqual(final String argName1, final char arg1,
      final String argName2, final char arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(argName1 + " and " + argName2
          + " must be equal.");
    }
    return arg1;
  }

  public static byte requireEqual(final String argName1, final byte arg1,
      final String argName2, final byte arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(argName1 + " and " + argName2
          + " must be equal.");
    }
    return arg1;
  }

  public static short requireEqual(final String argName1, final short arg1,
      final String argName2, final short arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(argName1 + " and " + argName2
          + " must be equal.");
    }
    return arg1;
  }

  public static int requireEqual(final String argName1, final int arg1,
      final String argName2, final int arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(argName1 + " and " + argName2
          + " must be equal.");
    }
    return arg1;
  }

  public static long requireEqual(final String argName1, final long arg1,
      final String argName2, final long arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(argName1 + " and " + argName2
          + " must be equal.");
    }
    return arg1;
  }

  public static <T> T requireEqual(final String argName1, final T arg1,
      final String argName2, final T arg2) {
    if (! Equality.equals(arg1, arg2)) {
      throw new IllegalArgumentException(argName1 + " and " + argName2
          + " must be equal.");
    }
    return arg1;
  }

  public static boolean requireNotEqual(final String argName1,
      final boolean arg1, final String argName2, final boolean arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException("The '" + argName1
          + "' must not equal to " + argName2);
    }
    return arg1;
  }

  public static char requireNotEqual(final String argName1, final char arg1,
      final String argName2, final char arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException("The '" + argName1
          + "' must not equal to " + argName2);
    }
    return arg1;
  }

  public static byte requireNotEqual(final String argName1, final byte arg1,
      final String argName2, final byte arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException("The '" + argName1
          + "' must not equal to " + argName2);
    }
    return arg1;
  }

  public static short requireNotEqual(final String argName1, final short arg1,
      final String argName2, final short arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException("The '" + argName1
          + "' must not equal to " + argName2);
    }
    return arg1;
  }

  public static int requireNotEqual(final String argName1, final int arg1,
      final String argName2, final int arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException("The '" + argName1
          + "' must not equal to " + argName2);
    }
    return arg1;
  }

  public static long requireNotEqual(final String argName1, final long arg1,
      final String argName2, final long arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException("The '" + argName1
          + "' must not equal to " + argName2);
    }
    return arg1;
  }

  public static <T> T requireNotEqual(final String argName1, final T arg1,
      final String argName2, final T arg2) {
    if (Equality.equals(arg1, arg2)) {
      throw new IllegalArgumentException(argName1 + " and " + argName2
          + " can not be equal.");
    }
    return arg1;
  }

  public static char requireLess(final String argName1, final char arg1,
      final String argName2, final char arg2) {
    if (arg1 >= arg2) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be less than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static byte requireLess(final String argName1, final byte arg1,
      final String argName2, final byte arg2) {
    if (arg1 >= arg2) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be less than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static short requireLess(final String argName1, final short arg1,
      final String argName2, final short arg2) {
    if (arg1 >= arg2) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be less than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static int requireLess(final String argName1, final int arg1,
      final String argName2, final int arg2) {
    if (arg1 >= arg2) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be less than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static long requireLess(final String argName1, final long arg1,
      final String argName2, final long arg2) {
    if (arg1 >= arg2) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be less than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static <T extends Comparable<T>> T requireLess(final String argName1,
      final T arg1, final String argName2, final T arg2) {
    final int rc = arg1.compareTo(arg2);
    if (rc >= 0) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be less than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static char requireLessEqual(final String argName1, final char arg1,
      final String argName2, final char arg2) {
    if (arg1 > arg2) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be less equal than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static byte requireLessEqual(final String argName1, final byte arg1,
      final String argName2, final byte arg2) {
    if (arg1 > arg2) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be less equal than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static short requireLessEqual(final String argName1, final short arg1,
      final String argName2, final short arg2) {
    if (arg1 > arg2) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be less equal than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static int requireLessEqual(final String argName1, final int arg1,
      final String argName2, final int arg2) {
    if (arg1 > arg2) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be less equal than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static long requireLessEqual(final String argName1, final long arg1,
      final String argName2, final long arg2) {
    if (arg1 > arg2) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be less equal than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static <T extends Comparable<T>> T requireLessEqual(
      final String argName1, final T arg1, final String argName2, final T arg2) {
    final int rc = arg1.compareTo(arg2);
    if (rc > 0) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be less equal than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static char requireGreater(final String argName1, final char arg1,
      final String argName2, final char arg2) {
    if (arg1 <= arg2) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be greater than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static byte requireGreater(final String argName1, final byte arg1,
      final String argName2, final byte arg2) {
    if (arg1 <= arg2) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be greater than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static short requireGreater(final String argName1, final short arg1,
      final String argName2, final short arg2) {
    if (arg1 <= arg2) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be greater than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static int requireGreater(final String argName1, final int arg1,
      final String argName2, final int arg2) {
    if (arg1 <= arg2) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be greater than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static long requireGreater(final String argName1, final long arg1,
      final String argName2, final long arg2) {
    if (arg1 <= arg2) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be greater than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static <T extends Comparable<T>> T requireGreater(
      final String argName1, final T arg1, final String argName2, final T arg2) {
    final int rc = arg1.compareTo(arg2);
    if (rc <= 0) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be greater than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static char requireGreaterEqual(final String argName1,
      final char arg1, final String argName2, final char arg2) {
    if (arg1 < arg2) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be greater equal than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static byte requireGreaterEqual(final String argName1,
      final byte arg1, final String argName2, final byte arg2) {
    if (arg1 < arg2) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be greater equal than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static short requireGreaterEqual(final String argName1,
      final short arg1, final String argName2, final short arg2) {
    if (arg1 < arg2) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be greater equal than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static int requireGreaterEqual(final String argName1, final int arg1,
      final String argName2, final int arg2) {
    if (arg1 < arg2) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be greater equal than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static long requireGreaterEqual(final String argName1,
      final long arg1, final String argName2, final long arg2) {
    if (arg1 < arg2) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be greater equal than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static <T extends Comparable<T>> T requireGreaterEqual(
      final String argName1, final T arg1, final String argName2, final T arg2) {
    final int rc = arg1.compareTo(arg2);
    if (rc < 0) {
      throw new IllegalArgumentException(argName1 + "(" + arg1
          + ") must be greater equal than " + argName2 + "(" + arg2 + ").");
    }
    return arg1;
  }

  public static byte requireInCloseRange(final String argumentName,
      final byte argument, final byte left, final byte right) {
    if ((argument < left) || (argument > right)) {
      throw new IllegalArgumentException(argumentName
          + " must in the close range [" + left + ", " + right
          + "], but it is " + argument);
    }
    return argument;
  }

  public static short requireInCloseRange(final String argumentName,
      final short argument, final short left, final short right) {
    if ((argument < left) || (argument > right)) {
      throw new IllegalArgumentException(argumentName
          + " must in the close range [" + left + ", " + right
          + "], but it is " + argument);
    }
    return argument;
  }

  public static int requireInCloseRange(final String argumentName,
      final int argument, final int left, final int right) {
    if ((argument < left) || (argument > right)) {
      throw new IllegalArgumentException(argumentName
          + " must in the close range [" + left + ", " + right + "], "
          + " but it is " + argument);
    }
    return argument;
  }

  public static long requireInCloseRange(final String argumentName,
      final long argument, final long left, final long right) {
    if ((argument < left) || (argument > right)) {
      throw new IllegalArgumentException(argumentName
          + " must in the close range [" + left + ", " + right + "], "
          + " but it is " + argument);
    }
    return argument;
  }

  public static byte requireInOpenRange(final String argumentName,
      final byte argument, final byte left, final byte right) {
    if ((argument <= left) || (argument >= right)) {
      throw new IllegalArgumentException(argumentName
          + " must in the open range (" + left + ", " + right + "), "
          + " but it is " + argument);
    }
    return argument;
  }

  public static short requireInOpenRange(final String argumentName,
      final short argument, final short left, final short right) {
    if ((argument <= left) || (argument >= right)) {
      throw new IllegalArgumentException(argumentName
          + " must in the open range (" + left + ", " + right + "), "
          + " but it is " + argument);
    }
    return argument;
  }

  public static int requireInOpenRange(final String argumentName,
      final int argument, final int left, final int right) {
    if ((argument <= left) || (argument >= right)) {
      throw new IllegalArgumentException(argumentName
          + " must in the open range (" + left + ", " + right + "), "
          + " but it is " + argument);
    }
    return argument;
  }

  public static long requireInOpenRange(final String argumentName,
      final long argument, final long left, final long right) {
    if ((argument <= left) || (argument >= right)) {
      throw new IllegalArgumentException(argumentName
          + " must in the open range (" + left + ", " + right + "), "
          + " but it is " + argument);
    }
    return argument;
  }

  public static byte requireInLeftOpenRange(final String argumentName,
      final byte argument, final byte left, final byte right) {
    if ((argument <= left) || (argument > right)) {
      throw new IllegalArgumentException(argumentName
          + " must in the left open range (" + left + ", " + right + "], "
          + " but it is " + argument);
    }
    return argument;
  }

  public static short requireInLeftOpenRange(final String argumentName,
      final short argument, final short left, final short right) {
    if ((argument <= left) || (argument > right)) {
      throw new IllegalArgumentException(argumentName
          + " must in the left open range (" + left + ", " + right + "], "
          + " but it is " + argument);
    }
    return argument;
  }

  public static int requireInLeftOpenRange(final String argumentName,
      final int argument, final int left, final int right) {
    if ((argument <= left) || (argument > right)) {
      throw new IllegalArgumentException(argumentName
          + " must in the left open range (" + left + ", " + right + "], "
          + " but it is " + argument);
    }
    return argument;
  }

  public static long requireInLeftOpenRange(final String argumentName,
      final long argument, final long left, final long right) {
    if ((argument <= left) || (argument > right)) {
      throw new IllegalArgumentException(argumentName
          + " must in the left open range (" + left + ", " + right + "], "
          + " but it is " + argument);
    }
    return argument;
  }

  public static byte requireInRightOpenRange(final String argumentName,
      final byte argument, final byte left, final byte right) {
    if ((argument < left) || (argument >= right)) {
      throw new IllegalArgumentException(argumentName
          + " must in the right open range [" + left + ", " + right + "), "
          + " but it is " + argument);
    }
    return argument;
  }

  public static short requireInRightOpenRange(final String argumentName,
      final short argument, final short left, final short right) {
    if ((argument < left) || (argument >= right)) {
      throw new IllegalArgumentException(argumentName
          + " must in the right open range [" + left + ", " + right + "), "
          + " but it is " + argument);
    }
    return argument;
  }

  public static int requireInRightOpenRange(final String argumentName,
      final int argument, final int left, final int right) {
    if ((argument < left) || (argument >= right)) {
      throw new IllegalArgumentException(argumentName
          + " must in the right open range [" + left + ", " + right + "), "
          + " but it is " + argument);
    }
    return argument;
  }

  public static long requireInRightOpenRange(final String argumentName,
      final long argument, final long left, final long right) {
    if ((argument < left) || (argument >= right)) {
      throw new IllegalArgumentException(argumentName
          + " must in the right open range [" + left + ", " + right + "), "
          + " but it is " + argument);
    }
    return argument;
  }

  public static int requireIndexInCloseRange(final int index, final int left,
      final int right) {
    if ((index < left) || (index > right)) {
      throw new IndexOutOfBoundsException("The index must in the close range ["
          + left + ", " + right + "], " + " but it is " + index);
    }
    return index;
  }

  public static int requireIndexInOpenRange(final int index, final int left,
      final int right) {
    if ((index <= left) || (index >= right)) {
      throw new IndexOutOfBoundsException("The index must in the open range ("
          + left + ", " + right + "), " + " but it is " + index);
    }
    return index;
  }

  public static int requireIndexInLeftOpenRange(final int index,
      final int left, final int right) {
    if ((index <= left) || (index > right)) {
      throw new IndexOutOfBoundsException(
          "The index must in the left open range (" + left + ", " + right
              + "], " + " but it is " + index);
    }
    return index;
  }

  public static int requireIndexInRightOpenRange(final int index,
      final int left, final int right) {
    if ((index < left) || (index >= right)) {
      throw new IndexOutOfBoundsException(
          "The index must in the right open range [" + left + ", " + right
              + "), " + " but it is " + index);
    }
    return index;
  }

  public static byte requireInEnum(final String argumentName,
      final byte argument, final byte[] allowedValues) {
    for (int i = 0; i < allowedValues.length; ++i) {
      if (argument == allowedValues[i]) {
        return argument;
      }
    }
    throw new IllegalArgumentException(argumentName + " must in enumeration ["
        + StringUtils.join(',', allowedValues) + "], " + " but it is "
        + argument);
  }

  public static short requireInEnum(final String argumentName,
      final short argument, final short[] allowedValues) {
    for (int i = 0; i < allowedValues.length; ++i) {
      if (argument == allowedValues[i]) {
        return argument;
      }
    }
    throw new IllegalArgumentException(argumentName + " must in enumeration ["
        + StringUtils.join(',', allowedValues) + "], " + " but it is "
        + argument);
  }

  public static int requireInEnum(final String argumentName,
      final int argument, final int[] allowedValues) {
    for (int i = 0; i < allowedValues.length; ++i) {
      if (argument == allowedValues[i]) {
        return argument;
      }
    }
    throw new IllegalArgumentException(argumentName + " must in enumeration ["
        + StringUtils.join(',', allowedValues) + "], " + " but it is "
        + argument);
  }

  public static long requireInEnum(final String argumentName,
      final long argument, final long[] allowedValues) {
    for (int i = 0; i < allowedValues.length; ++i) {
      if (argument == allowedValues[i]) {
        return argument;
      }
    }
    throw new IllegalArgumentException(argumentName + " must in enumeration ["
        + StringUtils.join(',', allowedValues) + "], " + " but it is "
        + argument);
  }

  public static int requireValidUnicode(final String argumentName,
      final int codePoint) {
    if (! Unicode.isValidUnicode(codePoint)) {
      throw new IllegalArgumentException(argumentName
          + " must be a valid Unicode code point," + " but it is " + codePoint);
    }
    return codePoint;
  }
}
