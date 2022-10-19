package ar.gabrielsuarez.glib.core;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import ar.gabrielsuarez.glib.G;

public abstract class Convert {

	/* ========== ATTRIBUTES ========== */
	public static Map<Class<?>, Function<Object[], ?>> castMap = new HashMap<>();

	/* ========== INIT ========== */
	static {
		castMap.put(String.class, values -> toString(values));
		castMap.put(Boolean.class, values -> toBoolean(values));
		castMap.put(Short.class, values -> toShort(values));
		castMap.put(Integer.class, values -> toInteger(values));
		castMap.put(Long.class, values -> toLong(values));
		castMap.put(Float.class, values -> toFloat(values));
		castMap.put(Double.class, values -> toDouble(values));
		castMap.put(BigInteger.class, values -> toBigInteger(values));
		castMap.put(BigDecimal.class, values -> toBigDecimal(values));
		castMap.put(Date.class, values -> toDate(values));
		castMap.put(java.sql.Date.class, values -> toSqlDate(values));
		castMap.put(LocalDate.class, values -> toLocalDate(values));
		castMap.put(LocalDateTime.class, values -> toLocalDateTime(values));
	}

	/* ========== CONVERT ========== */
	@SuppressWarnings("unchecked")
	public static <T> T cast(Class<T> type, Object... values) {
		Function<Object[], ?> function = castMap.get(type);
		if (function != null) {
			return (T) function.apply(values);
		}
		return null;
	}

	/* ========== STRING ========== */
	public static String toString(Object... values) {
		return convert(values, String.class, x -> x.toString());
	}

	/* ========== BOOLEAN ========== */
	public static Boolean toBoolean(Object... values) {
		return convert(values, Boolean.class, x -> {
			String string = x.toString().toLowerCase();
			Boolean data = null;
			data = (data == null && "true".equals(string)) ? Boolean.TRUE : data;
			data = (data == null && "y".equals(string)) ? Boolean.TRUE : data;
			data = (data == null && "s".equals(string)) ? Boolean.TRUE : data;
			data = (data == null && "1".equals(string)) ? Boolean.TRUE : data;
			data = (data == null && "false".equals(string)) ? Boolean.FALSE : data;
			data = (data == null && "n".equals(string)) ? Boolean.FALSE : data;
			data = (data == null && "0".equals(string)) ? Boolean.FALSE : data;
			return data;
		});
	}

	/* ========== SHORT ========== */
	public static Short toShort(Object... values) {
		return convert(values, Short.class, x -> Short.valueOf(x.toString()));
	}

	/* ========== INTEGER ========== */
	public static Integer toInteger(Object... values) {
		return convert(values, Integer.class, x -> Integer.valueOf(x.toString()));
	}

	/* ========== LONG ========== */
	public static Long toLong(Object... values) {
		return convert(values, Long.class, x -> Long.valueOf(x.toString()));
	}

	/* ========== FLOAT ========== */
	public static Float toFloat(Object... values) {
		return convert(values, Float.class, x -> Float.valueOf(x.toString()));
	}

	/* ========== DOUBLE ========== */
	public static Double toDouble(Object... values) {
		return convert(values, Double.class, x -> Double.valueOf(x.toString()));
	}

	/* ========== BIGINTEGER ========== */
	public static BigInteger toBigInteger(Object... values) {
		return convert(values, BigInteger.class, x -> new BigInteger(x.toString()));
	}

	/* ========== BIGDECIMAL ========== */
	public static BigDecimal toBigDecimal(Object... values) {
		return convert(values, BigDecimal.class, x -> new BigDecimal(x.toString()));
	}

	/* ========== DATE ========== */
	public static Date toDate(Object... values) {
		return convert(values, Date.class, x -> {
			if (x instanceof Date) {
				return (Date) x;
			}
			if (x instanceof TemporalAccessor) {
				return Date.from(Instant.from((TemporalAccessor) x).atZone(ZoneId.systemDefault()).toInstant());
			}
			if (x instanceof String) {
				String value = (String) x;
				String dateFormat = G.dateFormat(value);
				if (dateFormat != null) {
					SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
					try {
						return sdf.parse(value);
					} catch (Exception e) {
						return null;
					}
				}
			}
			return null;
		});
	}

	public static java.sql.Date toSqlDate(Object... values) {
		return convert(values, java.sql.Date.class, x -> {
			if (x instanceof Date) {
				return new java.sql.Date(((Date) x).getTime());
			}
			if (x instanceof TemporalAccessor) {
				Date date = Date.from(Instant.from((TemporalAccessor) x).atZone(ZoneId.systemDefault()).toInstant());
				return new java.sql.Date(date.getTime());
			}
			if (x instanceof String) {
				String value = (String) x;
				String dateFormat = G.dateFormat(value);
				if (dateFormat != null) {
					SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
					try {
						Date date = sdf.parse(value);
						return new java.sql.Date(date.getTime());
					} catch (Exception e) {
						return null;
					}
				}
			}
			return null;
		});
	}

	public static LocalDate toLocalDate(Object... values) {
		return convert(values, LocalDate.class, x -> {
			if (x instanceof Date) {
				return ((Date) x).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			}
			if (x instanceof TemporalAccessor) {
				return LocalDate.from((TemporalAccessor) x);
			}
			if (x instanceof String) {
				String value = (String) x;
				String dateFormat = G.dateFormat(value);
				if (dateFormat != null) {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);
					try {
						return LocalDate.parse(value, dtf);
					} catch (Exception e) {
						return null;
					}
				}
			}
			return null;
		});
	}

	public static LocalDateTime toLocalDateTime(Object... values) {
		return convert(values, LocalDateTime.class, x -> {
			if (x instanceof Date) {
				return ((Date) x).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			}
			if (x instanceof TemporalAccessor) {
				return LocalDateTime.from((TemporalAccessor) x);
			}
			if (x instanceof String) {
				String value = (String) x;
				String dateFormat = G.dateFormat(value);
				if (dateFormat != null) {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);
					try {
						return LocalDateTime.parse(value, dtf);
					} catch (Exception e) {
						return null;
					}
				}
			}
			return null;
		});
	}

	/* ========== PROTECTED ========== */
	@SuppressWarnings("unchecked")
	protected static <T> T convert(Object[] values, Class<T> type, Function<Object, T> function) {
		if (values != null) {
			for (Object value : values) {
				if (value != null) {
					if (value.getClass().equals(type)) {
						return (T) value;
					} else {
						try {
							T data = function.apply(value);
							if (data != null) {
								return data;
							}
						} catch (Exception e) {
						}
					}
				}
			}
		}
		return null;
	}
}
