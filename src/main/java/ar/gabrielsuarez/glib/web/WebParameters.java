package ar.gabrielsuarez.glib.web;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import ar.gabrielsuarez.glib.G;
import ar.gabrielsuarez.glib.data.Data;
import ar.gabrielsuarez.glib.data.DataFile;
import spark.Request;

public class WebParameters extends Data {

	/* ========== ATTRIBUTES ========== */
	private Map<String, DataFile> files = new LinkedHashMap<>();

	/* ========== OPTIONS ========== */
	public Boolean failOnNull = true;

	/* ========== INIT ========== */
	WebParameters init(Request request) {
		loadPath(request);
		loadQueryAndForm(request);
		loadBody(request);
		loadMultipart(request);
		return this;
	}

	/* ========== PRIVATE ========== */
	private Boolean isMultipart(Request request) {
		String contentType = request.headers("Content-Type");
		return contentType != null && contentType.contains("multipart/form-data");
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
				if (G.likeJson(body)) {
					this.loadJson(body);
				} else if (G.likeXml(body)) {
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

	/* ========== FILES ========== */
	public Set<String> files() {
		return files.keySet();
	}

	public DataFile file(String fileName) {
		return files.get(fileName);
	}

	public WebParameters setFile(DataFile file) {
		if (file != null && file.name != null) {
			files.put(file.name, file);
		}
		return this;
	}

	/* ========== DATA ========== */
	public Object object(String key) {
		return WebParamterException.check(failOnNull, key, super.object(key, null));
	}

	public Data data(String key) {
		return WebParamterException.check(failOnNull, key, super.data(key, null));
	}

	public Map<String, Object> map(String key) {
		return WebParamterException.check(failOnNull, key, super.map(key, null));
	}

	public List<Object> list(String key) {
		return WebParamterException.check(failOnNull, key, super.list(key, null));
	}

	public String string(String key) {
		return WebParamterException.check(failOnNull, key, super.string(key, null));
	}

	public Boolean bool(String key) {
		return WebParamterException.check(failOnNull, key, super.bool(key, null));
	}

	public Short shortInt(String key) {
		return WebParamterException.check(failOnNull, key, super.shortInt(key, null));
	}

	public Integer integer(String key) {
		return WebParamterException.check(failOnNull, key, super.integer(key, null));
	}

	public Long longInt(String key) {
		return WebParamterException.check(failOnNull, key, super.longInt(key, null));
	}

	public Float floatNumber(String key) {
		return WebParamterException.check(failOnNull, key, super.floatNumber(key, null));
	}

	public Double doubleNumber(String key) {
		return WebParamterException.check(failOnNull, key, super.doubleNumber(key, null));
	}

	public BigInteger bigInteger(String key) {
		return WebParamterException.check(failOnNull, key, super.bigInteger(key, null));
	}

	public BigDecimal bigDecimal(String key) {
		return WebParamterException.check(failOnNull, key, super.bigDecimal(key, null));
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

	/* ========== EXCEPTION ========== */
	public static class WebParamterException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public String key;
		public Object value;

		public WebParamterException(String key, Object value) {
			this.key = key;
			this.value = value;
		}

		public static <T> T check(Boolean failOnNull, String key, T value) {
			if (value == null && failOnNull) {
				throw new WebParamterException(key, value);
			}
			return value;
		}
	}
}
