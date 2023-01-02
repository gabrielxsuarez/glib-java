package ar.gabrielsuarez.glib.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ar.gabrielsuarez.glib.G;
import ar.gabrielsuarez.glib.core.Convert;

public class Data {

	/* ========== ATTRIBUTES ========== */
	private Map<String, Object> map;
	private List<Object> list;

	/* ========== INSTANCE ========== */
	public Data() {
		convertToMap();
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

	/* ========== GET / SET / ADD / DEL ========== */
	private Object get(String key, Object context) {
		if (key != null && !key.isEmpty() && context != null) {
			context = (context instanceof Data) ? ((Data) context).object() : context;
			Integer indexOf = key.indexOf('.');
			String localKey = (indexOf >= 0) ? key.substring(0, indexOf) : key;
			String remainKey = (indexOf >= 0) ? key.substring(indexOf + 1) : null;
			Object newContext = null;
			if (context instanceof Map) {
				newContext = ((Map<?, ?>) context).get(localKey);
			}
			if (context instanceof List) {
				List<?> list = (List<?>) context;
				Integer index = Convert.toInteger(localKey);
				if (index != null && list.size() > index) {
					newContext = list.get(index);
				}
			}
			if (remainKey != null) {
				return get(remainKey, newContext);
			}
			return newContext;
		}
		return null;
	}

	/* ========== GET ========== */
	public Object object() {
		Object object = null;
		object = (map != null) ? map : object;
		object = (list != null) ? list : object;
		return object;
	}

	public Object object(String key) {
		return object(key, null);
	}

	public Object object(String key, Object defaultValue) {
		Object object = get(key, this);
		return object != null ? object : defaultValue;
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

//	public Data objeto(String clave) {
//		return objeto(clave, new Data());
//	}
//
//	public Data objeto(String clave, Data valorPorDefecto) {
//		Object object = get(clave);
//		if (object != null && object instanceof Data) {
//			return (Data) object;
//		}
//		return valorPorDefecto;
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<Data> objetos() {
//		List<Data> objetos = new ArrayList<>();
//		if (list != null) {
//			for (Object object : list) {
//				if (object instanceof Map) {
//					Data objeto = Data.fromMap((Map<String, Object>) object);
//					objetos.add(objeto);
//				}
//				if (object instanceof Data) {
//					objetos.add((Data) object);
//				}
//			}
//		}
//		return objetos;
//	}
//
//	public Data objetos(Integer i) {
//		try {
//			return objetos().get(i);
//		} catch (Exception e) {
//			return new Data();
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<Data> objetos(String clave) {
//		List<Data> objetos = new ArrayList<>();
//		Object list = get(clave);
//		if (list instanceof Data) {
//			list = ((Data) list).toList();
//		}
//		if (list instanceof List) {
//			for (Object object : (List<Object>) list) {
//				if (object instanceof Map) {
//					Data objeto = Data.fromMap((Map<String, Object>) object);
//					objetos.add(objeto);
//				}
//			}
//		}
//		return objetos;
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<Object> objects(String clave) {
//		List<Object> objetos = new ArrayList<>();
//		Object list = get(clave);
//		if (list instanceof Data) {
//			list = ((Data) list).toList();
//		}
//		if (list instanceof List) {
//			for (Object object : (List<Object>) list) {
//				objetos.add(object);
//			}
//		}
//		return objetos;
//	}
//
//	/* ========== SET ========== */
//	public Data set(String clave) {
//		Data objeto = new Data();
//		set(clave, objeto);
//		return objeto;
//	}
//
//	public Data setIf(Boolean condicion, String clave, Object valor) {
//		if (condicion) {
//			return set(clave, valor);
//		}
//		return this;
//	}
//
//	public Data set(String clave, Object valor) {
//		Data destino = this;
//		if (clave != null && !clave.isEmpty()) {
//			String[] partes = clave.split("\\.");
//			for (Integer i = 0; i < partes.length; ++i) {
//				String subClave = partes[i];
//				if (i + 1 < partes.length) {
//					Data objeto = new Data();
//					if (!subClave.matches("[0-9]+")) {
//						Object object = destino.map.get(subClave);
//						if (object != null && object instanceof Data) {
//							objeto = (Data) object;
//						} else {
//							destino.map.put(subClave, objeto);
//						}
//					} else {
//						destino.list = destino.list == null ? new ArrayList<>() : destino.list;
//						Integer posicion = Integer.valueOf(subClave);
//						while (destino.list.size() <= posicion) {
//							destino.list.add(new Data());
//						}
//						Object object = destino.list.get(posicion);
//						if (object != null && object instanceof Data) {
//							objeto = (Data) object;
//						}
//					}
//					destino = objeto;
//				} else {
////					if (valor instanceof Fecha) {
////						destino.mapa.put(subClave, ((Fecha) valor).string("yyyy-MM-dd HH:mm:ss"));
////					} else {
////						destino.mapa.put(subClave, valor);
////					}
//				}
//			}
//		}
//		return destino;
//	}
//
//
//	/* ========== DEL ========== */
//	public Data del(String clave) {
//		Data destino = this;
//		if (clave != null && !clave.isEmpty()) {
//			String[] partes = clave.split("\\.");
//			for (Integer i = 0; i < partes.length; ++i) {
//				String subClave = partes[i];
//				if (i + 1 < partes.length) {
//					Data objeto = new Data();
//					if (!subClave.matches("[0-9]+")) {
//						Object object = destino.map.get(subClave);
//						if (object != null && object instanceof Data) {
//							objeto = (Data) object;
//						} else {
//							destino.map.put(subClave, objeto);
//						}
//					} else {
//						destino.list = destino.list == null ? new ArrayList<>() : destino.list;
//						Integer posicion = Integer.valueOf(subClave);
//						while (destino.list.size() <= posicion) {
//							destino.list.add(new Data());
//						}
//						Object object = destino.list.get(posicion);
//						if (object != null && object instanceof Data) {
//							objeto = (Data) object;
//						}
//					}
//					destino = objeto;
//				} else {
//					destino.map.remove(subClave);
//				}
//			}
//		}
//		return this;
//	}
//
//	public Data del(String... claves) {
//		for (String clave : claves) {
//			del(clave);
//		}
//		return this;
//	}
//
//	/* ========== ADD ========== */
//	public Data add() {
//		Data objeto = new Data();
//		add(objeto);
//		return objeto;
//	}
//
//	public Data add(String clave) {
//		Data objeto = new Data();
//		add(clave, objeto);
//		return objeto;
//	}
//
//	public Data add(Object valor) {
//		setList().list.add(valor);
//		return this;
//	}
//
//	public Data addValue(String valor) {
//		return add((Object) valor);
//	}
//
//	public Data addValue(String clave, String valor) {
//		Data objeto = objeto(clave, null);
//		objeto = objeto == null ? set(clave) : objeto;
//		objeto.add((Object) valor);
//		return this;
//	}
//
//	public Data add(String clave, Object valor) {
//		Data objeto = objeto(clave, null);
//		objeto = objeto == null ? set(clave) : objeto;
//		objeto.add(valor);
//		return this;
//	}
//
//	public Data addIf(Boolean condicion, String clave) {
//		if (condicion) {
//			return add(clave);
//		}
//		return new Data();
//	}
//
//	public Data addIf(Boolean condicion, String clave, Object valor) {
//		if (condicion) {
//			return add(clave, valor);
//		}
//		return new Data();
//	}
//
//	/* ========== UTIL ========== */
//	public Boolean isEmpty() {
//		Boolean isEmpty = true;
//		if (map != null && !map.isEmpty()) {
//			isEmpty = false;
//		}
//		if (list != null && !list.isEmpty()) {
//			isEmpty = false;
//		}
//		return isEmpty;
//	}
//
//	public Boolean isMap() {
//		return !isList();
//	}
//
//	public Boolean isList() {
//		return list != null;
//	}
//
//	public Boolean existe(String clave) {
//		return get(clave) != null;
//	}
//
//	/* ========== ORDENAR ========== */
//	// TODO: Ver Gabriel
//	public Data ordenar(String... campos) {
//		if (this.map == null && this.list == null) {
//			return this;
//		}
//
//		if (this.map != null && this.list == null) {
//			Map<String, Object> mapaA = new LinkedHashMap<>();
//			for (String campo : campos) {
//				if (map.containsKey(campo)) {
//					mapaA.put(campo, map.get(campo));
//				}
//			}
//			Map<String, Object> mapaB = new TreeMap<>();
//			for (String clave : map.keySet()) {
//				if (!mapaA.containsKey(clave)) {
//					mapaB.put(clave, map.get(clave));
//				}
//			}
//			map = new LinkedHashMap<>();
//			map.putAll(mapaA);
//			map.putAll(mapaB);
//			return this;
//		}
//
//		Map<String, Data> mapa = new TreeMap<>();
//		for (Data item : objetos()) {
//			String clave = "";
//			for (String campo : campos) {
//				if (campo.startsWith("_bigdecimal_")) {
//					BigDecimal original = item.bigDecimal(campo.substring("_bigdecimal_".length()));
//					original = original != null ? original : new BigDecimal("0");
//					BigDecimal diferencia = new BigDecimal("10000000000").subtract(original);
//					clave += diferencia.toString() + "_";
//				} else {
//					clave += item.string(campo) + "_";
//				}
//			}
//			clave += UUID.randomUUID();
//			mapa.put(clave, item);
//		}
//		list = null;
//		for (String clave : mapa.keySet()) {
//			add(mapa.get(clave));
//		}
//		return this;
//	}

	/* ========== TOSTRING ========== */
//	public String toString() {
//		return toJson();
//	}
}
