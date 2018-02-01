package com.tianyl.android.wechat.hook;

import java.util.Map;

import com.tianyl.android.wechat.util.WechatUtil;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class SqlExecuteHook extends XC_MethodHook{

	private LoadPackageParam loadPackageParam;
	
	public SqlExecuteHook(LoadPackageParam lpp){
		this.loadPackageParam = lpp;
	}
	
	@Override
	protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
		XposedBridge.log("----------------start-----------------method:" + param.method.getName() + ",time:" + WechatUtil.getTime());
		Object sqlObj = param.args[0];
		Object[] objs = (Object[])param.args[1];
		if(sqlObj == null){
			XposedBridge.log("sql为空");
		}
		if(objs == null){
			XposedBridge.log("参数为空");
		}
		if(sqlObj != null){
			String sql = sqlObj.toString();
			XposedBridge.log("execute sql:" + sql);
		}
		if(objs != null){
			int index = 0;
			for(Object obj:objs){
				XposedBridge.log("args[" + index + "]:" + WechatUtil.getType(obj));
				String val = WechatUtil.getStr(obj);
				XposedBridge.log("args[" + index + "]:" + val);
				index++;
			}
		}
//		XposedBridge.log("---------------stack------------------");
//		XposedBridge.log(new Exception());
		XposedBridge.log("-----------------end------------------");
	}
	
}
