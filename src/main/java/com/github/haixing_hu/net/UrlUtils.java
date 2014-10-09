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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provides the utility functions for handing URLs.
 *
 * TODO: add the test function of IPv6 address.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class UrlUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(UrlUtils.class);

  public static final String USER_AGENT = "MSIE 8.0";

  /**
   * Tests whether a host name is an IPv4 address.
   *
   * @param hostName
   *          a host name.
   * @return true if the host name is an IPv4 address; false otherwise.
   */
  public static boolean isIPv4Address(@Nonnull final String hostName) {
    // check the first three tuples
    int pos = 0;
    for (int i = 0; i < 3; ++i) {
      final int dot = hostName.indexOf('.', pos);
      if (!isIPv4Tuple(hostName, pos, dot)) {
        return false;
      }
      pos = dot + 1;
    }
    // check the last tuple
    return isIPv4Tuple(hostName, pos, hostName.length());
  }

  /**
   * Tests whether a substring of a string is a valid IPv4 address tuple.
   *
   * A valid IPv4 address tuple is a string of a decimal number between [0,
   * 255].
   *
   * @param str
   *          a string.
   * @param start
   *          the start current of the substring.
   * @param end
   *          the exclusive end current of the substring.
   * @return true if the substring is a valid IPv4 address tuple.
   */
  private static boolean isIPv4Tuple(final String str, final int start, final int end) {
    if (start >= end) {
      return false;
    }
    int value = 0;
    for (int i = start; i < end; ++i) {
      final char ch = str.charAt(i);
      if ((ch < '0') || (ch > '9')) {
        return false;
      }
      value *= 10;
      value += (ch - '0');
      if (value > 255) {
        return false;
      }
    }
    return value <= 255;
  }

  /**
   * Given a host name, returns the domain name of the host name. The domain of
   * a host is the substring of the host name without the subdomain names.
   *
   * For example,
   *
   * {@code 
   *    UrlUtils.getDomain("www.google.com")    = "google.com"
   *    UrlUtils.getDomain("www.sina.com.cn")   = "sina.com.cn"
   * }
   *
   * @param host
   *          a host name
   * @return the domain name of the host name.
   */
  public static String getDomain(@Nonnull final String host) {
    if (isIPv4Address(host)) {
      return host;
    }
    final DomainSuffixRegistry tlds = DomainSuffixRegistry.getInstance();
    String candidate = host;
    int index = 0;
    while (index >= 0) {
      index = candidate.indexOf('.');
      final String subCandidate = candidate.substring(index + 1);
      if (tlds.isDomainSuffix(subCandidate)) {
        return candidate;
      }
      candidate = subCandidate;
    }
    return candidate;
  }

  /**
   * Given a host name, returns the domain suffix corresponding to the last
   * public part of the host name.
   *
   * For example,
   *
   * {@code 
   *    UrlUtils.getDomainSuffix("www.google.com")    = "com"
   *    UrlUtils.getDomainSuffix("www.sina.com.cn")   = "com.cn"
   * }
   *
   * @param host
   *          a host name.
   * @return the domain suffix corresponding to the last public part of the host
   *         name.
   */
  public static DomainSuffix getDomainSuffix(@Nonnull final String host) {
    final DomainSuffixRegistry tlds = DomainSuffixRegistry.getInstance();
    String candidate = host;
    int index = 0;
    while (index >= 0) {
      index = candidate.indexOf('.');
      final String subCandidate = candidate.substring(index + 1);
      final DomainSuffix suffix = tlds.getDomainSuffix(subCandidate);
      if (suffix != null) {
        return suffix;
      }
      candidate = subCandidate;
    }
    return null;
  }

  /**
   * Opens an {@link InputStream} for a {@link URL}.
   *
   * This function does the same thing as {@link URL#openStream()}, except that
   * it will decode the compressed stream.
   *
   * @param url
   *          an {@link URL} object.
   * @return the {@link InputStream} opened for the URL. If the content is
   *         encoded using some compression algorithm, the function will decode
   *         the encoded stream.
   * @throws IOException
   */
  public static InputStream openStream(final URL url) throws IOException {
    final URLConnection connection = url.openConnection();
    connection.setRequestProperty("User-Agent", USER_AGENT);
    final String encoding = connection.getContentEncoding();
    final InputStream input = connection.getInputStream();
    if (encoding == null) {
      return input;
    }
    if ("gzip".equals(encoding) || "x-gzip".equals(encoding)) {
      return new GZIPInputStream(input);
    } else if ("deflate".equals(encoding)) {
      return new InflaterInputStream(input);
    } else {
      LOGGER.warn("Unknown content encoding. The stream is returned without decoding.");
      return input;
    }
  }
}
