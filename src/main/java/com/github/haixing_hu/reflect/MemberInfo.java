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

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * Stores the information of member.
 *
 * @author Haixing Hu
 */
public final class MemberInfo implements Comparable<MemberInfo> {

  /**
   * The member.
   */
  public final Member member;

  /**
   * The depth of the member in the specified class.
   */
  public final int depth;

  /**
   * Constructs a {@link MemberInfo}.
   *
   * @param member
   *          a member.
   * @param depth
   *          the depth of the member in the specified class.
   */
  public MemberInfo(Member member, int depth) {
    this.member = requireNonNull("member", member);
    this.depth = depth;
  }

  /**
   * Compares this {@link MemberInfo} with the other one.
   * <p>
   * The function will firstly compare the depth of two {@link MemberInfo}, and
   * the one with shallower depth will be smaller; if the depths of two
   * {@link MemberInfo} are the same, the function then lexicographically
   * compare the names of the members of two {@link MemberInfo}.
   *
   * @param other
   *          the other {@link MemberInfo} object.
   */
  @Override
  public int compareTo(MemberInfo other) {
    if (other == null) {
      return + 1;
    } else if (this.depth != other.depth) {
      return this.depth - other.depth;
    } else {
      final String thisName = this.member.getName();
      final String otherName = other.member.getName();
      return thisName.compareTo(otherName);
    }
  }
}
