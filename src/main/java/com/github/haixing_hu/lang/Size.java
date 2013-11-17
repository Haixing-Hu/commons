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

import java.util.LinkedList;

import javax.annotation.Nullable;

/**
 * This class defines the approximate size in bytes of common data types.
 *
 * @author Haixing Hu
 */
public final class Size {

  /**
   * The size in bytes of a <code>boolean</code> variable in this JVM.
   */
  public static final long BOOL   = 1L;

  /**
   * The size in bytes of a <code>char</code> variable in this JVM.
   */
  public static final long CHAR      = 2L;

  /**
   * The size in bytes of a <code>byte</code> variable in this JVM.
   */
  public static final long BYTE      = 1L;

  /**
   * The size in bytes of a <code>short</code> variable in this JVM.
   */
  public static final long SHORT     = 2L;

  /**
   * The size in bytes of a <code>int</code> variable in this JVM.
   */
  public static final long INT       = 4L;

  /**
   * The size in bytes of a <code>long</code> variable in this JVM.
   */
  public static final long LONG      = 8L;

  /**
   * The size in bytes of a <code>float</code> variable in this JVM.
   */
  public static final long FLOAT     = 4L;

  /**
   * The size in bytes of a <code>double</code> variable in this JVM.
   */
  public static final long DOUBLE    = 8L;

  /**
   * The size in bytes of an object reference variable in this JVM.
   */
  public static final long REFERENCE = (SystemUtils.IS_JAVA_64BIT ? 8L : 4L);

  /**
   * The size in bytes of a <code>Date</code> variable in this JVM.
   */
  public static final long DATE      = 8L;

  /**
   * The size in bytes of a entry object of the {@link LinkedList} class.
   */
  public static final long LINKED_LIST_ENTRY = 3 * REFERENCE;

  /**
   * The size in bytes of an empty {@link LinkedList} object.
   */
  public static final long LINKED_LIST = REFERENCE + 2 * INT + LINKED_LIST_ENTRY;

  /**
   * Gets the size in bytes of a <code>String</code> object.
   *
   * @param str
   *          a reference to a <code>String</code> object, which could be null.
   * @return the size in bytes of the specified <code>String</code> object; or 0
   *         if the reference is <code>null</code>.
   */
  public static long of(@Nullable final String str) {
    return (str == null ? 0 : str.length() * CHAR);
  }
}
