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
package com.github.haixing_hu.reflect.testbed;

/**
 * Bean that has primitive properties.
 *
 * @author Haixing Hu
 */
public class PrimitiveBean {

  private boolean booleanProperty;
  private char charProperty;
  private byte byteProperty;
  private short shortProperty;
  private int intProperty;
  private long longProperty;
  private float floatProperty;
  private double doubleProperty;

  public boolean isBooleanProperty() {
    return booleanProperty;
  }

  public void setBooleanProperty(boolean booleanProperty) {
    this.booleanProperty = booleanProperty;
  }

  public char getCharProperty() {
    return charProperty;
  }

  public void setCharProperty(char charProperty) {
    this.charProperty = charProperty;
  }

  public byte getByteProperty() {
    return byteProperty;
  }

  public void setByteProperty(byte byteProperty) {
    this.byteProperty = byteProperty;
  }

  public short getShortProperty() {
    return shortProperty;
  }

  public void setShortProperty(short shortProperty) {
    this.shortProperty = shortProperty;
  }

  public int getIntProperty() {
    return intProperty;
  }

  public void setIntProperty(int intProperty) {
    this.intProperty = intProperty;
  }

  public long getLongProperty() {
    return longProperty;
  }

  public void setLongProperty(long longProperty) {
    this.longProperty = longProperty;
  }

  public float getFloatProperty() {
    return floatProperty;
  }

  public void setFloatProperty(float floatProperty) {
    this.floatProperty = floatProperty;
  }

  public double getDoubleProperty() {
    return doubleProperty;
  }

  public void setDoubleProperty(double doubleProperty) {
    this.doubleProperty = doubleProperty;
  }
}
