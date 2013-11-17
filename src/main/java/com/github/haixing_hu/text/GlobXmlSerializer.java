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

package com.github.haixing_hu.text;

import javax.annotation.concurrent.Immutable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.io.serialize.XmlSerializer;
import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.text.xml.XmlSerializationException;
import com.github.haixing_hu.text.xml.XmlUtils;

import static com.github.haixing_hu.text.xml.DomUtils.*;

/**
 * The {@link XmlSerializer} for the {@link Glob} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class GlobXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE = "glob";
  public static final String FLAGS_ATTRIBUTE = "flags";

  public static final GlobXmlSerializer INSTANCE = new GlobXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public Glob deserialize(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    final int flags = getOptIntAttr(root, FLAGS_ATTRIBUTE, Glob.DEFAULT_FLAGS);
    final String pattern = getOptString(root, XmlUtils.PRESERVE_SPACE_ATTRIBUTE,
        true, StringUtils.EMPTY);
    return new Glob(pattern, flags);
  }

  @Override
  public Element serialize(final Document doc, final Object obj)
      throws XmlException {
    Glob glob;
    try {
      glob = (Glob) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element root = doc.createElement(ROOT_NODE);
    setOptIntAttr(root, FLAGS_ATTRIBUTE, glob.flags, Glob.DEFAULT_FLAGS);
    root.setTextContent(glob.pattern);
    return root;
  }
}
