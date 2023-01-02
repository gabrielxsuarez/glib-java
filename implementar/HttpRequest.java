package ar.gabrielsuarez.glib.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import ar.gabrielsuarez.glib.G;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpRequest {

	private static final OkHttpClient okHttpClient = okHttpClient();

	/* ========== ATRIBUTOS ========== */
	public String metodo;
	public String url;
	public Map<String, Object> headers = new LinkedHashMap<String, Object>();
	public Map<String, Object> paths = new LinkedHashMap<String, Object>();
	public Map<String, Object> querys = new LinkedHashMap<String, Object>();
	public Map<String, Object> body = new LinkedHashMap<String, Object>();

	/* ========== CONSTRUCTOR ========== */
	public HttpRequest(String metodo, String url) {
		this.metodo = metodo;
		this.url = url;
	}

	/* ========== URL ========== */
	private static String urlEncode(String url) {
		try {
			return url != null ? URLEncoder.encode(url, "UTF-8") : "";
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public String url() {
		String url = this.url;
		for (String clave : paths.keySet()) {
			String valor = (String) paths.get(clave);
			url = url.replace("{" + clave + "}", urlEncode(valor));
		}

		Integer i = 0;
		for (String clave : querys.keySet()) {
			String valor = (String) querys.get(clave);
			url += (i++ == 0 ? "?" : "&") + urlEncode(clave) + "=" + urlEncode(valor);
		}
		return url;
	}

	/* ========== PARAMETROS ========== */
	public void header(String clave, String valor) {
		this.headers.put(clave, valor);
	}

	public void path(String clave, String valor) {
		this.paths.put(clave, valor);
	}

	public void query(String clave, String valor) {
		this.querys.put(clave, valor);
	}

	public void body(String clave, Object valor) {
		this.body.put(clave, valor);
	}

	/* ========== LOG ========== */
	public String log() {
		StringBuilder log = new StringBuilder();

		log.append(metodo).append(" ").append(url()).append("\n");
		for (String clave : headers.keySet()) {
			if (headers.get(clave) != null) {
				log.append(clave).append(": ").append(headers.get(clave)).append("\n");
			}
		}
		if (!"GET".equals(metodo)) {
			log.append(headers.isEmpty() ? "" : "\n").append(G.toJson(body)).append("\n");
		}
		return log.toString();
	}

	/* ========== EJECUCION ========== */
	public HttpResponse run() {
		return runJson();
	}

	private HttpResponse runJson() {
		Request.Builder builder = new Request.Builder();
		builder.url(url());
		for (String clave : headers.keySet()) {
			String valor = (String) headers.get(clave);
			builder.addHeader(clave, valor != null ? valor : "");
		}
		if ("GET".equals(metodo)) {
			builder.get();
		} else {
			builder.method(metodo, RequestBody.create(G.toJson(body), MediaType.get("application/json; charset=utf-8")));
		}
		Request request = builder.build();
		Response response = null;
		try {
			response = okHttpClient.newCall(request).execute();
			ResponseBody responseBody = response.body();
			HttpResponse httpResponse = new HttpResponse();
			httpResponse.code = response.code();
			httpResponse.body = responseBody != null ? responseBody.string() : "";
			Map<String, List<String>> multimap = response.headers().toMultimap();
			for (String clave : response.headers().toMultimap().keySet()) {
				String valor = "";
				for (String item : multimap.get(clave)) {
					valor += valor.isEmpty() ? item : "; " + item;
				}
				httpResponse.headers.put(clave, valor);
			}
			return httpResponse;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	/* ========== METODOS PRIVADOS ========== */
	private static OkHttpClient okHttpClient() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.connectTimeout(10, TimeUnit.SECONDS);
		builder.writeTimeout(10, TimeUnit.SECONDS);
		builder.readTimeout(30, TimeUnit.SECONDS);

		final ProxySelector proxySelector = new ProxySelector() {
			public java.util.List<Proxy> select(final URI uri) {
				final List<Proxy> proxyList = new ArrayList<Proxy>(1);
				proxyList.add(Proxy.NO_PROXY);
				return proxyList;
			}

			public void connectFailed(URI uri, SocketAddress socketAddress, IOException ioException) {
				throw new RuntimeException(ioException);
			}
		};

		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
			}

			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}
		} };

		SSLContext trustAllSslContext = null;
		try {
			trustAllSslContext = SSLContext.getInstance("SSL");
			trustAllSslContext.init(null, trustAllCerts, new java.security.SecureRandom());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (KeyManagementException e) {
			throw new RuntimeException(e);
		}

		builder.sslSocketFactory(trustAllSslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
		builder.hostnameVerifier(new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});

		OkHttpClient http = builder.proxySelector(proxySelector).build();
		return http;
	}
}
