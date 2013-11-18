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

package com.github.haixing_hu.reflect;

import java.beans.PropertyDescriptor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.haixing_hu.beans.priv.PrivateBeanFactory;
import com.github.haixing_hu.beans.priv.PrivateDirect;
import com.github.haixing_hu.reflect.PropertyUtils;
import com.github.haixing_hu.reflect.testbed.Bean;
import com.github.haixing_hu.reflect.testbed.BeanPublicSubclass;

import static org.junit.Assert.*;

/**
 * Unit test of the {@link com.github.haixing_hu.beans.PropertyUtils}
 * class.
 *
 * @author Haixing Hu
 */
public class PropertyUtilTest {

  /**
   * The basic test bean for each test.
   */
  protected Bean                bean                = null;

  /**
   * The "package private subclass" test bean for each test.
   */
  protected BeanPackageSubclass beanPackageSubclass = null;

  /**
   * The test bean for private access tests.
   */
  protected PrivateDirect           beanPrivate         = null;

  /**
   * The test bean for private access tests of subclasses.
   */
  protected PrivateDirect           beanPrivateSubclass = null;

  /**
   * The "public subclass" test bean for each test.
   */
  protected BeanPublicSubclass  beanPublicSubclass  = null;

  /**
   * Set up instance variables required by this test case.
   */
  @Before
  public void setUp() {
    bean = new Bean();
    beanPackageSubclass = new BeanPackageSubclass();
    beanPrivate = PrivateBeanFactory.create();
    beanPrivateSubclass = PrivateBeanFactory.createSubclass();
    beanPublicSubclass = new BeanPublicSubclass();
  }

  /**
   * Tear down instance variables required by this test case.
   */
  @After
  public void tearDown() {
    bean = null;
    beanPackageSubclass = null;
    beanPrivate = null;
    beanPrivateSubclass = null;
    beanPublicSubclass = null;
  }

  @Test
  public void testInvokeMethod() {
    // fail("Not yet implemented");
  }

  @Test
  public void testGetPropertyDescriptors() {

    try {
      PropertyUtils.getPropertyDescriptors(null);
      fail("Should throw IllegalArgumentException");
    } catch (NullPointerException e) {
      // Expected response
    } catch (Throwable t) {
      fail("Threw " + t + " instead of NullPointerException");
    }

    PropertyDescriptor pd[] =
      PropertyUtils.getPropertyDescriptors(bean.getClass());
    assertNotNull("Got descriptors", pd);

    int count[] = new int[Bean.PROPERTIES.length];
    for (PropertyDescriptor element : pd) {
      String name = element.getName();
      boolean found = false;
      for (int j = 0; j < Bean.PROPERTIES.length; j++) {
        if (name.equals(Bean.PROPERTIES[j])) {
          ++count[j];
          found = true;
          break;
        }
      }
      if (! found) {
        fail("Undesired property '" + name + "'");
      }
    }

    for (int j = 0; j < Bean.PROPERTIES.length; j++) {
      if (count[j] <= 0) {
        fail("Missing property " + Bean.PROPERTIES[j]);
      } else if (count[j] > 1) {
        fail("Duplicate property " + Bean.PROPERTIES[j]);
      }
    }

    assertEquals(Bean.PROPERTIES.length, pd.length);
  }

  @Test
  public void testGetPropertyDescriptor() {
    // fail("Not yet implemented");
  }

}
