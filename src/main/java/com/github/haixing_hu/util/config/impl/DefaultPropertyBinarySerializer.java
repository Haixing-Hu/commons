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

package com.github.haixing_hu.util.config.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.github.haixing_hu.io.exception.InvalidFormatException;
import com.github.haixing_hu.io.exception.SerializationException;
import com.github.haixing_hu.io.serialize.BinarySerializer;
import com.github.haixing_hu.lang.Type;

import static com.github.haixing_hu.CommonsMessages.UNEXPECTED_NULL_VALUE;
import static com.github.haixing_hu.io.InputUtils.*;
import static com.github.haixing_hu.io.OutputUtils.*;

/**
 * The {@link BinarySerializer} for the {@link DefaultProperty} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class DefaultPropertyBinarySerializer implements BinarySerializer {

  public static final DefaultPropertyBinarySerializer INSTANCE =
    new DefaultPropertyBinarySerializer();

  @Override
  public DefaultProperty deserialize(final InputStream in,
      final boolean allowNull) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    }
    final DefaultProperty result = new DefaultProperty();
    result.setName(readString(in, false));
    result.setType(readEnum(Type.class, in, false));
    result.setDescription(readString(in, true));
    result.setFinal(readBoolean(in));
    final int n = readVarInt(in);
    result.readValues(result.getType(), n, in);
    return result;
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    if (writeNullMark(out, obj)) {
      return;
    }
    DefaultProperty prop;
    try {
      prop = (DefaultProperty) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    writeString(out, prop.getName());
    writeEnum(out, prop.getType());
    writeString(out, prop.getDescription());
    writeBoolean(out, prop.isFinal());
    final int n = prop.getCount();
    writeVarInt(out, n);
    prop.writeValues(out);
  }

}
