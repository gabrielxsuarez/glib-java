package ar.gabrielsuarez.glib.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public abstract class XCollection {

	/* ========== COLLECTIONS ========== */
	@SafeVarargs
	public static <T extends Object> List<T> listOf(T... values) {
		List<T> list = new ArrayList<>();
		for (int i = 0; i < values.length; ++i) {
			list.add(values[i]);
		}
		return list;
	}

	@SafeVarargs
	public static <T> Set<T> setOf(T... values) {
		Set<T> set = new LinkedHashSet<>();
		for (int i = 0; i < values.length; ++i) {
			set.add(values[i]);
		}
		return set;
	}

	/* ========== ADD ========== */
	public static <T> T add(Collection<T> collection, T value) {
		if (collection != null) {
			collection.add(value);
		}
		return value;
	}

	public static <T> T add(Collection<T> collection, T value, Boolean condition) {
		if (collection != null && condition != null && condition) {
			collection.add(value);
		}
		return value;
	}

	/* ========== MIN / MAX ========== */
	@SafeVarargs
	public static <T extends Comparable<T>> T min(T... values) {
		T min = null;
		for (T value : values) {
			if (value != null) {
				if (min == null || min.compareTo(value) > 0) {
					min = value;
				}
			}
		}
		return min;
	}

	@SafeVarargs
	public static <T extends Comparable<T>> T max(T... values) {
		T max = null;
		for (T value : values) {
			if (value != null) {
				if (max == null || max.compareTo(value) < 0) {
					max = value;
				}
			}
		}
		return max;
	}

	/* ========== EMPTY ========== */
	public static Boolean isEmpty(Object value) {
		return value == null || value.toString().isEmpty();
	}

	public static Boolean anyEmpty(Object... values) {
		Boolean anyEmpty = false;
		for (Object objeto : values) {
			anyEmpty |= isEmpty(objeto);
		}
		return anyEmpty;
	}

	public static Boolean allEmpty(Object... values) {
		Boolean allEmpty = values.length > 0;
		for (Object objeto : values) {
			allEmpty &= isEmpty(objeto);
		}
		return allEmpty;
	}

	@SafeVarargs
	public static <T> T firstNonEmpty(T... values) {
		for (T value : values) {
			if (!isEmpty(value)) {
				return value;
			}
		}
		return null;
	}

	@SafeVarargs
	public static <T> T lastNonEmpty(T... values) {
		T lastNonEmpty = null;
		for (T value : values) {
			if (!isEmpty(value)) {
				lastNonEmpty = value;
			}
		}
		return lastNonEmpty;
	}

	@SafeVarargs
	public static <T> T firstNonNull(T... values) {
		for (T value : values) {
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	@SafeVarargs
	public static <T> T lastNonNull(T... values) {
		T lastNonNull = null;
		for (T value : values) {
			if (value != null) {
				lastNonNull = value;
			}
		}
		return lastNonNull;
	}
}
