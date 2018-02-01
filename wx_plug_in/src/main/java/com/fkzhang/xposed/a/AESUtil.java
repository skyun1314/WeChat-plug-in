package com.fkzhang.xposed.a;

import android.util.Base64;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class AESUtil {
    private static final byte[] ʻ = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0};

    public static String ʻ(String str, String str2) throws GeneralSecurityException {
        try {
            return Base64.encodeToString(ʻ(ʻ(str), ʻ, str2.getBytes("UTF-8")), 2);
        } catch (Throwable e) {
            throw new GeneralSecurityException(e);
        }
    }

    private static SecretKeySpec ʻ(String str) throws Exception {
        MessageDigest instance = MessageDigest.getInstance("SHA-256");
        byte[] bytes = str.getBytes("UTF-8");
        instance.update(bytes, 0, bytes.length);
        return new SecretKeySpec(instance.digest(), "AES");
    }

    private static byte[] ʻ(SecretKeySpec secretKeySpec, byte[] bArr, byte[] bArr2) throws Exception {
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS7Padding");
        instance.init(1, secretKeySpec, new IvParameterSpec(bArr));
        return instance.doFinal(bArr2);
    }

    public static String ʼ(String str, String str2) throws GeneralSecurityException {
        try {
            return new String(ʼ(ʻ(str), ʻ, Base64.decode(str2, 2)), "UTF-8");
        } catch (Throwable e) {
            throw new GeneralSecurityException(e);
        }
    }

    private static byte[] ʼ(SecretKeySpec secretKeySpec, byte[] bArr, byte[] bArr2) throws  Exception {
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS7Padding");
        instance.init(2, secretKeySpec, new IvParameterSpec(bArr));
        return instance.doFinal(bArr2);
    }
}
