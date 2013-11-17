/******************************************************************************
 *
 *    Copyright (c) 2009-2012  Ascent Dimension, Inc. All rights reserved.
 *
 ******************************************************************************/

package com.github.haixing_hu.security.error;

/**
 * Thrown to indicate that the user has already been disabled.
 *
 * @author Haixing Hu
 */
public class UserDisabledException extends AuthenticationException {

  private static final long serialVersionUID = 4203416709048645675L;

  private final String username;

  public UserDisabledException(final String username) {
    super("The user '" + username + "' has already been deleted.");
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}
