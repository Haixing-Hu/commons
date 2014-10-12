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
package com.github.haixing_hu.text;

import java.io.Serializable;
import java.util.regex.Matcher;

import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.NotThreadSafe;

import com.github.haixing_hu.io.serialize.BinarySerialization;
import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.lang.Cloneable;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * A {@link Pattern} object represents a generic pattern of strings.
 * <p>
 * NOTE: This class is NOT thread safe. If more than one threads use the
 * {@link #matches(String)} method, it must be synchronized.
 * </p>
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class Pattern implements Cloneable<Pattern>, Serializable {

  private static final long serialVersionUID = - 6759804850795758979L;

  public static final PatternType DEFAULT_TYPE               = PatternType.LITERAL;
  public static final boolean     DEFAULT_IGNORE_CASE        = false;

  static {
    BinarySerialization.register(Pattern.class, PatternBinarySerializer.INSTANCE);
    XmlSerialization.register(Pattern.class, PatternXmlSerializer.INSTANCE);
  }

  private PatternType  type;
  private boolean      ignoreCase;
  private String       expression;
  private transient    Matcher matcher;

  public Pattern() {
    type = DEFAULT_TYPE;
    ignoreCase = DEFAULT_IGNORE_CASE;
    expression = StringUtils.EMPTY;
    matcher = null;
  }

  public Pattern(final String expression) {
    this.type = DEFAULT_TYPE;
    this.ignoreCase = DEFAULT_IGNORE_CASE;
    this.expression = requireNonNull("expression", expression);
    this.matcher = null;
  }

  public Pattern(final PatternType type, final String expression) {
    this.type = requireNonNull("type", type);
    this.ignoreCase = DEFAULT_IGNORE_CASE;
    this.expression = requireNonNull("expression", expression);
    this.matcher = null;
  }

  public Pattern(final PatternType type, final boolean caseInsensitive,
      final String expression) {
    this.type = requireNonNull("type", type);
    this.ignoreCase = caseInsensitive;
    this.expression = requireNonNull("expression", expression);
    this.matcher = null;
  }

  public PatternType getType() {
    return type;
  }

  public void setType(final PatternType type) {
    this.type = requireNonNull("type", type);
    this.matcher = null;
  }

  public boolean isIgnoreCase() {
    return ignoreCase;
  }

  public void setIgnoreCase(final boolean ignoreCase) {
    this.ignoreCase = ignoreCase;
    this.matcher = null;
  }

  public String getExpression() {
    return expression;
  }

  public void setExpression(final String expression) {
    this.expression = requireNonNull("expression", expression);
    this.matcher = null;
  }

  public boolean matches(@Nullable final String str) {
    if (str == null) {
      return false;
    }
    switch (type) {
      case LITERAL:
        if (ignoreCase) {
          return expression.equalsIgnoreCase(str);
        } else {
          return expression.equals(str);
        }
      case PREFIX:
        if (ignoreCase) {
          return StringUtils.startsWithIgnoreCase(str, expression);
        } else {
          return StringUtils.startsWith(str, expression);
        }
      case SUFFIX:
        if (ignoreCase) {
          return StringUtils.endsWithIgnoreCase(str, expression);
        } else {
          return StringUtils.endsWith(str, expression);
        }
      case REGEX:
        if (matcher == null) {
          setRegexMatcher();
        }
        return matcher.reset(str).matches();
      case GLOB:
        if (matcher == null) {
          setGlobMatcher();
        }
        return matcher.reset(str).matches();
      default:
        return false;
    }
  }

  @GuardedBy("this")
  private void setRegexMatcher() {
    int flags = 0;
    if (ignoreCase) {
      flags = java.util.regex.Pattern.CASE_INSENSITIVE;
    }
    matcher = java.util.regex.Pattern.compile(expression, flags)
                                     .matcher(StringUtils.EMPTY);
  }

  @GuardedBy("this")
  private void setGlobMatcher() {
    int flags = 0;
    if (ignoreCase) {
      flags = java.util.regex.Pattern.CASE_INSENSITIVE;
    }
    final String regex = Glob.toRegex(expression);
    matcher = java.util.regex.Pattern.compile(regex, flags)
                                     .matcher(StringUtils.EMPTY);
  }

  @Override
  public Pattern clone() {
    return new Pattern(type, ignoreCase, expression);
  }

  @Override
  public int hashCode() {
    final int multiplier = 71;
    int code = 771;
    code = Hash.combine(code, multiplier, type);
    code = Hash.combine(code, multiplier, ignoreCase);
    code = Hash.combine(code, multiplier, expression);
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
    final Pattern other = (Pattern) obj;
    return (type == other.type)
        && (ignoreCase == other.ignoreCase)
        && expression.equals(other.expression);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("type", type)
               .append("ignoreCase", ignoreCase)
               .append("expression", expression)
               .toString();
  }

}
