/******************************************************************************
 *
 *    Copyright (c) 2009-2012  Ascent Dimension, Inc. All rights reserved.
 *
 ******************************************************************************/

package com.github.haixing_hu.security.error;

import java.security.GeneralSecurityException;

/**
 * An exception thrown to indicate an authentication error.
 *
 * @author Haixing Hu
 */
public class AuthenticationException extends GeneralSecurityException {

  private static final long serialVersionUID = - 1431064676883994515L;

  public AuthenticationException() {
    super();
  }

  public AuthenticationException(final String message) {
    super(message);
  }

  public AuthenticationException(final Throwable cause) {
    super(cause);
  }
}
