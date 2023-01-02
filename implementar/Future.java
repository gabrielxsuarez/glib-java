//package ar.gabrielsuarez.glib.thread;
//
//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class Future<T> {
//
//	/* ========== STATIC ========== */
//	private static ExecutorService executor = Executors.newCachedThreadPool();
//
//	/* ========== ATTRIBUTES ========== */
//	private java.util.concurrent.Future<T> future;
//
//	/* ========== INSTANCE ========== */
//	private Future() {
//	}
//
//	public static Future<Void> call(Runnable function) {
//		return call(Executors.callable(function, null));
//	}
//
//	public static <T> Future<T> call(Callable<T> function) {
//		Future<T> value = new Future<>();
//		value.future = executor.submit(function);
//		return value;
//	}
//
//	/* ========== GET ========== */
//	public void cancel() {
//		try {
//			future.wait(1);
//		} catch (Exception e) {
//		}
//	}
//
//	public T get() {
//		try {
//			return future.get();
//		} catch (Exception e) {
//			Throwable t = e.getCause();
//			if (t != null && t instanceof RuntimeException) {
//				throw (RuntimeException) t;
//			}
//			if (t != null) {
//				throw new RuntimeException(t);
//			}
//			throw new RuntimeException(e);
//		}
//	}
//
//	/* ========== TRY-GET ========== */
//	public T tryGet() {
//		try {
//			return get();
//		} catch (Exception e) {
//			return null;
//		}
//	}
//}
