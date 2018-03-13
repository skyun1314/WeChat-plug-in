package me.iweizi.stepchanger.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by iweiz on 2017/9/5.
 * 计步传感器事件监听类，用于获取当前传感器步数
 */

public class StepCounterSensorListener implements SensorEventListener {

    private static StepCounterSensorListener sStepCounterSensorListener;
    private final SensorManager mSensorManager;
    private int mStep;

    private StepCounterSensorListener(Context context) {
        mStep = -1;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public static StepCounterSensorListener get(Context context) {
        if (sStepCounterSensorListener == null) {
            sStepCounterSensorListener = new StepCounterSensorListener(context);
        }
        return sStepCounterSensorListener;
    }

    public void register() {
        if (mSensorManager == null) {
            return;
        }
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister() {
        if (mSensorManager == null) {
            return;
        }
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        mStep = (int) event.values[0];
    }

    public int getStep() {
        return mStep;
    }
}
