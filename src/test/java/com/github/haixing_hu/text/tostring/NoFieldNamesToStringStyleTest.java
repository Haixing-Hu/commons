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

package com.github.haixing_hu.text.tostring;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import com.github.haixing_hu.text.tostring.NoFieldNameToStringStyle;
import com.github.haixing_hu.text.tostring.ToStringBuilder;
import com.github.haixing_hu.text.tostring.ToStringStyle;
import com.github.haixing_hu.text.tostring.ToStringStyleTest.Person;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for the ToStringBuilder and NoFieldNamesToStringStyle class.
 *
 * @author Haixing Hu
 */
public class NoFieldNamesToStringStyleTest {

  private final Integer base = new Integer(5);
  private final String baseStr = base.getClass().getName() + "@"
      + Integer.toHexString(System.identityHashCode(base));

  private static final ToStringStyle STYLE = NoFieldNameToStringStyle.INSTANCE;

  @Test
  public void testBlank() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    assertEquals(baseStr + "[]", builder.reset(base).toString());
  }

  @Test
  public void testAppendSuper() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);

    assertEquals(baseStr + "[]",
        builder.reset(base).appendSuper("Integer@8888[]").toString());

    assertEquals(baseStr + "[<null>]",
        builder.reset(base).appendSuper("Integer@8888[<null>]").toString());

    assertEquals(
        baseStr + "[hello]",
        builder.reset(base).appendSuper("Integer@8888[]").append("a", "hello").toString());

    assertEquals(
        baseStr + "[<null>,hello]",
        builder.reset(base).appendSuper("Integer@8888[<null>]").append("a",
            "hello").toString());

    assertEquals(baseStr + "[hello]",
        builder.reset(base).appendSuper(null).append("a", "hello").toString());
  }

  @Test
  public void testObject() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    final Integer i3 = new Integer(3);
    final Integer i4 = new Integer(4);

    assertEquals(baseStr + "[<null>]",
        builder.reset(base).append((Object) null).toString());

    assertEquals(baseStr + "[3]", builder.reset(base).append(i3).toString());

    assertEquals(baseStr + "[<null>]",
        builder.reset(base).append("a", (Object) null).toString());

    assertEquals(baseStr + "[3]",
        builder.reset(base).append("a", i3).toString());

    assertEquals(baseStr + "[3,4]",
        builder.reset(base).append("a", i3).append("b", i4).toString());

    assertEquals(baseStr + "[<Integer>]",
        builder.reset(base).append("a", i3, false).toString());

    assertEquals(
        baseStr + "[<size=0>]",
        builder.reset(base).append("a", new ArrayList<Byte>(), false).toString());

    assertEquals(baseStr + "[[]]",
        builder.reset(base).append("a", new ArrayList<Byte>(), true).toString());

    assertEquals(
        baseStr + "[<size=0>]",
        builder.reset(base).append("a", new HashMap<Byte, Byte>(), false).toString());

    assertEquals(
        baseStr + "[{}]",
        builder.reset(base).append("a", new HashMap<Byte, Byte>(), true).toString());

    assertEquals(
        baseStr + "[<size=0>]",
        builder.reset(base).append("a", (Object) new String[0], false).toString());

    assertEquals(
        baseStr + "[{}]",
        builder.reset(base).append("a", (Object) new String[0], true).toString());
  }

  @Test
  public void testPerson() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    final Person p = new Person();
    p.name = "Ron Paul";
    p.age = 72;
    p.smoker = false;
    final String pBaseStr = p.getClass().getName() + "@"
        + Integer.toHexString(System.identityHashCode(p));

    assertEquals(
        pBaseStr + "[Ron Paul,72,false]",
        builder.reset(p).append("name", p.name).append("age", p.age).append(
            "smoker", p.smoker).toString());
  }

  @Test
  public void testLong() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    assertEquals(baseStr + "[3]", builder.reset(base).append(3L).toString());

    assertEquals(baseStr + "[3]",
        builder.reset(base).append("a", 3L).toString());

    assertEquals(baseStr + "[3,4]",
        builder.reset(base).append("a", 3L).append("b", 4L).toString());
  }

  @Test
  public void testObjectArray() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    Object[] array = new Object[] { null, base, new int[] { 3, 6 } };

    assertEquals(baseStr + "[{<null>,5,{3,6}}]",
        builder.reset(base).append(array).toString());

    assertEquals(baseStr + "[{<null>,5,{3,6}}]",
        builder.reset(base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[<null>]",
        builder.reset(base).append(array).toString());

    assertEquals(baseStr + "[<null>]",
        builder.reset(base).append((Object) array).toString());
  }

  @Test
  public void testLongArray() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    long[] array = new long[] { 1, 2, - 3, 4 };
    assertEquals(baseStr + "[{1,2,-3,4}]",
        builder.reset(base).append(array).toString());

    assertEquals(baseStr + "[{1,2,-3,4}]",
        builder.reset(base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[<null>]",
        builder.reset(base).append(array).toString());

    assertEquals(baseStr + "[<null>]",
        builder.reset(base).append((Object) array).toString());
  }

  @Test
  public void testLongArrayArray() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    long[][] array = new long[][] { { 1, 2 }, null, { 5 } };

    assertEquals(baseStr + "[{{1,2},<null>,{5}}]",
        builder.reset(base).append(array).toString());

    assertEquals(baseStr + "[{{1,2},<null>,{5}}]",
        builder.reset(base).append((Object) array).toString());
    array = null;
    assertEquals(baseStr + "[<null>]",
        builder.reset(base).append(array).toString());
    assertEquals(baseStr + "[<null>]",
        builder.reset(base).append((Object) array).toString());
  }

}
