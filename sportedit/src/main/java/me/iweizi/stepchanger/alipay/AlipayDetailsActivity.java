package me.iweizi.stepchanger.alipay;

import android.os.Bundle;

import me.iweizi.stepchanger.BaseDetailsActivity;
import me.iweizi.stepchanger.R;

public class AlipayDetailsActivity extends BaseDetailsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_alipay_details;
        mStepData = NewPedoMeter.get();
        super.onCreate(savedInstanceState);
    }
}
