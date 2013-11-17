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

package com.github.haixing_hu.sql;

import java.sql.ParameterMetaData;

import com.github.haixing_hu.lang.Equality;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.sql.error.UnsupportedSqlTypeException;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

import static com.github.haixing_hu.lang.Argument.*;

/**
 * Represents the information about a parameter.
 *
 * @author Haixing Hu
 */
public final class ParamInfo {

  private static final int[] NULLABLE_VALUES = {
    ParameterMetaData.parameterNoNulls,
    ParameterMetaData.parameterNullable,
    ParameterMetaData.parameterNullableUnknown,
  };

  private static final int[] MODE_VALUES = {
    ParameterMetaData.parameterModeUnknown,
    ParameterMetaData.parameterModeIn,
    ParameterMetaData.parameterModeInOut,
    ParameterMetaData.parameterModeOut,
  };

  private int type;
  private String typeName;
  private String typeClassName;
  private int nullable;
  private boolean signed;
  private int precision;
  private int scale;
  private int mode;

  public ParamInfo() {
    this.type = -1;
    this.typeName = StringUtils.EMPTY;
    this.typeClassName = StringUtils.EMPTY;
    this.nullable = - 1;
    this.signed = false;
    this.precision = - 1;
    this.scale = - 1;
    this.mode = - 1;
  }

  public ParamInfo(final int type) throws UnsupportedSqlTypeException {
    this.type = requireInEnum("type", type, StandardTypeMapping.SQL_TYPES);
    this.typeName = StandardTypeMapping.getName(type);
    this.typeClassName = StandardTypeMapping.getJavaType(type).getName();
    this.nullable = - 1;
    this.signed = false;
    this.precision = - 1;
    this.scale = - 1;
    this.mode = - 1;
  }

  public ParamInfo(final int type, final String typeName,
      final String typeClassName) throws UnsupportedSqlTypeException {
    this.type = requireInEnum("type", type, StandardTypeMapping.SQL_TYPES);
    this.typeName = requireNonNull("typeName", typeName);
    this.typeClassName = requireNonNull("typeClassName", typeClassName);
    this.nullable = - 1;
    this.signed = false;
    this.precision = - 1;
    this.scale = - 1;
    this.mode = - 1;
  }

  public int getType() {
    return type;
  }

  public void setType(final int type) {
    this.type = requireInEnum("type", type, StandardTypeMapping.SQL_TYPES);
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(final String typeName) {
    this.typeName = requireNonNull("typeName", typeName);
  }

  public String getTypeClassName() {
    return typeClassName;
  }

  public void setTypeClassName(final String typeClassName) {
    this.typeClassName = requireNonNull("typeClassName", typeClassName);
  }

  public int isNullable() {
    return nullable;
  }

  public void setNullable(final int nullable) {
    requireInEnum("nullable", nullable, NULLABLE_VALUES);
    this.nullable = nullable;
  }

  public boolean isSigned() {
    return signed;
  }

  public void setSigned(final boolean signed) {
    this.signed = signed;
  }

  public int getPrecision() {
    return precision;
  }

  public void setPrecision(final int precision) {
    this.precision = precision;
  }

  public int getScale() {
    return scale;
  }

  public void setScale(final int scale) {
    this.scale = scale;
  }

  public int getMode() {
    return mode;
  }

  public void setMode(final int mode) {
    this.mode = requireInEnum("mode", mode, MODE_VALUES);
  }

  @Override
  public int hashCode() {
    final int multiplier = 5;
    int code = 3;
    code = Hash.combine(code, multiplier, type);
    code = Hash.combine(code, multiplier, typeName);
    code = Hash.combine(code, multiplier, typeClassName);
    code = Hash.combine(code, multiplier, nullable);
    code = Hash.combine(code, multiplier, signed);
    code = Hash.combine(code, multiplier, precision);
    code = Hash.combine(code, multiplier, scale);
    code = Hash.combine(code, multiplier, mode);
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
    final ParamInfo other = (ParamInfo) obj;
    return (type == other.type)
        && (nullable == other.nullable)
        && (signed == other.signed)
        && (precision == other.precision)
        && (scale == other.scale)
        && (mode == other.mode)
        && Equality.equals(typeName, other.typeName)
        && Equality.equals(typeClassName, other.typeClassName);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("type", type)
            .append("typeName", typeName)
            .append("typeClassName", typeClassName)
            .append("nullable", nullable)
            .append("signed", signed)
            .append("precision", precision)
            .append("scale", scale)
            .append("mode", mode)
            .toString();
  }


}
