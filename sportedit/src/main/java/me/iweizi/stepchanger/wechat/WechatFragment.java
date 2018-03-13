package me.iweizi.stepchanger.wechat;

import android.os.Bundle;
import android.view.View;

import me.iweizi.stepchanger.BaseFragment;
import me.iweizi.stepchanger.R;

public class WechatFragment extends BaseFragment {

    public WechatFragment() {
        mLayoutId = R.layout.fragment_wechat;
        mStepData = StepCounterCfg.get();
        mDetailsClass = WechatDetailsActivity.class;
    }

    public static WechatFragment newInstance() {
        WechatFragment fragment = new WechatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void findView(View view) {
        mLoadButton = view.findViewById(R.id.wechat_load_button);
        mStoreButton = view.findViewById(R.id.wechat_store_button);
        mDetailsButton = view.findViewById(R.id.wechat_details_button);
        mStepIncImageButton = view.findViewById(R.id.wechat_add_button);
        mStepDecImageButton = view.findViewById(R.id.wechat_sub_button);
        mCurrentTodayStepEditText = view.findViewById(R.id.wechat_current_today_step_edit_text);
    }
}
