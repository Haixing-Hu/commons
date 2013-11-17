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

package com.github.haixing_hu.util.codec;

/**
 * Provides the interface to decode an object to another.
 *
 * @author Haixing Hu
 */
public interface Decoder<FROM, TO> {

  /**
   * Decodes an object to another.
   *
   * @param source
   *          the source object to be decoded.
   * @return the decoding result.
   * @throws DecodingException
   *           if any decoding error occurred.
   */
  public TO decode(FROM source) throws DecodingException;

}
