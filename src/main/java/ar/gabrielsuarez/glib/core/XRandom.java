package ar.gabrielsuarez.glib.core;

import java.security.SecureRandom;

public abstract class XRandom {

	/* ========== ATTRIBUTES ========== */
	private static SecureRandom secureRandom = new SecureRandom();

	/* ========== RANDOM ========== */
	public static Integer randomInt(Integer min, Integer max) {
		return secureRandom.nextInt(max - min) + min;
	}

	@SafeVarargs
	public static <T> T randomFrom(T... values) {
		return values.length > 0 ? values[randomInt(0, values.length - 1)] : null;
	}
}
