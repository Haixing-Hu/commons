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
package com.github.haixing_hu.util.codec;

/**
 * Provides the interface to encode an object to another.
 *
 * @author Haixing Hu
 */
public interface Encoder<FROM, TO> {

  /**
   * Encodes an object to another.
   *
   * @param source
   *          the source object to be encoded.
   * @return the encoding result.
   * @throws EncodingException
   *           if any encoding error occurred.
   */
  public TO encode(FROM source) throws EncodingException;

}
