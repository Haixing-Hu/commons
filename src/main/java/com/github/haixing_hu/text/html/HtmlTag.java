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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This interface defines the constant of HTML tag names.
 *
 * The name of the constants are of the form <code>TAG_[uppercase tag name]</code>.
 *
 * @author Haixing Hu
 * @see HtmlAttribute
 * @see HtmlAttributeValue
 * @see HtmlEvent
 * @see http://www.w3schools.com/tags/default.asp
 * @see http://reference.sitepoint.com/html/elements
 */
public final class HtmlTag {

  private HtmlTag() {}

  /**
   * Defines an anchor (hyper-link).
   */
  public static final String TAG_A          = "a";

  /**
   * Defines an abbreviation.
   */
  public static final String TAG_ABBR       = "abbr";

  /**
   * Defines an acronym.
   */
  public static final String TAG_ACRONYM    = "acronym";

  /**
   * Defines contact information for the author/owner of a document.
   */
  public static final String TAG_ADDRESS    = "address";

  /**
   * Deprecated. Defines an embedded applet.
   */
  public static final String TAG_APPLET     = "applet";

  /**
   * Defines an area inside an image-map.
   */
  public static final String TAG_AREA       = "area";

  /**
   * Defines bold text.
   */
  public static final String TAG_B          = "b";

  /**
   * Defines a default address or a default target for all links on a page.
   */
  public static final String TAG_BASE       = "base";

  /**
   * Deprecated. Defines a default font, color, or size for the text in a page.
   */
  public static final String TAG_BASEFONT   = "basefont";

  /**
   * Defines the text direction.
   */
  public static final String TAG_BDO        = "bdo";

  /**
   * The <code>bgsound</code> element is used to play an audio file when the
   * page loads and has a handful of attributes to control that audio, including
   * balance, volume, loop and the most important of all, the <code>src</code>
   * attribute which refers to the file required.
   */
  public static final String TAG_BGSOUND    = "bgsound";

  /**
   * Defines big text.
   */
  public static final String TAG_BIG        = "big";

  /**
   * The blink element is not part of any standard and was originally introduced
   * by early versions of the Netscape browser. Its sole purpose is to blink
   * text on and off, and its sole result is the general annoyance to all who
   * come across it. To say it’s disliked is an understatement. Use this only if
   * your intention is to have invalid web pages and if you want your audience
   * to think that they’ve gone back in time, somewhere around the early 90s.
   */
  public static final String TAG_BLINK      = "blink";

  /**
   * Defines a long quotation.
   */
  public static final String TAG_BLOCKQUOTE = "blockquote";

  /**
   * Defines the document's body.
   */
  public static final String TAG_BODY       = "body";

  /**
   * Defines a single line break.
   */
  public static final String TAG_BR         = "br";

  /**
   * Defines a push button.
   */
  public static final String TAG_BUTTON     = "button";

  /**
   * Defines a table caption.
   */
  public static final String TAG_CAPTION    = "caption";

  /**
   * Deprecated. Defines centered text.
   */
  public static final String TAG_CENTER     = "center";

  /**
   * Defines a citation.
   */
  public static final String TAG_CITE       = "cite";

  /**
   * Defines computer code text.
   */
  public static final String TAG_CODE       = "code";

  /**
   * Defines attribute values for one or more columns in a table .
   */
  public static final String TAG_COL        = "col";

  /**
   * Defines a group of columns in a table for formatting.
   */
  public static final String TAG_COLGROUP   = "colgroup";

  /**
   * The comment element is a non-standard and little-supported element that’s
   * used to add comments to markup. It has just one attribute—data—that points
   * to a web page that provides further information about the comment. Moving
   * forward, this element shouldn’t be used. Instead, use the standard HTML
   * comment syntax of <!-- comment here -->.
   */
  public static final String TAG_COMMENT    = "comment";

  /**
   * Defines a description of a term in a definition list.
   */
  public static final String TAG_DD         = "dd";

  /**
   * Defines deleted text.
   */
  public static final String TAG_DEL        = "del";

  /**
   * Defines a definition term.
   */
  public static final String TAG_DFN        = "dfn";

  /**
   * Deprecated. Defines a directory list.
   */
  public static final String TAG_DIR        = "dir";

  /**
   * Defines a section in a document.
   */
  public static final String TAG_DIV        = "div";

  /**
   * Defines a definition list.
   */
  public static final String TAG_DL         = "dl";

  /**
   * Defines a term (an item) in a definition list.
   */
  public static final String TAG_DT         = "dt";

  /**
   * Defines emphasized text.
   */
  public static final String TAG_EM         = "em";

  /**
   * <code>embed</code> is a non-standard but well-supported element that’s used
   * to embed multimedia content, including media types that mightn’t usually be
   * natively supported by the browser. It can also be used for embedding media
   * types that are supported, such as images in .jpg, .gif, or .png format.
   */
  public static final String TAG_EMBED      = "embed";

  /**
   * Defines a border around elements in a form.
   */
  public static final String TAG_FIELDSET   = "fieldset";

  /**
   * Deprecated. Defines font, color, and size for text.
   */
  public static final String TAG_FONT       = "font";

  /**
   * Defines an HTML form for user input.
   */
  public static final String TAG_FORM       = "form";

  /**
   * Defines a window (a frame) in a frameset.
   */
  public static final String TAG_FRAME      = "frame";

  /**
   * Defines a set of frames.
   */
  public static final String TAG_FRAMESET   = "frameset";

  /**
   * Defines HTML heading 1.
   */
  public static final String TAG_H1         = "h1";

  /**
   * Defines HTML heading 2.
   */
  public static final String TAG_H2         = "h2";

  /**
   * Defines HTML heading 3.
   */
  public static final String TAG_H3         = "h3";

  /**
   * Defines HTML heading 4.
   */
  public static final String TAG_H4         = "h4";

  /**
   * Defines HTML heading 5.
   */
  public static final String TAG_H5         = "h5";

  /**
   * Defines HTML heading 6.
   */
  public static final String TAG_H6         = "h6";

  /**
   * Defines information about the document.
   */
  public static final String TAG_HEAD       = "head";

  /**
   * Defines a horizontal line.
   */
  public static final String TAG_HR         = "hr";

  /**
   * Defines an HTML document.
   */
  public static final String TAG_HTML       = "html";

  /**
   * Defines italic text.
   */
  public static final String TAG_I          = "i";

  /**
   * Defines an inline frame.
   */
  public static final String TAG_IFRAME     = "iframe";

  /**
   * Defines an image.
   */
  public static final String TAG_IMG        = "img";

  /**
   * Defines an input control.
   */
  public static final String TAG_INPUT      = "input";

  /**
   * Defines inserted text.
   */
  public static final String TAG_INS        = "ins";

  /**
   * Deprecated. Defines a searchable current related to a document.
   */
  public static final String TAG_ISINDEX    = "isindex";

  /**
   * Defines keyboard text.
   */
  public static final String TAG_KBD        = "kbd";

  /**
   * Defines a label for an <code>input</code> element.
   */
  public static final String TAG_LABEL      = "label";

  /**
   * Defines a caption for a <code>fieldset</code> element.
   */
  public static final String TAG_LEGEND     = "legend";

  /**
   * Defines a list item.
   */
  public static final String TAG_LI         = "li";

  /**
   * Defines the relationship between a document and an external resource.
   */
  public static final String TAG_LINK       = "link";

  /**
   * The marquee element provides a way for browsers to render text that moves
   * across the page without having to resort to JavaScript techniques. The
   * marquee is non-standard but enjoys (or possibly suffers from) good browser
   * support.
   */
  public static final String TAG_MARQUEE    = "marquee";

  /**
   * Defines an image-map.
   */
  public static final String TAG_MAP        = "map";

  /**
   * Deprecated. Defines a menu list.
   */
  public static final String TAG_MENU       = "menu";

  /**
   * Defines metadata about an HTML document.
   */
  public static final String TAG_META       = "meta";

  /**
   * The nobr element is a proprietary (as in not based on any standard) one
   * that was used to define sections of text that the browser should not allow
   * to wrap, regardless of what may happen, for example the user resizing the
   * window to a small viewport. As it is deprecated - and the effects can be
   * achieved using CSS - you should not use the element; this is here for
   * reference only.
   */
  public static final String TAG_NOBR       = "nobr";

  /**
   * The <code>noembed</code> element is used to provide alternative content for
   * browsers that don’t support the <code>embed</code> element. It’s not
   * defined by any standard (it was introduced by early Netscape browsers), and
   * as such there aren’t any guidelines as to what it may or may not contain.
   */
  public static final String TAG_NOEMBED    = "noembed";

  /**
   * Defines an alternate content for users that do not support frames.
   */
  public static final String TAG_NOFRAMES   = "noframes";

  /**
   * Defines an alternate content for users that do not support client-side
   * scripts.
   */
  public static final String TAG_NOSCRIPT   = "noscript";

  /**
   * Defines an embedded object.
   */
  public static final String TAG_OBJECT     = "object";

  /**
   * Defines an ordered list.
   */
  public static final String TAG_OL         = "ol";

  /**
   * Defines a group of related options in a select list.
   */
  public static final String TAG_OPTGROUP   = "optgroup";

  /**
   * Defines an option in a select list.
   */
  public static final String TAG_OPTION     = "option";

  /**
   * Defines a paragraph.
   */
  public static final String TAG_P          = "p";

  /**
   * Defines a parameter for an object.
   */
  public static final String TAG_PARAM      = "param";

  /**
   * The plaintext element was originally intended to instruct the browser to
   * ignore any formatting or HTML markup , such that &lt;p&gt; would appear in
   * screen as &lt;p&gt; rather than actually create a new paragraph. It is
   * deprecated and is probably best forgotten entirely.
   */
  public static final String TAG_PLAINTEXT  = "plaintext";

  /**
   * Defines preformatted text.
   *
   * @see #PREFORMATTED_XMP
   */
  public static final String TAG_PRE        = "pre";

  /**
   * The rb element is a child element of the ruby element, and is used to
   * contain the characters that require pronunciation help or are displayed as
   * a learning aid.
   */
  public static final String TAG_RB         = "rb";

  /**
   * The rbc (ruby base container) element groups a collection of rb elements
   * that will have related annotations in a subsequent rtc container. In the
   * example shown above, which was taken from the W3C documentation, the rbc
   * contains four Japanese characters (the more complex kanji symbols), each of
   * which has its own rb element. Meanwhile, the ruby annotations inside the
   * related rt elements are written in hiragana syllables (known as furigana
   * when used for this purpose). Finally, there’s an English annotation that
   * spans all four of the previous rb and rt elements.
   */
  public static final String TAG_RBC        = "rbc";

  /**
   * Despite having been defined as far back as 2001, ruby doesn’t enjoy great
   * support in browsers. For a change, Internet Explorer is ahead of the game
   * on this one! Created in light of the fact that not all browsers will
   * understand ruby, the rp element may be used to present content to users who
   * are viewing the ruby text on a browser that doesn’t understand or support
   * ruby, but removes that content for browsers that do support ruby. The
   * content inside the rp element should be an opening or closing parenthesis,
   * although there’s no definitive rule about which character should be used.
   * It’s most likely that you’ll use "(",")","[" or "]".
   */
  public static final String TAG_RP         = "rp";

  /**
   * The rt element is a child element of the ruby element and contains the
   * annotation that will be displayed to the user on screen, ideally beside or
   * above the base text (rb) which requires the annotation.
   */
  public static final String TAG_RT         = "rt";

  /**
   * The rtc (ruby text container) element groups a collection of rt elements
   * that hold the annotations related to the contents of the rbc container. The
   * example above contains four Japanese characters (the more complex Kanji
   * symbols), each with its own rb element, while the ruby annotations inside
   * the related rt elements are written in hiragana syllables (known as
   * furigana when used for this purpose). In the example shown above, which was
   * taken from the W3C documentation, there are two rtc elements that provide
   * Japanese and English annotations.
   */
  public static final String TAG_RTC        = "rtc";

  /**
   * The ruby element provides a mechanism for annotating characters of East
   * Asian languages (Japanese, Chinese, Korean, and so on). Typically, these
   * annotations appear in a smaller typeface above or beside the regular text.
   */
  public static final String TAG_RUBY       = "ruby";

  /**
   * Defines a short quotation.
   */
  public static final String TAG_Q          = "q";

  /**
   * Deprecated. Defines strikethrough text.
   *
   * @see #STRIKE
   */
  public static final String TAG_S          = "s";

  /**
   * Defines sample computer code.
   */
  public static final String TAG_SAMP       = "samp";

  /**
   * Defines a client-side script.
   */
  public static final String TAG_SCRIPT     = "script";

  /**
   * Defines a select list (drop-down list).
   */
  public static final String TAG_SELECT     = "select";

  /**
   * Defines small text.
   */
  public static final String TAG_SMALL      = "small";

  /**
   * Defines a section in a document.
   */
  public static final String TAG_SPAN       = "span";

  /**
   * Deprecated. Defines strike-through text.
   *
   * @see #STRIKE_SHORT
   */
  public static final String TAG_STRIKE     = "strike";

  /**
   * Defines strong text.
   */
  public static final String TAG_STRONG     = "strong";

  /**
   * Defines style information for a document,
   */
  public static final String TAG_STYLE      = "style";

  /**
   * Defines subscripted text.
   */
  public static final String TAG_SUB        = "sub";

  /**
   * Defines superscripted text.
   */
  public static final String TAG_SUP        = "sup";

  /**
   * Defines a table.
   */
  public static final String TAG_TABLE      = "table";

  /**
   * Groups the body content in a table.
   */
  public static final String TAG_TBODY      = "tbody";

  /**
   * Defines a cell in a table.
   */
  public static final String TAG_TD         = "td";

  /**
   * Defines a multi-line text <code>input</code> control.
   */
  public static final String TAG_TEXTAREA   = "textarea";

  /**
   * Groups the footer content in a table.
   */
  public static final String TAG_TFOOT      = "tfoot";

  /**
   * Defines a header cell in a table.
   */
  public static final String TAG_TH         = "th";

  /**
   * Groups the header content in a table.
   */
  public static final String TAG_THEAD      = "thead";

  /**
   * Defines the title of a document.
   */
  public static final String TAG_TITLE      = "title";

  /**
   * Defines a row in a table.
   */
  public static final String TAG_TR         = "tr";

  /**
   * Defines teletype text.
   */
  public static final String TAG_TT         = "tt";

  /**
   * Deprecated. Defines underlined text.
   */
  public static final String TAG_U          = "u";

  /**
   * Defines an unordered list.
   */
  public static final String TAG_UL         = "ul";

  /**
   * Defines a variable part of a text.
   */
  public static final String TAG_VAR        = "var";

  /**
   * The wbr element’s purpose is to suggest/hint to the browser where within a
   * word/phrase would be the most appropriate point for it to be broken
   * (indicated with a hyphen) in the event that the browser viewport or
   * containing element is reduced in size such that wrapping occurs.
   */
  public static final String TAG_WBR        = "wbr";

  /**
   * Deprecated. Defines preformatted text.
   *
   * @see #PREFORMATTED
   */
  public static final String TAG_XMP        = "xmp";

  /**
   * The set of structural elements.
   */
  public static final Set<String> STRUCTURAL_ELEMENTS;
  static {
    final Set<String> structuralElements = new HashSet<String>(14);
    structuralElements.add(TAG_BLOCKQUOTE);
    structuralElements.add(TAG_BODY);
    structuralElements.add(TAG_BR);
    structuralElements.add(TAG_DIV);
    structuralElements.add(TAG_H1);
    structuralElements.add(TAG_H2);
    structuralElements.add(TAG_H3);
    structuralElements.add(TAG_H4);
    structuralElements.add(TAG_H5);
    structuralElements.add(TAG_H6);
    structuralElements.add(TAG_HEAD);
    structuralElements.add(TAG_HR);
    structuralElements.add(TAG_HTML);
    structuralElements.add(TAG_P);

    STRUCTURAL_ELEMENTS = Collections.unmodifiableSet(structuralElements);
  }

  /**
   * The set of head elements.
   */
  public static final Set<String> HEAD_ELEMENTS;
  static {
    final Set<String> headElements = new HashSet<String>(6);
    headElements.add(TAG_BASE);
    headElements.add(TAG_LINK);
    headElements.add(TAG_META);
    headElements.add(TAG_SCRIPT);
    headElements.add(TAG_STYLE);
    headElements.add(TAG_TITLE);
    HEAD_ELEMENTS = Collections.unmodifiableSet(headElements);
  }

  /**
   * The set of list elements.
   */
  public static final Set<String> LIST_ELEMENTS;
  static {
    final Set<String> listElements = new HashSet<String>(8);
    listElements.add(TAG_DD);
    listElements.add(TAG_DIR);
    listElements.add(TAG_DL);
    listElements.add(TAG_DT);
    listElements.add(TAG_LI);
    listElements.add(TAG_MENU);
    listElements.add(TAG_OL);
    listElements.add(TAG_UL);
    LIST_ELEMENTS = Collections.unmodifiableSet(listElements);
  }

  /**
   * The set of text formatting elements.
   */
  public static final Set<String> TEXT_FORMATTING_ELEMENTS;
  static {
    final Set<String> textFormattingElements = new HashSet<String>(45);
    textFormattingElements.add(TAG_A);
    textFormattingElements.add(TAG_ABBR);
    textFormattingElements.add(TAG_ACRONYM);
    textFormattingElements.add(TAG_ADDRESS);
    textFormattingElements.add(TAG_B);
    textFormattingElements.add(TAG_BASEFONT);
    textFormattingElements.add(TAG_BDO);
    textFormattingElements.add(TAG_BIG);
    textFormattingElements.add(TAG_BLINK);
    textFormattingElements.add(TAG_CENTER);
    textFormattingElements.add(TAG_CITE);
    textFormattingElements.add(TAG_CODE);
    textFormattingElements.add(TAG_COMMENT);
    textFormattingElements.add(TAG_DEL);
    textFormattingElements.add(TAG_DFN);
    textFormattingElements.add(TAG_EM);
    textFormattingElements.add(TAG_FONT);
    textFormattingElements.add(TAG_I);
    textFormattingElements.add(TAG_INS);
    textFormattingElements.add(TAG_KBD);
    textFormattingElements.add(TAG_MARQUEE);
    textFormattingElements.add(TAG_NOBR);
    textFormattingElements.add(TAG_NOSCRIPT);
    textFormattingElements.add(TAG_PLAINTEXT);
    textFormattingElements.add(TAG_PRE);
    textFormattingElements.add(TAG_Q);
    textFormattingElements.add(TAG_RB);
    textFormattingElements.add(TAG_RBC);
    textFormattingElements.add(TAG_RP);
    textFormattingElements.add(TAG_RT);
    textFormattingElements.add(TAG_RTC);
    textFormattingElements.add(TAG_RUBY);
    textFormattingElements.add(TAG_S);
    textFormattingElements.add(TAG_SAMP);
    textFormattingElements.add(TAG_SMALL);
    textFormattingElements.add(TAG_SPAN);
    textFormattingElements.add(TAG_STRIKE);
    textFormattingElements.add(TAG_STRONG);
    textFormattingElements.add(TAG_SUB);
    textFormattingElements.add(TAG_SUP);
    textFormattingElements.add(TAG_TT);
    textFormattingElements.add(TAG_U);
    textFormattingElements.add(TAG_VAR);
    textFormattingElements.add(TAG_WBR);
    textFormattingElements.add(TAG_XMP);
    TEXT_FORMATTING_ELEMENTS = Collections.unmodifiableSet(textFormattingElements);
  }

  /**
   * The set of form elements.
   */
  public static final Set<String> FORM_ELEMENTS;
  static {
    final Set<String> formElements = new HashSet<String>(11);
    formElements.add(TAG_BUTTON);
    formElements.add(TAG_FIELDSET);
    formElements.add(TAG_FORM);
    formElements.add(TAG_INPUT);
    formElements.add(TAG_ISINDEX);
    formElements.add(TAG_LABEL);
    formElements.add(TAG_LEGEND);
    formElements.add(TAG_OPTGROUP);
    formElements.add(TAG_OPTION);
    formElements.add(TAG_SELECT);
    formElements.add(TAG_TEXTAREA);
    FORM_ELEMENTS = Collections.unmodifiableSet(formElements);
  }

  /**
   * The set of image and media elements.
   */
  public static final Set<String> MEDIA_ELEMENTS;
  static {
    final Set<String> mediaElements = new HashSet<String>(9);
    mediaElements.add(TAG_APPLET);
    mediaElements.add(TAG_AREA);
    mediaElements.add(TAG_BGSOUND);
    mediaElements.add(TAG_EMBED);
    mediaElements.add(TAG_IMG);
    mediaElements.add(TAG_MAP);
    mediaElements.add(TAG_NOEMBED);
    mediaElements.add(TAG_OBJECT);
    mediaElements.add(TAG_PARAM);
    MEDIA_ELEMENTS = Collections.unmodifiableSet(mediaElements);
  }

  /**
   * The set of table elements.
   */
  public static final Set<String> TABLE_ELEMENTS;
  static {
    final Set<String> tableElements = new HashSet<String>(10);
    tableElements.add(TAG_CAPTION);
    tableElements.add(TAG_COL);
    tableElements.add(TAG_COLGROUP);
    tableElements.add(TAG_TABLE);
    tableElements.add(TAG_TBODY);
    tableElements.add(TAG_TD);
    tableElements.add(TAG_TFOOT);
    tableElements.add(TAG_TH);
    tableElements.add(TAG_THEAD);
    tableElements.add(TAG_TR);
    TABLE_ELEMENTS = Collections.unmodifiableSet(tableElements);
  }

  /**
   * The set of frame and window elements.
   */
  public static final Set<String> FRAME_ELEMENTS;
  static {
    final Set<String> frameElements = new HashSet<String>(4);
    frameElements.add(TAG_FRAME);
    frameElements.add(TAG_FRAMESET);
    frameElements.add(TAG_NOFRAMES);
    frameElements.add(TAG_IFRAME);
    FRAME_ELEMENTS = Collections.unmodifiableSet(frameElements);
  }


}
