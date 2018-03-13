package me.iweizi.stepchanger.qq;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.util.Enumeration;

import me.iweizi.stepchanger.R;
import me.iweizi.stepchanger.StepData;
import me.iweizi.stepchanger.utils.Utils;

/**
 * Created by iweiz on 2017/9/8.
 * 用于读写/data/data/com.tencent.mobileqq/files/step.info
 */

public class StepInfo extends StepData {

    private static StepInfo sStepInfo = null;
    private static final String STEP_INFO = "/data/data/com.tencent.mobileqq/files/step.info";
    private static final String STEP_SIGN_INFO = "/data/data/com.tencent.mobileqq/files/stepSign.info";
    private static final String sKey = "4eY#X@~g.+U)2%$<";
    private static final String QQ = "com.tencent.mobileqq";
    private static final String QQ_MSF = "com.tencent.mobileqq:MSF";
    private static final String KEYSTORE_DIR = "/data/misc/keystore/user_0/";
    private static final int MAX_SUPPORT_VERSION_CODE = 730;

    private static final File sStepInfoFile = new File(STEP_INFO);
    private static final File sStepSignInfoFile = new File(STEP_SIGN_INFO);
    private JSONObject mStepInfo;
    private Cryptor mCryptor;

    private StepInfo(Context context) {
        if (checkVersion(context)) {
            ROOT_CMD = new String[]{
                    "chmod o+rw " + STEP_INFO
            };
        } else {
            /* 复制key */
            int qq_uid = getUID("com.tencent.mobileqq", context);
            int my_uid = getUID("me.iweizi.stepchanger", context);
            ROOT_CMD = new String[]{
                    "chmod o+rw " + STEP_INFO,
                    "chmod o+rw " + STEP_SIGN_INFO,
                    "cp " + KEYSTORE_DIR + String.valueOf(qq_uid) + "_USRCERT_step_info "
                            + KEYSTORE_DIR + String.valueOf(my_uid) + "_USRCERT_step_info",
                    "cp " + KEYSTORE_DIR + String.valueOf(qq_uid) + "_USRPKEY_step_info "
                            + KEYSTORE_DIR + String.valueOf(my_uid) + "_USRPKEY_step_info",
                    "chown keystore:keystore " + KEYSTORE_DIR + String.valueOf(my_uid) + "_USRCERT_step_info",
                    "chown keystore:keystore " + KEYSTORE_DIR + String.valueOf(my_uid) + "_USRPKEY_step_info"
            };
        }

        mCryptor = new Cryptor(sKey.getBytes());
        mStepInfo = null;
        mLoadButtonId = R.id.qq_load_button;
        mStoreButtonId = R.id.qq_store_button;
    }

    public static StepInfo get(Context context) {
        if (sStepInfo == null)
            sStepInfo = new StepInfo(context);
        return sStepInfo;
    }

    @Override
    protected boolean canRead() {
        return sStepInfoFile.canRead();
    }

    @Override
    protected boolean canWrite() {
        return sStepInfoFile.canWrite();
    }

    @Override
    protected int read(Context context) {
        killQQProcess(context);
        RandomAccessFile raf;
        byte[] ciphertext;
        byte[] plaintext;
        try {
            if(checkVersion(context)) {
                raf = new RandomAccessFile(STEP_INFO, "r");
                ciphertext = new byte[(int) raf.length()];
                raf.read(ciphertext);
                plaintext = mCryptor.decrypt(ciphertext);
                mStepInfo = new JSONObject(new String(plaintext));
                raf.close();
            } else {
                raf = new RandomAccessFile(STEP_INFO, "r");
                plaintext = new byte[(int) raf.length()];
                raf.read(plaintext);
                mStepInfo = new JSONObject(new String(plaintext));
                raf.close();
            }
        } catch (Throwable e) {
            return FAIL;
        }
        setStep(getLastUploadStep());
        return SUCCESS;
    }

    @Override
    protected int write(Context context) {
        killQQProcess(context);
        try {
            if (checkVersion(context)) {
                FileOutputStream outputStream = new FileOutputStream(STEP_INFO);
                String offsetKey = String.valueOf(Utils.beginOfToday()) + "_offset";
                int offset = mStepInfo.getInt(offsetKey);
                offset = offset + (int) (getStep() - getLastUploadStep());
                mStepInfo.put(offsetKey, offset);
                outputStream.write(mCryptor.encrypt(mStepInfo.toString().getBytes()));
                outputStream.close();
            } else {
                FileOutputStream stepOutputStream = new FileOutputStream(STEP_INFO);
                String offsetKey = String.valueOf(Utils.beginOfToday()) + "_offset";
                int offset = mStepInfo.getInt(offsetKey);
                offset = offset + (int) (getStep() - getLastUploadStep());
                mStepInfo.put(offsetKey, offset);
                stepOutputStream.write(mStepInfo.toString().getBytes());
                stepOutputStream.close();

                FileOutputStream stepSignOutputStream = new FileOutputStream(STEP_SIGN_INFO);
                String base64 = Base64.encodeToString(getSign(mStepInfo.toString().getBytes()), 0);
                stepSignOutputStream.write(base64.getBytes());
                stepSignOutputStream.close();
            }
        } catch (Throwable e) {
            return FAIL;
        }
        return SUCCESS;
    }

    @Override
    public long getLastUploadTime() {
        if (mStepInfo != null) {
            try {
                return mStepInfo.getLong("last_report_time");
            } catch (JSONException e) {
                return -1;
            }
        }
        return -1;
    }

    @Override
    public long getLastUploadStep() {
        String beginOfToday = String.valueOf(Utils.beginOfToday());
        if (mStepInfo != null) {
            try {
                if (!mStepInfo.has(beginOfToday+"_total")) {
                    String beginOfYesterday = String.valueOf(Utils.beginOfToday() - 24 * 60 * 60 * 1000);
                    int yesterday_total = mStepInfo.getInt(beginOfYesterday + "_total");
                    mStepInfo.put(beginOfToday + "_total", yesterday_total);
                    mStepInfo.put(beginOfToday + "_init", yesterday_total);
                    mStepInfo.put(beginOfToday + "_offset", 0);
                }

                return (mStepInfo.getInt(beginOfToday + "_total")
                        - mStepInfo.getInt(beginOfToday + "_init")
                        + mStepInfo.getInt(beginOfToday + "_offset"));
            } catch (JSONException e) {
                return -1;
            }
        }
        return -1;
    }

    @Override
    public long getLastSaveTime() {
        return -1;
    }

    @Override
    public long getLastSaveSensorStep() {
        String beginOfToday = String.valueOf(Utils.beginOfToday());
        if (mStepInfo != null) {
            try {
                return mStepInfo.getInt(beginOfToday + "_total");
            } catch (JSONException e) {
                return -1;
            }
        }
        return -1;
    }

    @Override
    public boolean isLoaded() {
        return !(mStepInfo == null);
    }

    private void killQQProcess(Context context) {
        ActivityManager am = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        am.killBackgroundProcesses(QQ);
        am.killBackgroundProcesses(QQ_MSF);
    }

    private boolean checkVersion(Context context) {
        PackageInfo pi = null;
        PackageManager pm = context.getPackageManager();
        try {
            pi = pm.getPackageInfo(QQ, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }

        return pi.versionCode <= MAX_SUPPORT_VERSION_CODE;
    }

    private int getUID(String packageName, Context context) {
        ApplicationInfo ai = null;
        PackageManager pm = context.getPackageManager();
        try {
            ai = pm.getApplicationInfo(packageName, 0);
            return ai.uid;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    private byte[] getSign(byte[] data) {
        try {
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            Enumeration<String> aliases = ks.aliases();
            KeyStore.Entry entry = ks.getEntry("step_info", null);
            if (entry != null) {

                Signature sig = Signature.getInstance("SHA256withRSA");
                sig.initSign(((KeyStore.PrivateKeyEntry)entry).getPrivateKey());
                sig.update(data);
                return sig.sign();
            }
            return null;
        } catch (KeyStoreException e) {
            return null;
        } catch (CertificateException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

}
