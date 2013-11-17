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

package com.github.haixing_hu.text.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.github.haixing_hu.io.exception.InvalidFormatException;
import com.github.haixing_hu.io.exception.SerializationException;
import com.github.haixing_hu.io.serialize.BinarySerializer;

import static com.github.haixing_hu.CommonsMessages.UNEXPECTED_NULL_VALUE;
import static com.github.haixing_hu.io.InputUtils.*;
import static com.github.haixing_hu.io.OutputUtils.*;

/**
 * The {@link BinarySerializer} for the {@link TagPattern} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class TagPatternBinarySerializer implements BinarySerializer {

  public static final TagPatternBinarySerializer INSTANCE = new TagPatternBinarySerializer();

  @Override
  public TagPattern deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    } else {
      final TagPattern result = new TagPattern();
      result.tagName = readString(in, true);
      result.attributeName = readString(in, true);
      result.attributeValue = readString(in, true);
      result.order = readVarInt(in);
      result.child = deserialize(in, true); // recursively deserialize child
      return result;
    }
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    if (! writeNullMark(out, obj)) {
      TagPattern tp;
      try {
        tp = (TagPattern) obj;
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      writeString(out, tp.tagName);
      writeString(out, tp.attributeName);
      writeString(out, tp.attributeValue);
      writeVarInt(out, tp.order);
      serialize(out, tp.child);
    }
  }

}
