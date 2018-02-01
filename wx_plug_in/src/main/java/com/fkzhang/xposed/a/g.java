package com.fkzhang.xposed.a;

import android.os.Environment;
import java.io.File;

public class g {
    private static boolean ʻ;
    private static boolean ʼ;

    public static File ʻ(String str) {
        return ʻ() ? new File(Environment.getExternalStorageDirectory(), str) : null;
    }

    public static boolean ʻ() {
        ʼ();
        return ʼ;
    }

    private static void ʼ() {
        String externalStorageState = Environment.getExternalStorageState();
        if (externalStorageState.equals("mounted")) {
            ʼ = true;
            ʻ = true;
        } else if (externalStorageState.equals("mounted") || externalStorageState.equals("mounted_ro")) {
            ʻ = true;
            ʼ = false;
        } else {
            ʼ = false;
            ʻ = false;
        }
    }
}
