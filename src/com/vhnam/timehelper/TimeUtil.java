package com.vhnam.timehelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	public static String milisecondToMunite(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
		return sdf.format(new Date(time));
	}
}
