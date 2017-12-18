package com.example.zk.myapplication.com.kingroot.sdk.root;/*
package com.kingroot.sdk.root;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import com.kingroot.sdk.util.d;
import h.e;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class r {
    public static String a = "";
    public static volatile boolean b = false;
    private BufferedWriter c;
    private SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private File e;

    public r() {
        b = true;
        File file = new File(e.a.b, "selog");
        if (!file.exists()) {
            file.mkdir();
        }
        File file2 = new File(file, "kr_93_" + System.currentTimeMillis() + "_" + Build.BRAND + "_" + Build.MODEL + ".log");
        try {
            this.c = new BufferedWriter(new FileWriter(file2));
            a("d", a);
        } catch (IOException e) {
            d.a(this.c);
            this.c = null;
        }
        this.e = file2;
    }

    public final void a(String str) {
        a("d", str);
    }

    public final void a(String str, String str2) {
        if (this.c != null) {
            try {
                this.c.append(this.d.format(new Date())).append(" ").append("[" + str + "]").append(" ").append(str2).append("\n");
                this.c.flush();
            } catch (Exception e) {
                d.a(this.c);
                this.c = null;
            }
        }
    }

    private void c() {
        d.a(this.c);
        b = false;
    }

    static */
/* synthetic *//*
 File[] b() {
        File file = new File(e.a.b, "selog");
        return !file.exists() ? new File[0] : file.listFiles(new s());
    }

    public static void a(Context context, Handler handler) {
        handler.post(new t(context));
    }

    public final void b(Context context, Handler handler) {
        c();
        a(context, handler);
    }

    public final void a() {
        c();
        if (this.e != null && this.e.exists()) {
            this.e.delete();
        }
    }
}*/
