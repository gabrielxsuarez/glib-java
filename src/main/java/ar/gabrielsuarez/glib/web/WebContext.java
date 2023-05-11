package ar.gabrielsuarez.glib.web;

import spark.Request;
import spark.Response;

public abstract class WebContext {

	/* ========== ATTRIBUTES ========== */
	public WebRequest request = new WebRequest();
	public WebResponse response = new WebResponse();

	/* ========== INIT ========== */
	void init(Request request, Response response) {
		this.request.init(request);
		this.response.init(response);
	}
}
