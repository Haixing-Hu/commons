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

package com.github.haixing_hu.text.html;

/**
 * This interface defines the constant of HTML event names.
 *
 * The name of the constants are of the form
 * <code>ATTR_[uppercase event attribute name]</code>.
 *
 * @author Haixing Hu
 * @see HtmlTag
 * @see HtmlAttribute
 * @see HtmlAttributeValue
 * @see http://www.w3schools.com/tags/ref_eventattributes.asp
 */
public interface HtmlEvent {

  /**
   * Script to be run when a document load.
   */
  public static final String ATTR_ONLOAD      = "onload";

  /**
   * Script to be run when a document unload.
   */
  public static final String ATTR_ONUNLOAD    = "onunload";

  /**
   * Script to be run when an element loses focus.
   */
  public static final String ATTR_ONBLUR      = "onblur";

  /**
   * Script to be run when an element change.
   */
  public static final String ATTR_ONCHANGE    = "onchange";

  /**
   * Script to be run when an element gets focus.
   */
  public static final String ATTR_ONFOCUS     = "onfocus";

  /**
   * Script to be run when a form is reset.
   */
  public static final String ATTR_ONRESET     = "onreset";

  /**
   * Script to be run when an element is selected.
   */
  public static final String ATTR_ONSELECT    = "onselect";

  /**
   * Script to be run when a form is submitted.
   */
  public static final String ATTR_ONSUBMIT    = "onsubmit";

  /**
   * Script to be run when loading of an image is interrupted.
   */
  public static final String ATTR_ONABORT     = "onabort";

  /**
   * Script to be run when a key is pressed.
   */
  public static final String ATTR_ONKEYDOWN   = "onkeydown";

  /**
   * Script to be run when a key is pressed and released.
   */
  public static final String ATTR_ONKEYPRESS  = "onkeypress";

  /**
   * Script to be run when a key is released.
   */
  public static final String ATTR_ONKEYUP     = "onkeyup";

  /**
   * Script to be run on a mouse click.
   */
  public static final String ATTR_ONCLICK     = "onclick";

  /**
   * Script to be run on a mouse double-click.
   */
  public static final String ATTR_ONDBLCLICK  = "ondblclick";

  /**
   * Script to be run when mouse button is pressed.
   */
  public static final String ATTR_ONMOUSEDOWN = "onmousedown";

  /**
   * Script to be run when mouse pointer moves.
   */
  public static final String ATTR_ONMOUSEMOVE = "onmousemove";

  /**
   * Script to be run when mouse pointer moves out of an element.
   */
  public static final String ATTR_ONMOUSEOUT  = "onmouseout";

  /**
   * Script to be run when mouse pointer moves over an element.
   */
  public static final String ATTR_ONMOUSEOVER = "onmouseover";

  /**
   * Script to be run when mouse button is released.
   */
  public static final String ATTR_ONMOUSEUP   = "onmouseup";
}
