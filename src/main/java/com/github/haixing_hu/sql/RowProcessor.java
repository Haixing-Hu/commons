/******************************************************************************
 *
 *    Copyright (c) 2009-2012  Ascent Dimension, Inc. All rights reserved.
 *
 ******************************************************************************/

package com.github.haixing_hu.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * An interface for objects processing rows of a {@link ResultSet} on a per-row
 * basis.
 * <p>
 * Implementations of this interface perform the actual work of processing each
 * row but don't need to worry about exception handling.
 *
 * @see RowMapper
 * @author Haixing Hu
 */
public interface RowProcessor {

  /**
   * Implementations must implement this method to process each row of data in
   * the {@link ResultSet}.
   * <p>
   * This method should not call {@link ResultSet#next()} on the
   * {@link ResultSet}; it is only supposed to extract values of the current
   * row.
   * <p>
   * Exactly what the implementation chooses to do is up to it: A trivial
   * implementation might simply count rows, while another implementation might
   * build an XML document.
   *
   * @param rs
   *          the ResultSet to process (pre-initialized for the current row).
   * @param rowNum
   *          the number of the current row. Note that the row number is count
   *          from 1.
   * @throws SQLException
   *           if a SQLException is encountered getting column values (that is,
   *           there's no need to catch SQLException)
   */
  public void processRow(ResultSet rs, int rowNum) throws SQLException;
}
