package ar.gabrielsuarez.glib.web;

import java.util.ArrayList;
import java.util.List;

import ar.gabrielsuarez.glib.G;
import spark.Route;
import spark.Service;

public class WebServer {

	/* ========== ATTRIBUTES ========== */
	private Service http = Service.ignite();
	private List<WebApplication<?>> applications = new ArrayList<>();

	/* ========== METHODS ========== */
	public void run(Integer port) {
		http.port(port);
		for (WebApplication<?> aplicacion : applications) {
			aplicacion.endpoints();
		}
		http.init();
		http.awaitInitialization();
		for (WebApplication<?> aplicacion : applications) {
			aplicacion.init();
		}
	}

	public <A extends WebApplication<C>, C extends WebContext> void register(Class<A> applicationType, Class<C> contextType) {
		A application = G.instance(applicationType);
		application.setServer(this);
		application.setContextType(contextType);
		applications.add(application);
	}

	public void staticFiles(String path) {
		http.staticFileLocation(path);
	}

	/* ========== HTTP ========== */
	void get(String path, Route route) {
		http.get(path, route);
	}

	void post(String path, Route route) {
		http.post(path, route);
	}

	void put(String path, Route route) {
		http.put(path, route);
	}

	void patch(String path, Route route) {
		http.patch(path, route);
	}

	void delete(String path, Route route) {
		http.delete(path, route);
	}

	void head(String path, Route route) {
		http.head(path, route);
	}

	void trace(String path, Route route) {
		http.trace(path, route);
	}

	void connect(String path, Route route) {
		http.connect(path, route);
	}

	void options(String path, Route route) {
		http.options(path, route);
	}
}
