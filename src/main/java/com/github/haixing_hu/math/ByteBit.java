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

package com.github.haixing_hu.math;

import javax.annotation.concurrent.ThreadSafe;

/**
 * A class providing functions for bit manipulation of <code>byte</code>.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class ByteBit {

    public static final int BITS            = 8;
    public static final int HALF_BITS       = 4;
    public static final int HALF_BITS_MASK  = 0x0F;
    public static final int FULL_BITS_MASK  = 0xFF;
    public static final int MSB_MASK        = 0x80;

    public static final int RANGE_MASK[]    = {
            0x00, 0x01, 0x03, 0x07, 0x0F, 0x1F, 0x3F, 0x7F, 0xFF
    };

    public static final int BIT_MASK[]      = {
            0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80
    };

    public static final int REVERSE[]       = {
            0x00, 0x80, 0x40, 0xC0, 0x20, 0xA0, 0x60, 0xE0, 0x10, 0x90, 0x50,
            0xD0, 0x30, 0xB0, 0x70, 0xF0, 0x08, 0x88, 0x48, 0xC8, 0x28, 0xA8,
            0x68, 0xE8, 0x18, 0x98, 0x58, 0xD8, 0x38, 0xB8, 0x78, 0xF8, 0x04,
            0x84, 0x44, 0xC4, 0x24, 0xA4, 0x64, 0xE4, 0x14, 0x94, 0x54, 0xD4,
            0x34, 0xB4, 0x74, 0xF4, 0x0C, 0x8C, 0x4C, 0xCC, 0x2C, 0xAC, 0x6C,
            0xEC, 0x1C, 0x9C, 0x5C, 0xDC, 0x3C, 0xBC, 0x7C, 0xFC, 0x02, 0x82,
            0x42, 0xC2, 0x22, 0xA2, 0x62, 0xE2, 0x12, 0x92, 0x52, 0xD2, 0x32,
            0xB2, 0x72, 0xF2, 0x0A, 0x8A, 0x4A, 0xCA, 0x2A, 0xAA, 0x6A, 0xEA,
            0x1A, 0x9A, 0x5A, 0xDA, 0x3A, 0xBA, 0x7A, 0xFA, 0x06, 0x86, 0x46,
            0xC6, 0x26, 0xA6, 0x66, 0xE6, 0x16, 0x96, 0x56, 0xD6, 0x36, 0xB6,
            0x76, 0xF6, 0x0E, 0x8E, 0x4E, 0xCE, 0x2E, 0xAE, 0x6E, 0xEE, 0x1E,
            0x9E, 0x5E, 0xDE, 0x3E, 0xBE, 0x7E, 0xFE, 0x01, 0x81, 0x41, 0xC1,
            0x21, 0xA1, 0x61, 0xE1, 0x11, 0x91, 0x51, 0xD1, 0x31, 0xB1, 0x71,
            0xF1, 0x09, 0x89, 0x49, 0xC9, 0x29, 0xA9, 0x69, 0xE9, 0x19, 0x99,
            0x59, 0xD9, 0x39, 0xB9, 0x79, 0xF9, 0x05, 0x85, 0x45, 0xC5, 0x25,
            0xA5, 0x65, 0xE5, 0x15, 0x95, 0x55, 0xD5, 0x35, 0xB5, 0x75, 0xF5,
            0x0D, 0x8D, 0x4D, 0xCD, 0x2D, 0xAD, 0x6D, 0xED, 0x1D, 0x9D, 0x5D,
            0xDD, 0x3D, 0xBD, 0x7D, 0xFD, 0x03, 0x83, 0x43, 0xC3, 0x23, 0xA3,
            0x63, 0xE3, 0x13, 0x93, 0x53, 0xD3, 0x33, 0xB3, 0x73, 0xF3, 0x0B,
            0x8B, 0x4B, 0xCB, 0x2B, 0xAB, 0x6B, 0xEB, 0x1B, 0x9B, 0x5B, 0xDB,
            0x3B, 0xBB, 0x7B, 0xFB, 0x07, 0x87, 0x47, 0xC7, 0x27, 0xA7, 0x67,
            0xE7, 0x17, 0x97, 0x57, 0xD7, 0x37, 0xB7, 0x77, 0xF7, 0x0F, 0x8F,
            0x4F, 0xCF, 0x2F, 0xAF, 0x6F, 0xEF, 0x1F, 0x9F, 0x5F, 0xDF, 0x3F,
            0xBF, 0x7F, 0xFF
    };

    public static final int COUNT[]         = {
            0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4, 1, 2, 2, 3, 2, 3,
            3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4,
            3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 1, 2,
            2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5,
            3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5,
            5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 1, 2, 2, 3,
            2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4,
            4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
            3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 2, 3, 3, 4, 3, 4,
            4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6,
            5, 6, 6, 7, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 4, 5,
            5, 6, 5, 6, 6, 7, 5, 6, 6, 7, 6, 7, 7, 8
    };

    public static final int FIRST_SET[]     = {
            8, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 4, 0, 1, 0, 2, 0,
            1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 5, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0,
            2, 0, 1, 0, 4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 6, 0,
            1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 4, 0, 1, 0, 2, 0, 1, 0,
            3, 0, 1, 0, 2, 0, 1, 0, 5, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0,
            1, 0, 4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 7, 0, 1, 0,
            2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 4, 0, 1, 0, 2, 0, 1, 0, 3, 0,
            1, 0, 2, 0, 1, 0, 5, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
            4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 6, 0, 1, 0, 2, 0,
            1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0,
            2, 0, 1, 0, 5, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 4, 0,
            1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0
    };

    public static final int LAST_SET[]      = {
            8, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4,
            4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
            5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6,
            6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
            6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
            6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7,
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7
    };

  /**
   * Return the value of x as if x is an unsigned byte.
   *
   * Note that this function is sometimes useful since there is no unsigned type
   * in Java.
   *
   * @param x
   *          A byte value which should be treated as an unsigned byte.
   * @return
   *         The value of x as if x is an unsigned byte. More precisely, if x is
   *         non-negative, returns the value of x; otherwise, returns the value
   *         of (256 + x).
   */
  public static int asUnsigned(byte x) {
    return (x & FULL_BITS_MASK);
  }

  /**
   * Returns the lowest n bits of x.
   *
   * @param x
   *          The value.
   * @param n
   *          The amount of lowest bits to be returned. It must be in the range
   *          of [0, BITS].
   * @return A value of type <code>byte</code> whose lowest n bits are the same
   *         of the lowest n bits of x, and other bits are 0. If n == 0, the
   *         function returns 0. If n == BITS, the function returns x. If n < 0
   *         or n > BITS, the behaviour is undefined.
   */
  public static byte low(byte x, int n) {
    assert ((0 <= n) && (n <= BITS));
    return (byte) (x & RANGE_MASK[n]);
  }

  /**
   * Returns the highest n bits of x.
   *
   * @param x
   *          The value.
   * @param n
   *          The amount of lowest bits to be returned. It must be in the range
   *          of [0, BITS].
   * @return A value of type <code>byte</code> whose highest n bits are the same
   *         of the highest n bits of x, and other bits are 0. If n == 0, the
   *         function returns 0. If n == BITS, the function returns x. If n < 0
   *         or n > BITS, the behaviour is undefined.
   */
  public static byte high(byte x, int n) {
    assert ((0 <= n) && (n <= BITS));
    return (byte) ((x & FULL_BITS_MASK) >>> (BITS - n));
  }

  /**
   * Returns the lower half bits of x.
   *
   * @param x
   *          The value.
   * @return A value of type <code>byte</code> whose lower half bits are the
   *         same of the lower half bits of x, and other bits are 0. In fact,
   *         this function returns low(x, HALF_BITS).
   */
  public static byte lowHalf(byte x) {
    return (byte) (x & HALF_BITS_MASK);
  }

  /**
   * Returns the higher half bits of x.
   *
   * @param x
   *          The value.
   * @return A value of type <code>byte</code> whose higher half bits are the
   *         same of the higher half bits of x, and other bits are 0. In fact,
   *         this function returns high(x, HALF_BITS).
   */
  public static byte highHalf(byte x) {
    return (byte) ((x & FULL_BITS_MASK) >>> HALF_BITS);
  }

  /**
   * Swap the lowest n bits and highest (BITS - n) bits of x. That is, swap the
   * bit range [0, n) and [n, BITS).
   *
   * @param x
   *          The value to be swaped.
   * @param n
   *          The amount of lowest bits which would be swapped with the rest
   *          high bits. It must be in the range of [0, BITS].
   * @return A new value whose highest n bits are x's lowest n bits, and whose
   *         lowest (BITS - n) bits are x's highest (BITS - n) bits. If n == 0
   *         or n == BITS, the function returns x. If n < 0 or n > BITS, the
   *         behaviour of the function is undefined.
   */
  public static byte rotate(byte x, int n) {
    assert ((0 <= n) && (n <= BITS));
    int m = BITS - n;
    int ix = (x & FULL_BITS_MASK);
    int ixl = (ix & RANGE_MASK[n]);
    int ixh = (ix >>> m);
    return (byte) ((ixl << m) | ixh);
  }

  /**
   * Swap the lowest half bits and highest half bits of x. That is, swap the bit
   * range [0, HALF_BITS) and [HALF_BITS, BITS).
   *
   * @param x
   *          The value to be swaped.
   * @return A new value whose highest half bits are x's lowest half bits, and
   *         whose lowest half bits are x's highest half bits.
   */
  public static byte rotateHalf(byte x) {
    int ix = (x & FULL_BITS_MASK);
    int ixl = (ix & HALF_BITS_MASK);
    int ixh = (ix >>> HALF_BITS);
    return (byte) ((ixl << HALF_BITS) | ixh);
  }

  /**
   * Swap the bit i and the bit j of x, return the new value.
   *
   * For example, assume IntType is unsigned char, x is 00101111 in binary, i =
   * 1, j = 4, swap(x, i, j) will swap the bit 1 of x with the bit 4 of x, and
   * gets 00111101.
   *
   * @param x
   *          The original value.
   * @param i
   *          , j The bits to be swaped. It must be in the range of [0, BITS).
   * @return A new value whose bit i is the bit j of x, and whose bit j is the
   *         bit i of x, and the rest bits are the same as x. If i == j, the
   *         function returns x.
   */
  public static byte swap(byte x, int i, int j) {
    assert ((0 <= i) && (i < BITS) && (0 <= j) && (j < BITS));

    // Use a trick from http://graphics.stanford.edu/~seander/bithacks.html
    int ix = (x & FULL_BITS_MASK);
    int iy = (((ix >>> i) ^ (ix >>> j)) & 1);
    return (byte) (ix ^ ((iy << i) | (iy << j)));
  }

  /**
   * Swap the bit range [i, i+n) and the bit range [j, j+n) of x, return the new
   * value.
   *
   * For example, assume byte is unsigned char, x is 00101111 in binary, i = 1,
   * j = 5, n = 3, swapRange(x, i, j, n) will swap the 1,2,3 bit of x with the
   * 5,6,7 bit of x, or in other words, swap the first parathesis and the second
   * parathesis in the following representation:
   *
   *    (001)0(111)1
   *
   * and gets
   *
   *    (111)0(001)1
   *
   * that is the binary 11100011.
   *
   * @param x
   *          The original value.
   * @param i
   *          ,j The start position of bit to be swaped.
   * @param n
   *          The length of the consecutive bits to be swapped.
   * @return A new value whose bits in the range [i, i+n) are those bits in the
   *         range [j, j+n) of x, and whose bits in the range [j, j+n) are those
   *         bits in the range [i, i+n) of x, and the rest bits are the same as
   *         x. If n == 0, the function returns x. If i + n > BITS or j + n >
   *         BITS, that is, the range [i, i+n) or [j, j+n) override the range
   *         [0, BITS), the behaviour of the function if undefined. If i == j,
   *         the function returns x. If i < j and i + n > j, or if j < i and j +
   *         n > i, that is, the two range [i, i+n) and [j, j+n) overlaps, the
   *         behaviour of the function is undefined.
   */
  public static byte swapRange(byte x, int i, int j, int n) {
    assert ((0 <= i) && (i < BITS) && (0 <= j) && (j < BITS) && (n > 0)
        && (i + n <= BITS) && (j + n <= BITS) && ((i + n <= j) || (j + n <= i)));

    // Use a trick from http://graphics.stanford.edu/~seander/bithacks.html
    int ix = (x & FULL_BITS_MASK);
    int iy = (((ix >>> i) ^ (ix >>> j)) & RANGE_MASK[n]);
    return (byte) (ix ^ ((iy << i) | (iy << j)));
  }

  /**
   * Reverse the bits in the x, that is, the bit at position i in the result is
   * the bit at position (BITS - 1 - i) in the x.
   *
   * For example, byte is a binary unsigned char of 01111011 (note that the
   * leading 0 are reserved). reverse(x) gets 11011110.
   *
   * @param x
   *          The value to be reversed.
   * @return A new value by reversing the bits of x.
   */
  public static byte reverse(byte x) {
    return (byte) REVERSE[x & FULL_BITS_MASK];
  }

  /**
   * Merge the bits from two values according to a mask.
   *
   * For each bit position i in the result value, if bit i of mask is 0, the bit
   * i of result is the same as the bit i of x; if bit i of mask is 1, the bit i
   * of result is the same as the bit i of y.
   *
   * For example, let x = 10101010, y = 11001100, mask = 01110010, merge(x, y,
   * mask) will get a result of 11001000 (all numbers are in the binary form).
   *
   * @param x
   *          The first operand
   * @param y
   *          The second operand.
   * @param mask
   *          The mask used to select bit from x and y. If the bit i in the mask
   *          is 0, select the i-th bit from x; otherwise, select the i-th bit
   *          from y.
   * @return The merge of x and y accroding to the mask.
   */
  public static byte merge(byte x, byte y, byte mask) {
    // Use a trick from http://graphics.stanford.edu/~seander/bithacks.html
    return (byte) (x ^ ((x ^ y) & mask));
  }

  /**
   * Sets all the bits of x to 1. In fact, this function set the x to be ~T(0).
   *
   * @param x
   *          The value to be modified.
   * @return The result of operation.
   */
  public static byte set(byte x) {
    return (byte) FULL_BITS_MASK;
  }

  /**
   * Sets the bit i of x to 1.
   *
   * @param x
   *          The value to be modified.
   * @param i
   *          The bit of x to be set. It must be in the range of [0, BITS).
   * @return The result of operation. If i < 0 or i >= BITS, the behaviour of
   *         the function is undefined.
   */
  public static byte set(byte x, int i) {
    assert ((0 <= i) && (i < BITS));
    return (byte) (x | BIT_MASK[i]);
  }

  /**
   * Sets the most significant bit of x to 1.
   *
   * Note that the most significant bit of a signed integral type variable is
   * its sign bit.
   *
   * @param x
   *          The value to be modified.
   * @return The result of operation.
   */
  public static byte setMsb(byte x) {
    return (byte) (x | MSB_MASK);
  }

  /**
   * Set the lowest n bits of x to 1.
   *
   * @param x
   *          The value to be modified.
   * @param n
   *          The amount of lowest bits to be set. It must be in the range of
   *          [0, BITS].
   * @return The result of operation.
   */
  public static byte setLow(byte x, int n) {
    assert ((0 <= n) && (n < BITS));
    return (byte) (x | RANGE_MASK[n]);
  }

  /**
   * Set the highest n bits of x to 1.
   *
   * @param x
   *          The value to be modified.
   * @param n
   *          The amount of highest bits to be set. It must be in the range of
   *          [0, BITS].
   * @return The result of operation.
   */
  public static byte setHigh(byte x, int n) {
    assert ((0 <= n) && (n < BITS));
    return (byte) (x | (~RANGE_MASK[BITS - n]));
  }

  /**
   * Sets all the bits of x to 0. In fact, this function set the x to T(0).
   *
   * @param x
   *          The value to be modified.
   * @return The result of operation.
   */
  public static byte unset(byte x) {
    return 0;
  }

  /**
   * Sets the bit i of x to 0.
   *
   * @param x
   *          The value to be modified.
   * @param i
   *          The bit of x to be reset. It must be in the range of [0, BITS).
   * @return The result of operation. If i < 0 or i >= BITS, the behaviour of
   *         the function is undefined.
   */
  public static byte unset(byte x, int i) {
    assert ((0 <= i) && (i < BITS));
    return (byte) (x & (~BIT_MASK[i]));
  }

  /**
   * Resets the most significant bit of x to 1.
   *
   * Note that the most significant bit of a signed integral type variable is
   * its sign bit.
   *
   * @param x
   *          The value to be modified.
   * @return The result of operation.
   */
  public static byte unsetMsb(byte x) {
    return (byte) (x & (~MSB_MASK));
  }

  /**
   * Set the lowest n bits of x to 0.
   *
   * @param x
   *          The value to be modified.
   * @param n
   *          The amount of lowest bits to be set. It must be in the range of
   *          [0, BITS].
   * @return The result of operation.
   */
  public static byte unsetLow(byte x, int n) {
    assert ((0 <= n) && (n < BITS));
    return (byte) (x & (~RANGE_MASK[n]));
  }

  /**
   * Set the highest n bits of x to 0.
   *
   * @param x
   *          The value to be modified.
   * @param n
   *          The amount of highest bits to be set. It must be in the range of
   *          [0, BITS].
   * @return The result of operation.
   */
  public static byte unsetHigh(byte x, int n) {
    assert ((0 <= n) && (n < BITS));
    return (byte) (x & RANGE_MASK[BITS - n]);
  }

  /**
   * Inverts all the bits of x, i.e., change 1s for 0s, and 0s for 1s.
   *
   * @param x
   *          The value to be modified.
   * @return The result of operation.
   */
  public static byte invert(byte x) {
    return (byte) (~x);
  }

  /**
   * Inverts the specified bit of x, i.e., change 1 for 0, and 0 for 1.
   *
   * @param x
   *          The value to be modified.
   * @param i
   *          The bit of x to be flipped. It must be in the range of [0, BITS).
   * @return The result of operation. If i < 0 or i >= BITS, the behaviour of
   *         the function is undefined.
   */
  public static byte invert(byte x, int i) {
    assert ((0 <= i) && (i < BITS));
    return (byte) (x ^ BIT_MASK[i]);
  }

  /**
   * Invert the most significant bit of x to 1.
   *
   * Note that the most significant bit of a signed integral type variable is
   * its sign bit.
   *
   * @param x
   *          The value to be modified.
   * @return The result of operation.
   */
  public static byte invertMsb(byte x) {
    return (byte) (x ^ MSB_MASK);
  }

  /**
   * Test the bit i of x.
   *
   * @param x
   *          The value.
   * @param i
   *          The bit of x to be test. It must be in the range of [0, BITS).
   * @return true if the bit i of x is 1, false otherwise. If i < 0 or i >=
   *         BITS, the behaviour of the function is undefined.
   */
  public static boolean test(byte x, int i) {
    assert ((0 <= i) && (i < BITS));
    return ((x & BIT_MASK[i]) != 0);
  }

  /**
   * Test the most significant bit of x.
   *
   * Note that the most significant bit of a signed integral type variable is
   * its sign bit.
   *
   * @param x
   *          The operand.
   * @return true if the most significant bit of x is 1, false otherwise.
   */
  public static boolean testMsb(byte x) {
    return ((x & MSB_MASK) != 0);
  }

  /**
   * Count the amount of bits set in the value of x.
   *
   * @param x
   *          The value whose bits are to be counted.
   * @return The amount of bits set in the x.
   */
  public static int count(byte x) {
    return COUNT[x & FULL_BITS_MASK];
  }

  /**
   * Test whether all bits of x are set to 1.
   *
   * @param x
   *          The value to be test.
   * @return true if all bits of x are set to 1, false otherwise.
   */
  public static boolean hasAll(byte x) {
    return (x == (byte) FULL_BITS_MASK);
  }

  /**
   * Test whether there is any bit of x which is set to 1.
   *
   * @param x
   *          The value to be test.
   * @return true if there is at least one bit of x which is set to 1, false
   *         otherwise.
   */
  public static boolean hasAny(byte x) {
    return (x != 0);
  }

  /**
   * Test whether none of the bit of x is set to 1, i.e., all bits of x are set
   * to 0.
   *
   * @param x
   *          The value to be test.
   * @return true if none of the bit of x is set to 1, false otherwise.
   */
  public static boolean hasNone(byte x) {
    return (x == 0);
  }

  /**
   * Find the lowest set bit (of value 1) in x.
   *
   * For example, x is an unsigned char of binary value 00101010,
   * findFirstSet(x) will return 1.
   *
   * @param x
   *          The value whose first set bit is to be found.
   * @return The lowest position of the bit in x which is set to 1. If all bits
   *         in x are 0s, returns BITS.
   */
  public static int findFirstSet(byte x) {
    return FIRST_SET[x & FULL_BITS_MASK];
  }

  /**
   * Find the lowest set bit (of value 1) in x starting from the bit n, i.e., in
   * the bit range [n, BITS).
   *
   * For example, x is an unsigned char of binary value 00101010,
   * findFirstSet(x, 2) will return 3.
   *
   * @param x
   *          The value whose first set bit is to be found.
   * @param n
   *          The searching starting from this bit, that is, searching in the
   *          bit range [n, BITS) of x.
   * @return The lowest position of the bit in x which is set to 1. If no such
   *         position is found, returns BITS. If n >= BITS, the behavour of the
   *         function is undefined.
   */
  public static int findFirstSet(byte x, int n) {
    assert ((0 <= n) && (n < BITS));

    return FIRST_SET[x & (~RANGE_MASK[n]) & FULL_BITS_MASK];
  }

  /**
   * Find the lowest unset bit (of value 0) in x.
   *
   * For example, x is an unsigned char of binary value 00101011,
   * findFirstUnset(x) will return 2.
   *
   * This function is equivalent to invert(x); return findFirstSet(x);
   *
   * @param x
   *          The value whose first set bit is to be found.
   * @return The lowest position of the bit in x which is set to 0. If all bits
   *         in x are 1s, returns BITS.
   */
  public static int findFirstUnset(byte x) {
    return FIRST_SET[(~x) & FULL_BITS_MASK];
  }

  /**
   * Find the lowest nset bit (of value 0) in x starting from the bit n, i.e, in
   * the bit range [n, BITS).
   *
   * For example, x is an unsigned char of binary value 00111011,
   * findFirstUnset(x, 3) will return 6.
   *
   * This function is equivalent to invert(x); return findFirstSet( x, n );
   *
   * @param x
   *          The value whose first set bit is to be found.
   * @param n
   *          The searching starting from this bit, that is, searching in the
   *          bit range [n, BITS) of x.
   * @return The lowest position of the bit in x which is set to 0. If no such
   *         position is found, returns BITS. If n >= BITS, the behavour of the
   *         function is undefined.
   */
  public static int findFirstUnset(byte x, int n) {
    assert ((0 <= n) && (n < BITS));

    return FIRST_SET[(~(x | RANGE_MASK[n])) & FULL_BITS_MASK];
  }

  /**
   * Find the highest set bit (of value 1) in x.
   *
   * For example, x is an unsigned char of binary value 00101101, findLastSet(x)
   * will return 5.
   *
   * In face, the value of the result is equal to the (int) log_2(x).
   *
   * @param x
   *          The value whose first set bit is to be found.
   * @return The highest position of the bit in x which is set to 1. If all bits
   *         in x are 0s, returns BITS.
   */
  public static int findLastSet(byte x) {
    return LAST_SET[x & FULL_BITS_MASK];
  }

  /**
   * Find the highest set bit (of value 1) in x starting from the bit 0 and
   * ending at the bit n (exclude), i.e., in the bit range [0, n).
   *
   * For example, x is an unsigned char of binary value 00101101, findLastSet(x,
   * 5) will return 3.
   *
   * @param x
   *          The value whose first set bit is to be found.
   * @param n
   *          The searching ending at this bit, that is, searching in the bit
   *          range [0, n) of x.
   * @return The highest position in the bit range [0, n) of x which is set to
   *         1. If no such position is found, returns BITS. If n == 0, returns
   *         BITS. If n == BITS, returns findLastSet(x). if n > BITS, the
   *         beheavour is undefined.
   */
  public static int findLastSet(byte x, int n) {
    assert ((0 <= n) && (n < BITS));

    return LAST_SET[x & RANGE_MASK[n] & FULL_BITS_MASK];
  }

  /**
   * Find the highest unset bit (of value 0) in x.
   *
   * For example, x is an unsigned char of binary value 11101011,
   * findLastUnset(x) will return 4.
   *
   * This function is equivalent to invert(x); return findLastSet(x);
   *
   * @param x
   *          The value whose first set bit is to be found.
   * @return The highest position of the bit in x which is set to 0. If all bits
   *         in x are 1s, return BITS.
   */
  public static int findLastUnset(byte x) {
    return LAST_SET[(~x) & FULL_BITS_MASK];
  }

  /**
   * Find the highest unset bit (of value 0) in x starting from the bit 0 and
   * ending at the bit n, i.e, in the bit range [0, n).
   *
   * For example, x is a unsigned char of binary value 10111011,
   * findLastUnset(x, 6) will return 2.
   *
   * This function is equivalent to invert(x); return findLastSet(x, n);
   *
   * @param x
   *          The value whose first set bit is to be found.
   * @param n
   *          The searching ending at this bit, that is, searching in the bit
   *          range [0, n) of x.
   * @return The lowest position in the bit range [0, n) of x which is set to 0.
   *         If no such position is found, returns BITS. If n == 0, returns
   *         BITS. If n > BITS, the behavour of the function is undefined.
   */
  public static int findLastUnset(byte x, int n) {
    assert ((0 <= n) && (n < BITS));

    return LAST_SET[(~x) & RANGE_MASK[n] & FULL_BITS_MASK];
  }

  /**
   * Test whether x is a subset of y, i.e., whether for every bit set in x, the
   * corresponding bit in y is also set.
   *
   * Note that if x == y, x is a subset of y, but it is not a "proper" subset of
   * y.
   *
   * @param x
   *          The value to be test.
   * @param y
   *          The other value to be test.
   * @return true if x is a subset of y; false otherwise.
   */
  public static boolean isSubsetOf(byte x, byte y) {
    int ix = (x & FULL_BITS_MASK);
    int iy = (y & FULL_BITS_MASK);
    return ((ix | iy) == iy);
  }

  /**
   * Test whether x is a proper subset of y, i.e., whether for every bit set in
   * x, the corresponding bit in y is also set, and there is at least on bit set
   * in y but the corresponding bit is not set in x (or simply, x is not equal
   * to y).
   *
   * @param x
   *          The value to be test.
   * @param y
   *          The other value to be test.
   * @return true if x is a proper subset of y; false otherwise.
   */
  public static boolean isProperSubsetOf(byte x, byte y) {
    int ix = (x & FULL_BITS_MASK);
    int iy = (y & FULL_BITS_MASK);
    return ((ix | iy) == iy) && (ix != iy);
  }

  /**
   * Lexically compare the bits.
   *
   * Returns an integer less than, equal to or greater than 0, if the bits of x
   * starting from position 0 to position BITS - 1 compares lexicographically
   * less than, equal to, or greater than the bits of y starting from position 0
   * to position BITS - 1.
   *
   * @param x
   *          The first operand of comparision.
   * @param y
   *          The second operand of comparision.
   * @return The result of lexically comparision, as described above.
   */
  public static int compare(byte x, byte y) {
    int ix = (x & FULL_BITS_MASK);
    int iy = (y & FULL_BITS_MASK);
    return (REVERSE[ix] - REVERSE[iy]);
  }

  /**
   * Determining whether the unsigned representation of an integer is a power of
   * 2. That is, wheter the bits of the integer has only one 1 and all rest bits
   * are 0s.
   *
   * Note that 0 is considered a power of 2, i.e., isPowerOfTwo(0) will returns
   * true.
   *
   * @param x
   *          The operand to be tested. Note that x is treated as a unsigned
   *          integer.
   * @return ture if x is a power of 2.
   */
  public static boolean isPowerOfTwo(byte x) {
    return (x & (x - 1)) == 0;
  }

  /**
   * Returns the floor of log_2(x). In other words, return the largest n such
   * that 2^n <= x < 2^{n+1}.
   *
   * @param x
   *          The operand. Note that x is treated as a unsigned integer.
   * @return The floor of log_2(x). If x is 0, the behaviour is undefined.
   */
  public static int getLog2Floor(byte x) {
    assert (x != 0);
    return LAST_SET[x & FULL_BITS_MASK];
  }

  /**
   * Returns the ceiling of log_2(x). In other words, return the smallest n such
   * that 2^{n-1} < x <= 2^{n}.
   *
   * @param x
   *          The operand. Note that x is treated as a unsigned integer.
   * @return The ceiling of log_2(x). If x is 0, the behaviour is undefined.
   */
  public static int getLog2Ceiling(byte x) {
    assert (x != 0);
    // note that (x & (x - 1)) == 0 means x is a power of 2
    return LAST_SET[x & FULL_BITS_MASK] + ((x & (x - 1)) != 0 ? 1 : 0);
  }

  /**
   * Returns the number of digits in the binary representation of a specified
   * unsigned integer.
   *
   * @param x
   *          The operand. Note that x is treated as a unsigned integer.
   * @return The number of digits in the binary representation of x. If x is 0,
   *         the function returns 1.
   */
  public static int getDigits2(byte x) {
    return (x == 0 ? 1 : LAST_SET[x & FULL_BITS_MASK] + 1);
  }

  /**
   * Returns the number of digits in the octal representation of a specified
   * unsigned integer.
   *
   * @param x
   *          The operand. Note that x is treated as a unsigned integer.
   * @return The number of digits in the octal representation of x. If x is 0,
   *         the function returns 1.
   */
  public static int getDigits8(byte x) {
    int digit2 = (x == 0 ? 1 : LAST_SET[x & FULL_BITS_MASK] + 1);
    return (digit2 + 2) / 3;
  }

  /**
   * Returns the number of digits in the decimal representation of a specified
   * unsigned integer.
   *
   * @param x
   *          The operand. Note that x is treated as a unsigned integer.
   * @return The number of digits in the decimal representation of x. If x is 0,
   *         the function returns 1.
   */
  public static int getDigits10(byte x) {
    // The fraction 643/2136 approximates log10(2) to 7 significant digits.
    int digit2 = (x == 0 ? 1 : LAST_SET[x & FULL_BITS_MASK] + 1);
    return (digit2 * 643) / 2136 + 1;
  }

  /**
   * Returns the number of digits in the hexadecimal representation of a
   * specified unsigned integer.
   *
   * @param x
   *          The operand. Note that x is treated as a unsigned integer.
   * @return The number of digits in the hexadecimal representation of x. If x
   *         is 0, the function returns 1.
   */
  public static int getDigits16(byte x) {
    int digit2 = (x == 0 ? 1 : LAST_SET[x & FULL_BITS_MASK] + 1);
    return (digit2 + 3) / 4;
  }

}