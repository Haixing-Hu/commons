/******************************************************************************
 *
 *    Copyright (c) 2009-2012  Ascent Dimension, Inc. All rights reserved.
 *
 ******************************************************************************/

package com.github.haixing_hu.security.error;

/**
 * An exception thrown to indicate the user does not exist.
 *
 * @author Haixing Hu
 */
public class UserNotExistException extends AuthenticationException {

  private static final long serialVersionUID = - 213011532544214875L;

  private final String username;

  public UserNotExistException(final String username) {
    super("The user '" + username + "' does not exist.");
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}
