package com.example.zk.myapplication.com.example.kingroot;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.zk.myapplication.R;

@TargetApi(Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {
   public static String  pakacagename="com.RushRoot.kingroot";
    public static String dataDIr="/data/data/"+pakacagename;
    public static String app_krsdk=dataDIr+"/app_krsdk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        ComponentName componetName = new ComponentName("com.example.wx_plug_in3",
                "com.amap.searchdemo.MainActivity");
        Intent intent = new Intent();
        intent.setComponent(componetName);
        startActivity(intent);


    }
}
