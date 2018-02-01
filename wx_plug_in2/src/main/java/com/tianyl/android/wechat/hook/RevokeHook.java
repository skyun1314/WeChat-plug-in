package com.tianyl.android.wechat.hook;

import android.database.Cursor;

import com.tianyl.android.wechat.Main;
import com.tianyl.android.wechat.util.StringUtil;
import com.tianyl.android.wechat.util.WechatDatabase;

import java.util.Map;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by tianyl on 17/2/15.
 */

public class RevokeHook {

    private XC_LoadPackage.LoadPackageParam loadPackageParam;

    private WechatDatabase wechatDatabase;

    public RevokeHook(XC_LoadPackage.LoadPackageParam lpp){
        this.loadPackageParam = lpp;
    }

    public void hook(){
        try {
            hookDatabase();
        }catch (Throwable e){
            XposedBridge.log(e);
        }

        try {
      //      hookRevoke();
        } catch (Throwable e){
            XposedBridge.log(e);
        }
    }

    private void hookDatabase() {

        XposedBridge.hookAllConstructors(ClassLoader.class,new XC_MethodHook(){
            public void beforeHookedMethod(MethodHookParam param){
                ClassLoader cl=(ClassLoader)param.args[0];//获取其它dex的类加载器
                if(cl==null)return;

                String t="com.tencent.mm.storage.t";
                String h="com.tencent.mm.bw.h";
            Class h_class=   XposedHelpers.findClass(h,cl);


                XposedHelpers.findAndHookConstructor(t, cl,h_class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        wechatDatabase = new WechatDatabase(param.args[0]);
                    }
                });

            }
        });


    }

    private void hookRevoke() {
        XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.bg",loadPackageParam.classLoader,
                "q",String.class,String.class,
                new XC_MethodHook(){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Map<String,String> map = (Map<String,String>)param.getResult();
                        if (map == null) {
                            return;
                        }
                        String type = map.get(".sysmsg.$type");
                        if (StringUtil.isBlank(type) || !"revokemsg".equals(type)) {
                            return;
                        }
                        String replaceMsg = map.get(".sysmsg.revokemsg.replacemsg");
                        if (replaceMsg != null && replaceMsg.trim().equals("你撤回了一条消息")){
                            return;
                        }
                        //阻止撤销
                        map.remove(".sysmsg.$type");
                        param.setResult(map);
                        //插入提示信息
                        if (wechatDatabase == null) {
                            XposedBridge.log("hook database error!!");
                            return;
                        }
                        String msgId = map.get(".sysmsg.revokemsg.newmsgid");
                        Cursor cursor = wechatDatabase.findMessageByMsgsvrid(msgId);
                        if (cursor == null || !cursor.moveToFirst()){
                            XposedBridge.log("select message error msgId:" + msgId);
                            return;
                        }
                        Long createTime = cursor.getLong(cursor.getColumnIndex("createTime")) + 1;
                        String talker = map.get(".sysmsg.revokemsg.session");
                        int talkerId = cursor.getInt(cursor.getColumnIndex("talkerId"));
                        cursor.close();
                        wechatDatabase.insertSysMessage(talker,talkerId,"已阻止试图撤回的消息",createTime);

                    }
                });
    }

}
