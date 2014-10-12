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
package com.github.haixing_hu.config.impl;

import javax.annotation.concurrent.Immutable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.io.serialize.XmlSerializer;
import com.github.haixing_hu.lang.Type;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.text.xml.XmlSerializationException;

import static com.github.haixing_hu.config.impl.DefaultProperty.DEFAULT_IS_FINAL;
import static com.github.haixing_hu.text.xml.DomUtils.*;
import static com.github.haixing_hu.util.value.MultiValues.DEFAULT_TYPE;

/**
 * The {@link XmlSerializer} for the {@link DefaultProperty} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class DefaultPropertyXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE                  = "property";
  public static final String NAME_ATTRIBUTE             = "name";
  public static final String TYPE_ATTRIBUTE             = "type";
  public static final String VALUE_NODE                 = "value";
  public static final String IS_FINAL_ATTRIBUTE         = "final";
  public static final String PRESERVE_SPACE_ATTRIBUTE   = "preserve-space";
  public static final String DESCRIPTION_NODE           = "description";

  public static final DefaultPropertyXmlSerializer INSTANCE = new DefaultPropertyXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public DefaultProperty deserialize(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    final String name = getReqStringAttr(root, NAME_ATTRIBUTE, false, false);
    final boolean isFinal = getOptBooleanAttr(root, IS_FINAL_ATTRIBUTE, DEFAULT_IS_FINAL);
    final Type type = getOptEnumAttr(root, TYPE_ATTRIBUTE, true, Type.class,
        DEFAULT_TYPE);
    final String description = getOptStringChild(root, DESCRIPTION_NODE,
        PRESERVE_SPACE_ATTRIBUTE, DefaultProperty.DEFAULT_TRIM, null);
    final DefaultProperty result = new DefaultProperty(name);
    result.setFinal(isFinal);
    result.setDescription(description);
    result.setType(type);
    result.getValuesFromXml(type, root, VALUE_NODE, PRESERVE_SPACE_ATTRIBUTE);
    return result;
  }

  @Override
  public Element serialize(final Document doc, final Object obj) throws XmlException {
    DefaultProperty prop;
    try {
      prop = (DefaultProperty) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element root = doc.createElement(ROOT_NODE);
    setReqStringAttr(root, NAME_ATTRIBUTE, prop.getName());
    setOptEnumAttr(root, TYPE_ATTRIBUTE, true, prop.getType(), DEFAULT_TYPE);
    setOptBooleanAttr(root, IS_FINAL_ATTRIBUTE, prop.isFinal(), DEFAULT_IS_FINAL);
    appendOptStringChild(doc, root, DESCRIPTION_NODE, PRESERVE_SPACE_ATTRIBUTE,
        prop.getDescription());
    prop.appendValuesToXml(doc, root, null, VALUE_NODE, PRESERVE_SPACE_ATTRIBUTE);
    return root;
  }
}
