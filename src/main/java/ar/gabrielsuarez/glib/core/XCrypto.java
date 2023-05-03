package ar.gabrielsuarez.glib.core;

import java.security.MessageDigest;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ar.gabrielsuarez.glib.G;

public abstract class XCrypto {

	/* ========== HASH ========== */
	public static String md2(String value) {
		return hash("MD2", value);
	}

	public static String md5(String value) {
		return hash("MD5", value);
	}

	public static String sha(String value) {
		return hash("SHA", value);
	}

	public static String sha224(String value) {
		return hash("SHA-224", value);
	}

	public static String sha256(String value) {
		return hash("SHA-256", value);
	}

	public static String sha384(String value) {
		return hash("SHA-384", value);
	}

	public static String sha512(String value) {
		return hash("SHA-512", value);
	}

	public static String sha512_244(String value) {
		return hash("SHA-512/224", value);
	}

	public static String sha512_256(String value) {
		return hash("SHA-512/256", value);
	}

	/* ========== PROTECTED ========== */
	protected static List<String> hashAlgorithms() {
		List<String> list = new ArrayList<>();
		Provider[] providers = Security.getProviders();
		for (Provider provider : providers) {
			Set<Service> services = provider.getServices();
			for (Service service : services) {
				if (MessageDigest.class.getSimpleName().equals(service.getType())) {
					list.add(service.getAlgorithm());
				}
			}
		}
		return list;
	}

	protected static String hash(String algorithm, String value) {
		try {
			if (value != null) {
				MessageDigest md = MessageDigest.getInstance(algorithm);
				byte[] bytes = md.digest(value.getBytes());
				return G.toHex(bytes);
			}
			return null;
		} catch (Exception e) {
			throw G.runtimeException(e);
		}
	}
}
