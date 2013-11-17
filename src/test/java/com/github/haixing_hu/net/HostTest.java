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

package com.github.haixing_hu.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import junit.framework.TestCase;

import org.junit.Assert;

import com.github.haixing_hu.io.exception.InvalidFormatException;
import com.github.haixing_hu.io.serialize.BinarySerialization;
import com.github.haixing_hu.net.Host;

/**
 * Unit tests for {@link Host}.
 */
public class HostTest extends TestCase {

  public HostTest(final String testName) {
    super(testName);
  }

  public void testConstructor() throws URISyntaxException, MalformedURLException {
    final Host host1 = new Host("somehost");
    assertEquals("somehost", host1.hostname());
    assertEquals(-1, host1.port());
    assertEquals("http", host1.scheme());
    final Host host2 = new Host("somehost", 8080);
    assertEquals("somehost", host2.hostname());
    assertEquals(8080, host2.port());
    assertEquals("http", host2.scheme());
    final Host host3 = new Host("somehost", - 1);
    assertEquals("somehost", host3.hostname());
    assertEquals(-1, host3.port());
    assertEquals("http", host3.scheme());
    final Host host4 = new Host("https", "somehost", 443);
    assertEquals("somehost", host4.hostname());
    assertEquals(443, host4.port());
    assertEquals("https", host4.scheme());
    try {
      new Host(null, null, - 1);
      fail("NullPointerException should have been thrown");
    } catch (final NullPointerException ex) {
      // expected
    }

    //  construct using URL
    Assert.assertEquals(new Host("localhost"),
        new Host(new URL("http://localhost/abcd")));
    Assert.assertEquals(new Host("localhost"),
        new Host(new URL("http://localhost/abcd%3A")));

    Assert.assertEquals(new Host("local_host"),
        new Host(new URL("http://local_host/abcd")));
    Assert.assertEquals(new Host("local_host"),
        new Host(new URL("http://local_host/abcd%3A")));

    Assert.assertEquals(new Host("localhost", 8),
        new Host(new URL("http://localhost:8/abcd")));
    Assert.assertEquals(new Host("local_host", 8),
        new Host(new URL("http://local_host:8/abcd")));

    Assert.assertEquals(new Host("localhost"),
        new Host(new URL("http://localhost:/abcd")));
    Assert.assertEquals(new Host("local_host"),
        new Host(new URL("http://local_host:/abcd")));

    Assert.assertEquals(new Host("localhost", 8080),
        new Host(new URL("http://user:pass@localhost:8080/abcd")));
    Assert.assertEquals(new Host("local_host", 8080),
        new Host(new URL("http://user:pass@local_host:8080/abcd")));

    Assert.assertEquals(new Host("localhost", 8080),
        new Host(new URL("http://@localhost:8080/abcd")));
    Assert.assertEquals(new Host("local_host", 8080),
        new Host(new URL("http://@local_host:8080/abcd")));

    //  construct using URI
    Assert.assertEquals(new Host("localhost"),
        new Host(new URI("http://localhost/abcd")));
    Assert.assertEquals(new Host("localhost"),
        new Host(new URI("http://localhost/abcd%3A")));

    Assert.assertEquals(new Host("local_host"),
        new Host(new URI("http://local_host/abcd")));
    Assert.assertEquals(new Host("local_host"),
        new Host(new URI("http://local_host/abcd%3A")));

    Assert.assertEquals(new Host("localhost", 8),
        new Host(new URI("http://localhost:8/abcd")));
    Assert.assertEquals(new Host("local_host", 8),
        new Host(new URI("http://local_host:8/abcd")));

    // URI seems to OK with missing port number
    Assert.assertEquals(new Host("localhost"),
        new Host(new URI("http://localhost:/abcd")));
    Assert.assertEquals(new Host("local_host"),
        new Host(new URI("http://local_host:/abcd")));

    Assert.assertEquals(new Host("localhost", 8080),
        new Host(new URI("http://user:pass@localhost:8080/abcd")));
    Assert.assertEquals(new Host("local_host", 8080),
        new Host(new URI("http://user:pass@local_host:8080/abcd")));

    Assert.assertEquals(new Host("localhost", 8080),
        new Host(new URI("http://@localhost:8080/abcd")));
    Assert.assertEquals(new Host("local_host", 8080),
        new Host(new URI("http://@local_host:8080/abcd")));
  }

  public void testHashCode() {
    final Host host1 = new Host("http", "somehost", 8080);
    final Host host2 = new Host("http", "somehost", 80);
    final Host host3 = new Host("http", "someotherhost", 8080);
    final Host host4 = new Host("http", "somehost", 80);
    final Host host5 = new Host("http", "SomeHost", 80);
    final Host host6 = new Host("myhttp", "SomeHost", 80);

    assertTrue(host1.hashCode() == host1.hashCode());
    assertTrue(host1.hashCode() != host2.hashCode());
    assertTrue(host1.hashCode() != host3.hashCode());
    assertTrue(host2.hashCode() == host4.hashCode());
    assertTrue(host2.hashCode() == host5.hashCode());
    assertTrue(host5.hashCode() != host6.hashCode());
  }

  public void testEquals() {
    final Host host1 = new Host("http", "somehost", 8080);
    final Host host2 = new Host("http", "somehost", 80);
    final Host host3 = new Host("http", "someotherhost", 8080);
    final Host host4 = new Host("http", "somehost", 80);
    final Host host5 = new Host("http", "SomeHost", 80);
    final Host host6 = new Host("myhttp", "SomeHost", 80);

    assertTrue(host1.equals(host1));
    assertFalse(host1.equals(host2));
    assertFalse(host1.equals(host3));
    assertTrue(host2.equals(host4));
    assertTrue(host2.equals(host5));
    assertFalse(host5.equals(host6));
    assertFalse(host1.equals(null));
    assertFalse(host1.equals("http://somehost"));
  }

  public void testToString() {
    final Host host1 = new Host("somehost");
    assertEquals("http://somehost", host1.toString());
    final Host host2 = new Host("somehost", - 1);
    assertEquals("http://somehost", host2.toString());
    final Host host3 = new Host("somehost", - 1);
    assertEquals("http://somehost", host3.toString());
    final Host host4 = new Host("somehost", 8888);
    assertEquals("http://somehost:8888", host4.toString());
    final Host host5 = new Host("myhttp", "somehost", - 1);
    assertEquals("myhttp://somehost", host5.toString());
    final Host host6 = new Host("myhttp", "somehost", 80);
    assertEquals("myhttp://somehost:80", host6.toString());
  }

  public void testToHostString() {
    final Host host1 = new Host("somehost");
    assertEquals("somehost", host1.toHostString());
    final Host host2 = new Host("somehost");
    assertEquals("somehost", host2.toHostString());
    final Host host3 = new Host("somehost", - 1);
    assertEquals("somehost", host3.toHostString());
    final Host host4 = new Host("somehost", 8888);
    assertEquals("somehost:8888", host4.toHostString());
  }


  public void testSerialization() throws Exception {
    final Host orig = new Host("https", "somehost", 8080);
    final ByteArrayOutputStream outbuffer = new ByteArrayOutputStream();
    final ObjectOutputStream outstream = new ObjectOutputStream(outbuffer);
    outstream.writeObject(orig);
    outstream.close();
    final byte[] raw = outbuffer.toByteArray();
    final ByteArrayInputStream inbuffer = new ByteArrayInputStream(raw);
    final ObjectInputStream instream = new ObjectInputStream(inbuffer);
    final Host clone = (Host) instream.readObject();
    assertEquals(orig, clone);
  }

  public void testBinarySerialization() throws IOException {
    Host host1, host2;
    byte[] data;

    host1 = null;
    data = BinarySerialization.serialize(Host.class, host1);
    host2 = BinarySerialization.deserialize(Host.class, data, true);
    assertEquals(host1, host2);

    host1 = null;
    data = BinarySerialization.serialize(Host.class, host1);
    try {
      host2 = BinarySerialization.deserialize(Host.class, data, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // pass
    }

    host1 = new Host("http", "www.sina.com.cn");
    data = BinarySerialization.serialize(Host.class, host1);
    host2 = BinarySerialization.deserialize(Host.class, data, false);
    assertEquals(host1, host2);

    host1 = new Host("www.sina.com.cn");
    data = BinarySerialization.serialize(Host.class, host1);
    host2 = BinarySerialization.deserialize(Host.class, data, false);
    assertEquals(host1, host2);


    host1 = new Host("http", "www.sina.com.cn", 8080);
    data = BinarySerialization.serialize(Host.class, host1);
    host2 = BinarySerialization.deserialize(Host.class, data, false);
    assertEquals(host1, host2);
  }

}
