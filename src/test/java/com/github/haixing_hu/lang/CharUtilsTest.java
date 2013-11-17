/******************************************************************************
 *
 *    Copyright (c) 2009-2012  Ascent Dimension, Inc. All rights reserved.
 *
 ******************************************************************************/

package com.github.haixing_hu.lang;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.github.haixing_hu.collection.primitive.IntIterator;
import com.github.haixing_hu.collection.primitive.impl.ArrayIntList;
import com.github.haixing_hu.lang.BooleanUtils;
import com.github.haixing_hu.lang.ByteUtils;
import com.github.haixing_hu.lang.CharUtils;
import com.github.haixing_hu.lang.DoubleUtils;
import com.github.haixing_hu.lang.FloatUtils;
import com.github.haixing_hu.lang.IntUtils;
import com.github.haixing_hu.lang.LongUtils;
import com.github.haixing_hu.lang.ShortUtils;

import static org.junit.Assert.*;

import static com.github.haixing_hu.lang.CharUtils.*;

/**
 * Unit test of the Chars class.
 *
 * @author Haixing Hu
 */
public class CharUtilsTest {

  // CHAR_CONST[i] is the string consisted of the characters whose type is i
  static final ArrayIntList[] CHAR_CONST;

  static {
    final Map<Integer, ArrayIntList> map = new HashMap<Integer, ArrayIntList>();
    int maxType = -1;
    for (int ch = 0; ch <= 0x10FFFF; ++ch) {
      final int type = Character.getType(ch);
      if (type > maxType) {
        maxType = type;
      }
      ArrayIntList list = map.get(type);
      if (list == null) {
        list = new ArrayIntList();
        map.put(type, list);
      }
      list.add(ch);
    }

    CHAR_CONST = new ArrayIntList[maxType + 1];
    for (final Map.Entry<Integer, ArrayIntList> entry : map.entrySet()) {
      final Integer type = entry.getKey();
      final ArrayIntList list = entry.getValue();
      CHAR_CONST[type.intValue()] = list;
    }
  }

  @Test
  public void testIsGraph_int() {
    for (int type = 0; type < CHAR_CONST.length; ++type) {
      final ArrayIntList char_const = CHAR_CONST[type];
      if (char_const == null) {
        continue;
      }
      boolean expected;
      switch (type) {
        case Character.CONTROL:
        case Character.FORMAT:
        case Character.SURROGATE:
        case Character.UNASSIGNED:
        case Character.SPACE_SEPARATOR:
        case Character.LINE_SEPARATOR:
        case Character.PARAGRAPH_SEPARATOR:
          expected = false;
          break;
        default:
          expected = true;
          break;
      }
      final IntIterator iter = char_const.iterator();
      while (iter.hasNext()) {
        final int ch = iter.next();
        final boolean actual = isGraph(ch);
        assertEquals(expected, actual);
      }
    }
  }

  @Test
  public void testIsBlank_int() {
    for (int type = 0; type < CHAR_CONST.length; ++type) {
      final ArrayIntList char_const = CHAR_CONST[type];
      if (char_const == null) {
        continue;
      }
      boolean expected;
      switch (type) {
        case Character.CONTROL:
        case Character.FORMAT:
        case Character.SURROGATE:
        case Character.UNASSIGNED:
        case Character.SPACE_SEPARATOR:
        case Character.LINE_SEPARATOR:
        case Character.PARAGRAPH_SEPARATOR:
          expected = true;
          break;
        default:
          expected = false;
          break;
      }
      final IntIterator iter = char_const.iterator();
      while (iter.hasNext()) {
        final int ch = iter.next();
        final boolean actual = isBlank(ch);
        assertEquals(expected, actual);
      }
    }
  }

  @Test
  public void testIsInlineBlank_int() {
    for (int type = 0; type < CHAR_CONST.length; ++type) {
      final ArrayIntList char_const = CHAR_CONST[type];
      if (char_const == null) {
        continue;
      }
      boolean expected;
      switch (type) {
        case Character.CONTROL:
        case Character.FORMAT:
        case Character.SURROGATE:
        case Character.UNASSIGNED:
        case Character.SPACE_SEPARATOR:
          expected = true;
          break;
        default:
          expected = false;
          break;
      }
      final IntIterator iter = char_const.iterator();
      while (iter.hasNext()) {
        final int ch = iter.next();
        final boolean actual = isInlineBlank(ch);
        if ((ch == '\n')
            || (ch == '\r')
            || (ch == '\u001C')
            || (ch == '\u001D')
            || (ch == '\u001E')
            || (ch == '\u0085')
            || (ch == '\u2028')
            || (ch == '\u2029')) {
          assertEquals(false, actual);
        } else {
          assertEquals(expected, actual);
        }
      }
    }
  }

  @Test
  public void testIsLineBreak_int() {
    assertEquals(true, CharUtils.isLineBreak('\r'));
    assertEquals(true, CharUtils.isLineBreak('\n'));
    assertEquals(false, CharUtils.isLineBreak(0x007F));
    assertEquals(false, CharUtils.isLineBreak(0x0083));
    assertEquals(false, CharUtils.isLineBreak(0x000C));
    assertEquals(false, CharUtils.isLineBreak(0x0020));
  }

  @Test
  public void testGetVisibility_int() {
    for (int type = 0; type < CHAR_CONST.length; ++type) {
      final ArrayIntList char_const = CHAR_CONST[type];
      if (char_const == null) {
        continue;
      }
      int expected = -1;
      switch (type) {
        case Character.CONTROL:
        case Character.FORMAT:
        case Character.SURROGATE:
        case Character.UNASSIGNED:
        case Character.SPACE_SEPARATOR:
          expected = VISIBILITY_INLINE_BLANK;
          break;
        case Character.LINE_SEPARATOR:
        case Character.PARAGRAPH_SEPARATOR:
          expected = VISIBILITY_LINE_BREAK;
          break;
        default:
          expected = VISIBILITY_GRAPH;
          break;
      }
      final IntIterator iter = char_const.iterator();
      while (iter.hasNext()) {
        final int ch = iter.next();
        final int actual = getVisibility(ch);
        if ((ch == '\n')
            || (ch == '\r')
            || (ch == '\u001C')
            || (ch == '\u001D')
            || (ch == '\u001E')
            || (ch == '\u0085')
            || (ch == '\u2028')
            || (ch == '\u2029')) {
          assertEquals(VISIBILITY_LINE_BREAK, actual);
        } else {
          assertEquals(expected, actual);
        }
      }
    }
  }

  @Test
  public void testToPrimitive_Character() {
    assertEquals(CharUtils.DEFAULT,CharUtils.toPrimitive(null));
    assertEquals('A', CharUtils.toPrimitive(new Character('A')));
    assertSame(toPrimitive(new Character('A')), toPrimitive(new Character('A')));
    assertEquals('B', CharUtils.toPrimitive(new Character('B')));
    assertSame(toPrimitive(new Character('B')), toPrimitive(new Character('B')));
  }

  @Test
  public void testToPrimitive_Character_char() {
    assertEquals('A', toPrimitive(null, 'A'));
    assertEquals('B', toPrimitive(null, 'B'));
    assertEquals('B', toPrimitive(new Character('B'), 'A'));
    assertEquals('A', toPrimitive(new Character('A'), 'B'));
  }

  @Test
  public void testToBoolean_char() {
    assertEquals(true, toBoolean('a'));
    assertEquals(true, toBoolean('0'));
    assertEquals(true, toBoolean('\n'));
    assertEquals(false, toBoolean('\0'));
  }

  @Test
  public void testToBoolean_Character() {
    assertEquals(BooleanUtils.DEFAULT, toBoolean(null));
    assertEquals(true, toBoolean(new Character('a')));
    assertEquals(true, toBoolean(new Character('0')));
    assertEquals(true, toBoolean(new Character('\n')));
    assertEquals(false, toBoolean(new Character('\0')));
  }

  @Test
  public void testToBooleanObject_char() {
    assertEquals(new Boolean(true), toBooleanObject('a'));
    assertEquals(new Boolean(true), toBooleanObject('0'));
    assertEquals(new Boolean(true), toBooleanObject('\n'));
    assertEquals(new Boolean(false), toBooleanObject('\0'));
  }

  @Test
  public void testToBooleanObject_Character() {
    assertEquals(null, toBooleanObject(null));
    assertEquals(new Boolean(true), toBooleanObject(new Character('a')));
    assertEquals(new Boolean(true), toBooleanObject(new Character('0')));
    assertEquals(new Boolean(true), toBooleanObject(new Character('\n')));
    assertEquals(new Boolean(false), toBooleanObject(new Character('\0')));
  }

  @Test
  public void testToBooleanObject_Character_Boolean() {
    Character charin = new Character('\0');
    Boolean boolin = new Boolean(null);
    Boolean boolout = new Boolean(null);

    final Object[][] Cases = {
      {null, null, null},
      {null, true, true},
      {null, false, false},
      {'a', true, true},
      {'0', false, true},
      {'\n', false, true},
      {'\0', true, false}
    };

    for (final Object[] ele : Cases) {
      charin = (Character) ele[0];
      boolin = (Boolean) ele[1];
      boolout = (Boolean) ele[2];
      assertEquals(boolout, toBooleanObject(charin, boolin));
    }
  }

  @Test
  public void testToByte_Char() {
    assertEquals((byte) 0, toByte('\0'));
    assertEquals((byte) 49, toByte('1'));
    assertEquals((byte) 65, toByte('A'));
    assertEquals((byte) 97, toByte('a'));
  }

  @Test
  public void testToByte_Character() {
    assertEquals(ByteUtils.DEFAULT, toByte(null));
    assertEquals((byte) 0, toByte(new Character('\0')));
    assertEquals((byte) 49, toByte(new Character('1')));
    assertEquals((byte) 65, toByte(new Character('A')));
    assertEquals((byte) 97, toByte(new Character('a')));
  }

  @Test
  public void testToByte_Character_byte() {
    assertEquals((byte) 49, toByte(null, (byte) 49));
    assertEquals((byte) 0, toByte(new Character('\0'), (byte) 49));
    assertEquals((byte) 49, toByte(new Character('1'), (byte) 30));
    assertEquals((byte) 65, toByte(new Character('A'), (byte) 49));
    assertEquals((byte) 97, toByte(new Character('a'), (byte) 49));
  }

  @Test
  public void testToByteObject_char() {
    assertEquals(new Byte((byte) 0), toByteObject('\0'));
    assertEquals(new Byte((byte) 49), toByteObject('1'));
    assertEquals(new Byte((byte) 65), toByteObject('A'));
    assertEquals(new Byte((byte) 97), toByteObject('a'));
  }

  @Test
  public void testToByteObject_Character() {
    assertEquals(null, toByteObject(null));
    assertEquals(new Byte((byte) 0), toByteObject(new Character('\0')));
    assertEquals(new Byte((byte) 49), toByteObject(new Character('1')));
    assertEquals(new Byte((byte) 65), toByteObject(new Character('A')));
    assertEquals(new Byte((byte) 97), toByteObject(new Character('a')));
  }

  @Test
  public void testToByteObject_Character_Byte() {
    Character charin = new Character('\0');
    Byte bytein = new Byte((byte) 0);
    Byte byteout= new Byte((byte) 0);

    final Object[][] Cases = {
      {null, null, null},
      {null, (byte) 0, (byte) 0},
      {null, (byte) 20, (byte) 20},
      {'\0', (byte) 20, (byte) 0},
      {'1', (byte) 20, (byte) 49},
      {'A', (byte) 20, (byte) 65},
      {'a', (byte) 20, (byte) 97}
    };

    for (final Object[] ele : Cases) {
      charin = (Character) ele[0];
      bytein = (Byte) ele[1];
      byteout = (Byte) ele[2];
      assertEquals(byteout, toByteObject(charin, bytein));
    }
  }

  @Test
  public void testToShort_char() {
    assertEquals((short) 0, toShort('\0'));
    assertEquals((short) 49, toShort('1'));
    assertEquals((short) 65, toShort('A'));
    assertEquals((short) 97, toShort('a'));
  }

  @Test
  public void testToShort_Character() {
    assertEquals(ShortUtils.DEFAULT, toShort(null));
    assertEquals((short) 0, toShort(new Character('\0')));
    assertEquals((short) 49, toShort(new Character('1')));
    assertEquals((short) 65, toShort(new Character('A')));
    assertEquals((short) 97, toShort(new Character('a')));
  }

  @Test
  public void testToShort_Character_short() {
    assertEquals((short) 49, toShort(null, (short) 49));
    assertEquals((short) 0, toShort(new Character('\0'), (short) 49));
    assertEquals((short) 49, toShort(new Character('1'), (short) 30));
    assertEquals((short) 65, toShort(new Character('A'), (short) 49));
    assertEquals((short) 97, toShort(new Character('a'), (short) 49));
  }

  @Test
  public void testToShortObject_char() {
    assertEquals(new Short((short) 0), toShortObject('\0'));
    assertEquals(new Short((short) 49), toShortObject('1'));
    assertEquals(new Short((short) 65), toShortObject('A'));
    assertEquals(new Short((short) 97), toShortObject('a'));
  }

  @Test
  public void testToShortObject_Character() {
    assertEquals(null, toShortObject(null));
    assertEquals(new Short((short) 0), toShortObject(new Character('\0')));
    assertEquals(new Short((short) 49), toShortObject(new Character('1')));
    assertEquals(new Short((short) 65), toShortObject(new Character('A')));
    assertEquals(new Short((short) 97), toShortObject(new Character('a')));
  }

  @Test
  public void testToShortObject_Character_Short() {
    Character charin = new Character('\0');
    Short shortin = new Short((short) 0);
    Short shortout= new Short((short) 0);

    final Object[][] Cases = {
      {null, null, null},
      {null, (short) 0, (short) 0},
      {null, (short) 20, (short) 20},
      {'\0', (short) 20, (short) 0},
      {'1', (short) 20, (short) 49},
      {'A', (short) 20, (short) 65},
      {'a', (short) 20, (short) 97}
    };

    for (final Object[] ele : Cases) {
      charin = (Character) ele[0];
      shortin = (Short) ele[1];
      shortout = (Short) ele[2];
      assertEquals(shortout, toShortObject(charin, shortin));
    }
  }

  @Test
  public void testToInt_char() {
    assertEquals(0, toInt('\0'));
    assertEquals(49, toInt('1'));
    assertEquals(65, toInt('A'));
    assertEquals(97, toInt('a'));
  }

  @Test
  public void testToInt_Character() {
    assertEquals(IntUtils.DEFAULT, toShort(null));
    assertEquals(0, toInt(new Character('\0')));
    assertEquals(49, toInt(new Character('1')));
    assertEquals(65, toInt(new Character('A')));
    assertEquals(97, toInt(new Character('a')));
  }

  @Test
  public void testToInt_Character_Int() {
    assertEquals(49, toInt(null, 49));
    assertEquals(0, toInt(new Character('\0'), 49));
    assertEquals(49, toInt(new Character('1'), 30));
    assertEquals(65, toInt(new Character('A'), 49));
    assertEquals(97, toInt(new Character('a'), 49));
  }

  @Test
  public void testToIntObject_char() {
    assertEquals(new Integer(0), toIntObject('\0'));
    assertEquals(new Integer(49), toIntObject('1'));
    assertEquals(new Integer(65), toIntObject('A'));
    assertEquals(new Integer(97), toIntObject('a'));
  }

  @Test
  public void testToIntObject_Character() {
    assertEquals(null, toIntObject(null));
    assertEquals(new Integer(0), toIntObject(new Character('\0')));
    assertEquals(new Integer(49), toIntObject(new Character('1')));
    assertEquals(new Integer(65), toIntObject(new Character('A')));
    assertEquals(new Integer(97), toIntObject(new Character('a')));
  }

  @Test
  public void testToIntObject_Character_Short() {
    Character charin = new Character('\0');
    Integer intin = new Integer(0);
    Integer intout= new Integer(0);

    final Object[][] Cases = {
      {null, null, null},
      {null, 0, 0},
      {null, 20, 20},
      {'\0', 20, 0},
      {'1', 20, 49},
      {'A', 20, 65},
      {'a', 20, 97}
    };

    for (final Object[] ele : Cases) {
      charin = (Character) ele[0];
      intin = (Integer) ele[1];
      intout = (Integer) ele[2];
      assertEquals(intout, toIntObject(charin, intin));
    }
  }

  @Test
  public void testToLong_char() {
    assertEquals(0, toLong('\0'));
    assertEquals(49, toLong('1'));
    assertEquals(65, toLong('A'));
    assertEquals(97, toLong('a'));
  }

  @Test
  public void testToLong_Character() {
    assertEquals(LongUtils.DEFAULT, toLong(null));
    assertEquals(0, toLong(new Character('\0')));
    assertEquals(49, toLong(new Character('1')));
    assertEquals(65, toLong(new Character('A')));
    assertEquals(97, toLong(new Character('a')));
  }

  @Test
  public void testToLong_Character_long() {
    assertEquals(49, toLong(null, 49));
    assertEquals(0, toLong(new Character('\0'), 49));
    assertEquals(49, toLong(new Character('1'), 30));
    assertEquals(65, toLong(new Character('A'), 49));
    assertEquals(97, toLong(new Character('a'), 49));
  }

  @Test
  public void testToLongObject_char() {
    assertEquals(new Long(0), toLongObject('\0'));
    assertEquals(new Long(49), toLongObject('1'));
    assertEquals(new Long(65), toLongObject('A'));
    assertEquals(new Long(97), toLongObject('a'));
  }

  @Test
  public void testToLongObject_Character() {
    assertEquals(null, toLongObject(null));
    assertEquals(new Long(0), toLongObject(new Character('\0')));
    assertEquals(new Long(49), toLongObject(new Character('1')));
    assertEquals(new Long(65), toLongObject(new Character('A')));
    assertEquals(new Long(97), toLongObject(new Character('a')));
  }

  @Test
  public void testToLongObject_Character_Long() {
    Character charin = new Character('\0');
    Long longin = new Long(0);
    Long longout= new Long(0);

    final Object[][] Cases = {
      {null, null, null},
      {null, (long) 0, (long) 0},
      {null, (long) 20, (long) 20},
      {'\0', (long) 20, (long) 0},
      {'1', (long) 20, (long) 49},
      {'A', (long) 20, (long) 65},
      {'a', (long) 20, (long) 97}
    };

    for (final Object[] ele : Cases) {
      charin = (Character) ele[0];
      longin = (Long) ele[1];
      longout = (Long) ele[2];
      assertEquals(longout, toLongObject(charin, longin));
    }
  }

  @Test
  public void testToFloat_char() {
    assertEquals(0, toFloat('\0'), FloatUtils.EPSILON);
    assertEquals(49, toFloat('1'), FloatUtils.EPSILON);
    assertEquals(65, toFloat('A'), FloatUtils.EPSILON);
    assertEquals(97, toFloat('a'), FloatUtils.EPSILON);
  }

  @Test
  public void testToFloat_Character() {
    assertEquals(FloatUtils.DEFAULT, toFloat(null), FloatUtils.EPSILON);
    assertEquals(0, toFloat(new Character('\0')), FloatUtils.EPSILON);
    assertEquals(49, toFloat(new Character('1')), FloatUtils.EPSILON);
    assertEquals(65, toFloat(new Character('A')), FloatUtils.EPSILON);
    assertEquals(97, toFloat(new Character('a')), FloatUtils.EPSILON);
  }

  @Test
  public void testToFloat_Character_float() {

    assertEquals(49, toFloat(null, 49), FloatUtils.EPSILON);
    assertEquals(0, toFloat(new Character('\0'), 49),
        FloatUtils.EPSILON);
    assertEquals(49, toFloat(new Character('1'), 30),
        FloatUtils.EPSILON);
    assertEquals(65, toFloat(new Character('A'), 49),
        FloatUtils.EPSILON);
    assertEquals(97, toFloat(new Character('a'), 49),
        FloatUtils.EPSILON);
  }

  @Test
  public void testToFloatObject_char() {
    assertEquals((Float) 0.0f, toFloatObject('\0'));
    assertEquals((Float) 49.0f, toFloatObject('1'));
    assertEquals((Float) 65.0f, toFloatObject('A'));
    assertEquals((Float)97.0f, toFloatObject('a'));
  }

  @Test
  public void testToFloatObject_Character() {
    assertEquals(null, toFloatObject(null));
    assertEquals((Float)0.0f, toFloatObject(new Character('\0')));
    assertEquals((Float)49.0f, toFloatObject(new Character('1')));
    assertEquals((Float)65.0f, toFloatObject(new Character('A')));
    assertEquals((Float)97.0f, toFloatObject(new Character('a')));
  }

  @Test
  public void testToFloatObject_Character_Float() {
    Character charin = new Character('\0');
    Float floatin = 0.0f;
    Float floatout= 0.0f;

    final Object[][] Cases = {
      {null, null, null},
      {null, (float) 0, (float) 0},
      {null, (float) 20, (float) 20},
      {'\0', (float) 20, (float) 0},
      {'1', (float) 20, (float) 49},
      {'A', (float) 20, (float) 65},
      {'a', (float) 20, (float) 97}
    };

    for (final Object[] ele : Cases) {
      charin = (Character) ele[0];
      floatin = (Float) ele[1];
      floatout = (Float) ele[2];
      assertEquals(floatout, toFloatObject(charin, floatin));
    }
  }

  @Test
  public void testToDouble_char() {
    assertEquals(0, toDouble('\0'), DoubleUtils.EPSILON);
    assertEquals(49, toDouble('1'), DoubleUtils.EPSILON);
    assertEquals(65, toDouble('A'), DoubleUtils.EPSILON);
    assertEquals(97, toDouble('a'), DoubleUtils.EPSILON);
  }

  @Test
  public void testToDouble_Character() {
    assertEquals(DoubleUtils.DEFAULT, toDouble(null), DoubleUtils.EPSILON);
    assertEquals(0, toDouble(new Character('\0')),
        DoubleUtils.EPSILON);
    assertEquals(49, toDouble(new Character('1')),
        DoubleUtils.EPSILON);
    assertEquals(65, toDouble(new Character('A')),
        DoubleUtils.EPSILON);
    assertEquals(97, toDouble(new Character('a')),
        DoubleUtils.EPSILON);
  }

  @Test
  public void testToDouble_Character_float() {

    assertEquals(49, toDouble(null, 49),
        DoubleUtils.EPSILON);
    assertEquals(0, toDouble(new Character('\0'), 49),
        DoubleUtils.EPSILON);
    assertEquals(49, toDouble(new Character('1'), 30),
        DoubleUtils.EPSILON);
    assertEquals(65, toDouble(new Character('A'), 49),
        DoubleUtils.EPSILON);
    assertEquals(97, toDouble(new Character('a'), 49),
        DoubleUtils.EPSILON);
  }

  @Test
  public void testToDoubleObject_char() {
    assertEquals(new Double(0), toDoubleObject('\0'));
    assertEquals(new Double(49), toDoubleObject('1'));
    assertEquals(new Double(65), toDoubleObject('A'));
    assertEquals(new Double(97), toDoubleObject('a'));
  }

  @Test
  public void testToDoubleObject_Character() {
    assertEquals(null, toFloatObject(null));
    assertEquals(new Double(0), toDoubleObject(new Character('\0')));
    assertEquals(new Double(49), toDoubleObject(new Character('1')));
    assertEquals(new Double(65), toDoubleObject(new Character('A')));
    assertEquals(new Double(97), toDoubleObject(new Character('a')));
  }

  @Test
  public void testToDoubleObject_Character_Double() {
    Character charin = new Character('\0');
    Double doublein = new Double(0);
    Double doubleout= new Double(0);

    final Object[][] Cases = {
      {null, null, null},
      {null, (double) 0, (double) 0},
      {null, (double) 20, (double) 20},
      {'\0', (double) 20, (double) 0},
      {'1', (double) 20, (double) 49},
      {'A', (double) 20, (double) 65},
      {'a', (double) 20, (double) 97}
    };

    for (final Object[] ele : Cases) {
      charin = (Character) ele[0];
      doublein = (Double) ele[1];
      doubleout = (Double) ele[2];
      assertEquals(doubleout, toDoubleObject(charin, doublein));
    }
  }

  @Test
  public void testToString_char() {
    assertEquals("a", CharUtils.toString('a'));
    assertSame(CharUtils.toString('a'), CharUtils.toString('a'));

    for (int i = 0; i < 128; i++) {
      final String str = CharUtils.toString((char) i);
      final String str2 = CharUtils.toString((char) i);
      assertSame(str, str2);
      assertEquals(1, str.length());
      assertEquals(i, str.charAt(0));
    }
    for (int i = 128; i < 196; i++) {
      final String str = CharUtils.toString((char) i);
      final String str2 = CharUtils.toString((char) i);
      assertEquals(str, str2);
      assertTrue(str != str2);
      assertEquals(1, str.length());
      assertEquals(i, str.charAt(0));
      assertEquals(1, str2.length());
      assertEquals(i, str2.charAt(0));
    }
  }

  @Test
  public void testToString_Character() {
    assertEquals(null, CharUtils.toString(null));
    assertEquals("A", CharUtils.toString(new Character('A')));
    assertSame(CharUtils.toString(new Character('A')),
        CharUtils.toString(new Character('A')));
    assertEquals("B", CharUtils.toString(new Character('B')));
    assertSame(CharUtils.toString(new Character('B')),
        CharUtils.toString(new Character('B')));
  }

  @Test
  public void testToUnicodeEscape_int() {
    assertEquals("\\u0041", CharUtils.toUnicodeEscape(65));

    for (int i = 0; i < 196; i++) {
      final String str = CharUtils.toUnicodeEscape((char) i);
      assertEquals(6, str.length());
      final int val = Integer.parseInt(str.substring(2), 16);
      assertEquals(i, val);
    }
    assertEquals("\\u0999", CharUtils.toUnicodeEscape((char) 0x999));
    assertEquals("\\u1001", CharUtils.toUnicodeEscape((char) 0x1001));
  }

  @Test
  public void testToUnicodeEscape_int_StringBuilder() {
    final StringBuilder builder = new StringBuilder();
    CharUtils.toUnicodeEscape(65, builder);
    assertEquals("\\u0041", builder.toString());
  }

  @Test
  public void testToUnicodeEscape_Character() {
    assertEquals(null, CharUtils.toUnicodeEscape(null));
    assertEquals("\\u0041", CharUtils.toUnicodeEscape(new Character('A')));
  }

  @Test
  public void testToUnicodeEscape_Character_StringBuilder() {
    final StringBuilder builder = new StringBuilder();
    builder.setLength(0);
    CharUtils.toUnicodeEscape(new Character('A'), builder);
    assertEquals("\\u0041", builder.toString() );

    builder.setLength(0);
    CharUtils.toUnicodeEscape(new Character('B'), builder);
    assertEquals("\\u0042", builder.toString());

    builder.setLength(0);
    CharUtils.toUnicodeEscape(new Character('C'), builder);
    assertEquals("\\u0043", builder.toString());
  }

}
