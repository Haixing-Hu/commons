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
 */package com.github.haixing_hu.lang;

import com.github.haixing_hu.lang.CharUtils;

/**
 * The base class for the unit test of Strings class.
 *
 * @author Haixing Hu
 */
public class StringUtilsTestBase {

  protected static final String WHITESPACE;
  protected static final String NON_WHITESPACE;
  protected static final String TRIMMABLE;
  protected static final String NON_TRIMMABLE;
  protected static final String BLANK;
  protected static final String NON_BLANK;
  protected static final String NON_WHITE_SPACE_BUT_BLANK;


  static {
    final StringBuilder whitespace = new StringBuilder();
    final StringBuilder non_whitespace = new StringBuilder();
    final StringBuilder blank = new StringBuilder();
    final StringBuilder non_blank = new StringBuilder();
    final StringBuilder trimmable = new StringBuilder();
    final StringBuilder non_trimmable = new StringBuilder();
    final StringBuilder non_whitespace_but_blank = new StringBuilder();

    for (int i = 0; i < Character.MAX_VALUE; i++) {
      boolean is_whitespace;
      boolean is_blank;
      if (Character.isWhitespace(i)) {
        is_whitespace = true;
        whitespace.appendCodePoint(i);
        if (i > 32) {
          non_trimmable.appendCodePoint(i);
        }
      } else {
        is_whitespace = false;
        if (i < 40) {
          non_whitespace.appendCodePoint(i);
        }
      }
      if (CharUtils.isBlank(i)) {
        is_blank = true;
        // append an extra space to avoid the successive surrogate pairs
        blank.appendCodePoint(i).append(' ');
      } else {
        is_blank = false;
        non_blank.appendCodePoint(i);
      }
      if ((is_whitespace == false) && (is_blank == true)) {
        // append an extra space to avoid the successive surrogate pairs
        non_whitespace_but_blank.appendCodePoint(i).append(' ');
      }
    }
    for (int i = 0; i <= 32; i++) {
      trimmable.appendCodePoint(i);
    }
    WHITESPACE = whitespace.toString();
    NON_WHITESPACE = non_whitespace.toString();
    TRIMMABLE = trimmable.toString();
    NON_TRIMMABLE = non_trimmable.toString();
    BLANK = blank.toString();
    NON_BLANK = non_blank.toString();
    NON_WHITE_SPACE_BUT_BLANK = non_whitespace_but_blank.toString();
  }

  protected static final String[] ARRAY_LIST = { "foo", "bar", "baz" };
  protected static final String[] EMPTY_ARRAY_LIST = {};
  protected static final String[] NULL_ARRAY_LIST = {null};
  protected static final String[] MIXED_ARRAY_LIST = {null, "", "foo"};
  protected static final Object[] MIXED_TYPE_LIST = {new String("foo"), new Long(2)};

  protected static final String SEPARATOR = ",";
  protected static final char   SEPARATOR_CHAR = ';';

  protected static final String TEXT_LIST = "foo,bar,baz";
  protected static final String TEXT_LIST_CHAR = "foo;bar;baz";
  protected static final String TEXT_LIST_NOSEP = "foobarbaz";

  protected static final String FOO_UNCAP = "foo";
  protected static final String FOO_CAP = "Foo";

  protected static final String SENTENCE_UNCAP = "foo bar baz";
  protected static final String SENTENCE_CAP = "Foo Bar Baz";

  protected static final String FOO = "foo";
  protected static final String BAR = "bar";
  protected static final String FOOBAR = "foobar";
  protected static final String CAP_FOO    = "FOO";
  protected static final String CAP_BAR    = "BAR";
  protected static final String CAP_FOOBAR = "FOOBAR";

  protected static final String[] FOOBAR_SUB_ARRAY = new String[] {"ob", "ba"};
  protected static final String BAZ = "baz";
  protected static final String SENTENCE = "foo bar baz";


}
