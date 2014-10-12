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
package com.github.haixing_hu.util.buffer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.github.haixing_hu.util.buffer.ByteBuffer;
import com.github.haixing_hu.util.buffer.CharBuffer;

import junit.framework.TestCase;

/**
 * Unit tests for {@link CharBuffer}.
 */
public class CharArrayTest extends TestCase {

  public CharArrayTest(final String testName) {
    super(testName);
  }

  public void testConstructor() throws Exception {
    final CharBuffer buffer = new CharBuffer(16);
    assertEquals(16, buffer.capacity());
    assertEquals(0, buffer.length());
    assertNotNull(buffer.buffer());
    assertEquals(16, buffer.buffer().length);
    try {
      new CharBuffer(- 1);
      fail("IllegalArgumentException should have been thrown");
    } catch (final IllegalArgumentException ex) {
      // expected
    }
  }

  public void testSimpleAppend() throws Exception {
    final CharBuffer buffer = new CharBuffer(16);
    assertEquals(16, buffer.capacity());
    assertEquals(0, buffer.length());
    final char[] b1 = buffer.toArray();
    assertNotNull(b1);
    assertEquals(0, b1.length);
    assertTrue(buffer.isEmpty());
    assertFalse(buffer.isFull());

    final char[] tmp = new char[] { '1', '2', '3', '4' };
    buffer.append(tmp, 0, tmp.length);
    assertEquals(16, buffer.capacity());
    assertEquals(4, buffer.length());
    assertFalse(buffer.isEmpty());
    assertFalse(buffer.isFull());

    final char[] b2 = buffer.toArray();
    assertNotNull(b2);
    assertEquals(4, b2.length);
    for (int i = 0; i < tmp.length; i++) {
      assertEquals(tmp[i], b2[i]);
      assertEquals(tmp[i], buffer.at(i));
    }
    assertEquals("1234", buffer.toString());

    buffer.clear();
    assertEquals(16, buffer.capacity());
    assertEquals(0, buffer.length());
    assertTrue(buffer.isEmpty());
    assertFalse(buffer.isFull());
  }

  public void testExpandAppend() throws Exception {
    final CharBuffer buffer = new CharBuffer(4);
    assertEquals(4, buffer.capacity());

    final char[] tmp = new char[] { '1', '2', '3', '4' };
    buffer.append(tmp, 0, 2);
    buffer.append(tmp, 0, 4);
    buffer.append(tmp, 0, 0);

    assertEquals(6, buffer.length());

    buffer.append(tmp, 0, 4);

    assertEquals(10, buffer.length());

    assertEquals("1212341234", buffer.toString());
  }

  public void testAppendString() throws Exception {
    final CharBuffer buffer = new CharBuffer(8);
    buffer.append("stuff");
    buffer.append(" and more stuff");
    assertEquals("stuff and more stuff", buffer.toString());
  }

  public void testAppendNullString() throws Exception {
    final CharBuffer buffer = new CharBuffer(8);
    buffer.append((String) null);
    assertEquals("", buffer.toString());
  }

  public void testAppendCharArray() throws Exception {
    final CharBuffer buffer1 = new CharBuffer(8);
    buffer1.append(" and more stuff");
    final CharBuffer buffer2 = new CharBuffer(8);
    buffer2.append("stuff");
    buffer2.append(buffer1);
    assertEquals("stuff and more stuff", buffer2.toString());
  }

  public void testAppendNullCharArray() throws Exception {
    final CharBuffer buffer = new CharBuffer(8);
    buffer.append((CharBuffer) null);
    buffer.append((CharBuffer) null, 0, 0);
    assertEquals("", buffer.toString());
  }

  public void testAppendSingleChar() throws Exception {
    final CharBuffer buffer = new CharBuffer(4);
    buffer.append('1');
    buffer.append('2');
    buffer.append('3');
    buffer.append('4');
    buffer.append('5');
    buffer.append('6');
    assertEquals("123456", buffer.toString());
  }

  public void testInvalidCharArrayAppend() throws Exception {
    final CharBuffer buffer = new CharBuffer(4);
    buffer.append((char[]) null, 0, 0);

    final char[] tmp = new char[] { '1', '2', '3', '4' };
    try {
      buffer.append(tmp, - 1, 0);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.append(tmp, 0, - 1);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.append(tmp, 0, 8);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.append(tmp, 10, Integer.MAX_VALUE);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.append(tmp, 2, 4);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
  }

  public void testSetLength() throws Exception {
    final CharBuffer buffer = new CharBuffer(4);
    buffer.setLength(2);
    assertEquals(2, buffer.length());
  }

  public void testSetInvalidLength() throws Exception {
    final CharBuffer buffer = new CharBuffer(4);
    try {
      buffer.setLength(- 2);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
//    try {
//      buffer.setLength(200);
//      fail("IndexOutOfBoundsException should have been thrown");
//    } catch (final IndexOutOfBoundsException ex) {
//      // expected
//    }
  }

  public void testEnsureCapacity() throws Exception {
    final CharBuffer buffer = new CharBuffer(4);
    buffer.reserve(2, true);
    assertEquals(4, buffer.capacity());
    buffer.reserve(8, false);
    assertEquals(8, buffer.capacity());
  }

  public void testIndexOf() {
    final CharBuffer buffer = new CharBuffer(16);
    buffer.append("name: value");
    assertEquals(4, buffer.indexOf(':'));
    assertEquals(- 1, buffer.indexOf(','));
    assertEquals(4, buffer.indexOf(':', - 1, 11));
    assertEquals(4, buffer.indexOf(':', 0, 1000));
    assertEquals(- 1, buffer.indexOf(':', 2, 1));
  }

  public void testSubstring() {
    final CharBuffer buffer = new CharBuffer(16);
    buffer.append(" name:  value    ");
    assertEquals(5, buffer.indexOf(':'));
    assertEquals(" name", buffer.substring(0, 5));
    assertEquals("  value    ", buffer.substring(6, buffer.length()));
    assertEquals("name", buffer.substringTrimmed(0, 5));
    assertEquals("value", buffer.substringTrimmed(6, buffer.length()));
    assertEquals("", buffer.substringTrimmed(13, buffer.length()));
  }

  public void testSubstringIndexOfOutBound() {
    final CharBuffer buffer = new CharBuffer(16);
    buffer.append("stuff");
    try {
      buffer.substring(- 2, 10);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.substringTrimmed(- 2, 10);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.substring(12, 10);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.substringTrimmed(12, 10);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.substring(2, 1);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.substringTrimmed(2, 1);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
  }

  public void testAppendAsciiByteArray() throws Exception {
    final String s1 = "stuff";
    final String s2 = " and more stuff";
    final byte[] b1 = s1.getBytes("US-ASCII");
    final byte[] b2 = s2.getBytes("US-ASCII");

    final CharBuffer buffer = new CharBuffer(8);
    buffer.append(b1, 0, b1.length);
    buffer.append(b2, 0, b2.length);

    assertEquals("stuff and more stuff", buffer.toString());
  }

  public void testAppendISOByteArray() throws Exception {
    final byte[] b = new byte[] { 0x00, 0x20, 0x7F, - 0x80, - 0x01 };

    final CharBuffer buffer = new CharBuffer(8);
    buffer.append(b, 0, b.length);
    final char[] ch = buffer.toArray();
    assertNotNull(ch);
    assertEquals(5, ch.length);
    assertEquals(0x00, ch[0]);
    assertEquals(0x20, ch[1]);
    assertEquals(0x7F, ch[2]);
    assertEquals(0x80, ch[3]);
    assertEquals(0xFF, ch[4]);
  }

  public void testAppendNullByteArray() throws Exception {
    final CharBuffer buffer = new CharBuffer(8);
    buffer.append((byte[]) null, 0, 0);
    assertEquals("", buffer.toString());
  }

  public void testAppendNullByteArrayBuffer() throws Exception {
    final CharBuffer buffer = new CharBuffer(8);
    buffer.append((ByteBuffer) null, 0, 0);
    assertEquals("", buffer.toString());
  }

  public void testInvalidAppendAsciiByteArray() throws Exception {
    final CharBuffer buffer = new CharBuffer(4);
    buffer.append((byte[]) null, 0, 0);

    final byte[] tmp = new byte[] { '1', '2', '3', '4' };
    try {
      buffer.append(tmp, - 1, 0);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.append(tmp, 0, - 1);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.append(tmp, 0, 8);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.append(tmp, 10, Integer.MAX_VALUE);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.append(tmp, 2, 4);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
  }

  public void testSerialization() throws Exception {
    final CharBuffer orig = new CharBuffer(32);
    orig.append('a');
    orig.append('b');
    orig.append('c');
    final ByteArrayOutputStream outbuffer = new ByteArrayOutputStream();
    final ObjectOutputStream outstream = new ObjectOutputStream(outbuffer);
    outstream.writeObject(orig);
    outstream.close();
    final byte[] raw = outbuffer.toByteArray();
    final ByteArrayInputStream inbuffer = new ByteArrayInputStream(raw);
    final ObjectInputStream instream = new ObjectInputStream(inbuffer);
    final CharBuffer clone = (CharBuffer) instream.readObject();
    assertEquals(orig.capacity(), clone.capacity());
    assertEquals(orig.length(), clone.length());
    final char[] data = clone.toArray();
    assertNotNull(data);
    assertEquals(3, data.length);
    assertEquals('a', data[0]);
    assertEquals('b', data[1]);
    assertEquals('c', data[2]);
  }

}
