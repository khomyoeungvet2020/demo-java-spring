package com.example.demo_java_spring.controller;

import java.security.KeyPair;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_java_spring.security.RSAUtil;

@RestController
@RequestMapping("/api/v1/rsa")
public class RSAGenerateController {

    @GetMapping("/generate-rsa-keypair")
    public String[] generateRSAKeyPair() throws Exception {
        KeyPair keyPair = RSAUtil.generateRSAKeyPair();
        String publicKey = RSAUtil.getBase64StringFromPublicKey(keyPair.getPublic());
        String privateKey = RSAUtil.getBase64StringFromPrivateKey(keyPair.getPrivate());
        return new String[] { publicKey, privateKey };
    }

    @GetMapping("/encrypt")
    public String encrypt(@RequestParam String plainText, @RequestParam String base64PublicKey) throws Exception {
        return RSAUtil.encrypt(plainText, RSAUtil.getPublicKeyFromBase64String(base64PublicKey));
    }

    @GetMapping("/decrypt")
    public String decrypt(@RequestParam String encryptedText, @RequestParam String base64PrivateKey) throws Exception {
        return RSAUtil.decrypt(encryptedText, RSAUtil.getPrivateKeyFromBase64String(base64PrivateKey));
    }
}
