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

package com.github.haixing_hu.text.xml;

import javax.annotation.concurrent.Immutable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.io.serialize.XmlSerializer;

import static com.github.haixing_hu.text.xml.DomUtils.*;

/**
 * The {@link XmlSerializer} for the {@link NodePattern} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NodePatternXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE = "node-pattern";

  public static final String XPATH_NODE = "xpath";

  public static final NodePatternXmlSerializer INSTANCE = new NodePatternXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public NodePattern deserialize(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    final String xpath = getReqStringChild(root, XPATH_NODE, null, true, true);
    return new NodePattern(xpath);
  }

  @Override
  public Element serialize(final Document doc, final Object obj) throws XmlException {
    NodePattern pattern;
    try {
      pattern = (NodePattern) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element root = doc.createElement(ROOT_NODE);
    appendReqStringChild(doc, root, XPATH_NODE, null, pattern.xpath);
    return root;
  }

}