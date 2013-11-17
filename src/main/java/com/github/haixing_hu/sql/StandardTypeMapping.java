/******************************************************************************
 *
 *    Copyright (c) 2009-2012  Ascent Dimension, Inc. All rights reserved.
 *
 ******************************************************************************/

package com.github.haixing_hu.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.github.haixing_hu.net.Url;
import com.github.haixing_hu.sql.error.UnsupportedJavaTypeException;
import com.github.haixing_hu.sql.error.UnsupportedSqlTypeException;

/**
 * Standard mapping between SQL type to Java class.
 *
 * @author Haixing Hu
 * @see <a href="http://docs.oracle.com/javase/6/docs/technotes/guides/jdbc/getstart/mapping.html">Mapping SQL and Java Types</a>
 */
public final class StandardTypeMapping {

  /**
   * The array of all allowed SQL types.
   */
  public static final int SQL_TYPES[] = {
    Types.ARRAY,
    Types.BIGINT,
    Types.BINARY,
    Types.BIT,
    Types.BLOB,
    Types.BOOLEAN,
    Types.CHAR,
    Types.CLOB,
    Types.DATALINK,
    Types.DATE,
    Types.DECIMAL,
    Types.DISTINCT,
    Types.DOUBLE,
    Types.FLOAT,
    Types.INTEGER,
    Types.JAVA_OBJECT,
    Types.LONGNVARCHAR,
    Types.LONGVARBINARY,
    Types.LONGVARCHAR,
    Types.NCHAR,
    Types.NCLOB,
    Types.NULL,
    Types.NUMERIC,
    Types.NVARCHAR,
    Types.OTHER,
    Types.REAL,
    Types.REF,
    Types.ROWID,
    Types.SMALLINT,
    Types.SQLXML,
    Types.STRUCT,
    Types.TIME,
    Types.TIMESTAMP,
    Types.TINYINT,
    Types.VARBINARY,
    Types.VARCHAR,
  };

  private static final Map<Integer, Class<?>> SQL_TO_JAVA_MAP;
  private static final Map<Class<?>, Integer> JAVA_TO_SQL_MAP;
  private static final Map<Integer, String> SQL_NAME_MAP;
  static {
    SQL_TO_JAVA_MAP = new HashMap<Integer, Class<?>>();

    SQL_TO_JAVA_MAP.put(Types.CHAR, String.class);
    SQL_TO_JAVA_MAP.put(Types.VARCHAR, String.class);
    SQL_TO_JAVA_MAP.put(Types.LONGVARCHAR, Reader.class);
    SQL_TO_JAVA_MAP.put(Types.CLOB, Clob.class);

    SQL_TO_JAVA_MAP.put(Types.BINARY, byte[].class);
    SQL_TO_JAVA_MAP.put(Types.VARBINARY, byte[].class);
    SQL_TO_JAVA_MAP.put(Types.LONGVARBINARY, InputStream.class);
    SQL_TO_JAVA_MAP.put(Types.BLOB, Blob.class);

    SQL_TO_JAVA_MAP.put(Types.BIT, Boolean.class);
    SQL_TO_JAVA_MAP.put(Types.BOOLEAN, Boolean.class);

    SQL_TO_JAVA_MAP.put(Types.TINYINT, Byte.class);
    SQL_TO_JAVA_MAP.put(Types.SMALLINT, Short.class);
    SQL_TO_JAVA_MAP.put(Types.INTEGER, Integer.class);
    SQL_TO_JAVA_MAP.put(Types.BIGINT, Long.class);
    SQL_TO_JAVA_MAP.put(Types.REAL, Float.class);
    SQL_TO_JAVA_MAP.put(Types.DOUBLE, Double.class);
    SQL_TO_JAVA_MAP.put(Types.FLOAT, Double.class);

    SQL_TO_JAVA_MAP.put(Types.NUMERIC, BigDecimal.class);
    SQL_TO_JAVA_MAP.put(Types.DECIMAL, BigDecimal.class);

    SQL_TO_JAVA_MAP.put(Types.DATE, Date.class);
    SQL_TO_JAVA_MAP.put(Types.TIME, Time.class);
    SQL_TO_JAVA_MAP.put(Types.TIMESTAMP, Timestamp.class);

    SQL_TO_JAVA_MAP.put(Types.DATALINK, URL.class);

    SQL_TO_JAVA_MAP.put(Types.ARRAY, java.sql.Array.class);
    SQL_TO_JAVA_MAP.put(Types.REF, java.sql.Ref.class);
    SQL_TO_JAVA_MAP.put(Types.STRUCT, java.sql.Struct.class);

    JAVA_TO_SQL_MAP = new HashMap<Class<?>, Integer>();

    JAVA_TO_SQL_MAP.put(String.class, Types.VARCHAR);
    JAVA_TO_SQL_MAP.put(Reader.class, Types.LONGVARCHAR);
    JAVA_TO_SQL_MAP.put(Clob.class, Types.CLOB);

    JAVA_TO_SQL_MAP.put(byte[].class, Types.VARBINARY);
    JAVA_TO_SQL_MAP.put(InputStream.class, Types.LONGVARBINARY);
    JAVA_TO_SQL_MAP.put(Blob.class, Types.BLOB);

    JAVA_TO_SQL_MAP.put(Boolean.class, Types.BOOLEAN);

    JAVA_TO_SQL_MAP.put(Byte.class, Types.TINYINT);
    JAVA_TO_SQL_MAP.put(Short.class, Types.SMALLINT);
    JAVA_TO_SQL_MAP.put(Integer.class, Types.INTEGER);
    JAVA_TO_SQL_MAP.put(Long.class, Types.BIGINT);
    JAVA_TO_SQL_MAP.put(Float.class, Types.REAL);
    JAVA_TO_SQL_MAP.put(Double.class, Types.DOUBLE);

    JAVA_TO_SQL_MAP.put(BigDecimal.class, Types.DECIMAL);

    JAVA_TO_SQL_MAP.put(Date.class, Types.DATE);
    JAVA_TO_SQL_MAP.put(Time.class, Types.TIME);
    JAVA_TO_SQL_MAP.put(Timestamp.class, Types.TIMESTAMP);

    JAVA_TO_SQL_MAP.put(URL.class, Types.DATALINK);
    JAVA_TO_SQL_MAP.put(Url.class, Types.DATALINK);

    JAVA_TO_SQL_MAP.put(java.sql.Array.class, Types.ARRAY);
    JAVA_TO_SQL_MAP.put(java.sql.Ref.class, Types.REF);
    JAVA_TO_SQL_MAP.put(java.sql.Struct.class, Types.STRUCT);

    SQL_NAME_MAP = new HashMap<Integer, String>();
    SQL_NAME_MAP.put(Types.ARRAY, "ARRAY");
    SQL_NAME_MAP.put(Types.BIGINT, "BIGINT");
    SQL_NAME_MAP.put(Types.BINARY, "BINARY");
    SQL_NAME_MAP.put(Types.BIT, "BIT");
    SQL_NAME_MAP.put(Types.BLOB, "BLOB");
    SQL_NAME_MAP.put(Types.BOOLEAN, "BOOLEAN");
    SQL_NAME_MAP.put(Types.CHAR, "CHAR");
    SQL_NAME_MAP.put(Types.CLOB, "CLOB");
    SQL_NAME_MAP.put(Types.DATALINK, "DATALINK");
    SQL_NAME_MAP.put(Types.DATE, "DATE");
    SQL_NAME_MAP.put(Types.DECIMAL, "DECIMAL");
    SQL_NAME_MAP.put(Types.DISTINCT, "DISTINCT");
    SQL_NAME_MAP.put(Types.DOUBLE, "DOUBLE");
    SQL_NAME_MAP.put(Types.FLOAT, "FLOAT");
    SQL_NAME_MAP.put(Types.INTEGER, "INTEGER");
    SQL_NAME_MAP.put(Types.JAVA_OBJECT, "JAVA_OBJECT");
    SQL_NAME_MAP.put(Types.LONGNVARCHAR, "LONGNVARCHAR");
    SQL_NAME_MAP.put(Types.LONGVARBINARY, "LONGVARBINARY");
    SQL_NAME_MAP.put(Types.LONGVARCHAR, "LONGVARCHAR");
    SQL_NAME_MAP.put(Types.NCHAR, "NCHAR");
    SQL_NAME_MAP.put(Types.NCLOB, "NCLOB");
    SQL_NAME_MAP.put(Types.NULL, "NULL");
    SQL_NAME_MAP.put(Types.NUMERIC, "NUMERIC");
    SQL_NAME_MAP.put(Types.NVARCHAR, "NVARCHAR");
    SQL_NAME_MAP.put(Types.OTHER, "OTHER");
    SQL_NAME_MAP.put(Types.REAL, "REAL");
    SQL_NAME_MAP.put(Types.REF, "REF");
    SQL_NAME_MAP.put(Types.ROWID, "ROWID");
    SQL_NAME_MAP.put(Types.SMALLINT, "SMALLINT");
    SQL_NAME_MAP.put(Types.SQLXML, "SQLXML");
    SQL_NAME_MAP.put(Types.STRUCT, "STRUCT");
    SQL_NAME_MAP.put(Types.TIME, "TIME");
    SQL_NAME_MAP.put(Types.TIMESTAMP, "TIMESTAMP");
    SQL_NAME_MAP.put(Types.TINYINT, "TINYINT");
    SQL_NAME_MAP.put(Types.VARBINARY, "VARBINARY");
    SQL_NAME_MAP.put(Types.VARCHAR, "VARCHAR");
  }

  public static String getName(final int sqlType)
      throws UnsupportedSqlTypeException {
    final String result = SQL_NAME_MAP.get(sqlType);
    if (result == null) {
      throw new UnsupportedSqlTypeException(sqlType);
    }
    return result;
  }

  public static Class<?> getJavaType(final int sqlType)
      throws UnsupportedSqlTypeException {
    // FIXME: support the special types: Ref, Array, Struct, etc.
    final Integer key = Integer.valueOf(sqlType);
    final Class<?> cls = SQL_TO_JAVA_MAP.get(key);
    if (cls == null) {
      throw new UnsupportedSqlTypeException(sqlType);
    }
    return cls;
  }

  public static int getSqlType(final Class<?> javaType)
      throws UnsupportedJavaTypeException {
    // FIXME: support the special types: Ref, Array, Struct, etc.
    final Integer result = JAVA_TO_SQL_MAP.get(javaType);
    if (result != null) {
      return result.intValue();
    }
    //  deal with the special types
    if (Clob.class.isAssignableFrom(javaType)) {
      return Types.CLOB;
    }
    if (Blob.class.isAssignableFrom(javaType)) {
      return Types.BLOB;
    }
    if (java.sql.Array.class.isAssignableFrom(javaType)) {
      return Types.ARRAY;
    }
    if (java.sql.Ref.class.isAssignableFrom(javaType)) {
      return Types.REF;
    }
    if (java.sql.Struct.class.isAssignableFrom(javaType)) {
      return Types.STRUCT;
    }
    throw new UnsupportedJavaTypeException(javaType);
  }

}
