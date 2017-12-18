package com.example.zk.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import com.example.zk.myapplication.com.MyLog;
import com.example.zk.myapplication.com.RootCmd;
import com.example.zk.myapplication.com.example.kingroot.Util;
import com.example.zk.myapplication.com.kingroot.sdk.root.CMDUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    protected static final String PREFS_FILE = "gank_device_id.xml";
    protected static final String PREFS_DEVICE_ID = "gank_device_id";
    protected static String uuid;
    private SurfaceView surfaceView = null;
    private Camera camera = null;

    public synchronized Camera getCamera() {
        if (this.camera == null) {
            try {
                this.camera = Camera.open();
                this.camera.setPreviewDisplay(this.surfaceView.getHolder());
            } catch (Exception e) {
                e.printStackTrace();
                if (this.camera != null) {
                    this.camera.release();
                    this.camera = null;
                }
            }
        }
        return this.camera;
    }


    private Runnable loadViews = new Runnable() {
        public void run() {
            MainActivity.this.surfaceView = (SurfaceView) MainActivity.this.findViewById(R.id.surfaceView);
            MainActivity.this.surfaceView.setZOrderOnTop(true);
            MainActivity.this.surfaceView.setBackgroundColor(-1);
            MainActivity.this.surfaceView.getHolder().addCallback(new Callback() {
                public void surfaceCreated(SurfaceHolder holder) {
                    if (MainActivity.this.camera != null) {
                        try {
                            MainActivity.this.camera.setPreviewDisplay(holder);
                            MainActivity.this.camera.startPreview();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }

                public void surfaceDestroyed(SurfaceHolder holder) {
                }
            });
            MainActivity.this.surfaceView.getHolder().setType(3);
        }
    };



    private void openFlashlight() {
            Camera camera = getCamera();
            if (camera != null) {
                try {
                    Camera.Parameters mParameters = camera.getParameters();
                    mParameters.setFlashMode("torch");
                    camera.setParameters(mParameters);
                    camera.startPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }




    static public String getUDID(Context context) {
       /* if (uuid == null) {
            if (uuid == null) {
                final SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0);
                final String id = prefs.getString(PREFS_DEVICE_ID, null);

                if (id != null) {
                    // Use the ids previously computed and stored in the prefs file
                    uuid = id;


                } else {

                    final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

                    // Use the Android ID unless it's broken, in which case fallback on deviceId,
                    // unless it's not available, then fallback on a random number which we store
                    // to a prefs file
                    try {
                        if (!"9774d56d682e549c".equals(androidId)) {
                            uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8")).toString();
                        } else {
                            final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                            uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")).toString() : UUID.randomUUID().toString();
                        }
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }

                    // Write the value out to the prefs file
                    prefs.edit().putString(PREFS_DEVICE_ID, uuid).commit();
                }
            }
        }*/
        return uuid;
    }

    public static void a(int[] iArr, int i, int i2) {
        int i3 = iArr[i];
        iArr[i] = iArr[i2];
        iArr[i2] = i3;
    }

    public static boolean a(byte[] bArr) {
        int length = bArr.length;
        if (length <= 0 || length > 256) {
            return false;
        }
        length = 0;
        for (byte b : bArr) {
            if ((b & 255) == 14) {
                length++;
                if (length > 3) {
                    return false;
                }
            }
        }
        return true;
    }


    public static byte[] gettooken(byte[] bArr, byte[] bArr2) {
        int i = 0;
        if (!a(bArr2)) {
            throw new IllegalArgumentException("key is fail!");
        } else if (bArr.length < 1) {
            throw new IllegalArgumentException("data is fail!");
        } else {
            int i2;
            int[] iArr = new int[256];
            for (i2 = 0; i2 < iArr.length; i2++) {
                iArr[i2] = i2;
            }
            int i3 = 0;
            for (i2 = 0; i2 < iArr.length; i2++) {
                i3 = ((i3 + iArr[i2]) + (bArr2[i2 % bArr2.length] & 255)) % 256;
                a(iArr, i2, i3);
            }
            byte[] bArr3 = new byte[bArr.length];
            i2 = 0;
            i3 = 0;
            while (i < bArr3.length) {
                i2 = (i2 + 1) % 256;
                i3 = (i3 + iArr[i2]) % 256;
                a(iArr, i2, i3);
                bArr3[i] = (byte) (iArr[(iArr[i2] + iArr[i3]) % 256] ^ bArr[i]);
                i++;
            }
            return bArr3;
        }
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        str_xx = "items=[{\"openId\":\"\",\"longitude\":\"\",\"latitude\":\"\",\"city\":\"\",\"suiteType\":2,\"imei\":\"866946023235515\",\"imsi\":\"460030991868105\",\"phoneModels\":\"PLK-AL10\",\"sysVersion\":\"android6.0\",\"currentVersion\":\"SDK_CTv3.1.9\",\"channelId\":\"\",\"thirdId\":\"8138110206\",\"appName\":\"android.graphics.drawable.BitmapDrawable@b9d3cdc\",\"appVersion\":\"7.9.5\",\"networkType\":\"WIFI\",\"ipAddress\":\"172.16.109.83\",\"responseCode\":\"0\",\"responseDesc\":\"\",\"deviceBrand\":\"IPHONE-X\",\"apps\":\"\",\"timeStamp\":1512381849983}]&optionAction=http://open.e.189.cn/api/clientSuit/updateResource.do&isCompress=false&itemSize=1&timeStamp=1512381849983";
        Thread t1 = new Thread(this.loadViews);
        t1.start();

//a(this);

        openFlashlight();
        // Log.e("wodelog", getUDID(this));

        //Log.e("wodelog", UUID.randomUUID().toString());

        byte hexData[] = {
                (byte) 0x94, (byte) 0xB0, (byte) 0xB7, (byte) 0xFB, 0x55, 0x7D, 0x11, (byte) 0xEC, 0x14, (byte) 0xA5, (byte) 0x91, 0x3C, 0x48, 0x2A, (byte) 0xBD, 0x03,
                0x2D, 0x36, 0x0D, 0x4C, (byte) 0x84, (byte) 0xF8, (byte) 0xC6, (byte) 0xA6, 0x6A, (byte) 0xC4, 0x65, 0x27, 0x5C, (byte) 0x94, 0x04, (byte) 0xF8,
                (byte) 0xDC, (byte) 0x82, 0x76, 0x2B, (byte) 0xC3, 0x63, (byte) 0xB8, (byte) 0xBD, (byte) 0xA8, 0x78, (byte) 0xA4, 0x15, (byte) 0xD5, (byte) 0xEC, (byte) 0x9B, (byte) 0xB2,
                (byte) 0x86, 0x72, 0x05, (byte) 0xE3, (byte) 0xB3, (byte) 0xEF, 0x7E, 0x21, (byte) 0xB0, 0x13, (byte) 0x97, 0x40, 0x6C, 0x0F, (byte) 0xD9, 0x78,
                0x7D, (byte) 0xF0, (byte) 0x9D, (byte) 0x90, 0x4E, 0x76, (byte) 0xE0, 0x34, (byte) 0xEF, (byte) 0xEF, 0x3B, (byte) 0xFF, (byte) 0xF1, 0x1F, (byte) 0xC2, 0x47,
                0x74
        };                                                         //这个是imei的MD5
        // String s = new String(gettooken(hexData, "68865e00815f08b1a6375ca6c05dc60a".getBytes()));
        String s = new String(gettooken(parseHexStr2Byte("D3B5B1A5192144EE18AA94374E2BBC0923330E"), "68865e00815f08b1a6375ca6c05dc60a".getBytes()));
        Log.e("wodelog", "smgui:" + s);
        String imei = "cantgetimei";
        // imei= "864162036315102";

        //  String cantgetimei = MyMD5(imei);
        //  Log.e("wodelog","MyMD5:"+cantgetimei);
    }


    static String str_xx = "deviceId=7331761174e331a1a1a2a999d6fc636e" +
            "&functionVersionCode=0" +
            "&mobileBrand=IPHONE-X" +
            "&clientType=1" +
            "&versionCode=v0" +
            "&osInfo=Android:6.0" +
            "&timeStamp=" + System.currentTimeMillis() +
            "&locationY=" +
            "&mobileModel=PLK-AL10" +
            "&terminalInfo=PLK-AL10" +
            "&imei=866946023235515" +
            "&locationX=" +
            "&sdkVersionCode=v3.1.9" +
            "&deviceSrc=4" +
            "&imsi=460030991868105" +
            "&thirdAppId=8138110206";


    byte b = (byte) new Random().nextInt(127);
    String str = "[{\"commons\":{\"departmentID\":\"Cardniu\",\"businessID\":\"NewActivationStatistics\",\"udid\":\"deviceId-866946023235515-generate-cardniu\",\"ts\":\"0\",\"appId\":\"10\",\"appVer\":\"7.9.5\",\"ifa\":\"deviceId-866946023235515-generate-cardniu\",\"systemVer\":\"6.0\",\"androidId\":\"ac990fca12f33a16\",\"dfrom\":\"cardniu\",\"systemName\":\"android\"},\"events\":[{\"dt\":\"1512387129\",\"m\":\"installFinished\",\"mac\":\"50:A7:2B:6D:CE:3C\",\"partner\":\"product\",\"network\":\"wifi\",\"ip\":\"172.16.109.83\"}]}]";
    byte[] arrayOfByte = a(str, b);

    private static byte[] a(String str, byte b) {
        if (!TextUtils.isEmpty(str)) {
            try {
                byte[] bytes = str.getBytes("utf-8");
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] = (byte) (bytes[i] ^ b);
                }
                return bytes;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }


    public static String a(Context context) {


        return b("30020", "json", "v1.46", "8138110206", "jBbvMXo9zi9oRXBPRtNtH1dAfxY44HA0", str_xx, context);
    }


    public static String b(String str, String str2, String str3, String str4, String str5, String map, Context context) {  //这个电信的加密函数
        String a = CMSform("com.mymoney.sms", "cn.com.chinatelecom.account.lib.b.g", context, new Class[]{String.class, String.class}, new Object[]{str_xx, "jBbvMXo9zi9oRXBPRtNtH1dAfxY44HA0"}, "a");
        String s = CMSform("com.mymoney.sms", "cn.com.chinatelecom.account.lib.b.g", context, new Class[]{String.class, String.class, String.class, String.class, String.class, String.class}, new Object[]{str4, str, str2, str3, a, str5}, "a");

        Log.e("wodelog", "paras:" + a);
        Log.e("wodelog", "sign:" + s);


        //   String a = a(map, str5);

        // String a2 = a(str4, str, str2, str3, a, str5);
        return "";
    }

    public static String CMSform(String pack, String classZ, Context thisatv, Class<?>[] pamaer_type, Object[] pamaer, String Method_name) {
        String ret = null;
        try {
            Context c = thisatv.createPackageContext(pack, Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
            Class<?> clazz = c.getClassLoader().loadClass(classZ);
            Method method = clazz.getMethod(Method_name, pamaer_type);
            ret = (String) method.invoke(clazz, pamaer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


    public static String MyMD5(String str) {
        MessageDigest instance;
        int i = 0;
        byte[] bytes = str.getBytes();
        char[] cArr = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            instance = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            instance = null;
        }
        if (instance == null) {
            return null;
        }
        instance.update(bytes);
        byte[] digest = instance.digest();
        char[] cArr2 = new char[32];
        for (int i2 = 0; i2 < 16; i2++) {
            byte b = digest[i2];
            int i3 = i + 1;
            cArr2[i] = cArr[(b >>> 4) & 15];
            i = i3 + 1;
            cArr2[i3] = cArr[b & 15];
        }
        return new String(cArr2);
    }


}
