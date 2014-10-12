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
package com.github.haixing_hu.reflect.testdata;

/**
 * A public derived class.
 *
 * @author Haixing Hu
 */
public class PublicDerived extends PublicBase {

  public static final String VALUE_5 = "PublicDerived.VALUE_5";

  protected static String VALUE_6 = "PublicDerived.VALUE_6";

  @SuppressWarnings("unused")
  private static String VALUE_7 = "PublicDerived.VALUE_7";

  public String field_6;

  private String field_7;

  private boolean field_8;

  protected String field_9;

  String field_10;

  private String field_1;

  public PublicDerived() {
    System.out.println("PublicDerived()");
    field_6 = "PublicDerived.field_6";
    field_7 = "PublicDerived.field_7";
    field_8 = true;
    field_9 = "PublicDerived.field_9";
    field_10 = "PublicDerived.field_10";
    field_1 = "PublicDerived.field_1";
  }

  public String getField_6() {
    return field_6;
  }

  public void setField_6(String field_6) {
    this.field_6 = field_6;
  }

  public String getField_7() {
    return field_7;
  }

  public void setField_7(String field_7) {
    this.field_7 = field_7;
  }

  public boolean isField_8() {
    return field_8;
  }

  public void setField_8(boolean field_8) {
    this.field_8 = field_8;
  }

  public String getField_9() {
    return field_9;
  }

  public void setField_9(String field_9) {
    this.field_9 = field_9;
  }

  public String getField_10() {
    return field_10;
  }

  public void setField_10(String field_10) {
    this.field_10 = field_10;
  }

  @Override
  public String getField_1() {
    return field_1;
  }

  @Override
  public void setField_1(String field_1) {
    this.field_1 = field_1;
  }

}
