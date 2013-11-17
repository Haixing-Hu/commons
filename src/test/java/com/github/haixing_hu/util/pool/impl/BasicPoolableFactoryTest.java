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

package com.github.haixing_hu.util.pool.impl;

import org.junit.Test;

import com.github.haixing_hu.util.pool.VisitTracker;
import com.github.haixing_hu.util.pool.impl.BasicPoolableFactory;

import static org.junit.Assert.*;

/**
 * Unit test of the {@link BasicPoolableFactory} class.
 *
 * @author Haixing Hu
 */
public class BasicPoolableFactoryTest {

  /**
   * Test method for {@link BasicPoolableFactory#BasicPoolableFactory(java.lang.Class)}.
   */
  @Test
  public void testBasicPoolableFactory() {
    final BasicPoolableFactory<Integer> factory1 = new BasicPoolableFactory<Integer>(Integer.class);
    assertEquals(Integer.class, factory1.getObjectClass());

    final BasicPoolableFactory<VisitTracker> factory2 = new BasicPoolableFactory<VisitTracker>(VisitTracker.class);
    assertEquals(VisitTracker.class, factory2.getObjectClass());

    try {
      new BasicPoolableFactory<VisitTracker>(null);
      fail("should throw NullPointerException");
    } catch (final NullPointerException e) {
      // pass
    }
  }

  /**
   * Test method for {@link BasicPoolableFactory#create()}.
   */
  @Test
  public void testCreate() {
    final BasicPoolableFactory<VisitTracker> factory = new BasicPoolableFactory<VisitTracker>(VisitTracker.class);
    assertEquals(VisitTracker.class, factory.getObjectClass());

    final VisitTracker obj1 = factory.create();
    assertNotNull(obj1);

    final VisitTracker obj2 = factory.create();
    assertNotNull(obj2);
  }

  /**
   * Test method for {@link BasicPoolableFactory#validate(java.lang.Object)}.
   */
  @Test
  public void testValidate() {
    final BasicPoolableFactory<VisitTracker> factory = new BasicPoolableFactory<VisitTracker>(VisitTracker.class);
    assertEquals(VisitTracker.class, factory.getObjectClass());

    final VisitTracker obj1 = factory.create();
    assertNotNull(obj1);
    assertEquals(true, factory.validate(obj1));

    final VisitTracker obj2 = factory.create();
    assertNotNull(obj2);
    assertEquals(true, factory.validate(obj2));
  }

  /**
   * Test method for {@link BasicPoolableFactory#activate(java.lang.Object)}.
   */
  @Test
  public void testActivate() {
    final BasicPoolableFactory<VisitTracker> factory = new BasicPoolableFactory<VisitTracker>(VisitTracker.class);
    assertEquals(VisitTracker.class, factory.getObjectClass());

    final VisitTracker obj1 = factory.create();
    assertNotNull(obj1);
    assertEquals(true, factory.validate(obj1));
    factory.activate(obj1);

    final VisitTracker obj2 = factory.create();
    assertNotNull(obj2);
    assertEquals(true, factory.validate(obj2));
    factory.activate(obj2);
  }

  /**
   * Test method for {@link BasicPoolableFactory#passivate(java.lang.Object)}.
   */
  @Test
  public void testPassivate() {
    final BasicPoolableFactory<VisitTracker> factory = new BasicPoolableFactory<VisitTracker>(VisitTracker.class);
    assertEquals(VisitTracker.class, factory.getObjectClass());

    final VisitTracker obj1 = factory.create();
    assertNotNull(obj1);
    assertEquals(true, factory.validate(obj1));
    factory.passivate(obj1);
    factory.activate(obj1);

    final VisitTracker obj2 = factory.create();
    assertNotNull(obj2);
    assertEquals(true, factory.validate(obj2));
    factory.passivate(obj1);
    factory.activate(obj2);
  }

  /**
   * Test method for {@link BasicPoolableFactory#destroy(java.lang.Object)}.
   */
  @Test
  public void testDestroy() {
    final BasicPoolableFactory<VisitTracker> factory = new BasicPoolableFactory<VisitTracker>(VisitTracker.class);
    assertEquals(VisitTracker.class, factory.getObjectClass());

    final VisitTracker obj1 = factory.create();
    assertNotNull(obj1);
    assertEquals(true, factory.validate(obj1));
    factory.passivate(obj1);
    factory.activate(obj1);
    factory.destroy(obj1);

    final VisitTracker obj2 = factory.create();
    assertNotNull(obj2);
    assertEquals(true, factory.validate(obj2));
    factory.passivate(obj1);
    factory.activate(obj2);
    factory.destroy(obj2);
  }

}
