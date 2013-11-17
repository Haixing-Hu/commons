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

package com.github.haixing_hu.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.github.haixing_hu.io.exception.InvalidFormatException;
import com.github.haixing_hu.io.exception.SerializationException;
import com.github.haixing_hu.io.serialize.BinarySerializer;
import com.github.haixing_hu.text.Pattern;

import static com.github.haixing_hu.CommonsMessages.UNEXPECTED_NULL_VALUE;
import static com.github.haixing_hu.io.InputUtils.*;
import static com.github.haixing_hu.io.OutputUtils.*;

/**
 * The {@link BinarySerializer} for the {@link UrlPattern} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class UrlPatternBinarySerializer implements BinarySerializer {

  public static final UrlPatternBinarySerializer INSTANCE = new UrlPatternBinarySerializer();

  @Override
  public UrlPattern deserialize(
      final InputStream in, final boolean allowNull) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    } else {
      final UrlPart part = readEnum(UrlPart.class, in, false);
      final Pattern pattern = readObject(Pattern.class, in, false);
      return new UrlPattern(part, pattern);
    }
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    if (! writeNullMark(out, obj)) {
      UrlPattern up;
      try {
        up = (UrlPattern) obj;
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      writeEnum(out, up.urlPart);
      writeObject(Pattern.class, out, up.pattern);
    }
  }
}
