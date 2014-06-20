package com.vhnam.activity;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.vhnam.ntpclock.R;
import com.vhnam.timehelper.TimeNTP;
import com.vhnam.timehelper.TimeUtil;
import com.vhnam.timehelper.TimerUltil;

public class MainActivity extends Activity implements Observer, OnClickListener {
	private TextView mTxtDate;
	private TextView mTxtStatus;
	private Button mBtnUpdateTime;
	private Handler mHandler = new Handler();
	private Context mContext;
	private TimeNTP mTimeNTP;
	TimerUltil mTimerUltil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		mContext = this;

		mTxtDate = (TextView) findViewById(R.id.txtDate);
		mTxtStatus = (TextView) findViewById(R.id.txtStatus);

		mBtnUpdateTime = (Button) findViewById(R.id.btnUpdateTime);
		mBtnUpdateTime.setOnClickListener(this);
		mBtnUpdateTime.setEnabled(false);

		mTimeNTP = TimeNTP.getInstance();
		mTimeNTP.addObserver(this);
		mTimeNTP.updateTime(mContext);

		mTimerUltil = TimerUltil.getInstance();
		mTimerUltil.addObserver(this);
		mTimerUltil.setTimer(TimerUltil.SCHEDULE_UPDATE, mContext);

		mTxtStatus.setText(getStatus(TimerUltil.SCHEDULE_UPDATE));
	}

	private String getStatus(long time) {
		return getString(R.string.status) + " "
				+ TimeUtil.milisecondToMunite(time);
	}

	@Override
	public void update(final Observable observable, final Object data) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (observable instanceof TimeNTP) {
					mTxtDate.setText(((Date) data).toString());
					mBtnUpdateTime.setEnabled(true);
				} else if (observable instanceof TimerUltil) {
					mTxtStatus.setText(getStatus((Long) data));
				}

			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnUpdateTime:
			mBtnUpdateTime.setEnabled(false);
			
			mTxtDate.setText(getString(R.string.load_time));
			mTimeNTP.updateTime(mContext);

			mTimerUltil.setTimer(TimerUltil.SCHEDULE_UPDATE, mContext);
			mTxtStatus.setText(getStatus(TimerUltil.SCHEDULE_UPDATE));
			break;
		}
	}
}