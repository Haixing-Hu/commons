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

package com.github.haixing_hu.util.transformer.string;

import javax.annotation.concurrent.ThreadSafe;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.io.serialize.XmlSerializer;
import com.github.haixing_hu.text.xml.XmlException;

import static com.github.haixing_hu.text.xml.DomUtils.*;

/**
 * The {@link XmlSerializer} for the {@link RegexTransformer} class.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class RegexTransformerXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE = "regex-transformer";

  public static final String INPUT_PATTERN_NODE = "input-pattern";

  public static final String OUTPUT_PATTERN_NODE = "output-pattern";

  public static final RegexTransformerXmlSerializer INSTANCE = new RegexTransformerXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public RegexTransformer deserialize(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    final String inputPattern = getReqStringChild(root, INPUT_PATTERN_NODE, null, true, true);
    final String outputPattern = getReqStringChild(root, OUTPUT_PATTERN_NODE, null, true, true);
    return new RegexTransformer(inputPattern, outputPattern);
  }

  @Override
  public Element serialize(final Document doc, final Object obj) throws XmlException {
    RegexTransformer tr;
    try {
      tr = (RegexTransformer) obj;
    } catch (final ClassCastException e) {
      throw new XmlException(e);
    }
    final Element root = doc.createElement(ROOT_NODE);
    appendReqStringChild(doc, root, INPUT_PATTERN_NODE, null, tr.getInputPattern());
    appendReqStringChild(doc, root, OUTPUT_PATTERN_NODE, null, tr.getOutputPattern());
    return root;
  }

}
