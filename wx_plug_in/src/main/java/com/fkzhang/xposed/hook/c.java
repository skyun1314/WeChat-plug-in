package com.fkzhang.xposed.hook;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class c {
    public static void ʻ(LoadPackageParam loadPackageParam) {
        try {
            if (loadPackageParam.packageName.equals("com.fkzhang.wechatxposed")) {
                XposedHelpers.findAndHookMethod(XposedHelpers.findClass("com.fkzhang.wechatxposed.ModuleActiveCheck", loadPackageParam.classLoader), "isActiveVersion", new Object[]{new XC_MethodHook() {
                    protected void afterHookedMethod(MethodHookParam methodHookParam) {
                        methodHookParam.setResult(Integer.valueOf(23));
                    }
                }});
            }
        } catch (Throwable th) {
            XposedBridge.log(th);
        }
    }
}
