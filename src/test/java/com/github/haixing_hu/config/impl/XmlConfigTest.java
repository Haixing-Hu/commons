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
package com.github.haixing_hu.config.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Test;

import com.github.haixing_hu.io.IoUtils;
import com.github.haixing_hu.lang.SystemUtils;
import com.github.haixing_hu.net.Url;
import com.github.haixing_hu.net.UrlUtils;
import com.github.haixing_hu.text.CharsetUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit test for the {@link XmlConfig} class.
 *
 * @author Haixing Hu
 */
public class XmlConfigTest extends ConfigTestBase {

  private static final String PACKAGE_PATH = "com/github/haixing_hu/config/impl/";

  @Test
  public void testDefaultConstructor() {
    final XmlConfig config = new XmlConfig();
    assertNotNull(config);
  }

  @Test
  public void testConstructorString() {
    final XmlConfig config = new XmlConfig(PACKAGE_PATH + BOOLEAN_CONFIG);
    assertNotNull(config);
    verifyBooleanProperties(config);
  }

  @Test
  public void testConstructorStringClass() {
    final XmlConfig config = new XmlConfig(BOOLEAN_CONFIG,
        XmlConfigTest.class);
    assertNotNull(config);
    verifyBooleanProperties(config);
  }

  @Test
  public void testConstructorStringClassLoader() {
    final XmlConfig config = new XmlConfig(PACKAGE_PATH + BOOLEAN_CONFIG,
        XmlConfigTest.class.getClassLoader());
    assertNotNull(config);
    verifyBooleanProperties(config);
  }

  @Test
  public void testConstructorUrl() throws MalformedURLException {
    final URL the_url = SystemUtils.getResource(BOOLEAN_CONFIG,
        XmlConfigTest.class);
    final Url url = new Url(the_url);
    final XmlConfig config = new XmlConfig(url);
    assertNotNull(config);
    verifyBooleanProperties(config);
  }

  @Test
  public void testConstructorURL() throws MalformedURLException {
    final URL url = SystemUtils
        .getResource(BOOLEAN_CONFIG, XmlConfigTest.class);
    final XmlConfig config = new XmlConfig(url);
    assertNotNull(config);
    verifyBooleanProperties(config);
  }

  @Test
  public void testConstructorURI() throws URISyntaxException {
    final URL url = SystemUtils
        .getResource(BOOLEAN_CONFIG, XmlConfigTest.class);
    final XmlConfig config = new XmlConfig(url.toURI());
    assertNotNull(config);
    verifyBooleanProperties(config);
  }

  @Test
  public void testConstructorFile() throws URISyntaxException {
    final URL url = SystemUtils
        .getResource(BOOLEAN_CONFIG, XmlConfigTest.class);
    final File file = new File(url.toURI());
    final XmlConfig config = new XmlConfig(file);
    assertNotNull(config);
    verifyBooleanProperties(config);
  }

  @Test
  public void testConstructorInputStream() throws IOException {
    final URL url = SystemUtils
        .getResource(BOOLEAN_CONFIG, XmlConfigTest.class);
    InputStream in = null;
    try {
      in = UrlUtils.openStream(url);
      final XmlConfig config = new XmlConfig(in);
      assertNotNull(config);
      verifyBooleanProperties(config);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  @Test
  public void testConstructorReader() throws IOException {
    final URL url = SystemUtils
        .getResource(BOOLEAN_CONFIG, XmlConfigTest.class);
    InputStream in = null;
    try {
      in = UrlUtils.openStream(url);
      final InputStreamReader reader = new InputStreamReader(in,
          CharsetUtils.UTF_8);
      final XmlConfig config = new XmlConfig(reader);
      assertNotNull(config);
      verifyBooleanProperties(config);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  @Test
  public void testLoadString() {
    final XmlConfig config = new XmlConfig();
    assertNotNull(config);
    config.addString("xxxx", "hello world");
    config.load(PACKAGE_PATH + BOOLEAN_CONFIG);
    verifyBooleanProperties(config);
  }

  @Test
  public void testLoadStringClass() {
    final XmlConfig config = new XmlConfig();
    assertNotNull(config);
    config.addString("xxxx", "hello world");
    config.load(PACKAGE_PATH + BOOLEAN_CONFIG, XmlConfigTest.class);
    verifyBooleanProperties(config);
  }

  @Test
  public void testLoadStringClassLoader() {
    final XmlConfig config = new XmlConfig();
    assertNotNull(config);
    config.addString("xxxx", "hello world");
    config.load(PACKAGE_PATH + BOOLEAN_CONFIG,
        XmlConfigTest.class.getClassLoader());
    verifyBooleanProperties(config);
  }

  @Test
  public void testLoadUrl() throws MalformedURLException {
    final URL the_url = SystemUtils.getResource(BOOLEAN_CONFIG,
        XmlConfigTest.class);
    final Url url = new Url(the_url);
    final XmlConfig config = new XmlConfig();
    assertNotNull(config);
    config.addString("xxxx", "hello world");
    config.load(url);
    verifyBooleanProperties(config);
  }

  @Test
  public void testLoadURL() throws MalformedURLException {
    final URL url = SystemUtils.getResource(BOOLEAN_CONFIG,
        XmlConfigTest.class);
    final XmlConfig config = new XmlConfig();
    assertNotNull(config);
    config.addString("xxxx", "hello world");
    config.load(url);
    verifyBooleanProperties(config);
  }

  @Test
  public void testLoadURI() throws URISyntaxException {
    final URL url = SystemUtils.getResource(BOOLEAN_CONFIG,
        XmlConfigTest.class);
    final XmlConfig config = new XmlConfig();
    assertNotNull(config);
    config.addString("xxxx", "hello world");
    config.load(url.toURI());
    verifyBooleanProperties(config);
  }

  @Test
  public void testLoadFile() throws URISyntaxException {
    final URL url = SystemUtils.getResource(BOOLEAN_CONFIG,
        XmlConfigTest.class);
    final File file = new File(url.toURI());
    final XmlConfig config = new XmlConfig();
    assertNotNull(config);
    config.addString("xxxx", "hello world");
    config.load(file);
    verifyBooleanProperties(config);
  }

  @Test
  public void testLoadInputStream() throws IOException {
    final URL url = SystemUtils.getResource(BOOLEAN_CONFIG,
        XmlConfigTest.class);
    InputStream in = null;
    try {
      in = UrlUtils.openStream(url);
      final XmlConfig config = new XmlConfig();
      assertNotNull(config);
      config.addString("xxxx", "hello world");
      config.load(in);
      verifyBooleanProperties(config);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  @Test
  public void testLoadReader() throws IOException {
    final URL url = SystemUtils.getResource(BOOLEAN_CONFIG,
        XmlConfigTest.class);
    InputStream in = null;
    try {
      in = UrlUtils.openStream(url);
      final InputStreamReader reader = new InputStreamReader(in,
          CharsetUtils.UTF_8);
      final XmlConfig config = new XmlConfig();
      assertNotNull(config);
      config.addString("xxxx", "hello world");
      config.load(reader);
      verifyBooleanProperties(config);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  @Test
  public void testStoreOutputStream() throws IOException {
    final XmlConfig config = new XmlConfig(STRING_CONFIG,
        XmlConfigTest.class);
    assertNotNull(config);

    final File file = File.createTempFile("test", ".xml");
    final FileOutputStream out = new FileOutputStream(file);
    config.store(out);
    out.close();

    final XmlConfig config2 = new XmlConfig(file);
    assertEquals(config, config2);
  }

  @Test
  public void testStorePrintStream() throws IOException {
    final XmlConfig config = new XmlConfig(STRING_CONFIG,
        XmlConfigTest.class);
    assertNotNull(config);
    final File file = File.createTempFile("test", ".xml");
    final PrintStream out = new PrintStream(file);
    config.store(out);
    out.close();
    final XmlConfig config2 = new XmlConfig(file);
    assertEquals(config, config2);
  }

  @Test
  public void testStoreWriter() throws IOException {
    final XmlConfig config = new XmlConfig(STRING_CONFIG,
        XmlConfigTest.class);
    assertNotNull(config);
    final StringWriter writer = new StringWriter();
    config.store(writer);
    final String str = writer.toString();
    final StringReader reader = new StringReader(str);
    final XmlConfig config2 = new XmlConfig(reader);
    assertEquals(config, config2);
  }

  @Test
  public void testStoreFile() throws IOException {
    final XmlConfig config = new XmlConfig(STRING_CONFIG,
        XmlConfigTest.class);
    assertNotNull(config);

    final File file = File.createTempFile("test", ".xml");
    config.store(file);

    final XmlConfig config2 = new XmlConfig(file);
    assertEquals(config, config2);
  }
}
