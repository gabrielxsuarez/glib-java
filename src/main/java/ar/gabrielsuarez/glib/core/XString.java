package ar.gabrielsuarez.glib.core;

import java.net.URLEncoder;
import java.util.Base64;
import java.util.Set;

import ar.gabrielsuarez.glib.G;

public abstract class XString {

	/* ========== ATTRIBUTES ========== */
	protected static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	protected static final Set<Character> BLANKS = G.setOf(' ', '\t', '\r', '\n');

	/* ========== UTIL ========== */
	public static Boolean isBlank(Character character) {
		return BLANKS.contains(character);
	}

	/* ========== HEX ========== */
	public static String toHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int i = 0; i < bytes.length; i++) {
			int v = bytes[i] & 0xFF;
			hexChars[i * 2] = HEX_ARRAY[v >>> 4];
			hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars);
	}

	/* ========== FORMAT ========== */
	public static String camelCase(String value) {
		String newText = pascalCase(value);
		newText = newText.substring(0, 1).toLowerCase() + newText.substring(1);
		return newText;
	}

	public static String pascalCase(String value) {
		if (value != null) {
			String newText = "";
			String normalized = value.replaceAll("[^0-9a-zA-ZñÑ]+", " ");
			String[] parts = normalized.split(" ");
			for (String part : parts) {
				if (!part.isEmpty()) {
					newText += part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase();
				}
			}
			return newText;
		}
		return null;
	}

	/* ========== ENCODING ========== */
	public static String urlEncode(String value) {
		try {
			return value != null ? URLEncoder.encode(value, "UTF-8") : "";
		} catch (Exception e) {
			throw G.runtimeException(e);
		}
	}

	public static String base64(String value) {
		return base64(value.getBytes());
	}

	public static String base64(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}
}
