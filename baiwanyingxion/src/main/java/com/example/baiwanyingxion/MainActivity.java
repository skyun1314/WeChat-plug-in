package com.example.baiwanyingxion;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {



    Handler handler=new Handler(new Handler.Callback() {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public boolean handleMessage(Message message) {

            switch (message.what){
                case 1:
                    String callback= (String) message.obj;
                    Log.e("wodelog","callback1："+callback);
                    callback=callback.substring(callback.indexOf("(")+1,callback.length()-1);
                    Log.e("wodelog","callback2："+callback);
                    try {
                        JSONObject jsonObject= JSONObject.parseObject(callback);
                        Object code = jsonObject.get("code");
                        JSONArray result = (JSONArray) jsonObject.get("result");
                        Log.e("wodelog","json_result:"+result);


                        JSONObject jbResult = JSON.parseObject(result.getString(result.size() - 1));

                        String title = jbResult.getString("title");
                        String answer = jbResult.getString("result");
                        Log.e("wodelog","title:"+title);
                        Log.e("wodelog","answer:"+answer);

                    } catch (Exception e1) {
                        Log.e("wodelog",e1.toString());
                    }

                    break;
            }

            return false;
        }
    });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Map<String, Object> mHeader = new HashMap<>();
        mHeader.put("User-Agent", "Mozilla/5.0 (Linux; Android 4.4.4; AOSP on HammerHead Build/KTU84P) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36 SogouSearch Android1.0 version3.0 AppVersion/4901");
        mHeader.put("Accept", "*/*");
        mHeader.put("Host", "140.143.49.31");
        mHeader.put("Referer", "http://nb.sa.sogou.com/");
        mHeader.put("Accept-Encoding", "gzip,deflate" );
        mHeader.put("X-Requested-With", "com.sogou.activity.src");
        mHeader.put("Url", "http://140.143.49.31/api/ans2?key=xigua&wdcallback=jQuery32107269316784551625273&_=1516195");
        mHeader.put("handler",handler);

        network.okhttp_get(mHeader);


    }
}
