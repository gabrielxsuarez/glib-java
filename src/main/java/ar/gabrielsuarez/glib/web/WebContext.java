package ar.gabrielsuarez.glib.web;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.gabrielsuarez.glib.data.Data;
import spark.Request;
import spark.Response;

public class WebContext {

	/* ========== ATTRIBUTES ========== */
	private Request request;
	private Response response;
	public WebParameters parameters = new WebParameters();
	public Data headers = new Data();

	/* ========== GETTER ========== */
	public HttpServletRequest request() {
		return this.request.raw();
	}

	public HttpServletResponse response() {
		return this.response.raw();
	}

	/* ========== SETTER ========== */
	void setRequest(Request request) {
		this.request = request;
	}

	void setResponse(Response response) {
		this.response = response;
	}

	/* ========== METHODS ========== */
	public String ip() {
		String ip = null;
		if (request != null) {
			String xForwardedFor = request.headers("x-forwarded-for");
			if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
				ip = xForwardedFor.split(",")[0].trim();
			} else {
				ip = request.ip();
			}
		}
		return ip;
	}

	public Boolean isGzipEnabled() {
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
