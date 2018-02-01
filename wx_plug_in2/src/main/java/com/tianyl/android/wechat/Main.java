package com.tianyl.android.wechat;

import com.tianyl.android.wechat.hook.MessageDeleteHook;
import com.tianyl.android.wechat.hook.MessageInsertHook;
import com.tianyl.android.wechat.hook.RevokeHook;
import com.tianyl.android.wechat.util.WechatUtil;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.PathClassLoader;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Main implements IXposedHookLoadPackage{

	private static final String WechatPackageName = "com.tencent.mm";
	
	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		String pkgName = lpparam.packageName;
		if(!pkgName.equals(WechatPackageName)){
			return;
		}
//		handleFromJar(lpparam);
		handle(lpparam);
		debug(lpparam);
	}



	public void handleFromJar(LoadPackageParam lpparam)throws Throwable {
		String curPkg = Main.class.getPackage().getName();
		String filePath = String.format("/data/app/%s-%s.apk", curPkg, 1);
		if (!new File(filePath).exists()){
            filePath = String.format("/data/app/%s-%s.apk", curPkg, 2);
            if (!new File(filePath).exists()){
                XposedBridge.log("Error:在/data/app找不到APK文件:" + filePath);
                return;
            }
        }
		final PathClassLoader pathClassLoader = new PathClassLoader(filePath, ClassLoader.getSystemClassLoader());
        final Class<?> aClass = Class.forName(curPkg + "." + Main.class.getSimpleName(), true, pathClassLoader);
//        final Method aClassMethod = aClass.getMethod("debug", XC_LoadPackage.LoadPackageParam.class);
        final Method aClassMethod = aClass.getMethod("handle", XC_LoadPackage.LoadPackageParam.class);
        aClassMethod.invoke(aClass.newInstance(), lpparam);		
	}

	public void handle(XC_LoadPackage.LoadPackageParam lp){
		XposedBridge.log("打开微信...:" + WechatUtil.getTime());

        //添加消息
		String className = "com.tencent.wcdb.database.SQLiteSession";
		XposedHelpers.findAndHookMethod(className, lp.classLoader, "executeForLastInsertedRowId",
				String.class, Object[].class, Integer.TYPE, "com.tencent.wcdb.support.CancellationSignal",
                new MessageInsertHook(lp));
        //删除消息
        XposedHelpers.findAndHookMethod(className,lp.classLoader,"executeForChangedRowCount",
                String.class,Object[].class, Integer.TYPE, "com.tencent.wcdb.support.CancellationSignal",
                new MessageDeleteHook(lp));
	}

    public void debug(XC_LoadPackage.LoadPackageParam lp){
        XposedBridge.log("打开微信...debug revoke:" + WechatUtil.getTime());
//        String className = "com.tencent.mmdb.database.SQLiteSession";
//        SqlExecuteHook hooker = new SqlExecuteHook(lp);
//		XposedHelpers.findAndHookMethod(className, lp.classLoader, "execute",
//				String.class, Object[].class, Integer.TYPE, "com.tencent.mmdb.support.CancellationSignal", hooker);
//		XposedHelpers.findAndHookMethod(className, lp.classLoader, "executeForChangedRowCount",
//				String.class, Object[].class, Integer.TYPE, "com.tencent.mmdb.support.CancellationSignal", hooker);
//		XposedHelpers.findAndHookMethod(className, lp.classLoader, "executeForCursorWindow",
//				String.class, Object[].class, Integer.TYPE, Integer.TYPE, Integer.TYPE, "com.tencent.mm.m.a.b", "com.tencent.mm.m.a.c", hooker);
//		XposedHelpers.findAndHookMethod(className, lp.classLoader, "executeForCursorWindow",
//				String.class, Object[].class, "com.tencent.mmdb.CursorWindow", Integer.TYPE, Integer.TYPE, Boolean.TYPE, Integer.TYPE, "com.tencent.mmdb.support.CancellationSignal",
//				hooker);
//		XposedHelpers.findAndHookMethod(className, lp.classLoader, "executeForLastInsertedRowId",
//				String.class, Object[].class, Integer.TYPE, "com.tencent.mmdb.support.CancellationSignal", hooker);
//		XposedHelpers.findAndHookMethod(className, lp.classLoader, "executeForLong",
//				String.class, Object[].class, Integer.TYPE, "com.tencent.mmdb.support.CancellationSignal", hooker);
//		XposedHelpers.findAndHookMethod(className, lp.classLoader, "executeForString",
//				String.class, Object[].class, Integer.TYPE, "com.tencent.mmdb.support.CancellationSignal", hooker);

        RevokeHook hook = new RevokeHook(lp);
        hook.hook();

//		XposedHelpers.findAndHookMethod("com.tencent.mm.booter.NotifyReceiver", lp.classLoader, "onReceive", Context.class,Intent.class,new XC_MethodHook() {
//
//			@Override
//			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//				Context context = (Context)param.args[0];
//				Intent intent = (Intent)param.args[1];
//				Bundle bundle = intent.getExtras();
//				if(bundle != null){
//					XposedBridge.log("接收消息，时间:" + WechatUtil.getTime());
//					XposedBridge.log("消息[notify_uin]:" + bundle.get("notify_uin"));
//					XposedBridge.log("消息[notify_respType]:" + bundle.getInt("notify_respType"));//268369921
////					XposedBridge.log("消息[notify_respBuf]:" + new String(bundle.getByteArray("notify_respBuf")));
//				}
//			}
//
//		});
    }
}
