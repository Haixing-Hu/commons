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
package com.github.haixing_hu.util.transformer.string;

import javax.annotation.concurrent.Immutable;

import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

/**
 * A {@link StripEndTransformer} transform a string by stripping the
 * trailing whitespace and non-printable characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class StripEndTransformer extends AbstractStringTransformer {

  public static final StripEndTransformer INSTANCE = new StripEndTransformer();

  public StripEndTransformer() {
    // empty
  }

  @Override
  public String transform(final String str) {
    return StringUtils.stripEnd(str);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).toString();
  }
}
