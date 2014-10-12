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

import java.util.HashMap;
import java.util.Map;

/**
 * Provides utility functions for {@link Map} objects.
 *
 * @author Haixing Hu
 */
public class MapUtils {

  private static final Object[][] EMPTY_ARRAY = new Object[0][0];

  public static <K,V> Object[][] toArray(final Map<K,V> map) {
    if (map.isEmpty()) {
      return EMPTY_ARRAY;
    }
    final Object[][] result = new Object[map.size()][2];
    int i = 0;
    for (final Map.Entry<K, V> entry : map.entrySet()) {
      result[i][0] = entry.getKey();
      result[i][1] = entry.getValue();
      ++i;
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public static <K,V> Map<K,V> fromArray(final Object[][] array) {
    final Map<K,V> result = new HashMap<K,V>();
    for (int i = 0; i < array.length; ++i) {
      final K key = (K) array[i][0];
      final V value = (V) array[i][1];
      result.put(key, value);
    }
    return result;
  }
}
