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
 * A public base class.
 *
 * @author Haixing Hu
 */
public class PublicBase implements Interface {

  public static final String VALUE_2 = "PublicBase.VALUE_2";

  protected static String VALUE_3 = "PublicBase.VALUE_3";

  @SuppressWarnings("unused")
  private static String VALUE_4 = "PublicBase.VALUE_4";

  public String field_1;

  private String field_2;

  private boolean field_3;

  protected String field_4;

  String field_5;

  public PublicBase() {
    System.out.println("PublicBase()");
    field_1 = "PublicBase.field_1";
    field_2 = "PublicBase.field_2";
    field_3 = true;
    field_4 = "PublicBase.field_4";
    field_5 = "PublicBase.field_5";
  }

  public String getField_1() {
    return field_1;
  }

  public void setField_1(String field_1) {
    this.field_1 = field_1;
  }

  public String getField_2() {
    return field_2;
  }

  public void setField_2(String field_2) {
    this.field_2 = field_2;
  }

  public boolean isField_3() {
    return field_3;
  }

  public void setField_3(boolean field_3) {
    this.field_3 = field_3;
  }

  public String getField_4() {
    return field_4;
  }

  public void setField_4(String field_4) {
    this.field_4 = field_4;
  }

  public String getField_5() {
    return field_5;
  }

  public void setField_5(String field_5) {
    this.field_5 = field_5;
  }

}
