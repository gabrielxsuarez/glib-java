package ar.gabrielsuarez.glib.core;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public abstract class XStream {

	/* ========== STREAM ========== */
	public static byte[] toBytes(InputStream inputStream) {
		return toByteArrayOutputStream(inputStream).toByteArray();
	}

	public static String toString(InputStream inputStream) {
		return toByteArrayOutputStream(inputStream).toString();
	}

	public static ByteArrayOutputStream toByteArrayOutputStream(InputStream inputStream) {
		try {
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			for (int length; (length = inputStream.read(buffer)) != -1;) {
				baos.write(buffer, 0, length);
			}
			return baos;
		} catch (Exception e) {
			return null;
		}
	}
}
