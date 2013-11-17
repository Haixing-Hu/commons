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
import java.net.MalformedURLException;

import javax.annotation.concurrent.Immutable;

import com.github.haixing_hu.io.exception.InvalidFormatException;
import com.github.haixing_hu.io.exception.SerializationException;
import com.github.haixing_hu.io.serialize.BinarySerializer;

import static com.github.haixing_hu.io.InputUtils.readString;
import static com.github.haixing_hu.io.OutputUtils.writeString;

/**
 * The {@link BinarySerializer} for the {@link Url} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class UrlBinarySerializer implements BinarySerializer {

  public static final UrlBinarySerializer INSTANCE = new UrlBinarySerializer();

  @Override
  public Url deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    final String str = readString(in, allowNull);
    if (str == null) {
      return null;
    }
    try {
      return new Url(str);
    } catch (final MalformedURLException e) {
      throw new InvalidFormatException(e);
    }
  }

  @Override
  public void serialize(final OutputStream out, final Object obj)
      throws IOException {
    Url url;
    try {
      url = (Url) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    writeString(out, (url == null ? null : url.toString()));
  }
}
