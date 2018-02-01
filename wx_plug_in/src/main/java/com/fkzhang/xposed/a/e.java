package com.fkzhang.xposed.a;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.json.JSONObject;

public class e {
    private final File ʻ;
    private final String ʼ;
    private JSONObject ʽ;

    public e(File file, String str) {
        this.ʻ = file;
        this.ʼ = str;
        this.ʽ = new JSONObject();
        try {
            ʻ();
        } catch (Throwable th) {
        }
    }

    public e(File file, String str, String str2) {
        this(new File(file, str), str2);
    }

    private String ʻ(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1048576];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read != -1) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                    return new String(byteArrayOutputStream.toByteArray());
                }
            }
        } catch (Throwable th) {
            return null;
        }
    }

    private void ʻ(File file, String str) {
        if (file != null) {
            try {
                if (file.exists() || file.createNewFile()) {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(str.getBytes());
                    fileOutputStream.close();
                }
            } catch (Throwable th) {
            }
        }
    }

    private void ʼ() {
        try {
            ʻ(this.ʻ, AESUtil.ʻ(this.ʼ, this.ʽ.toString()));
        } catch (Throwable th) {
        }
    }

    public String ʻ(String str, String str2) {
        try {
            if (this.ʽ != null && this.ʽ.has(str)) {
                str2 = this.ʽ.getString(str);
            }
        } catch (Throwable th) {
        }
        return str2;
    }

    public void ʻ() {
        if (this.ʻ.exists()) {
            try {
                this.ʽ = new JSONObject(AESUtil.ʼ(this.ʼ, ʻ(this.ʻ)));
            } catch (Throwable th) {
            }
        }
    }

    public boolean ʻ(String str) {
        try {
            if (this.ʽ != null) {
                return this.ʽ.has(str);
            }
        } catch (Throwable th) {
        }
        return false;
    }

    public boolean ʻ(String str, boolean z) {
        try {
            if (this.ʽ != null && this.ʽ.has(str)) {
                z = this.ʽ.getBoolean(str);
            }
        } catch (Throwable th) {
        }
        return z;
    }

    public void ʼ(String str, String str2) {
        try {
            if (this.ʽ != null) {
                this.ʽ.put(str, str2);
                ʼ();
            }
        } catch (Throwable th) {
        }
    }

    public void ʼ(String str, boolean z) {
        try {
            if (this.ʽ != null) {
                this.ʽ.put(str, z);
                ʼ();
            }
        } catch (Throwable th) {
        }
    }
}
