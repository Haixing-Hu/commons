/******************************************************************************
 *
 *    Copyright (c) 2009-2012  Ascent Dimension, Inc. All rights reserved.
 *
 ******************************************************************************/

package com.github.haixing_hu.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This interface sets values on a {@link PreparedStatement}, for each of a
 * number of updates in a batch using the same SQL.
 * <p>
 * Implementations are responsible for setting any necessary parameters. SQL
 * with placeholders will already have been supplied. Implementations do not
 * need to concern themselves with {@link SQLException} that may be thrown from
 * operations they attempt.
 *
 * @author Haixing Hu
 */
public interface PreparedStatementSetter {

  /**
   * Set parameter values on the given {@link PreparedStatement}.
   *
   * @param ps
   *          the {@link PreparedStatement} to invoke setter methods on.
   * @throws SQLException
   *           if a SQLException is encountered (i.e. there is no need to catch
   *           SQLException)
   */
  public void setValues(PreparedStatement ps) throws SQLException;

}
