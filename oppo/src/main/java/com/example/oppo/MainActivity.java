package com.example.oppo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.nearme.common.util.ClientIdUtil;
import com.oppo.cdo.update.domain.dto.UpgradeWrapReq;

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
import java.util.concurrent.ConcurrentHashMap;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class MainActivity extends Activity {


    private CharSequence cccc;

    public class c {
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


    static String e;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e = "content://" + getPackageName() + "/" + "pkgMd5";
        String home_url = "https://istore.oppomobile.com/card/store/v1/home?size=10&start=0";//首页内容
        String config_url = "https://istore.oppomobile.com/common/v1/config?imei=359125053640852&model=AOSP+on+HammerHead&osVersion=19&romVersion=0";//config
        String open_required = "https://istore.oppomobile.com/card/store/v1/open-required";//open_required
        String struct_url = "https://istore.oppomobile.com/card/store/v1/struct";//struct_url
        String float_url = "https://istore.oppomobile.com/common/v1/float?token=-1";//float_url
        String update_check = "https://istore.oppomobile.com/update/v1/check";//update_check


        byte[] bytes = serializeList(appList(this));

        Map<String, Object> e = e(update_check);
        e.put("postxx", bytes);
        network.okhttp_post(e);

    }

    public static <T> byte[] serializeList(T t) {

        Class cls = t.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);


        return ProtostuffIOUtil.toByteArray(t, oc.a(cls), buffer);


    }


    /* compiled from: SchemaUtils */
    public static class oc {
        private static ConcurrentHashMap<Class<?>, Schema<?>> axxx = new ConcurrentHashMap();

        public static <T> Schema<T> a(Class<T> cls) {
            Schema<T> nVar = (Schema) axxx.get(cls);
            if (nVar != null) {
                return nVar;
            }
            Schema a = RuntimeSchema.createFrom((Class) cls);
            axxx.put(cls, a);
            return a;
        }
    }


    private Map<String, Object> e(String url) {


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


    public static String get_url() {
        String str_data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // str_data="2018-01-11 14:46:02";
        String str2_screen = "1776#1080";
        String str3_net = "WIFI";

        String str4_url;
        if (true) {
            str4_url = "https://istore.oppomobile.com/common/v1/ad/splash";
        } else {
            str4_url = "https://183.131.22.101:8001/common/v1/ad/splash";
        }
        String url = str4_url + "?time=" + URLEncoder.encode(str_data) + "&screen=" + URLEncoder.encode(str2_screen) + "&networkId=" + URLEncoder.encode(str3_net);
        return url;
    }


    private void c(Context context) {  // 列出本地安装apk，新建数据库，看看数据库中有没有 用户apk，如果没有添加到数据库，如果有继续判断版本号是否一致 ，不一致就重新添加数据库 /
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

    protected UpgradeWrapReq appList(Context context) {
        List<UpgradeReq> arrayList = new ArrayList();

        UpgradeWrapReq upgradeWrapReq = new UpgradeWrapReq();

        List<PackageInfo> installedPackages = getPackageManager().getInstalledPackages(0);
        try {


            HashMap a = aaa(context);
            if (installedPackages != null) {


                for (PackageInfo packageInfo : installedPackages) {
                    if (!(packageInfo == null || packageInfo.versionName == null || packageInfo.packageName == null)) {
                        if (TextUtils.isEmpty(this.cccc) || packageInfo.packageName.equals(this.cccc)) {
                            UpgradeReq upgradeReq = new UpgradeReq();
                            upgradeReq.setPkgName(packageInfo.packageName);
                            upgradeReq.setVerCode((long) packageInfo.versionCode);
                            c cVar = (c) a.get(packageInfo.packageName);
                            if (cVar == null || TextUtils.isEmpty(cVar.c()) || !(cVar.b() == 0 || cVar.b() == ((long) packageInfo.versionCode))) {
                                upgradeReq.setMd5("");
                            } else {
                                upgradeReq.setMd5(cVar.c());
                            }
                            arrayList.add(upgradeReq);
                            if (!TextUtils.isEmpty(this.cccc) && packageInfo.packageName.equals(this.cccc)) {
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


    public HashMap aaa(Context arg7) {
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


}
