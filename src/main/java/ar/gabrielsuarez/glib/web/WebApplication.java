package ar.gabrielsuarez.glib.web;

import java.util.function.Function;

import ar.gabrielsuarez.glib.G;
import spark.Request;
import spark.Response;
import spark.Route;

public abstract class WebApplication<T extends WebContext> {

	/* ========== ATTRIBUTES ========== */
	private WebServer server = null;
	private Class<T> contextType;

	/* ========== SETTER ========== */
	void setWebServer(WebServer server) {
		this.server = server;
	}

	void setContextType(Class<T> contextType) {
		this.contextType = contextType;
	}

	/* ========== HTTP ========== */
	protected void get(String path, Function<T, Object> funcion) {
		server.get(path, process(funcion));
	}

	protected void post(String path, Function<T, Object> funcion) {
		server.post(path, process(funcion));
	}

	protected void put(String path, Function<T, Object> funcion) {
		server.put(path, process(funcion));
	}

	protected void patch(String path, Function<T, Object> funcion) {
		server.patch(path, process(funcion));
	}

	protected void delete(String path, Function<T, Object> funcion) {
		server.delete(path, process(funcion));
	}

	protected void head(String path, Function<T, Object> funcion) {
		server.head(path, process(funcion));
	}

	protected void trace(String path, Function<T, Object> funcion) {
		server.trace(path, process(funcion));
	}

	protected void connect(String path, Function<T, Object> funcion) {
		server.connect(path, process(funcion));
	}

	protected void options(String path, Function<T, Object> funcion) {
		server.options(path, process(funcion));
	}

	protected void any(String path, Function<T, Object> funcion) {
		server.get(path, process(funcion));
		server.post(path, process(funcion));
		server.put(path, process(funcion));
		server.patch(path, process(funcion));
		server.delete(path, process(funcion));
	}

	/* ========== PROCESS ========== */
	private Route process(Function<T, Object> function) {
		Route route = new Route() {
			public Object handle(Request request, Response response) {
				WebResponse webResponse = null;
				T context = G.instance(contextType);
				context.setRequest(request);
				try {
					before(context);
					Object body = function.apply(context);
					webResponse = after(context, body);
				} catch (Exception e) {
					webResponse = exception(context, e);
				}
				response.status(webResponse.httpCode);
				for (String header : webResponse.headers.keySet()) {
					response.header(header, webResponse.headers.get(header));
				}
				if (context.gzipEnabled()) {
					response.header("Content-Encoding", "gzip");
				}
				return webResponse.body;
			}
		};
		return route;
	}

	/* ========== ABSTRACT ========== */
	protected abstract void endpoints();

	protected abstract void before(T context);

	protected abstract WebResponse after(T context, Object body);

	protected abstract WebResponse exception(T context, Exception e);
}
