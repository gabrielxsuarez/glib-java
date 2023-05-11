package ar.gabrielsuarez.glib.core;

import java.util.Collection;
import java.util.function.Predicate;

public abstract class XLambda {

	public static <T> T findFirst(Collection<T> collection, Predicate<? super T> predicate) {
		return collection.stream().filter(predicate).findFirst().orElse(null);
	}
}