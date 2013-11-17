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

package com.github.haixing_hu.io;

import java.util.Map;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * A RecordWriter which writes records into a map.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class MapRecordWriter<KEY, VALUE> implements RecordWriter<KEY, VALUE> {

  private final Map<KEY, VALUE> map;

  public MapRecordWriter(final Map<KEY, VALUE> map) {
    this.map = requireNonNull("map", map);
  }

  @Override
  public void write(@Nullable final KEY key, @Nullable final VALUE value) {
    map.put(key, value);
  }

  @Override
  public void close() {
    // do nothing
  }

}
