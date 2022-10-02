package ar.gabrielsuarez.glib.web;

import java.util.HashMap;
import java.util.Map;

import ar.gabrielsuarez.glib.G;
import spark.Request;

public class WebParameters {

	/* ========== ATRIBUTOS ========== */
	private Map<String, Object> parametros = new HashMap<>();

	/* ========== CONSTRUCTOR ========== */
	public WebParameters(Request request) {
		for (String parametro : request.queryParams()) {
			String valor = request.queryParams(parametro);
			parametros.put(parametro, valor);
		}

		try {
			Map<String, Object> body = null;
			body = G.toMap(request.body());
			if (body != null) {
				parametros.putAll(body);
			}
		} catch (Exception e) {
		}
	}

	/* ========== SET ========== */
	public Object set(String clave, Object valor) {
		return parametros.put(clave, valor);
	}

	/* ========== STRING ========== */
	public String string(String parametro) {
		String valor = string(parametro, null);
		if (valor == null) {
			throw new RuntimeException("El parametro '" + parametro + "' es obligatorio");
		}
		return valor;
	}

	public String string(String parametro, String valorPorDefecto) {
		return G.toString(parametros.get(parametro), valorPorDefecto);
	}

	/* ========== INTEGER ========== */
	public Integer integer(String parametro) {
		Integer valor = integer(parametro, null);
		if (valor == null) {
			if (parametro == null || parametro.isEmpty()) {
				throw new RuntimeException("El parametro '" + parametro + "' es obligatorio");
			}
			throw new RuntimeException("El parametro '" + parametro + "' debe ser un número entero");
		}
		return valor;
	}

	public Integer integer(String parametro, Integer valorPorDefecto) {
		return G.toInteger(parametros.get(parametro), valorPorDefecto);
	}

	/* ========== BOOLEAN ========== */
	public Boolean bool(String parametro) {
		Boolean valor = bool(parametro, null);
		if (valor == null) {
			throw new RuntimeException("El parametro '" + parametro + "' es obligatorio");
		}
		return valor;
	}

	public Boolean bool(String parametro, Boolean valorPorDefecto) {
		return G.toBoolean(parametros.get(parametro), valorPorDefecto);
	}
}
