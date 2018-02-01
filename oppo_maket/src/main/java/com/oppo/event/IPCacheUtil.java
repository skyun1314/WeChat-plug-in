package com.oppo.event;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class IPCacheUtil {
    public static SharedPreferences a = null;
    public static int b = -1;
    private static final Pattern c = Pattern.compile("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");
    private static ConcurrentHashMap<String, Map<String, String>> d = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Boolean> e = new ConcurrentHashMap();
    private static boolean f = false;
    private static String g = "";
    private static ExecutorService h = Executors.newSingleThreadExecutor();

    public static boolean a(Context context) {
        d(context);
        return "true".equals(a.getString("pref.cache.ip.open", "true"));
    }

    public static String getLastUsefulIP(String str,Context context) {
        if (!a(context)) {
            return "";
        }
        h(context);
        CharSequence host = Uri.parse(str).getHost();
        CharSequence charSequence = "";
        if (d.containsKey(host)) {
            g(context);
            Map map = (Map) d.get(host);
            if (map.containsKey(String.valueOf(b) + g)) {
                charSequence = (String) map.get(String.valueOf(b) + g);
            } else if (map.containsKey(String.valueOf(3))) {
                charSequence = (String) map.get(String.valueOf(3));
            } else if (map.containsKey(String.valueOf(2))) {
                charSequence = (String) map.get(String.valueOf(2));
            } else if (map.containsKey(String.valueOf(1))) {
                charSequence = (String) map.get(String.valueOf(1));
            } else if (map.containsKey(String.valueOf(0))) {
                charSequence = (String) map.get(String.valueOf(0));
            } else {
                charSequence = (String) map.get(String.valueOf(-1));
            }
        }
        if (TextUtils.isEmpty(charSequence)) {
            return "";
        }
        return str.replace(host, charSequence);
    }

    public static void a(final String str, final String str2, final Context context) {
        h.execute(new Runnable() {
            public void run() {
                if (IPCacheUtil.a(context) && !IPCacheUtil.b(str,context) && !TextUtils.isEmpty(str2)) {
                    IPCacheUtil.h(context);
                    IPCacheUtil.g(context);
                    if (!IPCacheUtil.e.containsKey(str + IPCacheUtil.b + IPCacheUtil.g) || !IPCacheUtil.d.containsKey(str)) {
                        Map map;
                        if (IPCacheUtil.d.containsKey(str)) {
                            for (Entry entry : IPCacheUtil.d.entrySet()) {
                                if (str.equals(entry.getKey())) {
                                    map = (Map) entry.getValue();
                                    if (map != null) {
                                        String str = (String) map.get(String.valueOf(IPCacheUtil.b) + IPCacheUtil.g);
                                        if (str == null || !str.equals(str2)) {
                                            map.put(String.valueOf(IPCacheUtil.b) + IPCacheUtil.g, str2);
                                            IPCacheUtil.i(context);
                                            break;
                                        }
                                    } else {
                                        continue;
                                    }
                                }
                            }
                        } else {
                            map = new ConcurrentHashMap();
                            map.put(String.valueOf(IPCacheUtil.b) + IPCacheUtil.g, str2);
                            IPCacheUtil.d.put(str, map);
                            IPCacheUtil.i(context);
                        }
                        IPCacheUtil.e.put(str + IPCacheUtil.b + IPCacheUtil.g, Boolean.valueOf(true));
                    }
                }
            }
        });
    }

    public static void a(Context context, String str) {
        d(context);
        Editor edit = a.edit();
        edit.putString("pref.cache.ip.open", str);
        edit.commit();
    }

    @SuppressLint("WrongConstant")
    private static void g(Context context) {
        b = b(context);
        g = "";
        if (b == 0) {
            try {
                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
                if (wifiManager.getWifiState() == 3) {
                    WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                    if (connectionInfo != null) {
                        g = ":" + connectionInfo.getSSID();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean b(String str,Context context) {
        return c.matcher(str).matches();
    }

    private static int b(Context context) {
        String typeName;
        try {
            @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                if (activeNetworkInfo.getTypeName().equals("WIFI")) {
                    typeName = activeNetworkInfo.getTypeName();
                } else {
                    typeName = activeNetworkInfo.getExtraInfo();
                }
                if (typeName == null) {
                    return -1;
                }
                if (typeName.equalsIgnoreCase("WIFI")) {
                    return 0;
                }
                if (typeName.equals("3gwap") || typeName.equals("uniwap") || typeName.equals("3gnet") || typeName.equals("uninet")) {
                    return 2;
                }
                if (typeName.equals("cmnet") || typeName.equals("cmwap")) {
                    return 1;
                }
                return (typeName.equals("ctnet") || typeName.equals("ctwap")) ? 3 : -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        typeName = null;
        if (typeName == null) {
            return -1;
        }
        if (typeName.equalsIgnoreCase("WIFI")) {
            return 0;
        }
        if (!typeName.equals("3gwap")) {
        }
        return 2;
    }

    private static void h(Context context) {
        if (!f) {
            synchronized (d) {
                if (!f) {
                    for (String str : c(context).split("--")) {
                        String c = c(context, str);
                        try {
                            Map concurrentHashMap = new ConcurrentHashMap();
                            JSONObject jSONObject = new JSONObject(c);
                            Iterator keys = jSONObject.keys();
                            while (keys.hasNext()) {
                                c = (String) keys.next();
                                concurrentHashMap.put(c, jSONObject.getString(c));
                            }
                            d.put(str, concurrentHashMap);
                        } catch (Exception e) {
                        }
                    }
                    f = true;
                }
            }
        }
    }

    private static void i(Context context) {
        String str = "";
        for (Entry entry : d.entrySet()) {
            str = str + ((String) entry.getKey()) + "--";
            a(context, (String) entry.getKey(), new JSONObject((Map) entry.getValue()).toString());
        }
        if (!str.equals("") && str.endsWith("--")) {
            str = str.substring(0, str.length() - 1);
        }
        b(context, str);
    }

    private static void a(Context context, String str, String str2) {
        d(context);
        Editor edit = a.edit();
        edit.putString("pref.cache.ip." + str, str2);
        edit.commit();
    }

    private static void b(Context context, String str) {
        d(context);
        Editor edit = a.edit();
        edit.putString("pref.cache.ip.domain.list", str);
        edit.commit();
    }

    private static String c(Context context, String str) {
        d(context);
        return e(context).getString("pref.cache.ip." + str, "");
    }

    private static String c(Context context) {
        d(context);
        return e(context).getString("pref.cache.ip.domain.list", "");
    }

    private static void d(Context context) {
        if (a == null) {
            a = e(context);
        }
    }

    private static SharedPreferences e(Context context) {
        return context.getSharedPreferences(context.getPackageName() + "_ip_prefs", 0);
    }
}