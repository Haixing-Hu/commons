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
package com.github.haixing_hu.math;

import javax.annotation.concurrent.ThreadSafe;


/**
 * A class providing functions for bit manipulation of {@code short}.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class ShortBit {

    public static final int BITS            = 16;
    public static final int HALF_BITS       = 8;
    public static final int HALF_BITS_MASK  = 0x00FF;
    public static final int FULL_BITS_MASK  = 0xFFFF;
    public static final int MSB_MASK        = 0x8000;

    public static final int RANGE_MASK[]    = {
            0x0000, 0x0001, 0x0003, 0x0007, 0x000F, 0x001F, 0x003F, 0x007F,
            0x00FF, 0x01FF, 0x03FF, 0x07FF, 0x0FFF, 0x1FFF, 0x3FFF, 0x7FFF,
            0xFFFF
    };

    public static final int BIT_MASK[]      = {
            0x0001, 0x0002, 0x0004, 0x0008, 0x0010, 0x0020, 0x0040, 0x0080,
            0x0100, 0x0200, 0x0400, 0x0800, 0x1000, 0x2000, 0x4000, 0x8000
    };

  /**
   * Return the value of x as if x is an unsigned short.
   *
   * Note that this function is sometimes useful since there is no unsigned type
   * in Java.
   *
   * @param x
   *          A short value which should be treated as an unsigned short.
   * @return
   *
   *         The value of x as if x is an unsigned short. More precisely, if x
   *         is non-negative, returns the value of x; otherwise, returns the
   *         value of (256 + x).
   */
  public static int asUnsigned(short x) {
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
   * @return A value of type {@code short} whose lowest n bits are the same
   *         of the lowest n bits of x, and other bits are 0. If n == 0, the
   *         function returns 0. If n == BITS, the function returns x. If n < 0
   *         or n > BITS, the behaviour is undefined.
   */
  public static short low(short x, int n) {
    assert ((0 <= n) && (n <= BITS));
    return (short) (x & RANGE_MASK[n]);
  }

  /**
   * Returns the highest n bits of x.
   *
   * @param x
   *          The value.
   * @param n
   *          The amount of lowest bits to be returned. It must be in the range
   *          of [0, BITS].
   * @return A value of type {@code short} whose highest n bits are the
   *         same of the highest n bits of x, and other bits are 0. If n == 0,
   *         the function returns 0. If n == BITS, the function returns x. If n
   *         < 0 or n > BITS, the behaviour is undefined.
   */
  public static short high(short x, int n) {
    assert ((0 <= n) && (n <= BITS));
    return (short) ((x & FULL_BITS_MASK) >>> (BITS - n));
  }

  /**
   * Returns the lower half bits of x.
   *
   * @param x
   *          The value.
   * @return A value of type {@code short} whose lower half bits are the
   *         same of the lower half bits of x, and other bits are 0. In fact,
   *         this function returns low(x, HALF_BITS).
   */
  public static short lowHalf(short x) {
    return (short) (x & HALF_BITS_MASK);
  }

  /**
   * Returns the higher half bits of x.
   *
   * @param x
   *          The value.
   * @return A value of type {@code short} whose higher half bits are the
   *         same of the higher half bits of x, and other bits are 0. In fact,
   *         this function returns high(x, HALF_BITS).
   */
  public static short highHalf(short x) {
    return (short) ((x & FULL_BITS_MASK) >>> HALF_BITS);
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
  public static short rotate(short x, int n) {
    assert ((0 <= n) && (n <= BITS));
    int m = BITS - n;
    int ix = (x & FULL_BITS_MASK);
    int ixl = (ix & RANGE_MASK[n]);
    int ixh = (ix >>> m);
    return (short) ((ixl << m) | ixh);
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
  public static short rotateHalf(short x) {
    int ix = (x & FULL_BITS_MASK);
    int ixl = (ix & HALF_BITS_MASK);
    int ixh = (ix >>> HALF_BITS);
    return (short) ((ixl << HALF_BITS) | ixh);
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
  public static short swap(short x, int i, int j) {
    assert ((0 <= i) && (i < BITS) && (0 <= j) && (j < BITS));

    // Use a trick from http://graphics.stanford.edu/~seander/bithacks.html
    int ix = (x & FULL_BITS_MASK);
    int iy = (((ix >>> i) ^ (ix >>> j)) & 1);
    return (short) (ix ^ ((iy << i) | (iy << j)));
  }

  /**
   * Swap the bit range [i, i+n) and the bit range [j, j+n) of x, return the new
   * value.
   *
   * For example, assume short is unsigned char, x is 00101111 in binary, i = 1,
   * j = 5, n = 3, swapRange(x, i, j, n) will swap the 1,2,3 bit of x with the
   * 5,6,7 bit of x, or in other words, swap the first parathesis and the second
   * parathesis in the following representation:
   *
   * (001)0(111)1
   *
   * and gets
   *
   * (111)0(001)1
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
  public static short swapRange(short x, int i, int j, int n) {
    assert ((0 <= i) && (i < BITS) && (0 <= j) && (j < BITS) && (n > 0)
        && (i + n <= BITS) && (j + n <= BITS) && ((i + n <= j) || (j + n <= i)));

    // Use a trick from http://graphics.stanford.edu/~seander/bithacks.html
    int ix = (x & FULL_BITS_MASK);
    int iy = (((ix >>> i) ^ (ix >>> j)) & RANGE_MASK[n]);
    return (short) (ix ^ ((iy << i) | (iy << j)));
  }

  /**
   * Reverse the bits in the x, that is, the bit at position i in the result is
   * the bit at position (BITS - 1 - i) in the x.
   *
   * For example, short is a binary unsigned char of 01111011 (note that the
   * leading 0 are reserved). reverse(x) gets 11011110.
   *
   * @param x
   *          The value to be reversed.
   * @return A new value by reversing the bits of x.
   */
  public static short reverse(short x) {
    int ix = (x & FULL_BITS_MASK);
    int x0 = ByteBit.REVERSE[ix & ByteBit.FULL_BITS_MASK];
    int x1 = ByteBit.REVERSE[ix >>> ByteBit.BITS];

    return (short) ((x1 << ByteBit.BITS) | x0);
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
  public static short merge(short x, short y, short mask) {
    // Use a trick from http://graphics.stanford.edu/~seander/bithacks.html
    return (short) (x ^ ((x ^ y) & mask));
  }

  /**
   * Sets all the bits of x to 1. In fact, this function set the x to be ~T(0).
   *
   * @param x
   *          The value to be modified.
   * @return The result of operation.
   */
  public static short set(short x) {
    return (short) FULL_BITS_MASK;
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
  public static short set(short x, int i) {
    assert ((0 <= i) && (i < BITS));
    return (short) (x | BIT_MASK[i]);
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
  public static short setMsb(short x) {
    return (short) (x | MSB_MASK);
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
  public static short setLow(short x, int n) {
    assert ((0 <= n) && (n < BITS));
    return (short) (x | RANGE_MASK[n]);
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
  public static short setHigh(short x, int n) {
    assert ((0 <= n) && (n < BITS));
    return (short) (x | (~RANGE_MASK[BITS - n]));
  }

  /**
   * Sets all the bits of x to 0. In fact, this function set the x to T(0).
   *
   * @param x
   *          The value to be modified.
   * @return The result of operation.
   */
  public static short unset(short x) {
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
  public static short unset(short x, int i) {
    assert ((0 <= i) && (i < BITS));
    return (short) (x & (~BIT_MASK[i]));
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
  public static short unsetMsb(short x) {
    return (short) (x & (~MSB_MASK));
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
  public static short unsetLow(short x, int n) {
    assert ((0 <= n) && (n < BITS));
    return (short) (x & (~RANGE_MASK[n]));
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
  public static short unsetHigh(short x, int n) {
    assert ((0 <= n) && (n < BITS));
    return (short) (x & RANGE_MASK[BITS - n]);
  }

  /**
   * Inverts all the bits of x, i.e., change 1s for 0s, and 0s for 1s.
   *
   * @param x
   *          The value to be modified.
   * @return The result of operation.
   */
  public static short invert(short x) {
    return (short) (~x);
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
  public static short invert(short x, int i) {
    assert ((0 <= i) && (i < BITS));
    return (short) (x ^ BIT_MASK[i]);
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
  public static short invertMsb(short x) {
    return (short) (x ^ MSB_MASK);
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
  public static boolean test(short x, int i) {
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
  public static boolean testMsb(short x) {
    return ((x & MSB_MASK) != 0);
  }

  /**
   * Count the amount of bits set in the value of x.
   *
   * @param x
   *          The value whose bits are to be counted.
   * @return The amount of bits set in the x.
   */
  public static int count(short x) {
    int ix = (x & FULL_BITS_MASK);
    int x0 = (ix & ByteBit.FULL_BITS_MASK);
    int x1 = (ix >>> ByteBit.BITS);

    return ByteBit.COUNT[x0] + ByteBit.COUNT[x1];
  }

  /**
   * Test whether all bits of x are set to 1.
   *
   * @param x
   *          The value to be test.
   * @return true if all bits of x are set to 1, false otherwise.
   */
  public static boolean hasAll(short x) {
    return (x == (short) FULL_BITS_MASK);
  }

  /**
   * Test whether there is any bit of x which is set to 1.
   *
   * @param x
   *          The value to be test.
   * @return true if there is at least one bit of x which is set to 1, false
   *         otherwise.
   */
  public static boolean hasAny(short x) {
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
  public static boolean hasNone(short x) {
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
  public static int findFirstSet(short x) {
    return findFirstSetImpl(x & FULL_BITS_MASK);
  }

  static int findFirstSetImpl(int x) {
    int x0 = (x & HALF_BITS_MASK);
    if (x0 == 0) {
      return ByteBit.FIRST_SET[x >>> HALF_BITS] + HALF_BITS;
    } else {
      return ByteBit.FIRST_SET[x0];
    }
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
   *         position is found, returns -1. If n >= BITS, the behavour of the
   *         function is undefined.
   */
  public static int findFirstSet(short x, int n) {
    assert ((0 <= n) && (n < BITS));

    // clear the lowest n bits of x
    int x0 = (x & (~RANGE_MASK[n]) & FULL_BITS_MASK);
    return findFirstSetImpl(x0);
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
   *         in x are 1s, returns -1.
   */
  public static int findFirstUnset(short x) {
    // inverse x.
    int x0 = ((~x) & FULL_BITS_MASK);
    return findFirstSetImpl(x0);
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
   *         position is found, returns -1. If n >= BITS, the behavour of the
   *         function is undefined.
   */
  public static int findFirstUnset(short x, int n) {
    assert ((0 <= n) && (n < BITS));

    int x0 = ((~(x | RANGE_MASK[n])) & FULL_BITS_MASK);
    return findFirstSetImpl(x0);
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
   *         in x are 0s, returns -1.
   */
  public static int findLastSet(short x) {
    return findLastSetImpl(x & FULL_BITS_MASK);
  }

  static int findLastSetImpl(int x) {
    if (x == 0) {
      return BITS;
    } else {
      int x0 = (x >>> HALF_BITS);
      if (x0 == 0) { // find in the lower 16 bits.
        return ByteBit.LAST_SET[x & HALF_BITS_MASK];
      } else { // find in the higher 16 bits.
        return ByteBit.LAST_SET[x0] + HALF_BITS;
      }
    }
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
   *         -1. If n == BITS, returns findLastSet(x). if n > BITS, the
   *         beheavour is undefined.
   */
  public static int findLastSet(short x, int n) {
    assert ((0 <= n) && (n < BITS));

    int x0 = (x & RANGE_MASK[n] & FULL_BITS_MASK);
    return findLastSetImpl(x0);
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
   *         in x are 1s, return -1.
   */
  public static int findLastUnset(short x) {
    int x0 = ((~x) & FULL_BITS_MASK);
    return findLastSetImpl(x0);
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
   *         If no such position is found, returns -1. If n == 0, returns -1. If
   *         n > BITS, the behavour of the function is undefined.
   */
  public static int findLastUnset(short x, int n) {
    assert ((0 <= n) && (n < BITS));

    int x0 = ((~x) & RANGE_MASK[n] & FULL_BITS_MASK);
    return findLastSetImpl(x0);
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
  public static boolean isSubsetOf(short x, short y) {
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
  public static boolean isProperSubsetOf(short x, short y) {
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
  public static int compare(short x, short y) {
    return (reverse(x) - reverse(y));
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
  public static boolean isPowerOfTwo(short x) {
    return (x & (x - 1)) == 0;
  }

  /**
   * Returns the floor of log_2(x). In other words, return the largest n such
   * that 2^n <= x < 2^{n+1}.
   *
   * @param x
   *          The operand. Note that x is treated as a unsigned integer.
   * @return The floor of log_2(x). If x is 0, returns -1.
   */
  public static int getLog2Floor(short x) {
    assert (x != 0);
    return findLastSet(x);
  }

  /**
   * Returns the ceiling of log_2(x). In other words, return the smallest n such
   * that 2^{n-1} < x <= 2^{n}.
   *
   * @param x
   *          The operand. Note that x is treated as a unsigned integer.
   * @return The ceiling of log_2(x). If x is 0, returns -1.
   */
  public static int getLog2Ceiling(short x) {
    assert (x != 0);
    // note that (x & (x - 1)) == 0 means x is a power of 2
    int ix = (x & FULL_BITS_MASK);
    return findLastSetImpl(ix) + ((ix & (ix - 1)) != 0 ? 1 : 0);
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
  public static int getDigits2(short x) {
    return (x == 0 ? 1 : findLastSetImpl(x & FULL_BITS_MASK) + 1);
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
  public static int getDigits8(short x) {
    int digit2 = (x == 0 ? 1 : findLastSetImpl(x & FULL_BITS_MASK) + 1);
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
  public static int getDigits10(short x) {
    // The fraction 643/2136 approximates log10(2) to 7 significant digits.
    int digit2 = (x == 0 ? 1 : findLastSetImpl(x & FULL_BITS_MASK) + 1);
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
  public static int getDigits16(short x) {
    int digit2 = (x == 0 ? 1 : findLastSetImpl(x & FULL_BITS_MASK) + 1);
    return (digit2 + 3) / 4;
  }
}