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
package com.github.haixing_hu.text.html;

/**
 * This interface defines the constant of HTML attribute names.
 *
 * The name of the constants are of the form
 * {@code ATTR_[uppercase attribute name]}.
 *
 * @author Haixing Hu
 * @see HtmlTag
 * @see HtmlAttributeValue
 * @see HtmlEvent
 * @see http://www.w3schools.com/tags/default.asp
 */
public interface HtmlAttribute {

  /**
   * Specifies a keyboard shortcut to access an element.
   *
   * This is a standard attribute.
   */
  public static final String ATTR_ACCESSKEY      = "accesskey";

  /**
   * Specifies a class name for an element.
   *
   * This is a standard attribute.
   */
  public static final String ATTR_CLASS          = "class";

  /**
   * Specifies the text direction for the content in an element.
   *
   * This is a standard attribute.
   */
  public static final String ATTR_DIR            = "dir";

  /**
   * Specifies a unique id for an element.
   *
   * This is a standard attribute.
   */
  public static final String ATTR_ID             = "id";

  /**
   * Specifies a language code for the content in an element.
   *
   * This is a standard attribute.
   */
  public static final String ATTR_LANG           = "lang";

  /**
   * Specifies an inline style for an element.
   *
   * This is a standard attribute.
   */
  public static final String ATTR_STYLE          = "style";

  /**
   * Specifies the tab order of an element.
   *
   * This is a standard attribute.
   */
  public static final String ATTR_TABINDEX       = "tabindex";

  /**
   * Specifies extra information about an element.
   *
   * This is a standard attribute.
   */
  public static final String ATTR_TITLE          = "title";

  /**
   * Specifies a language code for the content in an element, in XHTML
   * documents.
   *
   * This is a standard attribute.
   */
  public static final String ATTR_XML_LANG       = "xml:lang";

  /**
   * Specifies the character-set of a linked document.
   *
   * Applied to tag {@code a}, {@code link}, {@code script}.
   */
  public static final String ATTR_CHARSET        = "charset";

  /**
   * Specifies the coordinates of an element.
   *
   * Applied to tag {@code a}, {@code area}.
   */
  public static final String ATTR_COORDS         = "coords";

  /**
   * Specifies the destination of a link.
   *
   * Applied to tag {@code a}, {@code area}, {@code base},
   * {@code link}.
   */
  public static final String ATTR_HREF           = "href";

  /**
   * Specifies the language of a linked document.
   *
   * Applied to tag {@code a}, {@code link}.
   */
  public static final String ATTR_HREFLANG       = "hreflang";

  /**
   * Specifies the name of an element.
   *
   * Applied to tag {@code a}, {@code applet}, {@code button},
   * {@code form}, {@code frame}, {@code iframe},
   * {@code input}, {@code map}, {@code meta},
   * {@code object}, {@code param}, {@code select},
   * {@code textarea}.
   */
  public static final String ATTR_NAME           = "name";

  /**
   * Specifies the relationship between the current document and the linked
   * document.
   *
   * Applied to tag {@code a}, {@code link}.
   */
  public static final String ATTR_REL            = "rel";

  /**
   * Specifies the relationship between the linked document and the current
   * document.
   *
   * Applied to tag {@code a}, {@code link}.
   */
  public static final String ATTR_REV            = "rev";

  /**
   * Specifies the shape of an element.
   *
   * Applied to tag {@code a}, {@code area}.
   */
  public static final String ATTR_SHAPE          = "shape";

  /**
   * Specifies where to open the linked page specified in the {@code href}
   * attribute.
   *
   * Applied to tag {@code a}, {@code area}, {@code base},
   * {@code form}, {@code link}.
   */
  public static final String ATTR_TARGET         = "target";

  /**
   * Specifies the file name of a Java applet.
   *
   * Applied to tag {@code applet}.
   */
  public static final String ATTR_CODE           = "code";

  /**
   * Specifies a reference to a serialized representation of an applet.
   *
   * Applied to tag {@code applet}.
   */
  public static final String ATTR_OBJECT         = "object";

  /**
   * Specifies the alignment of an element according to surrounding elements.
   *
   * Applied to tag {@code applet}, {@code caption}, {@code col},
   * {@code colgroup}, {@code div}, {@code h1}, {@code h2},
   * {@code h3}, {@code h4}, {@code h5}, {@code h6},
   * {@code hr}, {@code iframe}, {@code img}, {@code input},
   * {@code legend}, {@code object}, {@code p},
   * {@code table}, {@code tbody}, {@code td}, {@code tfoot}
   * , {@code th}, {@code thead}, {@code tr}.
   */
  public static final String ATTR_ALIGN          = "align";

  /**
   * Specifies an alternate text for an element.
   *
   * Applied to tag {@code applet}, {@code area}, {@code img},
   * {@code input}.
   */
  public static final String ATTR_ALT            = "alt";

  /**
   * Specifies the location of an archive file.
   *
   * Applied to tag {@code applet}, {@code object}.
   */
  public static final String ATTR_ARCHIVE        = "archive";

  /**
   * Specifies a relative base URL for applets/objects specified in the code
   * attribute.
   *
   * Applied to tag {@code applet}, {@code object}.
   */
  public static final String ATTR_CODEBASE       = "codebase";

  /**
   * Specifies the height of an element.
   *
   * Applied to tag {@code applet}, {@code iframe}, {@code img},
   * {@code object}, {@code td}, {@code th}.
   */
  public static final String ATTR_HEIGHT         = "height";

  /**
   * Defines the horizontal spacing around an element.
   *
   * Applied to tag {@code applet}, {@code img}, {@code object}.
   */
  public static final String ATTR_HSPACE         = "hspace";

  /**
   * Defines the vertical spacing around an element.
   *
   * Applied to tag {@code applet}, {@code img}, {@code object}.
   */
  public static final String ATTR_VSPACE         = "vspace";

  /**
   * Specifies the width of an element.
   *
   * Applied to tag {@code applet}, {@code col}, {@code colgroup}
   * , {@code hr}, {@code iframe}, {@code img},
   * {@code object}, {@code pre}, {@code table}, {@code td},
   * {@code th}.
   */
  public static final String ATTR_WIDTH          = "width";

  /**
   * Specifies that an area has no associated link.
   *
   * Applied to tag {@code area}.
   */
  public static final String ATTR_NOHREF         = "nohref";

  /**
   * Specifies the source of a quotation, or specifies a URL to a document which
   * explains why the text was deleted/inserted/changed.
   *
   * Applied to tag {@code blockquote}, {@code del}, {@code ins},
   * {@code q}.
   */
  public static final String ATTR_CITE           = "cite";

  /**
   * Specifies the color of an active link in a document.
   *
   * Deprecated. Use styles instead.
   *
   * Applied to tag {@code body}.
   */
  public static final String ATTR_ALINK          = "alink";

  /**
   * Specifies a background image for a document.
   *
   * Deprecated. Use styles instead.
   *
   * Applied to tag {@code body}.
   */
  public static final String ATTR_BACKGROUND     = "background";

  /**
   * Specifies the background color of an element.
   *
   * Deprecated. Use styles instead.
   *
   * Applied to tag {@code body}, {@code table}, {@code td},
   * {@code th}, {@code tr}.
   */
  public static final String ATTR_BGCOLOR        = "bgcolor";

  /**
   * Specifies the default color of unvisited links in a document.
   *
   * Deprecated. Use styles instead.
   *
   * Applied to tag {@code body}.
   */
  public static final String ATTR_LINK           = "link";

  /**
   * Specifies the color of the text in a document.
   *
   * Deprecated. Use styles instead.
   *
   * Applied to tag {@code body}.
   */
  public static final String ATTR_TEXT           = "text";

  /**
   * Specifies the default color of visited links in a document.
   *
   * Deprecated. Use styles instead.
   *
   * Applied to tag {@code body}.
   */
  public static final String ATTR_VLINK          = "vlink";

  /**
   * Specifies that an element should be disabled.
   *
   * Applied to tag {@code button}, {@code input},
   * {@code optgroup}, {@code option}, {@code select},
   * {@code textarea}.
   */
  public static final String ATTR_DISABLED       = "disabled";

  /**
   * Specifies the type of an element.
   *
   * Applied to tag {@code button}, {@code input}, {@code li},
   * {@code link}, {@code object}, {@code ol}, {@code param}
   * , {@code script}, {@code style}, {@code ul}.
   */
  public static final String ATTR_TYPE           = "type";

  /**
   * Specifies the underlying value of an element.
   *
   * Applied to tag {@code button}, {@code input}, {@code li},
   * {@code option}, {@code param}.
   */
  public static final String ATTR_VALUE          = "value";

  /**
   * Aligns the content in a cell to a character
   *
   * Applied to tag {@code col}, {@code colgroup}, {@code tbody},
   * {@code td}, {@code tfoot}, {@code th}, {@code thead},
   * {@code tr}.
   */
  public static final String ATTR_CHAR           = "char";

  /**
   * Specifies the number of characters the content will be aligned from the
   * character specified by the {@code char} attribute.
   *
   * Applied to tag {@code col}, {@code colgroup}, {@code tbody},
   * {@code td}, {@code tfoot}, {@code th}, {@code thead},
   * {@code tr}.
   */
  public static final String ATTR_CHAROFF        = "charoff";

  /**
   * Specifies the number of columns a table column element should span.
   *
   * Applied to tag {@code col}, {@code colgroup}.
   */
  public static final String ATTR_SPAN           = "span";

  /**
   * Specifies the vertical alignment of the content related to a table column
   * element.
   *
   * Applied to tag {@code col}, {@code colgroup}, {@code tbody},
   * {@code td}, {@code tfoot}, {@code th}, {@code thead},
   * {@code tr}.
   */
  public static final String ATTR_VALIGN         = "valign";

  /**
   * Specifies the date and time when the text was deleted/inserted/changed.
   *
   * Applied to tag {@code del}, {@code ins}.
   */
  public static final String ATTR_DATETIME       = "datetime";

  /**
   * Specifies that the list should render smaller than normal.
   *
   * Deprecated. Use styles instead.
   *
   * Applied to tag {@code dir}, {@code menu}, {@code ol},
   * {@code ul}.
   */
  public static final String ATTR_COMPACT        = "compact";

  /**
   * Specifies the color of text.
   *
   * Deprecated. Use styles instead.
   *
   * Applied to tag {@code font}.
   */
  public static final String ATTR_COLOR          = "color";

  /**
   * Specifies the font of text.
   *
   * Deprecated. Use styles instead.
   *
   * Applied to tag {@code font}.
   */
  public static final String ATTR_FACE           = "face";

  /**
   * Specifies the size of an element.
   *
   * Deprecated. Use styles instead.
   *
   * Applied to tag {@code font}, {@code hr}, {@code input},
   * {@code select}.
   */
  public static final String ATTR_SIZE           = "size";

  /**
   * Specifies where to send the form-data when a form is submitted.
   *
   * Applied to tag {@code form}.
   */
  public static final String ATTR_ACTION         = "action";

  /**
   * Specifies the types of files that can be submitted through a file upload.
   *
   * Applied to tag {@code form}, {@code input}.
   */
  public static final String ATTR_ACCEPT         = "accept";

  /**
   * Specifies the character-sets the server can handle for form-data.
   *
   * Applied to tag {@code form}.
   */
  public static final String ATTR_ACCEPT_CHARSET = "accept-charset";

  /**
   * Specifies how form-data should be encoded before sending it to a server.
   *
   * Applied to tag {@code form}.
   */
  public static final String ATTR_ENCTYPE        = "enctype";

  /**
   * Specifies how to send form-data.
   *
   * Applied to tag {@code form}.
   */
  public static final String ATTR_METHOD         = "method";

  /**
   * Specifies whether or not to display a border around a frame.
   *
   * Applied to tag {@code frame}, {@code iframe}.
   */
  public static final String ATTR_FRAMEBORDER    = "frameborder";

  /**
   * Specifies a page that contains a long description of the content of an
   * element.
   *
   * Applied to tag {@code frame}, {@code iframe}, {@code img}.
   */
  public static final String ATTR_LONGDESC       = "longdesc";

  /**
   * Specifies the top and bottom margins of a frame.
   *
   * Applied to tag {@code frame}, {@code iframe}.
   */
  public static final String ATTR_MARGINHEIGHT   = "marginheight";

  /**
   * Specifies the left and right margins of a frame.
   *
   * Applied to tag {@code frame}, {@code iframe}.
   */
  public static final String ATTR_MARGINWIDTH    = "marginwidth";

  /**
   * Specifies that a frame cannot be resized.
   *
   * Applied to tag {@code frame}.
   */
  public static final String ATTR_NORESIZE       = "noresize";

  /**
   * Specifies whether or not to display scrolling bars in a frame.
   *
   * Applied to tag {@code frame}, {@code iframe}.
   */
  public static final String ATTR_SCROLLING      = "scrolling";

  /**
   * Specifies the URL of the content to show in an element.
   *
   * Applied to tag {@code frame}, {@code iframe}, {@code img},
   * {@code input}, {@code script}.
   */
  public static final String ATTR_SRC            = "src";

  /**
   * Specifies the number and size of columns in an element.
   *
   * Applied to tag {@code frameset}, {@code textarea}.
   */
  public static final String ATTR_COLS           = "cols";

  /**
   * Specifies the number and size of rows in an element.
   *
   * Applied to tag {@code frameset}, {@code textarea}.
   */
  public static final String ATTR_ROWS           = "rows";

  /**
   * Specifies a URL to a document that contains a set of rules. The rules can
   * be read by browsers to clearly understand the information in the
   * {@code meta} tag's {@code content} attribute.
   *
   * Applied to tag {@code head}.
   */
  public static final String ATTR_PROFILE        = "profile";

  /**
   * Specifies that a {@code hr} element should render in one solid color
   * (no shaded), instead of a shaded color.
   *
   * Deprecated. Use styles instead.
   *
   * Applied to tag {@code hr}.
   */
  public static final String ATTR_NOSHADE        = "noshade";

  /**
   * Specifies the namespace to use (only for XHTML documents!).
   *
   * Applied to tag {@code html}.
   */
  public static final String ATTR_XMLNS          = "xmlns";

  /**
   * Specifies the width of the border around an element.
   *
   * Deprecated. Use styles instead.
   *
   * Applied to tag {@code img}, {@code object}, {@code table}.
   */
  public static final String ATTR_BORDER         = "border";

  /**
   * Specifies an image as a server-side image-map
   *
   * Applied to tag {@code img}.
   */
  public static final String ATTR_ISMAP          = "ismap";

  /**
   * Specifies an image as a client-side image-map
   *
   * Applied to tag {@code img}, {@code object}.
   */
  public static final String ATTR_USEMAP         = "usemap";

  /**
   * Specifies that an input element should be preselected when the page loads
   * (for type="checkbox" or type="radio").
   *
   * Applied to tag {@code input}.
   */
  public static final String ATTR_CHECKED        = "checked";

  /**
   * Specifies the maximum length (in characters) of an input field (for
   * type="text" or type="password").
   *
   * Applied to tag {@code input}.
   */
  public static final String ATTR_MAXLENGTH      = "maxlength";

  /**
   * Specifies that the content of an element should be read-only.
   *
   * Applied to tag {@code input}, {@code textarea}.
   */
  public static final String ATTR_READONLY       = "readonly";

  /**
   * Specifies which form element a label is bound to.
   *
   * Applied to tag {@code label}.
   */
  public static final String ATTR_FOR            = "for";

  /**
   * Specifies on what device the linked document will be displayed.
   *
   * Applied to tag {@code link}, {@code style}.
   */
  public static final String ATTR_MEDIA          = "media";

  /**
   * Specifies the content of the meta information.
   *
   * Applied to tag {@code meta}.
   */
  public static final String ATTR_CONTENT        = "content";

  /**
   * Provides an HTTP header for the information in the content attribute.
   *
   * Applied to tag {@code meta}.
   */
  public static final String ATTR_HTTP_EQUIV     = "http-equiv";

  /**
   * Specifies a scheme to be used to interpret the value of the content
   * attribute.
   *
   * Applied to tag {@code meta}.
   */
  public static final String ATTR_SCHEME         = "scheme";

  /**
   * Defines a class ID value as set in the Windows Registry or a URL.
   *
   * Applied to tag {@code object}.
   */
  public static final String ATTR_CLASSID        = "classid";

  /**
   * The internet media type of the code referred to by the {@code classid}
   * attribute.
   *
   * Applied to tag {@code object}.
   */
  public static final String ATTR_CODETYPE       = "codetype";

  /**
   * Defines a URL that refers to the object's data.
   *
   * Applied to tag {@code object}.
   */
  public static final String ATTR_DATA           = "data";

  /**
   * Defines that the object should only be declared, not created or
   * instantiated until needed.
   *
   * Applied to tag {@code object}.
   */
  public static final String ATTR_DECLARE        = "declare";

  /**
   * Defines a text to display while the object is loading.
   *
   * Applied to tag {@code object}.
   */
  public static final String ATTR_STANDBY        = "standby";

  /**
   * Specifies the start point in a list.
   *
   * Deprecated. Use styles instead.
   *
   * Applied to tag {@code ol}.
   */
  public static final String ATTR_START          = "start";

  /**
   * Specifies a description for a group of options.
   *
   * Applied to tag {@code optgroup}, {@code option}.
   */
  public static final String ATTR_LABEL          = "label";

  /**
   * Specifies that an option should be selected by default.
   *
   * Applied to tag {@code option}.
   */
  public static final String ATTR_SELECTED       = "selected";

  /**
   * Specifies the type of the value.
   *
   * Applied to tag {@code param}.
   */
  public static final String ATTR_VALUETYPE      = "valuetype";

  /**
   * Specifies that the execution of a script should be deferred (delayed) until
   * after the page has been loaded.
   *
   * Applied to tag {@code script}.
   */
  public static final String ATTR_DEFER          = "defer";

  /**
   * Specifies whether whitespace in code should be preserved.
   *
   * Applied to tag {@code script}.
   */
  public static final String ATTR_XML_SPACE      = "xml:space";

  /**
   * Specifies that multiple options can be selected.
   *
   * Applied to tag {@code select}.
   */
  public static final String ATTR_MULTIPLE       = "multiple";

  /**
   * Specifies the space between the cell wall and the cell content.
   *
   * Applied to tag {@code table}.
   */
  public static final String ATTR_CELLPADDING    = "cellpadding";

  /**
   * Specifies the space between cells.
   *
   * Applied to tag {@code table}.
   */
  public static final String ATTR_CELLSPACING    = "cellspacing";

  /**
   * Specifies which parts of the outside borders that should be visible.
   *
   * Applied to tag {@code table}.
   */
  public static final String ATTR_FRAME          = "frame";

  /**
   * Specifies which parts of the inside borders that should be visible.
   *
   * Applied to tag {@code table}.
   */
  public static final String ATTR_RULES          = "rules";

  /**
   * Specifies a summary of the content of a table.
   *
   * Applied to tag {@code table}.
   */
  public static final String ATTR_SUMMARY        = "summary";

  /**
   * Specifies an abbreviated version of the content in a cell.
   *
   * Applied to tag {@code td}, {@code th}.
   */
  public static final String ATTR_ABBR           = "abbr";

  /**
   * Categorizes cells.
   *
   * Applied to tag {@code td}, {@code th}.
   */
  public static final String ATTR_AXIS           = "axis";

  /**
   * Specifies the number of columns a cell should span.
   *
   * Applied to tag {@code td}, {@code th}.
   */
  public static final String ATTR_COLSPAN        = "colspan";

  /**
   * Specifies the table headers related to a cell.
   *
   * Applied to tag {@code td}.
   */
  public static final String ATTR_HEADERS        = "headers";

  /**
   * Specifies that the content inside a cell should not wrap.
   *
   * Deprecated. Use styles instead.
   *
   * Applied to tag {@code td}, {@code th}.
   */
  public static final String ATTR_NOWRAP         = "nowrap";

  /**
   * Sets the number of rows a cell should span.
   *
   * Applied to tag {@code td}, {@code th}.
   */
  public static final String ATTR_ROWSPAN        = "rowspan";

  /**
   * Defines a way to associate header cells and data cells in a table.
   *
   * Applied to tag {@code td}, {@code th}.
   */
  public static final String ATTR_SCOPE          = "scope";

}
