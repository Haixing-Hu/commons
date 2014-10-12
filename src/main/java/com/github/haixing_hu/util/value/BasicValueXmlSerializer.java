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
import com.github.haixing_hu.lang.TypeUtils;
import com.github.haixing_hu.text.xml.InvalidXmlNodeContentException;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.text.xml.XmlSerializationException;

import static com.github.haixing_hu.text.xml.DomUtils.*;

/**
 * The {@link XmlSerializer} for the {@link BasicValue} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class BasicValueXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE            = "value";
  public static final String TYPE_ATTRIBUTE       = "type";

  public static final BasicValueXmlSerializer INSTANCE = new BasicValueXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public BasicValue deserialize(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    final BasicValue result = new BasicValue();
    result.type = getOptEnumAttr(root, TYPE_ATTRIBUTE, true, Type.class, BasicValue.DEFAULT_TYPE);
    final String str = getOptString(root, PRESERVE_SPACE_ATTRIBUTE, true, null);
    if (str == null) {
      result.value = null;
    } else {
      try {
        result.value = TypeUtils.parseObject(result.type, str);
      } catch (final Exception e) {
        throw new InvalidXmlNodeContentException(ROOT_NODE, str, e.toString());
      }
    }
    return result;
  }

  @Override
  public Element serialize(final Document doc, final Object obj)
      throws XmlException {
    BasicValue var;
    try {
      var = (BasicValue) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element root = TypeUtils.toXmlNode(var.type, var.value, doc,
        ROOT_NODE, PRESERVE_SPACE_ATTRIBUTE);
    setOptEnumAttr(root, TYPE_ATTRIBUTE, true, var.type,
        BasicValue.DEFAULT_TYPE);
    return root;
  }
}
