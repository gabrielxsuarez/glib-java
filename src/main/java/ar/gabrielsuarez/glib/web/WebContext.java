package ar.gabrielsuarez.glib.web;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spark.Request;
import spark.Response;

public class WebContext {

	/* ========== ATTRIBUTES ========== */
	private Request sparkRequest;
	private Response sparkResponse;
	public WebParameters parameters = new WebParameters();
	public WebResponse response = new WebResponse();

	/* ========== INSTANCE ========== */
	public WebContext() {
	}

	/* ========== INIT ========== */
	public void init() {
	}

	void setRequest(Request request) {
		this.sparkRequest = request;
	}

	void setResponse(Response response) {
		this.sparkResponse = response;
	}

	/* ========== GETTER ========== */
	public HttpServletRequest request() {
		return sparkRequest != null ? sparkRequest.raw() : null;
	}

	public HttpServletResponse response() {
		return sparkResponse != null ? sparkResponse.raw() : null;
	}

	public Set<String> requestHeaders() {
		return sparkRequest != null ? sparkRequest.headers() : new HashSet<>();
	}

	public String requestHeader(String header) {
		return requestHeader(header, "");
	}

	public String requestHeader(String header, String defaultValue) {
		String value = sparkRequest != null ? sparkRequest.headers(header) : null;
		return value != null ? value : defaultValue;
	}

	/* ========== SETTER ========== */
	public void httpCode(Integer httpCode) {
		response.httpCode = httpCode;
	}

	public void contentType(String value) {
		responseHeader("Content-Type", value);
	}

	public void responseHeader(String header, String value) {
		this.response.headers.put(header, value);
	}

	/* ========== METHODS ========== */
	public String uri() {
		return sparkRequest != null ? sparkRequest.uri() : null;
	}

	public String ip() {
		String ip = null;
		if (sparkRequest != null) {
			String xForwardedFor = requestHeader("x-forwarded-for");
			if (!xForwardedFor.isEmpty()) {
				ip = xForwardedFor.split(",")[0].trim();
			} else {
				ip = sparkRequest.ip();
			}
		}
		return ip;
	}

	public String userAgent() {
		return requestHeader("user-agent");
	}

	public Boolean isGzipEnabled() {
		String acceptEncoding = requestHeader("Accept-Encoding");
		if (!acceptEncoding.isEmpty()) {
			String[] tokens = acceptEncoding.split(",");
			if (Arrays.stream(tokens).map(String::trim).anyMatch(s -> s.equalsIgnoreCase("gzip"))) {
				return true;
			}
		}
		return false;
	}
}
