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
package com.github.haixing_hu.io.serialize.predefined;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.github.haixing_hu.io.InputUtils;
import com.github.haixing_hu.io.OutputUtils;
import com.github.haixing_hu.io.exception.SerializationException;
import com.github.haixing_hu.io.serialize.BinarySerializer;

/**
 * The {@link BinarySerializer} for {@link boolean[]} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class BooleanArrayBinarySerializer implements BinarySerializer {

  public static final BooleanArrayBinarySerializer INSTANCE = new BooleanArrayBinarySerializer();

  @Override
  public boolean[] deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    return InputUtils.readBooleanArray(in, allowNull, null);
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    boolean[] value;
    try {
      value = (boolean[]) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    OutputUtils.writeBooleanArray(out, value);
  }

}
