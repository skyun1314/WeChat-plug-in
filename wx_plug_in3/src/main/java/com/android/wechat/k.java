package com.android.wechat;

/**
 * Created by zhaokai on 2018/3/22.
 */

import android.location.LocationManager;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import java.util.ArrayList;
import java.util.List;

public class k extends My_XC_MethodHook {
    public k(XSharedPreferences xSharedPreferences, ClassLoader classLoader, String str) {
        super(xSharedPreferences, classLoader, str);
    }

    public void a() {
        XposedBridge.hookAllMethods(LocationManager.class, "getProviders", this);
    }

    protected void a(MethodHookParam methodHookParam) {
    }

    protected void b(MethodHookParam methodHookParam) {
        Object obj;
        Object result = methodHookParam.getResult();
        if (result == null) {
            obj = 1;
        } else if (!((List) result).contains("gps")) {
            obj = null;
        } else {
            return;
        }
        if ((int)obj == 1) {
            result = new ArrayList();
        }
        XposedBridge.log("LM:gp add GPS_PROVIDER to the list" + this.c);
        ((List) result).add("gps");
        methodHookParam.setResult(result);
    }
}