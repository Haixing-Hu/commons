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
package com.github.haixing_hu.net;

import java.lang.reflect.Array;

import com.github.haixing_hu.text.Pattern;
import com.github.haixing_hu.text.PatternMap;

/**
 * The {@link PatternMap} maps a {@link Url} to an object according to
 * predefined patterns.
 *
 * @author Haixing Hu
 */
public class UrlPatternMap<VALUE> {

  private final PatternMap<VALUE>[] maps;
  private int size;

  @SuppressWarnings("unchecked")
  public UrlPatternMap() {
    final UrlPart[] parts = UrlPart.values();
    final int n = parts.length;
    maps = (PatternMap<VALUE>[]) Array.newInstance(PatternMap.class, n);
    size = 0;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return (size == 0);
  }

  public void clear() {
    for (int i = 0; i < maps.length; ++i) {
      if (maps[i] != null) {
        maps[i].clear();
      }
    }
    size = 0;
  }

  public void put(final UrlPattern urlPattern, final VALUE value) {
    final UrlPart part = urlPattern.getUrlPart();
    final int index = part.ordinal();
    if (maps[index] == null) {
      maps[index] = new PatternMap<VALUE>();
    }
    final Pattern pattern = urlPattern.getPattern();
    maps[index].put(pattern, value);
  }

  public VALUE get(final Url url) {
    final UrlPart[] parts = UrlPart.values();
    assert (parts.length == maps.length);
    for (int i = 0; i < parts.length; ++i) {
      if (maps[i] != null) {
        final String str = url.get(parts[i]);
        final VALUE result = maps[i].get(str);
        if (result != null) {
          return result;
        }
      }
    }
    return null;
  }
}
