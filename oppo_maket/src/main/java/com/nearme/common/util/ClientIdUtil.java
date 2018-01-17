package com.nearme.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public final class ClientIdUtil {
    public static final String DEFAULT_CLIENT_ID = "000000000000000";
    private static final String EXTRAS_KEY_CLIENT_ID = "clientId";
    private static final int EXTRAS_KEY_CLIENT_ID_LEN = 15;
    private static final String EXTRAS_KEY_DEFAULT_VALUE = "";
    private static final String EXTRAS_KEY_UNKNOWN = "unknown";
    private static final String EXTRAS_KEY_ZERO = "0";
    private static final int MAX_RETRY_COUNT = 3;
    private static final String MCS_CONTROL_PULL_MSG_INFO_FILE_NAME = "mcs_msg.ini";

    private static final String MCS_FILE_SUFFIX_NAME = ".ini";
    private static final String MCS_HIDDEN_FILE_STORAGE_PATH = (Environment.getExternalStorageDirectory().getPath() + File.separator + ".mcs");
    private static final String MCS_CONTROL_PULL_MSG_INFO_FILE_PATH = (MCS_HIDDEN_FILE_STORAGE_PATH + File.separator + MCS_CONTROL_PULL_MSG_INFO_FILE_NAME);
    private static final String MCS_HIDDEN_SD_CARD_FOLDER = ".mcs";
    private static final String TAG = ClientIdUtil.class.getSimpleName();
    private static final long TIMER_DELAY_PERIOD = 3000;
    private static final long TIMER_DELAY_TIME = 3000;
    private static String sClientId = "";
    private static AtomicInteger sRetryCount = new AtomicInteger(0);
    private static Timer sTimer = null;

    private ClientIdUtil() {
    }

    public static String getClientId(Context context) {
        if (context != null) {
            Context applicationContext = context.getApplicationContext();
            if (sClientId == null || "".equals(sClientId)) {
                synchronized (ClientIdUtil.class) {
                    if (sClientId == null || "".equals(sClientId)) {
                        sClientId = getClientIdFromFile(applicationContext);
                    }
                }
            }
        }
        return sClientId != null ? sClientId : DEFAULT_CLIENT_ID;
    }

    private static String getClientIdFromFile(Context context) {
        String localClientId = getLocalClientId();
        if (isNullOrEmpty(localClientId)) {
            localClientId = getClientIdByOS(context);
            if (localClientId == null) {
                startRetryTimer(context);
            } else if (isInvalidClientId(localClientId)) {
                localClientId = buildClientId();
            }
            setLocalClientId(localClientId);
        }
        return localClientId;
    }

    private static String getClientIdByOS(Context context) {
        String reflectColorImei = reflectColorImei(context);
        if (reflectColorImei == null) {
            return getDeviceId(context);
        }
        return reflectColorImei;
    }

    private static void startRetryTimer(final Context context) {
        if (sTimer == null) {
            sTimer = new Timer();
            sTimer.schedule(new TimerTask() {
                public void run() {
                    String access$000 = ClientIdUtil.getClientIdByOS(context);
                    if (access$000 != null) {
                        if (ClientIdUtil.isInvalidClientId(access$000)) {
                            access$000 = ClientIdUtil.buildClientId();
                        }
                        ClientIdUtil.setLocalClientId(access$000);
                        cancel();
                        ClientIdUtil.cancelTimer();
                    } else if (3 <= ClientIdUtil.sRetryCount.incrementAndGet()) {
                        cancel();
                        ClientIdUtil.cancelTimer();
                    }
                }
            }, 3000, 3000);
        }
    }

    private static void cancelTimer() {
        if (sTimer != null) {
            sTimer.cancel();
            sRetryCount.set(0);
            sTimer = null;
        }
    }

    private static boolean isInvalidClientId(String str) {
        return EXTRAS_KEY_UNKNOWN.equalsIgnoreCase(str) || "null".equalsIgnoreCase(str) || "0".equalsIgnoreCase(str);
    }

    @SuppressLint({"MissingPermission", "HardwareIds", "WrongConstant"})
    private static String getDeviceId(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        } catch (Exception e) {
            return null;
        }
    }

    private static String reflectColorImei(Context context) {
        try {
            Class cls = Class.forName("android.telephony.ColorOSTelephonyManager");
            Method method = cls.getMethod("getDefault", new Class[]{Context.class});
            return (String) cls.getMethod("colorGetImei", new Class[]{Integer.TYPE}).invoke(method.invoke(cls, new Object[]{context}), new Object[]{Integer.valueOf(0)});
        } catch (Exception e) {
            return null;
        }
    }

    private static String getLocalClientId() {
        String loadData = loadData(MCS_CONTROL_PULL_MSG_INFO_FILE_PATH);
        if (isNullOrEmpty(loadData)) {
            return null;
        }
        return getString(parseObject(loadData, null), EXTRAS_KEY_CLIENT_ID, "");
    }

    private static void setLocalClientId(String str) {
        JSONObject jSONObject = null;
        try {
            String loadData = loadData(MCS_CONTROL_PULL_MSG_INFO_FILE_PATH);
            if (!isNullOrEmpty(loadData)) {
                jSONObject = parseObject(loadData, null);
            }
            if (jSONObject == null) {
                jSONObject = new JSONObject();
            }
            try {
                if (!isNullOrEmpty(str)) {
                    jSONObject.put(EXTRAS_KEY_CLIENT_ID, str);
                }
            } catch (JSONException e) {
            }
            String jSONObject2 = jSONObject.toString();
            if (!isNullOrEmpty(jSONObject2)) {
                saveData(MCS_CONTROL_PULL_MSG_INFO_FILE_PATH, jSONObject2);
            }
        } catch (Exception e2) {
        }
    }

    private static String buildClientId() {
        String str = getTimeStamp().substring(0, 6) + getUUIDHashCode();
        if (str.length() < 15) {
            str = (str + "123456789012345").substring(0, 15);
        }
        return replaceNonHexChar(str);
    }

    private static String getUUIDHashCode() {
        String valueOf = String.valueOf(Math.abs(UUID.randomUUID().toString().hashCode()));
        if (valueOf.length() < 9) {
            while (valueOf.length() < 9) {
                valueOf = valueOf + "0";
            }
        }
        return valueOf.substring(0, 9);
    }

    private static String loadData(String str) {
        if (!isExternalStorageMediaMounted()) {
            return null;
        }
        StringBuilder readFile = readFile(str, "utf-8");
        if (readFile != null) {
            return readFile.toString();
        }
        return null;
    }

    private static JSONObject parseObject(String str, JSONObject jSONObject) {
        Object parse = parse(str, jSONObject);
        return parse instanceof JSONObject ? (JSONObject) parse : jSONObject;
    }

    private static Object parse(String str, Object obj) {
        if (!isNullOrEmpty(str)) {
            try {
                obj = new JSONTokener(str).nextValue();
            } catch (JSONException e) {
            }
        }
        return obj;
    }

    private static String getString(JSONObject jSONObject, String str, String str2) {
        Object objectValue = getObjectValue(jSONObject, str, str2);
        return objectValue == null ? "" : objectValue.toString();
    }

    private static Object getObjectValue(JSONObject jSONObject, String str, Object obj) {
        if (jSONObject != null) {
            try {
                if (!jSONObject.isNull(str)) {
                    obj = jSONObject.get(str);
                }
            } catch (Exception e) {
            }
        }
        return obj;
    }

    private static boolean isExternalStorageMediaMounted() {
        if (Environment.getExternalStorageState() != null) {
            return Environment.getExternalStorageState().equals("mounted");
        }
        return false;
    }

    private static StringBuilder readFile(String str, String str2) {
        Throwable th;
        BufferedReader bufferedReader = null;
        File file = new File(str);
        StringBuilder stringBuilder = new StringBuilder("");
        if (file == null || !file.isFile()) {
            return null;
        }
        BufferedReader bufferedReader2;
        try {
            bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file), str2));
            while (true) {
                try {
                    String readLine = bufferedReader2.readLine();
                    if (readLine == null) {
                        break;
                    }
                    if (!stringBuilder.toString().equals("")) {
                        stringBuilder.append("\r\n");
                    }
                    stringBuilder.append(readLine);
                } catch (IOException e) {
                    bufferedReader = bufferedReader2;
                } catch (Throwable th2) {
                    th = th2;
                }
            }
            if (bufferedReader2 == null) {
                return stringBuilder;
            }
            try {
                bufferedReader2.close();
                return stringBuilder;
            } catch (IOException e2) {
                return stringBuilder;
            }
        } catch (IOException e3) {
            if (bufferedReader == null) {
                return stringBuilder;
            }
            try {
                bufferedReader.close();
                return stringBuilder;
            } catch (IOException e4) {
                return stringBuilder;
            }
        }
    }

    private static void saveData(String str, String str2) {
        if (isExternalStorageMediaMounted()) {
            writeFile(str, str2, false);
        }
    }

    private static boolean writeFile(String str, String str2, boolean z) {
        Throwable th;
        boolean z2 = false;
        if (!isNullOrEmpty(str2)) {
            FileWriter fileWriter = null;
            try {
                makeDirs(str);
                File file = new File(str);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fileWriter2 = new FileWriter(str, z);

                    fileWriter2.write(str2);
                    fileWriter2.flush();
                    z2 = true;
                    if (fileWriter2 != null) {
                        try {
                            fileWriter2.close();
                        } catch (IOException e) {
                        }
                    }

            } catch (Exception e5) {
                if (fileWriter != null) {
                }
                return z2;
            }
        }
        return z2;
    }

    private static boolean makeDirs(String str) {
        String folderName = getFolderName(str);
        if (isNullOrEmpty(folderName)) {
            return false;
        }
        File file = new File(folderName);
        if (file.exists()) {
            return true;
        }
        return file.mkdirs();
    }

    private static String getFolderName(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        int lastIndexOf = str.lastIndexOf(File.separator);
        return lastIndexOf == -1 ? "" : str.substring(0, lastIndexOf);
    }

    private static String getTimeStamp() {
        return new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
    }

    private static String replaceNonHexChar(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        byte[] bytes = str.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            if (!isHexDigit(bytes[i])) {
                bytes[i] = (byte) 48;
            }
        }
        return new String(bytes);
    }

    private static boolean isHexDigit(byte b) {
        return (b >= (byte) 48 && b <= (byte) 57) || ((b >= (byte) 97 && b <= (byte) 122) || (b >= (byte) 65 && b <= (byte) 90));
    }

    private static boolean isNullOrEmpty(String str) {
        if (str == null) {
            return true;
        }
        return "".equals(str.trim());
    }



    public static String a(File file, byte[] bArr) {
        String str = null;
        if (file != null) {
            InputStream fileInputStream;
            try {
                if (file.exists()) {
                    fileInputStream = new FileInputStream(file);
                        MessageDigest instance = MessageDigest.getInstance("MD5");
                        while (true) {
                            int read = fileInputStream.read(bArr);
                            if (read <= 0) {
                                break;
                            }
                            instance.update(bArr, 0, read);
                        }
                        str = b(instance.digest());
                    return str;
                }
            }catch (Exception e) {
            }
        }
        return str;
    }

    public static String b(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            stringBuilder.append(a[(bArr[i] & 240) >>> 4]);
            stringBuilder.append(a[bArr[i] & 15]);
        }
        return stringBuilder.toString();
    }
    private static final char[] a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};



}