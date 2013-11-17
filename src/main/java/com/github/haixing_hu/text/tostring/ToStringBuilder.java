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

package com.github.haixing_hu.text.tostring;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * This class assists in implementing {@link Object#toString()} methods. This
 * class enables a good and consistent <code>toString()</code> to be built for
 * any class or object. This class aims to simplify the process by:
 * <ul>
 * <li>allowing field names</li>
 * <li>handling all types consistently</li>
 * <li>handling nulls consistently</li>
 * <li>outputting arrays and multi-dimensional arrays</li>
 * <li>enabling the detail level to be controlled for Objects and Collections</li>
 * <li>handling class hierarchies</li>
 * </ul>
 * To use this class write code as follows:
 *
 * <pre>
 * public class Person {
 *   String name;
 *   int age;
 *   boolean smoker;
 *
 *   ...
 *
 *   public String toString() {
 *     return new ToStringBuilder(this)
 *                .append("name", name);
 *                .append("age", age);
 *                .append("smoker", smoker)
 *                .toString();
 *   }
 * }
 * </pre>
 *
 * This will produce a toString of the format:
 *
 * <pre>
 *   Person@7f54[name=Stephen,age=29,smoker=false]
 * </pre>
 *
 * To add the superclass <code>toString</code>, use {@link #appendSuper}. To
 * append the <code>toString</code> from an object that is delegated to (or any
 * other object), use {@link #appendToString}. The exact format of the
 * <code>toString</code> is determined by the {@link ToStringStyle} passed into
 * the constructor. This class has a static StringBuilder object for each
 * thread, and the program could safely call the static function of this class
 * to implement the toString() method.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class ToStringBuilder {

  private static final int INITIAL_BUFFER_SIZE = 128;

  private final StringBuilder builder;
  private ToStringStyle style;
  private Object object;

  public ToStringBuilder() {
    builder = new StringBuilder(INITIAL_BUFFER_SIZE);
    style = ToStringStyle.getDefault();
    object = null;
  }

  public ToStringBuilder(final ToStringStyle style) {
    this.builder = new StringBuilder(INITIAL_BUFFER_SIZE);
    this.style = requireNonNull("style", style);
    this.object = null;
  }

  public ToStringBuilder(@Nullable final Object object) {
    this.builder = new StringBuilder(INITIAL_BUFFER_SIZE);
    this.style = ToStringStyle.getDefault();
    this.object = object;
    this.style.appendStart(this.builder, this.object);
  }

  public ToStringBuilder(final ToStringStyle style, @Nullable final Object object) {
    this.builder = new StringBuilder(INITIAL_BUFFER_SIZE);
    this.style = requireNonNull("style", style);
    this.object = object;
    this.style.appendStart(this.builder, this.object);
  }

  public ToStringStyle getStyle() {
    return style;
  }

  public void setStyle(final ToStringStyle style) {
    this.style = requireNonNull("style", style);
  }

  public int length() {
    return builder.length();
  }

  public void clear() {
    this.object = null;
    this.builder.setLength(0);
  }

  public ToStringBuilder reset(@Nullable final Object object) {
    this.object = object;
    this.builder.setLength(0);
    this.style.appendStart(this.builder, this.object);
    return this;
  }

  public ToStringBuilder append(final boolean value) {
    style.append(builder, null, value);
    return this;
  }

  public ToStringBuilder append(@Nullable final boolean[] value) {
    style.append(builder, null, value, null);
    return this;
  }

  public ToStringBuilder append(final char value) {
    style.append(builder, null, value);
    return this;
  }

  public ToStringBuilder append(@Nullable final char[] value) {
    style.append(builder, null, value, null);
    return this;
  }

  public ToStringBuilder append(final byte value) {
    style.append(builder, null, value);
    return this;
  }

  public ToStringBuilder append(@Nullable final byte[] value) {
    style.append(builder, null, value, null);
    return this;
  }

  public ToStringBuilder append(final short value) {
    style.append(builder, null, value);
    return this;
  }

  public ToStringBuilder append(@Nullable final short[] value) {
    style.append(builder, null, value, null);
    return this;
  }

  public ToStringBuilder append(final int value) {
    style.append(builder, null, value);
    return this;
  }

  public ToStringBuilder append(@Nullable final int[] value) {
    style.append(builder, null, value, null);
    return this;
  }

  public ToStringBuilder append(final long value) {
    style.append(builder, null, value);
    return this;
  }

  public ToStringBuilder append(@Nullable final long[] value) {
    style.append(builder, null, value, null);
    return this;
  }

  public ToStringBuilder append(final float value) {
    style.append(builder, null, value);
    return this;
  }

  public ToStringBuilder append(@Nullable final float[] value) {
    style.append(builder, null, value, null);
    return this;
  }

  public ToStringBuilder append(final double value) {
    style.append(builder, null, value);
    return this;
  }

  public ToStringBuilder append(@Nullable final double[] value) {
    style.append(builder, null, value, null);
    return this;
  }

  public ToStringBuilder append(@Nullable final Object value) {
    style.append(builder, null, value, null);
    return this;
  }

  public ToStringBuilder append(@Nullable final Object[] value) {
    style.append(builder, null, value, null);
    return this;
  }

  public ToStringBuilder append(final String name, final boolean value) {
    style.append(builder, name, value);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final boolean[] value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final boolean[] value, final boolean fullDetail) {
    style.append(builder, name, value, (fullDetail ? Boolean.TRUE
        : Boolean.FALSE));
    return this;
  }

  public ToStringBuilder append(final String name, final char value) {
    style.append(builder, name, value);
    return this;
  }

  public ToStringBuilder append(final String name, @Nullable final char[] value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final char[] value, final boolean fullDetail) {
    style.append(builder, name, value, (fullDetail ? Boolean.TRUE
        : Boolean.FALSE));
    return this;
  }

  public ToStringBuilder append(final String name, final byte value) {
    style.append(builder, name, value);
    return this;
  }

  public ToStringBuilder append(final String name, @Nullable final byte[] value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final byte[] value, final boolean fullDetail) {
    style.append(builder, name, value, (fullDetail ? Boolean.TRUE
        : Boolean.FALSE));
    return this;
  }

  public ToStringBuilder append(final String name, final short value) {
    style.append(builder, name, value);
    return this;
  }

  public ToStringBuilder append(final String name, @Nullable final short[] value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final short[] value, final boolean fullDetail) {
    style.append(builder, name, value, (fullDetail ? Boolean.TRUE
        : Boolean.FALSE));
    return this;
  }

  public ToStringBuilder append(final String name, final int value) {
    style.append(builder, name, value);
    return this;
  }

  public ToStringBuilder append(final String name, @Nullable final int[] value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name, @Nullable final int[] value,
      final boolean fullDetail) {
    style.append(builder, name, value, (fullDetail ? Boolean.TRUE
        : Boolean.FALSE));
    return this;
  }

  public ToStringBuilder append(final String name, final long value) {
    style.append(builder, name, value);
    return this;
  }

  public ToStringBuilder append(final String name, @Nullable final long[] value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final long[] value, final boolean fullDetail) {
    style.append(builder, name, value, (fullDetail ? Boolean.TRUE
        : Boolean.FALSE));
    return this;
  }

  public ToStringBuilder append(final String name, final float value) {
    style.append(builder, name, value);
    return this;
  }

  public ToStringBuilder append(final String name, @Nullable final float[] value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final float[] value, final boolean fullDetail) {
    style.append(builder, name, value, (fullDetail ? Boolean.TRUE
        : Boolean.FALSE));
    return this;
  }

  public ToStringBuilder append(final String name, final double value) {
    style.append(builder, name, value);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final double[] value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final double[] value, final boolean fullDetail) {
    style.append(builder, name, value, (fullDetail ? Boolean.TRUE
        : Boolean.FALSE));
    return this;
  }

  public ToStringBuilder append(final String name, @Nullable final Object value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final Object value, final boolean fullDetail) {
    style.append(builder, name, value, (fullDetail ? Boolean.TRUE
        : Boolean.FALSE));
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final Object[] value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final Object[] value, final boolean fullDetail) {
    style.append(builder, name, value, (fullDetail ? Boolean.TRUE
        : Boolean.FALSE));
    return this;
  }

  /**
   * Append the <code>toString</code> from the superclass. This method assumes
   * that the superclass uses the same <code>ToStringStyle</code> as this one.
   * If <code>superToString</code> is <code>null</code>, no change is made.
   *
   * @param superToString
   *          the result of <code>super.toString()</code>
   */
  public ToStringBuilder appendSuper(@Nullable final String superToString) {
    if (superToString != null) {
      style.appendSuper(builder, superToString);
    }
    return this;
  }

  /**
   * Append the <code>toString</code> from another object. This method is useful
   * where a class delegates most of the implementation of its properties to
   * another class. You can then call <code>toString()</code> on the other class
   * and pass the result into this method.
   *
   * <pre>
   *   private AnotherObject delegate;
   *   private int age;
   *   private boolean smoker;
   *
   *   public String toString() {
   *     final ToStringBuilderPool pool = ToStringBuilderPool.getInstance();
   *     final ToStringStyle style = ToStringStyle.getDefault();
   *     final ToStringBuilder builder = pool.borrow(style);
   *     try {
   *       return builder.reset(this)
   *                     .appendToString(delegate.toString());
   *                     .append("age", age);
   *                     .append("smoker", smoker)
   *                     .toString();
   *     } finally {
   *       pool.restore(style, builder);
   *     }
   *   }
   * </pre>
   *
   * This method assumes that the other object uses the same
   * <code>ToStringStyle</code> as this one. If the <code>toString</code> is
   * <code>null</code>, no change is made.
   *
   * @param toString
   *          the result of <code>toString()</code> on another object.
   */
  public ToStringBuilder appendToString(@Nullable final String toString) {
    if (toString != null) {
      style.appendToString(builder, null, toString);
    }
    return this;
  }

  /**
   * Append the <code>toString</code> from another object. This method is useful
   * where a class delegates most of the implementation of its properties to
   * another class. You can then call <code>toString()</code> on the other class
   * and pass the result into this method.
   *
   * <pre>
   *   private AnotherObject delegate;
   *   private int age;
   *   private boolean smoker;
   *
   *   public String toString() {
   *     final ToStringBuilderPool pool = ToStringBuilderPool.getInstance();
   *     final ToStringStyle style = ToStringStyle.getDefault();
   *     final ToStringBuilder builder = pool.borrow(style);
   *     try {
   *       return builder.reset(this)
   *                     .appendToString("delegate", delegate.toString());
   *                     .append("age", age);
   *                     .append("smoker", smoker)
   *                     .toString();
   *     } finally {
   *       pool.restore(style, builder);
   *     }
   *   }
   * </pre>
   *
   * This method assumes that the other object uses the same
   * <code>ToStringStyle</code> as this one. If the <code>toString</code> is
   * <code>null</code>, no change is made.
   *
   * @param fieldName
   *          the field name.
   * @param toString
   *          the result of <code>toString()</code> on the field.
   */
  public ToStringBuilder appendToString(final String fieldName,
      @Nullable final String toString) {
    if (toString != null) {
      style.appendToString(builder, fieldName, toString);
    }
    return this;
  }

  @Override
  public String toString() {
    if ((object == null) && (builder.length() == 0)) {
      builder.append(style.getNullText());
    } else {
      style.appendEnd(builder, object);
    }
    return builder.toString();
  }
}
