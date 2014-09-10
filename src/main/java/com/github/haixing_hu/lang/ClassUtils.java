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

package com.github.haixing_hu.lang;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.concurrent.ThreadSafe;

import com.github.haixing_hu.util.filter.character.WhitespaceCharFilter;

import static com.github.haixing_hu.lang.Argument.requireNonNull;
import static com.github.haixing_hu.lang.StringUtils.EMPTY;
import static com.github.haixing_hu.lang.StringUtils.removeChar;

/**
 * This class provides operations on {@link Class} objects.
 * <p>
 * This class tries to handle <code>null</code> input gracefully. An exception
 * will not be thrown for a <code>null</code> input. Each method documents its
 * behavior in more detail.
 * </p>
 * <p>
 * This class also handle the conversion from {@link Class} objects to common
 * types.
 * </p>
 *
 * @author Haixing Hu
 * @since 1.0.0
 */
@ThreadSafe
public final class ClassUtils {

  /**
   * The default {@link Class} value used when necessary.
   *
   * @since 1.0.0
   */
  public static final Class<?> DEFAULT = null;

  /**
   * The package separator character: <code>'&#x2e;' == {@value}</code>.
   *
   * @since 1.0.0
   */
  public static final char PACKAGE_SEPARATOR_CHAR = '.';

  /**
   * The package separator String: <code>"&#x2e;"</code>.
   *
   * @since 1.0.0
   */
  public static final String PACKAGE_SEPARATOR = ".";

  /**
   * The inner class separator character: <code>'$' == {@value}</code>.
   *
   * @since 1.0.0
   */
  public static final char INNER_CLASS_SEPARATOR_CHAR = '$';

  /**
   * The inner class separator String: <code>"$"</code>.
   *
   * @since 1.0.0
   */
  public static final String INNER_CLASS_SEPARATOR = "$";

  /**
   * Maps primitive <code>Class</code>es to their corresponding wrapper
   * <code>Class</code>.
   *
   * @since 1.0.0
   */
  private static Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<Class<?>, Class<?>>();

  static {
    primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
    primitiveWrapperMap.put(Byte.TYPE, Byte.class);
    primitiveWrapperMap.put(Character.TYPE, Character.class);
    primitiveWrapperMap.put(Short.TYPE, Short.class);
    primitiveWrapperMap.put(Integer.TYPE, Integer.class);
    primitiveWrapperMap.put(Long.TYPE, Long.class);
    primitiveWrapperMap.put(Double.TYPE, Double.class);
    primitiveWrapperMap.put(Float.TYPE, Float.class);
    primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
  }

  /**
   * Maps wrapper <code>Class</code>es to their corresponding primitive types.
   *
   * @since 1.0.0
   */
  private static Map<Class<?>, Class<?>> wrapperPrimitiveMap = new HashMap<Class<?>, Class<?>>();

  static {
    for (final Class<?> primitiveClass : primitiveWrapperMap.keySet()) {
      final Class<?> wrapperClass = primitiveWrapperMap.get(primitiveClass);
      if (! primitiveClass.equals(wrapperClass)) {
        wrapperPrimitiveMap.put(wrapperClass, primitiveClass);
      }
    }
  }

  /**
   * Maps a primitive class name to its corresponding abbreviation used in array
   * class names.
   *
   * @since 1.0.0
   */
  private static Map<String, String> abbreviationMap = new HashMap<String, String>();

  /**
   * Maps an abbreviation used in array class names to corresponding primitive
   * class name.
   *
   * @since 1.0.0
   */
  private static Map<String, String> reverseAbbreviationMap = new HashMap<String, String>();

  /**
   * Add primitive type abbreviation to maps of abbreviations.
   *
   * @param primitive
   *          Canonical name of primitive type
   * @param abbreviation
   *          Corresponding abbreviation of primitive type
   * @since 1.0.0
   */
  private static void addAbbreviation(final String primitive,
      final String abbreviation) {
    abbreviationMap.put(primitive, abbreviation);
    reverseAbbreviationMap.put(abbreviation, primitive);
  }

  /**
   * Feed abbreviation maps.
   *
   * @since 1.0.0
   */
  static {
    addAbbreviation("int", "I");
    addAbbreviation("boolean", "Z");
    addAbbreviation("float", "F");
    addAbbreviation("long", "J");
    addAbbreviation("short", "S");
    addAbbreviation("byte", "B");
    addAbbreviation("double", "D");
    addAbbreviation("char", "C");
  }

  private ClassUtils() {
  }

  /**
   * Checks if an array of Classes can be assigned to another array of Classes.
   * <p>
   * This method calls {@link #isAssignable(Class, Class) isAssignable} for each
   * Class pair in the input arrays. It can be used to check if a set of
   * arguments (the first parameter) are suitably compatible with a set of
   * method parameter types (the second parameter).
   * <p>
   * Unlike the {@link Class#isAssignableFrom(java.lang.Class)} method, this
   * method takes into account widenings of primitive classes and
   * <code>null</code>s.
   * <p>
   * Primitive widenings allow an int to be assigned to a <code>long</code>,
   * <code>float</code> or <code>double</code>. This method returns the correct
   * result for these cases.
   * <p>
   * <code>Null</code> may be assigned to any reference type. This method will
   * return <code>true</code> if <code>null</code> is passed in and the toClass
   * is non-primitive.
   * <p>
   * Specifically, this method tests whether the type represented by the
   * specified <code>Class</code> parameter can be converted to the type
   * represented by this <code>Class</code> object via an identity conversion
   * widening primitive or widening reference conversion. See
   * <em><a href="http://java.sun.com/docs/books/jls/">The Java Language Specification</a></em>
   * , sections 5.1.1, 5.1.2 and 5.1.4 for details.
   * </p>
   *
   * @param classArray
   *          the array of Classes to check, may be <code>null</code>.
   * @param toClassArray
   *          the array of Classes to try to assign into, may be
   *          <code>null</code>.
   * @return <code>true</code> if assignment possible.
   * @since 1.0.0
   */
  public static boolean isAssignable(Class<?>[] classArray,
      Class<?>[] toClassArray) {
    if (ArrayUtils.isSameLength(classArray, toClassArray) == false) {
      return false;
    }
    if (classArray == null) {
      classArray = ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    if (toClassArray == null) {
      toClassArray = ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    for (int i = 0; i < classArray.length; i++) {
      if (isAssignable(classArray[i], toClassArray[i]) == false) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if one <code>Class</code> can be assigned to a variable of another
   * <code>Class</code>.
   * <p>
   * Unlike the {@link Class#isAssignableFrom(java.lang.Class)} method, this
   * method takes into account widening of primitive classes and
   * <code>null</code>s.
   * <p>
   * Primitive widening allow an int to be assigned to a long, float or double.
   * This method returns the correct result for these cases.
   * <p>
   * <code>Null</code> may be assigned to any reference type. This method will
   * return <code>true</code> if <code>null</code> is passed in and the toClass
   * is non-primitive.
   * <p>
   * Specifically, this method tests whether the type represented by the
   * specified <code>cls</code> parameter can be converted to the type
   * represented by the <code>toClass</code> parameter via an identity conversion
   * widening primitive or widening reference conversion. See
   * <em><a href="http://java.sun.com/docs/books/jls/">The Java Language Specification</a></em>
   * , sections 5.1.1, 5.1.2 and 5.1.4 for details.
   *
   * @param cls
   *          the Class to check, may be null.
   * @param toClass
   *          the Class to try to assign into, returns false if null.
   * @return <code>true</code> if assignment possible.
   * @since 1.0.0
   */
  public static boolean isAssignable(Class<?> cls, Class<?> toClass) {
    if (toClass == null) {
      return false;
    }
    // have to check for null, as isAssignableFrom doesn't
    if (cls == null) {
      return (! toClass.isPrimitive());
    }
    // autoboxing for JDK >= 1.5
    if (SystemUtils.isJavaVersionAtLeast(1.5f)) {
      if (cls.isPrimitive() && (! toClass.isPrimitive())) {
        cls = primitiveToWrapper(cls);
        if (cls == null) {
          return false;
        }
      }
      if (toClass.isPrimitive() && (! cls.isPrimitive())) {
        cls = wrapperToPrimitive(cls);
        if (cls == null) {
          return false;
        }
      }
    }
    if (cls.equals(toClass)) {
      return true;
    }
    if (cls.isPrimitive()) {
      if (toClass.isPrimitive() == false) {
        return false;
      }
      if (Integer.TYPE.equals(cls)) {
        return Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass)
            || Double.TYPE.equals(toClass);
      }
      if (Long.TYPE.equals(cls)) {
        return Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
      }
      if (Boolean.TYPE.equals(cls)) {
        return false;
      }
      if (Double.TYPE.equals(cls)) {
        return false;
      }
      if (Float.TYPE.equals(cls)) {
        return Double.TYPE.equals(toClass);
      }
      if (Character.TYPE.equals(cls)) {
        return Integer.TYPE.equals(toClass) || Long.TYPE.equals(toClass)
            || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
      }
      if (Short.TYPE.equals(cls)) {
        return Integer.TYPE.equals(toClass) || Long.TYPE.equals(toClass)
            || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
      }
      if (Byte.TYPE.equals(cls)) {
        return Short.TYPE.equals(toClass) || Integer.TYPE.equals(toClass)
            || Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass)
            || Double.TYPE.equals(toClass);
      }
      // should never get here
      return false;
    }
    return toClass.isAssignableFrom(cls);
  }

  /**
   * Tests whether the specified class an inner class or static nested class.
   *
   * @param cls
   *          the class to check, may be null.
   * @return <code>true</code> if the class is an inner or static nested class,
   *         false if not or <code>null</code>.
   * @since 1.0.0
   */
  public static boolean isInnerClass(final Class<?> cls) {
    if (cls == null) {
      return false;
    }
    return cls.getName().indexOf(INNER_CLASS_SEPARATOR_CHAR) >= 0;
  }

  /**
   * Given a <code>List</code> of class names, this method converts them into
   * classes.
   * <p>
   * A new <code>List</code> is returned. If the class name cannot be found,
   * <code>null</code> is stored in the <code>List</code>. If the class name in
   * the <code>List</code> is <code>null</code>, <code>null</code> is stored in
   * the output <code>List</code>.
   *
   * @param classNames
   *          the classNames to change.
   * @return a <code>List</code> of Class objects corresponding to the class
   *         names, <code>null</code> if null input.
   * @throws ClassCastException
   *           if classNames contains a non String entry.
   * @since 1.0.0
   */
  public static List<Class<?>> namesToClasses(final List<String> classNames) {
    if (classNames == null) {
      return null;
    }
    final List<Class<?>> classes = new ArrayList<Class<?>>(classNames.size());
    for (final String className : classNames) {
      try {
        classes.add(Class.forName(className));
      } catch (final ClassNotFoundException ex) {
        classes.add(null);
      }
    }
    return classes;
  }

  /**
   * Given a <code>List</code> of <code>Class</code> objects, this method
   * converts them into class names.
   * <p>
   * A new <code>List</code> is returned. <code>null</code> objects will be
   * copied into the returned list as <code>null</code>.
   *
   * @param classes
   *          the classes to change.
   * @return a <code>List</code> of class names corresponding to the Class
   *         objects, <code>null</code> if null input.
   * @throws ClassCastException
   *           if <code>classes</code> contains a non-<code>Class</code> entry.
   * @since 1.0.0
   */
  public static List<String> classesToNames(final List<Class<?>> classes) {
    if (classes == null) {
      return null;
    }
    final List<String> classNames = new ArrayList<String>(classes.size());
    for (final Class<?> cls : classes) {
      if (cls == null) {
        classNames.add(null);
      } else {
        classNames.add(cls.getName());
      }
    }
    return classNames;
  }

  /**
   * Converts the specified wrapper class to its corresponding primitive class.
   * <p>
   * This method is the counter part of <code>primitiveToWrapper()</code>. If
   * the passed in class is a wrapper class for a primitive type, this primitive
   * type will be returned (e.g. <code>Integer.TYPE</code> for
   * <code>Integer.class</code>). For other classes, or if the parameter is
   * <b>null</b>, the return value is <b>null</b>.
   *
   * @param cls
   *          the class to convert, may be <b>null</b>.
   * @return the corresponding primitive type if <code>cls</code> is a wrapper
   *         class, <b>null</b> otherwise.
   * @see #primitiveToWrapper(Class)
   * @since 1.0.0
   */
  public static Class<?> wrapperToPrimitive(final Class<?> cls) {
    return wrapperPrimitiveMap.get(cls);
  }

  /**
   * Converts the specified primitive Class object to its corresponding wrapper
   * Class object.
   *
   * @param cls
   *          the class to convert, may be null.
   * @return the wrapper class for <code>cls</code> or <code>cls</code> if
   *         <code>cls</code> is not a primitive. <code>null</code> if null
   *         input.
   * @since 1.0.0
   */
  public static Class<?> primitiveToWrapper(final Class<?> cls) {
    Class<?> convertedClass = cls;
    if ((cls != null) && cls.isPrimitive()) {
      convertedClass = primitiveWrapperMap.get(cls);
    }
    return convertedClass;
  }

  /**
   * Converts the specified array of wrapper Class objects to an array of its
   * corresponding primitive Class objects.
   * <p>
   * This method invokes <code>wrapperToPrimitive()</code> for each element of
   * the passed in array.
   *
   * @param classes
   *          the class array to convert, may be null or empty
   * @return an array which contains for each given class, the primitive class
   *         or <b>null</b> if the original class is not a wrapper class.
   *         <code>null</code> if null input. Empty array if an empty array
   *         passed in.
   * @see #wrapperToPrimitive(Class)
   * @since 1.0.0
   */
  public static Class<?>[] wrappersToPrimitives(final Class<?>[] classes) {
    if (classes == null) {
      return null;
    }
    if (classes.length == 0) {
      return classes;
    }
    final Class<?>[] convertedClasses = new Class<?>[classes.length];
    for (int i = 0; i < classes.length; i++) {
      convertedClasses[i] = wrapperPrimitiveMap.get(classes[i]);
    }
    return convertedClasses;
  }

  /**
   * Converts the specified array of primitive Class objects to an array of its
   * corresponding wrapper Class objects.
   *
   * @param classes
   *          the class array to convert, may be null or empty
   * @return an array which contains for each given class, the wrapper class or
   *         the original class if class is not a primitive. <code>null</code>
   *         if null input. Empty array if an empty array passed in.
   * @since 1.0.0
   */
  public static Class<?>[] primitivesToWrappers(final Class<?>[] classes) {
    if (classes == null) {
      return null;
    }
    if (classes.length == 0) {
      return classes;
    }
    final Class<?>[] convertedClasses = new Class[classes.length];
    for (int i = 0; i < classes.length; i++) {
      if ((classes[i] != null) && classes[i].isPrimitive()) {
        convertedClasses[i] = primitiveWrapperMap.get(classes[i]);
      } else {
        convertedClasses[i] = classes[i];
      }
    }
    return convertedClasses;
  }

  /**
   * Converts an array of <code>Object</code> in to an array of
   * <code>Class</code> objects.
   * <p>
   * This method returns <code>null</code> for a <code>null</code> input array.
   *
   * @param array
   *          an <code>Object</code> array.
   * @return a <code>Class</code> array, <code>null</code> if null array input.
   * @since 1.0.0
   */
  public static Class<?>[] toClass(final Object[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    final Class<?>[] classes = new Class<?>[array.length];
    for (int i = 0; i < array.length; i++) {
      classes[i] = array[i].getClass();
    }
    return classes;
  }

  /**
   * Gets the class represented by <code>className</code> using the
   * <code>classLoader</code>.
   * <p>
   * This implementation supports names like " <code>java.lang.String[]</code>
   * " as well as " <code>[Ljava.lang.String;</code>".
   *
   * @param classLoader
   *          the class loader to use to load the class
   * @param className
   *          the class name
   * @param initialize
   *          whether the class must be initialized
   * @return the class represented by <code>className</code> using the
   *         <code>classLoader</code>
   * @throws NullPointerException
   *           if any argument is null.
   * @throws ClassNotFoundException
   *           if the class is not found.
   * @since 1.0.0
   */
  public static Class<?> getClass(final ClassLoader classLoader,
      final String className, final boolean initialize)
      throws ClassNotFoundException {
    requireNonNull("className", className);
    Class<?> clazz = null;
    if (abbreviationMap.containsKey(className)) {
      final String clsName = "[" + abbreviationMap.get(className);
      clazz = Class.forName(clsName, initialize, classLoader)
          .getComponentType();
    } else {
      clazz = Class
          .forName(toCanonicalName(className), initialize, classLoader);
    }
    return clazz;
  }

  /**
   * Gets the (initialized) class represented by <code>className</code> using
   * the <code>classLoader</code>.
   * <p>
   * This implementation supports names like " <code>java.lang.String[]</code>
   * " as well as " <code>[Ljava.lang.String;</code>".
   *
   * @param classLoader
   *          the class loader to use to load the class.
   * @param className
   *          the class name.
   * @return the class represented by <code>className</code> using the
   *         <code>classLoader</code>.
   * @throws NullPointerException
   *           if any argument is null.
   * @throws ClassNotFoundException
   *           if the class is not found.
   * @since 1.0.0
   */
  public static Class<?> getClass(final ClassLoader classLoader,
      final String className) throws ClassNotFoundException {
    return getClass(classLoader, className, true);
  }

  /**
   * Gets the (initialized )class represented by <code>className</code> using
   * the current thread's context class loader.
   * <p>
   * This implementation supports names like "<code>java.lang.String[]</code>
   * " as well as " <code>[Ljava.lang.String;</code>".
   *
   * @param className
   *          the class name.
   * @return the class represented by <code>className</code> using the current
   *         thread's context class loader.
   * @throws NullPointerException
   *           if any argument is null.
   * @throws ClassNotFoundException
   *           if the class is not found.
   * @since 1.0.0
   */
  public static Class<?> getClass(final String className)
      throws ClassNotFoundException {
    return getClass(className, true);
  }

  /**
   * Gets the class represented by <code>className</code> using the current
   * thread's context class loader.
   * <p>
   * This implementation supports names like " <code>java.lang.String[]</code>
   * " as well as " <code>[Ljava.lang.String;</code>".
   *
   * @param className
   *          the class name.
   * @param initialize
   *          whether the class must be initialized.
   * @return the class represented by <code>className</code> using the current
   *         thread's context class loader.
   * @throws NullPointerException
   *           if any argument is null.
   * @throws ClassNotFoundException
   *           if the class is not found.
   * @since 1.0.0
   */
  public static Class<?> getClass(final String className,
      final boolean initialize) throws ClassNotFoundException {
    final ClassLoader contextCL = Thread.currentThread()
        .getContextClassLoader();
    ClassLoader loader = contextCL;
    if (loader == null) {
      loader = ClassUtils.class.getClassLoader();
    }
    return getClass(loader, className, initialize);
  }

  /**
   * Gets the class name minus the package name for an <code>Object</code>.
   *
   * @param object
   *          the class to get the short name for, may be null.
   * @param valueIfNull
   *          the value to return if null.
   * @return the class name of the object without the package name, or the null
   *         value.
   * @since 1.0.0
   */
  public static String getShortClassName(final Object object,
      final String valueIfNull) {
    if (object == null) {
      return valueIfNull;
    } else {
      return getShortClassName(object.getClass().getName());
    }
  }

  /**
   * Gets the class name minus the package name from a <code>Class</code>.
   *
   * @param cls
   *          the class to get the short name for.
   * @return the class name without the package name or an empty string.
   * @since 1.0.0
   */
  public static String getShortClassName(final Class<?> cls) {
    if (cls == null) {
      return EMPTY;
    } else {
      return getShortClassName(cls.getName());
    }
  }

  /**
   * Gets the class name minus the package name from a String.
   * <p>
   * The string passed in is assumed to be a class name - it is not checked.
   *
   * @param className
   *          the className to get the short name for.
   * @return the class name of the class without the package name or an empty
   *         string.
   * @since 1.0.0
   */
  public static String getShortClassName(final String className) {
    if ((className == null) || (className.length() == 0)) {
      return EMPTY;
    }
    final int lastDotIdx = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);
    final int innerIdx = className.indexOf(INNER_CLASS_SEPARATOR_CHAR,
        lastDotIdx == - 1 ? 0 : lastDotIdx + 1);
    String out = className.substring(lastDotIdx + 1);
    if (innerIdx != - 1) {
      out = out.replace(INNER_CLASS_SEPARATOR_CHAR, PACKAGE_SEPARATOR_CHAR);
    }
    return out;
  }

  /**
   * Gets the package name of an <code>Object</code>.
   *
   * @param object
   *          the class to get the package name for, may be null.
   * @param valueIfNull
   *          the value to return if null.
   * @return the package name of the object, or the null value.
   * @since 1.0.0
   */
  public static String getPackageName(final Object object,
      final String valueIfNull) {
    if (object == null) {
      return valueIfNull;
    } else {
      return getPackageName(object.getClass().getName());
    }
  }

  /**
   * Gets the package name of a <code>Class</code>.
   *
   * @param cls
   *          the class to get the package name for, may be <code>null</code>.
   * @return the package name or an empty string.
   * @since 1.0.0
   */
  public static String getPackageName(final Class<?> cls) {
    if (cls == null) {
      return EMPTY;
    } else {
      return getPackageName(cls.getName());
    }
  }

  /**
   * Gets the package name from a <code>String</code>.
   * <p>
   * The string passed in is assumed to be a class name - it is not checked.
   * <p>
   * If the class is unpackaged, return an empty string.
   *
   * @param className
   *          the className to get the package name for, may be
   *          <code>null</code>.
   * @return the package name or an empty string.
   * @since 1.0.0
   */
  public static String getPackageName(final String className) {
    if (className == null) {
      return EMPTY;
    }
    final int i = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);
    if (i == - 1) {
      return EMPTY;
    }
    return className.substring(0, i);
  }

  /**
   * Gets the canonical name minus the package name for an <code>Object</code>.
   *
   * @param object
   *          the class to get the short name for, may be null.
   * @param valueIfNull
   *          the value to return if null.
   * @return the canonical name of the object without the package name, or the
   *         null value.
   * @since 1.0.0
   */
  public static String getShortCanonicalName(final Object object,
      final String valueIfNull) {
    if (object == null) {
      return valueIfNull;
    }
    return getShortCanonicalName(object.getClass().getName());
  }

  /**
   * Gets the canonical name minus the package name from a <code>Class</code>.
   *
   * @param cls
   *          the class to get the short name for.
   * @return the canonical name without the package name or an empty string.
   * @since 1.0.0
   */
  public static String getShortCanonicalName(final Class<?> cls) {
    if (cls == null) {
      return EMPTY;
    }
    return getShortCanonicalName(cls.getName());
  }

  /**
   * Gets the canonical name minus the package name from a String.
   * <p>
   * The string passed in is assumed to be a canonical name - it is not checked.
   *
   * @param canonicalName
   *          the class name to get the short name for.
   * @return the canonical name of the class without the package name or an
   *         empty string.
   * @since 1.0.0
   */
  public static String getShortCanonicalName(final String canonicalName) {
    return ClassUtils.getShortClassName(getCanonicalName(canonicalName));
  }

  /**
   * Gets the package name from the canonical name of an <code>Object</code>.
   *
   * @param object
   *          the class to get the package name for, may be null.
   * @param valueIfNull
   *          the value to return if null.
   * @return the package name of the object, or the null value.
   * @since 1.0.0
   */
  public static String getPackageCanonicalName(final Object object,
      final String valueIfNull) {
    if (object == null) {
      return valueIfNull;
    }
    return getPackageCanonicalName(object.getClass().getName());
  }

  /**
   * Gets the package name from the canonical name of a <code>Class</code>.
   *
   * @param cls
   *          the class to get the package name for, may be <code>null</code>.
   * @return the package name or an empty string.
   * @since 1.0.0
   */
  public static String getPackageCanonicalName(final Class<?> cls) {
    if (cls == null) {
      return EMPTY;
    }
    return getPackageCanonicalName(cls.getName());
  }

  /**
   * Gets the package name from the canonical name.
   * <p>
   * The string passed in is assumed to be a canonical name - it is not checked.
   * <p>
   * If the class is unpackaged, return an empty string.
   *
   * @param canonicalName
   *          the canonical name to get the package name for, may be
   *          <code>null</code>.
   * @return the package name or an empty string.
   * @since 1.0.0
   */
  public static String getPackageCanonicalName(final String canonicalName) {
    return ClassUtils.getPackageName(getCanonicalName(canonicalName));
  }

  /**
   * Gets a <code>List</code> of super-classes for the given class.
   *
   * @param cls
   *          the class to look up, may be <code>null</code>.
   * @return the <code>List</code> of super-classes in order going up from this
   *         one <code>null</code> if null input.
   * @since 1.0.0
   */
  public static List<Class<?>> getAllSuperclasses(final Class<?> cls) {
    if (cls == null) {
      return null;
    }
    final List<Class<?>> classes = new ArrayList<Class<?>>();
    Class<?> superclass = cls.getSuperclass();
    while (superclass != null) {
      classes.add(superclass);
      superclass = superclass.getSuperclass();
    }
    return classes;
  }

  /**
   * Gets a <code>List</code> of all interfaces implemented by the given class
   * and its super-classes.
   * <p>
   * The order is determined by looking through each interface in turn as
   * declared in the source file and following its hierarchy up. Then each
   * superclass is considered in the same way. Later duplicates are ignored, so
   * the order is maintained.
   *
   * @param cls
   *          the class to look up, may be <code>null</code>.
   * @return the <code>List</code> of interfaces in order, <code>null</code> if
   *         null input.
   * @since 1.0.0
   */
  public static List<Class<?>> getAllInterfaces(Class<?> cls) {
    if (cls == null) {
      return null;
    }
    final List<Class<?>> list = new ArrayList<Class<?>>();
    while (cls != null) {
      final Class<?>[] interfaces = cls.getInterfaces();
      for (final Class<?> interface1 : interfaces) {
        if (list.contains(interface1) == false) {
          list.add(interface1);
        }
        final List<Class<?>> superInterfaces = getAllInterfaces(interface1);
        for (final Class<?> intface : superInterfaces) {
          if (list.contains(intface) == false) {
            list.add(intface);
          }
        }
      }
      cls = cls.getSuperclass();
    }
    return list;
  }

  /**
   * Gets the desired Method much like <code>Class.getMethod</code>, however it
   * ensures that the returned Method is from a public class or interface and
   * not from an anonymous inner class. ,p This means that the Method is
   * invokable and doesn't fall foul of Java bug <a
   * href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4071957"
   * >4071957</a>).
   * <p>
   * <code><pre>Set set = Collections.unmodifiableSet(...);
   *  Method method = ClassUtils.getPublicMethod(set.getClass(), "isEmpty",  new Class[0]);
   *  Object result = method.invoke(set, new Object[]);</pre></code>
   *
   * @param cls
   *          the class to check, not null
   * @param methodName
   *          the name of the method
   * @param parameterTypes
   *          the list of parameters
   * @return the method
   * @throws NullPointerException
   *           if the class is null
   * @throws SecurityException
   *           if a a security violation occurred.
   * @throws NoSuchMethodException
   *           if the method is not found in the given class or if the method
   *           doen't conform with the requirements.
   * @since 1.0.0
   */
  public static Method getPublicMethod(final Class<?> cls,
      final String methodName, final Class<?> parameterTypes[])
      throws SecurityException, NoSuchMethodException {

    final Method declaredMethod = cls.getMethod(methodName, parameterTypes);
    if (Modifier.isPublic(declaredMethod.getDeclaringClass().getModifiers())) {
      return declaredMethod;
    }
    final List<Class<?>> candidateClasses = new ArrayList<Class<?>>();
    candidateClasses.addAll(getAllInterfaces(cls));
    candidateClasses.addAll(getAllSuperclasses(cls));

    for (final Class<?> candidateClass : candidateClasses) {
      if (! Modifier.isPublic(candidateClass.getModifiers())) {
        continue;
      }
      Method candidateMethod;
      try {
        candidateMethod = candidateClass.getMethod(methodName, parameterTypes);
      } catch (final NoSuchMethodException ex) {
        continue;
      }
      if (Modifier.isPublic(candidateMethod.getDeclaringClass().getModifiers())) {
        return candidateMethod;
      }
    }
    throw new NoSuchMethodException("Can't find a public method for "
        + methodName + " " + ArrayUtils.toString(parameterTypes));
  }

  /**
   * Converts a class name to a JLS style class name.
   *
   * @param className
   *          the class name.
   * @return the converted name.
   * @since 1.0.0
   */
  private static String toCanonicalName(String className) {
    requireNonNull("className", className);
    className = removeChar(className, WhitespaceCharFilter.INSTANCE, - 1);
    if (className.endsWith("[]")) {
      final StringBuilder builder = new StringBuilder();
      while (className.endsWith("[]")) {
        className = className.substring(0, className.length() - 2);
        builder.append('[');
      }
      final String abbreviation = abbreviationMap.get(className);
      if (abbreviation != null) {
        builder.append(abbreviation);
      } else {
        builder.append('L').append(className).append(';');
      }
      className = builder.toString();
    }
    return className;
  }

  /**
   * Converts a given name of class into canonical format. If name of class is
   * not a name of array class it returns unchanged name.
   * <p>
   * Example:
   * <ul>
   * <li><code>getCanonicalName("[I") = "int[]"</code></li>
   * <li>
   * <code>getCanonicalName("[Ljava.lang.String;") = "java.lang.String[]"</code>
   * </li>
   * <li><code>getCanonicalName("java.lang.String") = "java.lang.String"</code></li>
   * </ul>
   *
   * @param className
   *          the name of class.
   * @return canonical form of class name.
   * @since 1.0.0
   */
  private static String getCanonicalName(String className) {
    className = removeChar(className, WhitespaceCharFilter.INSTANCE, - 1);
    if (className == null) {
      return null;
    } else {
      int dim = 0;
      while (className.startsWith("[")) {
        dim++;
        className = className.substring(1);
      }
      if (dim < 1) {
        return className;
      } else {
        if (className.startsWith("L")) {
          int n = className.length();
          if (className.endsWith(";")) {
            --n;
          }
          className = className.substring(1, n);
        } else {
          if (className.length() > 0) {
            className = reverseAbbreviationMap.get(className.substring(0, 1));
          }
        }
        final StringBuilder builder = new StringBuilder();
        builder.append(className);
        for (int i = 0; i < dim; ++i) {
          builder.append("[]");
        }
        return builder.toString();
      }
    }
  }

}
