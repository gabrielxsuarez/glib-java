package ar.gabrielsuarez.glib.web;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spark.Request;
import spark.Response;

public class WebContext {

    /* ========== ATTRIBUTES ========== */
    private Request request;
    private Response response;
    private WebParameters parameters;

    /* ========== GETTER ========== */
    public HttpServletRequest request() {
        return this.request.raw();
    }

    public HttpServletResponse response() {
        return this.response.raw();
    }

    public WebParameters parameters() {
        return this.parameters;
    }

    /* ========== SETTER ========== */
    void setRequest(Request request) {
        this.request = request;
    }

    void setResponse(Response response) {
        this.response = response;
    }

    void setParameters(WebParameters parameters) {
        this.parameters = parameters;
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
