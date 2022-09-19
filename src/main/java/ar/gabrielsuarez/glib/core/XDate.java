package ar.gabrielsuarez.glib.core;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public abstract class XDate {

	/* ========== FORMAT ========== */
	public static String toString(Date value) {
		String string = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
		if (string.endsWith("00:00:00")) {
			return new SimpleDateFormat("yyyy-MM-dd").format(value);
		}
		return string;
	}

	public static String toString(TemporalAccessor value) {
		String string = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(value);
		if (string.endsWith("00:00:00")) {
			return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(value);
		}
		return string;
	}

	/* ========== FORMAT DATA ========== */
	public static Boolean isDate(String value) {
		return dateFormat(value) != null;
	}

	public static String dateMask(String value) {
		try {
			StringBuilder sb = new StringBuilder();
			for (char character : value.toCharArray()) {
				sb.append(character >= '0' && character <= '9' ? "x" : character);
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public static String dateFormat(String value) {
		try {
			String dateMask = dateMask(value);
			String format = null;
			format = dateMask.startsWith("xxxx-xx-xx") ? "yyyy-MM-dd" : format;
			format = dateMask.startsWith("xxxx-xx-xx xx:xx") ? "yyyy-MM-dd HH:mm" : format;
			format = dateMask.startsWith("xxxx-xx-xxTxx:xx") ? "yyyy-MM-dd'T'HH:mm" : format;
			format = dateMask.startsWith("xxxx-xx-xx xx:xx:xx") ? "yyyy-MM-dd HH:mm:ss" : format;
			format = dateMask.startsWith("xxxx-xx-xxTxx:xx:xx") ? "yyyy-MM-dd'T'HH:mm:ss" : format;
			return format;
		} catch (Exception e) {
			return null;
		}
	}

	public static Boolean dateFormatContainsTime(String dateFormat) {
		return dateFormat != null && dateFormat.contains("HH:mm");
	}
}
