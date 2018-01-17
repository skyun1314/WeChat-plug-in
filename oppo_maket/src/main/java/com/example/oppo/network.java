package com.example.oppo;

import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zk on 2018/1/11.
 */

public class network {


    public static void okhttp_get(Map<String, String> map) {
        final Request.Builder builder = new Request.Builder().url(map.get("Url"));
        map.remove("Url");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            builder.addHeader(entry.getKey(), entry.getValue());  //将请求头以键值对形式添加，可添加多个请求头
        }

        final Request request = builder.build();
        final OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build(); //设置各种超时时间
        final Call call = client.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    if (response != null) {
                        String xml = response.body().string();
                        String str = response.networkResponse().toString();
                        Log.i("wodelog", "network---" + str);
                        Log.i("wodelog", "headers---" + response.headers().toString());

                        if (xml.length() > 4000) {
                            for (int i = 0; i < xml.length(); i += 4000) {
                                if (i + 4000 < xml.length())
                                    Log.i("wodelog", xml.substring(i, i + 4000));
                                else
                                    Log.i("wodelog", xml.substring(i, xml.length()));
                            }
                        } else {
                            Log.i("wodelog", xml);
                        }


                    } else {

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public static void okhttp_post(Map<String, Object> map) {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/x-protostuff; charset=UTF-8");


        Request.Builder builder = new Request.Builder().url(map.get("Url").toString()).post(RequestBody.create(MEDIA_TYPE_MARKDOWN, (byte[]) map.get("postxx")));
        map.remove("Url");
        map.remove("postxx");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            builder.addHeader(entry.getKey(), entry.getValue().toString());  //将请求头以键值对形式添加，可添加多个请求头
        }

        Request request = builder.build();
        final Call call = okHttpClient.newCall(request);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Response response = call.execute();
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }
}
