package com.example.oppo;

import android.os.Handler;
import android.os.Message;
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


    public interface  response_haha{
        void haha( Response response);
    }


    public static void okhttp_get(Map<String, Object> map, final response_haha response_haha) {


        final Request.Builder builder = new Request.Builder().url((String) map.get("Url"));
        map.remove("Url");

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            builder.addHeader(entry.getKey(), (String) entry.getValue());  //将请求头以键值对形式添加，可添加多个请求头
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
                      /*  String xml = response.body().string();
                        String str = response.networkResponse().toString();
                      //  Log.i("wodelog", "network---" + str);
                      //  Log.i("wodelog", "headers---" + response.headers().toString());

                        if (xml.length() > 4000) {
                            for (int i = 0; i < xml.length(); i += 4000) {
                                if (i + 4000 < xml.length())
                                    Log.i("wodelog", xml.substring(i, i + 4000));
                                else
                                    Log.i("wodelog", xml.substring(i, xml.length()));
                            }
                        } else {
                            Log.i("wodelog", xml);
                        }*/
                        response_haha.haha(response);



                    } else {
                        Log.i("wodelog", "response==null");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("wodelog", e.toString());
                }
            }
        }).start();
    }


    public static void okhttp_post(Map<String, Object> map) {


        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse(map.get("MediaType").toString());
        map.remove("MediaType");
        Request.Builder builder = new Request.Builder().url(map.get("Url").toString()).post(RequestBody.create(MEDIA_TYPE_MARKDOWN, (byte[]) map.get("postxx")));
        map.remove("Url");
        map.remove("postxx");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Log.e("wodelog","key= " + entry.getKey() + " and value= " + entry.getValue());
            if(entry.getValue()==null){
                builder.removeHeader(entry.getKey());
            }else{
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
                    Log.e("wodelog", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public static void haha(){

    }


}
