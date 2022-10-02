package ar.gabrielsuarez.glib.web;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;

public class WebResponse {

	/* ========== ATRIBUTOS ========== */
	public Integer httpCode = 200;
	public Map<String, String> headers = new LinkedHashMap<>();
	public Object body = "";

	/* ========== METODOS ========== */
	public static WebResponse crear(Integer httpCode, Object body) {
		WebResponse gettyResponse = new WebResponse();
		gettyResponse.httpCode = httpCode;
		gettyResponse.body = body;
		return gettyResponse;
	}

	public static WebResponse json(Integer httpCode, Object json, String charset) {
		WebResponse gettyResponse = new WebResponse();
		gettyResponse.httpCode = httpCode;
		try {
			if (charset != null) {
				gettyResponse.body = new String(json.toString().getBytes(), charset);
				gettyResponse.headers.put("Content-Type", "application/json; charset=" + charset);
			} else {
				gettyResponse.body = json;
				gettyResponse.headers.put("Content-Type", "application/json; charset=" + Charset.defaultCharset());
			}
		} catch (UnsupportedEncodingException e) {
			gettyResponse.body = json;
			gettyResponse.headers.put("Content-Type", "application/json");
		}
		return gettyResponse;
	}
}
