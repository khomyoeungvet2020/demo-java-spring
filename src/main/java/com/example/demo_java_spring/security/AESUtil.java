package com.example.demo_java_spring.security;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    private static final String AES = "AES";
    private static final int AES_KEY_SIZE = 256; // You can use 128, 192, or 256 bits
    private static final int GCM_TAG_LENGTH = 16; // Tag length for GCM mode
    private static final int GCM_IV_LENGTH = 12; // IV length for GCM mode

    /**
     * Generates a new AES key.
     *
     * @return AES key as a SecretKey object.
     * @throws NoSuchAlgorithmException if the specified algorithm is not available.
     */
    public static SecretKey generateAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(AES);
        keyGen.init(AES_KEY_SIZE);
        return keyGen.generateKey();
    }

    /**
     * Generates a new AES key and returns it as a base64 encoded string.
     *
     * @return AES key as a base64 encoded string.
     * @throws NoSuchAlgorithmException if the specified algorithm is not available.
     */
    public static String generateAESKeyAsBase64() throws NoSuchAlgorithmException {
        SecretKey secretKey = generateAESKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * Encrypts a plain text using AES.
     *
     * @param plainText the plain text to encrypt.
     * @param secretKey the AES key used for encryption.
     * @return the encrypted text as a base64 encoded string.
     * @throws Exception if encryption fails.
     */
    public static String encrypt(String plainText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        byte[] iv = new byte[GCM_IV_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

        byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        byte[] cipherTextWithIv = new byte[iv.length + cipherText.length];
        System.arraycopy(iv, 0, cipherTextWithIv, 0, iv.length);
        System.arraycopy(cipherText, 0, cipherTextWithIv, iv.length, cipherText.length);

        return Base64.getEncoder().encodeToString(cipherTextWithIv);
    }

    /**
     * Decrypts an encrypted text using AES.
     *
     * @param encryptedText the encrypted text to decrypt.
     * @param secretKey     the AES key used for decryption.
     * @return the decrypted plain text.
     * @throws Exception if decryption fails.
     */
    public static String decrypt(String encryptedText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        byte[] cipherTextWithIv = Base64.getDecoder().decode(encryptedText);

        byte[] iv = new byte[GCM_IV_LENGTH];
        System.arraycopy(cipherTextWithIv, 0, iv, 0, iv.length);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

        byte[] cipherText = new byte[cipherTextWithIv.length - iv.length];
        System.arraycopy(cipherTextWithIv, iv.length, cipherText, 0, cipherText.length);

        byte[] plainText = cipher.doFinal(cipherText);

        return new String(plainText, StandardCharsets.UTF_8);
    }

    /**
     * Converts a base64 encoded AES key string to a SecretKey object.
     *
     * @param base64Key the base64 encoded AES key string.
     * @return the SecretKey object.
     */
    public static SecretKey getKeyFromBase64String(String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, AES);
    }
}
