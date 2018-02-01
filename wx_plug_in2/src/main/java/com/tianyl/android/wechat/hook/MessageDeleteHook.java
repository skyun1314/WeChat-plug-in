package com.tianyl.android.wechat.hook;

import com.tianyl.android.wechat.util.FileUtil;

import java.io.File;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by tianyl on 17/2/5.
 */

public class MessageDeleteHook extends XC_MethodHook {

    private XC_LoadPackage.LoadPackageParam loadPackageParam;

    public MessageDeleteHook(XC_LoadPackage.LoadPackageParam lpp){
        this.loadPackageParam = lpp;
    }

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        Object sqlObj = param.args[0];
        if(sqlObj == null){
            XposedBridge.log("sql为空");
            return;
        }
        String sql = sqlObj.toString();
        if(!sql.contains("message")){
            return;
        }
        if (sql.contains("DELETE FROM message")){
            FileUtil.appendStringToFile(sql, new File(FileUtil.getBathPath() + "sql/delete_message.sql"));
        }
    }
}
