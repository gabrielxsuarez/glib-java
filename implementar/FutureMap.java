//package ar.gabrielsuarez.glib.thread;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.Set;
//import java.util.function.Function;
//
//public class FutureMap<K, V> {
//
//	/* ========== ATTRIBUTES ========== */
//	private Map<K, Future<V>> futureMap = new LinkedHashMap<>();
//
//	/* ========== INSTANCE ========== */
//	private FutureMap() {
//	}
//
//	public static <K, V> FutureMap<K, V> call(Iterable<K> collection, Function<K, V> funcion) {
//		FutureMap<K, V> value = new FutureMap<>();
//		for (K item : collection) {
//			value.futureMap.put(item, Future.call(() -> funcion.apply(item)));
//		}
//		return value;
//	}
//
//	/* ========== GET ========== */
//	public Set<K> keySet() {
//		return futureMap.keySet();
//	}
//
//	public V get(K key) {
//		Future<V> future = futureMap.get(key);
//		if (future != null) {
//			return future.get();
//		}
//		return null;
//	}
//
//	/* ========== TRY-GET ========== */
//	public V tryGet(K key) {
//		Future<V> future = futureMap.get(key);
//		if (future != null) {
//			return future.tryGet();
//		}
//		return null;
//	}
//
//	public void close() {
//		for (K key : futureMap.keySet()) {
//			futureMap.get(key).cancel();
//		}
//	}
//}
