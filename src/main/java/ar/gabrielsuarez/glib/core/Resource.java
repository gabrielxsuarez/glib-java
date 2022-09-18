package ar.gabrielsuarez.glib.core;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import ar.gabrielsuarez.glib.G;

public abstract class Resource {

	/* ========== RESOURCE ========== */
	public static InputStream resourceInputStream(String path) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
	}

	public static byte[] resourceBytes(String path) {
		return G.toBytes(resourceInputStream(path));
	}

	public static String resourceBase64(String path) {
		return G.base64(resourceBytes(path));
	}

	/* ========== PROPERTIES ========== */
	public static Properties properties(String path) {
		Properties properties = new Properties();
		try (InputStream is = resourceInputStream(path)) {
			properties.load(is);
		} catch (Exception e) {
			throw G.runtimeException(e);
		}
		return properties;
	}

	public static Map<String, String> propertiesToMap(String path) {
		Map<String, String> map = new HashMap<>();
		Properties properties = properties(path);
		for (Object key : properties.keySet()) {
			map.put(key.toString(), properties.getProperty(key.toString()).trim());
		}
		return map;
	}
}
