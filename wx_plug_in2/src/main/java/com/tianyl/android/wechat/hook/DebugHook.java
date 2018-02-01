package com.tianyl.android.wechat.hook;

import java.lang.reflect.Member;

import com.tianyl.android.wechat.util.WechatUtil;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

public class DebugHook extends XC_MethodHook{

	@Override
	protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
		XposedBridge.log("DebugHooker start:" + WechatUtil.getTime());
		Object obj = param.thisObject;
		XposedBridge.log("class:" + obj.getClass().getName());
		Member member = param.method;
		XposedBridge.log("method:" + member.getName());
		XposedBridge.log("args:" + param.args);
		XposedBridge.log("DebugHooker end:" + WechatUtil.getTime());
	}
	
}
