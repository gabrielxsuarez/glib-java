package ar.gabrielsuarez.glib.sql;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.gabrielsuarez.glib.G;

public class SqlResponse {

	/* ========== ATTRIBUTES ========== */
	private Map<String, List<Object>> data = new HashMap<>();

	/* ========== INSTANCE ========== */
	public static SqlResponse fromResultSet(ResultSet resultSet) {
		SqlResponse sqlResponse = new SqlResponse();
		try {
			if (resultSet != null) {
				try (ResultSet rs = resultSet) {
					ResultSetMetaData rsmd = rs.getMetaData();
					Integer size = rsmd.getColumnCount();
					while (rs.next()) {
						for (int i = 1; i <= size; ++i) {
							String columnName = rsmd.getColumnLabel(i);
							Object value = rs.getObject(i);
							sqlResponse.put(columnName, value);
						}
					}
				}
			}
		} catch (Exception e) {
			throw G.runtimeException(e);
		}
		return sqlResponse;
	}

	public static <T> List<T> toList(Class<T> type, ResultSet resultSet) {
		return fromResultSet(resultSet).toList(type);
	}

	public static <T> T toClass(Class<T> type, ResultSet resultSet) {
		return fromResultSet(resultSet).toClass(type);
	}

	/* ========== METHODS ========== */
	public <T> List<T> toList(Class<T> type) {
		try {
			List<T> list = new ArrayList<>();
			Map<Field, List<Object>> fields = fields(type);
			for (Integer i = 0; i < size(); ++i) {
				T instance = G.instance(type);
				for (Field field : fields.keySet()) {
					Object value = fields.get(field).get(i);
					try {
						field.set(instance, value);
					} catch (IllegalArgumentException e) {
						field.set(instance, G.cast(field.getType(), value));
					}
				}
				list.add(instance);
			}
			return list;
		} catch (Exception e) {
			throw G.runtimeException(e);
		}
	}

	public <T> T toClass(Class<T> type) {
		return toList(type).stream().findFirst().orElse(null);
	}

	/* ========== PROTECTED ========== */
	protected void put(String key, Object value) {
		List<Object> list = data.get(key);
		if (list == null) {
			list = new ArrayList<>();
			data.put(key, list);
		}
		list.add(value);
	}

	protected Integer size() {
		for (String fieldName : data.keySet()) {
			return data.get(fieldName).size();
		}
		return 0;
	}

	protected <T> Map<Field, List<Object>> fields(Class<T> type) {
		Map<Field, List<Object>> fields = new HashMap<>();
		Map<String, Field> fieldMap = G.fieldMap(type);
		for (String fieldName : data.keySet()) {
			Field field = fieldMap.get(fieldName);
			if (field != null) {
				fields.put(field, data.get(fieldName));
			}
		}
		return fields;
	}
}