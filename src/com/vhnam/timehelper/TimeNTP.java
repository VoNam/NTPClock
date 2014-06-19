package com.vhnam.timehelper;

import java.util.Date;
import java.util.Observable;

import android.text.format.DateUtils;

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
	
	public void updateFromNTPServer() {
		
	}
}
