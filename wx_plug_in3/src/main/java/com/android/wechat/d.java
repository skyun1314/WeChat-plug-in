package com.android.wechat;

/**
 * Created by zhaokai on 2018/3/22.
 */

import android.location.GpsStatus.Listener;
import android.location.LocationManager;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class d extends My_XC_MethodHook {
    public d(XSharedPreferences xSharedPreferences, ClassLoader classLoader, String str) {
        super(xSharedPreferences, classLoader, str);
    }

    public void a() {
        XposedHelpers.findAndHookMethod(LocationManager.class, "addGpsStatusListener", new Object[]{Listener.class, this});
    }

    protected void a(MethodHookParam methodHookParam) {
    }

    protected void b(MethodHookParam methodHookParam) {
        if (((Boolean) methodHookParam.getResult()).booleanValue()) {
            Listener listener = (Listener) methodHookParam.args[0];
            if (listener != null) {
                XposedHelpers.callMethod(listener, "onGpsStatusChanged", new Object[]{Integer.valueOf(1)});
                XposedHelpers.callMethod(listener, "onGpsStatusChanged", new Object[]{Integer.valueOf(3)});
                XposedBridge.log("LM:agsl addGpsStatusListener: " + this.c);
            }
        }
    }
}