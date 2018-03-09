package com.android.wechat;

import android.util.Log;

import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by zk on 2018/3/8.
 */

public class Main implements IXposedHookLoadPackage {
    private static final String WechatPackageName = "com.tencent.mm";
    Class aClass;
    ;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        String pkgName = loadPackageParam.packageName;
        if (!pkgName.equals(WechatPackageName)) {
            return;
        }


        ClassLoader cl = loadPackageParam.classLoader;


        XposedBridge.hookAllConstructors(ClassLoader.class, new XC_MethodHook() {
            public void beforeHookedMethod(MethodHookParam param) {
                XposedBridge.log("我进入了hookAllConstructors");
                ClassLoader cl = (ClassLoader) param.args[0];//获取其它dex的类加载器
                if (cl == null) {
                    XposedBridge.log("ClassLoader为空");
                } else {
                    XposedBridge.log("ClassLoader有之");

                    aClass = XposedHelpers.findClass("com.tencent.mm.ad.d$a", cl);
                    XposedBridge.log("aClass:" + aClass.getName());


                }
                ;


            }
        });

       // if (aClass != null) {
            XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.bi", cl, "r",
                    String.class, String.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);

                            Log.e("wodelog", "执行到我了");
                            XposedBridge.log("执行到我了");
                            Map     result= (Map) param.getResult();




                            if(result==null)return;
                            Log.e("wodelog","result:"+result.toString());
                            if(result.get(".sysmsg.$type")==null)return;
                            if (result.get(".sysmsg.$type") .equals("revokemsg") ) {
                                String msgTag = ".sysmsg.revokemsg.replacemsg";
                                String replacemsg = (String) result.get(msgTag);
                                String user=replacemsg.substring(0,replacemsg.indexOf(" 撤回了一条消息"));
                                result.put(msgTag,user+" 旺旺");

                            }


                        }
                    });
       // }


    }
}
