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

package com.github.haixing_hu.reflect;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.github.haixing_hu.reflect.testbed.PrivatelyShadowedChild;
import com.github.haixing_hu.reflect.testbed.PublicChild;
import com.github.haixing_hu.reflect.testbed.PubliclyShadowedChild;
import com.github.haixing_hu.reflect.testbed.StaticContainer;
import com.github.haixing_hu.reflect.testdata.Interface;
import com.github.haixing_hu.reflect.testdata.PublicBase;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests FieldUtils
 */
public class FieldUtilsTest {

    static final String S = "s";
    static final String SS = "ss";
    static final Integer I0 = Integer.valueOf(0);
    static final Integer I1 = Integer.valueOf(1);
    static final Double D0 = Double.valueOf(0.0);
    static final Double D1 = Double.valueOf(1.0);

    private PublicChild publicChild;
    private PubliclyShadowedChild publiclyShadowedChild;
    private PrivatelyShadowedChild privatelyShadowedChild;
    private final Class<?> parentClass = PublicChild.class.getSuperclass();

    @Before
    public void setUp() {
        StaticContainer.reset();
        publicChild = new PublicChild();
        publiclyShadowedChild = new PubliclyShadowedChild();
        privatelyShadowedChild = new PrivatelyShadowedChild();
        publicChild.foo();
        publiclyShadowedChild.foo();
        privatelyShadowedChild.foo();
        System.out.println("parentClass = " + parentClass);
    }

    private <T> void fillSet(Set<T> set, T[] array) {
      set.clear();
      for (final T t : array) {
        set.add(t);
      }
    }

    @Test
    public void testGetAllFields() throws Exception {
      final HashSet<Field> expected = new HashSet<Field>();
      final HashSet<Field> actual = new HashSet<Field>();

      fillSet(expected, new Field[] {
          Interface.class.getDeclaredField("VALUE_1"),
      });
      fillSet(actual, FieldUtils.getAllFields(Interface.class, Option.ALL));
      assertEquals(expected, actual);

      fillSet(expected, new Field[] {});
      fillSet(actual, FieldUtils.getAllFields(Interface.class, Option.STATIC | Option.PRIVATE));
      assertEquals(expected, actual);

      fillSet(expected, new Field[] {});
      fillSet(actual, FieldUtils.getAllFields(Interface.class, Option.STATIC | Option.PACKAGE));
      assertEquals(expected, actual);

      fillSet(expected, new Field[] {});
      fillSet(actual, FieldUtils.getAllFields(Interface.class, Option.STATIC | Option.PROTECTED));
      assertEquals(expected, actual);

      fillSet(expected, new Field[] {
          Interface.class.getDeclaredField("VALUE_1"),
      });
      fillSet(actual, FieldUtils.getAllFields(Interface.class, Option.STATIC | Option.PUBLIC));
      assertEquals(expected, actual);

      fillSet(expected, new Field[] {});
      fillSet(actual, FieldUtils.getAllFields(Interface.class,  Option.ALL_ACCESS));
      assertEquals(expected, actual);


      fillSet(expected, new Field[] {
          Interface.class.getDeclaredField("VALUE_1"),
          PublicBase.class.getDeclaredField("VALUE_2"),
          PublicBase.class.getDeclaredField("VALUE_3"),
          PublicBase.class.getDeclaredField("VALUE_4"),
          PublicBase.class.getDeclaredField("field_1"),
          PublicBase.class.getDeclaredField("field_2"),
          PublicBase.class.getDeclaredField("field_3"),
          PublicBase.class.getDeclaredField("field_4"),
          PublicBase.class.getDeclaredField("field_5"),
      });
      fillSet(actual, FieldUtils.getAllFields(PublicBase.class, Option.ALL));

      // remove the JaCoCo injected field
      final Set<Field> toRemoved = new HashSet<Field>();
      for (final Field field : actual) {
	  if (field.toString().contains("$jacocoData")) {
	      toRemoved.add(field);
	  }
      }
      actual.removeAll(toRemoved);

      assertEquals(expected, actual);

    }

/*
    @Test
    public void testGetField() {
    	Field field = FieldUtils.getField(PublicChild.class, Option.ANCESTOR_PUBLIC, "VALUE");
    	assertNotNull(field);
        assertEquals(Foo.class, field.getDeclaringClass());

        field = FieldUtils.getField(PublicChild.class, Option.ALL, "s");
        assertNotNull(field);
        assertEquals(parentClass, field.getDeclaringClass());

        assertNull(FieldUtils.getField(PublicChild.class, Option.PUBLIC, "s"));
        assertNull(FieldUtils.getField(PublicChild.class, Option.ANCESTOR | Option.PRIVATE, "s"));
        assertNull(FieldUtils.getField(PublicChild.class, Option.ANCESTOR_PUBLIC, "b"));
        assertNull(FieldUtils.getField(PublicChild.class, Option.ANCESTOR_PUBLIC, "i"));
        assertNull(FieldUtils.getField(PublicChild.class, Option.ANCESTOR_PUBLIC, "d"));

        field = FieldUtils.getField(PubliclyShadowedChild.class, Option.ANCESTOR_PUBLIC, "VALUE");
        assertNotNull(field);
        assertEquals(Foo.class, field.getDeclaringClass());

        field = FieldUtils.getField(PubliclyShadowedChild.class, Option.ANCESTOR_PUBLIC, "s");
        assertNotNull(field);
        assertEquals(PubliclyShadowedChild.class, field.getDeclaringClass());

        field = FieldUtils.getField(PubliclyShadowedChild.class, Option.ANCESTOR_PUBLIC, "b");
        assertNotNull(field);
        assertEquals(PubliclyShadowedChild.class, field.getDeclaringClass());

        field = FieldUtils.getField(PubliclyShadowedChild.class, Option.ANCESTOR_PUBLIC, "i");
        assertNotNull(field);
        assertEquals(PubliclyShadowedChild.class, field.getDeclaringClass());

        field = FieldUtils.getField(PubliclyShadowedChild.class, Option.ANCESTOR_PUBLIC, "d");
        assertNotNull(field);
        assertEquals(PubliclyShadowedChild.class, field.getDeclaringClass());

        field = FieldUtils.getField(PrivatelyShadowedChild.class, Option.ANCESTOR_PUBLIC,"VALUE");
        assertNotNull(field);
        assertEquals(Foo.class, field.getDeclaringClass());

        field = FieldUtils.getField(PrivatelyShadowedChild.class, Option.ANCESTOR_PUBLIC,"s");
        assertNotNull(field);
        assertEquals(parentClass, field.getDeclaringClass());

        assertNull(FieldUtils.getField(PrivatelyShadowedChild.class, Option.ANCESTOR_PUBLIC,"b"));
        assertNull(FieldUtils.getField(PrivatelyShadowedChild.class, Option.ANCESTOR_PUBLIC,"i"));
        assertNull(FieldUtils.getField(PrivatelyShadowedChild.class, Option.ANCESTOR_PUBLIC,"d"));

        assertNull(FieldUtils.getField(PublicChild.class, Option.STATIC | Option.PUBLIC, "VALUE"));
        assertNull(FieldUtils.getField(PublicChild.class, Option.NON_STATIC | Option.PUBLIC, "s"));
        assertNull(FieldUtils.getField(PublicChild.class, Option.NON_STATIC | Option.PUBLIC, "b"));
        assertNull(FieldUtils.getField(PublicChild.class, Option.NON_STATIC | Option.PUBLIC, "i"));
        assertNull(FieldUtils.getField(PublicChild.class, Option.NON_STATIC | Option.PUBLIC, "d"));

        assertNull(FieldUtils.getField(PubliclyShadowedChild.class, Option.STATIC | Option.PUBLIC, "VALUE"));

        field = FieldUtils.getField(PubliclyShadowedChild.class, Option.NON_STATIC | Option.PUBLIC, "s");
        assertEquals(PubliclyShadowedChild.class, field.getDeclaringClass());

        field = FieldUtils.getField(PubliclyShadowedChild.class, Option.NON_STATIC | Option.PUBLIC, "b");
        assertEquals(PubliclyShadowedChild.class, field.getDeclaringClass());

        field = FieldUtils.getField(PubliclyShadowedChild.class, Option.NON_STATIC | Option.PUBLIC, "i");
        assertEquals(PubliclyShadowedChild.class, field.getDeclaringClass());

        field = FieldUtils.getField(PubliclyShadowedChild.class, Option.NON_STATIC | Option.PUBLIC, "d");
        assertEquals(PubliclyShadowedChild.class, field.getDeclaringClass());

        assertNull(FieldUtils.getField(PrivatelyShadowedChild.class, Option.STATIC | Option.ALL_ACCESS, "VALUE"));
        assertNull(FieldUtils.getField(PrivatelyShadowedChild.class, Option.NON_STATIC | Option.PUBLIC, "s"));
        assertNull(FieldUtils.getField(PrivatelyShadowedChild.class, Option.NON_STATIC | Option.PUBLIC, "b"));
        assertNull(FieldUtils.getField(PrivatelyShadowedChild.class, Option.NON_STATIC | Option.PUBLIC, "i"));
        assertNull(FieldUtils.getField(PrivatelyShadowedChild.class, Option.NON_STATIC | Option.PUBLIC, "d"));
    }

    @Test(expected=NullPointerException.class)
    public void testGetFieldNullPointerException() {
        FieldUtils.getField(null, Option.DEFAULT, "none");
    }

    @Test(expected=NullPointerException.class)
    public void testGetFieldNullPointerException2() {
        FieldUtils.getField(PublicChild.class, Option.DEFAULT, null);
    }

    @Test
    public void testReadStaticField() throws Exception {
    	final int options = Option.ANCESTOR | Option.STATIC | Option.PUBLIC;
    	final Object value = FieldUtils.readField(Foo.class, options, "VALUE", null);
        assertEquals(Foo.VALUE, value);
    }

    @Test(expected=NullPointerException.class)
    public void testReadStaticFieldNullPointerException() throws Exception {
    	final int options = Option.ANCESTOR | Option.STATIC | Option.PUBLIC;
    	FieldUtils.readField(null, options, "none", null);
    }

    @Test(expected=FieldNotExistException.class)
    public void testReadStaticFieldNotExistException() throws Exception {
    	final int options = Option.ANCESTOR | Option.STATIC | Option.PUBLIC;
        FieldUtils.readField(PublicChild.class, options, "s", null);
    }

    @Test
    public void testReadNamedStaticField() throws Exception {
    	final int options = Option.ANCESTOR | Option.STATIC | Option.PUBLIC;

        assertEquals(Foo.VALUE, FieldUtils.readField(Foo.class, options, "VALUE", null));
        assertEquals(Foo.VALUE, FieldUtils.readField(PubliclyShadowedChild.class, options, "VALUE", null));
        assertEquals(Foo.VALUE, FieldUtils.readField(PrivatelyShadowedChild.class, options, "VALUE", null));
        assertEquals(Foo.VALUE, FieldUtils.readField(PublicChild.class, options, "VALUE", null));

        try {
            FieldUtils.readField(null, options, "none", null);
            fail("null class should cause an NullPointerException");
        } catch (final NullPointerException e) {
            // expected
        }

        try {
            FieldUtils.readField(Foo.class, options, null, null);
            fail("null field name should cause an NullPointerException");
        } catch (final NullPointerException e) {
            // expected
        }

        try {
            FieldUtils.readField(Foo.class, options, "does_not_exist", null);
            fail("a field that doesn't exist should cause an FieldNotExistException");
        } catch (final FieldNotExistException e) {
            // expected
        }

        try {
            FieldUtils.readField(PublicChild.class, options, "s", null);
            fail("non-static field should cause an FieldNotExistException");
        } catch (final FieldNotExistException e) {
            // expected
        }
    }

    @Test
    public void testReadDeclaredNamedStaticField() throws Exception {
    	final int options = Option.ANCESTOR | Option.STATIC | Option.PUBLIC;

        assertEquals(Foo.VALUE, FieldUtils.readField(Foo.class, options, "VALUE", null));
        try {
            assertEquals("child", FieldUtils.readField(PublicChild.class, options, "VALUE", null));
            fail("expected FieldNotExistException");
        } catch (final FieldNotExistException e) {
            // pass
        }
        try {
            assertEquals(Foo.VALUE, FieldUtils.readField(PubliclyShadowedChild.class, options, "VALUE", null));
            fail("expected FieldNotExistException");
        } catch (final FieldNotExistException e) {
            // pass
        }
        try {
            assertEquals(Foo.VALUE, FieldUtils.readField(PrivatelyShadowedChild.class, options, "VALUE", null));
            fail("expected FieldNotExistException");
        } catch (final FieldNotExistException e) {
            // pass
        }
    }

    @Test
    public void testReadField() throws Exception {
        final Field parentS = FieldUtils.getDeclaredField(parentClass, "s");
        assertEquals("s", FieldUtils.readField(parentS, publicChild));
        assertEquals("s", FieldUtils.readField(parentS, publiclyShadowedChild));
        assertEquals("s", FieldUtils.readField(parentS, privatelyShadowedChild));
        final Field parentB = FieldUtils.getDeclaredField(parentClass, "b", true);
        assertEquals(Boolean.FALSE, FieldUtils.readField(parentB, publicChild));
        assertEquals(Boolean.FALSE, FieldUtils.readField(parentB, publiclyShadowedChild));
        assertEquals(Boolean.FALSE, FieldUtils.readField(parentB, privatelyShadowedChild));
        final Field parentI = FieldUtils.getDeclaredField(parentClass, "i", true);
        assertEquals(I0, FieldUtils.readField(parentI, publicChild));
        assertEquals(I0, FieldUtils.readField(parentI, publiclyShadowedChild));
        assertEquals(I0, FieldUtils.readField(parentI, privatelyShadowedChild));
        final Field parentD = FieldUtils.getDeclaredField(parentClass, "d", true);
        assertEquals(D0, FieldUtils.readField(parentD, publicChild));
        assertEquals(D0, FieldUtils.readField(parentD, publiclyShadowedChild));
        assertEquals(D0, FieldUtils.readField(parentD, privatelyShadowedChild));

        try {
            FieldUtils.readField((Field)null, publicChild);
            fail("a null field should cause an IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testReadNamedField() throws Exception {
        assertEquals("s", FieldUtils.readField(publicChild, "s"));
        assertEquals("ss", FieldUtils.readField(publiclyShadowedChild, "s"));
        assertEquals("s", FieldUtils.readField(privatelyShadowedChild, "s"));

        try {
            FieldUtils.readField(publicChild, null);
            fail("a null field name should cause an IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // expected
        }

        try {
            FieldUtils.readField((Object)null, "none");
            fail("a null target should cause an IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // expected
        }

        try {
            assertEquals(Boolean.FALSE, FieldUtils.readField(publicChild, "b"));
            fail("expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        assertEquals(Boolean.TRUE, FieldUtils.readField(publiclyShadowedChild, "b"));
        try {
            assertEquals(Boolean.FALSE, FieldUtils.readField(privatelyShadowedChild, "b"));
            fail("expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            assertEquals(I0, FieldUtils.readField(publicChild, "i"));
            fail("expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        assertEquals(I1, FieldUtils.readField(publiclyShadowedChild, "i"));
        try {
            assertEquals(I0, FieldUtils.readField(privatelyShadowedChild, "i"));
            fail("expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            assertEquals(D0, FieldUtils.readField(publicChild, "d"));
            fail("expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        assertEquals(D1, FieldUtils.readField(publiclyShadowedChild, "d"));
        try {
            assertEquals(D0, FieldUtils.readField(privatelyShadowedChild, "d"));
            fail("expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
    }

    @Test
    public void testReadDeclaredNamedField() throws Exception {
        try {
            FieldUtils.readDeclaredField(publicChild, null);
            fail("a null field name should cause an IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // expected
        }

        try {
            FieldUtils.readDeclaredField((Object)null, "none");
            fail("a null target should cause an IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // expected
        }

        try {
            assertEquals("s", FieldUtils.readDeclaredField(publicChild, "s"));
            fail("expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        assertEquals("ss", FieldUtils.readDeclaredField(publiclyShadowedChild, "s"));
        try {
            assertEquals("s", FieldUtils.readDeclaredField(privatelyShadowedChild, "s"));
            fail("expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            assertEquals(Boolean.FALSE, FieldUtils.readDeclaredField(publicChild, "b"));
            fail("expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        assertEquals(Boolean.TRUE, FieldUtils.readDeclaredField(publiclyShadowedChild, "b"));
        try {
            assertEquals(Boolean.FALSE, FieldUtils.readDeclaredField(privatelyShadowedChild, "b"));
            fail("expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            assertEquals(I0, FieldUtils.readDeclaredField(publicChild, "i"));
            fail("expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        assertEquals(I1, FieldUtils.readDeclaredField(publiclyShadowedChild, "i"));
        try {
            assertEquals(I0, FieldUtils.readDeclaredField(privatelyShadowedChild, "i"));
            fail("expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            assertEquals(D0, FieldUtils.readDeclaredField(publicChild, "d"));
            fail("expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        assertEquals(D1, FieldUtils.readDeclaredField(publiclyShadowedChild, "d"));
        try {
            assertEquals(D0, FieldUtils.readDeclaredField(privatelyShadowedChild, "d"));
            fail("expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
    }

    @Test
    public void testWriteStaticField() throws Exception {
        Field field = StaticContainer.class.getDeclaredField("mutablePublic");
        FieldUtils.writeStaticField(field, "new");
        assertEquals("new", StaticContainer.mutablePublic);
        field = StaticContainer.class.getDeclaredField("mutableProtected");
        try {
            FieldUtils.writeStaticField(field, "new");
            fail("Expected IllegalAccessException");
        } catch (final IllegalAccessException e) {
            // pass
        }
        field = StaticContainer.class.getDeclaredField("mutablePackage");
        try {
            FieldUtils.writeStaticField(field, "new");
            fail("Expected IllegalAccessException");
        } catch (final IllegalAccessException e) {
            // pass
        }
        field = StaticContainer.class.getDeclaredField("mutablePrivate");
        try {
            FieldUtils.writeStaticField(field, "new");
            fail("Expected IllegalAccessException");
        } catch (final IllegalAccessException e) {
            // pass
        }
        field = StaticContainer.class.getDeclaredField("IMMUTABLE_PUBLIC");
        try {
            FieldUtils.writeStaticField(field, "new");
            fail("Expected IllegalAccessException");
        } catch (final IllegalAccessException e) {
            // pass
        }
        field = StaticContainer.class.getDeclaredField("IMMUTABLE_PROTECTED");
        try {
            FieldUtils.writeStaticField(field, "new");
            fail("Expected IllegalAccessException");
        } catch (final IllegalAccessException e) {
            // pass
        }
        field = StaticContainer.class.getDeclaredField("IMMUTABLE_PACKAGE");
        try {
            FieldUtils.writeStaticField(field, "new");
            fail("Expected IllegalAccessException");
        } catch (final IllegalAccessException e) {
            // pass
        }
        field = StaticContainer.class.getDeclaredField("IMMUTABLE_PRIVATE");
        try {
            FieldUtils.writeStaticField(field, "new");
            fail("Expected IllegalAccessException");
        } catch (final IllegalAccessException e) {
            // pass
        }
    }

    @Test
    public void testWriteNamedStaticField() throws Exception {
        FieldUtils.writeStaticField(StaticContainerChild.class, "mutablePublic", "new");
        assertEquals("new", StaticContainer.mutablePublic);
        try {
            FieldUtils.writeStaticField(StaticContainerChild.class, "mutableProtected", "new");
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            FieldUtils.writeStaticField(StaticContainerChild.class, "mutablePackage", "new");
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            FieldUtils.writeStaticField(StaticContainerChild.class, "mutablePrivate", "new");
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            FieldUtils.writeStaticField(StaticContainerChild.class, "IMMUTABLE_PUBLIC", "new");
            fail("Expected IllegalAccessException");
        } catch (final IllegalAccessException e) {
            // pass
        }
        try {
            FieldUtils.writeStaticField(StaticContainerChild.class, "IMMUTABLE_PROTECTED", "new");
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            FieldUtils.writeStaticField(StaticContainerChild.class, "IMMUTABLE_PACKAGE", "new");
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            FieldUtils.writeStaticField(StaticContainerChild.class, "IMMUTABLE_PRIVATE", "new");
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
    }

    @Test
    public void testWriteDeclaredNamedStaticField() throws Exception {
        FieldUtils.writeStaticField(StaticContainer.class, "mutablePublic", "new");
        assertEquals("new", StaticContainer.mutablePublic);
        try {
            FieldUtils.writeDeclaredStaticField(StaticContainer.class, "mutableProtected", "new");
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            FieldUtils.writeDeclaredStaticField(StaticContainer.class, "mutablePackage", "new");
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            FieldUtils.writeDeclaredStaticField(StaticContainer.class, "mutablePrivate", "new");
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            FieldUtils.writeDeclaredStaticField(StaticContainer.class, "IMMUTABLE_PUBLIC", "new");
            fail("Expected IllegalAccessException");
        } catch (final IllegalAccessException e) {
            // pass
        }
        try {
            FieldUtils.writeDeclaredStaticField(StaticContainer.class, "IMMUTABLE_PROTECTED", "new");
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            FieldUtils.writeDeclaredStaticField(StaticContainer.class, "IMMUTABLE_PACKAGE", "new");
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            FieldUtils.writeDeclaredStaticField(StaticContainer.class, "IMMUTABLE_PRIVATE", "new");
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
    }

    @Test
    public void testWriteField() throws Exception {
        Field field = parentClass.getDeclaredField("s");
        FieldUtils.writeField(field, publicChild, "S");
        assertEquals("S", field.get(publicChild));
        field = parentClass.getDeclaredField("b");
        try {
            FieldUtils.writeField(field, publicChild, Boolean.TRUE);
            fail("Expected IllegalAccessException");
        } catch (final IllegalAccessException e) {
            // pass
        }
        field = parentClass.getDeclaredField("i");
        try {
            FieldUtils.writeField(field, publicChild, Integer.valueOf(Integer.MAX_VALUE));
        } catch (final IllegalAccessException e) {
            // pass
        }
        field = parentClass.getDeclaredField("d");
        try {
            FieldUtils.writeField(field, publicChild, Double.valueOf(Double.MAX_VALUE));
        } catch (final IllegalAccessException e) {
            // pass
        }
    }

    @Test
    public void testWriteNamedField() throws Exception {
        FieldUtils.writeField(publicChild, "s", "S");
        assertEquals("S", FieldUtils.readField(publicChild, "s"));
        try {
            FieldUtils.writeField(publicChild, "b", Boolean.TRUE);
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            FieldUtils.writeField(publicChild, "i", Integer.valueOf(1));
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            FieldUtils.writeField(publicChild, "d", Double.valueOf(1.0));
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }

        FieldUtils.writeField(publiclyShadowedChild, "s", "S");
        assertEquals("S", FieldUtils.readField(publiclyShadowedChild, "s"));
        FieldUtils.writeField(publiclyShadowedChild, "b", Boolean.FALSE);
        assertEquals(Boolean.FALSE, FieldUtils.readField(publiclyShadowedChild, "b"));
        FieldUtils.writeField(publiclyShadowedChild, "i", Integer.valueOf(0));
        assertEquals(Integer.valueOf(0), FieldUtils.readField(publiclyShadowedChild, "i"));
        FieldUtils.writeField(publiclyShadowedChild, "d", Double.valueOf(0.0));
        assertEquals(Double.valueOf(0.0), FieldUtils.readField(publiclyShadowedChild, "d"));

        FieldUtils.writeField(privatelyShadowedChild, "s", "S");
        assertEquals("S", FieldUtils.readField(privatelyShadowedChild, "s"));
        try {
            FieldUtils.writeField(privatelyShadowedChild, "b", Boolean.TRUE);
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            FieldUtils.writeField(privatelyShadowedChild, "i", Integer.valueOf(1));
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            FieldUtils.writeField(privatelyShadowedChild, "d", Double.valueOf(1.0));
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
    }

    @Test
    public void testWriteDeclaredNamedField() throws Exception {
        try {
            FieldUtils.writeDeclaredField(publicChild, "s", "S");
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            FieldUtils.writeDeclaredField(publicChild, "b", Boolean.TRUE);
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            FieldUtils.writeDeclaredField(publicChild, "i", Integer.valueOf(1));
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            FieldUtils.writeDeclaredField(publicChild, "d", Double.valueOf(1.0));
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }

        FieldUtils.writeDeclaredField(publiclyShadowedChild, "s", "S");
        assertEquals("S", FieldUtils.readDeclaredField(publiclyShadowedChild, "s"));
        FieldUtils.writeDeclaredField(publiclyShadowedChild, "b", Boolean.FALSE);
        assertEquals(Boolean.FALSE, FieldUtils.readDeclaredField(publiclyShadowedChild, "b"));
        FieldUtils.writeDeclaredField(publiclyShadowedChild, "i", Integer.valueOf(0));
        assertEquals(Integer.valueOf(0), FieldUtils.readDeclaredField(publiclyShadowedChild, "i"));
        FieldUtils.writeDeclaredField(publiclyShadowedChild, "d", Double.valueOf(0.0));
        assertEquals(Double.valueOf(0.0), FieldUtils.readDeclaredField(publiclyShadowedChild, "d"));

        try {
            FieldUtils.writeDeclaredField(privatelyShadowedChild, "s", "S");
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            FieldUtils.writeDeclaredField(privatelyShadowedChild, "b", Boolean.TRUE);
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            FieldUtils.writeDeclaredField(privatelyShadowedChild, "i", Integer.valueOf(1));
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        try {
            FieldUtils.writeDeclaredField(privatelyShadowedChild, "d", Double.valueOf(1.0));
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // pass
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAmbig() {
        FieldUtils.getField(Ambig.class, "VALUE");
    }
*/
}
