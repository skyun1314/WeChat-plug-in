package com.android.wechat;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;
import android.util.SparseArray;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by zk on 2018/3/13.
 */

public class SportEdit {
    private static int zhifubaoCount = 0;
    private static final String WEXIN = "com.tencent.mm";
    private static final String QQ = "com.tencent.mobileqq";
    private static final String ZHIFUBAO = "com.eg.android.AlipayGphone";
    private static Object sObject;
    private static int  max = 88888,m=100;
    private static boolean isAuto=true;

    public static void Sprot(final XC_LoadPackage.LoadPackageParam loadPackageParam){
        if (loadPackageParam.packageName.equals(ZHIFUBAO)  || loadPackageParam.packageName.equals(WEXIN) || loadPackageParam.packageName.equals(QQ) ) {
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
                        Log.e(Main.wodetag,"传感器为NULL,暴力设置步数");
                        return;
                    }else{

                    }
                    if (ss.getType() == Sensor.TYPE_ACCELEROMETER) {

                        if (loadPackageParam.packageName.equals(ZHIFUBAO)) {
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
                    }
                    if (ss.getType() == Sensor.TYPE_STEP_COUNTER || ss.getType() == Sensor.TYPE_STEP_DETECTOR) {
                        if ((loadPackageParam.packageName.equals(WEXIN))||( loadPackageParam.packageName.equals(QQ))||loadPackageParam.packageName.equals(ZHIFUBAO)) {
                            if (isAuto) {
                                if (m * zhifubaoCount <= max) {
                                    ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] + m * zhifubaoCount;
                                    zhifubaoCount += 1;
                                } else {
                                    zhifubaoCount = 0;
                                }
                            } else {
                                ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * m;
                            }
                        }


                        Log.e(Main.wodetag,loadPackageParam.packageName + "传感器类型：" + ss.getType() + ",修改后：" + ((float[]) param.args[1])[0]);
                    }
                }
            });
        }
    }
    }

