package com.example.a2345;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       // aes.setPassword("5036e0ae5270154b55000002");
        //String s = aes.decryptNoPadding("ZUFIuH5k3ImuZQe2i1uEE9UsIqCT0axyQYYLA7sOivwkauOqfT+y9/ir19N6pDXWdWnQlQZPRmGjmC80vKEuu2K89kL5g/h/9dbkoXD97F2PLSqvCeOc+5zeicbzPl0FdjYBjgUbnZ8rqDJhCk5x+w==", "UTF-8");

        //Log.e("wodelog","解密："+s);

        // String buildGetParams = buildGetParams(getBaseQuery());

        //String haha="ud_get=" + AesHelper.encryptNoPadding(buildGetParams, "UTF-8");

        new Thread(new Runnable() {
            @Override
            public void run() {
                a(true);
                hahaxx(MainActivity.this);
            }
        }).start();



    }


    private static String reaMac(String str) {
        try {
            Reader fileReader = new FileReader(str);

            if (fileReader != null) {
                BufferedReader bufferedReader = new BufferedReader(fileReader, 1024);
                String str2 = bufferedReader.readLine();
                return str2;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static Map<String, Object> getBaseQuery() {//log.umsns.com/bar/get/5036e0ae5270154b55000002/?ud_get=
        Map<String, Object> hashMap = new HashMap();
        hashMap.put("imei", "359125053640852");
        hashMap.put("md5imei", "6532A64C4DA485C5194C6BDACE787B13");
        CharSequence mac = "f8:a9:d0:71:6d:07";
        hashMap.put("mac", "f8:a9:d0:71:6d:07");
        hashMap.put("en", "Wi-Fi");// 2G/3G
        hashMap.put("de","AOSP on HammerHead" /*Build.MODEL*/);
        hashMap.put("sdkv", "6.1.1");
        hashMap.put("os", "Android");
        hashMap.put("android_id", "e8aaef782ac9e225");
        hashMap.put("sn", "06deab2c43a8b14e");
        hashMap.put("os_version", "4.4.4");
        hashMap.put("dt", Long.valueOf(System.currentTimeMillis()));
        hashMap.put("ak", "5036e0ae5270154b55000002");//umeng key
        hashMap.put("pcv", "2.0");//固定
        hashMap.put("u_sharetype", "native");
        hashMap.put("tp", Integer.valueOf(1));
        hashMap.put("opid", Integer.valueOf(1));
        hashMap.put("uid", null);
        hashMap.put("name", "share");
        hashMap.put("dc", "com.umeng.share");
        hashMap.put("use_coco2dx",String.valueOf(0) );
        return hashMap;
    }

    private static String buildGetParams(Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = stringBuilder;
        for (String str : map.keySet()) {
            if (map.get(str) != null) {
                stringBuilder2 = stringBuilder2.append(str + "=" + URLEncoder.encode(map.get(str).toString()) + "&");
            }
        }
        return stringBuilder2.substring(0, stringBuilder2.length() - 1).toString();
    }



   /* public static void a(boolean z, String str, a aVar) {
       ("http://houtai.2345.com/api/getWheather", a(z, str), aVar);
    }*/

    private static void a(boolean z, String str) {
        Map<String, Object> hashMap = new HashMap();
        hashMap.put("app", "2345browser");
        hashMap.put("appVersion", "8.7.3oem");
        hashMap.put("imei", "359125053640852");
        hashMap.put("uid", "e8aaef782ac9e225");
        hashMap.put("channel", "sc-lejingda_ins_lhp");
        if (z) {
            hashMap.put("isNeedGenerateUrl", z);
        }
        hashMap.put("ids", "100-0_200-0_300-0_400-0_500-0");
    }



    private static void a(boolean z) {
      //  "http://update.app.2345.com/index.php"
        Map<String, Object> hashMap = new HashMap();
        String str = "07d5d4fd2d375805f4ec4e45e09ad62c";
        String a = "sc-lejingda_ins_lhp";
        String valueOf = String.valueOf("108");
        String d = "8.7.3oem";
        String a2 ="e89b158e4bcf988ebd09eb83f5378e87";//固定值签名的md5
        String str2 ="update";
        String a3 = a("authkey" + str + a + valueOf + d + a2 + str2);
        hashMap.put("authkey", "authkey");
        hashMap.put("appkey",str);
        hashMap.put("channel",a);
        hashMap.put("version",valueOf);
        hashMap.put("user_version",d);
        hashMap.put("old_md5",a2);
        hashMap.put("type", str2);
        hashMap.put("sign", a3);
        hashMap.put("device_model","AOSP%20on%20HammerHead"/* Build.MODEL*/);
        hashMap.put("os_version","4.4.4" /*Build.VERSION.RELEASE*/);
        hashMap.put("brand","Android"/* Build.BRAND*/);
        hashMap.put("memory", 1855 + "");//可变内存
        hashMap.put("kernel_version","0");
        if (!z) {
            hashMap.put("first_start_time", ( System.currentTimeMillis() / 1000) + "");
            hashMap.put("ignore_time", "0");
        }
    }

    public static String x_b(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer("");
            for (int i2 : digest) {
                if (i2 < 0) {
                    i2 += 256;
                }
                if (i2 < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i2));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String a(String str) {
      //  "authkey07d5d4fd2d375805f4ec4e45e09ad62csc-lejingda_ins_lhp1088.7.3oeme89b158e4bcf988ebd09eb83f5378e87update"
        String b = x_b(str);
        String b2 = x_b("N#gK3OgTw#eRUI8+8bZsti78P==4s.5");
        byte[] bytes = b.getBytes();
        int length = b2.length();
        int length2 = bytes.length;
        byte[] bArr = new byte[bytes.length];
        for (int i = 0; i < length2; i++) {
            int i2 = i % length;
            bArr[i] = (byte) (b2.charAt(i2) ^ bytes[i]);
        }
        try {
            return new String(Base64.encode(bArr, 0), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }



    @SuppressLint("WrongConstant")
    public static String haha(Context context){
   //"http://app.50bang.org/apk_md5/?_c=log&action=sendData";

        String app_md5 = null;
        try {
             app_md5 = haha_a("/data/app/com.browser_llqhz-1.apk");
        } catch (Exception e) {
            e.printStackTrace();
        }


        JSONObject jSONObject = null;
        try {
            jSONObject = new JSONObject();
            jSONObject.put("start_stamp",System.currentTimeMillis()/ 1000);
            jSONObject.put("channel", "sc-lejingda_ins_lhp");
            jSONObject.put("app_version", "8.7.3oem");
            jSONObject.put("version_code", 108+"");
            jSONObject.put("app_md5", app_md5);
        } catch (JSONException e) {
            e.printStackTrace();
        }
            //7b7c445ab1a07c2894095db39fff1875  固定值 appkey
        return      a("7b7c445ab1a07c2894095db39fff1875",jSONObject.toString());
    }


    public static void hahaxx(Context context){
        String str2="http://app.50bang.org/apk_md5/?_c=log&action=sendData";
        String str= "http://app.50bang.org/apk_md5/?_c=log&action=session";
        HttpPost b = new HttpPost(str2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( haha(context));
        stringBuilder.append(a(str, b));
        String a = x_b(stringBuilder.toString());

    }


    public static String a(String str, HttpPost httpPost) {

        HttpParams basicHttpParams = new BasicHttpParams();
        basicHttpParams.setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
        basicHttpParams.setParameter("http.protocol.content-charset", "UTF-8");
        basicHttpParams.setParameter("http.connection.timeout", Integer.valueOf(15000));
        basicHttpParams.setParameter("http.connection.stalecheck", Boolean.valueOf(false));
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        DefaultHttpClient a = new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);




        HttpGet c = new HttpGet(str);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            HttpResponse execute = a.execute(c);
            if (execute == null) {
                return "";
            }
            Header[] allHeaders = execute.getAllHeaders();
            for (int i = 0; i < allHeaders.length; i++) {
                String value;
                if (allHeaders[i].getValue().contains("sid")) {
                    value = allHeaders[i].getValue();
                    stringBuilder.append(value.substring(value.indexOf("=") + 1));
                }
                if (allHeaders[i].getValue().contains("key")) {
                    value = allHeaders[i].getValue();
                    stringBuilder.append(value.substring(value.indexOf("=") + 1));
                }

            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return "";
        }
    }




    public static String haha_a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        File file = new File(str);
        if (!file.isFile()) {
            return null;
        }
        byte[] bArr = new byte[1024];
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            FileInputStream fileInputStream = new FileInputStream(file);
            while (true) {
                int read = fileInputStream.read(bArr, 0, 1024);
                if (read != -1) {
                    instance.update(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    return a(instance.digest());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String a(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bArr.length; i++) {
            int i2 = (bArr[i] >>> 4) & 15;
            int i3 = 0;
            while (true) {
                if (i2 < 0 || i2 > 9) {
                    stringBuilder.append((char) ((i2 - 10) + 97));
                } else {
                    stringBuilder.append((char) (i2 + 48));
                }
                int i4 = bArr[i] & 15;
                i2 = i3 + 1;
                if (i3 >= 1) {
                    break;
                }
                i3 = i2;
                i2 = i4;
            }
        }
        return stringBuilder.toString();
    }




    public static String a(String str, String str2) {
        String a = x_b(str);
        byte[] bytes = str2.getBytes();
        int length = a.length();
        int length2 = bytes.length;
        byte[] bArr = new byte[bytes.length];
        for (int i = 0; i < length2; i++) {
            int i2 = i % length;
            bArr[i] = (byte) (a.charAt(i2) ^ bytes[i]);
        }
        try {
            return new String(Base64.encode(bArr, 0), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }



}
