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
package com.github.haixing_hu.lang;

import org.junit.Test;

import com.github.haixing_hu.lang.Assignment;
import com.github.haixing_hu.lang.Cloneable;

import static org.junit.Assert.assertEquals;

/**
 * Unit test of the {@link Assignment} class.
 *
 * @author Haixing Hu
 */
public class AssignmentTest {

  static class A implements Cloneable<A> {

    protected int x;

    public A(final int x) {
      this.x = x;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + x;
      return result;
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final A other = (A) obj;
      if (x != other.x) {
        return false;
      }
      return true;
    }

    @Override
    public A clone() {
      return new A(x);
    }
  }

  static class B extends A {

    private final int y;

    public B(final int x, final int y) {
      super(x);
      this.y = y;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + y;
      return result;
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (! super.equals(obj)) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final B other = (B) obj;
      if (y != other.y) {
        return false;
      }
      return true;
    }

    @Override
    public B clone() {
      return new B(x, y);
    }
  }

  @Test
  public void testCloneCloneable() {
    A a = null;
    B b = null;
    assertEquals(a, Assignment.clone(a));
    assertEquals(b, Assignment.clone(b));

    a = new A(1);
    b = new B(2, 3);
    assertEquals(a, Assignment.clone(a));
    assertEquals(b, Assignment.clone(b));
  }

  // TODO: finish test of other functions

}
