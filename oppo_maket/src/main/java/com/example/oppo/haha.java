package com.example.oppo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.nearme.common.util.ClientIdUtil;
import com.nearme.common.util.SystemInfoUtil;
import com.oppo.MySerialize;
import com.oppo.anb;
import com.oppo.cdo.update.domain.dto.UpgradeWrapReq;
import com.oppo.event.IPCacheUtil;
import com.oppo.event.NetworkResponse;
import com.oppo.event.d;
import com.oppo.lr;
import com.oppo.whoops.domain.dto.req.Apk;
import com.oppo.whoops.domain.dto.req.UpgradeReq;
import com.oppo.whoops.domain.dto.req.mx;

import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by zk on 2018/1/16.
 */

public class haha {

    public static String e;

    private static CharSequence cccc;
   /* public static String packBaseEvent(Context context, LinkedList<BaseEventBean> linkedList) {
        if (linkedList == null || linkedList.isEmpty()) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("head", getHeaderObject(context));
            jSONObject.put("body", getBaseEventBody(linkedList));
        } catch (Exception e) {
            Log.e("wodelog", "com.android.statistics", e);
        }
        return jSONObject.toString();
    }*/


    public static JSONObject getHeaderObject(Context arg4) {
        JSONObject v1 = new JSONObject();
        try {
            v1.put("model", "AOSP ON HAMMERHEAD");
            v1.put("imei", "359125053640852");
            v1.put("carrier", SystemInfoUtil.getCarrierName(arg4));//这里是看移动联通电信
            v1.put("appId", 2);
            v1.put("appVersion", "5.2.1");
            v1.put("channel", "1");
            v1.put("sdkVersion", 501);
            v1.put("ssoid", "0");/////这个地方应该跟oppo账号有关系。需要多拿几个手机测试(默认是0)
            v1.put("clientTime", String.valueOf(System.currentTimeMillis()));
            v1.put("romVersion", "aosp_hammerhead-userdebug 4.4.4 KTU84P eng.zk.20170810.152219 test-keys");
            v1.put("osVersion", "4.4.4");
            v1.put("androidVersion", "4.4.4");
            v1.put("access", "WIFI");//{UNKNOWN,WIFI,UNKNOWN,2G,3G,4G}
        } catch (Exception v0) {
            Log.e("wodelog", "com.android.statistics", v0);
        }

        return v1;
    }

//POST http://kv.stat.nearme.com.cn/stat/event

    public static JSONObject getBaseEventObject(String eventTag, String eventID, Map arg8, String arg9, long duration) {//这个参数有点多先不看
        JSONObject v1 = new JSONObject();
        try {
            v1.put("eventID", eventID);
            v1.put("eventTag", eventTag);
            v1.put("eventTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            v1.put("appId", "2");
            v1.put("appVersion", "5.2.1");
            v1.put("duration", duration);
            Iterator v2 = arg8.keySet().iterator();
            while (v2.hasNext()) {
                Object v0_1 = v2.next();
                v1.put(((String) v0_1), arg8.get(v0_1));
            }
        } catch (Exception v0) {
            Log.e("wodelog", "com.android.statistics", v0);
        }

        return v1;
    }


    public static byte[] ads_body() {//http://data.ads.oppomobile.com/proxy/strategy/

        byte[] v0 = null;

        try {
            JSONObject v1 = new JSONObject();
            JSONObject v2 = new JSONObject();
            v2.put("imei", "359125053640852");
            v2.put("model", "AOSP on HammerHead");
            v2.put("osVersion", "AOSP_HAMMERHEAD-USERDEBUG 4.4.4 KTU84P ENG.ZK.20170810.152219 TEST-KEYS");
            v2.put("ptoVer", 100);//固定
            JSONObject v3 = new JSONObject();
            v3.put("pkgName", "com.oppo.market");
            v3.put("currTime", 0);//暂时是0，需要继续观察
            v1.put("head", v2);
            v1.put("body", v3);
            v0 = v1.toString().getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }


        return v0;
    }


    public static Map<String, Object> ads_header() {//http://data.ads.oppomobile.com/proxy/strategy/


        Map<String, Object> mHeader = new HashMap<>();
        mHeader.put("MediaType", "application/json");
        mHeader.put("Accept-Charset", "UTF-8");
        mHeader.put("User-Agent", "Dalvik/1.6.0 (Linux; U; Android 4.4.4; AOSP on HammerHead Build/KTU84P)");
        mHeader.put("Host", "data.ads.oppomobile.com");
        mHeader.put("Url", "http://data.ads.oppomobile.com/proxy/strategy/");
        mHeader.put("postxx", ads_body());

        return mHeader;
    }



    public static Map upgrade_header() {
        Map<String, Object> e = e(MainActivity.upgrade);
        e.put("postxx", MySerialize.serializeList(haha.a()));
        e.put("MediaType", "application/x-protostuff; charset=UTF-8");
        return e;
    }

    public static Map update_check_header(Context context) {
        byte[] bytes = MySerialize.serializeList(appList(context));

        Map<String, Object> e = e(MainActivity.update_check);
        e.put("postxx", bytes);
        e.put("MediaType", "application/x-protostuff; charset=UTF-8");
        return e;
    }


    public static UpgradeReq a() {//https://api.cdo.oppomobile.com/whoops/v1/upgrade

        List arrayList = new ArrayList();
        for (mx b : b().values()) {
            arrayList.add(b.b());
        }
        UpgradeReq upgradeReq = new UpgradeReq();
        upgradeReq.setAppType("app_store");
        upgradeReq.setApks(arrayList);
        // ((IApplication) AppUtil.getAppContext()).getNetRequestEngine().request(null, new my(upgradeReq, str), null, this.c);
        return upgradeReq;
    }

    private static Map<String, mx> a = new HashMap();

    public static void a(String str, String str2, int i, int i2) {
        Object obj = str2 == null ? str : str + "_" + str2;
        if (!a.containsKey(obj)) {
            mx mxVar = new mx();
            mxVar.a(aa(str, str2, i, i2));
            //  mxVar.a(mtVar);
            a.put(obj.toString(), mxVar);
        }
    }

    private static Apk aa(String str, String str2, int i, int i2) {
        Apk apk = new Apk();
        apk.setCatType(str);
        if (str2 != null) {
            apk.setPluginType(str2);
        }
        if (i != 0) {
            apk.setSupporter(i);
        }
        apk.setVerCode(i2);
        return apk;
    }

    private static Map<String, mx> b() {
        a("html", null, 5, 1094);
        a("plugin", "hot_fix", 55285565, 0);
        a("plugin", "download", 75285565, 1);
        a("plugin", "splash", 35285565, 1);
        a("app", null, 3, 5203);
        return a;
    }





    public static Map<String, Object> e(String url) {


        Map<String, Object> mHeader = new HashMap<>();
        String query;
        String imei = "359125053640852";
        int oSIntVersion = 19;
        String oSName = "4.4.4";
        long currentTimeMillis = System.currentTimeMillis();
        int b = 2;
        int a = 2101;

        StringBuilder append = new StringBuilder("Android").append("/").append("AOSP on HammerHead").append("/").append(oSIntVersion).append("/").append(oSName).append("/").append("0").append("/").append(b);
        StringBuilder append2 = new StringBuilder(append.toString()).append("/").append(a).append("/").append(5203);
        StringBuilder append3 = new StringBuilder(append.toString()).append("/").append("aosp_hammerhead-userdebug 4.4.4 KTU84P eng.zk.20170810.152219 test-keys").append("/").append(5203);
        String str = "";
        try {
            str = URLEncoder.encode(append2.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            str = append2.toString();
        }
        oSName = "";
        try {
            oSName = URLEncoder.encode(append3.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e3) {
            oSName = append3.toString();
        }
        mHeader.put("User-Agent", str);
        mHeader.put("t", String.valueOf(currentTimeMillis));
        mHeader.put("id", imei);
        mHeader.put("ocs", oSName);
        mHeader.put("ch", "" + a);
        mHeader.put("oak", "23a8ba872e430653");
        mHeader.put("Accept", "application/x-protostuff; charset=UTF-8");
        mHeader.put("locale", "zh-CN");
        mHeader.put("Url", url);
  /*      mHeader.put("Connection", "Keep-Alive");
        mHeader.put("Accept-Encoding", "gzip");
        mHeader.put("Host", "istore.oppomobile.com");*/


        String str2 = "";
        str = "";


        URL uri = null;
        try {
            uri = new URL(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        query = uri.getQuery();
        str = uri.getPath();


        append2 = new StringBuilder("23a8ba872e430653").append("70f68c62df3ba8a45f1c1a57c91df63e").append(oSName).append(currentTimeMillis).append(imei);
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        append = append2.append(str);
        if (TextUtils.isEmpty(query)) {
            query = "";
        }
        append = append.append(query);
        append.append(append.length());
        append.append("STORENEWMIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANYFY/UJGSzhIhpx6YM5KJ9yRHc7YeURxzb9tDvJvMfENHlnP3DtVkOIjERbpsSd76fjtZnMWY60TpGLGyrNkvuV40L15JQhHAo9yURpPQoI0eg3SLFmTEI/MUiPRCwfwYf2deqKKlsmMSysYYHX9JiGzQuWiYZaawxprSuiqDGvAgMBAAECgYEAtQ0QV00gGABISljNMy5aeDBBTSBWG2OjxJhxLRbndZM81OsMFysgC7dq+bUS6ke1YrDWgsoFhRxxTtx/2gDYciGp/c/h0Td5pGw7T9W6zo2xWI5oh1WyTnn0Xj17O9CmOk4fFDpJ6bapL+fyDy7gkEUChJ9+p66WSAlsfUhJ2TECQQD5sFWMGE2IiEuz4fIPaDrNSTHeFQQr/ZpZ7VzB2tcG7GyZRx5YORbZmX1jR7l3H4F98MgqCGs88w6FKnCpxDK3AkEA225CphAcfyiH0ShlZxEXBgIYt3V8nQuc/g2KJtiV6eeFkxmOMHbVTPGkARvt5VoPYEjwPTg43oqTDJVtlWagyQJBAOvEeJLno9aHNExvznyD4/pR4hec6qqLNgMyIYMfHCl6d3UodVvC1HO1/nMPl+4GvuRnxuoBtxj/PTe7AlUbYPMCQQDOkf4sVv58tqslO+I6JNyHy3F5RCELtuMUR6rG5x46FLqqwGQbO8ORq+m5IZHTV/Uhr4h6GXNwDQRh1EpVW0gBAkAp/v3tPI1riz6UuG0I6uf5er26yl5evPyPrjrD299L4Qy/1EIunayC7JYcSGlR01+EDYYgwUkec+QgrRC/NstV");
        mHeader.put("sign", md5Hex(append.toString()));
        return mHeader;
    }

    public static String md5Hex(String str) {
        byte[] md5 = md5(str);
        if (md5 == null) {
            return "";
        }
        return toHex(md5);
    }

    public static byte[] md5(String str) {
        try {
            return MessageDigest.getInstance("md5").digest(str.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static final char[] hexDigits = "0123456789abcdef".toCharArray();

    public static final String toHex(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            stringBuilder.append(hexDigits[(b >> 4) & 15]).append(hexDigits[b & 15]);
        }
        return stringBuilder.toString();//ff30286dbf3452fd1577c8a6da204fe5
    }


    public static String splash_url() {
        String str_data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // str_data="2018-01-11 14:46:02";
        String str2_screen = "1776#1080";
        String str3_net = "WIFI";

        String str4_url;
        if (true) {
            str4_url =MainActivity.splash;
        } else {
          //  str4_url = "https://183.131.22.101:8001/common/v1/ad/splash";
        }
        String url = str4_url + "?time=" + URLEncoder.encode(str_data) + "&screen=" + URLEncoder.encode(str2_screen) + "&networkId=" + URLEncoder.encode(str3_net);
        return url;
    }


    private static void c(Context context) {  // 列出本地安装apk，新建数据库，看看数据库中有没有 用户apk，如果没有添加到数据库，如果有继续判断版本号是否一致 ，不一致就重新添加数据库 /
        HashMap local_database1 = new HashMap();
        try {
            List v0_2 = context.getPackageManager().getInstalledPackages(0);
            byte[] v4 = new byte[131072];
            Iterator v5 = v0_2.iterator();
            while (v5.hasNext()) {
                Object v0_3 = v5.next();
                if (!isUserApp(((PackageInfo) v0_3)) && !((PackageInfo) v0_3).packageName.contains("com.nearme.gamecenter") && !((PackageInfo) v0_3).packageName.contains("com.oppo.market")) {
                    continue;
                }

                if (local_database1.containsKey(((PackageInfo) v0_3).packageName)) {
                    Object v1 = local_database1.remove(((PackageInfo) v0_3).packageName);
                    if ((((c) v1).a().equals(((PackageInfo) v0_3).packageName)) && ((c) v1).b() == ((long) getAppVersionCode(context))) {
                        Log.e("wodelog", "same md5: " + ((PackageInfo) v0_3).packageName);
                        continue;
                    }
                }

                Log.e("wodelog", "compute md5: " + ((PackageInfo) v0_3).packageName);
                a(context, ((PackageInfo) v0_3).packageName, ClientIdUtil.a(new File(((PackageInfo) v0_3).applicationInfo.sourceDir), v4), ((long) ((PackageInfo) v0_3).versionCode));
            }

            if (local_database1.isEmpty()) {
                return;
            }

            String[] v4_1 = new String[local_database1.size()];
            Iterator v3_1 = local_database1.keySet().iterator();
            int v1_1;
            for (v1_1 = 0; v3_1.hasNext(); ++v1_1) {
                v4_1[v1_1] = (String) v3_1.next();
            }

            if (v4_1.length <= 0) {
                return;
            }

            a(context, v4_1);
        } catch (Exception v0_1) {
            v0_1.printStackTrace();
        }
    }

    public static final int getAppVersionCode(Context context) {
        int sAppVersionCode = -1;

        if (-1 == sAppVersionCode) {
            try {
                sAppVersionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sAppVersionCode;
    }




    public static boolean isUserApp(PackageInfo packageInfo) {
        return (isSystemApp(packageInfo) || isSystemUpdateApp(packageInfo)) ? false : true;
    }

    public static boolean isSystemApp(PackageInfo packageInfo) {
        return (packageInfo.applicationInfo.flags & 1) != 0;
    }

    public static boolean isSystemUpdateApp(PackageInfo packageInfo) {
        return (packageInfo.applicationInfo.flags & 128) != 0;
    }



    public static void a(Context context, String str, String str2, long j) {
        ContentResolver contentResolver = context.getContentResolver();
        String[] strArr = new String[]{str};
        contentResolver.delete(Uri.parse(e), "package_name=?", strArr);
        ContentValues contentValues = new ContentValues();
        contentValues.put("package_name", str);
        contentValues.put("md5", str2);
        contentValues.put("local_version_code", Long.valueOf(j));
        contentResolver.insert(Uri.parse(e), contentValues);
    }


    public static void a(Context context, String... strArr) {
        if (strArr != null && strArr.length != 0) {
            context.getContentResolver().delete(Uri.parse(e), "package_namein(" + a(strArr), null);
        }
    }

    private static String a(String... strArr) {
        if (strArr == null || strArr.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strArr.length; i++) {
            if (i == strArr.length - 1) {
                stringBuilder.append("'" + strArr[i] + "')");
            } else {
                stringBuilder.append("'" + strArr[i] + "',");
            }
        }
        return stringBuilder.toString();
    }

    protected static UpgradeWrapReq appList(Context context) {
        c(context);

        List arrayList = new ArrayList();

        UpgradeWrapReq upgradeWrapReq = new UpgradeWrapReq();

        List<PackageInfo> installedPackages = context.getPackageManager().getInstalledPackages(0);
        try {


            HashMap a = aaa(context);
            if (installedPackages != null) {


                for (PackageInfo packageInfo : installedPackages) {
                    if (!(packageInfo == null || packageInfo.versionName == null || packageInfo.packageName == null)) {
                        if (TextUtils.isEmpty(cccc) || packageInfo.packageName.equals(cccc)) {
                            com.oppo.cdo.update.domain.dto.UpgradeReq upgradeReq = new com.oppo.cdo.update.domain.dto.UpgradeReq();
                            upgradeReq.setPkgName(packageInfo.packageName);
                            upgradeReq.setVerCode((long) packageInfo.versionCode);
                            c cVar = (c) a.get(packageInfo.packageName);
                            if (cVar == null || TextUtils.isEmpty(cVar.c()) || !(cVar.b() == 0 || cVar.b() == ((long) packageInfo.versionCode))) {
                                upgradeReq.setMd5("");
                            } else {
                                upgradeReq.setMd5(cVar.c());
                            }
                            arrayList.add(upgradeReq);
                            if (!TextUtils.isEmpty(cccc) && packageInfo.packageName.equals(cccc)) {
                                break;
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            Log.e("wodelog", e.toString());
            ;
        }
        upgradeWrapReq.setUpgrades(arrayList);
        return upgradeWrapReq;
    }


    public static HashMap aaa(Context arg7) {
        HashMap v6 = new HashMap();
        Cursor v1 = arg7.getContentResolver().query(Uri.parse(e), null, null, null, null);
        if (v1 != null) {
            try {
                if (v1.getCount() > 0) {
                    if (!v1.moveToFirst()) {
                        v1.close();
                        return null;
                    }

                    do {
                        String v0_2 = v1.getString(v1.getColumnIndex("package_name"));
                        long v2 = v1.getLong(v1.getColumnIndex("local_version_code"));
                        String v4 = v1.getString(v1.getColumnIndex("md5"));
                        c v5 = new c();
                        v5.a(v0_2);
                        v5.a(v2);
                        v5.b(v4);
                        v6.put(v0_2, v5);
                        if (v1.moveToNext()) {
                            continue;
                        }

                        break;
                    }
                    while (true);
                }
            } catch (Exception e) {
            }


        }

        return v6;
    }

    public static class c {
        String a;
        long b;
        String c;

        public c() {
            super();
        }

        public void a(String arg1) {
            this.a = arg1;
        }

        public void a(long arg2) {
            this.b = arg2;
        }

        public String a() {
            return this.a;
        }

        public void b(String arg1) {
            this.c = arg1;
        }

        public long b() {
            return this.b;
        }

        public String c() {
            return this.c;
        }
    }



    public static Map appevent_header() {//这个函数虽然能请求到结果，但是请求格式和正确的差了一点
        Map<String, Object> e = e(MainActivity.appevent);
      //  e.put("postxx", MySerialize.serializeList((Object) buildData()));
        e.put("MediaType", "application/x-protostuff; charset=UTF-8");
        e.put("stat", "201");
        return e;
    }









    protected static anb buildData(String str, String str2, String str3, long j, Map<String, String> arg12) {
      /*  arg12.put("r_ent_id","1");
        arg12.put("did","359125053640852");
        arg12.put("enter_id","1");
        arg12.put("name","201");*/



        arg12.put("key", str2);
        arg12.put("th_name", Thread.currentThread().getName());
        arg12.put("networkID", "wifi");
        arg12.put("rom_name","aosp_hammerhead-userdebug 4.4.4 KTU84P eng.zk.20170810.152219 test-keys");
        arg12.put("app_version","5.2.1");

        arg12.put("client_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
       // String v0 = com.nearme.platform.a.a(AppUtil.getAppContext()).getAccountManager().e();
            arg12.put("gc30", "-1");

        HashMap v2 = new HashMap();
        Iterator v3 = arg12.keySet().iterator();
        while(v3.hasNext()) {
            Object v0_1 = v3.next();
            Object v1 = arg12.get(v0_1);
            if(TextUtils.isEmpty((v1+""))) {
                continue;
            }

            ((Map)v2).put(v0_1, ((String)v1).replace("\n", "#"));
        }

        arg12.putAll(((Map)v2));
        anb a_a_a_anb = new anb();
        a_a_a_anb.a(str);
        a_a_a_anb.a((Map) arg12);
        a_a_a_anb.a(j);
        a_a_a_anb.b(str3);
        return a_a_a_anb;

    }
//body=359125053640852		AOSP on HammerHead	AOSP_HAMMERHEAD-USERDEBUG 4.4.4 KTU84P ENG.ZK.20170810.152219 TEST-KEYS		4.4.4	1.3.1	1	2	10002	5.2.1	WIFI	1516353731		1	cpd-app-expose	0	1	0,,,,,,,12/562/1/4,,11191,1,139868439,,,,,,A-0-5,1516353731779,1516353730797,,,,10
    //http://data.ads.oppomobile.com/upload/set0   这个函数参数也有点多。先不看

    private static String a(Context arg10, List arg11) {
       String v0_2 = null;

        /*if(arg11 != null && arg11.size() > 0) {
            try {
                JSONArray v1 = new JSONArray();
                Iterator v2 = arg11.iterator();
                while(v2.hasNext()) {
                    Object v0_1 = v2.next();
                    if(v0_1 != null) {
                        if(g.a(arg10, ((h)v0_1).l, ((h)v0_1).q)) {
                       //     com.oppo.acs.st.c.e.a(g.b, "dataType=" + ((h)v0_1).l + ",acsId=" + ((h)v0_1).m + ",effective");
                            ((h)v0_1).o = ((h)v0_1).o.replace("sessionId", "1");
                        }
                        else {
                         //   com.oppo.acs.st.c.e.a(g.b, "dataType=" + ((h)v0_1).l + ",acsId=" + ((h)v0_1).m + "， not effective");
                            ((h)v0_1).o = ((h)v0_1).o.replace("sessionId", "0");
                        }

                        ((h)v0_1).p = ((h)v0_1).p.replaceAll("#", "");
                    }

                    String v3 = g.b(arg10, ((h)v0_1).l);
                    JSONObject v4 = new JSONObject();
                    JSONObject v5 = new JSONObject();
                    v5.put("evtTime", ((h)v0_1).r);
                    v5.put("dataType", ((h)v0_1).l);
                    v4.put("headers", v5);
                    v4.put("body", ((h)v0_1).n + v3 + ((h)v0_1).o + v3 + ((h)v0_1).p);
                    v1.put(v4);
                }

                v0_2 = v1.toString();
            }
            catch(Exception v0) {
                //com.oppo.acs.st.c.e.c(g.b, "acsStDbCache2JsonString:" + v0.toString());
                v0_2 = "";
            }
        }
        else {
            v0_2 = "";
        }*/

        return v0_2;
    }



    public static Map<String, Object> GetSDKConfig_header() {// POST http://c.stat.nearme.com.cn:10014/GetSDKConfig HTTP/1.1


        Map<String, Object> mHeader = new HashMap<>();
        mHeader.put("MediaType", "text/json; charset=UTF-8");
       // mHeader.put("Host", "c.stat.nearme.com.cn:10014");
        mHeader.put("Url", MainActivity.GetSDKConfig);
        mHeader.put("User-Agent", null);
        mHeader.put("Accept-Encoding", null);

        byte[] v0 = null;

        try {
            JSONObject v2 = new JSONObject();
            v2.put("appVersion", "5.2.1");
            v2.put("appPackage", "com.oppo.market");
            v2.put("configMd5", "");

            v0 = v2.toString().getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        mHeader.put("postxx", v0);

        return mHeader;
    }



    public static void a(String str, String str2, String str3, Map<String, String> map) {
        if (map == null) {
            map = new HashMap();
        }
        if (!TextUtils.isEmpty(str3)) {
            map.put("opt_obj", str3);
        }
       a(str, str2, map);
    }


    public static void b(String str, int i) {
        Map hashMap = new HashMap();
        hashMap.put("module_id", str);
        if (i >= 0) {
            hashMap.put("pos", i + "");
        }
        a("1002", "301", hashMap);
    }


    public static void a(String PageId, String ModuleId, int pageposition, String cid) {
        if (!TextUtils.isEmpty(PageId) && !String.valueOf(0).equals(PageId)) {
            Map<String,String> b =new HashMap<>();
            String str4 = (String) b.get("page_id");
            if (!(PageId.equals(str4) || TextUtils.isEmpty(str4))) {
                b.put("pre_page_id", str4);
            }
            b.put("page_id", PageId);
            str4 = (String) b.get("module_id");
            if (!TextUtils.isEmpty(ModuleId) && !String.valueOf(0).equals(ModuleId)) {
                if (!(ModuleId.equals(str4) || TextUtils.isEmpty(str4))) {
                    b.put("pre_module_id", str4);
                }
                b.put("module_id", ModuleId);
            } else if (!TextUtils.isEmpty(str4)) {
                b.put("pre_module_id", str4);
            }
            if (pageposition >= 0) {
                str4 = (String) b.get("pos");
                if (!TextUtils.isEmpty(str4)) {
                    b.put("pre_pos", str4);
                }
                b.put("pos", pageposition + "");
            }
            if (!TextUtils.isEmpty(cid)) {
                str4 = (String) b.get("opt_obj");
                if (!TextUtils.isEmpty(str4)) {
                    b.put("pre_opt_obj", str4);
                }
                b.put("opt_obj", cid);
            }
            a("1002", "301", b);
        }
    }


    public static void a(List<lr> list) {
        if (list != null && list.size() > 0 && list.get(0) != null) {
            lr lrVar = (lr) list.get(0);
            String str = lrVar.b;
            int i = lrVar.c;
            long j = lrVar.a;
            if ((str.equals("20") && 200 == i) || ((str.equals("30") && 300 == i) || ((str.equals("11") && 101 == i) || 1007 == i || (str.equals("10") && 100 == i)))) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(str).append("-").append(i).append("-").append(j).append("|");
                int i2 = ((lr) list.get(0)).e;
                int i3 = ((lr) list.get(0)).f;
                stringBuilder.append(i2).append("-").append(i3).append("#");
                int i4 = i2;
                i2 = i3;
                for (lr lrVar2 : list) {
                    if (str == lrVar2.b && i == lrVar2.c) {
                        if (!((4 == lrVar2.q || lrVar2.e == i3) && lrVar2.f == i2)) {
                            stringBuilder.setLength(stringBuilder.length() - 1);
                            stringBuilder.append(";");
                            i4 = lrVar2.e;
                            i2 = lrVar2.f;
                            stringBuilder.append(i4).append("-").append(i2).append("#");
                        }
                        stringBuilder.append(lrVar2.g).append("-").append(lrVar2.i);
                        if (!(TextUtils.isEmpty(lrVar2.m) || lrVar2.m.toLowerCase().equals("null"))) {
                            stringBuilder.append("-").append(lrVar2.m);
                        }
                        stringBuilder.append("&");
                    }
                    i4 = i4;
                    i2 = i2;
                }
                stringBuilder.setLength(stringBuilder.length() - 1);
              //  je.a("exp-n", stringBuilder.toString());
                Map hashMap = new HashMap();
                hashMap.put("opt_obj", stringBuilder.toString());
                hashMap.put("page_id", String.valueOf(i));
                hashMap.put("module_id", str);
                if (1007 == i) {
                    hashMap.put("user_input_word", lrVar.j);
                    hashMap.put("custom_key_word", lrVar.k);
                    hashMap.put("search_type", String.valueOf(lrVar.l));
                }
                a("1003", "902", hashMap);
            }
        }
    }




    //行为请求地方，
    public static void haha(int case1){
        switch (case1){
            case 1:
                a("10005"  ,"5025",null,null);//这里是首次打开appo 跳过安装apk
                break;
            case 2:
                b("10",0);//这里是首次进去oppo， 触发了 onTabChanged 这个接口
                break;
            case 3:
                a("100","10",0,"0");// PageId,  ModuleId,  pageposition,  cid  和这几个参数有关系。具体不清楚
                break;
            case 4:
                a("5000","0",-1,(String)null);// PageId,  ModuleId,  pageposition,  cid  和这几个参数有关系。具体不清楚
                break;
        }
    }


    private void a(String str, long j, long j2, int i, String str2, String str3, Exception exception, String str4, String str5) {//带网址的请求地方，
        Map hashMap = new HashMap();
        hashMap.put("gc29", str);
        hashMap.put("gc31", String.valueOf(j));
        hashMap.put("gc28", String.valueOf(j2));
        hashMap.put("gc26", String.valueOf(i));
        hashMap.put("gc30", str2);
        if (j == 0 && str3 != null) {
            hashMap.put("gc32", str3);
        }
        if (exception != null) {
            hashMap.put("remark", exception.getMessage());
        }
        if (!TextUtils.isEmpty(str4)) {
            hashMap.put("reqId", str4);
        }
        if (!TextUtils.isEmpty(str5)) {
            hashMap.put("s_opt", str5);
        }
        Object obj = (exception != null || (i != 0 && (i < 200 || i >= 400))) ? 1 : null;
        try {
            hashMap.put("gc27", "wify");
            if (TextUtils.isEmpty(str4) && obj == null) {
                a("100111", "1007", hashMap, true);
                return;
            }
            if (obj != null) {
               Log.d("wodelog", "#failed#" + i + "#" + (exception != null ? exception.getMessage() : "null"));
            }
            a("100111", "1007", hashMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void a(d dVar, NetworkResponse networkResponse, Exception exception,Context context) {
        if (dVar == null || (dVar.getUrl().matches("https?://.+\\.(jpg|gif|png|JPG|webp|PNG|apk|APK)") && dVar.getOriginUrl().matches("https?://.+\\.(jpg|gif|png|JPG|webp|PNG|apk|APK)"))) {
            int code;
            String originUrl;
            if (networkResponse != null) {
                code = networkResponse.getCode();
            } else {
                code = -1;
            }
            if (dVar != null) {
                originUrl = dVar.getOriginUrl();
            } else {
                originUrl = "request = null";
            }
            Object obj = (exception != null || (code != 0 && (code <200 || code >= 400))) ? 1 : null;
            if (obj != null) {
               // je.d("NetLog", "OriginUrl=" + originUrl + "#url=" + dVar.getUrl() + "#failed#" + code + "#" + (exception != null ? exception.getMessage() : "null"));
                return;
            }
            return;
        }
        b(dVar, networkResponse, exception,context);
    }


    private void b(d dVar, NetworkResponse networkResponse, Exception exception,Context context) {
        long j;
        String str;
        String str2;
        String str3;
        String host;
        String str4 = null;
        String staticTag = dVar.getStaticTag();
        long j2 = 0;
        if (TextUtils.isEmpty(staticTag)) {
            j = 0;
        } else {
            String[] split = staticTag.split("#");
            try {
                if (split.length > 1) {
                    j2 = Long.valueOf(split[1]).longValue();
                }
                j = j2;
            } catch (Exception e) {
                j = 0;
            }
        }
        long currentTimeMillis = System.currentTimeMillis() - j;
        int i = -1;
        if (networkResponse != null) {
            i = networkResponse.getCode();
            if (networkResponse.headers.containsKey("reqId")) {
                str = (String) networkResponse.headers.get("reqId");
                if (!(dVar == null || dVar.getRequestHeader() == null || !dVar.getRequestHeader().containsKey("stat"))) {
                    str4 = (String) dVar.getRequestHeader().get("stat");
                }
                str2 = "";
                if (IPCacheUtil.a(context)) {
                    str3 = str2;
                } else {
                    try {
                        str3 = Uri.parse(dVar.getOriginUrl()).getHost();
                        host = Uri.parse(dVar.getUrl()).getHost();
                        if (str3 == null || a(str3) || host == null || !a(host) || !host.equals(Uri.parse(IPCacheUtil.getLastUsefulIP(dVar.getOriginUrl(),context)).getHost())) {
                            host = str2;
                        }
                        str3 = host;
                    } catch (Exception e2) {
                        str3 = str2;
                    }
                }
                a(dVar.getOriginUrl(), j, currentTimeMillis, i, str3, staticTag, exception, str, str4);
            }
        }
        str = null;
        str4 = (String) dVar.getRequestHeader().get("stat");
        str2 = "";
        if (IPCacheUtil.a(context)) {
            str3 = str2;
        } else {
            str3 = Uri.parse(dVar.getOriginUrl()).getHost();
            host = Uri.parse(dVar.getUrl()).getHost();
            host = str2;
            str3 = host;
        }
        a(dVar.getOriginUrl(), j, currentTimeMillis, i, str3, staticTag, exception, str, str4);
    }




    public void a(String str, String str2, Map<String, String> map, boolean z) {
        a(str, str2, 0, map, z);
    }





    public static void a(String str, String str2, int i, Map<String, String> map, boolean z) {
        Map hashMap = new HashMap();
        try {
            if (!true) {//这里不知道是啥。先设置true
                return;
            }
        } catch (Exception e) {
        }
        if (map == null) {
            hashMap = new HashMap();
        } else {
            hashMap = map;
        }
        hashMap.put("enter_id", "1"); //这个值还有可能是 0  还原完算法在分析
        if (!TextUtils.isEmpty(null)) {//这里是看push——id  是不是空，有可能不是空，目前抓包是空
            hashMap.put("push_id", null);
        }
        Map a = new HashMap();
        a.put("r_ent_id", "1");


        if (!(a == null || a.isEmpty())) {
            Iterator it = a.keySet().iterator();
            while (it != null && it.hasNext()) {
                String str3 = (String) it.next();
                String str4 = (String) a.get(str3);
                if (!(TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4))) {
                    hashMap.put(str3, str4);
                }
            }
        }
        hashMap.put("did", "359125053640852");
       // je.a(a, "category = " + str + ", name = " + str2 + ", value = " + i + ", StatMap = " + hashMap);
        if (z || !b.contains(str2)) {
           // b(str, str2, i, hashMap);
            //buildData(str, "", "", i, hashMap);
            getBaseEventObject(str,str2,hashMap,"",i);
            return;
        }
        hashMap.put("name", str2);
      //  c().onCustomEvent(str, "", "", 0, hashMap);
       // buildData(str, "", "", 0, hashMap);
        getBaseEventObject(str, "",hashMap, "", 0);
        getBaseEventObject( str, str2, map,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), i);

    }

    public static void a(String str, String str2, Map<String, String> map) {
        a(str, str2, 0, (Map) map);
    }

    public static void a(String str, String str2, int i, Map<String, String> map) {
        a(str, str2, i, map, false);
    }


    private boolean a(String str) {
        if (!str.contains("")) {
            return false;
        }
        String[] split = str.split("\\.");
        if (4 != split.length) {
            return false;
        }
        for (String b : split) {
            if (!b(b)) {
                return false;
            }
        }
        return true;
    }
    static Pattern d = Pattern.compile("[0-9]*");

    private boolean b(String str) {
        if (d.matcher(str).matches()) {
            return true;
        }
        return false;
    }



    private static List<String> b=new ArrayList<>();

    private void e() {
        this.b.add("7000");
        this.b.add("7002");
        this.b.add("7003");
        this.b.add("7004");
        this.b.add("7005");
        this.b.add("7006");
        this.b.add("7007");
        this.b.add("7008");
        this.b.add("7012");
        this.b.add("7013");
        this.b.add("7009");
        this.b.add("7010");
        this.b.add("7011");
        this.b.add("7020");
        this.b.add("7021");
        this.b.add("7022");
        this.b.add("7023");
        this.b.add("7014");
        this.b.add("7015");
        this.b.add("7016");
        this.b.add("7017");
        this.b.add("7018");
        this.b.add("7019");
        this.b.add("9001");
        this.b.add("201");
        this.b.add("302");
        this.b.add("202");
        this.b.add("203");
    }


}
