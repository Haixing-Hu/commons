/******************************************************************************
 *
 *    Copyright (c) 2009-2012  Ascent Dimension, Inc. All rights reserved.
 *
 ******************************************************************************/

package com.github.haixing_hu.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * This is a simple program used to encrypt the password.
 *
 * @author Haixing Hu
 */
public class EncryptPassword {

  public static void main(String[] args) {
    for (int i = 0; i < args.length; ++i) {
      try {
        final String encrypted = PasswordEncryptor.encryptPassword(args[i]);
        System.out.println(encrypted);
      } catch (final NoSuchAlgorithmException e) {
        System.err.println(e.getMessage());
        System.exit(-1);
      } catch (final InvalidKeySpecException e) {
        System.err.println(e.getMessage());
        System.exit(-1);
      }
    }
  }
}
