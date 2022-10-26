package ar.gabrielsuarez.glib.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import ar.gabrielsuarez.glib.G;

public abstract class XReflection {

	/* ========== INSTANCE ========== */
	public static <T> T instance(Class<T> type, Object... parameters) {
		try {
			Class<?>[] types = types(parameters);
			Constructor<T> constructor = type.getConstructor(types);
			T instance = constructor.newInstance(parameters);
			return instance;
		} catch (Exception e) {
			throw G.runtimeException(e);
		}
	}

	/* ========== TYPES ========== */
	public static Class<?>[] types(Object... values) {
		Class<?>[] types = new Class<?>[values.length];
		for (Integer i = 0; i < values.length; ++i) {
			types[i] = (values[i] != null) ? values[i].getClass() : null;
		}
		return types;
	}

	public static String javaType(Object value) {
		if (value != null) {
			Class<?> type = value.getClass();
			String javaType = type.getCanonicalName();
			javaType = (type == Float.class) ? "java.math.BigDecimal" : javaType;
			javaType = (type == Double.class) ? "java.math.BigDecimal" : javaType;
			javaType = (type == BigInteger.class) ? "java.math.BigInteger" : javaType;
			javaType = (type == BigDecimal.class) ? "java.math.BigDecimal" : javaType;
			javaType = (type == Date.class) ? "java.util.Date" : javaType;
			javaType = (type == java.sql.Date.class) ? "java.sql.Date" : javaType;
			javaType = (type == LocalDate.class) ? "java.time.LocalDate" : javaType;
			javaType = (type == LocalDateTime.class) ? "java.time.LocalDateTime" : javaType;
			if (type == String.class) {
				String string = (String) value;
				String dateFormat = G.dateFormat(string);
				if (dateFormat != null) {
					return G.dateFormatContainsTime(dateFormat) ? "java.time.LocalDateTime" : "java.time.LocalDate";
				}
				return "String";
			}
			return javaType;
		}
		return "java.lang.Object";
	}

	/* ========== FIELDS ========== */
	public static Field[] fields(Object object) {
		return fields(object.getClass());
	}

	public static Field[] fields(Class<?> type) {
		Field[] fields = type.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
		}
		return fields;
	}

	public static <T> Map<String, Field> fieldMap(Class<T> type) {
		Map<String, Field> map = new LinkedHashMap<>();
		for (Field field : fields(type)) {
			map.put(field.getName(), field);
		}
		return map;
	}

	/* ========== PROCESS FIELD ========== */
	public static <T> T trimAllFields(T object) {
		try {
			for (Field field : fields(object)) {
				Object value = field.get(object);
				if (value != null && value instanceof String) {
					field.set(object, value.toString().trim());
				}
			}
			return object;
		} catch (Exception e) {
			throw G.runtimeException(e);
		}
	}

	public static <T extends Iterable<?>> T trimAllFields(T objects) {
		if (objects != null) {
			for (Object object : objects) {
				trimAllFields(object);
			}
		}
		return objects;
	}

	/* ========== CLONE ========== */
	@SuppressWarnings("unchecked")
	public static <T> T clone(T object) {
		if (object != null) {
			String json = G.toJson(object);
			T value = (T) G.fromJson(json, object.getClass());
			return value;
		}
		return null;
	}

	/* ========== EQUALS & HASHCODE ========== */
	public static <T> Boolean equals(T object1, T object2) {
		try {
			if (object1 == null) {
				return object2 == null;
			} else if (object2 == null) {
				return object1 == null;
			} else {
				Boolean equals = true;
				Field[] fields = fields(object1);
				for (int i = 0; i < fields.length; ++i) {
					Object value1 = fields[i].get(object1);
					Object value2 = fields[i].get(object2);
					equals &= (value1 == null) ? (value2 == null) : value1.equals(value2);
				}
				return equals;
			}
		} catch (Exception e) {
			throw G.runtimeException(e);
		}
	}

	public static Integer hashCode(Object object) {
		try {
			if (object != null) {
				Field[] fields = fields(object);
				Object[] values = new Object[fields.length];
				for (int i = 0; i < fields.length; ++i) {
					values[i] = fields[i].get(object);
				}
				return Objects.hash(values);
			}
			return 0;
		} catch (Exception e) {
			throw G.runtimeException(e);
		}
	}
}
