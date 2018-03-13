package me.iweizi.stepchanger;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import eu.chainfire.libsuperuser.Shell;
import me.iweizi.stepchanger.utils.Utils;

/**
 * Created by iweiz on 2017/9/5.
 * 步数数据父类
 */

public class StepData {

    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    public static final int ROOTING = 2;
    public static final int CANCEL = 3;
    public static final int NEED_LOAD = 4;

    private static final int TOO_FAST = 5;
    private static final int TOO_MANY = 6;
    private static final int TOO_MANY_FAST = 7;

    private static final double MAX_STEP_PER_SEC = 5.0;
    private static final int MAX_STEP = 80000;
    protected String[] ROOT_CMD;
    protected int mLoadButtonId;
    protected int mStoreButtonId;
    private long mStep;
    private boolean isChecked;
    private boolean isContinue;

    protected StepData() {
        mStep = -1;
        isChecked = false;
        isContinue = true;
    }

    public final long getStep() {
        return mStep;
    }

    public final void setStep(long step) {
        mStep = step;
    }

    public final void changeStep(long diff) {
        mStep = mStep + diff;
        if (mStep < 0) {
            mStep = 0;
        }
    }

    private int check() {
        long current = System.currentTimeMillis();
        long lastUploadTime = getLastUploadTime();
        long step = getStep();
        long stepDiff = step - getLastUploadStep();
        long beginOfToday = Utils.beginOfToday();
        if (lastUploadTime < beginOfToday) {
            lastUploadTime = beginOfToday;
            stepDiff = step;
        }
        double timeDiff = (current - lastUploadTime) / 1000;
        double stepPerSec = stepDiff / timeDiff;
        if (step > MAX_STEP && stepPerSec > MAX_STEP_PER_SEC) {
            return TOO_MANY_FAST;
        } else if (step > MAX_STEP) {
            return TOO_MANY;
        } else if (stepPerSec > MAX_STEP_PER_SEC) {
            return TOO_FAST;
        }
        return SUCCESS;
    }

    public final int load(final Context context) {
        int readResult;
        if (!canRead()) {
            (new RootTask(context)).execute(true);
            return ROOTING;
        }

        readResult = read(context);
        mStep = getLastUploadStep();
        return readResult;
    }

    public final int store(final Context context) {
        int checkResult;
        int writeResult;
        if (!isLoaded()) {
            return NEED_LOAD;
        }

        if (!isChecked) {
            checkResult = check();
            isChecked = true;
            if (checkResult != SUCCESS) {
                String message;
                switch (checkResult) {
                    case TOO_FAST:
                        message = context.getString(R.string.too_fast);
                        break;
                    case TOO_MANY:
                        message = context.getString(R.string.too_many);
                        break;
                    case TOO_MANY_FAST:
                        message = context.getString(R.string.too_many_fast);
                        break;
                    default:
                        message = "";
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle(R.string.note)
                        .setMessage(message + context.getString(R.string.is_continue))
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                isContinue = true;
                                ((Activity) context).findViewById(mStoreButtonId).performClick();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                isContinue = false;
                                ((Activity) context).findViewById(mStoreButtonId).performClick();
                            }
                        });
                builder.create().show();
                return checkResult;
            }
        }

        if (!isContinue) {
            isContinue = true;
            isChecked = false;
            return CANCEL;
        }

        if (!canWrite()) {
            (new RootTask(context)).execute(false);
            return ROOTING;
        }

        writeResult = write(context);
        isChecked = false;
        isContinue = true;
        return writeResult;
    }

    // 子类应该实现
    protected boolean canRead() {
        return false;
    }

    // 子类应该实现
    protected boolean canWrite() {
        return false;
    }

    // 子类应该实现
    protected int read(Context context) {
        return FAIL;
    }

    // 子类应该实现
    protected int write(Context context) {
        return FAIL;
    }

    // 子类应该实现
    public long getLastUploadTime() {
        return -1;
    }

    // 子类应该实现
    public long getLastUploadStep() {
        return -1;
    }

    // 子类应该实现
    public long getLastSaveTime() {
        return -1;
    }

    // 子类应该实现
    public long getLastSaveSensorStep() {
        return -1;
    }

    // 子类应该实现
    public boolean isLoaded() {
        return false;
    }

    class RootTask extends AsyncTask<Boolean, Void, Void> {
        private Context mContext = null;
        private boolean mSuAvailable;
        @SuppressWarnings("deprecation")
        private ProgressDialog mProgressDialog = null;
        private AlertDialog mAlertDialog = null;
        private boolean isRead;

        public RootTask(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            //noinspection deprecation
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setTitle(R.string.require_access_title);
            mProgressDialog.setMessage(mContext.getString(R.string.require_access_message));
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Boolean... booleans) {
            isRead = booleans[0];
            mSuAvailable = Shell.SU.available();
            if (mSuAvailable) {
                Shell.SU.run(ROOT_CMD);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void results) {
            mProgressDialog.dismiss();

            if (mSuAvailable) {
                if (isRead && canRead()) {
                    try {
                        ((Activity) mContext).findViewById(mLoadButtonId).performClick();
                    } catch (NullPointerException e) {
                        ((Activity) mContext).findViewById(R.id.details_load_button).performClick();
                    }
                } else if (canWrite()) {
                    try {
                        ((Activity) mContext).findViewById(mStoreButtonId).performClick();
                    } catch (NullPointerException e) {
                        ((Activity) mContext).findViewById(R.id.details_store_button).performClick();
                    }
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                        .setTitle(R.string.note)
                        .setMessage(R.string.require_failed_message)
                        .setCancelable(true);
                mAlertDialog = builder.create();
                mAlertDialog.show();
            }
        }
    }
}
