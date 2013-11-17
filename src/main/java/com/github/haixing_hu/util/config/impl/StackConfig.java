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

package com.github.haixing_hu.util.config.impl;

import java.util.Collection;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.github.haixing_hu.util.config.AbstractConfig;
import com.github.haixing_hu.util.config.Config;
import com.github.haixing_hu.util.config.Property;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * An implementation of {@link Config} interface which get property values from
 * a stack of {@link Config}s.
 * <p>
 * The property value is looked up in the stack of {@link Config}s form the top
 * (the last one put into the stack) to the bottom (the first one put into the
 * stack).
 * </p>
 *
 * @author Haixing Hu
 */
public class StackConfig extends AbstractConfig {

  private static final long serialVersionUID = 7815389819448399587L;

  private final Stack<Config> configs;

  public StackConfig() {
    configs = new Stack<Config>();
  }

  /**
   * Pushes a {@link Config} to the stack.
   *
   * @param config
   *    the {@link Config} to be pushed into the stack.
   * @throws NullPointerException
   *    if the <code>config</code> is null.
   */
  public void push(final Config config) {
    configs.push(requireNonNull("config", config));
  }

  /**
   * Pops the top {@link Config} object in the stack.
   *
   * @return
   *    the top {@link Config} object in the stack.
   * @throws EmptyStackException
   *    if this stack is empty.
   */
  public Config pop() throws EmptyStackException {
    return configs.pop();
  }

  /**
   * Tests whether the stack is empty.
   *
   * @return
   *    true if the stack is empty; false otherwise.
   */
  public boolean isStackEmpty() {
    return configs.isEmpty();
  }

  /**
   * Gets the size of the stack.
   *
   * @return
   *    the size of the stack.
   */
  public int stackSize() {
    return configs.size();
  }

  @Override
  public boolean isEmpty() {
    for (final Config config : configs) {
      if (! config.isEmpty()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int size() {
    return getNames().size();
  }

  @Override
  public Collection<? extends Property> getProperties() {
    final Map<String, Property> propertiesMap = new HashMap<String, Property>();
    for (final Config config : configs) {
      for (final Property prop : config.getProperties()) {
        // note that the property in the last-in config will override
        // the properties with the same name in the early config
        propertiesMap.put(prop.getName(), prop);
      }
    }
    return propertiesMap.values();
  }

  @Override
  public Set<String> getNames() {
    final Set<String> names = new HashSet<String>();
    for (final Config config : configs) {
      names.addAll(config.getNames());
    }
    return names;
  }

  @Override
  public boolean contains(final String name) {
    for (final Config config : configs) {
      if (config.contains(name)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Property get(final String name) {
    // look up the property from the last to the first
    for (int i = configs.size() - 1; i >= 0; --i) {
      final Config config = configs.get(i);
      if (config.contains(name)) {
        return config.get(name);
      }
    }
    return null;
  }

  @Override
  public StackConfig clone() {
    final StackConfig result = new StackConfig();
    for (final Config config : this.configs) {
      result.configs.add(config);
    }
    return result;
  }
}
