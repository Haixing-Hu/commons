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
package com.github.haixing_hu.text;

import javax.annotation.concurrent.Immutable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.io.serialize.XmlSerializer;
import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.text.xml.XmlSerializationException;

import static com.github.haixing_hu.text.xml.DomUtils.*;

/**
 * The {@link XmlSerializer} for the {@link Pattern} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class PatternXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE = "pattern";
  public static final String IGNORE_CASE_ATTRIBUTE = "ignore-case";
  public static final String TYPE_ATTRIBUTE = "type";

  public static final PatternXmlSerializer INSTANCE = new PatternXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public Pattern deserialize(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    // parse the optional pattern type attribute
    final PatternType type = getOptEnumAttr(root, TYPE_ATTRIBUTE, true,
        PatternType.class, Pattern.DEFAULT_TYPE);
    final boolean ignoreCase = getOptBooleanAttr(root, IGNORE_CASE_ATTRIBUTE,
        Pattern.DEFAULT_IGNORE_CASE);
    final String expression = getOptString(root, null, true, StringUtils.EMPTY);
    return new Pattern(type, ignoreCase, expression);
  }

  @Override
  public Element serialize(final Document doc, final Object obj)
      throws XmlException {
    Pattern pattern;
    try {
      pattern = (Pattern) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element root = doc.createElement(ROOT_NODE);
    setOptEnumAttr(root, TYPE_ATTRIBUTE, true, pattern.getType(),
        Pattern.DEFAULT_TYPE);
    setOptBooleanAttr(root, IGNORE_CASE_ATTRIBUTE, pattern.isIgnoreCase(),
        Pattern.DEFAULT_IGNORE_CASE);
    root.setTextContent(pattern.getExpression());
    return root;
  }
}
