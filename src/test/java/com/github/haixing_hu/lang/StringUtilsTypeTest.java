package com.github.haixing_hu.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import com.github.haixing_hu.lang.DoubleUtils;
import com.github.haixing_hu.lang.FloatUtils;

import static com.github.haixing_hu.lang.StringUtils.*;

import static org.junit.Assert.*;

public class StringUtilsTypeTest {

  @Test
  public void testTruncateUtf16() {
    assertEquals("hello", truncateUtf16("hello", 20));
    assertEquals("hell", truncateUtf16("hello", 4));
    assertEquals(null, truncateUtf16(null, 10));
    assertEquals("\\uabcd", truncateUtf16("\\uabcd", 10));
    assertEquals("abcdefghijk", truncateUtf16("abcdefghijk\\uabcd", 11));
    
    try {
      truncateUtf16("hello", -10);
      fail("should throw");
    } catch (IllegalArgumentException e) {
      // pass
    }
  }

  @Test
  public void testTruncateUtf8() {
    assertEquals(null, truncateUtf8(null, 20));
    assertEquals(null, truncateUtf8(null, -10));
    assertEquals("hello", truncateUtf8("hello", 20));
    assertEquals("hell", truncateUtf8("hello", 4));
    assertEquals("hello", truncateUtf8("hello我", 6));
  }

  
  @Test
  public void testToCharArrayString_String_StringBuilder() {
    StringBuilder builderin = new StringBuilder();
    String str = new String();
    String strout = new String();
    
    Object[][] Cases = {
        {"0", "[\\u0030]"},
        {"0x00", "[\\u0030,\\u0078,\\u0030,\\u0030]"},
        {"0xAA", "[\\u0030,\\u0078,\\u0041,\\u0041]"},
        {"0xFF", "[\\u0030,\\u0078,\\u0046,\\u0046]"}
    };
    
    for (Object[] ele : Cases) {
      builderin.setLength(0);
      str = (String) ele[0];
      strout = (String) ele[1];
      toCharArrayString(str, builderin);
      assertEquals(strout, builderin.toString());
    }
  }
  

  @Test
  public void testToCharArrayString_String() {
    String str = new String();
    String strout = new String();
    
    Object[][] Cases = {
        {"0", "[\\u0030]"},
        {"0x00", "[\\u0030,\\u0078,\\u0030,\\u0030]"},
        {"0xAA", "[\\u0030,\\u0078,\\u0041,\\u0041]"},
        {"0xFF", "[\\u0030,\\u0078,\\u0046,\\u0046]"}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      strout = (String) ele[1];
      assertEquals(strout, toCharArrayString(str));
    }
  }

  @Test
  public void testToBoolean_String() {
    String str = new String();
    boolean booleanout;
    
    Object[][] Cases = {
        {null, false},
        {"true", true},
        {"false", false},
        {"  true", true},
        {"false  ", false},
        {"abc", false},
        {"  abc", false}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      booleanout = (Boolean) ele[1];
      assertEquals(booleanout, toBoolean(str));
    }
  }

  @Test
  public void testToBoolean_String_Boolean() {
    String str = new String();
    boolean booleanin;
    boolean booleanout;
    
    Object[][] Cases = {
        {null, true, true},
        {null, false, false},
        {"true", true, true},
        {"true", false, true},
        {"false", true, false},
        {"false", false, false},
        {"  true", true, true},
        {"  true", false, true},
        {"false  ", true, false},
        {"false  ", false, false},
        {"abc", true, true},
        {"  abc", false, false}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      booleanin = (Boolean) ele[1];
      booleanout = (Boolean) ele[2];
      assertEquals(booleanout, toBoolean(str, booleanin));
    }
  }

  @Test
  public void testToBooleanObject_String() {
    String str = new String();
    Boolean booleanout = null;
    
    Object[][] Cases = {
        {null, null},
        {"true", true},
        {"false", false},
        {"  true", true},
        {"false  ", false},
        {"abc", null},
        {"  abc", null}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      booleanout = (Boolean) ele[1];
      assertEquals(booleanout, toBooleanObject(str));
    }
  }

  @Test
  public void testToBooleanObject_String_Boolean() {
    String str = new String();
    Boolean booleanin;
    Boolean booleanout;
    
    Object[][] Cases = {
        {"true", true, true},
        {"true", false, true},
        {"true", null, true},
        
        {"false", true, false},
        {"false", false, false},
        {"false", null, false},
        
        {"  true", true, true},
        {"  true", false, true},
        {"  true", null, true},
        
        {"false  ", true, false},
        {"false  ", false, false},
        {"false  ", null, false},
        
        {"abc", true, true},
        {"  abc", false, false},
        {"abc", null, null},
        
        {null, true, true},
        {null, false, false},
        {null, null, null}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      booleanin = (Boolean) ele[1];
      booleanout = (Boolean) ele[2];
      assertEquals(booleanout, toBooleanObject(str, booleanin));
    }
  }

  @Test
  public void testToChar_String() {
    String str = new String();
    char charout = (char) 0;
    
    Object[][] Cases = {
        {null, (Character) (char) 0},
        {"", (Character) (char) 0},
        {"abc", (Character) 'a'}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      charout = (Character) ele[1];
      assertEquals(charout, toChar(str));
    }
  }

  @Test
  public void testToChar_String_Char() {
    String str = new String();
    char charin = (char) 0;
    char charout = (char) 0;
    
    Object[][] Cases = {
        {null, (Character) (char) 0, (Character) (char) 0},
        {"", (Character) (char) 0, (Character) (char) 0},
        {null, (Character) 'a', (Character) 'a'},
        {"", (Character) 'b', (Character) 'b'},
        {"abc", (Character) 'a', (Character) (char) 'a'},
        {"abc", (Character) 'f', (Character) (char) 'a'}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      charin = (Character) ele[1];
      charout = (Character) ele[2];
      assertEquals(charout, toChar(str, charin));
    }
  }

  @Test
  public void testToCharObject_String() {
    String str = new String();
    Character charout = (char) 0;
    
    Object[][] Cases = {
        {null, null},
        {"", null},
        {"abc", (Character) (char) 'a'}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      charout = (Character) ele[1];
      assertEquals(charout, toCharObject(str));
    }
  }

  @Test
  public void testToCharObject_String_Character() {
    String str = new String();
    Character charin = (char) 0;
    Character charout = (char) 0;
    
    Object[][] Cases = {
        {null, (Character) (char) 0, (Character) (char) 0},
        {null, (Character) 'a', (Character) 'a'},
        {null, null, null},
        {"", (Character) (char) 0, (Character) (char) 0},
        {"", (Character) 'b', (Character) 'b'},
        {"", null, null},
        {"abc", (Character) 'a', (Character) (char) 'a'},
        {"abc", (Character) 'f', (Character) (char) 'a'},
        {"abc", null, (Character) (char) 'a'}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      charin = (Character) ele[1];
      charout = (Character) ele[2];
      assertEquals(charout, toCharObject(str, charin));
    }
  }

  @Test
  public void testToByte_String() {
    String str = new String();
    byte byteout = (byte) 0;
    
    Object[][] Cases = {
        {null, (byte) 0},
        {"0", (byte) 0},
        {"abc", (byte) 0},
        {"123", (byte) 123}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      byteout = (Byte) ele[1];
      assertEquals(byteout, toByte(str));
    }
  }

  @Test
  public void testToByte_String_Byte() {
    String str = new String();
    byte bytein= (byte) 0;
    byte byteout = (byte) 0;
    
    Object[][] Cases = {
        {null, (byte) 0, (byte) 0},
        {"0", (byte) 0, (byte) 0},
        {"abc", (byte) 20, (byte) 20},
        {"123", (byte) 0, (byte) 123},
        {"12f", (byte) 0, (byte) 12f},
        {"0x12", (byte) 0, (byte) 0x12}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      bytein = (Byte) ele[1];
      byteout = (Byte) ele[2];
      assertEquals(byteout, toByte(str, bytein));
    }
  }

  @Test
  public void testToByteObject_String() {
    String str = new String();
    Byte byteout = (byte) 0;
    
    Object[][] Cases = {
        {null, null},
        {"0", (byte) 0},
        {"abc", null},
        {"123", (byte) 123},
        {"12f", (byte) 12f},
        {"0x12", (byte) 0x12}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      byteout = (Byte) ele[1];
      assertEquals(byteout, toByteObject(str));
    }
  }

  @Test
  public void testToByteObject_String_Byte() {
    String str = new String();
    Byte bytein = (byte) 0;
    Byte byteout = (byte) 0;
    
    Object[][] Cases = {
        {null, null, null},
        {null, (byte) 0, (byte) 0},
        {"0", (byte) 0, (byte) 0},
        {"abc", (byte) 20, (byte) 20},
        {"abc", null, null},
        {"123", (byte) 0, (byte) 123},
        {"12f", (byte) 0, (byte) 12f},
        {"0x12", (byte) 0, (byte) 0x12}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      bytein = (Byte) ele[1];
      byteout = (Byte) ele[2];
      assertEquals(byteout, toByteObject(str, bytein));
    }
  }

  @Test
  public void testToShort_String() {
    String str = new String();
    short shortout = (short) 0;
    
    Object[][] Cases = {
        {null, (short) 0},
        {"0",  (short) 0},
        {"abc", (short) 0},
        {"123", (short) 123},
        {"12f", (short) 12f},
        {"0x12", (short) 0x12}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      shortout = (Short) ele[1];
      assertEquals(shortout, toShort(str));
    }
  }

  @Test
  public void testToShort_String_Short() {
    String str = new String();
    short shortin = (short) 0;
    short shortout = (short) 0;
    
    Object[][] Cases = {
        {null, (short) 0, (short) 0},
        {"0", (short) 0, (short) 0},
        {"abc", (short) 20, (short) 20},
        {"123", (short) 0, (short) 123},
        {"12f", (short) 0, (short) 12f},
        {"0x12", (short) 0, (short) 0x12}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      shortin = (Short) ele[1];
      shortout = (Short) ele[2];
      assertEquals(shortout, toShort(str, shortin));
    }
  }

  @Test
  public void testToShortObject_String() {
    String str = new String();
    Short shortout = (short) 0;
    
    Object[][] Cases = {
        {null, null},
        {"0",  (short) 0},
        {"abc", null},
        {"123", (short) 123},
        {"12f", (short) 12f},
        {"0x12", (short) 0x12}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      shortout = (Short) ele[1];
      assertEquals(shortout, toShortObject(str));
    }
  }

  @Test
  public void testToShortObjectStringShort() {
    String str = new String();
    Short shortin = (short) 0;
    Short shortout = (short) 0;
    
    Object[][] Cases = {
        {null, null, null},
        {null, (short) 0, (short) 0},
        {"0", (short) 0, (short) 0},
        {"abc", (short) 20, (short) 20},
        {"abc", null, null},
        {"123", (short) 0, (short) 123},
        {"12f", (short) 0, (short) 12f},
        {"0x12", (short) 0, (short) 0x12}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      shortin = (Short) ele[1];
      shortout = (Short) ele[2];
      assertEquals(shortout, toShortObject(str, shortin));
    }
  }

  @Test
  public void testToInt_String() {
    String str = new String();
    int intout = 0;
    
    Object[][] Cases = {
        {null, 0},
        {"0123", 83},
        {"abc", 0},
        {"23f", (int) 23f},
        {"0x23", 0x23}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      intout = (Integer) ele[1];
      assertEquals(intout, toInt(str));
    }
  }

  @Test
  public void testToInt_String_Int() {
    String str = new String();
    int intin = 0;
    int intout = 0;
    
    Object[][] Cases = {
        {null, 20, 20},
        {"abc", 20, 20},
        {"123", 12, 123},
        {"23f", 23, (int) 23f},
        {"0x23", 34, 35}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      intin = (Integer) ele[1];
      intout = (Integer) ele[2];
      assertEquals(intout, toInt(str, intin));
    }
  }

  @Test
  public void testToIntObject_String() {
    String str = new String();
    Integer intout = 0;
    
    Object[][] Cases = {
        {null, null},
        {"0123", 83},
        {"abc", null},
        {"23f", (int) 23f},
        {"0x23", 0x23}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      intout = (Integer) ele[1];
      assertEquals(intout, toIntObject(str));
    }
  }

  @Test
  public void testToIntObject_String_Integer() {
    String str = new String();
    Integer intin = 0;
    Integer intout = 0;
    
    Object[][] Cases = {
        {null, 20, 20},
        {null, null, null},
        {"abc", 20, 20},
        {"abc", null, null},
        {"123", 12, 123},
        {"23f", 23, (int) 23f},
        {"0x23", 34, 35}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      intin = (Integer) ele[1];
      intout = (Integer) ele[2];
      assertEquals(intout, toIntObject(str, intin));
    }
  }

  @Test
  public void testToLong_String() {
    String str = new String();
    long longout = (long) 0;
    
    Object[][] Cases = {
        {null, (long) 0},
        {"12", (long) 12},
        {"abc", (long) 0},
        {"23f", (long) 23f},
        {"0x23", (long) 35} 
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      longout = (Long) ele[1];
      assertEquals(longout, toLong(str));
    }
  }

  @Test
  public void testToLongStringLong() {
    String str = new String();
    long longin = (long) 0;
    long longout = (long) 0;
    
    Object[][] Cases = {
        {null, (long) 20, (long) 20},
        {"12", (long) 20, (long) 12},
        {"abc", (long) 0, (long) 0},
        {"23f", (long) 3, (long) 23f},
        {"0x23", (long) 3, (long) 35} 
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      longin = (Long) ele[1];
      longout = (Long) ele[2];
      assertEquals(longout, toLong(str, longin));
    }
  }

  @Test
  public void testToLongObject_String() {
    String str = new String();
    Long longout = (long) 0;
    
    Object[][] Cases = {
        {null, null},
        {"12", (long) 12},
        {"abc", null},
        {"23f", (long) 23f},
        {"0x23", (long) 35} 
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      longout = (Long) ele[1];
      assertEquals(longout, toLongObject(str));
    }
  }

  @Test
  public void testToLongObjectStringLong() {
    String str = new String();
    Long longin = (long) 0;
    Long longout = (long) 0;
    
    Object[][] Cases = {
        {null, null, null},
        {null, (long) 20, (long) 20},
        {"12", (long) 20, (long) 12},
        {"abc", null, null},
        {"abc", (long) 0, (long) 0},
        {"23f", (long) 3, (long) 23f},
        {"0x23", (long) 3, (long) 35} 
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      longin = (Long) ele[1];
      longout = (Long) ele[2];
      assertEquals(longout, toLongObject(str, longin));
    }
  }

  @Test
  public void testToFloat_String() {
    String str = new String();
    float floatout = (float) 0;
    
    Object[][] Cases = {
        {null, (float) 0},
        {"abc", (float) 0},
        {"23", (float) 23},
        {"23f", 23f},
        {"2.3f", 2.3f},
        {"3e-2", 0.03f}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      floatout = (Float) ele[1];
      assertEquals(floatout, toFloat(str), FloatUtils.EPSILON);
    }
  }

  @Test
  public void testToFloat_String_Float() {
    String str = new String();
    float floatin = (float) 0;
    float floatout = (float) 0;
    
    Object[][] Cases = {
        {null, (float) 0, (float) 0},
        {"abc", (float) 0, (float) 0},
        {"123", (float) 20, (float) 123},
        {"23f", (float) 20, (float) 23f}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      floatin = (Float) ele[1];
      floatout = (Float) ele[2];
      assertEquals(floatout, toFloat(str, floatin), FloatUtils.EPSILON);
    }
  }

  @Test
  public void testToFloatObject_String() {
    String str = new String();
    Float floatout = (float) 0;
    
    Object[][] Cases = {
        {null, null},
        {"abc", null},
        {"23", (float) 23},
        {"23f", 23f}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      floatout = (Float) ele[1];
      assertEquals(floatout, toFloatObject(str));
    }
  }

  @Test
  public void testToFloatObject_String_Float() {
    String str = new String();
    Float floatin = (float) 0;
    Float floatout = (float) 0;
    
    Object[][] Cases = {
        {null, null, null},
        {null, (float) 0, (float) 0},
        {"abc", null, null},
        {"abc", (float) 0, (float) 0},
        {"123", (float) 20, (float) 123},
        {"23f", (float) 20, (float) 23f}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      floatin = (Float) ele[1];
      floatout = (Float) ele[2];
      assertEquals(floatout, toFloatObject(str, floatin));
    }
  }

  @Test
  public void testToDouble_String() {
    String str = new String();
    double doubleout = (double) 0;
    
    Object[][] Cases = {
        {null, (double) 0},
        {"abc", (double) 0},
        {"123", (double) 123},
        {"23f", (double) 23f}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      doubleout = (Double) ele[1];
      assertEquals(doubleout, toDouble(str), DoubleUtils.EPSILON);
    }
  }

  @Test
  public void testToDouble_String_Double() {
    String str = new String();
    double doublein = (double) 0;
    double doubleout = (double) 0;
    
    Object[][] Cases = {
        {null, (double) 123, (double) 123},
        {"abc", (double) 123, (double) 123},
        {"123", (double) 23, (double) 123},
        {"23f", (double) 23, (double) 23f}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      doublein = (Double) ele[1];
      doubleout = (Double) ele[2];
      assertEquals(doubleout, toDouble(str, doublein), DoubleUtils.EPSILON);
    }
  }

  @Test
  public void testToDoubleObject_String() {
    String str = new String();
    Double doubleout = (double) 0;
    
    Object[][] Cases = {
        {null, null},
        {"abc", null},
        {"123", (double) 123},
        {"23f", (double) 23f}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      doubleout = (Double) ele[1];
      assertEquals(doubleout, toDoubleObject(str));
    }
  }

  @Test
  public void testToDoubleObject_String_Double() {
    String str = new String();
    Double doublein = (double) 0;
    Double doubleout = (double) 0;
    
    Object[][] Cases = {
        {null, null, null},
        {null, (double) 23, (double) 23},
        {"abc", null, null},
        {"abc", (double) 23, (double) 23},
        {"123", (double) 23, (double) 123},
        {"123f", (double) 23, (double) 123f}
    };
    
    for (Object[] ele : Cases) {
      str = (String) ele[0];
      doublein = (Double) ele[1];
      doubleout = (Double) ele[2];
      assertEquals(doubleout, toDoubleObject(str, doublein));
    }
  }

  @Test
  public void testToDate_String() {
    String str = new String();
    Date dateout = new Date();
    
    str = null;
    dateout = null;
    assertEquals(dateout, toDate(str));
    
    str = "";
    dateout = null;
    assertEquals(dateout, toDate(str));
    
    str = "123";
    dateout = null;
    assertEquals(dateout, toDate(str));
    
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTimeInMillis(0);
    cal.set(2012, 0, 12, 0, 0, 0);
    str = "2012-01-12 00:00:00";
    dateout = cal.getTime();
    assertEquals(dateout, toDate(str));
  }

  @Test
  public void testToDateStringDate() {
    String str = null;
    Date datein = null;
    
    assertEquals(null, toDate(str, datein));
    
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTimeInMillis(0);
    cal.set(2012, 0, 12, 0, 0, 0);
    Date dateout = cal.getTime();
    assertEquals(dateout, toDate(str, dateout));
    
    str = "2012-01-12 00:00:00";
    assertEquals(dateout, toDate(str, datein));
  }

  @Test
  public void testToClass_String() {
    assertEquals(null, toClass(null));
    assertEquals(null, toClass(""));
    
    String str = new String();
    Class<?> classout = null;
    assertEquals(classout, toClass(str));

    str = "Integer";
    classout = null;
    assertEquals(classout, toClass(str));
    
    str = "java.lang.Integer";
    Integer x = (Integer) 2;
    classout = x.getClass();
    assertEquals(classout, toClass(str));
  }

  @Test
  public void testToClassStringClassOfQ() {
    String str = null;
    Class<?> classin = null;
    assertEquals(null, toClass(str, classin));
    
    str = null;
    Integer x = (Integer) 2;
    classin = x.getClass();
    assertEquals(classin, toClass(str, classin));
    
    str = "Integer";
    classin = null;
    assertEquals(null, toClass(str, classin));
    
    str = "java.lang.Float";
    Float y = (Float) 0.2f;
    Class<?> classout = y.getClass();
    assertEquals(classout, toClass(str, classin));
  }

  @Test
  public void testToByteArray_String() {
    assertArrayEquals(null, toByteArray(null));
    assertArrayEquals(null, toByteArray(""));
    
    String str = "0x00AA";
    final byte[] xa = {(byte) 0x00, (byte) 0xAA};
    assertArrayEquals(xa, toByteArray(str));
    
    str = "0x00FF";
    final byte[] xb = {(byte) 0x00, (byte) 0xFF};
    assertArrayEquals(xb, toByteArray(str));
    
    str = "0xAAAA";
    final byte[] xc = {(byte) 0xAA, (byte) 0xAA};
    assertArrayEquals(xc, toByteArray(str));
    
    str = "0xFFFF";
    final byte[] xd = {(byte) 0xFF, (byte) 0xFF};
    assertArrayEquals(xd, toByteArray(str));
  }

  @Test
  public void testToByteArray_String_ByteArray() {
    assertArrayEquals(null, toByteArray(null, null));
    assertArrayEquals(null, toByteArray("", null));
    
    byte[] bain = {(byte) 0xff};
    byte[] baout = {(byte) 0xff};
    assertArrayEquals(baout, toByteArray(null, bain));
    assertArrayEquals(baout, toByteArray("", bain));
    
    String str = "0x00AA";
    final byte[] xa = {(byte) 0x00, (byte) 0xAA};
    final byte[] ya = {(byte) 0xff};
    assertArrayEquals(xa, toByteArray(str, ya));
    
    str = "0x00FF";
    final byte[] xb = {(byte) 0x00, (byte) 0xFF};
    final byte[] yb = {(byte) 0xff};
    assertArrayEquals(xb, toByteArray(str, yb));
    
    str = "0xAAAA";
    final byte[] xc = {(byte) 0xAA, (byte) 0xAA};
    final byte[] yc = {(byte) 0xff};
    assertArrayEquals(xc, toByteArray(str, yc));
    
    str = "0xFFFF";
    final byte[] xd = {(byte) 0xFF, (byte) 0xFF};
    final byte[] yd = {(byte) 0xff};
    assertArrayEquals(xd, toByteArray(str, yd));
  }

  @Test
  public void testToBigInteger_String() {
    String str = new String();
    BigInteger biout;
    
    str = null;
    biout = null;
    assertEquals(biout, toBigInteger(str));
    
    str = "";
    biout = null;
    assertEquals(biout, toBigInteger(str));
    
    str = "20";
    biout = BigInteger.valueOf((long)20);
    assertEquals(biout, toBigInteger(str));
  }

  @Test
  public void testToBigIntegerStringBigInteger() {
    String str = new String();
    BigInteger biin;
    BigInteger biout;
    
    str = null;
    biin = null;
    biout = biin;
    assertEquals(biout, toBigInteger(str, biin));
    
    str = "";
    biin = null;
    biout = biin;
    assertEquals(biout, toBigInteger(str, biin));
    
    str = null;
    biin = BigInteger.valueOf((long) 20);
    biout = biin;
    assertEquals(biout, toBigInteger(str, biin));
    
    str = "";
    biin = BigInteger.valueOf((long) 20);
    biout = biin;
    assertEquals(biout, toBigInteger(str, biin));
    
    str = "20";
    biin = null;
    biout = BigInteger.valueOf((long) 20);
    assertEquals(biout, toBigInteger(str, biin));
    
    str = "20";
    biin = BigInteger.valueOf((long) 10);
    biout = BigInteger.valueOf((long) 20);
    assertEquals(biout, toBigInteger(str, biin));
  }

  @Test
  public void testToBigDecimal_String() {
    String str = new String();
    BigDecimal bdout;
    
    str = null;
    bdout = null;
    assertEquals(bdout, toBigDecimal(str));
    
    str = "";
    bdout = null;
    assertEquals(bdout, toBigDecimal(str));
    
    str = "20";
    bdout = BigDecimal.valueOf((long) 20);
    assertEquals(bdout, toBigDecimal(str));
  }

  @Test
  public void testToBigDecimal_String_BigDecimal() {
    String str = new String();
    BigDecimal bdin;
    BigDecimal bdout;
    
    str = null;
    bdin = null;
    bdout = null;
    assertEquals(bdout, toBigDecimal(str, bdin));
    
    str = null;
    bdin = BigDecimal.valueOf((long) 20);
    bdout = bdin;
    assertEquals(bdout, toBigDecimal(str, bdin));
    
    str = "";
    bdin = null;
    bdout = null;
    assertEquals(bdout, toBigDecimal(str, bdin));
    
    str = "";
    bdin = BigDecimal.valueOf((long) 20);
    bdout = bdin;
    assertEquals(bdout, toBigDecimal(str, bdin));
    
    str = "20";
    bdin = null;
    bdout = BigDecimal.valueOf((long) 20);
    assertEquals(bdout, toBigDecimal(str, bdin));
    
    str = "20";
    bdin = BigDecimal.valueOf((long) 10);
    bdout = BigDecimal.valueOf((long) 20);
    assertEquals(bdout, toBigDecimal(str, bdin));
  }

  @Test
  public void testToXmlNode() {
    //TODO
  }

}
