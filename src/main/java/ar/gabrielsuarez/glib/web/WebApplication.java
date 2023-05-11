package ar.gabrielsuarez.glib.web;

import java.util.function.Function;

import ar.gabrielsuarez.glib.G;
import ar.gabrielsuarez.glib.data.Data;
import spark.Request;
import spark.Response;
import spark.Route;

public abstract class WebApplication<T extends WebContext> {

	/* ========== ATTRIBUTES ========== */
	private WebServer server;
	private Class<T> contextType;

	/* ========== ABSTRACT ========== */
	protected abstract void endpoints();

	protected abstract void before(T context);

	protected abstract void after(T context);

	protected abstract void exception(T context, Exception e);

	protected abstract void ultimately(T context);

	/* ========== HTTP ========== */
	protected void all(String path, Function<T, Object> funcion) {
		get(path, funcion);
		post(path, funcion);
		put(path, funcion);
		patch(path, funcion);
		delete(path, funcion);
	}

	protected void get(String path, Function<T, Object> funcion) {
		server.get(path, handle(funcion));
	}

	protected void post(String path, Function<T, Object> funcion) {
		server.post(path, handle(funcion));
	}

	protected void put(String path, Function<T, Object> funcion) {
		server.put(path, handle(funcion));
	}

	protected void patch(String path, Function<T, Object> funcion) {
		server.patch(path, handle(funcion));
	}

	protected void delete(String path, Function<T, Object> funcion) {
		server.delete(path, handle(funcion));
	}

	protected void head(String path, Function<T, Object> funcion) {
		server.head(path, handle(funcion));
	}

	protected void trace(String path, Function<T, Object> funcion) {
		server.trace(path, handle(funcion));
	}

	protected void connect(String path, Function<T, Object> funcion) {
		server.connect(path, handle(funcion));
	}

	protected void options(String path, Function<T, Object> funcion) {
		server.options(path, handle(funcion));
	}

	/* ========== SETTER ========== */
	void setServer(WebServer server) {
		this.server = server;
	}

	void setContextType(Class<T> contextType) {
		this.contextType = contextType;
	}

	/* ========== HANDLE ========== */
	private Route handle(Function<T, Object> function) {
		Route route = new Route() {
			public Object handle(Request sparkRequest, Response sparkResponse) {
				T context = G.instance(contextType);
				context.init(sparkRequest, sparkResponse);
				try {
					before(context);
					context.response.body = function.apply(context);
					after(context);
				} catch (Exception e) {
					context.response.httpCode = 500;
					exception(context, e);
				} finally {
					ultimately(context);
				}
				sparkResponse.status(context.response.httpCode);
				Object body = context.response.body;
				if (body instanceof Data) {
					context.response.setContentType("application/json");
				}
				for (String header : context.response.headers.keySet()) {
					sparkResponse.header(header, context.response.headers.get(header));
				}
				if (context.request.gzipEnabled()) {
					sparkResponse.header("Content-Encoding", "gzip");
				}
				return body != null ? body : "";
			}
		};
		return route;
	}
}
