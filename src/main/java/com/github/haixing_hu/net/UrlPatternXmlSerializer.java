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
import com.github.haixing_hu.text.Pattern;
import com.github.haixing_hu.text.PatternXmlSerializer;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.text.xml.XmlSerializationException;

import static com.github.haixing_hu.text.xml.DomUtils.*;

/**
 * The {@link XmlSerializer} for the {@link UrlPattern} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class UrlPatternXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE = "url-pattern";
  public static final String URL_PART_NODE = "part";

  public static final UrlPatternXmlSerializer INSTANCE = new UrlPatternXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public UrlPattern deserialize(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    final UrlPart urlPart = getOptEnumChild(root, URL_PART_NODE, true,
        UrlPart.class, UrlPattern.DEFAULT_URL_PART);
    final Element patternNode = getReqChild(root, PatternXmlSerializer.ROOT_NODE);
    final Pattern pattern = PatternXmlSerializer.INSTANCE.deserialize(patternNode);
    return new UrlPattern(urlPart, pattern);
  }

  @Override
  public Element serialize(final Document doc, final Object obj)
      throws XmlException {
    UrlPattern urlPattern;
    try {
      urlPattern = (UrlPattern) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element root = doc.createElement(ROOT_NODE);
    appendOptEnumChild(doc, root, URL_PART_NODE, true, urlPattern.urlPart,
        UrlPattern.DEFAULT_URL_PART);
    final Element patternNode = PatternXmlSerializer.INSTANCE.serialize(doc, urlPattern.pattern);
    root.appendChild(patternNode);
    return root;
  }
}
