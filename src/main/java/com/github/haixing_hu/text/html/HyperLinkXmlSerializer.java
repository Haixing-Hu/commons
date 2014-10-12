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
package com.github.haixing_hu.text.html;

import java.net.MalformedURLException;

import javax.annotation.concurrent.ThreadSafe;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.io.serialize.XmlSerializer;
import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.net.Url;
import com.github.haixing_hu.text.xml.InvalidXmlAttributeException;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.text.xml.XmlSerializationException;

import static com.github.haixing_hu.text.xml.DomUtils.*;

/**
 * The {@link XmlSerializer} for the {@link HyperLink} class.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class HyperLinkXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE = "hyper-link";
  public static final String URL_ATTRIBUTE = "url";

  public static final HyperLinkXmlSerializer INSTANCE = new HyperLinkXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public HyperLink deserialize(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    final String str = getReqStringAttr(root, URL_ATTRIBUTE, true, true);
    Url url = null;
    try {
      url = new Url(str);
    } catch (final MalformedURLException e) {
      throw new InvalidXmlAttributeException(ROOT_NODE, URL_ATTRIBUTE, str);
    }
    final String anchor = getOptString(root, null, true, null);
    return new HyperLink(url, anchor);
  }

  @Override
  public Element serialize(final Document doc, final Object obj) throws XmlException {
    HyperLink link;
    try {
      link = (HyperLink) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element root = doc.createElement(ROOT_NODE);
    final Url url = link.url();
    if (url == null) {
      root.setAttribute(URL_ATTRIBUTE, StringUtils.EMPTY);
    } else {
      root.setAttribute(URL_ATTRIBUTE, url.toString());
    }
    final String anchor = link.anchor();
    if (anchor == null) {
      root.setTextContent(StringUtils.EMPTY);
    } else {
      root.setTextContent(anchor);
    }
    return root;
  }
}
