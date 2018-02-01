package com.tianyl.android.wechat.hook;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import com.tianyl.android.wechat.util.FileUtil;
import com.tianyl.android.wechat.util.WechatUtil;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class MessageInsertHook extends XC_MethodHook{

	private LoadPackageParam loadPackageParam;
	
	public MessageInsertHook(LoadPackageParam lpp){
		this.loadPackageParam = lpp;
	}
	
	@Override
	protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
		Object sqlObj = param.args[0];
		Object[] objs = (Object[])param.args[1];
		if(sqlObj == null){
			XposedBridge.log("sql为空");
			return;
		}
		if(!sqlObj.toString().contains("message")){
			return;
		}
		if(objs == null){
			XposedBridge.log("参数为空");
			return;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("sql:" + sqlObj.toString()+"\r\n");
		String semiXML = null;
		int index = 0;
		for(Object obj:objs){
			String val = WechatUtil.getStr(obj);
			sb.append("args[" + index + "]:" + WechatUtil.getType(obj)+"\r\n");
			sb.append("args[" + index + "]:" + val + "\r\n");
			index++;
			if(val.startsWith("~SEMI_XML~")){
				semiXML = val;
			}
		}
		if(semiXML != null){
			@SuppressWarnings("unchecked")
			Map<String, String> contentMap = (Map<String, String>)XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.tencent.mm.sdk.platformtools.ax",loadPackageParam.classLoader), "Uk", semiXML);
			if(contentMap != null){
				JSONObject json = new JSONObject(contentMap);
				String jsonStr = json.toString();
				String uuid = UUID.randomUUID().toString();
				FileUtil.saveStringToFile(jsonStr, new File(FileUtil.getBathPath() + "json/" + uuid + ".json"));

				//FileUtil.saveStringToFile(jsonStr, new File(FileUtil.getBathPath() + "json-bak/" + uuid + ".json"));
				//FileUtil.saveStringToFile(sb.toString(), new File(FileUtil.getBathPath() + "json-bak/" + uuid + ".txt"));
			}else{
				XposedBridge.log("content map is null");
			}
		}
	}
}
