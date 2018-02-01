package com.fkzhang.xposed.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import de.robv.android.xposed.XSharedPreferences;

public class f {
    private SharedPreferences ʻ = null;
    private XSharedPreferences ʼ = null;

    public f(Context context, String str) {
        try {
            this.ʻ = context.getSharedPreferences(str + "_preferences", 1);
        } catch (Throwable th) {
            this.ʻ = context.getSharedPreferences(str + "_preferences", 0);
        }
    }

    public f(String str) {
        try {
            this.ʼ = new XSharedPreferences(str);
            this.ʼ.makeWorldReadable();
            ʻ();
        } catch (Throwable th) {
        }
    }

    public String ʻ(String str, String str2) {
        return TextUtils.isEmpty(str) ? str2 : this.ʻ != null ? this.ʻ.getString(str, str2) : this.ʼ != null ? this.ʼ.getString(str, str2) : str2;
    }

    public void ʻ() {
        if (this.ʼ != null) {
            this.ʼ.reload();
        }
    }

    public boolean ʻ(String str, boolean z) {
        return TextUtils.isEmpty(str) ? z : this.ʻ != null ? this.ʻ.getBoolean(str, z) : this.ʼ != null ? this.ʼ.getBoolean(str, z) : z;
    }

    public void ʼ(String str, String str2) {
        Editor editor = null;
        if (this.ʻ != null) {
            editor = this.ʻ.edit();
        } else if (this.ʼ != null) {
            editor = this.ʼ.edit();
        }
        if (editor != null) {
            editor.putString(str, str2);
            editor.commit();
        }
    }

    public void ʼ(String str, boolean z) {
        Editor editor = null;
        if (this.ʻ != null) {
            editor = this.ʻ.edit();
        } else if (this.ʼ != null) {
            editor = this.ʼ.edit();
        }
        if (editor != null) {
            editor.putBoolean(str, z);
            editor.commit();
        }
    }
}
