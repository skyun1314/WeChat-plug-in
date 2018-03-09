package com.example.oppo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;


import com.oppo.MySerialize;
import com.oppo.anb;
import com.oppo.cdo.common.domain.dto.ResourceDto;
import com.oppo.cdo.update.domain.dto.UpgradeWrapReq;
import com.oppo.lr;
import com.oppo.whoops.domain.dto.req.Apk;
import com.oppo.whoops.domain.dto.req.UpgradeReq;
import com.oppo.whoops.domain.dto.req.mx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
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

import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by zk on 2018/1/16.
 */

public class OppoNetworks {


    private static CharSequence cccc;


    public static String packBaseEvent(shebei shebei) {
        if (OppoNetworks.my_jsonArray_array == null || my_jsonArray_array.length() == 0) {
            return null;
        }
        JSONObject jSONObject11 = new JSONObject();
        JSONObject jSONObject = new JSONObject();
        try {


            jSONObject11.put("ekv", my_jsonArray_array);


            jSONObject.put("head", getHeaderObject(shebei));
            jSONObject.put("body", jSONObject11);
        } catch (Exception e) {
            Log.e("wodelog", "com.android.statistics", e);
        }
        my_jsonArray_array = new JSONArray();
        return jSONObject.toString();
    }


    public static String getconfig(shebei shebei) {

        return "https://istore.oppomobile.com/common/v1/config?imei=" + shebei.imei + "&model=" + URLEncoder.encode(shebei.model) + "&osVersion=" + shebei.oSIntVersion + "&romVersion=0";//config
    }


    public static Map<String, Object> ClientPageVisit_header(shebei shebei) {    // http://i.stat.nearme.com.cn:10018/statistics/ClientPageVisit HTTP/1.1

        Map<String, Object> mHeader = new HashMap<>();
        mHeader.put("MediaType", "application/json");
        mHeader.put("Url", MainActivity.ClientPageVisit);


        mHeader.put("postxx", ClientPageVisit(shebei));

        return mHeader;
    }

    public static Map<String, Object> ClientStartUpload_header(shebei shebei) {    // http://i.stat.nearme.com.cn:10018/statistics/ClientPageVisit HTTP/1.1

        Map<String, Object> mHeader = new HashMap<>();
        mHeader.put("MediaType", "application/json");
        mHeader.put("Url", MainActivity.ClientStartUpload);
        mHeader.put("postxx", ClientStartUpload(shebei));

        return mHeader;
    }


    public static String serch_keyword(String keyword) {

        return "https://istore.oppomobile.com/search/v1/completion/card?keyword=" + URLEncoder.encode(keyword) + "&size=10&start=0";

    }

    public static int serch_keyword_ok = 0;

    public static String serch_keyword_ok(String keyword) {
        String s = "https://istore.oppomobile.com/search/v1/search?size=10&start=" + serch_keyword_ok + "&keyword=" + URLEncoder.encode(keyword);
        serch_keyword_ok += 10;
        return s;

    }

    public static String click_appid(long appId) {

        return "https://istore.oppomobile.com/card/store/v1/recommend/click?appid=" + appId;

    }

    public static String down_app(String down_app) {

        return down_app + "?ref=1007&type=1";

    }


    // http://i.stat.nearme.com.cn:10018/statistics/ClientPageVisit HTTP/1.1
    public static byte[] ClientPageVisit(shebei shebei) {//这个参数需要注意

        JSONObject jSONObject11 = new JSONObject();
        JSONObject jSONObject22 = new JSONObject();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray();
            jSONObject11.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            jSONObject11.put("duration", 453);
            jSONObject11.put("activities", new JSONArray("[[\"acv\",132],[\"acs\",321]]"));

            //
            jsonArray.put(jSONObject11);
            jSONObject22.put("page", jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }


        JSONObject jSONObject = new JSONObject();
        try {


            jSONObject.put("head", getHeaderObject(shebei));
            jSONObject.put("body", jSONObject22);
        } catch (Exception e) {
        }

        //  return "{\"head\":{\"androidVersion\":\"4.4.4\",\"model\":\"AOSP ON HAMMERHEAD\",\"osVersion\":\"4.4.4\",\"appId\":2,\"appVersion\":\"5.2.1\",\"ssoid\":\"0\",\"imei\":\"359125053640852\",\"carrier\":\"none\",\"romVersion\":\"aosp_hammerhead-userdebug 4.4.4 KTU84P eng.zk.20170810.152219 test-keys\",\"access\":\"WIFI\",\"clientTime\":\"1515659845294\",\"channel\":\"1\",\"sdkVersion\":501},\"body\":{\"page\":[{\"time\":\"2018-01-11 16:37:23\",\"duration\":453,\"activities\":[[\"acv\",132],[\"acs\",321]]}]}}".getBytes();

        return jSONObject.toString().getBytes();
    }

    public static byte[] ClientStartUpload(shebei shebei) {
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObjectx = new JSONObject();
        try {
            jSONObjectx.put("ssoId", 0);
            jSONObjectx.put("loginTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            jSONArray.put(jSONObjectx);

            jSONObject.put("head", getHeaderObject(shebei));
            jSONObject.put("body", jSONArray);
        } catch (Exception e) {
        }
        return jSONObject.toString().getBytes();
    }


    enum netname {
        中国移动,
        中国联通,
        中国电信,
        none
    }


    public static String getCarrierName(netname toLowerCase1) {
        String str = "none";
        String toLowerCase = toLowerCase1.name();
        if (toLowerCase.equals("中国移动") || toLowerCase.equals("china mobile") || toLowerCase.equals("chinamobile")) {
            return "China Mobile";
        }
        if (toLowerCase.equals("中国联通") || toLowerCase.equals("china unicom") || toLowerCase.equals("chinaunicom")) {
            return "China Unicom";
        }
        if (toLowerCase.equals("中国电信") || toLowerCase.equals("china net") || toLowerCase.equals("chinanet")) {
            return "China Net";
        }
        return str;
    }


    public static JSONObject getHeaderObject(shebei shebei) {
        JSONObject v1 = new JSONObject();
        try {
            v1.put("model", shebei.model /*"AOSP ON HAMMERHEAD"*/);
            v1.put("imei", shebei.imei /*"359125053640852"*/);
            v1.put("carrier", shebei.carrier /*getCarrierName(netname.中国电信)*/);//这里是看移动联通电信
            v1.put("appId", shebei.appId /*2*/);
            v1.put("appVersion", shebei.app_version_name /*"5.2.1"*/);
            v1.put("channel", shebei.channel /*"1"*/);
            v1.put("sdkVersion", shebei.sdkVersion /*501*/);
            v1.put("ssoid", shebei.ssoid /*"0"*/);/////这个地方应该跟oppo账号有关系。需要多拿几个手机测试(默认是0)
            v1.put("clientTime", String.valueOf(System.currentTimeMillis()));
            v1.put("romVersion", shebei.romVersion /*"aosp_hammerhead-userdebug 4.4.4 KTU84P eng.zk.20170810.152219 test-keys"*/);
            v1.put("osVersion", shebei.androidVersion /*"4.4.4"*/);
            v1.put("androidVersion", shebei.androidVersion /*"4.4.4"*/);
            v1.put("access", shebei.access /*"WIFI"*/);//{UNKNOWN,WIFI,UNKNOWN,2G,3G,4G}
        } catch (Exception v0) {
        }

        return v1;
    }


    public static JSONObject getBaseEventObject(shebei shebei, String eventTag, String eventID, Map arg8, String arg9, long duration) {//这个参数有点多先不看
        JSONObject v1 = new JSONObject();
        try {
            v1.put("eventID", eventID);
            v1.put("eventTag", eventTag);
            v1.put("eventTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            v1.put("appId", shebei.appId  /*"2"*/);
            v1.put("appVersion", shebei.app_version_name /*"5.2.1"*/);
            v1.put("duration", duration);
            Iterator v2 = arg8.keySet().iterator();
            while (v2.hasNext()) {
                Object v0_1 = v2.next();
                v1.put(((String) v0_1), arg8.get(v0_1));
            }
        } catch (Exception v0) {
            Log.e("wodelog", "com.android.statistics", v0);
        }
        my_jsonArray_array.put(v1);
        return v1;
    }


    public static byte[] ads_body(shebei shebei) {//http://data.ads.oppomobile.com/proxy/strategy/

        byte[] v0 = null;

        try {
            JSONObject v1 = new JSONObject();
            JSONObject head = new JSONObject();
            JSONObject v3 = new JSONObject();
            head.put("imei", shebei.imei /*"359125053640852"*/);
            head.put("model", shebei.model /*"AOSP ON HAMMERHEAD"*/);
            head.put("osVersion", shebei.romVersion /*"AOSP_HAMMERHEAD-USERDEBUG 4.4.4 KTU84P ENG.ZK.20170810.152219 TEST-KEYS"*/);
            head.put("ptoVer", 100);//固定

            v3.put("pkgName", shebei.pkgName /*"com.oppo.market"*/);
            v3.put("currTime", 0);//暂时是0，需要继续观察
            v1.put("head", head);
            v1.put("body", v3);
            v0 = v1.toString().getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }


        return v0;
    }


    public static Map<String, Object> setUser_Agent(shebei shebei) {


        Map<String, Object> mHeader = new HashMap<>();
        // mHeader.put("User-Agent", "Dalvik/1.6.0 (Linux; U; Android 4.4.4; AOSP on HammerHead Build/KTU84P)");
        mHeader.put("User-Agent", "Dalvik/1.6.0 (Linux; U; Android " + shebei.androidVersion + "; " + shebei.model + " Build/" + shebei.Build_ID + ")");

        return mHeader;
    }


    public static Map<String, Object> ads_header(shebei shebei) {//http://data.ads.oppomobile.com/proxy/strategy/


        Map<String, Object> mHeader = setUser_Agent(shebei);
        mHeader.put("MediaType", "application/json");
        mHeader.put("Accept-Charset", "UTF-8");
        //  mHeader.put("User-Agent", "Dalvik/1.6.0 (Linux; U; Android 4.4.4; AOSP on HammerHead Build/KTU84P)");
        mHeader.put("Host", "data.ads.oppomobile.com");
        mHeader.put("Url", MainActivity.strategy);
        mHeader.put("postxx", ads_body(shebei));

        return mHeader;
    }


    public static Map<String, Object> set0_header(shebei shebei, byte[] set0byte) {//http://data.ads.oppomobile.com/proxy/strategy/

        Map<String, Object> mHeader = setUser_Agent(shebei);
        mHeader.put("MediaType", "application/json");
        mHeader.put("Content-type", "application/json");
        mHeader.put("Accept-Charset", "UTF-8");
        // mHeader.put("User-Agent", "Dalvik/2.1.0 (Linux; U; Android 7.1.1; vivo X9s Plus Build/NMF26F)");
        mHeader.put("Url", MainActivity.set2);
        mHeader.put("postxx", set0byte);

        return mHeader;
    }

    public static Map<String, Object> splash_header(shebei shebei) {//https://istore.oppomobile.com/common/v1/ad/splash?time=2018-02-27+14%3A43%3A14&screen=1920%231080&networkId=WIFI

        Map<String, Object> stringObjectMap = OppoNetworks.setRequestHeader(shebei, splash_url(shebei));

        stringObjectMap.put("Accept", "application/json; charset=UTF-8");


        return stringObjectMap;
    }


    public static Map<String, Object> kv_stat_header(byte[] kv_stat) {//POST http://kv.stat.nearme.com.cn/stat/event


        Map<String, Object> mHeader = new HashMap<>();
        mHeader.put("MediaType", "text/json; charset=UTF-8");
        mHeader.put("Url", MainActivity.kv_stat);
        mHeader.put("postxx", kv_stat);

        return mHeader;
    }

    public static Map<String, Object> generate_204_header() {//POST "http://conn1.oppomobile.com/generate_204


        Map<String, Object> mHeader = new HashMap<>();
        mHeader.put("User-Agent", "STORE/5203");
        mHeader.put("Url", MainActivity.generate_204);
        return mHeader;
    }


    public static Map upgrade_header(shebei shebei) {
        Map<String, Object> e = setRequestHeader(shebei, MainActivity.upgrade);
        e.put("postxx", MySerialize.serializeList(OppoNetworks.a()));
        e.put("MediaType", "application/x-protostuff; charset=UTF-8");
        return e;
    }

    public static Map update_check_header(shebei shebei) {
        byte[] bytes = MySerialize.serializeList(appList());

        Map<String, Object> e = setRequestHeader(shebei, MainActivity.update_check);
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
            //  mxVar.analysis_home_page(mtVar);
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


    public static Map<String, Object> setsdkConfigHeader(String url) {
        Map<String, Object> mHeader = new HashMap<>();
        mHeader.put("Url", url);
        return mHeader;
    }


    public static Map<String, Object> setRequestHeader(shebei shebei, String url) {


        Map<String, Object> mHeader = new HashMap<>();
        String query;
        String imei = shebei.imei;
        int oSIntVersion = shebei.oSIntVersion;
        String oSName = shebei.androidVersion;
        long currentTimeMillis = System.currentTimeMillis();
        int a = 2101;

        StringBuilder append = new StringBuilder(shebei.Build_BRAND).append("/").append(shebei.model).append("/").append(oSIntVersion).append("/").append(oSName).append("/").append("0").append("/").append(2);
        StringBuilder append2 = new StringBuilder(append.toString()).append("/").append(a).append("/").append(5203);
        StringBuilder append3 = new StringBuilder(append.toString()).append("/").append(shebei.romVersion).append("/").append(5203);
        String User_Agent = "";
        try {
            User_Agent = URLEncoder.encode(append2.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            User_Agent = append2.toString();
        }
        oSName = "";
        try {
            oSName = URLEncoder.encode(append3.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e3) {
            oSName = append3.toString();
        }
        mHeader.put("User-Agent", User_Agent);
        mHeader.put("t", String.valueOf(currentTimeMillis));
        mHeader.put("id", imei);
        mHeader.put("ocs", oSName);
        mHeader.put("ch", "" + a);
        mHeader.put("oak", shebei.oak);
        mHeader.put("Accept", "application/x-protostuff; charset=UTF-8");
        mHeader.put("locale", "zh-CN");
        mHeader.put("Url", url);

        String str = "";


        URI uri = null;
        try {
            uri = new URI(url);
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


    public static String splash_url(shebei shebei) {
        String str_data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // str_data="2018-01-11 14:46:02";
        String str2_screen = shebei.screen_h + "#" + shebei.screen_w/*"1920#1080"*/;
        String str3_net = "WIFI";

        String str4_url;
        if (true) {
            str4_url = MainActivity.splash;
        } else {
            //  str4_url = "https://183.131.22.101:8001/common/v1/ad/splash";
        }
        String url = str4_url + "?time=" + URLEncoder.encode(str_data) + "&screen=" + URLEncoder.encode(str2_screen) + "&networkId=" + URLEncoder.encode(str3_net);
        return url;
    }


    /* private static void c(Context context) {  // 列出本地安装apk，新建数据库，看看数据库中有没有 用户apk，如果没有添加到数据库，如果有继续判断版本号是否一致 ，不一致就重新添加数据库 /
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
                        // Log.e("wodelog", "same md5: " + ((PackageInfo) v0_3).packageName);
                         continue;
                     }
                 }

                 //Log.e("wodelog", "compute md5: " + ((PackageInfo) v0_3).packageName);
                 String md5 = ClientIdUtil.a(new File(((PackageInfo) v0_3).applicationInfo.sourceDir), v4);
                 a(context, ((PackageInfo) v0_3).packageName,md5, ((long) ((PackageInfo) v0_3).versionCode));

                 Log.e("wodelog", ((PackageInfo) v0_3).packageName+"--"+md5+"--"+ (long) ((PackageInfo) v0_3).versionCode+"--"+((PackageInfo) v0_3).versionName);

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
         contentResolver.delete(Uri.parse(pkgMd5), "package_name=?", strArr);
         ContentValues contentValues = new ContentValues();
         contentValues.put("package_name", str);
         contentValues.put("md5", str2);
         contentValues.put("local_version_code", Long.valueOf(j));




         contentResolver.insert(Uri.parse(pkgMd5), contentValues);
     }


     public static void a(Context context, String... strArr) {
         if (strArr != null && strArr.length != 0) {
             context.getContentResolver().delete(Uri.parse(pkgMd5), "package_namein(" + a(strArr), null);
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
 */
    protected static UpgradeWrapReq appList() {
        //c(context);

        List arrayList = new ArrayList();

        UpgradeWrapReq upgradeWrapReq = new UpgradeWrapReq();

        // List<PackageInfo> installedPackages = context.getPackageManager().getInstalledPackages(0);
        try {


            //  HashMap a = aaa(context);
            if (MainActivity.listapps != null) {


                for (Object packageInfo1 : MainActivity.listapps) {
                    Apps packageInfo = (Apps) packageInfo1;
                    if (!(packageInfo == null || packageInfo.versionName == null || packageInfo.packageName == null)) {
                        if (TextUtils.isEmpty(cccc) || packageInfo.packageName.equals(cccc)) {
                            com.oppo.cdo.update.domain.dto.UpgradeReq upgradeReq = new com.oppo.cdo.update.domain.dto.UpgradeReq();
                            upgradeReq.setPkgName(packageInfo.packageName);
                            upgradeReq.setVerCode((long) packageInfo.versionCode);
                           /* c cVar = (c) a.get(packageInfo.packageName);
                            if (cVar == null || TextUtils.isEmpty(cVar.c()) || !(cVar.b() == 0 || cVar.b() == ((long) packageInfo.versionCode))) {
                                upgradeReq.setMd5("");
                            } else {
                                upgradeReq.setMd5(cVar.c());
                           // }
                           */
                            upgradeReq.setMd5(packageInfo.md5);
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
        }
        upgradeWrapReq.setUpgrades(arrayList);
        return upgradeWrapReq;
    }


   /* public static HashMap aaa(Context arg7) {
        HashMap v6 = new HashMap();
        Cursor v1 = arg7.getContentResolver().query(Uri.parse(pkgMd5), null, null, null, null);
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
*/


    public static Map appevent_header(shebei shebei) {//
        Map<String, Object> e = setRequestHeader(shebei, MainActivity.appevent);
        e.put("postxx", MySerialize.serializeList((Object) buildData(shebei, "10002", "201", "CokaIO-4", null)));
        e.put("MediaType", "application/x-protostuff; charset=UTF-8");
        e.put("stat", "201");
        return e;
    }

    public static Map downloading(String url,  String speed, String remark) {
        Map<String, Object> arg12 = new HashMap<>();
        arg12.put("cdn_ip", "123.129.234.41");
        arg12.put("custom_url", url);


        if (speed != null) {
            arg12.put("custom_speed", speed);
        }

        if (remark != null) {
            arg12.put("remark", "installPackage exception:-10000");
            arg12.put("install_code", -10000);
        }


        return arg12;
    }


    public static Map downloadInfo(int pos, shebei shebei, ResourceDto resourceDto, Map arg12, String name,String th_name,String str) {

        if (arg12 == null) {
            arg12 = new HashMap<>();
        }

        arg12.put("pos", pos);
        arg12.put("card_pos", serch_keyword_ok + pos);//这里是页数
        arg12.put("page_id", 1007);
        arg12.put("opt_obj", resourceDto.getVerId());
        arg12.put("s_d_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
        arg12.put("pre_page_id", 1006);
        arg12.put("card_id", 0);
        arg12.put("source_key", resourceDto.getSrcKey());
        arg12.put("search_type", 10);//大家都在搜  1，，，搜索记录：3   ，，自己输入的是（开始是）10，后来就是9
        arg12.put("custom_size", resourceDto.getSize());
        arg12.put("dl_c", 1);
        arg12.put("custom_key_word", shebei.key_word);
        arg12.put("dl_b_c", 75285565);//可能固定
        Map<String, Object> e = setRequestHeader(shebei, MainActivity.appevent);
        e.put("postxx", MySerialize.serializeList((Object) buildData(shebei, str,name, th_name,arg12)));
        e.put("MediaType", "application/x-protostuff; charset=UTF-8");
        e.put("stat", arg12.get("name"));
        return e;
    }


    protected static anb buildData(shebei shebei, String str, String name, String th_name, Map arg12) {
        if (arg12 == null) {
            arg12 = new HashMap<>();
        }
        arg12.put("r_ent_id", "1");
        arg12.put("key", "");
        arg12.put("did", shebei.imei);
        arg12.put("enter_id", "1");
        arg12.put("name", name);
        arg12.put("th_name", th_name);
        arg12.put("networkID", shebei.networkID);
        arg12.put("rom_name", shebei.romVersion/*"aosp_hammerhead-userdebug 4.4.4 KTU84P eng.zk.20170810.152219 test-keys"*/);
        arg12.put("app_version", shebei.app_version_name/*"5.2.1"*/);

        arg12.put("client_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
        arg12.put("gc30", "-1");

        HashMap v2 = new HashMap();
        Iterator v3 = arg12.keySet().iterator();
        while (v3.hasNext()) {
            Object v0_1 = v3.next();
            Object v1 = arg12.get(v0_1) + "";
            if (TextUtils.isEmpty((v1 + ""))) {
                continue;
            }

            ((Map) v2).put(v0_1, ((String) v1).replace("\n", "#"));
        }

        arg12.putAll(((Map) v2));
        anb a_a_a_anb = new anb();
        a_a_a_anb.a(str);
        a_a_a_anb.a((Map) arg12);
        a_a_a_anb.a(0);//抓包显示他还是下载软件重试的时间
        a_a_a_anb.b("");
        return a_a_a_anb;

    }
//body=359125053640852		AOSP on HammerHead	AOSP_HAMMERHEAD-USERDEBUG 4.4.4 KTU84P ENG.ZK.20170810.152219 TEST-KEYS		4.4.4	1.3.1	1	2	10002	5.2.1	WIFI	1516353731		1	cpd-app-expose	0	1	0,,,,,,,12/562/1/4,,11191,1,139868439,,,,,,A-0-5,1516353731779,1516353730797,,,,10
    //http://data.ads.oppomobile.com/upload/set0   这个函数参数也有点多。先不看

    public static String set0(shebei shebei, String adId, String adType, String price, String grade, String adOwner, String planId, String parEvtId, String displayTime, String creativeId) {
        JSONArray v1 = new JSONArray();
        String v3 = "\t";
        JSONObject v4 = new JSONObject();
        JSONObject headers = new JSONObject();
        // headers.put("evtTime", ((h)v0_1).r);
        long l = System.currentTimeMillis();
        try {
            headers.put("evtTime", l);
            // headers.put("dataType", ((h)v0_1).l);
            headers.put("dataType", "bd-expose");
            v4.put("headers", headers);
            //v4.put("body", ((h)v0_1).n + v3 + ((h)v0_1).o + v3 + ((h)v0_1).p);
            v4.put("body", shebei.imei + "\t\t" + shebei.model + "\t" + shebei.romVersion + "\t\t" + shebei.androidVersion + "\t141\t1\t2"
                    + v3
                    + "10003\t" + shebei.app_version_name + "\t" + shebei.access + "\t" + l / 1000 + "\t\t1\tbd-expose\t0\t1" +
                    v3 +
                    "0,,,40,,,,," + l + ",," + adId + "," + adType + "," + price + "," + grade + "," + adOwner + ",,,,," + planId + "," + parEvtId + ",,,," + displayTime + ",," + creativeId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        v1.put(v4);


        return v1.toString();
    }


    public static Map<String, Object> GetSDKConfig_header(shebei shebei) {// POST http://c.stat.nearme.com.cn:10014/GetSDKConfig HTTP/1.1


        Map<String, Object> mHeader = new HashMap<>();
        mHeader.put("MediaType", "text/json; charset=UTF-8");
        // mHeader.put("Host", "c.stat.nearme.com.cn:10014");
        mHeader.put("Url", MainActivity.GetSDKConfig);
        mHeader.put("User-Agent", null);
        mHeader.put("Accept-Encoding", null);

        byte[] v0 = null;

        try {
            JSONObject v2 = new JSONObject();
            v2.put("appVersion", shebei.app_version_name /*"5.2.1"*/);
            v2.put("appPackage", shebei.pkgName/*"com.oppo.market"*/);
            v2.put("configMd5", "");

            v0 = v2.toString().getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        mHeader.put("postxx", v0);

        return mHeader;
    }


    public static JSONObject a(shebei shebei, String str, String str2, String str3, Map<String, String> map) {
        if (map == null) {
            map = new HashMap();
        }
        if (!TextUtils.isEmpty(str3)) {
            map.put("opt_obj", str3);
        }
        return a(shebei, str, str2, map);
    }


    public static JSONObject b(shebei shebei, String str, int i) {
        Map hashMap = new HashMap();
        hashMap.put("module_id", str);
        if (i >= 0) {
            hashMap.put("pos", i + "");
        }
        return a(shebei, "1002", "301", hashMap);
    }


    public static JSONObject bbb(shebei shebei, String str, int i) {
        Map hashMap = new HashMap();

        return a(shebei, "1002", "301", hashMap);
    }


    public static JSONObject a(shebei shebei, String PageId, String ModuleId, int pageposition, String cid) {
        if (!TextUtils.isEmpty(PageId) && !String.valueOf(0).equals(PageId)) {
            Map<String, String> b = new HashMap<>();
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
            return a(shebei, "1002", "301", b);
        }
        return null;
    }


    public static JSONObject analysis_home_page(shebei shebei, List<lr> list) {
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
                //  je.analysis_home_page("exp-n", stringBuilder.toString());
                Map hashMap = new HashMap();
                hashMap.put("opt_obj", stringBuilder.toString());
                hashMap.put("page_id", String.valueOf(i));
                hashMap.put("module_id", str);
                if (1007 == i) {
                    hashMap.put("user_input_word", lrVar.j);
                    hashMap.put("custom_key_word", lrVar.k);
                    hashMap.put("search_type", String.valueOf(lrVar.l));
                }
                return a(shebei, "1003", "902", hashMap);
            }
        }
        return null;
    }


    //行为请求地方，
    public static JSONObject stat_event(shebei shebei, int case1) {
        switch (case1) {
            case 1:
                return a(shebei, "10005", "5025", null, null);//这里是首次打开appo 跳过安装apk
            case 2:
                return b(shebei, "10", 0);//这里是首次进去oppo， 触发了 onTabChanged 这个接口
            case 3:
                return a(shebei, "100", "10", 0, "0");// PageId,  ModuleId,  pageposition,  cid  和这几个参数有关系。具体不清楚
            case 4:
                return a(shebei, "5000", "0", -1, (String) null);// PageId,  ModuleId,  pageposition,  cid  和这几个参数有关系。具体不清楚
        }
        return null;
    }


    public static JSONObject a(shebei shebei, String url, long time, long file_lien, int respose_type, Exception exception, String reqId_null, String s_opt) {//带网址的请求地方，
        Map hashMap = new HashMap();
        hashMap.put("gc29", url);
        hashMap.put("gc31", String.valueOf(time));
        hashMap.put("gc28", String.valueOf(file_lien));
        hashMap.put("gc26", String.valueOf(respose_type));
        hashMap.put("gc30", "");
        if (time == 0 && null != null) {
            hashMap.put("gc32", null);
        }
        if (exception != null) {
            hashMap.put("remark", exception.getMessage());
        }
        if (!TextUtils.isEmpty(reqId_null)) {
            hashMap.put("reqId", reqId_null);
        }
        if (!TextUtils.isEmpty(s_opt)) {
            hashMap.put("s_opt", s_opt);
        }
        Object obj = (exception != null || (respose_type != 0 && (respose_type < 200 || respose_type >= 400))) ? 1 : null;
        try {
            hashMap.put("gc27", shebei.networkID /*"wify"*/);
            if (TextUtils.isEmpty(reqId_null) && obj == null) {
                return a(shebei, "100111", "1007", hashMap, true);
            }
            if (obj != null) {
                Log.d("wodelog", "#failed#" + respose_type + "#" + (exception != null ? exception.getMessage() : "null"));
            }
            return a(shebei, "100111", "1007", hashMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    static JSONArray my_jsonArray_array = new JSONArray();


    public static void b(shebei shebei,/*Map<String, Object> map, */Response response,/*d dVar,*/ Exception exception) {
        long j = System.currentTimeMillis();
        String reqId = null;
        String stat = null;

        long currentTimeMillis = System.currentTimeMillis() - j;
        int Response_code = -1;
        Headers headers = response.headers();
        if (response != null) {
            Response_code = response.code();


            if (headers.toString().contains("reqId")) {
                //if (networkResponse.headers.containsKey("reqId")) {
                reqId = (String) headers.get("reqId");
                if (!(headers.toString().contains("stat"))) {
                    stat = (String) headers.get("stat");
                }
                a(shebei, response.request().url().toString(), currentTimeMillis, j, Response_code, exception, reqId, stat);
            }
        }

        stat = (String) headers.get("stat");

        a(shebei, response.request().url().toString(), currentTimeMillis, j, Response_code, exception, reqId, stat);
    }


    public static JSONObject a(shebei shebei, String str, String str2, Map<String, String> map, boolean z) {
        return a(shebei, str, str2, 0, map, z);
    }


    public static JSONObject a(shebei shebei, String str, String str2, int i, Map<String, String> map, boolean z) {
        Map hashMap = new HashMap();
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
        String b = "";
        hashMap.put("did", shebei.imei);
        // je.analysis_home_page(analysis_home_page, "category = " + str + ", name = " + str2 + ", value = " + i + ", StatMap = " + hashMap);
        if (z || !b.contains(str2)) {
            // b(str, str2, i, hashMap);
            //buildData(str, "", "", i, hashMap);
            return getBaseEventObject(shebei, str, str2, hashMap, "", i);
        }
        hashMap.put("name", str2);
        //  c().onCustomEvent(str, "", "", 0, hashMap);
        // buildData(str, "", "", 0, hashMap);
        getBaseEventObject(shebei, str, "", hashMap, "", 0);
        return getBaseEventObject(shebei, str, str2, map, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), i);

    }

    public static JSONObject a(shebei shebei, String str, String str2, Map<String, String> map) {
        return a(shebei, str, str2, 0, (Map) map);
    }

    public static JSONObject a(shebei shebei, String str, String str2, int i, Map<String, String> map) {
        return a(shebei, str, str2, i, map, false);
    }


}
