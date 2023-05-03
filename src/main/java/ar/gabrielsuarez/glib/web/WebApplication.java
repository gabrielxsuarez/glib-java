package ar.gabrielsuarez.glib.web;

import java.util.function.Function;

import ar.gabrielsuarez.glib.G;
import spark.Request;
import spark.Response;
import spark.Route;

public abstract class WebApplication<T extends WebContext> {

	/* ========== ATTRIBUTES ========== */
	private WebServer server;
	private Class<T> contextType;

	/* ========== SETTER ========== */
	void setServer(WebServer server) {
		this.server = server;
	}

	void setContextType(Class<T> contextType) {
		this.contextType = contextType;
	}

	/* ========== HTTP ========== */
	protected void any(String path, Function<T, Object> funcion) {
		get(path, funcion);
		post(path, funcion);
		put(path, funcion);
		patch(path, funcion);
		delete(path, funcion);
	}

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

	/* ========== PRIVATE ========== */
	private Route process(Function<T, Object> function) {
		Route route = new Route() {
			public Object handle(Request sparkRequest, Response sparkResponse) {
				T context = G.instance(contextType);
				context.setRequest(sparkRequest);
				context.setResponse(sparkResponse);
				context.parameters.load(sparkRequest);
				context.init();
				try {
					before(context);
					if (!context.response.isReady()) {
						Object body = function.apply(context);
						context.response.setBody(body);
					}
					if (!context.response.isReady()) {
						after(context);
					}
				} catch (Exception e) {
					context.response.setHttpCode(500);
					exception(context, e);
				}
				sparkResponse.status(context.response.httpCode());
				for (String header : context.response.headers()) {
					sparkResponse.header(header, context.response.header(header));
				}
				if (context.isGzipEnabled()) {
					sparkResponse.header("Content-Encoding", "gzip");
				}
				return context.response.body();
			}
		};
		return route;
	}

	/* ========== ABSTRACT ========== */
	protected abstract void init();

	protected abstract void endpoints();

	protected abstract void before(T context);

	protected abstract void after(T context);

	protected abstract void exception(T context, Exception e);
}
