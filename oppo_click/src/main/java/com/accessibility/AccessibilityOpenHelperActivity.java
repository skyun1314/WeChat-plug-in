package com.accessibility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.accessibility.utils.AccessibilityUtil;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 辅助功能权限打开辅助activity，用于启动辅助功能设置页面
 */
public class AccessibilityOpenHelperActivity extends Activity {
    private boolean isFirstCome = true;
    private static final String ACTION = "action";
    private static final String ACTION_FINISH_SELF = "action_finis_self";

    private Timer timer;
    private TimerTask timerTask;
    private int mTimeoutCounter = 0;

    private int TIMEOUT_MAX_INTERVAL = 60 * 2; // 2 min

    private long TIMER_CHECK_INTERVAL = 1000;
    protected static Handler mHandle = new Handler();
    protected static Runnable tipToastDelayRunnable;

    private static void removeDelayedToastTask() {
        if (mHandle != null && tipToastDelayRunnable != null) {
            mHandle.removeCallbacks(tipToastDelayRunnable);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility_transparent_layout);
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            String action = intent.getStringExtra(ACTION);
            if (ACTION_FINISH_SELF.equals(action)) {
                finishCurrentActivity();
                return;
            }
        }
        mTimeoutCounter = 0;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishCurrentActivity();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.getExtras() != null) {
            String action = intent.getStringExtra(ACTION);
            if (ACTION_FINISH_SELF.equals(action)) {
                finishCurrentActivity();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirstCome) {
            removeDelayedToastTask();
            finishCurrentActivity();
        } else {
            jumpActivities();
            startCheckAccessibilityOpen();
        }
        isFirstCome = false;
    }

    private void jumpActivities() {
        try {
            Intent intent = AccessibilityUtil.getAccessibilitySettingPageIntent(this);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        freeTimeTask();
        super.onDestroy();
    }

    private void finishCurrentActivity() {
        freeTimeTask();
        finish();
    }

    private void startCheckAccessibilityOpen() {
        freeTimeTask();
        initTimeTask();
        timer.schedule(timerTask, 0, TIMER_CHECK_INTERVAL);
    }

    private void initTimeTask() {
        timer = new Timer();
        mTimeoutCounter = 0;
        timerTask = new TimerTask() {

            @SuppressWarnings("static-access")
            @Override
            public void run() {
                if (AccessibilityUtil.isAccessibilitySettingsOn(AccessibilityOpenHelperActivity.this)) {
                    freeTimeTask();
                    Looper.prepare();
                    try {
                        AccessibilityOpenHelperActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AccessibilityOpenHelperActivity.this, "辅助功能开启成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Intent intent = new Intent();
                        intent.putExtra(ACTION, ACTION_FINISH_SELF);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setClass(AccessibilityOpenHelperActivity.this, AccessibilityOpenHelperActivity.this.getClass());
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Looper.loop();
                }

                //超过2分钟超时，就释放timer。
                //解决： 可能有这样的一个场景，用户打开红包辅助功能，不勾选“猎豹安全大师”就退出到其他地方
                //      然后再另外一个场景又去勾选辅助功能，这个时候还是弹出红包场景，用户体验就很怪了
                mTimeoutCounter++;
                if (mTimeoutCounter > TIMEOUT_MAX_INTERVAL) {
                    freeTimeTask();
                }
            }
        };
    }

    private void freeTimeTask() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
