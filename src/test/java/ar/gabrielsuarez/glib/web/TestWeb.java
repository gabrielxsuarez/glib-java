package ar.gabrielsuarez.glib.web;

import ar.gabrielsuarez.glib.web.TestWeb.Contexto;

public class TestWeb extends WebApplication<Contexto> {

	public static void main(String[] args) {
		WebServer server = new WebServer();
		server.register(TestWeb.class, Contexto.class);
		server.run(8080);
	}

	public static class Contexto extends WebContext {
		public void init() {
		}
	}

	protected void endpoints() {
		post("/:hola", contexto -> saludar(contexto));
	}

	protected void init() {
	}

	public Object saludar(Contexto contexto) {
		return contexto.parameters;
	}

	protected void before(Contexto contexto) {
	}

	protected void after(Contexto contexto) {
	}

	protected void exception(Contexto contexto, Exception e) {
	}
}
