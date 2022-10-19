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
	private Map<String, Object> map;
	private List<Object> list;

	/* ========== CONVERT ========== */
	public Data convertToMap() {
		this.map = (map == null) ? new LinkedHashMap<>() : map;
		this.list = null;
		return this;
	}

	public Data convertToList() {
		this.map = null;
		this.list = (list == null) ? new ArrayList<>() : list;
		return this;
	}

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

	/* ========== LOAD ========== */
	@SuppressWarnings("unchecked")
	private Data load(Object object) {
		if (object instanceof Map) {
			convertToMap().map.putAll((Map<String, Object>) object);
		}
		if (object instanceof List) {
			convertToList().list.addAll((List<Object>) object);
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
	public Object get(String key) {
		Object current = this;
		String[] subkeys = key.split("\\.");
		for (String subkey : subkeys) {
			current = getSubKey(current, subkey);
		}
		return current;
	}

	@SuppressWarnings("unchecked")
	private Object getSubKey(Object context, String subkey) {
		context = context instanceof Data ? ((Data) context).raw() : context;
		if (context instanceof Map) {
			Map<String, Object> current = (Map<String, Object>) context;
			return current.get(subkey);
		} else if (context instanceof List) {
			List<Object> current = (List<Object>) context;
			Integer index = G.toInteger(subkey);
			if (index != null && current.size() > index && index >= 0) {
				return current.get(index);
			}
		}
		return null;
	}

	/* ========== SET ========== */
	public Data set(String key) {
		Data data = new Data();
		set(key, data);
		return data;
	}

	public Data set(String key, Object value) {
		Object current = this;
		String[] subkeys = key.split("\\.");
		for (int i = 0; i < subkeys.length; ++i) {
			current = (i + 1 == subkeys.length) ? setSubKey(current, subkeys[i], value) : setSubKey(current, subkeys[i]);
		}
		return this;
	}

	public Data set(String key, Object value, Boolean condition) {
		if (condition) {
			return set(key, value);
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	private Object setSubKey(Object context, String subkey) {
		Object next = getSubKey(context, subkey);
		if (next == null) {
			next = new Data();
			context = context instanceof Data ? ((Data) context).raw() : context;
			if (context instanceof Map) {
				Map<String, Object> current = (Map<String, Object>) context;
				current.put(subkey, next);
			} else if (context instanceof List) {
				List<Object> current = (List<Object>) context;
				Integer index = G.toInteger(subkey);
				if (index != null) {
					Integer size = current.size();
					for (int i = size; i <= index; ++i) {
						current.add(null);
					}
					current.set(index, next);
				}
			}
		}
		return next;
	}

	@SuppressWarnings("unchecked")
	private Object setSubKey(Object context, String subkey, Object value) {
		Integer index = G.toInteger(subkey);
		if (context instanceof Data) {
			context = index == null ? ((Data) context).raw() : ((Data) context).convertToList().raw();
		}
		if (context instanceof Map) {
			Map<String, Object> current = (Map<String, Object>) context;
			current.put(subkey, value);
		} else if (context instanceof List) {
			List<Object> current = (List<Object>) context;
			Integer size = current.size();
			for (int i = size; i <= index; ++i) {
				current.add(null);
			}
			if (index >= 0) {
				current.set(index, value);
			}
		}
		return null;
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
