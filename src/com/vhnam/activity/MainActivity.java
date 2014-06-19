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
import android.widget.TextView;

public class MainActivity extends Activity implements Observer{
	private TextView mTxtDate;
	private Handler mHandler = new Handler();
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		mContext = this;
		
		mTxtDate = (TextView) findViewById(R.id.txtDate);
		
		TimeNTP timeNTP = TimeNTP.getInstance();
		timeNTP.addObserver(this);
		timeNTP.updateSecond(timeNTP.getDate(mContext));
	}

	@Override
	public void update(Observable observable, final Object data) {
		if (observable instanceof TimeNTP) {
			mHandler.post(new Runnable() {				
				@Override
				public void run() {
					mTxtDate.setText(((Date)data).toString());
				}
			});
		}
	}
}
