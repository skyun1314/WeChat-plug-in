package me.iweizi.stepchanger.qq;

import android.os.Bundle;

import me.iweizi.stepchanger.BaseDetailsActivity;
import me.iweizi.stepchanger.R;

public class QQDetailsActivity extends BaseDetailsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_qq_details;
        mStepData = StepInfo.get(this);
        super.onCreate(savedInstanceState);
    }
}
