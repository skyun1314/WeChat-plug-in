package com.android.wechat;

/**
 * Created by zhaokai on 2018/3/22.
 */

import android.app.PendingIntent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;

import com.amap.api.maps.model.LatLng;

import java.lang.reflect.Method;

import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class m extends My_XC_MethodHook {
    public m(XSharedPreferences xSharedPreferences, ClassLoader classLoader, String str) {
        super(xSharedPreferences, classLoader, str);
    }

    public void a() {
        XposedHelpers.findAndHookMethod(LocationManager.class, "requestLocationUpdates", new Object[]{XposedHelpers.findClass("android.location.LocationRequest", this.b), LocationListener.class, Looper.class, PendingIntent.class, this});
    }

    protected void a(MethodHookParam methodHookParam) {
    }

    protected void b(MethodHookParam methodHookParam) {
        LocationListener locationListener = (LocationListener) methodHookParam.args[1];
        if (locationListener != null) {
            LatLng b = com.android.wechat.HookGPS.b();
            Location a = HookGPS.a(b.latitude, b.longitude);
            XposedBridge.log(locationListener.toString() + " requestLocationUpdates: latitude: " + b.latitude + " longitude: " + b.longitude);
            Method findMethodBestMatch = XposedHelpers.findMethodBestMatch(locationListener.getClass(), "onLocationChanged", new Object[]{a});
            if (findMethodBestMatch != null) {
                XposedBridge.log("find " + locationListener.getClass() + " method onLocationChanged, invoke...");
                try {
                    findMethodBestMatch.invoke(locationListener, new Object[]{a});
                    return;
                } catch (Exception e) {
                    XposedBridge.log("onLocationChanged invoke Exception: " + e.toString());
                    return;
                }
            }
            XposedBridge.log("not found " + locationListener.getClass() + " method onLocationChanged");
        }
    }
}