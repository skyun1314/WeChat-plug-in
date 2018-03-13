package me.iweizi.stepchanger.qq;

import android.os.Bundle;
import android.view.View;

import me.iweizi.stepchanger.BaseFragment;
import me.iweizi.stepchanger.R;

public class QQFragment extends BaseFragment {

    public QQFragment() {
        mLayoutId = R.layout.fragment_qq;
        mDetailsClass = QQDetailsActivity.class;
    }

    public static QQFragment newInstance() {
        QQFragment fragment = new QQFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void findView(View view) {
        mStepData = StepInfo.get(getActivity());
        mLoadButton = view.findViewById(R.id.qq_load_button);
        mStoreButton = view.findViewById(R.id.qq_store_button);
        mDetailsButton = view.findViewById(R.id.qq_details_button);
        mStepIncImageButton = view.findViewById(R.id.qq_add_button);
        mStepDecImageButton = view.findViewById(R.id.qq_sub_button);
        mCurrentTodayStepEditText = view.findViewById(R.id.qq_current_today_step_edit_text);
    }
}
