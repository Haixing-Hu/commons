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
package com.github.haixing_hu.util;

import javax.annotation.concurrent.Immutable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.io.serialize.XmlSerializer;
import com.github.haixing_hu.text.xml.XmlException;
import com.github.haixing_hu.text.xml.XmlSerializationException;

import static com.github.haixing_hu.text.xml.DomUtils.*;

/**
 * The {@link XmlSerializer} for the {@link DatePattern} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class DatePatternXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE = "date-pattern";

  public static final String PATTERN_NODE = "pattern";

  public static final String FORMAT_NODE = "format";

  public static final String LOCALE_NODE = "locale";

  public static final DatePatternXmlSerializer INSTANCE = new DatePatternXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public DatePattern deserialize(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    final DatePattern result = new DatePattern();
    result.matcher = null;
    result.dateFormat = null;
    // parse pattern
    result.pattern = getReqStringChild(root, PATTERN_NODE, null, true, true);
    result.format = getReqStringChild(root, FORMAT_NODE, null, true, true);
    final String localeId = getOptStringChild(root, LOCALE_NODE, null, true, null);
    if (localeId == null) {
      result.locale = null;
    } else {
      result.locale = LocaleUtils.fromPosixLocale(localeId);
    }
    return result;
  }

  @Override
  public Element serialize(final Document doc, final Object obj) throws XmlException {
    DatePattern dp;
    try {
      dp = (DatePattern) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element root = doc.createElement(ROOT_NODE);
    appendReqStringChild(doc, root, PATTERN_NODE, null, dp.pattern);
    appendReqStringChild(doc, root, FORMAT_NODE, null, dp.format);
    if (dp.locale != null) {
      final String localId = LocaleUtils.toPosixLocale(dp.locale);
      appendReqStringChild(doc, root, LOCALE_NODE, null, localId);
    }
    return root;
  }

}
