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
package com.github.haixing_hu.util;

import java.util.Locale;

import org.junit.Test;

import com.github.haixing_hu.util.LocaleUtils;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@link LocaleUtils} class.
 *
 * @author Haixing Hu
 */
public class LocaleUtilsTest {

  @Test
  public void testFromPosixLocale() {
    Locale actual = null;
    Locale expected = null;

    actual = LocaleUtils.fromPosixLocale(null);
    assertEquals(null, actual);

    actual = LocaleUtils.fromPosixLocale("");
    assertEquals(null, actual);

    actual = LocaleUtils.fromPosixLocale("XXXXX");
    assertEquals(null, actual);

    actual = LocaleUtils.fromPosixLocale("zh_abc");
    expected = new Locale("zh", "", "abc");
    assertEquals(expected, actual);

    actual = LocaleUtils.fromPosixLocale("zh_CN");
    expected = new Locale("zh", "CN");
    assertEquals(expected, actual);

    actual = LocaleUtils.fromPosixLocale("en_us");
    expected = new Locale("en", "us");
    assertEquals(expected, actual);

    actual = LocaleUtils.fromPosixLocale("zh_CN_variant");
    expected = new Locale("zh", "CN", "variant");
    assertEquals(expected, actual);

    actual = LocaleUtils.fromPosixLocale("zh_variant");
    expected = new Locale("zh", "", "variant");
    assertEquals(expected, actual);

    actual = LocaleUtils.fromPosixLocale("zh");
    expected = new Locale("zh");
    assertEquals(expected, actual);
  }

  public void testToPosixLocale() {
    Locale locale = null;

    assertEquals(null, LocaleUtils.toPosixLocale(null));

    locale = new Locale("");
    assertEquals("", LocaleUtils.toPosixLocale(locale));

    locale = new Locale("zh");
    assertEquals("zh", LocaleUtils.toPosixLocale(locale));

    locale = new Locale("zh", "cn");
    assertEquals("zh_CN", LocaleUtils.toPosixLocale(locale));

    locale = new Locale("zh", "", "var");
    assertEquals("zh_var", LocaleUtils.toPosixLocale(locale));
  }
}
