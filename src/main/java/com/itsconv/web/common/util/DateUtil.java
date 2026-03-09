package com.itsconv.web.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static String DATE_FORMAT = "yyyyMMdd";
	private static DateFormat DEFAULT_DATEFORMAT = new SimpleDateFormat(DATE_FORMAT);

	/**
	 * Date타입의 인스턴스로 부터 지정 포멧의 날짜를 반환한다. 
	 * 
	 * @param date Date타입의 인스턴스
	 * @param format 지정 날짜 포멧
	 * @return 날짜형식의 문자열
	 */
	public static String toString(Date date, String format) {
		DateFormat dateFormat = DEFAULT_DATEFORMAT;
		if (format != null && !DATE_FORMAT.equals(format)) {
			dateFormat = new SimpleDateFormat(format);
		}
		return dateFormat.format(date);
	}
}
