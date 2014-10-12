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
package com.github.haixing_hu.lang;

import java.io.Serializable;

import javax.annotation.concurrent.ThreadSafe;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * This class provides operations on {@code Object}.
 *
 * This class tries to handle {@code null} input gracefully. An exception
 * will generally not be thrown for a {@code null} input. Each method
 * documents its behavior in more detail.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class ObjectUtils {

  /**
   * Singleton used as a {@code null} place holder where {@code null}
   * has another meaning.
   *
   * For example, in a {@code HashMap} the
   * {@link java.util.HashMap#get(java.lang.Object)} method returns
   * {@code null} if the {@code Map} contains {@code null} or if
   * there is no matching key. The {@code Null} placeholder can be used to
   * distinguish between these two cases.
   *
   * Another example is {@code Hashtable}, where {@code null} cannot
   * be stored.
   *
   * This instance is Serializable.
   */
  public static final Null NULL = new Null();

  private ObjectUtils() { }

  /**
   * Returns a default value if the object passed is {@code null}.
   *
   * <pre>
   * Objects.defaultIfNull(null, null)      = null
   * Objects.defaultIfNull(null, "")        = ""
   * Objects.defaultIfNull(null, "zz")      = "zz"
   * Objects.defaultIfNull("abc", *)        = "abc"
   * Objects.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
   * </pre>
   *
   * @param obj
   *          the {@code Object} to test, may be {@code null}
   * @param defaultValue
   *          the default value to return, may be {@code null}
   * @return {@code object} if it is not {@code null}, defaultValue
   *         otherwise
   */
  public static <T> T defaultIfNull(final T obj, final T defaultValue) {
    return (obj != null ? obj : defaultValue);
  }

  /**
   * Gets the hash code of an object returning zero when the object is
   * {@code null}.
   *
   * <pre>
   * Objects.hashCode(null)   = 0
   * Objects.hashCode(obj)    = obj.hashCode()
   * </pre>
   *
   * @param obj
   *          the object to obtain the hash code of, may be {@code null}
   * @return the hash code of the object, or zero if null
   */
  public static int hashCode(final Object obj) {
    if (obj == null) {
      return 0;
    } else {
      return obj.hashCode();
    }
  }

  /**
   * Gets the toString that would be produced by {@code Object} if a class
   * did not override toString itself. {@code null} will return
   * {@code null}.
   *
   * <pre>
   * Objects.identityToString(null)         = null
   * Objects.identityToString("")           = "java.lang.String@1e23"
   * Objects.identityToString(Boolean.TRUE) = "java.lang.Boolean@7fa"
   * </pre>
   *
   * @param object
   *          the object to create a toString for, may be {@code null}
   * @return the default toString text, or {@code null} if
   *         {@code null} passed in
   */
  public static String identityToString(final Object object) {
    if (object == null) {
      return null;
    }
    final StringBuilder builder = new StringBuilder();
    identityToString(builder, object);
    return builder.toString();
  }

  /**
   * Appends the toString that would be produced by {@code Object} if a
   * class did not override toString itself. {@code null} will throw a
   * NullPointerException for either of the two parameters.
   *
   * <pre>
   * Objects.identityToString(buf, "")            = buf.append("java.lang.String@1e23"
   * Objects.identityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa"
   * Objects.identityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa")
   * </pre>
   *
   * @param buffer
   *          the buffer to append to
   * @param object
   *          the object to create a toString for
   */
  public static void identityToString(final StringBuilder builder, final Object object) {
    requireNonNull("object", object);
    builder.append(object.getClass().getName())
           .append('@')
           .append( Integer.toHexString(System.identityHashCode(object)) );
  }

  /**
   * Gets the {@code toString} of an {@code Object} returning an empty
   * string ("") if {@code null} input.
   *
   * <pre>
   * Objects.toString(null)         = ""
   * Objects.toString("")           = ""
   * Objects.toString("bat")        = "bat"
   * Objects.toString(Boolean.TRUE) = "true"
   * </pre>
   *
   * @see StringUtils#defaultString(String)
   * @see String#valueOf(Object)
   * @param obj
   *          the Object to {@code toString}, may be null
   * @return the passed in Object's toString, or nullStr if {@code null}
   *         input
   */
  public static String toString(final Object obj) {
    return (obj == null ? StringUtils.EMPTY : obj.toString());
  }

  /**
   * Gets the {@code toString} of an {@code Object} returning a
   * specified text if {@code null} input.
   *
   * <pre>
   * Objects.toString(null, null)           = null
   * Objects.toString(null, "null")         = "null"
   * Objects.toString("", "null")           = ""
   * Objects.toString("bat", "null")        = "bat"
   * Objects.toString(Boolean.TRUE, "null") = "true"
   * </pre>
   *
   * @see StringUtils#defaultString(String, String)
   * @see String#valueOf(Object)
   * @param obj
   *          the Object to {@code toString}, may be null
   * @param nullStr
   *          the String to return if {@code null} input, may be null
   * @return the passed in Object's toString, or nullStr if {@code null}
   *         input
   */
  public static String toString(final Object obj, final String nullStr) {
    return (obj == null ? nullStr : obj.toString());
  }

  /**
   * Class used as a null place holder where {@code null} has another
   * meaning.
   *
   * For example, in a {@code HashMap} the
   * {@link java.util.HashMap#get(java.lang.Object)} method returns
   * {@code null} if the {@code Map} contains {@code null} or if
   * there is no matching key. The {@code Null} place holder can be used to
   * distinguish between these two cases.
   *
   * Another example is {@code Hashtable}, where {@code null} cannot
   * be stored.
   */
  public static class Null implements Serializable {

    private static final long serialVersionUID = 661457446116895424L;

    Null() {}

    /**
     * Ensure singleton.
     *
     * @return the singleton value
     */
    private Object readResolve() {
      return ObjectUtils.NULL;
    }
  }
}
