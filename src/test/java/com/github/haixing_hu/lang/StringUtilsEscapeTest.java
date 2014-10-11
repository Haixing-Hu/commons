/******************************************************************************
 *
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
 ******************************************************************************/

package com.github.haixing_hu.lang;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for the {@link StringUitls#escape()} function.
 *
 * @author Haixing Hu
 */
public class StringUtilsEscapeTest {

  @Test
  public void testEscapeZero() {
    final String str = "\\hello, \\ world!";
    final String result = StringUtils.escape(str, '\\');
    assertEquals("\\\\hello, \\\\ world!", result);
  }

  @Test
  public void testEscapeOne() {
    final String str1 = "\\'hello, \\ world!'";
    final String result1 = StringUtils.escape(str1, '\\', '\'');
    assertEquals("\\\\\\'hello, \\\\ world!\\'", result1);

    final String str2 = "\\'hello, \\ world!'";
    final String result2 = StringUtils.escape(str2, '\\', '\\');
    assertEquals("\\\\'hello, \\\\ world!'", result2);
  }

  @Test
  public void testEscapeTwo() {
    final String str1 = "\\'he%llo, \\ wo%rld!'";
    final String result1 = StringUtils.escape(str1, '\\', '\'', '%');
    assertEquals("\\\\\\'he\\%llo, \\\\ wo\\%rld!\\'", result1);

    final String str2 = "\\'he%llo, \\ wo%rld!'";
    final String result2 = StringUtils.escape(str2, '\\', '\'', '\'');
    assertEquals("\\\\\\'he%llo, \\\\ wo%rld!\\'", result2);

    final String str3 = "\\'he%llo, \\ wo%rld!'";
    final String result3 = StringUtils.escape(str3, '\\', '\'', '\\');
    assertEquals("\\\\\\'he%llo, \\\\ wo%rld!\\'", result3);

    final String str4 = "\\'he%llo, \\ wo%rld!'";
    final String result4 = StringUtils.escape(str4, '\\', '\\', '\'');
    assertEquals("\\\\\\'he%llo, \\\\ wo%rld!\\'", result4);

    final String str5 = "\\'he%llo, \\ wo%rld!'";
    final String result5 = StringUtils.escape(str5, '\\', '\\', '\\');
    assertEquals("\\\\'he%llo, \\\\ wo%rld!'", result5);
  }

  @Test
  public void testEscapeThree() {
    final String str1 = "\\'he%ll#o, \\ wo%rl#d!'";
    final String result1 = StringUtils.escape(str1, '\\', '\'', '%', '#');
    assertEquals("\\\\\\'he\\%ll\\#o, \\\\ wo\\%rl\\#d!\\'", result1);

    final String str2 = "\\'he%ll#o, \\ wo%rl#d!'";
    final String result2 = StringUtils.escape(str2, '\\', '\'', '\'', '\'');
    assertEquals("\\\\\\'he%ll#o, \\\\ wo%rl#d!\\'", result2);

    final String str3 = "\\'he%ll#o, \\ wo%rl#d!'";
    final String result3 = StringUtils.escape(str3, '\\', '\'', '\\', '#');
    assertEquals("\\\\\\'he%ll\\#o, \\\\ wo%rl\\#d!\\'", result3);

    final String str4 = "\\'he%ll#o, \\ wo%rl#d!'";
    final String result4 = StringUtils.escape(str4, '\\', '\\', '\'', '\'');
    assertEquals("\\\\\\'he%ll#o, \\\\ wo%rl#d!\\'", result4);

    final String str5 = "\\'he%ll#o, \\ wo%rl#d!'";
    final String result5 = StringUtils.escape(str5, '\\', '\\', '\\', '\\');
    assertEquals("\\\\'he%ll#o, \\\\ wo%rl#d!'", result5);
  }

}
