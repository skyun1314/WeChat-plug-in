package com.oppo.statistics.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
//import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.regex.Pattern;

@SuppressLint({"DefaultLocale"})
public class SystemInfoUtil {
    private static final Pattern MTK_PATTERN = Pattern.compile("^[MT]{2}[a-zA-Z0-9]{0,10}$");
    private static final String OPPO_ROM_VERSION = "ro.build.version.opporom";
    private static int STATISTICS_PLATFORM_MTK = 1;
    private static int STATISTICS_PLATFORM_QUALCOMM = 2;
    public static final String SYSTEM_NAME = "Android";

    public static String getModel() {
        String str = "0";
        if (!isEmpty(Build.MODEL)) {
            return Build.MODEL.toUpperCase();
        }
        Log.w("com.android.statistics", "No MODEL.");
        return str;
    }

    public static int getPlatForm() {
        if (getHardware().equals("QCOM")) {
            return STATISTICS_PLATFORM_QUALCOMM;
        }
        if (MTK_PATTERN.matcher(getHardware()).find()) {
            return STATISTICS_PLATFORM_MTK;
        }
        return 0;
    }

    public static String getHardware() {
        if (!isEmpty(Build.HARDWARE)) {
            return Build.HARDWARE.toUpperCase();
        }
        Log.w("com.android.statistics", "No HARDWARE INFO.");
        return "0";
    }

    public static int getSDKVersion() {
        return VERSION.SDK_INT;
    }

    public static String getOsVersion() {
        String str = "0";
      //  str = SystemProperties.get(OPPO_ROM_VERSION);
        if (!TextUtils.isEmpty(str) && !str.equalsIgnoreCase("0")) {
            return str;
        }
        if (!isEmpty(VERSION.RELEASE)) {
            return VERSION.RELEASE.toUpperCase();
        }
        Log.w("com.android.statistics", "No OS VERSION.");
        return str;
    }

    public static String getRomVersion() {
        return "";//SystemProperties.get("ro.build.display.id", "");
    }

    public static String getAndroidVersion() {
        return VERSION.RELEASE;
    }

    @SuppressLint("MissingPermission")
    public static String getImei(Context context) {
        String str = "0";
        try {
            String str2 = "0";
            @SuppressLint("WrongConstant") TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (VERSION.SDK_INT >= 21) {
              //  str2 = telephonyManager.getImei(0);
            } else {
                str2 = telephonyManager.getDeviceId();
            }
            if (str2 != null) {
                return str2;
            }
        } catch (Exception e) {
            Log.w("com.android.statistics", "getImei()--Exception: " + e);
        }
        return str;
    }

    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getLocalPhoneNO(Context context) {
        String str = "0";
        try {
            @SuppressLint("WrongConstant") TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (!isEmpty(telephonyManager.getLine1Number())) {
                return telephonyManager.getLine1Number();
            }
        } catch (Exception e) {
            Log.e("com.android.statistics", e.toString());
        }
        return str;
    }

    @SuppressLint("WrongConstant")
    public static String getOperator(Context context) {
        String str = "";
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getNetworkOperatorName();
        } catch (Exception e) {
            Log.e("com.android.statistics", e.toString());
            return str;
        }
    }

    public static String getCarrierName(Context context) {
        String toLowerCase = getOperator(context).toLowerCase();
        String str = "none";
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

    public static String getMacAddress(Context context) {
        String str = "0";
        try {
            @SuppressLint("WrongConstant") WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
            if (!isEmpty(connectionInfo.getMacAddress())) {
                return connectionInfo.getMacAddress();
            }
            Log.w("com.android.statistics", "NO MAC ADDRESS.");
            return str;
        } catch (Exception e) {
            Log.e("com.android.statistics", e.toString());
        }
        return str;
    }

    public static String getMobile(Context context) {
        return "0";
    }

    private static boolean isEmpty(String str) {
        if (TextUtils.isEmpty(str) || "null".equals(str)) {
            return true;
        }
        return false;
    }
}