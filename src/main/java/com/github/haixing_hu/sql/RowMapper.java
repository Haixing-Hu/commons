/******************************************************************************
 *
 *    Copyright (c) 2009-2012  Ascent Dimension, Inc. All rights reserved.
 *
 ******************************************************************************/

package com.github.haixing_hu.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * An interface for the objects mapping rows of a {@link ResultSet} on a per-row
 * basis.
 * <p>
 * Implementations of this interface perform the actual work of mapping each row
 * to a result object, but don't need to worry about exception handling.
 *
 * @author Haixing Hu
 */
public interface RowMapper<T> {

  /**
   * Implementations must implement this method to map each row of data in the
   * {@link ResultSet}.
   * <p>
   * This method should not call {@link ResultSet#next()} on the
   * {@link ResultSet}; it is only supposed to map values of the current row.
   *
   * @param rs
   *          the ResultSet to map (pre-initialized for the current row).
   * @param rowNum
   *          the number of the current row. Note that the row number is count
   *          from 1.
   * @return the result object for the current row.
   * @throws SQLException
   *           if a SQLException is encountered getting column values (that is,
   *           there's no need to catch SQLException)
   */
  public T mapRow(ResultSet rs, int rowNum) throws SQLException;

}