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
package com.github.haixing_hu.config.impl;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for the {@link PropertiesConfig} class.
 *
 * @author Haixing Hu
 */
public class PropertiesConfigTest {

  @SuppressWarnings("unused")
  private static final String PACKAGE_PATH = "com/github/haixing_hu/config/impl/";

  @SuppressWarnings("unused")
  private static final String TEST_DATA = "properties-config.properties";

  @Test
  public void testDefaultConstructor() {
    final PropertiesConfig config = new PropertiesConfig();
    assertEquals(PropertiesConfig.DEFAULT_CHARSET, config.getCharset());
    assertEquals(true, config.isEmpty());
  }
  //
  //  @Test
  //  public void testConstructorString() {
  //    final PropertiesConfig config = new PropertiesConfig(PACKAGE_PATH
  //        + TEST_DATA);
  //    assertEquals(PropertiesConfig.DEFAULT_CHARSET, config.getCharset());
  //
  //    verifyConfig(config);
  //  }
  //
  //  private void verifyConfig(final Config config) {
  //    assertEquals("Hello world!", config.getString("com.github.prop1"));
  //
  //    assertArrayEquals(new String[] {"value 2.1", "value 2.2"},
  //        config.getStrings("com.github.prop2"));
  //
  //    assertEquals(12345, config.getInt("com.github.prop3"));
  //
  //    assertEquals("中文汉字", config.getString("com.github.prop4"));
  //  }


}
