package com.example.demo_java_spring.security;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSAUtil {

    private static final String RSA = "RSA";
    private static final int RSA_KEY_SIZE = 2048;

    /**
     * Generates a new RSA key pair.
     *
     * @return RSA key pair.
     * @throws Exception if key generation fails.
     */
    public static KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        keyPairGenerator.initialize(RSA_KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * Encrypts plain text using the RSA public key.
     *
     * @param plainText the plain text to encrypt.
     * @param publicKey the RSA public key used for encryption.
     * @return the encrypted text as a base64 encoded string.
     * @throws Exception if encryption fails.
     */
    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts encrypted text using the RSA private key.
     *
     * @param encryptedText the encrypted text to decrypt.
     * @param privateKey    the RSA private key used for decryption.
     * @return the decrypted plain text.
     * @throws Exception if decryption fails.
     */
    public static String decrypt(String encryptedText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * Converts a base64 encoded string to an RSA PublicKey object.
     *
     * @param base64PublicKey the base64 encoded RSA public key string.
     * @return the PublicKey object.
     * @throws Exception if conversion fails.
     */
    public static PublicKey getPublicKeyFromBase64String(String base64PublicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(spec);
    }

    /**
     * Converts a base64 encoded string to an RSA PrivateKey object.
     *
     * @param base64PrivateKey the base64 encoded RSA private key string.
     * @return the PrivateKey object.
     * @throws Exception if conversion fails.
     */
    public static PrivateKey getPrivateKeyFromBase64String(String base64PrivateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PrivateKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(spec);
    }

    /**
     * Converts an RSA PublicKey object to a base64 encoded string.
     *
     * @param publicKey the PublicKey object.
     * @return the base64 encoded string.
     */
    public static String getBase64StringFromPublicKey(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    /**
     * Converts an RSA PrivateKey object to a base64 encoded string.
     *
     * @param privateKey the PrivateKey object.
     * @return the base64 encoded string.
     */
    public static String getBase64StringFromPrivateKey(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }
}
