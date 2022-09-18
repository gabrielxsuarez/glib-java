package ar.gabrielsuarez.glib.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import ar.gabrielsuarez.glib.G;

public abstract class XFile {

	/* ========== CHECK ========== */
	public static Boolean fileExists(String path) {
		return new File(path).exists();
	}

	/* ========== READ ========== */
	public static String readFile(String path) {
		try {
			return new String(Files.readAllBytes(Paths.get(path)));
		} catch (Exception e) {
			throw G.runtimeException(e);
		}
	}

	/* ========== WRITE ========== */
	public static File writeFile(String path, String content) {
		new File(path).getParentFile().mkdirs();
		try (PrintWriter printWriter = new PrintWriter(path)) {
			printWriter.write(content);
		} catch (Exception e) {
			throw G.runtimeException(e);
		}
		return new File(path);
	}

	public static File writeFile(String basePath, String relativePath, String content) {
		String path = new File(basePath, relativePath).getAbsolutePath();
		return writeFile(path, content);
	}

	public static File writeFile(String path, byte[] content) {
		try (FileOutputStream fos = new FileOutputStream(path)) {
			fos.write(content);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new File(path);
	}

	public static File writeFile(String basePath, String relativePath, byte[] content) {
		String path = new File(basePath, relativePath).getAbsolutePath();
		return writeFile(path, content);
	}
}
