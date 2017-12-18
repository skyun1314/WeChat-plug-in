package com.example.zk.myapplication.com.example.kingroot;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import java.io.File;

@TargetApi(Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {
   public static String  pakacagename="com.RushRoot.kingroot";
    public static String dataDIr="/data/data/"+pakacagename;
    public static String app_krsdk=dataDIr+"/app_krsdk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {

                Util.CopyAssets(MainActivity.this,"haha",dataDIr);

                Log.e("wodelog","复制完毕");


                c c = new c(MainActivity.this, new File(app_krsdk), null, null);
                c.a();

            }
        }).start();;
*/

    }
}
