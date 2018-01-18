package com.example.oppo;

import android.content.Context;
import android.util.Log;

import com.oppo.statistics.util.SystemInfoUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zk on 2018/1/16.
 */

public class haha {

   /* public static String packBaseEvent(Context context, LinkedList<BaseEventBean> linkedList) {
        if (linkedList == null || linkedList.isEmpty()) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("head", getHeaderObject(context));
            jSONObject.put("body", getBaseEventBody(linkedList));
        } catch (Exception e) {
            Log.e("wodelog", "com.android.statistics", e);
        }
        return jSONObject.toString();
    }*/


    public static JSONObject getHeaderObject(Context arg4) {
        JSONObject v1 = new JSONObject();
        try {
            v1.put("model", "AOSP ON HAMMERHEAD");
            v1.put("imei", "359125053640852");
            v1.put("carrier", SystemInfoUtil.getCarrierName(arg4));//这里是看移动联通电信
            v1.put("appId", 2);
            v1.put("appVersion", "5.2.1");
            v1.put("channel", "1");
            v1.put("sdkVersion", 501);
            v1.put("ssoid", "0");/////这个地方应该跟oppo账号有关系。需要多拿几个手机测试(默认是0)
            v1.put("clientTime", String.valueOf(System.currentTimeMillis()));
            v1.put("romVersion", "aosp_hammerhead-userdebug 4.4.4 KTU84P eng.zk.20170810.152219 test-keys");
            v1.put("osVersion", "4.4.4");
            v1.put("androidVersion", "4.4.4");
            v1.put("access", "WIFI");//{UNKNOWN,WIFI,UNKNOWN,2G,3G,4G}
        } catch (Exception v0) {
            Log.e("wodelog", "com.android.statistics", v0);
        }

        return v1;
    }


    public static JSONObject getBaseEventObject(String arg6, String arg7, Map arg8, String arg9, long arg10) {//这个参数有点多先不看
        JSONObject v1 = new JSONObject();
        try {
            v1.put("eventID", arg7);
            v1.put("eventTag", arg6);
            v1.put("eventTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            v1.put("appId", "2");
            v1.put("appVersion", "5.2.1");
            v1.put("duration", arg10);
            Iterator v2 = arg8.keySet().iterator();
            while (v2.hasNext()) {
                Object v0_1 = v2.next();
                v1.put(((String) v0_1), arg8.get(v0_1));
            }
        } catch (Exception v0) {
            Log.e("wodelog", "com.android.statistics", v0);
        }

        return v1;
    }


    public static byte[] ads() {//http://data.ads.oppomobile.com/proxy/strategy/

        byte[] v0 = null;

        try {
            JSONObject v1 = new JSONObject();
            JSONObject v2 = new JSONObject();
            v2.put("imei", "359125053640852");
            v2.put("model", "AOSP on HammerHead");
            v2.put("osVersion", "AOSP_HAMMERHEAD-USERDEBUG 4.4.4 KTU84P ENG.ZK.20170810.152219 TEST-KEYS");
            v2.put("ptoVer", 100);//固定
            JSONObject v3 = new JSONObject();
            v3.put("pkgName", "com.oppo.market");
            v3.put("currTime", 0);//暂时是0，需要继续观察
            v1.put("head", v2);
            v1.put("body", v3);
            v0 = v1.toString().getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }


        return v0;
    }


}
