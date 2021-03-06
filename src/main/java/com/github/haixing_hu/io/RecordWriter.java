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

import java.io.Closeable;
import java.io.IOException;

import javax.annotation.Nullable;

/**
 * This interface provides functions to write key-value pair records input an
 * output destination.
 *
 * @author Haixing Hu
 */
public interface RecordWriter<KEY, VALUE> extends Closeable {

  /**
   * Writes a key/value pair to the output.
   *
   * @param key
   *          the key to be written.
   * @param value
   *          the value to be written.
   * @throws IOException
   *           if any I/O error occurred.
   */
  public void write(@Nullable KEY key, @Nullable VALUE value) throws IOException;
}
