package ar.gabrielsuarez.glib.core;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class XException {

	/* ========== INSTANCE ========== */
	public static RuntimeException runtimeException(Throwable t) {
		RuntimeException runtimeException = null;
		if (t instanceof RuntimeException) {
			runtimeException = (RuntimeException) t;
		} else {
			runtimeException = new RuntimeException(t);
		}
		return runtimeException;
	}

	/* ========== STACKTRACE ========== */
	public static Throwable getCause(Throwable t) {
		while (t.getCause() != null) {
			t = t.getCause();
		}
		return t;
	}
	
	public static StackTraceElement stackTraceFirstElement(Throwable t, String packageName) {
		t = getCause(t);
		StackTraceElement[] ste = t.getStackTrace();
		StackTraceElement st = null;
		for (Integer i = 0; i < ste.length; ++i) {
			if (ste[i].getClassName().startsWith(packageName)) {
				st = ste[i];
				break;
			}
		}
		return st;
	}

	public static String toString(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String stackTrace = sw.toString();
		return stackTrace;
	}
}
