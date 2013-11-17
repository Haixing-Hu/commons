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

import java.net.MalformedURLException;

import javax.annotation.concurrent.Immutable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.io.serialize.XmlSerializer;
import com.github.haixing_hu.text.xml.InvalidXmlNodeContentException;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.text.xml.XmlSerializationException;

import static com.github.haixing_hu.text.xml.DomUtils.*;

/**
 * The {@link XmlSerializer} for the {@link Url} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class UrlXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE = "url";

  public static final UrlXmlSerializer INSTANCE = new UrlXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public Url deserialize(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    final String str = getReqString(root, null, true, true);
    if (str.length() == 0) {
      return new Url();
    } else {
      try {
        return new Url(str);
      } catch (final MalformedURLException e) {
        throw new InvalidXmlNodeContentException(ROOT_NODE, str);
      }
    }
  }

  @Override
  public Element serialize(final Document doc, final Object obj)
      throws XmlException {
    Url url;
    try {
      url = (Url) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element result = doc.createElement(ROOT_NODE);
    result.setTextContent(url.toString());
    return result;
  }
}
