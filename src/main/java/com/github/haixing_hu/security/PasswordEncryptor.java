/******************************************************************************
 *
 *    Copyright (c) 2009-2012  Ascent Dimension, Inc. All rights reserved.
 *
 ******************************************************************************/

package com.github.haixing_hu.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.annotation.Nullable;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.github.haixing_hu.lang.StringUtils;
import com.github.haixing_hu.util.codec.string.HexCodec;

/**
 * Provides functions for deal with security passwords.
 *
 * @author Haixing Hu
 * @see http://jerryorr.blogspot.com/2012/05/secure-password-storage-lots-of-donts.html
 */
public class PasswordEncryptor {

  /**
   *  Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5.
   */
  public static final int SALT_BYTES = 8;

  /**
   *  The number of bytes of the encrypted password.
   *  <p>
   *  NOTE: No matter how long the password is, the encrypted password always
   *  have this length.
   */
  public static final int ENCRYPTION_BYTES = 20;


  public static final int ENCRYPTED_SALTED_PASSWORD_LENGTH =
      (2 * SALT_BYTES) + 1 + (2 * ENCRYPTION_BYTES);

  /**
   *  The name of the algorithm for encrypting the passwords.
   *  <p>
   *  We choose the PBKDF2 with SHA-1 as the hashing algorithm. Note that the
   *  NIST specifically names SHA-1 as an acceptable hashing algorithm for
   *  PBKDF2
   */
  public static final String ENCRYPT_ALGORITHM = "PBKDF2WithHmacSHA1";

  /**
   *  The name of the algorithm for the security random object.
   */
  public static final String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";

  /**
   *  The iteration count for encrypting.
   *  <p>
   *  The NIST recommends at least 1,000 iterations, and the iOS 4.x reportedly
   *  uses 10,000.
   *
   *  @see http://csrc.nist.gov/publications/nistpubs/800-132/nist-sp800-132.pdf
   *  @see http://blog.crackpassword.com/2010/09/smartphone-forensics-cracking-blackberry-backup-passwords/
   */
  public static final int ITERATIONS = 20000;

  /**
   *  The length of the to be derived key.
   *  <p>
   *  SHA-1 generates 160 bit hashes, so that's what makes sense here.
   */
  public static final int KEY_LENGTH = 160;

  /**
   * The character used to separate the salt and the encrypted password.
   */
  public static final char SALT_SEPARATOR = '$';

  /**
   * Checks the correctness of the password.
   *
   * @param attemptedPassword
   *          The password user input.
   * @param encryptedPassword
   *          The encrypted password previously stored.
   * @param salt
   *          The salt for the password.
   * @return true if the password is correct; false otherwise.
   * @throws NoSuchAlgorithmException
   *           If the encryption algorithm is not supported.
   * @throws InvalidKeySpecException
   *           If the key specification is not supported.
   */
  public static boolean checkPassword(@Nullable final String attemptedPassword,
      final byte[] encryptedPassword, final byte[] salt)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    // Encrypt the clear-text password using the same salt that was used to
    // encrypt the original password
    final byte[] encryptedAttempt = encryptPassword(attemptedPassword, salt);
    // Authentication succeeds if encrypted password that the user entered
    // is equal to the stored hash
    return Arrays.equals(encryptedPassword, encryptedAttempt);
  }

  /**
   * Checks the correctness of the password.
   *
   * @param attemptedPassword
   *          The password user input. If it is null, it is treated as an
   *          empty string.
   * @param encryptedPasswordWithSalt
   *          The concatenation of the HEX representation of the salt and the
   *          encrypted password, separated by a {@link #SALT_SEPARATOR}.
   * @return
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeySpecException
   */
  public static boolean checkPassword(@Nullable final String attemptedPassword,
      final String encryptedPasswordWithSalt) throws NoSuchAlgorithmException,
      InvalidKeySpecException {
    final int pos = encryptedPasswordWithSalt.indexOf(SALT_SEPARATOR);
    if (pos < 0) {
      throw new IllegalArgumentException(
          "Invalid format of the encrypted password with salt.");
    }
    final String saltEncode = encryptedPasswordWithSalt.substring(0, pos);
    final String encryptedEncode = encryptedPasswordWithSalt.substring(pos + 1);
    if (saltEncode.length() != (2 * SALT_BYTES)) {
      throw new IllegalArgumentException("Invalid format of the salt.");
    }
    if (encryptedEncode.length() != (2 * ENCRYPTION_BYTES)) {
      throw new IllegalArgumentException(
          "Invalid format of the encrypted password.");
    }
    final HexCodec codec = new HexCodec();
    codec.setSkipBlanks(false);
    final byte[] salt = codec.decode(saltEncode);
    final byte[] encryptedPassword = codec.decode(encryptedEncode);
    return checkPassword(attemptedPassword, encryptedPassword, salt);
  }

  /**
   * Generates the encrypted password.
   *
   * @param password
   *          The password. If it is null, the function returns a null.
   * @param salt
   *          The salt.
   * @return The encrypted password
   * @throws NoSuchAlgorithmException
   *           If the encryption algorithm is not supported.
   * @throws InvalidKeySpecException
   *           If the key specification is not supported.
   */
  public static byte[] encryptPassword(@Nullable final String password,
      final byte[] salt) throws NoSuchAlgorithmException,
      InvalidKeySpecException {
    if (password == null) {
      return null;
    }
    final char[] charArray = password.toCharArray();
    final KeySpec spec = new PBEKeySpec(charArray, salt, ITERATIONS, KEY_LENGTH);
    final SecretKeyFactory factory = SecretKeyFactory.getInstance(ENCRYPT_ALGORITHM);
    return factory.generateSecret(spec).getEncoded();
  }

  /**
   * Encrypts the password using a random generated salt, and returns the
   * concatenation of the HEX representation of the salt and the encrypted
   * password.
   *
   * @param password
   *    The password to be encrypted. If it is null, the function returns a null.
   * @return
   *    The concatenation of the HEX representation of the salt and the
   *    encrypted password, separated by a {@link #SALT_SEPARATOR}.
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeySpecException
   */
  public static String encryptPassword(@Nullable final String password)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    if (password == null) {
      return null;
    }
    final byte[] salt = generateSalt();
    final byte[] encrypted = encryptPassword(password, salt);
    final HexCodec codec = new HexCodec();
    codec.setSeparator(StringUtils.EMPTY);
    codec.setMaxPerLine(Integer.MAX_VALUE);
    codec.setShowRadix(false);
    codec.setUppercaseDigit(true);
    final String saltEncode = codec.encode(salt);
    final String encryptedEncode = codec.encode(encrypted);
    return saltEncode + SALT_SEPARATOR + encryptedEncode;
  }

  /**
   * Generates a salt.
   *
   * @return
   *    A byte array of salt.
   * @throws NoSuchAlgorithmException
   *    If the security random algorithm is not supported.
   */
  public static byte[] generateSalt() throws NoSuchAlgorithmException {
    // VERY important to use SecureRandom instead of just Random
    final SecureRandom random = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM);
    // Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
    final byte[] salt = new byte[SALT_BYTES];
    random.nextBytes(salt);
    return salt;
  }
}
