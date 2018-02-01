package com.fkzhang.wechatxposed;

import com.fkzhang.xposed.hook.b;
import com.fkzhang.xposed.hook.c;
import com.fkzhang.xposed.hook.d;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import java.util.HashMap;

public class XposedInit implements IXposedHookLoadPackage {
    private static HashMap<String, d> ʻ = new HashMap();

    public void handleLoadPackage(LoadPackageParam loadPackageParam) {
        try {
            String str = loadPackageParam.packageName;
            b.ʻ(loadPackageParam);
            c.ʻ(loadPackageParam);
            if (!ʻ.containsKey("wechat")) {
                ʻ.put("wechat", new com.fkzhang.wechatxposed.b());
            }
            for (d ʻ : ʻ.values()) {
                ʻ.ʻ(loadPackageParam, str);
            }
        } catch (Throwable th) {
            XposedBridge.log(th);
        }
    }
}
