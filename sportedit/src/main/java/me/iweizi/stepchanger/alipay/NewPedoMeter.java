package me.iweizi.stepchanger.alipay;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;
import me.iweizi.stepchanger.R;
import me.iweizi.stepchanger.StepData;
import me.iweizi.stepchanger.utils.StepCounterSensorListener;
import me.iweizi.stepchanger.utils.Utils;

/**
 * Created by iweiz on 2017/9/5.
 * 支付宝的步数数据类
 */

public class NewPedoMeter extends StepData {
    @SuppressLint("SdCardPath")
    private static final String ALIPAY_SP_DIR =
            "/data/data/com.eg.android.AlipayGphone/shared_prefs/";
    @SuppressLint("SdCardPath")
    private static final String SP_DIR =
            "/data/data/me.iweizi.stepchanger/shared_prefs/";
    private static final String NEW_PEDOMETER = "NewPedoMeter.xml";
    private static final String NEW_PEDOMETER_PRIVATE = "NewPedoMeter_private.xml";

    private static final String ALIPAY = "com.eg.android.AlipayGphone";
    private static final String ALIPAY_EXT = "com.eg.android.AlipayGphone:ext";
    private static final NewPedoMeter sNewPedoMeter = new NewPedoMeter();
    private final String mNewPedometer;
    private final String mNewPedometerPrivate;
    private final String mAlipayNewPedometer;
    private final String mAlipayNewPedometerPrivate;
    private final File mNewPedometerFile;
    private final File mNewPedometerPrivateFile;
    private SharedPreferences mNewPedometerSP;
    private SharedPreferences mNewPedometerPrivateSP;

    private NewPedoMeter() {
        super();
        mNewPedometer = SP_DIR + NEW_PEDOMETER;
        mNewPedometerPrivate = SP_DIR + NEW_PEDOMETER_PRIVATE;
        mAlipayNewPedometer = ALIPAY_SP_DIR + NEW_PEDOMETER;
        mAlipayNewPedometerPrivate = ALIPAY_SP_DIR + NEW_PEDOMETER_PRIVATE;

        mNewPedometerFile = new File(mNewPedometer);
        mNewPedometerPrivateFile = new File(mNewPedometerPrivate);

        File sp_dir = new File(SP_DIR);
        if (!sp_dir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            sp_dir.mkdir();
        }

        try {
            if (!mNewPedometerFile.exists()) mNewPedometerFile.createNewFile();
            if (!mNewPedometerPrivateFile.exists()) mNewPedometerPrivateFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ROOT_CMD = new String[]{
                "chmod g+rw " + mNewPedometer,
                "chmod g+rw " + mNewPedometerPrivate,
        };

        mLoadButtonId = R.id.alipay_load_button;
        mStoreButtonId = R.id.alipay_store_button;
    }

    public static NewPedoMeter get() {
        return sNewPedoMeter;
    }

    @Override
    public int read(Context context) {
        killAlipayProcess(context);
        Shell.SU.run(new String[]{
                "cat " + mAlipayNewPedometerPrivate + " > " + mNewPedometerPrivate,
                "cat " + mAlipayNewPedometer + " > " + mNewPedometer
        });
        mNewPedometerSP = context.getSharedPreferences("NewPedoMeter", Context.MODE_MULTI_PROCESS);
        mNewPedometerPrivateSP = context.getSharedPreferences("NewPedoMeter_private", Context.MODE_MULTI_PROCESS);
        return SUCCESS;
    }

    @Override
    public int write(Context context) {
        killAlipayProcess(context);
        ArrayList<APStepInfo> newStepRecord = new ArrayList<APStepInfo>();
        APStepInfo newLastRecord = new APStepInfo();
        long stepDiff = getStep() - getLastUploadStep();
        long sensorStep = StepCounterSensorListener.get(context).getStep();
        long lastUploadTime = getLastUploadTime();
        long beginOfToday = Utils.beginOfToday();
        if (lastUploadTime < beginOfToday) {
            lastUploadTime = beginOfToday + 1;
            stepDiff = getStep();
        }
        newLastRecord.setSteps((int) (sensorStep - stepDiff));
        newLastRecord.setTime(lastUploadTime);
        newStepRecord.add(newLastRecord);
        saveStepRecord(newStepRecord);
        Shell.SU.run(new String[]{
                "cat " + mNewPedometerPrivate + " > " + mAlipayNewPedometerPrivate
        });
        return SUCCESS;
    }

    private APStepInfo getBaseStep() {
        if (mNewPedometerSP == null) {
            return null;
        }
        String baseStepStr = mNewPedometerSP.getString("baseStep", null);
        if (baseStepStr == null || baseStepStr.isEmpty()) {
            return null;
        }
        return JSON.parseObject(baseStepStr, APStepInfo.class);
    }

    private APStepInfo getLastStepInfoToday() {
        if (mNewPedometerSP == null) {
            return null;
        }
        String lastStepInfoTodayStr = mNewPedometerSP.getString("last_stepinfo_today", null);

        if (lastStepInfoTodayStr == null || lastStepInfoTodayStr.isEmpty()) {
            return null;
        }

        return JSON.parseObject(lastStepInfoTodayStr, APStepInfo.class);
    }

    private List<APStepInfo> getStepRecord() {
        if (mNewPedometerPrivateSP == null) {
            return new ArrayList<>();
        }
        String stepRecordStr = mNewPedometerPrivateSP.getString("stepRecord", null);
        if (stepRecordStr == null) {
            return new ArrayList<>();
        }
        return JSON.parseArray(stepRecordStr, APStepInfo.class);
    }

    @SuppressLint("ApplySharedPref")
    private void saveStepRecord(List<APStepInfo> stepRecord) {
        SharedPreferences.Editor editor = mNewPedometerPrivateSP.edit();
        String stepRecordStr = JSON.toJSON(stepRecord).toString();
        editor.putString("stepRecord", stepRecordStr);
        editor.commit();
    }

    private void killAlipayProcess(Context context) {
        ActivityManager am = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        am.killBackgroundProcesses(ALIPAY);
        am.killBackgroundProcesses(ALIPAY_EXT);
    }

    @Override
    protected boolean canRead() {
        return mNewPedometerFile.canRead() && mNewPedometerPrivateFile.canRead();
    }

    @Override
    protected boolean canWrite() {
        return mNewPedometerFile.canWrite() && mNewPedometerPrivateFile.canWrite();

    }

    @Override
    public long getLastUploadStep() {
        APStepInfo stepInfo = getBaseStep();
        if (stepInfo != null) {
            return stepInfo.getSteps();
        }
        stepInfo = getLastStepInfoToday();
        if (stepInfo != null) {
            return stepInfo.getSteps();
        }
        return -1;
    }

    @Override
    public long getLastUploadTime() {
        APStepInfo stepInfo = getBaseStep();
        if (stepInfo != null) {
            return stepInfo.getTime();
        }
        stepInfo = getLastStepInfoToday();
        if (stepInfo != null) {
            return stepInfo.getTime();
        }
        return -1;
    }

    private APStepInfo getLastRecord() {
        List<APStepInfo> stepRecord = getStepRecord();
        if (stepRecord.isEmpty()) {
            return null;
        } else {
            return stepRecord.get(stepRecord.size() - 1);
        }
    }

    @Override
    public long getLastSaveTime() {
        APStepInfo lastRecord = getLastRecord();
        if (lastRecord != null) {
            return lastRecord.getTime();
        } else {
            return -1;
        }
    }

    @Override
    public long getLastSaveSensorStep() {
        APStepInfo lastRecord = getLastRecord();
        if (lastRecord != null) {
            return lastRecord.getSteps();
        } else {
            return -1;
        }
    }

    @Override
    public boolean isLoaded() {
        return mNewPedometerSP != null && mNewPedometerPrivateSP != null;
    }
}
