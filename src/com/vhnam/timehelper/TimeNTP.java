package com.vhnam.timehelper;

import java.net.InetAddress;
import java.util.Date;
import java.util.Observable;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import android.content.Context;
import android.text.format.DateUtils;

import com.vhnam.network.NetworkUtil;

public class TimeNTP extends Observable {
	private static TimeNTP timeNTP = new TimeNTP();
	public static final String TIME_SERVER = "time-a.nist.gov";

	public static TimeNTP getInstance() {
		return timeNTP;
	}

	public void updateTime(final Context mContext) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Date d = getDate(mContext);
				d.setTime(d.getTime());

				setChanged();
				notifyObservers(d);
				updateSecond(d);
			}
		};
		new Thread(runnable).start();
	}

	private void updateSecond(final Date date) {
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

	private Date getDate(Context context) {
		if (NetworkUtil.isConnected(context)) {
			long time = getCurrentNetworkTime();
			if (time == 0) {
				return new Date(System.currentTimeMillis());
			}
			return new Date(time);
		} else {
			return new Date(System.currentTimeMillis());
		}
	}

	private long getCurrentNetworkTime() {
		try {
			NTPUDPClient timeClient = new NTPUDPClient();
			InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
			TimeInfo timeInfo = timeClient.getTime(inetAddress);
			long returnTime = timeInfo.getMessage().getTransmitTimeStamp()
					.getTime();

			return returnTime;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return 0;
	}
}
