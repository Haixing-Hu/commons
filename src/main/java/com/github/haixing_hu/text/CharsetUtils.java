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

package com.github.haixing_hu.text;

import java.nio.charset.Charset;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines the constants of the common charsets.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class CharsetUtils {

  public static final String NAME_ASCII         = "ASCII";
  public static final String NAME_ISO_8859_1    = "ISO-8859-1";
  public static final String NAME_LATIN_1       = NAME_ISO_8859_1;
  public static final String NAME_UTF_8         = "UTF-8";
  public static final String NAME_UTF_16LE      = "UTF-16LE";
  public static final String NAME_UTF_16BE      = "UTF-16BE";
  public static final String NAME_UTF_16        = "UTF-16";
  public static final String NAME_UTF_32LE      = "UTF-32LE";
  public static final String NAME_UTF_32BE      = "UTF-32BE";
  public static final String NAME_UTF_32        = "UTF-32";
  public static final String NAME_GB2312        = "GB2312";
  public static final String NAME_GBK           = "GBK";
  public static final String NAME_GB18030       = "GB18030";
  public static final String NAME_BIG5          = "BIG5";

  public static final Charset ASCII             = Charset.forName(NAME_ASCII);
  public static final Charset ISO_8859_1        = Charset.forName(NAME_ISO_8859_1);
  public static final Charset LATIN_1           = ISO_8859_1;
  public static final Charset UTF_8             = Charset.forName(NAME_UTF_8);
  public static final Charset UTF_16LE          = Charset.forName(NAME_UTF_16LE);
  public static final Charset UTF_16BE          = Charset.forName(NAME_UTF_16BE);
  public static final Charset UTF_16            = Charset.forName(NAME_UTF_16);

  // The UTF_32LE and UTF_32 are not supported on the Mac OS X platform.

//  public static final Charset UTF_32LE          = Charset.forName(NAME_UTF_32LE);
//  public static final Charset UTF_32BE          = Charset.forName(NAME_UTF_32BE);
//  public static final Charset UTF_32            = Charset.forName(NAME_UTF_32);

  public static final Charset GB2312            = Charset.forName(NAME_GB2312);
  public static final Charset GBK               = Charset.forName(NAME_GBK);
  public static final Charset GB18030           = Charset.forName(NAME_GB18030);
  public static final Charset BIG5              = Charset.forName(NAME_BIG5);

  private static final Logger LOGGER = LoggerFactory.getLogger(CharsetUtils.class);

  public static final Charset forName(@Nullable final String charsetName,
      @Nullable final Charset defaultCharset) {
    Charset charset;
    if (charsetName == null) {
      LOGGER.debug("The charset name is null, use default '{}'", defaultCharset);
      charset = defaultCharset;
    } else {
      try {
        charset = Charset.forName(charsetName);
        LOGGER.debug("Successfully create Charset of name '{}'.", charsetName);
      } catch (final Exception e) {
        LOGGER.warn("Failed to create Charset of name '{}', use the default '{}'",
            charsetName, defaultCharset);
        charset = defaultCharset;
      }
    }
    return charset;
  }

  private CharsetUtils() {}
}
