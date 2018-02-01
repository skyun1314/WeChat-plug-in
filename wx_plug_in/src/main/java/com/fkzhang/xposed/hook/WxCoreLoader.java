package com.fkzhang.xposed.hook;

import android.app.AndroidAppHelper;
import android.content.ContentValues;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.fkzhang.wechatxposed.a;
import com.fkzhang.xposed.a.b;
import com.fkzhang.xposed.a.d;

import java.io.File;
import java.io.FilenameFilter;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class WxCoreLoader {
    private final d ʻ;
    private boolean ʼ;
    private XC_LoadPackage.LoadPackageParam ʽ;
    private ClassLoader ʾ;
    private final Context ʿ;
    private final Context ˆ;
    private ContentValues ˈ;
    private String ˉ;

    public WxCoreLoader(XC_LoadPackage.LoadPackageParam arg5, ClassLoader arg6, Context arg7, Context arg8, ContentValues arg9, int arg10) {
        super();
        this.ʽ = arg5;
        this.ʾ = arg6;
        this.ʿ = arg7;
        this.ˆ = arg8;
        this.ˈ = arg9;
        this.ʻ = new d(arg8);
        this.ʻ.ʼ(a.ᐧ);
        File v0 = new File(arg8.getApplicationInfo().nativeLibraryDir, a.ʿ);
        if(!v0.exists()) {
            XposedBridge.log(a.ٴ + ": loader does not exist");
            return;
        }

        try {
            System.load(v0.getAbsolutePath());
            String v0_2 = arg9.getAsString(a.ˈˈ);
            String v1 = arg9.getAsString(a.ᐧᐧ);
            this.ʻ(v0_2);
            if(TextUtils.isEmpty(this.ʽ())) {
                return;
            }

            if(!v1.contains(a.ᴵᴵ) && !this.ʽ().contains(((CharSequence)v1))) {
                return;
            }

            if(!new File(this.ʽ()).exists()) {
                return;
            }

            this.ʿ();
            this.ʻ(0, new Object[]{arg8, arg7, Integer.valueOf(arg10)});
            this.ʼ = true;
        }
        catch(Throwable v0_1) {
            XposedBridge.log(v0_1);
        }
    }

    private static native  Object CallMethod(int i, Object[] objArr);


    private void ʻ(String str) {
        this.ˉ = (String) this.ʻ(2,new Object[]{this.ʿ, AndroidAppHelper.currentApplication(), str} );
    }


    private Object ʻ(int arg2, Object[] arg3) {
        Object v0_1;
        try {
            v0_1 = WxCoreLoader.CallMethod(arg2, arg3);
        }
        catch(Throwable v0) {
            XposedBridge.log(v0);
            v0_1 = null;
        }

        return v0_1;
    }

    public boolean ʻ() {
        return this.ʼ;
    }

    public d ʼ() {
        return this.ʻ;
    }

    public String ʽ() {
        return this.ˉ;
    }

    public void ʾ() {
        int v1 = 1;
        if(this.ʼ) {
            try {
                if(Build.VERSION.SDK_INT == 19) {
                    v1 = 0;
                }

                Object v0_1 = this.ʻ(1, new Object[]{Integer.valueOf(v1), this.ʾ, a.ˆˆ});
                if(v0_1 == null) {
                    return;
                }

                if(v1 == 0) {
                    Class v0_2 = new com.fkzhang.xposed.hook.a(this.ʿ.getCacheDir().getAbsolutePath(), this.ʾ, ((Integer)v0_1).intValue()).loadClass(a.ˆˆ);
                }
                else {
                }

                if(v0_1 == null) {
                    return;
                }

                if(!((Boolean)XposedHelpers.callMethod(XposedHelpers.newInstance(((Class)v0_1), new Object[]{this.ʿ, this.ˆ, this.ˈ, this.ˉ}), "hook", new Object[]{this.ʽ.classLoader})).booleanValue()) {
                    return;
                }

                XposedBridge.log("com.fkzhang.wechatxposed (1.18): wechat version " + this.ˈ.getAsString(a.ˊˊ) + "(" + this.ˈ.getAsInteger(a.ˋˋ).intValue() + ")");
            }
            catch(Throwable v0) {
                XposedBridge.log(v0);
            }
        }
    }

    private void ʿ() {
        String v0 = this.ʽ();
        if(!TextUtils.isEmpty(((CharSequence)v0))) {
            File[] v1 = new File(v0).getParentFile().getParentFile().listFiles(new FilenameFilter() {
                public boolean accept(File arg2, String arg3) {
                    return arg3.contains(a.ʾʾ);
                }
            });
            if(v1 != null && v1.length != 0) {
                int v2 = v1.length;
                int v0_1;
                for(v0_1 = 0; v0_1 < v2; ++v0_1) {
                    File v3 = v1[v0_1];
                    if(v3.exists()) {
                        b.ʻ(v3);
                    }
                }
            }
        }
    }
}

