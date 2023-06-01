package ar.gabrielsuarez.glib.web;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import ar.gabrielsuarez.glib.data.Data;
import spark.Request;

public class WebRequest {

	/* ========== ATTRIBUTES ========== */
	private Request sparkRequest;
	private Data headers;
	private WebParameters webParameters;

	/* ========== INIT ========== */
	void init(Request sparkRequest) {
		this.sparkRequest = sparkRequest;
	}

	/* ========== METHODS ========== */
	public HttpServletRequest raw() {
		return sparkRequest != null ? sparkRequest.raw() : null;
	}

	public String method() {
		return sparkRequest != null ? sparkRequest.requestMethod() : null;
	}

	public String uri() {
		String uri = sparkRequest != null ? sparkRequest.uri() : "";
		return uri;
	}

	public Data headers() {
		if (headers == null) {
			Data data = new Data();
			if (sparkRequest != null) {
				for (String key : sparkRequest.headers()) {
					data.set(key, sparkRequest.headers(key));
				}
			}
			data.caseSensitive(false);
			headers = data;
		}
		return headers;
	}

	public String header(String key) {
		return headers().string(key);
	}

	public WebParameters parameters() {
		if (webParameters == null) {
			webParameters = new WebParameters();
			webParameters.init(sparkRequest);
		}
		return webParameters;
	}

	public WebParameters parameters(Boolean failOnNull) {
		WebParameters parameters = parameters();
		parameters.failOnNull = failOnNull;
		return parameters;
	}

	public String ip() {
		String ip = "";
		if (sparkRequest != null) {
			String xForwardedFor = headers().string("x-forwarded-for", "");
			if (!xForwardedFor.isEmpty()) {
				ip = xForwardedFor.split(",")[0].trim();
			} else {
				ip = sparkRequest.ip();
			}
		}
		return ip;
	}

	public String userAgent() {
		String userAgent = sparkRequest != null ? headers().string("user-agent", "") : "";
		return userAgent;
	}

	public Boolean gzipEnabled() {
		Boolean gzipEnabled = false;
		if (sparkRequest != null) {
			String acceptEncoding = headers().string("Accept-Encoding", "");
			if (!acceptEncoding.isEmpty()) {
				String[] tokens = acceptEncoding.split(",");
				if (Arrays.stream(tokens).map(String::trim).anyMatch(s -> s.equalsIgnoreCase("gzip"))) {
					return true;
				}
			}
		}
		return gzipEnabled;
	}
}
