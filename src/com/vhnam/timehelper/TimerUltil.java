package com.vhnam.timehelper;

import java.util.Observable;

import android.content.Context;
import android.text.format.DateUtils;

public class TimerUltil extends Observable {
	private static TimerUltil mTimerUltil = new TimerUltil();
	public static long SCHEDULE_UPDATE = 5 * DateUtils.SECOND_IN_MILLIS;// 10*DateUtils.MINUTE_IN_MILLIS;
	private Thread mTimerThread = null;

	public static TimerUltil getInstance() {
		return mTimerUltil;
	}

	public void setTimer(final long time, final Context context) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(DateUtils.SECOND_IN_MILLIS);
					long timeAfterSecond = time - DateUtils.SECOND_IN_MILLIS;

					setChanged();
					notifyObservers(timeAfterSecond);

					if (timeAfterSecond == 0) {
						setTimer(SCHEDULE_UPDATE + DateUtils.SECOND_IN_MILLIS, context);
						TimeNTP.getInstance().updateTime(context);
					} else {
						setTimer(timeAfterSecond, context);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		if (mTimerThread == null) {
			mTimerThread = new Thread(runnable);
			
		} else {
			if (mTimerThread.isAlive()) {
				mTimerThread.interrupt();
				mTimerThread = new Thread(runnable);
			}
		}		
		mTimerThread.start();
	}
}
