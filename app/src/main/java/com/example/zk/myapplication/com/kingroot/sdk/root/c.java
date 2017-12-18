package com.example.zk.myapplication.com.kingroot.sdk.root;

import android.content.Context;
import android.os.Handler;


import java.io.File;

//import com.kingroot.sdk.wupsession.b;


public final class c  {
    private Context a;
    private String b;
    private Handler c;
    private File d;
    private String e;


    File file;//=new File( MainActivity.app_krsdk);

    private String f = new StringBuilder(String.valueOf(file.getAbsolutePath())).append(File.separator).append("mm-direct-root.sh").toString();
    private String g = new StringBuilder(String.valueOf(file.getAbsolutePath())).append(File.separator).append("kd").toString();
    private Process h;

    public c(Context context, File file, String str, Handler handler) {
        this.a = context;
        this.d = file;
        this.b = str;
        this.c = handler;
        this.e = new File(file, "krmain").getAbsolutePath();
    }

/*    public final boolean a() {
        return true;
    }*/

    public  final int a() {
        boolean z = false;
        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();
        long nanoTime = System.nanoTime();



        try {
            int parseInt;
            boolean z2;

            CMDUtil.runCMD(this.e+ " -a "+ this.f+ " -c "+ this.d.getAbsolutePath(),this.d.getAbsolutePath());

           // this.h = new ProcessBuilder(new String[]{this.e, "-a", this.f, "-c", this.d.getAbsolutePath()}).redirectErrorStream(true).directory(this.d).start();
          //new d(this.h).start();


           /*   j.a(this.a, "EMID_KRSDK_EXReport_Info", this.b, "93", "", new StringBuilder(String.valueOf(u.d)).toString(), "0", "1");
            rVar.a("onRoot() start sid = " + this.b);
            String str = this.b + ".stdout : ";
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.h.getInputStream()));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    String trim = readLine.trim();
                    a.c(new StringBuilder(String.valueOf(str)).append(trim).toString());
                    rVar.a(trim);
                    if (trim.startsWith("[et] KRS|FT PARAMS:")) {
                        readLine = trim.substring(19).trim();
                        j.a(this.a, "EMID_KRSDK_EXReport_Info", this.b, "93", readLine, new StringBuilder(String.valueOf(u.d)).toString(), "0", "1");
                    }
                    if (trim.startsWith("krerrcode:")) {
                        parseInt = Integer.parseInt(trim.substring(10).trim());
                        z2 = true;
                        a.e("catch ::: errCode = " + parseInt);
                        com.kingroot.sdk.b.a.a(this.a).a("CATCH_SOLUTION_RESULT", parseInt == 0 ? 0 : 1, 0, trim, this.c, this.b);
                        b.a(this.a, this.c);
                        if (parseInt != 0) {
                            break;
                        }
                        i = parseInt;
                        z = true;
                    } else if (trim.startsWith("krerrmsg:")) {
                        stringBuilder.append(trim.substring(9).trim());
                    }
                    if (z) {
                        break;
                    }
                } else {
                    break;
                }
            }
            parseInt = i;
            z2 = z;
            rVar.a("onRoot() end sid = " + this.b + ", catchResult = " + z2 + ", errcode = " + parseInt + ", childDuingTime = " + ((System.nanoTime() - nanoTime) / 1000000));
            a.c("执行完成1！ catchResult = " + z2);
            String[] c = j.c(this.a, "EMID_KRSDK_EXReport_Info");
            j.a(this.a, "EMID_KRSDK_EXReport_Info");
            if (c.length >= 5) {
                com.kingroot.sdk.b.a a = com.kingroot.sdk.b.a.a(this.a);
                int i2 = (z2 && parseInt == 0) ? 0 : 1;
                a.a(200039, i2, 0, "catchResult=" + z2 + ", errCode=" + parseInt, this.c, c[0], c[1], c[2], c[3], Long.valueOf(nanoTime), Integer.valueOf(1));
                b.a(this.a, this.c);
            }
            if (!z2) {
                com.kingroot.sdk.util.e.a(7010, "Exe fail, EOF");
                rVar.a("onRoot() not catchResult : " + com.kingroot.sdk.util.e.c());
            } else if (parseInt == 0) {
                return 0;
            } else {
                com.kingroot.sdk.util.e.a(parseInt, stringBuilder.toString());
            }
            a.c("执行完成！");*/
            return 1;
        } catch (Throwable th) {
            /*th.printStackTrace();
            com.kingroot.sdk.util.e.a(7011, stringBuilder.toString(), th);
            rVar.a("onRoot() exception : " + com.kingroot.sdk.util.e.c());*/
            return 1;
        }
    }

 /*   public final a b() {
        int c;
        int i;
        a a = h.a(this.g, 50);
        if (a != null) {
            c = a.c();
        } else {
            c = 0;
        }
        com.kingroot.sdk.b.a a2 = com.kingroot.sdk.b.a.a(this.a);
        String str = "RETRY_KD_SHELL";
        if (a != null) {
            i = 0;
        } else {
            i = 1;
        }
        a2.a(str, i, com.kingroot.sdk.util.e.a(), com.kingroot.sdk.util.e.c(), this.c, this.b, Integer.valueOf(c));
        return a;
    }

    public final void c() {
        if (this.h != null) {
            try {
                this.h.destroy();
            } catch (Throwable th) {
                a.a("ExeRootSolution.destroy() throw e", th);
            }
        }
    }*/
}