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

import org.junit.Test;

import com.github.haixing_hu.lang.ObjectUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Unit test or the Objects class.
 *
 * @author Haixing Hu
 */
public class ObjectUtilsTest {
  private static final String FOO = "foo";
  private static final String BAR = "bar";

  @Test
  public void testIsNull() {
    Object o = FOO;
    Object dflt = BAR;
    assertSame("dflt was not returned when o was null", dflt, ObjectUtils.defaultIfNull(null, dflt));
    assertSame("dflt was returned when o was not null", o, ObjectUtils.defaultIfNull(o, dflt));
  }

  @Test
  public void testHashCode() {
    assertEquals(0, ObjectUtils.hashCode(null));
    assertEquals("a".hashCode(), ObjectUtils.hashCode("a"));
  }

///**
// * Show that java.util.Date and java.sql.Timestamp are apples and oranges.
// * Prompted by an email discussion.
// *
// * The behavior is different b/w Sun Java 1.3.1_10 and 1.4.2_03.
// */
//public void testDateEqualsJava() {
//    long now = 1076957313284L; // Feb 16, 2004 10:49... PST
//    java.util.Date date = new java.util.Date(now);
//    java.sql.Timestamp realTimestamp = new java.sql.Timestamp(now);
//    java.util.Date timestamp = realTimestamp;
//    // sanity check 1:
//    assertEquals(284000000, realTimestamp.getNanos());
//    assertEquals(1076957313284L, date.getTime());
//    //
//    // On Sun 1.3.1_10:
//    //junit.framework.AssertionFailedError: expected:<1076957313284> but was:<1076957313000>
//    //
//    //assertEquals(1076957313284L, timestamp.getTime());
//    //
//    //junit.framework.AssertionFailedError: expected:<1076957313284> but was:<1076957313000>
//    //
//    //assertEquals(1076957313284L, realTimestamp.getTime());
//    // sanity check 2:
//    assertEquals(date.getDay(), realTimestamp.getDay());
//    assertEquals(date.getHours(), realTimestamp.getHours());
//    assertEquals(date.getMinutes(), realTimestamp.getMinutes());
//    assertEquals(date.getMonth(), realTimestamp.getMonth());
//    assertEquals(date.getSeconds(), realTimestamp.getSeconds());
//    assertEquals(date.getTimezoneOffset(), realTimestamp.getTimezoneOffset());
//    assertEquals(date.getYear(), realTimestamp.getYear());
//    //
//    // Time values are == and equals() on Sun 1.4.2_03 but NOT on Sun 1.3.1_10:
//    //
//    //assertFalse("Sanity check failed: date.getTime() == timestamp.getTime()", date.getTime() == timestamp.getTime());
//    //assertFalse("Sanity check failed: timestamp.equals(date)", timestamp.equals(date));
//    //assertFalse("Sanity check failed: date.equals(timestamp)", date.equals(timestamp));
//    // real test:
//    //assertFalse("java.util.Date and java.sql.Timestamp should be equal", Objects.equals(date, timestamp));
//}

  @Test
  public void testIdentityToString() {
    assertEquals(null, ObjectUtils.identityToString(null));
    assertEquals("java.lang.String@" + Integer.toHexString(System.identityHashCode(FOO)),
        ObjectUtils.identityToString(FOO));
    Integer i = new Integer(90);
    String expected = "java.lang.Integer@" + Integer.toHexString(System.identityHashCode(i));
    assertEquals(expected, ObjectUtils.identityToString(i));
    StringBuilder builder = new StringBuilder();
    ObjectUtils.identityToString(builder, i);
    assertEquals(expected, builder.toString());

    try {
        ObjectUtils.identityToString(null, "tmp");
        fail("NullPointerException expected");
    } catch(NullPointerException npe) {
    }
    try {
        ObjectUtils.identityToString(new StringBuilder(), null);
        fail("NullPointerException expected");
    } catch(NullPointerException npe) {
    }
  }

  @Test
  public void testToString_Object() {
    assertEquals("", ObjectUtils.toString((Object) null) );
    assertEquals(Boolean.TRUE.toString(), ObjectUtils.toString(Boolean.TRUE) );
  }

  @Test
  public void testToString_ObjectString() {
    assertEquals(BAR, ObjectUtils.toString((Object) null, BAR) );
    assertEquals(Boolean.TRUE.toString(), ObjectUtils.toString(Boolean.TRUE, BAR) );
  }

  @Test
  public void testNull() {
    assertTrue(ObjectUtils.NULL != null);
    assertTrue(ObjectUtils.NULL instanceof ObjectUtils.Null);
    //assertSame(Objects.NULL, SerializationUtils.clone(Objects.NULL));
  }

//  @Test
//  public void testMax() {
//    Calendar calendar = Calendar.getInstance();
//    Date nonNullComparable1 = calendar.getTime();
//    Date nonNullComparable2 = calendar.getTime();
//
//    calendar.set( Calendar.YEAR, calendar.get( Calendar.YEAR ) -1 );
//    Date minComparable = calendar.getTime();
//
//    assertNotSame( nonNullComparable1, nonNullComparable2 );
//    assertSame( nonNullComparable1, Objects.max( null, nonNullComparable1 ) );
//    assertSame( nonNullComparable1, Objects.max( nonNullComparable1, null ) );
//    assertSame( nonNullComparable1, Objects.max( nonNullComparable1, nonNullComparable2 ) );
//    assertSame( nonNullComparable1, Objects.max( nonNullComparable1, minComparable ) );
//    assertSame( nonNullComparable1, Objects.max( minComparable, nonNullComparable1 ) );
//  }
//
//  @Test
//  public void testMin() {
//    Calendar calendar = Calendar.getInstance();
//    Date nonNullComparable1 = calendar.getTime();
//    Date nonNullComparable2 = calendar.getTime();
//
//    calendar.set( Calendar.YEAR, calendar.get( Calendar.YEAR ) -1 );
//    Date minComparable = calendar.getTime();
//
//    assertNotSame( nonNullComparable1, nonNullComparable2 );
//
//    assertSame( nonNullComparable1, Objects.min( null, nonNullComparable1 ) );
//    assertSame( nonNullComparable1, Objects.min( nonNullComparable1, null ) );
//    assertSame( nonNullComparable1, Objects.min( nonNullComparable1, nonNullComparable2 ) );
//    assertSame( minComparable, Objects.min( nonNullComparable1, minComparable ) );
//    assertSame( minComparable, Objects.min( minComparable, nonNullComparable1 ) );
//  }
}
