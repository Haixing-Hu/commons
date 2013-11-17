/******************************************************************************
 *
 *    Copyright (c) 2009-2012  Ascent Dimension, Inc. All rights reserved.
 *
 ******************************************************************************/

package com.github.haixing_hu.security.error;

/**
 * An exception thrown to indicate the password is invalid.
 *
 * @author Haixing Hu
 */
public class InvalidPasswordException extends AuthenticationException {

  private static final long serialVersionUID = 5595569018510974092L;

  private final String username;
  private final String password;

  public InvalidPasswordException() {
    super("The password is invalid.");
    username = null;
    password = null;
  }

  public InvalidPasswordException(final String username, final String password) {
    super("The password for user '" + username + "' is invalid: '" + password + "'");
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}
