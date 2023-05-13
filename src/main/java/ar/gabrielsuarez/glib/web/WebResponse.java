package ar.gabrielsuarez.glib.web;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import spark.Response;

public class WebResponse {

	/* ========== PRIVATE ========== */
	private Response sparkResponse;

	/* ========== ATTRIBUTES ========== */
	public Integer httpCode = 200;
	public Map<String, String> headers = new LinkedHashMap<>();
	public Object body;

	/* ========== INIT ========== */
	void init(Response sparkResponse) {
		this.sparkResponse = sparkResponse;
	}

	/* ========== GETTERS ========== */
	public HttpServletResponse raw() {
		return sparkResponse != null ? sparkResponse.raw() : null;
	}

	/* ========== METHODS ========== */
	public void setContentType(String value) {
		headers.put("Content-Type", value);
	}

	public String redirect(String url) {
		httpCode = 302;
		headers.put("Location", url);
		headers.remove("Content-Type");
		return "";
	}
}
