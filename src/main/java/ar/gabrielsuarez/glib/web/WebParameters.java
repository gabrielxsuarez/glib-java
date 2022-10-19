package ar.gabrielsuarez.glib.web;

import java.util.LinkedHashMap;
import java.util.Map;

import ar.gabrielsuarez.glib.G;
import spark.Request;

public class WebParameters {

    /* ========== ATTRIBUTES ========== */
    private Map<String, Object> map = new LinkedHashMap<>();

    /* ========== INSTANCE ========== */
    public WebParameters() {
    }

    /* ========== LOAD ========== */
    WebParameters load(Request request) {
        for (String parameter : request.params().keySet()) {
            String value = request.queryParams(parameter);
            map.put(parameter, value);
        }
        try {
            Map<String, Object> body = null;
            body = G.toMap(request.body());
            if (body != null) {
                map.putAll(body);
            }
        } catch (Exception e) {
        }
        return this;
    }

    /* ========== METHODS ========== */
    public Object get(String key) {
        return map.get(key);
    }

    public Object put(String ket, Object value) {
        return map.put(ket, value);
    }

    public Object remove(String clave) {
        return map.remove(clave);
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
        return G.toString(map.get(parametro), valorPorDefecto);
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
        return G.toInteger(map.get(parametro), valorPorDefecto);
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
        return G.toBoolean(map.get(parametro), valorPorDefecto);
    }

    /* ========== STRING ========== */
    public String toString() {
        return G.toJson(map);
    }
}
