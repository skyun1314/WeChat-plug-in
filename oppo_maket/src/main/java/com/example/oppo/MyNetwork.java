package com.example.oppo;

import android.annotation.SuppressLint;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

/**
 * Created by zk on 2018/1/11.
 */

public class MyNetwork {


    public interface response_interface {
        Object my_object = null;

        Object do_response(Response response);
    }


    /**
     * @param map
     * @param response_interface
     * @return
     */
    public static Object okhttp_get(Map<String, Object> map, final response_interface response_interface) {


        final Request.Builder builder = new Request.Builder().url((String) map.get("Url"));
        map.remove("Url");


        for (Map.Entry<String, Object> entry : map.entrySet()) {
            builder.addHeader(entry.getKey(), (String) entry.getValue());  //将请求头以键值对形式添加，可添加多个请求头
        }

        final Request request = builder.build();
        final OkHttpClient client = new OkHttpClient.Builder()


                .sslSocketFactory(createSSLSocketFactory())
                .hostnameVerifier(new TrustAllHostnameVerifier())

                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build(); //设置各种超时时间
        final Call call = client.newCall(request);


        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null && response_interface != null) {
            Object o = response_interface.do_response(response);
        } else {
            Log.i("wodelog", "response==null");
        }
        return null;
    }



    public static void okhttp_post(Map<String, Object> map) {


        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse(map.get("MediaType").toString());///这是 Content-Type  请求头
        map.remove("MediaType");



        Request.Builder builder = new Request.Builder().url(map.get("Url").toString()).post(RequestBody.create(MEDIA_TYPE_MARKDOWN,(String) map.get("postxx")));
        map.remove("Url");
        map.remove("postxx");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
           // Log.e("wodelog", "key= " + entry.getKey() + " and value= " + entry.getValue());
            if (entry.getValue() == null) {
                builder.removeHeader(entry.getKey());
            } else {
                builder.addHeader(entry.getKey(), entry.getValue().toString());  //将请求头以键值对形式添加，可添加多个请求头
            }


        }
        Request request = builder.build();


        final Call call = okHttpClient.newCall(request);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Response response = call.execute();

                    String string = response.body().string();


                   /* JSONObject jsonObject=new JSONObject(string);
                    JSONArray jsonArray = (JSONArray) jsonObject.get("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject o = (JSONObject) jsonArray.get(i);
                        Log.e("wodelog", "结果："+o.get("username")+" "+o.get("start")+" "+o.get("arrive")+" "+o.get("mobile"));
                    }*/

                   Log.e("wodelog",string);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }



    /**
     * 默认信任所有的证书
     * TODO 最好加上证书认证，主流App都有自己的证书
     *
     * @return
     */
    @SuppressLint("TrulyRandom")
    private static SSLSocketFactory createSSLSocketFactory() {

        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }



    static class GzipRequestInterceptor implements Interceptor {
        @Override public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            if (originalRequest.body() == null || originalRequest.header("Content-Encoding") != null) {
                return chain.proceed(originalRequest);
            }

            Request compressedRequest = originalRequest.newBuilder()
                    .header("Content-Encoding", "gzip")
                    .method(originalRequest.method(), forceContentLength(gzip(originalRequest.body())))
                    .build();
            return chain.proceed(compressedRequest);
        }

        /** https://github.com/square/okhttp/issues/350 */
        private RequestBody forceContentLength(final RequestBody requestBody) throws IOException {
            final Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            return new RequestBody() {
                @Override
                public MediaType contentType() {
                    return requestBody.contentType();
                }

                @Override
                public long contentLength() {
                    return buffer.size();
                }

                @Override
                public void writeTo(BufferedSink sink) throws IOException {
                    sink.write(buffer.snapshot());
                }
            };
        }

        private RequestBody gzip(final RequestBody body) {
            return new RequestBody() {
                @Override public MediaType contentType() {
                    return body.contentType();
                }

                @Override public long contentLength() {
                    return -1; // We don't know the compressed length in advance!
                }

                @Override public void writeTo(BufferedSink sink) throws IOException {
                    BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                    body.writeTo(gzipSink);
                    gzipSink.close();
                }
            };
        }
    }



    public static class GZIPUtils {

        public static final String GZIP_ENCODE_UTF_8 = "UTF-8";

        public static final String GZIP_ENCODE_ISO_8859_1 = "ISO-8859-1";

        /**
         * 字符串压缩为GZIP字节数组
         *
         * @param str
         * @return
         */
        public static byte[] compress(String str) {
            return compress(str, GZIP_ENCODE_UTF_8);
        }

        /**
         * 字符串压缩为GZIP字节数组
         *
         * @param str
         * @param encoding
         * @return
         */
        public static byte[] compress(String str, String encoding) {
            if (str == null || str.length() == 0) {
                return null;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip;
            try {
                gzip = new GZIPOutputStream(out);
                gzip.write(str.getBytes(encoding));
                gzip.close();
            } catch (IOException e) {
            }
            return out.toByteArray();
        }

        /**
         * GZIP解压缩
         *
         * @param bytes
         * @return
         */
        public static byte[] uncompress(byte[] bytes) {
            if (bytes == null || bytes.length == 0) {
                return null;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            try {
                GZIPInputStream ungzip = new GZIPInputStream(in);
                byte[] buffer = new byte[256];
                int n;
                while ((n = ungzip.read(buffer)) >= 0) {
                    out.write(buffer, 0, n);
                }
            } catch (IOException e) {
            }

            return out.toByteArray();
        }

        /**
         * @param bytes
         * @return
         */
        public static String uncompressToString(byte[] bytes) {
            return uncompressToString(bytes, GZIP_ENCODE_UTF_8);
        }

        /**
         * @param bytes
         * @param encoding
         * @return
         */
        public static String uncompressToString(byte[] bytes, String encoding) {
            if (bytes == null || bytes.length == 0) {
                return null;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            try {
                GZIPInputStream ungzip = new GZIPInputStream(in);
                byte[] buffer = new byte[256];
                int n;
                while ((n = ungzip.read(buffer)) >= 0) {
                    out.write(buffer, 0, n);
                }
                return out.toString(encoding);
            } catch (IOException e) {
            }
            return null;
        }

    }


}
