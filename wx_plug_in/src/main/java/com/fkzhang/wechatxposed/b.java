package com.fkzhang.wechatxposed;

import android.content.ContentValues;
import android.content.Context;
import com.fkzhang.xposed.a.c;
import com.fkzhang.xposed.a.d;
import com.fkzhang.xposed.hook.WxCoreLoader;
import com.fkzhang.xposed.hook.e;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class b extends e {
    private WxCoreLoader ʾ;

    protected void ʻ() {
        this.ʾ.ʾ();
    }

    protected boolean ʻ(LoadPackageParam loadPackageParam, ClassLoader classLoader, Context context, Context context2, ContentValues contentValues) {
        c cVar = new c(contentValues);
        cVar.ʻ("p");
        this.ʾ = new WxCoreLoader(loadPackageParam, classLoader, context, context2, cVar.ʻ(), 1);
        return this.ʾ.ʻ();
    }

    protected boolean ʻ(String str) {
        return ((str.contains("com.tencen") && str.contains("mm")) || ʻ.contains(str)) ? false : true;
    }

    protected d ʼ() {
        return this.ʾ.ʼ();
    }

    protected String ʽ() {
        return this.ʾ.ʽ();
    }
}
