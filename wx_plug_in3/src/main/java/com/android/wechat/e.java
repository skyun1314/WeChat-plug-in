package com.android.wechat;

/**
 * Created by zhaokai on 2018/3/22.
 */


import android.location.GpsStatus.NmeaListener;
import android.location.LocationManager;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class e extends My_XC_MethodHook {
    public e(XSharedPreferences xSharedPreferences, ClassLoader classLoader, String str) {
        super(xSharedPreferences, classLoader, str);
    }

    public void a() {
        XposedHelpers.findAndHookMethod(LocationManager.class, "addNmeaListener", new Object[]{NmeaListener.class, this});
    }

    protected void a(MethodHookParam methodHookParam) {
        XposedBridge.log("LM:anl status=on: " + this.c);
        methodHookParam.setResult(Boolean.valueOf(false));
    }

    protected void b(MethodHookParam methodHookParam) {
    }
}