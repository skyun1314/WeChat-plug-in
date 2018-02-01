package com.fkzhang.xposed.hook;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.fkzhang.wechatxposed.a;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import java.util.Iterator;
import java.util.List;

public class b {
    private static void ʻ() {
        try {
            XposedHelpers.findAndHookMethod(ActivityManager.class, "getRunningAppProcesses", new Object[]{new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam methodHookParam) {
                    try {
                        List list = (List) methodHookParam.getResult();
                        if (list != null && !list.isEmpty()) {
                            Iterator it = list.iterator();
                            while (it.hasNext()) {
                                if (b.ʼ(((RunningAppProcessInfo) it.next()).processName)) {
                                    it.remove();
                                }
                            }
                            methodHookParam.setResult(list);
                        }
                    } catch (Throwable th) {
                        XposedBridge.log(th);
                    }
                }
            }});
        } catch (Throwable th) {
            XposedBridge.log(th);
        }
        try {
            XposedHelpers.findAndHookMethod(ActivityManager.class, "getRunningTasks", new Object[]{Integer.TYPE, new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam methodHookParam) {
                    try {
                        List list = (List) methodHookParam.getResult();
                        if (list != null && !list.isEmpty()) {
                            Iterator it = list.iterator();
                            while (it.hasNext()) {
                                RunningTaskInfo runningTaskInfo = (RunningTaskInfo) it.next();
                                if (b.ʼ(runningTaskInfo.baseActivity.getPackageName()) || b.ʼ(runningTaskInfo.topActivity.getPackageName())) {
                                    it.remove();
                                }
                            }
                            methodHookParam.setResult(list);
                        }
                    } catch (Throwable th) {
                        XposedBridge.log(th);
                    }
                }
            }});
        } catch (Throwable th2) {
            XposedBridge.log(th2);
        }
        try {
            XposedHelpers.findAndHookMethod(ActivityManager.class, "getRunningServices", new Object[]{Integer.TYPE, new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam methodHookParam) {
                    try {
                        List list = (List) methodHookParam.getResult();
                        if (list != null && !list.isEmpty()) {
                            Iterator it = list.iterator();
                            while (it.hasNext()) {
                                if (b.ʼ(((RunningServiceInfo) it.next()).process)) {
                                    it.remove();
                                }
                            }
                            methodHookParam.setResult(list);
                        }
                    } catch (Throwable th) {
                        XposedBridge.log(th);
                    }
                }
            }});
        } catch (Throwable th22) {
            XposedBridge.log(th22);
        }
    }

    public static void ʻ(LoadPackageParam loadPackageParam) {
        try {
            String str = loadPackageParam.packageName;
            if (str.contains("tencen") || str.contains("safe") || str.contains("secure") || str.contains("security")) {
                ʻ(loadPackageParam.classLoader);
                ʻ();
            }
        } catch (Throwable th) {
            XposedBridge.log(th);
        }
    }

    private static void ʻ(ClassLoader classLoader) {
        Class findClass = XposedHelpers.findClass("android.app.ApplicationPackageManager", classLoader);
        if (findClass != null) {
            try {
                XposedHelpers.findAndHookMethod(findClass, "getInstalledApplications", new Object[]{Integer.TYPE, new XC_MethodHook() {
                    protected void afterHookedMethod(MethodHookParam methodHookParam) {
                        try {
                            List list = (List) methodHookParam.getResult();
                            if (list != null && !list.isEmpty()) {
                                Iterator it = list.iterator();
                                while (it.hasNext()) {
                                    if (b.ʼ(((ApplicationInfo) it.next()).packageName)) {
                                        it.remove();
                                    }
                                }
                                methodHookParam.setResult(list);
                            }
                        } catch (Throwable th) {
                            XposedBridge.log(th);
                        }
                    }
                }});
            } catch (Throwable th) {
                XposedBridge.log(th);
            }
            try {
                XposedHelpers.findAndHookMethod(findClass, "getInstalledPackages", new Object[]{Integer.TYPE, new XC_MethodHook() {
                    protected void afterHookedMethod(MethodHookParam methodHookParam) {
                        try {
                            List list = (List) methodHookParam.getResult();
                            if (list != null && !list.isEmpty()) {
                                Iterator it = list.iterator();
                                while (it.hasNext()) {
                                    if (b.ʼ(((PackageInfo) it.next()).packageName)) {
                                        it.remove();
                                    }
                                }
                                methodHookParam.setResult(list);
                            }
                        } catch (Throwable th) {
                            XposedBridge.log(th);
                        }
                    }
                }});
            } catch (Throwable th2) {
                XposedBridge.log(th2);
            }
            try {
                XposedHelpers.findAndHookMethod(findClass, "getPackageInfo", new Object[]{String.class, Integer.TYPE, new XC_MethodHook() {
                    protected void beforeHookedMethod(MethodHookParam methodHookParam) {
                        try {
                            String str = (String) methodHookParam.args[0];
                            if (!TextUtils.isEmpty(str)) {
                                if (b.ʼ(str)) {
                                    methodHookParam.args[0] = "AESUtil.b.c";
                                } else if (str.startsWith("!#!")) {
                                    methodHookParam.args[0] = str.substring(3, str.length());
                                }
                            }
                        } catch (Throwable th) {
                            XposedBridge.log(th);
                        }
                    }
                }});
            } catch (Throwable th22) {
                XposedBridge.log(th22);
            }
            try {
                XposedHelpers.findAndHookMethod(findClass, "getApplicationInfo", new Object[]{String.class, Integer.TYPE, new XC_MethodHook() {
                    protected void beforeHookedMethod(MethodHookParam methodHookParam) {
                        try {
                            String str = (String) methodHookParam.args[0];
                            if (!TextUtils.isEmpty(str)) {
                                if (b.ʼ(str)) {
                                    methodHookParam.args[0] = "AESUtil.b.c";
                                } else if (str.startsWith("!#!")) {
                                    methodHookParam.args[0] = str.substring(3, str.length());
                                }
                            }
                        } catch (Throwable th) {
                            XposedBridge.log(th);
                        }
                    }
                }});
            } catch (Throwable th222) {
                XposedBridge.log(th222);
            }
        }
    }

    private static boolean ʼ(String str) {
        return (TextUtils.isEmpty(str) || str.startsWith("!#!") || (!str.contains(a.ﹶ) && !str.contains("de.robv.android.xposed.installer") && !str.contains("pro.burgerz.wsm.manager"))) ? false : true;
    }
}
