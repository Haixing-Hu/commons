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

package com.github.haixing_hu.util.pair;

import java.util.Map;

import javax.annotation.Nullable;

import com.github.haixing_hu.lang.Equality;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

/**
 * A pair of key/value implementing the {@link Map.Entry} interface.
 *
 * @author Haixing Hu
 */
public final class KeyValuePair<K, V> implements Map.Entry<K,V> {

  public final K key;

  public V value;

  public KeyValuePair(final K key) {
    this.key = key;
    this.value = null;
  }

  public KeyValuePair(final K key, final V value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public K getKey() {
    return key;
  }

  @Override
  public V getValue() {
    return value;
  }

  @Override
  public V setValue(final V value) {
    final V old = this.value;
    this.value = value;
    return old;
  }

  @Override
  public int hashCode() {
    final int multiplier = 1111;
    int code = 123;
    code = Hash.combine(code, multiplier, key);
    code = Hash.combine(code, multiplier, value);
    return code;
  }

  @Override
  public boolean equals(@Nullable final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (obj.getClass() != KeyValuePair.class) {
      return false;
    }
    final KeyValuePair<?,?> other = (KeyValuePair<?,?>) obj;
    return Equality.equals(key, other.key)
        && Equality.equals(value, other.value);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("key", key)
               .append("value", value)
               .toString();
  }
}
