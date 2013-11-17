/******************************************************************************
 *
 *    Copyright (c) 2009-2012  Ascent Dimension, Inc. All rights reserved.
 *
 ******************************************************************************/

package com.github.haixing_hu.sql;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Types;

import javax.annotation.Nullable;

import com.github.haixing_hu.lang.Equality;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

import static java.sql.DatabaseMetaData.*;
import static java.sql.Types.*;

import static com.github.haixing_hu.lang.Argument.*;

/**
 * Stores the information about a type in the database.
 * <p>
 * An object of {@link TypeInfo} corresponds to a row in the returned
 * {@link ResultSet} of the {@link DatabaseMetaData#getTypeInfo()} function.
 * </p>
 *
 * @author Haixing Hu
 * @see DatabaseMetaData#getTypeInfo()
 */
public final class TypeInfo {

  public static final int[] DATA_TYPE_ENUM = {
    ARRAY,
    BIGINT,
    BINARY,
    BIT,
    BLOB,
    BOOLEAN,
    CHAR,
    CLOB,
    DATALINK,
    DATE,
    DECIMAL,
    DISTINCT,
    DOUBLE,
    FLOAT,
    INTEGER,
    JAVA_OBJECT,
    LONGNVARCHAR,
    LONGVARBINARY,
    LONGVARCHAR,
    NCHAR,
    NCLOB,
    NUMERIC,
    NVARCHAR,
    OTHER,
    REAL,
    REF,
    ROWID,
    SMALLINT,
    SQLXML,
    STRUCT,
    TIME,
    TIMESTAMP,
    TINYINT,
    VARBINARY,
    VARCHAR,
  };

  public static final short[] NULLABLE_ENUM = {
    typeNoNulls,
    typeNullable,
    typeNullableUnknown,
  };

  public static final short[] SEARCHABLE_ENUM = {
    typePredNone,
    typePredChar,
    typePredBasic,
    typeSearchable,
  };

  public static final String TYPE_NAME = "TYPE_NAME";

  public static final String DATA_TYPE = "DATA_TYPE";

  public static final String PRECISION = "PRECISION";

  public static final String LITERAL_PREFIX = "LITERAL_PREFIX";

  public static final String LITERAL_SUFFIX = "LITERAL_SUFFIX";

  public static final String CREATE_PARAMS = "CREATE_PARAMS";

  public static final String NULLABLE = "NULLABLE";

  public static final String CASE_SENSITIVE = "CASE_SENSITIVE";

  public static final String SEARCHABLE = "SEARCHABLE";

  public static final String UNSIGNED_ATTRIBUTE = "UNSIGNED_ATTRIBUTE";

  public static final String FIXED_PREC_SCALE = "FIXED_PREC_SCALE";

  public static final String AUTO_INCREMENT = "AUTO_INCREMENT";

  public static final String LOCAL_TYPE_NAME = "LOCAL_TYPE_NAME";

  public static final String MINIMUM_SCALE = "MINIMUM_SCALE";

  public static final String MAXIMUM_SCALE = "MAXIMUM_SCALE";

  public static final String SQL_DATA_TYPE = "SQL_DATA_TYPE";

  public static final String SQL_DATETIME_SUB = "SQL_DATETIME_SUB";

  public static final String NUM_PREC_RADIX = "NUM_PREC_RADIX";

  private String typeName;
  private int dataType;
  private int precision;
  private String literalPrefix;
  private String literalSuffix;
  private String createParams;
  private short nullable;
  private boolean caseSensitive;
  private short searchable;
  private boolean unsignedAttribute;
  private boolean fixedPrecScale;
  private boolean autoIncrement;
  private String localTypeName;
  private short minimumScale;
  private short maximumScale;
  private int sqlDataType;
  private int sqlDatetimeSub;
  private int numPrecRadix;

  public TypeInfo() {
    typeName = StringUtils.EMPTY;
    dataType = Types.OTHER;
    precision = 0;
    literalPrefix = null;
    literalSuffix = null;
    createParams = null;
    nullable = typeNullableUnknown;
    caseSensitive = true;
    searchable = typePredNone;
    unsignedAttribute = false;
    fixedPrecScale = false;
    autoIncrement = false;
    localTypeName = null;
    minimumScale = 0;
    maximumScale = 0;
    sqlDataType = 0;
    sqlDatetimeSub = 0;
    numPrecRadix = 10;
  }

  public String getTypeName() {
    return typeName;
  }

  protected void setTypeName(final String typeName) {
    this.typeName = requireNonNull("typeName", typeName);
  }

  public int getDataType() {
    return dataType;
  }

  protected void setDataType(final int dataType) {
    this.dataType = requireInEnum("dataType", dataType, DATA_TYPE_ENUM);
  }

  public int getPrecision() {
    return precision;
  }

  protected void setPrecision(final int precision) {
    this.precision = requireGreaterEqual("precision", precision, "zero", 0);
  }

  public String getLiteralPrefix() {
    return literalPrefix;
  }

  protected void setLiteralPrefix(@Nullable final String literalPrefix) {
    this.literalPrefix = literalPrefix;
  }

  public String getLiteralSuffix() {
    return literalSuffix;
  }

  protected void setLiteralSuffix(@Nullable final String literalSuffix) {
    this.literalSuffix = literalSuffix;
  }

  public String getCreateParams() {
    return createParams;
  }

  protected void setCreateParams(@Nullable final String createParams) {
    this.createParams = createParams;
  }

  public short getNullable() {
    return nullable;
  }

  protected void setNullable(final short nullable) {
    this.nullable = requireInEnum("nullable", nullable, NULLABLE_ENUM);
  }

  public boolean isCaseSensitive() {
    return caseSensitive;
  }

  protected void setCaseSensitive(final boolean caseSensitive) {
    this.caseSensitive = caseSensitive;
  }

  public short getSearchable() {
    return searchable;
  }

  protected void setSearchable(final short searchable) {
    this.searchable = requireInEnum("searchable", searchable, SEARCHABLE_ENUM);
  }

  public boolean isUnsignedAttribute() {
    return unsignedAttribute;
  }

  protected void setUnsignedAttribute(final boolean unsignedAttribute) {
    this.unsignedAttribute = unsignedAttribute;
  }

  public boolean isFixedPrecScale() {
    return fixedPrecScale;
  }

  protected void setFixedPrecScale(final boolean fixedPrecScale) {
    this.fixedPrecScale = fixedPrecScale;
  }

  public boolean isAutoIncrement() {
    return autoIncrement;
  }

  protected void setAutoIncrement(final boolean autoIncrement) {
    this.autoIncrement = autoIncrement;
  }

  public String getLocalTypeName() {
    return localTypeName;
  }

  protected void setLocalTypeName(@Nullable final String localTypeName) {
    this.localTypeName = localTypeName;
  }

  public short getMinimumScale() {
    return minimumScale;
  }

  protected void setMinimumScale(final short minimumScale) {
    this.minimumScale = requireGreaterEqual("minimumScale", minimumScale,
        "zero", (short)0);
  }

  public short getMaximumScale() {
    return maximumScale;
  }

  protected void setMaximumScale(final short maximumScale) {
    this.maximumScale = requireGreaterEqual("maximumScale", maximumScale,
        "zero", (short)0);
  }

  public int getSqlDataType() {
    return sqlDataType;
  }

  protected void setSqlDataType(final int sqlDataType) {
    this.sqlDataType = sqlDataType;
  }

  public int getSqlDatetimeSub() {
    return sqlDatetimeSub;
  }

  protected void setSqlDatetimeSub(final int sqlDatetimeSub) {
    this.sqlDatetimeSub = sqlDatetimeSub;
  }

  public int getNumPrecRadix() {
    return numPrecRadix;
  }

  protected void setNumPrecRadix(final int numPrecRadix) {
    this.numPrecRadix = requireGreaterEqual("numPrecRadix", numPrecRadix,
        "zero", 0);
  }

  @Override
  public int hashCode() {
    final int multiplier = 3;
    int code = 13;
    code = Hash.combine(code, multiplier, typeName);
    code = Hash.combine(code, multiplier, dataType);
    code = Hash.combine(code, multiplier, precision);
    code = Hash.combine(code, multiplier, literalPrefix);
    code = Hash.combine(code, multiplier, literalSuffix);
    code = Hash.combine(code, multiplier, createParams);
    code = Hash.combine(code, multiplier, nullable);
    code = Hash.combine(code, multiplier, caseSensitive);
    code = Hash.combine(code, multiplier, searchable);
    code = Hash.combine(code, multiplier, unsignedAttribute);
    code = Hash.combine(code, multiplier, fixedPrecScale);
    code = Hash.combine(code, multiplier, autoIncrement);
    code = Hash.combine(code, multiplier, localTypeName);
    code = Hash.combine(code, multiplier, minimumScale);
    code = Hash.combine(code, multiplier, maximumScale);
    code = Hash.combine(code, multiplier, sqlDataType);
    code = Hash.combine(code, multiplier, sqlDatetimeSub);
    code = Hash.combine(code, multiplier, numPrecRadix);
    return code;
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
    final TypeInfo other = (TypeInfo) obj;
    return Equality.equals(typeName, other.typeName)
        && (dataType == other.dataType)
        && (precision == other.precision)
        && Equality.equals(literalPrefix, other.literalPrefix)
        && Equality.equals(literalSuffix, other.literalSuffix)
        && Equality.equals(createParams, other.createParams)
        && (nullable == other.nullable)
        && (caseSensitive == other.caseSensitive)
        && (searchable == other.searchable)
        && (unsignedAttribute == other.unsignedAttribute)
        && (fixedPrecScale == other.fixedPrecScale)
        && (autoIncrement == other.autoIncrement)
        && Equality.equals(localTypeName, other.localTypeName)
        && (minimumScale == other.minimumScale)
        && (maximumScale == other.maximumScale)
        && (sqlDataType == other.sqlDataType)
        && (sqlDatetimeSub == other.sqlDatetimeSub)
        && (numPrecRadix == other.numPrecRadix);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("typeName", typeName)
               .append("dataType", dataType)
               .append("precision", precision)
               .append("literalPrefix", literalPrefix)
               .append("literalSuffix", literalSuffix)
               .append("createParams", createParams)
               .append("nullable", nullable)
               .append("caseSensitive", caseSensitive)
               .append("searchable", searchable)
               .append("unsignedAttribute", unsignedAttribute)
               .append("fixedPrecScale", fixedPrecScale)
               .append("autoIncrement", autoIncrement)
               .append("localTypeName", localTypeName)
               .append("minimumScale", minimumScale)
               .append("maximumScale", maximumScale)
               .append("sqlDataType", sqlDataType)
               .append("sqlDatetimeSub", sqlDatetimeSub)
               .append("numPrecRadix", numPrecRadix)
               .toString();
  }

}
