package ar.gabrielsuarez.glib.core;

public abstract class XDate {

	/* ========== FORMAT ========== */
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
			format = dateMask.equals("xxxx-xx-xx") ? "yyyy-MM-dd" : format;
			format = dateMask.equals("xxxx-xx-xxTxx:xx") ? "yyyy-MM-dd'T'HH:mm" : format;
			format = dateMask.equals("xxxx-xx-xx xx:xx") ? "yyyy-MM-dd HH:mm" : format;
			format = dateMask.startsWith("xxxx-xx-xxTxx:xx:xx") ? "yyyy-MM-dd'T'HH:mm:ss" : format;
			format = dateMask.startsWith("xxxx-xx-xx xx:xx:xx") ? "yyyy-MM-dd HH:mm:ss" : format;
			return format;
		} catch (Exception e) {
			return null;
		}
	}
}
