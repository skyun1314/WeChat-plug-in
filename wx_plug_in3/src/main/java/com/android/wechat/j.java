package com.android.wechat;

/**
 * Created by zhaokai on 2018/3/22.
 */

import android.location.Location;
import android.location.LocationManager;

import com.amap.api.maps.model.LatLng;

import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class j extends My_XC_MethodHook {
    public j(XSharedPreferences xSharedPreferences, ClassLoader classLoader, String str) {
        super(xSharedPreferences, classLoader, str);
    }

    public void a() {
        XposedHelpers.findAndHookMethod(LocationManager.class, "getLastLocation", new Object[]{this});
    }

    protected void a(MethodHookParam methodHookParam) {
    }

    protected void b(MethodHookParam methodHookParam) {
        LatLng b = HookGPS.b();
        Location a = HookGPS.a(b.latitude, b.longitude);
        XposedBridge.log("getLastLocation: latitude: " + b.latitude + " longitude: " + b.longitude);
        methodHookParam.setResult(a);
    }
}