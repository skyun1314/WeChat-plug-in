package me.iweizi.stepchanger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class BaseDetailsActivity extends AppCompatActivity {
    protected int mLayoutId;
    protected StepData mStepData;
    private Button mLoadButton;
    private Button mBackButton;
    private Button mStoreButton;
    private TextView mLastUploadStepTV;
    private TextView mLastUploadTimeTV;
    private TextView mLastSaveTimeTV;
    private TextView mLastSaveSensorStepTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mLayoutId);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findView();
        setListener();

    }

    private void findView() {
        mLastUploadStepTV = (TextView) findViewById(R.id.last_upload_step_text_view);
        mLastUploadTimeTV = (TextView) findViewById(R.id.last_upload_time_text_view);
        mLastSaveTimeTV = (TextView) findViewById(R.id.last_save_time_text_view);
        mLastSaveSensorStepTV = (TextView) findViewById(R.id.last_save_sensor_step_text_view);

        mLoadButton = (Button) findViewById(R.id.details_load_button);
        mBackButton = (Button) findViewById(R.id.details_back_button);
        mStoreButton = (Button) findViewById(R.id.details_store_button);
    }

    private void setListener() {
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = mStepData.load(BaseDetailsActivity.this);
                if (result == StepData.SUCCESS) {
                    updateUI();
                    Toast.makeText(BaseDetailsActivity.this, R.string.loaded, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = mStepData.store(BaseDetailsActivity.this);
                if (result == StepData.SUCCESS) {
                    Toast.makeText(BaseDetailsActivity.this, R.string.stored, Toast.LENGTH_SHORT).show();
                } else if (result == StepData.CANCEL) {
                    Toast.makeText(BaseDetailsActivity.this, R.string.canceled, Toast.LENGTH_SHORT).show();
                } else if (result == StepData.NEED_LOAD) {
                    Toast.makeText(BaseDetailsActivity.this, R.string.need_load, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        if (mStepData.isLoaded()) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss.SSS", Locale.CHINA);
            Date date = new Date();
            mLastUploadStepTV.setText(String.valueOf(mStepData.getLastUploadStep()));
            date.setTime(mStepData.getLastUploadTime());
            mLastUploadTimeTV.setText(dateFormat.format(date));
            long lastSaveTime = mStepData.getLastSaveTime();
            if (lastSaveTime != -1) {
                date.setTime(mStepData.getLastSaveTime());
                mLastSaveTimeTV.setText(dateFormat.format(date));
            }
            mLastSaveSensorStepTV.setText(String.valueOf(mStepData.getLastSaveSensorStep()));
        }
    }
}
