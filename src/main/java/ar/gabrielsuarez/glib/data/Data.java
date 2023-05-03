package ar.gabrielsuarez.glib.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ar.gabrielsuarez.glib.G;

public class Data {

	/* ========== ATTRIBUTES ========== */
	public Map<String, Object> map;
	public List<Object> list;

	/* ========== INSTANCE ========== */
	public Data() {
		convertToMap();
	}

	public static Data fromData(Data data) {
		return new Data().load(data.raw());
	}

	public static Data fromMap(Map<String, Object> map) {
		return new Data().loadMap(map);
	}

	public static Data fromList(List<Object> list) {
		return new Data().loadList(list);
	}

	public static Data fromJson(String json) {
		return new Data().loadJson(json);
	}

	public static Data fromXml(String xml) {
		return new Data().loadXml(xml);
	}

	public static Data fromYaml(String yaml) {
		return new Data().loadYaml(yaml);
	}

	/* ========== INSTANCE RAW ========== */
	public static Data fromRawMap(Map<String, Object> map) {
		Data data = new Data();
		data.map = map;
		return data;
	}

	public static Data fromRawList(List<Object> list) {
		Data data = new Data();
		data.list = list;
		return data;
	}

	/* ========== CONVERT ========== */
	public Map<String, Object> convertToMap() {
		this.map = (map == null) ? new LinkedHashMap<>() : map;
		this.list = null;
		return this.map;
	}

	public List<Object> convertToList() {
		this.map = null;
		this.list = (list == null) ? new ArrayList<>() : list;
		return this.list;
	}

	/* ========== LOAD ========== */
	@SuppressWarnings("unchecked")
	public Data load(Object object) {
		if (object instanceof Map) {
			convertToMap().putAll((Map<String, Object>) object);
		}
		if (object instanceof List) {
			convertToList().addAll((List<Object>) object);
		}
		return this;
	}

	public Data loadData(Data data) {
		return load(data.raw());
	}

	public Data loadMap(Map<String, Object> map) {
		return load(map);
	}

	public Data loadList(List<Object> list) {
		return load(list);
	}

	public Data loadJson(String json) {
		return load((json != null && !json.isEmpty()) ? G.fromJson(json) : null);
	}

	public Data loadXml(String xml) {
		return load((xml != null && !xml.isEmpty()) ? G.fromXml(xml) : null);
	}

	public Data loadYaml(String yaml) {
		return load((yaml != null && !yaml.isEmpty()) ? G.fromYaml(yaml) : null);
	}

	/* ========== SERIALIZER ========== */
	public Map<String, Object> toMap() {
		return (map != null) ? new LinkedHashMap<>(map) : new LinkedHashMap<>();
	}

	public List<Object> toList() {
		return (list != null) ? new ArrayList<>(list) : new ArrayList<>();
	}

	public String toJson() {
		return G.toJson(this);
	}

	public String toJsonSingleLine() {
		return G.toJsonSingleLine(this);
	}

	public String toXml(String root) {
		return G.toXml(this, root);
	}

	public String toXmlSingleLine(String root) {
		return G.toXmlSingleLine(this, root);
	}

	public String toYaml() {
		return G.toYaml(this);
	}

	public <T> T toClass(Class<T> type) {
		return G.fromJson(toJson(), type);
	}

	/* ========== GET ========== */
	@SuppressWarnings("unchecked")
	public Object get(String key) {
		Object current = this;
		String[] subkeys = key.split("\\.");
		for (int i = 0; i < subkeys.length; ++i) {
			current = current instanceof Data ? ((Data) current).raw() : current;
			if (current instanceof Map) {
				Map<String, Object> map = (Map<String, Object>) current;
				current = map.get(subkeys[i]);
			} else if (current instanceof List) {
				List<Object> list = (List<Object>) current;
				Integer index = G.toInteger(subkeys[i]);
				current = (index != null && list.size() > index && index >= 0) ? list.get(index) : null;
			} else {
				current = null;
			}
		}
		return current;
	}

	/* ========== SET ========== */
	public Data set(String key) {
		Data data = new Data();
		set(key, data);
		return data;
	}

	@SuppressWarnings("unchecked")
	public Data set(String key, Object value) {
		Data context = this;
		Data next = null;
		String[] subkeys = key.split("\\.");
		for (int i = 0; i < subkeys.length; ++i) {
			Object object = null;
			String subkey = subkeys[i];
			Integer index = G.toInteger(subkey);
			Boolean validIndex = (index != null && index >= 0);
			Boolean isLast = (i + 1 == subkeys.length);
			if (!validIndex) {
				context.convertToMap();
				object = context.map.get(subkey);
			} else {
				context.convertToList();
				while (context.list.size() <= index) {
					context.list.add(null);
				}
				object = context.list.get(index);
			}
			if (!isLast) {
				if (object instanceof Data) {
					next = (Data) object;
				} else if (object instanceof Map) {
					next = Data.fromRawMap((Map<String, Object>) object);
				} else if (object instanceof List) {
					next = Data.fromRawList((List<Object>) object);
				} else {
					next = new Data();
					object = (!validIndex) ? context.map.put(subkey, next) : context.list.set(index, next);
				}
				context = next;
			} else {
				object = (!validIndex) ? context.map.put(subkey, value) : context.list.set(index, value);
			}
		}
		return this;
	}

	public Data set(String key, Object value, Boolean condition) {
		if (condition) {
			return set(key, value);
		}
		return this;
	}

	/* ========== ADD ========== */
	public Data add() {
		Data data = new Data();
		convertToList().add(data);
		return data;
	}

	public Data add(String key) {
		Data data = new Data();
		add(key, data);
		return data;
	}

	public Data add(Object value) {
		convertToList().add(value);
		return this;
	}

	@SuppressWarnings("unchecked")
	public Data add(String key, Object value) {
		Data context = this;
		Data next = null;
		String[] subkeys = key.split("\\.");
		for (int i = 0; i < subkeys.length; ++i) {
			Object object = null;
			String subkey = subkeys[i];
			Integer index = G.toInteger(subkey);
			Boolean validIndex = (index != null && index >= 0);
			if (!validIndex) {
				context.convertToMap();
				object = context.map.get(subkey);
			} else {
				context.convertToList();
				while (context.list.size() <= index) {
					context.list.add(null);
				}
				object = context.list.get(index);
			}
			if (object instanceof Data) {
				next = (Data) object;
			} else if (object instanceof Map) {
				next = Data.fromRawMap((Map<String, Object>) object);
			} else if (object instanceof List) {
				next = Data.fromRawList((List<Object>) object);
			} else {
				next = new Data();
				object = (!validIndex) ? context.map.put(subkey, next) : context.list.set(index, next);
			}
			context = next;
		}
		context.convertToList().add(value);
		return this;
	}

	public Data add(String key, Object value, Boolean condition) {
		if (condition) {
			return add(key, value);
		}
		return this;
	}

	public Data addValue(Object value) {
		convertToList().add(value);
		return this;
	}

	/* ========== REMOVE ========== */
	@SuppressWarnings("unchecked")
	public Boolean remove(String key) {
		Data context = this;
		Data next = null;
		String[] subkeys = key.split("\\.");
		for (int i = 0; i < subkeys.length; ++i) {
			Object object = null;
			String subkey = subkeys[i];
			Integer index = G.toInteger(subkey);
			Boolean validIndex = (index != null && index >= 0);
			Boolean isLast = (i + 1 == subkeys.length);
			if (context.map != null) {
				object = context.map.get(subkey);
			} else if (context.list != null && validIndex) {
				object = context.list.get(index);
			}
			if (!isLast) {
				if (object instanceof Data) {
					next = (Data) object;
				} else if (object instanceof Map) {
					next = Data.fromRawMap((Map<String, Object>) object);
				} else if (object instanceof List) {
					next = Data.fromRawList((List<Object>) object);
				} else {
					return false;
				}
				context = next;
			} else {
				if (context.map != null) {
					object = context.map.remove(subkey);
					return object != null;
				} else if (context.list != null && validIndex) {
					return context.list.remove(index);
				} else {
					return false;
				}
			}
		}
		return false;
	}

	public Boolean remove(String key, Boolean condition) {
		if (condition) {
			return remove(key);
		}
		return false;
	}

	/* ========== DATA ========== */
	public Object raw() {
		Object object = null;
		object = (map != null) ? map : object;
		object = (list != null) ? list : object;
		return object;
	}

	public Object object(String key) {
		return object(key, null);
	}

	public Object object(String key, Object defaultValue) {
		Object object = get(key);
		return object != null ? object : defaultValue;
	}

	public Data data(String key) {
		return data(key, null);
	}

	@SuppressWarnings("unchecked")
	public Data data(String key, Data defaultValue) {
		Object object = object(key);
		if (object instanceof Data) {
			return (Data) object;
		}
		if (object instanceof Map) {
			return fromMap((Map<String, Object>) object);
		}
		if (object instanceof Data) {
			return fromList((List<Object>) object);
		}
		return defaultValue;
	}

	@SuppressWarnings("unchecked")
	public List<Data> dataList() {
		Data data = this;
		List<?> list = (data.list != null) ? data.list : new ArrayList<>();
		return (List<Data>) list;
	}

	@SuppressWarnings("unchecked")
	public List<Data> dataList(String key) {
		Data data = data(key);
		List<?> list = (data.list != null) ? data.list : new ArrayList<>();
		return (List<Data>) list;
	}

	public Map<String, Object> map(String key) {
		return map(key, null);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> map(String key, Map<String, Object> defaultValue) {
		Object object = object(key);
		if (object instanceof Data) {
			return ((Data) object).map;
		}
		if (object instanceof Map) {
			return (Map<String, Object>) object;
		}
		return defaultValue;
	}

	public List<Object> list(String key) {
		return list(key, null);
	}

	@SuppressWarnings("unchecked")
	public List<Object> list(String key, List<Object> defaultValue) {
		Object object = object(key);
		if (object instanceof Data) {
			return ((Data) object).list;
		}
		if (object instanceof List) {
			return (List<Object>) object;
		}
		return defaultValue;
	}

	public String string(String key) {
		return string(key, null);
	}

	public String string(String key, String defaultValue) {
		Object object = object(key);
		return object != null ? object.toString() : defaultValue;
	}

	public Boolean bool(String key) {
		return bool(key, null);
	}

	public Boolean bool(String key, Boolean defaultValue) {
		return G.toBoolean(string(key), defaultValue);
	}

	public Short shortInt(String key) {
		return shortInt(key, null);
	}

	public Short shortInt(String key, Short defaultValue) {
		return G.toShort(string(key), defaultValue);
	}

	public Integer integer(String key) {
		return integer(key, null);
	}

	public Integer integer(String key, Integer defaultValue) {
		return G.toInteger(string(key), defaultValue);
	}

	public Long longInt(String key) {
		return longInt(key, null);
	}

	public Long longInt(String key, Long defaultValue) {
		return G.toLong(string(key), defaultValue);
	}

	public Float floatNumber(String key) {
		return floatNumber(key, null);
	}

	public Float floatNumber(String key, Float defaultValue) {
		return G.toFloat(string(key), defaultValue);
	}

	public Double doubleNumber(String key) {
		return doubleNumber(key, null);
	}

	public Double doubleNumber(String key, Double defaultValue) {
		return G.toDouble(string(key), defaultValue);
	}

	public BigInteger bigInteger(String key) {
		return bigInteger(key, null);
	}

	public BigInteger bigInteger(String key, BigInteger defaultValue) {
		return G.toBigInteger(string(key), defaultValue);
	}

	public BigDecimal bigDecimal(String key) {
		return bigDecimal(key, null);
	}

	public BigDecimal bigDecimal(String key, BigDecimal defaultValue) {
		return G.toBigDecimal(string(key), defaultValue);
	}

	/* ========== TOSTRING ========== */
	public String toString() {
		return toJson();
	}
}
