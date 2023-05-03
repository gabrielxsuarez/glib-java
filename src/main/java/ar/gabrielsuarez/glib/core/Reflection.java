package ar.gabrielsuarez.glib.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import ar.gabrielsuarez.glib.G;

public abstract class Reflection {

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
		if (values != null) {
			for (Integer i = 0; i < values.length; ++i) {
				types[i] = (values[i] != null) ? values[i].getClass() : null;
			}
		}
		return types;
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
}
