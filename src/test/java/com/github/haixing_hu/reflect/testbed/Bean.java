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

package com.github.haixing_hu.reflect.testbed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * General purpose test bean for the unit test.
 *
 * @author Haixing Hu
 */
public class Bean implements Serializable {

  private static final long serialVersionUID = - 6708163537454039977L;

  /*
   * Another nested reference to a bean containing mapp properties
   */
  public class MappedTestBean {
    public void setValue(String key, String val) {
    }

    public String getValue(String key) {
      return "Mapped Value";
    }
  }

  /**
   * The set of property names we expect to have returned when calling
   * <code>getPropertyDescriptors()</code>.  You should update this list
   * when new properties are added to TestBean.
   */
  public final static String[] PROPERTIES = {
      "booleanProperty",
      "booleanSecond",
      "charProperty",
      "byteProperty",
      "shortProperty",
      "intProperty",
      "longProperty",
      "floatProperty",
      "doubleProperty",
      "stringProperty",
      "dateProperty",
      "nullProperty",
      "readOnlyProperty",
      "writeOnlyProperty",
      "intArray",
      "intIndexed",
      "dateArrayProperty",
      "stringArray",
      "stringIndexed",
      "dupProperty",
      "string2dArray",
      "listIndexed",
      "mapProperty",
     // "mappedObjects",
     // "mappedProperty",
   //   "mappedIntProperty",
      "nested",
      "anotherNested",
      "mappedNested",
      "invalidBoolean",
      // NOTE that EVERY class has a implicit property: class, since the Object
      // has a getClass() method.
      "class",
  };

  /**
   * A boolean property.
   */
  private boolean booleanProperty = true;

  /**
   * A boolean property that uses an "is" method for the getter.
   */
  private boolean booleanSecond = true;

  /**
   * A char property.
   */
  private char charProperty = 'x';

  /**
   * A byte property.
   */
  private byte byteProperty = (byte) 121;

  /**
   * A short property.
   */
  private short shortProperty = (short) 987;

  /**
   * A int property.
   */
  private int intProperty = 123;

  /**
   * A long property.
   */
  private long longProperty = 321;

  /**
   * A float property.
   */
  private float floatProperty = (float) 123.0;

  /**
   * A double property.
   */
  private double doubleProperty = 321.0;

  /**
   * A String property.
   */
  private String stringProperty = "This is a string";

  /**
   * A {@link java.util.Date} property.
   */
  private Date dateProperty;

  /**
   * A String property with an initial value of null.
   */
  private String nullProperty = null;

  /**
   * A read-only String property.
   */
  private String readOnlyProperty = "Read Only String Property";

  /**
   * A write-only String property.
   */
  @SuppressWarnings("unused")
  private String writeOnlyProperty = "Write Only String Property";

  /**
   * An integer array property accessed as an array.
   */
  private int intArray[] = { 0, 10, 20, 30, 40 };

  /**
   * An integer array property accessed as an indexed property.
   */
  private int intIndexed[] = { 0, 10, 20, 30, 40 };

  /**
   * A {@link java.util.Date} array property.
   */
  private Date[] dateArrayProperty;

  /**
   * A String array property accessed as a String array.
   */
  private String[] stringArray = {
      "String 0", "String 1", "String 2", "String 3", "String 4"
  };

  /**
   * A String array property accessed as an indexed property.
   */
  private String[] stringIndexed = {
      "String 0", "String 1", "StriinvalidBooleanng 2", "String 3", "String 4"
  };

  /**
   * An "indexed property" accessible via both array and subscript
   * based getters and setters.
   */
  private String[] dupProperty = {
      "Dup 0", "Dup 1", "Dup 2", "Dup 3", "Dup 4"
  };

  /**
   * A two-dimension string array proeprty.
   */
  private String[][] string2dArray = new String[][] {
      new String[] { "1", "2", "3" },
      new String[] { "4", "5", "6" }
  };

  /**
   * A List property accessed as an indexed property.
   */
  private List<String> listIndexed = new ArrayList<String>();

  /**
   * A mapped property with only a getter and setter for a Map.
   */
  private Map<String, String> mapProperty = null;

  /**
   * A mapped property that has String keys and Object values.
   */
  private HashMap<String, Object> mappedObjects = null;

  /**
   * A mapped property that has String keys and String values.
   */
  private HashMap<String, String> mappedProperty = null;

  /**
   * A mapped property that has String keys and int values.
   */
  private HashMap<String, Integer> mappedIntProperty = null;

  /**
   * A nested reference to another test bean (populated as needed).
   */
  private Bean nested = null;

  /**
   * Another nested reference to another test bean,
   */
  private Bean anotherNested = null;

  /**
   * Another nested reference to a bean containing mapped properties
   */
  private MappedTestBean mappedNested = null;

  /**
   * An invalid property that has two boolean getters (getInvalidBoolean and
   * isInvalidBoolean) plus a String setter (setInvalidBoolean). By the rules
   * described in the JavaBeans Specification, this will be considered a
   * read-only boolean property, using isInvalidBoolean() as the getter.
   */
  private boolean invalidBoolean = false;

  public Bean() {
    listIndexed.add("String 0");
    listIndexed.add("String 1");
    listIndexed.add("String 2");
    listIndexed.add("String 3");
    listIndexed.add("String 4");
  }

  public Bean(String stringProperty) {
    setStringProperty(stringProperty);
  }

  public Bean(float floatProperty) {
    setFloatProperty(floatProperty);
  }

  public Bean(boolean booleanProperty) {
    setBooleanProperty(booleanProperty);
  }

  public Bean(Boolean booleanSecond) {
    setBooleanSecond(booleanSecond.booleanValue());
  }

  public Bean(float floatProperty, String stringProperty) {
    setFloatProperty(floatProperty);
    setStringProperty(stringProperty);
  }

  public Bean(boolean booleanProperty, String stringProperty) {
    setBooleanProperty(booleanProperty);
    setStringProperty(stringProperty);
  }

  public Bean(Boolean booleanSecond, String stringProperty) {
    setBooleanSecond(booleanSecond.booleanValue());
    setStringProperty(stringProperty);
  }

  public Bean(Integer intProperty) {
    setIntProperty(intProperty.intValue());
  }

  public Bean(double doubleProperty) {
    setDoubleProperty(doubleProperty);
  }

  Bean(int intProperty) {
    setIntProperty(intProperty);
  }

  protected Bean(boolean booleanProperty, boolean booleanSecond,
      String stringProperty) {
    setBooleanProperty(booleanProperty);
    setBooleanSecond(booleanSecond);
    setStringProperty(stringProperty);
  }

  public Bean(List<String> listIndexed) {
    this.listIndexed = listIndexed;
  }

  public Bean(String[][] string2dArray) {
    this.string2dArray = string2dArray;
  }

  public boolean getBooleanProperty() {
    return booleanProperty;
  }

  public void setBooleanProperty(boolean booleanProperty) {
    this.booleanProperty = booleanProperty;
  }

  public boolean isBooleanSecond() {
    return booleanSecond;
  }

  public void setBooleanSecond(boolean booleanSecond) {
    this.booleanSecond = booleanSecond;
  }

  public char getCharProperty() {
    return charProperty;
  }

  public void setCharProperty(char charProperty) {
    this.charProperty = charProperty;
  }

  public byte getByteProperty() {
    return this.byteProperty;
  }

  public void setByteProperty(byte byteProperty) {
    this.byteProperty = byteProperty;
  }

  public short getShortProperty() {
    return this.shortProperty;
  }

  public void setShortProperty(short shortProperty) {
    this.shortProperty = shortProperty;
  }

  public int getIntProperty() {
    return this.intProperty;
  }

  public void setIntProperty(int intProperty) {
    this.intProperty = intProperty;
  }

  public long getLongProperty() {
    return this.longProperty;
  }

  public void setLongProperty(long longProperty) {
    this.longProperty = longProperty;
  }

  public float getFloatProperty() {
    return this.floatProperty;
  }

  public void setFloatProperty(float floatProperty) {
    this.floatProperty = floatProperty;
  }

  public double getDoubleProperty() {
    return this.doubleProperty;
  }

  public void setDoubleProperty(double doubleProperty) {
    this.doubleProperty = doubleProperty;
  }

  public String getStringProperty() {
    return this.stringProperty;
  }

  public void setStringProperty(String stringProperty) {
    this.stringProperty = stringProperty;
  }

  public java.util.Date getDateProperty() {
    return dateProperty;
  }

  public void setDateProperty(java.util.Date dateProperty) {
    this.dateProperty = dateProperty;
  }

  public String getNullProperty() {
    return this.nullProperty;
  }

  public void setNullProperty(String nullProperty) {
    this.nullProperty = nullProperty;
  }

  public String getReadOnlyProperty() {
    return this.readOnlyProperty;
  }

  public void setWriteOnlyProperty(String writeOnlyProperty) {
    this.writeOnlyProperty = writeOnlyProperty;
  }

  public int[] getIntArray() {
    return this.intArray;
  }

  public void setIntArray(int[] intArray) {
    this.intArray = intArray;
  }

  public int getIntIndexed(int index) {
    return (intIndexed[index]);
  }

  public void setIntIndexed(int index, int value) {
    intIndexed[index] = value;
  }

  public java.util.Date[] getDateArrayProperty() {
    return dateArrayProperty;
  }

  public void setDateArrayProperty(java.util.Date[] dateArrayProperty) {
    this.dateArrayProperty = dateArrayProperty;
  }

  public String[] getStringArray() {
    return this.stringArray;
  }

  public void setStringArray(String[] stringArray) {
    this.stringArray = stringArray;
  }

  public String getStringIndexed(int index) {
    return (stringIndexed[index]);
  }

  public void setStringIndexed(int index, String value) {
    stringIndexed[index] = value;
  }

  public String[] getDupProperty() {
    return this.dupProperty;
  }

  public String getDupProperty(int index) {
    return (this.dupProperty[index]);
  }

  public void setDupProperty(int index, String value) {
    this.dupProperty[index] = value;
  }

  public void setDupProperty(String[] dupProperty) {
    this.dupProperty = dupProperty;
  }

  public String[] getString2dArray(int index) {
    return string2dArray[index];
  }

  public List<String> getListIndexed() {
    return listIndexed;
  }

  public Map<String, String> getMapProperty() {
    // Create the map the very first time
    if (mapProperty == null) {
      mapProperty = new HashMap<String, String>();
      mapProperty.put("First Key", "First Value");
      mapProperty.put("Second Key", "Second Value");
    }
    return mapProperty;
  }

  public void setMapProperty(Map<String, String> mapProperty) {
    // Create the map the very first time
    if (mapProperty == null) {
      mapProperty = new HashMap<String, String>();
      mapProperty.put("First Key", "First Value");
      mapProperty.put("Second Key", "Second Value");
    }
    this.mapProperty = mapProperty;
  }

  public Object getMappedObjects(String key) {
    // Create the map the very first time
    if (mappedObjects == null) {
      mappedObjects = new HashMap<String, Object>();
      mappedObjects.put("First Key", "First Value");
      mappedObjects.put("Second Key", "Second Value");
    }
    return (mappedObjects.get(key));
  }

  public void setMappedObjects(String key, Object value) {
    // Create the map the very first time
    if (mappedObjects == null) {
      mappedObjects = new HashMap<String, Object>();
      mappedObjects.put("First Key", "First Value");
      mappedObjects.put("Second Key", "Second Value");
    }
    mappedObjects.put(key, value);
  }

  public String getMappedProperty(String key) {
    // Create the map the very first time
    if (mappedProperty == null) {
      mappedProperty = new HashMap<String, String>();
      mappedProperty.put("First Key", "First Value");
      mappedProperty.put("Second Key", "Second Value");
    }
    return mappedProperty.get(key);
  }

  public void setMappedProperty(String key, String value) {
    // Create the map the very first time
    if (mappedProperty == null) {
      mappedProperty = new HashMap<String, String>();
      mappedProperty.put("First Key", "First Value");
      mappedProperty.put("Second Key", "Second Value");
    }
    mappedProperty.put(key, value);
  }

  public int getMappedIntProperty(String key) {
    // Create the map the very first time
    if (mappedIntProperty == null) {
      mappedIntProperty = new HashMap<String, Integer>();
      mappedIntProperty.put("One", new Integer(1));
      mappedIntProperty.put("Two", new Integer(2));
    }
    Integer x = mappedIntProperty.get(key);
    return ((x == null) ? 0 : x.intValue());
  }

  public void setMappedIntProperty(String key, int value) {
    mappedIntProperty.put(key, new Integer(value));
  }

  public Bean getNested() {
    if (nested == null) {
      nested = new Bean();
    }
    return nested;
  }

  public Bean getAnotherNested() {
    return anotherNested;
  }

  public void setAnotherNested(Bean anotherNested) {
    this.anotherNested = anotherNested;
  }

  public MappedTestBean getMappedNested() {
    if (mappedNested == null) {
      mappedNested = new MappedTestBean();
    }
    return mappedNested;
  }

  public boolean getInvalidBoolean() {
    return this.invalidBoolean;
  }

  public boolean isInvalidBoolean() {
    return this.invalidBoolean;
  }

  public void setInvalidBoolean(String invalidBoolean) {
    if ("true".equalsIgnoreCase(invalidBoolean)
        || "yes".equalsIgnoreCase(invalidBoolean)
        || "1".equalsIgnoreCase(invalidBoolean)) {
      this.invalidBoolean = true;
    } else {
      this.invalidBoolean = false;
    }
  }

  // Static Variables

  // Static Variables

  /**
   * A static variable that is accessed and updated via static methods for
   * MethodUtils testing.
   */
  private static int counter = 0;

  /**
   * Return the current value of the counter.
   */
  public static int currentCounter() {
    return counter;
  }

  /**
   * Increment the current value of the counter by 1.
   */
  public static void incrementCounter() {
    incrementCounter(1);
  }

  /**
   * Increment the current value of the counter by the specified amount.
   *
   * @param amount
   *          Amount to be added to the current counter
   */
  public static void incrementCounter(int amount) {
    counter += amount;
  }

  /**
   * Increments the current value of the count by the specified amount * 2. It
   * has the same name as the method above so as to test the looseness of
   * getMethod.
   */
  public static void incrementCounter(Number amount) {
    counter += 2 * amount.intValue();
  }

}
