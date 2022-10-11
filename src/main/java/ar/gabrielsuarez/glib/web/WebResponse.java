package ar.gabrielsuarez.glib.web;

import java.util.LinkedHashMap;
import java.util.Map;

public class WebResponse {

    /* ========== ATTRIBUTES ========== */
    public Integer httpCode = 200;
    public Map<String, String> headers = new LinkedHashMap<>();
    public Object body = "";
}
