package com.android.wechat;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zhaokai on 2018/3/21.
 */

public class Util {

   /// 往SD卡写入文件的方法
    public static void savaFileToSD(String filename, String filecontent)  {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
            try {
            filename = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + filename;

            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
            FileOutputStream output = new FileOutputStream(filename);
            output.write(filecontent.getBytes());
            //将String字符串以字节流的形式写入到输出流中

                output.close();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(Main.wodetag, "写文件错误"+e.toString());
            }
            //关闭输出流
    }

    //读取SD卡中文件的方法
//定义读取文件的方法:
    public static String readFromSD(String filename){
        StringBuilder sb = new StringBuilder("");
            try {
            filename = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + filename;
            //打开文件输入流
            FileInputStream input = new FileInputStream(filename);
            byte[] temp = new byte[1024];

            int len = 0;
            //读取文件内容:
            while ((len = input.read(temp)) > 0) {
                sb.append(new String(temp, 0, len));
            }
            //关闭输入流

                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return sb.toString();
    }

}
