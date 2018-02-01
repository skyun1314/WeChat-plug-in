package com.tianyl.android.wechat.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class NetUtil {

    private static String auth = null;

    public static String post(String url, String msg) {
        StringBuffer result = new StringBuffer();
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            if (conn instanceof HttpsURLConnection) {
                HttpsURLConnection sconn = (HttpsURLConnection) conn;
                try {
                    SSLContext context = SSLContext.getInstance("TLS");
                    context.init(null, new TrustManager[] { new X509TrustManager() {

                        @Override
                        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                                throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                                throws CertificateException {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }

                    } }, new SecureRandom());
                    sconn.setSSLSocketFactory(context.getSocketFactory());
                    sconn.setHostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String arg0, SSLSession arg1) {
                            return true;
                        }
                    });
                } catch (NoSuchAlgorithmException | KeyManagementException e) {
                    e.printStackTrace();
                }

            }

            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if(auth == null){
                auth = FileUtil.read(new File(FileUtil.getBathPath() + "auth")).trim();
            }
            conn.setRequestProperty("Authorization", "Basic " + auth);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.write(msg.getBytes(Charset.forName("utf-8")));
            out.flush();
            out.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                result.append(temp);
            }
            reader.close();
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
