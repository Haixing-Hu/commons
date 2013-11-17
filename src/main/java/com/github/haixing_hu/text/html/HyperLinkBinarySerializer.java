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

package com.github.haixing_hu.text.html;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.concurrent.ThreadSafe;

import com.github.haixing_hu.io.exception.InvalidFormatException;
import com.github.haixing_hu.io.exception.SerializationException;
import com.github.haixing_hu.io.serialize.BinarySerializer;
import com.github.haixing_hu.net.Url;
import com.github.haixing_hu.net.UrlBinarySerializer;

import static com.github.haixing_hu.CommonsMessages.UNEXPECTED_NULL_VALUE;
import static com.github.haixing_hu.io.InputUtils.*;
import static com.github.haixing_hu.io.OutputUtils.*;

/**
 * The {@link BinarySerializer} for the {@link HyperLink} class.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class HyperLinkBinarySerializer implements BinarySerializer {

  public static final HyperLinkBinarySerializer INSTANCE = new HyperLinkBinarySerializer();

  @Override
  public HyperLink deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new InvalidFormatException(UNEXPECTED_NULL_VALUE);
      }
    } else {
      final Url url = UrlBinarySerializer.INSTANCE.deserialize(in, true);
      final String anchor = readString(in, true);
      return new HyperLink(url, anchor);
    }
  }

  @Override
  public void serialize(final OutputStream out, final Object obj)
      throws IOException {
    if (! writeNullMark(out, obj)) {
      HyperLink link;
      try {
        link = (HyperLink) obj;
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      UrlBinarySerializer.INSTANCE.serialize(out, link.url());
      writeString(out, link.anchor());
    }
  }
}
