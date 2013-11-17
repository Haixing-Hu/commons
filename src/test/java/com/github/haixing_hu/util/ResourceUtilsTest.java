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

package com.github.haixing_hu.util;

import junit.framework.TestCase;

import org.junit.Test;

import com.github.haixing_hu.util.ResourceUtils;

/**
 * Unit test of ResourceUtils class.
 *
 * @author Haixing Hu
 */
public class ResourceUtilsTest extends TestCase {

  static final String BUNDLE_NAME = "com.github.haixing_hu.util.TestBundle";

  @Test
  public void testOverridden() {
    assertEquals("wrong message",
        "The class name \"ResourceHelper\" is invalid.",
        ResourceUtils.getMessage(BUNDLE_NAME, "ClassValidator.bad.classname",
            "ResourceHelper"));
  }

  @Test
  public void testNewMessage1Param() {
    assertEquals("wrong message",
        "Some might say we will find a brighter day.",
        ResourceUtils.getMessage(BUNDLE_NAME, "test.message"));
  }

  @Test
  public void testNewMessage2Params() {
    assertEquals("wrong message",
        "Some might say we will find a brighter day.",
        ResourceUtils.getMessage(BUNDLE_NAME, "test.message", "Some"));
  }

  @Test
  public void testNewMessage3Params() {
    assertEquals("wrong message",
        "Some might say we will find a brighter day.",
        ResourceUtils.getMessage(BUNDLE_NAME, "test.message", "Some", "might"));
  }

  @Test
  public void testNewMessage4Params() {
    assertEquals("wrong message",
        "Some might say we will find a brighter day.",
        ResourceUtils.getMessage(BUNDLE_NAME, "test.message", "Some", "might",
            "say"));
  }

  @Test
  public void testDefaultBundle() {
    assertEquals("wrong message",
        "The class name \"ResourceHelper\" is invalid.",
        ResourceUtils.getMessage(BUNDLE_NAME, "ClassValidator.bad.classname",
            "ResourceHelper"));
  }
}
