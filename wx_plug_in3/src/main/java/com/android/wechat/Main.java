package com.android.wechat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;


/**
 * Created by zk on 2018/3/8.
 */

public class Main implements IXposedHookLoadPackage {
    //provided
    private static final String WechatPackageName = "com.tencent.mm";

    public static String wodetag = "wodelog";

    public static void show_msg(String message, ContentValues contentValues, String methead_name) {
        if (message.equals("message")) {
            Log.i(wodetag, "  ");
            Log.i(wodetag, methead_name + "  :start-----------------");
            for (Map.Entry<String, Object> item : contentValues.valueSet()) {
                Log.i(wodetag, "   ContentValues:" + item.getKey() + " -> " + item.getValue().toString());
            }
            Log.i(wodetag, methead_name + " :end-----------------");
        }
    }


    public static void hook_SQLiteDatabase(Class hookclass, final ClassLoader cl) {
        XposedHelpers.findAndHookMethod(hookclass, "updateWithOnConflict",
                String.class, ContentValues.class, String.class, String[].class, int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        ContentValues contentValues = (ContentValues) param.args[1];
                        String message = (String) param.args[0];
                        if (message == null || contentValues == null) {
                            return;
                        }

                        show_msg(message, contentValues, "updateWithOnConflict");
                        Object content = contentValues.get("content");
                        Object type = contentValues.get("type");



                        if (content!=null&&type!=null&&message.equals("message") && ((String)content ).contains(" 撤回了一条消息") && ((int) type) == 10000) {
                            param.args[0] = "";
                            Cursor cursor = (Cursor) XposedHelpers.callMethod(param.thisObject, "rawQuery", "select * from message where msgId=?", new String[]{(contentValues.get("msgId") + "")});
                            cursor.moveToNext();
                            Log.i(wodetag, "  ");
                            //    Log.i(wodetag, "cursor1  :start-----------------");
                            ContentValues contentValues1 = new ContentValues();
                            for (int i = 0; i < cursor.getColumnCount(); i++) {
                                String columnName = cursor.getColumnName(i);
                                Object haha;
                                if (columnName.equals("msgId") || columnName.equals("msgSvrId") || columnName.equals("type") || columnName.equals("status")
                                        || columnName.equals("isSend") || columnName.equals("isShowTimer") || columnName.equals("createTime") || columnName.equals("talkerId")
                                        || columnName.equals("bizChatId") || columnName.equals("msgSeq") || columnName.equals("flag")) {
                                    long string = cursor.getLong(i);
                                    if (columnName.equals("createTime")) {
                                        string += 1;
                                    }
                                    if (columnName.equals("type")) {
                                        string = 10000;
                                    }
                                    haha = string;
                                    contentValues1.put(columnName, string);
                                } else if (columnName.equals("lvbuffer")) {
                                    byte[] string = cursor.getBlob(i);
                                    haha = string;
                                    contentValues1.put(columnName, string);
                                } else {
                                    String string = cursor.getString(i);
                                    haha = string;
                                    if (string == null) {
                                        continue;
                                    }
                                    if (columnName.equals("content")) {
                                        string = ((String) contentValues.get("content"));
                                        // "🎾🎾🎾🎾🎾🎾阿甘" 撤回了一条消息//
                                        String revoke = "撤回了一条消息";
                                        String substring = string.substring(0, string.length() - revoke.length());

                                        string = substring + "妄图撤回一条消息";
                                    }
                                    contentValues1.put(columnName, string);
                                }

                                //   Log.e(wodetag, "   ContentValues:" + columnName + " -> " + haha);
                            }

                            //  Log.i(wodetag, "cursor1  :end-----------------");
                            Log.e(wodetag, "   ");
                            Log.i(wodetag, "cursor2  :start-----------------");
                            for (Map.Entry<String, Object> item : contentValues1.valueSet()) {
                                Log.i(wodetag, "   拦截测回消息修改后的：ContentValues:" + item.getKey() + " -> " + item.getValue().toString());
                            }
                            Log.i(wodetag, "cursor2  :end-----------------");
                            XposedHelpers.callMethod(param.thisObject, "insertWithOnConflict", "message", "msgId", contentValues1, 0);

                        }
                    }
                });
        XposedHelpers.findAndHookMethod(hookclass, "insertWithOnConflict",
                String.class, String.class, ContentValues.class, int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        ContentValues contentValues = (ContentValues) param.args[2];
                        String message1 = (String) param.args[0];


                        show_msg(message1, contentValues, "insertWithOnConflict");
                        ((ContentValues) param.args[2]).remove("msgId");
                        if (message1.equals("message")) {
                            String string = (String) param.args[1];
                            int int1 = (int) param.args[3];
                            Log.e(wodetag, "查看 insertWithOnConflict 函数参数： message1" + message1 + "; string:" + string + "; int1:" + int1);

                            String reserved = (String) contentValues.get("reserved");
                            int type = (int) contentValues.get("type");
                            if (reserved != null && (type == 419430449)) {


                                handleTransfer(contentValues, cl);


                            } else if (reserved != null && (type == 436207665)) {

                                LuckyMoney(contentValues, cl);


                            }
                        }


                    }
                });


        // hook红包界面初始化“开”按钮的方法，在该方法完成后自动点击开按钮领取红包
        XposedHelpers.findAndHookMethod("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI", cl, "d", int.class, int.class,
                String.class, XposedHelpers.findClass("com.tencent.mm.ad.k", cl), new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                        if (kaihongbao == false) {
                            return;
                        }

                        Log.e(wodetag, "执行了LuckyMoneyReceiveUI d函数 参数");
                        Field buttonField = XposedHelpers.findField(param.thisObject.getClass(), "nxj");
                        final Button kaiButton = (Button) buttonField.get(param.thisObject);
                        boolean b = kaiButton.performClick();
                        Log.e(wodetag, "执行点击" + b);
                        // ((Activity)param.thisObject).finish();
                        kaihongbao = false;
                    }
                });
        // hook红包界面初始化“开”按钮的方法，在该方法完成后自动点击开按钮领取红包
        XposedHelpers.findAndHookMethod("com.tencent.mm.plugin.remittance.ui.RemittanceDetailUI", cl, "d", int.class, int.class,
                String.class, XposedHelpers.findClass("com.tencent.mm.ad.k", cl), new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                        if (kaizhuanzhang == false) {
                            return;
                        }

                        Log.e(wodetag, "执行了 RemittanceDetailUI d函数");
                        Field buttonField = XposedHelpers.findField(param.thisObject.getClass(), "pbz");
                        final Button kaiButton = (Button) buttonField.get(param.thisObject);
                        boolean b = kaiButton.performClick();
                        Log.e(wodetag, "执行点击" + b);
                        kaizhuanzhang = false;
                        // ((Activity)param.thisObject).finish();
                    }
                });

    }


    public static boolean kaihongbao = false;
    public static boolean kaizhuanzhang = false;


    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam mLpp) throws Throwable {

        SportEdit.Sprot(mLpp);

        String pkgName = mLpp.packageName;



        if (!pkgName.equals(WechatPackageName)) {
            return;
        }


        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                ClassLoader cl = ((Context) param.args[0]).getClassLoader();

                try {
                    Class<?> SQLiteDatabase = cl.loadClass("com.tencent.wcdb.database.SQLiteDatabase");
                    hook_SQLiteDatabase(SQLiteDatabase, cl);
                } catch (Exception e) {
                    Log.e(wodetag, "loadClass报错" + e.getMessage());
                }

                try {
                    Class<?> LauncherUI = cl.loadClass("com.tencent.mm.ui.LauncherUI");
                    hook_LauncherUI(LauncherUI, cl);
                    hook_LauncherUI(LauncherUI);
                } catch (Exception e) {
                    Log.e(wodetag, "loadClass报错" + e.getMessage());
                }

                sezi(cl);
                create_dialog(cl);
            }
        });


    }

    private static Activity launcherUiActivity = null;

    private void hook_LauncherUI(Class<?> launcherUI) {
        XposedHelpers.findAndHookMethod(launcherUI, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                launcherUiActivity = (Activity) param.thisObject;
            }
        });
    }

    private void hook_LauncherUI(Class<?> launcherUI, ClassLoader cl) {
        XposedHelpers.findAndHookMethod("com.tencent.mm.plugin.remittance.ui.RemittanceDetailUI", cl, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Activity activity = (Activity) param.thisObject;
                String sender_name = activity.getIntent().getStringExtra("sender_name");
                boolean is_sender = activity.getIntent().getBooleanExtra("is_sender", false);
                int appmsg_type = activity.getIntent().getIntExtra("appmsg_type", 0);
                String transfer_id = activity.getIntent().getStringExtra("transfer_id");
                String transaction_id = activity.getIntent().getStringExtra("transaction_id");
                int effective_date = activity.getIntent().getIntExtra("effective_date", 3);
                int total_fee = activity.getIntent().getIntExtra("total_fee", 0);
                String fee_type = activity.getIntent().getStringExtra("fee_type");


                Log.e(wodetag, "sender_name: " + sender_name + "\n");
                Log.e(wodetag, "is_sender: " + is_sender + "\n");
                Log.e(wodetag, "transfer_id: " + transfer_id + "\n");
                Log.e(wodetag, "appmsg_type: " + appmsg_type + "\n");
                Log.e(wodetag, "transaction_id: " + transaction_id + "\n");
                Log.e(wodetag, "effective_date: " + effective_date + "\n");
                Log.e(wodetag, "total_fee: " + total_fee + "\n");
                Log.e(wodetag, "fee_type: " + fee_type + "\n");

            }
        });
    }

    public static void sezi(ClassLoader cl){
        XposedHelpers.findAndHookMethod(XposedHelpers.findClass("com.tencent.mm.sdk.platformtools.bh", cl), "en", int.class, int.class, new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                int arg1 = (int) param.args[0];
             //   Log.e(wodetag,"hook 骰子参数"+arg1+";"+param.args[1]);
             //   Log.e(wodetag,"hook 骰子返回值:"+param.getResult());

                if (arg1==5){
                   // param.setResult(1);
                }else if(arg1==2){//石头1，剪子0，布  2
                    //param.setResult(1);
                }


            }


        });
    }


    public static void create_dialog(ClassLoader cl){
        XposedHelpers.findAndHookMethod(XposedHelpers.findClass("com.tencent.mm.view.SmileyGrid$1", cl), "onItemClick", AdapterView.class, View.class,int.class,long.class, new XC_MethodHook() {

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);

                AdapterView adapterView= (AdapterView) param.args[0];
                View view111= (View) param.args[1];

             ViewGroup view1= (ViewGroup) adapterView.getAdapter().getView((int)param.args[2],null,null);
                Log.e(wodetag,"create_dialog  获取view:"+view1.getClass().getName());
                for (int i = 0; i < view1.getChildCount(); i++) {
                    View child = view1.getChildAt(i);
                    Log.e(wodetag,"create_dialog  获取view:"+child.getClass().getName());
                    Log.e(wodetag,"create_dialog  获取view:"+child.getId());
                    Log.e(wodetag,"create_dialog  获取view:"+child.getContentDescription());
                }
             


                Log.e(wodetag,"create_dialog  参数"+param.args[2]+";"+param.args[3]);
                String shaizi[]={"1","2","3","4","5","6","随机"};
                String caiquan[]={"石头","剪刀","布","随机"};
                View view= (View) param.args[1];

                Log.e(wodetag,view.getClass().getName());
                ViewParent parent = view.getParent();
                while (parent.getParent()!=null){
                    Log.e(wodetag,parent.getClass().getName());
                    parent = parent.getParent();
                }
                view.getClass();

                CharSequence contentDescription = view.getContentDescription();

                Log.e(wodetag,"按钮描述"+contentDescription);
                String xueze[];
                if(contentDescription.equals("剪刀石头布")){
                    xueze=caiquan;
                }else if(contentDescription.equals("骰子")){
                    xueze=shaizi;
                }else{return;}

                if (launcherUiActivity != null) {
                    new AlertDialog.Builder(launcherUiActivity)
                            .setTitle("发送几点")
                            .setSingleChoiceItems(xueze, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.e(wodetag,"选择了："+i);
                                    dialogInterface.dismiss();
                                }
                            })
                            .setNegativeButton("取消",null)
                            .show();
                }




            }


        });
    }



    private static void LuckyMoney(ContentValues contentValues, ClassLoader cl) {

        Log.e(wodetag, "我收到一个红包");
        kaihongbao = true;
        String nativeUrlString = null;
        try {
            JSONObject wcpayinfo = new XmlToJson.Builder(contentValues.getAsString("content")).build().toJson()
                    .getJSONObject("msg").getJSONObject("appmsg").getJSONObject("wcpayinfo");

            nativeUrlString = wcpayinfo.getString("nativeurl");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // 启动红包页面
        if (launcherUiActivity != null) {
            Intent paramau = new Intent();
            paramau.putExtra("key_way", 1);
            paramau.putExtra("key_native_url", nativeUrlString);
            paramau.putExtra("key_username", contentValues.getAsString("talker"));
            XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.tencent.mm.bk.d", cl), "b", launcherUiActivity, "luckymoney", ".ui.LuckyMoneyReceiveUI", paramau);

        } else {
            Log.e(wodetag, "launcherUiActivity == null" + "\n");
        }

    }


    public static void handleTransfer(ContentValues contentValues, ClassLoader lpparam) throws Exception {


        JSONObject wcpayinfo = new XmlToJson.Builder(contentValues.getAsString("content")).build().toJson()
                .getJSONObject("msg").getJSONObject("appmsg").getJSONObject("wcpayinfo");

        int paysubtype = wcpayinfo.getInt("paysubtype");
        if (paysubtype != 1) {
            return;
        }
        kaizhuanzhang = true;
        Log.e(wodetag, "我收到一个转账");
        String transactionId = wcpayinfo.getString("transcationid");
        String transferId = wcpayinfo.getString("transferid");
        int invalidtime = wcpayinfo.getInt("invalidtime");

        String talker = contentValues.getAsString("talker");


        // 启动红包页面
        if (launcherUiActivity != null) {
            Intent intent = new Intent();
            intent.putExtra("sender_name", talker);
            intent.putExtra("is_sender", true);
            intent.putExtra("appmsg_type", 1);
            intent.putExtra("transfer_id", transferId);
            intent.putExtra("transaction_id", transactionId);
            intent.putExtra("effective_date", 1);
            intent.putExtra("total_fee", 0);
            intent.putExtra("fee_type", "ZAR");
            XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.tencent.mm.bk.d", lpparam), "b", launcherUiActivity, "remittance", ".ui.RemittanceDetailUI", intent);

        } else {
            Log.e(wodetag, "launcherUiActivity == null" + "\n");
        }


    }
}