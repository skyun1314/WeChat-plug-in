package me.iweizi.stepchanger.wechat;

import android.os.Bundle;

import me.iweizi.stepchanger.BaseDetailsActivity;
import me.iweizi.stepchanger.R;

public class WechatDetailsActivity extends BaseDetailsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_wechat_details;
        mStepData = StepCounterCfg.get();
        super.onCreate(savedInstanceState);
    }
}
