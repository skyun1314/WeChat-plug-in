package com.tianyl.android.wechat;

import com.tianyl.android.wechat.sync.UploadService;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        ((Button)findViewById(R.id.btnUpload)).setText("上传新增信息(" + UploadService.findJsonFiles().size() + ")");
		findViewById(R.id.btnUpload).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                Toast.makeText(MainActivity.this,"start upload add msg",Toast.LENGTH_LONG).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UploadService.update();
                    }
                }).start();
            }
		});
        findViewById(R.id.btnUploadDelMsg).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"start upload del msg",Toast.LENGTH_LONG).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UploadService.uploadDelMsg();
                    }
                }).start();
            }
        });
	}
	
}
