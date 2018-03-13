package me.iweizi.stepchanger.alipay;


import android.os.Bundle;
import android.view.View;

import me.iweizi.stepchanger.BaseFragment;
import me.iweizi.stepchanger.R;

public class AlipayFragment extends BaseFragment {

    public AlipayFragment() {
        mLayoutId = R.layout.fragment_alipay;
        mStepData = NewPedoMeter.get();
        mDetailsClass = AlipayDetailsActivity.class;
    }

    public static AlipayFragment newInstance() {
        AlipayFragment fragment = new AlipayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void findView(View view) {
        mLoadButton = view.findViewById(R.id.alipay_load_button);
        mStoreButton = view.findViewById(R.id.alipay_store_button);
        mDetailsButton = view.findViewById(R.id.alipay_details_button);
        mStepIncImageButton = view.findViewById(R.id.alipay_add_button);
        mStepDecImageButton = view.findViewById(R.id.alipay_sub_button);
        mCurrentTodayStepEditText = view.findViewById(R.id.alipay_current_today_step_edit_text);
    }
}
