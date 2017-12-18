package com.example.zk.myapplication;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zk on 2017/12/5.
 */

public class okhttp {

    static void postAsynHttp(final Activity context) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("size", "10")
                .build();
        Request request = new Request.Builder()
                .url("http://api.1-blog.com/biz/bizserver/article/list.do")
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(context.getApplicationContext(), "请求失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.i("wodelog", str);

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context.getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }

}
