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

package com.github.haixing_hu.util.config;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.github.haixing_hu.collection.primitive.BooleanList;
import com.github.haixing_hu.collection.primitive.ByteList;
import com.github.haixing_hu.collection.primitive.CharList;
import com.github.haixing_hu.collection.primitive.DoubleList;
import com.github.haixing_hu.collection.primitive.FloatList;
import com.github.haixing_hu.collection.primitive.IntList;
import com.github.haixing_hu.collection.primitive.LongList;
import com.github.haixing_hu.collection.primitive.ShortList;
import com.github.haixing_hu.collection.primitive.impl.ArrayBooleanList;
import com.github.haixing_hu.collection.primitive.impl.ArrayByteList;
import com.github.haixing_hu.collection.primitive.impl.ArrayCharList;
import com.github.haixing_hu.collection.primitive.impl.ArrayDoubleList;
import com.github.haixing_hu.collection.primitive.impl.ArrayFloatList;
import com.github.haixing_hu.collection.primitive.impl.ArrayIntList;
import com.github.haixing_hu.collection.primitive.impl.ArrayLongList;
import com.github.haixing_hu.collection.primitive.impl.ArrayShortList;
import com.github.haixing_hu.lang.Equality;
import com.github.haixing_hu.lang.SystemUtils;
import com.github.haixing_hu.lang.Type;
import com.github.haixing_hu.util.config.error.ConfigurationError;
import com.github.haixing_hu.util.config.impl.DefaultConfig;
import com.github.haixing_hu.util.value.MultiValues;

import static org.junit.Assert.*;

/**
 * Unit test for the {@link Config} and {@link Config} classes.
 *
 * @author Haixing Hu
 */
public class ConfigTest {

  private static final float FLOAT_EPSILON   = 0.00001f;

  private static final double DOUBLE_EPSILON = 0.00001;

  @Test
  public void testBoolean() {
    final DefaultConfig config = new DefaultConfig();

    config.setBoolean("prop1", true);
    assertEquals(true, config.getBoolean("prop1"));

    config.setBoolean("prop2", false);
    assertEquals(false, config.getBoolean("prop2"));

    config.setBoolean("prop1", false);
    assertEquals(false, config.getBoolean("prop1"));

    // add an empty property, then set the value
    config.setFinal("prop3", true);
    config.setBoolean("prop3", true);
    assertEquals(true, config.getBoolean("prop3"));
    assertEquals(true, config.isFinal("prop3"));
  }

  private static void assertBooleanArrayEquals(final boolean[] lhs, final boolean[] rhs) {
    // since the org.junit.Assert has no assertArrayEquals(boolean[], boolean[]) function,
    // we present a one.
    assertTrue(Equality.equals(lhs, rhs));
  }

  @Test
  public void testBooleans() {
    final DefaultConfig config = new DefaultConfig();

    config.addBoolean("prop1", true);
    assertEquals(true, config.getBoolean("prop1"));
    assertBooleanArrayEquals(new boolean[]{true},
        config.getBooleans("prop1"));

    config.addBoolean("prop1", false);
    assertEquals(true, config.getBoolean("prop1"));
    assertBooleanArrayEquals(new boolean[]{true, false},
        config.getBooleans("prop1"));

    // set the value of a exist list property
    config.setFinal("prop1", true);
    config.setBoolean("prop1", true);
    assertEquals(true, config.getBoolean("prop1"));
    assertBooleanArrayEquals(new boolean[]{true},
        config.getBooleans("prop1"));
    assertEquals(true, config.isFinal("prop1"));

    // add an array of values
    config.addBooleans("prop1", false, true);
    assertBooleanArrayEquals(new boolean[]{true, false, true},
        config.getBooleans("prop1"));

    config.addBooleans("prop2", true);
    assertBooleanArrayEquals(new boolean[]{true},
        config.getBooleans("prop2"));

    config.addBooleans("prop3", true, false, true);
    assertBooleanArrayEquals(new boolean[]{true, false, true},
        config.getBooleans("prop3"));

    // add a collection of values
    final BooleanList list = new ArrayBooleanList();
    list.add(false);
    list.add(true);
    list.add(false);

    config.addBooleans("prop1", list);
    assertBooleanArrayEquals(new boolean[]{true, false, true,
        false, true, false},
        config.getBooleans("prop1"));

  }

  @Test
  public void testByte() {
    final DefaultConfig config = new DefaultConfig();

    config.setByte("prop1", (byte)0);
    assertEquals((byte)0, config.getByte("prop1"));

    config.setByte("prop2", (byte)1);
    assertEquals((byte)1, config.getByte("prop2"));

    config.setByte("prop1", (byte)2);
    assertEquals((byte)2, config.getByte("prop1"));

    // add an empty property, then set the value
    config.setFinal("prop3", true);
    config.setByte("prop3", (byte)3);
    assertEquals((byte)3, config.getByte("prop3"));
    assertEquals(true, config.isFinal("prop3"));
  }

  @Test
  public void testBytes() {
    final DefaultConfig config = new DefaultConfig();

    config.addByte("prop1", (byte)0);
    assertEquals((byte)0, config.getByte("prop1"));
    assertArrayEquals(new byte[]{(byte)0},
        config.getBytes("prop1"));

    config.addByte("prop1", (byte)1);
    assertEquals((byte)0, config.getByte("prop1"));
    assertArrayEquals(new byte[]{(byte)0, (byte)1},
        config.getBytes("prop1"));

    // set the value of a exist list property
    config.setFinal("prop1", true);
    config.setByte("prop1", (byte)0);
    assertEquals((byte)0, config.getByte("prop1"));
    assertArrayEquals(new byte[]{(byte)0},
        config.getBytes("prop1"));
    assertEquals(true, config.isFinal("prop1"));

    // add an array of values
    config.addBytes("prop1", (byte)1, (byte)2);
    assertArrayEquals(new byte[]{(byte)0, (byte)1, (byte)2},
        config.getBytes("prop1"));

    config.addBytes("prop2", (byte)0);
    assertArrayEquals(new byte[]{(byte)0},
        config.getBytes("prop2"));

    config.addBytes("prop3", (byte)0, (byte)1, (byte)2);
    assertArrayEquals(new byte[]{(byte)0, (byte)1, (byte)2},
        config.getBytes("prop3"));

    // add a collection of values
    final ByteList list = new ArrayByteList();
    list.add((byte)3);
    list.add((byte)4);
    list.add((byte)5);

    config.addBytes("prop1", list);
    assertArrayEquals(new byte[]{(byte)0, (byte)1, (byte)2,
        (byte)3, (byte)4, (byte)5},
        config.getBytes("prop1"));
  }

  @Test
  public void testShort() {
    final DefaultConfig config = new DefaultConfig();

    config.setShort("prop1", (short)0);
    assertEquals((short)0, config.getShort("prop1"));

    config.setShort("prop2", (short)1);
    assertEquals((short)1, config.getShort("prop2"));

    config.setShort("prop1", (short)2);
    assertEquals((short)2, config.getShort("prop1"));

    // add an empty property, then set the value
    config.setFinal("prop3", true);
    config.setShort("prop3", (short)3);
    assertEquals((short)3, config.getShort("prop3"));
    assertEquals(true, config.isFinal("prop3"));
  }

  @Test
  public void testShorts() {
    final DefaultConfig config = new DefaultConfig();

    config.addShort("prop1", (short)0);
    assertEquals((short)0, config.getShort("prop1"));
    assertArrayEquals(new short[]{(short)0},
        config.getShorts("prop1"));

    config.addShort("prop1", (short)1);
    assertEquals((short)0, config.getShort("prop1"));
    assertArrayEquals(new short[]{(short)0, (short)1},
        config.getShorts("prop1"));

    // set the value of a exist list property
    config.setFinal("prop1", true);
    config.setShort("prop1", (short)0);
    assertEquals((short)0, config.getShort("prop1"));
    assertArrayEquals(new short[]{(short)0},
        config.getShorts("prop1"));
    assertEquals(true, config.isFinal("prop1"));

    // add an array of values
    config.addShorts("prop1", (short)1, (short)2);
    assertArrayEquals(new short[]{(short)0, (short)1, (short)2},
        config.getShorts("prop1"));

    config.addShorts("prop2", (short)0);
    assertArrayEquals(new short[]{(short)0},
        config.getShorts("prop2"));

    config.addShorts("prop3", (short)0, (short)1, (short)2);
    assertArrayEquals(new short[]{(short)0, (short)1, (short)2},
        config.getShorts("prop3"));

    // add a collection of values
    final ShortList list = new ArrayShortList();
    list.add((short)3);
    list.add((short)4);
    list.add((short)5);

    config.addShorts("prop1", list);
    assertArrayEquals(new short[]{(short)0, (short)1, (short)2,
        (short)3, (short)4, (short)5},
        config.getShorts("prop1"));

  }


  @Test
  public void testInt() {
    final DefaultConfig config = new DefaultConfig();

    config.setInt("prop1", 0);
    assertEquals(0, config.getInt("prop1"));

    config.setInt("prop2", 1);
    assertEquals(1, config.getInt("prop2"));

    config.setInt("prop1", 2);
    assertEquals(2, config.getInt("prop1"));

    // add an empty property, then set the value
    config.setFinal("prop3", true);
    config.setInt("prop3", 3);
    assertEquals(3, config.getInt("prop3"));
    assertEquals(true, config.isFinal("prop3"));
  }

  @Test
  public void testInts() {
    final DefaultConfig config = new DefaultConfig();

    config.addInt("prop1", 0);
    assertEquals(0, config.getInt("prop1"));
    assertArrayEquals(new int[]{0},
        config.getInts("prop1"));

    config.addInt("prop1", 1);
    assertEquals(0, config.getInt("prop1"));
    assertArrayEquals(new int[]{0, 1},
        config.getInts("prop1"));

    // set the value of a exist list property
    config.setFinal("prop1", true);
    config.setInt("prop1", 0);
    assertEquals(0, config.getInt("prop1"));
    assertArrayEquals(new int[]{0},
        config.getInts("prop1"));
    assertEquals(true, config.isFinal("prop1"));

    // add an array of values
    config.addInts("prop1", 1, 2);
    assertArrayEquals(new int[]{0, 1, 2},
        config.getInts("prop1"));

    config.addInts("prop2", 0);
    assertArrayEquals(new int[]{0},
        config.getInts("prop2"));

    config.addInts("prop3", 0, 1, 2);
    assertArrayEquals(new int[]{0, 1, 2},
        config.getInts("prop3"));

    // add a collection of values
    final IntList list = new ArrayIntList();
    list.add(3);
    list.add(4);
    list.add(5);

    config.addInts("prop1", list);
    assertArrayEquals(new int[]{0, 1, 2,
        3, 4, 5},
        config.getInts("prop1"));

  }

  @Test
  public void testLong() {
    final DefaultConfig config = new DefaultConfig ();

    config.setLong("prop1", 0L);
    assertEquals(0L, config.getLong("prop1"));

    config.setLong("prop2", 1L);
    assertEquals(1L, config.getLong("prop2"));

    config.setLong("prop1", 2L);
    assertEquals(2L, config.getLong("prop1"));

    // add an empty property, then set the value
    config.setFinal("prop3", true);
    config.setLong("prop3", 3L);
    assertEquals(3L, config.getLong("prop3"));
    assertEquals(true, config.isFinal("prop3"));
  }

  @Test
  public void testLongs() {
    final DefaultConfig config = new DefaultConfig ();

    config.addLong("prop1", 0L);
    assertEquals(0L, config.getLong("prop1"));
    assertArrayEquals(new long[]{0L},
        config.getLongs("prop1"));

    config.addLong("prop1", 1L);
    assertEquals(0L, config.getLong("prop1"));
    assertArrayEquals(new long[]{0L, 1L},
        config.getLongs("prop1"));

    // set the value of a exist list property
    config.setFinal("prop1", true);
    config.setLong("prop1", 0L);
    assertEquals(0L, config.getLong("prop1"));
    assertArrayEquals(new long[]{0L},
        config.getLongs("prop1"));
    assertEquals(true, config.isFinal("prop1"));

    // add an array of values
    config.addLongs("prop1", 1L, 2L);
    assertArrayEquals(new long[]{0L, 1L, 2L},
        config.getLongs("prop1"));

    config.addLongs("prop2", 0L);
    assertArrayEquals(new long[]{0L},
        config.getLongs("prop2"));

    config.addLongs("prop3", 0L, 1L, 2L);
    assertArrayEquals(new long[]{0L, 1L, 2L},
        config.getLongs("prop3"));

    // add a collection of values
    final LongList list = new ArrayLongList();
    list.add(3L);
    list.add(4L);
    list.add(5L);

    config.addLongs("prop1", list);
    assertArrayEquals(new long[]{0L, 1L, 2L,
        3L, 4L, 5L},
        config.getLongs("prop1"));

  }

  @Test
  public void testChar() {
    final DefaultConfig config = new DefaultConfig ();

    config.setChar("prop1", '0');
    assertEquals('0', config.getChar("prop1"));

    config.setChar("prop2", '1');
    assertEquals('1', config.getChar("prop2"));

    config.setChar("prop1", '2');
    assertEquals('2', config.getChar("prop1"));

    // add an empty property, then set the value
    config.setFinal("prop3", true);
    config.setChar("prop3", '3');
    assertEquals('3', config.getChar("prop3"));
    assertEquals(true, config.isFinal("prop3"));
  }

  @Test
  public void testChars() {
    final DefaultConfig config = new DefaultConfig ();

    config.addChar("prop1", '0');
    assertEquals('0', config.getChar("prop1"));
    assertArrayEquals(new char[]{'0'},
        config.getChars("prop1"));

    config.addChar("prop1", '1');
    assertEquals('0', config.getChar("prop1"));
    assertArrayEquals(new char[]{'0', '1'},
        config.getChars("prop1"));

    // set the value of a exist list property
    config.setFinal("prop1", true);
    config.setChar("prop1", '0');
    assertEquals('0', config.getChar("prop1"));
    assertArrayEquals(new char[]{'0'},
        config.getChars("prop1"));
    assertEquals(true, config.isFinal("prop1"));

    // add an array of values
    config.addChars("prop1", '1', '2');
    assertArrayEquals(new char[]{'0', '1', '2'},
        config.getChars("prop1"));

    config.addChars("prop2", '0');
    assertArrayEquals(new char[]{'0'},
        config.getChars("prop2"));

    config.addChars("prop3", '0', '1', '2');
    assertArrayEquals(new char[]{'0', '1', '2'},
        config.getChars("prop3"));

    // add a collection of values
    final CharList list = new ArrayCharList();
    list.add('3');
    list.add('4');
    list.add('5');

    config.addChars("prop1", list);
    assertArrayEquals(new char[]{'0', '1', '2',
        '3', '4', '5'},
        config.getChars("prop1"));
  }


  @Test
  public void testFloat() {


    final DefaultConfig config = new DefaultConfig ();
    config.setFloat("prop1", 0.0f);
    assertEquals(0.0f, config.getFloat("prop1"), FLOAT_EPSILON);

    config.setFloat("prop2", 1.1f);
    assertEquals(1.1f, config.getFloat("prop2"), FLOAT_EPSILON);

    config.setFloat("prop1", 2.2f);
    assertEquals(2.2f, config.getFloat("prop1"), FLOAT_EPSILON);

    // add an empty property, then set the value
    config.setFinal("prop3", true);
    config.setFloat("prop3", 3.3f);
    assertEquals(3.3f, config.getFloat("prop3"), FLOAT_EPSILON);
    assertEquals(true, config.isFinal("prop3"));
  }

  @Test
  public void testFloats() {
    final DefaultConfig config = new DefaultConfig ();

    config.addFloat("prop1", 0.0f);
    assertEquals(0.0f, config.getFloat("prop1"), FLOAT_EPSILON);
    assertArrayEquals(new float[]{0.0f},
        config.getFloats("prop1"), FLOAT_EPSILON);

    config.addFloat("prop1", 1.1f);
    assertEquals(0.0f, config.getFloat("prop1"), FLOAT_EPSILON);
    assertArrayEquals(new float[]{0.0f, 1.1f},
        config.getFloats("prop1"), FLOAT_EPSILON);

    // set the value of a exist list property
    config.setFinal("prop1", true);
    config.setFloat("prop1", 0.0f);
    assertEquals(0.0f, config.getFloat("prop1"), FLOAT_EPSILON);
    assertArrayEquals(new float[]{0.0f},
        config.getFloats("prop1"), FLOAT_EPSILON);
    assertEquals(true, config.isFinal("prop1"));

    // add an array of values
    config.addFloats("prop1", 1.1f, 2.2f);
    assertArrayEquals(new float[]{0.0f, 1.1f, 2.2f},
        config.getFloats("prop1"), FLOAT_EPSILON);

    config.addFloats("prop2", 0.0f);
    assertArrayEquals(new float[]{0.0f},
        config.getFloats("prop2"), FLOAT_EPSILON);

    config.addFloats("prop3", 0.0f, 1.1f, 2.2f);
    assertArrayEquals(new float[]{0.0f, 1.1f, 2.2f},
        config.getFloats("prop3"), FLOAT_EPSILON);

    // add a collection of values
    final FloatList list = new ArrayFloatList();
    list.add(3.3f);
    list.add(4.4f);
    list.add(5.5f);

    config.addFloats("prop1", list);
    assertArrayEquals(new float[]{0.0f, 1.1f, 2.2f,
        3.3f, 4.4f, 5.5f},
        config.getFloats("prop1"), FLOAT_EPSILON);

  }


  @Test
  public void testDouble() {
    final DefaultConfig config = new DefaultConfig ();

    config.setDouble("prop1", 0.0);
    assertEquals(0.0, config.getDouble("prop1"), DOUBLE_EPSILON);

    config.setDouble("prop2", 1.1);
    assertEquals(1.1, config.getDouble("prop2"), DOUBLE_EPSILON);

    config.setDouble("prop1", 2.2);
    assertEquals(2.2, config.getDouble("prop1"), DOUBLE_EPSILON);

    // add an empty property, then set the value
    config.setFinal("prop3", true);
    config.setDouble("prop3", 3.3);
    assertEquals(3.3, config.getDouble("prop3"), DOUBLE_EPSILON);
    assertEquals(true, config.isFinal("prop3"));
  }

  @Test
  public void testDoubles() {
    final DefaultConfig config = new DefaultConfig ();

    config.addDouble("prop1", 0.0);
    assertEquals(0.0, config.getDouble("prop1"), DOUBLE_EPSILON);
    assertArrayEquals(new double[]{0.0},
        config.getDoubles("prop1"), DOUBLE_EPSILON);

    config.addDouble("prop1", 1.1);
    assertEquals(0.0, config.getDouble("prop1"), DOUBLE_EPSILON);
    assertArrayEquals(new double[]{0.0, 1.1},
        config.getDoubles("prop1"), DOUBLE_EPSILON);

    // set the value of a exist list property
    config.setFinal("prop1", true);
    config.setDouble("prop1", 0.0);
    assertEquals(0.0, config.getDouble("prop1"), DOUBLE_EPSILON);
    assertArrayEquals(new double[]{0.0},
        config.getDoubles("prop1"), DOUBLE_EPSILON);
    assertEquals(true, config.isFinal("prop1"));

    // add an array of values
    config.addDoubles("prop1", 1.1, 2.2);
    assertArrayEquals(new double[]{0.0, 1.1, 2.2},
        config.getDoubles("prop1"), DOUBLE_EPSILON);

    config.addDoubles("prop2", 0.0);
    assertArrayEquals(new double[]{0.0},
        config.getDoubles("prop2"), DOUBLE_EPSILON);

    config.addDoubles("prop3", 0.0, 1.1, 2.2);
    assertArrayEquals(new double[]{0.0, 1.1, 2.2},
        config.getDoubles("prop3"), DOUBLE_EPSILON);

    // add a collection of values
    final DoubleList list = new ArrayDoubleList();
    list.add(3.3);
    list.add(4.4);
    list.add(5.5);

    config.addDoubles("prop1", list);
    assertArrayEquals(new double[]{0.0, 1.1, 2.2,
        3.3, 4.4, 5.5},
        config.getDoubles("prop1"), DOUBLE_EPSILON);

  }

  @Test
  public void testBigDecimal() {
    final DefaultConfig config = new DefaultConfig ();

    config.setBigDecimal("prop1", BigDecimal.ONE);
    assertEquals(BigDecimal.ONE, config.getBigDecimal("prop1"));

    config.setBigDecimal("prop2", BigDecimal.TEN);
    assertEquals(BigDecimal.TEN, config.getBigDecimal("prop2"));

    config.setBigDecimal("prop1", BigDecimal.ZERO);
    assertEquals(BigDecimal.ZERO, config.getBigDecimal("prop1"));

    // add an empty property, then set the value
    config.setFinal("prop3", true);
    config.setBigDecimal("prop3", BigDecimal.TEN);
    assertEquals(BigDecimal.TEN, config.getBigDecimal("prop3"));
    assertEquals(true, config.isFinal("prop3"));
  }

  @Test
  public void testBigDecimals() {
    final DefaultConfig config = new DefaultConfig ();

    config.addBigDecimal("prop1", BigDecimal.ONE);
    assertEquals(BigDecimal.ONE, config.getBigDecimal("prop1"));
    assertArrayEquals(new Object[]{BigDecimal.ONE},
        config.getBigDecimals("prop1"));

    config.addBigDecimal("prop1", BigDecimal.TEN);
    assertEquals(BigDecimal.ONE, config.getBigDecimal("prop1"));
    assertArrayEquals(new Object[]{BigDecimal.ONE, BigDecimal.TEN},
        config.getBigDecimals("prop1"));

    // set the value of a exist list property
    config.setFinal("prop1", true);
    config.setBigDecimal("prop1", BigDecimal.ZERO);
    assertEquals(BigDecimal.ZERO, config.getBigDecimal("prop1"));
    assertArrayEquals(new Object[]{BigDecimal.ZERO},
        config.getBigDecimals("prop1"));
    assertEquals(true, config.isFinal("prop1"));

    // add an array of values
    config.addBigDecimals("prop1", BigDecimal.ONE, BigDecimal.TEN);
    assertArrayEquals(new Object[]{BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN},
        config.getBigDecimals("prop1"));

    config.addBigDecimals("prop2", BigDecimal.ONE);
    assertArrayEquals(new Object[]{BigDecimal.ONE},
        config.getBigDecimals("prop2"));

    config.addBigDecimals("prop3", BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN);
    assertArrayEquals(new Object[]{BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN},
        config.getBigDecimals("prop3"));

    // add a collection of values
    final List<BigDecimal> list = new LinkedList<BigDecimal>();
    list.add(BigDecimal.ZERO);
    list.add(BigDecimal.ONE);
    list.add(BigDecimal.TEN);

    config.addBigDecimals("prop1", list);
    assertArrayEquals(new Object[]{BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN,
        BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN},
        config.getBigDecimals("prop1"));

  }

  @Test
  public void testBigInteger() {
    final DefaultConfig config = new DefaultConfig ();

    config.setBigInteger("prop1", BigInteger.ONE);
    assertEquals(BigInteger.ONE, config.getBigInteger("prop1"));

    config.setBigInteger("prop2", BigInteger.TEN);
    assertEquals(BigInteger.TEN, config.getBigInteger("prop2"));

    config.setBigInteger("prop1", BigInteger.ZERO);
    assertEquals(BigInteger.ZERO, config.getBigInteger("prop1"));

    // add an empty property, then set the value
    config.setFinal("prop3", true);
    config.setBigInteger("prop3", BigInteger.TEN);
    assertEquals(BigInteger.TEN, config.getBigInteger("prop3"));
    assertEquals(true, config.isFinal("prop3"));
  }

  @Test
  public void testBigIntegers() {
    final DefaultConfig config = new DefaultConfig ();

    config.addBigInteger("prop1", BigInteger.ONE);
    assertEquals(BigInteger.ONE, config.getBigInteger("prop1"));
    assertArrayEquals(new Object[]{BigInteger.ONE},
        config.getBigIntegers("prop1"));

    config.addBigInteger("prop1", BigInteger.TEN);
    assertEquals(BigInteger.ONE, config.getBigInteger("prop1"));
    assertArrayEquals(new Object[]{BigInteger.ONE, BigInteger.TEN},
        config.getBigIntegers("prop1"));

    // set the value of a exist list property
    config.setFinal("prop1", true);
    config.setBigInteger("prop1", BigInteger.ZERO);
    assertEquals(BigInteger.ZERO, config.getBigInteger("prop1"));
    assertArrayEquals(new Object[]{BigInteger.ZERO},
        config.getBigIntegers("prop1"));
    assertEquals(true, config.isFinal("prop1"));

    // add an array of values
    config.addBigIntegers("prop1", BigInteger.ONE, BigInteger.TEN);
    assertArrayEquals(new Object[]{BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN},
        config.getBigIntegers("prop1"));

    config.addBigIntegers("prop2", BigInteger.ONE);
    assertArrayEquals(new Object[]{BigInteger.ONE},
        config.getBigIntegers("prop2"));

    config.addBigIntegers("prop3", BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN);
    assertArrayEquals(new Object[]{BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN},
        config.getBigIntegers("prop3"));

    // add a collection of values
    final List<BigInteger> list = new LinkedList<BigInteger>();
    list.add(BigInteger.ZERO);
    list.add(BigInteger.ONE);
    list.add(BigInteger.TEN);

    config.addBigIntegers("prop1", list);
    assertArrayEquals(new Object[]{BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN,
        BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN},
        config.getBigIntegers("prop1"));

  }

  @Test
  public void testDate() {
    final DefaultConfig config = new DefaultConfig ();
    final Date date = new Date();

    date.setTime(0L);
    config.setDate("prop1", date);
    assertEquals(date, config.getDate("prop1"));
    assertNotSame(date, config.getDate("prop1"));

    date.setTime(1L);
    config.setDate("prop2", date);
    assertEquals(date, config.getDate("prop2"));
    assertNotSame(date, config.getDate("prop2"));

    date.setTime(2L);
    // note the the Date set to the configuration must be cloned.
    config.setDate("prop1", date);
    assertEquals(date, config.getDate("prop1"));
    assertNotSame(date, config.getDate("prop1"));

    // add an empty property, then set the value
    config.setFinal("prop3", true);
    date.setTime(3L);
    config.setDate("prop3", date);
    assertEquals(date, config.getDate("prop3"));
    assertNotSame(date, config.getDate("prop3"));
    assertEquals(true, config.isFinal("prop3"));

  }

  @Test
  public void testDates() {
    final DefaultConfig config = new DefaultConfig ();
    final Date date0 = new Date(0L);
    final Date date1 = new Date(1L);
    final Date date2 = new Date(2L);
    final Date date3 = new Date(3L);
    final Date date4 = new Date(4L);
    final Date date5 = new Date(5L);

    config.addDate("prop1", date0);
    assertEquals(date0, config.getDate("prop1"));
    assertNotSame(date0, config.getDate("prop1"));
    assertArrayEquals(new Date[]{date0},
        config.getDates("prop1"));

    config.addDate("prop1", date1);
    assertEquals(date0, config.getDate("prop1"));
    assertArrayEquals(new Date[]{date0, date1},
        config.getDates("prop1"));

    // set the value of a exist list property
    config.setFinal("prop1", true);
    config.setDate("prop1", date0);
    assertEquals(date0, config.getDate("prop1"));
    assertArrayEquals(new Date[]{date0},
        config.getDates("prop1"));
    assertEquals(true, config.isFinal("prop1"));

    // add an array of values
    config.addDates("prop1", date1, date2);
    assertArrayEquals(new Date[]{date0, date1, date2},
        config.getDates("prop1"));

    config.addDates("prop2", date0);
    assertArrayEquals(new Date[]{date0},
        config.getDates("prop2"));

    config.addDates("prop3", date0, date1, date2);
    assertArrayEquals(new Date[]{date0, date1, date2},
        config.getDates("prop3"));

    // add a collection of values
    final List<Date> list = new LinkedList<Date>();
    list.add(date3);
    list.add(date4);
    list.add(date5);

    config.addDates("prop1", list);
    assertArrayEquals(new Date[]{date0, date1, date2,
        date3, date4, date5},
        config.getDates("prop1"));
  }

  @Test
  public void testRawString() {
    final DefaultConfig config = new DefaultConfig ();
    String str = null;

    str = "0";
    config.setString("prop1", str);
    assertEquals(str, config.getRawString("prop1"));
    assertSame(str, config.getRawString("prop1"));

    str = "${prop1}=0";
    config.setString("prop2", str);
    assertEquals(str, config.getRawString("prop2"));
    assertSame(str, config.getRawString("prop2"));

    str = "${prop2}=${prop1}";
    // note the the String set to the configuration must NOT be cloned.
    config.setString("prop1", str);
    assertEquals(str, config.getRawString("prop1"));
    assertSame(str, config.getRawString("prop1"));

    // add an empty property, then set the value
    config.setFinal("prop3", true);
    str = "${prop3}=${prop1}";
    config.setString("prop3", str);
    assertEquals(str, config.getRawString("prop3"));
    assertSame(str, config.getRawString("prop3"));
    assertEquals(true, config.isFinal("prop3"));

  }

  @Test
  public void testRawStrings() {
    final DefaultConfig config = new DefaultConfig ();
    final String str0 = "0";
    final String str1 = "${prop1}=0";
    final String str2 = "2";
    final String str3 = "${prop2}=${prop1}";
    final String str4 = "4";
    final String str5 = "${prop3}=${prop1}";

    config.addString("prop1", str0);
    assertEquals(str0, config.getRawString("prop1"));
    assertSame(str0, config.getRawString("prop1"));
    assertArrayEquals(new String[]{str0},
        config.getRawStrings("prop1"));

    config.addString("prop1", str1);
    assertEquals(str0, config.getRawString("prop1"));
    assertArrayEquals(new String[]{str0, str1},
        config.getRawStrings("prop1"));

    // set the value of a exist list property
    config.setFinal("prop1", true);
    config.setString("prop1", str0);
    assertEquals(str0, config.getRawString("prop1"));
    assertArrayEquals(new String[]{str0},
        config.getRawStrings("prop1"));
    assertEquals(true, config.isFinal("prop1"));

    // add an array of values
    config.addStrings("prop1", str1, str2);
    assertArrayEquals(new String[]{str0, str1, str2},
        config.getRawStrings("prop1"));

    config.addStrings("prop2", str0);
    assertArrayEquals(new String[]{str0},
        config.getRawStrings("prop2"));

    config.addStrings("prop3", str0, str1, str2);
    assertArrayEquals(new String[]{str0, str1, str2},
        config.getRawStrings("prop3"));

    // add a collection of values
    final List<String> list = new LinkedList<String>();
    list.add(str3);
    list.add(str4);
    list.add(str5);

    config.addStrings("prop1", list);
    assertArrayEquals(new String[]{str0, str1, str2,
        str3, str4, str5},
        config.getRawStrings("prop1"));
  }

  @Test
  public void testString() {
    final DefaultConfig config = new DefaultConfig ();
    String str = null;

    str = "0";
    config.setString("prop1", str);
    assertEquals(str, config.getString("prop1"));
    assertSame(str, config.getString("prop1"));

    str = "1";
    config.setString("prop2", str);
    assertEquals(str, config.getString("prop2"));
    assertSame(str, config.getString("prop2"));

    str = "2";
    // note the the String set to the configuration must NOT be cloned.
    config.setString("prop1", str);
    assertEquals(str, config.getString("prop1"));
    assertSame(str, config.getString("prop1"));

    // add an empty property, then set the value
    config.setFinal("prop3", true);
    str = "3";
    config.setString("prop3", str);
    assertEquals(str, config.getString("prop3"));
    assertSame(str, config.getString("prop3"));
    assertEquals(true, config.isFinal("prop3"));

  }

  @Test
  public void testSubstitutedString() {
    final DefaultConfig config = new DefaultConfig ();
    config.setInt("intvar", 131);
    config.setString("my.int", "x=${intvar}");
    config.setString("my.base", "/tmp/${user.name}");
    config.setString("my.file", "hello");
    config.setString("my.suffix", ".txt");
    config.setString("my.relfile", "${my.file}${my.suffix}");
    config.setString("my.fullfile", "${my.base}/${my.file}${my.suffix}");
    config.setString("my.failsexpand", "a${my.undefvar}b");
    config.setString("my.recursive", "1${my.recursive}");

    assertEquals("x=131",
        config.getString("my.int"));
    assertEquals("/tmp/" + SystemUtils.getProperty("user.name"),
        config.getString("my.base"));
    assertEquals("hello.txt",
        config.getString("my.relfile"));
    assertEquals("/tmp/" + SystemUtils.getProperty("user.name") + "/hello.txt",
        config.getString("my.fullfile"));
    assertEquals("a${my.undefvar}b",
        config.getString("my.failsexpand"));

    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < Config.MAX_SUBSTITUTION_DEPTH; ++i) {
      builder.append('1');
    }
    builder.append("1${my.recursive}");
    final String expected = builder.toString();
    assertEquals(expected, config.getString("my.recursive"));
  }

  @Test
  public void testStrings() {
    final DefaultConfig config = new DefaultConfig ();
    final String str0 = "0";
    final String str1 = "1";
    final String str2 = "2";
    final String str3 = "3";
    final String str4 = "4";
    final String str5 = "5";

    config.addString("prop1", str0);
    assertEquals(str0, config.getString("prop1"));
    assertSame(str0, config.getString("prop1"));
    assertArrayEquals(new String[]{str0},
        config.getStrings("prop1"));

    config.addString("prop1", str1);
    assertEquals(str0, config.getString("prop1"));
    assertArrayEquals(new String[]{str0, str1},
        config.getStrings("prop1"));

    // set the value of a exist list property
    config.setFinal("prop1", true);
    config.setString("prop1", str0);
    assertEquals(str0, config.getString("prop1"));
    assertArrayEquals(new String[]{str0},
        config.getStrings("prop1"));
    assertEquals(true, config.isFinal("prop1"));

    // add an array of values
    config.addStrings("prop1", str1, str2);
    assertArrayEquals(new String[]{str0, str1, str2},
        config.getStrings("prop1"));

    config.addStrings("prop2", str0);
    assertArrayEquals(new String[]{str0},
        config.getStrings("prop2"));

    config.addStrings("prop3", str0, str1, str2);
    assertArrayEquals(new String[]{str0, str1, str2},
        config.getStrings("prop3"));

    // add a collection of values
    final List<String> list = new LinkedList<String>();
    list.add(str3);
    list.add(str4);
    list.add(str5);

    config.addStrings("prop1", list);
    assertArrayEquals(new String[]{str0, str1, str2,
        str3, str4, str5},
        config.getStrings("prop1"));

  }


  @Test
  public void testClass() {
    final DefaultConfig config = new DefaultConfig ();
    Class<?> cls = null;

    cls = Byte.class;
    config.setClass("prop1", cls);
    assertEquals(cls, config.getClass("prop1"));
    assertSame(cls, config.getClass("prop1"));

    cls = Short.class;
    config.setClass("prop2", cls);
    assertEquals(cls, config.getClass("prop2"));
    assertSame(cls, config.getClass("prop2"));

    cls = Integer.class;
    config.setClass("prop1", cls);
    assertEquals(cls, config.getClass("prop1"));
    assertSame(cls, config.getClass("prop1"));

    // add an empty property, then set the value
    config.setFinal("prop3", true);
    cls = Long.class;
    config.setClass("prop3", cls);
    assertEquals(cls, config.getClass("prop3"));
    assertSame(cls, config.getClass("prop3"));
    assertEquals(true, config.isFinal("prop3"));

  }

  @Test
  public void testClasses() {
    final DefaultConfig config = new DefaultConfig ();
    final Class<?> cls0 = Byte.class;
    final Class<?> cls1 = Short.class;
    final Class<?> cls2 = Integer.class;
    final Class<?> cls3 = Long.class;
    final Class<?> cls4 = Float.class;
    final Class<?> cls5 = Double.class;

    config.addClass("prop1", cls0);
    assertEquals(cls0, config.getClass("prop1"));
    assertSame(cls0, config.getClass("prop1"));
    assertArrayEquals(new Class<?>[]{cls0},
        config.getClasses("prop1"));

    config.addClass("prop1", cls1);
    assertEquals(cls0, config.getClass("prop1"));
    assertArrayEquals(new Class<?>[]{cls0, cls1},
        config.getClasses("prop1"));

    // set the value of a exist list property
    config.setFinal("prop1", true);
    config.setClass("prop1", cls0);
    assertEquals(cls0, config.getClass("prop1"));
    assertArrayEquals(new Class<?>[]{cls0},
        config.getClasses("prop1"));
    assertEquals(true, config.isFinal("prop1"));

    // add an array of values
    config.addClasses("prop1", cls1, cls2);
    assertArrayEquals(new Class<?>[]{cls0, cls1, cls2},
        config.getClasses("prop1"));

    config.addClasses("prop2", cls0);
    assertArrayEquals(new Class<?>[]{cls0},
        config.getClasses("prop2"));

    config.addClasses("prop3", cls0, cls1, cls2);
    assertArrayEquals(new Class<?>[]{cls0, cls1, cls2},
        config.getClasses("prop3"));

    // add a collection of values
    final List<Class<?>> list = new LinkedList<Class<?>>();
    list.add(cls3);
    list.add(cls4);
    list.add(cls5);

    config.addClasses("prop1", list);
    assertArrayEquals(new Class<?>[]{cls0, cls1, cls2,
        cls3, cls4, cls5},
        config.getClasses("prop1"));
  }


  @Test
  public void testByteArray() {
    final DefaultConfig config = new DefaultConfig ();
    byte[] bytes = null;

    bytes = new byte[]{0, 1};
    config.setByteArray("prop1", bytes);
    assertArrayEquals(bytes, config.getByteArray("prop1"));
    assertNotSame(bytes, config.getByteArray("prop1"));

    bytes = new byte[]{0, 1, 2};
    config.setByteArray("prop2", bytes);
    assertArrayEquals(bytes, config.getByteArray("prop2"));
    assertNotSame(bytes, config.getByteArray("prop2"));

    bytes[0] = 1;
    assertArrayEquals(new byte[]{0, 1}, config.getByteArray("prop1"));
    config.setByteArray("prop1", bytes);
    assertArrayEquals(bytes, config.getByteArray("prop1"));
    assertNotSame(bytes, config.getByteArray("prop1"));

    // add an empty property, then set the value
    config.setFinal("prop3", true);
    bytes = new byte[]{0, 1, 2, 3, 4};
    config.setByteArray("prop3", bytes);
    assertArrayEquals(bytes, config.getByteArray("prop3"));
    assertNotSame(bytes, config.getByteArray("prop3"));
    assertEquals(true, config.isFinal("prop3"));

  }

  @Test
  public void testByteArrays() {
    final DefaultConfig config = new DefaultConfig ();
    final byte[] bytes0 = new byte[]{};
    final byte[] bytes1 = new byte[]{0};
    final byte[] bytes2 = new byte[]{0, 1};
    final byte[] bytes3 = new byte[]{0, 1, 2};
    final byte[] bytes4 = new byte[]{0, 1, 2, 3};
    final byte[] bytes5 = new byte[]{0, 1, 2, 3, 4};

    config.addByteArray("prop1", bytes0);
    assertArrayEquals(bytes0, config.getByteArray("prop1"));
    assertNotSame(bytes0, config.getByteArray("prop1"));
    assertArrayEquals(new byte[][]{bytes0},
        config.getByteArrays("prop1"));

    config.addByteArray("prop1", bytes1);
    assertArrayEquals(bytes0, config.getByteArray("prop1"));
    assertArrayEquals(new byte[][]{bytes0, bytes1},
        config.getByteArrays("prop1"));

    // set the value of a exist list property
    config.setFinal("prop1", true);
    config.setByteArray("prop1", bytes0);
    assertArrayEquals(bytes0, config.getByteArray("prop1"));
    assertArrayEquals(new byte[][]{bytes0},
        config.getByteArrays("prop1"));
    assertEquals(true, config.isFinal("prop1"));

    // add an array of values
    config.addByteArrays("prop1", bytes1, bytes2);
    assertArrayEquals(new byte[][]{bytes0, bytes1, bytes2},
        config.getByteArrays("prop1"));

    config.addByteArrays("prop2", bytes0);
    assertArrayEquals(new byte[][]{bytes0},
        config.getByteArrays("prop2"));

    config.addByteArrays("prop3", bytes0, bytes1, bytes2);
    assertArrayEquals(new byte[][]{bytes0, bytes1, bytes2},
        config.getByteArrays("prop3"));

    // add a collection of values
    final List<byte[]> list = new LinkedList<byte[]>();
    list.add(bytes3);
    list.add(bytes4);
    list.add(bytes5);

    config.addByteArrays("prop1", list);
    assertArrayEquals(new byte[][]{bytes0, bytes1, bytes2,
        bytes3, bytes4, bytes5},
        config.getByteArrays("prop1"));
  }

  @Test
  public void testGetValue() {

  }

  @Test
  public void testGetValues() {

  }

  @Test
  public void testIsEmpty() {
    final DefaultConfig config = new DefaultConfig ();

    assertEquals(true, config.isEmpty());

    config.setString("prop1", "value1");
    config.setString("prop2", "value2");
    config.setString("prop3", "value3");
    config.setString("prop4", "value4");
    assertEquals(false, config.isEmpty());

    config.removeAll();
    assertEquals(true, config.isEmpty());
  }

  @Test
  public void testSize() {
    final DefaultConfig config = new DefaultConfig ();

    assertEquals(0, config.size());

    config.setString("prop1", "value1");
    config.setString("prop2", "value2");
    config.setString("prop3", "value3");
    config.setString("prop4", "value4");
    config.addString("prop1", "value1-2");
    config.setFinal("prop5", true);

    assertEquals(5, config.size());

    config.removeAll();
    assertEquals(0, config.size());
  }

  @Test
  public void testGetNames() {
    final DefaultConfig config = new DefaultConfig ();
    Set<String> names = null;

    names = config.getNames();
    assertTrue(names.isEmpty());

    config.setString("prop1", "value1");
    config.setString("prop2", "value2");
    config.setString("prop3", "value3");
    config.setString("prop4", "value4");
    assertEquals(4, names.size());

    final Set<String> expectedNames = new HashSet<String>();
    expectedNames.add("prop4");
    expectedNames.add("prop3");
    expectedNames.add("prop1");
    expectedNames.add("prop2");


    assertEquals(expectedNames, names);
  }

  @Test
  public void testContains() {
    final DefaultConfig config = new DefaultConfig ();

    assertEquals(false, config.contains("prop1"));

    config.setString("prop1", "value1");
    config.setString("prop2", "value2");
    config.setString("prop3", "value3");
    config.setString("prop4", "value4");
    assertEquals(true, config.contains("prop1"));
    assertEquals(true, config.contains("prop2"));
    assertEquals(true, config.contains("prop3"));
    assertEquals(true, config.contains("prop4"));
    assertEquals(false, config.contains("prop5"));
    assertEquals(false, config.contains(""));
    assertEquals(false, config.contains(null));
  }

  @Test
  public void testGetValueCount() {
    final DefaultConfig config = new DefaultConfig ();

    assertEquals(0, config.getCount("prop1"));

    config.setString("prop1", "value1-1");
    config.setString("prop2", "value2");
    config.setString("prop3", "value3");
    config.setString("prop4", "value4");
    assertEquals(1, config.getCount("prop1"));
    assertEquals(1, config.getCount("prop2"));
    assertEquals(1, config.getCount("prop3"));
    assertEquals(1, config.getCount("prop4"));
    assertEquals(0, config.getCount("prop5"));
    assertEquals(0, config.getCount(""));
    assertEquals(0, config.getCount(null));

    config.addString("prop1", "value1-2");
    assertEquals(2, config.getCount("prop1"));

    config.addString("prop1", "value1-3");
    assertEquals(3, config.getCount("prop1"));
  }

  @Test
  public void testGetSetFinal() {
    final DefaultConfig config = new DefaultConfig ();

    try {
      config.isFinal("prop1");
      fail("should throw");
    } catch (final ConfigurationError e) {
      // pass
    }

    try {
      config.isFinal("prop2");
      fail("should throw");
    } catch (final ConfigurationError e) {
      // pass
    }

    try {
      config.isFinal("");
      fail("should throw");
    } catch (final ConfigurationError e) {
      // pass
    }

    try {
      config.isFinal(null);
      fail("should throw");
    } catch (final ConfigurationError e) {
      // pass
    }

    config.setFinal("prop1", true);
    config.setFinal("prop2", false);
    config.setFinal("prop3", true);
    config.setFinal("prop4", false);
    assertEquals(true, config.isFinal("prop1"));
    assertEquals(false, config.isFinal("prop2"));
    assertEquals(true, config.isFinal("prop3"));
    assertEquals(false, config.isFinal("prop4"));

    config.setFinal("prop1", false);
    config.setFinal("prop2", true);
    config.setFinal("prop3", false);
    config.setFinal("prop4", true);
    assertEquals(false, config.isFinal("prop1"));
    assertEquals(true, config.isFinal("prop2"));
    assertEquals(false, config.isFinal("prop3"));
    assertEquals(true, config.isFinal("prop4"));
  }

  @Test
  public void testGetSetType() {
    final DefaultConfig config = new DefaultConfig ();

    try {
      config.getType("prop1");
      fail("should throw");
    } catch (final ConfigurationError e) {
      // pass
    }

    try {
      config.getType("");
      fail("should throw");
    } catch (final ConfigurationError e) {
      // pass
    }

    try {
      config.getType(null);
      fail("should throw");
    } catch (final ConfigurationError e) {
      // pass
    }

    config.setFinal("prop1", true);
    assertSame(MultiValues.DEFAULT_TYPE, config.getType("prop1"));

    config.setType("prop1", Type.INT);
    assertSame(Type.INT, config.getType("prop1"));

    config.setType("prop1", Type.BIG_DECIMAL);
    assertSame(Type.BIG_DECIMAL, config.getType("prop1"));

    config.setChar("prop1", 'x');
    assertSame(Type.CHAR, config.getType("prop1"));

    config.setType("prop1", Type.BIG_DECIMAL);
    assertSame(Type.BIG_DECIMAL, config.getType("prop1"));
    assertEquals(0, config.getCount("prop1"));
  }

  @Test
  public void testGetSetDescription() {
    final DefaultConfig config = new DefaultConfig ();

    try {
      config.getDescription("prop1");
      fail("should throw");
    } catch (final ConfigurationError e) {
      // pass
    }

    try {
      config.getDescription("");
      fail("should throw");
    } catch (final ConfigurationError e) {
      // pass
    }

    try {
      config.getDescription(null);
      fail("should throw");
    } catch (final ConfigurationError e) {
      // pass
    }

    config.setFinal("prop1", true);
    config.setDescription("prop2", "description2");
    config.setDescription("prop3", "description3");
    config.setString("prop4", "value4");
    assertEquals(null, config.getDescription("prop1"));
    assertEquals("description2", config.getDescription("prop2"));
    assertEquals("description3", config.getDescription("prop3"));
    assertEquals(null, config.getDescription("prop4"));

    config.setDescription("prop1", "description1-2");
    assertEquals("description1-2", config.getDescription("prop1"));

    try {
      config.setDescription("prop1", null);
    } catch (final NullPointerException e) {
      fail("Should not throw a NullPointerException.");
    }
  }

  @Test
  public void testRemove() {
    final DefaultConfig config = new DefaultConfig ();

    assertEquals(0, config.getCount("prop1"));

    config.setString("prop1", "value1-1");
    config.setString("prop2", "value2");
    config.setString("prop3", "value3");
    config.setString("prop4", "value4");
    assertEquals(1, config.getCount("prop1"));
    assertEquals(1, config.getCount("prop2"));
    assertEquals(1, config.getCount("prop3"));
    assertEquals(1, config.getCount("prop4"));
    assertEquals(0, config.getCount("prop5"));
    assertEquals(0, config.getCount(""));
    assertEquals(0, config.getCount(null));

    config.addString("prop1", "value1-2");
    assertEquals(2, config.getCount("prop1"));

    config.addString("prop1", "value1-3");
    assertEquals(3, config.getCount("prop1"));

    config.remove("prop1");
    assertEquals(false, config.contains("prop1"));
    assertEquals(0, config.getCount("prop1"));

    config.remove("prop2");
    assertEquals(false, config.contains("prop2"));
    assertEquals(0, config.getCount("prop2"));

    config.remove("prop3");
    assertEquals(false, config.contains("prop3"));
    assertEquals(0, config.getCount("prop3"));

    config.remove("prop4");
    assertEquals(false, config.contains("prop4"));
    assertEquals(0, config.getCount("prop4"));

    assertEquals(false, config.contains("prop5"));
    assertEquals(0, config.getCount("prop5"));
    config.remove("prop5");
    assertEquals(false, config.contains("prop5"));
    assertEquals(0, config.getCount("prop5"));

    assertEquals(false, config.contains(""));
    assertEquals(0, config.getCount(""));
    config.remove("");
    assertEquals(false, config.contains(""));
    assertEquals(0, config.getCount(""));

    assertEquals(false, config.contains(null));
    assertEquals(0, config.getCount(null));
    config.remove(null);
    assertEquals(false, config.contains(null));
    assertEquals(0, config.getCount(null));
  }

  @Test
  public void testClear() {
    final DefaultConfig config = new DefaultConfig ();

    assertEquals(0, config.getCount("prop1"));

    config.setString("prop1", "value1-1");
    config.setString("prop2", "value2");
    config.setString("prop3", "value3");
    config.setString("prop4", "value4");
    assertEquals(1, config.getCount("prop1"));
    assertEquals(1, config.getCount("prop2"));
    assertEquals(1, config.getCount("prop3"));
    assertEquals(1, config.getCount("prop4"));
    assertEquals(0, config.getCount("prop5"));
    assertEquals(0, config.getCount(""));
    assertEquals(0, config.getCount(null));

    config.addString("prop1", "value1-2");
    assertEquals(2, config.getCount("prop1"));

    config.addString("prop1", "value1-3");
    assertEquals(3, config.getCount("prop1"));

    config.clear("prop1");
    assertEquals(true, config.contains("prop1"));
    assertEquals(0, config.getCount("prop1"));

    config.clear("prop2");
    assertEquals(true, config.contains("prop2"));
    assertEquals(0, config.getCount("prop2"));

    config.clear("prop3");
    assertEquals(true, config.contains("prop3"));
    assertEquals(0, config.getCount("prop3"));

    config.clear("prop4");
    assertEquals(true, config.contains("prop4"));
    assertEquals(0, config.getCount("prop4"));

    assertEquals(false, config.contains("prop5"));
    assertEquals(0, config.getCount("prop5"));
    config.clear("prop5");
    assertEquals(false, config.contains("prop5"));
    assertEquals(0, config.getCount("prop5"));

    assertEquals(false, config.contains(""));
    assertEquals(0, config.getCount(""));
    config.clear("");
    assertEquals(false, config.contains(""));
    assertEquals(0, config.getCount(""));

    assertEquals(false, config.contains(null));
    assertEquals(0, config.getCount(null));
    config.clear(null);
    assertEquals(false, config.contains(null));
    assertEquals(0, config.getCount(null));

    assertEquals(false, config.isEmpty() );
    config.removeAll();
    assertEquals(true, config.isEmpty() );

    assertEquals(true, config.isEmpty() );
    config.removeAll();
    assertEquals(true, config.isEmpty() );
  }

  private static void addPrefix1(final DefaultConfig config) {
    config.addString("prefix1.prop1", "value1-1");
    config.addString("prefix1.prop1", "value1-2");
    config.addString("prefix1.prop1", "value1-3");
    config.addStrings("prefix1.prop1", "value1-4", "value1-5", "value1-6");
    config.addString("prefix1.prop2", "value2-1");
    config.addString("prefix1.prop2", "value2-2");
    config.setString("prefix1.prop3", "value3-1");
  }

  private static void addPrefix2(final DefaultConfig config) {
    config.addString("prefix2.prop1", "value1-1");
    config.addStrings("prefix2.prop1", "value1-2", "value1-3");
    config.addString("prefix2.prop2", "value2-1");
    config.addString("prefix2.prop3", "value3-1");
    config.addString("prefix2.prop3", "value3-2");
    config.setString("prefix2.prop4", "value4-1");
  }


//  @Test
//  public void testSubset() {
//    final DefaultConfig config = new DefaultConfig ();
//    final DefaultConfig expected = new DefaultConfig();
//    Config subConfig = null;
//
//    subConfig = config.subset("prefix1");
//    assertEquals(true, subConfig.isEmpty());
//
//    addPrefix1(config);
//    addPrefix2(config);
//
//    subConfig = config.subset("prefix1");
//    expected.removeAll();
//    addPrefix1(expected);
////    System.err.println("expected: ");
////    System.err.println(expected.toString());
////    System.err.println("subConfig: ");
////    System.err.println(subConfig.toString());
//    assertEquals(expected, subConfig);
//
//    subConfig = config.subset("prefix2");
//    expected.removeAll();
//    addPrefix2(expected);
//    assertEquals(expected, subConfig);
//  }

  @Test
  public void testAssign() {
    final DefaultConfig modifiableConfig1 = new DefaultConfig();
    final DefaultConfig modifiableConfig2 = new DefaultConfig();
    final Config config1 = modifiableConfig1;
    final Config config2 = modifiableConfig2;

    assertEquals(true, modifiableConfig1.equals(modifiableConfig2));
    assertEquals(true, config1.equals(config2));

    addPrefix1(modifiableConfig1);
    addPrefix2(modifiableConfig2);

    assertEquals(false, modifiableConfig1.equals(modifiableConfig2));
    assertEquals(false, config1.equals(config2));
    assertEquals(true, config1.equals(modifiableConfig1));
    assertEquals(true, modifiableConfig1.equals(config1));
    assertEquals(true, config2.equals(modifiableConfig2));
    assertEquals(true, modifiableConfig2.equals(config2));


    modifiableConfig1.assign(modifiableConfig1);
    assertEquals(true, modifiableConfig1.equals(modifiableConfig1));
    assertEquals(true, config1.equals(config1));

    modifiableConfig1.assign(modifiableConfig2);
    assertEquals(true, modifiableConfig1.equals(modifiableConfig2));
    assertEquals(true, config1.equals(config2));


    modifiableConfig2.removeAll();
    assertEquals(false, modifiableConfig1.equals(modifiableConfig2));
    assertEquals(false, config1.equals(config2));

    modifiableConfig1.assign(modifiableConfig2);
    assertEquals(true, modifiableConfig1.equals(modifiableConfig2));
    assertEquals(true, config1.equals(config2));

  }

  @Test
  public void testClone() {
    final DefaultConfig modifiableConfig1 = new DefaultConfig();
    DefaultConfig modifiableConfig2 = null;
    Config config1 = modifiableConfig1;
    Config config2 = null;

    assertEquals(false, modifiableConfig1.equals(modifiableConfig2));
    assertEquals(false, config1.equals(config2));

    addPrefix1(modifiableConfig1);

    assertEquals(false, modifiableConfig1.equals(modifiableConfig2));
    assertEquals(false, config1.equals(config2));

    modifiableConfig2 = (DefaultConfig) modifiableConfig1.clone();
    assertEquals(true, modifiableConfig1.equals(modifiableConfig2));
    assertNotSame(modifiableConfig1, modifiableConfig2);

    config2 = modifiableConfig1.clone();
    assertEquals(true, modifiableConfig1.equals(config2));
    assertNotSame(modifiableConfig1, config2);

    modifiableConfig1.removeAll();
    assertEquals(false, modifiableConfig1.equals(modifiableConfig2));
    assertEquals(false, modifiableConfig1.equals(config2));

    modifiableConfig2 = (DefaultConfig) modifiableConfig1.clone();
    assertEquals(true, modifiableConfig1.equals(modifiableConfig2));
    assertNotSame(modifiableConfig1, modifiableConfig2);

    config2 = modifiableConfig1.clone();
    assertEquals(true, modifiableConfig1.equals(config2));
    assertNotSame(modifiableConfig1, config2);

    addPrefix1(modifiableConfig1);
    config1 = modifiableConfig1.clone();
    config2 = modifiableConfig1.clone();
    assertEquals(true, config1.equals(config2));
    assertEquals(true, modifiableConfig1.equals(config2));
    assertNotSame(config1, config2);
  }

  @Test
  public void testMerge() {
    final DefaultConfig config1 = new DefaultConfig();
    final DefaultConfig config2 = new DefaultConfig();
    final DefaultConfig expected = new DefaultConfig();

    config1.removeAll();
    config1.addString("prop1", "value1-1");
    config1.addString("prop1", "value1-2");
    config1.addString("prop2", "value2-1");
    config1.setFinal("prop2", true);
    config2.removeAll();
    config2.addString("prop1", "value1-3");
    config2.addString("prop2", "value2-2");
    config2.addString("prop3", "value3-1");
    config1.merge(config2, MergingPolicy.SKIP);
    expected.removeAll();
    expected.addString("prop1", "value1-1");
    expected.addString("prop1", "value1-2");
    expected.addString("prop2", "value2-1");
    expected.setFinal("prop2", true);
    expected.addString("prop3", "value3-1");
    assertEquals(expected, config1);


    config1.removeAll();
    config1.addString("prop1", "value1-1");
    config1.addString("prop1", "value1-2");
    config1.addString("prop2", "value2-1");
    config1.setFinal("prop2", true);
    config2.removeAll();
    config2.addString("prop1", "value1-3");
    config2.addString("prop2", "value2-2");
    config2.addString("prop3", "value3-1");
    config1.merge(config2, MergingPolicy.UNION);
    expected.removeAll();
    expected.addString("prop1", "value1-1");
    expected.addString("prop1", "value1-2");
    expected.addString("prop1", "value1-3");
    expected.addString("prop2", "value2-1");
    expected.setFinal("prop2", true);
    expected.addString("prop3", "value3-1");
    assertEquals(expected, config1);


    config1.removeAll();
    config1.addString("prop1", "value1-1");
    config1.addString("prop1", "value1-2");
    config1.addString("prop2", "value2-1");
    config1.setFinal("prop2", true);
    config2.removeAll();
    config2.addString("prop1", "value1-3");
    config2.addString("prop2", "value2-2");
    config2.addString("prop3", "value3-1");
    config1.merge(config2, MergingPolicy.OVERWRITE);
    expected.removeAll();
    expected.addString("prop1", "value1-3");
    expected.addString("prop2", "value2-1");
    expected.setFinal("prop2", true);
    expected.addString("prop3", "value3-1");
    assertEquals(expected, config1);

  }
}
