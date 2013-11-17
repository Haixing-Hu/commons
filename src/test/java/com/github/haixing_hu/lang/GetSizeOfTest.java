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

package com.github.haixing_hu.lang;

import org.junit.Test;

import com.github.haixing_hu.lang.SystemUtils;

/**
 * Test the {@link Systems.getSizeOf(Class)} method.
 *
 * @author Haixing Hu
 */
public class GetSizeOfTest {

  public static final class Node {
    public char[] key;
    public int    value;
  }

  @Test
  public void testGetSizeOf() {
    long size = 0;

    size = SystemUtils.getSizeOf(Object.class);
    System.out.println("sizeof(Object) = " + size);

//    size = Systems.getSizeOf(Boolean.class);
//    System.out.println("sizeof(Boolean) = " + size);

//    size = Systems.getSizeOf(Character.class);
//    System.out.println("sizeof(Character) = " + size);

//    size = Systems.getSizeOf(Byte.class);
//    System.out.println("sizeof(Byte) = " + size);
//
//    size = Systems.getSizeOf(Short.class);
//    System.out.println("sizeof(Short) = " + size);
//
//    size = Systems.getSizeOf(Integer.class);
//    System.out.println("sizeof(Integer) = " + size);
//
//    size = Systems.getSizeOf(Long.class);
//    System.out.println("sizeof(Long) = " + size);
//
//    size = Systems.getSizeOf(Float.class);
//    System.out.println("sizeof(Float) = " + size);
//
//    size = Systems.getSizeOf(Double.class);
//    System.out.println("sizeof(Double) = " + size);

    size = SystemUtils.getSizeOf(String.class);
    System.out.println("sizeof(String) = " + size);

//    size = Systems.getSizeOf(int[].class);
//    System.out.println("sizeof(int[]) = " + size);

    size = SystemUtils.getSizeOf(Node.class);
    System.out.println("sizeof(Node) = " + size);
  }
}
