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
package com.github.haixing_hu.io;

import java.util.Iterator;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * A RecordReader which reads records form a map.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class MapRecordReader<KEY, VALUE> implements RecordReader<KEY, VALUE> {

  private final Map<KEY, VALUE> map;
  private final Class<KEY> keyClass;
  private final Class<VALUE> valueClass;
  private final long size;
  private long position;
  private Iterator<Map.Entry<KEY,VALUE>> iterator;

  public MapRecordReader(final Map<KEY, VALUE> map, final Class<KEY> keyClass,
      final Class<VALUE> valueClass) {
    this.map = requireNonNull("map", map);
    this.keyClass = requireNonNull("keyClass", keyClass);
    this.valueClass = requireNonNull("valueClass", valueClass);
    this.size = map.size();
    this.position = 0;
    this.iterator = map.entrySet().iterator();
  }

  @Override
  public KEY createKey() throws InstantiationException, IllegalAccessException {
    return keyClass.newInstance();
  }

  @Override
  public VALUE createValue() throws InstantiationException, IllegalAccessException {
    return valueClass.newInstance();
  }

  @Override
  public long getPosition() {
    return position;
  }

  @Override
  public float getProgress() {
    return (float)position / (float) size;
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public Map.Entry<KEY, VALUE> next() {
    final Map.Entry<KEY, VALUE> result = iterator.next();
    ++position;
    return result;
  }

  @Override
  public void close() {
    position = 0;
    iterator = map.entrySet().iterator();
  }

}
