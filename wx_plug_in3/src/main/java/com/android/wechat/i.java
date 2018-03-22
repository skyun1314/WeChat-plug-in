package com.android.wechat;

/**
 * Created by zhaokai on 2018/3/22.
 */


import android.location.LocationManager;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class i extends My_XC_MethodHook {
    public i(XSharedPreferences xSharedPreferences, ClassLoader classLoader, String str) {
        super(xSharedPreferences, classLoader, str);
    }

    public void a() {
        XposedHelpers.findAndHookMethod(LocationManager.class, "getLastKnownLocation", new Object[]{String.class, this});
    }

    protected void a(MethodHookParam methodHookParam) {
    }

    protected void b(MethodHookParam methodHookParam) {
        XposedBridge.log("LM:gbp return GPS_PROVIDER directly: " + this.c);
        methodHookParam.setResult("gps");
    }
}