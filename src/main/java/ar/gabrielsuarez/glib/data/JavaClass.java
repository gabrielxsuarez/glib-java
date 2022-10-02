package ar.gabrielsuarez.glib.data;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.gabrielsuarez.glib.G;

public class JavaClass {

	/* ========== ATTRIBUTES ========== */
	public String packageName;
	public List<String> imports = new ArrayList<>(); 
	public String name;
	public Set<Attribute> attributes = new LinkedHashSet<>();
	public List<JavaClass> subClasses = new ArrayList<>();

	/* ========== OPTIONS ========== */
	public String tagAttributes = "ATTRIBUTES";
	public String tabSubClasses = "CLASSES";

	/* ========== CLASSES ========== */
	public static class Attribute {
		public String modifier;
		public String type;
		public String name;
		public String inicialize;
	}

	/* ========== LOAD ========== */
	@SuppressWarnings("unchecked")
	public JavaClass loadJson(String json) {
		Object object = G.fromJson(json);
		if (object instanceof Map) {
			return loadMap((Map<String, Object>) object);
		} else if (object instanceof List<?>) {
//			return loadList((List<Object>) object);
		}
		return this;
	}

	public JavaClass loadMap(Map<String, Object> map) {
		for (String key : map.keySet()) {
			Object value = map.get(key);
			if (value instanceof Map) {
				return null;
			} else if (value instanceof List<?>) {
				return null;
			} else {

			}
		}
		return this;
	}

//	public JavaClass loadList(List<Object> list) {
//		for (Object object : list) {
//			
//		}
//		return this;
//	}
}
