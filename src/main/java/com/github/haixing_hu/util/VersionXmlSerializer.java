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

package com.github.haixing_hu.util;

import java.text.ParseException;

import javax.annotation.concurrent.Immutable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.io.serialize.XmlSerializer;
import com.github.haixing_hu.text.xml.InvalidXmlNodeContentException;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.text.xml.XmlSerializationException;

import static com.github.haixing_hu.text.xml.DomUtils.*;

/**
 * The {@link XmlSerializer} for the {@link Version} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class VersionXmlSerializer implements XmlSerializer {

  public static final String  ROOT_NODE                 = "version";

  public static final VersionXmlSerializer INSTANCE = new VersionXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public Version deserialize(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    final String str = getReqString(root, PRESERVE_SPACE_ATTRIBUTE, false, false);
    try {
      return new Version(str);
    } catch (final ParseException e) {
      throw new InvalidXmlNodeContentException(ROOT_NODE, str);
    }
  }

  @Override
  public Element serialize(final Document doc, final Object obj) throws XmlException {
    Version version;
    try {
      version = (Version) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element result = doc.createElement(ROOT_NODE);
    result.setTextContent(version.toString());
    return result;
  }

}
