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

import java.io.Serializable;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.concurrent.NotThreadSafe;

import com.github.haixing_hu.io.serialize.BinarySerialization;
import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.lang.Assignable;
import com.github.haixing_hu.lang.Cloneable;
import com.github.haixing_hu.lang.Equality;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.lang.TypeMismatchException;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

import static com.github.haixing_hu.lang.Argument.requireNonNull;

/**
 * The class of the filename glob patterns.
 *
 * The glob pattern syntax obey the rule at
 * http://www.jedit.org/users-guide/globs.html.
 *
 * The following character sequences have special meaning within a glob pattern:
 *
 * <ul>
 * <li>? matches any one character</li>
 * <li>* matches any number of characters</li>
 * <li>{!glob} Matches anything that does not match glob</li>
 * <li>{a,b,c} matches any one of a, b or c</li>
 * <li>[abc] matches any character in the set a, b or c</li>
 * <li>[^abc] matches any character not in the set a, b or c</li>
 * <li>[a-z] matches any character in the range a to z, inclusive. A leading or
 * trailing dash will be interpreted literally</li>
 * </ul>
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class Glob implements Serializable, Cloneable<Glob>, Assignable<Glob> {

  private static final long   serialVersionUID = -1303433525002426637L;

  public static int DEFAULT_FLAGS              = Pattern.CASE_INSENSITIVE;
  public static final String  ROOT_NODE        = "glob";
  public static final String  FLAGS_ATTRIBUTE  = "flags";

  private static final Object NEGATIVE         = new Object();
  private static final Object GROUP            = new Object();

  static {
    BinarySerialization.register(Glob.class, GlobBinarySerializer.INSTANCE);
    XmlSerialization.register(Glob.class, GlobXmlSerializer.INSTANCE);
  }

  /**
   * Converts a Unix-style filename glob to a regular expression.
   *
   * <p>
   * Since we use java.util.regex patterns to implement globs, this means that
   * in addition to the above, a number of “character class metacharacters” may
   * be used. Keep in mind, their usefulness is limited since the regex
   * quantifier metacharacters (asterisk, questionmark, and curly brackets) are
   * redefined to mean something else in filename glob language, and the regex
   * quantifiers are not available in glob language.
   *
   * <list>
   * <li>\w matches any alphanumeric character or underscore</li>
   * <li>\s matches a space or horizontal tab</li>
   * <li>\S matches a printable non-whitespace.</li>
   * <li>\d matches a decimal digit</li> </list>
   *
   * <p>
   * Here are some examples of glob patterns:
   *
   * <list>
   * <li>"*" - all files.</li>
   * <li>"*.java" - all files whose names end with ".java".</li>
   * <li>"*.[ch]" - all files whose names end with either ".c" or ".h".</li>
   * <li>"*.{c,cpp,h,hpp,cxx,hxx}" - all C or C++ files.</li>
   * <li>"[^#]*" - all files whose names do not start with "#".</li> </list>
   *
   * <p>
   * This function makes the following conversion:
   *
   * <list>
   * <li>"?" becomes "."</li>
   * <li>"*" becomes ".*"</li>
   * <li>"{aa,bb}" becomes "(aa|bb)"</li>
   * <li>all other special meta-characters in regular expressions are escaped.</li>
   * </list>
   *
   * @param glob
   *          the Unix-style filename glob pattern
   */
  public static String toRegex(final String glob) {
    final Stack<Object> state = new Stack<Object>();
    boolean backSlash = false;
    final int globLen = glob.length();
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < globLen; ++i) {
      final char ch = glob.charAt(i);
      if (backSlash) {
        builder.append('\\').append(ch);
        backSlash = false;
        continue;
      }
      switch (ch) {
        case '\\':
          backSlash = true;
          break;
        case '?':
          builder.append('.');
          break;
        case '.':
        case '+':
        case '(':
        case ')':
          builder.append('\\').append(ch);
          break;
        case '*':
          builder.append('.').append('*');
          break;
        case '|':
          if (backSlash) {
            builder.append('\\').append('|');
          } else {
            builder.append('|');
          }
          break;
        case '{': {
          builder.append('(');
          final int next = i + 1;
          if ((next != glob.length()) && (glob.charAt(next) == '!')) {
            builder.append('?');
            state.push(NEGATIVE);
          } else {
            state.push(GROUP);
          }
          break;
        }
        case ',':
          if (! state.isEmpty() && (state.peek() == GROUP)) {
            builder.append('|');
          } else {
            builder.append(',');
          }
          break;
        case '}':
          if (! state.isEmpty()) {
            builder.append(")");
            if (state.pop() == NEGATIVE) {
              builder.append('.').append('*');
            }
          } else {
            builder.append('}');
          }
          break;
        default:
          builder.append(ch);
      }
    }
    return builder.toString();
  }


  protected String            pattern;
  protected int               flags;

  protected transient Matcher matcher;

  public Glob() {
    this.pattern = StringUtils.EMPTY;
    this.flags = DEFAULT_FLAGS;
    this.matcher = null;
  }

  /**
   * Construct a case-insensitive glob pattern object.
   *
   * @param pattern
   *          the string of glob pattern.
   */
  public Glob(final String pattern) {
    this.pattern = requireNonNull("pattern", pattern);
    this.flags = DEFAULT_FLAGS;
    this.matcher = null;
  }

  /**
   * Construct a glob pattern object.
   *
   * @param pattern
   *          the string of glob pattern.
   * @param flags
   *          the match flags, a bit mask that may include
   *          Pattern.CASE_INSENSITIVE, Pattern.MULTILINE, Pattern.DOTALL,
   *          Pattern.UNICODE_CASE, Pattern.CANON_EQ, Pattern.UNIX_LINES,
   *          Pattern.LITERAL and Pattern.COMMENTS. Where Pattern is the
   *          java.util.regex.Pattern class.
   */
  public Glob(final String pattern, final int flags) {
    this.pattern = requireNonNull("pattern", pattern);
    this.flags = flags;
    this.matcher = null;
  }

  public int getFlags() {
    return flags;
  }

  public void setFlags(final int flags) {
    this.flags = flags;
    this.matcher = null;
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(final String pattern) {
    this.pattern = requireNonNull("pattern", pattern);
    this.matcher = null;
  }

  public void set(final String pattern, final int flags) {
    this.pattern = requireNonNull("pattern", pattern);
    this.flags = flags;
    this.matcher = null;
  }

  public boolean matches(final String str) {
    if (str == null) {
      return false;
    } else if (pattern.length() == 0) {
      return false;
    }
    if (matcher == null) {
      final String regex = toRegex(pattern);
      matcher = Pattern.compile(regex, flags)
                       .matcher(StringUtils.EMPTY);
    }
    return matcher.reset(str).matches();
  }

  @Override
  public void assign(final Glob that) {
    if (this != that) {
      final Class<?> thisClass = this.getClass();
      final Class<?> thatClass = that.getClass();
      if (thisClass != thatClass) {
        throw new TypeMismatchException(thisClass, thatClass);
      }
      this.flags = that.flags;
      this.pattern = that.pattern;
      if (that.matcher == null) {
        this.matcher = null;
      } else {
        this.matcher = that.matcher.pattern().matcher(StringUtils.EMPTY);
      }
    }
  }

  @Override
  public Glob clone() {
    final Glob result = new Glob();
    result.flags = this.flags;
    result.pattern = this.pattern;
    result.matcher = null;
    return result;
  }

  @Override
  public int hashCode() {
    int code = 77;
    final int multiplier = 131;
    code = Hash.combine(code, multiplier, flags);
    code = Hash.combine(code, multiplier, pattern);
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
    final Glob other = (Glob) obj;
    return (flags == other.flags)
        && Equality.equals(pattern, other.pattern);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("flags", flags)
               .append("pattern", pattern)
               .toString();
  }
}
