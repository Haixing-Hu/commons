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
package com.github.haixing_hu.beans.priv;

/**
 * This class is designed to test the default access jvm problem workaround. The
 * issue is that public methods of a public subclass contained in a default
 * access superclass are returned by reflection but an IllegalAccessException is
 * thrown when they are invoked.
 *
 * This is the default access superclass
 *
 * @author Haixing Hu
 */

class PackageBean {

  /**
   * Package private constructor - can only use factory method to create beans.
   */
  PackageBean() {
    super();
  }

  private String bar = "This is bar";

  public String getBar() {
    return (this.bar);
  }

  public void setBar(String bar) {
    this.bar = bar;
  }
}
