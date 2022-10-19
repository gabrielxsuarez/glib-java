package ar.gabrielsuarez.glib.web;

import ar.gabrielsuarez.glib.web.TestWeb.Contexto;

public class TestWeb extends WebApplication<Contexto> {
	
	public static void main(String[] args) {
		WebServer server = new WebServer();
		server.register(TestWeb.class, Contexto.class);
		server.run(8080);
	}

	public static class Contexto extends WebContext {
	}

	protected void endpoints() {
		post("/:hola", contexto -> saludar(contexto));
	}

	public Object saludar(Contexto contexto) {
		return contexto.parameters;
	}

	protected void before(Contexto contexto) {
	}

	protected WebResponse after(Contexto contexto, Object body) {
		WebResponse response = new WebResponse();
		response.body = body;
		return response;
	}

	protected WebResponse exception(Contexto contexto, Exception e) {
		e.printStackTrace();
		return null;
	}
}
