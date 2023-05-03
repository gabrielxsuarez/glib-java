package ar.gabrielsuarez.glib.web;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import ar.gabrielsuarez.glib.G;
import ar.gabrielsuarez.glib.data.Data;
import ar.gabrielsuarez.glib.data.DataFile;
import spark.Request;

public class WebParameters extends Data {

	/* ========== ATTRIBUTES ========== */
	private Map<String, DataFile> files = new LinkedHashMap<>();

	/* ========== INSTANCE ========== */
	public WebParameters() {
	}

	/* ========== LOAD ========== */
	WebParameters load(Request request) {
		loadPath(request);
		loadQueryAndForm(request);
		loadBody(request);
		loadMultipart(request);
		return this;
	}

	private void loadPath(Request request) {
		for (String pathParam : request.params().keySet()) {
			set(pathParam, request.params(pathParam));
		}
	}

	private void loadQueryAndForm(Request request) {
		for (String queryParam : request.queryParams()) {
			set(queryParam, request.queryParams(queryParam));
		}
	}

	private void loadBody(Request request) {
		if (!isMultipart(request)) {
			String body = request.body();
			try {
				if (G.posibleJson(body)) {
					this.loadJson(body);
				} else if (G.posibleXml(body)) {
					this.loadXml(body);
				}
			} catch (Exception e) {
			}
		}
	}

	private void loadMultipart(Request request) {
		try {
			if (isMultipart(request)) {
				request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement(G.tmpPath()));
				for (Part part : request.raw().getParts()) {
					String key = part.getName();
					String fileName = part.getSubmittedFileName();
					if (fileName == null) {
						String value = G.toString(part.getInputStream());
						this.set(key, value);
					} else {
						byte[] bytes = G.toBytes(part.getInputStream());
						DataFile file = new DataFile(fileName, bytes);
						files.put(key, file);
					}
				}
			}
		} catch (Exception e) {
			throw G.runtimeException(e);
		}
	}

	/* ========== GETTER ========== */
	public Map<String, DataFile> files() {
		return files;
	}

	public DataFile file(String fileName) {
		return files.get(fileName);
	}

	/* ========== PRIVATE ========== */
	private Boolean isMultipart(Request request) {
		return request.headers("Content-Type").contains("multipart/form-data");
	}

	/* ========== TOSTRING ========== */
	public String toString() {
		Data data = Data.fromData(this);
		for (String key : files.keySet()) {
			String value = "[binary:" + files.get(key).bytes.length + "]";
			data.set(key, value);
		}
		return data.toString();
	}
}
