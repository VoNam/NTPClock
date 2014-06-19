package com.vhnam.timehelper;

import java.util.Date;
import java.util.Observable;

import com.vhnam.network.NetworkUtil;

import android.content.Context;
import android.text.format.DateUtils;
import android.widget.Toast;

public class TimeNTP extends Observable {
	private static TimeNTP mTimeNTP =  new TimeNTP();
	
	public static TimeNTP getInstance() {
		return mTimeNTP;
	}
	
	public void updateSecond(final Date date) {
		Runnable runnable = new Runnable() {			
			@Override
			public void run() {
				Date d = date;
				d.setTime(d.getTime() + DateUtils.SECOND_IN_MILLIS);
				
				setChanged();
				notifyObservers(d);		
				
				try {
					Thread.sleep(DateUtils.SECOND_IN_MILLIS);
					updateSecond(d);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(runnable).start();
	}
	
	public Date getDate(Context context) {
		if (NetworkUtil.isConnected(context)) {
			Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
			return new Date(System.currentTimeMillis());
		} else {
			return new Date(System.currentTimeMillis());
		}
	}
	public void updateFromNTPServer() {
		
	}
}
