package com.example.zk.myapplication.com.kingroot.sdk.root;

import android.util.Log;

final class d extends Thread {
    private final /* synthetic */ Process a;

    d(Process process) {
        this.a = process;
    }

    public final void run() {
        try {
            Log.e("wodelog","ExeRootSolution process exit: " + this.a.waitFor());
        } catch (Throwable e) {
            Log.e("wodelog","ExeRootSolution waitFor throw e", e);
        }
    }
}