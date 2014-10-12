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
package com.github.haixing_hu.io.serialize;

import javax.annotation.concurrent.ThreadSafe;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.haixing_hu.text.xml.XmlException;

/**
 * A {@link XmlSerializer} provides interface to serialize and deserialize
 * objects to and from an XML DOM true.
 * <p>
 * <b>NOTE</b>: All implementation of this interface <b>MUST</b> be thread safe.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public interface XmlSerializer {

  /**
   * Gets the name of the root node of the XML DOM tree for the objects of this
   * class.
   *
   * @return the name of the root node in the XML DOM tree for the objects of
   *         this class.
   */
  public String getRootNodeName();

  /**
   * Deserializes an object from an XML DOM true.
   *
   * @param root
   *          The root node of the XML DOM tree.
   * @return The object deserialized from XML DOM tree.
   * @throws NullPointerException
   *           If the {@code root} is {@code null}.
   * @throws XmlException
   *           If any XML error occurred.
   */
  public Object deserialize(Element root) throws XmlException;

  /**
   * Serializes an object to an XML DOM true.
   *
   * @param doc
   *          The XML DOM document used to create XML DOM node.
   * @param obj
   *          The object to be serialized. It can't be {@code null}.
   * @return The root node of the resulting XML DOM tree.
   * @throws NullPointerException
   *           If the {@code obj} is {@code null}.
   * @throws XmlException
   *           If any XML error occurred.
   */
  public Element serialize(Document doc, Object obj) throws XmlException;

}
