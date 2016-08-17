package com.mirzaakhena.batchsystem.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Tools {

	public static SimpleDateFormat DDMMYYYY = new SimpleDateFormat("dd-MM-yyyy");

	public static SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");

	public static String[] validateDate(String[] date) throws Exception {
		if (date.length == 0) {
			throw new Exception("Date parameter must exist");
		}
		if (date.length > 2) {
			throw new Exception("Date parameter must less than 2");
		}
		if (date.length == 1) {
			return new String[] { date[0], date[0] };
		}
		if (date.length == 2) {
			return new String[] { date[0], date[1] };
		}
		return null;
	}

	public static Date getDDMMYYYYComplete(Date date) {
		Calendar calendarDate = Calendar.getInstance();
		calendarDate.setTime(date);
		
		Calendar calendarNow = Calendar.getInstance();
		calendarNow.setTime(new Date());
		
		calendarNow.set(calendarDate.get(Calendar.YEAR), calendarDate.get(Calendar.MONTH), calendarDate.get(Calendar.DATE));
		
		return calendarNow.getTime();
	}
	
}
