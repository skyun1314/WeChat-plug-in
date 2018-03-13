package me.iweizi.stepchanger.wechat;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import me.iweizi.stepchanger.R;
import me.iweizi.stepchanger.StepData;

/**
 * Created by iweiz on 2017/9/4.
 * 微信的步数数据类
 */

class StepCounterCfg extends StepData {
    private static final int CURRENT_TODAY_STEP = 201;
    private static final int PRE_SENSOR_STEP = 203;
    private static final int LAST_SAVE_STEP_TIME = 204;
    /*
        static final int BEGIN_OF_TODAY = 202;
        static final int REBOOT_TIME = 205;
        static final int SENSOR_TIME_STAMP = 209;
    */
    private static final int LAST_UPLOAD_TIME = 3;
    private static final int LAST_UPLOAD_STEP = 4;

    @SuppressLint("SdCardPath")
    private static final String STEP_COUNTER_CFG = "/data/data/com.tencent.mm/MicroMsg/stepcounter.cfg";
    @SuppressLint("SdCardPath")
    private static final String MM_STEP_COUNTER_CFG = "/data/data/com.tencent.mm/MicroMsg/MM_stepcounter.cfg";
    private static final String WECHAT = "com.tencent.mm";
    private static final String WECHAT_EX = "com.tencent.mm:exdevice";
    private static final StepCounterCfg sStepCounterCfg = new StepCounterCfg();
    private final File mStepCounterCfgFile;
    private final File mMMStepCounterCfgFile;
    private HashMap<Integer, Long> mStepCounterMap = null;
    private HashMap<Integer, ?> mMMStepCounterMap = null;

    private StepCounterCfg() {
        super();

        mStepCounterCfgFile = new File(STEP_COUNTER_CFG);
        mMMStepCounterCfgFile = new File(MM_STEP_COUNTER_CFG);

        ROOT_CMD = new String[]{
                "chmod o+rw " + mStepCounterCfgFile.getAbsolutePath(),
                "chmod o+rw " + mMMStepCounterCfgFile.getAbsolutePath(),
                "chmod o+x " + mStepCounterCfgFile.getParent(),
                "chmod o+x " + mMMStepCounterCfgFile.getParent()
        };
        mLoadButtonId = R.id.wechat_load_button;
        mStoreButtonId = R.id.wechat_store_button;
    }

    static StepCounterCfg get() {
        return sStepCounterCfg;
    }

    @Override
    protected int read(Context context) {
        FileInputStream fis;
        ObjectInputStream ois;

        killWechatProcess(context);
        try {

            fis = new FileInputStream(mStepCounterCfgFile);
            ois = new ObjectInputStream(fis);
            //noinspection unchecked
            mStepCounterMap = (HashMap<Integer, Long>) ois.readObject();
            ois.close();
            fis.close();

            fis = new FileInputStream(mMMStepCounterCfgFile);
            ois = new ObjectInputStream(fis);
            //noinspection unchecked
            mMMStepCounterMap = (HashMap<Integer, ?>) ois.readObject();
            return SUCCESS;
        } catch (Exception e) {
            return FAIL;
        }
    }


    @Override
    protected int write(Context context) {
        FileOutputStream fos;
        ObjectOutputStream oos;

        if (mStepCounterMap == null) {
            return FAIL;
        }
        try {
            killWechatProcess(context);
            fos = new FileOutputStream(STEP_COUNTER_CFG);
            oos = new ObjectOutputStream(fos);
            mStepCounterMap.put(CURRENT_TODAY_STEP, getStep());
            oos.writeObject(mStepCounterMap);
            oos.close();
            fos.close();
            return SUCCESS;
        } catch (Exception e) {
            return FAIL;
        }
    }

    @Override
    protected boolean canRead() {
        return mStepCounterCfgFile.canRead() && mMMStepCounterCfgFile.canRead();
    }

    @Override
    protected boolean canWrite() {
        return mStepCounterCfgFile.canWrite() && mMMStepCounterCfgFile.canWrite();
    }

    @Override
    public long getLastUploadStep() {
        if (mMMStepCounterMap != null) {
            //noinspection unchecked
            return ((HashMap<Integer, Long>) mMMStepCounterMap).get(LAST_UPLOAD_STEP);
        } else {
            return -1;
        }
    }

    @Override
    public long getLastUploadTime() {
        if (mMMStepCounterMap != null) {
            //noinspection unchecked
            return ((HashMap<Integer, Long>) mMMStepCounterMap).get(LAST_UPLOAD_TIME);
        } else {
            return -1;
        }
    }

    @Override
    public long getLastSaveTime() {
        if (mStepCounterMap != null) {
            return mStepCounterMap.get(LAST_SAVE_STEP_TIME);
        } else {
            return -1;
        }
    }

    @Override
    public long getLastSaveSensorStep() {
        if (mStepCounterMap != null) {
            return mStepCounterMap.get(PRE_SENSOR_STEP);
        } else {
            return -1;
        }
    }

    @Override
    public boolean isLoaded() {
        return mMMStepCounterMap != null && mStepCounterMap != null;
    }

    private void killWechatProcess(Context context) {
        ActivityManager am = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        am.killBackgroundProcesses(WECHAT);
        am.killBackgroundProcesses(WECHAT_EX);
    }
}
