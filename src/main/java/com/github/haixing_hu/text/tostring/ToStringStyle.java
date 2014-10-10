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

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Stack;

import javax.annotation.concurrent.Immutable;

import com.github.haixing_hu.CommonsConfig;
import com.github.haixing_hu.config.Config;
import com.github.haixing_hu.lang.ClassUtils;
import com.github.haixing_hu.lang.Equality;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.lang.ObjectUtils;
import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.lang.SystemUtils;

/**
 * Controls {@code String} formatting for {@link ToStringBuilder}.
 * <p>
 * These classes are intended to be used as {@code Singletons}. There is no
 * need to instantiate a new style each time. A program will generally use one
 * of the predefined constants on this class. Alternatively, the
 * {@link StandardToStringStyle} class can be used to set the individual
 * settings. Thus most styles can be achieved without sub-classing.
 * </p>
 * <p>
 * If required, a subclass can override as many or as few of the methods as it
 * requires. Each object type (from {@code boolean} to {@code long} to
 * {@code Object} to {@code int[]}) has its own methods to output it.
 * Most have two versions, detail and summary.
 * </p>
 * <p>
 * For example, the detail version of the array based methods will output the
 * whole array, whereas the summary method will just output the array length.
 * </p>
 * <p>
 * If you want to format the output of certain objects, such as dates, you must
 * create a subclass and override a method.
 * </p>
 *
 * <pre>
 * public class MyStyle extends ToStringStyle {
 *   protected void appendDetail(StringBuilder builder, String fieldName,
 *       Object value) {
 *     if (value instanceof Date) {
 *       value = new SimpleDateFormat(&quot;yyyy-MM-dd&quot;).format(value);
 *     }
 *     builder.append(value);
 *   }
 * }
 * </pre>
 *
 * @author Haixing Hu
 */
@Immutable
public class ToStringStyle implements Serializable {

  private static final long serialVersionUID = - 3342036824434541804L;

  /**
   * The value of this property is the class name of the implementation of the
   * default {@link ToStringStyle} used by the {@link ToStringBuilder}.
   *
   * <table border="1">
   * <tr>
   * <th>Type</th>
   * <th>Count</th>
   * <th>Value</th>
   * <th>Required</th>
   * <th>Default</th>
   * <th>Range</th>
   * </tr>
   * <tr>
   * <td>class</td>
   * <td>1</td>
   * <td>the class name of the implementation of the default
   * {@link ToStringStyle} used by the {@link ToStringBuilder}.</td>
   * <td>no</td>
   * <td>{@link DefaultToStringStyle#INSTANCE}</td>
   * <td></td>
   * </tr>
   * </table>
   *
   * @see #DEFAULT
   */
  public static final String PROPERTY_DEFAULT =
    "com.github.haixing_hu.text.tostring.ToStringStyle.default";

  private static volatile ToStringStyle defaultStyle = null;

  /**
   * Gets the default {@link ToStringStyle}, which will be loaded from the
   * configuration of the commons module.
   *
   * @return the default {@link ToStringStyle}.
   */
  public static ToStringStyle getDefault() {
    if (defaultStyle == null) {
      synchronized (ToStringStyle.class) {
        if (defaultStyle == null) {
          final Config config = CommonsConfig.get();
          defaultStyle = config.getInstance(PROPERTY_DEFAULT,
              DefaultToStringStyle.INSTANCE);
        }
      }
    }
    return defaultStyle;
  }

  /**
   * A registry of objects used to detect cyclically object references and avoid
   * infinite loops.
   * <p>
   * For example, suppose we have the following class and objects:
   * </p>
   *
   * <pre>
   * public class ObjectCycle {
   *   public Object obj;
   *   public String toString() {
   *     return new ToStringBuilder(this).append(obj.toString()).toString();
   *   }
   * }
   * ObjectCycle a, b;
   * a.obj = b;
   * b.obj = a;
   * System.out.println(a.toString());
   * </pre>
   * <p>
   * Note that the {@code a.toString()} will call {@code a.obj.toString()},
   * that is, {@code b.toString()} , and {@code b.toString()} will call
   * {@code b.obj.toString()} , that is, {@code a.toString()} , then a
   * infinity loop occurs. In order to prevent this situation, we have to remember
   * all the object currently calling the ToStringBuildermethods in the current thread.
   * </p>
   */
  private static ThreadLocal<Stack<Object>> threadLocalStack =
    new ThreadLocal<Stack<Object>>() {
      @Override
      protected Stack<Object> initialValue() {
        // The HashSet implementation is not synchronized,
        // which is just what we need here.
        return new Stack<Object>();
      }
    };

  static boolean stackContains(final Object object) {
    final Stack<Object> stack = threadLocalStack.get();
    for (final Object obj : stack) {
      // note that we only need to compare the REFERENCE of objects, instead
      // of the content of the objects.
      if (obj == object) {
        return true;
      }
    }
    return false;
  }

  static void pushStack(final Object object) {
    final Stack<Object> stack = threadLocalStack.get();
    stack.push(object);
  }

  static void popStack(final Object object) {
    final Stack<Object> stack = threadLocalStack.get();
    if (! stack.isEmpty()) {
      final Object top = stack.peek();
      if (object == top) {
        stack.pop();
      }
    }
  }

  /**
   * Whether to use the field names, the default is {@code true}.
   */
  private boolean useFieldNames = true;

  /**
   * Whether to use the class name, the default is {@code true}.
   */
  private boolean useClassName = true;

  /**
   * Whether to use short class names, the default is {@code false}.
   */
  private boolean useShortClassName = false;

  /**
   * Whether to use the identity hash code, the default is {@code true}.
   */
  private boolean useIdentityHashCode = true;

  /**
   * The content start {@code '['}.
   */
  private String contentStart = "[";

  /**
   * The content end {@code ']'}.
   */
  private String contentEnd = "]";

  /**
   * The field name value separator {@code '='}.
   */
  private String fieldNameValueSeparator = "=";

  /**
   * Whether the field separator should be added before any other fields.
   */
  private boolean fieldSeparatorAtStart = false;

  /**
   * Whether the field separator should be added after any other fields.
   */
  private boolean fieldSeparatorAtEnd = false;

  /**
   * The field separator {@code ','}.
   */
  private String fieldSeparator = ",";

  /**
   * The array start {@code '{'}.
   */
  private String arrayStart = "{";

  /**
   * The array separator {@code ','}.
   */
  private String arraySeparator = ",";

  /**
   * The detail for array content.
   */
  private boolean arrayContentDetail = true;

  /**
   * The array end {@code '}'}.
   */
  private String arrayEnd = "}";

  /**
   * The value to use when fullDetail is {@code null}, the default value is
   * {@code true}.
   */
  private boolean defaultFullDetail = true;

  /**
   * The {@code null} text {@code '&lt;null&gt;'}.
   */
  private String nullText = "<null>";

  /**
   * The summary size text start <code>'<size'</code>.
   */
  private String sizeStartText = "<size=";

  /**
   * The summary size text start {@code '&gt;'}.
   */
  private String sizeEndText = ">";

  /**
   * The summary object text start {@code '&lt;'}.
   */
  private String summaryObjectStartText = "<";

  /**
   * The summary object text start {@code '&gt;'}.
   */
  private String summaryObjectEndText = ">";

  /**
   * Protected constructor.
   */
  protected ToStringStyle() {}

  /**
   * Append to the {@code toString} the superclass toString. NOTE: It
   * assumes that the toString has been created from the same ToStringStyle. A
   * {@code null} {@code superToString} is ignored.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param superToString
   *          the {@code super.toString()}
   */
  public void appendSuper(final StringBuilder builder,
      final String superToString) {
    if (superToString != null) {
      final int pos1 = superToString.indexOf(contentStart)
          + contentStart.length();
      final int pos2 = superToString.lastIndexOf(contentEnd);
      if ((pos1 != pos2) && (pos1 >= 0) && (pos2 >= 0)) {
        final String data = superToString.substring(pos1, pos2);
        if (fieldSeparatorAtStart) {
          removeLastFieldSeparator(builder);
        }
        builder.append(data);
        appendFieldSeparator(builder);
      }
    }
  }

  /**
   * Append to the {@code toString} another toString. NOTE: It assumes that
   * the toString has been created from the same ToStringStyle. A
   * {@code null} {@code toString} is ignored.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name.
   * @param toString
   *          the additional {@code toString}
   */
  public void appendToString(final StringBuilder builder,
      final String fieldName, final String toString) {
    if (toString == null) {
      appendFieldStart(builder, fieldName);
      appendNullText(builder, fieldName);
      appendFieldEnd(builder, fieldName);
    } else {
      final int pos1 = toString.indexOf(contentStart) + contentStart.length();
      final int pos2 = toString.lastIndexOf(contentEnd);
      if ((pos1 != pos2) && (pos1 >= 0) && (pos2 >= 0)) {
        final String data = toString.substring(pos1, pos2);
        if (fieldSeparatorAtStart) {
          removeLastFieldSeparator(builder);
        }
        appendFieldStart(builder, fieldName);
        builder.append(data);
        appendFieldEnd(builder, fieldName);
      } else {
        if (fieldName != null) {
          appendFieldStart(builder, fieldName);
          builder.append(toString);
          appendFieldEnd(builder, fieldName);
        }
      }
    }
  }

  /**
   * Append to the {@code toString} the start of data indicator.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param object
   *          the {@code Object} to build a {@code toString} for
   */
  public void appendStart(final StringBuilder builder, final Object object) {
    if (object != null) {
      pushStack(object);
      appendClassName(builder, object);
      appendIdentityHashCode(builder, object);
      appendContentStart(builder);
      if (fieldSeparatorAtStart) {
        appendFieldSeparator(builder);
      }
    }
  }

  /**
   * Append to the {@code toString} the end of data indicator.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param object
   *          the {@code Object} to build a {@code toString} for.
   */
  public void appendEnd(final StringBuilder builder, final Object object) {
    if (fieldSeparatorAtEnd == false) {
      removeLastFieldSeparator(builder);
    }
    appendContentEnd(builder);
    popStack(object);
  }

  /**
   * Remove the last field separator from the buffer.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   */
  protected void removeLastFieldSeparator(final StringBuilder builder) {
    final int len = builder.length();
    final int sepLen = fieldSeparator.length();
    if ((len > 0) && (sepLen > 0) && (len >= sepLen)) {
      boolean match = true;
      for (int i = 0; i < sepLen; i++) {
        if (builder.charAt(len - 1 - i) != fieldSeparator.charAt(sepLen - 1 - i)) {
          match = false;
          break;
        }
      }
      if (match) {
        builder.setLength(len - sepLen);
      }
    }
  }

  /**
   * Append to the {@code toString} an {@code Object} value, printing
   * the full {@code toString} of the {@code Object} passed in.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name
   * @param value
   *          the value to add to the {@code toString}
   * @param fullDetail
   *          {@code true} for detail, {@code false} for summary info,
   *          {@code null} for style decides
   */
  public void append(final StringBuilder builder, final String fieldName,
      final Object value, final Boolean fullDetail) {
    appendFieldStart(builder, fieldName);
    if (value == null) {
      appendNullText(builder, fieldName);
    } else {
      appendInternal(builder, fieldName, value, isFullDetail(fullDetail));
    }
    appendFieldEnd(builder, fieldName);
  }

  /**
   * Append to the {@code toString} an {@code Object}, correctly
   * interpreting its type. This method performs the main lookup by Class type
   * to correctly route arrays, {@code Collections}, {@code Maps} and
   * {@code Objects} to the appropriate method. Either detail or summary
   * views can be specified. If a cycle is detected, an object will be appended
   * with the {@code Object.toString()} format.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param value
   *          the value to add to the {@code toString}, not
   *          {@code null}
   * @param detail
   *          output detail or not
   */
  protected void appendInternal(final StringBuilder builder,
      final String fieldName, final Object value, final boolean detail) {

    if (stackContains(value)
        && ! ((value instanceof Number) || (value instanceof Boolean)
            || (value instanceof Character) || (value instanceof String) || (value instanceof Enum<?>))) {
      appendCyclicObject(builder, fieldName, value);
      return;
    }

    pushStack(value);
    try {
      if (value instanceof Enum<?>) {
        if (detail) {
          appendDetail(builder, fieldName, ((Enum<?>) value).name());
        } else {
          appendSummary(builder, fieldName, ((Enum<?>) value).name());
        }
      } else if (value instanceof Collection<?>) {
        if (detail) {
          appendDetail(builder, fieldName, (Collection<?>) value);
        } else {
          appendSummarySize(builder, fieldName, ((Collection<?>) value).size());
        }
      } else if (value instanceof Map<?, ?>) {
        if (detail) {
          appendDetail(builder, fieldName, (Map<?, ?>) value);
        } else {
          appendSummarySize(builder, fieldName, ((Map<?, ?>) value).size());
        }
      } else if (value instanceof long[]) {
        if (detail) {
          appendDetail(builder, fieldName, (long[]) value);
        } else {
          appendSummary(builder, fieldName, (long[]) value);
        }
      } else if (value instanceof int[]) {
        if (detail) {
          appendDetail(builder, fieldName, (int[]) value);
        } else {
          appendSummary(builder, fieldName, (int[]) value);
        }
      } else if (value instanceof short[]) {
        if (detail) {
          appendDetail(builder, fieldName, (short[]) value);
        } else {
          appendSummary(builder, fieldName, (short[]) value);
        }
      } else if (value instanceof byte[]) {
        if (detail) {
          appendDetail(builder, fieldName, (byte[]) value);
        } else {
          appendSummary(builder, fieldName, (byte[]) value);
        }
      } else if (value instanceof char[]) {
        if (detail) {
          appendDetail(builder, fieldName, (char[]) value);
        } else {
          appendSummary(builder, fieldName, (char[]) value);
        }
      } else if (value instanceof double[]) {
        if (detail) {
          appendDetail(builder, fieldName, (double[]) value);
        } else {
          appendSummary(builder, fieldName, (double[]) value);
        }
      } else if (value instanceof float[]) {
        if (detail) {
          appendDetail(builder, fieldName, (float[]) value);
        } else {
          appendSummary(builder, fieldName, (float[]) value);
        }
      } else if (value instanceof boolean[]) {
        if (detail) {
          appendDetail(builder, fieldName, (boolean[]) value);
        } else {
          appendSummary(builder, fieldName, (boolean[]) value);
        }
      } else if (value.getClass().isArray()) {
        if (detail) {
          appendDetail(builder, fieldName, (Object[]) value);
        } else {
          appendSummary(builder, fieldName, (Object[]) value);
        }
      } else {
        if (detail) {
          appendDetail(builder, fieldName, value);
        } else {
          appendSummary(builder, fieldName, value);
        }
      }
    } finally {
      popStack(value);
    }
  }

  /**
   * Append to the {@code toString} an {@code Object} value that has
   * been detected to participate in a cycle. This implementation will print the
   * standard string value of the value.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param value
   *          the value to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendCyclicObject(final StringBuilder builder,
      final String fieldName, final Object value) {
    ObjectUtils.identityToString(builder, value);
  }

  /**
   * Append to the {@code toString} an {@code Object} value, printing
   * the full detail of the {@code Object}.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param value
   *          the value to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendDetail(final StringBuilder builder,
      final String fieldName, final Object value) {
//    if (value instanceof Date) {
//      //  NOTE: in most cases, we should print the date as milliseconds, since
//      //  it's easier to compare the dates in this form.
//      final Date date = (Date) value;
//      builder.append(date.getTime());
//    } else {
      builder.append(value.toString());
//    }
  }

  /**
   * Append to the {@code toString} a {@code Collection}.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param coll
   *          the {@code Collection} to add to the {@code toString},
   *          not {@code null}
   */
  protected void appendDetail(final StringBuilder builder,
      final String fieldName, final Collection<?> coll) {
    builder.append(coll.toString());
  }

  /**
   * Append to the {@code toString} a <code>Map<code>.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param map
   *          the {@code Map} to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendDetail(final StringBuilder builder,
      final String fieldName, final Map<?, ?> map) {
    builder.append(map.toString());
  }

  /**
   * Append to the {@code toString} an {@code Object} value, printing
   * a summary of the {@code Object}.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param value
   *          the value to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendSummary(final StringBuilder builder,
      final String fieldName, final Object value) {
    builder.append(summaryObjectStartText);
    builder.append(ClassUtils.getShortClassName(value.getClass()));
    builder.append(summaryObjectEndText);
  }

  /**
   * Append to the {@code toString} a {@code long} value.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name
   * @param value
   *          the value to add to the {@code toString}
   */
  public void append(final StringBuilder builder, final String fieldName,
      final long value) {
    appendFieldStart(builder, fieldName);
    appendDetail(builder, fieldName, value);
    appendFieldEnd(builder, fieldName);
  }

  /**
   * Append to the {@code toString} a {@code long} value.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param value
   *          the value to add to the {@code toString}
   */
  protected void appendDetail(final StringBuilder builder,
      final String fieldName, final long value) {
    builder.append(value);
  }

  /**
   * Append to the {@code toString} an {@code int} value.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name
   * @param value
   *          the value to add to the {@code toString}
   */
  public void append(final StringBuilder builder, final String fieldName,
      final int value) {
    appendFieldStart(builder, fieldName);
    appendDetail(builder, fieldName, value);
    appendFieldEnd(builder, fieldName);
  }

  /**
   * Append to the {@code toString} an {@code int} value.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param value
   *          the value to add to the {@code toString}
   */
  protected void appendDetail(final StringBuilder builder,
      final String fieldName, final int value) {
    builder.append(value);
  }

  /**
   * Append to the {@code toString} a {@code short} value.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name
   * @param value
   *          the value to add to the {@code toString}
   */
  public void append(final StringBuilder builder, final String fieldName,
      final short value) {
    appendFieldStart(builder, fieldName);
    appendDetail(builder, fieldName, value);
    appendFieldEnd(builder, fieldName);
  }

  /**
   * Append to the {@code toString} a {@code short} value.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param value
   *          the value to add to the {@code toString}
   */
  protected void appendDetail(final StringBuilder builder,
      final String fieldName, final short value) {
    builder.append(value);
  }

  // ----------------------------------------------------------------------------

  /**
   * Append to the {@code toString} a {@code byte} value.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name
   * @param value
   *          the value to add to the {@code toString}
   */
  public void append(final StringBuilder builder, final String fieldName,
      final byte value) {
    appendFieldStart(builder, fieldName);
    appendDetail(builder, fieldName, value);
    appendFieldEnd(builder, fieldName);
  }

  /**
   * Append to the {@code toString} a {@code byte} value.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param value
   *          the value to add to the {@code toString}
   */
  protected void appendDetail(final StringBuilder builder,
      final String fieldName, final byte value) {
    builder.append(value);
  }

  /**
   * Append to the {@code toString} a {@code char} value.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name
   * @param value
   *          the value to add to the {@code toString}
   */
  public void append(final StringBuilder builder, final String fieldName,
      final char value) {
    appendFieldStart(builder, fieldName);
    appendDetail(builder, fieldName, value);
    appendFieldEnd(builder, fieldName);
  }

  /**
   * Append to the {@code toString} a {@code char} value.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param value
   *          the value to add to the {@code toString}
   */
  protected void appendDetail(final StringBuilder builder,
      final String fieldName, final char value) {
    builder.append(value);
  }

  /**
   * Append to the {@code toString} a {@code double} value.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name
   * @param value
   *          the value to add to the {@code toString}
   */
  public void append(final StringBuilder builder, final String fieldName,
      final double value) {
    appendFieldStart(builder, fieldName);
    appendDetail(builder, fieldName, value);
    appendFieldEnd(builder, fieldName);
  }

  /**
   * Append to the {@code toString} a {@code double} value.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param value
   *          the value to add to the {@code toString}
   */
  protected void appendDetail(final StringBuilder builder,
      final String fieldName, final double value) {
    builder.append(value);
  }

  /**
   * Append to the {@code toString} a {@code float} value.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name
   * @param value
   *          the value to add to the {@code toString}
   */
  public void append(final StringBuilder builder, final String fieldName,
      final float value) {
    appendFieldStart(builder, fieldName);
    appendDetail(builder, fieldName, value);
    appendFieldEnd(builder, fieldName);
  }

  /**
   * Append to the {@code toString} a {@code float} value.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param value
   *          the value to add to the {@code toString}
   */
  protected void appendDetail(final StringBuilder builder,
      final String fieldName, final float value) {
    builder.append(value);
  }

  /**
   * Append to the {@code toString} a {@code boolean} value.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name
   * @param value
   *          the value to add to the {@code toString}
   */
  public void append(final StringBuilder builder, final String fieldName,
      final boolean value) {
    appendFieldStart(builder, fieldName);
    appendDetail(builder, fieldName, value);
    appendFieldEnd(builder, fieldName);
  }

  /**
   * Append to the {@code toString} a {@code boolean} value.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param value
   *          the value to add to the {@code toString}
   */
  protected void appendDetail(final StringBuilder builder,
      final String fieldName, final boolean value) {
    builder.append(value);
  }

  /**
   * Append to the {@code toString} an {@code Object} array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name
   * @param array
   *          the array to add to the toString
   * @param fullDetail
   *          {@code true} for detail, {@code false} for summary info,
   *          {@code null} for style decides
   */
  public void append(final StringBuilder builder, final String fieldName,
      final Object[] array, final Boolean fullDetail) {
    appendFieldStart(builder, fieldName);
    if (array == null) {
      appendNullText(builder, fieldName);
    } else if (isFullDetail(fullDetail)) {
      appendDetail(builder, fieldName, array);
    } else {
      appendSummary(builder, fieldName, array);
    }
    appendFieldEnd(builder, fieldName);
  }

  /**
   * Append to the {@code toString} the detail of an {@code Object}
   * array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param array
   *          the array to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendDetail(final StringBuilder builder,
      final String fieldName, final Object[] array) {
    builder.append(arrayStart);
    for (int i = 0; i < array.length; i++) {
      final Object item = array[i];
      if (i > 0) {
        builder.append(arraySeparator);
      }
      if (item == null) {
        appendNullText(builder, fieldName);
      } else {
        appendInternal(builder, fieldName, item, arrayContentDetail);
      }
    }
    builder.append(arrayEnd);
  }

  /**
   * Append to the {@code toString} the detail of an array type.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param array
   *          the array to add to the {@code toString}, not
   *          {@code null}
   * @since 2.0
   */
  protected void reflectionAppendArrayDetail(final StringBuilder builder,
      final String fieldName, final Object array) {
    builder.append(arrayStart);
    final int length = Array.getLength(array);
    for (int i = 0; i < length; i++) {
      final Object item = Array.get(array, i);
      if (i > 0) {
        builder.append(arraySeparator);
      }
      if (item == null) {
        appendNullText(builder, fieldName);
      } else {
        appendInternal(builder, fieldName, item, arrayContentDetail);
      }
    }
    builder.append(arrayEnd);
  }

  /**
   * Append to the {@code toString} a summary of an {@code Object}
   * array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param array
   *          the array to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendSummary(final StringBuilder builder,
      final String fieldName, final Object[] array) {
    appendSummarySize(builder, fieldName, array.length);
  }

  /**
   * Append to the {@code toString} a {@code long} array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name
   * @param array
   *          the array to add to the {@code toString}
   * @param fullDetail
   *          {@code true} for detail, {@code false} for summary info,
   *          {@code null} for style decides
   */
  public void append(final StringBuilder builder, final String fieldName,
      final long[] array, final Boolean fullDetail) {
    appendFieldStart(builder, fieldName);
    if (array == null) {
      appendNullText(builder, fieldName);
    } else if (isFullDetail(fullDetail)) {
      appendDetail(builder, fieldName, array);
    } else {
      appendSummary(builder, fieldName, array);
    }
    appendFieldEnd(builder, fieldName);
  }

  /**
   * Append to the {@code toString} the detail of a {@code long}
   * array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param array
   *          the array to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendDetail(final StringBuilder builder,
      final String fieldName, final long[] array) {
    builder.append(arrayStart);
    for (int i = 0; i < array.length; i++) {
      if (i > 0) {
        builder.append(arraySeparator);
      }
      appendDetail(builder, fieldName, array[i]);
    }
    builder.append(arrayEnd);
  }

  /**
   * Append to the {@code toString} a summary of a {@code long} array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param array
   *          the array to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendSummary(final StringBuilder builder,
      final String fieldName, final long[] array) {
    appendSummarySize(builder, fieldName, array.length);
  }

  /**
   * Append to the {@code toString} an {@code int} array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name
   * @param array
   *          the array to add to the {@code toString}
   * @param fullDetail
   *          {@code true} for detail, {@code false} for summary info,
   *          {@code null} for style decides
   */
  public void append(final StringBuilder builder, final String fieldName,
      final int[] array, final Boolean fullDetail) {
    appendFieldStart(builder, fieldName);
    if (array == null) {
      appendNullText(builder, fieldName);
    } else if (isFullDetail(fullDetail)) {
      appendDetail(builder, fieldName, array);
    } else {
      appendSummary(builder, fieldName, array);
    }
    appendFieldEnd(builder, fieldName);
  }

  /**
   * Append to the {@code toString} the detail of an {@code int}
   * array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param array
   *          the array to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendDetail(final StringBuilder builder,
      final String fieldName, final int[] array) {
    builder.append(arrayStart);
    for (int i = 0; i < array.length; i++) {
      if (i > 0) {
        builder.append(arraySeparator);
      }
      appendDetail(builder, fieldName, array[i]);
    }
    builder.append(arrayEnd);
  }

  /**
   * Append to the {@code toString} a summary of an {@code int} array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param array
   *          the array to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendSummary(final StringBuilder builder,
      final String fieldName, final int[] array) {
    appendSummarySize(builder, fieldName, array.length);
  }

  /**
   * Append to the {@code toString} a {@code short} array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name
   * @param array
   *          the array to add to the {@code toString}
   * @param fullDetail
   *          {@code true} for detail, {@code false} for summary info,
   *          {@code null} for style decides
   */
  public void append(final StringBuilder builder, final String fieldName,
      final short[] array, final Boolean fullDetail) {
    appendFieldStart(builder, fieldName);
    if (array == null) {
      appendNullText(builder, fieldName);
    } else if (isFullDetail(fullDetail)) {
      appendDetail(builder, fieldName, array);
    } else {
      appendSummary(builder, fieldName, array);
    }
    appendFieldEnd(builder, fieldName);
  }

  /**
   * Append to the {@code toString} the detail of a {@code short}
   * array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param array
   *          the array to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendDetail(final StringBuilder builder,
      final String fieldName, final short[] array) {
    builder.append(arrayStart);
    for (int i = 0; i < array.length; i++) {
      if (i > 0) {
        builder.append(arraySeparator);
      }
      appendDetail(builder, fieldName, array[i]);
    }
    builder.append(arrayEnd);
  }

  /**
   * Append to the {@code toString} a summary of a {@code short}
   * array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param array
   *          the array to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendSummary(final StringBuilder builder,
      final String fieldName, final short[] array) {
    appendSummarySize(builder, fieldName, array.length);
  }

  /**
   * Append to the {@code toString} a {@code byte} array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name
   * @param array
   *          the array to add to the {@code toString}
   * @param fullDetail
   *          {@code true} for detail, {@code false} for summary info,
   *          {@code null} for style decides
   */
  public void append(final StringBuilder builder, final String fieldName,
      final byte[] array, final Boolean fullDetail) {
    appendFieldStart(builder, fieldName);
    if (array == null) {
      appendNullText(builder, fieldName);
    } else if (isFullDetail(fullDetail)) {
      appendDetail(builder, fieldName, array);
    } else {
      appendSummary(builder, fieldName, array);
    }
    appendFieldEnd(builder, fieldName);
  }

  /**
   * Append to the {@code toString} the detail of a {@code byte}
   * array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param array
   *          the array to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendDetail(final StringBuilder builder,
      final String fieldName, final byte[] array) {
    builder.append(arrayStart);
    for (int i = 0; i < array.length; i++) {
      if (i > 0) {
        builder.append(arraySeparator);
      }
      appendDetail(builder, fieldName, array[i]);
    }
    builder.append(arrayEnd);
  }

  /**
   * Append to the {@code toString} a summary of a {@code byte} array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param array
   *          the array to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendSummary(final StringBuilder builder,
      final String fieldName, final byte[] array) {
    appendSummarySize(builder, fieldName, array.length);
  }

  /**
   * Append to the {@code toString} a {@code char} array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name
   * @param array
   *          the array to add to the {@code toString}
   * @param fullDetail
   *          {@code true} for detail, {@code false} for summary info,
   *          {@code null} for style decides
   */
  public void append(final StringBuilder builder, final String fieldName,
      final char[] array, final Boolean fullDetail) {
    appendFieldStart(builder, fieldName);
    if (array == null) {
      appendNullText(builder, fieldName);
    } else if (isFullDetail(fullDetail)) {
      appendDetail(builder, fieldName, array);
    } else {
      appendSummary(builder, fieldName, array);
    }
    appendFieldEnd(builder, fieldName);
  }

  /**
   * Append to the {@code toString} the detail of a {@code char}
   * array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param array
   *          the array to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendDetail(final StringBuilder builder,
      final String fieldName, final char[] array) {
    builder.append(arrayStart);
    for (int i = 0; i < array.length; i++) {
      if (i > 0) {
        builder.append(arraySeparator);
      }
      appendDetail(builder, fieldName, array[i]);
    }
    builder.append(arrayEnd);
  }

  /**
   * Append to the {@code toString} a summary of a {@code char} array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param array
   *          the array to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendSummary(final StringBuilder builder,
      final String fieldName, final char[] array) {
    appendSummarySize(builder, fieldName, array.length);
  }

  /**
   * Append to the {@code toString} a {@code double} array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name
   * @param array
   *          the array to add to the toString
   * @param fullDetail
   *          {@code true} for detail, {@code false} for summary info,
   *          {@code null} for style decides
   */
  public void append(final StringBuilder builder, final String fieldName,
      final double[] array, final Boolean fullDetail) {
    appendFieldStart(builder, fieldName);
    if (array == null) {
      appendNullText(builder, fieldName);
    } else if (isFullDetail(fullDetail)) {
      appendDetail(builder, fieldName, array);
    } else {
      appendSummary(builder, fieldName, array);
    }
    appendFieldEnd(builder, fieldName);
  }

  /**
   * Append to the {@code toString} the detail of a {@code double}
   * array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param array
   *          the array to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendDetail(final StringBuilder builder,
      final String fieldName, final double[] array) {
    builder.append(arrayStart);
    for (int i = 0; i < array.length; i++) {
      if (i > 0) {
        builder.append(arraySeparator);
      }
      appendDetail(builder, fieldName, array[i]);
    }
    builder.append(arrayEnd);
  }

  /**
   * Append to the {@code toString} a summary of a {@code double}
   * array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param array
   *          the array to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendSummary(final StringBuilder builder,
      final String fieldName, final double[] array) {
    appendSummarySize(builder, fieldName, array.length);
  }

  /**
   * Append to the {@code toString} a {@code float} array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name
   * @param array
   *          the array to add to the toString
   * @param fullDetail
   *          {@code true} for detail, {@code false} for summary info,
   *          {@code null} for style decides
   */
  public void append(final StringBuilder builder, final String fieldName,
      final float[] array, final Boolean fullDetail) {
    appendFieldStart(builder, fieldName);
    if (array == null) {
      appendNullText(builder, fieldName);
    } else if (isFullDetail(fullDetail)) {
      appendDetail(builder, fieldName, array);
    } else {
      appendSummary(builder, fieldName, array);
    }
    appendFieldEnd(builder, fieldName);
  }

  /**
   * Append to the {@code toString} the detail of a {@code float}
   * array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param array
   *          the array to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendDetail(final StringBuilder builder,
      final String fieldName, final float[] array) {
    builder.append(arrayStart);
    for (int i = 0; i < array.length; i++) {
      if (i > 0) {
        builder.append(arraySeparator);
      }
      appendDetail(builder, fieldName, array[i]);
    }
    builder.append(arrayEnd);
  }

  /**
   * Append to the {@code toString} a summary of a {@code float}
   * array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param array
   *          the array to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendSummary(final StringBuilder builder,
      final String fieldName, final float[] array) {
    appendSummarySize(builder, fieldName, array.length);
  }

  /**
   * Append to the {@code toString} a {@code boolean} array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name
   * @param array
   *          the array to add to the toString
   * @param fullDetail
   *          {@code true} for detail, {@code false} for summary info,
   *          {@code null} for style decides
   */
  public void append(final StringBuilder builder, final String fieldName,
      final boolean[] array, final Boolean fullDetail) {
    appendFieldStart(builder, fieldName);
    if (array == null) {
      appendNullText(builder, fieldName);
    } else if (isFullDetail(fullDetail)) {
      appendDetail(builder, fieldName, array);
    } else {
      appendSummary(builder, fieldName, array);
    }
    appendFieldEnd(builder, fieldName);
  }

  /**
   * Append to the {@code toString} the detail of a {@code boolean}
   * array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param array
   *          the array to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendDetail(final StringBuilder builder,
      final String fieldName, final boolean[] array) {
    builder.append(arrayStart);
    for (int i = 0; i < array.length; i++) {
      if (i > 0) {
        builder.append(arraySeparator);
      }
      appendDetail(builder, fieldName, array[i]);
    }
    builder.append(arrayEnd);
  }

  /**
   * Append to the {@code toString} a summary of a {@code boolean}
   * array.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param array
   *          the array to add to the {@code toString}, not
   *          {@code null}
   */
  protected void appendSummary(final StringBuilder builder,
      final String fieldName, final boolean[] array) {
    appendSummarySize(builder, fieldName, array.length);
  }

  /**
   * Append to the {@code toString} the class name.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param object
   *          the {@code Object} whose name to output
   */
  protected void appendClassName(final StringBuilder builder,
      final Object object) {
    if (useClassName && (object != null)) {
      if (useShortClassName) {
        builder.append(ClassUtils.getShortClassName(object.getClass()));
      } else {
        builder.append(object.getClass().getName());
      }
    }
  }

  /**
   * Append the {@link SystemUtils#identityHashCode(java.lang.Object)}.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param object
   *          the {@code Object} whose id to output
   */
  protected void appendIdentityHashCode(final StringBuilder builder,
      final Object object) {
    if (this.isUseIdentityHashCode() && (object != null)) {
      builder.append('@');
      builder.append(Integer.toHexString(System.identityHashCode(object)));
    }
  }

  /**
   * Append to the {@code toString} the content start.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   */
  protected void appendContentStart(final StringBuilder builder) {
    builder.append(contentStart);
  }

  /**
   * Append to the {@code toString} the content end.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   */
  protected void appendContentEnd(final StringBuilder builder) {
    builder.append(contentEnd);
  }

  /**
   * Append to the {@code toString} an indicator for {@code null}. The
   * default indicator is {@code '&lt;null&gt;'}.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   */
  protected void appendNullText(final StringBuilder builder,
      final String fieldName) {
    builder.append(nullText);
  }

  /**
   * Append to the {@code toString} the field separator.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   */
  protected void appendFieldSeparator(final StringBuilder builder) {
    builder.append(fieldSeparator);
  }

  /**
   * Append to the {@code toString} the field start.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name
   */
  protected void appendFieldStart(final StringBuilder builder,
      final String fieldName) {
    if (useFieldNames && (fieldName != null)) {
      builder.append(fieldName);
      builder.append(fieldNameValueSeparator);
    }
  }

  /**
   * Append to the <code>toString<code> the field end.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   */
  protected void appendFieldEnd(final StringBuilder builder,
      final String fieldName) {
    appendFieldSeparator(builder);
  }

  /**
   * Append to the {@code toString} a size summary. The size summary is
   * used to summarize the contents of {@code Collections},
   * {@code Maps} and arrays. The output consists of a prefix, the passed
   * in size and a suffix. The default format is <code>'&lt;size=n&gt;'<code>.
   *
   * @param builder
   *          the {@code StringBuilder} to populate
   * @param fieldName
   *          the field name, typically not used as already appended
   * @param size
   *          the size to append
   */
  protected void appendSummarySize(final StringBuilder builder,
      final String fieldName, final int size) {
    builder.append(sizeStartText);
    builder.append(size);
    builder.append(sizeEndText);
  }

  /**
   * Is this field to be output in full detail. This method converts a detail
   * request into a detail level. The calling code may request full detail (
   * {@code true}), but a subclass might ignore that and always return
   * {@code false}. The calling code may pass in {@code null}
   * indicating that it doesn't care about the detail level. In this case the
   * default detail level is used.
   *
   * @param fullDetailRequest
   *          the detail level requested
   * @return whether full detail is to be shown
   */
  public boolean isFullDetail(final Boolean fullDetailRequest) {
    if (fullDetailRequest == null) {
      return defaultFullDetail;
    }
    return fullDetailRequest.booleanValue();
  }

  // Setters and getters for the customizable parts of the style
  // These methods are not expected to be overridden, except to make public
  // (They are not public so that immutable subclasses can be written)
  // ---------------------------------------------------------------------

  /**
   * Gets whether to use the class name.
   *
   * @return the current useClassName flag
   */
  public boolean isUseClassName() {
    return useClassName;
  }

  /**
   * Sets whether to use the class name.
   *
   * @param useClassName
   *          the new useClassName flag
   */
  protected void setUseClassName(final boolean useClassName) {
    this.useClassName = useClassName;
  }

  /**
   * Gets whether to output short or long class names.
   *
   * @return the current useShortClassName flag
   */
  public boolean isUseShortClassName() {
    return useShortClassName;
  }

  /**
   * Sets whether to output short or long class names.
   *
   * @param useShortClassName
   *          the new useShortClassName flag
   */
  protected void setUseShortClassName(final boolean useShortClassName) {
    this.useShortClassName = useShortClassName;
  }

  /**
   * Gets whether to use the identity hash code.
   *
   * @return the current useIdentityHashCode flag
   */
  public boolean isUseIdentityHashCode() {
    return useIdentityHashCode;
  }

  /**
   * Sets whether to use the identity hash code.
   *
   * @param useIdentityHashCode
   *          the new useIdentityHashCode flag
   */
  protected void setUseIdentityHashCode(final boolean useIdentityHashCode) {
    this.useIdentityHashCode = useIdentityHashCode;
  }

  /**
   * Gets whether to use the field names passed in.
   *
   * @return the current useFieldNames flag
   */
  public boolean isUseFieldNames() {
    return useFieldNames;
  }

  /**
   * Sets whether to use the field names passed in.
   *
   * @param useFieldNames
   *          the new useFieldNames flag
   */
  protected void setUseFieldNames(final boolean useFieldNames) {
    this.useFieldNames = useFieldNames;
  }

  /**
   * Gets whether to use full detail when the caller doesn't specify.
   *
   * @return the current defaultFullDetail flag
   */
  public boolean isDefaultFullDetail() {
    return defaultFullDetail;
  }

  /**
   * Sets whether to use full detail when the caller doesn't specify.
   *
   * @param defaultFullDetail
   *          the new defaultFullDetail flag
   */
  protected void setDefaultFullDetail(final boolean defaultFullDetail) {
    this.defaultFullDetail = defaultFullDetail;
  }

  /**
   * Gets whether to output array content detail.
   *
   * @return the current array content detail setting
   */
  public boolean isArrayContentDetail() {
    return arrayContentDetail;
  }

  /**
   * Sets whether to output array content detail.
   *
   * @param arrayContentDetail
   *          the new arrayContentDetail flag
   */
  protected void setArrayContentDetail(final boolean arrayContentDetail) {
    this.arrayContentDetail = arrayContentDetail;
  }

  /**
   * Gets the array start text.
   *
   * @return the current array start text
   */
  public String getArrayStart() {
    return arrayStart;
  }

  /**
   * Sets the array start text. {@code null} is accepted, but will be
   * converted to an empty String.
   *
   * @param arrayStart
   *          the new array start text
   */
  protected void setArrayStart(String arrayStart) {
    if (arrayStart == null) {
      arrayStart = StringUtils.EMPTY;
    }
    this.arrayStart = arrayStart;
  }

  /**
   * Gets the array end text.
   *
   * @return the current array end text
   */
  public String getArrayEnd() {
    return arrayEnd;
  }

  /**
   * Sets the array end text. {@code null} is accepted, but will be
   * converted to an empty String.
   *
   * @param arrayEnd
   *          the new array end text
   */
  protected void setArrayEnd(String arrayEnd) {
    if (arrayEnd == null) {
      arrayEnd = StringUtils.EMPTY;
    }
    this.arrayEnd = arrayEnd;
  }

  /**
   * Gets the array separator text.
   *
   * @return the current array separator text
   */
  public String getArraySeparator() {
    return arraySeparator;
  }

  /**
   * Sets the array separator text. {@code null} is accepted, but will be
   * converted to an empty String.
   *
   * @param arraySeparator
   *          the new array separator text
   */
  protected void setArraySeparator(String arraySeparator) {
    if (arraySeparator == null) {
      arraySeparator = StringUtils.EMPTY;
    }
    this.arraySeparator = arraySeparator;
  }

  /**
   * Gets the content start text.
   *
   * @return the current content start text
   */
  public String getContentStart() {
    return contentStart;
  }

  /**
   * Sets the content start text. {@code null} is accepted, but will be
   * converted to an empty String.
   *
   * @param contentStart
   *          the new content start text
   */
  protected void setContentStart(String contentStart) {
    if (contentStart == null) {
      contentStart = StringUtils.EMPTY;
    }
    this.contentStart = contentStart;
  }

  /**
   * Gets the content end text.
   *
   * @return the current content end text
   */
  public String getContentEnd() {
    return contentEnd;
  }

  /**
   * Sets the content end text. {@code null} is accepted, but will be
   * converted to an empty String.
   *
   * @param contentEnd
   *          the new content end text
   */
  protected void setContentEnd(String contentEnd) {
    if (contentEnd == null) {
      contentEnd = StringUtils.EMPTY;
    }
    this.contentEnd = contentEnd;
  }

  /**
   * Gets the field name value separator text.
   *
   * @return the current field name value separator text
   */
  public String getFieldNameValueSeparator() {
    return fieldNameValueSeparator;
  }

  /**
   * Sets the field name value separator text. {@code null} is accepted,
   * but will be converted to an empty String.
   *
   * @param fieldNameValueSeparator
   *          the new field name value separator text
   */
  protected void setFieldNameValueSeparator(String fieldNameValueSeparator) {
    if (fieldNameValueSeparator == null) {
      fieldNameValueSeparator = StringUtils.EMPTY;
    }
    this.fieldNameValueSeparator = fieldNameValueSeparator;
  }

  /**
   * Gets the field separator text.
   *
   * @return the current field separator text
   */
  public String getFieldSeparator() {
    return fieldSeparator;
  }

  /**
   * Sets the field separator text. {@code null} is accepted, but will be
   * converted to an empty String.
   *
   * @param fieldSeparator
   *          the new field separator text
   */
  protected void setFieldSeparator(String fieldSeparator) {
    if (fieldSeparator == null) {
      fieldSeparator = StringUtils.EMPTY;
    }
    this.fieldSeparator = fieldSeparator;
  }

  /**
   * Gets whether the field separator should be added at the start of each
   * buffer.
   *
   * @return the fieldSeparatorAtStart flag
   */
  public boolean isFieldSeparatorAtStart() {
    return fieldSeparatorAtStart;
  }

  /**
   * Sets whether the field separator should be added at the start of each
   * buffer.
   *
   * @param fieldSeparatorAtStart
   *          the fieldSeparatorAtStart flag
   */
  protected void setFieldSeparatorAtStart(final boolean fieldSeparatorAtStart) {
    this.fieldSeparatorAtStart = fieldSeparatorAtStart;
  }

  /**
   * Gets whether the field separator should be added at the end of each buffer.
   *
   * @return fieldSeparatorAtEnd flag
   */
  public boolean isFieldSeparatorAtEnd() {
    return fieldSeparatorAtEnd;
  }

  /**
   * Sets whether the field separator should be added at the end of each buffer.
   *
   * @param fieldSeparatorAtEnd
   *          the fieldSeparatorAtEnd flag
   */
  protected void setFieldSeparatorAtEnd(final boolean fieldSeparatorAtEnd) {
    this.fieldSeparatorAtEnd = fieldSeparatorAtEnd;
  }

  /**
   * Gets the text to output when {@code null} found.
   *
   * @return the current text to output when null found
   */
  public String getNullText() {
    return nullText;
  }

  /**
   * Sets the text to output when {@code null} found. {@code null} is
   * accepted, but will be converted to an empty String.
   *
   * @param nullText
   *          the new text to output when null found
   */
  protected void setNullText(String nullText) {
    if (nullText == null) {
      nullText = StringUtils.EMPTY;
    }
    this.nullText = nullText;
  }

  /**
   * Gets the start text to output when a {@code Collection},
   * {@code Map} or array size is output. This is output before the size
   * value.
   *
   * @return the current start of size text
   */
  public String getSizeStartText() {
    return sizeStartText;
  }

  /**
   * Sets the start text to output when a {@code Collection},
   * {@code Map} or array size is output. This is output before the size
   * value. {@code null} is accepted, but will be converted to an empty
   * String.
   *
   * @param sizeStartText
   *          the new start of size text
   */
  protected void setSizeStartText(String sizeStartText) {
    if (sizeStartText == null) {
      sizeStartText = StringUtils.EMPTY;
    }
    this.sizeStartText = sizeStartText;
  }

  /**
   * Gets the end text to output when a {@code Collection},
   * {@code Map} or array size is output. This is output after the size
   * value.
   *
   * @return the current end of size text
   */
  public String getSizeEndText() {
    return sizeEndText;
  }

  /**
   * Sets the end text to output when a {@code Collection},
   * {@code Map} or array size is output. This is output after the size
   * value. {@code null} is accepted, but will be converted to an empty
   * String.
   *
   * @param sizeEndText
   *          the new end of size text
   */
  protected void setSizeEndText(String sizeEndText) {
    if (sizeEndText == null) {
      sizeEndText = StringUtils.EMPTY;
    }
    this.sizeEndText = sizeEndText;
  }

  /**
   * Gets the start text to output when an {@code Object} is output in
   * summary mode. This is output before the size value.
   *
   * @return the current start of summary text
   */
  public String getSummaryObjectStartText() {
    return summaryObjectStartText;
  }

  /**
   * Sets the start text to output when an {@code Object} is output in
   * summary mode. This is output before the size value. {@code null} is
   * accepted, but will be converted to an empty String.
   *
   * @param summaryObjectStartText
   *          the new start of summary text
   */
  protected void setSummaryObjectStartText(String summaryObjectStartText) {
    if (summaryObjectStartText == null) {
      summaryObjectStartText = StringUtils.EMPTY;
    }
    this.summaryObjectStartText = summaryObjectStartText;
  }

  /**
   * Gets the end text to output when an {@code Object} is output in
   * summary mode. This is output after the size value.
   *
   * @return the current end of summary text
   */
  public String getSummaryObjectEndText() {
    return summaryObjectEndText;
  }

  /**
   * Sets the end text to output when an {@code Object} is output in
   * summary mode. This is output after the size value. {@code null} is
   * accepted, but will be converted to an empty String.
   *
   * @param summaryObjectEndText
   *          the new end of summary text
   */
  protected void setSummaryObjectEndText(String summaryObjectEndText) {
    if (summaryObjectEndText == null) {
      summaryObjectEndText = StringUtils.EMPTY;
    }
    this.summaryObjectEndText = summaryObjectEndText;
  }

  @Override
  public int hashCode() {
    final int multiplier = 11;
    int code = 31;
    code = Hash.combine(code, multiplier, useFieldNames);
    code = Hash.combine(code, multiplier, useClassName);
    code = Hash.combine(code, multiplier, useShortClassName);
    code = Hash.combine(code, multiplier, useIdentityHashCode);
    code = Hash.combine(code, multiplier, contentStart);
    code = Hash.combine(code, multiplier, contentEnd);
    code = Hash.combine(code, multiplier, fieldNameValueSeparator);
    code = Hash.combine(code, multiplier, fieldSeparatorAtStart);
    code = Hash.combine(code, multiplier, fieldSeparatorAtEnd);
    code = Hash.combine(code, multiplier, fieldSeparator);
    code = Hash.combine(code, multiplier, arrayStart);
    code = Hash.combine(code, multiplier, arraySeparator);
    code = Hash.combine(code, multiplier, arrayContentDetail);
    code = Hash.combine(code, multiplier, arrayEnd);
    code = Hash.combine(code, multiplier, defaultFullDetail);
    code = Hash.combine(code, multiplier, nullText);
    code = Hash.combine(code, multiplier, sizeStartText);
    code = Hash.combine(code, multiplier, sizeEndText);
    code = Hash.combine(code, multiplier, summaryObjectStartText);
    code = Hash.combine(code, multiplier, summaryObjectEndText);
    return code;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ToStringStyle other = (ToStringStyle) obj;
    return (useFieldNames == other.useFieldNames)
        && (useClassName == other.useClassName)
        && (useShortClassName == other.useShortClassName)
        && (useIdentityHashCode == other.useIdentityHashCode)
        && Equality.equals(contentStart, other.contentStart)
        && Equality.equals(contentEnd, other.contentEnd)
        && Equality.equals(fieldNameValueSeparator, other.fieldNameValueSeparator)
        && (fieldSeparatorAtStart == other.fieldSeparatorAtStart)
        && (fieldSeparatorAtEnd == other.fieldSeparatorAtEnd)
        && Equality.equals(fieldSeparator, other.fieldSeparator)
        && Equality.equals(arrayStart, other.arrayStart)
        && Equality.equals(arrayEnd, other.arrayEnd)
        && Equality.equals(arraySeparator, other.arraySeparator)
        && (arrayContentDetail == other.arrayContentDetail)
        && (defaultFullDetail == other.defaultFullDetail)
        && Equality.equals(nullText, other.nullText)
        && Equality.equals(sizeStartText, other.sizeStartText)
        && Equality.equals(sizeEndText, other.sizeEndText)
        && Equality.equals(summaryObjectStartText, other.summaryObjectStartText)
        && Equality.equals(summaryObjectEndText, other.summaryObjectEndText);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("useFieldNames", useFieldNames)
               .append("useClassName", useClassName)
               .append("useShortClassName", useShortClassName)
               .append("useIdentityHashCode", useIdentityHashCode)
               .append("contentStart", contentStart)
               .append("contentEnd", contentEnd)
               .append("fieldNameValueSeparator", fieldNameValueSeparator)
               .append("fieldSeparatorAtStart", fieldSeparatorAtStart)
               .append("fieldSeparatorAtEnd", fieldSeparatorAtEnd)
               .append("fieldSeparator", fieldSeparator)
               .append("arrayStart", arrayStart)
               .append("arrayEnd", arrayEnd)
               .append("arraySeparator", arraySeparator)
               .append("arrayContentDetail", arrayContentDetail)
               .append("defaultFullDetail", defaultFullDetail)
               .append("nullText", nullText)
               .append("sizeStartText", sizeStartText)
               .append("sizeEndText", sizeEndText)
               .append("summaryObjectStartText", summaryObjectStartText)
               .append("summaryObjectEndText", summaryObjectEndText)
               .toString();
  }
}
