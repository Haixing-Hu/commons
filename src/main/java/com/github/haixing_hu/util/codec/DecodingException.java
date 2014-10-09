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
 * Thrown when there is a failure condition during the decoding process.
 * <p>
 * This exception is thrown when a {@link Decoder} encounters a decoding
 * specific exception such as invalid data, or characters outside of the
 * expected range.
 * </p>
 *
 * @author Haixing Hu
 */
public class DecodingException extends Exception {

  private static final long serialVersionUID = 5731557146723096837L;

  /**
   * Constructs a new exception with {@code null} as its detail message.
   * The cause is not initialized, and may subsequently be initialized by a call
   * to {@link #initCause}.
   */
  public DecodingException() {
    super();
  }

  /**
   * Constructs a new exception with the specified detail message. The cause is
   * not initialized, and may subsequently be initialized by a call to
   * {@link #initCause}.
   *
   * @param message
   *          The detail message which is saved for later retrieval by the
   *          {@link #getMessage()} method.
   */
  public DecodingException(final String message) {
    super(message);
  }

  /**
   * Constructsa new exception with the specified detail message and cause.
   * <p>
   * Note that the detail message associated with {@code cause} is not
   * automatically incorporated into this exception's detail message.
   * </p>
   *
   * @param message
   *          The detail message which is saved for later retrieval by the
   *          {@link #getMessage()} method.
   * @param cause
   *          The cause which is saved for later retrieval by the
   *          {@link #getCause()} method. A {@code null} value is
   *          permitted, and indicates that the cause is nonexistent or unknown.
   */
  public DecodingException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new exception with the specified cause and a detail message of
   * {@code (cause==null ?
   * null : cause.toString())} (which typically contains the class and
   * detail message of {@code cause}). This constructor is useful for
   * exceptions that are little more than wrappers for other throwables.
   *
   * @param cause
   *          The cause which is saved for later retrieval by the
   *          {@link #getCause()} method. A {@code null} value is
   *          permitted, and indicates that the cause is nonexistent or unknown.
   */
  public DecodingException(final Throwable cause) {
    super(cause);
  }
}
