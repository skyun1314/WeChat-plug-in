package com.android.wechat;

/**
 * Created by zhaokai on 2018/3/22.
 */


import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XSharedPreferences;

public abstract class My_XC_MethodHook extends XC_MethodHook {
    protected XSharedPreferences a;
    protected ClassLoader b;
    protected String c;

    public abstract void a();

    protected abstract void a(MethodHookParam methodHookParam);

    protected abstract void b(MethodHookParam methodHookParam);

    public My_XC_MethodHook(XSharedPreferences xSharedPreferences, ClassLoader classLoader, String str) {
        this.a = xSharedPreferences;
        this.b = classLoader;
        this.c = str;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        b(methodHookParam);
    }

    protected void beforeHookedMethod(MethodHookParam methodHookParam) {
        a(methodHookParam);
    }
}