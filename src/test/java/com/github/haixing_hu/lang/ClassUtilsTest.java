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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.github.haixing_hu.lang.ArrayUtils;
import com.github.haixing_hu.lang.ClassUtils;

import static org.junit.Assert.*;

/**
 * Unit test for the ClassUtils class.
 *
 * @author Haixing Hu
 */
public class ClassUtilsTest {

  private static class Inner {
  }

  @Test
  public void test_getShortClassName_Object() {
      assertEquals("ClassUtils", ClassUtils.getShortClassName(ClassUtils.class));
      assertEquals("ClassUtilsTest.Inner", ClassUtils.getShortClassName(new Inner(), "<null>"));
      assertEquals("String", ClassUtils.getShortClassName("hello", "<null>"));
      assertEquals("<null>", ClassUtils.getShortClassName(null, "<null>"));
  }

  @Test
  public void test_getShortClassName_Class() {
      assertEquals("ClassUtils", ClassUtils.getShortClassName(ClassUtils.class));
      assertEquals("Map.Entry", ClassUtils.getShortClassName(Map.Entry.class));
      assertEquals("", ClassUtils.getShortClassName((Class<?>) null));
  }

  @Test
  public void test_getShortClassName_String() {
      assertEquals("ClassUtils", ClassUtils.getShortClassName(ClassUtils.class.getName()));
      assertEquals("Map.Entry", ClassUtils.getShortClassName(Map.Entry.class.getName()));
      assertEquals("", ClassUtils.getShortClassName((String) null));
      assertEquals("", ClassUtils.getShortClassName(""));
  }

  @Test
  public void test_getPackageName_Object() {
      assertEquals("com.github.haixing_hu.lang", ClassUtils.getPackageName(ClassUtils.class));
      assertEquals("com.github.haixing_hu.lang", ClassUtils.getPackageName(new Inner(), "<null>"));
      assertEquals("<null>", ClassUtils.getPackageName(null, "<null>"));
  }

  @Test
  public void test_getPackageName_Class() {
      assertEquals("java.lang", ClassUtils.getPackageName(String.class));
      assertEquals("java.util", ClassUtils.getPackageName(Map.Entry.class));
      assertEquals("", ClassUtils.getPackageName((Class<?>)null));
  }

  @Test
  public void test_getPackageName_String() {
      assertEquals("com.github.haixing_hu.lang", ClassUtils.getPackageName(ClassUtils.class.getName()));
      assertEquals("java.util", ClassUtils.getPackageName(Map.Entry.class.getName()));
      assertEquals("", ClassUtils.getPackageName((String)null));
      assertEquals("", ClassUtils.getPackageName(""));
  }

  @Test
  public void test_getAllSuperclasses_Class() {
      final List<Class<?>> list = ClassUtils.getAllSuperclasses(CY.class);
      assertEquals(2, list.size());
      assertEquals(CX.class, list.get(0));
      assertEquals(Object.class, list.get(1));
      assertEquals(null, ClassUtils.getAllSuperclasses(null));
  }

  @Test
  public void test_getAllInterfaces_Class() {
      final List<Class<?>> list = ClassUtils.getAllInterfaces(CY.class);
      assertEquals(6, list.size());
      assertEquals(IB.class, list.get(0));
      assertEquals(IC.class, list.get(1));
      assertEquals(ID.class, list.get(2));
      assertEquals(IE.class, list.get(3));
      assertEquals(IF.class, list.get(4));
      assertEquals(IA.class, list.get(5));

      assertEquals(null, ClassUtils.getAllInterfaces(null));
  }

  private static interface IA {
  }
  private static interface IB {
  }
  private static interface IC extends ID, IE {
  }
  private static interface ID {
  }
  private static interface IE extends IF {
  }
  private static interface IF {
  }
  private static class CX implements IB, IA, IE {
  }
  private static class CY extends CX implements IB, IC {
  }

  @Test
  public void test_convertClassNamesToClassUtils_List() {
      final List<String> list = new ArrayList<String>();
      List<Class<?>> result = ClassUtils.namesToClasses(list);
      assertEquals(0, result.size());

      list.add("java.lang.String");
      list.add("java.lang.xxx");
      list.add("java.lang.Object");
      result = ClassUtils.namesToClasses(list);
      assertEquals(3, result.size());
      assertEquals(String.class, result.get(0));
      assertEquals(null, result.get(1));
      assertEquals(Object.class, result.get(2));

      assertEquals(null, ClassUtils.namesToClasses(null));
  }

  @Test
  public void test_convertClassUtilsToClassNames_List() {
      final List<Class<?>> list = new ArrayList<Class<?>>();
      List<String> result = ClassUtils.classesToNames(list);
      assertEquals(0, result.size());

      list.add(String.class);
      list.add(null);
      list.add(Object.class);
      result = ClassUtils.classesToNames(list);
      assertEquals(3, result.size());
      assertEquals("java.lang.String", result.get(0));
      assertEquals(null, result.get(1));
      assertEquals("java.lang.Object", result.get(2));

      assertEquals(null, ClassUtils.classesToNames(null));
  }

  @Test
  public void test_isInnerClass_Class() {
      assertEquals(true, ClassUtils.isInnerClass(Inner.class));
      assertEquals(true, ClassUtils.isInnerClass(Map.Entry.class));
      assertEquals(false, ClassUtils.isInnerClass(this.getClass()));
      assertEquals(false, ClassUtils.isInnerClass(String.class));
      assertEquals(false, ClassUtils.isInnerClass(null));
  }

  @Test
  public void test_isAssignable_ClassArray_ClassArray() throws Exception {
      final Class<?>[] array2 = new Class<?>[] {Object.class, Object.class};
      final Class<?>[] array1 = new Class<?>[] {Object.class};
      final Class<?>[] array1s = new Class<?>[] {String.class};
      final Class<?>[] array0 = new Class<?>[] {};

      assertEquals(false, ClassUtils.isAssignable(array1, array2));
      assertEquals(false, ClassUtils.isAssignable(null, array2));
      assertEquals(true, ClassUtils.isAssignable(null, array0));
      assertEquals(true, ClassUtils.isAssignable(array0, array0));
      assertEquals(true, ClassUtils.isAssignable(array0, null));
      assertEquals(true, ClassUtils.isAssignable((Class[]) null, (Class[]) null));

      assertEquals(false, ClassUtils.isAssignable(array1, array1s));
      assertEquals(true, ClassUtils.isAssignable(array1s, array1s));
      assertEquals(true, ClassUtils.isAssignable(array1s, array1));
  }

  @Test
  public void test_isAssignable() throws Exception {
      assertEquals(false, ClassUtils.isAssignable((Class<?>) null, null));
      assertEquals(false, ClassUtils.isAssignable(String.class, null));

      assertEquals(true, ClassUtils.isAssignable(null, Object.class));
      assertEquals(true, ClassUtils.isAssignable(null, Integer.class));
      assertEquals(false, ClassUtils.isAssignable(null, Integer.TYPE));
      assertEquals(true, ClassUtils.isAssignable(String.class, Object.class));
      assertEquals(true, ClassUtils.isAssignable(String.class, String.class));
      assertEquals(false, ClassUtils.isAssignable(Object.class, String.class));
      assertEquals(false, ClassUtils.isAssignable(Integer.TYPE, Integer.class));
      assertEquals(false, ClassUtils.isAssignable(Integer.class, Integer.TYPE));
      assertEquals(true, ClassUtils.isAssignable(Integer.TYPE, Integer.TYPE));
      assertEquals(true, ClassUtils.isAssignable(Integer.class, Integer.class));
  }

  @Test
  public void test_isAssignable_Widening() throws Exception {
      // test byte conversions
      assertEquals("byte -> char", false, ClassUtils.isAssignable(Byte.TYPE, Character.TYPE));
      assertEquals("byte -> byte", true, ClassUtils.isAssignable(Byte.TYPE, Byte.TYPE));
      assertEquals("byte -> short", true, ClassUtils.isAssignable(Byte.TYPE, Short.TYPE));
      assertEquals("byte -> int", true, ClassUtils.isAssignable(Byte.TYPE, Integer.TYPE));
      assertEquals("byte -> long", true, ClassUtils.isAssignable(Byte.TYPE, Long.TYPE));
      assertEquals("byte -> float", true, ClassUtils.isAssignable(Byte.TYPE, Float.TYPE));
      assertEquals("byte -> double", true, ClassUtils.isAssignable(Byte.TYPE, Double.TYPE));
      assertEquals("byte -> boolean", false, ClassUtils.isAssignable(Byte.TYPE, Boolean.TYPE));

      // test short conversions
      assertEquals("short -> char", false, ClassUtils.isAssignable(Short.TYPE, Character.TYPE));
      assertEquals("short -> byte", false, ClassUtils.isAssignable(Short.TYPE, Byte.TYPE));
      assertEquals("short -> short", true, ClassUtils.isAssignable(Short.TYPE, Short.TYPE));
      assertEquals("short -> int", true, ClassUtils.isAssignable(Short.TYPE, Integer.TYPE));
      assertEquals("short -> long", true, ClassUtils.isAssignable(Short.TYPE, Long.TYPE));
      assertEquals("short -> float", true, ClassUtils.isAssignable(Short.TYPE, Float.TYPE));
      assertEquals("short -> double", true, ClassUtils.isAssignable(Short.TYPE, Double.TYPE));
      assertEquals("short -> boolean", false, ClassUtils.isAssignable(Short.TYPE, Boolean.TYPE));

      // test char conversions
      assertEquals("char -> char", true, ClassUtils.isAssignable(Character.TYPE, Character.TYPE));
      assertEquals("char -> byte", false, ClassUtils.isAssignable(Character.TYPE, Byte.TYPE));
      assertEquals("char -> short", false, ClassUtils.isAssignable(Character.TYPE, Short.TYPE));
      assertEquals("char -> int", true, ClassUtils.isAssignable(Character.TYPE, Integer.TYPE));
      assertEquals("char -> long", true, ClassUtils.isAssignable(Character.TYPE, Long.TYPE));
      assertEquals("char -> float", true, ClassUtils.isAssignable(Character.TYPE, Float.TYPE));
      assertEquals("char -> double", true, ClassUtils.isAssignable(Character.TYPE, Double.TYPE));
      assertEquals("char -> boolean", false, ClassUtils.isAssignable(Character.TYPE, Boolean.TYPE));

      // test int conversions
      assertEquals("int -> char", false, ClassUtils.isAssignable(Integer.TYPE, Character.TYPE));
      assertEquals("int -> byte", false, ClassUtils.isAssignable(Integer.TYPE, Byte.TYPE));
      assertEquals("int -> short", false, ClassUtils.isAssignable(Integer.TYPE, Short.TYPE));
      assertEquals("int -> int", true, ClassUtils.isAssignable(Integer.TYPE, Integer.TYPE));
      assertEquals("int -> long", true, ClassUtils.isAssignable(Integer.TYPE, Long.TYPE));
      assertEquals("int -> float", true, ClassUtils.isAssignable(Integer.TYPE, Float.TYPE));
      assertEquals("int -> double", true, ClassUtils.isAssignable(Integer.TYPE, Double.TYPE));
      assertEquals("int -> boolean", false, ClassUtils.isAssignable(Integer.TYPE, Boolean.TYPE));

      // test long conversions
      assertEquals("long -> char", false, ClassUtils.isAssignable(Long.TYPE, Character.TYPE));
      assertEquals("long -> byte", false, ClassUtils.isAssignable(Long.TYPE, Byte.TYPE));
      assertEquals("long -> short", false, ClassUtils.isAssignable(Long.TYPE, Short.TYPE));
      assertEquals("long -> int", false, ClassUtils.isAssignable(Long.TYPE, Integer.TYPE));
      assertEquals("long -> long", true, ClassUtils.isAssignable(Long.TYPE, Long.TYPE));
      assertEquals("long -> float", true, ClassUtils.isAssignable(Long.TYPE, Float.TYPE));
      assertEquals("long -> double", true, ClassUtils.isAssignable(Long.TYPE, Double.TYPE));
      assertEquals("long -> boolean", false, ClassUtils.isAssignable(Long.TYPE, Boolean.TYPE));

      // test float conversions
      assertEquals("float -> char", false, ClassUtils.isAssignable(Float.TYPE, Character.TYPE));
      assertEquals("float -> byte", false, ClassUtils.isAssignable(Float.TYPE, Byte.TYPE));
      assertEquals("float -> short", false, ClassUtils.isAssignable(Float.TYPE, Short.TYPE));
      assertEquals("float -> int", false, ClassUtils.isAssignable(Float.TYPE, Integer.TYPE));
      assertEquals("float -> long", false, ClassUtils.isAssignable(Float.TYPE, Long.TYPE));
      assertEquals("float -> float", true, ClassUtils.isAssignable(Float.TYPE, Float.TYPE));
      assertEquals("float -> double", true, ClassUtils.isAssignable(Float.TYPE, Double.TYPE));
      assertEquals("float -> boolean", false, ClassUtils.isAssignable(Float.TYPE, Boolean.TYPE));

      // test float conversions
      assertEquals("double -> char", false, ClassUtils.isAssignable(Double.TYPE, Character.TYPE));
      assertEquals("double -> byte", false, ClassUtils.isAssignable(Double.TYPE, Byte.TYPE));
      assertEquals("double -> short", false, ClassUtils.isAssignable(Double.TYPE, Short.TYPE));
      assertEquals("double -> int", false, ClassUtils.isAssignable(Double.TYPE, Integer.TYPE));
      assertEquals("double -> long", false, ClassUtils.isAssignable(Double.TYPE, Long.TYPE));
      assertEquals("double -> float", false, ClassUtils.isAssignable(Double.TYPE, Float.TYPE));
      assertEquals("double -> double", true, ClassUtils.isAssignable(Double.TYPE, Double.TYPE));
      assertEquals("double -> boolean", false, ClassUtils.isAssignable(Double.TYPE, Boolean.TYPE));

      // test float conversions
      assertEquals("boolean -> char", false, ClassUtils.isAssignable(Boolean.TYPE, Character.TYPE));
      assertEquals("boolean -> byte", false, ClassUtils.isAssignable(Boolean.TYPE, Byte.TYPE));
      assertEquals("boolean -> short", false, ClassUtils.isAssignable(Boolean.TYPE, Short.TYPE));
      assertEquals("boolean -> int", false, ClassUtils.isAssignable(Boolean.TYPE, Integer.TYPE));
      assertEquals("boolean -> long", false, ClassUtils.isAssignable(Boolean.TYPE, Long.TYPE));
      assertEquals("boolean -> float", false, ClassUtils.isAssignable(Boolean.TYPE, Float.TYPE));
      assertEquals("boolean -> double", false, ClassUtils.isAssignable(Boolean.TYPE, Double.TYPE));
      assertEquals("boolean -> boolean", true, ClassUtils.isAssignable(Boolean.TYPE, Boolean.TYPE));
  }

  @Test
  public void testPrimitiveToWrapper() {

      // test primitive classes
      assertEquals("boolean -> Boolean.class",
          Boolean.class, ClassUtils.primitiveToWrapper(Boolean.TYPE));
      assertEquals("byte -> Byte.class",
          Byte.class, ClassUtils.primitiveToWrapper(Byte.TYPE));
      assertEquals("char -> Character.class",
          Character.class, ClassUtils.primitiveToWrapper(Character.TYPE));
      assertEquals("short -> Short.class",
          Short.class, ClassUtils.primitiveToWrapper(Short.TYPE));
      assertEquals("int -> Integer.class",
          Integer.class, ClassUtils.primitiveToWrapper(Integer.TYPE));
      assertEquals("long -> Long.class",
          Long.class, ClassUtils.primitiveToWrapper(Long.TYPE));
      assertEquals("double -> Double.class",
          Double.class, ClassUtils.primitiveToWrapper(Double.TYPE));
      assertEquals("float -> Float.class",
          Float.class, ClassUtils.primitiveToWrapper(Float.TYPE));

      // test a few other classes
      assertEquals("String.class -> String.class",
          String.class, ClassUtils.primitiveToWrapper(String.class));
      assertEquals("ClassUtils.class -> ClassUtils.class",
          ClassUtils.class,
          ClassUtils.primitiveToWrapper(ClassUtils.class));
      assertEquals("Void.TYPE -> Void.TYPE",
          Void.TYPE, ClassUtils.primitiveToWrapper(Void.TYPE));

      // test null
      assertNull("null -> null",
          ClassUtils.primitiveToWrapper(null));
  }

  @Test
  public void testPrimitivesToWrappers() {
      // test null
      assertNull("null -> null", ClassUtils.primitivesToWrappers(null));
      // test empty array
      assertArrayEquals("empty -> empty",
              ArrayUtils.EMPTY_CLASS_ARRAY, ClassUtils.primitivesToWrappers(ArrayUtils.EMPTY_CLASS_ARRAY));

      // test an array of various classes
      final Class<?>[] primitives = new Class[] {
              Boolean.TYPE, Byte.TYPE, Character.TYPE, Short.TYPE,
              Integer.TYPE, Long.TYPE, Double.TYPE, Float.TYPE,
              String.class, ClassUtils.class
      };
      final Class<?>[] wrappers= ClassUtils.primitivesToWrappers(primitives);

      for (int i=0; i < primitives.length; i++) {
          // test each returned wrapper
          final Class<?> primitive = primitives[i];
          final Class<?> expectedWrapper = ClassUtils.primitiveToWrapper(primitive);

          assertEquals(primitive + " -> " + expectedWrapper, expectedWrapper, wrappers[i]);
      }

      // test an array of no primitive classes
      final Class<?>[] noPrimitives = new Class[] {
              String.class, ClassUtils.class, Void.TYPE
      };
      // This used to return the exact same array, but no longer does.
      assertNotSame("unmodified", noPrimitives, ClassUtils.primitivesToWrappers(noPrimitives));
  }

  @Test
  public void testWrapperToPrimitive() {
      // an array with classes to convert
      final Class<?>[] primitives = {
              Boolean.TYPE, Byte.TYPE, Character.TYPE, Short.TYPE,
              Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE
      };
      for (final Class<?> primitive : primitives) {
          final Class<?> wrapperCls = ClassUtils.primitiveToWrapper(primitive);
          assertFalse("Still primitive", wrapperCls.isPrimitive());
          assertEquals(wrapperCls + " -> " + primitive, primitive,
                  ClassUtils.wrapperToPrimitive(wrapperCls));
      }
  }

  @Test
  public void testWrapperToPrimitiveNoWrapper() {
      assertNull("Wrong result for non wrapper class", ClassUtils.wrapperToPrimitive(String.class));
  }

  @Test
  public void testWrapperToPrimitiveNull() {
      assertNull("Wrong result for null class", ClassUtils.wrapperToPrimitive(null));
  }

  @Test
  public void testWrappersToPrimitives() {
      // an array with classes to test
      final Class<?>[] classes = {
              Boolean.class, Byte.class, Character.class, Short.class,
              Integer.class, Long.class, Float.class, Double.class,
              String.class, ClassUtils.class, null
      };

      final Class<?>[] primitives = ClassUtils.wrappersToPrimitives(classes);
      // now test the result
      assertEquals("Wrong length of result array", classes.length, primitives.length);
      for (int i = 0; i < classes.length; i++) {
          final Class<?> expectedPrimitive = ClassUtils.wrapperToPrimitive(classes[i]);
          assertEquals(classes[i] + " -> " + expectedPrimitive, expectedPrimitive,
                  primitives[i]);
      }
  }

  @Test
  public void testWrappersToPrimitivesNull() {
      assertNull("Wrong result for null input", ClassUtils.wrappersToPrimitives(null));
  }

  @Test
  public void testWrappersToPrimitivesEmpty() {
      final Class<?>[] empty = new Class[0];
      assertArrayEquals("Wrong result for empty input", empty, ClassUtils.wrappersToPrimitives(empty));
  }

  @Test
  public void testGetClassClassNotFound() throws Exception {
      assertGetClassThrowsClassNotFound( "bool" );
      assertGetClassThrowsClassNotFound( "bool[]" );
      assertGetClassThrowsClassNotFound( "integer[]" );
  }

  @Test
  public void testGetClassInvalidArguments() throws Exception {
      assertGetClassThrowsNullPointerException( null );
      assertGetClassThrowsClassNotFound( "[][][]" );
      assertGetClassThrowsClassNotFound( "[[]" );
      assertGetClassThrowsClassNotFound( "[" );
      assertGetClassThrowsClassNotFound( "java.lang.String][" );
      assertGetClassThrowsClassNotFound( ".hello.world" );
      assertGetClassThrowsClassNotFound( "hello..world" );
  }

  @Test
  public void testWithInterleavingWhitespace() throws ClassNotFoundException {
      assertEquals( int[].class, ClassUtils.getClass( " int [ ] " ) );
      assertEquals( long[].class, ClassUtils.getClass( "\rlong\t[\n]\r" ) );
      assertEquals( short[].class, ClassUtils.getClass( "\tshort                \t\t[]" ) );
      assertEquals( byte[].class, ClassUtils.getClass( "byte[\t\t\n\r]   " ) );
  }

  @Test
  public void testGetClassByNormalNameArrays() throws ClassNotFoundException {
      assertEquals( int[].class, ClassUtils.getClass( "int[]" ) );
      assertEquals( long[].class, ClassUtils.getClass( "long[]" ) );
      assertEquals( short[].class, ClassUtils.getClass( "short[]" ) );
      assertEquals( byte[].class, ClassUtils.getClass( "byte[]" ) );
      assertEquals( char[].class, ClassUtils.getClass( "char[]" ) );
      assertEquals( float[].class, ClassUtils.getClass( "float[]" ) );
      assertEquals( double[].class, ClassUtils.getClass( "double[]" ) );
      assertEquals( boolean[].class, ClassUtils.getClass( "boolean[]" ) );
      assertEquals( String[].class, ClassUtils.getClass( "java.lang.String[]" ) );
  }

  @Test
  public void testGetClassByNormalNameArrays2D() throws ClassNotFoundException {
      assertEquals( int[][].class, ClassUtils.getClass( "int[][]" ) );
      assertEquals( long[][].class, ClassUtils.getClass( "long[][]" ) );
      assertEquals( short[][].class, ClassUtils.getClass( "short[][]" ) );
      assertEquals( byte[][].class, ClassUtils.getClass( "byte[][]" ) );
      assertEquals( char[][].class, ClassUtils.getClass( "char[][]" ) );
      assertEquals( float[][].class, ClassUtils.getClass( "float[][]" ) );
      assertEquals( double[][].class, ClassUtils.getClass( "double[][]" ) );
      assertEquals( boolean[][].class, ClassUtils.getClass( "boolean[][]" ) );
      assertEquals( String[][].class, ClassUtils.getClass( "java.lang.String[][]" ) );
  }

  @Test
  public void testGetClassWithArrayClassUtils2D() throws Exception {
      assertGetClassReturnsClass( String[][].class );
      assertGetClassReturnsClass( int[][].class );
      assertGetClassReturnsClass( long[][].class );
      assertGetClassReturnsClass( short[][].class );
      assertGetClassReturnsClass( byte[][].class );
      assertGetClassReturnsClass( char[][].class );
      assertGetClassReturnsClass( float[][].class );
      assertGetClassReturnsClass( double[][].class );
      assertGetClassReturnsClass( boolean[][].class );
  }

  @Test
  public void testGetClassWithArrayClassUtils() throws Exception {
      assertGetClassReturnsClass( String[].class );
      assertGetClassReturnsClass( int[].class );
      assertGetClassReturnsClass( long[].class );
      assertGetClassReturnsClass( short[].class );
      assertGetClassReturnsClass( byte[].class );
      assertGetClassReturnsClass( char[].class );
      assertGetClassReturnsClass( float[].class );
      assertGetClassReturnsClass( double[].class );
      assertGetClassReturnsClass( boolean[].class );
  }

  @Test
  public void testGetClassRawPrimitives() throws ClassNotFoundException {
      assertEquals( int.class, ClassUtils.getClass( "int" ) );
      assertEquals( long.class, ClassUtils.getClass( "long" ) );
      assertEquals( short.class, ClassUtils.getClass( "short" ) );
      assertEquals( byte.class, ClassUtils.getClass( "byte" ) );
      assertEquals( char.class, ClassUtils.getClass( "char" ) );
      assertEquals( float.class, ClassUtils.getClass( "float" ) );
      assertEquals( double.class, ClassUtils.getClass( "double" ) );
      assertEquals( boolean.class, ClassUtils.getClass( "boolean" ) );
  }

  private void assertGetClassReturnsClass(final Class<?> c ) throws Exception {
      assertEquals( c, ClassUtils.getClass( c.getName() ) );
  }

  private void assertGetClassThrowsException( final String className, final Class<?> exceptionType ) throws Exception {
      try {
          ClassUtils.getClass( className );
          fail( "ClassUtils.getClass() should fail with an exception of type " + exceptionType.getName() + " when given class name \"" + className + "\"." );
      }
      catch( final Exception e ) {
          assertTrue( exceptionType.isAssignableFrom( e.getClass() ) );
      }
  }

  private void assertGetClassThrowsNullPointerException( final String className ) throws Exception {
    assertGetClassThrowsException( className, NullPointerException.class );
  }

  private void assertGetClassThrowsClassNotFound( final String className ) throws Exception {
      assertGetClassThrowsException( className, ClassNotFoundException.class );
  }

  // Show the Java bug: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4071957
  // We may have to delete this if a JDK fixes the bug.
  @Test
  public void testShowJavaBug() throws Exception {
      // Tests with Collections$UnmodifiableSet
      final Set<String> set = Collections.unmodifiableSet(new HashSet<String>());
      final Method isEmptyMethod = set.getClass().getMethod("isEmpty",  new Class[0]);
      try {
          isEmptyMethod.invoke(set, new Object[0]);
          fail("Failed to throw IllegalAccessException as expected");
      } catch(final IllegalAccessException iae) {
          // expected
      }
  }

  @Test
  public void testGetPublicMethod() throws Exception {
      // Tests with Collections$UnmodifiableSet
      final Set<String> set = Collections.unmodifiableSet(new HashSet<String>());
      final Method isEmptyMethod = ClassUtils.getPublicMethod(set.getClass(), "isEmpty",  new Class[0]);
          assertTrue(Modifier.isPublic(isEmptyMethod.getDeclaringClass().getModifiers()));

      try {
          isEmptyMethod.invoke(set, new Object[0]);
      } catch(final java.lang.IllegalAccessException iae) {
          fail("Should not have thrown IllegalAccessException");
      }

      // Tests with a public Class
      final Method toStringMethod = ClassUtils.getPublicMethod(Object.class, "toString",  new Class[0]);
          assertEquals(Object.class.getMethod("toString", new Class[0]), toStringMethod);
  }

  @Test
  public void testToClass_object() {
      assertArrayEquals(null, ClassUtils.toClass(null));

      assertSame(ArrayUtils.EMPTY_CLASS_ARRAY, ClassUtils.toClass(new Class[0]));

      final Object[] array = new Object[3];
      array[0] = new String("Test");
      array[1] = new Integer(1);
      array[2] = new Double(99);

      final Class<?>[] results = ClassUtils.toClass(array);
      assertEquals("String", ClassUtils.getShortClassName(results[0]));
      assertEquals("Integer", ClassUtils.getShortClassName(results[1]));
      assertEquals("Double", ClassUtils.getShortClassName(results[2]));
  }

  @Test
  public void test_getShortCanonicalName_Object() {
      assertEquals("<null>", ClassUtils.getShortCanonicalName(null, "<null>"));
      assertEquals("ClassUtils", ClassUtils.getShortCanonicalName(ClassUtils.class));
      assertEquals("ClassUtils[]", ClassUtils.getShortCanonicalName(new ClassUtils[0], "<null>"));
      assertEquals("ClassUtils[][]", ClassUtils.getShortCanonicalName(new ClassUtils[0][0], "<null>"));
      assertEquals("int[]", ClassUtils.getShortCanonicalName(new int[0], "<null>"));
      assertEquals("int[][]", ClassUtils.getShortCanonicalName(new int[0][0], "<null>"));
  }

  @Test
  public void test_getShortCanonicalName_Class() {
      assertEquals("ClassUtils", ClassUtils.getShortCanonicalName(ClassUtils.class));
      assertEquals("ClassUtils[]", ClassUtils.getShortCanonicalName(ClassUtils[].class));
      assertEquals("ClassUtils[][]", ClassUtils.getShortCanonicalName(ClassUtils[][].class));
      assertEquals("int[]", ClassUtils.getShortCanonicalName(int[].class));
      assertEquals("int[][]", ClassUtils.getShortCanonicalName(int[][].class));
  }

  @Test
  public void test_getShortCanonicalName_String() {
      assertEquals("ClassUtils", ClassUtils.getShortCanonicalName("com.github.haixing_hu.lang.ClassUtils"));
      assertEquals("ClassUtils[]", ClassUtils.getShortCanonicalName("[Lcom.github.haixing_hu.lang.ClassUtils;"));
      assertEquals("ClassUtils[][]", ClassUtils.getShortCanonicalName("[[Lcom.github.haixing_hu.lang.ClassUtils;"));
      assertEquals("ClassUtils[]", ClassUtils.getShortCanonicalName("com.github.haixing_hu.lang.ClassUtils[]"));
      assertEquals("ClassUtils[][]", ClassUtils.getShortCanonicalName("com.github.haixing_hu.lang.ClassUtils[][]"));
      assertEquals("int[]", ClassUtils.getShortCanonicalName("[I"));
      assertEquals("int[][]", ClassUtils.getShortCanonicalName("[[I"));
      assertEquals("int[]", ClassUtils.getShortCanonicalName("int[]"));
      assertEquals("int[][]", ClassUtils.getShortCanonicalName("int[][]"));
  }

  @Test
  public void test_getPackageCanonicalName_Object() {
      assertEquals("<null>", ClassUtils.getPackageCanonicalName(null, "<null>"));
      assertEquals("com.github.haixing_hu.lang", ClassUtils.getPackageCanonicalName(ClassUtils.class));
      assertEquals("com.github.haixing_hu.lang", ClassUtils.getPackageCanonicalName(new ClassUtils[0], "<null>"));
      assertEquals("com.github.haixing_hu.lang", ClassUtils.getPackageCanonicalName(new ClassUtils[0][0], "<null>"));
      assertEquals("", ClassUtils.getPackageCanonicalName(new int[0], "<null>"));
      assertEquals("", ClassUtils.getPackageCanonicalName(new int[0][0], "<null>"));
  }

  @Test
  public void test_getPackageCanonicalName_Class() {
      assertEquals("com.github.haixing_hu.lang", ClassUtils.getPackageCanonicalName(ClassUtils.class));
      assertEquals("com.github.haixing_hu.lang", ClassUtils.getPackageCanonicalName(ClassUtils[].class));
      assertEquals("com.github.haixing_hu.lang", ClassUtils.getPackageCanonicalName(ClassUtils[][].class));
      assertEquals("", ClassUtils.getPackageCanonicalName(int[].class));
      assertEquals("", ClassUtils.getPackageCanonicalName(int[][].class));
  }

  @Test
  public void test_getPackageCanonicalName_String() {
      assertEquals("com.github.haixing_hu.lang",
          ClassUtils.getPackageCanonicalName("com.github.haixing_hu.lang.ClassUtils"));
      assertEquals("com.github.haixing_hu.lang",
          ClassUtils.getPackageCanonicalName("[Lcom.github.haixing_hu.lang.ClassUtils;"));
      assertEquals("com.github.haixing_hu.lang",
          ClassUtils.getPackageCanonicalName("[[Lcom.github.haixing_hu.lang.ClassUtils;"));
      assertEquals("com.github.haixing_hu.lang",
          ClassUtils.getPackageCanonicalName("com.github.haixing_hu.lang.ClassUtils[]"));
      assertEquals("com.github.haixing_hu.lang",
          ClassUtils.getPackageCanonicalName("com.github.haixing_hu.lang.ClassUtils[][]"));
      assertEquals("", ClassUtils.getPackageCanonicalName("[I"));
      assertEquals("", ClassUtils.getPackageCanonicalName("[[I"));
      assertEquals("", ClassUtils.getPackageCanonicalName("int[]"));
      assertEquals("", ClassUtils.getPackageCanonicalName("int[][]"));
  }

}
