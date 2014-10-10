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

package com.github.haixing_hu.config.impl;

import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.io.serialize.XmlSerializer;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.text.xml.XmlSerializationException;

import static com.github.haixing_hu.text.xml.DomUtils.*;

/**
 * The {@link XmlSerializer} for the {@link DefaultConfig} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class DefaultConfigXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE = "configuration";

  public static final DefaultConfigXmlSerializer INSTANCE = new DefaultConfigXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public DefaultConfig deserialize(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    final DefaultConfig result = new DefaultConfig();
    final List<Element> propNodeList = getChildren(root, null);
    if ((propNodeList == null) || propNodeList.isEmpty()) {
      return result;
    }
    final DefaultPropertyXmlSerializer propSerializer = DefaultPropertyXmlSerializer.INSTANCE;
    for (final Element propNode : propNodeList) {
      final DefaultProperty prop = propSerializer.deserialize(propNode);
      result.properties.put(prop.getName(), prop);
    }
    return result;
  }

  @Override
  public Element serialize(final Document doc, final Object obj) throws XmlException {
    DefaultConfig config;
    try {
      config = (DefaultConfig) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final DefaultPropertyXmlSerializer propSerializer = DefaultPropertyXmlSerializer.INSTANCE;
    final Element root = doc.createElement(ROOT_NODE);
    for (final DefaultProperty prop : config.properties.values()) {
      final Element node = propSerializer.serialize(doc, prop);
      root.appendChild(node);
    }
    return root;
  }

}
