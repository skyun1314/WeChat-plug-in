package android.app.ActivityThread;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by zk on 2018/1/22.
 */

public class ActivityThread
{


    public void haha(){
        haha(new haha());
    }


    public static void haha(haha data){
        String tag="snowInject";
        //snownote 2016/12/
        //=================全局注入之修改java层===================================================
        String processName = data.processName;
        Log.e(tag,"--processname="+processName);
        File ssfile=new File("/data/local/changeFrameWorkHookConfig.txt");
        if(ssfile.exists()) {

            FileReader ssfr = null;
            try {
                ssfr = new FileReader(ssfile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            BufferedReader ssbr = new BufferedReader(ssfr);
            String tagetPackage = null;
            String soPath = null;
            try {
                tagetPackage = ssbr.readLine();//得到进程名，一般就是包名
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (tagetPackage != null) {
                try {
                    soPath = ssbr.readLine();//得到要加载的so路径
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.e("snowInject","配置文件存在——需要hook的包名为："+tagetPackage+"\n需要加载的so为："+soPath);
            try {
                ssfr.close();
                ssbr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (processName.equals(tagetPackage)) {
                Log.e(tag, "需要hook的app已经打开：" + tagetPackage);
                if (soPath != null) {
                    Log.e(tag, "---start-load-soPath：" + soPath);
                    System.load(soPath);
                }
            }else{
                Log.e(tag, "这个包名不是需要hook的：" + tagetPackage);
            }
        }else{
            Log.e("snowInject","配置文件不存在：");
        }
    }
}
