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

import javax.annotation.concurrent.Immutable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.io.serialize.XmlSerializer;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.text.xml.XmlSerializationException;

import static com.github.haixing_hu.text.xml.DomUtils.*;

/**
 * The {@link XmlSerializer} for the {@link Host} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class HostXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE = "host";
  public static final String SCHEME_NODE = "scheme";
  public static final String HOSTNAME_NODE = "hostname";
  public static final String PORT_NODE = "port";

  public static final HostXmlSerializer INSTANCE = new HostXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public Host deserialize(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    final String scheme = getReqStringChild(root, SCHEME_NODE, null, true,
        true);
    final String hostname = getReqStringChild(root, HOSTNAME_NODE, null,
        true, true);
    final int port = getOptIntChild(root, PORT_NODE, - 1);
    return new Host(scheme, hostname, port);
  }

  @Override
  public Element serialize(final Document doc, final Object obj)
      throws XmlException {
    Host host;
    try {
      host = (Host) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element root = doc.createElement(ROOT_NODE);
    appendReqStringChild(doc, root, SCHEME_NODE, null, host.scheme());
    appendReqStringChild(doc, root, HOSTNAME_NODE, null, host.hostname());
    appendOptIntChild(doc, root, PORT_NODE, host.port(), - 1);
    return root;
  }
}
