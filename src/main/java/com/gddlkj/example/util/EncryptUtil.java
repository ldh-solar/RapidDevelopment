package com.gddlkj.example.util;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bson.internal.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Security;


public class EncryptUtil {

    // 加密算法
    private static final String ENCRY_ALGORITHM = "AES";
    // 加密算法/加密模式/填充类型
    private static final String CIPHER_MODE = "AES/CBC/PKCS7Padding";
    // 密钥
    private static final String KEY = "gddlkj0123456789";
    // 设置iv偏移量，ECB加密模式不需要设置 iv 偏移量
    private static final String IV = "gddlkj0123456789";
    // 设置加密字符集
    private static final String CHARACTER = "UTF-8";
    // 加密密码长度。默认 16 byte * 8 = 128 bit
    private static final int PWD_SIZE = 16;


    static {
        // 添加 AES/CBC/PKCS7Padding 支持
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 密码长度不足补"0"
     */
    private static byte[] pwdHandler(String password) throws UnsupportedEncodingException {
        byte[] data = null;
        if (password == null) {
            password = "";
        }
        StringBuilder sb = new StringBuilder(PWD_SIZE);
        sb.append(password);
        while (sb.length() < PWD_SIZE) {
            sb.append("0");
        }
        if (sb.length() > PWD_SIZE) {
            sb.setLength(PWD_SIZE);
        }
        data = sb.toString().getBytes(CHARACTER);
        return data;
    }

    public static String decryptAES(String content) {
        try {
            byte[] ciphertext = Base64.decode(content);
            // 获取解密密钥
            SecretKeySpec keySpec = new SecretKeySpec(pwdHandler(KEY), ENCRY_ALGORITHM);
            // 获取Cipher实例
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);

            // 初始化Cipher实例。设置执行模式以及加密密钥
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(IV.getBytes(CHARACTER)));
            // 执行
            return new String(cipher.doFinal(ciphertext));
        } catch (Exception ignored) {

        }
        return null;
    }


}
