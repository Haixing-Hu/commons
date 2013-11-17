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

import java.util.Iterator;

import org.junit.Test;

import com.github.haixing_hu.lang.ArrayUtils;
import com.github.haixing_hu.lang.StringUtils;

import static org.junit.Assert.*;


/**
 * The unit test of the join() functions of Strings class.
 *
 * @author Haixing Hu
 */
public class StringUtilsJoinTest {

  @Test
  public void testJoin_CharObjectArray() {
    Object[] objArray = null;
    String[] strArray = null;

    assertNull(StringUtils.join(',', objArray));

    strArray = new String[]{};
    assertEquals("", StringUtils.join(';', strArray));

    strArray = new String[]{ "foo", "bar", "baz" };
    assertEquals("foo;bar;baz", StringUtils.join(';', strArray));
    assertEquals("foo;bar", StringUtils.join(';', strArray, 0, 2));
    assertEquals("baz", StringUtils.join(';', strArray, 2, 3));
    assertEquals("foo;bar;baz", StringUtils.join(';', strArray, -1, 1000));
    assertEquals("", StringUtils.join(';', strArray, 4, 1000));

    strArray = new String[]{null, "", "foo"};
    assertEquals(";;foo", StringUtils.join(';', strArray));
    assertEquals("", StringUtils.join(';', strArray, 0, 0));
    assertEquals("", StringUtils.join(';', strArray, 0, 1));
    assertEquals(";", StringUtils.join(';', strArray, 0, 2));

    objArray = new Object[]{new String("foo"), null, new Long(2)};
    assertEquals("foo;;2", StringUtils.join(';', objArray));
    assertEquals("foo", StringUtils.join(';', objArray, 0, 1));
    assertEquals("", StringUtils.join(';', objArray, 1, 2));
    assertEquals(";2", StringUtils.join(';', objArray, 1, 3));
    assertEquals("", StringUtils.join(';', objArray, 3, 2));
  }

  @Test
  public void testJoin_StringObjectArray() {
    Object[] objArray = null;
    String[] strArray = null;

    assertNull(StringUtils.join(",", objArray));

    strArray = new String[]{};
    assertEquals("", StringUtils.join(";", strArray));

    strArray = new String[]{ "foo", "bar", "baz" };
    assertEquals("foo--bar--baz", StringUtils.join("--", strArray));
    assertEquals("foo--bar", StringUtils.join("--", strArray, 0, 2));
    assertEquals("baz", StringUtils.join("--", strArray, 2, 3));
    assertEquals("foo--bar--baz", StringUtils.join("--", strArray, -1, 1000));
    assertEquals("", StringUtils.join("--", strArray, 4, 1000));

    assertEquals("foobarbaz", StringUtils.join("", strArray));
    assertEquals("foobar", StringUtils.join("", strArray, 0, 2));
    assertEquals("baz", StringUtils.join("", strArray, 2, 3));
    assertEquals("foobarbaz", StringUtils.join("", strArray, -1, 1000));
    assertEquals("", StringUtils.join("", strArray, 4, 1000));

    assertEquals("foobarbaz", StringUtils.join(null, strArray));
    assertEquals("foobar", StringUtils.join(null, strArray, 0, 2));
    assertEquals("baz", StringUtils.join(null, strArray, 2, 3));
    assertEquals("foobarbaz", StringUtils.join(null, strArray, -1, 1000));
    assertEquals("", StringUtils.join(null, strArray, 4, 1000));

    strArray = new String[]{null, "", "foo"};
    assertEquals("----foo", StringUtils.join("--", strArray));
    assertEquals("", StringUtils.join("--", strArray, 0, 0));
    assertEquals("", StringUtils.join("--", strArray, 0, 1));
    assertEquals("--", StringUtils.join("--", strArray, 0, 2));

    assertEquals("foo", StringUtils.join("", strArray));
    assertEquals("", StringUtils.join("", strArray, 0, 0));
    assertEquals("", StringUtils.join("", strArray, 0, 1));
    assertEquals("", StringUtils.join("", strArray, 0, 2));

    assertEquals("foo", StringUtils.join(null, strArray));
    assertEquals("", StringUtils.join(null, strArray, 0, 0));
    assertEquals("", StringUtils.join(null, strArray, 0, 1));
    assertEquals("", StringUtils.join(null, strArray, 0, 2));

    objArray = new Object[]{new String("foo"), null, new Long(2)};
    assertEquals("foo;;2", StringUtils.join(';', objArray));
    assertEquals("foo", StringUtils.join(';', objArray, 0, 1));
    assertEquals("", StringUtils.join(';', objArray, 1, 2));
    assertEquals(";2", StringUtils.join(';', objArray, 1, 3));
    assertEquals("", StringUtils.join(';', objArray, 3, 2));

    assertEquals("foo----2", StringUtils.join("--", objArray));
    assertEquals("foo", StringUtils.join("--", objArray, 0, 1));
    assertEquals("", StringUtils.join("--", objArray, 1, 2));
    assertEquals("--2", StringUtils.join("--", objArray, 1, 3));
    assertEquals("", StringUtils.join("--", objArray, 3, 2));

    assertEquals("foo2", StringUtils.join("", objArray));
    assertEquals("foo", StringUtils.join("", objArray, 0, 1));
    assertEquals("", StringUtils.join("", objArray, 1, 2));
    assertEquals("2", StringUtils.join("", objArray, 1, 3));
    assertEquals("", StringUtils.join("", objArray, 3, 2));

  }

  @Test
  public void testJoin_CharIterator() {

    Object[] objArray = null;
    String[] strArray = null;

    assertNull(StringUtils.join(',', (Iterator<String>) null));

    strArray = new String[]{};
    assertEquals("", StringUtils.join(';', ArrayUtils.asList(strArray).iterator()));

    strArray = new String[]{ "foo", "bar", "baz" };
    assertEquals("foo;bar;baz", StringUtils.join(';', ArrayUtils.asList(strArray).iterator()));

    strArray = new String[]{null, "", "foo"};
    assertEquals(";;foo", StringUtils.join(';', ArrayUtils.asList(strArray).iterator()));

    objArray = new Object[]{new String("foo"), null, new Long(2)};
    assertEquals("foo;;2", StringUtils.join(';', ArrayUtils.asList(objArray).iterator()));
  }

  @Test
  public void testJoin_StringIterator() {
    Object[] objArray = null;
    String[] strArray = null;

    assertNull(StringUtils.join(",", (Iterator<String>)null));

    strArray = new String[]{};
    assertEquals("", StringUtils.join(";", ArrayUtils.asList(strArray).iterator()));

    strArray = new String[]{ "foo", "bar", "baz" };
    assertEquals("foo--bar--baz", StringUtils.join("--", ArrayUtils.asList(strArray).iterator()));

    assertEquals("foobarbaz", StringUtils.join("", ArrayUtils.asList(strArray).iterator()));

    assertEquals("foobarbaz", StringUtils.join(null, ArrayUtils.asList(strArray).iterator()));

    strArray = new String[]{null, "", "foo"};
    assertEquals("----foo", StringUtils.join("--", ArrayUtils.asList(strArray).iterator()));
    assertEquals("foo", StringUtils.join("", ArrayUtils.asList(strArray).iterator()));
    assertEquals("foo", StringUtils.join(null, ArrayUtils.asList(strArray).iterator()));

    objArray = new Object[]{new String("foo"), null, new Long(2)};
    assertEquals("foo;;2", StringUtils.join(';', ArrayUtils.asList(objArray).iterator()));
    assertEquals("foo----2", StringUtils.join("--", ArrayUtils.asList(objArray).iterator()));
    assertEquals("foo2", StringUtils.join("", ArrayUtils.asList(objArray).iterator()));
  }

  @Test
  public void testJoin_CharIterable() {

    Object[] objArray = null;
    String[] strArray = null;

    assertNull(StringUtils.join(',', (Iterable<String>) null));

    strArray = new String[]{};
    assertEquals("", StringUtils.join(';', ArrayUtils.asList(strArray)));

    strArray = new String[]{ "foo", "bar", "baz" };
    assertEquals("foo;bar;baz", StringUtils.join(';', ArrayUtils.asList(strArray)));

    strArray = new String[]{null, "", "foo"};
    assertEquals(";;foo", StringUtils.join(';', ArrayUtils.asList(strArray)));

    objArray = new Object[]{new String("foo"), null, new Long(2)};
    assertEquals("foo;;2", StringUtils.join(';', ArrayUtils.asList(objArray)));
  }

  @Test
  public void testJoin_StringIterable() {
    Object[] objArray = null;
    String[] strArray = null;

    assertNull(StringUtils.join(",", (Iterable<String>)null));

    strArray = new String[]{};
    assertEquals("", StringUtils.join(";", ArrayUtils.asList(strArray)));

    strArray = new String[]{ "foo", "bar", "baz" };
    assertEquals("foo--bar--baz", StringUtils.join("--", ArrayUtils.asList(strArray)));

    assertEquals("foobarbaz", StringUtils.join("", ArrayUtils.asList(strArray)));

    assertEquals("foobarbaz", StringUtils.join(null, ArrayUtils.asList(strArray)));

    strArray = new String[]{null, "", "foo"};
    assertEquals("----foo", StringUtils.join("--", ArrayUtils.asList(strArray)));
    assertEquals("foo", StringUtils.join("", ArrayUtils.asList(strArray)));
    assertEquals("foo", StringUtils.join(null, ArrayUtils.asList(strArray)));

    objArray = new Object[]{new String("foo"), null, new Long(2)};
    assertEquals("foo;;2", StringUtils.join(';', ArrayUtils.asList(objArray)));
    assertEquals("foo----2", StringUtils.join("--", ArrayUtils.asList(objArray)));
    assertEquals("foo2", StringUtils.join("", ArrayUtils.asList(objArray)));
  }
  
  @Test
  public void testJoin_char_boolean() {
    boolean[] values = null;
    assertEquals(null, StringUtils.join(';', values));
    
    values = new boolean[]{};
    assertEquals("", StringUtils.join(';', values));
    assertEquals("", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("", StringUtils.join(';', values, -1, 20));
    
    values = new boolean[]{true};
    assertEquals("true", StringUtils.join(';', values));
    assertEquals("true", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("true", StringUtils.join(';', values, -1, 20));
    
    values = new boolean[]{true, false, true};
    assertEquals("true;false;true", StringUtils.join(';', values));
    assertEquals("true;false;true", StringUtils.join(';', values, 0, values.length));    
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("true;false;true", StringUtils.join(';', values, -1, 20));
  }
 
  @Test
  public void testJoin_char_char() {
    char[] values = null;
    assertEquals(null, StringUtils.join(';', values));
    
    values = new char[]{};
    assertEquals("", StringUtils.join(';', values));
    assertEquals("", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("", StringUtils.join(';', values, -1, 20));
 
    values = new char[]{'a'};
    assertEquals("a", StringUtils.join(';', values));
    assertEquals("a", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("a", StringUtils.join(';', values, -1, 20));
    
    values = new char[]{'a', 'b', 'c'};
    assertEquals("a;b;c", StringUtils.join(';', values));
    assertEquals("a;b;c", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("a;b;c", StringUtils.join(';', values, -1, 20));
  }
  
  @Test
  public void testJoin_char_byte() {
    byte[] values = null;
    assertEquals(null, StringUtils.join(';', values));
    
    values = new byte[]{};
    assertEquals("", StringUtils.join(';', values));
    assertEquals("", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("", StringUtils.join(';', values, -1, 20));
    
    values = new byte[]{(byte)2};
    assertEquals("2", StringUtils.join(';', values));
    assertEquals("2", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("2", StringUtils.join(';', values, -1, 20));
    
    values = new byte[]{(byte)20, (byte)10, (byte)2};
    assertEquals("20;10;2", StringUtils.join(';', values));
    assertEquals("20;10;2", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("20;10;2", StringUtils.join(';', values, -1, 20));
  }
  
  @Test
  public void testJoin_char_double() {
    double[] values = null;
    assertEquals(null, StringUtils.join(';', values));
    
    values = new double[]{};
    assertEquals("", StringUtils.join(';', values));
    assertEquals("", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("", StringUtils.join(';', values, -1, 20));
    
    values = new double[]{(double)2};
    assertEquals("2.0", StringUtils.join(';', values));
    assertEquals("2.0", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("2.0", StringUtils.join(';', values, -1, 20));
    
    values = new double[]{(double)20, (double)10, (double)2};
    assertEquals("20.0;10.0;2.0", StringUtils.join(';', values));
    assertEquals("20.0;10.0;2.0", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("20.0;10.0;2.0", StringUtils.join(';', values, -1, 20));
  }
  
  @Test
  public void testJoin_char_float() {
    float[] values = null;
    assertEquals(null, StringUtils.join(';', values));
    
    values = new float[]{};
    assertEquals("", StringUtils.join(';', values));
    assertEquals("", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("", StringUtils.join(';', values, -1, 20));
    
    values = new float[]{(float)2};
    assertEquals("2.0", StringUtils.join(';', values));
    assertEquals("2.0", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("2.0", StringUtils.join(';', values, -1, 20));
    
    values = new float[]{(float)20, (float)10, (float)2};
    assertEquals("20.0;10.0;2.0", StringUtils.join(';', values));
    assertEquals("20.0;10.0;2.0", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("20.0;10.0;2.0", StringUtils.join(';', values, -1, 20));
  }
  
  @Test
  public void testJoin_char_int() {
    int[] values = null;
    assertEquals(null, StringUtils.join(';', values));
    
    values = new int[]{};
    assertEquals("", StringUtils.join(';', values));
    assertEquals("", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("", StringUtils.join(';', values, -1, 20));
    
    values = new int[]{2};
    assertEquals("2", StringUtils.join(';', values));
    assertEquals("2", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("2", StringUtils.join(';', values, -1, 20));
    
    values = new int[]{20, 10, 2};
    assertEquals("20;10;2", StringUtils.join(';', values));
    assertEquals("20;10;2", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("20;10;2", StringUtils.join(';', values, -1, 20));
  }
  
  @Test
  public void testJoin_char_long() {
    long[] values = null;
    assertEquals(null, StringUtils.join(';', values));
    
    values = new long[]{};
    assertEquals("", StringUtils.join(';', values));
    assertEquals("", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("", StringUtils.join(';', values, -1, 20));
    
    values = new long[]{(long)2};
    assertEquals("2", StringUtils.join(';', values));
    assertEquals("2", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("2", StringUtils.join(';', values, -1, 20));
    
    values = new long[]{(long)20, (long)10, (long)2};
    assertEquals("20;10;2", StringUtils.join(';', values));
    assertEquals("20;10;2", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("20;10;2", StringUtils.join(';', values, -1, 20));
  }
  
  @Test
  public void testJoin_char_short() {
    short[] values = null;
    assertEquals(null, StringUtils.join(';', values));
    
    values = new short[]{};
    assertEquals("", StringUtils.join(';', values));
    assertEquals("", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("", StringUtils.join(';', values, -1, 20));
    
    values = new short[]{(short)2};
    assertEquals("2", StringUtils.join(';', values));
    assertEquals("2", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("2", StringUtils.join(';', values, -1, 20));
    
    values = new short[]{(short)20, (short)10, (short)2};
    assertEquals("20;10;2", StringUtils.join(';', values));
    assertEquals("20;10;2", StringUtils.join(';', values, 0, values.length));
    assertEquals("", StringUtils.join(';', values, -1, -1));
    assertEquals("20;10;2", StringUtils.join(';', values, -1, 20));
  }
  
  @Test
  public void testJoin_String_boolean() {
    boolean[] values = null;
    assertEquals(null, StringUtils.join(";", values));
    
    values = new boolean[]{};
    assertEquals("", StringUtils.join(";", values));
    assertEquals("", StringUtils.join(";", values, 0, values.length));
    
    values = new boolean[]{true};
    assertEquals("true", StringUtils.join(";", values));
    assertEquals("true", StringUtils.join(";", values, 0, values.length));
    
    values = new boolean[]{true, false, true};
    assertEquals("true;false;true", StringUtils.join(";", values));
    assertEquals("true;false;true", StringUtils.join(";", values, 0, values.length));  
  }
  
  @Test
  public void testJoin_string_char() {
    char[] values = null;
    assertEquals(null, StringUtils.join(';', values));
    
    values = new char[]{};
    assertEquals("", StringUtils.join(";", values));
    assertEquals("", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("", StringUtils.join(";", values, -1, 20));
 
    values = new char[]{'a'};
    assertEquals("a", StringUtils.join(";", values));
    assertEquals("a", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("a", StringUtils.join(";", values, -1, 20));
    
    values = new char[]{'a', 'b', 'c'};
    assertEquals("a;b;c", StringUtils.join(";", values));
    assertEquals("a;b;c", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("a;b;c", StringUtils.join(";", values, -1, 20));
  }
  
  @Test
  public void testJoin_string_byte() {
    byte[] values = null;
    assertEquals(null, StringUtils.join(";", values));
    
    values = new byte[]{};
    assertEquals("", StringUtils.join(";", values));
    assertEquals("", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("", StringUtils.join(";", values, -1, 20));
    
    values = new byte[]{(byte)2};
    assertEquals("2", StringUtils.join(";", values));
    assertEquals("2", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("2", StringUtils.join(";", values, -1, 20));
    
    values = new byte[]{(byte)20, (byte)10, (byte)2};
    assertEquals("20;10;2", StringUtils.join(";", values));
    assertEquals("20;10;2", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("20;10;2", StringUtils.join(";", values, -1, 20));
  }
  
  @Test
  public void testJoin_string_double() {
    double[] values = null;
    assertEquals(null, StringUtils.join(";", values));
    
    values = new double[]{};
    assertEquals("", StringUtils.join(";", values));
    assertEquals("", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("", StringUtils.join(";", values, -1, 20));
    
    values = new double[]{(double)2};
    assertEquals("2.0", StringUtils.join(";", values));
    assertEquals("2.0", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("2.0", StringUtils.join(";", values, -1, 20));
    
    values = new double[]{(double)20, (double)10, (double)2};
    assertEquals("20.0;10.0;2.0", StringUtils.join(";", values));
    assertEquals("20.0;10.0;2.0", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("20.0;10.0;2.0", StringUtils.join(";", values, -1, 20));
  }
  
  @Test
  public void testJoin_string_float() {
    float[] values = null;
    assertEquals(null, StringUtils.join(";", values));
    
    values = new float[]{};
    assertEquals("", StringUtils.join(";", values));
    assertEquals("", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("", StringUtils.join(";", values, -1, 20));
    
    values = new float[]{(float)2};
    assertEquals("2.0", StringUtils.join(";", values));
    assertEquals("2.0", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("2.0", StringUtils.join(";", values, -1, 20));
    
    values = new float[]{(float)20, (float)10, (float)2};
    assertEquals("20.0;10.0;2.0", StringUtils.join(";", values));
    assertEquals("20.0;10.0;2.0", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("20.0;10.0;2.0", StringUtils.join(";", values, -1, 20));
  }
  
  @Test
  public void testJoin_string_int() {
    int[] values = null;
    assertEquals(null, StringUtils.join(";", values));
    
    values = new int[]{};
    assertEquals("", StringUtils.join(";", values));
    assertEquals("", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("", StringUtils.join(";", values, -1, 20));
    
    values = new int[]{2};
    assertEquals("2", StringUtils.join(";", values));
    assertEquals("2", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("2", StringUtils.join(";", values, -1, 20));
    
    values = new int[]{20, 10, 2};
    assertEquals("20;10;2", StringUtils.join(";", values));
    assertEquals("20;10;2", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("20;10;2", StringUtils.join(";", values, -1, 20));
  }
  
  @Test
  public void testJoin_string_long() {
    long[] values = null;
    assertEquals(null, StringUtils.join(";", values));
    
    values = new long[]{};
    assertEquals("", StringUtils.join(";", values));
    assertEquals("", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("", StringUtils.join(";", values, -1, 20));
    
    values = new long[]{(long)2};
    assertEquals("2", StringUtils.join(";", values));
    assertEquals("2", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("2", StringUtils.join(";", values, -1, 20));
    
    values = new long[]{(long)20, (long)10, (long)2};
    assertEquals("20;10;2", StringUtils.join(";", values));
    assertEquals("20;10;2", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("20;10;2", StringUtils.join(";", values, -1, 20));
  }
  
  @Test
  public void testJoin_string_short() {
    short[] values = null;
    assertEquals(null, StringUtils.join(";", values));
    
    values = new short[]{};
    assertEquals("", StringUtils.join(";", values));
    assertEquals("", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("", StringUtils.join(";", values, -1, 20));
    
    values = new short[]{(short)2};
    assertEquals("2", StringUtils.join(";", values));
    assertEquals("2", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("2", StringUtils.join(";", values, -1, 20));
    
    values = new short[]{(short)20, (short)10, (short)2};
    assertEquals("20;10;2", StringUtils.join(";", values));
    assertEquals("20;10;2", StringUtils.join(";", values, 0, values.length));
    assertEquals("", StringUtils.join(";", values, -1, -1));
    assertEquals("20;10;2", StringUtils.join(";", values, -1, 20));
  }  
}
