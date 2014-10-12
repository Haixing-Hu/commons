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
package com.github.haixing_hu.reflect;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

/**
 * Defines the options used in reflection operations.
 *
 * @author Haixing Hu
 */
public class Option {

  /**
   * Indicates that the operation is also applied to the members declared in the
   * ancestor class or ancestor interfaces of the specified class.
   */
  public static final int ANCESTOR = 0x0001;

  /**
   * Indicates that the operation is applied to the static members.
   */
  public static final int STATIC = 0x0002;

  /**
   * Indicates that the operation is applied to the non-static members.
   */
  public static final int NON_STATIC = 0x0004;

  /**
   * Indicates that the operation is applied to the private accessible members.
   * <p>
   * Note that a member is private accessible if it satisfies any of the
   * following conditions:
   * <ul>
   * <li>It is declared in the specified class as a private member.</li>
   * <li>It is declared in an ancestor class of the specified class as a private
   * member.</li>
   * </ul>
   */
  public static final int PRIVATE = 0x0008;

  /**
   * Indicates that the operation is applied to the package accessible members.
   * <p>
   * Note that a member is package accessible if it satisfies any of the
   * following conditions:
   * <ul>
   * <li>It is declared in the specified class as a member without access
   * modifier.</li>
   * <li>It is declared in an ancestor class of the specified class as a member
   * without access modifier.</li>
   * <li>It is declared in an ancestor class of the specified class as a
   * non-private member, and the ancestor class is declared without access
   * modifier.</li>
   * </ul>
   */
  public static final int PACKAGE = 0x0010;

  /**
   * Indicates that the operation is applied to the protected accessible
   * members.
   * <p>
   * Note that a member is protected accessible if it satisfies any of the
   * following conditions:
   * <ul>
   * <li>It is declared in the specified class as a protected member.</li>
   * <li>It is declared in an ancestor class of the specified class as a
   * protected member, and the ancestor class is declared with a public
   * modifier.</li>
   * </ul>
   */
  public static final int PROTECTED = 0x0020;

  /**
   * Indicates that the operation is applied to the public accessible members.
   * <p>
   * Note that a member is public accessible if it satisfies any of the
   * following conditions:
   * <ul>
   * <li>It is declared in the specified class as a public member.</li>
   * <li>It is declared in an ancestor class of the specified class as a public
   * member, and the ancestor class is declared with a public modifier.</li>
   */
  public static final int PUBLIC = 0x0040;

  /**
   * Indicates that the operation will try to utility the serialization
   * mechanics of Java.
   * <p>
   * This option is only useful in the invoking of default constructors.
   */
  public static final int SERIALIZATION = 0x0080;

  /**
   * Indicates that the operation is applied to private, package, protected,
   * and public members.
   */
  public static final int ALL_ACCESS = PRIVATE | PACKAGE | PROTECTED | PUBLIC;

  /**
   * Indicates that the operation is applied to all members, including static
   * members, non-public members, and those members declared in the ancestor
   * class or ancestor interfaces of the specified class.
   */
  public static final int ALL = ANCESTOR | STATIC | NON_STATIC | ALL_ACCESS | SERIALIZATION;

  /**
   * The options for Java bean's getter/setter methods.
   */
  public static final int BEAN_METHOD = ANCESTOR | NON_STATIC | PUBLIC;

  /**
   * Indicates that the options is applied to static or non-static public members of the
   * specified class or its super classes/interfaces.
   */
  public static final int ANCESTOR_PUBLIC = ANCESTOR | STATIC | NON_STATIC | PUBLIC;

  /**
   * The default options, which is only applied to non-static members declared
   * in the specified class, with all possible accessibility.
   */
  public static final int DEFAULT = NON_STATIC | ALL_ACCESS | SERIALIZATION;

  /**
   * Tests whether a member of a class satisfies the specified options.
   *
   * @param cls
   *          a class.
   * @param member
   *          a member of the class.
   * @param options
   *          the options.
   * @return {@code true} if the member of the class satisfies the options;
   *         {@code false} otherwise.
   */
  public static boolean satisfy(Class<?> cls, Member member, int options) {
    if (options == ALL) {
      return true;
    }
    if (((options & Option.ANCESTOR) == 0)
        && (! cls.equals(member.getDeclaringClass()))) {
      return false;
    }
    final int mod = member.getModifiers();
    final int declClsMod = member.getDeclaringClass().getModifiers();
    if ((options & Option.STATIC) == 0) {
       if (Modifier.isStatic(mod)) {
         return false;
       }
    }
    if ((options & Option.NON_STATIC) == 0) {
      if (! Modifier.isStatic(mod)) {
        return false;
      }
    }
    if ((options & Option.PRIVATE) == 0) {
      if (Modifier.isPrivate(mod)) {
        return false;
      }
    }
    if ((options & Option.PACKAGE) == 0) {
      if (MemberUtils.isPackage(mod)) {
        return false;
      } else if ((! Modifier.isPrivate(mod))
          && MemberUtils.isPackage(declClsMod)) {
        return false;
      }
    }
    if ((options & Option.PROTECTED) == 0) {
      if (Modifier.isProtected(mod) && Modifier.isPublic(declClsMod)) {
        return false;
      }
    }
    if ((options & Option.PUBLIC) == 0) {
      if (Modifier.isPublic(mod) && Modifier.isPublic(declClsMod)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Converts an options to a string representation.
   *
   * @param options the options.
   * @return the string representation of the options.
   */
  public static String toString(int options) {
    final StringBuilder builder = new StringBuilder();
    boolean first = true;
    if ((options & ANCESTOR) != 0) {
      builder.append("ancestor");
      first = false;
    }
    if ((options & STATIC) != 0) {
      if (! first) {
        builder.append(',');
      }
      builder.append("static");
      first = false;
    }
    if ((options & NON_STATIC) != 0) {
      if (! first) {
        builder.append(',');
      }
      builder.append("non-static");
      first = false;
    }
    if ((options & PRIVATE) != 0) {
      if (! first) {
        builder.append(',');
      }
      builder.append("private");
      first = false;
    }
    if ((options & PACKAGE) != 0) {
      if (! first) {
        builder.append(',');
      }
      builder.append("package");
      first = false;
    }
    if ((options & PROTECTED) != 0) {
      if (! first) {
        builder.append(',');
      }
      builder.append("protected");
      first = false;
    }
    if ((options & PUBLIC) != 0) {
      if (! first) {
        builder.append(',');
      }
      builder.append("public");
      first = false;
    }
    return builder.toString();
  }
}
