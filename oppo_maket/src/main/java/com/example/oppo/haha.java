package com.example.oppo;

import android.content.Context;
import android.util.Log;

import com.oppo.statistics.util.SystemInfoUtil;

import org.json.JSONObject;

/**
 * Created by zk on 2018/1/16.
 */

public class haha {

    public static String packBaseEvent(Context context, LinkedList<BaseEventBean> linkedList) {
        if (linkedList == null || linkedList.isEmpty()) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("head", getHeaderObject(context));
            jSONObject.put("body", getBaseEventBody(linkedList));
        } catch (Exception e) {
            Log.e("wodelog","com.android.statistics", e);
        }
        return jSONObject.toString();
    }



    public static JSONObject getHeaderObject(Context arg4) {
        JSONObject v1 = new JSONObject();
        try {
            v1.put("model", "AOSP ON HAMMERHEAD");
            v1.put("imei", "359125053640852");
            v1.put("carrier", SystemInfoUtil.getCarrierName(arg4));//这里是看移动联通电信
            v1.put("appId", 2);
            v1.put("appVersion","5.2.1");
            v1.put("channel", "1");
            v1.put("sdkVersion", 501);
            v1.put("ssoid", AccountUtil.getSsoId(arg4));
            v1.put("clientTime", String.valueOf(TimeInfoUtil.getCurrentTime()));
            v1.put("romVersion", SystemInfoUtil.getRomVersion());
            v1.put("osVersion", SystemInfoUtil.getOsVersion());
            v1.put("androidVersion", SystemInfoUtil.getAndroidVersion());
            v1.put("access", NetInfoUtil.getNetworkType(arg4));
        }
        catch(Exception v0) {
            Log.e("wodelog","com.android.statistics", v0);
        }

        return v1;
    }



}
