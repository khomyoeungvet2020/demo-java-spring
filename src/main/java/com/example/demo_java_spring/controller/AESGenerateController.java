package com.example.demo_java_spring.controller;

import java.security.NoSuchAlgorithmException;

import javax.crypto.SecretKey;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_java_spring.security.AESUtil;

@RestController
@RequestMapping("/api/v1/aes")
public class AESGenerateController {

    @GetMapping("/generate-aes-key")
    public String generateAESKey() throws NoSuchAlgorithmException {
        return AESUtil.generateAESKeyAsBase64();
    }

    @GetMapping("/encrypt")
    public String encrypt(@RequestParam String plainText, @RequestParam String base64Key) throws Exception {
        SecretKey secretKey = AESUtil.getKeyFromBase64String(base64Key);
        return AESUtil.encrypt(plainText, secretKey);
    }

    @GetMapping("/decrypt")
    public String decrypt(@RequestParam String encryptedText, @RequestParam String base64Key) throws Exception {
        SecretKey secretKey = AESUtil.getKeyFromBase64String(base64Key);
        return AESUtil.decrypt(encryptedText, secretKey);
    }
}
