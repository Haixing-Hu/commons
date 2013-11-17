package com.github.haixing_hu.lang;

import java.util.ArrayList;

import org.junit.Test;

import static com.github.haixing_hu.lang.Hash.combine;

import static org.junit.Assert.assertEquals;

public class HashTest {

  static class TestObject {
    private int a;

    public TestObject() {
    }

    public TestObject(final int a) {
      this.a = a;
    }

    @Override
    public boolean equals(final Object o) {
      if (o == null) {
        return false;
      }
      if (o == this) {
        return true;
      }
      if (o.getClass() != getClass()) {
        return false;
      }
      final TestObject rhs = (TestObject) o;
      return (a == rhs.a);
    }

    public void setA(final int a) {
      this.a = a;
    }

    public int getA() {
      return a;
    }
  }

  static public enum TestEnum {
    a,
    b,
    c,
    d,
    e
  };

  @Test
  public void testBoolean() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final boolean[] argarray = { true, false };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          out = intarray1[i] * intarray2[j] + (argarray[k] ? 1 : 0);
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testBooleanArray() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final boolean[][] argarray = { null, { true, true }, { true, false },
        { false, true }, { false, false }, { true, false, false } };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          if (argarray[k] == null) {
            out = intarray1[i] * intarray2[j];
          } else {
            out = intarray1[i];
            for (int l = 0; l < argarray[k].length; ++l) {
              out = out * intarray2[j] + (argarray[k][l] ? 1 : 0);
            }
            ;
          }
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testChar() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final char[] argarray = { Character.MIN_VALUE, Character.MIN_VALUE / 2,
        (char) 0, (char) 1, Character.MAX_VALUE / 2,
        Character.MAX_VALUE };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          out = intarray1[i] * intarray2[j] + argarray[k];
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testCharArray() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final char[][] argarray = { null,
        { Character.MIN_VALUE, Character.MIN_VALUE / 2 },
        { (char) 0 }, { (char) 1, Character.MAX_VALUE / 2 },
        { Character.MAX_VALUE } };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          if (argarray[k] == null) {
            out = intarray1[i] * intarray2[j];
          } else {
            out = intarray1[i];
            for (int l = 0; l < argarray[k].length; ++l) {
              out = out * intarray2[j] + argarray[k][l];
            }
            ;
          }
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testByte() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final byte[] argarray = { Byte.MIN_VALUE, Byte.MIN_VALUE / 2, (byte) -1,
        (byte) 0, (byte) 1, Byte.MAX_VALUE / 2, Byte.MAX_VALUE };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          out = intarray1[i] * intarray2[j] + argarray[k];
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testByteArray() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final byte[][] argarray = { null, { Byte.MIN_VALUE, Byte.MIN_VALUE / 2 },
        { (byte) -1, (byte) 0 }, { (byte) 1, Byte.MAX_VALUE / 2 },
        { Byte.MAX_VALUE } };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          if (argarray[k] == null) {
            out = intarray1[i] * intarray2[j];
          } else {
            out = intarray1[i];
            for (int l = 0; l < argarray[k].length; ++l) {
              out = out * intarray2[j] + argarray[k][l];
            }
            ;
          }
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testShort() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final short[] argarray = { Short.MIN_VALUE, Short.MIN_VALUE / 2,
        (short) -1, (short) 0, (short) 1, Short.MAX_VALUE / 2, Short.MAX_VALUE };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          out = intarray1[i] * intarray2[j] + argarray[k];
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testShortArray() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final short[][] argarray = { null,
        { Short.MIN_VALUE, Short.MIN_VALUE / 2 }, { (short) -1, (short) 0 },
        { (short) 1, Short.MAX_VALUE / 2 }, { Short.MAX_VALUE } };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          if (argarray[k] == null) {
            out = intarray1[i] * intarray2[j];
          } else {
            out = intarray1[i];
            for (int l = 0; l < argarray[k].length; ++l) {
              out = out * intarray2[j] + argarray[k][l];
            }
            ;
          }
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testInt() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] argarray = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          out = intarray1[i] * intarray2[j] + argarray[k];
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testIntArray() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[][] argarray = { null,
        { Integer.MIN_VALUE, Integer.MIN_VALUE / 2 }, { -1, 0 },
        { 1, Integer.MAX_VALUE / 2 }, { Integer.MAX_VALUE } };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          if (argarray[k] == null) {
            out = intarray1[i] * intarray2[j];
          } else {
            out = intarray1[i];
            for (int l = 0; l < argarray[k].length; ++l) {
              out = out * intarray2[j] + argarray[k][l];
            }
            ;
          }
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testLong() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final long[] argarray = { Long.MIN_VALUE, Long.MIN_VALUE / 2, (long) -1,
        (long) 0, (long) 1, Long.MAX_VALUE / 2, Long.MAX_VALUE };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          out = intarray1[i] * intarray2[j]
              + (int) (argarray[k] ^ (argarray[k] >> 32));
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testLongArray() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final long[][] argarray = { null, { Long.MIN_VALUE, Long.MIN_VALUE / 2 },
        { (long) -1, (long) 0 }, { (long) 1, Long.MAX_VALUE / 2 },
        { Long.MAX_VALUE } };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          if (argarray[k] == null) {
            out = intarray1[i] * intarray2[j];
          } else {
            out = intarray1[i];
            for (int l = 0; l < argarray[k].length; ++l) {
              out = out * intarray2[j]
                  + (int) (argarray[k][l] ^ (argarray[k][l] >> 32));
            }
            ;
          }
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testFloat() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final float[] argarray = { Float.MIN_VALUE, Float.MIN_VALUE / 2,
        (float) -1, (float) 0, (float) 1, Float.MAX_VALUE / 2, Float.MAX_VALUE };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          out = intarray1[i] * intarray2[j] + Float.floatToIntBits(argarray[k]);
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testFloatArray() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final float[][] argarray = { null,
        { Float.MIN_VALUE, Float.MIN_VALUE / 2 }, { (float) -1, (float) 0 },
        { (float) 1, Float.MAX_VALUE / 2 }, { Float.MAX_VALUE } };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          if (argarray[k] == null) {
            out = intarray1[i] * intarray2[j];
          } else {
            out = intarray1[i];
            for (int l = 0; l < argarray[k].length; ++l) {
              out = out * intarray2[j] + Float.floatToIntBits(argarray[k][l]);
            }
            ;
          }
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testDouble() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final double[] argarray = { Double.MIN_VALUE, Double.MIN_VALUE / 2,
        (double) -1, (double) 0, (double) 1, Double.MAX_VALUE / 2,
        Double.MAX_VALUE };
    long trans;
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          trans = Double.doubleToLongBits(argarray[k]);
          out = intarray1[i] * intarray2[j] + (int) (trans ^ (trans >> 32));
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testDoubleArray() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final double[][] argarray = { null,
        { Double.MIN_VALUE, Double.MIN_VALUE / 2 },
        { (double) -1, (double) 0 }, { (double) 1, Double.MAX_VALUE / 2 },
        { Double.MAX_VALUE } };
    long trans;
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          if (argarray[k] == null) {
            out = intarray1[i] * intarray2[j];
          } else {
            out = intarray1[i];
            for (int l = 0; l < argarray[k].length; ++l) {
              trans = Double.doubleToLongBits(argarray[k][l]);
              out = out * intarray2[j] + (int) (trans ^ (trans >> 32));
            }
            ;
          }
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testBooleanObject() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final Boolean[] argarray = { true, false };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          out = intarray1[i] * intarray2[j] + argarray[k].hashCode();
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testBooleanObjectArray() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final Boolean[][] argarray = { null, { true, true }, { true, false },
        { false, true }, { false, false }, { true, false, false } };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          if (argarray[k] == null) {
            out = intarray1[i] * intarray2[j];
          } else {
            out = intarray1[i];
            for (int l = 0; l < argarray[k].length; ++l) {
              out = out * intarray2[j]
                  + (argarray[k][l] == null ? 0 : argarray[k][l].hashCode());
            }
            ;
          }
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testCharacter() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final Character[] argarray = { Character.MIN_VALUE,
        Character.MIN_VALUE / 2, (char) 0, (char) 1,
        Character.MAX_VALUE / 2, Character.MAX_VALUE };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          out = intarray1[i] * intarray2[j] + argarray[k].hashCode();
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testCharacterArray() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final Character[][] argarray = { null,
        { Character.MIN_VALUE, Character.MIN_VALUE / 2 },
        { (char) 0 }, { (char) 1, Character.MAX_VALUE / 2 },
        { Character.MAX_VALUE } };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          if (argarray[k] == null) {
            out = intarray1[i] * intarray2[j];
          } else {
            out = intarray1[i];
            for (int l = 0; l < argarray[k].length; ++l) {
              out = out * intarray2[j]
                  + (argarray[k][l] == null ? 0 : argarray[k][l].hashCode());
            }
            ;
          }
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testByteObject() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final Byte[] argarray = { Byte.MIN_VALUE, Byte.MIN_VALUE / 2, (byte) -1,
        (byte) 0, (byte) 1, Byte.MAX_VALUE / 2, Byte.MAX_VALUE };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          out = intarray1[i] * intarray2[j] + argarray[k].hashCode();
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testByteObjectArray() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final Byte[][] argarray = { null, { Byte.MIN_VALUE, Byte.MIN_VALUE / 2 },
        { (byte) -1, (byte) 0 }, { (byte) 1, Byte.MAX_VALUE / 2 },
        { Byte.MAX_VALUE } };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          if (argarray[k] == null) {
            out = intarray1[i] * intarray2[j];
          } else {
            out = intarray1[i];
            for (int l = 0; l < argarray[k].length; ++l) {
              out = out * intarray2[j]
                  + (argarray[k][l] == null ? 0 : argarray[k][l].hashCode());
            }
            ;
          }
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testShortObject() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final Short[] argarray = { Short.MIN_VALUE, Short.MIN_VALUE / 2,
        (short) -1, (short) 0, (short) 1, Short.MAX_VALUE / 2, Short.MAX_VALUE };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          out = intarray1[i] * intarray2[j] + argarray[k].hashCode();
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testShortObjectArray() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final Short[][] argarray = { null,
        { Short.MIN_VALUE, Short.MIN_VALUE / 2 }, { (short) -1, (short) 0 },
        { (short) 1, Short.MAX_VALUE / 2 }, { Short.MAX_VALUE } };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          if (argarray[k] == null) {
            out = intarray1[i] * intarray2[j];
          } else {
            out = intarray1[i];
            for (int l = 0; l < argarray[k].length; ++l) {
              out = out * intarray2[j]
                  + (argarray[k][l] == null ? 0 : argarray[k][l].hashCode());
            }
            ;
          }
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testInteger() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final Integer[] argarray = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1,
        0, 1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          out = intarray1[i] * intarray2[j] + argarray[k].hashCode();
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testIntegerArray() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final Integer[][] argarray = { null,
        { Integer.MIN_VALUE, Integer.MIN_VALUE / 2 }, { -1, 0 },
        { 1, Integer.MAX_VALUE / 2 }, { Integer.MAX_VALUE } };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          if (argarray[k] == null) {
            out = intarray1[i] * intarray2[j];
          } else {
            out = intarray1[i];
            for (int l = 0; l < argarray[k].length; ++l) {
              out = out * intarray2[j]
                  + (argarray[k][l] == null ? 0 : argarray[k][l].hashCode());
            }
            ;
          }
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testLongObject() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final Long[] argarray = { Long.MIN_VALUE, Long.MIN_VALUE / 2, (long) -1,
        (long) 0, (long) 1, Long.MAX_VALUE / 2, Long.MAX_VALUE };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          out = intarray1[i] * intarray2[j] + argarray[k].hashCode();
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testLongObjectArray() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final Long[][] argarray = { null, { Long.MIN_VALUE, Long.MIN_VALUE / 2 },
        { (long) -1, (long) 0 }, { (long) 1, Long.MAX_VALUE / 2 },
        { Long.MAX_VALUE } };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          if (argarray[k] == null) {
            out = intarray1[i] * intarray2[j];
          } else {
            out = intarray1[i];
            for (int l = 0; l < argarray[k].length; ++l) {
              out = out * intarray2[j]
                  + (argarray[k][l] == null ? 0 : argarray[k][l].hashCode());
            }
            ;
          }
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testFloatObject() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final Float[] argarray = { Float.MIN_VALUE, Float.MIN_VALUE / 2,
        (float) -1, (float) 0, (float) 1, Float.MAX_VALUE / 2, Float.MAX_VALUE };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          out = intarray1[i] * intarray2[j] + argarray[k].hashCode();
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testFloatObjectArray() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final Float[][] argarray = { null,
        { Float.MIN_VALUE, Float.MIN_VALUE / 2 }, { (float) -1, (float) 0 },
        { (float) 1, Float.MAX_VALUE / 2 }, { Float.MAX_VALUE } };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          if (argarray[k] == null) {
            out = intarray1[i] * intarray2[j];
          } else {
            out = intarray1[i];
            for (int l = 0; l < argarray[k].length; ++l) {
              out = out * intarray2[j]
                  + (argarray[k][l] == null ? 0 : argarray[k][l].hashCode());
            }
            ;
          }
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testDoubleObject() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final Double[] argarray = { Double.MIN_VALUE, Double.MIN_VALUE / 2,
        (double) -1, (double) 0, (double) 1, Double.MAX_VALUE / 2,
        Double.MAX_VALUE };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          out = intarray1[i] * intarray2[j] + argarray[k].hashCode();
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testDoubleObjectArray() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final Double[][] argarray = { null,
        { Double.MIN_VALUE, Double.MIN_VALUE / 2 },
        { (double) -1, (double) 0 }, { (double) 1, Double.MAX_VALUE / 2 },
        { Double.MAX_VALUE } };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          if (argarray[k] == null) {
            out = intarray1[i] * intarray2[j];
          } else {
            out = intarray1[i];
            for (int l = 0; l < argarray[k].length; ++l) {
              out = out * intarray2[j]
                  + (argarray[k][l] == null ? 0 : argarray[k][l].hashCode());
            }
            ;
          }
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testString() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final String[] argarray = { null, "long", "Looong", "agoooo" };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          if (argarray[k] == null) {
            out = intarray1[i] * intarray2[j];
          } else {
            out = intarray1[i] * intarray2[j] + argarray[k].hashCode();
          }
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testStringArray() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final String[][] argarray = { null, { "long", "Long" },
        { "loooooong", "ago" }, { "quite", "long" }, { "agoooo" } };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          if (argarray[k] == null) {
            out = intarray1[i] * intarray2[j];
          } else {
            out = intarray1[i];
            for (int l = 0; l < argarray[k].length; ++l) {
              out = out * intarray2[j]
                  + (argarray[k][l] == null ? 0 : argarray[k][l].hashCode());
            }
            ;
          }
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testEnum() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final TestEnum[] argarray = { null, TestEnum.a, TestEnum.b, TestEnum.c,
        TestEnum.d, TestEnum.e };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          if (argarray[k] == null) {
            out = intarray1[i] * intarray2[j];
          } else {
            out = intarray1[i] * intarray2[j] + argarray[k].ordinal();
          }
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testEnumArray() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final TestEnum[][] argarray = { null, { TestEnum.a, TestEnum.b },
        { TestEnum.c, TestEnum.d }, { TestEnum.e } };
    int out;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray.length; ++k) {
          if (argarray[k] == null) {
            out = intarray1[i] * intarray2[j];
          } else {
            out = intarray1[i];
            for (int l = 0; l < argarray[k].length; ++l) {
              out = out * intarray2[j]
                  + (argarray[k][l] == null ? 0 : argarray[k][l].ordinal());
            }
            ;
          }
          assertEquals(out, combine(intarray1[i], intarray2[j], argarray[k]));
        }
      }
    }
  }

  @Test
  public void testObject() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final boolean[] argarray1 = { true, false, true, true };
    final char[] argarray2 = { (char) 0, (char) 1,
        Character.MIN_VALUE / 2, Character.MAX_VALUE / 2, Character.MIN_VALUE,
        Character.MAX_VALUE / 2 };
    final byte[] argarray3 = { (byte) 0, (byte) 1, (byte) -1,
        Byte.MIN_VALUE / 2, Byte.MAX_VALUE / 2, Byte.MIN_VALUE, Byte.MAX_VALUE };
    final short[] argarray4 = { (short) 0, (short) 1, (short) -1,
        Short.MIN_VALUE, Short.MIN_VALUE / 2, Short.MAX_VALUE / 2,
        Short.MAX_VALUE };
    final int[] argarray5 = { 0, 1, -1, Integer.MIN_VALUE / 2,
        Integer.MIN_VALUE, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final long[] argarray6 = { (long) 0, (long) 1, (long) -1,
        Long.MIN_VALUE / 2, Long.MIN_VALUE, Long.MAX_VALUE / 2, Long.MAX_VALUE };
    final float[] argarray7 = { (float) 0, (float) 1, (float) -1,
        Float.MIN_VALUE, Float.MIN_VALUE / 2, Float.MAX_VALUE / 2,
        Float.MAX_VALUE };
    final double[] argarray8 = { (double) 0, (double) 1, (double) -1,
        Double.MIN_VALUE, Double.MIN_NORMAL / 2, Double.MAX_VALUE / 2,
        Double.MAX_VALUE };
    final boolean[][] argarray9 = { { true, false }, { true }, { false },
        { false, true } };
    final String arg10 = "long long ago";
    final Class<?> arg11 = null;

    int out;
    long bits;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = 0;
        for (int k = 0; k < argarray1.length; ++k) {
          if (k == 0) {
            out = intarray1[i] * intarray2[j] + (argarray1[k] ? 1 : 0);
          } else {
            out = out * intarray2[j] + (argarray1[k] ? 1 : 0);
          }
        }
        assertEquals(out,
            combine(intarray1[i], intarray2[j], (Object) argarray1));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = 0;
        for (int k = 0; k < argarray2.length; ++k) {
          if (k == 0) {
            out = intarray1[i] * intarray2[j] + argarray2[k];
          } else {
            out = out * intarray2[j] + argarray2[k];
          }
        }
        assertEquals(out,
            combine(intarray1[i], intarray2[j], (Object) argarray2));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = 0;
        for (int k = 0; k < argarray3.length; ++k) {
          if (k == 0) {
            out = intarray1[i] * intarray2[j] + argarray3[k];
          } else {
            out = out * intarray2[j] + argarray3[k];
          }
        }
        assertEquals(out,
            combine(intarray1[i], intarray2[j], (Object) argarray3));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = 0;
        for (int k = 0; k < argarray4.length; ++k) {
          if (k == 0) {
            out = intarray1[i] * intarray2[j] + argarray4[k];
          } else {
            out = out * intarray2[j] + argarray4[k];
          }
        }
        assertEquals(out,
            combine(intarray1[i], intarray2[j], (Object) argarray4));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = 0;
        for (int k = 0; k < argarray5.length; ++k) {
          if (k == 0) {
            out = intarray1[i] * intarray2[j] + argarray5[k];
          } else {
            out = out * intarray2[j] + argarray5[k];
          }
        }
        assertEquals(out,
            combine(intarray1[i], intarray2[j], (Object) argarray5));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = 0;
        for (int k = 0; k < argarray6.length; ++k) {
          if (k == 0) {
            out = intarray1[i] * intarray2[j]
                + (int) (argarray6[k] ^ (argarray6[k] >> 32));
          } else {
            out = out * intarray2[j]
                + (int) (argarray6[k] ^ (argarray6[k] >> 32));
          }
        }
        assertEquals(out,
            combine(intarray1[i], intarray2[j], (Object) argarray6));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = 0;
        for (int k = 0; k < argarray7.length; ++k) {
          if (k == 0) {
            out = intarray1[i] * intarray2[j]
                + Float.floatToIntBits(argarray7[k]);
          } else {
            out = out * intarray2[j] + Float.floatToIntBits(argarray7[k]);
          }
        }
        assertEquals(out,
            combine(intarray1[i], intarray2[j], (Object) argarray7));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = 0;
        for (int k = 0; k < argarray8.length; ++k) {
          bits = Double.doubleToLongBits(argarray8[k]);
          if (k == 0) {
            out = intarray1[i] * intarray2[j] + (int) (bits ^ (bits >> 32));
          } else {
            out = out * intarray2[j] + (int) (bits ^ (bits >> 32));
          }
        }
        assertEquals(out,
            combine(intarray1[i], intarray2[j], (Object) argarray8));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = 0;
        for (int k = 0; k < argarray1.length; ++k) {
          out = combine(intarray1[i], intarray2[j], argarray9);
        }
        assertEquals(out,
            combine(intarray1[i], intarray2[j], (Object) argarray9));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = intarray1[i] * intarray2[j] + arg10.hashCode();
        assertEquals(out, combine(intarray1[i], intarray2[j], (Object) arg10));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = intarray1[i] * intarray2[j];
        assertEquals(out, combine(intarray1[i], intarray2[j], (Object) arg11));
      }
    }
  }

  @Test
  public void testObjectArray() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };

    final boolean[][] argarray1 = { { true }, { false, true }, { true } };
    final char[][] argarray2 = { { (char) 0, (char) 1},
        { Character.MIN_VALUE / 2, Character.MAX_VALUE / 2 },
        { Character.MIN_VALUE, Character.MAX_VALUE / 2 } };
    final byte[][] argarray3 = { { (byte) 0, (byte) 1, (byte) -1 },
        { Byte.MIN_VALUE / 2, Byte.MAX_VALUE / 2 },
        { Byte.MIN_VALUE, Byte.MAX_VALUE } };
    final short[][] argarray4 = { { (short) 0, (short) 1, (short) -1 },
        { Short.MIN_VALUE, Short.MIN_VALUE / 2 },
        { Short.MAX_VALUE / 2, Short.MAX_VALUE } };
    final int[][] argarray5 = { { 0, 1, -1 },
        { Integer.MIN_VALUE / 2, Integer.MIN_VALUE },
        { Integer.MAX_VALUE / 2, Integer.MAX_VALUE } };
    final long[][] argarray6 = { { (long) 0, (long) 1, (long) -1 },
        { Long.MIN_VALUE / 2, Long.MIN_VALUE },
        { Long.MAX_VALUE / 2, Long.MAX_VALUE } };
    final float[][] argarray7 = { { (float) 0, (float) 1, (float) -1 },
        { Float.MIN_VALUE, Float.MIN_VALUE / 2 },
        { Float.MAX_VALUE / 2, Float.MAX_VALUE } };
    final double[][] argarray8 = { { (double) 0, (double) 1, (double) -1 },
        { Double.MIN_VALUE, Double.MIN_NORMAL / 2 },
        { Double.MAX_VALUE / 2, Double.MAX_VALUE } };
    final boolean[][][] argarray9 = { { { true, false }, { true } },
        { { false }, { false, true } } };

    Object[] argarray10 = null;
    int out = 0;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray1.length; ++k) {
          if (k == 0) {
            out = combine(intarray1[i], intarray2[j], argarray1[k]);
          } else {
            out = combine(out, intarray2[j], argarray1[k]);
          }
        }
        assertEquals(out,
            combine(intarray1[i], intarray2[j], (Object[]) argarray1));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray2.length; ++k) {
          if (k == 0) {
            out = combine(intarray1[i], intarray2[j], argarray2[k]);
          } else {
            out = combine(out, intarray2[j], argarray2[k]);
          }
        }
        assertEquals(out,
            combine(intarray1[i], intarray2[j], (Object[]) argarray2));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray3.length; ++k) {
          if (k == 0) {
            out = combine(intarray1[i], intarray2[j], argarray3[k]);
          } else {
            out = combine(out, intarray2[j], argarray3[k]);
          }
        }
        assertEquals(out,
            combine(intarray1[i], intarray2[j], (Object[]) argarray3));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray4.length; ++k) {
          if (k == 0) {
            out = combine(intarray1[i], intarray2[j], argarray4[k]);
          } else {
            out = combine(out, intarray2[j], argarray4[k]);
          }
        }
        assertEquals(out,
            combine(intarray1[i], intarray2[j], (Object[]) argarray4));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray5.length; ++k) {
          if (k == 0) {
            out = combine(intarray1[i], intarray2[j], argarray5[k]);
          } else {
            out = combine(out, intarray2[j], argarray5[k]);
          }
        }
        assertEquals(out,
            combine(intarray1[i], intarray2[j], (Object[]) argarray5));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray6.length; ++k) {
          if (k == 0) {
            out = combine(intarray1[i], intarray2[j], argarray6[k]);
          } else {
            out = combine(out, intarray2[j], argarray6[k]);
          }
        }
        assertEquals(out,
            combine(intarray1[i], intarray2[j], (Object[]) argarray6));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray7.length; ++k) {
          if (k == 0) {
            out = combine(intarray1[i], intarray2[j], argarray7[k]);
          } else {
            out = combine(out, intarray2[j], argarray7[k]);
          }
        }
        assertEquals(out,
            combine(intarray1[i], intarray2[j], (Object[]) argarray7));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray8.length; ++k) {
          if (k == 0) {
            out = combine(intarray1[i], intarray2[j], argarray8[k]);
          } else {
            out = combine(out, intarray2[j], argarray8[k]);
          }
        }
        assertEquals(out,
            combine(intarray1[i], intarray2[j], (Object[]) argarray8));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        for (int k = 0; k < argarray9.length; ++k) {
          if (k == 0) {
            out = combine(intarray1[i], intarray2[j], argarray9[k]);
          } else {
            out = combine(out, intarray2[j], argarray9[k]);
          }
        }
        assertEquals(out,
            combine(intarray1[i], intarray2[j], (Object[]) argarray9));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = intarray1[i] * intarray2[j];
        assertEquals(out,
            combine(intarray1[i], intarray2[j], (Object[]) argarray10));
      }
    }

  }

  @Test
  public void testCollection() {
    final int[] intarray1 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };
    final int[] intarray2 = { Integer.MIN_VALUE, Integer.MIN_VALUE / 2, -1, 0,
        1, Integer.MAX_VALUE / 2, Integer.MAX_VALUE };

    ArrayList<?> argarray0 = null;

    ArrayList<Boolean> argarray1 = new ArrayList<Boolean>();
    argarray1.add(true);
    argarray1.add(false);
    argarray1.add(true);

    ArrayList<Character> argarray2 = new ArrayList<Character>();
    argarray2.add((char) 0);
    argarray2.add((char) 1);
    argarray2.add((char) (Character.MIN_VALUE / 2));
    argarray2.add(Character.MIN_VALUE);
    argarray2.add((char) (Character.MAX_VALUE / 2));
    argarray2.add(Character.MAX_VALUE);

    ArrayList<Byte> argarray3 = new ArrayList<Byte>();
    argarray3.add((byte) 0);
    argarray3.add((byte) -1);
    argarray3.add((byte) 1);
    argarray3.add((byte) (Byte.MIN_VALUE / 2));
    argarray3.add(Byte.MIN_VALUE);
    argarray3.add((byte) (Byte.MAX_VALUE / 2));
    argarray3.add(Byte.MAX_VALUE);

    ArrayList<Short> argarray4 = new ArrayList<Short>();
    argarray4.add((short) 0);
    argarray4.add((short) -1);
    argarray4.add((short) 1);
    argarray4.add((short) (Short.MIN_VALUE / 2));
    argarray4.add(Short.MIN_VALUE);
    argarray4.add((short) (Short.MAX_VALUE / 2));
    argarray4.add(Short.MAX_VALUE);

    ArrayList<Integer> argarray5 = new ArrayList<Integer>();
    argarray5.add((int) 0);
    argarray5.add((int) -1);
    argarray5.add((int) 1);
    argarray5.add((int) (Integer.MIN_VALUE / 2));
    argarray5.add(Integer.MIN_VALUE);
    argarray5.add((int) (Integer.MAX_VALUE / 2));
    argarray5.add(Integer.MAX_VALUE);

    ArrayList<Long> argarray6 = new ArrayList<Long>();
    argarray6.add((long) 0);
    argarray6.add((long) -1);
    argarray6.add((long) 1);
    argarray6.add((long) (Long.MIN_VALUE / 2));
    argarray6.add(Long.MIN_VALUE);
    argarray6.add((long) (Long.MAX_VALUE / 2));
    argarray6.add(Long.MAX_VALUE);

    ArrayList<Float> argarray7 = new ArrayList<Float>();
    argarray7.add((float) 0);
    argarray7.add((float) -1);
    argarray7.add((float) 1);
    argarray7.add((float) (Float.MIN_VALUE / 2));
    argarray7.add(Float.MIN_VALUE);
    argarray7.add((float) (Float.MAX_VALUE / 2));
    argarray7.add(Float.MAX_VALUE);

    ArrayList<Double> argarray8 = new ArrayList<Double>();
    argarray8.add((double) 0);
    argarray8.add((double) -1);
    argarray8.add((double) 1);
    argarray8.add((double) (Double.MIN_VALUE / 2));
    argarray8.add(Double.MIN_VALUE);
    argarray8.add((double) (Double.MAX_VALUE / 2));
    argarray8.add(Double.MAX_VALUE);

    ArrayList<Object> argarray9 = new ArrayList<Object>();
    argarray9.add(argarray1);
    argarray9.add(argarray2);
    argarray9.add(argarray3);
    argarray9.add(argarray4);
    argarray9.add(argarray5);
    argarray9.add(argarray6);
    argarray9.add(argarray7);
    argarray9.add(argarray8);

    int out = 0;

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = intarray1[i] * intarray2[j];
        assertEquals(out, combine(intarray1[i], intarray2[j], argarray0));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = intarray1[i];
        for (Boolean val : argarray1) {
          out = out * intarray2[j] + val.hashCode();
        }
        assertEquals(out, combine(intarray1[i], intarray2[j], argarray1));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = intarray1[i];
        for (Character val : argarray2) {
          out = out * intarray2[j] + val.hashCode();
        }
        assertEquals(out, combine(intarray1[i], intarray2[j], argarray2));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = intarray1[i];
        for (Byte val : argarray3) {
          out = out * intarray2[j] + val.hashCode();
        }
        assertEquals(out, combine(intarray1[i], intarray2[j], argarray3));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = intarray1[i];
        for (Short val : argarray4) {
          out = out * intarray2[j] + val.hashCode();
        }
        assertEquals(out, combine(intarray1[i], intarray2[j], argarray4));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = intarray1[i];
        for (Integer val : argarray5) {
          out = out * intarray2[j] + val.hashCode();
        }
        assertEquals(out, combine(intarray1[i], intarray2[j], argarray5));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = intarray1[i];
        for (Long val : argarray6) {
          out = out * intarray2[j] + val.hashCode();
        }
        assertEquals(out, combine(intarray1[i], intarray2[j], argarray6));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = intarray1[i];
        for (Float val : argarray7) {
          out = out * intarray2[j] + val.hashCode();
        }
        assertEquals(out, combine(intarray1[i], intarray2[j], argarray7));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = intarray1[i];
        for (Double val : argarray8) {
          out = out * intarray2[j] + val.hashCode();
        }
        assertEquals(out, combine(intarray1[i], intarray2[j], argarray8));
      }
    }

    for (int i = 0; i < intarray1.length; ++i) {
      for (int j = 0; j < intarray2.length; ++j) {
        out = intarray1[i];
        for (Object val : argarray9) {
          out = combine(out, intarray2[j], val);
        }
        assertEquals(out, combine(intarray1[i], intarray2[j], argarray9));
      }
    }
  }

}
