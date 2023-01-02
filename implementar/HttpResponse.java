package ar.gabrielsuarez.glib.http;

import java.util.LinkedHashMap;
import java.util.Map;

import ar.gabrielsuarez.glib.data.Data;

/** @author Gabriel Suarez */
public class HttpResponse {

	/* ========== ATRIBUTOS ========== */
	public Integer code;
	public Map<String, String> headers = new LinkedHashMap<>();
	public String body;

	/* ========== CONSTRUCTOR ========== */
	public HttpResponse() {
	}

	public HttpResponse(String body) {
		this.code = 200;
		this.body = body;
	}

	public HttpResponse(Integer code, String body) {
		this.code = code;
		this.body = body;
	}

	public HttpResponse(String code, String body) {
		this.code = Integer.parseInt(code);
		this.body = body;
	}

	/* ========== METODOS ========== */
	public Data jsonBody() {
		Data objeto = Data.fromJson(body);
		return objeto;
	}

	/* ========== LOG ========== */
	public String log() {
		StringBuilder log = new StringBuilder();
		try {
			log.append(Data.fromJson(body).toJson()).append("\n");
		} catch (Exception e) {
			log.append(body).append("\n");
		}
		return log.toString();
	}
}
