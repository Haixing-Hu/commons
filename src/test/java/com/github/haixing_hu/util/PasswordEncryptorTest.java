/******************************************************************************
 *
 *    Copyright (c) 2009-2012  Ascent Dimension, Inc. All rights reserved.
 *
 ******************************************************************************/

package com.github.haixing_hu.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import org.junit.Test;

import com.github.haixing_hu.security.PasswordEncryptor;

import static org.junit.Assert.*;

/**
 * Unit test for the {@link PasswordEncryptor} class.
 *
 * @author Haixing Hu
 */
public class PasswordEncryptorTest {

  /**
   * Test method for {@link PasswordEncryptor#checkPassword(String, byte[], byte[])}.
   */
  @Test
  public void testCheckPassword() throws NoSuchAlgorithmException,
      InvalidKeySpecException {
    byte[] salt1 = PasswordEncryptor.generateSalt();
    String password1 = "abcdefg";    
    byte[] encrypted1 = PasswordEncryptor.encryptPassword(password1, salt1);
    
    assertTrue(PasswordEncryptor.checkPassword("abcdefg", encrypted1, salt1));
    assertFalse(PasswordEncryptor.checkPassword("abcdefh", encrypted1, salt1));        
  }
  
  /**
   * Test method for {@link PasswordEncryptor#checkPassword(String, String)}.
   */
  @Test
  public void testCheckPasswordStringString() throws NoSuchAlgorithmException,
      InvalidKeySpecException {
    String password1 = "abcdefg";    
    String encrypted1 = PasswordEncryptor.encryptPassword(password1);
    System.out.println("Encrypted password with salt is: " + encrypted1);
    assertTrue(PasswordEncryptor.checkPassword("abcdefg", encrypted1));
    assertFalse(PasswordEncryptor.checkPassword("abcdefh", encrypted1));        
  }

  /**
   * Test method for {@link PasswordEncryptor#encryptPassword(String, byte[])}.
   * @throws NoSuchAlgorithmException 
   * @throws InvalidKeySpecException 
   */
  @Test
  public void testEncryptPassword() throws NoSuchAlgorithmException,
      InvalidKeySpecException {
    byte[] salt1 = PasswordEncryptor.generateSalt();
    String password = "abcdefg";
    
    byte[] encrypted1 = PasswordEncryptor.encryptPassword(password, salt1);
    assertNotNull(encrypted1);
    System.out.printf("encrypted password length = %d\n", encrypted1.length);    
    byte[] salt2 = PasswordEncryptor.generateSalt();
    
    byte[] encrypted2 = PasswordEncryptor.encryptPassword(password, salt2);
    assertNotNull(encrypted2);
    System.out.printf("encrypted password length = %d\n", encrypted2.length);
    assertFalse(Arrays.equals(encrypted1, encrypted2));
        
    byte[] salt32 = PasswordEncryptor.generateSalt();
    String password32 = "12345678901234567890123456789012";
    byte[] encrypted32 = PasswordEncryptor.encryptPassword(password32, salt32);
    assertNotNull(encrypted32);
    System.out.printf("encrypted password length = %d\n", encrypted32.length);
  }
  
  /**
   * Test method for {@link PasswordEncryptor#encryptPassword(String)}.
   * @throws NoSuchAlgorithmException 
   * @throws InvalidKeySpecException 
   */
  @Test
  public void testEncryptPasswordString() throws NoSuchAlgorithmException,
      InvalidKeySpecException {
    String password = "abcdefg";
    
    String encrypted1 = PasswordEncryptor.encryptPassword(password);
    assertNotNull(encrypted1);
    assertEquals(PasswordEncryptor.ENCRYPTED_SALTED_PASSWORD_LENGTH, encrypted1.length());
    System.out.printf("encrypted password = %s\n", encrypted1);                
    
    String password32 = "12345678901234567890123456789012";
    String encrypted32 = PasswordEncryptor.encryptPassword(password32);
    assertNotNull(encrypted32);
    assertEquals(PasswordEncryptor.ENCRYPTED_SALTED_PASSWORD_LENGTH, encrypted32.length());
    System.out.printf("encrypted password = %s\n", encrypted32);    
  }

  /**
   * Test method for {@link PasswordEncryptor#generateSalt()}.
   */
  @Test
  public void testGenerateSalt() throws NoSuchAlgorithmException {
    byte[] salt1 = PasswordEncryptor.generateSalt();
    assertNotNull(salt1);
    assertEquals(PasswordEncryptor.SALT_BYTES, salt1.length);
    
    byte[] salt2 = PasswordEncryptor.generateSalt();
    assertNotNull(salt2);
    assertEquals(PasswordEncryptor.SALT_BYTES, salt2.length);
    
    assertFalse(Arrays.equals(salt1, salt2));
  }

}
