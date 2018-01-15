package com.example.a2345;


import android.text.TextUtils;
import android.util.Base64;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by zk on 2018/1/4.
 */

public class aes {

    private static final String UTF_8 = "UTF-8";
    private static byte[] iv = "nmeug.f9/Om+L823".getBytes();
    private static byte[] pwd = null;

    public static String encryptNoPadding(String str, String str2)  {

        try {
            Cipher instance = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = instance.getBlockSize();
            byte[] bytes = str.getBytes(str2);
            int length = bytes.length;
            if (length % blockSize != 0) {
                length += blockSize - (length % blockSize);
            }
            byte[] obj = new byte[length];
            System.arraycopy(bytes, 0, obj, 0, bytes.length);
            instance.init(1, new SecretKeySpec(pwd, "AES"), new IvParameterSpec(iv));

            return   Base64.encodeToString(instance.doFinal(obj),Base64.DEFAULT);
            //   return Base64.encodeBase64String(instance.doFinal(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptNoPadding(String str, String str2) {
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/NoPadding");
            instance.init(2, new SecretKeySpec(pwd, "AES"), new IvParameterSpec(iv));
            // Base64.decodeBase64(str)
            byte[] decode= Base64.decode(str,Base64.NO_WRAP);
            return new String(instance.doFinal(decode), str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void setPassword(String str) {
        if (!TextUtils.isEmpty(str)) {
            String md5 = md5(str);
            if (md5.length() >= 16) {
                md5 = md5.substring(0, 16);
            }
            pwd = md5.getBytes();
        }
    }

    public static String md5(String str) {
        if (str == null) {
            return null;
        }
        try {
            byte[] bytes = str.getBytes();
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.reset();
            instance.update(bytes);
            bytes = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                stringBuffer.append(String.format("%02X", new Object[]{Byte.valueOf(bytes[i])}));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            return str.replaceAll("[^[a-z][A-Z][0-9][.][_]]", "");
        }
    }


}
