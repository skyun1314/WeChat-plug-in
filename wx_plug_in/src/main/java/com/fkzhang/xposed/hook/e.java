package com.fkzhang.xposed.hook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AndroidAppHelper;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Process;
import android.text.TextUtils;
import com.fkzhang.wechatxposed.a;
import com.fkzhang.xposed.a.b;
import com.fkzhang.xposed.a.d;
import com.fkzhang.xposed.a.f;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashSet;

public abstract class e implements com.fkzhang.xposed.hook.d {
    protected static HashSet<String> ʻ = new HashSet();
    protected static HashSet<String> ʼ = new HashSet();
    protected static com.fkzhang.xposed.a.e ʽ;
    private static f ʾ;

    static {
        String v0_1;
        e.ʻ = new HashSet();
        e.ʼ = new HashSet();
        try {
            e.ʾ = new f("com.fkzhang.wechatxposed");
            v0_1 = e.ʾ.ʻ(a.ﹳ, "");
            if(!TextUtils.isEmpty(((CharSequence)v0_1))) {
                e.ʻ.addAll(Arrays.asList(v0_1.split(";")));
            }

            v0_1 = e.ʾ.ʻ(a.ﾞ, "");
            if(!TextUtils.isEmpty(((CharSequence)v0_1))) {
                e.ʼ.addAll(Arrays.asList(v0_1.split(";")));
            }


        }
        catch(Throwable v0) {
        }

        try {

            File v0_2 = new File(Environment.getExternalStorageDirectory(), a.ٴ);
            if(v0_2.exists()) {
                e.ʽ = new com.fkzhang.xposed.a.e(v0_2, a.ˏ, a.ᵢ);
                v0_1 = e.ʽ.ʻ(a.ﹳ, "");
                if(!TextUtils.isEmpty(((CharSequence)v0_1))) {
                    e.ʻ.addAll(Arrays.asList(v0_1.split(";")));
                }

                v0_1 = e.ʽ.ʻ(a.ﾞ, "");
                if(!TextUtils.isEmpty(((CharSequence)v0_1))) {
                    e.ʼ.addAll(Arrays.asList(v0_1.split(";")));
                }


            }


        }
        catch(Throwable v0) {
        }
    }

    @SuppressLint("WrongConstant")
    private Context ʻ(Context context, String str) {
        try {
            return context.createPackageContext(str, 3);
        } catch (Throwable th) {
            XposedBridge.log(th);
            return null;
        }
    }

    private void ʼ(String str) {
        if (!TextUtils.isEmpty(str) && !str.startsWith("/")) {
            b.ʻ(new File(str));
        }
    }

    private Context ʾ() {
        Context currentApplication = AndroidAppHelper.currentApplication();
        if (currentApplication != null) {
            return currentApplication;
        }
        Object callStaticMethod = XposedHelpers.callStaticMethod(XposedHelpers.findClass("android.app.ActivityThread", null), "currentActivityThread", new Object[0]);
        return callStaticMethod != null ? (Context) XposedHelpers.callMethod(callStaticMethod, "getSystemContext", new Object[0]) : currentApplication;
    }

    private boolean ʿ() {
        try {
            return (ʾ != null && ʾ.ʻ("isreset", false)) || (ʽ != null && ʽ.ʻ("isreset", false));
        } catch (Throwable th) {
            XposedBridge.log(th);
            return false;
        }
    }

    protected abstract void ʻ();

    public void ʻ(LoadPackageParam loadPackageParam, String str) {
        if (!ʻ(str) && !ʼ.contains(loadPackageParam.processName)) {
            int myPid = Process.myPid();
            try {
                XposedHelpers.findAndHookMethod(Activity.class, "onCreate", new Object[]{Bundle.class, new XC_MethodHook() {
                    /*final *//* synthetic *//* e ʻ;

                    {
                        this.ʻ = r1;
                    }*/

                    protected void beforeHookedMethod(MethodHookParam methodHookParam) {
                        try {
                            Activity activity = (Activity) methodHookParam.thisObject;
                            if (activity.getIntent().getComponent().getClassName().endsWith("AlbumPreviewUI") && new f(activity, "com.fkzhang.wechatxposed").ʻ("remove_max_limit", false)) {
                                activity.getIntent().putExtra("max_select_count", 1000);
                            }
                        } catch (Throwable th) {
                            XposedBridge.log(th);
                        }
                    }
                }});
            } catch (Throwable th) {
                XposedBridge.log(th);
            }
            if ((loadPackageParam.processName.equals(loadPackageParam.packageName) && (loadPackageParam.isFirstApplication || AndroidAppHelper.currentApplication() != null)) || !loadPackageParam.processName.startsWith(loadPackageParam.packageName)) {
                try {
                    Context ʾ = ʾ();
                    if (!b.ʻ(ʾ, myPid).contains(":")) {
                        final Context ʻ = ʻ(ʾ, str);
                        final Context ʻ2 = ʻ(ʾ, "com.fkzhang.wechatxposed");
                        if (ʻ != null && ʻ2 != null && !b.ʻ(ʻ2)) {
                            PackageInfo packageInfo = ʻ.getPackageManager().getPackageInfo(str, 0);
                            String str2 = packageInfo.versionName;
                            myPid = packageInfo.versionCode;
                            final ClassLoader classLoader = getClass().getClassLoader();
                            if (TextUtils.isEmpty(str2)) {
                                if (str.equals("com.tencent.mm4")) {
                                    str2 = "6.3.5";
                                } else {
                                    return;
                                }
                            }
                            if (str2.equals("9.0")) {
                                str2 = "6.3.9";
                            }
                            final ContentValues contentValues = new ContentValues();
                            contentValues.put(a.י, Integer.valueOf(23));
                            contentValues.put(a.ـ, "1.18");
                            contentValues.put(a.ˋˋ, Integer.valueOf(myPid));
                            contentValues.put(a.ˊˊ, str2);
                            contentValues.put(a.ˈˈ, str);
                            contentValues.put(a.ᐧᐧ, loadPackageParam.processName);
                            final LoadPackageParam loadPackageParam2 = loadPackageParam;
                            new Handler().post(new Runnable() {
                                final /* synthetic */ e ˆ=e.this;

                                public void run() {
                                    try {
                                        if (ˆ.ʿ()) {
                                            XposedBridge.log(a.ٴ + ": reset mode");
                                            File[] listFiles = ʻ.getFilesDir().listFiles(new FilenameFilter() {
                                               /* final *//* synthetic *//* AnonymousClass2 ʻ;

                                                {
                                                    this.ʻ = r1;
                                                }*/

                                                public boolean accept(File file, String str) {
                                                    return str.startsWith("wx");
                                                }
                                            });
                                            if (listFiles != null) {
                                                for (File ʻ : listFiles) {
                                                    b.ʻ(ʻ);
                                                }
                                            }
                                        } else if (this.ˆ.ʻ(loadPackageParam2, classLoader, ʻ, ʻ2, contentValues)) {
                                            XposedBridge.log(loadPackageParam2.processName + " loading " + "com.fkzhang.wechatxposed");
                                            this.ˆ.ʻ();
                                            d ʼ = this.ˆ.ʼ();
                                            if (ʼ != null && !TextUtils.isEmpty(this.ˆ.ʽ())) {
                                                if (!ʼ.ʻ(new File(this.ˆ.ʽ(), "C"))) {
                                                    this.ˆ.ʼ(this.ˆ.ʽ());
                                                } else if (!ʼ.ʻ(new File(this.ˆ.ʽ(), "L"))) {
                                                    this.ˆ.ʼ(this.ˆ.ʽ());
                                                }
                                            }
                                        }
                                    } catch (Throwable th) {
                                        XposedBridge.log(th);
                                    }
                                }
                            });
                        }
                    }
                } catch (Throwable th2) {
                    XposedBridge.log(th2);
                }
            }
        }
    }

    protected abstract boolean ʻ(LoadPackageParam loadPackageParam, ClassLoader classLoader, Context context, Context context2, ContentValues contentValues);

    protected abstract boolean ʻ(String str);

    protected abstract d ʼ();

    protected abstract String ʽ();
}