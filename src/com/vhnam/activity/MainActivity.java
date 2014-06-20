package com.vhnam.activity;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import com.vhnam.ntpclock.R;
import com.vhnam.timehelper.TimeNTP;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements Observer, OnClickListener {
	private TextView mTxtDate;
	private Button mBtnUpdateTime;
	private Handler mHandler = new Handler();
	private Context mContext;
	private TimeNTP timeNTP;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		mContext = this;

		mTxtDate = (TextView) findViewById(R.id.txtDate);

		mBtnUpdateTime = (Button) findViewById(R.id.btnUpdateTime);
		mBtnUpdateTime.setOnClickListener(this);
		mBtnUpdateTime.setEnabled(false);
		
		timeNTP = TimeNTP.getInstance();
		timeNTP.addObserver(this);
		timeNTP.updateTime(mContext);
	}

	@Override
	public void update(Observable observable, final Object data) {
		if (observable instanceof TimeNTP) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					mTxtDate.setText(((Date) data).toString());
					mBtnUpdateTime.setEnabled(true);
				}
			});
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnUpdateTime:
			mBtnUpdateTime.setEnabled(false);
			mTxtDate.setText(getString(R.string.load_time));
			timeNTP.updateTime(mContext);
			break;
		}
	}
}