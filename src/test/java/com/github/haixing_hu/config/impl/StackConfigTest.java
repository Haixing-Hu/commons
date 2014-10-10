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

package com.github.haixing_hu.config.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.github.haixing_hu.config.impl.DefaultConfig;
import com.github.haixing_hu.config.impl.DefaultProperty;
import com.github.haixing_hu.config.impl.StackConfig;
import com.github.haixing_hu.lang.Type;

import static org.junit.Assert.assertEquals;

import static com.github.haixing_hu.util.junit.Assert.assertCollectionEquals;

/**
 * Unit test for the {@link StackConfig} class.
 *
 * @author Haixing Hu
 */
public class StackConfigTest {

  public void testIsStackEmpty() {
    final StackConfig config = new StackConfig();
    final DefaultConfig config1 = new DefaultConfig();

    assertEquals(true, config.isStackEmpty());
    config.push(config1);
    assertEquals(false, config.isStackEmpty());
  }

  public void testStackSize() {
    final StackConfig config = new StackConfig();
    final DefaultConfig config1 = new DefaultConfig();
    final DefaultConfig config2 = new DefaultConfig();
    final DefaultConfig config3 = new DefaultConfig();

    assertEquals(0, config.stackSize());
    config.push(config1);
    assertEquals(1, config.stackSize());
    config.push(config2);
    assertEquals(2, config.stackSize());
    config.push(config3);
    assertEquals(3, config.stackSize());
  }

  public void testIsEmpty() {
    final StackConfig config = new StackConfig();
    final DefaultConfig config1 = new DefaultConfig();
    final DefaultConfig config2 = new DefaultConfig();
    final DefaultConfig config3 = new DefaultConfig();

    assertEquals(true, config.isEmpty());
    config.push(config1);
    assertEquals(true, config.isEmpty());
    config.push(config2);
    assertEquals(true, config.isEmpty());
    config.push(config3);
    assertEquals(true, config.isEmpty());

    config1.add("prop1", Type.INT);
    assertEquals(false, config.isEmpty());
  }


  public void testSize() {
    final StackConfig config = new StackConfig();
    final DefaultConfig config1 = new DefaultConfig();
    final DefaultConfig config2 = new DefaultConfig();
    final DefaultConfig config3 = new DefaultConfig();

    assertEquals(0, config.size());
    config.push(config1);
    assertEquals(0, config.size());
    config.push(config2);
    assertEquals(0, config.size());
    config.push(config3);
    assertEquals(0, config.size());

    config1.add("prop1", Type.INT);
    assertEquals(1, config.size());

    config1.add("prop2", Type.INT);
    assertEquals(2, config.size());

    config2.add("prop3", Type.INT);
    assertEquals(3, config.size());

    config2.add("prop1", Type.INT);
    assertEquals(3, config.size());

    config3.add("prop3", Type.INT);
    assertEquals(3, config.size());
  }

  @Test
  public void testGetNames() {
    final StackConfig config = new StackConfig();
    final DefaultConfig config1 = new DefaultConfig();
    final DefaultConfig config2 = new DefaultConfig();
    final DefaultConfig config3 = new DefaultConfig();
    final Set<String> names = new HashSet<String>();

    assertEquals(names, config.getNames());
    config.push(config1);
    assertEquals(names, config.getNames());
    config.push(config2);
    assertEquals(names, config.getNames());
    config.push(config3);
    assertEquals(names, config.getNames());

    config1.add("prop1", Type.INT);
    names.add("prop1");
    assertEquals(names, config.getNames());

    config1.add("prop2", Type.INT);
    names.add("prop2");
    assertEquals(names, config.getNames());

    config2.add("prop3", Type.INT);
    names.add("prop3");
    assertEquals(names, config.getNames());

    config2.add("prop1", Type.INT);
    assertEquals(names, config.getNames());

    config3.add("prop3", Type.INT);
    assertEquals(names, config.getNames());
  }

  @Test
  public void testGetProperties() {
    final StackConfig config = new StackConfig();
    final DefaultConfig config1 = new DefaultConfig();
    final DefaultConfig config2 = new DefaultConfig();
    final DefaultConfig config3 = new DefaultConfig();
    final Map<String, DefaultProperty> propertiesMap = new HashMap<String, DefaultProperty>();

    assertCollectionEquals(propertiesMap.values(), config.getProperties());
    config.push(config1);
    assertCollectionEquals(propertiesMap.values(), config.getProperties());
    config.push(config2);
    assertCollectionEquals(propertiesMap.values(), config.getProperties());
    config.push(config3);
    assertCollectionEquals(propertiesMap.values(), config.getProperties());

    final DefaultProperty prop1 = new DefaultProperty("prop1", Type.INT);
    config1.add(prop1);
    propertiesMap.put(prop1.getName(), prop1);
    assertCollectionEquals(propertiesMap.values(), config.getProperties());

    final DefaultProperty prop2 = new DefaultProperty("prop2", Type.STRING);
    config1.add(prop2);
    propertiesMap.put(prop2.getName(), prop2);
    assertCollectionEquals(propertiesMap.values(), config.getProperties());

    final DefaultProperty prop3 = new DefaultProperty("prop3", Type.FLOAT);
    config2.add(prop3);
    propertiesMap.put(prop3.getName(), prop3);
    assertCollectionEquals(propertiesMap.values(), config.getProperties());

    final DefaultProperty prop4 = new DefaultProperty("prop1", Type.FLOAT);
    config2.add(prop4);
    propertiesMap.put(prop4.getName(), prop4);
    assertCollectionEquals(propertiesMap.values(), config.getProperties());

    final DefaultProperty prop5 = new DefaultProperty("prop3", Type.DOUBLE);
    config3.add(prop5);
    propertiesMap.put(prop5.getName(), prop5);
    assertCollectionEquals(propertiesMap.values(), config.getProperties());
  }
}
