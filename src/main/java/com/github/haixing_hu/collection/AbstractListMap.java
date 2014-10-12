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
package com.github.haixing_hu.collection;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.concurrent.NotThreadSafe;

import com.github.haixing_hu.lang.Equality;


/**
 * An implementation of map using an abstract list to store the keys and values.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public abstract class AbstractListMap<KEY, VALUE> implements Map<KEY, VALUE> {

  protected static class Entry<KEY, VALUE> implements Map.Entry<KEY, VALUE> {
    KEY key;
    VALUE value;

    public Entry(KEY key, VALUE value) {
      this.key = key;
      this.value = value;
    }

    public KEY getKey() {
      return key;
    }

    public VALUE getValue() {
      return value;
    }

    public VALUE setValue(VALUE value) {
      VALUE oldValue = this.value;
      this.value = value;
      return oldValue;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((key == null) ? 0 : key.hashCode());
      result = prime * result + ((value == null) ? 0 : value.hashCode());
      return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      Entry<KEY, VALUE> other = (Entry<KEY, VALUE>) obj;
      if (key == null) {
        if (other.key != null) {
          return false;
        }
      } else if (!key.equals(other.key)) {
        return false;
      }
      if (value == null) {
        if (other.value != null) {
          return false;
        }
      } else if (!value.equals(other.value)) {
        return false;
      }
      return true;
    }
  }

  protected List<Entry<KEY, VALUE>> list;

  protected abstract List<Entry<KEY, VALUE>> makeList();

  protected AbstractListMap() {
    list = null;
  }

  public boolean isEmpty() {
    return (list == null) || list.isEmpty();
  }

  public int size() {
    return (list == null ? 0 : list.size());
  }

  public void clear() {
    if (list != null) {
      list.clear();
    }
  }

  public Set<Map.Entry<KEY, VALUE>> entrySet() {
    if (list == null) {
      return Collections.emptySet();
    } else {
      return new EntrySet();
    }
  }

  public Set<KEY> keySet() {
    if (list == null) {
      return Collections.emptySet();
    } else {
      return new KeySet();
    }
  }

  public Collection<VALUE> values() {
    if (list == null) {
      return Collections.emptySet();
    } else {
      return new ValueCollection();
    }
  }

  private ListIterator<Entry<KEY, VALUE>> getEntryIterator(Object key) {
    if (list == null) {
      return null;
    }
    if (key == null) {
      for (ListIterator<Entry<KEY, VALUE>> it = list.listIterator(); it.hasNext(); ) {
        Entry<KEY, VALUE> entry = it.next();
        if (entry.key == null) {
          return it;
        }
      }
    } else {
      for (ListIterator<Entry<KEY, VALUE>> it = list.listIterator(); it.hasNext(); ) {
        Entry<KEY, VALUE> entry = it.next();
        if (key.equals(entry.key)) {
          return it;
        }
      }
    }
    return null;
  }

  public boolean containsKey(Object key) {
    return getEntryIterator(key) != null;
  }

  public boolean containsValue(Object value) {
    if (list == null) {
      return false;
    }
    if (value == null) {
      for (Entry<KEY, VALUE> entry : list) {
        if (entry.value == null) {
          return true;
        }
      }
    } else {
      for (Entry<KEY, VALUE> entry : list) {
        if (value.equals(entry.value)) {
          return true;
        }
      }
    }
    return false;
  }

  public VALUE get(Object key) {
    ListIterator<Entry<KEY, VALUE>> it = getEntryIterator(key);
    return (it == null ? null : it.previous().value);
  }

  public VALUE put(KEY key, VALUE value) {
    if (list == null) {
      list = makeList();
      list.add(new Entry<KEY, VALUE>(key, value));
      return null;
    } else {
      ListIterator<Entry<KEY, VALUE>> it = getEntryIterator(key);
      if (it == null) {
        list.add(new Entry<KEY, VALUE>(key, value));
        return null;
      } else {
        Entry<KEY, VALUE> entry = it.previous();
        VALUE oldValue = entry.value;
        entry.value = value;
        return oldValue;
      }
    }
  }

  public void putAll(Map<? extends KEY, ? extends VALUE> map) {
    if ((map == this) || map.isEmpty()) {
      return;
    }
    if (list == null) {
      list = makeList();
      for (Map.Entry<? extends KEY, ? extends VALUE> entry : map.entrySet()) {
        list.add(new Entry<KEY, VALUE>(entry.getKey(), entry.getValue()));
      }
    } else {
      for (Map.Entry<? extends KEY, ? extends VALUE> entry : map.entrySet()) {
        KEY key = entry.getKey();
        VALUE value = entry.getValue();
        ListIterator<Entry<KEY, VALUE>> it = getEntryIterator(key);
        if (it == null) {
          list.add(new Entry<KEY, VALUE>(key, value));
        } else {
          it.previous().value = value;
        }
      }
    }
  }

  public VALUE remove(Object key) {
    if (list == null) {
      return null;
    }
    ListIterator<Entry<KEY, VALUE>> it = getEntryIterator(key);
    if (it == null) {
      return null;
    }
    Entry<KEY, VALUE> entry = it.previous();
    VALUE oldValue = entry.value;
    it.remove();
    return oldValue;
  }


  private final class EntrySet extends AbstractSet<Map.Entry<KEY, VALUE>> {

    @Override
    public Iterator<Map.Entry<KEY, VALUE>> iterator() {
      return new EntryIterator();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object o) {
      if (!(o instanceof Map.Entry)) {
        return false;
      }
      Map.Entry<KEY, VALUE> e = (Map.Entry<KEY, VALUE>) o;
      ListIterator<Entry<KEY, VALUE>> it = getEntryIterator(e.getKey());
      if (it == null) {
        return false;
      } else {
        Entry<KEY, VALUE> candidate = it.previous();
        if (candidate.value == null) {
          return e.getValue() == null;
        } else {
          return candidate.value.equals(e.getValue());
        }
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(Object o) {
      if (list == null) {
        return false;
      }
      if (!(o instanceof Map.Entry)) {
        return false;
      }
      Map.Entry<KEY, VALUE> e = (Map.Entry<KEY, VALUE>) o;
      ListIterator<Entry<KEY, VALUE>> it = getEntryIterator(e.getKey());
      if (it == null) {
        return false;
      } else {
        Entry<KEY, VALUE> candidate = it.previous();
        if (Equality.equals(candidate.value, e.getValue())) {
          it.remove();
          return true;
        } else {
          return false;
        }
      }
    }

    @Override
    public int size() {
      return (list == null ? 0 : list.size());
    }

    @Override
    public void clear() {
      if (list != null) {
        list.clear();
      }
    }
  }

  private final class EntryIterator implements Iterator<Map.Entry<KEY, VALUE>> {

    private ListIterator<Entry<KEY, VALUE>> iter;

    public EntryIterator() {
      if (list == null) {
        List<Entry<KEY, VALUE>> emptyList = Collections.emptyList();
        iter = emptyList.listIterator();
      } else {
        iter = list.listIterator();
      }
    }

    public boolean hasNext() {
      return iter.hasNext();
    }

    public Map.Entry<KEY, VALUE> next() {
      return iter.next();
    }

    public void remove() {
      iter.remove();
    }
  }

  private final class KeySet extends AbstractSet<KEY> {

    @Override
    public Iterator<KEY> iterator() {
      return new KeyIterator();
    }

    @Override
    public boolean contains(Object key) {
      return containsKey(key);
    }

    @Override
    public boolean remove(Object key) {
      return AbstractListMap.this.remove(key) != null;
    }

    @Override
    public int size() {
      return (list == null ? 0 : list.size());
    }

    @Override
    public void clear() {
      if (list != null) {
        list.clear();
      }
    }
  }

  private final class KeyIterator implements Iterator<KEY> {
    private ListIterator<Entry<KEY, VALUE>> iter;

    public KeyIterator() {
      if (list == null) {
        List<Entry<KEY, VALUE>> emptyList = Collections.emptyList();
        iter = emptyList.listIterator();
      } else {
        iter = list.listIterator();
      }
    }

    public boolean hasNext() {
      return iter.hasNext();
    }

    public KEY next() {
      return iter.next().key;
    }

    public void remove() {
      iter.remove();
    }
  }

  private final class ValueCollection extends AbstractCollection<VALUE> {

    @Override
    public Iterator<VALUE> iterator() {
      return new ValueIterator();
    }

    @Override
    public boolean isEmpty() {
      return (list == null) || list.isEmpty();
    }

    @Override
    public int size() {
      return (list == null ? 0 : list.size());
    }

    @Override
    public void clear() {
      if (list != null) {
        list.clear();
      }
    }

    @Override
    public boolean contains(Object o) {
      return containsValue(o);
    }

  }

  private final class ValueIterator implements Iterator<VALUE> {
    private ListIterator<Entry<KEY, VALUE>> iter;

    public ValueIterator() {
      if (list == null) {
        List<Entry<KEY, VALUE>> emptyList = Collections.emptyList();
        iter = emptyList.listIterator();
      } else {
        iter = list.listIterator();
      }
    }

    public boolean hasNext() {
      return iter.hasNext();
    }

    public VALUE next() {
      return iter.next().value;
    }

    public void remove() {
      iter.remove();
    }
  }

}
