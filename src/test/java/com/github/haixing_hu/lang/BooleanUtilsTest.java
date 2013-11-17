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


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.github.haixing_hu.lang.BooleanUtils;

import static org.junit.Assert.*;

/**
 * Unit test for the Booleans class.
 *
 * @author Haixing Hu
 */
public class BooleanUtilsTest {

  @Test
  public void test_negate_Boolean() {
    assertSame(null, BooleanUtils.negate(null));
    assertSame(Boolean.TRUE, BooleanUtils.negate(Boolean.FALSE));
    assertSame(Boolean.FALSE, BooleanUtils.negate(Boolean.TRUE));
  }

  @Test
  public void test_isTrue_Boolean() {
    assertEquals(true, BooleanUtils.isTrue(Boolean.TRUE));
    assertEquals(false, BooleanUtils.isTrue(Boolean.FALSE));
    assertEquals(false, BooleanUtils.isTrue((Boolean) null));
  }

  @Test
  public void test_isNotTrue_Boolean() {
    assertEquals(false, BooleanUtils.isNotTrue(Boolean.TRUE));
    assertEquals(true, BooleanUtils.isNotTrue(Boolean.FALSE));
    assertEquals(true, BooleanUtils.isNotTrue((Boolean) null));
  }

  @Test
  public void test_isFalse_Boolean() {
    assertEquals(false, BooleanUtils.isFalse(Boolean.TRUE));
    assertEquals(true, BooleanUtils.isFalse(Boolean.FALSE));
    assertEquals(false, BooleanUtils.isFalse((Boolean) null));
  }

  @Test
  public void test_isNotFalse_Boolean() {
    assertEquals(true, BooleanUtils.isNotFalse(Boolean.TRUE));
    assertEquals(false, BooleanUtils.isNotFalse(Boolean.FALSE));
    assertEquals(true, BooleanUtils.isNotFalse((Boolean) null));
  }

  @Test
  public void test_toPrimitive_Boolean() {
    assertEquals(true, BooleanUtils.toPrimitive(Boolean.TRUE));
    assertEquals(false, BooleanUtils.toPrimitive(Boolean.FALSE));
    assertEquals(false, BooleanUtils.toPrimitive((Boolean) null));
  }

  @Test
  public void test_toPrimitive_Boolean_boolean() {
    assertEquals(true, BooleanUtils.toPrimitive(Boolean.TRUE, true));
    assertEquals(true, BooleanUtils.toPrimitive(Boolean.TRUE, false));
    assertEquals(false, BooleanUtils.toPrimitive(Boolean.FALSE, true));
    assertEquals(false, BooleanUtils.toPrimitive(Boolean.FALSE, false));
    assertEquals(true, BooleanUtils.toPrimitive((Boolean) null, true));
    assertEquals(false, BooleanUtils.toPrimitive((Boolean) null, false));
  }

  @Test
  public void test_toInt_boolean() {
    assertEquals(1, BooleanUtils.toInt(true));
    assertEquals(0, BooleanUtils.toInt(false));
  }

  @Test
  public void test_toIntObject_boolean() {
    assertEquals(new Integer(1), BooleanUtils.toIntObject(true));
    assertEquals(new Integer(0), BooleanUtils.toIntObject(false));
  }

  @Test
  public void test_toIntObject_Boolean() {
    assertEquals(new Integer(1), BooleanUtils.toIntObject(Boolean.TRUE));
    assertEquals(new Integer(0), BooleanUtils.toIntObject(Boolean.FALSE));
    assertEquals(null, BooleanUtils.toIntObject((Boolean) null));
  }

  @Test
  public void test_toString_boolean() {
    assertEquals("true", BooleanUtils.toString(true));
    assertEquals("false", BooleanUtils.toString(false));
  }

  @Test
  public void test_toString_Boolean() {
    assertEquals(null, BooleanUtils.toString((Boolean) null));
    assertEquals("true", BooleanUtils.toString(Boolean.TRUE));
    assertEquals("false", BooleanUtils.toString(Boolean.FALSE));
  }

  @Test
  public void test_toStringOnOff_boolean() {
    assertEquals("on", BooleanUtils.toStringOnOff(true));
    assertEquals("off", BooleanUtils.toStringOnOff(false));
  }

  @Test
  public void test_toStringOnOff_Boolean() {
    assertEquals(null, BooleanUtils.toStringOnOff((Boolean) null));
    assertEquals("on", BooleanUtils.toStringOnOff(Boolean.TRUE));
    assertEquals("off", BooleanUtils.toStringOnOff(Boolean.FALSE));
  }

  @Test
  public void test_toStringYesNo_boolean() {
    assertEquals("yes", BooleanUtils.toStringYesNo(true));
    assertEquals("no", BooleanUtils.toStringYesNo(false));
  }

  @Test
  public void test_toStringYesNo_Boolean() {
    assertEquals(null, BooleanUtils.toStringYesNo((Boolean) null));
    assertEquals("yes", BooleanUtils.toStringYesNo(Boolean.TRUE));
    assertEquals("no", BooleanUtils.toStringYesNo(Boolean.FALSE));
  }

  @Test
  public void test_toString_boolean_String_String_String() {
    assertEquals("Y", BooleanUtils.toString(true, "Y", "N"));
    assertEquals("N", BooleanUtils.toString(false, "Y", "N"));
  }

  @Test
  public void test_toString_Boolean_String_String_String() {
    assertEquals("U", BooleanUtils.toString((Boolean) null, "Y", "N", "U"));
    assertEquals("Y", BooleanUtils.toString(Boolean.TRUE, "Y", "N", "U"));
    assertEquals("N", BooleanUtils.toString(Boolean.FALSE, "Y", "N", "U"));
  }

  @Test
  public void testAnd_primitive_nullInput() {
    final boolean[] b = null;
    try {
        BooleanUtils.and(b);
        fail("Exception was not thrown for null input.");
    } catch (final NullPointerException ex) {}
  }

  @Test
  public void testAnd_primitive_emptyInput() {
    assertEquals(true, BooleanUtils.and(new boolean[] {}));
  }

  @Test
  public void testAnd_primitive_validInput() {

    boolean[] array = null;

    array = new boolean[] {true, true};
    assertEquals(true, BooleanUtils.and(array));

    array = new boolean[] {false, false};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[] {true, false};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[] {false, true};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[] {false, false, true};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[] {false, true, false};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[] {true, false, false};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[] {true, true, true};
    assertEquals(true, BooleanUtils.and(array));

    array = new boolean[] {false, false, false};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[] {true, true, false};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[] {true, false, true};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[] {false, true, true};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[] {false, false, true, false};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[] {false, true, false, false};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[] {true, false, false, true};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[] {true, true, true, true};
    assertEquals(true, BooleanUtils.and(array));

    array = new boolean[] {false, false, false, false};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[] {true, true, false, true};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[] {true, false, true, false};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[] {false, true, true, false};
    assertEquals(false, BooleanUtils.and(array));
  }

  @Test
  public void testAnd_object_nullInput() {
    final Boolean[] b1 = null;
    try {
        BooleanUtils.and(b1);
        fail("Exception was not thrown for null input.");
    } catch (final NullPointerException ex) {}

    final List<Boolean> b2 = null;
    try {
        BooleanUtils.and(b2);
        fail("Exception was not thrown for null input.");
    } catch (final NullPointerException ex) {}
  }

  @Test
  public void testAnd_object_emptyInput() {
    assertEquals(Boolean.TRUE, BooleanUtils.and(new Boolean[] {}));
    assertEquals(Boolean.TRUE, BooleanUtils.and(new ArrayList<Boolean>()));
  }

  @Test
  public void testAnd_object_nullElementInput() {
    Boolean[] array = null;

    array = new Boolean[] {null};
    assertNull(BooleanUtils.and(array));

    array = new Boolean[] {null, Boolean.FALSE};
    assertNull(BooleanUtils.and(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.TRUE, null};
    assertNull(BooleanUtils.and(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.TRUE, null};
    assertNull(BooleanUtils.and(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.FALSE, null};
    assertNull(BooleanUtils.and(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.FALSE, null};
    assertNull(BooleanUtils.and(array));

  }

  @Test
  public void testAnd_object_validInput() {
    Boolean[] array = null;

    array = new Boolean[] {Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.and(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.and(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));


    array = new Boolean[] {Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.and(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));
  }


  @Test
  public void testOr_primitive_nullInput() {
    final boolean[] b = null;
    try {
        BooleanUtils.or(b);
        fail("Exception was not thrown for null input.");
    } catch (final NullPointerException ex) {}
  }

  @Test
  public void testOr_primitive_emptyInput() {
    assertEquals(false, BooleanUtils.or(new boolean[] {}));
  }

  @Test
  public void testOr_primitive_validInput() {

    boolean[] array = null;

    array = new boolean[] {true, true};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[] {false, false};
    assertEquals(false, BooleanUtils.or(array));

    array = new boolean[] {true, false};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[] {false, true};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[] {false, false, true};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[] {false, true, false};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[] {true, false, false};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[] {true, true, true};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[] {false, false, false};
    assertEquals(false, BooleanUtils.or(array));

    array = new boolean[] {true, true, false};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[] {true, false, true};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[] {false, true, true};
    assertEquals(true, BooleanUtils.or(array));


    array = new boolean[] {false, false, true, false};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[] {false, true, false, false};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[] {true, false, false, true};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[] {true, true, true, true};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[] {false, false, false, false};
    assertEquals(false, BooleanUtils.or(array));

    array = new boolean[] {true, true, false, true};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[] {true, false, true, false};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[] {false, true, true, false};
    assertEquals(true, BooleanUtils.or(array));
  }

  @Test
  public void testOr_object_nullInput() {
    final Boolean[] b = null;
    try {
        BooleanUtils.or(b);
        fail("Exception was not thrown for null input.");
    } catch (final NullPointerException ex) {}

    final List<Boolean> list = null;
    try {
        BooleanUtils.or(list);
        fail("Exception was not thrown for null input.");
    } catch (final NullPointerException ex) {}
  }

  @Test
  public void testOr_object_emptyInput() {
    assertEquals(Boolean.FALSE, BooleanUtils.or(new Boolean[] {}));
    assertEquals(Boolean.FALSE, BooleanUtils.or(new ArrayList<Boolean>()));
  }

  @Test
  public void testOr_object_nullElementInput() {
    Boolean[] array = null;

    array = new Boolean[] {null};
    assertNull(BooleanUtils.or(array));

    array = new Boolean[] {null, Boolean.FALSE};
    assertNull(BooleanUtils.or(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.TRUE, null};
    assertNull(BooleanUtils.or(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.TRUE, null};
    assertNull(BooleanUtils.or(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.FALSE, null};
    assertNull(BooleanUtils.or(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.FALSE, null};
    assertNull(BooleanUtils.or(array));
  }

  @Test
  public void testOr_object_validInput() {
    Boolean[] array = null;

    array = new Boolean[] {Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.or(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.or(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));


    array = new Boolean[] {Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.or(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));
  }

  @Test
  public void testXor_primitive_nullInput() {
    final boolean[] b = null;
    try {
        BooleanUtils.xor(b);
        fail("Exception was not thrown for null input.");
    } catch (final NullPointerException ex) {}
  }

  @Test
  public void testXor_primitive_emptyInput() {
    assertEquals(false, BooleanUtils.xor(new boolean[] {}));
  }

  @Test
  public void testXor_primitive_validInput() {

    boolean[] array = null;

    array = new boolean[] {true, true};
    assertEquals(false, BooleanUtils.xor(array));

    array = new boolean[] {false, false};
    assertEquals(false, BooleanUtils.xor(array));

    array = new boolean[] {true, false};
    assertEquals(true, BooleanUtils.xor(array));

    array = new boolean[] {false, true};
    assertEquals(true, BooleanUtils.xor(array));

    array = new boolean[] {false, false, true};
    assertEquals(true, BooleanUtils.xor(array));

    array = new boolean[] {false, true, false};
    assertEquals(true, BooleanUtils.xor(array));

    array = new boolean[] {true, false, false};
    assertEquals(true, BooleanUtils.xor(array));

    array = new boolean[] {true, true, true};
    assertEquals(true, BooleanUtils.xor(array));

    array = new boolean[] {false, false, false};
    assertEquals(false, BooleanUtils.xor(array));

    array = new boolean[] {true, true, false};
    assertEquals(false, BooleanUtils.xor(array));

    array = new boolean[] {true, false, true};
    assertEquals(false, BooleanUtils.xor(array));

    array = new boolean[] {false, true, true};
    assertEquals(false, BooleanUtils.xor(array));


    array = new boolean[] {false, false, true, false};
    assertEquals(true, BooleanUtils.xor(array));

    array = new boolean[] {false, true, false, false};
    assertEquals(true, BooleanUtils.xor(array));

    array = new boolean[] {true, false, false, true};
    assertEquals(false, BooleanUtils.xor(array));

    array = new boolean[] {true, true, true, true};
    assertEquals(false, BooleanUtils.xor(array));

    array = new boolean[] {false, false, false, false};
    assertEquals(false, BooleanUtils.xor(array));

    array = new boolean[] {true, true, false, true};
    assertEquals(true, BooleanUtils.xor(array));

    array = new boolean[] {true, false, true, false};
    assertEquals(false, BooleanUtils.xor(array));

    array = new boolean[] {false, true, true, false};
    assertEquals(false, BooleanUtils.xor(array));
  }

  @Test
  public void testXor_object_nullInput() {
    final Boolean[] b = null;
    try {
        BooleanUtils.xor(b);
        fail("Exception was not thrown for null input.");
    } catch (final NullPointerException ex) {}

    final List<Boolean> list = null;
    try {
        BooleanUtils.xor(list);
        fail("Exception was not thrown for null input.");
    } catch (final NullPointerException ex) {}
  }

  @Test
  public void testXor_object_emptyInput() {
    assertEquals(Boolean.FALSE, BooleanUtils.xor(new Boolean[] {}));
    assertEquals(Boolean.FALSE, BooleanUtils.xor(new ArrayList<Boolean>()));
  }

  @Test
  public void testXor_object_nullElementInput() {
    Boolean[] array = null;

    array = new Boolean[] {null};
    assertNull(BooleanUtils.xor(array));

    array = new Boolean[] {null, Boolean.FALSE};
    assertNull(BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.TRUE, null};
    assertNull(BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.TRUE, null};
    assertNull(BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.FALSE, null};
    assertNull(BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.FALSE, null};
    assertNull(BooleanUtils.xor(array));
  }

  @Test
  public void testXor_object_validInput() {
    Boolean[] array = null;

    array = new Boolean[] {Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));


    array = new Boolean[] {Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));

    array = new Boolean[] {Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));
  }
}
