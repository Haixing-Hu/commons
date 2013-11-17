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

package com.github.haixing_hu.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.concurrent.Immutable;

import com.github.haixing_hu.io.exception.InvalidFormatException;
import com.github.haixing_hu.io.exception.SerializationException;
import com.github.haixing_hu.io.serialize.BinarySerializer;

import static com.github.haixing_hu.CommonsMessages.UNEXPECTED_NULL_VALUE;
import static com.github.haixing_hu.io.InputUtils.*;
import static com.github.haixing_hu.io.OutputUtils.*;

/**
 * The {@link BinarySerializer} for the {@link DatePattern} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class DatePatternBinarySerializer implements BinarySerializer {

  public static final DatePatternBinarySerializer INSTANCE = new DatePatternBinarySerializer();

  @Override
  public DatePattern deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    } else {
      final DatePattern result = new DatePattern();
      result.pattern = readString(in, false);
      result.format = readString(in, false);
      final String localeId = readString(in, true);
      result.locale = LocaleUtils.fromPosixLocale(localeId);
      result.matcher = null;
      result.dateFormat = null;
      return result;
    }
  }

  @Override
  public void serialize(final OutputStream out, final Object obj) throws IOException {
    if (! writeNullMark(out, obj)) {
      DatePattern dp;
      try {
        dp = (DatePattern) obj;
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      writeString(out, dp.pattern);
      writeString(out, dp.format);
      final String localeId = LocaleUtils.toPosixLocale(dp.locale);
      writeString(out, localeId);
    }
  }

}
