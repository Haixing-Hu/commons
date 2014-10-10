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

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.CommonsConfig;
import com.github.haixing_hu.config.Config;
import com.github.haixing_hu.lang.SystemUtils;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.text.xml.XmlUtils;

import static com.github.haixing_hu.text.xml.DomUtils.*;

/**
 * A {@link DefaultPorts} object stores a map between the scheme and its
 * default port.
 *
 * @author Haixing Hu
 */
@Immutable
public final class DefaultPorts {

  /**
   * The value of this property is the name of the XML resource file of the
   * {@link DefaultPorts} class.
   *
   * <table border="1">
   * <tr>
   * <th>Type</th>
   * <th>Count</th>
   * <th>Value</th>
   * <th>Required</th>
   * <th>Default</th>
   * <th>Range</th>
   * </tr>
   * <tr>
   * <td>String</td>
   * <td>1</td>
   * <td>the name of the XML resource file of the {@link DefaultPorts} class.</td>
   * <td>no</td>
   * <td>{@link DEFAULT_RESOURCE}</td>
   * <td></td>
   * </tr>
   * </table>
   *
   * @see #DEFAULT_RESOURCE
   */
  public static final String PROPERTY_RESOURCE = "com.github.haixing_hu.net.DefaultPorts.resource";

  /**
   * The default value of the property {@link PROPERTY_RESOURCE}.
   *
   * @see PROPERTY_RESOURCE
   */
  public static final String DEFAULT_RESOURCE = "default-ports.xml";

  public static final String ROOT_NODE = "default-ports";

  public static final String DEFAULT_PORT_NODE = "default-port";

  public static final String SCHEME_NODE = "scheme";

  public static final String PORT_NODE = "port";

  private static volatile Map<String, Integer> PORTS = null;

  /**
   * Gets the default port number of a specified scheme.
   *
   * @param scheme
   *          a specified scheme.
   * @return the default port number of the specified scheme; or -1 if no such
   *         default port for the specified scheme.
   */
  public static int get(final String scheme) {
    if (PORTS == null) {
      synchronized (DefaultPorts.class) {
        if (PORTS == null) {
          final Config config = CommonsConfig.get();
          final String resource = config.getString(PROPERTY_RESOURCE,
              DEFAULT_RESOURCE);
          loadDefaultPorts(resource);
        }
      }
    }
    final Integer result = PORTS.get(scheme);
    return (result != null ? result.intValue() : - 1);
  }

  private static void loadDefaultPorts(final String resource) {
    PORTS = new HashMap<String, Integer>();
    final URL url = SystemUtils.getResource(resource, DefaultPorts.class);
    if (url == null) {
      final Logger logger = LoggerFactory.getLogger(DefaultPorts.class);
      logger.error("Can't find the default ports resource file: {}",
          resource);
      return;
    }
    try {
      final Document doc = XmlUtils.parse(url);
      parse(doc.getDocumentElement());
    } catch (final XmlException e) {
      final Logger logger = LoggerFactory.getLogger(DefaultPorts.class);
      logger.error("Failed to load the default ports from {}.", resource, e);
    }
  }

  private static void parse(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    final List<Element> nodeList = getChildren(root, null);
    if ((nodeList == null) || nodeList.isEmpty()) {
      return;
    }
    for (final Element node : nodeList) {
      checkNode(node, DEFAULT_PORT_NODE);
      final String scheme = getReqStringChild(node, SCHEME_NODE, null, true, false);
      final int port = getReqIntChild(node, PORT_NODE);
      PORTS.put(scheme, port);
    }
  }

}
