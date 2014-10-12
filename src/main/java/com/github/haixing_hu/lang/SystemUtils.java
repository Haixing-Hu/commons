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

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.SecureRandom;
import java.util.Random;

import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.haixing_hu.CommonsMessages.CREATE_INSTANCE_FAILED;

/**
 * This class provides helper functions for {@code java.lang.System}.
 *
 * If a system property cannot be read due to security restrictions, the
 * corresponding field in this class will be set to {@code null} and a
 * message will be written to the log.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class SystemUtils {

  /**
   * The prefix String for all Windows OS.
   */
  private static final String OS_NAME_WINDOWS_PREFIX = "Windows";

  /**
   * The System property key for the Java home directory.
   */
  public static final String JAVA_HOME_KEY = "java.home";

  /**
   * The System property key for the Java IO temporary directory.
   */
  public static final String JAVA_IO_TMPDIR_KEY = "java.io.tmpdir";

  /**
   * The System property key for the Java class path.
   */
  public static final String JAVA_CLASS_PATH_KEY = "java.class.path";

  /**
   * The System property key for the Java class format version number.
   */
  public static final String JAVA_CLASS_VERSION_KEY = "java.class.version";

  /**
   * The System property key for Java compiler name.
   */
  public static final String JAVA_COMPILER_KEY = "java.compiler";

  /**
   * The System property key for the Java endorsed directory or directories..
   */
  public static final String JAVA_ENDORSED_DIRS_KEY = "java.endorsed.dirs";

  /**
   * The System property key for the Java extension directory or directories.
   */
  public static final String JAVA_EXT_DIRS_KEY = "java.ext.dirs";

  /**
   * The System property key for the list of paths to search when loading
   * libraries.
   */
  public static final String JAVA_LIBRARY_PATH_KEY = "java.library.path";

  /**
   * The System property key for the Java runtime environment name.
   */
  public static final String JAVA_RUNTIME_NAME_KEY = "java.runtime.name";

  /**
   * The System property key for the Java runtime environment version.
   */
  public static final String JAVA_RUNTIME_VERSION_KEY = "java.runtime.version";

  /**
   * The System property key for the Java runtime environment specification
   * name.
   */
  public static final String JAVA_SPECIFICATION_NAME_KEY = "java.specification.name";

  /**
   * The System property key for the Java runtime environment specification
   * version.
   */
  public static final String JAVA_SPECIFICATION_VERSION_KEY = "java.specification.version";

  /**
   * The System property key for the Java runtime environment specification
   * vendor.
   */
  public static final String JAVA_SPECIFICATION_VENDOR_KEY = "java.specification.vendor";

  /**
   * The System property key for the Java PreferencesFactory class name.
   */
  public static final String JAVA_PREFERENCES_FACTORY_KEY = "java.util.prefs.PreferencesFactory";

  /**
   * The System property key for the Java vendor-specific string.
   */
  public static final String JAVA_VENDOR_KEY = "java.vendor";

  /**
   * The System property key for the Java vendor URL.
   */
  public static final String JAVA_VENDOR_URL_KEY = "java.vendor.url";

  /**
   * The System property key for the Java version number.
   */
  public static final String JAVA_VERSION_KEY = "java.version";

  /**
   * The System property key for the Java Virtual Machine implementation info.
   */
  public static final String JAVA_VM_INFO_KEY = "java.vm.info";

  /**
   * The System property key for the Java Virtual Machine implementation name.
   */
  public static final String JAVA_VM_NAME_KEY = "java.vm.name";

  /**
   * The System property key for the Java Virtual Machine implementation
   * version.
   */
  public static final String JAVA_VM_VERSION_KEY = "java.vm.version";

  /**
   * The System property key for the Java Virtual Machine implementation vendor.
   */
  public static final String JAVA_VM_VENDOR_KEY = "java.vm.vendor";

  /**
   * The System property key for the Java Virtual Machine specification name.
   */
  public static final String JAVA_VM_SPECIFICATION_NAME_KEY = "java.vm.specification.name";

  /**
   * The System property key for the Java Virtual Machine specification vendor.
   */
  public static final String JAVA_VM_SPECIFICATION_VENDOR_KEY = "java.vm.specification.vendor";

  /**
   * The System property key for the Java Virtual Machine specification version.
   */
  public static final String JAVA_VM_SPECIFICATION_VERSION_KEY = "java.vm.specification.version";

  /**
   * The System property key for the operating system architecture.
   */
  public static final String OS_ARCH_KEY = "os.arch";

  /**
   * The System property key for the operating system name.
   */
  public static final String OS_NAME_KEY = "os.name";

  /**
   * The System property key for the operating system version.
   */
  public static final String OS_VERSION_KEY = "os.version";

  /**
   * The System property key for the operating system line separator.
   */
  public static final String LINE_SEPARATOR_KEY = "line.separator";

  /**
   * The System property key for the operating system path separator.
   */
  public static final String PATH_SEPARATOR_KEY = "path.separator";

  /**
   * The System property key for the user's country code.
   */
  public static final String USER_COUNTRY_KEY = "user.country";

  /**
   * The System property key for the user's current working directory.
   */
  public static final String USER_DIR_KEY = "user.dir";

  /**
   * The System property key for the user's home directory.
   */
  public static final String USER_HOME_KEY = "user.home";

  /**
   * The System property key for the user's language code, such as
   * {@code "en"}.
   */
  public static final String USER_LANGUAGE_KEY = "user.language";

  /**
   * The System property key for the user's account name.
   */
  public static final String USER_NAME_KEY = "user.name";

  /**
   * The System property key for the user's time zone. For example:
   * {@code "America/Los_Angeles"}.
   */
  public static final String USER_TIMEZONE_KEY = "user.timezone";

  /**
   * The System property key for the AWT toolkit class name.
   */
  public static final String AWT_TOOLKIT_KEY = "awt.toolkit";

  /**
   * The System property key for the file encoding name.
   */
  public static final String FILE_ENCODING_KEY = "file.encoding";

  /**
   * The System property key for the file separator.
   */
  public static final String FILE_SEPARATOR_KEY = "file.separator";

  /**
   * The System property key for the AWT fonts.
   */
  public static final String JAVA_AWT_FONTS_KEY = "java.awt.fonts";

  /**
   * The System property key for the AWT graphic environment.
   */
  public static final String JAVA_AWT_GRAPHICSENV_KEY = "java.awt.graphicsenv";

  /**
   * The System property key for the AWT headless property. The value of this
   * property is the String {@code "true"} or {@code "false"}.
   *
   * @see #isJavaAwtHeadless()
   */
  public static final String JAVA_AWT_HEADLESS_KEY = "java.awt.headless";

  /**
   * The System property key for the AWT printer job property.
   */
  public static final String JAVA_AWT_PRINTERJOB_KEY = "java.awt.printerjob";

  /**
   * The System property key for the Sun's architecture data model.
   */
  public static final String SUN_ARCH_DATA_MODEL_KEY = "sun.arch.data.model";

  /**
   * The home directory of Java.
   */
  public static final String JAVA_HOME = getProperty(JAVA_HOME_KEY);

  /**
   * The pathname of the Java IO temporary directory.
   */
  public static final String JAVA_IO_TMPDIR = getProperty(JAVA_IO_TMPDIR_KEY);

  /**
   * The version string of the Java.
   */
  public static final String JAVA_VERSION = getProperty(JAVA_VERSION_KEY);

  /**
   * Gets the Java version as a {@code String} trimming leading letters.
   *
   * The field will return {@code null} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final String JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();

  /**
   * Gets the Java version as a {@code float}.
   *
   * Example return values:
   * <ul>
   * <li>{@code 1.2f} for JDK 1.2
   * <li>{@code 1.31f} for JDK 1.3.1
   * </ul>
   *
   * The field will return zero if {@link #JAVA_VERSION} is {@code null}.
   */
  public static final float JAVA_VERSION_FLOAT = getJavaVersionAsFloat();

  /**
   * Gets the Java version as an {@code int}.
   *
   * Example return values:
   * <ul>
   * <li>{@code 120} for JDK 1.2
   * <li>{@code 131} for JDK 1.3.1
   * </ul>
   *
   * The field will return zero if {@link #JAVA_VERSION} is {@code null}.
   */
  public static final int JAVA_VERSION_INT = getJavaVersionAsInt();

  /**
   * The operating system architecture.
   */
  public static final String OS_ARCH = getProperty(OS_ARCH_KEY);

  /**
   * The operating system name.
   */
  public static final String OS_NAME = getProperty(OS_NAME_KEY);

  /**
   * The operating system version.
   */
  public static final String OS_VERSION = getProperty(OS_VERSION_KEY);

  /**
   * The operating system line separator. If it is failed to get the
   * {@link LINE_SEPARATOR_KEY} property value from the system properties, the
   * class will call the {@link PrintWriter#println()} function to get a line
   * separator.
   */
  public static final String LINE_SEPARATOR;
  static {
    String lineSpearator = getProperty(LINE_SEPARATOR_KEY);
    if (lineSpearator == null) {
      final StringWriter buffer = new StringWriter(4);
      final PrintWriter out = new PrintWriter(buffer);
      out.println();
      lineSpearator = buffer.toString();
    }
    LINE_SEPARATOR = lineSpearator;
  }

  /**
   * The operating system path separator.
   */
  public static final String PATH_SEPARATOR = getProperty(PATH_SEPARATOR_KEY);

  /**
   * The user's home directory pathname.
   */
  public static final String USER_HOME = getProperty(USER_HOME_KEY);

  /**
   * The user's current working directory pathname.
   */
  public static final String USER_DIR = getProperty(USER_DIR_KEY);

  /**
   * The user's language code, such as {@code "en"}.
   */
  public static final String USER_LANGUAGE = getProperty(USER_LANGUAGE_KEY);

  /**
   * The user's account name.
   */
  public static final String USER_NAME = getProperty(USER_NAME_KEY);

  /**
   * The user's time zone. For example: {@code "America/Los_Angeles"}.
   */
  public static final String USER_TIMEZONE = getProperty(USER_TIMEZONE_KEY);

  /**
   * Is {@code true} if this is Java version 1.1 (also 1.1.x versions).
   *
   * The field will return {@code false} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final boolean IS_JAVA_1_1 = getJavaVersionMatches("1.1");

  /**
   * Is {@code true} if this is Java version 1.2 (also 1.2.x versions).
   *
   * The field will return {@code false} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final boolean IS_JAVA_1_2 = getJavaVersionMatches("1.2");

  /**
   * Is {@code true} if this is Java version 1.3 (also 1.3.x versions).
   *
   * The field will return {@code false} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final boolean IS_JAVA_1_3 = getJavaVersionMatches("1.3");

  /**
   * Is {@code true} if this is Java version 1.4 (also 1.4.x versions).
   *
   * The field will return {@code false} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final boolean IS_JAVA_1_4 = getJavaVersionMatches("1.4");

  /**
   * Is {@code true} if this is Java version 1.5 (also 1.5.x versions).
   *
   * The field will return {@code false} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final boolean IS_JAVA_1_5 = getJavaVersionMatches("1.5");

  /**
   * Is {@code true} if this is Java version 1.6 (also 1.6.x versions).
   *
   * The field will return {@code false} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final boolean IS_JAVA_1_6 = getJavaVersionMatches("1.6");

  /**
   * Is {@code true} if this is Java version 1.7 (also 1.7.x versions).
   *
   * The field will return {@code false} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final boolean IS_JAVA_1_7 = getJavaVersionMatches("1.7");

  /**
   * Is {@code true} if the current JRE is 64-bit.
   *
   * FIXME: this logic may not be correct.
   */
  public static final boolean IS_JAVA_64BIT;
  static {
    final String dataModel = getProperty(SUN_ARCH_DATA_MODEL_KEY);
    if (dataModel != null) {
      IS_JAVA_64BIT = dataModel.indexOf("64") != - 1;
    } else {
      if ((OS_ARCH != null) && (OS_ARCH.indexOf("64") != - 1)) {
        IS_JAVA_64BIT = true;
      } else {
        IS_JAVA_64BIT = false;
      }
    }
  }

  /**
   * Is {@code true} if the current JRE is 32-bit.
   */
  public static final boolean IS_JAVA_32BIT = (! IS_JAVA_64BIT);

  /**
   * Is {@code true} if this is AIX.
   *
   * The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   */
  public static final boolean IS_OS_AIX = getOSMatches("AIX");

  /**
   * Is {@code true} if this is HP-UX.
   *
   * The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   */
  public static final boolean IS_OS_HP_UX = getOSMatches("HP-UX");

  /**
   * Is {@code true} if this is Irix.
   *
   * The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   */
  public static final boolean IS_OS_IRIX = getOSMatches("Irix");

  /**
   * Is {@code true} if this is Linux.
   *
   * The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   */
  public static final boolean IS_OS_LINUX = getOSMatches("Linux")
      || getOSMatches("LINUX");

  /**
   * Is {@code true} if this is Mac.
   *
   * The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   *
   * @since 2.0
   */
  public static final boolean IS_OS_MAC = getOSMatches("Mac");

  /**
   * Is {@code true} if this is Mac.
   *
   * The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   *
   * @since 2.0
   */
  public static final boolean IS_OS_MAC_OSX = getOSMatches("Mac OS X");

  /**
   * Is {@code true} if this is OS/2.
   *
   * The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   *
   * @since 2.0
   */
  public static final boolean IS_OS_OS2 = getOSMatches("OS/2");

  /**
   * Is {@code true} if this is Solaris.
   *
   * The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   *
   * @since 2.0
   */
  public static final boolean IS_OS_SOLARIS = getOSMatches("Solaris");

  /**
   * Is {@code true} if this is SunOS.
   *
   * The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   *
   * @since 2.0
   */
  public static final boolean IS_OS_SUN_OS = getOSMatches("SunOS");

  /**
   * Is {@code true} if this is a POSIX compilant system, as in any of AIX,
   * HP-UX, Irix, Linux, MacOSX, Solaris or SUN OS.
   *
   * The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   *
   * @since 2.1
   */
  public static final boolean IS_OS_UNIX = IS_OS_AIX || IS_OS_HP_UX
      || IS_OS_IRIX || IS_OS_LINUX || IS_OS_MAC_OSX || IS_OS_SOLARIS
      || IS_OS_SUN_OS;

  /**
   * Is {@code true} if this is Windows.
   *
   * The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   *
   * @since 2.0
   */
  public static final boolean IS_OS_WINDOWS = getOSMatches(OS_NAME_WINDOWS_PREFIX);

  /**
   * Is {@code true} if this is Windows 2000.
   *
   * The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   *
   * @since 2.0
   */
  public static final boolean IS_OS_WINDOWS_2000 = getOSMatches(
      OS_NAME_WINDOWS_PREFIX, "5.0");

  /**
   * Is {@code true} if this is Windows 95.
   *
   * The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   *
   * @since 2.0
   */
  public static final boolean IS_OS_WINDOWS_95 = getOSMatches(
      OS_NAME_WINDOWS_PREFIX + " 9", "4.0");
  // JDK 1.2 running on Windows98 returns 'Windows 95', hence the above

  /**
   * Is {@code true} if this is Windows 98.
   *
   * The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   *
   * @since 2.0
   */
  public static final boolean IS_OS_WINDOWS_98 = getOSMatches(
      OS_NAME_WINDOWS_PREFIX + " 9", "4.1");

  /**
   * Is {@code true} if this is Windows ME.
   *
   * The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   */
  public static final boolean IS_OS_WINDOWS_ME = getOSMatches(
      OS_NAME_WINDOWS_PREFIX, "4.9");

  /**
   * Is {@code true} if this is Windows NT.
   *
   * The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   */
  public static final boolean IS_OS_WINDOWS_NT = getOSMatches(OS_NAME_WINDOWS_PREFIX
      + " NT");

  /**
   * Is {@code true} if this is Windows XP.
   *
   * The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   */
  public static final boolean IS_OS_WINDOWS_XP = getOSMatches(
      OS_NAME_WINDOWS_PREFIX, "5.1");

  /**
   * Is {@code true} if this is Windows Vista.
   *
   * The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   */
  public static final boolean IS_OS_WINDOWS_VISTA = getOSMatches(
      OS_NAME_WINDOWS_PREFIX, "6.0");

  /**
   * The native {@code ByteOrder} of this system.
   */
  public static final ByteOrder BYTE_ORDER = ByteOrder.nativeOrder();

  /**
   * {@code true}, if this platform supports unmapping mmapped files.
   */
  public static final boolean UNMAP_MMAP_SUPPORTED;
  static {
    boolean supported;
    try {
      Class.forName("sun.misc.Cleaner");
      Class.forName("java.nio.DirectByteBuffer").getMethod("cleaner");
      supported = true;
    } catch (final Exception e) {
      supported = false;
    }
    UNMAP_MMAP_SUPPORTED = supported;
  }

  private static final Logger LOGGER = LoggerFactory
      .getLogger(SystemUtils.class);

  /**
   * Private constructor.
   */
  private SystemUtils() {
  }

  /**
   * Gets the Java version number as a {@code float}.
   *
   * Example return values:
   * <ul>
   * <li>{@code 1.2f} for JDK 1.2
   * <li>{@code 1.31f} for JDK 1.3.1
   * </ul>
   *
   * Patch releases are not reported. Zero is returned if
   * {@link #JAVA_VERSION_TRIMMED} is {@code null}.
   *
   * @return the version, for example 1.31f for JDK 1.3.1
   */
  private static float getJavaVersionAsFloat() {
    if (JAVA_VERSION_TRIMMED == null) {
      return 0f;
    }
    String str = JAVA_VERSION_TRIMMED.substring(0, 3);
    if (JAVA_VERSION_TRIMMED.length() >= 5) {
      str = str + JAVA_VERSION_TRIMMED.substring(4, 5);
    }
    try {
      return Float.parseFloat(str);
    } catch (final NumberFormatException ex) {
      return 0;
    }
  }

  /**
   * Gets the Java version number as an {@code int}.
   *
   * Example return values:
   * <ul>
   * <li>{@code 120} for JDK 1.2
   * <li>{@code 131} for JDK 1.3.1
   * </ul>
   *
   * Patch releases are not reported. Zero is returned if
   * {@link #JAVA_VERSION_TRIMMED} is {@code null}.
   *
   * @return the version, for example 131 for JDK 1.3.1
   */
  private static int getJavaVersionAsInt() {
    if (JAVA_VERSION_TRIMMED == null) {
      return 0;
    }
    String str = JAVA_VERSION_TRIMMED.substring(0, 1);
    str = str + JAVA_VERSION_TRIMMED.substring(2, 3);
    if (JAVA_VERSION_TRIMMED.length() >= 5) {
      str = str + JAVA_VERSION_TRIMMED.substring(4, 5);
    } else {
      str = str + "0";
    }
    try {
      return Integer.parseInt(str);
    } catch (final NumberFormatException e) {
      return 0;
    }
  }

  /**
   * Trims the text of the java version to start with numbers.
   *
   * @return the trimmed java version
   */
  private static String getJavaVersionTrimmed() {
    if (JAVA_VERSION != null) {
      for (int i = 0; i < JAVA_VERSION.length(); i++) {
        final char ch = JAVA_VERSION.charAt(i);
        if ((ch >= '0') && (ch <= '9')) {
          return JAVA_VERSION.substring(i);
        }
      }
    }
    return null;
  }

  /**
   * Decides if the java version matches.
   *
   * @param versionPrefix
   *          the prefix for the java version
   * @return true if matches, or false if not or can't determine
   */
  private static boolean getJavaVersionMatches(final String versionPrefix) {
    if (JAVA_VERSION_TRIMMED == null) {
      return false;
    }
    return JAVA_VERSION_TRIMMED.startsWith(versionPrefix);
  }

  /**
   * Decides if the operating system matches.
   *
   * @param osNamePrefix
   *          the prefix for the os name
   * @return true if matches, or false if not or can't determine
   */
  private static boolean getOSMatches(final String osNamePrefix) {
    if (OS_NAME == null) {
      return false;
    }
    return OS_NAME.startsWith(osNamePrefix);
  }

  /**
   * Decides if the operating system matches.
   *
   * @param osNamePrefix
   *          the prefix for the os name
   * @param osVersionPrefix
   *          the prefix for the version
   * @return true if matches, or false if not or can't determine
   */
  private static boolean getOSMatches(final String osNamePrefix,
      final String osVersionPrefix) {
    if ((OS_NAME == null) || (OS_VERSION == null)) {
      return false;
    }
    return OS_NAME.startsWith(osNamePrefix)
        && OS_VERSION.startsWith(osVersionPrefix);
  }

  /**
   * Gets a System property, defaulting to {@code null} if the property
   * cannot be read. If a {@code SecurityException} is caught, the return
   * value is {@code null} and a message is written to
   * {@code System.err}.
   *
   * @param property
   *          the system property name
   * @return the system property value or {@code null} if no such property
   *         or a security problem occurs.
   */
  public static String getProperty(final String property) {
    try {
      return System.getProperty(property);
    } catch (final SecurityException e) {
      // we are not allowed to look at this property
      LOGGER.error("Failed to read the system property {}.", property, e);
      return null;
    }
  }

  /**
   * Is the Java version at least the requested version.
   *
   * Example input:
   * <ul>
   * <li>{@code 1.2f} to test for JDK 1.2</li>
   * <li>{@code 1.31f} to test for JDK 1.3.1</li>
   * </ul>
   *
   * @param requiredVersion
   *          the required version, for example 1.31f
   * @return {@code true} if the actual version is equal or greater than
   *         the required version
   */
  public static boolean isJavaVersionAtLeast(final float requiredVersion) {
    return JAVA_VERSION_FLOAT >= requiredVersion;
  }

  /**
   * Is the Java version at least the requested version.
   *
   * Example input:
   *
   * <ul>
   * <li>{@code 120} to test for JDK 1.2 or greater</li>
   * <li>{@code 131} to test for JDK 1.3.1 or greater</li>
   * </ul>
   *
   * @param requiredVersion
   *          the required version, for example 131
   * @return {@code true} if the actual version is equal or greater than
   *         the required version
   */
  public static boolean isJavaVersionAtLeast(final int requiredVersion) {
    return JAVA_VERSION_INT >= requiredVersion;
  }

  /**
   * Gets the size in bytes of the default constructed object of a specified
   * class.
   *
   * @param clazz
   *          a specified class, which must has a default constructor.
   * @return the size in bytes of the default constructed object of the
   *         specified class.
   */
  public static long getSizeOf(final Class<?> clazz) {
    final Runtime runtime = Runtime.getRuntime();
    final Object[] objects = new Object[100];
    for (int i = 0; i < 4; ++i) {
      runGC();
    }
    final long memoryUsedBefore = runtime.totalMemory() - runtime.freeMemory();
    for (int i = 0; i < objects.length; ++i) {
      try {
        objects[i] = clazz.newInstance();
      } catch (final InstantiationException e) {
        LOGGER.error(CREATE_INSTANCE_FAILED + clazz, e);
      } catch (final IllegalAccessException e) {
        LOGGER.error(CREATE_INSTANCE_FAILED + clazz, e);
      }
    }
    for (int i = 0; i < 4; ++i) {
      runGC();
    }
    final long memoryUsedAfter = runtime.totalMemory() - runtime.freeMemory();
    return (memoryUsedAfter - memoryUsedBefore) / objects.length;
  }

  public static void runGC() {
    final Runtime runtime = Runtime.getRuntime();
    long usedMem1 = runtime.totalMemory() - runtime.freeMemory();
    long usedMem2 = Long.MAX_VALUE;
    for (int i = 0; i < 1024; ++i) {
      runtime.runFinalization();
      runtime.gc();
      Thread.yield();
      usedMem2 = usedMem1;
      usedMem1 = runtime.totalMemory() - runtime.freeMemory();
      if (usedMem1 >= usedMem2) {
        return;
      }
    }
  }

  /**
   * Try to unmap the buffer, this method silently fails if no support for that
   * in the JVM. On Windows, this leads to the fact, that mmapped files cannot
   * be modified or deleted.
   *
   * @param buffer
   *          the mmapped buffer.
   */
  public static final void cleanupMmapping(final ByteBuffer buffer)
      throws IOException {
    if (UNMAP_MMAP_SUPPORTED) {
      try {
        AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
          @Override
          public Object run() throws Exception {
            final Class<?> bufferClass = buffer.getClass();
            final Method getCleanerMethod = bufferClass.getMethod("cleaner");
            getCleanerMethod.setAccessible(true);
            final Object cleaner = getCleanerMethod.invoke(buffer);
            if (cleaner != null) {
              cleaner.getClass().getMethod("clean").invoke(cleaner);
            }
            return null;
          }
        });
      } catch (final PrivilegedActionException e) {
        final IOException ioe = new IOException(
            "Unable to unmap the mapped buffer");
        ioe.initCause(e.getCause());
        throw ioe;
      }
    }
  }

  private static final String GETTING_CONTEXT_RESOURCE = "Getting resource {} using context class loader ...";

  private static final String GETTING_SYSTEM_RESOURCE = "Getting resource {} using system class loader ...";

  private static final String GETTING_CLASS_RESOURCE = "Getting resource {} using specified class loader ...";

  private static final String RESOURCE_URL_IS = "The URL of the resource {} is: {}";

  public static URL getResource(final String resource) {
    LOGGER.debug(GETTING_CONTEXT_RESOURCE, resource);
    final ClassLoader loader = Thread.currentThread().getContextClassLoader();
    URL url = loader.getResource(resource);
    if (url == null) {
      LOGGER.debug(GETTING_SYSTEM_RESOURCE, resource);
      url = ClassLoader.getSystemResource(resource);
    }
    LOGGER.debug(RESOURCE_URL_IS, resource, url);
    return url;
  }

  public static URL getResource(final String resource, final Class<?> clazz) {
    LOGGER.debug(GETTING_CLASS_RESOURCE, resource);
    URL url = clazz.getResource(resource);
    if (url == null) {
      LOGGER.debug(GETTING_CONTEXT_RESOURCE, resource);
      final ClassLoader loader = Thread.currentThread().getContextClassLoader();
      url = loader.getResource(resource);
      if (url == null) {
        LOGGER.debug(GETTING_SYSTEM_RESOURCE, resource);
        url = ClassLoader.getSystemResource(resource);
      }
    }
    LOGGER.debug(RESOURCE_URL_IS, resource, url);
    return url;
  }

  public static URL getResource(final String resource, final ClassLoader loader) {
    LOGGER.debug(GETTING_CLASS_RESOURCE, resource);
    URL url = loader.getResource(resource);
    if (url == null) {
      LOGGER.debug(GETTING_CONTEXT_RESOURCE, resource);
      final Thread current = Thread.currentThread();
      final ClassLoader contextLoader = current.getContextClassLoader();
      url = contextLoader.getResource(resource);
      if (url == null) {
        LOGGER.debug(GETTING_SYSTEM_RESOURCE, resource);
        url = ClassLoader.getSystemResource(resource);
      }
    }
    LOGGER.debug(RESOURCE_URL_IS, resource, url);
    return url;
  }

  /**
   * Dump all of the thread's information and stack traces.
   *
   * @return the thread's information and stack traces as a string.
   */
  public static String getThreadInfo() {
    final int STACK_DEPTH = 20;
    final StringBuilder builder = new StringBuilder();
    final ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
    final boolean contention = threadBean.isThreadContentionMonitoringEnabled();
    final long[] threadIds = threadBean.getAllThreadIds();
    builder.append("Process Thread Dump:\n");
    builder.append(threadIds.length).append(" active threads\n");
    for (final long tid : threadIds) {
      final ThreadInfo info = threadBean.getThreadInfo(tid, STACK_DEPTH);
      if (info == null) {
        builder.append("  Inactive\n");
        continue;
      }
      builder.append("Thread ").append(info.getThreadId()).append(" (")
          .append(info.getThreadName()).append("):\n");
      final Thread.State state = info.getThreadState();
      builder.append("  State:         ").append(state).append('\n');
      builder.append("  Blocked count: ").append(info.getBlockedCount())
          .append('\n');
      builder.append("  Waited count:  ").append(info.getWaitedCount())
          .append('\n');
      if (contention) {
        builder.append("  Blocked time:  ").append(info.getBlockedTime())
            .append('\n');
        builder.append("  Waited time:   ").append(info.getWaitedTime())
            .append('\n');
      }
      if (state == Thread.State.WAITING) {
        builder.append("  Waiting on:    ").append(info.getLockName())
            .append('\n');
      } else if (state == Thread.State.BLOCKED) {
        builder.append("  Blocked on:    ").append(info.getLockName())
            .append('\n');
        builder.append("  Blocked by:    ").append(info.getLockOwnerId())
            .append(" (").append(info.getLockOwnerName()).append(")\n");
      }
      builder.append("  Stack:\n");
      for (final StackTraceElement frame : info.getStackTrace()) {
        builder.append("    ").append(frame.toString()).append('\n');
      }
    }
    return builder.toString();
  }

  /**
   * Gets a lazy initialized globally {@link SecureRandom} object.
   *
   * @return a lazy initialized globally {@link SecureRandom} object.
   */
  public static SecureRandom getSecureRandom() {
    return LazySecureRandom.random;
  }

  // lazy initialization of SecureRandom
  private static class LazySecureRandom {
    static final SecureRandom random = new SecureRandom();
  }

  /**
   * Gets a lazy initialized globally {@link Random} object.
   *
   * @return a lazy initialized globally {@link Random} object.
   */
  public static Random getRandom() {
    return LazyRandom.random;
  }

  // lazy initialization of Random
  private static class LazyRandom {
    static final Random random = new Random();
  }

  /**
   * Generates a random name with the specified prefix and suffix.
   *
   * @param prefix
   *          a specified prefix, which could not be null but can be an empty
   *          string.
   * @param suffix
   *          a specified suffix, which could not be null but can be an empty
   *          string.
   * @return a randomly generated temporary name.
   */
  public static String generateRandomName(final String prefix,
      final String suffix) {
    long n = LazySecureRandom.random.nextLong();
    if (n == Long.MIN_VALUE) {
      n = 0; // corner case
    } else {
      n = Math.abs(n);
    }
    return prefix + Long.toString(n) + suffix;
  }

}
