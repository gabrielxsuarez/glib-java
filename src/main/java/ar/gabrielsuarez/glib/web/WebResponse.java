package ar.gabrielsuarez.glib.web;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class WebResponse {

	/* ========== ATTRIBUTES ========== */
	private Integer httpCode;
	private Map<String, String> headers = new LinkedHashMap<>();
	private Object body = "";

	/* ========== GETTERS ========== */
	public Integer httpCode() {
		return httpCode != null ? httpCode : 200;
	}

	public Set<String> headers() {
		return headers.keySet();
	}

	public String header(String header) {
		return headers.get(header);
	}

	public Object body() {
		return body;
	}

	/* ========== SETTERS ========== */
	public void setHttpCode(Integer httpCode) {
		this.httpCode = httpCode;
	}

	public void setHeader(String header, String value) {
		headers.put(header, value);
	}

	public void removeHeader(String header) {
		headers.remove(header);
	}

	public void setContentType(String value) {
		setHeader("Content-Type", value);
	}

	public void setBody(Object body) {
		this.body = body;
	}

	/* ========== METHODS ========== */
	public Boolean isReady() {
		return httpCode != null;
	}

	public String redirect(String url) {
		setHttpCode(302);
		setHeader("Location", url);
		removeHeader("Content-Type");
		return "";
	}
}
