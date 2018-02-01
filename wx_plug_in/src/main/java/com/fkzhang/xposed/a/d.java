package com.fkzhang.xposed.a;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class d {
    private PublicKey ʻ;
    private Context ʼ;
    private byte[] ʽ;
    private String ʾ;
    private byte[] ʿ;

    public d(Context arg1) {
        super();
        this.ʼ = arg1;
    }

    private String ʻ(byte[] arg6) {
        String v0_3;
        try {
            MessageDigest v0_1 = MessageDigest.getInstance("MD5");
            v0_1.update(arg6);
            byte[] v1 = v0_1.digest();
            StringBuilder v2 = new StringBuilder();
            int v3 = v1.length;
            int v0_2;
            for(v0_2 = 0; v0_2 < v3; ++v0_2) {
                v2.append(Integer.toHexString(v1[v0_2] & 255));
            }

            v0_3 = v2.toString();
        }
        catch(Throwable v0) {
            v0.printStackTrace();
            v0_3 = "";
        }

        return v0_3;
    }

    private byte[] ʻ(InputStream arg2) {
        byte[] v0 = new byte[0];
        try {
            v0 = new byte[arg2.available()];
            arg2.read(v0);
            arg2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return v0;
    }

    public String ʻ() {
        String v0;
        if(!TextUtils.isEmpty(this.ʾ)) {
            v0 = this.ʾ;
        }
        else {
            byte[] v0_1 = this.ʾ();
            if(v0_1 == null) {
                v0 = "";
            }
            else {
                this.ʾ = this.ʻ(v0_1);
                v0 = this.ʾ;
            }
        }

        return v0;
    }

    public boolean ʻ(File arg7) {
        boolean v0 = false;
        if(arg7.exists()) {
            try {
                v0 = this.ʻ(new FileInputStream(arg7), new FileInputStream(new File(arg7.getAbsolutePath() + "_s")));
            }
            catch(Throwable v1) {
                b.ʻ(v1);
            }
        }

        return v0;
    }

    public boolean ʻ(InputStream arg7, InputStream arg8) {
        byte[] v0 = this.ʻ(arg8);
        try {
            Signature v1 = Signature.getInstance("SHA1withRSA", "BC");
            v1.initVerify(this.ʽ());
            BufferedInputStream v2 = new BufferedInputStream(arg7);
            byte[] v3 = new byte[2048];
            while(v2.available() != 0) {
                v1.update(v3, 0, v2.read(v3));
            }

            arg7.close();
            v2.close();
            return v1.verify(v0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public byte[] ʻ(String arg3) {
        byte[] v0_1;
        try {
            InputStream v1 = this.ʼ.getAssets().open(arg3);
            v0_1 = new byte[v1.available()];
            v1.read(v0_1);
            v1.close();
        }
        catch(Throwable v0) {
            b.ʻ(v0);
            v0_1 = null;
        }

        return v0_1;
    }

    @SuppressLint("WrongConstant")
    public String ʼ() {
        String v0_1;
        try {
            v0_1 = this.ʻ(this.ʼ.getPackageManager().getPackageInfo(this.ʼ.getPackageName(), 64).signatures[0].toByteArray());
        }
        catch(Throwable v0) {
            b.ʻ(v0);
            v0_1 = null;
        }

        return v0_1;
    }

    public void ʼ(String arg2) {
        this.ʿ = this.ʻ(arg2);
    }

    private PublicKey ʽ() {
        if(this.ʻ == null) {
            try {
                this.ʻ = CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(this.ʿ)).getPublicKey();
            }
            catch(Throwable v0) {
                b.ʻ(v0);
            }
        }

        return this.ʻ;
    }

    private byte[] ʾ() {
        if(this.ʽ != null) {
            byte[] v0 = this.ʽ;
            return v0;
        }

        try {
            JarFile v1 = new JarFile(this.ʼ.getPackageCodePath());
            JarEntry v0_2 = v1.getJarEntry("classes.dex");
            InputStream v1_1 = v1.getInputStream(((ZipEntry)v0_2));
            byte[] v2 = new byte[v1_1.available()];
            do {
            }
            while(v1_1.read(v2) != -1);

            v1_1.close();
            Certificate[] v0_3 = v0_2.getCertificates();
            if(v0_3 == null) {
                return null;
            }

            if(v0_3.length <= 0) {
                return null;
            }

            this.ʽ = v0_3[0].getEncoded();
            return this.ʽ;
        }
        catch(Throwable v0_1) {
            b.ʻ(v0_1);
        }

        return null;
    }
}

