package ar.gabrielsuarez.glib.core;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public abstract class Enviroment {

	/* ========== CHECK ========== */
	public static Boolean isWindows() {
		return System.getProperty("os.name", "generic").toLowerCase().contains("windows");
	}

	public static Boolean isJar() {
		return "jar".equalsIgnoreCase(Thread.currentThread().getContextClassLoader().getResource("").getProtocol());
	}

	public static Boolean isDocker() {
		try (Stream<String> stream = Files.lines(Paths.get("/proc/1/cgroup"))) {
			return stream.anyMatch(line -> line.contains("docker"));
		} catch (Exception e) {
			return false;
		}
	}

	/* ========== PATH ========== */
	public static String desktopPath() {
		return new File(System.getProperty("user.home"), "Desktop").getAbsolutePath();
	}

	public static String desktopPath(String relativePath) {
		return new File(desktopPath(), relativePath).getAbsolutePath();
	}

	public static String compiledPath() {
		return new File(Thread.currentThread().getContextClassLoader().getResource("").getPath()).getAbsolutePath();
	}

	public static String sourcePath() {
		return sourcePath("src/main/java");
	}

	public static String sourcePath(String relativePath) {
		File currentPath = new File(compiledPath());
		while (currentPath.getParentFile() != null) {
			currentPath = currentPath.getParentFile();
			Set<String> names = new HashSet<>(Arrays.asList(currentPath.list()));
			if (names.contains("pom.xml") || names.contains("build.gradle")) {
				return new File(currentPath.getAbsoluteFile(), relativePath).getAbsolutePath();
			}
		}
		return null;
	}
}
