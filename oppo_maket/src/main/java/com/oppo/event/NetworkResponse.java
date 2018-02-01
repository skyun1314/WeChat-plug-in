package com.oppo.event;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

public class NetworkResponse implements Serializable {
    private static final long serialVersionUID = 1;
    private byte[] data;
    public Map<String, String> headers;
    private transient InputStream inputStream;
    public String mUrl;
    public long networkTimeMs;
    public boolean notModified;
    public int statusCode;

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
            }
        }
    }

    public void updateBytes(byte[] bArr) {
        this.data = bArr;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public void setUrl(String str) {
        this.mUrl = str;
    }

    public byte[] readData() throws IOException {
        if (this.data == null && this.inputStream != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[8192];
            while (true) {
                int read = this.inputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
            this.data = byteArrayOutputStream.toByteArray();
            closeQuietly(this.inputStream);
            byteArrayOutputStream.close();
        }
        return null;
    }

    public byte[] getData() throws IOException {
        if (this.data == null) {
            readData();
        }
        return this.data;
    }

    public void updateInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public InputStream getInputStrem() {
        return this.inputStream;
    }

    public int getCode() {
        return this.statusCode;
    }

    public void close() {
        closeQuietly(this.inputStream);
    }
}