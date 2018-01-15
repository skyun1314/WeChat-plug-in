package com.example.javatesty;


import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by zk on 2017/11/30.
 */

public class aes {

    static String key = "&*($HJDGH4867%&T";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    public static void main(String[] args) {

        byte[] feedbacks = encrypt("hw0866946023235515300000224500CN01", key);
        System.out.println("jiami:" + parseByte2HexStr(feedbacks));


        System.out.println("jiemi:" + decrypt(feedbacks, key));

        byte[] bytes = parseHexStr2Byte("553ad36874028de98a7dcc09971432d27c08a1e4b1d638fa7a29407bf52f0d5204192fd6123e86d3ddb096c3693843eb2d1d3ab19a5bd4f67ce9e2f505d401b715052d7cf3af9babd264d25337481794e79611a3b626fd467f0f5e844bb5351fafc0776570a5b7e00d971b3cf17371e5b16e28e069d57243519f37eaa130b525b32fb7b0190a5925361a84b7fb1b77838e510f294141cda27a1d25e232606605b648c612fc8b3ac43650e648e569b7329eac38acb63e83ddf06f30aa242424963bd882c8fde5dd64e310cae7c377db5e83c61d8d4f8b4eec46a3e53b6b74813bf0a8e936e9d77db9a51c0aeb80d972295433ca1b2d9bd1d90280af05b728ac69290877e39cb646e4e49641e1aee3eedb93b83d718d599829ad7bce63e61cba744fc68fbf418b32ee84bbce56054362ed038ac9636f0b587980048df77d3f8d38");


        System.out.println("jiemi:" + decrypt(bytes, key));

    }

    /**
     * AES加密
     *
     * @param source 源字符串
     * @param key    密钥
     * @return 加密后的密文
     * @throws Exception
     */
    public static byte[] encrypt(String source, String key) {
        byte[] decrypted = new byte[0];
        try {
            byte[] sourceBytes = source.getBytes("UTF-8");
            // byte[] keyBytes = Base64.decodeBase64(key);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"));
            decrypted = cipher.doFinal(sourceBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return Base64.encodeBase64String(decrypted);
        return decrypted;
    }

    /**
     * AES解密
     *
     * @param encryptStr 加密后的密文
     * @param key        密钥
     * @return 源字符串
     * @throws Exception
     */
    public static String decrypt(byte[] encryptStr, String key) {
        // byte[] sourceBytes = Base64.decodeBase64(encryptStr);
        //  byte[] keyBytes = Base64.decodeBase64(key);
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"));
            byte[] decoded = cipher.doFinal(encryptStr);
            return new String(decoded, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }





}
