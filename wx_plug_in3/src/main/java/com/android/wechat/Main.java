package com.android.wechat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;


/**
 * Created by zk on 2018/3/8.
 */

public class Main implements IXposedHookLoadPackage {
    //provided
    private static final String WechatPackageName = "com.tencent.mm";
    public static String location_str="location";
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


    public static void hook_SQLiteDatabase(final ClassLoader cl) {//hook数据库的，insert''和update
        Class<?> SQLiteDatabase = null;
        try {
            SQLiteDatabase = cl.loadClass("com.tencent.wcdb.database.SQLiteDatabase");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(SQLiteDatabase==null){
            return;
        }

        XposedHelpers.findAndHookMethod(SQLiteDatabase, "updateWithOnConflict",
                String.class, ContentValues.class, String.class, String[].class, int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        ContentValues contentValues = (ContentValues) param.args[1];
                        String message = (String) param.args[0];
                        if (message == null || contentValues == null) {
                            return;
                        }

                        show_msg(message, contentValues, "updateWithOnConflict");//打印出
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
                            /*Log.i(wodetag, "cursor2  :start-----------------");
                            for (Map.Entry<String, Object> item : contentValues1.valueSet()) {
                                Log.i(wodetag, "   拦截测回消息修改后的：ContentValues:" + item.getKey() + " -> " + item.getValue().toString());
                            }
                            Log.i(wodetag, "cursor2  :end-----------------");*/
                            XposedHelpers.callMethod(param.thisObject, "insertWithOnConflict", "message", "msgId", contentValues1, 0);

                        }
                    }
                });
        XposedHelpers.findAndHookMethod(SQLiteDatabase, "insertWithOnConflict",
                String.class, String.class, ContentValues.class, int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        ContentValues contentValues = (ContentValues) param.args[2];
                        String message1 = (String) param.args[0];


                        show_msg(message1, contentValues, "insertWithOnConflict");
                        ((ContentValues) param.args[2]).remove("msgId");
                        if (message1.equals("message")) {


                          //  FrendCheck(param.thisObject);


                            String string = (String) param.args[1];
                            int int1 = (int) param.args[3];
                            Log.e(wodetag, "查看 insertWithOnConflict 函数参数： message1" + message1 + "; string:" + string + "; int1:" + int1);

                            String reserved = (String) contentValues.get("reserved");
                         //   int type = (int) contentValues.get("type");
                            Number type = (Number) contentValues.get("type");

                            if (reserved != null && (type.intValue() == 419430449)) {


                                OpenzhuanZhang(contentValues, cl);


                            } else if (reserved != null && (type.intValue() == 436207665)) {

                                LuckyMoney(contentValues, cl);


                            }
                        }


                    }
                });

    }


    public static void openMoney(final ClassLoader cl){
        Class<?> ad_k = null;
        Class<?> LuckyMoneyReceiveUI = null;
        try {
            ad_k = cl.loadClass("com.tencent.mm.ad.k");
            LuckyMoneyReceiveUI = cl.loadClass("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        if(ad_k==null){
            return;
        }


        // hook红包界面初始化“开”按钮的方法，在该方法完成后自动点击开按钮领取红包
        XposedHelpers.findAndHookMethod("com.tencent.mm.plugin.remittance.ui.RemittanceDetailUI", cl, "d", int.class, int.class,
                String.class,ad_k, new XC_MethodHook() {
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
        if(LuckyMoneyReceiveUI==null){
            return;
        }
        // hook红包界面初始化“开”按钮的方法，在该方法完成后自动点击开按钮领取红包
        XposedHelpers.findAndHookMethod(LuckyMoneyReceiveUI, "d", int.class, int.class,
                String.class,ad_k, new XC_MethodHook() {
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


    }



    public static boolean kaihongbao = false;
    public static boolean kaizhuanzhang = false;



    private static boolean a(String str) {
        return str.equals("android") || str.equals("com.android.providers.settings") || str.equals("com.android.server.telecom") || str.equals("com.android.location.fused") || str.equals("com.qualcomm.location");
    }

    private static boolean b(String str) {
        return str.startsWith("com.sonymobile") || str.startsWith("com.sonyericsson");
    }

public static String packageNmae="com.example.wx_plug_in3";
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam mLpp) throws Throwable {

        SportEdit.Sprot(mLpp);

        String pkgName = mLpp.packageName;



        if (!pkgName.contains(WechatPackageName)) {
            return;
        }




        XposedBridge.log("[handleLoadPackage] " + mLpp.packageName);
        if (!a(mLpp.packageName) && !b(mLpp.packageName)) {

            XSharedPreferences mysp = new XSharedPreferences(packageNmae, "FAKEMAP");

            HookGPS a = HookGPS.a();
            a.a(mysp, mLpp.classLoader);
            a.a(mLpp.classLoader);
        }

        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                ClassLoader cl = ((Context) param.args[0]).getClassLoader();


                hook_launcherUiActivity(cl);//拿到一直存在的activity去打开红包,和增加入口按钮
                hook_RemittanceDetailUI( cl);//打印跳转到红包页面接收的参数
                hook_SQLiteDatabase( cl);
                openMoney(cl);
                sezi(cl);
                create_dialog(cl);





              //  HookGPS.HookAndChange(cl);

               /* addFrend(cl);
                addFrend_result(cl);

                Frend_page(cl);
                Frend_photo(cl);*/

            }
        });


    }

    private static Activity launcherUiActivity = null;

    private void hook_launcherUiActivity(ClassLoader cl) {//拿到一直存在的activity去打开红包

        Class<?> LauncherUI = null;
        try {
            LauncherUI = cl.loadClass("com.tencent.mm.ui.LauncherUI");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e(wodetag,"hook_launcherUiActivity:"+e.toString());


            LauncherUI=  XposedHelpers.findClass("com.tencent.mm.ui.LauncherUI",cl);

        }




        if(LauncherUI==null){
            Log.e(wodetag,"hook_launcherUiActivity:hook失败");
            return;
        }

        XposedHelpers.findAndHookMethod(LauncherUI, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                launcherUiActivity = (Activity) param.thisObject;
            }
        });


        XposedHelpers.findAndHookMethod(LauncherUI, "onCreateOptionsMenu", Menu.class, new XC_MethodHook() {


            @Override
            protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Menu menu= (Menu) param.args[0];
                Log.e(wodetag,"增加微X模块按钮");
                menu.add(0,3,0,"微X模块");
                menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {


                        Intent intent=new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//参数是包名，类全限定名，注意直接用类名不行
                        ComponentName cn=new ComponentName("com.example.wx_plug_in3",
                                "com.amap.searchdemo.MainActivity");
                        intent.setComponent(cn);
                        ((Activity)param.thisObject).startActivity(intent);
                       // Toast.makeText((Context)param.thisObject,"haha",Toast.LENGTH_LONG).show();;

                        return false;
                    }
                });
            }
        });







    }

    private void hook_RemittanceDetailUI(ClassLoader cl)  {//打印跳转到红包页面接收的参数

        Class   RemittanceDetailUI = null;
        try {
               RemittanceDetailUI = cl.loadClass("com.tencent.mm.plugin.remittance.ui.RemittanceDetailUI");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(RemittanceDetailUI==null){
            return;
        }
        XposedHelpers.findAndHookMethod(RemittanceDetailUI, "onCreate", Bundle.class, new XC_MethodHook() {
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

                if(shaizi_position!=-1){
                    param.setResult(shaizi_position);
                }
                shaizi_position=-1;


            }


        });
    }
    public static int shaizi_position=-1;


    public static void create_dialog(ClassLoader cl){
        Class  SmileyGrid$1=null;
        try {
              SmileyGrid$1 = cl.loadClass("com.tencent.mm.view.SmileyGrid$1");
            XposedHelpers.findAndHookMethod(SmileyGrid$1, "onItemClick", AdapterView.class, View.class, int.class, long.class, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(final MethodHookParam param) throws Throwable {


                    int positon=(int)  param.args[2];

                    Log.e(wodetag,"create_dialog  参数"+param.args[2]+";"+param.args[3]);
                    String shaizi[]={"1","2","3","4","5","6"};
                    String caiquan[]={"剪刀","石头","布"};



                    String xueze[] = new String[0];

                    if(positon==1){
                        xueze=caiquan;
                    }else if(positon==2){
                        xueze=shaizi;
                    }else{
                        return  XposedBridge.invokeOriginalMethod(param.method,  param.thisObject,  param.args);
                    }

                    if (launcherUiActivity != null) {
                        new AlertDialog.Builder(launcherUiActivity)
                                .setTitle("发送几点")
                                .setSingleChoiceItems(xueze, 0, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Log.e(wodetag,"选择了："+i);
                                        shaizi_position=i;
                                        dialogInterface.dismiss();
                                        try {
                                            XposedBridge.invokeOriginalMethod(param.method,  param.thisObject,  param.args);
                                        } catch (Exception e) {
                                            Log.e(wodetag,"选择点数的时候报错："+e.toString());
                                            e.printStackTrace();
                                        }
                                    }
                                })
                                .setNegativeButton("取消",null)
                                .setPositiveButton("随机", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        try {
                                            XposedBridge.invokeOriginalMethod(param.method,  param.thisObject,  param.args);

                                        } catch (Exception e) {
                                            Log.e(wodetag,"选择随机的时候报错："+e.toString());
                                            e.printStackTrace();
                                        }
                                    }
                                })
                                .show();
                    }



                    return null;
                }


            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e(wodetag,"create_dialog     "+e.toString());
        }



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


    public static void OpenzhuanZhang(ContentValues contentValues, ClassLoader lpparam) throws Exception {


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

    public static Cursor select_from_table(Object thisObject,String sql_str,String[]params ){
        //Cursor cursor = (Cursor) XposedHelpers.callMethod(thisObject, "rawQuery", "select * from message where msgId=?", new String[]{(contentValues.get("msgId") + "")});
        Cursor cursor = (Cursor) XposedHelpers.callMethod(thisObject, "rawQuery", sql_str, params);
        return  cursor;
    }



    public static void addFrend(ClassLoader cl){
        Class<?> aClass = null;
        try {
           aClass = cl.loadClass("com.tencent.mm.plugin.search.ui.FTSAddFriendUI");
            XposedHelpers.findAndHookMethod(aClass, "HQ", String.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Log.e(wodetag,"addFrend:"+param.args[0]);
                }
            });
        } catch (ClassNotFoundException e) {
            Log.e(wodetag,"addFrend:"+e.toString());
            e.printStackTrace();
        }


    }



    public static void addFrend_result(ClassLoader cl){
        Class<?> aClass = null;
        try {
            aClass = cl.loadClass("com.tencent.mm.h.a");
            XposedHelpers.findAndHookMethod(aClass, "dZ", String.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Log.e(wodetag,"addFrend_result:  "+param.args[0]);
                }
            });
        } catch (ClassNotFoundException e) {
            Log.e(wodetag,"addFrend_result:  "+e.toString());
            e.printStackTrace();
        }


    }



    public static void Frend_page(ClassLoader cl){
        Class<?> aClass = null;
        try {
            aClass = cl.loadClass("com.tencent.mm.plugin.profile.ui.ContactInfoUI");
            XposedHelpers.findAndHookMethod(aClass, "onCreate", Bundle.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);

                    Activity activity= (Activity) param.thisObject;
                    int Contact_Scene= activity.getIntent().getIntExtra("Contact_Scene", 9);
                    String Verify_ticket =activity. getIntent().getStringExtra("Verify_ticket");
                    boolean Chat_Readonly = activity.getIntent().getBooleanExtra("Chat_Readonly", false);
                    boolean User_Verify= activity.getIntent().getBooleanExtra("User_Verify", false);
                    String Contact_User = activity.getIntent().getStringExtra("Contact_User");
                    int Contact_VUser_Info_Flag = activity.getIntent().getIntExtra("Contact_VUser_Info_Flag", 0);
                    String Contact_VUser_Info = activity.getIntent().getStringExtra("Contact_VUser_Info");
                    int Contact_Ext_Flag = activity.getIntent().getIntExtra("Contact_Ext_Flag", 0);
                    boolean force_get_contact =activity. getIntent().getBooleanExtra("force_get_contact", false);
                    Log.e(wodetag,"Frend_page:  Contact_Scene:"+Contact_Scene+"Contact_VUser_Info_Flag:"+Contact_VUser_Info_Flag+" Contact_VUser_Info:"+Contact_VUser_Info+
                            " Contact_Ext_Flag:"+Contact_Ext_Flag+" force_get_contact"+force_get_contact+
                            " Verify_ticket: "+Verify_ticket+" Chat_Readonly:"+Chat_Readonly+" User_Verify:"+User_Verify+" Contact_User"+Contact_User);
                }
            });
        } catch (ClassNotFoundException e) {
            Log.e(wodetag," Frend_page :"+e.toString());
            e.printStackTrace();
        }


    }

    public static void Frend_photo(ClassLoader cl){
        Class<?> aClass = null;
        try {
            aClass = cl.loadClass("com.tencent.mm.pluginsdk.ui.preference.SnsPreference");



            XposedHelpers.findAndHookMethod(aClass, "bVy", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);


                    List list= (List) XposedHelpers.getObjectField( param.thisObject,"list");
                    Log.e(wodetag,"相册list："+"size"+list.size()+" ; list:"+list.toString());


                  /*  StackTraceElement[] wodelogs = new Throwable("wodelog").getStackTrace();
                    for (int i = 1; i < 6; i++) {
                        Log.e(wodetag,wodelogs[i].toString());
                    }*/


                }
            });
        } catch (ClassNotFoundException e) {
            Log.e(wodetag," Frend_photo :"+e.toString());
            e.printStackTrace();
        }


    }




    public static void FrendCheck(Object thisObject){


        Cursor count_cursor = select_from_table(thisObject, "select count(*)from rcontact where type=3 AND username!='weixin'", null);
        count_cursor.moveToFirst();
        Long count = count_cursor.getLong(0);

        Log.e(wodetag,"开始检测僵尸粉\n您的好友数量:"+count+"\n僵尸粉会以名片显示\n同时会标记在标签中");
        int lahei=0;
        int shanchu=0;
        int yichang = 0;
       // Cursor frend_cursor=  select_from_table(thisObject,"select * from rcontact where contactLabelIds = 4",null);
        Cursor frend_cursor=  select_from_table(thisObject,"select * from rcontact where type=3 AND username!='weixin'",null);


        if (frend_cursor != null ) {
            while (frend_cursor.moveToNext()){







                String nickname = frend_cursor.getString(4);
                int type = frend_cursor.getInt(8);
                String contactLabelIds = frend_cursor.getString(18);
                if(contactLabelIds==null){
                    Log.e(wodetag,"contactLabelIds 为空");
                }

                String username = frend_cursor.getString(0);

                Log.e(wodetag,"  ");
                Log.e(wodetag,"nickname："+nickname+"   username:"+username+   "      contactLabelIds:"+contactLabelIds);

               /* switch (Integer.parseInt(contactLabelIds)){

                    case 1://异常
                        yichang++;
                        Log.e(wodetag,"异常好友："+nickname);
                        break;
                    case 2://删除
                        shanchu++;
                        Log.e(wodetag,"僵尸粉："+nickname);
                        break;
                    case 4://拉黑
                        lahei++;
                        Log.e(wodetag,"被拉黑："+nickname);
                        break;
                }*/
            }
        }else{
            Log.e(wodetag,"  ");
            Log.e(wodetag,"查询到数据0条");
        }


        Log.e(wodetag,"  ");
        Log.e(wodetag,"检测完毕\n僵尸粉:"+shanchu+"个\n被拉黑:"+lahei+"个\n异常好友:"+yichang+"个");
    }

}