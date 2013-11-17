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

package com.github.haixing_hu.util.filter.string;

import java.util.List;

import com.github.haixing_hu.text.AsciiTrie;

/**
 * The base class for BlackListAsciiStringFilter and WhiteListAsciiStringFilter
 * classes.
 *
 * @author Haixing Hu
 */
class ListAsciiStringFilter extends AbstractStringFilter {

  protected AsciiTrie trie;
  protected boolean   matchReturn;
  protected boolean   caseInsensitive;

  protected ListAsciiStringFilter(boolean matchReturn, boolean caseInsensitive) {
    this.trie = null;
    this.matchReturn = matchReturn;
    this.caseInsensitive = caseInsensitive;
  }

  public boolean isCaseInsensitive() {
    return caseInsensitive;
  }

  public int size() {
    return (trie == null ? 0 : trie.size());
  }

  public boolean isEmpty() {
    return ((trie == null) || trie.isEmpty());
  }

  public void clear() {
    if (trie != null) {
      trie.clear();
    }
  }

  public void setList(List<String> strList) {
    if (trie != null) {
      trie.clear();
    }
    if ((strList != null) && (strList.size() > 0)) {
      if (trie == null) {
        trie = new AsciiTrie(caseInsensitive);
      }
      for (String str : strList) {
        trie.add(str);
      }
    }
  }

  public void addToList(String str) {
    if (trie == null) {
      trie = new AsciiTrie(caseInsensitive);
    }
    trie.add(str);
  }

  @Override
  public boolean accept(String str) {
    if (trie != null) {
      if (trie.contains(str)) {
        return matchReturn;
      }
    }
    return (! matchReturn);
  }

}
