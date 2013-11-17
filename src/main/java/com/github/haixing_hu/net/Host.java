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

import java.io.Serializable;
import java.net.URI;
import java.net.URL;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.github.haixing_hu.io.serialize.BinarySerialization;
import com.github.haixing_hu.io.serialize.XmlSerialization;
import com.github.haixing_hu.lang.Cloneable;
import com.github.haixing_hu.lang.Comparison;
import com.github.haixing_hu.lang.Equality;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.text.NumberFormat;

/**
 * A {@link Host} object represents a remote host, storing its scheme, hostname
 * and port.
 * <p>
 * The scheme and hostname will be lower-cased automatically.
 * </p>
 * <p>
 * Note that the {@link Host} object is immutable.
 * </p>
 *
 * @author Haixing Hu
 */
@Immutable
public final class Host implements Cloneable<Host>, Comparable<Host>,
    Serializable {

  private static final long serialVersionUID = - 1489490467901212305L;

  public static final String DEFAULT_SCHEME = "http";

  public static final int DEFAULT_PORT = 80;

  static {
    BinarySerialization.register(Host.class, HostBinarySerializer.INSTANCE);
    XmlSerialization.register(Host.class, HostXmlSerializer.INSTANCE);
  }

  private final @Nonnull
  String scheme;
  private final @Nonnull
  String hostname;
  private final int port;

  public Host(final String hostname) {
    this.scheme = DEFAULT_SCHEME;
    this.hostname = hostname.toLowerCase();
    this.port = - 1;
  }

  public Host(@Nonnull final String scheme, @Nonnull final String hostname) {
    this.scheme = scheme.toLowerCase();
    this.hostname = hostname.toLowerCase();
    this.port = DefaultPorts.get(this.scheme);
  }

  public Host(@Nonnull final String hostname, final int port) {
    this.scheme = DEFAULT_SCHEME;
    this.hostname = hostname.toLowerCase();
    if (port < 0) {
      this.port = - 1;
    } else {
      this.port = port;
    }
  }

  public Host(@Nonnull final String scheme, @Nonnull final String hostname,
      final int port) {
    this.scheme = scheme.toLowerCase();
    this.hostname = hostname.toLowerCase();
    if (port < 0) {
      this.port = - 1;
    } else {
      this.port = port;
    }
  }

  public Host(@Nonnull final Url url) {
    this.scheme = url.scheme();
    this.hostname = url.hostname();
    this.port = url.port();
  }

  public Host(@Nonnull final URL url) {
    this(url.getProtocol(), url.getHost(), url.getPort(), url.getAuthority());
  }

  public Host(@Nonnull final URI uri) {
    this(uri.getScheme(), uri.getHost(), uri.getPort(), uri.getAuthority());
  }

  private Host(String scheme, String hostname, int port, final String authority) {
    // FIXME: if the hostname part of the URL contains an illegal character,
    // for example, "http://local_host/dir", the URI parser will parse
    // the "local_host" as a registry-based authority, and let the hostname
    // be null.
    if (hostname == null) {
      // let's try to parse the userInfo, hostname and port by ourself
      if (authority != null) {
        // parse the userInfo
        int at = authority.indexOf('@');
        if (at < 0) {
          at = - 1;
        }
        // parse the port
        int colon = authority.lastIndexOf(':');
        if (colon < 0) {
          port = - 1;
          colon = authority.length();
        } else {
          final String portstr = authority.substring(colon + 1);
          final NumberFormat nf = new NumberFormat();
          port = nf.parseInt(portstr);
          if (nf.fail()) {
            port = - 1;
          }
        }
        // parse the hostname
        hostname = authority.substring(at + 1, colon);
      }
    }
    if (scheme == null) {
      scheme = StringUtils.EMPTY;
    }
    if (hostname == null) {
      hostname = StringUtils.EMPTY;
    }
    // initialize the fields
    this.scheme = scheme;
    this.hostname = hostname;
    this.port = port;
  }

  public String scheme() {
    return scheme;
  }

  public String hostname() {
    return hostname;
  }

  public int port() {
    return port;
  }

  @Override
  public int hashCode() {
    final int multiplier = 131;
    int code = 31;
    code = Hash.combine(code, multiplier, scheme);
    code = Hash.combine(code, multiplier, hostname);
    code = Hash.combine(code, multiplier, port);
    return code;
  }

  @Override
  public boolean equals(@Nullable final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Host other = (Host) obj;
    return (port == other.port) && Equality.equals(scheme, other.scheme)
        && Equality.equals(hostname, other.hostname);
  }

  @Override
  public int compareTo(@Nullable final Host other) {
    if (other == null) {
      return + 1;
    } else {
      int rc = Comparison.compare(scheme, other.scheme);
      if (rc != 0) {
        return rc;
      }
      rc = Comparison.compare(hostname, other.hostname);
      if (rc != 0) {
        return rc;
      }
      return port - other.port;
    }
  }

  public String toHostString() {
    if (port >= 0) {
      return hostname + ':' + String.valueOf(port);
    } else {
      return hostname;
    }
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append(scheme).append("://").append(hostname);
    if (port >= 0) {
      builder.append(':').append(port);
    }
    return builder.toString();
  }

  @Override
  public Host clone() {
    return new Host(scheme, hostname, port);
  }

}
