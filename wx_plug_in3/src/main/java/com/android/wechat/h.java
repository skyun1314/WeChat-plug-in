package com.android.wechat;

/**
 * Created by zhaokai on 2018/3/22.
 */


import android.location.GpsStatus;
import android.location.LocationManager;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class h extends My_XC_MethodHook {
    public h(XSharedPreferences xSharedPreferences, ClassLoader classLoader, String str) {
        super(xSharedPreferences, classLoader, str);
    }

    public void a() {
        XposedHelpers.findAndHookMethod(LocationManager.class, "getGpsStatus", new Object[]{GpsStatus.class, this});
    }

    protected void a(MethodHookParam methodHookParam) {
    }

    protected void b(MethodHookParam methodHookParam) {
        XposedBridge.log("LM:ggs status=on : " + this.c);
        GpsStatus gpsStatus = (GpsStatus) methodHookParam.getResult();
        methodHookParam.args[0] = gpsStatus;
        methodHookParam.setResult(gpsStatus);
    }
}