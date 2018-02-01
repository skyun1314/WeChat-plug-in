package com.accessibility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.accessibility.utils.AccessibilityLog;

public class AccessibilityMainActivity extends Activity implements View.OnClickListener {

    private View mOpenSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility_main);
        initView();
        AccessibilityOperator.getInstance().init(this);
        AccessibilityNormalSample.haha(this);




    }

    private void initView() {
        mOpenSetting = findViewById(R.id.open_accessibility_setting);
        mOpenSetting.setOnClickListener(this);
        findViewById(R.id.accessibility_find_and_click).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.open_accessibility_setting:
                OpenAccessibilitySettingHelper.jumpToSettingPage(this);
                break;
            case R.id.accessibility_find_and_click:
                startActivity(new Intent(this, AccessibilityNormalSample.class));
                break;
        }
    }



   public static Handler handler = new Handler();
    public static  Runnable runnable = new Runnable() {
        @Override
        public void run() {
            boolean result = AccessibilityOperator.getInstance().myclick(969,349);
            Log.e("wodelog",result ? "自定义模拟点击成功" : "自定义开关模拟点击失败");
        }
    };

}
