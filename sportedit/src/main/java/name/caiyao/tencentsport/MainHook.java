package name.caiyao.tencentsport;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.SparseArray;

import java.lang.reflect.Field;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by 蔡小木 on 2016/2/16 0016.
 */
public class MainHook implements IXposedHookLoadPackage, IXposedHookZygoteInit {
    private static final String WEXIN = "com.tencent.mm";
    private static final String QQ = "com.tencent.mobileqq";
    private static final String ZHIFUBAO = "com.eg.android.AlipayGphone";
    private static final String YUEDONG = "com.yuedong.sport";
    private static final String LEDONG = "cn.ledongli.ldl";
    private static final String PINGAN = "com.pingan.papd";
    private static final String CODOON = "com.codoon.gps";
    private static final String WEIBO = "com.sina.weibo";

    private static int weixinCount = 0, qqCount = 0, ledongCount = 0, yuedongCount = 0, pinganCount = 0, codoonCount = 0, zhifubaoCount = 0;
    private static float count = 0;
    private static boolean isWeixin, isQQ, isAuto, isLedong, isYuedong, isPingan, isCodoon, isWeibo, isAlipay;
    private XSharedPreferences sharedPreferences;
    private static int m, max = 100000;

    private static Object sObject;

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        final Object activityThread = XposedHelpers.callStaticMethod(XposedHelpers.findClass("android.app.ActivityThread", null), "currentActivityThread");
        final Context systemContext = (Context) XposedHelpers.callMethod(activityThread, "getSystemContext");
        IntentFilter intentFilter = new IntentFilter();
        String SETTING_CHANGED = "name.caiyao.tencentsport.SETTING_CHANGED";
        intentFilter.addAction(SETTING_CHANGED);
        systemContext.registerReceiver(new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                isWeixin = intent.getExtras().getBoolean("weixin", true);
                isQQ = intent.getExtras().getBoolean("qq", true);
                m = Integer.valueOf(intent.getExtras().getString("magnification", "100"));
                isAuto = intent.getExtras().getBoolean("autoincrement", false);
                isLedong = intent.getExtras().getBoolean("ledong", true);
                isYuedong = intent.getExtras().getBoolean("yuedong", true);
                isPingan = intent.getExtras().getBoolean("pingan", true);
                isCodoon = intent.getExtras().getBoolean("codoon", true);
                isWeibo = intent.getExtras().getBoolean("weibo", true);
                isAlipay = intent.getExtras().getBoolean("alipay", true);
            }
        }, intentFilter);

        if (loadPackageParam.packageName.equals(YUEDONG) || loadPackageParam.packageName.equals(CODOON)) {
            Thread autoThread = new Thread() {
                @Override
                public void run() {
                    while (!isInterrupted()) {
                        if (isYuedong) {
                            try {
                                Thread.sleep(100);
                                if (sObject != null) {
                                    count++;
                                    XposedHelpers.callMethod(sObject, "dispatchSensorEvent", 5, new float[]{count, 0, 0}, 3, System.currentTimeMillis());
                                }
                                if (count == max) {
                                    count = 0;
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (isCodoon) {
                            try {
                                Thread.sleep(100);
                                if (sObject != null) {
                                    count++;
                                    XposedHelpers.callMethod(sObject, "dispatchSensorEvent", 5, new float[]{count, 0, 0}, 3, System.currentTimeMillis());
                                }
                                if (count == Integer.MAX_VALUE) {
                                    count = 0;
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            };
            autoThread.start();
        }

        if (loadPackageParam.packageName.equals(ZHIFUBAO) || loadPackageParam.packageName.equals(WEIBO) || loadPackageParam.packageName.equals(PINGAN) || loadPackageParam.packageName.equals(WEXIN) || loadPackageParam.packageName.equals(QQ) || loadPackageParam.packageName.equals(LEDONG) || loadPackageParam.packageName.equals(YUEDONG) || loadPackageParam.packageName.equals(CODOON)) {
            getKey();
            final Class<?> sensorEL = XposedHelpers.findClass("android.hardware.SystemSensorManager$SensorEventQueue", loadPackageParam.classLoader);
            XposedBridge.hookAllMethods(sensorEL, "dispatchSensorEvent", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    int handle = (Integer) param.args[0];
                    sObject = param.thisObject;
                    Field field = param.thisObject.getClass().getDeclaredField("mSensorsEvents");
                    field.setAccessible(true);
                    Sensor ss = ((SparseArray<SensorEvent>) field.get(param.thisObject)).get(handle).sensor;
                    if (ss == null) {
                        XposedBridge.log("传感器为NULL");
                        return;
                    }
                    if (ss.getType() == Sensor.TYPE_ACCELEROMETER) {
                        if (isLedong && loadPackageParam.packageName.equals(LEDONG)) {
                            ledongCount += 1;
                            //完美
                            if (ledongCount % 3 == 0) {
                                ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 100;
                                ((float[]) param.args[1])[1] += (float) -10;
                            } else if (ledongCount % 2 == 0) {
                                ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 1000;
                                ((float[]) param.args[1])[2] += (float) -20;
                                ((float[]) param.args[1])[1] += (float) -5;
                            } else {
                                ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 10;
                                ((float[]) param.args[1])[2] += (float) 20;
                                ((float[]) param.args[1])[1] += (float) -15;
                            }
                        }
                        if (isAlipay && loadPackageParam.packageName.equals(ZHIFUBAO)) {
                            zhifubaoCount += 1;
                            //完美
                            if (zhifubaoCount % 3 == 0) {
                                ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 100;
                                ((float[]) param.args[1])[1] += (float) -10;
                            } else if (zhifubaoCount % 2 == 0) {
                                ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 1000;
                                ((float[]) param.args[1])[2] += (float) -20;
                                ((float[]) param.args[1])[1] += (float) -5;
                            } else {
                                ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 10;
                                ((float[]) param.args[1])[2] += (float) 20;
                                ((float[]) param.args[1])[1] += (float) -15;
                            }
                        }
                        if (isYuedong && loadPackageParam.packageName.equals(YUEDONG)) {
                            yuedongCount += 1;
                            ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 1000;
                            if (yuedongCount % 2 == 0) {
                                ((float[]) param.args[1])[2] += (float) -20;
                                ((float[]) param.args[1])[1] += (float) -5;
                            } else {
                                ((float[]) param.args[1])[2] += (float) 20;
                                ((float[]) param.args[1])[1] += (float) -15;
                            }
                        }
                        if (isPingan && loadPackageParam.packageName.equals(PINGAN)) {
                            pinganCount += 1;
                            ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 1000;
                            if (pinganCount % 2 == 0) {
                                ((float[]) param.args[1])[2] += (float) -20;
                                ((float[]) param.args[1])[1] += (float) -5;
                            } else {
                                ((float[]) param.args[1])[2] += (float) 20;
                                ((float[]) param.args[1])[1] += (float) -15;
                            }
                        }
                        if (isCodoon && loadPackageParam.packageName.equals(CODOON)) {
                            codoonCount += 1;
                            ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 1000;
                            if (codoonCount % 2 == 0) {
                                ((float[]) param.args[1])[2] += (float) -20;
                                ((float[]) param.args[1])[1] += (float) -5;
                            } else {
                                ((float[]) param.args[1])[2] += (float) 20;
                                ((float[]) param.args[1])[1] += (float) -15;
                            }
                        }
                    }
                    if (ss.getType() == Sensor.TYPE_STEP_COUNTER || ss.getType() == Sensor.TYPE_STEP_DETECTOR) {
                        if ((isWeixin && loadPackageParam.packageName.equals(WEXIN))) {
                            if (isAuto) {
                                if (m * weixinCount <= max) {
                                    ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] + m * weixinCount;
                                    weixinCount += 1;
                                } else {
                                    weixinCount = 0;
                                }
                            } else {
                                ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * m;
                            }
                        }
                        if ((isQQ && loadPackageParam.packageName.equals(QQ))) {
                            if (isAuto) {
                                if (m * qqCount <= max) {
                                    ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] + m * qqCount;
                                    qqCount += 1;
                                } else {
                                    qqCount = 0;
                                }
                            } else {
                                ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * m;
                            }
                        }
                        if (isAlipay && loadPackageParam.packageName.equals(ZHIFUBAO)) {
                            if (10000 * zhifubaoCount <= max) {
                                ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] + 10000 * zhifubaoCount;
                                zhifubaoCount += 1;
                            } else {
                                zhifubaoCount = 0;
                            }
                        }
                        if ((isWeibo && loadPackageParam.packageName.equals(WEIBO))) {
                            ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * m;
                        }
                        XposedBridge.log(loadPackageParam.packageName + "传感器类型：" + ss.getType() + ",修改后：" + ((float[]) param.args[1])[0]);
                    }
                }
            });
        }
    }

    private void getKey() {
        sharedPreferences.reload();
        isWeixin = sharedPreferences.getBoolean("weixin", true);
        isQQ = sharedPreferences.getBoolean("qq", true);
        m = Integer.valueOf(sharedPreferences.getString("magnification", "100"));
        isAuto = sharedPreferences.getBoolean("autoincrement", false);
        isLedong = sharedPreferences.getBoolean("ledong", true);
        isYuedong = sharedPreferences.getBoolean("yuedong", true);
        isPingan = sharedPreferences.getBoolean("pingan", true);
        isCodoon = sharedPreferences.getBoolean("codoon", true);
        isWeibo = sharedPreferences.getBoolean("weibo", true);
        isAlipay = sharedPreferences.getBoolean("alipay", true);
    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        sharedPreferences = new XSharedPreferences(BuildConfig.APPLICATION_ID);
    }
}
