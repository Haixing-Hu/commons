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
package com.github.haixing_hu.sql;

import java.io.Serializable;
import java.sql.ResultSetMetaData;
import java.sql.Types;

import com.github.haixing_hu.lang.Equality;
import com.github.haixing_hu.lang.Hash;
import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.text.tostring.ToStringBuilder;

import static com.github.haixing_hu.lang.Argument.*;

/**
 * This class stores the meta data of columns.
 *
 * @author Haixing Hu
 */
public final class ColumnInfo implements Serializable {

  private static final long serialVersionUID = - 6309690229495461909L;

  public static final int SIGNED = 0x0001;

  public static final int SEARCHABLE = 0x0002;

  public static final int AUTO_INCREMENT = 0x0004;

  public static final int CASE_SENSITIVE = 0x0008;

  public static final int CURRENCY = 0x0010;

  public static final int NON_NULLABLE = 0x0020;

  public static final int NULLABLE = 0x0040;

  public static final int WRITABLE = 0x0080;

  public static final int DEFINITELY_WRITABLE = 0x0100 | WRITABLE;

  public static final int NULLABILITY = NON_NULLABLE | NULLABLE;

  public static final int WRITABILITY = WRITABLE | DEFINITELY_WRITABLE;

  public static final int DEFAULT_OPTIONS = SIGNED
                                          | SEARCHABLE
                                          | CASE_SENSITIVE;

  private String name;
  private String label;
  private String tableName;
  private String catalogName;
  private String schemaName;
  private String typeName;
  private int type;
  private int precision;
  private int scale;
  private int displaySize;
  private int options;

  public ColumnInfo() {
    name = StringUtils.EMPTY;
    label = StringUtils.EMPTY;
    tableName = StringUtils.EMPTY;
    catalogName = StringUtils.EMPTY;
    schemaName = StringUtils.EMPTY;
    typeName = StringUtils.EMPTY;
    type = Types.OTHER;
    precision = 0;
    scale = 0;
    displaySize = 0;
    options = DEFAULT_OPTIONS;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = requireNonNull("name", name);
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(final String label) {
    this.label = requireNonNull("label", label);
  }

  public String getTableName() {
    return schemaName;
  }

  public void setTableName(final String tableName) {
    this.tableName = requireNonNull("tableName", tableName);
  }

  public String getCatalogName() {
    return catalogName;
  }

  public void setCatalogName(final String catalogName) {
    this.catalogName = requireNonNull("catalogName", catalogName);
  }

  public String getSchemaName() {
    return schemaName;
  }

  public void setSchemaName(final String schemaName) {
    this.schemaName = requireNonNull("schemaName", schemaName);
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(final String typeName) {
    this.typeName = requireNonNull("typeName", typeName);
  }

  public int getType() {
    return type;
  }

  public void setType(final int type) {
    this.type = requireInEnum("type", type, StandardTypeMapping.SQL_TYPES);
  }

  public int getPrecision() {
    return precision;
  }

  public void setPrecision(final int precision) {
    this.precision = requireGreaterEqual("precision", precision, "zero", 0);
  }

  public int getScale() {
    return scale;
  }

  public void setScale(final int scale) {
    this.scale = requireGreaterEqual("scale", scale, "zero", 0);
  }

  public int getDisplaySize() {
    return displaySize;
  }

  public void setDisplaySize(final int displaySize) {
    this.displaySize = requireGreaterEqual("displaySize", displaySize, "zero", 0);
  }

  public int getOptions() {
    return options;
  }

  public void setOptions(final int options) {
    this.options = options;
  }

  public boolean isSigned() {
    return (options & SIGNED) != 0;
  }

  public void setSigned(final boolean signed) {
    if (signed) {
      options |= SIGNED;
    } else {
      options &= (~ SIGNED);
    }
  }

  public boolean isSearchable() {
    return (options & SEARCHABLE) != 0;
  }

  public void setSearchable(final boolean searchable) {
    if (searchable) {
      options |= SEARCHABLE;
    } else {
      options &= (~ SEARCHABLE);
    }
  }

  public boolean isAutoIncrement() {
    return (options & AUTO_INCREMENT) != 0;
  }

  public void setAutoIncrement(final boolean autoIncrement) {
    if (autoIncrement) {
      options |= AUTO_INCREMENT;
    } else {
      options &= (~ AUTO_INCREMENT);
    }
  }

  public boolean isCaseSensitive() {
    return (options & CASE_SENSITIVE) != 0;
  }

  public void setCaseSensitive(final boolean caseSensitive) {
    if (caseSensitive) {
      options |= CASE_SENSITIVE;
    } else {
      options &= (~ CASE_SENSITIVE);
    }
  }

  public boolean isCurrency() {
    return (options & CURRENCY) != 0;
  }

  public void setCurrency(final boolean currency) {
    if (currency) {
      options |= CURRENCY;
    } else {
      options &= (~ CURRENCY);
    }
  }

  public int isNullable() {
    switch (options & NULLABILITY) {
      case NON_NULLABLE:
        return ResultSetMetaData.columnNoNulls;
      case NULLABLE:
        return ResultSetMetaData.columnNullable;
      default:
        return ResultSetMetaData.columnNullableUnknown;
    }
  }

  public void setNullable(final int nullable) {
    options &= (~ NULLABILITY);
    switch (nullable) {
      case ResultSetMetaData.columnNoNulls:
        options |= NON_NULLABLE;
        break;
      case ResultSetMetaData.columnNullable:
        options |= NULLABLE;
        break;
      case ResultSetMetaData.columnNullableUnknown:
      default:
        break;
    }
  }

  public boolean isReadOnly() {
    return (options & WRITABILITY) == 0;
  }

  public void setReadOnly(final boolean readOnly) {
    if (readOnly) {
      options &= (~ WRITABILITY);
    } else {
      options |= WRITABLE;
    }
  }

  public boolean isWritable() {
    return (options & WRITABLE) != 0;
  }

  public void setWritable(final boolean writable) {
    options &= (~ WRITABILITY);
    if (writable) {
      options |= WRITABLE;
    }
  }

  public boolean isDefinitelyWritable() {
    return (options & DEFINITELY_WRITABLE) != 0;
  }

  public void setDefinitelyWritable(final boolean definitely) {
    options &= (~ WRITABILITY);
    if (definitely) {
      options |= DEFINITELY_WRITABLE;
    }
  }


  @Override
  public int hashCode() {
    final int multiplier = 3;
    int code = 123;
    code = Hash.combine(code, multiplier, name);
    code = Hash.combine(code, multiplier, label);
    code = Hash.combine(code, multiplier, tableName);
    code = Hash.combine(code, multiplier, catalogName);
    code = Hash.combine(code, multiplier, schemaName);
    code = Hash.combine(code, multiplier, typeName);
    code = Hash.combine(code, multiplier, type);
    code = Hash.combine(code, multiplier, precision);
    code = Hash.combine(code, multiplier, scale);
    code = Hash.combine(code, multiplier, displaySize);
    code = Hash.combine(code, multiplier, options);
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
    final ColumnInfo other = (ColumnInfo) obj;
    return Equality.equals(name, other.name)
         && Equality.equals(label, other.label)
         && Equality.equals(tableName, other.tableName)
         && Equality.equals(catalogName, other.catalogName)
         && Equality.equals(schemaName, other.schemaName)
         && Equality.equals(typeName, other.typeName)
         && (type == other.type)
         && (precision == other.precision)
         && (scale == other.scale)
         && (displaySize == other.displaySize)
         && (options == other.options);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("name", name)
               .append("label", label)
               .append("tableName", tableName)
               .append("catalogName", catalogName)
               .append("schemaName", schemaName)
               .append("typeName", typeName)
               .append("type", type)
               .append("precision", precision)
               .append("scale", scale)
               .append("displaySize", displaySize)
               .append("options", options)
               .toString();
  }
}
