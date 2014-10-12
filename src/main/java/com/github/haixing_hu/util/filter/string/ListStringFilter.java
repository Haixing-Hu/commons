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
package com.github.haixing_hu.util.filter.string;

import java.util.Collection;

import com.github.haixing_hu.text.Trie;

/**
 * The base class for BlackListStringFilter and WhiteListStringFilter classes.
 *
 * @author Haixing Hu
 */
class ListStringFilter extends AbstractStringFilter {

  protected Trie    trie;
  protected boolean matchReturn;
  protected boolean caseInsensitive;

  protected ListStringFilter(boolean matchReturn, boolean caseInsensitive) {
    this.trie = null;
    this.matchReturn = matchReturn;
    this.caseInsensitive = caseInsensitive;
  }

  public final boolean isCaseInsensitive() {
    return caseInsensitive;
  }

  public final int size() {
    return (trie == null ? 0 : trie.size());
  }

  public final boolean isEmpty() {
    return ((trie == null) || trie.isEmpty());
  }

  public final void clear() {
    if (trie != null) {
      trie.clear();
    }
  }

  public final void setList(Collection<String> strList) {
    // note that even if strList is empty,
    // we still need to clear the trie.
    if (trie != null) {
      trie.clear();
    }
    if (! strList.isEmpty()) {
      if (trie == null) {
        trie = new Trie(caseInsensitive);
      }
      for (String str : strList) {
        trie.add(str);
      }
    }
  }

  public final void setList(String ... strList) {
    // note that even if strList is empty,
    // we still need to clear the trie.
    if (trie != null) {
      trie.clear();
    }
    if (strList.length > 0) {
      if (trie == null) {
        trie = new Trie(caseInsensitive);
      }
      for (String str : strList) {
        trie.add(str);
      }
    }
  }

  public final void addToList(String str) {
    if (trie == null) {
      trie = new Trie(caseInsensitive);
    }
    trie.add(str);
  }

  public final void addToList(Collection<String> strings) {
    if (strings.isEmpty()) {
      return;
    }
    if (trie == null) {
      trie = new Trie(caseInsensitive);
    }
    for (String str : strings) {
      trie.add(str);
    }
  }

  public final void addToList(String ... strings) {
    if (strings.length == 0) {
      return;
    }
    if (trie == null) {
      trie = new Trie(caseInsensitive);
    }
    for (String str : strings) {
      trie.add(str);
    }
  }

  @Override
  public final boolean accept(String str) {
    if (trie != null) {
      if (trie.contains(str)) {
        return matchReturn;
      }
    }
    return (! matchReturn);
  }

}
