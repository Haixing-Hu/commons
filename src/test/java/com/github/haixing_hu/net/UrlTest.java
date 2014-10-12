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
package com.github.haixing_hu.net;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import com.github.haixing_hu.net.Url;

import static org.junit.Assert.assertEquals;

/**
 * Unit test of the Url class.
 *
 * @author Haixing Hu
 */
public class UrlTest {

  @Test
  public void testUrl() throws MalformedURLException {
    Url url = null;

    url = new Url();
    assertEquals(true, url.isEmpty());

// FIXME: implement the parser by ourself
//    url = new Url("http://www.baidu.com/s?wd=\"波司登\"\"简介\"+site%3Anews.eastday.com");
//    assertEquals(false, url.isEmpty());

    url = new Url(
        "hTTp://starfish:123@mirror1.NEWs.sina.com.cn:80////tt/./../../../../2010/06/01/current.html?id=123#top");
    assertEquals(false, url.isEmpty());
    assertEquals(
        "http://starfish:123@mirror1.news.sina.com.cn/2010/06/01/current.html?id=123#top",
        url.toString());
    assertEquals("http", url.scheme());
    assertEquals("starfish:123", url.userInfo());
    assertEquals("mirror1.news.sina.com.cn", url.hostname());
    assertEquals("/2010/06/01/current.html", url.path());
    assertEquals("id=123", url.query());
    assertEquals("top", url.fragment());
    assertEquals(-1, url.port());

    // check that leading and trailing spaces are removed
    url = new Url(" http://foo.com/ ");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    // check that scheme is lower cased
    url = new Url(" Http://foo.com/ ");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    // check that host is lower cased
    url = new Url(" http://Foo.Com/Index.html");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/Index.html", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/Index.html", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    // check that port number is normalized
    url = new Url(" http://Foo.Com:80/Index.html");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/Index.html", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/Index.html", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    url = new Url(" http://Foo.Com:81/Index.html");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com:81/Index.html", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/Index.html", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(81, url.port());

    // check that null path is normalized
    url = new Url(" http://Foo.Com:80");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    // check that escaped octets is normalized
    url = new Url("http://foo.com/%66oo%20bar.html");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/foo bar.html", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/foo bar.html", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    // check that unnecessary "../" are removed
    url = new Url("http://foo.com/aa/../");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    url = new Url("http://foo.com/aa/bb/../");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/aa/", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/aa/", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    // assertEquals("http://foo.com/aa/..",
    // normalizer.transform("http://foo.com/aa/.."));

    url = new Url("http://foo.com/aa/bb/cc/../../foo.html");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/aa/foo.html", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/aa/foo.html", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    url = new Url("http://foo.com/aa/bb/../cc/dd/../ee/foo.html?id=/../abc//");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/aa/cc/ee/foo.html?id=/../abc//", url
        .toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/aa/cc/ee/foo.html", url.path());
    assertEquals("id=/../abc//", url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    url = new Url("http://foo.com/../foo.html");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/foo.html", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/foo.html", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    url = new Url("http://foo.com/../../foo.html");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/foo.html", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/foo.html", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    url = new Url("http://foo.com/../aa/../foo.html");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/foo.html", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/foo.html", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    url = new Url("http://foo.com/aa/../../foo.html");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/foo.html", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/foo.html", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    url = new Url("http://foo.com/aa/../bb/../foo.html/../../");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    url = new Url("http://foo.com/../aa/foo.html");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/aa/foo.html", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/aa/foo.html", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    url = new Url("http://foo.com/../aa/../foo.html");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/foo.html", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/foo.html", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    url = new Url("http://foo.com/../a..a/foo.html");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/a..a/foo.html", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/a..a/foo.html", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    url = new Url("http://foo.com/../a..a/../foo.html");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/foo.html", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/foo.html", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    url = new Url("http://foo.com/../a..a/../bb/././foo.html");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/bb/foo.html", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/bb/foo.html", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    url = new Url("http://foo.com/foo/./foo.html");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/foo/foo.html", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/foo/foo.html", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    url = new Url("http://foo.com/foo.././foo.html");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/foo../foo.html", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/foo../foo.html", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

    url = new Url("http://foo.com/foo.././///foo.html");
    assertEquals(false, url.isEmpty());
    assertEquals("http://foo.com/foo../foo.html", url.toString());
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("foo.com", url.hostname());
    assertEquals("/foo../foo.html", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
    assertEquals(-1, url.port());

  }

  @Test
  public void testUrlFromURI() throws URISyntaxException {
    URI uri = null;
    Url url = null;

    uri = new URI("file:///home/user/dir");
    url = new Url(uri);
    assertEquals("file", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("", url.hostname());
    assertEquals(-1, url.port());
    assertEquals("/home/user/dir", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());

    uri = new URI("http://localhost/abcd");
    url = new Url(uri);
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("localhost", url.hostname());
    assertEquals(-1, url.port());
    assertEquals("/abcd", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());

    uri = new URI("http://local_host/abcd");
    url = new Url(uri);
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("local_host", url.hostname());
    assertEquals(-1, url.port());
    assertEquals("/abcd", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());

    uri = new URI("http://local_host:8080/abcd");
    url = new Url(uri);
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("local_host", url.hostname());
    assertEquals(8080, url.port());
    assertEquals("/abcd", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());

    uri = new URI("http://user:password@local_host:8080/abcd");
    url = new Url(uri);
    assertEquals("http", url.scheme());
    assertEquals("user:password", url.userInfo());
    assertEquals("local_host", url.hostname());
    assertEquals(8080, url.port());
    assertEquals("/abcd", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());


    uri = new URI("http://local_host:/abcd");
    url = new Url(uri);
    assertEquals("http", url.scheme());
    assertEquals(null, url.userInfo());
    assertEquals("local_host", url.hostname());
    assertEquals(-1, url.port());
    assertEquals("/abcd", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());

    uri = new URI("http://@local_host:/abcd");
    url = new Url(uri);
    assertEquals("http", url.scheme());
    assertEquals("", url.userInfo());
    assertEquals("local_host", url.hostname());
    assertEquals(-1, url.port());
    assertEquals("/abcd", url.path());
    assertEquals(null, url.query());
    assertEquals(null, url.fragment());
  }

  // FIXME

//
//  @Test
//  public void testJdbcURL() throws Exception {
//    Url url = new Url("jdbc:oracle://172.18.81.1:8080/database");
//    assertEquals("172.18.81.1", url.host());
//    assertEquals(8080, url.host());
//    assertEquals("database", url.path());
//    assertEquals("jdbc:oracle", url.scheme());
//  }

}
