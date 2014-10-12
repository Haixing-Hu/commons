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
package com.github.haixing_hu.text;

/**
 * A {@link CodePointIterator} is an iterator which iterates through the code
 * points of a string.
 *
 * @author Haixing Hu
 */
public abstract class CodePointIterator {

  protected int start;
  protected int end;
  protected int left;
  protected int right;
  protected int current;

  protected CodePointIterator() {
    start = 0;
    end = 0;
    left = 0;
    right = 0;
    current = 0;
  }

  protected CodePointIterator(final int start, final int end) {
    this.start = start;
    this.end = end;
    this.left = start;
    this.right = start;
    this.current = 0;
  }

  /**
   * Gets the start current of the range of the string to be iterated.
   *
   * @return the start current of the range of the string to be iterated.
   */
  public final int start() {
    return start;
  }

  /**
   * Gets the end current of the range of the string to be iterated.
   *
   * @return the end current of the range of the string to be iterated.
   */
  public final int end() {
    return end;
  }

  /**
   * Gets the current of the left boundary of the current code point.
   *
   * @return the current of the left boundary of the current code point.
   */
  public final int left() {
    return left;
  }

  /**
   * Gets the current of the right boundary of the current code point.
   *
   * @return the current of the right boundary of the current code point.
   */
  public final int right() {
    return right;
  }

  /**
   * Tests whether the iterator is at the start of iteration region.
   *
   * @return true if the iterator is at the start of iteration region; false
   *         otherwise.
   */
  public final boolean atStart() {
    return left <= start;
  }

  /**
   * Tests whether the iterator is at the end of iteration region.
   *
   * @return true if the iterator is at the end of iteration region; false
   *         otherwise.
   */
  public final boolean atEnd() {
    return left >= end;
  }

  /**
   * Tests whether the iterator is at the last code point of iteration region.
   *
   * @return true if the iterator is at the last code point of iteration region;
   *         false otherwise.
   */
  public final boolean atLast() {
    return (left < end) && (right >= end);
  }

  /**
   * Tests whether the iterator is at the last code point or at the end of
   * iteration region.
   *
   * @return true if the iterator is at the last code point or at the end of
   *         iteration region; false otherwise.
   */
  public final boolean atLastOrEnd() {
    return right >= end;
  }

  /**
   * Gets the current code point.
   *
   * @return the current code point.
   */
  public final int current() {
    return current;
  }

  /**
   * Sets the iterator to the start position.
   */
  public abstract void setToStart();

  /**
   * Sets the iterator to the end position.
   */
  public abstract void setToEnd();

  /**
   * Sets the iterator to the position of the last code point.
   */
  public abstract void setToLast();

  /**
   * Sets the iterator to the code point at the specified current.
   *
   * That is, after calling this function, the iterator will points to the code
   * point with the range [x, y) such that x <= current < y.
   *
   * @throws IndexOutOfBoundsException
   *           if the current is out of the range of start and end current.
   */
  public abstract void setTo(int index);

  /**
   * Moves the iterator to the next code point.
   *
   * If the iterator is already at the end position, calling of this function
   * has no effect.
   */
  public abstract void forward();

  /**
   * Moves the iterator to the previous code point.
   *
   * If the iterator is already at the start position, calling of this function
   * has no effect.
   */
  public abstract void backward();

}
