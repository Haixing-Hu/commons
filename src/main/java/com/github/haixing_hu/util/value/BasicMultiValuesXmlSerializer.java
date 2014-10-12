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
package com.github.haixing_hu.util.value;

import javax.annotation.concurrent.Immutable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.io.serialize.XmlSerializer;
import com.github.haixing_hu.lang.Type;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.text.xml.XmlSerializationException;

import static com.github.haixing_hu.text.xml.DomUtils.*;

/**
 * The {@link XmlSerializer} for the {@link BasicMultiValues} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class BasicMultiValuesXmlSerializer implements XmlSerializer {

  public static final BasicMultiValuesXmlSerializer INSTANCE =
    new BasicMultiValuesXmlSerializer();

  public static final String ROOT_NODE = "multi-values";

  public static final String TYPE_ATTRIBUTE = "type";

  public static final String VALUE_NODE = "value";

  public static final String PRESERVE_SPACE_ATTRIBUTE = "preserve-space";

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public BasicMultiValues deserialize(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    final BasicMultiValues result = new BasicMultiValues();
    final Type type = getOptEnumAttr(root, TYPE_ATTRIBUTE, true, Type.class,
        MultiValues.DEFAULT_TYPE);
    result.getValuesFromXml(type, root, VALUE_NODE,
        PRESERVE_SPACE_ATTRIBUTE);
    return result;
  }

  @Override
  public Element serialize(final Document doc, final Object obj)
      throws XmlException {
    BasicMultiValues var;
    try {
      var = (BasicMultiValues) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element root = doc.createElement(ROOT_NODE);
    setOptEnumAttr(root, TYPE_ATTRIBUTE, true, var.getType(),
        MultiValues.DEFAULT_TYPE);
    var.appendValuesToXml(doc, root, null, VALUE_NODE,
        PRESERVE_SPACE_ATTRIBUTE);
    return root;
  }

}
