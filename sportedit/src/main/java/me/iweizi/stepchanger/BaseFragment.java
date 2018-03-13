package me.iweizi.stepchanger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by iweiz on 2017/9/5.
 * BaseFragment是 WechatFragment和 AlipayFragment的父类
 */

public class BaseFragment extends Fragment {

    private static final int COUNTER_DIFF = 1000;

    protected Button mLoadButton;
    protected Button mStoreButton;
    protected Button mDetailsButton;

    protected EditText mCurrentTodayStepEditText;
    protected ImageButton mStepIncImageButton;
    protected ImageButton mStepDecImageButton;

    protected int mLayoutId;
    protected StepData mStepData;
    protected Class mDetailsClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(mLayoutId, container, false);
        findView(view);
        setListener();

        return view;
    }

    private void setListener() {

        mStepIncImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStepData.changeStep(COUNTER_DIFF);
                updateUI();
            }
        });

        mStepDecImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStepData.changeStep(-COUNTER_DIFF);
                updateUI();
            }
        });

        mCurrentTodayStepEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                mStepData.setStep(Integer.valueOf(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = mStepData.load(getActivity());
                if (result == StepData.SUCCESS) {
                    updateUI();
                    Toast.makeText(getActivity(), R.string.loaded, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = mStepData.store(getActivity());
                if (result == StepData.SUCCESS) {
                    Toast.makeText(getActivity(), R.string.stored, Toast.LENGTH_SHORT).show();
                } else if (result == StepData.CANCEL) {
                    Toast.makeText(getActivity(), R.string.canceled, Toast.LENGTH_SHORT).show();
                } else if (result == StepData.NEED_LOAD) {
                    Toast.makeText(getContext(), R.string.need_load, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDetailsClass != null) {
                    Intent intent = new Intent(getActivity(), mDetailsClass);
                    startActivity(intent);
                }
            }
        });
    }

    // 子类应该实现
    protected void findView(View view) {
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        if (mStepData.getStep() >= 0) {
            mCurrentTodayStepEditText.setText(String.valueOf(mStepData.getStep()));
        }
    }
}
