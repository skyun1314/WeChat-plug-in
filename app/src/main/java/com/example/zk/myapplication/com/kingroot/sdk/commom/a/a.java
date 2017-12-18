package com.example.zk.myapplication.com.kingroot.sdk.commom.a;/*
package com.kingroot.sdk.commom.a;

import android.os.Environment;
import android.util.Log;
import com.kingroot.sdk.root.r;
import com.kingroot.sdk.util.d;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class a {
    private static BufferedWriter a;
    private static SimpleDateFormat b = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static boolean f_a = true;
    public static void a(String str) {
        if (f_a) {
            Log.i("kingroot-sdk", str);
            a("i", str);
        }
    }

    public static void b(String str) {
        if (f_a) {
            Log.v("kingroot-sdk", str);
            a("v", str);
        }
    }

    public static void c(String str) {
        if (f_a) {
            Log.d("kingroot-sdk", str);
            a("d", str);
        }
    }

    public static void a(r rVar, String str) {
        rVar.a("d", str);
        c(str);
    }

    public static void d(String str) {
        if (f_a) {
            Log.w("kingroot-sdk", str);
            a("w", str);
        }
    }

    public static void b(r rVar, String str) {
        rVar.a("w", str);
        d(str);
    }

    public static void e(String str) {
        Log.e("kingroot-sdk", str);
        if (f_a) {
            a("e", str);
        }
    }

    public static void c(r rVar, String str) {
        rVar.a("e", str);
        e(str);
    }

    public static void a(String str, Throwable th) {
        Log.e("kingroot-sdk", str, th);
        if (f_a) {
            a("e", new StringBuilder(String.valueOf(str)).append("\n").append(Log.getStackTraceString(th)).toString());
        }
    }

    private static void a(String str, String str2) {
        if (a == null) {
            if (Environment.getExternalStorageState().equals("mounted")) {
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                if (externalStorageDirectory.exists()) {
                    try {
                        a = new BufferedWriter(new FileWriter(new File(externalStorageDirectory, "krsdk_debug.txt")));
                    } catch (IOException e) {
                        d.a(a);
                        a = null;
                    }
                } else {
                    return;
                }
            }
            return;
        }
        if (a != null) {
            try {
                a.append(b.format(new Date())).append(" ").append("[" + str + "]").append(" ").append(str2).append("\n");
                a.flush();
            } catch (Exception e2) {
                d.a(a);
                a = null;
            }
        }
    }
}*/
