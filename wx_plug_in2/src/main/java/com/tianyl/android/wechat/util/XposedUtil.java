package com.tianyl.android.wechat.util;

import android.content.Context;

import de.robv.android.xposed.XposedHelpers;

/**
 * Created by tianyl on 17/2/15.
 */

public class XposedUtil {

    public static Context getContext(){
        Object thread = XposedHelpers.callStaticMethod(XposedHelpers.findClass("android.app.ActivityThread",null),"currentActivityThread");
        if (thread == null) {
            return null;
        }
        Context context = (Context) XposedHelpers.callMethod(thread,"getSystemContext");
        if (context == null) {
            return null;
        }
        return context;
    }

}
