package com.nearme.common.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.graphics.Rect;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.oppo.acs.st.c.d;
import com.oppo.statistics.util.SystemInfoUtil;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class DeviceUtil {

    public class Commponent {
        public static final String COMPONENT_CACHE = "cache";
        public static final String COMPONENT_EVENT = "event";
        public static final String COMPONENT_IMAGELOAD = "imageloader";
        public static final String COMPONENT_LOG = "log";
        public static final String COMPONENT_NETENGINE = "netengine";
        public static final String COMPONENT_SCHEDULER = "scheduler";
        public static final String COMPONENT_SHARED_PREFERENCE = "sharepref";
        public static final String COMPONENT_TRANSACTION_MNG = "transaction";
    }


    private static final String BRAND_ONEPLUS = "oneplus";
    private static final String BRAND_OPPO = "OPPO";
    private static String COLOR_OS_ROM_NAME = "";
    private static String COLOR_OS_VERSION = "";
    private static final int DEFAULT_STATUSBAR_HEIGHT = 24;
    private static final String KEY_IMEI = "imei";
    private static final Pattern MTK_PATTERN = Pattern.compile("^[mt]{2}[a-zA-Z0-9]{0,10}$");
    private static final String OS_VERSION_UNKNOWN = "0";
    private static final String SP_NAME = "com.nearme.common.util.DeviceUtil";
    private static int mColorOsVersion = -1;
    private static String mImei = "";
    private static String mImsi = "";
    private static String mMacAddress = "";
    private static int mScreenHeight;
    private static int mScreenWidth;
    private static String sDeviceScreenSize = "";

    public enum Platform {
        UNKNOWN(0),
        MTK(1),
        QUALCOMM(2);

        private int type;

        private Platform(int i) {
            this.type = i;
        }

        public static Platform valueOf(int i) {
            switch (i) {
                case 0:
                    return UNKNOWN;
                case 1:
                    return MTK;
                case 2:
                    return QUALCOMM;
                default:
                    return UNKNOWN;
            }
        }
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static String getIMEI(Context context) {
        if (!isInvalidImei(mImei)) {
            mImei = getImeiFromSP(context);
            if (!isInvalidImei(mImei)) {
                mImei = ClientIdUtil.getClientId(context);
            }
            if (isInvalidImei(mImei)) {
                saveImeiToSP(context, mImei);
            }
        }
        return mImei;
    }

    private static boolean isInvalidImei(String str) {
        return (TextUtils.isEmpty(str) || ClientIdUtil.DEFAULT_CLIENT_ID.equals(str)) ? false : true;
    }

    private static String getImeiFromSP(Context context) {
        return context.getSharedPreferences(SP_NAME, 0).getString("imei", mImei);
    }

    private static void saveImeiToSP(Context context, String str) {
        Editor edit = context.getSharedPreferences(SP_NAME, 0).edit();
        edit.putString("imei", str);
        edit.commit();
    }

    public static String getIMSI(Context context) {
        if (TextUtils.isEmpty(mImsi)) {
            @SuppressLint({"MissingPermission", "WrongConstant"}) String subscriberId = ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
            if (subscriberId == null) {
                subscriberId = "";
            }
            mImsi = subscriberId;
        }
        return mImsi;
    }

    public static String getLocalMacAddress(Context context) {
        if (TextUtils.isEmpty(mMacAddress)) {
            @SuppressLint("WrongConstant") String macAddress = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            if (macAddress == null) {
                macAddress = "";
            }
            mMacAddress = macAddress;
        }
        return mMacAddress;
    }

    public static int getScreenHeight(Context context) {
        if (mScreenHeight == 0) {
            mScreenHeight = context.getResources().getDisplayMetrics().heightPixels;
        }
        return mScreenHeight;
    }

    public static int getScreenWidth(Context context) {
        if (mScreenWidth == 0) {
            mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
        }
        return mScreenWidth;
    }

    public static String getScreenSizeString(Context context) {
        if (TextUtils.isEmpty(sDeviceScreenSize)) {
            sDeviceScreenSize = getScreenHeight(context) + "*" + String.valueOf(getScreenWidth(context));
        }
        return sDeviceScreenSize;
    }

    public static String getPhoneName() {
        return Build.MODEL;
    }

    public static int getOSIntVersion() {
        return VERSION.SDK_INT;
    }

    public static String getOSName() {
        return VERSION.RELEASE;
    }

    public static String getPhoneBrand() {
        return Build.BRAND;
    }

    public static boolean isOppoBrand() {
        String phoneBrand = getPhoneBrand();
        if (TextUtils.isEmpty(phoneBrand) || !phoneBrand.equalsIgnoreCase(BRAND_OPPO)) {
            return false;
        }
        return true;
    }

    public static boolean isOnePlusBrand() {
        String phoneBrand = getPhoneBrand();
        if (TextUtils.isEmpty(phoneBrand) || !phoneBrand.equalsIgnoreCase(BRAND_ONEPLUS)) {
            return false;
        }
        return true;
    }

    public static int getStatusbarHeight(Activity activity) {
        try {
            Rect rect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            return rect.top;
        } catch (Exception e) {
            return (int) (activity.getResources().getDisplayMetrics().density * 24.0f);
        }
    }

    public static String getMobileRomVersion() {
        if (TextUtils.isEmpty(COLOR_OS_VERSION)) {
            try {
                Class cls = Class.forName("android.os.SystemProperties");
                COLOR_OS_VERSION = (String) cls.getMethod("get", new Class[]{String.class, String.class}).invoke(cls, new Object[]{"ro.build.version.opporom", "0"});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return COLOR_OS_VERSION;
    }

    public static String getRomName() {
        if (TextUtils.isEmpty(COLOR_OS_ROM_NAME)) {
            try {
                Class cls = Class.forName("android.os.SystemProperties");
                COLOR_OS_ROM_NAME = (String) cls.getMethod("get", new Class[]{String.class, String.class}).invoke(cls, new Object[]{"ro.build.display.id", ""});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return COLOR_OS_ROM_NAME;
    }

    public static boolean isColorOsV3() {
        String mobileRomVersion = getMobileRomVersion();
        if (TextUtils.isEmpty(mobileRomVersion)) {
            return false;
        }
        if (mobileRomVersion.startsWith("v3") || mobileRomVersion.startsWith("V3")) {
            return true;
        }
        return false;
    }

    public static boolean isColorOsV2() {
        String mobileRomVersion = getMobileRomVersion();
        if (TextUtils.isEmpty(mobileRomVersion)) {
            return false;
        }
        if (mobileRomVersion.startsWith("v2") || mobileRomVersion.startsWith("V2")) {
            return true;
        }
        return false;
    }

    public static int getColorOSVersion() {
        int i = mColorOsVersion;
        if (i >= 0) {
            return mColorOsVersion;
        }
        try {
            i = ((Integer) ReflectHelp.invokeStatic(ReflectHelp.getClassFromName("com.color.os.ColorBuild"), "getColorOSVERSION", null, null)).intValue();
        } catch (Exception e) {
            i = 0;
        }
        if (i == 0) {
            try {
                String mobileRomVersion = getMobileRomVersion();
                if (mobileRomVersion.startsWith("V1.4")) {
                    return 3;
                }
                if (mobileRomVersion.startsWith("V2.0")) {
                    return 4;
                }
                if (mobileRomVersion.startsWith("V2.1")) {
                    return 5;
                }
            } catch (Exception e2) {
            }
        }
        mColorOsVersion = i;
        return mColorOsVersion;
    }

    public static File getIndividualCacheDirectory(Context context, String str) {
        File cacheDirectory = getCacheDirectory(context, true);
        File file = new File(cacheDirectory, str);
        return (file.exists() || file.mkdir()) ? file : cacheDirectory;
    }

    public static File getCacheDirectory(Context context, boolean z) {
        Object externalStorageState;
        File file = null;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) {
            externalStorageState = "";
        }
       /* if (z && "mounted".equals(r1)) {
            file = getExternalCacheDir(context);
        }*/
        if (file == null) {
            File filesDir = context.getFilesDir();
            if (filesDir != null) {
                file = new File(filesDir, Commponent.COMPONENT_CACHE);
            }
        }
        if (file == null) {
            return new File("/data/data/" + context.getPackageName() + "/files/cache");
        }
        return file;
    }

    private static File getExternalCacheDir(Context context) {
        File file = new File(new File(new File(new File(new File(Environment.getExternalStorageDirectory(), SystemInfoUtil.SYSTEM_NAME), d.r), context.getPackageName()), "files"), Commponent.COMPONENT_CACHE);
        if (file.exists()) {
            return file;
        }
        if (!file.mkdirs()) {
            return null;
        }
        try {
            new File(file, ".nomedia").createNewFile();
            return file;
        } catch (IOException e) {
            return file;
        }
    }

   /* public static boolean isLowEndDevice() {
        return ((Long) DeviceMemoryUtil.getSysMemoryInfo().get("MemTotal:")).longValue() / 1000 <= 1024;
    }*/

    public static Platform getPlatForm() {
        if ("qcom".equals(getHardware())) {
            return Platform.QUALCOMM;
        }
        if (MTK_PATTERN.matcher(getHardware()).find()) {
            return Platform.MTK;
        }
        return Platform.UNKNOWN;
    }

    private static String getHardware() {
        try {
            return Build.HARDWARE;
        } catch (Throwable th) {
            return "0";
        }
    }
}