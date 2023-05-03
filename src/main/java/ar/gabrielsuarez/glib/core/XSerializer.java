package ar.gabrielsuarez.glib.core;

import ar.gabrielsuarez.glib.G;

public abstract class XSerializer {

	public static Boolean posibleJson(String value) {
		if (value != null) {
			for (int i = 0; i < value.length(); i++) {
				char character = value.charAt(i);
				if (G.isBlank(character)) {
					continue;
				} else if (character == '{' || character == '[') {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	public static Boolean posibleXml(String value) {
		if (value != null) {
			for (int i = 0; i < value.length(); i++) {
				char character = value.charAt(i);
				if (G.isBlank(character)) {
					continue;
				} else if (character == '<') {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
}
