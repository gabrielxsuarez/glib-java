package ar.gabrielsuarez.glib.web;

import java.util.Arrays;

import spark.Request;

public class WebContext {

	/* ========== ATRIBUTOS ========== */
	private Request request;
	public WebParameters parametros;

	/* ========== SETTER ========== */
	void setRequest(Request request) {
		this.request = request;
	}

	/* ========== METODOS ========== */
	public Boolean gzipEnabled() {
		String acceptEncoding = request.headers("Accept-Encoding");
		if (acceptEncoding != null) {
			String[] tokens = acceptEncoding.split(",");
			if (Arrays.stream(tokens).map(String::trim).anyMatch(s -> s.equalsIgnoreCase("gzip"))) {
				return true;
			}
		}
		return false;
	}
}
