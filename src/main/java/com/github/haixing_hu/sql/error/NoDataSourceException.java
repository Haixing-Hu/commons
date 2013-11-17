/******************************************************************************
 *
 *    Copyright (c) 2009-2012  Ascent Dimension, Inc. All rights reserved.
 *
 ******************************************************************************/

package com.github.haixing_hu.sql.error;

import java.sql.SQLException;

/**
 * Thrown to indicate that the data source is not specified.
 *
 * @author Haixing Hu
 */
public class NoDataSourceException extends SQLException {

  private static final long serialVersionUID = 1176648404381564211L;

  public NoDataSourceException() {
    super("The DataSource is not set.");
  }
}
